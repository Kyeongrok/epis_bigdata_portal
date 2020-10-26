<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/dmm/style.css"/>" />
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />"></script>
<style type="text/css">
	#floatMenu {position: fixed; right: 20px; top: 45px; color: #000; }
	#zoomin{font-weight: bold;}
	#zoomout{font-weight: bold; margin-left: 10px; margin-right: 10px;}
	.erd > img{zoom: 39%;}
	.erd > h3 {margin-top: 38px; margin-bottom: 38px; font-size: 43px;}
</style>
<title>ERD 이미지 확인</title>
</head>
<body>

	<c:if test="${modelType eq 'l'}">
	<div class="erd">	
		<c:forEach var="item" items="${erdImages}" varStatus="status">
			<c:if test="${status.index eq 0}">
				<h3>${item.metaSysNm} 논리모델</h3>
			</c:if>
			<img src="/images/bigdata/dmm/erd/${item.erdImgLogic}"/>			
		</c:forEach>
	</div>	
	</c:if>
	
	<c:if test="${modelType eq 'p'}">
	<div class="erd">	
	<c:forEach var="item" items="${erdImages}" varStatus="status">
		<c:if test="${status.index eq 0}">
			<h3>${item.metaSysNm} 물리모델</h3>
		</c:if>
		<img class="erd" src="/images/bigdata/dmm/erd/${item.erdImgPhysical}"/>
	</c:forEach>	
	</div>
	</c:if>	

	<div id="floatMenu">
		<span id="zoomin">
			<img src="/images/bigdata/dmm/zoom_in.png">
		</span>
		<span id=zoomout>
			<img src="/images/bigdata/dmm/zoom_out.png">
		</span>
		<span id=exit>
			<img src="/images/bigdata/dmm/exit.png">
		</span>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var isCloseWindow = "${empty erdImages}";
			
			if(isCloseWindow == 'true') {
				noImg();
			}
		});
		
		function noImg() {
			alert("등록된 ERD 이미지가 없습니다.");
			window.open("about:blank", "_self").close();
		}
	
		var zoom = 40;
		
		$('#zoomin').click(function(e) {		
			zoom += 10;						
			$('.erd > img').animate({'zoom': zoom+'%'}, 0);
			
			return false;
		});
		
		$('#zoomout').click(function(e) {
			
			if(zoom <= 40) {
				zoom = 39;
			} else {
				zoom -= 10;
			}
			
			$('.erd > img').animate({'zoom': zoom+'%'}, 0);
	
			return false;
		});
		
		$('#exit').click(function(e) {
			self.opener = self;
			window.close();
		});
		
	</script>
</body>
</html>