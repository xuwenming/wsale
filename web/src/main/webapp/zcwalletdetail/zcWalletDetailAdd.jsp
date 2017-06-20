<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcWalletDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	var userCombogrid;
	$(function() {
	 	parent.$.messager.progress('close');
		setTimeout(function(){
			userCombogrid = $('#userId').combogrid({
				url:'${pageContext.request.contextPath}/userController/queryUsers',
				panelWidth:510,
				width : 140,
				height : 29,
				idField:'id',
				textField:'nickname',
				mode:'remote',
				method:'post',
				nowrap : true,
				striped:true,
				columns:[[
					{field:'nickname',title:'昵称',width:200},
					{field:'mobile',title:'手机号',width:150},
					{field:'wechatNo',title:'微信号',width:150}
				]]
			});

			userCombogrid.next('span').find('input').focus(function(){
				userCombogrid.combogrid("showPanel");
			});
		}, 20);

		$('#form').form({
			url : '${pageContext.request.contextPath}/zcWalletDetailController/add',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if($("#userId").combobox('getValue') == $("#userId").combobox('getText')) {
					isValid = false;
					alert('请正确选择充值用户！');
				}
				if(isValid && $('#amount').val() == 0) {
					isValid = false;
					alert('充值金额不允许为0！');
				}

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
			<table class="table table-hover table-condensed">
				<tr>
					<th width="10%">充值用户</th>
					<td width="40%">
						<input id="userId" name="userId"/>
					</td>
					<th width="10%">充值金额</th>
					<td width="40%">
						<input name="amount" id="amount" value="100" class="easyui-numberspinner"
							   style="width: 140px; height: 29px;" required="required"
							   data-options="editable:true"><font color="red">（负数表示扣除余额）</font>
					</td>
				</tr>
				<tr>
					<th>充值描述</th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="description" class="easyui-validatebox" data-options="required:true"></textarea>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>