<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPraise" %>
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
					<th><%=TzcPraise.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcPraise.objectType}							
					</td>							
					<th><%=TzcPraise.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcPraise.objectId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPraise.ALIAS_USER_ID%></th>	
					<td>
						${zcPraise.userId}							
					</td>							
					<th><%=TzcPraise.ALIAS_ADDTIME%></th>	
					<td>
						${zcPraise.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>