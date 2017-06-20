<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	var userCombogrid;
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcCategoryController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				var chiefModeratorId = $("#chiefModeratorId").combobox("getValue");
				if(chiefModeratorId != '' && $("#chiefModeratorId").combobox("getText") == chiefModeratorId) {
					parent.$.messager.alert('错误', '请选择正确的首席版主', 'error');
					isValid = false;
				}
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

		userCombogrid = $('#chiefModeratorId').combogrid({
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

		if(${!empty zcCategory.chiefModeratorId}) {
			userCombogrid.combogrid('grid').datagrid({'queryParams':{q: '${zcCategory.chiefModeratorName}'}});
			userCombogrid.combogrid('setValue', '${zcCategory.chiefModeratorId}');
		}


		userCombogrid.next('span').find('input').focus(function(){
			userCombogrid.combogrid("showPanel");
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value = "${zcCategory.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="10%"><%=TzcCategory.ALIAS_NAME%></th>
					<td width="40%">
						<input name="name" type="text" value="${zcCategory.name}" class="easyui-validatebox span2" data-options="required:true"/>
					</td>
					<th width="10%"><%=TzcCategory.ALIAS_PID%></th>
					<td width="40%">
						<jb:selectSql dataType="SL001" name="pid" value="${zcCategory.pid}"></jb:selectSql>
					</td>
				</tr>
				<tr>
					<th>icon图标</th>
					<td colspan="3">
						<img class="img-preview" src="${zcCategory.icon}" width="50" height="50"/>
						<input type="file" id="iconFile" name="iconFile">
					</td>
				</tr>
				<tr>
					<th valign="top">分类简介</th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="summary">${zcCategory.summary}</textarea>
					</td>
				</tr>
				<tr>
					<th><%=TzcCategory.ALIAS_SEQ%></th>
					<td>
						<input name="seq" value="${zcCategory.seq}" class="easyui-numberspinner"
							   style="width: 140px; height: 29px;" required="required"
							   data-options="editable:true" >
					</td>
					<th>首席版主</th>
					<td>
						<input id="chiefModeratorId" name="chiefModeratorId"/>
					</td>
				</tr>
				<c:if test="${zcCategory.pid == '0'}">
					<tr>
						<th>阅读增量</th>
						<td colspan="3">
							<input name="autoRead" value="${zcCategory.autoRead}" class="easyui-numberspinner"
								   style="width: 140px; height: 29px;" required="required"
								   data-options="editable:true" >
							<span style="color: red;">(*如果输入n则每次增加n次；输入-n则每次增加1-n之间随机次数)</span>
						</td>
					</tr>
				</c:if>
				<tr>
					<th valign="top"><%=TzcCategory.ALIAS_FORUM_INTRODUCE%></th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="forumIntroduce">${zcCategory.forumIntroduce}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>