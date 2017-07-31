<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--聊天列表 --%>
<div id="chat_list_template" class="qunfa-info" style="display: none;">
	<div class="left-touxiang">
		<img class="sysinfo-icon" name="friendUser" data-name="headImage" />
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

<%--系统消息 --%>
<div id="sys_msg_template" style="display: none;">
	<div class="xinpin-datetime">
		<span class="xinpin-time" name="newtime"></span>
	</div>
	<div class="info-content">
		<div class="info-title" style=" height: 50px;">
			<div style="float: left;"><img class="lazy" name="product" data-name="icon"/></div>
			<div class="normal-text line-two" name="product" data-name="content"></div>
		</div>
		<div class="sysinfo-detail">
			<div class="sysinfo-more">
				<img data-flag="down" src="${pageContext.request.contextPath}/wsale/images/down-icon.png" />
			</div>
			<div>
				<span class="msgCount" name="msgCount"></span>
				<span class="normal-text" name="msgType"></span>
			</div>
			<div class="sysMsgLogs"></div>
			<!--<div class="grayright-text sysinfo-contnet">
				<img class="sys-timeicon" src="${pageContext.request.contextPath}/wsale/images/time-icon.png" /> <span>sorry，拍卖失败！</span>
				<div>
					尊敬的李玉先生，你的拍品“和田玉”在截止时间内无人出价，你可以在右下角“客户服务”寻求帮助。
				</div>
				<div class="sysinfo-time">
					16小时前
				</div>
			</div>
			<div class="grayright-text sysinfo-contnet hide-info">
				<img class="sys-timeicon" src="${pageContext.request.contextPath}/wsale/images/time-icon.png" /> <span>拍卖一小时结束</span>
				<div>
					您好，您的拍品“和田玉”将于23:00结束，分享到微信有助于成交
				</div>
				<div class="sysinfo-time">
					16小时前
				</div>
			</div>-->
		</div>
	</div>
</div>

