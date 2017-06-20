<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var editor;
	$(function() {
		window.setTimeout(function() {
			editor = KindEditor.create('#note', {
				width : '670px',
				height : '370px',
				items : [ 'fullscreen', 'preview', 'print' ]
			});
			//editor.readonly();

			parent.$.messager.progress('close');
		}, 1);
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
			<tr>
				<th>编号</th>
				<td colspan="3">${bug.id}</td>
			</tr>
			<tr>
				<th>系统类型</th>
				<td>${bug.systemType}</td>
				<th>手机型号</th>
				<td>${bug.phoneModel}</td>
			</tr>
			<tr>
				<th width="10%">BUG类型</th>
				<td width="40%">${bug.typeName}</td>
				<th width="10%">下载文件</th>
				<td width="40%">
					<a href="${pageContext.request.contextPath}/fileController/download?filePath=${bug.filePath}">点击下载</a>
				</td>
			</tr>
			<tr>
				<th>BUG描述</th>
				<td colspan="3"><textarea name="note" id="note" cols="50"
						rows="5" style="visibility: hidden;">${bug.note}</textarea></td>
			</tr>
		</table>
	</div>
</div>