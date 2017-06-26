<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>新品开拍</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="" class="jqm-demos" style="background-color:#f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content">
                <div class="history-info" style="margin-top: 10px;margin-bottom: 25px;">
                    <img src="${pageContext.request.contextPath}/wsale/images/historytime-icon.png" />&nbsp;
                    <span class="history-msg">努力加载中</span>
                </div>
                <div class="newProducts"></div>
            </div>
        </div><!-- /content -->

        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <li><a rel="external" href="javascript:href('api/apiFindController/find');" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->
        <jsp:include page="../template/product_template.jsp"></jsp:include>
    </div><!-- /page -->

    <script type="text/javascript">
        var scrollH = 0, currPage = 1, rows = 5;
        $(function(){
            drawNewProducts();

            $(".history-info").click(function(){
                if($('.history-msg').html() == '查看更多') {
                    $('.history-msg').html('努力加载中');
                    drawNewProducts();
                }
            });
        });

        function drawNewProducts() {
            ajaxPost('api/apiFindController/newProductList', {page: currPage, rows: rows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            buildNewProduct(result.rows[i]);
                        }

                        scroll(true);
                        currPage ++;
                        $(".lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });
                    }

                    if(result.rows.length < rows) {
                        $('.history-info').hide();
                    } else {
                        $('.history-msg').html('查看更多');
                    }
                } else {
                    $('.history-info').hide();
                }
            });
        }

        function buildNewProduct(newProduct) {
            var viewData = Util.cloneJson(newProduct);
            viewData.addtime = new Date(newProduct.addtime.replace(/-/g,"/")).format('yyyy年MM月dd日 HH:mm');
            viewData.newProduct = getNewProductHtml(newProduct);

            var dom = Util.cloneDom("new_product_template", newProduct, viewData);
            $(".newProducts").prepend(dom);

            dom.find('.xinpin-model a').click(function(){
                var num = $(this).index();
                if(num == 0) href('api/apiShop/shop?userId=' + $(this).attr('data-user-id'));
                else href('api/apiProductController/productDetail?id=' + $(this).attr('data-product-id'));
            });
            return dom;
        }

        function getNewProductHtml(newProduct) {
            var html = '';
            for(var i=0; i<newProduct.products.length; i++) {
                var product = newProduct.products[i];
                var viewData = Util.cloneJson(product);
                if(i == 0) {
                    viewData.nickname = "『"+newProduct.user.nickname+"』的新品开拍啦";
                    var dom = Util.cloneDom("new_product_first_template", product, viewData);
                    dom.attr('data-user-id', newProduct.userId);
                    html += $('<div>').append(dom).html();
                }
                dom = Util.cloneDom("new_product_other_template", product, viewData);
                dom.attr('data-product-id', product.id);
                html += $('<div>').append(dom).html();
            }
            return html;
        }

        function scroll(load) {
            setTimeout(function(){
                if(load)
                    $.mobile.silentScroll($(document).height() - scrollH);
                else
                    $.mobile.silentScroll($(document).height());
                setTimeout(function(){
                    scrollH = $(document).height();//$(window).scrollTop();
                },20);
            }, 200);
        }
    </script>
</body>

</html>
