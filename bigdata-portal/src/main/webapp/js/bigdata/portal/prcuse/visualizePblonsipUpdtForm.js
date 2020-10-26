/**
 * 시각화 공유 수정 폼
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
		//파일 삭제
		$('a.deleteFile').click(function(){
			if(!confirm('삭제하시겠습니까?')){
				return;
			}

			//
			let atchFileId = $(this).siblings('[name=atchFileId]').val();
			let fileNo = $(this).siblings('[name=fileNo]').val();

			let p={};
			p.name = 'deletedFileNo';
			p.value = fileNo;

			//폼에 hidden 추가
			$('#uf').append( $.cs.createElement(p) );

			//화면에서 삭제
			$(this).closest('div').remove();
		});


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

			//파일 업로드
			$.cs.upload($('[type=file]'), './visualizePblonsipUpload.json', function(fileVos){
				console.log(fileVos);

				//
				let p={};
				p.nttId = $('#uf [name=nttId]').val();
				p.bbsGbn = $('#uf [name=bbsGbn]').val();
				p.nttSj = $('#uf [name=nttSj]').val();
				p.nttCn = $('#uf [name=nttCn]').val();

				//삭제된 파일이 있으면
				let arr=[];
				$('#uf [name=deletedFileNo]').each(function(i,item){
					arr.push($(item).val());
				});
				p.deletedFileNo = arr.join(',');

				//첨부된 파일이 있으면
				if(!$.cs.isNull(fileVos) && 0 < fileVos.length){
					for(let i=0; i<fileVos.length; i++){
						p['file['+i+']'] = fileVos[i].fileVo;
					}
				}

				//데이터 전송
				$.cs.submitAjax('./visualizePblonsipUpdt.json', p, function(res){
					alert('수정했습니다.');

					//
					$.cs.submitForm($('#uf'), './visualizePblonsipDetail.do');
				});


			}, {fileExtGbns:['PHOTO']});

			return false;
		});

		//취소
		$('a.cancel').click(function(){
			$.cs.submitForm($('#uf'), './visualizePblonsipDetail.do');
		});
	};
});

//
let pageObj = new PageObj();
$(document).ready(function(){
	pageObj.init();
});