<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProtection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcProtection管理</title>
<jsp:include page="../inc.jsp"></jsp:include>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcProtectionController/dataGridByUser?payStatus=PS02&userId=${userId}',
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
				title : '操作时间',
				width : 50
				}, {
				field : 'protectionTypeZh',
				title : '类型',
				width : 50		
				}, {
				field : 'price',
				title : '<%=TzcProtection.ALIAS_PRICE%>',
				width : 50,
				formatter : function(value, row, index) {
					return value.toFixed(2);
				}
				}, {
				field : 'reason',
				title : '备注',
				width : 100
			}  ] ],
			toolbar: [{
				text:'充值',
				iconCls: 'brick_add',
				handler: function(){
					//recharge();
					edit('PN01');
				}
			},'-',{
				text:'提现',
				iconCls: 'brick_delete',
				handler: function(){
					edit('PN03');
				}
			},'-',{
				text:'扣除',
				iconCls: 'brick_delete',
				handler: function(){
					edit('PN02');
				}
			}]
		});
	});

	function edit(type) {
		var title, text;
		if(type == 'PN01') {
			title = '消保金充值';
			text = '充值';
		} else if(type == 'PN02') {
			title = '消保金扣除';
			text = '扣除';
		} else {
			title = '消保金提现';
			text = '提现';
		}
		parent.$.modalDialog({
			title : title,
			width : 650,
			height : 260,
			href : '${pageContext.request.contextPath}/zcProtectionController/addPage?userId=${userId}&protectionType=' + type,
			buttons : [ {
				text : text,
				handler : function() {
					parent.$.messager.confirm('询问', '操作提醒，是否继续？', function(b) {
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
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
</body>
</html>