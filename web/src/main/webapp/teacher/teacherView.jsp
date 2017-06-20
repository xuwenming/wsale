<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.Tteacher" %>
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
					<th><%=Tteacher.ALIAS_NAME%></th>	
					<td>
						${teacher.name}							
					</td>							
					<th><%=Tteacher.ALIAS_AGE%></th>	
					<td>
						${teacher.age}							
					</td>							
				</tr>		
				<tr>	
					<th><%=Tteacher.ALIAS_SEX%></th>	
					<td>
						${teacher.sex}							
					</td>							
					<th><%=Tteacher.ALIAS_POSITION%></th>	
					<td>
						${teacher.position}							
					</td>							
				</tr>		
		</table>
	</div>
</div>