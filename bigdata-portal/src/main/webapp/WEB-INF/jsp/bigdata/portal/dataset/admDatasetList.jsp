<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>


<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/admin/dataset/list.do'/>" >
		<input type="hidden" name="searchType1" value="${searchType1}" />
		<select name="searchCondition" title="검색 조건 선택">
			<option value="all" <c:if test="${searchCondition == 'all'}">selected="selected"</c:if> >전체</option>
			<option value="dataCateDept" <c:if test="${searchCondition == 'dataCateDept'}">selected="selected"</c:if> >데이터셋 분류</option>
			<option value="dataList" <c:if test="${searchCondition == 'dataList'}">selected="selected"</c:if> >데이터목록</option>
			<option value="dataSet" <c:if test="${searchCondition == 'dataSet'}">selected="selected"</c:if> >데이터셋명</option>
			<option value="orgName" <c:if test="${searchCondition == 'orgName'}">selected="selected"</c:if> >제공기관</option>
		</select>
			
		<span class="searcharea">
			<input type="text" name="searchKeyword" id="searchKeyword" value="<c:out value='${searchKeyword}'/>" title="검색 단어 입력"/>
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
	</form>
</div>

<div class="tablearea">
	<div class="total">
		<span class="count"><strong><c:out value='${pageInfo.totalRecordCount}'/></strong>건</span>
		<div class="btnarea">			
			<a href="/bdp/admin/dataset/list.do" class="btn <c:choose><c:when test='${searchType1 == ""}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">전체</a>
			<a href="/bdp/admin/dataset/list.do?searchType1=BDS" class="btn <c:choose><c:when test='${searchType1 == "BDS"}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">빅데이터셋</a>
			<a href="/bdp/admin/dataset/list.do?searchType1=STA" class="btn <c:choose><c:when test='${searchType1 == "STA"}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">통계데이터</a>
			<a href="/bdp/admin/dataset/list.do?searchType1=PRI" class="btn <c:choose><c:when test='${searchType1 == "PRI"}'>red</c:when><c:otherwise>grey</c:otherwise></c:choose>">민간데이터</a>
		</div>
	</div>
	
	<form name="datasetForm">	
	<table class="tbl01 adm_tbl01 column10_mber" summary="빅데이터셋 관리 리스트 게시판입니다.">
		<caption>빅데이터셋 데이터 리스트 테이블</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>원천</th>
				<th>분석</th>
				<th>개방</th>
				<th>구분</th>
				<c:if test="${searchType1 == 'BDS' || searchType1 == ''}">
				<th>데이터셋 분류</th>
				</c:if>
				<th>데이터목록</th>
				<th>데이터셋명</th>
				<th>제공기관</th>
				<th>등록일자</th>
				<th>활성상태</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td><c:out value='${articleNo-status.index}'/></td>
				<td><input type="checkbox" id="openRangeSource${status.index}" name="openRangeSource" value="Y" data-dsId="${row.dsId}" <c:if test="${row.dsSourceOpen == 'Y'}">checked="checked"</c:if> /><label for="openRangeSource${status.index}"><span></span></label></td>
				<td><input type="checkbox" id="openRangeAnalysis${status.index}" name="openRangeAnalysis" value="Y" data-dsId="${row.dsId}" <c:if test="${row.dsAnalysisOpen == 'Y'}">checked="checked"</c:if> /><label for="openRangeAnalysis${status.index}"><span></span></label></td>
				<td><input type="checkbox" id="openRangeOpen${status.index}" name="openRangeOpen" value="Y" data-dsId="${row.dsId}" <c:if test="${row.dsDataOpen == 'Y'}">checked="checked"</c:if> /><label for="openRangeOpen${status.index}"><span></span></label></td>
				<td><c:choose><c:when test="${row.dlType == 'BDS' }">빅데이터셋</c:when><c:when test="${row.dlType == 'STA' }">통계데이터</c:when><c:when test="${row.dlType == 'PRI' }">민간데이터</c:when></c:choose></td>
				<c:if test="${searchType1 == 'BDS' || searchType1 == ''}">
				<td><span class="depth">${row.dlCateDept1}</span><c:if test="${not empty row.dlCateDept2}"><span class="depth">${row.dlCateDept2}</span></c:if></td>
				</c:if>
				<td><a href="<c:url value='/bdp/admin/dataset/datasetView.do'/>?dsId=${row.dsId}&pageIndex=${pageIndex}">${row.dlName}</a></td>
				<td><a href="<c:url value='/bdp/admin/dataset/datasetView.do'/>?dsId=${row.dsId}&pageIndex=${pageIndex}"><c:out value='${row.dsName}'/></a></td>
				<td><c:out value='${row.orgName}'/></td>
				<td><c:out value='${row.dsLastUpdateDate}'/></td>
				<td>
					<select name="dsUseAt" class="dsUseAt" data-dsId="${row.dsId}">
						<option value="Y" <c:if test="${row.dsUseAt == 'Y'}">selected="selected"</c:if>>사용</option>
						<option value="N" <c:if test="${row.dsUseAt == 'N'}">selected="selected"</c:if>>사용안함</option>
						<option value="R" <c:if test="${row.dsUseAt == 'R'}">selected="selected"</c:if>>보류</option>
					</select>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	
	<!-- pagination -->
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
</div>

<script type="text/javascript">
function fn_link_page(pageNo) {
	location.href = "<c:url value='/bdp/dataset/list.do'/>?pageIndex="+pageNo;
}

$(function() {
	$("#searchListBtn").click(function() {
		
		var searchKeyword = $("#searchKeyword").val();
		if(searchKeyword == "") {
			alert('검색 단어를 입력하세요!');
			return false;
		} else {
			var frm = document.searchListFrm;
			frm.submit();
		}

	});	

	// 개방값 변경
	$("input[name=openRangeSource]").click(function() {
		
		var dsId = $(this).attr("data-dsId");
		var val = "N";
		if($(this).is(":checked")) {
			val = "Y";
		} else {
			val = "N";
		}
		
		var param = {
			"dsId" : dsId,
			"val" : val,
			"target" : "SOURCE",
			"mode" : "OPEN_MOD"
		};
		
		ajaxUpdateOpenData(param);

	});
	
	$("input[name=openRangeAnalysis]").click(function() {
		var dsId = $(this).attr("data-dsId");
		var val = "N";
		if($(this).is(":checked")) {
			val = "Y";
		} else {
			val = "N";
		}
		
		var param = {
			"dsId" : dsId,
			"val" : val,
			"target" : "ANALYSIS",
			"mode" : "OPEN_MOD"
		}
		
		ajaxUpdateOpenData(param);
		
	});
	
	$("input[name=openRangeOpen]").click(function() {
		var dsId = $(this).attr("data-dsId");
		var val = "N";
		if($(this).is(":checked")) {
			val = "Y";
		} else {
			val = "N";
		}
		
		var param = {
			"dsId" : dsId,
			"val" : val,
			"target" : "DATAOPEN",
			"mode" : "OPEN_MOD"
		}
		
		ajaxUpdateOpenData(param);
		
	});
	
	$(".dsUseAt").change(function() {
		var dsId = $(this).attr("data-dsId");
		var dsUseAt = $(this).val();
		$.post("./dsUseAtUpdate.do", {"dsId":dsId,"dsUseAt":dsUseAt}, function(json) {
			if(json.result > 0) {
				alert("수정되었습니다.");
			} else {
				alert('데이터를 수정하지 못했습니다.');
			}
		});
		
	});
	
});


function ajaxUpdateOpenData(param) {
	$.ajax({
		url : "./openRangeProc.do",
		data : param,
		type : "POST",
		dataType : "JSON",
		success : function(json) {
			if(json.result == "error") {
				alert('잘못된 접근으로 업데이트를 완료하지 못했습니다.');
			}
		}, error : function() {
			alert('데이터롤 불러오는중 시간초과로 실패하였습니다.');
		}
	});	
}
</script>