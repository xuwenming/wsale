<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>余额明细</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0;">
                <div class="yue-zhifu">
                    <div class="big-text pay-text">
                        <c:choose>
                            <c:when test="${detail.isIncome and detail.amount > 0}">收入金额</c:when>
                            <c:otherwise>支出金额</c:otherwise>
                        </c:choose>
                    </div>
                    <div class="pay-value">
                        <c:if test="${detail.amount > 0}">
                            <c:choose>
                                <c:when test="${detail.isIncome}">+</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </c:if>
                        <fmt:formatNumber type="number" value="${detail.amount}" pattern="0.00" maxFractionDigits="2"/>元
                    </div>
                </div>

                <div class="renzheng-input" style="border-top:10px solid #f5f5f5;">
                    <a class="faxian-link">
                        <div class="right-value normal-text">
                            ${detail.orderNo}
                        </div>
                        <div class="normal-text">交易号</div>
                    </a>
                    <a class="faxian-link">
                        <div class="right-value normal-text">
                            <fmt:formatDate value="${detail.addtime}" pattern="MM月dd日 HH:mm"/>
                        </div>
                        <div class="normal-text">交易时间</div>
                    </a>
                    <a class="faxian-link">
                        <div class="right-value normal-text">
                            <c:choose>
                                <c:when test="${detail.wtype == 'WT01' and detail.amount < 0}">扣款</c:when>
                                <c:otherwise>${detail.wtypeZh}</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="normal-text">
                            交易种类
                        </div>
                    </a>
                    <c:choose>
                        <c:when test="${detail.wtype == 'WT02'}">
                            <a class="faxian-link">
                                <div class="right-value normal-text">
                                    <c:choose>
                                        <c:when test="${detail.channel == 'CS01'}">
                                            微信
                                        </c:when>
                                        <c:when test="${detail.channel == 'CS03'}">
                                            银行卡
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div class="normal-text">提取到</div>
                            </a>
                            <c:if test="${detail.channel == 'CS03'}">
                                <a class="faxian-link">
                                    <div class="right-value normal-text">
                                            ${detail.bankAccount}
                                    </div>
                                    <div class="normal-text">开户名</div>
                                </a>
                                <a class="faxian-link">
                                    <div class="right-value normal-text">
                                            ${detail.bankPhone}
                                    </div>
                                    <div class="normal-text">手机号</div>
                                </a>
                                <a class="faxian-link">
                                    <div class="right-value normal-text">
                                            ${detail.bankIdNo}
                                    </div>
                                    <div class="normal-text">身份证号</div>
                                </a>
                                <a class="faxian-link">
                                    <div class="right-value normal-text">
                                            ${detail.bankName}
                                    </div>
                                    <div class="normal-text">开户银行</div>
                                </a>
                                <a class="faxian-link">
                                    <div class="right-value normal-text">
                                            ${detail.bankCard}
                                    </div>
                                    <div class="normal-text">银行卡号</div>
                                </a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <a class="faxian-link">
                                <div class="right-value normal-text">
                                    <c:choose>
                                        <c:when test="${detail.wtype == 'WT01' and detail.amount < 0}">后台扣款</c:when>
                                        <c:otherwise>${detail.channelZh}</c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="normal-text">支付方式</div>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <a class="faxian-link">
                        <div class="right-value normal-text">
                           ${detail.description}
                        </div>
                        <div class="normal-text">备注</div>
                    </a>
                    <c:if test="${detail.wtype == 'WT02' && detail.handleStatus != 'HS03'}">
                        <a class="faxian-link">
                            <div class="right-value normal-text">
                                <c:choose>
                                    <c:when test="${detail.handleStatus == 'HS04'}">
                                        提现失败
                                    </c:when>
                                    <c:otherwise>处理中</c:otherwise>
                                </c:choose>
                            </div>
                            <div class="normal-text">状态</div>
                        </a>
                        <c:if test="${detail.handleStatus == 'HS04'}">
                            <a class="faxian-link">
                                <div class="right-value normal-text">
                                        ${detail.handleRemark}
                                </div>
                                <div class="normal-text">失败原因</div>
                            </a>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div><!-- /content -->
    </div><!-- /page -->

    <script type="text/javascript">
        
    </script>
</body>

</html>
