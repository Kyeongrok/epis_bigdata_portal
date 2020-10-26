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
	
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/riceDataObj.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/riceCmprForm.js'/>"></script>

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
					<button type="button" class="btnB predict"><span>예상생산량 확인</span></button>
				</div>

	    	</div><!-- //areaListWrap -->
<!-- //지역명 리스트 -->	    	



<!-- 지도&그래프 -->
	    	<div class="cntrWrap output">
		    	
	    		<h3>생산량 또는 재배면적 비교하기</h3>
    			<form class="formBox">
	        		<table class="grid typeA">
	        			<caption></caption>
	        			<colgroup>
	        				<col width="14%">
	        				<col width="*">
		        		</colgroup>
	        			<tbody>
	        				<tr>
	        					<th scope="row">기간 (연도)</th>
	        					<td>
	        						<div class="">
	        							<select class="w100 fromYear">
	        								
	        							</select>
	        							<span>&#8764;</span>
	        							<select class='w100 toYear'>
	        								
	        							</select>
	        						</div>
	        					</td>
	        				</tr>
	        				<tr>
	        					<th scope="row">면적 &#40;<span>m<em>2</em></span>&#41;</th>
	        					<td>
			                        <label><input type="radio" name="arGbn" value="reqst" checked="checked"><span>신고면적</span></label>
			                        <label><input type="radio" name="arGbn" value="dcsn"><span>확정면적</span></label>
	        					</td>
	        				</tr>
	        				<tr>
	        					<th scope="row">지역</th>
	        					<td>
	        						<label><input type="radio" name="gbn" value="sd" checked="checked"><span>시도</span></label>
			                        <label><input type="radio" name="gbn" value="sgg"><span>시군구</span></label>
	        					</td>
	        				</tr>
	        				<tr class="sido">
	        					<th scope="row">시도</th>
	        					<td class="sido list">
	        						<!-- template
	        						<label><input type="checkbox" name="333"><span>경기도</span></label>
			                        <label><input type="checkbox" name="333"><span>강원도</span></label>
			                        <label><input type="checkbox" name="333"><span>충청북도</span></label>
			                        <label><input type="checkbox" name="333"><span>충청남도</span></label>
			                        <label><input type="checkbox" name="333"><span>전라북도</span></label>
			                        <label><input type="checkbox" name="333"><span>전라남도</span></label>
			                        <label><input type="checkbox" name="333"><span>경상북도</span></label>
			                        <label><input type="checkbox" name="333"><span>경상남도</span></label>
			                         -->
	        					</td>
	        				</tr>
	        				<tr class="sigungu" style="display:none;">
	        					<th scope="row">시군구</th>
	        					<td class="sigungu list">
	        						<!-- template
	        						<div class="selWrap">
	        							<select class="w100">
	        								<option value="">경기도</option>
	        							</select>
	        							<select class='w100'>
	        								<option value="">가평군</option>
	        							</select>
	        						</div>
	        						 -->
	        						   
	        						<button type="button" class="btnPlus add sigungu"><span>+</span></button>  						
	        					</td>
	        				</tr>
	        			</tbody>
	        		</table>
	        		<div class="btnWrap">
						<button type="button" class="btnB cmpr outtrn"><span>생산량 비교하기</span></button>
						<button type="button" class="btnP cmpr ar"><span>재배면적 비교하기</span></button>
					</div>
	        	</form>

		    	<div class="graphWrap" style="width:800px;height:500px;">
		    		<div class="chartWrap" style="width:800px;height:480px;">
						<canvas id="chart" width="800" height="480"></canvas>
					</div>
		    	</div>

		    	<div class="gridWrap">
		    		<!-- template
	        		<table class="grid">
	        			<caption></caption>
	        			<colgroup>
	        				<col width="20%">
	        				<col width="*">
	        				<col width="16%">
	        				<col width="16%">
	        				<col width="16%">
	        				<col width="16%">
		        		</colgroup>
	        			<thead>
	        				<tr>
	        					<th scope="col">시군구</th>
	        					<th scope="col">2015</th>
	        					<th scope="col">2016</th>
	        					<th scope="col">2017</th>
	        					<th scope="col">2018</th>
	        					<th scope="col">2019</th>
	        				</tr>
	        			</thead>
	        			<tbody>
	        				<tr>
	        					<th scope="row">경기도 &minus; 가평군</th>
	        					<td>495</td>
	        					<td>516</td>
	        					<td>469</td>
	        					<td>506</td>
	        					<td>514</td>
	        				</tr>	        				
	        			</tbody>
	        		</table>
	        		 -->
	        	</div>
		    </div><!-- //cntrWrap -->
<!-- //지도&그래프 -->





	    </section><!-- //cntWrap -->


    </div><!-- //wrap -->
</body>

</html>