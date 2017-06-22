<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcTopic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcTopicController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${zcTopic.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcTopic.ALIAS_TITLE%></th>	
					<td>
											<input class="span2" name="title" type="text" value="${zcTopic.title}"/>
					</td>							
					<th><%=TzcTopic.ALIAS_ICON%></th>	
					<td>
											<input class="span2" name="icon" type="text" value="${zcTopic.icon}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopic.ALIAS_CONTENT%></th>	
					<td>
											<input class="span2" name="content" type="text" value="${zcTopic.content}"/>
					</td>							
					<th><%=TzcTopic.ALIAS_TOPIC_COMMENT%></th>	
					<td>
											<input class="span2" name="topicComment" type="text" value="${zcTopic.topicComment}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopic.ALIAS_TOPIC_READ%></th>	
					<td>
											<input class="span2" name="topicRead" type="text" value="${zcTopic.topicRead}"/>
					</td>							
					<th><%=TzcTopic.ALIAS_TOPIC_REWARD%></th>	
					<td>
											<input class="span2" name="topicReward" type="text" value="${zcTopic.topicReward}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopic.ALIAS_TOPIC_PRAISE%></th>	
					<td>
											<input class="span2" name="topicPraise" type="text" value="${zcTopic.topicPraise}"/>
					</td>							
					<th><%=TzcTopic.ALIAS_TOPIC_COLLECT%></th>	
					<td>
											<input class="span2" name="topicCollect" type="text" value="${zcTopic.topicCollect}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopic.ALIAS_SEQ%></th>	
					<td>
											<input class="span2" name="seq" type="text" value="${zcTopic.seq}"/>
					</td>							
					<th><%=TzcTopic.ALIAS_ADD_USER_ID%></th>	
					<td>
											<input class="span2" name="addUserId" type="text" value="${zcTopic.addUserId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopic.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcTopic.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcTopic.addtime}"/>
					</td>							
					<th><%=TzcTopic.ALIAS_UPDATE_USER_ID%></th>	
					<td>
											<input class="span2" name="updateUserId" type="text" value="${zcTopic.updateUserId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopic.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcTopic.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${zcTopic.updatetime}"/>
					</td>							
					<th><%=TzcTopic.ALIAS_IS_DELETED%></th>	
					<td>
											<input class="span2" name="isDeleted" type="text" value="${zcTopic.isDeleted}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>