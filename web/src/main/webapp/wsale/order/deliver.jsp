<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>发货物流信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.deliver.css"/>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="发货物流信息" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0">
                <a class="faxian-link">

                    <div class="normal-text" style="text-align:left;">
                        拍品信息
                    </div>
                </a>
                <div class="dingdan-list ">
                    <div class="dingdan-content">
                        <div class="dingdan-img">
                            <img src="${product.icon}" />
                        </div>
                        <div>
                            <div class="dingdan-title">
                                ${product.content}
                            </div>
                            <div class="dingdan-info">
                                <div>成交金额：￥<fmt:formatNumber type="number" value="${product.hammerPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                <div>付款时间：<fmt:formatDate value="${order.paytime}" pattern="MM-dd HH:mm"/></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div style="border-bottom: 10px solid #f5f5f5;">
                <a style="padding: 10px;border-bottom:solid 1px #F5F5F5">
                    <div style="display: -webkit-box !important;display: box !important;position:relative;">
                        <div  class="dingdan-title" style="margin-right:20px;">买家收货地址</div>
                    </div>
                </a>
                <a style="padding: 10px;border-bottom: 1px solid #f5f5f5;">
                    <div>
                        <div style="display: -webkit-box !important;display: box !important;position:relative;">
                            <div class="dingdan-title" style="margin-right:10px;">收货人：</div>
                            <div class="dingdan-title" >${address.userName} ${address.telNumber}</div>
                        </div>
                        <div class="dingdan-title" style="margin-top:10px;color:#aaa;font-size:12px;">
                            ${address.provinceName}${address.cityName}${address.countyName}${address.detailInfo}
                        </div>
                    </div>
                </a>
            </div>

            <div  class="renzheng-input" style="border-bottom: 10px solid #f5f5f5;">
                <a class="faxian-link">
                    <div style="float:right;">
                        <input type="text" placeholder="请输入快递公司" id="expressName" maxlength="10"/>
                    </div>
                    <div class="dingdan-title">快递公司</div>
                </a>
            </div>


            <div  class="renzheng-input" style="border-bottom: 10px solid #f5f5f5;">
                <a class="faxian-link">
                    <div style="float:right;">
                        <input class="onlyNum" maxlength="20" type="text" placeholder="请输入运单号" id="expressNo"/>
                        <img class="shaoyishao scanBtn" src="${pageContext.request.contextPath}/wsale/images/saoyisao.png" alt=""/>
                    </div>
                    <div class="dingdan-title">运单号</div>
                </a>
            </div>

            <div>
                <a type="button" id="deliverBtn" style="color:#fff;font-size:14px;display:block;height:35px;text-align: center; line-height:35px; background-color:#dc721c; margin:20px;">确认发货</a>
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
        var params = {
            id: '${order.id}'
        };

        $(function(){
            $('#deliverBtn').click(function(){
                var expressName = $('#expressName').val(), expressNo = $('#expressNo').val();
                if(Util.checkEmpty(expressName)) {
                    $('#expressName').focus();
                    return;
                }
                if(Util.checkEmpty(expressNo)) {
                    $('#expressNo').focus();
                    return;
                }
                params.expressName = expressName;
                params.expressNo = expressNo;

                $.confirm("您确认要提交收货物流信息?", "系统提示", function() {
                    ajaxPost('api/apiOrder/deliver', params, function(data){
                        if(data.success) {
                            window.location.replace(document.referrer);
                        } else {
                            $.alert(data.msg, "系统提示！");
                        }
                    },function(){
                        $.loading.load({type:2, msg:'正在提交...'});
                    }, -1);
                }, function() {});
            });

            $('.scanBtn').click(function(){
                JWEIXIN.scanQRCode(function(data){
                    var result = data.split(',');
                    if(result.length == 2) {
                        $('#expressNo').val(result[1]);
                    } else {
                        $.toast("无法识别", "text");
                    }
                });
            });
        });
    </script>
</body>

</html>
