<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcReward" %>
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
					<th><%=TzcReward.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcReward.objectType}							
					</td>							
					<th><%=TzcReward.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcReward.objectId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcReward.ALIAS_REWARD_FEE%></th>	
					<td>
						${zcReward.rewardFee}							
					</td>							
					<th><%=TzcReward.ALIAS_USER_ID%></th>	
					<td>
						${zcReward.userId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcReward.ALIAS_ADDTIME%></th>	
					<td>
						${zcReward.addtime}							
					</td>							
					<th><%=TzcReward.ALIAS_PAY_STATUS%></th>	
					<td>
						${zcReward.payStatus}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcReward.ALIAS_PAYTIME%></th>	
					<td>
						${zcReward.paytime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>