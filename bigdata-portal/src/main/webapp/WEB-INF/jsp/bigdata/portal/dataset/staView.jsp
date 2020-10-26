<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />

<form name="searchFrm" method="get" action="<c:url value='/bdp/dataset/staView.do'/>">
<input type="hidden" name="dsId" value="${dsId}" />
<input type="hidden" name="tblId" value="${tblId}" />
<input type="hidden" name="pageIndex" value="${param.pageIndex}" />
<input type="hidden" name="esIndexNm" value="${esIndexNm}" />
	<div class="w100p">
		<table class="tbl02 tline column4 vz_view" summary="데이터셋 뷰 테이블입니다.">
			<caption>데이터셋 뷰 테이블</caption>
			<tbody>
				<tr>
					<th>통계테이블명</th>
					<td colspan="3">${dsName}
						<%-- <a href="#" id="popupKosis" data-tblid="${tblId}" target="_blank" class="btn lightgrey intbl ml5">국가통계포털</a> --%>
						<a href="#" id="popupKosis" data-tblid="${dsEndPoint}" target="_blank" class="btn lightgrey intbl ml5">국가통계포털</a>
					</td>
				</tr>
				<tr>
					<th>기간선택</th>
					<td>
						<select name="prdDe" id="prdDe" title="날짜선택">
						<c:forEach var="row" items="${prdDeList}" varStatus="status">
							<option value="${row}" <c:if test="${prdDe == row}">selected="selected"</c:if> >  ${row}</option>
						</c:forEach>
						</select>
						<button id="searchListBtn" class="btn red intbl ml5">검색</button>
					</td>
					<%-- <th>기간</th>
					<td>
						${prdDeStr}
						<button id="export-csv" class="btn lightgrey intbl ml15"><i class="ico download"></i> csv</button>
						<button id="export-excel" class="btn lightgrey intbl ml5"><i class="ico download"></i> excel</button>						
					</td> --%>
				</tr>
			</tbody>
		</table>
	</div>
</form>
	
<h3><i class="ico dataset"></i>데이터셋</h3>

<h3 id="h3DataInfo" style="padding-bottom: 25px;">
	<a href="#" class="filter_toggle" onClick="showRelationDataset();return false;">연관 데이터셋</a>
</h3>
<div class="w100p" id="relationDatasetTable" style="display: none;">
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
						<a href="./staView.do?dsId=${data.dsId}&esIndexNm=${data.esIndexNm}&pageIndex=${pageIndex}&tblId=${data.htblId}">${data.dsName}</a>
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

<div id="staTable" class="databox"></div>

<h3 id="h3DataInfo" style="padding-bottom: 25px;">
	<button id="export-csv" class="btn lightgrey intbl ml5">CSV 다운로드</button>
	<button id="back-list" class="btn lightgrey intbl ml5">목록</button>
</h3>

<!-- <div class="tac mt40 disinblock w100p">
	<a href="./list.do" class="btn floatl">리스트</a>
	<input id="visualize_del_btn" type="button" value="삭제" class="btn grey normal_input vat floatr"/>
</div> -->


<script type="text/javascript">

var relationDataset = false;

function getEsStaDataJson() {
	var dsId = "${dsId}";
	var esIndexNm = "${esIndexNm}";
	var prdDe = "${prdDe}";
	console.log('prdDe : ', prdDe);
	var param = {
		"dsId":dsId,
		"esIndexNm": esIndexNm,
		"prdDe": prdDe
		};
	
	var url = "./getEsStaDataJson.do";
	
	$.ajax({
		url : url,
		type : "POST",
		data : param,
		dataType : "json",
		success : function(json) {
			displayHadsonTable(json);
		}, error : function(error) {
			alert("데이터가 존재하지 않습니다.");
		}
	});
}

function csvDownload() {
	var dsId = "${dsId}";
	var esIndexNm = "${esIndexNm}";
	var prdDe = "${prdDe}";
	console.log('prdDe : ', prdDe);
	var param = {
		"dsId":dsId,
		"esIndexNm": esIndexNm,
		"prdDe": prdDe
		};
	
	var url = "./getEsStaDataJson.do";
	
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
				//console.log("esRow : ", esRow);
				esRows.push(esRow);
			});
			
			arrayExportToCsv(esIndexNm + "_" + getFormatDate(new Date()) + ".csv", esRows);
		}, error : function(error) {
			alert("데이터가 존재하지 않습니다.");
		}
	});
}

function displayHadsonTable(json) {
	
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
	
	var handsonSetting = {
		data:esRows,
		readOnly:true,
		colHeaders : esColHeadersAry,
		rowHeaders:true
	}
	
	hot = new Handsontable(document.getElementById("staTable"), handsonSetting);
	
}

// 배열 중복 제거
function removeDuplicatesArray(arr) {
    var tempArr = [];
    for (var i = 0; i < arr.length; i++) {
        if (tempArr.length == 0) {
            tempArr.push(arr[i]);
        } else {
            var duplicatesFlag = true;
            for (var j = 0; j < tempArr.length; j++) {
                if (tempArr[j] == arr[i]) {
                    duplicatesFlag = false;
                    break;
                }
            }
            if (duplicatesFlag) {
                tempArr.push(arr[i]);
            }
        }
    }
    return tempArr;
}

var winRef;
$(function() {
	getEsStaDataJson();

	$("#searchListBtn").click(function() {
		var frm = document.searchFrm;
		frm.submit();
		return false;
	});
	
	$("#export-excel").click(function() {
		location.href="/excel/download/staExcelDownload.do?dsId=${dsId}&tblId=${tblId}&prdDe=${prdDe}";
		return false;
	});
	
	// export csv
	$("#export-csv").click(function() {
		csvDownload();
		return false;
	});
	
	// list back
	$('#back-list').click(function() {
		location.href="/bdp/dataset/list.do?searchType1=STA";
		return false;
	});
	
	$("#popupKosis").click(function() {
		var endPoint = "${dsEndPoint}";
		// var url = "http://kosis.kr/statHtml/statHtml.do?orgId=114&tblId="+$(this).data("tblid");
		
		if(endPoint.trim().length > 0) {
			winRef = window.open (endPoint, "winPopupKosis","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1080, height=850");
			winRef.focus();	
		} else {
			alert("등록된 국가통계포털 URL이 없습니다.");
		}
		
		
		return false;
		
	});
	
	
});

function showRelationDataset() {
	if(!relationDataset) {
		$('#relationDatasetTable').show();
		//$('#colInfoTable').hide();		
		relationDataset = true;
		//dataInfo = false;
	} else {
		$('#relationDatasetTable').hide();
		relationDataset = false;
	}
}

</script>