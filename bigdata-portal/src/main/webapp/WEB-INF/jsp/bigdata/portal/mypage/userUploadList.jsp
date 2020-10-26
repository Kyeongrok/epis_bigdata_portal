<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>


<div class="total">
	<span class="count">전체
		<strong><c:out value='${totalCount}' /></strong>건	</span>
</div>

<div class="tablearea">

	<table class="tbl01 column7" summary="사용자 파일업로드 리스트 페이지입니다.">
		<caption>사용자 파일업로드 리스트</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>아이디</th>
				<th>업로드페이지</th>
				<th>파일명</th>
				<th>파일크기</th>
				<th>등록일</th>
				<th>업로드IP</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td>${articleNo-status.index}</td>
				<td>${userId}</td>
				<td>${row.targetPage}</td>
				<td>${row.fileName}&nbsp;<a href="/csv/download/uploadLogFile.do?ulId=${row.ulId}" title="${row.fileName}을 다운로드 합니다.">다운로드</a></td>
				<td>${row.fileUnit}</td>
				<td><fmt:formatDate pattern="yyyy.MM.dd" value="${row.uploadDttm}" /></td>
				<td>${row.uploadIp}</td>
			</tr>
			</c:forEach>
			<c:if test="${totalCount == 0}">
				<tr>
					<td colspan="7" class="nodata">데이터가 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	
	<div class="tar mt20 disinblock w100p">
		<a href="<c:url value="/uss/umt/EgovMberManage.do" />" class="btn grey">회원관리 목록으로 이동</a>
	</div>
	<!-- pagination -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
	
</div>

<script type="text/javascript">
function fn_link_page(pageNo) {
	location.href = "<c:url value='/bdp/uploadList.do'/>?pageIndex="+pageNo;
}
