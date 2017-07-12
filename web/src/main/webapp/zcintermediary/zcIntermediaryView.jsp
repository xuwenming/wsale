<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcIntermediary" %>
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
					<th><%=TzcIntermediary.ALIAS_IM_NO%></th>	
					<td>
						${zcIntermediary.imNo}							
					</td>							
					<th><%=TzcIntermediary.ALIAS_BBS_ID%></th>	
					<td>
						${zcIntermediary.bbsId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcIntermediary.ALIAS_SELL_USER_ID%></th>	
					<td>
						${zcIntermediary.sellUserId}							
					</td>							
					<th><%=TzcIntermediary.ALIAS_USER_ID%></th>	
					<td>
						${zcIntermediary.userId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcIntermediary.ALIAS_AMOUNT%></th>	
					<td>
						${zcIntermediary.amount}							
					</td>							
					<th><%=TzcIntermediary.ALIAS_REMARK%></th>	
					<td>
						${zcIntermediary.remark}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcIntermediary.ALIAS_STATUS%></th>	
					<td>
						${zcIntermediary.status}							
					</td>							
					<th><%=TzcIntermediary.ALIAS_ADDTIME%></th>	
					<td>
						${zcIntermediary.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>