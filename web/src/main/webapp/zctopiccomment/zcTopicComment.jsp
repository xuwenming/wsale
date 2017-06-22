<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcTopicComment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcTopicComment管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcTopicCommentController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcTopicCommentController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcTopicCommentController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcTopicCommentController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
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
				field : 'topicId',
				title : '<%=TzcTopicComment.ALIAS_TOPIC_ID%>',
				width : 50		
				}, {
				field : 'comment',
				title : '<%=TzcTopicComment.ALIAS_COMMENT%>',
				width : 50		
				}, {
				field : 'ctype',
				title : '<%=TzcTopicComment.ALIAS_CTYPE%>',
				width : 50		
				}, {
				field : 'pid',
				title : '<%=TzcTopicComment.ALIAS_PID%>',
				width : 50		
				}, {
				field : 'isDeleted',
				title : '<%=TzcTopicComment.ALIAS_IS_DELETED%>',
				width : 50		
				}, {
				field : 'userId',
				title : '<%=TzcTopicComment.ALIAS_USER_ID%>',
				width : 50		
				}, {
				field : 'addtime',
				title : '<%=TzcTopicComment.ALIAS_ADDTIME%>',
				width : 50		
				}, {
				field : 'auditStatus',
				title : '<%=TzcTopicComment.ALIAS_AUDIT_STATUS%>',
				width : 50		
				}, {
				field : 'auditTime',
				title : '<%=TzcTopicComment.ALIAS_AUDIT_TIME%>',
				width : 50		
				}, {
				field : 'auditUserId',
				title : '<%=TzcTopicComment.ALIAS_AUDIT_USER_ID%>',
				width : 50		
				}, {
				field : 'auditRemark',
				title : '<%=TzcTopicComment.ALIAS_AUDIT_REMARK%>',
				width : 50		
			}, {
				field : 'action',
				title : '操作',
				width : 100,
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
				$.post('${pageContext.request.contextPath}/zcTopicCommentController/delete', {
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
			href : '${pageContext.request.contextPath}/zcTopicCommentController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/zcTopicCommentController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcTopicCommentController/addPage',
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
	        url:'${pageContext.request.contextPath}/zcTopicCommentController/download',
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
								<%=TzcTopicComment.ALIAS_TOPIC_ID%>：
											<input type="text" name="topicId" maxlength="36" class="span2"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_COMMENT%>：
											<input type="text" name="comment" maxlength="500" class="span2"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_CTYPE%>：
											<input type="text" name="ctype" maxlength="18" class="span2"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_PID%>：
											<input type="text" name="pid" maxlength="36" class="span2"/>
							</td>
						</tr>	
						<tr>	
							<td>
								<%=TzcTopicComment.ALIAS_IS_DELETED%>：
											<input type="text" name="isDeleted" maxlength="0" class="span2"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_USER_ID%>：
											<input type="text" name="userId" maxlength="36" class="span2"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_ADDTIME%>：
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcTopicComment.FORMAT_ADDTIME%>'})" id="addtimeBegin" name="addtimeBegin"/>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcTopicComment.FORMAT_ADDTIME%>'})" id="addtimeEnd" name="addtimeEnd"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_AUDIT_STATUS%>：
											<jb:select dataType="AS" name="auditStatus"></jb:select>	
							</td>
						</tr>	
						<tr>	
							<td>
								<%=TzcTopicComment.ALIAS_AUDIT_TIME%>：
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcTopicComment.FORMAT_AUDIT_TIME%>'})" id="auditTimeBegin" name="auditTimeBegin"/>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcTopicComment.FORMAT_AUDIT_TIME%>'})" id="auditTimeEnd" name="auditTimeEnd"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_AUDIT_USER_ID%>：
											<input type="text" name="auditUserId" maxlength="36" class="span2"/>
							</td>
							<td>
								<%=TzcTopicComment.ALIAS_AUDIT_REMARK%>：
											<input type="text" name="auditRemark" maxlength="500" class="span2"/>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcTopicCommentController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcTopicCommentController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>