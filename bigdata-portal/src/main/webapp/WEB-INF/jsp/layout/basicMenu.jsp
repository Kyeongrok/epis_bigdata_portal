<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- <c:set var="menuNum" value="${menuNum}" /> --%>
<%-- <c:set var="pageNum" value="${pageNum}"/> --%>

<c:choose>
	<c:when test="${menuNum eq 1}">
				<lnb id="lnb">
					<div class="wrapper">
						<ul class="local column6">
							<li class="<c:if test="${pageNum eq 1}">select</c:if>"><a href="/bdp/dataset/list.do">빅데이터셋</a></li>
							<li class="<c:if test="${pageNum eq 2}">select</c:if>"><a href="/bdp/dataset/list.do?searchType1=STA">통계데이터</a></li>
							<li class="<c:if test="${pageNum eq 3}">select</c:if>"><a href="/bdp/dataset/public/list.do">공공데이터</a></li>
							<li class="<c:if test="${pageNum eq 4}">select</c:if>"><a href="/bdp/dataset/list.do?searchType1=PRI">민간데이터</a></li>
							<li class="<c:if test="${pageNum eq 6}">select</c:if>"><a href="/bdp/dataset/list.do?searchType1=BDS&spceDataSe=Y">공간데이터</a></li>
							<li class="<c:if test="${pageNum eq 5}">select</c:if>"><a href="/bdp/dmm/main.do">데이터맵</a></li>
						</ul>
					</div>
				</lnb>
	</c:when>
	
	<c:when test="${menuNum eq 2}">
				<lnb id="lnb">
					<div class="wrapper">
						<ul class="local column3">
							<li class="<c:if test="${pageNum eq 1}">select</c:if>"><a href="/bdp/analysis/create.do">분석하기</a></li>
							<li class="<c:if test="${pageNum eq 2}">select</c:if>"><a href="/bdp/analysis/merge.do">결합하기</a></li>
							<li class="<c:if test="${pageNum eq 3}">select</c:if>"><a href="/bdp/exview/view.do?viewId=0030" target="_blank">RStudio</a></li>
						</ul>
					</div>
				</lnb>
	</c:when>

	<c:when test="${menuNum eq 3}">
				<lnb id="lnb">
					<div class="wrapper">
						<ul class="local column2">
							<li class="<c:if test="${pageNum eq 1}">select</c:if>"><a href="/bdp/visual/list.do">시각화리스트</a></li>
							<li class="<c:if test="${pageNum eq 2}">select</c:if>"><a href="/bdp/visual/create.do">시각화하기</a></li>
						</ul>
					</div>
				</lnb>
	</c:when>
	
	<c:when test="${menuNum eq 5}">
				<!-- 관리자 -->
				<%-- <lnb id="lnb">
					<div class="wrapper">
						<ul class="local column4"><!-- 탭 갯수에 따라 column1~4 가 들어가면 됩니다. -->
							<li class="<c:if test="${pageNum eq 1}">select</c:if>">								
								<a href="/uss/umt/EgovMberManage.do">사용자관리</a>
							</li>						
							<li class="<c:if test="${pageNum eq 4}">select</c:if>">
								<a href="/sec/rgm/EgovAuthorGroupList.do">권한관리</a>
							</li>									
							<li class="<c:if test="${pageNum eq 2}">select</c:if>">
								<a href="/bdp/cmng/scheManagerList.do">스케줄관리</a>
							</li>
							<li class="<c:if test="${pageNum eq 3}">select</c:if>">
								<a href="/bdp/exview/view.do?viewId=0090" target="_blank">하둡모니터링/관리</a>
							</li>
						
						</ul>
					</div>
				</lnb> --%>
	</c:when>
	
	<c:when test="${menuNum eq 6}">
		<!-- 사용자 -->
		<lnb id="lnb">
			<div class="wrapper">
				<ul class="local column2"><!-- 탭 갯수에 따라 column1~4 가 들어가면 됩니다. -->
					<li class="<c:if test="${pageNum eq 3}">select</c:if>">
						<a href="/bdp/user/list.do">내 데이터 목록</a>
					</li>
					<li class="<c:if test="${pageNum eq 1}">select</c:if>">								
						<a href="/bdp/user/mypage.do">내 정보 변경</a>
					</li>
				</ul>
			</div>
		</lnb>
	</c:when>
	
</c:choose>

	
	
