<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPositionApply" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th width="14%"><%=TzcPositionApply.ALIAS_CATEGORY_ID%></th>
					<td width="36%">
						${zcPositionApply.categoryName}
					</td>							
					<th width="14%"><%=TzcPositionApply.ALIAS_ROLE_ID%></th>
					<td width="36%">
						${zcPositionApply.roleName}
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPositionApply.ALIAS_APPLY_USER_ID%></th>	
					<td>
						${zcPositionApply.applyUserName}
					</td>
					<th><%=TzcPositionApply.ALIAS_ADDTIME%></th>
					<td>
						<fmt:formatDate value="${zcPositionApply.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>		
				<tr>	
					<th><%=TzcPositionApply.ALIAS_RECOMMEND%></th>	
					<td>
						${zcPositionApply.recommend}							
					</td>							
					<th><%=TzcPositionApply.ALIAS_COMPANY_NAME%></th>	
					<td>
						${zcPositionApply.companyName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPositionApply.ALIAS_SPECIALTY%></th>	
					<td colspan="3">
						${zcPositionApply.specialty}							
					</td>
				</tr>
				<tr>
					<th><%=TzcPositionApply.ALIAS_ADVICE%></th>
					<td colspan="3">
						${zcPositionApply.advice}
					</td>
				</tr>
				<tr>
					<th><%=TzcPositionApply.ALIAS_ACTIVITY_FORUM%></th>
					<td colspan="3">
						${zcPositionApply.activityForum}
					</td>
				</tr>
				<tr>
					<th><%=TzcPositionApply.ALIAS_ONLINE_DURATION%></th>
					<td colspan="3">
						${zcPositionApply.onlineDuration}
					</td>
				</tr>
				<tr>
					<th><%=TzcPositionApply.ALIAS_PAY_STATUS%></th>
					<td>
						${zcPositionApply.payStatusZh}
					</td>
					<th>支付时间</th>
					<td>
						<fmt:formatDate value="${zcPositionApply.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<th><%=TzcPositionApply.ALIAS_AUDIT_STATUS%></th>	
					<td>
						${zcPositionApply.auditStatusZh}
					</td>							
					<th><%=TzcPositionApply.ALIAS_AUDIT_TIME%></th>	
					<td>
						<c:if test="${zcPositionApply.auditTime != null}">
							<fmt:formatDate value="${zcPositionApply.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</c:if>
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcPositionApply.ALIAS_AUDIT_USER_ID%></th>
					<td colspan="3">
						${zcPositionApply.auditUserName}
					</td>							
				</tr>
				<tr>
					<th valign="top">审核备注</th>
					<td colspan="3">
						${zcPositionApply.auditRemark}
					</td>
				</tr>

		</table>
	</div>
</div>