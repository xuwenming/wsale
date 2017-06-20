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
                <div style="position:absolute;margin:10px; font-size:12px; color:#aaa;">提示：点击发布即表示同意<span style="color:#dc721c;">《竞拍服务协议》</span></div>
                <div style="background-color:#f0f0f0; position:absolute;bottom:80px;left:0;right:0; line-height:1.5;">
                    <img src="${pageContext.request.contextPath}/wsale/images/luyin-icon.png" style="width:25%;" class="startBtn"/>
                    <div style="font-size:14px;" class="stopBtn">开始录音</div>
                    <div style="font-size:12px;color:#bbb;">最多30分钟 <t class="timed"></t></div>
                </div>
                <div>
                    <a id="publish" type="button" style="color:#fff;font-size:14px;display:block;height:40px; line-height:40px; background-color:#dc721c; position:absolute;bottom:20px;left:20px;right:20px;">发布</a>
                </div>
            </div>


        </div><!-- /content -->


    </div><!-- /page -->

    <script type="text/javascript">
        var recordFlag = false;
        var voiceLocalIds = [];
        var voicePaths = '', totalLen = 0, timed = 0;
        var timeInterval;
        $(function(){
            $("#publish").one('click', publish);

            $(".chooseImage").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:3,msg:'正在上传...'});
                    // TODO 弹出loading
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
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

            $('.startBtn').click(function(){
                if(!recordFlag) {
                    if(totalLen >= 30*60) {
                        $.toptip('您已超出30分钟，本次将禁止录音！');
                        return;
                    }
                    JWEIXIN.startRecord(function(){
                        recordFlag = true;
                        timeInterval = setInterval(timedFun, 1000);
                        $('.stopBtn').html('停止录音');
                    });
                } else {
                    JWEIXIN.stopRecord(function(localId){
                        recordFlag = false;
                        clearInterval(timeInterval);
                        $('.stopBtn').html('开始录音');
                        uploadVoice(localId);
                    });
                }
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

        function timedFun() {
            if(timed == 30*60) {
                recordFlag = true;
                $('.startBtn').click();
                $.toptip('您已录满30分钟，本次将不再录音！');
            }
            timed ++;
            var recorded;
            if(timed < 60)
                recorded = '00:' + (timed < 10 ? '0' + timed : timed);
            else {
                var m = parseInt(timed/60), s = timed%60;
                recorded = (m < 10 ? '0' + m : m) + ':' + (s < 10 ? '0' + s : s);
            }
            $('.timed').html('已录制' + recorded);
        }

        wx.ready(function () {
            JWEIXIN.onVoiceRecordEnd(function(localId){
                clearInterval(timeInterval);
                if(totalLen < 30*60) {
                    recordFlag = false;
                    $('.startBtn').click();
                    uploadVoice(localId);
                } else {
                    $.toptip('您已超出30分钟，本次将不再录音！');
                }
            });
        });

        function uploadVoice(localId) {
            voiceLocalIds.push(localId);
            JWEIXIN.uploadVoice(localId, function(serverId){
                ajaxPost('api/bbsController/uploadVoice', {mediaId:serverId}, function(data){
                    if(data.success) {
                        if(voicePaths != '') voicePaths += ',';
                        voicePaths += data.obj.timestamp + "||" + data.obj.path + "||" + data.obj.duration;

                        totalLen += data.obj.duration;
                        Util.arrayRemove(voiceLocalIds, localId);
                    }
                });
            });
        }

        var publishInterval;
        function publish() {
            if(recordFlag) {
                $.toptip('正在录音中');
                $("#publish").one('click', publish);
                return;
            }
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

            if(voiceLocalIds.length == 0 && voicePaths == '') {
                $.toptip('请您先录制语音文件！');
                $("#publish").one('click', publish);
                return;
            }

            $.loading.load({type:1, msg:'正在发布...'});
            var mediaIds = $(".image").map(function(){
                return $(this).attr('serverId');
            }).get().join(',');
            var params = {categoryId : $("#categoryId").val(), bbsType:$("#bbsType").val()};
            params.bbsTitle = bbsTitle;
            params.bbsContent = bbsContent;
            params.mediaIds = mediaIds; // TODO 调用微信JS-SDK上传图片
            publishInterval = setInterval(function(){
                if(voiceLocalIds.length == 0) {
                    clearInterval(publishInterval);
                    params.voicePaths = voicePaths;
                    ajaxPost('api/bbsController/publishBbs', params, function(data){
                        if(data.success) {
                            replace('api/bbsController/bbsDetail?backCustom=true&id=' + data.obj);
                        }
                    });
                }
            }, 500);

        }
    </script>
</body>

</html>
