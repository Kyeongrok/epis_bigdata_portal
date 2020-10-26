<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
/**
 * @Class Name  : egovAuthorUpdate.java
 * @Description : egovAuthorUpdate jsp
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

<c:set var="registerFlag" value="${empty authorManageVO.authorCode ? 'INSERT' : 'UPDATE'}"/>
<c:set var="registerFlagName" value="${empty authorManageVO.authorCode ? '신규권한 등록' : '권한 수정'}"/>

<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css"> --%>
<title>권한관리</title>
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<!--<validator:javascript formName="authorManage" staticJavascript="false" xhtml="true" cdata="false"/> -->
<script type="text/javaScript" language="javascript">

function fncSelectAuthorList() {
	var varFrom = document.getElementById("authorManage");
	varFrom.action = "<c:url value='/sec/ram/EgovAuthorList.do'/>";
	varFrom.submit();
}

function fncAuthorInsert() {

    var varFrom = document.getElementById("authorManage");
    varFrom.action = "<c:url value='/sec/ram/EgovAuthorInsert.do'/>";

    if(confirm("저장 하시겠습니까?")){
    	if($('#authorCode').val().trim() == "" || $('#authorCode').val().length <= 0) {
    		alert("권한 ID가 입력되지 않았습니다.");
    		return false;
    	}
    		
    	if($('#authorNm').val().trim() == "" || $('#authorNm').val().length <= 0) {
    		alert("권한명이 입력되지 않았습니다.");
    		return false;
    	}
    }    
    
	varFrom.submit();
}
function fncAuthorUpdate() {
	var varFrom = document.getElementById("authorManage");
	varFrom.action = "<c:url value='/sec/ram/EgovAuthorUpdate.do'/>";

    if(confirm("저장 하시겠습니까?")){
    	varFrom.submit();
    }
}

function fncAuthorDelete() {
	var varFrom = document.getElementById("authorManage");
	varFrom.action = "<c:url value='/sec/ram/EgovAuthorDelete.do'/>";
	if(confirm("삭제 하시겠습니까?")){
	    varFrom.submit();
	}
}

</script>
</head>

<body>
<div id="main" class="wrap_admin">

<form:form commandName="authorManage" method="post" >
	<h3 class="mt0"><i class="ico ico_arrow_grey"></i><c:out value="${registerFlagName}"/></h3>

	<table class="tbl02 adm_tbl02 column2" summary="권한을 수정하는 테이블입니다.권한 코드,권한 명,설명,등록일자 정보를 담고 있습니다.">
		<tr>
			<th scope="row">권한 ID <em>*</em></th>
			<td>
				<input type="text" name="authorCode" id="authorCode" readonly="readonly" value="<c:out value='${authorManage.authorCode}'/>" title="권한코드"/>
				<form:errors path="authorCode" />
			</td>
		</tr>
		<tr>
			<th scope="row">권한명 <em>*</em></th>
			<td>
				<input type="text" name="authorNm" id="authorNm" value="<c:out value='${authorManage.authorNm}'/>" title="권한명"/>
				<form:errors path="authorNm" />
			</td>
		</tr>
		<tr>
			<th scope="row">설명</th>
			<td>
				<input type="text" name="authorDc" id="authorDc" value="<c:out value='${authorManage.authorDc}'/>" title="설명"/>
			</td>
		</tr>
		<tr>
			<th scope="row">등록일자</th>
			<td>
				<input type="text" name="authorCreatDe" id="authorCreatDe" value="<c:out value='${authorManage.authorCreatDe}'/>" readonly="readonly" title="등록일자"/>
			</td>
		</tr>
	</table>

	<div class="tac mt20 disinblock w100p">
		<a href="#LINK" onclick="javascript:fncSelectAuthorList()" class="btn floatl">리스트</a>
		<div class="floatr">
			<c:if test="${registerFlag == 'INSERT'}">
				<a href="#LINK" onclick="javascript:fncAuthorInsert()" class="btn red">확인</a>
			</c:if>
			<c:if test="${registerFlag == 'UPDATE'}">
				<a href="#LINK" onclick="javascript:fncAuthorUpdate()" class="btn grey">수정</a>
				<a href="#LINK" onclick="javascript:fncAuthorDelete()" class="btn grey">삭제</a>
			</c:if>
		</div>
	</div>
<!--
    <button type="button" onclick="javascript:fncSelectAuthorList();">List</button>
    <input type="submit" value="<c:out value='${registerFlag}'/>"/>
 -->
	<!-- 검색조건 유지 -->
	<c:if test="${registerFlag == 'UPDATE'}">
		<input type="hidden" name="searchCondition" value="<c:out value='${authorManageVO.searchCondition}'/>"/>
		<input type="hidden" name="searchKeyword" value="<c:out value='${authorManageVO.searchKeyword}'/>"/>
		<input type="hidden" name="pageIndex" value="<c:out value='${authorManageVO.pageIndex}'/>"/>
	</c:if>
</form:form>

</div>
</body>
</html>

