<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
/**
 * @Class Name  : EgovLoginPolicyRegist.java
 * @Description : EgovLoginPolicyRegist jsp
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
 *  Copyright (C) 2009 by MOPAS  All right reserved.
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
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>로그인정책 등록</title>
<script type="text/javaScript" language="javascript">

function fncSelectLoginPolicyList() {
    var varFrom = document.getElementById("loginPolicy");
    varFrom.action = "<c:url value='/uat/uap/selectLoginPolicyList.do'/>";
    varFrom.submit();
}

function fncLoginPolicyInsert() {

    var varFrom = document.getElementById("loginPolicy");
    varFrom.action = "<c:url value='/uat/uap/addLoginPolicy.do'/>";

    if(confirm("저장 하시겠습니까?")){
        if(ipValidate())
            varFrom.submit();
        else
            return;
    }
}

function ipValidate() {

    var varFrom = document.getElementById("loginPolicy");
    var IPvalue = varFrom.ipInfo.value;

    var ipPattern = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
    var ipArray = IPvalue.match(ipPattern);

    var result = "";
    var thisSegment;

    if(IPvalue == "0.0.0.0") {
        alert(IPvalue + "는 예외 아이피 입니다..");
        result = false;
    } else if (IPvalue == "255.255.255.255") {
        alert(result =IPvalue + "는 예외 아이피 입니다.");
        result = false;
    } else {
        result = true;
    }

    if(ipArray == null) {
        alert("형식이 일치 하지않습니다. ");
        result = false;
    } else {
        for (var i=1; i<5; i++) {

            thisSegment = ipArray[i];

            if (thisSegment > 255) {
                alert("형식이 일치 하지않습니다. ");
                result = false;
            }

            if ((i == 0) && (thisSegment > 255)) {
                alert("형식이 일치 하지않습니다. ");
                result = false;
            }
        }
    }

    return result;
}

</script>
</head>

<body>
<div id="main" class="wrap_admin">
    
    
<form:form commandName="loginPolicy" method="post" action="${pageContext.request.contextPath}/uat/uap/addLoginPolicy.do' />">
<h3 class="mt0"><i class="ico ico_arrow_grey"></i>IP정책 등록</h3>


	<table class="tbl02 adm_tbl02 column2" summary="권한을 수정하는 테이블입니다.권한 코드,권한 명,설명,등록일자 정보를 담고 있습니다.">
		<tr>
			<th scope="row">사용자ID <em>*</em></th>
			<td>
				<input name="emplyrId" id="emplyrId" title="사용자ID" type="text" value="<c:out value='${loginPolicy.emplyrId}'/>" size="30" class="readOnlyClass" readonly >
				
				<form:errors path="emplyrId" />
			</td>
		</tr>
		<tr>
			<th scope="row">사용자명 <em>*</em></th>
			<td>
				<input name="emplyrNm" id="emplyrNm" title="사용자명" type="text" value="<c:out value='${loginPolicy.emplyrNm}'/>" maxLength="50" size="30" class="readOnlyClass" readonly >
				<form:errors path="emplyrNm" />
			</td>
		</tr>
		<tr>
			<th scope="row">IP정보 <em>*</em></th>
			<td>
				<input name="ipInfo" id="ipInfo" title="IP정보" type="text" maxLength="23" size="30"  >
			</td>
		</tr>
		<tr>
			<th scope="row">IP제한여부 <em>*</em></th>
			<td>
				      <select name="lmttAt" id="lmttAt" title="IP제한여부">
				          <option value="Y">Y</option>
				          <option value="N">N</option>
				      </select>&nbsp;<form:errors path="lmttAt" />
			</td>
		</tr>
	</table>

<div class="tac mt20 disinblock w100p">
	<a href="#LINK" onclick="fncLoginPolicyInsert(); return false;" class="btn grey">확인</a>
	<a href="<c:url value='/uat/uap/selectLoginPolicyList.do'/>?pageIndex=<c:out value='${loginPolicyVO.pageIndex}'/>&amp;searchKeyword=<c:out value="${loginPolicyVO.searchKeyword}"/>&amp;searchCondition=1" onclick="fncSelectLoginPolicyList(); return false;" class="btn floatl">리스트</a>
</div>


<input type="hidden" name="dplctPermAt" value="Y" >
<input type="hidden" name="searchCondition" value="<c:out value='${loginPolicyVO.searchCondition}'/>" >
<input type="hidden" name="searchKeyword" value="<c:out value='${loginPolicyVO.searchKeyword}'/>" >
<input type="hidden" name="pageIndex" value="<c:out value='${loginPolicyVO.pageIndex}'/>" >
</form:form>

</td>
</tr>
</table>
</DIV>
</body>
</html>

