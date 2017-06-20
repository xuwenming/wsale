var conn = null, reOpen = false;
$(function() {
    connInit();
    setTimeout(function(){
        loginHx();
    }, 200);
});

function connInit() {
    conn = new WebIM.connection({
        isMultiLoginSessions: WebIM.config.isMultiLoginSessions,
        https: typeof WebIM.config.https === 'boolean' ? WebIM.config.https : location.protocol === 'https:',
        url: WebIM.config.xmppURL,
        isAutoLogin: true
    });
    conn.listen({
        onOpened: function onOpened(message) {
            console.log('onOpened', message);
            if(reOpen)
                setTimeout(function(){
                    $.toptip('连接成功', 'success');
                }, 1000);
        },
        onClosed: function onClosed() {
            console.log('onClosed');
        },
        onTextMessage: function onTextMessage(message) {
            console.log('onTextMessage', message);
            //console.log(WebIM.utils.parseEmoji(message.data));
            try{
                handleMessage(message, 'txt');
            }catch (e){}
            //Demo.api.appendMsg(message, 'txt');
        },
        onEmojiMessage: function onEmojiMessage(message) {
            console.log('onEmojiMessage', message);
            try{
                handleMessage(message, 'emoji');
            }catch (e){}
            //Demo.api.appendMsg(message, 'emoji');
        },
        onPictureMessage: function onPictureMessage(message) {
            console.log('onPictureMessage', message);
            //Demo.api.appendMsg(message, 'img');
            try{
                handleMessage(message, 'img');
            }catch (e){}
        },
        onAudioMessage: function onAudioMessage(message) {
            console.log('onError', message);
            try{
                handleMessage(message, 'aud');
            }catch (e){}
            //Demo.api.appendMsg(message, 'aud');
        },
        onLocationMessage: function onLocationMessage(message) {
            console.log('onError', message);
            //Demo.api.appendMsg(message, 'loc');
            try{
                handleMessage(message, 'loc');
            }catch (e){}
        },
        onFileMessage: function onFileMessage(message) {
            console.log('onError', message);
            //Demo.api.appendMsg(message, 'file');
            try{
                handleMessage(message, 'file');
            }catch (e){}
        },
        onVideoMessage: function onVideoMessage(message) {
            console.log('onError', message);
            //Demo.api.appendMsg(message, 'video');
            try{
                handleMessage(message, 'video');
            }catch (e){}
        },
        onOnline: function onOnline() {
            console.log('online');
        },
        onOffline: function onOffline() {
            console.log('offline');
        },
        onError: function onError(message) {
            console.log('onError', message);
        }
    });
}

function loginHx() {
    var options = {
        apiUrl: WebIM.config.apiURL,
        user: fromUser,
        pwd: hxPass,
        appKey: WebIM.config.appkey
    };

    conn.open(options);
}

WebIM.Emoji = {
    path: base + 'wsale/images/face/' /*表情包路径*/
    , map: {
        '[em_1]':'1.gif',
        '[em_2]':'2.gif',
        '[em_3]':'3.gif',
        '[em_4]':'4.gif',
        '[em_5]':'5.gif',
        '[em_6]':'6.gif',
        '[em_7]':'7.gif',
        '[em_8]':'8.gif',
        '[em_9]':'9.gif',
        '[em_10]':'10.gif',
        '[em_11]':'11.gif',
        '[em_12]':'12.gif',
        '[em_13]':'13.gif',
        '[em_14]':'14.gif',
        '[em_15]':'15.gif',
        '[em_16]':'16.gif',
        '[em_17]':'17.gif',
        '[em_18]':'18.gif',
        '[em_19]':'19.gif',
        '[em_20]':'20.gif',
        '[em_21]':'21.gif',
        '[em_22]':'22.gif',
        '[em_23]':'23.gif',
        '[em_24]':'24.gif',
        '[em_25]':'25.gif',
        '[em_26]':'26.gif',
        '[em_27]':'27.gif',
        '[em_28]':'28.gif',
        '[em_29]':'29.gif',
        '[em_30]':'30.gif',
        '[em_31]':'31.gif',
        '[em_32]':'32.gif',
        '[em_33]':'33.gif',
        '[em_34]':'34.gif',
        '[em_35]':'35.gif',
        '[em_36]':'36.gif',
        '[em_37]':'37.gif',
        '[em_38]':'38.gif',
        '[em_39]':'39.gif',
        '[em_40]':'40.gif',
        '[em_41]':'41.gif',
        '[em_42]':'42.gif',
        '[em_43]':'43.gif',
        '[em_44]':'44.gif',
        '[em_45]':'45.gif',
        '[em_46]':'46.gif',
        '[em_47]':'47.gif',
        '[em_48]':'48.gif',
        '[em_49]':'49.gif',
        '[em_50]':'50.gif',
        '[em_51]':'51.gif',
        '[em_52]':'52.gif',
        '[em_53]':'53.gif',
        '[em_54]':'54.gif',
        '[em_55]':'55.gif',
        '[em_56]':'56.gif',
        '[em_57]':'57.gif',
        '[em_58]':'58.gif',
        '[em_59]':'59.gif',
        '[em_60]':'60.gif',
        '[em_61]':'61.gif',
        '[em_62]':'62.gif',
        '[em_63]':'63.gif',
        '[em_64]':'64.gif',
        '[em_65]':'65.gif',
        '[em_66]':'66.gif',
        '[em_67]':'67.gif',
        '[em_68]':'68.gif',
        '[em_69]':'69.gif',
        '[em_70]':'70.gif',
        '[em_71]':'71.gif',
        '[em_72]':'72.gif',
        '[em_73]':'73.gif',
        '[em_74]':'74.gif',
        '[em_75]':'75.gif'
    }
}