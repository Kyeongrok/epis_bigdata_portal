<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>


<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/dataset/list.do'/>" >
		<input type="hidden" name="searchType1" value="<c:out value='${searchType1}'/>" />
		<select name="searchCondition" title="검색 조건 선택">
			<option value="all" <c:if test="${searchCondition == 'all'}">selected="selected"</c:if> >전체</option>
			<option value="dataList" <c:if test="${searchCondition == 'dataList'}">selected="selected"</c:if> >데이터목록</option>
			<option value="dataSet" <c:if test="${searchCondition == 'dataSet'}">selected="selected"</c:if> >데이터명</option>
			<option value="orgName" <c:if test="${searchCondition == 'orgName'}">selected="selected"</c:if> >제공기관</option>
		</select>

		<span class="searcharea">
			<input type="text" name="searchKeyword" id="searchKeyword" value="<c:out value='${searchKeyword}'/>" title="검색 단어 입력"/>
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
	</form>
</div>

<div class="tablearea">
	<div class="total">
		<span class="count"><strong><c:out value='${pageInfo.totalRecordCount}'/></strong>건</span>
	</div>

	<table class="tbl01 column6_bdata2" summary="공간데이터 리스트 게시판입니다.">
		<caption>공간 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>데이터목록</th>
				<th>데이터명</th>
				<th>제공기관</th>
				<th>등록일자</th>
				<th>맵보기</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td><c:out value='${articleNo-status.index}'/></td>
				<td><span class="depth">${row.dlName}</span></td>
				<td><a href="<c:url value='/bdp/dataset/spceView.do'/>?dsId=${row.dsId}&esIndexNm=${row.esIndexNm}&pageIndex=${pageIndex}"><c:out value='${row.dsName}'/></a></td>
				<td><c:out value='${row.orgName}'/></td>
				<td><c:out value='${row.dsLastUpdateDate}'/></td>
<!-- 				<td><a href="javascript:window.open('http://211.237.50.27:8080/pinogio-web')">피노지오</a></td> -->
				<td><a href="javascript:window.open('${row.mapViewUrl}')">피노지오</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!-- pagination -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
</div>

<script type="text/javascript">
function fn_link_page(pageNo) {
	location.href = "<c:url value='/bdp/dataset/list.do'/>?pageIndex="+pageNo;
}

$(function() {
	$("#searchListBtn").click(function() {

		var searchKeyword = $("#searchKeyword").val();
		if(searchKeyword == "") {
			alert('검색 단어를 입력하세요!');
			return false;
		} else {
			var frm = document.searchListFrm;
			frm.submit();
		}

	});

});
</script>