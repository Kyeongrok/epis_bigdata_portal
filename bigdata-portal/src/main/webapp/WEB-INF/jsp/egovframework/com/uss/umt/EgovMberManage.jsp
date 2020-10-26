<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
 /**
  * @Class Name : EgovUserManage.jsp
  * @Description : 사용자관리(조회,삭제) JSP
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
function fnCheckAll() {
    var checkField = document.listForm.checkField;
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
function fnDeleteUser() {
    var checkField = document.listForm.checkField;
    var id = document.listForm.checkId;
    var checkedIds = "";
    var checkedCount = 0;
    if(checkField) {
        if(checkField.length > 1) {
            for(var i=0; i < checkField.length; i++) {
                if(checkField[i].checked) {
                    checkedIds += ((checkedCount==0? "" : ",") + id[i].value);
                    checkedCount++;
                }
            }
        } else {
            if(checkField.checked) {
                checkedIds = id.value;
            }
        }
    }
    if(checkedIds.length > 0) {
    	//alert(checkedIds);
        if(confirm("<spring:message code="common.delete.msg" />")){
        	document.listForm.checkedIdForDel.value=checkedIds;
            document.listForm.action = "<c:url value='/uss/umt/EgovMberDelete.do'/>";
            document.listForm.submit();
        }
    }
}
function fnSelectUser(id) {
	document.listForm.selectedId.value = id;
	array = id.split(":");
	if(array[0] == "") {
	} else {
	    userTy = array[0];
	    userId = array[1];    
	}
	document.listForm.selectedId.value = userId;
    document.listForm.action = "<c:url value='/uss/umt/EgovMberSelectUpdtView.do'/>";
    document.listForm.submit();
}
function fnAddUserView() {
    document.listForm.action = "<c:url value='/uss/umt/EgovMberInsertView.do'/>";
    document.listForm.submit();
}
function fnLinkPage(pageNo){
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/uss/umt/EgovMberManage.do'/>";
    document.listForm.submit();
}
function fnSearch(){
	document.listForm.pageIndex.value = 1;
	document.listForm.action = "<c:url value='/uss/umt/EgovMberManage.do'/>";
    document.listForm.submit();
}
<c:if test="${!empty resultMsg}">alert("<spring:message code="${resultMsg}" />");</c:if>
//-->
</script>


<div class="tablearea">
	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>회원관리</h3>
	
<form name="listForm" action="<c:url value='/uss/umt/EgovMberManage.do' />" method="post">
<input name="selectedId" type="hidden" />
<input name="checkedIdForDel" type="hidden" />
<input name="pageIndex" type="hidden" value="<c:out value='${userSearchVO.pageIndex}'/>"/>
	<div class="searchbox_adm w60p">
		<select name="sbscrbSttus" id="sbscrbSttus" title="검색조건선택1">
			<option value="0" <c:if test="${empty mberVO.sbscrbSttus || mberVO.sbscrbSttus == '0'}">selected="selected"</c:if> >상태(전체)</option>
			<option value="A" <c:if test="${mberVO.sbscrbSttus == 'A'}">selected="selected"</c:if> >가입신청</option>
			<option value="D" <c:if test="${mberVO.sbscrbSttus == 'D'}">selected="selected"</c:if> >삭제</option>
			<option value="P" <c:if test="${mberVO.sbscrbSttus == 'P'}">selected="selected"</c:if> >승인</option>
		</select>
		<select name="searchCondition" id="searchCondition" title="검색조건선택2">
			<option value="0" <c:if test="${mberVO.searchCondition == '0'}">selected="selected"</c:if> >아이디</option>
			<option value="1" <c:if test="${empty mberVO.searchCondition || mberVO.searchCondition == '1'}">selected="selected"</c:if> >사용자이름</option>
		</select>
		<span class="searcharea w50p">
			<input type="text" name="searchKeyword" id="searchKeyword" title="검색" value="<c:out value="${mberVO.searchKeyword}"/>" />
			<input type="submit" value="<spring:message code="button.search" />" onclick="fnSearch(); return false;" />
		</span>
	</div>
	<div class="floatr">
		<!-- 등록 -->
		<a href="<c:url value='/uat/uap/selectLoginPolicyList.do'/>" class="btn red">IP접근 관리</a>
		<a href="<c:url value='/sym/log/clg/SelectLogList.do'/>" class="btn red">이력관리</a>
		<a href="<c:url value='/uss/umt/EgovMberInsertView.do'/>" onclick="fnAddUserView(); return false;" class="btn red"><spring:message code="button.create" /></a>
		<!-- 삭제 -->
		<a href="#LINK" onclick="fnDeleteUser(); return false;" class="btn grey"><spring:message code="button.delete" /></a>
	</div>

	<div class="total">
		<span class="count">사용자수 <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong></span>
	</div>
        
	<table class="tbl01 adm_tbl01 column11_mber" summary="일반회원관리에  관한 테이블입니다.">
		<thead>
			<tr>
				<th scope="col">
					<!-- <input type="checkbox" name="checkAll" title="Check All" class="chk_basic" onclick="javascript:fnCheckAll();"/> -->
					<input type="checkbox" id="checkAll" name="checkAll" title="Check All" onclick="javascript:fnCheckAll();"/>
					<label for="checkAll"><span></span></label>
				</th>
				<th scope="col">번호</th>
				<th scope="col">아이디</th>
				<th scope="col">사용자이름</th>
				<th scope="col">사용자이메일</th>
				<th scope="col">전화번호</th>
				<th scope="col">등록일</th>
				<th scope="col">권한</th>
				<th scope="col">가입상태</th>
				<th scope="col">업로드파일</th>
				<th scope="col">내 데이터목록</th>				
			</tr>
		</thead>
		<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td>
					<%-- <input type="checkbox" name="checkField" class="chk_basic" title="checkField <c:out value="${status.count}"/>"/> --%>
					<input type="checkbox" id="checkbox<c:out value='${status.index}'/>" name="checkField" title="checkField <c:out value="${status.count}"/>"/>
					<label for="checkbox<c:out value='${status.index}'/>"><span></span></label>
					<input name="checkId" type="hidden" value="<c:out value='${result.userTy}'/>:<c:out value='${result.uniqId}'/>"/>
				</td>
				<td><c:out value="${status.count}"/></td>
				<td>
					<a href="<c:url value='/uss/umt/EgovMberSelectUpdtView.do'/>?selectedId=<c:out value="${result.uniqId}"/>"  onclick="javascript:fnSelectUser('<c:out value="${result.userTy}"/>:<c:out value="${result.uniqId}"/>'); return false;"><c:out value="${result.userId}"/></a>
				</td>
				<td><c:out value="${result.userNm}"/></td>
				<td><c:out value="${result.emailAdres}"/></td>
				<td><c:out value="${result.areaNo}"/>)<c:out value="${result.middleTelno}"/>-<c:out value="${result.endTelno}"/></td>
				<td><fmt:formatDate value="${result.sbscrbDe}" pattern="yyyy-MM-dd" /></td>
				<td><c:out value="${result.authorNm}"/></td>
				<td>
				<c:forEach var="entrprsMberSttus_result" items="${entrprsMberSttus_result}" varStatus="status">
					<c:if test="${result.sttus == entrprsMberSttus_result.code}"><c:out value="${entrprsMberSttus_result.codeNm}"/></c:if>
				</c:forEach>
				</td>
				<td><a href="<c:url value='/bdp/user/uploadList.do?userId=${result.userId}'/>" class="btn grey">보기</a></td>
				<td><a href="<c:url value='/bdp/user/articleList.do?userId=${result.userId}'/>" class="btn grey">보기</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<!--  page start -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
	<!--  page end -->
</form>
</div>
