<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/userController/edit',
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
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<c:choose>
						<c:when test="${user.utype == 'UT01'}">
							<th>编号</th>
							<td><input name="id" type="text" class="span2"
									   value="${user.id}" readonly="readonly"></td>
							<th>登录名称</th>
							<td><input name="name" type="text" placeholder="请输入登录名称"
									   class="easyui-validatebox span2" data-options="required:true"
									   value="${user.name}"></td>
						</c:when>
						<c:otherwise>
							<th>昵称</th>
							<td>
								<input type="hidden" name="id" value="${user.id}"/>
								<input name="nickname" type="text" placeholder="请输入昵称"
									   class="easyui-validatebox span2" data-options="required:true"
									   value="${user.nickname}">
							</td>
							<th>是否禁言</th>
							<td>
								<select name="isGag" class="easyui-combobox"
										data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="0" <c:if test="${!user.isGag}">selected="selected"</c:if>>否</option>
									<option value="1" <c:if test="${user.isGag}">selected="selected"</c:if>>是</option>
								</select>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<c:if test="${user.utype == 'UT02'}">
					<tr>
						<th>手机号</th>
						<td>
							<input name="mobile" type="text" placeholder="请输入手机号"
								   class="span2" value="${user.mobile}">
						</td>
						<th>微信号</th>
						<td>
							<input name="wechatNo" type="text" placeholder="请输入微信号"
								   class="span2" value="${user.wechatNo}">
						</td>
					</tr>
					<tr>
						<th>费用</th>
						<td colspan="3">
							<input name="serviceFeePer" value="${user.serviceFeePer}" class="easyui-numberspinner"
								   style="width: 140px; height: 29px;" required="required"
								   data-options="editable:true,min:0">
							<span style="color: red;">(*拍品成交时收取买方技术服务费百分比)</span>
						</td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
</div>