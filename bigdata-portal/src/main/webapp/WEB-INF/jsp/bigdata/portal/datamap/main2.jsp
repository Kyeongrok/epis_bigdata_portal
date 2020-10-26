<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/dmm/popup.css"/>" />
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/dmm/style.css"/>" /> -->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/dmm/vis.min.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/dmm/vis-network.min.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/dmm/semantic.css"/>" class="ui" />


<!-- 기존 레이어팝업 소스 -->
<style>

/* text style */
.sr_ttl{ height:30px; border-bottom:#ddd 1px solid;}
.sr_ttl2{height:30px; border-bottom:#ddd 1px solid; margin-top:30px; }
.ts_01{ font-size:17px; font-weight:bold; text-align: left; float: left; margin-right: 5px; font-family: 'Noto Sans'; }
.ts_01 span{ color:#de311f;  display: inline;}
.ts_02{color: #de311f; font-size: 17px; letter-spacing: 0;}
.ts_03{ float: left; width: 50%; text-align: right;}

.sr_con { padding: 10px 0; border-bottom: 1px dashed #ddd; }
.sr_con > span { display: inline-block; }
.sr_con_nodata { color: #333; background: #f0f3f2; padding: 40px 0; text-align: center; border-bottom: 1px solid #ddd; }
.ts_04{ color:#00888e; font-weight:bold;}
.ts_04 a {color: #00888e}
.ts_04 a::after { content: '/'; display: inline-block; color: #aaa; padding: 0 10px; font-size: 12px; font-weight: 200; }
.ts_05 { display: block; }
.ts_05{ color: #da452b; margin: 5px 0; }
.ts_05 a { display: inline-block; margin-right: 5px; margin-top: 5px; color: #da452b; border: 1px #da452b solid; border-radius: 1px; padding: 3px 5px; font-size: 14px; }

/* popup_01 */
.ui.modal {border-radius:0 !important; font-size: 1em !important;}
.ui.modal > :first-child {border-radius:0 !important;;}

/*
#popch{position:absolute; top:49%; left:48%; width:780px; padding:0 0 15px 0; margin:0 0 0 -390px; box-shadow:2px 2px #e1e1e1; border:3px solid #3876bb; background:#fff; z-index:1000;}
#popch h3{ background:#3876bb; font-size:1.35em; color:#FFF; padding:0 0 0 10px; line-height:40px; float: left;}
#popch .btn_close{position: absolute; top: 1px; right: 0px;}
#popch ul li{ padding:3px;}
*/
div.ui.dimmer { background-color: rgba(0, 0, 0, 0.3); }

#popch{position:absolute; top:50%; left:50%; width:860px; margin-left: -390px; background:#fff; z-index:1000;}
#popch h3{ color:#fff; margin: 0; padding: 21px 20px; text-align: center; font-size: 18px;
	background-color: #00666b;
    background: -webkit-linear-gradient(90deg, #00666b 34%, #c63e26 105%);
    background: -o-linear-gradient(90deg, #00666b 34%, #c63e26 105%);
    background: -moz-linear-gradient(90deg, #00666b 34%, #c63e26 105%);
    background: linear-gradient(90deg, #00666b 34%, #c63e26 105%); }
#popch .btn_close { position: absolute; top: 17px; right: 20px;}
#popch > ul { width: 100%; }
#popch ul li.ppli{ padding: 20px; }

.btn_01 { padding-top: 20px; text-align: center; }
.btn_01 li { float:left; padding-left: 4px; }
.btn_01 li:first-of-type { padding-left: 0; }
.btn_01 li a i.ico_arrow { display: inline-block; background-size: 100%; vertical-align: middle; }


#popch2{position:absolute; top:49%; left:50%; width: 780px; padding: 0; margin: 0 0 0 -390px; background: #fff; z-index: 1000; display: none;}
#popch2 h3{ color:#fff; margin: 0; padding: 21px 20px; text-align: center; font-size: 18px;
	background-color: #00666b;
    background: -webkit-linear-gradient(90deg, #00666b 34%, #c63e26 105%);
    background: -o-linear-gradient(90deg, #00666b 34%, #c63e26 105%);
    background: -moz-linear-gradient(90deg, #00666b 34%, #c63e26 105%);
    background: linear-gradient(90deg, #00666b 34%, #c63e26 105%); }
#popch2 .btn_close { position: absolute; top: 17px; right: 20px;}
#popch2 .srbox { height: 620px; overflow: auto; padding: 20px; font-family: 'Nanum Gothic'; }

#popch3 { position: absolute; top: 15%; left:7%; width: 85%; height: 710px; padding: 0; border: 3px solid #3876bb; background: #fff; z-index: 1000; display: none; overflow: auto; }

#popch4{position:absolute; top:49%; left:48%; width:860px; padding:0 0 15px 0; margin:0 0 0 -390px; /*box-shadow:2px 2px #e1e1e1;*/ border:3px solid #3876bb; background:#fff; z-index:1000;}
#popch4 h3{ background:#3876bb; height:30px; font-size:1.35em; color:#FFF; padding:10px 0 0 10px;}
#popch4 .btn_close{position: absolute; top: 0px; right: 0px;}
#popch4 ul li.ppli{ padding:12px;}
</style>

 <!--기존 레이어팝업 소스 끝 -->

<script type="text/javascript" src="<c:url value="/js/bigdata/dmm/vis.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/dmm/Hogan.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/dmm/vis-network.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/dmm/intense.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/dmm/semantic.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/components/dropdown.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/components/transition.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/components/sidebar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/components/modal.js"/>"></script>

<ul class="tab01 column2 mb20">
	<li><a href="javascript:tabClick(null, null)" class="active">기관별</a></li>
	<li><a href="javascript:tabClick('fdmorg', '01')">분류체계별</a></li>
</ul>

<div class="tab03 mb20" id="txmn1">
	<ul>
		<li><a href="javascript:tabClick(null, null)">네트워크</a></li>
		<li><a href="javascript:openTreeMap()">트리맵</a></li>
	</ul>
</div>
<div class="tab03 mb20" id="fdmorg1">
	<ul>
		<li><a href="javascript:tabClick('fdmorg', '01')">농산</a></li>
		<li><a href="javascript:tabClick('fdmorg', '02')">축산</a></li>
		<li><a href="javascript:tabClick('fdmorg', '03')">산림</a></li>
		<li><a href="javascript:tabClick('fdmorg', '04')">식품</a></li>
	</ul>
</div>

			<div class="box_wh">
				<div class="tar">
					<div class="tit_depth">
						<ul>
							<li class="mnli_01"></li>
						</ul>
					</div>
					<span class="searcharea2">
						<input type="text" name="textfield" id="searchDataList" value="${searchListWord}" title="검색 단어 입력" placeholder="검색"/>
						<a href="#" id="btnSearch"><i class="ico search">검색</i></a>
					</span>
					<!-- <a href="#" class="btn vat">범례</a> -->
					<div class="mnli_03">
						<a href="#" id="legend" class="btn lightgrey vat">범례</a>
						<div class="lgd_body">
							<ul class="color">
								<li>생산</li>
								<li>유통</li>
								<li>소비</li>
								<li>관리</li>
							</ul>
							<ul class="icon">
								<li>농산</li>
								<li>축산</li>
								<li>산림</li>
								<li>식품</li>
							</ul>
						</div>
					</div>
				</div>
			
				<div class="datamap_tree mt30" id="treeMap" style="display: none;">
						<div class="treename">
							<span>기관</span>
							<span>정보시스템</span>
							<span>데이터베이스</span>
							<span>테이블</span>
						</div>	
						
						<div class="tNav">
						
						<!-- <ul class="one"> -->
						<ul class="treetbl">
							<!-- <li class="n"> -->
							<li>
								<a href="#" class="active">농림축산식품부</a>
								<!-- fdmorg -->
								<ul class="two">		
								{{#graph}}
									<li>
										<a href="#" data-item="{{id}}">{{name}}</a>
										<!-- info_system -->
										<ul class="three">	
										{{#infoSystem}}				
											<li>
												<a href="#" data-item="{{id}}">{{name}}</a>
												<ul class="four">
												{{#database}}
													<li>
														<a href="#" data-item="{{id}}">{{name}}</a>
															<ul class="five">
															{{#table}}
																<li>
																	<a href="#" data-item="{{id}}">{{name}}</a>									
																</li>
															{{/table}}
															</ul>																							
													</li>
													{{/database}}
												</ul>																																
											</li>
										{{/infoSystem}}	
										</ul>
										<!-- info_system fin -->															
									</li>
								{{/graph}}				
								</ul>
								<!-- fdmorg fin -->
							</li>
						</ul>	
					</div>
				</div>
				
					
				<div id="mynetwork" class="pusher" style="height: 700px;">
					<svg width="1118" height="700">
						<!-- 그래프 영역 --></svg>
				</div>
				
			</div>
			
				<div id="popch2" class="ui modal">
					<h3>“한우질병”과 관련한 데이터 검색결과</h3>
					<p class="btn_close close_login" id="popch2_close">
						<a href="#" onClick="popupClose(this);"><img src="/images/bigdata/dmm/icon_04.png" alt="닫기"></a>
					</p>
					<div class="srbox">
							<div class="sr_ttl">
								<span class="ts_01">정보시스템 검색결과</span>
								&#40;<span class="ts_02">{{searchSysInfoCnt}}</span>건&#41;
								<span class="ts_03">
							</div>
							{{#searchSysInfo}}
							<div class="sr_con">							
								<span class="ts_04"><a href="#" onclick="showSysInfoMap('{{sysCode}}', '{{fdmorgCode}}')">{{metaSysNM}}</a></span>
								<span class="search_text b">{{fdmorgNM}}</span>	            	
								<span class="search_text">{{metaSubject}}</span>	            			
							</div>
							{{/searchSysInfo}}
							{{^searchSysInfo}}
							<p class="sr_con_nodata">정보시스템 검색결과가 없습니다.</p>
							{{/searchSysInfo}}
							<!-- INFO_SYSTEM PAGING -->
							
							<div class="paging">
								<div class="wrap_page">
									{{{sysPaging}}}
								</div>
							</div>
							<!-- INFO_SYSTEM PAGING FIN -->
							
							<div class="sr_ttl2">
								<span class="ts_01">테이블 검색결과</span>
								&#40;<span class="ts_02">{{searchDbInfoCnt}}</span>건&#41;
								<span class="ts_03">
							</div>
							{{#searchTableInfo}}
							<div class="sr_con">							
								<span class="ts_04"><a href="#" onclick="showTableInfoMap('{{fdmsTableIdx}}')">{{fdmsTableName}}</a></span>       					
								<span class="search_text"><b>{{fdmsSysNm}} > {{fdmsDbNm}}</b></span>
							</div>
							{{/searchTableInfo}}
							{{^searchTableInfo}}
								<p class="sr_con_nodata">데이터 검색결과가 없습니다.</p>
							{{/searchTableInfo}}
							
							<!-- TABLE PAGING -->
							<div class="paging">
								<div class="wrap_page">
									{{{tablePaging}}}
								</div>
							</div>
							<!-- TABLE PAGING FIN -->
					</div>
				</div>

				<!-- 정보시스템(+디비) 메타데이터 팝업 -->
				<div id="popch" class="ui modal">
					<h3>정보시스템 메타데이터</h3>
					<p class="btn_close close_login" id="popch_close">
						<a href="#" onClick="popupClose(this);"><img src="/images/bigdata/dmm/icon_04.png" alt="닫기"></a>
					</p>
					<ul>
					    <li class="ppli">
					      <table class="tbl02 column4_dmap bdt_333">
					        <tbody>
					        <tr>
					          <th>제목</th>
					          <td id="title">{{metaSysNM}}</td>
					          <th>기관</th>
					          <td>{{fdmorgName}}</td>
					        </tr>
					        <tr>
					          <th>담당자명</th>
					          <td>{{metaManagerName}}</td>
					          <th>운영부서명</th>
					          <td>{{metaManagerDept}}</td>
					        </tr>
					        <tr>
					          <th>전화번호</th>
					          <td>{{metaManagerTel}}</td>
					          <th>이메일</th>
					          <td>{{metaManagerEmail}}</td>
					        </tr>
					        <tr>
					          <th>키워드</th>
					          <td>{{metaKeyword}}</td>
					          <th>활용자</th>
					          <td>
					          		대국민&#40;{{metaUtilNation}}&#41;,
					        		민간기업&#40;{{metaUtilComp}}&#41;,<br/>
					        		타공공기관&#40;{{metaUtilInst}}&#41;,
					        		기관내부&#40;O&#41;
					        	</td>
					        </tr>
					        <tr>
					        	<th>관련법명</th>
					        	<td colspan="3">
					        		{{metaSysMemo}}
					        	</td>
					        </tr>
					        <tr class="db1">
					        	<th>논리DB명</th>
					        	<td>{{logical}}</td>
					        	<th>물리DB명</th>
					        	<td>{{physical}}</td>
					        </tr>
					        <tr class="db1">
					        	<th>DB설명</th>
					        	<td colspan="3">{{description}}</td>
					        </tr>
					      </tbody>
					      </table>
					    </li>
					  </ul>
				</div>
				<!-- 테이블 메타데이터 팝업 -->
				<div id="popch4" class="ui modal">
					<h3>테이블 메타데이터</h3>
					<p class="btn_close close_login" id="popch_close">
						<a href="#" onClick="popupClose(this);"><img src="/images/bigdata/dmm/icon_03.png" alt="닫기" /></a>
					</p>
					<ul>
					    <li class="ppli">
					      <table class="tbl02">
					        <tbody>
								<tr>
									<th width="13%">테이블명</th>
									<td width="28%">{{fdmsTableName}}</td>
									<th width="13%" class="s01">소속</th>
									<td colspan="3">{{fdmsSysNm}} > {{fdmsDbNm}}</td>
								</tr>
								<tr>
									<th>주제</th>
									<td>{{fdmsTableSubject}}</td>
									<th class="s01">갱신주기</th>
									<td colspan="3">{{fdmsTableUpdate}}</td>
								</tr>
								<tr>
									<th>수집방법</th>
									<td>{{fdmsTableCollect}}</td>
									<th class="s01">연계방식</th>
									<td colspan="3">{{fdmsTableConmethod}}</td>
								</tr>
								<tr>
									<th>개방명</th>
									<td>{{fdmsTableOpenName}}</td>
									<th rowspan="4" class="s01">활용사례</th>
									<td width="10%">활용기관</td>
									<td colspan="2">
										<ul>
											<li>{{fdmsTableUseCase}}</li>
										</ul>
								</tr>
								<tr>
									<th class="s01">개방형태</th>
									<td>{{fdmsTableOpenType}}</td>
									<td>제공기관</td>
									<td colspan="2">{{fdmsTableProvider}}</td>
								</tr>
								<tr>
									<th rowspan="2" class="s01">키워드</th>
									<td rowspan="2">
										<ul>
											<li>{{fdmsTableKeyword}}</li>
										</ul>
									</td>
									<td>연계정보</td>
									<td colspan="2">
										<ul>
											<li>{{fdmsTableConinfo}}</li>
										</ul>
								</tr>
								<tr>
									<td>연계주기</td>
									<td colspan="2">{{fdmsTableCondate}}</td>
								</tr>
								<tr>
									<th>URL</th>
									<td colspan="5">{{fdmsTableUrl}}</td>
								</tr>
					      </tbody></table>
					    </li>
					  </ul>
				</div>

				<div id="popch3">
					<!-- ERD -->
				</div>


			<div class="dim-layer">
				<div class="dimBg"></div>
				<div id="layer2" class="pop-layer">
					<div class="pop-container">
						<div class="pop-conts">
							<p class="ctxt mb20">
							<table class="ui celled padded table">
								<tbody>
									<tr>
										<th>
											<h2 class="ui center aligned header">A</h2>
										</th>
										<td class="single line">Power Output</td>
									</tr>
									<tr>
										<th>
											<h2 class="ui center aligned header">A</h2>
										</th>
										<td class="single line">Power Output</td>
									</tr>
								</tbody>
							</table>
							<a class="demo-image" data-image="<c:url value='/jfile/preview.do?fileseq=15e2c78803274' />" data-title="농림수산식품 교육문화정보원" data-caption="모델 : 논리<br/>서비스 : 생명자원정보<br/>데이터베이스 : 텃밭분양정보"> ERD 보기
							</a>
							<div class="btn-r">
								<a href="#" class="btn-layerClose">논리 모델</a> <a href="#" class="btn-layerClose">물리 모델</a> <a href="#" class="btn-layerClose">메타 정보</a> <a href="#" class="btn-layerClose">Close</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		<!-- 데이터 지도 레이아웃 시작 
		<div id="container">
			<div id="ttl_bar">
				<ul>
					<li class="pttl">농식품 데이터지도</li>
					<li class="pbtn2_01_n"><a href="javascript:tabClick('fdmorg', '01')">분류체계별</a></li>
					<li>
						<p class="pbtn2_sp01 pbtn2_sp_s" style="display: none;">
							<a href="javascript:tabClick('fdmorg', '01')">농산</a>
						</p>
						<p class="pbtn2_sp02 pbtn2_sp_n" style="display: none;">
							<a href="javascript:tabClick('fdmorg', '02')">축산</a>
						</p>
						<p class="pbtn2_sp03 pbtn2_sp_n" style="display: none;">
							<a href="javascript:tabClick('fdmorg', '03')">산림</a>
						</p>
						<p class="pbtn2_sp04 pbtn2_sp_n" style="display: none;">
							<a href="javascript:tabClick('fdmorg', '04')">식품</a>
						</p>
					</li>
					<li class="pbtn2_02"><a href="javascript:tabClick(null, null)">기관별</a></li>
					<li class="pbtn2_03_n"><a href="javascript:openTreeMap()">계층별</a></li>					
					
				</ul>
			</div>	
		</div>
		 데이터 지도 레이아웃 끝 -->

<script type="text/javascript">

var systemPage = 1;
var dbinfoPage = 1;

var currentMenu = '';
var currentTxnm = '';

var graphSvg = null; // 그래프 영역(D3)
var forceSimulation = null; // 그래프 오브젝트(D3)
var visNetwork = null; // 그래프 오브젝트(VIS.JS)
var visNetworkDrawTimer = null; //그래프 노드추가 타이머(VIS.JS)
var dataNodes = null; // 그래프 노드
var dataLinks = null; // 그래프 링크
var dataLinkedByIndex = {}; // 노드 연결 정보 인덱스
var dataLoadEndedByIndex = {}; // 하위 노드 1회 불러온 이후 로딩 안함
var graphZoom = null; // 그래프 확대/축소 값(D3)
var selectMetaInfo = null;

var templateSystem = Hogan.compile($('#popch').html()); // 정보시스템 메타데이터 템플릿
var templateTable = Hogan.compile($('#popch4').html()); // 테이블 메타데이터 템플릿
var templateSearchData = Hogan.compile($('#popch2').html());
var template = Hogan.compile($('.tNav').html());

var searchSystemPage = 1;
var searchTablePage = 1;

var isHierarchical = false; 
var isFdmorgMode = false;

var menuName = [];

var initGraphNode = new vis.DataSet();
var initGraphEdge = new vis.DataSet();
var addGraphNode = false;
var treeMapJson;
var compareId;
var highlightActive = false;

var selDatabase = '';

var icon = {	

	org01:'/images/bigdata/dmm/icon/ga_ico/19.png',
	org02:'/images/bigdata/dmm/icon/ga_ico/02.png',	
	org03:'/images/bigdata/dmm/icon/ga_ico/13.png',
	org04:'/images/bigdata/dmm/icon/ga_ico/09.png',
	org05:'/images/bigdata/dmm/icon/ga_ico/14.png',	
	org06:'/images/bigdata/dmm/icon/ga_ico/20.png',
	org07:'/images/bigdata/dmm/icon/ga_ico/06.png',
	org08:'/images/bigdata/dmm/icon/ga_ico/15.png',	
    org09:'/images/bigdata/dmm/icon/ga_ico/01.png',
	org10:'/images/bigdata/dmm/icon/ga_ico/05.png',   
	org11:'/images/bigdata/dmm/icon/ga_ico/11.png',    
	org12:'/images/bigdata/dmm/icon/ga_ico/18.png',
	org13:'/images/bigdata/dmm/icon/ga_ico/08.png',
	org14:'/images/bigdata/dmm/icon/ga_ico/12.png',	
	org15:'/images/bigdata/dmm/icon/ga_ico/03.png',
	org16:'/images/bigdata/dmm/icon/ga_ico/10.png',	

	system111:'/images/bigdata/dmm/icon/4z_ico/4z_e_01.svg',
	system112:'/images/bigdata/dmm/icon/4z_ico/4z_n_01.png',    	         
	system211:'/images/bigdata/dmm/icon/4z_ico/4z_e_02.svg',
	system212:'/images/bigdata/dmm/icon/4z_ico/4z_n_02.png',
	system311:'/images/bigdata/dmm/icon/4z_ico/4z_e_03.svg',
	system312:'/images/bigdata/dmm/icon/4z_ico/4z_n_03.png',    	            
	system411:'/images/bigdata/dmm/icon/4z_ico/4z_e_04.svg',
	system412:'/images/bigdata/dmm/icon/4z_ico/4z_n_04.png',    	            
	system121:'/images/bigdata/dmm/icon/4z_ico/4z_e_05.svg',
	system122:'/images/bigdata/dmm/icon/4z_ico/4z_n_05.png',    	            
	system221:'/images/bigdata/dmm/icon/4z_ico/4z_e_06.svg',
	system222:'/images/bigdata/dmm/icon/4z_ico/4z_n_06.png',    	            
	system321:'/images/bigdata/dmm/icon/4z_ico/4z_e_07.svg',
	system322:'/images/bigdata/dmm/icon/4z_ico/4z_n_07.png',    	            
	system421:'/images/bigdata/dmm/icon/4z_ico/4z_e_08.svg',
	system422:'/images/bigdata/dmm/icon/4z_ico/4z_n_08.png',    	            
	system131:'/images/bigdata/dmm/icon/4z_ico/4z_e_09.svg',
	system132:'/images/bigdata/dmm/icon/4z_ico/4z_n_09.png',    	            
	system231:'/images/bigdata/dmm/icon/4z_ico/4z_e_10.svg',
	system232:'/images/bigdata/dmm/icon/4z_ico/4z_n_10.png',    	            
	system331:'/images/bigdata/dmm/icon/4z_ico/4z_e_11.svg',
	system332:'/images/bigdata/dmm/icon/4z_ico/4z_n_11.png',    	            
	system431:'/images/bigdata/dmm/icon/4z_ico/4z_e_12.svg',
	system432:'/images/bigdata/dmm/icon/4z_ico/4z_n_12.png',    	            
	system141:'/images/bigdata/dmm/icon/4z_ico/4z_e_13.svg',
	system142:'/images/bigdata/dmm/icon/4z_ico/4z_n_13.png',    	            
	system241:'/images/bigdata/dmm/icon/4z_ico/4z_e_14.svg',
	system242:'/images/bigdata/dmm/icon/4z_ico/4z_n_14.png',    	            
	system341:'/images/bigdata/dmm/icon/4z_ico/4z_e_15.svg',
	system342:'/images/bigdata/dmm/icon/4z_ico/4z_n_15.png',    	            
	system441:'/images/bigdata/dmm/icon/4z_ico/4z_e_16.svg',
	system442:'/images/bigdata/dmm/icon/4z_ico/4z_n_16.png',	
	database: '/images/bigdata/dmm/icon/4z_ico/4z_database.png',
	table: '/images/bigdata/dmm/icon/4z_ico/4z_table.png'
};

var opacityIcon = {	
		org01:'/images/bigdata/dmm/icon/ga_ico/19-1.png',
		org02:'/images/bigdata/dmm/icon/ga_ico/02-1.png',	
		org03:'/images/bigdata/dmm/icon/ga_ico/13-1.png',
		org04:'/images/bigdata/dmm/icon/ga_ico/09-1.png',
		org05:'/images/bigdata/dmm/icon/ga_ico/14-1.png',	
		org06:'/images/bigdata/dmm/icon/ga_ico/20-1.png',
		org07:'/images/bigdata/dmm/icon/ga_ico/06-1.png',
		org08:'/images/bigdata/dmm/icon/ga_ico/15-1.png',	
	    org09:'/images/bigdata/dmm/icon/ga_ico/01-1.png',
		org10:'/images/bigdata/dmm/icon/ga_ico/05-1.png',   
		org11:'/images/bigdata/dmm/icon/ga_ico/11-1.png',    
		org12:'/images/bigdata/dmm/icon/ga_ico/18-1.png',
		org13:'/images/bigdata/dmm/icon/ga_ico/08-1.png',
		org14:'/images/bigdata/dmm/icon/ga_ico/12-1.png',	
		org15:'/images/bigdata/dmm/icon/ga_ico/03-1.png',
		org16:'/images/bigdata/dmm/icon/ga_ico/10-1.png',	
		
		system111:'/images/bigdata/dmm/icon/4z_ico/4z_e_01_1.svg',
		system112:'/images/bigdata/dmm/icon/4z_ico/4z_n_01_1.png',    	         
		system211:'/images/bigdata/dmm/icon/4z_ico/4z_e_02_1.svg',
		system212:'/images/bigdata/dmm/icon/4z_ico/4z_n_02_1.png',
		system311:'/images/bigdata/dmm/icon/4z_ico/4z_e_03_1.svg',
		system312:'/images/bigdata/dmm/icon/4z_ico/4z_n_03_1.png',    	            
		system411:'/images/bigdata/dmm/icon/4z_ico/4z_e_04_1.svg',
		system412:'/images/bigdata/dmm/icon/4z_ico/4z_n_04_1.png',    	            
		system121:'/images/bigdata/dmm/icon/4z_ico/4z_e_05_1.svg',
		system122:'/images/bigdata/dmm/icon/4z_ico/4z_n_05_1.png',    	            
		system221:'/images/bigdata/dmm/icon/4z_ico/4z_e_06_1.svg',
		system222:'/images/bigdata/dmm/icon/4z_ico/4z_n_06_1.png',    	            
		system321:'/images/bigdata/dmm/icon/4z_ico/4z_e_07_1.svg',
		system322:'/images/bigdata/dmm/icon/4z_ico/4z_n_07_1.png',    	            
		system421:'/images/bigdata/dmm/icon/4z_ico/4z_e_08_1.svg',
		system422:'/images/bigdata/dmm/icon/4z_ico/4z_n_08_1.png',    	            
		system131:'/images/bigdata/dmm/icon/4z_ico/4z_e_09_1.svg',
		system132:'/images/bigdata/dmm/icon/4z_ico/4z_n_09_1.png',    	            
		system231:'/images/bigdata/dmm/icon/4z_ico/4z_e_10_1.svg',
		system232:'/images/bigdata/dmm/icon/4z_ico/4z_n_10_1.png',    	            
		system331:'/images/bigdata/dmm/icon/4z_ico/4z_e_11_1.svg',
		system332:'/images/bigdata/dmm/icon/4z_ico/4z_n_11_1.png',    	            
		system431:'/images/bigdata/dmm/icon/4z_ico/4z_e_12_1.svg',
		system432:'/images/bigdata/dmm/icon/4z_ico/4z_n_12_1.png',    	            
		system141:'/images/bigdata/dmm/icon/4z_ico/4z_e_13_1.svg',
		system142:'/images/bigdata/dmm/icon/4z_ico/4z_n_13_1.png',    	            
		system241:'/images/bigdata/dmm/icon/4z_ico/4z_e_14_1.svg',
		system242:'/images/bigdata/dmm/icon/4z_ico/4z_n_14_1.png',    	            
		system341:'/images/bigdata/dmm/icon/4z_ico/4z_e_15_1.svg',
		system342:'/images/bigdata/dmm/icon/4z_ico/4z_n_15_1.png',    	            
		system441:'/images/bigdata/dmm/icon/4z_ico/4z_e_16_1.svg',
		system442:'/images/bigdata/dmm/icon/4z_ico/4z_n_16_1.png',
		database: '/images/bigdata/dmm/icon/4z_ico/4z_database_1.png',
		table: '/images/bigdata/dmm/icon/4z_ico/4z_table_1.png'
	};

var saveClickCodeVal = ["", "", "", ""];
var saveClickIndex = ["", "", "", ""];
var clickNodeId = '';

var searchNodeData;

// 클릭한 기관의 데이터 임시 저장
var tmpOrgNodes = new vis.DataSet();
var tmpOrgEdges = new vis.DataSet();

// 클릭한 정보시스템의 데이터 임시 저장
var tmpSystemNodes = new vis.DataSet();
var tmpSystemEdges = new vis.DataSet();

// 클릭한 데이터베이스의 데이터 임시 저장
var tmpDatabaseNodes = new vis.DataSet();
var tmpDatabaseEdges = new vis.DataSet();
// 클릭한 데이터베이스의 ID 저장 변수
var databaseIndex = 0;


$(document).ready(function() {
	//graphD3Init();
	graphVisInit();
	tabClick(null, null);
	makeTreeMap(0);
	//-------------------------------------------------------------------------
	// 버튼설정
	
	// 범례 보기
    $('#legend').click(function(e) {
    	if(!$('.mnli_04').is(":visible")) {
    		$('.mnli_04').show();
    	} else {
    		$('.mnli_04').hide();
    	}
    	return false;
    });
	
	// 검색버튼 클릭
	$("#btnSearch").click(function (e) {
		dataSearch();
		return false;
	});
	
	// 팝업 닫기 클릭
	$(".btn_close").click(function() {
		$(this).parent().modal("hide");
		return false;
	});
	
	
});


function openTreeMap() {
	$('#mynetwork').css('display', 'none');
	$('#treeMap').css('display', 'block');	
	
	$('.lgd_body').css('display', 'none');
	
	$('.pbtn2_01').attr('class', 'pbtn2_01_n');
	$('.pbtn2_01_n').attr('class', 'pbtn2_01_n');
	$('.pbtn2_02').attr('class', 'pbtn2_02_n');
	$('.pbtn2_02_n').attr('class', 'pbtn2_02_n');
	$('.pbtn2_03_n').attr('class', 'pbtn2_03');
	$('.pbtn2_sp01').hide();
	$('.pbtn2_sp02').hide();
	$('.pbtn2_sp03').hide();
	$('.pbtn2_sp04').hide();
	
	$('.tab03 ul > li').eq(0).find('> a').removeClass();
	$('.tab03 ul > li').eq(1).find('> a').addClass('active');
	
	$('.mnli_01').text('트리맵');
	$('.mnli_03 > a').css('display', 'none'); 
	
	$('.mnli_04').css('display', 'none');
	
	var height = getUlHeight() + 150;
	console.log(height);
	$('.datamap_tree').height(height);
	
}
function makeTreeMap(flag) {
	// 트리구조 JSON 생성
	
	var treeModel = function (arrayList, rootId) {
		var rootNodes = [];
		var traverse = function (nodes, item, index) {
		if (nodes instanceof Array) {
		return nodes.some(function (node) {					
			if (node.id === item.parentId) {
				if(node.type == "database") {
					node.table = node.table || [];
					return node.table.push(arrayList.splice(index, 1)[0]);
				} else if(node.type == "infoSystem"){
					node.database = node.database || [];
					return node.database.push(arrayList.splice(index, 1)[0]);
				} else {
					node.infoSystem = node.infoSystem || [];
					return node.infoSystem.push(arrayList.splice(index, 1)[0]);
				}
			}
						
			if(node.type == "database") {
				return traverse(node.table, item, index);
			} else if (node.type == "infoSystem") {
				return traverse(node.database, item, index);
			}
							
			return traverse(node.infoSystem, item, index);
					
			});
		}
	};

	while (arrayList.length > 0) {
		arrayList.some(function (item, index) {
			if (item.parentId === rootId) {
				return rootNodes.push(arrayList.splice(index, 1)[0]);
			}
	
			return traverse(rootNodes, item, index);
			});
		}
	
		return rootNodes;
	};

	
	if(flag == 0) {
		saveClickCodeVal[0] = "07";
		saveClickIndex[0] = 0;
		compareId = "07";
	} else {
		$('ul.four').empty();
	}
	
    $.ajax({
    	url: "/bdp/dmm/printTreeMap.do",
    	type: "GET",
    	async: false,
    	data: {"fdmorgCode": saveClickCodeVal[0], "syslstCode": saveClickCodeVal[1], "dbCode": saveClickCodeVal[2]},
    	success: function(data) {
    		treeMapJson = JSON.stringify(data.graph);
    		var json = new Object();    		    		
    		var tree = treeModel(data.graph, null);    		
    		json.graph = tree; 		
    		var output = template.render(json);
			
    		$('.tNav').html(output).promise().done(function() {    		
    			if(flag == 0) {
    				$('.tNav').css('display', 'block');
    				showInitTreeMap();
    			} else {
        			if(flag == 'three') {
       				 $('.two > li').each(function(i) {
       						if($(this).find('> a').attr('data-item') == saveClickCodeVal[0]) {
       							saveClickIndex[0] = i;
       						}
       					 });
       					 
       					 $('.three > li').each(function(i) {
       						if($(this).find('a').attr('data-item') == saveClickCodeVal[1]) {
       							saveClickIndex[1] = i;
       						}
       					 });
        			} else if (flag == 'four') {
        				 $('.two > li').each(function(i) {
     						if($(this).find('> a').attr('data-item') == saveClickCodeVal[0]) {
     							saveClickIndex[0] = i;
     						}
     					 });
     					 
     					 $('.three > li').each(function(i) {
     						if($(this).find('a').attr('data-item') == saveClickCodeVal[1]) {
     							saveClickIndex[1] = i;
     						}
     					 });
     					 
     					 $('.four > li').each(function(i) {
      						if($(this).find('a').attr('data-item') == saveClickCodeVal[2]) {
      							saveClickIndex[2] = i;
      						}
      					 });     					 
     					         			
       				} else if (flag == 'five') {
       					
          				 $('.two > li').each(function(i) {
        						if($(this).find('> a').attr('data-item') == saveClickCodeVal[0]) {
        							saveClickIndex[0] = i;
        						}
        					 });
        					 
        					 $('.three > li').each(function(i) {
        						if($(this).find('a').attr('data-item') == saveClickCodeVal[1]) {
        							saveClickIndex[1] = i;
        						}
        					 });
        					 
        					 $('.four > li').each(function(i) {
         						if($(this).find('a').attr('data-item') == saveClickCodeVal[2]) {
         							saveClickIndex[2] = i;
         						}
         					 });
        					 
        					 $('.five > li').each(function(i) {
         						if($(this).find('a').attr('data-item') == saveClickCodeVal[3]) {
         							saveClickIndex[3] = i;
         						}
         					 });
       					
       				}
    				setTreeMap();
    				showTreeNode(flag);
    				var height = getUlHeight() + 150;
    				$('.datamap_tree').height(height);
    				
    				
    				for(var i=0; i<$('ul.four').length; i++) {
    				 	if($('ul.four > li > a').eq(i).hasClass('active')) {
    				 		$('.datamap_tree').height($('ul.five').eq(i).height() + 150);
    					}
    				}
    				
    				
    			}
    			    			
    		});
    	},
    	
    	error: function() {
    		alert("연결 에러");
    	}    	
    });
    


}

function showTreeNode(className) {
	
	// $('.one > li > a').css("border", "#FF0000 1px solid");
	$('.one > li > a').addClass("active");
	
	if(className == 'one') {
		$('ul.two').css('display', 'block');	
		$('ul.one li').addClass('n');
	}
	
	else if(className == 'two') {
		$('ul.one').css('display', 'block');
		$('ul.two').css('display', 'block');
		$('ul.three').css('display', 'block');
		$('ul.four').css('display', 'block');
		$('ul.five').css('display', 'block');
		
		initBold();
		
		$('.three li').css('font-weight', 'normal');
		
		// $('.two li').eq(saveClickIndex[0]).css('font-weight', 'bold');
		// $('.two li').eq(saveClickIndex[0]).find('> a').css("border", "#FF0000 1px solid");
		$('.two li').eq(saveClickIndex[0]).find('> a').addClass("active");

		// $('.three li').eq(0).find('> a').css('font-weight', 'bold');
		// $('.three li').eq(0).find('> a').css("border", "#FF0000 1px solid");
		$('.three li').eq(0).find('> a').addClass("active");
		
		// $('.four li').eq(0).find('> a').css('font-weight', 'bold');
		// $('.four li').eq(0).find('> a').css("border", "#FF0000 1px solid");
		$('.four li').eq(0).find('> a').addClass("active");
				
		if($('ul.two >li').size() > 1) {
			$('ul.two > li').eq(0).addClass("trs");
			$('ul.two > li').eq($('ul.two > li').size()-1).addClass("tre");
		} else {
			$('ul.two > li').eq(0).addClass("n");
		}
		
		if($('ul.three > li').size() > 1) {
			$('ul.three > li').eq(0).addClass("trs");
			$('ul.three > li').eq($('ul.three > li').size()-1).addClass("tre");
		} else {
			$('ul.three > li').eq(0).addClass("n");
		}
		
		if($('ul.four > li').size() > 1) {
			$('ul.four > li').eq(0).addClass("trs");
			$('ul.four > li').eq($('ul.four > li').size()-1).addClass("tre");
		} else {
			$('ul.four > li').eq(0).addClass("n");
		}
		
		if($('ul.five > li').size() > 1) {
			$('ul.five > li').eq(0).addClass("trs");
			$('ul.five > li').eq($('ul.five > li').size()-1).addClass("tre");
		} else {
			$('ul.five > li').eq(0).addClass("n");
		}
		
	} else if(className == 'three') {
	
		$('ul.one').css('display', 'block');
		$('ul.two').css('display', 'block');
		$('ul.three').css('display', 'block');
		$('ul.four').css('display', 'block');
		$('ul.five').css('display', 'block');
		
		initBold();
		
		$('.two li').eq(saveClickIndex[0]).css('font-weight', 'bold');
		$('.three li').css('font-weight', 'normal');
		$('.three li').eq(saveClickIndex[1]).css('font-weight', 'bold');
		
		$('.two li').eq(saveClickIndex[0]).find('> a').addClass("active");
		$('.three li').eq(saveClickIndex[1]).find('> a').addClass("active");
		
		//$('.two li').eq(saveClickIndex[0]).find('> a').css("border", "#FF0000 1px solid");
		//$('.three li').eq(saveClickIndex[1]).find('> a').css("border", "#FF0000 1px solid");
		
		
		if($('ul.two > li').size() > 1) {
			$('ul.two > li').eq(0).addClass("trs");
			$('ul.two > li').eq($('ul.two > li').size()-1).addClass("tre");
		} else {
			$('ul.two > li').eq(0).addClass("n");
		}
		
		if($('ul.three > li').size() > 1) {
			$('ul.three > li').eq(0).addClass("trs");
			$('ul.three > li').eq($('ul.three > li').size()-1).addClass("tre");
		}else {
			$('ul.three > li').eq(0).addClass("n");
		}
		
		if($('ul.four > li').size() > 1) {
			$('ul.four > li').eq(0).addClass("trs");
			$('ul.four > li').eq($('ul.four  > li').size()-1).addClass("tre");
		} else {
			$('ul.four > li').eq(0).addClass("n");
		} 
		
		if($('ul.five > li').size() > 1) {
			$('ul.five > li').eq(0).addClass("trs");
			$('ul.five > li').eq($('ul.five  > li').size()-1).addClass("tre");
		} else {
			$('ul.five > li').eq(0).addClass("n");
		} 
		

	} else if(className == 'four') {
	
		$('ul.one').css('display', 'block');
		$('ul.two').css('display', 'block');
		$('ul.three').css('display', 'block');
		$('ul.four').css('display', 'block');
		$('ul.five').css('display', 'block');
		
		initBold();
		
		/*
		$('.two li').eq(saveClickIndex[0]).css('font-weight', 'bold');
		$('.three li').css('font-weight', 'normal');
		$('.three li').eq(saveClickIndex[1]).css('font-weight', 'bold');
		$('.four li').css('font-weight', 'normal');
		$('.four li').eq(saveClickIndex[2]).css('font-weight', 'bold');
		
		$('.two li').eq(saveClickIndex[0]).find('> a').css("border", "#FF0000 1px solid");
		$('.three li').eq(saveClickIndex[1]).find('> a').css("border", "#FF0000 1px solid");
		$('.four li').eq(saveClickIndex[2]).find('> a').css("border", "#FF0000 1px solid");
		*/
		
		$('.two li').eq(saveClickIndex[0]).find('> a').addClass("active");
		$('.three li').eq(saveClickIndex[1]).find('> a').addClass("active");
		$('.four li').eq(saveClickIndex[2]).find('> a').addClass("active");

		if($('ul.two > li').size() > 1) {
			$('ul.two > li').eq(0).addClass("trs");
			$('ul.two > li').eq($('ul.two > li').size()-1).addClass("tre");
		} else {
			$('ul.two > li').eq(0).addClass("n");
		}
		
		if($('ul.three > li').size() > 1) {
			$('ul.three > li').eq(0).addClass("trs");
			$('ul.three > li').eq($('ul.three > li').size()-1).addClass("tre");
		}else {
			$('ul.three > li').eq(0).addClass("n");
		}
		
		if($('ul.four > li').size() > 1) {
			$('ul.four > li').eq(0).addClass("trs");
			$('ul.four > li').eq($('ul.four > li').size()-1).addClass("tre");
		} else {
			$('ul.four > li').eq(0).addClass("n");
		} 
		
		if($('ul.five > li').size() > 1) {
			$('ul.five > li').eq(0).addClass("trs");
			$('ul.five > li').eq($('ul.five > li').size()-1).addClass("tre");
		} else {
			$('ul.five > li').eq(0).addClass("n");
		}
	} else {
			
		$('ul.one').css('display', 'block');
		$('ul.two').css('display', 'block');
		$('ul.three').css('display', 'block');
		$('ul.four').css('display', 'block');
		$('ul.five').css('display', 'block');
		
		initBold();
		
		/*
		$('.two li').eq(saveClickIndex[0]).css('font-weight', 'bold');
		$('.three li').css('font-weight', 'normal');
		$('.three li').eq(saveClickIndex[1]).css('font-weight', 'bold');
		$('.four li').css('font-weight', 'normal');
		$('.four li').eq(saveClickIndex[2]).css('font-weight', 'bold');
		$('.five li').css('font-weight', 'normal');
		$('.five li').eq(saveClickIndex[3]).css('font-weight', 'bold');
		
		$('.two li').eq(saveClickIndex[0]).find('> a').css("border", "#FF0000 1px solid");
		$('.three li').eq(saveClickIndex[1]).find('> a').css("border", "#FF0000 1px solid");
		$('.four li').eq(saveClickIndex[2]).find('> a').css("border", "#FF0000 1px solid");
		$('.five li').eq(saveClickIndex[3]).find('> a').css("border", "#FF0000 1px solid");
		*/
		
		$('.two li').eq(saveClickIndex[0]).find('> a').addClass("active");
		$('.three li').eq(saveClickIndex[1]).find('> a').addClass("active");
		$('.four li').eq(saveClickIndex[2]).find('> a').addClass("active");
		$('.five li').eq(saveClickIndex[3]).find('> a').addClass("active");
		
		if($('ul.two > li').size() > 1) {
			$('ul.two > li').eq(0).addClass("trs");
			$('ul.two > li').eq($('ul.two > li').size()-1).addClass("tre");
		} else {
			$('ul.two > li').eq(0).addClass("n");
		}
		
		if($('ul.three > li').size() > 1) {
			$('ul.three > li').eq(0).addClass("trs");
			$('ul.three > li').eq($('ul.three > li').size()-1).addClass("tre");
		}else {
			$('ul.three > li').eq(0).addClass("n");
		}
		
		if($('ul.four > li').size() > 1) {
			$('ul.four > li').eq(0).addClass("trs");
			$('ul.four > li').eq($('ul.four > li').size()-1).addClass("tre");
		} else {
			$('ul.four > li').eq(0).addClass("n");
		} 
		
		if($('ul.five > li').size() > 1) {
			$('ul.five > li').eq(0).addClass("trs");
			$('ul.five > li').eq($('ul.five > li').size()-1).addClass("tre");
		} else {
			$('ul.five > li').eq(0).addClass("n");
		}
	}
}

function showInitTreeMap() {
	$('.one > li > a').css('font-weight', 'bold');
	$('.one > li > a').css("border", "#FF0000 1px solid");
	$('.two').eq(0).css('display', 'block');
	$('.three').eq(0).css('display', 'block');
	$('.four').eq(0).css('display', 'block');
	$('.five').eq(0).css('display', 'block');

	/*
	$('.two').find('li').eq(0).find('> a').css('font-weight', 'bold');
	$('.two').find('li').eq(0).find('> a').css("border", "#FF0000 1px solid");
	
	$('.three').find('li').eq(0).find('> a').css('font-weight', 'bold');
	$('.three').find('li').eq(0).find('> a').css("border", "#FF0000 1px solid");
	
	$('.four').find('li').eq(0).find('> a').css('font-weight', 'bold');
	$('.four').find('li').eq(0).find('> a').css("border", "#FF0000 1px solid");
	*/
	
	
	$('.two').find('li').eq(0).find('> a').addClass("active");
	$('.three').find('li').eq(0).find('> a').addClass("active");
	$('.four').find('li').eq(0).find('> a').addClass("active");
	
	
	
	$('ul.two li').eq(0).addClass("trs");
	$('ul.two li').eq($('ul.two li').size()-1).addClass("tre");
	
	$('ul.three li').eq(0).addClass("trs");
	$('ul.three li').eq($('ul.three li').size()-1).addClass("tre");
	
	$('ul.four li').eq(0).addClass("trs");
	$('ul.four li').eq($('ul.four li').size()-1).addClass("tre");
	
	$('ul.five li').eq(0).addClass("trs");
	$('ul.five li').eq($('ul.five li').size()-1).addClass("tre");	
		
	setTreeMap();
	
}

function setTreeMap(){	
	$('.tNav li').dblclick(function() {
		var id;		
		var tmj = JSON.parse(treeMapJson);
		selectMetaInfo = {};

		for(var key in tmj) {
			if(tmj[key].id == $(this).find(' > a').attr('data-item'))	{
				id = tmj[key].id;
				
				if(tmj[key].id.length == 3) {
				    selectMetaInfo.code = tmj[key].id;
				    selectMetaInfo.codeType = 'system';
				    
				} else if(tmj[key].id.length == 4) {					
				    selectMetaInfo.code = tmj[key].parentId;
				    selectMetaInfo.codeType = 'system';	
				}
				
				if(2 < id.length && id.length < 5) {
					selDatabase = tmj[key].name;
					loadMetaData(tmj[key]);
				}
			}
		}
		
		return false;

	}).click(function() {
			
		if(compareId == $(this).find('a').attr('data-item')) {
			compareId = $(this).find('a').attr('data-item');
						
			return false;
			
		} else {		
			var params = "nodeNum=" + $(this).find('a').attr('data-item');
			compareId = $(this).find('a').attr('data-item');
						
			if($(this).closest('ul').attr('class') == 'one') {

				return false;
			}
			
			else if($(this).closest('ul').attr('class') == 'two') {
				
				// 기관
				saveClickCodeVal[0] = $(this).find('a').attr('data-item');
				// 정보시스템
				saveClickCodeVal[1] = "";
				// 데이터베이스
				saveClickCodeVal[2] = "";				
				saveClickCodeVal[3] = "";
				
				saveClickIndex[0] = $(this).index();
				saveClickIndex[1] = "";
				saveClickIndex[2] = "";
				saveClickIndex[3] = "";

				makeTreeMap($(this).closest('ul').attr('class'));
				return false;
				
			} else if($(this).closest('ul').attr('class') == 'three') {
				saveClickCodeVal[1] = $(this).find('a').attr('data-item');
				saveClickCodeVal[2] = "";
				saveClickCodeVal[3] = "";
				
				saveClickIndex[1] = $(this).index();
				saveClickIndex[2] = "";
				saveClickIndex[3] = "";
				
				makeTreeMap($(this).closest('ul').attr('class'));
			
				return false;
		
			} else if($(this).closest('ul').attr('class') == 'four') {
				saveClickCodeVal[2] = $(this).find('a').attr('data-item');
				saveClickCodeVal[3] = "";
				saveClickIndex[2] = $(this).index();
				saveClickIndex[3] = "";
				makeTreeMap($(this).closest('ul').attr('class'));
				return false;
		
			} else {
				saveClickCodeVal[3] = $(this).find('a').attr('data-item');
				saveClickIndex[3] = $(this).index();
				makeTreeMap($(this).closest('ul').attr('class'));
				return false;
			}
		}
	});
	
}
	

function tabClick(codeType, code) {
	currentTxnm = '';
	clickNodeId = '';
	$('.lgd_body').css('display', 'none');
	
	$('#searchDataList').val("");
	isFdmorgMode = false;
	
	
	var hide = true;
	$('#legend').click(function() {
		if(!hide) {
			$('.lgd_body').css('display', 'none');
			hide = true;
		} else {
			$('.lgd_body').css('display', 'block');
			hide = false;			
		}
	});
	
	$('#mynetwork').css('display', 'block');
	$('#treeMap').css('display', 'none');	
	$('.mnli_03 > a').css('display', 'block');
	$('.mnli_04').css('display', 'none');

	// 그래프 상단 네비게이션 표시, 버튼 설정
    if(codeType == 'fdmorg') {
    	var tabName = ['농산', '축산', '산림', '식품'];
    	var codeNum = +code;
    	isHierarchical = false;    	
    	
    	$('#fdmorg1').css('display', 'block');
    	$('#txmn1').css('display', 'none');
    	
    	currentMenu = "분류체계별(" + tabName[codeNum-1] + ")";
     	$('.pbtn2_01_n').attr('class', 'pbtn2_01');
    	$('.pbtn2_02').attr('class', 'pbtn2_02_n');
    	$('.pbtn2_03').attr('class', 'pbtn2_03_n');
  		$('.pbtn2_sp01').attr('class', 'pbtn2_sp01 pbtn2_sp_' + (codeNum == 1 ? 's' : 'n')).show();
   		$('.pbtn2_sp02').attr('class', 'pbtn2_sp02 pbtn2_sp_' + (codeNum == 2 ? 's' : 'n')).show();
   		$('.pbtn2_sp03').attr('class', 'pbtn2_sp03 pbtn2_sp_' + (codeNum == 3 ? 's' : 'n')).show();
   		$('.pbtn2_sp04').attr('class', 'pbtn2_sp04 pbtn2_sp_' + (codeNum == 4 ? 's' : 'n')).show();
   		   		

   		
   		for(var i=0; i<tabName.length; i++) {
   			if(i == (codeNum-1)) {
   		    	$('#fdmorg1 ul > li').eq(i).find('> a').addClass('active');
   				
   			} else {
   		    	$('#fdmorg1 ul > li').eq(i).find('> a').removeClass();
   			}
   		}
   		
   		$('.mnli_03 > a').css('display', 'none');
   		$('#legend').css('display', 'block');
   		
   		isFdmorgMode = true;
   		
   		$('.tab01.column2 > li').eq(0).find('> a').removeClass();
   		$('.tab01.column2 > li').eq(1).find('> a').addClass('active');
   		
   		
   		
    } else {
    	
    	$('#fdmorg1').css('display', 'none');
    	$('#txmn1').css('display', 'block');
    	currentMenu = "기관별";
    	
    	$('.pbtn2_01').attr('class', 'pbtn2_01_n');
    	$('.pbtn2_02').attr('class', 'pbtn2_02');
    	$('.pbtn2_02_n').attr('class', 'pbtn2_02');
    	$('.pbtn2_03').attr('class', 'pbtn2_03_n');
    	$('.pbtn2_sp01').hide();
    	$('.pbtn2_sp02').hide();
    	$('.pbtn2_sp03').hide();
    	$('.pbtn2_sp04').hide();
    	
   		$('.tab01.column2 > li').eq(0).find('> a').addClass('active');
   		$('.tab01.column2 > li').eq(1).find('> a').removeClass();
    	
    	$('#txmn1 ul > li').eq(0).find('> a').addClass('active');
    	$('#txmn1 ul > li').eq(1).find('> a').removeClass();
   		
    	$('.mnli_03 > a').css('display', 'block');
    	
    }  
		
	$('.mnli_01').html("<span>" + currentMenu + "</span>");   
	$("#mynetwork").data("codeType", codeType);
	
	
	
	dataLoadEndedByIndex = {};
	
    tmpSystemNodes.clear();
    tmpSystemEdges.clear();
    tmpDatabaseNodes.clear();
    tmpDatabaseEdges.clear();	        
    tmpOrgNodes.clear();
    tmpOrgEdges.clear();
	
	graphVisInit();
	loadData(codeType, code);
}

function loadData(codeType, code) {
	var url = "";
	var param = {};
	param.codeType = codeType;
	param.code = code;
	if(isHierarchical == true) {
		param.mode = 'H';
	} else {
		param.mode = null;
	}
	
	$.ajax({
		url: "/bdp/dmm/graph.do",
		type: "POST",
		data: param,
		async: false,
		success: function(data) {
			console.log(data);
			graphVisReset();
			graphVisRedraw(data.nodes, data.edges, null);			
		},
    	error: function() {
    		alert("연결 에러");
    	}
	});
}

function loadChild(node, params) {
	var url = "";
	var param = {};
    param.lastID = dataNodes.length;
    param.rootID = node.id;

	if(dataLoadEndedByIndex[node.id] == 1){
		neighbourhoodHighlight(params, dataNodes);
		return;
	}
	
	dataLoadEndedByIndex[node.id] = 1;
	
	// 기관 선택시
	if(node['group'].indexOf('org') > -1) {
        param.codeType = "FDMORG";
        param.code = node['fdmorgCode'];        
        groupName = "정보 시스템이";
        
	}
	// 정보시스템 선택시
	else if(node['group'].indexOf('system') > -1) {
        param.codeType = "SYSTEM";
        param.code = node['sysCode'];
        
        groupName = "데이터베이스가";
	}
	// 데이터베이스 선택시
	else if(node['group'] == 'database') {
        param.codeType = "DATABASE";
        param.code = node['dbCode'];        
        groupName = "테이블이";
	}
	// 테이블 선택시
	else {
		neighbourhoodHighlight(params, dataNodes);
		return;
	}
	
	$.ajax({
		url: "/bdp/dmm/childNode.do",
		type: "POST",
		data: param,
		async: false,
		success: function(data) {
			$("#orgComment").text("총 " + data.nodes.length + "개의 " + groupName + " 있습니다");
			graphVisRedraw(data.nodes, data.edges, node, params);
			initGraphNode.update(data.nodes);
			initGraphEdge.update(data.edges);
		},
    	error: function() {
    		alert("연결 에러");
    	}
	});
}

//-----------------------------------------------------------------------------
// VIS.JS
//-----------------------------------------------------------------------------
//최초 1회
function graphVisInit(_nodes, _links) {    
	// NODE ICON
	var groups = {};
	$.each(icon, function(k, v) {
		groups[k] = {shape: 'image', color: '#808080', image: v, shapeProperties: {useImageSize: false}, font: {size: 34, strokeWidth: 3, strokeColor: 'white'}};
	});
	
	var options = {
    	nodes: {
    		shapeProperties: {
    			interpolation: false,  // only for image and circularImage shapes
    		},
            color: {
                border: 'lightgray',
                background: 'white',
                highlight: {
                  border: 'lightgray',
                  background: 'lightblue'
             	}
            }
    	},
    	
    	interaction: {
    		hover: true,
    		selectConnectedEdges: true,
    		selectable: true
    	},    	
    	groups: groups
	};
	
	if(isFdmorgMode == true) {
		options['layout'] = {improvedLayout: true, randomSeed: 191006};
		options['physics'] = {
				forceAtlas2Based: {
				gravitationalConstant: -1300,
				springLength: 480				
			},			
			solver: 'forceAtlas2Based',
			timestep: 0.34,
			minVelocity: 6,
		    stabilization: {
	            enabled: true,
	            onlyDynamicEdges: true,         
                iterations: 20000,
                updateInterval:25
		    }
		};		
		options['edges'] = {
    		smooth: {
    			enabled: true,
    			forceDirection: "none",    			
    			roundness: 0,
    			type: "dynamic"
    		}
    	};
	    
	} else {
		options['layout'] = {improvedLayout: true, randomSeed: 191006};
		
		options['physics'] = {
				forceAtlas2Based: {
				gravitationalConstant: -700,
				springLength: 700,
				springConstant: 0.50,
				damping: 0.50,
			},			
			solver: 'forceAtlas2Based',
			timestep: 0.34,
			minVelocity: 4,
		    stabilization: {
	            enabled: true,
	            onlyDynamicEdges: true,         
                iterations:20000,
                updateInterval:25
		    }
		};
		
		options['edges'] = {
    		smooth: {
    			enabled: true,
    			forceDirection: "none",
    			roundness: 0,
    			type: "dynamic"
    		}
    	};
		
	}
	// 그래프 옵션

    var container = document.getElementById('mynetwork');
	visNetwork = new vis.Network(container, {nodes: null, edges: null}, options);

	visNetwork.on("doubleClick", function(params) {
		var targetNode = dataNodes.get(params.nodes[0]);
		
		if(targetNode.group != 'table') {
			
			if(targetNode.group != null ) {
				if(targetNode.group == "database") {
					selDatabase = targetNode.label;
				} else {
					selDatabase = '';
				}
			}			
			
    		loadMetaData(targetNode);
		}
		
    });
    
	visNetwork.on("click", function (params) {
		
		if(typeof params.nodes[0] != 'undefined') {			
			if (params.nodes.length == 1) {
				
				var node = dataNodes.get(params.nodes[0]);											
				selectedNode(node);
				loadChild(node, params);	            				
	            clickNodeId = params.nodes[0];        	
			}
		} else {			
			neighbourhoodHighlight(params, dataNodes);
			clickNodeId = '';
			
			if(isFdmorgMode == true) {
				var allNodes = dataNodes.get({returnType:"Object"});
			    var updateArray = [];
				var node = dataNodes.get(params.nodes[0]);	
			        
				for (nodeId in allNodes) {

					if (allNodes.hasOwnProperty(nodeId)) {
			        	 if(allNodes[nodeId].group.indexOf("system") > -1) {
			      	        //allNodes[nodeId].size = 180;
			           	 } else if(allNodes[nodeId].group.indexOf("database") > -1) {
			           		allNodes[nodeId].size = 110;
			           	 } else if(allNodes[nodeId].group.indexOf("table") > -1) {
			           		allNodes[nodeId].size = 100;        		 
			           	 }
					    allNodes[nodeId].font = {
					    	size: 100,
							color: '#000000'		
						};     
				        	
				     updateArray.push(allNodes[nodeId]);
				    }
				}
				
				dataNodes.update(updateArray);
				visNetwork.moveTo({scale: 0.105});
			}
		}
    });
	
	visNetwork.on("selectNode", function (params) {
    	var options = {
    		nodes:{
    			color: {
    				background: '#FFFFFF',
    			},
    			hidden: false
    		},
	    	edges: {
	    		color:'#000000'
			}
	    };
    	visNetwork.setOptions(options);
	});
	
	visNetwork.on("deselectNode", function () {		
		deselectNode();
    });
	visNetwork.on("dragStart", function() {
		clickNodeId = '';
	});
	
	visNetwork.on("stabilized", function(params) {
		var allNodes = dataNodes.get({returnType:"Object"});		
	
		for(var i=0; i<allNodes.length; i++) {
			var coodi = visNetwork.getPositions(allNodes[i].id);
			allNodes[i].x = coodi.x;
			allNodes[i].y = coodi.y;
			allNodes[i].fixed = true;
		}
		
		if(clickNodeId != '') {
			graphVisSetCenter(clickNodeId);
		}
			
    }); 
	
}

// 그래프 리셋
function graphVisReset() {
	clearInterval(visNetworkDrawTimer);
	
    dataNodes = new vis.DataSet();
    dataLinks = new vis.DataSet();
    visNetwork.setData({nodes: dataNodes, edges: dataLinks});
    
    initGraphNode.clear();
    initGraphEdge.clear();
	
    visNetwork.fit(); // canvas center
	if($("#mynetwork").data("codeType") == "fdmorg") {
		visNetwork.moveTo({scale: 0.105});
	} else {
		visNetwork.moveTo({scale: 0.38});		
	}
	
	dataLinkedByIndex = {};
	dataLoadEndedByIndex = {};
}

function graphDistinctGroup(_nodes, _links, _clickNode, params) {
	
	if(_clickNode.group.indexOf("org") != -1) {
		tmpOrgNodes.add(_nodes);
		tmpOrgEdges.add(_links);								  
	} else if(_clickNode.group.indexOf("system") != -1) {
		tmpSystemNodes.add(_nodes);
		tmpSystemEdges.add(_links);
	} else if(_clickNode.group.indexOf("database") != -1) {
		tmpDatabaseNodes.add(_nodes);
		tmpDatabaseEdges.add(_links);
	}
	
	dataNodes.add(_nodes);
	dataLinks.add(_links);
	neighbourhoodHighlight(params, dataNodes);	
}

// 그래프 노드 추가
function graphVisRedraw(_nodes, _links, _clickNode, params) {
	var drawInterval = 20;
	var drawIndex = drawInterval;
	
		if(params != null) {
			graphDistinctGroup(_nodes, _links, _clickNode, params);	
		} else {
			dataLinks.add(_links);
			dataNodes.add(_nodes);
			
			if(isFdmorgMode == true) {				
			    var updateArray = [];       
			    var allNodes = dataNodes.get({returnType:"Object"});	
			    for (var nodeId in allNodes) {
			        var gName = allNodes[nodeId].group;
			        allNodes[nodeId].image = icon[gName];
		        	allNodes[nodeId].size = allNodes[nodeId].size * 2.8;
			        allNodes[nodeId].font = {
				        size: 103,
						color: '#000000'		
					}
			        updateArray.push(allNodes[nodeId]);
			    }
			    
			 dataNodes.update(updateArray);
			}
		}
}

// 그래프 센터로 이동
function graphVisSetCenter(nodeId) {
	if(nodeId.length <= 0) {
		return;
	}
	
	if(isFdmorgMode == false) {
	    visNetwork.focus(nodeId, {
	    	scale: 0.33,
	        animation: {
	            duration: 500,
	            easingFunction: "easeInOutQuint"
	        }
	    });
	} else {
	    visNetwork.focus(nodeId, {
	    	scale: 0.105,
	        animation: {
	            duration: 500,
	            easingFunction: "easeInOutQuint"
	        }
	    });
	}
}

function graphDataClear(node, group) {
	var lock = false;
	
    for (var key in dataLoadEndedByIndex) {
    	if(key == node.id) {
    		 lock = true;
    		 return ;
    	}
    }
    
    if(lock == false) {
    	switch(group) {
	    	case 1:
	    		dataLoadEndedByIndex = {};
		        dataNodes.remove(tmpOrgNodes.get());
		        dataLinks.remove(tmpOrgEdges.get());
		        tmpOrgNodes.clear();
		        tmpOrgEdges.clear();
	    	case 2:
	    		dataLoadEndedByIndex = {};
		        dataNodes.remove(tmpSystemNodes.get());
		        dataLinks.remove(tmpSystemEdges.get());
		        tmpSystemNodes.clear();
		        tmpSystemEdges.clear();
	    	case 3:
		        dataNodes.remove(tmpDatabaseNodes.get());
		        dataLinks.remove(tmpDatabaseEdges.get());
		        tmpDatabaseNodes.clear();
		        tmpDatabaseEdges.clear();
    	}
    }
}

// 노드 선택시 타이틀 및 버튼, 데이터 설정
function selectedNode(node) {
	
	// 기관 선택시
	if(node['group'].indexOf('org') > -1) {
		currentTxnm = node['fdmorgNm'];
		nodeName = node['fdmorgNm'];
		
		$("#selectedNode").text(nodeName);
        $("#orgLabel").show();
        $("#actionButtons").hide();
        
        menuName[0] = nodeName;
        $('.mnli_01').html("<span>" + currentMenu + "</span><span>" + nodeName + "</span>");
        graphDataClear(node, 1);
        
        clearInterval(visNetworkDrawTimer);
        
	}
	// 정보시스템 선택시
	else if(node['group'].indexOf('system') > -1) {
        nodeName = node.label;
        menuName[1] = node.label;
        var mName = printMenu(currentMenu, menuName, 1);
        $('.mnli_01').html("<span>" + currentMenu + "</span><span>" + mName + "</span>");
        
        console.log(currentMenu);
        console.log(mName);
        
        $("#selectedNode").text(nodeName);
        $("#orgLabel").hide();
        $("#actionButtons").show(); // ???
        $("#btnMeta").show();
        
        // 버튼 활성화
        $('.mdbtn a').css("background", '#4174b8');
        
        graphDataClear(node, 2);
        
        // 메타데이터 로드를 위한 값 세팅
        selectMetaInfo = {};
	    selectMetaInfo.code = node['sysCode'];
	    selectMetaInfo.codeType = 'system'; 
	}
	// 데이터베이스 선택시
	else if(node['group'].indexOf('database') > -1) {
		var idx = 0;
		
        $("#selectedNode").text(node.label);
        $("#orgLabel").hide();
        $("#actionButtons").show();
        $("#btnMeta").show();

        $('.mdbtn a').css("background", '#4174b8');
        
        nodeName = node.label;
        menuName[2] = node.label;
        var mName = printMenu(currentMenu, menuName, 2);
        $('.mnli_01').html("<span>" + currentMenu + "</span>" + mName);
        
        graphDataClear(node, 3);
        
 		 if(databaseIndex != 0 && node.id != databaseIndex) {
			 delete dataLoadEndedByIndex[databaseIndex];
			 databaseIndex = node.id;
		 } else {
			 databaseIndex = node.id;
		 }
        
        // 메타데이터 로드를 위한 값 세팅
        selectMetaInfo = {};
	    selectMetaInfo.code = node['sysCode'];
	    selectMetaInfo.codeType = 'system';
	}
	// 테이블 선택시
	else if(node['group'].indexOf('table') > -1) {
        $("#selectedNode").text(node.label);
        $("#orgLabel").hide();
        $("#actionButtons").show();                
        $("#btnMeta").show();
        
        $('.mdbtn a').css("background", '#4174b8');
        
        nodeName = node.label;
        menuName[3] = node.label;	    
        var mName = printMenu(currentMenu, menuName, 3);
        $('.mnli_01').html("<span>" + currentMenu + "</span>" + mName);
	    
        selectMetaInfo = {};
        selectMetaInfo.code = node['tableCode'];
        selectMetaInfo.codeType = 'table';
	}
	
}

function printMenu(currentMenu, menuNames, depth) {
	var mName = '';
	var i = 0;	
	
	if(currentMenu.indexOf("분류체계") == 0) {
		i = 1;
	} 

	for(i; i<=depth; i++) {
		if(i == depth) {
			mName += "<span>"+menuNames[i]+"</span>";
		} else {		
			mName +="<span>"+menuNames[i]+"</span>";
		}
	}
	
	return mName;
	
}

function deselectNode() {
	currentTxnm = "";
	selectMetaInfo = null;

    $("#selectedNode").text("");
    $("#orgLabel").hide();
    $("#actionButtons").hide();
    $("#btnMeta").hide();
    
	$('.mdbtn a').css("background", '#3876bb');
	$('.mnli_01').html("<span>" + currentMenu + "</span>");
	

}

//-----------------------------------------------------------------------------
// 검색 버튼 클릭 시
function dataSearch() {
	
	var searchKeyword = $('#searchDataList').val();
	// $('.srbox').empty();
	
    $.ajax({
    	url: "/bdp/dmm/search.do",
    	type: "POST",
    	async: false,
    	data: {keyword: searchKeyword},
    	success: function(data) {
    		searchNodeData = data;    		
    		if($.trim(searchKeyword) == "") {
    			searchKeyword = '전체';
    		}    		
   			var output = templateSearchData.render(data);   			
    		
   			$('#popch2').html(output).promise().done(function() {
   				$('#popch2').modal({closable: false, transition: 'scale', allowMultiple: true}).modal('show');
   				$('#popch2 h3').text("'" + searchKeyword + "' 키워드 관련 데이터 검색결과");

   				$('.srbox').find('p.sr_con').find('.search_text').each(function(i, e) {
   					var spanText = $(this).text();
   					$(this).html(spanText.replace(searchKeyword, '<span class=\"highlight\">'+searchKeyword+'</span>'));   
   				});
   				
   				$('.link').click(function() {   					
   					if($(this).attr('href') == '') {
   	   					$(this).removeAttr('target');   					
   						alert("등록된 샘플데이터 페이지가 없습니다.");
   	  					return false;
   	  					
   					}
   					
   				});
   					
   			});    	
    		
    	},
    	error: function() {
    		alert("연결 에러");
    	}
    });
}

function paging(page, pagingType) {
	
	var pagingOption = new Object();
	
	if(pagingType == "system") {
		searchSystemPage = page;
	} else {
		searchTablePage = page;
	}
	
	var searchKeyword = $('#searchDataList').val();
	if($.trim(searchKeyword) == "") {
		searchKeyword = '전체';
	}    
	
	pagingOption.searchSystemPage = searchSystemPage;
	pagingOption.searchTablePage = searchTablePage;
	pagingOption.pagingType = pagingType;
	pagingOption.keyword = $('#searchDataList').val();
	
    $.ajax({
    	url: "/bdp/dmm/paging.do",
    	type: "POST",
    	data: pagingOption,
    	async: false,
    	success: function(data) {
    		searchNodeData = data;
   			var output = templateSearchData.render(data);
   			if(pagingType == 'system') {
   				var offset = $('#sys_paging').offset();   				
   				$('#popch2').html(output).promise().done(function() {
   					$('.srbox').animate({scrollTop: 0}, 0);
   				});
   				
   			} else {
   				var offset = $('#table_paging').offset();   				
   				$('#popch2').html(output).promise().done(function() {
   					$('.srbox').animate({scrollTop: 741}, 0);
   				});
   			}
   			
   			$('.srbox').find('p.sr_con').find('.search_text').each(function(i, e) {
   				var spanText = $(this).text();
   				$(this).html(spanText.replace(searchKeyword, '<span class=\"highlight\">'+searchKeyword+'</span>'));   
   			});
   			
   			$('#popch2 h3').text("'" + searchKeyword + "' 키워드 관련 데이터 검색결과");
   			
			$('.link').click(function() {   					
   				if($(this).attr('href') == '') {
   	   				$(this).removeAttr('target');   					
   					alert("등록된 샘플데이터 페이지가 없습니다.");
   	  				return false;   	  				
   				}   				
   			});
   			
    	},
    	error: function() {
    		alert("연결 에러");
    	}
    });	
}


function dbLoadMetaData(dbNm, sysCode) {
	
	var params = "dbNm="+dbNm+"&sysCode="+sysCode;
	var resultDbMetaData = '';
	
	$.ajax({
		url: "/bdp/dmm/dbMetadata.do",
		type: "POST",
		data: params,
		async: false,
		success: function(data) {
			resultDbMetaData = data;
		}
		
	});
	
	return resultDbMetaData;
}
//-----------------------------------------------------------------------------
// 메타데이터 팝업 호출
function loadMetaData(targetNode) {
	if(selectMetaInfo == null) return;
	
	console.log("loadMetaData");
	
	$.ajax({
		url: "/bdp/dmm/metadata.do",
		type: "POST",
		data: selectMetaInfo,
		async: false,
		success: function(data) {
			if(selectMetaInfo.codeType == 'system') {
				console.log(data);
				
				var dbInfo;
				var metadata = data.sysMetaInfo;			
				if(selDatabase.length > 0) {
					dbInfo = dbLoadMetaData(selDatabase, data.sysMetaInfo.syslstCode);
					
					if(dbInfo.dbMetaInfo != null) {
					
						metadata.logical = JSON.parse(dbInfo.dbMetaInfo.metaJson).logical;
						metadata.physical = JSON.parse(dbInfo.dbMetaInfo.metaJson).physical;
						metadata.description = JSON.parse(dbInfo.dbMetaInfo.metaJson).description;
					}
				}
       			var output = templateSystem.render(metadata);       			

       			$('#popch').html(output).promise().done(function() {
       				$('#popch').modal({closable: false, transition: 'scale', allowMultiple: true}).modal('show');    				
           	        $('#lbtn a').attr("href", "./erdImg.do?syslst="+metadata.syslstCode+'&modelType=l');
           	        $('#pbtn a').attr("href", "./erdImg.do?syslst="+metadata.syslstCode+'&modelType=p');
           	        
           	        if(metadata.publicDataLink != null) {
           	        	$('.sampleData > a').attr("href", metadata.publicDataLink);
           	        } else {
           	        	$('.sampleData > a').attr("href", "#").removeAttr("target");
           	        }
           	        
           	        $('.sampleData > a').click(function() {
           	        	var href = $(this).attr("href");
           	        	if(href == "#") {
           	        		alert("등록된 샘플데이터 사이트가 없습니다.");
           	        	}
           	        });
           	        
        			$('#tbl_url a').text(metadata.publicDataLink);
        			$('#tbl_url a').attr("href", metadata.publicDataLink);
        			
        			if(targetNode != null) {        				
        				
	        			if(typeof targetNode.group !== "undefined") {        				
		        	    		if(targetNode.group.indexOf("system") > -1) {        	    		
		        	       	        $('#popch > h3').text("정보시스템 메타데이터");
		        	       	        $('.db1').hide();
		        	    		} else if(targetNode.group.indexOf("database") > -1){
		        	       	        $('#popch > h3').text("데이터베이스 메타데이터");
		        	       	     	$('.db1').show();
		        	    		}
		          				$('#title').text(targetNode.label);
		          				
		        			} else if (typeof targetNode.type !== "undefined") {
		        	    		if(targetNode.type.indexOf("infoSystem") > -1) {        	    		
		        	       	        $('#popch > h3').text("정보시스템 메타데이터");    
		        	       	     	$('.db1').hide();
		        	    		} else if(targetNode.type.indexOf("database") > -1){
		        	       	        $('#popch > h3').text("데이터베이스 메타데이터");
		        	       	     	$('.db1').show();		        	       	        
		        	    		}
		        	    		$('#title').text(targetNode.name);
		        			}
        			}
       			});       	        
				
			}
			else if(selectMetaInfo.codeType == 'table') {
				var metadata = data.tableMetaInfo;				
	   	        var output = templateTable.render(metadata);
	   	        
       	        $('#popch4').html(output).promise().done(function() {
       				$('#popch4').modal({closable: false, transition: 'scale', allowMultiple: true}).modal('show');
       				$('#lbtn a').attr("href", "./erdImg.do?syslst="+metadata.syslstCode+'&modelType=l');
           	        $('#pbtn a').attr("href", "./erdImg.do?syslst="+metadata.syslstCode+'&modelType=p');
           	        
           	        if(metadata.publicDataLink != null) {
           	        	$('.sampleData > a').attr("href", metadata.publicDataLink);
           	        } else {
           	        	$('.sampleData > a').attr("href", "#").removeAttr("target");
           	        }
           	        
           	        $('.sampleData > a').click(function() {
           	        	var href = $(this).attr("href");
           	        	if(href == "#") {
           	        		alert("등록된 샘플데이터 사이트가 없습니다.");
           	        	}
           	        });
           	        
        			$('#tbl_url a').text(metadata.publicDataLink);
        			$('#tbl_url a').attr("href", metadata.publicDataLink);
        	        
       			}); 
			}
		},
   	 	error: function() {
   	 		alert("연결 에러");
		}
   	 });
}

// 검색팝업에서 메타데이터 팝업 호출
function loadMetaDataFromSearch(code, codeType) {
    selectMetaInfo = {};
    selectMetaInfo.code = code;
    selectMetaInfo.codeType = codeType;

    loadMetaData(null);
}

function nullChk(x) {
	if(x === null)return ' ';
	return x;
}

function numberWithCommas(x) {
	if(x === null) return ' ';
	if(x.march('미공개')) return x;
	
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function popupClose(o) {
	$(o).parent().parent().modal("hide");
	event.preventDefault();
	
	return false;
}

function initBold() {
	$('.one li').css('font-weight', 'bold');
	$('.two li').css('font-weight', 'normal');
	$('.three li').css('font-weight', 'normal');
	$('.four li').css('font-weight', 'normal');
	$('.five li').css('font-weight', 'normal');	
}

function getUlHeight() {
	var heights = [];
	var maxHeight = 0;
	heights[0] = $('.two').height();
	heights[1] = $('.three').height();
	heights[2] = $('.four').height();
	heights[3] = $('.five').height();
	maxHeight = heights[0];

	for(var i=1; i < heights.length; i++) {
		if(heights[i] > maxHeight) {
			maxHeight = heights[i];
		}
	}
	
	return maxHeight;
	
}

function neighbourhoodHighlight(params, _dNode) {
    // if something is selected:
    var allNodes = _dNode.get({returnType:"Object"});	
    
    if (params.nodes.length > 0) {
      highlightActive = true;
        
      var i,j;
      var selectedNode = params.nodes[0];
      var selectNodeInfo = dataNodes.get(params.nodes[0]);
      var degrees = 2;    
      
      // mark all nodes as hard to read.
      for (var nodeId in allNodes) {    	
        allNodes[nodeId].color = 'rgba(200,200,200,0.2)';
        var gName = allNodes[nodeId].group;        
        if(isFdmorgMode == false) {
        	allNodes[nodeId].image = opacityIcon[gName];
			allNodes[nodeId].font = {
				color: '#dcdcdc'		
			}
        } else {	  	

        	allNodes[nodeId].image = opacityIcon[gName];
	       	 if(allNodes[nodeId].group.indexOf("system") > -1) {	        	 
	  	        //allNodes[nodeId].size = 180;
	       	 } else if(allNodes[nodeId].group.indexOf("database") > -1) {
	       		allNodes[nodeId].size = 110;
	       	 } else if(allNodes[nodeId].group.indexOf("table") > -1) {
	       		allNodes[nodeId].size = 100;        		 
	       	 }
			allNodes[nodeId].font = {
				size: 103,
				color: '#dcdcdc'		
			}
        }
      }
      
      var connectedNodes = visNetwork.getConnectedNodes(selectedNode);
      var allConnectedNodes = [];

      // get the second degree nodes
      for (i = 1; i < degrees; i++) {
        for (j = 0; j < connectedNodes.length; j++) {
          allConnectedNodes = allConnectedNodes.concat(visNetwork.getConnectedNodes(connectedNodes[j]));
        }
      }

      for (i = 0; i < connectedNodes.length; i++) {
        allNodes[connectedNodes[i]].color = undefined;  
        
        var gName = allNodes[connectedNodes[i]].group;
        if(isFdmorgMode == false) {
        	allNodes[connectedNodes[i]].image = icon[gName];
			allNodes[connectedNodes[i]].font = {
					color: '#000000'		
			}
        } else {
        	allNodes[connectedNodes[i]].image = icon[gName];
			allNodes[connectedNodes[i]].font = {
	 					size: 103,
						color: '#000000'		
			}

        	 if(allNodes[connectedNodes[i]].group.indexOf("system") > -1) {	        	 
	         	 //allNodes[connectedNodes[i]].size = 180;
        	 } else if(allNodes[connectedNodes[i]].group.indexOf("database") > -1) {
        		 allNodes[connectedNodes[i]].size = 110;
        	 } else if(allNodes[connectedNodes[i]].group.indexOf("table") > -1) {
        		 allNodes[connectedNodes[i]].size = 100;        		 
        	 }
        }
        
       
        if (allNodes[connectedNodes[i]].hiddenLabel !== undefined) {
          allNodes[connectedNodes[i]].label = allNodes[connectedNodes[i]].hiddenLabel;
          allNodes[connectedNodes[i]].hiddenLabel = undefined;
        }
      }
      
      // the main node gets its own color and its label back.
      var gName = allNodes[selectedNode].group;
      allNodes[selectedNode].color = '#888888';
      allNodes[selectedNode].image = icon[gName];
      
      if(isFdmorgMode == true) {
     	 if(allNodes[selectedNode].group.indexOf("system") > -1) {	        	 
	        //allNodes[selectedNode].size = 180;
     	 } else if(allNodes[selectedNode].group.indexOf("database") > -1) {
     		allNodes[selectedNode].size = 110;
     	 } else if(allNodes[selectedNode].group.indexOf("table") > -1) {
     		allNodes[selectedNode].size = 100;        		 
     	 }
    	  
		  allNodes[selectedNode].font = {
			size: 103,
			color: '#000000'		
		  };
      } else {
		  allNodes[selectedNode].font = {
				color: '#000000'		
		  };
      }
          
    } else if (highlightActive === true) {
      // reset all nodes
      for (var nodeId in allNodes) {
        var gName = allNodes[nodeId].group;
        allNodes[nodeId].color = undefined;
        allNodes[nodeId].image = icon[gName];
                
        if(isFdmorgMode == true) {
        	 if(allNodes[nodeId].group.indexOf("system") > -1) {	        	 
     	        //allNodes[nodeId].size = 180;
          	 } else if(allNodes[nodeId].group.indexOf("database") > -1) {
          		allNodes[nodeId].size = 110;
          	 } else if(allNodes[nodeId].group.indexOf("table") > -1) {
          		allNodes[nodeId].size = 100;        		 
          	 }
	        allNodes[nodeId].font = {
		        size: 103,
				color: '#000000'		
			}     
        } else {        	
	        allNodes[nodeId].font = {
				color: '#000000'		
			}  
        }
      }
      highlightActive = false;
    }
    
    // transform the object into an array
    var updateArray = [];
    for (nodeId in allNodes) {
      if (allNodes.hasOwnProperty(nodeId)) {
    	 var coodi = visNetwork.getPositions(nodeId);
		allNodes[nodeId].x = coodi.x;
		allNodes[nodeId].y = coodi.y;
        updateArray.push(allNodes[nodeId]);
      }
    }
    
    dataNodes.update(updateArray);
    
  }
  
  function showSysInfoMap(sysCode, fdmorgCode) {
	 openTreeMap();
	 saveClickCodeVal[0] = fdmorgCode;
	 saveClickCodeVal[1] = sysCode;
	 saveClickCodeVal[2] = "";
	 
	 makeTreeMap('three');
	 $('.modal').modal('hide');

  }
  
  function showTableInfoMap(tableCode) {
	
	  openTreeMap();
	  
	  var tableInfoList = searchNodeData.searchTableInfo;
	  for (var i=0; i<tableInfoList.length; i++) {
		  if(tableInfoList[i].fdmsTableIdx == tableCode) {
				saveClickCodeVal[0] = tableInfoList[i].fdmorgCode;
				saveClickCodeVal[1] = tableInfoList[i].syslstCode;
				saveClickCodeVal[2] = tableInfoList[i].fdmsDblistCode;
				saveClickCodeVal[3] = tableInfoList[i].fdmsTableIdx;
				break;
		  }
	 }
	  
	 makeTreeMap('five');
	 $('.modal').modal('hide');
  }
  
</script>
