/**
 * 상세화면
 */

/** 발표를 위해 만듦. 지원정책 검색용*/
let PolicySrchObj = (function(){
	this.ctprvn = '';
	//TODO 시군구
	this.signgu = '';
	//TODO 읍면동
	this.emd = '';
	//귀농정책 수
	this.retnPolicyCnt = 0;
	//교육정책 수
	this.edcPolicyCnt = 0;
})


let DataObj = (function(){
	this.form = {};

	DataObj.prototype.init = function(){
		this.refreshDataObj();

	}

	DataObj.prototype.getDummyAreaInfo = function(){


		return {


		};
	}


	/**서버에 저장된 DataObj 데이터를 불러온다.*/
	DataObj.prototype.refreshDataObj = function(){
		$.post("./retnFmlgDetail.json", function(json){
//			console.log(json);
			if(json.result == "success"){
				dataObj.form = json.dataObj;
				dataObj.areaInfo = json.areaInfo.datas;

				if(dataObj.areaInfo.length > 0 ){
					myInfoObj.setSideMenuInfo(); // DataObj 데이터를 기준으로 'My 내가 입력한 여건입니다.'를 수정
					resultViewOrdrObj.refresh();
					pageObj.setRecomendAreaTitle();
					pageObj.setSimilrTrnsfrnSttus(); // 유사 귀농인 전입지역 현황 이미지 교체
					pageObj.setUserCnsdr(); // 사용자의 고려사항을 유사귀농인정보 > 지역선택 고려요인비교에 입력
					dataObj.similrRetnFmlgInfo(); // 유사 귀농인 정보를 불러온다.
					dataObj.getSelectAreaCtvtInfo(); // 선택지역재배 품목 정보를 불러온다.
					console.log(dataObj.form);
					if(dataObj.form.insteadCtvt != null) {
						alert("희망 귀농지역에 해당하는 품목이 없어 차순위 품목을 표시합니다\n("+dataObj.form.insteadCtvt+"->"+dataObj.form.hopeCtvt+")");
					}
//					dataObj.updtDataObj(false);
				}else{
					if(dataObj.form.hopeRdNmAdr != ""){
						myInfoObj.setSideMenuInfo(); // DataObj 데이터를 기준으로 'My 내가 입력한 여건입니다.'를 수정
						alert("희망 귀농지역을 찾지 못하였습니다\n다른지역을 입력해주시기 바랍니다");
					}
				}

			}else if(json.result == "fail"){
				dataObj.form = json.dataObj;
				myInfoObj.setSideMenuInfo(); // DataObj 데이터를 기준으로 'My 내가 입력한 여건입니다.'를 수정
				alert(json.msg);
			}

		});
	}

	/**유사 귀농인 정보를 불러온다.*/
	DataObj.prototype.similrRetnFmlgInfo = function(){
		let areaInfo = {};
		areaInfo.areaInfo = dataObj.areaInfo;
		areaInfo = JSON.stringify(areaInfo);

		$.post("./similrRetnFmlgInfo.json", areaInfo, function(json){
			if(json.result == "success"){
				dataObj.similr = json.similrRetnFmlgInfo;
				pageObj.setSimilrRetnFmlgCnsdr();
				similrRetnFmlgChart.refreshChart();
			}else{
				console.err("./similrRetnFmlgInfo.json Ajax not success");
			}
		});
	}


	/**선택지역 및 품목 분석 데이터를 불러온다..*/
	DataObj.prototype.getSelectAreaCtvtInfo = function(){

		$.post("./selectAreaCtvtInfo.json", {}, function(json){
			if(json.result == "success"){
				dataObj.selectAreaCtvtInfo = json.selectAreaCtvtInfo;
				selectAreaCtvtChart.refreshChart();
				console.log(dataObj);
			}else{
				console.err("./selectAreaCtvtInfo.json Ajax not success");
			}
		});
	}

	DataObj.prototype.updtDataObj = function(refresh){
		console.log(dataObj.form);
		let datasJson = JSON.stringify(dataObj.form);
		$.post('./updtRetnFmlgInfo.json', datasJson, function(json){
//			console.log("update data : ",json);

			console.log("refresh: "+refresh);
			if(refresh != false) {
				dataObj.refreshDataObj(); // 결과화면 다시 그리기
			}else {
				console.log("updtDataObj  ",dataObj);
				dataObj.similrRetnFmlgInfo(); // 유사 귀농인 정보를 불러온다.
				dataObj.getSelectAreaCtvtInfo(); // 선택지역재배 품목 정보를 불러온다.
			}
		});
	}

})

let PageObj = (function(){
	PageObj.prototype.init = function(){
		dataObj.init();

		this.setEventHandler();
	}


	/**
	 * 정책 목록 화면에 표시
	 * @parm pageNo 페이지 번호
	 */
	PageObj.prototype.showPolicyDatas = function(pageNo){

		//사업 목록
		let bsnssHtml = policyObj.getPagedDatasHtml(pageNo);
		$('.table_area.list ul').html(bsnssHtml);


		// 상세 클릭 이벤트 등록
		$('.list .bsns .link').click(function(){
//			if('TODO' === 'TODO'){
//				return;
//			}

			//
			$li = $(this).closest('li.bsns');
			let lclasNmCode = $li.find('[name=LCLAS_NM_CODE]').val();
			let mlsfcNmCode = $li.find('[name=MLSFC_NM_CODE]').val();
			let bsnsCode = $li.find('[name=BSNS_CODE]').val();

			//데이터 조회
			policyObj.getDetailDataAsync(mlsfcNmCode, bsnsCode, function(data){
				//목록 div hide
				$('.table_area.list').hide();

				//상세 데이터 조회
				let s = policyObj.getDetailHtml(data);

				//화면에 표시
				$('.table_area.detail').show().html(s);

				//목록보기 클릭 이벤트 등록
				$('.btnList').click(function(){
					//상세 div hide
					$('.table_area.detail').hide();
					//목록 div show
					$('.table_area.list').show();
				});

				//홀수/짝수 class 추가
				$('.bsns.detail:odd').addClass('table_area02');
				$('.bsns.detail:even').addClass('table_area');
			});
		});

		//탭
		let tabHtml = policyObj.getTabHtml();
		$('.tab_menu_area').html(tabHtml);

		//페이징
		let pagingHtml = policyObj.getPagingHtml(pageNo);
		$('.page_area').html(pagingHtml);

		// 페이징 클릭 이벤트 등록
		$('.page_area a').click(function(){
			let pageNo = parseInt( $(this).data('page-no') );

			//
			pageObj.showPolicyDatas(pageNo);
		});
	};


	PageObj.prototype.setEventHandler = function(){

		//정책 목록
		$('#btn_popup_open').click(function(){

			//
			$('.popup_panel').fadeIn('fast');
		});

		//정책 목록창 닫기
		$('#btn_popup_close').click(function(){
			$('.popup_panel').fadeOut('fast');
		});


	    /************************** 결과 화면 메뉴이동 **************************/
	    $('.scrollMove').on('click',function(event){
	    	event.preventDefault();
	    	$('html,body').animate({scrollTop:$(this.hash).offset().top-170},500);
	    	$(this).parent('li').siblings().removeClass('on');
			$(this).parent('li').addClass('on');
	    });


		$('.tabTitle>.tablinks').on('click',function(){
			$(this).siblings().removeClass('on');
			$(this).addClass('on');


		});

		/************************** 재배품목 클릭 시 이벤트 **************************/
		$('.fixesCtvt .tablinks').on('click', function(){
			pageObj.setFiexsCtvt();

		});


		$('.tablinks').on('click',function(){

			var _index = $(this).parent('ul').children('li').index(this);

			$(this).parent().siblings('.tabContWrap').children('.tabcontent').siblings().removeClass('on');
			$(this).parent().siblings('.tabContWrap').children('.tabcontent').eq(_index).addClass('on');
		});


		/************************** 지역,품목 선택 **************************/
		$('.areaList button,.itemList button').on('click',function(){
			$(this).siblings().removeClass('on');
			$(this).addClass('on');
		});




		$("#retnFmlgPolicySearchBtn").on("click",function(){
			window.open('./retnFmlgPolicySearchList.do', '_blank');
		});

		/************************** PAGING **************************/
		$('.myBox button.open').on('click',function(){
			$(this).closest('.myBox').toggleClass('on')
		});


		/************************** PAGING **************************/
		$('.paging li').on('click',function(){
			$(this).siblings().removeClass('on');
			$(this).addClass('on');
		});

		/************************** HELP **************************/
		$('.help>em').hover(
		  function() {
		    $(this).closest('.help').addClass('on');
		  }, function() {
		    $(this).closest('.help').removeClass('on');
		  }
		);

		/**맞춤 귀농 정보 추천 3개지역 탭 눌렀을 때 이벤트*/
		$('.recomendArea').on('click', function(){
			$('.recomendArea').removeClass('on');
			$(this).addClass('on');

			pageObj.refreshAreaSetlCnd(); // 지역정주여건 정보 새로고침
		});


	}//setEventHandler()

	/**유사귀농인 전입지역 현황*/
	PageObj.prototype.setSimilrTrnsfrnSttus = function(){
		let index = dataObj.form.index;
		$("#similrTrnsfrnSttus").attr('src', "/images/bigdata/svc/retnFmlg/retnFmlgIndex"+index+".png");
	}

	/*유사귀농인 > 지역선택 고려요인 비교 > 이용자가 선택한 고려 항목*/
	PageObj.prototype.setUserCnsdr = function(){
		let userCnsdr =  dataObj.form;

		let upperCnsdr = "";
		let middleCnsdr = "";
		let lowerCnsdr = "";

		userCnsdr.upperCnsdr.forEach(function(val){
			upperCnsdr += val.desc + "<br/>";
		});
		userCnsdr.middleCnsdr.forEach(function(val){
			middleCnsdr += val.desc + "<br/>";
		});
		userCnsdr.lowerCnsdr.forEach(function(val){
			lowerCnsdr += val.desc + "<br/>";
		});
		$('.userCnsdrWght .upper').html(upperCnsdr);
		$('.userCnsdrWght .middle').html(middleCnsdr);
		$('.userCnsdrWght .lower').html(lowerCnsdr);
	}

	/*유사귀농인 > 지역선택 고려요인 비교 > 유사귀농인 고려항목*/
	PageObj.prototype.setSimilrRetnFmlgCnsdr = function(){
		let similrRetnFmlgCnsdr = dataObj.similr.similrRetnFmlgCnsdrDesc;

		let upperCnsdr = "";
		let middleCnsdr = "";
		let lowerCnsdr = "";

		similrRetnFmlgCnsdr.upperCnsdrDesc.forEach(function(val){
			upperCnsdr += val + "<br/>";
		});
		similrRetnFmlgCnsdr.middleCnsdrDesc.forEach(function(val){
			middleCnsdr += val + "<br/>";
		});
		similrRetnFmlgCnsdr.lowerCnsdrDesc.forEach(function(val){
			lowerCnsdr += val + "<br/>";
		});


		$('.cnsdrWght .upper').html(upperCnsdr);
		$('.cnsdrWght .middle').html(middleCnsdr);
		$('.cnsdrWght .lower').html(lowerCnsdr);

	}

	/**추천 재배 품목 데이터 셋팅 함수*/
	PageObj.prototype.setFiexsCtvt = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let pageSelectCtvtInfo = pageObj.getSelectCtvtInfo();

		pageObj.clearCtvtChart(); // 재배 품목 차트 없애기
		areaSetlCndChart.refreshCtvtChart(); // 재배 품목 차트 그리기

		/*소득정보 셋팅하기*/
		let prdlstAdiTab = $(".fixesCtvt + .tabContWrap > .tabcontent .tabcontent:nth-child(5)"); // 품목부가정보 탭

		let prdlstIncome = dataObj.areaInfo[selectAreaInfo.index].fixesCtvt[pageSelectCtvtInfo.index].prdlstIncome; // 품목소득정보
		let prdlstIncomeHtml = "";

		if(prdlstIncome == null){//만약 소득정보가 없으면
			prdlstIncomeHtml += '<tr>'
							+ '<th class="txtC">-</th>'
							+ '<td class="txtC">-</td>'
							+ '<td class="txtC">-</td>'
							+ '<td class="txtC">-</td>'
							+ '<td class="txtC">-</td>'
							+'</tr>';
		}else{ // 품목소득 정보가 있으면
			prdlstIncomeHtml += '<tr>'
				+ '<th class="txtC">'+ $.cs.addComma(Math.round(prdlstIncome.avgGrIncmeAmount)) +'</th>'
				+ '<td class="txtC">'+ $.cs.addComma(Math.round(prdlstIncome.avgMngmtCt)) +'</td>'
				+ '<td class="txtC">'+ $.cs.addComma(Math.round(prdlstIncome.avgIncomeAmount)) +'</td>'
				+ '<td class="txtC">'+ prdlstIncome.avgIncomeRate.toFixed(1) +'</td>'
				+ '<td class="txtC">'+ $.cs.addComma(Math.round(prdlstIncome.avgFrmhsReceptUntpc)) +'</td>'
				+'</tr>';
		}

		$(prdlstAdiTab.find('tbody')[0]).html(prdlstIncomeHtml);

		/*소매가격정보 셋팅하기
		 * */
		let ctvtInfo = dataObj.areaInfo[selectAreaInfo.index].fixesCtvt[pageSelectCtvtInfo.index];
		let rtlsalTodayGroup = ctvtInfo.rtlsalToday.aggregations.group_by_state.buckets; //당일 소매가격정보 그룹
		let rtlsalBefore3DateGroup = ctvtInfo.rtlsalBefore3Date.aggregations.group_by_state.buckets; //3일 전 소매가격정보 그룹
		let rtlsalBefore7DateGroup = ctvtInfo.rtlsalBefore7Date.aggregations.group_by_state.buckets; //7일 전 소매가격정보 그룹
		let rtlsalBefore1MonthGroup = ctvtInfo.rtlsalBefore1Month.aggregations.group_by_state.buckets; //한달 전 소매가격정보 그룹
		let rtlsalBefore1YeaGroupr = ctvtInfo.rtlsalBefore1Year.aggregations.group_by_state.buckets; //1년전 당일 소매가격정보 그룹
		let rtlsalrtlsalAvgYearGroup = ctvtInfo.rtlsalAvgYear.aggregations.group_by_state.buckets; //평년 소매가격정보 그룹

		function getPrice(key, group){
			let price = '-';
			group.forEach(function(bucket){
				if(bucket.key == key) price = $.cs.addComma(bucket.avd_value.value.toFixed(0));
			});
			return price;
		}


		let rtlsalHtml = "";

		//평년 소매 가격 정보의 거래 단위(bds_whsal_rtlsal_pc(엘라스틱서치 인덱스) : kindname)을 기준으로 테이블의 row를 생성한다.
		for(let i=0; i<rtlsalrtlsalAvgYearGroup.length; i++){
			let d = rtlsalrtlsalAvgYearGroup[i];
			let key = d.key;

			let rtlsalToday = getPrice(key, rtlsalTodayGroup); //당일 소매가격정보
			let rtlsalBefore3Date = getPrice(key, rtlsalBefore3DateGroup); //3일 전 소매가격정보
			let rtlsalBefore7Date = getPrice(key, rtlsalBefore7DateGroup); //7일 전 소매가격정보
			let rtlsalBefore1Month = getPrice(key, rtlsalBefore1MonthGroup); //한달 전 소매가격정보
			let rtlsalBefore1Year = getPrice(key, rtlsalBefore1YeaGroupr); //1년전 당일 소매가격정보
			let rtlsalrtlsalAvgYear = getPrice(key, rtlsalrtlsalAvgYearGroup); //평년 소매가격정보

			rtlsalHtml += '<tr>'
				+ '<th class="txtC">'+ d.key +'</th>'
				+ '<td class="txtC">'+ rtlsalToday +'</th>'
				+ '<td class="txtC">'+ rtlsalBefore3Date +'</th>'
				+ '<td class="txtC">'+ rtlsalBefore7Date +'</th>'
				+ '<td class="txtC">'+ rtlsalBefore1Month +'</th>'
				+ '<td class="txtC">'+ rtlsalBefore1Year +'</th>'
				+ '<td class="txtC">'+ rtlsalrtlsalAvgYear +'</th>'
				+'</tr>';

		}
		if(rtlsalrtlsalAvgYearGroup.length == 0){
			rtlsalHtml += '<tr>'
				+ '<th class="txtC">'+ '-' +'</th>'
				+ '<td class="txtC">'+ '-' +'</th>'
				+ '<td class="txtC">'+ '-' +'</th>'
				+ '<td class="txtC">'+ '-' +'</th>'
				+ '<td class="txtC">'+ '-' +'</th>'
				+ '<td class="txtC">'+ '-' +'</th>'
				+ '<td class="txtC">'+ '-' +'</th>'
				+'</tr>';
		}

		$(prdlstAdiTab.find('tbody')[1]).html(rtlsalHtml);

	}



	/**추천 재배 품목 셋팅*/
	PageObj.prototype.setFixesCtvtTitle = function(){
		console.log(dataObj);
		let recomendArea = this.getSelectedAreaInfo();

		for(let i=0; i<dataObj.areaInfo[recomendArea.index].fixesCtvt.length; i++){
			let fixesCtvt = dataObj.areaInfo[recomendArea.index].fixesCtvt[i].prdlstNm;
			$($(".fixesCtvt > .tablinks")[i]).find('span').text(fixesCtvt);
		}

		/*희망 재배 품목 셋팅*/
		$('#result06 > .tabWrap > .tabTitle > .tablinks:nth-child(1) span').text(dataObj.form.hopeCtvt);
	}

	/*현재 선택되어있는 추천 품목의 인덱스 반환한다.*/
	PageObj.prototype.getSelectCtvtInfo = function(){
		let result = {};
		$(".fixesCtvt > .tablinks").each(function(index, item){
			if($(item).hasClass("on")){
				result.index = index;
			}
		});
		return result;
	}

	/**현재 선택되어있는 지역의 주소와 인덱스를 반환한다*/
	PageObj.prototype.getSelectedAreaInfo = function(){
		let count = $('.recomendArea.on').find('.num').text();
		let recomendArea =	dataObj.areaInfo[count-1];

		return {
			index : count-1
			, ctprvn : recomendArea.ctprvn
			, signgu : recomendArea.signgu
			, emd    : recomendArea.emd};
	}

	/**
	 * 	1) 지원정책, 정주여건, 맞춤 품목 정보 뒤에 오는 주소를 변경한다
	 * 	2) 차트 위에 오는 서브 타이틀 제목을 변경한다.
	 * */
	PageObj.prototype.refreshSubTitle = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();

		/* 지원정책, 정주여건 주소 수정*/
		$('#result03, #result04, #result05, #result06').each(function(index, item){
			let subTitle = $(item).find('h3');
			let subTitleText = subTitle.text();

			subTitleText = subTitleText.substring(0, subTitleText.indexOf('-')+2); // - 뒤의 주소를 없앤다.
			subTitleText += selectAreaInfo.ctprvn + ' ' + selectAreaInfo.signgu;

			subTitle.text(subTitleText);
		});

		/*차트 위 서브 타이틀 수정*/
		$(".selectSigngu").text(selectAreaInfo.signgu);
		$(".selectEmd").text(selectAreaInfo.emd);
	}

	/*선택한 지역에 따라 > 유통 정보 수정*/
	PageObj.prototype.setDistbInfo = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();

		let apcInfoArr = dataObj.areaInfo[selectAreaInfo.index].distbInfoApc;
		let localInfoArr = dataObj.areaInfo[selectAreaInfo.index].distbInfoLocal;

		let apcHtml = "";
		let localHtml = "";

		if(apcInfoArr.length == 0 ){
			apcHtml += '<tr>'
					+ '<th scope="row">-</th>'
					+ '<td class="txtC">-</td>'
					+ '<td class="txtC">-</td>'
					+ '<td class="txtC">-</td>'
					+ '</tr>'
		}
		if(localInfoArr.length == 0 ){
			localHtml += '<tr>'
				+ '<th scope="row">-</th>'
				+ '<td class="txtC">-</td>'
				+ '<td class="txtC">-</td>'
				+ '<td class="txtC">-</td>'
				+ '</tr>'
		}
		apcInfoArr.forEach(function(apcInfo, index){
			apcHtml += '<tr>'
					+ '<th scope="row">'+ (index+1) +'</th>'
					+ '<td class="txtC">'+ apcInfo.bsnmNm +'</td>'
					+ '<td class="txtC">'+ apcInfo.trtmntPrdlst +'</td>'
					+ '<td class="txtC">'+ apcInfo.locplc +'</td>'
					+ '</tr>'
		});
		localInfoArr.forEach(function(localInfo, index){
			let addr = localInfo.ctprvn + ' ' + localInfo.signgu  + ' ' + localInfo.emd
			localHtml += '<tr>'
				+ '<th scope="row">'+ (index+1) +'</th>'
				+ '<td class="txtC">'+ localInfo.strNm +'</td>'
				+ '<td class="txtC">'+ addr +'</td>'
				+ '<td class="txtC">'+ localInfo.cttpc +'</td>'
				+ '</tr>'
		});

		$(".distbInfoApc tbody").html(apcHtml) // 농산물산지유통센터정보
		$(".distbInfoLocal tbody").html(localHtml) // 로컬푸드직매장 현황
	}

	/**선택한 지역에 따라 > 부가정보 수정*/
	PageObj.prototype.setAdiInfo = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();

		let uninhbhousInfoArr = dataObj.areaInfo[selectAreaInfo.index].uninhbhousInfo.hitsHits; //빈집정보
		let jbhntInfoArr = dataObj.areaInfo[selectAreaInfo.index].jbhntInfo.hitsHits; //일자리정보
		let altrvSchulArr = dataObj.areaInfo[selectAreaInfo.index].altrvSchul; //대안학교

		let uninhbhousInfoHtml = "";
		let jbhntInfoHtml = "";
		let altrvSchulHtml = "";


		function getEmptyHtml(cnt){
			let emptyHtml = '<tr>'	+ '<th scope="row">-</th>';

			for(let i=0; i<cnt; i++){emptyHtml += '<td class="txtC">-</td>';}
			emptyHtml += '</tr>'
			return emptyHtml;
		}


		if(uninhbhousInfoArr.length == 0 ) { uninhbhousInfoHtml += getEmptyHtml(4); }
		if(jbhntInfoArr.length == 0 ) { jbhntInfoHtml += getEmptyHtml(5); }
		if(altrvSchulArr.length == 0 ) { altrvSchulHtml += getEmptyHtml(3); }

		uninhbhousInfoArr.forEach(function(uninhbhousInfo, index){
			uninhbhousInfo = uninhbhousInfo['_source'];
			let regDt = uninhbhousInfo.REG_DT; // 등록날짜
			regDt = regDt.substring(0,4) + "." + regDt.substring(4,6) + "." + regDt.substring(6,8);

			let addr = uninhbhousInfo.ADDR; // 주소지
			let dealYype = uninhbhousInfo.DEAL_TYPE; // 거래 유형
			let lotArea = (uninhbhousInfo.LOT_AREA*1).toFixed(2); // 토지면적

			uninhbhousInfoHtml += '<tr>'
				+ '<th scope="row">' + (index+1) +'</th>'
				+ '<td class="txtC">'+ regDt +'</td>'
				+ '<td class="txtC">'+ addr +'</td>'
				+ '<td class="txtC">'+ dealYype +'</td>'
				+ '<td class="txtC">'+ lotArea +'</td>'
				+ '</tr>';
		});

		jbhntInfoArr.forEach(function(jbhntInfo, index){
			jbhntInfo = jbhntInfo['_source'];
			let regDt = '20' + jbhntInfo.regDt.replace(/-/gi,'.'); // 등록일
			let cmpnyNm = jbhntInfo.company; // 회사명
			let title = jbhntInfo.title; // 채용공고
			let workCnd = jbhntInfo.salTpNm + ' ' + jbhntInfo.sal; // 근무조건
			let closeDt = jbhntInfo.closeDt; // 마감일

			closeDt = closeDt.replace("채용시까지  ", ""); // '채용시까지  '라는 단어를 없앤다.

			//D-day를 구한다.
			let closeDtObj = new Date( '20'+ closeDt.substring(0,2), closeDt.substring(3,5)-1, closeDt.substring(6,8));
			let nowDt = new Date();
			let gap = nowDt.getTime() - closeDtObj.getTime();
			gap = Math.floor(gap / (1000 * 60 * 60 * 24)); // 날자 차이
			closeDt = "D-"+gap;

			jbhntInfoHtml += '<tr>'
				+ '<th scope="row">' + (index+1) +'</th>'
				+ '<td class="txtC">'+ regDt +'</td>'
				+ '<td class="txtC">'+ cmpnyNm +'</td>'
				+ '<td class="txtC">'+ title +'</td>'
				+ '<td class="txtC">'+ workCnd +'</td>'
				+ '<td class="txtC">'+ closeDt +'</td>'
				+ '</tr>';
		});
		altrvSchulArr.forEach(function(altrvSchul, index){
			let addr = altrvSchul.ctprvn + ' ' + altrvSchul.signgu  + ' ' + altrvSchul.emd
			altrvSchulHtml += '<tr>'
						+ '<th scope="row">' + (index+1) +'</th>'
						+ '<td class="txtC">'+ altrvSchul.schulNm +'</td>'
						+ '<td class="txtC">'+ addr +'</td>'
						+ '<td class="txtC">'+ altrvSchul.schulSe +'</td>'
						+ '</tr>';
		});

		$(".uninhbhous tbody").html(uninhbhousInfoHtml);
		$(".jbhnt tbody").html(jbhntInfoHtml);
		$(".altrvSchul tbody").html(altrvSchulHtml);

	}

	/*선택한 지역에 따라 지역정주여건 > 관련 정보들을 수정한다.*/
	PageObj.prototype.refreshAreaSetlCnd = function(){

		pageObj.refreshSubTitle(); // 주소
		pageObj.setFixesCtvtTitle(); // 맞춤 재배 품목 제목 셋팅
		pageObj.setDistbInfo(); // 유통정보 셋팅
		pageObj.setAdiInfo(); // 부가정보 셋팅
		pageObj.clearChart(); // 차트 지우기
		detailChart.refreshChart(); // 결과화면 차트 만들기
		pageObj.setFiexsCtvt();
		pageObj.setPolicySrchObj(); // 지원정책검색 객체 셋팅



	}

	/*귀농지원정책 관련 객체 설정*/
	PageObj.prototype.setPolicySrchObj = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let areaInfo = dataObj.areaInfo[selectAreaInfo.index];

		policySrchObj.ctprvn = areaInfo.ctprvn;
		policySrchObj.signgu = areaInfo.signgu
		policySrchObj.emd = areaInfo.emd;

		// 시도
		let ctprvn = policySrchObj.ctprvn;
		// 시군구
		let signgu = policySrchObj.signgu;
		// 읍면동
		let emd = policySrchObj.emd;
		//
		policyObj.getDatasAsync(ctprvn, signgu, emd,'귀농/귀촌/창업,교육/컨설팅', function(){
			pageObj.showPolicyDatas(1);


			//귀농 결과화면에서 귀농 지원 정책 정보를 수정
			if(policySrchObj.retnPolicyCnt == null) policySrchObj.retnPolicyCnt = 0;
			if(policySrchObj.edcPolicyCnt == null) policySrchObj.edcPolicyCnt = 0;

			//귀농사업정보 수정
			let retnFmlgBsnsInfo = $('#result05 .roundBx:nth-child(1)');
			retnFmlgBsnsInfo.find('strong').each(function(index, item){
				switch(index){
//				case 0:	//전체
//						$(item).text(policySrchObj.retnPolicyCnt + policySrchObj.edcPolicyCnt);
//						break;
//				case 0: //교육
//					$(item).text(policySrchObj.edcPolicyCnt);
//					break;
				case 0: //정책
					$(item).text(policySrchObj.retnPolicyCnt);
					break;
				}
			});
			//유사 농업경영체 농림사업 수혜정보
			let similrFmlgInfo = $('#result05 .roundBx:nth-child(2)');
			similrFmlgInfo.find('strong').each(function(index, item){
				switch(index){
				case 0:	//전체
						$(item).text(policySrchObj.retnPolicyCnt + policySrchObj.edcPolicyCnt);
						break;
				case 1: //금액
						$(item).text(policySrchObj.edcPolicyCnt);
					break;
				}
			});

//			console.log(policySrchObj);
		});

	}



	/**
	 * 유사경영체 관련 처리
	 */
	PageObj.prototype.processSimilrAtmnent = function(){
		console.log('>>.processSimilrAtmnent');

		//유사 경영체 존재하는 목록만 조회
		let _getODatas = function(datas){
			let arr = [];

			datas.forEach(function(x){
				if(0 < x.CNT){
					arr.push(x);
				}
			});


			//
			return arr;
		};

		//유사 경영체 수 표시
		let _showCount = function(similrDatas){
			$('strong.similr.atmnent.count').html(similrDatas.length);
		};





		//아그릭스 사업 코드 목록
		let bsnsCodes = saObj.getAgrixBsnsCodes(FixesSportPolicyObj.datas);

		//
		if(0 === bsnsCodes.length){
			//
			$('strong.similr.atmnent.count').html('0');
			$('strong.similr.atmnent.amount').html('0');
			//
			return;
		}

		//TODO 조회조건
		let sexdstn = '';	//성별
		let age = '';	//나이
		let farmCareer = '';	//영농경력
		let pdlstSetCode = '';	//품목군코드
		let prdlstCode = '';	//품목코드
		let ctvtTyCode = '';	//재배유형
		let arOrCo = '';	//면적|두수

		//데이터 조회 비동기
		saObj.getDatasAsync(bsnsCodes, sexdstn, age, farmCareer, prdlstSetCode, prdlstCode, ctvtTyCode, arOrCo, function(datas){
			console.log(datas);

			//
			let similrDatas = _getODatas(datas);
			//유사경영체 전체 건수
			_showCount(similrDatas);
		});
	};

	/**맞춤 귀농 지역 정보 3개소 주소 셋팅 */
	PageObj.prototype.setRecomendAreaTitle = function(){

		let maxCnt = (dataObj.areaInfo.length > 3) ? 3 : dataObj.areaInfo.length;
		for(let i=0; i<maxCnt; i++){
			let recomendArea = dataObj.areaInfo[i];
			let recomendAreaAddr = recomendArea.ctprvn+ ' ' + recomendArea.signgu + ' ' + recomendArea.emd;
			$($('.recomendArea')[i]).find('.txt').text(recomendAreaAddr);
		}

		$('.recomendArea.on').trigger('click');
		pageObj.setFiexsCtvt();
	}

	/**
	 * 차트에 이것저것 그리기
	 */
	Chart.plugins.register({
		afterDatasetDraw: function(chartInstance, easing) {
			var ctx = chartInstance.chart.ctx;
			var chartTitle = chartInstance.chart.config.options.title.text;

			if(ctx.name == 'N') return;
			var subTitle = ctx.canvas.name;

			ctx.fillStyle = '#808184';

    		// 값
            var fontSize = 12;
            var fontStyle = 'normal';
            var fontFamily = 'Nanum Square';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';

            ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);
            //console.log('ctx info : ', ctx.canvas.width);
//            ctx.fillText(subTitle, ctx.canvas.width/2, 90);

			if(chartInstance.config.type == "bar") {
				//ctx.globalAlpha = 0.8;
				chartInstance.data.datasets.forEach(function (dataset, i) {
					var meta = chartInstance.getDatasetMeta(i);
					if(!meta.hidden) {
						meta.data.forEach(function(element, index) {
							if(dataset.data[index] > 0) {
	                    		ctx.fillStyle = '#808184';

	                    		// 값
		                        var fontSize = 14;
		                        var fontStyle = 'bold';
		                        var fontFamily = 'Nanum Square';
		                        var dataVal = Number(dataset.data[index]).toFixed(0);


		                        switch(ctx.unit){
		                        case "만원" :
		                        	fontSize = 8;
//		                        	dataVal = Math.floor(dataVal / 10000);
//
//		                        	dataVal += "만원";
		                        	break;
		                        }

		                        ctx.textAlign = 'center';
		                        ctx.textBaseline = 'middle';

		                        var position = element.tooltipPosition();
		                        ctx.font = Chart.helpers.fontString(fontSize, fontStyle, fontFamily);

		                        ctx.fillText(numberWithComma(dataVal), position.x, position.y - fontSize / 2);

		                        function numberWithComma(x){
		                        	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		                        }
	                    	}
						});
					}
				});
				//ctx.globalAlpha = 1;
			}
		}
	});

	/**
	 * 차트 그리기
	 */
	PageObj.prototype.drawChart = function(p) {

		let innerDatas = [];
		let deli = p.deli;
		let groupData = [];
		let graphDatas;


		if(p.innerBorderColors == null ) p.innerBorderColors = p.innerColors;
		// group chart일경우
		if(deli > 0) {
			let tempData = [];
			for(var i = 0; i < deli; i++) {
				for(var j = 0; j < p.labels.length; j++) {
					tempData.push(p.innerDatas[j*deli + i]);
				}
				innerDatas.push({label: p.innerLabels[i], backgroundColor: p.innerColors[i], data: tempData, borderColor : p.innerBorderColors[i]}  );
				tempData = [];
			}
		} else {
			let colors = [];
			if(p.labels.length != p.colors.length) {
				for(var i = 0; i < p.labels.length; i++) {
					colors.push(p.colors[0]);
				}
			} else {
				colors = p.colors;
			}
			innerDatas.push({labels: p.labels, data: p.datas, backgroundColor: colors});
		}

		graphDatas = {
		    labels: p.labels,
		    datasets: innerDatas
		};

//		console.log('graphDatas : ', graphDatas);
		if(!document.getElementById(p.ctx)) {
			console.log('ID NOT FOUND : ', p.ctx);
			return;
		}
		let ctx = document.getElementById(p.ctx).getContext('2d');
		let scales = {
				xAxes:[{
					maxBarThickness : 30
				}],
				yAxes: [{
					ticks: {
						beginAtZero: true,
						callback: function(value, index, values){ // 차트 y축 콤마 표시(소수점이 이하 숫자가 00이면 00을 없애고 콤마를 붙여서 출력한다.)
							let zeroNum = 0;
							value = value.toFixed(2);
							zeroNum = value.substring(value.indexOf('.')+1) * 1;
							if(zeroNum == 0) {
								value *= 1;
								value = value.toFixed(0);
								return $.cs.addComma(value);
							}else {
								return value;
							}
						}
					}
				}]
		};



		//툴팁
		let tooltips = {
			callbacks : {
	    		label : function(tooltipItem, data){ // 차트 그래프에 마우스 커서를 두면 보이는 데이터에 콤마 추가
	    			let value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
	    			let label = data.datasets[tooltipItem.datasetIndex].label;
	    			value = value.toString();

	    			if(p.deli > 0)	{
	    				return label+": "+$.cs.addComma(value);
	    			}
	    			else return $.cs.addComma(value);
	    		}
			}
		}

		if(p.yAxes_notComma == true){
			delete scales.yAxes[0].ticks.callback; // y축에 콤마를 표시하지 않음
			delete tooltips.callbacks; // 툴팁에 콤마를 표시하지 않음
		}

		if(p.scales == false) scales = {}
		ctx.name = p.showText;
		ctx.canvas.name = p.subTitle;
		ctx.unit = p.unit;

		let chartObj = new Chart(ctx, {
			type: p.type,
		    data: graphDatas,
		    options: {
		    	title: {
		    		padding:30,
		            display: false,
		            fontColor: '#00B9AD',
		            fontSize: 18,
		            fontFamily: 'Nanum Square',
		            fontStyle: 'bold',
		            text: [p.title] //[p.title, p.subTitle]
		        },
		        scales: scales,
		        responsive : true,
				maintainAspectRatio : false,
				legend:{
					display: p.legend,
					position:'top'
				},
				layout: {
		            padding: {
		                left: 0,
		                right: 0,
		                top: 0,
		                bottom: 0
		            }
		        },
		        tooltips: tooltips
		    },

		});
		Chart.Legend.prototype.afterFit = function() {
		    this.height = this.height + 15;
		};

		//차트 관리 객체에 차트 추가
		chartObjArr.push(chartObj);
	};

	/** 라인, 바 콤보 차트*/
	PageObj.prototype.drawComboChart = function(p) {

		let innerDatas = [];
		let deli = p.deli;
		let groupData = {};
		let graphDatas;

		// group chart일경우

		for(let i=0; i<p.innerDatas.length; i++){

			innerDatas.push({
							type: p.innerDatas[i].type
							,label: p.innerDatas[i].label
							,data : p.innerDatas[i].data
							,borderColor: p.innerDatas[i].borderColor
							,backgroundColor: p.innerDatas[i].backgroundColor
							,fill : false
							}
			);
		}
		graphDatas = {
				labels: p.labels,
				datasets: innerDatas
		};

		if(!document.getElementById(p.ctx)) {
			console.log('ID NOT FOUND : ', p.ctx);
			return;
		}
		let ctx = document.getElementById(p.ctx).getContext('2d');
		ctx.name = p.showText;
		ctx.canvas.name = p.subTitle;
		let chartObj = new Chart(ctx, {
			type: 'bar',
			data: graphDatas,
			options: {
				title: {
					padding:30,
					display: false,
					fontColor: '#00B9AD',
					fontSize: 18,
					fontFamily: 'Nanum Square',
					fontStyle: 'bold',
					text: [p.title] //[p.title, p.subTitle]
				},
				scales: {
					xAxes:[{
						maxBarThickness : 30
					}],
					yAxes: [{
						ticks: {
							beginAtZero: true,
							callback: function(value, index, values){ // 차트 y축 콤마 표시
								value = value.toString();
								value = value.split(/(?=(?:...)*$)/);
								value = value.join(',');
								return value;
							}
						}
					}]
				},
				responsive : true,
				maintainAspectRatio : false,
				legend:{
					display: p.legend,
					position:'top',
					reverse : true
				},
				layout: {
					padding: {
						left: 0,
						right: 0,
						top: 0,
						bottom: 0
					}
				},
				tooltips: {
		        	callbacks : {
		        		label : function(tooltipItem, data){ // 차트 그래프에 마우스 커서를 두면 보이는 데이터에 콤마 추가
		        			let value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
			    			let label = data.datasets[tooltipItem.datasetIndex].label;
			    			value = value.toString();

		    				return label+": "+$.cs.addComma(value);

		        		}
		        	}
		        }
			}
		});
		//차트 관리 객체에 차트 추가
		chartObjArr.push(chartObj);
	};


	/** 파이 차트*/
	PageObj.prototype.drawDoughnutChart = function(p) {

		let innerDatas = [];
		let deli = p.deli;
		let groupData = {};
		let graphDatas;

		// group chart일경우
		if(deli > 0) {
			let tempData = [];
			for(var i = 0; i < deli; i++) {
				for(var j = 0; j < p.labels.length; j++) {
					tempData.push(p.innerDatas[j*deli + i]);
				}
				innerDatas.push({label: p.innerLabels[i], backgroundColor: p.innerColors[i], data: tempData});
				tempData = [];
			}
		} else {
			let colors = [];
			if(p.labels.length != p.colors.length) {
				for(var i = 0; i < p.labels.length; i++) {
					colors.push(p.colors[0]);
				}
			} else {
				colors = p.colors;
			}
			innerDatas.push({labels: p.labels, data: p.datas, backgroundColor: colors});
		}


		graphDatas = {
				labels: p.labels,
				datasets: innerDatas
		};

		if(!document.getElementById(p.ctx)) {
			console.log('ID NOT FOUND : ', p.ctx);
			return;
		}
		let ctx = document.getElementById(p.ctx).getContext('2d');
		ctx.name = p.showText;
		ctx.canvas.name = p.subTitle;
		let chartObj = new Chart(ctx, {
			type: 'doughnut',
			data: graphDatas,
			options: {
				title: {
					padding:30,
					display: false,
					fontColor: '#00B9AD',
					fontSize: 18,
					fontFamily: 'Nanum Square',
					fontStyle: 'bold',
					text: [p.title] //[p.title, p.subTitle]
				},
				responsive : true,
				maintainAspectRatio : false,
				legend:{
					display: true,
					position:'right',
					align : 'center',
					labels: {
						fontSize : 15,
						fontFamily: 'Nanum Square',
						fontStyle: 'bold',
						padding : 20,
						fontColor : "rgb(0,0,0)"
					}

				},
				layout: {
					padding: {
						left: 0,
						right: 0,
						top: 50,
						bottom: 0
					}
				},
				tooltips: {
					callbacks : { // 도넛 차트에 그래프에 마우스 Hover 시 데이터를 %가 표시되도록 수정
						label : function(tooltipItem, data) {
							let label = data.labels[tooltipItem.index];
							let per = 0;
							let sumData = 0;
							for(let i=0; i<p.datas.length; i++){
								let d = p.datas[i];
								sumData += d*1;
							}
							per = (p.datas[tooltipItem.index] / sumData * 100).toFixed(1);
							label = label.substring(0,label.indexOf("  "));
							label += ' '+per+"%"
							return label;
						}
					}
				}
			}
		});
		//차트 관리 객체에 차트 추가
		chartObjArr.push(chartObj);

	};

	/*전체 차트 없애기*/
	PageObj.prototype.clearChart = function(){
		for(let i=0; i<chartObjArr.length; i++){
			chartObjArr[i].destroy();
		}
	}

	/*재배 품목 차트 없애기*/
	PageObj.prototype.clearCtvtChart = function(){
		for(let i=0; i<chartObjArr.length; i++){
			let chartCanvasId = $(chartObjArr[i].titleBlock.ctx.canvas).attr('id');
			let ctvtCharCanvasIdArr = ['sc_b_0_0_0'
									, 'sc_b_0_1_0'
									, 'sc_b_0_2_0', 'sc_b_0_2_1'
									, 'sc_b_0_3_0', 'sc_b_0_3_1', 'sc_b_0_3_2']

			ctvtCharCanvasIdArr.forEach(function(ctvtCharCanvasId){
				if(ctvtCharCanvasId == chartCanvasId){
					chartObjArr[i].destroy();
				}
			});
		}
	}

});

/*결과화면 순서 객체*/
let ResultViewOrdrObj = (function(){

	ResultViewOrdrObj.prototype.refresh = function(){
		let hopeRdNmAdr = dataObj.form.hopeRdNmAdr; // 희망 귀농지역
		let hopeCtvt = dataObj.form.hopeCtvt; // 희망 재배품목

		if(hopeRdNmAdr == "" && hopeCtvt == "") this.view_1();
		if(hopeRdNmAdr != "" && hopeCtvt != "") this.view_4();
		if(hopeRdNmAdr != "" && hopeCtvt == "") this.view_2();
		if(hopeRdNmAdr == "" && hopeCtvt != "") this.view_3();

	}

	/**
	 * 기본 결과화면
	 *
	 * 	맞춤 귀농 지역 정보
	 *	지역맞춤 귀농교육 및 지원정책
	 *	지역 정주 여건 정보
	 *	귀농 지역 맞춤 품목 정보
	 *	유사귀농인 정보
	 * */
	ResultViewOrdrObj.prototype.view_1 = function(){
		console.log("view_1");

		this.setMenuOrdr(["result02","result05","result03","result04","result01"]);
		this.setDisplayProp(["result02","result05","result03","result04","result01"], "block");
		this.setDisplayProp(["result06"], "none");

		$('.fixesCtvt .tablinks').removeClass('on').css('display', 'block');
		$('.fixesCtvt .tablinks:nth-child(1)').addClass('on').css('display', 'block');


		/*지역 정주 여건 정보에 ~시, ~읍면동 차트 표시되도록 함*/
		$('#result03 .tabcontent .w30p, #result03 .tabcontent .w40p').css('display', 'block');
		$('#result03 .tabcontent .w70p').attr('class', 'w70p');
		$('#result03 .tabcontent .w60p').attr('class', 'w60p');
	}

	/**
	 * 희망 지역 결과화면
	 *
	 *	귀농 지역 맞춤 품목 정보
	 *	지역맞춤 귀농교육 및 지원정책
	 *	지역 정주 여건 정보
	 *	유사귀농인 정보
	 * */
	ResultViewOrdrObj.prototype.view_2 = function(){
		console.log("view_2");
		this.setMenuOrdr(["result04","result05","result03", "result01"]);
		this.setDisplayProp(["result04","result05","result03", "result01"], "block");
		this.setDisplayProp(["result02","result06"], "none");

		$('.fixesCtvt .tablinks').removeClass('on').css('display', 'block');
		$('.fixesCtvt .tablinks:nth-child(1)').addClass('on').css('display', 'block');

		/*지역 정주 여건 정보에 ~시 차트만 표시되도록 함*/
		$('#result03 .tabcontent .w30p, #result03 .tabcontent .w40p').css('display', 'none');
		$('#result03 .tabcontent .w70p').attr('class', 'w100p w70p');
		$('#result03 .tabcontent .w60p').attr('class', 'w100p w60p');
	}

	/**
	 * 희망 재배 품목 결과화면
	 * */
	ResultViewOrdrObj.prototype.view_3 = function(){
		console.log("view_3");

		this.setMenuOrdr(["result02","result05","result03","result04", "result01"]);
		this.setDisplayProp(["result02","result05","result03","result04", "result01"], "block");
		this.setDisplayProp(["result06"], "none");


		//첫번째 추천 재배 작물 태그에 on 클래스 추가
		$('.fixesCtvt .tablinks').removeClass('on').css('display', 'none');
		$('.fixesCtvt .tablinks:nth-child(1)').addClass('on').css('display', 'block');

		/*지역 정주 여건 정보에 ~시, ~읍면동 차트 표시되도록 함*/
		$('#result03 .tabcontent .w30p, #result03 .tabcontent .w40p').css('display', 'block');
		$('#result03 .tabcontent .w70p').attr('class', 'w70p');
		$('#result03 .tabcontent .w60p').attr('class', 'w60p');

	}

	/**
	 * 희망 지역/재배 품목 결과화면
	 * */
	ResultViewOrdrObj.prototype.view_4 = function(){
		console.log("view_4");

		this.setMenuOrdr(["result06","result05","result03","result04"]);
		this.setDisplayProp(["result06","result05","result03","result04"], "block");
		this.setDisplayProp(["result01", "result02"], "none");

		$('.fixesCtvt .tablinks').removeClass('on').css('display', 'none');
		$('.fixesCtvt .tablinks:nth-child(1)').addClass('on').css('display', 'block');

		/*지역 정주 여건 정보에 ~시 차트만 표시되도록 함*/
		$('#result03 .tabcontent .w30p, #result03 .tabcontent .w40p').css('display', 'none');
		$('#result03 .tabcontent .w70p').attr('class', 'w100p w70p');
		$('#result03 .tabcontent .w60p').attr('class', 'w100p w60p');

	}

	/**
	 * 메뉴 순서를 변경함
	 * @param menuIdArr(string)
	 * */
	ResultViewOrdrObj.prototype.setMenuOrdr = function(menuIdArr){
		let beforeMenu = "menu_top";
		for(let i=0; i<menuIdArr.length; i++) {
			let menuId = menuIdArr[i];

			$('#'+menuId).insertAfter("#"+beforeMenu);
			$('#'+menuId+"Nav").insertAfter("#"+beforeMenu+"Nav");

			beforeMenu = menuId;
		} // for

		//첫번째 내비게이션에 click 이벤트를 준다.
		$('.scrollMove:first').trigger('click');
	}

	/**
	 * 메뉴를 display 속성 변경
	 * @param menuIdArr(string)
	 * @param prop(string) - none, block, inline-blick ...
	 * */
	ResultViewOrdrObj.prototype.setDisplayProp = function(menuIdArr, prop){
		let beforeMenu = "menu_top";
		for(let i=0; i<menuIdArr.length; i++) {
			let menuId = menuIdArr[i];

			$('#'+menuId).css('display', prop);
			$('#'+menuId+"Nav").css('display', prop);

			beforeMenu = menuId;
		} // for
	}
})

const pageObj = new PageObj();
const dataObj = new DataObj();
const policySrchObj = new PolicySrchObj();
const resultViewOrdrObj = new ResultViewOrdrObj();
const chartObjArr = [];
$(document).ready(function() {
	pageObj.init();

});
