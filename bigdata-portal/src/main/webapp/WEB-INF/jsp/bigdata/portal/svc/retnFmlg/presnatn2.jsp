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

<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/graph.js'/>" defer></script>


<link href="<c:url value='/css/bigdata/retnFmlg/presnatn/layout.css'/>" rel="stylesheet">

	<div id="skipnavigation">
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#main_content">본문으로 바로가기</a>
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#menu_top">메뉴로 바로가기</a>
	</div>
<div class="wrap">

<!-- 좌측 GNB 영역 -->
        <header>
        	<div class="gnb">
	    		<h1><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#">bdp</a><span class="hide">빅데이터포털</span></h1>
	    		<ul class="nav">
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#"><img src="/images/bigdata/svc/navicon01.png" alt=""></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#"><img src="/images/bigdata/svc/navicon02.png" alt=""></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#"><img src="/images/bigdata/svc/navicon03.png" alt=""></a></li>
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
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result01" class="scrollMove"><span>품목분석</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result05" class="scrollMove"><span>지원정책</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result03" class="scrollMove"><span>정주여건</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result04" class="scrollMove"><span>품목정보</span></a></li>

	    		</ul>
	    	</div>
	    	<!-- //상단고정 -->


	    	<div class="resultBox" id="result01">
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
			                		<li class="tablinks on"><button type="button"><span>농업인 현황</span></button></li>
			                		<li class="tablinks"><button type="button"><span>재배면적추이</span></button></li>
			                		<li class="tablinks"><button type="button"><span>초기 영농인 영농현황</span></button></li>
<!-- 			                		<li class="tablinks"><button type="button"><span>평균재배면적추이</span></button></li> -->
<!-- 			                		<li class="tablinks"><button type="button"><span>연령별 평균재배면적</span></button></li> -->
			                	</ul>
			                	<div class="tabContWrap">
			                		<div class="tabcontent on">
			            				<img src="/images/bigdata/svc/retnFmlg/smpl_img05.png" alt="" style="margin:20px 0 10px 30px;display:inline-block;">
			                		</div>

			                		<!-- 재배면적추이 -->
			                		<div class="tabcontent">
			                			<div>
			                				<div style="width:69%;float:left;">
			                					<canvas id="graph_t1_0" height="250"></canvas>
			                				</div>
			                				<div style="width:29%;float:left;">
			                					<canvas id="graph_t1_1" height="250" ></canvas>
			                				</div>
			                			</div>
			            				<!-- <img src="/images/bigdata/svc/retnFmlg/smpl_img12.png" alt="" style="margin:20px 0 10px 30px;display:inline-block;"> -->
<!-- 				                		<h5>재배면적추이</h5> -->
			                		</div>

			                		<!-- 초기 영농인 영농현황 -->
			                		<div class="tabcontent">
			                			<div style="width:33%;float:left">
			                				<canvas id="graph_t2_0" height="250"></canvas>
			                			</div>
			                			<div style="width:33%;float:left">
			                				<canvas id="graph_t2_1" height="250"></canvas>
			                			</div>
			                			<div style="width:33%;float:left">
			                				<canvas id="graph_t2_2" height="250"></canvas>
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
	        </div><!-- //resultBox result04 -->


	        <div class="resultBox support" id="result05">
	        	<h3>지역맞춤 귀농교육 및 지원정책 - 경상북도 상주시</h3>
	        	<p class="mt20">귀하와 적합한 <strong>맞춤 귀농 지역</strong> 및 요건에 맞는 <br><strong>지역맞춤 귀농교육 및 지원정책 결과를 요약</strong>하여 아래와 같이 알려드립니다. <br>정보를 클릭하시면 정보의 리스트 및 상세정보를 확인하실 수 있습니다.</p>
	        	<a id="btn_popup_open" href="#">
		        	<img src="/images/bigdata/svc/retnFmlg/smpl_img02_B.png" alt="다이어그램" style="margin:10px 0 0 0;display:inline-block;">
		        </a>
	        </div><!-- //resultBox result05 -->


	        <div class="resultBox" id="result03">
	        	<h3>지역 정주 여건 정보 - 경상북도 상주시</h3>
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
                		<div class="tabcontent">
                			<div>
                				<div style="width:29%;float:left;">
                					<canvas id="sj_b_t0_0" height="250"></canvas>
                				</div>
                				<div style="width:69%;float:left;">
                					<canvas id="sj_b_t0_1" height="250"></canvas>
                				</div>
                				<div style="width:29%;float:left;">
                					<canvas id="sj_b_t0_2" height="250"></canvas>
                				</div>
                				<div style="width:69%;float:left;">
                					<canvas id="sj_b_t0_3" height="250"></canvas>
                				</div>
                			</div>
							<!-- <img src="/images/bigdata/svc/retnFmlg/smpl_graph30.png" alt="" style="margin:30px 0 10px 0;display:inline-block;"> -->
                		</div>
                		<div class="tabcontent">
            				<div class="lineBox mt20">
            					<p class="title">
            						<span>연령대별 귀농인 정보(명) </span>
            						<span style="margin-left:110px;">귀농인 전출지역 정보 </span>
            						<span style="margin-left:160px;">귀농인 재배품목 정보 </span>
            					</p>
            					<div class="">
            						<img src="/images/bigdata/svc/retnFmlg/smpl_graph09.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                				</div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div class="row">
	                			<div class="w27p">
	                				<h5>교육시설별 접근시간</h5>
	                				<div class="lineBox">
	                					<p class="title">자가용 이용시 소요시간(분)</p>
		                				<div class="">
		                					<img src="/images/bigdata/svc/retnFmlg/smpl_graph14.png" alt="" style="margin:50px 0 0 30px;display:inline-block;">
		                				</div>
		                			</div>
	                			</div>
	                			<div class="w69p fr">
	                				<h5>안성시 읍면동별 교육시설 접근성 비교</h5>
	                				<div class="lineBox">
	                					<div class="tabWrap typeB">
	                						<ul class="tabTitle">
						                		<li class="tablinks on"><button type="button"><span>초등학교</span></button></li>
						                		<li class="tablinks"><button type="button"><span>중학교</span></button></li>
						                		<li class="tablinks"><button type="button"><span>고등학교</span></button></li>
						                	</ul>
						                	<div class="tabContWrap">
						                		<div class="tabcontent on">
						                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph15.png" alt="" style="margin:3px 0 0 25px;display:inline-block;">
						                		</div>
						                		<div class="tabcontent">
						                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph16.png" alt="" style="margin:3px 0 0 25px;display:inline-block;">
						                		</div>
						                		<div class="tabcontent">
						                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph17.png" alt="" style="margin:3px 0 0 25px;display:inline-block;">
						                		</div>
						                	</div>

		                				</div>
		                			</div>
	                			</div>
	                		</div>
                		</div>
                		<div class="tabcontent on">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph18.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<h5>편의</h5>

                		</div>
                		<div class="tabcontent">
                			<h5>문화</h5>

                		</div>
                		<div class="tabcontent">
                			<h5>유통</h5>

                		</div>
                		<div class="tabcontent">
                			<h5>부가정보</h5>

                		</div>
                	</div>
                </div>
	        </div><!-- //resultBox result03 -->



	        <div class="resultBox" id="result04">
	        	<h3>귀농 지역 맞춤 품목 정보 - 경상북도 상주시</h3>
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
			                		<li class="tablinks on"><button type="button"><span>재배면적추이</span></button></li>
			                		<li class="tablinks"><button type="button"><span>도매시장 경락 가격</span></button></li>
			                		<li class="tablinks"><button type="button"><span>연령대별 재배농가현황</span></button></li>
			                		<li class="tablinks"><button type="button"><span>생산/유통 안정성</span></button></li>
			                		<li class="tablinks"><button type="button"><span>품목부가정보</span></button></li>
			                	</ul>
			                	<div class="tabContWrap">
			                		<div class="tabcontent on">
			            				<img src="/images/bigdata/svc/retnFmlg/smpl_img03.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<h5>도매시장 경락 가격</h5>
			                		</div>
			                		<div class="tabcontent">                			<h5>연령대별 재배농가현황</h5>
			                		</div>
			                		<div class="tabcontent">
				                		<h5>생산/유통 안정성</h5>
			                		</div>
			                		<div class="tabcontent">
				                		<h5>품목부가정보</h5>
			                		</div>
			                	</div><!-- //tabContWrap -->
			                </div><!-- //tabWrap -->
                		</div><!-- //배 -->
                		<div class="tabcontent">

                		</div><!-- //포도 -->
                		<div class="tabcontent">

                		</div><!-- //양파 -->
                	</div><!--  //tabContWrap -->
                </div><!-- //tabWrap - 배포도양파 -->
	        </div><!-- //resultBox result04 -->



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
        			<span class="w100p">서울특별시 강동구</span>
        		</div>
        		<div class="row">
        			<span class="w48p">남자</span>
        			<span class="w48p">59</span>
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
        			<span class="w48p">55</span>
        		</div>
<!--         		<div class="row"> -->
<!--         			<span class="w100p mint">자녀</span> -->
<!--         		</div> -->
<!--         		<div class="row"> -->
<!--         			<span class="w48p">여자</span> -->
<!--         			<span class="w48p">12</span> -->
<!--         		</div> -->
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>희망귀농지역</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<!-- 답변 없는 경우 class="blank" -->
        			<span class="w100p">경상북도 상주시</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>희망재배품목</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p">포도</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>고려사항</span></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<span class="w48p hig">교통</span>
        			<span class="w48p hig">의료</span>
        		</div>
        		<div class="row">
        			<span class="w48p low">학교</span>
        			<span class="w48p low">교육</span>
        		</div>
        		<div class="row">
        			<span class="w48p low " >상업</span>
        			<span class="w48p low ">편의</span>
        		</div>
        		<div class="row">
        			<span class="w48p low ">문화</span>
        			<span class="w48p mid">귀농지원정책</span>
        		</div>
        		<div class="row">
        			<span class="w48p low ">농산물유통여건</span>
        			<span class="w48p low ">농지실거래가격</span>
        		</div>
        		<div class="row">
        			<span class="w48p low ">농지임대가격</span>
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
	tempSrchObj.sigungu = '상주시';
// 	tempSrchObj.umd = '한림읍';
})