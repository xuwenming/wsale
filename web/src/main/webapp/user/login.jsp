<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#qrcode img{
		margin: 20px auto;
	}
</style>
<script type="text/javascript" charset="utf-8">
	var loginDialog;
	var defaultUserInfoDialog;
	var loginTabs;
	var userLoginCombobox;
	var userLoginCombogrid;
	$(function() {
		loginDialog = $('#loginDialog').show().dialog({
			modal : true,
			closable : false,
			buttons : [
			/*{
				text : '注册',
				handler : function() {
					$('#registerDialog').dialog('open');
				}
			}, */{
				text : '登录',
				handler : function() {
					loginFun();
				}
			} ]
		});

		userLoginCombobox = $('#userLoginCombobox').combobox({
			url : '${pageContext.request.contextPath}/userController/loginCombobox',
			valueField : 'name',
			textField : 'name',
			required : true,
			panelHeight : 'auto',
			delay : 500
		});

		userLoginCombogrid = $('#userLoginCombogrid').combogrid({
			url : '${pageContext.request.contextPath}/userController/loginCombogrid',
			panelWidth : 450,
			panelHeight : 200,
			idField : 'name',
			textField : 'name',
			pagination : true,
			fitColumns : true,
			required : true,
			rownumbers : true,
			mode : 'remote',
			delay : 500,
			sortName : 'name',
			sortOrder : 'asc',
			pageSize : 5,
			pageList : [ 5, 10 ],
			columns : [ [ {
				field : 'name',
				title : '登录名',
				width : 150
			}, {
				field : 'createdatetime',
				title : '创建时间',
				width : 150
			}, {
				field : 'modifydatetime',
				title : '最后修改时间',
				width : 150
			} ] ]
		});

		defaultUserInfoDialog = $('#defaultUserInfoDialog').show().dialog({
			top : 0,
			left : 200
		});

		$('#loginDialog input').keyup(function(event) {
			if (event.keyCode == '13') {
				loginFun();
			}
		});
// 		userLoginCombobox.combobox('textbox').keyup(function(event) {
// 			if (event.keyCode == '13') {
// 				loginFun();
// 			}
// 		});
// 		userLoginCombogrid.combogrid('textbox').keyup(function(event) {
// 			if (event.keyCode == '13') {
// 				loginFun();
// 			}
// 		});

		$('#loginTabs').tabs({
			onSelect:function(title, index){
				if(index == 1) {
					$('#loginDialog').dialog({
						width: 330,
						height: 220
					});
					$("#loginDialog .dialog-button").show();
				} else {
					$('#loginDialog').dialog({
						width: 300,
						height: 400
					});
					$("#loginDialog .dialog-button").hide();
				}

			}
		});

		getQrcodeUrl();

		var sessionInfo_userId = '${sessionInfo.id}';
		if (sessionInfo_userId) {/*目的是，如果已经登陆过了，那么刷新页面后也不需要弹出登录窗体*/
			loginDialog.dialog('close');
			defaultUserInfoDialog.dialog('close');
		}
	});

	function getQrcodeUrl() {
		$.post('${pageContext.request.contextPath}/userController/getQrcodeUrl', {}, function(result) {
			if (result.success) {
				$('#qrcode').empty();
				// 设置参数方式
				var qrcode = new QRCode('qrcode', {
					width: 180,
					height: 180
				});
				// 使用 API
				qrcode.clear();
				qrcode.makeCode(result.obj.url);

				//检查验证登录
				checkScan(result.obj.uuid);
			}
		}, "JSON");
	}

	var interval;
	function checkScan(uuid) {

		interval = setInterval(function() {
			$.post('${pageContext.request.contextPath}/userController/checkScan', {uuid:uuid}, function(result) {
				console.log(result);
				if (result.success) {
					clearInterval(interval);
					window.location.href = "${pageContext.request.contextPath}/index.jsp";
				} else {
					if(result.msg == 'invalid') clearInterval(interval);
				}
			}, "JSON");
		},2000)
	}

	function loginFun() {
		if (layout_west_tree) {//当west功能菜单树加载成功后再执行登录

			loginTabs = $('#loginTabs').tabs('getSelected');//当前选中的tab
			var form = loginTabs.find('form');//选中的tab里面的form

			if (form.form('validate')) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.get('${pageContext.request.contextPath}/userController/getPublicKey', function(result) {
					if (result.success) {
						var encrypt = new JSEncrypt();
						encrypt.setPublicKey(result.obj);
						var username = encrypt.encrypt($('#username').val());
						var password = encrypt.encrypt($('#password').val());
						$.post('${pageContext.request.contextPath}/userController/login', {name : username, pwd : password}, function(result) {
							if (result.success) {
								if (!layout_west_tree_url) {
									layout_west_tree.tree({
										url : '${pageContext.request.contextPath}/resourceController/tree',
										onBeforeLoad : function(node, param) {
											parent.$.messager.progress({
												title : '提示',
												text : '数据处理中，请稍后....'
											});
										}
									});
								}
								$('#loginDialog').dialog('close');
								//$('#sessionInfoDiv').html($.formatString('[<strong>{0}</strong>]，欢迎你！您使用[<strong>{1}</strong>]IP登录！', result.obj.name, result.obj.ip));
								$('#sessionInfoDiv').html($.formatString('[<strong>{0}</strong>]，欢迎你！', result.obj.nickname));
							} else {
								$.messager.alert('错误', result.msg, 'error');
							}
							parent.$.messager.progress('close');
						}, "JSON");
					}
				}, "JSON");
			}
		}
	}
</script>
<div id="loginDialog" title="用户登录" style="overflow: hidden; display: none;">
	<div id="loginTabs" class="easyui-tabs" data-options="fit:true,border:false">
		<div title="扫码登录" style="overflow: hidden; padding: 10px;text-align: center">
			<div class="scanLogin">
				<div id="qrcode"></div>
				<div style="font-size: 16px;margin-top: 50px;">请使用微信扫一扫以登录</div>
			</div>
			<!--<div class="loginConfirm">
				<div class="saoma-login-img">
					<img src="${pageContext.request.contextPath}/wsale/images/search-icon.png"/>
					<h3>扫描成功</h3>
					<p>请在手机上确认登录</p>
				</div>
			</div>-->
		</div>
		<div title="用户名登录" style="overflow: hidden; padding: 10px;">
			<form method="post">
				<table class="table table-hover table-condensed">
					<tr>
						<th>登录名</th>
						<td><input id="username" name="name" type="text" placeholder="请输入登录名" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>密码</th>
						<td><input id="password" name="pwd" type="password" placeholder="请输入密码" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
				</table>
			</form>
		</div>

		<!--
		<div title="数据表格模式" style="overflow: hidden; padding: 10px;">
			<form method="post">
				<table class="table table-hover table-condensed">
					<tr>
						<th>登录名</th>
						<td><input id="userLoginCombogrid" name="name" type="text" placeholder="请输入登录名" class="easyui-validatebox" data-options="required:true" value="" style="height: 29px;"></td>
					</tr>
					<tr>
						<th>密码</th>
						<td><input name="pwd" type="password" placeholder="请输入密码" class="easyui-validatebox" data-options="required:true" value=""></td>
					</tr>
				</table>
			</form>
		</div> -->
	</div>
</div>

<!-- 
<div id="defaultUserInfoDialog" title="系统测试账号" style="width: 300px; height: 260px; overflow: hidden; display: none;">
	<div class="well well-small" style="margin: 3px;">请大家不要随意更改系统默认账户的信息，如果想测试，请自己新建立用户进行测试</div>
	<div class="well well-small" style="margin: 3px;">
		<div>
			<span class="badge">1</span>超管：John/123456
		</div>
		<div>
			<span class="badge badge-success">2</span>资源管理员：admin1/123456
		</div>
		<div>
			<span class="badge badge-warning">3</span>角色管理员：admin2/123456
		</div>
		<div>
			<span class="badge badge-important">4</span>用户管理员：admin3/123456
		</div>
		<div>
			<span class="badge badge-info">5</span>数据源管理员：admin4/123456
		</div>
		<div>
			<span class="badge badge-inverse">6</span>BUG管理员：admin5/123456
		</div>
		<div>
			<span class="badge">7</span>来宾用户：guest/123456
		</div>
	</div>
</div> -->