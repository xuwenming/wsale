<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPayOrder" %>
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
					<th><%=TzcPayOrder.ALIAS_ORDER_NO%></th>	
					<td>
						${zcPayOrder.orderNo}							
					</td>							
					<th><%=TzcPayOrder.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcPayOrder.objectType}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPayOrder.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcPayOrder.objectId}							
					</td>							
					<th><%=TzcPayOrder.ALIAS_CHANNEL%></th>	
					<td>
						${zcPayOrder.channel}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPayOrder.ALIAS_TOTAL_FEE%></th>	
					<td>
						${zcPayOrder.totalFee}							
					</td>							
					<th><%=TzcPayOrder.ALIAS_USER_ID%></th>	
					<td>
						${zcPayOrder.userId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPayOrder.ALIAS_PAY_STATUS%></th>	
					<td>
						${zcPayOrder.payStatus}							
					</td>							
					<th><%=TzcPayOrder.ALIAS_PAYTIME%></th>	
					<td>
						${zcPayOrder.paytime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPayOrder.ALIAS_ADDTIME%></th>	
					<td>
						${zcPayOrder.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>