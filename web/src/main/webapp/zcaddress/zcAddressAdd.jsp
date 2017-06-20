<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcAddress" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcAddressController/add',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">	
		<form id="form" method="post">		
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcAddress.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text"/>
					</td>							
					<th><%=TzcAddress.ALIAS_ATYPE%></th>	
					<td>
											<input class="span2" name="atype" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAddress.ALIAS_USER_NAME%></th>	
					<td>
											<input class="span2" name="userName" type="text"/>
					</td>							
					<th><%=TzcAddress.ALIAS_TEL_NUMBER%></th>	
					<td>
											<input class="span2" name="telNumber" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAddress.ALIAS_PROVINCE_NAME%></th>	
					<td>
											<input class="span2" name="provinceName" type="text"/>
					</td>							
					<th><%=TzcAddress.ALIAS_CITY_NAME%></th>	
					<td>
											<input class="span2" name="cityName" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAddress.ALIAS_COUNTY_NAME%></th>	
					<td>
											<input class="span2" name="countyName" type="text"/>
					</td>							
					<th><%=TzcAddress.ALIAS_DETAIL_INFO%></th>	
					<td>
											<input class="span2" name="detailInfo" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAddress.ALIAS_POSTAL_CODE%></th>	
					<td>
											<input class="span2" name="postalCode" type="text"/>
					</td>							
					<th><%=TzcAddress.ALIAS_IS_DEFAULT%></th>	
					<td>
											<input class="span2" name="isDefault" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAddress.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcAddress.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>