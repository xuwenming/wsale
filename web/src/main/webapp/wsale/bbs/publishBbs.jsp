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
            <div class="home-content">
                <div style="margin:0 10px;">
                    <input type="hidden" id="categoryId" value="${categoryId}"/>
                    <input type="hidden" id="bbsType" value="${bbsType}"/>
                    <input type="text" placeholder="请输入标题" id="bbsTitle" />
                    <textarea style="margin-top:20px;" placeholder="请输入帖子内容" id="bbsContent"></textarea>
                    <div>
                        <ul class="fatie-imglist" id="images">
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
                <div style="position:absolute;margin:20px 10px; font-size:12px; color:#aaa;">提示：点击发布即表示同意<span style="color:#dc721c;" onclick="javascript:location.href='http://mp.weixin.qq.com/s/7e2-PKZK8wbkdd-7v2BA9g';">《用户使用协议》</span></div>
                <div>
                    <a id="publish" type="button" style="color:#fff;font-size:14px;display:block;height:40px; line-height:40px; background-color:#dc721c; position:absolute;bottom:20px;left:20px;right:20px;">发布</a>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
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

            var mediaIds = $(".image").map(function(){
                return $(this).attr('serverId');
            }).get().join(',');
            var params = {categoryId : $("#categoryId").val(), bbsType:$("#bbsType").val()};
            params.bbsTitle = bbsTitle;
            params.bbsContent = bbsContent;
            params.mediaIds = mediaIds;
            ajaxPost('api/bbsController/publishBbs', params, function(data){
                if(data.success) {
                    replace('api/bbsController/bbsDetail?backCustom=true&id=' + data.obj);
                } else {
                    $.alert(data.msg, "系统提示！");
                    $("#publish").one('click', publish);
                    $.loading.close();
                }
            },function(){
                $.loading.load({type:1, msg:'正在发布...'});
            }, -1);
        }
    </script>
</body>

</html>
