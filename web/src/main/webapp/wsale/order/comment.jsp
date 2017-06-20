<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>订单评价</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0;">
                <div style="margin:0px 10px;">
                    <textarea style="margin-top:10px;" placeholder="评价内容，说点什么吧！" id="content"></textarea>
                    <div class="renzheng-input">
                        <a class="faxian-link">
                            <div class="list-right">
                                <div id="star"></div>
                            </div>
                            <div class="normal-text">评分评价</div>
                        </a>
                    </div>

                    <div>
                        <a class="bottom-btn" style="color:#fff;">发表评价</a>
                    </div>
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

    <script type="text/javascript">
        $(function(){
            $.fn.raty.defaults.path = base + 'wsale/images';
            $('#star').raty({
                score:3
            });

            $('.bottom-btn').click(function(){
                var grade = $('#star').raty('score'), content = $('#content').val();
                if(Util.checkEmpty(content)) {
                    $.toptip('请输入评价内容！');
                    return;
                }
                ajaxPost('api/apiOrder/addComment', {orderId:'${orderId}', productId:'${productId}', content:content, grade:grade}, function(data){
                    if(data.success) {
                        replace('api/apiOrder/orderDetail?id=${orderId}');
                    }
                }, function(){
                    $.loading.load({type:3, msg:'发表中...'});
                }, -1);
            });
        });
    </script>
</body>

</html>
