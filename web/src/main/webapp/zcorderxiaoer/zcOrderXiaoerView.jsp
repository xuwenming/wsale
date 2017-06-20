<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOrderXiaoer" %>
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
					<th><%=TzcOrderXiaoer.ALIAS_ORDER_ID%></th>	
					<td>
						${zcOrderXiaoer.orderId}							
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_REASON%></th>	
					<td>
						${zcOrderXiaoer.reason}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_CONTENT%></th>	
					<td>
						${zcOrderXiaoer.content}							
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_STATUS%></th>	
					<td>
						${zcOrderXiaoer.status}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_REMARK%></th>	
					<td>
						${zcOrderXiaoer.remark}							
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_ADD_USER_ID%></th>	
					<td>
						${zcOrderXiaoer.addUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_ADDTIME%></th>	
					<td>
						${zcOrderXiaoer.addtime}							
					</td>							
					<th><%=TzcOrderXiaoer.ALIAS_UPDATE_USER_ID%></th>	
					<td>
						${zcOrderXiaoer.updateUserId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcOrderXiaoer.ALIAS_UPDATETIME%></th>	
					<td>
						${zcOrderXiaoer.updatetime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>