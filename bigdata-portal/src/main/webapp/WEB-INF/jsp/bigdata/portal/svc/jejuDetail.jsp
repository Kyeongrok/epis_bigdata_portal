<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/jejuwinter.css" />" />

<script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.min.js"></script>

<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/vendor/jquery-ui/jquery-ui.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/vendor/jquery-tmpl/jquery.tmpl-1.0.0.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.spin.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/common.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/placeholders.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/dashboard.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/jejuObj.js" />" ></script>
<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.34/browser.js"></script>

<script>
	
	// 최근 5년 년도별 평균가격
	// [0] : 월동무, [1] : 양배추, [2] : 당근
	var prodFiveYearEachAvgMapDatas = ${prodFiveYearEachAvgMapDatasJson};
	
	// 최근 5년 년도별 재배 면적
	// [0] : 월동무, [1] : 양배추, [2] : 당근
	//var fiveYearEachAreaMapDatas = ${fiveYearEachAreaMapDatasJson};
	
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

<div class="wrap">
	<header>
		<!-- 좌측 GNB 영역 -->
       	<div class="gnb">
    		<h1><a href="#">bdp</a><span class="hide">빅데이터포털</span></h1>
    		<!-- <ul class="nav01">
    			<li><a href="#"><img src="" alt="" /></a></li>
    			<li><a href="#"><img src="" alt="" /></a></li>
    			<li><a href="#"><img src="" alt="" /></a></li>
    			<li><a href="#"><img src="" alt="" /></a></li>
    		</ul> -->
    		<%-- <ul class="nav02">
    			<li><a href="javascript:window.open('/bdp/svc/fixesSportPolicySearchList.do')"><img src="<c:url value='/images/bigdata/policy.png' />" alt="맞춤지원정책 안내 서비스" /></a></li>
    			<li><a href="javascript:window.open('/bdp/svc/retnFmlg/retnFmlgInfo.do')"><img src="<c:url value='/images/bigdata/return_farm.png' />" alt="귀농의사결정지원 서비스" /></a></li>
    			<li><a href="#"><img src="<c:url value='/images/bigdata/jeju_disable.png' />" alt="제주도 주요 월동작물 생산량시범분석 서비스" /></a></li>
    			<li><a href="javascript:window.open('/bdp/svc/riceDetail.do')"><img src="<c:url value='/images/bigdata/rice.png' />" alt="쌀 생산유통 상시분석 서비스" /></a></li>
    		</ul> --%>
    	</div><!-- //gnb -->
		<!-- 사이트 안내 텍스트 -->
		<div class="description">
			<h1>
				제주도<br />주요 월동작물<br />생산량<br />시범분석 서비스
			</h1>
			<p class="web_ex">
				제주도 주요 월동작물 생산량<br /> 시범분석 서비스는<br /> 제주도 주요 월동작물인<br /> 월동무, 양배추,
				당근에 대한<br /> 생산량 분석 등 수급 의사결정을 위한<br /> 다양한 정보제공으로<br /> 수급관련 정책 대응력
				향상을<br />지원하기 위한 서비스입니다.
			</p>
			<p class="web_ex2">
				<span class="gray">최근5년평균생산량 대비<br />예상생산량 비율이
				</span><br />
				<span class="redOrange">100%이상인 경우 : <br />해당 작물이 평균생산량 대비 <br />생산
					과잉 가능성 높음
				</span><br />
				<span class="orange">100%이하인 경우 : <br />해당 작물이 평균생산량 대비 <br />생산
					미달 가능성음 높음
				</span>
			</p>
		</div>
	</header>

	<!-- 중앙 컨텐츠 영역 -->
	<section class="Information" id="center">
		<ul class="info_box01">
			<li class="detail_info01 column01">
				<div class="content01">
					<img src="<c:url value='/images/bigdata/radish.png' />" alt="무"
						class="image01" />
					<h2>월동무</h2>
					<dl class="expectOutput">
						<dt>
							<span>${predictProdYear}</span>년산 <span>예상생산량</span> &#40;<span>현재</span>&#41;
						</dt>
						<dd>
							<strong><fmt:formatNumber value="${predictProdNowRadish}" pattern="#,###" /></strong><span>톤</span>
						</dd>
					</dl>
					<dl class="averageExpect">
						<dt>최근5년평균생산량 대비 예상생산량</dt>
						<dd>
							<strong><fmt:formatNumber value="${predicVsFiveYearProdRadish}" pattern="##.#" /></strong><span>%</span>
						</dd>
					</dl>
					<dl class="averageOutput">
						<dt>최근5년평균생산량</dt>
						<dd>
							<!-- <strong>289,502</strong><span>톤</span> -->
							<strong><fmt:formatNumber value="${prodFiveYearAvgRadish}" pattern="#,###" /></strong><span>톤</span>
						</dd>
					</dl>
				</div>
				<div class="content02">
					<h4 class="graph_title">재배 생산량 예측</h4>
					<div class="graphWrap" id="radishProdChart"></div>
				</div> <a class="close_button" href="#"><img
					src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
			</li>
			<li class="detail_info01 column02">
				<div class="content01">
					<img src="<c:url value='/images/bigdata/cabbage.png' />" alt="무"
						class="image01" />
					<h2>양배추</h2>
					<dl class="expectOutput">
						<dt>
							<span>${predictProdYear}</span>년산 <span>예상생산량</span> &#40;<span>현재</span>&#41;
						</dt>
						<dd>
							<strong><fmt:formatNumber value="${predictProdNowCabbage}" pattern="#,###" /></strong><span>톤</span>
						</dd>
					</dl>
					<dl class="averageExpect">
						<dt>최근5년평균생산량 대비 예상생산량</dt>
						<dd>
							<strong><fmt:formatNumber value="${predicVsFiveYearProdCabbage}" pattern="##.#" /></strong><span>%</span>
						</dd>
					</dl>
					<dl class="averageOutput">
						<dt>최근5년평균생산량</dt>
						<dd>
							<!-- <strong>124,440</strong><span>톤</span> -->
							<strong><fmt:formatNumber value="${prodFiveYearAvgCabbage}" pattern="#,###" /></strong><span>톤</span>
						</dd>
					</dl>
				</div>
				<div class="content02">
					<h4 class="graph_title">재배 생산량 예측</h4>
					<div class="graphWrap" id="cabbageProdChart"></div>
				</div> <a class="close_button" href="#"><img
					src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
			</li>
			<li class="detail_info01 column03">
				<div class="content01">
					<img src="<c:url value='/images/bigdata/carrot.png' />" alt="무"
						class="image01" />
					<h2>당근</h2>
					<dl class="expectOutput">
						<dt>
							<span>${predictProdYear}</span>년산 <span>예상생산량</span> &#40;<span>현재</span>&#41;
						</dt>
						<dd>
							<strong><fmt:formatNumber value="${predictProdNowCarrot}" pattern="#,###" /></strong><span>톤</span>
						</dd>
					</dl>
					<dl class="averageExpect">
						<dt>최근5년평균생산량 대비 예상생산량</dt>
						<dd>
							<strong><fmt:formatNumber value="${predicVsFiveYearProdCarrot}" pattern="##.#" /></strong><span>%</span>
						</dd>
					</dl>
					<dl class="averageOutput">
						<dt>최근5년평균생산량</dt>
						<dd>
							<!-- <strong>46,317</strong><span>톤</span> -->
							<strong><fmt:formatNumber value="${prodFiveYearAvgCarrot}" pattern="#,###" /></strong><span>톤</span>
						</dd>
					</dl>
				</div>
				<div class="content02">
					<h4 class="graph_title">재배 생산량 예측</h4>
					<div class="graphWrap" id="carrotProdChart"></div>
				</div> <a class="close_button" href="#"><img
					src="<c:url value='/images/bigdata/close.png' />" alt="닫기" /></a>
			</li>
		</ul>
		<ul class="info_box02">
			<li class="detail_info02 column01">
				<div class="content01">
					<dl class="cultiIntention">
						<dt>
							${prodWillDisplayOrd}&#40;<span>${prodWillDisplayYearMonth}</span>&#41;
						</dt>
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
						<dd>
							<strong><fmt:formatNumber value="${fiveYearAvgAreaRadish}" pattern="#,###" /></strong><span>ha</span>
						</dd>
					</dl>
				</div>
				<div class="content02">
					<h4 class="graph_title">생산면적</h4>

					<!-- 월동무 재배의향 탭 -->
					<div class="tabWrap typeC" id="willRadishTab">
						<ul class="tabTitle">
							<li class="tablinks on"><button type="button">
									<span>1차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>2차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>3차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>확정재배신고</span>
								</button></li>
						</ul>
						<div class="tabContWrap">
							<div class="tabcontent on">
								<!-- 월동무 그래프 영역cont01 -->
								<div class="graphWrap"></div>
							</div>
							<!-- 월동무 그래프 영역cont02 -->
							<div class="tabcontent">
								<div class="graphWrap"></div>
							</div>
							<!-- 월동무 그래프 영역cont03 -->
							<div class="tabcontent">
								<div class="graphWrap"></div>
							</div>
							<!-- 월동무 그래프 영역cont04 -->
							<div class="tabcontent">
								<div class="graphWrap"></div>
							</div>
						</div>
					</div>
					<!-- //tabWrap typeC -->

				</div>
			</li>
			<li class="detail_info02 column02">
				<div class="content01">
					<dl class="cultiIntention">
						<dt>
							${prodWillDisplayOrd}&#40;<span>${prodWillDisplayYearMonth}</span>&#41;
						</dt>
						<dd>
							<strong>
								<c:if test="${empty prodWillDisplayValCabbage}">
									- - - -  
								</c:if>
								<c:if test="${!empty prodWillDisplayValCabbage}">
									<fmt:formatNumber value="${prodWillDisplayValCabbage}" pattern="#,###" />
								</c:if>
							</strong><span>ha</span>
						</dd>
					</dl>
					<dl class="cultidArea">
						<dt>최근5년평균재배면적</dt>
						<dd>
							<strong><fmt:formatNumber value="${fiveYearAvgAreaCabbage}" pattern="#,###" /></strong><span>ha</span>
						</dd>
					</dl>
				</div>
				<div class="content02">
					<h4 class="graph_title">생산면적</h4>

					<!-- 양배추 재배의향 탭 -->
					<div class="tabWrap typeC" id="willCabbageTab">
						<ul class="tabTitle">
							<li class="tablinks on"><button type="button">
									<span>1차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>2차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>3차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>확정재배신고</span>
								</button></li>
						</ul>
						<div class="tabContWrap">
							<div class="tabcontent on">
								<div class="graphWrap">
									<!-- 양배추 그래프 영역cont01 -->
								</div>
							</div>
							<div class="tabcontent">
								<div class="graphWrap">
									<!-- 양배추 그래프 영역cont02 -->
								</div>
							</div>
							<div class="tabcontent">
								<div class="graphWrap">
									<!-- 양배추 그래프 영역cont03 -->
								</div>
							</div>
							<div class="tabcontent">
								<div class="graphWrap">
									<!-- 양배추 그래프 영역cont04 -->
								</div>
							</div>
						</div>
					</div>
					<!-- //tabWrap typeC -->
				</div>
			</li>
			<li class="detail_info02 column03">
				<div class="content01">
					<dl class="cultiIntention">
						<dt>
							${prodWillDisplayOrd}&#40;<span>${prodWillDisplayYearMonth}</span>&#41;
						</dt>
						<dd>
							<strong>
								<c:if test="${empty prodWillDisplayValCarrot}">
									- - - -  
								</c:if>
								<c:if test="${!empty prodWillDisplayValCarrot}">
									<fmt:formatNumber value="${prodWillDisplayValCarrot}" pattern="#,###" />
								</c:if>
							</strong><span>ha</span>
						</dd>
					</dl>
					<dl class="cultidArea">
						<dt>최근5년평균재배면적</dt>
						<dd>
							<strong><fmt:formatNumber value="${fiveYearAvgAreaCarrot}" pattern="#,###" /></strong><span>ha</span>
						</dd>
					</dl>
				</div>
				<div class="content02">
					<h4 class="graph_title">생산면적</h4>

					<!-- 당근 재배의향 탭 -->
					<div class="tabWrap typeC" id="willCarrotTab">
						<ul class="tabTitle">
							<li class="tablinks on"><button type="button">
									<span>1차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>2차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>3차재배의향</span>
								</button></li>
							<li class="tablinks"><button type="button">
									<span>확정재배신고</span>
								</button></li>
						</ul>
						<div class="tabContWrap">
							<div class="tabcontent on">
								<div class="graphWrap">
									<!-- 당근 그래프 영역cont01 -->
								</div>
							</div>
							<div class="tabcontent">
								<div class="graphWrap">
									<!-- 당근 그래프 영역cont02 -->
								</div>
							</div>
							<div class="tabcontent">
								<div class="graphWrap">
									<!-- 당근 그래프 영역cont03 -->
								</div>
							</div>
							<div class="tabcontent">
								<div class="graphWrap">
									<!-- 당근 그래프 영역cont04 -->
								</div>
							</div>
						</div>
					</div>
					<!-- //tabWrap typeC -->
				</div>
			</li>
		</ul>
		<ul class="info_box03">
			<li class="detail_info03 column01">
				<div class="content01">
					<dl class="marketPrice">
						<dt>
							<span>도매시장거래가격</span> &#40;<span>일별,20kg당</span>&#41;
						</dt>
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
				</div>
				<div class="content02">

					<!--
							활성화되는 TAB 의 class 에 on 추가 
							<li class="tablinks on">
							<div class="tabcontent on">
                    	-->
					<!-- 월동무 도매시장거래가격 Tab 일/주/월/연별 -->
					<div class="tabWrap typeA">
						<ul class="tabTitle">
							<li class="tablinks on"><button type="button">
									일별 도매시장 가격<br />
									<span>13일전 ~ 오늘 &#40;14일/일별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									주간별 도매시장 가격<br />
									<span>&#40;12주/주별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									월별 도매시장 가격<br />
									<span>&#40;1년/월별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									연간 도매시장 가격 추이<br />
									<span>&#40;5년/년별평균&#41;</span>
								</button></li>
						</ul>
						<div class="tabContWrap">
							<div class="tabcontent on">
								<!-- 월동무 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 평균 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 평균 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 평균 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 상품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 상품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 상품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 중품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 중품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 중품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 하품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 하품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 하품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 월동무 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 평균 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 평균 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 평균 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 상품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 상품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 상품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 중품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 중품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 중품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 하품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 하품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 하품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 월동무 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 평균 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 평균 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 평균 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 상품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 상품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 상품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 중품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 중품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 중품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 하품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 하품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 하품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 월동무 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 평균 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 평균 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 평균 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 상품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 상품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 상품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 중품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 중품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 중품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 월동무 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 하품 전년 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 하품 최근5년간의평균 월동무 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 하품 평년 월동무 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<!-- //tabcontent -->
						</div>
						<!-- //tabContWrap -->
					</div>
					<!-- //tabWrap typeA -->

				</div>
				<!-- //content02 -->
			</li>
			<li class="detail_info03 column02">
				<div class="content01">
					<dl class="marketPrice">
					    <dt>
					        <span>도매시장거래가격</span> &#40;<span>일별,20kg당</span>&#41;
					    </dt>
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
					    <!-- ※ 작년경락가격 <span>6,800</span>원 -->
					    <c:if test="${empty cabbageLastYearPrice }">
					        ※ 작년경락가격 <span>N/A</span>
					    </c:if>
					    <c:if test="${!empty cabbageLastYearPrice }">
					        ※ 작년경락가격 <span><fmt:formatNumber value="${cabbageLastYearPrice}" pattern="#,###" /></span>
					    </c:if>
					</p>
				</div>
				<div class="content02">

					<!-- 양배추 도매시장거래가격 Tab 일/주/월/연별 -->
					<div class="tabWrap typeA">
						<ul class="tabTitle">
							<li class="tablinks on"><button type="button">
									일별 도매시장 가격<br />
									<span>13일전 ~ 오늘 &#40;14일/일별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									주간별 도매시장 가격<br />
									<span>&#40;12주/주별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									월별 도매시장 가격<br />
									<span>&#40;1년/월별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									연간 도매시장 가격 추이<br />
									<span>&#40;5년/년별평균&#41;</span>
								</button></li>
						</ul>
						<div class="tabContWrap">
							<div class="tabcontent on">
								<!-- 양배추 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 평균 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 평균 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 평균 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 상품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 상품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 상품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 중품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 중품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 중품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 하품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 하품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 하품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 양배추 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 평균 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 평균 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 평균 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 상품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 상품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 상품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 중품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 중품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 중품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 하품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 하품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 하품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 양배추 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 평균 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 평균 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 평균 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 상품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 상품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 상품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 중품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 중품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 중품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 하품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 하품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 하품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 양배추 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 평균 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 평균 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 평균 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 상품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 상품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 상품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 중품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 중품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 중품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 양배추 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 연별 하품 전년 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 하품 최근5년간의평균 양배추 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 연별 하품 평년 양배추 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<!-- //tabcontent -->
						</div>
						<!-- //tabContWrap -->
					</div>
					<!-- //tabWrap typeA -->

				</div>
				<!-- //content02 -->
			</li>
			<li class="detail_info03 column03">
				<div class="content01">
					<dl class="marketPrice">
					    <dt>
					        <span>도매시장거래가격</span> &#40;<span>일별,20kg당</span>&#41;
					    </dt>
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
					    <!-- ※ 작년경락가격 <span>6,800</span>원 -->
					    <c:if test="${empty carrotLastYearPrice }">
					        ※ 작년경락가격 <span>N/A</span>
					    </c:if>
					    <c:if test="${!empty carrotLastYearPrice }">
					        ※ 작년경락가격 <span><fmt:formatNumber value="${carrotLastYearPrice}" pattern="#,###" /></span>
					    </c:if>
					</p>
				</div>
				<div class="content02">
					<!-- 당근 도매시장거래가격 Tab 일/주/월/연별 -->
					<div class="tabWrap typeA">
						<ul class="tabTitle">
							<li class="tablinks on"><button type="button">
									일별 도매시장 가격<br />
									<span>13일전 ~ 오늘 &#40;14일/일별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									주간별 도매시장 가격<br />
									<span>&#40;12주/주별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									월별 도매시장 가격<br />
									<span>&#40;1년/월별평균&#41;</span>
								</button></li>
							<li class="tablinks"><button type="button">
									연간 도매시장 가격 추이<br />
									<span>&#40;5년/년별평균&#41;</span>
								</button></li>
						</ul>
						<div class="tabContWrap">
							<div class="tabcontent on">
								<!-- 당근 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 평균 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 평균 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 평균 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 상품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 상품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 상품 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 중품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 중품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 중품 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 일별 하품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 하품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 일별 하품 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 당근 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 평균 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 평균 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 평균 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 상품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 상품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 상품 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 중품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 중품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 중품 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 주별 하품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 하품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 주별 하품 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
									</div>
									<!-- //tabContWrap -->
								</div>
								<!-- //tabWrap typeB -->
							</div>
							<div class="tabcontent">
								<!-- 당근 도매시장거래가격 Tab 상/중/하품별 -->
								<div class="tabWrap typeB">
									<ul class="tabTitle">
										<li class="tablinks on"><button type="button">
												<span>평균보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>특품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>상품보기</span>
											</button></li>
										<li class="tablinks"><button type="button">
												<span>보통보기</span>
											</button></li>
									</ul>
									<div class="tabContWrap">
										<div class="tabcontent on">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 평균 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 평균 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 평균 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 상품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 상품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 상품 평년 당근 그래프 영역 -->
														</div>
													</div>
												</div>
											</div>
											<!-- //tabWrap typeC -->
										</div>
										<div class="tabcontent">
											<!-- 당근 전년/평년 -->
											<div class="tabWrap typeC">
												<ul class="tabTitle">
													<li class="tablinks on"><button type="button">
															<span>전년</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>최근5년간의평균</span>
														</button></li>
													<li class="tablinks"><button type="button">
															<span>평년</span>
														</button></li>
												</ul>
												<div class="tabContWrap">
													<div class="tabcontent on">
														<div class="graphWrap">
															<!-- 월별 중품 전년 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 중품 최근5년간의평균 당근 그래프 영역 -->
														</div>
													</div>
													<div class="tabcontent">
														<div class="graphWrap">
															<!-- 월별 중품 평년 당근 그래프 영역 -->
															</div>
														</div>
													</div>
												</div>
												<!-- //tabWrap typeC -->
											</div>
											<div class="tabcontent">
												<!-- 당근 전년/평년 -->
												<div class="tabWrap typeC">
													<ul class="tabTitle">
														<li class="tablinks on"><button type="button">
																<span>전년</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>최근5년간의평균</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>평년</span>
															</button></li>
													</ul>
													<div class="tabContWrap">
														<div class="tabcontent on">
															<div class="graphWrap">
																<!-- 월별 하품 전년 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 월별 하품 최근5년간의평균 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 월별 하품 평년 당근 그래프 영역 -->
															</div>
														</div>
													</div>
												</div>
												<!-- //tabWrap typeC -->
											</div>
										</div>
										<!-- //tabContWrap -->
									</div>
									<!-- //tabWrap typeB -->
								</div>
								<div class="tabcontent">
									<!-- 당근 도매시장거래가격 Tab 상/중/하품별 -->
									<div class="tabWrap typeB">
										<ul class="tabTitle">
											<li class="tablinks on"><button type="button">
													<span>평균보기</span>
												</button></li>
											<li class="tablinks"><button type="button">
													<span>특품보기</span>
												</button></li>
											<li class="tablinks"><button type="button">
													<span>상품보기</span>
												</button></li>
											<li class="tablinks"><button type="button">
													<span>보통보기</span>
												</button></li>
										</ul>
										<div class="tabContWrap">
											<div class="tabcontent on">
												<!-- 당근 전년/평년 -->
												<div class="tabWrap typeC">
													<ul class="tabTitle">
														<li class="tablinks on"><button type="button">
																<span>전년</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>최근5년간의평균</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>평년</span>
															</button></li>
													</ul>
													<div class="tabContWrap">
														<div class="tabcontent on">
															<div class="graphWrap">
																<!-- 연별 평균 전년 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 평균 최근5년간의평균 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 평균 평년 당근 그래프 영역 -->
															</div>
														</div>
													</div>
												</div>
												<!-- //tabWrap typeC -->
											</div>
											<div class="tabcontent">
												<!-- 당근 전년/평년 -->
												<div class="tabWrap typeC">
													<ul class="tabTitle">
														<li class="tablinks on"><button type="button">
																<span>전년</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>최근5년간의평균</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>평년</span>
															</button></li>
													</ul>
													<div class="tabContWrap">
														<div class="tabcontent on">
															<div class="graphWrap">
																<!-- 연별 상품 전년 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 상품 최근5년간의평균 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 상품 평년 당근 그래프 영역 -->
															</div>
														</div>
													</div>
												</div>
												<!-- //tabWrap typeC -->
											</div>
											<div class="tabcontent">
												<!-- 당근 전년/평년 -->
												<div class="tabWrap typeC">
													<ul class="tabTitle">
														<li class="tablinks on"><button type="button">
																<span>전년</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>최근5년간의평균</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>평년</span>
															</button></li>
													</ul>
													<div class="tabContWrap">
														<div class="tabcontent on">
															<div class="graphWrap">
																<!-- 연별 중품 전년 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 중품 최근5년간의평균 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 중품 평년 당근 그래프 영역 -->
															</div>
														</div>
													</div>
												</div>
												<!-- //tabWrap typeC -->
											</div>
											<div class="tabcontent">
												<!-- 당근 전년/평년 -->
												<div class="tabWrap typeC">
													<ul class="tabTitle">
														<li class="tablinks on"><button type="button">
																<span>전년</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>최근5년간의평균</span>
															</button></li>
														<li class="tablinks"><button type="button">
																<span>평년</span>
															</button></li>
													</ul>
													<div class="tabContWrap">
														<div class="tabcontent on">
															<div class="graphWrap">
																<!-- 연별 하품 전년 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 하품 최근5년간의평균 당근 그래프 영역 -->
															</div>
														</div>
														<div class="tabcontent">
															<div class="graphWrap">
																<!-- 연별 하품 평년 당근 그래프 영역 -->
															</div>
														</div>
													</div>
												</div>
												<!-- //tabWrap typeC -->
											</div>
										</div>
										<!-- //tabContWrap -->
									</div>
									<!-- //tabWrap typeB -->
								</div>
								<!-- //tabcontent -->
							</div>
							<!-- //tabContWrap -->
						</div>
						<!-- //tabWrap typeA -->
					</div>
			</li>
		</ul>
	</section>

	<!-- 우측메뉴영역 -->
	<section class="menu">
		<!-- <ul class="basic_button">
			<li><a href="#">검색</a></li>
			<li><a href="#">공유하기</a></li>
			<li><a href="#">마이페이지</a></li>
		</ul> -->
		<div class="weatherWrap">
			<h3>
				${today}<span class="hide">날씨</span>
			</h3>
			<!--  날씨 아이콘 불러오기 -->
			<!-- ${SKY} : 0 - 5 : 맑음 , 6 ~ 8 : 구름많음, 9 ~ 10 흐림 -->
			<p class="wPic">
				<c:if test="${SKY <= 5}">
					<img src="<c:url value='/images/bigdata/weather01.png' />" alt="맑음" />
				</c:if> 
				<c:if test="${SKY >= 6 && SKY <= 8}">
					<img src="<c:url value='/images/bigdata/weather03.png' />" alt="구름많음" />
				</c:if> 
				<c:if test="${SKY > 8}">
					<img src="<c:url value='/images/bigdata/weather02.png' />" alt="흐림" />
				</c:if> 
				<span>강수확률 <em>${POP}%</em></span>
			</p>
			<ul>
				<li><span>최저기온</span> <strong><em>${TMN}</em> &#176;c</strong></li>
				<li><span>최고기온</span> <strong><em>${TMX}</em> &#176;c</strong></li>
				<li><span>풍향</span> <strong><em>${VEC}</em></strong></li>
				<li><span>풍속</span> <strong><em>${WSD}</em>m/s</strong></li>
				<li><span>강수량</span> <strong><em>${R06}</em> ml</strong></li>
				<li><span>습도</span> <strong><em>${REH}</em>&#37;</strong></li>
			</ul>
		</div>
	</section>
</div>
