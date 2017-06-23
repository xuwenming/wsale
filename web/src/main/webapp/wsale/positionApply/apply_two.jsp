<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jb.listener.Application"%>
<%
    String totalFee = Application.getString("AF03");
    totalFee = totalFee == null ? "300" : totalFee;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>申请职务</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="申请职务" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div style="padding:5px 0px 15px 0px; border-bottom:10px solid #f5f5f5; font-size:14px; letter-spacing:0;">
                    <img src="${pageContext.request.contextPath}/wsale/images/step1-icon.png" style="width:24px; vertical-align:bottom;" /> <span>申请职务条件</span>
                    <span style="margin:10px; color:#888;">>></span>
                    <img src="${pageContext.request.contextPath}/wsale/images/step2-icon.png" style="width:24px; vertical-align:bottom;" /> <span>填写基本资料</span>
                </div>
                <form method="post" id="apply_form">
                    <div id="sqzw-info" style="text-align:left;margin:10px; font-size:14px; color:#777; line-height:1.5;">
                        <input type="hidden" name="categoryId" id="categoryId" value="${positionApply.categoryId}"/>
                        <input type="hidden" name="roleId" value="${positionApply.roleId}"/>
                        <div>昵称：${positionApply.applyUserName}</div>
                        <div>推荐人：</div>
                        <input type="text" placeholder="填写推荐人的昵称" name="recommend" />
                        <div>就职单位：</div>
                        <input type="text" placeholder="请输入" name="companyName"/>
                        <div>个人专长、特长：</div>
                        <textarea placeholder="请输入" name="specialty"></textarea>
                        <div>*版面发展、建议：</div>
                        <textarea placeholder="请输入" name="advice"></textarea>
                        <div>*经常参与的版块：</div>
                        <input type="text" placeholder="请输入" name="activityForum"/>
                        <div>*平均每天在线时长：</div>
                        <div id="day-hour">
                            <div style="float:right; height:35px; line-height:35px;">小时</div>
                            <input type="tel" placeholder="请输入" name="onlineDuration" maxlength="2" class="onlyNum"/>
                        </div>
                        <a id="applyBtn" type="button" style="color:#fff;font-size:14px;display:block;height:40px; line-height:40px; background-color:#dc721c; text-align:center;">确定</a>
                    </div>
                </form>
            </div>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $("#applyBtn").bind("click", applyPosition);
        });

        function applyPosition() {
            ajaxPost('api/apiPositionApply/applyPosition', $('#apply_form').serialize(), function(data){
                if(data.success) {
                    //pay(data.obj);
                    href('api/pay/toPay?objectId='+data.obj+'&objectType=PO01&totalFee=<%=totalFee %>');
                }
            }, function(){
                $.loading.load({type:1, msg:'提交中...'});
            }, -1);
        }

        function pay(objectId) {
            $.modal({
                title: "申请职务",
                text: "此次申请需要支付费用300元",
                buttons: [
                    { text: "微信支付", onClick: function(){
                        var params = {totalFee : 0.01,objectType:'PO01',objectId:objectId, channel:'CS01'};
                        wxPayCall(params, function(){
                            replace('api/apiPositionApply/applySuccess?categoryId=' + $("#categoryId").val());
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
                            replace('api/apiCategoryController/forum?id=' + $("#categoryId").val());
                        });
                    }},
                ]
            });
        }
    </script>
</body>

</html>
