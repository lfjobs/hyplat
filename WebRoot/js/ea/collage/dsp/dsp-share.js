var ua = window.navigator.userAgent;
var isWeixin = ua.toLowerCase().indexOf('micromessenger') != -1;

var shareSDKInitialized = false

function initShareSDK() {
    if (!isWeixin) {
        return
    }

    try {
        var url = basePath + "ea/qrshare/sajax_ea_getJssdkConfig.jspa";
        var retUrl = location.href.split('#')[0];

        var config = null;

        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                retUrl: retUrl
            },
            success: function (data) {
                config = eval("(" + data + ")");
                console.log(data)
            }
        });

        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: config.appId, // 必填，公众号的唯一标识
            timestamp: config.timestamp, // 必填，生成签名的时间戳
            nonceStr: config.nonceStr, // 必填，生成签名的随机串
            signature: config.signature,// 必填，签名
            jsApiList: [
                "checkJsApi",
                "updateAppMessageShareData",
                "updateTimelineShareData"
            ] // 必填，需要使用的JS接口列表
        });

        wx.checkJsApi({
            jsApiList: [
                "updateAppMessageShareData",
                "updateTimelineShareData"
            ],
            success: function (res) {
                console.log(res)
            }
        })

        wx.ready(function (res) {
            console.log(res)
            shareSDKInitialized = true
        });

        wx.error(function (res) {
            console.log(res)
            shareSDKInitialized = false
        })
    } catch (e) {
        console.log(e)
    }
}

/**
 *
 * @param title 标题
 * @param desc 描述
 * @param link 链接
 * @param imgUrl 图片地址
 */
function updateShareData(title, desc, link, imgUrl) {
    if (!shareSDKInitialized){
        return
    }

    // 自定义“分享给朋友”及“分享到QQ”按钮的分享内容
    wx.updateAppMessageShareData({
        title, // 分享标题
        desc, // 分享描述
        link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        imgUrl, // 分享图标
        success: function () {
            // 设置成功
            console.log('分享给朋友配置成功')
        }
    })

    // 自定义“分享给朋友”及“分享到QQ”按钮的分享内容
    wx.updateTimelineShareData({
        title, // 分享标题
        link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        imgUrl, // 分享图标
        success: function () {
            // 设置成功
            console.log('分享朋友圈配置成功')
        }
    })

    // 获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
    wx.onMenuShareWeibo({
        title, // 分享标题
        desc, // 分享描述
        link, // 分享链接
        imgUrl, // 分享图标
        success: function () {
            // 用户确认分享后执行的回调函数
            console.log('分享到腾讯微博成功')
        },
        cancel: function () {
            // 用户取消分享后执行的回调函数
            console.log('取消分享到腾讯微博')
        }
    });
}