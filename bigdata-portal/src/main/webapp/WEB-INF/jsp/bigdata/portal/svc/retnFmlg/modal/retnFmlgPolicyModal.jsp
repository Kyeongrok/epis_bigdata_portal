<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!-- popup 정책 -->

<%-- <link href="<c:url value='/css/bigdata/retnFmlg/modal/reset.css'/>" --%>
<!-- 	rel="stylesheet" type="text/css" media="all"> -->
<link href="<c:url value='/css/bigdata/retnFmlg/modal/policy.css'/>"
	rel="stylesheet" type="text/css" media="all">

<script type="text/javascript"	src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript"	src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />"></script>

<script	src="<c:url value='/js/bigdata/portal/svc/retnFmlg/modal/retnFmlgPolicyModalObj.js'/>"></script>

<!-- <a href="javascript:void(0)" id="btn_popup_open">상세보기</a> -->
<!-- popup 정책 -->
<div class="popup_panel">
	<div class="popup_bg"></div>
	<div class="popup_contents">
		<p class="pop_tit">맞춤 지원 정책</p>
		<a href="javascript:void(0)" id="btn_popup_close">x</a>
		<div class="pop_wrap">
			<div class="tab_menu_area pdNone">
				<ul>
					<!-- 탭 목록 crated by js -->
				</ul>
			</div>

			<!-- bsns list -->
			<div class="table_area list pdNone">
				<ul>
					<!-- 사업 목록 created by js -->
				</ul>

				<!-- 페이징 -->
				<div class="page_area">
					<!-- created by js -->
				</div>
			</div>
			<!-- //bsns list -->

			<!-- bsns detail -->
			<div class="table_area detail pdNone">
			</div>
			<!-- //bsns detail -->
		</div>
	</div>
	<!-- // -->
</div>
<!-- //popup 정책 -->


