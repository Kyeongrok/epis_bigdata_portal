/**
 * 쌀 - 시군구
 */
let PageObj = (function(){

	
	/**
	 * 초기
	 */
	PageObj.prototype.init = function(){
		//
		pageObj.setEventHandler();
		
		//특별시,광역시등은 화면에 표시하지 않음	0228
		$('li.sido').each(function(i,item){
			let sidoName = $(item).data('sido-name');
			
			//
			if(-1 !== sidoName.indexOf('특별') || -1 !== sidoName.indexOf('광역시')){
				$(item).remove();
			}
		});
		$('li.sido').show();
		
		
		//시도,직불금,단수 데이터 조회
		rdObj.getAllDatas(function(){
			//강제 시도 클릭
			let sidoCode = $('[name=sidoCode]').val();
			$('li.sido[data-sido-code="'+sidoCode+'"]').click();	
			
			
			//강제 생산량 예측 클릭
			$('button.thisYear.outtrn').click();
		});
		
		
	};
	
	
	
	
	/**
	 * 이벤트 등록
	 */
	PageObj.prototype.setEventHandler = function(){
		//추이 비교
		$('button.cmpr').click(function(){
			location.href = './riceCmprForm.do';
		});
		
		//나의 생산량 예측
		$('button.predict').click(function(){
			location.href = './ricePredictForm.do';
		});
		
		/**
		 * 연도별 생산량 추이 신청면적
		 */
		$('button.byYearOuttrnReqst').click(function(){

			//
			let labels = RiceDataObj.getYears();
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let yr = labels[i];
				
				let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':yr});
				let v = RiceDataObj.sum(subdatas, 'outtrnReqstAr');
				v = Math.round(v/RiceDataObj.UNIT);
				datas.push(v);
			}
			
			
			//차트
			$('div.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawLineChart(labels, datas, {'title':'연도별 생산량 추이(톤)', 'color':'#F7B3B8'});
			//표
			pageObj.showDataTable(labels, datas, '가로');
					
		});
		
		
		/**
		 * 연도별 생산량 추이 확정면적
		 */
		$('button.byYearOuttrnDcsn').click(function(){
			//
			let labels = RiceDataObj.getYears();
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let yr = labels[i];
				
				let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':yr});
				let v = RiceDataObj.sum(subdatas, 'outtrnDcsnAr');
				v = Math.round(v/RiceDataObj.UNIT);
				datas.push(v);
			}
			
			
			//차트
			$('div.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawLineChart(labels, datas, {'title':'연도별 생산량 추이(톤)', 'color':'#F7B3B8'});
			//표
			pageObj.showDataTable(labels, datas, '가로');
		});
		
		
		/**
		 * 연도별 면적 추이 신청면적
		 */
		$('button.byYearArReqst').click(function(){
			
			//
			let labels = RiceDataObj.getYears();
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let yr = labels[i];
				
				let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':yr});
				let v = RiceDataObj.sum(subdatas, 'reqstAr');
				v = Math.round(v);
				datas.push(v);
			}
			
			
			//차트
			$('div.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawLineChart(labels, datas, {'title':'연도별 재배면적 추이(㎡)', 'color':'#FF9FFF'});
			//표
			pageObj.showDataTable(labels, datas, '가로');
		});
		
		
		/**
		 * 연도별 면적 추이 확정면적
		 */
		$('button.byYearArDcsn').click(function(){
			
			
			//
			let labels = RiceDataObj.getYears();
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let yr = labels[i];
				
				let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':yr});
				let v = RiceDataObj.sum(subdatas, 'dcsnAr');
				v = Math.round(v);
				datas.push(v);
			}
			
			
			//차트
			$('div.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawLineChart(labels, datas, {'title':'연도별 재배면적 추이(㎡)', 'color':'#FF9FFF'});
			//표
			pageObj.showDataTable(labels, datas, '가로');
		});
		
		
		
		
		//시도 클릭
		$('li.sido').click(function(){
			$('li.sido').removeClass('on');
			$(this).addClass('on');
			
			//클릭된 시도 정보
			let sidoJson = pageObj.getSidoJson();

			//전역변수에 저장
			pageObj.datas = rdObj.byYearAndSigungu(sidoJson.sidoCode, sidoJson.sidoName);
			
			//
			pageObj.showTot(sidoJson.sidoCode, sidoJson.sidoName, pageObj.datas);

			//
			let idx = $(this).data('index');			
			let idxOfMap = pageObj.convertIndex(sidoJson.sidoName);
			//시군구명
			$('[name="mapArea"]').attr('src','../../resources/svc/rice/images/map_area'+  idxOfMap +'_name.png');
			//시군구 경계 이미지 변경
			$('.whole img').attr('src','../../resources/svc/rice/images/map_whole_area'+  idxOfMap +'.png');
			//선택된 시도명 표시
			$('p.areaName').html( sidoJson.sidoName);
			
			
			//강제 생산량 예측 클릭
			$('button.thisYear.outtrn').click();
			
		});
		
		
		/**
		 * 생산량 예측 클릭
		 */
		$('button.thisYear.outtrn').click(function(){
						
			//
			let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':RiceDataObj.LAST_YEAR});
			let labels = RiceDataObj.distinct(subdatas, 'sigunguName');
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let sigunguName = labels[i];
				
				//
				let arr = RiceDataObj.getDatas(subdatas, {'sigunguName':sigunguName});
				let v = RiceDataObj.sum(arr, 'outtrn');
				v = Math.round(v / RiceDataObj.UNIT);
				datas.push(v);
			}
			
			//차트 표시
			$('div.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawBarChart(labels, datas, {'title':'쌀 생산량 예측(톤)', 'color':'#88D2CE'});
			//
			pageObj.showDataTable(labels, datas, '세로');
		});
		
		
		/**
		 * 생산면적 클릭
		 */
		$('button.thisYear.ar').click(function(){
						
			//
			let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':RiceDataObj.LAST_YEAR});
			let labels = RiceDataObj.distinct(subdatas, 'sigunguName');
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let sigunguName = labels[i];
				
				//
				let arr = RiceDataObj.getDatas(subdatas, {'sigunguName':sigunguName});
				let v = RiceDataObj.sum(arr, 'ar');
				v = Math.round(v);
				datas.push(v);
			}
			
			//차트 표시
			$('div.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawBarChart(labels, datas, {'title':'직불금 면적(㎡)', 'color':'#A3BAE0'});
			//
			pageObj.showDataTable(labels, datas, '세로');
		});
		
		
		
		/**
		 * @deprecated 0312
		 * 년도별 생산량 추이 클릭
		 */
		$('button.byYear.outtrn').click(function(){
			
		});
		
		
		
		/**
		 * @deprecated 0312
		 * 년도별 재배면적 추이 클릭
		 */
		$('button.byYear.ar').click(function(){
			
		});
		
		
	};
	
	
	/**
	 * 해당 시도 하위의 시군구 목록
	 * @param sidoCode 시도 코드
	 */
	PageObj.prototype.getSigungus = function(sidoCode){
		let arr=[];
		
		//
		$('li.sido.on li.sigungu').each(function(i,item){
			arr.push({sigunguCode : $(item).data('sigungu-code'), sigunguName : $(item).data('sigungu-name')});
		});
		
		//
		return arr;
	};
	
	
	/**
	 * 선택된 시도코드,시도명 리턴
	 */
	PageObj.prototype.getSidoJson = function(){
		let json={};
		
		json.sidoCode = $('li.sido.on').data('sido-code');
		json.sidoName = $('li.sido.on').data('sido-name');
		
		//
		return json;
	};
	
	
	
	


	/**
	 * 화면의 인덱스와 지도명의 인덱스가 다름
	 * 화면의 인덱스로 지도명의 인덱스 구하기
	 * @param idx 화면의 인덱스
	 */
	PageObj.prototype.convertIndex = function(sidoName){
		let p = {};
		p['경기도'] = '04';
		p['강원도'] = '01';
		p['충청북도'] = '17';
		p['충청남도'] = '16';
		p['전라북도'] = '14';
		p['전라남도'] = '13';
		p['경상북도'] = '06';
		p['경상남도'] = '05';
		
		return p[sidoName];
	}
	
	
	
	/**
	 * 생산량 예측, 직불금 면적 화면에 표시
	 * 이 값은 시도단수 데이터로 계산함 by ygh
	 * @param sidoCode 시도 코드
	 * @param sidoName 시도 명
	 * @param yr 년도
	 */
	PageObj.prototype.showTot = function(sidoCode, sidoName, datas){
		
		//
		let subdatas = RiceDataObj.getDatas(datas, {'sidoName':sidoName, 'year':RiceDataObj.LAST_YEAR});
		
		//생산량
		let outtrn = RiceDataObj.sum(subdatas, 'outtrnDcsnAr');
		outtrn = Math.round(outtrn / RiceDataObj.UNIT);
		
		//면적
		let ar = RiceDataObj.sum(subdatas, 'dcsnAr');
		ar = Math.round(ar);
		
		//
		$('.tot.outtrn').html( $.cs.addComma(outtrn)).attr('title', RiceDataObj.LAST_YEAR + '년');
		$('.tot.ar').html( $.cs.addComma(ar)).attr('title', RiceDataObj.LAST_YEAR+'년');
		
	};
	
	

	/**
	 * 막대차트 그리기
	 * @param json {'title':string, 'color':string}
	 */
	PageObj.prototype.drawBarChart = function(labels, datas, json){
		//
		let ctx = document.getElementById('chart').getContext('2d');
		
		//
		let title = json.title;
		let c = json.color;
		
		//
		new Chart(ctx, {
			type : 'bar',
			data : {
				labels : labels,
				datasets : [
					{
						fill : false,
						label : title,
						data : datas,
						backgroundColor : c,
						borderColor : c
					}
				]
			},
			options:{
				tooltips: {
					mode: 'index',
					intersect: false,
					callbacks:{
						label:function(items, data){
							return $.cs.addComma(data['datasets'][0]['data'][items['index']]);
						}
					}
				},
				scales:{
					yAxes: [{
						ticks: {
							beginAtZero: false
						}
					}]
				}
			}
		});
	};
	
	

	/**
	 * 선 차트 그리기
	 * @param labels
	 * @param datas
	 * @param json {'title':string, 'color':string}
	 */
	PageObj.prototype.drawLineChart = function(labels, datas, json){
		//
		let ctx = document.getElementById('chart').getContext('2d');
		
		//
		let title = json.title;
		let c = json.color;
		
		//
		new Chart(ctx, {
			type : 'line',
			data : {
				labels : labels,
				datasets : [
					{
						fill : false,
						label : title,
						data : datas,
						backgroundColor : c,
						borderColor : c
					}
				]
			},
			options:{
				tooltips: {
					mode: 'index',
					intersect: false,
					callbacks:{
						label:function(items, data){
							return $.cs.addComma(data['datasets'][0]['data'][items['index']]);
						}
					}
				},
				scales:{
					yAxes: [{
						ticks: {
							beginAtZero: false
						}
					}]
				}
			}
		});
	};
	
	

	
	/**
	 * 데이터 테이블 생성
	 * @param labels
	 * @param datas
	 * @param direction
	 */
	PageObj.prototype.showDataTable = function(labels, datas, direction){
		//
		let _colgroup = function(len){
			let s = '';
			let x = Math.round(100 / len);
			//
			//s += '<col width="*"/>';
			for(let i=0; i<len; i++){
				s += '<col width="'+x+'%"/>';
			}
			
			//
			return s;
		};
		
		//
		let _title = function(labels){
			let s = '';
			
			//
			for(let i=0; i<labels.length; i++){
				let d = labels[i];
				
				//
				s += '<th>'+d+'</th>';
			}
			
			//
			return s;
		};
		
		//
		let _datas = function(datas){
			let s = '';
			
			//
			for(let i=0; i<datas.length; i++){
				let d = datas[i];
				
				//
				s += '<td>'+$.cs.addComma(d)+'</td>';
			}
			
					
			//
			return s;
		};
		
		
		let s = '';
		
		//
		if('세로' === direction){
			//
			s += ' <table class="grid tblData"> ';
			s += ' 	<colgroup> ';
			s += '		<col width="*"/>';
			s += '		<col width="50%"/>';
			s += ' 	</colgroup> ';
			s += ' 	<thead> ';
			s += ' 		<tr> ';
			s += '			<th>시군구</th>';
			s += '			<th>값</th>';
			s += ' 		</tr> ';
			s += ' 	</thead> ';
			s += ' 	<tbody> ';
			for(let i=0; i<labels.length; i++){
				s += ' 	<tr> ';
				s += '		<th>'+labels[i]+'</th>';
				s += '		<td>'+$.cs.addComma(datas[i])+'</td>';
				s += ' 	</tr> ';
			}
			s += ' 	</tbody> ';
			s += ' </table> ';
			
			//
			$('.gridWrap').html(s);
			return;
		}
		
		//
		s += ' <table class="grid tblData"> ';
		s += ' 	<colgroup> ';
		s += 		_colgroup(labels.length);
		s += ' 	</colgroup> ';
		s += ' 	<thead> ';
		s += ' 		<tr> ';
		//s += ' 			<th scope="col"></th> ';
		s +=			_title(labels);
		s += ' 		</tr> ';
		s += ' 	</thead> ';
		s += ' 	<tbody> ';
		s += ' 		<tr> ';
		//s += ' 			<th scope="row"></th> ';
		s +=			_datas(datas);
		s += ' 		</tr> ';
		s += ' 	</tbody> ';
		s += ' </table> ';
		
		//
		$('.gridWrap').html(s);		
	};
	
	
});


/**
 * 
 */
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});
