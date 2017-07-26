<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <title>
    <c:choose>
      <c:when test="${type == 'isDeleted'}">账号查封</c:when>
      <c:otherwise>微信扫码访问</c:otherwise>
    </c:choose>

  </title>
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
  <meta content="yes" name="apple-mobile-web-app-capable">
  <meta content="black" name="apple-mobile-web-app-status-bar-style">
  <meta content="telephone=no" name="format-detection">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.error.css"/>

  <script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/jquery.js" charset="utf-8"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/qrcode.js" charset="utf-8"></script>
  <script>
    var redirect_uri = "${redirect_uri}" || "";
    $(function(){
      // 设置参数方式
      var qrcode = new QRCode('qrcode', {
        width: 180,
        height: 180
      });
      // 使用 API
      qrcode.clear();
      qrcode.makeCode(redirect_uri);
    });
  </script>
</head>
<body>
<div id="gray">
  <div id="main">
    <c:choose>
      <c:when test="${type == 'isDeleted'}">
        <p>您的账号【${nickname}】<span>已被封号</span></p>
        <p><span>解封</span>请联系在线客服或拨打客服电话</p>
        <p>客服电话：<span><a href="tel:0579-82586020" style="text-decoration: none;color: red;">0579-82586020</a></span></p>
      </c:when>
      <c:otherwise>
        <p><span>微信扫描</span> 二维码</p>

        <p>或 <span>微信关注 </span> 公众号(集东集西)</p>

        <p>或 <span>复制网址至微信</span>打开</p>
        <div id="qrcode"></div>
        <p>开启愉快微拍之旅</p>
      </c:otherwise>
    </c:choose>
  </div>
  <!--<div class="hr"></div>
  <div id="footer">
    <div>如果您在微信里，点击继续访问</div>
  </div>-->
</div>

</body>
</html>
