<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcBestProduct" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcBestProductController/edit',
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

		$('.imageSlide img').simpleSlide();
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post">
			<input type="hidden" name="id" value = "${zcBestProduct.id}"/>
			<input type="hidden" name="productId" value = "${zcBestProduct.productId}"/>
			<input type="hidden" name="addUserId" value = "${zcBestProduct.addUserId}"/>
			<div style="font-size: 16pt; padding: 8px;">基本信息</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">申请用户</th>
					<td width="36%">${zcBestProduct.addUserName}</td>
					<th width="14%">职务</th>
					<td width="36%">
						${user.position}
					</td>
				</tr>
				<tr>
					<th>是否认证</th>
					<td>
						<c:choose>
							<c:when test="${user.isAuth}">是</c:when>
							<c:otherwise>否</c:otherwise>
						</c:choose>
					</td>
					<th>消保金</th>
					<td>${protection}</td>
				</tr>
				<tr>
					<th>信誉</th>
					<td>
						${order_status_count.OS10}
					</td>
					<th>违约</th>
					<td>${order_status_count.S_OS15 + order_status_count.B_OS15}</td>
				</tr>
				<c:choose>
					<c:when test="${utype == 'UT01'}">
						<tr>
							<th><%=TzcBestProduct.ALIAS_START_TIME%></th>
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcBestProduct.FORMAT_START_TIME%>'})" name="startTimeStr"
										value="<fmt:formatDate value="${zcBestProduct.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 140px;"/>
							</td>
							<th><%=TzcBestProduct.ALIAS_END_TIME%></th>
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TzcBestProduct.FORMAT_END_TIME%>'})" name="endTimeStr"
										value="<fmt:formatDate value="${zcBestProduct.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 140px;"/>
							</td>
						</tr>
						<tr>
							<th>店铺排序</th>
							<td>
								<input type="hidden" name="oldShopSeq" value="${zcBestProduct.shopSeq}">
								<input name="shopSeq" value="${zcBestProduct.shopSeq}" class="easyui-numberspinner"
									   style="width: 140px; height: 29px;" required="required"
									   data-options="editable:true">
							</td>
							<th>拍品排序</th>
							<td>
								<input name="productSeq" value="${zcBestProduct.productSeq}" class="easyui-numberspinner"
									   style="width: 140px; height: 29px;" required="required"
									   data-options="editable:true">
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<span style="color: red;">*1、起始时间和结束时间不选则默认一天；2、排序数值越大越靠前</span>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
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
					</c:otherwise>
				</c:choose>
				<tr>
					<th>申请精拍频道</th>
					<td>
						<input type="hidden" name="channel" value = "${zcBestProduct.channel}"/>
						${zcBestProduct.channelZh}
					</td>
					<th>申请时间</th>
					<td>
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
						<input type="hidden" name="auditStatus" value = "${zcBestProduct.auditStatus}"/>
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
						<textarea style="width: 510px;height: 60px;" name="auditRemark">${zcBestProduct.auditRemark}</textarea>
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
					<th>是否包退</th>
					<td>
						${product.approvalDaysZh}
					</td>
					<th>是否包邮</th>
					<td>
						<c:choose>
							<c:when test="${product.isFreeShipping}">包邮</c:when>
							<c:otherwise>不包邮</c:otherwise>
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
		</form>
	</div>
</div>