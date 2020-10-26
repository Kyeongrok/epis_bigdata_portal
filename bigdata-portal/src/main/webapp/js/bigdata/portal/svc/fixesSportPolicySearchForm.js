let PageObj = (function(){
	
	PageObj.prototype.init = function(){
		pageObj.setEventHandler();
		
		//
		console.log('<<init');
	};
	
	PageObj.prototype.setEventHandler = function(){
		//검색 엔터
		$.cs.bindEnterKey( $('.frmer[name=searchKeyword],.cpr[name=searchKeyword],.notFrmer[name=searchKeyword]'), function($this,event){
			let gbn = '';
			if($this.hasClass('frmer')){
				gbn = 'frmer';
			}
			if($(this).hasClass('cpr')){
				gbn = 'cpr';
			}
			if($(this).hasClass('notFrmer')){
				gbn = 'notFrmer';
			}
			
			
			//
			$('.'+gbn+'.search').click();
		});
		
		//검색 버튼 클릭
		$('.frmer.search,.cpr.search,.notFrmer.search').click(function(event){
			event.stopPropagation();
			
			//
			let searchGbn='';
			$('button.tablinks').each(function(i,item){
				if($(item).hasClass('active')){
					searchGbn = $(item).data('gbn');
				}
			});
			
			//
			let searchKeyword = '';
			if($('#famer').is(':visible')){
				searchKeyword = $('#famer [name=searchKeyword]').val();
			}
			if($('#lowfirm').is(':visible')){
				searchKeyword = $('#lowfirm [name=searchKeyword]').val();
			}
			if($('#be_famer').is(':visible')){
				searchKeyword = $('#be_famer [name=searchKeyword]').val();
			}
			
			//
			if(-1 != searchKeyword.indexOf('\\')){
				alert('사용할 수 없는 문자가 포함되었습니다. \n확인 후 다시 입력하시기 바랍니다.');
				return;
			}
			
			//
			if($.cs.isEmpty(searchKeyword)){
				alert('검색어를 입력하시기 바랍니다.');
				return;
			}

			//
			let arr = [];
			arr.push({'name':'searchGbn', 'value':searchGbn});
			arr.push({'name':'searchKeyword', 'value':searchKeyword});
			
			//
			$.cs.createFormAndSubmit(arr, './fixesSportPolicySearchList.do');
		});
	};
});

let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});