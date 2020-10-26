<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<script type="text/javascript" src="<c:url value="/js/bigdata/portal/prcuse/visualizePblonsipDetail.js" />"></script>


<div class="wrap_admin">

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>시각화 공유 조회</h3>

	<form id="sf">
		<input type="hidden" name="pageIndex" value="${paramMap.pageIndex }"/>
		<input type="hidden" name="searchCondition" value="${paramMap.searchCondition }"/>
		<input type="hidden" name="searchKeyword" value="${paramMap.searchKeyword }"/>
	</form>

	<form id="df" >
	<input type="hidden" name="bbsGbn" value="${data.bbsGbn }" />
	<input type="hidden" name="nttId" value="${data.nttId}" />
	<table class="tbl02 ">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<tr>
			<th>제목</th>
			<td colspan="3">
				<!-- TODO XSS -->
				${data.nttSj }
			</td>
		</tr>
		<tr>
			<th>내용<em></em></th>
			<td colspan="3">
				<!-- TODO XSS -->
				<pre>${data.nttCn }</pre>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan="3">
				<c:if test="${!empty files }">
					<c:forEach items="${files }" var="row">
						<div>
							<img src="./visualizePblonsipDwld.do?atchFileId=${row.atchFileId }&fileNo=${row.fileNo }" width="800" height="600" alt="${row.originFileName }"/>
						</div>
					</c:forEach>
				</c:if>


				<c:if test="${!empty files }">
					<c:forEach items="${files }" var="row">
						<div>${row.originFileName } (${row.fileSize }바이트)</div>
					</c:forEach>
				</c:if>

				<c:if test="${empty files }">
					<div>첨부파일이 없습니다.</div>
				</c:if>
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>
				${data.ntcrNm } (${data.ntcrId })
			</td>
			<th>작성일시</th>
			<td>
				<fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${data.frstRegistPnttm }"/>
			</td>
		</tr>
	</table>
	</form>

	<div class="tac mt20 disinblock w100p">

		<div class="floatr">
			<c:if test="${isRoleAdmin == true || isRoleAnalyst == true || isRoleUser == true}">
				<c:if test="${isRoleUser && loginUserId == data.ntcrId }">
					<a href="javascript:;"  class="btn red updt">수정</a>
					<a href="javascript:;"  class="btn red delete">삭제</a>
				</c:if>
				<c:if test="${isRoleAnalyst && loginUserId == data.ntcrId }">
					<a href="javascript:;"  class="btn red updt">수정</a>
					<a href="javascript:;"  class="btn red delete">삭제</a>
				</c:if>
				<c:if test="${isRoleAdmin == true}">
					<a href="javascript:;"  class="btn red updt">수정</a>
					<a href="javascript:;"  class="btn red delete">삭제</a>
				</c:if>
			</c:if>
			<a href="javascript:;"  class="btn list">목록</a>
		</div>
	</div>

</div>
