<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>申请退货</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="申请退货" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0">
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
                                <div>截止时间：<fmt:formatDate value="${receiveDownTime}" pattern="MM-dd HH:mm"/></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="backApplyBtn">
                <a style="padding: 10px;border-bottom: 1px solid #eee;">
                    <div class="money-more">
                        <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-t.png" style="width:12px;height:7px;"/>
                    </div>

                    <div style="display: -webkit-box !important;display: box !important;position:relative;">
                        <div  class="dingdan-title" style="margin-right:20px;">申请退货理由</div>
                        <div id="select_value" class="dingdan-title" style="color:#dc721c;width:60%;" data-flag="up" >请选择理由</div>
                    </div>
                </a>
            </div>

            <div id="option_value" style="display:none;">
                <c:forEach items="${returnApplyReasons}" var="rr">
                    <a class="faxian-link">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/check.png" />
                        </div>
                        <div class="dingdan-title" data-value="${rr.id}">${rr.name}</div>
                    </a>
                </c:forEach>

                <div style="margin:0 10px; display: none;" id="other">
                    <textarea style="margin-top:10px;" placeholder="请您写明退货理由" id="returnApplyReasonOther"></textarea>
                </div>

            </div>

            <div>
                <a id="applyBtn" type="button" style="color:#fff;font-size:14px;display:block;height:35px; line-height:35px; background-color:#dc721c; margin:20px;text-align:center">申请退货</a>
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
        $(function(){
            $("#backApplyBtn").click(function(){
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

                    if(option_value == 'RR99') {
                        $('#other').show();
                    } else {
                        $('#other').hide();
                    }
                }
            });

            $('#applyBtn').click(function(){
                var params = {id:'${orderId}'};
                var returnApplyReason = $("#select_value").attr("data-value");
                if(!returnApplyReason) {
                    $.toptip('请选择理由');
                    return;
                }
                params.returnApplyReason = returnApplyReason;

                var msg = '您确认申请退货?';
                var returnApplyReasonOther = $('#returnApplyReasonOther').val();
                if(returnApplyReason == 'RR99') {
                    if(Util.checkEmpty(returnApplyReasonOther)) {
                        $.toptip('请您写明退货理由');
                        return;
                    }
                    params.returnApplyReasonOther = returnApplyReasonOther;
                    msg = '您选择其他理由退货将<font color="#F00;">不退回技术服务费</font>， 是否继续？';
                }

                $.confirm(msg, "系统提示", function() {
                    ajaxPost('api/apiOrder/backApply', params, function(data) {
                        if(data.success) {
                            $.loading.close();
                            $.alert("退货申请成功，请等待卖家确认！", "系统提示！", function(){
                                window.location.replace(document.referrer);
                            });
                        } else {
                            $.alert(data.msg, "系统提示！");
                        }
                    },function(){
                        $.loading.load({type:3, msg:'正在提交...'});
                    }, -1);
                }, function() {});
            });
        });
    </script>
</body>

</html>
