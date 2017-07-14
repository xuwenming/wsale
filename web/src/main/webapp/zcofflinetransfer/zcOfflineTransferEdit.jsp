<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOfflineTransfer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcOfflineTransferController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');

				var checkPwd = "", checkPwdInp = $('#checkPwdInp').val();
				if(checkPwdInp != '') {
					$.ajax({
						type: "GET",
						url: "${pageContext.request.contextPath}/userController/getPublicKey",
						dataType: "json",
						async : false,
						success:function (data) {
							if(data.success) {
								var encrypt = new JSEncrypt();
								encrypt.setPublicKey(data.obj);
								checkPwd = encrypt.encrypt($('#checkPwdInp').val());
							}
						}
					});
				}
				$('#checkPwd').val(checkPwd);

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
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post">
			<input type="hidden" name="id" value = "${zcOfflineTransfer.id}"/>
			<input type="hidden" name="isWallet" value = "${zcOfflineTransfer.isWallet}"/>
			<input type="hidden" name="userId" value = "${zcOfflineTransfer.userId}"/>
			<input type="hidden" name="oldHandleStatus" value = "${zcOfflineTransfer.handleStatus}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">转账用户</th>
					<td width="36%">${zcOfflineTransfer.user.nickname}</td>
					<th width="14%">手机号</th>
					<td width="36%">${zcOfflineTransfer.user.mobile}</td>
				</tr>
				<tr>
					<th>汇款人姓名</th>
					<td>
						${zcOfflineTransfer.transferUserName}
					</td>
					<th>汇款金额</th>
					<td>
						<c:choose>
							<c:when test="${zcOfflineTransfer.handleStatus == 'HS03'}">${zcOfflineTransfer.transferAmount}</c:when>
							<c:otherwise><input class="span2" name="transferAmount" type="text" value="${zcOfflineTransfer.transferAmount}"/></c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>汇款时间</th>
					<td colspan="3">
						<fmt:formatDate value="${zcOfflineTransfer.transferTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<th>汇款备注</th>
					<td colspan="3">
						${zcOfflineTransfer.remark}
					</td>
				</tr>
				<tr>
					<th>处理状态</th>
					<td colspan="3">
						<c:choose>
							<c:when test="${zcOfflineTransfer.handleStatus == 'HS03'}">处理成功</c:when>
							<c:otherwise><jb:select dataType="HS" name="handleStatus" value="${zcOfflineTransfer.handleStatus}"></jb:select></c:otherwise>
						</c:choose>
						<c:if test="${zcOfflineTransfer.isWallet}">
							<span style="color: red;">(*已充值到用户余额)</span>
						</c:if>
					</td>
				</tr>
				<c:if test="${zcOfflineTransfer.handleStatus != 'HS01'}">
					<tr>
						<th>处理人</th>
						<td>
							${zcOfflineTransfer.handleUserName}
						</td>
						<th>处理时间</th>
						<td>
							<fmt:formatDate value="${zcOfflineTransfer.handleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<th>处理结果</th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="handleRemark">${zcOfflineTransfer.handleRemark}</textarea>
					</td>
				</tr>
				<tr>
					<th>校验密码<font color="red" id="msg">*</font></th>
					<td colspan="3">
						<input id="checkPwd" name="checkPwd" type="hidden" />
						<input id="checkPwdInp" type="password" class="easyui-validatebox span2" data-options="required:true" maxlength="20"/>
					</td>
				</tr>

			</table>				
		</form>
	</div>
</div>