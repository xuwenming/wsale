<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--评论列表--%>
<div id="comment_template" style="border-top:10px solid #f5f5f5;clear: both;padding:10px 10px 0px 10px;display: none;">
	<div class="bbs-com-img">
		<img class="lazy" src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'" style="width:62px;height: 62px;" name="user" data-name="headImage"/>
		<div style="margin-top:-20px; text-align:right;width:100%;">
			<img name="user" data-name="positionIcon" style="width:50%;">
		</div>
	</div>

	<div class="bbs-detail-com-list">
		<!--<a style="font-size:12px;color:#888; float:right; display: none;">更多 <img src="${pageContext.request.contextPath}/wsale/images/more-icon.png" style="height:10px; vertical-align:middle;" /></a>-->
		<div class="reply">
			<div style="color:#ff0000;font-size:13px;" name="user" data-name="nickname"></div>
			<div class="comment-template-at attBtn">+ 关注</div>
		</div>
		<div class="reply" style="font-size:13px; color:#000000;" name="comment"></div>
		<div class="comment-template-info"><span style="color:#EF8326;font-size:12px;" class="bbsNums">帖子:<font name="user" data-name="bbsNums"></font></span>&nbsp;<span style="font-size:12px; color:#aaa;" name="addtime"></span>
			<div style="float:right;margin-right:10px;display: none;" class="del"><img src="${pageContext.request.contextPath}/wsale/images/delete-icon.png" style="width:12px;" /></div>
		</div>
		<%--<div style="font-size:13px; color:#000000;" name="comment">是不是真的和田玉呀，看着不像。是不是真的和田玉呀，看着不像。</div>--%>
	</div>
</div>

<%--我的评论列表--%>
<li id="my_comment_template" style="display: none;">
	<div class="speak-li-left">
		<img class="lazy" name="headImage" src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'" />
	</div>
	<div class="speak-li-right">
		<div class="speak-content">
			<span class="speak-user" name="nickname"></span>
			<div class="new-speak" name="comment"></div>
			<div class="new-speak-time" name="addtime"></div>
		</div>
		<div class="bg-img lazy" name="bbs" data-name="icon"></div>
	</div>
	<div style="clear: both"></div>
</li>

