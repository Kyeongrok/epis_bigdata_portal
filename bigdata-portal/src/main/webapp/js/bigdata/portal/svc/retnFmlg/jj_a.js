/**
 * 
 */
//
function jj_a_5_1(){
  let p = {};
  p.ctx = "jj_a_5_1"; //제주_지역 정주 여건 정보_문화_2nd 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "한림읍 읍면동별 문화시설 비교";
  p.subTitle= "";
  p.deli    = 4;
  p.labels  = ["제주시 ","구좌읍","애월읍","우도면","조천읍","추자면","한경면"];
  p.innerLabels = ["문화의집","도서관","박물관","지방문화원"];
  p.innerDatas  = ["3","5","12","","","1","","","1","1","5","","","","","","1","1","4","","","","","","1","1","3",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];

  pageObj.drawChart(p);
};
jj_a_5_1();


/////

//
function jj_a_5_0(){
  let p = {};
  p.ctx = "jj_a_5_0"; //제주_지역 정주 여건 정보_문화_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "한림읍 문화시설 수";
  p.subTitle = "";
  p.deli = 0;
  p.labels  = ["문화의집","도서관","박물관","지방문화원"];
  p.datas   = ["","1","",""];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)","rgb(255, 159, 255)"];

  pageObj.drawChart(p);
};
jj_a_5_0();


/////

//
function jj_a_4_3(){
  let p = {};
  p.ctx = "jj_a_4_3"; //제주_지역 정주 여건 정보_편의_4th 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "한림읍 읍면동별 마트시설 접근성 비교";
  p.subTitle= "자가용 이용시 소요시간(분)";
  p.deli    = 2;
  p.labels  = ["제주시","구좌읍","애월읍","우도면","조천읍","추자면","한경면"];
  p.innerLabels = ["대형점포","전통시장"];
  p.innerDatas  = ["","","","","","","","","","","","","",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
jj_a_4_3();


/////


/////

//
function jj_a_4_0(){
  let p = {};
  p.ctx = "jj_a_4_0"; //제주_지역 정주 여건 정보_편의_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "한림읍 마트시설 접근시간";
  p.subTitle = "자가용 이용시 소요시간(분)";
  p.deli = 0;
  p.labels  = ["대형점포","전통시장"];
  p.datas   = ["",""];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
jj_a_4_0();


/////

//
function jj_a_3_1(){
  let p = {};
  p.ctx = "jj_a_3_1"; //제주_지역 정주 여건 정보_교통_2nd 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "한림읍 읍면동별 교통시설 접근성 비교";
  p.subTitle= "자가용 이용시 소요시간(분)";
  p.deli    = 3;
  p.labels  = ["제주시","구좌읍","애월읍","우도면","조천읍","추자면","한경면"];
  p.innerLabels = ["버스터미널","기차역","공항"];
  p.innerDatas  = ["","","","","","","","","","","","","","","","","","","","",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_3_1();


/////

//
function jj_a_3_0(){
  let p = {};
  p.ctx = "jj_a_3_0"; //제주_지역 정주 여건 정보_교통_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "한림읍 교통시설 접근시간";
  p.subTitle = "자가용 이용시 소요시간(분)";
  p.deli = 0;
  p.labels  = ["버스터미널","기차역","공항"];
  p.datas   = ["","",""];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_3_0();


/////

//
function jj_a_2_1(){
  let p = {};
  p.ctx = "jj_a_2_1"; //제주_지역 정주 여건 정보_교육_2nd 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "한림읍 읍면동별 교육시설 접근성 비교";
  p.subTitle= "자가용 이용시 소요시간(분)";
  p.deli    = 3;
  p.labels  = ["제주시","구좌읍","애월읍","우도면","조천읍","추자면","한경면"];
  p.innerLabels = ["초","중","고"];
  p.innerDatas  = ["","","","","","","","","","","","","","","","","","","","",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_2_1();


/////

//
function jj_a_2_0(){
  let p = {};
  p.ctx = "jj_a_2_0"; //제주_지역 정주 여건 정보_교육_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "한림읍 교육시설별 접근시간";
  p.subTitle = "자가용 이용시 소요시간(분)";
  p.deli = 0;
  p.labels  = ["초","중","고"];
  p.datas   = ["","",""];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_2_0();


/////

//
function jj_a_1_2(){
  let p = {};
  p.ctx = "jj_a_1_2"; //제주_지역 정주 여건 정보_귀농인_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "귀농인 재배 품목";
  p.subTitle = "(명)";
  p.deli = 0;
  p.labels  = ["감귤","콩","브로코리(녹색꽃양배추)","가을무","만감","양배추","대파","조미채소류 기타","마늘","아로니아","건고추","여름배추","맥주보리","기장","무화과","과실류 기타"];
  p.datas   = ["14","3","2","2","2","1","1","1","1","1","1","1","1","1","1","1"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
jj_a_1_2();


/////

//
function jj_a_1_1(){
  let p = {};
  p.ctx = "jj_a_1_1"; //제주_지역 정주 여건 정보_귀농인_2nd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "귀농인 전출 지역";
  p.subTitle = "(명)";
  p.deli = 0;
  p.labels  = ["제주특별자치도","경기도","경상남도","서울특별시","부산광역시","경상북도"];
  p.datas   = ["24","3","3","2","1","1"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
jj_a_1_1();


/////

//
function jj_a_1_0(){
  let p = {};
  p.ctx = "jj_a_1_0"; //제주_지역 정주 여건 정보_귀농인_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "한림읍 연령대별 귀농인";
  p.subTitle = "(명)";
  p.deli = 0;
  p.labels  = ["30대","40대","50대","60대이상"];
  p.datas   = ["1","1","1","2"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
jj_a_1_0();


/////

//
function jj_a_0_3(){
  let p = {};
  p.ctx = "jj_a_0_3"; //제주_지역 정주 여건 정보_농지가격_4th 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "제주시 읍면동별 농지 임대가격";
  p.subTitle= "평균 거래가격(원/3.3㎥)";
  p.showText = 'N';
  p.deli    = 3;
  p.labels  = ["제주시","구좌읍","애월읍","우도면","조천읍","추자면","한경면"];
  p.innerLabels = ["전","답","과수"];
  p.innerDatas  = ["59.54375","","56.84615385","98.6","","","36.1","","82.9","19.2","","","52","","23.8","","","","59.6","","11"];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_0_3();


/////

//
function jj_a_0_2(){
  let p = {};
  p.ctx = "jj_a_0_2"; //제주_지역 정주 여건 정보_농지가격_3rd 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "한림읍 농지 임대가격";
  p.subTitle = "평균 거래가격(원/3.3㎥)";
  p.deli = 0;
  p.labels  = ["전","답","과수"];
  p.datas   = ["59.6","","11"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_0_2();


/////

//
function jj_a_0_1(){
  let p = {};
  p.ctx = "jj_a_0_1"; //제주_지역 정주 여건 정보_농지가격_2nd 차트
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "제주시 읍면동별 농지 실거래가 비교";
  p.subTitle= "평균 거래가격(원/3.3㎥)";
  p.showText = 'N';
  p.deli    = 3;
  p.labels  = ["제주시","구좌읍","애월읍","우도면","조천읍","추자면","한경면"];
  p.innerLabels = ["전","답","과수"];
  p.innerDatas  = ["2313586.183","1481869.933","3018292.7","2802616.9","","","2368236.8","1511450.4","","","","","2123116.3","1896081.8","3018292.7","","","","2439801.4","1087392.6",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_0_1();


/////

//
function jj_a_0_0(){
  let p = {};
  p.ctx = "jj_a_0_0"; //제주_지역 정주 여건 정보_농지가격_1st 차트 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "한림읍 농지 실거래가";
  p.subTitle = "평균 거래가격(원/3.3㎥)";
  p.deli = 0;
  p.labels  = ["전","답","과수"];
  p.datas   = ["1852072","",""];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
jj_a_0_0();


/////

