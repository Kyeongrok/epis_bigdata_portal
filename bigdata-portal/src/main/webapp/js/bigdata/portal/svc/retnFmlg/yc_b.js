/**
 * 예천_귀농지역 맞춤 품목 정보
 */
//
function yc_b_y_3_2(){
  let p = {};
  p.ctx = "yc_b_y_3_2"; //예천_귀농지역 맞춤 품목 정보_양파_생산/유통 안정성_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격 추세 지수";
  p.subTitle = "높을 수록 가격이 상승하는 추세인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","양파"];
  p.datas   = ["49.51","44"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_y_3_2();


/////

//
function yc_b_y_3_1(){
  let p = {};
  p.ctx = "yc_b_y_3_1"; //예천_귀농지역 맞춤 품목 정보_양파_생산/유통 안정성_2nd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격 변동성 지수";
  p.subTitle = "낮을 수록 가격변동 폭이 안정적인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","양파"];
  p.datas   = ["0.464","0.45608"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_y_3_1();


/////

//
function yc_b_y_3_0(){
  let p = {};
  p.ctx = "yc_b_y_3_0"; //예천_귀농지역 맞춤 품목 정보_양파_생산/유통 안정성_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "현재 가격 위치 지수";
  p.subTitle = "최근 2년간 시장거래 가격에서의 현재 가격 위치";
  p.deli = 0;
  p.labels  = ["전체평균","양파"];
  p.datas   = ["48.26","29.49"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_y_3_0();


/////

//
function yc_b_y_1_0(){
  let p = {};
  p.ctx = "yc_b_y_1_0"; //예천_귀농지역 맞춤 품목 정보_양파_도매시장 경락 가격_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "도매시장 경락가격 현황(최근 3년간)";
  p.subTitle = "(kg/원)";
  p.deli = 0;
  p.labels  = ["2017","","2018","","2019",""];
  p.datas   = ["전체평균","순창군","전체평균","순창군","전체평균","순창군"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_y_1_0();


/////

//
function yc_b_k_3_2(){
  let p = {};
  p.ctx = "yc_b_k_3_2"; //예천_귀농지역 맞춤 품목 정보_콩_생산/유통 안정성_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격 추세 지수";
  p.subTitle = "높을 수록 가격이 상승하는 추세인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","콩"];
  p.datas   = ["49.51","47"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_k_3_2();


/////

//
function yc_b_k_3_1(){
  let p = {};
  p.ctx = "yc_b_k_3_1"; //예천_귀농지역 맞춤 품목 정보_콩_생산/유통 안정성_2nd 차트 
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
yc_b_k_3_1();


/////

//
function yc_b_k_3_0(){
  let p = {};
  p.ctx = "yc_b_k_3_0"; //예천_귀농지역 맞춤 품목 정보_콩_생산/유통 안정성_1st 차트 
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
yc_b_k_3_0();


/////

//
function yc_b_k_1_0(){
  let p = {};
  p.ctx = "yc_b_k_1_0"; //예천_귀농지역 맞춤 품목 정보_콩_도매시장 경락 가격_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "도매시장 경락가격 현황(최근 3년간)";
  p.subTitle = "(kg/원)";
  p.deli = 0;
  p.labels  = ["2017","","2018","","2019",""];
  p.datas   = ["전체평균","순창군","전체평균","순창군","전체평균","순창군"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_k_1_0();


/////

//
function yc_b_p_3_2(){
  let p = {};
  p.ctx = "yc_b_p_3_2"; //예천_귀농지역 맞춤 품목 정보_포도_생산/유통 안정성_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격 추세 지수";
  p.subTitle = "높을 수록 가격이 상승하는 추세인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","포도"];
  p.datas   = ["49.51","47"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_p_3_2();


/////

//
function yc_b_p_3_1(){
  let p = {};
  p.ctx = "yc_b_p_3_1"; //예천_귀농지역 맞춤 품목 정보_포도_생산/유통 안정성_2nd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "가격변동성 지수";
  p.subTitle = "낮을 수록 가격 변동 폭이 안정적인 품목";
  p.deli = 0;
  p.labels  = ["전체평균","포도"];
  p.datas   = ["0.464","0.31548"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_p_3_1();


/////

//
function yc_b_p_3_0(){
  let p = {};
  p.ctx = "yc_b_p_3_0"; //예천_귀농지역 맞춤 품목 정보_포도_생산/유통 안정성_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "현재 가격 위치 지수";
  p.subTitle = "최근 2년간 시장거래 가격에서의 현재 가격 위치";
  p.deli = 0;
  p.labels  = ["전체평균","포도"];
  p.datas   = ["48.26","33.69"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_p_3_0();


/////

//
function yc_b_p_1_0(){
  let p = {};
  p.ctx = "yc_b_p_1_0"; //예천_귀농지역 맞춤 품목 정보_포도_도매시장 경락 가격_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "도매시장 경락가격 현황(최근 3년간)";
  p.subTitle = "(kg/원)";
  p.deli = 0;
  p.labels  = ["2017","","2018","","2019",""];
  p.datas   = ["전체평균","순창군","전체평균","순창군","전체평균","순창군"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
yc_b_p_1_0();


/////

