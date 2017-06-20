<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jb.listener.Application"%>
<%
    String totalFee_h = Application.getString("AF04");
    totalFee_h = totalFee_h == null ? "300" : totalFee_h;
    String totalFee_c = Application.getString("AF05");
    totalFee_c = totalFee_c == null ? "300" : totalFee_c;
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>拍品管理</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">

        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="mask-layer-1" style=""></div>
            <div class="home-content" style="margin:0; ">
                <div>
                    <ul class="wode-paipintitle">
                        <li class="wodepaipin-active">竞拍中</li>
                        <li>已截拍</li>
                        <li>已流拍</li>
                        <li>已失败</li>
                        <li>草稿箱</li>
                    </ul>
                </div>
                <div class="productManage">
                </div>
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    正在加载中
                </div>
            </div>
            <jsp:include page="../template/product_template.jsp"></jsp:include>
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
        var loading = true, currPage = 1, rows = 10;
        $(function(){

            $(document.body).on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    var index = $('.wode-paipintitle li[class=wodepaipin-active]').index();
                    drawProduct(index + 1);
                }, 20);
            });

            if(${isDraft}) {
                $('.wode-paipintitle li').removeClass('wodepaipin-active');
                $('.wode-paipintitle li:eq(4)').addClass('wodepaipin-active');
                drawProduct(5);
            } else {
                drawProduct(1);
            }

            $('.wode-paipintitle li').click(function() {
                $('.mask-layer-1').show();
                currPage = 1;
                var num = $(this).index();
                $(document.body).destroyInfinite();
                $(".productManage").empty();
                $(".home-content .weui-infinite-scroll").show();
                drawProduct(num + 1);
            });

        });

        function drawProduct(type) {
            type = type || 1;
            var params = {page:currPage, rows:rows};
            if(type == 1) {
                params.status = 'PT03'
            } else if(type == 2) {
                params.status = 'PT04'
            } else if(type == 3) {
                params.status = 'PT05'
            } else if(type == 4) {
                params.status = 'PT06'
            } else if(type == 5) {
                params.status = 'PT01'
            }
            ajaxPost('api/apiProductMangerController/productList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var product = result.rows[i];
                            buildProduct(product, type);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".productManage").append(Util.noDate(1, '您还没有相关的拍品'));
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

        function buildProduct(product, type) {
            var viewData = Util.cloneJson(product);
//            viewData.icon = base + product.icon;
            var btnHtml = '',
                cbBtn = '<li class="jp-classify">分类精选</li>',
                hbBtn = '<li class="jp-home">首页精选</li>',
                sjBtn = '<li class="putaway">上架</li>',
                xjBtn = '<li class="sold-out">下架</li>',
                ycBtn = '<li class="p-close p-hide">隐藏</li>',
                xsBtn = '<li class="p-close p-show">显示</li>',
                delBtn = '<li class="del">删除</li>';
            if(type == 1) {
                viewData.time = '开拍时间：' + new Date(product.startingTime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                btnHtml = cbBtn + hbBtn + xjBtn;
            } else if(type == 2) {
                viewData.time = '截拍时间：' + new Date(product.realDeadline.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                if(product.isClose)
                    btnHtml = xsBtn;
                else
                    btnHtml = ycBtn;
            } else if(type == 3) {
                viewData.time = '流拍时间：' + new Date(product.realDeadline.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                btnHtml = xjBtn;
            } else if(type == 4) {
                viewData.time = '失败时间：' + new Date(product.updatetime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                if(product.isClose)
                    btnHtml = xsBtn;
                else
                    btnHtml = ycBtn;
            } else if(type == 5) {
                viewData.time = '创建时间：' + new Date(product.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
                btnHtml = sjBtn + delBtn;
            }
            var dom = Util.cloneDom("product_manage_template", product, viewData);
            $(".productManage").append(dom);

            var $opearte = dom.find('.dingdan-opearte');
            if(btnHtml == '') $opearte.remove();
            else $opearte.html(btnHtml);

            $opearte.find('.jp-classify').click(product, cbFun);
            $opearte.find('.jp-home').click(product, hbFun);
            $opearte.find('.putaway').click(product, sjFun);
            $opearte.find('.sold-out').click(product, xjFun);
            $opearte.find('.p-close').click(product, closeFun);
            $opearte.find('.del').click(product, delFun);

            dom.children('div').click(product, function(event){
                var product = event.data;
                if(product.status != 'PT01') {
                    href('api/apiProductController/productDetail?id=' + product.id);
                } else {
                    href('api/apiProductController/toFirst?productId=' + product.id);
                }
            });
            return dom;
        }

        // 申请分类精选
        function cbFun(event) {
            $.modal({
                title: "申请须知",
                text: "<font style='font-size: 8pt;'>一.您申请的是分类版面的精选藏品&nbsp;<br>二.条件：包退、描述相符、认证和&nbsp;<br>消保金用户&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;<br>三.时间：审核通过后的24小时&#12288;&#12288;<br>四.申请未通过，费用退回余额&#12288;&#12288;&nbsp;</font><br><br>是否继续支付？",
                buttons: [
                    { text: "取消", className: "default"},
                    { text: "去支付", onClick: function(){
                        var product = event.data;
                        applyBestProduct(product.id, 'CATEGORY');
                    } }
                ]
            });

        }

        // 申请首页精选
        function hbFun(event) {
            $.modal({
                title: "申请须知",
                text: "<font style='font-size: 8pt;'>一.您申请的是首页精选拍品&#12288;&#12288;&#12288;&nbsp;<br>二.条件：包退、描述相符、认证和&nbsp;<br>消保金用户&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;<br>三.时间：审核通过后的24小时&#12288;&#12288;<br>四.申请未通过，费用退回余额&#12288;&#12288;&nbsp;</font><br><br>是否继续支付？",
                buttons: [
                    { text: "取消", className: "default"},
                    { text: "去支付", onClick: function(){
                        var product = event.data;
                        applyBestProduct(product.id, 'HOME');
                    } }
                ]
            });
        }

        // 上架
        function sjFun(event) {
            var product = event.data;
            href('api/apiProductController/toFirst?productId=' + product.id);
        }

        // 下架
        function xjFun(event) {
            var product = event.data, _this = this;
            $.confirm("您确认下架此拍品吗？", "系统提示", function () {
                ajaxPost('api/apiProductController/productOff', {id:product.id, status:product.status}, function(data){
                    if(data.success) {
                        $(_this).closest('.p-list').remove();
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
            if($(_this).hasClass('p-hide')) {
                isClose = true;
            }
            var msg = isClose ? '确认隐藏此拍品？' : '确认显示此拍品？';
            $.confirm(msg, "系统提示", function() {
                ajaxPost('api/apiProductController/edit', {id:product.id, isClose:isClose}, function(data){
                    if(data.success) {
                        if(isClose)
                            $(_this).addClass('p-show').removeClass('p-hide').html('显示');
                        else
                            $(_this).addClass('p-hide').removeClass('p-show').html('隐藏');
                    }
                });
            });

        }

        // 删除
        function delFun(event) {
            var product = event.data, _this = this;
            $.confirm("确认删除草稿？", "系统提示", function () {
                ajaxPost('api/apiProductController/del', {id:product.id}, function (datas) {
                    if (datas.success) {
                        $(_this).closest('.p-list').remove();
                    }
                });
            });
        }

        function applyBestProduct(productId, channel) {
            ajaxPost('api/apiBestProductController/applyBestProduct',{productId: productId, channel: channel}, function(data){
                if(data.success) {
                    var objectType, totalFee;
                    if(channel == 'CATEGORY') {
                        objectType = 'PO09';
                        totalFee = '<%=totalFee_c %>'
                    } else {
                        objectType = 'PO03';
                        totalFee = '<%=totalFee_h %>';
                    }
                    href('api/pay/toPay?objectId='+data.obj+'&objectType='+objectType+'&attachType='+productId+'&totalFee=' + totalFee);
                }
            });
        }

    </script>
</body>

</html>
