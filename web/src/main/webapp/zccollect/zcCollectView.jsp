<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcCollect" %>
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
					<th><%=TzcCollect.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcCollect.objectType}							
					</td>							
					<th><%=TzcCollect.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcCollect.objectId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcCollect.ALIAS_USER_ID%></th>	
					<td>
						${zcCollect.userId}							
					</td>							
					<th><%=TzcCollect.ALIAS_ADDTIME%></th>	
					<td>
						${zcCollect.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>