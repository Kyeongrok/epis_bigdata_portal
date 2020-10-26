<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

데이터 검색 팝업

<form name="searchFrm" id="searchFrm" action="<c:url value='/bdp/dataset/search.do' />" method="get">
<input type="text" name="keyword" value="" />
<input type="submit" name="검색">
</form>

데이터 리스트

<table>
<tr>
<th scope="col"></th>
<td></td>

<th scope="col"></th>
<td></td>
</tr>


<tr>

</tr>

</table>



선택시 데이터 정보


선택
취소
