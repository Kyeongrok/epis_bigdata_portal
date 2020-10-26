/**
 * 모달 창에서 공통으로 사용할 자바스크립트
 */

	/**
	 * 유효성 검사 객체
	 * */

let VaildObj = (function(){
	VaildObj.prototype.checkSelf = function(){}
	VaildObj.prototype.checkSelf.srchAddr = function(){
			if($.trim($("#selfDiv").find("input[name='mvtRdNmAdr']").val()) == ""){
				alert("현거주지 정보를 입력해주세요");
				$("#selfDiv").find("input[name='mvtRdNmAdr']").focus();
				return false;
			}
			return true;
	}

	VaildObj.prototype.checkSelf.selfSexdstn = function(){
		if($.trim($("#selfDiv").find("input[name='selfSexdstn']:checked").length) == 0){
			alert("본인 성별을 선택해주세요");
			$("#selfDiv").find("input[name='selfSexdstn']:checked").focus();
			return false;
		}
		return true;
	}
	VaildObj.prototype.checkSelf.selfAge = function(){
		if($.trim($("#selfDiv").find("input[name='selfAge']").val()) == ""){
			alert("본인 나이를 입력해주세요");
			$("#selfDiv").find("input[name='selfAge']").focus();
			return false;
		}
		return true;
	}

	VaildObj.prototype.checkRelate = function(){
		console.log("click");

		let radioTags = $(".relateDiv input:radio");
//		radioTags = Array.from(radioTags);
		for(let i=0; i<radioTags.length ; i++){
			let name = $(radioTags[i]).attr('name').substring(2);

			switch(name){
			case "relate" :
				if($(".relateDiv input:radio[name='"+$(radioTags[i]).attr('name')+"']:checked").length == 0){
					alert("동거가족 관계를 선택해주세요");
					return false;
				}
				break;
			case "relateSexdstn" :
				if($(".relateDiv input:radio[name='"+$(radioTags[i]).attr('name')+"']:checked").length == 0){
					console.log($(".relateDiv input:radio[value='empty']:checked").length);
					if($(".relateDiv input:radio[value='empty']:checked").length == 0){
						alert("동거가족 성별를 선택해주세요");
						return false;
					}
				}
				break;
			case "relateAge" :
				if($(".relateDiv input:radio[name='"+$(radioTags[i]).attr('name')+"']:checked").length == 0){
					if($(".relateDiv input:radio[value='empty']:checked").length == 0){
						alert("동거가족 나이를 선택해주세요");
						return false;
					}
				}
				break;
			}//switch
		}//for


		let textTags = $(".relateDiv input[type='text']");


		for(let i=0; i<textTags.length; i++){
			let name = $(textTags[i]).attr('name').substring(2);
			if($.trim($(textTags[i]).val()) == "") {
				if($(".relateDiv input:radio[value='empty']:checked").length == 0){
					alert("동거가족 나이을 입력해주세요");
					return false;
				}
			}
		}

		return true;
	}

	VaildObj.prototype.hopeArea = function(){
		if($.trim($("input[name='hopeRdNmAdr']").val()) == ""){
			alert("희망 귀농지역을 입력해주세요");
			return false;
		}
		return true;
	}

	VaildObj.prototype.hopeCtvt = function(){
		if($.trim($("input[name='hopeCtvt']").val()) == ""){
			alert("희망 재배품목을 입력해주세요");
			return false;
		}
		return true;

	}

})

let ModalCommObj = (function(){
	ModalCommObj.prototype.init = function(){
		this.setEventHandler();
	}

	ModalCommObj.prototype.setEventHandler = function(){
		//모달 'X' 버튼 클릭 시 이벤트
		$( document ).on('click', '.layPop .btnClose', function(){
	    	$(this).closest('.layPop').removeClass('on');
	    });

		//모달 팝업 버튼 클릭 시 띄우기
		$( document ).on('click', '.layPopBtn' ,function(){
			let targetModal = $(this).attr("data-target");
			$('#'+targetModal).addClass("on");
		});

	}

})


let modalCommObj = new ModalCommObj();
let vaildObj = new VaildObj();

$(document).ready(function(){
	modalCommObj.init();
});