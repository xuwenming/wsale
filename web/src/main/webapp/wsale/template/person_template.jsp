<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--我的关注列表--%>
<div id="my_atted_template" class="guanzhu-content" style="display: none;">
	<img class="guanzhu-touxiang" name="headImage" />
	<div class="guanzhu-name info-xinxi" name="nickname"></div>
	<div class="guanzhu-level">
		<img src="${pageContext.request.contextPath}/wsale/images/v2.png" class="guanzhu-vimg"  /> <span class="guanzhu-score">信誉：<scope name="credit">0</scope></span>
	</div>
</div>

<%--钻石店铺列表--%>
<div id="star_shop_template" class="guanzhu-content-1" style="display: none;">
	<div class="guanzhu-name info-xinxi" name="nickname" style="margin-top: 5px;"></div>
	<img class="guanzhu-touxiang" name="headImage" style="margin-top: 5px;"/>
	<div class="guanzhu-score-1">竞拍中：<span class="guanzhu-name" name="biddingNums"></span>&nbsp;单</div>
	<div class="guanzhu-level">
		<span class="guanzhu-score-1">成交额：<scope class="guanzhu-name" name="turnover" data-name="turnover">0</scope>&nbsp;元</span>
	</div>
</div>

<!-- 粉丝/屏蔽列表 -->
<div id="shieldorfans_template" class="faxian-link" style="display: none;">
	<div class="left-touxiang">
		<img class="fensi-touxiang" name="headImage"/>
	</div>
	<div class="right-guanzhu">
		<span class="guanzhu-btn" name="btnName"></span>
	</div>
	<div class="normal-text">
		<div name="nickname"></div>
		<div class="fensi-desc info-xinxi" name="bardian"></div>
	</div>
</div>

