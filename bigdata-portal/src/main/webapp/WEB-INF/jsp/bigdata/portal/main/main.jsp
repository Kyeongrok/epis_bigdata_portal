<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!-- S : 컨텐츠 -->
<div class="visual">
	<div class="wrapper">
		<div class="sch_wrap">
			<p class="tit"><strong>농림축산식품부 빅데이터 포털</strong>농림축산식품부와 연관기관의 데이터 공동활용 관리 체계를 제공합니다.</p>
			<form id="mainSearchFrm" name="mainSearchFrm" method="get"
				action="<c:url value='/bdp/dataset/searchAll.do'/>">
				<span class="searcharea">
					<input type="text" name="searchKeyword" placeholder="데이터를 검색 해 보세요." title="데이터 입력">
					<a href="javascript:;" id="mainSearchBtn"><i class="ico search">검색</i></a>
				</span>
			</form>
		</div>
		<div class="info">
			<div>
				<dl>
					<dt><i class="ico visual1"></i>빅데이터셋</dt>
					<dd><strong>${bdsGrpTotal}</strong> 종<br />데이터 ${bdsTotal}건</dd>
				</dl>
			</div>
			<div>
				<dl>
					<dt><i class="ico visual2"></i>통계데이터셋</dt>
					<dd><strong>${staGrpTotal}</strong> 종<br />데이터 ${staTotal}건</dd>
				</dl>
			</div>
			<div>
				<dl>
					<dt><i class="ico visual3"></i>농식품공공데이터</dt>
					<dd><strong>${pdlGrpTotal}</strong> 종<br />리스트 ${pdlTotal}건</dd>
				</dl>
			</div>
			<div>
				<dl>
					<dt><i class="ico visual4"></i>민간데이터</dt>
					<dd><strong>${priGrpTotal}</strong> 종<br />데이터 ${priTotal}건</dd>
				</dl>
			</div>
		</div>
	</div>
</div>
<div class="mbody">
	<div class="wrapper">
		<section class="m_section1">
			<h1>시각화<em>데이터 분석 결과를 시각화 된 정보로 보다 쉽게 확인하세요.</em></h1>
			<c:forEach var="row" items="${visualizeList}" varStatus="status">
				<div><a href="<c:url value='/bdp/prcuse/visualizePblonsipDetail.do'/>?nttId=${row.nttId}">
					<img src="/bdp/prcuse/visualizePblonsipDwld.do?atchFileId=${row.files[0].atchFileId}&amp;fileNo=${row.files[0].fileNo}" alt="${row.files[0].originFileName}" height="240px">
					<span><em><c:out value="${row.nttSj}" /></em></span>
					</a></div>
			</c:forEach>
			<a href="/bdp/prcuse/visualizePblonsipList.do" class="btn m_more">더보기<i class="arrow"></i></a>
		</section>
		<%-- <section class="m_section2">
			<a href="/bdp/analysis/create.do">
				<span class="banner">정형데이터<em>정형데이터를 분석할수 있습니다.</em></span>
				<span class="btn mbanner">분석하기<i class="arrow ml30"></i></span>
			</a>
			<a href="/bdp/analysis/create.do?atype=A008">
				<span class="banner">공간데이터<em>공간 정보를 활용하여 분석할수 있습니다.</em></span>
				<span class="btn mbanner">분석하기<i class="arrow ml30"></i></span>
			</a>
		</section>--%>



<!--  S : 새로운 소식  -->
		<section class="m_section2">
			<h2>새로운 소식</h2>
			<div>
				<h3>공지사항</h3>
				<ul>
					<c:if test="${fn:length(noticeList) > 0}">
						<c:forEach var="notice" items="${noticeList}">
							<li><a href="/bdp/prcuse/noticeView.do?nttId=${notice.nttId}"><strong>${notice.nttSj}</strong><em>${fn:substring(notice.frstRegistPnttm,0,10)}</em></a></li>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(noticeList) == 0}">
						<li>게시글이 없습니다.</li>
					</c:if>
				</ul>
				<a href="/bdp/prcuse/noticeList.do" class="btn m_more">더보기<i class="arrow"></i></a>
			</div>
			<div>
				<h3>문의사항</h3>
				<ul>
					<c:if test="${fn:length(questionList) > 0}">
						<c:forEach var="question" items="${questionList}">
							<li><a href="/bdp/prcuse/questionView.do?nttId=${question.nttId}"><strong>${question.nttSj}</strong><em>${fn:substring(question.frstRegistPnttm,0,10)}</em></a></li>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(questionList) == 0}">
						<li>게시글이 없습니다.</li>
					</c:if>
				</ul>
				<a href="/bdp/prcuse/questionList.do" class="btn m_more">더보기<i class="arrow"></i></a>
			</div>
			<div>
				<h3>신규빅데이터셋</h3>
				<ul>
					<c:if test="${fn:length(newBigdataSetList) > 0}">
						<c:forEach var="newBigdataSet" items="${newBigdataSetList}">
							<li><a href="/bdp/prcuse/newBigdatasetReqList.do?nttId=${newBigdataSet.nttId}"><strong>${newBigdataSet.nttSj}</strong><em>${fn:substring(newBigdataSet.frstRegistPnttm,0,10)}</em></a></li>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(newBigdataSetList) == 0}">
						<li>게시글이 없습니다.</li>
					</c:if>
				</ul>
				<a href="/bdp/prcuse/newBigdatasetReqList.do" class="btn m_more">더보기<i class="arrow"></i></a>
			</div>
		</section>
<!--  E : 새로운 소식  -->



		<section class="m_section3">
			<a href="/bdp/mypage/myDataList.do">
				<span class="banner">MyData 바로가기<em>
						빅데이터포털에서 나의 데이터를 업로드하고 분석할 수 있게하는 지원서비스 입니다.</em></span>
				<span class="mbanner">바로가기<i class="arrow ml20"></i></span>
			</a>
		</section>
		<a href="#wrap" class="to_top disnone"><span style="display:none;">맨위로</span></a>
	</div>
</div>
<!-- E : 컨텐츠 -->
<script type="text/javascript">
	$(function () {
		$("#mainSearchFrm").submit(function () {
			if ($.trim($("#mainSearchFrm [name=searchKeyword]").val()) == "") {
				alert("검색어를 입력하세요.");
				return false;
			}
		});

		$("#mainSearchBtn").click(function () {
			$("#mainSearchFrm").submit();
			return false;
		});
	});
</script>