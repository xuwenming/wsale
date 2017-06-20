<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProduct" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcProduct管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcProductController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcProductController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcProductController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid, userCombogrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcProductController/dataGrid',
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
				field : 'pno',
				title : '<%=TzcProduct.ALIAS_PNO%>',
				width : 80,
				formatter : function (value, row, index) {
					if ($.canView) {
						return '<a onclick="viewProduct(\'' + row.id + '\', \''+row.pno+'\')">' + row.pno + '</a>';
					} else return row.pno;

				}
				}, {
				field : 'content',
				title : '<%=TzcProduct.ALIAS_CONTENT%>',
				width : 100
				},{
				field : 'cname',
				title : '分类',
				width : 40
				}, {
				field : 'startingTime',
				title : '<%=TzcProduct.ALIAS_STARTING_TIME%>',
				width : 60
				}, {
				field : 'realDeadline',
				title : '截拍时间',
				width : 60
				}, {
				field : 'startingPrice',
				title : '<%=TzcProduct.ALIAS_STARTING_PRICE%>',
				width : 40
				},{
				field : 'fixedPrice',
				title : '<%=TzcProduct.ALIAS_FIXED_PRICE%>',
				width : 40
				}, {
				field : 'currentPrice',
				title : '<%=TzcProduct.ALIAS_CURRENT_PRICE%>',
				width : 40
				}, {
				field : 'isFreeShippingZh',
				title : '是否包邮',
				width : 40
				}, {
				field : 'approvalDaysZh',
				title : '包退',
				width : 40
				}, {
				field : 'userName',
				title : '成交人',
				width : 50
				}, {
				field : 'hammerPrice',
				title : '<%=TzcProduct.ALIAS_HAMMER_PRICE%>',
				width : 40
				}, {
				field : 'statusZh',
				title : '状态',
				width : 40,
				formatter : function (value, row, index) {
					var str = row.statusZh;
					if(row.status == 'PT03') str = '<font color="#4cd964;">' + row.statusZh + '</font>';
					else if(row.status == 'PT04') str =  '<font color="#000;">' + row.statusZh + '</font>';
					else if(row.status == 'PT05') str =  '<font color="#c09853;">' + row.statusZh + '</font>';
					else if(row.status == 'PT06') str =  '<font color="#F00;">' + row.statusZh + '</font>';

					return str;
				}
				}, {
				field : 'addUserName',
				title : '发布人',
				width : 50
				}, {
				field : 'addtime',
				title : '发布时间',
				width : 60
				},{
				field : 'action',
				title : '操作',
				width : 55,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						//str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
						str += '<a onclick="editFun(\'' + row.id + '\')">围观数</a>';
					}
					str += '&nbsp;&nbsp;';
					if ($.canDelete) {
						//str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');
						str += '<a onclick="deleteFun(\'' + row.id + '\')">删除</a>';
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

		userCombogrid = $('#addUserId').combogrid({
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

		$('#categoryId').combotree({
			url : '${pageContext.request.contextPath}/zcCategoryController/tree',
			parentField : 'pid',
			textFiled : 'name',
			lines : true,
			panelHeight : 'auto',
			onBeforeSelect:function(node){
				if(node.state){
					$("#cc").tree("unselect");
				}
			},
			onLoadSuccess : function() {
				$('#categoryId').combotree('collapseAll');
			}
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
				$.post('${pageContext.request.contextPath}/zcProductController/delete', {
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
			width : 500,
			height : 150,
			href : '${pageContext.request.contextPath}/zcProductController/editPage?id=' + id,
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

	function viewProduct(id, pno) {
		var href = '${pageContext.request.contextPath}/zcProductController/view?id=' + id,
			title = '拍品详情-' + pno, t = parent.$("#index_tabs");
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

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcProductController/addPage',
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
	        url:'${pageContext.request.contextPath}/zcProductController/download',
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
								<%=TzcProduct.ALIAS_PNO%>：
								<input type="text" name="pno" maxlength="255" class="span2"/>
							</td>
							<td>
								<%=TzcProduct.ALIAS_CONTENT%>：
								<input type="text" name="content" maxlength="65535" class="span2"/>
							</td>
							<td>
								分类：
								<select id="categoryId" name="categoryId" style="width: 140px; height: 29px;"></select>
							</td>
							<td>
								发布人：
								<input id="addUserId" name="addUserId"/>
							</td>
						</tr>	
						<tr>
							<td>
								是否包邮：
								<select name="isFreeShipping" class="easyui-combobox"
										data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="">不限</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
							<td>
								包退：
								<jb:select dataType="AD" name="approvalDays"></jb:select>
							</td>
							<td colspan="2">
								状态：
								<jb:select dataType="PT" name="status"></jb:select>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcProductController/addPage')}">
			<!--<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>-->
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcProductController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>