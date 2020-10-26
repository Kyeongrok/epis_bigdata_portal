
//// 차트 정렬
/*
AmCharts.addInitHandler( function( chart ) {
  // Check if `orderByField` is set
  if ( chart.orderByField === undefined ) {
    // Nope - do nothing
    return;
  }

  // Re-order the data provider
  chart.dataProvider.sort( function( a, b ) {
    if ( a[ chart.orderByField ] > b[ chart.orderByField ] ) {
      return 1;
    } else if ( a[ chart.orderByField ] == b[ chart.orderByField ] ) {
      return 0;
    }
    return -1;
  } );

}, [ "serial" ] );
*/


// 선택된 데이터만 정리
function visDataPivot(dataSheet, cols) {
	// 컬럼 중복 제거
	cols = cols.filter(function(value, index, self) {
	    return self.indexOf(value) === index;
	});
	
	var data = [];
	dataSheet.forEach(function(row, i) {
		if(i == 0) return;
		
		var newRow = {};
		cols.forEach(function(d) {
			newRow["col" + d] = row[d];
		});
		
		data.push(newRow);
	});

	return data;
}

// 
function visDataXYPivot(dataSheet, xColumn, yColumn, zColumn) {
	var indexArr = [];
	var cateArr = [];

	var data = [];
	dataSheet.forEach(function(d, i) {
		if(i == 0) return;
		
		var x = d[xColumn];
		var y = d[yColumn];
		var z = zColumn ? d[zColumn] : dataSheet[0][yColumn];
		
		var row = $.inArray(x, indexArr);
		if(row < 0) {
			indexArr.push(x);
			row = indexArr.length - 1;
			
			data.push({"x" : x});
		}
		
		var col = $.inArray(z, cateArr);
		if(col < 0) {
			cateArr.push(z);
			col = cateArr.length - 1;
		}
		data[row]["y" + col] = y;
	});
		
	return [cateArr, data];
}

function visStringFirstUpCase(c) {
	return c[0].toUpperCase() + c.slice(1);
}

function visStringFirstUpCase(c) {
	return c[0].toUpperCase() + c.slice(1);
}

///////////////////////////////////////////////////////////////////////////////
// 차트 개별 옵션
function getAmChartBasicOptions(userOptions) {
	var options = {
	    "theme": userOptions.theme,
	    "autoMargins" : true,
	    "autoMarginOffset": 20,
	    //"mouseWheelZoomEnabled":true,
	    "balloon": {
	        "borderThickness": 1,
	        "shadowAlpha": 0
	    },		
		"chartCursor": {
			//"pan": true,
			"zoomable":false,
	        "valueLineEnabled": true,
	        "oneBalloonOnly":true,
	        "valueLineBalloonEnabled": true,
	        "cursorAlpha":0.2,
	        "cursorColor":"#000000",
	        "valueLineAlpha":0.2,
	        "graphBulletAlpha" : 1,
	        "graphBulletSize" : 1.7,
	        "bulletsEnabled":false,
	        
		},
		"balloon": {
			"fixedPosition":true,
			"fillAlpha" : 0.8,
			//"fillColor" : "#3b475a",
			"borderAlpha": 1,
			//"adjustBorderColor": false,
			"horizontalPadding": 5,
			"verticalPadding": 5,
			"color": "#666666",
			"shadowAlpha": 0.2
		},
		"fontFamily" : "Nanum Gothic",
		"fontSize" : 12,
		"color": "#666666",
		"export": {
			"enabled": true,
			"menu" : [{
				"class" : "export-main",
				"label" : "Export",
				"menu" : [ {
					"label" : "그래프다운로드",
					"menu" : [ "PNG", "JPG", "SVG", "PDF" ]
				}, {
					"label" : "데이터다운로드",
					"menu" : [ "CSV", "XLSX", "JSON" ]
				}]
			}]		
		}
	};
	
	if(userOptions.theme !== "patterns" && userOptions.colors != null && userOptions.colors.length > 0) {
		options.colors = userOptions.colors;
	}
			 
	return options;
}

function getAmChartSimpleScrollOptions(chartOptions, userOptions) {
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar = {
			"scrollbarHeight": 5
		};
	}
	return chartOptions;
}

function getAmChartScrollOptions(chartOptions, userOptions) {
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar = {
	        "oppositeAxis":false,
	        "offset":70,
	        "scrollbarHeight": 50,
	        "autoGridCount": true
	    };
	}
	
	if(userOptions.valueScrollbar.display == true) {
		chartOptions.valueScrollbar = {
		      "oppositeAxis":false,
		      "offset": userOptions.valueScrollbar.offset,
		      "scrollbarHeight":10
	    };
	}	
	
	return chartOptions;
}

function getAmChartValueAxisOption(chartOptions) {
}

function getAmChartValueAxesOption(chartOptions, userOptions) {
	// 현재 사용자 설정을 3개까지만 지원하도록 해놓음
	var valueAxes = [];
	var left = 0;
	var right = 0;
	
	userOptions.valueAxes.forEach(function(d, i) {
		if(i == 0 || $.trim(d.title) != "") {
			var axis = {
		        "id": "AmAxis-" + i,
		        "axisAlpha": 1,
		        "axisThickness": 1,
				"axisColor": "#aaaaaa",
		        "position": d.position,
		        "synchronizeGrid":true,
		        "title" : d.title,
		        "rotation": d.position == "left" ? 270 : 90,
		        "stackType" : userOptions.graph.stack == true ? "regular" : "none",
		        "offset" : +d.offset,
		        "minVerticalGap" : 50,
		        "ignoreAxisWidth" : true
		    };
			
			//if(d.position == "left" && left > 0) axis.offset = left * 80;
			//if(d.position == "right" && right > 0) axis.offset = right * 80;
			
			left += (d.position == "left" ? 1 : 0);
			right += (d.position == "right" ? 1 : 0);
			
			valueAxes.push(axis);
		}
	});

	chartOptions.valueAxes = valueAxes;
	return chartOptions;
}

function getAmChartXYValueAxesOption(chartOptions, userOptions) {
	var xAxis = {
		"id": "ValueAxis-0",
		"position": "bottom",
		"axisAlpha": 0,
		"gridPosition": "start",
		"dateFormats" : [
			{"period":"fff","format":"JJ:NN:SS"},
			{"period":"ss","format":"JJ:NN:SS"},
    		{"period":"mm","format":"JJ:NN"},
    		{"period":"hh","format":"JJ:NN"},
    		{"period":"DD","format":"MM.DD"},
    		{"period":"WW","format":"MM.DD"},
    		{"period":"MM","format":"YY.M월"},
    		{"period":"YYYY","format":"YYYY"}]
	};
	var yAxis = {
		"id": "ValueAxis-1",
		"axisAlpha": 0,
	    "minMaxMultiplier": 1.2,
		"position": "left"
	};

	if(userOptions.categoryAxis.parseDates === true) {
		xAxis.type = "date";
		xAxis.dashLength = 1;
		xAxis.minorGridEnabled = true;
		xAxis.minPeriod = userOptions.categoryAxis.minPeriod;
		
		chartOptions.dataDateFormat = userOptions.dataDateFormat;
		
		if($.trim(userOptions.xy.xDateMin) != "") {
			xAxis.minimumDate = new Date(Date.parse(userOptions.xy.xDateMin));
		}
		if($.trim(userOptions.xy.xDateMax) != "") {
			xAxis.maximumDate = new Date(Date.parse(userOptions.xy.xDateMax));
		}
		// 안먹힘
		//chartOptions.chartCursor.categoryBalloonDateFormat = userOptions.chartCursor.categoryBalloonDateFormat;
	} else {
		if(userOptions.xy.xMin != null)
			xAxis.minimum = +userOptions.xy.xMin;
		if(userOptions.xy.xMax != null)
			xAxis.maximum = +userOptions.xy.xMax;
	}
	
	if(userOptions.xy.yMin != null)
		yAxis.minimum = +userOptions.xy.yMin;
	if(userOptions.xy.yMax != null)
		yAxis.maximum = +userOptions.xy.yMax;
	
	if($.trim(userOptions.categoryAxis.title) != "") {
		xAxis.title = userOptions.categoryAxis.title;
	}
	
	if($.trim(userOptions.valueAxes[0].title) != "") {
		yAxis.title = userOptions.valueAxes[0].title;
	}
	
	chartOptions.valueAxes = [xAxis, yAxis];
	
	return chartOptions;
}

function getAmChartCategoryAxisOption(chartOptions, userOptions) {
	var categoryAxis = {
		"title" : userOptions.categoryAxis.title,
		"labelRotation" : +userOptions.categoryAxis.labelRotation,
		"labelOffset" : Math.abs(+userOptions.categoryAxis.labelRotation) > 30 ? 0 : -5,
		"axisAlpha": 1,
        "axisThickness": 1,
		"gridAlpha": 0,
		"axisColor": "#aaaaaa",
		"gridPosition": "start",
		"dateFormats" : [
			{"period":"fff","format":"JJ:NN:SS"},
			{"period":"ss","format":"JJ:NN:SS"},
    		{"period":"mm","format":"JJ:NN"},
    		{"period":"hh","format":"JJ:NN"},
    		{"period":"DD","format":"MM.DD"},
    		{"period":"WW","format":"MM.DD"},
    		{"period":"MM","format":"YY.M월"},
    		{"period":"YYYY","format":"YYYY"}]
	};
	
	if(userOptions.categoryAxis.parseDates === true) {
		categoryAxis.parseDates = true;
		categoryAxis.dashLength = 1;
		categoryAxis.minorGridEnabled = true;
		categoryAxis.minPeriod = userOptions.categoryAxis.minPeriod;
		//categoryAxis.equalSpacing = true;
		
		chartOptions.dataDateFormat = userOptions.dataDateFormat;
		chartOptions.chartCursor.categoryBalloonDateFormat = userOptions.chartCursor.categoryBalloonDateFormat;
		
		chartOptions.orderByField = chartOptions.categoryField;
	}
	if($.trim(userOptions.categoryAxis.title) != "") {
		categoryAxis.title = userOptions.categoryAxis.title;
	}
	
	chartOptions.categoryAxis = categoryAxis;
	
	return chartOptions;
}


function getAmChartLegendOption(chartOptions, userOptions) {
	if(userOptions.regend.display == true) {
		// LEGEND
		var legend = {
			"position" : userOptions.regend.position,
			"spacing" : 25,
			"valueWidth" : "auto",
			"equalWidths": false,
			"valueAlign": "center",
			"align": "center",
			"switchType" : "none",
			"useGraphSettings" : true
			//"autoMargins": true
		};
		
		if(legend.position == "right") legend.marginRight = 100;
		if(legend.position == "left") legend.marginLeft = 100;
		
		chartOptions.legend = legend;
	}
	
	return chartOptions;
}

function getAmChartPieLegendOption(chartOptions, userOptions) {
	if(userOptions.regend.display == true) {
		// LEGEND
		var legend = {
			"position" : userOptions.regend.position,
			"spacing" : 25,
			"valueWidth" : "auto",
			"equalWidths": false,
			"valueAlign": "center",
			"align": "center",
			"switchType" : "none"
			//"autoMargins": true
		};
		
		if(legend.position == "right") legend.marginRight = 100;
		if(legend.position == "left") legend.marginLeft = 100;
		
		chartOptions.legend = legend;
	}
	
	return chartOptions;
}


function getAmChartBalloonStyle() {
	return {
		"textAlign" : "left",
		"fontSize" : 15,
		"horizontalPadding": 20,
		"verticalPadding": 10
	};
}


function getAmChartBulletType(type, index, colors) {
	var colorLen = colors == null ? 10 : colors.length;
	var bullets = ["round", "square", "triangleUp", "triangleDown", "diamond"];
	switch(type) {
		case "mix" : 
			return bullets[index % bullets.length];
		case "order" : 
			return bullets[Math.floor(index / colorLen) % bullets.length];
		default : return type;
	}
}

///////////////////////////////////////////////////////////////////////////////
// 차트 생성

// 라인차트
function visLineChart(dataSheet, userOptions) {
	var chartId = userOptions._chartId;
	var xColumns = userOptions._columns.x;
	var yColumns = userOptions._columns.y;
	
	var xLabels = userOptions._labels.x;
	var yLabels = userOptions._labels.y;
	
	var colAll = [];
	colAll = colAll.concat(xColumns);
	colAll = colAll.concat(yColumns);	
	
	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);

	var chartOptions = getAmChartBasicOptions(userOptions);
	chartOptions.dataProvider = dataProvider;
		
	// Y축
	chartOptions = getAmChartValueAxesOption(chartOptions, userOptions);
	
	// CHART LINE
	var graphs = [];
	yColumns.forEach(function(d, i) {
		var graph = {
	        "id": "AmGraph-" + d,
	        "balloon": getAmChartBalloonStyle(),
	        "type" : userOptions.graph.type,
	        "bullet": getAmChartBulletType(userOptions.graph.bullet, i, chartOptions.colors),
	        "bulletBorderAlpha": userOptions.graph.bulletSize == 0 ? 0 : 1,
	        "bulletColor": "#FFFFFF",
	        "bulletSize": userOptions.graph.bulletSize,
	        "bulletAlpha": userOptions.graph.bulletSize == 0 ? 0 : 1,
	        "hideBulletsCount": 50,
	        "lineThickness": userOptions.graph.lineThickness,
	        "title": yLabels[i],
	        "useLineColorForBulletBorder": true,
	        "valueField": "col" + d,
	        "valueAxis" : "AmAxis-0",
	        "balloonText": "[[title]]<br />[[category]] : <b>[[value]]</b>"
		};
		
		for(var ix = 1; ix < userOptions.valueAxes.length; ix++) {			
			var tar = userOptions.valueAxes[ix].graph;
			if($.inArray(d+"", tar) > -1) {
				graph.valueAxis = "AmAxis-" + ix;
			}
		}
		
		if(userOptions.graph.displayLabelText == true) {
			graph.labelText = "[[value]]";
		}
		
		graphs.push(graph);
	});
	chartOptions.graphs = graphs;
	
	// X 데이터
	chartOptions.categoryField = "col" + xColumns[0];

	// X축
	chartOptions = getAmChartCategoryAxisOption(chartOptions, userOptions);
	
	// 차트 스크롤바
	chartOptions = getAmChartScrollOptions(chartOptions, userOptions);
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar.graph = "AmGraph-" + yColumns[0];
	}
	
	//--------------------------------------
	// BASIC OPTION SET
	
	chartOptions.type = "serial";
	chartOptions.titles = [{"text": userOptions.title, "size":20}];
	chartOptions.marginRight = +userOptions.marginRight;
	chartOptions.marginLeft = +userOptions.marginLeft;
	chartOptions.marginTop = +userOptions.marginTop;
	chartOptions.marginBottom = +userOptions.marginBottom;
	
	// LEGEND
	chartOptions = getAmChartLegendOption(chartOptions, userOptions);

	//--------------------------------------
	// CHART DRAW
	var chart = AmCharts.makeChart(chartId, chartOptions);
}

// 바차트
function visBarChart(dataSheet, userOptions) {
	var chartId = userOptions._chartId;
	var xColumns = userOptions._columns.x;
	var yColumns = userOptions._columns.y;
	
	var xLabels = userOptions._labels.x;
	var yLabels = userOptions._labels.y;
	
	var colAll = [];
	colAll = colAll.concat(xColumns);
	colAll = colAll.concat(yColumns);	
	
	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);

	var chartOptions = getAmChartBasicOptions(userOptions);
	chartOptions.dataProvider = dataProvider;
		
	// Y축
	chartOptions = getAmChartValueAxesOption(chartOptions, userOptions);
	
	// CHART LINE
	var graphs = [];
	yColumns.forEach(function(d, i) {
		var graph = {		
			"id": "AmGraph-" + d,
			"type": "column",
			"balloon": getAmChartBalloonStyle(),
			"useLineColorForBulletBorder": true,
			"title": yLabels[i],
			"fillAlphas": 0.8,
			"lineAlpha": 1,
			"valueField": "col" + d,
			"columnWidth" : userOptions.graph.columnWidth,
			"valueAxis" : "AmAxis-0",
			"clustered": userOptions.graph.clustered == true  ? false : true,
			"balloonText": "[[title]]<br />[[category]] : <b>[[value]]</b>"
		};
		
		for(var ix = 1; ix < userOptions.valueAxes.length; ix++) {			
			var tar = userOptions.valueAxes[ix].graph;
			if($.inArray(d+"", tar) > -1) {
				graph.valueAxis = "AmAxis-" + ix;
			}
		}
		
		if(userOptions.graph.displayLabelText == true) {
			graph.labelText = "[[value]]";
		}
		
		graphs.push(graph);	
	});
	chartOptions.graphs = graphs;
	
	// X 데이터
	chartOptions.categoryField = "col" + xColumns[0];

	// X축
	chartOptions = getAmChartCategoryAxisOption(chartOptions, userOptions);
	
	// 차트 스크롤바
	chartOptions = getAmChartScrollOptions(chartOptions, userOptions);
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar.graph = "AmGraph-" + yColumns[0];
	}
	
	//--------------------------------------
	// BASIC OPTION SET
	
	chartOptions.type = "serial";
	chartOptions.titles = [{"text": userOptions.title, "size":20}];
	chartOptions.marginRight = +userOptions.marginRight;
	chartOptions.marginLeft = +userOptions.marginLeft;
	chartOptions.marginTop = +userOptions.marginTop;
	chartOptions.marginBottom = +userOptions.marginBottom;
	
	// LEGEND
	chartOptions = getAmChartLegendOption(chartOptions, userOptions);

	//--------------------------------------
	// CHART DRAW
	var chart = AmCharts.makeChart(chartId, chartOptions);
}

// 영역 차트
function visAreaChart(dataSheet, userOptions) {
	var chartId = userOptions._chartId;
	var xColumns = userOptions._columns.x;
	var yColumns = userOptions._columns.y;
	
	var xLabels = userOptions._labels.x;
	var yLabels = userOptions._labels.y;
	
	var colAll = [];
	colAll = colAll.concat(xColumns);
	colAll = colAll.concat(yColumns);	
	
	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);

	var chartOptions = getAmChartBasicOptions(userOptions);
	chartOptions.dataProvider = dataProvider;
	
	// Y축
	chartOptions = getAmChartValueAxesOption(chartOptions, userOptions);
	
	// CHART LINE
	var graphs = [];
	yColumns.forEach(function(d, i) {
		var graph = {
	        "id": "AmGraph-" + d,
	        "balloon": getAmChartBalloonStyle(),
	        "type" : userOptions.graph.type,
	        "bullet": getAmChartBulletType(userOptions.graph.bullet, i, chartOptions.colors),
	        "bulletBorderAlpha": userOptions.graph.bulletSize == 0 ? 0 : 1,
	        "bulletColor": "#FFFFFF",
	        "bulletSize": userOptions.graph.bulletSize,
	        "bulletAlpha": userOptions.graph.bulletSize == 0 ? 0 : 1,
	        "hideBulletsCount": 50,
	        "lineThickness": userOptions.graph.lineThickness,
			"fillAlphas":0.3,
	        "title": yLabels[i],
	        "useLineColorForBulletBorder": true,
	        "valueField": "col" + d,
	        "valueAxis" : "AmAxis-0",
	        "balloonText": "[[title]]<br />[[category]] : <b>[[value]]</b>"
		};
		
		for(var ix = 1; ix < userOptions.valueAxes.length; ix++) {			
			var tar = userOptions.valueAxes[ix].graph;
			if($.inArray(d+"", tar) > -1) {
				graph.valueAxis = "AmAxis-" + ix;
			}
		}
		
		if(userOptions.graph.displayLabelText == true) {
			graph.labelText = "[[value]]";
		}
		
		graphs.push(graph);
	});
	chartOptions.graphs = graphs;
	
	// X 데이터
	chartOptions.categoryField = "col" + xColumns[0];

	// X축
	chartOptions = getAmChartCategoryAxisOption(chartOptions, userOptions);
	
	// 차트 스크롤바
	chartOptions = getAmChartScrollOptions(chartOptions, userOptions);
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar.graph = "AmGraph-" + yColumns[0];
	}
	
	//--------------------------------------
	// BASIC OPTION SET
	
	chartOptions.type = "serial";
	chartOptions.titles = [{"text": userOptions.title, "size":20}];
	chartOptions.marginRight = +userOptions.marginRight;
	chartOptions.marginLeft = +userOptions.marginLeft;
	chartOptions.marginTop = +userOptions.marginTop;
	chartOptions.marginBottom = +userOptions.marginBottom;
	
	// LEGEND
	chartOptions = getAmChartLegendOption(chartOptions, userOptions);

	//--------------------------------------
	// CHART DRAW
	var chart = AmCharts.makeChart(chartId, chartOptions);
}

// 콤보 차트
function visComboChart(dataSheet, userOptions) {
	var chartId = userOptions._chartId;
	var xColumns = userOptions._columns.x;
	var yColumns = userOptions._columns.y;
	
	var xLabels = userOptions._labels.x;
	var yLabels = userOptions._labels.y;
	
	var colAll = [];
	colAll = colAll.concat(xColumns);
	colAll = colAll.concat(yColumns);	
	
	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);

	var chartOptions = getAmChartBasicOptions(userOptions);
	chartOptions.dataProvider = dataProvider;
		
	// Y축
	chartOptions = getAmChartValueAxesOption(chartOptions, userOptions);
	
	// CHART LINE
	var graphs = [];
	yColumns.forEach(function(d, i) {
		var type = userOptions.graph.type;
		
		if($.inArray(d + "", userOptions.graph.types) > -1) {
			type = "column";
		}
		
		var graph = {
	        "id": "AmGraph-" + d,
	        "balloon": getAmChartBalloonStyle(),
	        "type" : type,
	        "bullet": getAmChartBulletType(userOptions.graph.bullet, i, chartOptions.colors),	        		
	        "bulletBorderAlpha": userOptions.graph.bulletSize == 0 || type == "column" ? 0 : 1,
	        "bulletColor": "#FFFFFF",
	        "bulletSize": userOptions.graph.bulletSize,
	        "bulletAlpha": userOptions.graph.bulletSize == 0 || type == "column" ? 0 : 1,		
	        "hideBulletsCount": 50,
	        "lineThickness": type == "column" ? 1 : userOptions.graph.lineThickness,
			"fillAlphas": type == "column" ? 0.8 : 0,
	        "title": yLabels[i],
	        "useLineColorForBulletBorder": true,
	        "valueField": "col" + d,
	        "valueAxis" : "AmAxis-0",
	        "balloonText": "[[title]]<br />[[category]] : <b>[[value]]</b>"
		};
		
		for(var ix = 1; ix < userOptions.valueAxes.length; ix++) {			
			var tar = userOptions.valueAxes[ix].graph;
			if($.inArray(d+"", tar) > -1) {
				graph.valueAxis = "AmAxis-" + ix;
			}
		}
		
		if(userOptions.graph.displayLabelText == true) {
			graph.labelText = "[[value]]";
		}
		graphs.push(graph);
	});
	chartOptions.graphs = graphs;
	
	// X 데이터
	chartOptions.categoryField = "col" + xColumns[0];

	// X축
	chartOptions = getAmChartCategoryAxisOption(chartOptions, userOptions);
	
	// 차트 스크롤바
	chartOptions = getAmChartScrollOptions(chartOptions, userOptions);
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar.graph = "AmGraph-" + yColumns[0];
	}
	
	//--------------------------------------
	// BASIC OPTION SET
	
	chartOptions.type = "serial";
	chartOptions.titles = [{"text": userOptions.title, "size":20}];
	chartOptions.marginRight = +userOptions.marginRight;
	chartOptions.marginLeft = +userOptions.marginLeft;
	chartOptions.marginTop = +userOptions.marginTop;
	chartOptions.marginBottom = +userOptions.marginBottom;
	
	// LEGEND
	chartOptions = getAmChartLegendOption(chartOptions, userOptions);

	//--------------------------------------
	// CHART DRAW
	var chart = AmCharts.makeChart(chartId, chartOptions);
}

function visPieChart(dataSheet, userOptions) {
	var chartId = userOptions._chartId;
	var xColumns = userOptions._columns.x;
	var yColumns = userOptions._columns.y;
	
	var xLabels = userOptions._labels.x;
	var yLabels = userOptions._labels.y;
	
	var colAll = [];
	colAll = colAll.concat(xColumns);
	colAll = colAll.concat(yColumns);	
	
	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);

	var chartOptions = getAmChartBasicOptions(userOptions);
	chartOptions.dataProvider = dataProvider;
	
	chartOptions.titleField = "col" + xColumns[0];
	chartOptions.valueField = "col" + yColumns[0];

	chartOptions.radius = +userOptions.pie.radius;
	chartOptions.labelRadius = +userOptions.pie.labelRadius;
	chartOptions.innerRadius = +userOptions.pie.innerRadius;
	if(+userOptions.pie.depth3D > 0) {
		chartOptions.depth3D = +userOptions.pie.depth3D;
	}
	chartOptions.angle = +userOptions.pie.angle;	
	chartOptions.outlineThickness = +userOptions.pie.outlineThickness;
	chartOptions.alpha = +userOptions.pie.alpha;
	
	
	//--------------------------------------
	// BASIC OPTION SET
	
	chartOptions.type = "pie";
	chartOptions.titles = [{"text": userOptions.title, "size":20}];
	chartOptions.marginRight = +userOptions.marginRight;
	chartOptions.marginLeft = +userOptions.marginLeft;
	chartOptions.marginTop = +userOptions.marginTop;
	chartOptions.marginBottom = +userOptions.marginBottom;
	
	// LEGEND
	chartOptions = getAmChartPieLegendOption(chartOptions, userOptions);

	//--------------------------------------
	// CHART DRAW
	var chart = AmCharts.makeChart(chartId, chartOptions);
}

function visBubbleChart(dataSheet, userOptions) {
	var chartId = userOptions._chartId;
	var xColumns = userOptions._columns.x;
	var yColumns = userOptions._columns.y;
	var zColumns = userOptions._columns.z;
	
	var xLabels = userOptions._labels.x;
	var yLabels = userOptions._labels.y;
	var zLabels = userOptions._labels.z;
	
	// 차트 값 확인
	if(xColumns.length > 1 && xColumns.length != zColumns.length) {
		alert("차트의 X축 기준이 하나 이상일 경우 값기준과 동일한 개수로 설정해야 합니다.");
		return false;
	}
	if(yColumns.length > 1 && yColumns.length != zColumns.length) {
		alert("차트의 Y축 기준이 하나 이상일 경우 값기준과 동일한 개수로 설정해야 합니다.");
		return false;
	}

	var colAll = [];
	colAll = colAll.concat(xColumns);
	colAll = colAll.concat(yColumns);
	colAll = colAll.concat(zColumns);
	
	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);

	var chartOptions = getAmChartBasicOptions(userOptions);
	chartOptions.dataProvider = dataProvider;
	
	// X/Y축
	chartOptions = getAmChartXYValueAxesOption(chartOptions, userOptions);

	var graphs = [];
	zColumns.forEach(function(d, i) {
		var graph = {
	        "id": "AmGraph-" + d,
	        "balloon": getAmChartBalloonStyle(),
	        "bullet": "circle",
	        "bulletAlpha": userOptions.xy.bulletAlpha,
	        "bulletBorderAlpha": userOptions.xy.bulletAlpha,
	        "bulletBorderThickness" : userOptions.xy.bulletBorderThickness,
	        "lineAlpha": 0,
			"fillAlphas":0,
	        "title": zLabels[i],
	        "valueField": "col" + d,
	        "xField": "col" + (xColumns.length > 1 ? xColumns[i] : xColumns[0]),
	        "yField": "col" + (yColumns.length > 1 ? yColumns[i] : yColumns[0]),
	        "maxBulletSize": userOptions.xy.maxBulletSize,
	        "minBulletSize": userOptions.xy.minBulletSize,
	        "balloonText": "<div style='text-align:left;'>x: [[x]]<br />y : [[y]]<br />value : <b>[[value]]</b></div>"
		};
				
		if(userOptions.graph.displayLabelText == true) {
			graph.labelText = "[[value]]";
		}
		
		graphs.push(graph);
	});
	chartOptions.graphs = graphs;	
	
	// 차트 스크롤바
	chartOptions = getAmChartScrollOptions(chartOptions, userOptions);
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar.graph = "AmGraph-" + yColumns[0];
	}
	
	//--------------------------------------
	// BASIC OPTION SET
	
	chartOptions.type = "xy";
	chartOptions.titles = [{"text": userOptions.title, "size":20}];
	chartOptions.marginRight = +userOptions.marginRight;
	chartOptions.marginLeft = +userOptions.marginLeft;
	chartOptions.marginTop = +userOptions.marginTop;
	chartOptions.marginBottom = +userOptions.marginBottom;
	
	// LEGEND
	chartOptions = getAmChartLegendOption(chartOptions, userOptions);

	//--------------------------------------
	// CHART DRAW
	var chart = AmCharts.makeChart(chartId, chartOptions);
}

function visScatterChart(dataSheet, userOptions) {
	var chartId = userOptions._chartId;
	var xColumns = userOptions._columns.x;
	var yColumns = userOptions._columns.y;
	var zColumns = userOptions._columns.z;
			
	// 차트에 사용될 데이터 정리(z값에 따라구분)
	//  x, y, 구분 -> x, y, 구분1, 구분2, 구분3
	var data = visDataXYPivot(dataSheet, xColumns[0], yColumns[0],  zColumns[0]);
	
	var dataLabel = data[0];
	var dataProvider = data[1];
	
	var chartOptions = getAmChartBasicOptions(userOptions);
	chartOptions.dataProvider = dataProvider;
	
	// X/Y축
	chartOptions = getAmChartXYValueAxesOption(chartOptions, userOptions);

	var graphs = [];
	dataLabel.forEach(function(d, i) {
		var graph = {
	        "id": "AmGraph-" + d,
	        "balloon": getAmChartBalloonStyle(),
	        "bullet": "circle",
	        "bulletAlpha": userOptions.xy.bulletAlpha,
	        "bulletBorderAlpha": userOptions.xy.bulletAlpha,
	        "bulletBorderThickness" : userOptions.xy.bulletBorderThickness,
	        "lineAlpha": 0,
			"fillAlphas":0,
	        "title": d,
	        "xField": "x",
	        "yField": "y" + i,
	        "bulletSize": userOptions.xy.bulletSize,
	        "balloonText": "<div style='text-align:left;'>x: [[x]]<br />y : [[y]]<br />category : <b>[[title]]</b></div>"
		};
				
		if(userOptions.graph.displayLabelText == true) {
			graph.labelText = "[[value]]";
		}
		
		graphs.push(graph);
	});
	chartOptions.graphs = graphs;	
	
	// 차트 스크롤바
	chartOptions = getAmChartScrollOptions(chartOptions, userOptions);
	if(userOptions.chartScrollbar.display == true) {
		chartOptions.chartScrollbar.graph = "AmGraph-" + yColumns[0];
	}
	
	//--------------------------------------
	// BASIC OPTION SET
	
	chartOptions.type = "xy";
	chartOptions.titles = [{"text": userOptions.title, "size":20}];
	chartOptions.marginRight = +userOptions.marginRight;
	chartOptions.marginLeft = +userOptions.marginLeft;
	chartOptions.marginTop = +userOptions.marginTop;
	chartOptions.marginBottom = +userOptions.marginBottom;
	
	// LEGEND
	chartOptions = getAmChartLegendOption(chartOptions, userOptions);

	//--------------------------------------
	// CHART DRAW
	var chart = AmCharts.makeChart(chartId, chartOptions);
}

function visScatterMatrixChart(dataSheet, userOptions) {
	var colAll = [];
	colAll = colAll.concat(userOptions._columns.x);
	colAll = colAll.concat(userOptions._columns.y);

	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);
	
	var chartOptions = {
		"chartId" : userOptions._chartId,
		"species" : "col" + userOptions._columns.x[0],
		"labels" : userOptions._labels.y,
		"margins" : {
			"left" : +userOptions.marginLeft,
			"right" : +userOptions.marginRight,
			"top" : +userOptions.marginTop,
			"bottom" : +userOptions.marginBottom
		},
		"title" : userOptions.title,
		"colors" : userOptions.colors,
		"regend" : userOptions.regend
	}
	
	// scatter_matrix.js
	graphScatterplotMatrix(dataProvider, chartOptions);
}


function visMap(dataSheet, userOptions) {
	var colAll = [];
	colAll = colAll.concat(userOptions._columns.x);
	colAll = colAll.concat(userOptions._columns.y);
	colAll = colAll.concat(userOptions._columns.z);
	
	if(userOptions.map.label != "") {
		colAll.push(userOptions.map.label);
	}
	
	// 차트에 사용될 데이터 정리
	var dataProvider = visDataPivot(dataSheet, colAll);
	
	var chartOptions = {
			"mapId" : userOptions._mapId,
			"x" : "col" + userOptions._columns.x[0],
			"y" : "col" + userOptions._columns.y[0],
			"margins" : {
				"left" : +userOptions.marginLeft,
				"right" : +userOptions.marginRight,
				"top" : +userOptions.marginTop,
				"bottom" : +userOptions.marginBottom
			},
			"colors" : userOptions.colors,
	};
	
	if(userOptions._columns.z.length > 0) {
		chartOptions.z = "col" + userOptions._columns.z[0];
	}
	
	for(var k in userOptions.map) {
		chartOptions[k] = userOptions.map[k];
	}
	
	//vworldMap(dataProvider, chartOptions);
	olMap(dataProvider, chartOptions);
}

function visWordcloud(dataSheet, userOptions) {

}

function visTreeMap(dataSheet, userOptions) {

}



///////////////////////////////////////////////////////////////////////////////
// 웹에서 호출

// 시각화 기본값 설정
function visFieldOptions(headers) {
	var options = {
		theme : "light",
		colors : null,
		title : "",
		marginLeft : 60,
		marginRight : 20,
		marginTop : 20,
		marginBottom : 20,
		rotate : false,
		regend : {
			display : false,
			position : "right"
		},
		categoryAxis : {
			title : "",
			labelRotation : 0,
			parseDates : false,
			dataDateFormat : "YYYY-MM-DD",
			minPeriod : "DD",
			min : null,
			max : null
		},
		valueAxes : [{
			title : "",
			position: "left",
			offset: 0
		}, {
			title : "",
			position: "right",
			graph: [],
			offset : 0
		}, {
			title : "",
			position: "right",
			graph: [],
			offset : 0
		}],
		valueAxis : {
			min : null,
			max : null
		},
		graph : {
			lineThickness : 2,
			bulletSize : 6,
			bullet : "round",
			columnWidth : 0.5,
			stack : false,
			clustered : false,
			types : null,
			displayLabelText: false
		},
		pie : {
			dept3D : 0,
			angle : 0,
			innerRadius : 0,
			labelRadius : 30,
			radius : "",
			outlineThickness : 0,
			alpha : 1
		},
		xy : {
			bulletSize:4,
			minBulletSize: 4,
			maxBulletSize: 50,
			xMin: null,
			xMax: null,
			xDateMin: null,
			xDateMax: null,
			yMin: null,
			yMax: null,
			bulletAlpha : 0.7,
			bulletBorderThickness:0
		},
		chartScrollbar : {
			display: false
		},
		valueScrollbar : {
			display: false,
			offset:80
		},
		chartCursor : {
			categoryBalloonDateFormat : "YYYY-MM-DD"
		},
		map : {
			crs : "",
			style : {
				colors : [],
				levels : [],
				stroke : 0,
				opacity : 0.3,
			},
			nullBoundary : false,
			radiusMax : 100,
			radiusMin : 3,
			radius : 3,
			showLabel : false,
			showValue : false
		}
	};
	
	
	var fn_set = function(n, val) {		
		var x = 1;
		var ptr = options;
		var defaultValue = null;
		var regExp = new RegExp("\\[([0-9]+)\\]");
		var opt = "";
		
		while(x < (n.length - 1)) {
			opt = n[x++];
			
			if(regExp.test(opt)) {
				var f = opt.match(regExp);
				var ix = $.trim(f[1]);
				opt = opt.replace(regExp, "");
				ptr = ptr[opt][+ix];			
			} else {
				ptr = ptr[opt];
			}
		}
		
		opt = n[x];
		var regExp = new RegExp("\\[([0-9]*)\\]");
		if(regExp.test(opt)) {
			var f = opt.match(regExp);
			var ix = $.trim(f[1]);
			opt = opt.replace(regExp, "");
			ptr = ptr[opt];
			
			if(ptr === null) ptr = [];
			opt = ix == "" ? ptr.length - 1 : +ix;
		}
		
		ptr[opt] = val !== null ? val : ptr[opt];
	}
	
	$("#vis_set dl:visible").find("input[name^='chartOption.'], select[name^='chartOption.']").each(function(i, o) {
		var optionName = $(this).attr("name").split(".");
		var tagName = $(this).prop("tagName").toLowerCase();
		var objType = $(this).attr("type");
		var val = null;
		
		if(tagName == "select") {
			if($(this).attr("multiple") == "multiple") {
				$(this).find("option:selected").each(function(i, o) {
					if(val == null) val = [];
					val.push($(o).val());
				});
			} else {
				val = $(this).find("option:selected").val();
			}
		} else if(objType == "text") {
			if($(this).is(".spinner")) {
				val = $(this).suf_spinner( "value" );
			} else {
				val = $(this).val();
			}
			
			if($(this).attr("multiple") == "multiple") {
				val = val.split(",");
			}
		} else if(objType == "checkbox") {
			val = $(this).is(":checked");
		} else if(objType == "radio") {
			if($(this).is(":checked")) val = $(this).val();
		}
		
		fn_set(optionName, val);
	});	
	
	// 컬러셋설정
	var colors = [];
	$("#color_palette").find("li.colorpic").each(function(i, o) {
		colors.push($(o).data("color"));
	});
	
	if(colors.length > 0) {
		options.colors = colors;
	} else if(options.theme == "light") {
		//options.colors = ["#a48fdf","#f18595","#e9c352","#9fc96b","#f88d5a","#56c3ba","#5f83c7","#d39731","#5fb1e5","#8bb8a8","#b3ddea"];
	}
	
	//$("#visFrm input[name=visChartOptions").val(JSON.stringify(options));
	
	
	// 컬럼 선택 값 (언더바(_)가 붙은 옵션값은 차트 설정값이 아님)
	// _columns : {x : [1, 3, 6] ...}
	// - Excel Column A, B, C, D ... -> 0 ~ N
	options._columns = { "x" : [], "y" : [], "z" : [] };
	options._labels = { "x" : [], "y" : [], "z" : [] };

	for(var k in options._columns) {
		if($("#" + k + "_selector select").is("select")) {
			if($("#" + k + "_selector select").attr("multiple") != "multiple") {
				var v = $("#" + k + "_selector option:selected").val();
				
				options._columns[k].push( v );
				options._labels[k].push( headers[v] );
			} else {
				// value값으로는 선택 순서를 알 수 없음
				$("#" + k + "_selector a.search-choice-close").each(function(i, o) {
					var v = $(o).data("option-array-index");
	
					options._columns[k].push( v );
					options._labels[k].push( headers[v] );
				});
			}
			
			$("#visFrm input[name=visChart" + k.toUpperCase() + "]").val(options._columns[k].join(","));
			$("#visFrm input[name=visChart" + k.toUpperCase() + "Label]").val(options._labels[k].join("||"));
		}
	};

	return options;
}

function appendVisColor(obj, colorHexString) {
	$("<li class=\"colorpic\"></li>").data("color", colorHexString).css("background", colorHexString).click(function() {
		$(this).children("a").click();
	}).append($("<a href=\"#\" title=\"삭제\">×</a>").click(function() {
		$(this).parent().remove();
		return false;
	})).insertBefore($(obj).children(":last"));
	return 
}

function getVisColorListTheme(obj, theme) {
	var theme = AmCharts.themes[theme].AmCoordinateChart;
	
	theme.colors.forEach(function(d, i) {
		appendVisColor(obj, d);
	});
}

function getVisColorListThemePattern(obj) {
	var theme = AmCharts.themes.patterns.AmCoordinateChart;
	// 삭제 못함
	theme.patterns.forEach(function(d, i) {
		$("<li class=\"colorpic\"></li>").css("background", "url(/js/vendor/amcharts/" + d.url + ") #ffffff center center").insertBefore($(obj).children(":last"));
	});
}


//-----------------------------------------------------------------------------
// 시각화 선택
function visCreateChart(chartType, dataSheet, visOptions) {
	var fnChart = {
			"1" : visLineChart,
			"2" : visBarChart,
			"3" : visAreaChart,
			"4" : visComboChart,
			"5" : visPieChart,
			"6" : visBubbleChart,
			"7" : visScatterChart,
			"8" : visScatterMatrixChart,
			"21" : visMap,
			"31" : visWordcloud,
			"32" : visTreeMap
	};
	
	// 시각화 생성 함수 실행
	// xColumns : {column : [1, 3, 6], label : ["DATE", "A", "B"]}
	// 		- Excel Column A, B, C, D ... -> 0 ~ N
	// dataSheet : handsontable sheet
	visOptions._chartId = "chartdiv";
	visOptions._mapId = "chartdiv";
	
	$("#" + visOptions._chartId).empty();
	$("#" + visOptions._mapId).empty();
	fnChart[chartType](dataSheet, visOptions);
}

// 섬네일 저장
function visCreateThumbnail(afterFunc) {
	try {
		var thumbId = "thumbdiv";
		var chartId = "chartdiv";
		
		var obj = null;
		if($("#" + chartId + " > .ol-viewport").is("div")) {
			if(_olMap != null) {
				olMapToImage(_olMap, afterFunc);
				return;
			}
			throw new Error(0, "map is null");
		} else {
			visSvgToImage(chartId, thumbId);
			obj = $("#" + thumbId + " > div")[0];
			
			html2canvas(obj, {"backgroundColor": "transparent"}).then(function(canvas) {
				var data = canvas.toDataURL("image/png");
				$("#" + thumbId).empty();
				try {
					afterFunc(data);
				} catch (e) {}
			});
		}
	} catch(e) {
		$("#" + thumbId).empty();
		afterFunc(null);
	}
}

function getChartStyleSeet(targetSelector, parent) {
	var styleDefs = "";
	var sheets = document.styleSheets;

	var cls = $(targetSelector).attr("class") + " ";
	if(parent != null && $(parent) != $(targetSelector)) {
		cls += $(parent).attr("class");
	}
	cls = cls.replace(/[ ]+/g, " ");
	var regexp = new RegExp("(^|,)[ ]*([.]" + (cls.split(" ").join("|[.]")) + ")([ ,.#]|$)");

// 	for (var i = 0; i < sheets.length; i++) {
// 		if(!/(\/assets\/js\/d3\/c3[a-zA-Z0-9._-]*.css|\/graph\/[a-zA-Z0-9._-]*.css)$/.test(sheets[i].href)) continue;

// 		var rules = sheets[i].cssRules;
// 		for (var j = 0; j < rules.length; j++) {
// 			var rule = rules[j];

// 			if (rule.style) {
// 				var selectorText = rule.selectorText;
// 				var selectorTextTmp = selectorText;
// 				while(regexp.test(selectorTextTmp)) {
// 					selectorTextTmp = selectorTextTmp.replace(regexp, "$1$3");
// 				}

// 				var chk = 0;
// 				if(selectorTextTmp != "" && 
// 						(selectorTextTmp != selectorText 
// 							|| $(targetSelector).has(selectorText).is(targetSelector))) {
// 					styleDefs += selectorTextTmp + " { " + rule.style.cssText + " }\n";
// 					chk = 1;
// 				}
// 			}
// 		}
// 	}
	var style = document.createElement('style');
	style.setAttribute('type', 'text/css');
	style.innerHTML = "<![CDATA[\n" + styleDefs + "\n]]>";

	return style;
}

function visSvgToImage(targetId, thumbId) {
	var obj = $("<div></div>").appendTo("#" + thumbId);
	
	var width = $("#" + targetId).width();
	var height = $("#" + targetId).height();	
	obj.width(width).height(height);
	
	obj.html($("#" + targetId).html());
	
	var svgAll = d3.selectAll("#" + thumbId + " svg");
	svgAll.each(function(d, i) {
		var svg = d3.select(this),
			width_ = +svg.attr("width"),
			height_ = +svg.attr("height");

		//var styleSeet = getChartStyleSeet("#" + thumbId, svg.node().parentNode);
		//var defs = svg.select("defs");
		//if(defs == '') defs = svg.insert("defs", ":first-child");
		//defs.node().appendChild(styleSeet);

		var conv = $("<canvas></canvas>").attr("id", thumbId + 'InnerCanvas' + i).width(width_).height(height_);
		$(this).after(conv);

		conv.append(this);
		var html = svg
				.attr("version", 1.1)
				.attr("xmlns", "http://www.w3.org/2000/svg")
				.node().parentNode.innerHTML; 
		svg.remove();

		canvg(thumbId + 'InnerCanvas' + i, html);
		var img = conv[0].toDataURL("image/png");
		conv.after($("<img>").attr("src", img));
		conv.remove();
	});
}