/**
 * 신규빅데이터셋 신청 폼
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
		//등록
		$('a.regist').click(function(event){
			//필수입력 검사
			let json = $.cs.validateReq($('[data-vreq]'));
			if(!json.b){
				return false;
			}
			
			//
			if(!confirm('등록하시겠습니까?')){
				return false;
			}
			
			//파일 업로드
			$.cs.upload($('[type=file]'), './newBigdatasetReqFileUpload.json', function(fileVos){
				console.log(fileVos);
				
				//
				let p={};
				p.bbsGbn = $('[name=bbsGbn]').val();
				p.nttSj = $('[name=nttSj]').val();
				p.nttCn = $('[name=nttCn]').val();
								
				//
				if(!$.cs.isNull(fileVos) && 0 < fileVos.length){
					for(let i=0; i<fileVos.length; i++){
						p['file['+i+']'] = fileVos[i].fileVo;
					}
				}
				
				//데이터 전송
				$.cs.submitAjax('./registNewBigdatasetReq.json', p, function(res){
					alert('등록했습니다.');
					$('a.cancel').click();
				});
				
				
			}, {fileExtGbns:['OFFICE','TEXT','PHOTO','ZIP']});
			
			return false;
		});
		
		//취소
		$('a.cancel').click(function(){
			location.href = './newBigdatasetReqList.do'
		});
	};
});

//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});