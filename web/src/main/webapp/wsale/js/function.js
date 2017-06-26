// JavaScript Document

		$(function() {
			$(".first-category").css("max-height",window.screen.height*3/5 - 60);
			//var listHeight = window.screen.height-58;
			//$("#fenlei-list").css("height",listHeight);

			//$("#index-content").css("height",listHeight);
			
			$("body").on("click",".rightTop-more",function(e){
				$('.mask-layer').show();
				// TODO
				/*if($(".topmore-list").css("display")=="none"){
					$(".topmore-list").slideDown("fast");
				}else{
					$(".topmore-list").slideUp("fast");
				}*/
				$(".topmore-list").slideDown("fast");
				$(document).one("click", function(){
					var target = $(e.target);
					if(target.closest(".topmore-list").length == 0){
						$(".topmore-list").hide();
					}
				});
				e.stopPropagation();
				
			});
			
			/* TODO 1
			$("body").on("click",".apply-banmian",function(){
				$(this).css("color","#fff"); 
				$(this).css("background-color","#C87C1C");
				$(this).siblings().css("color","#C87C1C"); 
				$(this).siblings().css("background-color","#fff");
			});
			
			$("body").on("click","#fenlei-list li",function(){
				$(this).addClass("fenlei-active");
				$(this).siblings().removeClass("fenlei-active");
				var flId = $(this).attr("id");
				$("."+flId).show();
				$("."+flId).siblings().hide();
			});
			
			$("body").on("click",".tab-title li",function(){
				$(this).addClass("titletab-active");
				$(this).siblings().removeClass("titletab-active");
				var flId = $(this).attr("id");
				$("."+flId).show();
				$("."+flId).siblings().hide();
			});
			
			$("body").on("click",".rule-choose",function(){
				$(this).find("div").text("√");
				$(this).siblings().find("div").text("");
			});*/
			
			$("body").on("click",".tieziInfo-more",function(e){
				$('.mask-layer').show();
				var top = $(this).find('img').offset().top, left = $(this).find('img').offset().left + 10;
				$(".more-dialog").css({top:top,left:left}).slideDown("fast");
				$(document).one("click", function(){
					var target = $(e.target);
					if(target.closest(".more-dialog").length == 0){
						$(".more-dialog").hide();
					}
				});
				e.stopPropagation();
			});
			
			/* TODO 4
			$(".bianji-input").blur(function(){
				$(".bianji-content").show();
				$(".bianji-content").text($(".bianji-input").val());
				$(".bianji-area .ui-input-text").hide();
			});*/
			
			
			$( "[data-role='navbar']" ).navbar();
			$( "[data-role='header'], [data-role='footer']" ).toolbar();
			 
			//$(".GaugeMeter").gaugeMeter();
			
			 var bannerSwiper = new Swiper ('.bannerSwiper', {
				autoplay:6000,
				pagination: '.swiper-pagination'
			});
			
			$("body").on("click","#toubiao-btn,.tixian-btn,.smrz-link,.kaihu-btn,.bank-list li",function(){
				$(".shade-div,.alert-content").show();
			});
			$("body").on("click",".know-btn",function(){
				$(".shade-div,.alert-content,.alert-system,.alert-jiaoyimima,.alert-quitlogin").hide();
			});
			
			$("body").on("click",".acceptbank",function(){
				$(".shade-div,.acceptbank-list").show();
			});
			$("body").on("click",".close-acceptbank",function(){
				$(".shade-div,.acceptbank-list").hide();
			});
			
			$("body").on("click",".change-tel",function(){
				$(".shade-div,.alert-system").show();
			});
			$("body").on("click",".change-jiaoyi",function(){
				$(".shade-div,.alert-jiaoyimima").show();
			});
			$("body").on("click",".quitlogin-btn",function(){
				$(".shade-div,.alert-quitlogin").show();
			});
			
			$("body").on("click",".edit-info",function(){
				$(".shade-div,.biaoji-edit").show();
			});
			$("body").on("click",".info-list .shade-div,.cancel-edit",function(){
				$(".shade-div,.biaoji-edit").hide();
			});
			
			$("body").on("click","#mian-link",function(){
				$(".shade-div,.mian-yaoqing").show();
			});
			$("body").on("click","#friend-link",function(){
				$(".shade-div,.friend-yaoqing").show();
			});
			$("body").on("click",".ok-close",function(){
				$(".shade-div,.mian-yaoqing,.friend-yaoqing").hide();
			});
			
			$("body").on("click",".dingdan-showicon",function(){
				if($(".toubiao-dingdan").css("display")=="none"){
					$("#show-info").attr("src","images/show-1.png");
					$(".toubiao-dingdan").show();
					$("#arrow-icon").show();
				}else{
					$("#show-info").attr("src","images/show-2.png");
					$(".toubiao-dingdan").hide();
					$("#arrow-icon").hide();
				}
			});
			
			$("body").on("click","#toubiao-okbtn",function(){
				$(".shade-div,.alert-content").show();
			});
			
			$("body").on("click",".itemdetail-title li",function(){
				var tabId = $(this).attr("id");
				$("."+tabId).show();
				$("."+tabId).siblings().hide();
				$(this).addClass("current-tabtitle");
				$(this).siblings().removeClass("current-tabtitle");
			});
			
			$("body").on("click","#set-touxiang",function(){
				$("#file_head").click();
			});

			$("body").on("click",".switch-icon,.switch-btn",function(){
				var flag = $(this).parent().find(".switch-icon").attr("data-flag");
				if(flag=="true"){
					$(this).parent().find(".switch-btn").css("float","left");
					$(this).parent().find(".switch-icon").css("background-color","#ccc");
					$(this).parent().find(".switch-icon").attr("data-flag","false");
				}
				if(flag=="false"){
					$(this).parent().find(".switch-btn").css("float","right");
					$(this).parent().find(".switch-icon").css("background-color","#C87C1C");
					$(this).parent().find(".switch-icon").attr("data-flag","true");
				}
			});
			$(".ui-content").on("click",".product-style, .chooseCategory",function(){
				$(".mask-layer,.dialog-content").show();
			});

			$("body").on("click",".mask-layer",function(){
				$(".mask-layer,.dialog-content,.baotui-dialog,.second-style,.jingjia-dialog,.type-dialog, .subscribe").hide();
			});

			$("body").on("click",".secondstyle-list",function(){
				$(this).addClass("second-active");
				$(this).siblings().removeClass("second-active");
			});
			$("body").on("click",".retry-choose",function(){
				$(".dialog-content").show();
				$(".second-style").hide();
			});
			$("body").on("click",".choose-ok",function(){
				$(".mask-layer,.second-style").hide();
			});
			$("body").on("click",".first-style",function(){
				$(".dialog-content").hide();
				$(".second-style").show();
			});
			$("body").on("click",".baotui-choose",function(){
				$(this).css("color","#fff");
				$(this).css("background-color","#C87C1C");
				$(this).siblings().css("color","#C87C1C");
				$(this).siblings().css("background-color","#fff");
			});
			$("body").on("click",".sevenday-tui",function(){
				$(".mask-layer,.baotui-dialog").show();
			});

			$("body").on("click",".confirm-cancel",function(){
				$(".mask-layer,.baotui-dialog").hide();
			});
			$("body").on("click",".confirm-cancel,.confirm-ok",function(){
				$(".mask-layer,.baotui-dialog").hide();
			});

			$(".ui-content").on("click",".jjfd-set",function(){
				//$(".mask-layer,.jingjia-dialog").show();
				$('#rangePopup').wePopup();
			});

			/*我的*/
			$(".wode-paipin li,.wode-paipintitle li").click(function(){
				$(this).addClass("wodepaipin-active");
				$(this).siblings().removeClass("wodepaipin-active");
				var flId = $(this).attr("id");
				$("."+flId).show();
				$("."+flId).siblings().hide();
			});

			/*粉丝关注*/
			$(".fensi-list").on('click', '.right-guanzhu', function(){
				$(".mask-layer,.fensi-dialog").show();
			});
			$(".guanzhu-ok,.guanzhu-cancel,.mask-layer").click(function(){
				if(!$(this).hasClass('sp'))
					$(".mask-layer,.fensi-dialog").hide();
			});

			/*我的订单*/
			$(".wddd-tab li").click(function(){
				$(this).addClass("titletab-active");
				$(this).siblings().removeClass("titletab-active");
			});

			/*搜索*/
			$(".status-list").click(function(){
				var statusflag = $(this).find("img").attr("data-flag");
				if(statusflag == "true"){
					$(this).find("img").attr("src","images/radio-icon.png");
					$(this).find("img").attr("data-flag","false");
				}
				if(statusflag == "false"){
					$(this).find("img").attr("src","images/selectedradio-icon.png");
					$(this).find("img").attr("data-flag","true");
				}
			});

			/*余额筛选款项*/
			$(".shaixuan-btn").click(function(e){
				if($(".shaixuan-type").is(':hidden'))
					$(".shaixuan-type").show();
				else
					$(".shaixuan-type").hide();
				$(document).one("click", function(){
					var target = $(e.target);
					if(target.closest(".shaixuan-type").length == 0){
						$(".shaixuan-type").hide();
					}
				});
				e.stopPropagation();
			});


			/*他人店铺*/
			$(".others-tab li").click(function(){
				if($(this).index() == 3) return;
				$(this).addClass("titletab-active");
				$(this).siblings().removeClass("titletab-active");
				var flId = $(this).attr("id");
				$("."+flId).show();
				$("."+flId).siblings().hide();
			});


			/*他人主页*/
			$(".zhuye-guanzhu").click(function(){
				$(".mask-layer,.guanzhu-dialog").show();
			});
			$(".mask-layer,.pingbi-cancel").click(function(){
				$(".mask-layer,.guanzhu-dialog").hide();
			});
			/*系统信息*/
			$(".info-show").click(function(){
				var infoflag = $(this).attr("data-flag");
				if(infoflag == "down"){
					$(this).parent().parent().find(".hide-info").show();
					$(this).attr("data-flag","up");
					$(this).attr("src","images/up-icon.png");
				}
				if(infoflag == "up"){
					$(this).parent().parent().find(".hide-info").hide();
					$(this).attr("data-flag","down");
					$(this).attr("src","images/down-icon.png");
				}
			});

			/*视图模式切换*/
			$("body").on("click",".cbp-vm-icon", function(){
				var dataView = $(this).attr("data-view");
				if(dataView == "cbp-vm-view-grid"){
					$(".cbp-vm-time").show();
					$(".cbp-vm").removeClass("cbp-vm-view-grid");
					$(".cbp-vm").addClass("cbp-vm-view-list");
					$(this).attr("data-view","cbp-vm-view-list");
				}
				if(dataView == "cbp-vm-view-list"){
					$(".cbp-vm-time").hide();
					$(".cbp-vm").removeClass("cbp-vm-view-list");
					$(".cbp-vm").addClass("cbp-vm-view-grid");
					$(this).attr("data-view","cbp-vm-view-grid");
				}
			});


			$(".share-icon").click(function(){
				$('.share-list li:eq(1) .msg').text('发送链接');
				$('.share-list li:eq(2) .msg').text('用微信分享');
				//$(".mask-layer,.share-content").show();
				$('#sharePopup').wePopup();
			});
			$(".mask-layer").click(function(){
				$(".share-content").hide();
			});

			$(".text-desc").click(function(){
				$(".bank-layer,.support-bank").show();
			});
			$(".dialog-know").click(function(){
				$(".bank-layer,.support-bank").hide();
			});
			$(".jiepai-time li").click(function(){
				$(this).addClass("jiepaitime-active");
				$(this).siblings().removeClass("jiepaitime-active");
				var flId = $(this).attr("id");
				$("."+flId).show();
				$("."+flId).siblings().hide();
			});
			$(".reason-icon").click(function(){
				var infoflag = $(this).find("img").attr("data-flag");
				if(infoflag == "down"){
					$(this).parent().find(".reason-info").show();
					$(this).find("img").attr("data-flag","up");
					$(this).find("img").attr("src",base + "wsale/images/up-icon.png");
				}
				if(infoflag == "up"){
					$(this).parent().find(".reason-info").hide();
					$(this).find("img").attr("data-flag","down");
					$(this).find("img").attr("src",base + "wsale/images/down-icon.png");
				}
			});
		});
		$( document ).on( "pagecontainerchange", function() {
			// Each of the four pages in this demo has a data-title attribute
			// which value is equal to the text of the nav button
			// For example, on first page: <div data-role="page" data-title="Info">
			var current = $( ".ui-page-active" ).jqmData( "title" );
			// Change the heading
			$( "[data-role='header'] h1" ).text(current);
			// Remove active class from nav buttons
			$( "[data-role='navbar'] a.ui-btn-active" ).removeClass( "ui-btn-active" );
			// Add active class to current nav button
			$( "[data-role='navbar'] a" ).each(function() {
				if ( $( this ).text() === current ) {
					$( this ).addClass( "ui-btn-active" );
				}
			});
		});
