<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>个人信息编辑</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="个人信息编辑" class="jqm-demos" style="background-color:#f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">

            <div id="syncPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 190px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden;font-size: 14px; ">
                        <div>
                            更新时会同步微信头像。
                        </div>
                        <div>
                            确认更新用户信息吗？
                        </div>
                        <a class="bottom-btn guanzhu-ok" style="color: #fff;font-size: 16px;">确定</a>
                        <a class="bottom-btn close-popup" style="color: #fff;font-size: 16px;background-color: #aaa;">取消</a>
                    </div>
                </div>
            </div>
            <div class="home-content" style="margin:0; text-align:left;">
                <div class="top-info">
                    <div class="wode-touxiang">
                        <img src="${user.headImage}" src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'"  style="border:2px solid #fff;" class="wode-userimg" />
                    </div>
                    <div class="renzheng-flag" id="syncBtn">
                        <img src="${pageContext.request.contextPath}/wsale/images/refresh-icon.png" />
                        <div>刷新</div>
                    </div>
                    <div>
                        <div class="renzheng-username">
                            <span>${user.nickname}</span>
                        </div>
                        <div class="renzheng-note">
                            <span>(头像将同步微信)</span>
                        </div>
                    </div>
                </div>
                <div class="userset-content">
                    <a href="javascript:href('api/userController/nickname?nickname=${user.nickname}');" class="userset-list">
                        <div style="float:right;">
                            <span class="grayright-text">${user.nickname}</span><img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">昵称</div>
                    </a>
                    <a href="javascript:href('api/userController/bardian?bardian=${user.bardian}');" class="userset-list">
                        <div style="float:right;">
                            <span class="grayright-text">${user.bardian}</span><img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">个人签名</div>
                    </a>
                    <a href="javascript:href('api/userController/contact?contact=${user.contact}');" class="userset-list">
                        <div style="float:right;">
                            <span class="grayright-text">${user.contact}</span><img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">联系人</div>
                    </a>
                    <a href="javascript:href('api/userController/wechatNo?wechatNo=${user.wechatNo}');" class="userset-list">
                        <div style="float:right;">
                            <span class="grayright-text">${user.wechatNo}</span><img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">微信号</div>
                    </a>
                    <a href="javascript:href('api/userController/mobile?mobile=${user.mobile}');" class="userset-list">
                        <div style="float:right;">
                            <span class="grayright-text">${user.mobile}</span><img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text">手机号码</div>
                    </a>
                    <a class="userset-list receiptAddress">
                        <div class="normal-text adrress-text">收货地址</div>
                        <div class="address-detail">
                            <div class="grayright-text tuihuo-address address">
                                <c:if test="${address != null}">
                                    ${address.userName} ${address.telNumber}<br>
                                    ${address.provinceName} ${address.cityName} ${address.countyName} ${address.detailInfo}
                                </c:if>
                            </div>
                            <div class="address-arrow">
                                <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div><!-- /content -->


        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/apiFindController/find');" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->

    </div><!-- /page -->

    <script type="text/javascript">
        $(function(){
            $('#syncBtn').click(function(){
                //$(".mask-layer,.fensi-dialog").show();
                $('#syncPopup').wePopup();
            });

            $('.guanzhu-ok').click(function(){
                ajaxPost('api/userController/syncInfo', {}, function(data){
                    if(data.success) {
                        replace('api/userController/my');
                    }
                }, function(){
                    $.loading.load({type:1, msg:'更新用户信息...'});
                }, -1);
            });

            $('.receiptAddress').click(function(){
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
                    params.atype = 1;

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
        });
    </script>
</body>

</html>
