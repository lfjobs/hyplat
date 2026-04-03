$(document).ready(function() {

    //获取经纬度,城市名称
    obtain();

    ajax();
})



function ajax() {
    var url = basePath + "/ea/mappointment/sajax_ea_queryPlan.jspa";
    $.ajax({
            url : encodeURI(url),
            type : "post",
            data : {
                "venueInformation.siteId":siteId
            },
            dataType : "json",
            async : true,
            success : function(data) {
                var jsonresult = eval("(" + data + ")");
                var list = jsonresult.list;
                var test = [];
                var test1 = [];
                if(list.length>0){
                    $(list).each(function(i, dom) {
                       var carType = this[6];
                       if(carType=="c"){

                           if(this[3].indexOf("金币计时")>=0){
                               $(".jlcxs").text("教练车："+this[4]+"/时");

                           }else {
                               test.push("<a href='javascript:void(0)' class='park_product_box clearfix'>");
                               test.push("<img src='"+basePath+"images/ea/office/jlctc.jpg' class='park_proimg' alt=''>");
                               test.push("<div class='park_pro_text'>");
                               var a;
                               var b;

                               if (this[3].indexOf("包年计时") >= 0) {
                                   a = "(包年)";
                                   b = "1年";
                               } else if (this[3].indexOf("包月计时") >= 0) {
                                   a = "(包月)";
                                   b = "1个月";
                               } else if (this[3].indexOf("包天计时") >= 0) {
                                   a = "(包天)";
                                   b = this[7] == "0" ? "仅限当天" : this[7] == "24" ? "限24小时" : "限8小时";
                               }
                               test.push("<div class='park_pro_tit'>" + this[2] + a + "</div>");
                               test.push("<div class='park_pro_time'>有效期：" + b + "</div>");
                               test.push("<div class='park_pro_price clearfix'>");
                               test.push("<span>￥" + this[4] + "</span>");
                               test.push("<span onclick='details(this)' data-carType='c' data-ppId='" + this[0] + "' data-goodsId='" + this[5] + "'>立即购买》</span>");
                               test.push("</div></div></a>");
                           }
                       }else{



                           if(this[3].indexOf("金币计时")>=0){
                               $(".sjcxs").text("私家车："+this[4]+"/时");

                           }else {
                               test1.push("<a href='javascript:void(0)' class='park_product_box clearfix'>");
                               test1.push("<img src='"+basePath+"images/ea/office/tcsfs.jpg' class='park_proimg' alt=''>");
                               test1.push("<div class='park_pro_text'>");
                               var a;
                               var b;
                               if (this[3].indexOf("包年计时") >= 0) {
                                   a = "(包年)";
                                   b = "1年";
                               } else if (this[3].indexOf("包月计时") >= 0) {
                                   a = "(包月)";
                                   b = "1个月";
                               } else if (this[3].indexOf("包天计时") >= 0) {
                                   a = "(包天)";
                                   b = this[7] == "0" ? "仅限当天" : this[7] == "24" ? "限24小时" : "限8小时";
                               }
                               test1.push("<div class='park_pro_tit'>" + this[2] + a + "</div>");
                               test1.push("<div class='park_pro_time'>有效期：" + b + "</div>");
                               test1.push("<div class='park_pro_price clearfix'>");
                               test1.push("<span>￥" + this[4] + "</span>");
                               test1.push("<span onclick='details(this)' data-carType='p' data-ppId='" + this[0] + "' data-goodsId='" + this[5] + "'>立即购买》</span>");
                               test1.push("</div></div></a>");
                           }
                       }

                    })
                }
                $(".jlc").append(test.join(""));
                $(".sjc").append(test1.join(""));
            }
        })
}

//场地详情
function details(obj) {
    var ppId = $(obj).attr("data-ppId");
    var goodsId = $(obj).attr("data-goodsId");
    var carType = $(obj).attr("data-carType");
    document.location.href = basePath +"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppId+"&goodsid="+goodsId+"&companyId="+compnayid+"&ccompanyId="+ccompanyid+"&carType="+carType;
}


function obtain(){
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    try {
        if (isAndroid == true) {
            console.log("安卓");
            var collection = Android.callgetLocal();//调用安卓接口
            if(collection!="-1"){
                var a = collection.split(",");
                city = a[0];//所在城市
                eastLongitude = a[1];//东经
                northLatitude = a[2];//北纬
                $(".park_area").text(city);
            }else{
                $(".park_area").text("未知");
            }
        } else if (isiOS == true) {
            console.log("IOS");
            var url= "func=" + 'calliosMapInfo';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }catch (err){
        console.log("err");
    }

}

function calliosMapInfo(name){
    if(name!="-1"){
        var a = name.split(",");
        city = a[0];//所在城市
        eastLongitude = a[1];//东经
        northLatitude = a[2];//北纬
        $(".park_area").text(city);
    }else{
        $(".park_area").text("未知");
    }
}