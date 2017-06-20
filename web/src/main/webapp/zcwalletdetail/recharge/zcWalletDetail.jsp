<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcWalletDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcWalletDetail管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcWalletDetailController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid, userCombogrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcWalletDetailController/rechargeDataGrid?wtype=WT01',
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
				field : 'userName',
				title : '交易人',
				width : 50
				}, {
				field : 'orderNo',
				title : '<%=TzcWalletDetail.ALIAS_ORDER_NO%>',
				width : 85,
				sortable:true
				}, {
				field : 'amount',
				title : '金额',
				width : 40
				}, {
				field : 'walletAmount',
				title : '钱包余额',
				width : 40,
				formatter : function(value, row, index) {
					return '<a onclick="viewWallet(\'' + row.userId + '\', \''+row.userName+'\')">' + row.walletAmount.toFixed(2)+ '</a>';
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
				field : 'action',
				title : '操作',
				width : 50,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canView) {
						//str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_link.png');
						str += '<a onclick="viewFun(\'' + row.id + '\')">查看</a>';
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

		userCombogrid = $('#userId').combogrid({
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

		userCombogrid.next('span').find('input').focus(function(){
			userCombogrid.combogrid("showPanel");
		});
	});

	function viewWallet(id, nickname) {
		var href = '${pageContext.request.contextPath}/userController/viewWallet?id=' + id,
				title = '余额-' + nickname,
				t = parent.$("#index_tabs");
		if(t.tabs('exists', title)) {
			t.tabs('select', title);
		} else {
			t.tabs('add', {
				title : title,
				content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
				closable : true
			});
		}
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
				$.post('${pageContext.request.contextPath}/zcWalletDetailController/delete', {
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
			href : '${pageContext.request.contextPath}/zcWalletDetailController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/zcWalletDetailController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '钱包余额充值',
			width : 780,
			height : 300,
			href : '${pageContext.request.contextPath}/zcWalletDetailController/addPage',
			buttons : [ {
				text : '充值',
				handler : function() {
					parent.$.messager.confirm('询问', '您是否要对该用户进行余额充值？', function(b) {
						if (b) {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.submit();
						}
					});
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
	        url:'${pageContext.request.contextPath}/zcWalletDetailController/download',
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
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 110px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>	
							<td>
								交易人：
								<input id="userId" name="userId"/>
							</td>
							<td>
								<%=TzcWalletDetail.ALIAS_ORDER_NO%>：
								<input type="text" name="orderNo" maxlength="64" class="span2"/>
							</td>
							<td>
								交易渠道：
								<jb:select dataType="CS" name="channel"></jb:select>
							</td>
						</tr>	
						<tr>
							<td>
								<%=TzcWalletDetail.ALIAS_DESCRIPTION%>：
								<input type="text" name="description" maxlength="255" class="span2"/>
							</td>
							<td>
								<%=TzcWalletDetail.ALIAS_HANDLE_STATUS%>：
								<jb:select dataType="HS" name="handleStatus"></jb:select>
							</td>
							<td>
								<%=TzcWalletDetail.ALIAS_ADDTIME%>：
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcWalletDetail.FORMAT_ADDTIME%>'})" id="addtimeBegin" name="addtimeBegin"/>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcWalletDetail.FORMAT_ADDTIME%>'})" id="addtimeEnd" name="addtimeEnd"/>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcWalletDetailController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">充值</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>	
</body>
</html>