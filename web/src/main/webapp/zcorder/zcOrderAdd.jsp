<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcOrderController/add',
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
					<th><%=TzcOrder.ALIAS_ORDER_NO%></th>	
					<td>
											<input class="span2" name="orderNo" type="text"/>
					</td>							
					<th><%=TzcOrder.ALIAS_PRODUCT_ID%></th>	
					<td>
											<input class="span2" name="productId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_IS_COMMENTED%></th>	
					<td>
											<input class="span2" name="isCommented" type="text"/>
					</td>							
					<th><%=TzcOrder.ALIAS_DELAY_TIME%></th>	
					<td>
					<input name="delayTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_DELAY_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_DELAY_TIMES%></th>	
					<td>
											<input class="span2" name="delayTimes" type="text"/>
					</td>							
					<th><%=TzcOrder.ALIAS_DELIVER_TIME%></th>	
					<td>
					<input name="deliverTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_DELIVER_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_RECEIVE_TIME%></th>	
					<td>
					<input name="receiveTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_RECEIVE_TIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcOrder.ALIAS_RETURN_APPLY_TIME%></th>	
					<td>
					<input name="returnApplyTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_RETURN_APPLY_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_RETURN_TIME%></th>	
					<td>
					<input name="returnTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_RETURN_TIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcOrder.ALIAS_COMPENSATION%></th>	
					<td>
											<input class="span2" name="compensation" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_ADD_USER_ID%></th>	
					<td>
											<input class="span2" name="addUserId" type="text"/>
					</td>							
					<th><%=TzcOrder.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_UPDATE_USER_ID%></th>	
					<td>
											<input class="span2" name="updateUserId" type="text"/>
					</td>							
					<th><%=TzcOrder.ALIAS_UPDATETIME%></th>	
					<td>
					<input name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_UPDATETIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_PAY_STATUS%></th>	
					<td>
											<jb:select dataType="PS" name="payStatus"></jb:select>	
					</td>							
					<th><%=TzcOrder.ALIAS_PAYTIME%></th>	
					<td>
					<input name="paytime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_PAYTIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_SEND_STATUS%></th>	
					<td>
											<jb:select dataType="SS" name="sendStatus"></jb:select>	
					</td>							
					<th><%=TzcOrder.ALIAS_BACK_STATUS%></th>	
					<td>
											<jb:select dataType="RS" name="backStatus"></jb:select>	
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_ORDER_STATUS%></th>	
					<td>
											<jb:select dataType="OS" name="orderStatus"></jb:select>	
					</td>							
					<th><%=TzcOrder.ALIAS_ORDER_STATUS_TIME%></th>	
					<td>
					<input name="orderStatusTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcOrder.FORMAT_ORDER_STATUS_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcOrder.ALIAS_FACE_TO_FACE%></th>	
					<td>
											<input class="span2" name="faceToFace" type="text"/>
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>