<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <jsp:include page="inc.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/css/ui.home.best.css" />
</head>
<body>
    <div data-role="page" data-title="首页" class="jqm-demos" style="background-color:#f5f5f5;">
        <div data-role="header" data-position="fixed" data-tap-toggle="false" data-theme="b" style="z-index: 100;">
            <div class="home-title"><img src="${pageContext.request.contextPath}/wsale/images/home-logo.png" /></div>

        </div><!-- /header -->
        <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="swiper-container bannerSwiper">
                <div class="swiper-wrapper">
                    <c:forEach items="${banners}" var="banner" varStatus="vs">
                        <div class="swiper-slide">
                            <a <c:if test="${!empty banner.detailUrl}">href="${banner.detailUrl}"</c:if> rel="external" target="_blank">
                                <img src="${banner.url}" style="width:100%;" />
                                <div class="swiper-title">
                                    <span class="swiper-text" style="margin-right:5px;float:right;">${banner.title}</span>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="home-content" style="background-color: #FFF;">
                <div>
                    <!--<a style="width:29%;display:inline-block; background-color:#fff;padding:15px 0px;" href="javascript:href('api/apiCategoryController/category');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon1.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">论坛分类</div>
                    </a>
                    <a style="width:29%; display:inline-block;background-color:#fff;padding:15px 0px; margin:0 2%;" href="javascript:href('api/apiBestProductController/homeBest');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon2.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">精选拍品</div>
                    </a>-->
                    <a style="width:23%; display:inline-block;background-color:#fff;padding:15px 0px;border-right: 1px solid #eee;" href="javascript:href('api/apiHomeController/myAttedProduct');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon3.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">我的关注</div>
                    </a>
                    <a style="width:23%; display:inline-block;background-color:#fff;padding:15px 0px;" href="javascript:href('api/bbsController/hotBbs');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon6.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">热门主题</div>
                    </a>
                    <a style="width:23%; display:inline-block;background-color:#fff;padding:15px 0px;border-left: 1px solid #eee;" href="javascript:href('api/bbsController/hotBbs?bbsType=BT03');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon5.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">热门讲堂</div>
                    </a>
                    <a style="width:23%; display:inline-block;background-color:#fff;padding:15px 0px;border-left: 1px solid #eee;" href="javascript:href('api/apiTopic/topic');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon4.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">专题文章</div>
                    </a>
                </div>
                <!--<div style="margin-top:10px;">
                    <a style="width:29%;display:inline-block; background-color:#fff;padding:15px 0px;">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon4.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">展会动态</div>
                    </a>
                    <a style="width:29%; display:inline-block;background-color:#fff;padding:15px 0px; margin:0 2%;" href="javascript:href('api/bbsController/hotBbs?bbsType=BT03');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon5.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">热门讲堂</div>
                    </a>
                    <a style="width:29%; display:inline-block;background-color:#fff;padding:15px 0px;" href="javascript:href('api/bbsController/hotBbs');">
                        <img alt="" src="${pageContext.request.contextPath}/wsale/images/home-icon6.png" style="width:30%;margin-bottom:10px;" />
                        <div style="font-size:14px;color:#808080;">热门主题</div>
                    </a>
                </div>-->
            </div>

            <div style="width: 100%;background-color: #FFF;">
                <div class="faxian-link homeBbsTitle" style="display: none;">
                    <!--<div class="list-right more">
                        <span class="grayright-text">更多</span>
                        <img class="arrow-right" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png">
                    </div>-->
                    <span style="margin-left: 5px;font-size: 15px;">热门推荐</span>
                </div>

                <div class="homeBbsList" style="padding: 0 10px;">
                </div>

                <div class="faxian-link homeTopicTitle" style="border-top: 10px solid rgb(245, 245, 245); display: none;">
                    <span style="margin-left: 5px;font-size: 15px;">专题推荐</span>
                </div>
                <div class="homeTopicList" style="padding: 0 10px;">
                </div>

                <div class="faxian-link bestTitle" style="border-top: 10px solid rgb(245, 245, 245); display: none;">
                    <span style="margin-left: 5px;font-size: 15px;">精选推荐</span>
                </div>
                <div class="bestList">

                </div>
            </div>
            <div class="home-content">
                <div class="weui-infinite-scroll">
                    <div class="infinite-preloader"></div>
                    努力加载中
                </div>
            </div>

        </div><!-- /content -->


        <div id="bottombar" data-role="footer" data-position="fixed" data-theme="a" data-tap-toggle="false" style="position: fixed;">
            <div data-role="navbar">
                <ul>
                    <li><a rel="external" href="javascript:href('api/apiHomeController/home');" data-prefetch="true" data-transition="turn" data-icon="home" class="ui-icon-myicon">首页</a></li>
                    <li><a rel="external" href="javascript:href('api/apiCategoryController/category');" data-prefetch="true" data-transition="turn" data-icon="bullets">论坛</a></li>
                    <!--<li><a rel="external" href="javascript:href('api/apiFindController/find');" data-prefetch="true" data-transition="turn" data-icon="eye">发现</a></li>-->
                    <li><a rel="external" href="javascript:href('api/apiProductController/toFirst');" data-prefetch="true" data-transition="turn" data-icon="camera">拍</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->

    </div><!-- /page -->
    <jsp:include page="template/home_template.jsp"></jsp:include>
    <jsp:include page="template/bbs_template.jsp"></jsp:include>
    <jsp:include page="template/topic_template.jsp"></jsp:include>

    <script type="text/javascript">
        document.onreadystatechange =function(){
            if(document.readyState == 'interactive') {
                $.loading.load();
            }
            if(document.readyState == 'complete') {
                $.loading.close();
            }
        };

        var loading = true, currPage = 1, rows = 5, scrollTop = 0;
        $(function(){
            $(".toPublishHot").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:3,msg:'正在上传...'});
                    var serverIds = [];
                    // 弹出loading
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        $('.U-msg .moreMsg').html(index + "/" + localIds.length);
                        serverIds.push(serverId);
                        if(index == localIds.length) {
                            // 关闭loading
                            setTimeout(function(){
                                $.loading.close();
                                href('api/bbsController/toPublishHot?serverIds=' + serverIds + '&localIds=' + localIds);
                            }, 200);
                        }
                    });
                });
            });

            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    drawBest();
                }, 20);
            });

            drawBbsList();
            drawTopicList();

            var obj = $.cookie('home_best');
            if(obj != null) {
                $.cookie('home_best', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                drawBest(obj.currPage);
            } else {
                drawBest();
            }

        });

        function drawBbsList() {
            ajaxPost('api/bbsController/bbsList', {page:1, rows:5, isHomeHot:true,bbsStatus:'BS01', sort:'seq desc, t.addtime', order:'desc'}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.total != 0) {
                        $('.homeBbsTitle').show();
                        for(var i in result.rows) {
                            var bbs = result.rows[i];
                            buildBbs(bbs);
                        }

                        $(".homeBbsList .lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        // 开放更多按钮
                        if(result.total > 5) {

                        }
                    } else {
                        $('.homeBbsTitle, .homeBbsList').remove();
                    }
                }
            });
        }

        function drawTopicList() {
            ajaxPost('api/apiTopic/topicList', {page:1, rows:5, seq:1}, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.total != 0) {
                        $('.homeTopicTitle').show();
                        for(var i in result.rows) {
                            var topic = result.rows[i];
                            buildTopic(topic);
                        }

                        $(".homeTopicList .lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        // 开放更多按钮
                        if(result.total > 5) {

                        }
                    } else {
                        $('.homeTopicTitle, .homeTopicList').remove();
                    }
                }
            });
        }

        function drawBest(page) {
            currPage = page || currPage;
            var params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows, channel:'HOME'};
            ajaxPost('api/apiBestProductController/bestProductGroupList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        $('.bestTitle').show();
                        for(var i in result.rows) {
                            var best = result.rows[i];
                            var dom = buildBest(best, i);
                        }

                        loading = false;
                        currPage ++;
                    }

                    if(result.rows.length >= rows) {
                        $(".weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".weui-infinite-scroll").hide();
                    }
                } else {
                    $(document.body).destroyInfinite();
                    $(".weui-infinite-scroll").hide();
                }
                if(scrollTop > 0) {
                    $.mobile.silentScroll(scrollTop);
                    scrollTop = 0;
                }
            });
        }

        function buildBbs(bbs) {
            var viewData = Util.cloneJson(bbs);
            var color = '', d = false;
            viewData.spIcon = '';
            if(bbs.isLight) {
                color = 'rgb(19,172,189)';
                d = true;
                viewData.spIcon += '<img src="${pageContext.request.contextPath}/wsale/images/jsp-icon2.png" style="width:20px;" />';
            }
            if(bbs.isEssence) {
                color = 'rgb(252,79,30)';
                d = true;
                viewData.spIcon += '<img src="${pageContext.request.contextPath}/wsale/images/jsp-icon3.png" style="width:20px;" />';
            }

            viewData.name_time = '发帖人:'+bbs.addUserName;
            viewData.time = Util.getTime(bbs.addtime);
            viewData.count = '回复：'+bbs.bbsComment+' &nbsp;&nbsp;围观：' + bbs.bbsRead;

            var dom = Util.cloneDom("bbs_template", bbs, viewData);
            dom.find("[name=bbsTitle]").css('color', color);
            if(d) dom.find("[name=spIcon]").show();

            if(viewData.time.indexOf('刚刚') != -1 || viewData.time.indexOf('前') != -1) {
                dom.find("[name=time]").css('color', 'rgb(252,79,30)');
            }

            $(".homeBbsList").append(dom);
            // dom绑定事件
            dom.click(bbs.id, function(event){
                href('api/bbsController/bbsDetail?id=' + event.data);
            });
        }

        function buildTopic(topic) {
            var viewData = Util.cloneJson(topic);

            viewData.name_time = '发布人:' + topic.user.nickname;
            viewData.time = Util.getTime(topic.addtime);
            viewData.count = '点赞：'+topic.topicPraise+'&nbsp;&nbsp;阅读：' + topic.topicRead;

            var dom = Util.cloneDom("topic_template", topic, viewData);
            if(viewData.time.indexOf('刚刚') != -1 || viewData.time.indexOf('前') != -1) {
                dom.find("[name=time]").css('color', 'rgb(252,79,30)');
            }
            $(".homeTopicList").append(dom);
            // dom绑定事件
            dom.click(topic.id, function(event){
                href('api/apiTopic/topicDetail?id=' + event.data);
            });
        }

        function buildBest(best, index) {
            var viewData = Util.cloneJson(best);
            viewData.readCount = best.readCount || 0;
            var dom = Util.cloneDom("home_best_template", best, viewData);
            dom.find('.headImage').css('background-image', 'url('+best.user.headImage+')');
            if(best.user.self) dom.find('.attentionIt').hide();
            else {
                if(best.user.attred) {
                    dom.find('.attentioned').addClass('attred').html('已');
                }
            }
            if(best.products) {
                for(var i in best.products) {
                    var product = best.products[i];
                    dom.find('.pIconBox').append('<div class="swiper-slide pIconImg" data-pro-id="'+product.id+'" style="background-image: url(\''+product.icon+'\');"></div>');
                }
            }
            $(".bestList").append(dom);

            dom.find('.swiper-container').addClass('swiper-container' + index);
            dom.find('.swiper-pagination').addClass('swiper-p' + index);

            dom.find('.headImage, .nickname').click(best.user.id, function(event){
                $.cookie('home_best', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}), {expires:5*60*1000});
                href('api/apiShop/shop?userId=' + event.data);
            });

            dom.find('.pIconBox .pIconImg').click(function(){
                $.cookie('home_best', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}), {expires:5*60*1000});
                var productId = $(this).attr('data-pro-id');
                href('api/apiProductController/productDetail?id=' + productId);
            });

            dom.find('.attentionIt').click(best.user.id, function(event){
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
                var swiper = new Swiper ('.swiper-container' + index, {
                    pagination: '.swiper-p' + index,
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
                        $a.removeClass('attred').html('+&nbsp;');
                    } else {
                        $a.addClass('attred').html('已');
                    }
                }
            });
        }
    </script>
</body>

</html>
