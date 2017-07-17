<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcIntermediary" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcIntermediary管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcIntermediaryController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcIntermediaryController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcIntermediaryController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid, bbsCombogrid, sellerCombogrid, buyerCombogrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcIntermediaryController/dataGrid',
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
				title : '<%=TzcIntermediary.ALIAS_ADDTIME%>',
				width : 50,
				sortable : true
				}, {
				field : 'imNo',
				title : '<%=TzcIntermediary.ALIAS_IM_NO%>',
				width : 50,
				formatter : function (value, row, index) {
					return '<a onclick="viewIM(\'' + row.id + '\', \''+row.imNo+'\')">' + row.imNo + '</a>';
				}
				}, {
				field : 'bbsTitle',
				title : '<%=TzcIntermediary.ALIAS_BBS_ID%>',
				width : 80
				}, {
				field : 'sellerUserName',
				title : '<%=TzcIntermediary.ALIAS_SELL_USER_ID%>',
				width : 50		
				}, {
				field : 'buyerUserName',
				title : '<%=TzcIntermediary.ALIAS_USER_ID%>',
				width : 50		
				}, {
				field : 'amount',
				title : '<%=TzcIntermediary.ALIAS_AMOUNT%>',
				width : 50,
				formatter:function(value){
					return $.fenToYuan(value);
				}
				}, {
				field : 'statusZh',
				title : '<%=TzcIntermediary.ALIAS_STATUS%>',
				width : 50,
				formatter : function (value, row, index) {
					if(row.status == 'IS02') return '<font color="#4cd964;">' + row.statusZh + '</font>';
					else if(row.status == 'IS04') return '<font color="#f6383a;">' + row.statusZh + '</font>';
					else if(row.status == 'IS03') return '<font color="red">' + row.statusZh + '</font>';
					else return row.statusZh;
				}
			}, {
				field : 'action',
				title : '操作',
				width : 100,
				hidden : true,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
					}
					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');
					}
					str += '&nbsp;';
					if ($.canView) {
						str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_link.png');
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

		bbsCombogrid = $('#bbsId').combogrid({
			url:'${pageContext.request.contextPath}/zcForumBbsController/queryBbs',
			panelWidth:520,
			width : 140,
			height : 29,
			idField:'id',
			textField:'bbsTitle',
			mode:'remote',
			method:'post',
			nowrap : true,
			striped:true,
			columns:[[
				{field:'bbsTitle',title:'标题',width:200},
				{field:'categoryName',title:'所属分类',width:80},
				{field:'bbsTypeZh',title:'类别',width:80},
				{field:'addUserName',title:'发帖人',width:140}
			]]
		});

		bbsCombogrid.next('span').find('input').focus(function(){
			bbsCombogrid.combogrid("showPanel");
		});

		sellerCombogrid = $('#sellUserId').combogrid({
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

		buyerCombogrid = $('#userId').combogrid({
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
				$.post('${pageContext.request.contextPath}/zcIntermediaryController/delete', {
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
			href : '${pageContext.request.contextPath}/zcIntermediaryController/editPage?id=' + id,
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

	function viewIM(id, imNo) {
		var href = '${pageContext.request.contextPath}/zcIntermediaryController/view?id=' + id,
				title = '交易详情-' + imNo, t = parent.$("#index_tabs");
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

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcIntermediaryController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcIntermediaryController/addPage',
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
	        url:'${pageContext.request.contextPath}/zcIntermediaryController/download',
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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>	
							<td>
								<%=TzcIntermediary.ALIAS_IM_NO%>：
								<input type="text" name="imNo" maxlength="64" class="span2"/>
							</td>
							<td>
								<%=TzcIntermediary.ALIAS_BBS_ID%>：
								<input id="bbsId" name="bbsId"/>
							</td>
							<td>
								<%=TzcIntermediary.ALIAS_SELL_USER_ID%>：
								<input id="sellUserId" name="sellUserId"/>
							</td>
							<td>
								<%=TzcIntermediary.ALIAS_USER_ID%>：
								<input id="userId" name="userId"/>
							</td>
							<td>
								<%=TzcIntermediary.ALIAS_STATUS%>：
								<jb:select dataType="IS" name="status"></jb:select>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcIntermediaryController/addPage')}">
			<!--<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>-->
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcIntermediaryController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>