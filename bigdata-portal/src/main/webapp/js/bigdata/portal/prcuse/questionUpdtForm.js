/**
 * 문의게시판 수정 폼
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
		$('a.updt').click(function(event){
			//필수입력 검사
			let json = $.cs.validateReq($('[data-vreq]'));
			if(!json.b){
				return false;
			}
			
			//
			if(!confirm('수정하시겠습니까?')){
				return false;
			}
			
			//
			let p={};
			p.nttId = $('#uf [name=nttId]').val();
			p.bbsGbn = $('#uf [name=bbsGbn]').val();
			p.nttSj = $('#uf [name=nttSj]').val();
			p.nttCn = $('#uf [name=nttCn]').val();
			
			//데이터 전송
			$.cs.submitAjax('./questionUpdt.json', p, function(res){
				alert('수정했습니다.');
				location.href = './questionView.do?nttId='+ $('#uf [name=nttId]').val();
			});
			
			return false;
		});
		
		//취소
		$('a.cancel').click(function(){
			location.href = './questionView.do?nttId='+ $('#uf [name=nttId]').val();
		});
	};
});

//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});