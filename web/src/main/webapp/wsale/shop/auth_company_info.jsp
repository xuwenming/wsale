<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>企业信息认证</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="企业信息认证" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div class="renzheng-steptitle">
                    <span <c:if test="${auth.authStep >= 1}">onclick="href('api/apiAuth/auth?authType=${authType}');"</c:if> class="renzheng-current">企业信息</span>
                    <span class="renzheng-aaa">》</span>
                    <span <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toPersonalInfo?authId=${auth.id}');"</c:if>>个人信息</span>
                    <span class="renzheng-aaa">》</span>
                    <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toShopInfo?authId=${auth.id}');"</c:if>>店铺信息</span>
                    <span class="renzheng-aaa">》</span>
                    <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toSubmitAudit?authId=${auth.id}');"</c:if>>提交审核</span>
                </div>
                <form method="post" id="save_company_form">
                    <input type="hidden" name="id" id="authId" value="${auth.id}">
                    <input type="hidden" name="authType" id="authType" value="${authType}">
                    <div class="renzheng-input">
                        <div class="fbpp-title">
                            企业认证(请上传真实的企业信息，认证通过后无法进行修改)
                        </div>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="text" placeholder="请输入企业(公司)名称" name="companyName" id="companyName" value="${auth.companyName}"/>
                            </div>
                            <div class="normal-text">企业名称</div>
                        </a>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="text" placeholder="请输入" name="creditId" id="creditId" value="${auth.creditId}" />
                            </div>
                            <div class="normal-text">统一社会信用码</div>
                        </a>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="text" placeholder="请输入企业(公司)法人名称" name="legalPersonName" id="legalPersonName" value="${auth.legalPersonName}" />
                            </div>
                            <div class="normal-text">法人姓名</div>
                        </a>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="hidden" name="idType" value="IT01">
                                <span class="grayright-text">身份证</span><img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                            </div>
                            <div class="normal-text">证件类型</div>
                        </a>
                        <a class="faxian-link">
                            <div class="list-right">
                                <input type="text" placeholder="请输入" name="legalPersonId" value="${auth.legalPersonId}" id="legalPersonId" />
                            </div>
                            <div class="normal-text">证件号码</div>
                        </a>
                        <div class="fbpp-title">
                            上传证件
                        </div>
                        <div class="upload-cardImg">
                            <div class="upload-card upload">
                                <div>身份证正面照片</div>
                                <c:choose>
                                    <c:when test="${empty auth.legalPersonIdFront}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/shenfenzheng1-img.png" />
                                        <div class="upload-opacity addDiv">添加照片</div>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${auth.legalPersonIdFront}" />
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" class="mediaId" name="legalPersonIdFrontMediaId" id="legalPersonIdFrontMediaId">
                                <input type="hidden" id="legalPersonIdFront" value="${auth.legalPersonIdFront}">
                            </div>
                            <div class="upload-card upload" style="float:right;">
                                <div>身份证反面照片</div>
                                <c:choose>
                                    <c:when test="${empty auth.legalPersonIdBack}">
                                        <img src="${pageContext.request.contextPath}/wsale/images/shenfenzheng2-img.png" />
                                        <div class="upload-opacity addDiv">添加照片</div>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${auth.legalPersonIdBack}" />
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" class="mediaId" name="legalPersonIdBackMediaId" id="legalPersonIdBackMediaId">
                                <input type="hidden" id="legalPersonIdBack" value="${auth.legalPersonIdBack}">
                            </div>
                            <div class="upload-shouchi upload">
                                <div>营业执照</div>
                                <c:choose>
                                    <c:when test="${empty auth.bussinessLicense}">
                                        <img class="upload-shouchiimg" src="${pageContext.request.contextPath}/wsale/images/qiyerenzheng-img.png" />
                                        <div class="upload-opacity2 addDiv">添加照片</div>
                                    </c:when>
                                    <c:otherwise>
                                        <img class="upload-shouchiimg" src="${auth.bussinessLicense}" />
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" class="mediaId" name="bussinessLicenseMediaId" id="bussinessLicenseMediaId">
                                <input type="hidden" id="bussinessLicense" value="${auth.bussinessLicense}">
                            </div>
                        </div>
                    </div>
                </form>
                <c:if test="${auth.payStatus != 'PS02' || auth.auditStatus == 'AS03'}">
                    <div>
                        <a id="save_company_btn" class="bottom-btn" style="color:#fff;">下一步</a>
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

            $('#save_company_btn').bind('click', saveCompany);
        });

        function saveCompany() {
            if(!validate()) return;

            ajaxPost('api/apiAuth/saveCompany', $('#save_company_form').serialize(), function(data){
                if(data.success) {
                    href('api/apiAuth/toPersonalInfo?authId=' + data.obj);
                } else {
                    $.loading.close();
                    $.toptip('保存失败');
                }
            },function(){
                $.loading.load({type:1, msg:'信息保存中'});
            }, -1);
        }

        function validate() {
            var companyName = $("#companyName").val();
            if(Util.checkEmpty(companyName)) {
                $.toptip('请输入企业名称');
                return false;
            }

            var creditId = $('#creditId').val();
            if(Util.checkEmpty(creditId)) {
                $.toptip('请输入统一社会信用码');
                return false;
            }

            var legalPersonName = $('#legalPersonName').val();
            if(Util.checkEmpty(legalPersonName)) {
                $.toptip('请输入法人姓名');
                return false;
            }

            var legalPersonId = $('#legalPersonId').val();
            if(Util.checkEmpty(legalPersonId)) {
                $.toptip('请输入证件号码');
                return false;
            }

            var legalPersonIdFront = $("#legalPersonIdFront").val(), legalPersonIdFrontMediaId = $("#legalPersonIdFrontMediaId").val();
            if(Util.checkEmpty(legalPersonIdFront) && Util.checkEmpty(legalPersonIdFrontMediaId)) {
                $.toptip('请上传身份证正面照片');
                return false;
            }

            var legalPersonIdBack = $("#legalPersonIdBack").val(), legalPersonIdBackMediaId = $("#legalPersonIdBackMediaId").val();
            if(Util.checkEmpty(legalPersonIdBack) && Util.checkEmpty(legalPersonIdBackMediaId)) {
                $.toptip('请上传身份证反面照片');
                return false;
            }

            var bussinessLicense = $("#bussinessLicense").val(), bussinessLicenseMediaId = $("#bussinessLicenseMediaId").val();
            if(Util.checkEmpty(bussinessLicense) && Util.checkEmpty(bussinessLicenseMediaId)) {
                $.toptip('请上传营业执照');
                return false;
            }

            return true;
        }
    </script>
</body>

</html>
