<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
 /**
  * @Class Name : EgovLoginLogList.jsp
  * @Description : 로그인 로그 정보목록 화면
  * @Modification Information
  * @
  * @  수정일      수정자          수정내용
  * @  -------    --------    ---------------------------
  * @ 2009.03.11   이삼섭          최초 생성
  * @ 2011.07.08   이기하          패키지 분리, 경로수정(sym.log -> sym.log.clg)
  *   2011.09.14      서준식          검색 후 화면에 검색일자 표시안되는 오류 수정
  *  @author 공통서비스 개발팀 이삼섭
  *  @since 2009.03.11
  *  @version 1.0
  *  @see
  *
  */
%>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/sym/cal/EgovCalPopup.js'/>" ></script>
<script type="text/javascript">

	function fn_egov_select_loginLog(pageNo){
		var fromDate = document.frm.searchBgnDe.value;
		var toDate = document.frm.searchEndDe.value;

		if(fromDate > toDate) {
	        alert("종료일자는 시작일자보다  이후날짜로 선택하세요.");
	        //return false;
	    } else {
			document.frm.pageIndex.value = pageNo;
			document.frm.action = "<c:url value='/sym/log/clg/SelectLogList.do'/>";
			document.frm.submit();
	    }
	}

	function fn_egov_inqire_loginLog(logId){
		var url = "<c:url value='/sym/log/clg/InqireLog.do' />?logId="+logId;

		var openParam = "scrollbars=yes,toolbar=0,location=no,resizable=0,status=0,menubar=0,width=750,height=240,left=0,top=0";
		window.open(url,"p_loginLogInqire", openParam);
	}
</script>
<div class="tablearea">
	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>이력관리</h3>

<form name="frm" action ="<c:url value='/sym/log/clg/SelectLogList.do'/>" method="post">
<input type="hidden" name="cal_url" value="<c:url value='/sym/cal/EgovNormalCalPopup.do'/>" />


	<table class="tbl01 adm_tbl01 column5_log">
		<thead>
			<tr>
				<!-- th class="title" width="3%" nowrap><input type="checkbox" name="all_check" class="check2"></th-->
				<th>번호</th>
				<th>로그ID</th>
				<!-- <th class="title" width="30%" nowrap>로그유형</th>  -->
				<th>요청자</th>
				<th>상세보기</th>
				<th>발생일자</th>
			</tr>
		</thead>
		<tbody>
			<%-- 데이터를 없을때 화면에 메세지를 출력해준다 --%>
			<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="6" class="nodata"><spring:message code="common.nodata.msg" /></td>
				</tr>
			</c:if>
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<!--td class="lt_text3" nowrap><input type="checkbox" name="check1" class="check2"></td-->
					<td><c:out
							value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}" /></td>
					<td><c:out value="${result.logId}" /></td>
					<!-- <td><c:out value="${result.loginMthd}"/></td>  -->
					<td><c:out value="${result.loginNm}" /></td>
					<td><c:out value="${result.logDesc}" /></td>
					<td><c:out value="${result.creatDt}" /></td>
					<!-- 
					<td>
						<a href="javascript:fn_egov_inqire_loginLog('<c:out value="${result.logId}"/>');">
							<img src="<c:url value='/images/egovframework/com/cmm/icon/search.gif'/>" alt="상세보기"	width="15" height="15" align="middle">
						</a>
					</td>
					-->
				</tr>
			</c:forEach>
		</tbody>
		<!--tfoot>
			<tr class="">
				<td colspan=6 align="center"></td>
			</tr>
		</tfoot -->
	</table>

	<!--  page start -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
	<!--  page end -->
	<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
</form>

</div>
</body>
</html>
