/**
 * 특광역시는 목록에 표시하지 않음
 */
let PageObj = (function(){

	//
	PageObj.imgSrc = {};
	
	
	
	
	
	/**
	 * 초기
	 */
	PageObj.prototype.init = function(){
		console.log('init');
		
		

		//이벤트 핸들러 등록
		this.setEventHandler();
		
		
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
			//전역변수에 저장
			pageObj.datas = rdObj.byYearAndSido();	
						
			//
			pageObj.showTot(pageObj.datas);
			
			//강제 생산량 예측 클릭
			$('button.thisYear.outtrn').click();
		});
		
		
	};
	
	
	
	/**
	 * 이벤트 핸들러 등록
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
		 * 연도별 생산량 신청면적 추이
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
		 * 연도별 생산량 확정면적 추이
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
		 * 연도별 재배면적 신청면적 추이
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
			pageObj.showDataTable(labels, datas, '세로');
		});
		
		/**
		 * 연도별 재배면적 확정면적 추이
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
			pageObj.showDataTable(labels, datas, '세로');
		});
		
		
		/**
		 * 생산량 예측 클릭
		 */
		$('button.thisYear.outtrn').click(function(){
						
			//
			let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':RiceDataObj.LAST_YEAR});
			let labels = RiceDataObj.distinct(subdatas, 'sidoName');
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let sidoName = labels[i];
				
				//
				let arr = RiceDataObj.getDatas(subdatas, {'sidoName':sidoName});
				let v = RiceDataObj.sum(arr, 'outtrn');
				v = Math.round(v / RiceDataObj.UNIT);
				datas.push(v);
			}
			
			//차트 표시
			$('div.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawBarChart(labels, datas, {'title':'쌀 생산량 예측(톤)', 'color':'#88D2CE'});
			//
			pageObj.showDataTable(labels, datas, '가로');
		});
		
		
		/**
		 * 직불금 면적 클릭
		 */
		$('button.thisYear.ar').click(function(){		
			
			//
			let subdatas = RiceDataObj.getDatas(pageObj.datas, {'year':RiceDataObj.LAST_YEAR});
			let labels = RiceDataObj.distinct(subdatas, 'sidoName');
			let datas=[];
			//
			for(let i=0; i<labels.length; i++){
				let sidoName = labels[i];
				
				//
				let arr = RiceDataObj.getDatas(subdatas, {'sidoName':sidoName});
				let v = RiceDataObj.sum(arr, 'ar');
				v = Math.round(v);
				datas.push(v);
			}
			
			//차트 표시
			$('.chartWrap').html('<canvas id="chart" width="792" ></canvas>').show();
			pageObj.drawBarChart(labels, datas, {'title':'직불금 면적(㎡)', 'color':'#A3BAE0'});
			//
			pageObj.showDataTable(labels, datas, '가로');
			
		});
		
		
		
		
		//지도 위 마우스 오버
		$('area[id^=map_area]').mouseover(function(){
			let sidoName = $(this).data('sido-name');
			
			//해당 시도 색깔 다른 지도 화면에 표시
			$('[name=mapArea]').prop('src', pageObj.getImgSrcBySidoName(sidoName));
			
			//시도 목록에서 선택상태로 변경
			$('li.sido')
				.removeClass('on')
				.each(function(i,item){
					if(sidoName === $(item).data('sido-name')){
						$(item).addClass('on');
					}
				});
			
		});
		
		//지도 클릭
		$('area[id^=map_area]').click(function(){
			//
			$('li.sido.on').click();
		});
		
		//시도 목록 마우스 오버
		$('li.sido').mouseover(function(){
			let sidoName = $(this).data('sido-name');
			
			//해당 시도 색깔 다른 지도 화면에 표시
			$('[name=mapArea]').prop('src', pageObj.getImgSrcBySidoName(sidoName));
			
		});
		
		
		
		//시도 클릭
		$('.sido').click(function(){
			//
			let sidoCode = $(this).data('sido-code');
			//
			location.href = './riceSigunguDetail.do?sidoCode=' + sidoCode;
		});
		
		
	};
	
	
	
	/**
	 * 시도명에 해당하는 이미지 url 리턴
	 */
	PageObj.prototype.getImgSrcBySidoName = function(sidoName){
		let filename = PageObj.imgSrc[sidoName];
		if(!$.cs.isEmpty(filename)){
			return filename;
		}
		
		//
		$('[id^=map_img]').each(function(i,item){
			if(sidoName === $(item).data('sido-name')){
				filename = $(item).prop('src');
			}
		});
		
		//
		PageObj.imgSrc[sidoName] = filename;
		
		//
		return filename;
	};
	
	
	PageObj.prototype.checkSido = function(sidoCode){
		//체크된 시도 선택 해제
		$('.sido').each(function(i,item){
			if($(item).hasClass('on')){
				$(item).removeClass('on')
					.closest('div')
					.find('.sigungu')
					.hide();
			}
		});
		
		//
		$('.sido').each(function(i,item){
			if(sidoCode === $(item).data('sido-code')){
				//해당 시도만 선택 상태로 변경
				$(item).addClass('on');
				//
				let $div = $(item).closest('div');
				$div.find('.sigungu').toggle();
				
				if(1 == $div.find('.sigungu:first:visible').length){
					$(this).addClass('on');
				}
			}
		});
	};
	
	
	
	/**
	 * 전체 생산량 예측, 직불금 면적 표시
	 */
	PageObj.prototype.showTot = function(datas){
		console.log('showTot', datas);
		
		
		//
		let subdatas = RiceDataObj.getDatas(datas, {'year':RiceDataObj.LAST_YEAR});
		
		//
		let outtrn=0, ar=0;
		for(let i=0; i<subdatas.length; i++){
			let d = subdatas[i];
			
			outtrn += d.outtrn;
			ar += d.ar;
		}
		
		//
		outtrn = Math.round(outtrn / RiceDataObj.UNIT);
		ar = Math.round(ar);
		
		//
		$('.infoWrap strong.outtrn').html($.cs.addComma(outtrn));
		$('.infoWrap strong.ar').html($.cs.addComma(ar));
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
			s += '			<th>년도</th>';
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


//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
})