<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--我的关注列表--%>
<div id="my_atted_template" class="guanzhu-content" style="display: none;">
	<img class="guanzhu-touxiang" name="headImage"  onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'" />
	<div class="guanzhu-name info-xinxi" name="nickname"></div>
	<div class="guanzhu-level">
		<img src="${pageContext.request.contextPath}/wsale/images/v2.png" class="guanzhu-vimg"  /> <span class="guanzhu-score">信誉：<scope name="credit">0</scope></span>
	</div>
</div>

<!-- 粉丝/屏蔽列表 -->
<div id="shieldorfans_template" class="faxian-link" style="display: none;">
	<div class="left-touxiang">
		<img src="${user.headImage}" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'"  class="fensi-touxiang" name="headImage"/>
	</div>
	<div class="right-guanzhu">
		<span class="guanzhu-btn" name="btnName"></span>
	</div>
	<div class="normal-text">
		<div name="nickname"></div>
		<div class="fensi-desc info-xinxi" name="bardian"></div>
	</div>
</div>

