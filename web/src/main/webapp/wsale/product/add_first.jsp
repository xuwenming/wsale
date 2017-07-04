<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>发布拍品</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="发布拍品" class="jqm-demos" style="background-color:#f5f5f5;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div class="mask-layer" style="z-index: 99;"></div>
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

            <div class="home-content" style="margin:0;">
                <div style="background-color:#fff;">
                    <a class="right-next" style="color:#F56A22;font-size:14px;">
                        下一步
                    </a>
                    <div>
                        <a class="left-text" style="font-size:14px;">
                            存为草稿
                        </a>
                    </div>
                </div>
                <div style="background-color:#fff;">
                    <div style="margin:10px;padding:5px 0px 5px 0px;">
                        <input type="hidden" id="id" value="${productId}"/>
                        <textarea placeholder="请输入" style="margin-top:10px;" id="content">${product.content}</textarea>
                        <div style="border-bottom: 1px solid #f0f0f0;">
                            <ul class="fatie-imglist" id="images">
                                <c:choose>
                                    <c:when test="${empty productId}">
                                        <c:forEach items="${images}" var="image" varStatus="vs">
                                            <li class="image" serverId="${image.serverId}">
                                                <div style="position: absolute;margin-left: 34px;margin-top: -13px;">
                                                    <img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;padding: 8px;" class="del"/>
                                                </div>
                                                <img src="${image.localId}" width="55" height="55" />
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${product.files}" var="image" varStatus="vs">
                                            <li class="image" id="${image.id}">
                                                <div style="position: absolute;margin-left: 34px;margin-top: -13px;z-index: 1;">
                                                    <img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;padding: 8px;" class="del"/>
                                                </div>
                                                <img src="${image.fileHandleUrl}" width="55" height="55" />
                                            </li>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>

                                <li>
                                    <div class="upload-img">
                                        <img src="${pageContext.request.contextPath}/wsale/images/add-img-large.png" style="" />
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <div style="text-align: left;">
                            <div style="padding: 12px 0;" class="product-style">
                                <div style="float:right;line-height: 25px;">
                                    <input type="hidden" name="categoryId" value="${product.categoryId}">
                                    <span id="categoryName">
                                        <c:choose>
                                            <c:when test="${not empty categoryName}">${categoryName}</c:when>
                                            <c:otherwise>请选择</c:otherwise>
                                        </c:choose>
                                    </span>
                                    <img class="arrow-right right-iocn" src="${pageContext.request.contextPath}/wsale/images/fenlei-icon.png" />
                                </div>
                                <div style="font-size: 15px;">选择拍品分类</div>
                            </div>
                        </div>
                    </div>
                    <div class="bottom-text">提示：点击下一步即表示同意<span style="color:#dc721c;" onclick="javascript:location.href='http://mp.weixin.qq.com/s/7e2-PKZK8wbkdd-7v2BA9g';">《用户使用协议》</span></div>
                </div>
            </div>

        </div>


    </div><!-- /content -->


    <script type="text/javascript">
        var delFileIds = "", categoryName = '';
        $(function(){
            $(".upload-img").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    // TODO 弹出loading
                    $.loading.load({type:3,msg:'正在上传...'});
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        $('.U-msg .moreMsg').html(index + "/" + localIds.length);
                        var $li = $('<li class="image"></li>').attr({serverId:serverId});
                        $li.append('<div style="position: absolute;margin-left: 34px;margin-top: -13px;z-index: 1;"><img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;padding: 8px;" class="del" /></div>');
                        $li.append('<img src="'+localId+'" width="55" height="55"/>');
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
                var id = $image.attr("id");
                if(id) delFileIds += "," + id;
                $image.remove();
                event.stopPropagation();
                return false;
            });

            $('.right-next').bind('click', 'next', addFirst);
            $('.left-text').bind('click', 'draft', addFirst);

            $(".first-style").click(function(){
                $('.second-title .fenlei-title').html($(this).attr("categoryName"));
                drawChildCategory($(this).attr("categoryId"));
            });

            $("#childCategory").on('click', '.secondstyle-list', function(){
                var _this = $(this);
                var pid = _this.attr('pid'), categoryId = _this.attr('categoryId');
                $('input[name=categoryId]').val(categoryId);

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

        function addFirst(event) {
            var content = $.trim($('#content').val());
            if(Util.checkEmpty(content)) {
                $.toptip('请输入拍品介绍');
                $('#content').focus();
                return;
            }

            var $images = $("#images .image");
            if($images.length == 0 || $images.length > 9) {
                $.toptip('请上传1至9张拍品图片');
                return;
            }

            var categoryId = $('input[name=categoryId]').val();
            if(Util.checkEmpty(categoryId)) {
                $.toptip('请选择拍品分类');
                return false;
            }

            var mediaIds = $(".image").map(function(){
                return $(this).attr('serverId');
            }).get().join(',');
            var params = {content:content,mediaIds:mediaIds,categoryId:categoryId};
            if($("#id").val() != '') {
                params.id = $("#id").val();
                if(delFileIds != '')
                    params.delFileIds = delFileIds.substring(1);
            }
            ajaxPost('api/apiProductController/add', params, function(data){
                if(data.success) {
                    if(event.data == 'draft')
                        window.history.go(-1);
                    else
                        replace('api/apiProductController/toSecond?id=' + data.obj);
                }
            },function(){
                $.loading.load({type:1, msg:'正在发布...'});
            }, -1);
        }
    </script>
</body>

</html>
