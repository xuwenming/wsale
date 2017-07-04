<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--首页精拍列表--%>
<div id="home_best_template" class="bestItem" style="display: none;">
	<div class="seller">
		<div class="nickname" name="user" data-name="nickname"></div>
		<div class="attentionIt">
			<span class="attentioned">+&nbsp;</span>
			<span>关注</span>
		</div>
	</div>
	<div class="headImage"></div>

	<div class="swiper-container pIcon">
		<div class="swiper-wrapper pIconBox">
			<!--<div class="swiper-slide pIconImg" style="background-image: url('http://cdn01.weipaitang.com/img/20170523cac76757-eaac-41fb-9646-ecad73d0cc29-W800H800/w/640');">
			</div>
			<div class="swiper-slide pIconImg" style="background-image: url('http://cdn01.weipaitang.com/img/20170523YoZeM0PuGdBaj7oriILmkZ0vmNjhmR0yWyDCeq081-BEsiY-JJZtTa6x0CzJ6u1L-W640H640/w/640');">
			</div>-->
		</div>
		<div class="swiper-pagination"></div>
	</div>

	<div class="readNum new-readNum">
		<div class="info-xinxi home-best-img-title">我是图片的标题我是图片标题我是图片标题我是图片标题</div>
		<div class="home-best-item-text">
			<span class="home-best-money"> ￥200</span>&#x3000;
			<span>竞拍50次</span>
		</div>
		<img src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" style="width:13px;" />
		<span name="readCount">0</span>
	</div>
</div>

<div id="home_product_template" class="bestItem" style="display: none;">
	<div class="seller">
		<div class="nickname" name="user" data-name="nickname"></div>
		<div class="attentionIt">
			<span class="attentioned">+&nbsp;</span>
			<span>关注</span>
		</div>
	</div>
	<div class="headImage"></div>

	<div class="swiper-container pIcon">
		<div class="swiper-wrapper pIconBox">
			<!--<div class="swiper-slide pIconImg" style="background-image: url('http://cdn01.weipaitang.com/img/20170523cac76757-eaac-41fb-9646-ecad73d0cc29-W800H800/w/640');">
			</div>
			<div class="swiper-slide pIconImg" style="background-image: url('http://cdn01.weipaitang.com/img/20170523YoZeM0PuGdBaj7oriILmkZ0vmNjhmR0yWyDCeq081-BEsiY-JJZtTa6x0CzJ6u1L-W640H640/w/640');">
			</div>-->
		</div>
		<div class="swiper-pagination"></div>
	</div>

	<div class="readNum">
		<img src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" style="width:13px;" />
		<span name="readCount">0</span>
	</div>
</div>


