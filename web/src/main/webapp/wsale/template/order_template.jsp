<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--订单列表--%>
<div id="order_template" class="dingdan-list" style="display: none;">
	<div class="faxian-link">
		<div class="dingdan-right">
			<a style="display: inline;" class="tel"><img class="phone-icon" src="${pageContext.request.contextPath}/wsale/images/phone-icon.png" /></a>
			<span class="status-text" name="statusName">交易完成</span>
		</div>
		<div class="normal-text userShop">
			<img class="dianpu-icon" src="${pageContext.request.contextPath}/wsale/images/dianpu-icon.png" />
			<span name="nickname" class="nickname">小鱼丸瓷片</span>
			<img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
		</div>
	</div>
	<div class="dingdan-content">
		<div class="dingdan-img">
			<img name="product" data-name="icon" />
		</div>
		<div>
			<div class="dingdan-title" name="product" data-name="content" style="-webkit-line-clamp:1">
				福寿绵长-江南山水图-天地造(名家纯手工名家纯手工-江南山水图-天地造
			</div>
			<div class="dingdan-info">
				<div name="orderStatusName">成交金额：￥10.00</div>
				<!--<a class="money-more"><img class="money-icon" src="${pageContext.request.contextPath}/wsale/images/qiankuan-icon.png" /> 钱款 <img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" /></a>-->
				<div name="orderStatusTime">收货时间：10-12 13:24</div>
				<div name="other" style="display: none;"></div>
			</div>
		</div>
	</div>
	<div class="dingdan-opearte">
		<span>退货</span>
		<span>当面交易</span>
		<span>小二介入</span>
		<span>取消</span>
		<span>确认收货</span>
		<span>支付</span>
	</div>
</div>

