<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jb.listener.Application"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>潜程商家管理平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/winvoice/resource/img/favicon.ico" rel="shortcut icon"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/styleFront.css">

<script src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js"
	type="text/javascript" charset="utf-8"></script>
<style type="text/css">
input:-webkit-autofill { -webkit-box-shadow: 0 0 0px 1000px #DBF1FC inset; }
</style>
<script type="text/javascript">
$(function(){
	$('#loginForm input').keyup(function(event) {
		if (event.keyCode == '13') {
			loginFun();
		}
	});
});

function loginFun() {
	$.post('${pageContext.request.contextPath}/userController/login', $("#loginForm").serialize(), function(result) {
		if (result.success) {
			window.location.href = "${pageContext.request.contextPath}/index.jsp";
		} else {
			//$.messager.alert('错误', result.msg, 'error');
			$("#msg").show();
			$("#msg").text(result.msg);
		}
	}, "JSON");
}
</script>
</head>

<body>
<div id="login">
	<div class="login_top">
		<img alt="" src="${pageContext.request.contextPath}/style/images/qian_pc_02.png" width="120" height="55">
	</div>
    <div class="login_content1">
		<div class="login_con">
   			<div class="login_font">
           	<form action="" method="post" name="loginForm" id="loginForm">
       			<p class="log_sel" style="display:block; border: inherit;"></p>
               	<p class="yhm" id="accountP"><input type="text" id="name" name="name" class="yhm_id" placeholder="用户名" style="padding-left: 10px;"/></p>
               	<p class="mima" id="passwordP"><input type="password" id="pwd" name="pwd" class="mima_id" placeholder="密码" style="padding-left: 10px;"/></p>
               	<b class="login_bor" id="msg" style="display: none;"></b>
               	<p style="display:block; margin-top:20px; height:24px;">
      				<!-- 是否记住密码 -->
      				<span class="fleft curposter" id="chkRememberPassSpan"><input type="checkbox" id="chkRememberPass" name="chkRememberPass" class="checkbox_but" value="1" checked="checked" />记住密码</span>
               		<span class="fright"><a href="javascript:downfile();" class="lan">忘记密码？</a></span></p>
               	<p class="marTop">
               		<input type="button" class="login_btn" id="login_id" value=" " onclick="loginFun();"/>
               		<input type="button" class="register_btn" />
               	</p>
           	</form>
            </div>
        </div>
    </div>
   <!--  <div class="login_center">
    	<div class="login_bot"></div>
	</div> -->
</div>
</body>
</html>
