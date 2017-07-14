<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--订单列表--%>
<div id="im_template" class="dingdan-list" style="display: none;">
	<div class="faxian-link">
		<div class="dingdan-right">
			<a style="display: inline;padding:10px 0 10px 5px" class="tel"><img class="phone-icon" src="${pageContext.request.contextPath}/wsale/images/phone-icon.png" /></a>
			<span class="status-text" name="statusZh"></span>
		</div>
		<div class="normal-text userShop">
			<img class="dianpu-icon" src="${pageContext.request.contextPath}/wsale/images/dianpu-icon.png" />
			<span name="nickname" class="nickname"></span>
			<img class="more-icon" src="${pageContext.request.contextPath}/wsale/images/arrow-r.png" />
		</div>
	</div>
	<div class="dingdan-content">
		<div class="dingdan-img">
			<img name="bbs" data-name="icon" />
		</div>
		<div class="dingdan-content-flex">
			<div class="dingdan-title" name="bbs" data-name="bbsTitle" style="-webkit-line-clamp:1">
			</div>
			<div class="dingdan-info">
				<div name="amount"></div>
				<div name="time"></div>
				<div name="other" class="info-xinxi" style="display: none;"></div>
			</div>
		</div>
	</div>
	<div class="paipin-guanli dingdan-opearte">

	</div>
</div>

