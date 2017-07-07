<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jb.listener.Application"%>
<%
	String server_url = Application.getString("SV101");
%>

<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/jquery.mobile-1.4.5.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/style.css?v=${staticVersion}">
<link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/component.css?v=${staticVersion}">
<link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/swiper.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/weui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/jquery-weui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/load/load.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery.js" charset="utf-8"></script>
<script type="text/javascript">
	var base = '${pageContext.request.contextPath}/';
	var appId = '${appId}';
	var timestamp = '${timestamp}';
	var nonceStr = '${nonceStr}';
	var signature = '${signature}';
	var tokenId = '${tokenId}';
	var server_url = '<%=server_url %>';
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery-weui.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery.mobile-1.4.5.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/swiper.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery-browser.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery.qqFace.js?v=${staticVersion}" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/function.js?v=${staticVersion}" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/common.js?v=${staticVersion}" charset="utf-8"></script>
<!-- 扩展jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/extJquery.js?v=${staticVersion}" charset="utf-8"></script>
<!-- 微信JS-SDK -->
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jweixin-1.0.0.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jweixin-common.js?v=${staticVersion}" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery.lazyload.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/load/load.js?v=${staticVersion}" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/fastclick.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery.raty.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jsencrypt.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/md5.min.js" charset="utf-8"></script>
<script type="text/javascript">
	window.addEventListener('load', function() {
		FastClick.attach(document.body);
	}, false);
	$(function(){
		if(tokenId) {
			//$.cookie('tokenId', null, {path:'/'});
			$.cookie('tokenId', tokenId, {path:'/'});
		}
	});
</script>
