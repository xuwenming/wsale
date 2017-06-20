<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TzcForumBbs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	var audioStatus = true;
	var imageStatus = true;
	$(function() {
	 	//parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/zcForumBbsController/add',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if($('#bbsType').combotree('getValue') == 'BT03') {
					if (audioStatus) {
						var completeLength = $('#audioQueue span[class="data complete"]').length;
						if(completeLength == 0) {
							alert('请上传语音');
							isValid = false;
						} else if(completeLength == 1) {
							var audioFileName = '';
							$('#audioQueue span[class="fileName"][fileName != ""]').each(function() {
								audioFileName = $(this).attr('fileName');
							});
							$('#audioFileName').val(audioFileName);
						} else {
							alert('系统最多支持您上传1个音频文件,请在提交前有选择的删除额外的文件');
						}
					} else  {
						alert("当前文件仍然处于数据传输中，请稍后上传");
						isValid = false;
					}
				}

				if (imageStatus) {
					var completeLength = $('#imageQueue span[class="data complete"]').length;
					if(completeLength == 0) {
						alert('请上传图片');
						isValid = false;
					} else if(completeLength <= 9) {
						var imageFileNames = '';
						$('#imageQueue span[class="fileName"][fileName != ""]').each(function() {
							var fn = $(this).attr('fileName');
							if (fn != '') {
								imageFileNames = fn + ';' + imageFileNames;
							}
						});
						$('#imageFileNames').val(imageFileNames);
					} else {
						alert('系统最多支持您上传1个音频文件,请在提交前有选择的删除额外的文件');
					}
				} else  {
					alert("当前文件仍然处于数据传输中，请稍后上传");
					isValid = false;
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

		$('#categoryId').combotree({
			url : '${pageContext.request.contextPath}/zcCategoryController/tree',
			parentField : 'pid',
			textFiled : 'name',
			lines : true,
			panelHeight : 'auto',
			required:true,
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
				$('#categoryId').treegrid('collapseAll');
			},
			onBeforeSelect:function(node){
				if(node.state){
					$("#cc").tree("unselect");
				}
			}
		});

		$('#bbsType').combobox({
			required:true,
			onSelect: function(data){
				if(data.value == 'BT03') {
					$('.audio').show();
				} else {
					$('.audio').hide();
					$("#audioFile").uploadify('cancel');
				}
			}
		});

		$("#audioFile").uploadify({
			'debug': false, //开启调试
			'auto': true, //是否自动上传
			'swf': '${pageContext.request.contextPath}/jslib/uploadify/uploadify.swf',  //引入uploadify.swf
			'uploader': '${pageContext.request.contextPath}/zcForumBbsController/upload',// 请求路径
			'queueID' : 'audioQueue',//队列id,用来展示上传进度的
			'width'     : '80',  //按钮宽度
			'height'    : '26',  //按钮高度
			'queueSizeLimit' : 1,  //同时上传文件的个数
			'fileTypeDesc':'支持的音频格式：mp3、wav、ogg',
			'fileTypeExts'   : '*.ogg;*.wav;*.mp3', // 控制可上传文件的扩展名
			'multi'          : false,  //允许多文件上传
			'preventCaching' : true,  // 设置随机参数，防止缓存
			'buttonText'     : '上传语音',//按钮上的文字
			'fileSizeLimit' : '150MB', //设置单个文件大小限制
			'fileObjName' : 'audioFile',  //<input type="file"/>的name
			'method' : 'post',
			'removeCompleted' : false,//上传完成后自动删除队列
			'successTimeout':99999,
			'onUploadStart' : function() {
				audioStatus = false;
			},
			'onFallback':function(){
				alert("您未安装FLASH控件，无法上传文件！请安装FLASH控件后再试。");
			},
			'onUploadSuccess' : function(file, data, response){ // 单个文件上传成功触发
				var data = jQuery.parseJSON(data);
				if (data.success) {
					//data就是action中返回来的数据
					$("#" + file.id).find('span[class="fileName"]').attr({'fileName' : data.obj});
					$("#" + file.id).find('span[class="data"]').addClass("complete").html(' - 上传成功');
					$("#" + file.id).find('div[class="uploadify-progress"]').remove();
				} else {
					$("#" + file.id).find('div[class="uploadify-progress"]').html("<font style='color: red;'>上传失败</font>");
				}
			},
			'onQueueComplete' : function(queueData) {
				audioStatus = true;
			}
		});

		$("#imageFile").uploadify({
			'debug': false, //开启调试
			'auto': true, //是否自动上传
			'swf': '${pageContext.request.contextPath}/jslib/uploadify/uploadify.swf',  //引入uploadify.swf
			'uploader': '${pageContext.request.contextPath}/zcForumBbsController/upload',// 请求路径
			'queueID' : 'imageQueue',//队列id,用来展示上传进度的
			'width'     : '80',  //按钮宽度
			'height'    : '26',  //按钮高度
			'queueSizeLimit' : 9,  //同时上传文件的个数
			'fileTypeDesc':'支持的图片格式：gif、jpg、jpeg、png、bmp',
			'fileTypeExts'   : '*.gif;*.jpg;*.jpeg;*.png;*.bmp', // 控制可上传文件的扩展名
			'multi'          : true,  //允许多文件上传
			'preventCaching' : true,  // 设置随机参数，防止缓存
			'buttonText'     : '上传图片',//按钮上的文字
			'fileSizeLimit' : '5MB', //设置单个文件大小限制
			'fileObjName' : 'imageFile',  //<input type="file"/>的name
			'method' : 'post',
			'removeCompleted' : false,//上传完成后自动删除队列
			'successTimeout':99999,
			'onUploadStart' : function() {
				imageStatus = false;
			},
			'onFallback':function(){
				alert("您未安装FLASH控件，无法上传文件！请安装FLASH控件后再试。");
			},
			'onUploadSuccess' : function(file, data, response){ // 单个文件上传成功触发
				var data = jQuery.parseJSON(data);
				if (data.success) {
					//data就是action中返回来的数据
					$("#" + file.id).find('span[class="fileName"]').attr({'fileName' : data.obj});
					$("#" + file.id).find('span[class="data"]').addClass("complete").html(' - 上传成功');
					$("#" + file.id).find('div[class="uploadify-progress"]').remove();
				} else {
					$("#" + file.id).find('div[class="uploadify-progress"]').html("<font style='color: red;'>上传失败</font>");
				}
			},
			'onQueueComplete' : function(queueData) {
				imageStatus = true;
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
					<th width="10%"><%=TzcForumBbs.ALIAS_BBS_TYPE%></th>
					<td width="40%">
						<jb:select dataType="BT" name="bbsType" value="BT01"></jb:select>
					</td>
					<th width="10%">所属分类</th>
					<td width="40%">
						<select id="categoryId" name="categoryId" style="width: 140px; height: 29px;"></select>
					</td>
				</tr>
				<tr>	
					<th><%=TzcForumBbs.ALIAS_BBS_TITLE%></th>
					<td colspan="3">
						<input class="easyui-validatebox span2" data-options="required:true" name="bbsTitle" type="text" style="width: 510px;"/>
					</td>							
				</tr>
				<tr>
					<th><%=TzcForumBbs.ALIAS_BBS_CONTENT%></th>
					<td colspan="3">
						<textarea style="width: 510px;height: 60px;" name="bbsContent" class="easyui-validatebox" data-options="required:true"></textarea>
					</td>
				</tr>
				<tr class="audio" style="display: none;">
					<td colspan="4">
						<input type="hidden" name="audioFileName" id="audioFileName" />
						<input type="file" id="audioFile" name="audioFile" />支持mp3、wma、ogg等格式，优先mp3格式!
						<div id="audioQueue" style="margin-top:5px;"></div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="hidden" name="imageFileNames" id="imageFileNames" />
						<input type="file" id="imageFile" name="imageFile" />支持gif、jpg、jpeg、png、bmp等格式!
						<div id="imageQueue" style="margin-top:5px;"></div>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>