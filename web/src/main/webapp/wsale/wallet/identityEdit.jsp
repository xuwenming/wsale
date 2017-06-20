<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改身份信息</title>
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
                            请输入您的支付密码
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

                <div class="faxian-list renzheng-input">
                    <input type="hidden" value="${wallet.id}" id="id">
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" value="${wallet.realName}" placeholder="填写您的真实姓名" id="realName" maxlength="10"/>
                        </div>
                        <div class="normal-text">姓名</div>
                    </a>
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" value="${wallet.idNo}" placeholder="填写您的身份证号" id="idNo" maxlength="18"/>
                        </div>
                        <div class="normal-text">身份证号</div>
                    </a>
                </div>
                <div class="search-btn">
                    <a class="bottom-btn" href="#full" data-rel="popup">确认</a>
                </div>

            </div>
        </div>
    </div><!-- /content -->

    <script type="text/javascript">
        var first = true, index = 0, pay_password = '', old_password = '${wallet.payPassword}' || '';
        $(function(){
            $('#realName, #idNo').one('focus', function(){
                if(first) {
                    $('#realName').val('');
                    $('#idNo').val('');
                    first = false;
                }
            });
            $('#realName').focus(function(){
                $(this).val('');
            });
            $('#idNo').focus(function(){
                $(this).val('');
            });
            $('.bottom-btn').click(function(){
                var realName = $.trim($('#realName').val());
                if(Util.checkEmpty(realName)) {
                    $.toptip('请正确填写您的姓名', 'warning');
                    return;
                }
                var idNo = $.trim($('#idNo').val());
                if(Util.checkEmpty(idNo) || idNo.length != 18) {
                    $.toptip('请正确填写您的身份证号', 'warning');
                    return;
                }

                $('.mask-layer-2, .pwd-dialog').show();

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
                    $('.big-text').html('请输入您的支付密码');
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
                        if(old_password) {
                            if(old_password != md5(check_pass_word)) {
                                success = false;
                                $.alert("支付密码输入错误，请再试一次！", "系统提示！", function(){
                                    index = 0;
                                    $('#password li input').val('');
                                    $('.big-text').html('请输入您的支付密码');
                                });
                            }
                        } else {
                            if(pay_password == '') {
                                success = false;
                                pay_password = check_pass_word;
                                index = 0;
                                $('#password li input').val('');
                                $('.big-text').html('再输一遍，确认您的支付密码');
                            } else {
                                if(pay_password != check_pass_word) {
                                    success = false;
                                    $.alert("两次密码输入不一致，重新输入！", "系统提示！", function(){
                                        pay_password = '';
                                        index = 0;
                                        $('#password li input').val('');
                                        $('.big-text').html('请输入您的支付密码');
                                    });
                                }
                            }
                        }

                        if(success) {
                            $('.mask-layer-2, .pwd-dialog').hide();
                            var realName = $.trim($('#realName').val()), idNo = $.trim($('#idNo').val());
                            var params = {id:$('#id').val(), realName:realName, idNo:idNo};
                            if(pay_password) {
                                ajaxPost('api/apiCommon/getPublicKey', {}, function(data){
                                    if(data.success) {
                                        var encrypt = new JSEncrypt();
                                        encrypt.setPublicKey(data.obj);
                                        params.payPassword = encrypt.encrypt(pay_password);
                                        editWallet(params);
                                    }
                                });

                            } else {
                                editWallet(params);
                            }
                        }

                    }
                }
                return false;
            });
        });

        function editWallet(params) {
            ajaxPost('api/apiWallet/editWallet', params, function(data){
                if(data.success) {
                    window.history.go(-1);
                }
            }, function(){
                $.loading.load({type:3, msg:'信息更新中...'});
            }, -1);
        }
    </script>
</body>

</html>
