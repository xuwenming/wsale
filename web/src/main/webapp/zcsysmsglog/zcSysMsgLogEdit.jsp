<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcSysMsgLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcSysMsgLogController/edit',
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
				<input type="hidden" name="id" value = "${zcSysMsgLog.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcSysMsgLog.ALIAS_SYS_MSG_ID%></th>	
					<td>
											<input class="span2" name="sysMsgId" type="text" value="${zcSysMsgLog.sysMsgId}"/>
					</td>							
					<th><%=TzcSysMsgLog.ALIAS_MTYPE%></th>	
					<td>
											<input class="span2" name="mtype" type="text" value="${zcSysMsgLog.mtype}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcSysMsgLog.ALIAS_TIME_UNIT%></th>	
					<td>
											<input class="span2" name="timeUnit" type="text" value="${zcSysMsgLog.timeUnit}"/>
					</td>							
					<th><%=TzcSysMsgLog.ALIAS_CONTENT%></th>	
					<td>
											<input class="span2" name="content" type="text" value="${zcSysMsgLog.content}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcSysMsgLog.ALIAS_IS_READ%></th>	
					<td>
											<input class="span2" name="isRead" type="text" value="${zcSysMsgLog.isRead}"/>
					</td>							
					<th><%=TzcSysMsgLog.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcSysMsgLog.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcSysMsgLog.addtime}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>