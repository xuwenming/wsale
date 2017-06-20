<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>店铺设置</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="店铺设置" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0;">
                <div style="text-align:left;">
                    <div class="fbpp-title">
                        店铺设置
                    </div>
                    <!--<a class="faxian-link">
                        <div style="float:right;">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">店铺公告</div>
                    </a>-->
                    <a href="javascript:href('api/apiAuth/authApply');" class="faxian-link">
                        <div style="float:right;">
                            <span class="grayright-text">
                                <c:choose>
                                    <c:when test="${sessionInfo.isAuth}">已认证</c:when>
                                    <c:otherwise>未认证</c:otherwise>
                                </c:choose>
                            </span>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">实名认证</div>
                    </a>
                    <a href="javascript:href('api/apiWallet/myProtection');" class="faxian-link">
                        <div style="float:right;">
                            <span class="grayright-text">
                                ${protectionMsg}
                            </span>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">消保金</div>
                    </a>
                    <a class="faxian-link returnAddress">
                        <div class="normal-text adrress-text">退货地址</div>
                        <div class="address-detail">
                            <div class="grayright-text tuihuo-address address">
                                <c:choose>
                                    <c:when test="${address == null}">未设置</c:when>
                                    <c:otherwise>
                                        ${address.userName} ${address.telNumber}<br>
                                        ${address.provinceName} ${address.cityName} ${address.countyName} ${address.detailInfo}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="address-arrow">
                                <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                            </div>
                        </div>

                    </a>
                    <div class="fbpp-title">
                        买家出价条件设置
                    </div>
                    <a class="faxian-link isNeedPhone">
                        <div style="float:right;">
                            <c:choose>
                                <c:when test="${shop == null || !shop.isNeedPhone}">
                                    <div class="switch-icon" data-flag="false">
                                        <div class="switch-btn"></div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="switch-icon" data-flag="true" style="background-color: rgb(200, 124, 28);">
                                        <div class="switch-btn" style="float: right;"></div>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                        <div class="normal-text">首次出价需认证手机号</div>
                    </a>
                    <a class="faxian-link isNeedReelId">
                        <div style="float:right;">
                            <c:choose>
                                <c:when test="${shop == null || !shop.isNeedReelId}">
                                    <div class="switch-icon" data-flag="false">
                                        <div class="switch-btn"></div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="switch-icon" data-flag="true" style="background-color: rgb(200, 124, 28);">
                                        <div class="switch-btn" style="float: right;"></div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="normal-text">首次出价需实名认证</div>
                    </a>
                </div>
                <div>
                    <a class="bottom-btn" style="color:#fff;" id="save_shop_set_btn">保存</a>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('.returnAddress').click(function(){
                JWEIXIN.openAddress(function(data){
                    var params = {};
                    if(${address != null}) {
                        if(data.userName == '${address.userName}' && data.postalCode == '${address.postalCode}'
                                && data.provinceName == '${address.provinceName}' && data.cityName == '${address.cityName}'
                                && data.countryName == '${address.countyName}' && data.detailInfo == '${address.detailInfo}'
                                && data.telNumber == '${address.telNumber}') {
                            return;
                        }
                        params.id = '${address.id}';
                    }
                    params.userName = data.userName;
                    params.postalCode = data.postalCode;
                    params.provinceName = data.provinceName;
                    params.cityName = data.cityName;
                    params.countyName = data.countryName;
                    params.detailInfo = data.detailInfo.replace(/[\r\n]/g, "");
                    params.telNumber = data.telNumber;
                    params.atype = 2;

                    ajaxPost('api/apiShop/editAddress', params, function(result){
                        if(result.success) {
                            window.location.reload();
                            //$('.address').html(data.userName + " " + data.telNumber + "<br>" + data.provinceName + " " + data.cityName + " " + data.countryName + " " + data.detailInfo);
                        }
                    });
                    //alert(data.userName + " " + data.postalCode + " " + data.provinceName + " " + data.cityName
                    //+ " " + data.countryName + " " + data.detailInfo + " " + data.nationalCode + " " + data.telNumber)
                });
            });

            $("#save_shop_set_btn").bind('click', saveShopSet);
        });

        function saveShopSet() {
            $("#save_shop_set_btn").unbind('click');
            var isNeedPhone = $('.isNeedPhone .switch-icon').attr("data-flag") == 'true' ? 1 : 0;
            var isNeedReelId = $('.isNeedReelId .switch-icon').attr("data-flag") == 'true' ? 1 : 0;
            ajaxPost('api/apiShop/saveShopSet', {isNeedPhone : isNeedPhone, isNeedReelId : isNeedReelId}, function(result){
                if(result.success) {
                    $.toast("保存成功");
                } else {
                    $.toast("保存失败", "forbidden");
                }
                $("#save_shop_set_btn").bind('click', saveShopSet);
            });
        }
    </script>
</body>

</html>
