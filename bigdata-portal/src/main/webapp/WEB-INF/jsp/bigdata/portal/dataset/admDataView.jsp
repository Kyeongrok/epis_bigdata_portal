<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>


<div class="wrap_admin">

	<h3 class="mt0"><i class="ico ico_arrow_grey"></i>데이터목록 관리</h3>

	<form name="datasetForm" id="datasetForm">
	<input type="hidden" name="mode" value="MOD" />
	<input type="hidden" name="dlId" id="dsId" value="${row.dlId}" />
	<table class="tbl02 column3">
		<tr>
			<th>데이터 목록명 <em>*</em></th>
			<td>
				<input type="text" name="dlName" id="dlName" value="${row.dlName}" class="w50p"/>
			</td>
		</tr>
		<tr>
			<th>분류<em></em></th>
			<td>
				<input type="text" name="dlCateDept1" id="dlCateDept1" value="${row.dlCateDept1}" class="w10p" /> &gt; <input type="text" name="dlCateDept2" id="dlCateDept2" value="${row.dlCateDept2}" class="w10p"/>
			</td>
		</tr>
		<tr>
			<th>데이터 목록 설명 <em>*</em></th>
			<td>
				<textarea name="dlComment" id="dlComment" rows="3" cols="100">${row.dlComment}</textarea>
			</td>
		</tr>
		<tr>
			<th>업무 담당자 부서</th>
			<td>
				<input type="text" name="cpDept" id="cpDept" value="${row.cpDept}" />
			</td>
		</tr>
		<tr>
			<th>업무 담당자 이름</th>
			<td>
				<input type="text" name="cpNm" id="cpNm" value="${row.cpNm}" />
			</td>
		</tr>
		<tr>
			<th>업무 담당자 전화번호</th>
			<td>
				<input type="text" name="cpTel" id="cpTel" value="${row.cpTel}" />
			</td>
		</tr>
		<tr>
			<th>제공기관</th>
			<td>
				<select name="orgId" id="orgId">
					<c:forEach var="orgList" items="${orgList}" varStatus="status">
					<option value="${orgList.orgId}" <c:if test="${row.orgId == orgList.orgId}">selected="selected"</c:if>>${orgList.orgName}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		
		<tr>
			<th>활성상태</th>
			<td>
				<select name="dlUseAt" class="dlUseAt">
					<option value="Y" <c:if test="${row.dlUseAt == 'Y'}">selected="selected"</c:if>>사용</option>
					<option value="N" <c:if test="${row.dlUseAt == 'N'}">selected="selected"</c:if>>사용안함</option>
					<option value="R" <c:if test="${row.dlUseAt == 'R'}">selected="selected"</c:if>>보류</option>
				</select>
			</td>
		</tr>
	</table>
	</form>

	<div class="tac mt20 disinblock w100p">
		<a href="<c:url value='/bdp/admin/dataset/datalist.do?pageIndex=${pageIndex}'/>" class="btn floatl"><spring:message code="button.list" /></a>

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
		
		if($("#dlName").val() == "") {
			alert("데이터 목록명을 입력하세요.");
			$("#dlName").focus();
			return false;
		}
		if($("#dlComment").val() == "") {
			alert("데이터 목록 설명을 입력하세요.");
			$("#dlComment").focus();
			return false;
		}

		$.ajax({
			url : "./dataModify.do",
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