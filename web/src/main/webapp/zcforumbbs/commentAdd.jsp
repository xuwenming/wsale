<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcForumBbs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
	var base = '${pageContext.request.contextPath}/';
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/strophe.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/web-im-1.1.2/websdk-1.1.2.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wsale/js/emoji.config.js" charset="utf-8"></script>
<script type="text/javascript">
	var userCombogrid;
	$(function() {
	 	parent.$.messager.progress('close');
		setTimeout(function(){
			userCombogrid = $('#userId').combogrid({
				url:'${pageContext.request.contextPath}/userController/queryUsers',
				panelWidth:460,
				width : 140,
				height : 29,
				idField:'id',
				textField:'nickname',
				mode:'remote',
				method:'post',
				nowrap : true,
				striped:true,
				columns:[[
					{field:'nickname',title:'昵称',width:150},
					{field:'mobile',title:'手机号',width:100},
					{field:'wechatNo',title:'微信号',width:100},
					{field:'utypeZh',title:'用户类型',width:100}
				]]
			});

			userCombogrid.next('span').find('input').focus(function(){
				userCombogrid.combogrid("showPanel");
			});
		}, 20);

		$('#commentSel').combobox({
			onChange : function(){
				showComment($('#commentSel').combobox("getText"));
			},
			onSelect : function(){
				showComment($('#commentSel').combobox("getText"));
			}
		});

		$('#commentSel').next('span').find('input').focus(function(){
			$('#commentSel').combobox("showPanel");
		});

		$('#form').form({
			url : '${pageContext.request.contextPath}/zcForumBbsController/addComment',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');

				var comment = $('#commentSel').combobox("getText");
				if(comment == '') {
					alert("请输入评论内容");
					isValid = false;
				}
				$("#comment").val(comment);

				if($('#userId').combogrid("getText") == $('#userId').combogrid("getValue")) {
					$('#userId').combogrid("setValue", "");
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
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});

		$('#emojiShortcut span').click(function(){
			var value = $(this).attr('value');
			var comment = $('#commentSel').combobox("getText");
			$('#commentSel').combobox("setText", comment + value);
			showComment(comment + value);
		});
	});

	function showComment(comment) {
		$('#showComment').html(WebIM.utils.parseEmoji(comment));
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">	
		<form id="form" method="post">		
			<input type="hidden" name="ids" value="${ids}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="10%">评论人</th>
					<td colspan="3">
						<input id="userId" name="userId"/>
						<span style="color: red;margin-left: 10px;">(*不选择系统随机模拟用户)</span>
					</td>
				</tr>
				<tr>
					<th>评论内容</th>
					<td colspan="3">
						<input type="hidden" name="comment" id="comment"/>
						<jb:select dataType="AC" name="commentSel" editable="true"></jb:select>
						<span style="color: red;margin-left: 10px;"  id="showComment"></span>
					</td>
				</tr>
				<tr>
					<td colspan="4" id="emojiShortcut">
						<span value="[em_1]">[em_1]=<img class="emoji" src="/wsale/images/face/1.gif"></span>；
						<span value="[em_63]">[em_63]=<img class="emoji" src="/wsale/images/face/63.gif"></span>；
						<span value="[em_65]">[em_65]=<img class="emoji" src="/wsale/images/face/65.gif"></span>；
						<span value="[em_71]">[em_71]=<img class="emoji" src="/wsale/images/face/71.gif"></span>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>