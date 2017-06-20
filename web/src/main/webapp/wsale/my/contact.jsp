<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>联系人</title>
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
            <input style="margin:10px;background-color: #fff;" type="text" maxlength="18" placeholder="请输入您的联系人..." id="contact" value="${contact}"/>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('#saveBtn').click(function(){
                ajaxPost('api/userController/edit', {contact : $('#contact').val()}, function(data){
                    if(data.success) {
                        href('api/userController/info');
                    }
                });
            });
        });
    </script>
</body>

</html>
