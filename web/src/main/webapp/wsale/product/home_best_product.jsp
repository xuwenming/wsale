<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>精选</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="精选" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div>
                <div style="border-bottom:10px solid #f5f5f5;">
                    <a class="right-camera">
                        <img src="${pageContext.request.contextPath}/wsale/images/xiangji-icon.png" style="width:20px;" />
                    </a>
                    <div>
                        <ul class="tab-title">
                            <li class="titletab-active" style="width:20%;border:none; font-size: 15px;">
                                精选
                                <!--<span class="jingxuan-number">12</span>-->
                            </li>
                        </ul>
                    </div>
                </div>
                <div>
                    <div style="margin:10px 0px;" class="bestProducts">

                    </div>
                </div>
            </div>

            <div class="home-content">
                <div class="weui-infinite-scroll" style="display: none;">
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
                    <li><a rel="external" href="javascript:void(0);" data-prefetch="true" data-transition="turn" data-icon="camera" class="publishP">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->
    </div><!-- /page -->
    <jsp:include page="../template/product_template.jsp"></jsp:include>

    <script type="text/javascript">
        var loading = true;
        var currPage = 1;
        var rows = 10;
        var scrollTop = 0;
        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawProduct();
                }, 20);
            });

            var obj = $.cookie('home_best_product');
            if(obj != null) {
                $.cookie('home_best_product', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                drawProduct(true, obj.currPage);
            } else {
                drawProduct(true);
            }

            $(".right-camera, .publishP").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:3,msg:'正在上传...'});
                    var serverIds = [];
                    // TODO 弹出loading
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        $('.U-msg .moreMsg').html(index + "/" + localIds.length);
                        serverIds.push(serverId);
                        if(index == localIds.length) {
                            // TODO 关闭loading
                            setTimeout(function(){
                                $.loading.close();
                                href('api/apiProductController/toFirst?serverIds=' + serverIds + '&localIds=' + localIds);
                            }, 200);
                        }
                    });
                });
                //href('api/apiProductController/toFirst?serverIds=&localIds=');
            });
        });

        function drawProduct(load, page) {
            currPage = page || currPage;
            var params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows, channel:'HOME'};
            ajaxPost('api/apiBestProductController/bestProductList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows != 0) {
                        for(var i in result.rows) {
                            var product = result.rows[i].zcProduct;
                            buildProduct(product);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if($(".bestProducts a").length == 0)
                            $(".bestProducts").append(Util.noDate(2, "暂无精选"));
                    }

                    if(result.rows.length >= rows) {
                        $(".home-content .weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".home-content .weui-infinite-scroll").hide();
                    }
                } else {
                    $(document.body).destroyInfinite();
                    $(".home-content .weui-infinite-scroll").hide();
                }

                if(scrollTop > 0) {
                    $.mobile.silentScroll(scrollTop);
                    scrollTop = 0;
                }
            }, function(){
                if(load) $.loading.load({type:1});
            });
        }

        function buildProduct(product) {
            var viewData = Util.cloneJson(product);
//            viewData.icon = base + product.icon;
            var likeIcon = product.liked ? '${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png' : '${pageContext.request.contextPath}/wsale/images/guanzhu-icon.png';
            var likeClass = product.liked ? 'liked' : '';
            viewData.likeCount = '<img src="'+likeIcon+'" style="width:15px;" class="'+likeClass+'"/> <count>' + product.likeCount + '</count>';
            viewData.currentPrice = '￥' + product.currentPrice;
            var dom = Util.cloneDom("best_product_template", product, viewData, 'inline-block');
            $(".bestProducts").append(dom);
            // dom绑定点赞事件
            dom.find("span[name=likeCount]").click(product, function(event){
                var product = event.data, url, _this = this, count = parseInt($(this).find('count').text() || 0), clazz = '';
                if($(this).find('img').hasClass('liked')) {
                    url = 'api/apiProductController/cancelLike';
                    count -= 1;
                } else {
                    url = 'api/apiProductController/addLike';
                    count += 1;
                    clazz = 'liked'
                }
                ajaxPost(url, {productId:product.id}, function(data){
                    if(data.success) {
                        var likeIcon = $(_this).find('img').hasClass('liked') ? '${pageContext.request.contextPath}/wsale/images/guanzhu-icon.png' : '${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png';
                        $(_this).html('<img src="'+likeIcon+'" style="width:15px;" class="'+clazz+'"/> <count>' + count + '</count>');
                    }
                });
            });
            // dom绑定事件
            dom.find('.jingxuan-img').click(product.id, function(event){
                $.cookie('home_best_product', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/apiProductController/productDetail?id=' + event.data);
            });

            dom.find(".lazy").lazyload({
                placeholder : base + 'wsale/images/lazyload.png'
            });
        }
    </script>
</body>

</html>
