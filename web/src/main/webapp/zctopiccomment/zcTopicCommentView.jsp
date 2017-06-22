<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcTopicComment" %>
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
					<th><%=TzcTopicComment.ALIAS_TOPIC_ID%></th>	
					<td>
						${zcTopicComment.topicId}							
					</td>							
					<th><%=TzcTopicComment.ALIAS_COMMENT%></th>	
					<td>
						${zcTopicComment.comment}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopicComment.ALIAS_CTYPE%></th>	
					<td>
						${zcTopicComment.ctype}							
					</td>							
					<th><%=TzcTopicComment.ALIAS_PID%></th>	
					<td>
						${zcTopicComment.pid}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopicComment.ALIAS_IS_DELETED%></th>	
					<td>
						${zcTopicComment.isDeleted}							
					</td>							
					<th><%=TzcTopicComment.ALIAS_USER_ID%></th>	
					<td>
						${zcTopicComment.userId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopicComment.ALIAS_ADDTIME%></th>	
					<td>
						${zcTopicComment.addtime}							
					</td>							
					<th><%=TzcTopicComment.ALIAS_AUDIT_STATUS%></th>	
					<td>
						${zcTopicComment.auditStatus}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopicComment.ALIAS_AUDIT_TIME%></th>	
					<td>
						${zcTopicComment.auditTime}							
					</td>							
					<th><%=TzcTopicComment.ALIAS_AUDIT_USER_ID%></th>	
					<td>
						${zcTopicComment.auditUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcTopicComment.ALIAS_AUDIT_REMARK%></th>	
					<td>
						${zcTopicComment.auditRemark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>