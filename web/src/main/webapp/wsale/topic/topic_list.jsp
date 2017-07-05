<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${isHomeHot}">专题推荐</c:when>
            <c:otherwise> 专题文章</c:otherwise>
        </c:choose>
    </title>
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
        .topic-list-flex{
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%;
        }
    </style>
</head>
<body>
<div data-role="page" class="jqm-demos">
    <div role="main" class="ui-content jqm-content jqm-fullwidth">
        <div id="msgPopup" class="weui-popup-container popup-bottom">
            <div class="weui-popup-overlay"></div>
            <div class="weui-popup-modal" style="height: 200px;overflow: hidden; text-align: center;background-color: #fff;">
                <div class="modal-content" style="padding-top: 0;overflow: hidden; ">
                    <div style="font-size: 10pt;padding: 8px;">
                        目前不支持手机端发布&#12288;&#12288;&#12288;&#12288;<br>
                        复制链接登录PC客户端&#12288;&#12288;&#12288;&nbsp;&nbsp;<br>
                        微信扫码登录即可发布集物专题
                    </div>
                    <div class="cparea">
                        <div class="kwd" id="kwd"><span>https://m.zcys2016.com/wsale</span></div>
                    </div>
                    <div class="cparea" style="margin-bottom: 10px;">
                        <span class="kdes"><b>★</b>长按虚线框,拷贝链接</span>
                    </div>
                </div>
            </div>
        </div>

        <div style="padding:0px 10px;" class="topicList">
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
                <li><a rel="external" href="javascript:toPublish();" data-prefetch="true" data-transition="turn" data-icon="edit">发布</a></li>
                <li><a rel="external" href="javascript:href('api/userController/my');" data-prefetch="true" data-transition="turn" data-icon="user">我的</a></li>
            </ul>
        </div><!-- /navbar -->
    </div><!-- /footer -->
    <jsp:include page="../template/topic_template.jsp"></jsp:include>
</div><!-- /page -->

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
                drawTopicList();
            }, 20);
        });

        var obj = $.cookie('topic_list');
        if(obj != null) {
            $.cookie('topic_list', null);
            obj = $.parseJSON(obj);
            scrollTop = obj.scrollTop;
            drawTopicList(obj.currPage);
        } else {
            drawTopicList();
        }
    });

    function drawTopicList(page) {
        currPage = page || currPage;
        var params = {page:(page && 1) || currPage, rows:(page && page*rows) || rows};
        if(${!empty addUserId}) params.addUserId = '${addUserId}';
        if(${isHomeHot}) {
            params.seq = 1;
        }
        ajaxPost('api/apiTopic/topicList', params, function(data){
            if(data.success) {
                var result = data.obj;
                if(result.rows.length != 0) {
                    for(var i in result.rows) {
                        var topic = result.rows[i];
                        buildTopic(topic);
                    }

                    $(".topicList .lazy").lazyload({
                        placeholder : base + 'wsale/images/lazyload.png'
                    });

                    loading = false;
                    currPage ++;
                } else {
                    if(result.total == 0)
                        $(".topicList").append(Util.noDate(1, '没有相关专题'));
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

    function buildTopic(topic) {
        var viewData = Util.cloneJson(topic);

        viewData.name_time = '发布人:' + topic.user.nickname;
        viewData.time = Util.getTime(topic.addtime);
        viewData.count = '点赞：'+topic.topicPraise+'&nbsp;&nbsp;阅读：' + topic.topicRead;

        var dom = Util.cloneDom("topic_template", topic, viewData);
        if(viewData.time.indexOf('刚刚') != -1 || viewData.time.indexOf('前') != -1) {
            dom.find("[name=time]").css('color', 'rgb(252,79,30)');
        }
        $(".topicList").append(dom);
        // dom绑定事件
        dom.click(topic.id, function(event){
            $.cookie('topic_list', JSON.stringify({scrollTop:$(window).scrollTop(), currPage:currPage-1}));
            href('api/apiTopic/topicDetail?id=' + event.data);
        });
    }

    function toPublish() {
        $('#msgPopup').wePopup();
    }

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
