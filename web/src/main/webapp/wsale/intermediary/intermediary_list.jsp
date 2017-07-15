<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>中介交易</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <style>
        .wode-paipintitle li{
            width: 25%;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div data-role="page" class="jqm-demos">

    <div id="index-content" role="main" class="ui-content jqm-content jqm-fullwidth">
        <div class="mask-layer-1" style="display: none;"></div>

        <div id="refusePopup" class="weui-popup-container">
            <div class="weui-popup-overlay"></div>
            <div class="weui-popup-modal" style="overflow: hidden;">
                <div class="modal-content" style="padding-top: 0; margin-top: 0px; overflow: hidden;">
                    <div style="background-color:#fff; padding: 0 5px;border-bottom:1px solid #ddd;">
                        <div style="float:right;padding: 10px 0px;width:15%; text-align:center;color: green;" class="refusePopupBtn">
                            确 认
                        </div>
                        <div style="width:80%; padding: 10px;" class="close-popup">
                            <span style="padding: 10px 0px;">关 闭</span>
                        </div>
                    </div>
                    <textarea style="margin:10px 0px; background-color: #fff;" maxlength="100" placeholder="请输入拒绝原因..." id="content"></textarea>
                </div>
            </div>
        </div>

        <div class="home-content" style="margin:0; ">
            <div>
                <ul class="wode-paipintitle">
                    <li class="wodepaipin-active">待处理</li>
                    <li>已完成</li>
                    <li>已取消</li>
                </ul>
            </div>
            <div class="imList">
            </div>
            <div class="weui-infinite-scroll">
                <div class="infinite-preloader"></div>
                正在加载中
            </div>
        </div>
        <jsp:include page="../template/im_template.jsp"></jsp:include>
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
    var loading = true, currPage = 1, rows = 10, nowTime = new Date().getTime();
    var pDom = null, refuseId = null;
    $(function(){
        $(document.body).on("infinite", function() {
            if(loading) return;
            loading = true;
            setTimeout(function() {
                var index = $('.wode-paipintitle li[class=wodepaipin-active]').index();
                drawIntermediary(index + 1);
            }, 20);
        });

        drawIntermediary(1);

        $('.wode-paipintitle li').click(function() {
            $('.mask-layer-1').show();
            currPage = 1;
            var num = $(this).index();
            $(document.body).destroyInfinite();
            $(".imList").empty();
            $(".home-content .weui-infinite-scroll").show();
            drawIntermediary(num + 1);
        });

        $('.refusePopupBtn').bind('click', refuseIM);
    });

    function drawIntermediary(type) {
        type = type || 1;
        var params = {page:currPage, rows:rows};
        if(type == 1) {
            params.status = 'IS01';
        } else if(type == 2) {
            params.status = 'IS02';
        } else if(type == 3) {
            params.status = 'IS03';
        }
        ajaxPost('api/apiIntermediary/intermediaryList', params, function(data){
            if(data.success) {
                var result = data.obj;
                if(result.rows.length != 0) {
                    for(var i in result.rows) {
                        var intermediary = result.rows[i];
                        buildIntermediary(intermediary, type);
                    }

                    loading = false;
                    currPage ++;
                } else {
                    if(result.total == 0)
                        $(".imList").append(Util.noDate(2, '这里还没有内容<br>前往论坛申请交易哟'));
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

    function buildIntermediary(intermediary, type) {
        var viewData = Util.cloneJson(intermediary);
        viewData.nickname = intermediary.isBuyer ?  intermediary.imUser.nickname + '(卖家)' : intermediary.imUser.nickname + '(买家)';
        viewData.amount = '交易金额：￥' + Util.fenToYuan(intermediary.amount);
        var btnHtml = '', otherDownTime = 0, otherDownMsg = '',
            cancelBtn = '<li class="cancelBtn">取消交易</li>',
            agreeBtn = '<li class="agreeBtn">同意交易</li>',
            refuseBtn = '<li class="refuseBtn">拒绝交易</li>';
        if(type == 1) {
            viewData.time = '申请时间：' + new Date(intermediary.lastLog.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
            otherDownTime = (new Date(intermediary.lastLog.addtime.replace(/-/g,"/")).getTime() + 72*60*60*1000) - nowTime;
            otherDownMsg = '同意截止';
            if(intermediary.isBuyer) {
                btnHtml = cancelBtn;
            } else {
                btnHtml = agreeBtn + refuseBtn;
            }
        } else if(type == 2) {
            viewData.time = '同意时间：' + new Date(intermediary.lastLog.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
        } else if(type == 3) {
            viewData.time = '取消时间：' + new Date(intermediary.lastLog.addtime.replace(/-/g,"/")).format('MM月dd日 HH:mm');
            otherDownMsg = '取消原因：' + intermediary.lastLog.logTypeZh;
            if(intermediary.lastLog.content)
                otherDownMsg += ' - ' + intermediary.lastLog.content;
        }
        var dom = Util.cloneDom("im_template", intermediary, viewData);
        $(".imList").append(dom);

        if(intermediary.imUser.mobile && intermediary.imUser.mobile != 'undefined') {
            dom.find('.tel').attr('href', 'tel:' + intermediary.imUser.mobile);
        }

        // 跳转用户主页
        dom.find('.nickname').click(intermediary.imUser.id, function(event){
            href('api/userController/homePage?userId=' + event.data);
        });
        // 跳转详情
        dom.find('.dingdan-content').click(intermediary.id, function(event){
            href('api/apiIntermediary/intermediaryDetail?id=' + event.data);
        });

        var $opearte = dom.find('.dingdan-opearte');
        if(btnHtml == '') $opearte.remove();
        else $opearte.html(btnHtml);

        if(otherDownMsg != '') {
            dom.find('[name=other]').show();
            if(otherDownTime == 0) {
                dom.find('[name=other]').html(otherDownMsg);
            } else {
                addTimer(dom.find('[name=other]'), otherDownTime/1000, otherDownMsg);
            }

        }

        $opearte.find('.cancelBtn').click(intermediary.id, cancelFun);
        $opearte.find('.agreeBtn').click(intermediary.id, agreeFun);
        $opearte.find('.refuseBtn').click(intermediary.id, refuseFun);

        return dom;
    }

    // 取消交易
    function cancelFun(event) {
        var $p = $(this).closest('.dingdan-list');
        $.confirm("您确认要取消该中介交易?", "系统提示", function() {
            ajaxPost('api/apiIntermediary/cancelIM', {id:event.data}, function(data){
                if(data.success) {
                    $.toast("取消成功");
                    $p.remove();
                }
            });
        }, function() {});
    }

    function agreeFun(event) {
        var $p = $(this).closest('.dingdan-list');
        $.confirm("确认后生成订单，是否同意?", "系统提示", function() {
            ajaxPost('api/apiIntermediary/agreeIM', {id:event.data}, function(data){
                if(data.success) {
                    $.toast("已同意");
                    $p.remove();
                }
            });
        }, function() {});
    }

    function refuseFun(event) {
        pDom = $(this).closest('.dingdan-list');
        refuseId = event.data;
        $.confirm("本次操作拒绝交易，是否继续?", "系统提示", function() {
            $('#refusePopup').wePopup();
        });
    }

    function refuseIM() {
        var content = $.trim($('#content').val());
        if(Util.checkEmpty(content)) {
            $.toptip("请输入拒绝原因");
            return;
        }

        $.closePopup();
        ajaxPost('api/apiIntermediary/refuseIM', {id:refuseId, content:content}, function(data){
            if(data.success) {
                $.toast("已拒绝");
                pDom.remove();
            }
        });
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
            else return "交易取消";
        }
    }) ();
</script>
</body>

</html>
