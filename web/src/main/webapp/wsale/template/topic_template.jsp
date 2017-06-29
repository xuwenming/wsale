<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--专题列表--%>
<a id="topic_template" href="javascript:void(0);" style="height:100px;border-bottom:1px solid #ddd;display: none;padding:10px 0;">
	<div style="vertical-align:middle; text-align:center;float: left;">
		<div class="topic-list-img lazy" name="icon"></div>
	</div>
	<div style="vertical-align:middle;display:block;padding-left: 10px;float:right;width:calc(100% - 130px);height: 100px;">
		<div class="topic-list-flex">
			<div name="title" class="line-two" style="width: 85%; font-size: 14px;"></div>
			<div style="color:#aaa; line-height:1.4;padding-bottom:5px;font-size: 12px;">
				<div style="float: right;" name="time"></div>
				<div class="info-xinxi" name="name_time"></div>
				<div name="count"></div>
			</div>
		</div>
	</div>
</a>

<%--首页专题列表--%>
<a id="home_topic_template" href="javascript:void(0);" style="height:70px;border-bottom:1px solid #ddd;display: none;margin-top: 10px;">
	<div style="vertical-align:middle; text-align:center;float: left;">
		<div class="home-topic-list-img lazy" name="icon"></div>
	</div>
	<div style="display:block;vertical-align:middle;padding-left: 75px;">
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

