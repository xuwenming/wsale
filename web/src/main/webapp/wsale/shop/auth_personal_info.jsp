<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>个人信息认证</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="个人信息认证" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div class="renzheng-steptitle">
                    <c:choose>
                        <c:when test="${authType == 'AT01'}">
                            <step <c:if test="${auth.authStep >= 1}">onclick="href('api/apiAuth/auth?authType=${authType}');"</c:if>>
                                <img src="${pageContext.request.contextPath}/wsale/images/step1-icon.png" class="renzheng-stepimg" /> <span>个人信息</span>
                            </step>
                            <span class="renzheng-aaa">》</span>
                            <step <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toShopInfo?authId=${auth.id}');"</c:if>>
                                <img src="${pageContext.request.contextPath}/wsale/images/graystep2-icon.png" class="renzheng-stepimg" /> <span>店铺信息</span>
                            </step>
                            <span class="renzheng-aaa">》</span>
                            <step <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toSubmitAudit?authId=${auth.id}');"</c:if>>
                                <img src="${pageContext.request.contextPath}/wsale/images/graystep3-icon.png" class="renzheng-stepimg" /> <span>提交审核</span>
                            </step>
                        </c:when>
                        <c:otherwise>
                            <span <c:if test="${auth.authStep >= 1}">onclick="href('api/apiAuth/auth?authType=${authType}');"</c:if> class="renzheng-current">企业信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toPersonalInfo?authId=${auth.id}');"</c:if> class="renzheng-current">个人信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toShopInfo?authId=${auth.id}');"</c:if>>店铺信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toSubmitAudit?authId=${auth.id}');"</c:if>>提交审核</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <form method="post" id="save_personal_form">
                    <input type="hidden" name="id" id="authId" value="${auth.id}">
                    <input type="hidden" name="authType" id="authType" value="${authType}">
                    <div class="renzheng-input">
                        <div class="fbpp-title">
                            实名认证(请上传真实的个人信息，认证通过后无法进行修改)
                        </div>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="text" placeholder="请输入真实的姓名" name="userName" id="userName" value="${auth.userName}"/>
                            </div>
                            <div class="normal-text">姓名</div>
                        </a>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="hidden" name="idType" value="IT01">
                                <span class="grayright-text">
                                    身份证
                                </span>
                                <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                            </div>
                            <div class="normal-text">证件类型</div>
                        </a>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="text" maxlength="18" placeholder="请输入" name="idNo" value="${auth.idNo}" id="idNo"/>
                            </div>
                            <div class="normal-text">证件号码</div>
                        </a>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input class="onlyNum" type="tel" maxlength="11" placeholder="请输入" name="phone" value="${auth.phone}" id="phone"/>
                            </div>
                            <div class="normal-text">联系电话</div>
                        </a>
                        <div class="fbpp-title">
                            上传身份证
                        </div>
                        <div class="upload-cardImg">
                            <div class="upload-card upload">
                                <div>身份证正面照片</div>
                                <c:choose>
                                    <c:when test="${empty auth.idFront}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/shenfenzheng1-img.png" />
                                        <div class="upload-opacity addDiv">添加照片</div>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${auth.idFront}" />
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" class="mediaId" name="idFrontMediaId" id="idFrontMediaId">
                                <input type="hidden" id="idFront" name="idFront" value="${auth.idFront}">
                            </div>
                            <div class="upload-card upload" style="float:right;">
                                <div>身份证反面照片</div>
                                <c:choose>
                                    <c:when test="${empty auth.idFront}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/shenfenzheng2-img.png" />
                                        <div class="upload-opacity addDiv">添加照片</div>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${auth.idBack}" />
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" class="mediaId" name="idBackMediaId" id="idBackMediaId">
                                <input type="hidden" id="idBack" name="idBack" value="${auth.idBack}">
                            </div>
                            <div class="upload-shouchi upload">
                                <div>手持身份证照片</div>
                                <c:choose>
                                    <c:when test="${empty auth.idFront}">
                                        <img class="upload-shouchiimg" src="${pageContext.request.contextPath}/wsale/images/shenfenzheng3-img.png" />
                                        <div class="upload-opacity2 addDiv">添加照片</div>
                                    </c:when>
                                    <c:otherwise>
                                        <img class="upload-shouchiimg" src="${auth.idFrontByhand}" />
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" class="mediaId" name="idFrontByhandMediaId" id="idFrontByhandMediaId">
                                <input type="hidden" id="idFrontByhand" name="idFrontByhand" value="${auth.idFrontByhand}">
                            </div>
                        </div>
                    </div>
                </form>
                <c:if test="${auth.payStatus != 'PS02' || auth.auditStatus == 'AS03'}">
                    <div>
                        <a class="bottom-btn" id="save_personal_btn" style="color:#fff;">下一步</a>
                    </div>
                </c:if>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('.upload-cardImg .upload').click(function(){
                var _this = this;
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:3,msg:'正在上传...'});
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        $(_this).find('img').attr('src', localId);
                        $(_this).find('div.addDiv').remove();
                        $(_this).find('input[class=mediaId]').val(serverId);
                        $.loading.close(200);
                    });
                }, 1);
            });

            $('#save_personal_btn').bind('click', savePersonal);
        });

        function savePersonal() {
            if(!validate()) return;

            ajaxPost('api/apiAuth/savePerson', $('#save_personal_form').serialize(), function(data){
                if(data.success) {
                    href('api/apiAuth/toShopInfo?authId=' + data.obj.id);
                } else {
                    $.loading.close();
                    $.toptip('保存失败');
                }
            },function(){
                $.loading.load({type:1, msg:'信息保存中'});
            }, -1);
        }

        function validate() {
            var userName = $("#userName").val();
            if(Util.checkEmpty(userName)) {
                $.toptip('请输入姓名');
                return false;
            }

            var idNo = $('#idNo').val();
            if(Util.checkEmpty(idNo)) {
                $.toptip('请输入证件号码');
                return false;
            }

            var phone = $('#phone').val();
            if(!Util.checkPhone(phone)) {
                $.toptip('请输入正确联系电话');
                return false;
            }

            var idFront = $("#idFront").val(), idFrontMediaId = $("#idFrontMediaId").val();
            if(Util.checkEmpty(idFront) && Util.checkEmpty(idFrontMediaId)) {
                $.toptip('请上传身份证正面照片');
                return false;
            }

            var idBack = $("#idBack").val(), idBackMediaId = $("#idBackMediaId").val();
            if(Util.checkEmpty(idBack) && Util.checkEmpty(idBackMediaId)) {
                $.toptip('请上传身份证反面照片');
                return false;
            }

            var idFrontByhand = $("#idFrontByhand").val(), idFrontByhandMediaId = $("#idFrontByhandMediaId").val();
            if(Util.checkEmpty(idFrontByhand) && Util.checkEmpty(idFrontByhandMediaId)) {
                $.toptip('请上传手持身份证照片');
                return false;
            }

            return true;
        }
    </script>
</body>

</html>
