<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--评论列表--%>
<div id="comment_template" style="border-top:10px solid #f5f5f5;padding:10px 10px 20px 10px; display: none;">
	<div style="width:20%; float: left;">
		<img class="lazy" style="width:100%;" name="user" data-name="headImage"/>
		<div style="margin-top:-20px; text-align:right;width:100%;">
			<img name="user" data-name="positionIcon" style="width:50%;">
		</div>
	</div>

	<div style="width:78%; margin-left: 21%; line-height:1.5;">
		<a style="font-size:12px;color:#888; float:right; display: none;">更多 <img src="${pageContext.request.contextPath}/wsale/images/more-icon.png" style="height:10px; vertical-align:middle;" /></a>
		<div class="reply">
			<div style="color:#ff0000;font-size:13px;" name="user" data-name="nickname">王鑫</div>
			<div style="font-size:13px; color:#000000;" name="comment">是不是真的和田玉呀，看着不像。是不是真的和田玉呀，看着不像。</div>
		</div>
		<div style="float:right;margin-right:10px;display: none;" class="del"><img src="${pageContext.request.contextPath}/wsale/images/delete-icon.png" style="width:12px;" /></div>
		<div><span style="color:#EF8326;font-size:12px;" class="bbsNums">帖子:<font name="user" data-name="bbsNums">30</font></span>&nbsp;<span style="font-size:12px; color:#aaa;" name="addtime">2016-11-24&nbsp;2楼</span></div>
	</div>
</div>

