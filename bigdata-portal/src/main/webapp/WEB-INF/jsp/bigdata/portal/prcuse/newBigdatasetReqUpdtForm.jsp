<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<script type="text/javascript" src="<c:url value="/js/bigdata/portal/prcuse/newBigdatasetReqUpdtForm.js" />"></script>  

    
<div class="wrap_admin">

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>신규빅데이터셋 요청 수정</h3>

	<form name="uf" id="uf">
	<input type="hidden" name="bbsGbn" value="${data.bbsGbn }" />
	<input type="hidden" name="nttId" value="${data.nttId }" />
	
	<table class="tbl02 column3">
		<tr>
			<th>제목<em>*</em></th>
			<td>
				<input type="text" name="nttSj"  class="w100p" title="제목" data-vreq value="${data.nttSj }"/>
			</td>
		</tr>
		<tr>
			<th>내용<em>*</em></th>
			<td>
				<textarea name="nttCn" rows="5" cols="100" class="w100p" title="내용" data-vreq>${data.nttCn }</textarea>
			</td>
		</tr>
	</table>
	</form>

	<div class="tac mt20 disinblock w100p">

		<div class="floatr">
			<a href="javascript:;" class="btn red updt">수정</a>
			<a href="javascript:;" class="btn cancel">취소</a>			
		</div>
	</div>

</div>
