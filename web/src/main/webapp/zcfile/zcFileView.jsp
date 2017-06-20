<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcFile" %>
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
					<th><%=TzcFile.ALIAS_OBJECT_TYPE%></th>	
					<td>
						${zcFile.objectType}							
					</td>							
					<th><%=TzcFile.ALIAS_OBJECT_ID%></th>	
					<td>
						${zcFile.objectId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcFile.ALIAS_FILE_TYPE%></th>	
					<td>
						${zcFile.fileType}							
					</td>							
					<th><%=TzcFile.ALIAS_FILE_ORIGINAL_URL%></th>	
					<td>
						${zcFile.fileOriginalUrl}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcFile.ALIAS_FILE_HANDLE_URL%></th>	
					<td>
						${zcFile.fileHandleUrl}							
					</td>							
					<th><%=TzcFile.ALIAS_FILE_ORIGINAL_SIZE%></th>	
					<td>
						${zcFile.fileOriginalSize}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcFile.ALIAS_FILE_HANDLE_SIZE%></th>	
					<td>
						${zcFile.fileHandleSize}							
					</td>							
					<th><%=TzcFile.ALIAS_DURATION%></th>	
					<td>
						${zcFile.duration}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcFile.ALIAS_ADDTIME%></th>	
					<td>
						${zcFile.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>