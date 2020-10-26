<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/dataset/searchAll.do'/>" >
		<span class="searcharea">
			<input type="text" name="searchKeyword" id="searchKeyword" value="<c:out value='${param.searchKeyword}' />" title="검색 단어 입력"/>
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
	</form>
</div>

<div class="tablearea">

	<h3><i class="ico dataset"></i>빅데이터셋</h3>
	<div class="total">
		<span class="count"><strong>${bdsTotal}</strong>건</span>
		<div class="btnarea">
			<a href="<c:url value='/bdp/dataset/list.do?searchType1=BDS'/>" class="btn">더보기<i class="ico ico_arrow_grey"></i></a>
		</div>
	</div>
	<table class="tbl01 column5" summary="빅데이터셋 리스트입니다.">
		<caption>빅데이터셋 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>분류</th>
				<th>데이터목록 / 데이터명</th>
				<th>제공기관</th>
				<th>등록일자</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${bdsList}" varStatus="status">
			<tr>
				<td>${bdsTotal-status.index}</td>
				<td>${row.dlCateDept1} <c:if test="${row.dlCateDept2 != 'null'}">></c:if>   ${row.dlCateDept2}</td>
				<td><span class="wrap_list">${row.dlName}</span><a href="<c:url value='/bdp/dataset/view.do'/>?dsId=${row.dsId}">${row.dsName}</a></td>
				<td>${row.orgName}</td>
				<td>${row.dsLastUpdateDate}</td>
			</tr>
		</c:forEach>
		<c:if test="${bdsTotal < 1}">
			<tr>
				<td colspan="5" class="nodata">검색결과가 없습니다.</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	

	<h3><i class="ico dataset"></i>통계 데이터셋</h3>
	<div class="total">
		<span class="count"><strong>${staTotal}</strong>건</span>
		<div class="btnarea">
			<a href="<c:url value='/bdp/dataset/list.do?searchType1=STA'/>" class="btn">더보기<i class="ico ico_arrow_grey"></i></a>
		</div>
	</div>
	<table class="tbl01 column4" summary="통계 데이터셋 리스트입니다.">
		<caption>통계 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>통계목록 / 통계테이블명</th>
				<th>제공기관</th>
				<th>등록일자</th>
			</tr>
		</thead>
		<tbody>	
		<c:forEach var="row" items="${staList}" varStatus="status">
			<tr>
				<td>${staTotal-status.index}</td>
				<td><span class="wrap_list">${row.dlName}</span><a href="<c:url value='/bdp/dataset/staView.do'/>?tblId=${row.htblId}">${row.dsName}</a></td>
				<td>${row.orgName}</td>
				<td>${row.dsLastUpdateDate}</td>
			</tr>
		</c:forEach>
		<c:if test="${staTotal < 1}">
			<tr>
				<td colspan="4" class="nodata">검색결과가 없습니다.</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	
	
	<h3><i class="ico dataset"></i>농식품 공공 데이터</h3>
	<div class="total">
		<span class="count"><strong>${pdlTotal}</strong>건</span>
		<div class="btnarea">
			<a href="<c:url value='/bdp/dataset/public/list.do'/>" class="btn">더보기<i class="ico ico_arrow_grey"></i></a>
		</div>
	</div>
	<table class="tbl01 column6_public" summary="농식품 공공데이터셋 리스트입니다.">
		<caption>농식품 공공데이터셋 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>분류</th>
				<th>데이터목록 / 공공데이터명</th>
				<th>제공기관</th>
				<th>등록일자</th>
				<th>서비스 유형</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${pdlList}" varStatus="status">
			<tr>
				<td>${pdlTotal-status.index}</td>
				<td>${row.brmClCode}</td>
				<td><span class="wrap_list">${row.koreanNm}</span><a href="${row.viewLink}" target="_blank">${row.dataNm}</a></td>
				<td>${row.insttNm}</td>
				<td><fmt:formatDate value="${row.dsRegistDt}" pattern="yyyy-MM-dd" /></td>
				<td><c:set var="provdOpenStatus" value="${fn:replace(row.provdOpenStatus, ',', ', ')}" />${provdOpenStatus}</td>
			</tr>
		</c:forEach>
		<c:if test="${pdlTotal < 1}">
			<tr>
				<td colspan="6" class="nodata">검색결과가 없습니다.</td>
			</tr>
		</c:if>
		</tbody>
	</table>


	<h3><i class="ico dataset"></i>민간데이터</h3>
	<div class="total">
		<span class="count"><strong>${priTotal}</strong>건</span>
		<div class="btnarea">
			<a href="<c:url value='/bdp/dataset/list.do?searchType1=PRI'/>" class="btn">더보기<i class="ico ico_arrow_grey"></i></a>
		</div>
	</div>
	<table class="tbl01 column5" summary="민간데이터 리스트입니다.">
		<caption>민간데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>분류</th>
				<th>데이터목록 / 데이터명</th>
				<th>제공기관</th>
				<th>등록일자</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${priList}" varStatus="status">
			<tr>
				<td>${priTotal-status.index}</td>
				<td>${row.dlCateDept1} <c:if test="${row.dlCateDept2 != 'null'}">></c:if>   ${row.dlCateDept2}</td>
				<td><span class="wrap_list">${row.dlName}</span><a href="<c:url value='/bdp/dataset/view.do'/>?dsId=${row.dsId}">${row.dsName}</a></td>
				<td>${row.orgName}</td>
				<td>${row.dsLastUpdateDate}</td>
			</tr>
		</c:forEach>
		<c:if test="${priTotal < 1}">
			<tr>
				<td colspan="5" class="nodata">검색결과가 없습니다.</td>
			</tr>
		</c:if>
		</tbody>
	</table>
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