<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<script type="text/javascript" src="<c:url value="/js/bigdata/portal/prcuse/visualizePblonsipList.js" />"></script>

<div class="sub_top_box">
    <h2>활용지원 - 시각화공유</h2>
<!--     <h2>시각화공유</h2> -->
</div>

<div class="searchbox">
	<form name="searchListFrm" method="get"  >
		<input type="hidden" name="pageIndex" value="${paramMap.pageIndex }"/>
		<select name="searchCondition" title="검색 조건 선택">
			<option value="nttSj" <c:if test="${paramMap.searchCondition == 'nttSj'}">selected="selected"</c:if> >제목</option>
		</select>

		<span class="searcharea">
			<input type="text" name="searchKeyword" id="searchKeyword" value="<c:out value='${paramMap.searchKeyword}'/>" title="검색 단어 입력"/>
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
	</form>
</div>

<div class="tablearea">
	<div class="total">
		<span class="count"><strong><c:out value='${pageInfo.totalRecordCount}'/></strong>건</span>
	</div>


	<table class="tbl01 " summary="시각화공유 리스트 게시판입니다.">
		<caption>시각화 공유 리스트 테이블</caption>
		<colgroup>
			<col width="10%"/>
			<col width="*"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="10%"/>
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>조회수</th>
				<th>등록자</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${datas}" varStatus="status">
			<tr>
				<td>${articleNo - status.index }</td>
				<td>
					<a href="javascript:;" class="detail">${row.nttSj }</a>
					<input type="hidden" name="nttId" value="${row.nttId }"/>
					<input type="hidden" name="nttNo" value="${row.nttNo }"/>
				</td>
				<td>${row.rdcnt }</td>
				<td>${row.ntcrNm }</td>
				<td><fmt:formatDate pattern="yyyy.MM.dd"  value="${row.frstRegistPnttm}"/></td>
			</tr>
		</c:forEach>

		<c:if test="${empty datas }">
			<tr><td colspan="4">데이터가 없습니다.</td></tr>
		</c:if>
		</tbody>
	</table>

	<!-- pagination -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
</div>

<c:if test="${isRoleAdmin == true || isRoleAnalyst == true || isRoleUser == true}">
	<a href="<c:url value='./visualizePblonsipRegistForm.do'/>" class="floatr btn red">등록</a>
</c:if>


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