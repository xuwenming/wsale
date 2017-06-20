<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcComment" %>
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
					<th><%=TzcComment.ALIAS_ORDER_ID%></th>	
					<td>
						${zcComment.orderId}							
					</td>							
					<th><%=TzcComment.ALIAS_PRODUCT_ID%></th>	
					<td>
						${zcComment.productId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcComment.ALIAS_GRADE%></th>	
					<td>
						${zcComment.grade}							
					</td>							
					<th><%=TzcComment.ALIAS_CONTENT%></th>	
					<td>
						${zcComment.content}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcComment.ALIAS_ADD_USER_ID%></th>	
					<td>
						${zcComment.addUserId}							
					</td>							
					<th><%=TzcComment.ALIAS_ADDTIME%></th>	
					<td>
						${zcComment.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>