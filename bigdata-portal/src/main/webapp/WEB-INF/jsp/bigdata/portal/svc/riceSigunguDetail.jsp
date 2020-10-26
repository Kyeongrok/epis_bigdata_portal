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
    
    <title>쌀 생산유통 상시분석 서비스</title>
    <link rel="stylesheet" href="<c:url value='/resources/svc/rice/css/layout.css'/>">
    
	
    <script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js"></script>
	
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/riceSigunguDetail.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/riceDataObj.js'/>"></script>
	
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

			<input type="hidden" name="sidoCode" value="${sidoCode }"/>
			
			<!-- 지역명 리스트 -->
	    	<div class="areaListWrap">
	    		<p class="whole"><button type="button" onclick="location.href='./riceDetail.do'" style="font-size:18px;"><span>전국</span></button></p>
	    		<ul class="dep1" dir="ltr">
	    			<c:forEach items="${sidos }" var="d" varStatus="status">
		    			<li class="sido" data-index="${status.count }"  data-sido-code="${d.sidoCode }" data-sido-name="${d.sidoName }" style="display:none;">
		    				<button type="button" class="dist sido" id="map_name${status.count }"  data-sido-code="${d.sidoCode }" data-sido-name="${d.sidoName }" style="font-size:18px;"><span>${d.sidoName }</span></button>
		    				<button type="button" class="btn"><span>열기/닫기</span></button>
		    				<ul class="dep2" style="display:none;">
		    					<c:forEach items="${sigungus }" var="d2" varStatus="st2">
		    						<c:if test="${d.sidoCode eq d2.sidoCode }">
				    					<li class="sigungu" data-sido-code="${d2.sidoCode }" data-sigungu-code="${d2.sigunguCode }" data-sigungu-name="${d2.sigunguName }">
				    						<button type="button" id="map_name_${varStatus.count }_${st2.count }" disabled="disabled" style="cursor: default;font-size:17px;"><span>${d2.sigunguName }</span></button>
				    					</li>
		    						</c:if>
		    					</c:forEach>
		    				</ul>
		    			</li>
	    			</c:forEach>	    			
	    		</ul><div class="btnWrap">
					<button type="button" class="btnB cmpr"><span>추이 비교</span></button>
					<button type="button" class="btnB predict"><span>예상생산량 예측</span></button>
				</div>
	    	</div><!-- //areaListWrap -->
<!-- //지역명 리스트 -->	    	



<!-- 지도&그래프 -->
	    	<div class="cntrWrap">
		    	<div class="mapWrap">
		    		<div class="whole">
		    			<p><img src="./images/map_whole_area01.png" alt="강원도" /></p>
		    		</div>
		    		<ul>
    					
		    		</ul>
		    		<div class="areaName" style=";">
		    			<p><img src="./images/map_area01_name.png" alt="지역명" usemap="#map" name="mapArea"></p>
		    			
		    		</div>
		    	</div><!-- //mapWrap -->


		    	<div class="graphWrap" style="height:400px;">
		    		<!-- <img src="./images/smpl_img01.png" alt="" style="margin: 5px 0 0 15px;"/> -->
		    		
		    		<div class="chartWrap" style="width:792px;height:400px;">
		    		</div>
		    		
		    	</div>
		    	
		    	<div class="gridWrap" style="margin-top:10px;"></div>
		    </div><!-- //cntrWrap -->
<!-- //지도&그래프 -->




<!--  우측정보  -->
	    	<div class="infoWrap">
	    		<ul>
	    			<li>
	    				<p class="areaName">강원도</p>
	    				<p class="title"><button type="button" class="thisYear outtrn">쌀 생산량 예측(톤)</button></p>
	    				<p class="num"><strong class="tot outtrn">0</strong><span></span></p>
	    			</li>
	    			<li>
	    				<p class="title"><button type="button" class="thisYear ar">직불금 면적(㎡)</button></p>
	    				<p class="num"><strong class="tot ar">0</strong><span></span></p>
	    				<p class="exp">쌀 직불금 확정면적 기준 쌀 재배면적</p>
	    			</li>
	    			<li>
	    				<p class="title"><button type="button" class="byYear outtrn "><span>연도별 생산량 추이</span></button></p>
	    				<!-- <p class="exp">쌀 직불금 확정면적 기준 최근 5년간의 쌀 생산량 정보</p> -->
	    				<div class="btnWrap">
							<button type="button" class="btnO byYearOuttrnReqst"><span>신청면적</span></button>
							<button type="button" class="btnO byYearOuttrnDcsn"><span>확정면적</span></button>
						</div>
	    			</li>
	    			<li>
	    				<p class="title"><button type="button" class="byYear ar "><span>연도별 재배면적 추이</span></button></p>
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