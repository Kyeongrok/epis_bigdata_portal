<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
귀농의사결정 지원 - 발표용..
2020.02.18
--%>

<script src="<c:url value='/js/vendor/jquery/jquery-2.1.4.min.js'/>"></script>
<script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.min.js"></script>


<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnFmlgDetail.js'/>" defer></script>

<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/yc_a.js'/>" defer></script>
<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/yc_b.js'/>" defer></script>
<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/sc_c.js'/>" defer></script>

<link href="<c:url value='/css/bigdata/retnFmlg/presnatn/layout.css'/>" rel="stylesheet">

	<div id="skipnavigation">
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#main_content">본문으로 바로가기</a>
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#menu_top">메뉴로 바로가기</a>
	</div>
<div class="wrap">

<!-- 좌측 GNB 영역 -->
        <header>
        	<div class="gnb">
	    		<h1><a href="#">bdp</a><span class="hide">빅데이터포털</span></h1>
	    		<ul class="nav">
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    		</ul>
	    	</div><!-- //gnb -->
        </header>


<!-- 중앙 컨텐츠 영역 -->
        <section class="cntWrap result" id="main_content">


        	<!-- 상단고정 -->
        	<div class="description">
	    		<h2><strong>귀농의사결정</strong>지원 서비스</h2>
	            <p class="web_ex">
	            	<strong>귀농의사결정지원 서비스에 오신것을 환영합니다.</strong><br>
	            	<span>입력하신 정보를 기반으로 빅데이터 분석 및 귀농지원 알고리즘 적용을 통해 적합한 귀농정보를 제공해 드립니다.</span>
	            </p>
	    	</div>

	    	<div class="menuTop" id="menu_top">
	    		<ul>
	    			<li class="on"><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result02" class="scrollMove"><span>지역정보</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result05" class="scrollMove"><span>지원정책</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result03" class="scrollMove"><span>정주여건</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result04" class="scrollMove"><span>품목정보</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result01" class="scrollMove"><span>귀농인정보</span></a></li>
	    		</ul>
	    	</div>
	    	<!-- //상단고정 -->

	    	<div class="resultBox" id="result02">
	        	<h3>맞춤 귀농 지역 정보</h3>
	        	<div class="row mt20">
        			<div class="areaList w65p">
        				<p>귀하와 적합한 <strong>맞춤 귀농 지역</strong>입니다. <br>아래의 <strong>지역명을 클릭</strong>하시면역 <strong>지역맞춤 귀농교육 및 지원정책</strong>과 지역 <strong>정주 여건 정보, 귀농 지역 맞춤 품목 정보</strong>를 비롯하여 <strong>유사 귀농인 정보</strong>를 보여드립니다. </p>
						<!--
							선택된 버튼에 class = on
						-->
        				<button type="button" class="num01" onclick="location.href='./test1.do;'">
        					<span class="num">1</span>
        					<span class="txt">전라북도 순창군 순창읍</span>
        					<span class="btn">정보 보기</span>
        				</button>
        				<button type="button" class="num02 on" onclick="location.href='./test1-2.do'">
        					<span class="num">2</span>
        					<span class="txt">경상북도 예천군 호명면</span>
        					<span class="btn">정보 보기</span>
        				</button>
        				<button type="button" class="num03" onclick="location.href='./test1-3.do'">
        					<span class="num">3</span>
        					<span class="txt">제주특별자치도 제주시 한림읍</span>
        					<span class="btn">정보 보기</span>
        				</button>
        			</div>
        			<div class="w32p fr">
        				<div class="diagram">
        					<img src="/images/bigdata/svc/retnFmlg/smpl_img01_2.png" alt="다이어그램" style="margin:30px 0 0 30px;display:inline-block;">
        				</div>
        			</div>
        		</div>
	        </div><!-- //resultBox result02 -->


	        <div class="resultBox support" id="result05">
	        	<h3>지역맞춤 귀농교육 및 지원정책 - 경상북도 예천군</h3>
	        	<p class="mt20">귀하와 적합한 <strong>맞춤 귀농 지역</strong> 및 요건에 맞는 <br><strong>지역맞춤 귀농교육 및 지원정책 결과를 요약</strong>하여 아래와 같이 알려드립니다. <br>정보를 클릭하시면 정보의 리스트 및 상세정보를 확인하실 수 있습니다.</p>
	        	<a id="btn_popup_open" href="#">
		        	<img src="/images/bigdata/svc/retnFmlg/smpl_img02_2.png" alt="다이어그램" style="margin:10px 0 0 0;display:inline-block;">
		        </a>
	        </div><!-- //resultBox result05 -->


	        <div class="resultBox" id="result03">
	        	<h3>지역 정주 여건 정보 - 경상북도 예천군</h3>
		    	<div class="tabWrap has8Tab">
                	<ul class="tabTitle">
                		<li class="tablinks on"><button type="button"><span>농지가격</span></button></li>
                		<li class="tablinks"><button type="button"><span>귀농인</span></button></li>
                		<li class="tablinks"><button type="button"><span>교육</span></button></li>
                		<li class="tablinks"><button type="button"><span>교통</span></button></li>
                		<li class="tablinks"><button type="button"><span>편의</span></button></li>
                		<li class="tablinks"><button type="button"><span>문화</span></button></li>
                		<li class="tablinks"><button type="button"><span>유통</span></button></li>
                		<li class="tablinks"><button type="button"><span>부가정보</span></button></li>
                	</ul>
                	<div class="tabContWrap">
                		<!-- <div class="tabcontent on">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph19_2.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph20.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph21.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph22.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph23.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph24.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph25.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph26.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div> -->
                		<div class="tabcontent on">
                			<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_0_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_0_1" ></canvas></div>
                			</div>
                			<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_0_2" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_0_3" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="yc_a_1_0" ></canvas></div>
	                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="yc_a_1_1" ></canvas></div>
	                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="yc_a_1_2" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_2_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_2_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_3_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_3_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_4_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_4_1" ></canvas></div>
                			</div>
                			<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_4_2" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_4_3" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_5_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_5_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph25.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph26.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                	</div>
                </div>
	        </div><!-- //resultBox result03 -->

	        <div class="resultBox" id="result04">
	        	<h3>귀농 지역 맞춤 품목 정보 - 경상북도 예천군</h3>
	        	<div class="tabWrap">
		        	<ul class="tabTitle itemList">
            			<!--
							선택된 버튼에 class = on
						-->
                		<li class="tablinks on">
	        				<button type="button"><span>포도</span></button>
                		</li>
                		<li class="tablinks">
	        				<button type="button"><span>콩</span></button>
                		</li>
                		<li class="tablinks">
	        				<button type="button"><span>양파</span></button>
                		</li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
            				<div class="tabWrap has5Tab">
			                	<ul class="tabTitle">
			                		<li class="tablinks on"><button type="button"><span>재배면적추이</span></button></li>
			                		<li class="tablinks"><button type="button"><span>도매시장 경락 가격</span></button></li>
			                		<li class="tablinks"><button type="button"><span>연령대별 재배농가현황</span></button></li>
			                		<li class="tablinks"><button type="button"><span>생산/유통 안정성</span></button></li>
			                		<li class="tablinks"><button type="button"><span>품목부가정보</span></button></li>
			                	</ul>
			                	<div class="tabContWrap">
			                		<!-- <div class="tabcontent on">
			            				<img src="/images/bigdata/svc/retnFmlg/smpl_img03_2.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_graph27.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
			                		  	<img src="/images/bigdata/svc/retnFmlg/smpl_graph28.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_graph29.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_img07.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div> -->
			                		<div class="tabcontent on">
			                			<img src="/images/bigdata/svc/retnFmlg/smpl_img03_2.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
			            				<%-- <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_p_0_0" ></canvas></div>
			                			</div> --%>
			                		</div>
			                		<div class="tabcontent">
				                		<div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_p_1_0" ></canvas></div>
			                			</div>
			                		</div>
			                		<div class="tabcontent">
			                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph28.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
			                		  	<%-- <div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_p_2_0" ></canvas></div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_p_2_1" ></canvas></div>
			                			</div> --%>
			                		</div>
			                		<div class="tabcontent">
				                		<div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_p_3_0" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_p_3_1" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_p_3_2" ></canvas></div>
			                			</div>
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_img07.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div>
			                	</div><!-- //tabContWrap -->
			                </div><!-- //tabWrap -->
                		</div><!-- //배 -->
                		<div class="tabcontent">
            				<div class="tabWrap has5Tab">
                                <ul class="tabTitle">
                                    <li class="tablinks on"><button type="button"><span>재배면적추이</span></button></li>
                                    <li class="tablinks"><button type="button"><span>도매시장 경락 가격</span></button></li>
                                    <li class="tablinks"><button type="button"><span>연령대별 재배농가현황</span></button></li>
                                    <li class="tablinks"><button type="button"><span>생산/유통 안정성</span></button></li>
                                    <li class="tablinks"><button type="button"><span>품목부가정보</span></button></li>
                                </ul>
                                <div class="tabContWrap">
                                    <!-- <div class="tabcontent on">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean01.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean02.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean04.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div> -->
                                    <div class="tabcontent on">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_img03.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_k_0_0" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_k_1_0" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_bean03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_k_2_0" ></canvas></div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_k_2_1" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_k_3_0" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_k_3_1" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_k_3_2" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                </div><!-- //tabContWrap -->
                            </div><!-- //tabWrap -->
                		</div><!-- //포도 -->
                		<div class="tabcontent">
            				<div class="tabWrap has5Tab">
                                <ul class="tabTitle">
                                    <li class="tablinks on"><button type="button"><span>재배면적추이</span></button></li>
                                    <li class="tablinks"><button type="button"><span>도매시장 경락 가격</span></button></li>
                                    <li class="tablinks"><button type="button"><span>연령대별 재배농가현황</span></button></li>
                                    <li class="tablinks"><button type="button"><span>생산/유통 안정성</span></button></li>
                                    <li class="tablinks"><button type="button"><span>품목부가정보</span></button></li>
                                </ul>
                                <div class="tabContWrap">
                                    <!-- <div class="tabcontent on">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry01.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry02.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry04.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div> -->
                                    <div class="tabcontent on">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_berry01.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_y_0_0" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_y_1_0" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_berry03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_y_2_0" ></canvas></div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_y_2_1" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_y_3_0" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_y_3_1" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_y_3_2" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                </div><!-- //tabContWrap -->
                            </div><!-- //tabWrap -->
                		</div><!-- //양파 -->
                	</div><!--  //tabContWrap -->
                </div><!-- //tabWrap - 배포도양파 -->
	        </div><!-- //resultBox result04 -->



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
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img04.png" alt="" style="margin:20px 0 10px 20px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img08.png" alt="" style="margin:20px 0 10px 20px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img09.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img10.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img11.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div>
                		<%-- <div class="tabcontent on">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_0" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_2" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_3" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img11.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div> --%>
                	</div>
                </div>
	        </div><!-- //resultBox result01 -->

        </section><!-- //cntWrap -->



<!-- 나의 입력 여건 -->
        <section class="myAnswer">
        	<h3>내가 입력한 여건입니다.<br><span><strong>정보의 제목</strong>을 <strong>클릭</strong>하면 <strong>수정</strong>할 수 있습니다.</span></h3>
        	<!--
				항목의 열고/닫기 클릭 여부에 따라 myBox에 class='on' 있기/없기.
        	-->
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>본인정보</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p">서울특별시 강북구</span>
        		</div>
        		<div class="row">
        			<span class="w48p">남자</span>
        			<span class="w48p">45</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>동거가족</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p mint">배우자</span>
        		</div>
        		<div class="row">
        			<span class="w48p">여자</span>
        			<span class="w48p">41</span>
        		</div>
        		<div class="row">
        			<span class="w100p mint">자녀</span>
        		</div>
        		<div class="row">
        			<span class="w48p">여자</span>
        			<span class="w48p">12</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>희망귀농지역</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<!-- 답변 없는 경우 class="blank" -->
        			<span class="w100p blank"></span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>희망재배품목</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p blank"></span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>고려사항</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<span class="w48p mid blank">교통</span>
        			<span class="w48p low blank">의료</span>
        		</div>
        		<div class="row">
        			<span class="w48p low blank">학교</span>
        			<span class="w48p mid blank">교육</span>
        		</div>
        		<div class="row">
        			<span class="w48p low blank" >상업</span>
        			<span class="w48p hig blank">편의</span>
        		</div>
        		<div class="row">
        			<span class="w48p mid blank">문화</span>
        			<span class="w48p hig blank">귀농지원정책</span>
        		</div>
        		<div class="row">
        			<span class="w48p hig blank">농산물유통여건</span>
        			<span class="w48p low blank">농지실거래가격</span>
        		</div>
        		<div class="row">
        			<span class="w48p hig blank">농지임대가격</span>
        		</div>
        		<div class="exBox">
        			<strong>범례</strong>
        			<div class="row">
        				<span class="w31p hig">상위</span>
	        			<span class="w31p mid">중위</span>
	        			<span class="w30p low">하위</span>
	        		</div>
        		</div>
        	</div>
        	<div class="btnWrap">
        		<button type="button"><span>결과보기</span></button>
        	</div>
        </section><!-- // -->


    </div><!-- //wrap -->
   <jsp:include page="./modal/retnFmlgPolicyModal.jsp"/>

<script>

$(function(){
	tempSrchObj.sido = '경상북도';
	tempSrchObj.sigungu = '예천군';
	tempSrchObj.umd = '호명면';
})
</script>