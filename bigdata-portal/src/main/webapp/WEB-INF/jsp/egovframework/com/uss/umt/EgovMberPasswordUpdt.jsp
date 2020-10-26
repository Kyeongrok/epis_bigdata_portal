<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%
 /**
  * @Class Name : EgovMberPasswordUpdt.jsp
  * @Description : 일반회원암호수정 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.04.02    조재영          최초 생성
  *
  *  @author 공통서비스 개발팀 조재영
  *  @since 2009.04.02
  *  @version 1.0
  *  @see
  *  
  */
%>

<script type="text/javaScript" language="javascript" defer="defer">
<!--
function fnListPage(){
    document.passwordChgVO.action = "<c:url value='/uss/umt/EgovMberManage.do'/>";
    document.passwordChgVO.submit();
}
function fnUpdate(){
    if(validatePasswordChgVO(document.passwordChgVO)){
        if(document.passwordChgVO.newPassword.value != document.passwordChgVO.newPassword2.value){
            alert("<spring:message code="fail.user.passwordUpdate2" />");
            return;
        }
        document.passwordChgVO.submit();
    }
}
<c:if test="${!empty resultMsg}">alert("<spring:message code="${resultMsg}" />");</c:if>
//-->
</script>
<script>
	if($('#oldPassword').val().trim() == "" || $('#oldPassword').val().length <= 0) {
		alert("비밀번호가 입력되지 않았습니다.");
		return false;
	}
	
	if(!/^(?=.*[a-z])(?=.*[A-Z])(?=.*[$@$!%*?&]).{9,}/.test($('#oldPassword').val().trim())) {
		alert("비밀번호는 대문자와 특수문자를 1개이상 포함한 9자 이상으로 설정하셔야 합니다.");
		return false;
	}
	
	if($('#newPassword').val().trim() == "" || $('#newPassword').val().length <= 0) {
		alert("비밀번호가 입력되지 않았습니다.");
		return false;
	}
	
	if(!/^(?=.*[a-z])(?=.*[A-Z])(?=.*[$@$!%*?&]).{9,}/.test($('#newPassword').val().trim())) {
		alert("비밀번호는 대문자와 특수문자를 1개이상 포함한 9자 이상으로 설정하셔야 합니다.");
		return false;
	}
</script>


<div class="wrap_admin">
        
<form name="passwordChgVO" method="post" action="<c:url value="${'/uss/umt/EgovMberPasswordUpdt.do'}"/>">
<!-- onsubmit="javascript:return FormValidation(document.passwordChgVO);" >  -->
<!-- 상세정보 사용자 삭제시 prameter 전달용 input -->
<input name="checkedIdForDel" type="hidden" />
<!-- 검색조건 유지 -->
<input type="hidden" name="searchCondition" value="<c:out value='${userSearchVO.searchCondition}'/>"/>
<input type="hidden" name="searchKeyword" value="<c:out value='${userSearchVO.searchKeyword}'/>"/>
<input type="hidden" name="sbscrbSttus" value="<c:out value='${userSearchVO.sbscrbSttus}'/>"/>
<input type="hidden" name="pageIndex" value="<c:out value='${userSearchVO.pageIndex}'/>"/>
<!-- 우편번호검색 -->
<input type="hidden" name="url" value="<c:url value='/sym/ccm/zip/EgovCcmZipSearchPopup.do'/>" />

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>회원 암호변경</h3>
	<table class="tbl02 adm_tbl02 column2">
		<tr>
			<th>회원아이디 <em>*</em></th>
			<td>
				<label for="mberId" class="w50p"><input name="mberId" id="mberId" type="text" value="<c:out value='${mberManageVO.mberId}'/>" readonly>
					<input name="uniqId" id="uniqId" type="hidden" value="<c:out value='${mberManageVO.uniqId}'/>" >
					<input name="userTy" id="userTy" type="hidden" value="<c:out value='${mberManageVO.userTy}'/>" >
				</label>
			</td>
		</tr>
		<tr> 
			<th>기존 비밀번호 <em>*</em></th>
			<td><label for="oldPassword" class="w50p"><input name="oldPassword" id="oldPassword" type="password" value="" ></label></td>
		</tr>
		<tr> 
			<th>비밀번호 <em>*</em></th>
			<td><label for="newPassword" class="w50p"><input name="newPassword" id="newPassword" type="password" value="" ></label></td>
		</tr>
		<tr>
			<th>비밀번호확인 <em>*</em></th>
			<td><label for="newPassword2" class="w50p"><input name="newPassword2" id="newPassword2" type="password" value="" ></label></td>
		</tr>
	</table>

	<div class="tac mt20 disinblock w100p">
		<!-- 리스트 -->
		<a href="<c:url value='/bdp/user/mypage.do'/>" class="btn floatl">돌아가기</a>
		<div class="floatr">
			<!-- 수정 -->
			<input type="submit" value="<spring:message code="button.update" />" onclick="fnUpdate(); return false;" class="btn red" />
		</div>
	</div>


</form>

</div>

