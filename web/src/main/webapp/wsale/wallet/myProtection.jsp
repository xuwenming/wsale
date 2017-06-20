<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>我的消保金</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" class="jqm-demos" style="background-color:#f5f5f5;">

        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="home-content" style="margin:0; ">
                <div>
                    <div class="xiaobaojin-list">
                        <div class="jiaona-btn" onclick="href('api/apiWallet/payProtection');">
                            <span>缴纳</span>
                        </div>
                        <div class="normal-text">
                            <div>消保金：<span class="yue-sum"><fmt:formatNumber type="number" value="${protection}" pattern="0.00" maxFractionDigits="2"/>元</span></div>
                        </div>
                    </div>

                    <div class="xiaobaojin-list">
                        <div class="jiaona-btn">
                            <img src="${pageContext.request.contextPath}/wsale/images/jilu-icon.png" />
                        </div>
                        <div class="normal-text">
                            <div>记录</div>
                        </div>
                    </div>
                    <div class="fensi-list">
                    </div>
                    <div class="weui-infinite-scroll">
                        <div class="infinite-preloader"></div>
                        正在加载中
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
    <jsp:include page="../template/wallet_template.jsp"></jsp:include>

    <script type="text/javascript">
        var loading = true, currPage = 1, rows = 10;
        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawProtections();
                }, 20);
            });

            drawProtections();
        });

        function drawProtections() {
            ajaxPost('api/apiWallet/protections', {page:currPage, rows:rows}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var protection = result.rows[i];
                            buildProtection(protection);
                        }

                        loading = false;
                        currPage ++;
                    } else {
                        if(result.total == 0)
                            $(".fensi-list").append(Util.noDate(1, '没有记录'));
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
            });
        }

        function buildProtection(protection) {
            var viewData = Util.cloneJson(protection);
            if(protection.isIncome) {
                viewData.price = '<span class="in-money">+'+protection.price.toFixed(2)+'</span>';
            } else {
                viewData.price = '<span class="out-money">-'+protection.price.toFixed(2)+'</span>';
            }
            viewData.addtime = new Date(protection.addtime.replace(/-/g,"/")).format('yyyy-MM-dd HH:mm');
            var dom = Util.cloneDom("protection_template", protection, viewData);
            $(".fensi-list").append(dom);
        }
    </script>
</body>

</html>
