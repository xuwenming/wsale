<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcWallet" %>
<%@ page import="jb.model.TzcWalletDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
<title>ZcWallet管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcWalletDetailController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcWalletDetailController/dataGridByUser?userId=${userId}',
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
				field : 'orderNo',
				title : '<%=TzcWalletDetail.ALIAS_ORDER_NO%>',
				width : 85,
				sortable:true,
				formatter : function(value, row, index) {
					return '<a onclick="viewFun(\'' + row.id + '\')">' + row.orderNo + '</a>';
				}
			}, {
				field : 'amount',
				title : '金额',
				width : 40
			}, {
				field : 'walletAmount',
				title : '钱包余额',
				width : 40,
				formatter : function(value, row, index) {
					return value.toFixed(2);

				}
			}, {
				field : 'wtypeZh',
				title : '类型',
				width : 40,
				formatter : function(value, row, index) {
					if(row.wtype == 'WT01' && row.amount < 0) return "扣款";
					else return row.wtypeZh;
				}
			}, {
				field : 'description',
				title : '描述',
				width : 40
			}, {
				field : 'channelZh',
				title : '渠道',
				width : 40
			}, {
				field : 'addtime',
				title : '<%=TzcWalletDetail.ALIAS_ADDTIME%>',
				width : 80,
				sortable:true
			}, {
				field : 'handleStatusZh',
				title : '<%=TzcWalletDetail.ALIAS_HANDLE_STATUS%>',
				width : 40,
				formatter : function(value, row, index) {
					var str = value;
					if(row.wtype != 'WT02') str = '';
					return str;

				}
			} ] ],
			toolbar : '#toolbar'
		});
	});

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcWalletDetailController/view?id=' + id
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed">
					<tr>
						<td>
							<%=TzcWalletDetail.ALIAS_WTYPE%>：
							<jb:select dataType="WT" name="wtype"></jb:select>
						</td>
						<td>
							交易渠道：
							<jb:select dataType="CS" name="channel"></jb:select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>

	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>
</body>
</html>