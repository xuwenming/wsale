function ajaxPost(url, parameter, success,beforeSend,time) {
    if(!url) return;
    $.ajax({
        type: "POST",
        url: getUrl(url),
        data: parameter,
        dataType:"json",
        beforeSend:function(){
            if(beforeSend)
                beforeSend();
        },
        success:function (data) {
            if(success)
                success(data);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.log(XMLHttpRequest.status + " " + XMLHttpRequest.readyState + " " + textStatus + " " + errorThrown);
        },
        complete:function(XMLHttpRequest,textStatus){
            time = time || 500;
            if(time != -1) $.loading.close(time);
        }
    });
}

function ajaxPostSync(url, parameter, success,beforeSend,time) {
    if(!url) return;
    $.ajax({
        type: "POST",
        url: getUrl(url),
        data: parameter,
        dataType:"json",
        async : false,
        beforeSend:function(){
            if(beforeSend)
                beforeSend();
        },
        success:function (data) {
            if(success)
                success(data);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.log(XMLHttpRequest.status + " " + XMLHttpRequest.readyState + " " + textStatus + " " + errorThrown);
        },
        complete:function(XMLHttpRequest,textStatus){
            time = time || 500;
            if(time != -1) $.loading.close(time);
        }
    });
}

function href(url) {
    window.location.href = getUrl(url);
}

function replace(url) {
    window.location.replace(getUrl(url));
}

// TODO tokenId从cookie中取,测试tokenId:1D96DACB84F21890ED9F4928FA8B352B 正式环境去除
function getUrl(url) {
    var index = url.indexOf("api");
    if(index != 0) url = url.substring(index);
    var tokenId = $.cookie('tokenId') || '1D96DACB84F21890ED9F4928FA8B352B', _url = base + url;
    if(tokenId)
        _url += (url.indexOf("?") == -1 ? "?" : "&") + "tokenId=" + tokenId;
    return _url;
}

function removeTokenId(url) {
    return delQueStr(url, "tokenId");
}

function removeQueDefault(url) {
    return delQueStrs(url, ['tokenId', 'code', 'state', 'fromShare']);
}

function delQueStrs(url, refs) {
    for(var i=0; i<refs.length; i++) {
        url = delQueStr(url, refs[i]);
    }
    return url;
}

//删除参数值
function delQueStr(url, ref) {
    var str = "";

    if (url.indexOf('?') != -1)
        str = url.substr(url.indexOf('?') + 1);
    else
        return url;
    var arr = "";
    var returnurl = "";
    if (str.indexOf('&') != -1) {
        arr = str.split('&');
        for (i in arr) {
            if (arr[i].split('=')[0] != ref) {
                returnurl = returnurl + arr[i].split('=')[0] + "=" + arr[i].split('=')[1] + "&";
            }
        }
        return url.substr(0, url.indexOf('?')) + "?" + returnurl.substr(0, returnurl.length - 1);
    } else {
        arr = str.split('=');
        if (arr[0] == ref)
            return url.substr(0, url.indexOf('?'));
        else
            return url;
    }
}

var Util = {};
// 密码校验，6-20位字母,数字组合
Util.checkPassword = function(v){
    var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
    if(reg.test(v)) {
        return true;
    }

    return false;
};

// 手机号校验
Util.checkPhone = function(v) {
    var reg = /^0{0,1}(13[0-9]|15[0-9]|18[0-9]|177|176)[0-9]{8}$/;
    if(reg.test(v)) {
        return true;
    }
    return false;
};

Util.checkEmpty = function(v){
    var val = v.trim();
    if(val== "" || val.length == 0){
        return true;
    }
    return false;
};
Util.clearNoNum = function(obj){
    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
};
Util.arrayRemove = function(arr, v){
    var index = -1;
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == v) index = i;
    }
    if (index > -1) {
        arr.splice(index, 1);
    }
};
Util.noDate = function(type, text, image){
    var $div = $('<div class="nodata"></div>');
    type = type || 1;
    image = image || (type == 1 ? 'wsale/images/nodata-icon.png' : 'wsale/images/nodata2-icon.png');
    text = text || (type == 1 ? '暂无记录' : '暂无拍品');
    $div.append('<img src="'+base+image+'" class="nodata_img'+type+'"/>');
    $div.append('<div>'+text+'</div>');
    return $div;
};
Util.getTime = function(date) {
    if(!date || date == 'undefined') return;
    var now = new Date();
    if(typeof(date) == "string") {
        date = new Date(date.replace(/-/g,"/"));
    }
    var nowY = now.getFullYear(), nowM = now.getMonth() + 1, nowD = now.getDate(),
        y = date.getFullYear(), m = date.getMonth() + 1, d = date.getDate();
    var time;
    if(nowY == y && nowM == m && d == nowD - 1) {
        var ms = Math.floor ((now.getTime() - date.getTime()) / 1000 / 60 / 60);
        if(ms >= 24)
            time = '昨天 ' + date.format('HH:mm');
        else {
            if(ms == 0) {
                ms = Math.floor ((now.getTime() - date.getTime()) / 1000 / 60);
                time = ms + '分钟前';
            } else time = ms + '小时前';
        }
    } else if(nowY == y && nowM == m && d == nowD) {

        var nowH = now.getHours(), nowMi = now.getMinutes(), h = date.getHours(), mi = date.getMinutes();
        if(nowH == h && nowMi == mi) {
            time = '刚刚';
        } else if(nowH == h && nowMi != mi) {
            var ms = Math.floor ((now.getTime() - date.getTime()) / 1000 / 60);
            time = ms == 0 ? '刚刚' : ms + '分钟前';
        } else {
            var ms = Math.floor ((now.getTime() - date.getTime()) / 1000 / 60 / 60);
            if(ms == 0) {
                ms = Math.floor ((now.getTime() - date.getTime()) / 1000 / 60);
                time = ms + '分钟前';
            } else time = ms + '小时前';
        }
    } else {
        time = date.format('MM/dd HH:mm');
    }
    return time;
};

//克隆dom,并渲染text,data是存储的data,viewData展示的数据
Util.cloneDom = function(templateId,data,viewData, showType){
    var template;
    if(typeof templateId == "string")
        template = $("#"+templateId).clone();
    else
        template = templateId.clone();
    template.data(data);
    var view = viewData||data;
    var id = data.id||view.id;
    if(!id){
        template.removeAttr("id");
    }else{
        templateId = templateId+"_"+id;
        template.attr("id",templateId);
    }
    for(var i in view){
        template.find("[name='"+i+"']").each(function(){
            var _this = $(this), val;
            var d = _this.attr("data-name"); // 二级属性
            if(d) val = view[i][d];
            else val = view[i];
            if(val || val == 0){
                if(_this.hasClass('lazy'))
                    _this.attr("data-original",val);
                else {
                    if(_this.is("img")){
                        _this.attr("src",val);
                    }else{
                        _this.html(val);
                    }
                }
            }
        });
    }
    if(showType) template.css("display", showType);
    else template.css("display", "");

    return template;
};

//深度克隆json
Util.cloneJson = function(oldObject){
    var newObject = jQuery.extend(true, {}, oldObject);
    return newObject;
};

function wxPayCall(params, success, fail) {
    ajaxPost('api/pay/pay', params, function(data){
        if (data.success) {
            var obj = data.obj;
            if(parseInt(obj.agent) < 5) {
                alert("您的微信版本低于5.0无法使用微信支付");
                return;
            };
            WeixinJSBridge.invoke('getBrandWCPayRequest',{
                "appId" : obj.appId,              // 公众号名称，由商户传入
                "timeStamp": obj.timeStamp,       // 时间戳，自 1970 年以来的秒数
                "nonceStr" : obj.nonceStr,        // 随机串
                "package" : obj.packageValue,     // 商品包信息
                "signType" : obj.signType,        // 微信签名方式:
                "paySign" : obj.paySign           // 微信签名
            }, function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok") { // 成功
                    if(success)
                        success();
                } else { // 取消/失败
                   // alert("支付失败");
                    if(fail)
                        fail();
                }
            });
        } else {
            alert(data.msg);
        }
    }, function(){
        $.loading.load({type:2, msg:'支付中...'});
    });
}

$(function(){
    $('body').on('keyup', 'input.onlyNum', function(){
        $(this).val($(this).val().replace(/\D/g,''));
    }).on('paste', 'input.onlyNum', function(){
        $(this).val($(this).val().replace(/\D/g,''));
    });
});