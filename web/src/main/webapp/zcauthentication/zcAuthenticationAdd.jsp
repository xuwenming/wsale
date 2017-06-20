<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcAuthentication" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcAuthenticationController/add',
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
					<th><%=TzcAuthentication.ALIAS_AUTH_TYPE%></th>	
					<td>
											<input class="span2" name="authType" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_USER_NAME%></th>	
					<td>
											<input class="span2" name="userName" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_ID_TYPE%></th>	
					<td>
											<input class="span2" name="idType" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_ID_NO%></th>	
					<td>
											<input class="span2" name="idNo" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_PHONE%></th>	
					<td>
											<input class="span2" name="phone" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_ID_FRONT_BYHAND%></th>	
					<td>
											<input class="span2" name="idFrontByhand" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_ID_FRONT%></th>	
					<td>
											<input class="span2" name="idFront" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_ID_BACK%></th>	
					<td>
											<input class="span2" name="idBack" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_COMPANY_NAME%></th>	
					<td>
											<input class="span2" name="companyName" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_CREDIT_ID%></th>	
					<td>
											<input class="span2" name="creditId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_BUSSINESS_LICENSE%></th>	
					<td>
											<input class="span2" name="bussinessLicense" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_LEGAL_PERSON_NAME%></th>	
					<td>
											<input class="span2" name="legalPersonName" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_LEGAL_PERSON_ID%></th>	
					<td>
											<input class="span2" name="legalPersonId" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_LEGAL_PERSON_ID_FRONT%></th>	
					<td>
											<input class="span2" name="legalPersonIdFront" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_LEGAL_PERSON_ID_BACK%></th>	
					<td>
											<input class="span2" name="legalPersonIdBack" type="text"/>
					</td>							
					<th><%=TzcAuthentication.ALIAS_PAY_STATUS%></th>	
					<td>
											<input class="span2" name="payStatus" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_PAYTIME%></th>	
					<td>
					<input name="paytime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcAuthentication.FORMAT_PAYTIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcAuthentication.ALIAS_ADD_USER_ID%></th>	
					<td>
											<input class="span2" name="addUserId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcAuthentication.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcAuthentication.ALIAS_AUDIT_STATUS%></th>	
					<td>
											<jb:select dataType="AS" name="auditStatus"></jb:select>	
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_AUDIT_TIME%></th>	
					<td>
					<input name="auditTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcAuthentication.FORMAT_AUDIT_TIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcAuthentication.ALIAS_AUDIT_USER_ID%></th>	
					<td>
											<input class="span2" name="auditUserId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcAuthentication.ALIAS_AUDIT_REMARK%></th>	
					<td>
											<input class="span2" name="auditRemark" type="text"/>
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>