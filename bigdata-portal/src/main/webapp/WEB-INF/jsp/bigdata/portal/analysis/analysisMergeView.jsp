<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.form.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/js/vendor/spectrum/spectrum.js" />" charset="utf-8"></script>
<link rel="stylesheet" href="<c:url value="/js/vendor/spectrum/spectrum.css" />" type="text/css" />

<ul class="tab01 column2 mb30">
	<li><a href="merge.do">결합하기</a></li>
	<li><a href="./mergeList.do" class="active">결합 리스트</a></li>
</ul>

<div class="tit_merge">
	<input type="text" name="visTitle" id="vis_title" value="" readonly="readonly" />
</div>

<h3><i class="ico dataset"></i>데이터셋</h3>

<div id="data_sheet_table" class="databox"></div>

<div class="tac mt40">
	<input id="visualize_move_btn" type="button" value="데이터분석 등록" class="btn grey normal_input" />
	<input id="visualize_save_btn" type="button" value="CSV저장" class="btn red normal_input"/>
</div>

<script type="text/javascript">

var selectSheetNum = "",
	selectSheet = null,
	sheetData = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	sheetHeader = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	selectDataset = {a:{}, b:{}},
	dialogSheet = null,
	filterPointer = [],
	filterIsReplace = false;

$(function() {
	// 테이블 생성	
	var selectSheetOption = {
		language: "ko-KR",
		search: true,
		rowHeaders: true,
		rowHeights: 30,
		colHeaders: true,
		contextMenu: true,
		selectionMode: 'range',
		renderAllRows: false,
		viewportColumnRenderingOffset : 20,
		viewportRowRenderingOffset : 30,
		outsideClickDeselects: false,
		autoColumnSize: false,
		autoRowSize:false,
		manualColumnResize: true,
		fixedRowsTop:1,
		fixedColumnsLeft:1,
		afterCreateCol : function () {
			sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		afterRemoveCol : function() {
			sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		//afterCreateRow : function (index) {
		//	if(index == 0) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		//},
		//afterRemoveRow : function(index) {
		//	if(index == 0) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		//},
		afterUndo : function() {
			if(selectSheet.countCols() != sheetHeader[selectSheetNum]) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		afterRedo : function() {
			if(selectSheet.countCols() != sheetHeader[selectSheetNum]) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		afterChange : function(changes) {
			if(changes != null) 
				changes.forEach(function(param) {
					if(param.row == 0) {
						sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
						return false;
					}
				});
		}
	};	
	selectSheet = new Handsontable($("#data_sheet_table")[0], selectSheetOption);
	
	// TEST
	/*
	sheetData["a"] = [['PNU(동)','AAA','BB','CC','DD','EE','위도','경도'],
		['27110',35,39,26,57,96,128.5488060862452,35.83719516471503],
		['27140',14,90,72,49,46,128.53329768741622,35.858748273078476],
		['27170',94,47,28,85,7,128.64567131821127,35.84036434790146],
		['27230',10,93,22,50,66,128.59168237312844,35.80916637744599]];
	*/
	
	$.ajax({
		url : "./getAnalysisMergeGrid.do",
		data : {anadmIdx:"${anadmIdx}"},
		type : "POST",
		dataType : "JSON",
		async : false,
		timeout : 5000,
		success : function(json) {
			var dataList = json.dataList;
			sheetData["a"] = dataList;
			$("#vis_title").val(json.anadmTitle);
		}, error : function() {
			alert('데이터롤 불러오는중 시간초과로 실패하였습니다.');
		}
	});
	
	
	selectSheetNum = "a";
	selectSheet.loadData(sheetData[selectSheetNum]);
	sheetHeader[selectSheetNum] = sheetData[selectSheetNum][0];
	
	loadSelectedDataAfter();
	
	$("#visualize_move_btn").click(function() {
		document.location.href = "./create.do?init=MGE&id=${anadmIdx}";
	});
	
	$("#visualize_save_btn").click(function() {
		/*
		var csvRows = [];
		$.each(sheetData["a"], function(i, v){
			csvRows.push(v);
		});
		
		arrayExportToCsv($("#vis_title").val()+"_utf8.csv", csvRows);
		csvRows = null;
		*/
		document.location.href="/csv/download/mergeCsv.do?anadmIdx=${anadmIdx}";
		return false;
	});

});

//데이터 로드 후 컬럼 사이즈 맞춤
//자동맞춤일경우 랜더링시간이 많이 걸려 최초 데이터 로드시에만 실행하도록 변경
function loadSelectedDataAfter() {
	var plugin = selectSheet.getPlugin('autoColumnSize');
	plugin.calculateColumnsWidth(void 0, void 0, true);
	var colWidths = [];
	for (var i = 0;i < selectSheet.countCols(); i++) {
		colWidths.push(plugin.getColumnWidth(i));
	}
	selectSheet.updateSettings({colWidths: colWidths});
}
</script>