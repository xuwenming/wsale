<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--帖子列表--%>
<a id="bbs_template" href="javascript:void(0);" style="margin-top:10px;height:70px;border-bottom:1px solid #ddd;display: none;">
	<div style="display:inline-block; vertical-align:middle;width:20%; text-align:center;">
		<img class="lazy" style="width:90%;height:60px;" name="icon"/>
		<%--<div class="bbsListImg"></div>--%>
	</div>
	<div style="display:inline-block;vertical-align:middle;width:78%; float:right;">
		<div style="float:right;display: none;" name="spIcon">

		</div>
		<div name="bbsTitle" class="tiezi-title" style="width: 85%;"></div>
		<div style="font-size:12px;color:#aaa; line-height:1.4;margin-top:8px">
			<div style="float: right;" name="time"></div>
			<div class="info-xinxi" name="name_time"></div>
			<div name="count"></div>
		</div>
	</div>
</a>

<a id="bbs_new_template" href="javascript:void(0);" style="margin-top:10px;height:70px;border-bottom:1px solid #ddd;display: none;">
	<%--<div style="display:inline-block; vertical-align:middle;width:20%; text-align:center;">--%>
	<div style="vertical-align:middle;text-align:center;float:left">
		<div class="img-parent">
			<%--<img class="lazy" style="width:90%;height:60px;" name="icon"/>--%>
			<%--<img class="lazy" style="height:60px;width:60px;float:left;" name="icon"/>--%>
			<div class="bbs-list-img lazy icon"></div>
		</div>
		<%--<img class="lazy" style="width:90%;height:60px;" name="icon"/>--%>
	</div>
	<%--<div style="display:inline-block;vertical-align:middle;width:78%; float:right;">--%>
	<div style="display:block;vertical-align:middle;padding-left: 75px;">
		<div style="float:right;display: none;" name="spIcon">
		</div>
		<div name="bbsTitle" class="tiezi-title" style="width: 85%;"></div>
		<div style="font-size:12px;color:#aaa; line-height:1.4;margin-top:8px">
			<div style="float: right;" name="time"></div>
			<div class="info-xinxi" name="name_time"></div>
			<div name="count"></div>
		</div>
	</div>
</a>
