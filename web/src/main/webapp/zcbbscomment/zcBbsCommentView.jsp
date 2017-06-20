<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcBbsComment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcBbsComment.ALIAS_BBS_ID%></th>	
					<td>
						${zcBbsComment.bbsId}							
					</td>							
					<th><%=TzcBbsComment.ALIAS_COMMENT%></th>	
					<td>
						${zcBbsComment.comment}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcBbsComment.ALIAS_PID%></th>	
					<td>
						${zcBbsComment.pid}							
					</td>							
					<th><%=TzcBbsComment.ALIAS_IS_DELETED%></th>	
					<td>
						${zcBbsComment.isDeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcBbsComment.ALIAS_USER_ID%></th>	
					<td>
						${zcBbsComment.userId}							
					</td>							
					<th><%=TzcBbsComment.ALIAS_ADDTIME%></th>	
					<td>
						${zcBbsComment.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>