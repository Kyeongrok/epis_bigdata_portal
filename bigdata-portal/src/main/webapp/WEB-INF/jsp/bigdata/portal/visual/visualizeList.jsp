<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="searchbox">
	<form id="searchListFrm" name="searchListFrm" method="get" action="<c:url value='./list.do'/>">
			<select name="searchType1" title="품목별 분류">
				<option value="">품목별 분류</option>
				<c:forEach var="row" items="${codeItem}" varStatus="status">
					<option value="${row.code}" <c:if test="${row.code == param.searchType1}">selected="selected"</c:if>>${row.codeNm}</option>
				</c:forEach>
			</select>
			<select name="searchType2"  title="영역별 분류">
				<option value="">영역별 분류</option>
				<c:forEach var="row" items="${codeType}" varStatus="status">
					<option value="${row.code}" <c:if test="${row.code == param.searchType2}">selected="selected"</c:if>>${row.codeNm}</option>
				</c:forEach>
			</select>
		<span class="searcharea">
			<input type="hidden" name="searchMy" value="<c:out value="${param.searchMy}"/>"/>
			<input type="text" placeholder="데이터를 검색해 보세요." name="searchKeyword" id="searchKeyword" value="<c:out value="${param.searchKeyword}"/>"  title="데이터 검색어 입력">
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
	</form>
</div>

<div class="tablearea">
	<div class="total">
		<c:if test="${isLogin == true}">
		<span><input type="checkbox" id="my" name="my" class="tswitch" <c:if test="${param.searchMy == 'Y'}">checked="checked"</c:if>><label for="my" class="mt20 deep_grey"><span></span>내시각화만 보기</label></span>
		</c:if>
		<div class="btnarea">
			<a href="<c:url value='./create.do'/>" class="floatr btn red"><i class="ico plus"></i>시각화하기</a>
		</div>
	</div>
	<div class="glist">
		<c:forEach var="row" items="${dataList}" varStatus="status">
		<div>
			<fmt:formatNumber var="graphImageNo" minIntegerDigits="2" value="${row.visChartType}" type="number"/>
			<a href="<c:url value='./view.do'/>?visId=${row.visId}">
				<c:choose>
					<c:when test="${row.visThumbPath != null and row.visThumbPath.length() > 8}">
						<img src="./thumbnail.do?date=${row.visThumbPath.substring(0, 8)}&thumbId=${row.visThumbPath.substring(9, row.visThumbPath.length() - 4)}" alt="<c:out value="${row.visTitle}"/>">
					</c:when>
					<c:otherwise>
						<img src="/images/bigdata/graph_view/graph${graphImageNo}.jpg" alt="<c:out value="${row.visTitle}"/>">
					</c:otherwise>
				</c:choose>
				<span><em><c:out value="${row.visTitle}"/></em></span>
			</a>
		</div>
		</c:forEach>
	</div>
	
	<c:import url="/WEB-INF/jsp/layout/pages.jsp" />
</div>

<script type="text/javascript">
$(function() {
	$("#searchListFrm").submit(function() {
		if($.trim($("#searchListFrm [name=searchKeyword]").val()) == "" 
				&& $("#searchListFrm [name=searchType1]").val() == "" 
				&& $("#searchListFrm [name=searchType2]").val() == "") {
			alert("검색어를 입력하세요.");
			return false;
		}
	});
	
	$("#searchListBtn").click(function() {
		$("#searchListFrm").submit();
		return false;
	});
	
	// 내시각화만 보기
	$("#my").click(function() {
		$("#searchListFrm input[name='searchMy']").val($(this).is(":checked") ? "Y" : "N");
		document.searchListFrm.submit(); // 입력체크를 실행하지않고 서브밋 
	})
});
</script>