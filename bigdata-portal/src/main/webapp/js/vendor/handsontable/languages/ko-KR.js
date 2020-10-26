(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory(require("../../handsontable"));
	else if(typeof define === 'function' && define.amd)
		define(["../../handsontable"], factory);
	else {
		var a = typeof exports === 'object' ? factory(require("../../handsontable")) : factory(root["Handsontable"]);
		for(var i in a) (typeof exports === 'object' ? exports : root)[i] = a[i];
	}
})(typeof self !== 'undefined' ? self : this, function(__WEBPACK_EXTERNAL_MODULE_0__) {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 3);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

module.exports = __WEBPACK_EXTERNAL_MODULE_0__;

/***/ }),
/* 1 */,
/* 2 */,
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.__esModule = true;

var _dictionary;

var _handsontable = __webpack_require__(0);

var _handsontable2 = _interopRequireDefault(_handsontable);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; } /**
                                                                                                                                                                                                                   * @preserve
                                                                                                                                                                                                                   * Authors: Handsoncode
                                                                                                                                                                                                                   * Last updated: Nov 15, 2017
                                                                                                                                                                                                                   *
                                                                                                                                                                                                                   * Description: Definition file for English - United States language-country.
                                                                                                                                                                                                                   */


var C = _handsontable2.default.languages.dictionaryKeys;

var dictionary = (_dictionary = {
  languageCode: 'ko-KR'
}, 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ROW_ABOVE, '위에 행 삽입'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ROW_BELOW, '아래에 행 삽입'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_INSERT_LEFT, '왼쪽에 열 삽입'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_INSERT_RIGHT, '오른쪽에 열 삽입'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_ROW, ['행 삭제', '여러 행 삭제']), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_COLUMN, ['열 삭제', '여러 열 삭제']), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_UNDO, '되돌리기'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REDO, '다시실행'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_READ_ONLY, '읽기 전용으로'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_CLEAR_COLUMN, '열 내용 지우기'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT, '정렬'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_LEFT, '왼쪽 정렬'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_CENTER, '중앙 정렬'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_RIGHT, '오른쪽 정렬'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_JUSTIFY, '양쪽 정렬'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_TOP, '위쪽 맞춤'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_MIDDLE, '가운데 맞춤'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_BOTTOM, '아래쪽 맞춤'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_FREEZE_COLUMN, '열 고정'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_UNFREEZE_COLUMN, '열 고정 취소'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS, '테두리'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_TOP, '위'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_RIGHT, '오른쪽'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_BOTTOM, '아래'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_LEFT, '왼쪽'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_BORDERS, '테두리 제거'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ADD_COMMENT, '메모'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_EDIT_COMMENT, '메모 수정'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_COMMENT, '메모 삭제'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_READ_ONLY_COMMENT, '메모를 읽기전용으로'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_MERGE_CELLS, '셀 병합'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_UNMERGE_CELLS, '셀 병합 취소'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_COPY, '복사'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_CUT, '잘라내기'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_NESTED_ROWS_INSERT_CHILD, '하위 행 삽입'), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_NESTED_ROWS_DETACH_CHILD, '상위 행에서 분리'),
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_HIDE_COLUMN, ['열 숨김', '열 숨김']), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_SHOW_COLUMN, ['열 표시', '열 표시']), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_HIDE_ROW, ['행 숨김', '행 숨김']), 
	_defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_SHOW_ROW, ['행 표시', '행 표시']), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_NONE, '없음'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_EMPTY, '공백'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_EMPTY, '비어 있지 않은'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_EQUAL, '같음'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_EQUAL, '다음과 같지 않음'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_BEGINS_WITH, '다음으로 시작'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_ENDS_WITH, '다음으로 끝나는'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_CONTAINS, '다음 포함'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_CONTAIN, '포함하지 않음'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_GREATER_THAN, '큼'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_GREATER_THAN_OR_EQUAL, '크거나 같음'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_LESS_THAN, '작음'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_LESS_THAN_OR_EQUAL, '작거나 같음'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_BETWEEN, '사이'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_BETWEEN, '다음 사이에 없는'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_AFTER, '다음 이후의 날짜'), 
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_BEFORE, '다음 이전 날짜'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_TODAY, '오늘'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_TOMORROW, '내일'),
	_defineProperty(_dictionary, C.FILTERS_CONDITIONS_YESTERDAY, '어제'),
	_defineProperty(_dictionary, C.FILTERS_VALUES_BLANK_CELLS, '빈 셀'),
	_defineProperty(_dictionary, C.FILTERS_DIVS_FILTER_BY_CONDITION, '조건에서 필터'),
	_defineProperty(_dictionary, C.FILTERS_DIVS_FILTER_BY_VALUE, '값 필터'),
	_defineProperty(_dictionary, C.FILTERS_LABELS_CONJUNCTION, 'And'),
	_defineProperty(_dictionary, C.FILTERS_LABELS_DISJUNCTION, 'Or'),
	_defineProperty(_dictionary, C.FILTERS_BUTTONS_SELECT_ALL, '모두 선택'),
	_defineProperty(_dictionary, C.FILTERS_BUTTONS_CLEAR, '지우기'),
	_defineProperty(_dictionary, C.FILTERS_BUTTONS_OK, '확인'),
	_defineProperty(_dictionary, C.FILTERS_BUTTONS_CANCEL, '취소'),
	_defineProperty(_dictionary, C.FILTERS_BUTTONS_PLACEHOLDER_SEARCH, '검색'),
	_defineProperty(_dictionary, C.FILTERS_BUTTONS_PLACEHOLDER_VALUE, '값'),
	_defineProperty(_dictionary, C.FILTERS_BUTTONS_PLACEHOLDER_SECOND_VALUE, '값2'), 
	_dictionary);

_handsontable2.default.languages.registerLanguageDictionary(dictionary);

exports.default = dictionary;

/***/ })
/******/ ])["___"];
});