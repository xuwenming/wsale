<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jb.listener.Application"%>
<%
    String totalFee = Application.getString("AF04");
    totalFee = totalFee == null ? "300" : totalFee;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>发布拍品</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <style>
        .info-list .ui-input-text input {
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div data-role="page" data-title="发布拍品" class="jqm-demos" style="background-color: #f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <input type="hidden" id="bindMobile" value="${sessionInfo.mobile}" />
            <div class="mask-layer"></div>
            <div class="dialog-content">
                <div class="fenlei-liebiao" style="background-color:#eee;">
                    <span class="fenlei-title">分类</span>
                    <span class="fenlei-desc">请谨慎选择，切勿跨品类</span>
                </div>
                <div style="overflow-y: auto;" class="first-category">
                    <c:forEach items="${categorys}" var="category" varStatus="vs">
                        <div class="fenlei-liebiao first-style" categoryId="${category.id}">
                            <img src="${pageContext.request.contextPath}/wsale/images/zhubao-icon.png" class="fenlei-img" /> <span class="fenlei-title">${category.name}</span>
                            <span class="fenlei-desc">${category.summary}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="second-style">
                <div class="second-title" style="background-color:#eee; text-align:center;">
                    <div class="retry-choose">重选</div>
                    <div class="choose-ok">完成</div>
                    <span class="fenlei-title">文玩杂项</span>
                </div>
                <div style="margin:10px;">
                    <div style="font-size:12px;color:#888;margin-bottom:10px;">请选择二级分类</div>
                    <div id="childCategory">
                        <c:forEach items="${childCategorys}" var="childCategory" varStatus="vs">
                            <div class="secondstyle-list" pid="${childCategory.pid}" categoryId="${childCategory.id}">${childCategory.name}</div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="baotui-dialog">
                <div style="margin:10px;">
                    <img class="confirm-img" src="${pageContext.request.contextPath}/wsale/images/confirm-icon.png" />
                    <div class="confirm-info">
                        确认收货后，拍品进入7天包退期，包退期后货款自动解冻。
                    </div>
                    <a class="bottom-btn confirm-cancel" style="color:#dc721c;border:1px solid #dc721c; background-color:#fff; text-align:center;margin:10px 20px;">取消</a>
                </div>
            </div>

            <div id="bindMobilePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 240px; overflow: hidden;">
                    <div class="toolbar">
                        <div class="toolbar-inner">
                            <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                            <h1 class="title">绑定手机号</h1>
                        </div>
                    </div>
                    <div class="modal-content">
                        <input class="onlyNum" style="margin:10px 0;background-color: #fff;" type="tel" maxlength="11" placeholder="请输入您的手机号码..." id="mobile"/>
                        <input class="onlyNum" style="margin:10px 0;background-color: #fff;" type="tel" maxlength="6" placeholder="请输入验证码..."  id="vcode"/>
                        <div style="float:right;width:90px;text-align:center; margin: -45px 10px;font-size: 15px;border: 1px solid #f0f0f0;padding: 5px 10px" id="vcode-btn">
                            点击获取
                        </div>
                        <div style="text-align: center;">
                            <a class="bottom-btn" style="color: #fff;font-size: 16px;" id="bindMobileBtn">确认</a>
                        </div>
                    </div>
                </div>
            </div>

            <form method="post" id="product_add_form">
                <input type="hidden" name="id" id="id" value="${product.id}">
                <div class="home-content" style="margin:0;">

                    <div id="rangePopup" class="weui-popup-container popup-bottom">
                        <div class="weui-popup-overlay"></div>
                        <div class="weui-popup-modal" style="height: 340px;overflow: hidden; text-align: center;">
                            <div class="toolbar">
                                <div class="toolbar-inner">
                                    <h1 class="title">竞价阶梯</h1>
                                </div>
                            </div>
                            <div class="modal-content" style="overflow: hidden;background-color: #fff;">
                                <div class="jingjia-content">
                                    <c:forEach items="${ranges}" var="range" varStatus="vs">
                                        <div style="margin-bottom: 5px;">
                                            <input class="onlyNum" type="tel" value="${range.startPrice.longValue()}" name="startPrices" maxlength="10"/> <span>元到</span>
                                            <input class="onlyNum" type="tel" value="${range.endPrice.longValue()}" name="endPrices" maxlength="10"/> <span>元时，每次加</span>
                                            <input class="onlyNum" type="tel" value="${range.price.longValue()}" name="prices" maxlength="10"/> <span>元</span>
                                        </div>
                                    </c:forEach>
                                </div>
                                <a class="bottom-btn close-popup" style="color: #fff;font-size: 16px;">保存</a>
                            </div>
                        </div>
                    </div>

                    <div style="text-align:left;">
                        <div class="fbpp-title">
                            基础信息（必填）
                        </div>
                        <div class="info-list deadline-sel">
                            <div style="float:right; text-align: right; " class="jzsj-content">
                                <input type="text" placeholder="请选择时间" id="deadline" name="deadlineStr" readonly style="font-size: 12px;margin-top: 5px;">
                                <!--<span>请选择时间</span> -->
                                <img class="arrow-right right-iocn" src="${pageContext.request.contextPath}/wsale/images/rili-icon.png" />
                            </div>
                            <div class="normal-text">截止时间</div>
                        </div>
                        <div class="info-list product-style">
                            <div style="float:right;">
                                <input type="hidden" name="categoryId" value="${product.categoryId}">
                                <span id="categoryName">
                                    <c:choose>
                                        <c:when test="${not empty categoryName}">${categoryName}</c:when>
                                        <c:otherwise>请选择拍品分类</c:otherwise>
                                    </c:choose>
                                </span>
                                <img class="arrow-right right-iocn" src="${pageContext.request.contextPath}/wsale/images/fenlei-icon.png" />
                            </div>
                            <div class="normal-text">分类</div>
                        </div>
                        <div class="info-list">
                            <div style="float:right; text-align: right;" class="bzj-conent">
                                <input class="onlyNum" type="tel" maxlength="10" id="startingPrice" name="startingPrice" value="${product.startingPrice.longValue()}">
                            </div>
                            <div class="normal-text">起拍价</div>
                        </div>
                        <div class="info-list jjfd-set">
                            <div style="float:right;">
                                <span>点击设置</span>
                            </div>
                            <div class="normal-text">加价幅度</div>
                        </div>
                        <div class="fbpp-title">
                            可选设置
                        </div>
                        <div class="info-list">
                            <input type="hidden" name="approvalDays" value="${product.approvalDays}">
                            <div style="float:right;">
                                <div class="baotui-choose" <c:if test="${product.approvalDays != 'AD07'}">style="background-color:#fff;color:#C87C1C;"</c:if>>7天包退</div>
                                <div class="baotui-choose" <c:if test="${product.approvalDays != 'AD03'}">style="background-color:#fff;color:#C87C1C;"</c:if>>3天包退</div>
                                <div class="baotui-choose" <c:if test="${product.approvalDays != 'AD99'}">style="background-color:#fff;color:#C87C1C;"</c:if>>不包退</div>
                            </div>
                            <div class="normal-text">包退</div>
                        </div>
                        <div class="info-list">
                            <input type="hidden" name="isFreeShipping" value="${product.isFreeShipping}">
                            <div style="float:right;">
                                <c:choose>
                                    <c:when test="${!product.isFreeShipping}">
                                        <div class="switch-icon" data-flag="false"><div class="switch-btn"></div></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="switch-icon" data-flag="true" style="background-color: rgb(200, 124, 28);">
                                            <div class="switch-btn" style="float: right;"></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="normal-text">包邮</div>
                        </div>
                        <div class="info-list">
                            <div style="float:right; text-align:right;" class="bzj-conent">
                                <input class="onlyNum" type="tel" maxlength="10" placeholder="请输入" value="0" readonly name="margin"/>
                                <span class="marginBtn">点击开通</span> <img class="right-iocn" src="${pageContext.request.contextPath}/wsale/images/baozhengjin-icon.png" />
                            </div>
                            <div class="normal-text">保证金</div>
                        </div>
                        <div class="info-list">
                            <input type="hidden" name="isOpenBest" value="0">
                            <div style="float:right;" class="bzj-conent">
                                <span class="bestBtn">点击开通</span> <img class="right-iocn" src="${pageContext.request.contextPath}/wsale/images/jingpai-icon.png" />
                            </div>
                            <div class="normal-text">申请精选</div>
                        </div>
                        <!--<div class="info-list">
                            <input type="hidden" name="isNeedRealId" value="${product.isNeedRealId}">
                            <div style="float:right;">
                                <c:choose>
                                    <c:when test="${!product.isNeedRealId}">
                                        <div class="switch-icon" data-flag="false"><div class="switch-btn"></div></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="switch-icon" data-flag="true" style="background-color: rgb(200, 124, 28);">
                                            <div class="switch-btn" style="float: right;"></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="normal-text">实名认证</div>
                        </div>-->
                        <!--<div class="info-list">
                            <input type="hidden" name="isNeedProtectionPrice" value="${product.isNeedProtectionPrice}">
                            <div style="float:right;">
                                <c:choose>
                                    <c:when test="${!product.isNeedProtectionPrice}">
                                        <div class="switch-icon" data-flag="false"><div class="switch-btn"></div></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="switch-icon" data-flag="true" style="background-color: rgb(200, 124, 28);">
                                            <div class="switch-btn" style="float: right;"></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="normal-text">消保金</div>
                        </div>-->
                        <div class="fbpp-title">
                            高级设置
                        </div>
                        <!--<div class="info-list">
                            <div style="float:right; text-align: right;" class="bzj-conent">
                                <input type="text" placeholder="立即开拍" id="startingTime" name="startingTimeStr" readonly>
                                <span>未实名认证</span> <img class="right-iocn" src="${pageContext.request.contextPath}/wsale/images/renzheng-icon.png" />
                            </div>
                            <div class="normal-text">开拍时间</div>
                        </div>
                        <div class="fbpp-title">
                            可设置立即开拍或延迟开拍
                        </div>-->
                        <div class="info-list">
                            <div style="float:right;text-align: right;" class="bzj-conent">
                                <input class="onlyNum" type="tel" maxlength="10" id="fixedPrice" name="fixedPrice" value="${product.fixedPrice.longValue()}">
                            </div>
                            <div class="normal-text">一口价</div>
                        </div>
                        <div class="fbpp-title">
                            出价达到或超过此价格立即成交（无一口价请设0元）
                        </div>
                        <div class="info-list">
                            <div style="float:right;text-align: right;" class="bzj-conent">
                                <input class="onlyNum" type="tel" maxlength="10" id="referencePrice" name="referencePrice" value="${product.referencePrice.longValue()}">
                            </div>
                            <div class="normal-text">参考价</div>
                        </div>
                        <div class="fbpp-title">
                            提供此拍品的参考价格（无参考价请设0元）
                        </div>
                    </div>
                    <div>
                        <a class="bottom-btn" style="color:#fff;font-size: 15px;" id="product_add_btn">发布</a>
                    </div>
                </div>
            </form>

        </div><!-- /content -->


    </div><!-- /page -->


    <script type="text/javascript">
        var categoryName = '';
        var positionId = '${sessionInfo.positionId}' || 'by';
        var time = 59, timeInterval;
        $(function(){
            $('input[name=margin]').parent().hide();
            var now = new Date();
            var min = now.format('yyyy-MM-dd'), max = '';
            if(positionId == 'vip' || positionId == 'by') {
                now.setDate(now.getDate() + 2);
                max = now.format('yyyy-MM-dd');
            }
            $("#deadline").datetimePicker({
                title: '截止时间',
                min : min,
                max : max,
                times: function () {
                    return [{
                        values: ['10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
                    },{
                        divider: true,  // 这是一个分隔符
                        content: ':'
                    },{
                        values: ['00', '15', '30','45']
                    }
                    ];
                },
                onChange: function (picker, values, displayValues) {
                    //var times = values.split(',');
                    //alert(new Date(times[0],times[1],times[2],times[3],times[4]).getTime());
                }
            });

            $(".first-style").click(function(){
                drawChildCategory($(this).attr("categoryId"));
            });

            $("#childCategory").on('click', '.secondstyle-list', function(){
                var _this = $(this);
                var pid = _this.attr('pid'), categoryId = _this.attr('categoryId');
                $('input[name=categoryId]').val(categoryId);

                categoryName = $(".first-style[categoryId="+pid+"] .fenlei-title").text() + " - " + _this.text();
            });

            $("#product_add_btn").bind('click', addSecond);

            $('.info-list').on('blur', 'input[type=tel]', function(){
                if(Util.checkEmpty($(this).val())) $(this).val(0);
            });

            $('.info-list').on('focus', 'input[type=tel]', function(){
                if($(this).val() == 0) {
                    $(this).val('');
                }
            });

            $(".baotui-choose").click(function(){
                var num = $(this).index();
                if(num == 0) $('input[name=approvalDays]').val('AD07');
                else if(num == 1) $('input[name=approvalDays]').val('AD03');
                else $('input[name=approvalDays]').val('AD99');
            });

            $('.switch-icon').click(function(){
                var $input = $(this).closest('.info-list').find('input');
                if($(this).attr('data-flag') == 'true') {
                    $input.val(0);
                } else {
                    $input.val(1);
                }
            });

            $(".marginBtn").click(function(){
                $(this).hide();
                $(this).parent().find('.right-iocn').hide();
                $("input[name=margin]").parent().show();
                $("input[name=margin]").attr("readonly", false).val(0).focus();

            });

            $('.bestBtn').click(function(){
                var _this = this;
                $.confirm("<font style='font-size: 10pt;'>一.申请的是二级分类版块的精选藏品&#12288;&#12288;<br>二.条件：包退、描述相符&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;<br>三.申请未通过，费用退回到余额&#12288;&#12288;&#12288;&#12288;</font><br><br>是否继续支付费用？", "申请须知", function() {
                    $('input[name=isOpenBest]').val(1);
                    $(_this).hide();
                });
            });

            $(".choose-ok").click(function(){
                if(categoryName != '') {
                    $('#categoryName').text(categoryName);
                }
            });

            $('#vcode-btn').bind('click', sendVCode);
            $('#bindMobileBtn').bind('click', bindMobile);
        });

        function bindMobile() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码');
                return;
            }
            var vcode = $('#vcode').val();
            if(Util.checkEmpty(vcode)) {
                $.toptip('请输入验证码');
                return;
            }
            ajaxPost('api/userController/edit', {mobile : mobile, vcode : vcode}, function(data){
                if(data.success) {
                    $.toptip('绑定成功', 'success');
                    $("#bindMobile").val(mobile);
                    $.closePopup();
                } else {
                    $.toptip(data.msg);
                }
            });
        }

        function sendVCode() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码', 'error');
                $('#mobile').focus();
                return;
            }
            $('#vcode-btn').unbind('click').html('重发（<span id=\"time\">'+time+'</span>）');
            time--;
            timeInterval = setInterval(function(){
                $("#time").html(time);
                if(time == 0) {
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                } else {
                    time -- ;
                }
            }, 1000);

            ajaxPost('api/userController/sendVCode', {mobile:mobile, checkMobile:true}, function(data){
                if(data.success) {
                    $.toptip('验证码已发送至手机', 'success');
                } else {
                    $.toptip(data.msg, 'error');
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                }
            });

        }

        function drawChildCategory(pid) {
            $("#childCategory").empty();
            ajaxPost('api/apiCategoryController/categorys', {pid : pid}, function(data){
                if(data.success) {
                    var result = data.obj;
                    for(var i in result) {
                        $("#childCategory").append('<div class="secondstyle-list" pid="'+pid+'" categoryId="'+result[i].id+'">'+result[i].name+'</div>');
                    }
                }
            });
        }

        function addSecond() {
            if(!validate()) return;

            ajaxPost('api/apiProductController/publish', $('#product_add_form').serialize(), function(data){
                if(data.success) {
                    if($('input[name=isOpenBest]').val() == 0) {
                        replace('api/apiProductController/productDetail?id=' + $("#id").val());
                    } else {
                        //pay(data.obj);
                        replace('api/pay/toPay?objectId='+data.obj+'&objectType=PO03&attachType='+$("#id").val()+'&totalFee=<%=totalFee %>');
                    }
                }
            },function(){
                $.loading.load({type:1, msg:'正在发布...'});
            }, -1);
        }

        function validate() {
            if(!$("#bindMobile").val()) {
                $('#bindMobilePopup').wePopup();
                return;
            }

            var deadlineStr = $("#deadline").val();
            if(Util.checkEmpty(deadlineStr)) {
                $.toptip('请选择截止时间');
                return false;
            }

            var categoryId = $('input[name=categoryId]').val();
            if(Util.checkEmpty(categoryId)) {
                $.toptip('请选择拍品分类');
                return false;
            }

            var flag = true;
            $('.jingjia-content input').each(function(){
                if($.trim($(this).val()) == '') {
                    flag = false;
                    return flag;
                }
            });

            if(!flag) {
                $.toptip('加价幅度不允许设置为空');
                return false;
            }

            return true;
        }

        function pay(objectId) {
            $.modal({
                title: "申请精拍",
                text: "此次申请需要支付费用300元",
                buttons: [
                    { text: "微信支付", onClick: function(){
                        var params = {totalFee : 0.01,objectType:'PO03',objectId:objectId, channel:'CS01'};
                        wxPayCall(params, function(){
                            replace('api/apiProductController/productDetail?id=' + $("#id").val());
                        });
                    } },
                    { text: "其他", onClick: function(){
                        $.alert("主人，人家还没准备好", function(){
                            pay(objectId);
                        });

                    } },
                    { text: "取消", className: "default", onClick: function(){
                        $.confirm("再次取消视为放弃，是否放弃?", "系统提示", function() {
                            pay(objectId);
                        },function(){
                            replace('api/apiProductController/productDetail?id=' + $("#id").val());
                        });
                    }}
                ]
            });
        }
    </script>
</body>

</html>
