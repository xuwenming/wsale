<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcIntermediary" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcIntermediaryController/edit',
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
				<input type="hidden" name="id" value = "${zcIntermediary.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcIntermediary.ALIAS_IM_NO%></th>	
					<td>
											<input class="span2" name="imNo" type="text" class="easyui-validatebox span2" data-options="required:true" value="${zcIntermediary.imNo}"/>
					</td>							
					<th><%=TzcIntermediary.ALIAS_BBS_ID%></th>	
					<td>
											<input class="span2" name="bbsId" type="text" value="${zcIntermediary.bbsId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcIntermediary.ALIAS_SELL_USER_ID%></th>	
					<td>
											<input class="span2" name="sellUserId" type="text" value="${zcIntermediary.sellUserId}"/>
					</td>							
					<th><%=TzcIntermediary.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text" value="${zcIntermediary.userId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcIntermediary.ALIAS_AMOUNT%></th>	
					<td>
											<input class="span2" name="amount" type="text" value="${zcIntermediary.amount}"/>
					</td>							
					<th><%=TzcIntermediary.ALIAS_REMARK%></th>	
					<td>
											<input class="span2" name="remark" type="text" value="${zcIntermediary.remark}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcIntermediary.ALIAS_STATUS%></th>	
					<td>
											<jb:select dataType="IS" name="status" value="${zcIntermediary.status}"></jb:select>	
					</td>							
					<th><%=TzcIntermediary.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcIntermediary.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcIntermediary.addtime}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>