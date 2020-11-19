<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
귀농의사결정 지원 - 메인화면
2020.02.09
--%>
<head>
	<title>데이터 기반 귀농 지역·품목 안내 서비스</title>

    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-4EMQKSH6X9"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());
    
        gtag('config', 'G-4EMQKSH6X9');
    </script>
</head>
    <link rel="stylesheet" href="<c:url value='/css/bigdata/retnFmlg/info/layout.css'/>">
    <script src="<c:url value='/js/vendor/jquery/jquery-2.1.4.min.js'/>"></script>
    <script src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>
    <script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnFmlgCommon.js'/>" defer></script>
    <script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnFmlgInfo.js'/>" defer></script>
<%--     <script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/modal/commonModal.js'/>" defer></script> --%>

<div class="wrap">

<!-- 좌측 GNB 영역 -->
    <header>

    	<div class="gnb">
 		<h1><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/dashboard.html#">bdp</a><span class="hide">빅데이터포털</span></h1>
 		<!-- 농업on 로고 추가 -->
 		<!--<img src="/images/bigdata/svc/retnFmlg/h1_logo.png" alt="">  -->
<!--  		<ul class="nav"> -->
<!--  			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/dashboard.html#"><img src="/images/bigdata/svc/navicon01.png" alt=""></a></li> -->
<!--  			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/dashboard.html#"><img src="/images/bigdata/svc/navicon02.png" alt=""></a></li> -->
<!--  			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/dashboard.html#"><img src="/images/bigdata/svc/navicon03.png" alt=""></a></li> -->
<!--  		</ul> -->
 	</div><!-- //gnb -->
    </header>


<!-- 중앙 컨텐츠 영역 -->
    <section class="cntWrap">

    	<div class="description">
 		<h1>데이터 기반<br><span>귀농 지역·<br>품목 안내<br>서비스</span></h1>
         <p class="web_ex">
         	데이터 기반 귀농 지역·품목 안내<br> 서비스는 입력하신 정보와 유사한<br> 귀농인 13,930명의 데이터를<br> 분석한 결과를 기반으로<br> 귀농 지역 및 품목, 부가정보를<br> 제공하는 서비스입니다.<br><br> (유사귀농인데이터:19.12월기준)
         </p>
 	</div>

 	<div class="formWrap">
     	<h2>정보입력</h2>
     	<p>귀농 의사결정을 위해 귀농 희망자의 정보를 입력하는 화면입니다.</p>
     	<!-- MK 버튼 삭제 -->
     	<!-- <div class="resultBox btnWrap">
			<button type="button" id="showResultBtn" class="showResultBtn"><span>결과보기</span></button>
		</div> -->
         <div class="formBox">
             <p>○ 본인 정보를 입력하세요.(<span class="filsu">필수</span>)</p>
             <div id="selfDiv" class="rowWrap">
	             <div class="row">
	             	<input type="text" name="mvtRdNmAdr" class="w80p" placeholder="현거주지 정보를 입력하세요.">
	             	<button id="mvtSrchAddr" type="button" class="srchAddr layPopBtn w20p" data-target="addrModal" data-addrPrefix="mvt" ><span>검색하기</span></button>
	             </div>
	             <div class="row">
	             	<p class="w20p">성별</p>
	             	<div class="w80p">
	              	<label><input type="radio" name="selfSexdstn" value='male'><span>남자</span></label>
	              	<label><input type="radio" name="selfSexdstn" value='female'><span>여자</span></label>
	              </div>
	             </div>
	             <div class="row">
	             	<p class="w20p">나이</p>
	             	<input type="text" class="w80p" name="selfAge">
	             </div>
	             <div class="txtR fs10">
	             	<span style="font-weight: bold">* 개인정보 보호법에 의거하여 상세주소를 입력하여도 시군구 단위까지만 지역이 선택되어 보여집니다</span>
	             </div>
	             <div class="txtR fs11">
	             	<span><a href="http://www.juso.go.kr">www.juso.go.kr</a> API 사용</span>
	             </div>
             </div>
         </div><!-- //formBox -->
         <div class="formBox">
             <p>○ 귀농 시<strong>동거가족</strong>을 입력하세요.(<span class="filsu">필수</span>)</p>
             <div class="relateDiv rowWrap">
	             <div class="row">
	             	<p class="w20p">관계</p>
	             	<div class="w80p">
	              	<label><input type="radio" name="1-relate" value="spouse"><span>배우자</span></label>
	              	<label><input type="radio" name="1-relate" value="chldrn"><span>자녀</span></label>
	              	<label><input type="radio" name="1-relate" value="parnts"><span>부모</span></label>
	              	<label><input type="radio" name="1-relate" value="empty"><span>없음</span></label>
	              </div>
	             </div>
	             <div class="row">
	             	<p class="w20p">성별</p>
	             	<div class="w80p">
	              	<label><input type="radio" name="1-relateSexdstn" value="male"><span>남자</span></label>
	              	<label><input type="radio" name="1-relateSexdstn" value="female"><span>여자</span></label>
	              </div>
	             </div>
	             <div class="row">
	             	<p class="w20p">나이</p>
	             	<input type="text" class="w80p" name="1-relateAge">
	             </div>
	         </div>
             <div class="row noBg">
              <button type="button" id="delLivtgtBtn" class="btnAdd fr"><span class="filsu">삭제하기</span></button>
              <button type="button" id="addLivtgtBtn" class="btnAdd fr"><span>추가하기</span></button>
        	 </div>
         </div><!-- //formBox -->
         <div class="formBox">
             <p>○ 귀농 시 희망<strong>귀농지역</strong>을 입력하세요.(<span class="sel">선택</span>)</p>
             <div id="hopeAreaDiv" class="rowWrap">
	             <div class="row">
	             	<input type="text" class="w80p" name="hopeRdNmAdr" placeholder="희망 귀농지역을 입력하세요.">
	             	<button id="hopeSrchAddr" type="button" class="srchAddr layPopBtn w20p" data-target="addrModal" data-addrPrefix="hope"><span>검색하기</span></button>
	             </div>
             </div>
             <div class="txtR fs10">
	             	<span style="font-weight: bold">* 개인정보 보호법에 의거하여 상세주소를 입력하여도 시군구 단위까지만 지역이 선택되어 보여집니다</span>
	             </div>
             <div class="txtR fs11">
	             	<span><a href="http://www.juso.go.kr">www.juso.go.kr</a> API 사용</span>
	             </div>
         </div><!-- //formBox -->
         <div class="formBox">
             <p>○ 귀농 시 희망<strong>재배품목</strong>을 입력하세요.(<span class="sel">선택</span>)</p>
             <div id="hopeCtvtDiv" class="rowWrap">
	             <div class="row">
	             	<input type="text" class="w80p" name="hopeCtvt" placeholder="희망 재배품목을 입력하세요.">
	             	<button id="srchCtvt" class="srchCtvt layPopBtn w20p" type="button" data-target="hopeCtvtModal"><span>검색하기</span></button>
	             </div>
	         </div>
         </div><!-- //formBox -->
         <div class="formBox">
             <p>○ 귀농 시<strong>고려사항</strong>을 입력하세요.(<span class="sel">선택</span>)</p>
             <div id="cnsdrDiv" class="rowWrap">
	             <div class="row">
	             	<dl class="layPopBtn" data-level="U" data-target="cnsdrModal">
	             		<dt>상위고려사항</dt>
	             	</dl>
<!-- 	             	<input type="text" class="layPopBtn w100p" placeholder="상위 고려사항을 선택하세요." data-level="u" data-target="cnsdrModal" readonly value="asdfsadfasdfsadfsadfasdfsadfsadfasdfasfsadfsafasf가나다나나나나나가가ㅏ가나나다ㅏ아라아라아라"> -->
	             </div>
	             <div class="row">
	             	<dl class="layPopBtn" data-level="M" data-target="cnsdrModal">
	             		<dt>중위고려사항</dt>
	             	</dl>
<!-- 	             	<input type="text" class="layPopBtn w100p" placeholder="중위 고려사항을 선택하세요." data-level="m" data-target="cnsdrModal" readonly> -->
	             </div>
	             <div class="row">
	             	<dl class="layPopBtn" data-level="L" data-target="cnsdrModal">
	             		<dt>하위고려사항</dt>
	             	</dl>
<!-- 	             	<input type="text" class="layPopBtn w100p" placeholder="하위 고려사항을 선택하세요." data-level="l" data-target="cnsdrModal" readonly> -->
	             </div>
	         </div>
         </div><!-- //formBox -->
	<div class="resultBox btnWrap">
		<button type="button" class="showResultBtn"><span>결과보기</span></button>
	</div>
     </div><!-- //formWrap -->

    </section><!-- //cntWrap -->





<!--  하단 검색 영역 -->
    <!-- <div class="schWrap">
    	<div class="row">
    		<p class="w25p">귀농/귀촌/창업</p>
    		<input type="text" id="fixesSportSrchKwrd" placeholder="맞춤 지원정책 안내입니다. 검색 키워드를 입력하세요." class="w65p">
    		<button type="button" id="fixesSportSrchBtn" class="w10p"><span>검색</span></button>
    	</div>
    </div>

</div>//wrap

<form id="fixesSportSrchForm">
<input type='hidden' name='searchGbn' value='frmer'/>
<input type='hidden' name='searchKeyword' value=''/>
<input type='hidden' name='searchSportRelmCode' value='귀농/귀촌/창업'/>
</form> -->



<jsp:include page="./modal/retnFmlgModal.jsp"/>
