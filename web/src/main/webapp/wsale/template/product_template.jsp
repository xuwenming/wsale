<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--拍品列表--%>
<!--<a id="product_template" style="width:47%; margin:0 1%; font-size:14px; vertical-align:top; display: none">
	<img onerror="this.src='${pageContext.request.contextPath}/wsale/images/jsq-list2.png'" style="width:100%;" name="icon" />
	<div name="content">福寿绵长-江南山水图-天地造（名家纯手...）</div>
	<div style="margin-top:5px;">
		<div style="float:right; vertical-align:middle;">
			<span style="font-size:12px;"><img src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" style="width:13px;" /> <count name="readCount">36</count></span>
			<span style="font-size:12px;" name="likeCount"><img src="${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png" style="width:15px;" /> 48</span>
		</div>
		<div style="font-size:16px;margin-top:5px; color:#F56A22;" name="currentPrice">￥4088</div>
	</div>
</a>-->

<li id="product_template" style="display: none;">
	<a class="cbp-vm-image">
		<%--<img class="lazy" name="icon">--%>
		<div class="product-list-img lazy" name="icon"></div>
		<span class="others-time" style="display: none;">
			距截拍 <span class="others-timeno">00</span>:<span class="others-timeno">00</span>:<span class="others-timeno">00</span>
	 	</span>
	</a>
	<div class="cbp-vm-title">
		<span name="content" class="wupin-title info-xinxi" style="height: 25px;"></span>
		<div class="cbp-vm-time"><!--距截拍：<span class="cbp-vm-timenumber">10</span>时<span class="cbp-vm-timenumber">23</span>分<span class="cbp-vm-timenumber">23</span>秒--></div>
		<div>
			<div class="cbp-vm-right">
				<span style="font-size:12px;"><img src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" style="width:12px;" /> <count name="readCount">36</count></span>
				<span style="font-size:12px;" name="likeCount"><img src="${pageContext.request.contextPath}/wsale/images/guanzhu-icon.png" style="width:14px;" /> <count>48</count></span>
			</div>
			<div class="cbp-vm-price" name="currentPrice" style="margin-top: 0;">￥4088</div>
		</div>
	</div>

</li>

<a id="best_product_template" class="jingxuan-list" style="display:none;">
	<%--<img   class="jingxuan-image lazy" name="icon" />--%>
	<div class="jingxuan-img lazy" name="icon"></div>
	<div name="content" class="wupin-title info-xinxi" style="height: 25px;font-size: 14px;"></div>
	<div>
		<div class="jingxuan-info">
			<span style="font-size:12px;"><img src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" style="width:12px;" /> <count name="readCount"></count></span>
			<span style="font-size:12px;" name="likeCount"><img src="${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png" style="width:14px;" /> <count></count></span>
		</div>
		<div class="jingxuan-price" name="currentPrice">￥4088</div>
	</div>
</a>

<%--出价列表--%>
<li id="auction_template" style="display: none;">
	<div class="avatr">
		<!--<span class="order-sign" style="display: none;">状元</span>-->
	</div>
	<div class="order-right">
		<div class="order-right-top">
			<b name="user" data-name="nickname"></b>
			<img class="order-flag" src="${pageContext.request.contextPath}/wsale/images/chuju-icon.png" />
		</div>
		<p class="order-right-middle">(自动出价)</p>
		<div class="order-right-bottom">
			<span name="bid"></span>
			<b name="addtime"></b>
		</div>
	</div>
</li>
<!--<li id="auction_template" style="display: none;">
	<div class="order-sign" style="display: none;">状元</div>
	<img class="order-touxiang lazy" name="user" data-name="headImage" />
	<div class="order-right">
		<div style="float:right;">
			<img class="order-flag" src="${pageContext.request.contextPath}/wsale/images/chuju-icon.png" />
		</div>
		<div name="user" data-name="nickname">老同</div>
		<div style="margin-top:20px;">
			<div class="order-datetime" name="addtime">16-10-17 15:30</div>
			<div class="order-price order-price-orange" name="bid">￥4088</div>
		</div>
	</div>
</li>-->

<%--拍品管理列表--%>
<div id="product_manage_template" class="p-list" style="padding:5px;border-bottom:10px solid #f5f5f5;display: none;">
	<div style="text-align:left;padding:5px 0px;border-bottom:1px solid #eee;">
		<div class="paipin-img">
			<img name="icon"/>
		</div>
		<div class="paipin-img-after">
			<div name="content" class="paipin-title dingdan-title"></div>
			<div name="time" class="paipin-time"></div>
		</div>
	</div>
	<ul class="paipin-guanli dingdan-opearte">
		<li class="jp-classify">分类精选</li>
		<li class="jp-home">首页精选</li>
		<li class="putaway">上架</li>
		<li class="sold-out">下架</li>
	</ul>
</div>

<%--全部拍品列表--%>
<div id="product_detail_template" class="qbpp-detail" style="display: none;">
	<div class="ppxq-leftinfo">
		<div>
			<img class="ppxq-touxiang" onerror="this.src='${pageContext.request.contextPath}/wsale/images/user-default.png'"  name="user" data-name="headImage" />
			<div class="ppxq-level">
				<img name="user" data-name="positionIcon" style="width:50%;" />
			</div>
			<div>
				<%--<img class="qbpp-sixin" src="${pageContext.request.contextPath}/wsale/images/sixin-icon.png" />--%>
				<%--<div class="product-template-guanzhu">+ 关 注</div>--%>
				<div class="product-template-sixin">私 信</div>
				<%--<div class="product-template-jubao">举报</div>--%>
			</div>
		</div>
	</div>
	<div class="ppxq-rightinfo">
		<div class="qbpp-title new-qbpp-title" name="user" style="display: inline-block" data-name="nickname"></div>
		<div class="product-all-guanzhu attBtn">+ 关注</div>
		<div style="margin-bottom: -8px;clear: both;">
			<img class="ppxq-smallicon protection" src="${pageContext.request.contextPath}/wsale/images/baozhang-icon.png" style="display: none;"/>
			<img class="ppxq-smallicon auth" src="${pageContext.request.contextPath}/wsale/images/renzheng2-icon.png" style="display: none;"/>
			<img class="ppxq-smallicon" src="${pageContext.request.contextPath}/wsale/images/v2.png" />
		</div>
		<div class="ppxq-desc" name="product" data-name="content">
		</div>
		<div class="showMore hide">全文</div>
		<div class="images">
		</div>
		<div style="margin-top:10px;">
			<div class="qbpp-operate">
				<span><img class="qbpp-icon" style="width: 12px;"  src="${pageContext.request.contextPath}/wsale/images/huoyan-icon.png" /> <readCount name="product" data-name="readCount"></readCount></span>
				<span class="likeCount" name="likeCount"><img class="qbpp-icon" src="${pageContext.request.contextPath}/wsale/images/yiguanzhu-icon.png" /> 48</span>
				<span class="share-icon"><img class="qbpp-fenxiang" src="${pageContext.request.contextPath}/wsale/images/fenxiang-icon.png" />分享</span>
			</div>
			<span class="paipin-baoyou baoyou">包邮</span>
			<span class="paipin-baoyou baotui">3天包退</span>
			<span class="qbpp-xiajia xiajia-btn">下架</span>
		</div>
		<div class="likeList">
			<div style="margin:5px 0px;"></div>
		</div>
		<div class="auction-opt">
			<div>
				<!--<img src="${pageContext.request.contextPath}/wsale/images/zhengzaipaimai-icon.png" class="remai-icon" />-->
				<div style="font-size:12px; vertical-align: middle; display:inline-block; width:75%;">
					<div class="deadline"><font style="color: #E3F3FE;">拍卖倒计时：</font><span class="cbp-vm-timenumber">0</span>时<span class="cbp-vm-timenumber">0</span>分<span class="cbp-vm-timenumber">0</span>秒</div>
				</div>
			</div>
			<!--<div style="margin-top:5px;">
				<span class="jiage-operate sub_btn">—</span>
				<span class="jiage-value"></span>
				<span style="padding:5px 7px;" class="jiage-operate add_btn">+</span>
				<span style="padding:5px 10px;" class="jiage-operate auction_btn">出价</span>
				<span class="jiage-operate auto_auction_btn">自动出价</span>
			</div>
			<div class="updateBid">
				<div>更新<i class="newbidTM"></i></div>
			</div>-->

			<div class="btn-con">
				<div class="btn-con-lf">
					<span class="jiage-operate lf sub_btn">—</span>
					<input class="jiage-value onlyNum" type="tel" maxlength="10"/>
					<span class="jiage-operate rg add_btn">+</span>
				</div>
				<div class="btn-con-rg">
					<span class="jiage-operate auction_btn">出价</span>
				</div>

				<div class="updateBid">
					<span>更新</span><i class="newbidTM"></i>
				</div>
			</div>

			<div class="notify">
				<span onclick="javascript:location.href='http://mp.weixin.qq.com/s/gujJIeeMDDXvg8Y-crvhOA';">竞拍须知</span>
				<a class="auto_auction_btn">设置自动出价</a>
			</div>
		</div>
		<!--<div>
			<div class="price-desc">
				<span class="icon-label">起</span>￥<span name="product" data-name="startingPrice"></span>元
				<span class="icon-label">加</span>￥<rangePrice class="rangePrice" name="rangePrice"></rangePrice>元
				<span class="icon-label">保</span>￥<span name="product" data-name="margin"></span>元
			</div>
			<div class="price-desc">
				<span class="icon-label">参考价</span>￥<span name="product" data-name="referencePrice"></span>元&nbsp;&nbsp;
				<span class="icon-label">延</span> 5分钟/次
			</div>
		</div>-->
		<div class="price-desc">
			<div class="price-desc-item">
				<span class="icon-label">起</span>
				<b>￥<span name="product" data-name="startingPrice"></span></b>
			</div>
			<div class="price-desc-item">
				<span class="icon-label">加</span>
				<b>￥<rangePrice class="rangePrice" name="rangePrice"></rangePrice></b>
			</div>
			<div class="price-desc-item">
				<span class="icon-label">保</span>
				<b>￥<span name="product" data-name="margin"></span></b>
			</div>
			<div class="price-desc-item">
				<span class="icon-label">参</span>
				<b>￥<span name="product" data-name="referencePrice"></span></b>
			</div>
			<div class="price-desc-item">
				<span class="icon-label">延</span>
				<b>5分钟</b>
			</div>
			<div class="price-desc-item">
				<span class="icon-label">一</span>
				<b>￥<span name="product" data-name="fixedPrice"></span></b>
			</div>
		</div>
		<div class="order-con">
			<ul class="order-list auctions" page-currPage="1">
			</ul>
			<div class="check-more">
				<a style="font-size:14px;">查看更多 ></a>
			</div>
		</div>
		<!--<div style="background-color:#f0f0f0; display: none;">
			<ul class="jingjia-order auctions">
			</ul>
			<div class="check-more">
				<a style="font-size:14px;">查看更多 ></a>
			</div>
		</div>-->
	</div>
</div>

<%-- 新品开拍列表 --%>
<div id="new_product_template" style="display: none; padding: 0px 20px;">
	<div class="xinpin-datetime">
		<span class="xinpin-time" name="addtime">2015年02月02日 12:33</span>
	</div>
	<div class="xinpin-model" name="newProduct">
	</div>
</div>

<a id="new_product_first_template" style="display: none;">
	<%--<img class="xinpin-largeimg lazy" name="icon" />--%>
	<div class="xinpin-large-img lazy " name="icon"></div>
	<div class="xinpin-title">
		<span style="margin-left:5px;" name="nickname">国家级玉雕大师张保国作品</span>
	</div>
</a>
<a id="new_product_other_template" class="xinpin-list" style="display: none;">
	<div class="xinpin-smallimg"><img class="lazy" name="icon" /></div>
	<div class="xinpin-desc" name="content">国家级玉雕大师张保国作品 和田玉白玉一品清廉 把件234.60g</div>
</a>

