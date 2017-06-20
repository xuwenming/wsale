<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcAddress" %>
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
					<th><%=TzcAddress.ALIAS_USER_ID%></th>	
					<td>
						${zcAddress.userId}							
					</td>							
					<th><%=TzcAddress.ALIAS_ATYPE%></th>	
					<td>
						${zcAddress.atype}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAddress.ALIAS_USER_NAME%></th>	
					<td>
						${zcAddress.userName}							
					</td>							
					<th><%=TzcAddress.ALIAS_TEL_NUMBER%></th>	
					<td>
						${zcAddress.telNumber}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAddress.ALIAS_PROVINCE_NAME%></th>	
					<td>
						${zcAddress.provinceName}							
					</td>							
					<th><%=TzcAddress.ALIAS_CITY_NAME%></th>	
					<td>
						${zcAddress.cityName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAddress.ALIAS_COUNTY_NAME%></th>	
					<td>
						${zcAddress.countyName}							
					</td>							
					<th><%=TzcAddress.ALIAS_DETAIL_INFO%></th>	
					<td>
						${zcAddress.detailInfo}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAddress.ALIAS_POSTAL_CODE%></th>	
					<td>
						${zcAddress.postalCode}							
					</td>							
					<th><%=TzcAddress.ALIAS_IS_DEFAULT%></th>	
					<td>
						${zcAddress.isDefault}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TzcAddress.ALIAS_ADDTIME%></th>	
					<td>
						${zcAddress.addtime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>