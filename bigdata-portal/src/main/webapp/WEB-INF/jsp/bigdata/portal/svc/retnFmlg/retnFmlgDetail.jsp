<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
귀농의사결정 지원 - 상세보기
2020.02.13
--%>

<head>
	<title>데이터 기반 귀농 지역·품목 안내 서비스</title>
</head>
	<link rel="stylesheet" href="<c:url value='/css/bigdata/retnFmlg/info/layout.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/bigdata/retnFmlg/detail/layout.css'/>">

    <script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>

	<!-- <script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.min.js"></script> -->
	<script type="text/javascript"	src="/js/bigdata/portal/svc/Chart.bundle.min.js"></script>

    <script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnFmlgCommon.js'/>" defer></script>
    <script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnDetailChart.js'/>" defer></script>
    <script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnFmlgDetail.js'/>" defer></script>

    <script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/similrAtmnentObj.js'/>" defer></script>
   	<div id="skipnavigation">
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult.html#main_content">본문으로 바로가기</a>
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult.html#menu_top">메뉴로 바로가기</a>
	</div>

<!-- 20201027 jhok 로딩바출력할 div -->
<div id = "loading" class="loading">
	<img src="/images/bigdata/svc/loadingbar.gif"/>
	<div class="loadingSpan"><font color="white">입력하신 정보와 유사한 귀농인 정보를 검색중입니다. 잠시만 기다려주세요.</font></div>
</div>
 
<div class="wrap">
<!-- 좌측 GNB 영역 -->
        <header>
        	<div class="gnb">
	    		<h1><a href="#">bdp</a><span class="hide">빅데이터포털</span></h1>
<!-- 	    		<ul class="nav"> -->
<!-- 	    			<li><a href="#"><img src="" alt="" /></a></li> -->
<!-- 	    			<li><a href="#"><img src="" alt="" /></a></li> -->
<!-- 	    			<li><a href="#"><img src="" alt="" /></a></li> -->
<!-- 	    		</ul> -->
	    	</div><!-- //gnb -->
        </header>


<!-- 중앙 컨텐츠 영역 -->
        <section class="cntWrap result" id="main_content">


        	<!-- 상단고정 -->
        	<div class="description">
	    		<h2><strong>데이터 기반</strong> 귀농 지역·품목 안내 서비스</h2>
	            <p class="web_ex">
	            	<strong>데이터 기반 귀농 지역·품목 안내 서비스에 오신것을 환영합니다.</strong><br>
	            	<!-- MK 상세 설명 제거 -->
	            	<span>&nbsp;</span>
	            	<button id="showDataSrc" type="button" class="showDataSrc layPopBtn w20p" data-target="dataSource" data-addrPrefix="src" ><div class="data"><em class="data_source">?</em> 데이터별출처</div></button>
	            	<!-- <span>입력하신 정보를 기반으로 빅데이터 분석 및 귀농지원 알고리즘 적용을 통해 적합한 귀농정보를 제공해 드립니다.</span> -->
	            </p>
	    	</div>

	    	<div class="menuTop" id="menu_top">
	    		<ul>
	    			<li id="menu_topNav" style="display:none;"><a href=""><span>더미(정렬할때 사용)</span></a></li>
	    			<li id="result02Nav"><a href="#result02" class="scrollMove"><span id="resultColor02" class="tabon">지역정보</span></a></li>
	    			<li id="result04Nav"><a href="#result04" class="scrollMove"><span id="resultColor04" >품목정보</span></a></li>
	    			<li id="result05Nav"><a href="#result05" class="scrollMove"><span id="resultColor05">지원정책</span></a></li>
	    			<li id="result03Nav"><a href="#result03" class="scrollMove"><span id="resultColor03">정주여건</span></a></li>
	    			<li id="result01Nav"><a href="#result01" class="scrollMove"><span id="resultColor01">귀농인정보</span></a></li>
	    			<li id="result06Nav"><a href="#result06" class="scrollMove"><span id="resultColor06">품목분석</span></a></li>
	    		</ul>
	    	</div>
	    	<!-- //상단고정 -->

	    	<div class="resultBox" id="result02">
	        	<h3>귀농 지역 정보 안내</h3>
	        	<div class="row mt20">
        			<div class="areaList w65p">
        				<p>귀하와 적합한 <strong>귀농 지역</strong>입니다. <br>아래의 <strong>지역명을 클릭</strong>하시면 <strong>지역 귀농교육 및 지원정책</strong>과 지역 <strong>정주 여건 정보, 귀농 지역 품목 정보</strong>를 비롯하여 <strong>유사 귀농인 정보</strong>를 보여드립니다. </p>
						<!--
							선택된 버튼에 class = on
						-->
        				 <button type="button" class="recomendArea num01 on">
        					<span class="num">1</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button>
        				<button type="button" class="recomendArea num02">
        					<span class="num">2</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button>
        				<button type="button" class="recomendArea num03">
        					<span class="num">3</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>   
        				</button> 
        				<div id="moreArea" style="display:block;cursor:hand;">+지역더보기</div>
        				<div id="moreInfo" style="display:none;">
        				<button type="button" class="recomendArea num04">
        					<span class="num">4</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button>
        				<button type="button" class="recomendArea num05">
        					<span class="num">5</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button> 
        				<button type="button" class="recomendArea num06">
        					<span class="num">6</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button> 
        				<button type="button" class="recomendArea num07">
        					<span class="num">7</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button> 
        				<button type="button" class="recomendArea num08">
        					<span class="num">8</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button> 
        				<button type="button" class="recomendArea num09">
        					<span class="num">9</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button> 
        				<button type="button" class="recomendArea num10">
        					<span class="num">10</span>
        					<span class="txt"></span>
        					<span class="btn">정보 보기</span>
        				</button> 
        				<div id="moreAreaClose" style="display:block;cursor:hand;">-더보기닫기</div>
        				</div>
        			</div>
        			<div class="w32p fr">
        				<div class="diagram">
							<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_z_0_0" ></canvas></div>
							<!--<img src="/images/bigdata/svc/retnFmlg/smpl_img01.png" alt="다이어그램" style="margin:30px 0 0 30px;display:inline-block;"> -->
        				</div>
        			</div>
        		</div>
	        </div><!-- //resultBox result02 -->

	        <div class="resultBox" id="result04">
	        	<h3>귀농 지역 품목 정보 - </h3>
	        	<div class="tabWrap">
		        	<ul class="fixesCtvt tabTitle itemList">
            			<!--
							선택된 버튼에 class = on
						-->
                		<li class="tablinks on">
	        				<button type="button"><span></span></button>
                		</li>
                		<li class="tablinks">
	        				<button type="button"><span></span></button>
                		</li>
                		<li class="tablinks">
	        				<button type="button"><span></span></button>
                		</li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
            				<div class="tabWrap has4Tab">
			                	<ul class="tabTitle">
			                		<li class="tablinks on"><button type="button"><span>재배면적추이</span></button></li>
			                		<li class="tablinks"><button type="button"><span>도매시장 경락 가격</span></button></li>
			                		<li class="tablinks"><button type="button"><span>연령대별 재배농가현황</span></button></li>
			                		<!-- <li class="tablinks"><button type="button"><span>생산/유통 안정성</span></button></li> -->
			                		<li class="tablinks"><button type="button"><span>품목부가정보</span></button></li>
			                	</ul>
			                	<div class="tabContWrap">
			                		<div class="tabcontent on">
			            				<div class="h4wrap">
				            				<h4 class="hasUnit"><strong>재배면적추이</strong>
				                				<span class="unit">기준년도: 2015~2019년 &nbsp;&nbsp;(단위 : 3.3m<em>2</em>)</span>
				                				<p class="help right">
					            					<!-- <em>?</em> -->
					            					<span>해당 품목의 시군내 재배면적 추이(전체 농업인, 초기 영농인)</span>
					            				</p>
				                			</h4>
				            			</div>
<!-- 				            			<div class="graphEx mt_20"> -->
<!-- 			            					<span>전체</span> -->
<!-- 			            					<span>초기영농인</span> -->
<!-- 			            				</div> -->
				            			<div class="graph">
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_b_0_0_0" ></canvas></div>
			                			</div>
			                		</div>
			                		<div class="tabcontent">
				                		<div class="h4wrap">
				            				<h4 class="hasUnit"><strong>도매시장 경락가격 현황</strong>
				                				<span class="unit">기준년도: 2018~2020년 &nbsp;&nbsp;(단위 : 원/kg)</span>
				                				<p class="help right">
					            					<!-- <em>?</em> -->
					            					<span>도매시장 경락가격 추이 (해당품목 전국 평균, 출하시군 평균 가격)</span>
					            				</p>
				                			</h4>
				            			</div>
<!-- 				            			<div class="graphEx mt_20"> -->
<!-- 			            					<span>전체평균</span> -->
<!-- 			            					<span class="selectSigngu">순창군</span> -->
<!-- 			            				</div> -->
				            			<div class="graph">
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_b_0_1_0" ></canvas></div>
			                			</div>
			                		</div>
			                		<div class="tabcontent">
			                		  	<div class="row">
			                				<div class="w50p">
			                					<div class="h4wrap">
						            				<h4 class="hasUnit"><strong>연령대별 재배농가 현황(전체)</strong>
							            				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;</span>
						                				<p class="help lower">
							            					<!-- <em>?</em> -->
							            					<span>연령대(40대 미만, 40대, 50대, 60대 이상) 재배농가 비중(해당시군, 해당품목 전체 농가)</span>
							            				</p>
						                			</h4>
						            			</div>
						            			<div class="graph">
					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_b_0_2_0" ></canvas></div>
					                			</div>
			                				</div>
			                				<div class="w50p">
			                					<div class="h4wrap">
						            				<h4 class="hasUnit"><strong>연령대별 재배농가 현황(초기 영농인)</strong>
						                				<p class="help lower exception01">
							            					<!-- <em>?</em> -->
							            					<span>영농기간이 5년 이내인 초기영농인의 연령대별 구성현황 정보</span>
							            				</p>
						                			</h4>
						            			</div>
						            			<div class="graph">
					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_b_0_2_1" ></canvas></div>
					                			</div>
			                				</div>
			                			</div>
			                		</div>
			                		<!-- MK 생산 유통 안정성 탭 삭제 -->
			                		<%-- <div class="tabcontent">
				                		<div class="row">
			                				<div class="w34p">
			                					<div class="h4wrap">
							            			<h4 class="hasUnit"><strong>현재 가격 위치 지수</strong>
								            			<span class="unit">기준년도: 2016~2019년 &nbsp;&nbsp;</span>
								            			<p class="help lower">
							            					<!-- <em>?</em> -->
							            					<span>시장거래 가격에서의 현재 가격 위치</span>
							            				</p>
							            			</h4>
												</div>
			                				</div>
			                				<div class="w33p">
			                					<div class="h4wrap">
							            			<h4 class="hasUnit"><strong>가격변동성 지수</strong>
								            			<span class="unit">기준년도: 2016~2019년 &nbsp;&nbsp;</span>
								            			<p class="help lower">
								            					<!-- <em>?</em> -->
								            					<span>낮을수록 가격변동 폭이 안정적인 품목</span>
								            				</p>
							            			</h4>
						            			</div>
			                				</div>
			                				<div class="w33p">
			                					<div class="h4wrap">
							            			<h4 class="hasUnit"><strong>가격추세 지수</strong>
								            			<span class="unit">기준년도: 2016~2019년 &nbsp;&nbsp;</span>
								            			<p class="help lower">
								            					<!-- <em>?</em> -->
								            					<span>높을수록 가격이 상승하는 추세인 품목</span>
								            				</p>
							            			</h4>
						            			</div>
			                				</div>
			                			</div>
				            			<div class="graph">
				                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="sc_b_0_3_0" ></canvas></div>
				                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="sc_b_0_3_1" ></canvas></div>
				                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="sc_b_0_3_2" ></canvas></div>
			                			</div>
				                		<div>
			                			</div>
			                		</div> --%>
			                		<div class="tabcontent">
				                		<div class="row">
					                			<div class="w48p">
					                				<h4 class="hasUnit">
					                					<strong>소득정보</strong>
						                				<span class="unit">기준년도: 2019년 &nbsp;&nbsp;(단위 : 원/10a)</span>
						                			</h4>
						            				<div class="gridWrap">
										        		<table class="grid typeB">
										        			<caption>품목 소득정보</caption>
										        			<colgroup>
										        				<col width="20%">
										        				<col width="20%">
										        				<col width="*">
										        				<col width="20%">
										        				<col width="20%">
											        		</colgroup>
										        			<thead>
										        				<tr>
										        					<th scope="col">총수입</th>
										        					<th scope="col">경영비</th>
										        					<th scope="col">소득금액</th>
										        					<th scope="col">소득율(%)</th>
										        					<th scope="col">kg당 수취단가</th>
										        				</tr>
										        			</thead>
										        			<tbody>
										        				<tr>
										        					<th class="txtC">2,297,38</th>
										        					<td class="txtC">1,469,940</td>
										        					<td class="txtC">824,444</td>
										        					<td class="txtC">36.0</td>
										        					<td class="txtC">11,425</td>
										        				</tr>
										        			</tbody>
										        		</table>
										        	</div><!-- //gridWrap -->
					                			</div>
					                			<div class="w48p fr">
					                				<h4 class="hasUnit">
					                					<strong>소매가격정보</strong>
						                				<span class="unit">기준년도: 실시간 &nbsp;&nbsp;(단위 : 원/kg)</span>
						                			</h4>
						            				<div class="gridWrap">
										        		<table class="grid typeB">
										        			<caption>품목 소매가격정보</caption>
										        			<colgroup>
										        				<col width="*%">
										        				<col width="12%">
										        				<col width="12%">
										        				<col width="12%">
										        				<col width="12%">
										        				<col width="12%">
										        				<col width="12%">
											        		</colgroup>
										        			<thead>
										        				<tr>
										        					<th scope="col">구분</th>
										        					<th scope="col">당일</th>
										        					<th scope="col">3일전</th>
										        					<th scope="col">7일전</th>
										        					<th scope="col">1개월전</th>
										        					<th scope="col">1년전</th>
										        					<th scope="col">평년</th>
										        				</tr>
										        			</thead>
										        			<tbody>
										        				<tr>
										        					<th scope="row">평균</th>
										        					<td class="txtC">253</td>
										        					<td class="txtC">253</td>
										        					<td class="txtC">243</td>
										        					<td class="txtC">243</td>
										        					<td class="txtC">238</td>
										        					<td class="txtC">417</td>
										        				</tr>
										        			</tbody>
										        		</table>
										        	</div><!-- //gridWrap -->
					                			</div>
					                		</div>
			                		</div>
			                	</div><!-- //tabContWrap -->
			                </div><!-- //tabWrap -->
                		</div><!-- //배 -->
                	</div><!--  //tabContWrap -->
                </div><!-- //tabWrap - 배포도양파 -->
	        </div><!-- //resultBox result04 -->

	        <div class="resultBox spprtPolicy" id="result05">
	        	<h3>지역 귀농교육 및 지원정책 - </h3>
	        	<p class="mt20">귀하와 적합한 <strong>귀농 지역</strong> 및 요건에 맞는 <br><strong>지역 귀농교육 및 지원정책 결과를 요약</strong>하여 아래와 같이 알려드립니다. <br>정보를 클릭하시면 정보의 리스트 및 상세정보를 확인하실 수 있습니다.</p>
	        	<div class="row">
		        	<div class="w100p roundBx graphArea">
		        		<h4>귀농지원사업정보</h4>
		        		<ul>
		        			<li style="margin: -6px 0 0 0;padding: 0px;width: 50%;text-align: center;">
		        				<!-- <span>농업교육포털 : <a href="https://agriedu.net" target="_blank">https://agriedu.net</a></span><br/>
		        				<span>귀농귀촌종합센터 교육정보 : <a href="http://www.returnfarm.com" target="_blank">http://www.returnfarm.com</a></span> -->
		        				<a href="https://agriedu.net" target="_blank"><span>농업교육포털 바로가기</span>&nbsp;<img src="/images/bigdata/svc/retnFmlg/foreign_gray.png" alt="" width="13px" height="13px"></a><br/>
		        				<a href="http://www.returnfarm.com" target="_blank"><span>귀농귀촌종합센터 교육정보 바로가기</span>&nbsp;<img src="/images/bigdata/svc/retnFmlg/foreign_gray.png" alt="" width="13px" height="13px"></a>
		        			</li>
		        			<li style="margin: 6px 0 0 0;padding: 0px;width: 50%;">정책 <strong></strong></li>
		        		</ul>
		        	</div>
		        </div>
		        <p class="cmmnt"></p>
		        <button type="button" id="btn_popup_open"><span>정책 상세보기</span></button>
	        </div><!-- //resultBox result05 -->


	        <div class="resultBox" id="result03">
	        	<h3>지역 정주 여건 정보 - </h3>
		    	<div class="tabWrap has8Tab">
                	<ul class="tabTitle">
                	<!-- MK 의료 추가 및 메뉴 순서 변경 -->
                		<li class="tablinks on"><button type="button"><span>농지가격</span></button></li>
                		<li class="tablinks"><button type="button"><span>귀농인</span></button></li>
                		<li class="tablinks"><button type="button"><span>교통</span></button></li>
                		<li class="tablinks"><button type="button"><span>교육</span></button></li>
                		<li class="tablinks"><button type="button"><span>의료</span></button></li>                		
                		<li class="tablinks"><button type="button"><span>편의</span></button></li>
                		<li class="tablinks"><button type="button"><span>문화</span></button></li>
                		<li class="tablinks"><button type="button"><span>유통</span></button></li>
                		<li class="tablinks"><button type="button"><span>부가정보</span></button></li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
                			<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 농지 평균 실거래가격</strong>
		                				<span class="unit">기준년도: 2018~2020년 평균 &nbsp;&nbsp;(단위 : 원/3.3m<em>2</em>)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_0_0" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 농지 평균 실거래가격 비교</strong>
		                				<span class="unit">기준년도: 2018~2020년 평균 &nbsp;&nbsp;(단위 : 원/3.3m<em>2</em>)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_0_1" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                			<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 농지 평균 임대가격</strong>
		                				<span class="unit">기준년도: 2018~2020년 평균 &nbsp;&nbsp;(단위 : 원/3.3m<em>2</em>)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_0_2" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 농지 평균 임대가격 비교</strong>
		                				<span class="unit">기준년도: 2018~2020년 평균 &nbsp;&nbsp;(단위 : 원/3.3m<em>2</em>)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_0_3" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div class="row">
                				<div class="w34p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 연령대별 귀농인</strong>
		                				<span class="unit">기준년도: 2019년 &nbsp;&nbsp;(단위 : 명)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_1_0" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w33p">
                					<h4 class="hasUnit"><strong>귀농인 전출 지역</strong>
		                				<span class="unit">기준년도: 2015~2019년 &nbsp;&nbsp;(단위 : 명)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_1_1" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w33p">
                					<h4 class="hasUnit"><strong>귀농인 재배 품목</strong>
		                				<span class="unit">기준년도: 2015~2019년 (단위 : 명)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_1_2" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                		</div>
                		<!-- MK 의료탭 추가 및 탭 순서 변경 -->
                		<div class="tabcontent">
            				<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 교통시설 접근시간</strong>
		                				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_3_0" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 교통시설 접근성비교</strong>
		                				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_3_1" ></canvas></div>
		                			</div>
                				</div>
                			</div>
            				<div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 교육시설별 접근시간</strong>
                						<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_2_0" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 교육시설 접근성비교</strong>
		                				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_2_1" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                			<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 교육시설 수</strong>
		                				<span class="unit">기준년도: 2020년 &nbsp;&nbsp;(단위 : 개)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_2_2" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 교육시설 비교</strong>
		                				<span class="unit">기준년도: 2020년 &nbsp;&nbsp;(단위 : 개)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_2_3" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                		</div>
                		<!-- MK 의료 탭 추가 -->
                		<div class="tabcontent">
            				<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 의료시설 접근시간</strong>
		                				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_6_0" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 의료시설 접근성비교</strong>
		                				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_6_1" ></canvas></div>
		                			</div>
                				</div>
                			</div>
            				<div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 마트시설 접근시간</strong>
		                				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_4_0" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 마트시설 접근성비교</strong>
		                				<span class="unit">기준년도: 2018년 &nbsp;&nbsp;(단위 : 자가용이용시 소요시간. 분)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_4_1" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                			<div class="row">
                				<div class="w30p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 편의시설 수</strong>
		                				<span class="unit">기준년도: 2019년 &nbsp;&nbsp;(단위 : 개)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_4_2" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 편의시설 비교</strong>
		                				<span class="unit">기준년도: 2019년 &nbsp;&nbsp;(단위 : 개)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_4_3" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div class="row">
                				<div class="w40p">
                					<h4 class="hasUnit"><strong><span class="selectEmd"></span> 문화시설 수</strong>
		                				<span class="unit">기준년도: 2019년 &nbsp;&nbsp;(단위 : 개)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_5_0" ></canvas></div>
		                			</div>
                				</div>
                				<div class="w60p">
                					<h4 class="hasUnit"><strong><span class="selectSigngu"></span> 읍면별 문화시설 수 비교</strong>
		                				<span class="unit">기준년도: 2019년 &nbsp;&nbsp;(단위 : 개)</span>
		                			</h4>
		                			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_a_5_1" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<h4 class="txtC"><strong>농산물산지유통센터 정보</strong><span class="unit">기준년도: 2020년 &nbsp;&nbsp;</span></h4>
            				<div class="distbInfoApc gridWrap">
				        		<table class="grid typeA">
				        			<caption>농산물산지유통센터 정보</caption>
				        			<colgroup>
				        				<col width="8%">
				        				<col width="25%">
				        				<col width="25%">
				        				<col width="*">
					        		</colgroup>
				        			<thead>
				        				<tr>
				        					<th scope="col">No</th>
				        					<th scope="col">사업자명</th>
				        					<th scope="col">취급품목</th>
				        					<th scope="col">주소지</th>
				        				</tr>
				        			</thead>
				        			<tbody>
				        				<tr>
				        					<th scope="row">1</th>
				        					<td class="txtC"></td>
				        					<td class="txtC"></td>
				        					<td class="txtC"></td>
				        				</tr>
				        				<tr>
				        					<th scope="row">2</th>
				        					<td class="txtC"></td>
				        					<td class="txtC"></td>
				        					<td class="txtC"></td>
				        				</tr>
				        			</tbody>
				        		</table>
				        	</div>
				        	<h4 class="txtC"><strong>로컬푸드 직매장 현황</strong><span class="unit">기준년도: 2020년 &nbsp;&nbsp;</span></h4>
            				<div class="distbInfoLocal gridWrap">
				        		<table class="grid typeA">
				        			<caption>로컬푸드 직매장 현황</caption>
				        			<colgroup>
				        				<col width="8%">
				        				<col width="25%">
				        				<col width="45%">
				        				<col width="*">
					        		</colgroup>
				        			<thead>
				        				<tr>
				        					<th scope="col">No</th>
				        					<th scope="col">매장명</th>
				        					<th scope="col">주소지</th>
				        					<th scope="col">전화번호</th>
				        				</tr>
				        			</thead>
				        			<tbody>
				        				<tr>
				        					<th scope="row">1</th>
				        					<td class="txtC"></td>
				        					<td class="txtC"></td>
				        					<td class="txtC"></td>
				        				</tr>
				        			</tbody>
				        		</table>
				        	</div><!-- //gridWrap -->
                		</div>
                		<div class="tabcontent">
                		<!-- MK 빈집정보 삭제 -->
                		<%-- 
            				<h4 class="txtC"><strong>빈집정보</strong><span class="unit">기준년도: 실시간 &nbsp;&nbsp;</span></h4>
            				<div class="uninhbhous gridWrap">
				        		<table class="grid typeA">
				        			<caption>빈집정보</caption>
				        			<colgroup>
				        				<col width="8%">
				        				<col width="20%">
				        				<col width="*">
				        				<col width="20%">
				        				<col width="20%">
					        		</colgroup>
				        			<thead>
				        				<tr>
				        					<th scope="col">No</th>
				        					<th scope="col">등록일</th>
				        					<th scope="col">소재지</th>
				        					<th scope="col">거래유형</th>
				        					<th scope="col">면적<span class="unit">(단위 : 3.3m<em>2</em>)</span></th>
				        				</tr>
				        			</thead>
				        			<tbody>
				        			</tbody>
				        		</table>
				        		<div class="pagingWrap">
<!-- 						        	<ul class="paging2"> -->
<!-- 						        		선택된 li의 class="on" -->
<!-- 						        		<li class="on"><button type="button"><span>1</span></button></li> -->
<!-- 						        		<li><button type="button"><span>2</span></button></li> -->
<!-- 						        		<li><button type="button"><span>3</span></button></li> -->
<!-- 						        	</ul> -->
						        </div>
				        	</div><!-- //gridWrap --> --%>
				        	<h4 class="txtC"><strong>일자리 정보</strong><span class="unit">기준년도: 실시간 &nbsp;&nbsp;</span></h4>
            				<div class="jbhnt gridWrap">
				        		<table class="grid typeA">
				        			<caption>일자리 정보</caption>
				        			<colgroup>
				        				<col width="8%">
				        				<col width="16%">
				        				<col width="16%">
				        				<col width="*">
				        				<col width="12%">
				        				<col width="12%">
					        		</colgroup>
				        			<thead>
				        				<tr>
				        					<th scope="col">No</th>
				        					<th scope="col">등록일</th>
				        					<th scope="col">회사명</th>
				        					<th scope="col">채용공고</th>
				        					<th scope="col">근무조건</th>
				        					<th scope="col">마감일</th>
				        				</tr>
				        			</thead>
				        			<tbody>
				        				<tr>
				        					<th scope="row">1</th>
				        					<td class="txtC">20.02.17</td>
				        					<td class="txtC">(주)농림</td>
				        					<td class="txtC">농림에서 배달기사님을 모십니다.</td>
				        					<td class="txtC">월 180</td>
				        					<td class="txtC">D-10</td>
				        				</tr>
				        				<tr>
				        					<th scope="row">1</th>
				        					<td class="txtC">20.02.17</td>
				        					<td class="txtC">(주)농림</td>
				        					<td class="txtC">농림에서 배달기사님을 모십니다.</td>
				        					<td class="txtC">월 180</td>
				        					<td class="txtC">D-10</td>
				        				</tr>
				        				<tr>
				        					<th scope="row">1</th>
				        					<td class="txtC">20.02.17</td>
				        					<td class="txtC">(주)농림</td>
				        					<td class="txtC">농림에서 배달기사님을 모십니다.</td>
				        					<td class="txtC">월 180</td>
				        					<td class="txtC">D-10</td>
				        				</tr>
				        			</tbody>
				        		</table>
				        		<div class="pagingWrap">
<!-- 						        	<ul class="paging2"> -->
<!-- 						        		선택된 li의 class="on" -->
<!-- 						        		<li class="on"><button type="button"><span>1</span></button></li> -->
<!-- 						        		<li><button type="button"><span>2</span></button></li> -->
<!-- 						        		<li><button type="button"><span>3</span></button></li> -->
<!-- 						        	</ul> -->
						        </div>
				        	</div><!-- //gridWrap -->
				        	<h4 class="txtC"><strong>대안학교 정보</strong><span class="unit">기준년도: 2020년 &nbsp;&nbsp;</span></h4>
            				<div class="altrvSchul gridWrap">
				        		<table class="grid typeA">
				        			<caption>대안학교 정보</caption>
				        			<colgroup>
				        				<col width="8%">
				        				<col width="24%">
				        				<col width="*">
				        				<col width="24%">
					        		</colgroup>
				        			<thead>
				        				<tr>
				        					<th scope="col">No</th>
				        					<th scope="col">학교명</th>
				        					<th scope="col">주소</th>
				        					<th scope="col">분야</th>
				        				</tr>
				        			</thead>
				        			<tbody>
				        			</tbody>
				        		</table>
				        	</div><!-- //gridWrap -->				        	
                		</div>
                	</div>
                </div>
	        </div><!-- //resultBox result03 -->


	        <div class="resultBox" id="result01">
	        	<h3>유사 귀농인 정보</h3>
				<div class="tabWrap has5Tab">
                	<ul class="tabTitle">
                		<li class="tablinks on"><button type="button"><span>귀농인 전입지역 정보</span></button></li>
                		<li class="tablinks"><button type="button"><span class="narrowTxt">유사 귀농인 전입지역 정보</span></button></li>
                		<li class="tablinks"><button type="button"><span>초기영농인 재배품목</span></button></li>
                		<li class="tablinks"><button type="button"><span>유사귀농인 재배품목</span></button></li>
                		<li class="tablinks"><button type="button"><span>지역선택 고려요인  비교</span></button></li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
                			<div class="row">
                				<div class="w30p">
                					<div class="h4wrap">
			            				<h4 class="txtC"><strong>귀농인 전입지역 현황</strong>
			                				<p class="help lower">
				            					<!-- <em>?</em> -->
				            					<span>귀농인 전입지역 정보</span>
				            				</p>
			                			</h4>
			            			</div>
			            			<div class="graph">
			            				<img src="/images/bigdata/svc/retnFmlg/retnFmlgTrnsfrnArea.png" alt="" style="width:200px;margin:35px 0 0 25px;display:inline-block;">
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit">
	                					<strong>귀농인 상위 전입지역</strong>
		                				<span class="unit">기준년도: 2015~2019년 &nbsp;&nbsp;(단위 : 명)</span>
		                			</h4>
			            			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_c_0_1" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div class="row">
                				<div class="w30p">
                					<div class="h4wrap">
			            				<h4 class="txtC">
			            					<strong>유사 귀농인 전입지역 현황</strong>
			                				<p class="help lower">
				            					<!-- <em>?</em> -->
				            					<span>빅데이터 분석을 통해 이용자와 유사한 귀농인이 전입한 지역정보</span>
				            				</p>
			                			</h4>
			            			</div>
			            			<div class="graph">
										<img id="similrTrnsfrnSttus" src="/images/bigdata/svc/retnFmlg/retnFmlgTrnsfrnArea.png" alt="" style="width:200px;margin:35px 0 0 25px;display:inline-block;">
		                			</div>
                				</div>
                				<div class="w70p">
                					<h4 class="hasUnit">
	                					<strong>유사 귀농인 상위 전입지역</strong>
		                				<span class="unit">기준년도: 2015~2019년 &nbsp;&nbsp;(단위 : 명)</span>
		                			</h4>
			            			<div class="graph">
		                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_c_1_0" ></canvas></div>
		                			</div>
                				</div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div class="h4wrap">
	                			<h4 class="hasUnit">
	            					<strong>초기 영농인 상위 10개 재배품목</strong>
	                				<span class="unit">기준년도: 2015~2019년 &nbsp;&nbsp;(단위 : 명)</span>
	                				<p class="help right">
		            					<!-- <em>?</em> -->
		            					<span>영농기간이 5년 이내인 초기영농인의 재배품목 정보</span>
		            				</p>
	                			</h4>
	                		</div>
	            			<div class="graph">
                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_c_2_0" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div class="h4wrap">
	                			<h4 class="hasUnit">
	            					<strong>유사 귀농인 상위 10개 재배품목</strong>
	                				<span class="unit">기준년도: 2015~2019년 &nbsp;&nbsp;(단위 : 명)</span>
	                				<p class="help right">
		            					<!-- <em>?</em> -->
		            					<span>빅데이터 분석을 통해 이용자와 유사한 귀농인이 재배하고 있는 품목에 대한 정보</span>
		            				</p>
	                			</h4>
	                		</div>
	            			<div class="graph">
                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_c_3_0" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div class="row">
                				<div class="w48p">
                					<div class="h4wrap">
			                			<h4 class="txtC">
			            					<strong>유사귀농인 고려항목</strong>
			                				<p class="help lower">
				            					<!-- <em>?</em> -->
				            					<span>빅데이터 분석을 통해 이용자와 유사한 귀농인이 귀농지역 선택시 고려하는 요인</span>
				            				</p>
			                			</h4>
			                		</div>
			                		<div class="cnsdrWght gridWrap">
						        		<table class="grid typeC">
						        			<caption>유사귀농인 고려항목</caption>
						        			<colgroup>
						        				<col width="25%">
						        				<col width="*">
							        		</colgroup>
						        			<tbody>
						        				<tr>
						        					<th scope="row">상위고려사항</th>
						        					<td class="upper">
						        					</td>
						        				</tr>
						        				<tr>
						        					<th scope="row">중위고려사항</th>
						        					<td class="middle">
						        					</td>
						        				</tr>
						        				<tr>
						        					<th scope="row">하위고려사항</th>
						        					<td class="lower">
						        					</td>
						        				</tr>
						        			</tbody>
						        		</table>
						        	</div><!-- //gridWrap -->
                				</div>
                				<div class="w48p fr">
		                			<h4 class="txtC">
		            					<strong>이용자가 선택한 고려항목</strong>
		                			</h4>
			                		<div class="userCnsdrWght gridWrap">
						        		<table class="grid typeC">
						        			<caption>이용자가 선택한 고려항목</caption>
						        			<colgroup>
						        				<col width="25%">
						        				<col width="*">
							        		</colgroup>
						        			<tbody>
						        				<tr>
						        					<th scope="row">상위고려사항</th>
						        					<td class="upper">

						        					</td>
						        				</tr>
						        				<tr>
						        					<th scope="row">중위고려사항</th>
						        					<td class="middle">

						        					</td>
						        				</tr>
						        				<tr>
						        					<th scope="row">하위고려사항</th>
						        					<td class="lower">

						        					</td>
						        				</tr>
						        			</tbody>
						        		</table>
						        	</div><!-- //gridWrap -->
                				</div>
                			</div>
                		</div>
                	</div>
                </div>
	        </div><!-- //resultBox result01 -->
	        <div class="resultBox" id="result06">
	        	<h3>선택지역 및 품목 분석 - 경상북도 상주시</h3>
	        	<div class="tabWrap">
		        	<ul class="tabTitle itemList">
            			<!--
							선택된 버튼에 class = on
						-->
                		<li class="tablinks on">
	        				<button type="button"><span>포도</span></button>
                		</li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
            				<div class="tabWrap has5Tab">
			                	<ul class="tabTitle">
			                		<li class="tablinks on" style="width:34%;"><button type="button"><span>농업인 현황</span></button></li>
			                		<li class="tablinks" style="width:33%;"><button type="button"><span>재배면적추이</span></button></li>
			                		<li class="tablinks" style="width:33%;"><button type="button"><span>초기 영농인 영농현황</span></button></li>
<!-- 			                		<li class="tablinks"><button type="button"><span>평균재배면적추이</span></button></li> -->
<!-- 			                		<li class="tablinks"><button type="button"><span>연령별 평균재배면적</span></button></li> -->
			                	</ul>
			                	<div class="tabContWrap">
			                		<div class="tabcontent on">
										<div class="row">
			                				<div class="w50p">
			                					<h4 class="hasUnit"><strong>재배농업인현황</strong>
					                				<span class="unit">(단위 : 명)</span>
					                			</h4>
					                			<div class="graph">
					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_d_0_0" ></canvas></div>
					                			</div>
			                				</div>
			                				<div class="w50p">
			                					<h4 class="hasUnit"><strong>연령대별 농업인현황</strong>
					                				<span class="unit">(단위 : 명)</span>
					                			</h4>
					                			<div class="graph">
					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_d_0_1" ></canvas></div>
					                			</div>
			                				</div>
		                				</div>
			                		</div>

			                		<!-- 재배면적추이 -->
			                		<div class="tabcontent">
			                			<div class="row">
			                				<div class="w50p">
			                					<h4 class="hasUnit"><strong>재배면적추이</strong>
					                				<span class="unit">(단위 : 3.3m<em>2</em>)</span>
					                			</h4>
					                			<div class="graph">
					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_d_1_0" ></canvas></div>
					                			</div>
			                				</div>
			                				<div class="w50p">
			                					<h4 class="hasUnit"><strong>연령대별 재배면적추이</strong>
					                				<span class="unit">(단위 : 3.3m<em>2</em>)</span>
					                			</h4>
					                			<div class="graph">
					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_d_1_1" ></canvas></div>
					                			</div>
			                				</div>
		                				</div>

			                		</div>

			                		<!-- 초기 영농인 영농현황 -->
			                		<div class="tabcontent">
			                			<div class="row">
				                			<div class="w33p">
				                					<h4 class="hasUnit"><strong>연령대별 귀농인</strong>
						                				<span class="unit">(단위 : 명)</span>
						                			</h4>
						                			<div class="graph">
	<%-- 					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_d_1_0" ></canvas></div> --%>
						                				<div style="width:100%;float:left"><div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>
						                					<canvas id="sc_d_2_0" height="312" width="370" class="chartjs-render-monitor" style="display: block; height: 250px; width: 296px;"></canvas>
						                				</div>
						                			</div>
				                			</div>
				                			<div class="w33p">
				                					<h4 class="hasUnit"><strong>전출지역</strong>
						                				<span class="unit">(단위 : 명)</span>
						                			</h4>
						                			<div class="graph">
	<%-- 					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_d_1_0" ></canvas></div> --%>
						                				<div style="width:100%;float:left"><div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>
						                					<canvas id="sc_d_2_1" height="312" width="368" class="chartjs-render-monitor" style="display: block; height: 250px; width: 295px;"></canvas>
						                				</div>
						                			</div>
				                			</div>
				                			<div class="w33p">
				                					<h4 class="hasUnit"><strong>재배품목</strong>
						                				<span class="unit">(단위 : 명)</span>
						                			</h4>
						                			<div class="graph">
	<%-- 					                				<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="sc_d_1_0" ></canvas></div> --%>
						                				<div style="width:100%;float:left"><div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>
						                					<canvas id="sc_d_2_2" height="312" width="370" class="chartjs-render-monitor" style="display: block; height: 250px; width: 296px;"></canvas>
						                				</div>
						                			</div>
				                			</div>
			                			</div>

<!-- 			                		<h5>평균재배면적추이</h5> -->
			            				<!-- <img src="/images/bigdata/svc/retnFmlg/smpl_img13.png" alt="" style="margin:20px 0 10px 30px;display:inline-block;"> -->
			                		</div>
			                		<div class="tabcontent">
				                		<h5>연령별 평균재배면적</h5>
			                		</div>
			                		<div class="tabcontent">
				                		<h5>초기 영농인 영농현황</h5>
			                		</div>
			                	</div><!-- //tabContWrap -->
			                </div><!-- //tabWrap -->
                		</div><!-- //배 -->
                		<div class="tabcontent">
            				포도
                		</div><!-- //포도 -->
                		<div class="tabcontent">
            				양파
                		</div><!-- //양파 -->
                	</div><!--  //tabContWrap -->
                </div><!-- //tabWrap - 배포도양파 -->
	        </div>

        </section><!-- //cntWrap -->

<jsp:include page="./modal/retnFmlgModal.jsp"/>
<jsp:include page="./modal/retnFmlgPolicyModal.jsp"/>
<jsp:include page="./retnFmlgMyAnswer.jsp"/>



