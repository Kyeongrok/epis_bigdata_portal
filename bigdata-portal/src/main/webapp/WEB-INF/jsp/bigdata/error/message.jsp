<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="Referrer" content="origin">
    <meta name="viewport" content="width=device-width, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
</head>
<body>
<script type="text/javascript">
var message = "${message}";
var redirect = "${redirect}";
var target = <c:choose><c:when test="target != null && target.trim() != ''">${target}</c:when><c:otherwise>this</c:otherwise></c:choose>;

if(message != "") {
	alert(message);
}
if(redirect == "back") {
	target.history.back();
} else if(redirect == "close") {
	target.close();
} else if(redirect != "") {
	target.location.replace(redirect, true);
}
</script>
</body>
</html>