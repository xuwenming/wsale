<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="jb.listener.Application"%>
<%
    String amSwitch = Application.getString("SV400");
    amSwitch = amSwitch == null ? "1" : amSwitch;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${user.self}">我的全部拍品</c:when>
            <c:otherwise>『${user.nickname}』的全部拍品</c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.product.all.list.css?v=${staticVersion}"/>
    <style>
        .ui-header-fixed {
            position: absolute;
        }
        .renzheng-input .ui-input-text input {
            font-size: 14px;
        }
    </style>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_auto_bid')}">
        <script type="text/javascript">
            $.authAutoBid = true; // 自动出价
        </script>
    </c:if>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div data-role="header" data-position="fixed" data-theme="b" data-tap-toggle="false" style="height:43px;z-index: 99;">
            <!--<a href="../toolbar/" data-rel="back" class="ui-btn ui-btn-left ui-alt-icon ui-nodisc-icon ui-corner-all ui-btn-icon-notext ui-icon-carat-l">Back</a>-->
            <h2></h2>
            <div class="ppxq-userinfo">
                <div class="ppxq-content">
                    <div style=" font-size:18px; font-weight:normal;">${user.nickname}</div>
                </div>
                <img class="ppxq-title" src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'" onclick="href('api/userController/homePage?userId=${user.id}');"/>
            </div>
        </div><!-- /header -->
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <input type="hidden" id="bindMobile" value="${sessionInfo.mobile}" />
            <div class="mask-layer bbs-detail-layer" style="z-index: 1001;">
                <img src="${pageContext.request.contextPath}/wsale/images/subscribe/bid-icon.jpg" class="subscribe"/>
            </div>

            <div id="sharePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 145px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden;font-size: 14px; ">
                        <div>分享给小伙伴</div>
                        <div>
                            <ul class="share-list">
                                <li>
                                    <div class="share-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/code-icon.png" />
                                    </div>
                                    <div class="msg">二维码</div>
                                </li>
                                <li>
                                    <div class="share-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/link-icon.png" />
                                    </div>
                                    <div class="msg">发送链接</div>
                                </li>
                                <li>
                                    <div class="share-img">
                                        <img style="margin-top:23px" src="${pageContext.request.contextPath}/wsale/images/other-icon.png" />
                                    </div>
                                    <div class="msg">用微信分享</div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div id="maxPricePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 200px;overflow: hidden; text-align: center;">
                    <div class="toolbar">
                        <div class="toolbar-inner">
                            <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                            <h1 class="title">请输入自动出价上限最高价</h1>
                        </div>
                    </div>

                    <div class="modal-content" style="overflow: hidden;">
                        <div class="renzheng-input" style="background-color: #fff;">
                            <a class="faxian-link" style="padding: 14px;">
                                <div class="list-right">
                                    <input type="tel" placeholder="请输入最高价" id="maxPrice" maxlength="10"/>
                                </div>
                                <div class="normal-text">最高价</div>
                            </a>
                        </div>
                        <a class="bottom-btn guanzhu-ok auto-bid" style="color: #fff;font-size: 16px;">自动出价</a>
                    </div>
                </div>
            </div>

            <div id="bindMobilePopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 240px; overflow: hidden;">
                    <div class="toolbar">
                        <div class="toolbar-inner">
                            <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                            <h1 class="title">绑定手机号</h1>
                        </div>
                    </div>
                    <div class="modal-content">
                        <input style="margin:10px 0;background-color: #fff;" type="tel" maxlength="11" placeholder="请输入您的手机号码..." id="mobile"/>
                        <input style="margin:10px 0;background-color: #fff;" type="tel" maxlength="6" placeholder="请输入验证码..."  id="vcode"/>
                        <div style="float:right;width:90px;text-align:center; margin: -45px 10px;font-size: 15px;border: 1px solid #f0f0f0;padding: 5px 10px" id="vcode-btn">
                            点击获取
                        </div>
                        <div style="text-align: center;">
                            <a class="bottom-btn" style="color: #fff;font-size: 16px;" id="bindMobileBtn">确认</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="all_list" style="margin-top: 37px;">

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
        <jsp:include page="../template/product_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var loading = true;
        var pCurrPage = 1;
        var rows = 5, pRows = 5;
        var self = ${user.self};
        var st = 0, time = 59, timeInterval;
        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawProductDetail();
                }, 20);
            });

            drawProductDetail();

            $('.share-list li').click(function(){
                var _this = this, num = $(this).index();
                var productId = $('.share-list').attr('data-product-id');
                if(num == 0) {
                    openQrcode(productId);
                } else if(num == 1) {
                    sendLink(productId, function(){
                        $(_this).find('.msg').html('发送成功');
                    });
                } else {
                    $(_this).find('.msg').html('可以分享了');
                    var $p = $('.qbpp-detail[productId='+productId+']');
                    var shareData = {
                        title:"『${sessionInfo.nickname}』发现了一件不错的集东集西拍品，快来围观！",
                        desc:$p.find('.ppxq-desc').html().replace(/\n/g,''),
                        link:server_url + base + 'api/apiProductController/productDetail?id=' + productId,
                        imgUrl:$p.find(".images img:eq(0)").attr("data-original")
                    };
                    share(shareData);
                }
            });

            $(".auto-bid").click(autoAuction);

            $(".subscribe").off('touchstart').on("touchstart", function (e) {
                e.stopPropagation();
                st = Date.now();
            });
            $(".subscribe").off('touchend').on("touchend", function (e) {
                e.stopPropagation();
                if(st > 0 && (Date.now() - st) < 300) {
                    $('.mask-layer').hide();
                    $(this).hide();
                }
            });
            $('#vcode-btn').bind('click', sendVCode);
            $('#bindMobileBtn').bind('click', bindMobile);
        });

        function bindMobile() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码');
                return;
            }
            var vcode = $('#vcode').val();
            if(Util.checkEmpty(vcode)) {
                $.toptip('请输入验证码');
                return;
            }
            ajaxPost('api/userController/edit', {mobile : mobile, vcode : vcode}, function(data){
                if(data.success) {
                    $.toptip('绑定成功', 'success');
                    $("#bindMobile").val(mobile);
                    $.closePopup();
                } else {
                    $.toptip(data.msg);
                }
            });
        }

        function sendVCode() {
            var mobile = $('#mobile').val();
            if(!Util.checkPhone(mobile)) {
                $.toptip('请输入正确的手机号码', 'error');
                $('#mobile').focus();
                return;
            }
            $('#vcode-btn').unbind('click').html('重发（<span id=\"time\">'+time+'</span>）');
            time--;
            timeInterval = setInterval(function(){
                $("#time").html(time);
                if(time == 0) {
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                } else {
                    time -- ;
                }
            }, 1000);

            ajaxPost('api/userController/sendVCode', {mobile:mobile, checkMobile:true}, function(data){
                if(data.success) {
                    $.toptip('验证码已发送至手机', 'success');
                } else {
                    $.toptip(data.msg, 'error');
                    clearInterval(timeInterval);
                    $("#vcode-btn").bind("click", sendVCode).html("点击获取");
                    time = 59;
                }
            });

        }

        function openQrcode(productId) {
            var content = server_url + base + 'api/apiProductController/productDetail?id=' + productId;
            ajaxPost('api/userController/getQR', {content:content, objectId:productId, objectType:'PRODUCT'}, function(data) {
                if(data.success) {
                    try{
                        JWEIXIN.previewImage([data.obj + "?t=" + new Date().getTime()]);
                    } catch (e){}

                }
            }, function(){
                $.loading.load({type:3, msg:'正在打开...'});
            });
        }

        function sendLink(productId, callback) {
            var link = server_url + base + 'api/apiProductController/productDetail?id=' + productId;
            ajaxPost('api/apiCommon/sendLink', {openid : '${sessionInfo.name}', link:link}, function(data) {
                if(data.success && callback) {
                    callback();
                }
            });
        }

        function addSubscribeLog(productId){
            ajaxPost('api/apiCommon/addSubscribeLog', {objectType : 'PRODUCT', objectId : productId}, function(data) {
                if(data.success) {
                }
            });
        }

        function drawProductDetail() {
            ajaxPost('api/apiProductController/productAllList', {addUserId:'${user.id}',page:pCurrPage, rows:pRows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var productDetail = result.rows[i];
                            buildProductDetail(productDetail);
                        }

                        loading = false;
                        pCurrPage ++;
                    } else {
                        if(result.total == 0)
                            $(".all_list").append(Util.noDate(2, '尚未发布拍品'));
                    }

                    if(result.rows.length >= pRows) {
                        $(".weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".weui-infinite-scroll").hide();
                    }
                } else {
                    $(document.body).destroyInfinite();
                    $(".weui-infinite-scroll").hide();
                }
            });
        }

        function buildProductDetail(productDetail) {
            var product = productDetail.product;
            var viewData = Util.cloneJson(productDetail);
            viewData.user = {
                headImage : '${user.headImage}',
                nickname : '${user.nickname}',
                positionIcon : base + 'wsale/images/p-${user.positionId}.png'
            };
            if(product.liked) {
                viewData.likeCount = '<img class="qbpp-icon liked" src="${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png" />\n';
            } else {
                viewData.likeCount = '<img class="qbpp-icon" src="${pageContext.request.contextPath}/wsale/images/guanzhu-icon.png" />\n';
            }
            viewData.likeCount += '<count>'+product.likeCount+'</count>';
            viewData.product.content = product.content.replace(/\n/g,'<br>');

            var dom = Util.cloneDom("product_detail_template", productDetail, viewData);
            dom.attr({productId:product.id, currentPrice:product.currentPrice});
            $('.all_list').append(dom);

            if(dom.find(".ppxq-desc").height() == dom.find(".ppxq-desc").css('max-height').replace('px', '')) {
                dom.find(".showMore").removeClass("hide");
            }

            drawImages(dom.find('.images'), product);

            if(${user.isPayBond}) dom.find('.protection').show();
            if(${user.isAuth}) dom.find('.auth').show();

            if(!product.isFreeShipping) dom.find('.baoyou').hide();

            if(product.approvalDays == 'AD03') dom.find('.baotui').html('3天包退');
            else if(product.approvalDays == 'AD07') dom.find('.baotui').html('7天包退');
            else dom.find('.baotui').hide();

            if(!self) {
                if(${!user.attred}) {
                    dom.find('.attBtn').show();
                }
                dom.find('.qbpp-xiajia').removeClass('xiajia-btn').html(new Date(product.startingTime.replace(/-/g,"/")).format('MM月dd日'));
            } else {
                if(product.status == 'PT03' || product.status == 'PT05')
                    dom.find('.qbpp-xiajia').addClass('p-off').html('下架');
                else if(product.status == 'PT04' || product.status == 'PT06') {
                    dom.find('.qbpp-xiajia').addClass('p-close').html('隐藏');
                    if(product.isClose) {
                        dom.find('.qbpp-xiajia').addClass('p-show').html('显示');
                    }
                }

            }

            drawLikeList(dom.find('.likeList'), productDetail.likes);

            if(product.deadlineLen == 0) {
                dom.find('.auction-opt').html('<div class="paipin-done">'+new Date(product.realDeadline.replace(/-/g,"/")).format('M月dd日 HH:mm')+'拍卖已结束</div>');
            } else {
                addTimer(dom, product.deadlineLen, product.id);
                if(!self) {
                    var price = product.currentPrice == 0 ? (product.startingPrice || productDetail.rangePrice) : (product.currentPrice + productDetail.rangePrice);
                    dom.find('.jiage-value').val(price);
                } else {
                    dom.find('.auction-opt .btn-con .btn-con-lf').html('<span class="share-icon jiage-operate" style="width: 100%;">分享给朋友们</span>');
                    dom.find('.auction-opt .btn-con .btn-con-rg').html('<span class="jiage-operate makeQr">生成二维码</span>');
                }
            }

            if(!productDetail.closeFlag) {
                drawAuction(dom.find('.auctions'), product, true);
            } else {
                dom.find('.auctions').parent().hide();
            }

            createEvent(dom, productDetail);
            return dom;
        }

        function createEvent(dom, productDetail) {
            dom.find('.attBtn').click(function(){
                ajaxPost('api/userController/addShieldorfans', {objectType:'FS', userId:'${user.id}'}, function(data){
                    if(data.success) {
                        $('.attBtn').remove();
                    }
                });
            });

            dom.find(".showMore").click(function(e){
                e.stopPropagation();
                e.preventDefault();
                var self = $(this);
                setTimeout(function() {
                    dom.find('.ppxq-desc').addClass("fullDesc");
                    self.remove();
                }, 200);
            });

            dom.find('.likeCount').click(productDetail.product.id, likeFun);
            dom.find('.moreLike').click(productDetail.product.id, moreLikeFun);
            dom.find('.auction_btn').click(productDetail.product, auction);
            if($.authAutoBid) {
                dom.find(".auto_auction_btn").click(productDetail.product, function(event){
                    if('${subscribe}' == 0) {
                        $('.mask-layer, .subscribe').show();
                        addSubscribeLog(event.data.id);
                        return;
                    }

                    if(self) {
                        $.alert("请分享给朋友，自己不能出价！", "系统提示");
                        return;
                    }

                    if(!$("#bindMobile").val()) {
                        $('#bindMobilePopup').wePopup();
                        return;
                    }

                    var $p = $(this).closest('.qbpp-detail');
                    $p.find('.updateBid span').click();

                    var marginFlag = event.data.marginFlag;
                    var margin = event.data.margin;
                    if(marginFlag) {
                        $.modal({
                            title: "系统提示",
                            text: "拍品已设置出价保护，您本次出价需支付保证金："+margin+"元！如违约，保证金扣除赔偿给卖家。",
                            buttons: [
                                { text: "取消", className: "default"},
                                { text: "去支付", onClick: function(){
                                    ajaxPost('api/apiProductController/addMargin', {productId: event.data.id, margin: margin}, function(data){
                                        if(data.success) {
                                            href('api/pay/toPay?objectId=' + data.obj + '&objectType=PO08&totalFee=' + margin);
                                        }
                                    });
                                } }
                            ]
                        });

                        return;
                    }
                    var bid = $p.find('.jiage-value').val();
                    $('#maxPricePopup').attr({'data-product-id':event.data.id, 'data-product-fixed' : event.data.fixedPrice, 'data-bid' : bid});
                    $('#maxPricePopup').wePopup();
                });
            } else {
                dom.find(".auto_auction_btn").parent().remove();
            }


            dom.find(".ppxq-desc").click(productDetail.product.id, function(event){
                href('api/apiProductController/productDetail?id=' + event.data);
            });

            if(self) {
                dom.find(".product-template-sixin").hide();
            } else {
                dom.find(".product-template-sixin").click(productDetail.product.id, function(event){
                    href('api/apiChat/chat?toUserId=${user.id}&subscribe=true&productId=' + event.data);
                });
            }

            dom.find(".check-more").click(productDetail.product, function(event){
                drawAuction(dom.find('.auctions'), event.data);
            });

            dom.find(".sub_btn").click(function(){
                var $p = $(this).closest('.qbpp-detail');
                var bid = parseFloat($p.find('.jiage-value').val()),
                    rangePrice = parseFloat($p.find('.rangePrice').html()),
                    currentPrice = parseFloat($p.attr('currentPrice') || 0),
                    fixedPrice = productDetail.product.fixedPrice || 0,
                    startingPrice = productDetail.product.startingPrice || 0;
                if(isNaN(bid)) {
                    $p.find('.jiage-value').val(currentPrice);
                    return;
                }
                var minPrice = currentPrice == 0 ? (startingPrice == 0 ? rangePrice : startingPrice) : (currentPrice + rangePrice);
                if(bid <= minPrice) {
                    if(fixedPrice == 0 || bid < fixedPrice) {
                        $.alert("出价不能再低了！", "系统提示");
                        return;
                    } else {
                        $p.find('.jiage-value').val(fixedPrice);
                        return;
                    }
                }

                $p.find('.jiage-value').val(bid - rangePrice);
            });

            dom.find(".add_btn").click(function(){
                var $p = $(this).closest('.qbpp-detail');
                var bid = parseFloat($p.find('.jiage-value').val()),
                    rangePrice = parseFloat($p.find('.rangePrice').html()),
                    fixedPrice = productDetail.product.fixedPrice;
                if(fixedPrice != 0 && bid + rangePrice > fixedPrice) {
                    $p.find('.jiage-value').val(fixedPrice);
                    return;
                }
                $p.find('.jiage-value').val(bid + rangePrice);
            });

            dom.find('.share-icon').click(productDetail.product.id, function(event){
                $('.share-list li:eq(1) .msg').text('发送链接');
                $('.share-list li:eq(2) .msg').text('用微信分享');
                $('.share-list').attr('data-product-id', event.data);
                //$(".mask-layer,.share-content").show();
                $('#sharePopup').wePopup();
            });

            dom.find('.p-off').click(productDetail.product, xjFun);
            dom.find('.p-close').click(productDetail.product, closeFun);
            dom.find('.updateBid span').bind('click', productDetail.product, updateBid);

            dom.find('.makeQr').click(productDetail.product.id, function(event){
                openQrcode(event.data);
            });
        }

        // 手动更新
        function updateBid(event) {
            var $p = $(this).closest('.qbpp-detail'),product = event.data;
            ajaxPostSync('api/apiProductController/updateBid', {id:product.id, currentPrice : $p.attr('currentPrice')}, function(data){
                var result = data.obj;

                if(result.product.deadlineLen > 0) {
                    addTimer($p, result.product.deadlineLen, product.id);
                } else {
                    $p.find('.auction-opt').html('<div class="paipin-done">'+new Date().format("M月dd日 HH:mm")+'拍卖已结束</div>');
                    clearInterval(timerArr['interval_' + product.id]);
                }

                $p.find('.updateBid .newbidTM').html(new Date().format('HH:mm:ss'));

                if(data.success) {
                    var currentPrice = result.product.currentPrice;
                    $p.attr('currentPrice', currentPrice);
                    $p.find('.rangePrice').html(result.rangePrice);
                    $p.find('.jiage-value').val(currentPrice == 0 ? (result.product.startingPrice == 0 ? result.rangePrice : result.product.startingPrice) : (currentPrice + result.rangePrice));

                    $p.find('.auctions').attr('page-currPage', 1);
                    drawAuction($p.find('.auctions'), product, true);
                }
            });
        }

        // 下架
        function xjFun(event) {
            var product = event.data, _this = this;
            $.confirm("您确认下架此拍品吗？", "系统提示", function () {
                ajaxPost('api/apiProductController/productOff', {id:product.id, status:product.status}, function(data){
                    if(data.success) {
                        $(_this).closest('.qbpp-detail').remove();
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                });
            });
        }

        // 影藏/显示
        function closeFun(event) {
            var product = event.data, _this = this, isClose = false;
            // 当前拍品为显示，修改为影藏
            if(!$(_this).hasClass('p-show')) {
                isClose = true;
            }
            var msg = isClose ? '确认隐藏此拍品？' : '确认显示此拍品？';
            $.confirm(msg, "系统提示", function() {
                ajaxPost('api/apiProductController/edit', {id:product.id, isClose:isClose}, function(data){
                    if(data.success) {
                        if(isClose)
                            $(_this).addClass('p-show').html('显示');
                        else
                            $(_this).removeClass('p-show').html('隐藏');
                    }
                });
            });

        }

        function likeFun(event) {
            var url, _this = this, count = parseInt($(this).find('count').text() || 0),
                    productId = event.data,
                    $likeList = $(this).closest('.qbpp-detail').find('.likeList');
            if($(this).find('img').hasClass('liked')) {
                url = 'api/apiProductController/cancelLike';
                count -= 1;
            } else {
                url = 'api/apiProductController/addLike';
                count += 1;
            }
            ajaxPost(url, {productId:productId}, function(data){
                if(data.success) {
                    $(_this).find('count').text(count);
                    if($(_this).find('img').hasClass('liked')) {
                        $(_this).find("img").removeClass('liked').attr('src', base + 'wsale/images/guanzhu-icon.png');
                        $likeList.find("img[id='${sessionInfo.id}']").remove();
                        if($likeList.find("img").length == 0) {
                            $likeList.html('<div style="margin:5px 0px;"></div>');
                        }
                    } else {
                        $(_this).find("img").addClass('liked').attr('src', base + 'wsale/images/yiguanzhu-icon.png');
                        var $div = $likeList.find('div');
                        if($likeList.find("img").length == 0) {
                            $div.css({'background-color':'#f0f0f0', 'padding': '4px'});
                        }
                        $div.prepend('<img class="touxiang-list" src="${sessionInfo.headImage}"'+' onerror="this.src='+'${pageContext.request.contextPath}/wsale/images/user-default.png"'+' id="${sessionInfo.id}" onclick="href(\'api/userController/homePage?userId=${sessionInfo.id}\');"/>\n');
                    }
                }
            });
        }

        function moreLikeFun(event) {
            var $likeList = $(this).closest('.qbpp-detail').find('.likeList');
            if($likeList.find('.moreLike').hasClass('down')) {
                if($likeList.find("img.more").length > 0) {
                    $likeList.find("img.more").show();
                } else {
                    ajaxPost("api/apiProductController/likes", {productId:event.data}, function(data){
                        if(data.success) {
                            $likeList.find("img:not(.moreLike)").remove();
                            var likes = data.obj;
                            for(var i=0; i<likes.length; i++) {
                                var like = likes[i], clazz = '';
                                if(i >= 12) clazz = 'more';
                                $likeList.find('.moreLike').before('<img class="touxiang-list '+clazz+'" src="'+like.user.headImage+' onerror="this.src='+'${pageContext.request.contextPath}/wsale/images/user-default.png"' +'" id="'+like.user.id+'" onclick="href(\'api/userController/homePage?userId='+like.user.id+'\');" />\n');
                            }
                        }
                    });
                }

                $likeList.find('.moreLike').removeClass('down').attr('src', base + 'wsale/images/up-icon.png');
            } else {
                $likeList.find("img.more").hide();
                $likeList.find('.moreLike').addClass('down').attr('src', base + 'wsale/images/down-icon.png');
            }

        }

        // 出价
        function auction(event) {
            var product = event.data;
            if('${subscribe}' == 0) {
                $('.mask-layer, .subscribe').show();
                addSubscribeLog(product.id);
                return;
            }

            if(self) {
                $.alert("请分享给朋友，自己不能出价！", "系统提示");
                return;
            }

            if(!$("#bindMobile").val()) {
                $('#bindMobilePopup').wePopup();
                return;
            }

            var $p = $(this).closest('.qbpp-detail');
            $p.find('.updateBid span').click();

            var bid = parseFloat($p.find('.jiage-value').val()),
                rangePrice = parseFloat($p.find('.rangePrice').html()),
                currentPrice = parseFloat($p.attr('currentPrice') || 0),
                fixedPrice = product.fixedPrice || 0,
                startingPrice =  product.startingPrice || 0;
            if(isNaN(bid)) {
                $p.find('.jiage-value').val(currentPrice);
                return;
            }
            var minPrice = currentPrice == 0 ? (startingPrice == 0 ? rangePrice : startingPrice) : (currentPrice + rangePrice);
            if(bid < minPrice) {
                if(fixedPrice == 0 || bid < fixedPrice) {
                    $.alert("加价幅度不能低于"+rangePrice+"元！", "系统提示", function(){
                        $p.find('.jiage-value').val(minPrice);
                    });
                    return;
                }
            }
            if(fixedPrice != 0 && bid > fixedPrice) {
                $.alert("出价不能超过一口价！", "系统提示", function(){
                    $p.find('.jiage-value').val(fixedPrice);
                });
                return;
            }

            if(product.marginFlag) {
                $.modal({
                    title: "系统提示",
                    text: "拍品已设置出价保护，您本次出价需支付保证金："+product.margin+"元！如违约，保证金扣除赔偿给卖家。",
                    buttons: [
                        { text: "取消", className: "default"},
                        { text: "去支付", onClick: function(){
                            ajaxPost('api/apiProductController/addMargin', {productId: product.id, margin: product.margin}, function(data){
                                if(data.success) {
                                    href('api/pay/toPay?objectId=' + data.obj + '&objectType=PO08&totalFee=' + product.margin);
                                }
                            });
                        } }
                    ]
                });

                return;
            }
            var $first = $p.find(".auctions li:eq(0)");
            if('${sessionInfo.id}' == $first.find('div.avatr').attr('userId')) {
                $.alert("您的出价已领先，请勿连续出价！", "系统提示");
                return;
            }
            $.confirm("您确认出价此拍品吗？", "系统提示", function () {
                var bid = parseFloat($p.find('.jiage-value').val());
                ajaxPost('api/apiProductController/bid', {productId:product.id, bid : bid}, function(data){
                    var result = data.obj;
                    if(data.success) {
                        $.toast("出价成功", "text");
                        /*if($first.length != 0) {
                            $first.find(".order-sign").hide();
                            $first.find(".order-flag").attr('src', base + 'wsale/images/chuju-icon.png');
                        } else {
                            $p.find('.auctions').parent().show();
                        }
                        var dom = buildAuction($p.find('.auctions'), result.auction);
                        $p.find('.auctions').prepend(dom);
                        dom.find(".order-sign").show();

                        if(result.isDeal) {
                            $p.find('.auction-opt').html('<div class="paipin-done">'+new Date().format("M月dd日 HH:mm")+'拍卖已结束</div>');
                            dom.find(".order-flag").attr('src', base + 'wsale/images/deal-icon.png');
                        } else {
                            $p.attr('currentPrice', bid);
                            $p.find('.rangePrice').html(result.rangePrice);
                            $p.find('.jiage-value').val(bid + result.rangePrice);
                            dom.find(".order-flag").attr('src', base + 'wsale/images/lingxian-icon.png');
                            if(result.deadlineLen > 0) {
                                addTimer($p, result.deadlineLen, product.id);
                            }
                        }*/

                        $p.find('.updateBid span').click();
                    } else {
                        $.alert(data.msg, "系统提示");
                    }
                });
            });
        }

        var reg = /^[0-9]*(\.[0-9]{1,2})?$/;
        // 自动出价
        function autoAuction() {
            $.closePopup();

            var $p = $(this).closest('#maxPricePopup'),
                productId = $p.attr('data-product-id');

            var maxPrice = $('#maxPrice').val();
            $('#maxPrice').val('');
            if(!reg.test(maxPrice)) {
                $.toast("<font size='2'>最高价格式不正确</font>", "text");
                return;
            }
            var bid = parseFloat($p.attr('data-bid'));
            if(bid >= maxPrice) {
                $.alert("最高价不能低于当前出价！", "系统提示");
                return;
            }
            var fixedPrice = parseFloat($p.attr('data-product-fixed'));
            if(fixedPrice > 0 && maxPrice > fixedPrice) {
                $.alert("最高价不能超出一口价！", "系统提示");
                return;
            }

            ajaxPost('api/apiProductController/autoBid', {productId:productId, maxPrice : maxPrice}, function(data){
                if(data.success) {
                    $.alert("自动出价成功，请手动更新！", "系统提示", function(){
                        $p.find('.updateBid span').click();
                    });
                } else {
                    $.alert(data.msg, "系统提示");
                }
            });
        }

        function drawImages(elm, product) {
            var files = product.files;
            if(!files || files.length == 0) return;
            var items = [], oneImg = "";
            if(files.length == 1) oneImg = "one-img";

            for(var i=0; i<files.length; i++) {
//                elm.append('<img class="lazy ppxq-imglist" data-original="'+files[i].fileHandleUrl+'" />\n');
                elm.append('<div class="content-img lazy '+oneImg+'" data-original="'+files[i].fileHandleUrl+'"></div>\n');
                items.push(files[i].fileHandleUrl);
            }

            elm.find('.content-img').lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            }).click(function(){
                if('${subscribe}' == 0) {
                    $('.mask-layer, .subscribe').show();
                    addSubscribeLog(product.id);
                    return;
                }
                JWEIXIN.previewImage(items, $(this).index());
            });
        }

        function drawLikeList(elm, likes) {
            if(!likes || likes.rows.length == 0) return;
            var $div = $('<div style="margin:5px 0px; background-color:#f0f0f0;padding:4px;"></div>');
            for(var i=0; i<likes.rows.length; i++) {
                var like = likes.rows[i];
                $div.append('<img class="lazy touxiang-list"'+' onerror="this.src='+'${pageContext.request.contextPath}/wsale/images/user-default.png"'+' data-original="'+like.user.headImage+'" id="'+like.user.id+'" onclick="href(\'api/userController/homePage?userId='+like.user.id+'\');"/>\n');
            }
            if(likes.total > likes.rows.length)
                $div.append('<img class="moreLike down" style="width: 29px; height: 29px; border: 1px solid #9A9DA5;" src="${pageContext.request.contextPath}/wsale/images/down-icon.png" />');
            elm.html($div);

            elm.find('.lazy').lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });
        }

        function drawAuction(elm, product, load) {
            if(load) $(elm).empty();
            var $d = elm.parent();
            var currPage = elm.attr('page-currPage') || 1;
            ajaxPostSync('api/apiProductController/auctions', {productId:product.id, page:currPage, rows:rows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    var flag = product.status == 'PT03';
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var auction = result.rows[i];
                            buildAuction(elm, auction, flag);
                        }

                        elm.find("li:eq(0)").find(".order-sign").show();
                        if(product.status != 'PT03' || product.deadlineLen == 0) {
                            elm.find("li:eq(0)").find(".order-flag").attr('src', base + 'wsale/images/deal-icon.png');
                        } else {
                            elm.find("li:eq(0)").find(".order-flag").attr('src', base + 'wsale/images/lingxian-icon.png');
                        }

                        currPage ++;
                        elm.attr('page-currPage', currPage);
                    }

                    if(result.rows.length < rows) $d.find('.check-more').hide();
                    else $d.find('.check-more').show();

                    if(result.total > 0) $d.show();
                    else $d.hide();
                }
            });
        }

        function buildAuction(elm, auction, flag) {
            var isClick = true;
            if('<%=amSwitch %>' == 1 && auction.user.id != '${sessionInfo.id}' && !self) {
                auction.user.nickname = '匿名';
                auction.user.headImage = base + 'wsale/images/touxiang-img.png';
                isClick = false;
            }
            var viewData = Util.cloneJson(auction);
            viewData.addtime = auction.addtime.substring(2);
            viewData.bid = '￥' + auction.bid;
            var dom = Util.cloneDom("auction_template", auction, viewData);
            dom.find('div.avatr').attr('userId', auction.user.id).css('background-image', 'url('+auction.user.headImage+')');
            if(auction.isAuto) dom.find('.order-right-middle').show();
            elm.append(dom);
            if(isClick)
                dom.find("div.avatr").click(auction.user.id, function(event){
                    href('api/userController/homePage?userId=' + event.data);
                });
            return dom;
        }

        var timerArr = {};
        var addTimer = (function () {

            return function (dom, time, key) {
                timerArr['interval_time_' + key] = time + 1;
                if (!timerArr['interval_' + key]) {
                    timerArr['interval_' + key] = setInterval(function(){
                        go(dom, key);
                    }, 1000);

                    go(dom, key);
                }
            };

            function go(dom, key) {
                var time = timerArr['interval_time_' + key];
                var timerStr = getTimerString(time ? timerArr['interval_time_' + key] -= 1 : 0);
                if(timerStr == -1) {
                    dom.find('.updateBid span').click();
                }
                else dom.find('.deadline').html(timerStr);
            }

            function getTimerString(time) {
                var d = Math.floor(time / 86400),
                        h = Math.floor((time % 86400) / 3600),
                        m = Math.floor(((time % 86400) % 3600) / 60),
                        s = Math.floor(((time % 86400) % 3600) % 60);
                if (time > 0) {
                    var dh = d == 0 ? '' : '<span class="cbp-vm-timenumber">'+d+'</span>天';
                    return '<font style="color: #a8a8a8;">拍卖倒计时：</font>'+dh+'<span class="cbp-vm-timenumber">'+h+'</span>时<span class="cbp-vm-timenumber">'+m+'</span>分<span class="cbp-vm-timenumber">'+s+'</span>秒'
                }
                else return -1;
            }
        }) ();

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                title:"『${user.nickname}』的集东集西店铺",
                desc:"推荐给您一个好店铺，快来看看一起把货掏回家吧！",
                link:server_url + base + 'api/apiShop/shop?userId=${user.id}',
                imgUrl:'${user.headImage}'
            };
            share(shareData);
        });

        function share(shareData) {
            JWEIXIN.onMenuShareAppMessage(shareData);
            JWEIXIN.onMenuShareTimeline(shareData);
            JWEIXIN.onMenuShareQQ(shareData);
            JWEIXIN.onMenuShareWeibo(shareData);
            JWEIXIN.onMenuShareQZone(shareData);
        }
    </script>
</body>

</html>
