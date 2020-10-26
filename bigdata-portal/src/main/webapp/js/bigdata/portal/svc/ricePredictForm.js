/**
 * 예상 생산량 예측
 */
let PageObj = (function(){
	
	
	/**
	 * sido select html 생성
	 * @param id select의 id
	 * @param name select의 name
	 * @param classes css
	 * @param datas 시도 데이터 목록
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
	 * 시군구 select html 생성
	 * @param id select's id
	 * @param name select's name
	 * @param classes css
	 * @datas 시군구 목록
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
		
		if($.cs.isNull(datas) || 0 == datas.length){
			s += '<option value="">-선택-</option>';
		}else{
			//
			for(let i=0; i<datas.length; i++){
				let d = datas[i];
				
				s += '<option value="'+d.sigunguCode+'">'+d.sigunguName+'</option>';
			}
			
		}
		
		//
		s += '</select>';
		
		
		return s;
	};
	
	/**
	 * 초기
	 */
	PageObj.prototype.init = function(){
		//
		pageObj.setEventHandler();
		
		//모든 데이터 조회
		predictObj.getDatasAsync(function(){
			//시도 목록
			let html = PageObj.getSidoSelectHtml('', 'sido', ['wd100'], predictObj.sidos);
			//화면에 추가
			$('td.sel').html(html);
			//change이벤트 등록
			$('select[name=sido]').change(function(){
				let sidoCode = $(this).val();
				let sidoName = $(this).find('option:selected').text();
				//시도에 해당하는 시군구 목록만 추출
				let sigungus = predictObj.getSigungusBySido(sidoCode, sidoName, predictObj.sigungus);
				//시군구 select html
				let s = PageObj.getSigunguSelectHtml('', 'sigungu', ['wd100'], sigungus);
				//기존 시군구 select 삭제
				$('select[name=sigungu]').remove();
				//신규 시군구 select 추가
				$('td.sel').append(s);
			});
			
			//dummy 시군구 select 추가
			$('td.sel').append('<select name="sigungu" class="wd100"><option value="">-선택-</option></select>');
		});
	};
	
	
	/**
	 * 이벤트 등록
	 */
	PageObj.prototype.setEventHandler = function(){
		//대시보드
		$('button.dashboard').click(function(){
			location.href = './riceDetail.do';
		});
		
		//추이 비교
		$('button.cmpr').click(function(){
			location.href = './riceCmprForm.do';
		});
		
		//생산량 예측
		$('button.predict').click(function(){
			
			let ar = $('input.ar').val();
			let sidoCode = $('select[name=sido]').val();
			let sidoName = $('select[name=sido] option:selected').text();
			let sigunguCode = $('select[name=sigungu]').val();
			let sigunguName = $('select[name=sigungu] option:selected').text();
			
			// 입력값 검사
			if($.cs.isEmpty(ar) || '0' === ar){
				alert('생산면적을 입력하시기 바랍니다.');
				return;
			}
			if($.cs.isEmpty(sidoCode) || $.cs.isEmpty(sigunguCode)){
				alert('지역을 선택하시기 바랍니다.');
				return;
			}
			
			//
			let resultJson = predictObj.process(ar, sidoCode, sidoName, sigunguCode, sigunguName);
			
			//
			$('input.result.outtrn').val($.cs.addComma(resultJson.outtrn1));
			$('input.result.outtrn.byar').val($.cs.addComma(resultJson.outtrn2.toFixed(3)));
		});
	};
	
	
});


/**
 * 예측
 */
let PredictObj = (function(){
	PredictObj.prototype.init = function(){
		
	};
	

	/**
	 * 서버 데이터 조회 비동기
	 */
	PredictObj.prototype.getDatasAsync = function(callbackFunction){
		$.cs.submitAjax('./getAllRiceDatas.json', [], function(res){
			console.log(res);
			
			//조회된 데이터는 모두 전역변수로 저장
			predictObj.sidos = res.sidos;
			predictObj.sigungus = res.sigungus;
			predictObj.sidoDansus = res.sidoDansus;
			predictObj.sigunguDansus = res.sigunguDansus;
			predictObj.directSbsidyReqsts = res.directSbsidyReqsts;
			
			//
			callbackFunction(res);
		});
	};
	
	
	/**
	 * 시도에 해당하는 시군구 목록만 추출
	 * @param sidoCode 시도코드
	 * @param sidoName 시도명
	 * @param sigungus 모든 시군구 목록
	 */
	PredictObj.prototype.getSigungusBySido = function(sidoCode, sidoName, sigungus){
		let arr=[];
		
		//
		for(let i=0; i<sigungus.length; i++){
			let d = sigungus[i];
			
			//
			if(sidoName === d.sidoName){
				arr.push(d);
			}
		}
		
		//
		return arr;
	};
	
	
	/**
	 * 처리
	 * @param ar
	 * @param sidoCode
	 * @param sidoName
	 * @param sigunguCode
	 * @param sigunguName 
	 */
	PredictObj.prototype.process = function(ar, sidoCode, sidoName, sigunguCode, sigunguName){
		
		/*
		 * 단수 구하기
		 * @param sigunguCode
		 * @param sigunguName
		 * @param datas 시군구별 단수 목록
		 */
		let _dansu = function(sigunguCode, sigunguName, datas){
			let dansu=0;
			
			//
			let year = (new Date()).getFullYear();
			for(let j=0; j<10; j++){
				let yr = year-j;
				
				//
				for(let i=0; i<datas.length; i++){
					let d = datas[i]._source;
					
					//
					if($.cs.isNull(d.DAN_SU) || 0 == d.DAN_SU){
						continue;
					}
					
					// 년도 비교
					if(yr !== parseInt(d.YEAR)){
						continue;
					}
					
					//
					if(sigunguName === d.CTPRVN_NM){					
						dansu = parseInt(d.DAN_SU);
					}
				}
				
				//
				if(0 < dansu){
					console.log('_dansu', sigunguName, yr, dansu);
					break;
				}
				
			}
			
			//
			return dansu;
		};
		
		
		console.log('>>PredictObj.process', ar, sidoName, sigunguName);
		
		//
		let resultJson = {'outtrn1':0, 'outtrn2':0};
		
		// 계산
		let dansu = _dansu(sigunguCode, sigunguName, predictObj.sigunguDansus);
		resultJson.outtrn1 = ((dansu * ar) / 1000);	//단수는 10a로 계산되어 있음. 해서 m2로 변환하기 위해 /1000함??? 
		resultJson.outtrn2 = resultJson.outtrn1 / ar;
		
		
		//
		console.log('<<PredictObj.process', resultJson);
		return resultJson;
	};
});

//
let pageObj = new PageObj();
let predictObj = new PredictObj();
$(document).ready(function(){
	pageObj.init();
	predictObj.init();
});