<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.form.min.js" />" charset="utf-8"></script>

<ul class="tab01 column2 mb30">
	<li><a href="./create.do" class="active">분석하기</a></li>
	<li><a href="./createList.do">분석 리스트</a></li>
</ul>



<!-- <div class="tit_dchoose mb40">
	<a href="" id="btn_data_select" class="btn redline">데이터선택</a>
	<input type="text" name="ana_title" id="ana_title" value="" placeholder="제목을 입력하세요">
</div> -->
<div class="tit_merge bdt_ddd">
	<input type="text" name="ana_title" id="ana_title" value="" placeholder="제목을 입력하세요">
</div>
<div>
	<div class="w100p">
		<table class="tbl02 column2 anaform" summary="데이터분석 등록폼입니다.">
			<caption>데이터분석 등록폼 테이블</caption>
			<tbody>
				<tr>
					<th>데이터명</th>
					<td class="ana_dname">
						<a href="" id="btn_data_select" class="btn redline">데이터선택</a>
						<input type="text" name="" id="ana_data_name" value="" readonly>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>




<ul id="select_atype" class="tab02 column8 mt30">
	<li><a href="#" class="<c:if test="${param.atype eq 'A002' || param.atype eq null || param.atype eq ''}">active</c:if>" data-atype="A002" data-column="변수" data-value="" data-default-value="">빈도분석</a></li>
	<li><a href="#" class="<c:if test="${param.atype eq 'A001'}">active</c:if>" data-atype="A001" data-column="변수" data-value="" data-default-value="">기초통계</a></li>
	<li><a href="#" class="<c:if test="${param.atype eq 'A005'}">active</c:if>" data-atype="A005" data-column="행|열" data-value="" data-default-value="">교차분석</a></li>
	<li><a href="#" class="<c:if test="${param.atype eq 'A006'}">active</c:if>" data-atype="A006" data-column="변수" data-value="" data-default-value="">상관분석</a></li>
	<li><a href="#" class="<c:if test="${param.atype eq 'A004'}">active</c:if>" data-atype="A004" data-column="종속변수|독립변수" data-value="" data-default-value="">회귀분석</a></li>
	<li><a href="#" class="<c:if test="${param.atype eq 'A003'}">active</c:if>" data-atype="A003" data-column="변수" data-value="클러스터 수" data-default-value="3|">군집분석</a></li>
	<li><a href="#" class="<c:if test="${param.atype eq 'A007'}">active</c:if>" data-atype="A007" data-column="행정동코드|변수|행정구역" data-value="" data-default-value="">행정구역</a></li>
	<li><a href="#" class="<c:if test="${param.atype eq 'A008'}">active</c:if>" data-atype="A008" data-column="열" data-value="클러스터 수" data-default-value="3|">GIS</a></li>
</ul>
<div>
	<div class="w100p">
		<table class="tbl02 column2 noline anaform" summary="데이터분석 등록폼입니다.">
			<caption>데이터분석 등록폼 테이블</caption>
			<tbody>
				<tr>
					<!-- <th>설명</th> -->
					<td colspan="2" id="select_atype_comment"></td>
				</tr>
				<tr>
					<th>변수선택</th>
					<td id="select_atype_body">
						<div id="ana_column_1"><span></span><select name="columnA" class="seloption" multiple></select></div>
						<div id="ana_column_2"><span></span><select name="columnB" class="seloption"></select></div>
						<div id="ana_column_3"><span></span><select name="columnC" class="seloption"></select></div>
						<div id="ana_value_1"><span></span><input type="text" name="valueA" class="seloption"/></div>						
					</td>
				</tr>
				<tr>
					<th>AND조건</th>
					<td>
						<ul id="and_terms_area">
							<li>
								<select name="and_terms_column" id="and_terms_column" class="seloption"></select>			
								<select name="and_terms_where" id="and_terms_where">
									<option value=">">&gt;</option>
									<option value=">=">&gt;&#61;</option>
									<option value="<">&lt;</option>
									<option value="<=">&lt;&#61;</option>
									<option value="=">&#61;</option>
									<option value="<>">&lt;&gt;</option>
								</select>
								<input type="text" name="and_terms_input_value" id="and_terms_input_value" value="" placeholder="값을 입력하세요" />
								<button id="and_terms_add_btn" title="조건 추가">+</button>
							</li>
						</ul>
						<ul id="and_terms_list"></ul>
					</td>
				</tr>
				<tr>
					<th>OR조건</th>
					<td>
						<ul id="or_terms_area">
							<li>
								<select name="or_terms_column" id="or_terms_column" class="seloption"></select>
								<select name="or_terms_where" id="or_terms_where">
									<option value=">">&gt;</option>
									<option value=">=">&gt;&#61;</option>
									<option value="<">&lt;</option>
									<option value="<=">&lt;&#61;</option>
									<option value="=">&#61;</option>
									<option value="<>">&lt;&gt;</option>
								</select>
								<input type="text" name="or_terms_input_value" id="or_terms_input_value" value="" placeholder="값을 입력하세요" />
								<button id="or_terms_add_btn" title="조건 추가">+</button>
							</li>
						</ul>
						<ul id="or_terms_list"></ul>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>


<div id="data_sheet">
	<h3><i class="ico dataset"></i><a href="#" data-sheetnum="a" disable>데이터셋</a></h3><!-- a태그 클릭 안되게 해주세요 -->
	<div id="data_sheet_table" class="databox"></div>
</div>

<div class="tac mt40">
	<!-- input type="button" id="ana_run_button" class="btn grey" value="분석하기" />
	<a href="#" class="btn red w80"><i class="ico check"></i>실행</a-->
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
					<input type="hidden" name="target" value="ANALY" />
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

var selectSheetNum = "a",
	selectSheet = null,
	sheetData = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	sheetHeader = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	selectDataset = {a:{}, b:{}},
	dialogSheet = null,
	filterPointer = [],
	filterIsReplace = false;
	
var userId = "${userId}";

// 선택된 컬럼 배열
//var selectedColumnAry = [];
var selectAtypeDataVal = 0;

var hdfsFilePath = "";

var ana_table = "";

// 데이터 분석방법
var ana_data_kind = "";
// 군집분석시 클러스터 갯수
var ana_data_clustering = 0;

var prdDeText = "";
var dataIdVal = 0;

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
	sheetData["a"] = [['DATE','AAA','BB','CC','DD','EE'],
		['2018-01-01',35,39,26,57,96],
		['2018-01-02',14,90,72,49,46],
		['2018-01-03',94,47,28,85,7],
		['2018-01-04',10,93,22,50,66],
		['2018-01-05',98,71,62,49,59],
		['2018-01-06',31,63,80,72,62],
		['2018-01-07',84,9,68,3,7],
		['2018-01-08',75,30,65,18,24],
		['2018-01-09',64,44,60,96,5],
		['2018-01-10',21,82,12,56,61],
		['2018-01-11',1,84,10,72,12],
		['2018-01-12',88,97,16,51,42],
		['2018-01-13',37,71,93,98,34],
		['2018-01-14',30,12,32,77,78],
		['2018-01-15',63,55,60,10,63],
		['2018-01-16',99,46,17,1,52],
		['2018-01-17',5,77,83,19,100],
		['2018-01-18',51,28,88,83,31],
		['2018-01-19',1,27,68,21,49],
		['2018-01-20',88,79,61,48,82],
		['2018-01-21',29,54,89,76,72],
		['2018-01-22',99,32,33,73,74],
		['2018-01-23',83,74,39,7,20],
		['2018-01-24',80,81,78,16,15],
		['2018-01-25',64,71,60,37,19],
		['2018-01-26',54,33,7,80,6],
		['2018-01-27',94,42,91,45,70],
		['2018-01-28',82,46,81,20,5],
		['2018-01-29',60,79,59,7,97],
		['2018-01-30',7,61,29,70,46],
		['2018-01-31',87,26,47,77,4]];
	
	selectSheet.loadData(sheetData["a"]);
	sheetHeader["a"] = sheetData["a"][0];
	*/
	
	//-------------------------------------------------------------------------
	// 데이터 검색

	// 검색 초기화
	$("#search_btn").bind("deselect", function (event) {
 		for(var i = 0; i < filterPointer.length; i++) {
 			[y, x] = filterPointer[i];
 			selectSheet.setCellMeta(y, x, "isSearchResult", false);
 		}
 		selectSheet.render();
	}).bind("reset", function (event) {
		$(this).trigger("deselect");
		$("#search_field").val("");
		$("#replace_field").val("");
	});
		
	// 검색표시
	$("#search_btn").click(function (event) {
		$("#search_btn").trigger("deselect");
		dataFilter(false);
		return false;
	});
	// 검색 후 변경
	$("#replace_btn").click(function (event) {
		$("#search_btn").trigger("deselect");
		dataFilter(true);
		return false;
	});

	//-------------------------------------------------------------------------
	// 데이터 선택
	
	// 데이터 선택 창
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
	
	// 데이터 선택 창 오픈
	$("#btn_data_select").click(function() {
		if($(this).is(".disabled")) return false;
		
        $("#dialog_data_select").dialog("open");
        
    	dialogSheet = new Handsontable($("#dialog_data_detail")[0], {
    		language: "ko-KR",
			rowHeaders: true,
			colHeaders: true,
			readOnly : true,
			fixedRowsTop: 1,
			fixedColumnsLeft:1
    	});

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
		searchData(1, keyword, datasetTab, userId);
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
        		$("#dialog_data_upload").data({tab: json.datasetTab, type: json.dataType, id: json.dataId, name: json.fileName});
        		dialogSheet.loadData(json.dataList);
        		
        		// hdfs upload 파일명			
        		hdfsFilePath = json.hdfsFilePath;
        		ana_table = hdfsFilePath.replace(/^[/]+/,"");
        		
        	} else {
        		alert("데이터를 읽지 못하였습니다.");
        		$("#dialog_data_upload").data({tab: "", type: "", id: "", name: ""});
        		dialogSheet.loadData([]);
        	}        	
        },
        error: function(error){
        	alert("데이터 전송 중 오류가 발생하였습니다.");
    		$("#dialog_data_upload").data({tab: "", type: "", id: "", name: ""});
    		dialogSheet.loadData([]);
        }
    });	
		
	
	
	//-------------------------------------------------------------------------
	// 데이터 분석
			
	// 데이터 분석 기법 선택	
	$("#select_atype > li > a").click(function() {
		$("#select_atype > li > a").removeClass("active");
		$(this).addClass("active");
		$("#select_atype").trigger("reset");
		selectAtypeDataVal = $(this).data("atype");

		var columnLabel = $(this).data("column").split("|");
		var valueLabel = $(this).data("value").split("|");
		var defaultValue = $(this).data("default-value").split("|");
		
	
		if(columnLabel[0] != "") {
			for(var k in columnLabel) {
				$("#ana_column_" + (+k+1)).show().addClass("visible").children("span").text(columnLabel[k]);
			}
		}

		if(valueLabel[0] != "") {
			for(var k in valueLabel) {
				$("#ana_value_" + (+k+1)).show().addClass("visible").children("span").text(valueLabel[k]);
			}
		}		

		if(defaultValue[0] != "") {
			for(var k in defaultValue) {
				$("#ana_value_" + (+k+1)).children("input[type=text]").val(defaultValue[k]);
			}
		}
		
		
		$("#select_atype_body [type='button']").val("분석하기").show();

		var columns = sheetHeader[selectSheetNum].length > 0 ? sheetHeader[selectSheetNum] : [];
		if(columns != null && columns.length < 0) return false;
		
		var options = "";
		for(var i=0; i < columns.length; i++) {
			options += '<option value="' + i + '">' + $.trim(columns[i]) + '</option>';
		}
		$("#select_atype_body div.visible select[name^=column]").append(options).trigger("chosen:updated");		
		
		// A001기초 : 5개 선택
		// A002빈도 : 5개 선택
		// A003군집 : 2개 선택
		// A004회귀 : 1개 선택, VALUE 1개 선택
		// A005교차 : 1개 선택, VALUE 1개 선택
		// A006상관 : 5개 선택
		// A007PNU : 1개 선택, VALUE 1개 선택
		// A008PNU : 2개 선택, VALUE 1개 선택
		var max_selected_options = 0;
		var chosen_config = {};
		if( $(this).data("atype") == "A001" ) {
			max_selected_options = 5;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			
			$("#select_atype_comment").html("변수의 평균, 표준편차, 최소값, 최대값, 사분위수 등의 기초통계 분석할 수 있습니다. <em>&#40;수치형 변수로 최대 "+max_selected_options+"개까지 선택가능합니다.&#41;</em>");
		} else if($(this).data("atype") == "A002") {
			max_selected_options = 5;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			
			$("#select_atype_comment").html("변수의 빈출 경향의 정도를 빈도와 백분율로 분석할 수 있습니다. <em>&#40;수치형 또는 범주형 변수로 최대 "+max_selected_options+"개까지 선택가능합니다.&#41;</em>");
		} else if($(this).data("atype") == "A003") {
			max_selected_options = 2;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			
			$("#select_atype_comment").html("변수의 유사성을 측정하여 유사성이 높은 K개의 대상집단을 분류하고 군집간의 상이성을 분석할 수 있습니다. <em>&#40;수치형 변수로 최대 "+max_selected_options+"개까지 선택가능합니다.&#41;</em>");
		} else if($(this).data("atype") == "A004") {
			max_selected_options = 1;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			$("#select_atype_comment").html("종속변수와 독립변수의 선형상관관계를 모델링하여 독립변수의 종속변수에 대한 영향을 분석할 수 있습니다. <em>&#40;두개의 수치형 변수로 최대 "+max_selected_options+"개까지 선택가능합니다.&#41;</em>");
		} else if($(this).data("atype") == "A005") {
			max_selected_options = 1;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			$("#select_atype_comment").html("변수의 행과 열의 중복된 빈도를 교차표를 통해 분석할 수 있습니다. <em>&#40;두개의 수치형 또는 범주형 변수로 최대 "+max_selected_options+"개까지 선택가능합니다.&#41;</em>");
		} else if($(this).data("atype") == "A006") {
			max_selected_options = 5;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			$("#select_atype_comment").html("변수간의 선형적 관계가 있는지를 분석할 수 있습니다. <em>&#40;하나 이상의 수치형 변수로 최대 "+max_selected_options+"개까지 선택가능합니다.&#41;</em>");
		} else if($(this).data("atype") == "A007") {
			max_selected_options = 1;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			// columnC에 행정구역값 셋팅
			$("select[name=columnC]").empty();
			var columnCTextAry = new Array("시별","구군별","읍면동");
			$.each(columnCTextAry, function(i, v){
				$("select[name=columnC]").append("<option value=\""+v+"\">"+v+"</option>").trigger("chosen:updated");
			});
			$("#select_atype_comment").html("행정동 코드를 기준으로 변수값을 합으로 분석할수 있습니다 <em>&#40;"+max_selected_options+"개의 행정동코드와 "+max_selected_options+"개의 변수값 선택이 가능합니다.&#41;</em>");
		} else if($(this).data("atype") == "A008") {
			max_selected_options = 2;
			chosen_config = {
				'max_selected_options' : max_selected_options
			}
			$("#select_atype_comment").html("위도 경도 좌표를 기준으로 변수의 유사성을 측정하여 유사성이 높은 K개의 대상집단을 분류하고 군집간의 상이성을 분석할 수 있습니다. <em>&#40;"+max_selected_options+"개의 좌표값 선택이 가능합니다.&#41;</em>");
		}
		
		$(".chosen").chosen("destroy").trigger("chosen:updated");
		$(".chosen").chosen(chosen_config).trigger("chosen:updated");

		// and 조건
		$("#and_terms_area li > select[name=and_terms_column]").append(options).trigger("chosen:updated");
		
		// or 조건
		$("#or_terms_area li > select[name=or_terms_column]").append(options).trigger("chosen:updated");
		
		// 데이터 분석방법 값
		ana_data_kind = $(this).data("atype");
		
		return false;
	});
	
	
	// and 조건 추가
	$("#and_terms_add_btn").click(function() {
		if($("#and_terms_column option:selected").text() == "") {
			alert('선택된 열이 없습니다.');
		} else if($("#and_terms_input_value").val() == "") {
			alert('조건값을 입력하세요!');
			$("#and_terms_input_value").focus();
		} else {
			var col = $("#and_terms_column option:selected").text();
			var where = $("#and_terms_where").val();
			var value = $("#and_terms_input_value").val();
			var condition = col + " " + where +" " +value;
			$("#and_terms_list").css("display","block").append("<li><span class=\"and_text\">"+condition+"</span><a href=\"#\" class=\"and_text_del\"></a></li>");
		}
		return false;
	});
	
	// or 조건 추가
	$("#or_terms_add_btn").click(function() {
		if($("#or_terms_column option:selected").text() == "") {
			alert('선택된 열이 없습니다.');
		} else if($("#or_terms_input_value").val() == "") {
			alert('조건값을 입력하세요!');
			$("#or_terms_input_value").focus();
		} else {
			var col = $("#or_terms_column option:selected").text();
			var where = $("#or_terms_where").val();
			var value = $("#or_terms_input_value").val();
			var condition = col + " " + where + " " + value;
			$("#or_terms_list").css("display","block").append("<li><span class=\"or_text\">"+condition+"</span><a href=\"#\" class=\"or_text_del\"></a></li>");
		}
		return false;
	});

	// 리셋
	$("#select_atype").bind("reset", function() {
		$("#select_atype_body > div").hide().removeClass("visible");
		$("#select_atype_body select[name^=column]").empty();
		$("#select_atype_body input[name^=value]").val("");
		$("#select_atype_body [type='button']").hide();
		
		// and, or selectbox reset
		$("#and_terms_area select[name=and_terms_column]").empty();
		$("#or_terms_area select[name=or_terms_column]").empty();
		
		// and, or 조건식 reset
		$("#and_terms_list").empty();
		$("#or_terms_list").empty();
	});   
	

	$("#select_atype_body select[name^=column]").chosen({
		placeholder_text : "컬럼을 선택하세요.",
	   	no_results_text : "입력하신 컬럼을 찾을 수 없습니다."
	}).addClass("chosen");
	
	//-------------------------------------------------------------------------
	// 데이터셋 탭 선택	
	$("#select_atype").trigger("reset");
	$("#select_atype > li > a.active").trigger("click");
	selectAtypeDataVal = $("#select_atype > li > a.active").data("atype");
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

	// 분석저장
	$(document).on("click", "#ana_run_button", function() {
		
		var and_length = $(".and_text").length;
		var or_length = $(".or_text").length;
		
		var column_list_ary = [];
		var and_terms_list_ary = [];
		var or_terms_list_ary = [];

		$("#select_atype_body select[name='columnA'] option:selected").each(function() {
			column_list_ary.push($(this).text());
		});
		// 선택한 컬럼들 문자열로 변환
		// A004,A005,A007인것은 columnA, columnB 컬럼 합치기
		var atype = "";
		$("#select_atype > li > a").each(function(i) {			
			if($(this).hasClass("active") == true) {
				atype = $(this).data("atype");
				return false;
			}
		});
		
		if(atype == "A004" || atype == "A005" || atype == "A007") {
			$("#select_atype_body select[name='columnB'] option:selected").each(function() {
				column_list_ary.push($(this).text());
			});
		}
		
		var columnData = column_list_ary.join(",");
		

		for(var i=0; i < and_length; i++) {
			var and_terms_list = {};
			var and_terms_list_temp = $(".and_text:eq("+i+")").text();			
			
			and_terms_list_temp = and_terms_list_temp.replace(/ > /g, "&&>&&");
			and_terms_list_temp = and_terms_list_temp.replace(/ >= /g, "&&>=&&");
			and_terms_list_temp = and_terms_list_temp.replace(/ < /g, "&&<&&");
			and_terms_list_temp = and_terms_list_temp.replace(/ <= /g, "&&<=&&");
			and_terms_list_temp = and_terms_list_temp.replace(/ = /g, "&&=&&");
			and_terms_list_temp = and_terms_list_temp.replace(/ <> /g, "&&<>&&");
			
			and_terms_list.text = and_terms_list_temp; 
			and_terms_list_ary.push(and_terms_list);
		}		
		
		for(var i=0; i < or_length; i++) {
			var or_terms_list = {};
			var or_terms_list_temp = $(".or_text:eq("+i+")").text();
			
			or_terms_list_temp = or_terms_list_temp.replace(/ > /g, "&&>&&");
			or_terms_list_temp = or_terms_list_temp.replace(/ >= /g, "&&>=&&");
			or_terms_list_temp = or_terms_list_temp.replace(/ < /g, "&&<&&");
			or_terms_list_temp = or_terms_list_temp.replace(/ <= /g, "&&<=&&");
			or_terms_list_temp = or_terms_list_temp.replace(/ = /g, "&&=&&");
			or_terms_list_temp = or_terms_list_temp.replace(/ <> /g, "&&<>&&");
			
			or_terms_list.text = or_terms_list_temp;
			or_terms_list_ary.push(or_terms_list);
		}
		

		// hive 테이블명 또는 hdfs 파일경로
		// ana_table
		
		// UP(hdfs), BDS, STA, PRI(hive)
		var datasetTab = selectDataset[selectSheetNum].datasetTab;
		// UP : U, HIVE : H
		var ana_table_stat = get_ana_table_stat(datasetTab);

		//console.log("datasetTab : " + datasetTab);
		//console.log("ana_table_sta : " + ana_table_stat);
		var ana_title = $("#ana_title").val();
		if(ana_title == "") {
			alert('분석 제목을 입력하세요.');
			$("#ana_title").focus();
			
			return false;
		}
		
		var column_list= JSON.stringify(column_list_ary);
		var and_terms_json = JSON.stringify(and_terms_list_ary);
		var or_terms_json = JSON.stringify(or_terms_list_ary);
		
		// 통계 데이터 param
		var ana_sta_param_req_ary = [];
		var ana_sta_param_req = {};
		ana_sta_param_req.datasetTab = datasetTab;
		ana_sta_param_req.dataId = dataIdVal;
		ana_sta_param_req.prdDe = prdDeText;
		ana_sta_param_req_ary.push(ana_sta_param_req);

		var ana_sta_param_req_json = JSON.stringify(ana_sta_param_req_ary);
		
		if(datasetTab != "STA") {
			ana_sta_param_req_json = "";
		}
		
		var ana_pnu_kind = atype == "A007" ? $("select[name=columnC] option:selected").val() : "";
		
		// 군집분석일 경우 클러스터 수
		ana_data_clustering = atype == "A003" || atype == "A008" ? $("#select_atype_body div.visible input[name=valueA]").val() : ana_data_clustering;
		
		if(columnData == "") {
			alert("변수를 선택하세요.");
			return false;
		}
		if(and_terms_list_ary.length <= 0 && or_terms_list_ary.length <= 0) {
			alert("AND조건 또는 OR조건 둘중 하나를 추가하세요.");
			return false;
		}
		var save_ok = false;
		save_ok = confirm("현재 선택한 값으로 분석을 저장하시겠습니까?");
		if(!save_ok) {
			return false;
		}
		
		
		var url = "./createDataInsert.do";
		var param = {
			"anaTitle" : ana_title,
			"anaTable" : ana_table,
			"anaTableStat" : ana_table_stat,
			"anaDataColumn" : columnData,
			"anaAndTerms" : and_terms_json,
			"anaOrTerms" : or_terms_json,
			"anaDataKind" : ana_data_kind,
			"anaDataClustering" : ana_data_clustering,
			"anaStaParamReq" : ana_sta_param_req_json,
			"anaPnuKind" : ana_pnu_kind
		}

		$.ajax({
			url : url,
			type : "POST",
			data : param,
			dataType : "json",
			success : function(data) {
				if(data.result == "OK") {
					// redirect
					location.href="/bdp/analysis/createList.do";
				} else if(data.result == "FAIL") {
					alert('데이터를 저장하지 못했습니다. \r\n값을 다시 설정하여 실행하세요');
				}
			}, error : function() {
				alert('데이터 전송시 오류가 발생하였습니다. 다시 시도하여 주세요!');
			}
		});
	});
	
	
	// 데이터 결합뷰에서 분석하기 이동시
	if("${init}" == "MGE") {
		$.ajax({
			url : "./analysisDataMergeRawdata.do",
			data : {datasetTab:"${init}", dataId:"${id}", userId:userId, limit:10000},
			type : "POST",
			dataType : "JSON",
			async : false,
			timeout : 10000,
			success : function(json) {
				
				sheetData[selectSheetNum] = json.dataList;
				sheetHeader[selectSheetNum] = json.dataList.length > 0 ? json.dataList[0] : [];
								
				selectSheet.loadData[sheetData["a"]];				
				sheetHeader["a"] = sheetData["a"][0];
				
				setSheetData(sheetData[selectSheetNum]);
				
				hdfsFilePath = json.hdfsFilePath;
				// "/" 첫번째 문자 제거
				ana_table = hdfsFilePath.replace(/^[/]+/,"");
				
				$("#ana_title").val(json.anadmTitle);
				
				selectDataset[selectSheetNum].datasetTab = "${init}";
				
			}, error : function() {
				alert('데이터롤 불러오는중 시간초과로 실패하였습니다.');
			}
		});
	}
});

// 시트에 데이터 세팅
function setSheetData(data) {
	if(data == null || data.length <= 0 || data[0] == null || data[0].length <= 0) {
		data = null;
	}

	selectSheet.loadData(data);
	loadSelectedDataAfter();
	
	// 선택한 값들 reset
	//$("#select_atype").trigger("reset");
	$("#select_atype > li").find(".active").trigger("click");
}

// 데이터 불러오기 창 리셋 
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
function searchData(pageIndex, keyword, datasetTab, userId) {
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
				$("#ana_data_name").val($(this).parent().children(".dataListChoice_txt").text());
				
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
				$("#ana_data_name").val($(this).parent().children(".dataListChoice_txt").text());
				
				if(datasetTab == "STA") {
					var prdDe = $(this).parent("li").find("select#prdDe").val();
					loadSelectedStaDataSample($(this).data("tab"), $(this).data("id"), prdDe);
				} else {
					loadSelectedDataSample($(this).data("tab"), $(this).data("id"));
				}
	
				return false;
			});
			// 페이징
			$("#dialog_data_list .paging a").click(function() {
				searchData($(this).data("page"), keyword, datasetTab, userId);
				return false;
			});
		});
	}
}

// 데이터 선택시(확인 버튼 클릭시 데이터 로드)
function selectedData() {
	if($("#dialog_data_search").is(":visible")) {
		var datasetTab = $("#dialog_data_list > ul span.active").data("tab");
		var dataId = $("#dialog_data_list > ul span.active").data("id");
		// 통계인경우
		var prdDe = $("#dialog_data_list > ul span.active").parent("li").find("select#prdDe").val();
		prdDeText = prdDe;
		dataIdVal = dataId;

		selectDataset[selectSheetNum] = {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe};
		
		$("#ana_data_name").val($("#dialog_data_list > ul span.active").data("title"));
	} else {		
		var dataParentId = $("#dialog_data_upload").data("tab");
		var dataId = $("#dialog_data_upload").data("id");
		var dataType = $("#dialog_data_upload").data("type");
		var title = $("#dialog_data_upload").data("title");

		selectDataset[selectSheetNum] = {datasetTab:"UP", dataId:dataId, dataParentId:dataParentId, dataType:dataType};
		
		$("#ana_data_name").val($("#dialog_data_upload").data("title"));
	}
	
	return loadSelectedData(1);
}
// 선택된 데이터 로드
function loadSelectedData(pageIndex) {	
	var datasetTab = selectDataset[selectSheetNum].datasetTab;
	var dataId = selectDataset[selectSheetNum].dataId;
	var prdDe = selectDataset[selectSheetNum].prdDe;

	// 파일업로드인 경우 hdfs경로 받아와야 함
	// 아닌경우에는 hive
	
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
			
		});
	} else {		
		if(dataId == "") {
			alert("데이터를 선택해 주세요.");
			return false;
		}
		
		if(datasetTab == "MGE") {
			$.ajax({
				url : "./analysisDataMergeRawdata.do",
				data : {datasetTab:datasetTab, dataId:dataId, userId:userId, limit:10000},
				type : "POST",
				dataType : "JSON",
				async : false,
				timeout : 10000,
				success : function(json) {
					
					sheetData[selectSheetNum] = json.dataList;
					sheetHeader[selectSheetNum] = json.dataList.length > 0 ? json.dataList[0] : [];
									
					setSheetData(sheetData[selectSheetNum]);
					
					hdfsFilePath = json.hdfsFilePath;
					// "/" 첫번째 문자 제거
					ana_table = hdfsFilePath.replace(/^[/]+/,"");
					
					
				}, error : function() {
					alert('데이터롤 불러오는중 시간초과로 실패하였습니다.');
				}
			});
		} else {			
			$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe, limit:100}, function(json) {
			//console.log(json.dataList);
				sheetData[selectSheetNum] = json.dataList;
				sheetHeader[selectSheetNum] = json.dataList.length > 0 ? json.dataList[0] : [];
				
				setSheetData(sheetData[selectSheetNum]);
	
				if(datasetTab == "STA") {
					hdfsFilePath = json.hdfsFilePath;					
					ana_table = hdfsFilePath.replace(/^[/]+/,"");
				} else {
					// hive 테이블명
					ana_table = json.htblId;
				}
			});	
		}
	}
}

// 데이터 로드 후 컬럼 사이즈 맞춤
// 자동맞춤일경우 랜더링시간이 많이 걸려 최초 데이터 로드시에만 실행하도록 변경
function loadSelectedDataAfter() {
	var plugin = selectSheet.getPlugin('autoColumnSize');
	plugin.calculateColumnsWidth(void 0, void 0, true);
	var colWidths = [];
	for (var i = 0;i < selectSheet.countCols(); i++) {
		colWidths.push(plugin.getColumnWidth(i));
	}
	selectSheet.updateSettings({colWidths: colWidths});
}

// 데이터 샘플 불러오기(최대 100건)
function loadSelectedDataSample(datasetTab, dataId) {
	$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, limit: 100}, function(json) {
		dialogSheet.loadData(json.dataList);
	});
}

// 통계 데이터 샘플 불러오기
function loadSelectedStaDataSample(datasetTab, dataId, prdDe) {
	$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe}, function(json) {
		dialogSheet.loadData(json.dataList);
	});
}

// 결합데이터셋 샘플 불러오기
function loadSelectedMgeDataSample(datasetTab, dataId, userId) {
	
	$.ajax({
		url : "../analysis/analysisDataMergeRawdata.do",
		data : {datasetTab:datasetTab, dataId:dataId, userId:userId},
		type : "POST",
		dataType : "JSON",
		async : false,
		timeout : 10000,
		success : function(json) {
			dialogSheet.loadData(json.dataList);
		}, error : function() {
			alert('데이터롤 불러오는중 시간초과로 실패하였습니다.');
		}
	});
	
	/*
	$.postJSON("../analysis/analysisDataMergeRawdata.do", {datasetTab:datasetTab, dataId:dataId, userId:userId}, function(json) { 
		dialogSheet.loadData(json.dataList);
	});
	*/
	
}

</script>