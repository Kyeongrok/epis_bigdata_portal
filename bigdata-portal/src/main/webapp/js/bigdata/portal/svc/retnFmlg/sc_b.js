/**
 * 순창_귀농지역 맞춤 품목 정보
 */
//
function sc_b_d_3_2(){
  let p = {};
  p.ctx = "sc_b_d_3_2"; //순창_귀농지역 맞춤 품목 정보_두릅_생산/유통 안정성_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격추세 지수";
  p.subTitle = "높을 수록 가격이 상승하는 추세인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","두릅"];
  p.datas   = ["49.51","47"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_d_3_2();


/////

//
function sc_b_d_3_1(){
  let p = {};
  p.ctx = "sc_b_d_3_1"; //순창_귀농지역 맞춤 품목 정보_두릅_생산/유통 안정성_2nd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격변동성 지수";
  p.subTitle = "낮을 수록 가격변경 폭이 안정적인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","두릅"];
  p.datas   = ["0.464","0.497"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_d_3_1();


/////

//
function sc_b_d_3_0(){
  let p = {};
  p.ctx = "sc_b_d_3_0"; //순창_귀농지역 맞춤 품목 정보_두릅_생산/유통 안정성_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "현재 가격 위치 지수";
  p.subTitle = "최근 2년간 시장거래 가격에서의 현재 가격 위치";
  p.deli = 0;
  p.labels  = ["전체평균","두릅"];
  p.datas   = ["48.26","30.65"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_d_3_0();



//
//
function sc_b_d_1_0(){
  let p = {};
  p.ctx = "sc_b_d_1_0"; //순창_귀농지역 맞춤 품목 정보_두릅_도매시장 경락 가격_1st 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "도매시장 경락가격 현황(최근 3년간)";
  p.subTitle= "(kg/원)";
  p.deli    = 2;
  p.labels  = ["2018","2019"];
  p.innerLabels = ["전체평균","순창군"];
  p.innerDatas  = ["14509","13248","27176","33569"];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_d_1_0();


/////





/////

//
function sc_b_k_3_2(){
  let p = {};
  p.ctx = "sc_b_k_3_2"; //순창_귀농지역 맞춤 품목 정보_콩_생산/유통 안정성_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격추세 지수";
  p.subTitle = "높을 수록 가격이 상승하는 추세인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","콩"];
  p.datas   = ["49.51","47"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_k_3_2();


/////

//
function sc_b_k_3_1(){
  let p = {};
  p.ctx = "sc_b_k_3_1"; //순창_귀농지역 맞춤 품목 정보_콩_생산/유통 안정성_2nd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격 변동성 지수";
  p.subTitle = "낮을 수록 가격변동 폭이 안정적인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","콩"];
  p.datas   = ["0.464","0.45995"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_k_3_1();


/////

//
function sc_b_k_3_0(){
  let p = {};
  p.ctx = "sc_b_k_3_0"; //순창_귀농지역 맞춤 품목 정보_콩_생산/유통 안정성_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "현재 가격 위치 지수";
  p.subTitle = "최근 2년간 시장거래 가격에서의 현재 가격 위치";
  p.deli = 0;
  p.labels  = ["전체평균","콩"];
  p.datas   = ["48.26","31.1"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_k_3_0();


/////

//
function sc_b_k_1_0(){
  let p = {};
  p.ctx = "sc_b_k_1_0"; //순창_귀농지역 맞춤 품목 정보_콩_도매시장 경락 가격_1st 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "도매시장 경락가격 현황(최근 3년간)";
  p.subTitle= "(kg/원)";
  p.deli    = 2;
  p.labels  = ["2017","2018","2019"];
  p.innerLabels = ["전체평균","순창군"];
  p.innerDatas  = ["","","","","",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_k_1_0();


/////


//
function sc_b_b_3_2(){
  let p = {};
  p.ctx = "sc_b_b_3_2"; //순창_귀농지역 맞춤 품목 정보_블루베리_생산/유통 안정성_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격 추세 지수";
  p.subTitle = "높을 수록 가격이 상승하는 추세인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","블루베리"];
  p.datas   = ["49.51","48"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_b_3_2();


/////

//
function sc_b_b_3_1(){
  let p = {};
  p.ctx = "sc_b_b_3_1"; //순창_귀농지역 맞춤 품목 정보_블루베리_생산/유통 안정성_2nd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격 변동성 지수";
  p.subTitle = "낮을 수록 가격변동 폭이 안정적인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","블루베리"];
  p.datas   = ["0.464","0.48476"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_b_3_1();


/////

//
function sc_b_b_3_0(){
  let p = {};
  p.ctx = "sc_b_b_3_0"; //순창_귀농지역 맞춤 품목 정보_블루베리_생산/유통 안정성_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "현재 가격 위치 지수";
  p.subTitle = "최근 2년간 시장거래 가격에서의 현재 가격 위치";
  p.deli = 0;
  p.labels  = ["전체평균","블루베리"];
  p.datas   = ["48.26","40.39"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_b_3_0();


/////

//
function sc_b_b_1_0(){
  let p = {};
  p.ctx = "sc_b_b_1_0"; //순창_귀농지역 맞춤 품목 정보_블루베리_도매시장 경락 가격_1st 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "도매시장 경락가격 현황(최근 3년간)";
  p.subTitle= "(kg/원)";
  p.deli    = 2;
  p.labels  = ["2014","2015","2016","2017"];
  p.innerLabels = ["전체","초기영농인"];
  p.innerDatas  = ["","","","","","",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
sc_b_b_1_0();


/////

