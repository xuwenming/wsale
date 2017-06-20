<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jb.listener.Application"%>
<%
    String totalFee_1 = Application.getString("AF01");
    String totalFee_2 = Application.getString("AF02");
    totalFee_1 = totalFee_1 == null ? "300" : totalFee_1;
    totalFee_2 = totalFee_2 == null ? "300" : totalFee_2;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${auth.authType == 'AT01'}">提交个人认证申请</c:when>
            <c:otherwise>提交企业认证申请</c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">
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
                                <img src="${pageContext.request.contextPath}/wsale/images/step3-icon.png" class="renzheng-stepimg" /> <span>提交审核</span>
                            </step>
                        </c:when>
                        <c:otherwise>
                            <span <c:if test="${auth.authStep >= 1}">onclick="href('api/apiAuth/auth?authType=${auth.authType}');"</c:if> class="renzheng-current">企业信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 2}">onclick="href('api/apiAuth/toPersonalInfo?authId=${auth.id}');"</c:if> class="renzheng-current">个人信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toShopInfo?authId=${auth.id}');"</c:if> class="renzheng-current">店铺信息</span>
                            <span class="renzheng-aaa">》</span>
                            <span <c:if test="${auth.authStep >= 3}">onclick="href('api/apiAuth/toSubmitAudit?authId=${auth.id}');"</c:if> class="renzheng-current">提交审核</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="renzheng-input" style="border-top:10px solid #f5f5f5;">
                    <c:choose>
                        <c:when test="${auth.authType == 'AT01'}">
                            <a class="faxian-link">
                                <div class="right-value normal-text">个人实名认证</div>
                                <div class="normal-text">认证类型</div>
                            </a>
                            <a class="faxian-link">
                                <div class="right-value normal-text">
                                    ${auth.userName}
                                </div>
                                <div class="normal-text">姓名</div>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a class="faxian-link">
                                <div class="right-value normal-text">企业实名认证</div>
                                <div class="normal-text">认证类型</div>
                            </a>
                            <a class="faxian-link">
                                <div class="right-value normal-text">
                                    ${auth.companyName}
                                </div>
                                <div class="normal-text">企业名称</div>
                            </a>
                            <a class="faxian-link">
                                <div class="right-value normal-text">
                                    ${auth.legalPersonName}
                                </div>
                                <div class="normal-text">法人姓名</div>
                            </a>
                            <a class="faxian-link">
                                <div class="right-value normal-text">
                                    ${auth.userName}
                                </div>
                                <div class="normal-text">联系人姓名</div>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <a class="faxian-link">
                        <div class="right-value normal-text">
                            ${auth.phone}
                        </div>
                        <div class="normal-text">联系电话</div>
                    </a>
                    <a class="faxian-link">
                        <div class="right-value normal-text">
                            ${auth.idNo}
                        </div>
                        <div class="normal-text">身份证号</div>
                    </a>
                    <a class="faxian-link">
                        <div class="right-value normal-text">
                            ${shop.name}
                        </div>
                        <div class="normal-text">店铺名称</div>
                    </a>
                    <c:if test="${auth.payStatus != 'PS02' || auth.auditStatus == 'AS03'}">
                        <div class="back-info" onclick="href('api/apiAuth/auth?authType=${auth.authType}');">
                            返回修改信息》
                        </div>
                    </c:if>
                </div>
                <c:if test="${auth.payStatus != 'PS02' || auth.auditStatus == 'AS03'}">
                    <div>
                        <a class="bottom-btn" id="submit_audit_btn" style="color:#fff;">支付认证费用并提交审核</a>
                    </div>
                </c:if>
            </div>
        </div><!-- /content -->
    </div><!-- /page -->
    
    <script type="text/javascript">
        var authType = '${auth.authType}';
        $(function(){
            $('#submit_audit_btn').click(function(){
                var totalFee = authType == 'AT01' ? '<%=totalFee_1 %>' : '<%=totalFee_2 %>';
                href('api/pay/toPay?objectId=${auth.id}&objectType=PO02&attachType=${auth.authType}&totalFee=' + totalFee);
            });
        });
    </script>
</body>

</html>
