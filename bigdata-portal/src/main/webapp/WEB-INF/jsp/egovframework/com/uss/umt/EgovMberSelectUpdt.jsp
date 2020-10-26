<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%
 /**
  * @Class Name : EgovMberSelectUpdt.jsp
  * @Description : 일반회원상세조회, 수정 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.03.02    조재영          최초 생성
  *
  *  @author 공통서비스 개발팀 조재영
  *  @since 2009.03.02
  *  @version 1.0
  *  @see
  *
  */
%>

<script type="text/javaScript" language="javascript" defer="defer">
<!--
function fnListPage(){
    document.mberManageVO.action = "<c:url value='/uss/umt/EgovMberManage.do'/>";
    document.mberManageVO.submit();
}
function fnDeleteMber(checkedIds) {
	if(confirm("<spring:message code="common.delete.msg" />")){
	    document.mberManageVO.checkedIdForDel.value=checkedIds;
	    document.mberManageVO.action = "<c:url value='/uss/umt/EgovMberDelete.do'/>";
	    document.mberManageVO.submit();
	}
}
function fnPasswordMove(){
    document.mberManageVO.action = "<c:url value='/uss/umt/EgovMberPasswordUpdtView.do'/>";
    document.mberManageVO.submit();
}

//-->
</script>
</head>
<body>
<div class="wrap_admin">

<form:form commandName="mberManageVO" action="${pageContext.request.contextPath}/uss/umt/EgovMberSelectUpdt.do" name="mberManageVO"  method="post" >

<!-- 상세정보 사용자 삭제시 prameter 전달용 input -->
<!-- 검색조건 유지 -->
<input type="hidden" name="searchCondition" value="<c:out value='${userSearchVO.searchCondition}'/>"/>
<input type="hidden" name="searchKeyword" value="<c:out value='${userSearchVO.searchKeyword}'/>"/>
<input type="hidden" name="sbscrbSttus" value="<c:out value='${userSearchVO.sbscrbSttus}'/>"/>
<input type="hidden" name="pageIndex" value="<c:out value='${userSearchVO.pageIndex}'/>"/>
<input type="hidden" name="checkedIdForDel" value=""/>
<!-- 우편번호검색 -->
<input type="hidden" name="zip_url" value="<c:url value='/sym/ccm/zip/EgovCcmZipSearchPopup.do'/>" />
<!-- 사용자유형정보 : password 수정화면으로 이동시 타겟 유형정보 확인용, 만약검색조건으로 유형이 포함될경우 혼란을 피하기위해 userTy명칭을 쓰지 않음-->
<input type="hidden" name="userTyForPassword" value="<c:out value='${mberManageVO.userTy}'/>" />
<input type="hidden" id="status" value="<c:out value="${status}"/>">
<!-- for validation -->
<input type="hidden" name="password" id="password" value="Test#$123)"/>

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>회원 상세조회(수정)</h3>
	
	<table class="tbl02 adm_tbl02 column2">
		<tr>
			<th>회원아이디 <em>*</em></th>
			<td>
				<form:input path="mberId" id="mberId" cssClass="w50p" readonly="true" title="일반회원아이디"/>
				<form:errors path="mberId" cssClass="error" />
				<form:hidden path="uniqId" />
			</td>
		</tr>
		<tr>
			<th>회원이름 <em>*</em></th>
			<td>
				<form:input path="mberNm" id="mberNm" cssClass="w50p" title="일반회원이름"/>
				<form:errors path="password" cssClass="error" />
			</td>
		</tr>
		<tr>
			<th>비밀번호힌트 <em>*</em></th>
			<td>
				<form:select path="passwordHint" id="passwordHint" title="비밀번호힌트">
					<form:option value="" label="--선택하세요--"/>
					<form:options items="${passwordHint_result}" itemValue="code" itemLabel="codeNm"/>
				</form:select>
				<form:errors path="passwordHint" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<th>비밀번호정답 <em>*</em></th>
			<td>
				<form:input path="passwordCnsr" id="passwordCnsr" cssClass="w50p" title="비밀번호정답"/>
				<form:errors path="passwordCnsr" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<th>성별구분코드</th>
			<td>
				<form:select path="sexdstnCode" id="sexdstnCode" title="성별구분코드">
					<form:option value="" label="--선택하세요--"/>
					<form:options items="${sexdstnCode_result}" itemValue="code" itemLabel="codeNm"/>
				</form:select>
			</td>
		</tr>
		<tr>
			<th>전화번호 <em>*</em></th>
			<td>
				<form:input path="areaNo" id="areaNo" cssClass="w60" title="지역번호" maxlength="5" />
				<i class="dash">-</i>
				<form:input path="middleTelno" id="middleTelno" cssClass="w60" title="중간번호" maxlength="5" />
				<i class="dash">-</i>
				<form:input path="endTelno" id="endTelno" cssClass="w60" title="끝번호" maxlength="5" />
				<form:errors path="areaNo" cssClass="error" />
				<form:errors path="middleTelno" cssClass="error" />
				<form:errors path="endTelno" cssClass="error" />
			</td>
		</tr>
		<tr>
			<th>이메일주소 <em>*</em></th>
			<td>
				<form:input path="mberEmailAdres" id="mberEmailAdres" cssClass="w50p" title="이메일주소"/>
				<form:errors path="mberEmailAdres" cssClass="error" />
			</td>
		</tr>
		<tr>
			<th>부서</th>
			<td>
				<form:input path="deptnm" id="deptnm" cssClass="w50p" title="부서"/>
			</td>
		</tr>
		<tr>
			<th>직위</th>
			<td>
				<form:input path="ofcpsnm" id="ofcpsnm" cssClass="w50p" title="직위"/>
			</td>
		</tr>
		<tr>
			<th>회원상태코드 <em>*</em></th>
			<td>
				<c:out value="${mberSttus}"/>
				
				<c:if test="${author == 'ROLE_ADMIN'}">
					<form:select path="mberSttus" id="mberSttus" title="일반회원상태코드">
						<form:option value="" label="--선택하세요--"/>
						<form:options items="${mberSttus_result}" itemValue="code" itemLabel="codeNm"/>
					</form:select>
					<form:errors path="mberSttus" cssClass="error"/>
					<input type="hidden" name="password" value="dummy">
				</c:if>
				
				<c:if test="${author != 'ROLE_ADMIN'}">
					<c:if test="${mberManageVO.mberSttus == 'A'}">
						회원가입 신청상태
					</c:if>
					
					<c:if test="${mberManageVO.mberSttus == 'D'}">
						회원가입 삭제상태
					</c:if>
					
					<c:if test="${mberManageVO.mberSttus == 'P'}">
						회원가입 승인상태
					</c:if>
					<form:hidden path="mberSttus" value="${mberManageVO.mberSttus}"/>
					
					<form:errors path="mberSttus" cssClass="error"/>
					<input type="hidden" name="password" value="dummy">
					
				</c:if>
							
			</td>
		</tr>
		<tr>
			<th>등록 가능한 용량</th>
			<td>
				<c:if test="${author == 'ROLE_ADMIN'}">
					<form:input path="quotaSize" id="quotaSize" value="${quotaSize}" cssClass="w60" title="일반회원아이디"/> <i class="dash">GB</i>
				</c:if>
				
				<c:if test="${author != 'ROLE_ADMIN'}">
					<i class="dash"><c:out value="${viewQuotaSizeMB}"/> GB </i>
					<form:hidden path="quotaSize" value="${quotaSize}"/>					
				</c:if>			
					
			</td>
		</tr>
		<tr>
			<th>사용 중인 용량</th>
			<td>            	
				<i class="dash">${viewDirSizeMB} MB</i>            		
			</td>
		</tr>
	</table>


	<div class="tac mt20 disinblock w100p">
		<!-- 리스트 -->
		<c:if test="${author == 'ROLE_ADMIN'}">
		<a href="<c:url value='/uss/umt/EgovMberManage.do'/>" onclick="fnListPage(); return false;" class="btn floatl"><spring:message code="button.list" /></a>
		</c:if>
		
		<div class="floatr">
			<!-- 수정 확인 -->
			<input type="submit" value="<spring:message code="button.update" />" class="btn red" />
			<!-- 암호변경 -->
			<a href="<c:url value='/uss/umt/EgovMberPasswordUpdtView.do'/>" onclick="fnPasswordMove(); return false;" class="btn grey">비밀번호 변경</a>
			<!-- 삭제 -->		
			<a href="<c:url value='/uss/umt/EgovMberManage.do'/>" onclick="fnDeleteMber('<c:out value='${mberManageVO.userTy}'/>:<c:out value='${mberManageVO.uniqId}'/>'); return false;" class="btn grey">회원탈퇴</a>
						
		</div>
	</div>


</form:form>

</div>
<script>
	$(document).ready(function() {
		var status = $('#status').val();
		if(status == "success") {
			alert("회원정보 수정이 완료되었습니다.");
		}
	});
</script>
