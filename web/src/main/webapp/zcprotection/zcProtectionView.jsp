<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProtection" %>
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
					<th><%=TzcProtection.ALIAS_USER_ID%></th>	
					<td>
						${zcProtection.userId}							
					</td>							
					<th><%=TzcProtection.ALIAS_PROTECTION_TYPE%></th>	
					<td>
						${zcProtection.protectionType}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProtection.ALIAS_PRICE%></th>	
					<td>
						${zcProtection.price}							
					</td>							
					<th><%=TzcProtection.ALIAS_REASON%></th>	
					<td>
						${zcProtection.reason}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProtection.ALIAS_PAY_STATUS%></th>	
					<td>
						${zcProtection.payStatus}							
					</td>							
					<th><%=TzcProtection.ALIAS_PAYTIME%></th>	
					<td>
						${zcProtection.paytime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProtection.ALIAS_ADD_USER_ID%></th>	
					<td>
						${zcProtection.addUserId}							
					</td>							
					<th><%=TzcProtection.ALIAS_ADDTIME%></th>	
					<td>
						${zcProtection.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProtection.ALIAS_UPDATE_USER_ID%></th>	
					<td>
						${zcProtection.updateUserId}							
					</td>							
					<th><%=TzcProtection.ALIAS_UPDATETIME%></th>	
					<td>
						${zcProtection.updatetime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>