<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>余额提现</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/ladingPasswordConfirm.js" charset="utf-8"></script>
    <style>
        .renzheng-input .ui-input-text input {
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="mask-layer-2"></div>
                <div class="pwd-dialog" style="display: none;">
                    <div class="pay-pwd" style="position: relative">
                        <div class="big-text" style="text-align: center;">
                            输入您的支付密码
                        </div>
                        <div class="pwd-input">
                            <ul id="password">
                                <li>
                                    <input readonly class="pass" type="password"maxlength="1"value="">
                                </li><li>
                                <input readonly class="pass" type="password"maxlength="1"value="">
                            </li><li>
                                <input readonly class="pass" type="password"maxlength="1"value="">
                            </li><li>
                                <input readonly class="pass" type="password"maxlength="1"value="">
                            </li><li>
                                <input readonly class="pass" type="password"maxlength="1"value="">
                            </li><li style="border-right:1px solid #dc721c;">
                                <input readonly class="pass" type="password"maxlength="1" value="">
                            </li>
                            </ul>
                        </div>
                    </div>
                    <div id="keyboardDIV"></div>
                </div>

                <div id="bankPopup" class="weui-popup-container popup-bottom">
                    <div class="weui-popup-overlay"></div>
                    <div class="weui-popup-modal" style="height: 360px;overflow: hidden; text-align: center;">
                        <div class="toolbar">
                            <div class="toolbar-inner">
                                <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                                <h1 class="title">填写银行卡信息</h1>
                            </div>
                        </div>

                        <div class="modal-content" style="overflow: hidden;">
                            <div class="renzheng-input" style="background-color: #fff;">
                                <a class="faxian-link" style="padding: 14px;">
                                    <div class="list-right">
                                        <input type="text" placeholder="请输入开户名" id="bankAccount" value="${walletDetail.bankAccount}" maxlength="18"/>
                                    </div>
                                    <div class="normal-text">开户名</div>
                                </a>
                                <a class="faxian-link" style="padding: 14px;">
                                    <div class="list-right">
                                        <input type="tel" placeholder="请输入开户时预留手机号" id="bankPhone" value="${walletDetail.bankPhone}" maxlength="11" class="onlyNum"/>
                                    </div>
                                    <div class="normal-text">手机号</div>
                                </a>
                                <a class="faxian-link" style="padding: 14px;">
                                    <div class="list-right">
                                        <input type="text" placeholder="请输入身份证号" id="bankIdNo" value="${walletDetail.bankIdNo}" maxlength="18"/>
                                    </div>
                                    <div class="normal-text">身份证号</div>
                                </a>
                                <a class="faxian-link" style="padding: 14px;">
                                    <div class="list-right">
                                        <input type="tel" placeholder="请输入银行卡号" id="bankCard" value="${walletDetail.bankCard}" maxlength="36"  class="onlyNum"/>
                                    </div>
                                    <div class="normal-text">银行卡号</div>
                                </a>
                            </div>
                            <a class="bottom-btn guanzhu-ok" style="color: #fff;font-size: 16px;">确认提现</a>
                        </div>
                    </div>
                </div>

                <div class="yue-zhifu">
                    <div class="big-text pay-text">
                        余额提现
                    </div>
                    <div class="pay-value">￥${amount}元</div>
                </div>
                <div class="pay-content">
                    <div class="pay-list">
                        <span class="big-text">本次提现：</span><span class="pay-smallval">￥${amount}元</span>
                    </div>
                    <div>
                        <div class="pay-list pay-choose">
                            <img class="choose-icon" src="${pageContext.request.contextPath}/wsale/images/xuanzhong-icon.png" data-flag="true" /> <img class="pay-way" src="${pageContext.request.contextPath}/wsale/images/weixin-icon.png" />
                            <div class="way-content">
                                <div class="big-text">微信零钱</div>
                                <div class="pay-desc">小额提现，快速到账</div>
                            </div>
                        </div>
                        <div class="pay-list pay-choose">
                            <img class="choose-icon" src="${pageContext.request.contextPath}/wsale/images/weixuanzhong-icon.png" data-flag="false" /> <img style="height:35px;" class="pay-way" src="${pageContext.request.contextPath}/wsale/images/yinlian-icon.png" />
                            <div class="way-content">
                                <div><span class="big-text">银行卡提现</span></div>
                                <div class="pay-desc">单笔最高5,000-100,000元</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="margin-top:30px;">
                    <a href="" class="bottom-btn" id="cashBtn" style="color:#fff;">确认提现</a>
                </div>
            </div>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        var index = 0;
        $(function(){
            $('#cashBtn').click(function(){
                var num = $('.choose-icon[data-flag=true]').closest('.pay-choose').index();
                if(num == 0) {
                    if(${amount > 3000}) {
                        $.alert("提现至微信零钱最大额度为3000元！", "系统提示");
                        return;
                    }
                    $('.mask-layer-2, .pwd-dialog').show();
                }
                else
                    $('#bankPopup').wePopup();
                    //$('.mask-layer, .fensi-dialog').show();
            });

            $('.guanzhu-ok').click(function(){
                var bankAccount = $('#bankAccount').val(), bankPhone = $('#bankPhone').val(),
                    bankIdNo = $('#bankIdNo').val(), bankCard = $('#bankCard').val();
                if(Util.checkEmpty(bankAccount)) {
                    $('#bankAccount').focus();
                    return;
                }
                if(Util.checkEmpty(bankPhone)) {
                    $('#bankPhone').focus();
                    return;
                }
                if(Util.checkEmpty(bankIdNo)) {
                    $('#bankIdNo').focus();
                    return;
                }
                if(Util.checkEmpty(bankCard)) {
                    $('#bankCard').focus();
                    return;
                }
                $.closePopup();
                //$('.mask-layer, .fensi-dialog').hide();
                $('.mask-layer-2, .pwd-dialog').show();
            });

            $(".pay-choose").click(function(){
                var imgFlag = $(this).find(".choose-icon").attr("data-flag");
                if(imgFlag == "false"){
                    $(this).find(".choose-icon").attr("data-flag","true");
                    $(this).siblings().find(".choose-icon").attr("data-flag","false");
                    $(this).find(".choose-icon").attr("src", base + "wsale/images/xuanzhong-icon.png");
                    $(this).siblings().find(".choose-icon").attr("src", base + "wsale/images/weixuanzhong-icon.png");
                }
            });

            $('#keyboard li').click(function() {
                if ($(this).hasClass('delete')) {
                    $('#password li:eq('+(--index%6)+') input').val('');
                    if($('#password li:eq(0) input').val()==''){
                        index = 0;
                    }
                    return;
                }
                if ($(this).hasClass('cancle')) {
                    $('.mask-layer-2, .pwd-dialog').hide();
                    index = 0;
                    $('#password li input').val('');
                    return;
                }
                if ($(this).hasClass('symbol') || $(this).hasClass('tab')){
                    var character = $(this).text();
                    if($('#password li:eq(5) input').val() != ''){
                        index = 0;
                        return;
                    }
                    $('#password li:eq('+(index++%6)+') input').val(character);
                    if($('#password li:eq(5) input').val()!='') {
                        var check_pass_word = '', success = true;
                        $('#password li').each(function(){
                            check_pass_word += $(this).find('input').val();
                        });

                        if(md5(check_pass_word) != '${wallet.payPassword}') {
                            success = false;
                            $.alert("支付密码输入错误，请再试一次！", "系统提示！", function(){
                                index = 0;
                                $('#password li input').val('');
                            });
                        }

                        if(success) {
                            $('.mask-layer-2, .pwd-dialog').hide();
                            var num = $('.choose-icon[data-flag=true]').closest('.pay-choose').index();
                            var params = {totalFee : ${amount}};
                            if(num == 0) {
                                params.channel = 'CS01';
                            } else {
                                params.channel = 'CS03';
                                params.bankAccount = $('#bankAccount').val();
                                params.bankPhone = $('#bankPhone').val();
                                params.bankIdNo = $('#bankIdNo').val();
                                params.bankCard = $('#bankCard').val();
                            }
                            ajaxPost('api/pay/transfers', params, function(data){
                                if(data.success) {
                                    $.toast("提现成功", function(){
                                        replace('api/apiWallet/myWallet');
                                    });
                                } else {
                                    $.toast("余额不足", "forbidden", function(){
                                        replace('api/apiWallet/myWallet');
                                    });
                                }
                            }, function(){
                                $.loading.load({type:2, msg:'提现中...'});
                            });
                        }

                    }
                }
                return false;
            });
        });
    </script>
</body>

</html>
