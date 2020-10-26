/**
 *
 */


/**
 * 자바스크립트 페이징
 */
let ModalPagingObj = (function(modal, rowDatas){
	this.modal = modal;
	this.rowDatas = rowDatas; // 데이터
	this.totalCount = 0; // 주소 검색 데이터 총 개수
	this.currentPage = 1; // 현재 페이지
	this.countPerPage = 10; // 페이지당 출력할 결과 Row 수
	this.currentPageDatas = []; // 현재 페이지에 출력되는 데이터
	this.currentStartIndex = 0;
	this.currentEndIndex = 0;

	this.dataChildTag = "";
	this.pageChildTag = "";

	/** 객체 프로퍼티 초기화 */
	ModalPagingObj.prototype.init = function(){
		console.log(this.modal);

		props = Object.keys(this);
		props.forEach(function(prop){
			if(prop == "modal") return true;
			prop = "";
		});


		this.cleanAddrDatas();

	};

	/**창 크기에 맞게 탑 마진을 재설정 함*/
	ModalPagingObj.prototype.setTopMargin = function(){
		let winHeight = $(window).height();
		let modalHeight = this.modal.find('.popWrp').height();

		let diff = (winHeight - modalHeight) / 2 - 50;
		if(diff < 0) diff = 0;

		this.modal.find('.popWrp').css('top', diff+'px');
	}

	/** 모달 창 안의 데이터 초기화 */
	ModalPagingObj.prototype.cleanAddrDatas = function(){
		this.modal.find('.formBox .rowWrap').empty();
	}

	/** 데이터 셋팅
	 *  모달 창 내부의 하위 태그에 데이터를 셋팅한다.
	 *
	 *  @param {childTag :모달 내부에 데이터를 보여줄 위치, fnInsertHtml : childTag에 해당되는 html 코드를 반환하는 함수 }
	 *
	 * */
//	ModalPagingObj.prototype.setAddrDatas = function({childTag : childTag, fnInsertHtml : fnInsertHtml}){ // es6
	ModalPagingObj.prototype.setAddrDatas = function(obj){
		this.modal.find(obj.childTag).html(obj.fnInsertHtml());
	}

	/** 페이지 리스트 셋팅
	 *  모달 창 내부의 하위 태그에 페이지 정보를 셋팅한다.
	 * */
//	ModalPagingObj.prototype.setPageList = function({childTag : childTag, fnInsertHtml : fnInsertHtml}){ // es6
	ModalPagingObj.prototype.setPageList = function(obj){
		this.modal.find(obj.childTag).html(obj.fnInsertHtml());
	}

	ModalPagingObj.prototype.initPageList = function(){
//		this.totalCount = rowDatas.length;
		this.totalPageCount = Math.ceil(this.totalCount / this.countPerPage); // 총 페이지 개수
		this.currentPageMaxCount = Math.ceil( (this.currentPage / 10 == 0)? 1: this.currentPage / 10 ) * 10 // 현재 페이지가 1일 경우 1~10, 19인 경우 10~19까지 표시하도록 함
		this.currentPageMinCount = Math.floor(this.currentPage / 10 - 0.0001) * 10 + 1; // 현재 페이지가 1일 경우 1~10, 19인 경우 10~19까지 표시하도록 함
		if(this.currentPageMinCount < 1) this.currentPageMinCount = 1;
		if(this.currentPageMaxCount > this.totalPageCount) this.currentPageMaxCount = this.totalPageCount;
	}


	ModalPagingObj.prototype.fnDataInsertHtml = function(){ //아래처럼 사용하면 된다.
		return "";

	}

	ModalPagingObj.prototype.fnPageInsertHtml = function(){
		return "";
//		let pagingHtml = "";
		//
//				let firstPage = 1; // 맨 처음 페이지
//				let prePage = (this.currentPageMinCount-10 > 0) ? this.currentPageMinCount-10 : 1; // 이전 페이지
//				let lastPage = this.totalPageCount; // 맨 마지막 페이지
//				let nextPage = (this.currentPageMaxCount+1 > lastPage) ? lastPage : this.currentPageMaxCount+1; // 다음 페이지
		//
//				//태그 생성 html
//				let aTagFirst = "<a class='first' href='#' onclick='jusoObj.reqAddrLinkApi("+ firstPage +")'><em>처음으로 이동</em></a>";
//				let aTagPre = "<a class='pre' href='#' onclick='jusoObj.reqAddrLinkApi("+ prePage +")' ><em>이전 페이지로 이동</em></a>";
//				let aTagNum = "<span class='num'>";
//				for(let i = pageInfo.currentPageMinCount; i <= pageInfo.currentPageMaxCount; i++) { //페이지 이동 버튼
//					if(pageInfo.currentPage == i){
//						aTagNum += "<strong>"+ i +"</strong>";
//					}else{
//						aTagNum += "<a href='#' onclick='jusoObj.reqAddrLinkApi(" + i + ")'> " + i + "</a>";
//					}
//				}
//				aTagNum += "</span>";
//				let aTagNext = "<a class='next' href='#' onclick='jusoObj.reqAddrLinkApi("+ nextPage +")' ><em>다음 페이지로 이동</em></a>";
//				let aTagLast = "<a class='last' href='#' onclick='jusoObj.reqAddrLinkApi("+ lastPage +")' ><em>마지막 페이지로 이동</em></a>";
		//
//				//페이징 태그 조합
//				pagingHtml += aTagFirst + aTagPre + aTagNum + aTagNext + aTagLast;
		//
//				//페이징 태그 새로고침
//				this.modal.find('.wrap_page').html(pagingHtml);
//		 		return pagingHtml
	}

	ModalPagingObj.prototype.showModalPage = function(){
		this.setAddrDatas({childTag : this.dataChildTag, fnInsertHtml : this.fnDataInsertHtml});
		this.setPageList({childTag : this.pageChildTag, fnInsertHtml : this.fnPageInsertHtml});
		this.setTopMargin();
	}
});



/**
 * 주소 API 객체
 * 코드 정리가 필요하나 3주만에 귀농 구현을 해야되서 그냥 씀.. 2020.02.18
 * */
let JusoObj = (function(){
	this.apiUrl = 'https://www.juso.go.kr/addrlink/addrLinkApi.do'; // 주소 API URL
	this.confmKey = 'U01TX0FVVEgyMDIwMDUxMjE1NDM0MTEwOTc0OTM='; // 승인키(개발용으로 웹 접근이 가능한 환경에서만 사용가능 2020.02.11 김종윤 - 사용기간 : 2020-02-11 ~ 2020-05-11)
	this.totalCount = 0;
	this.currentPage = 1; // 현재 페이지 번호
	this.countPerPage = 5; // 페이지당 출력할 결과 Row 수
//	this.countPerPgaeList = 10; // 한 번에 보여줄 페이지 수( 1~10, 11~20 > 10) //특별히 요청이 없는 한 그냥 주석
	this.resultType = 'json' // 검색 결과 형식(xml, json)
	this.keyword = ''; // 검색어
	this.addrData = []; // 한 페이지에 보여질 주소 데이터(배열)


	/**주소 API 요청*/
	JusoObj.prototype.reqAddrLinkApi = function(currentPage){
		jusoModalObj.cleanAddrDatas(); // 주소 데이터 목록 초기화

		if(typeof currentPage == 'number'){
			this.currentPage = currentPage;
		}
		$.post(this.apiUrl,
				 {"confmKey":this.confmKey, "currentPage":this.currentPage,
					 "countPerPage":this.countPerPage, "resultType":this.resultType,
					 "keyword":this.keyword},
				function(json) {
						 console.log(json);

			this.totalCount = json.results.common.totalCount; // 주소 검색 데이터 총 개수
			this.currentPage = json.results.common.currentPage; // 현재 페이지
			this.countPerPage = json.results.common.countPerPage; // 페이지당 출력할 결과 Row 수
			this.addrDatas = json.results.juso;

			if(json.results.common.errorMessage != '정상'){
				$("#addrModal").removeClass("on");
				alert(json.results.common.errorMessage);
				return false;
			}
			if(this.totalCount == 0) {
				$("#addrModal").removeClass("on");
				alert("주소가 검색되지 않았습니다.");
				return false;
			}


			let totalPageCount = Math.ceil(this.totalCount / this.countPerPage); // 총 페이지 개수
			let currentPageMaxCount = Math.ceil( (this.currentPage / 10 == 0)? 1: this.currentPage / 10 ) * 10 // 현재 페이지가 1일 경우 1~10, 19인 경우 10~19까지 표시하도록 함
			let currentPageMinCount = Math.floor(this.currentPage / 10 - 0.0001) * 10 + 1; // 현재 페이지가 1일 경우 1~10, 19인 경우 10~19까지 표시하도록 함

			if(currentPageMinCount < 1) currentPageMinCount = 1;
			if(currentPageMaxCount > totalPageCount) currentPageMaxCount = totalPageCount;

			// 주소 리스트 표시
			jusoModalObj.setAddrDatas(this.addrDatas);
			jusoModalObj.setPageList({
					totalPageCount : totalPageCount
					, currentPageMinCount : currentPageMinCount
					, currentPageMaxCount : currentPageMaxCount
					, currentPage : this.currentPage});
			jusoModalObj.setTopMargin();


		}.bind(this));
	}


	/**검색어 셋팅*/
	JusoObj.prototype.setKeyword = function(keyword){
		this.keyword = $.trim(keyword);
	}

});


/**
 * 본인정보 수정하기 모달 객체
 * */

let SelfInfoObj = function(){
	SelfInfoObj.prototype.init = function(){
		this.setEventHendler();
	}

	SelfInfoObj.prototype.setEventHendler = function(){
		// 본인정보 수정 버튼 클릭 시
		$("#updtSelfInfoBtn").on("click", function(){
//			console.log(dataObj);
			jusoModalObj.init(); // 초기화
			jusoModalObj.setAddrDataObj();

			//dataObj 값 수정
			dataObj.form.selfSexdstn = $('#updtInfoModal [name="selfSexdstn"]:checked').val();
			dataObj.form.selfSexdstnText = $('#updtInfoModal [name="selfSexdstn"]:checked').siblings('span').text();
			dataObj.form.selfAge = $('#updtInfoModal').find('[name="selfAge"]').val();

			myInfoObj.setSideMenuInfo(); // DataObj 데이터를 기준으로 오른쪽 myAnswer 메뉴 수정
			dataObj.updtDataObj(); // dataObj 객체 업데이트 및 수정된 정보로 다시 조회함.

			$('#updtInfoModal').removeClass("on"); // 모달 창 닫기
		});
	};
}

/**
 * 동거가족 모달 객체
 * */
let LivtgtObj = (function(){
	this.updtModal = $("#updtLivtgtModal");

	LivtgtObj.prototype.init = function(){
		this.setEventHandler();
	}

	LivtgtObj.prototype.setEventHandler = function(){
		/** 입력화면에서 동거가족 추가하기 버튼 클릭 시 실행 */
		$("#addLivtgtBtn").on("click", function(){
			let modalRowWrap = $(this).closest('.popWrp').find('.rowWrap');
			let lastLivgtNameIdx = $('.relateDiv').find('input').last().attr('name').substring(0,1); // 현재 name의 번호를 뽑아서 다음 번호를 입력한다.
			lastLivgtNameIdx *= 1; // 숫자형으로 변환한다.
			lastLivgtNameIdx += 1; //

			let html = '<div class="relateDiv rowWrap">'
			 +'<div class="row">'
		     +'    	<p class="w20p">관계</p>'
		     +'    	<div class="w80p">'
		     +'     	<label><input type="radio" name="'+lastLivgtNameIdx+'-relate" value="spouse"><span>배우자</span></label>'
		     +'     	<label><input type="radio" name="'+lastLivgtNameIdx+'-relate" value="chldrn"><span>자녀</span></label>'
		     +'     	<label><input type="radio" name="'+lastLivgtNameIdx+'-relate" value="parnts"><span>부모</span></label>'
		     +'     	<label><input type="radio" name="'+lastLivgtNameIdx+'-relate" value="empty"><span>없음</span></label>'
		     +'     </div>'
		     +'    </div>'
		     +'    <div class="row">'
		     +'    	<p class="w20p">성별</p>'
		     +'    	<div class="w80p">'
		     +'     	<label><input type="radio" name="'+lastLivgtNameIdx+'-relateSexdstn" value="male"><span>남자</span></label>'
		     +'     	<label><input type="radio" name="'+lastLivgtNameIdx+'-relateSexdstn" value="female"><span>여자</span></label>'
		     +'     </div>'
		     +'    </div>'
		     +'    <div class="row">'
		     +'    	<p class="w20p">나이</p>'
		     +'    	<input type="text" class="w80p" name="'+lastLivgtNameIdx+'-relateAge">'
		     +'    </div>'
		     +'</div>'

			$('.relateDiv').last().after(html); // 모달창에 입력한 값이 복사가 안됨
		});

		/** 입력화면에서 동거가족 삭제하기 버튼 클릭 시 실행 */
		$("#delLivtgtBtn").on('click', function(){
			if($(".relateDiv").length > 1){
				$(".relateDiv:last").remove();
			}
		});

		/** 동거가족 수정 모달화면에서 '수정하기' 버튼 클릭 시 실행*/
		$("#updtLivtgtModalBtn").on("click", function(){
			console.log(dataObj.form.livtgt);
			dataObj.form.livtgt = []; // dataObj의 동거가족 배열 객체 초기화

			/*현재 입력된 동거 가족 정보 불러오기와서 dataObj에 셋팅한다.*/
			console.log(livtgtObj.updtModal.find(".relateDiv"));
			livtgtObj.updtModal.find(".relateDiv").each(function(idx,item){
				console.log(item);
				let radioTags =  $(item).find("input[type='radio']");
				let inputTags =  $(item).find("input[type='text']");
				let relateObj = {};
				for(let i=0; i<radioTags.length; i++){

					//체크된 radio 태그의 값을 dataObj로 셋팅한다.
					if($(radioTags[i]).prop("checked")){
						let name = $(radioTags[i]).prop("name");
						let shortName = name.substring(2);
						let text = $(radioTags[i]).siblings('span').text();
						let value = $(radioTags[i]).val();
						relateObj[shortName] = value;
						relateObj[shortName+"Text"] = text;
					}//if
				}//for

				for(let i=0; i<inputTags.length; i++){
					let name = $(inputTags[i]).prop("name");
					let shortName = name.substring(2);

					//input 태그의 값을 dataObj로 셋팅한다.
					let value = $(inputTags[i]).val();
					relateObj[shortName] = value;
				}


				dataObj.form.livtgt.push(relateObj)
			});


			myInfoObj.setSideMenuInfo(); // DataObj 데이터를 기준으로 결과화면 오른쪽의 'My 내가 입력한 여건입니다.'를 수정
			dataObj.updtDataObj(); // dataObj 객체 업데이트 및 수정된 정보로 다시 조회함.
			livtgtObj.updtModal.removeClass("on");
		});

	} // setEventHandler

})

/**
 * 희망귀농지역 수정 모달 객체
 * */
let HopeAreaObj = (function(){
	this.updtModal = $("#updtHopeAreaModal");

	HopeAreaObj.prototype.init = function(){
		this.setEventHandler();
	}
	HopeAreaObj.prototype.setEventHandler = function(){

		//희망귀농지역 모달창에서 '수정하기' 버튼 클릭 이벤트 시
		$("#updtHopeAreaModalBtn").on("click", function(){
			hopeAreaObj.updtModal.find("input").each(function(idx,item){
				let name = $(item).attr('name');
				let value = $(item).val();
				if(name == "hopeRdNmAdr" && value == "") {
					hopeAreaObj.updtModal.find("input").each(function(idx,item){
						$(item).val("");
					});
				}
				dataObj.form[name] = value;

			});
			myInfoObj.setSideMenuInfo();
			hopeAreaObj.updtModal.removeClass("on"); // 모달 창 닫기
			dataObj.updtDataObj(); // dataObj 객체 업데이트 및 수정된 정보로 다시 조회함.
		})
	}

})


/** 희망 재배 품목 Modal 객체
 *
 * */
let HopeCtvtModalObj = (function(){
	this.modal = $('#hopeCtvtModal');
	this.updtModal = $('#updtHopeCtvtModal');
	this.dataChildTag = ".formBox .rowWrap";
	this.pageChildTag = ".wrap_page";
	this.selectCtvt = "";

	HopeCtvtModalObj.prototype.init = function(){
		this.setEventHandler();
	}
	HopeCtvtModalObj.prototype.setEventHandler = function(){
		/**
		 * 희망 재배 품목 '검색하기' 버튼 클릭 시 이벤트
		 * */
		$(".srchCtvt").on("click", function(){
			let ctvtKeyword = $(this).siblings("input[name='hopeCtvt']").val();
			if(!vaildObj.hopeCtvt()) return false;
			hopeCtvtModalObj.srchCtvt(ctvtKeyword);
		});

		/**
		 * 희망재배 품목 데이터 선택 시 이벤트
		 * */
		$('#hopeCtvtModal').on("click", '.row input', function(){
			let rowObj = $(this).closest('.row');
			rowObj.siblings('div').css('border-color', '#d9d9da');
			rowObj.css('border-color', 'red');
			hopeCtvtModal.selectCtvt = $(this).val();			
		});

		/**
		 * 희망재배 품목 데이터 '등록하기' 버튼 클릭 시 이벤트
		 * */
		$("#setCtvtBtn").on("click",function(){
			let props = Object.keys(hopeCtvtModal);
			let targetTag = hopeCtvtModal.targetTag;
			props.forEach(function(prop){
				if(prop == "prefix" || prop == "modal"){
					return;
				}
				dataObj.form[hopeCtvtModalObj.prefix + prop] = hopeCtvtModalObj[prop];
				$("input[name='"+ hopeCtvtModalObj.prefix + prop+"']").val(hopeCtvtModalObj[prop]);				
			});
			$("input[name='hopeCtvt']").val(hopeCtvtModal.selectCtvt);
			/*console.log("품목 등록!!!")
			console.log($("input[name='hopeCtvt']").val(hopeCtvtModal.selectCtvt));
			dataObj.form.hopeCtvt = hopeCtvtModal.selectCtvt;
			console.log("옵젝!!!")
			console.log(dataObj.form);*/
			$('#hopeCtvtModal').removeClass("on"); // 모달 창 닫기
		})
				
		/**희망재배품목 수정 모달에서 '수정하기' 버튼 클릭시 이벤트*/
		$("#updtHopeCtvtModalBtn").on("click", function(){
			dataObj.form.hopeCtvt = hopeCtvtModalObj.updtModal.find("[name='hopeCtvt']").val();
			myInfoObj.setSideMenuInfo();
			hopeCtvtModalObj.updtModal.removeClass("on");
			console.log(dataObj.form);
			dataObj.updtDataObj(); // dataObj 객체 업데이트 및 수정된 정보로 다시 조회함.
			console.log(dataObj.form);
		});
	} // setEventHandler

	HopeCtvtModalObj.prototype.srchCtvt = function(keyword){
		let jsonData = JSON.stringify({keyword : keyword});
		$.post("./srchCtvt.json", jsonData, function(json){
			dataObj.srchCtvtList = json.ctvtList.datas;
			hopeCtvtModalObj.showCtvtData();
		}.bind(this));

	}

});
    HopeCtvtModalObj.prototype = new ModalPagingObj(this.modal, this.rowDatas); // ModalPagingObj 상속


	HopeCtvtModalObj.prototype.showCtvtData = function(currentPage){
		if(typeof currentPage == 'number') this.currentPage = currentPage;
		else currentPage = 1;
		this.rowDatas = dataObj.srchCtvtList;
		this.totalCount = dataObj.srchCtvtList.length;
		this.showModalPage();
	}

	HopeCtvtModalObj.prototype.fnDataInsertHtml = function(){
		let dataHtml = "";
//		console.log(dataObj.srchCtvtList);
//		console.log(hopeCtvtModalObj.totalCount);

		//전체 데이터에서 해당 페이지에 해당하는 만큼 자르기
		hopeCtvtModalObj.currentStartIndex = (hopeCtvtModalObj.currentPage - 1) * 10;
		hopeCtvtModalObj.currentEndIndex = hopeCtvtModalObj.currentPage * 10;
		hopeCtvtModalObj.currentPageDatas = hopeCtvtModalObj.rowDatas.slice(hopeCtvtModalObj.currentStartIndex, hopeCtvtModalObj.currentEndIndex);

		//데이터 표시
		if(Array.isArray(hopeCtvtModalObj.currentPageDatas)) {
			hopeCtvtModalObj.currentPageDatas.forEach(function(data){
				dataHtml += "<div class='row'>"
					dataHtml += "<input type='text' class='w100p' value='"+ data.prdlstNm +"' readonly onfocus='this.blur()'>"; // 도로명 주소
				dataHtml += "</div>";
			});
		}
		return dataHtml;
	};

	HopeCtvtModalObj.prototype.fnPageInsertHtml = function(){
		hopeCtvtModalObj.initPageList();

		let pagingHtml = "";
		//
				let firstPage = 1; // 맨 처음 페이지
				let prePage = (hopeCtvtModalObj.currentPageMinCount-10 > 0) ? hopeCtvtModalObj.currentPageMinCount-10 : 1; // 이전 페이지
				let lastPage = hopeCtvtModalObj.totalPageCount; // 맨 마지막 페이지
				let nextPage = (hopeCtvtModalObj.currentPageMaxCount+1 > lastPage) ? lastPage : hopeCtvtModalObj.currentPageMaxCount+1; // 다음 페이지

				//태그 생성 html
				let aTagFirst = "<a class='first' href='#' onclick='hopeCtvtModalObj.showCtvtData("+ firstPage +")'><em>처음으로 이동</em></a>";
				let aTagPre = "<a class='pre' href='#' onclick='hopeCtvtModalObj.showCtvtData("+ prePage +")' ><em>이전 페이지로 이동</em></a>";
				let aTagNum = "<span class='num'>";
				for(let i = hopeCtvtModalObj.currentPageMinCount; i <= hopeCtvtModalObj.currentPageMaxCount; i++) { //페이지 이동 버튼
					if(hopeCtvtModalObj.currentPage == i){
						aTagNum += "<strong>"+ i +"</strong>";
					}else{
						aTagNum += "<a href='#' onclick='hopeCtvtModalObj.showCtvtData(" + i + ")'> " + i + "</a>";
					}
				}
				aTagNum += "</span>";
				let aTagNext = "<a class='next' href='#' onclick='hopeCtvtModalObj.showCtvtData("+ nextPage +")' ><em>다음 페이지로 이동</em></a>";
				let aTagLast = "<a class='last' href='#' onclick='hopeCtvtModalObj.showCtvtData("+ lastPage +")' ><em>마지막 페이지로 이동</em></a>";

				//페이징 태그 조합
				pagingHtml += aTagFirst + aTagPre + aTagNum + aTagNext + aTagLast;

//				console.log(pagingHtml);
				//페이징 태그 새로고침
		 		return pagingHtml;
	}



/**
 * 고려사항 모달 객체
 * */
let CnsdrObj = (function(){
	this.modal = $("#cnsdrModal");

	CnsdrObj.prototype.init = function(){
		this.setEventHandler();
		this.setCnsdrList();
	}

	CnsdrObj.prototype.setCnsdrList = function(){
		$.post("./retnFmlgInfo.json", {}, function(json){ // 고려사항
			let html = "<ul>"
			console.log(json,json.cnsdr.data.length);
			let keys = Object.keys(json.cnsdr.data);
			for(let i=1; i<=keys.length; i++){
				html += "<li>";
				html += "<label>";
				html += "<input type='checkbox' name='cnsdrCheckBox' value='"+ i +"'/>";
				html += "<span>"+ json.cnsdr.data[i] +"</span>";
				html += "</label>";
				html += "</li>";

			}
			html += "</ul>";
			$("#cnsdrModal .rowWrap").html(html);

		});
	}

	CnsdrObj.prototype.setEventHandler = function(){
		/**
		 * 입력화면에서 고려사항 항목 클릭 시
		 * */
		$("#cnsdrDiv").on("click",this.updtCheckBox);

		/**
		 * 결과화면 MyAnswer에서 '수정하기'클릭 시
		 * */
		$(".myAnswer .myBox:nth-child(6)").on("click", '.exBox span',this.updtCheckBox);


		/**
		 * 고려사항 모달에서 '등록하기' 버튼 클릭 시
		 * */

		$("#setCnsdrBtn").on("click",function(){
			let checkedBox = $("input[name='cnsdrCheckBox']:checked");
			let modal = cnsdrObj.modal;

			let cnsdrLevel = modal.attr("data-level");
//				checkedBox = Array.from(checkedBox);
				let currentCnsdr = "";
				switch(cnsdrLevel){
				case "U" : dataObj.form.upperCnsdr = [];
						currentCnsdr = $("#cnsdrDiv").find(".row:nth-child(1)");
						break;
				case "M" : dataObj.form.middleCnsdr = [];
						currentCnsdr = $("#cnsdrDiv").find(".row:nth-child(2)");
						break;
				case "L" : dataObj.form.lowerCnsdr = [];
						currentCnsdr = $("#cnsdrDiv").find(".row:nth-child(3)");
						break;

				}

				//현재 입력되어있는 고려사항 초기화
				currentCnsdr.find('dd').each(function(index, item){
					item.remove();
				})

//				checkedBox.forEach(function(data){
				checkedBox.each(function(index,data){

					//info 고려사항 항목 업데이트
					currentCnsdr.find("dl").append("<dd>"+$(data).siblings('span').text()+"</dd>");

					switch(cnsdrLevel){
					case "U" :
								dataObj.form.upperCnsdr.push( {idx : $(data).val(), desc : $(data).siblings('span').text()} ); break;

					case "M" :
								dataObj.form.middleCnsdr.push( {idx : $(data).val(), desc : $(data).siblings('span').text()} ); break;
					case "L" :
								dataObj.form.lowerCnsdr.push( {idx : $(data).val(), desc : $(data).siblings('span').text()} ); break;
					}
				});
			if(typeof myInfoObj != "undefined")  myInfoObj.setSideMenuInfo();  // DataObj 데이터를 기준으로 결과화면 오른쪽의 'My 내가 입력한 여건입니다.'를 수정
			$('#cnsdrModal').removeClass("on"); // 모달 창 닫기
			dataObj.updtDataObj(); // dataObj 객체 업데이트 및 수정된 정보로 다시 조회함.
		});
	} // setEventHandler

	/**
	 *  고려사항 Modal을 열 때 현재 레벨에 맞게끔 고려사항들을 체크하거나 비활성화한다.
	 * */
	CnsdrObj.prototype.updtCheckBox = function(event){
		function disabledTag(data){
			$(data).closest('label').addClass("disabled");
			$(data).prop("disabled", true);
			$(data).prop('checked', false);
		}
		function abledTag(data){
			$(data).closest('label').removeClass("disabled");
			$(data).prop("disabled", false);
			$(data).prop('checked', true);
		}
		function checkCnsdrVal(cnsdrArr, data){
			let result = false;
			//if(dataObj.form.upperCnsdr.find(function(c){return c.idx == $(data).val()}) != null) {
			for(let i=0; i<cnsdrArr.length; i++){
				let c = cnsdrArr[i];
				if(c.idx == $(data).val()) result = true;
			}
			return result;
		}

		let titleSuffix = "고려사항입력";
		let btnTitle = "등록하기";

		let cnsdrLevel = $(event.target).closest('dl').attr("data-level"); //고려사항 레벨(상,중,하 : U,M,L)
		let modal = cnsdrObj.modal;
		if(cnsdrLevel == null){ // myanswer에서 '수정하기'로 실행되면
			titleSuffix = "고려사항수정";
			btnTitle = "수정하기";
			cnsdrLevel = $(event.target).attr("data-level"); //고려사항 레벨(상,중,하 : U,M,L)
		}
		let checkBox = $("input[name='cnsdrCheckBox']");

//		checkBox = Array.from(checkBox);

		modal.attr("data-level", cnsdrLevel); // 모달에 레벨 셋팅

		let title = "";
		if(dataObj.form.upperCnsdr == null) dataObj.form.upperCnsdr = [];
		if(dataObj.form.middleCnsdr == null) dataObj.form.middleCnsdr = [];
		if(dataObj.form.lowerCnsdr == null) dataObj.form.lowerCnsdr = [];


		switch(cnsdrLevel){
		case "U": title += "상위";
		checkBox.each(function(index, data){
				if(checkCnsdrVal(dataObj.form.upperCnsdr, data)) abledTag(data);
				if(checkCnsdrVal(dataObj.form.middleCnsdr, data)) disabledTag(data);
				if(checkCnsdrVal(dataObj.form.lowerCnsdr, data)) disabledTag(data);
			});
					break;
		case "M": title += "중위";
			checkBox.each(function(index, data){
				if(checkCnsdrVal(dataObj.form.upperCnsdr, data)) disabledTag(data);
				if(checkCnsdrVal(dataObj.form.middleCnsdr, data)) abledTag(data);
				if(checkCnsdrVal(dataObj.form.lowerCnsdr, data)) disabledTag(data);
			});
			break;
		case "L": title += "하위";
			checkBox.each(function(index, data){
				if(checkCnsdrVal(dataObj.form.upperCnsdr, data)) disabledTag(data);
				if(checkCnsdrVal(dataObj.form.middleCnsdr, data)) disabledTag(data);
				if(checkCnsdrVal(dataObj.form.lowerCnsdr, data)) abledTag(data);
			});
			break;
		}
		title += titleSuffix;
		modal.find('h3').text(title);
		modal.find("#setCnsdrBtn span").text(btnTitle);
	}

});






/** 주소 Modal 객체
 *  → 페이징 및 주소 데이터 관련
 * */
let JusoModalObj = (function(){
	this.modal = $('#addrModal');
	this.RdNmAdr = ""; // 주소
	this.Ctprvn = ""; // 시,도
	this.Signgu = ""; // 시군구
	this.Emd = ""; // 읍면동
	this.AdmCd = ""; // 행정동코드
	this.prefix = ""; // 전출지 주소인지, 희망지역인지 구분(전출지: mvt, 희망지역: hope)

	/** 객체 프로퍼티 초기화*/
	JusoModalObj.prototype.init = function(){
		props = Object.keys(this);
		props.forEach(function(prop){
			if(prop == "modal") return true;
			prop = "";
		});

		this.setEventHandler();
	}

	JusoModalObj.prototype.setEventHandler = function(){

		// 주소 검색 버튼 클릭 시 주소 검색하기
		$('.srchAddr').on('click', function(){
			let inputTag = $(this).siblings('input');
			if($.trim(inputTag.val()) == ""){
				alert("주소를 입력해 주세요");
				return false;
			}
			let keyword = inputTag.val();
			jusoModalObj.prefix = $(this).attr('data-addrPrefix');
			jusoObj.setKeyword(keyword);
			jusoObj.reqAddrLinkApi();
			// MK 주소 강제 입력, API 사용가능시 삭제 필요
			/*jusoModalObj.RdNmAdr = '서울특별시 강북구 미아동 3-9';
			jusoModalObj.Ctprvn = '서울특별시';
			jusoModalObj.Signgu = '강북구';
			jusoModalObj.Emd = '미아동';
			jusoModalObj.AdmCd = '11305101';
			console.log(jusoModalObj.RdNmAdr);
			console.log(jusoModalObj.AdmCd);*/
			
			if(jusoModalObj.prefix == 'mvt'){
				jusoModalObj.RdNmAdr = '서울특별시 강동구 길동 42';
				jusoModalObj.Ctprvn = '서울특별시';
				jusoModalObj.Signgu = '강동구';
				jusoModalObj.Emd = '길동';
				jusoModalObj.AdmCd = '11740105';
				console.log(jusoModalObj.RdNmAdr);
				console.log("주소!!!");
			}
			if(jusoModalObj.prefix == 'hope'){
				jusoModalObj.RdNmAdr = '경상북도 상주시 사벌국면 덕담리 1008';
				jusoModalObj.Ctprvn = '경상북도';
				jusoModalObj.Signgu = '상주시';
				jusoModalObj.Emd = '사벌국면';
				jusoModalObj.AdmCd = '47250325';
				console.log(jusoModalObj.RdNmAdr);
				console.log("희망지역!!!");
			}
		});

		// 주소 데이터 클릭 시 jusoModalObj 객체 속성에 값을 셋팅한다.
		$("#addrModal").on("click", ".row input", function(){

			//Focus 표시
			let rowObj = $(this).closest('.row');
			rowObj.siblings('div').css('border-color', '#d9d9da');
			rowObj.css('border-color', 'red');

			//데이터 셋팅
//			jusoModalObj.RdNmAdr = $(this).val();
			jusoModalObj.RdNmAdr = $(this).siblings('.ctprvn').val() + ' ' + $(this).siblings('.signgu').val();
			jusoModalObj.Ctprvn = $(this).siblings('.ctprvn').val();
			jusoModalObj.Signgu = $(this).siblings('.signgu').val();
			jusoModalObj.Emd = $(this).siblings('.emd').val();
			jusoModalObj.AdmCd = $(this).siblings('.admCd').val();
		});

		// 주소 등록 버튼 클릭 시
		$("#setAddrBtn").on("click", function(){
			//html input 태그에 값을 셋팅한다.
			let props = Object.keys(jusoModalObj);
			let targetTag = jusoModalObj.targetTag;
			props.forEach(function(prop){
				if(prop == "prefix" || prop == "modal"){
					return;
				}
				dataObj.form[jusoModalObj.prefix + prop] = jusoModalObj[prop];
				$("input[name='"+ jusoModalObj.prefix + prop+"']").val(jusoModalObj[prop]);
			});			
			
			/*if(jusoModalObj.prefix == 'mvt'){
				jusoModalObj.RdNmAdr = '서울특별시 강동구 길동 42';
				jusoModalObj.Ctprvn = '서울특별시';
				jusoModalObj.Signgu = '강동구';
				jusoModalObj.Emd = '길동';
				jusoModalObj.AdmCd = '11740105';
				console.log(jusoModalObj.RdNmAdr);
				console.log("주소!!!");
			}
			if(jusoModalObj.prefix == 'hope'){
				jusoModalObj.RdNmAdr = '경상북도 상주시 사벌국면 덕담리 1008';
				jusoModalObj.Ctprvn = '경상북도';
				jusoModalObj.Signgu = '상주시';
				jusoModalObj.Emd = '사벌국면';
				jusoModalObj.AdmCd = '47250325';
				console.log(jusoModalObj.RdNmAdr);
				console.log("희망지역!!!");
			}	*/		
			
			// MK 주소 강제 입력, API 사용가능시 삭제 필요
			/*jusoModalObj.RdNmAdr = '서울특별시 강북구 미아동 3-9';
			jusoModalObj.Ctprvn = '서울특별시';
			jusoModalObj.Signgu = '강북구';
			jusoModalObj.Emd = '미아동';
			jusoModalObj.AdmCd = '11305101';
			console.log(jusoModalObj.RdNmAdr);
			console.log(jusoModalObj.AdmCd);*/
			
			

			jusoModalObj.init(); // 초기화

			$('#addrModal').removeClass("on"); // 모달 창 닫기
		});



	}

	/*데이터 객체에 주소 셋팅*/
	JusoModalObj.prototype.setAddrDataObj = function(){
		dataObj.form[jusoModalObj.prefix + "RdNmAdr"] = jusoModalObj.RdNmAdr;
		dataObj.form[jusoModalObj.prefix + "Ctprvn"] = jusoModalObj.Ctprvn;
		dataObj.form[jusoModalObj.prefix + "Signgu"] = jusoModalObj.Signgu;
		dataObj.form[jusoModalObj.prefix + "Emd"] = jusoModalObj.Emd;
	}


	/**창 크기에 맞게 탑 마진을 재설정 함*/
	JusoModalObj.prototype.setTopMargin = function(){
		let winHeight = $(window).height();
		let modalHeight = this.modal.find('.popWrp').height();

		let diff = (winHeight - modalHeight) / 2;
		if(diff < 0) diff = 0;

		this.modal.find('.popWrp').css('top', diff+'px');

	}

	JusoModalObj.prototype.cleanAddrDatas = function(){
		this.modal.find('.formBox .rowWrap').empty();
	}

	/** 주소 데이터 셋팅 */
	JusoModalObj.prototype.setAddrDatas = function(addrDatas){
		let dataHtml = "";


		if(Array.isArray(addrDatas)) {
			addrDatas.forEach(function(juso){
				dataHtml += "<div class='row'>"
	//			dataHtml += "<div><p class='road'></p><p class='jibun'></p></div>";
				dataHtml += "<input type='text' class='w100p' value='"+ juso.roadAddrPart1 + " (" + $.trim(juso.emdNm + " " + juso.liNm) + ")"+ "' readonly onfocus='this.blur()'>"; // 도로명 주소
				dataHtml += "<input type='text' class='w100p' value='"+ juso.jibunAddr +  "' readonly onfocus='this.blur()'>"; // 지번 주소
				dataHtml += "<input type='hidden' class='ctprvn w100p' value='"+ juso.siNm +"'>"; // 시,도
				dataHtml += "<input type='hidden' class='signgu w100p' value='"+ juso.sggNm +"'>"; // 시군구
				dataHtml += "<input type='hidden' class='emd w100p' value='"+ juso.emdNm +"'>"; // 동/면
				dataHtml += "<input type='hidden' class='liNm w100p' value='"+ juso.liNm +"'>"; // 리
				dataHtml += "<input type='hidden' class='rnMgtSn w100p' value='"+ juso.rnMgtSn +"'>"; // 도로명 코드
				dataHtml += "<input type='hidden' class='admCd w100p' value='"+ juso.admCd +"'>"; // 행정동 코드
				dataHtml += "</div>";
			});
			this.modal.find('.formBox .rowWrap').html(dataHtml);
		}
	}

	JusoModalObj.prototype.setPageList = function(pageInfo){
		let pagingHtml = "";

		let firstPage = 1; // 맨 처음 페이지
		let prePage = (pageInfo.currentPageMinCount-10 > 0) ? pageInfo.currentPageMinCount-10 : 1; // 이전 페이지
		let lastPage = pageInfo.totalPageCount; // 맨 마지막 페이지
		let nextPage = (pageInfo.currentPageMaxCount+1 > lastPage) ? lastPage : pageInfo.currentPageMaxCount+1; // 다음 페이지

		//태그 생성 html
		let aTagFirst = "<a class='first' href='#' onclick='jusoObj.reqAddrLinkApi("+ firstPage +")'><em>처음으로 이동</em></a>";
		let aTagPre = "<a class='pre' href='#' onclick='jusoObj.reqAddrLinkApi("+ prePage +")' ><em>이전 페이지로 이동</em></a>";
		let aTagNum = "<span class='num'>";
		for(let i = pageInfo.currentPageMinCount; i <= pageInfo.currentPageMaxCount; i++) { //페이지 이동 버튼
			if(pageInfo.currentPage == i){
				aTagNum += "<strong>"+ i +"</strong>";
			}else{
				aTagNum += "<a href='#' onclick='jusoObj.reqAddrLinkApi(" + i + ")'> " + i + "</a>";
			}
		}
		aTagNum += "</span>";
		let aTagNext = "<a class='next' href='#' onclick='jusoObj.reqAddrLinkApi("+ nextPage +")' ><em>다음 페이지로 이동</em></a>";
		let aTagLast = "<a class='last' href='#' onclick='jusoObj.reqAddrLinkApi("+ lastPage +")' ><em>마지막 페이지로 이동</em></a>";

		//페이징 태그 조합
		pagingHtml += aTagFirst + aTagPre + aTagNum + aTagNext + aTagLast;

		//페이징 태그 새로고침
		this.modal.find('.wrap_page').html(pagingHtml);
	}

});

let ModalPageObj = (function(){
	ModalPageObj.prototype.init = function(){
		jusoModalObj.init();
		selfInfoObj.init();
		livtgtObj.init();
		hopeAreaObj.init();
		hopeCtvtModalObj.init();
		cnsdrObj.init();
	}
});

/* MK Input Box 엔터키 입력시 이벤트 실행 추가 */
$("input[type=text]").keydown(function(e){
	if(e.keyCode==13){		
	console.log("팝팝!!!");
		if($(this).attr("name") == 'mvtRdNmAdr'){
			console.log("주소!!!");
			$("#mvtSrchAddr").trigger("click");
		}
		if($(this).attr("name") == 'hopeRdNmAdr'){
			console.log("희망지역!!!");
			$("#hopeSrchAddr").trigger("click");			
		}
		if($(this).attr("name") == 'hopeCtvt'){
			console.log("희망품목!!!");
			$("#srchCtvt").trigger("click");
		}		
	}
});


let jusoObj = new JusoObj();
let jusoModalObj = new JusoModalObj();
let selfInfoObj = new SelfInfoObj();
let livtgtObj = new LivtgtObj();
let hopeAreaObj = new HopeAreaObj();
let hopeCtvtModalObj = new HopeCtvtModalObj();
let cnsdrObj = new CnsdrObj();
let modalPageObj = new ModalPageObj();


$( document ).ready(function(){
	modalPageObj.init();
})