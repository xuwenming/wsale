<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcShop" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcShopController/edit',
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
				<input type="hidden" name="id" value = "${zcShop.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TzcShop.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text" value="${zcShop.userId}"/>
					</td>							
					<th><%=TzcShop.ALIAS_NAME%></th>	
					<td>
											<input class="span2" name="name" type="text" value="${zcShop.name}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShop.ALIAS_LOGO_URL%></th>	
					<td>
											<input class="span2" name="logoUrl" type="text" value="${zcShop.logoUrl}"/>
					</td>							
					<th><%=TzcShop.ALIAS_NOTICE%></th>	
					<td>
											<input class="span2" name="notice" type="text" value="${zcShop.notice}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShop.ALIAS_INTRODUCTION%></th>	
					<td>
											<input class="span2" name="introduction" type="text" value="${zcShop.introduction}"/>
					</td>							
					<th><%=TzcShop.ALIAS_PROTECTION_PRICE%></th>	
					<td>
											<input class="span2" name="protectionPrice" type="text" value="${zcShop.protectionPrice}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShop.ALIAS_IS_NEED_PHONE%></th>	
					<td>
											<input class="span2" name="isNeedPhone" type="text" value="${zcShop.isNeedPhone}"/>
					</td>							
					<th><%=TzcShop.ALIAS_IS_NEED_REEL_ID%></th>	
					<td>
											<input class="span2" name="isNeedReelId" type="text" value="${zcShop.isNeedReelId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShop.ALIAS_SHOP_URL%></th>	
					<td>
											<input class="span2" name="shopUrl" type="text" value="${zcShop.shopUrl}"/>
					</td>							
					<th><%=TzcShop.ALIAS_SHOP_QRCODE_URL%></th>	
					<td>
											<input class="span2" name="shopQrcodeUrl" type="text" value="${zcShop.shopQrcodeUrl}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShop.ALIAS_GRADE%></th>	
					<td>
											<input class="span2" name="grade" type="text" value="${zcShop.grade}"/>
					</td>							
					<th><%=TzcShop.ALIAS_ADD_USER_ID%></th>	
					<td>
											<input class="span2" name="addUserId" type="text" value="${zcShop.addUserId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShop.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcShop.FORMAT_ADDTIME%>'})"   maxlength="0" value="${zcShop.addtime}"/>
					</td>							
					<th><%=TzcShop.ALIAS_UPDATE_USER_ID%></th>	
					<td>
											<input class="span2" name="updateUserId" type="text" value="${zcShop.updateUserId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TzcShop.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcShop.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${zcShop.updatetime}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>