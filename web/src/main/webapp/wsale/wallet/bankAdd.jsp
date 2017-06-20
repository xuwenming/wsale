<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>添加银行卡</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="添加银行卡" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0;">
                <div class="renzheng-input">
                    <div class="fbpp-title">
                        银行卡信息（必填）
                    </div>
                    <form method="post" id="save_submit_form">
                                        <input type="hidden" name="id" id="authId" value="${auth.id}">
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" placeholder="开户名" name="openName" id="openName"  />
                        </div>
                        <div class="normal-text">开户名</div>
                    </a>
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" placeholder="身份证号"  name="bodyNum" id="bodyNum" />
                        </div>
                        <div class="normal-text">身份证号</div>
                    </a>
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" placeholder="支持信用卡、借记卡" name="bankCard" id="bankCard"  />
                        </div>
                        <div class="normal-text">银行卡号</div>
                    </a>

                    <div class="fbpp-title">
                        更多银行卡信息（余额提现时必须）
                    </div>
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" placeholder="开户行时所填写的手机号" name="mobileNum" id="mobileNum"/>
                        </div>
                        <div class="normal-text">手机号</div>
                    </a>
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" placeholder="开户行所在省份 如：浙江" name="provinceName" id="provinceName" />
                        </div>
                        <div class="normal-text">开户行省份</div>
                    </a>
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" placeholder="开户行所在省份 如：杭州" name="placeName" id="placeName" />
                        </div>
                        <div class="normal-text">开户行城市</div>
                    </a>
                    <a class="faxian-link">
                        <div class="list-right">
                            <input type="text" placeholder="开户行银行支行名" name="bankName" id="bankName" />
                        </div>
                        <div class="normal-text">开户行支行</div>
                    </a>

                    <div class="fbpp-title">
                        上传资质文件（余额提现时必须）
                    </div>
                    <div class="upload-cardImg">
                        <div class="upload-card">
                            <div>身份证正面照片</div>
                             <c:choose>
                                 <c:when test="${empty auth.idFront}">
                            <img src="${pageContext.request.contextPath}/wsale/images/shenfenzheng1-img.png" />
                            <div class="upload-opacity">添加照片</div>
                                 </c:when>
                                 <c:otherwise>
                                     <img src="${auth.idFront}" />
                                 </c:otherwise>
                             </c:choose>
                            <input type="hidden" name="idFrontMediaId" id="idFrontMediaId">
                            <input type="hidden" id="idFront" value="${auth.idFront}">
                        </div>
                        <div class="upload-card" style="float:right;">
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
                            <input type="hidden" name="idBackMediaId" id="idBackMediaId">
                            <input type="hidden" id="idBack" value="${auth.idBack}">
                        </div>
                        <div class="upload-shouchi">
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
                            <input type="hidden" name="idFrontByhandMediaId" id="idFrontByhandMediaId">
                            <input type="hidden" id="idFrontByhand" value="${auth.idFrontByhand}">
                        </div>

                        <div class="upload-shouchi">
                            <div>银行卡正面照片</div>
                            <c:choose>
                                 <c:when test="${empty auth.idFront}">
                            <img class="upload-shouchiimg" src="${pageContext.request.contextPath}/wsale/images/yinghangka-img.png" />
                            <div class="upload-opacity2">添加照片</div>
                                 </c:when>
                                <c:otherwise>
                                    <img class="upload-shouchiimg" src="${auth.idFrontByhand}" />
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" name="idBankNumMediaId" id="idBankNumMediaId">
                            <input type="hidden" id="idBankNum" value="${auth.idBankNum}">
                        </div>
                    </div>
                        </form>
                </div>

                <div>

                    <a class="bottom-btn" style="color:#fff;">保存</a>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('.upload-cardImg .upload').click(function(){
                var _this = this;
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:4,msg:'正在上传...'});
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        $(_this).find('img').attr('src', localId);
                        $(_this).find('div.addDiv').remove();
                        $(_this).find('input[name]').val(serverId);
                        $.loading.close(200);
                    });
                }, 1);
            });

            $('.bottom-btn').bind('click', submitSave);
        });

        function submitSave() {
            if(!validate()) return;

            ajaxPost('api/apiAuth/submitSave', $('#save_submit_form').serialize(), function(data){
                if(data.success) {
                    href('api/apiAuth/toShopInfo?authId=' + data.obj.id + "&authType=" + data.obj.authType);
                } else {
                    $.loading.close();
                    $.toptip('保存失败');
                }
            },function(){
                $.loading.load({type:1, msg:'信息保存中'});
            }, -1);
        }

        function validate() {
            var openName = $("#openName").val();
            if(Util.checkEmpty(openName)) {
                $.toptip('请输入开户名');
                return false;
            }

            var bodyNum = $('#bodyNum').val();
            if(Util.checkEmpty(bodyNum)) {
                $.toptip('请输入身份证号');
                return false;
            }

            var bankCard = $('#bankCard').val();
            if(!Util.checkPhone(bankCard)) {
                $.toptip('请输入正确银行卡号');
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
            var  idBankNum = $("#idBankNum").val(), idBankNumMediaId = $("#idBankNumMediaId").val();
            if(Util.checkEmpty(idBankNum) && Util.checkEmpty(idFrontByhandMediaId)) {
                $.toptip('请上传银行卡正面照片');
                return false;
            }
            return true;
        }



    </script>
</body>

</html>
