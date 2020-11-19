<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="robots" content="index, follow">
    
	<title>쌀 생산 유통 상시분석 서비스</title>
	
	<link rel="stylesheet" href="<c:url value='/resources/svc/rice/css/layout.css'/>">

	
	<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js"></script>
	
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/riceDetail.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/riceDataObj.js'/>"></script>
	
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-4EMQKSH6X9"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());
    
        gtag('config', 'G-4EMQKSH6X9');
    </script>
</head>
<body>

	<div id="skipnavigation">
		<a href="#main_content">본문으로 바로가기</a> 
		<a href="javascript:;">메뉴로 바로가기</a>
	</div>

    <div class="wrap">

<!-- 좌측 GNB 영역 -->    	
        <header>
        	<div class="gnb">
	    		<h1><a href="javascript:;">bdp</a><span class="hide">빅데이터포털</span></h1>
	    		<!-- <ul class="nav">
	    			<li><a href="javascript:;"><img src="" alt="" />이동</a></li>
	    			<li><a href="javascript:;"><img src="" alt="" />이동</a></li>
	    			<li><a href="javascript:;"><img src="" alt="" />이동</a></li>
	    		</ul> -->
	    	</div><!-- //gnb -->	  
        </header>


<!-- 중앙 컨텐츠 영역 -->
        <section class="cntWrap" id="main_content">

        	<div class="description">
	    		<h2>
	    			<span style="margin-bottom:5px;"><strong>쌀 생산</strong>유통</span>
	    			<br/>
	    			<span style="margin-bottom:5px;"><strong>상시분석</strong></span>
	    			<br/>
	    			<span>서비스</span>
	    		</h2>
	            <p class="web_ex">
	            	쌀 생산유통 상시분석 서비스는 <br/>직불금 면적정보에 생육정보 등을 <br/>반영한 알고리즘 모델을 적용하여<br/>전국, 시, 도, 군 단위의 <br/>쌀 생산량을 예측하는 <br/>서비스입니다.
	            </p>
	    	</div> 	

<!-- 지역명 리스트 -->
	    	<div class="areaListWrap">
	    		<p class="whole"><button type="button" style="font-size:18px;"><span>전국</span></button></p>
	    		<ul class="dep1" dir="ltr">
	    			<c:forEach items="${sidos }" var="d" varStatus="status">
		    			<li class="sido" data-sido-code="${d.sidoCode }" data-sido-name="${d.sidoName }" style="display:none;">
		    				<button type="button" class="dist sido" id="map_name${status.count }"  data-sido-code="${d.sidoCode }" data-sido-name="${d.sidoName }" style="font-size:18px;"><span>${d.sidoName }</span></button>
		    				<button type="button" class="btn"><span>열기/닫기</span></button>
		    			</li>
	    			</c:forEach>
	    			<!-- <li>
	    				<button type="button" class="dist" id="map_name04"><span>경기도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name05"><span>경상남도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name06"><span>경상북도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name07"><span>광주광역시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name08"><span>대구광역시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name09"><span>대전광역시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name10"><span>부산광역시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name02"><span>서울특별시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name11"><span>세종시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name12"><span>울산광역시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name03"><span>인천광역시</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name13"><span>전라남도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name14"><span>전라북도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name15"><span>제주특별자치도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name16"><span>충청남도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li>
	    			<li>
	    				<button type="button" class="dist" id="map_name17"><span>충청북도</span></button>
	    				<button type="button" class="btn"><span>열기/닫기</span></button>
	    			</li> -->
	    		</ul>
	    		
	    		<div class="btnWrap">
					<button type="button" class="btnB cmpr"><span>추이 비교</span></button>
					<button type="button" class="btnB predict"><span>예상생산량 예측</span></button>
				</div>
	    	</div><!-- //areaListWrap -->
<!-- //지역명 리스트 -->	    	



<!-- 지도&그래프 -->
	    	<div class="cntrWrap">
		    	<div class="mapWrap">
		    		<div class="whole">
		    			<p><img src="<c:url value='/resources/svc/rice/images/map_whole.png'/>" alt="전국" /></p>
		    		</div>
		    		
		    		<!--  -->
		    		<ul>
		    			<li class="area01">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area01.png'/>" alt="강원도" id="map_img01" data-sido-name="강원도" /></p>
		    			</li>
		    			<li class="area02">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area04.png'/>" alt="경기도" id="map_img04"  data-sido-name="경기도" /></p>
		    			</li>
		    			<li class="area03">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area05.png'/>" alt="경상남도" id="map_img05"  data-sido-name="경상남도" /></p>
		    			</li>
		    			<li class="area04">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area06.png'/>" alt="경상북도" id="map_img06"  data-sido-name="경상북도" /></p>
		    			</li>
		    			<li class="area05">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area07.png'/>" alt="광주광역시" id="map_img07" data-sido-name="광주광역시"  /></p>
		    			</li>
		    			<li class="area06">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area08.png'/>" alt="대구광역시" id="map_img08" data-sido-name="대구광역시"  /></p>
		    			</li>
		    			<li class="area07">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area09.png'/>" alt="대전광역시" id="map_img09" data-sido-name="대전광역시"  /></p>
		    			</li>
		    			<li class="area08">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area10.png'/>" alt="부산광역시" id="map_img10" data-sido-name="부산광역시"  /></p>
		    			</li>
		    			<li class="area09">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area02.png'/>" alt="서울특별시" id="map_img02" data-sido-name="서울특별시"  /></p>
		    			</li>
		    			<li class="area10">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area11.png'/>" alt="세종시" id="map_img11" data-sido-name="세종특별자치시"  /></p>
		    			</li>
		    			<li class="area11">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area12.png'/>" alt="울산광역시" id="map_img12" data-sido-name="울산광역시"  /></p>
		    			</li>
		    			<li class="area12">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area03.png'/>" alt="인천광역시" id="map_img03" data-sido-name="인천광역시"  /></p>
		    			</li>
		    			<li class="area13">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area13.png'/>" alt="전라남도" id="map_img13" data-sido-name="전라남도"  /></p>
		    			</li>
		    			<li class="area14">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area14.png'/>" alt="전라북도" id="map_img14" data-sido-name="전라북도"  /></p>
		    			</li>
		    			<li class="area15">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area15.png'/>" alt="제주특별자치도" id="map_img15" data-sido-name="제주특별자치도"  /></p>
		    			</li>
		    			<li class="area16">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area16.png'/>" alt="충청남도" id="map_img16" data-sido-name="충청남도"  /></p>
		    			</li>
		    			<li class="area17">
		    				<p><img src="<c:url value='/resources/svc/rice/images/map_area17.png'/>" alt="충청북도" id="map_img17" data-sido-name="충청북도"  /></p>
		    			</li>
		    		</ul>
		    		<div class="areaName">
		    			<p><img src="<c:url value='/resources/svc/rice/images/map_areaname.png'/>" alt="지역명" usemap="#map" name="mapArea"></p>
		    			<map name="map" >
						 	<area id="map_area01" shape="poly" data-sido-name="강원도" coords="351,32,414,33,436,19,434,3,441,10,464,60,504,111,510,130,494,139,456,137,442,134,425,118,413,126,395,130,393,123,402,96,394,90,382,89,379,72,385,68,385,62,359,44,356,43" alt="강원도" href="#" target="_self">
						 	<area id="map_area02" shape="poly" data-sido-name="서울특별시" coords="329,92,350,79,349,86,358,99,345,106,332,101,321,92" alt="서울특별시" href="#" target="_self">
						 	<area id="map_area03" shape="poly" data-sido-name="인천광역시" coords="315,90,321,86,325,93,325,99,328,104,324,111,313,101,314,92" alt="인천광역시" href="#" target="_self">
						 	<area id="map_area04" shape="poly" data-sido-name="경기도" coords="320,64,354,28,362,43,376,50,387,66,379,77,383,92,400,97,396,127,364,152,338,147,325,137,297,120,283,72" alt="경기도" href="#" target="_self">
						 	<area id="map_area05" shape="poly" data-sido-name="경상남도" coords="407,235,415,238,426,253,452,261,469,264,484,260,479,265,500,280,479,291,469,299,477,311,458,337,424,344,402,335,399,313,383,284,388,274,384,263,390,244,406,235" alt="경상남도" href="#" target="_self">
						 	<area id="map_area06" shape="poly" data-sido-name="경상북도" coords="460,139,508,132,518,166,515,196,514,222,524,221,518,256,493,249,482,257,477,256,468,264,450,258,471,244,462,220,433,234,434,257,423,258,418,241,411,235,401,234,401,223,416,211,417,203,403,201,405,174,434,154,438,159,446,158,446,148,460,139" alt="경상북도" href="#" target="_self">
						 	<area id="map_area07" shape="poly" data-sido-name="광주광역시" coords="318,292,327,288,341,290,347,301,339,306,326,308,310,299,315,293" alt="광주광역시" href="#" target="_self">
						 	<area id="map_area08" shape="poly" data-sido-name="대구광역시" coords="445,230,461,221,467,231,471,242,461,251,453,261,434,258,438,232" alt="대구광역시" href="#" target="_self">
						 	<area id="map_area09" shape="poly" data-sido-name="대전광역시" coords="368,185,378,189,380,194,376,212,361,210,354,203" alt="대전광역시" href="#" target="_self">
						 	<area id="map_area10" shape="poly" data-sido-name="부산광역시" coords="496,279,506,284,485,312,470,303,466,299" alt="부산광역시" href="#" target="_self">
						 	
						 	<area id="map_area11" shape="poly" data-sido-name="세종특별자치시" coords="351,163,363,167,363,173,369,182,367,192,354,191,351,178,351,172" alt="세종시" href="#" target="_self">
						 	<area id="map_area12" shape="poly" data-sido-name="울산광역시" coords="484,256,491,249,519,257,506,286,479,266" alt="울산광역시" href="#" target="_self">
						 	
						 	<area id="map_area13" shape="poly" data-sido-name="전라남도" coords="304,275,310,284,323,280,330,268,340,277,345,273,348,287,370,285,373,280,384,285,401,316,401,363,358,382,304,385,271,370,256,343,277,294,283,283" alt="전라남도" href="#" target="_self">
						 	<area id="map_area14" shape="poly" data-sido-name="전라북도" coords="335,215,343,211,352,222,368,217,376,230,389,224,406,225,405,236,387,248,384,266,390,275,383,288,377,278,370,286,346,285,343,273,326,269,325,275,312,286,304,273,305,256,316,230,334,222" alt="전라북도" href="#" target="_self">
						 	<area id="map_area15" shape="poly" data-sido-name="제주특별자치도" coords="284,450,300,436,338,428,347,437,328,457,302,463,285,463,276,454" alt="제주특별자치도" href="#" target="_self">
						 	<area id="map_area16" shape="poly" data-sido-name="충청남도" coords="301,137,329,150,340,151,353,145,371,158,373,164,364,168,356,162,350,165,354,177,353,189,359,193,362,191,358,205,367,214,369,208,375,213,379,207,384,210,390,224,375,228,366,217,349,223,345,216,334,214,334,224,321,228,306,216,294,188,285,167,275,162,295,139" alt="충청남도" href="#" target="_self">
						 	<area id="map_area17" shape="poly" data-sido-name="충청북도" coords="362,151,394,126,410,122,436,124,459,140,438,158,430,156,402,178,403,203,415,208,412,216,402,226,388,221,381,203,380,204,381,194,373,179,368,180,361,166,374,160" alt="충청북도" href="#" target="_self">
						</map>
		    		</div>
		    	</div><!-- //mapWrap -->


		    	<div class="graphWrap" style="height:400px;">
		    		<%-- <img src="<c:url value='/resources/svc/rice/images/smpl_img01.png'/>" alt="" style="margin: 5px 0 0 15px;"/> --%>
		    		 <div class="chartWrap" style="width:792px;height:400px;">
			    		 <%--template 
				    	<canvas id="chart" ></canvas>
				    	--%>
				    </div> 
		    	</div>
		    	<div class="gridWrap" class="display:none;" ></div>
		    	
		    </div><!-- //cntrWrap -->
<!-- //지도&그래프 -->




<!--  우측정보  -->
	    	<div class="infoWrap">
	    		<ul>
	    			<li>
	    				<p class="areaName">전국</p>
	    				<p class="title"><button type="button" class="thisYear outtrn">쌀 생산량 예측<span>(톤)</span></button></p>
	    				<p class="num"><strong class="outtrn">0</strong></p>
	    			</li>
	    			<li>
	    				<p class="title"><button type="button" class="thisYear ar">직불금 면적<span>(㎡)</span></button></p>
	    				<p class="num"><strong class="ar">0</strong></p>
	    				<p class="exp">쌀 직불금 확정면적 기준 쌀 재배면적</p>
	    			</li>
	    			<li>
	    				<p class="title"><button type="button" class="byYear outtrn"><span>연도별 생산량 추이</span></button></p>
	    				<!-- <p class="exp">쌀 직불금 확정면적 기준 최근 5년간의 쌀 생산량 정보</p> -->
	    				<div class="btnWrap">
							<button type="button" class="btnO byYearOuttrnReqst"><span>신청면적</span></button>
							<button type="button" class="btnO byYearOuttrnDcsn"><span>확정면적</span></button>
						</div>
	    			</li>
	    			<li>
	    				<p class="title"><button type="button" class="byYear ar"><span>연도별 재배면적 추이</span></button></p>
	    				<!-- <p class="exp">쌀 직불금 확정면적 기준 최근 5년간의 쌀 재배면적 추이 정보</p> -->
	    				<div class="btnWrap">
							<button type="button" class="btnO byYearArReqst"><span>신청면적</span></button>
							<button type="button" class="btnO byYearArDcsn"><span>확정면적</span></button>
						</div>
	    			</li>
	    		</ul>
	    	</div><!-- //infoWrap -->
<!--  //우측정보  -->	    	


	    </section><!-- //cntWrap -->


    </div><!-- //wrap -->
    
   
    
    

 
 	
</body>
</html>