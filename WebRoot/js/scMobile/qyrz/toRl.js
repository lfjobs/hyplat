var zffs = "3";
var ntoken = 0;
var token = 0;
var i1 = 0;
var i = 0;
var  h5_url = "";
var ddid = "";
let timer = null;
//调用ios/android
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function(){
    setTimeout(function(){ show()}, 2000);
    const btn = document.getElementById("aiBtn");

// 点击事件
    btn.addEventListener("click", async () => {
        // 防止重复点击
        if (btn.dataset.loading === "1") return;
        btn.dataset.loading = "1";

        // 启动动画
        let dots = 0;
        btn.innerText = "正在识别";
        timer = setInterval(() => {
            dots = (dots + 1) % 4;
            btn.innerText = "正在识别" + ".".repeat(dots);
        }, 400);

        try {
            // 调用异步方法
            await getCompanyInfoFromDoubao();
        } catch(e) {
            console.error(e);
        } finally {
            // 停止动画
            clearInterval(timer);
            btn.innerText = "点击识别";
            btn.dataset.loading = "0";
            timer = null;
        }
    });

    //支付完成或者失败
    $(".payresult").click(function(){
        serverQuery(1);
    });



    //行业
    $(".industryType").click(function(){
      $(".hyfl").show();
        $("#sel").text("请选择行业");
        getIndustry("");

    });
    //行业返回
    $(".hyback").click(function(){
        $(".hyfl").hide();
    });

    //查询子行业
    $(document).on("click",".hyfl li",function(){

        var codeID = $(this).attr("id");
        if(codeID!="") {
            $("#selid").val(codeID);
        }
        var codeValue = $(this).text();
        var codePID = $(this).attr("codepid-data");
        if($("#sel").text()=="请选择行业"){
            $("#sel").html("<span class='"+codePID+"' >"+codeValue+"</span>");
        }else {
            $("#sel").append("<span class='" + codePID + "' >/" + codeValue + "</span>");
        }

        getIndustry(codeID);
    });
    //按层级回退行业
    $(document).on("click","#sel span",function(){
        var codePID = $(this).attr("class");
        if(codePID!="") {
            $("#sel ." + codePID).nextAll().remove();
            $("#sel ." + codePID).remove();
        }else{
            $("#sel").text("请选择行业");
        }
        getIndustry(codePID);

    });

    //开户注册切换
    $(".ul-list li").click(function(){
        $(this).parent(".ul-list").find("li").removeClass("active");
        $(this).addClass("active");
        $(".div-list").children("div").hide();
        $(".div-list").children("div").eq($(this).index()).show();
    })

    //提交咨询
    $("#submit_btn").click(function(){
        var _name = $("#name").val();
        if(_name== ""){
            prompt("姓名不能为空");
            return false;

        }
        var tel = $("#tel").val();
        if(tel ==""){
            prompt("手机号不能为空");

            return false;
        }
        if(!ver_phone(tel,"zx")){

            return false;
        }
        var valnum = $("#valnum").val();
        if(!verCode(valnum)){
            return false;
        }

        if(ntoken==1){
            return false;
        }
        ntoken = 1;
        var url = basePath + "/ea/consult/sajax_ea_saveConsult.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            data: $('#form1').serialize(),
            dataType: "json",
            async: true,
            success: function (data) {
                prompt("提交成功！");
                document.form1.reset();
                clearInterval(clock); //清除js定时器
                $("#ver_btn").attr("disabled",false);
                $("#ver_btn").text('获取验证码');
                nums = 60; //重置时间

            }
        })
    })


     //提交认领
    $("#submitrl").click(function(){
        var industryType = $(".industryType").val();
        if(industryType== ""){
            prompt("请选择行业");
            return false;

        }

        var companyName = $(".companyName").val();
        if(companyName== ""){
            prompt("请填写准确企业名称");
            return false;

        }
        var shopname = $(".shopname").val();
        if(shopname== ""){
            prompt("请填写店铺名称");
            return false;

        }
        var companyPhone = $(".companyPhone").val();
        if(companyPhone== ""){
            prompt("请填写企业电话");
            return false;

        }

        var companyManager = $(".companyManager").val();
        if(companyManager== ""){
            prompt("请填写负责人姓名");
            return false;

        }
        var managertel = $(".managertel").val();
        if(managertel ==""){
            prompt("请填写负责人手机号");

            return false;
        }
        if(!ver_phone(managertel,"rz")){

            return false;
        }
        if(head=="show") {
            var valnum1 = $("#valnum1").val();
            if (!verCode1(valnum1)) {
                return false;
            }
        }
        if(token==1){
            return false;
        }
        token = 1;
        ajaxsut();

    });

    //切换支付方式
    $(".wfj11_015_choice").click(function(){
        $(".wfj11_015_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
        $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
        zffs = $(this).find(".second").find("img").attr("name");
    });

    $("#occlusion2").on("click",function(){
        $("#occlusion2").hide();
        $(".wfj11_015_buy_commit").fadeOut();
        $("body").removeClass("no-of");

    });

})


//确认订单 生成订单方法
function ajaxsut() {
    h5_url="";
    var morre = $("#claimForm").find("#morre").val();
     if(parseFloat(morre)=="0"){
        //提交加载
        $("#btn_gwc").find("p").text("正在努力创建企业平台...");

     }else{
         $("#btn_gwc").find("p").text("正在提交请稍后...");
     }
    $("#btn_gwc").show();
    var formData = $("#claimForm").serialize();
    formData = decodeURIComponent(formData,true);

    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_Shopping.jspa?d="+new Date();
    $.ajax({
        type : "GET",
        url : ulp+formData,
        async : true,
        dataType : "json",
        success : function(data) {
            $("#btn_gwc").hide();
            var json = eval('(' + data + ')');
            var login = json.login;
            var result = json.result;
            if(login=="login"){
                document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
                return false;
            }

            if(result=="2"){
                alert("该负责人手机号已认领过公司不能重复认领");
                return false;
            }else if(result=="1"){
                alert("该公司已经被认领不能重复认领");
                return false;
            }

            ddid = json.ddid;
            $(".journalNum").val(ddid);
            token = 0;
            if (json == null) {
                alert("数据提交失败。请重试");
            } else {
                if(parseFloat(morre)=="0"){
                    var jump = head=="show"?"load":"rz";
                   document.location.href = basePath+"page/scMobile/qyrz/toSuccess.jsp?comBz="+$(".companyName").val()+"&jump="+jump;
                }else{
                    $("body").addClass("no-of");
                    $("#occlusion2").css("z-index",$(".content").css("z-index")+1);
                    $("#occlusion2").show();
                    $(".wfj11_015_buy_commit").css("z-index",$("#occlusion2").css("z-index")+1);
                    $(".wfj11_015_buy_commit").fadeIn(1000);
                }

            }
        },
        error : function(data) {
            $("#btn_gwc").hide();
            alert("提交订单失败");
        }
    });

}


//点击确认支付
function zf() {
       var formData = $("#formsutm").serialize();
       formData = decodeURIComponent(formData,true);

        var money = $("#morre").val();

        if (typeof(ddid) == "undefined")
        {
            alert("订单提交失败，无法支付");
            return;
        }

         var goodsnames = goodsname+"("+companyname+")";
        if (zffs == '1') {
            var par = new Array();
            par.push(basePath);

            par.push("page/WFJClient/ShopOwner/wfjAlipay.jsp?");

            par.push("WIDout_trade_no=" + ddid);

            par.push("&WIDtotal_fee="+ money);

            par.push("&WIDsubject=" + goodsnames);

           par.push("&WIDbody=vip''");//订单描述


            par.push("&WIDit_b_pay=''");//超时时间
            par.push("&WIDextern_token=''");//钱包
            //window.location.href = encodeURI(par.join(""));
            _AP.pay(encodeURI(par.join("")));
        } else if (zffs == '3') {

            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            var attach = "vip";
            $("#btn_gwc").find("p").text("加载中...");
            $("#btn_gwc").show();
            if (!isWeixin) {

                if (ua.indexOf("browser") != -1||ua.indexOf("Browser") != -1||ua.indexOf("qq") != -1||ua.indexOf("safari") != -1) {

                    h5_url = $(".h5_url").val();
                    if(h5_url!=""){
                        $("#btn_gwc").hide();
                        if(isAndroid==true){
                            $("#weixinqr").show();
                            document.location.href = h5_url;
                        }else{
                            document.location.href = h5_url+"&redirect_url="+window.location.href;
                        }

                        return false;
                    }

                    if(goodsnames.length>126){
                        goodsnames = goodsnames.substring(0,126);
                    }
                    //浏览器
                    //app微信支付
                    $.ajax({
                        url:basePath+"ea/wfjshop/sajax_ea_h5WechatPay.jspa",
                        type:"get",
                        data:{
                            "payDto.orderId":ddid,
                            "payDto.totalFee":money,
                            "payDto.body":goodsnames,
                            "payDto.attach":attach
                        },
                        success:function suc(data){
                            $("#btn_gwc").hide();
                            var mb=eval("("+data+")");
                             h5_url = mb.h5_url;
                             $(".h5_url").val(h5_url);
                            $("#btn_gwc").hide();

                            if(isAndroid==true){

                                $("#weixinqr").show();
                                document.location.href = h5_url;
                            }else{

                                $("#occlusion2").hide();
                                $(".wfj11_015_buy_commit").hide();
                                var jump = head == "show" ? "load" : "rz";
                                var redirectUrl = basePath + "page/scMobile/qyrz/toSuccess.jsp?comBz="+$(".companyName").val()+"&jump="+jump+"&ddid="+ddid;
                                document.location.href = h5_url+"&redirect_url="+encodeURIComponent(redirectUrl);
                            }




                        }

                    });
                } else {




                    //app微信支付
                    $.ajax({
                        url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa",
                        type:"get",
                        data:{
                            "payDto.orderId":ddid,
                            "payDto.totalFee":money,
                            "payDto.body":goodsnames,
                            "payDto.attach":attach
                        },
                        success:function suc(data){
                            var mb=eval("("+data+")");
                            var appPackage=mb.appPackage;


                            var  appid = appPackage.appid
                            var partnerid = appPackage.partnerid;
                            var prepayid = appPackage.prepayid;
                            var packages = appPackage.packages;
                            var noncestr = appPackage.noncestr;
                            var timestamp = appPackage.timestamp;
                            var err_code = appPackage.err_code;
                            var sign = appPackage.sign;


                            $("#btn_gwc").hide();
                            try
                            {
                                if(isAndroid==true){
                                    Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,ddid,err_code,goodsnames,attach);
                                }else if(isiOS==true){
                                    var url= "func=" + 'ioscallappChat';
                                    params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':ddid,'errcode':err_code,'goodsname':goodsnames,'attach':attach};
                                    for(var i in params){
                                        url = url + "&" + i + "=" + params[i];
                                    }
                                    window.webkit.messageHandlers.Native.postMessage(url);
                                }
                            }
                            catch(err)
                            {

                            }

                        }

                    });
                }





            }else{
                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+goodsnames+"-"+money+"-"+staffID+"-1-1-"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

            }
        } else {
            $.ajax({
                url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
                type:"get",
                data:"fenlei=03&ddid="+ddid,
                success:function suc(data){
                    var mb=eval("("+data+")");
                    var succ=mb.succ;
                    var threeNo=mb.threeNo;
                    if(succ=="success"){
                        window.location.href=basePath+"page/WFJClient/suc/xxzf.jsp?threeNo="+threeNo+"&user1="+user1;

                    };
                }

            });

        }

}





//咨询验证码
var clock = '';
var nums = 60;
function sendCode(thisBtn) {
    if($.trim($("#ver_btn").text())!="获取验证码"){
        return false;
    }
    var _name = $("#name").val();
    if(_name == ""){
        prompt("姓名不能为空");
        return false;
    }
    var tel = $("#tel").val();
    if(tel ==""){
        prompt("手机号不能为空");
        return false;
    }
    if(!ver_phone(tel,"zx")){
        return false;
    }
    //发送短信
    $.ajax({
        cache : true,
        type :"POST",
        url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+tel,
        async :false,
        dataType : "json",
        success :function(data){
            var member = data;
            i = member.returna;
        }
    });

    $("#ver_btn").text(nums + '秒');
    clock = setInterval(doLoop, 1000); //一秒执行一次

}

//认领验证
var clock1 = '';
var nums1 = 60;
function sendCode1(thisBtn) {
    if($.trim($("#ver_btn1").text())!="获取验证码"){
              return false;
    }
    var tel = $(".managertel").val();
    if(tel ==""){
        prompt("请填写负责人手机号");
        return false;
    }
    if(!ver_phone(tel,"rz")){
        return false;
    }
    //发送短信
    $.ajax({
        cache : true,
        type :"POST",
        url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+tel,
        async :false,
        dataType : "json",
        success :function(data){
            var member = data;
            i1 = member.returna;
        }
    });
    $("#ver_btn1").attr("disabled",true); //将按钮置为不可点击
    $("#ver_btn1").text(nums1 + '秒');
    clock1 = setInterval(doLoop1, 1000); //一秒执行一次

}




function prompt(obj) {
    $("#btn_gwc").find("p").text(obj);
    if(!($("#btn_gwc").is(":animated"))){
        $("#btn_gwc").show();
        setTimeout(function () {
            $("#btn_gwc").animate({
                opacity: "0",
            },1000,function () {
                $("#btn_gwc").css("opacity","1");
                $("#btn_gwc").hide();
            })
        }, 1000);
    }

}

//手机号验证
function ver_phone(tel,source) {
    var Sreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
    if(tel ==""){
        prompt("手机号不能为空");
        return false;
    }else if(!Sreg.test(tel)){
        prompt("请输入正确格式电话号！");
        return false;
    }else {
        if(source=="zx") {
            if (isCheckin(tel, ppId)) {
                return true;
            } else {
                return false;
            }
        }else{
            if (checkAccount(tel)) {
                return true;
            } else {
                return false;
            }
        }

    }
}



function doLoop() {
    nums--;
    if (nums > 0) {
        $("#ver_btn").text(nums + '秒');
    } else {
        clearInterval(clock); //清除js定时器
        $("#ver_btn").attr("disabled",false);
        $("#ver_btn").text('获取验证码');
        nums = 60; //重置时间
    }
}
function doLoop1() {
    nums1--;
    if (nums1 > 0) {
        $("#ver_btn1").text(nums1 + '秒');
    } else {
        clearInterval(clock1); //清除js定时器
        $("#ver_btn1").attr("disabled",false);
        $("#ver_btn1").text('获取验证码');
        nums1 = 60; //重置时间
    }
}

//验证码验证
function verCode(valnum) {
    if(valnum==""){
        prompt("请填写验证码");
        return false;
    } else if(valnum!=i){
        prompt("验证码不正确");
        return false;
    }else {
        return true;
    }
}

//验证码验证
function verCode1(valnum) {
    if(valnum==""){
        prompt("请填写验证码");
        return false;
    } else if(valnum!=i1){
        prompt("验证码不正确");
        return false;
    }else {
        return true;
    }
}



// 判断手机号登记过
function isCheckin(tel,ppId){
    var bool = null;
    $.ajax({
        type :"POST",
        url : basePath+"/ea/consult/sajax_ea_checkIn.jspa",
        async :false,
        dataType : "json",
        data:{
            "consult.consultantPhone":tel,
            "consult.ppid":ppId
        },
        success :function(data){
            var member = eval("(" + data + ")");
            if(member.result==0){
                console.log("未登记，可以使用");
                bool = true;
            }
            else{
                prompt("您已提交过，无需重复提交！");
                $("#tel").val("");
                $("#tel").focus();

                bool = false;
            }
        },
        error:function(data){
            console.log("验证失败");
        }
    });

    return bool;

}




// 模拟异步识别方法
function getCompanyInfoFromDoubao() {
    return new Promise(resolve => {
        // 模拟5秒耗时
        setTimeout(() => {
            var bool = null;
            var companyName = document.querySelector(".companyName").value;

            $.ajax({
                type :"GET",
                url : basePath+"ea/qyrz/sajax_ea_getCompanyInfoFromDoubao.jspa",
                async :false,
                dataType : "json",
                data:{
                    companyName:companyName
                },
                success :function(data){
                    if (data)
                    {
                        var obj=null;
                        var result=null;
                        // 匹配花括号包起来的 JSON
                        const match = data.match(/\{[\s\S]*\}/);
                        if(match) {
                            try {
                                obj = JSON.parse(match[0]);
                                result = obj.output
                                    ?.flatMap(o => o.content ?? [])
                                    ?.find(c => c?.text?.result)
                                    ?.text?.result;
                            } catch (e) {

                            }
                        }else {
                             obj = JSON.parse(data);
                             result = obj.output
                                ?.flatMap(o => o.content ?? [])
                                ?.find(c => c?.text?.result)
                                ?.text?.result;
                        }
                        console.error("JSON解析", obj);
                        console.log(result.brand);
                        console.log(result.companyPhone);

                        document.querySelector('[name="company.industryType"]').value = result.market;
                        document.querySelector('[name="company.brandInfo"]').value = result.brand;
                        document.querySelector('[name="cdl.companyPhone"]').value = result.companyPhone;
                        document.querySelector('[name="cdl.companyManager"]').value = result.legal;
                        document.querySelector('[name="cdl.managertel"]').value = result.legalPhone;



                    }

                },
                error:function(data){
                    console.log("验证失败");
                }
            });
            resolve();
        }, 5000);
    });
}
// 从豆包获取公司信息


// 判断手机号是否注册过公司
function checkAccount(tel){
    var bool = null;
    $.ajax({
        type :"POST",
        url : basePath+"ea/qyrz/sajax_ea_checkAccount.jspa",
        async :false,
        dataType : "json",
        data:{
            tel:tel
        },
        success :function(data){
            var member = eval("(" + data + ")");
            if(member.result=="0"){
                console.log("不是公司可以入驻");
                bool = true;
            }
            else{
                // prompt("手机号已入驻过公司");
                // bool = false;

                // 允许一个手机号注册多家公司
                bool = true
            }
        },
        error:function(data){
            console.log("验证失败");
        }
    });

    return bool;

}

//获取行业
function getIndustry(codePID){
    $.ajax({
        url : basePath+"/ea/qyrz/sajax_ea_getIndustry.jspa",
        type :"POST",
        async :false,
        dataType : "json",
        data:{
            codePID:codePID
        },
        success :function(data){
            var member = eval("(" + data + ")");
            var industryList = member.industryList;
            if(industryList==null||industryList.length==0){
                 $(".hyfl").hide();
                $(".industryType").val($("#sel").text());
                $(".industryId").val($("#selid").val());
            }
            var html = new Array();
            var obj ;
            for(var i = 0;i<industryList.length;i++){
                obj = industryList[i];
                html.push("<li class='clearfix' id='"+obj.codeID+"' codepid-data='"+codePID+"'>");
                html.push("<p>"+obj.codeValue+"</p>");
                html.push("<p><img src='"+basePath+"/images/scMobile/qyrz/a.png'/></p>");
                html.push("</li>");

            }
            $(".hy").html(html.join(""));
        },
        error:function(data){
            console.log("获取行业失败");
        }
    });


}
//拨打电话
function getPhone(num) {
    if (num != null && num != ""){
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            if (confirm("确定呼叫?")) {
                Android.callPhone(num+"");
            }
        } else if (isiOS == true) {
            if (confirm("确定呼叫?")) {
                var url = "func=" + 'iosCallphone';
                params = {'phoneNum': num};
                for (var i in params) {
                    url = url + "&" + i + "=" + params[i];
                }
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        }
    }else {
        alert("无电话号码");
    }
}
//服务商模式H5支付查询订单状态
function serverQuery(c) {

    if(ddid=="") {
        ddid = $(".journalNum").val();
    }
    if (ddid == "") {
        return false;
    }
    $.ajax({
        url: basePath + "ea/wfjshop/sajax_ea_serverQuery.jspa",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            journalNum: ddid
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var trade_state = member.trade_state;

            if (trade_state == "SUCCESS") {

                var jump = head == "show" ? "load" : "rz";
                document.location.href = basePath + "page/scMobile/qyrz/toSuccess.jsp?comBz=" + $(".companyName").val() + "&jump=" + jump;
            } else {
                if (c == 1) {
                    $("#weixinqr").hide();
                    $("#btn_gwc").find("p").text("支付失败请重新支付");
                    $("#btn_gwc").show();
                    setTimeout(function () {
                        $("#btn_gwc").hide();

                    }, 2000);
                } else {
                    $("#weixinqr").show();
                    $("#btn_gwc").hide();
                }

                $("body").addClass("no-of");
                $("#occlusion2").css("z-index", $(".content").css("z-index") + 1);
                $("#occlusion2").show();
                $(".wfj11_015_buy_commit").css("z-index", $("#occlusion2").css("z-index") + 1);
                $(".wfj11_015_buy_commit").fadeIn(1000);


            }

        },
        error: function (data) {
            console.log("查询订单失败");
        }
    });

}

    function show(){
        h5_url = $(".h5_url").val();
        if(isAndroid==true){
            h5_url = $(".h5_url").val();
            if(h5_url!="") {
                $("#weixinqr").show();
                $("#btn_gwc").hide();
                $("body").addClass("no-of");
                $("#occlusion2").css("z-index",$(".content").css("z-index")+1);
                $("#occlusion2").show();
                $(".wfj11_015_buy_commit").css("z-index",$("#occlusion2").css("z-index")+1);
                $(".wfj11_015_buy_commit").fadeIn(1000);
            }else{
                $("#weixinqr").hide();
                $("#btn_gwc").hide();
            }
        }else if(isiOS==true){
            if(h5_url!="") {
                serverQuery(1);
            }
        }



}