<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${user.self}">我的店铺信息</c:when>
            <c:otherwise>『${user.nickname}』的店铺信息</c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <style>
        .ui-header-fixed {
            position: absolute;
        }
    </style>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div data-role="header" data-position="fixed" data-theme="b" data-tap-toggle="false" style="height:43px;z-index: 100;">
            <!--<a href="../toolbar/" data-rel="back" class="ui-btn ui-btn-left ui-alt-icon ui-nodisc-icon ui-corner-all ui-btn-icon-notext ui-icon-carat-l">Back</a>-->
            <h2></h2>
            <div class="ppxq-userinfo">
                <div class="ppxq-content" style="width: inherit;">
                    <div class="others-name">${user.nickname}</div>
                    <div class="others-info">
                        <img class="others-level" src="${pageContext.request.contextPath}/wsale/images/v2.png" />
                        <span>信誉 ${order_status_count.OS10}</span>&nbsp;&nbsp;
                        <span>粉丝 ${user.fans}</span>
                        <c:if test="${!user.self}">
                            <c:choose>
                                <c:when test="${!user.attred}"><span class="guanzhu-flag">关注</span></c:when>
                                <c:otherwise><span class="guanzhu-flag attred">已关注</span></c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                </div>
                <img class="ppxq-title" src="${user.headImage}" />
            </div>
        </div><!-- /header -->
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="others-content">
                <div class="notice-content">
                    <div class="notice-info">
                        <img src="${pageContext.request.contextPath}/wsale/images/notice-icon.png" />
                    </div>
                    <div class="notice-text">
                        ${not empty user.bardian ? user.bardian : '这个人很懒，什么也没说'}
                    </div>
                </div>
                <div class="others-type">
                    <ul class="others-tab">
                        <li class="titletab-active">新品开拍</li>
                        <li>即将截拍</li>
                        <!--<li>参拍拍品</li>-->
                        <li>全部拍品</li>
                    </ul>
                </div>
                <div>
                    <div class="cbp-vm-switcher cbp-vm-view-grid others-xpkp" style="padding:0;">
                        <ul class="products">
                        </ul>
                    </div>
                </div>
            </div>
            <div class="home-content">
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    正在加载中
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
    <jsp:include page="../template/product_template.jsp"></jsp:include>

    <script type="text/javascript">
        var loading = true, currPage = 1, rows = 10, scrollTop = 0;

        $(function(){
            $(document.body).on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    var num = $("li.titletab-active").index();
                    if(num == 0) drawProduct(1);
                    else if(num == 1) drawProduct(2);
                    else if(num == 2) drawProduct(3);
                }, 20);
            });

            var obj = $.cookie('shop_page');
            if(obj != null) {
                $.cookie('shop_page', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                var num = obj.num;
                $("li.titletab-active").removeClass('titletab-active');
                $(".others-tab li").eq(num).addClass('titletab-active');
                drawProduct(num + 1, obj.currPage);
            } else {
                drawProduct();
            }

            $('.others-tab li').click(function() {
                var num = $(this).index();
                if(num == 2) {
                    $.cookie('shop_page', JSON.stringify({num:$("li.titletab-active").index(), scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                    href('api/apiShop/showAllProducts?userId=${user.id}');
                    return;
                }
                currPage = 1;
                $(document.body).destroyInfinite();
                $(".products").empty();
                $(".home-content .weui-infinite-scroll").show();
                drawProduct(num + 1)
            });

            $('.guanzhu-flag').click(function(){
                var url = 'api/userController/addShieldorfans', _this = this;
                if($(_this).hasClass('attred')) {
                    url = 'api/userController/delShieldorfans';
                    $.confirm("是否取消对该用户的关注?", "系统提示", function() {
                        attrFun(_this, url);
                    }, function() {});
                } else {
                    attrFun(_this, url);
                }
            });

            $('img.ppxq-title').click(function(){
                try{
                    JWEIXIN.previewImage([$(this).attr('src')]);
                } catch (e){console.log(e)}
            });
        });

        function attrFun(elm, url) {
            ajaxPost(url, {objectType:'FS', userId:'${user.id}'}, function(data){
                if(data.success) {
                    if($(elm).hasClass('attred')) {
                        $(elm).removeClass('attred').html('关注');
                    } else {
                        $(elm).addClass('attred').html('已关注');
                    }
                }
            });
        }

        // type:1-新品开拍；2-即将截拍；3-参拍拍品
        function drawProduct(type, page) {
            type = type || 1, currPage = page || currPage;
            var url, params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows, addUserId:'${user.id}'};
            if(type == 1 || type == 2) {
                url = 'api/apiProductController/productList';
                params.qtype = type;
            } else {
                url = 'api/apiAuctionController/auctionProductList';
                params.userId = '${user.id}';
            }
            ajaxPost(url, params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var product = type == 3 ? result.rows[i].zcProduct : result.rows[i];
                            buildProduct(product, type);
                        }
                        $(".lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".products").append(Util.noDate(2));
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
//            viewData.icon = base + product.icon;
            viewData.currentPrice = '￥' + product.currentPrice;
            var dom = Util.cloneDom("product_template", product, viewData);
            dom.find('.cbp-vm-right span:eq(1)').remove();
            $(".products").append(dom);
            // dom绑定事件
            dom.find('.cbp-vm-image').click(product.id, function(event){
                $.cookie('shop_page', JSON.stringify({num:$("li.titletab-active").index(), scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/apiProductController/productDetail?id=' + event.data);
            });
            if(type == 2) {
                dom.find('.others-time').show();
                addTimer(dom.find('.others-time'), product.deadlineLen);
            }
            return dom;
        }

        function loadShow() {
            $(document.body).infinite();
            $(".home-content .weui-infinite-scroll").show();
        }
        function loadHide() {
            $(document.body).destroyInfinite();
            $(".home-content .weui-infinite-scroll").hide();
        }

        var addTimer = (function () {
            var list = [], interval;

            return function (dom, time) {
                if (!interval)
                    interval = setInterval(go, 1000);
                list.push({ ele: dom, time: time });
                go();
            }

            function go() {
                for (var i = 0; i < list.length; i++) {
                    var dom = list[i].ele, time = list[i].time;
                    dom.html(getTimerString(time ? list[i].time -= 1 : 0));
                    if (!time)
                        list.splice(i--, 1);
                }
            }

            function getTimerString(time) {
                var h = Math.floor(time / 3600),
                    m = Math.floor(((time % 86400) % 3600) / 60),
                    s = Math.floor(((time % 86400) % 3600) % 60);
                m = m < 10 ? '0' + m : m, s = s < 10 ? '0' + s : s;
                if (time > 0)
                    return '距截拍 <span class="others-timeno">'+h+'</span>:<span class="others-timeno">'+m+'</span>:<span class="others-timeno">'+s+'</span>';
                else return "已截拍";
            }
        }) ();

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                title:"『${user.nickname}』的集东集西店铺",
                desc:"推荐给您一个好店铺，快来看看一起把货掏回家吧！",
                link:removeQueDefault(location.href),
                imgUrl:'${user.headImage}'
            };
            JWEIXIN.onMenuShareAppMessage(shareData);
            JWEIXIN.onMenuShareTimeline(shareData);
            JWEIXIN.onMenuShareQQ(shareData);
            JWEIXIN.onMenuShareWeibo(shareData);
            JWEIXIN.onMenuShareQZone(shareData);
        });
    </script>
</body>

</html>
