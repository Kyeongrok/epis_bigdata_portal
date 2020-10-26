/**
 * 시각화 공유 조회
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
			$.cs.submitForm($('#df'), './visualizePblonsipUpdtForm.do');
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
			$.cs.submitAjax('./visualizePblonsipDelete.json', p, function(res){
				$('a.list').click();
			});
		});

		//목록
		$('a.list').click(function(){
			$.cs.submitForm($('#sf'), './visualizePblonsipList.do');
		});
	};
});

//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});