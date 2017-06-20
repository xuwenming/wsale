<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcPositionApply" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcPositionApplyController/add',
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
					<th><%=TzcPositionApply.ALIAS_CATEGORY_ID%></th>	
					<td>
											<input class="span2" name="categoryId" type="text"/>
					</td>							
					<th><%=TzcPositionApply.ALIAS_ROLE_ID%></th>	
					<td>
											<input class="span2" name="roleId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPositionApply.ALIAS_APPLY_USER_ID%></th>	
					<td>
											<input class="span2" name="applyUserId" type="text"/>
					</td>							
					<th><%=TzcPositionApply.ALIAS_APPLY_CONTENT%></th>	
					<td>
											<input class="span2" name="applyContent" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPositionApply.ALIAS_RECOMMEND%></th>	
					<td>
											<input class="span2" name="recommend" type="text"/>
					</td>							
					<th><%=TzcPositionApply.ALIAS_COMPANY_NAME%></th>	
					<td>
											<input class="span2" name="companyName" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPositionApply.ALIAS_SPECIALTY%></th>	
					<td>
											<input class="span2" name="specialty" type="text"/>
					</td>							
					<th><%=TzcPositionApply.ALIAS_ADVICE%></th>	
					<td>
											<input class="span2" name="advice" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPositionApply.ALIAS_ACTIVITY_FORUM%></th>	
					<td>
											<input class="span2" name="activityForum" type="text"/>
					</td>							
					<th><%=TzcPositionApply.ALIAS_ONLINE_DURATION%></th>	
					<td>
											<input class="span2" name="onlineDuration" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPositionApply.ALIAS_AUDIT_STATUS%></th>	
					<td>
											<jb:select dataType="AS" name="auditStatus"></jb:select>	
					</td>							
					<th><%=TzcPositionApply.ALIAS_AUDIT_TIME%></th>	
					<td>
					<input name="auditTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcPositionApply.FORMAT_AUDIT_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPositionApply.ALIAS_AUDIT_USER_ID%></th>	
					<td>
											<input class="span2" name="auditUserId" type="text"/>
					</td>							
					<th><%=TzcPositionApply.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcPositionApply.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcPositionApply.ALIAS_PAY_STATUS%></th>	
					<td>
											<input class="span2" name="payStatus" type="text"/>
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>