<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <title>로그인</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/reset.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/site.css" />" />

    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/container.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/grid.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/header.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/image.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/menu.css" />" />

    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/divider.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/segment.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/form.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/input.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/button.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/list.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/message.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/icon.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/components/checkbox.css" />" />
    <style type="text/css">
        body { background-color: #DADADA; }
        body > .grid { height: 100%; }
        .column { max-width: 450px; }
    </style>
</head>
<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
                <div class="content">
                    
                </div>
            </h2>
            <form class="ui large form" name="loginForm" action ="<c:url value='/uat/uia/actionLogin.do'/>" method="post">
                <div style="visibility:hidden;display:none;">
                    <input name="iptSubmit1" type="submit" value="전송" title="전송">
                    <input type="hidden" id="message" name="message" value="<c:out value='${message}'/>">
                    <input name="userSe" type="hidden" value="USR"/>
                    <input name="j_username" type="hidden"/>
                </div>
                <div class="ui stacked segment">
                    <div class="inline fields">
                        <div class="field">
                            <div class="ui radio checkbox">
                                <input name="rdoSlctUsr" type="radio" id="loginType1" onClick="checkLogin('GNR');" tabindex="1"/><label for="loginType1">일반</label>
                            </div>
                        </div>
                        <div class="field">
                            <div class="ui radio checkbox">
                                <input name="rdoSlctUsr" type="radio" id="loginType2" onClick="checkLogin('ENT');" tabindex="2"/><label for="loginType2">기업</label>
                            </div>
                        </div>
                        <div class="field">
                            <div class="ui radio checkbox">
                                <input name="rdoSlctUsr" type="radio" checked="checked" id="loginType3" onClick="checkLogin('USR');" tabindex="3"/><label for="loginType3">업무</label>
                            </div>
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <input id="id" name="id" type="text" data-length="10" tabindex="4" placeholder="사용자 아이디">
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input id="password" name="password" type="password" placeholder="패스워드" data-length="10" tabindex="5" onKeyDown="javascript:if (event.keyCode == 13) { actionLogin(); }">
                        </div>
                    </div>
                    <div class="ui fluid large teal submit button">Login</div>
                </div>

                <div class="ui error message"></div>

                <div class="ui message">
                    <div class="ui checkbox">
                        <input type="checkbox" class="filled-in" name="checkId" id="checkId" onClick="javascript:saveid(document.loginForm);" tabindex="6"/><label for="checkId">아이디 저장</label>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="<c:url value="/js/components/form.js" />"></script>
<script src="<c:url value="/js/components/transition.js"/>"></script>
<script type="text/javaScript" language="javascript">
    function checkLogin(userSe) {
        if (userSe == "GNR") {
            document.loginForm.rdoSlctUsr[0].checked = true;
            document.loginForm.rdoSlctUsr[1].checked = false;
            document.loginForm.rdoSlctUsr[2].checked = false;
            document.loginForm.userSe.value = "GNR";
        } else if (userSe == "ENT") {
            document.loginForm.rdoSlctUsr[0].checked = false;
            document.loginForm.rdoSlctUsr[1].checked = true;
            document.loginForm.rdoSlctUsr[2].checked = false;
            document.loginForm.userSe.value = "ENT";
        } else if (userSe == "USR") {
            document.loginForm.rdoSlctUsr[0].checked = false;
            document.loginForm.rdoSlctUsr[1].checked = false;
            document.loginForm.rdoSlctUsr[2].checked = true;
            document.loginForm.userSe.value = "USR";
        }
    }
    function setCookie (name, value, expires) {
        document.cookie = name + "=" + escape (value) + "; path=/; expires=" + expires.toGMTString();
    }
    function getCookie(Name) {
        var search = Name + "=";
        if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
            offset = document.cookie.indexOf(search);
            if (offset != -1) { // 쿠키가 존재하면
                offset += search.length;
                // set index of beginning of value
                end = document.cookie.indexOf(";", offset);
                // 쿠키 값의 마지막 위치 인덱스 번호 설정
                if (end == -1)
                    end = document.cookie.length;
                return unescape(document.cookie.substring(offset, end));
            }
        }
        return "";
    }
    function saveid(form) {
        var expdate = new Date();
        if (form.checkId.checked)
            expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);
        else
            expdate.setTime(expdate.getTime() - 1);
        setCookie("saveid", form.id.value, expdate);
    }
    function getid(form) {
        form.checkId.checked = ((form.id.value = getCookie("saveid")) != "");
    }
    $(document).ready(function(){
        getid(document.loginForm);
        $('.ui.form').form(
            {
                fields: {
                    id: {
                        identifier  : 'id',
                        rules: [
                            {
                                type   : 'empty',
                                prompt : '아이디를 입력해 주세요.'
                            }
                        ]
                    },
                    password: {
                        identifier  : 'password',
                        rules: [
                            {
                                type   : 'empty',
                                prompt : '패스워드를 입력해 주세요.'
                            },
                            {
                                type   : 'length[6]',
                                prompt : '패스워드는 최소 6글자 이상입니다.'
                            }
                        ]
                    }
                }
            }
        );
    });

</script>
</html>


