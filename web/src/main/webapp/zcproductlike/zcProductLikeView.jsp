<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProductLike" %>
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
					<th><%=TzcProductLike.ALIAS_PRODUCT_ID%></th>	
					<td>
						${zcProductLike.productId}							
					</td>							
					<th><%=TzcProductLike.ALIAS_USER_ID%></th>
					<td>
						${zcProductLike.userId}
					</td>
				</tr>		

				<tr>	
					<th><%=TzcProductLike.ALIAS_ADDTIME%></th>	
					<td>
						${zcProductLike.addtime}							
					</td>							

				</tr>		

		</table>
	</div>
</div>