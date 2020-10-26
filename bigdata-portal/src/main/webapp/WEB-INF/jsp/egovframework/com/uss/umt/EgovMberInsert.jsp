<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<%
 /**
  * @Class Name : EgovMberInsert.jsp
  * @Description : 일반회원등록 JSP
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

<script type="text/javascript" src="<c:url value='/js/egovframework/com/sym/ccm/zip/EgovZipPopup.js' />" ></script>
<script type="text/javaScript">


function fnIdCheck(){
	var id = $('#mberId').val();
	
	$.ajax({
		data: {"checkId": id},
		url: "/uss/umt/EgovIdCheck.do",
		success: function(data) {
			alert(data.statusMessage);
		}
	});
}

$(document).ready(function () {
	
	$('#checkId').click(function() {
		fnIdCheck();
	});	
	
	$('#mberManageVO').submit(function() {

		if($('#mberId').val().trim() == "" || $('#mberId').val().length <= 0) {
			alert("회원아이디가 입력되지 않았습니다.");
			return false;
		}
		
		if($('#mberNm').val().trim() == "" || $('#mberNm').val().length <= 0) {
			alert("회원명이 입력되지 않았습니다.");			
			return false;
		}
		
		if($('#password').val().trim() == "" || $('#password').val().length <= 0) {
			alert("비밀번호가 입력되지 않았습니다.");
			return false;
		}
		
		if(!/^(?=.*[a-z])(?=.*[A-Z])(?=.*[$@$!%*?&]).{9,}/.test($('#password').val().trim())) {
			alert("비밀번호는 대문자와 특수문자를 1개이상 포함한 9자 이상으로 설정하셔야 합니다.");
			return false;
		}
		
		if($('#password2').val().trim() == "" || $('#password2').val().length <= 0) {
			alert("비밀번호가 입력되지 않았습니다.");
			return false;
		}
		
		if(!/^(?=.*[a-z])(?=.*[A-Z])(?=.*[$@$!%*?&]).{9,}/.test($('#password2').val().trim())) {
			alert("비밀번호는 대문자와 특수문자를 1개이상 포함한 9자 이상으로 설정하셔야 합니다.");
			return false;
		}
		
		if($('#password').val().trim() != $('#password2').val().trim()) {
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
		
		if($('#passwordCnsr').val().trim() == "" || $('#passwordCnsr').val().length <= 0) {
			alert("비밀번호가 입력되지 않았습니다.");
			return false;
		}
		
		if($('#areaNo').val().trim() == "" || $('#areaNo').val().length <= 0) {
			alert("전화번호가 입력되지 않았습니다.");
			return false;
		}
		
		if($('#middleTelno').val().trim() == "" || $('#middleTelno').val().length <= 0) {
			alert("전화번호가 입력되지 않았습니다.");
			return false;
		}
		
		if($('#endTelno').val().trim() == "" || $('#endTelno').val().length <= 0) {
			alert("전화번호가 입력되지 않았습니다.");
			return false;
		}
		
		if($('#mberEmailAdres').val().trim() == "" || $('#mberEmailAdres').val().length <= 0) {
			alert("이메일 주소가 입력되지 않았습니다.");
			return false;
		}
		
		if($('#mberSttus').val().trim() == "" || $('#mberSttus').val().length <= 0) {
			alert("상태코드 설정이 되지 않았습니다.");
			return false;
		}
		
		if($('#mberSttus').val().trim() == "" || $('#mberSttus').val().length <= 0) {
			alert("상태코드 설정이 되지 않았습니다.");
			return false;
		}
		
	});
});
</script>


<div class="wrap_admin">

<form:form commandName="mberManageVO" action="${pageContext.request.contextPath}/uss/umt/EgovMberInsert.do" name="mberManageVO"  method="post" >

<input name="checkedIdForDel" type="hidden" />
<!-- 검색조건 유지 -->
<input type="hidden" name="searchCondition" value="<c:out value='${userSearchVO.searchCondition}'/>"/>
<input type="hidden" name="searchKeyword" value="<c:out value='${userSearchVO.searchKeyword}'/>"/>
<input type="hidden" name="sbscrbSttus" value="<c:out value='${userSearchVO.sbscrbSttus}'/>"/>
<input type="hidden" name="pageIndex" value="<c:out value='${userSearchVO.pageIndex}'/>"/>
<!-- 우편번호검색 -->
<input type="hidden" name="zip_url" value="<c:url value='/sym/ccm/zip/EgovCcmZipSearchPopup.do'/>" />

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>일반회원 등록</h3>
	
	<table class="tbl02 adm_tbl02 column2">
		<tr>
			<th>일반회원아이디 <em>*</em></th>
			<td>
				<form:input path="mberId" id="mberId" title="일반회원아이디" cssClass="w50p" />
				<a href="#LINK" id="checkId" class="btn grey">중복아이디 검색</a>
				<div><form:errors path="mberId" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>일반회원이름 <em>*</em></th>
			<td><input name="mberNm" id="mberNm" title="일반회원이름" type="text" value="" Class="w50p" /></td>
		</tr>
		<tr>
			<th>비밀번호 <em>*</em></th>
			<td>
				<form:password path="password" id="password" title="비밀번호" Class="w50p" />
				<div><form:errors path="password" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>비밀번호확인 <em>*</em></th>
			<td>
				<input name="password2" id="password2" title="비밀번호확인" type="password" Class="w50p" />
			</td>
		</tr>
		<tr>
			<th>비밀번호힌트 <em>*</em></th>
			<td>
				<form:select path="passwordHint" id="passwordHint" title="비밀번호힌트">
					<form:option value="" label="--선택하세요--"/>
					<form:options items="${passwordHint_result}" itemValue="code" itemLabel="codeNm"/>
				</form:select>
				<div><form:errors path="passwordHint" cssClass="error"/></div>
			</td>
		</tr>
		<tr>
			<th>비밀번호정답 <em>*</em></th>
			<td>
				<form:input path="passwordCnsr" id="passwordCnsr" title="비밀번호정답" />
				<div><form:errors path="passwordCnsr" cssClass="error"/></div>
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
				<form:input path="areaNo" id="areaNo" title="전화번호" maxlength="5" Class="w60" />
				<i class="dash">-</i>
				<form:input path="middleTelno" id="middleTelno" maxlength="5" Class="w60" />
				<i class="dash">-</i>
				<form:input path="endTelno" id="endTelno" maxlength="5" Class="w60" />
				<div><form:errors path="areaNo" cssClass="error" /></div>
				<div><form:errors path="middleTelno" cssClass="error" /></div>
				<div><form:errors path="endTelno" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>이메일주소 <em>*</em></th>
			<td>
				<form:input path="mberEmailAdres" id="mberEmailAdres" title="이메일주소" Class="w50p" />
				<div><form:errors path="mberEmailAdres" cssClass="error" /></div>
			</td>
		</tr>
		
		<tr>
			<th>부서</th>
			<td>
				<form:input path="deptnm" id="deptnm" cssClass="w50p" title="부서"/>
				<div><form:errors path="deptnm" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>직위</th>
			<td>
				<form:input path="ofcpsnm" id="ofcpsnm" cssClass="w50p" title="직위"/>
				<div><form:errors path="ofcpsnm" cssClass="error" /></div>
			</td>
		</tr>		
		
		
		<tr>
			<th>일반회원상태코드 <em>*</em></th>
			<td>
				<form:select path="mberSttus" id="mberSttus" title="일반회원상태코드">
					<form:option value="" label="--선택하세요--"/>
					<form:options items="${mberSttus_result}" itemValue="code" itemLabel="codeNm"/>
				</form:select>
				<div><form:errors path="mberSttus" cssClass="error"/></div>
			</td>
		</tr>
	</table>
	
	
	<div class="tac mt20 disinblock w100p">
		<!-- 리스트 -->
		<a href="<c:url value='/uss/umt/EgovMberManage.do'/>" onclick="fnListPage(); return false;" class="btn floatl"><spring:message code="button.list" /></a>
		<div class="floatr">
			<!-- 확인 -->
			<input type="submit" value="<spring:message code="button.create" />" class="btn red" />
			<!-- 취소 -->
			<a href="#LINK" onclick="javascript:document.mberManageVO.reset();" class="btn grey">초기화</a>
		</div>
	</div>
	
</form:form>
</div>

