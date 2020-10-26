<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />

<!-- S : 공간데이터 -->
<h3>공간데이터</h3>
<div class="w100p">
	<table class="tbl02 tline column4 vz_view" summary="데이터셋 정보 테이블입니다.">
		<caption>공간데이터 정보 테이블</caption>
		<tbody>
			<tr>
				<th>데이터목록</th>
				<td>${row.dlName}</td>
				<th>데이터명</th>
				<td>${row.dsName}</td>
			</tr>
			<tr>
				<th>제공기관</th>
				<td>${row.orgName}</td>
				<th>등록일</th>
				<td><fmt:formatDate value="${row.dsRegistDttm}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th>맵보기</th>
				<td><a href="javascript:window.open('${row.mapViewUrl}')">피노지오</a></td> <!-- button here -->
				<th>제공방식</th>
				<td>${row.dsDataType}</td>
			</tr>
		</tbody>
	</table>
</div>
<!-- E : 데이터셋정보 -->

<!-- S : 데이터상세 -->
<div id="filter_area">
	<ul id="filter_condition">
	</ul>
	<div id="filter_btn"></div>
</div>

<h3 id="h3DataInfo" style="padding-bottom: 25px;">
	<a href="#" class="filter_toggle" onClick="showDataInfo();return false;">데이터 속성보기</a>
	<a href="#" class="filter_toggle" onClick="showRelationDataset();return false;">연관 데이터셋</a>
	<!-- <a href="javascript:void(0)" id="filter_toggle">결과 내 재검색</a> -->
</h3>

<div class="w100p" style="display: none;" id="colInfoTable">
	<table class="tbl02 tline column4 vz_view" id="datasetCol" summary="데이터셋 컬럼정보 테이블입니다." >
		<caption>데이터셋 컬럼정보 테이블</caption>
		<tr>
			<th>번호</th>
			<th>컬럼명</th>
			<th>컬럼ID</th>
			<th>컬럼설명</th>
		</tr>
		<tbody></tbody>
	</table>
</div>

<div class="w100p" style="display: none;" id="relationDatasetTable">
	<table class="tbl02 tline column5 vz_view" summary="데이터셋 뷰 테이블입니다.">
		<caption>연관 데이터</caption>
		<tr>
			<th>데이터명</th>
			<th>제공기관</th>
			<th>서비스유형</th>
			<th>등록일자</th>
		</tr>
			<c:if test="${!empty associativeDataList}">
				<c:forEach var="data" items="${associativeDataList}" varStatus="status">
					<tbody>
						<tr>
							<td>
								<a href="./view.do?dsId=${data.dsId}&esIndexNm=${data.esIndexNm}&pageIndex=${pageIndex}">${data.dsName}</a>
							</td>
							<td>
								${data.orgName}
							</td>
							<td>

							</td>
							<td>
								${data.dsLastUpdateDate}
							</td>
						</tr>
					</tbody>
				</c:forEach>
			</c:if>
			<c:if test="${empty associativeDataList}">
				<tbody>
					<tr>
						<td colspan="4">
							연관 데이터셋이 없습니다.
						</td>
					</tr>
				</tbody>
			</c:if>
	</table>
</div>

<div class="paging viewpage">
	<div class="wrap_page">
		<a href="#" id="datasetPrevPage" data-start-num="${startNum}" class="pre" title="이전 페이지 이동">이전 페이지</a>
		<span class="line"></span>
		<a href="#" id="datasetNextPage" data-end-num="${limit}" class="next" title="다음 페이지 이동">다음 페이지</a>
	</div>
</div>

<div id="gridTable" class="databox"></div>

<h3 id="h3DataInfo" style="padding-bottom: 25px;">
	<!-- <button id="export-excel" class="btn lightgrey intbl ml15"></i>XSL 다운로드</button> -->
	<button id="export-csv" class="btn lightgrey intbl ml5">CSV 다운로드</button>
	<button id="back-list" class="btn lightgrey intbl ml5">목록</button>
</h3>
<!-- E : 데이터상세 -->

<c:if test="${row.dsDataType eq 'TABLE'}">
<script type="text/javascript">

var datasetTab = "${row.dlType}";
var totalRowCount = 0;
var dataInfo = false;
var relationDataset = false;
var dbTotalCount = 0;

function pageCheck(startNum) {
	var endNum = 0;

	if(startNum <= 0) {
		startNum = 0;
	}

	return startNum;
}

function csvDownload() {
	var dsId = "${dsId}";
	var esIndexNm = "${esIndexNm}";
	var startNum = $("#datasetPrevPage").attr("data-start-num");
	var endNum = $("#datasetNextPage").attr("data-end-num");
	var param = {
			"datasetTab": datasetTab,
			"dataId": dsId,
			"startNum": startNum,
			"endNum": endNum,
			"esIndexNm": esIndexNm
	};

	var url = "./rawdatadb.do";

	$.ajax({
		url : url,
		type : "POST",
		data : param,
		dataType : "json",
		success : function(json) {
			var esData = json.esDataList;
			var esColEngInfoList = json.esColEngInfoList;
			var esColHeadersAry = [];

			$.each(esColEngInfoList, function(i, v) {
				esColHeadersAry.push(v.kor);
			});

			var esColNameList = json.esColEngInfoList;

			var esRows = new Array();
			esRows.push(esColHeadersAry);
			$.each(esData, function(i, v) {
				var esRow = new Array();
				$.each(esColNameList, function(j, col) {
					esRow.push(v[col.eng]);
				});
				esRows.push(esRow);
			});

			arrayExportToCsv(esIndexNm + "_" + getFormatDate(new Date()) + ".csv", esRows);
		}, error : function(error) {
			alert("데이터가 존재하지 않습니다.");
		}
	});
}

function getLoadDataJson() {
	if(datasetTab == "STA") {
		return false;
	}

	var startNum = $("#datasetPrevPage").attr("data-start-num");
	var endNum = $("#datasetNextPage").attr("data-end-num");

	console.log("startNum : " + startNum);
	console.log("endNum : " + endNum);

	$.postJSON("./rawdatadb.do", {"datasetTab":datasetTab, "dataId":"${dsId}", "startNum":startNum, "endNum":endNum, "esIndexNm":"${esIndexNm}"}, function(json){
		var esData = json.esDataList;
		var esColEngInfoList = json.esColEngInfoList;
		var esColHeadersAry = [];
		dbTotalCount = json.esTotalCount;

		$.each(esColEngInfoList, function(i, v) {
			esColHeadersAry.push(v.kor);
		});

		var esColNameList = json.esColEngInfoList;

		if($('#datasetCol > tbody > tr').length <= 1) {
			for(var i=0; i< esColNameList.length; i++) {
				var index = (i+1);
				var esColName = esColNameList[i].kor;
				var esColId = esColNameList[i].eng;
				var esColDesc = esColNameList[i].kor;
				var row = '<tr><td>'+index+'</td><td>'+esColName+'</td><td>'+esColId+'</td><td>'+esColDesc+'</td></tr>';
				$('#datasetCol > tbody:first').append(row);
			}
		}

		var esRows = new Array();
		$.each(esData, function(i, v) {
			esRow = {}
			$.each(esColNameList, function(j, col) {
				esRow[col.eng] = v[col.eng];
			});
			esRows.push(esRow);
		});

		console.log(esRows);

		var handsonSetting = {
			data:esRows,
			readOnly:true,
			colHeaders : esColHeadersAry,
			rowHeaders:true
		}

		var container1 = document.getElementById('gridTable');
		hObj = new Handsontable(container1, handsonSetting);

		console.log('check here2');

		//getDataCount(htblId);

		var esColEngList = json.esColEngInfoList;
		$("#filter_condition").html("");
		$.each(esColEngList, function(i, v){
			$("#filter_condition").append('<li><span>'+v.kor+'</span><input type="text" name="'+v.eng+'" data-colname="'+v.eng+'" value="" /></li>');
		});

		$("#filter_btn").html("");
		if(esColEngList.length > 0) {
			$("#filter_btn").append("<button id='filter_reload_btn' class='btn grey'>초기화</button>");
			$("#filter_btn").append("<button id='filter_run_btn' class='btn red ml5'>검색</button>");
		}

	});

}


function getDataCount(htblId) {

	$.postJSON("./rawdataCount.do", {htblId:htblId}, function(json) {
		totalRowCount = json.totalCount;

		if (totalRowCount !="0"){
			$(".count").html("<strong>"+numberWithComma(totalRowCount)+"</strong>건");

		}
	});
}

function showDataInfo() {
	if(!dataInfo) {
		$('#colInfoTable').show();
		$('#relationDatasetTable').hide();
		dataInfo = true;
		relationDataset = false;
		$('#h3DataInfo').eq(0).addClass("filter_toggle open");
	} else {
		$('#colInfoTable').hide();
		dataInfo = false;
	}
}

function showRelationDataset() {
	if(!relationDataset) {
		$('#relationDatasetTable').show();
		$('#colInfoTable').hide();
		relationDataset = true;
		dataInfo = false;
	} else {
		$('#relationDatasetTable').hide();
		relationDataset = false;
	}
}


$(function(){

	getLoadDataJson();

	$("#export-excel").click(function() {
		location.href=
			"<c:url value='/bdp/dataset/excelDownload.do' />?datasetTab=${row.dlType}?dataId=${dsId}";
		return false;
	});

	// export csv
	$("#export-csv").click(function() {
		csvDownload();
		return false;
	});

	$("#back-list").click(function(){
		location.href ="<c:url value='/bdp/dataset/list.do'/>?pageIndex=${pageIndex}&searchType1=BDS&spceDataSe=Y";
	});
});

var hObj;

$(document).ready(function() {

	$(document).on("click", "#datasetPrevPage", function() {
		var startNum = parseInt($("#datasetPrevPage").attr("data-start-num"));
		var calc = startNum - parseInt("${limit}");
		var endNum = 0;
		if(startNum <= 0) {
			alert('처음 페이지입니다.');
			return false;
		}

		// 다음 페이지 이전 페이지 값 체크
		startNum = pageCheck(calc);
		endNum =  parseInt($("#datasetNextPage").attr("data-end-num")) - parseInt("${limit}");

		console.log("이전페이지 startNum: " + startNum);
		console.log("이전페이지 endNum: " + endNum);

		if (startNum < dbTotalCount) {
			$("#datasetPrevPage").attr("data-start-num", startNum);
			$("#datasetNextPage").attr("data-end-num", endNum);

			getLoadDataJson();
		}

		return false;

	});

	$(document).on("click", "#datasetNextPage", function() {
		var startNum = parseInt($("#datasetPrevPage").attr("data-start-num")) + parseInt("${limit}");
		var endNum = 0;
		startNum = pageCheck(startNum);
		endNum = startNum + parseInt("${limit}");

		if(endNum >= dbTotalCount) {
			alert('마지막 페이지입니다.');
			return false;
		}

		$("#datasetPrevPage").attr("data-start-num", startNum);
		$("#datasetNextPage").attr("data-end-num", endNum);

		if(endNum < dbTotalCount) {
			getLoadDataJson();
		}

		return false;
	});

});


</script>
</c:if>
