<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcBanner" %>
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
				<th><%=TzcBanner.ALIAS_TITLE%></th>
				<td colspan="3">
					${zcBanner.title}
				</td>
			</tr>
			<tr>
				<th width="10%"><%=TzcBanner.ALIAS_SORT_NUMBER%></th>
				<td width="40%">
					${zcBanner.sortNumber}
				</td>
				<th width="10%"><%=TzcBanner.ALIAS_STATUS%></th>
				<td width="40%">
					${zcBanner.statusZh}
				</td>
			</tr>
			<tr>
				<th valign="top"><%=TzcBanner.ALIAS_URL%></th>
				<td colspan="3">
					<img alt="" src="${zcBanner.url}" width="240" height="240">
				</td>
			</tr>
			<tr>
				<th><%=TzcBanner.ALIAS_ADD_USER_ID%></th>
				<td>
					${zcBanner.addUserName}
				</td>
				<th><%=TzcBanner.ALIAS_ADDTIME%></th>
				<td>
					<fmt:formatDate value="${zcBanner.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th valign="top"><%=TzcBanner.ALIAS_DETAIL_URL%></th>
				<td colspan="3">
					${zcBanner.detailUrl}
				</td>
			</tr>
			<tr>
				<th valign="top"><%=TzcBanner.ALIAS_CONTENT%></th>
				<td colspan="3">
					${zcBanner.content}
				</td>
			</tr>

		</table>
	</div>
</div>