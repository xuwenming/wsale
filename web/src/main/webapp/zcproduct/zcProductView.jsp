<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProduct" %>
<%@ page import="jb.model.TzcAuction" %>
<%@ page import="jb.model.TzcUserAutoPrice" %>
<%@ page import="jb.model.TzcProductLike" %>
<%@ page import="jb.model.TzcProductMargin" %>
<%@ page import="jb.model.TzcProductRange" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setAttribute("vEnter", "\n");
%>
<!DOCTYPE html>
<html>
<head>
	<title>ZcForumBbs管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		parent.imageSlide.initImageSlide($('.imageSlide img'));
		$('.imageSlide img').click(function(){
			parent.imageSlide.showImageSlide($(this).index());
		});
		var gridMap = {
			handle:function(obj,clallback){
				if (obj.grid == null) {
					obj.grid = clallback();
				} else {
					obj.grid.datagrid('reload');
				}
			},
			0: {
				invoke: function () {
					parent.imageSlide.initImageSlide($('.imageSlide img'));
					$('.imageSlide img').click(function(){
						parent.imageSlide.showImageSlide($(this).index());
					});
				}, grid: null
			}, 1: {
				invoke: function () {
					gridMap.handle(this,loadAuctionDataGrid);
				}, grid: null
			}, 2: {
				invoke: function () {
					gridMap.handle(this,loadAutoAuctionDataGrid);
				}, grid: null
			}, 3: {
				invoke: function () {
					gridMap.handle(this,loadRangeDataGrid);
				}, grid: null
			}, 4: {
				invoke: function () {
					gridMap.handle(this,loadLikeDataGrid);
				}, grid: null
			}, 5: {
				invoke: function () {
					gridMap.handle(this,loadMarginDataGrid);
				}, grid: null
			}
		};
		$('#product_view_tabs').tabs({
			onSelect: function (title, index) {
				gridMap[index].invoke();
			}
		});
	});

	function loadAuctionDataGrid() {
		return $('#auctionDataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcAuctionController/dataGridByProduct?productId=${zcProduct.id}',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'bid',
			sortOrder : 'desc, t.addtime desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'addtime',
				title : '<%=TzcAuction.ALIAS_ADDTIME%>',
				width : 50,
				sortable : true
			}, {
				field : 'buyerName',
				title : '<%=TzcAuction.ALIAS_BUYER_ID%>',
				width : 50
			}, {
				field : 'bid',
				title : '<%=TzcAuction.ALIAS_BID%>',
				width : 50,
				sortable : true
			}, {
				field : 'isAuto',
				title : '<%=TzcAuction.ALIAS_IS_AUTO%>',
				width : 50,
				formatter : function(value, row, index) {
					if(row.isAuto) return "是";
					else return "否";
				}
			} ] ]
		});
	}
	function loadAutoAuctionDataGrid() {
		return $('#autoAuctionDataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcUserAutoPriceController/dataGridByProduct?productId=${zcProduct.id}',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'addtime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'addtime',
				title : '设置时间',
				width : 50,
				sortable : true
			}, {
				field : 'userName',
				title : '设置用户',
				width : 50
			}, {
				field : 'maxPrice',
				title : '设置最高价',
				width : 50,
				sortable : true

			} ] ]
		});
	}

	function loadRangeDataGrid() {
		return $('#rangeDataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcProductRangeController/dataGridByProduct?productId=${zcProduct.id}',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'startPrice',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'startPrice',
				title : '<%=TzcProductRange.ALIAS_START_PRICE%>',
				width : 50
			}, {
				field : 'endPrice',
				title : '<%=TzcProductRange.ALIAS_END_PRICE%>',
				width : 50
			}, {
				field : 'price',
				title : '<%=TzcProductRange.ALIAS_PRICE%>',
				width : 50
			} ] ]
		});
	}
	function loadLikeDataGrid() {
		return $('#likeDataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcProductLikeController/dataGridByProduct?productId=${zcProduct.id}&payStatus=PS02',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'addtime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'addtime',
				title : '<%=TzcProductLike.ALIAS_ADDTIME%>',
				width : 50,
				sortable : true
			}, {
				field : 'userName',
				title : '<%=TzcProductLike.ALIAS_USER_ID%>',
				width : 50
			} ] ]
		});
	}
	function loadMarginDataGrid() {
		return $('#marginDataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcProductMarginController/dataGridByProduct?productId=${zcProduct.id}',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'paytime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'paytime',
				title : '<%=TzcProductMargin.ALIAS_PAYTIME%>',
				width : 50,
				sortable : true
			}, {
				field : 'buyUserName',
				title : '<%=TzcProductMargin.ALIAS_BUY_USER_ID%>',
				width : 50
			}, {
				field : 'margin',
				title : '金额',
				width : 50
			}, {
				field : 'refundNo',
				title : '<%=TzcProductMargin.ALIAS_REFUND_NO%>',
				width : 50
			}, {
				field : 'returnTime',
				title : '<%=TzcProductMargin.ALIAS_REFUND_TIME%>',
				width : 50
			} ] ]
		});
	}

	function viewOrder() {
		parent.$.modalDialog({
			title : '订单详情',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcOrderController/viewByProduct?productId=${zcProduct.id}'
		});
	}
</script>
</head>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;">
			<div style="font-size: 12pt; padding: 8px;">拍品编号：${zcProduct.pno}</div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="product_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
				<div title="基本信息">
					<table class="table table-hover table-condensed">
						<tr>
							<th width="14%">拍品编号</th>
							<td width="36%">${zcProduct.pno}</td>
							<th width="14%">所属分类</th>
							<td width="36%">${zcProduct.cname}</td>
						</tr>

						<tr>
							<th>开拍时间</th>
							<td>
								<fmt:formatDate value="${zcProduct.startingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<th>截拍时间</th>
							<td>
								<fmt:formatDate value="${zcProduct.realDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr>
							<th>包退</th>
							<td>
								${zcProduct.approvalDaysZh}
							</td>
							<th>是否包邮</th>
							<td>
								${zcProduct.isFreeShippingZh}
							</td>
						</tr>
						<tr>
							<th>起拍价</th>
							<td>
								${zcProduct.startingPrice}
							</td>
							<th>当前价</th>
							<td>
								${zcProduct.currentPrice}
							</td>
						</tr>
						<tr>
							<th>一口价</th>
							<td>
								${zcProduct.fixedPrice}
							</td>
							<th>参考价</th>
							<td>
								${zcProduct.referencePrice}
							</td>
						</tr>
						<tr>
							<th>出价保证金</th>
							<td colspan="3">
								${zcProduct.margin}
							</td>
						</tr>
						<tr>
							<th>拍品状态</th>
							<td>
								${zcProduct.statusZh}
								<c:if test="${!empty zcProduct.userId}">
									<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="viewOrder();">查看订单</a>
								</c:if>
							</td>
							<th>封存状态</th>
							<td>
								<c:choose>
									<c:when test="${zcProduct.isClose}">已封存</c:when>
									<c:otherwise>未封存</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>成交人</th>
							<td>
								${zcProduct.userName}
							</td>
							<th>成交金额</th>
							<td>
								${zcProduct.hammerPrice}
							</td>
						</tr>
						<tr>
							<th>成交时间</th>
							<td colspan="3">
								<fmt:formatDate value="${zcProduct.hammerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr>
							<th>发布人</th>
							<td>
								${zcProduct.addUserName}
							</td>
							<th>发布时间</th>
							<td>
								<fmt:formatDate value="${zcProduct.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr>
							<th valign="top">拍品内容</th>
							<td colspan="3">
								${fn:replace(zcProduct.content, vEnter, '<br>')}
							</td>
						</tr>
						<tr>
							<th>拍品图片</th>
							<td colspan="3" class="imageSlide">
								<c:forEach items="${zcProduct.files}" var="file">
									<img src="${file.fileHandleUrl}" i="${file.fileHandleUrl}" style="width: 200px; height: 150px; margin: 1px;"/>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div title="出价记录">
					<table id="auctionDataGrid"></table>
				</div>
				<div title="自动出价记录">
					<table id="autoAuctionDataGrid"></table>
				</div>
				<div title="加价幅度">
					<table id="rangeDataGrid"></table>
				</div>
				<div title="点赞列表">
					<table id="likeDataGrid"></table>
				</div>
				<c:if test="${zcProduct.margin > 0}">
					<div title="保证金记录">
						<table id="marginDataGrid"></table>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>