<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>店铺信息认证</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="店铺信息认证" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div class="renzheng-steptitle">
                    <c:choose>
                        <c:when test="${auth.authType == 'AT01'}">
                            <step <c:if test="${auth.authStep >= 1}">onclick="href('api/apiAuth/auth?authType=${auth.authType}');"</c:if>>
                                <img src="${pageContext.request.contextPath}/wsale/images/step1-icon.png" class="renzheng-stepimg" /> <span>个人信息</span>
                            </step>
                            <span class="renzheng-aaa">》</span>
                            <step <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toShopInfo?authId=${auth.id}');"</c:if>>
                                <img src="${pageContext.request.contextPath}/wsale/images/step2-icon.png" class="renzheng-stepimg" /> <span>店铺信息</span>
                            </step>
                            <span class="renzheng-aaa">》</span>
                            <step <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toSubmitAudit?authId=${auth.id}');"</c:if>>
                                <img src="${pageContext.request.contextPath}/wsale/images/graystep3-icon.png" class="renzheng-stepimg" /> <span>提交审核</span>
                            </step>
                        </c:when>
                        <c:otherwise>
                            <span <c:if test="${auth.authStep >= 1}">onclick="href('api/apiAuth/auth?authType=${auth.authType}');"</c:if> class="renzheng-current">企业信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toPersonalInfo?authId=${auth.id}');"</c:if> class="renzheng-current">个人信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toShopInfo?authId=${auth.id}');"</c:if> class="renzheng-current">店铺信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toSubmitAudit?authId=${auth.id}');"</c:if>>提交审核</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <form method="post" id="save_shop_form">
                    <input type="hidden" name="authId" value="${auth.id}">
                    <input type="hidden" name="authType" value="${auth.authType}">
                    <input type="hidden" name="id" value="${shop.id}">
                    <div class="renzheng-input">
                        <div class="fbpp-title">
                            店铺信息
                        </div>
                        <a class="faxian-link">
                            <div style="float:right;">
                                <input type="text" placeholder="请输入" name="name" id="shopName" value="${shop.name}"/>
                            </div>
                            <div class="normal-text">店铺名称</div>
                        </a>
                        <a class="faxian-link">
                            <div class="normal-text">店铺介绍</div>
                            <textarea style="margin-top:20px;" placeholder="请输入" name="introduction" id="introduction">${shop.introduction}</textarea>
                        </a>
                        <div style="margin:10px;">
                            <div class="normal-text">上传店铺logo</div>
                            <div class="upload-shopimg">
                                <c:choose>
                                    <c:when test="${!empty shop.logoUrl}"><img width="50" height="50" src="${shop.logoUrl}" /></c:when>
                                    <c:when test="${!empty headImage}"><img width="50" height="50" src="${headImage}" /></c:when>
                                    <c:otherwise><img class="upload-jia" src="${pageContext.request.contextPath}/wsale/images/add-img-large.png" /></c:otherwise>
                                </c:choose>
                                <!--<img width="50" height="50" src="${headImage}" />-->
                            </div>
                            <input type="hidden" name="logoUrlMediaId" id="logoUrlMediaId">
                        </div>
                    </div>
                </form>
                <c:if test="${auth.payStatus != 'PS02' || auth.auditStatus == 'AS03'}">
                    <div style=" margin-top:40px;">
                        <a class="bottom-btn" id="save_shop_btn" style="color:#fff;">下一步</a>
                    </div>
                </c:if>
            </div>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('.upload-shopimg').click(function(){
                var _this = this;
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:3,msg:'正在上传...'});
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        $(_this).html('<img width="50" height="50" src="'+localId+'" />');
                        $('#logoUrlMediaId').val(serverId);
                        $.loading.close(200);
                    });
                }, 1);
            });

            $('#save_shop_btn').bind('click', saveShop);
        });

        function saveShop() {
            if(!validate()) return;

            ajaxPost('api/apiAuth/saveShop', $('#save_shop_form').serialize(), function(data){
                if(data.success) {
                    href('api/apiAuth/toSubmitAudit?authId=${auth.id}');
                } else {
                    $.loading.close();
                    $.toptip('保存失败');
                }
            },function(){
                $.loading.load({type:1, msg:'信息保存中'});
            }, -1);
        }

        function validate() {
            var shopName = $("#shopName").val();
            if(Util.checkEmpty(shopName)) {
                $.toptip('请输入店铺名称');
                return false;
            }

            var introduction = $('#introduction').val();
            if(Util.checkEmpty(introduction)) {
                $.toptip('请输入店铺介绍');
                return false;
            }

            if($('.upload-shopimg .upload-jia').length > 0) {
                $.toptip('请上传店铺logo');
                return false;
            }

            return true;
        }
    </script>
</body>

</html>
