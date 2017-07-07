<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcNotice" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	var editor;
	$(function() {
		window.setTimeout(function() {
			editor = KindEditor.create('#content', {
				width : '580px',
				height : '300px',
				items : ['undo', 'redo', '|', 'removeformat', 'hr', 'lineheight', 'link', 'unlink', '|', 'image', 'multiimage', 'media', '|', 'fullscreen', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent'],
				uploadJson : '${pageContext.request.contextPath}/fileController/upload',
				fileManagerJson : '${pageContext.request.contextPath}/fileController/fileManage',
				allowFileManager : true
			});
		}, 1);
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcNoticeController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if($.trim(editor.html()) == '') {
					alert("专题内容不能为空");
					isValid = false;
				}
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				editor.sync();
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
			<input type="hidden" name="id" value = "${zcNotice.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TzcNotice.ALIAS_STATUS%></th>
					<td colspan="3">
						<jb:select dataType="ST" name="status" value="${zcNotice.status}"></jb:select>
					</td>
				</tr>
				<tr>
					<th valign="top"><%=TzcNotice.ALIAS_CONTENT%></th>
					<td colspan="3">
						<textarea name="content" id="content" style="height:180px;visibility:hidden;">${zcNotice.content}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>