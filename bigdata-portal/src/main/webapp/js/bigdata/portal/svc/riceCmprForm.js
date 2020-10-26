/**
 * 쌀 비교
 */
let PageObj = (function(){
	
	PageObj.UNIT = 1000000;
	
	
	
	/**
	 * 시군구 select html 생성
	 * @param id
	 * @param name
	 * @param classes
	 * @datas
	 */
	PageObj.getSigunguSelectHtml = function(id, name, classes, datas){
		let s = '';
		s += '<select';
		if(!$.cs.isEmpty(id)){
			s += ' id="'+id+'" ';
		}
		if(!$.cs.isEmpty(name)){
			s += ' name="'+name+'" ';
		}
		if(0 < classes.length){
			s += ' class="'+classes.join(' ')+'" ';
		}
		s += '>';
		
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			s += '<option value="'+d.sigunguCode+'">'+d.sigunguName+'</option>';
		}
		
		//
		s += '</select>';
		
		
		return s;
	};
	
	
	PageObj.getSidoCheckboxHtml = function(datas){
		let s = '';
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			if(-1 !== d.sidoName.indexOf('특별') || -1 !== d.sidoName.indexOf('광역')){
				continue;
			}
			
			//
			s += '<label><input type="checkbox" name="sido" data-sido-name="'+d.sidoName+'" value="'+d.sidoCode+'"><span>'+d.sidoName+'</span></label>';
		}
		
		//
		return s;
	};
	
	
	/**
	 * 동적으로 시도 select 생성하기 위한 html string
	 * @param id
	 * @param name
	 * @param classes
	 * @param datas
	 */
	PageObj.getSidoSelectHtml = function(id, name, classes, datas){
		let s = '';
		s += '<select';
		if(!$.cs.isEmpty(id)){
			s += ' id="'+id+'" ';
		}
		if(!$.cs.isEmpty(name)){
			s += ' name="'+name+'" ';
		}
		if(0 < classes.length){
			s += ' class="'+classes.join(' ')+'" ';
		}
		s += '>';
		
		//
		s += '<option value="">-선택-</option>';
		
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			if(-1 !== d.sidoName.indexOf('특별') || -1 !== d.sidoName.indexOf('광역')){
				continue;
			}
			
			//
			s += '<option value="'+d.sidoCode+'">'+d.sidoName+'</option>';
		}
		
		//
		s += '</select>';
		
		
		return s;
	};
	
	/**
	 * 초기화
	 */
	PageObj.prototype.init = function(){
		//
		let _sido = function(datas){
			//화면에 시도 체크박스 목록 추가
			let html = PageObj.getSidoCheckboxHtml(datas);
			$('td.sido.list').html(html);
			//시도 체크 이벤트 등록
			$('input[name=sido]').click(function(){
				if(5 < $('input[name=sido]:checked').length){
					alert('더 이상 시도를 선택할 수 없습니다.');
					$(this).prop('checked',false);
					return;
				}
			});
			
		};
		
		//
		pageObj.setEventHandler();
		
		//초기 데이터 로드
		rdObj.getAllDatas(function(){
			_sido(rdObj.sidos);
		});
		
		
		//bind 년도
		pageObj.bindYearDatas();
		
	};
	
	

	

	/**
	 * 
	 */
	PageObj.prototype.bindYearDatas = function(){
		let years = RiceDataObj.getYears();
		years.reverse();
		
		//
		for(let i=0; i<years.length; i++){
			let yr = years[i];
			let s = '<option value="'+yr+'">'+yr+'</option>';
			
			//
			if(0 == i){
				$('.fromYear,.toYear').html(s);			
			}else{
				$('.fromYear,.toYear').append(s);
			}
		}
		
		//
		$('.fromYear').prop('selectedIndex', years.length-1);
	};
	
	
	/**
	 * 이벤트 핸들러 등록
	 */
	PageObj.prototype.setEventHandler = function(){
		//지역 옵션 클릭
		$('input[name=gbn]').click(function(){
			
			//
			pageObj.resetSidosUi();
			pageObj.resetSigungusUi();
			
			//
			if('sd' === $(this).val()){
				//시도
				$('tr.sido').show();
				$('tr.sigungu').hide();
			}else{
				//시군구
				$('tr.sido').hide();
				$('tr.sigungu').show();
			}
		});
		
		
		//대시보드로 이동 클릭
		$('button.dashboard').click(function(){
			location.href = './riceDetail.do';
		});
		
		//예상 생산량 확인 클릭
		$('button.predict').click(function(){
			location.href = './ricePredictForm.do';
		});
		
		//시군구 추가
		$('button.add.sigungu').click(function(){
			//갯수 제한
			if(5 <= $('td.sigungu.list div').length){
				alert('더 이상 시군구를 추가할 수 없습니다.');
				return;
			}
			
			
			//
			let $div = $('<div class="selWrap" />');
			
			//화면에 표시
			$('td.sigungu.list').append($div);
			
			//시도 select 추가
			$div.append( PageObj.getSidoSelectHtml('', 'sido', ['sido', 'wd100'], RiceDataObj.getSidos()));			
			
			//change 이벤트 등록
			$('td.sigungu.list [name=sido]:last').change(function(){
				// 시도에 해당하는 시군구 조회
				let sidoCode = $(this).val();
				let sidoName = $(this).find('option:selected').text();
				let sigungus = RiceDataObj.getSigungus(sidoName);
				//화면에 시군구 select 표시
				let html = PageObj.getSigunguSelectHtml('', 'sigungu', ['sigungu', 'wd100'], sigungus);
				$(this).siblings('[name=sigungu]').html(html);
			});
			
			//시군구 select 추가
			$div.append('<select name="sigungu" class="sigungu"><option value="">-선택-</option></select>');
			
			//삭제 버튼 추가
			$div.append('<button type="button">삭제</button>');
			
			//삭제 click 이벤트 등록
			$div.find('button:last').click(function(){
				if(!confirm('삭제하시겠습니까?')){
					return;
				}
				
				$(this).closest('div').remove();
			});
		});
		
		$('.fromYear').change(function(){
			
		});
		
		$('.toYear').change(function(){
			
		});
		
		
		
		
		
		//비교 버튼 클릭
		$('button.cmpr.outtrn,button.cmpr.ar').click(function(){
			
			let cmprGbn = '';
			//생산량 비교
			if($(this).hasClass('outtrn')){
				cmprGbn = 'outtrn';
			}
			//면적 비교
			if($(this).hasClass('ar')){
				cmprGbn = 'ar';
			}
			
			
			//
			let arGbn = $('input[name=arGbn]:checked').val();
			
			//
			let gbn = $('input[name=gbn]:checked').val();
			
			//
			if('outtrn' === cmprGbn){
				//생산량
				if('sd' === gbn){
					//시도
					pageObj.outtrnSido(arGbn);
					
				}else{
					//시군구
					pageObj.outtrnSigungu(arGbn);
				}
			}else{
				//면적
				if('sd' === gbn){
					//시도
					pageObj.arSido(arGbn);
					
				}else{
					//시군구
					pageObj.arSigungu(arGbn);
				}
			}
			

		});
	};
	
	
	
	
	/**
	 * 데이터 테이블 표시
	 * @param xLabels
	 * @param dsLabels
	 * @param datasets
	 * @param title
	 * @param direction
	 */
	PageObj.prototype.showDataTableSido = function(xLabels, dsLabels, datasets, title, direction){
		console.log('>>.showDataTableSido', xLabels, dsLabels, datasets, title, direction);
		
		//
		let _colgroup = function(datas){
			let s = '';
			
			let diff = 100 / (datas.length+1);
			
			s += '<colgroup>';
			s += '	<col width="*"/>';
			for(let i=0; i<datas.length; i++){
				s += '<col width="'+Math.round(diff)+'%"/>';
			}
			s += '</colgroup>';
			
			//
			return s;
		};
		
		//
		let _thead = function(datas){
			//
			let s = '';
			
			//
			s += '	<thead>';
			s += '		<tr>';
			s += '			<th>년도</th>';
			for(let i=0; i<datas.length; i++){
				let d = datas[i];
				s += '		<th>'+d+'</th>';
			}
			s += '		</tr>';
			s += '	</thead>';
			
			//
			return s;
		};
		
		//
		let _tbody = function(dsLabels, datasets){
			let s = '';
			
			
			//
			s += '<tbody>';
			for(let i=0; i<dsLabels.length; i++){
				let d = dsLabels[i];
				
				s += '<tr>';
				s += '	<td>'+d+'</td>';
				let subs = datasets[i];
				for(let j=0; j<subs.length; j++){
					s += '	<td style="text-align:right !important;">'+ $.cs.addComma(subs[j]) +'</td>';
				}
				
				s += '</tr>';
			}
			s += '</tbody>';
			//
			
			//
			return s;
		};
		
		
		//
		let _tbody2 = function(xLabels, dsLabels, datasets){
			let s = '';
			
			//
			for (let i = 0; i < xLabels.length; i++) {
				let yr = xLabels[i];
				
				//
				s += '<tr>';
				s += '	<td>'+yr+'</td>';
				for(let j=0; j<datasets.length;j++){
					let d = datasets[j][i];
					
					s += '<td class="num">' + $.cs.addComma(d) + '</td>';
					
				}
				s += '</tr>';
				
			}
			
			//
			return s;
		};
		
		
		
		//
		let s = '';
		
		//
		if('세로'===direction){
			s += '<table class="grid">';
			s += _colgroup(dsLabels);
			s += _thead(dsLabels);
			s += _tbody2(xLabels, dsLabels, datasets);
			s += '</table>';
			
		}else{
			s += '<table class="grid">';
			s += _colgroup(xLabels);
			s += _thead(xLabels);
			s += _tbody(dsLabels, datasets);
			s += '</table>';
		}
	
		
		
		//
		$('div.gridWrap').html(s);
	};
	

	

	/**
	 * 데이터 테이블 표시
	 * @param datas
	 * @param arGbn 면적 구분. 신청(reqst)|확정(dcsn)
	 * @param cmprGbn 비교 구분. 생산량(outtrn)|면적(ar)
	 */
	PageObj.prototype.showDataTableSigungu = function(datas, arGbn, cmprGbn){
		console.log('>>.showDataTableSigungu', datas, arGbn, cmprGbn);
		
		//
		let _colgroup = function(datas){
			let s = '';
			
			let diff = 100 / (datas.length+2);
			
			s += '<colgroup>';
			s += '	<col width="*"/>';
			s += '	<col width="*"/>';
			for(let i=0; i<datas.length; i++){
				s += '<col width="'+Math.round(diff)+'%"/>';
			}
			s += '</colgroup>';
			
			//
			return s;
		};
		
		//
		let _thead = function(datas){
			//
			let s = '';
			
			//
			s += '	<thead>';
			s += '		<tr>';
			s += '			<th colspan="2">&nbsp;</th>';
			for(let i=0; i<datas.length; i++){
				let d = datas[i];
				s += '		<th>'+d.year+'</th>';
			}
			s += '		</tr>';
			s += '	</thead>';
			
			//
			return s;
		};
		
		//
		let _tbody = function(datas, arGbn, cmprGbn){
			let s = '';
			
			//
			let arKey='';
			if('reqst' === arGbn){
				arKey = 'reqstAr';
			}else if('dcsn' === arGbn){
				arKey = 'dcsnAr';
			}
			
			//
			let sidos = [];
			for(let i=0; i<datas[0].sigungus.length; i++){
				let d = datas[0].sigungus[i];
				sidos.push({'sidoCode' : d.sidoCode, 'sidoName':d.sidoName, 'sigunguCode':d.sigunguCode, 'sigunguName':d.sigunguName});
			}
			
			
			//
			s += '<tbody>';
			for(let i=0; i<sidos.length; i++){
				
				s += '<tr>';
				s += '	<th scope="row">' + sidos[i].sidoName + '</th>';
				s += '	<th scope="row">' + sidos[i].sigunguName + '</th>';
				for(let j=0; j<datas.length; j++){
					let d = datas[j];
					
					//
					let v=0;
					if('outtrn' === cmprGbn){
						v = Math.round((d.sigungus[i].dansu * d.sigungus[i][arKey]) / PageObj.UNIT);
					}else if('ar' === cmprGbn){
						v = Math.round(d.sigungus[i][arKey]);
					}
					
					//
					s += '	<td class="num">' + $.cs.addComma(v) + '</td>';
				}
				s += '</tr>';
			}
			s += '</tbody>';
			//
			
			//
			return s;
		};
		
	
		
		//
		let s = '';
		s += '<table class="grid">';
		s += _colgroup(datas);
		s += _thead(datas);
		s += _tbody(datas, arGbn, cmprGbn);
		s += '</table>';
		
		//
		$('div.gridWrap').html(s);
	};
	

	
	
	/**
	 * 시군구별 비교 차트 그리기
	 * @param datas 데이터 목록
	 * @param arGbn reqst|dcsn
	 * @param cmprGbn outtrn|ar
	 */
	PageObj.prototype.drawChartSigungu = function(datas, arGbn, cmprGbn){
		//년도 목록
		let _years = function(datas){
			let arr = [];
			for(let i=0; i<datas.length; i++){
				arr.push( datas[i].year);
			}

			//
			console.log('_years', arr);
			return arr;
		};

		//시군구 목록
		let _sigungus = function(datas){
			let arr=[];
			
			for(let i=0; i<datas[0].sigungus.length; i++){
				arr.push( datas[0].sigungus[i].sigunguName);
			}
			
			//
			console.log('_sigungus', arr);
			return arr;
		};

		//막대 차트용 데이터 생성
		let _barChartData = function(datas, arKey){
			let labels = _years(datas);
			
			//
			let arr=[];
			let sigungus = _sigungus(datas);
			for(let i=0; i<sigungus.length; i++){
				let sigunguName = sigungus[i];
				
				arr.push(_ds(datas, sigunguName, arKey, cmprGbn));
			}
			
			//
			return {
				labels: labels,
				datasets: arr

			};
		};

		//데이터셋의 내용 생성
		let _ds = function(datas, sigunguName, arKey, cmprGbn){
			
			//
			let arr=[];
			for(let i=0; i<datas.length; i++){
				let d = datas[i];
				
				for(let j=0; j<d.sigungus.length; j++){
					let sigunguJson = d.sigungus[j];
					if(sigunguName !== sigunguJson.sigunguName){
						continue;
					}
					
					//
					let v=0;
					if('outtrn' === cmprGbn){
						//생산량
						v = Math.round((d.sigungus[j].dansu * d.sigungus[j][arKey]) / PageObj.UNIT);
					}else{
						//면적
						v = Math.round(d.sigungus[j][arKey]);
					}
					arr.push(v);
				}
			}
			
			//
			let c1 = $.cs.randColor();
			
			//
			console.log('_ds', arr);
			return {
				label: sigunguName,
				backgroundColor: c1,
				borderColor: c1,
				borderWidth: 1,
				hoverBackgroundColor : c1,
				hoverBorderColor : c1,
				data: arr
			};
		};

		//차트 그리기
		let _drawChart = function(datas, elId, arKey, text, cmprGbn){
			//
			let ctx = document.getElementById(elId).getContext('2d');
			
			//신청 면적 차트 생성
			new Chart(ctx, {
				type : 'bar',
				data : _barChartData(datas, arKey, cmprGbn),
				options:{
					responsive: true,
					legend: {
						position: 'top',
					},
					title: {
						display: true,
						text: text
					},
					tooltips: {
						mode: 'index',
						intersect: false,
						callbacks:{
							label: function (t, d) {
								return $.cs.addComma(t.yLabel);
			                }
						}
					}
				}
			});
			
		};
		
		
		
		
		//
		let t='';
		if('outtrn' === cmprGbn){
			t = '생산량(톤)';
		}else{
			if('reqst' === arGbn){
				t = '신청면적(㎡)';
			}else{
				t = '확정면적(㎡)';
			}
		}

		//
		if('reqst' === arGbn){
			_drawChart(datas, 'chart', 'reqstAr', '년도별 시군구 ' + t, cmprGbn);
		}
		
		//
		if('dcsn' === arGbn){
			_drawChart(datas, 'chart', 'dcsnAr', '년도별 시군구 ' + t, cmprGbn);
		}
	};

	
	/**
	 * 시도별 비교 차트 그리기
	 * @param xLabels
	 * @param dsLabels
	 * @param datasets
	 * @param title
	 */
	PageObj.prototype.drawChartSido = function(xLabels, dsLabels, datasets, title){
		console.log('>>.drawChartSido', xLabels, dsLabels, datasets, title);
		
		
		//막대 차트용 데이터 생성
		let _barChartData = function(xLabels, dsLabels, datasets){
			
			//
			let arr=[];
			for(let i=0; i<dsLabels.length; i++){
				let sidoName = dsLabels[i];
				
				arr.push(_ds(i, sidoName, datasets));
			}
			
			//
			return {
				labels: xLabels,
				datasets: arr
			}
		};
		
		/**
		 * 데이터셋의 내용 생성
		 * @param datas
		 * @param sidoName
		 * @param arGbn request|dcsn
		 * @param cmprGbn outtrn|ar 
		 */
		let _ds = function(i, sidoName, datasets){
			
			let c1 = $.cs.randColor();
			
			//
//			console.log('_ds', arr);
			return {
				label: sidoName,
				backgroundColor: c1,
				borderColor: c1,
				hoverBackgroundColor : c1,
				hoverBorderColor : c1,
				borderWidth: 1,
				data: datasets[i]
			}
		};
		
		//
		$('div.chartWrap').html('<canvas id="chart" width="792" height="480"></canvas>');
		//
		let ctx = document.getElementById('chart').getContext('2d');
		
		//신청 면적 차트 생성
		new Chart(ctx, {
			type : 'bar',
			data : _barChartData(xLabels, dsLabels, datasets, title),
			options:{
				responsive: true,
				legend: {
					position: 'top',
				},
				title:{
					display : true,
					text : title
				},
				tooltips: {
					mode: 'index',
					intersect: false,
					callbacks:{
						label: function (t, d) {
							return $.cs.addComma(t.yLabel);
		                }
					}
				}
			}
		});

		//
		ctx.height = 500;
	};
	
	
	/**
	 * 모든 시도 체크 해제
	 */
	PageObj.prototype.resetSidosUi = function(){
		$('td.sido.list input[name=sido]:checked').prop('checked',false);
	};
	
	
	/**
	 * 모든 시군구 select 제거
	 */
	PageObj.prototype.resetSigungusUi = function(){
		$('td.sigungu.list div.selWrap').remove();
	};
	
	
	

	
	
	/**
	 * 년도 선후관계 확인
	 */
	PageObj.prototype.validYear = function(){
		let fromYear = $('.fromYear').val();
		let toYear = $('.toYear').val();
		
		// 년도 선후 검사
		if (parseInt(fromYear) > parseInt(toYear)){
			alert('기간의 선후 관계가 올바르지 않습니다.');
			return false;
		}
		
		//
		return true;
	};
	
	
	/**
	 * 같은 시도 존재여부 확인
	 */
	PageObj.prototype.validSido = function(){
		let sidos = [];
		
		//
		$('input[name=sido]:checked').each(function(i,item){
			let sidoCode = $(item).val();
			let sidoName = $(item).data('sido-name');
			sidos.push({'sidoCode' : sidoCode, 'sidoName' : sidoName});
		});
		
		//
		if(0 === sidos.length){
			alert('시도를 선택하시기 바랍니다.');
			return false;
		}
		
		
		//
		return true;
	};
	
	
	/**
	 * 체크된(선택된) 시군구 목록 리턴
	 */
	PageObj.prototype.getCheckedSigungus = function(){
		let arr=[];
		
		//
		$('td.sigungu.list div.selWrap').each(function(i,item){
			let sidoCode = $(item).find('[name=sido]').val();
			let sidoName = $(item).find('[name=sido] option:selected').text();
			let sigunguCode = $(item).find('[name=sigungu]').val();
			let sigunguName = $(item).find('[name=sigungu] option:selected').text();
			
			//
			arr.push({
				'sidoCode' : sidoCode,
				'sidoName' : sidoName,
				'sigunguCode' : sigunguCode,
				'sigunguName' : sigunguName
			});
		});
		
		//
		return arr;
	};
	
	/**
	 * 체크된 시도 목록 리턴
	 */
	PageObj.prototype.getCheckedSidos = function(){
		let sidos = [];
		
		//
		$('input[name=sido]:checked').each(function(i,item){
			let sidoCode = $(item).val();
			let sidoName = $(item).data('sido-name');
			sidos.push({'sidoCode' : sidoCode, 'sidoName' : sidoName});
		});
		
		
		//
		return sidos;
	};
	
	
	PageObj.prototype.validSigungu = function(){
		let sigungus=[], p={};
		$('td.sigungu.list div.selWrap').each(function(i,item){
			let sidoCode = $(item).find('select[name=sido]').val();
			let sidoName = $(item).find('select[name=sido] option:selected').text();
			let sigunguCode = $(item).find('select[name=sigungu]').val();
			let sigunguName = $(item).find('select[name=sigungu] option:selected').text();
			
			//
			sigungus.push({'sidoCode':sidoCode, 'sidoName':sidoName, 'sigunguCode':sigunguCode, 'sigunguName':sigunguName});
			p[sigunguCode] = sigunguCode;
		});
		
		//
		if(0 == sigungus.length){
			alert('시군구를 선택하시기 바랍니다.');
			return false;
		}
		
		//공백 존재 검사
		for(let i=0; i<sigungus.length; i++){
			let d = sigungus[i];
			
			if('' === d.sidoCode){
				alert('선택되지않은 시도가 존재합니다. 확인하시기 바랍니다.');
				return false;
			}
		}
		
		//같은 시군구 존재여부 검사
		if(Object.keys(p).length !== sigungus.length){
			alert('동일한 시군구가 존재합니다. 확인하시기 바랍니다.');
			return false;
		}
		
		//
		return true;
	};
	
	

	/**
	 * 생산량 시도 
	 * @param arGbn reqst|dcsn
	 */
	PageObj.prototype.outtrnSido = function(arGbn){
		//
		let b = pageObj.validYear();
		if(!b){
			return;
		}
		
		//
		b = pageObj.validSido();
		if(!b){
			return;
		}
		
		//년도
		let fromYear = parseInt($('.fromYear').val());
		let toYear = parseInt($('.toYear').val()); 
		//시도
		let sidos = pageObj.getCheckedSidos();
		//머지된 전체 데이터
		let datas = rdObj.byYearAndSido();
		
		//
		let arr=[];
		//년도
		for(let i=fromYear; i<=toYear; i++){
			let yr = i;
			
			let subdatas = RiceDataObj.getDatas(datas, {'year':yr});
			
			//시도
			for(let j=0; j<sidos.length; j++){
				let sidoJson = sidos[j];
				
				let subdatas2 = RiceDataObj.getDatas(subdatas, {'sidoName':sidoJson.sidoName})
				arr = arr.concat(subdatas2);
			}
		}
		
		//
		console.log(arr);
		
		//
		let years = RiceDataObj.distinct(arr, 'year');
		let sidoNames = RiceDataObj.distinct(arr, 'sidoName');
		let yarr=[];
		
		//
		for(let i=0; i<sidoNames.length; i++){
			let sidoName = sidoNames[i];
			
			//
			let xarr=[];
			for(let j=0; j<years.length; j++){
				let yr = years[j];
				
				//
				let subdatas = RiceDataObj.getDatas(arr, {'year':yr});
				subdatas = RiceDataObj.getDatas(subdatas, {'sidoName':sidoName});
				let outtrns = $.cs.getArr(subdatas, (RiceDataObj.REQST===arGbn?'outtrnReqstAr':'outtrnDcsnAr'));
				outtrns = RiceDataObj.divide(outtrns, RiceDataObj.UNIT);
				outtrns = RiceDataObj.round(outtrns);
				//
				xarr = xarr.concat(outtrns);
			}
			
			//
			yarr.push(xarr);
		}
		
		//차트
		pageObj.drawChartSido(years, sidoNames, yarr, '연도별 시도 생산량(톤)', '세로');
		
		//데이터 테이블
		pageObj.showDataTableSido(years, sidoNames, yarr, '연도별 시도 생산량(톤)', '세로');
	};
	
	
	
	/**
	 * 면적 시도 
	 * @param arGbn reqst|dcsn
	 */
	PageObj.prototype.arSido = function(arGbn){
		//
		let b = pageObj.validYear();
		if(!b){
			return;
		}
		
		//
		b = pageObj.validSido();
		if(!b){
			return;
		}
		
		//년도
		let fromYear = parseInt($('.fromYear').val());
		let toYear = parseInt($('.toYear').val()); 
		//시도
		let sidos = pageObj.getCheckedSidos();
		//머지된 전체 데이터
		let datas = rdObj.byYearAndSido();
		
		//
		let arr=[];
		//년도
		for(let i=fromYear; i<=toYear; i++){
			let yr = i;
			
			let subdatas = RiceDataObj.getDatas(datas, {'year':yr});
			
			//시도
			for(let j=0; j<sidos.length; j++){
				let sidoJson = sidos[j];
				
				let subdatas2 = RiceDataObj.getDatas(subdatas, {'sidoName':sidoJson.sidoName})
				arr = arr.concat(subdatas2);
			}
		}
		
		//
		console.log(arr);
		
		//
		let years = RiceDataObj.distinct(arr, 'year');
		let sidoNames = RiceDataObj.distinct(arr, 'sidoName');
		let yarr=[];
		
		//
		for(let i=0; i<sidoNames.length; i++){
			let sidoName = sidoNames[i];
			
			//
			let xarr=[];
			for(let j=0; j<years.length; j++){
				let yr = years[j];
				
				//
				let subdatas = RiceDataObj.getDatas(arr, {'year':yr});
				subdatas = RiceDataObj.getDatas(subdatas, {'sidoName':sidoName});
				let ars = $.cs.getArr(subdatas, (RiceDataObj.REQST===arGbn?'reqstAr':'dcsnAr'));
				ars = RiceDataObj.round(ars);
				//
				xarr = xarr.concat(ars);
			}
			
			//
			yarr.push(xarr);
		}
		
		//차트
		pageObj.drawChartSido(years, sidoNames, yarr, '연도별 시도 면적(㎡)');
		
		//데이터 테이블
		pageObj.showDataTableSido(years, sidoNames, yarr, '연도별 시도 면적(㎡)', '세로');
	};
	
	
	/**
	 * 생산량 시군구
	 * @param arGbn rest|dcsn
	 */
	PageObj.prototype.outtrnSigungu = function(arGbn){
		//
		let b = pageObj.validYear();
		if(!b){
			return;
		}
		
		//
		b = pageObj.validSigungu();
		if(!b){
			return;
		}
		
		//년도
		let fromYear = parseInt($('.fromYear').val());
		let toYear = parseInt($('.toYear').val()); 
		//시군구
		let sigungus = pageObj.getCheckedSigungus();
		//머지된 전체 데이터
		
		//
		let arr=[];
		//년도
		for(let i=fromYear; i<=toYear; i++){
			let yr = i;
			
			
			//시군구
			for(let j=0; j<sigungus.length; j++){
				let sigunguJson = sigungus[j];
				
				let datas = rdObj.byYearAndSigungu(sigunguJson.sidoCode, sigunguJson.sidoName);
				let subdatas = RiceDataObj.getDatas(datas, {'year':yr});				
				let subdatas2 = RiceDataObj.getDatas(subdatas, {'sigunguName':sigunguJson.sigunguName})
				arr = arr.concat(subdatas2);
			}
		}
		
		//
//		console.log(arr);
		
		//
		let years = RiceDataObj.distinct(arr, 'year');
		let sigunguNames = RiceDataObj.distinct(arr, 'sigunguName');
		let yarr=[];
		
		//
		for(let i=0; i<sigunguNames.length; i++){
			let sigunguName = sigunguNames[i];
			
			//
			let xarr=[];
			for(let j=0; j<years.length; j++){
				let yr = years[j];
				
				//
				let subdatas = RiceDataObj.getDatas(arr, {'year':yr});
				subdatas = RiceDataObj.getDatas(subdatas, {'sigunguName':sigunguName});
				let outtrns = $.cs.getArr(subdatas, (RiceDataObj.REQST===arGbn?'outtrnReqstAr':'outtrnDcsnAr'));
				outtrns = RiceDataObj.divide(outtrns, RiceDataObj.UNIT);
				outtrns = RiceDataObj.round(outtrns);
				//
				xarr = xarr.concat(outtrns);
			}
			
			//
			yarr.push(xarr);
		}
		
		//차트
		pageObj.drawChartSido(years, sigunguNames, yarr, '연도별 시군구 생산량(톤)', '세로');
		
		//데이터 테이블
		pageObj.showDataTableSido(years, sigunguNames, yarr, '연도별 시군구 생산량(톤)', '세로');
	};
	
	
	/**
	 * 면적 시군구
	 * @param arGbn rest|dcsn
	 */
	PageObj.prototype.arSigungu = function(arGbn){
		//
		let b = pageObj.validYear();
		if(!b){
			return;
		}
		
		//
		b = pageObj.validSigungu();
		if(!b){
			return;
		}
		
		//년도
		let fromYear = parseInt($('.fromYear').val());
		let toYear = parseInt($('.toYear').val()); 
		//시군구
		let sigungus = pageObj.getCheckedSigungus();
		//머지된 전체 데이터
		
		//
		let arr=[];
		//년도
		for(let i=fromYear; i<=toYear; i++){
			let yr = i;
			
			
			//시군구
			for(let j=0; j<sigungus.length; j++){
				let sigunguJson = sigungus[j];
				
				let datas = rdObj.byYearAndSigungu(sigunguJson.sidoCode, sigunguJson.sidoName);
				let subdatas = RiceDataObj.getDatas(datas, {'year':yr});				
				let subdatas2 = RiceDataObj.getDatas(subdatas, {'sigunguName':sigunguJson.sigunguName})
				arr = arr.concat(subdatas2);
			}
		}
		
		//
//		console.log(arr);
		
		//
		let years = RiceDataObj.distinct(arr, 'year');
		let sigunguNames = RiceDataObj.distinct(arr, 'sigunguName');
		let yarr=[];
		
		//
		for(let i=0; i<sigunguNames.length; i++){
			let sigunguName = sigunguNames[i];
			
			//
			let xarr=[];
			for(let j=0; j<years.length; j++){
				let yr = years[j];
				
				//
				let subdatas = RiceDataObj.getDatas(arr, {'year':yr});
				subdatas = RiceDataObj.getDatas(subdatas, {'sigunguName':sigunguName});
				let ars = $.cs.getArr(subdatas, (RiceDataObj.REQST===arGbn?'reqstAr':'dcsnAr'));
				ars = RiceDataObj.round(ars);
				//
				xarr = xarr.concat(ars);
			}
			
			//
			yarr.push(xarr);
		}
		
		//차트
		pageObj.drawChartSido(years, sigunguNames, yarr, '연도별 시군구 면적(㎡)', '세로');
		
		//데이터 테이블
		pageObj.showDataTableSido(years, sigunguNames, yarr, '연도별 시군구 면적(㎡)', '세로');
	};
	
	
	
});





//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});