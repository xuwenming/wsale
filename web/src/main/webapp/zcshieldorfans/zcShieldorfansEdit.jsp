<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcShieldorfans" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcShieldorfansController/edit',
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
				<input type="hidden" name="id" value = "${zcShieldorfans.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcShieldorfans.ALIAS_OBJECT_TYPE%></th>	
					<td>
											<input class="span2" name="objectType" type="text" value="${zcShieldorfans.objectType}"/>
					</td>							
					<th><%=TzcShieldorfans.ALIAS_OBJECT_BY_ID%></th>	
					<td>
											<input class="span2" name="objectById" type="text" value="${zcShieldorfans.objectById}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShieldorfans.ALIAS_OBJECT_ID%></th>	
					<td>
											<input class="span2" name="objectId" type="text" value="${zcShieldorfans.objectId}"/>
					</td>							
					<th><%=TzcShieldorfans.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcShieldorfans.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcShieldorfans.addtime}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>