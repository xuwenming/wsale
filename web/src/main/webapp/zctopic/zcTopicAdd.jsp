<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcTopic" %>
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
			url : '${pageContext.request.contextPath}/zcTopicController/add',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');

				if($("#iconFile").val() == '') {
					alert("请选择封面");
					isValid = false;
				}
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

		function ProcessFile() {
			var file = document.getElementById('iconFile').files[0];
			if (file) {
				var reader = new FileReader();
				reader.onload = function ( event ) {
					var txt = event.target.result;
					$('.img-preview').show().attr({'src':txt, 'i':txt});
				};
			}
			reader.readAsDataURL(file);
		}
		$(document).delegate('#iconFile','change',function () {
			ProcessFile();
		});
	});

	$('#categoryId').combotree({
		url : '${pageContext.request.contextPath}/zcCategoryController/tree',
		parentField : 'pid',
		textFiled : 'name',
		lines : true,
		panelHeight : 'auto',
		required:true,
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">	
		<form id="form" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TzcTopic.ALIAS_TITLE%></th>
					<td colspan="3">
						<input class="easyui-validatebox span2" name="title" type="text" data-options="required:true" style="width: 500px;" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<th>所属分类</th>
					<td colspan="3">
						<select id="categoryId" name="categoryId" style="width: 140px; height: 29px;"></select>
					</td>
				</tr>
				<tr>
					<th><%=TzcTopic.ALIAS_ICON%></th>
					<td colspan="3">
						<img class="img-preview" src="" width="80" height="80" style="display: none;"/>
						<input type="file" id="iconFile" name="iconFile">
					</td>
				</tr>
				<!--<tr>
					<th><%=TzcTopic.ALIAS_SEQ%></th>
					<td colspan="3">
						<input name="seq" value="0" class="easyui-numberspinner"
							   style="width: 140px; height: 29px;" required="required"
							   data-options="editable:true">
					</td>
				</tr>-->
				<tr>
					<th valign="top"><%=TzcTopic.ALIAS_CONTENT%></th>
					<td colspan="3">
						<textarea name="content" id="content" style="height:180px;visibility:hidden;"></textarea>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>