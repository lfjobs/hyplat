/**
 * 常用js
 * 请放在jquery.js后面
 * 
 */
function getAndroidFlag() {
    // 获取元素并根据系统类型修改显示状态
    return  sessionStorage.getItem('AndroidApp');

}
const androidFlag="AndroidApp";
function setAndroidFlag(flag) {
    // 获取元素并根据系统类型修改显示状态
    sessionStorage.setItem(androidFlag, flag);
}
function setShowFromSystem(elementId) {
    // 获取元素并根据系统类型修改显示状态

    const pImgElement = document.getElementById(elementId);
    const systemType = sessionStorage.getItem(androidFlag);
    if (pImgElement) {
        if (systemType === androidFlag) {
            pImgElement.style.display = 'none';
            if (elementId==='p-img'){
                const parent = pImgElement.parentElement;
                parent.style.display='flex';
                parent.style.justifyContent='space-between';
            }
        } else {
            pImgElement.style.display = 'block';

        }
    }
}
function htmlEncode(str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&/g, "&amp;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    return s;
}

function htmlDecode(str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&amp;/g, "&");
    s = s.replace(/&lt;/g, "<");
    s = s.replace(/&gt;/g, ">");
    s = s.replace(/&#39;/g, "\'");
    s = s.replace(/&quot;/g, "\"");
    return s;
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
/////////////→ ↓↓↓↓↓↓↓cookie操作←↓↓↓↓↓↓↓/////////////////////////////
function setcookie(name,value){  
    var Days = 30;  
    var exp  = new Date();  
    exp.setTime(exp.getTime() + Days*24*60*60*1000);  
    document.cookie = name + "="+ encodeURIComponent (value) + ";expires=" + exp.toGMTString();
}
function getcookie(name){  
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));  
    if(arr != null){  
        return (arr[2]);  
    }else{  
        return "";  
    }  
} 
function delcookie(name){  
    var exp = new Date();  
    exp.setTime(exp.getTime() - 1);  
    var cval=getCookie(name);  
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();  
}

function clearAllCookie() {
    var date=new Date();
    date.setTime(date.getTime()-10000);
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    console.log("需要删除的cookie名字："+keys);
    if (keys) {
        for (var i =  keys.length; i--;)
            document.cookie=keys[i]+"=; expire="+date.toGMTString()+"; path=/";
    }
}
/////////////→ ↑↑↑↑↑↑↑cookie操作↑↑↑↑↑↑↑↑ ←/////////////////////////////


//格式化日期eg:yyyy-MM-dd
Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
     (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
     RegExp.$1.length == 1 ? o[k] :
     ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

/*文字提示*/
$(function() {
    var x = 10;
    var y = 20;
    $(".tooltip").mouseover(function(e) {
        this.myTitle = this.title;
        if (this.myTitle) {
            this.title = "";
            var tooltip = "<div id='tooltip'>" + this.myTitle + "</div>";
            $("body").append(tooltip); //把它追加到文档中
            $("#tooltip").css({ "top": (e.pageY + y) + "px", "left": (e.pageX + x) + "px" }).show("fast");   //设置x坐标和y坐标，并且显示
        }
    }).mouseout(function() {
        this.title = this.myTitle;
        $("#tooltip").remove();   //移除 
    }).mousemove(function(e) {
        $("#tooltip").css({ "top": (e.pageY + y) + "px", "left": (e.pageX + x) + "px" });
    });
});

/*图片自适应大小*/
function resizeImage(img, maxWidth, maxHeight, callback) {
    if (img.nodeName != "IMG") {
        return;
    }

    var image = new Image();
    image.src = img.src;
    if (img.complete) {
        if (image.width > maxWidth || image.height > maxHeight) {
            if (image.width / maxWidth > image.height / maxHeight) {
                img.style.width = maxWidth + "px";
            } else {
                img.style.height = maxHeight + "px";
            }
        }
        if (typeof callback == "function") {
            callback(img);
        }
    } else {
        image.onload = function() {
            if (this.width > maxWidth || this.height > maxHeight) {
                if (this.width / maxWidth > this.height / maxHeight) {
                    img.style.width = maxWidth + "px";
                } else {
                    img.style.height = maxHeight + "px";
                }
            }
            if (typeof callback == "function") {
                callback(img);
            }
        };
        image.onload();
    }
}
String.prototype.lengths = function() { return this.replace(/[^\x00-\xff]/g, "**").length; };
///字符串截取，中文算2个字符
String.prototype.substrs = function(len) {
    if (this == "" || len <= 0) {
        return "";
    } else if (this.lengths() <= len) {
        return this.toString();
    }
    var a = 0, i = 0, temp = '';
    for (; i < this.length; i++) {
        a += ((this.charCodeAt(i) > 255) ? 2 : 1);
        if (a >= len) break;
    }
    return this.substr(0, ++i);
};

String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g, ""); };
String.prototype.format = function() {
    var txt = this;
    i = arguments.length;
    while (i-- >= 0) {
        txt = txt.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return txt; 
};
/*
文本框，最大输入长度验证

length:最大输入长度 
targetId:需要验证的对象id
*/
function maxInput(textlen, targetId){
    var desc = $('#' + targetId);
    var clewid = targetId + "_word_count";
    var _msg = "字数不超过 {0} 字，还剩 <span id='{1}' style='color:Red;'>{2}</span> 字".format(textlen, clewid, (textlen - desc.val().replace(/[\n]/g, "**").length));
    desc.parent().append(_msg);


    desc.bind("keydown keyup blur", function() {
        var elsLength = 0;
        var value = desc.val();

        var len1 = value.replace(/[\n]/g, "**").length;
        if (len1 > textlen) {
            /*
            * 处理换行，因为换行符在C#中占一个字符长度，
            * 而在数据库中是占了两个字符长度。
            * 所以如果存在换行符，并且达到数据库字段的最大长度时会报异常         
            */
            var spare = len1 - textlen;
            var l = 0, i = 0;
            while (l < spare) {
                l += ((value[value.length - ++i] == "\n") ? 2 : 1);
            }
            value = value.substring(0, value.length - i);
            desc.val(value);
            elsLength = textlen - value.replace(/[\n]/g, "**").length;
        } else {
            elsLength = textlen - len1;
        }
        $("#" + clewid).html(elsLength);
    });
}

// 微信小程序隐藏头部和底部
const isWebview = localStorage.getItem('isWebview');
const isWebviewBool = isWebview === 'true';
if(isWebviewBool){
    $(".footer").hide();
    $("header").eq(0).hide();
}
