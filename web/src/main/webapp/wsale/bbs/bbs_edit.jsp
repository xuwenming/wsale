<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>编辑</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="编辑" class="jqm-demos" style="background-color:#f0f0f0;">
        <div role="main" class="ui-content jqm-content jqm-fullwidth" style="background-color:#fff;">
            <div class="home-content">
                <div style="margin:0 10px;">
                    <input type="hidden" id="id" value="${bbs.id}"/>
                    <input type="text" placeholder="请输入标题" id="bbsTitle" value="${bbs.bbsTitle}"/>
                    <textarea style="margin-top:20px;" placeholder="请输入帖子内容" id="bbsContent">${bbs.bbsContent}</textarea>
                    <div>
                        <ul class="fatie-imglist" id="images">
                            <c:forEach items="${bbs.files}" var="file" varStatus="vs">
                                <li class="image" id="${file.id}">
                                    <div style="position: absolute;margin-left: 34px;margin-top: -13px;">
                                        <img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;padding: 8px;" class="del"/>
                                    </div>
                                    <img src="${file.fileHandleUrl}" width="55" height="55" />
                                </li>
                            </c:forEach>
                            <li>
                                <div style="border:1px solid #ccc;width:50px;height:50px; text-align:center;" class="chooseImage">
                                    <img src="${pageContext.request.contextPath}/wsale/images/add-img-large.png" style="width:30px;height:30px;margin-top:9px;" />
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div style="position:absolute;margin:10px; font-size:12px; color:#aaa;">提示：点击发布即表示同意<span style="color:#dc721c;">《竞拍服务协议》</span></div>
                <div>
                    <a id="publish" type="button" style="color:#fff;font-size:14px;display:block;height:40px; line-height:40px; background-color:#dc721c; position:absolute;bottom:20px;left:20px;right:20px;">发布</a>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        var delFileIds = "";
        $(function(){
            $("#publish").one('click', publish);

            $(".chooseImage").click(function(){
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
        });

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

            var $images = $("#images .image");
            if($images.length == 0 || $images.length > 9) {
                $.toptip('请上传1至9张图片');
                $("#publish").one('click', publish);
                return;
            }

            var mediaIds = $("li.image[serverId]").map(function(){
                return $(this).attr('serverId');
            }).get().join(',');
            var params = {id : $("#id").val()};
            params.bbsTitle = bbsTitle;
            params.bbsContent = bbsContent;
            params.mediaIds = mediaIds;
            if(delFileIds != '')
                params.delFileIds = delFileIds.substring(1);

            params.isEdit = true; // 编辑标记，区别加亮加精等
            ajaxPost('api/bbsController/editBbs', params, function(data){
                if(data.success) {
                    replace('api/bbsController/bbsDetail?backCustom=true&id=' + $("#id").val());
                }
            },function(){
                $.loading.load({type:1, msg:'正在发布...'});
            });
        }
    </script>
</body>

</html>
