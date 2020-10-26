

let DataObj = (function(){
	
	this.vo = {
			//구분 - 농업인(frmer),법인(cpr),비농업인(notFrmer)
			gbn : '',
			//키워드
			keyword : '',
			//지원영역
			sportRelmCode : '',
			//성별
			sexdstnCode : '',
			//지원유형
			sportTyCode : '',
			//지원주체
			sportMbyCode : '',
			//나이
			age : '',
			//거주지
			resdncCode : '',
			//농업경영체 등록 여부
			atmnentRegistAt : '',
			//영농 경력
			farmCareer : '',
			//재배품목군
			ctvtPrdlstSetCode : '',
			//재배품목
			ctvtPrdlstCode : '',
			//재배유형
			ctvtTyCode : '',
			//재배면적
			ctvtAr : '',
			//법인 - 설립년도
			fondYear : '',
			//법인 - 자본금
			capl : '',
			//법인 - 출자자 수
			investorCo : '',
			//법인 - 취급규모
			trtmntScale : '',
			//법인 - 매출액
			selngAmount : '',
			//농업계 학교 출신 여부
			farmngSchulOrginAt : '',
			
			//
			pageSize : 10,
			currentPageNo : '',
			//필터로 검색(filter), 키워드로 검색(keyword)
			searchBy : ''
			
	};
	
	//
	this.responseData = {
		datas : [
			{
				//사업명	lclasNm
				//사업코드	lclasNmCode
				//세부사업명	detailBsnsNm
				//세부사업코드	mlsfcNmCode
				//사업내용	bsnsCn
				//지원영역	sportArea
				//지원유형	sportStle
				//지원주체	sportSub
				//사업요약	bsnsSummary
				//농업인 지원가능	fmerReqst
				//농업인 지원금액 - 최소	fmerSportAmntMin
				//농업인 지원금액 - 최대	fmerSportAmntMax
				//법인 지원가능	coReqst
				//법인 지원금액 - 최소	coSportAmntMin
				//법인 지원금액 - 최대	coSportAmntMax
				//비농업인 지원가능	nmReqst				
				//비농업인 - 지원금액 - 최소 nmSportAmntMin
				//비농업인 - 지원금액 - 최대 nmSportAmntMax				
				//사업기간	bsnsPd
				//신청시기	applyTime				
			}
		],
		totalRecordCount : 999,
		currentPageNo : 1,
		pageSize : 10
	};
	
	this.filteredDatas = [];
	
	
	DataObj.prototype.getFilteredDatas = function(){
		let datas = this.responseData.datas;
		
		//지원영역 존재시 필터링
		datas = this._filteringBySportRelmCode(datas);
	};
	
	
	/**
	 * 지원영역으로 필터링
	 */
	DataObj.prototype._filteringBySportRelmCode = function(datas){
		
		//
		if($.cs.isEmpty(this.vo.sportRelmCode)){
			return datas;
		}
		
		//
		let arr = [];
		
		//
		for (let i = 0; i < datas.length; i++) {
			let item = datas[i];
			if(dataObj.vo.sportRelmCode === item.sportArea){
				arr.push(item);
			}
		}
		
		
		//
		$.cs.log('<<._filteringBySportRelmCode', arr.length);
		return arr;
	};
	
	/**
	 * vo object를 array로 변환 
	 */
	DataObj.prototype.toArrayOfNameValue = function(){
		let arr = [];
		let keys = Object.keys(dataObj.vo);
		
		//
		for (let i = 0; i < keys.length; i++) {
			let item = keys[i];
//			console.log(item);
			arr.push({'name':item, 'value':dataObj.vo[item]});
		}
		
		
		//
		return arr;
	};

	
	/**
	 * get 지원영역 목록
	 */
	DataObj.prototype.getSportRelms = function(){
		return [
				{'codeId':'SR1', 'codeNm':'6차산업/도농교류'}
				,{'codeId':'SR2', 'codeNm':'안전/복지'}
				,{'codeId':'SR3', 'codeNm':'교육/컨설팅'}
				,{'codeId':'SR4', 'codeNm':'생산/유통기반 확충'}
				,{'codeId':'SR5', 'codeNm':'유통/마케팅'}
				,{'codeId':'SR6', 'codeNm':'경영안전지원'}
				,{'codeId':'SR7', 'codeNm':'귀농/귀촌/창업'}
				];
	};
	
	/**
	 * get 지원유형 목록
	 */
	DataObj.prototype.getSportTys = function(){
		return [
				{'codeId':'보조사업', 'codeNm':'보조사업'}
				,{'codeId':'융자사업', 'codeNm':'융자사업'}
				];
	};
	
	/**
	 * get 지원주체 목록
	 */
	DataObj.prototype.getSportMbys = function(){
		return [
				{'codeId':'중앙정부', 'codeNm':'중앙정부'}
				,{'codeId':'지방정부', 'codeNm':'지방정부'}
				];
	};
	
	/**
	 * get 성별 목록
	 */
	DataObj.prototype.getSexdstns = function(){
		return [{'codeId':'M', 'codeNm':'남'},{'codeId':'F', 'codeNm':'여'}];
	};
	
	/**
	 * get 거주지 목록
	 */
	DataObj.prototype.getResdncs = function(){
		
	};
	
	/**
	 * get 농업경영체 등록 여부 목록
	 */
	DataObj.prototype.getAtmnentRegistAts = function(){
		return [
				{'codeId':'Y', 'codeNm':'등록'}
				,{'codeId':'N', 'codeNm':'미등록'}
				];
	};
	
	/**
	 * get 재배품목군 목록
	 */
	DataObj.prototype.getCtvtPrdlstSets = function(){
		return [
			{'codeId':'CPS1', 'codeNm':'과실류'}
			,{'codeId':'CPS2', 'codeNm':'채소류'}
			];
	};
	
	/**
	 * get 재배품목 목록 by 재배품목군
	 */
	DataObj.prototype.getCtvtPrdlsts = function(ctvtPrdlstSetCode){
		if('CPS1' === ctvtPrdlstSetCode){
			return [
				{'codeId':'CP1', 'codeNm':'사과'}
				,{'codeId':'CP2', 'codeNm':'배'}
				];
		}
		
		//
		if('CPS2' === ctvtPrdlstSetCode){
			return [
				{'codeId':'CP1', 'codeNm':'오이'}
				,{'codeId':'CP2', 'codeNm':'가지'}
				];
		}
		
	};
	
	/**
	 * get 재배유형 목록 
	 */
	DataObj.prototype.getCtvtTy = function(){
		return [
				{'codeId':'시설', 'codeNm':'시설'}
				,{'codeId':'노지', 'codeNm':'노지'}
				];
	};
	
	/**
	 * get 농업계 학교 출신여부 목록 
	 */
	DataObj.prototype.getFarmngSchulOrginAts = function(){
		return [
				{'codeId':'Y', 'codeNm':'농업계 학교 출신'}
				,{'codeId':'N', 'codeNm':'농업계 학교 출신 아님'}
				];
	};
});

let dataObj = new DataObj();
