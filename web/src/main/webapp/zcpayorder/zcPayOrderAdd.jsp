<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPayOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcPayOrderController/add',
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
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcPayOrder.ALIAS_ORDER_NO%></th>	
					<td>
											<input class="span2" name="orderNo" type="text"/>
					</td>							
					<th><%=TzcPayOrder.ALIAS_OBJECT_TYPE%></th>	
					<td>
											<jb:select dataType="PO" name="objectType"></jb:select>	
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPayOrder.ALIAS_OBJECT_ID%></th>	
					<td>
											<input class="span2" name="objectId" type="text"/>
					</td>							
					<th><%=TzcPayOrder.ALIAS_CHANNEL%></th>	
					<td>
											<jb:select dataType="CS" name="channel"></jb:select>	
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPayOrder.ALIAS_TOTAL_FEE%></th>	
					<td>
											<input class="span2" name="totalFee" type="text"/>
					</td>							
					<th><%=TzcPayOrder.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPayOrder.ALIAS_PAY_STATUS%></th>	
					<td>
											<jb:select dataType="PS" name="payStatus"></jb:select>	
					</td>							
					<th><%=TzcPayOrder.ALIAS_PAYTIME%></th>	
					<td>
											<input class="span2" name="paytime" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPayOrder.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcPayOrder.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>