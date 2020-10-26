/**
 * 분석대행요청 view
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
		//수정
		$('a.updt').click(function(){
			$.cs.submitForm($('#df'), './analysisReqUpdtForm.do');
		});
		
		//삭제
		$('a.delete').click(function(){
			if(!confirm('삭제하시겠습니까?')){
				return;
			}
			
			//
			let p = {};
			p.nttId = $('#df [name=nttId]').val();
			
			//
			$.cs.submitAjax('./analysisReqDelete.json', p, function(res){
				$('a.list').click();
			});
		});
		
		//목록
		$('a.list').click(function(){
			location.href = './analysisReqList.do'
		});
		
		//댓글
		$('a.rply').click(function() {
			let json = $.cs.validateReq($('#df [name=rplyCn]'));
			if(!json.b) {
				return false;
			}
			if(!confirm('댓글을 등록하시겠습니까?')) {
				return;
			}
			
			let p = {};
			p.nttId = $('#df [name=nttId]').val();
			p.nttNo= $('#df [name=nttNo]').val();
			p.bbsGbn = $('#df [name=bbsGbn]').val();
			p.rplyCn = $('#df [name=rplyCn]').val();
			$.cs.submitAjax('./analysisReqRply.json', p, function(res) {
				location.href = './analysisReqView.do?nttId=' + p.nttId;  
			});
		});
	};
});

//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});