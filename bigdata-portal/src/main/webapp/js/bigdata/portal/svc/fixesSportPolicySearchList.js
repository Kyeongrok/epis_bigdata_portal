

/**
 * 맞춤 지원 정책 
 * @dendency similrAtmnentObj.js fixesSportPolicyDetailObj.js
 */
let PageObj = (function(){
	
	//전체
	PageObj.ALL = 'all';
	//농업인
	PageObj.FRMER = 'frmer';
	//법인
	PageObj.CPR = 'cpr';
	//비농업인
	PageObj.NOT_FRMER = 'notFrmer';
	//처리중
	PageObj.MESSAGE_LOADING = '처리중...';
	
	/**
	 * 
	 */
	PageObj.prototype.init = function(){
		console.log('>>.init');
		
		//
		pageObj.setEventHandler();
		
		//귀농 화면에서 호출한거면...
		let searchSportRelmCode = $('[name=searchSportRelmCode]').val();
		if(!$.cs.isEmpty(searchSportRelmCode)){
			$('.frmer[name=sportRelmCode]').removeClass('checked')
				.prop('checked',false);
			
			//
			$('.frmer[name=sportRelmCode]').each(function(i,item){
				if(searchSportRelmCode === $(item).val()){
					$(item).addClass('checked')
						.prop('checked',true);
				}
			});
		}
		
		let searchGbn = $('[name=searchGbn]').val();
		if($.cs.isEmpty(searchGbn)){
			$('button.tablinks[data-gbn=all]').click();
			
		}else{
			//gbn에 의한 탭 auto 클릭
			$('button.tablinks[data-gbn="'+searchGbn+'"]').click();
		}
		
		//
		$('#sub_content').on('loading.start',function(){
			//아무것도 하지 않음
		});
		
		
		//
		console.log('<<.init');
	};
	
	
	/**
	 * 이벤트 핸들러 등록
	 */
	PageObj.prototype.setEventHandler = function(){
		
		
		/**
		 * 구분 클릭
		 */
		$('button.tablinks').click(function(){
			//클릭된 버튼만 active
			$('button.tablinks').removeClass('active');
			$(this).addClass('active');
			
			//
			let gbn = $(this).data('gbn');
			
			//gbn에 해당하는 content만 show
			$('div.tabcontent').hide();
			$('div.tabcontent[data-gbn="'+gbn+'"]').show();
			
			//
			if(PageObj.ALL === gbn){
				$('.result_area').hide();
			}else{
				$('.result_area').show();
			}
			
			//검색 
			pageObj.getDatasAsync();			
		});
		
		
		//필터 조건 slide up/down
		$('.widget_inner li a').click(function () {
			$(this).siblings().toggle('fast');
		});
		
		
		//요약정보 토글
		$('.result_area a').click(function(){
			$(this).siblings().toggle('fast');
		});
		
		
		
		
		//////////////////////////////////////////////////////
		
		//전체 - 검색어 엔터
		$.cs.bindEnterKey($('.all[name=searchKeyword]'), function($item,e){
			let v = $item.val();
			
			$('.frmer[name=searchKeyword]').val(v);
			$('.cpr[name=searchKeyword]').val(v);
			$('.notFrmer[name=searchKeyword]').val(v);
			
			//
			pageObj.getDatasAsync();
		});
		
		//전체 - 검색 버튼 클릭
		$('.all.btnSearch').click(function(){
			// 데이터 조회
			pageObj.getDatasAsync();
		});
		
		//전체 - 지원영역 변경
		$('.all[name=sportRelmCode]').change(function(){
			$('.all[name=sportRelmCode]').removeClass('checked').prop('checked',false);
			$(this).addClass('chekced').prop('checked',true);
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		/////////////////////////////////////////////////////
		
		
		//농업인 - 검색어 엔터
		$.cs.bindEnterKey($('div.tabcontent.frmer [name=searchKeyword]'), function(){
			let v = $('div.tabcontent.frmer [name=searchKeyword]').val();
			
			$('.all[name=searchKeyword]').val(v);
			$('.cpr[name=searchKeyword]').val(v);
			$('.notFrmer[name=searchKeyword]').val(v);
			
			//
			pageObj.getDatasAsync();
		});
		
		//농업인 - 검색 버튼 클릭
		$('div.tabcontent.frmer .btnSearch').click(function(){
			// 데이터 조회
			pageObj.getDatasAsync();
		});
		
		
		//농업인 - 지원영역 클릭
		$('div.tabcontent.frmer [name=sportRelmCode]').click(function(){
			$('div.tabcontent.frmer [name=sportRelmCode]').removeClass('checked').prop('checked',false);
			$(this).addClass('checked').prop('checked',true);
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		//농업인 - 성별 클릭
		$('div.tabcontent.frmer [name=sexdstn]').click(function(){
			$('div.tabcontent.frmer [name=sexdstn]').removeClass('checked').prop('checked',false);
			$(this).addClass('checked').prop('checked',true);
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//농업인 - 나이 엔터
		$.cs.bindEnterKey($('div.tabcontent.frmer [name=age]'), function(){
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//농업인 - 지원 지역 - 시도 변경
		$('.frmer[name=sidoCode]').change(function(){
			let sidoCode = $(this).val();
			
			//
			let sigunguCodes = pageObj.getSigunguCodes(sidoCode);
			
			//
			let s = '<option value="">전체</option>';
			for (let i = 0; i < sigunguCodes.length; i++) {
				let item = sigunguCodes[i];
				s += '<option value="'+item.name+'">'+item.name+'</option>';
			}

			$('.frmer[name=sigunguCode]').html(s);
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//농업인 - 지원 지역 - 시군구 변경
		$('.frmer[name=sigunguCode]').change(function(){
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		
		
		//농업인 - 거주지 엔터
//		$.cs.bindEnterKey( $('div.tabcontent.frmer [name=residence]'), function(){
//			// 데이터 필터링
//			pageObj.filterDatas();
//		});
		
		
		//농업인 - 영농경력 엔터
		$.cs.bindEnterKey($('div.tabcontent.frmer [name=farmCareer]'), function(){
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		//농업인 - 재배품목군 변경
		$('.frmer[name=ctvtPrdlstSetCode]').change(function(){
			let ctvtPrdlstSetCode = $(this).val();
			
			
			//재배품목 코드 목록을 화면에 표시
			let showCtvtPrdlstCodes = function(arr){
				let s = '<option value="">전체</option>';
				
				//
				for (let i = 0; i < arr.length; i++) {
					let item = arr[i];
					s += '<option value="'+item.code+'">'+item.name+'</option>';
				}
				
				//
				$('.frmer[name=ctvtPrdlstCode]').html(s);				
			};
			
			//
			let ctvtPrdlstCodes = pageObj.getPrdlstCodes(ctvtPrdlstSetCode);
			
			//
			showCtvtPrdlstCodes(ctvtPrdlstCodes);
			
			
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//농업인 - 재배품목 변경
		$('.frmer[name=ctvtPrdlstCode]').change(function(){
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//농업인 - 면적(두수) 엔터
		$.cs.bindEnterKey($('.frmer[name=arOrCo]'), function(){
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//농업인 - 재배유형 변경
		$('.frmer[name=ctvtTyCode]').change(function(){
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		
		
		
		///////////////////////////////////////
		
		//법인 - 검색어 엔터
		$.cs.bindEnterKey($('div.tabcontent.cpr [name=searchKeyword]'), function(){
			let v = $('div.tabcontent.cpr [name=searchKeyword]').val();
			
			$('.all[name=searchKeyword]').val(v);
			$('.frmer[name=searchKeyword]').val(v);
			$('.notFrmer[name=searchKeyword]').val(v);
			
			//
			pageObj.getDatasAsync();
		});
		
		
		//법인 - 검색 버튼 클릭
		$('div.tabcontent.cpr .btnSearch').click(function(){
			// 데이터 조회
			pageObj.getDatasAsync();
		});
		
		//법인 - 지원영역 클릭
		$('div.tabcontent.cpr [name=sportRelmCode]').click(function(){
			$('div.tabcontent.cpr [name=sportRelmCode]').removeClass('checked').prop('checked',false);
			$(this).addClass('chekced').prop('checked',true);
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		//법인 - 지원유형 클릭
		$('div.tabcontent.cpr [name=sportTyCode]').click(function(){
			$('div.tabcontent.cpr [name=sportTyCode]').removeClass('checked').prop('checked',false);
			$(this).addClass('chekced').prop('checked',true);
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//법인 - 지원주체 클릭
		$('div.tabcontent.cpr [name=sportMbyCode]').click(function(){
			$('div.tabcontent.cpr [name=sportMbyCode]').removeClass('checked').prop('checked',false);
			$(this).addClass('chekced').prop('checked',true);
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//법인 - 설립년도 엔터
		$.cs.bindEnterKey($('div.tabcontent.cpr [name=fondYear]'), function(){
			// 데이터 필터링
			pageObj.filterDatas();
			
		});
		
		//법인 - 자본금 엔터
		$.cs.bindEnterKey($('div.tabcontent.cpr [name=capl]'), function(){
			// 데이터 필터링
			pageObj.filterDatas();
			
		});
		
		//법인 - 출자자 수 엔터
		$.cs.bindEnterKey($('div.tabcontent.cpr [name=investorCo]'), function(){
			// 데이터 필터링
			pageObj.filterDatas();
			
		});
		
		
			
		
		
		//법인 - 주 취급품목군 변경
		$('.cpr[name=ctvtPrdlstSetCode]').change(function(){
			let ctvtPrdlstSetCode = $(this).val();
			
			
			//재배품목 코드 목록을 화면에 표시
			let showCtvtPrdlstCodes = function(arr){
				let s = '<option value="">전체</option>';
				
				//
				for (let i = 0; i < arr.length; i++) {
					let item = arr[i];
					s += '<option value="'+item.code+'">'+item.name+'</option>';
				}
				
				//
				$('.cpr[name=ctvtPrdlstCode]').html(s);				
			};
			
			//
			let ctvtPrdlstCodes = pageObj.getPrdlstCodes(ctvtPrdlstSetCode);
			//
			showCtvtPrdlstCodes(ctvtPrdlstCodes);
			
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		
		//법인 - 주 취급품목 변경
		$('.cpr[name=ctvtPrdlstCode]').change(function(){
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//취급규모 엔터
		$.cs.bindEnterKey($('div.tabcontent.cpr [name=trtmntScale]'), function(){
			// 데이터 필터링
			pageObj.filterDatas();
			
		});
		
		//매출액
		$.cs.bindEnterKey($('div.tabcontent.cpr [name=selngAmount]'), function(){
			// 데이터 필터링
			pageObj.filterDatas();
			
		});
		
		
		
		
		/////////////////////////////////////////
		
		//비농업인 - 검색어 엔터
		$.cs.bindEnterKey($('div.tabcontent.notFrmer [name=searchKeyword]'), function(){
			let v = $('div.tabcontent.notFrmer [name=searchKeyword]').val();
			
			$('.all[name=searchKeyword]').val(v);
			$('.frmer[name=searchKeyword]').val(v);
			$('.cpr[name=searchKeyword]').val(v);
			
			//
			pageObj.getDatasAsync();
		});
		
		//비농업인 - 검색 버튼 클릭
		$('div.tabcontent.notFrmer .btnSearch').click(function(){
			// 데이터 조회
			pageObj.getDatasAsync();
		});
		
		
		//비농업인 - 성별 클릭
		$('div.tabcontent.notFrmer [name=sexdstn]').click(function(){
			//
			$('div.tabcontent.notFrmer [name=sexdstn]').prop('checked',false).removeClass('checked');
			$(this).prop('checked',true).addClass('checked');
			
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//비농업인 - 나이 엔터
		$.cs.bindEnterKey( $('div.tabcontent.notFrmer [name=age]'), function(){
			$.cs.log($('div.tabcontent.notFrmer [name=age]').val());
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
		//비농업인 - 지원지역 - 시도
		$('.notFrmer[name=sidoCode]').change(function(){
			let sidoCode = $(this).val();
			
			//
			let sigunguCodes = pageObj.getSigunguCodes(sidoCode);

			//
			let s = '<option value="">전체</option>';
			for (let i = 0; i < sigunguCodes.length; i++) {
				let item = sigunguCodes[i];
				s += '<option value="'+item.name+'">'+item.name+'</option>';
			}
			$('.notFrmer[name=sigunguCode]').html(s);
			
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//비농업인 - 지원지역 - 시군구
		$('.notFrmer[name=sigunguCode]').change(function(){
			
			//데이터 필터링
			pageObj.filterDatas();
		});
		
		
		//비농업인 - 거주지 엔터
//		$.cs.bindEnterKey( $('div.tabcontent.notFrmer [name=residence]'), function(){
//			//데이터 필터링
//			pageObj.filterDatas();
//		});

				
		
		//비농업인 - 농업계학교 출신여부 change
		$('div.tabcontent.notFrmer [name=farmngSchulOrginAt]').change(function(){
			// 데이터 필터링
			pageObj.filterDatas();
		});
		
	};
	
	
	
	
	////////////////////////////////////////////////
	
	
	
	/**
	 * 체크된(선택된) 구분값(frmer, cpr, notFrmer) 조회
	 */
	PageObj.prototype.getCheckedGbn = function(){
		let gbn = '';
		
		$('button.tablinks').each(function(i,item){
			if($(item).hasClass('active')){
				gbn = $(item).data('gbn');
			}
		});
		
		//
		return gbn;
	};
	
	
	/**
	 * 필터링된 데이터 조회
	 * 구분별 옵션에 따른 데이터 필터링
	 * 페이징 표시
	 * 지원영역별 탭 표시
	 * 데이터 목록 화면에 표시
	 */
	PageObj.prototype.filterDatas = function(currentPageNo){
		let _datas = dataObj.responseData.datas;
		let _currentPageNo = $.cs.isNull(currentPageNo) ? 1 : currentPageNo;
		
		//
		let filteredDatas = [];
		//
		let gbn = pageObj.getCheckedGbn();
		
		//전체
		if(PageObj.ALL === gbn){
			filteredDatas = pageObj.getFilteredDatasOfAll(_datas);
		}
		
		//농업인
		if(PageObj.FRMER === gbn){
			filteredDatas = pageObj.getFilteredDatasOfFrmer(_datas);
		}
		
		//법인
		if(PageObj.CPR === gbn){
			filteredDatas = pageObj.getFilteredDatasOfCpr(_datas);
		}
		
		//비농업인
		if(PageObj.NOT_FRMER === gbn){
			filteredDatas = pageObj.getFilteredDatasOfNotFrmer(_datas);
		}
		
		// 페이징
		let paginateJson = pageObj.showPager(_currentPageNo, filteredDatas.length);
		
		//지원영역별 탭 표시
		pageObj.processCountsBySportArea(filteredDatas);
		
		//페이징 번호로 필터링
		let fromToDatas = pageObj.getFromToDatas(filteredDatas, paginateJson.startIndex, paginateJson.endIndex);
		
		// 화면에 표시
		pageObj.showDatas(fromToDatas);
		
		//유사 경영체 관련
		pageObj.similrAtmnent(filteredDatas, $.cs.isNull(currentPageNo));
		
		//상세
		$('.table_area.detail,.table_area02.detail').hide();
		//목록
		$('.table_area.list').show();
		
		//상세 조회일때는 quick hide함
		$('.widget_area').show();		
	};
	
	
	///////////////////////////////////////////////////////
	
	/**
	 * 데이터 필터링 - 전체
	 */
	PageObj.prototype.getFilteredDatasOfAll = function(datas){
		let _datas = datas;
		
		//
		let $div = $('div.tabcontent.' + PageObj.ALL);
		
		//지원영역
		let sportRelmCode = $div.find('[name=sportRelmCode]:checked').val();
		if(!$.cs.isEmpty(sportRelmCode)){
			//
			_datas = pageObj.getFilteredDatasOfAllBySportRelmCode(sportRelmCode, _datas);
		}
		
		//
		console.log(PageObj.FRMER, 'before:', datas.length, 'after:', _datas.length);
		return _datas;
	};
	
	
	
	
	
	////////////////////////////////////////////////////////////
	
	/**
	 * 데이터 필터링 - 농업인
	 */
	PageObj.prototype.getFilteredDatasOfFrmer = function(datas){
		let _datas = datas;
		
		//
		let $div = $('div.tabcontent.' + PageObj.FRMER);
		
		//지원영역
		let sportRelmCode = $div.find('[name=sportRelmCode]:checked').val();
		if(!$.cs.isEmpty(sportRelmCode)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerBySportRelmCode(sportRelmCode, _datas);
		}
		
		//성별
		let sexdstn = $div.find('[name=sexdstn]:checked').val();
		if(!$.cs.isEmpty(sexdstn)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerBySexdstn(sexdstn, _datas);
		}
		
		//나이
		let age = $div.find('[name=age]').val();
		if(!$.cs.isEmpty(age)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerByAge(age, _datas);
		}
		
		//지원지역 - 시도
		let sidoCode = $div.find('[name=sidoCode]').val();
		if(!$.cs.isEmpty(sidoCode)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerBySidoCode(sidoCode, _datas);
		}
		
		//지원지역 - 시군구
		let sigunguCode = $div.find('[name=sigunguCode]').val();
		if(!$.cs.isEmpty(sigunguCode)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerBySigunguCode(sigunguCode, _datas);
		}
		
		//거주지
//		let residence = $div.find('[name=residence]').val();
//		if(!$.cs.isEmpty(residence)){
//			//
//			_datas = pageObj.getFilteredDatasOfFrmerByResidence(residence, _datas);
//		}
		
		
		//영농경력
		let farmCareer = $div.find('[name=farmCareer]').val();
		if(!$.cs.isEmpty(farmCareer)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerByFarmCareer(farmCareer, _datas);
		}
		
		//재배품목군
		let ctvtPrdlstSetCode = $div.find('[name=ctvtPrdlstSetCode]').val();
		if(!$.cs.isEmpty(ctvtPrdlstSetCode)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerByCtvtPrdlstSetCode(ctvtPrdlstSetCode, _datas);
		}
		
		//재배품목
		let ctvtPrdlstCode = $div.find('[name=ctvtPrdlstCode]').val();
		if(!$.cs.isEmpty(ctvtPrdlstCode)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerByCtvtPrdlstCode(ctvtPrdlstCode, _datas);
		}
		
		//면적(두수)
		let arOrCo = $div.find('[name=arOrCo]').val();
		if(!$.cs.isEmpty(arOrCo)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerByArOrCo(arOrCo, _datas);
		}
		
		//재배유형
		let ctvtTyCode = $div.find('[name=ctvtTyCode]').val();
		if(!$.cs.isEmpty(ctvtTyCode)){
			//
			_datas = pageObj.getFilteredDatasOfFrmerByCtvtTyCode(ctvtTyCode, _datas);
		}
		
		//
		console.log(PageObj.FRMER, 'before:', datas.length, 'after:', _datas.length);
		return _datas;
	};
	
	/**
	 * 데이터 필터링 - 농업인 - 지원지역 - 시도
	 * @param sidoCode 시도 코드
	 * @param 데이터 목록
	 */
	PageObj.prototype.getFilteredDatasOfFrmerBySidoCode = function(sidoCode, datas){
		let arr = [];
		let ks = ['SIDO', 'SIDO_CODE'];
		
		//
		for (let j = 0; j < datas.length; j++) {
			let item = datas[j];
			let d = item._source;
			
			//
			let b = false;
			for (let i = 0; i < ks.length; i++) {
				let k = ks[i];
				if(!$.cs.isNull(d[k]) && sidoCode == d[k]){
					arr.push(item);
				}
			}
		}
	
		
		//
		console.log('BySidoCode', sidoCode, 'before', datas.length, 'after', arr.length);
		return arr;
	}
	
	/**
	 * 데이터 필터링 - 농업인 - 지원지역 - 시군구
	 * @param sigunguCode 시군구 코드
	 * @param datas 데이터 목록
	 */
	PageObj.prototype.getFilteredDatasOfFrmerBySigunguCode = function(sigunguCode, datas){
		let arr = 	[];
		let ks = ['SIGUN', 'SIGUN_CODE'];
		
		//
		for (let j = 0; j < datas.length; j++) {
			let item = datas[j];
			let d = item._source;
			
			//	
			for (let i = 0; i < ks.length; i++) {
				let k = ks[i];
				if(!$.cs.isNull(d[k]) && sigunguCode == d[k]){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('BySigunguCode', sigunguCode, 'before', datas.length, 'after', arr.length);
		return arr;
	}
	
	/**
	 * 데이터 필터링 - 농업인 - 거주지
	 */
	PageObj.prototype.getFilteredDatasOfFrmerByResidence = function(residence, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('N' != d.FMER_RESIDE_AREA){
				if(-1 != d.FMER_RESIDE_AREA.indexOf(residence)){
					arr.push(item);
				}
			}
		}
	
		
		//
		console.log('residence',residence, 'before',datas.length, 'after', arr.length);
		return arr;
	
		
	};
	
	
	/**
	 * 데이터 필터링 - 농업인 - 면적(두수)
	 * @param arOrCo 면적(두수)
	 * @param datas 데이터 목록
	 */
	PageObj.prototype.getFilteredDatasOfFrmerByArOrCo = function(arOrCo, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			if('Y' === d.FMER_CTVT_AR){
				if(d.FMER_CTVT_AR_MIN <= arOrCo && arOrCo < d.FMER_CTVT_AR_MAX){
					arr.push(item);
				}
			}
		}
	
		
		
		//
		console.log('byArOrCo',arOrCo, 'before',datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 농업인 - 재배유형
	 * @param ctvtTyCode 재배 유형 코드
	 * @param datas 데이터 목록
	 */
	PageObj.prototype.getFilteredDatasOfFrmerByCtvtTyCode = function(ctvtTyCode, datas){
		let arr = [];
		let ks = ["FMER_TYPE_CTVT", 'FMER_TYPE_CTVT_CODE'];
		
		//
		for (let j = 0; j < datas.length; j++) {
			let item = datas[j];
			let d = item._source;
			
			//
			for (let i = 0; i < ks.length; i++) {
				let k = ks[i];
				//
				if($.cs.isNull(d[k]) || 'N' === d[k]){
					continue;
				}
				
				//
				if(-1 != d[k].indexOf(ctvtTyCode)){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('ctvtTyCode',ctvtTyCode, 'before',datas.length, 'after', arr.length);
		return arr;
	
		
	};
	
	
	/**
	 * 데이터 필터링 - 농업인 - 재배품목
	 */
	PageObj.prototype.getFilteredDatasOfFrmerByCtvtPrdlstCode = function(ctvtPrdlstCode, datas){
		let arr = [];
		let k = 'FMER_CTVT_PRDLST_CODE';
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			if($.cs.isNull(d[k])){
				continue;
			}
			
			//
			if(-1 != d[k].indexOf(ctvtPrdlstCode)){
				arr.push(item);
			}
		}
	
		
		//
		console.log('ctvtPrdlstCode', ctvtPrdlstCode, 'before',datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 농업인 - 재배품목군
	 */
	PageObj.prototype.getFilteredDatasOfFrmerByCtvtPrdlstSetCode = function(ctvtPrdlstSetCode, datas){
		let arr = [];
		let k = 'FMER_CTVT_PRDLST_GRP_CODE';
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if($.cs.isNull(d[k])){
				continue;
			}
			
			if(-1 != d[k].indexOf(ctvtPrdlstSetCode)){
				arr.push(item);
			}
		}
	
		
		//
		console.log('ctvtPrdlstSetCode', ctvtPrdlstSetCode, 'before',datas.length, 'after', arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 농업인 - 영농경력
	 */
	PageObj.prototype.getFilteredDatasOfFrmerByFarmCareer = function(farmCareer, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			if('Y' === d.FMER_MNT_PD){
				if(d.FMER_MNT_PD_MIN <= farmCareer && farmCareer < d.FMER_MNT_PD_MAX){
					arr.push(item);
				}
			}
		}
		
	
		
		//
		console.log('farmCareer',farmCareer, 'before',datas.length, 'after', arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 농업인 - 시군구
	 */
	PageObj.prototype.getFilteredDatasOfFrmerBySigunguCode = function(sigunguCode, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if(sigunguCode === d.FMER_RESIDE_AREA){
				arr.push(item);
			}
		}
	
		
		//
		console.log('sigunguCode',sigunguCode, 'before',datas.length, 'after', arr.length);
		return arr;
	};
	
	
	
	/**
	 * 데이터 필터링 - 농업인 - 나이
	 */
	PageObj.prototype.getFilteredDatasOfFrmerByAge = function(age, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.FMER_AGE){
				if(d.FMER_AGE_MIN <= age && age < d.FMER_AGE_MAX){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('age',age, 'before',datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 농업인 - 성별
	 */
	PageObj.prototype.getFilteredDatasOfFrmerBySexdstn = function(sexdstn, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.FMER_SEX){
				if('M' === sexdstn && 'Y' === d.FMER_SEX_M){
					arr.push(item);
				}
				if('F' === sexdstn && 'Y' === d.FMER_SEX_F){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('sexdstn', sexdstn, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 농업인 - 지원영역
	 */
	PageObj.prototype.getFilteredDatasOfFrmerBySportRelmCode = function(sportRelmCode, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.FMER_REQST){
				if(sportRelmCode === d.SPORT_AREA){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('sportRelmCode', sportRelmCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	//////////////////////////////////////////////////////
	

	/**
	 * 데이터 필터링 - 전체 - 지원영역
	 */
	PageObj.prototype.getFilteredDatasOfAllBySportRelmCode = function(sportRelmCode, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if(sportRelmCode === d.SPORT_AREA){
				arr.push(item);
			}
		}
		
		//
		console.log('sportRelmCode', sportRelmCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	
	///////////////////////////////////////////////////////////
	
	/**
	 *  법인
	 */
	PageObj.prototype.getFilteredDatasOfCpr = function(datas){
		let _datas = datas;
		
		//
		let $div = $('div.tabcontent.' + PageObj.CPR);
		
		//지원영역
		let sportRelmCode = $div.find('[name=sportRelmCode]:checked').val();
		if(!$.cs.isEmpty(sportRelmCode)){
			//
			_datas = pageObj.getFilteredDatasOfCprBySportRelmCode(sportRelmCode, _datas);
		}
		
		//지원유형
		let sportTyCode = $div.find('[name=sportTyCode]:checked').val();
		if(!$.cs.isEmpty(sportTyCode)){
			//
			_datas = pageObj.getFilteredDatasOfCprBySportTyCode(sportTyCode, _datas);
		}
		
		//지원주체
		let sportMbyCode = $div.find('[name=sportMbyCode]:checked').val();
		if(!$.cs.isEmpty(sportMbyCode)){
			//
			_datas = pageObj.getFilteredDatasOfCprBySportMbyCode(sportMbyCode, _datas);
		}
		
		//설립년도
		let fondYear = $div.find('[name=fondYear]').val();
		if(!$.cs.isEmpty(fondYear)){
			//
			_datas = pageObj.getFilteredDatasOfCprByFondYear(fondYear, _datas);
		}
		
		//자본금
		let capl = $div.find('[name=capl]').val();
		if(!$.cs.isEmpty(capl)){
			//
			_datas = pageObj.getFilteredDatasOfCprByCapl(capl, _datas);
		}
		
		//출자자수
		let investorCo = $div.find('[name=investorCo]').val();
		if(!$.cs.isEmpty(investorCo)){
			//
			_datas = pageObj.getFilteredDatasOfCprByInvestorCo(investorCo, _datas);
		}
		
		//소재지 - 시도
		let sidoCode = $div.find('[name=sidoCode]').val();
		if(!$.cs.isEmpty(sidoCode)){
			//처리하지 않음
		}
		
		//소재지 - 시군구
		let sigunguCode = $div.find('[name=sigunguCode]').val();
		if(!$.cs.isEmpty(sigunguCode)){
			//처리하지 않음
		}
		
		//주 취급품목군
		let ctvtPrdlstSetCode = $div.find('[name=ctvtPrdlstSetCode]').val();
		if(!$.cs.isEmpty(ctvtPrdlstSetCode)){
			//
			_datas = pageObj.getFilteredDatasOfCprByCtvtPrdlstSetCode(ctvtPrdlstSetCode, _datas);
		}
		
		//주 취급품목
		let ctvtPrdlstCode = $div.find('[name=ctvtPrdlstCode]').val();
		if(!$.cs.isEmpty(ctvtPrdlstCode)){
			//
			_datas = pageObj.getFilteredDatasOfCprByCtvtPrdlstCode(ctvtPrdlstCode, _datas);
		}
		
		
		//취급규모 - (판매규모로 계산)
		let trtmntScale = $div.find('[name=trtmntScale]').val();
		if(!$.cs.isEmpty(trtmntScale)){
			//
			_datas = pageObj.getFilteredDatasOfCprByTrtmntScale(trtmntScale, _datas);
		}
		
		//매출액
		let selngAmount = $div.find('[name=selngAmount]').val();
		if(!$.cs.isEmpty(selngAmount)){
			//
			_datas = pageObj.getFilteredDatasOfCprBySelngAmount(selngAmount, _datas);
		}
		
			
		//
		console.log(PageObj.CPR, 'before:', datas.length, 'after:', _datas.length);
		return _datas;
		
	};
	
	
	
	/**
	 * 데이터 필터링 - 법인 - 매출액
	 */
	PageObj.prototype.getFilteredDatasOfCprBySelngAmount = function(selngAmount, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.CO_SALE_AMNT){
				if(d.CO_SALE_AMNT_MIN <= selngAmount && selngAmount < d.CO_SALE_AMNT_MAX){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('selngAmount', selngAmount, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 법인 - 취급 규모 (판대규모로 계산)
	 */
	PageObj.prototype.getFilteredDatasOfCprByTrtmntScale = function(trtmntScale, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.CO_SALE_SCL){
				if(d.CO_SALE_SCL_MIN <= trtmntScale && trtmntScale < d.CO_SALE_SCL_MAX){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('trtmntScale', trtmntScale, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 법인 - 주 취급품목
	 */
	PageObj.prototype.getFilteredDatasOfCprByCtvtPrdlstCode = function(ctvtPrdlstCode, datas){
		let arr =[];
		let k = 'CO_HAND_CTVT_CODE';
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if($.cs.isNull(d[k])){
				continue;
			}
			
			//
			if(-1 != d[k].indexOf(ctvtPrdlstCode)){
				arr.push(item);
			}
		}
		
		
		//
		console.log('ctvtPrdlstCode', ctvtPrdlstCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 법인 - 주 취급품목군
	 */
	PageObj.prototype.getFilteredDatasOfCprByCtvtPrdlstSetCode = function(ctvtPrdlstSetCode, datas){
		let arr =[];
		let k = 'CO_HAND_CTVT_GRP_CODE';
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if($.cs.isNull(d[k])){
				continue;
			}
			
			//
			if(-1 != d[k].indexOf(ctvtPrdlstSetCode)){
				arr.push(item);
			}
		}
		
		
		//
		console.log('ctvtPrdlstSetCode', ctvtPrdlstSetCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 법인 - 출자자 수
	 */
	PageObj.prototype.getFilteredDatasOfCprByInvestorCo = function(investorCo, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.CO_INVST_NUM){
				if(d.CO_INVST_MIN <= investorCo && investorCo < d.CO_INVST_MAX){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('investorCo', investorCo, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 법인 - 자본금
	 */
	PageObj.prototype.getFilteredDatasOfCprByCapl = function(capl, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.CO_CAPITAL){
				if(d.CO_CAPITAL_MIN <= capl && capl < d.CO_CAPITAL_MAX){
					arr.push(item);
				}
			}
		}
		
		//
		console.log('capl', capl, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 법인 - 설립년도
	 */
	PageObj.prototype.getFilteredDatasOfCprByFondYear = function(fondYear, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.CO_OPRT_PD){
				if(d.CO_OPRT_PD_MIN <= fondYear && fondYear < d.CO_OPRT_PD_MAX){
					arr.push(item);
				}
			}
		}
	
		
		//
		console.log('fondYear', fondYear, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 법인 - 지원주체
	 * TODO 공통 함수로 만들기
	 */
	PageObj.prototype.getFilteredDatasOfCprBySportMbyCode = function(sportMbyCode, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('중앙정부' === sportMbyCode && '지자체' != d.SPORT_SUB){
				arr.push(item);
			}
			if('지방정부' === sportMbyCode && '지자체' === d.SPORT_SUB){
				arr.push(item);
			}
		}
		
		//
		console.log('sportMbyCode', sportMbyCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 법인 - 지원유형
	 * TODO 공통 함수로 만들기
	 */
	PageObj.prototype.getFilteredDatasOfCprBySportTyCode = function(sportTyCode, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if(sportTyCode === d.SPORT_STLE){
				arr.push(item);
			}
		}
		
		//
		console.log('sportTyCode', sportTyCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 법인 - 지원영역
	 * TODO 공통 함수로 만들기
	 */
	PageObj.prototype.getFilteredDatasOfCprBySportRelmCode = function(sportRelmCode, datas){
		let arr =[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if(sportRelmCode === d.SPORT_AREA){
				arr.push(item);
			}
		}
		
		//
		console.log('sportRelmCode', sportRelmCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	
	
	
	/////////////////////////////////////
	
	
	/**
	 * 화면에 목록 표시
	 */
	PageObj.prototype.showDatas = function(datas){
		console.log('>>.showDatas', datas.length);
		
		//지원 대상 문자열
		let getSportTargetString = function(gbn, item){
			let arr = [];
			
			if('Y' === item.FMER_REQST){
				arr.push('농업인');
			}
			if('Y' === item.CO_REQST){
				arr.push('법인');
			}
			if('Y' === item.NM_REQST){
				arr.push('비농업인');
			}
			
			//
			return arr.join(',');
		};
		
		//지원 금액 문자열
		let getSportAmountString = function(gbn, item){
			let keyMin='', keyMax='';
			
			//
			if(PageObj.FRMER === gbn){
				keyMin = 'FMER_SPORT_AMNT_MIN';
				keyMax = 'FMER_SPORT_AMNT_MAX';
			}else if(PageObj.CPR === gbn){
				keyMin = 'CO_SPORT_AMNT_MIN';
				keyMax = 'CO_SPORT_AMNT_MAX';
			}else if(PageObj.NOT_FRMER === gbn){
				keyMin = 'NM_SPORT_AMNT_MIN';
				keyMax = 'NM_SPORT_AMNT_MAX';
			}				
			
			//
			let s = '';
			s += (isNaN(item[keyMin]) ? '' : item[keyMin]);
			s += ' ~ ';
			s += (isNaN(item[keyMax]) ? '' : item[keyMax]);
			
			//~만 존재하면...
			return (3 == s.length ? '' : s);
		};
		
		let s = '';
		let gbn = pageObj.getCheckedGbn();
		
		//
		for (let i = 0; i < datas.length; i++) {
			let x = datas[i];
			let item = x._source;
			
			s += '<li class="bsns" data-bsns-code="'+item.BSNS_CODE+'">';
			s += '<div class="article_area">';
			s += '	<p class="big_tit">';
			s += '		<a href="javascript:;" class="link" data-bsns-code="'+item.BSNS_CODE+'" data-mlsfc-nm-code="'+item.MLSFC_NM_CODE+'" title="상세페이지이동">'+item.MLSFC_NM+ ('N' != item.DETAIL_BSNS_NM ? '('+item.DETAIL_BSNS_NM + ')' : '') + '</a>';
			s += '	</p>';
			s += '	<div class="keyword_item">';
			s += '		<button type="button" class="item_05 similr" title="유사농업 경영체" style="display:none;">★</button>';
			s += '		<button type="button" class="item_01">'+item.SPORT_SUB+'</button>';
			s += '		<button type="button" class="item_02">'+getSportTargetString(gbn, item)+'</button>';
			s += '		<button type="button" class="item_03">'+item.SPORT_STLE+'</button>';
			s += '		<button type="button" class="item_04">'+item.SPORT_AREA+'</button>';
			s += '	</div>';
			s += '	<ul>';
			s += '		<li>';
			s += '			<dl>';
			s += '				<dt>지원금액(백만원, 최소~최대)</dt>';
			s += '				<dd>'+getSportAmountString(gbn, item)+'</dd>';
			s += '			</dl>';
			s += '		</li>';
			s += '		<li>';
			s += '			<dl>';
			s += '				<dt>사업기간</dt>';
			s += '				<dd>'+('N' != item.BSNS_PD ? item.BSNS_PD : '')+'</dd>';
			s += '			</dl>';
			s += '		</li>';
			s += '		<li>';
			s += '			<dl>';
			s += '				<dt>신청시기</dt>';
			s += '				<dd>' + ('N' != item.APPLY_TIME ? item.APPLY_TIME : '') + '</dd>';
			s += '			</dl>';
			s += '		</li>';
			s += '	</ul>';
			s += '	<p class="s_txt">'+item.BSNS_CN+'</p>';
			s += '</div>';
			s += '</li>';			
		}
	
		
		
		//
		if(0 == datas.length){
			s += '<li><p class="big_tit">데이터가 없습니다.</p></li>';
		}
		
		//
		$('.table_area.list ul').html(s);
		
		//클릭 이벤트 바인드
		$('.article_area .link').click(function(){
			let mlsfcNmCode = $(this).data('mlsfc-nm-code');
			let bsnsCode = $(this).data('bsns-code');
			
			//
			location.href = '#sub_content';
			
			//상세 데이터 조회&표시
			$('.widget_area').hide();
			detailObj.getAndShowData(gbn, mlsfcNmCode, bsnsCode);
			
		});
		
		//
		console.log('<<.showDatas');
	};
	
	
	/**
	 * 구분별 선택된 필터값 목록
	 */
	PageObj.prototype.getFilterValuesByGbn = function(gbn){
		let arr = [];
		
		if(PageObj.FRMER === gbn){
			arr.push({'name':'sportRelmCode',	'value' : $('.frmer[name=sportRelmCode]:checked').val()});
			arr.push({'name':'sexdstn',	'value' : $('.frmer[name=sexdstn]:checked').val()});
			arr.push({'name':'age',	'value' : $('.frmer[name=age]').val()});
			arr.push({'name':'residence',	'value' : $('.frmer[name=residence]').val()});
			arr.push({'name':'farmCareer',	'value' : $('.frmer[name=farmCareer]').val()});
			arr.push({'name':'ctvtPrdlstSetCode',	'value' : $('.frmer[name=ctvtPrdlstSetCode]').val()});
			arr.push({'name':'ctvtPrdlstCode',	'value' : $('.frmer[name=ctvtPrdlstCode]').val()});
			arr.push({'name':'ctvtTyCode',	'value' : $('.frmer[name=ctvtTyCode]:checked').val()});
			
		}else if(PageObj.CPR === gbn){
			arr.push({'name':'sportRelmCode',	'value' : $('.frmer[name=sportRelmCode]:checked').val()});
			arr.push({'name':'sportTyCode',	'value' : $('.frmer[name=sportTyCode]:checked').val()});
			arr.push({'name':'sportMbyCode',	'value' : $('.frmer[name=sportMbyCode]:checked').val()});
			arr.push({'name':'fondYear',	'value' : $('.frmer[name=fondYear]').val()});
			arr.push({'name':'capl',	'value' : $('.frmer[name=capl]').val()});
			arr.push({'name':'investorCo',	'value' : $('.frmer[name=investorCo]').val()});
			arr.push({'name':'ctvtPrdlstSetCode',	'value' : $('.frmer[name=ctvtPrdlstSetCode]').val()});
			arr.push({'name':'ctvtPrdlstCode',	'value' : $('.frmer[name=ctvtPrdlstCode]').val()});
			arr.push({'name':'trtmntScale',	'value' : $('.frmer[name=trtmntScale]').val()});
			arr.push({'name':'selngAmount',	'value' : $('.frmer[name=selngAmount]').val()});
			
		}else if(PageObj.NOT_FRMER === gbn){
			arr.push({'name':'sexdstn',	'value' : $('.frmer[name=sexdstn]:checked').val()});
			arr.push({'name':'age',	'value' : $('.frmer[name=age]').val()});
			arr.push({'name':'residence',	'value' : $('.frmer[name=residence]').val()});
			arr.push({'name':'farmngSchulOrginAt',	'value' : $('.frmer[name=farmngSchulOrginAt]').val()});
			
		}
		
		arr.push({'name':'searchGbn',	'value' : gbn});
		arr.push({'name':'searchKeyword',	'value' : $('.'+gbn+'[name=searchKeyword]').val()});
		
		//
		return arr;
		
	};
	
	
	/**
	 * 일부 데이터만 추출
	 */
	PageObj.prototype.getFromToDatas = function(datas, startIndex, endIndex){
		console.log('>>.getFromToDatas', datas.length, startIndex, endIndex);
		
		//
		let arr = [];
		
		//
		if(0 > startIndex || 0 > endIndex){
			return arr;
		}
	
		
		for(let i=startIndex; i<=endIndex; i++){
			arr.push( datas[i] );			
		}
		
		//
		console.log('<<.getFromToDatas');
		return arr;
	};
	
	
	/**
	 * 비농업인 - 조건 필터링
	 */
	PageObj.prototype.getFilteredDatasOfNotFrmer = function(datas){
		let _datas = datas;
		
		//
		let $div = $('div.tabcontent.notFrmer');
		
		//성별
		let sexdstn = $div.find('[name=sexdstn]:checked').val();
		if(!$.cs.isEmpty(sexdstn)){
			_datas = pageObj.getFilteredDatasOfNorFrmerBySexdstn(sexdstn, _datas);
		}
		
		//나이
		let age = $div.find('[name=age]').val();
		if(!$.cs.isEmpty(age)){
			_datas = pageObj.getFilteredDatasOfNorFrmerByAge(parseInt(age), _datas);
		}
		
		//지원지역 - 시도
		let sidoCode = $div.find('[name=sidoCode]').val();
		if(!$.cs.isEmpty(sidoCode)){
			//
			_datas = pageObj.getFilteredDatasOfNorFrmerBySidoCode(sidoCode, _datas);
		}
		
		//지원지역 - 시군구
		let sigunguCode = $div.find('[name=sigunguCode]').val();
		if(!$.cs.isEmpty(sigunguCode)){
			//
			_datas = pageObj.getFilteredDatasOfNorFrmerBySigunguCode(sigunguCode, _datas);
		}		
		
		//거주지
//		let residence = $div.find('[name=residence]').val();
//		if(!$.cs.isEmpty(residence)){
//			//
//			_datas = pageObj.getFilteredDatasOfNorFrmerByResidence(residence, _datas);
//		}
		
		
		//농업계학교 출신여부
		let farmngSchulOrginAt = $div.find('[name=farmngSchulOrginAt]').val();
		if(!$.cs.isEmpty(farmngSchulOrginAt)){
			_datas = pageObj.getFilteredDatasOfNorFrmerByFarmngSchulOrginAt(farmngSchulOrginAt, _datas);
		}
		
		//
		console.log('ofNotFrmer', sidoCode, 'before:', datas.length, 'after:', _datas.length);
		return _datas;
		
	};
	
	/**
	 * 데이터 필터링 - 비농업인 - 지원지역 - 시도
	 */
	PageObj.prototype.getFilteredDatasOfNorFrmerBySidoCode = function(sidoCode, datas){
		let arr = [];
		let ks = ['SIDO', "SIDO_CODE"];
		
		//
		for (let j = 0; j < datas.length; j++) {
			let item = datas[j];
			let d = item._source;
			
			//
			for (let i = 0; i < ks.length; i++) {
				let k = ks[i];
				//
				if($.cs.isNull(d[k])){
					continue;
				}
				
				//
				if(sidoCode == d[k]){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('bySidoCode', 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 비농업인 - 지원지역 - 시군구
	 */
	PageObj.prototype.getFilteredDatasOfNorFrmerBySigunguCode = function(sigunguCode, datas){
		let arr = [];
		let ks = ['SIGUN', "SIGUN_CODE"];
		
		//
		for (let j = 0; j < datas.length; j++) {
			let item = datas[j];
			let d = item._source;
			
			//
			for (let i = 0; i < ks.length; i++) {
				let k = ks[i];
				//
				if($.cs.isNull(d[k])){
					continue;
				}
				
				//
				if(sigunguCode == d[k]){
					arr.push(item);
				}
			}
		}
		
		
		//
		console.log('bySigunguCode', sigunguCode, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	/**
	 * 데이터 필터링 - 비농업인 - 거주지
	 */
	PageObj.prototype.getFilteredDatasOfNorFrmerByResidence = function(residence, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('N' != d.NM_RESIDE_AREA){
				if(-1 != d.NM_RESIDE_AREA.indexOf(residence)){
					arr.push(item);
				}
			}
		}
		
		//
		console.log('residence', residence, 'before', datas.length, 'after', arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 비농업인 - 농업계학교 출신여부
	 */
	PageObj.prototype.getFilteredDatasOfNorFrmerByFarmngSchulOrginAt = function(farmngSchulOrginAt, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			
			//
			if('Y' === farmngSchulOrginAt && 'Y' === d.NM_AGRI_SCH){
				arr.push(item);
			}else if('N' === farmngSchulOrginAt && 'N' === d.NM_AGRI_SCH){
				arr.push(item);
			}
		}
		
		//
		$.cs.log('byFarmngSchulOrginAt\tbefore:' + datas.length + '\tafter:' + arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 비농업인 - 나이
	 * @param age 나이
	 * @param datas 데이터 목록
	 */
	PageObj.prototype.getFilteredDatasOfNorFrmerByAge = function(age, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.NM_AGE){
				if(parseInt(d.NM_AGE_MIN) <= age && age <= parseInt(d.NM_AGE_MAX)){
					arr.push(item);
				}
			}
		}
		
		//
		$.cs.log('byAge\tbefore:' + datas.length + '\tafter:' + arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 필터링 - 비농업인 - 성별
	 * @param sexdstn 성별
	 * @param datas 데이터 목록
	 */
	PageObj.prototype.getFilteredDatasOfNorFrmerBySexdstn = function(sexdstn, datas){
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//
			if('Y' === d.NM_SEX){
				if('M' === sexdstn && 'Y' === d.NM_SEX_M){
					//남성
					arr.push(item);
				}else if('F' === sexdstn && 'Y' === d.NM_SEX_F){
					//여성
					arr.push(item);
				}
			}
		}
		
		//
		$.cs.log('bySexdstn\tbefore:' + datas.length + '\tafter:' + arr.length);
		return arr;
	};
	
	
	/**
	 * 데이터 요청
	 * gbn, keyword에 의한 모든 데이터 조회
	 */
	PageObj.prototype.getDatasAsync = function(){
		console.log('>>.getDatasAsync');
		
		
		let arr = [];
		
		//
		let gbn = '';
		$('button.tablinks').each(function(i,item){
			if($(item).hasClass('active')){
				gbn = $(item).data('gbn');
			}
		});
		
		//
		if($.cs.isEmpty(gbn)){
			$.cs.log('empty gbn');
			return;
		}
		
		//
		arr.push({'name':'searchGbn', 'value': gbn});
		arr.push({'name':'searchKeyword', 'value': $('div.tabcontent.'+gbn+' [name=searchKeyword]').val()});
		
		//
		if(-1 != arr[1].value.indexOf('\\')){
			alert('사용할 수 없는 문자가 포함되었습니다. \n확인 후 다시 입력하시기 바랍니다.');
			return;
		}
		
		//
		$('#sub_content').loading();

		//
		$.cs.submitAjax('./getFixesSportPolicyDatas.json', arr, function(res){
			console.log('<<.getDatasAsync');
			
			//
			dataObj.responseData = res;
			
			//전체건수
			$('.fir_ch b').html(dataObj.responseData.datas.length);
						
			//
			pageObj.filterDatas();
			//
			$('#sub_content').loading('stop');
		} );
		
	};
	

	/**
	 * 지원영역별 건수 계산 & 화면에 표시
	 */
	PageObj.prototype.processCountsBySportArea = function(datas){
		//전체 건수 표시
		let getTotcnt = function(json){
			let totcnt=0;
			let keys = Object.keys(json);
			for (let i = 0; i < keys.length; i++) {
				let k = keys[i];
				totcnt += json[k];
			}
			
			//
			return totcnt;
		};
		
		//화면 표시
		let show = function(json){
			let s = '';
			s += '<ul>';
			
			s += '<li class="fir_ch">';
			s += '	전체';
			s += '	<b>'+getTotcnt(json)+'</b>';
			s += '<li>';
			
			let keys = Object.keys(json);
			for (let i = 0; i < keys.length; i++) {
				let x = keys[i];
				s += '<li>';
				s += '	<span>'+x+'</span>';
				s += '	<b>'+json[x]+'</b>';
				s += '<li>';
			}
			
			s += '</ul>';
			
			//
			$('.tab_menu_area').html(s);


			//TODO 클릭 이벤트 바인드
		};
		
		//지원영역별 건수
		let getCountsBySportRelmCode = function(datas){
			let json = {};
			
			for (let i = 0; i < datas.length; i++) {
				let item = datas[i];
				let d = item._source;
				let k = d.SPORT_AREA;
				
				if($.cs.isNull(json[k])){
					json[k] = 1;
				}else{
					json[k] = json[k] + 1;
				}
			}


			//
			return json;
		};
		
		
		
		//지원영역별 건수 계산
		let json = getCountsBySportRelmCode(datas);

		// 화면에 표시
		show(json);
		
		//요약정보 표시
		pageObj.showSumry(datas, json);

	};
	
	
	/**
	 * 요약정보 표시
	 * @param datas
	 * @param countsBySportRelmCode 지원영역별 건수 [{'지원영역':건수}]
	 */
	PageObj.prototype.showSumry = function(datas, countsBySportRelmCode){

		//
		let getMinValue = function(stringOrNumber, minValue){
			if(isNaN(stringOrNumber)){
				return minValue;
			}
			
			//
			return (minValue > parseInt(stringOrNumber) ? parseInt(stringOrNumber) : minValue);
		};
		
		//
		let getMaxValue = function(stringOrNumber, maxValue){
			if(isNaN(stringOrNumber)){
				return maxValue;
			}
			
			//
			return (maxValue < parseInt(stringOrNumber) ? parseInt(stringOrNumber) : maxValue);
		};
				
		//지원 정책 건수 표시
		let showSportPosblPolicyCount = function(countsBySportRelmCode){
			let totcnt=0;
			
			let keys = Object.keys(countsBySportRelmCode);
			for (let i = 0; i < keys.length; i++) {
				let k = keys[i];
				totcnt += countsBySportRelmCode[k];
			}
			
			
			//
			$('.result_area .sportPosblPolicyCount').html(totcnt);
		};
		
		//지원 예상 금액
		let showAmount = function(datas){
			let min = Number.MAX_VALUE;
			let max = Number.MIN_VALUE;
			let sum = 0;
			let gbn = pageObj.getCheckedGbn();
			
			//
			for (let i = 0; i < datas.length; i++) {
				let item = datas[i];
				let d = item._source;
				
				//농업인
				if(PageObj.FRMER === gbn && 'Y' === d.FMER_REQST){
					min = getMinValue(d.FMER_SPORT_AMNT_MIN, min);
					max = getMaxValue(d.FMER_SPORT_AMNT_MAX, max);
					
					//
					if(!isNaN(d.FMER_SPORT_AMNT_MAX)){
						sum += parseInt(d.FMER_SPORT_AMNT_MAX);
					}
				}
				//법인
				if(PageObj.CPR === gbn && 'Y' === d.CO_REQST){
					min = getMinValue(d.CO_SPORT_AMNT_MIN, min);
					max = getMaxValue(d.CO_SPORT_AMNT_MAX, max);
					
					//
					if(!isNaN(d.CO_SPORT_AMNT_MAX)){
						sum += parseInt(d.CO_SPORT_AMNT_MAX);
					}
				}
				//비농업인
				if(PageObj.NOT_FRMER === gbn && 'Y' === d.NM_REQST){
					min = getMinValue(d.NM_SPORT_AMNT_MIN, min);
					max = getMaxValue(d.NM_SPORT_AMNT_MAX, max);
					
					//
					if(!isNaN(d.NM_SPORT_AMNT_MAX)){
						sum += parseInt(d.NM_SPORT_AMNT_MAX);
					}
				}
			}
			
			
			//
			if(min === Number.MAX_VALUE){
				min = 0;
			}
			if(max === Number.MIN_VALUE){
				max = 0;
			}
			
			//
			if(PageObj.ALL === gbn){
//				alert('TODO 전체일 경우 최대 지원 가능 금액은 무엇으로 계산해야 하나?');
			}

			//
			$('.result_area .sumAmount').html( $.cs.addComma(sum) );

		};
		
		//지원 정책 건수
		showSportPosblPolicyCount(countsBySportRelmCode);
		//지원 예상 금액
		showAmount(datas);
	};
	
	
	/**
	 * 페이징 표시
	 * @param currentPageNo
	 * @param totalRecordCount
	 * @return paginate json
	 */
	PageObj.prototype.showPager = function(currentPageNo, totalRecordCount){
		let json = $.cs.paginate(totalRecordCount, currentPageNo);
		
		//
		let s = '';
		
		s += '<a href="javascript:;" data-page-no="1" title="처음">&lt;&lt;</a>';
	
		
		//page list
		for (let i = 0; i < json.pages.length; i++) {
			let item = json.pages[i];
			if(currentPageNo == item){
				s += '<a href="javascript:;" class="on" data-page-no="'+item+'">'+item+'</a>';
			}else{
				s += '<a href="javascript:;" data-page-no="'+item+'">'+item+'</a>';				
			}
		}

		//last
		s += '<a href="javascript:;" data-page-no="'+json.totalPages+'" title="마지막" style="border-right:0;">&gt;&gt;</a>';
		
		//append to html 
		$('.page_area').html(s);
		
		//클릭 이벤트 등록
		$('.page_area a').click(function(){
			let currentPageNo = $(this).data('page-no');
			
			//
			pageObj.filterDatas(parseInt(currentPageNo));
			
			//
			location.href = '#sub_content';
		});
		
		//
		console.log('<<.showPager', json);
		return json;
	};
	
	
	
	/**
	 * (공통) 품목군 코드로 품목 코드 목록 구하기
	 * @param prdlstSetCode 품목 군 코드
	 */
	PageObj.prototype.getPrdlstCodes = function(prdlstSetCode){
		let arr = [];
		
		//
		$('.prdlstCodes').each(function(i,item){
			if(prdlstSetCode === $(item).data('prdlst-set-code')){
				arr.push({'code': $(item).data('prdlst-code'), 'name': $(item).html()});
			}
		});

		//
		console.log('<<.getPrdlstCodes', prdlstSetCode, arr.length);
		return arr;
	};
	
	
	/**
	 * (공통) 시도 코드로 시군구 목록 추출
	 * @param sidoCode 시도 코드
	 */
	PageObj.prototype.getSigunguCodes = function(sidoCode){
		let arr = [];
		
		//
		$('.sigunguCodes').each(function(i,item){
			
			//코드 또는 명이 match되면
			if((sidoCode == $(item).data('sido-code')) || (sidoCode == $(item).data('sido-name'))){
				arr.push({'code': $(item).data('sigungu-code'), 'name': $(item).html()});
			}
		});
		
		//
		return arr;
	}
	
	
	
	/**
	 * 유사 경영체. 농업인 일때만 처리
	 * @param datas 필터링된 사업 목록
	 * @param isReloadDatas 데이터 재 조회 여부
	 */
	PageObj.prototype.similrAtmnent = function(datas, isReloadDatas){
		console.log('>>.similrAtmnent', datas.length, isReloadDatas);
		
		//
		let gbn = pageObj.getCheckedGbn();
		
		//농업인일때만 처리
		if(PageObj.FRMER !== gbn){
			return;
		}
		
		let arr=[];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			let d = item._source;
			
			//아그릭스 사업만 처리
			if('N' !== d.BSNS_CODE){
				arr.push(d.BSNS_CODE);
			}
		}
		
		//
		let sexdstn=null, age=null, farmCareer=null, prdlstSetCode=null, prdlstCode=null, ctvtTyCode=null, arOrCo=null;
		
		//
		if(PageObj.FRMER === gbn){
			//성별
			sexdstn = $('.'+gbn+'[name=sexdstn]:checked').val();
			//나이
			age = $('.'+gbn+'[name=age]').val();
			//영농경력
			farmCareer = $('.'+gbn+'[name=farmCareer]').val();
			//품목군
			prdlstSetCode = $('.'+gbn+'[name=ctvtPrdlstSetCode]').val();
			//품목
			prdlstCode = $('.'+gbn+'[name=ctvtPrdlstCode]').val();
			//재배유형
			ctvtTyCode = $('.'+gbn+'[name=ctvtTyCode]').val();
			//면적or두수
			arOrCo = $('.'+gbn+'[name=arOrCo]').val();
			
		}
		
			
		//유사 경영체 존재하는 목록만 조회
		let getODatas = function(datas){
			let arr = [];
			
			for (let i = 0; i < datas.length; i++) {
				let item = datas[i];
				if(0 < item.CNT){
					arr.push(item);
				}
			}
			
			//
			return arr;
		};
		
		
		//유사 경영체 전체 건수 표시
		let showSimilrCnt = function(odatas){
			$('.result_area .similrAtmnentCount').html(odatas.length);
		};
		
		//유사 경영체 탭 표시
		let showSimilrTab = function(odatas){
			let s = '';
			s += '<li>	<span>유사경영체</span>	<b>'+odatas.length+'</b></li>';
			
			$('.tab_menu_area ul').append(s);
		};
		

		//유사 경영체 사업에 아이콘 표시
		let showSimilrAtmnentIcon = function(odatas){
			for (let i = 0; i < odatas.length; i++) {
				let item = odatas[i];
				let $li = $('li.bsns[data-bsns-code='+item.SPORT_BSNS_CODE+']');
				if(0 < item.CNT){
					$li.find('button.similr').show()
				}
			}
		};
		
		//데이터 조회 후 처리
		let callbackFunction = function(datas){
			//유사 경영체 존재 목록만 조회
			let odatas = getODatas(datas);
			
			//전체 유사 경영체 건수 표시
			showSimilrCnt(odatas);
			
			//유사 경영체 탭 표시
			showSimilrTab(odatas);
			
			//유사 경영체 사업 표시
			showSimilrAtmnentIcon(odatas);			
		};
		
		//
		if(isReloadDatas){
			//
			saObj.getDatasAsync(arr, sexdstn, age, farmCareer, prdlstSetCode, prdlstCode, ctvtTyCode, arOrCo, callbackFunction);
			
		}else{
			callbackFunction(saObj.datas);
		}
		
		//
		console.log('<<.similrAtmnent');
	};
});


//
let pageObj = new PageObj();

//
$(document).ready(function(){
	pageObj.init();
});