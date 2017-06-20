<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcBbsReward" %>
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
					<th><%=TzcBbsReward.ALIAS_BBS_ID%></th>	
					<td>
						${zcBbsReward.bbsId}							
					</td>							
					<th><%=TzcBbsReward.ALIAS_REWARD_FEE%></th>	
					<td>
						${zcBbsReward.rewardFee}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcBbsReward.ALIAS_USER_ID%></th>	
					<td>
						${zcBbsReward.userId}							
					</td>							
					<th><%=TzcBbsReward.ALIAS_ADDTIME%></th>	
					<td>
						${zcBbsReward.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcBbsReward.ALIAS_PAY_STATUS%></th>	
					<td>
						${zcBbsReward.payStatus}							
					</td>							
				</tr>		
		</table>
	</div>
</div>