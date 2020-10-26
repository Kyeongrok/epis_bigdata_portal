<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>


<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/dataset/list.do'/>" >
		<select name="searchCondition" title="검색 조건 선택">
			<option value="all" <c:if test="${searchCondition == 'all'}">selected="selected"</c:if> >전체</option>
			<option value="dataCateDept" <c:if test="${searchCondition == 'dataCateDept'}">selected="selected"</c:if> >분류</option>
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


	<table class="tbl01 column6_bdata" summary="빅데이터셋 리스트 게시판입니다.">
		<caption>빅데이터셋 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>분류</th>
				<th>데이터목록</th>
				<th>데이터명</th>
				<th>건수</th>
				<th>제공기관</th>
				<th>수집기간</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td><c:out value='${articleNo-status.index}'/></td>
				<td><span class="depth">${row.dlCateDept1}</span><c:if test="${row.dlCateDept2 != 'null'}"><span class="depth"><c:out value='${row.dlCateDept2}'/></span></c:if></td>
				<td><c:out value='${row.dlName}'/></td>
				<td><a href="<c:url value='/bdp/dataset/view.do'/>?dsId=${row.dsId}&esIndexNm=${row.esIndexNm}&pageIndex=${pageIndex}"><c:out value='${row.dsName}'/></a></td>
				<td><c:out value='${row.esIndexDocTotcnt}'/></td>
				<td><c:out value='${row.orgName}'/></td>
				<td>
					<c:if test="${row.dsLastUpdateDate == '9999-12-31'}">지속 수집 중</c:if>
					<c:if test="${row.dsLastUpdateDate != '9999-12-31'}"><c:out value='${row.dsLastUpdateDate}'/></c:if>
				</td>
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