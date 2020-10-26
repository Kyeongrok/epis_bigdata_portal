
var _olMap = null;
var _olMapType = null;
function olMap(data, options) {
	var mapId = "#" + options.mapId,
		mapType = options.mapType,
		labels = options.labels,
		width = $(mapId).width(),
		height = $(mapId).height();
		
	var x = options.x;
	var y = options.y;
	var z = options.z;

	var label_col = options.label != null && options.label != "" ? "col" + options.label : null;
	
	// 좌표	
	if(options.geoType == "geocode") {
		createMapFromGeo();
	} else if(options.geoType == "geogroup") {
		createMapFromGeoGroup();
	}
	// PNU
	else {
		createMapFromPNU();
	}
	
	// PNU 코드로 지도 그리기
	// 동단위까지밖에 안됨
	function createMapFromPNU() {
		var max = d3.max(data, function(d) { return +d[z]; });
		var min = d3.min(data, function(d) { return +d[z]; });
		var geojson_file = "";
		var geojson_id = "";
		
		var pnu_len = 8;
		switch(options.boundaryLevel) {
		case "adsido":
			pnu_len = 2;
			geojson_file = "sido_all";
			if($.trim(options.boundarySingleCd) != "") {
				geojson_file = "sido/sido_" + options.boundarySingleCd;
			}
			geojson_id = "CTPRVN_CD";
			geojson_label = "CTP_KOR_NM";
			break;
		case "adsigg":
			pnu_len = 5;
			geojson_file = "sigg_all";
			if($.trim(options.boundarySingleCd) != "") {
				geojson_file = "sigg/sigg_" + options.boundarySingleCd;
			}
			
			geojson_id = "SIG_CD";
			geojson_label = "SIG_KOR_NM";
			break;
		default:
			pnu_len = 8;
			geojson_file = "emd_all";
			if($.trim(options.boundarySingleCd) != "") {
				geojson_file = "emd/emd_" + options.boundarySingleCd;
				if(options.boundarySingleCd.length > 2) {
					geojson_file = "sigg_emd/emd_" + options.boundarySingleCd;
				}
			}
			
			geojson_id = "EMD_CD";
			geojson_label = "EMD_KOR_NM";
		}
				
		var scaleFunction = d3.scale.linear().domain( [min, max] ).range( [100, 0] );
		var pnuSpl = function(v) { return $.trim(new String(v)).substring(0, pnu_len); };
				
		var featureStyleIndex = {};
		for(var i = 0; i < data.length; i++) {
			var lv = scaleFunction(+data[i][z]);
			var n = 0;
			while(lv > +options.style.levels[n] && n < 5) { n++; }
			featureStyleIndex[pnuSpl(data[i][x])] = {"lv" : n, "label" : label_col !== null ? data[i][label_col] : "", "value": data[i][z]};
		}
		
		d3.json("/js/bigdata/graph/data/" + geojson_file + ".geojson", function(error, data) {
			var geojsonObject = {
				"type": "FeatureCollection",
				"crs": {
					"type": "name",
					"properties": {
						"name": "EPSG:4326"
					}
				},
				"features" : data.features
			};
			
			// 컬러 스팩트럼 설정
			var styles = [];
			
			var strokeWidth = (+options.style.stroke);
			
			var strokeOpacity = strokeWidth == 0 ? 0 : 0.8;

			for(var n = 0; n < options.style.colors.length; n++) {
				var rgb = hexToRgb(options.style.colors[n]);
				
				styles[n] = new ol.style.Style({
					stroke: new ol.style.Stroke({
						color: "rgba(" + rgb.r + "," + rgb.g + ", " + rgb.b + ", " + strokeOpacity + ")",
						width: strokeWidth
					}),
					fill: new ol.style.Fill({
						color : "rgba(" + rgb.r + "," + rgb.g + ", " + rgb.b + "," + options.style.opacity + ")"
					})
				});
			}

			var defaultStyle = new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "rgba(75,91,102," + strokeOpacity + ")",
					width: strokeWidth
				}),
				fill: null
			});
	
			var styleFunction = function(feature, resolution) {
				var id = feature.get(geojson_id);
				var st = options.nullBoundary == true ? null : defaultStyle;
				var lbl = label_col !== null ? "" : feature.get(geojson_label);
				var val = "";

				if(id in featureStyleIndex) {
					st = styles[featureStyleIndex[id].lv];
					if(label_col !== null) {
						lbl = featureStyleIndex[id].label;
					}
					val = featureStyleIndex[id].value;
				}

				if(st != null) {
					feature.set("label", lbl);
					feature.set("value", val);
					
					st.setText(getLabelStyle(feature, resolution));
				}
				return st;
			};
			
			var vectorSource = new ol.source.Vector({
				features: (new ol.format.GeoJSON()).readFeatures(geojsonObject)
			});
			
			createMap(vectorSource, styleFunction);
		});
	}
	
	
	// 좌표 정보로 지도 그리기
	function createMapFromGeo() {
		
		var max = d3.max(data, function(d) { return +d[z]; });
		var min = d3.min(data, function(d) { return +d[z]; });

		var radiusFunction = d3.scale.linear().domain( [min, max] ).range( [options.radiusMin, options.radiusMax] );
		var scaleFunction = d3.scale.linear().domain( [min, max] ).range( [100, 0] );
		
		var geojsonObject = {
			"type": "FeatureCollection",
			"crs": {
				"type": "name",
				"properties": {
					"name": options.crs
				}
			},
			"features" : []
		};
		var features = geojsonObject.features;
	
		for(var i = 0; i < data.length; i++) {
			// Circle 반지름 생성
			var lv = scaleFunction(+data[i][z]);
			var radius = radiusFunction(data[i][z]);
			var n = 0;
			while(lv > +options.style.levels[n] && n < 5) { n++; }
			var rgb = hexToRgb(options.style.colors[n]);
			
			features.push({
				"type": "Feature",
				"geometry": {
					"type": "Point", 
					"coordinates": [data[i][x], data[i][y]]
				},
				"properties" : {
					"value" : data[i][z],
					"radius" : radius,
					"color" : rgb,
					"index" : n,
					"label" : label_col !== null ? data[i][label_col] : ""
				}
			});
		}		
		
		var vectorSource = new ol.source.Vector({
			features: (new ol.format.GeoJSON()).readFeatures(geojsonObject)
		});		
		
		var styleFunction = function(feature, resolution) {		
			var r = feature.get("radius");
			var rgb = feature.get("color");
			
			return new ol.style.Style({
				image: new ol.style.Circle({
					radius: r,
					stroke: new ol.style.Stroke({
						color : "rgba(" + rgb.r + "," + rgb.g + ", " + rgb.b + ", 0.8)",
						width: (+options.style.stroke)
					}),
					fill: new ol.style.Fill({
						color : "rgba(" + rgb.r + "," + rgb.g + ", " + rgb.b + "," + options.style.opacity + ")"
					})
				}),
				text : getLabelStyle(feature, resolution)
			});
		};
				
		createMap(vectorSource, styleFunction);
	}
	
	
	// 좌표 정보로 지도 그리기
	function createMapFromGeoGroup() {
		var scaleIndex = [];
		var scaleFunction = function(value) {
			var idx = $.inArray(value, scaleIndex);
			if(idx < 0){
				scaleIndex.push(value);
				idx = scaleIndex.length - 1;
			}
			
			return idx % options.colors.length;
		};
		
		var geojsonObject = {
			"type": "FeatureCollection",
			"crs": {
				"type": "name",
				"properties": {
					"name": options.crs
				}
			},
			"features" : []
		};
		var features = geojsonObject.features;
	
		for(var i = 0; i < data.length; i++) {
			// Circle 반지름 생성
			var lv = scaleFunction(data[i][z]);
			features.push({
				"type": "Feature",
				"geometry": {
					"type": "Point", 
					"coordinates": [data[i][x], data[i][y]]
				},
				"properties" : {
					"value" : data[i][z],
					"index" : lv,
					"label" : label_col !== null ? data[i][label_col] : ""
				}
			});
		}		
		
		var vectorSource = new ol.source.Vector({
			features: (new ol.format.GeoJSON()).readFeatures(geojsonObject)
		});		
		
		var styles = [];
		for(var i=0; i < options.colors.length; i++) {
			var rgb = hexToRgb(options.colors[i]);
			styles.push(new ol.style.Style({
				image: new ol.style.Circle({
					radius: options.radius,
					stroke: new ol.style.Stroke({
						color : "rgba(" + rgb.r + "," + rgb.g + ", " + rgb.b + ", 0.8)",
						width: (+options.style.stroke)
					}),
					fill: new ol.style.Fill({
						color : "rgba(" + rgb.r + "," + rgb.g + ", " + rgb.b + "," + options.style.opacity + ")"
					})
				})
			}));
		}
		
		var styleFunction = function(feature, resolution) {		
			var idx = feature.get("index");
			var st = styles[idx];
			st.setText(getLabelStyle(feature, resolution));
			
			return st;
		};
				
		createMap(vectorSource, styleFunction);
	}


	function createMap(vectorSource, styleFunction) {
		var vectorLayer = new ol.layer.Vector({
			source: vectorSource,
			style: styleFunction
		});
	
		//지도 영역

		// 기본지도 타일(vworld tiles)
		// Base, gray, midnight, Hybrid, Satellite
		var mapLayer = null;
		if(mapType == "none") {
			mapLayer = new ol.layer.Tile({name : "mapBaseLayer", id : "mapBaseLayer"});
		} else {			
			var source = new ol.source.OSM({
				attributions: ['배경지도 - VWORLD 공간정보 오픈플랫폼 | 국토교통부'],
				url: "//api.vworld.kr/req/wmts/1.0.0/20A78326-5B3D-303C-BF5C-5F1C9C16F440/" + mapType + "/{z}/{y}/{x}.png",
				crossOrigin: null
			})
			
			mapLayer = new ol.layer.Tile({
				name : "mapBaseLayer", 
				id : "mapBaseLayer",
				source: source
			});
		}

		var view = new ol.View({
			 projection: ol.proj.get("EPSG:4326"),
		     extent: ol.proj.get("EPSG:4326").getExtent() || undefined,
			 center:[127.100616,37.402142],
		     zoom: 7,
		     minZoom : 7,
		     maxZoom : 18
		});
		var map = new ol.Map({
			layers: [mapLayer, vectorLayer],
			target: document.getElementById(options.mapId),
			view: view
		});

		var extent = vectorLayer.getSource().getExtent();
		view.fit(extent, map.getSize());

		_olMap = map;
		_olMapType = mapType;
	}
	
	function hexToRgb(hex) {
	    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
	    return result ? {
	        r: parseInt(result[1], 16),
	        g: parseInt(result[2], 16),
	        b: parseInt(result[3], 16)
	    } : null;
	}
	
	function getCenterOfExtent(extent){
		var X = extent[0] + (extent[2]-extent[0])/2;
		var Y = extent[1] + (extent[3]-extent[1])/2;
		return [X, Y];
	}
	
	// 라벨 표시
	function getLabelStyle(feature, resolution) {
		if(options.showLabel != true && options.showValue != true) {
			return new ol.style.Text();
		}

		return new ol.style.Text({
			textAlign: "center",
			textBaseline: "middle",
			font: "13px Nanum Gothic",
			fontWeight : "bold",
			text: getText(feature, resolution, "normal"),
			fill: new ol.style.Fill({color: "#000000"}),
			stroke: new ol.style.Stroke({
				color: "#ffffff", 
				width: 3
			}),
			offsetX: 0,
			offsetY: 0,
			placement: "point",
			maxAngle: 45,
			overflow: false,
			rotation: 0
		});
	}
	
	// 라벨 표시
    function getText(feature, resolution, type) {
        var maxResolution = 1200;
        var text = "";
        
        if(options.showLabel == true && feature.get("label") != "") {
        	text += feature.get("label");
        }
        if(options.showValue == true && feature.get("value") != "") {
        	text += (text != "" ? " : " : "") + feature.get("value");
        }
        
        if (resolution > maxResolution) {
          text = '';
        } else if (type == 'hide') {
          text = '';
        } else if (type == 'shorten') {
          text = text.trunc(12);
        } else if (type == 'wrap') {
          text = stringDivider(text, 16, '\n');
        }

        return text;
	}
      
	// 문자열 자르기
	function stringDivider(str, width, spaceReplacer) {
		if(str == "") return "";
		
		if (str.length > width) {
			var p = width;
			while (p > 0 && (str[p] != ' ' && str[p] != '-')) {
				p--;
			}
			if (p > 0) {
				var left;
				if (str.substring(p, p + 1) == '-') {
					left = str.substring(0, p + 1);
				} else {
					left = str.substring(0, p);
				}
				var right = str.substring(p + 1);
				return left + spaceReplacer + stringDivider(right, width, spaceReplacer);
			}
		}
		return str;
	}
}	


function olMapToImage(map, afterFunc) {
	var thumbId = "thumbdiv";
	var chartId = "chartdiv";
	
	var obj = $("<div></div>").appendTo("#" + thumbId);
	var width = $("#" + chartId).width();
	var height = $("#" + chartId).height();	
	obj.width(width).height(height);

	// 카피해서 프록시로 배경 이미지만 세팅
	var source = new ol.source.OSM({
		attributions: ['배경지도 - VWORLD 공간정보 오픈플랫폼 | 국토교통부'],
		url: "/bdp/visual/proxy/map/20A78326-5B3D-303C-BF5C-5F1C9C16F440/" + _olMapType + "/{z}/{y}/{x}.do",
		crossOrigin: null
	});
	
	var mapLayer = new ol.layer.Tile({
		name : "mapCopyLayer", 
		id : "mapCopyLayer",
		source: source
	});
	
	var view = new ol.View({
		 projection: ol.proj.get("EPSG:4326"),
	     extent: ol.proj.get("EPSG:4326").getExtent() || undefined,
		 center: map.getView().getCenter(),
	     zoom: map.getView().getZoom()
	});
		
	var copyMap = new ol.Map({
		layers: [mapLayer, map.getLayers().getArray()[1]],
		target: obj[0],
		view: view
	});	
		
	copyMap.once('rendercomplete', function(event) {
		var dataURL;
		var canvas = event.context.canvas;
		if (ol.has.DEVICE_PIXEL_RATIO == 1) {
			dataURL = canvas.toDataURL('image/png');
		} else {
			var targetCanvas = document.createElement('canvas');
			var size = copyMap.getSize();
			targetCanvas.width = size[0];
			targetCanvas.height = size[1];
			targetCanvas.getContext('2d').drawImage(canvas, 0, 0,
					canvas.width, canvas.height, 0, 0, targetCanvas.width,
					targetCanvas.height);
			dataURL = targetCanvas.toDataURL('image/png');
		}
		$("#" + thumbId).empty();
		try {			
			afterFunc(dataURL);
		} catch (e) {}
	});
	copyMap.renderSync();
}



