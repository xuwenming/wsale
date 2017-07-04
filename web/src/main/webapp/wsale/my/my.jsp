<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="jb.listener.Application"%>
<%
    String helpUrl = Application.getString("WP400");
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.my.order.list.css"/>
    <style>
        .redPoint {
            margin: -48px 10px;
            min-width: 0;
            min-height: 0;
            width: 8px;
            height: 8px;
        }
    </style>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; text-align:left; ">
                <a href="javascript:href('api/userController/homePage?userId=${user.id}');" class="wode-userinfo">
                    <div class="wode-touxiang">
                        <img src="${user.headImage}" style="border:2px solid #fff;" class="wode-userimg" />
                        <img src="${pageContext.request.contextPath}/wsale/images/p-${user.positionId}.png" class="wode-userflag" />
                    </div>
                    <div style="float:right;margin-top:15px;">
                        <img src="${pageContext.request.contextPath}/wsale/images/jiantou-white.png" />
                    </div>
                    <div>
                        <div>
                            <span class="wode-username">${user.nickname}</span>&nbsp;&nbsp;
                            <c:if test="${user.isAuth}">
                                <img src="${pageContext.request.contextPath}/wsale/images/shimingrenzheng-icon.png" class="wode-flag" />
                            </c:if>
                            <c:if test="${user.isPayBond}">
                                <img src="${pageContext.request.contextPath}/wsale/images/baozhengjin-white.png" class="wode-flag" />
                            </c:if>
                        </div>
                        <div class="wode-info">
                            <img class="wode-level" src="${pageContext.request.contextPath}/wsale/images/v2.png" />
                            <span>桃子：100</span> <span>|</span> <span>信誉：${order_status_count.OS10}</span>
                        </div>
                    </div>
                </a>
                <div style="border-top: 10px solid #f5f5f5;">
                    <div class="my-order-title" onclick="href('api/apiOrder/myOrder?type=0');">
                        <span class="order-left">我的订单</span>
                        <div style="float: right;">
                            <span  class="grayright-text" style="font-size: 14px;">全部</span>
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                    </div>
                    <ul class="wode-operate myOrder">
                        <li>
                            <a>
                                <c:if test="${order_count.unpay_count > 0}">
                                    <span class="infocenter-number">${order_count.unpay_count}</span>
                                </c:if>
                                <img src="${pageContext.request.contextPath}/wsale/images/daifukuan-icon.png" />
                                <div>待付款</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <c:if test="${order_count.unreceipt_count > 0}">
                                    <span class="infocenter-number">${order_count.unreceipt_count}</span>
                                </c:if>
                                <img src="${pageContext.request.contextPath}/wsale/images/daishouhuo-icon.png" />
                                <div>待收货</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <c:if test="${order_count.undeliver_count > 0}">
                                    <span class="infocenter-number">${order_count.undeliver_count}</span>
                                </c:if>
                                <img src="${pageContext.request.contextPath}/wsale/images/weifahuo-icon.png" />
                                <div>未发货</div>
                            </a>
                        </li>
                        <li>
                            <a>
                                <c:if test="${order_count.uncomment_count > 0}">
                                    <span class="infocenter-number">${order_count.uncomment_count}</span>
                                </c:if>
                                <img src="${pageContext.request.contextPath}/wsale/images/diapingjia-icon.png" />
                                <div>待评价</div>
                            </a>
                        </li>
                        <li>
                            <a><img src="${pageContext.request.contextPath}/wsale/images/shouhouchuli-icon.png" />
                                <div>售后处理</div></a>
                        </li>
                    </ul>
                </div>
                <div class="faxian-list my-faxian-flex">
                    <a href="javascript:href('api/apiWallet/myWallet');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/yue-icon.png" class="faxian-lefticon" />
                            <div>余额</div>
                        </div>
                        <div>
                            <span style="color:#1AAFF0; font-size:12px;"><fmt:formatNumber type="number" value="${amount}" pattern="0.00" maxFractionDigits="2"/></span>
                        </div>
                    </a>
                    <a class="faxian-link" href="javascript:href('api/apiChat/chat_list');">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/xiaoxizhongxin-icon.png" class="faxian-lefticon" />
                            <div>消息中心</div>
                        </div>
                        <div>
                            <c:if test="${chat_unread_count > 0}">
                                <span class="infocenter-number redPoint"></span>
                            </c:if>
                        </div>

                    </a>
                    <a href="javascript:href('api/apiProductController/productManage');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/paipin-icon.png" class="faxian-lefticon" />
                            <div>拍品管理</div>
                        </div>
                        <div>
                            <span class="grayright-text2">竞拍中${auction_in_count}单</span>
                            <%--<img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />--%>
                        </div>

                    </a>
                    <!--<a class="faxian-link">
                        <div style="float:right;">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/xiaoshoubaobiao-icon.png" class="faxian-lefticon" /> 销售报表</div>
                    </a>-->
                    <a href="javascript:href('api/apiShop/myShopSet');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/dianpushezhi-icon.png" class="faxian-lefticon" />
                            <div>店铺设置</div>
                        </div>
                        <div>
                            <span class="grayright-text2">
                                <c:choose>
                                    <c:when test="${user.isAuth}">已认证</c:when>
                                    <c:otherwise>未认证</c:otherwise>
                                </c:choose>

                            </span>
                        </div>
                    </a>
                    <a href="javascript:href('api/userController/myCollect');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/shoucang-icon.png" class="faxian-lefticon" />
                            <div>收藏</div>
                        </div>
                    </a>
                    <a href="javascript:href('api/userController/myComment');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/pinglun-icon.png" class="faxian-lefticon" />
                            <div>评论</div>
                        </div>
                    </a>
                    <a href="javascript:href('api/userController/myFans');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/fensi-icon.png" class="faxian-lefticon" />
                            <div>粉丝</div>
                        </div>
                        <div>
                            <span style="color:#dc721c; font-size:12px;">${user.fans}</span>
                        </div>
                    </a>
                    <a href="javascript:href('api/userController/shieldors');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/pingbiyonghu-icon.png" class="faxian-lefticon" />
                            <div>屏蔽用户</div>
                        </div>
                        <div>
                            <span style="color:#dc721c; font-size:12px;">${user.shieldors}</span>
                        </div>
                    </a>
                    <a href="javascript:href('api/userController/myTheme');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/wodezhuti-icon.png" class="faxian-lefticon" />
                            <div>我的主题</div>
                        </div>
                    </a>
                <!--<a class="faxian-link">
                        <div style="float:right;">
                            <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/yongjinbaobiao-icon.png" class="faxian-lefticon" /> 佣金报表</div>
                    </a>-->

                    <a href="javascript:href('api/userController/info');" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/yonghushezhi-icon.png" class="faxian-lefticon" />
                            <div>用户设置</div>
                        </div>
                        <div>
                            <%--<img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />--%>
                        </div>

                    </a>
                    <a href="<%=helpUrl %>" class="faxian-link">
                        <div class="normal-text"><img src="${pageContext.request.contextPath}/wsale/images/help-icon.png" class="faxian-lefticon" />
                            <div>帮助中心</div>
                        </div>
                        <div>
                            <%--<img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />--%>
                        </div>
                    </a>
                </div>
                <div>
                    <ul class="wode-paipin">
                        <li id="canpaipaipin" class="wodepaipin-active">参拍拍品</li>
                        <li id="dianzanpaipin">点赞拍品</li>
                        <li id="guanzhudianpu">关注店铺</li>
                    </ul>
                </div>
                <div style="padding:0 5px 5px 5px;">
                    <div class="canpaipaipin">
                        <div class="cbp-vm-switcher cbp-vm-view-grid" style="padding:0;">
                            <ul class="auction-list" style="background-color: #f5f5f5;">
                            </ul>
                        </div>
                    </div>
                    <div class="dianzanpaipin">
                        <div class="cbp-vm-switcher cbp-vm-view-grid" style="padding:0;">
                            <ul class="like-list">
                            </ul>
                        </div>
                    </div>
                    <div class="guanzhudianpu">
                        <div class="guanzhu-list" style="padding-bottom: 0px;">
                        </div>
                    </div>
                </div>
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    正在加载中
                </div>
            </div>
            <jsp:include page="../template/product_template.jsp"></jsp:include>
            <jsp:include page="../template/person_template.jsp"></jsp:include>

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
        var loading = true;
        var currPage = 1;
        var rows = 10, userRows = 30;
        var scrollTop = 0;
        $(function(){
            $(document.body).on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    var num = $("li.wodepaipin-active").index();
                    if(num == 0) drawProduct(1);
                    else if(num == 1) drawProduct(2);
                    else drawUsers();
                }, 20);
            });

            var obj = $.cookie('my');
            if(obj != null) {
                $.cookie('my', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                var num = obj.num;
                $("li.wodepaipin-active").removeClass('wodepaipin-active');
                $(".wode-paipin li").eq(num).addClass('wodepaipin-active');
                var flId = $(".wode-paipin li").eq(num).attr("id");
                $("."+flId).show();
                $("."+flId).siblings().hide();
                if(num == 0) drawProduct(1, obj.currPage);
                else if(num == 1) drawProduct(2, obj.currPage);
                else drawUsers(obj.currPage);
            } else {
                drawProduct();
            }

            $('.wode-paipin li').click(function() {
                currPage = 1;
                $(document.body).destroyInfinite();
                var num = $(this).index();
                if(num == 0) {
                    $(".auction-list").empty();
                    $(".home-content .weui-infinite-scroll").show();
                    drawProduct(1);
                }
                else if(num == 1) {
                    $(".like-list").empty();
                    $(".home-content .weui-infinite-scroll").show();
                    drawProduct(2);
                }
                else {
                    $(".guanzhu-list").empty();
                    $(".home-content .weui-infinite-scroll").show();
                    drawUsers();
                }
            });

            $('.myOrder li').click(function(){
                var type = $(this).index() + 1;
                href('api/apiOrder/myOrder?type=' + type);
            });
        });

        // type:1-参拍拍品；2-点赞拍品
        function drawProduct(type, page) {
            type = type || 1, currPage = page || currPage;
            var url = 'api/apiProductLikeController/productLikeList';
            if(type == 1) url = 'api/apiAuctionController/auctionProductList';
            ajaxPost(url, {page:(page && 1) || currPage, rows:(page && page*rows) || rows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var product = result.rows[i].zcProduct;
                            buildProduct(product, type);
                        }
                        $(".lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0) {
                            if(type == 1) $(".auction-list").append(Util.noDate(2, '暂无参拍拍品'));
                            else $(".like-list").append(Util.noDate(2, '暂无点赞拍品'));
                        }
                    }

                    if(result.rows.length >= rows) {
                        loadShow()
                    } else {
                        loadHide();
                    }

                    if(scrollTop > 0) {
                        $.mobile.silentScroll(scrollTop);
                        scrollTop = 0;
                    }
                } else {
                    loadHide();
                }
            });
        }

        function buildProduct(product, type) {
            var viewData = Util.cloneJson(product);
            viewData.currentPrice = '￥' + product.currentPrice;
            var dom = Util.cloneDom("product_template", product, viewData);
            dom.find('.cbp-vm-right span:eq(1)').remove();
            if(type == 1) $(".auction-list").append(dom);
            else $(".like-list").append(dom);
            // dom绑定事件
            dom.find('.cbp-vm-image').click(product.id, function(event){
                $.cookie('my', JSON.stringify({num:$("li.wodepaipin-active").index(), scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/apiProductController/productDetail?id=' + event.data);
            });
            return dom;
        }

        function drawUsers(page) {
            currPage = page || currPage;
            ajaxPost('api/userController/shieldorAttrs', {page:(page && 1) || currPage, rows:(page && page*userRows) || userRows, objectType:'FS'}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var user = result.rows[i];
                            buildUser(user);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".guanzhu-list").append(Util.noDate(1, '暂无关注'));
                    }
                    if(result.rows.length >= userRows) {
                        loadShow()
                    } else {
                        loadHide();
                    }

                    if(scrollTop > 0) {
                        $.mobile.silentScroll(scrollTop);
                        scrollTop = 0;
                    }
                } else {
                    loadHide();
                }
            });
        }

        function buildUser(user) {
            var viewData = Util.cloneJson(user);
            var dom = Util.cloneDom("my_atted_template", user, viewData, "inline-block");
            $(".guanzhu-list").append(dom);
            dom.click(user.id, function(event){
                $.cookie('my', JSON.stringify({num:$("li.wodepaipin-active").index(), scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/userController/homePage?userId=' + event.data);
            });
        }

        function loadShow() {
            $(document.body).infinite();
            $(".home-content .weui-infinite-scroll").show();
        }
        function loadHide() {
            $(document.body).destroyInfinite();
            $(".home-content .weui-infinite-scroll").hide();
        }

    </script>
</body>

</html>
