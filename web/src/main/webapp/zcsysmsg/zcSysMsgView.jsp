<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcSysMsg" %>
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
					<th><%=TzcSysMsg.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcSysMsg.objectType}							
					</td>							
					<th><%=TzcSysMsg.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcSysMsg.objectId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcSysMsg.ALIAS_USER_ID%></th>	
					<td>
						${zcSysMsg.userId}							
					</td>							
					<th><%=TzcSysMsg.ALIAS_MTYPE%></th>	
					<td>
						${zcSysMsg.mtype}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcSysMsg.ALIAS_TIME_UNIT%></th>	
					<td>
						${zcSysMsg.timeUnit}							
					</td>							
					<th><%=TzcSysMsg.ALIAS_CONTENT%></th>	
					<td>
						${zcSysMsg.content}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcSysMsg.ALIAS_ADDTIME%></th>	
					<td>
						${zcSysMsg.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>