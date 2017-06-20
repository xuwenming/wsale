<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPositionApply" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcPositionApply管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcPositionApplyController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcPositionApplyController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcPositionApplyController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid, userCombogrid;
	$(function() {
		var queryParams = {};
		if(${mark}) queryParams.roleId = 'zsjs';
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcPositionApplyController/dataGrid',
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
			queryParams : queryParams,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
				}, {
				field : 'categoryName',
				title : '<%=TzcPositionApply.ALIAS_CATEGORY_ID%>',
				width : 90
				}, {
				field : 'roleName',
				title : '<%=TzcPositionApply.ALIAS_ROLE_ID%>',
				width : 50		
				}, {
				field : 'applyUserName',
				title : '<%=TzcPositionApply.ALIAS_APPLY_USER_ID%>',
				width : 60
				}, {
				field : 'addtime',
				title : '<%=TzcPositionApply.ALIAS_ADDTIME%>',
				width : 100,
				sortable:true
				}, {
				field : 'auditStatusZh',
				title : '<%=TzcPositionApply.ALIAS_AUDIT_STATUS%>',
				width : 50,
				formatter : function (value, row, index) {
					var str = row.auditStatusZh;
					if(row.auditStatus == 'AS02') str = '<font color="#4cd964;">' + row.auditStatusZh + '</font>';
					else if(row.auditStatus == 'AS03') str =  '<font color="#f6383a;">' + row.auditStatusZh + '</font>';

					return str;
				}
				}, {
				field : 'auditUserName',
				title : '<%=TzcPositionApply.ALIAS_AUDIT_USER_ID%>',
				width : 50
				}, {
				field : 'auditTime',
				title : '<%=TzcPositionApply.ALIAS_AUDIT_TIME%>',
				width : 100,
				sortable:true
				}, {
				field : 'auditRemark',
				title : '审核备注',
				width : 100
				}, {
				field : 'payStatusZh',
				title : '<%=TzcPositionApply.ALIAS_PAY_STATUS%>',
				width : 50,
				formatter : function (value, row, index) {
					var str = row.payStatusZh;
					if(row.payStatus == 'PS02') str = '<font color="#4cd964;">' + row.payStatusZh + '</font>';
					else if(row.payStatus == 'PS03') str =  '<font color="#f6383a;">' + row.payStatusZh + '</font>';

					return str;
				}
				}, {
				field : 'paytime',
				title : '支付时间',
				width : 100,
				sortable:true
			}, {
				field : 'action',
				title : '操作',
				width : 80,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						//str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
						str += '<a onclick="editFun(\'' + row.id + '\')">审核</a>';
					}
					//str += '&nbsp;';
					if ($.canDelete) {
						//str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');
					}
					str += '&nbsp;&nbsp;';
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
		$("#categoryId").next('span').find('input').focus(function(){
			$("#categoryId").combobox("showPanel");
		});
		$("#roleId").next('span').find('input').focus(function(){
			$("#roleId").combobox("showPanel");
		});

		userCombogrid = $('#applyUserId').combogrid({
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
				$.post('${pageContext.request.contextPath}/zcPositionApplyController/delete', {
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
			href : '${pageContext.request.contextPath}/zcPositionApplyController/editPage?id=' + id,
			buttons : [ {
				text : '通过',
				handler : function() {
					parent.$.messager.confirm('询问', '审核通过，是否继续？', function(b) {
						if (b) {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.find("input[name=auditStatus]").val("AS02");
							f.submit();
						}
					});
				}
			},{
				text : '不通过',
				handler : function() {
					parent.$.messager.confirm('询问', '审核不通过，是否继续？', function(b) {
						if (b) {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.find("input[name=auditStatus]").val("AS03");
							f.submit();
						}
					});
				}

			}]
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
			href : '${pageContext.request.contextPath}/zcPositionApplyController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcPositionApplyController/addPage',
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
	        url:'${pageContext.request.contextPath}/zcPositionApplyController/download',
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
		$('#searchForm input:not(.zsjs)').val('');
		searchFun();
		//dataGrid.datagrid('load', {});
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
								<%=TzcPositionApply.ALIAS_CATEGORY_ID%>：
								<jb:selectSql dataType="SL003" name="categoryId" editable="true" hasDownArrow="false"></jb:selectSql>
								<!--<input type="text" name="categoryName" maxlength="36" class="span2"/>-->
							</td>
							<td>
								<%=TzcPositionApply.ALIAS_ROLE_ID%>：
								<c:choose>
									<c:when test="${mark}">
										<input type="hidden" name="roleId" value="zsjs" class="zsjs">
										<input type="text" value="资深讲师" class="span2 zsjs" readonly/>
									</c:when>
									<c:otherwise>
										<jb:selectSql dataType="SL004" name="roleId" editable="true" hasDownArrow="false"></jb:selectSql>
									</c:otherwise>
								</c:choose>

								<!--<input type="text" name="roleName" maxlength="36" class="span2"/>-->
							</td>
							<td>
								<%=TzcPositionApply.ALIAS_APPLY_USER_ID%>：
								<input id="applyUserId" name="applyUserId"/>
							</td>
						</tr>
						<tr>
							<td>
								<%=TzcPositionApply.ALIAS_AUDIT_STATUS%>：
								<jb:select dataType="AS" name="auditStatus"></jb:select>
							</td>
							<td colspan="3">
								<%=TzcPositionApply.ALIAS_PAY_STATUS%>：
								<jb:select dataType="PS" name="payStatus"></jb:select>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcPositionApplyController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcPositionApplyController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>