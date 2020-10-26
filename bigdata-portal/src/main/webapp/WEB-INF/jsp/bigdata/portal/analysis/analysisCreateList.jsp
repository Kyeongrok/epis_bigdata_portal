<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<ul class="tab01 column2 mb30">
	<li><a href="./create.do">분석하기</a></li>
	<li><a href="./createList.do" class="active">분석 리스트</a></li>
</ul>

<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/analysis/createList.do'/>">
	<span class="searcharea">
		<input type="text" name="searchKeyword" id="searchKeyword" value="<c:out value='${searchKeyword}'/>" title="검색 단어 입력" placeholder="데이터를 검색 해 보세요."/>
		<a href="#" id="searchKeywordBtn"><i class="ico search">검색</i></a>
	</span>
	</form>
</div>
<div class="tablearea">
	<form name="listFrm" method="get" action="">
	<input type="hidden" name="mode" value="" />
	<input type="hidden" name="pageIndex" value="${pageIndex}" />
	
		<div class="total">
			<span class="count"><strong>${totalCount}</strong>건</span>
			<div class="btnarea">
				<a href="./create.do" class="btn red w80"><i class="ico plus"></i>분석하기</a>
				<a href="#" id="listDelBtn" class="btn grey">삭제</a>
				<!-- <input type="button" id="listDelBtn" value="삭제" /> -->
			</div>
		</div>
		<table class="tbl01 column6" summary="빅데이터셋 리스트 게시판입니다.">
			<caption>빅데이터셋 데이터 리스트 테이블</caption>
			<thead>
				<tr>
					<th>
						<input type="checkbox" id="check_all" name="check_all">
						<label for="check_all"><span></span></label>
					</th>
					<th>번호</th>
					<th>분류</th>
					<th>분석명</th>
					<th>상태</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="row" items="${anaList}" varStatus="status">
				<tr>
					<td>
						<input type="checkbox" id="anaIdx${row.anaIdx}" name="anaIdx" value="<c:out value='${row.anaIdx}'/>">
						<label for="anaIdx${row.anaIdx}"><span></span></label>
						<%-- <input type="checkbox" name="anaIdx" value="${row.anaIdx}" /> --%>
					</td>
					<td>${articleNo-status.index}</td>
					<td>${row.anaDataKindCodeNm}</td>
					<td><c:choose>
						<c:when test="${row.anaStat == 'Y'}">
							<a href="/bdp/analysis/createVisualView.do?anaIdx=${row.anaIdx}"><c:out value="${row.anaTitle}"/></a>
						</c:when>
						<c:when test="${row.anaStat == 'N'}">
							<a href="javascript:alert('분석중입니다.')">${row.anaTitle}</a>
						</c:when>
						<c:when test="${row.anaStat == 'R'}">
							<a href="javascript:alert('분석중입니다.')">${row.anaTitle}</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:alert('데이터 분석 실행중 오류가 발생하여 분석진행이 취소되었습니다.')"><c:out value="${row.anaTitle}"/></a>
						</c:otherwise>
					</c:choose></td>
					<td><c:out value='${row.anaStatStr}'/></td>
					<td><fmt:formatDate pattern="yyyy.MM.dd" value="${row.regDate}" /></td>		
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
	location.href = "<c:url value='/bdp/analysis/createList.do'/>?pageIndex="+pageNo;
}

$(function(){
	$("#listDelBtn").click(function() {
		var length = $("input:checkbox[name=anaIdx]:checked").length;
		var frm = document.listFrm;
		if(length > 0) {
			var del_ok = false;
			del_ok = confirm("선택한 데이터를 삭제하시겠습니까?");
			if(del_ok) {				
				frm.method = "POST";
				frm.action = "<c:url value='/bdp/analysis/createProc.do'/>";
				frm.mode.value = "listDel";
				frm.submit();
			} else {
				frm.method = "GET";
				frm.action = "";
				frm.mode.value = "";
			}
			
			return false;
		} else {
			alert('삭제할 데이터를 선택하세요!');
			return false;
		}
	});
	
	$("#searchKeywordBtn").click(function() {
		var frm = document.searchListFrm;
		
		if( frm.searchKeyword.value == "" ) {
			alert('검색할 키워드를 입력하세요!');
			return false;
		} else {
			frm.submit();
		}
	});
	
	$("#check_all").click(function() {
		if($("#check_all").is(":checked") == true) {
			$("input:checkbox[name=anaIdx]").each(function() {
				this.checked = true;
			});
		} else {
			$("input:checkbox[name=anaIdx]").each(function() {
				this.checked = false
			});
		}
	});
});
</script>