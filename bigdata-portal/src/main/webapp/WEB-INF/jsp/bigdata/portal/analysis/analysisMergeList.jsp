<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ul class="tab01 column2 mb30">
	<li><a href="merge.do">결합하기</a></li>
	<li><a href="./mergeList.do" class="active">결합 리스트</a></li>
</ul>

<div class="searchbox">
	<form name="searchListFrm" method="get" action="<c:url value='/bdp/analysis/mergeList.do'/>">
		<span class="searcharea">
			<input type="text" name="searchKeyword" id="searchKeyword" value="<c:out value='${searchKeyword}'/>" placeholder="데이터를 검색 해 보세요.">
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
			<a href="./merge.do" class="btn red w80"><i class="ico plus"></i>결합하기</a>
			<a href="#" id="listDelBtn" class="btn grey">삭제</a>
		</div>
	</div>
	<table class="tbl01 column5_merge" summary="데이터결합 리스트 게시판입니다.">
		<caption>데이터결합 리스트 테이블</caption>
		<thead>
			<tr>
				<th>
					<input type="checkbox" id="check_all" name="check_all">
					<label for="check_all"><span></span></label>
				</th>
				<th>번호</th>
				<th>데이터명</th>
				<th>상태</th>
				<th>등록일</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="row" items="${dataList}" varStatus="status">
			<tr>
				<td><input type="checkbox" id="anadmIdx${row.anadmIdx}" name="anadmIdx" value="${row.anadmIdx}" /><label for="anadmIdx${row.anadmIdx}"><span></span></label></td>
				<td>${articleNo-status.index}</td>
				<td><c:choose>
						<c:when test="${row.anadmMergeStat == 'Y'}">
							<a href="/bdp/analysis/mergeView.do?anadmIdx=${row.anadmIdx}">${row.anadmTitle}</a>
						</c:when>
						<c:when test="${row.anadmMergeStat == 'N'}">
							<a href="javascript:alert('결합중입니다.\r\n 결합완료후 확인이 가능합니다.')">${row.anadmTitle}</a>
						</c:when>
						<c:when test="${row.anadmMergeStat == 'R'}">
							<a href="javascript:alert('결합중입니다.\r\n 결합완료후 확인이 가능합니다.')">${row.anadmTitle}</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:alert('데이터 결합 실행중 오류가 발생하여 결합진행이 취소되었습니다.')">${row.anadmTitle}</a>
						</c:otherwise>
					</c:choose></td>
				<td>${row.anadmStatStr}</td>
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

$(function() {
	$("#listDelBtn").click(function() {
		var length = $("input:checkbox[name=anadmIdx]:checked").length;
		var frm = document.listFrm;
		if(length > 0) {
			var del_ok = false;
			del_ok = confirm("선택한 데이터를 삭제하시겠습니까?");
			if(del_ok) {
				frm.method = "POST";
				frm.action = "<c:url value='/bdp/analysis/mergeProc.do' />";
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
			$("input:checkbox[name=anadmIdx]").each(function() {
				this.checked = true;
			});
		} else {
			$("input:checkbox[name=anadmIdx]").each(function() {
				this.checked = false
			});
		}
	});
});
</script>