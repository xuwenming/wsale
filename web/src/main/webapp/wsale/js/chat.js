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