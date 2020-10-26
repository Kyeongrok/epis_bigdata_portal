<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>



<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/dataset/public/list.do'/>" >
		<select name="searchListSel" title="검색 조건 선택">
			<option value="all" <c:if test="${searchListSel == 'all'}">selected="selected"</c:if> >전체</option>
			<option value="pdlCateOld" <c:if test="${searchListSel == 'pdlCateOld'}">selected="selected"</c:if> >분류</option>
			<option value="dataList" <c:if test="${searchListSel == 'dataList'}">selected="selected"</c:if> >데이터목록</option>
			<option value="dataSet" <c:if test="${searchListSel == 'dataSet'}">selected="selected"</c:if> >공공데이터명</option>
			<option value="pdlOrgName" <c:if test="${searchListSel == 'pdlOrgName'}">selected="selected"</c:if> >제공기관</option>
		</select>

		<span class="searcharea">
			<input type="text" name="searchListWord" id="searchListWord" value="<c:out value='${searchListWord}'/>" title="검색 단어 입력"/>
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
	</form>
</div>


<div class="tablearea">
	<div class="total">
		<span class="count"><strong>${pageInfo.totalRecordCount}</strong>건</span>
		<div class="btnarea">
			<a href="http://data.mafra.go.kr" target="_blank" class="btn red">농식품 공공데이터 포털 바로가기<i class="ico ico_arrow"></i></a>
		</div>
	</div>
	

	<table class="tbl01 column8" summary="농식품 공공데이터셋 리스트 게시판입니다.">
		<caption>농식품 공공데이터셋 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>데이터목록명</th>
				<th>공공데이터명</th>
				<th>분류</th>
				<th>제공기관</th>
				<th>조회수</th>
				<th>등록일자</th>
				<th>서비스 유형</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${publicDataList}" varStatus="status">
			<tr>
				<td>${articleNo-status.index}</td>
				<td>${row.koreanNm}</td>
				<td><a href="${row.viewLink}" target="_blank">${row.dataNm}</a></td>
				<td>
					<c:if test="${row.pClnm != ''}">
						<span class="depth">${row.pClnm}</span>
					</c:if>
					<span class="depth">${row.clNm}</span>
				</td>
				<td>${row.insttNm}</td>
				<td>${row.rdcnt}</td>
				<td><fmt:formatDate value="${row.dsRegistDt}" pattern="yyyy-MM-dd" /></td>
				<td><c:set var="provdOpenStatus" value="${fn:replace(row.provdOpenStatus, ',', ', ')}" />${provdOpenStatus}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!-- pagination -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
</div>

<script type="text/javascript">
function fn_link_page(pageNo) {
	location.href = "<c:url value='/bdp/dataset/public/list.do'/>?pageIndex="+pageNo;
}

$(function() {
	$("#searchListBtn").click(function() {
		
		var searchListWord = $("#searchListWord").val();
		if(searchListWord == "") {
			alert('검색 단어를 입력하세요!');
			return false;
		} else {
			var frm = document.searchListFrm;
			frm.submit();
		}

	});
	
});
</script>