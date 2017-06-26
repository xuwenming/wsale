<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--订单列表--%>
<div id="order_template" class="dingdan-list" style="display: none;">
	<div class="faxian-link">
		<div class="dingdan-right">
			<a style="display: inline;padding:10px 0 10px 5px" class="tel"><img class="phone-icon" src="${pageContext.request.contextPath}/wsale/images/phone-icon.png" /></a>
			<span class="status-text" name="statusName"></span>
		</div>
		<div class="normal-text userShop">
			<img class="dianpu-icon" src="${pageContext.request.contextPath}/wsale/images/dianpu-icon.png" />
			<span name="nickname" class="nickname"></span>
			<img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
		</div>
	</div>
	<div class="dingdan-content">
		<div class="dingdan-img">
			<img name="product" data-name="icon" />
			<%--<div class="dingdan-detail-img lazy" name="product" data-name="icon"></div>--%>
		</div>
		<div class="dingdan-content-flex">
			<div class="dingdan-title" name="product" data-name="content" style="-webkit-line-clamp:1">
			</div>
			<div class="dingdan-info">
				<div name="orderStatusName"></div>
				<!--<a class="money-more"><img class="money-icon" src="${pageContext.request.contextPath}/wsale/images/qiankuan-icon.png" /> 钱款 <img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" /></a>-->
				<div name="orderStatusTime"></div>
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

