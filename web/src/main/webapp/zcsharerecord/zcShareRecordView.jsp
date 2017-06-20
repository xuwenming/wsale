<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcShareRecord" %>
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
					<th><%=TzcShareRecord.ALIAS_BBS_ID%></th>	
					<td>
						${zcShareRecord.bbsId}							
					</td>							
					<th><%=TzcShareRecord.ALIAS_SHARE_CHANNEL%></th>	
					<td>
						${zcShareRecord.shareChannel}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcShareRecord.ALIAS_USER_ID%></th>	
					<td>
						${zcShareRecord.userId}							
					</td>							
					<th><%=TzcShareRecord.ALIAS_ADDTIME%></th>	
					<td>
						${zcShareRecord.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>