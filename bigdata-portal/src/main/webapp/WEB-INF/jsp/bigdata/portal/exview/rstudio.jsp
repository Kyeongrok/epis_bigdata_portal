<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form target="_self" id="login" name="realform" method="POST" action="${viewUrl}/auth-do-sign-in">
	<input type="hidden" name="persist" id="persist" value="0"/>
	<input type="hidden" name="appUri" value=""/>
	<input type="hidden" name="clientPath" id="clientPath" value=""/>
	<input type="hidden" id="package" name="v" value="${v}"/>
</form>

<iframe id="frame" style="border: 0; position:fixed; top:0; left:0; right:0; bottom:0; width:100%; height:100%"></iframe>
 
<script type="text/javascript">
	document.getElementById('login').submit();
</script>
