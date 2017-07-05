<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcForumBbs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcForumBbs管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcForumBbsController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcForumBbsController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcForumBbsController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcForumBbsController/editNumPage')}">
	<script type="text/javascript">
		$.canEditNum = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid, userCombogrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcForumBbsController/dataGrid',
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
				checkbox : ${utype == 'UT02'} ? false : true,
				hidden : ${utype == 'UT02'} ? true : false
				}, {
				field : 'bbsTitle',
				title : '标题',
				width : 110,
				formatter : function (value, row, index) {
					if ($.canView) {
						return '<a onclick="viewBbs(\'' + row.id + '\', \''+row.bbsTitle+'\')">' + row.bbsTitle + '</a>';
					} else return row.bbsTitle;

				}
				}, {
				field : 'categoryName',
				title : '所属分类',
				width : 70
				}, {
				field : 'bbsTypeZh',
				title : '类别',
				width : 40
				}, {
				field : 'bbsStatusZh',
				title : '状态',
				width : 30
				}, {
				field : 'isOffReplyZh',
				title : '<%=TzcForumBbs.ALIAS_IS_OFF_REPLY%>',
				width : 50		
				}, {
				field : 'isTopZh',
				title : '<%=TzcForumBbs.ALIAS_IS_TOP%>',
				width : 40
				}, {
				field : 'isLightZh',
				title : '<%=TzcForumBbs.ALIAS_IS_LIGHT%>',
				width : 40
				}, {
				field : 'isEssenceZh',
				title : '<%=TzcForumBbs.ALIAS_IS_ESSENCE%>',
				width : 40
				}, {
				field : 'isHomeHotZh',
				title : '首页推荐',
				width : 40
				}, {
				field : 'addUserName',
				title : '发帖人',
				width : 70
				}, {
				field : 'addtime',
				title : '发帖时间',
				width : 80,
				sortable : true
			}, {
				field : 'action',
				title : '操作',
				width : 60,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						//str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
						str += '<a onclick="editFun(\'' + row.id + '\')">编辑</a>';
					}
					str += '&nbsp;';
					if ($.canDelete) {
						//str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');
						str += '<a onclick="deleteFun(\'' + row.id + '\')">删除</a>';
					}
					//str += '&nbsp;';
					//if ($.canView) {
					//	str += $.formatString('<img onclick="viewBbs(\'{0}\',\'{1}\');" src="{2}" title="查看"/>', row.id,row.bbsTitle, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_link.png');
					//}
					str += '&nbsp;';
					if ($.canEditNum) {
						//str += $.formatString('<a onclick="editNumFun(\'{0}\');" style="color:red;cursor: pointer;">围观数</a>', row.id);
						str += '<a onclick="editNumFun(\'' + row.id + '\')">围观数</a>';
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
				$.post('${pageContext.request.contextPath}/zcForumBbsController/delete', {
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
			href : '${pageContext.request.contextPath}/zcForumBbsController/editPage?id=' + id,
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

	function editNumFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 500,
			height : 150,
			href : '${pageContext.request.contextPath}/zcForumBbsController/editNumPage?id=' + id,
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

	function viewBbs(id, title) {
		if(title.length > 4)
			title = title.substr(0, 4) + "...";
		var href = '${pageContext.request.contextPath}/zcForumBbsController/view?id=' + id;
		parent.$("#index_tabs").tabs('add', {
			title : '帖子详情-' + title,
			content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
			closable : true
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcForumBbsController/addPage',
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

	function commentFun() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			parent.$.modalDialog({
				title : '帖子评论',
				width : 600,
				height : 220,
				href : '${pageContext.request.contextPath}/zcForumBbsController/addCommentPage?ids=' + ids.join(','),
				buttons : [ {
					text : '评论',
					handler : function() {
						parent.$.messager.confirm('询问', '是否发表评论？', function(b) {
							if (b) {
								var f = parent.$.modalDialog.handler.find('#form');
								f.submit();
							}
						});
					}
				} ]
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要评论的帖子！'
			});
		}
	}

	function downloadTable(){
		var options = dataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/zcForumBbsController/download',
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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 145px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>
							<td>
								标题：
								<input type="text" name="bbsTitle" maxlength="100" class="span2"/>
							</td>
							<td>
								所属分类：
								<select id="categoryId" name="categoryId" style="width: 140px; height: 29px;"></select>
							</td>
							<td>
								类别：
								<jb:select dataType="BT" name="bbsType"></jb:select>
							</td>
						</tr>
						<tr>
							<td>
								发帖人：
								<input id="addUserId" name="addUserId"/>
							</td>
							<td>
								首页推荐：
								<select name="isHomeHot" class="easyui-combobox"
										data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="">不限</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
							<td colspan="2">
								发帖时间：
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcForumBbs.FORMAT_ADDTIME%>'})" id="addtimeBegin" name="addtimeBegin"/>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcForumBbs.FORMAT_ADDTIME%>'})" id="addtimeEnd" name="addtimeEnd"/>
							</td>
						</tr>
						<tr>
							<td>
								评论次数 >=
								<input name="bbsComment" class="easyui-numberspinner"
									   style="width: 140px; height: 29px;" data-options="editable:true" >
							</td>
							<td>
								转发次数 >=
								<input name="bbsShare" class="easyui-numberspinner"
									   style="width: 140px; height: 29px;" data-options="editable:true" >
							</td>
							<td>
								打赏次数 >=
								<input name="bbsReward" class="easyui-numberspinner"
									   style="width: 140px; height: 29px;" data-options="editable:true" >
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcForumBbsController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">发帖</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcForumBbsController/addCommentPage')}">
			<a onclick="commentFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'comments'">评论</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcForumBbsController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>