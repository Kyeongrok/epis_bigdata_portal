<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/layout_m.css" />" />

<script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.min.js"></script>

<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/dashboard_m.js" />" ></script>
<script>
	
	// 최근 5년 년도별 평균가격
	// [0] : 월동무, [1] : 양배추, [2] : 당근
	var prodFiveYearEachAvgMapDatas = ${prodFiveYearEachAvgMapDatasJson};
	
	// 재배의향 1,2,3차
	// [0] : 월동무, [1] : 양배추, [2] : 당근
	// 1차재배의향
	var willProdFirstMapDatas = ${willProdFirstMapDatasJson};
	// 2차재배의향
	var willProdSecondMapDatas = ${willProdSecondMapDatasJson};
	// 3차재배의향
	var willProdThirdMapDatas = ${willProdThirdMapDatasJson};
	// 확정재배의향
	var confirmProdMapDatas = ${confirmProdMapDatasJson};
	
</script>

<!-- <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="robots" content="index, follow">
    
    <title>제주도 주요 월동작물</title>
   <link rel="stylesheet" href="css/common.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Mukta|Yanone+Kaffeesatz:200,300,400&display=swap" rel="stylesheet">
    <script>
    </script>
</head> -->

<body>
	<div id="skipnavigation">
		<a href="#main_content">본문으로 바로가기</a> 
		<a href="#radish">월동무 바로가기</a> 
		<a href="#cabbage">양배추 바로가기</a> 
		<a href="#carrot">당근 바로가기</a> 
	</div>
    
<!--
	intro 화면의 경우, class="wrap intro"
-->    
    <div class="wrap intro">

        <header>
            <!-- 좌측 GNB 영역 -->
            <div class="gnb">
                <h1>bdp<span class="hide">빅데이터포털</span></h1>
            </div><!-- //gnb -->

            <!-- 사이트 안내 텍스트 -->
            <div class="description">
                <h2><strong>제주도<br/> 주요 월동작물 생산량</strong> <br/>시범분석 서비스 </h2>
                <p class="web_ex">
                제주도 주요 월동작물 생산량<br/> 시범분석 서비스는<br/> 제주도 주요 월동작물인<br/> 월동무, 양배추, 당근에 대한<br/> 생산량 분석 등 수급 의사결정을 위한<br/> 다양한 정보제공으로<br/> 수급관련 정책 대응력 향상을<br/>지원하기 위한 서비스입니다.
                </p>
            </div><!-- //description -->
        </header>


        <!-- 중앙 컨텐츠 영역 -->
        <section class="Information tabWrap" id="main_content">
            <ul class="topMenu tabTitle">
                <li class="tablinks">
                	<button type="button">
	                    <img src="<c:url value='/images/bigdata/radish.png' />" alt="무" class="image01" style="height:55px"/>	                    
	                </button>
	                <h3>월동무</h3>
                </li>
                <li class="tablinks">
                	<button type="button">
	                    <img src="<c:url value='/images/bigdata/cabbage.png' />" alt="양배추" class="image01" style="height:50px"/>	                    
                    </button>
                    <h3>양배추</h3>
                </li>
                <li class="tablinks">
                	<button type="button">
	                    <img src="<c:url value='/images/bigdata/carrot.png' />" alt="당근" class="image01" style="height:50px"/>	                    
                    </button>
                    <h3>당근</h3>
                </li>
            </ul>



            <div class="tabContWrap">

<!-- 월동무 -->                
                <div class="tabcontent crop column01" id="radish">
                    <div class="tabWrap">
                        <ul class="dep2 tabTitle">
                            <li class="tablinks on">
                            	<button type="button">
                                	<h2>예상생산량</h2>
                                </button>
                            </li>
                            <li class="tablinks">
                            	<button type="button">
                                	<h2>재배면적</h2>
                                </button>
                            </li>
                            <li class="tablinks">
                            	<button type="button">
                                	<h2>도매시장<br/>거래가격</h2>
                                </button>
                            </li>
                        </ul>
                        <div class="tabContWrap">

                            <div class="tabcontent prod column01_01 on">
                               <div class="content01">                        
                                    <dl class="expectOutput">
                                        <dt><span>${predictProdYear}</span>년산 <span>예상생산량</span> &#40;<span>현재</span>&#41;</dt>
                                        <dd><strong><fmt:formatNumber value="${predictProdNowRadish}" pattern="#,###" /></strong><span>톤</span></dd>
                                    </dl>
                                    <dl class="averageExpect">
                                        <dt>최근5년평균생산량 대비 예상생산량</dt>
                                        <dd><strong><fmt:formatNumber value="${predicVsFiveYearProdRadish}" pattern="##.#" /></strong><span>%</span></dd>
                                    </dl>
                                    <dl class="averageOutput">
                                        <dt>최근5년평균생산량</dt>
                                        <dd><strong><fmt:formatNumber value="${prodFiveYearAvgRadish}" pattern="#,###" /></strong><span>톤</span></dd>
                                    </dl>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">
                                    <h4 class="graph_title">생산량 추이</h4>
                                    <div class="graphWrap">
                                    </div>
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div>
                            </div><!-- //tabcontent 예상생산량 -->



                            <div class="tabcontent area column01_02">
                                <div class="content01">
                                    <dl class="cultiIntention">
                                        <dt>${prodWillDisplayOrd}&#40;<span>${prodWillDisplayYearMonth}</span>&#41;</dt>
                                        <dd>
                                        	<strong>
												<c:if test="${empty prodWillDisplayValRadish }">
													- - - -
												</c:if>
												<c:if test="${!empty prodWillDisplayValRadish }">
													<fmt:formatNumber value="${prodWillDisplayValRadish}" pattern="#,###" />
												</c:if>
											</strong><span>ha</span>
                                        </dd>
                                    </dl>
                                    <dl class="cultidArea">
                                        <dt>최근5년평균재배면적</dt>
                                        <dd><strong><fmt:formatNumber value="${fiveYearAvgAreaRadish}" pattern="#,###" /></strong><span>ha</span></dd>
                                    </dl>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">
                                    <h4 class="graph_title">생산면적 추이</h4>
                                    <!-- 월동무 재배의향 탭 -->
                                    <div class="tabWrap typeC">
                                        <ul class="tabTitle">
                                            <li class="tablinks on"><button type="button"><span>1차재배의향</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>2차재배의향</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>3차재배의향</span></button></li>
                                        </ul>
                                        <div class="tabContWrap">
                                            <div class="tabcontent will on"><div class="graphWrap"></div></div>
                                            <div class="tabcontent will"><div class="graphWrap"></div></div>
                                            <div class="tabcontent will"><div class="graphWrap"></div></div>
                                        </div>
                                    </div><!-- //tabWrap typeC -->
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div>
                            </div><!-- //tabcontent 재배면적 -->



                            <div class="tabcontent price column01_03">
                                <div class="content01">
                                    <dl class="marketPrice">
                                        <dt><span>도매시장거래가격</span> &#40;<span>일별,20kg당</span>&#41;</dt>
                                        <dd>
                                        	<c:if test="${empty radishTodayPrice}">
												<strong>N/A</strong><span>원</span>
											</c:if>
											<c:if test="${!empty radishTodayPrice}">
												<strong><fmt:formatNumber value="${radishTodayPrice}" pattern="#,###" /></strong><span>원</span>
											</c:if>
                                        </dd>
                                    </dl>
                                    <dl class="risePrice">
                                        <dt>전년대비 상승가격</dt>
                                        <dd>
                                        	<c:if test="${empty radishTodayUpDownPrice}">
												<em class="inc"><span>상승</span></em><strong>N/A</strong><span>원</span>
											</c:if>
											<c:if test="${!empty radishTodayUpDownPrice}">
												<c:if test="${radishTodayUpDownPrice >= 0}">
													<em class="inc"><span>상승</span></em><strong><fmt:formatNumber value="${radishTodayUpDownPrice}" pattern="#,###" /></strong><span>원</span>
												</c:if>
												<c:if test="${radishTodayUpDownPrice < 0}">
													<em class="dec"><span>하강</span></em><strong><fmt:formatNumber value="${radishTodayUpDownPrice}" pattern="#,###" /></strong><span>원</span>
												</c:if>
											</c:if>
                                        </dd>
                                    </dl>
                                    <dl class="perChange">
                                        <dt>증감률</dt>
                                        <dd>
                                        	<c:if test="${empty radishTodayUpDownPercent}">
												<em class="inc"><span>상승</span></em><strong>N/A</strong><span>%</span>
											</c:if>
											<c:if test="${!empty radishTodayUpDownPercent}">
												<c:if test="${radishTodayUpDownPercent >= 0}">
													<em class="inc"><span>상승</span></em><strong><fmt:formatNumber value="${radishTodayUpDownPercent}" pattern="###.#" /></strong><span>%</span>
												</c:if>
												<c:if test="${radishTodayUpDownPercent < 0}">
													<em class="dec"><span>하강</span></em><strong><fmt:formatNumber value="${radishTodayUpDownPercent}" pattern="###.#" /></strong><span>%</span>
												</c:if>
											</c:if>
                                        </dd>
                                    </dl>
                                    <p class="exp">
                                    	<c:if test="${empty radishLastYearPrice }">
											※ 작년경락가격 <span>N/A</span>
										</c:if>
										<c:if test="${!empty radishLastYearPrice }">
											※ 작년경락가격 <span><fmt:formatNumber value="${radishLastYearPrice}" pattern="#,###" /></span>
										</c:if>
                                    </p>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">      
                                    <h4 class="graph_title">도매시장 가격 추이</h4>                              
                                    <!--
                                        활성화되는 TAB 의 class 에 on 추가 
                                        <li class="tablinks on">
                                        <div class="tabcontent on">
                                    -->
                                    <!-- 월동무 도매시장거래가격 Tab 일/주/월/연별 -->
                                    <div class="tabWrap typeA">
                                        <ul class="tabTitle">
                                            <li class="tablinks on"><button type="button" ><span>일별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>주별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>월별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>연간</span></button></li>
                                        </ul>
                                        <div class="tabContWrap">
                                            <div class="tabcontent period on">
                                                <!-- 월동무 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 월동무 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 월동무 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 월동무 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 월동무 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div><!-- //tabcontent -->
                                        </div><!-- //tabContWrap -->
                                    </div><!-- //tabWrap typeA -->
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div><!-- //content02 -->
                            </div><!-- //tabcontent 도매시장거래가격 -->
                        </div><!-- //tabContWrap -->
                    </div><!-- //tabWrap -->
                </div><!-- //tabcontent -->
<!-- //월동무 -->        


<!-- 양배추 -->                
                <div class="tabcontent crop column02" id="cabbage">
                    <div class="tabWrap">
                        <ul class="dep2 tabTitle">
                            <li class="tablinks on">
                            	<button type="button">
                                	<h2>예상생산량</h2>
                                </button>
                            </li>
                            <li class="tablinks">
                            	<button type="button">
                                	<h2>재배면적</h2>
                                </button>
                            </li>
                            <li class="tablinks">
                            	<button type="button">
                                	<h2>도매시장<br/>거래가격</h2>
                                </button>
                            </li>
                        </ul>
                        <div class="tabContWrap">

                            <div class="tabcontent prod column02_01 on">
                               <div class="content01">                        
                                    <dl class="expectOutput">
                                        <dt><span>${predictProdYear}</span>년산 <span>예상생산량</span> &#40;<span>현재</span>&#41;</dt>
                                        <dd><strong><fmt:formatNumber value="${predictProdNowCabbage}" pattern="#,###" /></strong><span>톤</span></dd>
                                    </dl>
                                    <dl class="averageExpect">
                                        <dt>최근5년평균생산량 대비 예상생산량</dt>
                                        <dd><strong><fmt:formatNumber value="${predicVsFiveYearProdCabbage}" pattern="##.#" /></strong><span>%</span></dd>
                                    </dl>
                                    <dl class="averageOutput">
                                        <dt>최근5년평균생산량</dt>
                                        <dd><strong><fmt:formatNumber value="${prodFiveYearAvgCabbage}" pattern="#,###" /></strong><span>톤</span></dd>
                                    </dl>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">
                                    <h4 class="graph_title">생산량 추이</h4>
                                    <div class="graphWrap"></div>
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div>
                            </div><!-- //tabcontent 예상생산량 -->



                            <div class="tabcontent area column02_02">
                                <div class="content01">
                                    <dl class="cultiIntention">
                                        <dt>${prodWillDisplayOrd}&#40;<span>${prodWillDisplayYearMonth}</span>&#41;</dt>
                                        <dd>
                                        	<strong>
												<c:if test="${empty prodWillDisplayValCabbage }">
													- - - -
												</c:if>
												<c:if test="${!empty prodWillDisplayValCabbage }">
													<fmt:formatNumber value="${prodWillDisplayValCabbage}" pattern="#,###" />
												</c:if>
											</strong><span>ha</span>
                                        </dd>
                                    </dl>
                                    <dl class="cultidArea">
                                        <dt>최근5년평균재배면적</dt>
                                        <dd><strong><fmt:formatNumber value="${fiveYearAvgAreaCabbage}" pattern="#,###" /></strong><span>ha</span></dd>
                                    </dl>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">
                                    <h4 class="graph_title">생산면적 추이</h4>
                                    <!-- 양배추 재배의향 탭 -->
                                    <div class="tabWrap typeC">
                                        <ul class="tabTitle">
                                            <li class="tablinks on"><button type="button"><span>1차재배의향</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>2차재배의향</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>3차재배의향</span></button></li>
                                        </ul>
                                        <div class="tabContWrap">
                                            <div class="tabcontent will on"><div class="graphWrap"></div></div>
                                            <div class="tabcontent will"><div class="graphWrap"></div></div>
                                            <div class="tabcontent will"><div class="graphWrap"></div></div>
                                        </div>
                                    </div><!-- //tabWrap typeC -->
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div>
                            </div><!-- //tabcontent 재배면적 -->



                            <div class="tabcontent price column02_03">
                                <div class="content01">
                                    <dl class="marketPrice">
                                        <dt><span>도매시장거래가격</span> &#40;<span>일별,20kg당</span>&#41;</dt>
                                        <dd>
                                        	<c:if test="${empty cabbageTodayPrice}">
												<strong>N/A</strong><span>원</span>
											</c:if>
											<c:if test="${!empty cabbageTodayPrice}">
												<strong><fmt:formatNumber value="${cabbageTodayPrice}" pattern="#,###" /></strong><span>원</span>
											</c:if>
                                        </dd>
                                    </dl>
                                    <dl class="risePrice">
                                        <dt>전년대비 상승가격</dt>
                                        <dd>
                                        	<c:if test="${empty cabbageTodayUpDownPrice}">
												<em class="inc"><span>상승</span></em><strong>N/A</strong><span>원</span>
											</c:if>
											<c:if test="${!empty cabbageTodayUpDownPrice}">
												<c:if test="${cabbageTodayUpDownPrice >= 0}">
													<em class="inc"><span>상승</span></em><strong><fmt:formatNumber value="${cabbageTodayUpDownPrice}" pattern="#,###" /></strong><span>원</span>
												</c:if>
												<c:if test="${cabbageTodayUpDownPrice < 0}">
													<em class="dec"><span>하강</span></em><strong><fmt:formatNumber value="${cabbageTodayUpDownPrice}" pattern="#,###" /></strong><span>원</span>
												</c:if>
											</c:if>
                                        </dd>
                                    </dl>
                                    <dl class="perChange">
                                        <dt>증감률</dt>
                                        <dd>
                                        	<c:if test="${empty cabbageTodayUpDownPercent}">
												<em class="inc"><span>상승</span></em><strong>N/A</strong><span>%</span>
											</c:if>
											<c:if test="${!empty cabbageTodayUpDownPercent}">
												<c:if test="${cabbageTodayUpDownPercent >= 0}">
													<em class="inc"><span>상승</span></em><strong><fmt:formatNumber value="${cabbageTodayUpDownPercent}" pattern="###.#" /></strong><span>%</span>
												</c:if>
												<c:if test="${cabbageTodayUpDownPercent < 0}">
													<em class="dec"><span>하강</span></em><strong><fmt:formatNumber value="${cabbageTodayUpDownPercent}" pattern="###.#" /></strong><span>%</span>
												</c:if>
											</c:if>
                                        </dd>
                                    </dl>
                                    <p class="exp">
                                    	<c:if test="${empty cabbageLastYearPrice }">
											※ 작년경락가격 <span>N/A</span>
										</c:if>
										<c:if test="${!empty cabbageLastYearPrice }">
											※ 작년경락가격 <span><fmt:formatNumber value="${cabbageLastYearPrice}" pattern="#,###" /></span>
										</c:if>
                                    </p>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">      
                                    <h4 class="graph_title">도매시장 가격 추이</h4>                              
                                    <!--
                                        활성화되는 TAB 의 class 에 on 추가 
                                        <li class="tablinks on">
                                        <div class="tabcontent on">
                                    -->
                                    <!-- 양배추 도매시장거래가격 Tab 일/주/월/연별 -->
                                    <div class="tabWrap typeA">
                                        <ul class="tabTitle">
                                            <li class="tablinks on"><button type="button" ><span>일별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>주별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>월별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>연간</span></button></li>
                                        </ul>
                                        <div class="tabContWrap">
                                            <div class="tabcontent period on">
                                                <!-- 양배추 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 양배추 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 양배추 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 양배추 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 양배추 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div><!-- //tabcontent -->
                                        </div><!-- //tabContWrap -->
                                    </div><!-- //tabWrap typeA -->
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div><!-- //content02 -->
                            </div><!-- //tabcontent 도매시장거래가격 -->
                        </div><!-- //tabContWrap -->
                    </div><!-- //tabWrap -->
                </div><!-- //tabcontent -->
<!-- //양배추 -->        


<!-- 당근 -->                
                <div class="tabcontent crop column02" id="carrot">
                    <div class="tabWrap">
                        <ul class="dep2 tabTitle">
                            <li class="tablinks on">
                            	<button type="button">
                                	<h2>예상생산량</h2>
                                </button>
                            </li>
                            <li class="tablinks">
                            	<button type="button">
                                	<h2>재배면적</h2>
                                </button>
                            </li>
                            <li class="tablinks">
                            	<button type="button">
                                	<h2>도매시장<br/>거래가격</h2>
                                </button>
                            </li>
                        </ul>
                        <div class="tabContWrap">

                            <div class="tabcontent prod column02_01 on">
                               <div class="content01">                        
                                    <dl class="expectOutput">
                                        <dt><span>${predictProdYear}</span>년산 <span>예상생산량</span> &#40;<span>현재</span>&#41;</dt>
                                        <dd><strong><fmt:formatNumber value="${predictProdNowCarrot}" pattern="#,###" /></strong><span>톤</span></dd>
                                    </dl>
                                    <dl class="averageExpect">
                                        <dt>최근5년평균생산량 대비 예상생산량</dt>
                                        <dd><strong><fmt:formatNumber value="${predicVsFiveYearProdCarrot}" pattern="##.#" /></strong><span>%</span></dd>
                                    </dl>
                                    <dl class="averageOutput">
                                        <dt>최근5년평균생산량</dt>
                                        <dd><strong><fmt:formatNumber value="${prodFiveYearAvgCarrot}" pattern="#,###" /></strong><span>톤</span></dd>
                                    </dl>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">
                                    <h4 class="graph_title">생산량 추이</h4>
                                    <div class="graphWrap"></div>
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div>
                            </div><!-- //tabcontent 예상생산량 -->



                            <div class="tabcontent area column02_02">
                                <div class="content01">
                                    <dl class="cultiIntention">
                                        <dt>${prodWillDisplayOrd}&#40;<span>${prodWillDisplayYearMonth}</span>&#41;</dt>
                                        <dd>
                                        	<strong>
												<c:if test="${empty prodWillDisplayValCarrot }">
													- - - -
												</c:if>
												<c:if test="${!empty prodWillDisplayValCarrot }">
													<fmt:formatNumber value="${prodWillDisplayValCarrot}" pattern="#,###" />
												</c:if>
											</strong><span>ha</span>
                                        </dd>
                                    </dl>
                                    <dl class="cultidArea">
                                        <dt>최근5년평균재배면적</dt>
                                        <dd><strong><fmt:formatNumber value="${fiveYearAvgAreaCarrot}" pattern="#,###" /></strong><span>ha</span></dd>
                                    </dl>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">
                                    <h4 class="graph_title">생산면적 추이</h4>
                                    <!-- 당근 재배의향 탭 -->
                                    <div class="tabWrap typeC">
                                        <ul class="tabTitle">
                                            <li class="tablinks on"><button type="button"><span>1차재배의향</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>2차재배의향</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>3차재배의향</span></button></li>
                                        </ul>
                                        <div class="tabContWrap">
                                            <div class="tabcontent will on"><div class="graphWrap"></div></div>
                                            <div class="tabcontent will"><div class="graphWrap"></div></div>
                                            <div class="tabcontent will"><div class="graphWrap"></div></div>
                                        </div>
                                    </div><!-- //tabWrap typeC -->
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div>
                            </div><!-- //tabcontent 재배면적 -->



                            <div class="tabcontent price column02_03">
                                <div class="content01">
                                    <dl class="marketPrice">
                                        <dt><span>도매시장거래가격</span> &#40;<span>일별,20kg당</span>&#41;</dt>
                                        <dd>
                                        	<c:if test="${empty carrotTodayPrice}">
												<strong>N/A</strong><span>원</span>
											</c:if>
											<c:if test="${!empty carrotTodayPrice}">
												<strong><fmt:formatNumber value="${carrotTodayPrice}" pattern="#,###" /></strong><span>원</span>
											</c:if>
                                        </dd>
                                    </dl>
                                    <dl class="risePrice">
                                        <dt>전년대비 상승가격</dt>
                                        <dd>
                                        	<c:if test="${empty carrotTodayUpDownPrice}">
												<em class="inc"><span>상승</span></em><strong>N/A</strong><span>원</span>
											</c:if>
											<c:if test="${!empty carrotTodayUpDownPrice}">
												<c:if test="${carrotTodayUpDownPrice >= 0}">
													<em class="inc"><span>상승</span></em><strong><fmt:formatNumber value="${carrotTodayUpDownPrice}" pattern="#,###" /></strong><span>원</span>
												</c:if>
												<c:if test="${carrotTodayUpDownPrice < 0}">
													<em class="dec"><span>하강</span></em><strong><fmt:formatNumber value="${carrotTodayUpDownPrice}" pattern="#,###" /></strong><span>원</span>
												</c:if>
											</c:if>
                                        </dd>
                                    </dl>
                                    <dl class="perChange">
                                        <dt>증감률</dt>
                                        <dd>
                                        	<c:if test="${empty carrotTodayUpDownPercent}">
												<em class="inc"><span>상승</span></em><strong>N/A</strong><span>%</span>
											</c:if>
											<c:if test="${!empty carrotTodayUpDownPercent}">
												<c:if test="${carrotTodayUpDownPercent >= 0}">
													<em class="inc"><span>상승</span></em><strong><fmt:formatNumber value="${carrotTodayUpDownPercent}" pattern="###.#" /></strong><span>%</span>
												</c:if>
												<c:if test="${carrotTodayUpDownPercent < 0}">
													<em class="dec"><span>하강</span></em><strong><fmt:formatNumber value="${carrotTodayUpDownPercent}" pattern="###.#" /></strong><span>%</span>
												</c:if>
											</c:if>
                                        </dd>
                                    </dl>
                                    <p class="exp">
                                    	<c:if test="${empty carrotLastYearPrice }">
											※ 작년경락가격 <span>N/A</span>
										</c:if>
										<c:if test="${!empty carrotLastYearPrice }">
											※ 작년경락가격 <span><fmt:formatNumber value="${carrotLastYearPrice}" pattern="#,###" /></span>
										</c:if>
                                    </p>
                                    <button type="button" class="btnOpenPop"><span>추이보기</span></button>
                                </div>
                                <div class="content02">      
                                    <h4 class="graph_title">도매시장 가격 추이</h4>                              
                                    <!--
                                        활성화되는 TAB 의 class 에 on 추가 
                                        <li class="tablinks on">
                                        <div class="tabcontent on">
                                    -->
                                    <!-- 당근 도매시장거래가격 Tab 일/주/월/연별 -->
                                    <div class="tabWrap typeA">
                                        <ul class="tabTitle">
                                            <li class="tablinks on"><button type="button" ><span>일별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>주별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>월별</span></button></li>
                                            <li class="tablinks"><button type="button" ><span>연간</span></button></li>
                                        </ul>
                                        <div class="tabContWrap">
                                            <div class="tabcontent period on">
                                                <!-- 당근 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 당근 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 당근 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div>
                                            <div class="tabcontent period">
                                                <!-- 당근 도매시장거래가격 Tab 상/중/보통별 -->
                                                <div class="tabWrap typeB">
                                                    <ul class="tabTitle">
                                                        <li class="tablinks on"><button type="button" ><span>평균보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>특품보기</span></button></li>
                                                        <li class="tablinks"><button type="button"><span>상품보기</span></button></li>
                                                        <li class="tablinks"><button type="button" ><span>보통보기</span></button></li>
                                                    </ul>
                                                    <div class="tabContWrap">
                                                        <div class="tabcontent grade on">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                        <div class="tabcontent grade">
                                                            <!-- 당근 전년/평년 -->
                                                            <div class="tabWrap typeC">
                                                                <ul class="tabTitle">
                                                                    <li class="tablinks on"><button type="button" ><span>전년</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>5년간 평균</span></button></li>
                                                                    <li class="tablinks"><button type="button" ><span>평년</span></button></li>
                                                                </ul>
                                                                <div class="tabContWrap">
                                                                    <div class="tabcontent years on">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                    <div class="tabcontent years">
                                                                        <div class="graphWrap"></div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- //tabWrap typeC -->
                                                        </div>
                                                    </div><!-- //tabContWrap -->
                                                </div><!-- //tabWrap typeB -->
                                            </div><!-- //tabcontent -->
                                        </div><!-- //tabContWrap -->
                                    </div><!-- //tabWrap typeA -->
                                    <a class="close_button" href="#"><img src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
                                </div><!-- //content02 -->
                            </div><!-- //tabcontent 도매시장거래가격 -->
                        </div><!-- //tabContWrap -->
                    </div><!-- //tabWrap -->
                </div><!-- //tabcontent -->
<!-- //당근 -->        




            </div><!-- //tabContWrap -->



 
        </section><!-- //Information -->

    </div><!-- //wrap -->
       
</body>

</html>