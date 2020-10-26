<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>


<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/admin/dataset/datalist.do'/>" >
		<c:if test="${searchType1 != ''}">
		<input type="hidden" name="searchType1" value="${searchType1}" />
		</c:if>
		<select name="searchCondition" title="검색 조건 선택">
			<option value="all" <c:if test="${searchCondition == 'all'}">selected="selected"</c:if> >전체</option>
			<option value="dlTypeName" <c:if test="${dlTypeName == 'dlTypeName'}">selected="selected"</c:if> >구분</option>
			<option value="dataCateDept" <c:if test="${searchCondition == 'dataCateDept'}">selected="selected"</c:if> >분류</option>
			<option value="dataList" <c:if test="${searchCondition == 'dataList'}">selected="selected"</c:if> >데이터목록명</option>
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
		<div class="btnarea">			
			<a href="/bdp/admin/dataset/datalist.do" class="btn <c:choose><c:when test='${searchType1 == ""}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">전체</a>
			<a href="/bdp/admin/dataset/datalist.do?searchType1=BDS" class="btn <c:choose><c:when test='${searchType1 == "BDS"}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">빅데이터셋</a>
			<a href="/bdp/admin/dataset/datalist.do?searchType1=STA" class="btn <c:choose><c:when test='${searchType1 == "STA"}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">통계데이터</a>
			<a href="/bdp/admin/dataset/datalist.do?searchType1=PRI" class="btn <c:choose><c:when test='${searchType1 == "PRI"}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">민간데이터</a>
		</div>
	</div>

	<form name="listForm" method="get">	
	<table class="tbl01 adm_tbl01 column10_mber" summary="빅데이터셋 관리 리스트 게시판입니다.">
		<caption>빅데이터셋 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>구분</th>
				<th>분류</th>
				<th>데이터목록명</th>
				<th>제공기관</th>
				<th>데이터제공일자</th>
				<th>사용여부</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td><c:out value='${articleNo-status.index}'/></td>
				<td>${row.dlTypeName}</td>
				<td><span class="depth">${row.dlCateDept1}</span><c:if test="${not empty row.dlCateDept2}"><span class="depth">${row.dlCateDept2}</span></c:if></td>
				<td><a href="<c:url value='/bdp/admin/dataset/dataView.do' />?dlId=${row.dlId}&pageIndex=${pageIndex}">${row.dlName}</a></td>
				<td><a href="<c:url value='/bdp/admin/dataset/dataView.do' />?dlId=${row.dlId}&pageIndex=${pageIndex}">${row.orgName}</a></td>
				<td>${row.dlFirstReleaseDate}</td>
				<td>
					<select name="dlUseAt" class="dlUseAt" data-dlId="${row.dlId}">
						<option value="Y" <c:if test="${row.dlUseAt == 'Y'}">selected="selected"</c:if>>사용</option>
						<option value="N" <c:if test="${row.dlUseAt == 'N'}">selected="selected"</c:if>>사용안함</option>
						<option value="R" <c:if test="${row.dlUseAt == 'R'}">selected="selected"</c:if>>보류</option>
					</select>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	
	<!-- pagination -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
</div>

<script type="text/javascript">
$(function() {
	$(".dlUseAt").change(function() {
		var dlId = $(this).attr("data-dlId");
		var dlUseAt = $(this).val();
		$.post("./dlUseAtUpdate.do", {"dlId":dlId, "dlUseAt":dlUseAt}, function(json) {
			if(json.result > 0) {
				alert('수정되었습니다.');
			} else {
				alert('데이터를 수정하지 못했습니다.');
			}
		});
	});
	
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
})
</script>