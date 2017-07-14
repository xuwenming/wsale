
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
  <title>编辑页面</title>
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
  <meta content="yes" name="apple-mobile-web-app-capable">
  <meta content="black" name="apple-mobile-web-app-status-bar-style">
  <meta content="telephone=no" name="format-detection">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/wsale/mynew/ui-my-edit.css"/>
</head>
<body>
<div class="my-editor-web">
    <div class="editor-header">
      <span>删除</span>
      <div>
        <span>预览</span>
        <span>完成</span>
      </div>
    </div>
    <div class="editor-main">
      <div>
        <img src="${pageContext.request.contextPath}/wsale/images/touxiang-icon.png" alt=""/>
        <div>
          <div>
            <p><img src="${pageContext.request.contextPath}/wsale/images/add-img-large.png" alt=""/>点击添加标题</p>
            <p class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/model.png" alt=""/>模板</p>
          </div>
        </div>
      </div>
      <div class="editor-main-m">
        <span class="e-black-w"><img src="${pageContext.request.contextPath}/wsale/images/no-msic.png" alt=""/>暂无背景音乐</span>
        <span class="e-black e-black-y"><img src="${pageContext.request.contextPath}/wsale/images/music.png" alt=""/>音乐</span>
      </div>
    </div>

    <div class="e-add">
      <div style="position: relative">
        <div class="editor-add"><img src="${pageContext.request.contextPath}/wsale/images/add.png" alt=""/></div>
        <div class="editor-add-li">
          <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/pic.png" alt=""/><span>图片</span></div>
          <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/A.png" alt=""/><span>文字</span></div>
          <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/shipin.png" alt=""/><span>视频</span></div>
        </div>
      </div>
    </div>

    <div class="editor-added">
        <div class="added-del"><img src="${pageContext.request.contextPath}/wsale/images/del.png" alt=""/></div>
        <div class="added-relate">
          <div class="added-con">
            这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频这里是要发表的内容图片视频
            <img src="${pageContext.request.contextPath}/wsale/images/bg-img.jpg" alt=""/>
          </div>
          <div class="added-foo">
            <div class="foo-add"><img src="${pageContext.request.contextPath}/wsale/images/add-icon.png" alt=""/>添加图片</div>
            <div class="foo-or">
              <div class="foo-or-a">
                <img src="${pageContext.request.contextPath}/wsale/images/e-up-b.png" style="display: none">
                <img src="${pageContext.request.contextPath}/wsale/images/e-up.png" alt=""/>
              </div>
              <div  class="foo-or-b">
                <img src="${pageContext.request.contextPath}/wsale/images/e-down-b.png">
                <img src="${pageContext.request.contextPath}/wsale/images/e-down.png" style="display: none">
              </div>
            </div>
          </div>
        </div>
    </div>

    <div class="e-add">
      <div style="position: relative">
        <div class="editor-add"><img src="${pageContext.request.contextPath}/wsale/images/add.png" alt=""/></div>
        <div class="editor-add-li">
          <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/pic.png" alt=""/><span>图片</span></div>
          <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/A.png" alt=""/><span>文字</span></div>
          <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/shipin.png" alt=""/><span>视频</span></div>
        </div>
      </div>
    </div>

  <div class="editor-added">
    <div class="added-del"><img src="${pageContext.request.contextPath}/wsale/images/del.png" alt=""/></div>
    <div class="added-relate">
      <div class="added-con">
        <img src="${pageContext.request.contextPath}/wsale/images/bg-img.jpg" alt=""/>我要发表图片
      </div>
      <div class="added-foo">
        <div class="foo-add"><img src="${pageContext.request.contextPath}/wsale/images/add-icon.png" alt=""/>添加图片</div>
        <div class="foo-or">
          <div class="foo-or-a">
            <img src="${pageContext.request.contextPath}/wsale/images/e-up-b.png" style="display: none">
            <img src="${pageContext.request.contextPath}/wsale/images/e-up.png" alt=""/>
          </div>
          <div  class="foo-or-b">
            <img src="${pageContext.request.contextPath}/wsale/images/e-down-b.png">
            <img src="${pageContext.request.contextPath}/wsale/images/e-down.png" style="display: none">
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="e-add">
    <div style="position: relative">
      <div class="editor-add"><img src="${pageContext.request.contextPath}/wsale/images/add.png" alt=""/></div>
      <div class="editor-add-li">
        <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/pic.png" alt=""/><span>图片</span></div>
        <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/A.png" alt=""/><span>文字</span></div>
        <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/shipin.png" alt=""/><span>视频</span></div>
      </div>
    </div>
  </div>

  <div class="editor-added">
    <div class="added-del"><img src="${pageContext.request.contextPath}/wsale/images/del.png" alt=""/></div>
    <div class="added-relate">
      <div class="added-con">
        <video width="100%" height="180px;" controls onclick="if(this.paused){this.play()} else{this.pause()}">
          <source src="${pageContext.request.contextPath}/wsale/mynew/audio-model.mp4" type="video/mp4">
          <source src="movie.ogg" type="video/ogg">
          您的浏览器不支持 HTML5 video 标签。
        </video>我要发布视频
      </div>
      <div class="added-foo">
        <div class="foo-add"><img src="${pageContext.request.contextPath}/wsale/images/add-icon.png" alt=""/>添加图片</div>
        <div class="foo-or">
          <div class="foo-or-a">
            <img src="${pageContext.request.contextPath}/wsale/images/e-up-b.png" style="display: none">
            <img src="${pageContext.request.contextPath}/wsale/images/e-up.png" alt=""/>
          </div>
          <div  class="foo-or-b">
            <img src="${pageContext.request.contextPath}/wsale/images/e-down-b.png">
            <img src="${pageContext.request.contextPath}/wsale/images/e-down.png" style="display: none">
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="e-add">
    <div style="position: relative">
      <div class="editor-add"><img src="${pageContext.request.contextPath}/wsale/images/add.png" alt=""/></div>
      <div class="editor-add-li">
        <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/pic.png" alt=""/><span>图片</span></div>
        <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/A.png" alt=""/><span>文字</span></div>
        <div class="e-black"><img src="${pageContext.request.contextPath}/wsale/images/shipin.png" alt=""/><span>视频</span></div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
