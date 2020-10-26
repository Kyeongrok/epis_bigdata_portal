<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>


<div class="wrap_admin">

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>데이터셋 관리</h3>

	<form name="datasetForm" id="datasetForm">
	<input type="hidden" name="mode" value="MOD" />
	<input type="hidden" name="dsId" id="dsId" value="${row.dsId}" />
	<input type="hidden" name="dlType" id="dlType" value="${row.dlType}" />
	<input type="hidden" name="dsStorageType" id="dsStorageType" value="${row.dsStorageType}" />
	<input type="hidden" name="dsDataType" id="dsDataType" value="${row.dsDataType}" />
	<input type="hidden" name="htblPartition" id="htblPartition" value="${row.htblPartition}" />
	<table class="tbl02 column2">
		<tr>
			<th>데이터 목록명<em>*</em></th>
			<td>
				<select name="dlId" id="bdpDataListSel"> 
				<c:forEach var="bdpDataList" items="${bdpDataList}" varStatus="status">
					<option value="${bdpDataList.dlId}" <c:if test="${bdpDataList.dlId eq row.dlId}">selected="selected"</c:if>><c:choose><c:when test="${bdpDataList.dlType == 'BDS'}">[빅데이터] </c:when><c:when test="${bdpDataList.dlType == 'STA'}">[통계] </c:when><c:when test="${bdpDataList.dlType == 'PRI'}">[민간] </c:when></c:choose>${bdpDataList.dlName}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<c:if test="${row.dlType != 'STA'}">
		<tr>
			<th>분류<em></em></th>
			<td>
				<input type="text" name="dsCateDept1" id="dsCateDept1" value="${row.dsCateDept1}" class="w10p" /> &gt; <input type="text" name="dsCateDept2" id="dsCateDept2" value="${row.dsCateDept2}" class="w10p"/>
			</td>
		</tr>
		</c:if>
		<tr>
			<th>데이터셋명 <em>*</em></th>
			<td>
				<input type="text" name="dsName" id="dsName" value="${row.dsName}" class="w50p" />
			</td>
		</tr>
		<tr>
			<th>데이터셋 설명</th>
			<td>
				<textarea name="dsComment" id="dsComment" rows="3" cols="100">${row.dsComment}</textarea>
			</td>
		</tr>
		<tr>
			<th id="endPointNm"><c:if test="${row.dlType == 'BDS' or row.dlType == 'PRI'}">데이터셋 링크</c:if><c:if test="${row.dlType == 'STA'}">국가통계포털<br />링크</c:if></th>
			<td>
				<input type="text" name="dsEndPoint" id="dsEndPoint" value="${row.dsEndPoint}" class="w50p" />
			</td>
		</tr>		
		<tr>
			<th>데이터셋 개방<em></em></th>
			<td>
				<input type="checkbox" id="openRangeSource" name="openRangeSource" value="Y" <c:if test="${row.dsSourceOpen == 'Y'}">checked="checked"</c:if> /><label for="openRangeSource">원천<span></span></label>
				<input type="checkbox" id="openRangeAnalysis" name="openRangeAnalysis" value="Y" <c:if test="${row.dsAnalysisOpen == 'Y'}">checked="checked"</c:if> /><label for="openRangeAnalysis">분석<span></span></label>
				<input type="checkbox" id="openRangeOpen" name="openRangeOpen" value="Y" <c:if test="${row.dsDataOpen == 'Y'}">checked="checked"</c:if> /><label for="openRangeOpen">개방<span></span></label>				
			</td>
		</tr>
		<tr>
			<th>활성상태</th>
			<td>
				<select name="dsUseAt" class="dsUseAt">
					<option value="Y" <c:if test="${row.dsUseAt == 'Y'}">selected="selected"</c:if>>사용</option>
					<option value="N" <c:if test="${row.dsUseAt == 'N'}">selected="selected"</c:if>>사용안함</option>
					<option value="R" <c:if test="${row.dsUseAt == 'R'}">selected="selected"</c:if>>보류</option>
				</select>
			</td>
		</tr>
	</table>
	</form>

	<div class="tac mt20 disinblock w100p">
		<a href="<c:url value='/bdp/admin/dataset/list.do?pageIndex=${pageIndex}'/>" class="btn floatl"><spring:message code="button.list" /></a>

		<div class="floatr">
			<a href="#" id="datasetUpdateBtn" class="btn red">수정</a>			
		</div>
	</div>

</div>

<script type="text/javascript">
$(function(){
	// 데이터목록 정보
	$("#bdpDataListSel").change(function() {
		var dlId = $(this).val();
		$.getJSON("./getJsonDataList.do", {"dlId": dlId}, function(json) {
			var data = json.bdpDataList;
			// 데이터목록의 기본 디폴트 값을 정의
			if($("#dsCateDept1").val() == "") {
				$("#dsCateDept1").val(data.dlCateDept1);
				$("#dsCateDept2").val(data.dlCateDept2);
			}
			
			$("input[name=dlType]").val(data.dlType);
			if(data.dlType == "BDS" || data.dlType == "PRI") {
				$("#dsStorageType").val("HIVE");
				$("#dsDataType").val("TABLE");
				$("#htblPartition").val("TBL_ID={TBL_ID}/PRD_DE={PRD_DE}");
				
				$("#endPointNm").text("데이터셋 링크");
			} else if(data.dlType == "STA") {
				$("#dsStorageType").val("HDFS");
				$("#dsDataType").val("CSV");
				$("#htblPartition").val("");
				
				$("#endPointNm").text("국가통계포털 링크");
			}
			
			return false;
		});
	});

	<c:if test="${row.dlType != 'STA'}">
	if("${row.dsCateDept1}" == "") {
		$("#bdpDataListSel").trigger("change");
	}
	</c:if>
	

	$("#datasetUpdateBtn").click(function(){
		var formData = $("#datasetForm").serialize();
		var send_ok = false;
		
		if($("#dsName").val() == "") {
			alert("데이터셋명을 입력하세요.");
			return false;
		}
		if($("#dsComment").val() == "") {
			alert("데이터셋 설명을 입력하세요.");
			return false;
		}
		
		send_ok = confirm("수정을 완료하시겠습니까?");
		if(send_ok == false) {
			return false;
		}
		
		$.ajax({
			url : "./datasetModify.do",
			data : formData,
			type : "POST",
			dataType : "JSON",
			success : function(json) {
				if(json.result > 0) {
					alert("수정되었습니다.");
					location.reload();
				} else {
					alert('데이터를 수정하지 못했습니다.');
				}
			},
			error : function() {
				alert('데이터 통신 오류!!');
			}
			
		});
		return false;
	});
	
});
</script>