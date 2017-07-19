<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的订单</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="我的订单" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="mask-layer-1" style=""></div>

            <div id="payPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 210px; overflow: hidden;">
                    <div class="toolbar">
                        <div class="toolbar-inner">
                            <a href="javascript:;" class="picker-button close-popup" style="color: #e64340;font-size: .85rem;">关闭</a>
                            <h1 class="title">确认收货地址</h1>
                        </div>
                    </div>
                    <div class="modal-content">
                        <a style="padding: 10px;border-bottom: 1px solid #f5f5f5;background-color: #fff;" class="receiptAddress">
                            <div class="money-more" style="margin-top: 5px;">
                                <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                            </div>
                            <div style="display: none;" class="addr-none">
                                <div class="dingdan-title" style="color:#f6383a;font-size:14px;">
                                    尚未设置收货地址，点击设置
                                </div>
                            </div>
                            <div style="display: none;" class="addr">
                                <div style="display: -webkit-box !important;display: box !important;position:relative;">
                                    <div class="dingdan-title" style="margin-right:10px;">收货人：</div>
                                    <div class="dingdan-title username">
                                    </div>
                                </div>
                                <div class="dingdan-title address" style="margin-top:10px;color:#aaa;font-size:14px;">
                                </div>
                            </div>
                        </a>
                        <div style="text-align: center;">
                            <a class="bottom-btn" style="color: #fff;font-size: 16px;" id="pay">支付</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="home-content">
                <div class="wddd-toptab">
                    <div class="wddd-search">
                        <img class="wddd-searchicon" src="${pageContext.request.contextPath}/wsale/images/search-icon.png">
                    </div>
                    <div>
                        <ul class="wddd-tab">
                            <li>全部</li>
                            <li>待付款</li>
                            <li>待收货</li>
                            <li>未发货</li>
                            <li>待评价</li>
                        </ul>
                    </div>
                </div>
                <div class="dingdan-topmargin" id="order-list">

                </div>
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
    <jsp:include page="../template/order_template.jsp"></jsp:include>

    <script type="text/javascript">
        var type = ${type};
        var loading = true, currPage = 1, rows = 10, nowTime = new Date().getTime(), hasAddress = true, _that, orderId;
        var addressParams = {
            userName : '',
            postalCode : '',
            provinceName : '',
            cityName : '',
            countyName : '',
            detailInfo : '',
            telNumber : ''
        };
        $(function(){
            $('.wddd-tab li:eq('+type+')').addClass('titletab-active');
            $(document.body).on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawOrders($('.wddd-tab li[class=titletab-active]').index());
                }, 20);
            });

            drawOrders();

            $('.wddd-tab li').click(function() {
                $('.mask-layer-1').show();
                var num = $(this).index();
                currPage = 1;
                $(document.body).destroyInfinite();
                $("#order-list").empty();
                $(".home-content .weui-infinite-scroll").show();
                drawOrders(num);
            });

            $('.receiptAddress').click(function(){
                JWEIXIN.openAddress(function(data){
                    if(data.userName == addressParams.userName && data.postalCode == addressParams.postalCode
                            && data.provinceName == addressParams.provinceName && data.cityName == addressParams.cityName
                            && data.countryName == addressParams.countyName && data.detailInfo == addressParams.detailInfo
                            && data.telNumber == addressParams.telNumber) {
                        return;
                    }

                    addressParams.userName = data.userName;
                    addressParams.postalCode = data.postalCode;
                    addressParams.provinceName = data.provinceName;
                    addressParams.cityName = data.cityName;
                    addressParams.countyName = data.countryName;
                    addressParams.detailInfo = data.detailInfo.replace(/[\r\n]/g, "");
                    addressParams.telNumber = data.telNumber;

                    if($('.receiptAddress .addr').is(':hidden')) {
                        $('.receiptAddress .addr').show();
                        $('.receiptAddress .addr-none').hide();
                    }
                    $('.username').html(data.userName + " " + data.telNumber);
                    $('.address').html(data.provinceName + data.cityName + data.countryName + data.detailInfo);

                    if(!hasAddress) {
                        addressParams.atype = 1; // 收货地址
                        ajaxPost('api/apiShop/editAddress', addressParams, function(result){
                        });
                    }

                    hasAddress = true;
                });
            });

        });

        function drawOrders(tab) {
            tab = tab == 0 ? tab : (tab || type);
            var params = {page:currPage, rows:rows};
            if(tab == 1) {
                params.payStatus = 'PS01'; // 待付款
                params.orderStatus = 'OS01'; // 待付款
            } else if(tab == 2) {
                params.sendStatus = 'SS03'; // 待收货/已发货
                params.orderStatus = 'OS05'; // 待收货/已发货
            } else if(tab == 3) {
                params.sendStatus = 'SS01'; // 待发货
                params.orderStatus = 'OS02'; // 待发货
            } else if(tab == 4) { // 待评价
                params.orderStatus = 'OS10';
                params.isCommented = false;
            }
            ajaxPost('api/apiOrder/orderList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var order = result.rows[i];
                            buildOrder(order);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $("#order-list").append(Util.noDate(1, '您还没有相关的订单'));
                    }
                    if(result.rows.length >= rows) {
                        loadShow()
                    } else {
                        loadHide();
                    }
                } else {
                    loadHide();
                }
            });
        }

        function buildOrder(order) {
            var viewData = Util.cloneJson(order);
            viewData.nickname = order.isBuyer ?  order.seller.nickname + '(卖家)' : order.buyer.nickname + '(买家)';
            var btnHtml = '', payDownTime = 0, otherDownTime = 0, otherDownMsg = '';
            if(order.payStatus == 'PS01') { // 待付款
                if(order.orderStatus == 'OS15') { // 交易失败
                    viewData.statusName = order.orderStatusZh;
                    viewData.orderStatusName = '失败原因：' + order.orderCloseReasonZh;
                    viewData.orderStatusTime = '失败时间：' + new Date(order.orderStatusTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                } else if(order.orderStatus == 'OS10') {
                    viewData.statusName = order.orderStatusZh;
                    viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                    viewData.orderStatusTime = '交易类型：当面交易';
                    if(!order.isCommented && order.isBuyer)
                        btnHtml = '<span class="commentBtn">去评价</span>';
                } else {
                    viewData.statusName = order.orderStatusZh;
                    viewData.orderStatusName = '成交金额：￥' + order.totalPrice.toFixed(2);
                    if(order.isBuyer) {
                        if(!order.isIntermediary && !order.faceStatus)
                            btnHtml = '<span class="dmjyBtn">当面交易</span>';
                        else if(order.faceStatus && order.faceStatus == 'FS01')
                            viewData.statusName = '当面交易申请中';
                        btnHtml += '<span class="payBtn">支付</span>';
                    } else {
                        if(order.faceStatus && order.faceStatus == 'FS01') {
                            viewData.statusName = '当面交易确认';
                            btnHtml += '<span class="tydmBtn">同意</span><span class="jjdmBtn">拒绝</span>';
                        }
                    }

                    payDownTime = (new Date(order.addtime.replace(/-/g,"/")).getTime() + 72*60*60*1000) - nowTime
                }
            } else {
                if(order.backStatus && order.orderStatus != 'OS10' && order.orderStatus != 'OS15') { // 退货申请中且订单未结束
                    if(order.backStatus == 'RS01') { // 申请中
                        viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                        viewData.orderStatusTime = '申请时间：' + new Date(order.returnApplyTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                        if(order.isBuyer) {
                            viewData.statusName = '申请退货';
                            btnHtml = '<span class="shBtn">确认收货</span>';
                        } else {
                            viewData.statusName = '确认退货申请';
                            btnHtml = '<span class="jjthBtn">拒绝退货</span><span class="tythBtn">同意退货</span>';

                            otherDownTime = (new Date(order.returnApplyTime.replace(/-/g,"/")).getTime() + 72*60*60*1000) - nowTime;
                            otherDownMsg = '确认截止';
                        }
                    } else if(order.backStatus == 'RS02') { // 拒接退货
                        viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                        viewData.orderStatusTime = '拒绝时间：' + new Date(order.returnConfirmTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                        if(order.isBuyer) {
                            if(order.isXiaoer) {
                                var xiaoer = order.xiaoer;
                                if(xiaoer.status == 'XS01') {
                                    viewData.statusName = '等待小二处理';
                                    viewData.orderStatusTime = '小二介入：' + new Date(xiaoer.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                                    btnHtml = '<span class="cxxrBtn">撤销小二</span><span class="shBtn">确认收货</span>';
                                } else {
                                    viewData.statusName = '拒绝退货';
                                    btnHtml = '<span class="thBtn">申请退货</span><span class="shBtn">确认收货</span>';
                                }
                            } else {
                                viewData.statusName = '拒绝退货';
                                btnHtml = '<span class="sqxrBtn">申请小二</span><span class="shBtn">确认收货</span>';
                                otherDownTime = (new Date(order.returnConfirmTime.replace(/-/g,"/")).getTime() + 3*24*60*60*1000) - nowTime;
                                otherDownMsg = '确认截止';
                            }
                        } else {
                            if(order.isXiaoer) {
                                var xiaoer = order.xiaoer;
                                if(xiaoer.status == 'XS01') {
                                    viewData.statusName = '等待小二处理';
                                    viewData.orderStatusTime = '小二介入：' + new Date(xiaoer.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                                } else {
                                    viewData.statusName = '拒绝退货';
                                }
                            } else {
                                viewData.statusName = '拒绝退货';
                            }
                        }
                    } else if(order.backStatus == 'RS03') { // 同意退货
                        viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                        viewData.orderStatusTime = '同意时间：' + new Date(order.returnConfirmTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                        if(order.isBuyer) {
                            viewData.statusName = '同意退货';
                            btnHtml = '<span class="ljthBtn">立即退货</span><span class="shBtn">确认收货</span>';
                        } else {
                            viewData.statusName = '等待买家退货';
                        }
                        otherDownTime = (new Date(order.returnConfirmTime.replace(/-/g,"/")).getTime() + 5*24*60*60*1000) - nowTime;
                        otherDownMsg = '发货截止';
                    } else if(order.backStatus == 'RS04') { // 退货已发货
                        viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                        viewData.orderStatusTime = '退货时间：' + new Date(order.returnDeliverTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                        if(order.isBuyer) {
                            if(order.isXiaoer) {
                                var xiaoer = order.xiaoer;
                                if(xiaoer.status == 'XS01') {
                                    viewData.statusName = '等待小二处理';
                                    viewData.orderStatusTime = '小二介入：' + new Date(xiaoer.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                                } else {
                                    viewData.statusName = '等待卖家确认';
                                }
                            } else {
                                viewData.statusName = '等待卖家确认';
                            }
                            btnHtml = '<span class="shBtn">确认收货</span>';
                        } else {
                            if(order.isXiaoer) {
                                var xiaoer = order.xiaoer;
                                if(xiaoer.status == 'XS01') {
                                    viewData.statusName = '等待小二处理';
                                    viewData.orderStatusTime = '小二介入：' + new Date(xiaoer.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                                    btnHtml = '<span class="cxxrBtn">撤销小二</span><span class="tkBtn">确认退款</span>';
                                } else {
                                    viewData.statusName = '买家退货已发';
                                    btnHtml = '<span class="tkBtn">确认退款</span>';
                                    otherDownTime = (new Date(order.returnDeliverTime.replace(/-/g,"/")).getTime() + 10*24*60*60*1000) - nowTime;
                                    otherDownMsg = '确认截止';
                                }
                            } else {
                                viewData.statusName = '买家退货已发';
                                btnHtml = '<span class="sqxrBtn">申请小二</span><span class="tkBtn">确认退款</span>';
                                otherDownTime = (new Date(order.returnDeliverTime.replace(/-/g,"/")).getTime() + 10*24*60*60*1000) - nowTime;
                                otherDownMsg = '确认截止';
                            }
                        }

                    } else {
                        viewData.statusName = '交易失败';
                        viewData.orderStatusName = '失败原因：买家退货';
                        viewData.orderStatusTime = '失败时间：' + new Date(order.orderStatusTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                    }
                } else {
                    if(order.orderStatus == 'OS10') {
                        viewData.statusName = order.orderStatusZh;
                        viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                        viewData.orderStatusTime = '收货时间：' + new Date(order.receiveTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                        if(!order.isCommented && order.isBuyer)
                            btnHtml = '<span class="commentBtn">去评价</span>';
                    } else if(order.orderStatus == 'OS15') {
                        viewData.statusName = order.orderStatusZh;
                        viewData.orderStatusName = '失败原因：' + order.orderCloseReasonZh;
                        viewData.orderStatusTime = '失败时间：' + new Date(order.orderStatusTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                    } else {
                        if(order.sendStatus == 'SS01') { // 卖家待发货
                            viewData.statusName = order.orderStatusZh;
                            viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                            viewData.orderStatusTime = '付款时间：' + new Date(order.paytime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                            if(order.isBuyer) {
                               // btnHtml = '<span class="thBtn">退货</span>';
                            } else {
                                btnHtml = '<span class="fhBtn">立即发货</span>';
                            }
                            otherDownTime = (new Date(order.paytime.replace(/-/g,"/")).getTime() + 5*24*60*60*1000) - nowTime;
                            otherDownMsg = '发货截止';
                        } else if(order.sendStatus == 'SS03') { // 卖家已发货
                            var deliverTime = new Date(order.deliverTime.replace(/-/g,"/"));
                            viewData.statusName = order.orderStatusZh;
                            viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                            viewData.orderStatusTime = '发货时间：' + deliverTime.format('MM月dd日 HH:mm');
                            // 发货三天后显示退货按钮
                            if(order.isBuyer) {
                                if(nowTime - deliverTime.getTime() >= 24*60*60*1000) {
                                    btnHtml = '<span class="thBtn">申请退货</span><span class="shBtn">确认收货</span>';
                                } else {
                                    btnHtml = '<span class="shBtn">确认收货</span>';
                                }
                            }
                            otherDownTime = (new Date(order.deliverTime.replace(/-/g,"/")).getTime() + 14*24*60*60*1000) - nowTime;
                            otherDownMsg = '确认收货剩余';
                        } else { // 买家已收货
                            viewData.statusName = order.orderStatusZh;
                            viewData.orderStatusName = '交易金额：￥' + order.totalPrice.toFixed(2);
                            viewData.orderStatusTime = '收货时间：' + new Date(order.receiveTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                            if(!order.isCommented && order.isBuyer)
                                btnHtml = '<span class="commentBtn">去评价</span>';
                        }
                    }
                }
            }
            var dom = Util.cloneDom("order_template", order, viewData);
            $("#order-list").append(dom);

            if(order.isIntermediary) dom.find('.intermediary').show();

            if(order.isBuyer) {
                if(order.seller.mobile && order.seller.mobile != 'undefined') {
                    dom.find('.tel').attr('href', 'tel:' + order.seller.mobile);
                }
            } else {
                if(order.buyer.mobile && order.buyer.mobile != 'undefined') {
                    dom.find('.tel').attr('href', 'tel:' + order.buyer.mobile);
                }
            }

            // 跳转用户主页
            dom.find('.nickname').click(order, function(event){
                var order = event.data, userId;
                if(order.isBuyer) userId = order.seller.id;
                else userId = order.buyer.id;
                href('api/userController/homePage?userId=' + userId);
            });
            // 跳转订单详情
            dom.find('.dingdan-content').click(order.id, function(event){
                href('api/apiOrder/orderDetail?id=' + event.data);
            });

            var $opearte = dom.find('.dingdan-opearte');
            if(btnHtml == '') $opearte.remove();
            else $opearte.html(btnHtml);

            if(payDownTime > 0) {
                addTimer(dom.find('[name=orderStatusTime]'), payDownTime/1000, '付款截止');
            }

            if(otherDownTime > 0) {
                dom.find('[name=other]').show();
                addTimer(dom.find('[name=other]'), otherDownTime/1000, otherDownMsg);
            }

            // 买家申请退货
            $opearte.find('.thBtn').click(order, thFun);
            // 卖家同意退货
            $opearte.find('.tythBtn').click(order, tythFun);
            // 卖家拒绝退货
            $opearte.find('.jjthBtn').click(order, jjthFun);
            // 买家立即退货
            $opearte.find('.ljthBtn').click(order, ljthFun);
            // 卖家确认退款
            $opearte.find('.tkBtn').click(order, tkFun);
            // 买家申请当面交易
            $opearte.find('.dmjyBtn').click(order.id, dmjyFun);
            // 卖家同意当面交易
            $opearte.find('.tydmBtn').click(order.id, tydmFun);
            // 卖家拒接当面交易
            $opearte.find('.jjdmBtn').click(order.id, jjdmFun);
            // 买家去评价
            $opearte.find('.commentBtn').click(order, commentFun);
            // 卖家发货
            $opearte.find('.fhBtn').click(order, fhFun);
            // 买家/卖家申请小二
            $opearte.find('.sqxrBtn').click(order, sqxrFun);
            // 买家/卖家撤销小二
            $opearte.find('.cxxrBtn').click(order, cxxrFun);
            // 买家确认收货
            $opearte.find('.shBtn').click(order, shFun);
            // 买家支付
            $opearte.find('.payBtn').click(order, payFun);
            return dom;
        }

        // 申请退货
        function thFun(event) {
            var order = event.data;
            href('api/apiOrder/toBackApply?orderId=' + order.id + '&productId=' + order.product.id);
            /*var order = event.data;
            $.confirm("是否申请退货?", "系统提示", function() {
                ajaxPost('api/apiOrder/backApply', {id:order.id}, function(data){
                    if(data.success) {
                        $.alert("退货申请成功，请等待卖家确认！", "系统提示！");
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                    $('.wddd-tab li[class=titletab-active]').click();
                });
            }, function() {});*/
        }
        // 同意退货
        function tythFun(event) {
            var order = event.data;
            href('api/apiOrder/toAgreeBack?orderId=' + order.id);
            /*$.confirm("同意后此次交易将会关闭，是否继续?", "系统提示", function() {
                ajaxPost('api/apiOrder/back', {id:order.id}, function(data){
                    if(data.success) {
                        $.alert("交易已关闭，钱款已退回买家！", "系统提示！");
                        $('.wddd-tab li[class=titletab-active]').click();
                    }
                });
            }, function() {});*/
        }
        // 拒绝退货
        function jjthFun(event) {
            var order = event.data;
            href('api/apiOrder/toRefuseBack?orderId=' + order.id);
        }
        // 立即退货
        function ljthFun(event) {
            var order = event.data;
            href('api/apiOrder/toBack?orderId=' + order.id);
        }
        // 确认退款
        function tkFun(event) {
            var order = event.data;
            href('api/apiOrder/toRefund?orderId=' + order.id);
        }
        // 当面交易事情
        function dmjyFun(event) {
            $.confirm("您确认要申请当面交易?", "系统提示", function() {
                ajaxPost('api/apiOrder/faceApply', {id:event.data}, function(data){
                    if(data.success) {
                        $.alert("申请成功，等待卖家确认！", "系统提示！");
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                    $('.wddd-tab li[class=titletab-active]').click();
                });
            }, function() {});
        }
        // 卖家同意当面交易
        function tydmFun(event) {
            $.confirm("确认后交易完成，平台不承担后续责任！", "系统提示", function() {
                ajaxPost('api/apiOrder/agreeFace', {id:event.data}, function(data){
                    if(data.success) {
                        $.alert("同意成功，订单已完成！", "系统提示！");
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                    $('.wddd-tab li[class=titletab-active]').click();
                });
            }, function() {});
        }
        // 卖家拒绝当面交易
        function jjdmFun(event) {
            $.confirm("您是否拒绝当面交易？", "系统提示", function() {
                ajaxPost('api/apiOrder/refuseFace', {id:event.data}, function(data){
                    if(data.success) {
                        $.alert("拒绝成功，等待买家付款！", "系统提示！");
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                    $('.wddd-tab li[class=titletab-active]').click();
                });
            }, function() {});
        }
        function commentFun(event) {
            var order = event.data;
            href('api/apiOrder/comment?orderId=' + order.id + '&productId=' + order.productId);
        }
        // 发货
        function fhFun(event) {
            var order = event.data;
            href('api/apiOrder/toDeliver?orderId=' + order.id);
        }
        // 申请小二
        function sqxrFun(event) {
            var order = event.data;
            href('api/apiOrder/xiaoer?orderId=' + order.id);
        }
        // 撤销小二
        function cxxrFun(event) {
            var order = event.data;
            $.confirm("您确认要撤销小二介入?", "系统提示", function() {
                ajaxPost('api/apiOrder/cancelXr', {id:order.xiaoer.id,orderId:order.id,idType:order.xiaoer.idType}, function(data){
                    if(data.success) {
                        $.alert("成功撤销小二介入！", "系统提示！");
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                    $('.wddd-tab li[class=titletab-active]').click();
                });
            }, function() {});
        }
        // 确认收货
        function shFun(event) {
            var order = event.data, _this = this;
            $.confirm("确认后此次交易完成，钱款将打给卖家，是否继续?", "系统提示", function() {
                ajaxPost('api/apiOrder/receive', {id:order.id}, function(data){
                    if(data.success) {
                        var index = $('.wddd-tab li[class=titletab-active]').index();
                        if(index == 0)
                            $('.wddd-tab li[class=titletab-active]').click();
                        else
                            $(_this).closest('.dingdan-list').remove();
                    }
                });
            }, function() {});
        }
        // 支付
        function payFun(event) {
            var order = event.data;
            ajaxPost('api/userController/getAddress', {atype:1}, function(data){
                if(data.success) {
                    if(data.obj) {
                        hasAddress = true;

                        var address = data.obj;
                        $('.addr').show();
                        $('.username').html(address.userName + " " + address.telNumber);
                        $('.address').html(address.provinceName + address.cityName + address.countyName + address.detailInfo);

                        addressParams.userName = address.userName;
                        addressParams.postalCode = address.postalCode;
                        addressParams.provinceName = address.provinceName;
                        addressParams.cityName = address.cityName;
                        addressParams.countyName = address.countyName;
                        addressParams.detailInfo = address.detailInfo.replace(/[\r\n]/g, "");
                        addressParams.telNumber = address.telNumber;

                    } else {
                        hasAddress = false;
                        $('.addr-none').show();
//                        $('#pay').unbind('click').bind('click', function(){
//                            $.toptip('请选择收货地址');
//                        });
                    }

                    $('#pay').unbind('click').bind('click', order, pay);
                    $("#payPopup").wePopup();
                } else {
                    $.toptip(data.msg);
                }
            });
        }

        function pay(event) {
            if(!hasAddress) {
                $.toptip('请选择收货地址');
                return ;
            }

            var order = event.data;
            addressParams.orderId = order.id;
            ajaxPost('api/apiOrder/addAddress', addressParams, function(data){
                if(data.success) {
                    href('api/pay/toPay?objectId='+order.id+'&objectType=PO05&totalFee=' + order.totalPrice + '&userId=' + order.product.sellerUserId + '&isIntermediary=' + order.isIntermediary);
                } else {
                    $.toptip(data.msg);
                }
            });
        }

        function loadShow() {
            $(document.body).infinite();
            $(".home-content .weui-infinite-scroll").show();
            $('.mask-layer-1').hide();
        }
        function loadHide() {
            $(document.body).destroyInfinite();
            $(".home-content .weui-infinite-scroll").hide();
            $('.mask-layer-1').hide();
        }

        var addTimer = (function () {
            var list = [], interval;

            return function (dom, time, msg) {
                if (!interval)
                    interval = setInterval(go, 1000);
                list.push({ ele: dom, time: time , msg:msg});
                go();
            }

            function go() {
                for (var i = 0; i < list.length; i++) {
                    var dom = list[i].ele, time = list[i].time, msg = list[i].msg;
                    dom.html(getTimerString(time ? list[i].time -= 1 : 0, msg));
                    if (!time)
                        list.splice(i--, 1);
                }
            }

            function getTimerString(time, msg) {
                var h = Math.floor(time / 3600),
                        m = Math.floor(((time % 86400) % 3600) / 60),
                        s = Math.floor(((time % 86400) % 3600) % 60);
                m = m < 10 ? '0' + m : m, s = s < 10 ? '0' + s : s;
                if (time > 0)
                    return msg + '：<span class="nopay-money">'+h+'</span>时<span class="nopay-money">'+m+'</span>分<span class="nopay-money">'+s+'</span>秒';
                else return "交易关闭";
            }
        }) ();

    </script>
</body>

</html>
