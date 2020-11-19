/**
 * 귀농의사결정 서비스
 */

/**
 * 데이터 객체
 *
 * */
let DataObj = (function(){
	this.form = {
		mvtRdNmAdr : '', // 전출지 주소
		mvtCtprvn : '', // 주소 : 시도
		mvtSigngu : '', // 주소 : 시군구
		mvtEmd : '', // 주소 : 읍면동
		selfSexdstn : '', // 귀농인 성별
		selfSexdstnText : '', // 귀농인 성별
		selfAge : '', // 귀농인 나이
		mvtAdmCd : '', // 전출 행정동코드

		livtgt : [], // 동거인 배열

		hopeRdNmAdr : '', // 귀농희망 주소
		hopeCtprvn : '', // 귀농희망 주소 : 시도
		hopeSigngu : '', // 귀농희망 주소 : 시군구
		hopeEmd : '', // 귀농희망 주소 : 읍면동

		hopeCtvt : '', // 희망재배품목
		srchCtvtList : [], // 검색된 희망재배품목

		upperCnsdr : [], // 상위 고려사항
		middleCnsdr : [], // 중위 고려사항
		lowerCnsdr : [], // 하위 고려사항

	}


	this.relateObj = function(){ // 배열에 들어가야되기 때문에 function으로 정의.
		this.relate = ''; // 동거인과의 관계
		this.relateText = ''; // 동거인과의 관계
		this.relateSexdstn = ''; // 동거인 성별
		this.relateSexdstnText = ''; // 동거인과의 관계
		this.relateAge = ''; // 동거인 나이
	}

	this.cnsdrObj = function(){ // 배열에 들어가야되기 때문에 function으로 정의.
			this.gdn = ''; //상위,중위,하위 고려 사항
			//TODO 고려사항 DB로 뽑아야될거같음.
	}

	this.ctvtList = []; //품목 재배 리스트
	this.cnsdrList = {}; //고려사항 리스트

	DataObj.prototype.init = function(){
	}

})








/**페이지 객체*/
let PageObj = (function(){

	PageObj.prototype.init = function(){
		console.log('init');

		dataObj.init(); // 데이터 객체 초기화

		this.setEvenHandler(); //이벤트 핸들러 등록

	}

	PageObj.prototype.setEvenHandler = function(){

		/**동거가족 중 배우자 라디오 버튼 클릭하면 '본인 성별'을 확인하여 동거가족 배우자 성별을 선택한다.*/
		$(".relateDiv").on("click", "input[type='radio']", function(){
			if($(this).val() == 'spouse'){
				let selfSexdstn = $("#selfDiv [name='selfSexdstn']:checked").val();

				if(selfSexdstn == "male"){
					$(this).closest(".relateDiv").find("[value='female']").prop("checked", true);
				}if(selfSexdstn == "female"){
					$(this).closest(".relateDiv").find("[value='male']").prop("checked", true);
				}
			}
		});

		/**관계없음을 선책하면 성별, 나이가 비활성화 된다*/
		$(".relateDiv").on("click", "input[type='radio']", function(){
			let clickObj = $(this);
			let clickObjName = clickObj.attr('name');
			let name = clickObjName.substring(clickObjName.indexOf('-')+1, clickObjName.length);
			if($(this).val() == 'empty'){
				$(this).closest('.relateDiv').find('.row').each(function(index, item){ // .row
					if(index > 0){
						$(item).find("input[type='radio']").each(function(index, item2){ // 비활성화
							$(item2).prop('checked', false);
							$(item2).closest('label').addClass('disabled');
							$(item2).prop("disabled", "disabled");
						})

						$(item).find("input[type='text']").each(function(index, item2){
							$(item2).prop('readonly',true);
							$(item2).val('');
						});

						$('#addLivtgtBtn').css('display', 'none');
					}
				});
			}else if(name == 'relate'){
				$(this).closest('.relateDiv').find('.row').each(function(index, item){ // .row
					if(index > 0){
						$(item).find("input[type='radio']").each(function(index, item2){ // radio
							$(item2).closest('label').removeClass('disabled');
							$(item2).prop("disabled", "");
						})

						$(item).find("input[type='text']").each(function(index, item2){
							$(item2).prop('readonly',false);
						});

						$('#addLivtgtBtn').css('display', 'block');;
					}
				});
			}
		});

		//가운데 formWrap에 있는 값이 바뀌면 dataObj에 데이터에 셋팅하고 dataObj 기준으로 itemWrap의 값이 바뀌도록 함
		$(".formWrap").on("click keyup blur change", "input", function(){// keyup focus blur
			let formInputTags = $(this).closest(".formWrap");

			let singleObjs = formInputTags.find("#selfDiv, #hopeAreaDiv, #hopeCtvtDiv"); // 현 거주지, 귀농지역, 재배품복
			let arrayObjs = formInputTags.find(".relateDiv"); // 동거가족, 고려사항

			//단일 객체
			singleObjs = singleObjs.find("input");

			//객체 복사
			dataObj.form = $.extend(dataObj.form, pageObj.getDataObj(singleObjs));
//			dataObj.form = Object.assign(dataObj.form, pageObj.getDataObj(singleObjs));	// es6


			//배열 객체
			dataObj.form.livtgt = []; // 배열초기화
			arrayObjs.each(function(index,arrayObj){

				let relateObj = new dataObj.relateObj();
				arrayObj = $(arrayObj).find("input");

//				relateObj = Object.assign(relateObj, pageObj.getDataObj(arrayObj)); // 객체복사(es6)
				$.extend(relateObj, pageObj.getDataObj(arrayObj)); // 객체복사
				dataObj.form.livtgt.push(relateObj); // 동거인 배열에 추가

			});

		});

		PageObj.prototype.updtDataObj = function(){

			$(".formWrap input").first().trigger("blur");
		}

		//결과보기
		$(".showResultBtn").on("click", function(){
			if(!vaildObj.checkSelf.srchAddr()) return false;
			if(!vaildObj.checkSelf.selfSexdstn()) return false;
			if(!vaildObj.checkSelf.selfAge()) return false;
			if(!vaildObj.checkRelate()) return false;

			let datasJson = JSON.stringify(dataObj.form);
			let datas = dataObj.form;


			$.post('./updtRetnFmlgInfo.json', datasJson, function(json){
				console.log(json);
				location.href="./retnFmlgDetail.do"; // 본래 사용할 url

				//모든 input를 초기화한다.
				$("input[type='text']").val('');
				$("input[type='radio']").prop("checked", "");
				$("input[type='checkbox']").prop("checked", "");
			});


		});
	}//setEventHandler


	/**
	 * div 객체에서 input 태그를 찾고 name의 명칭과 value 값에 따라 객체를 만들어 반환함
	 * @param [] arrayObj
	 * @return {Object} { name 태그 명칭 : value 값}
	 *
	 * */
	PageObj.prototype.getDataObj = function(arrayObj){
		let result = {};
		for(let i=0; i<arrayObj.length; i++){
			let inputType = $(arrayObj[i]).attr("type");
			let inputName = $(arrayObj[i]).attr("name");

			if(inputName.indexOf('-') > -1) inputName = inputName.substring(2);

			if(inputType == "text") { //form의 input이 text 타입일 경우
				if(inputName == null) return;
				result[inputName] = $(arrayObj[i]).val();
			}else if(inputType == "radio"){ //form의 input이 radio 타입일 경우
				if($(arrayObj[i]).is(":checked")){
					result[inputName] = $(arrayObj[i]).val();
					result[inputName+"Text"] = $(arrayObj[i]).siblings('span').text();
				}
			}
		}
		return result;
	}





});


let pageObj = new PageObj();
let dataObj = new DataObj();

$( document ).ready(function(){
	pageObj.init();

	$("input [type='text']").focus(function(){
		this.blur();
	});
});
