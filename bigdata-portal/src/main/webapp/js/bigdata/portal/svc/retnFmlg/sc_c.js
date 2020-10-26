/**
 * 순창 
 */
//
function sc_c_t2_0(){
  let p = {};
  p.ctx = "sc_c_t2_0"; // 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "초기 영농인 상위 10개 재배 품목";
  p.subTitle = "(명)";
  p.deli = 0;
  p.labels  = ["벼","건고추","콩","매실","참깨","두릅","들깨","복분자","고구마","떫은감"];
  p.datas   = ["2427","1762","1755","1091","946","897","765","644","625","605"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
sc_c_t2_0();


/////

//
function sc_c_t3_0(){
  let p = {};
  p.ctx = "sc_c_t3_0"; // 
  p.type = "bar";
  p.width = 270;
  p.height = 200;
  p.title = "유사 귀농인 상위 10개 재배 품목";
  p.subTitle = "(명)";
  p.deli = 0;
  p.labels  = ["벼","건고추","들깨","고구마","감자","사과","콩","기타 사료작물","마늘","김장(가을)배추"];
  p.datas   = ["384","242","111","70","61","39","35","23","22","22"];
  p.colors  = ["rgb(136, 210, 206)"];

  pageObj.drawChart(p);
};
sc_c_t3_0();


/////

