<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcForumBbs" %>
<%@ page import="jb.model.TzcBbsLog" %>
<%@ page import="jb.model.TzcBbsComment" %>
<%@ page import="jb.model.TzcBbsReward" %>
<%@ page import="jb.model.TzcShareRecord" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<%
	request.setAttribute("vEnter", "\n");
%>

<!DOCTYPE html>
<html>
<head>
	<title>ZcForumBbs管理</title>
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
						gridMap.handle(this,loadLogDataGrid);
					}, grid: null
				}, 4: {
					invoke: function () {
						gridMap.handle(this,loadShareDataGrid);
					}, grid: null
				}
			};
			$('#bbs_view_tabs').tabs({
				onSelect: function (title, index) {
					gridMap[index].invoke();
				}
			});
		});

		function loadCommentDataGrid() {
			return $('#commentDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcBbsCommentController/dataGridByBbs?bbsId=${zcForumBbs.id}',
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
					title : '<%=TzcBbsComment.ALIAS_ADDTIME%>',
					width : 50,
					sortable : true
				}, {
					field : 'userName',
					title : '<%=TzcBbsComment.ALIAS_USER_ID%>',
					width : 50
				}, {
					field : 'comment',
					title : '<%=TzcBbsComment.ALIAS_COMMENT%>',
					width : 100,
					formatter : function(value, row, index) {
						var str = "";
						if(value && row.ctype == 'IMAGE'){
							str = "<img class=\"imageS\" style=\"height: 60px;width: 80px;\" src=\""+value+"\" i=\""+value+"\" />";
						} else str = value;
						return str;
					}
				} ] ],
				onLoadSuccess : function() {
					parent.imageSlide.initImageSlide($('.imageS'));
					$('.imageS').click(function(){
						parent.imageSlide.showImageSlide($(this).index());
					});
				}
			});
		}
		function loadRewardDataGrid() {
			return $('#rewardDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcRewardController/dataGridByBbs?objectId=${zcForumBbs.id}&objectType=BBS',
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
					title : '<%=TzcBbsReward.ALIAS_ADDTIME%>',
					width : 80,
					sortable:true
				}, {
					field : 'userName',
					title : '<%=TzcBbsReward.ALIAS_USER_ID%>',
					width : 80
				}, {
					field : 'rewardFee',
					title : '<%=TzcBbsReward.ALIAS_REWARD_FEE%>',
					width : 50,
					sortable:true,
					formatter:function(value){
						return $.fenToYuan(value);
					}
				} ] ]
			});
		}
		function loadLogDataGrid() {
			return $('#logDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcBbsLogController/dataGridByBbs?bbsId=${zcForumBbs.id}',
				fitColumns: true,
				fit:true,
				border: false,
				pagination: true,
				idField: 'id',
				pageSize: 10,
				pageList: [10, 20, 30, 40, 50],
				sortName: 'addtime',
				sortOrder: 'desc',
				checkOnSelect: false,
				selectOnCheck: false,
				nowrap: false,
				striped: true,
				rownumbers: true,
				singleSelect: true,
				columns : [ [ {
					field : 'id',
					title : '编号',
					width : 150,
					hidden : true
				}, {
					field : 'addtime',
					title : '<%=TzcBbsLog.ALIAS_ADDTIME%>',
					width : 50,
					sortable : true
				}, {
					field : 'userName',
					title : '<%=TzcBbsLog.ALIAS_USER_ID%>',
					width : 50
				}, {
					field : 'logTypeZh',
					title : '<%=TzcBbsLog.ALIAS_LOG_TYPE%>',
					width : 50
				}, {
					field : 'content',
					title : '<%=TzcBbsLog.ALIAS_CONTENT%>',
					width : 100
				} ] ],
				toolbar: [{
					text:'查询',
					iconCls: 'brick_add',
					handler: function(){
						$('#logDataGrid').datagrid('load', $.serializeObject($('#logSearchForm')));
					}
				},'-',{
					text:'清空条件',
					iconCls: 'brick_delete',
					handler: function(){
						$('#logSearchForm input').val('');
						$('#logDataGrid').datagrid('load', {});
					}
				}]

			});
		}
		function loadShareDataGrid() {
			return $('#shareDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcShareRecordController/dataGridByBbs?bbsId=${zcForumBbs.id}',
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
					title : '<%=TzcShareRecord.ALIAS_ADDTIME%>',
					width : 80,
					sortable:true
				}, {
					field : 'userName',
					title : '<%=TzcShareRecord.ALIAS_USER_ID%>',
					width : 80
				}, {
					field : 'shareChannelZh',
					title : '<%=TzcShareRecord.ALIAS_SHARE_CHANNEL%>',
					width : 80
				} ] ]
			});
		}
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;">
			<div style="font-size: 12pt; padding: 8px;">帖子标题：${zcForumBbs.bbsTitle}</div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="bbs_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
				<div title="基本信息">
					<table class="table table-hover table-condensed" id="bbsInfo">
						<tr>
							<th width="12%"><%=TzcForumBbs.ALIAS_BBS_TYPE%></th>
							<td width="38%">
								${zcForumBbs.bbsTypeZh}
							</td>
							<th width="12%">所属分类</th>
							<td width="38%">
								${categoryName}
							</td>
						</tr>
						<tr>
							<th><%=TzcForumBbs.ALIAS_BBS_TITLE%></th>
							<td colspan="3">
								${zcForumBbs.bbsTitle}
							</td>
						</tr>
						<tr>
							<th valign="top"><%=TzcForumBbs.ALIAS_BBS_CONTENT%></th>
							<td colspan="3">
								${fn:replace(zcForumBbs.bbsContent, vEnter, '<br>')}
							</td>
						</tr>
						<tr>
							<th><%=TzcForumBbs.ALIAS_IS_OFF_REPLY%></th>
							<td>
								${zcForumBbs.isOffReplyZh}
							</td>
							<th><%=TzcForumBbs.ALIAS_IS_TOP%></th>
							<td>
								${zcForumBbs.isTopZh}
							</td>
						</tr>
						<tr>
							<th><%=TzcForumBbs.ALIAS_IS_LIGHT%></th>
							<td>
								${zcForumBbs.isLightZh}
							</td>
							<th><%=TzcForumBbs.ALIAS_IS_ESSENCE%></th>
							<td>
								${zcForumBbs.isEssenceZh}
							</td>
						</tr>
						<tr>
							<th>状态</th>
							<td>
								${zcForumBbs.bbsStatusZh}
							</td>
							<th><%=TzcForumBbs.ALIAS_UPDATETIME%></th>
							<td>
								<fmt:formatDate value="${zcForumBbs.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr>
							<th>发帖人</th>
							<td>
								${zcForumBbs.addUserName}
							</td>
							<th><%=TzcForumBbs.ALIAS_ADDTIME%></th>
							<td>
								<fmt:formatDate value="${zcForumBbs.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr>
							<th><%=TzcForumBbs.ALIAS_BBS_COMMENT%></th>
							<td>
								${zcForumBbs.bbsComment}
							</td>
							<th><%=TzcForumBbs.ALIAS_BBS_READ%></th>
							<td>
								${zcForumBbs.bbsRead}
							</td>
						</tr>
						<tr>
							<th><%=TzcForumBbs.ALIAS_BBS_REWARD%></th>
							<td>
								${zcForumBbs.bbsReward}
							</td>
							<th><%=TzcForumBbs.ALIAS_BBS_SHARE%></th>
							<td>
								${zcForumBbs.bbsShare}
							</td>
						</tr>
						<c:if test="${zcForumBbs.bbsType == 'BT03'}">
							<tr>
								<th><%=TzcForumBbs.ALIAS_BBS_LISTEN%></th>
								<td colspan="3">
										${zcForumBbs.bbsListen}
								</td>
							</tr>
							<tr>
								<th>音频下载</th>
								<td colspan="3">
									<c:forEach items="${zcForumBbs.voiceFiles}" var="voice" varStatus="vs">
										<a href="${pageContext.request.contextPath}/fileController/download?filePath=${voice.fileHandleUrl}">语音${vs.index+1}</a>
										&nbsp;&nbsp;
									</c:forEach>
								</td>
							</tr>
						</c:if>
						<tr>
							<th valign="top">图片预览</th>
							<td colspan="3" class="imageSlide">
								<c:forEach items="${zcForumBbs.files}" var="image">
									<img src="${image.fileHandleUrl}" i="${image.fileHandleUrl}" style="width:200px; height:150px; margin: 1px;"/>
								</c:forEach>
							</td>
						</tr>

					</table>
				</div>
				<div title="评论信息">
					<table id="commentDataGrid"></table>
				</div>
				<div title="打赏信息">
					<table id="rewardDataGrid"></table>
				</div>
				<div title="日志信息">
					<div class="easyui-layout" data-options="fit : true,border : false">
						<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
							<form id="logSearchForm">
								<table class="table table-hover table-condensed">
									<td>
										<%=TzcBbsLog.ALIAS_CONTENT%>：
										<input type="text" name="content" maxlength="512" class="span2"/>
									</td>
									<td>
										<%=TzcBbsLog.ALIAS_LOG_TYPE%>：
										<jb:select dataType="BL" name="logType"></jb:select>
									</td>
								</table>
							</form>
						</div>
						<div data-options="region:'center',border:false">
							<table id="logDataGrid"></table>
						</div>
					</div>
				</div>
				<div title="转发记录">
					<table id="shareDataGrid"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>