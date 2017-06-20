<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>余额充值</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div class="bank-layer"></div>
                <div class="support-bank">
                    <div style="margin:10px;">
                        <span class="big-text" style="font-weight:bold;">支持银行及限额说明</span>
                        <span class="pay-desc">(已支持16家银行)</span>
                    </div>
                    <div style="margin:0px 10px;">
                        <table class="supbank-list" cellpadding="0" cellspacing="0">
                            <thead>
                            <th>支持银行</th>
                            <th>借记卡额度</th>
                            <th>信用卡额度</th>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/gsyh-icon.png" /><span>工商银行</span>
                                </td>
                                <td>5000</td>
                                <td>不支持</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/syyh-icon.png" /><span>中信银行</span>
                                </td>
                                <td>5000</td>
                                <td>20000</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/zgyh-icon.png" /><span>中国银行</span>
                                </td>
                                <td>5000</td>
                                <td>不支持</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/gsyh-icon.png" /><span>工商银行</span>
                                </td>
                                <td>5000</td>
                                <td>不支持</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/nyyh-icon.png" /><span>农业银行</span>
                                </td>
                                <td>5000</td>
                                <td>2000</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/gsyh-icon.png" /><span>工商银行</span>
                                </td>
                                <td>5000</td>
                                <td>不支持</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/syyh-icon.png" /><span>中信银行</span>
                                </td>
                                <td>5000</td>
                                <td>20000</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/zgyh-icon.png" /><span>中国银行</span>
                                </td>
                                <td>5000</td>
                                <td>不支持</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/gsyh-icon.png" /><span>工商银行</span>
                                </td>
                                <td>5000</td>
                                <td>不支持</td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="bank-logo" src="images/nyyh-icon.png" /><span>农业银行</span>
                                </td>
                                <td>5000</td>
                                <td>2000</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="dialog-know">我知道了</div>
                </div>
                <div class="chongzhi-value">
                    <div class="chongzhi-jine">
                        <span class="big-text" style="margin-left:10px;">金额</span>
                        <input type="tel" placeholder="请输入充值金额" id="amount"/>
                    </div>
                </div>
                <div style="margin-top:30px;">
                    <a class="bottom-btn" style="color:#fff; margin-bottom:10px;">下一步</a>
                    <!--<div class="text-desc">支持银行及限额说明</div>-->
                </div>
            </div>
        </div><!-- /content -->
        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->

    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('.bottom-btn').click(function(){
                var amount = $("#amount").val();
                if(Util.checkEmpty(amount)) {
                    $("#amount").focus();
                    return;
                }
                href('api/pay/toRecharge?objectType=PO06&totalFee=' + amount + '&backUrl=' + encodeURIComponent(document.referrer));
            });
        });
    </script>
</body>

</html>
