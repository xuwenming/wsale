<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcForumBbs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcForumBbsController/edit',
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
			},
			value : '${zcForumBbs.categoryId}'
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${zcForumBbs.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="12%"><%=TzcForumBbs.ALIAS_BBS_TYPE%></th>
					<td width="38%">
						${zcForumBbs.bbsTypeZh}
					</td>
					<th width="12%">所属分类</th>
					<td width="38%">
						<select id="categoryId" name="categoryId" style="width: 140px; height: 29px;"></select>
					</td>
				</tr>
				<tr>
					<th>是否首页推荐</th>
					<td>
						<select name="isHomeHot" class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" <c:if test="${!zcForumBbs.isHomeHot}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${zcForumBbs.isHomeHot}">selected="selected"</c:if>>是</option>
						</select>
					</td>
					<th>首页推荐排序</th>
					<td>
						<input name="seq" value="${zcForumBbs.seq}" class="easyui-numberspinner"
							   style="width: 140px; height: 29px;" data-options="editable:true" >
						<span style="color: red;">(*数值越大越靠前)</span>
					</td>
				</tr>
				<tr>
					<th><%=TzcForumBbs.ALIAS_BBS_TITLE%></th>
					<td colspan="3">
						<input value="${zcForumBbs.bbsTitle}" class="easyui-validatebox span2" data-options="required:true" name="bbsTitle" type="text" style="width: 510px;"/>
					</td>
				</tr>
				<tr>
					<th><%=TzcForumBbs.ALIAS_BBS_CONTENT%></th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="bbsContent" class="easyui-validatebox" data-options="required:true">${zcForumBbs.bbsContent}</textarea>
					</td>
				</tr>
				<!--<tr>
					<th><%=TzcForumBbs.ALIAS_IS_OFF_REPLY%></th>
					<td>
						<select name="isOffReply" class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" <c:if test="${!zcForumBbs.isOffReply}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${zcForumBbs.isOffReply}">selected="selected"</c:if>>是</option>
						</select>
					</td>
					<th><%=TzcForumBbs.ALIAS_IS_TOP%></th>
					<td>
						<select name="isTop" class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" <c:if test="${!zcForumBbs.isTop}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${zcForumBbs.isTop}">selected="selected"</c:if>>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><%=TzcForumBbs.ALIAS_IS_LIGHT%></th>
					<td>
						<select name="isLight" class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" <c:if test="${!zcForumBbs.isLight}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${zcForumBbs.isLight}">selected="selected"</c:if>>是</option>
						</select>
					</td>
					<th><%=TzcForumBbs.ALIAS_IS_ESSENCE%></th>
					<td>
						<select name="isEssence" class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" <c:if test="${!zcForumBbs.isEssence}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${zcForumBbs.isEssence}">selected="selected"</c:if>>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>状态</th>
					<td colspan="3">
						<jb:select dataType="BS" name="bbsStatus" value="${zcForumBbs.bbsStatus}"></jb:select>
					</td>
				</tr>-->


			</table>				
		</form>
	</div>
</div>