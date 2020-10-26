<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
/**
 * @Class Name  : EgovRoleUpdate.java
 * @Description : EgovRoleUpdate jsp
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

	<table class="tbl02 adm_tbl02 column2" summary="롤을 수정하는 테이블입니다.롤 코드,롤 명,롤 패턴,설명,롤 타입,롤 Sort,등록일자 정보를 담고 있습니다.">
		<tr>
			<th>롤  코드 <em>*</em></th>
			<td><input type="text" name="roleCode" id="roleCode" value="<c:out value='${roleManage.roleCode}'/>" readonly="readonly" title="롤 코드" /></td>
		</tr>
		<tr>
			<th>롤 명 <em>*</em></th>
			<td>
				<input type="text" name="roleNm" id="roleNm" value="<c:out value='${roleManage.roleNm}'/>" maxLength="50" title="롤명" />
				<form:errors path="roleNm" />
			</td>
		</tr>
		<tr>
			<th>롤 패턴 <em>*</em></th>
			<td>
				<input type="text" name="rolePtn" id="rolePtn" value="<c:out value='${roleManage.rolePtn}'/>" maxLength="200" title="롤패턴" />
				<form:errors path="rolePtn" />
			</td>
		</tr>
		<tr>
			<th>설명</th>
			<td><input type="text" name="roleDc" id="roleDc" value="<c:out value='${roleManage.roleDc}'/>" maxLength="50" title="설명" /></td>
		</tr>
		<tr>
			<th>롤 타입 <em>*</em></th>
			<td>
				<select name="roleTyp" title="롤타입">
				<c:forEach var="cmmCodeDetail" items="${cmmCodeDetailList}" varStatus="status">
					<option value="<c:out value="${cmmCodeDetail.code}"/>" <c:if test="${cmmCodeDetail.code == roleManage.roleTyp}">selected</c:if> ><c:out value="${cmmCodeDetail.codeNm}"/></option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>롤 Sort <em>*</em></th>
			<td><input type="text" name="roleSort" id="roleSort" value="<c:out value='${roleManage.roleSort}'/>" maxLength="10" title="롤sort" /></td>
		</tr>
		<tr>
			<th>등록일자</th>
			<td><input type="text" name="roleCreatDe" id="roleCreatDe" value="<c:out value='${roleManage.roleCreatDe}'/>" maxLength="50" title="등록일자" readonly="readonly"/></td>
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

