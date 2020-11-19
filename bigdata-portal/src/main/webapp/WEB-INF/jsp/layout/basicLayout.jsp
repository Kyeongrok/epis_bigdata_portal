<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<!-- <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests"> -->
<c:import url="/WEB-INF/jsp/layout/css_script.jsp" />
<title><t:insertAttribute name="title" ignore="true" /></title>

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
<div id="wrap" class="s_body">
	<t:insertAttribute name="header" />

	<div id="container">
		<!-- S : body -->
		<div id="sub">
			<!-- S : sub -->
			<t:insertAttribute name="left" />
			<div class="wrapper">
				<t:insertAttribute name="body"/>
				<a href="#wrap" class="to_top disnone"><span style="display:none;">맨위로</span></a>
			</div>
			<!-- E : sub -->

		</div>
		<!-- E : body -->
	</div>

	<t:insertAttribute name="footer"/>
</div>

<div id="loader" style="display:none; position: fixed; top: 0px; left: 0px; right:0px; bottom:0px; width:100%; height:100%; vertical-align: middle; text-align: center; z-index: 99999; background-color: rgba(128, 128, 128, 0.5);"></div>
</body>
</html>
