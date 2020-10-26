/**
 * 결과보기 화면에서 사용될 차트 들
 *
 *
 */

/**지역 정주 여건 차트*/
let AreaSetlCndChart = (function() {
	AreaSetlCndChart.prototype.refreshChart = function(){
		this.fixesRetnFmlgRadarChart(); // 맞춤 귀농 레이더 차트
		this.ladDelngAmountChart(); // 토지 실거래 가
		this.ladRentAmountChart(); // 토지 임대 가
		this.upperLadDelngAmountChart(); // 읍면동별 토지 실거래 가
		this.upperLadRentAmonutChart(); // 읍면동별 토지 임대 가
		this.upperFarmerAgeChart(); // 귀농인 연령대
		this.upperFarmerMvtChart(); // 귀농인 지역별 전출
		this.upperFarmerCtvtChart(); // 귀농인 지역별 재배 품목
		this.edcAccesPosbltyChart(); // 교육 접근성
		this.upperEdcAccesPosbltyChart(); // 읍면동별 교육 접근성
		this.edcCoChart(); // MK 교육 시설 수
		this.upperEdcCoChart(); // MK 읍면동별 교육 시설 수
		this.trnsportAccesPosbltyChart(); // 교통 접근성
		this.upperTrnsportAccesPosbltyChart(); // 읍면동별 교통 접근성
		this.hsptlAccesPosbltyChart(); // MK 의료 접근성
		this.upperHsptlAccesPosbltyChart(); // MK 읍면동별 의료 접근성
		this.cnvncAccesPosbltyChart(); // 편의 접근성
		this.upperCnvncAccesPosbltyChart(); // 읍면동별 편의 접근성
		this.cnvncCoChart(); // 편의 시설 수
		this.upperCnvncCoChart(); // 읍면동별 편의 시설 수
		this.clturCoChart(); // 문화 시설 수
		this.upperClturCoChart(); // 읍면동별 문화 시설 수
		this.refreshCtvtChart(); // 재배품목 차트 새로고침
	}

	/*재배 품목을 클릭했을 때 재배 품목에 해당하는 차트만 새로고침*/
	AreaSetlCndChart.prototype.refreshCtvtChart = function(){
		this.yearCtvtAra(); // 품목별 연도별 재배면적
		this.wholeSaleChart(); // 도매시장 경락 가격 현황
		this.allAgeCtvt(); // 전체 영농인 품목별 연령대별 농가
		this.beginAgeCtvt(); // 초기 영농인 품목별 연령대별 농가
		this.avgPrdlstIndex(); // 생산유통안정성
	}

	/**맞춤 귀농 정보 레이더 차트*/
	AreaSetlCndChart.prototype.fixesRetnFmlgRadarChart = function() {
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};

		p.ctx = "sc_z_0_0"
		p.type = "radar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [ '교육', '경제', '문화', '편의', '의료', '교통' ];
		p.innerLabels = [];
		p.innerDatas = [];
		p.innerColors = [];
//		p.innerColors = [ "rgb(163, 186, 224, 0.0)", "rgb(136, 210, 206, 0.0)", "rgb(253, 207, 77, 0.0)"];
		let endIndex = (dataObj.areaInfo.length > 3) ? 3 : dataObj.areaInfo.length;
		for(let i=0; i<endIndex; i++){
			let areaInfo = dataObj.areaInfo[i];
			let addr = areaInfo.ctprvn + ' ' + areaInfo.signgu + ' ' + areaInfo.emd;

			p.innerLabels.push(addr);
			p.innerDatas.push(areaInfo.edcAvgAccesPosbltyScoreScore); // 교육
			p.innerDatas.push(areaInfo.ladAvrgDelngAmountScoreScore); // 경제
			p.innerDatas.push(areaInfo.clturAvgCoscoreScore); // 문화
			p.innerDatas.push(areaInfo.cnvncMrktAvgAccesPosbltyScoreScore); // 편의
			p.innerDatas.push(areaInfo.hsptlAvgAccesPosbltyScoreScore); // 의료
			p.innerDatas.push(areaInfo.trnsportAvgAccesPosbltyScoreScore); // 교통

			let transparency = 0.0; // 레이더차트 투명도
			if(i == selectAreaInfo.index){
				transparency = 0.7;
			}
			switch(i){
			case 0: p.innerColors.push( "rgba(163, 186, 224, "+ transparency+")" );
				break;
			case 1: p.innerColors.push( "rgba(136, 210, 206, "+ transparency+")" );
				break;
			case 2: p.innerColors.push( "rgba(253, 207, 77, "+ transparency+")" );
				break;
			}

		}
		p.innerBorderColors = [ "rgb(163, 186, 224)", "rgb(136, 210, 206)", "rgb(253, 207, 77)"];
		p.deli    = 3;
		p.scales = false;

		console.log(p);
		pageObj.drawChart(p);
	}
	/**농지가격 > 농지 실거래가*/
	AreaSetlCndChart.prototype.ladDelngAmountChart = function() {
		let selectAreaInfo = pageObj.getSelectedAreaInfo();

		let p = {};
		p.ctx = "sc_a_0_0"
		p.type = "bar";
		p.width = 270;
		p.height = 200;
//		p.title = selectAreaInfo.emd+" 농지 실거래 가격";
//		p.subTitle = "평균 거래가격(원/3.3㎥)";
		p.deli = 0;
		p.labels = [ "전", "답", "과수" ];
		p.datas = [ dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.ladBfeAvrgDelngAmount.toFixed(0)
					, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.ladRicfldAvrgDelngAmount.toFixed(0)
					, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.orchrdAvrgDelngAmount.toFixed(0) ];
		p.colors = [ "rgb(136, 210, 206)", "rgb(163, 186, 224)", "rgb(247, 179, 184)" ];


		pageObj.drawChart(p);
	}

	/**농지가격 > 농지 임대가*/
	AreaSetlCndChart.prototype.ladRentAmountChart = function() {
		let selectAreaInfo = pageObj.getSelectedAreaInfo();

		let p = {};
		p.ctx = "sc_a_0_2"
		p.type = "bar";
		p.width = 270;
		p.height = 200;
//		p.title = selectAreaInfo.emd+" 농지 실거래 가격";
//		p.subTitle = "평균 거래가격(원/3.3㎥)";
		p.deli = 0;
		p.labels = [ "전", "답", "과수" ];
		p.datas = [ dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.ladBfeAvrgRentAmount.toFixed(0)
					, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.ladRicfldAvrgRentAmount.toFixed(0)
					, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.orchrdAvrgRentAmount.toFixed(0) ];
		p.colors = [ "rgb(136, 210, 206)", "rgb(163, 186, 224)", "rgb(247, 179, 184)" ];

		pageObj.drawChart(p);

	}

	/** 농지가격 > 시군구 읍면동별 농지 실거래가 */
	AreaSetlCndChart.prototype.upperLadDelngAmountChart = function() {
		let selectAreaInfo = pageObj.getSelectedAreaInfo();

		let p = {};
		p.ctx = "sc_a_0_1"; //순창_지역 정주 여건 정보_농지가격_2nd 차트
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.unit = "만원";
		p.deli    = 3;
		p.labels = [];
		p.innerLabels = ["전","답","과수"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd); // 시군구 하위 읍면동
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].ladBfeAvrgDelngAmount.toFixed(0)); // 시군구, 읍면동 별 토지 전 실거래
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].ladRicfldAvrgDelngAmount.toFixed(0)); // 시군구, 읍면동 별 토지 답 실거래
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].orchrdAvrgDelngAmount.toFixed(0)); // 시군구, 읍면동 별 토지 과수원 실거래
			}
		}
		p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

		pageObj.drawChart(p);
	}

	/** 농지가격 > 시군구 읍면동별 농지 임대가가 */
	AreaSetlCndChart.prototype.upperLadRentAmonutChart = function() {
		let selectAreaInfo = pageObj.getSelectedAreaInfo();

		let p = {};
		p.ctx = "sc_a_0_3"; //순창_지역 정주 여건 정보_농지가격_2nd 차트
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 3;
		p.labels = [];
		p.innerLabels = ["전","답","과수"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd); // 시군구 하위 읍면동
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].ladBfeAvrgRentAmount.toFixed(0)); // 시군구, 읍면동 별 토지 전 실거래
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].ladRicfldAvrgRentAmount.toFixed(0)); // 시군구, 읍면동 별 토지 답 실거래
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].orchrdAvrgRentAmount.toFixed(0)); // 시군구, 읍면동 별 토지 과수원 실거래
			}
		}
		p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

		pageObj.drawChart(p);
	}

	/**귀농인 > 연령대별 귀농인*/
	AreaSetlCndChart.prototype.upperFarmerAgeChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_1_0"
		p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [ "20대", "30대", "40대", "50대", "60대 이상" ];
		p.datas = [];
		for(let i=0; i<p.labels.length; i++){
			let data = 0;
			let label = p.labels[i];

			for(let j=0; j<dataObj.areaInfo[selectAreaInfo.index].upperAreaAgeSctn.length; j++){
				let d = dataObj.areaInfo[selectAreaInfo.index].upperAreaAgeSctn[j];
				let ageSctn = d.ageSctn;
				let chartData = d.ageSctnCnt;
				if(label == ageSctn) data = chartData;
			}

			p.datas.push(data);
		}
		p.colors = [ "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)" ];

		pageObj.drawChart(p);
	}


	/**귀농인 > 전출지역*/
	AreaSetlCndChart.prototype.upperFarmerMvtChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_1_1"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperAreaMvtInfo.length; i++){
			p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperAreaMvtInfo[i].mvtCtprvn)
		}
		p.datas = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperAreaMvtInfo.length; i++){
			p.datas.push(dataObj.areaInfo[selectAreaInfo.index].upperAreaMvtInfo[i].mvtCtprvnCnt)
		}
		p.colors = [ "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)" ];
		console.log("sc_a_1_1!!!");
		console.log(dataObj.areaInfo[selectAreaInfo.index].upperAreaMvtInfo);
		pageObj.drawChart(p);
	}
	/**귀농인 > 재배작물*/
	AreaSetlCndChart.prototype.upperFarmerCtvtChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_1_2"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperAreaCtvtInfo.length; i++){
			p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperAreaCtvtInfo[i].itemNm)
		}
		p.datas = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperAreaCtvtInfo.length; i++){
			p.datas.push(dataObj.areaInfo[selectAreaInfo.index].upperAreaCtvtInfo[i].itemNmCnt)
		}
		p.colors = [ "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)", "rgb(136, 210, 206)" ];
		console.log("sc_a_1_2!!!");
		console.log(dataObj.areaInfo[selectAreaInfo.index].upperAreaCtvtInfo);
		pageObj.drawChart(p);
	}

	/**교육 > 지역 접근성*/
	AreaSetlCndChart.prototype.edcAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_2_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ['초등학교','중학교','고등학교'];
		p.datas = [
			dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.eleschAccesPosblty.toFixed(0)
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.mskulAccesPosblty.toFixed(0)
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.hgschlAccesPosblty.toFixed(0)

		];
		p.colors = [ "rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)" ];

		pageObj.drawChart(p);
	}

	/**교육 > 읍면동별 지역 접근성*/
	AreaSetlCndChart.prototype.upperEdcAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_2_1";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 3;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd);
			}
		}
		p.innerLabels = ["초등학교","중학교","고등학교"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].eleschAccesPosblty.toFixed(0));
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].mskulAccesPosblty.toFixed(0));
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].hgschlAccesPosblty.toFixed(0));
			}
		}
		p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];
		pageObj.drawChart(p);
	}
	
	/**교육 > 학원 및 교습소 시설 수*/
	AreaSetlCndChart.prototype.edcCoChart = function(){
		console.log("학원및교습소!!!");
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_2_2"
		p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ["학원 및 교습소"];
		p.datas = [
			dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.instutCo			

			];
		p.colors  = ["rgb(136, 210, 206)"];

		pageObj.drawChart(p);
	}

	/**교육 > 읍면동별 학원 및 교습소 시설 수*/
	AreaSetlCndChart.prototype.upperEdcCoChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_2_3";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 1;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd);
			}
		}
		p.innerLabels = ["학원 및 교습소"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].instutCo);				

			}
		}
		p.innerColors  = ["rgb(136, 210, 206)"];
		pageObj.drawChart(p);
	}

	/**교통 > 지역 접근성*/
	AreaSetlCndChart.prototype.trnsportAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_3_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ["버스","기차","공항"];
		p.datas = [
			dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.busTrminlAccesPosblty.toFixed(0)
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.trainStatnAccesPosblty.toFixed(0)
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.arprtAccesPosblty.toFixed(0)

			];
		p.colors = [ "rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)" ];

		pageObj.drawChart(p);
	}

	/**교통 > 읍면동별 지역 접근성*/
	AreaSetlCndChart.prototype.upperTrnsportAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_3_1";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 3;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd);
			}
		}
		p.innerLabels = ["버스","기차","공항"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].busTrminlAccesPosblty.toFixed(0));
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].trainStatnAccesPosblty.toFixed(0));
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].arprtAccesPosblty.toFixed(0));
			}
		}
		p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];
		pageObj.drawChart(p);
	}

/** MK 의료 > 지역 접근성*/
	AreaSetlCndChart.prototype.hsptlAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_6_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ["공공의료","일반병원","종합병원"];
		p.datas = [
			dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.publicMlfltAccesPosblty.toFixed(0)
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.gnrlHsptlAccesPosblty.toFixed(0)
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.gnrlzHsptlAccesPosblty.toFixed(0)

			];
		p.colors = [ "rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)" ];

		pageObj.drawChart(p);
	}

/** MK 의료 > 읍면동별 지역 접근성*/
	AreaSetlCndChart.prototype.upperHsptlAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_6_1";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 3;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd);
			}
		}
		p.innerLabels = ["공공의료","일반병원","종합병원"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].publicMlfltAccesPosblty.toFixed(0));
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].gnrlHsptlAccesPosblty.toFixed(0));
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].gnrlzHsptlAccesPosblty.toFixed(0));
			}
		}		
		p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];
		pageObj.drawChart(p);
	}

	/**편의 > 수*/
	AreaSetlCndChart.prototype.cnvncAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_4_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ["대형점포","전통시장"];
		p.datas = [
			dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.lrsclStorAccesPosblty.toFixed(0)
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.trditMrktAccesPosblty.toFixed(0)

			];
		p.colors = [ "rgb(136, 210, 206)","rgb(163, 186, 224)"];

		pageObj.drawChart(p);
	}

	/**편의 > 읍면동별 수*/
	AreaSetlCndChart.prototype.upperCnvncAccesPosbltyChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_4_1";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 2;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd);
			}
		}
		p.innerLabels = ["대형점포","전통시장"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].lrsclStorAccesPosblty.toFixed(0));
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].trditMrktAccesPosblty.toFixed(0));
			}
		}
		p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];
		pageObj.drawChart(p);
	}

	/**편의 > 시설 수*/
	AreaSetlCndChart.prototype.cnvncCoChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_4_2"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ["편의점","은행", "극장"];
		p.datas = [
			dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.cvnstrCo
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.bankCo
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.theatCo

			];
		p.colors = [ "rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)" ];

		pageObj.drawChart(p);
	}

	/**편의 > 읍면동별 시설 수*/
	AreaSetlCndChart.prototype.upperCnvncCoChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_4_3";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 3;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd);
			}
		}
		p.innerLabels = ["편의점","은행", "극장"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].cvnstrCo);
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].bankCo);
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].theatCo);
			}
		}
		p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];
		pageObj.drawChart(p);
	}

	/**문화 > 시설 수*/
	AreaSetlCndChart.prototype.clturCoChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_5_0"
		p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ["문화의 집","도서관", "박물관", "지방문화원"];
		p.datas = [
			dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.clturHouseCo
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.lbrryCo
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.museumCo
			, dataObj.areaInfo[selectAreaInfo.index].areaSetlCnd.lcltyClturHouseCo

			];
		p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];

		pageObj.drawChart(p);
	}

	/**문화 > 읍면동별 시설 수*/
	AreaSetlCndChart.prototype.upperClturCoChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let p = {};
		p.ctx = "sc_a_5_1";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 4;
		p.labels = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.labels.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].emd);
			}
		}
		p.innerLabels = ["문화의 집","도서관", "박물관", "지방문화원"];
		p.innerDatas  = [];
		for(let i=0; i<dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea.length; i++){
			if(i<8){
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].clturHouseCo);
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].lbrryCo);
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].museumCo);
				p.innerDatas.push(dataObj.areaInfo[selectAreaInfo.index].upperRecomendArea[i].lcltyClturHouseCo);

			}
		}
		p.innerColors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];
		pageObj.drawChart(p);
	}

	/**재배 품목 > 재배면적 추이(바, 라인 차트)*/
	AreaSetlCndChart.prototype.yearCtvtAra = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let pageSelectCtvtInfo = pageObj.getSelectCtvtInfo();
		let selectCtvtInfo = dataObj.areaInfo[selectAreaInfo.index].fixesCtvt[pageSelectCtvtInfo.index];

		let p = {};
		let beginFarmerData = [];
		let allFarmerData = [];

		p.ctx = "sc_b_0_0_0";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = '';
		p.legend = true;
		p.deli    = 0;
		p.labels = [];
		for(let i=0; i<selectCtvtInfo.allRetnYearCtvtAra.length; i++){
			p.labels.push(selectCtvtInfo.allRetnYearCtvtAra[i].gigunYear); // 년도
			allFarmerData.push(selectCtvtInfo.allRetnYearCtvtAra[i].avgOfactAgrldAra.toFixed(0)); // 전체 영농인
			beginFarmerData.push(selectCtvtInfo.beginRetnYearCtvtAra[i].avgCtvtArSm.toFixed(0)); // 초기 귀농인
		}

		p.innerDatas  = [
			{
				type: 'line',
				label: '초기 귀농인',
				borderColor: "rgb(163, 186, 255)",
				backgroundColor: "rgb(163, 186, 255)",
				borderWidth: 2,
				fill: false,
				data: beginFarmerData
			}
			, {
				type: 'bar',
				label: '전체귀농인',
				backgroundColor: "rgb(136, 210, 206)",
				data: allFarmerData,
					borderColor: "rgb(136, 210, 206)",
					borderWidth: 2,
			}
			];
		pageObj.drawComboChart(p);


	}

	/**재배 품목 > 전체 영농인 연령대별 재배 농가 현황*/
	AreaSetlCndChart.prototype.allAgeCtvt = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let pageSelectCtvtInfo = pageObj.getSelectCtvtInfo();
		let selectCtvtInfo = dataObj.areaInfo[selectAreaInfo.index].fixesCtvt[pageSelectCtvtInfo.index];

		let p = {};
		p.ctx = "sc_b_0_2_0";
		p.type    = "doughnut";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.deli    = 0;
		p.labels = ['40대 미만', '40대', '50대', '60대 이상'];
		p.datas = [];
		p.colors  = ["rgb(141, 232, 139)","rgb(243, 207, 108)","rgb(251, 105, 192)","rgb(93, 225, 239)"];


		for(let i=0; i<p.labels.length; i++){
			let data = 0;
			let label = p.labels[i];

			for(let j=0; j<selectCtvtInfo.allRetnAgeCtvt.length; j++){
				let d = selectCtvtInfo.allRetnAgeCtvt[j];
				let ageSctn = d.ageSctn;
				let chartData = d.sclasCnt;
				if(label == ageSctn) data = chartData;
			}

			p.datas.push(data);

			/*라벨 옆에 실제 데이터 수 셋팅*/
//			//공백 처리
			let labelLeng = p.labels[i].length;
			let spaceCnt = 20 - (labelLeng*2.5);

			for(let z=0; z<=spaceCnt; z++){
				p.labels[i] += ' ';
			}
			p.labels[i] = p.labels[i] + $.cs.addComma(data);
		}

		pageObj.drawDoughnutChart(p);

	}


	/**재배 품목 > 도매시장 경락가격 현황*/
	AreaSetlCndChart.prototype.wholeSaleChart = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo(); 
		let pageSelectCtvtInfo = pageObj.getSelectCtvtInfo();
		let selectCtvtInfo = dataObj.areaInfo[selectAreaInfo.index].fixesCtvt[pageSelectCtvtInfo.index];

		let allAvgWholeSale2018 = selectCtvtInfo.allAvgWholeSale2018.aggregations.avg.value;
		let areaAvgWholeSale2018 = selectCtvtInfo.areaAvgWholeSale2018.aggregations.avg.value;
		let allAvgWholeSale2019 = selectCtvtInfo.allAvgWholeSale2019.aggregations.avg.value;
		let areaAvgWholeSale2019 = selectCtvtInfo.areaAvgWholeSale2019.aggregations.avg.value;
		let allAvgWholeSale2020 = selectCtvtInfo.allAvgWholeSale2020.aggregations.avg.value;
		let areaAvgWholeSale2020 = selectCtvtInfo.areaAvgWholeSale2020.aggregations.avg.value;

		//값이 null이면 숫자 0을 입력한다(toFixed 함수를 사용하기 위함)
		allAvgWholeSale2018 = (allAvgWholeSale2018 == null) ? 0 : allAvgWholeSale2018;
		areaAvgWholeSale2018 = (areaAvgWholeSale2018 == null) ? 0 : areaAvgWholeSale2018;
		allAvgWholeSale2019 = (allAvgWholeSale2019 == null) ? 0 : allAvgWholeSale2019;
		areaAvgWholeSale2019 = (areaAvgWholeSale2019 == null) ? 0 : areaAvgWholeSale2019;
		allAvgWholeSale2020 = (allAvgWholeSale2020 == null) ? 0 : allAvgWholeSale2020;
		areaAvgWholeSale2020 = (areaAvgWholeSale2020 == null) ? 0 : areaAvgWholeSale2020;


		let p = {};
		p.ctx = "sc_b_0_1_0";
		p.type    = "bar";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.legend = true;
		p.deli    = 2;
		p.labels = ['2018', '2019' , '2020'];
		p.innerLabels = ["전체", selectCtvtInfo.signgu];
		p.innerDatas  = [];

		p.innerDatas.push(allAvgWholeSale2018.toFixed(0));
		p.innerDatas.push(areaAvgWholeSale2018.toFixed(0));
		p.innerDatas.push(allAvgWholeSale2019.toFixed(0));
		p.innerDatas.push(areaAvgWholeSale2019.toFixed(0));
		p.innerDatas.push(allAvgWholeSale2020.toFixed(0));
		p.innerDatas.push(areaAvgWholeSale2020.toFixed(0));
		

		p.innerColors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];
		pageObj.drawChart(p);
	}






	/**재배 품목 > 초기 영농인 연령대별 재배 농가 현황*/
	AreaSetlCndChart.prototype.beginAgeCtvt = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let pageSelectCtvtInfo = pageObj.getSelectCtvtInfo();
		let selectCtvtInfo = dataObj.areaInfo[selectAreaInfo.index].fixesCtvt[pageSelectCtvtInfo.index];

		let p = {};
		p.ctx = "sc_b_0_2_1";
		p.type    = "doughnut";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.deli    = 0;
		p.labels = ['40대 미만', '40대', '50대', '60대 이상'];
		p.datas = [];
		p.colors  = ["rgb(141, 232, 139)","rgb(243, 207, 108)","rgb(251, 105, 192)","rgb(93, 225, 239)"];
		for(let i=0; i<p.labels.length; i++){
			let data = 0;
			let label = p.labels[i];

			for(let j=0; j<selectCtvtInfo.beginRetnAgeCtvt.length; j++){
				let d = selectCtvtInfo.beginRetnAgeCtvt[j];
				let ageSctn = d.ageSctn;
				let chartData = d.sclasCnt;
				if(label == ageSctn) data = chartData;
			}

			p.datas.push(data);

			/*라벨 옆에 실제 데이터 수 셋팅*/
//			//공백 처리
			let labelLeng = p.labels[i].length;
			let spaceCnt = 20 - (labelLeng*2.5);

			for(let z=0; z<=spaceCnt; z++){
				p.labels[i] += ' ';
			}
			p.labels[i] = p.labels[i] + $.cs.addComma(data);
		}

		pageObj.drawDoughnutChart(p);

	}

	/**재배 품목 > 생산/유통 안정성*/
	AreaSetlCndChart.prototype.avgPrdlstIndex = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		let pageSelectCtvtInfo = pageObj.getSelectCtvtInfo();
		let selectCtvtInfo = dataObj.areaInfo[selectAreaInfo.index].fixesCtvt[pageSelectCtvtInfo.index];

		let p = {};
		p.ctx = "sc_b_0_3_0";
		p.type = "bar";
		p.width = 270;
		p.height = 200;
//		p.showText = 'N';
		p.deli = 0;
		p.labels = ["전체평균", selectCtvtInfo.prdlstNm];
		p.datas = [
			selectCtvtInfo.avgPrdlstIndex.index1.toFixed(2),
			selectCtvtInfo.index1.toFixed(2)
			];
		p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];
		p.yAxes_notComma = true;
		pageObj.drawChart(p);

		p.ctx = "sc_b_0_3_1";
		p.datas = [
			selectCtvtInfo.avgPrdlstIndex.index3.toFixed(2),
			selectCtvtInfo.index3.toFixed(2),
		];
		pageObj.drawChart(p);

		p.ctx = "sc_b_0_3_2";
		p.datas = [
			selectCtvtInfo.avgPrdlstIndex.index2.toFixed(2),
			selectCtvtInfo.index2.toFixed(2),
		];
		pageObj.drawChart(p);

	}



});


/**유사귀농인 차트*/
let SimilrRetnFmlgChart = (function(){

	SimilrRetnFmlgChart.prototype.refreshChart = function(){
		this.retnFmlgTrnsfrnArea();
		this.similrRetnFmlgTrnsfrnArea();
		this.beginRetnCtvtCnt();
		this.similrRetnFmlgCtvt();
	}

	/*전체 귀농인 상위 전입지역*/
	SimilrRetnFmlgChart.prototype.retnFmlgTrnsfrnArea = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		if(dataObj.similr == null) return;
		let similrRetnFmlg = dataObj.similr.retnFmlgTrnsfrnArea;


		let p = {};
		p.ctx = "sc_c_0_1"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [];
		p.datas = [];

		for(let i=0; i<similrRetnFmlg.length; i++){
			let addr = similrRetnFmlg[i].ctprvn
						+ ' '
						+ similrRetnFmlg[i].signgu;
			p.labels.push(addr);
			p.datas.push(similrRetnFmlg[i].cnt);
		}

		p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];

		pageObj.drawChart(p);
	}


	/*유사 귀농인 상위 전입지역*/
	SimilrRetnFmlgChart.prototype.similrRetnFmlgTrnsfrnArea = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		if(dataObj.similr == null) return;
		let similrRetnFmlg = dataObj.similr.similrRetnFmlgTrnsfrnArea;


		let p = {};
		p.ctx = "sc_c_1_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [];
		p.datas = [];

		for(let i=0; i<similrRetnFmlg.length; i++){
			let addr = similrRetnFmlg[i].ctprvn
				+ ' '
				+ similrRetnFmlg[i].signgu;
			p.labels.push(addr);
			p.datas.push(similrRetnFmlg[i].cnt);
		}

		p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];

		pageObj.drawChart(p);
	}


	/*초기영농인 재배 품목*/
	SimilrRetnFmlgChart.prototype.beginRetnCtvtCnt = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		if(dataObj.similr == null) return;
		let similrRetnFmlg = dataObj.similr.beginRetnCtvtCntList[selectAreaInfo.index];


		let p = {};
		p.ctx = "sc_c_2_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [];
		p.datas = [];

		for(let i=0; i<similrRetnFmlg.length; i++){
			p.labels.push(similrRetnFmlg[i].sclas);
			p.datas.push(similrRetnFmlg[i].sclasCnt);
		}

		p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];

		pageObj.drawChart(p);
	}

	/*유사귀농인 재배 품목*/
	SimilrRetnFmlgChart.prototype.similrRetnFmlgCtvt = function(){
		let selectAreaInfo = pageObj.getSelectedAreaInfo();
		if(dataObj.similr == null) return;
		let similrRetnFmlg = dataObj.similr.similrRetnFmlgCtvt;


		let p = {};
		p.ctx = "sc_c_3_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [];
		p.datas = [];

		for(let i=0; i<similrRetnFmlg.length; i++){
			p.labels.push(similrRetnFmlg[i].itemNm);
			p.datas.push(similrRetnFmlg[i].sclasCnt);
		}

		p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];

		pageObj.drawChart(p);
	}

})

let SelectAreaCtvtChart = (function(){
	SelectAreaCtvtChart.prototype.refreshChart = function(){
		this.allRetnMenSttusChart();
		this.allRetnMenSttusAgeSctnChart();
		this.allRetnCtvtAreaYearChart();
		this.allRetnCtvtAreaAgeSctnChart();
		this.beginRetnSttusMenCntChart();
		this.beginRetnSttusAreaCntChart();
		this.beginRetnSttusCtvtChart();
	}

	/*선택지역 및 품목 분석 > 농업인 현황 > 재배농업인현황*/
	SelectAreaCtvtChart.prototype.allRetnMenSttusChart = function(){
		let selectAreaCtvtInfo = dataObj.selectAreaCtvtInfo;

		if(selectAreaCtvtInfo.allRetnMenSttus == null) return;

		let p = {};
		p.ctx = "sc_d_0_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [];
		p.datas = [];

		for(let i=0; i<selectAreaCtvtInfo.allRetnMenSttus.length; i++){
			p.labels.push(selectAreaCtvtInfo.allRetnMenSttus[i].gigunYear);
			p.datas.push(selectAreaCtvtInfo.allRetnMenSttus[i].sclasCnt);
		}

		p.colors  = ["rgb(136, 210, 206)"];
		pageObj.drawChart(p);

	}

	/*선택지역 및 품목 분석 > 농업인 현황 > 연령대별 재배농업인현황*/
	SelectAreaCtvtChart.prototype.allRetnMenSttusAgeSctnChart = function(){
		let selectAreaCtvtInfo = dataObj.selectAreaCtvtInfo;

		if(selectAreaCtvtInfo.allRetnMenSttusAgeSctn == null) return;

		let p = {};
		p.ctx = "sc_d_0_1";
		p.type    = "doughnut";
		p.width   = 570;
		p.height  = 200;
//		p.showText = 'N';
		p.deli    = 0;
		p.labels = ['40대 미만', '40대', '50대', '60대 이상'];
		p.datas = [];
		p.colors  = ["rgb(141, 232, 139)","rgb(243, 207, 108)","rgb(251, 105, 192)","rgb(93, 225, 239)"];

		for(let i=0; i<p.labels.length; i++){
			let data = 0;
			let label = p.labels[i];

			for(let j=0; j<selectAreaCtvtInfo.allRetnMenSttusAgeSctn.length; j++){
				let d = selectAreaCtvtInfo.allRetnMenSttusAgeSctn[j];
				let ageSctn = d.ageSctn;
				let chartData = d.sclasCnt;
				if(label == ageSctn) data = chartData;
			}

			p.datas.push(data);

			/*라벨 옆에 실제 데이터 수 셋팅*/
//			//공백 처리
			let labelLeng = p.labels[i].length;
			let spaceCnt = 20 - (labelLeng*2.5);

			for(let z=0; z<=spaceCnt; z++){
				p.labels[i] += ' ';
			}
			p.labels[i] = p.labels[i] + $.cs.addComma(data);
		}


		pageObj.drawDoughnutChart(p);
	}

	/*선택지역 및 품목 분석 > 재배면적추이 > 년도별 재배면적 추이*/
	SelectAreaCtvtChart.prototype.allRetnCtvtAreaYearChart = function(){
		let selectAreaCtvtInfo = dataObj.selectAreaCtvtInfo;

		if(selectAreaCtvtInfo.allRetnCtvtAreaYear == null) return;

		let p = {};
		p.ctx = "sc_d_1_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
//		p.showText = '';
		p.deli    = 2;
		p.labels = [];
		p.innerDatas = [];
		p.innerLabels = ["전체", dataObj.form.hopeCtvt];
		for(let i=0; i<selectAreaCtvtInfo.allRetnCtvtAreaYear.length; i++){
			p.labels.push(selectAreaCtvtInfo.allRetnCtvtAreaYear[i].gigunYear);
			p.innerDatas.push(selectAreaCtvtInfo.allRetnAreaYear[i].avgOfactAgrldAra);
			p.innerDatas.push(selectAreaCtvtInfo.allRetnCtvtAreaYear[i].avgOfactAgrldAra);
		}

		p.innerColors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];
		pageObj.drawChart(p);
	}

	/*선택지역 및 품목 분석 > 재배면적추이 > 연령별 재배면적 추이*/
	SelectAreaCtvtChart.prototype.allRetnCtvtAreaAgeSctnChart = function(){
		let selectAreaCtvtInfo = dataObj.selectAreaCtvtInfo;

		if(selectAreaCtvtInfo.allRetnCtvtAreaAgeSctn == null) return;

		let p = {};
		p.ctx = "sc_d_1_1"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ['40대 미만', '40대', '50대', '60대 이상'];
		p.datas = [];


		for(let i=0; i<p.labels.length; i++){
			let data = 0;
			let label = p.labels[i];

			for(let j=0; j<selectAreaCtvtInfo.allRetnCtvtAreaAgeSctn.length; j++){
				let d = selectAreaCtvtInfo.allRetnCtvtAreaAgeSctn[j];
				let ageSctn = d.ageSctn;
				let chartData = d.avgOfactAgrldAra;
				if(label == ageSctn) data = chartData;
			}

			p.datas.push(data);

		}


//		for(let i=0; i<selectAreaCtvtInfo.allRetnCtvtAreaAgeSctn.length; i++){
//			p.labels.push(selectAreaCtvtInfo.allRetnCtvtAreaAgeSctn[i].ageSctn);
//			p.datas.push(selectAreaCtvtInfo.allRetnCtvtAreaAgeSctn[i].avgOfactAgrldAra);
//			p.datas.push(selectAreaCtvtInfo.allRetnAreaYear[i].avgOfactAgrldAra);
//		}

		p.colors  = ["rgb(136, 210, 206)"];
		pageObj.drawChart(p);
	}

	/*선택지역 및 품목 분석 > 초기영농인 영농현황 > 연령별 영농인 수*/
	SelectAreaCtvtChart.prototype.beginRetnSttusMenCntChart = function(){
		let selectAreaCtvtInfo = dataObj.selectAreaCtvtInfo;

		if(selectAreaCtvtInfo.beginRetnSttusMenCnt == null) return;

		let p = {};
		p.ctx = "sc_d_2_0"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = ['40대 미만', '40대', '50대', '60대 이상'];
		p.datas = [];

		let sumData = 0;

		for(let i=0; i<p.labels.length; i++){
			let data = 0;
			let label = p.labels[i];

			for(let j=0; j<selectAreaCtvtInfo.beginRetnSttusMenCnt.length; j++){
				let d = selectAreaCtvtInfo.beginRetnSttusMenCnt[j];
				let ageSctn = d.ageSctn;
				let chartData = d.ageCnt;
				if(label == ageSctn) data = chartData;
			}

			sumData += data;
			p.datas.push(data);

		}


		p.labels.push('합계');
		p.datas.push(sumData);

		p.colors  = ["rgb(136, 210, 206)"];
		pageObj.drawChart(p);
	}

	/*선택지역 및 품목 분석 > 초기영농인 영농현황 > 전출지역*/
	SelectAreaCtvtChart.prototype.beginRetnSttusAreaCntChart = function(){
		let selectAreaCtvtInfo = dataObj.selectAreaCtvtInfo;

		if(selectAreaCtvtInfo.beginRetnSttusThisAreaCnt == null) return;

		let p = {};
		p.ctx = "sc_d_2_1"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [ dataObj.form.hopeSigngu , '기타지역', '합계'];
		p.datas = [
					selectAreaCtvtInfo.beginRetnSttusThisAreaCnt[0].cnt,
				   selectAreaCtvtInfo.beginRetnSttusEtcAreaCnt[0].cnt,
				   (selectAreaCtvtInfo.beginRetnSttusThisAreaCnt[0].cnt+selectAreaCtvtInfo.beginRetnSttusEtcAreaCnt[0].cnt)
					];
		
		p.colors  = ["rgb(136, 210, 206)"];
		pageObj.drawChart(p);
	}

	/*선택지역 및 품목 분석 > 초기영농인 영농현황 > 재배품목*/
	SelectAreaCtvtChart.prototype.beginRetnSttusCtvtChart = function(){
		let selectAreaCtvtInfo = dataObj.selectAreaCtvtInfo;

		if(selectAreaCtvtInfo.beginRetnSttusCtvt == null) return;

		let p = {};
		p.ctx = "sc_d_2_2"
			p.type = "bar";
		p.width = 270;
		p.height = 200;
		p.deli = 0;
		p.labels = [ ];
		p.datas = [];
				
		for(let i=0; i<selectAreaCtvtInfo.beginRetnSttusCtvt.length; i++){
			p.labels.push(selectAreaCtvtInfo.beginRetnSttusCtvt[i].sclas);
			p.datas.push(selectAreaCtvtInfo.beginRetnSttusCtvt[i].sclasCnt);
		}

		p.colors  = ["rgb(136, 210, 206)"];
		pageObj.drawChart(p);
	}

});

let DetailChart = (function(){
	DetailChart.prototype.refreshChart = function(){
		areaSetlCndChart.refreshChart();
		similrRetnFmlgChart.refreshChart();
	}
})


let areaSetlCndChart = new AreaSetlCndChart(); // 지역정주여건
let similrRetnFmlgChart = new SimilrRetnFmlgChart();
let selectAreaCtvtChart = new SelectAreaCtvtChart();
let detailChart = new DetailChart();