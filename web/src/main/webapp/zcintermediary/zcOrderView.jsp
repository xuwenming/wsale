<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('.pImageSlide img').simpleSlide();
		$('.xImageSlide img').simpleSlide();

		$('.moneyFormat').each(function(){
			$(this).text($.fenToYuan($(this).text().trim()));
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div style="font-size: 16pt; padding: 8px;">订单信息</div>
		<table class="table table-hover table-condensed">
			<tr>
				<th width="14%">订单号</th>
				<td width="36%">${order.orderNo}</td>
				<th width="14%">交易金额</th>
				<td width="36%">${order.product.hammerPrice}</td>
			</tr>
			<tr>
				<th>卖家</th>
				<td>
					${order.seller.nickname}
				</td>
				<th>卖家电话</th>
				<td>
					${order.seller.mobile}
				</td>
			</tr>
			<tr>
				<th>买家</th>
				<td>
					${order.buyer.nickname}
				</td>
				<th>买家电话</th>
				<td>
					${order.buyer.mobile}
				</td>
			</tr>
			<tr>
				<th>订单状态</th>
				<td>
					${order.orderStatusZh}
					<c:if test="${order.orderStatus == 'OS15'}">
						<span style="color: red;">(${order.orderCloseReasonZh})</span>
					</c:if>
				</td>
				<th>创建时间</th>
				<td>
					<fmt:formatDate value="${order.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<c:if test="${!empty order.faceStatus}">
				<tr>
					<th>当面交易</th>
					<td>
						<c:if test="${order.faceStatus == 'FS01'}">申请中</c:if>
						<c:if test="${order.faceStatus == 'FS02'}">卖家同意</c:if>
						<c:if test="${order.faceStatus == 'FS03'}">卖家拒绝</c:if>
					</td>
					<th>申请时间</th>
					<td>
						<fmt:formatDate value="${order.faceTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</c:if>
		</table>

		<c:if test="${!empty payOrder and payOrder.payStatus == 'PS02'}">
			<div style="font-size: 16pt; padding: 8px;">支付信息</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th>交易流水号</th>
					<td colspan="3">${payOrder.orderNo}</td>
				</tr>
				<c:if test="${!empty payOrder.refTransactionNo}">
					<tr>
						<th>第三方支付订单号</th>
						<td colspan="3">
								${payOrder.refTransactionNo}
						</td>
					</tr>
				</c:if>
				<tr>
					<th width="14%">支付渠道</th>
					<td width="36%">
							${payOrder.channelZh}
					</td>
					<th width="14%">支付时间</th>
					<td width="36%"><fmt:formatDate value="${payOrder.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<th>支付金额</th>
					<td>
							${payOrder.totalFee}
					</td>
					<th>技术服务费</th>
					<td class="moneyFormat">
							${payOrder.serviceFee}
					</td>
				</tr>
				<c:if test="${!empty payOrder.refundNo}">
					<tr>
						<th>退款单号</th>
						<td>
								${payOrder.refundNo}
						</td>
						<th>退款金额</th>
						<td class="moneyFormat">
								${payOrder.refundFee}
						</td>
					</tr>
					<tr>
						<th>退款时间</th>
						<td colspan="3">
							<fmt:formatDate value="${payOrder.refundtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:if>
			</table>
		</c:if>

		<c:if test="${order.payStatus == 'PS02' and !empty address}">
			<div style="font-size: 16pt; padding: 8px;">发货信息</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">收货人</th>
					<td width="36%">${address.userName}</td>
					<th width="14%">电话</th>
					<td width="36%">${address.telNumber}</td>
				</tr>
				<c:if test="${!empty order.expressName && !empty order.expressNo}">
					<tr>
						<th>快递公司</th>
						<td>
								${order.expressName}
						</td>
						<th>运单号</th>
						<td>
								${order.expressNo}
						</td>
					</tr>
					<tr>
						<th>发货时间</th>
						<td colspan="3">
							<fmt:formatDate value="${order.deliverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<th>收货地址</th>
					<td colspan="3">
							${address.provinceName}${address.cityName}${address.countyName}${address.detailInfo}
					</td>
				</tr>
			</table>
		</c:if>

		<c:if test="${!empty order.returnApplyReason}">
			<div style="font-size: 16pt; padding: 8px;">买家-申请退货</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">理由</th>
					<td width="36%">
							${order.returnApplyReasonZh}
						<c:if test="${order.returnApplyReason == 'RR99'}">
							- ${order.returnApplyReasonOther}
						</c:if>
					</td>
					<th width="14%">时间</th>
					<td width="36%"><fmt:formatDate value="${order.returnApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
			<c:if test="${!empty order.refuseReturnReason}">
				<div style="font-size: 16pt; padding: 8px;">卖家-拒绝退货</div>
				<table class="table table-hover table-condensed">
					<tr>
						<th width="14%">理由</th>
						<td width="36%">
								${order.refuseReturnReasonZh}
							<c:if test="${order.refuseReturnReason == 'RF99'}">
								- ${order.returnApplyReasonOther}
							</c:if>
						</td>
						<th width="14%">时间</th>
						<td width="36%"><fmt:formatDate value="${order.returnConfirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
			</c:if>
		</c:if>

		<c:if test="${backAddress != null}">
			<div style="font-size: 16pt; padding: 8px;">退货信息</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">收货人</th>
					<td width="36%">${backAddress.userName}</td>
					<th width="14%">电话</th>
					<td width="36%">${backAddress.telNumber}</td>
				</tr>
				<c:if test="${!empty order.returnExpressName && !empty order.returnExpressNo}">
					<tr>
						<th>快递公司</th>
						<td>
								${order.returnExpressName}
						</td>
						<th>运单号</th>
						<td>
								${order.returnExpressNo}
						</td>
					</tr>
					<tr>
						<th>退货发货时间</th>
						<td colspan="3">
							<fmt:formatDate value="${order.returnDeliverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<th>收货地址</th>
					<td colspan="3">
							${backAddress.provinceName}${backAddress.cityName}${backAddress.countyName}${backAddress.detailInfo}
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${order.orderStatus == 'OS10' && order.isCommented}">
			<div style="font-size: 16pt; padding: 8px;">评价信息</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">评分</th>
					<td width="36%">
						${order.comment.grade}
					</td>
					<th width="14%">评价内容</th>
					<td width="36%">${order.comment.content}</td>
				</tr>
			</table>
		</c:if>

		<c:if test="${order.isXiaoer}">
			<div style="font-size: 16pt; padding: 8px;">小二介入信息</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">身份类型</th>
					<td width="36%">
						<c:choose>
							<c:when test="${order.xiaoer.idType == 1}">买家</c:when>
							<c:otherwise>卖家</c:otherwise>
						</c:choose>
					</td>
					<th width="14%">状态</th>
					<td width="36%">${order.xiaoer.statusZh}</td>
				</tr>
				<tr>
					<th>介入原因</th>
					<td>
						${order.xiaoer.reason}
					</td>
					<th>申请时间</th>
					<td>
						<fmt:formatDate value="${order.xiaoer.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<th>简要描述</th>
					<td colspan="3">
						${order.xiaoer.content}
					</td>
				</tr>
				<c:if test="${order.xiaoer.status == 'XS02'}">
					<tr>
						<th>撤销操作人</th>
						<td>
							${order.xiaoer.updateUserName}
						</td>
						<th>撤销时间</th>
						<td>
							<fmt:formatDate value="${order.xiaoer.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${!empty order.xiaoer.files}">
					<tr>
						<th>凭证图片</th>
						<td colspan="3" class="xImageSlide">
							<c:forEach items="${order.xiaoer.files}" var="file">
								<img src="${file.fileHandleUrl}" i="${file.fileHandleUrl}" style="width: 200px; height: 150px; margin: 1px;"/>
							</c:forEach>
						</td>
					</tr>
				</c:if>
			</table>
		</c:if>
	</div>
</div>