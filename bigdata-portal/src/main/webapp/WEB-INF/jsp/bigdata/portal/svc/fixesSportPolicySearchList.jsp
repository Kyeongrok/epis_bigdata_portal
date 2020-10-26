<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="csTag" uri="http://www.ucsit.co.kr/tlds/csTag.tld"%>
<%--
맞춤 지원 정책
0207
--%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <title>맞춤 지원 정책서비스</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta name="HandheldFriendly" content="true">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/svc/fixesSportPolicy/css/reset.css'/>">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/svc/fixesSportPolicy/css/layout.css'/>">

	<script src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
	<script src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>
	<script src="<c:url value="/js/bigdata/portal/jquery.loading.min.js" />" ></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js"></script>
	<%-- <script src="<c:url value="/js/bigdata/portal/chartjs-plugin-labels.js" />"></script> --%>
	
	<script src="<c:url value='/js/bigdata/portal/svc/fixesSportPolicySearchList.js'/>" defer></script>
	<script src="<c:url value='/js/bigdata/portal/svc/fixesSportPolicyDataObj.js'/>" defer></script>
	<script src="<c:url value='/js/bigdata/portal/svc/fixesSportPolicyDetailObj.js'/>" defer></script>
	<script src="<c:url value='/js/bigdata/portal/svc/fixesSportPolicyStatsObj.js'/>" defer></script>
	<script src="<c:url value='/js/bigdata/portal/svc/similrAtmnentObj.js'/>" defer></script>
	<script src="<c:url value='/js/bigdata/portal/svc/quick.js'/>"></script>

</head>

<body>
    <div id="skipnavigation"><a href="#sub_content">본문으로 바로가기</a> <a href="">메뉴로 바로가기</a></div>
    <div class="header_area">
        <div class="header_box">
            <h1><a href="javascript:;"><img src="<c:url value='/resources/svc/fixesSportPolicy/img/logo.png'/>" alt="로고"></a></h1>

        </div>
    </div>
    <div id="sub_content" class="sub_cont">
        <div class="sub_top_box">
            <h2>맞춤 <b>지원정책</b> 안내 서비스</h2>
        </div>

        <!--search_form-->
        <div class="sub_sch_area">
            <h3>맞춤 <b>지원정책</b> 안내 서비스에 오신것을 환영합니다.</h3>
            <p>맞춤 <b>지원정책</b> 안내 서비스는 여러분들의 여건에 맞는 지원정책을 안내 해드리는 검색서비스입니다. </p>

            <div class="tab_area">
                <div class="tab_inner">
                    <button class="tablinks active" onclick="openCity(event, 'all')" data-gbn="all" >전체</button>
                    <button class="tablinks" onclick="openCity(event, 'famer')" data-gbn="frmer">농업인</button>
                    <button class="tablinks" onclick="openCity(event, 'lowfirm')" data-gbn="cpr">법인</button>
                    <button class="tablinks" onclick="openCity(event, 'be_famer')" data-gbn="notFrmer">비농업인</button>
                </div>
                
                <!-- 전체 -->
                <div id="all" class="tabcontent all" data-gbn="all">
                    <div class="sch_form">
                        <label for="all_searchKeyword" class="hdn">검색어입력</label>
                        <input type="hidden" name="searchSportRelmCode" value="${paramMap.searchSportRelmCode }"/>
                        <input type="hidden" name="searchGbn" value="${paramMap.searchGbn }"/>
                        <input type="search" class="all" id="all_searchKeyword" name="searchKeyword" value="${paramMap.searchKeyword }" placeholder="원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button class="all btnSearch"><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                    <div id="quick_all" class="widget_area">
                        <div class="widget_tit mo_btn">필터를 입력하세요 <span></span></div>
                        <div class="filter_modal widget_inner">
                            <div class="mo_widget_tit">필터를 입력하세요 <span class="mo_close">&times;</span></div>

                            <ul>
                                <li><a href="javascript:;">지원영역</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="all_sportRelmCode" class="only-sr checked all" type="checkbox" name="sportRelmCode"  value="" checked>
                                                    <label for="all_sportRelmCode">전체</label>
                                                </div>
                                            </div>
                                            
                                            <c:forEach items="${sportRelmCodes }" var="d" varStatus="status">
	                                            <div class="col_half">
	                                                <div class="radio-items">
	                                                    <input id="all_sportRelmCode_SR_${status.index }" class="only-sr all" type="checkbox" name="sportRelmCode" value="${d.code }">
	                                                    <label for="all_sportRelmCode_SR_${status.index }" style="font-size:12px;" >${d.codeNm }</label>
	                                                </div>
	                                            </div>
                                            </c:forEach>

                                        </li>
                                    </ul>
                                </li>
                                
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- //전체 -->
                
                <!-- 농업인 -->
                <div id="famer" class="tabcontent frmer" data-gbn="frmer">
                    <div class="sch_form">
                        <label for="frmer_searchKeyword" class="hdn">검색어입력</label>
                        <input type="hidden" name="searchSportRelmCode" value="${paramMap.searchSportRelmCode }"/>
                        <input type="hidden" name="searchGbn" value="${paramMap.searchGbn }"/>
                        <input type="search" class="frmer" id="frmer_searchKeyword" name="searchKeyword" value="${paramMap.searchKeyword }" placeholder="원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button class="btnSearch"><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                    <div id="quick_frmer" class="widget_area">
                        <div class="widget_tit mo_btn">필터를 입력하세요 <span></span></div>
                        <div class="filter_modal widget_inner">
                            <div class="mo_widget_tit">필터를 입력하세요 <span class="mo_close">&times;</span></div>

                            <ul>
                                <li><a href="javascript:;">지원영역</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="frmer_sportRelmCode" class="only-sr checked frmer" type="checkbox" name="sportRelmCode"  value="" checked>
                                                    <label for="frmer_sportRelmCode">전체</label>
                                                </div>
                                            </div>
                                            
                                            <c:forEach items="${sportRelmCodes }" var="d" varStatus="status">
	                                            <div class="col_half">
	                                                <div class="radio-items">
	                                                    <input id="frmer_sportRelmCode_SR_${status.index }" class="only-sr frmer" type="checkbox" name="sportRelmCode" value="${d.code }">
	                                                    <label for="frmer_sportRelmCode_SR_${status.index }" style="font-size:12px;" >${d.codeNm }</label>
	                                                </div>
	                                            </div>
                                            </c:forEach>

                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">성별</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input class="only-sr checked frmer" type="checkbox" id="frmer_sexdstn" name="sexdstn"  value="" checked>
                                                    <label for="frmer_sexdstn">전체</label>
                                                </div>
                                            </div>
                                            <c:forEach items="${sexdstns }" var="d" varStatus="status">
	                                            <div class="col_half">
	                                                <div class="radio-items">
	                                                    <input class="only-sr checked frmer" type="checkbox" id="frmer_sexdstn_${status.index }" name="sexdstn"  value="${d.code }">
	                                                    <label for="frmer_sexdstn_${status.index }">${d.codeNm }</label>
	                                                </div>
	                                            </div>
                                            </c:forEach>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">나이</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="frmer_age" class="hdn">검색어입력</label>
                                                <input id="frmer_age" type="number" class="frmer" name="age" placeholder="나이를 입력하세요." ><span>세</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">지원 지역</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="frmer_sidoCode" class="hdn">검색어입력</label>
                                                <csTag:select
                                                	id="frmer_sidoCode"
                                                	name="sidoCode"
                                                	dataSource="${sidoCodes }"
                                                	tKey="sidoName"
                                                	vKey="sidoName"
                                                	headerText="ALL"
                                                	css="frmer" />
                                                <!-- <input id="frmer_residence" type="text" class="frmer" name="residence" placeholder="시군구를 입력하세요." ><span></span> -->
                                            </div>
                                            <div class="radio-items">
                                                <label for="frmer_sigunguCode" class="hdn">검색어입력</label>
                                                <select id="frmer_sigunguCode" name="sigunguCode" class="frmer">
                                                	<option value="">전체</option>
                                                </select>
                                                <!-- <input id="frmer_residence" type="text" class="frmer" name="residence" placeholder="시군구를 입력하세요." ><span></span> -->
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">영농경력</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="frmer_farmCareer" class="hdn">검색어입력</label>
                                                <input id="frmer_farmCareer" type="number" class="frmer" name="farmCareer" placeholder="영농경력을 입력하세요."><span>년</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">재배(사육)품목군</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="frmer_ctvtPrdlstSetCode" class="hdn">검색어입력</label>
                                                <csTag:select
                                                	id="frmer_ctvtPrdlstSetCode"
                                                	name="ctvtPrdlstSetCode"
                                                	dataSource="${prdlstSetCodes }"
                                                	tKey="lclasNm"
                                                	vKey="lclasCode"
                                                	headerText="ALL"
                                                	css="frmer" />
                                                <!-- <input id="frmer_ctvtPrdlstSetCode" type="text" class="frmer" name="ctvtPrdlstSetCode" placeholder="취급품목군을 입력하세요."><span></span> -->
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">재배(사육)품목</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="frmer_ctvtPrdlstCode" class="hdn">검색어입력</label>
                                                <select id="frmer_ctvtPrdlstCode" name="ctvtPrdlstCode" class="frmer">
                                                	<option value="">전체</option>
                                                </select>
                                                <!-- <input id="frmer_ctvtPrdlstCode" type="text" class="frmer" name="ctvtPrdlstCode" placeholder="취급품목을 입력하세요."><span></span> -->
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">면적(두수)</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="frmer_arOrCo" class="hdn">검색어입력</label>
                                                <input type="number" id="frmer_arOrCo" name="arOrCo" class="frmer" style="width:80%;">
                                                <span style="width:40px">㎡(두수)</span>
                                                <!-- <input id="frmer_ctvtPrdlstCode" type="text" class="frmer" name="ctvtPrdlstCode" placeholder="취급품목을 입력하세요."><span></span> -->
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">재배유형</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="frmer_ctvtTyCode" class="hdn">검색어입력</label>
                                                <csTag:select  
                                                	id="frmer_ctvtTyCode"
                                                	name="ctvtTyCode"
                                                	dataSource="${ctvtTyCodes }"
                                                	tKey="ctvtTyName" 
                                                	vKey="ctvtTyName" 
                                                	headerText="ALL"
                                                	css="frmer" 	/>
                                                <!-- <input id="frmer_ctvtTyCode" type="text" class="frmer" name="ctvtTyCode" placeholder="재배유형을 입력하세요."><span></span> -->
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- //농업인 -->
                
                <!-- 법인 -->
                <div id="lowfirm" class="tabcontent cpr" data-gbn="cpr">
                    <div class="sch_form">
                        <label for="cpr_searchKeyword" class="hdn">검색어입력</label>
                        <input type="search" class="cpr" id="cpr_searchKeyword" name="searchKeyword" value="${paramMap.searchKeyword }" placeholder="원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button class="btnSearch"><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                    <div id="quick_cpr" class="widget_area">
                        <div class="widget_tit mo_btn">필터를 입력하세요 <span></span></div>
                        <div class="filter_modal widget_inner">
                            <div class="mo_widget_tit">필터를 입력하세요 <span class="mo_close">&times;</span></div>

                            <ul>
                                <li><a href="javascript:;">지원영역</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="cpr_sportRelmCode" class="only-sr checked cpr" type="checkbox" name="sportRelmCode"  value="" checked>
                                                    <label for="cpr_sportRelmCode">전체</label>
                                                </div>
                                            </div>
                                            <c:forEach items="${sportRelmCodes }" var="d" varStatus="status">
	                                            <div class="col_half">
	                                                <div class="radio-items">
	                                                    <input id="cpr_sportRelmCode_SR_${status.index }" class="only-sr cpr" type="checkbox" name="sportRelmCode" value="${d.code }">
	                                                    <label for="cpr_sportRelmCode_SR_${status.index }" style="font-size:12px;" >${d.codeNm }</label>
	                                                </div>
	                                            </div>
                                            </c:forEach>                                            
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">지원유형</a>
                                    <ul>
                                        <li>
                                        	<c:forEach items="${sportTyCodes }" var="d" varStatus="status">
	                                            <div class="col_half">
	                                                <div class="radio-items">
	                                                    <input id="cpr_sportTyCode_${status.index }" class="only-sr cpr <c:if test="${0 eq status.index }">checked</c:if>" type="checkbox" name="sportTyCode" value="${d.code }" <c:if test="${0 eq status.index }"> checked </c:if> >
	                                                    <label for="cpr_sportTyCode_${status.index }">${d.codeNm }</label>
	                                                </div>
	                                            </div>
                                        	</c:forEach>
                                            
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">지원주체</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="cpr_sportMbyCode_SM1" class="only-sr checked cpr" type="checkbox" name="sportMbyCode" value="중앙정부" checked>
                                                    <label for="cpr_sportMbyCode_SM1">중앙정부</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="cpr_sportMbyCode_SM2" class="only-sr cpr" type="checkbox" name="sportMbyCode" value="지방정부">
                                                    <label for="cpr_sportMbyCode_SM2">지방정부</label>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">설립연도</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_fondYear" class="hdn">검색어입력</label>
                                                <input id="cpr_fondYear" type="number" class="cpr" name="fondYear"  placeholder="설립연도를 입력하세요."><span>년</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">자본금(만원)</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_capl" class="hdn">검색어입력</label>
                                                <input id="cpr_capl" type="number" class="cpr" name="capl"  placeholder="자본금을 입력하세요."><span>만원</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">출자자 수</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_investorCo" class="hdn">검색어입력</label>
                                                <input id="cpr_investorCo" type="number" class="cpr" name="investorCo"  placeholder="출자자수를 입력하세요."><span>명</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <%-- <li><a href="javascript:;">소재지</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_sidoCode" class="hdn">시도 선택</label>
                                                <select id="cpr_sidoCode" name="sidoCode">
                                                    <option value="" selected="selected">-전체-</option>
                                                    <c:forEach items="${sidoCodes }" var="v">
	                                                    <option value="${v.sidoCode }" >${v.sidoName }</option>
                                                    </c:forEach>
                                                </select>
                                                <label for="cpr_sigunguCode" class="hdn">시군구 선택</label>
                                                <select id="cpr_sigunguCode" name="sigunguCode">
                                                    <option value="" selected="selected">-전체-</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </li> --%>
                                <li><a href="javascript:;">주 취급품목군</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_ctvtPrdlstSetCode" class="hdn">검색어입력</label>
                                                <csTag:select
                                                	id="cpr_ctvtPrdlstSetCode"
                                                	name="ctvtPrdlstSetCode"
                                                	dataSource="${prdlstSetCodes}"
                                                	tKey="lclasNm"
                                                	vKey="lclasCode"
                                                	headerText="ALL"
                                                	css="cpr"	/>
                                                <!-- <input id="cpr_ctvtPrdlstSetCode" type="text" class="cpr" name="ctvtPrdlstSetCode" placeholder="주 취급품목군을 입력하세요."><span></span> -->
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">주 취급품목</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_ctvtPrdlstCode" class="hdn">검색어입력</label>
                                                <select id="cpr_ctvtPrdlstCode" name="ctvtPrdlstCode" class="cpr">
                                                	<option value="">전체</option>
                                                </select>
                                                <!-- <input id="cpr_ctvtPrdlstCode" type="text" class="cpr" name="ctvtPrdlstCode" placeholder="주 취급품목을 입력하세요."><span></span> -->
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">취급규모(톤)</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_trtmntScale" class="hdn">검색어입력</label>
                                                <input id="cpr_trtmntScale" type="number" class="cpr" name="trtmntScale"  placeholder="취급규모를 입력하세요."><span>톤</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">매출액(만원)</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="cpr_selngAmount" class="hdn">검색어입력</label>
                                                <input id="cpr_selngAmount" type="number" class="cpr" name="selngAmount" placeholder="매출액을 입력하세요."><span>만원</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- //법인 -->
                
                <!-- 비농업인 -->
                <div id="be_famer" class="tabcontent notFrmer" data-gbn="notFrmer">
                    <div class="sch_form">
                        <label for="notFrmer_searchKeyword" class="hdn">검색어입력</label>
                        <input type="search" class="notFrmer" id="notFrmer_searchKeyword" name="searchKeyword" value="${paramMap.searchKeyword }" placeholder="원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button class="btnSearch"><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                    <div id="quick_notFrmer" class="widget_area">
                        <div class="widget_tit mo_btn">필터를 입력하세요 <span></span></div>
                        <div class="filter_modal widget_inner">
                            <div class="mo_widget_tit">필터를 입력하세요 <span class="mo_close">&times;</span></div>

                            <ul>
                                <li><a href="javascript:;">성별</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="notFrmer_sexdstn" class="only-sr checked notFrmer" type="checkbox" name="sexdstn" value="" checked>
                                                    <label for="notFrmer_sexdstn">전체</label>
                                                </div>
                                            </div>
                                            <c:forEach items="${sexdstns }" var="d" varStatus="status">
	                                            <div class="col_half">
	                                                <div class="radio-items">
	                                                    <input id="notFrmer_sexdstn_${status.index }" class="only-sr notFrmer" type="checkbox" name="sexdstn" value="${d.code }" >
	                                                    <label for="notFrmer_sexdstn_${status.index }">${d.codeNm }</label>
	                                                </div>
	                                            </div>
                                            </c:forEach>
                                            
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">나이</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="notFrmer_age" class="hdn">검색어입력</label>
                                                <input id="notFrmer_age" class="notFrmer" type="number" name="age" placeholder="나이를 입력하세요."><span>세</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">지원 지역</a>
                                    <ul>
                                        <li>
                                        	<div class="radio-items">
                                                <label for="notFrmer_sidoCode" class="hdn">검색어입력</label>
                                                <csTag:select
                                                	id="notFrmer_sidoCode"
                                                	name="sidoCode"
                                                	dataSource="${sidoCodes }"
                                                	tKey="sidoName"
                                                	vKey="sidoName"
                                                	headerText="ALL"
                                                	css="notFrmer"	/>
                                                <!-- <input id="notFrmer_residence" class="notFrmer" type="text" name="residence" placeholder="시군구를 입력하세요."><span></span> -->
                                            </div>
                                        	<div class="radio-items">
                                                <label for="notFrmer_sigunguCode" class="hdn">검색어입력</label>
                                                <select id="notFrmer_sigunguCode" name="sigunguCode" class="notFrmer">
                                                	<option value="">전체</option>
                                                </select>
                                                <%-- <input id="notFrmer_residence" class="notFrmer" type="text" name="residence" placeholder="시군구를 입력하세요."><span></span> --%>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="javascript:;">농업계 학교 출신여부</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="notFrmer_farmngSchulOrginAt" class="hdn">농업계 학교 출신여부선택</label>
                                                <select id="notFrmer_farmngSchulOrginAt" name="farmngSchulOrginAt" class="notFrmer">
                                                    <option value="" selected="selected">농업계 학교 출신여부를 선택하세요</option>
                                                    <c:forEach items="${farmngSchulOrginAts }" var="d">
	                                                    <option value="${d.code }">${d.codeNm }</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
				<!-- 비농업인 -->

            </div>
            <div class="tab_menu_area">
                <ul>
                    <li class="fir_ch">전체<b>37</b></li>
                    <li>경영안전지원<b>20</b></li>
                    <li>교육컨설팅<b>17</b></li>
                    <li>유사농업경영체<b>3</b></li>
                </ul>
            </div>

            <div class="result_area" style="">
                <ul>
                    <li><a href="javascript:;"><b>귀하</b>께서 검색하신 결과를 요약하여 아래와 같이 알려드립니다.</a>
                        <ul style="display:block;">
                            <li>
                            	<p>
                            		▶ 지원 가능 정책 : <b><span class="sportPosblPolicyCount">0</span></b>건 
                            		<span class="similr" style="">| 유사농업 경영체 : <b><span class="similrAtmnentCount">0</span></b>건</span>
                            	</p>
                            	<p>
                            		▶ 최대 지원 가능 금액 : <b><span class="sumAmount" >0</span></b> <span class="sumAmountUnit">백만원</span> 
                            		<span style="margin-left:10px;color:red;font-style: italic;">※ 이 금액은 확정된 금액이 아니며 신청된 지원정책금에 따라 달라질 수 있습니다.</span>                            	
                            	<!-- <p>▶ 검색하신 정책 또는 유사 정책으로 유사농업 경영체가 수혜받은 평균 지원금은 <b><span class="avgAmount">0</span></b>원 입니다. -->
                            	</p>
                            </li>
                        </ul>
                    </li>
                </ul>

            </div>
            <!-- list -->
            <div class="table_area list">
                <ul>
                    <!--lineStart-->
                    <!-- created by js -->
                    <!--//-->
              	</ul>
                    
                    
                <!-- 페이징 -->    
                <div class="page_area">
                    <!-- created by js -->
                </div>
            </div>
        	<!-- //list -->
        
        
        	<!-- detail -->
        	<div class="table_area detail bsnsSumry" style="display:none;">
        	 	<ul>
	                <li class="bdNone">
	                    <div class="article_area">
	                    	
	                    	<input type="hidden" name="lclasNmCode" />	<%--사업 코드 --%>
	                    	<input type="hidden" name="mlsfcNmCode" />	<%--세부 사업 코드 --%>
	                    	<input type="hidden" name="bsnsCode" />	<%--아그리스 사업 코드 --%>
	                        <p class="big_tit"><span class="MLSFC_NM"></span> <span class="DETAIL_BSNS_NM"></span></p>
	
	                        <div class="keyword_item flNone cl">
	                            <button type="button" class="item_01"><span class="SPORT_SUB"></span></button>
	                            <button type="button" class="item_02"><span class="sportTarget"></span></button>
	                            <button type="button" class="item_03"><span class="SPORT_STLE"></span></button>
	                            <button type="button" class="item_04"><span class="SPORT_AREA"></span></button>
	                        </div>
	                        <p class="s_txt cl"><span class="BSNS_CN"></span></p>
	                        <button type="button" class="go_btn flr btnDwld" style="right:140px !important;">지침서 내려받기</button>
	                        <button type="button" class="go_btn flr btnList">목록으로</button>
	                    </div>
	                </li>
	            </ul>
        	</div>
        	
        	<div class="table_area02 detail sportCnd" style="display:none;">
	            <div class="article_area frmer">
	                <h4>지원조건(농업인)</h4>
	                <ul class="col_four">
                        <!-- created by js -->
	                </ul>
	            </div>
	            
	            <div class="article_area cpr"  style="display:none">
	                <h4>지원조건(법인)</h4>
	                <ul class="col_four">
                        <!-- created by js -->
	                </ul>
	          	</div>
	          	
	            <div class="article_area notFrmer" style="">
	                <h4>지원조건(비농업인)</h4>
	                <ul class="col_four">
                        <!-- created by js -->
	                </ul>
	          	</div>
	        </div>
	       
	        
	        
	        <div class="table_area detail" style="display:none;">
	            <div class="article_area">
	                <h4>기타 제한요건</h4>
	                <ul class="list_type">
	                    <li><!-- created by js --></li>
	                </ul>
	            </div>
	        </div>
	        
	        <div class="table_area detail bsnsCn" style="display:none;">
	            <div class="article_area">
	                <h4>사업내용</h4>
	                <ul class="list_type_half">
	                    <li><!-- created by js --></li>
	                </ul>
	            </div>
	        </div>
	        
	        <div class="table_area detail sportCn" style="display:none;">
	            <div class="article_area">
	                <h4>지원내용</h4>
	                <ul class="list_type_half">
	                    <li><!-- created by js --></li>
	                </ul>
	            </div>
	        </div>
	        
	        <div class="table_area02 detail etcEssntlCond" style="display:none;">
	            <div class="article_area">
	                <h4>기타 필수 요건</h4>
	                <ul class="list_type">
	                    <li><!-- created by js --></li>
	                </ul>
	            </div>
	        </div>
	        
	        <div class="table_area02 detail etcLmttCond" style="display:none;">
	            <div class="article_area">
	                <h4>기타 제한 요건</h4>
	                <ul class="list_type">
	                    <li><!-- created by js --></li>
	                </ul>
	            </div>
	        </div>
	        
	        <div class="table_area02 detail pvltrtCnd" style="display:none;">
	            <div class="article_area">
	                <h4>우대조건</h4>
	                <ul class="list_type_half">
	                    <li><!-- created by js --></li>
	                    
	                </ul>
	            </div>
	        </div>
	        
	        
	        <div class="table_area02 detail contact" style="display:none;">
	            <div class="article_area">
	                <h4>담당자</h4>
	                <ul class="list_type_half">
	                    <li><!-- created by js --></li>
	                    
	                </ul>
	            </div>
	        </div>
	        
	        <div class="title_box detail similrBsns" style="display:none;">유사사업
	            <p>밭농업직접지불보조금, 쌀소득보전고정직불(고정직불금), 경관보전직불</p>
	        </div>
	        
	        
	        <div class="table_area detail stats" style="display:none;">
	            <div class="article_area">
	                <h4>통계보기</h4>
	                <div class="graph_area col_half stats byYear" style="margin-right:20px;width:350px;height:300px !important;min-height:375px !important;display:none;">
		                <!-- <h5 class="big_tit">년도별</h5> -->
	                	<canvas width="350" height="300" id="chartByYear" style="width:350px;height:300px;"></canvas>
	                </div>
	                
	                <div class="graph_area col_half stats byAge" style="margin-right:20px;width:350px;height:300px !important;min-height:375px !important;display:none;">
	                	<!-- <h5 class="big_tit">연령대별</h5> -->
	                	<canvas width="350" height="300" id="chartByAge" style="width:350px;height:300px;"></canvas>
	                </div>
	                
	                <div class="graph_area col_half stats byPrdlstSet" style="margin-right:20px;width:350px;height:300px !important;min-height:375px !important;display:none;">
	               	 	<!-- <h5 class="big_tit">품목군별</h5> -->
	                	<canvas width="350" height="300" id="chartByPrdlstSet" style="width:350px;height:300px;"></canvas>
	                </div>
	                
	                <div class="graph_area col_half stats bySido" style="margin-right:20px;width:350px;height:300px !important;min-height:375px !important;display:none;">
		                <!-- <h5 class="big_tit">지역별(시도)</h5> -->
	                	<canvas width="350" height="300" id="chartBySido" style="width:350px;height:300px;"></canvas>
	                </div>
	                
	                <div class="graph_area col_half stats bySigungu" style="margin-right:20px;width:350px;height:300px !important;min-height:375px !important;display:none;">
	                	<!-- <h5 class="big_tit">
	                		지역별(시군구)
	                		<button type="button" class="sigungu back">&lt;뒤로이동</button>
	                	</h5> -->
	                	
	                	<canvas width="350" height="300" id="chartBySigungu" style="width:350px;height:300px;"></canvas>
	                </div>
	            </div>
	        </div>
	        
	        <div class="table_area detail back" style="display:none;">
	        	<button type="button" class="go_btn btnList">목록으로</button>
	        </div>
	        
        	<!-- detail -->
        </div>
    </div>



    <!-- <div class="ico_nav">
        <ul>
            <li class="ico_menu01"><a href="javascript:;" title="">바로가기</a></li>
            <li class="ico_menu02"><a href="javascript:;" title="">바로가기</a></li>
            <li class="ico_menu03"><a href="javascript:;" title="">바로가기</a></li>
        </ul>
    </div> -->

	
	
	<%--전체 시군구 목록 --%>
	<div style="display:none">
		<c:forEach items="${sigunguCodes }"	 var="d">
			<div class="sigunguCodes" data-sido-code="${d.sidoCode }" data-sido-name="${d.sidoName }" data-sigungu-code="${d.sigunguCode }" >${d.sigunguName }</div>
		</c:forEach>
	</div>
	
	
	<%--품목 --%>
	<div style="display:none">
		<c:forEach items="${prdlstCodes }"	 var="d">
			<div class="prdlstCodes" data-prdlst-set-code="${d.lclasCode }" data-prdlst-set-name="${d.lclasNm }" data-prdlst-code="${d.prdlstCode }">${d.sclasNm }</div>
		</c:forEach>	
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
        //document.getElementById("defaultOpen").click();


    </script>



    <!--radio버튼을 위한-->
    <script>
        function hasClass(target, className) {
            if ((' ' + target.className + ' ').replace(/[\n\t]/g, ' ').indexOf(' ' + className + ' ') > -1) return true;
            return false;
        }
        function removeClass(target, className) {
            var elClass = ' ' + target.className + ' ';
            while (elClass.indexOf(' ' + className + ' ') !== -1) {
                elClass = elClass.replace(' ' + className + ' ', '');
            }
            target.className = elClass;
        }
        function addClass(target, className) {
            target.className += ' ' + className;
        }

        if (hasClass(document.getElementsByTagName('html')[0], 'ie8')) { // ie8 일 경우
            var radios = document.querySelectorAll('input[type="radio"]'),
                i,
                len = radios.length;

            for (i = 0; i < len; i++) {
                radios[i].attachEvent('onchange', function (e) {
                    var siblingsChecked = this.parentNode.parentNode.querySelector('.checked'); // 이전 checked 버튼

                    removeClass(siblingsChecked, 'checked'); // checked 삭제
                    addClass(this, 'checked'); // checked 부여
                });
            }
        }
    </script>
    
    

    
    

    <!--mobile_modal창을 위한-->
    <script>
        // Get the modal

        var modalparent = document.getElementsByClassName("filter_modal");

        // Get the button that opens the modal

        var modal_btn_multi = document.getElementsByClassName("mo_btn");

        // Get the <span> element that closes the modal
        var span_close_multi = document.getElementsByClassName("mo_close");

        // When the user clicks the button, open the modal
        function setDataIndex() {

            for (i = 0; i < modal_btn_multi.length; i++) {
                modal_btn_multi[i].setAttribute('data-index', i);
                modalparent[i].setAttribute('data-index', i);
                span_close_multi[i].setAttribute('data-index', i);
            }
        }



        for (i = 0; i < modal_btn_multi.length; i++) {
            modal_btn_multi[i].onclick = function () {
                var ElementIndex = this.getAttribute('data-index');
                modalparent[ElementIndex].style.display = "block";
            };

            // When the user clicks on <span> (x), close the modal
            span_close_multi[i].onclick = function () {
                var ElementIndex = this.getAttribute('data-index');
                modalparent[ElementIndex].style.display = "none";
            };

        }

        window.onload = function () {

            setDataIndex();
        };

        window.onclick = function (event) {
            if (event.target === modalparent[event.target.getAttribute('data-index')]) {
                modalparent[event.target.getAttribute('data-index')].style.display = "none";
            }

            // OLD CODE
            if (event.target === modal) {
                modal.style.display = "none";
            }
        };

        // Get the modal
        var modal = document.getElementById("filter_modal");

        // Get the button that opens the modal
        var btn = document.getElementById("mo_btn");

        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("mo_close")[0];

        // When the user clicks the button, open the modal 
        //btn.onclick = function () {
        //    modal.style.display = "block";
        //}
        
        span.mo_close.onclick = function(){
        	modal.style.display = 'block;'
        }

        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
        }

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
    
    <!--quick-->
    <script>
        $(function () {
            var $win = $(window);
            var top = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.

            /*사용자 설정 값 시작*/
            var speed = 1000;     // 따라다닐 속도 : "slow", "normal", or "fast" or numeric(단위:msec)
            var easing = 'linear'; // 따라다니는 방법 기본 두가지 linear, swing
            var $layer = $('#div'); // 레이어 셀렉팅
            var layerTopOffset = 0;   // 레이어 높이 상한선, 단위:px
            $layer.css('position', 'absolute');
            /*사용자 설정 값 끝*/

            // 스크롤 바를 내린 상태에서 리프레시 했을 경우를 위해
            if (top > 0)
                $win.scrollTop(layerTopOffset + top);
            else
                $win.scrollTop(0);

            //스크롤이벤트가 발생하면
            $(window).scroll(function () {
                yPosition = $win.scrollTop() - 123;
                if (yPosition < 0) {
                    yPosition = 0;
                }
                $layer.animate({ "top": yPosition }, { duration: speed, easing: easing, queue: false });
            });
        });
    </script>    

</body>

</html>

