var t = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端

function resource() {
    document.location.href = basePath + "/mobile/office/mobileoffice_toMobileLogin.jspa?sys=sys&companyId=" + companyId + "&flag=yd";
}

//查询资讯详情
function information(obj) {
    var informationPpid = $(obj).find("#ppid").val();
    document.location.href = basePath + "ea/industry/ea_informationDetails.jspa?ppId=" + informationPpid + "&ccompanyId=" + ccompanyId + "&type=web" + "&miniSystemJudge=02";
}

function activity(type) {
    var url = basePath + "/st/enroll/sajax_ea_activityList.jspa?type=" + type;
    $.ajax({
        url: encodeURI(url),
        type: "POST",
        dataType: "json",
        async: false,
        success: function (data) {
            var member = eval("(" + data + ")");
            var activityList = member.activityList;
            if (activityList != null) {
                var companyList = [];
                document.location.href = basePath + "/ea/industry/ea_getAllCompanyList.jspa?flag=activity&activityType=" + type;
            } else {
                alert("暂无该活动")
            }
        }
    })
}

function consultantSubmit(obj) {
    var name = $(obj).parents(".ipt_con").find(".consultantName").val();
    var phone = $(obj).parents(".ipt_con").find(".consultantPhone").val();
    var regular1 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
    var regular2 = /(^13\d{9}$)|(^14)[5,7]\d{8}$|(^15[0,1,2,3,5,6,7,8,9]\d{8}$)|(^17)[6,7,8]\d{8}$|(^18\d{9}$)/g;
    if (!regular1.test(name)) {
        alert("名称格式不正确");
        return;
    }
    if (!regular2.test(phone)) {
        alert("手机格式不正确");
        return;
    }

    var url = basePath + "ea/industry/sajax_ea_ajaxAddConsultant.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "post",
        data: {
            "consultingRegistration.companyId": companyId,
            "consultingRegistration.consultantName": name,
            "consultingRegistration.consultantPhone": phone
        },
        dataType: "json",
        async: true,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var stuts = jsonresult.stuts;
            if (stuts == "0") {
                alert("提交成功")
            } else if (stuts == "1") {
                alert("提交失败")
            }
        }
    })
}

function sendCode(thisBtn) {
    if (_name.value == "") {
        alert("姓名不能为空");
        return false;
    }
    if (tel.value == "") {
        alert("手机号不能为空");
        return false;
    }
    if (!ver_phone()) {
        return false;
    }
    //发送短信
    $.ajax({
        cache: true,
        type: "POST",
        url: basePath + "/ea/android/sajax_ea_getduanxin.jspa?pahe=" + tel.value,
        async: false,
        dataType: "json",
        success: function (data) {
            var member = data;
            i = member.returna;
        }
    });
    btn = thisBtn;
    btn.disabled = true; //将按钮置为不可点击
    //btn.innerHTML = nums + '秒重新获取';
    btn.value = nums + '秒重新获取';
    clock = setInterval(doLoop, 1000); //一秒执行一次
    btn.className = "send_btn disable_btn";

}

//手机验证格式
tel.addEventListener("input", function () {
    var Sreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (!Sreg.test(tel.value)) {
        //console.log("格式不正确，不提交");
        if (tel.value.length == 11) {
            alert("请输入正确格式");
            tel.value = "";
            tel.focus();
        }
    } else {
        isRegister();
    }
});

//验证码失去焦点验证
//valnum.addEventListener("blur", verCode);

//处理浏览器输入法遮挡
var screenH = window.innerHeight;
window.onresize = function () {
    var t = window.innerHeight;
    console.log(t);
    console.log(screenH);
    var inp = $("input:focus")[0];
    if (t < screenH) {
        inp.scrollIntoView(false);
    }
};
//点击注册
submit_btn.addEventListener("click", function () {
    if (_name.value == "") {
        alert("姓名不能为空");
        return false;
        name.focus();
    }
    if (tel.value == "") {
        alert("手机号不能为空");
        return false;
        tel.focus();
    }
    if (!ver_phone()) {
        return false;
        tel.focus();
    }
    if (!verCode()) {
        return false;
        valnum.focus();
    }
    if (!ver_password()) {
        return false;

    }
    $("#myform").attr("action", basePath + "/ea/wfjlogin/ea_seves.jspa");
    $("#myform").submit();
})

//手机号验证
function ver_phone() {
    var Sreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (tel.value == "") {
        alert("手机号不能为空");
        return false;
    } else if (!Sreg.test(tel.value)) {
        alert("请输入正确格式电话号！");
        return false;
    } else {
        return true;
    }
}


// 判断手机号是否注册
function isRegister() {
    if (tel.value != '' && ver_phone()) {
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "/ea/android/sajax_ea_isacounnt.jspa?pahe=" + tel.value,
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member.result == 0) {
                    d = 1;
                    console.log("未注册，可以使用");
                } else {
                    alert("已被注册,请更换手机号码！");
                    tel.value = "";
                    tel.focus();
                    d = 2;

                }
            }
        });
    } else {
        tel.value = "";
    }
}

function doLoop() {
    nums--;
    if (nums > 0) {
        btn.value = nums + '秒重新获取';
    } else {
        clearInterval(clock); //清除js定时器
        btn.disabled = false;
        btn.value = '获取验证码';
        nums = 60; //重置时间
        btn.className = "send_btn";
    }
}

//验证码验证
function verCode() {
    if (valnum.value == "") {
        alert("请填写验证码");
        return false;
    } else if (valnum.value != i) {
        alert("验证码不正确");
        return false;
    } else {
        return true;
    }
}

//密码
function ver_password() {

    var flag = false;
    var message = "";

    if (password.value == '') {
        alert("请输入密码！");
        return false;
    } else if (password.value.length < 6) {
        alert("密码长度不安全");
        return false;
    } else if (confirmPassword.value == '') {
        alert("请输入确认密码");
    } else if (confirmPassword.value != password.value) {
        alert("二次密码输入不一致，请重新输入！");
        confirmPassword.value = "";
        confirmPassword.focus();
        return false;
    } else {
        return true;
    }
}

var numc = 0

function stmore() {
    numc++;
    $(".stmorelist img").rotate({
        angle: 1,
        animateTo: (180 * numc) % 360,
        duration: 800
    });
    $(".sthide").slideToggle();
}

//安卓，苹果返回
function retAndroid() {
    try {
        Android.callAndroidjianli();
    } catch (err) {
        $("#ret").removeAttr("onclick");
        $("#ret").attr("onclick", "javascript: window.history.go(-1);return false;");
        $("#ret").trigger('click');
        /*if(isflag == "gjpt"){
         $("#ret").attr("href",basePath+"/page/ea/BuildPlatform/wfjIndustryPlatfrom/resources.jsp");
         }else if(typeback =="typeback"){
         $("#ret").attr("href",basePath+"/ea/wfjshop/ea_getWFJshops.jspa");
         }else if(typeback =="st"){
         $("#ret").attr("href",basePath+"ea/industry/ea_getCompanyHome.jspa");
         }else{
         if(typelei=="ZHANGZHAOLIANG"&&typelei!=null){
         $("#ret").attr("href",basePath+"ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid="+sccid);
         }else{
         $("#ret").attr("href",basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType="+industryType+"&etype="+etype);
         }
         }*/
    }
}

function retIOS() {
    try {
        calProduct('');
    } catch (err) {
        $("#ret").removeAttr("onclick");

        $("#ret").attr("onclick", "javascript: window.history.go(-1);return false;");
        $("#ret").trigger('click');
        /*if(isflag == "gjpt"){
         $("#ret").attr("href",basePath+"/page/ea/BuildPlatform/wfjIndustryPlatfrom/resources.jsp");
         }else if(typeback =="typeback"){
         $("#ret").attr("href",basePath+"/ea/wfjshop/ea_getWFJshops.jspa");
         }else if(typeback =="st"){
         $("#ret").attr("href",basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170713test&industryType="+industryType+"&etype="+etype);
         }else{
         if(typelei=="ZHANGZHAOLIANG"&&typelei!=null){
         $("#ret").attr("href",basePath+"ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid="+sccid);
         }else{
         $("#ret").attr("href",basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType="+industryType+"&etype="+etype);
         }
         }*/
    }
}

$(document).ready(function (e) {
    //app返回
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


    try {
        if (isAndroid == true) {
            var obj = document.getElementById("ret");
            obj.setAttribute("href", "#");
            obj.setAttribute("onclick", "retAndroid()");
        } else if (isiOS == true) {
            var obj = document.getElementById("ret");
            obj.setAttribute("href", "#");
            obj.setAttribute("onclick", "retIOS(    )");
        }
    } catch (e) {
        console.log("报错啦");
    }
    findCompanyID();

    function findCompanyID() {
        var url = basePath + "/st/enroll/sajax_ea_findCompanyID.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            data: {"ccompanyID": ccompanyId},
            dataType: "json",
            async: false,
            success: function (data) {
                var member = eval("(" + data + ")");
                companyId = member.company[2];
            }
        })

        $("#qrcodeCanvas").qrcode({
            render: "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
            text: window.location.href,
            width: "150",               //二维码的宽度
            height: "150",              //二维码的高度
            background: "#ffffff",       //二维码的后景色
            foreground: "#000000",        //二维码的前景色
            //src:$("#logo").attr("src")    //二维码中间的图片
        })

    }

    //新闻的展示
    var url = basePath + "/st/enroll/sajax_ea_AjaxNewsList.jspa?";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var page = member.pageForm;
            if (page != null && page.lenght != 0) {
                var sp = page.list;
                var newstr = "";
                var i = 0;
                var len = 3;
                for (i; i < sp.length; i++) {
                    var news = sp[i];
                    if (news[1].hours < 10) {
                        news[1].hours = "0" + news[1].hours;
                    }
                    if (news[1].minutes < 10) {
                        news[1].minutes = "0" + news[1].minutes;
                    }
                    var time = news[1].hours + ":" + news[1].minutes;
                    if (news[1].month < 9) {
                        if (news[1].date < 10) {
                            var date = (news[1].year + 1900) + "-0" + (news[1].month + 1) + "-0" + news[1].date;
                        } else {
                            var date = (news[1].year + 1900) + "-0" + (news[1].month + 1) + "-" + news[1].date;
                        }
                    } else {
                        var date = (news[1].year + 1900) + "-" + (news[1].month + 1) + "-" + news[1].date;
                    }
                    var name = news[9] == null ? "" : news[9];
                    if (news[2] != null) {
                        var t = news[2].split(".")[0] + "small." + news[2].split(".")[1];
                        newstr += "<li class='clearfix' onclick='information(this)'>";
                        newstr += "<input type='hidden' id='ppid' value='" + news[5] + "' />";
                        newstr += "<input type='hidden' id='types' value='" + news[6] + "' />";
                        newstr += "<input type='hidden' id='companyId' value='" + news[7] + "' />";
                        newstr += "<input type='hidden' id='ccompanyId' value='" + news[8] + "' />";
                        newstr += "<img src='" + basePath + t + "' class='new_img' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>";
                        newstr += "   <div>";
                        newstr += "       <h3><span class='txt'>" + news[0] + "</span></h3>";
                        newstr += "       <p><span class='txt'>" + name + "</span><span class='new_time' data-date='" + date + "' data-time='" + time + "'></span></p>";
                        newstr += "   </div> </li>";
                    }
                }
                $(".news menu").append(newstr);
            } else {
                $(".news menu").append("<p>暂无新闻！</p>");
            }
            //当前时间
            var curDate = getNowFormatDate();
            $(".new_time").each(function () {
                var d = $(this).attr("data-date");
                var t = $(this).attr("data-time");
                if (curDate == d) {
                    $(this).text(t);
                } else {
                    $(this).text(d);
                }
            });

            //获取当前时间（格式：2017-01-23）
            function getNowFormatDate() {
                var day = new Date();
                var Year = 0;
                var Month = 0;
                var Day = 0;
                var CurrentDate = "";
                //初始化时间
                Year = day.getFullYear();
                Month = day.getMonth() + 1;
                Day = day.getDate();
                //Hour = day.getHours();
                // Minute = day.getMinutes();
                // Second = day.getSeconds();
                CurrentDate += Year + "-";
                if (Month >= 10) {
                    CurrentDate += Month + "-";
                } else {
                    CurrentDate += "0" + Month + "-";
                }
                if (Day >= 10) {
                    CurrentDate += Day;
                } else {
                    CurrentDate += "0" + Day;
                }
                return CurrentDate;
            }
        },
        error: function (data) {

        }
    });

    //活动
    //var reurl = basePath + "ea/lottery/sajax_ea_allActivityList.jspa?flag=0";
    // $.ajax({
    //     url : encodeURI(reurl),
    //     type : "get",
    //     async : false,
    //     dataType : "json",
    //     success : function (data) {
    //         var member = eval("("+data+")");
    //         var pageForm = member.pageForm;
    //         var str = new Array();
    //         if (pageForm != null && pageForm.recordCount> 0)
    //         {
    //             pagenumber = pageForm.pageNumber;
    //             pagecount = pageForm.pageCount;
    //             var list = pageForm.list;
    //             for (var i = 0;i < list.length;i++)
    //             {
    //                 var entity = list[i];
    //                 $("#relist").append('<a href='+basePath+'/ea/industry/ea_getCompanyHome.jspa?typeback=st&ccompanyId='+entity[0]+' ><li class="activity" style="float: none; font-size: 0.7rem;text-decoration: underline;line-height: 1.5rem;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;height: 1.5rem;">'+entity[3]+'</li>');
    //             }
    //         }else {
    //             $("#relist").append('<li></li><li style="height:30px"><span style="font-size: 14px">暂无抽奖活动</span></li><li></li>')
    //         }
    //     }
    // });

    // var signurl = basePath + "ea/lottery/sajax_ea_allActivityList.jspa?flag=1";
    // $.ajax({
    //     url : encodeURI(signurl),
    //     type : "get",
    //     async : false,
    //     dataType : "json",
    //     success : function (data) {
    //         var member = eval("("+data+")");
    //         var pageForm = member.pageForm;
    //         var str = new Array();
    //         if (pageForm != null && pageForm.recordCount> 0)
    //         {
    //             pagenumber = pageForm.pageNumber;
    //             pagecount = pageForm.pageCount;
    //             var list = pageForm.list;
    //             $(".last").removeClass("last");
    //             for (var i = 0;i < 3;i++)
    //             {
    //                 var entity = list[i];
    //                 $("#signlist").append('<a href='+basePath+'/ea/industry/ea_getCompanyHome.jspa?typeback=st&ccompanyId='+entity[0]+' ><li class="activity" style="float: none; font-size: 0.7rem;text-decoration: underline;line-height: 1.5rem;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;height: 1.5rem;">'+entity[3]+'</li>');
    //             }
    //         }else {
    //             $("#signlist").append('<li></li><li style="height:30px"><span style="font-size: 14px">暂无积分活动</span></li><li></li>')
    //         }
    //     }
    // });

    var producturl = basePath + "/st/enroll/sajax_ea_driverProducts.jspa";
    $.ajax({
        url: encodeURI(producturl),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var ProductList = member.ProductList;
            var str = [];
            if (ProductList != null && ProductList.length > 0) {
                for (var i = 0; i < 6; i++) {
                    str.push("<li>");
                    if (ProductList[i][6] == "学员报名") {
                        var parms = [];
                        parms.push("&goodsName=" + ProductList[i][5]);
                        parms.push("&ppid=" + ProductList[i][0]);
                        parms.push("&companyID=" + ProductList[i][2]);
                        parms.push("&photo=" + ProductList[i][4]);
                        parms.push("&remark=" + ProductList[i][10]);
                        parms.push("&ccompanyId=" + ProductList[i][3]);
                        parms.push("&price=" + ProductList[i][14]);

                        parms.push("&goodsID=" + ProductList[i][1]);
                        parms.push("&licenceType=" + ProductList[i][6])
                        parms.push("&brand=" + ProductList[i][7]);
                        parms.push("&categoryName=" + ProductList[i][12]);
                        parms.push("&categoryId=" + ProductList[i][13]);
                        parms.push("&priceType=" + 0);
                        parms.push("&activityid=''");
                        parms.push("&cost=" + ProductList[i][14]);
                        var url = basePath + "/st/enroll/ea_getEnroll.jspa?" + parms.join("");
                        str.push("<a href='" + url + "'>");
                    } else {
                        str.push("<a href='" + basePath + "ea/wfjshop/ea_doodsDetail.jspa?ppid=" + ProductList[i][0] + "&goodsid=" + ProductList[i][1] + "&companyId=" + ProductList[i][2] + "&ccompanyId=" + ProductList[i][3] + "'>");

                    }
                    str.push("<img src='" + basePath + ProductList[i][4] + "' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                    str.push("<p>" + ProductList[i][5] + "</p></a></li>");
                }
                $(".driverProducts menu").append(str.join(""))
            }
        }
    });

    var sturl = basePath + "/st/enroll/sajax_ea_stList.jspa?ccompanyID=" + ccompanyId;
    $.ajax({
        url: encodeURI(sturl),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var stList = member.stList;
            var str = [];
            if (stList != null && stList.length > 0) {
                for (var i = 0; i < stList.length; i++) {
                    if (i > 5) {
                        $(".dropdown").show();
                        str.push("<li class='sthide'><a href='' class='clearfix'>");
                    } else {
                        $(".dropdown").hide();
                        str.push("<li><a href='" + basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + stList[i][0] + "&industryType=汽车驾校' class='clearfix'>");
                    }
                    str.push("<img src='" + basePath + stList[i][1] + "' onerror='this.src=\"" + basePath + "/images/WFJClient/PersonalJoining/logo@2x.png\"'/>");
                    str.push("<div><h3>" + stList[i][2] + "</h3>");
                    str.push("<h4 class='txt'>" + stList[i][3] + "</h4></div></a></li>");
                }
                $(".stList menu").append(str.join(""))
            }
        }
    });
    $(".sthide").hide();

    //优秀员工
    var employeesurl = basePath + "/st/enroll/sajax_ea_AjaxTeam.jspa";
    $.ajax({
        url: encodeURI(employeesurl),
        type: "POST",
        async: false,
        dateType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var personnelList = member.personnelList;
            var str = [];
            if (personnelList != null) {
                for (var i = 0; i < personnelList.length; i++) {
                    if (i < 5) {
                        str.push("<a href='" + basePath + "st/enroll/ea_getTeamDetails.jspa?staffId=" + personnelList[i][0] + "&postName=" + personnelList[i][1] + "'>");
                        str.push("<li class='clearfix' id='" + personnelList[i][0] + "'>")
                        str.push("<img src=" + basePath + (personnelList[i][2] == null ? "images/ea/driving/elkc/head.png" : personnelList[i][2]) + " alt='' />");
                        str.push("<div><h3><span  class='txt'>" + personnelList[i][1] + "</span></h3>");
                        str.push("<p>" + personnelList[i][4] + "</p></div></li></a>");
                    }
                }
                $(".employees menu").append(str.join(""))
            }
        }
    })
    //共享教练车
    var carurl = basePath + "/st/enroll/sajax_ea_AjaxEqment.jspa";
    $.ajax({
        url: encodeURI(carurl),
        dataType: "json",
        type: "GET",
        async: false,
        success: function (data) {
            var member = eval("(" + data + ")");
            var carList = member.carList;
            var str = [];
            if (carList != null) {
                for (var i = 0; i < carList.length; i++) {
                    if (i < 8) {
                        str.push('<li><a href="' + basePath + 'st/enroll/ea_getEqptDetails.jspa?&carNum=' + carList[i][0] + '&cartype=' + carList[i][1] + '&name=' + carList[i][2] + '&reference=' + carList[i][3] + '&address=' + carList[i][4] + '&photo=' + carList[i][5] + '"> ');
                        str.push('<img src=' + basePath + (carList[i][5] == null ? "st/images/ico-car1.png" : carList[i][5]) + '>');
                        str.push('<p>' + (carList[i][1] == null ? "" : carList[i][1]) + '</p>');
                        str.push('</a></li>');
                    }
                }
                $(".car menu").append(str.join(""));
            }
        }
    })

    //投诉
    var url = basePath + "/st/enroll/sajax_ea_complaints.jspa";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var complaintList = member.complaintList;
            var str = [];
            if (complaintList != null) {
                for (var i = 0; i < complaintList.length; i++) {
                    str.push('<li class="clearfix" onclick="detCom(\'' + complaintList[i][2] + '\',\'' + complaintList[i][3] + '\')">');
                    str.push('<img src=' + basePath + (complaintList[i][0] == null ? "images/ea/driving/elkc/head.png" : complaintList[i][2]) + ' alt=""/>');
                    str.push('<div><h3><a  class="txt">' + complaintList[i][1] + '</a></h3>');
                    str.push('<p>' + complaintList[i][4] + '</p></div></li>');
                }
                $(".complaints menu").append(str.join(""))
            } else {
                $(".complaints").hide();
            }
        }
    })

    //场地
    var url = basePath + "/st/enroll/sajax_ea_complaints.jspa";
    $.ajax({
        url: url,
        async: false,
        type: "get",
        dataType: "json",
        success: function (data) {

        }
    })

})


//考场约车
function preCar() {

    if (sccID == null || sccID == "") {

        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

    } else {
        document.location.href = basePath + "ea/mappointment/ea_theTestTime.jspa?sccId=" + sccID + "&sc=web";
    }

}

//拼货拉农超
function phl() {
    if (sccID == "") {
        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

    } else if (companyID == "" || companyID == "null") {
        document.location.href = basePath + "ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany";

    } else {
        document.location.href = basePath + "ea/phl/ea_getPhlIndex.jspa";
    }


}

// 考勤签到
function signface() {
    var posNum = null

    if (isAndroid) {
        posNum = Android.forAndroidDeviceId();
    } else {
        alert("签到考勤功能仅安卓设备支持!");
        return;
    }

    try {
        if (posNum !== "" && posNum != null) {
            posNum = isExistPosNum(posNum);
        }
    } catch (error) {
        if (($(window).width() == 1080 && $(window).height() == 1546) || ($(window).width() == 534 && $(window).height() == 636)) {
            posNum = 123;
        }
    }
    if (posNum !== null && posNum !== '') {
        window.location.href = basePath + '/ea/bonuspoints/ea_getCurrCompany.jspa?posNum=' + posNum;
    } else {
        if (isAndroid) {
            const deviceId = Android.forAndroidDeviceId();
            alert(`当前设备[${deviceId}]未在系统中注册，请联系管理员处理。`);
        }
    }
}


//判断是否是终端机器
function isExistPosNum(posNum) {
    var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        async: false,
        data: {
            posNum: posNum
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            if (result != "0") {
                posNum = "";
            }
        },
        error: function (data) {
            // alert("验证失败");
        }
    });
    return posNum;
}