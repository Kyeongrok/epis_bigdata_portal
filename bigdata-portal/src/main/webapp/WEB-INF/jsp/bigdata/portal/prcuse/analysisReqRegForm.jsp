<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<script type="text/javascript" src="<c:url value="/js/bigdata/portal/prcuse/analysisReqRegForm.js" />"></script>

<div class="wrap_admin">

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>분석대행신청</h3>

	<form name="rf" id="rf">
	<input type="hidden" name="bbsGbn" value="A" />
	
	<table class="tbl02 column3">
		<tr>
			<th>제목<em>*</em></th>
			<td>
				<input type="text" name="nttSj"  class="w100p" title="제목" data-vreq/>
			</td>
		</tr>
		<tr>
			<th>내용<em>*</em></th>
			<td>
				<textarea name="nttCn" rows="5" cols="100" class="w100p" title="내용" data-vreq></textarea>
			</td>
		</tr>
		<tr>
			<th>첨부파일<em></em></th>
			<td>
				<div><input type="file" name="f"/></div>
				<div><input type="file" name="f"/></div>
				<div><input type="file" name="f"/></div>
			</td>
		</tr>		
	</table>
	</form>

	<div class="tac mt20 disinblock w100p">
		<a href="<c:url value='/bdp/prcuse/analysisReqList.do'/>" class="btn floatl"><spring:message code="button.list" /></a>

		<div class="floatr">
			<a href="javascript:;" class="btn red regist">등록</a>
			<a href="javascript:;" class="btn cancel">취소</a>			
		</div>
	</div>

</div>