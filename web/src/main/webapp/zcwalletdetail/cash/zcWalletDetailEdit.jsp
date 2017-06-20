<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcWalletDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcWalletDetailController/edit',
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
			<input type="hidden" name="id" value = "${zcWalletDetail.id}"/>
			<input type="hidden" name="handleStatus" value = "${zcWalletDetail.handleStatus}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">交易号</th>
					<td width="36%">${zcWalletDetail.orderNo}</td>
					<th width="14%">申请时间</th>
					<td width="36%"><fmt:formatDate value="${zcWalletDetail.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<th>提现到</th>
					<td>
						${zcWalletDetail.channelZh}
					</td>
					<th>提现金额</th>
					<td>
						${zcWalletDetail.amount}
					</td>
				</tr>
				<c:if test="${zcWalletDetail.channel == 'CS03'}">
					<tr>
						<th>开户名</th>
						<td>
							${zcWalletDetail.bankAccount}
						</td>
						<th>手机号</th>
						<td>
							${zcWalletDetail.bankPhone}
						</td>
					</tr>
					<tr>
						<th>身份证</th>
						<td>
							${zcWalletDetail.bankIdNo}
						</td>
						<th>银行卡号</th>
						<td>
							${zcWalletDetail.bankCard}
						</td>
					</tr>
				</c:if>
				<!--<tr>
					<th>处理状态</th>
					<td colspan="3">
						<jb:select dataType="HS" name="handleStatus" value="${zcWalletDetail.handleStatus}"></jb:select>
					</td>
				</tr>-->
				<tr>
					<th>处理结果</th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="handleRemark">${zcWalletDetail.handleRemark}</textarea>
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