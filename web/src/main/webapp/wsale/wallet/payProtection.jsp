<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="jb.listener.Application"%>
<%
    String protection = Application.getString("AF06");
    protection = protection == null ? "1000" : protection;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>缴纳消保金</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div>
                    <div class="xiaobaojin-list" style="border-bottom: inherit;">
                        <div class="jiaona-btn">
                            <font class="yue-sum"><fmt:formatNumber type="number" value="${protection}" pattern="0.00" maxFractionDigits="2"/>元</font>
                        </div>
                        <div class="normal-text">
                            <div>消保金：</div>
                        </div>
                    </div>
                    <div style="font-size: 14px;color: #999;text-align: left;margin: 8px;">需要缴纳</div>
                    <div class="chongzhi-value">
                        <div class="chongzhi-jine">
                            <span class="big-text" style="margin-left:10px;">金额</span>
                            <input type="tel" placeholder="请输入缴纳金额" id="protection" <c:if test="${protection < 1000}">value="${(1000 - protection).longValue()}"</c:if>/>
                        </div>
                    </div>
                    <div style="font-size: 12px;color: #999;text-align: right;margin: 8px;">
                        至少缴纳 <fmt:formatNumber type="number" value="<%=protection %>" pattern="#,###"/> 元
                    </div>
                    <div style="margin-top:30px;">
                        <a class="bottom-btn" style="color:#fff; margin-bottom:10px;">下一步</a>
                    </div>
                </div>
            </div>

    
        </div><!-- /content -->

        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->

    </div><!-- /page -->

    <script type="text/javascript">
        var protection = ${protection} || 0;
        $(function(){
            $('#protection').blur(function(){
                var leastP = parseFloat('<%=protection %>') - protection;
                if(leastP > 0 && $(this).val() < leastP) {
                    $(this).val(leastP);
                }
            });

            $('.bottom-btn').click(function(){
                var price = $('#protection').val();
                if(price <= 0) {
                    $('#protection').focus();
                    return;
                }
                ajaxPost('api/apiWallet/addProtection', {protectionType : 'PN01', price:price}, function(data){
                    if(data.success) {
                        href('api/pay/toPay?objectId=' + data.obj + '&objectType=PO07&totalFee=' + price);
                    }
                });
            });
        });
    </script>
</body>

</html>
