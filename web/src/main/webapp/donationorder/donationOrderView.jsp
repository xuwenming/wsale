<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TdonationOrder" %>
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
				<th width="10%"><%=TdonationOrder.ALIAS_ORDER_NO%></th>
				<td width="40%">
					${donationOrder.orderNo}
				</td>
				<th width="10%"><%=TdonationOrder.ALIAS_TOTAL_FEE%></th>
				<td width="40%">
					${donationOrder.totalFee}元
				</td>
			</tr>
			<tr>
				<th><%=TdonationOrder.ALIAS_NICKNAME%></th>
				<td>
					${donationOrder.nickname}
				</td>
				<th>性别</th>
				<td>
					${donationOrder.sexZh}
				</td>
			</tr>
			<tr>
				<th>用户openid</th>
				<td>
					${donationOrder.openid}
				</td>
				<th><%=TdonationOrder.ALIAS_COUNTRY%></th>
				<td>
					${donationOrder.country}
				</td>
			</tr>
			<tr>
				<th><%=TdonationOrder.ALIAS_PROVINCE%></th>
				<td>
					${donationOrder.province}
				</td>
				<th><%=TdonationOrder.ALIAS_CITY%></th>
				<td>
					${donationOrder.city}
				</td>
			</tr>
			<tr>
				<th><%=TdonationOrder.ALIAS_PAY_STATUS%></th>
				<td>
					${donationOrder.payStatusZh}
				</td>
				<th><%=TdonationOrder.ALIAS_PAYTIME%></th>
				<td>
					<fmt:formatDate value="${donationOrder.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th>下单时间</th>
				<td colspan="3">
					<fmt:formatDate value="${donationOrder.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th><%=TdonationOrder.ALIAS_HEADIMGURL%></th>
				<td colspan="3"><img alt="" src="${donationOrder.headimgurl}" width="240" height="240"> </td>
			</tr>

		</table>
	</div>
</div>