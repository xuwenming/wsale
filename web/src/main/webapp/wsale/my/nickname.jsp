<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>昵称</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div style="background-color:#fff; padding: 5px;border-bottom:1px solid #ddd;">
                <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" id="saveBtn">
                    保 存
                </div>
                <div style="width:80%; padding: 10px;">
                    <span onclick="href('api/userController/info');" style="padding: 10px 0px;">取 消</span>
                </div>
            </div>
            <input style="margin:10px;background-color: #fff;" type="text" maxlength="18" placeholder="请输入您的昵称..." id="nickname" value="${nickname}"/>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('#saveBtn').click(function(){
                var nickname = $.trim($('#nickname').val());
                if(Util.checkEmpty(nickname)) {
                    $.toptip('昵称不能为空');
                    return;
                }
                if(nickname.gblen() > 16) {
                    $.toptip('昵称最多8个汉字或16个字母');
                    return;
                }
                ajaxPost('api/userController/edit', {nickname : nickname}, function(data){
                    if(data.success) {
                        href('api/userController/info');
                    } else {
                        $.toptip(data.msg);
                    }
                });
            });
        });
    </script>
</body>

</html>
