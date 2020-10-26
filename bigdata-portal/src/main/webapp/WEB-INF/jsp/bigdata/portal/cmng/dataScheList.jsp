<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.form.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/js/vendor/spectrum/spectrum.js" />" charset="utf-8"></script>
<link rel="stylesheet" href="<c:url value="/js/vendor/spectrum/spectrum.css" />" type="text/css" />

<div class="tablearea">
	<button type="button" id="scheGuide">데이터 수집 주기 도움말</button>
	<table class="tbl01 adm_tbl01 column8" summary="데이터 수집 리스트입니다.">
		<thead>
			<tr>
				<th>번호</th>
				<th>수집 데이터 리스트</th>
				<th>수집 상태</th>
				<th>최초 수집 등록일</th>
				<th>마지막 수집 등록일</th>
				<th>데이터 수집 주기 <em>(시/일/월/ 요일)</em></th>
				<th>동작 여부</th>
				<th>설정</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${schedulerList}" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<c:if test="${row.dcsId == 'DCS_STA'}"><td><a href="<c:url value='/bdp/cmng/scheHistoryView.do'/>?dcsId=${row.dcsId}">통계데이터</a></td></c:if>
				<c:if test="${row.dcsId == 'DCS_PDL'}"><td><a href="<c:url value='/bdp/cmng/scheHistoryView.do'/>?dcsId=${row.dcsId}">공공데이터</a></td></c:if>
				<c:if test="${row.dcsId == 'DCS_BDS'}"><td><a href="<c:url value='/bdp/cmng/scheHistoryView.do'/>?dcsId=${row.dcsId}">빅데이터</a></td></c:if>
				<c:if test="${row.dcsId == 'DCS_TEST'}"><td><a href="<c:url value='/bdp/cmng/scheHistoryView.do'/>?dcsId=${row.dcsId}">수집simulation</a></td></c:if>
				<c:if test="${row.dcsCollectState == 'IDLE'}"><td>대기</td></c:if>
				<c:if test="${row.dcsCollectState == 'RUNNING'}"><td>실행중</td></c:if> 
				<c:if test="${row.dcsCollectState == 'ERROR'}"><td>오류발생</td></c:if>  
				<td>${row.dcsRegistDttm}</td>
				<td>${row.dcsLastCollectDttm}</td>
				
				<td id="sche_${row.dcsId}" style="display: table-cell">${row.dcsSchedule}</td>
				<td id="scheEdit_${row.dcsId}" style="display:none">
					<label>시 : </label>
					<input type="text" name="cron_1" value="${index}">
					<label>일 : </label>
					<input type="text" name="cron_2" value="${index}">
					<label>월 : </label>
					<input type="text" name="cron_3" value="${index}">
					<label>요일 : </label>
					<input type="text" name="cron_4" value="${index}">
					
					<%-- <label style="margin-left:10px"> 시 </label>
					<label style="margin-left:25px"> 일 </label>
					<label style="margin-left:27px"> 월 </label>
					<label style="margin-left:24px"> 요일 </label><br>
					<c:forEach var="index" items="${fn:split(row.dcsSchedule, ' ')}" varStatus="status">
						<input type="text" name="cron_${status.count}" value="${index}" size="2" style="text-align:center">
					</c:forEach> --%>
				</td>
				<td id="scheEnable_${row.dcsId}" style="display: table-cell">${row.dcsScheduleEnable}</td>
				<td id="scheEnableEdit_${row.dcsId}" style="display:none">
					<select id="scheEnableSelect_${row.dcsId}">
						<option value="${row.dcsScheduleEnable}" selected="selected">${row.dcsScheduleEnable}</option>
						<option value='N'>N</option>
						<option value='T'>T</option>
					</select>
				<td>
					<div class="btnarea">
						<a class="btn sm red w100p bdbox" id="${row.dcsId}_scheEditBtn" style="display:show">수정</a>
						<a class="btn sm red w100p bdbox" id="${row.dcsId}_scheSaveBtn" style="display:none">저장</a>
					</div>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div id="dialog_scheGuide" title="스케줄러 설정 가이드">	
	<table class="tbl02">
		<thead>
			<tr>
				<th>필드명</th>
				<th>허용값</th>
				<th>허용문자</th>
				<th>예시</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th>시 (Hours)</th>
				<td>0 ~ 23</td>
				<td>, - * /</td>
				<td rowspan="4">
					12 * * * : 매일 낮 12시에 실행<br>
					10 15 * * : 매달 15일 오전 10시에 실행<br>
					10 ? * MON-FRI : 주중 오전 10시에 실행<br>
					10 L * ? : 매월 말일 오전 10시에 실행
				</td>
			</tr>
			<tr>
				<th>일 (Day-of-Month)</th>
				<td>1 ~ 31</td>
				<td>, - * ? / L W</td>
				
			</tr>
			<tr>
				<th>월 (Month)</th>
				<td>1 ~ 12 or JAN ~ DEC</td>
				<td>, - * /</td>
				
			</tr>
			<tr>
				<th>요일 (Day-of-Week)</th>
				<td>0 ~ 6 or MON ~ SUN</td>
				<td>, - * ? / L #</td>
				
			</tr>
		</tbody>
	</table>
	<dl>
		<dt>*</dt><dd>:&nbsp; 모든 값을 뜻합니다.</dd>
		<dt>?</dt><dd>:&nbsp; 특정한 값이 없음을 뜻합니다.</dd>
		<dt>-</dt><dd>:&nbsp; 범위를 뜻합니다. (예) 월요일에서 수요일까지는 MON-WED로 표현</dd>
		<dt>,</dt><dd>:&nbsp; 특별한 값일 때만 동작 (예) 월,수,금 MON,WED,FRI</dd>
		<dt>/</dt><dd>:&nbsp; 시작시간 / 단위  (예) 0분부터 매 5분 0/5</dd>
		<dt>L</dt><dd>:&nbsp; 일에서 사용하면 마지막 일, 요일에서는 마지막 요일(토요일)</dd>
		<dt>W</dt><dd>:&nbsp; 가장 가까운 평일 (예) 15W는 15일에서 가장 가까운 평일 (월 ~ 금)을 찾음</dd>
		<dt>#</dt><dd>:&nbsp; 몇째주의 무슨 요일을 표현 (예) 3#2 : 2번째주 수요일</dd>
	</dl>
</div>

<script type="text/javascript">
$(function() {
	// 수집 주기 변경
	$("#DCS_STA_scheEditBtn, #DCS_PDL_scheEditBtn, #DCS_BDS_scheEditBtn, #DCS_TEST_scheEditBtn").click(function() {
		var id = $(this).attr('id');
		if(id =="DCS_TEST_scheEditBtn"){
			var dcsId = id.substring(0,8);	
		}else{
			var dcsId = id.substring(0,7);
		}
		
		
		$("#sche_"+dcsId).hide();
		$("#scheEdit_"+dcsId).show();
		$("#scheEnable_"+dcsId).hide();
		$("#scheEnableEdit_"+dcsId).show();
		
		if($("#scheEnableSelect_"+dcsId+" option:selected").val() == "Y"){
			var index = $("#scheEnableSelect_"+dcsId+" option").index($("#scheEnableSelect_"+dcsId+" option:selected"));
			if(index == 1)
				$("#scheEnableSelect_"+dcsId+" option:eq(0)").replaceWith("<option value='N'>N</option>");
			else
				$("#scheEnableSelect_"+dcsId+" option:eq(1)").replaceWith("<option value='N'>N</option>");
		}
		else if($("#scheEnableSelect_"+dcsId+" option:selected").val() == "N"){
			var index = $("#scheEnableSelect_"+dcsId+" option").index($("#scheEnableSelect_"+dcsId+" option:selected"));
			if(index == 1)
				$("#scheEnableSelect_"+dcsId+" option:eq(0)").replaceWith("<option value='Y'>Y</option>");
			else
				$("#scheEnableSelect_"+dcsId+" option:eq(1)").replaceWith("<option value='Y'>Y</option>");
		}
		
		$("#"+dcsId+"_scheEditBtn").hide();
		$("#"+dcsId+"_scheSaveBtn").show();
	});
	
	// 수집 동작 여부 변경
	$("#DCS_STA_scheSaveBtn, #DCS_PDL_scheSaveBtn, #DCS_BDS_scheSaveBtn, #DCS_TEST_scheSaveBtn").click(function() {
		var id = $(this).attr('id');
		if(id =="DCS_TEST_scheSaveBtn"){			
			var dcsId = id.substring(0,8);	
		}else{
			var dcsId = id.substring(0,7);
		}
		var isChange = false;
		var isValid = true;

		$("#sche_"+dcsId).show();
		$("#scheEdit_"+dcsId).hide();
		$("#scheEnable_"+dcsId).show();
		$("#scheEnableEdit_"+dcsId).hide();
		$("#"+dcsId+"_scheEditBtn").show();
		$("#"+dcsId+"_scheSaveBtn").hide();
		
		// 값 가져오기(수집 주기)
		var cronHour = $("#scheEdit_"+dcsId+" input[name=cron_1]").val();
		var cronDay = $("#scheEdit_"+dcsId+" input[name=cron_2]").val();
		var cronMonth = $("#scheEdit_"+dcsId+" input[name=cron_3]").val();
		var cronDayofweek = $("#scheEdit_"+dcsId+" input[name=cron_4]").val();
		var cronString = cronHour + " " + cronDay + " " + cronMonth + " " + cronDayofweek;
		console.log("cronString : " + cronString);
		
		// 값 가져오기(동작 여부)
		var scheEnable = $("#scheEnableSelect_"+dcsId+" option:selected").val();
		var text = $("#scheEnable_"+dcsId).html();
		
		if($("#scheEnable_"+dcsId).html() != scheEnable) {
			if(scheEnable == 'Y') {
				$("#scheEnableSelect_"+dcsId+" option:eq(0)").replaceWith("<option value='Y' selected='selected'>Y</option>");
				$("#scheEnableSelect_"+dcsId+" option:eq(1)").replaceWith("<option value='N'>N</option>");
			}
			else {
				$("#scheEnableSelect_"+dcsId+" option:eq(0)").replaceWith("<option value='N' selected='selected'>N</option>");
				$("#scheEnableSelect_"+dcsId+" option:eq(1)").replaceWith("<option value='Y'>Y</option>");
			}
		}
		
		// 변경 값이 없으면 DB 업데이트 skip
		if(($("#sche_"+dcsId).html() != cronString) || ($("#scheEnable_"+dcsId).html() != scheEnable))
			isChange = true;
		
		// 허용 문자 체크
		var strRegxCaseHour = /[^0-9,\-\*/]/g;
		var strRegxCaseDay = /[^0-9,\?\-\*/LW]/g;
		var strRegxCaseMonth = /[^0-9A-Z,\-\*/]/g;
		var strRegxCaseWeek = /[^0-9A-Z,\?\-\*/#]/g;
		
		if(new RegExp(strRegxCaseHour).test(cronHour) ||
		   new RegExp(strRegxCaseDay).test(cronDay) ||
		   new RegExp(strRegxCaseMonth).test(cronMonth) ||
		   new RegExp(strRegxCaseWeek).test(cronDayofweek)) {
			console.log("no valid character.");
			isValid = false;
		}
		
		// 수집 주기 입력 값 체크
		if(cronHour.length > 0 && cronDay.length > 0 && cronMonth.length > 0 && cronDayofweek.length > 0) {
			if(isValid) {
				$("#sche_"+dcsId).html(cronString);
			}
			else {
				alert('허용 가능한 문자가 포함되어 있습니다. 다시 확인해 주시기 바랍니다.');
				return;
			}
		}
		else {
			alert('입력 값이 없습니다. 다시 입력하시기 바랍니다.');
			return;
		}
		
		// 변경값 업데이트
		if($("#scheEnable_"+dcsId).html() != scheEnable)
			$("#scheEnable_"+dcsId).html(scheEnable);
		
		// 설정 값 DB 업데이트
		if(isChange) {
			$.ajax({
				url : "./scheManagerUpdateData.do",
				data : {dcsId:dcsId, cronString:cronString, scheEnable:scheEnable},
				type : "POST",
				dataType : "JSON",
				async : false,
				timeout : 5000,
				success : function(json) {
					var result = json.result;
					console.log("result : " + result);
				}, error : function() {
					alert('데이터 업데이트 과정에서 오류가 발생하였습니다.');
				}
			});
		}
	});
	
	// 스케줄러 설정 도움말 layerpopup
	$("#dialog_scheGuide").dialog({
		autoOpen: false,
		resizable: false,
		height: "auto",
		width: 700,
		modal: true,
		buttons: {
			"확인": function() {
				$(this).dialog("close");
			}
		}
	});
	
	// 스케줄 설정 도움말
	$("#scheGuide").click(function() {
		$("#dialog_scheGuide").dialog("open");
		
        return false;
	});
});
</script>