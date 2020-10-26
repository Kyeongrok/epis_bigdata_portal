/**
 * 시각화 공유 목록
 */
let PageObj = (function(){
	
	/**
	 * 초기화
	 */
	PageObj.prototype.init = function(){
		pageObj.setEventHandler();
	};
	
	/**
	 * 이벤트 등록
	 */
	PageObj.prototype.setEventHandler = function(){
		//상세
		$('a.detail').click(function(event){
			let arr=[];
			arr.push({'name':'nttId', 'value' : $(this).siblings('[name=nttId]').val()});
			arr.push({'name':'nttNo', 'value' : $(this).siblings('[name=nttNo]').val()});
			arr.push({'name':'searchCondition', 'value' : $('[name=searchCondition]').val()});
			arr.push({'name':'searchKeyword', 'value' : $('[name=searchKeyword]').val()});
			arr.push({'name':'pageIndex', 'value' : $('[name=pageIndex]').val()});
			
			//
			let $f = $.cs.createForm(arr);
			$('body').append($f);
			
			//
			$f.prop('method', 'post')
				.prop('action', './myDataView.do');
			
			//이동
			$('body form:last').submit();
			return false;
		});
	};
});

//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});