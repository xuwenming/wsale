<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcAuthentication" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcAuthenticationController/edit',
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
			<input type="hidden" name="id" value = "${zcAuthentication.id}"/>
			<div style="font-size: 16pt; padding: 8px;">基本信息</div>
			<table class="table table-hover table-condensed">
				<tr>	
					<th width="10%">用户</th>
					<td width="40%">${zcAuthentication.addUserName}</td>
					<th width="10%">认证类型</th>
					<td width="40%">
						${zcAuthentication.authTypeZh}
					</td>							
				</tr>
				<tr>
					<th><%=TzcAuthentication.ALIAS_ADDTIME%></th>
					<td colspan="3">
						<fmt:formatDate value="${zcAuthentication.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<th><%=TzcAuthentication.ALIAS_PAY_STATUS%></th>
					<td>
						${zcAuthentication.payStatusZh}
					</td>
					<th><%=TzcAuthentication.ALIAS_PAYTIME%></th>
					<td>
						<fmt:formatDate value="${zcAuthentication.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<th><%=TzcAuthentication.ALIAS_AUDIT_STATUS%></th>
					<td>
						<input type="hidden" name="auditStatus" value = "${zcAuthentication.auditStatus}"/>
						${zcAuthentication.auditStatusZh}
					</td>
					<th><%=TzcAuthentication.ALIAS_AUDIT_USER_ID%></th>
					<td>
						${zcAuthentication.auditUserName}
					</td>
				</tr>
				<tr>
					<th><%=TzcAuthentication.ALIAS_AUDIT_TIME%></th>
					<td colspan="3">
						<fmt:formatDate value="${zcAuthentication.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<th><%=TzcAuthentication.ALIAS_AUDIT_REMARK%></th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="auditRemark">${zcAuthentication.auditRemark}</textarea>
					</td>
				</tr>
			</table>
			<c:if test="${zcAuthentication.authType == 'AT02'}">
				<div style="font-size: 16pt; padding: 8px;">企业信息</div>
				<table class="table table-hover table-condensed">
					<tr>
						<th width="14%"><%=TzcAuthentication.ALIAS_COMPANY_NAME%></th>
						<td width="36%">
							${zcAuthentication.companyName}
						</td>
						<th width="14%">信用码</th>
						<td width="36%">
							${zcAuthentication.creditId}
						</td>
					</tr>
					<tr>
						<th><%=TzcAuthentication.ALIAS_LEGAL_PERSON_NAME%></th>
						<td>
							${zcAuthentication.legalPersonName}
						</td>
						<th>法人身份证号</th>
						<td>
							${zcAuthentication.legalPersonId}
						</td>
					</tr>
					<tr>
						<th>身份证正面照片</th>
						<td colspan="3" class="imageSlide">
							<img src="${zcAuthentication.legalPersonIdFront}" i="${zcAuthentication.legalPersonIdFront}" style="width:200px; height:150px;"/>
						</td>
					</tr>
					<tr>
						<th>身份证反面照片</th>
						<td colspan="3" class="imageSlide">
							<img src="${zcAuthentication.legalPersonIdBack}" i="${zcAuthentication.legalPersonIdBack}" style="width:200px; height:150px;"/>
						</td>
					</tr>
					<tr>
						<th><%=TzcAuthentication.ALIAS_BUSSINESS_LICENSE%></th>
						<td colspan="3" class="imageSlide">
							<img src="${zcAuthentication.bussinessLicense}" i="${zcAuthentication.bussinessLicense}" style="width:200px; height:150px;"/>
						</td>
					</tr>
				</table>
			</c:if>

			<div style="font-size: 16pt; padding: 8px;">个人信息</div>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="14%">姓名</th>
					<td width="36%">
						${zcAuthentication.userName}
					</td>
					<th width="14%"><%=TzcAuthentication.ALIAS_PHONE%></th>
					<td width="36%">
						${zcAuthentication.phone}
					</td>
				</tr>
				<tr>
					<th>身份证号</th>
					<td colspan="3">
						${zcAuthentication.idNo}
					</td>
				</tr>
				<tr>
					<th>身份证正面照片</th>
					<td colspan="3" class="imageSlide">
						<img src="${zcAuthentication.idFront}" i="${zcAuthentication.idFront}" style="width:200px; height:150px;"/>
					</td>
				</tr>
				<tr>
					<th>身份证反面照片</th>
					<td colspan="3" class="imageSlide">
						<img src="${zcAuthentication.idBack}" i="${zcAuthentication.idBack}" style="width:200px; height:150px;"/>
					</td>
				</tr>
				<tr>
					<th>手持身份证照片</th>
					<td colspan="3" class="imageSlide">
						<img src="${zcAuthentication.idFrontByhand}" i="${zcAuthentication.idFrontByhand}" style="width:200px; height:150px;"/>
					</td>
				</tr>
			</table>

			<c:choose>
				<c:when test="${shop != null}">
					<div style="font-size: 16pt; padding: 8px;">店铺信息</div>
					<table class="table table-hover table-condensed">
						<tr>
							<th width="14%">店铺名称</th>
							<td width="36%">
								${shop.name}
							</td>
							<th width="14%"></th>
							<td width="36%"></td>
						</tr>
						<tr>
							<th>店铺介绍</th>
							<td colspan="3">
								${shop.introduction}
							</td>
						</tr>
						<tr>
							<th>店铺logo</th>
							<td colspan="3" class="imageSlide">
								<img src="${shop.logoUrl}" i="${shop.logoUrl}" style="width:200px; height:150px;"/>
							</td>
						</tr>
					</table>
				</c:when>
				<c:otherwise><div style="font-size: 16pt; padding: 8px;">暂无填写店铺信息</div></c:otherwise>
			</c:choose>
		</form>
	</div>
</div>