<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcAuthentication" %>
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
					${zcAuthentication.auditRemark}
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
							<img src="${shop.logoUrl}" i="${shop.logoUrl}"  style="width:200px; height:150px;"/>
						</td>
					</tr>
				</table>
			</c:when>
			<c:otherwise><div style="font-size: 16pt; padding: 8px;">暂无填写店铺信息</div></c:otherwise>
		</c:choose>
	</div>
</div>