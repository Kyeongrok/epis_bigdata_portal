<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
귀농의사결정 지원 - 내가 입력한 정보 화면
2020.02.13
--%>

<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnFmlgMyAnswer.js'/>" defer></script>

<!-- 나의 입력 여건 -->
<div clas="myAnswerFixed">
        <section class="myAnswer">
        	<h3>내가 입력한 여건입니다.<br><span></span>&nbsp;</h3>
        	<!--
				항목의 열고/닫기 클릭 여부에 따라 myBox에 class='on' 있기/없기.
        	-->
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" id="updtInfoBtn" class="title layPopBtn" data-target="updtInfoModal"><span>본인정보</span><em>수정</em></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p"></span>
        		</div>
        		<div class="row">
        			<span class="w48p"></span>
        			<span class="w48p"></span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" id="updtLivtgtBtn" class="title layPopBtn" data-target="updtLivtgtModal"><span>동거가족</span><em>수정</em></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="rowWrap">
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" id="updtHopeArea" class="title layPopBtn" data-target="updtHopeAreaModal"><span>희망귀농지역</span><em>수정</em></button>
        			<button type="button" class="open"><span>열기/닫기</span></button>
        		</p>
        		<div class="row">
        			<!-- 답변 없는 경우 class="blank" -->
        			<span class="w100p blank"></span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" id="updtHopeCtvtBtn" class="title layPopBtn" data-target="updtHopeCtvtModal"><span>희망재배품목</span><em>수정</em></button>
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
        		<div class="exBox">
        			<div class="row">
        				<span class="w31p hig layPopBtn" data-level="U" data-target="cnsdrModal">상위수정</span>
	        			<span class="w31p mid layPopBtn" data-level="M" data-target="cnsdrModal">중위수정</span>
	        			<span class="w30p low layPopBtn" data-level="L" data-target="cnsdrModal">하위수정</span>
	        		</div>
        		</div>
        		<div class="row">
        			<span class="w48p mid blank" data-cd="1">교통</span>
        			<span class="w48p low blank" data-cd="2">의료</span>
        		</div>
        		<div class="row">
        			<span class="w48p low blank" data-cd="3">학교</span>
        			<span class="w48p mid blank" data-cd="4">교육</span>
        		</div>
        		<div class="row">
        			<span class="w48p low blank" data-cd="5">상업</span>
        			<span class="w48p hig blank" data-cd="6">편의</span>
        		</div>
        		<div class="row">
        			<span class="w48p mid blank" data-cd="7">문화</span>
        			<span class="w48p hig blank" data-cd="8">귀농지원정책</span>
        		</div>
        		<div class="row">
        			<span class="w48p hig blank" data-cd="9">농산물유통여건</span>
        			<span class="w48p low blank" data-cd="10">농지실거래가격</span>
        		</div>
        		<div class="row">
        			<span class="w48p hig blank" data-cd="11">농지임대가격</span>
        		</div>
<!--         		<div class="exBox"> -->
<!--         			<strong>범례</strong> -->
<!--         			<div class="row"> -->
<!--         				<span class="w31p hig layPopBtn" data-level="U" data-target="cnsdrModal">상위</span> -->
<!-- 	        			<span class="w31p mid layPopBtn" data-level="M" data-target="cnsdrModal">중위</span> -->
<!-- 	        			<span class="w30p low layPopBtn" data-level="L" data-target="cnsdrModal">하위</span> -->
<!-- 	        		</div> -->
<!--         		</div> -->
        	</div>
<!--         	<div class="btnWrap"> -->
<!--         		<button type="button"><span>결과보기</span></button> -->
<!--         	</div> -->
        </section><!-- // -->
</div><!-- //fixed -->
