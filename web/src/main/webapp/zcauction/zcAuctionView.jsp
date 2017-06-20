<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcAuction" %>
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
					<th><%=TzcAuction.ALIAS_PRODUCT_ID%></th>	
					<td>
						${zcAuction.productId}							
					</td>							
					<th><%=TzcAuction.ALIAS_BUYER_ID%></th>	
					<td>
						${zcAuction.buyerId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAuction.ALIAS_BID%></th>	
					<td>
						${zcAuction.bid}							
					</td>							
					<th><%=TzcAuction.ALIAS_STATUS%></th>	
					<td>
						${zcAuction.status}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAuction.ALIAS_IS_AUTO%></th>	
					<td>
						${zcAuction.isAuto}							
					</td>							
					<th><%=TzcAuction.ALIAS_ADD_USER_ID%></th>	
					<td>
						${zcAuction.addUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAuction.ALIAS_ADDTIME%></th>	
					<td>
						${zcAuction.addtime}							
					</td>							
					<th><%=TzcAuction.ALIAS_UPDATE_USER_ID%></th>	
					<td>
						${zcAuction.updateUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAuction.ALIAS_UPDATETIME%></th>	
					<td>
						${zcAuction.updatetime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>