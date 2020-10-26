/**
 * 지원 정책
 * @since
 * 	0219	init
 */
let FixesSportPolicyObj = (function(){

	//농업인
	FixesSportPolicyObj.FRMER = 'frmer';
	//법인
	FixesSportPolicyObj.CPR = 'cpr';
	//비농업인
	FixesSportPolicyObj.NOT_FRMER = 'notFrmer';

	//
	FixesSportPolicyObj.PAGE_SIZE = 2;

	//조회된 전체 데이터 목록
	FixesSportPolicyObj.datas = [];

	FixesSportPolicyObj.getString = function(obj, defaultValue){
		if(isNaN(obj)){
			return defaultValue;
		}

		return (99999 > parseInt(obj) ? obj : defaultValue);
	};


	/**
	 *
	 */
	FixesSportPolicyObj.prototype.init = function(){
//		console.log('<<FixesSportPolicyObj.init');
	};

	/**
	 * 데이터 조회
	 * @param searchSido 시도명. 필수
	 * @param searchSigungu 시군구명. 필수
	 * @param searchUmd 읍면동명. 필수
	 * @param searchSportRelmCode 지원정책 목록. 구분자:콤마. 필수
	 * @param callbackFunction 콜백함수
	 */
	FixesSportPolicyObj.prototype.getDatasAsync = function(searchSido, searchSigungu, searchUmd, searchSportRelmCode, callbackFunction){
//		console.log('>>FixesSportPolicyObj.getDatasAsync');

		let p = {};
		p.searchSido = searchSido;
		p.searchSigungu = searchSigungu;
		p.searchUmd = searchUmd;
		p.searchSportRelmCode = searchSportRelmCode;

		//데이터 요청
		$.cs.submitAjax('/bdp/svc/getFixesSportPolicyDatasForRetnFmlg.json', p, function(res){
		//$.cs.submitAjax('../getFixesSportPolicyDatasForRetnFmlg.json', p, function(res){
//			console.log('<<FixesSportPolicyObj.getDatasAsync', res.datas.length);

			//
			FixesSportPolicyObj.datas = res.datas;


			//콜백함수 호출
			callbackFunction(res.resultCode, res.resultMessage);
		});
	};


	/**
	 * 데이터 조회
	 * @param mlsfcNmCode 세부 사업 코드
	 * @param bsnsCode 아그릭스 사업 코드
	 * @param callbackFunction 콜백 함수
	 */
	FixesSportPolicyObj.prototype.getDetailDataAsync = function(mlsfcNmCode, bsnsCode, callbackFunction){
//		console.log('>>FixesSportPolicyObj.getDetailDataAsync', mlsfcNmCode, bsnsCode);

		//
		let p = {};
		p.mlsfcNmCode = mlsfcNmCode.trim();
		p.bsnsCode = bsnsCode.trim();

		//
		$.cs.submitAjax('/bdp/svc/getFixesSportPolicyDatasByMlsfcNmCode.json', p, function(res){
//			console.log('<<FixesSportPolicyObj.getDetailDataAsync', res.datas.length);

			//
			if(0 < res.datas.length){
				callbackFunction(res.datas[0]);
			}

		});
	};


	/**
	 * 상세 html 리턴
	 * @param data
	 */
	FixesSportPolicyObj.prototype.getDetailHtml = function(data){
		//요약
		let sumryHtml = function(d){
			let s = '';

			//
			let arr=[];
			if('Y' === d.FMER_REQST){
				arr.push('농업인');
			}
			if('Y' === d.CPR_REQST){
				arr.push('법인');
			}
			if('Y' === d.NM_REQST){
				arr.push('비농업인');
			}

			s += ' <div class="bsns detail sumry pdNone" > ';
			s += ' 	<ul> ';
			s += ' 		<li class="bdNone"> ';
			s += ' 			<div class="article_area"> ';

			s += ' 				<input type="hidden" name="LCLAS_NM_CODE" value="'+d.LCLAS_NM_CODE+'">	 ';
			s += ' 				<input type="hidden" name="MLSFC_NM_CODE" value="'+d.MLSFC_NM_CODE+'">	 ';
			s += ' 				<input type="hidden" name="BSNS_CODE" value="'+d.BSNS_CODE+'">	 ';
			s += ' 				<p class="big_tit"><span class="MLSFC_NM">'+d.MLSFC_NM+'</span> <span class="DETAIL_BSNS_NM">('+d.DETAIL_BSNS_NM+')</span></p> ';

			s += ' 				<div class="keyword_item flNone cl"> ';
			s += ' 					<button type="button" class="item_01"><span class="SPORT_SUB">'+d.SPORT_SUB+'</span></button> ';
			s += ' 					<button type="button" class="item_02"><span class="sportTarget">'+arr.join(',')+'</span></button> ';
			s += ' 					<button type="button" class="item_03"><span class="SPORT_STLE">'+d.SPORT_STLE+'</span></button> ';
			s += ' 					<button type="button" class="item_04"><span class="SPORT_AREA">'+d.SPORT_AREA+'</span></button> ';
			s += ' 				</div> ';
			s += ' 				<p class="s_txt cl"><span class="BSNS_CN">'+d.BSNS_CN+'</span></p> ';
//			s += ' 				<button type="button" class="go_btn flr btnDwld" style="right:140px !important;">지침서 내려받기</button> ';
			s += ' 				<button type="button" class="go_btn flr btnList">목록으로</button> ';
			s += ' 			</div> ';
			s += ' 		</li> ';
			s += ' 	</ul> ';
			s += ' </div> ';


			//
			return s;
		};


		//지원조건
		let sportCnd = function(d){
			let s = '';
			s += '<div class="bsns detail sportCnd pdNone" > ';

			//농업인
			s += sportCndFrmer(d);
			//법인
			s += sportCndCpr(d);
			//비농업인
			s += sportCndNotFrmer(d);

			s += '</div>';

			//
			return s;
		};

		//지원조건 농업인
		let sportCndFrmer = function(d){
			if('Y' !== d.FMER_REQST){
				return '';
			}

			//
			let s = '';
			s += '	<div class="article_area frmer">';
			s += '		<h4 class="fixes">지원조건(농업인)</h4>';
			s += '		<ul>';
			//
			if('Y' === d.FMER_AGE){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>연령</dt>';
				s += '		<dd>' + $.cs.nvl(d.FMER_AGE_MIN, '') + ' ~ ' + $.cs.nvl(d.FMER_AGE_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.FMER_SEX){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>성별</dt>';
				s += '		<dd>' + ('Y' === d.FMER_SEX_M ? '남자':'여자') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.FMER_RESIDE_AREA){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>거주지</dt>';
				s += '		<dd>' + d.FMER_RESIDE_AREA + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.FMER_ATMNENT_ESSNTL_AT){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>경영체등록</dt>';
				s += '		<dd>' + d.FMER_ATMNENT_ESSNTL_AT + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' !== d.FMER_ATMNENT_ESSNTL_PD_MIN || 'N' !== d.FMER_ATMNENT_ESSNTL_PD_MAX){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>경영체등록 기간(년)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.FMER_ATMNENT_ESSNTL_PD_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.FMER_ATMNENT_ESSNTL_PD_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.FMER_MNT_PD){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>농업 경영 기간(년)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.FMER_MNT_PD_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.FMER_MNT_PD_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.FMER_CTVT_PRDLST_GRP){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>재배(사육)품목군</dt>';
				s += '		<dd>';
				s += '			<span class="dots" title="' + d.FMER_CTVT_PRDLST_GRP + '">' + d.FMER_CTVT_PRDLST_GRP + '</span>';
				s += '		</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.FMER_CTVT_PRDLST_NM){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>재배(사육)품목</dt>';
				s += '		<dd>';
				s += '			<span class="dots" title="' + d.FMER_CTVT_PRDLST_NM + '">' + d.FMER_CTVT_PRDLST_NM + '</span>';
				s += '		</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.FMER_TYPE_CTVT){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>재배유형</dt>';
				s += '		<dd>' + d.FMER_TYPE_CTVT + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.FMER_CTVT_AR){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>재배면적(㎡)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.FMER_CTVT_AR_MIN,'') + ' ~ ' + FixesSportPolicyObj.getString(d.FMER_CTVT_AR_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.FMER_AR_OWN_PD){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>농지 소유 기간(년)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.FMER_AR_OWN_PD_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.FMER_AR_OWN_PD_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.FMER_AR_USE_PD){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>농지 이용 기간(년)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.FMER_AR_USE_PD_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.FMER_AR_USE_PD_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.FMER_AR_STND){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>농지 기준</dt>';
				s += '		<dd>' + getArStnd(d.FMER_AR_STND) + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.FMER_SALE_AMNT){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>연간 판매 금액(백만원)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.FMER_SALE_AMNT_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.FMER_SALE_AMNT_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}

			s += '		</ul>';
			s += '	</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			//
			return s;
		};

		//지원조건 - 법인
		let sportCndCpr = function(d){
			if('Y' !== d.CO_REQST){
				return '';
			}

			//
			let s = '';
			s += '	<div class="article_area cpr">';
			s += '		<h4 class="fixes">지원조건(법인)</h4>';
			s += '		<ul>';

			//
			if('Y' === d.CO_CAPITAL){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>자본금(백만원)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_CAPITAL_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_CAPITAL_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.CO_EQ_CAPTIAL_STND){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>자기자본 기준</dt>';
				s += '		<dd>' + d.CO_EQ_CAPTIAL_STND + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.CO_INVST_NUM){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>출자자 수(명)</dt>';
				s += '		<dd>' + d.CO_INVST_NUM_MIN + ' ~</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.CO_OPRT_PD){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>법인 운영 기간(년)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_OPRT_PD_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_OPRT_PD_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			s += '<li>';
			s += '	<dl>';
			s += '		<dt>대기업 제한 여부</dt>';
			s += '		<dd>' + d.CO_BIGCOM_RSTCT + '</dd>';
			s += '	</dl>';
			s += '</li>';
			if('N' != d.CO_HAND_CTVT_GRP){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>취급품목군</dt>';
				s += '		<dd>';
				s += '			<span class="dots" title="'+d.CO_HAND_CTVT_GRP+'">' + d.CO_HAND_CTVT_GRP + '</span>';
				s += '		</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.CO_HAND_CTVT){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>취급품목</dt>';
				s += '		<dd>';
				s += '			<span class="dots" title="' + d.CO_HAND_CTVT + '">' + d.CO_HAND_CTVT + '</span>';
				s += '		</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.CO_PURCHS_SCL){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>매입규모(톤)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_PURCHS_SCL_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_PURCHS_SCL_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.CO_PURCHS_SCL_PD){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>매입규모 평균 적용 기간(년)</dt>';
				s += '		<dd>' + d.CO_PURCHS_SCL_PD + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.CO_SALE_SCL){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>판매 규모(톤)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_SALE_SCL_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_SALE_SCL_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' != d.CO_SALE_SCL_PD){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>판매규모 평균 적용 기간(년)</dt>';
				s += '		<dd>' + d.CO_SALE_SCL_PD + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.CO_SALE_AMNT){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>매출액(백만원)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_SALE_AMNT_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_SALE_AMNT_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.CO_HAND_AMNT){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>취급액(백만원)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_HAND_AMNT_MIN, '') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_HAND_AMNT_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.CO_FRMHS_SCL){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>참여 농가 규모(명)</dt>';//참여 농가 규모
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_FRMHS_SCL_MIN,'') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_FRMHS_SCL_MAX,'') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.CO_FAM_SCL){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>생산 규모(ha)</dt>';//참여농지 규모
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_FAM_SCL_MIN,'') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_FAM_SCL_MAX,'') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' !== d.CO_SPORT_AMNT_MIN || 'N' !== d.CO_SPORT_AMNT_MAX){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>지원 금액(백만원)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.CO_SPORT_AMNT_MIN,'') + ' ~ ' + FixesSportPolicyObj.getString(d.CO_SPORT_AMNT_MAX, '') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}

			s += '		</ul>';
			s += '	</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			//
			return s;
		};

		//지원조건 - 비농업인
		let sportCndNotFrmer = function(d){
			if('Y' !== d.NM_REQST){
				return '';
			}

			//
			let s = '';
			s += '	<div class="article_area notFrmer">';
			s += '		<h4 class="fixes">지원조건(비농업인)</h4>';
			s += '		<ul>';

			if('Y' === d.NM_AGE){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>연령</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.NM_AGE_MIN,'') + ' ~ ' + FixesSportPolicyObj.getString(d.NM_AGE_MAX,'') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('Y' === d.NM_SEX){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>연령</dt>';
				s += '		<dd>' + ('Y' === d.NM_SEX_M?'남자':'여자') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' !== d.NM_RESIDE_AREA){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>거주지</dt>';
				s += '		<dd>' + d.NM_RESIDE_AREA + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}
			if('N' !== d.NM_SPORT_AMNT_MIN || 'N' !== d.NM_SPORT_AMNT_MAX){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>지원 금액(백만원)</dt>';
				s += '		<dd>' + FixesSportPolicyObj.getString(d.NM_SPORT_AMNT_MIN,'') + ' ~ ' + FixesSportPolicyObj.getString(d.NM_SPORT_AMNT_MAX,'') + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}

			//
			let getArgiSch = function(data){
				let arr = [];

				if('Y' === d.NM_ATTD){
					arr.push('재학');
				}
				if('Y' === d.NM_GRDU){
					arr.push('졸업');
				}
				if('Y' === d.NM_HISCH_STDT){
					arr.push('고등학생');
				}
				if('Y' === d.NM_UNVS_STDT){
					arr.push('대학생');
				}
				//
				return arr;
			};

			if('Y' === d.NM_AGRI_SCH){
				s += '<li>';
				s += '	<dl>';
				s += '		<dt>농업계 학교 출신</dt>';
				s += '		<dd>' + getArgiSch(data) + '</dd>';
				s += '	</dl>';
				s += '</li>';
			}

			//
			s += '		</ul>';
			s += '	</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			//
			return s;
		};

		//지원 내용
		let sportCn = function(d){
			let s = '';
			s += '<div class="bsns detail sportCn pdNone">';
			s += '	<div class="article_area">';
			s += '		<h4 class="fixes">지원 내용</h4>';
			s += '		<ul>';

			//
			if(!$.cs.isNull(d.SPORT_ST) && 'N' != d.SPORT_ST){
				s += '<li>지원기준 : '+d.SPORT_ST+'</li>';
			}
			if(!$.cs.isNull(d.SPORT_CD) && 'N' != d.SPORT_CD){
				s += '<li>지원조건 : '+d.SPORT_CD+'</li>';
			}
			if(!$.cs.isNull(d.NXNDR_PER) && 'N' != d.NXNDR_PER){
				s += '<li>국비(%) : '+d.NXNDR_PER+'</li>';
			}
			if(!$.cs.isNull(d.LCLTY_CT_PER) && 'N' != d.LCLTY_CT_PER){
				s += '<li>지방비(%) : '+d.LCLTY_CT_PER+'</li>';
			}
			if(!$.cs.isNull(d.FRMHS_PER) && 'N' != d.FRMHS_PER){
				s += '<li>자부담(%) : '+d.FRMHS_PER+'</li>';
			}
			if(!$.cs.isNull(d.FINANC_CT_PER) && 'N' != d.FINANC_CT_PER){
				s += '<li>융자(%) : '+d.FINANC_CT_PER+'</li>';
			}
			if(!$.cs.isNull(d.BSNS_PD) && 'N' != d.BSNS_PD){
				s += '<li>사업기간 : '+d.BSNS_PD+'</li>';
			}
			if(!$.cs.isNull(d.APPLY_TIME) && 'N' != d.APPLY_TIME){
				s += '<li>신청시기 : '+d.APPLY_TIME+'</li>';
			}

			s += '		</ul>';
			s += '	</div>';
			s += '</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			return s;
		};


		//우대조건 표시
		let pvltrtCnd = function(d){
			let s = '';
			s += '<div class="bsns detail pvltrtCnd pdNone">';
			s += '	<div class="article_area">';
			s += '		<h4 class="fixes">우대 조건</h4>';
			s += '		<ul>';

			//
			if(!$.cs.isNull(d.CERTIFI) && 'N' != d.CERTIFI){
				s += '<li>인증 : '+d.CERTIFI+'</li>';
			}
			if(!$.cs.isNull(d.CST) && 'N' != d.CST){
				s += '<li>컨설팅 : '+d.CST+'</li>';
			}
			if(!$.cs.isNull(d.EDU) && 'N' != d.EDU){
				s += '<li>교육 : '+d.EDU+'</li>';
			}
			if(!$.cs.isNull(d.ORGNI_FORM) && 'N' != d.ORGNI_FORM){
				s += '<li>조직형태 : '+d.ORGNI_FORM+'</li>';
			}
			if(!$.cs.isNull(d.PROD_FORM) && 'N' != d.PROD_FORM){
				s += '<li>생산형태 : '+d.PROD_FORM+'</li>';
			}
			if(!$.cs.isNull(d.SHIP_FORM) && 'N' != d.SHIP_FORM){
				s += '<li>출하형태 : '+d.SHIP_FORM+'</li>';
			}
			if(!$.cs.isNull(d.PR_ETC) && 'N' != d.PR_ETC){
				s += '<li>기타 : '+d.PR_ETC+'</li>';
			}

			//
			s += '		</ul>';
			s += '	</div>';
			s += '</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			//
			return s;
		};

		//기타 필수 조건
		let etcEssntlCond = function(d){
			//
			if('N' === d.RPQS_ECT){
				return '';
			}

			//
			let s = '';
			s += '<div class="bsns detail etcEssntlCond pdNone">';
			s += '	<div class="article_area">';
			s += '		<h4 class="fixes">기타 필수 조건</h4>';
			s += '		<ul>';

			let arr = d.RPQS_ECT.split('｜');
			for (var i = 0; i < arr.length; i++) {
				var item = arr[i];
				s += '<li>' + item + '</li>';

			}

			//
			s += '		</ul>';
			s += '	</div>';
			s += '</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			return s;
		};

		//기타 제한 조건
		let etcLmttCond = function(d){
			let s = '';

			//
			if('N' === d.RSTCT_ECT){
				return s;
			}

			//
			s += '<div class="bsns detail etcLmttCond pdNone">';
			s += '	<div class="article_area">';
			s += '		<h4 class="fixes">기타 제한 조건</h4>';
			s += '		<ul>';

			//
			let arr = d.RSTCT_ECT.split('｜');
			for (var i = 0; i < arr.length; i++) {
				var item = arr[i];
				s += '<li>' + item + '</li>';

			}

			//
			s += '		</ul>';
			s += '	</div>';
			s += '</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			//
			return s;
		};

		//연락처
		let contact = function(d){
			let s = '';

			//
			if($.cs.isEmpty(d.Contact) || 'N' === d.Contact){
				return s;
			}

			//
			s += '<div class="bsns detail contact pdNone">';
			s += '	<div class="article_area">';
			s += '		<h4 class="fixes">연락처</h4>';
			s += '		<ul>';

			s += '		<li>' + d.Contact + '</li>';

			//
			s += '		</ul>';
			s += '	</div>';
			s += '</div>';

			//
			if(-1 === s.indexOf('<li>')){
				return '';
			}

			//
			return s;
		};



		//
		let d = data._source;

		//
		let s = '';

		//sumry
		s += sumryHtml(d);
		//sportCnd
		s += sportCnd(d);
		//sportCn
		s += sportCn(d);
		//pvltrtCnd
		s += pvltrtCnd(d);
		//etcEssntlCond
		s += etcEssntlCond(d);
		//etcLmttCond
		s += etcLmttCond(d);
		//contact
		s += contact(d);

		//
		return s;
	};



	/**
	 * 전체 데이터 건수 리턴
	 */
	FixesSportPolicyObj.prototype.getTotcnt = function(datas){
		if($.cs.isNull(datas)){
			return FixesSportPolicyObj.datas.length;
		}else{
			return datas.length;
		}

	};


	/**
	 * 페이징된 데이터 목록 리턴
	 * @param pageNo 현재 페이지 번호. 1부터 시작
	 */
	FixesSportPolicyObj.prototype.getPagedDatas = function(pageNo){
		//
		let json = $.cs.paginate(policyObj.getTotcnt(), pageNo, FixesSportPolicyObj.PAGE_SIZE);

		//
		let arr = [];

		//
		if(0 > json.startIndex || 0 > json.endIndex){
			return arr;
		}

		//
		for(let i=json.startIndex; i<=json.endIndex; i++){
			let d = FixesSportPolicyObj.datas[i]._source;

			//
			arr.push( d );
		}

		//
//		console.log('<<.getPagedDatas', json, arr.length);
		return arr;
	};


	/**
	 * 데이터 1건 조회
	 * @param lclasNmCode 사업코드. 필수
	 * @param mlsfcNmCode 세부 사업 코드. 필수
	 * @param bsnsCode (아그릭스) 사업 코드. 필수
	 */
	FixesSportPolicyObj.prototype.getDetailData = function(lclasNmCode, mlsfcNmCode, bsnsCode){
		let p = {};

		//
		for (var i = 0; i < FixesSportPolicyObj.datas.length; i++) {
			var item = FixesSportPolicyObj.datas[i];
			let d = item._source;

			//
			if(lclasNmCode !== d.LCLAS_NM_CODE){
				return;
			}

			//
			if(mlsfcNmCode !== d.MLSFC_NM_CODE){
				return;
			}

			//
			if(bsnsCode !== d.BSNS_CODE){
				return;
			}

			//
			p = d;

		}


		//
//		console.log('<<.getData', p, lclasNmCode, mlsfcNmCode, bsnsCode);
		return p;
	};



	/**
	 * 화면 출력용 html 문자열 생성
	 */
	FixesSportPolicyObj.prototype.getDetailDataHtml = function(data){
		let d = data._source;

		//
		let s = '';

		//TODO

		//
		return s;
	};


	/**
	 * 페이징된 데이터 화면 표시를 위한 html 문자열 리턴
	 * @param pageNo
	 */
	FixesSportPolicyObj.prototype.getPagedDatasHtml = function(pageNo){
		//지원 금액 문자열
		let getSportAmountString = function(item){
			let keyMin='', keyMax='';


			//
			if('Y' === item.FMER_REQST){
				keyMin = 'FMER_SPORT_AMNT_MIN';
				keyMax = 'FMER_SPORT_AMNT_MAX';
			}else if('Y' === item.CO_REQST){
				keyMin = 'CO_SPORT_AMNT_MIN';
				keyMax = 'CO_SPORT_AMNT_MAX';
			}else if('Y' === item.NM_REQST){
				keyMin = 'NM_SPORT_AMNT_MIN';
				keyMax = 'NM_SPORT_AMNT_MAX';
			}

			//
			let s = '';
			s += (isNaN(item[keyMin]) ? '' : item[keyMin]);
			s += ' ~ ';
			s += (isNaN(item[keyMax]) ? '' : item[keyMax]);

			//~만 존재하면...
			return (3 == s.length ? '' : s);
		};

		//지원 대상 문자열
		let getSportTargetString = function(item){
			let arr = [];

			if('Y' === item.FMER_REQST){
				arr.push('농업인');
			}
			if('Y' === item.CO_REQST){
				arr.push('법인');
			}
			if('Y' === item.NM_REQST){
				arr.push('비농업인');
			}

			//
			return arr.join(',');
		};

		//
		let datas = policyObj.getPagedDatas(pageNo);

		let s = '';

		//
		if($.cs.isNull(datas) || 0 == datas.length){
//			console.log('<<FixesSportPolicyObj.getPagedDatasHtml', 'pageNo', pageNo, 'empty datas');
			return s;
		}


		//
		for (var i = 0; i < datas.length; i++) {
			var item = datas[i];
			s += '<li class="bsns" > ';
			s += '	<!--사업코드 --><input type="hidden" name="LCLAS_NM_CODE" value="' + item.LCLAS_NM_CODE + ' ">';
			s += '	<!--세부사업코드 --><input type="hidden" name="MLSFC_NM_CODE" value="' + item.MLSFC_NM_CODE + ' ">';
			s += '	<!--아그릭스사업코드 --><input type="hidden" name="BSNS_CODE" value="' + item.BSNS_CODE + ' ">';
			s += '    <div class="pop_area"> ';
			s += '        <p class="big_tit"> ';
			s += '            <a title="상세페이지이동" class="link" href="#">'+item.MLSFC_NM+ ('N' != item.DETAIL_BSNS_NM ? '('+item.DETAIL_BSNS_NM + ')' : '') + '</a> ';
			s += '        </p> ';
			s += '            <div class="keyword_item"> ';
			s += '                <button class="item_01" type="button">'+item.SPORT_SUB+'</button> ';
			s += '                <button class="item_02" type="button">'+getSportTargetString(item)+'</button> ';
			s += '                <button class="item_03" type="button">'+item.SPORT_STLE+'</button> ';
			s += '                <button class="item_04" type="button">'+item.SPORT_AREA+'</button> ';
			s += '            </div> ';
			s += '            <ul> ';
			s += '                <li> ';
			s += '                    <dl> ';
			s += '                        <dt>지원금액(백만원, 최소~최대)</dt> ';
			s += '                        <dd>'+getSportAmountString(item)+'</dd> ';
			s += '                    </dl> ';
			s += '                </li> ';
			s += '                <li> ';
			s += '                    <dl> ';
			s += '                        <dt>사업기간</dt> ';
			s += '                        <dd>'+('N' != item.BSNS_PD ? item.BSNS_PD : '')+'</dd> ';
			s += '                    </dl> ';
			s += '                </li> ';
			s += '                <li> ';
			s += '                    <dl> ';
			s += '                        <dt>신청시기</dt> ';
			s += '                        <dd>' + ('N' != item.APPLY_TIME ? item.APPLY_TIME : '') + '</dd> ';
			s += '                    </dl> ';
			s += '                </li> ';
			s += '            </ul> ';
			s += '            <p class="s_txt">'+item.BSNS_CN+'</p> ';
			s += '    </div> ';
			s += '</li> ';

		}


		//
		return s;
	};


	/**
	 * 페이징 html 문자열 리턴
	 * 호출한 곳에서는 html 문자열 렌더링 후 클릭 이벤트 등록해야 함
	 * @param pageNo 현재 페이지 번호
	 */
	FixesSportPolicyObj.prototype.getPagingHtml = function(pageNo){
		//
		let json = $.cs.paginate(policyObj.getTotcnt(), pageNo, FixesSportPolicyObj.PAGE_SIZE);

		//
		let s = '';

		//first
		s += '<a href="javascript:;" data-page-no="1" title="처음">&lt;&lt;</a>';

		//page list
		for (var i = 0; i < json.pages.length; i++) {
			var item = json.pages[i];
			if(json.currentPage == item){
				s += '<a href="javascript:;" class="on" data-page-no="'+item+'">'+item+'</a>';
			}else{
				s += '<a href="javascript:;" data-page-no="'+item+'">'+item+'</a>';
			}

		}

		//last
		s += '<a href="javascript:;" data-page-no="'+json.totalPages+'" title="마지막" style="border-right:0;">&gt;&gt;</a>';

		//
		return s;
	};



	/**
	 * 탭처럼 표시되는 지원영역별 건수 html 문자열
	 */
	FixesSportPolicyObj.prototype.getTabHtml = function(){

		//지원영역별 건수
		let getCountsBySportRelmCode = function(datas){
			let json = {};

			for (var i = 0; i < datas.length; i++) {
				var item = datas[i];
				let d = item._source;
				let k = d.SPORT_AREA;

				if($.cs.isNull(json[k])){
					json[k] = 1;
				}else{
					json[k] = json[k] + 1;
				}

			}

//			console.log(json);
			policySrchObj.retnPolicyCnt = json['귀농/귀촌/창업'];
			policySrchObj.edcPolicyCnt = json['교육/컨설팅'];
			return json;
		};

		//전체 건수 표시
		let getTotcnt = function(json){
			let totcnt=0;
			let keys = Object.keys(json);
			for (var i = 0; i < keys.length; i++) {
				var k = keys[i];
				totcnt += json[k];

			}

			//
			return totcnt;
		};


		//
		let json = getCountsBySportRelmCode(FixesSportPolicyObj.datas);

		//
		let s = '';
		s += '<ul>';

		s += '<li class="fir_ch">';
		s += '	전체';
		s += '	<b>'+getTotcnt(json)+'</b>';
		s += '<li>';

		//
		let keys = Object.keys(json);
		for (var i = 0; i < keys.length; i++) {
			var x = keys[i];
			s += '<li>';
			s += '	<span>'+x+'</span>';
			s += '	<b>'+json[x]+'</b>';
			s += '<li>';

		}

		s += '</ul>';

		//
		return s;

	};

});

//인스턴스 생성
let policyObj = new FixesSportPolicyObj();

$(document).ready(function(){
	policyObj.init();
});