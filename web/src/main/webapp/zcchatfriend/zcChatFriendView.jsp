<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcChatFriend" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcChatFriend.ALIAS_USER_ID%></th>	
					<td>
						${zcChatFriend.userId}							
					</td>							
					<th><%=TzcChatFriend.ALIAS_FRIEND_USER_ID%></th>	
					<td>
						${zcChatFriend.friendUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcChatFriend.ALIAS_IS_DELETED%></th>	
					<td>
						${zcChatFriend.isDeleted}							
					</td>							
					<th><%=TzcChatFriend.ALIAS_LAST_CONTENT%></th>	
					<td>
						${zcChatFriend.lastContent}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcChatFriend.ALIAS_LAST_TIME%></th>	
					<td>
						${zcChatFriend.lastTime}							
					</td>							
					<th><%=TzcChatFriend.ALIAS_ADDTIME%></th>	
					<td>
						${zcChatFriend.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>