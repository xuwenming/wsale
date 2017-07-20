<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--聊天列表 --%>
<div id="chat_list_template" class="qunfa-info" style="display: none;">
	<div class="left-touxiang">
		<img class="sysinfo-icon" name="friendUser" data-name="headImage" src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'" />
	</div>
	<div class="text-right grayright-text" name="lastTimeStr">
	</div>
	<div class="normal-text">
		<div name="friendUser" data-name="nickname"></div>
		<span class="infocenter-number unreadCount" name="unreadCount" style="position: inherit;"></span>
		<div class="grayright-text info-xinxi" name="lastContent"></div>
	</div>
	<div class="delete ui-link">删除</div>
</div>

<%--他人左侧的聊天消息 --%>
<div id="chat_friend_template" style="display: none;">
	<div class="sixin-datetime">
		<span class="sixin-time" name="addtime">2015-02-02 12:33</span>
	</div>
	<div class="sixin-leftcontent">
		<img class="sixin-leftimg" name="user" data-name="headImage" />
		<div class="sixin-record left-margin">
			<div class="triangle-left"></div>
			<span name="content"></span>
		</div>
	</div>
</div>

<%--自己右侧的聊天消息 --%>
<div id="chat_own_template" style="display: none;">
	<div class="sixin-datetime">
		<span class="sixin-time" name="addtime">2015-02-02 12:33</span>
	</div>
	<div class="sixin-rightcontent">
		<img class="sixin-rightimg" name="user" data-name="headImage"/>
		<div class="sixin-record right-margin">
			<div class="triangle-right"></div>
			<span name="content"></span>
		</div>
	</div>
</div>

