<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="egovframework.com.cmm.LoginVO" %>
<%@ page import="egovframework.com.cmm.util.EgovUserDetailsHelper" %>
<%
/**
 * @Class Name : EgovLoginPolicyList.java
 * @Description : EgovLoginPolicyList jsp
 * @Modification Information
 * @
 * @  수정일        수정자                수정내용
 * @ ---------     --------    ---------------------------
 * @ 2009.02.01    lee.m.j     최초 생성
 * @ 2011.09.30    이기하      데이터 없을시 메시지 추가
 *
 *  @author lee.m.j
 *  @since 2009.03.21
 *  @version 1.0
 *  @see
 *
 *  Copyright (C) 2009 by MOPAS  All right reserved.
 */
%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>로그인정책 목록조회</title>

<script type="text/javaScript" language="javascript" defer="defer">
<!--

function fncCheckAll() {
    var checkField = document.listForm.delYn;
    if(document.listForm.checkAll.checked) {
        if(checkField) {
            if(checkField.length > 1) {
                for(var i=0; i < checkField.length; i++) {
                    checkField[i].checked = true;
                }
            } else {
                checkField.checked = true;
            }
        }
    } else {
        if(checkField) {
            if(checkField.length > 1) {
                for(var j=0; j < checkField.length; j++) {
                    checkField[j].checked = false;
                }
            } else {
                checkField.checked = false;
            }
        }
    }
}

function fncManageChecked() {

    var checkField = document.listForm.delYn;
    var checkId = document.listForm.checkId;
    var returnValue = "";
    var returnBoolean = false;
    var checkCount = 0;

    if(checkField) {
        if(checkField.length > 1) {
            for(var i=0; i<checkField.length; i++) {
                if(checkField[i].checked) {
                	checkCount++;
                    checkField[i].value = checkId[i].value;
                    if(returnValue == "")
                        returnValue = checkField[i].value;
                    else
                        returnValue = returnValue + ";" + checkField[i].value;
                }
            }
            if(checkCount > 0)
                returnBoolean = true;
            else {
                alert("선택된  로그인정책이 없습니다.");
                returnBoolean = false;
            }
        } else {
            if(document.listForm.delYn.checked == false) {
                alert("선택된  로그인정책이 없습니다.");
                returnBoolean = false;
            }
            else {
                returnValue = checkId.value;
                returnBoolean = true;
            }
        }
    } else {
    	alert("조회된 결과가 없습니다.");
    }

    document.listForm.emplyrIds.value = returnValue;
    return returnBoolean;
}

function fncSelectLoginPolicyList(pageNo){
    document.listForm.searchCondition.value = "1";
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/uat/uap/selectLoginPolicyList.do'/>";
    document.listForm.submit();
}

function fncSelectLoginPolicy(emplyrId) {
    document.listForm.emplyrId.value = emplyrId;
    document.listForm.action = "<c:url value='/uat/uap/getLoginPolicy.do'/>";
    document.listForm.submit();
}

function fncInsertCheckId() {

    var checkedCounter = 0;
    var checkIds = document.listForm.delYn;
    var checkIdv = document.listForm.checkId;
    var checkReg = document.listForm.regYn;

    if(checkIds == null) {
        alert("조회 후 등록하시기 바랍니다");
        return;
    }
    else {

        for(var i=0; i<checkIds.length; i++) {
            if(checkIds[i].checked) {
                if(checkReg[i].value == 'Y' ) {
                    alert("이미 로그인정책이 등록되어 있습니다.");
                    return;
                }
                checkedCounter++;
                document.listForm.emplyrId.value = checkIdv[i].value;
            }
        }

        if(checkedCounter > 1) {
            alert("등록대상 하나만 선택하십시오");
            return false;
        } else if(checkedCounter < 1) {
            alert("선택된 등록대상이  없습니다");
            return false;
        }

        return true;
    }
}

function fncAddLoginPolicyInsert() {

    if(fncInsertCheckId()) {
        document.listForm.action = "<c:url value='/uat/uap/addLoginPolicyView.do'/>";
        document.listForm.submit();
    }
}

function fncLoginPolicyListDelete() {
	if(fncManageChecked()) {
        if(confirm("삭제하시겠습니까?")) {
            document.listForm.action = "<c:url value='/uat/uap/removeLoginPolicyList.do'/>";
            document.listForm.submit();
        }
    }
}

function linkPage(pageNo){
    document.listForm.searchCondition.value = "1";
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/uat/uap/selectLoginPolicyList.do'/>";
    document.listForm.submit();
}

function press() {

    if (event.keyCode==13) {
    	fncSelectLoginPolicyList('1');
    }
}
-->
</script>

</head>

<body>

<div class="tablearea">
	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>IP접근 관리</h3>

<div class="searchbox_adm w80p">


<form name="listForm" action="<c:url value='/uat/uap/selectLoginPolicyList.do'/>" method="post">

	<div class="searchbox_adm w40p">
	
		<span class="searcharea w120p">
			<input name="searchKeyword" type="text" value="<c:out value="${loginPolicyVO.searchKeyword}"/>" onkeypress="press();" title="사용자명검색" class="w80p">
			<input type="submit" value="<spring:message code="button.inquire" />" onclick="fncSelectLoginPolicyList('1'); return false;" title="검색단어입력">
		</span>
	</div>


<table width="100%" cellpadding="8" class="table-search" border="0">

<input type="hidden" name="emplyrId">
<input type="hidden" name="pageIndex" value="<c:if test="${empty loginPolicyVO.pageIndex }">1</c:if><c:if test="${!empty loginPolicyVO.pageIndex }"><c:out value='${loginPolicyVO.pageIndex}'/></c:if>">
<input type="hidden" name="searchCondition" value="1" >
</form>


<table class="tbl01 adm_tbl01 column8_mber" summary="로그인정책에 대한 목록을 제공한다.">
 <caption>로그인정책 관리</caption>
 <thead>
  <tr>
    <th scope="col" nowrap>사용자 ID</th>
    <th scope="col" nowrap>사용자 명</th>
    <th scope="col" nowrap>IP 정보</th>
    <th scope="col" nowrap>제한여부</th>
  </tr>
 </thead>
 <tbody>
  <%-- 데이터를 없을때 화면에 메세지를 출력해준다 --%>
<c:if test="${fn:length(loginPolicyList) == 0}">
<tr>
<td class="lt_text3" colspan="4">
	<spring:message code="common.nodata.msg" />
</td>
</tr>
</c:if>
 <c:forEach var="loginPolicy" items="${loginPolicyList}" varStatus="status">
  <tr>
    <td class="lt_text3" nowrap>
        <form name="item" action="<c:url value='/uat/uap/getLoginPolicy.do'/>">
            <input type="hidden" name="emplyrId" value="<c:out value="${loginPolicy.emplyrId}"/>">
            <input type="hidden" name="pageIndex" value="<c:out value='${loginPolicyVO.pageIndex}'/>">
            <input type="hidden" name="searchCondition" value="<c:out value='${loginPolicyVO.searchCondition}'/>">
            <input type="hidden" name="searchKeyword" value="<c:out value="${loginPolicyVO.searchKeyword}"/>">
            <span class="link">
            	<a href="/uat/uap/getLoginPolicy.do?emplyrId=<c:out value="${loginPolicy.emplyrId}"/>"><c:out value="${loginPolicy.emplyrId}"/></a>            	
            </span>
        </form>
    </td>
    <td><c:out value="${loginPolicy.emplyrNm}"/></td>
    <td><c:out value="${loginPolicy.ipInfo}"/></td>
    <td><c:if test="${loginPolicy.lmttAt == 'Y'}">Y</c:if><c:if test="${loginPolicy.lmttAt == 'N'}">N</c:if></td>
  </tr>
 </c:forEach>
 </tbody>


</td>
</tr>
</table>

</div>

	<!--  page start -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
	<!--  page end -->

</body>
</html>
