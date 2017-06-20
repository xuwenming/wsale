<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>支付安全</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/ladingPasswordConfirm.js" charset="utf-8"></script>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="mask-layer-2"></div>
                <div class="pwd-dialog" style="display: none;">
                    <div class="pay-pwd" style="position: relative">
                        <div class="big-text" style="text-align: center;">
                            输入您支付密码，以验证身份
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

                <div class="faxian-list">
                    <a class="faxian-link resetPwd">
                        <input type="hidden" value="${wallet.id}" id="id">
                        <input type="hidden" value="${wallet.realName}" id="realName">
                        <input type="hidden" value="${wallet.idNo}" id="idNo">
                        <input type="hidden" value="${wallet.payPassword}" id="payPassword">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">重置支付密码</div>
                    </a>
                    <a class="faxian-link forgetPwd">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">忘记支付密码</div>
                    </a>
                </div>
                <div class="faxian-list" style="border-top:none;">

                    <a href="javascript:href('api/apiWallet/toEditIdentity');" class="faxian-link">
                        <div class="money-more">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">修改身份信息</div>
                    </a>

                    </a>
                </div>

            </div>
        </div>


    </div><!-- /content -->

    <script type="text/javascript">
        var index = 0, pay_password = '', verify_pwd = '';
        $(function(){
            $('.resetPwd').click(function(){
                if(Util.checkEmpty($('#realName').val()) || Util.checkEmpty($('#idNo').val())) {
                    $.modal({
                        title: "系统提示！",
                        text: "您的身份信息尚不完善",
                        buttons: [
                            { text: "取消", className: "default" },
                            { text: "去完善", onClick: function(){
                                href('api/apiWallet/toEditIdentity');
                            } },
                        ]
                    });
                    return;
                }

                $('.mask-layer-2, .pwd-dialog').show();
            });

            $('.forgetPwd').click(function(){
                if(Util.checkEmpty($('#realName').val()) || Util.checkEmpty($('#idNo').val())) {
                    $.modal({
                        title: "系统提示！",
                        text: "您的身份信息尚不完善",
                        buttons: [
                            { text: "取消", className: "default" },
                            { text: "去完善", onClick: function(){
                                href('api/apiWallet/toEditIdentity');
                            } },
                        ]
                    });
                    return;
                }

                href('api/apiWallet/forgetPwd');
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
                    pay_password = '';
                    verify_pwd = '';
                    $('.big-text').html('输入您的支付密码，以验证身份');
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

                        if(verify_pwd == '') {
                            success = false;
                            verify_pwd = check_pass_word;
                            if(md5(verify_pwd) != $('#payPassword').val()) {
                                $.alert("支付密码输入错误，请再试一次！", "系统提示！", function(){
                                    index = 0;
                                    $('#password li input').val('');
                                    verify_pwd = '';
                                    $('.big-text').html('输入您的支付密码，以验证身份');
                                });
                            } else {
                                index = 0;
                                $('#password li input').val('');
                                $('.big-text').html('请输入您的新支付密码');
                            }
                        } else {
                            if(pay_password == '') {
                                success = false;
                                pay_password = check_pass_word;
                                index = 0;
                                $('#password li input').val('');
                                $('.big-text').html('再输一遍，确认您的新支付密码');
                            } else {
                                if(pay_password != check_pass_word) {
                                    success = false;
                                    $.alert("两次密码输入不一致，重新输入！", "系统提示！", function(){
                                        pay_password = '';
                                        index = 0;
                                        $('#password li input').val('');
                                        $('.big-text').html('请输入您的新支付密码');
                                    });
                                }
                            }
                        }

                        if(success) {
                            $('.mask-layer-2, .pwd-dialog').hide();
                            index = 0;
                            $('#password li input').val('');
                            pay_password = '';
                            verify_pwd = '';
                            $('.big-text').html('输入您支付密码，以验证身份');

                            ajaxPost('api/apiCommon/getPublicKey', {}, function(data){
                                if(data.success) {
                                    var encrypt = new JSEncrypt();
                                    encrypt.setPublicKey(data.obj);
                                    ajaxPost('api/apiWallet/editWallet', {id:$('#id').val(), payPassword:encrypt.encrypt(check_pass_word)}, function(data){
                                        if(data.success) {
                                            $('#payPassword').val(md5(check_pass_word));
                                        }
                                    });
                                }
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
