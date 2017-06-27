<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcChatMsg" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>ZcChatMsg管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var base = '${pageContext.request.contextPath}/';
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/strophe.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/websdk-1.1.2.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/emoji.config.js" charset="utf-8"></script>

<c:if test="${fn:contains(sessionInfo.resourceList, '/zcChatMsgController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcChatMsgController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/zcChatMsgController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid, fromUserCombogrid, toUserCombogrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/zcChatMsgController/dataGrid',
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
				title : '<%=TzcChatMsg.ALIAS_ADDTIME%>',
				width : 50,
				sortable : true
				}, {
				field : 'content',
				title : '<%=TzcChatMsg.ALIAS_CONTENT%>',
				width : 50,
				formatter : function (value, row, index) {
					if(row.mtype == 'TEXT') {
						return WebIM.utils.parseEmoji(row.content.replace(/[\r\n]/g, "<br/>"));
					} else if(row.mtype == 'IMAGE') {
						return "<img class=\"imageS\" style=\"height: 60px;width: 80px;\" src=\""+value+"\" i=\""+value+"\" />";
					} else if(row.mtype == 'AUDIO') {
						return '<a onclick="playAudio(\'' + row.content + '\')">播放语音</a>';
					} else if(row.mtype == 'PRODUCT') {
						return '<a onclick="viewProduct(\'' + row.content + '\')">查看拍品</a>';
					} else if(row.mtype == 'BBS') {
						return '<a onclick="viewBbs(\'' + row.content + '\')">查看帖子</a>';
					}
					return row.content;
				}
				}, {
				field : 'mtype',
				title : '<%=TzcChatMsg.ALIAS_MTYPE%>',
				width : 50,
				formatter : function (value, row, index) {
					var str = "";
					if(row.mtype == 'TEXT') {
						str = '文本';
					} else if(row.mtype == 'IMAGE') {
						str = '图片';
					} else if(row.mtype == 'AUDIO') {
						str = '语音';
					} else if(row.mtype == 'PRODUCT') {
						str = '拍品';
					} else if(row.mtype == 'BBS') {
						str = '帖子';
					}
					return str;
				}
				}, {
				field : 'fromUserName',
				title : '<%=TzcChatMsg.ALIAS_FROM_USER_ID%>',
				width : 50		
				}, {
				field : 'toUserName',
				title : '<%=TzcChatMsg.ALIAS_TO_USER_ID%>',
				width : 50		
				}, {
				field : 'unread',
				title : '状态',
				width : 50,
				formatter : function (value, row, index) {
					if(row.unread) return '未读';
					else return '已读';
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');

				parent.imageSlide.initImageSlide($('.imageS'));
				$('.imageS').click(function(){
					parent.imageSlide.showImageSlide($(this).index());
				});
			}
		});

		fromUserCombogrid = $('#fromUserId').combogrid({
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

		fromUserCombogrid.next('span').find('input').focus(function(){
			fromUserCombogrid.combogrid("showPanel");
		});

		toUserCombogrid = $('#toUserId').combogrid({
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

		toUserCombogrid.next('span').find('input').focus(function(){
			toUserCombogrid.combogrid("showPanel");
		});
	});

	var audio = document.getElementById("audio");
	function playAudio(src) {
		if(!audio)
			audio = document.createElement('audio');
		audio.src = src;
		audio.play();
		audio.onended = function() {
			audio.src = '';
		};
	}

	function viewProduct(id) {
		var href = '${pageContext.request.contextPath}/zcProductController/view?id=' + id;
		parent.$("#index_tabs").tabs('add', {
			title : '私信-拍品详情',
			content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
			closable : true
		});
	}

	function viewBbs(id) {
		var href = '${pageContext.request.contextPath}/zcForumBbsController/view?id=' + id;
		parent.$("#index_tabs").tabs('add', {
			title : '私信-帖子详情',
			content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
			closable : true
		});
	}

	function deleteFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/zcChatMsgController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcChatMsgController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcChatMsgController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/zcChatMsgController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	function downloadTable(){
		var options = dataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/zcChatMsgController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>	
							<td>
								<%=TzcChatMsg.ALIAS_MTYPE%>：
								<select name="mtype" class="easyui-combobox"
										data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="">全部</option>
									<option value="TEXT">文本</option>
									<option value="IMAGE">图片</option>
									<option value="AUDIO">语音</option>
									<option value="PRODUCT">拍品</option>
									<option value="BBS">帖子</option>
								</select>
							</td>
							<td>
								<%=TzcChatMsg.ALIAS_CONTENT%>：
								<input type="text" name="content" class="span2"/>
							</td>
							<td>
								<%=TzcChatMsg.ALIAS_FROM_USER_ID%>：
								<input id="fromUserId" name="fromUserId"/>
							</td>
							<td>
								<%=TzcChatMsg.ALIAS_TO_USER_ID%>：
								<input id="toUserId" name="toUserId"/>
							</td>
						</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/zcChatMsgController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
		<audio id="audio"></audio>
	</div>	
</body>
</html>