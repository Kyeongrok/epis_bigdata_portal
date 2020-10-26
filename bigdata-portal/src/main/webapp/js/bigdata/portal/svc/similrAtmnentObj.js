/**
 * 유사 경영체
 * @since
 * 	0220	init
 */
let SimilrAtmnentObj = (function(){

	//cache
	SimilrAtmnentObj.datas = [];
	
	/**
	 * 
	 */
	SimilrAtmnentObj.prototype.init = function(){
		
	};
	
	
	/**
	 * 데이터 요청
	 * @param bsnsCodes 아그릭스 사업 코드 목록. 필수
	 * @param sexdstn 성별. 선택 1:남 2:여
	 * @param age 나이. 선택
	 * @param farmCareer 영농 경력. 선택
	 * @param prdlstSetCode 품목군 코드. 선택
	 * @param prdlstCode 품목 코드. 선택
	 * @param ctvtTyCode 재배 유형 코드. 선택
	 * @param arOrCo 면적 or 두수. 선택
	 */
	SimilrAtmnentObj.prototype.getDatasAsync = function(bsnsCodes, sexdstn, age, farmCareer, prdlstSetCode, prdlstCode, ctvtTyCode, arOrCo, callbackFunction){
		console.log('>>SimilrAtmnentObj.getDatasAsync');
		
		//사업 목록이 없으면
		if(0 == bsnsCodes.length){
			console.log('<<SimilrAtmnentObj.getDatasAsync', 'empty bsnsCodes');
			
			saObj.datas = [];
			
			callbackFunction(saObj.datas);
			
			//
			return;
		}
		
		let p = {};
		p.bsnsCodes = bsnsCodes.join(',');
		//성별
		if(!$.cs.isEmpty(sexdstn)){
			p.sexdstn = ('M' === sexdstn ? '1' : '2');
		}
		//나이
		if(!$.cs.isEmpty(age)){
			p.age = age;
		}
		//영농 경력
		if(!$.cs.isEmpty(farmCareer)){
			p.farmCareer = farmCareer;
		}
		//품목군 코드
		if(!$.cs.isEmpty(prdlstSetCode)){
			p.prdlstSetCode = prdlstSetCode;			
		}
		//품목 코드
		if(!$.cs.isEmpty(prdlstCode)){
			p.prdlstCode = prdlstCode;
		}
		//재배 유형 코드
		if(!$.cs.isEmpty(ctvtTyCode)){
			p.ctvtTyCode = ('시설' === ctvtTyCode ? 'sisul' : 'noji'); 
		}
		//면적 or 두수
		if(!$.cs.isEmpty(arOrCo)){
			p.arOrCo = arOrCo;
		}
		
		//
		$.cs.submitAjax('./getSimilrAtmnentCounts.json', p, function(res){
			console.log('<<SimilrAtmnentObj.getDatasAsync', res.datas.length);
			
			//cache
			saObj.datas = res.datas;
			
			//
			callbackFunction(res.datas);
		});
	};
	
	

	/**
	 * 조회된(필터링된) 데이터 목록에서 아그릭스 사업 코드 목록만 추출
	 */
	SimilrAtmnentObj.prototype.getAgrixBsnsCodes = function(datas){
		let arr=[];
		
		//
		if($.cs.isNull(datas) || 0 == datas.length){
			return arr;
		}
		
		//
		for (let i = 0; i < datas.length; i++) {
			let x = datas[i];
			let d = x._source;
			
			//아그릭스 사업만 처리
			if('N' !== d.BSNS_CODE){
				arr.push(d.BSNS_CODE);
			}
		}
		
		//
		console.log('<<SimilrAtmnentObj.getAgrixBsnsCodes', arr);
		return arr;
	};
});

let saObj = new SimilrAtmnentObj();