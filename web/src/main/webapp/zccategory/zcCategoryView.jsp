<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcCategory" %>
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
					<th width="10%"><%=TzcCategory.ALIAS_NAME%></th>
					<td width="40%">
						${zcCategory.name}							
					</td>
					<th width="10%"><%=TzcCategory.ALIAS_PID%></th>
					<td width="40%">
						${zcCategory.pname}
					</td>
				</tr>
				<tr>
					<th valign="top">分类简介</th>
					<td colspan="3">
						${zcCategory.summary}
					</td>
				</tr>
				<tr>	
					<th><%=TzcCategory.ALIAS_SEQ%></th>	
					<td>
						${zcCategory.seq}							
					</td>
					<th><%=TzcCategory.ALIAS_CHIEF_MODERATOR_ID%></th>
					<td>
						${zcCategory.chiefModeratorName}
					</td>
				</tr>
				<tr>
					<th valign="top"><%=TzcCategory.ALIAS_FORUM_INTRODUCE%></th>
					<td colspan="3">
						${zcCategory.forumIntroduce}
					</td>
				</tr>
				<tr>	
					<th><%=TzcCategory.ALIAS_ADD_USER_ID%></th>	
					<td>
						${zcCategory.addUserName}
					</td>							
					<th><%=TzcCategory.ALIAS_ADDTIME%></th>	
					<td>
						<fmt:formatDate value="${zcCategory.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>

					</td>							
				</tr>
				<tr>
					<th valign="top"><%=TzcCategory.ALIAS_ICON%></th>
					<td colspan="3">
						<c:if test="${zcCategory.icon != null && zcCategory.icon != ''}">
							<img alt="" src="${zcCategory.icon}" width="240" height="240">
						</c:if>

					</td>
				</tr>
		</table>
	</div>
</div>