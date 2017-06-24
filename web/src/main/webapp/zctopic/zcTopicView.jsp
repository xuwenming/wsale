<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcTopicComment" %>
<%@ page import="jb.model.TzcReward" %>
<%@ page import="jb.model.TzcPraise" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>

<!DOCTYPE html>
<html>
<head>
	<title>ZcTopic管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			parent.imageSlide.initImageSlide($('.imageSlide img'));
			$('.imageSlide img').click(function(){
				parent.imageSlide.showImageSlide($(this).index());
			});
			var gridMap = {
				handle:function(obj,clallback){
					if (obj.grid == null) {
						obj.grid = clallback();
					} else {
						obj.grid.datagrid('reload');
					}
				},
				0: {
					invoke: function () {
						parent.imageSlide.initImageSlide($('.imageSlide img'));
						$('.imageSlide img').click(function(){
							parent.imageSlide.showImageSlide($(this).index());
						});
					}, grid: null
				}, 1: {
					invoke: function () {
						gridMap.handle(this,loadCommentDataGrid);
					}, grid: null
				}, 2: {
					invoke: function () {
						gridMap.handle(this,loadRewardDataGrid);
					}, grid: null
				}, 3: {
					invoke: function () {
						gridMap.handle(this,loadPraiseDataGrid);
					}, grid: null
				}
			};
			$('#topic_view_tabs').tabs({
				onSelect: function (title, index) {
					gridMap[index].invoke();
				}
			});
		});

		function loadCommentDataGrid() {
			return $('#commentDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcTopicCommentController/dataGridByTopic?topicId=${zcTopic.id}',
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
					title : '<%=TzcTopicComment.ALIAS_ADDTIME%>',
					width : 50,
					sortable:true
				}, {
					field : 'userName',
					title : '<%=TzcTopicComment.ALIAS_USER_ID%>',
					width : 50
				}, {
					field : 'comment',
					title : '<%=TzcTopicComment.ALIAS_COMMENT%>',
					width : 80
				} ] ]
			});
		}

		function loadRewardDataGrid() {
			return $('#rewardDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcRewardController/dataGridByTopic?objectId=${zcTopic.id}&objectType=TOPIC',
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
					title : '<%=TzcReward.ALIAS_ADDTIME%>',
					width : 80,
					sortable:true
				}, {
					field : 'userName',
					title : '<%=TzcReward.ALIAS_USER_ID%>',
					width : 80
				}, {
					field : 'rewardFee',
					title : '<%=TzcReward.ALIAS_REWARD_FEE%>',
					width : 50,
					sortable:true,
					formatter:function(value){
						return $.fenToYuan(value);
					}
				} ] ]
			});
		}

		function loadPraiseDataGrid() {
			return $('#praiseDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcPraiseController/dataGridByTopic?objectId=${zcTopic.id}&objectType=TOPIC',
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
					title : '<%=TzcPraise.ALIAS_ADDTIME%>',
					width : 80,
					sortable:true
				}, {
					field : 'userName',
					title : '<%=TzcPraise.ALIAS_USER_ID%>',
					width : 80
				} ] ]
			});
		}
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;">
			<div style="font-size: 12pt; padding: 8px;">专题标题：${zcTopic.title}</div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="topic_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
				<div title="基本信息">
					<table class="table table-hover table-condensed" id="topicInfo">
						<tr>
							<th>专题标题</th>
							<td colspan="3">
								${zcTopic.title}
							</td>
							<th width="8%">封面icon</th>
							<td align="left" rowspan="5" class="imageSlide">
								<img src="${zcTopic.icon}" i="${zcTopic.icon}" width="100" height="100"/>
							</td>
						</tr>
						<tr>
							<th width="8%">所属分类</th>
							<td width="20%">
								${zcTopic.categoryName}
							</td>
							<th width="8%">热门排序</th>
							<td width="20%">
								${zcTopic.seq}
							</td>
						</tr>
						<tr>
							<th>阅读数</th>
							<td>
								${zcTopic.topicRead}
							</td>
							<th>点赞数</th>
							<td>
								${zcTopic.topicPraise}
							</td>
						</tr>
						<tr>
							<th>留言数</th>
							<td>
								${zcTopic.topicComment}
							</td>
							<th>打赏数</th>
							<td>
								${zcTopic.topicReward}
							</td>
						</tr>
						<tr>
							<th>发布人</th>
							<td>
								${zcTopic.addUserName}
							</td>
							<th>发布时间</th>
							<td>
								<fmt:formatDate value="${zcTopic.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr>
							<th valign="top">专题内容</th>
							<td colspan="3">
								${zcTopic.content}
							</td>
							<td colspan="2"></td>
						</tr>
					</table>
				</div>
				<div title="留言记录">
					<table id="commentDataGrid"></table>
				</div>
				<div title="打赏记录">
					<table id="rewardDataGrid"></table>
				</div>
				<div title="点赞记录">
					<table id="praiseDataGrid"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>