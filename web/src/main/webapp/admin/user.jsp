<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/userController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/userController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/userController/grantPage')}">
	<script type="text/javascript">
		$.canGrant = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/userController/editPwdPage')}">
	<script type="text/javascript">
		$.canEditPwd = true;
	</script>
</c:if>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/userController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'createdatetime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'name',
				title : '登录名称',
				width : 100,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'nickname',
				title : '昵称',
				width : 100,
				formatter : function (value, row, index) {
					if(row.utype == 'UT01') return row.nickname;

					return '<div style="min-width:100px;" onclick="viewUser(\'' + row.id + '\', \''+row.nickname+'\')"><a>' + row.nickname+ '</a></div>';
				}
			}, {
				field : 'headImage',
				title : '头像',
				width : 55,
				formatter : function(value, row, index) {
					var str = "";
					if(value){
						str = "<img style=\"height: 60px;width: 80px;\" src=\""+value+"\" />";
					}
					return str;
				}
			}, {
				field : 'utype',
				title : '类型',
				width : 50,
				formatter : function(value, row, index) {
					if(value == 'UT01') return '后端';
					else if(value == 'UT03') return '模拟';
					else return '前端';
				}
			}, {
				field : 'walletAmount',
				title : '余额',
				width : 80,
				align:'right',
				formatter : function (value, row, index) {
					if(row.utype == 'UT01' || row.walletAmount == undefined) return "";

					return '<a onclick="viewWallet(\'' + row.id + '\', \''+row.nickname+'\')">' + row.walletAmount.toFixed(2)+ '</a>';
				}
			}, {
				field : 'protection',
				title : '消保金',
				width : 80,
				align:'right',
				formatter : function (value, row, index) {
					if(row.utype == 'UT01' || row.protection == undefined) return "";

					return '<a onclick="viewProtection(\'' + row.id + '\', \''+row.nickname+'\')">' + row.protection.toFixed(2)+ '</a>';
				}
			}, {
				field : 'mobile',
				title : '手机号',
				width : 80,
				sortable : true
			}, {
				field : 'wechatNo',
				title : '微信号',
				width : 80,
				sortable : true
			}, {
				field : 'sex',
				title : '性别',
				width : 50,
				formatter : function(value, row, index) {
					if(value == 1) return '男';
					else if(value == 2) return '女'
					else return '未知';
				}
			}, {
				field : 'area',
				title : '地区',
				width : 80
			}, {
				field : 'isGag',
				title : '是否禁言',
				width : 50,
				formatter : function(value, row, index) {
					if(value) return '是';
					else return '否'
				}
			}, {
				field : 'createdatetime',
				title : '创建时间',
				width : 100,
				sortable : true
			}, {
				field : 'roleIds',
				title : '所属角色ID',
				width : 150,
				hidden : true
			}, {
				field : 'roleNames',
				title : '所属角色名称',
				width : 150
			}, {
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '';
					if(row.id == '0' && '${sessionInfo.id}' != '0') {
						str = '超级管理员';
					} else {
						if ($.canEdit) {
							str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
						}
						str += '&nbsp;';
						if ($.canGrant) {
							str += $.formatString('<img onclick="grantFun(\'{0}\');" src="{1}" title="授权"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
						}
						str += '&nbsp;';
						if (row.id != '0' && $.canDelete) {
							str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
						}
						str += '&nbsp;';
						if ($.canEditPwd && row.utype == 'UT01') {
							str += $.formatString('<img onclick="editPwdFun(\'{0}\');" src="{1}" title="修改密码"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/lock/lock_edit.png');
						}
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function(data) {
				var panel = dataGrid.datagrid('getPanel');
				var checkbox = panel.find('div.datagrid-cell-check input[type=checkbox]');
			   	// 去除系统管理员checkbox
			   	for(var i=0; i<data.rows.length; i++) {
			   		if(data.rows[i]['id'] == '0') {
			   			var index = $("#dataGrid").datagrid('getRowIndex', 0);
	        			$(checkbox.get(index)).remove();
			   		}
			   	}
				
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
			/*,
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}*/
		});
	});

	function editPwdFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '编辑用户密码',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/userController/editPwdPage?id=' + id,
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

	function viewUser(id, nickname) {
		var href = '${pageContext.request.contextPath}/userController/view?id=' + id,
			title = '用户详情-' + nickname, t = parent.$("#index_tabs");
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

	function viewWallet(id, nickname) {
		var href = '${pageContext.request.contextPath}/userController/viewWallet?id=' + id,
			title = '余额-' + nickname, t = parent.$("#index_tabs");
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
	function viewProtection(id, nickname) {
		var href = '${pageContext.request.contextPath}/userController/viewProtection?id=' + id,
			title = '消保金-' + nickname, t = parent.$("#index_tabs");
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
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/userController/delete', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						parent.$.messager.progress('close');
					}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以删除自己！'
					});
				}
			}
		});
	}

	function batchDeleteFun() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
						if (currentUserId != rows[i].id) {
							ids.push(rows[i].id);
						} else {
							flag = true;
						}
					}
					$.getJSON('${pageContext.request.contextPath}/userController/batchDelete', {
						ids : ids.join(',')
					}, function(result) {
						if (result.success) {
							dataGrid.datagrid('load');
							dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
						}
						if (flag) {
							parent.$.messager.show({
								title : '提示',
								msg : '不可以删除自己！'
							});
						} else {
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						parent.$.messager.progress('close');
					});
				}
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑用户',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/userController/editPage?id=' + id,
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

	function addFun() {
		parent.$.modalDialog({
			title : '添加用户',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/userController/addPage',
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

	function batchGrantFun() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			parent.$.modalDialog({
				title : '用户授权',
				width : 500,
				height : 300,
				href : '${pageContext.request.contextPath}/userController/grantPage?ids=' + ids.join(','),
				buttons : [ {
					text : '授权',
					handler : function() {
						parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要授权的记录！'
			});
		}
	}

	function grantFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '用户授权',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/userController/grantPage?ids=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}

	function syncFun() {
		parent.$.messager.confirm('询问', '您是否要一键同步环信账号？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});

				$.getJSON('${pageContext.request.contextPath}/userController/syncHxAccount', {
				}, function(result) {
					parent.$.messager.progress('close');
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}

				});
			}
		});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false"
			style="height: 110px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<td width="30%">
							昵称：
							<input type="text" name="nickname" placeholder="可以模糊查询昵称" class="span2" />
						</td>
						<td width="30%">
							手机号：
							<input type="text" name="mobile" class="span2" />
						</td>
						<td>
							是否禁言：
							<select name="isGag" class="easyui-combobox"
									data-options="width:140,height:29,editable:false,panelHeight:'auto'">
								<option value="">不限</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							类型：
							<select name="utype" class="easyui-combobox"
									data-options="width:140,height:29,editable:false,panelHeight:'auto'">
								<option value="">不限</option>
								<option value="UT01">后端</option>
								<option value="UT02">前端</option>
								<option value="UT03">模拟</option>
							</select>
						</td>
						<td colspan="4">
							创建时间：
							<input type="text" class="span2" name="createdatetimeStart" placeholder="点击选择时间"
								onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								readonly="readonly" />至
							<input type="text" class="span2" name="createdatetimeEnd" placeholder="点击选择时间"
								onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								readonly="readonly" />
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
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/userController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);"
				class="easyui-linkbutton"
				data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/userController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);"
				class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/userController/batchDelete')}">
			<a onclick="batchDeleteFun();" href="javascript:void(0);"
				class="easyui-linkbutton" data-options="plain:true,iconCls:'delete'">批量删除</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a
			href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_delete',plain:true"
			onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/syncHxAccount')}">
			<a onclick="syncFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'database_refresh'">同步环信</a>
		</c:if>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/userController/addPage')}">
			<div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
		</c:if>
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/userController/delete')}">
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		</c:if>
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/userController/editPage')}">
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
		</c:if>
	</div>
</body>
</html>