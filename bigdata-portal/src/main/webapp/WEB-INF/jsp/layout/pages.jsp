<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--
pageUnit = 2;
pageIndex = 12;
pageTotal = 100;
		
PageInfo
currentPageNo: 12
firstPageNo: 1
firstPageNoOnPageList: 11
firstRecordIndex: 22 
lastPageNo: 50
lastPageNoOnPageList: 20
lastRecordIndex: 24
nextPageBlockNo: 30
nextPageNo: 21
pageSize: 10
pageList: (10) [20, 0, 0, 0, 0, 0, 0, 0, 0, 0]
prevPageBlockNo: 1
prevPageNo: 10
recordCountPerPage: 2
totalPageCount: 50
totalRecordCount: 100
--%>
<%--
<div>
	전체 ${pageInfo.totalRecordCount}건, ${pageInfo.currentPageNo} / ${pageInfo.totalPageCount} 페이지
</div>
--%>
<c:if test="${not empty pageInfo}">
<div class="paging">
	<div class="wrap_page">
		<a href="${pageInfo.url}pageIndex=${pageInfo.firstPageNo}" class="first <c:if test="${pageInfo.firstPageNo == pageInfo.currentPageNo}">disable</c:if>" title="처음 페이지 이동"><em>처음 페이지 이동</em></a>
		<a href="${pageInfo.url}pageIndex=${pageInfo.prevPageNoOnPageList}" class="pre <c:if test="${pageInfo.prevPageNoOnPageList == pageInfo.currentPageNo}">disable</c:if>" title="이전 페이지 이동"><em>이전 페이지 이동</em></a>
		<span class="num">		
		<c:forEach var="page" items="${pageInfo.pageList}">
			<c:choose>
			    <c:when test="${pageInfo.currentPageNo eq page}">
					<strong>${page}</strong>
			    </c:when>
			    <c:otherwise>
					<a href="${pageInfo.url}pageIndex=${page}">${page}</a>
			    </c:otherwise>
			</c:choose>
		</c:forEach>
		</span>
		<a href="${pageInfo.url}pageIndex=${pageInfo.nextPageNoOnPageList}" class="next <c:if test="${pageInfo.nextPageNoOnPageList == pageInfo.currentPageNo}">disable</c:if>" title="다음 페이지 이동"><em>다음 페이지 이동</em></a>
		<a href="${pageInfo.url}pageIndex=${pageInfo.lastPageNo}" class="last <c:if test="${pageInfo.lastPageNo == pageInfo.currentPageNo}">disable</c:if>" title="마지막 페이지 이동"><em>마지막 페이지 이동</em></a>
	</div>

	<div class="wrap_page_mobile">
		<a href="${pageInfo.url}pageIndex=${pageInfo.prevPageBlockNoMobile}" class="pre <c:if test="${pageInfo.prevPageBlockNoMobile == pageInfo.currentPageNo}">disable</c:if>" title="이전"><em>이전</em></a>
		<span class="num">		
		<c:forEach var="page" items="${pageInfo.pageListMobile}">
			<c:choose>
			    <c:when test="${pageInfo.currentPageNo eq page}">
					<strong>${page}</strong>
			    </c:when>
			    <c:otherwise>
					<a href="${pageInfo.url}pageIndex=${page}">${page}</a>
			    </c:otherwise>
			</c:choose>
		</c:forEach>
		</span>
		<a href="${pageInfo.url}pageIndex=${pageInfo.nextPageBlockNoMobile}" class="next <c:if test="${pageInfo.prevPageBlockNoMobile == pageInfo.currentPageNo}">disable</c:if>" title="다음"><em>다음</em></a>
	</div>

</div>
</c:if>
