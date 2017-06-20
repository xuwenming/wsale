<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcProduct" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcProductController/add',
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
					<th><%=TzcProduct.ALIAS_PNO%></th>	
					<td>
											<input class="span2" name="pno" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_CATEGORY_ID%></th>	
					<td>
											<input class="span2" name="categoryId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_DEADLINE%></th>	
					<td>
					<input name="deadline" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProduct.FORMAT_DEADLINE%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcProduct.ALIAS_REAL_DEADLINE%></th>	
					<td>
					<input name="realDeadline" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProduct.FORMAT_REAL_DEADLINE%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_STARTING_PRICE%></th>	
					<td>
											<input class="span2" name="startingPrice" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_APPROVAL_DAYS%></th>	
					<td>
											<input class="span2" name="approvalDays" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_IS_FREE_SHIPPING%></th>	
					<td>
											<input class="span2" name="isFreeShipping" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_MARGIN%></th>	
					<td>
											<input class="span2" name="margin" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_IS_NEED_REAL_ID%></th>	
					<td>
											<input class="span2" name="isNeedRealId" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_IS_NEED_PROTECTION_PRICE%></th>	
					<td>
											<input class="span2" name="isNeedProtectionPrice" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_STARTING_TIME%></th>	
					<td>
					<input name="startingTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProduct.FORMAT_STARTING_TIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcProduct.ALIAS_FIXED_PRICE%></th>	
					<td>
											<input class="span2" name="fixedPrice" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_REFERENCE_PRICE%></th>	
					<td>
											<input class="span2" name="referencePrice" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_CONTENT%></th>	
					<td>
											<input class="span2" name="content" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_STATUS%></th>	
					<td>
											<input class="span2" name="status" type="text"/>
					</td>							

				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_IS_CLOSE%></th>	
					<td>
											<input class="span2" name="isClose" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_READ_COUNT%></th>	
					<td>
											<input class="span2" name="readCount" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_LIKE_COUNT%></th>	
					<td>
											<input class="span2" name="likeCount" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_SHARE_COUNT%></th>	
					<td>
											<input class="span2" name="shareCount" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_CURRENT_PRICE%></th>	
					<td>
											<input class="span2" name="currentPrice" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_HAMMER_PRICE%></th>	
					<td>
											<input class="span2" name="hammerPrice" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_HAMMER_TIME%></th>	
					<td>
					<input name="hammerTime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProduct.FORMAT_HAMMER_TIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_IS_DELETED%></th>	
					<td>
											<input class="span2" name="isDeleted" type="text"/>
					</td>							
					<th><%=TzcProduct.ALIAS_ADD_USER_ID%></th>	
					<td>
											<input class="span2" name="addUserId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_ADDTIME%></th>	
					<td>
					<input name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProduct.FORMAT_ADDTIME%>'})"  maxlength="0" class="span " />
					</td>							
					<th><%=TzcProduct.ALIAS_UPDATE_USER_ID%></th>	
					<td>
											<input class="span2" name="updateUserId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TzcProduct.ALIAS_UPDATETIME%></th>	
					<td>
					<input name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TzcProduct.FORMAT_UPDATETIME%>'})"  maxlength="0" class="span " />
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>