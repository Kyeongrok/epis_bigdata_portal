<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
/**
 * @Class Name  : EgovRoleInsert.java
 * @Description : EgovRoleInsert jsp
 * @Modification Information
 * @
 * @  수정일         수정자          수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.02.01    lee.m.j          최초 생성
 *
 *  @author lee.m.j
 *  @since 2009.03.11
 *  @version 1.0
 *  @see
 *
 */
 --%>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<c:set var="registerFlag" value="${empty roleManageVO.roleCode ? 'INSERT' : 'UPDATE'}"/>
<c:set var="registerFlagName" value="${empty roleManageVO.roleCode ? '롤 등록' : '롤 수정'}"/>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css"> --%>
<title>롤관리</title>
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<!--<validator:javascript formName="roleManage" staticJavascript="false" xhtml="true" cdata="false"/>-->
<script type="text/javaScript" language="javascript">

function fncSelectRoleList() {
    var varFrom = document.getElementById("roleManage");
    varFrom.action = "<c:url value='/sec/rmt/EgovRoleList.do'/>";
    varFrom.submit();
}

function fncRoleInsert() {

    var varFrom = document.getElementById("roleManage");
    varFrom.action = "<c:url value='/sec/rmt/EgovRoleInsert.do'/>";

    if(confirm("저장 하시겠습니까?")){
    	varFrom.submit();
    }
}

function fncRoleUpdate() {
    var varFrom = document.getElementById("roleManage");
    varFrom.action = "<c:url value='/sec/rmt/EgovRoleUpdate.do'/>";

    if(confirm("저장 하시겠습니까?")){
    	varFrom.submit();
    }
}

function fncRoleDelete() {
    var varFrom = document.getElementById("roleManage");
    varFrom.action = "<c:url value='/sec/rmt/EgovRoleDelete.do'/>";
    if(confirm("삭제 하시겠습니까?")){
        varFrom.submit();
    }
}

</script>
</head>

<body>
<div id="main" class="wrap_admin">

<form:form commandName="roleManage" method="post" >
	<h3 class="mt0"><i class="ico ico_arrow_grey"></i><c:out value="${registerFlagName}"/></h3>

	<table class="tbl02 adm_tbl02 column2" summary="롤 등록 테이블입니다.롤 코드,롤 명,롤 패턴,설명,롤 타입,롤 Sort,등록일자의 정보를 담고 있습니다.">
		<tr>
			<th scope="row">롤  코드 <em>*</em></th>
			<td><input name="roleCode" id="roleCode" type="text" value="<c:out value='${roleManage.roleCode}'/>" title="롤 코드" readonly="readonly" /></td>
		</tr>
		<tr>
			<th scope="row">롤 명 <em>*</em></th>
			<td>
				<input name="roleNm" id="roleNm" type="text" value="<c:out value='${roleManage.roleNm}'/>" title="롤명"/>
				<form:errors path="roleNm" />
			</td>
		</tr>
		<tr>
			<th scope="row">롤 패턴 <em>*</em></th>
			<td>
				<input name="rolePtn" id="rolePtn" type="text" value="<c:out value='${roleManage.rolePtn}'/>" title="롤패턴"/>
				<form:errors path="rolePtn" />
			</td>
		</tr>
		<tr>
			<th scope="row">설명</th>
			<td><input name="roleDc" id="roleDc" type="text" value="<c:out value='${roleManage.roleDc}'/>" title="설명"/></td>
		</tr>
		<tr>
			<th scope="row">롤 타입 <em>*</em></th>
			<td>
				<select name="roleTyp" title="롤타입">
				<c:forEach var="cmmCodeDetail" items="${cmmCodeDetailList}" varStatus="status">
					<option value="<c:out value='${cmmCodeDetail.code}'/>" <c:if test="${cmmCodeDetail.code == roleManage.roleTyp}">selected</c:if> ><c:out value="${cmmCodeDetail.codeNm}"/></option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th scope="row">롤 Sort <em>*</em></th>
			<td><input name="roleSort" id="roleSort" type="text" value="<c:out value='${roleManage.roleSort}'/>" title="롤sort"/></td>
		</tr>
		<tr>
			<th scope="row">등록일자</th>
			<td><input name="roleCreatDe" id="roleCreatDe" type="text" value="<c:out value='${roleManage.roleCreatDe}'/>" readonly="readonly" title="등록일자"/></td>
		</tr>
	</table>

	<div class="tac mt20 disinblock w100p">
		<a href="#LINK" onclick="javascript:fncSelectRoleList()" class="btn floatl">리스트</a>
		<div class="floatr">
			<c:if test="${registerFlag == 'INSERT'}">
				<a href="#LINK" onclick="javascript:fncRoleInsert()" class="btn red">확인</a>
			</c:if>
			<c:if test="${registerFlag == 'UPDATE'}">
				<a href="#LINK" onclick="javascript:fncRoleUpdate()" class="btn grey">수정</a>
				<a href="#LINK" onclick="javascript:fncRoleDelete()" class="btn grey">삭제</a>
			</c:if>
		</div>
	</div>

<!-- 검색조건 유지 -->
<c:if test="${registerFlag == 'UPDATE'}">
	<input type="hidden" name="searchCondition" value="<c:out value='${roleManageVO.searchCondition}'/>"/>
	<input type="hidden" name="searchKeyword" value="<c:out value='${roleManageVO.searchKeyword}'/>"/>
	<input type="hidden" name="pageIndex" value="<c:out value='${roleManageVO.pageIndex}'/>"/>
</c:if>
</form:form>
    <div align="right">
        <input type="text" name="message" value="<c:out value='${message}'/>" size="30" readonly="readonly" title="메시지" />
    </div>

</div>
</body>
</html>

