<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProduct" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcProductController/edit',
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
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${zcProduct.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="12%">围观数</th>
					<td width="40%">
						<input value="${zcProduct.readCount}" class="easyui-validatebox span2" data-options="required:true" name="readCount" type="text"/>
					</td>
					<th width="12%">推荐排序</th>
					<td width="40%">
						<input name="seq" value="${zcProduct.seq}" class="easyui-numberspinner"
							   style="width: 140px; height: 29px;" required="required"
							   data-options="editable:true">
					</td>
				</tr>
				<tr>
					<th>保留价</th>
					<td colspan="3">
						<input name="reservePrice" value="${zcProduct.reservePrice}" class="easyui-numberspinner"
							   style="width: 140px; height: 29px;" required="required"
							   data-options="editable:true">
						<span style="color: red;">(*仅供后台参考数据)</span>
					</td>
				</tr>
				<tr>
					<th valign="top">内容</th>
					<td colspan="3">
						<textarea style="width: 510px;height: 200px;" name="content">${zcProduct.content}</textarea>
					</td>
				</tr>

			</table>
		</form>
	</div>
</div>