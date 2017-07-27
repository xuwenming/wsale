<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPayOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<%
	request.setAttribute("vEnter", "\n");
%>

<!DOCTYPE html>
<html>
<head>
	<title>user管理</title>
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
						gridMap.handle(this,loadAddressDataGrid);
					}, grid: null
				}, 2: {
					invoke: function () {
						gridMap.handle(this,loadPayOrderDataGrid);
					}, grid: null
				}, 3: {
					invoke: function () {
						gridMap.handle(this,loadAttedDataGrid);
					}, grid: null
				}, 4: {
					invoke: function () {
						gridMap.handle(this,loadFansDataGrid);
					}, grid: null
				}, 5: {
					invoke: function () {
						gridMap.handle(this,loadShieldorsDataGrid);
					}, grid: null
				}
			};
			$('#user_view_tabs').tabs({
				onSelect: function (title, index) {
					gridMap[index].invoke();
				}
			});
		});

		function loadAddressDataGrid() {
			return $('#addressDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcAddressController/dataGridByUser?userId=${user.id}&orderId=-1',
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
					title : '创建时间',
					width : 50
				}, {
					field : 'atype',
					title : '类型',
					width : 30,
					formatter:function(value){
						var str = "";
						if(value == 1) str = "收货地址";
						else str = "退货地址";
						return str;
					}
				}, {
					field : 'userName',
					title : '姓名',
					width : 30
				}, {
					field : 'telNumber',
					title : '联系电话',
					width : 30
				}, {
					field : 'detailInfo',
					title : '详细地址',
					width : 100,
					formatter:function(value, row, index){
						return row.provinceName + row.cityName + row.countyName + row.detailInfo;
					}
				} ] ]
			});
		}
		function loadPayOrderDataGrid() {
			return $('#payOrderDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcPayOrderController/dataGridByUser?userId=${user.id}',
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
				nowrap : true,
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
					title : '<%=TzcPayOrder.ALIAS_ADDTIME%>',
					width : 50,
					sortable : true
				}, {
					field : 'orderNo',
					title : '<%=TzcPayOrder.ALIAS_ORDER_NO%>',
					width : 50
				}, {
					field : 'totalFee',
					title : '<%=TzcPayOrder.ALIAS_TOTAL_FEE%>',
					width : 50,
					sortable : true
				}, {
					field : 'serviceFee',
					title : '技术服务费',
					width : 50,
					formatter:function(value){
						return $.fenToYuan(value);
					}
				}, {
					field : 'objectTypeZh',
					title : '<%=TzcPayOrder.ALIAS_OBJECT_TYPE%>',
					width : 50,
					formatter : function(value, row, index) {
						if(row.objectType == 'PO05') return '<a onclick="viewOrderFun(\'' + row.objectId + '\')">'+value+'</a>';
						else return value;
					}
				}, {
					field : 'channelZh',
					title : '<%=TzcPayOrder.ALIAS_CHANNEL%>',
					width : 50
				}, {
					field : 'payStatusZh',
					title : '<%=TzcPayOrder.ALIAS_PAY_STATUS%>',
					width : 50,
					formatter : function(value, row, index) {
						if(row.payStatus == 'PS02') return '<font color="#4cd964;">' + row.payStatusZh + '</font>';
						return row.payStatusZh;
					}
				}, {
					field : 'paytime',
					title : '支付时间',
					width : 50,
					sortable : true
				}, {
					field : 'refundNo',
					title : '退款单号',
					width : 50
				}, {
					field : 'refundFee',
					title : '退款金额',
					width : 50,
					formatter:function(value){
						if(!value) return "";
						return $.fenToYuan(value);
					}
				}, {
					field : 'refundtime',
					title : '退款时间',
					width : 50,
					sortable : true
				}, {
					field : 'refTransactionNo',
					title : '第三方支付订单号',
					width : 50
				} ] ],
				toolbar: [{
					text:'查询',
					iconCls: 'brick_add',
					handler: function(){
						$('#payOrderDataGrid').datagrid('load', $.serializeObject($('#payOrderSearchForm')));
					}
				},'-',{
					text:'清空条件',
					iconCls: 'brick_delete',
					handler: function(){
						$('#payOrderSearchForm input').val('');
						$('#payOrderDataGrid').datagrid('load', {});
					}
				}]
			});
		}

		function loadAttedDataGrid() {
			return loadShieldorfansDataGrid($('#attedDataGrid'), '${pageContext.request.contextPath}/zcShieldorfansController/dataGridByUser?objectId=${user.id}&objectType=FS', 1);
		}
		function loadFansDataGrid() {
			return loadShieldorfansDataGrid($('#fansDataGrid'), '${pageContext.request.contextPath}/zcShieldorfansController/dataGridByUser?objectById=${user.id}&objectType=FS', 2);
		}
		function loadShieldorsDataGrid() {
			return loadShieldorfansDataGrid($('#shieldorsDataGrid'), '${pageContext.request.contextPath}/zcShieldorfansController/dataGridByUser?objectId=${user.id}&objectType=SD', 1);
		}
		function loadShieldorfansDataGrid(dom, url, type) {
			return dom.datagrid({
				url : url,
				fitColumns: true,
				fit:true,
				border: false,
				pagination: true,
				idField: 'id',
				pageSize: 10,
				pageList: [10, 20, 30, 40, 50],
				sortName: 'addtime',
				sortOrder: 'desc',
				checkOnSelect: false,
				selectOnCheck: false,
				nowrap: false,
				striped: true,
				rownumbers: true,
				singleSelect: true,
				columns : [ [ {
					field : 'id',
					title : '编号',
					width : 150,
					hidden : true
				}, {
					field : 'addtime',
					title : '创建时间',
					width : 30,
					sortable : true
				}, {
					field : 'nickname',
					title : '用户昵称',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
						if(type == 1) str = row.objectByName;
						else str = row.objectName;
						return str;
					}
				} ] ]

			});
		}

		function viewOrderFun(id) {
			parent.$.modalDialog({
				title : '查看数据',
				width : 780,
				height : 500,
				href : '${pageContext.request.contextPath}/zcOrderController/view?id=' + id
			});
		}
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;">
			<div style="font-size: 12pt; padding: 8px;">用户昵称：${user.nickname}</div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="user_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
				<div title="基本信息">
					<table class="table table-hover table-condensed" id="bbsInfo">
						<tr>
							<th width="5%">昵称</th>
							<td width="20%">
								${user.nickname}
							</td>
							<th width="5%">性别</th>
							<td width="20%">
								<c:choose>
									<c:when test="${user.sex == 1}">男</c:when>
									<c:when test="${user.sex == 2}">女</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<th width="5%">头像</th>
							<td align="left" rowspan="5" class="imageSlide">
								<img src="${user.headImage}" i="${user.headImage}" width="100" height="100"/>
							</td>
						</tr>
						<tr>
							<th>余额</th>
							<td>
								${user.walletAmount}
							</td>
							<th>消保金</th>
							<td>
								${user.protection}
							</td>
						</tr>
						<tr>
							<th>手机号</th>
							<td>
								${user.mobile}
							</td>
							<th>微信号</th>
							<td>
								${user.wechatNo}
							</td>
						</tr>
						<tr>
							<th>地区</th>
							<td>
								${user.area}
							</td>
							<th>联系人</th>
							<td>
								${user.contact}
							</td>
						</tr>
						<tr>
							<th>是否认证</th>
							<td>
								<c:if test="${user.isAuth}">是</c:if>
								<c:if test="${!user.isAuth}">否</c:if>
							</td>
							<th>是否禁言</th>
							<td>
								<c:if test="${user.isGag}">是</c:if>
								<c:if test="${!user.isGag}">否</c:if>
							</td>
						</tr>
						<c:if test="${user.utype == 'UT02'}">
							<tr>
								<th>店铺排序</th>
								<td>
									${user.shopSeq}
								</td>
								<th>成交额</th>
								<td>
									${turnover}
								</td>
							</tr>
						</c:if>
						<tr>
							<th>职务</th>
							<td>
								${user.position}
							</td>
							<th>发帖数量</th>
							<td>
								${user.bbsNums}
							</td>
							<th>个人签名</th>
							<td>
								${user.bardian}
							</td>
						</tr>
						<tr>
							<th>粉丝数</th>
							<td>
								${user.fans}
							</td>
							<th>屏蔽数</th>
							<td>
								${user.shieldors}
							</td>
							<th>信誉</th>
							<td>
								${user.credit}
							</td>
						</tr>

					</table>
				</div>
				<div title="地址管理">
					<table id="addressDataGrid"></table>
				</div>
				<div title="支付记录">
					<div class="easyui-layout" data-options="fit : true,border : false">
						<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
							<form id="payOrderSearchForm">
								<table class="table table-hover table-condensed">
									<td>
										交易单号：
										<input type="text" name="orderNo" class="span2"/>
									</td>
									<td>
										支付状态：
										<jb:select dataType="PS" name="payStatus"></jb:select>
									</td>
									<td>
										支付类型：
										<jb:select dataType="PO" name="objectType"></jb:select>
									</td>
									<td>
										支付渠道：
										<jb:select dataType="CS" name="channel"></jb:select>
									</td>
								</table>
							</form>
						</div>
						<div data-options="region:'center',border:false">
							<table id="payOrderDataGrid"></table>
						</div>
					</div>
				</div>
				<div title="关注列表">
					<table id="attedDataGrid"></table>
				</div>
				<div title="粉丝列表">
					<table id="fansDataGrid"></table>
				</div>
				<div title="屏蔽列表">
					<table id="shieldorsDataGrid"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>