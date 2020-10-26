<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--
pageUnit = 2;
pageIndex = 12;
pageTotal = 100;
		
PageInfo
currentPageNo: 12
firstPageNo: 1
firstPageNoOnPageList: 11
firstRecordIndex: 22
lastPageNo: 50
lastPageNoOnPageList: 20
lastRecordIndex: 24
nextPageBlockNo: 30
nextPageNo: 21
pageSize: 10
pageList: (10) [20, 0, 0, 0, 0, 0, 0, 0, 0, 0]
prevPageBlockNo: 1
prevPageNo: 10
recordCountPerPage: 2
totalPageCount: 50
totalRecordCount: 100
--%>
<%--
<div>
	전체 {{= totalRecordCount}}건, {{= currentPageNo}} / {{= totalPageCount}} 페이지
</div>
--%>
{{if 'pageInfo' in $data}}
<div class="paging">
	<a href="#" class="first" data-page="{{= pageInfo.firstPageNo}}"><i class="fa fa-backward" aria-hidden="true"></i></a>
	<a href="#" class="prev" data-page="{{= pageInfo.prevPageNoOnPageList}}"><i class="fa fa-caret-left" aria-hidden="true"></i></a>
	{{each pageInfo.pageList}}
		{{if pageInfo.currentPageNo == $value}}
		<strong>{{= $value}}</strong>
		{{else}}
		<a href="#" data-page="{{= $value}}">{{= $value}}</a>
		{{/if}}
	{{/each}}
	<a href="#" class="next" data-page="{{= pageInfo.nextPageNoOnPageList}}"><i class="fa fa-caret-right" aria-hidden="true"></i></a>
	<a href="#" class="last" data-page="{{= pageInfo.lastPageNo}}"><i class="fa fa-forward" aria-hidden="true"></i></a>
</div>
{{/if}}