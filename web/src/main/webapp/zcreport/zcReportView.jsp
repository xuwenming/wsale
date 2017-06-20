<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcReport" %>
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
					<th><%=TzcReport.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcReport.objectType}							
					</td>							
					<th><%=TzcReport.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcReport.objectId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcReport.ALIAS_REPORT_REASON%></th>	
					<td>
						${zcReport.reportReason}							
					</td>							
					<th><%=TzcReport.ALIAS_USER_ID%></th>	
					<td>
						${zcReport.userId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcReport.ALIAS_ADDTIME%></th>	
					<td>
						${zcReport.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>