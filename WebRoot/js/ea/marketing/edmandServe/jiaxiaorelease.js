$(function() {
    var btn = document.getElementById("send_code");
    var tel = $("#tel");
    var _name = $("#ddcontactname");
    var valnum = document.getElementById("valnum");
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    //提示框
    $("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
    $("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

    //加载一二级行业
    $("#selsct_classify").click(function() {
        $(".tab_box").show();
        $(".nest_bd").find(".classify_wrap").remove();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=3",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList=member.beanList;

                if(beanList.length > 0){
                    var htmls="<div class='classify_wrap'>";
                    var numl=0;
                    for (var i=0;i<beanList.length;i++){
                        var a = beanList[i];
                        var codeid=a[0];
                        var ans=a[2];
                        if(ans=="2"){
                            numl=numl+1;
                            htmls+="<div class='level_wrap'><div class='level_box'><i class='classify_ico list_ico_0"+numl+"'></i>";
                            htmls+="<div class='level_text'><div class='level_1'>"+a[3]+"<input type='hidden' class='oid' value='"+a[0]+"'/></div>";
                            htmls+="<div class='level_2'>";
                            for (var j=0;j<beanList.length;j++){
                                var b = beanList[j];
                                var codepid=b[1];
                                var bns=b[2];
                                if(codeid==codepid){
                                    htmls+=b[3]+"/<input type='hidden' class='tid' value='"+b[0]+"'/>";
                                }
                            }
                            htmls+="</div></div><i class='fold_ico'></i></div><div class='level_fold'></div></div>";
                        }
                    }
                    htmls+="</div>";
                    $(".nest_bd").append(htmls);
                }
            }
        });
        $(".nest_page").show();
        $(".tab_box").hide();
    });

    $(".nest_back").click(function() {
        $(".nest_page").hide();
        packInit();
    });

    //加载三四级行业
    $(document).on("click",".level_box",function() {
        $(".tab_box").show();
        var pid=$(this).find(".oid").val();
        var level_fold=$(this).parent().find(".level_fold");
        $(this).parent().find(".level_3").remove();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=5&pid="+pid,
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList=member.beanList;

                if(beanList.length > 0){
                    var htmls="";

                    for (var i=0;i<beanList.length;i++){
                        var a = beanList[i];
                        var codeid=a[0];
                        var ans=a[2];
                        if(ans=="4"){
                            var numl=0;
                            var claval="no_level_4";
                            htmls+="<div class='level_3 '><div class='level_3_tit '><i class='fold_level3'></i>"+a[3]+"<input type='hidden' class='tid' value='"+a[0]+"'/></div>";
                            htmls+="<div class='level_4 clearfix'>";
                            /*for (var j=0;j<beanList.length;j++){
                                var b = beanList[j];
                                var codepid=b[1];
                                var bns=b[2];
                                if(bns=="5"){
                                    if(codeid==codepid){
                                        numl=numl+1;
                                        htmls+="<div class='level_4_box'>"+b[3]+"<input type='hidden' class='fid' value='"+b[0]+"'/></div>";
                                    }
                                }
                            }*/
                            htmls+="</div>";
                            claval="";
                            htmls+="</div>";
                        }
                    }
                    level_fold.append(htmls);
                    level_fold.find(".level_3").each(function(){
                        var level_4=$(this).find(".level_4").html();
                        if(level_4==""){
                            $(this).addClass("no_level_4");
                            $(this).find(".level_4").remove();
                        }
                    });

                }
            }
        });

        $(".level_4").each(function() {
            $(this).slideUp(200);
        });

        $(this).parent().find(".level_fold").slideToggle(200)
            .end().find(".fold_ico").toggleClass("fold_up")
            .end().siblings().find(".level_fold").slideUp(200)
            .end().find(".fold_ico").removeClass("fold_up");

        $(".fold_level3").each(function() {
            $(this).removeClass("fold_up");
        });
        $(".tab_box").hide();
    });

    $(document).on("click",".level_3_tit",function() {
        var L_4 = $(this).parent().find(".level_4");
        if (L_4.length) {
            $(this).find(".fold_level3").toggleClass("fold_up").parent().parent().siblings().find(".fold_level3").removeClass("fold_up");
            L_4.slideToggle(200).parent().siblings().find(".level_4").slideUp(200);
        } else {
            var t = $(this).text();
            var b=$(this).find(".tid").val();
            $("#selsct_classify").val(t);
            $("#ddscodeid").val(b);
            $(".nest_page").hide();
            packInit();
            $(".tab_box").show();
            $("#bbb").hide();
        }
    });

    $(document).on("click",".level_4_box",function() {
        var a = $(this).parent().parent().find(".level_3_tit").text();
        var t = $(this).text();
        var b = $(this).find(".fid").val();
        $("#selsct_classify").val(a+">"+t);
        $("#ddscodeid").val(b);
        $(".nest_page").hide();
        packInit();
    });

    $(".release_rec").click(function () {
        document.location.href=basePath+"/ea/dserve/ea_detailListBySccid.jspa?sccid="+sccId;
    });


    //初始化折叠选择行业分类
    function packInit() {
        $(".level_fold").each(function() {
            $(this).slideUp(200);
        });
        $(".level_4").each(function() {
            $(this).slideUp(200);
        });
        $(".fold_ico,.fold_level3").each(function() {
            $(this).removeClass("fold_up");
        });
    }

    $(".d_r_site").click(function(){
        if(isAndroid){
            Android.callgetRoundLocal();
        }else if(isiOS){
            var url= "func=" + 'iosMapaddress';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    });

    valnum.addEventListener("blur",verCode);

    //处理浏览器输入法遮挡
    var screenH = window.innerHeight;
    window.onresize = function() {
        var t = window.innerHeight;
        console.log(t);
        console.log(screenH);
        var inp = $("input:focus")[0];
        if (t < screenH) {
            inp.scrollIntoView(false);
        }
    };

    //选择时间插件
    var currYear = (new Date()).getFullYear();
    var opt = {};
    opt.date = {
        preset: 'date'
    };

    opt.default = {
        theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式
        mode: 'scroller', //日期选择模式
        dateFormat: 'yyyy-mm-dd',
        lang: 'zh',
        showNow: true,
        nowText: "今天",
        startYear: currYear - 10, //开始年份
        endYear: currYear + 10 //结束年份
    };


    var optDate = $.extend(opt['date'], opt['default']);
    $("#begin_time").mobiscroll(optDate).date(optDate);

    //时间判断

    $("#begin_time").change(function() {
        var cur_time = getNowFormatDate();

        //console.log(cur_time);
        var t = $(this).val();
        var d = new Date(t).getTime(); //开始时间
        //console.log(d);
        if (d < cur_time) {
            console.log("请选择大于当前时间");
            $(this).val('');
        }
    })
    $("#end_time").change(function() {
        var begin = $("#begin_time").val(); //开始时间
        var end = $(this).val(); //结束时间
        if (begin) {
            if (end < begin) {
                console.log("请选择大于开始时间的时间");
                $(this).val('');
            }
        } else {
            console.log("请先选择开始时间");
            $(this).val('');
        }

    });

});

//发送验证码
function sendCode(thisBtn) {
    if($("#ddcontactname").val() == ""){
        prompt("姓名不能为空");
        return false;
    }
    if($("#tel").val() ==""){
        prompt("手机号不能为空");
        return false;
    }
    if(!ver_phone()){
        return false;
    }
    //发送短信
    $.ajax({
        cache : true,
        type :"POST",
        url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+tel.value,
        async :false,
        dataType : "json",
        success :function(data){
            var member = eval("(" + data + ")");
            i = member.returna;
        }
    });

    btn = thisBtn;
    btn.disabled = true; //将按钮置为不可点击
    btn.innerHTML = nums + '秒重新获取';
    clock = setInterval(doLoop, 1000); //一秒执行一次
    btn.className = "send_code disabled";
}

//验证码验证
function verCode() {
    if($("#valnum").val()==""){
        prompt("请填写验证码");
        return false;
    } else if($("#valnum").val()!=i){
        prompt("验证码不正确");
        return false;
    }else {
        return true;
    }
}

function doLoop() {
    nums--;
    if (nums > 0) {
        btn.innerHTML = nums + '秒重新获取';
    } else {
        clearInterval(clock); //清除js定时器
        btn.disabled = false;
        btn.innerHTML = '获取验证码';
        nums = 60; //重置时间
        btn.className = "send_code";
    }
}


//手机号验证
function ver_phone() {
    var Sreg= /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if($("#tel").val() ==""){
        prompt("手机号不能为空");
        return false;
    }else if(!Sreg.test($("#tel").val())){
        prompt("请输入正确格式电话号！");
        return false;
    }else {
        return true;
    }
}

function save_submit(){
    var b=false;
    $(".notNull").each(function(){
        var nnval=$(this).val();
        if(nnval==null||nnval==""){
            prompt("请把信息填写完整");
            b=true;
            return false;
        }
    });

    if(b){
        return false;
    }

    if($("#valnum").val()!=i){
        prompt("验证码不正确");
        return false;
    }
    $(".tab_box").show();
    $(".nest_bd").find(".classify_wrap").remove();

    $.ajax({
        cache: true,
        type: "POST",
        url: basePath + "ea/dserve/sajax_ea_saveDetail.jspa?",
        async: false,
        dataType: "json",
        data:$('#savedetailform').serialize(),
        success: function (data) {
            var flag=data.flag;
            if(flag=="操作成功"){
                document.location.href=basePath+"ea/dserve/ea_toPage_success.jspa?tle="+tle;
            }else{
                prompt(flag);
            }
        }
    });
    $(".tab_box").hide();
}

/**************************定位获取地址开始************************/
function ios_address(param){
    var p=param.substring(0,param.indexOf(">"));
    var address=p.substring(0,p.indexOf("<"));
    var jv=p.substring(p.indexOf("<")+1);
    var j=jv.substring(1,jv.indexOf(","));
    var v=jv.substring(jv.indexOf(",")+2);
    $("#ddaddress").val(address);
    $("#coordinate").val(j+","+v);
}

function a_address(param){
    var address=param.substring(0,param.indexOf(","));
    var coordinate=param.substring(param.indexOf(",")+1);
    $("#ddaddress").val(address);
    $("#coordinate").val(coordinate);
}

/**************************定位获取地址结束************************/

//时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;

    var n = new Date(currentdate).getTime();
    //console.log(n);
    return n;
}

//提示框样式
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