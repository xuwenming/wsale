<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProductMargin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcProductMarginController/add',
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
					<th><%=TzcProductMargin.ALIAS_PRODUCT_ID%></th>	
					<td>
											<input class="span2" name="productId" type="text"/>
					</td>							
					<th><%=TzcProductMargin.ALIAS_BUY_USER_ID%></th>	
					<td>
											<input class="span2" name="buyUserId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProductMargin.ALIAS_MARGIN%></th>	
					<td>
											<input class="span2" name="margin" type="text"/>
					</td>							
					<th><%=TzcProductMargin.ALIAS_RETURN_TIME%></th>	
					<td>
					<input name="returnTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProductMargin.FORMAT_RETURN_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProductMargin.ALIAS_PAY_STATUS%></th>	
					<td>
											<jb:select dataType="PS" name="payStatus"></jb:select>	
					</td>							
					<th><%=TzcProductMargin.ALIAS_PAYTIME%></th>	
					<td>
					<input name="paytime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProductMargin.FORMAT_PAYTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProductMargin.ALIAS_ADD_USER_ID%></th>	
					<td>
											<input class="span2" name="addUserId" type="text"/>
					</td>							
					<th><%=TzcProductMargin.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProductMargin.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProductMargin.ALIAS_UPDATE_USER_ID%></th>	
					<td>
											<input class="span2" name="updateUserId" type="text"/>
					</td>							
					<th><%=TzcProductMargin.ALIAS_UPDATETIME%></th>	
					<td>
					<input name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProductMargin.FORMAT_UPDATETIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>