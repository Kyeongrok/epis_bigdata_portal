<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>농식품 빅데이터 포탈</title>
<link rel="icon" type="image/png" href="../../images/bigdata/favicon.png">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bigdata/all.css" />" /> 
<script type="text/javascript" src="<c:url value="/js/bigdata/encrypt.min.js" />" ></script>
<!-- <script type="text/javascript" src="/js/vendor/jquery/jquery-2.1.4.min.js" ></script> -->

</head>
<body>
<div id="login">
	<div class="wrap_login">
		<div class="loginbox">
			<div class="logo_login"><img src="/images/bigdata/logo.svg" alt="농림축산식품부 빅데이터 포털"></div>
			
			<form class="ui large form" id="loginForm" name="loginForm" action="<c:url value='/uat/uia/actionLogin.do'/>" method="post" onsubmit="xmlHttpRequest(); return false;">
				<input type="hidden" id="message" name="message" value="<c:out value='${message}'/>">
                <input name="userSe" type="hidden" value="GNR"/>
                <input name="j_username" type="hidden"/>
                <input name="p" id="p" type="hidden"/>
				<dl class="form_login">
					<dt class="form_id">아이디</dt>
					<dd>
						<input id="id" name="id" type="text" placeholder="아이디" title="아이디">
					</dd>
					<dt class="form_pw mt20">비밀번호</dt>
					<dd class="mt20">
						<input id="password" name="password" type="password" placeholder="비밀번호" title="비밀번호">
					</dd>
				</dl>
				<div class="chk_login">
					<span>
						<input type="checkbox" id="id_save" name="id_save" title="아이디 저장">
						<label for="id_save"><span></span>아이디 저장</label>
					</span>
					<!-- 
					<span class="floatr">
						<input type="checkbox" id="idpw_find" name="idpw_find">
						<label for="idpw_find"><span></span>아이디/비밀번호 찾기</label>
					</span>
					 -->
				</div>
				<button type="submit"></button>
				<a href="#" id="submt" class="btn btn_login">로그인<i class="arrow_wh ml20"></i></a>
			</form>			
		</div>
		<div class="copy_login">Copyrightⓒ 2018 농림축산식품부 빅데이터포털. All right reserved.</div>
	</div>
</div>
<script type="text/javascript">
//eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('9 x(){5.6("L").o()}9 y(a,b,c){3 d=q z();d.A(d.B()+c);3 e=M(b)+((c==N)?"":"; C="+d.D());5.r=a+"="+e}9 s(a){3 b=q z();b.A(b.B()-1);5.r=a+"= "+"; C="+b.D()}9 E(a){a=a+\'=\';3 b=5.r;3 c=b.F(a);3 d=\'\';7(c!=-1){c+=a.i;3 e=b.F(\';\',c);7(e==-1)e=b.i;d=b.O(c,e)}t P(d)}9 G(){7(5.6("j").8==""){k("아이디를 입력하세요.");t H}7(5.6("I").8==""){k("비밀번호를 입력하세요.");t H}7(5.6("u").v){3 g=5.6(\'j\').8;y("l",g,Q)}R{s("l")}3 h=q S();h.T("U","/V/W/X.Y",J);h.Z=9(){10{3 a=5.6(\'j\').8+"\\n"+5.6(\'I\').8;7(h.11==4){7(h.12==13){3 b=h.14;3 c=b.15(\':\',2);3 d=c[0];3 e=c[1];3 f=16(a,d,e);5.6(\'p\').8=f;x()}}}17(K){k("18: "+K)}};h.19()}3 w=E("l");7(w.i>0){5.6("u").v=J;5.6("j").8=w}3 m=5.6("m").8;7(m.i>0){k(m)}3 o=5.6(\'1a\');o.1b=9(){G()};7(!5.6("u").v){s("l")}',62,74,'|||var||document|getElementById|if|value|function|||||||||length|id|alert|episBigdataPortalId|message||submit||new|cookie|deleteCookie|return|id_save|checked|userInputId|login|setCookie|Date|setDate|getDate|expires|toGMTString|getCookie|indexOf|xmlHttpRequest|false|password|true|exception|loginForm|escape|null|substring|unescape|365|else|XMLHttpRequest|open|GET|bdp|auth|check|do|onreadystatechange|try|readyState|status|200|responseText|split|encrypt|catch|Error|send|submt|onclick'.split('|'),0,{}))
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('8 p(){2.4("E").i()}8 q(5,6,j){3 d=r s();d.t(d.u()+j);3 9=F(6)+((j==G)?"":"; v="+d.w());2.k=5+"="+9}8 x(5){3 e=r s();e.t(e.u()-1);2.k=5+"= "+"; v="+e.w()}8 y(5){5=5+\'=\';3 a=2.k;3 b=a.z(5);3 9=\'\';7(b!=-1){b+=5.f;3 g=a.z(\';\',b);7(g==-1)g=a.f;9=a.H(b,g)}l I(9)}8 A(){7(2.4("c").6==""){m("아이디를 입력하세요.");l B}7(2.4("J").6==""){m("비밀번호를 입력하세요.");l B}7(2.4("C").D){3 c=2.4(\'c\').6;q("n",c,K)}L{x("n")}p()}3 o=y("n");7(o.f>0){2.4("C").D=M;2.4("c").6=o}3 h=2.4("h").6;7(h.f>0){m(h)}3 i=2.4(\'N\');i.O=8(){A()};',51,51,'||document|var|getElementById|cookieName|value|if|function|cookieValue|cookieData|start|id|exdate|expireDate|length|end|message|submit|exdays|cookie|return|alert|episBigdataPortalId|userInputId|login|setCookie|new|Date|setDate|getDate|expires|toGMTString|deleteCookie|getCookie|indexOf|xmlHttpRequest|false|id_save|checked|loginForm|escape|null|substring|unescape|password|365|else|true|submt|onclick'.split('|'),0,{}))
</script>
	
<!-- 
	<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/vendor/jquery-ui/jquery-ui.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/vendor/jquery-tmpl/jquery.tmpl-1.0.0.min.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.spin.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/common.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/placeholders.min.js" />" ></script>	
 -->
</body>
</html>