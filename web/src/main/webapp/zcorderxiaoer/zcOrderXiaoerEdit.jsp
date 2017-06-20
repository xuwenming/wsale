<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOrderXiaoer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcOrderXiaoerController/edit',
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
				<input type="hidden" name="id" value = "${zcOrderXiaoer.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_ORDER_ID%></th>	
					<td>
											<input class="span2" name="orderId" type="text" value="${zcOrderXiaoer.orderId}"/>
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_REASON%></th>	
					<td>
											<jb:select dataType="XR" name="reason" value="${zcOrderXiaoer.reason}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_CONTENT%></th>	
					<td>
											<input class="span2" name="content" type="text" value="${zcOrderXiaoer.content}"/>
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_STATUS%></th>	
					<td>
											<input class="span2" name="status" type="text" value="${zcOrderXiaoer.status}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_REMARK%></th>	
					<td>
											<input class="span2" name="remark" type="text" value="${zcOrderXiaoer.remark}"/>
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_ADD_USER_ID%></th>	
					<td>
											<input class="span2" name="addUserId" type="text" value="${zcOrderXiaoer.addUserId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrderXiaoer.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcOrderXiaoer.addtime}"/>
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_UPDATE_USER_ID%></th>	
					<td>
											<input class="span2" name="updateUserId" type="text" value="${zcOrderXiaoer.updateUserId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrderXiaoer.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${zcOrderXiaoer.updatetime}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>