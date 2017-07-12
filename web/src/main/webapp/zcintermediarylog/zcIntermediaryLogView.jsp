<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcIntermediaryLog" %>
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
					<th><%=TzcIntermediaryLog.ALIAS_IM_ID%></th>	
					<td>
						${zcIntermediaryLog.imId}							
					</td>							
					<th><%=TzcIntermediaryLog.ALIAS_USER_ID%></th>	
					<td>
						${zcIntermediaryLog.userId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcIntermediaryLog.ALIAS_LOG_TYPE%></th>	
					<td>
						${zcIntermediaryLog.logType}							
					</td>							
					<th><%=TzcIntermediaryLog.ALIAS_CONTENT%></th>	
					<td>
						${zcIntermediaryLog.content}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcIntermediaryLog.ALIAS_ADDTIME%></th>	
					<td>
						${zcIntermediaryLog.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>