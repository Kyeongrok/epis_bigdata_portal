/**
 * 귀농
 * 내가 입력한 정보 화면
 */

let MyInfoObj = (function(){

	MyInfoObj.prototype.init = function(){
//		console.log(dataObj);

		this.setEventHandler();

	}

	MyInfoObj.prototype.setEventHandler = function(){

		//개인정보 수정 버튼 클릭하면  '본인정보 수정하기' Modal 안의 값을 dataObj의 값으로 변경한다.
		$("#updtInfoBtn").on("click", function(){
			let $updtInfoModal = $("#updtInfoModal");
			$updtInfoModal.find('[name="mvtRdNmAdr"]').val(dataObj.form.mvtRdNmAdr); // 주소 값 반영

			$updtInfoModal.find('[name="selfSexdstn"]:radio[value="'+ dataObj.form.selfSexdstn +'"]').prop('checked',true);
			$updtInfoModal.find('[name="selfAge"]').val(dataObj.form.selfAge); // 나이 값 반영

		});

		//동거가족 수정 버튼 클릭하면  '본인정보 수정하기' Modal 안의 값을 dataObj의 값으로 변경한다.
		$("#updtLivtgtBtn").on("click", function(){
			let $updtLivtgtModal = $("#updtLivtgtModal");

			//동거가족 수 만큼 입력박스 생성
			let html = "";
			const count = (dataObj.form.livtgt.length == 0)?  1: dataObj.form.livtgt.length;
			for(let i=1; i<=count; i++){
				html +=
				'<div class="relateDiv rowWrap">'
			   +'<div class="row">'
               +'     <p class="w20p">관계</p>'
               +'     <div class="w80p">'
               +'         <label><input type="radio" name="'+i+'-relate" value="spouse" ><span>배우자</span></label>'
               +'         <label><input type="radio" name="'+i+'-relate" value="chldrn"><span>자녀</span></label>'
               +'         <label><input type="radio" name="'+i+'-relate" value="parnts"><span>부모</span></label>'
               +'         <label><input type="radio" name="'+i+'-relate" value="empty"><span>없음</span></label>'
               +'     </div>'
               +' </div>'
               +' <div class="row">'
               +'     <p class="w20p">성별</p>'
               +'     <div class="w80p">'
               +'         <label><input type="radio" name="'+i+'-relateSexdstn" value="male"><span>남자</span></label>'
               +'         <label><input type="radio" name="'+i+'-relateSexdstn" value="female"><span>여자</span></label>'
               +'     </div>'
               +' </div>'
               +' <div class="row">'
               +'     <p class="w20p">나이</p>'
               +'     <input type="text" class="w80p" name="'+i+'-relateAge">'
               +' </div>'
			   +' </div>';
			}

			$updtLivtgtModal.find(".rowWrap").html(html);

			//데이터 셋팅
//			console.log(myInfoObj);
			for(let i=1; i<=count; i++){
				$updtLivtgtModal.find('[name="'+i+'-relate"]:radio[value="'+ dataObj.form.livtgt[i-1].relate +'"]').prop('checked',true);
				$updtLivtgtModal.find('[name="'+i+'-relateSexdstn"]:radio[value="'+ dataObj.form.livtgt[i-1].relateSexdstn +'"]').prop('checked',true);
				$updtLivtgtModal.find('[name="'+i+'-relateAge"]').val(dataObj.form.livtgt[i-1].relateAge);
			}

		});

		/**희망귀농지역 수정하기 버튼 클릭 시 이벤트*/
		$("#updtHopeArea").on("click", function(){
			$("#updtHopeAreaModal").find("[name='hopeRdNmAdr']").val(dataObj.form.hopeRdNmAdr);
		});

		/**희망재배품목 수정하기 버튼 클릭 시 이벤트*/
		$("#updtHopeCtvtBtn").on("click", function(){
			$("#updtHopeCtvtModal").find("[name='hopeCtvt']").val(dataObj.form.hopeCtvt);
		});

	}//setEventHandler

	/**
	 * 	DataObj 데이터를 기준으로 결과화면 오른쪽의 'My 내가 입력한 여건입니다.'를 수정
	 * */
	MyInfoObj.prototype.setSideMenuInfo = function(){
		// 본인정보(주소)
		let selfInfoAddr = '<span class="w100p">'+ dataObj.form.mvtCtprvn+' '+ dataObj.form.mvtSigngu +'</span>';
		if(dataObj.form.mvtCtprvn == dataObj.form.mvtSigngu) selfInfoAddr = '<span class="w100p">'+ dataObj.form.mvtSigngu +'</span>';
		let selfInfoSexAge = '<span class="w48p">'+ dataObj.form.selfSexdstnText+'</span>'
							+'<span class="w48p">'+ dataObj.form.selfAge +'세</span>';

		// 동거가족
		let livtgtInfo = "";
		for(let i=0; i<dataObj.form.livtgt.length; i++){
			livtgtInfo +=
			'<div class="relateModalDiv">'
			+'<p class="row">'
			+'<span class="w100p mint" name="'+(i+1)+'-relate">' + (dataObj.form.livtgt[i].relateText == "" ? "　" : dataObj.form.livtgt[i].relateText) +'</span>'
			+'</p>'
			+'<p class="row">'
			+'<span class="w48p name="'+(i+1)+'-relateSexdstn">' + (dataObj.form.livtgt[i].relateSexdstnText == "" ? "　" : dataObj.form.livtgt[i].relateSexdstnText) +'</span>'
			+'<span class="w48p name="'+(i+1)+'-relateAge">' + (dataObj.form.livtgt[i].relateAge == "" ? "" : dataObj.form.livtgt[i].relateAge+'세') +'</span>'
			+'</p>'
			+'</div>'
			;
		}

		// 귀농지역
		let areaBlank = (dataObj.form.hopeRdNmAdr == "" ? "blank" : "");
		let areaInfo =  '<span class="w100p '+ areaBlank +'" name="hopeRdNmAdr" >'+ dataObj.form.hopeCtprvn+' '+ dataObj.form.hopeSigngu +'</span>';

		// 재배품목
		let ctvtBlank = (dataObj.form.hopeCtvt == "" ? "blank" : "");
		let ctvtInfo = '<span class="w100p '+ ctvtBlank +'">'+ dataObj.form.hopeCtvt +'</span>';



		//고려사항 항목 초기화
		let cnsdrDiv = $(".myBox:nth-child(6)");
		cnsdrDiv.find('span').each(function(){
			let cnsdrCd = $(this).attr('data-cd');
			if(cnsdrCd != null){
				$(this).addClass("blank");
			}
		})

		//상위 고려사항(6)
		if(Array.isArray(dataObj.form.upperCnsdr)){
			cnsdrDiv.find("span").each(function(index,item){
				let thisSpan = $(this);
				let cnsdrCd = $(this).attr('data-cd');
				dataObj.form.upperCnsdr.forEach(function(val){
					if(val.idx == cnsdrCd){
						thisSpan.removeClass("mid low blank");
						thisSpan.addClass("hig");

					}
				})
			})//each;
		}

		//중위 고려사항(7)
		if(Array.isArray(dataObj.form.middleCnsdr)){
			cnsdrDiv.find("span").each(function(index,item){
				let thisSpan = $(this);
				let cnsdrCd = $(this).attr('data-cd');
				dataObj.form.middleCnsdr.forEach(function(val){
//				if(dataObj.form.middleCnsdr.find(function(c){return c.idx == cnsdrCd}) != null){
					if(val.idx == cnsdrCd){
						thisSpan.removeClass("hig low blank");
						thisSpan.addClass("mid");
					}
				})
			})//each;
		}


		//하위 고려 사항(8)
		if(Array.isArray(dataObj.form.lowerCnsdr)){
			cnsdrDiv.find("span").each(function(index,item){
				let thisSpan = $(this);
				let cnsdrCd = $(this).attr('data-cd');
				dataObj.form.lowerCnsdr.forEach(function(val){
					if(val.idx == cnsdrCd){
						thisSpan.removeClass("hig mid blank");
						thisSpan.addClass("low");
					}
				})
			})//each;
		}


		$(".myBox:nth-child(2)").find(".row:nth-child(2)").html(selfInfoAddr);
		$(".myBox:nth-child(2)").find(".row:nth-child(3)").html(selfInfoSexAge);
		$(".myBox:nth-child(3)").find(".rowWrap").html(livtgtInfo);
		$(".myBox:nth-child(4)").find(".row").html(areaInfo);
		$(".myBox:nth-child(5)").find(".row").html(ctvtInfo);


	} // setSideMenuInfo


});

const myInfoObj = new MyInfoObj();

$( document ).ready(function(){
	myInfoObj.init();

})