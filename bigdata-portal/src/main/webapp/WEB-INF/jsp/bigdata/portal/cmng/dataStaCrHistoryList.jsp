<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.form.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/js/vendor/spectrum/spectrum.js" />" charset="utf-8"></script>
<link rel="stylesheet" href="<c:url value="/js/vendor/spectrum/spectrum.css" />" type="text/css" />


<div class="tablearea">
	<input type="hidden" name="pageIndex" value="${pageIndex}" />
	<div class="total">
		<span class="count"><strong>${totalCount}</strong>건</span>
	</div>
	<table class="tbl01 column8" summary="통계데이터 수집 히스토리 리스트입니다.">
		<caption>통계데이터 수집 히스토리 리스트 테이블</caption>
		<thead>
			<tr>
				<c:choose>
					<c:when  test="${dcsId == 'DCS_STA'}">
						<th>번호</th>
						<th>수집 시작 날짜</th>
						<th>수집 종료 날짜</th>
						<th>변경된 통계표 아이디</th>
						<th>변경된 데이터 생성 시점</th>
						<th>변경된 데이터 갯수</th>
						<th>삭제된 데이터 갯수</th>
						<th>업데이트 상태</th>
					</c:when >
					<c:otherwise>
						<th>번호</th>
						<th>수집 시작 날짜</th>
						<th>수집 종료 날짜</th>
						<th>변경된 테이블 아이디</th>
						<th>전체 데이터 갯수</th>
						<th>수집된 데이터 갯수</th>
						<th>업데이트 상태</th>
						<th>에러메세지</th>
					</c:otherwise>
				</c:choose>			
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${crHistoryList}" varStatus="status">
			<tr>
				<c:choose>
					<c:when  test="${dcsId == 'DCS_STA'}">
						<td>${articleNo-status.index}</td>
						<td>${row.staStartDttm}</td>
						<td>${row.staEndDttm}</td>
						<td>${row.staUpdateTblId}</td>
						<td>${row.staUpdateDe}</td>
						<td>${row.staUpdateDataCount}</td>
						<td>${row.staDeleteDataCount}</td>
						<td>${row.staUpdateState}</td>
					</c:when>
					<c:otherwise>
						<td>${articleNo-status.index}</td>
						<td>${row.dclStartDttm}</td>
						<td>${row.dclEndDttm}</td>
						<td>${row.dclUpdateTblId}</td>
						<td>${row.dclTotalDataCount}</td>
						<td>${row.dclUpdateDataCount}</td>
						<td>${row.dclUpdateResult}</td>	
						<td>${row.dclErrorMessage}</td>						
					</c:otherwise>
				</c:choose>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- pagination -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />	
</div>