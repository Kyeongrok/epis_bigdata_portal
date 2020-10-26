<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/ricePredictForm.js'/>"></script>
</head>

<body>

	<div id="skipnavigation">
		<a href="#main_content">본문으로 바로가기</a> 
		<a href="#">바로가기</a>
	</div>

    <div class="wrap">

<!-- 좌측 GNB 영역 -->    	
        <header>
        	<div class="gnb">
	    		<h1>bdp<span class="hide">빅데이터포털</span></h1>
	    		<!--
	    		<ul class="nav">
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    		</ul>
	    	-->
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
	    	<div class="areaListWrap" id="area_list">

				<div class="btnWrap mt55">
					<button type="button" class="btnB dashboard"><span>대시보드</span></button>
					<button type="button" class="btnB cmpr"><span>추이 비교</span></button>
				</div>

	    	</div><!-- //areaListWrap -->
<!-- //지역명 리스트 -->	    	



<!-- 지도&그래프 -->
	    	<div class="cntrWrap output">
		    	
	    		<h3>예상생산량 확인하기</h3>
    			<form class="formBox">
	        		<table class="grid typeA">
	        			<caption></caption>
	        			<colgroup>
	        				<col width="14%">
	        				<col width="*">
		        		</colgroup>
	        			<tbody>
	        				<tr>
	        					<th scope="row">생산 면적</th>
	        					<td>
	        						<input type="number" placeholder="면적을 입력하세요" value="0" class="ar" autofocus="autofocus" />
	        						<span class="unit">m<em>2</em></span>
	        					</td>
	        				<tr>
	        					<th scope="row">지역</th>
	        					<td class="sel">
	        						<!-- template 
	        						<select class="w100">
        								<option value="">경기도</option>
        							</select>
        							<select class='w100'>
        								<option value="">가평군</option>
        							</select> -->
	        					</td>
	        				</tr>
	        			</tbody>
	        		</table>
	        		<div class="btnWrap">
						<button type="button" class="btnB predict"><span>생산량 예측하기</span></button>
					</div>
	        	</form>

		    	<div class="myPrediction">
		    		<h3>나의 생산면적과 나의 재배지역의 단수를 예측하여 분석한 나의 생산량 예측정보입니다.</h3>
		    		<h3>재배면적이 미비하여 단수예측값을 제공하지 않는 시군구의 경우 생산량이 0으로 표기될수 있으니 주변 시군구를 선택하여 이용하세요.</h3>
		    		<div class="grayBox">
		    			<p>
		    				나의 <strong>쌀 예상 생산량</strong>은 
		    				<input type="text" disabled="disabled" class="result outtrn"/>
		    				kg 입니다.
		    			</p>
		    			<p>
		    				나의 <strong>면적별 예상 생산량</strong>은 
		    				<input type="text" disabled="disabled" class="result outtrn byar"/>
		    				kg/<span class="unit">m<em>2</em></span> 입니다.
		    			</p>
		    		</div>
		    	</div>

		    </div><!-- //cntrWrap -->
<!-- //지도&그래프 -->





	    </section><!-- //cntWrap -->


    </div><!-- //wrap -->
</body>

</html>