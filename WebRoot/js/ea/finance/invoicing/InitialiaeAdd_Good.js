var pamar;
var comid;
var staffid;
$(function () {
    //启用点击
    $(document).on("click", ".li-zx", function () {
        $(".div-zx").show();
        $("body").addClass("hid");

    });
    //启用选择
    $(".div-zx ul li").click(function () {
        $(".div-zx ul li").find("img").attr("src", "/images/ea/finance/invoicing/xuan_03.png");
        $(".div-zx ul li").find("p").removeClass("active");
        $(this).find("img").attr("src", "/images/ea/finance/invoicing/xuan_06.png");
        $(this).find("p").addClass("active");
    });
    //启用提交
    $(".div-tj").click(function () {
        var ptext = $(".div-zx .active").parents("li").children("p").text();
        $(".li-zx").find("span").text(ptext);
        if (ptext == "启用") {
            $(".li-zx").find("input").val("01");
        } else {
            $(".li-zx").find("input").val("00");
        }

        $(".div-zx").hide();
        $("body").removeClass("hid");
    });

    $(".kf").click(function () {
        $("#ckiframe").attr("src","/ea/initialize/ea_getListDepotmanageByPID.jspa?depotID=001&sort=2&compayid="+comid);
        $(".div-kc").show();
    });

    $("#span-close").click(function () {
        $(".div-kc").hide();
    });

    localforage.getItem('formjum').then(function (value) {
        //当离线仓库中的值被载入时，此处代码运行
        console.log(value);
        if (value != null && value != "") {
            $(value).each(function () {
                $("#" + this.name).text(this.value);
                $("#" + this.name).val(this.value);
                if(this.name=="companyid"){
                    comid=this.value;
                }else if(this.name=="staffid"){
                    staffid=this.value;
                }
            });
            localforage.getItem('formgoods').then(function (value) {
                //当离线仓库中的值被载入时，此处代码运行
                console.log(value);
                if (value != null && value != "") {
                    $(value).each(function () {
                        $("#" + this.name).text(this.value);
                        $("#" + this.name).val(this.value);
                    });
                    pamar = value;
                    //localforage.removeItem('formgoods');
                }
            }).catch(function (err) {
                // 当出错时，此处代码运行
                console.log(err);
            });
        }
    }).catch(function (err) {
        // 当出错时，此处代码运行
        console.log(err);
    });

    $(".deopt").click(function () {
        var id = $(this).attr("id");
    });

    $("#okbut").click(function () {
        var reg = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);//称重商品重量
        if($("#cskc").val()==""||$("#cskc").val()==null||$("#cskc").val()==0){
            alert("库存不能为空");
            return false;
        }else if (!reg.test($("#cskc").val())) {
            alert("请确保数据有效！");
            $("#cskc").val(0);
            return false;
        }else{
            var serializeObj = {};
            $(pamar).each(function () {
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                    }
                } else {
                    serializeObj[this.name] = this.value;
                }
            });

            serializeObj.cskc=$("#cskc").val();
            serializeObj.fbname=$(".li-zx").find("span").text();
            serializeObj.fbtype=$(".li-zx").find("input").val();
            serializeObj.kfid=$(".kf").find("input").val();
            serializeObj.kfname=$(".kf").find("span").text();

            localforage.getItem('serializeJson').then(function (value) {
                //当离线仓库中的值被载入时，此处代码运行
                console.log(value);
                console.log(serializeObj);
                var serializeJson=[];
                if (value != null && value != "") {
                    serializeJson=value;
                }
                //serializeJson.push({"id":serializeObj.ppid,"goods":serializeObj});
                serializeJson.push(serializeObj);
                localforage.setItem('serializeJson', serializeJson).then(function (value) {
                    console.log(value);
                    //window.history.go(-1);
                    window.location.href = "/page/ea/main/finance/invoicing/InitialiaeAdd.jsp?compayid=" + comid+"&staffid="+staffid;
                }).catch(function (err) {
                    // 当出错时，此处代码运行
                    console.log(err);
                });
            }).catch(function (err) {
                // 当出错时，此处代码运行
                console.log(err);
            });
        }
    });
});

function toBack() {
    /*try {
        if (isAndroid == true) {
            console.log("安卓");
            Android.callAndroidjianli();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
            var url = "func=" + 'callIOSjianli';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    } catch (error) {
        window.history.go(-1);
        return false;
    }*/
    window.location.href = "/page/ea/main/finance/invoicing/InitialiaeAdd.jsp?compayid=" + comid+"&staffid="+staffid;
}