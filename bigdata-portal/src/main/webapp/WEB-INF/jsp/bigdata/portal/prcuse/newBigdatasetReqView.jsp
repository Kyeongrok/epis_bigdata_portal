<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<script type="text/javascript" src="<c:url value="/js/bigdata/portal/prcuse/newBigdatasetReqView.js" />"></script>


<div class="wrap_admin">

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>신규빅데이터셋요청</h3>

	<form id="sf">
		<input type="hidden" name="pageIndex" value="${paramMap.pageIndex }"/>
		<input type="hidden" name="searchCondition" value="${paramMap.searchCondition }"/>
		<input type="hidden" name="searchKeyword" value="${paramMap.searchKeyword }"/>
	</form>

	<form id="df" >
	<input type="hidden" name="bbsGbn" value="${data.bbsGbn }" />
	<input type="hidden" name="nttId" value="${data.nttId}" />
	<input type="hidden" name="nttNo" value="${data.nttNo}" />
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
				${data.nttSj}
			</td>
		</tr>
		<tr>
			<th>등록일</th>
			<td>
				<fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${data.frstRegistPnttm }"/>
			</td>
			<th>조회수</th>
			<td>
				${data.rdcnt}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<!-- TODO XSS -->
				<pre>${data.nttCn }</pre>
			</td>
		</tr>
		<tr>
			<th colspan="4">댓글</th>
		</tr>
		<c:if test="${!empty rplies }">
			<c:forEach items="${rplies }" var="row">
				<tr>
					<td colspan="4">
						<pre>* 댓글작성자 : ${row.ntcrNm} (<fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${row.frstRegistPnttm }"/>) <br> ${row.nttCn}</pre>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${isRoleAdmin || loginUserId == data.ntcrId }">
			<tr>
				<td colspan="4">
					<!-- TODO XSS -->
					<textarea name="rplyCn" rows="5" cols="100" class="w100p" title="내용" >${data.rplyCn }</textarea>
				</td>
			</tr>
		</c:if>
	</table>
	</form>

	<div class="tac mt20 disinblock w100p">

		<div class="floatr">
			<c:if test="${isRoleAdmin || loginUserId == data.ntcrId }">
				<a href="javascript:;"  class="btn red updt">수정</a>
				<a href="javascript:;"  class="btn red delete">삭제</a>
				<a href="javascript:;"  class="btn red rply">댓글등록</a>
			</c:if>
			<a href="javascript:;"  class="btn list">목록</a>
		</div>
	</div>

</div>
