<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>实名认证</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="个人实名认证" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div class="smrz-title">个人实名认证流程</div>
                <div>
                    <div class="renzheng-flow flow-step1">
                        <div class="step-img">
                            <img src="${pageContext.request.contextPath}/wsale/images/step-1icon.png" />
                        </div>
                        <div>
                            填写真实姓名、联系电话、身份证号并上传身份正面、反面和手持身份证的清晰照。
                        </div>
                    </div>

                    <div class="renzheng-flow flow-step2">
                        <div class="step-img">
                            <img src="${pageContext.request.contextPath}/wsale/images/step-2icon.png" />
                        </div>
                        <div class="step2-desc">
                            设置店铺名称、店铺logo即店铺介绍。
                        </div>
                    </div>

                    <div class="renzheng-flow flow-step3">
                        <div class="step-img">
                            <img src="${pageContext.request.contextPath}/wsale/images/step-3icon.png" />
                        </div>
                        <div class="step3-desc">
                            提交实名认证申请后，工作人员将在10个工作日与您联系核对信息。
                        </div>
                    </div>
                </div>
                <div class="renzheng-btn">
                    <a href="javascript:href('api/apiAuth/auth?authType=AT01');" class="bottom-btn" style="color:#fff;">我要实名认证</a>
                    <div>点击即表示同意《微信拍认证增值服务协议》</div>
                </div>
            </div>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        
    </script>
</body>

</html>
