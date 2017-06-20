<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcBestProduct" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('.imageSlide img').simpleSlide();
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div style="font-size: 16pt; padding: 8px;">基本信息</div>
		<table class="table table-hover table-condensed">
			<tr>
				<th width="14%">申请用户</th>
				<td width="36%">${zcBestProduct.addUserName}</td>
				<th width="14%">申请精拍频道</th>
				<td width="36%">
					${zcBestProduct.channelZh}
				</td>
			</tr>
			<c:if test="${zcBestProduct.auditStatus == 'AS02'}">
				<tr>
					<th><%=TzcBestProduct.ALIAS_START_TIME%></th>
					<td>
						<fmt:formatDate value="${zcBestProduct.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<th><%=TzcBestProduct.ALIAS_END_TIME%></th>
					<td>
						<fmt:formatDate value="${zcBestProduct.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</c:if>
			<tr>
				<th>申请时间</th>
				<td colspan="3">
					<fmt:formatDate value="${zcBestProduct.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th><%=TzcBestProduct.ALIAS_PAY_STATUS%></th>
				<td>
					${zcBestProduct.payStatusZh}
				</td>
				<th><%=TzcBestProduct.ALIAS_PAYTIME%></th>
				<td>
					<fmt:formatDate value="${zcBestProduct.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th><%=TzcBestProduct.ALIAS_AUDIT_STATUS%></th>
				<td>
					${zcBestProduct.auditStatusZh}
				</td>
				<th><%=TzcBestProduct.ALIAS_AUDIT_USER_ID%></th>
				<td>
					${zcBestProduct.auditUserName}
				</td>
			</tr>
			<tr>
				<th><%=TzcBestProduct.ALIAS_AUDIT_TIME%></th>
				<td colspan="3">
					<fmt:formatDate value="${zcBestProduct.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th><%=TzcBestProduct.ALIAS_AUDIT_REMARK%></th>
				<td colspan="3">
					${zcBestProduct.auditRemark}
				</td>
			</tr>
		</table>

		<div style="font-size: 16pt; padding: 8px;">申请拍品信息</div>
		<table class="table table-hover table-condensed">
			<tr>
				<th width="14%">拍品编号</th>
				<td width="36%">${product.pno}</td>
				<th width="14%">所属分类</th>
				<td width="36%">${product.cname}</td>
			</tr>
			<tr>
				<th>开拍时间</th>
				<td>
					<fmt:formatDate value="${product.startingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>截拍时间</th>
				<td>
					<fmt:formatDate value="${product.realDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th>拍品状态</th>
				<td>
					${product.statusZh}
				</td>
				<th>封存状态</th>
				<td>
					<c:choose>
						<c:when test="${product.isClose}">已封存</c:when>
						<c:otherwise>未封存</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>拍品内容</th>
				<td colspan="3">
					${product.content}
				</td>
			</tr>
			<tr>
				<th>拍品图片</th>
				<td colspan="3" class="imageSlide">
					<c:forEach items="${product.files}" var="file">
						<img src="${file.fileHandleUrl}" i="${file.fileHandleUrl}" style="width: 200px; height: 150px; margin: 1px;"/>
					</c:forEach>
				</td>
			</tr>
		</table>
	</div>
</div>