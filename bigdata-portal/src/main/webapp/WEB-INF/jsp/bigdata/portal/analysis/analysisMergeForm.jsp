<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.form.min.js" />" charset="utf-8"></script>

<ul class="tab01 column2 mb30">
	<li><a href="merge.do" class="active">결합하기</a></li>
	<li><a href="./mergeList.do">결합 리스트</a></li>
</ul>

<div class="tit_merge">
	<!-- <span id="ana_title_str">제목</span> -->
	<input type="text" name="anadm_title" id="anadm_title" value="" placeholder="제목을 입력하세요">
</div>
<div class="tablearea mergeform">
	<table class="tbl02 column3" summary="데이터결합 테이블입니다.">
		<caption>데이터결합  테이블</caption>
		<thead>
			<tr>
				<th></th>
				<th>데이터셋A</th>
				<th>데이터셋B</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th>데이터명</th>
				<td class="dchoose">
					<a href="#" id="btn_data_selectA" data-sheetnum="a" class="btn redline">데이터선택</a>
					<input type="text" id="anadm_data_name_a" class="fhidden" readonly>
				</td>
				<td class="dchoose">
					<a href="" id="btn_data_selectB" data-sheetnum="b" class="btn redline">데이터선택</a>
					<input type="text" id="anadm_data_name_b" class="fhidden" readonly>
				</td>
			</tr>
			<tr id="selectSheetArea">
				<th>대상컬럼</th>
				<td><select class="chosen" id="columnA" name="columnA" multiple></select></td>
				<td><select class="chosen" id="columnB" name="columnB" multiple></select></td>
			</tr>
			<tr>
				<th>AND조건</th>
				<td>
					<div id="select_atype_selected_column_area1" title="선택한 열을 표시" style="height:0;">
						<ul id="select_atype_selected_column_list1"></ul>
					</div>
					<ul id="and_terms_area1">
						<li>
							<select name="and_terms_column1" id="and_terms_column1" class="seloption"></select>			
							<select name="and_terms_where1" id="and_terms_where1">
								<option value=">">&gt;</option>
								<option value=">=">&gt;&#61;</option>
								<option value="<">&lt;</option>
								<option value="<=">&lt;&#61;</option>
								<option value="=">&#61;</option>
								<option value="<>">&lt;&gt;</option>
							</select>
							<input type="text" name="and_terms_input_value1" id="and_terms_input_value1" value="" placeholder="값을 입력하세요" />
							<button id="and_terms_add_btn1" title="조건 추가">+</button>
						</li>
					</ul>
					<ul id="and_terms_list1"></ul>
				</td>
				<td>
					<div id="select_atype_selected_column_area2" title="선택한 열을 표시" style="height:0;">
						<ul id="select_atype_selected_column_list2"></ul>
					</div>
					<ul id="and_terms_area2">
						<li>
							<select name="and_terms_column2" id="and_terms_column2" class="seloption"></select>			
							<select name="and_terms_where2" id="and_terms_where2">
								<option value=">">&gt;</option>
								<option value=">=">&gt;&#61;</option>
								<option value="<">&lt;</option>
								<option value="<=">&lt;&#61;</option>
								<option value="=">&#61;</option>
								<option value="<>">&lt;&gt;</option>
							</select>
							<input type="text" name="and_terms_input_value2" id="and_terms_input_value2" value="" placeholder="값을 입력하세요" />
							<button id="and_terms_add_btn2" title="조건 추가">+</button>
						</li>
					</ul>
					<ul id="and_terms_list2"></ul>
				</td>
			</tr>
			<tr>
				<th>OR조건</th>
				<td>
					<ul id="or_terms_area1">
						<li>
							<select name="or_terms_column1" id="or_terms_column1" class="seloption"></select>
							<select name="or_terms_where1" id="or_terms_where1">
								<option value=">">&gt;</option>
								<option value=">=">&gt;&#61;</option>
								<option value="<">&lt;</option>
								<option value="<=">&lt;&#61;</option>
								<option value="=">&#61;</option>
								<option value="<>">&lt;&gt;</option>
							</select>
							<input type="text" name="or_terms_input_value1" id="or_terms_input_value1" value="" placeholder="값을 입력하세요" />
							<button id="or_terms_add_btn1" title="조건 추가">+</button>
						</li>
					</ul>
					<ul id="or_terms_list1"></ul>
				</td>
				<td>
					<ul id="or_terms_area2">
						<li>
							<select name="or_terms_column2" id="or_terms_column2" class="seloption"></select>
							<select name="or_terms_where2" id="or_terms_where2">
								<option value=">">&gt;</option>
								<option value=">=">&gt;&#61;</option>
								<option value="<">&lt;</option>
								<option value="<=">&lt;&#61;</option>
								<option value="=">&#61;</option>
								<option value="<>">&lt;&gt;</option>
							</select>
							<input type="text" name="or_terms_input_value2" id="or_terms_input_value2" value="" placeholder="값을 입력하세요" />
							<button id="or_terms_add_btn2" title="조건 추가">+</button>
						</li>
					</ul>
					<ul id="or_terms_list2"></ul>
				</td>
			</tr>
			<tr>
				<th>기준열선택</th>
				<td><select id="anadm_merge_key1" name="anadm_merge_key1"></select></td>
				<td><select id="anadm_merge_key2" name="anadm_merge_key2"></select></td>
			</tr>
		</tbody>
	</table>
</div>


<div id="data_sheetA">
	<h3><i class="ico dataset"></i><a href="#" data-sheetnum="a" disable>데이터셋A</a></h3><!-- a태그 클릭 안되게 해주세요 -->
	<div id="data_sheet_tableA" class="databox"></div>
</div>

<div id="data_sheetB">
	<h3><i class="ico dataset"></i><a href="#" data-sheetnum="b" disable>데이터셋B</a></h3><!-- a태그 클릭 안되게 해주세요 -->
	<div id="data_sheet_tableB" class="databox"></div>
</div>

<div class="tac mt40">
	<button id="ana_run_button" class="btn red w110"><i class="ico check"></i>실행</button>
</div>


<div id="dialog_data_select" title="데이터선택">	
	<ul class="tab01 column2">
		<li><a href="#" data-tab="1" class="active">검색</a></li>
		<li><a href="#" data-tab="2">파일업로드</a></li>
	</ul>	
	<div class="dialog_body mt15">
	
		<div id="dialog_data_search">
		<form>
			<input type="text" name="search" value="" placeholder="검색어를 입력하세요." />
			<select name="datasetTab">
				<option value="BDS">빅데이터셋</option>
				<option value="STA">통계데이터셋</option>
				<option value="PRI">민간데이터셋</option>
				<option value="MGE">결합데이터셋</option>
			</select>
			
			<input type="submit" name="" value="검색" class="btn red"/>
		</form>
			
			<div id="dialog_data_list" class="mt15">
				<script id="tmpl_dialog_data_list" type="text/x-jquery-tmpl">
				<ul>					
					{{each dataList}}
					<li>						
						<span class="dataListChoice_txt">{{= dlName}} {{= dsName}} {{if dlType == "STA"}} <select id="prdDe">
							{{each prdDe}}
							<option value="{{= prdDe}}">{{= prdDe}}</option>
							{{/each}}							
						</select>{{/if}}</span> <span class="dataListChoice" data-tab="{{= dlType}}" data-id="{{= dsId}}" data-title="{{= dsName}}">선택</span>
					</li>
					{{/each}}
					{{if dataList.length <= 0}}
					<li class="nodata">검색 결과가 없습니다.</li>
					{{/if}}
				</ul>
				<c:import url="/WEB-INF/jsp/layout/pages_tmpl.jsp" />
				</script>
			</div>
		</div>

		
		<div id="dialog_data_upload">
			<form action="../dataset/upload.do" method="post" enctype="multipart/form-data">
				<!-- 파일 선택<input type="file" name="file" />
				<input type="submit" value="업로드" /> -->
				<div class="pb10">※엑셀,CSV 파일 업로드시 첫번째 행에 헤더컬럼이 반드시 포함되어 있어야 합니다.</div>
				<div class="file_input_route w84p">
					<input type="hidden" name="target" value="MERGE" />
					<input type="text" readonly="readonly" id="file_route" placeholder="엑셀 또는 csv 파일을 업로드하세요">
					<label>파일 선택<input type="file" name="cleanwordfile" onchange="javascript:document.getElementById('file_route').value = this.value.split('\\')[this.value.split('\\').length-1]"></label>
				</div>				
				<input type="submit" value="업로드" class="btn red w15p"/>
			</form>
		</div>

		<div id="dialog_data_detail" class="databox h200"></div>

	</div>
</div>


<script type="text/javascript">
var selectSheetNum = "",
	selectSheet = null,
	selectSheet1 = null,
	selectSheet2 = null,
	sheetData = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	sheetHeader = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	selectDataset = {a:{}, b:{}},
	dialogSheet = null,
	filterPointer = [],
	filterIsReplace = false;

var userId = "${userId}";

var hdfsFilePath = "";

var anadm_table1 = "";
var anadm_table1_stat = "";
var anadm_table1_and_terms = "";
var anadm_table1_or_terms = "";
var anadm_table1_param_req = "";
// anadm_table1_param_req  sub
var anadm_table1_param_req_ary = [];
var anadm_table1_param_req_obj = {};

var anadm_table1_column = "";

var anadm_table2 = "";
var anadm_table2_stat = "";
var anadm_table2_and_terms = "";
var anadm_table2_or_terms = "";
var anadm_table2_param_req = "";
//anadm_table2_param_req  sub
var anadm_table2_param_req_ary = [];
var anadm_table2_param_req_obj = {};

var anadm_table2_column = "";

var anadm_merge_key = "";

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
		fixedRowsTop: 1,
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
	
	selectSheet1 = new Handsontable($("#data_sheet_tableA")[0], selectSheetOption);
	selectSheet2 = new Handsontable($("#data_sheet_tableB")[0], selectSheetOption);

	
	// 데이터 선택창
	dialogDataSearchReset();
	$("#dialog_data_select").dialog({
		autoOpen: false,
		resizable: false,
		height: "auto",
		width: 700,
		modal: true,
		buttons: {
			"확인": function() {
				if(selectedData() !== false) {
					$(this).dialog("close");
				}
			},
			"취소": function() {
				$(this).dialog("close");
			}
		},
		close : function() {
			dialogDataSearchReset();
		}
	});

	// 데이터 선택A,B 창 오픈
	$("#btn_data_selectA, #btn_data_selectB").click(function() {		
		if($(this).is(".disabled")) return false;
		
		$("#dialog_data_select").dialog("open");
		
		dialogSheet = new Handsontable($("#dialog_data_detail")[0], {
			language : "ko-KR",
			rowHeaders : true,
			colHeaders : true,
			readOnly : true,
			fixedRowsTop: 1,
			fixedColumnsLeft:1
		});

		// 데이터셋 A,B 대상 지정
		var sheetNum = $(this).data("sheetnum");
		selectSheetNum = sheetNum;
		
		if(selectSheetNum == 'a') {
			selectSheet = selectSheet1; 
		} else {
			selectSheet = selectSheet2;
		}
		return false;
	});

	// 데이터 선택 창 내 검색/업로드 등 선택
	$("#dialog_data_select").find(".tab01 > li > a").click(function() {
		$("#dialog_data_select").find(".tab01 > li > a").removeClass("active");
		var tab = $(this).data("tab");
		$("#dialog_data_select").find(".tab01 > li > a").each(function(i){
			if($(this).data("tab") == tab) {
				$(this).addClass("active");
			}
		});
		$("#dialog_data_select").find(".dialog_body > div").hide();
		$("#dialog_data_select").find(".dialog_body > div:eq(" + ($(this).data("tab") - 1) + ")").show();
		$("#dialog_data_detail").show();
		return false;
	});
	
	// 데이터 검색
	$("#dialog_data_search").find("[type=submit]").click(function() {
		var keyword = $("#dialog_data_search").find("input[name=search]").val();
		var datasetTab = $("#dialog_data_search").find("select[name=datasetTab]").val();		
		searchData(1, keyword, datasetTab);
		return false;
	});
	
	// 데이터 업로드
	$("#dialog_data_upload").find("form").ajaxForm({
		url : "../dataset/upload.do",
		method : "post",
		beforeSubmit: function (data, $form, option) {
			if($.trim($form.find("input[type=file]").val()) == "") {
				alert("파일을 선택해 주세요.");
				return false;
			}
        },
        success: function(json){
        	if(json.result == "success") {
        		$("#dialog_data_upload").data({tab: json.datasetTab, type: json.dataType, id: json.dataId});
        		dialogSheet.loadData(json.dataList);
        		
				if(selectSheetNum == "a") {
					// hdfs upload 파일명
					hdfsFilePath = json.hdfsFilePath;
					// "/" 첫번째 문자 제거
					anadm_table1 = hdfsFilePath.replace(/^[/]+/,"");

				} else {
					hdfsFilePath = json.hdfsFilePath;
					// "/" 첫번째 문자 제거
					anadm_table2 = hdfsFilePath.replace(/^[/]+/,"");
				}
        		
        	} else {
        		alert("데이터를 읽지 못하였습니다.");
        		$("#dialog_data_upload").data({tab: "", type: "", id: ""});
        		dialogSheet.loadData([]);
        	}        	
        },
        error: function(error){
        	alert("데이터 전송 중 오류가 발생하였습니다.");
    		$("#dialog_data_upload").data({tab: "", type: "", id: ""});
    		dialogSheet.loadData([]);
        }
    });
	
		
	// 시트선택 reset
	$("#columnA").bind("reset", function() {
		$("#columnA").empty();		
		
		// and, or selectbox reset
		$("#and_terms_area1 select[name=and_terms_column1]").empty();
		$("#or_terms_area1 select[name=or_terms_column1]").empty();
		$("#and_terms_input_value1").val("");
		$("#or_terms_input_value1").val("");
		
		// and, or 조건식 reset
		$("#select_atype_selected_column_list1").empty();
		$("#and_terms_list1").empty();
		$("#or_terms_list1").empty();
		
		// 기준열 reset
		$("#anadm_table1_column").empty();
		
	});
	$("#columnB").bind("reset", function() {
		$("#columnB").empty();

		// and, or selectbox reset
		$("#and_terms_area2 select[name=and_terms_column2]").empty();
		$("#or_terms_area2 select[name=or_terms_column2]").empty();
		$("#and_terms_input_value2").val("");
		$("#or_terms_input_value2").val("");
		
		// and, or 조건식 reset
		$("#select_atype_selected_column_list2").empty();
		$("#and_terms_list2").empty();
		$("#or_terms_list2").empty();
		
		$("#anadm_table2_column").empty();
	});

	$("#selectSheetArea select[name=columnA]").chosen({		
		placeholder_text : "컬럼을 선택하세요.",
	   	no_results_text : "입력하신 컬럼을 찾을 수 없습니다."
	}).addClass("chosen-select1");	
	$("#selectSheetArea select[name=columnB]").chosen({
		placeholder_text : "컬럼을 선택하세요.",
	   	no_results_text : "입력하신 컬럼을 찾을 수 없습니다."
	}).addClass("chosen-select2");
	
	
	// and 조건 추가
	$("#and_terms_add_btn1").click(function() {
		if($("#and_terms_column1 option:selected").text() == "") {
			alert('선택된 열이 없습니다.');
		} else if($("#and_terms_input_value1").val() == "") {
			alert('조건값을 입력하세요!');
			$("#and_terms_input_value1").focus();
		} else {
			var col = $("#and_terms_column1 option:selected").text();
			var where = $("#and_terms_where1").val();
			var value = $("#and_terms_input_value1").val();
			var condition = col + " " + where +" " +value;
			$("#and_terms_list1").css("display","block").append("<li><span class=\"and_text1\">"+condition+"</span><a href=\"#\" class=\"and_text_del\"></a></li>");
		}
		return false;
	});
	$("#and_terms_add_btn2").click(function() {
		if($("#and_terms_column2 option:selected").text() == "") {
			alert('선택된 열이 없습니다.');
		} else if($("#and_terms_input_value2").val() == "") {
			alert('조건값을 입력하세요!');
			$("#and_terms_input_value2").focus();
		} else {
			var col = $("#and_terms_column2 option:selected").text();
			var where = $("#and_terms_where2").val();
			var value = $("#and_terms_input_value2").val();
			var condition = col + " " + where +" " +value;
			$("#and_terms_list2").css("display","block").append("<li><span class=\"and_text2\">"+condition+"</span><a href=\"#\" class=\"and_text_del\"></a></li>");
		}
		return false;
	});
	
	// or 조건 추가
	$("#or_terms_add_btn1").click(function() {
		if($("#or_terms_column1 option:selected").text() == "") {
			alert('선택된 열이 없습니다.');
		} else if($("#or_terms_input_value1").val() == "") {
			alert('조건값을 입력하세요!');
			$("#or_terms_input_value1").focus();
		} else {
			var col = $("#or_terms_column1 option:selected").text();
			var where = $("#or_terms_where1").val();
			var value = $("#or_terms_input_value1").val();
			var condition = col + " " + where + " " + value;
			$("#or_terms_list1").css("display","block").append("<li><span class=\"or_text1\">"+condition+"</span><a href=\"#\" class=\"or_text_del\"></a></li>");
		}
		return false;
	});
	// or 조건 추가
	$("#or_terms_add_btn2").click(function() {
		if($("#or_terms_column2 option:selected").text() == "") {
			alert('선택된 열이 없습니다.');
		} else if($("#or_terms_input_value2").val() == "") {
			alert('조건값을 입력하세요!');
			$("#or_terms_input_value2").focus();
		} else {
			var col = $("#or_terms_column2 option:selected").text();
			var where = $("#or_terms_where2").val();
			var value = $("#or_terms_input_value2").val();
			var condition = col + " " + where + " " + value;
			$("#or_terms_list2").css("display","block").append("<li><span class=\"or_text2\">"+condition+"</span><a href=\"#\" class=\"or_text_del\"></a></li>");
		}
		return false;
	});
});



$(document).ready(function() {
	$(document).on("click", ".and_text_del", function() {
		$(this).parent("li").remove();
		return false;
	});
	$(document).on("click", ".or_text_del", function() {
		$(this).parent("li").remove();
		return false;
	});

	// merge 실행
	$(document).on("click", "#ana_run_button", function() {
		
		var anadm_title = $("#anadm_title").val();
		if(anadm_title == "") {
			alert('제목을 입력하세요.');
			$("#anadm_title").focus();
			
			return false;
		}
		
		// 시트선택
		var columnA_list_ary = [];
		var columnB_list_ary = [];
		
		// and, or 조건
		var and_terms_list1_ary = [];
		var or_terms_list1_ary = [];
		
		var and_terms_list2_ary = [];
		var or_terms_list2_ary = [];
		
		
		$("#columnA option:selected").each(function() {
			columnA_list_ary.push($(this).text());
		});
		$("#columnB option:selected").each(function() {
			columnB_list_ary.push($(this).text());
		});
		
		// A열 and, or 조건
		var and_length1 = $(".and_text1").length;
		var or_length1 = $(".or_text1").length;
		
		
		// B열 and, or 조건
		var and_length2 = $(".and_text2").length;
		var or_length2 = $(".or_text2").length;
		
		for(var i = 0; i < and_length1; i++) {
			var and_terms_obj = {};
			var and_terms_obj_term = $(".and_text1:eq("+i+")").text();
						
			and_terms_obj_term = and_terms_obj_term.replace(/ > /g, "&&>&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ >= /g, "&&>=&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ < /g, "&&<&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ <= /g, "&&<=&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ = /g, "&&=&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ <> /g, "&&<>&&");
			
			and_terms_obj.text = and_terms_obj_term;
			and_terms_list1_ary.push(and_terms_obj);
		}
		
		for(var i = 0; i < or_length1; i++) {
			var or_terms_obj = {};
			var or_terms_obj_temp = $(".or_text1:eq("+i+")").text();
			
			or_terms_obj_temp = or_terms_obj_temp.replace(/ > /g, "&&>&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ >= /g, "&&>=&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ < /g, "&&<&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ <= /g, "&&<=&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ = /g, "&&=&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ <> /g, "&&<>&&");
			
			or_terms_obj.text = or_terms_obj_temp;
			or_terms_list1_ary.push(or_terms_obj);
		}
		
		for(var i = 0; i < and_length2; i++) {
			var and_terms_obj= {};			
			var and_terms_obj_term = $(".and_text2:eq("+i+")").text();
			
			and_terms_obj_term = and_terms_obj_term.replace(/ > /g, "&&>&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ >= /g, "&&>=&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ < /g, "&&<&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ <= /g, "&&<=&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ = /g, "&&=&&");
			and_terms_obj_term = and_terms_obj_term.replace(/ <> /g, "&&<>&&");
			
			and_terms_obj.text = and_terms_obj_term;
			
			and_terms_list2_ary.push(and_terms_obj);
		}
		
		for(var i = 0; i < or_length2; i++) {
			var or_terms_obj = {};
			var or_terms_obj_temp = $(".or_text2:eq("+i+")").text(); 

			or_terms_obj_temp = or_terms_obj_temp.replace(/ > /g, "&&>&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ >= /g, "&&>=&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ < /g, "&&<&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ <= /g, "&&<=&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ = /g, "&&=&&");
			or_terms_obj_temp = or_terms_obj_temp.replace(/ <> /g, "&&<>&&");
			
			or_terms_obj.text = or_terms_obj_temp;
			or_terms_list2_ary.push(or_terms_obj);
		}
		
		// 기준열선택
		//anadm_merge_key에 join
		var anadm_merge_key_ary = [];
		anadm_merge_key_ary.push($("#anadm_merge_key1 option:selected").val());
		anadm_merge_key_ary.push($("#anadm_merge_key2 option:selected").val());
		anadm_merge_key = anadm_merge_key_ary.join(",");
		
		var datasetTab1 = selectDataset["a"].datasetTab;
		var datasetTab2 = selectDataset["b"].datasetTab;
		
		// UP : U, HIVE : H
		anadm_table1_stat = get_ana_table_stat(datasetTab1);
		anadm_table2_stat = get_ana_table_stat(datasetTab2);
		
		// json으로 변경
		anadm_table1_and_terms = JSON.stringify(and_terms_list1_ary);
		anadm_table1_or_terms = JSON.stringify(or_terms_list1_ary);
		
		anadm_table2_and_terms = JSON.stringify(and_terms_list2_ary);
		anadm_table2_or_terms = JSON.stringify(or_terms_list2_ary);
		
		anadm_table1_param_req = "";
		if(datasetTab1 == "STA") {
			anadm_table1_param_req = JSON.stringify(anadm_table1_param_req_ary);
		}
		
		anadm_table2_param_req = "";
		if(datasetTab2 == "STA") {
			anadm_table2_param_req = JSON.stringify(anadm_table2_param_req_ary);
		}
		
		// 시트A선택
		var anadm_table1_column_ary = [];
		$("#columnA option:selected").each(function() {
			anadm_table1_column_ary.push($(this).text());
		});
		anadm_table1_column = anadm_table1_column_ary.join(",");

		// 시트B선택
		var anadm_table2_column_ary = [];
		$("#columnB option:selected").each(function() {
			anadm_table2_column_ary.push($(this).text());
		});
		anadm_table2_column = anadm_table2_column_ary.join(",");
		
		
		if(anadm_table1_column_ary.length <= 0) {
			alert("데이터셋A의 대상컬럼을 선택하세요.");
			return false;
		}
		if(and_terms_list1_ary.length <= 0 && or_terms_list1_ary.length <= 0) {
			alert("데이터셋A의 AND조건 또는 OR조건 둘중 하나를 추가하세요.");
			return false;
		}		
		if($("#anadm_merge_key1 option:selected").val() == ""){
			alert("데이터셋A의 기준열을 선택하세요.");
			return false;
		}
		
		if(anadm_table2_column_ary.length <= 0) {
			alert("데이터셋B의 대상컬럼을 선택하세요.");
			return false;
		}
		if(and_terms_list2_ary.length <= 0 && or_terms_list2_ary.length <= 0) {
			alert("데이터셋B의 AND조건 또는 OR조건 둘중 하나를 추가하세요.");
			return false;
		}		
		if($("#anadm_merge_key2 option:selected").val() == ""){
			alert("데이터셋B의 기준열을 선택하세요.");
			return false;
		}
		
		var save_ok = false;
		save_ok = confirm("현재 선택한 셋으로 결합을 저장하시겠습니까?");
		if(!save_ok) {
			return false;
		}
		
		var url = "./mergeDataInsert.do";
		var param = {
			"anadmTitle" : anadm_title,
			"anadmTable1" : anadm_table1,
			"anadmTable1Stat" : anadm_table1_stat,
			"anadmTable1AndTerms" : anadm_table1_and_terms,
			"anadmTable1OrTerms" : anadm_table1_or_terms,
			"anadmTable1ParamReq" : anadm_table1_param_req,
			"anadmTable1Column" : anadm_table1_column,
			"anadmTable2" : anadm_table2,
			"anadmTable2Stat" : anadm_table2_stat,
			"anadmTable2AndTerms" : anadm_table2_and_terms,
			"anadmTable2OrTerms" : anadm_table2_or_terms,
			"anadmTable2ParamReq" : anadm_table2_param_req,
			"anadmTable2Column" : anadm_table2_column,
			"anadmMergeKey" : anadm_merge_key
		}

		$.ajax({
			url : url,
			type : "POST",
			data : param,
			dataType : "json",
			success : function(data) {
				if(data.result == "OK") {
					location.href = "/bdp/analysis/mergeList.do";
				} else if(data.result == "FAIL") {
					alert('데이터를 저장하지 못했습니다. \r\n값을 다시 설정하여 실행하세요')
				}
			}, error : function() {
				alert('데이터 전송시 오류가 발생하였습니다. 다시 시도하여 주세요!');
			}
		});
	});
	
});

//시트에 데이터 세팅
function setSheetData(data) {
	if(data == null || data.length <= 0 || data[0] == null || data[0].length <= 0) {
		data = null;
	}

	selectSheet.loadData(data);
	loadSelectedDataAfter();
}


//데이터 불러오기 창 리셋
function dialogDataSearchReset() {
	$("#dialog_data_search").find("form")[0].reset();
	$("#dialog_data_list").empty().append($("#tmpl_dialog_data_list").tmpl({dataList : []}));
	$("#dialog_data_detail").empty();
		
	$("#dialog_data_upload").find("form")[0].reset();
	$("#dialog_data_upload").data({tab: "", type: "", id: ""});
	
	$("#dialog_data_select").find(".dialog_body > div").hide().filter(":first").show();
	$("#dialog_data_select").find(".tab01 > li > a").removeClass("active");
	$("#dialog_data_select").find(".tab01 > li > a:first").addClass("active");
	$("#dialog_data_detail").show();
	
	if(dialogSheet) dialogSheet.destroy();
	dialogSheet = null;
}

//데이터 검색
function searchData(pageIndex, keyword, datasetTab) {
	if($.trim(keyword) == "") {
		alert("검색 키워드를 입력하세요.");
		return false;
	}
	
	// 결합데이터셋
	if(datasetTab == "MGE") {		
		$.getJSON("../analysis/searchAnalysisMerge.do", {keyword:keyword, datasetTab:datasetTab, userId:userId, pageIndex:pageIndex, userId:userId}, function(json) {
			
			$("#dialog_data_list").empty().append($("#tmpl_dialog_data_list").tmpl(json));
			$("#dialog_data_list > ul span.dataListChoice").click(function() {
				$("#dialog_data_list > ul span.dataListChoice").removeClass("active");
				$(this).addClass("active");
				
				$("#anadm_data_name_"+selectSheetNum).val($(this).parent().children(".dataListChoice_txt").html().split("<")[0].trim());
				
				loadSelectedMgeDataSample(datasetTab, $(this).data("id"), userId);
				return false;
			});
		});
		
		// 페이징
		$("#dialog_data_list .paging a").click(function() {
			searchData($(this).data("page"), keyword, datasetTab, userId);
			return false;
		});
		
	} else {		
		$.getJSON("../dataset/search.do", {keyword: keyword, datasetTab: datasetTab, pageIndex: pageIndex}, function(json) {
			
			$("#dialog_data_list").empty().append($("#tmpl_dialog_data_list").tmpl(json));
	
			$("#dialog_data_list > ul span.dataListChoice").click(function() {
				$("#dialog_data_list > ul span.dataListChoice").removeClass("active");
				$(this).addClass("active");
				
				$("#anadm_data_name_"+selectSheetNum).val($(this).parent().children(".dataListChoice_txt").html().split("<")[0].trim());
				
				var tab = $(this).data("tab");
				if(tab == "STA") {
					var prdDe = $(this).parent("li").find("select#prdDe").val();
					loadSelectedStaDataSample($(this).data("tab"), $(this).data("id"), prdDe);
				} else {
					loadSelectedDataSample($(this).data("tab"), $(this).data("id"));
				}
	
				return false;
			});
			// 페이징
			$("#dialog_data_list .paging a").click(function() {
				searchData($(this).data("page"), keyword, datasetTab);
				return false;
			});
		});
	}
}

// 데이터 선택시(확인 버튼 클릭시 데이터 로드)
function selectedData() {
	// 검색
	if($("#dialog_data_search").is(":visible")) {
		var datasetTab = $("#dialog_data_list > ul span.active").data("tab");
		var dataId = $("#dialog_data_list > ul span.active").data("id");
		// 통계인경우
		var prdDe = $("#dialog_data_list > ul span.active").parent("li").find("select#prdDe").val();

		selectDataset[selectSheetNum] = {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe};
		
		$("#anadm_data_name_"+selectSheetNum).val($("#dialog_data_list > ul span.active").data("title"));
	} else {
		// 파일업로드
		var dataParentId = $("#dialog_data_upload").data("tab");
		var dataId = $("#dialog_data_upload").data("id");
		var dataType = $("#dialog_data_upload").data("type");

		selectDataset[selectSheetNum] = {datasetTab:"UP", dataId:dataId, dataParentId:dataParentId, dataType:dataType};
		
		$("#anadm_data_name_"+selectSheetNum).val($("#dialog_data_upload").data("title"));
	}
	
	return loadSelectedData(1);
}

//선택된 데이터 로드
function loadSelectedData(pageIndex) {	
	var datasetTab = selectDataset[selectSheetNum].datasetTab;
	var dataId = selectDataset[selectSheetNum].dataId;
	var prdDe = selectDataset[selectSheetNum].prdDe;

	// 파일업로드인 경우 hdfs경로 받아와야 함
	// 아닌경우에는 hive
	// 통계데이터는 UP이 아닌경우에만 
	
	// 파일업로드일경우
	if(datasetTab == "UP") {
		var dataParentId = selectDataset[selectSheetNum].dataParentId;
		var dataType = selectDataset[selectSheetNum].dataType;
		
		if(dataId == "") {
			alert("업로드된 데이터가 없습니다.");
			return false;
		}

		$.postJSON("../dataset/rawdataUploaded.do", {datasetTab:dataParentId, dataId:dataId, dataType:dataType}, function(json) {
			sheetData[selectSheetNum] = json.dataList;
			sheetHeader[selectSheetNum] = json.dataList.length > 0 ? json.dataList[0] : [];
			
			setSheetData(sheetData[selectSheetNum]);
			
			
			
			loadSelectBoxSelectedHeader();
		});
	} else {		
		if(dataId == "") {
			alert("데이터를 선택해 주세요.");
			return false;
		}
		
		$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe, limit:100}, function(json) {
		//console.log(json.dataList);
			sheetData[selectSheetNum] = json.dataList;
			sheetHeader[selectSheetNum] = json.dataList.length > 0 ? json.dataList[0] : [];
			
			setSheetData(sheetData[selectSheetNum]);

			// hive 테이블명
			if(selectSheetNum == "a") {
				anadm_table1 = json.htblId;
				
				// 통계선택인 경우 
				if(datasetTab == "STA") {
					anadm_table1_param_req_obj = {};
					anadm_table1_param_req_obj.datasetTab = datasetTab;
					anadm_table1_param_req_obj.dataId = dataId;
					anadm_table1_param_req_obj.prdDe = prdDe;
					
					anadm_table1_param_req_ary.push(anadm_table1_param_req_obj);
					
					hdfsFilePath = json.hdfsFilePath;					
					anadm_table1 = hdfsFilePath.replace(/^[/]+/,"");
				}
			} else {
				anadm_table2 = json.htblId;
				
				if(datasetTab == "STA") {
					anadm_table2_param_req_obj = {};
					anadm_table2_param_req_obj.datasetTab = datasetTab;
					anadm_table2_param_req_obj.dataId = dataId;
					anadm_table2_param_req_obj.prdDe = prdDe;
					
					anadm_table2_param_req_ary.push(anadm_table2_param_req_obj);
					
					hdfsFilePath = json.hdfsFilePath;
					anadm_table2 = hdfsFilePath.replace(/^[/]+/,"");
				}
			}
			
			loadSelectBoxSelectedHeader();

		});	
	}	
}

// 선택한 데이터셋을 SELECT BOX에 표시
function loadSelectBoxSelectedHeader() {
	
	var columns = sheetHeader[selectSheetNum].length > 0 ? sheetHeader[selectSheetNum] : [];
	if(columns != null && columns.length < 0) return false;
	
	var options = "";
	for(var i=0; i < columns.length; i++) {
		options += '<option value="' + $.trim(columns[i]) + '">' + $.trim(columns[i]) + '</option>';
	}
	
	var chosen_config = {};
	chosen_config = {
		'max_selected_options' : 5
	}
	
	if(selectSheetNum == "a") {
		$("#columnA").trigger("reset");
		$("#columnA").append(options).trigger("chosen:update");
		$(".chosen-select1").chosen("destroy").trigger("chosen:updated");
		$(".chosen-select1").chosen(chosen_config).trigger("chosen:updated");
		
		// and 조건
		$("#and_terms_area1 li > select[name=and_terms_column1]").append(options);
		
		// or 조건
		$("#or_terms_area1 li > select[name=or_terms_column1]").append(options);
		
		$("#anadm_merge_key1").append(options);
	} else {
		$("#columnB").trigger("reset");
		$("#columnB").append(options).trigger("chosen:update");
		$(".chosen-select2").chosen("destroy").trigger("chosen:updated");
		$(".chosen-select2").chosen(chosen_config).trigger("chosen:updated");
		
		// and 조건
		$("#and_terms_area2 li > select[name=and_terms_column2]").append(options);
		
		// or 조건
		$("#or_terms_area2 li > select[name=or_terms_column2]").append(options);

		$("#anadm_merge_key2").append(options);
		
	}
	
}

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

//데이터 샘플 불러오기(최대 100건)
function loadSelectedDataSample(datasetTab, dataId) {
	$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, limit: 100}, function(json) {
		dialogSheet.loadData(json.dataList);
	});
}

//통계 데이터 샘플 불러오기
function loadSelectedStaDataSample(datasetTab, dataId, prdDe) {
	$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe}, function(json) {
		dialogSheet.loadData(json.dataList);
	});
}

//결합데이터셋 샘플 불러오기
function loadSelectedMgeDataSample(datasetTab, dataId, userId) {
	$.postJSON("../analysis/analysisDataMergeRawdata.do", {datasetTab:datasetTab, dataId:dataId, userId:userId}, function(json) {
		dialogSheet.loadData(json.dataList);
	});
}

</script>