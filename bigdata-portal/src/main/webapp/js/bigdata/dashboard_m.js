var chart01 = "chart01";
var chart02 = "chart02";
var chart03 = "일별도매시장가격";

var dateReq = "1d";
var weekReq = "1w";
var monthReq = "1M";
var yearReq = "1y";

var gradeExtra = '특';
var gradeGood = '상';
var gradeAvg = '보통';

$(document).ready(function() {
	
/* TAB */

	$('.tablinks').on('click',function(){

		$(this).siblings().removeClass('on');
		$(this).addClass('on');

		var _index = $(this).parent('ul').children('li').index(this);

		$(this).parent().siblings('.tabContWrap').children('.tabcontent').siblings().removeClass('on');
		$(this).parent().siblings('.tabContWrap').children('.tabcontent').eq(_index).addClass('on');
		
		/*
		 * crop 작물
		 * 		prod 예상생산량 (graph)
		 * 		area 재배면적
		 * 			will 1차재배의향, 2차재배의향, 3차재배의향 (graph)
		 * 		price 도매시장
		 * 			period  일별, 주별, 월별, 연간
		 * 				grade 평균, 특품, 상품, 보통
		 * 					years 전년, 5년간평균, 평년 (graph)
        */
		
		var cropContent = $(document).find('.tabcontent.crop.on');
		var cropIndex = cropContent.index();
		
		var prodContent = cropContent.find('.tabcontent.prod.on');
		var areaContent = cropContent.find('.tabcontent.area.on');
		var priceContent = cropContent.find('.tabcontent.price.on');
		
		if(prodContent.length > 0) {
			console.log('prod on');
			var target = prodContent.find('.graphWrap');
			var unit = '톤';
			// 예상생산량 그래프
			drawChartDataWithLineAvg(target, 'crop_' + cropIndex + '_cv3', prodFiveYearEachAvgMapDatas[cropIndex], chart01, unit);
		} else if(areaContent.length > 0) {
			console.log('area on');
			// 재배면적 그래프
			// 선택된 재배의향 탭을 찾는다.
			var willContent = areaContent.find('.tabcontent.will.on');
			var target = willContent.find('.graphWrap');
			var willIndex = willContent.index();
			var lineColor;
			if(willIndex == 0) { lineColor = '#EC008C'; }
			if(willIndex == 1) { lineColor = '#8DC63F'; }
			if(willIndex == 2) { lineColor = '#C2B59B'; }
			var unit='ha';
			drawChartDataMixed(target, cropIndex, confirmProdMapDatas[cropIndex], willProdFirstMapDatas[cropIndex], chart02, '#ffdd00', lineColor, unit);
		} else if(priceContent.length > 0) {
			console.log('price on');
			var periodContent = priceContent.find('.tabcontent.period.on');
			var periodIndex = periodContent.index();
			
			var gradeContent = periodContent.find('.tabcontent.grade.on');
			var gradeIndex = gradeContent.index();
			
			var yearsContent = gradeContent.find('.tabcontent.years.on');
			var yearsIndex = yearsContent.index();
			
			var target = yearsContent.find('.graphWrap');
			
			var cropParam;			// 작물컬럼
	        var periodParam;		// 조회기간탭
	        var gradeParam;			// 등급탭
	        var yearsParam;			// 조회시점 막대그래프
	        
	        // crop
        	if(cropIndex == 0) {
        		cropParam = '월동무';
        	} else if(cropIndex == 1) {
        		cropParam = '양배추';
        	} else if(cropIndex == 2) {
        		cropParam = '당근';
        	}
        	
        	// period
        	if(periodIndex == 0) {
        		periodParam = dateReq;
        	} else if(periodIndex == 1) {
        		periodParam = weekReq;
        	} else if(periodIndex == 2) {
        		periodParam = monthReq;
        	} else if(periodIndex == 3) {
        		periodParam = yearReq;
        	}
        	
        	// 등급
    		if(gradeIndex == 0) {	// 평균
    			gradeParam = "";
    		} else if(gradeIndex == 1) {	// 특
    			gradeParam = gradeExtra;
    		} else if(gradeIndex == 2) {	// 상
    			gradeParam = gradeGood;
    		} else if(gradeIndex == 3) {	// 보통
    			gradeParam = gradeAvg;
    		}
        	
        	// 조회시점
    		yearsParam = yearsIndex;
        	
        	// parameter settings
        	var arr = [];
        	arr.push({'name':'crop', 'value':cropParam+''}); 				// 작물
        	arr.push({'name':'interval', 'value':periodParam+''}); 			// 일별, 주간별, 월별, 연간 탭 정보 (dateReq, weekReq, monthReq, yearReq)
        	arr.push({'name':'viewpoint', 'value':yearsParam+''}); 		// 조회시점
    		arr.push({'name':'grade', 'value':gradeParam+''});				// 등급 정보 : 평균, 특, 상, 보통
    		
    		// line color settings
        	if(yearsIndex == 0) {			// 전년 -> 이 경우는 꺽은선이 좌로 한칸 이동해야 한다. ㅎㅎㅎ ㅅㅂ
        		lineColor = '#00AEEF';
    		} else if (yearsIndex == 1) { 	// 최근5년
    			lineColor = '#EC008C';
    		} else if (yearsIndex == 2) {	// 평년
    			lineColor = '#8DC63F';
    		}
        	
        	// 전년, 최근5년, 평년        	
    		$.cs.submitAjax('/bdp/svc/getPriceByGrade.json', arr, function(res) {
    			var barDatas = res.datas[0];
            	var lineDatas = res.datas[1];
            	barDatas = sortObject(barDatas);
            	lineDatas = sortObject(lineDatas);
            	
            	// 토요일, 일요일 제거 시발
            	if(periodParam == dateReq) {
            		var keys = Object.keys(barDatas);
            		keys.forEach(function(key) {
            			var newKey = key.split(".").join("-");
            			var date = new Date(newKey);
            			var weekDay = date.getDay();
                		if(weekDay == 0 || weekDay == 6) {
                			delete barDatas[key];
                			delete lineDatas[key];
                		}
            		});
            	}
            	
            	if (periodIndex == 3 && yearsIndex == 0) {
            		// line data를 왼쪽으로 1년 shift 전년 + 연간도매시장 가격 추이일경우 
            		lineDatas = shiftLeft(lineDatas);
            	}
            	var unit = '원';
            	drawChartDataMixed(target, 'price', barDatas, lineDatas, chart03, '#ffffff', lineColor, unit);
    		});
		}
	});


/* 추이보기 버튼(popup 열기) */
	$('.btnOpenPop').on('click',function(){
		$(this).parent().siblings('.content02').addClass('on');
	});


/* 팝업 닫기 */
	$('.close_button').on('click',function(){
		$(this).parent('.content02').removeClass('on');
	});


/* 인트로 */
	$('.topMenu .tablinks').on('click',function(){
		$('.wrap').removeClass('intro');		
	});

	$('.wrap .gnb h1').on('click',function(){
		$('.wrap .on').removeClass('on');
		$('.tabTitle>.tablinks:first-child,.tabContWrap>.tabcontent:first-child').addClass('on');
		$('.topMenu .tablinks').removeClass('on');
		$('.Information>.tabContWrap>.tabcontent').removeClass('on');
		$('.wrap').addClass('intro');
	});
	
	/**
	 * chartjs - graph에 값 그리기
	 */
	Chart.plugins.register({
	    afterDatasetsDraw: function(chartInstance, easing) {
	    	var ctx = chartInstance.chart.ctx;
            var sum = 0;	            
            var chartType = chartInstance.chart.config.options.title.text;
            var barMetaDatas;
            var lineMetaDatas;
            
            chartInstance.data.datasets.forEach(function (dataset, i) {
                var meta = chartInstance.getDatasetMeta(i);
                
                if(!meta.hidden && meta.type == "line") {
                	
                	if(chartType == chart03) {
                		lineMetaDatas = meta.data;
                	}
                	meta.data.forEach(function(element, index) {
                		if(chartType == chart01 && index == 0) {	// 평균값 하나 만 표시하므로 처음 데이터에서만 그려준다.
	                        
	                        // 타원그리기
	                        /*ctx.globalAlpha = 0.6;
	                        ctx.fillStyle = 'blue';
	                        ctx.beginPath();
	                        ctx.ellipse(position.x + offset, position.y - fontSize * 4, 50, 50, 0, 0, 4 * Math.PI);
	                        ctx.fill();*/
	                        
	                        var img = new Image();
	                        img.onload = function(){
	                        	var displayVal = dataset.data[index];
	                			var position = element.tooltipPosition();
	                			var fontSize = 14;
		                        var fontStyle = 'bold';
		                        var fontFamily = 'Nanum Square';
		                        var displayText = "";
		                        var offset = 20;
	                        	
	                        	ctx.globalAlpha = 0.6;
	                          	ctx.drawImage(img, position.x - offset + 2, position.y - fontSize * 4 - offset);
	                          	ctx.globalAlpha = 1;
	                          
	                          	ctx.fillStyle = 'white';				// font color?
		                        ctx.textAlign = 'center';
		                        ctx.textBaseline = 'middle';
		                        
		                        fontSize = 15;
		                        fontStyle = 'normal';
		                        ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
		                        ctx.fillText("기준생산량평균", position.x + 19, position.y - fontSize * 3 - offset / 2);
		                        ctx.fillText("(최근5년)", position.x + 19, position.y - fontSize * 2 - offset / 2);
		                        
		                        displayVal = parseFloat(displayVal) * 0.0001;
		                        displayVal = displayVal.toFixed(1);
		                        
		                        fontSize = 18;
		                        fontStyle = 'bold';
		                        ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
		                        ctx.fillText(numberWithComma(displayVal), position.x + 19, position.y - fontSize);
	                        };
	                        img.src = '/images/bigdata/baloon01.png'; // Set source path	                        	                        
                		}
                		
                		// 생산면적그래프 line data 표기
                		if(chartType == chart02 && dataset.data[index] > 0) {
                			var displayVal = dataset.data[index];
                			var position = element.tooltipPosition();
                			var fontSize = 14;
	                        var fontStyle = 'bold';
	                        var fontFamily = 'Nanum Square';
	                        var displayText = "";
	                        var offset = 10;
	                        
	                        ctx.fillStyle = dataset.backgroundColor;				// font color?
	                        ctx.textAlign = 'center';
	                        ctx.textBaseline = 'middle';
	                        ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
	                        
	                        displayVal = parseFloat(displayVal).toFixed(0);
	                        
	                        fontSize = 18;
	                        ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
	                        ctx.fillText(numberWithComma(displayVal), position.x + offset, position.y - fontSize);
                		}
                	});
                }

                else if (!meta.hidden && meta.type == "bar") {
                	if(chartType == chart03) {
                		barMetaDatas = meta.data;
                	}
                    meta.data.forEach(function(element, index) {
                    	
                    	// 상단 재배생산량 차트
                    	if(chartType == chart01 || chartType == chart02) {
                    		if(dataset.data[index] > 0) {

		                        var dataString = chartInstance.data.labels[index];		// label
		                        var dataString2 = dataset.data[index];					// data
		                        var dataPercent = dataset.data[index] / sum * 100;		// percent			                        
		                        dataPercent = parseFloat(dataPercent).toFixed(0) + "%";
		                        
		                        // chart01 type은 데이터를 10000 : 1로 스케일 다운해 보여준다.
		                        if(chartType == chart01) {
		                        	dataString2 = parseFloat(dataString2) * 0.0001;
		                        	dataString2 = parseFloat(dataString2).toFixed(1);
		                        } else if (chartType == chart02) {
		                        	dataString2 = parseFloat(dataString2).toFixed(0);
		                        }

		                        ctx.textAlign = 'center';
		                        ctx.textBaseline = 'middle';
		                       
		                        // value
		                        var padding = 25;
		                        var position = element.tooltipPosition();
		                         
		                        var fontSize = 18;
		                        var fontStyle = 'bold';
		                        var fontFamily = 'Nanum Square';
		                        if(chartType == chart01) {
		                        	ctx.fillStyle = '#FAA519';
		                        } else {
		                        	ctx.fillStyle = '#808284';
		                        }
		                        ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
		                        ctx.fillText(numberWithComma(dataString2), position.x, position.y + element.height() - fontSize);
	                    	}
                    	// 하단 도매시장가격 차트
                    	} else if(chartType == chart03) {
                    		
                    	}
                    });
                }
                
                // 증감률 표시
                if(barMetaDatas && lineMetaDatas && chartType == chart03) {                	
                	// chart title?
                	var fontStyle = 'bold';
                    var fontFamily = 'Nanum Square';
                    var fontSize = 16;
                    //ctx.fillStyle = 'white';
                    //ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
                    //ctx.fillText(chart03, 60, 20);
                    
                    // fontsize
                	if(barMetaDatas.length > 10) {
                		fontSize = 9;
                	} else if(barMetaDatas.length < 6) {
                		fontSize = 18;
                	} else {
                		fontSize = 13;
                	}
                	
                	barMetaDatas.forEach(function(element, index) {
                		var position = element.tooltipPosition();
                        var offset = 10;
                        
                        var updownRate = 0;
                        var displayVal;
                        var lineDatas = chartInstance.data.datasets[0];
                        var barDatas = chartInstance.data.datasets[1];
                        
                        if(lineDatas.data[index] == 0 || barDatas.data[index] == 0) {
                        	displayVal = "N/A";
                        } else {
                        	updownRate = (barDatas.data[index] - lineDatas.data[index]) / lineDatas.data[index] * 100;
                        	displayVal = updownRate.toFixed(1) + "%";
                        }
                        
                        if(updownRate >= 0) {
                        	ctx.fillStyle = '#1072BA';
                        } else {
                        	ctx.fillStyle = '#EC2227';
                        }
                        if(displayVal == "N/A") {
                        	ctx.fillStyle = '#A7A9AC';
                        }
                        ctx.textAlign = 'center';
                        ctx.textBaseline = 'middle';
                        ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
                        ctx.fillText(displayVal, position.x, ctx.canvas.height / 2);
                	});
                }
            });
	    }
	});
    
});

function shiftLeft(inData) {
	var newObj = {};
	var keys = Object.keys(inData);
	keys.forEach(function(key, val) {
		var newKey = key - 1;
		newObj[newKey] = val;
	});
	
	return newObj;
}

//object를 키 이름으로 정렬하여 반환
function sortObject(o)
{
    var sorted = {},
    key, a = [];
    // 키이름을 추출하여 배열에 집어넣음
    for (key in o) {
        if (o.hasOwnProperty(key)) a.push(key);
    }

    // 키이름 배열을 정렬
    a.sort();
    // 정렬된 키이름 배열을 이용하여 object 재구성
    for (key=0; key<a.length; key++) {
        sorted[a[key]] = o[a[key]];
    }
    return sorted;
}

function drawChartDataWithLineAvg(target, cName, chartData, chartType, unit) {
	
	unit = (typeof unit !== 'undefined') ? unit : '';
	
	var canvasName = cName;
	var s = '';
		
	s += '<div name="' + canvasName + '" ><canvas id="' + canvasName + '"></canvas></div>';
	
	$('[name=' + canvasName + ']').remove();
	target.append($(s));
	
	var chartId = document.getElementById(canvasName);
	if(!chartId) {
		console.log('chartId is null');
		return;
	}
	
	iemDss = [];
	colors = [];
	lineDatas = [];
	var chartKeys = Object.keys(chartData);
	
	// get avg value
	var avg = 0;
	for(var i = 0; i < chartKeys.length; i++) {
		if(i == chartKeys.length - 1) {
			avg = avg
		} else {
			avg = parseFloat(avg) + parseFloat(chartData[chartKeys[i]]);
		}
	}
	avg = parseFloat(avg) / (chartKeys.length - 1);
	for(var i = 0; i < chartKeys.length; i++) {
		
		var dt = chartData[chartKeys[i]];
		iemDss.push(dt.toFixed(0));		
		if(i == chartKeys.length - 1) {
			colors.push('#ffdd00');
		} else {
			colors.push('#ffffff');
		}
		lineDatas.push(avg.toFixed(0));
	}
	
	var ctx = chartId.getContext('2d');
	var prodChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: Object.keys(chartData),
			datasets: [
				{
		            label: '',
		            data: lineDatas,
		            type: 'line',
		            fill: false,
		            backgroundColor: '#00AEEF',
		            borderColor: '#00AEEF'
		        },
				{
				label: "",
				data: iemDss,
				backgroundColor: colors
				}
	        ]
		},
		options: {
			scales: {
				xAxes: [{
					ticks: {
						fontColor: '#FFFFFF'
					}
				}],
				yAxes: [{
					ticks: {
						beginAtZero: true,
						fontColor: '#FFFFFF',
						callback: function(value, index, values) {
							if(this.end != values[0] * 2 - values[1]) {
								this.end = this.end + values[0] - values[1];
							}
							return value;
						}
					}
				}]
			},
			legend:{
				display: false,
				position:'bottom',
			},
			title: {
	            display: false,
	            text: chartType
	        },
	        tooltips: {
	        	callbacks: {
	        		label: function(tooltipItem, data) {
	                    var label = data.datasets[tooltipItem.datasetIndex].label || '';
	                    label += numberWithComma(tooltipItem.yLabel.toFixed(0)) + unit;
	                    return label;
	                }
	            }
	        }
		}
	});
}

//
function drawChartDataMixed(target, cName, barData, lineData, chartType, lastColor, lineColor, unit) {
	
	lastColor = (typeof lastColor !== 'undefined') ? lastColor : '#ffffff';
	lineColor = (typeof lineColor !== 'undefined') ? lineColor : '#000000';
	unit = (typeof unit !== 'undefined') ? unit : '';
	
	var canvasName = cName;
	var s = '';
		
	s += '<div name="' + canvasName + '" ><canvas id="' + canvasName + '"></canvas></div>';
	
	$('[name=' + canvasName + ']').remove();
	target.append($(s));
	
	var chartId = document.getElementById(canvasName);
	if(!chartId) {
		console.log('chartId is null');
		return;
	}
	
	iemDss = [];
	colors = [];
	lineIemDss = [];
	
	var barDataKeys = Object.keys(barData);
	var lineDataKeys = Object.keys(lineData);
	
	for(var i = 0; i < barDataKeys.length; i++) {
		var barTemp = parseFloat(barData[barDataKeys[i]]).toFixed(2);
		iemDss.push(barTemp);
		
		if(i == barDataKeys.length - 1) {
			colors.push(lastColor);
		} else {
			colors.push('#ffffff');
		}
		
		var lineTemp = 0;
		for(var j = 0; j < lineDataKeys.length; j++) {
			if(barDataKeys[i] == lineDataKeys[j]) {
				lineTemp = parseFloat(lineData[lineDataKeys[j]]).toFixed(2);
				break;
			}
		}
		lineIemDss.push(lineTemp);
	}
	
	var ctx = chartId.getContext('2d');
	var radishProdChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: barDataKeys,
			datasets: [
				{
		            //label: '기존생산량평균',
		            data: lineIemDss,
		            type: 'line',
		            fill: false,
		            backgroundColor: lineColor,
		            borderColor: lineColor
		        },
				{
				label: "",
				data: iemDss,
				backgroundColor: colors
				}
	        ]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true,
						fontColor: '#FFFFFF',
						callback: function(value, index, values) {
							if(this.end != values[0] * 2 - values[1]) {
								this.end = this.end + values[0] - values[1];
							}
							return value;
						}
					}
				}],
				xAxes: [{
					ticks: {
						fontColor: '#FFFFFF',
						callback: function(value, index, values) {
							if(value.length > 4) {
								return value.substring(5);
							}
							return value;
	                    }
					}
				}]
			},
			legend:{
				display: false,
				position:'bottom'
			},
			title: {
				display: false,
	            text: chartType
	        },
	        tooltips: {
	        	callbacks: {
	        		label: function(tooltipItem, data) {
	                    var label = data.datasets[tooltipItem.datasetIndex].label || '';
	                    label += numberWithComma(tooltipItem.yLabel.toFixed(0)) + unit;
	                    return label;
	                }
	            }
	        }
		}
	});
}

/**
 * 숫자에 콤마를 찍어준다.
 * @param val
 * @returns {*}
 */
function numberWithComma(x){
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
