<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>小二介入</title>
    <jsp:include page="../inc.jsp"></jsp:include>
</head>
<body>
    <div data-role="page" data-title="小二介入" class="jqm-demos">
        <div role="main" class="ui-content jqm-content jqm-fullwidth">
            <div id="msgPopup" class="weui-popup-container popup-bottom">
                <div class="weui-popup-overlay"></div>
                <div class="weui-popup-modal" style="height: 200px;overflow: hidden; text-align: center;">
                    <div class="modal-content" style="padding-top: 0;overflow: hidden;">
                        <div id="errorMsg" style="padding: 5px 10px;"></div>
                        <a class="bottom-btn guanzhu-ok" style="color: #fff;font-size: 16px;">确定</a>
                        <a class="bottom-btn close-popup" style="color: #fff;font-size: 16px;background-color: #aaa;">取消</a>
                    </div>
                </div>
            </div>

            <div class="home-content" style="margin:0;">

                <div class="dingdan-list">
                    <a class="faxian-link">
                        <div class="normal-text" style="text-align:left;">
                            拍品信息
                        </div>
                       <!-- <div class="normal-text">
                            <img class="dianpu-icon" src="${pageContext.request.contextPath}/wsale/images/dianpu-icon.png" /> 小鱼丸瓷片 <img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
                        </div>-->
                    </a>
                    <div class="dingdan-content">
                        <div class="dingdan-img">
                            <img src="${product.icon}" />
                        </div>
                        <div class="dingdan-content-flex">
                            <div class="dingdan-title">
                                ${product.content}
                            </div>
                            <div class="dingdan-info">
                                <div>成交金额：￥<fmt:formatNumber type="number" value="${product.totalPrice}" pattern="0.00" maxFractionDigits="2"/></div>
                                <c:choose>
                                    <c:when test="${empty product.realDeadline}">
                                        <div>成交时间：<fmt:formatDate value="${product.startingTime}" pattern="MM-dd HH:mm"/></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>截拍时间：<fmt:formatDate value="${product.realDeadline}" pattern="MM-dd HH:mm"/></div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="baobiao-content">
                    <div class="reason-icon">
                        <span id="reasonText">请选择介入原因</span><img data-flag="up" src="${pageContext.request.contextPath}/wsale/images/up-icon.png" />
                    </div>
                    <div class="baobiao-title">介入原因<span style="color:#ff0000;">*</span></div>
                    <div class="reason-info">
                        <table class="reason-list">
                            <tbody>
                                <tr>
                                    <td>错发/漏发</td>
                                    <td>未收到货</td>
                                    <td>假冒品牌</td>
                                </tr>
                                <tr>
                                    <td>尺寸不符</td>
                                    <td>材质不符</td>
                                    <td>年份不符</td>
                                </tr>
                                <tr>
                                    <td>运费问题</td>
                                    <td>商品不符</td>
                                    <td>活体死亡</td>
                                </tr>
                                <tr>
                                    <td>商品破损</td>
                                    <td>未发货</td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div style="margin:0px 10px;">
                    <textarea style="margin-top:10px;" placeholder="请您写明申请小二介入理由，方便小二进行判断" id="content"></textarea>
                    <div>
                        <ul class="fatie-imglist" id="images">
                            <li>
                                <div style="border:1px solid #ccc;width:50px;height:50px; text-align:center;" class="chooseImage">
                                    <img src="${pageContext.request.contextPath}/wsale/images/add-img-large.png" style="width:30px;height:30px;margin-top:9px;" />
                                </div>
                            </li>
                        </ul>
                    </div>

                    <div>
                        <a class="bottom-btn" id="xrApplyBtn" style="color:#fff;">申请小二介入</a>
                    </div>
                </div>

            </div>


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
        var params = {
            orderId : '${orderId}',
            idType : '${idType}'
        };
        $(function(){
            $(".chooseImage").click(function(){
                JWEIXIN.chooseImage(function(localIds){
                    $.loading.load({type:3,msg:'正在上传...'});
                    // 弹出loading
                    JWEIXIN.uploadImage(localIds, function(serverId, localId, index){
                        localId = localId || localIds[index-1];
                        $('.U-msg .moreMsg').html(index + "/" + localIds.length);
                        var $li = $('<li class="image"></li>').attr({serverId:serverId});
                        $li.append('<div style="position: absolute;margin-left: 42px;margin-top: -5px;"><img src="${pageContext.request.contextPath}/wsale/images/delete.png" style="width:15px;" class="del"/></div>');
                        $li.append('<img src="'+localId+'" style="width:55px;height:55px;"/>');
                        $("#images li:last").before($li);
                        if(index == localIds.length) {
                            // 关闭loading
                            setTimeout(function(){
                                $.loading.close();
                            }, 200);
                        }
                    });
                }, 3);
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

            $('.reason-list td').click(function(){
                var reasonText = $(this).html();
                if(Util.checkEmpty(reasonText)) return;
                $('#reasonText').html(reasonText);
            });

            $('#xrApplyBtn').click(function(){
                var reasonText = $('#reasonText').html();
                if(reasonText == '请选择介入原因') {
                    $.toptip('请选择介入原因');
                    return;
                }
                params.reason = reasonText;

                var content = $("#content").val();
                if(Util.checkEmpty(content)) {
                    $("#content").focus();
                    return;
                }
                params.content = content;

                var $images = $("#images .image");
                if($images.length > 3) {
                    $.toptip('请上传最多3张凭证图片');
                    return;
                }
                var mediaIds = $(".image").map(function(){
                    return $(this).attr('serverId');
                }).get().join(',');
                params.mediaIds = mediaIds;

                $('#errorMsg').html('您确认以“'+reasonText+'”的理由，申请小二介入？');
                //$(".mask-layer,.fensi-dialog").show();
                $('#msgPopup').wePopup();
            });

            $('.guanzhu-ok').click(function(){
                ajaxPost('api/apiOrder/applyXr', params, function(data) {
                    if(data.success) {
                        window.location.replace(document.referrer);
                    } else {
                        $.alert(data.msg, "系统提示！");
                    }
                },function(){
                    $.loading.load({type:2, msg:'申请中...'});
                }, -1);
            });
        });
    </script>
</body>

</html>
