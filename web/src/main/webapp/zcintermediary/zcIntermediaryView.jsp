<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcIntermediary" %>
<%@ page import="jb.model.TzcForumBbs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<title>ZcForumBbs管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			parent.imageSlide.initImageSlide($('.imageSlide img'));
			$('.imageSlide img').click(function(){
				parent.imageSlide.showImageSlide($(this).index());
			});
			$('.moneyFormat').each(function(){
				$(this).text($.fenToYuan($(this).text().trim()));
			});
		});

		function viewOrder() {
			parent.$.modalDialog({
				title : '订单详情',
				width : 780,
				height : 500,
				href : '${pageContext.request.contextPath}/zcOrderController/viewByIM?productId=${zcIntermediary.id}'
			});
		}
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div style="font-size: 16pt; padding: 8px;">
			基本信息
		</div>
		<table class="table table-hover table-condensed">
			<tr>
				<th width="10%"><%=TzcIntermediary.ALIAS_IM_NO%></th>
				<td width="40%">
					${zcIntermediary.imNo}
				</td>
				<th width="10%"><%=TzcIntermediary.ALIAS_AMOUNT%></th>
				<td width="40%" class="moneyFormat">
					${zcIntermediary.amount}
				</td>
			</tr>
			<tr>
				<th><%=TzcIntermediary.ALIAS_SELL_USER_ID%></th>
				<td>
					${zcIntermediary.sellerUserName}
				</td>
				<th><%=TzcIntermediary.ALIAS_USER_ID%></th>
				<td>
					${zcIntermediary.buyerUserName}
				</td>
			</tr>
			<tr>
				<th><%=TzcIntermediary.ALIAS_ADDTIME%></th>
				<td>
					<fmt:formatDate value="${zcIntermediary.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>

				</td>
				<th><%=TzcIntermediary.ALIAS_STATUS%></th>
				<td>
					${zcIntermediary.statusZh}
					<c:if test="${zcIntermediary.status == 'IS02'}">
						<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="viewOrder();">查看订单</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th><%=TzcIntermediary.ALIAS_REMARK%></th>
				<td colspan="3">
					${zcIntermediary.remark}
				</td>
			</tr>
		</table>
		<div style="font-size: 16pt; padding: 8px;">
			历史记录
		</div>
		<table class="table table-hover table-condensed">
			<c:forEach items="${zcIntermediary.logs}" var="log">
				<tr>
					<td>
						<fmt:formatDate value="${log.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						<c:choose>
							<c:when test="${log.logType == 'IL01' || log.logType == 'IL04'}">买家 <font color="blue">${zcIntermediary.buyerUserName}</font></c:when>
							<c:otherwise>卖家 <font color="blue">${zcIntermediary.sellerUserName}</font></c:otherwise>
						</c:choose>
						${log.logTypeZh}
						<c:if test="${!empty log.content}">
							- ${log.content}
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>

		<div style="font-size: 16pt; padding: 8px;">帖子信息</div>
		<table class="table table-hover table-condensed" id="bbsInfo">
			<tr>
				<th width="12%"><%=TzcForumBbs.ALIAS_BBS_TYPE%></th>
				<td width="38%">
					${zcIntermediary.bbs.bbsTypeZh}
				</td>
				<th width="12%">所属分类</th>
				<td width="38%">
					${zcIntermediary.bbs.categoryName}
				</td>
			</tr>
			<tr>
				<th><%=TzcForumBbs.ALIAS_BBS_TITLE%></th>
				<td colspan="3">
					${zcIntermediary.bbs.bbsTitle}
				</td>
			</tr>
			<tr>
				<th valign="top"><%=TzcForumBbs.ALIAS_BBS_CONTENT%></th>
				<td colspan="3">
					${zcIntermediary.bbs.bbsContent}
				</td>
			</tr>
			<tr>
				<th><%=TzcForumBbs.ALIAS_IS_OFF_REPLY%></th>
				<td>
					${zcIntermediary.bbs.isOffReplyZh}
				</td>
				<th><%=TzcForumBbs.ALIAS_IS_TOP%></th>
				<td>
					${zcIntermediary.bbs.isTopZh}
				</td>
			</tr>
			<tr>
				<th><%=TzcForumBbs.ALIAS_IS_LIGHT%></th>
				<td>
					${zcIntermediary.bbs.isLightZh}
				</td>
				<th><%=TzcForumBbs.ALIAS_IS_ESSENCE%></th>
				<td>
					${zcIntermediary.bbs.isEssenceZh}
				</td>
			</tr>
			<tr>
				<th>状态</th>
				<td>
					${zcIntermediary.bbs.bbsStatusZh}
				</td>
				<th><%=TzcForumBbs.ALIAS_UPDATETIME%></th>
				<td>
					<fmt:formatDate value="${zcIntermediary.bbs.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th>发帖人</th>
				<td>
					${zcIntermediary.bbs.addUserName}
				</td>
				<th><%=TzcForumBbs.ALIAS_ADDTIME%></th>
				<td>
					<fmt:formatDate value="${zcIntermediary.bbs.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th><%=TzcForumBbs.ALIAS_BBS_COMMENT%></th>
				<td>
					${zcIntermediary.bbs.bbsComment}
				</td>
				<th><%=TzcForumBbs.ALIAS_BBS_READ%></th>
				<td>
					${zcIntermediary.bbs.bbsRead}
				</td>
			</tr>
			<tr>
				<th><%=TzcForumBbs.ALIAS_BBS_REWARD%></th>
				<td>
					${zcIntermediary.bbs.bbsReward}
				</td>
				<th><%=TzcForumBbs.ALIAS_BBS_SHARE%></th>
				<td>
					${zcIntermediary.bbs.bbsShare}
				</td>
			</tr>
			<c:if test="${zcIntermediary.bbs.bbsType == 'BT03'}">
				<tr>
					<th><%=TzcForumBbs.ALIAS_BBS_LISTEN%></th>
					<td colspan="3">
							${zcIntermediary.bbs.bbsListen}
					</td>
				</tr>
				<tr>
					<th>音频下载</th>
					<td colspan="3">
						<c:forEach items="${zcIntermediary.bbs.voiceFiles}" var="voice" varStatus="vs">
							<a href="${pageContext.request.contextPath}/fileController/download?filePath=${voice.fileHandleUrl}">语音${vs.index+1}</a>
							&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>
			</c:if>
			<tr>
				<th valign="top">图片预览</th>
				<td colspan="3" class="imageSlide">
					<c:forEach items="${zcIntermediary.bbs.files}" var="image">
						<img src="${image.fileHandleUrl}" i="${image.fileHandleUrl}" style="width:200px; height:150px; margin: 1px;"/>
					</c:forEach>
				</td>
			</tr>

		</table>
	</div>
</div>
</body>
</html>