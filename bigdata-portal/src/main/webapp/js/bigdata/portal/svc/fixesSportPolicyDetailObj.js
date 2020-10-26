/**
 * 사업 상세
 */
let DetailObj = (function(){
	
	DetailObj.FRMER = "frmer";
	DetailObj.CPR = "cpr";
	DetailObj.NOT_FRMER = "notFrmer";
	
	DetailObj.getString = function(obj, defaultValue){
		if(isNaN(obj)){
			return defaultValue;
		}
		
		return (99999 > parseInt(obj) ? obj : defaultValue);
	};
	
	/**
	 * init
	 */
	DetailObj.prototype.init = function(){
		
		detailObj.setEventHandler();
		
		//
		console.log('<<DetailObj.init');
		
	};
	
	/**
	 * 이벤트 핸들러 등록
	 */
	DetailObj.prototype.setEventHandler = function(){
		//뒤로
		$('.detail .btnList').click(function(){
			$('.table_area.detail,.table_area02.detail').hide();
			
			$('.table_area.list').show();
			
			//
			$('.widget_area').show();
		});
		
		//지침서 다운로드
		$('.btnDwld').click(function(){
			if(!confirm('지침서를 내려받기 하시겠습니까?')){
				return;
			}
			
			//
			let p = '';
			p += '?lclasNmCode=' + $('.detail.bsnsSumry [name=lclasNmCode]').val();
			p += '&mlsfcNmCode=' + $('.detail.bsnsSumry [name=mlsfcNmCode]').val();
			
			//
			location.href = './dwldManual.do' + p;
		});
	};
	
	
	/**
	 * 데이터 조회 & 화면 표시
	 * @param gbn 구분(all/frmer/cpr/notFrmer)
	 * @param mlsfcNmCode 세부 사업 코드
	 * @param bsnsCode 아그릭스 사업코드
	 */
	DetailObj.prototype.getAndShowData = function(gbn, mlsfcNmCode, bsnsCode){
		detailObj.getDataAsync(gbn, mlsfcNmCode, bsnsCode, detailObj.showData);
		
		//
		if('N' === bsnsCode || $.cs.isEmpty(bsnsCode)){
			return;
		}
		
		//
		$('div.stats').show();
		
		//년도별 차트
		$('.stats.byYear').html('<canvas id="chartByYear" width="350" height="300" ></canvas>');
		statsObj.drawChartByYear(bsnsCode, 'chartByYear');
		
		//연령대별 차트
		$('.stats.byAge').html('<canvas id="chartByAge" width="350" height="300" ></canvas>');
		statsObj.drawChartByAge(bsnsCode, 'chartByAge');
		
		//시도별 차트
		$('.stats.bySigungu').hide();
		$('.stats.bySido').html('<canvas id="chartBySido" width="350" height="300" ></canvas>');
		statsObj.drawChartBySido(bsnsCode, 'chartBySido');
		
		//품목군별 차트
		$('.stats.byPrdlstSet').html('<canvas id="chartByPrdlstSet" width="350" height="300" ></canvas>');
		statsObj.drawChartByPrdlstSet(bsnsCode, 'chartByPrdlstSet');
		
	};
	
	
	/**
	 * 데이터 조회
	 * @param gbn 구분(frmer/cpr/notFrmer)
	 * @param mlsfcNmCode 세부 사업 코드
	 * @param callbackFunction 콜백 함수
	 */
	DetailObj.prototype.getDataAsync = function(gbn, mlsfcNmCode, bsnsCode, callbackFunction){
		console.log('>>DetailObj.getDataAsync');
		
		//
		let arr = [{'name':'mlsfcNmCode', 'value':mlsfcNmCode+''}];
		arr.push({'name':'bsnsCode', 'value':bsnsCode+''});
		
		//
		$.cs.submitAjax('./getFixesSportPolicyDatasByMlsfcNmCode.json', arr, function(res){
			console.log('<<DetailObj.getDataAsync');
			
			//
			if(0 < res.datas.length){
				callbackFunction(gbn, res.datas);
			}
			
		});
	};
	
	
	/**
	 * 데이터 표시
	 * @param gbn 구분(frmer/cpr/notFrmer)
	 * @param datas 데이터 목록 
	 */
	DetailObj.prototype.showData = function(gbn, datas){
		if(0 == datas.length){
			return;
		}
		
		
		let d = datas[0]._source;
		
		//
		$('.list').hide();
		$('.detail.back').show();
		
		//
		detailObj.showBsnsSumry(d);
		
		//
		detailObj.showSportCndFrmer(gbn, d);
		detailObj.showSportCndCpr(gbn, d);
		detailObj.showSportCndNotFrmer(gbn, d);
		
		//
		detailObj.showSportCn(d);
		detailObj.showPvltrtCnd(d);
		//기타 필수 요건
		detailObj.showEtcEssntlCond(d);
		//기타 제한 요건
		detailObj.showEtcLmttCond(d);
		//담당자
		detailObj.showContact(d);
		
		//배경색 odd,even 처리
		let css = 'table_area';
		$('.detail').each(function(i,item){
			if($(item).is(':visible')){
				$(item).removeClass('table_area table_area02');
				$(item).addClass(css);
				
				//
				css = ('table_area' === css ? 'table_area02' : 'table_area');
			}
		});
		
		//맨 마지막꺼는 table_area이어야 함
		$('.detail:last').removeClass('table_area02').addClass('table_area');
		
		
	};
	
	
	/**
	 *  사업 개요
	 */
	DetailObj.prototype.showBsnsSumry = function(data){
		//
		let getSportTarget = function(data){
			let arr = [];
			
			//
			if('Y' === data.FMER_REQST){
				arr.push('농업인');
			}
			if('Y' === data.CO_REQST){
				arr.push('법인');
			}
			if('Y' === data.NM_REQST){
				arr.push('비농업인');
			}
			//
			return arr.join(',');
		};
		
		
		$('.detail.bsnsSumry [name=lclasNmCode]').val(data.LCLAS_NM_CODE);
		$('.detail.bsnsSumry [name=mlsfcNmCode]').val(data.MLSFC_NM_CODE);
		$('.detail.bsnsSumry [name=bsnsCode]').val(data.BSNS_CODE);
		$('.detail.bsnsSumry .MLSFC_NM').html(data.MLSFC_NM);
		$('.detail.bsnsSumry .DETAIL_BSNS_NM').html(('N' !== data.DETAIL_BSNS_NM ? '('+data.DETAIL_BSNS_NM+')':''));
		$('.detail.bsnsSumry .SPORT_SUB').html(data.SPORT_SUB);
		$('.detail.bsnsSumry .sportTarget').html( getSportTarget(data) );
		$('.detail.bsnsSumry .SPORT_STLE').html(data.SPORT_STLE);
		$('.detail.bsnsSumry .SPORT_AREA').html(data.SPORT_AREA);
		$('.detail.bsnsSumry .BSNS_CN').html(data.BSNS_CN);
		
		//
		let arr = [];
		arr.push({'name':'lclasNmCode', 'value':data.LCLAS_NM_CODE});
		arr.push({'name':'mlsfcNmCode', 'value':data.MLSFC_NM_CODE});
		
		//
		$('.btnDwld').show();
		
		//지침서 파일이 존재하는지 여부
		$.cs.submitAjaxSync('./getManualData.json', arr, function(res){
			if($.cs.isNull(res.data) || $.cs.isEmpty(res.data.LCLAS_NM_CODE)){
				$('.btnDwld').hide();
			}
		});
		
		//
		$('.detail.bsnsSumry').show();
	};
	
	/**
	 * 지원조건 농업인
	 * @param gbn
	 * @param data
	 */
	DetailObj.prototype.showSportCndFrmer = function(gbn, data){
		let getArStnd = function(fmerArStnd){
			let arr = [];
			
			if(-1 != fmerArStnd.indexOf('1001')){
				arr.push('논');
			}
			if(-1 != fmerArStnd.indexOf('1002')){
				arr.push('밭');
			}
			if(-1 != fmerArStnd.indexOf('1003')){
				arr.push('과수원');
			}
			//
			return arr;
		}
		
		if(DetailObj.FRMER !== gbn || 'Y' != data.FMER_REQST){
			$('.detail.sportCnd .frmer').hide();
			return;
		}
		
		//
		let s = '';
		
		//
		if('Y' === data.FMER_AGE){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>연령</dt>';
			s += '		<dd>' + DetailObj.getString(data.FMER_AGE_MIN, '') + ' ~ ' + DetailObj.getString(data.FMER_AGE_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.FMER_SEX){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>성별</dt>';
			s += '		<dd>' + ('Y' === data.FMER_SEX_M ? '남자':'여자') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.FMER_RESIDE_AREA){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>거주지</dt>';
			s += '		<dd>' + data.FMER_RESIDE_AREA + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.FMER_ATMNENT_ESSNTL_AT){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>경영체등록</dt>';
			s += '		<dd>' + data.FMER_ATMNENT_ESSNTL_AT + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' !== data.FMER_ATMNENT_ESSNTL_PD_MIN || 'N' !== data.FMER_ATMNENT_ESSNTL_PD_MAX){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>경영체등록 기간(년)</dt>';
			s += '		<dd>' + DetailObj.getString(data.FMER_ATMNENT_ESSNTL_PD_MIN, '') + ' ~ ' + DetailObj.getString(data.FMER_ATMNENT_ESSNTL_PD_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.FMER_MNT_PD){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>농업 경영 기간(년)</dt>';
			s += '		<dd>' + DetailObj.getString(data.FMER_MNT_PD_MIN, '') + ' ~ ' + DetailObj.getString(data.FMER_MNT_PD_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.FMER_CTVT_PRDLST_GRP){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>재배(사육)품목군</dt>';
			s += '		<dd>';
			s += '			<span class="dots" title="' + data.FMER_CTVT_PRDLST_GRP + '">' + data.FMER_CTVT_PRDLST_GRP + '</span>';
			s += '		</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.FMER_CTVT_PRDLST_NM){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>재배(사육)품목</dt>';
			s += '		<dd>';
			s += '			<span class="dots" title="' + data.FMER_CTVT_PRDLST_NM + '">' + data.FMER_CTVT_PRDLST_NM + '</span>';
			s += '		</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.FMER_TYPE_CTVT){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>재배유형</dt>';
			s += '		<dd>' + data.FMER_TYPE_CTVT + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.FMER_CTVT_AR){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>재배면적(㎡)</dt>';
			s += '		<dd>' + DetailObj.getString(data.FMER_CTVT_AR_MIN,'') + ' ~ ' + DetailObj.getString(data.FMER_CTVT_AR_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.FMER_AR_OWN_PD){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>농지 소유 기간(년)</dt>';
			s += '		<dd>' + DetailObj.getString(data.FMER_AR_OWN_PD_MIN, '') + ' ~ ' + DetailObj.getString(data.FMER_AR_OWN_PD_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.FMER_AR_USE_PD){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>농지 이용 기간(년)</dt>';
			s += '		<dd>' + DetailObj.getString(data.FMER_AR_USE_PD_MIN, '') + ' ~ ' + DetailObj.getString(data.FMER_AR_USE_PD_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.FMER_AR_STND){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>농지 기준</dt>';
			s += '		<dd>' + getArStnd(data.FMER_AR_STND) + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.FMER_SALE_AMNT){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>연간 판매 금액(백만원)</dt>';
			s += '		<dd>' + DetailObj.getString(data.FMER_SALE_AMNT_MIN, '') + ' ~ ' + DetailObj.getString(data.FMER_SALE_AMNT_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		
		//
		if($.cs.isEmpty(s)){
			return;
		}
    
		//
		$('.detail.sportCnd .frmer ul')
			.html(s)
			.parent()
			.show()
			.parent()
			.show();
	};
	
	/**
	 * 지원조건 법인
	 * @param gbn
	 * @param data
	 */
	DetailObj.prototype.showSportCndCpr = function(gbn, data){
		if(DetailObj.CPR !== gbn || 'Y' != data.CO_REQST){
			$('.detail.sportCnd .cpr').hide();
			return;
		}
		
		
		
		//
		let s = '';
		
		//
		if('Y' === data.CO_CAPITAL){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>자본금(백만원)</dt>';
			s += '		<dd>' + DetailObj.getString(data.CO_CAPITAL_MIN, '') + ' ~ ' + DetailObj.getString(data.CO_CAPITAL_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.CO_EQ_CAPTIAL_STND){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>자기자본 기준</dt>';
			s += '		<dd>' + data.CO_EQ_CAPTIAL_STND + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.CO_INVST_NUM){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>출자자 수(명)</dt>';
			s += '		<dd>' + data.CO_INVST_NUM_MIN + ' ~</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.CO_OPRT_PD){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>법인 운영 기간(년)</dt>';
			s += '		<dd>' + DetailObj.getString(data.CO_OPRT_PD_MIN, '') + ' ~ ' + DetailObj.getString(data.CO_OPRT_PD_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		s += '<li>';
		s += '	<dl>';
		s += '		<dt>대기업 제한 여부</dt>';
		s += '		<dd>' + data.CO_BIGCOM_RSTCT + '</dd>';
		s += '	</dl>';
		s += '</li>';
		if('N' != data.CO_HAND_CTVT_GRP){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>취급품목군</dt>';
			s += '		<dd>';
			s += '			<span class="dots" title="'+data.CO_HAND_CTVT_GRP+'">' + data.CO_HAND_CTVT_GRP + '</span>';
			s += '		</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.CO_HAND_CTVT){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>취급품목</dt>';
			s += '		<dd>';
			s += '			<span class="dots" title="' + data.CO_HAND_CTVT + '">' + data.CO_HAND_CTVT + '</span>';
			s += '		</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.CO_PURCHS_SCL){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>매입규모(톤)</dt>';
			s += '		<dd>' + DetailObj.getString(data.CO_PURCHS_SCL_MIN, '') + ' ~ ' + DetailObj.getString(data.CO_PURCHS_SCL_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.CO_PURCHS_SCL_PD){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>매입규모 평균 적용 기간(년)</dt>';
			s += '		<dd>' + data.CO_PURCHS_SCL_PD + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.CO_SALE_SCL){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>판매 규모(톤)</dt>';
			s += '		<dd>' + DetailObj.getString(data.CO_SALE_SCL_MIN, '') + ' ~ ' + DetailObj.getString(data.CO_SALE_SCL_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' != data.CO_SALE_SCL_PD){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>판매규모 평균 적용 기간(년)</dt>';
			s += '		<dd>' + data.CO_SALE_SCL_PD + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.CO_SALE_AMNT){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>매출액(백만원)</dt>';
			s += '		<dd>' + DetailObj.getString(data.CO_SALE_AMNT_MIN, '') + ' ~ ' + DetailObj.getString(data.CO_SALE_AMNT_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.CO_HAND_AMNT){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>취급액(백만원)</dt>';
			s += '		<dd>' + DetailObj.getString(data.CO_HAND_AMNT_MIN, '') + ' ~ ' + DetailObj.getString(data.CO_HAND_AMNT_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.CO_FRMHS_SCL){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>참여 농가 규모(명)</dt>';//참여 농가 규모
			s += '		<dd>' + DetailObj.getString(data.CO_FRMHS_SCL_MIN,'') + ' ~ ' + DetailObj.getString(data.CO_FRMHS_SCL_MAX,'') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.CO_FAM_SCL){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>생산 규모(ha)</dt>';//참여농지 규모
			s += '		<dd>' + DetailObj.getString(data.CO_FAM_SCL_MIN,'') + ' ~ ' + DetailObj.getString(data.CO_FAM_SCL_MAX,'') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' !== data.CO_SPORT_AMNT_MIN || 'N' !== data.CO_SPORT_AMNT_MAX){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>지원 금액(백만원)</dt>';
			s += '		<dd>' + DetailObj.getString(data.CO_SPORT_AMNT_MIN,'') + ' ~ ' + DetailObj.getString(data.CO_SPORT_AMNT_MAX, '') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
//		지원내용으로 이동
//		if('N' != data.RPQS_ECT){
//			s += '<li>';
//			s += '	<dl>';
//			s += '		<dt>기타 필수 요건</dt>';
//			s += '		<dd>' + data.RPQS_ECT + '</dd>';
//			s += '	</dl>';
//			s += '</li>';
//		}
//		if('N' != data.RSTCT_ECT){
//			s += '<li>';
//			s += '	<dl>';
//			s += '		<dt>기타 제한 요건</dt>';
//			s += '		<dd>' + data.RSTCT_ECT + '</dd>';
//			s += '	</dl>';
//			s += '</li>';
//		}
		
		//
		if($.cs.isEmpty(s)){
			return;
		}
		
		//
		$('.detail.sportCnd .cpr ul')
			.html(s)
			.parent()
			.show()
			.parent()
			.show();
	};
	
	/**
	 *  비농업인
	 *  @param gbn
	 *  @param data
	 */
	DetailObj.prototype.showSportCndNotFrmer = function(gbn, data){
		if(DetailObj.NOT_FRMER !== gbn || 'Y' !== data.NM_REQST){
			$('.detail.sportCnd .notFrmer').hide();
			return;
		}
		
		//
		let s = '';
		
		//
		if('Y' === data.NM_AGE){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>연령</dt>';
			s += '		<dd>' + DetailObj.getString(data.NM_AGE_MIN,'') + ' ~ ' + DetailObj.getString(data.NM_AGE_MAX,'') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('Y' === data.NM_SEX){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>연령</dt>';
			s += '		<dd>' + ('Y' === data.NM_SEX_M?'남자':'여자') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' !== data.NM_RESIDE_AREA){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>거주지</dt>';
			s += '		<dd>' + data.NM_RESIDE_AREA + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		if('N' !== data.NM_SPORT_AMNT_MIN || 'N' !== data.NM_SPORT_AMNT_MAX){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>지원 금액(백만원)</dt>';
			s += '		<dd>' + DetailObj.getString(data.NM_SPORT_AMNT_MIN,'') + ' ~ ' + DetailObj.getString(data.NM_SPORT_AMNT_MAX,'') + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		
		//
		let getArgiSch = function(data){
			let arr = [];
			
			if('Y' === data.NM_ATTD){
				arr.push('재학');
			}
			if('Y' === data.NM_GRDU){
				arr.push('졸업');
			}
			if('Y' === data.NM_HISCH_STDT){
				arr.push('고등학생');
			}
			if('Y' === data.NM_UNVS_STDT){
				arr.push('대학생');
			}
			//
			return arr;
		};
		
		if('Y' === data.NM_AGRI_SCH){
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>농업계 학교 출신</dt>';
			s += '		<dd>' + getArgiSch(data) + '</dd>';
			s += '	</dl>';
			s += '</li>';
		}
		
		//
		if($.cs.isEmpty(s)){
			return;
		}
		
		//
		$('.detail.sportCnd .notFrmer ul')
			.html(s)
			.parent()
			.show()
			.parent()
			.show();
	};
	
	/**
	 *  지원내용
	 */
	DetailObj.prototype.showSportCn = function(data){
		let s = '';
		
		//
		if(!$.cs.isNull(data.SPORT_ST) && 'N' != data.SPORT_ST){
			s += '<li>지원기준 : '+data.SPORT_ST+'</li>';
		}
		if(!$.cs.isNull(data.SPORT_CD) && 'N' != data.SPORT_CD){
			s += '<li>지원조건 : '+data.SPORT_CD+'</li>';
		}
		if(!$.cs.isNull(data.NXNDR_PER) && 'N' != data.NXNDR_PER){
			s += '<li>국비(%) : '+data.NXNDR_PER+'</li>';
		}
		if(!$.cs.isNull(data.LCLTY_CT_PER) && 'N' != data.LCLTY_CT_PER){
			s += '<li>지방비(%) : '+data.LCLTY_CT_PER+'</li>';
		}
		if(!$.cs.isNull(data.FRMHS_PER) && 'N' != data.FRMHS_PER){
			s += '<li>자부담(%) : '+data.FRMHS_PER+'</li>';
		}
		if(!$.cs.isNull(data.FINANC_CT_PER) && 'N' != data.FINANC_CT_PER){
			s += '<li>융자(%) : '+data.FINANC_CT_PER+'</li>';
		}
		if(!$.cs.isNull(data.BSNS_PD) && 'N' != data.BSNS_PD){
			s += '<li>사업기간 : '+data.BSNS_PD+'</li>';
		}
		if(!$.cs.isNull(data.APPLY_TIME) && 'N' != data.APPLY_TIME){
			s += '<li>신청시기 : '+data.APPLY_TIME+'</li>';
		}
		
		
		
		//
		if($.cs.isEmpty(s)){
			return;
		}
		
		//
		$('div.sportCn ul')
			.html(s)
			.parent()
			.parent()
			.show();
	};
	
	
	
	/**
	 * 기타 필수 조건 표시
	 */
	DetailObj.prototype.showEtcEssntlCond = function(data){
		let selector = '.detail.etcEssntlCond'; 
		
		//
		$(selector).hide();
		
		//
		if('N' === data.RPQS_ECT){
			return;
		}
		
		let s = '';
		let arr = data.RPQS_ECT.split('｜');
		for (let i = 0; i < arr.length; i++) {
			let item = arr[i];
			s += '<li>' + item + '</li>';
		}
		
		
		//
		$(selector)
			.find('ul')
			.html(s)
			.closest(selector)
			.show();
	};
	
	
	/**
	 * 담당자 표시
	 */
	DetailObj.prototype.showContact = function(data){
		let selector = '.detail.contact';
		
		//
		$(selector).hide();
		
		//
		if($.cs.isEmpty(data.Contact) || 'N' === data.Contact){
			return;
		}
		
		let s = '';
		s += '<li>' + data.Contact + '</li>';
		
		//
		$(selector)
			.find('ul')
			.html(s)
			.closest(selector)
			.show();
	};
	
	
	/**
	 * 기타 제한 조건 표시
	 */
	DetailObj.prototype.showEtcLmttCond = function(data){
		let selector = '.detail.etcLmttCond';
		
		//
		$(selector).hide();
		
		//
		if('N' === data.RSTCT_ECT){
			return;
		}
		
		let s = '';
		let arr = data.RSTCT_ECT.split('｜');
		for (let i = 0; i < arr.length; i++) {
			let item = arr[i];
			s += '<li>' + item + '</li>';
		}
		
		//
		$(selector)
			.find('ul')
			.html(s)
			.closest(selector)
			.show();
	};
	
	
	
	
	/**
	 *  우대조건 표시
	 */
	DetailObj.prototype.showPvltrtCnd = function(data){
		let s = '';

		//
		if(!$.cs.isNull(data.CERTIFI) && 'N' != data.CERTIFI){
			s += '<li>인증 : '+data.CERTIFI+'</li>';
		}
		if(!$.cs.isNull(data.CST) && 'N' != data.CST){
			s += '<li>컨설팅 : '+data.CST+'</li>';
		}
		if(!$.cs.isNull(data.EDU) && 'N' != data.EDU){
			s += '<li>교육 : '+data.EDU+'</li>';
		}
		if(!$.cs.isNull(data.ORGNI_FORM) && 'N' != data.ORGNI_FORM){
			s += '<li>조직형태 : '+data.ORGNI_FORM+'</li>';
		}
		if(!$.cs.isNull(data.PROD_FORM) && 'N' != data.PROD_FORM){
			s += '<li>생산형태 : '+data.PROD_FORM+'</li>';
		}
		if(!$.cs.isNull(data.SHIP_FORM) && 'N' != data.SHIP_FORM){
			s += '<li>출하형태 : '+data.SHIP_FORM+'</li>';
		}
		if(!$.cs.isNull(data.PR_ETC) && 'N' != data.PR_ETC){
			s += '<li>기타 : '+data.PR_ETC+'</li>';
		}
		
		//
		if($.cs.isEmpty(s)){
			return;
		}
		
		//
		$('.detail.pvltrtCnd ul')
			.html(s)
			.parent()
			.parent()
			.show();
	};
	
	/**
	 * TODO 유사사업
	 */
	DetailObj.prototype.showSimilrBsns = function(data){
		
	};
	
	/**
	 * TODO 통계
	 */
	DetailObj.prototype.showStats = function(data){
		
	};
	
});

let detailObj = new DetailObj();

$(document).ready(function(){
	detailObj.init();
});