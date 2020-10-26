<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<header id="header">
		<c:set var="userAgentInfo" value="${fn:toLowerCase(header['User-Agent'])}" />
		<c:set var="isMobile" value="${fn:indexOf(userAgentInfo, 'mobi')}" />

		<c:if test="${isRoleAdmin == false}">
		<div class="util_wrap">
		    <div>
		        <span><a href="/bdp/">마이데이터</a></span>
		        <span><a href="">계정관리</a></span>
		        <span><a href="">권한승인요청</a></span>
		    </div>
		</div>
		</c:if>

		<!-- S : 메뉴영역 -->
		<gnb id="gnb">
			<nav>
				<logo id="logo"><a href="/bdp/main/main.do"><img src="/images/bigdata/logo.svg" alt="농림축산식품부 빅데이터 포털"></a></logo>
				<ul class="menu admin_mode"><!-- 관리자 모드일 때 class="admin_mode" 추가 -->
					<li>
						<a href="/bdp/dataset/list.do" target="_blank">데이터셋</a>
						<ul class="smenu">
							<li><a href="/bdp/dataset/list.do" target="_self">빅데이터셋</a></li>
							<li><a href="/bdp/dataset/list.do?searchType1=STA" target="_self">통계데이터</a></li>
							<li><a href="/bdp/dataset/public/list.do" target="_self">공공데이터</a></li>
							<li><a href="/bdp/dataset/list.do?searchType1=PRI" target="_self">민간데이터</a></li>
							<li><a href="/bdp/dataset/list.do?searchType1=BDS&spceDataSe=Y" target="_self">공간데이터</a></li>
							<li><a href="/bdp/dmm/main.do" target="_self">데이터맵</a></li>
						</ul>
					</li>
					<c:if test="${isLogin == true && isRoleUser != true}">
					<li>
						<%-- <a href="http://210.92.91.199:25601">데이터분석 및 시각화</a> --%> <%--개발 --%>
						<a href="http://bigdata.agrion.kr/kibana" target="_blank">데이터분석 및 시각화</a> <%--운영 --%>
						<ul class="smenu">
 							<%-- <li><a href="http://210.92.91.199:25601">Kibana(분석 및 시각화)</a></li>--%> <%-- 개발 키바나 --%>
							<li><a href="http://bigdata.agrion.kr/kibana" target="_blank">Kibana(분석 및 시각화)</a></li> <%-- 운영 키바나 --%>


 							<%-- <li><a href="http://mango.iptime.org:3413/studio">Pinogio(공간분석)</a></li> --%> <%--개발 --%>
							<li><a href="http://bigdata.agrion.kr/pinogio" target="_blank">Pinogio(공간분석)</a></li> <%--운영 --%>

							<%-- <li><a href="http://210.92.91.199:28787" >R Studio(직접자기분석)</a></li>  --%> <%--개발 --%>
							<li><a href="http://bigdata.agrion.kr/rstudio/" target="_blank">R Studio(직접자기분석)</a></li> <%--운영 --%>
						</ul>
					</li>
					</c:if>
					<!-- <li>
						<a href="/bdp/visual/list.do">시각화</a>
						<ul class="smenu">
							<li><a href="/bdp/visual/list.do">시각화리스트</a></li>
							<li><a href="/bdp/visual/create.do">시각화하기</a></li>
						</ul>
					</li> -->
					<li>
						<a href="javascript:;">활용지원</a>
						<ul class="smenu">
							<li><a href="/bdp/prcuse/noticeList.do">공지사항</a></li>
							<li><a href="/bdp/prcuse/visualizePblonsipList.do">시각화공유</a></li>
							<li><a href="/bdp/prcuse/questionList.do">문의사항</a></li>
							<li><a href="/bdp/prcuse/analysisReqList.do">분석대행신청</a></li>
							<li><a href="/bdp/prcuse/newBigdatasetReqList.do">신규빅데이터셋</a></li>
						</ul>
					</li>
					<c:if test="${isRoleAdmin == true || isRoleAnalyst == true}">
						<li>
							<a href="javascript:;">모델 및 서비스</a>
							<ul class="smenu">
									<!-- <li><a href="/bdp/svc/retnFmlg/retnFmlgInfo.do" target="_blank">귀농의사결정지원</a></li> -->
									<li><a href="/ReturnFarm" target="_blank">귀농의사결정지원</a></li>
									<li><a href="/bdp/svc/fixesSportPolicySearchList.do" target="_blank">맞춤 지원정책 안내</a></li>

								<c:if test="${isRoleAdmin == true}">
									<li><a href="/bdp/svc/riceDetail.do" target="_blank">쌀 생산유통 상시분석</a></li>
									<c:if test="${isMobile >= 0}">
										<li><a href="/bdp/svc/mJejuDetail.do" target="_blank">제주도 월동작물 생산량 분석</a></li>
									</c:if>
									<c:if test="${isMobile < 0}">
										<li><a href="/bdp/svc/jejuDetail.do" target="_blank">제주도 월동작물 생산량 분석</a></li>
									</c:if>
									<li><a href="http://118.41.246.206:9999/" target="_blank">SAP <i class="ico blank"></i></a></li>
								</c:if>

							</ul>
						</li>
					</c:if>
					<c:if test="${isRoleAdmin == true}">
					<li>
						<a href="/uss/umt/EgovMberManage.do">모니터링/관리</a>
						<ul class="smenu">
							<li><a href="/uss/umt/EgovMberManage.do">사용자관리</a></li>
							<li><a href="/sec/rgm/EgovAuthorGroupList.do">권한관리</a></li>
							<!-- <li><a href="/bdp/cmng/scheManagerList.do">스케줄관리</a></li>
							<li><a href="/bdp/exview/view.do?viewId=0090" target="_blank">하둡모니터링/관리</a></li> -->
							<li><a href="/bdp/admin/dataset/datalist.do">빅데이터목록 관리</a></li>
							<li><a href="/bdp/admin/dataset/list.do">빅데이터셋 관리</a></li>
							<!-- <li><a href="http://192.168.4.50:5601/app/monitoring#/overview?_g=(cluster_uuid:\'9D3m-eDgSjurIuUdBypXmA\">Kibana관리</a> -->
							<li><a href="http://bigdata.agrion.kr/kibana/app/monitoring">kibana 관리</a>
							<li><a href="http://10.10.10.53:8080">Ambari관리</a>
							<li><a href="http://211.237.50.23:9090">Airflow관리</a>
						</ul>
					</li>
					</c:if>

					<c:if test="${isLogin == true && isRoleAdmin != true}">
					<li>
						<a href="/bdp/mypage/myDataList.do">마이페이지</a>
						<ul class="smenu">
							<!-- <li><a href="/bdp/user/list.do">내 데이터 목록</a></li>
							<li><a href="/bdp/user/mypage.do">내 정보 변경</a></li>
							<li><a href="/bdp/user/uploadList.do">내 파일업로드 목록</a> -->
							<li><a href="/bdp/mypage/myDataList.do">내 데이터 목록</a></li>
						</ul>
					</li>
					</c:if>
					<c:if test="${isLogin == true && isRoleAdmin == true}">
					<li>
						<a href="/bdp/mypage/myDataList.do">데이터업로드</a>
						<ul class="smenu">
							<li><a href="/bdp/mypage/myDataList.do">데이터업로드</a></li>
						</ul>
					</li>
					</c:if>

				</ul>
				<div class="menu_slide"></div>
				<div class="util">
					<c:if test="${isLogin != true}">
						<a href="/bdp/auth/login.do"><i class="ico login"></i>로그인</a>
					</c:if>
					<c:if test="${isLogin == true}">
						<span><strong><c:out value="${loginUserName}" /></strong>님</span>
						<a href="<c:url value='/bdp/auth/logout.do'/>"><i class="ico logout"></i>로그아웃</a>
					</c:if>
				</div>
				<div id="ico_bar">
					<span></span>
					<span></span>
					<span></span>
				</div>
			</nav>
		</gnb>
		<!-- E : 메뉴영역 -->

	</header>



