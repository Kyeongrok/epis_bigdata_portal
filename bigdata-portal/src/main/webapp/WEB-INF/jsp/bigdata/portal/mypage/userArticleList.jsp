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
				<th>상태</th>
				<th>내용</th>
				<th>링크</th>
				<th>등록일</th>
				<th>IP</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td>${articleNo-status.index}</td>
				<td>${row.userId}</td>
				<td><c:choose>
					<c:when test="${row.artStatus eq 'REG'}">${row.artStatusStr}</c:when>
					<c:when test="${row.artStatus eq 'MOD'}">${row.artStatusStr}</c:when>
					<c:when test="${row.artStatus eq 'DEL'}">${row.artStatusStr}</c:when>
				</c:choose></td>				
				<td>${row.artCont}</td>				
				<td><c:choose>
						<c:when test="${row.artStatus eq 'REG'}"><a href="<c:url value="${row.artUrl}" />" class="btn grey">보기</a></c:when>
						<c:when test="${row.artStatus eq 'DEL'}"></c:when>
					</c:choose></td>
				<td><fmt:formatDate pattern="yyyy.MM.dd" value="${row.artDttm}" /></td>
				<td>${row.artIp}</td>
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