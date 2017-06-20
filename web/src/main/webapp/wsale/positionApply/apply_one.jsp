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
            <div class="home-content">
                <div style="padding:5px 0px 15px 0px; border-bottom:10px solid #f5f5f5; font-size:14px; letter-spacing:0;">
                    <img src="${pageContext.request.contextPath}/wsale/images/step1-icon.png" style="width:24px; vertical-align:bottom;" /> <span>申请职务条件</span>
                    <span style="margin:10px; color:#888;">>></span>
                    <img src="${pageContext.request.contextPath}/wsale/images/graystep2-icon.png" style="width:24px; vertical-align:bottom;" /> <span>填写基本资料</span>
                </div>
                <div style="text-align:left;">
                    <a style="background-color:#fff;padding:10px;border-bottom:1px solid #eee;">
                        <div style="float:right;" class="chooseCategory">
                            <input type="hidden" id="categoryId" value="${category.id}"/>
                            <span id="categoryName">${category.name}</span> <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" style="height:15px; vertical-align:middle;" />
                        </div>
                        <div class="normal-text">申请版面</div>
                    </a>
                    <a style="background-color:#fff;padding:10px;border-bottom:1px solid #eee;">
                        <div style="float:right;">
                            <div roleId="zsjs" class="apply-banmian" style="width:30px;height:20px; line-height:20px;border:1px solid #C87C1C;color:#fff; background-color:#C87C1C;text-align:center; display:inline-block;">讲师</div>
                            <div roleId="gb" class="apply-banmian" style="width:30px;height:20px; line-height:20px;border:1px solid #C87C1C;color:#C87C1C;text-align:center;display:inline-block;margin:0 5px;">贵宾</div>
                            <div roleId="bz" class="apply-banmian" style="width:30px;height:20px; line-height:20px;border:1px solid #C87C1C;color:#C87C1C; text-align:center;display:inline-block; float:right;">版主</div>
                        </div>
                        <div class="normal-text">申请职务</div>
                    </a>
                    <div style="margin:10px; line-height:1.4;">
                        <div style="font-size:14px;">申请条件：</div>
                        <div style="font-size:12px;color:#999;">
                            <div>1.论坛鉴赏区发主题帖子10篇；</div>
                            <div>2.只能申请一个版面的职务，如已在其他版面担任版主请勿申请；</div>
                            <div>3.实名认证用户；</div>
                            <div>
                                <div style="color:#C87C1C; font-size:12px; float:right;">
                                    <div class="rule-choose" style="display:inline-block;"><div style="width:12px;height:12px; line-height:12px;border:1px solid #ddd;display:inline-block; vertical-align:middle;">√</div> <span>是</span></div>
                                    <div class="rule-choose" style="display:inline-block;"><div style="width:12px;height:12px; line-height:12px;border:1px solid #ddd;display:inline-block; vertical-align:middle;"></div> <span>否</span></div>
                                </div>
                                4.是否已详细阅读并遵守以下规定：
                            </div>
                            <div style="color:#C87C1C;margin-top:10px;">
                                <span style="color:#dc721c;" onclick="javascript:location.href='http://mp.weixin.qq.com/s/9wW1RWgjDPqCiHhHeQGPKA';">《论坛版主申请管理规定》</span>
                                <span style="color:#dc721c;" onclick="javascript:location.href='http://mp.weixin.qq.com/s/m1SgOidbieooppk_nMN1Eg';">《论坛版主管理规定》</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <a href="javascript:next();" type="button" style="color:#fff;font-size:14px;display:block;height:40px; line-height:40px; background-color:#dc721c; position:absolute;bottom:20px;left:20px;right:20px;">下一步</a>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        var roleId = 'zsjs';
        var isRule = true;
        var categoryName = '';
        $(function(){
            $(".apply-banmian").click(function() {
                roleId = $(this).attr('roleId');
                $(this).css("color","#fff");
                $(this).css("background-color","#C87C1C");
                $(this).siblings().css("color","#C87C1C");
                $(this).siblings().css("background-color","#fff");
            });

            $(".rule-choose").click(function(){
                if($(this).index() == 0) isRule = true;
                else isRule = false;
                $(this).find("div").text("√");
                $(this).siblings().find("div").text("");
            });

            $("#childCategory").on('click', '.secondstyle-list', function(){
                var _this = $(this);
                var pid = _this.attr('pid'), categoryId = _this.attr('categoryId');
                $('#categoryId').val(categoryId);

                categoryName = $(".first-style[categoryId="+pid+"] .fenlei-title").text() + " - " + _this.text();
            });

            $(".choose-ok").click(function(){
                if(categoryName != '') {
                    $('#categoryName').text(categoryName);
                }
            });

            $(".first-style").click(function(){
                drawChildCategory($(this).attr("categoryId"));
            });
        });

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

        function next() {
            if(!isRule) {
                $.toptip('请先详细阅读并遵守相关规定！');
                return;
            }
            ajaxPost('api/apiPositionApply/checkApply', {roleId:roleId}, function(data){
                if(data.success) {
                    var roleName = $('div[roleId='+roleId+']').html();
                    var apply = data.obj.apply;
                    if(data.obj.applyed) {
                        var flag = true;
                        if(apply.auditStatus == 'AS03') {
                            $.modal({
                                title: "系统提示",
                                text: "您申请的【"+roleName+"】审核不通过，是否重新申请？",
                                buttons: [
                                    { text: "取消", className: "default"},
                                    { text: "重新申请", onClick: function(){
                                        href('api/apiPositionApply/applyTwo?categoryId=' + $("#categoryId").val() + "&roleId=" + roleId);
                                    } }
                                ]
                            });
                            flag = false;
                        } else if(apply.auditStatus == 'AS01' && apply.payStatus != 'PS02') {
                            $.modal({
                                title: "系统提示",
                                text: "您尚有未完成的订单！",
                                buttons: [
                                    { text: "取消", className: "default"},
                                    { text: "去支付", onClick: function(){
                                        href('api/pay/toPay?objectId='+apply.id+'&objectType=PO01&totalFee=<%=totalFee %>');
                                    } }
                                ]
                            });
                            flag = false;
                        } else {
                            $.alert("您已经申请【"+roleName+"】,请勿重复申请！", "系统提示！");
                            flag = false;
                        }

                        if(!flag) return;
                    }
                    if(!data.obj.isAuth) {
                        $.modal({
                            title: "系统提示",
                            text: "您尚未实名认证！",
                            buttons: [
                                { text: "取消", className: "default"},
                                { text: "去认证", onClick: function(){
                                    href('api/apiAuth/authApply');
                                } }
                            ]
                        });
                        return;
                    }
                    href('api/apiPositionApply/applyTwo?categoryId=' + $("#categoryId").val() + "&roleId=" + roleId);
                } else {
                    $.toast("申请条件失败", "forbidden");
                }
            });

        }
    </script>
</body>

</html>
