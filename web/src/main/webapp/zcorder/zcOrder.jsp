<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcOrder管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcOrderController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcOrderController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcOrderController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/zcOrderController/cancelXr')}">
		<script type="text/javascript">
			$.canCancelXr = true;
		</script>
	</c:if>
<script type="text/javascript">
	var dataGrid, productCombogrid, sellerCombogrid, buyerCombogrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcOrderController/dataGrid',
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
				title : '<%=TzcOrder.ALIAS_ORDER_NO%>',
				width : 50		
				}, {
				field : 'pno',
				title : '拍品编号',
				width : 50,
				formatter : function(value, row, index) {
					var product = row.product;
					return product.pno;
				}
				},{
				field : 'isIntermediaryZh',
				title : '是否中介',
				width : 30,
				formatter : function(value, row, index) {
					if(row.isIntermediary) return '<font color="#f6383a;">是</font>';
					else return '否';
				}
				},{
				field : 'sellerUserId',
				title : '卖家',
				width : 50,
				formatter : function(value, row, index) {
					var seller = row.seller;
					return seller.nickname;
				}
				},{
				field : 'buyerUserId',
				title : '买家',
				width : 50,
				formatter : function(value, row, index) {
					var buyer = row.buyer;
					return buyer.nickname;
				}
				},{
				field : 'totalPrice',
				title : '订单金额(元)',
				width : 50
				},{
				field : 'payStatusZh',
				title : '<%=TzcOrder.ALIAS_PAY_STATUS%>',
				width : 50,
				formatter : function(value, row, index) {
					if(row.payStatus == 'PS02') return '<font color="#4cd964;">' + row.payStatusZh + '</font>';
					if(row.orderStatus == 'OS10' && row.payStatus == 'PS01') return '<font color="#f6383a;">当面交易</font>';
					if(row.faceStatus && row.faceStatus == 'FS01' && row.payStatus != 'PS02') return '<font color="#f6383a;">当面交易申请中</font>';

					return row.payStatusZh;
				}
				}, {
				field : 'paytime',
				title : '<%=TzcOrder.ALIAS_PAYTIME%>',
				width : 50		
				}, {
				field : 'orderStatusZh',
				title : '<%=TzcOrder.ALIAS_ORDER_STATUS%>',
				width : 50,
				formatter : function (value, row, index) {
					var str = row.orderStatusZh;
					if(row.orderStatus == 'OS02') str = '<font color="orange">' + row.orderStatusZh + '</font>';
					else if(row.orderStatus == 'OS05') str = '<font style="color:#1AAFF0;">' + row.orderStatusZh + '</font>';
					else if(row.orderStatus == 'OS10') str = '<font color="#4cd964;">' + row.orderStatusZh + '</font>';
					else if(row.orderStatus == 'OS15') str =  '<font color="red">' + row.orderStatusZh + '</font>';

					return str;
				}
				}, {
				field : 'addtime',
				title : '<%=TzcOrder.ALIAS_ADDTIME%>',
				width : 50,
				sortable : true
				}, {
				field : 'action',
				title : '操作',
				width : 80,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						//str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
					}
					//str += '&nbsp;';
					if ($.canDelete) {
						//str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');
					}
					//str += '&nbsp;';
					if ($.canView) {
						//str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_link.png');
						str += '<a onclick="viewFun(\'' + row.id + '\')">查看</a>';
					}
					var xr = row.xiaoer;
					if(row.isXiaoer && xr.status == 'XS01') {
						str += '&nbsp;';
						if ($.canCancelXr) {
							str += $.formatString('<a onclick="cancelXrFun(\'{0}\', \'{1}\', \'{2}\');" style="color:red;cursor: pointer;" title="撤销小二">撤销小二</a>', row.id, row.xiaoer.id, row.xiaoer.idType);
							if(row.xiaoer.idType == 1) {
								str += '&nbsp;';
								str += $.formatString('<a onclick="agreeBack(\'{0}\', \'{1}\');" style="color:red;cursor: pointer;" title="同意退货">同意退货</a>', row.id, row.productId);
							}
						}
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});

		productCombogrid = $('#productId').combogrid({
			url:'${pageContext.request.contextPath}/zcProductController/queryProducts',
			panelWidth:600,
			width : 140,
			height : 29,
			idField:'id',
			textField:'pno',
			mode:'remote',
			method:'post',
			nowrap : true,
			striped:true,
			columns:[[
				{field:'pno',title:'拍品编号',width:140},
				{field:'content',title:'拍品内容',width:200},
				{field:'cname',title:'所属分类',width:80},
				{field:'addUserName',title:'发布人',width:150}
			]],
			queryParams : {isPno:true}
		});

		productCombogrid.next('span').find('input').focus(function(){
			productCombogrid.combogrid("showPanel");
		});

		sellerCombogrid = $('#sellerUserId').combogrid({
			url:'${pageContext.request.contextPath}/userController/queryUsers',
			panelWidth:510,
			width : 140,
			height : 29,
			idField:'id',
			textField:'nickname',
			mode:'remote',
			method:'post',
			nowrap : true,
			striped:true,
			columns:[[
				{field:'nickname',title:'昵称',width:200},
				{field:'mobile',title:'手机号',width:150},
				{field:'wechatNo',title:'微信号',width:150}
			]]
		});

		sellerCombogrid.next('span').find('input').focus(function(){
			sellerCombogrid.combogrid("showPanel");
		});

		buyerCombogrid = $('#buyerUserId').combogrid({
			url:'${pageContext.request.contextPath}/userController/queryUsers',
			panelWidth:510,
			width : 140,
			height : 29,
			idField:'id',
			textField:'nickname',
			mode:'remote',
			method:'post',
			nowrap : true,
			striped:true,
			columns:[[
				{field:'nickname',title:'昵称',width:200},
				{field:'mobile',title:'手机号',width:150},
				{field:'wechatNo',title:'微信号',width:150}
			]]
		});

		buyerCombogrid.next('span').find('input').focus(function(){
			buyerCombogrid.combogrid("showPanel");
		});
	});

	function cancelXrFun(orderId, xrId, idType) {
		parent.$.messager.confirm('询问', '您是否要撤销小二介入？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/zcOrderController/cancelXr', {
					id : xrId, orderId : orderId, idType : idType
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function agreeBack(id, productId) {
		parent.$.messager.confirm('询问', '您是否要同意退货？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/zcOrderController/agreeBack', {
					id : id, productId : productId
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function deleteFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/zcOrderController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcOrderController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcOrderController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcOrderController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	function downloadTable(){
		var options = dataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/zcOrderController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		$('.searchXr .l-btn-text').html('过滤小二申请');
		dataGrid.datagrid('load', {});
	}

	function searchXrFun() {
		var isXiaoer = $('#isXiaoer').val();
		if(isXiaoer == 1) {
			$('#isXiaoer').val(0);
			$('.searchXr .l-btn-text').html('过滤小二申请');
		} else {
			$('#isXiaoer').val(1);
			$('.searchXr .l-btn-text').html('取消过滤小二');
		}
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 110px; overflow: hidden;">
			<form id="searchForm">
				<input type="hidden" name="isXiaoer" id="isXiaoer" value="0">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>	
							<td>
								<%=TzcOrder.ALIAS_ORDER_NO%>：
								<input type="text" name="orderNo" maxlength="36" class="span2"/>
							</td>
							<td>
								拍品编号：
								<!--<input id="productId" name="productId"/>-->
								<input type="text" name="pno" maxlength="36" class="span2"/>
							</td>
							<td>
								订单状态：
								<jb:select dataType="OS" name="orderStatus"></jb:select>
							</td>

						</tr>
						<tr>
							<td>
								卖家：
								<input id="sellerUserId" name="sellerUserId"/>
							</td>
							<td>
								买家：
								<input id="buyerUserId" name="buyerUserId"/>
							</td>
							<td>
								是否中介：
								<select name="isIntermediary" class="easyui-combobox"
										data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="">不限</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcOrderController/addPage')}">
			<!--<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>-->
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcOrderController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>

		<a onclick="searchXrFun();" href="javascript:void(0);" class="easyui-linkbutton searchXr" data-options="plain:true">过滤小二申请</a>
	</div>	
</body>
</html>