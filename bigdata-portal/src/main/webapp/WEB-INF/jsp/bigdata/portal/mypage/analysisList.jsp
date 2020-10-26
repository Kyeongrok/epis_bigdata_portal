<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

<!-- 
<c:out value='${analysisList}'/>
<c:out value='${analysisMergeList}'/>
<c:out value='${visualizeList}'/>
 -->

<div class="total">
	<span class="count">분석 
		<strong><c:out value='${analysisListSize}' /></strong>건	</span>
	<div class="btnarea">
		<a href="/bdp/analysis/createList.do" class="btn red w80"><i class="ico plus"></i>더 보기</a>
	</div>
</div>



<table class="tbl01 column6" summary="빅데이터셋 리스트 게시판입니다.">
	<caption>빅데이터셋 데이터 리스트 테이블</caption>
	<thead>
		<tr>
			<th></th>
			<th>번호</th>
			<th>분류</th>
			<th>분석명</th>
			<th>상태</th>
			<th>등록일</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="row" items="${analysisList}" varStatus="status">
			<tr>
				<td></td>
				<td>${analysisListSize-status.index}</td>
				<td>${row.anaDataKindCodeNm}</td>
				<td><c:choose>
						<c:when test="${row.anaStat == 'Y'}">
							<a href="/bdp/analysis/createVisualView.do?anaIdx=${row.anaIdx}"><c:out
									value="${row.anaTitle}" /></a>
						</c:when>
						<c:when test="${row.anaStat == 'N'}">
							<a href="javascript:alert('분석중입니다.')">${row.anaTitle}</a>
						</c:when>
						<c:when test="${row.anaStat == 'R'}">
							<a href="javascript:alert('분석중입니다.')">${row.anaTitle}</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:alert('데이터 분석 실행중 오류가 발생하여 분석진행이 취소되었습니다.')"><c:out
									value="${row.anaTitle}" /></a>
						</c:otherwise>
					</c:choose></td>
				<td><c:out value='${row.anaStatStr}' /></td>
				<td><fmt:formatDate pattern="yyyy.MM.dd" value="${row.regDate}" /></td>
			</tr>
		</c:forEach>

		<c:if test="${analysisListSize == 0}">
			<tr>
				<td colspan="6">데이터가 없습니다.</td>
			</tr>
		</c:if>
	</tbody>
</table>

<br />

<div class="total">
	<span class="count">결합 <strong><c:out value='${analysisMergeListSize}' /></strong>건</span>
	<div class="btnarea">
		<a href="/bdp/analysis/mergeList.do" class="btn red w80"><i class="ico plus"></i>더 보기</a>
	</div>
</div>
<table class="tbl01 column5_merge" summary="데이터결합 리스트 게시판입니다.">
	<caption>데이터결합 리스트 테이블</caption>
	<thead>
		<tr>
			<th></th>
			<th>번호</th>
			<th>데이터명</th>
			<th>상태</th>
			<th>등록일</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach var="row" items="${analysisMergeList}" varStatus="status">
			<tr>
				<td></td>
				<td>${analysisMergeListSize-status.index}</td>
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

		<c:if test="${analysisMergeListSize == 0}">
			<tr>
				<td colspan="5">데이터가 없습니다.</td>
			</tr>
		</c:if>
	</tbody>
</table>

<br />
<br />

<div class="total">
	<span class="count">시각화 <strong><c:out value='${visualizeListSize}' /></strong>건</span>
	<div class="btnarea">
		<a href="/bdp/visual/list.do" class="btn red w80"><i class="ico plus"></i>더 보기</a>
	</div>
</div>
<table class="tbl01 column5_merge" summary="데이터결합 리스트 게시판입니다.">
	<caption>데이터결합 리스트 테이블</caption>
	<thead>
		<tr>
			<th></th>
			<th>번호</th>
			<th>데이터명</th>
			<th>상태</th>
			<th>등록일</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach var="row" items="${visualizeList}" varStatus="status">
			<tr>
				<td></td>
				<td>${visualizeListSize-status.index}</td>
				<td><a href="/bdp/visual/view.do?visId=${row.visId}">${row.visTitle}</a>
				</td>
				<c:choose>
					<c:when test="${row.visOpenAt == 'Y'}">
						<td>전체공개</td>
					</c:when>
					<c:otherwise>
						<td>비공개</td>
					</c:otherwise>
				</c:choose>
				<td><fmt:formatDate pattern="yyyy.MM.dd"
						value="${row.visRegistDttm}" /></td>
			</tr>
		</c:forEach>

		<c:if test="${visualizeListSize == 0}">
			<tr>
				<td colspan="5">데이터가 없습니다.</td>
			</tr>
		</c:if>

	</tbody>
</table>

