<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="icon" type="image/png" href="../../images/bigdata/favicon.png">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/all.css" />" /> 
<link rel="stylesheet" type="text/css" href="<c:url value="/js/vendor/jquery-ui/jquery-ui.css" />" /> 
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/js/vendor/font-awesome/css/font-awesome.min.css" />" /> -->

<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/vendor/jquery-ui/jquery-ui.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/vendor/jquery-tmpl/jquery.tmpl-1.0.0.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.spin.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/common.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/placeholders.min.js" />" ></script>

<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>

<script type="text/javascript">
		
	$(window).load(function() {
		
		// refresh page fadein
		$("#wrap").addClass("fadein");
	});
	
	$(document).ready(function() {
		// sub header
		$(".s_body").ready(function() {
			$(".s_body #gnb").css('background-color', 'rgba(255,255,255,1)');
		});
		
		//header scroll background
		$(window).scroll(function() {
			var scroll = $(window).scrollTop();

			if (scroll >= 600) {
				$("#gnb").addClass("hover");
			} if (scroll >= 300) {
				$(".to_top").removeClass("disnone");
			} else {
				$("#gnb").removeClass("hover");
				$(".to_top").addClass("disnone");
			}
		});

		// mobile menu
		$("#ico_bar").click(function() {
			$("#wrap").toggleClass("y_scroll");
		});
		
		// toggle tooltip
		$(".t_tooltip").click(function() {
			$(".t_tooltip > span").fadeToggle('fast');
		});
		
		// datamap tree table
		$(".treetbl a").click(function() {

			$(this).parent().siblings().find("a").removeClass("active");				
			$(this).addClass("active");	

			$(this).siblings("ul").children("li:eq(0)").children("a").click();
		});
		$(".datamap_tree .step1 > li:first-child > a").click();

	});
</script>
	
	
	