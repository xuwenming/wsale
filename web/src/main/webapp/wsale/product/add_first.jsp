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
                        <div>
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
                                                <div style="position: absolute;margin-left: 34px;margin-top: -13px;">
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
                    </div>
                    <div class="bottom-text">提示：点击下一步即表示同意<span style="color:#dc721c;" onclick="javascript:location.href='http://mp.weixin.qq.com/s/7e2-PKZK8wbkdd-7v2BA9g';">《用户使用协议》</span></div>
                </div>
            </div>

        </div>


    </div><!-- /content -->


    <script type="text/javascript">
        var delFileIds = "";
        $(function(){
            $(".upload-img").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    // TODO 弹出loading
                    $.loading.load({type:3,msg:'正在上传...'});
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        $('.U-msg .moreMsg').html(index + "/" + localIds.length);
                        var $li = $('<li class="image"></li>').attr({serverId:serverId});
                        $li.append('<div style="position: absolute;margin-left: 34px;margin-top: -13px;"><img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;padding: 8px;" class="del" /></div>');
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
        });

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

            var mediaIds = $(".image").map(function(){
                return $(this).attr('serverId');
            }).get().join(',');
            var params = {content:content,mediaIds:mediaIds};
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
