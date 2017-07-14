<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>拒绝退货</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
<div data-role="page" data-title="拒绝退货" class="jqm-demos">
    <div role="main" class="ui-content jqm-content jqm-fullwidth">
        <div id="msgPopup" class="weui-popup-container popup-bottom">
            <div class="weui-popup-overlay"></div>
            <div class="weui-popup-modal" style="height: 200px;overflow: hidden; text-align: center;">
                <div class="modal-content" style="padding-top: 0;overflow: hidden;">
                    <div id="errorMsg" style="padding: 0 30px;"></div>
                    <a class="bottom-btn guanzhu-ok" style="color: #fff;font-size: 16px;">确定</a>
                    <a class="bottom-btn close-popup" style="color: #fff;font-size: 16px;background-color: #aaa;">取消</a>
                </div>
            </div>
        </div>

        <div class="home-content " style="margin:0">
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
                    <div class="dingdan-content-flex">
                        <div class="dingdan-title">
                            ${product.content}
                        </div>
                        <div class="dingdan-info">
                            <div>成交金额：￥<fmt:formatNumber type="number" value="${product.totalPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                            <div>截止时间：<fmt:formatDate value="${endTime}" pattern="MM-dd HH:mm"/></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="refuseBackBtn">
            <a style="padding: 10px;border-bottom: 1px solid #eee;">
                <div class="money-more">
                    <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-t.png" style="width:12px;height:7px;"/>
                </div>
                <div style="display: -webkit-box !important;display: box !important;position:relative;">
                    <div  class="dingdan-title" style="margin-right:20px;">拒绝退货理由</div>
                    <div id="select_value" class="dingdan-title" style="color:#dc721c;width:60%;" data-flag="up" >请选择理由</div>
                </div>
            </a>
        </div>
        <div id="option_value" style="display:none;">
            <c:forEach items="${refuseReturnReasons}" var="rf">
                <a class="faxian-link">
                    <div class="money-more">
                        <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/check.png" />
                    </div>
                    <div class="dingdan-title" data-value="${rf.id}">${rf.name}</div>
                </a>
            </c:forEach>
            <div style="margin:0 10px; display: none;" id="other">
                <textarea style="margin-top:10px;" placeholder="请您写明拒绝退后理由，方便小二进行判断" id="refuseReturnReasonOther"></textarea>
            </div>
        </div>

        <div style="background-color:#f0f0f0;padding-top:20px;">
            <div>
                <a id="refuseBtn" type="button" style="color:#fff;font-size:14px;display:block;height:35px; line-height:35px; background-color:#dc721c; margin:0 20px 20px 20px;text-align:center">拒绝退货</a>
            </div>

            <div style="display: -webkit-box !important;display: box !important;position:relative;background-color:white">
                <div style="border-right:solid 1px #A9A9A9;width:33.33%;padding:5px;font-size: 14px;color: #555;">事件</div>
                <div style="border-right:solid 1px #A9A9A9;width:33.33%;padding:5px;font-size: 14px;color: #555;">理由</div>
                <div style="width:33.33%;padding:5px;font-size: 14px;color: #555;">时间</div>
            </div>
            <div style="display: -webkit-box !important;display: box !important;position:relative;background-color:#EAEAEA;">
                <div style="border-right:solid 1px #A9A9A9;width:33.33%;padding:5px;font-size: 12px;color: #555;line-height:20px;">买家-申请退货</div>
                <div style="border-right:solid 1px #A9A9A9;width:33.33%;padding:5px;font-size: 12px;color: #555;line-height:20px;">
                    ${order.returnApplyReasonZh}
                    <c:if test="${order.returnApplyReason == 'RR99'}">
                        - ${order.returnApplyReasonOther}
                    </c:if>
                </div>
                <div style="width:33.33%;padding:5px;font-size: 12px;color: #555;line-height:20px;"><fmt:formatDate value="${order.returnApplyTime}" pattern="MM-dd HH:mm"/></div>
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
        var params = {
            id : '${order.id}'
        };
        $(function(){
            $("#refuseBackBtn").click(function(){
                $("#option_value").toggle();
            });

            $("#option_value").on("click",".faxian-link", function() {

                if(!$(this).find("img").hasClass('active')){
                    $(".faxian-link").find("img").removeClass('active').attr("src", base + "wsale/images/check.png");
                    $(this).find("img").addClass('active').attr("src", base + "wsale/images/checked.png");

                    var option_text = $(this).find(".dingdan-title").text();
                    var option_value = $(this).find(".dingdan-title").attr("data-value");
                    $("#select_value").attr("data-value", option_value);
                    $("#select_value").text(option_text);

                    if(option_value == 'RF99') {
                        $('#other').show();
                    } else {
                        $('#other').hide();
                    }
                }
            });

            $('#refuseBtn').click(function(){
                var refuseReturnReason = $("#select_value").attr("data-value");
                var refuseReturnReasonText = $("#select_value").html();
                if(!refuseReturnReason) {
                    $.toptip('请选择理由');
                    return;
                }
                params.refuseReturnReason = refuseReturnReason;

                var refuseReturnReasonOther = $('#refuseReturnReasonOther').val();
                if(refuseReturnReason == 'RF99') {
                    if(Util.checkEmpty(refuseReturnReasonOther)) {
                        $.toptip('请您写明退货理由');
                        return;
                    }
                    params.refuseReturnReasonOther = refuseReturnReasonOther;
                    refuseReturnReasonText += '-' + (refuseReturnReasonOther.length > 18 ? (refuseReturnReasonOther.substring(0, 18) + "...") : refuseReturnReasonOther);
                }
                $('#errorMsg').html('您确认以“'+refuseReturnReasonText+'”的理由，拒绝退货？');

                $('#msgPopup').wePopup();
            });

            $('.guanzhu-ok').click(function(){
                ajaxPost('api/apiOrder/refuseBack', params, function(data) {
                    if(data.success) {
                        window.location.replace(document.referrer);
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                },function(){
                    $.loading.load({type:2, msg:'正在提交...'});
                }, -1);
            });
        });
    </script>
</body>

</html>
