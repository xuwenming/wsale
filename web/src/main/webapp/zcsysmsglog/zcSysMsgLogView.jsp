<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcSysMsgLog" %>
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
					<th><%=TzcSysMsgLog.ALIAS_SYS_MSG_ID%></th>	
					<td>
						${zcSysMsgLog.sysMsgId}							
					</td>							
					<th><%=TzcSysMsgLog.ALIAS_MTYPE%></th>	
					<td>
						${zcSysMsgLog.mtype}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcSysMsgLog.ALIAS_TIME_UNIT%></th>	
					<td>
						${zcSysMsgLog.timeUnit}							
					</td>							
					<th><%=TzcSysMsgLog.ALIAS_CONTENT%></th>	
					<td>
						${zcSysMsgLog.content}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcSysMsgLog.ALIAS_IS_READ%></th>	
					<td>
						${zcSysMsgLog.isRead}							
					</td>							
					<th><%=TzcSysMsgLog.ALIAS_ADDTIME%></th>	
					<td>
						${zcSysMsgLog.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>