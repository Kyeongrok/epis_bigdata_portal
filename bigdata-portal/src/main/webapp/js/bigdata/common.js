
var loader = true;
$(function() {
	initField();
	
	$.ajaxSetup({
		contentType : 'application/x-www-form-urlencoded; charset=utf-8',
		headers: {'Action-Call-Type': 'AJAX'}
	});
	
	
	$( document ).ajaxStart(function() {
		if(loader) {
			$('#loader').show();
			if('spin' in $.fn) {
				loading(true);
			}
		}
	});
	
	$( document ).ajaxStop(function() {
		if(loader) {
			$('#loader').hide();
			if('spin' in $.fn) {
				loading(false);
			}
		}
	});	

	$.getMultiScripts = function(arr, path) {
		var _arr = $.map(arr, function(scr) {
			return $.getScript( (path||"") + scr );
		});

		_arr.push($.Deferred(function( deferred ){
			$( deferred.resolve );
		}));
		return $.when.apply($, _arr);
	};

	$.getCSS = function(scr, path) {
		$('<link/>', {rel: 'stylesheet', type: 'text/css', href: (path||"") + scr}).appendTo('head');
	}

	$.getMultiCSS = function(arr, path) {
		$.each(arr, function(i, scr) {
			$.getCSS(scr, path);
		});
	};
	
	// jquery tmpl 사용시
	if('tmpl' in $.fn) {
		$("script[type='text/x-jquery-tmpl']").appendTo("body");
	}	
});


function loading(show) {
	if(show) {
		var opts = {
			  lines: 10, // The number of lines to draw
			  length: 20, // The length of each line
			  width: 10, // The line thickness
			  radius: 20, // The radius of the inner circle
			  //scale: 0.9, // Scales overall size of the spinner
			  corners: 1, // Corner roundness (0..1)
			  color: '#ffffff', // CSS color or array of colors
			  fadeColor: 'transparent', // CSS color or array of colors
			  speed: 0.7, // Rounds per second
			  rotate: 0, // The rotation offset
			  animation: 'spinner-line-fade-quick', // The CSS animation name for the lines
			  direction: 1, // 1: clockwise, -1: counterclockwise
			  zIndex: 2e9, // The z-index (defaults to 2000000000)
			  className: 'spinner', // The CSS class to assign to the spinner
			  //top: '50%', // Top position relative to parent
			  //left: '50%', // Left position relative to parent
			  shadow: '0 0 2px transparent', // Box-shadow for the lines
			  //position: 'absolute', // Element positioning
		};
		$('#loader').show().spin(opts);
	} else {
		$('#loader').empty().hide();
	}
}


//특정 element 제외
$.fn.ignore = function(sel){
	return this.clone().find(sel||">*").remove().end();
};

//data 값으로 필터링
$.fn.filterByData = function(prop, val) {
    return this.filter(
        function() { return $(this).data(prop)==val; }
    );
}

$.fn.outerHtml = function() {
	var o = this.clone().wrapAll("<div/>").parent();
	var x = o.html();
	o.remove().end();
	
	return x;
}

$.fn.serializeObject = function() {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function() {
                    obj[this.name] = this.value;
                });
            }//if ( arr ) {
        }
    } catch (e) {
        alert(e.message);
    } finally {
    }
 
    return obj;
};

if( typeof(Spinner) != 'undefined' ) {
	$.fn.spin = function(opts) {
		this.each(function() {
			var $this = $(this),
				data = $this.data();

			if (data.spinner) {
				data.spinner.stop();
				delete data.spinner;
			}
			if (opts !== false) {
				data.spinner = new Spinner($.extend({
					color : $this.css('color')
				}, opts)).spin(this);
			}
		});
		return this;
	};
}

$.extend({
	postJSON : function( url, data, callback ) {
		return jQuery.post( url, data, callback, "json" );
	}
});

if('tmpl' in $) {
	$.extend(jQuery.tmpl.tag, {
	    "for": {
	        _default: {$2: "var i=1;i<=1;i++"},
	        open: 'for ($2){',
	        close: '};'
	    }
	});
}

//browser detect
//if(!('browser' in $)) {
	$.browser = (function() {
		var s = navigator.userAgent.toLowerCase();

		var match = 
			/(edge)\/([\w.]+)\./.exec( s ) ||
			/(chrome)[ \/]([\w.]+)/.exec( s ) ||
			/(webkit)[ \/]([\w.]+)/.exec( s ) ||
			/(opera)(?:.*version|)[ \/]([\w.]+)/.exec( s ) ||
			/(msie) ([\w.]+)/.exec( s ) ||
			/(trident).*rv[ :]*([\w.]+)\./.exec( s ) ||
			(s.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec( s )) ||
		[];
		
		var b = {'msie':false, 'msedge':false, 'opera':false, 'mozilla':false, 'webkit':false, 'chrome':false, 'safari':false};
		var browser = match[ 1 ] || "";
		var version = match[ 2 ] || "0";
		
		if ( browser == "edge" ) {
			b.msedge = true;
			b.version = match[ 2 ] || "";
		} else if ( browser == "trident" ) {
			b.msie = true;
			b.version = match[ 2 ] || "";
		} else if ( browser ) {
			b[ browser] = true;
			b.version = version;
		} 
		
		if ( b.chrome ) {
			b.webkit = true;
		} else if ( b.webkit ) {
			b.safari = true;
		}
		return b;
	}());
//}

if(!('device' in $)) {
	$.device = (function() {
		var ua = navigator.userAgent.toLowerCase();
		if(/ipad/.test(ua)) {
			return 'tablet';
		} else if(/lgtelecom/.test(ua) || /android/.test(ua) || /blackberry/.test(ua) || /iphone/.test(ua) 
				|| /samsung/.test(ua) || /symbian/.test(ua) 
				|| /sony/.test(ua) || /SCH-/.test(ua) || /SPH-/.test(ua)) {
			return 'mobile';
		}
		return 'pc';
	}());
}

if('datepicker' in $) {
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: '주차',
		dateFormat: 'yy-mm-dd',
		altFormat:  'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		showOtherMonths: true,
		selectOtherMonths: true,
		yearSuffix: '년 ',
		changeYear: true,
		changeMonth: true,
		showButtonPanel: true,
		gotoCurrent : true,
		currentText: '오늘',
		closeText: '닫기'
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	// custom
	$.datepicker._updateDatepicker_original = $.datepicker._updateDatepicker;
	$.datepicker._updateDatepicker = function(inst) {
		$.datepicker._updateDatepicker_original(inst);

		var afterShow = this._get( inst, 'afterShow' );
		if (afterShow) {
			afterShow.apply( inst.input, [ inst.input, inst ]);  // trigger custom callback
		}
	};
	$.datepicker._gotoToday = function(id) {
		var date,
			target = $(id),
			inst = this._getInst(target[0]);

		date = new Date();
		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();

		this._notifyChange(inst);
		this._adjustDate(target);
	};
}

if('spinner' in $.ui) {
	$.widget( "ui.suf_spinner", $.ui.spinner, {
		_format: function( value ) {
			if ( value === "" ) {
				return "";
			}
			
			if(("suffix" in this.options) && this.options.suffix != null) {
				return value + this.options.suffix;
			} 
			
			return window.Globalize && this.options.numberFormat ?
				Globalize.format( value, this.options.numberFormat, this.options.culture ) :
				value;
		},
		_parse: function( val ) {
			if ( typeof val === "string" && val !== "" ) {
				if(("suffix" in this.options) && this.options.suffix != null) {
					var _val = new String(val);
					return +(_val.substring(0, _val.length - this.options.suffix.length));
				}
				
				val = window.Globalize && this.options.numberFormat ?
					Globalize.parseFloat( val, 10, this.options.culture ) : +val;
			}
			
			return val === "" || isNaN( val ) ? null : val;
		}
	});
}


/**
 * 필드 기본 액션 설정
 * @param selector 선택 엘리먼트 하위 필드만 적용
 */
function initField(selector) {
	if(selector == null) selector = document;
	
	// 숫자필드
	$(selector).find("input[type='text'].numeric").bind("keyup blur", function() {
		var v = $(this).val().replace(/[^0-9]+/g,"");
		if(v != $(this).val()) $(this).val(v);
	}).css({"imeMode":"disabled"});

	$(selector).find("input[type='text'].decimal").bind("keyup blur", function() {
		var v = $(this).val().replace(/[^0-9.-]+/g,"");
		if(v != $(this).val()) $(this).val(v);
	}).css({"imeMode":"disabled"});
	
	$(selector).find("input[type='text'].numcomma").bind("keyup blur", function() {
		var v = $(this).val().replace(/[^0-9]+/g,"");
		
		var regexp = /(^[+-]?\d+)(\d{3})/;
		while (regexp.test(v)) {
			v = v.replace(regexp, "$1,$2");
		}
		
		if(v != $(this).val()) $(this).val(v);
	}).css({"imeMode":"disabled"});
	
	// 입력불가 필드
	$(selector).find("input[readonly='readonly'],input[readonly]").css({
		"cursor":"basic",
		//"background-color":"#f5f5f5"
		//"border":"1px solid"
	}).bind("keydown", function(event) {
		if ( event.which == 8 ) {
			 event.preventDefault();
		}
	});
	
	if('datepicker' in $.ui) {
		$(selector).find("input[type='text'].datepicker").addClass("readonly").datepicker({
			beforeShow: function(input, inst) {
				input = input.target || input;

				var max = $(input).data("datepicker-max");
				var min = $(input).data("datepicker-min");
				if(max) $( input ).datepicker('option', 'maxDate', max);
				if(min) $( input ).datepicker('option', 'minDate', min);
			},
			afterShow : function(input, inst) {	
				$(inst.dpDiv).find("td.ui-state-current > a").addClass("ui-state-hover");
			}
		}).each(function() {
			var _ele = this;
			if($(_ele).data("next-btn") == true) {
				$(_ele).next("input[type='image'], img").click(function() {
					$(_ele).focus();
				}).css("cursor", "pointer");
			}
		});
	}

	if('button' in $.ui) {
		$(selector).find("input[type=button].button,input[type=submit].button,a.button").button();
	}
}

/**
 * 주간단위 캘린더 생성
 * @param selector start date ~ end date
 * @param firstDay 0~1 (일~월)
 * @param addWeek null or number 선택 제한
 */
function makeCalendarWeek( selector, firstDay, addWeek, selector2, selector3)
{
	if(firstDay < 0 || firstDay > 6) firstDay = 0;
	//if(addWeek == null) addWeek = 0;

	$( selector ).each(function(idx, obj) {
		var sel_date = "";
		var term = $(obj).val();
		if(term != null && $.trim(term) != "") {
			var t = term.split("~");
			if(t.length >= 2) {
				sel_date = $.trim(t[1]);
			}
		}

		var target = $('<input type="text" value="' + sel_date + '"/>').width($(obj).width());
		$(obj).before(target).bind("click focus", function(e) {
			$(target).focus();
		}).prop("readonly", true).addClass("ui-custom-weeksel");
		
		// 필요시 아래 클래스로 CSS수정
		target.prop("readonly", true).addClass("ui-custom-weeksel-hidden").css({
			"padding"	: $(obj).css("padding"), 
			"font-size"	: $(obj).css("font-size"), 
			"border" 	: $(obj).css("border"), 
			"line-height" : $(obj).css("line-height"), 
			"margin" : $(obj).css("margin"),
			"height" : $(obj).css("height")
		});

		target.css("margin-right", ((target.outerWidth() + parseInt($(obj).css("margin-left"))) * -1));

		$(target).datepicker({
			dateFormat: 'yy-mm-dd',
			altFormat:  'yy-mm-dd',
			showButtonPanel: true,
			closeText: '닫기',
			gotoCurrent : true,
			currentText : '오늘',
			beforeShowDay: function(date) {
				var format = $(target).datepicker( "option", "dateFormat" );
				var d = $.datepicker.formatDate(format, date);

				var term = $(obj).val();
				if(term != null && $.trim(term) != "") {
					var t = term.split("~");

					if(t.length >= 2 && $.trim(t[0]) <= d && d <= $.trim(t[1])) {
						return [true, "ui-state-week-current", ""];
					}
				}

				return [true, "", ""];
			},
			beforeShow: function(input, inst) {
				input = input.target || input;

				var max = $(obj).data("datepicker-max");
				var min = $(obj).data("datepicker-min");
				if(max) $( input ).datepicker('option', 'maxDate', max);
				if(min) $( input ).datepicker('option', 'minDate', min);

				if(addWeek != '' && addWeek != false && addWeek != null) {
					var d = new Date();
					var dn = d.getDay();
					var t = d.getTime();
					dn = dn - firstDay < 0 ? dn - firstDay + 7 : dn - firstDay;
					t = t - (86400000 * dn) + 518400000;
					t = t + addWeek * 7 * 86400000; 
					d.setTime(t);

					$( input ).datepicker('option', 'maxDate', d);
				}
			},
			afterShow: function(input, inst) {
				input = input.target || input;

				$(inst.dpDiv).find("td > a").hover(function() {
					var format = $(target).datepicker( "option", "dateFormat" );
					$(inst.dpDiv).find("td > a").removeClass("ui-state-week-active");

					var y = $(this).parent().data("year");
					var m = $(this).parent().data("month") + 1;
					var d = $(this).text();
					var date = $.datepicker.parseDate(format, y + "-" + m + "-" + d);
					var dn = date.getDay();

					var a_list = $(inst.dpDiv).find("td > a");
					var idx = a_list.index(this);
	
					var s_idx = dn - firstDay < 0 ? idx - (dn - firstDay + 7) : idx - (dn - firstDay);
					var e_idx = s_idx+7;

					if(s_idx < 0) s_idx = 0;
					if(e_idx > a_list.length) e_idx = a_list.length;

					$(a_list).slice(s_idx, e_idx).addClass("ui-state-week-hover");
				}, function() {
					$(inst.dpDiv).find("td > a").removeClass("ui-state-week-hover");
					$(inst.dpDiv).find("td.ui-state-week-current > a").addClass("ui-state-week-active");
				});
				$(".ui-state-active").removeClass("ui-state-active");
				$(".ui-state-hover").removeClass("ui-state-hover");
				$(inst.dpDiv).find("td.ui-state-week-current > a").addClass("ui-state-week-active ui-state-week-hover");
			},
			onSelect: function(dateText, inst) {
				var format = $(target).datepicker( "option", "dateFormat" );

				var d = $.datepicker.parseDate(format, dateText);
				var dn = d.getDay();
				dn = dn - firstDay < 0 ? dn - firstDay + 7 : dn - firstDay;

				d.setTime(d.getTime() - 86400000 * dn);
				var sdate = $.datepicker.formatDate(format, d);

				d.setTime(d.getTime() + 518400000);
				var edate = $.datepicker.formatDate(format, d);

				$(obj).val(sdate + "~" + edate).trigger("change");
				
				if(selector2 != null) $(selector2).val(sdate);
				if(selector3 != null) $(selector3).val(edate);				
			}
		});
	});	
}
/**
 * 주간단위 캘린더 생성
 * @param selector1 start date field
 * @param selector2 end date field
 * @param firstDay 0~1 (일~월)
 * @param addWeek null or number 선택 제한
 * @param selector3 output field
 */
function makeCalendarWeek2( selector1, selector2, firstDay, addWeek, selector3)
{
	if(firstDay < 0 || firstDay > 6) firstDay = 0;
	//if(addWeek == null) addWeek = 0;

	var sel_date = "";
	var term = $(selector1).val();
	if(term != null && $.trim(term) != "") {
		var t = term.split("~");
		if(t.length >= 2) {
			sel_date = $.trim(t[1]);
		}
	}
	
	$(selector1 + ", " + selector2).datepicker({
		dateFormat: 'yy-mm-dd',
		altFormat:  'yy-mm-dd',
		showButtonPanel: true,
		closeText: '닫기',
		gotoCurrent : true,
		currentText : '이번주',
		beforeShowDay: function(date) {
			var format = $(selector1).datepicker( "option", "dateFormat" );
			var d = $.datepicker.formatDate(format, date);

			var term_s = $(selector1).val();
			var term_e = $(selector2).val();
			
			if(term_s != "" && term_e != "" && $.trim(term_s) <= d && d <= $.trim(term_e)) {
				return [true, "ui-state-week-current", ""];
			}

			return [true, "", ""];
		},
		beforeShow: function(input, inst) {
			input = input.target || input;

			var max = $(input).data("datepicker-max");
			var min = $(input).data("datepicker-min");
			if(max) $( input ).datepicker('option', 'maxDate', max);
			if(min) $( input ).datepicker('option', 'minDate', min);

			if(addWeek != '' && addWeek != false && addWeek != null) {
				var d = new Date();
				var dn = d.getDay();
				var t = d.getTime();
				dn = dn - firstDay < 0 ? dn - firstDay + 7 : dn - firstDay;
				t = t - (86400000 * dn) + 518400000;
				t = t + addWeek * 7 * 86400000; 
				d.setTime(t);

				$( input ).datepicker('option', 'maxDate', d);
			}
		},
		afterShow: function(input, inst) {
			input = input.target || input;

			$(inst.dpDiv).find("td > a").hover(function() {
				var format = $(selector1).datepicker( "option", "dateFormat" );
				$(inst.dpDiv).find("td > a").removeClass("ui-state-week-active");

				var y = $(this).parent().data("year");
				var m = $(this).parent().data("month") + 1;
				var d = $(this).text();
				var date = $.datepicker.parseDate(format, y + "-" + m + "-" + d);
				var dn = date.getDay();

				var a_list = $(inst.dpDiv).find("td > a");
				var idx = a_list.index(this);

				var s_idx = dn - firstDay < 0 ? idx - (dn - firstDay + 7) : idx - (dn - firstDay);
				var e_idx = s_idx+7;

				if(s_idx < 0) s_idx = 0;
				if(e_idx > a_list.length) e_idx = a_list.length;

				$(a_list).slice(s_idx, e_idx).addClass("ui-state-week-hover");
			}, function() {
				$(inst.dpDiv).find("td > a").removeClass("ui-state-week-hover");
				$(inst.dpDiv).find("td.ui-state-week-current > a").addClass("ui-state-week-active");
			});
			$(".ui-state-active").removeClass("ui-state-active");
			$(".ui-state-hover").removeClass("ui-state-hover");
			$(inst.dpDiv).find("td.ui-state-week-current > a").addClass("ui-state-week-active ui-state-week-hover");
		},
		onSelect: function(dateText, inst) {
			var format = $(selector1).datepicker( "option", "dateFormat" );

			var d = $.datepicker.parseDate(format, dateText);
			var dn = d.getDay();
			dn = dn - firstDay < 0 ? dn - firstDay + 7 : dn - firstDay;

			d.setTime(d.getTime() - 86400000 * dn);
			var sdate = $.datepicker.formatDate(format, d);

			d.setTime(d.getTime() + 518400000);
			var edate = $.datepicker.formatDate(format, d);

			$(selector1).datepicker( "setDate", sdate ).trigger("change");
			$(selector2).datepicker( "setDate", edate ).trigger("change");

			if(selector3 != null) {
				$(selector3).val(sdate + "~" + edate);
			}

			$(this).blur();
		}
	});
}

//날짜 기간 설정
function makeTermFromDate(baseDate, type, cnt) {
	var rs = makeTermFromDateToObject(baseDate, type, cnt);
	var startDate = rs[0];
	var endDate = rs[1];
	
	startDate = startDate.getFullYear() + "-" + padZero(startDate.getMonth()+1) + "-" + padZero(startDate.getDate());
	endDate = endDate.getFullYear() + "-" + padZero(endDate.getMonth()+1) + "-" + padZero(endDate.getDate());

	// [s, e]
	return [ startDate, endDate ];
}

//날짜 기간 설정 (Object)
function makeTermFromDateToObject(baseDate, type, cnt) {
	var y, m, d, baseDateStr;
	
	if ( baseDate == "" ) {
		baseDate = new Date();
	} else if (typeof(baseDate) == 'object'){ 	

	} else {		
		baseDate = new Date(Date.parse(baseDate));
	}
	
	y = baseDate.getFullYear();
	m = baseDate.getMonth();
	d = baseDate.getDate();
	baseDateStr = y + "-" + padZero(m+1) + "-" + padZero(d);
	
	// cnt만큼 주단위로 움직여 월요일~일요일 날짜 반환
	// cnt만큼 주단위로 움직여 월요일~일요일 날짜 반환
	if(/WT[0-6]?/.test(type)) {
		var cday = parseInt(type.slice(-1)); // 월요일
		if(isNaN(cday)) cday = 1;

		var t = (-6 - baseDate.getDay() + cday -1) % 7;
		t += cnt * 7;

    	startDate = new Date(baseDate.getFullYear(), baseDate.getMonth(), baseDate.getDate() + t);
    	endDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 6);

		// JSON.stringify 주의 ISO String
    	return [startDate, endDate];
	}
	
	if(cnt == 0) {
		return [baseDate, baseDate];
	}
	
	var date = new Date();
	var startDate = baseDate;
	var endDate = baseDate;

	var did = 1;
	if(cnt > 0) did = -1;
	
	if(type == "D") {
		date = new Date(y, m, d + cnt + did);
	} else if(type == "M") {
		// 월말처리
		if(cnt < 0) {
			var ldate = new Date(y, m + cnt + did, 0);
			if(d > ldate.getDate()) d = ldate.getDate();

			var fdate = new Date(y, m + 1, 0);
			if(d == fdate.getDate()) d = ldate.getDate();
		}

		date = new Date(y, m + cnt, d + did);
	} else if(type == "W") {
		date =  new Date(y, m, d + (cnt * 7) + did);
	} else if(type == "Y") {
		// 월말처리
		if(cnt < 0) {
			var ldate = new Date(y + cnt, m + did, 0);
			if(d > ldate.getDate()) d = ldate.getDate();

			var fdate = new Date(y, m + did, 0);
			if(cnt < 0 && d == fdate.getDate()) d = ldate.getDate();
		}

		date =  new Date(y + cnt, m, d + did);
	} 
	
	if(cnt > 0) endDate = date;
	else startDate = date;
	
	// [s, e]
	return [ startDate, endDate ];
}

// 기간 생성해서 input or selectbox 에 표시
function makeTermSetField( type, cType, cnt, startObjId, endObjId ) {
	cnt = cnt * -1;
	if(type == "DAY") {
		var date = $("#" + endObjId).val();
		var rs = makeTermFromDate(date, cType, cnt);

		$("#" + startObjId).val(rs[0]);
	}
	else if(type == "MONTH") {
		if(typeof(startObjId) != "object" || startObjId.length < 2 
				|| typeof(endObjId) != "object" || endObjId.length < 2) {
			return;
		}
		
		var y = parseInt($("#" + endObjId[0]).val(), 10);
		var m = parseInt($("#" + endObjId[1]).val(), 10) - 1;
		
		var date = new Date(y, m, 1);
		var rs = makeTermFromDateObject(date, cType, cnt);
		date = rs[0];
		
		// [20131218 cocktial]jQuery Option 변경 방식 변경
		$("#" + startObjId[0] + " option:contains('" + date.getFullYear() + "')").prop("selected", true);
		$("#" + startObjId[1] + " option:contains('" + padZero(date.getMonth()+1) + "')").prop("selected", true);		
	}
	else if(type == "YEAR") {
		var y =  parseInt($("#" + endObjId).val(), 10);
		$("#" + startObjId + " option:contains('" + parseInt(y-cnt) + "')").prop("selected", true);
		$("#" + startObjId).val(parseInt(y-cnt));
	}
}

/**
 * 
 * @param num
 * @param z
 * @returns
 */
function padZero(num, z) {
	z = z || 2;	
	return (new Array(z).join('0') + num).slice(-z);
}

/**
 * 숫자에 콤마를 찍어준다.
 * @param val
 * @returns {*}
 */
function numberWithComma(x){
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/**
 * Math.round
 */
function round(num, z) {
	var zz = Math.pow(10, z);
	return Math.round(num * zz) / zz;
}

/**
 * 태그제거
 * @param val
 * @returns {*}
 */
function stripHTML(dirtyString) {
  return $("<div></div>").html(dirtyString).text();
}

/**
 * 숫자를 Tween 애니메이션으로 출력한다.
 * @param text_object
 * @param dataValue
 */
function showNumberTweenTransition(text_object, dataValue, postFix) {
	$('#' + text_object).data("value", dataValue);
	var needlePosMax = $('#' + text_object).data("value") * 10;
	d3.select("#" + text_object).transition().duration(1200).attrTween("class", function() {
		return function(t) {
			$("#" + text_object).text(numberWithComma(Math.round(Math.ceil(t * needlePosMax)/10)) + postFix);
		};
	});
}

/**
 * 배열에 중복된 값은 입력하지 않는다.
 *
 * @param arrayObject
 * @param item
 * @returns {*}
 */
function pushAtomicArray(arrayObject, item) {
	for(var i=0; i<arrayObject.length;i++){
		if(arrayObject[i] == item){
			return arrayObject;
		}
	}
	arrayObject.push(item);
	return arrayObject;
}

/**
 * key/value object 에 중복된 키는 넣지 않는다.
 * @param arrayObject
 * @param key
 * @param value
 * @param is_accumulation	누적모드
 * @returns {*}
 */
function pushAtomicKey(object, key, value, is_accumulation) {

	var exists = false;

	if(key in object){
		if(!is_accumulation){
			return object;
		} else {
			exists = true;
		}
	}

	if(!is_accumulation) {
		object[key] = value;
	} else {
		if(exists){
			if(typeof(object[key]) != "object"){
				var tmp = object[key];
				object[key] = [];
				object[key].push(tmp);
			}
			object[key].push(value);
		} else {
			object[key] = value;
		}
	}

	return object;
}

/**
 * json Data를 피벗하여 리턴한다.
 *
 * @param jsonObject
 * @param column
 * @param is_accumulation	중복값을 누적한다.
 * @returns {Array}
 */
function jsonPivot(jsonObject, column, is_accumulation) {
	var dataset = [];
	var resultSet = [];

	if(is_accumulation == undefined) {
		is_accumulation = false;
	}

	$.each(jsonObject, function(i, row){
		$.each(row, function(j, val) {
			if(j == column) {
				pushAtomicKey(dataset, val, [], false);
			}
		});
		$.each(row, function(j, val) {
			pushAtomicKey(dataset[eval('row.' + column)], j, val, is_accumulation);
		});
	});

	for(key in dataset) {
		resultSet.push(dataset[key]);
	}

	return resultSet;
}



/******************************************************************************
 * 쿠키
 ******************************************************************************/
function getCookieVal(offset) {
    var endstr = document.cookie.indexOf (";", offset);
    if (endstr == -1) {
        endstr = document.cookie.length;
    }
    value = document.cookie.substring(offset, endstr);
    if(value == 'null' || value == '' || value == null) return null;
    else return unescape(value);
}

function getCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;

    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg) {
            return getCookieVal (j);
        }
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break;
    }
    return null;
}

function setCookie(name, value) {
    var argv = setCookie.arguments;
    var argc = setCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : "/";
    var domain = (argc > 4) ? argv[4] : null;
    var secure = (argc > 5) ? argv[5] : false;

	if(typeof(expires) != "object") {
		var todayDate = new Date();
		todayDate.setDate( todayDate.getDate() + expires );
		expires = todayDate;
	}

    document.cookie = name + "=" + escape (value) +
        ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
        ((path == null) ? "" : ("; path=" + path)) +
        ((domain == null) ? "" : ("; domain=" + domain)) +
        ((secure == true) ? "; secure" : "");
}

function deleteCookie(name) {
    var exp = new Date();
    exp.setTime (exp.getTime() - 1);
    var cval = getCookie (name);
    if (cval != null) {
		setCookie(name, '', exp, '/');
    }
}


/******************************************************************************
 * 배열을 csv로 파일 저장
 ******************************************************************************/
function arrayExportToCsv(filename, rows) {
    var processRow = function (row) {
        var finalVal = '';
        for (var j = 0; j < row.length; j++) {
            var innerValue = (row[j] === null || typeof row[j] === 'undefined') ? '' : row[j].toString();
            if (row[j] instanceof Date) {
                innerValue = row[j].toLocaleString();
            };
            var result = innerValue.replace(/"/g, '""');
            if (result.search(/("|,|\n)/g) >= 0)
                result = '"' + result + '"';
            if (j > 0)
                finalVal += ',';
            finalVal += result;
        }
        return finalVal + '\n';
    };

    var csvFile = '';
    for (var i = 0; i < rows.length; i++) {
    	console.log("row [", i, "] : ", rows[i]);
        csvFile += processRow(rows[i]);
    }


    var blob = new Blob(['\ufeff' + csvFile], { type: 'text/csv;charset=utf-8;' });
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, filename);
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { // feature detection
            // Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
}


/******************************************************************************
 * 데이터셋
 ******************************************************************************/
function get_ana_table_stat(str) {
	var ret = "";
	switch(str) {
		case "UP":
			ret = "U";
			break;
		
		case "STA":
			ret = "U";
			break;
			
		case "MGE":
			ret = "U";
			break;
			
		default :
			ret = "H";		
	}
	
	return ret;
}

/******************************************************************************
 * AJAX
 ******************************************************************************/
function ajaxErrorPrint(xhr, status, thrown) {
	var msg = "Sorry but there was an error: \n";
	alert(msg + xhr.status + " " + xhr.statusText);
}
function loadErrorPrint(xhr, status, thrown) {
	if (status == "error") {
		ajaxErrorPrint(xhr, status, thrown);
		return false;
	}
	else if(xhr == "error") {
		alert("정상적으로 처리되지 않았습니다.");
		return false;
	}
	else if(xhr == "error_input") {
		alert("필요한 값이 정상적으로 입력되지 않았습니다.");
		return false;
	}
	else if(xhr == "error_insert") {
		alert("등록할 수 없습니다. \n잠시후 다시 시도해 주십시오.");
		return false;
	}
	else if(xhr == "error_delete") {
		alert("삭제할 수 없습니다. \n잠시후 다시 시도해 주십시오.");
		return false;
	}
	else if(xhr == "error_update") {
		alert("수정할 수 없습니다. \n잠시후 다시 시도해 주십시오.");
		return false;
	}
	else if(xhr == "error_nodata") {
		alert("선택된 데이터가 없습니다.");
		return false;
	}
	else if(xhr == "error_dupl") {
		alert("중복된 데이터입니다.");
		return false;
	}
	else if(xhr == "error_auth") {
		alert("권한이 없습니다.");
		return false;
	}
	else if(xhr.result == "error") {
		alert(xhr.errormsg);
		return false;
	}	
	return true;
}

function getFormatDate(date){
    var year = date.getFullYear();              //yyyy
    var month = (1 + date.getMonth());          //M
    month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
    var day = date.getDate();                   //d
    day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
    return  year + '' + month + '' + day;
}

