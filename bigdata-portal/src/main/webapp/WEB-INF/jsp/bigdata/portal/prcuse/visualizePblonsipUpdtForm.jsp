<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<script type="text/javascript" src="<c:url value="/js/bigdata/portal/prcuse/visualizePblonsipUpdtForm.js" />"></script>  

    
<div class="wrap_admin">

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>시각화 공유 수정</h3>

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
		<tr>
			<th>첨부파일<em></em></th>
			<td>
				<!-- 기존 첨부파일 목록 표시 -->
				<c:if test="${!empty files }">
					<c:forEach items="${files }" var="row">
						<div>
							<span>${row.originFileName }</span>
							<a href="javascript:;" class="deleteFile" style="margin-left:10px;display: inline;">삭제</a>
							<input type="hidden" name="atchFileId" value="${row.atchFileId }"/>
							<input type="hidden" name="fileNo" value="${row.fileNo }"/>
						</div>
					</c:forEach>
				</c:if>
				
				<div><input type="file" name="f"/></div>
				<div><input type="file" name="f"/></div>
				<div><input type="file" name="f"/></div>
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
