<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcTopic" %>
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
					<th><%=TzcTopic.ALIAS_TITLE%></th>	
					<td>
						${zcTopic.title}							
					</td>							
					<th><%=TzcTopic.ALIAS_ICON%></th>	
					<td>
						${zcTopic.icon}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopic.ALIAS_CONTENT%></th>	
					<td>
						${zcTopic.content}							
					</td>							
					<th><%=TzcTopic.ALIAS_TOPIC_COMMENT%></th>	
					<td>
						${zcTopic.topicComment}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopic.ALIAS_TOPIC_READ%></th>	
					<td>
						${zcTopic.topicRead}							
					</td>							
					<th><%=TzcTopic.ALIAS_TOPIC_REWARD%></th>	
					<td>
						${zcTopic.topicReward}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopic.ALIAS_TOPIC_PRAISE%></th>	
					<td>
						${zcTopic.topicPraise}							
					</td>							
					<th><%=TzcTopic.ALIAS_TOPIC_COLLECT%></th>	
					<td>
						${zcTopic.topicCollect}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopic.ALIAS_SEQ%></th>	
					<td>
						${zcTopic.seq}							
					</td>							
					<th><%=TzcTopic.ALIAS_ADD_USER_ID%></th>	
					<td>
						${zcTopic.addUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopic.ALIAS_ADDTIME%></th>	
					<td>
						${zcTopic.addtime}							
					</td>							
					<th><%=TzcTopic.ALIAS_UPDATE_USER_ID%></th>	
					<td>
						${zcTopic.updateUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopic.ALIAS_UPDATETIME%></th>	
					<td>
						${zcTopic.updatetime}							
					</td>							
					<th><%=TzcTopic.ALIAS_IS_DELETED%></th>	
					<td>
						${zcTopic.isDeleted}							
					</td>							
				</tr>		
		</table>
	</div>
</div>