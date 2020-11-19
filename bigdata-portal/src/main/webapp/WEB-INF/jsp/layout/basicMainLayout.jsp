<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
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
<div id="wrap">
	<t:insertAttribute name="header" />
	<div id="container">
		
		<!-- S : body -->
		<div id="main">
			<t:insertAttribute name="body"/>
		</div>
		<!-- E : body -->

	</div>
	<t:insertAttribute name="footer"/>
</div>

</body>
</html>
