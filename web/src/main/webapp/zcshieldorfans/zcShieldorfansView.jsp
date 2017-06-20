<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcShieldorfans" %>
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
					<th><%=TzcShieldorfans.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcShieldorfans.objectType}							
					</td>							
					<th><%=TzcShieldorfans.ALIAS_OBJECT_BY_ID%></th>	
					<td>
						${zcShieldorfans.objectById}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcShieldorfans.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcShieldorfans.objectId}							
					</td>							
					<th><%=TzcShieldorfans.ALIAS_ADDTIME%></th>	
					<td>
						${zcShieldorfans.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>