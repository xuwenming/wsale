<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#roleIds').combotree({
			url : '${pageContext.request.contextPath}/roleController/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			multiple : true,
			onLoadSuccess : function() {
				var options = $('#roleIds').combotree('options');
				console.log(options);
				parent.$.messager.progress('close');
			},
			cascadeCheck : false,
			value : $.stringToList('${user.roleIds}')
		});

		$('#categoryId').combotree({
			url : '${pageContext.request.contextPath}/zcCategoryController/tree',
			parentField : 'pid',
			textFiled : 'name',
			lines : true,
			panelHeight : '400',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
				$('#categoryId').treegrid('collapseAll');
			},
			onBeforeSelect:function(node){
				if(node.state){
					$("#cc").tree("unselect");
				}
			}
		});

		$('#form').form({
			url : '${pageContext.request.contextPath}/userController/grant',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
					parent.$.modalDialog.openner_dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>编号</th>
					<td><input name="ids" type="text" class="span2" value="${ids}"
						readonly="readonly"></td>
					<th>所属角色</th>
					<td><select id="roleIds" name="roleIds"
						style="width: 140px; height: 29px;"></select><img
						src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png"
						onclick="$('#roleIds').combotree('clear');" /></td>
				</tr>
				<tr>
					<th>所属分类</th>
					<td colspan="3">
						<select id="categoryId" name="categoryId" style="width: 140px; height: 29px;"></select>
						<img src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png"
								onclick="$('#categoryId').combotree('clear');" />
						(仅选择了前端角色此参数才有效)
					</td>
				</tr>
				<tr>
					<td colspan="4"><span style="color: red;">*</span> 变更前端角色必须选择所属分类，否则操作无效</td>
				</tr>
				<tr>
					<td colspan="4"><span style="color: red;">*</span> 设置首席版主角色请至分类管理模块进行操作</td>
				</tr>
			</table>
		</form>
		<div>

		</div>
	</div>
</div>