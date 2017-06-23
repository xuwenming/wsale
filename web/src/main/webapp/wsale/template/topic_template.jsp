<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--专题列表--%>
<a id="topic_template" href="javascript:void(0);" style="margin-top:10px;height:70px;border-bottom:1px solid #ddd;display: none;">
	<div style="display:inline-block; vertical-align:middle;width:20%; text-align:center;">
		<img class="lazy" style="width:90%;height:60px;" name="icon"/>
	</div>
	<div style="display:inline-block;vertical-align:middle;width:78%; float:right;">
		<div name="title" class="tiezi-title" style="width: 85%;"></div>
		<div style="font-size:12px;color:#aaa; line-height:1.4;margin-top:8px">
			<div style="float: right;" name="time"></div>
			<div class="info-xinxi" name="name_time"></div>
			<div name="count"></div>
		</div>
	</div>
</a>

<%--专题留言列表--%>
<div id="topic_comment_template" class="footer_item" style="display: none;">
	<div class="footer_img">
		<img class="lazy" name="user" data-name="headImage"/>
	</div>
	<div class="footer_content">
		<span class="footer_name" name="user" data-name="nickname"></span>
		<span class="footer_time" name="addtime"></span>
		<p name="comment"></p>
		<!--<span class="delete">删除</span>-->
	</div>
</div>

