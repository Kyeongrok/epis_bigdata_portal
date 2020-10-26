/**
 * 통계
 */
let StatsObj = (function(){
	
	
	
	StatsObj.rand = function(){
		return Math.floor((Math.random() * 10) + 1);
	};

	
	
	StatsObj.randNum = function(min, max){
		while(true){
			let num = Stats.rand();
			
			if(min <= num && num <= max){
				return num;
			}
		}
		
		//
		return 3;
	};
	

	/**
	 * @param denominators 분모
	 * @param dKey 분모 키
	 * @param dValueKey 분모 값 키
	 * @param numerators 분자
	 * @param nKey 분자 키
	 * @param nValueKey 분자 값 키
	 * @param labelKey 레이블 키
	 */
	StatsObj.xxx = function(denominators, dKey, dValueKey, numerators, nKey, nValueKey, labelKey){

		let labels = $.cs.getArr(denominators, dKey);
		
		//
		let arr=[];
		
		for (let j = 0; j < labels.length; j++) {
			let x = labels[j];
			
			//분모
			let v1=0;
			let lbl = '';
			for (let i = 0; i < denominators.length; i++) {
				let item = denominators[i];
				if(x === item[dKey]){
					v1 = item[dValueKey];
					lbl = item[labelKey];
				}
			}
			
			
			//분자
			let v2=0;
			for (let i = 0; i < numerators.length; i++) {
				let item = numerators[i];
				if(x === item[nKey]){
					v2 = item[nValueKey];
				}
			}
			
			//
			let x2=0;
			if(0 < v2){
				x2 = (v2 / v1) * 100;
			}
			
			//
			let p = {};
			p[dKey] = x;
			p.v1 = v1; //분모 값
			p.x1 = 100; //분모 비율
			p.v2 = v2; //분자 값
			p.x2 = parseFloat(x2.toFixed(1)); //분자 비율
			p.label = lbl; //레이블
			
			//100%넘는건 100으로 자름
			if(100 < p.x2){
				p.x2 = 100;
			}
			
			//
			arr.push(p);
		}
		
		
		
		
		//
		console.log('<<StatsObj.xxx', dKey, arr);
		return arr;
	
	};
	
	StatsObj.prototype.init = function(){
		statsObj.setEventHandler();
		
		//
		console.log('<<StatsObj.init');
	};
	
	StatsObj.prototype.setEventHandler = function(){
		$('button.sigungu.back').click(function(){
			$('#chartBySido').closest('div').show();
			$('#chartBySigungu').closest('div').hide();
			
		});
	};
	

	
	/**
	 * 시군구별 차트 그리기
	 */
	StatsObj.prototype.drawChartBySigungu = function(bsnsCode, sidoCode, sidoName, elId){
		let _drawChart = function(elId, denominators, numerators){
			//
			let ctx = document.getElementById(elId).getContext('2d');
			
			//
			let color1 = '#29aae2';
			let color2 = '#fcc05a';
			
			//
			let arr = StatsObj.xxx(denominators, 'sigunguCode', 'sumFre', numerators, 'sigunguCode', 'sumFmerFre', 'sigunguName');
//			arr.sort(function(a,b){
//				return b.x2 - a.x2;
//			});
			
			//
			let myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : $.cs.getArr(arr, 'label'),
					datasets : [/*{
						label : '전체 농업인 비율(%)',
						data : $.cs.getArr(arr, 'x1'),
						backgroundColor : color1,
						hoverBackgroundColor : color1
					},*/{
						label : '수혜 농업인 비율(%)',
						data : $.cs.getArr(arr, 'x2'),
						backgroundColor : color2,
						hoverBackgroundColor : color2
					}]
				},
				options : {			
					title : {
						display : true,
						text : sidoName + ' 시군구별'
					},
					tooltips: {
						mode: 'index',
						intersect: false
					},
					scales : {
						yAxes : [{
							ticks : {
								beginAtZero : true
							}
						}]
					},
					responsive : true,
					maintainAspectRatio : true
				}
			});
		};
		
		
		//
		let p={};
		p.bsnsCode = bsnsCode;
		p.sidoCode = sidoCode;
		p.gbn = 'bySigungu';
		$.cs.submitAjax('./getStatsDatas.json', p, function(res){
			
			//
			_drawChart(elId, res.denominators, res.numerators);
		});
	};
	
	
	
	
	/**
	 * 시도별 차트 그리기
	 */
	StatsObj.prototype.drawChartBySido = function(bsnsCode, elId){
		

		//
		let _drawChart = function(elId, denominators, numerators){
			//
			let ctx = document.getElementById(elId).getContext('2d');
			
			//
			let color1 = '#29aae2';
			let color2 = '#fcc05a';
			
			//
			let arr = StatsObj.xxx(denominators, 'sidoCode', 'sumFre', numerators, 'sidoCode', 'sumFmerFre', 'sidoName');
			arr.sort(function(a,b){
				return b.x2 - a.x2;
			});
			
			
			//
			let myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : $.cs.getArr(arr, 'label'),
					datasets : [/*{
						label : '전체 농업인 비율(%)',
						data : $.cs.getArr(arr, 'x1'),
						backgroundColor : color1,
						hoverBackgroundColor : color1
					},*/{
						label : '수혜 농업인 비율(%)',
						data : $.cs.getArr(arr, 'x2'),
						backgroundColor : color2,
						hoverBackgroundColor : color2
					}]
				},
				options : {			
					title : {
						display : true,
						text : '시도별'
					},
					tooltips: {
						mode: 'index',
						intersect: false
					},
					scales : {
						yAxes : [{
							ticks : {
								beginAtZero : true
							}
						}]
					},
					'onClick':function(evt, item){
						if($.cs.isNull(item) || 0 == item.length){
							return;
						}
						
						
						//분모 막대
						let sidoName = item[0]['_model']['label'];
						
						//
						let sidoCode='';
						for (let i = 0; i < StatsObj.sidos.length; i++) {
							let x = StatsObj.sidos[i];
							if(sidoName === x.sidoName){
								sidoCode = x.sidoCode;
							}
						}
						
						//
						if($.cs.isEmpty(sidoCode)){
							return;
						}
						
						//
						$('.stats.bySigungu').html('<canvas id="chartBySigungu" width="350" height="300" ></canvas>').show();
						statsObj.drawChartBySigungu(StatsObj.bsnsCode, sidoCode, sidoName, 'chartBySigungu');
						
						//
//						console.log(sidoName, evt,item);
					},
				      hover: {
				          onHover: function(e) {
				             var point = this.getElementAtEvent(e);
				             if (point.length) e.target.style.cursor = 'pointer';
				             else e.target.style.cursor = 'default';
				          }
				       },
					responsive : true,
					maintainAspectRatio : true
				}
			});
		};
		
		//
		let p={};
		p.bsnsCode = bsnsCode;
		p.gbn = 'bySido';
		$.cs.submitAjax('./getStatsDatas.json', p, function(res){
			
			//
			let sidos=[];
			for (let i = 0; i < res.denominators.length; i++) {
				let x = res.denominators[i];
				sidos.push({'sidoCode' : x.sidoCode, 'sidoName':x.sidoName});
			}

			StatsObj.sidos = sidos;
			StatsObj.bsnsCode = bsnsCode;
			
			//
			_drawChart(elId, res.denominators, res.numerators);
		});
	};
	
	/**
	 * 연령대별 차트 그리기
	 */
	StatsObj.prototype.drawChartByAge = function(bsnsCode, elId){
		console.log('>>StatsObj.drawChartByAge', bsnsCode, elId);
		
		//
		let _drawChart = function(elId, denominators, numerators){
			//
			let ctx = document.getElementById(elId).getContext('2d');
			
			//
			let color1 = '#29aae2';
			let color2 = '#645ca7';
			
			//
			let arr = StatsObj.xxx(denominators, 'AGE_GROUP', 'SUM_FRE', numerators, 'AGE_GROUP', 'SUM_FRE', 'AGE_GROUP');
			
			let arr2=[];
			let labels = ['40대미만', '40대', '50대', '60대', '65세 이상'];
			for(let i=0; i<labels.length; i++){
				let d = labels[i];
				
				//
				for(let j=0; j<arr.length; j++){
					if(d === arr[j].label){
						arr2.push(arr[j]);
						break;
					}
				}
			}
			
			//
			let myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : $.cs.getArr(arr2, 'label'),
					datasets : [/*{
						label : '전체 농업인 비율(%)',
						data : $.cs.getArr(arr, 'x1'),
						backgroundColor : color1,
						hoverBackgroundColor : color1
					},*/{
						label : '수혜 농업인 비율(%)',
						data : $.cs.getArr(arr2, 'x2'),
						backgroundColor : color2,
						hoverBackgroundColor : color2
					}]
				},
				options : {			
					title : {
						display : true,
						text : '연령대별'
					},
					tooltips: {
						mode: 'index',
						intersect: false
					},
					scales : {
						yAxes : [{
							ticks : {
								beginAtZero : true
							}
						}]
					},
					responsive : true,
					maintainAspectRatio : true
				}
			});
		};
		
		
		
		//
		let p={};
		p.bsnsCode = bsnsCode;
		p.gbn = 'byAge';
		$.cs.submitAjax('./getStatsDatas.json', p, function(res){
			//
			_drawChart(elId, res.denominators, res.numerators);
		});
		
	};
	
	
	
	
	/**
	 * 년도별 차트 그리기
	 */
	StatsObj.prototype.drawChartByYear = function(bsnsCode, elId){
		console.log('>>StatsObj.drawChartByYear', bsnsCode, elId);
	
	
		
		/**
		 *차트 그리기
		 *@param denominators 분모
		 *@param numerators 분자 
		 */
		let _drawChart = function(elId, denominators, numerators){
			let ctx = document.getElementById(elId).getContext('2d');
			
			//
			let arr = StatsObj.xxx(denominators, 'STDR_YEAR', 'FRMER_COUNT', numerators, 'STDR_YEAR', 'SUM_FMER_FRE', 'STDR_YEAR');
			
			//
			let color1 = '#29aae2';
			let color2 = '#bbce9c';
			
			let myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : $.cs.getArr(arr, 'label'),
					datasets : [/*{
						label : '전체 농업인 비율(%)',
						backgroundColor : color1,
						hoverBackgroundColor : color1,
						data : $.cs.getArr(arr, 'x1')
					},*/{
						label : '수혜 농업인 비율(%)',
						backgroundColor : color2,
						hoverBackgroundColor : color2,
						data : $.cs.getArr(arr, 'x2')
						
					}]
				},
				options : {			
					title : {
						display : true,
						text : '년도별'
					},
					tooltips: {
						mode: 'index',
						intersect: false
					},
					scales : {
						yAxes : [{
							ticks : {
								beginAtZero : true
							}
						}]
					},
					responsive : true,
					maintainAspectRatio : true
				}
			});
			
			//
			console.log('<<._drawChart', myChart);
		};
		
		
		//
		//데이터 조회
		//
		let p = {};
		p.gbn = 'byYear';
		p.bsnsCode = bsnsCode;
		
		//
		$.cs.submitAjax('./getStatsDatas.json', p, function(res){
			//
			_drawChart(elId, res.denominators, res.numerators);
		});			
		
		
	};
	
	

	/**
	 * 품목군별 차트 그리기
	 */
	StatsObj.prototype.drawChartByPrdlstSet = function(bsnsCode, elId){
		console.log('>>StatsObj.drawChartByPrdlstSet', bsnsCode, elId);
		
		//
		let _drawChart = function(elId, denominators, numerators){
			//
			let ctx = document.getElementById(elId).getContext('2d');
			
			//
			let color1 = '#29aae2';
			let color2 = '#eba9cc';
			
			//
			let arr = StatsObj.xxx(denominators, 'prdlstSetCode', 'sumFre', numerators, 'prdlstSetCode', 'sumFmerFre', 'prdlstSetName');
			arr.sort(function(a,b){
				return b.x2 - a.x2;
			});
			
			
			//
			let myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : $.cs.getArr(arr, 'label'),
					datasets : [/*{
						label : '전체 농업인 비율(%)',
						data : $.cs.getArr(arr, 'x1'),
						backgroundColor : color1,
						hoverBackgroundColor : color1
					},*/{
						label : '수혜 농업인 비율(%)',
						data : $.cs.getArr(arr, 'x2'),
						backgroundColor : color2,
						hoverBackgroundColor : color2
					}]
				},
				options : {			
					title : {
						display : true,
						text : '품목군별'
					},
					tooltips: {
						mode: 'index',
						intersect: false
					},
					scales : {
						yAxes : [{
							ticks : {
								beginAtZero : true
							}
						}]
					},
					responsive : true,
					maintainAspectRatio : true
				}
			});
		};
		
		
		
		//
		let p={};
		p.bsnsCode = bsnsCode;
		p.gbn = 'byPrdlstSet';
		$.cs.submitAjax('./getStatsDatas.json', p, function(res){
			//
			_drawChart(elId, res.denominators, res.numerators);
		});
		
	};
	
	
	
	
	/**
	 * 시도로 시군구 목록 조회
	 */
//	StatsObj.prototype.getSigungusBySido = function(sido, callbackFunction){
//		//TODO 데이터 조회
//		
//		//
//		callbackFunction([]);
//	};
			
});

let statsObj = new StatsObj();
$(document).ready(function(){
	statsObj.init();
})