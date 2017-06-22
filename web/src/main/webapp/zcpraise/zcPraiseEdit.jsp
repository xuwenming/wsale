<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPraise" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcPraiseController/edit',
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
				<input type="hidden" name="id" value = "${zcPraise.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcPraise.ALIAS_OBJECT_TYPE%></th>	
					<td>
											<input class="span2" name="objectType" type="text" value="${zcPraise.objectType}"/>
					</td>							
					<th><%=TzcPraise.ALIAS_OBJECT_ID%></th>	
					<td>
											<input class="span2" name="objectId" type="text" value="${zcPraise.objectId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcPraise.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text" value="${zcPraise.userId}"/>
					</td>							
					<th><%=TzcPraise.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcPraise.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcPraise.addtime}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>