/**
 * 
 */
let RiceDataObj = (function(){
	
	RiceDataObj.UNIT = 1000000;
	
	RiceDataObj.LAST_YEAR = 2019;
	
	RiceDataObj.REQST = 'reqst';
	RiceDataObj.DCSN = 'dcsn';
	
	RiceDataObj.REQST_AR = 'reqstAr';
	RiceDataObj.DCSN_AR = 'dcsnAr';
	
	RiceDataObj.OUTTRN_REQST_AR = 'outtrnReqstAr';
	RiceDataObj.OUTTRN_DCSN_AR = 'outtrnDcsnAr';
	
	RiceDataObj.round = function(datas){
		let arr=[];
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			arr.push( Math.round(d) );
		}
		
		//
//		console.log('round', arr);
		return arr;
	};
	
	
	RiceDataObj.divide = function(datas, x){
		let arr=[];
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			arr.push( d / x);
		}
		
		//
//		console.log('divide', arr);
		return arr;
	};
	
	
	RiceDataObj.max = function(datas){
		let v = Number.MIN_VALUE;
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			//
			if(v < d){
				v = d;
			}
		}
		
		//
		return v;
	};
	
	/**
	 * 중복 제거 목록 리턴
	 * @params datas 데이터 목록
	 * @param key 중복 검사할 json's key
	 */
	RiceDataObj.distinct = function(datas, key){
		
		let p={};
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			//
			p[d[key]] = d[key];
		}
		
		//
		let arr = Object.keys(p);
//		console.log('<<RiceDataObj.distinct', arr);
		return arr;
	};
	
	
	/**
	 * 데이터 합계 리턴
	 * @param datas 데이터 목록
	 * @param datakey json's key
	 */
	RiceDataObj.sum = function(datas, dataKey){
		let x=0;
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			x += d[dataKey];
		}
		
		//
		return x;
	};
	
	
	/**
	 * 필터링된 데이터 목록 리턴
	 * @param datas 데이터 목록
	 * @param json {'year':string, 'sidoName':string, 'sigunguName':string}
	 */
	RiceDataObj.getDatas = function(datas, json){
		let arr=[];
		
		//
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			//
			if(!$.cs.isNull(json.year)){
				if(json.year != d.year){
					//년도 다름
					continue;
				}
			}
			
			//
			if(!$.cs.isNull(json.sidoName)){
				if(json.sidoName !== d.sidoName){
					//시도명 다름
					continue;
				}
			}
			
			//
			if(!$.cs.isNull(json.sigunguName)){
				if(json.sigunguName !== d.sigunguName){
					//시군구명 다름
					continue;
				}
			}
			
			//
			arr.push(d);
		}
		
		//
//		console.log('getDatas', json, arr.length);
		return arr;
	};
	
	
	/**
	 * 시도로 필터링된 시군구 목록
	 * @param sidoName 시도명
	 */
	RiceDataObj.getSigungus = function(sidoName){
		let arr=[];
		
		//
		for(let i=0; i<rdObj.sigungus.length; i++){
			let d = rdObj.sigungus[i];
			
			//
			if(sidoName === d.sidoName){
				arr.push(d);
			}
		}
		
		//
		return arr;
	};
	

	

	
	
	
	/**
	 * 지역별 단수 목록에서 해당하는 sum(단수) 구하기
	 * @param datas 지역별 단수 목록
	 * @param json {year:int, sidoName:string, sigunguName:string}
	 */
	RiceDataObj.getDansu = function(datas, json){
		let dansu=0;
		
		//
		for(let i=0; i<datas.length; i++){
			let x = datas[i];
			
			//
			let d = x._source;
			
			if($.cs.isNull(d.DAN_SU)){
				continue;
			}
			
			//
			if(!$.cs.isNull(json.year)){
				if(d.YEAR != json.year){
					//년도 다름
					continue;
				}
			}
			
			//
			if(!$.cs.isNull(json.sidoName)){
				if(d.CTPRVN_NM != json.sidoName){
					//시도명 다름
					continue;
				}
			}
			
			//
			if(!$.cs.isNull(json.sigunguName)){
				if(d.CTPRVN_NM !== json.sigunguName){
					//시군구명 다름
					continue;
				}
			}
			
			//
			dansu += parseFloat(d.DAN_SU.trim());
		}
		
		
		//
		return dansu;
	};
	
	
	/**
	 * sum(면적) 구하기
	 * @param datas 직불금 신청 면적 목록
	 * @param json {year:int, sidoName:string, sigunguName:string, arKey:string}
	 */
	RiceDataObj.getAr = function(datas, json){
		let ar = 0;
		
		//
		for(let i=0; i<datas.length; i++){
			let x = datas[i];
			let d = x._source;
			
			//
			if(!$.cs.isNull(json.year)){
				if(d.bsns_year != json.year){
					//년도 다름
					continue;
				}
			}
			
			//
			if(!$.cs.isNull(json.sidoName)){
				if(d.ctprvn_nm != json.sidoName){
					//시도명 다름
					continue;
				}
			}
			
			//
			if(!$.cs.isNull(json.sigunguName)){
				if(d.signgu_nm !== json.sigunguName){
					//시군구명 다름
					continue;
				}
			}
			
			//
			let k = '';
			if(!$.cs.isNull(json.arKey)){
				k = json.arKey;
			}else{
				//
				k = 'dcsn_ar';
				if(!$.cs.isNull(json.year) && json.year == (new Date()).getFullYear()){
					let mm = 1+(new Date()).getMonth();
					if(4 <= mm && mm < 10){
						//신청면적
						k = 'reqst_ar';
					}
				}
			}
			
			
			//
			ar += parseFloat( d[k] );
		}
	
		
		//
		return ar;
	};
	
	
	/**
	 * 최근 10년 연도 목록
	 */
	RiceDataObj.getYears = function(){
		let year = (new Date()).getFullYear();
		let arr=[];
		
		//
		for(let i=10; i>0; i--){
			arr.push(year-i);
		}
		
		//
		return arr;
	};
	
	
	
	/**
	 * 특/광역시를 제외한 시도 목록
	 */
	RiceDataObj.getSidos = function(){
		let arr=[];
		
		//
		for(let i=0; i<rdObj.sidos.length; i++){
			let d = rdObj.sidos[i];
			
			//
			if(-1 !== d.sidoName.indexOf('특별')){
				continue;
			}
			//
			if(-1 !== d.sidoName.indexOf('광역')){
				continue;
			}
			
			//
			arr.push(d);
		}
		
		//
		return arr;
	};
	
	//
	RiceDataObj.prototype.init = function(){
		
	};
	
	
	/**
	 * 모든 호출하여 cache에 저장
	 */
	RiceDataObj.prototype.getAllDatas = function(callbackFunction){
		console.log('>>.getAllDatas');
		
		//
		$.cs.submitAjax('./getAllRiceDatas.json', {}, function(res){
			//직불금 신청
			rdObj.directSbsidyReqsts = res.directSbsidyReqsts;
			//시도 단수
			rdObj.sidoDansus = res.sidoDansus;
			//시군구 단수
			rdObj.sigunguDansus = res.sigunguDansus;
			//시도
			rdObj.sidos = res.sidos;
			//시군구
			rdObj.sigungus = res.sigungus;
			
			//
			console.log('<<.getAllDatas', res.resultCode, res.resultMessage);
			//
			callbackFunction(res.resultCode, res.resultMessage);
		});
	};
	
	
	/**
	 * 년도별
	 */
	RiceDataObj.prototype.calcByYear = function(){
		
		//단수
		let _getDansu = function(datas, year){
			let dansu=0;
			
			//
			for(let i=0; i<datas.length; i++){
				let x = datas[i];
				let d = x._source;
				
				//
				if($.cs.isNull(d.DAN_SU)){
					continue;
				}
				//
				if(year !== parseInt(d.YEAR)){
					continue;
				}
				//
				if(-1 !== d.CTPRVN_NM.indexOf('특별') || -1 !== d.CTPRVN_NM.indexOf('자치')){
					continue;
				}

				//
				dansu += parseInt( d.DAN_SU.trim() );
			}
			
			//
			return dansu;
		};
		
		//면적
		let _getAr = function(datas, year, arKey){
			let k = '';
			
			//
			if($.cs.isNull(arKey)){
				k = 'dcsn_ar';
				
				if((new Date()).getFullYear() === year){
					let month = 1 + (new Date()).getMonth();
					if(4 <= month && month < 10){
						k = 'reqst_ar';
					}
				}
			}else{
				k = arKey;
			}
			
			//
			let ar=0;
			
			//
			for(let i=0; i<datas.length; i++){
				let x = datas[i];
				let d = x._source;
				
				//
				if($.cs.isNull(d[k])){
					continue;
				}
				//
				if(year !== parseInt(d.bsns_year)){
					continue;
				}
				//
				if(-1 !== d.ctprvn_nm.indexOf('특별') || -1 !== d.ctprvn_nm.indexOf('광역')){
					continue;
				}
				
				
				//
				ar += parseFloat(d[k]);
			}
			
			//
			return ar;
		};
		
	
		//
		let arr = [];
		
		//
		let years = RiceDataObj.getYears();
		
		//
		for(let i=0; i<years.length; i++){
			let yr = years[i];
			let dansu = _getDansu(rdObj.sidoDansus, yr);
			let ar = _getAr(rdObj.directSbsidyReqsts, yr);
			let reqstAr = _getAr(rdObj.directSbsidyReqsts, yr, 'reqst_ar');
			let dcsnAr = _getAr(rdObj.directSbsidyReqsts, yr, 'dcsn_ar');
			//
			arr.push({
				'year' : yr,
				'dansu' : dansu,
				'ar' : ar,
				'reqstAr' : reqstAr,
				'dcsnAr' : dcsnAr,
				'outtrn' : (dansu*ar),
				'outtrnReqstAr' : (dansu * reqstAr),
				'outtrnDcsnAr' : (dansu * dcsnAr)
			});
		}
		
		
		
		//
		console.log('<<RiceDataObj.calcByYear', arr);
		return arr;
	};
	
	
	
	/**
	 * @param yr 년도
	 */
	RiceDataObj.prototype.calcBySido = function(yr){
		//
		let getDansu = function(sidoCode, sidoName, year, sidoDansus){
			let dansu = 0;
			
			//
			for(let i=0; i<sidoDansus.length; i++){
				let item = sidoDansus[i];
				let d = item._source;
				
				//
				if($.cs.isNull(d.DAN_SU)){
					continue;
				}
				
				//
				if(year !== parseInt(d.YEAR)){
					continue;
				}
				
				//
				if((sidoName !== d.CTPRVN_NM)){
					continue;
				}
				
				//
				dansu += parseInt( d.DAN_SU.trim() );
			}
			
			//
			return dansu;
		};
		
		//
		let _getAr = function(sidoCode, sidoName, year, k){
			let ar = 0;
			
			//
			if($.cs.isNull(k)){
				k = 'dcsn_ar';
				
				let thisYear = (new Date()).getFullYear();
				if(thisYear === year){
					let mm = 1 + (new Date()).getMonth();
					if(4 <= mm && mm < 10){
						k = 'reqst_ar';
					}
				}
			}
						
			//
			for(let i=0; i<rdObj.directSbsidyReqsts.length; i++){
				let item = rdObj.directSbsidyReqsts[i];
				let d = item._source;
				
				//
				if($.cs.isNull(d[k])){
					continue;
				}
				//
				if(year !== parseInt(d.bsns_year)){
					continue;
				}				
				//
				if(sidoName !== d.ctprvn_nm){
					continue;
				}
				
				//
				ar += parseFloat( d[k].trim() );
			}
			
			//
			return ar;
		};
	
		//
		let arr = [];
		
		//
		for(let i=0; i<rdObj.sidos.length; i++){
			let item = rdObj.sidos[i];
			let sidoCode = item.sidoCode;
			let sidoName = item.sidoName;
			
			//
			if(-1 !== sidoName.indexOf('특별') || -1 !== sidoName.indexOf('광역')){
				continue;
			}
		
			//면적
			let ar = _getAr(sidoCode, sidoName, yr, null);
			//신청면적
			let reqstAr = _getAr(sidoCode, sidoName, yr, 'reqst_ar');
			//확정면적
			let dcsnAr = _getAr(sidoCode, sidoName, yr, 'dcsn_ar');
			
			//단수
			let dansu = getDansu(sidoCode, sidoName, yr, rdObj.sidoDansus);
			
			
			//
			arr.push({'year' : yr,
				'sidoName' : sidoName,
				'ar' : ar,
				'reqstAr' : reqstAr,
				'dcsnAr' : dcsnAr,
				'dansu' : dansu,
				'outtrn' : (dansu * ar),
				'outtrnReqstAr' : (dansu * reqstAr),
				'outtrnDcsnAr' : (dansu * dcsnAr)}
			);
		}
		
		
		//
		console.log('<<RiceDataObj.calcBySido', arr);
		return arr;
	};
	
	
	/**
	 * 
	 */
	RiceDataObj.prototype.calcBySigungu = function(sidoCode, sidoName, sigungus, year){
		//
		let getDansu = function(sigunguCode, sigunguName, year){
			let dansu = 0;
			
			//
			for(let i=0; i<rdObj.sigunguDansus.length; i++){
				let item = rdObj.sigunguDansus[i];
				let d = item._source;
				
				if((sigunguName === d.CTPRVN_NM) && (year === parseInt(d.YEAR))){
					if(!$.cs.isNull(d.DAN_SU)){
						dansu = parseInt( d.DAN_SU.trim() );
					}
				}
			}
			
			//
			return dansu;
		};
		
		//
		let getAr = function(sigunguCode, sigunguName, year, k){
			let ar = 0;
			
			//
			for(let i=0; i<rdObj.directSbsidyReqsts.length; i++){
				let item = rdObj.directSbsidyReqsts[i];
				let d = item._source;
				
				//
				if((sigunguName === d.signgu_nm) && (year === parseInt(d.bsns_year))){
					ar = parseFloat( d[k].trim() );
				}
			}
			
			//
			return ar;
		};
		
		//
		if($.cs.isEmpty(sidoCode) || $.cs.isEmpty(sidoName) || $.cs.isNull(sigungus) || 0 == sigungus.length){
			console.log('<<RiceDataObj.calcBySigungu - empty sidoCode or sidoName or sigungus');
			return [];
		}
		
		
		//
//		let year = (new Date()).getFullYear();
		let month = 1+(new Date()).getMonth();
		
		//
		let k='';
		if(4 <= month && month < 10){
			//신청면적으로 계산
			k = 'reqst_ar';
		}else{
			//확정면적으로 계산
			k = 'dcsn_ar';
		}
		
		//
		let arr = [];
		
		//
		for(let i=0; i<sigungus.length; i++){
			let item = sigungus[i];
			let sigunguCode = item.sigunguCode;
			let sigunguName = item.sigunguName;
			
			//
			let ar = getAr(sigunguCode, sigunguName, year, k);
			let dansu = getDansu(sigunguCode, sigunguName, year);
			let outtrnPredict = ar * dansu;
			
			//
			arr.push({year : year,
				sigunguCode : sigunguCode,
				sigunguName : sigunguName,
				ar : ar,
				outtrnPredict : outtrnPredict,
				outtrn : outtrnPredict});
		}
		
		//
		console.log('<<RiceDataObj.calcBySigungu', arr);
		return arr;
	};
	
	
	/**
	 * 시군구별 년도별 추이 계산
	 */
	RiceDataObj.prototype.calcBySigunguByYear = function(sidoCode, sidoName, sigungus){
		console.log('>>RiceDataObj.calcBySigunguByYear');
		
		/**
		 * sum(면적)
		 * 년도,월을 판단하여 확정면적|신청면적으로 합계 리턴
		 * @param datas 직불금 데이터 목록
		 * @param yr 필터링된 년도
		 * @param arGbn reqst|dcsn
		 */
		let _getSumAr = function(directSbsidyReqsts, sidoCode, sidoName, yr, arGbn){
			let k = '';
			
			//
			if(!$.cs.isNull(arGbn)){
				k = (RiceDataObj.REQST === arGbn ? 'reqst_ar' : 'dcsn_ar');
			}else{
				k='dcsn_ar';
				if(yr === (new Date()).getFullYear()){
					let mm = 1 + (new Date()).getMonth();
					if(4 <= mm && mm < 10){
						//신청면적
						k = 'reqst_ar';
					}
				}				
			}
			
			
			//
			let ar = 0;
			
			//
			for(let i=0; i<directSbsidyReqsts.length; i++){
				let item = directSbsidyReqsts[i];
				let d = item._source;
				
				//
				if(sidoName != d.ctprvn_nm){
					continue;
				}
				
				//
				if(yr != d.bsns_year){
					continue;
				}
				
				//
				ar += parseFloat(d[k]);
			}
			
			
			//
			return ar;
		};
		
		/**
		 * @param sigunguDansus 시군구 단수 목록
		 * @param sigungus 시군구 목록
		 * @yr 년도
		 */
		let _getSumDansu = function(sigunguDansus, sigungus, yr){
			let dansu=0;
			
			//단수 목록
			for(let i=0; i<sigunguDansus.length; i++){
				let item = sigunguDansus[i];
				let d = item._source;
				
				//
				if(yr != d.YEAR){
					continue;
				}
				
				//시군구 목록
				for(let j=0; j<sigungus.length; j++){
					let x = sigungus[j];
					if(d.CTPRVN_NM == x.sigunguName){
						if(!$.cs.isNull(d.DAN_SU)){
							dansu += parseFloat(d.DAN_SU);
						}
					}
				}
			}
			
			
			//
			return dansu;
		};
		
		
		
		//
		let arr=[];
		
		//연도 목록
		let years = RiceDataObj.getYears();
		
		//
		for(let i=0; i<years.length; i++){
			let yr = years[i];
			//sum(면적)
			let ar = _getSumAr(rdObj.directSbsidyReqsts, sidoCode, sidoName, yr);
			//신청면적
			let reqstAr = _getSumAr(rdObj.directSbsidyReqsts, sidoCode, sidoName, yr, RiceDataObj.REQST);
			//확정면적
			let dcsnAr = _getSumAr(rdObj.directSbsidyReqsts, sidoCode, sidoName, yr, RiceDataObj.DCSN);
			//단수
			let dansu = _getSumDansu(rdObj.sigunguDansus, sigungus, yr);
			
			
			//
			arr.push({'year' : yr,
				'ar' : ar,
				'reqstAr' : reqstAr,
				'dcsnAr' : dcsnAr,
				'dansu' : dansu,
				'outtrn' : (dansu * ar),
				'outtrnReqstAr' : (dansu * reqstAr),
				'outtrnDcsnAr' : (dansu * dcsnAr)
				});
		}
		
		//
		console.log('<<RiceDataObj.calcBySigunguByYear', arr);
		return arr;
		
	};
	
	
	
	/**
	 * 특정 시도의 연도별 생산량
	 * @param sidoCode 시도 코드
	 * @param sidoName 시도 명
	 */
	RiceDataObj.prototype.calcSidoOuttrnByYear = function(sidoCode, sidoName){
	
		//
		let years = RiceDataObj.getYears();
		
		//
		let arr=[];
		for(let i=0; i<years.length; i++){
			let yr = years[i];
			let dansu = RiceDataObj.getDansu(rdObj.sidoDansus, {'year':yr, 'sidoName':sidoName});
			let ar = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoName':sidoName});
			let reqstAr = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoName':sidoName, 'arKey':'reqst_ar'});
			let dcsnAr = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoName':sidoName, 'arKey':'dcsn_ar'});
			
			//
			arr.push({
				'year' : yr
				,'dansu' : dansu
				,'ar' : ar
				,'reqstAr' : reqstAr
				,'dcsnAr' : dcsnAr
				,'outtrn' : (dansu*ar)
				,'outtrnReqstAr' : (dansu * reqstAr)
				,'outtrnDcsnAr' : (dansu * dcsnAr)
			});
		}
		
		//
		console.log('<<RiceDataObj.calcSidoOuttrnByYear', arr);
		return arr;
	};
	
	
	/**
	 * 년도별 시도별
	 */
	RiceDataObj.prototype.byYearAndSido = function(){
		
		//
		let years = RiceDataObj.getYears();
		let sidos = RiceDataObj.getSidos();
		
		//
		let arr=[];
		//
		for(let i=0; i<years.length; i++){
			let yr = years[i];
			
			//
			
			//
			for(let j=0; j<sidos.length; j++){
				let sidoJson = sidos[j];
				
				//
				let dansu = RiceDataObj.getDansu(rdObj.sidoDansus, {'year':yr, 'sidoCode':sidoJson.sidoCode, 'sidoName':sidoJson.sidoName});
				let ar = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoCode':sidoJson.sidoCode, 'sidoName':sidoJson.sidoName});
				let reqstAr = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoCode':sidoJson.sidoCode, 'sidoName':sidoJson.sidoName, 'arKey':'reqst_ar'});
				let dcsnAr = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoCode':sidoJson.sidoCode, 'sidoName':sidoJson.sidoName, 'arKey':'dcsn_ar'});
				
				//
				arr.push({
					'year' : yr,
					'sidoCode' : sidoJson.sidoCode,
					'sidoName' : sidoJson.sidoName,
					'ar' : ar,
					'reqstAr' : reqstAr,
					'dcsnAr' : dcsnAr,
					'dansu' : dansu,
					'outtrn' : (dansu * ar),
					'outtrnReqstAr' : (dansu * reqstAr),
					'outtrnDcsnAr' : (dansu * dcsnAr)
				});
			}
			
		}
		
		//
		console.log('byYearAndSido', arr);
		return arr;
	};
	
	
	
	/**
	 * 년도별 시군구별
	 */
	RiceDataObj.prototype.byYearAndSigungu = function(sidoCode, sidoName){
		
		//
		let years = RiceDataObj.getYears();
		let sigungus = RiceDataObj.getSigungus(sidoName);
		
		//
		let arr=[];
		//
		for(let i=0; i<years.length; i++){
			let yr = years[i];
			
			//
			
			//
			for(let j=0; j<sigungus.length; j++){
				let sigunguJson = sigungus[j];
				
				//
				let dansu = RiceDataObj.getDansu(rdObj.sigunguDansus, {'year':yr, 'sigunguName':sigunguJson.sigunguName});
				let ar = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoName':sidoName, 'sigunguName':sigunguJson.sigunguName});
				let reqstAr = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoName':sidoName, 'sigunguName':sigunguJson.sigunguName, 'arKey':'reqst_ar'});
				let dcsnAr = RiceDataObj.getAr(rdObj.directSbsidyReqsts, {'year':yr, 'sidoName':sidoName, 'sigunguName':sigunguJson.sigunguName, 'arKey':'dcsn_ar'});
				
				//
				arr.push({
					'year' : yr,
					'sidoCode' : sidoCode,
					'sidoName' : sidoName,
					'sigunguCode' : sigunguJson.sigunguCode,
					'sigunguName' : sigunguJson.sigunguName,
					'ar' : ar,
					'reqstAr' : reqstAr,
					'dcsnAr' : dcsnAr,
					'dansu' : dansu,
					'outtrn' : (dansu * ar),
					'outtrnReqstAr' : (dansu * reqstAr),
					'outtrnDcsnAr' : (dansu * dcsnAr)
				});
			}
			
		}
		
		//
//		console.log('byYearAndSigungu', arr);
		return arr;
	};
	
	
	RiceDataObj.prototype.testSidoOuttrn = function(year){
		let datas = rdObj.byYearAndSido();
		//
		let ar=0;
		for(let i=0; i<datas.length; i++){
			let d = datas[i];
			
			//
			if(year !== d.year){
				continue;
			}
			
			//
			ar += d.outtrnDcsnAr;
		}
		
		//
		console.log(ar);
	};
});

//
let rdObj = new RiceDataObj();