<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />

<div class="w100p">
	<table class="tbl02 column2 vz_view" summary="시각화 뷰 테이블입니다.">
		<caption>시각화 뷰 테이블</caption>
		<tbody>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="" id="vis_title" value="<c:out value="${data.visTitle}"/>" class="w100p fhidden" readonly title="데이터 제목"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tac">
					<form id="visFrm" name="visFrm" action="<c:url value="./delete.do" />" method="post" onsubmit="return false;">
					<input type="hidden" name="visId" value="<c:out value="${data.visId}" />"/>
					
					<input type="hidden" name="visChartX" value="<c:out value="${data.visChartX}" />"/>
					<input type="hidden" name="visChartY" value="<c:out value="${data.visChartY}" />"/>
					<input type="hidden" name="visChartZ" value="<c:out value="${data.visChartZ}" />"/>
					
					<input type="hidden" name="visChartXLabel" value="<c:out value="${data.visChartXLabel}" />"/>
					<input type="hidden" name="visChartYLabel" value="<c:out value="${data.visChartYLabel}" />"/>
					<input type="hidden" name="visChartZLabel" value="<c:out value="${data.visChartZLabel}" />"/>
					</form>
					
					
					<div id="chartdiv" class="vz_graph"><!-- 그래프 영역 --></div>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="comment">
					데이터 분류 : <c:out value="${data.visCate1Name}"/> &gt; <c:out value="${data.visCate2Name}"/><br />
					시각화 종류 : <c:out value="${data.visChartName}"/><br />
					시각화 공개여부 : <c:if test="${data.visOpenAt == 'Y'}">공개</c:if><c:if test="${data.visOpenAt != 'Y'}">비공개</c:if><br />
					
					<fmt:formatDate var="visRegistDttm" value="${data.visRegistDttm}" pattern="yyyy-MM-dd HH:mm" />
					작성일 : ${visRegistDttm}<br />
					
					자료출처 : <c:out value="${data.visSource}"/><br />
					시각화 설명 : <c:out value="${data.visChartDesc}"/>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<h3><i class="ico dataset"></i>데이터셋</h3>

<div id="data_sheet_table" class="databox"></div>

<div class="tac mt40 disinblock w100p">
	<a href="./list.do" class="btn floatl">리스트</a>

	<c:if test="${loginUserId != null && loginUserId == data.userId}">
		<input id="visualize_del_btn" type="button" value="삭제" class="btn grey normal_input vat floatr"/>
	</c:if>
</div>

<script type="text/javascript">

var selectSheet = null,
	sheetData = [],
	sheetHeader = [];

$(function() {
	// 테이블 생성	
	var selectSheetOption = {
   		language: "ko-KR",
		rowHeaders: true,
		colHeaders: true,
		readOnly : true,
		fixedRowsTop:1,
		fixedColumnsLeft:1
	};	
	selectSheet = new Handsontable($("#data_sheet_table")[0], selectSheetOption);
	
	// 시각화 데이터 불러오기
	$.getJSON("./viewData.do", {visId: ${data.visId}}, function(sheetData) {
		var chartType = ${data.visChartType};
		var sheet = sheetData;
		var header = sheetData[0];
		
		selectSheet.loadData(sheetData);

		var visOptions = ${data.visChartProperty};
		
		visOptions._columns = { "x" : [], "y" : [], "z" : [] };
		visOptions._labels = { "x" : [], "y" : [], "z" : [] };
		
		visOptions._columns.x = $("#visFrm input[name=visChartX]").val().split(",");
		visOptions._columns.y = $("#visFrm input[name=visChartY]").val().split(",");
		visOptions._columns.z = $("#visFrm input[name=visChartZ]").val().split(",");
		
		visOptions._labels.x = $("#visFrm input[name=visChartXLabel]").val().split("||");
		visOptions._labels.y = $("#visFrm input[name=visChartYLabel]").val().split("||");
		visOptions._labels.z = $("#visFrm input[name=visChartZLabel]").val().split("||");
		
		visOptions._chartId = "chartdiv";
		visOptions._mapId = "chartdiv";

		visCreateChart(chartType, sheet, visOptions);
	});
	
	$("#visualize_del_btn").click(function() {
		if(!confirm("시각화를 삭제하시겠습니까.?")) return false;
		document.visFrm.submit();
		return false;
	});
});
</script>