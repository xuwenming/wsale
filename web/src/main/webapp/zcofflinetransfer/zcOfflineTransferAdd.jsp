<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOfflineTransfer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcOfflineTransferController/add',
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
					<th><%=TzcOfflineTransfer.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text"/>
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_TRANSFER_USER_NAME%></th>	
					<td>
											<input class="span2" name="transferUserName" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_TRANSFER_AMOUNT%></th>	
					<td>
											<input class="span2" name="transferAmount" type="text"/>
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_TRANSFER_TIME%></th>	
					<td>
					<input name="transferTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOfflineTransfer.FORMAT_TRANSFER_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_REMARK%></th>	
					<td>
											<input class="span2" name="remark" type="text"/>
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_STATUS%></th>	
					<td>
											<jb:select dataType="HS" name="handleStatus"></jb:select>	
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_USER_ID%></th>	
					<td>
											<input class="span2" name="handleUserId" type="text"/>
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_REMARK%></th>	
					<td>
											<input class="span2" name="handleRemark" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_TIME%></th>	
					<td>
					<input name="handleTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOfflineTransfer.FORMAT_HANDLE_TIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOfflineTransfer.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>