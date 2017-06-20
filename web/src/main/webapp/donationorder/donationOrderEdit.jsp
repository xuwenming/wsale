<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TdonationOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/donationOrderController/edit',
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
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${donationOrder.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TdonationOrder.ALIAS_OPENID%></th>	
					<td>
											<input class="span2" name="openid" type="text" value="${donationOrder.openid}"/>
					</td>							
					<th><%=TdonationOrder.ALIAS_NICKNAME%></th>	
					<td>
											<input class="span2" name="nickname" type="text" value="${donationOrder.nickname}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdonationOrder.ALIAS_SEX%></th>	
					<td>
											<input class="span2" name="sex" type="text" value="${donationOrder.sex}"/>
					</td>							
					<th><%=TdonationOrder.ALIAS_CITY%></th>	
					<td>
											<input class="span2" name="city" type="text" value="${donationOrder.city}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdonationOrder.ALIAS_PROVINCE%></th>	
					<td>
											<input class="span2" name="province" type="text" value="${donationOrder.province}"/>
					</td>							
					<th><%=TdonationOrder.ALIAS_COUNTRY%></th>	
					<td>
											<input class="span2" name="country" type="text" value="${donationOrder.country}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdonationOrder.ALIAS_HEADIMGURL%></th>	
					<td>
											<input class="span2" name="headimgurl" type="text" value="${donationOrder.headimgurl}"/>
					</td>							
					<th><%=TdonationOrder.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TdonationOrder.FORMAT_ADDTIME%>'})"   maxlength="0" value="${donationOrder.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdonationOrder.ALIAS_PAY_STATUS%></th>	
					<td>
											<input class="span2" name="payStatus" type="text" value="${donationOrder.payStatus}"/>
					</td>							
					<th><%=TdonationOrder.ALIAS_PAYTIME%></th>	
					<td>
					<input class="span2" name="paytime" type="text" onclick="WdatePicker({dateFmt:'<%=TdonationOrder.FORMAT_PAYTIME%>'})"   maxlength="0" value="${donationOrder.paytime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdonationOrder.ALIAS_ORDER_NO%></th>	
					<td>
											<input class="span2" name="orderNo" type="text" value="${donationOrder.orderNo}"/>
					</td>							
					<th><%=TdonationOrder.ALIAS_TOTAL_FEE%></th>	
					<td>
											<input class="span2" name="totalFee" type="text" value="${donationOrder.totalFee}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>