<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>${title}</title>
    <script type="text/javascript">

    </script>
    <jsp:include page="../inc.jsp"></jsp:include>
    <style>
        .cparea{
            text-align: center;
            font-family: Microsoft Yahei;
            margin: -2em 0 0;
        }
        .kwd{
            display: inline-block;
            color: #272727;
            background-color: #fff;
            font-size: 1.1875em;
            font-size: 1.1875em;
            padding: .75em 1em;
            border: 1px dashed #e60012;
            -webkit-user-select:element;
            margin: 2em;
        }
        .kwd span{
            display: block;
            border: 1px solid #fff;
        }
        .kdes{
            display: inline-block;
            color: #212121;
            font-size: .875em;
            padding-top: 0;
        }
        .kdes b{
            color: #ed5353;
            font-size: 1.25em;
            padding-right: .1em;
        }
        .infocenter-number {
            top: 8px;
            margin-left: -2px;
            min-width: 0;
            min-height: 0;
            width: 8px;
            height: 8px;
        }
    </style>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_bjjs')}">
        <script type="text/javascript">
            $(function(){
                $(".bianji-btn").show();
                $(".bianji-area").css('width', '84%');
            });
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_on_off')}">
        <script type="text/javascript">
            $.authOnOff = true; // 关闭/打开
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_jdxq')}">
        <script type="text/javascript">
            $.authJdxq = true; // 查看鉴定区帖子详情
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, 'auth_jtft')}">
        <script type="text/javascript">
            $.authJtft = true; // 讲堂发帖
        </script>
    </c:if>
</head>
<body>
    <div data-role="page" class="jqm-demos">
        <input type="hidden" id="categoryId" value="${category.id}"/>
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div id="msgPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 200px;overflow: hidden; text-align: center;background-color: #fff;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden; ">
                        <div style="font-size: 10pt;padding: 8px;">
                            目前不支持手机端上传音频&#12288;&#12288;<br>
                            复制链接登录PC客户端&#12288;&#12288;&#12288;&nbsp;&nbsp;<br>
                            微信扫码登录即可发布语音节目
                        </div>
                        <!--<div class="faxian-list renzheng-input">
                            <input style="padding: 20px 0px;background-color: #fff;text-align: center;font-size: 14pt;" type="text" value="http://zcys2016.com/wsale" readonly/>
                        </div>-->
                        <div class="cparea">
                            <div class="kwd" id="kwd"><span>https://m.zcys2016.com/wsale</span></div>
                        </div>
                        <div class="cparea" style="margin-bottom: 10px;">
                            <span class="kdes"><b>★</b>长按虚线框,拷贝链接</span>
                        </div>
                    </div>
                </div>
            </div>

            <div>
                <div class="forum-top" style="z-index:1;border-bottom:10px solid #f5f5f5;position:fixed; top:0; left:0; right:0; background-color:#fff;">
                    <div style="float:right;margin:7px 0px;border-left:1px solid #ddd;width:15%; text-align:center;" class="cbp-vm-icon" data-view="cbp-vm-view-grid">
                        <img style="width:18px;" src="${pageContext.request.contextPath}/wsale/images/jsq-icon1.png">
                    </div>
                    <div style="width:83%;">
                        <ul class="tab-title">
                            <li id="hdlt-title" class="titletab-active">互动论坛</li>
                            <li id="mrwp-title">
                                默认拍品
                                <c:if test="${!empty pCount and pCount > 0}">
                                    <span class="infocenter-number"></span>
                                </c:if>
                            </li>
                            <li id="jxwp-title">
                                精选拍品
                                <c:if test="${!empty bpCount and bpCount > 0}">
                                    <span class="infocenter-number"></span>
                                </c:if>
                            </li>
                        </ul>
                    </div>
                </div>
                <div style="margin-top:47px;">
                    <div class="hdlt-title">
                        <div style="padding:10px; border-bottom:1px solid #ddd;">
                            <a class="bianji-btn" style="display:block;width:15%;;height:28px; text-align:center; line-height:28px;color:#fff;font-size:12px; background-color:rgb(252,79,30); float:right;display: none;">编辑</a>
                            <div class="bianji-area" style="font-size:12px; color:rgb(252,79,30); width:100%; display:inline-block;">
                                <span class="bianji-content">[板块介绍]<content class="introduce">${category.forumIntroduce}</content></span>
                                <input type="text" class="bianji-input" style="font-size:12px;" />
                            </div>
                        </div>
                        <div style="padding:10px; border-bottom:1px solid #ddd;">

                            <div style="font-size:12px; line-height:1.5;">
                                <a href="javascript:positionApply();" style="display:block;width:60px;height:20px; text-align:center; line-height:20px;color:#fff;font-size:12px; background-color:#F7B361; float:right;">申请职位</a>
                                <!--<div>
                                    <span style="color:#aaa;">首席版主：</span>
                                    <span>${empty category.chiefModeratorName ? "无" : category.chiefModeratorName}</span>
                                </div>-->
                            </div>
                            <div style="font-size:12px; line-height:1.5;margin-top:5px;">
                                <span style="color:#aaa;">版主：</span>
                                <span style="letter-spacing: -1px;">
                                    <c:choose>
                                        <c:when test="${category.moderators.size() > 0}">
                                            <c:forEach items="${category.moderators}" var="moderator" varStatus="vs">
                                                <c:if test="${vs.index != 0}">、</c:if>
                                                ${moderator.nickname}
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>无</c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>
                        <div style="padding:10px;font-size:14px;border-bottom:1px solid #ddd;">
                            <div class="list-tab">
                                <a href="javascript:void(0);" rel="external" style="width:27%;height:30px; line-height:30px;background-color:#C87C1C;border:1px solid #C87C1C;color:#fff; text-align:center; display:inline-block;">鉴赏区</a>
                                <a href="javascript:void(0);" rel="external" style="width:27%;height:30px; line-height:30px;border:1px solid #C87C1C;color:#C87C1C; text-align:center;display:inline-block;margin:0 7%;">鉴定部</a>
                                <a href="javascript:void(0);" rel="external" style="width:27%;height:30px; line-height:30px;border:1px solid #C87C1C;color:#C87C1C; text-align:center;display:inline-block; float:right;">精英讲堂</a>
                            </div>
                            <div style="margin-top:15px; display: none;">
                                <a href="javascript:moreTopBbs();" style="float:right;margin-top:2px;">更多 ></a>
                                <div class="topBbs">
                                    <span style="background-color:#ff0000;color:#fff;font-size:12px;padding:2px 5px;margin-right:10px;">置顶</span><span>石卯文化之玉</span>
                                    <div style="font-size:12px;color:#aaa;margin-top:5px;">
                                        发帖人：渡劫者少卿&nbsp;&nbsp;发帖时间：2015-12-12
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="padding:0px 10px;" class="bbsList">
                            <ul class="tiezi-tab">
                                <li style="color:#dc721c;">| 全部帖子</li>
                                <li>| 精华帖子</li>
                                <li>| 加亮帖子</li>
                                <li>| 发帖时间</li>
                            </ul>
                        </div>
                    </div>
                    <div class="mrwp-title" style="">
                        <div id="cbp-vm" class="cbp-vm-switcher cbp-vm-view-grid">
                            <ul class="defaultProducts"></ul>
                        </div>
                    </div>
                    <div class="jxwp-title">
                        <div class="cbp-vm-switcher cbp-vm-view-grid">
                            <ul class="bestProducts"></ul>
                        </div>
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
                    <li><a rel="external" href="javascript:toPublish();" data-prefetch="true" data-transition="turn" data-icon="edit" class="publish">发帖</a></li>
                    <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
                </ul>
            </div><!-- /navbar -->
        </div><!-- /footer -->
        <jsp:include page="../template/bbs_template.jsp"></jsp:include>
        <jsp:include page="../template/product_template.jsp"></jsp:include>
    </div>

    <script type="text/javascript">
        var loading = true;
        var bbsType = 'BT01';
        var isSp = 0; // 0-全部；1-精华；2-加亮
        var currPage = 1;
        var rows = 10;
        var topTab = 0;
        var scrollTop = 0;
        $(function(){
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    if(topTab == 0) drawBbsList();
                    else if(topTab == 1) drawProduct();
                    else drawBestProduct();
                }, 20);
            });

            $(".tab-title li").bind("click", function(){
                if($(this).hasClass("titletab-active")) return;
                topShow($(this));
                var num = $(this).index();
                if(num == 0) initBbs();
                else if(num == 1) initProduct();
                else initBestProduct();
            });

            $(".tiezi-tab li").bind('click', function(){
                currPage = 1;
                isSp = $(this).index();
                drawBbsList(true);
                $(this).css({'color':'#dc721c'});
                $(this).siblings().css({'color':''});
            });
            $(".list-tab a").bind('click', function(){
                currPage = 1;
                var num = $(this).index();
                if(num == 0) bbsType = 'BT01';
                else if(num == 1) bbsType = 'BT02';
                else bbsType = 'BT03';
                drawTopBbs();
                drawBbsList(true);
                $(this).css({'color':'#fff', 'background-color' : '#C87C1C'});
                $(this).siblings().css({'color':'#C87C1C', 'background-color':''});
            });

            $("body").on("click",".bianji-btn",function(){
                var _this = this;
                if($('.bianji-content').is(':hidden')) {
                    var old_introduce = $(".bianji-content .introduce").text(),
                        introduce = $.trim($(".bianji-input").val());
                    if(introduce == '' || old_introduce == introduce) {
                        $(_this).html('编辑');
                        $(".bianji-content").show();
                        // $(".bianji-content .introduce").text(introduce);
                        $(".bianji-area .ui-input-text").hide();
                    } else {
                        ajaxPost('api/apiCategoryController/editIntroduce', {id : $("#categoryId").val(),forumIntroduce:introduce}, function(data){
                            if(data.success) {
                                $(_this).html('编辑');
                                $(".bianji-content").show();
                                $(".bianji-content .introduce").text(introduce);
                                $(".bianji-area .ui-input-text").hide();
                            }
                        });
                    }
                } else {
                    $(_this).html('保存');
                    $(".bianji-content").hide();
                    $(".bianji-area .ui-input-text").show();
                    $(".bianji-input").val($(".bianji-content .introduce").text()).focus();
                }


            });

            //$(".bianji-input").blur(function(){

            //});

            var obj = $.cookie('forum');
            if(obj != null) {
                $.cookie('forum', null);
                obj = $.parseJSON(obj);
                scrollTop = obj.scrollTop;
                if(obj.topTab == 0) {
                    bbsType = obj.bbsType;
                    isSp = obj.isSp;
                    var btNum = 0;
                    if(bbsType == 'BT02') btNum = 1;
                    else if(bbsType == 'BT03') btNum = 2;
                    $(".list-tab a").eq(btNum).css({'color':'#fff', 'background-color' : '#C87C1C'});
                    $(".list-tab a").eq(btNum).siblings().css({'color':'#C87C1C', 'background-color':''});
                    $(".tiezi-tab li").eq(isSp).css({'color':'#dc721c'});
                    $(".tiezi-tab li").eq(isSp).siblings().css({'color':''});

                    initBbs(obj.currPage);
                } else if(obj.topTab == 1) {
                    topShow(1);
                    initProduct(obj.currPage);
                } else {
                    topShow(2);
                    initBestProduct(obj.currPage);
                }
            } else {
                initBbs();
            }
        });

        function topShow(em) {
            var _this = em;
            if(typeof em == 'number') {
                _this = $(".tab-title li").eq(em);
            }
            _this.addClass("titletab-active");
            _this.siblings().removeClass("titletab-active");
            var flId = _this.attr("id");
            $("."+flId).show();
            $("."+flId).siblings().hide();
        }

        function initBbs(page) {
            $('.publish').html('发帖');
            currPage = page || 1, topTab = 0;
            drawTopBbs();
            drawBbsList(true,page && page*rows);
        }

        function initProduct(page) {
            $('.publish').html('发布');
            currPage = page || 1, topTab = 1;
            drawProduct(true, page && page*rows);

            if($("#mrwp-title .infocenter-number").length != 0) {
                $("#mrwp-title .infocenter-number").remove();
            }
        }

        function initBestProduct(page) {
            $('.publish').html('发布');
            currPage = page || 1, topTab = 2;
            drawBestProduct(true, page && page*rows);

            if($("#jxwp-title .infocenter-number").length != 0) {
                $("#jxwp-title .infocenter-number").remove();
            }
        }

        function drawTopBbs() {
            $(".topBbs").empty();
            var params = {categoryId : $("#categoryId").val(), page:1, rows:1, isTop:true, bbsType:bbsType};
            if(isSp == 1)  params.isEssence = true;
            else if(isSp == 2) params.isLight = true;
            else if(isSp == 3) params.sort = 'addtime';
            params.bbsStatus = 'BS01';
            ajaxPost('api/bbsController/bbsList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.total != 0) {
                        $(".topBbs").parent().show();
                        var topBbs = result.rows[0];
                        $(".topBbs").append('<span style="background-color:#ff0000;color:#fff;font-size:12px;padding:2px 5px;margin-right:10px;">置顶</span><span>'+topBbs.bbsTitle+'</span>');
                        $(".topBbs").append('<div style="font-size:12px;color:#aaa;margin-top:5px;"> 发帖人：'+topBbs.addUserName+'&nbsp;&nbsp;发帖时间：'+Util.getTime(topBbs.addtime)+' </div>');
                        $(".topBbs").click(topBbs, function(event){
                            var topBbs = event.data;
                            href('api/bbsController/bbsDetail?id=' + topBbs.id);
                        });
                    } else {
                        $(".topBbs").parent().hide();
                    }

                }
            });
        }

        function drawBbsList(load,loadRows) {
            if(load) $(".bbsList a, .bbsList .nodata").remove();
            $(document.body).destroyInfinite();
            var params = {categoryId : $("#categoryId").val(), page:(loadRows && 1) || currPage, rows:loadRows || rows, bbsType:bbsType};
            if(isSp == 1)  params.isEssence = true;
            else if(isSp == 2) params.isLight = true;
            else if(isSp == 3) params.sort = 'addtime';
            ajaxPost('api/bbsController/bbsList', params, function(data){
                if(data.success) {

                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var bbs = result.rows[i];
                            buildBbs(bbs);
                        }
                        $(".bbsList img.lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        loading = false;
                        currPage ++;
                    } else {
                        if($(".bbsList a").length == 0)
                            $(".bbsList").append(Util.noDate(1));
                    }

                    if(result.rows.length >= rows) {
                        $(document.body).infinite();
                        $(".home-content .weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".home-content .weui-infinite-scroll").hide();
                    }

                }
                if(scrollTop > 0) {
                    $.mobile.silentScroll(scrollTop);
                    scrollTop = 0;
                }

            }, function(){
                if(load) $.loading.load({type:2});
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
            viewData.time = Util.getTime(bbs.addtime); //bbs.addtime.substring(0, 10);
            viewData.count = '回复：'+bbs.bbsComment+' &nbsp;&nbsp;围观：' + bbs.bbsRead;

            var dom = Util.cloneDom("bbs_new_template", bbs, viewData);
            dom.find('.icon').attr("data-original", bbs.icon);
            dom.find('.icon').css('background-image','url("'+bbs.icon+'")')
            dom.find("[name=bbsTitle]").css('color', color);
            if(d) dom.find("[name=spIcon]").show();
            if(viewData.time.indexOf('刚刚') != -1 || viewData.time.indexOf('前') != -1) {
                dom.find("[name=time]").css('color', 'rgb(252,79,30)');
            }
            $(".bbsList").append(dom);
            // dom绑定事件
            dom.click(bbs, function(event){
                var bbs = event.data;
                if('${sessionInfo.id}' != bbs.addUserId && !$.authOnOff && bbs.bbsStatus == 'BS02') {
                    $.alert("该帖子已关闭！", "系统提示！");
                    return;
                }
                /*if(bbsType == 'BT02' && !$.authJdxq) {
                    $.alert("您目前不能查看鉴定部帖子哟！", "系统提示！");
                    return;
                }*/
                $.cookie('forum', JSON.stringify({topTab:topTab,bbsType:bbsType,isSp:isSp, scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/bbsController/bbsDetail?id=' + bbs.id);
            });
        }

        function drawProduct(load, loadRows) {
            if(load) $(".defaultProducts").empty();
            $(document.body).destroyInfinite();
            var params = {categoryId : $("#categoryId").val(), page:(loadRows && 1) || currPage, rows:loadRows || rows, status:'PT03'};
            ajaxPost('api/apiProductController/productList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var product = result.rows[i];
                            var dom = buildProduct(product);
                            $(".defaultProducts").append(dom);
                            $('.product-list-img').eq(i).css('background-image','url('+result.rows[i].icon+')')
                        }


                        $(".defaultProducts img.lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        loading = false;
                        currPage ++;
                    } else {
                        if($(".defaultProducts a").length == 0)
                            $(".defaultProducts").append(Util.noDate(2));
                    }

                    if(result.rows.length >= rows) {
                        $(document.body).infinite();
                        $(".home-content .weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".home-content .weui-infinite-scroll").hide();
                    }

                }
                if(scrollTop > 0) {
                    $.mobile.silentScroll(scrollTop);
                    scrollTop = 0;
                }
            }, function(){
                if(load) $.loading.load({type:2});
            });
        }

        function drawBestProduct(load, loadRows) {
            if(load) $(".bestProducts").empty();
            $(document.body).destroyInfinite();
            var params = {categoryId : $("#categoryId").val(), page:(loadRows && 1) || currPage, rows:loadRows || rows, channel:'CATEGORY'};
            ajaxPost('api/apiBestProductController/bestProductList', params, function(data){
                if(data.success) {
                    var result = data.obj;
                    if(result.rows.length != 0) {
                        for(var i in result.rows) {
                            var product = result.rows[i].zcProduct;
                            var dom = buildProduct(product);
                            $(".bestProducts").append(dom);
                        }
                        $(".bestProducts img.lazy").lazyload({
                            placeholder : base + 'wsale/images/lazyload.png'
                        });

                        loading = false;
                        currPage ++;
                    } else {
                        if($(".bestProducts a").length == 0)
                            $(".bestProducts").append(Util.noDate(2, '暂无精拍'));
                    }

                    if(result.rows.length >= rows) {
                        $(document.body).infinite();
                        $(".home-content .weui-infinite-scroll").show();
                    } else {
                        $(document.body).destroyInfinite();
                        $(".home-content .weui-infinite-scroll").hide();
                    }

                }
                if(scrollTop > 0) {
                    $.mobile.silentScroll(scrollTop);
                    scrollTop = 0;
                }
            }, function(){
                if(load) $.loading.load({type:2});
            });
        }

        function buildProduct(product) {
            var viewData = Util.cloneJson(product);
            var likeIcon = product.liked ? '${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png' : '${pageContext.request.contextPath}/wsale/images/guanzhu-icon.png';
            var likeClass = product.liked ? 'liked' : '';
            viewData.likeCount = '<img src="'+likeIcon+'" style="width:15px;" class="'+likeClass+'"/> <count>' + product.likeCount + '</count>';
            viewData.currentPrice = '￥' + product.currentPrice;
            var dom = Util.cloneDom("product_template", product, viewData);
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
            dom.find('.cbp-vm-image').click(product.id, function(event){
                $.cookie('forum', JSON.stringify({topTab:topTab, scrollTop:$(window).scrollTop(), currPage:currPage-1}));
                href('api/apiProductController/productDetail?id=' + event.data);
            });

            addTimer(dom.find('.cbp-vm-time'), product.deadlineLen);
            return dom;
        }

        function moreTopBbs() {
            $.cookie('forum', JSON.stringify({topTab:topTab,bbsType:bbsType,isSp:isSp, scrollTop:$(window).scrollTop(), currPage:currPage-1}));
            href('api/bbsController/topBbs?categoryId=' + $("#categoryId").val() + "&bbsType=" + bbsType);
        }

        function toPublish() {
            $.cookie('forum', JSON.stringify({topTab:topTab,bbsType:bbsType,isSp:isSp, scrollTop:$(window).scrollTop(), currPage:currPage-1}));
            if(topTab == 0) {
                if(bbsType == 'BT03') {
                    if(!$.authJtft) {
                        $.modal({
                            title: "系统提示",
                            text: "不能发布哦，建议您去申请讲师职位！",
                            buttons: [
                                { text: "取消", className: "default"},
                                { text: "去申请", onClick: function(){
                                    positionApply();
                                } }
                            ]
                        });
                        return;
                    } else {
                        $('#msgPopup').wePopup();
                        //$(".mask-layer,.fensi-dialog").show();
                        //$.alert("请登录后台发布，暂缺导航页！", "系统提示！");
                    }
                    //href('api/bbsController/toPublishForumBbs?categoryId=' + $("#categoryId").val() + "&bbsType=" + bbsType);
                } else {
                    href('api/bbsController/toPublish?categoryId=' + $("#categoryId").val() + "&bbsType=" + bbsType);
                }
            } else {
                href('api/apiProductController/toFirst');
            }
        }

        function positionApply() {
            href('api/apiPositionApply/applyOne?categoryId=' + $("#categoryId").val());
        }

        var addTimer = (function () {
            var list = [], interval;

            return function (dom, time) {
                if (!interval)
                    interval = setInterval(go, 1000);
                list.push({ ele: dom, time: time });
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
                var d = Math.floor(time / 86400),
                    h = Math.floor((time % 86400) / 3600),
                    m = Math.floor(((time % 86400) % 3600) / 60),
                    s = Math.floor(((time % 86400) % 3600) % 60);
                if (time > 0) {
                    var dh = d == 0 ? '' : '<span class="cbp-vm-timenumber">'+d+'</span>天';
                    return '距截拍：'+dh+'<span class="cbp-vm-timenumber">'+h+'</span>时<span class="cbp-vm-timenumber">'+m+'</span>分<span class="cbp-vm-timenumber">'+s+'</span>秒'
                }
                else return "已截拍";
            }
        }) ();

        wx.ready(function () {
            JWEIXIN.showOptionMenu();
            var shareData = {
                title:"${title} - 这里应有尽有，尽情挑吧！",
                desc:"集东集西--不仅珍宝立刻拍回家还可以玩转贴吧哟！",
                link:server_url + base + 'api/apiCategoryController/forum?id=' + $("#categoryId").val(),
                imgUrl:server_url + base + 'wsale/images/jsq-list1.png'
            };
            JWEIXIN.onMenuShareAppMessage(shareData);
            JWEIXIN.onMenuShareTimeline(shareData);
            JWEIXIN.onMenuShareQQ(shareData);
            JWEIXIN.onMenuShareWeibo(shareData);
            JWEIXIN.onMenuShareQZone(shareData);
        });

        $(function(){
            $("#kwd").bind("taphold", function(){
                var doc = document,
                        text = doc.getElementById("kwd"),
                        range,
                        selection;
                if (doc.body.createTextRange) { //IE
                    range = document.body.createTextRange();
                    range.moveToElementText(text);
                    range.select();

                } else if (window.getSelection) {   //FF CH SF
                    selection = window.getSelection();
                    range = document.createRange();
                    range.selectNodeContents(text);
                    selection.removeAllRanges();
                    selection.addRange(range);

                    //测试
                    console.log(text.textContent);
                    text.innerText && console.log(text.innerText);  //FireFox不支持innerText
                    console.log(text.textContent.length);
                    text.innerText && console.log(text.innerText.length);   //在Chrome下长度比IE/FF下多1
                    console.log(text.firstChild.textContent.length);
                    text.innerText && console.log(text.firstChild.innerText.length);
                    console.log(text.firstChild.innerHTML.length);

                    //注意IE9-不支持textContent
                    makeSelection(0, text.firstChild.textContent.length, 0, text.firstChild);
                    /*
                     if(selection.setBaseAndExtent){
                     selection.selectAllChildren(text);
                     selection.setBaseAndExtent(text, 0, text, 4);
                     }
                     */
                }else{
                    alert("浏览器不支持长按复制功能");
                }

            });
            function makeSelection(start, end, child, parent) {
                var range = document.createRange();
                //console.log(parent.childNodes[child]);
                range.setStart(parent.childNodes[child], start);
                range.setEnd(parent.childNodes[child], end);

                var sel = window.getSelection();
                sel.removeAllRanges();
                sel.addRange(range);
            }
        });

    </script>
</body>

</html>
