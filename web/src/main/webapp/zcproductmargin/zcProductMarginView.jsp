<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProductMargin" %>
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
					<th><%=TzcProductMargin.ALIAS_PRODUCT_ID%></th>	
					<td>
						${zcProductMargin.productId}							
					</td>							
					<th><%=TzcProductMargin.ALIAS_BUY_USER_ID%></th>	
					<td>
						${zcProductMargin.buyUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProductMargin.ALIAS_MARGIN%></th>	
					<td>
						${zcProductMargin.margin}							
					</td>							
					<th><%=TzcProductMargin.ALIAS_RETURN_TIME%></th>	
					<td>
						${zcProductMargin.returnTime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProductMargin.ALIAS_PAY_STATUS%></th>	
					<td>
						${zcProductMargin.payStatus}							
					</td>							
					<th><%=TzcProductMargin.ALIAS_PAYTIME%></th>	
					<td>
						${zcProductMargin.paytime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProductMargin.ALIAS_ADD_USER_ID%></th>	
					<td>
						${zcProductMargin.addUserId}							
					</td>							
					<th><%=TzcProductMargin.ALIAS_ADDTIME%></th>	
					<td>
						${zcProductMargin.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcProductMargin.ALIAS_UPDATE_USER_ID%></th>	
					<td>
						${zcProductMargin.updateUserId}							
					</td>							
					<th><%=TzcProductMargin.ALIAS_UPDATETIME%></th>	
					<td>
						${zcProductMargin.updatetime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>