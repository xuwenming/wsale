<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcWallet" %>
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
					<th><%=TzcWallet.ALIAS_USER_ID%></th>	
					<td>
						${zcWallet.userId}							
					</td>							
					<th><%=TzcWallet.ALIAS_AMOUNT%></th>	
					<td>
						${zcWallet.amount}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcWallet.ALIAS_FROZEN_AMOUNT%></th>	
					<td>
						${zcWallet.frozenAmount}							
					</td>							
					<th><%=TzcWallet.ALIAS_PAY_PASSWORD%></th>	
					<td>
						${zcWallet.payPassword}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcWallet.ALIAS_REAL_NAME%></th>	
					<td>
						${zcWallet.realName}							
					</td>							
					<th><%=TzcWallet.ALIAS_ID_NO%></th>	
					<td>
						${zcWallet.idNo}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcWallet.ALIAS_ADDTIME%></th>	
					<td>
						${zcWallet.addtime}							
					</td>							
					<th><%=TzcWallet.ALIAS_UPDATETIME%></th>	
					<td>
						${zcWallet.updatetime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>