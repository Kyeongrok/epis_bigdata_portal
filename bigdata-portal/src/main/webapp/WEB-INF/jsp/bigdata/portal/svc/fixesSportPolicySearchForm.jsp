<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <title></title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/svc/fixesSportPolicy/css/reset.css'/>">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/svc/fixesSportPolicy/css/layout.css'/>">

	<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>
	
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/fixesSportPolicySearchForm.js'/>"></script>

</head>

<body>
    <div id="skipnavigation"><a href="#main_content">본문으로 바로가기</a> <a href="">메뉴로 바로가기</a></div>
    <div class="header_area">
        <div class="header_box">
            <h1><a herf="#"><img src="<c:url value='/resources/svc/fixesSportPolicy/img/logo.png'/>" alt="로고"></a></h1>

        </div>
    </div>
    <div id="main_content" class="main_cont">
        <div class="top_box">
            <h2>맞춤 <b>지원정책</b> 안내 서비스</h2>
        </div>

        <!--search_form-->
        <div class="sch_area">
            <h3>맞춤 <b>지원정책</b> 안내 서비스에 오신것을 환영합니다.</h3>
            <p>맞춤 <b>지원정책</b> 안내 서비스는 여러분들의 여건에 맞는 지원정책을 안내 해드리는 검색서비스입니다. </p>

            <div class="tab_area">
                <div class="tab_inner">
                    <button class="tablinks" onclick="openCity(event, 'famer')" id="defaultOpen" data-gbn="frmer">농업인</button>
                    <button class="tablinks" onclick="openCity(event, 'lowfirm')" data-gbn="cpr">법인</button>
                    <button class="tablinks" onclick="openCity(event, 'be_famer')" data-gbn="notFrmer">비농업인</button>
                </div>
                <div id="famer" class="tabcontent">
                    <div class="sch_form">
                        <label for="" class="hdn">검색어입력</label>
                        <input type="search" name="searchKeyword" class="frmer" placeholder="원하는 검색 키워드를 입력하세요." autofocus="autofocus">

                        <div class="sch_btn">
                            <button class="frmer search"><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                </div>
                <div id="lowfirm" class="tabcontent">
                    <div class="sch_form">
                        <label for="" class="hdn">검색어입력</label>
                        <input type="search" name="searchKeyword" class="cpr" placeholder="원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button class="cpr search"><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                </div>
                <div id="be_famer" class="tabcontent">
                    <div class="sch_form">
                        <label for="" class="hdn">검색어입력</label>
                        <input type="search" name="searchKeyword" class="notFrmer" placeholder="원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button class="notFrmer search"><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </div>
    <div class="ico_nav">
        <ul>
            <li class="ico_menu01"><a href="#" title="">바로가기</a></li>
            <li class="ico_menu02"><a href="#" title="">바로가기</a></li>
            <li class="ico_menu03"><a href="#" title="">바로가기</a></li>
        </ul>
    </div>


    <!--tab_menu를 위한-->
    <script>
        function openCity(evt, tabname) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(tabname).style.display = "block";
            evt.currentTarget.className += " active";
        }

        // Get the element with id="defaultOpen" and click on it
        document.getElementById("defaultOpen").click();
    </script>


</body>

</html>