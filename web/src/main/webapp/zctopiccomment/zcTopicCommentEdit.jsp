<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcTopicComment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcTopicCommentController/edit',
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
				<input type="hidden" name="id" value = "${zcTopicComment.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcTopicComment.ALIAS_TOPIC_ID%></th>	
					<td>
											<input class="span2" name="topicId" type="text" value="${zcTopicComment.topicId}"/>
					</td>							
					<th><%=TzcTopicComment.ALIAS_COMMENT%></th>	
					<td>
											<input class="span2" name="comment" type="text" value="${zcTopicComment.comment}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopicComment.ALIAS_CTYPE%></th>	
					<td>
											<input class="span2" name="ctype" type="text" value="${zcTopicComment.ctype}"/>
					</td>							
					<th><%=TzcTopicComment.ALIAS_PID%></th>	
					<td>
											<input class="span2" name="pid" type="text" value="${zcTopicComment.pid}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopicComment.ALIAS_IS_DELETED%></th>	
					<td>
											<input class="span2" name="isDeleted" type="text" value="${zcTopicComment.isDeleted}"/>
					</td>							
					<th><%=TzcTopicComment.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text" value="${zcTopicComment.userId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopicComment.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcTopicComment.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcTopicComment.addtime}"/>
					</td>							
					<th><%=TzcTopicComment.ALIAS_AUDIT_STATUS%></th>	
					<td>
											<jb:select dataType="AS" name="auditStatus" value="${zcTopicComment.auditStatus}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopicComment.ALIAS_AUDIT_TIME%></th>	
					<td>
					<input class="span2" name="auditTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcTopicComment.FORMAT_AUDIT_TIME%>'})"   maxlength="0" value="${zcTopicComment.auditTime}"/>
					</td>							
					<th><%=TzcTopicComment.ALIAS_AUDIT_USER_ID%></th>	
					<td>
											<input class="span2" name="auditUserId" type="text" value="${zcTopicComment.auditUserId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcTopicComment.ALIAS_AUDIT_REMARK%></th>	
					<td>
											<input class="span2" name="auditRemark" type="text" value="${zcTopicComment.auditRemark}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>