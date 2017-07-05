<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>拍品推荐</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.home.best.css?v=${staticVersion}" />
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="productList">

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
        <jsp:include page="../template/home_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var loading = true;
        var currPage = 1;
        var rows = 5;
        var scrollTop = 0;

        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawProductList();
                }, 20);
            });

            var obj = $.cookie('hotProduct');
            if(obj != null) {
                $.cookie('hotProduct', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                drawProductList(obj.currPage);
            } else {
                drawProductList();
            }
        });

        function drawProductList(page) {
            currPage = page || currPage;
            var params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows, seq:1};

            ajaxPost('api/apiProductController/hotList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var product = result.rows[i];
                            buildProduct(product, i);
                        }

                        loading = false;
                        currPage ++;
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
            });
        }

        function buildProduct(product, index) {
            var viewData = Util.cloneJson(product);
            viewData.readCount = product.readCount || 0;
            viewData.currentPrice = '￥' + product.currentPrice;
            var dom = Util.cloneDom("home_product_template", product, viewData);
            dom.find('.headImage').css('background-image', 'url('+product.user.headImage+')');
            if(product.user.self) dom.find('.attentionIt').hide();
            else {
                dom.find('.attentioned').attr('data-user-id', product.user.id);
                if(product.user.attred) {
                    dom.find('.attentioned').addClass('attred').html('已');
                }
            }
            if(product.files) {
                for(var i in product.files) {
                    var file = product.files[i];
                    dom.find('.pIconBox').append('<div class="swiper-slide pIconImg" style="background-image: url(\''+file.fileHandleUrl+'\');"></div>');
                }
            }
            $(".productList").append(dom);

            dom.find('.swiper-container').addClass('product-swiper-container' + index);
            dom.find('.swiper-pagination').addClass('product-swiper-p' + index);

            dom.find('.headImage, .nickname').click(product.user.id, function(event){
                $.cookie('hotProduct', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/apiShop/shop?userId=' + event.data);
            });

            dom.find('.pIconBox .pIconImg').click(product.id, function(event){
                $.cookie('hotProduct', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/apiProductController/productDetail?id=' + event.data);
            });

            dom.find('.attentionIt').click(product.user.id, function(event){
                var userId = event.data, url = 'api/userController/addShieldorfans', _this = $(this);
                if($(_this).find('.attentioned').hasClass('attred')) {
                    url = 'api/userController/delShieldorfans';
                    $.confirm("是否取消对该用户的关注?", "系统提示", function() {
                        attrFun(_this, url, userId);
                    }, function() {});
                } else {
                    attrFun(_this, url, userId);
                }
            });

            setTimeout(function(){
                var swiper = new Swiper ('.product-swiper-container' + index, {
                    pagination: '.product-swiper-p' + index,
                    paginationClickable: true
                });

                if(dom.find('.pIconBox').children().length <= 1) {
                    dom.find('.pIcon .swiper-pagination').remove();
                }
            }, 20);

            return dom;
        }

        function attrFun(elm, url, userId) {
            ajaxPost(url, {objectType:'FS', userId:userId}, function(data){
                if(data.success) {
                    var $a = $(elm).find('.attentioned');
                    if($a.hasClass('attred')) {
                        $('.attentioned[data-user-id='+userId+']').removeClass('attred').html('+&nbsp;');
                    } else {
                        $('.attentioned[data-user-id='+userId+']').addClass('attred').html('已');
                    }
                }
            });
        }
    </script>

</body>

</html>
