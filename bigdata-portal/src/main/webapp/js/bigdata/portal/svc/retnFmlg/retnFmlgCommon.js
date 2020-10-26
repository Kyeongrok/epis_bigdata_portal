/**
 * 귀농 공통 자바스크립트
 */


let CommonObj = (function(){

	CommonObj.prototype.init = function(){
		this.setEventHandler();
	}

	CommonObj.prototype.setEventHandler = function(){

		/**
		 * 맞춤 지원정책 검색
		 * */
		$("#fixesSportSrchBtn").on("click", function(){
			let $fixesSportSrchForm = $("#fixesSportSrchForm");


			window.open("","fixesSportSrchPop");

//			$fixesSportSrchForm.append("<input type='hidden' name='searchGbn' value='frmer'/>");
//			$fixesSportSrchForm.append("<input type='hidden' name='searchKeyword' value='"+ $("#fixesSportSrchKwrd").val() +"'/>");
//			$fixesSportSrchForm.append("<input type='hidden' name='searchSportRelmCode' value='귀농/귀촌/창업'/>");


			$("input[name='searchKeyword']").val($("#fixesSportSrchKwrd").val());


			$fixesSportSrchForm.attr('action','/bdp/svc/fixesSportPolicySearchList.do')
							   .attr('method','POST')
							   .attr('target','fixesSportSrchPop')
							   .submit()
							   ;


//			let arr = [
//				{'name':'searchGbn', 'value':'frmer'},
//				{'name':'searchKeyword', 'value': $("#fixesSportSrchKwrd").val()},
//				{'name':'searchSportRelmCode', 'value': '귀농/귀촌/창업'}
//				];
//			$.cs.createFormAndSubmit(arr, '/bdp/svc/fixesSportPolicySearchList.do');
		});
	}
});

const commonObj = new CommonObj();

$( document ).ready(function(){
	commonObj.init();
});