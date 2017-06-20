<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcWalletDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th width="14%">交易人</th>
					<td width="36%">
						${zcWalletDetail.userName}
					</td>							
					<th width="14%"><%=TzcWalletDetail.ALIAS_ORDER_NO%></th>
					<td width="36%">
						${zcWalletDetail.orderNo}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcWalletDetail.ALIAS_AMOUNT%></th>	
					<td>
						${zcWalletDetail.amount}							
					</td>							
					<th>交易渠道</th>
					<td>
						${zcWalletDetail.channelZh}
					</td>
				</tr>		
				<tr>	
					<th><%=TzcWalletDetail.ALIAS_DESCRIPTION%></th>	
					<td>
						${zcWalletDetail.description}							
					</td>
					<th><%=TzcWalletDetail.ALIAS_WTYPE%></th>
					<td>
						<c:choose>
							<c:when test="${zcWalletDetail.wtype == 'WT01' and zcWalletDetail.amount < 0}">扣款</c:when>
							<c:otherwise>${zcWalletDetail.wtypeZh}</c:otherwise>
						</c:choose>
					</td>
				</tr>		
				<tr>
					<th><%=TzcWalletDetail.ALIAS_ADDTIME%></th>	
					<td colspan="3">
						<fmt:formatDate value="${zcWalletDetail.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<c:if test="${zcWalletDetail.channel == 'CS03'}">
					<tr>
						<th>开户名</th>
						<td>
							${zcWalletDetail.bankAccount}
						</td>
						<th>手机号</th>
						<td>
							${zcWalletDetail.bankPhone}
						</td>
					</tr>
					<tr>
						<th>身份证</th>
						<td>
							${zcWalletDetail.bankIdNo}
						</td>
						<th>银行卡号</th>
						<td>
							${zcWalletDetail.bankCard}
						</td>
					</tr>
				</c:if>
				<c:if test="${zcWalletDetail.wtype == 'WT02'}">
					<tr>
						<th><%=TzcWalletDetail.ALIAS_HANDLE_STATUS%></th>
						<td>
								${zcWalletDetail.handleStatusZh}
						</td>
						<th><%=TzcWalletDetail.ALIAS_HANDLE_USER_ID%></th>
						<td>
								${zcWalletDetail.handleUserName}
						</td>
					</tr>
					<tr>
						<th><%=TzcWalletDetail.ALIAS_HANDLE_REMARK%></th>
						<td>
								${zcWalletDetail.handleRemark}
						</td>
						<th><%=TzcWalletDetail.ALIAS_HANDLE_TIME%></th>
						<td>
							<fmt:formatDate value="${zcWalletDetail.handleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:if>
		</table>
	</div>
</div>