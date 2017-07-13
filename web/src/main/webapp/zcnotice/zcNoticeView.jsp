<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcNotice" %>
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
				<th width="10%">发布时间</th>
				<td width="40%">
					<fmt:formatDate value="${zcNotice.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th width="10%">发布人</th>
				<td width="40%">
					${zcNotice.addUserName}
				</td>
			</tr>
			<c:if test="${not empty zcNotice.updateUserId}">
				<tr>
					<th>更新时间</th>
					<td>
						<fmt:formatDate value="${zcNotice.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<th>更新人</th>
					<td>
						${zcNotice.updateUserName}
					</td>
				</tr>
			</c:if>
			<tr>
				<th><%=TzcNotice.ALIAS_STATUS%></th>
				<td colspan="3">
					${zcNotice.statusZh}
				</td>
			</tr>
			<tr>
				<th valign="top"><%=TzcNotice.ALIAS_CONTENT%></th>
				<td colspan="3">
					${zcNotice.content}
				</td>
			</tr>

		</table>
	</div>
</div>