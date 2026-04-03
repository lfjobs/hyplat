var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

document.onkeydown = function(evt) {//捕捉回车
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        var barcode = $(".barcode").val();
        $(".barcode").val("");
         var  kt = barcode.substring(0,2);
        if (barcode.indexOf("bif")!=-1||barcode.indexOf("book")!=-1||kt=="99") {
                   if(kt=="99"){
                       if(barcode.length!=21){
                           return false;
                       }
                       barcode =  barcode.substring(2);
                   }

            $(".div-ts").show();
            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            try {
                if (isAndroid == true) {

                    Android.speechOutputForAndroid("扫码成功请稍后");
                } else {
                    console.log("请在安卓设备访问！");
                }

            }catch(error){

            }
            searchOrder(barcode);
            return false;
        }
    }
}
$(document).ready(function() {
    if(posNum!=""&&posNum!=null){
        $(".QR_btn").hide();
        $(".ScanFace").show();

        $(".barcode").focus();
        $("body").click(function(){
                $(".barcode").focus();

        });
    }

    ajax();
    //实时监听
    $('.search_inp').bind('input propertychange', function() {
        if($('.search_inp').val().length==11||$('.search_inp').val().length==0){
            $(".barcode").focus();
            pageNumber = 0;
            $(".exam_rec_con").empty();
            ajax();
        }

    });
    $('.search_inp').click(function() {
        event.stopPropagation();    //  阻止事件冒泡
    })
        //调用扫一扫
    $(".QR_btn").click(function () {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            Android.openCamear();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
            var url= "func=" + 'calliosJiShiShouFei';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    })

    //刷脸签到
    $(".ScanFace").click(function () {
        var storeID = companyId;
        if (companyId.length > 32) {
            storeID = companyId.substring(7);
        }
       Android.androidAcquireFaceID(storeID,companyname);
    })

    $(".order_btn").click(function () {
        if(companyId!=null && companyId!=""){
            if(ppID!=null && ppID!=""){
                document.location.href = basePath+"/ea/mappointment/ea_jumpToBuy.jspa?ppk.ppID="+ppID+"&companyId="+companyId+"&type=00";
            }else{
                alert("该驾校暂时未开放考车");
            }
        }else{
            document.location.href = basePath+"/ea/mappointment/ea_queryTheTest.jspa";
        }
    })
})

function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(".last").offset().top - $(".last").height() <= $(window)
                .height()) {
            if (pageNumber < pageCount) {
                if(s=="0"){
                    ajax();
                }else if(s=="1"){
                    ajax1();
                }
            }
        }
    }
}

function ajax() {
    var conditions = $(".search_inp").val();
    var url = basePath + "/ea/mappointment/sajax_ea_userRecord.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        data : {
            "sccId":sccId,
            "companyId":companyId,
            "pageForm.pageNumber" : pageNumber+1,
            "pageForm.pageSize":pageSize,
            "conditions":conditions,
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var jsonresult = eval("(" + data + ")");
            var pageForm = jsonresult.pageForm;
            var test = [];
            $(".last").removeClass("last");
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                $(pageForm.list).each(function(i, dom) {
                    if ($(pageForm.list).length - 1 == i) {
                        test.push("<div class='exam_rec_box last' onclick='details(this)' data-cbid='"+this[9]+"' data-sccid='"+this[8]+"'>");
                    } else {
                        test.push("<div class='exam_rec_box' onclick='details(this)' data-cbid='"+this[9]+"' data-sccid='"+this[8]+"'>");
                    }
                    test.push("<div class='exam_info_h'>");
                    test.push("<img src='"+basePath+this[0]+"' class='exam_headimg' alt=''  onerror=\"this.src=\'' + basePath + '/images/boy.png\'\">");
                    var tels = this[1];
                    if(posNum!=null&&posNum!=""){
                        tels = tels.substring(0,7)+"****";
                    }
                    test.push("<span>"+tels+"</span>");
                    test.push("<span class='exam_info_time'>"+this[2]+"</span>");
                    test.push("</div>");
                    test.push("<div class='exam_time'>");
                    test.push("<div class='exam_time_h'>");
                    test.push("<i class='begin_time'>始</i>");
                    if(this[3]==null){
                        test.push("<span>未签到</span>");
                    }else{
                        test.push("<span>"+this[3]+"</span>");
                    }
                    test.push("</div>");
                    test.push("<div class='exam_time_h'>");
                    test.push("<i class='end_time'>终</i>");
                    if(this[4]==null){
                        test.push("<span>未签退</span>");
                    }else{
                        test.push("<span>"+this[4]+"</span>");
                    }
                    test.push("</div>");
                    var a;
                    if(this[5]=="00"){
                        if(this[2]==getNowFormatDate()){
                            a="未签到";
                        }else{
                            a="已失效";
                        }
                    }else if(this[5]=="01" || this[5]=="03"){
                        a="已签到";
                    }else if(this[5]=="02"){
                        a="已签退";
                    }else if(this[5]=="04"){
                        a="待支付";
                    }
                    test.push("<div class='seam_state finished'>"+a+"</div>");
                    test.push("</div>");
                    test.push("</div>");
                })
                $(".my").append(test.join(""));
                $(".my").removeClass("no_thing");
            }else{
                $(".my").addClass("no_thing");
            }

            if (pageForm != null) {
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                if (pageNumber < pageCount) {
                    getHeight();
                }
            }
        }
    })
}

function ajax1() {
    var url = basePath + "/ea/mappointment/sajax_ea_myRecord.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        data : {
            "sccId":sccId,
            "companyId":companyId,
            "pageForm.pageNumber" : pageNumber+1,
            "pageForm.pageSize":pageSize,
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var jsonresult = eval("(" + data + ")");
            var pageForm = jsonresult.pageForm;
            var test = [];
            $(".last").removeClass("last");
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                $(pageForm.list).each(function(i, dom) {
                    if ($(pageForm.list).length - 1 == i) {
                        test.push("<div class='exam_rec_box last' onclick='details(this)' data-cbid='"+this[9]+"' data-sccid='"+this[8]+"'>");
                    } else {
                        test.push("<div class='exam_rec_box' onclick='details(this)' data-cbid='"+this[9]+"' data-sccid='"+this[8]+"'>");
                    }
                    test.push("<div class='exam_info_h'>");
                    test.push("<img src='"+basePath+this[0]+"' class='exam_headimg' alt='' onerror=\"this.src=\'' + basePath + '/images/boy.png\'\">");
                    test.push("<span>"+this[1]+"</span>");
                    test.push("<span class='exam_info_time'>"+this[2]+"</span>");
                    test.push("</div>");
                    test.push("<div class='exam_time'>");
                    test.push("<div class='exam_time_h'>");
                    test.push("<i class='begin_time'>始</i>");
                    if(this[3]==null){
                        test.push("<span>未签到</span>");
                    }else{
                        test.push("<span>"+this[3]+"</span>");
                    }
                    test.push("</div>");
                    test.push("<div class='exam_time_h'>");
                    test.push("<i class='end_time'>终</i>");
                    if(this[4]==null){
                        test.push("<span>未签退</span>");
                    }else{
                        test.push("<span>"+this[4]+"</span>");
                    }
                    test.push("</div>");
                    var a;
                    if(this[5]=="00"){
                        if(this[2]==getNowFormatDate()){
                            a="未签到";
                        }else{
                            a="已失效";
                        }
                    }else if(this[5]=="01" || this[5]=="03"){
                        a="已签到";
                    }else if(this[5]=="02"){
                        a="已签退";
                    }else if(this[5]=="04"){
                        a="待支付";
                    }
                    test.push("<div class='seam_state finished'>"+a+"</div>");
                    test.push("</div>");
                    test.push("</div>");
                })
                $(".user").append(test.join(""));
            }else{
                $(".user").addClass("no_thing");
            }

            if (pageForm != null) {
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                if (pageNumber < pageCount) {
                    getHeight();
                }
            }
        }
    })
}





function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
    return currentdate;
}




function details(obj) {
    if(posNum!=""&&posNum!=null){
        var search = $(".search_inp").val();
        if(search!=""&&search!=null&&search.length==11){
           search = "search";
        }else{
            search=""
        }
         $(".search_inp").val("");
        document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+$(obj).attr("data-cbid")+"&sccId="+$(obj).attr("data-sccid")+"&dp=1&search="+search+"&dz=1";

    }else{
        document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+$(obj).attr("data-cbid")+"&sccId="+$(obj).attr("data-sccid");

    }
}



function back() {
    if(companyId!=null && companyId!=""){
        javascript: window.history.go(-1);
        return false;
    }else{
    	if(sc=="web"){
    		window.history.go(-1);
            return false;
    	}else{
    		
    		try{
    	      var u = window.navigator.userAgent;
              var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
              var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
              if (isAndroid == true) {
                 console.log("安卓");
                 Android.callAndroidjianli();// 调用安卓接口
              } else if (isiOS == true) {
        	        console.log("IOS");
        	        var url = "func=" + 'doneClose';
    				window.webkit.messageHandlers.Native.postMessage(url);
             }
              
    	}catch(error){
    		
    		window.history.go(-1);
            return false;
    	}
    	}
    }
}
function  searchOrder(code){
    if(code.indexOf("book")!=-1){
        document.location.href = code;
        return false;
    }
   var url = basePath+"/ea/restaurant/sajaxj_ea_scancode.jspa?scancode=04"+code;

    $.ajax({
        url : encodeURI(url),
        type : "get",
        data : {
            "pos":"pos",
        },
        dataType : "json",
        async : false,
        success : function(data) {
            $(".div-ts").hide();
            var bl = data.bl;
            var os = data.os;
            var cbId = os[15];
            var xysccid = os[16];

       document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+cbId+"&sccId="+xysccid+"&dp=1&sm=1";
        },
        error:function(data){
            $(".div-ts").hide();
            console.log(data);
        }
    })
}
function dealFaceID(openid){
    var url =  basePath+"/ea/mappointment/sajax_ea_faceValidate.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "get",
        data : {
            openid:openid,
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var m = eval("("+data+")");
            var result = m.result;
            if(result=="0"||result=="1"){
                //没有账号，就要提示绑定手机号或有账号，没关联手机号，绑定手机号
                //跳转绑定手机号页面
                Android.speechOutputForAndroid("没有绑定预约手机号请绑定");
                document.location.href = basePath+"page/WFJClient/ShopOwner/bindWfjAccount.jsp?openid="+openid;

            }else if(result=="2"){
               //没有预约练车语音提醒
                Android.speechOutputForAndroid("没有预约记录，请先预约");
                prompt("没有预约记录请先预约");
            }else if(result=="3"){
                var signInState = m.signInState;

                if(signInState=="00"||signInState=="01"){
                    //有预约 如果00 ，01状态，调用签到或者签退
                    var bifId = m.bifId;
                    searchOrder(bifId);
                }else if(signInState=="04"){
                    //有预约 如果04状态，调用详情页面去付款
                    var cbId = m.cbId;
                    var sccId = m.sccId;
                    document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+cbId+"&sccId="+sccId+"&dp=1&sm=1";

                }
            }

        }
    })
}

function prompt(obj){
    if($("#prompt").css("display")!="none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function(){
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 2000);
}