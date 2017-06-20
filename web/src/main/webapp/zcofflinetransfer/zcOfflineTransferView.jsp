<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOfflineTransfer" %>
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
					<th><%=TzcOfflineTransfer.ALIAS_USER_ID%></th>	
					<td>
						${zcOfflineTransfer.userId}							
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_TRANSFER_USER_NAME%></th>	
					<td>
						${zcOfflineTransfer.transferUserName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_TRANSFER_AMOUNT%></th>	
					<td>
						${zcOfflineTransfer.transferAmount}							
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_TRANSFER_TIME%></th>	
					<td>
						${zcOfflineTransfer.transferTime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_REMARK%></th>	
					<td>
						${zcOfflineTransfer.remark}							
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_STATUS%></th>	
					<td>
						${zcOfflineTransfer.handleStatus}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_USER_ID%></th>	
					<td>
						${zcOfflineTransfer.handleUserId}							
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_REMARK%></th>	
					<td>
						${zcOfflineTransfer.handleRemark}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOfflineTransfer.ALIAS_HANDLE_TIME%></th>	
					<td>
						${zcOfflineTransfer.handleTime}							
					</td>							
					<th><%=TzcOfflineTransfer.ALIAS_ADDTIME%></th>	
					<td>
						${zcOfflineTransfer.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>