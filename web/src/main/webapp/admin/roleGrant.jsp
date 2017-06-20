<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var resourceTree;
	var loadFlg = false;
	var parentFlg = true;
	var childFlg = true;
	$(function() {
		resourceTree = $('#resourceTree').tree({
			url : '${pageContext.request.contextPath}/resourceController/allTree',
			parentField : 'pid',
			//lines : true,
			checkbox : true,
			cascadeCheck : false,
			onLoadSuccess : function(node, data) {
				var ids = $.stringToList('${role.resourceIds}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						if (resourceTree.tree('find', ids[i])) {
							resourceTree.tree('check', resourceTree.tree('find', ids[i]).target);
						}
					}
				}
				$('#roleGrantLayout').layout('panel', 'west').panel('setTitle', $.formatString('[{0}]角色可以访问的资源', '${role.name}'));
				parent.$.messager.progress('close');
				loadFlg = true;
			},
			onCheck: function(node, checked) {
				if(loadFlg) {
					var childrenNodes = $(this).tree('getChildren', node.target);
					var parentNodes = $(this).tree('getParent', node.target);
					if(parentNodes != null && childrenNodes.length == 0) {
						if(childFlg) {
							if(checked) {
								parentFlg = false;
								$(this).tree('check', parentNodes.target);
							} else {
								childrenNodes = $(this).tree('getChildren', parentNodes.target);
								var flag = false;
								for(var i=0; i<childrenNodes.length; i++) {
									if(childrenNodes[i].checked){
										flag = true;
										break;
									}
								}
								if(!flag) {
									parentFlg = false;
									$(this).tree('uncheck', parentNodes.target);
								}
							}
						}
						childFlg = true;
					} else if(parentNodes == null && childrenNodes.length > 0) {
						childFlg = false;
						if(parentFlg) {
							for(var i=0; i<childrenNodes.length; i++) {
								if(checked) {
									$(this).tree('check', childrenNodes[i].target);
								} else {
									$(this).tree('uncheck', childrenNodes[i].target);
								}

							}
						}
						parentFlg = true;
					}
				}
			}

		});

		$('#form').form({
			url : '${pageContext.request.contextPath}/roleController/grant',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				var checknodes = resourceTree.tree('getChecked');
				var ids = [];
				if (checknodes && checknodes.length > 0) {
					for ( var i = 0; i < checknodes.length; i++) {
						ids.push(checknodes[i].id);
					}
				}
				$('#resourceIds').val(ids);
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为role.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				}
			}
		});
	});

	function checkAll() {
		var nodes = resourceTree.tree('getChecked', 'unchecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceTree.tree('check', nodes[i].target);
			}
		}
	}
	function uncheckAll() {
		var nodes = resourceTree.tree('getChecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceTree.tree('uncheck', nodes[i].target);
			}
		}
	}
	function checkInverse() {
		var unchecknodes = resourceTree.tree('getChecked', 'unchecked');
		var checknodes = resourceTree.tree('getChecked');
		if (unchecknodes && unchecknodes.length > 0) {
			for ( var i = 0; i < unchecknodes.length; i++) {
				resourceTree.tree('check', unchecknodes[i].target);
			}
		}
		if (checknodes && checknodes.length > 0) {
			for ( var i = 0; i < checknodes.length; i++) {
				resourceTree.tree('uncheck', checknodes[i].target);
			}
		}
	}
</script>
<div id="roleGrantLayout" class="easyui-layout"
	data-options="fit:true,border:false">
	<div data-options="region:'west'" title="系统资源"
		style="width: 400px; padding: 1px;">
		<div class="well well-small">
			<form id="form" method="post">
				<input name="id" type="hidden" class="span2" value="${role.id}"
					readonly="readonly">
				<ul id="resourceTree"></ul>
				<input id="resourceIds" name="resourceIds" type="hidden" />
			</form>
		</div>
	</div>
	<div data-options="region:'center'" title=""
		style="overflow: hidden; padding: 10px;">
		<div class="well well-small">
			<span class="label label-success">${role.name}</span>
			<div>${role.remark}</div>
		</div>
		<div class="well well-large">
			<button class="btn btn-success" onclick="checkAll();">全选</button>
			<br /> <br />
			<button class="btn btn-warning" onclick="checkInverse();">反选</button>
			<br /> <br />
			<button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
		</div>
	</div>
</div>