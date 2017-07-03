<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>发帖</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="发帖" class="jqm-demos" style="background-color:#f0f0f0;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth" style="background-color:#fff;">
            <div class="mask-layer"></div>
            <div class="dialog-content">
                <div class="fenlei-liebiao" style="background-color:#eee;">
                    <span class="fenlei-title">分类</span>
                    <span class="fenlei-desc">请谨慎选择，切勿跨品类</span>
                </div>
                <div style="overflow-y: auto;" class="first-category">
                    <c:forEach items="${categorys}" var="category" varStatus="vs">
                        <div class="fenlei-liebiao first-style" categoryId="${category.id}" categoryName="${category.name}">
                            <img src="${pageContext.request.contextPath}/wsale/images/zhubao-icon.png" class="fenlei-img" /> <span class="fenlei-title">${category.name}</span>
                            <span class="fenlei-desc">${category.summary}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="second-style">
                <div class="second-title" style="background-color:#eee; text-align:center;">
                    <div class="retry-choose">重选</div>
                    <div class="choose-ok">完成</div>
                    <span class="fenlei-title"></span>
                </div>
                <div style="margin:10px;">
                    <div style="font-size:12px;color:#888;margin-bottom:10px;">请选择二级分类</div>
                    <div id="childCategory">
                        <c:forEach items="${childCategorys}" var="childCategory" varStatus="vs">
                            <div class="secondstyle-list" pid="${childCategory.pid}" categoryId="${childCategory.id}">${childCategory.name}</div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="home-content">
                <div class="type-dialog">
                    <div class="fenlei-liebiao" style="background-color:#eee;">
                        <span class="fenlei-title">请选择类型</span>
                    </div>
                    <div style="overflow-y: auto;" class="first-category">
                        <div class="fenlei-liebiao bbs-type">
                            <span class="fenlei-desc" data-value="BT01">鉴赏区</span>
                        </div>
                        <div class="fenlei-liebiao bbs-type">
                            <span class="fenlei-desc" data-value="BT02">鉴定部</span>
                        </div>
                    </div>
                </div>

                <div style="margin:0 10px;">
                    <input type="text" placeholder="请输入标题" id="bbsTitle" />
                    <textarea style="margin-top:20px;" placeholder="请输入帖子内容" id="bbsContent"></textarea>
                    <div style="text-align:left; margin-bottom: 10px;">
                        <div class="info-list chooseBbsType">
                            <div style="float:right;">
                                <input type="hidden" id="bbsType">
                                <span id="bbsTypeName">请选择类型</span>
                            </div>
                            <div class="normal-text">类型</div>
                        </div>
                        <div class="info-list chooseCategory">
                            <div style="float:right;">
                                <input type="hidden" id="categoryId">
                                <span id="categoryName">请选择分类</span>
                                <img class="arrow-right right-iocn" src="${pageContext.request.contextPath}/wsale/images/fenlei-icon.png" />
                            </div>
                            <div class="normal-text">分类</div>
                        </div>
                    </div>
                    <div>
                        <ul class="fatie-imglist" id="images">
                            <c:forEach items="${images}" var="image" varStatus="vs">
                                <li class="image" serverId="${image.serverId}">
                                    <div style="position: absolute;margin-left: 34px;margin-top: -13px;">
                                        <img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;padding: 8px;" class="del"/>
                                    </div>
                                    <img src="${image.localId}" width="55" height="55" />
                                </li>
                            </c:forEach>
                            <!--<li class="image">
                                <div style="position: absolute;margin-left: 42px;margin-top: -5px;">
                                    <img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;" />
                                </div>
                                <img src="${pageContext.request.contextPath}/wsale/images/jsq-list1.png" width="50" height="50" />
                            </li>-->
                            <li>
                                <div style="border:1px solid #ccc;width:50px;height:50px; text-align:center;" class="chooseImage">
                                    <img src="${pageContext.request.contextPath}/wsale/images/add-img-large.png" style="width:30px;height:30px;margin-top:9px;" />
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div style="position:absolute;margin:20px 10px; font-size:12px; color:#aaa;">提示：点击发布即表示同意<span style="color:#dc721c;">《竞拍服务协议》</span></div>
                <div>
                    <a id="publish" type="button" style="color:#fff;font-size:14px;display:block;height:40px; line-height:40px; background-color:#dc721c; position:absolute;bottom:20px;left:20px;right:20px;">发布</a>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        var categoryName = '';
        $(function(){
            $("#publish").one('click', publish);

            $(".chooseImage").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:3,msg:'正在上传...'});
                    // TODO 弹出loading
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        localId = localId || localIds[index-1];
                        $('.U-msg .moreMsg').html(index + "/" + localIds.length);
                        var $li = $('<li class="image"></li>').attr({serverId:serverId});
                        $li.append('<div style="position: absolute;margin-left: 34px;margin-top: -13px;"><img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;padding: 8px;" class="del"/></div>');
                        $li.append('<img src="'+localId+'" style="width:55px;height:55px;"/>');
                        $("#images li:last").before($li);
                        if(index == localIds.length) {
                            // TODO 关闭loading
                            setTimeout(function(){
                                $.loading.close();
                            }, 200);
                        }
                    });
                });
            });

            $("#images").on('click', '.image', function(){
                var imageUrls = [];
                $("#images li.image").each(function(){
                    var src = $(this).children("img").attr('src');
                    imageUrls.push(src);
                });
                JWEIXIN.previewImage(imageUrls, $(this).index());
            });

            $("#images").on('click', '.del', function(){
                var $image = $(this).closest('.image');
                $image.remove();
                event.stopPropagation();
                return false;
            });

            $('.chooseBbsType').click(function(){
                $(".mask-layer,.type-dialog").show();
            });

            $('.bbs-type').click(function(){
                $("#bbsTypeName").html($(this).find('span').html());
                $("#bbsType").val($(this).find('span').attr('data-value'));
                $(".mask-layer,.type-dialog").hide();
            });

            $(".first-style").click(function(){
                $('.second-title .fenlei-title').html($(this).attr("categoryName"));
                drawChildCategory($(this).attr("categoryId"));
            });

            $("#childCategory").on('click', '.secondstyle-list', function(){
                var _this = $(this);
                var pid = _this.attr('pid'), categoryId = _this.attr('categoryId');
                $('#categoryId').val(categoryId);

                categoryName = $(".first-style[categoryId="+pid+"] .fenlei-title").text() + " - " + _this.text();
            });

            $(".choose-ok").click(function(){
                if(categoryName != '') {
                    $('#categoryName').text(categoryName);
                }
            });
        });

        function drawChildCategory(pid) {
            $("#childCategory").empty();
            ajaxPost('api/apiCategoryController/categorys', {pid : pid}, function(data){
                if(data.success) {
                    var result = data.obj;
                    for(var i in result) {
                        $("#childCategory").append('<div class="secondstyle-list" pid="'+pid+'" categoryId="'+result[i].id+'">'+result[i].name+'</div>');
                    }
                    $(".dialog-content").hide();
                    $(".second-style").show();
                }
            });
        }

        function publish() {
            var bbsTitle = $.trim($('#bbsTitle').val());
            if(bbsTitle == '') {
                $.toptip('请输入标题');
                $("#publish").one('click', publish);
                return;
            }

            var bbsContent = $.trim($('#bbsContent').val());
            if(bbsContent == '') {
                $.toptip('请输入帖子内容');
                $("#publish").one('click', publish);
                return;
            }

            var bbsType = $("#bbsType").val();
            if(bbsType == '') {
                $.toptip('请选择类型');
                $("#publish").one('click', publish);
                return;
            }

            var categoryId = $("#categoryId").val();
            if(categoryId == '') {
                $.toptip('请选择分类');
                $("#publish").one('click', publish);
                return;
            }

            var $images = $("#images .image");
            if($images.length == 0 || $images.length > 9) {
                $.toptip('请上传1至9张图片');
                $("#publish").one('click', publish);
                return;
            }

            var mediaIds = $(".image").map(function(){
                return $(this).attr('serverId');
            }).get().join(',');
            var params = {categoryId : categoryId, bbsType:bbsType};
            params.bbsTitle = bbsTitle;
            params.bbsContent = bbsContent;
            params.mediaIds = mediaIds; //  调用微信JS-SDK上传图片
            ajaxPost('api/bbsController/publishBbs', params, function(data){
                if(data.success) {
                    replace('api/bbsController/bbsDetail?backCustom=true&id=' + data.obj);
                }
            },function(){
                $.loading.load({type:1, msg:'正在发布...'});
            },-1);
        }
    </script>
</body>

</html>
