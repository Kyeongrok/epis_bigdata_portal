/**
 * 
 */
//
function graph_t1_0(){
  let p = {};
  p.ctx = "graph_t1_0"; //포도 - 재배면적추이
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "재배면적 추이";
  p.subTitle= "";
  p.deli    = 2;
  p.labels  = ["2015","2016","2017","2018"];
  p.innerLabels = ["전체","포도"];
  p.innerDatas  = ["1909.23","2323.82","1880.73","2228.51","1866.16","2169.33","1855.18","2145.96"];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)"];

  pageObj.drawChart(p);
};
graph_t1_0();


/////

//
function graph_t1_1(){
  let p = {};
  p.ctx = "graph_t1_1"; //포도 - 재배면적추이 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "연령대별 평균 재배 면적 현황";
  p.subTitle = "";
  p.deli = 0;
  p.labels  = ["40대미만","40~49","50~59","60이상"];
  p.datas   = ["2413.56","2306.97","2181.22","2151.74"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
graph_t1_1();


/////

//
function graph_t2_0(){
  let p = {};
  p.ctx = "graph_t2_0"; // 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "상주군 연령대별 귀농인";
  p.subTitle = "";
  p.deli = 0;
  p.labels  = ["40대미만","40~49","50~59","60이상","합계"];
  p.datas   = ["84","168","400","220","872"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
graph_t2_0();


/////

//
function graph_t2_1(){
  let p = {};
  p.ctx = "graph_t2_1"; // 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "귀농인 전출지역";
  p.subTitle = "";
  p.deli = 0;
  p.labels  = ["상주시","기타지역","합계"];
  p.datas   = ["872","0","872"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
graph_t2_1();


/////

//
function graph_t2_2(){
  let p = {};
  p.ctx = "graph_t2_2"; // 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "귀농인 재배품목";
  p.subTitle = "";
  p.deli = 0;
  p.labels  = ["벼","떫은감","포도","콩","들깨"];
  p.datas   = ["1428","939","872","787","574"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
graph_t2_2();


/////


//
function sj_b_t0_0(){
  let p = {};
  p.ctx = "sj_b_t0_0"; // 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "사벌국면 농지 실거래 가격";
  p.subTitle = "";
  p.deli = 0;
  p.labels  = ["전","답","과수"];
  p.datas   = ["71807","87606","84348"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
sj_b_t0_0();


/////

//
function sj_b_t0_1(){
  let p = {};
  p.ctx = "sj_b_t0_1"; //
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "상주시 읍면동별 농지 실거래가격 비교";
  p.subTitle= "";
  p.showText = 'N';
  p.deli    = 3;
  p.labels  = ["상주시","공검면","공성면","낙동면","내서면","모동면","모서면","외남면","외서면","은척면","이안면","중동면","청리면","함창읍","화남면","화동면","화북면","화서면"];
  p.innerLabels = ["전","답","과수"];
  p.innerDatas  = ["80282","77000","102789","65311","66543","","91737","72962","89535","74779","79215","83673","62578","49735","69134","53012","76917","127403","68021","58311","","65259","80383","118937","61623","52852","","173370","69858","","91997","82679","129757","123994","73650","","66571","79706","121484","190089","134501","","61440","61812","","43743","70552","","99962","115274","","101469","89150",""];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
sj_b_t0_1();


/////

//
function sj_b_t0_2(){
  let p = {};
  p.ctx = "sj_b_t0_2"; // 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "사벌국면 농지 임대가격";
  p.subTitle = "";
  p.deli = 0;
  p.labels  = ["전","답","과수"];
  p.datas   = ["0","129","154"];
  p.colors  = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
sj_b_t0_2();


/////

//
function sj_b_t0_3(){
  let p = {};
  p.ctx = "sj_b_t0_3"; //
  p.type    = "bar";
  p.width   = 570;
  p.height  = 200;
  p.title   = "상주시 읍면동별 농지 임대가격 비교";
  p.subTitle= "";
  p.showText = 'N';
  p.deli    = 3;
  p.labels  = ["상주시","공검면","공성면","낙동면","내서면","모동면","모서면","외남면","외서면","은척면","이안면","중동면","청리면","함창읍","화남면","화동면","화북면","화서면"];
  p.innerLabels = ["전","답","과수"];
  p.innerDatas  = ["62","165","114","79","119","77","5","171","226","27","198","34","54","111","162","","177","107","25","180","195","278","171","0","16","173","26","22","162","0","68","234","","","212","150","","189","0","263","180","","29","87","","125","127","279","49","","0","25","131","69"];
  p.innerColors = ["rgb(136, 210, 206)","rgb(163, 186, 224)","rgb(247, 179, 184)"];

  pageObj.drawChart(p);
};
sj_b_t0_3();


/////

