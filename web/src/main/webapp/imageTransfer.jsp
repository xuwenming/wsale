<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jb.listener.Application"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		$('a').click(function(){
			var m = $(this).attr('data-m');
			$.post('${pageContext.request.contextPath}/api/imageTransfer/'+m+'?t=' + new Date().getTime(), {}, function(result) {
				console.log(result)
				alert(result.msg);
			}, "JSON");
		});
	});
</script>
</head>
<body>
	<a data-m="basedata">tbasedata</a><br>
	<a data-m="authentication">authentication</a><br>
	<a data-m="banner">banner</a><br>
	<a data-m="bbsComment">bbsComment</a><br>
	<a data-m="category">category</a><br>
	<a data-m="chatMsg">chatMsg</a><br>
	<a data-m="file">file</a><br>
	<a data-m="shop">shop</a><br>
	<a data-m="chatFriend">chatFriend</a><br>
	<a data-m="refreshHeadImage">refreshHeadImage</a><br>
</body>
</html>