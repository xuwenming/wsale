<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcForumBbs" %>
<%@ page import="jb.model.TzcBbsLog" %>
<%@ page import="jb.model.TzcBbsComment" %>
<%@ page import="jb.model.TzcBbsReward" %>
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
	<title>user管理</title>
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
				}
			};
			$('#user_view_tabs').tabs({
				onSelect: function (title, index) {
					gridMap[index].invoke();
				}
			});
		});

		function loadCommentDataGrid() {
			return $('#commentDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcBbsCommentController/dataGrid?bbsId=${zcForumBbs.id}',
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
					$('.imageS').simpleSlide();
				}
			});
		}
		function loadRewardDataGrid() {
			return $('#rewardDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcBbsRewardController/dataGrid?bbsId=${zcForumBbs.id}',
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
					sortable:true
				} ] ]
			});
		}
		function loadLogDataGrid() {
			return $('#logDataGrid').datagrid({
				url : '${pageContext.request.contextPath}/zcBbsLogController/dataGrid?bbsId=${zcForumBbs.id}',
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
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;">
			<div style="font-size: 12pt; padding: 8px;">用户昵称：${user.nickname}</div>
		</div>
		<div data-options="region:'center',border:false">
			<div id="user_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
				<div title="基本信息">
					<table class="table table-hover table-condensed" id="bbsInfo">
						<tr>
							<th width="5%">昵称</th>
							<td width="20%">
								${user.nickname}
							</td>
							<th width="5%">性别</th>
							<td width="20%">
								<c:choose>
									<c:when test="${user.sex == 1}">男</c:when>
									<c:when test="${user.sex == 2}">女</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</td>
							<th width="5%">头像</th>
							<td align="left" rowspan="5" class="imageSlide">
								<img src="${user.headImage}" i="${user.headImage}" width="100" height="100"/>
							</td>
						</tr>
						<tr>
							<th>余额</th>
							<td>
								${user.walletAmount}
							</td>
							<th>消保金</th>
							<td>
								${user.protection}
							</td>
						</tr>
						<tr>
							<th>手机号</th>
							<td>
								${user.mobile}
							</td>
							<th>微信号</th>
							<td>
								${user.wechatNo}
							</td>
						</tr>
						<tr>
							<th>地区</th>
							<td>
								${user.area}
							</td>
							<th>联系人</th>
							<td>
								${user.contact}
							</td>
						</tr>
						<tr>
							<th>是否认证</th>
							<td>
								<c:if test="${user.isAuth}">是</c:if>
								<c:if test="${!user.isAuth}">否</c:if>
							</td>
							<th>是否禁言</th>
							<td>
								<c:if test="${user.isGag}">是</c:if>
								<c:if test="${!user.isGag}">否</c:if>
							</td>
						</tr>
						<tr>
							<th>职务</th>
							<td>
								${user.position}
							</td>
							<th>发帖数量</th>
							<td>
								${user.bbsNums}
							</td>
							<th>个人签名</th>
							<td>
								${user.bardian}
							</td>
						</tr>
						<tr>
							<th>粉丝数</th>
							<td>
								${user.fans}
							</td>
							<th>屏蔽数</th>
							<td>
								${user.shieldors}
							</td>
							<th>信誉</th>
							<td>
								${user.credit}
							</td>
						</tr>

					</table>
				</div>
				<div title="地址管理">
					<table ></table>
				</div>
				<div title="信息1">
					<table ></table>
				</div>
				<div title="信息2">
					<table ></table>
				</div>
				<div title="信息3">
					<table ></table>
				</div>

			</div>
		</div>
	</div>
</body>
</html>