/**************************************************************************
 * 디자인 받은 common.js 내용 추가
 * ************************************************************************/

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
	    
    var info_box = $('.info_box'); 
    var detail_info01 = $('.detail_info01');
    var detail_info02 = $('.detail_info02');
    var detail_info03 = $('.detail_info03');
    var infoWid = $('.Information').width();
    var Information = $('.Information');
    var close_button01 = detail_info01.find('.close_button');
    var detail_infoindex = 0;
    
   /* 마우스오버시*/
	detail_info01.add(detail_info02).add(detail_info03).hover(function(){
		/* 선택한 요소의 index값 */
		var _index = $(this).parent('ul').children('li').index(this);
		/* index로 위치를 요소를 셀렉터 한다음 hover 클래스 */
		detail_info01.eq(_index).add(detail_info02.eq(_index)).add(detail_info03.eq(_index)).addClass('hover');
	}, function(){
		/* hover 클래스 삭제 */
		detail_info01.add(detail_info02).add(detail_info03).removeClass('hover');
	});


   /* detail_info01 마우스 클릭*/
    detail_info01.on('click',function(){
		
    	// _index 0 : radish, 1 : cabbage, 2 : carrot
		var _index = detail_info01.index(this);
		detail_infoindex = _index;
		
		if($(this).hasClass('showbox01')) return false;

		detail_info01_click(this);
		detail_info02_click(detail_info02.eq(_index));
		detail_info03_click(detail_info03.eq(_index));
		
		detailInfoChartLoad(_index);
        
        // 재배의향 차트 보이기
		
		return false;
    });
    
	/* detail_info02 마우스 클릭 */
    detail_info02.on('click',function(){

		var _index = detail_info02.index(this);
		detail_infoindex = _index;
		
		if($(this).hasClass('showbox01')) return false;

		detail_info01_click(detail_info01.eq(_index));
		detail_info02_click(this);
		detail_info03_click(detail_info03.eq(_index));
		
		detailInfoChartLoad(_index);
        
		return false;
    });

    /* detail_info03 마우스 클릭 */
    detail_info03.on('click',function(){

		var _index = detail_info03.index(this);
		detail_infoindex = _index;
		
		if($(this).hasClass('showbox01')) return false;

		detail_info01_click(detail_info01.eq(_index));
		detail_info02_click(detail_info02.eq(_index));
		detail_info03_click(this);
		
		detailInfoChartLoad(_index);
        
		return false;
    });

	/* detail_info01 실행 function */
	function detail_info01_click(obj){
		
	   $(obj).addClass('showbox01');
       detail_info01.not(obj).css('display','none');
       $(obj).width(infoWid);
       $(obj).children('.content02').show();
       
	}

	/* detail_info02 실행 function */
	function detail_info02_click(obj){

       $(obj).addClass('showbox01');
       detail_info02.not(obj).css('display','none');
       $(obj).width(infoWid);
       $(obj).children('.content02').show();
	}

	/* detail_info03 실행 function */
	function detail_info03_click(obj){

       $(obj).addClass('showbox01');
       detail_info03.not(obj).css('display','none');
       $(obj).width(infoWid);
       $(obj).children('.content02').show();
	}
	
	 /*닫기*/
    close_button01.on('click',function(){

		var _index = close_button01.index(this);
		close_button01_click(_index);
		return false;
    });


	/* close_button01_click 실행 function */
	function close_button01_click(_index){

		/* 선택된 상세 info01 */
		var detail_info01_sel = detail_info01.eq(_index);
		var detail_info02_sel = detail_info02.eq(_index);
		var detail_info03_sel = detail_info03.eq(_index);
		
		detail_info01_sel.removeClass('showbox01');
        detail_info01.show();
        detail_info01.width('31%');

		detail_info02_sel.removeClass('showbox01');
        detail_info02.show();
        detail_info02.width('31%');

        detail_info03_sel.removeClass('showbox01');
        detail_info03.show();
        detail_info03.width('31%');
	}
	
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
	
	function detailInfoChartLoad(_index) {
		
		// ======================================================
		// S : 재배 생산량 예측 chart 그리기
		var graphTarget = detail_info01.eq(_index).children('.content02').children('div');
		var unit = '톤';
        //drawChart(graphTarget, graphTarget.attr('id') + '_cv3');
		if(detail_infoindex == 0) {
			drawChartDataWithLineAvg(graphTarget, graphTarget.attr('id') + '_cv3', prodFiveYearEachAvgMapDatas[0], chart01, unit);
	    } else if(detail_infoindex == 1) {
	    	drawChartDataWithLineAvg(graphTarget, graphTarget.attr('id') + '_cv3', prodFiveYearEachAvgMapDatas[1], chart01, unit);
	    } else {
	    	drawChartDataWithLineAvg(graphTarget, graphTarget.attr('id') + '_cv3', prodFiveYearEachAvgMapDatas[2], chart01, unit);
	    }
		
		// E : 재배 생산량 예측 chart 그리기
        
        // ======================================================		
		// S : 생산면적(재배의향 탭)에 chart 그리기
        
        var columnIndex = detail_infoindex + 1;
		var selectedDetailInfo = $('.detail_info02.column0' + columnIndex);
        
        var tabLinks = selectedDetailInfo.children('.content02').children('.tabWrap').children('.tabTitle').children('li');
        var tabConts = selectedDetailInfo.children('.content02').children('.tabWrap').children('.tabContWrap').children('.tabcontent');
        var tabId = selectedDetailInfo.children('.content02').children('.tabWrap').attr('id');
        
        var hasOn = false;

        // 선택된 탭 찾기
        tabConts.each(function(index, item) {
        	if($(item).hasClass("on")) {
        		hasOn = true;
        		$(tabLinks[index]).trigger("click");
        	}
        });
        
        // 선택된 탭이 없다면 첫번째 탭을 그려준다.
        if(!hasOn) {
        	$(tabLinks[0]).trigger("click");
        }
        
        // E : 생산면적(재배의향 탭)에 chart 그리기
        
        // ======================================================
        // S : 도매시장 거래가격 Chart 그리기
        // 월동무, 양배추, 당근 컬럼 찾기
		var columnIndex = detail_infoindex + 1;
		var selectedDetailInfo = $('.detail_info03.column0' + columnIndex);
		
		// typeA tab 일별, 주간별, 월별, 연간 탭
        var typeATab = selectedDetailInfo.children('.content02').find('.tabWrap.typeA').children('.tabTitle');
        var typeAOnTablink = typeATab.find('.tablinks.on');
        $(typeAOnTablink).trigger("click");
        
        // E : 도매시장 거래가격 Chart 그리기
	};


	/* TAB */
	//$('.typeA>.tabTitle>.tablinks,.typeB>.tabTitle>.tablinks').on('click',function(){
	$('.typeA>.tabTitle>.tablinks,.typeB>.tabTitle>.tablinks,.typeC>.tabTitle>.tablinks').on('click',function(){
		$(this).siblings().removeClass('on');
		$(this).addClass('on');
	});

	var prevGradeTab = -1;
	$('.tablinks').on('click',function(){
		
		var _index = $(this).parent('ul').children('li').index(this);
		
		// 생산면적 box(두번째박스)의 확정재배신고 링크 클릭시 암것도 안한다...
		if(_index == 3 && $(this).closest('.info_box03').length == 0) return;

		$(this).parent().siblings('.tabContWrap').children('.tabcontent').siblings().removeClass('on');
		$(this).parent().siblings('.tabContWrap').children('.tabcontent').eq(_index).addClass('on');
		
		if($(this).closest('.info_box03').length == 0) {
			
			// 탭에 chart 그리기
			var tabCont = $(this).parent().siblings('.tabContWrap').children('.tabcontent').eq(_index).children('.graphWrap');
			var tabId = $(this).closest('div').attr('id');
					
			if(!tabCont) {
			} else {
				// 생산면적 차트 그리기
				// columnIndex == 0 : 월동무, 1 : 양배추, 2 : 당근
				var unit = 'ha';
		        if(detail_infoindex == 0) {
		        	if(_index == 0) { // 1차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[0], willProdFirstMapDatas[0], chart02, '#ffdd00', '#EC008C', unit);
		        	}
		        	if(_index == 1) { // 2차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[0], willProdSecondMapDatas[0], chart02, '#ffdd00', '#8DC63F', unit);
		        	}
		        	if(_index == 2) {// 3차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[0], willProdThirdMapDatas[0], chart02, '#ffdd00', '#C2B59B', unit);
		        	}
		        	if(_index == 3) { // 확정 재배신고 
		        		
		        	}
		        } else if(detail_infoindex == 1) {
		        	if(_index == 0) { // 1차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[1], willProdFirstMapDatas[1], chart02, '#ffdd00', '#EC008C', unit);
		        	}
		        	if(_index == 1) {// 2차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[1], willProdSecondMapDatas[1], chart02, '#ffdd00', '#8DC63F', unit);
		        	}
		        	if(_index == 2) {// 3차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[1], willProdThirdMapDatas[1], chart02, '#ffdd00', '#C2B59B', unit);
		        	}
		        	if(_index == 3) {// 확정 재배신고
		        		
		        	}
		        } else {
		        	if(_index == 0) { // 1차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[2], willProdFirstMapDatas[2], chart02, '#ffdd00', '#EC008C', unit);
		        	}
		        	if(_index == 1) { // 2차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[2], willProdSecondMapDatas[2], chart02, '#ffdd00', '#8DC63F', unit);
		        	}
		        	if(_index == 2) { // 3차 재배의향
		        		drawChartDataMixed(tabCont, _index, confirmProdMapDatas[2], willProdThirdMapDatas[2], chart02, '#ffdd00', '#C2B59B', unit);
		        	}
		        	if(_index == 3) {// 확정 재배신고
		        		
		        	}
		        }
			}
		} else {	
			
			// 도매시장 거래가격 그래프 그리기
			
			// 월동무, 양배추, 당근 컬럼 찾기
			var columnIndex = detail_infoindex + 1;
			var selectedDetailInfo = $('.detail_info03.column0' + columnIndex);
			
			// typeA tab 일별, 주간별, 월별, 연간 탭
	        var typeATab = selectedDetailInfo.children('.content02').find('.tabWrap.typeA').children('.tabTitle');
	        var typeAOnTablink = typeATab.find('.tablinks.on');
	        var typeAOnTabContent = selectedDetailInfo.children('.content02').children('.tabWrap.typeA').children('.tabContWrap').children('.tabcontent.on');
	        
	        // typeB tab 평균, 상품, 중품, 하품
	        var typeBOnTablink = typeAOnTabContent.find('.tabWrap.typeB').find('.tablinks.on');
	        var typeBOnTabContent = typeAOnTabContent.find('.tabWrap.typeB').children('.tabContWrap').children('.tabcontent.on');
	        
	        // typeC tab 전년, 최근5년간, 평년
	        var typeCOnTablink = typeBOnTabContent.find('.tabWrap.typeC').find('.tablinks.on');
	        var typeCOnTabContent = typeBOnTabContent.find('.tabWrap.typeC').children('.tabContWrap').children('.tabcontent.on');
	        
	        var graphTarget = typeCOnTabContent.children('div');
	        var tabIndexConcat = typeAOnTablink.index() + '_' + typeBOnTablink.index() + '_' + typeCOnTablink.index() + '_cv3';
	        
	        // 컬럼정보(월동무, 양배추, 당근) 
	        var cropParam;			// 작물컬럼
	        var intervalParam;		// 조회기간탭
	        var gradeParam;			// 등급탭
	        var viewpointParam;		// 조회시점 막대그래프
	        
	        // 작물
        	if(columnIndex == 1) {
        		cropParam = '월동무';
        	} else if(columnIndex == 2) {
        		cropParam = '양배추';
        	} else if(columnIndex == 3) {
        		cropParam = '당근';
        	}
        	
        	// 조회기간
        	if(typeAOnTablink.index() == 0) {
        		intervalParam = dateReq;
        	} else if(typeAOnTablink.index() == 1) {
        		intervalParam = weekReq;
        	} else if(typeAOnTablink.index() == 2) {
        		intervalParam = monthReq;
        	} else if(typeAOnTablink.index() == 3) {
        		intervalParam = yearReq;
        	}
        	
        	// 조회시점
    		viewpointParam = typeCOnTablink.index();
    		
    		// 등급
    		if(typeBOnTablink.index() == 0) {	// 평균
    			gradeParam = "";
    		} else if(typeBOnTablink.index() == 1) {	// 특
    			gradeParam = gradeExtra;
    		} else if(typeBOnTablink.index() == 2) {	// 상
    			gradeParam = gradeGood;
    		} else if(typeBOnTablink.index() == 3) {	// 보통
    			gradeParam = gradeAvg;
    		}
        	
        	// parameter settings
        	var arr = [];
        	arr.push({'name':'crop', 'value':cropParam+''}); 				// 작물
        	arr.push({'name':'interval', 'value':intervalParam+''}); 		// 일별, 주간별, 월별, 연간 탭 정보 (dateReq, weekReq, monthReq, yearReq)
        	arr.push({'name':'viewpoint', 'value':viewpointParam+''}); 		// 조회시점
    		arr.push({'name':'grade', 'value':gradeParam+''});				// 등급 정보 : 평균, 특, 상, 보통
    		
    		// line color settings
        	if(typeCOnTablink.index() == 0) {			// 전년 -> 이 경우는 꺽은선이 좌로 한칸 이동해야 한다. ㅎㅎㅎ ㅅㅂ
        		lineColor = '#00AEEF';
    		} else if (typeCOnTablink.index() == 1) { 	// 최근5년
    			lineColor = '#EC008C';
    		} else if (typeCOnTablink.index() == 2) {	// 평년
    			lineColor = '#8DC63F';
    		}
        	
    		// 전년, 최근5년, 평년        	
    		$.cs.submitAjax('/bdp/svc/getPriceByGrade.json', arr, function(res) {
    			var barDatas = res.datas[0];
            	var lineDatas = res.datas[1];
            	barDatas = sortObject(barDatas);
            	lineDatas = sortObject(lineDatas);
            	// 토요일, 일요일 제거 시발
            	if(intervalParam == dateReq) {
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
            	
            	if (typeAOnTablink.index() == 3 && typeCOnTablink.index() == 0) {
            		// line data를 왼쪽으로 1년 shift 전년 + 연간도매시장 가격 추이일경우 
            		lineDatas = shiftLeft(lineDatas);
            	}
            	var unit = '원';
            	drawChartDataMixed(graphTarget, tabIndexConcat, barDatas, lineDatas, chart03, '#ffffff', lineColor, unit);
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
							//console.log('xAxes value : ', value);
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