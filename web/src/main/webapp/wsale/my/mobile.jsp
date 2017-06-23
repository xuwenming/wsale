<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>手机号码</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <style>
        .ui-input-text{
            margin: 0;
        }
    </style>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div style="background-color:#fff; padding: 5px;border-bottom:1px solid #ddd;margin-bottom: .5em;">
                <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" id="saveBtn">
                    保 存
                </div>
                <div style="width:80%; padding: 10px;">
                    <span onclick="href('api/userController/info');" style="padding: 10px 0px;">取 消</span>
                </div>
            </div>
            <input class="onlyNum" style="margin:10px;background-color: #fff;" type="tel" maxlength="11" placeholder="请输入您的手机号码..." id="mobile"/>
            <input class="onlyNum" style="margin:10px;background-color: #fff;" type="tel" maxlength="6" placeholder="请输入验证码..." id="vcode"/>
            <div style="float:right;width:90px;text-align:center; margin: -45px 10px;font-size: 15px;border: 1px solid #f0f0f0;padding: 5px 10px" id="vcode-btn">
                点击获取
            </div>
        </div><!-- /content -->

    </div><!-- /page -->

    <script type="text/javascript">
        var time = 59, timeInterval;
        $(function(){
            $('#saveBtn').click(function(){
                var mobile = $('#mobile').val();
                if(!Util.checkPhone(mobile)) {
                    $.toptip('手机号码有误');
                    return;
                }
                var vcode = $('#vcode').val();
                if(Util.checkEmpty(vcode)) {
                    $.toptip('请输入验证码');
                    return;
                }
                ajaxPost('api/userController/edit', {mobile : mobile, vcode : vcode}, function(data){
                    if(data.success) {
                        href('api/userController/info');
                    } else {
                        $.toptip(data.msg);
                    }
                });
            });

            $('#vcode-btn').bind('click', sendVCode);
        });

        function sendVCode() {

            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码', 'error');
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
    </script>
</body>

</html>
