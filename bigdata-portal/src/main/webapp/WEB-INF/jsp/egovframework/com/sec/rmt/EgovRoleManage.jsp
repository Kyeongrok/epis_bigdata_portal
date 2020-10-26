<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%

/**
 * @Class Name : EgovRoleManage.java
 * @Description : EgovRoleManage jsp
 * @Modification Information
 * @
 * @  수정일                    수정자                수정내용
 * @ ---------     --------    ---------------------------
 * @ 2009.02.01    lee.m.j     최초 생성
 *
 *  @author lee.m.j
 *  @since 2009.03.21
 *  @version 1.0
 *  @see
 *
 */

%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css"> --%>
<title>롤관리</title>

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
                alert("선택된  롤이 없습니다.");
                returnBoolean = false;
            }
        } else {
            if(document.listForm.delYn.checked == false) {
                alert("선택된 롤이 없습니다.");
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

    document.listForm.roleCodes.value = returnValue;
    return returnBoolean;
}

function fncSelectRoleList(pageNo){
    document.listForm.searchCondition.value = "1";
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/sec/rmt/EgovRoleList.do'/>";
    document.listForm.submit();
}

function fncSelectRole(roleCode) {
    document.listForm.roleCode.value = roleCode;
    document.listForm.action = "<c:url value='/sec/rmt/EgovRole.do'/>";
    document.listForm.submit();
}

function fncAddRoleInsert() {
    location.replace("<c:url value='/sec/rmt/EgovRoleInsertView.do'/>");
}

function fncRoleListDelete() {
	if(fncManageChecked()) {
        if(confirm("삭제하시겠습니까?")) {
            document.listForm.action = "<c:url value='/sec/rmt/EgovRoleListDelete.do'/>";
            document.listForm.submit();
        }
    }
}

function fncAddRoleView() {
    document.listForm.action = "<c:url value='/sec/rmt/EgovRoleUpdate.do'/>";
    document.listForm.submit();
}

function linkPage(pageNo){
    document.listForm.searchCondition.value = "1";
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/sec/rmt/EgovRoleList.do'/>";
    document.listForm.submit();
}

function press() {

    if (event.keyCode==13) {
    	fncSelectRoleList('1');
    }
}
-->
</script>

</head>

<body>
<div id="main" class="wrap_admin">


<form:form name="listForm" action="${pageContext.request.contextPath}/sec/rmt/EgovRoleList.do" method="post">
	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>롤 관리</h3>
	<div class="searchbox_adm">
		<span class="searcharea">
			<input type="text" name="searchKeyword" value="<c:out value='${roleManageVO.searchKeyword}'/>" onkeypress="press();" placeholder="롤 명 검색"/>
			<a href="#LINK" onclick="javascript:fncSelectRoleList('1')"><i class="ico search">조회</i></a>
		</span>
	</div>
	<div class="floatr">
		<a href="#LINK" onclick="javascript:fncAddRoleInsert()" class="btn red">등록</a>
		<a href="#LINK" onclick="javascript:fncRoleListDelete()" class="btn grey">삭제</a>
	</div>

	<table class="tbl01 adm_tbl01 column8" summary="롤 관리 테이블입니다.롤  ID,롤 명,롤 타입,롤 Sort,롤 설명,등록일자의 정보를 담고 있습니다.">
		<thead>
			<tr>
				<th scope="col">
					<!-- <input type="checkbox" name="checkAll" class="chk_basic" onclick="javascript:fncCheckAll()" title="전체선택"> -->
					<input type="checkbox" id="checkAll" name="checkAll" onclick="javascript:fncCheckAll()" title="전체선택체크박스">
					<label for="checkAll"><span></span></label>
				</th>
				<th scope="col">롤 ID</th>
				<th scope="col">롤 명</th>
				<th scope="col">롤 타입</th>
				<th scope="col">롤 Sort</th>
				<th scope="col">롤 설명</th>
				<th scope="col">등록일자</th>
				<th scope="col">상세조회</th>
			</tr>
		</thead>
		<tbody>
		<%-- 데이터를 없을때 화면에 메세지를 출력해준다 --%>
		<c:if test="${fn:length(roleList) == 0}">
			<tr>
				<td colspan="8" class="nodata">
					<spring:message code="common.nodata.msg" />
				</td>
			</tr>
		</c:if>
		<c:forEach var="role" items="${roleList}" varStatus="status">
			<tr>
				<td>
					<!-- <input type="checkbox" name="delYn" class="chk_basic" title="선택"> -->
					<!-- 체크박스 : 가변적인 id와 label for값이 필요합니다 --> 
					<input type="checkbox" id="" name="delYn" title="선택" >
					<label for=""><span></span></label>
					<input type="hidden" name="checkId" value="<c:out value="${role.roleCode}"/>" />
				</td>
				<td><a href="#LINK" onclick="javascript:fncSelectRole('<c:out value="${role.roleCode}"/>')"><c:out value="${role.roleCode}"/></a></td>
				<td><c:out value="${role.roleNm}"/></td>
				<td><c:out value="${role.roleTyp}"/></td>
				<td><c:out value="${role.roleSort}"/></td>
				<td><c:out value="${role.roleDc}"/></td>
				<td><c:out value="${role.roleCreatDe}"/></td>
				<td class="adm_tbl_ico">
					<a href="#LINK" onclick="javascript:fncSelectRole('<c:out value="${role.roleCode}"/>')" class="btn lightgrey sm w100p bdbox">
						<i class="ico search">상세조회</i>
					</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

<c:if test="${!empty roleManageVO.pageIndex }">
	<div align="center" class="mt20">
	    <div>
	        <ui:pagination paginationInfo = "${paginationInfo}"
	            type="default"
	            jsFunction="linkPage"
	            />
	    </div>
	    <div align="right">
	        <input type="text" name="message" value="<c:out value='${message}'/>" size="30" readonly="readonly" title="메시지" />
	    </div>
	</div>
</c:if>
	<input type="hidden" name="roleCode"/>
	<input type="hidden" name="roleCodes"/>
	<input type="hidden" name="pageIndex" value="<c:out value='${roleManageVO.pageIndex}'/>"/>
	<input type="hidden" name="searchCondition"/>
</form:form>


</div>
</body>
</html>
