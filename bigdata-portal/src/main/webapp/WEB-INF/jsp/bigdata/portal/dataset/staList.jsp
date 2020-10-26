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
			<option value="dataList" <c:if test="${searchCondition == 'dataList'}">selected="selected"</c:if> >통계목록</option>
			<option value="dataSet" <c:if test="${searchCondition == 'dataSet'}">selected="selected"</c:if> >통계테이블명</option>
			<option value="orgName" <c:if test="${searchCondition == 'orgName'}">selected="selected"</c:if> >제공기관</option>
<%-- 			<option value="dsLastUpdateDate" <c:if test="${searchCondition == 'dsLastUpdateDate'}">selected="selected"</c:if> >등록일자</option> --%>
		</select>
			
		<span class="searcharea">
			<input type="text" name="searchKeyword" id="searchKeyword" value="<c:out value='${searchKeyword}'/>" title="검색 단어 입력"/>
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
	</form>
</div>


<div class="tablearea">
	<div class="total">
		<span class="count"><strong>${pageInfo.totalRecordCount}</strong>건</span>
		<div class="btnarea">
			<a href="https://kass.mafra.go.kr" target="_blank" class="btn red">국가 농식품통계 서비스 바로가기<i class="ico ico_arrow"></i></a>
		</div>
	</div>
	
	<table class="tbl01 column5" summary="통계 데이터셋 리스트 게시판입니다.">
		<caption>통계 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>통계목록</th>
				<th>통계테이블명</th>
				<th>제공기관</th>
				<th>등록일자</th>
			</tr>
		</thead>
		<tbody>	
		<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td>${articleNo-status.index}</td>
				<td>${row.dlName}</td>
				<td><a href="<c:url value='/bdp/dataset/staView.do'/>?dsId=${row.dsId}&tblId=${row.htblId}&esIndexNm=${row.esIndexNm}&pageIndex=${pageIndex}">${row.dsName}</a></td>
				<td>${row.orgName}</td>
				<td>${row.dsLastUpdateDate}</td>
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