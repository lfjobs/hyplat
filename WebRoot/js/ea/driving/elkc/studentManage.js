/**
 * Created by Administrator on 2017/8/25.
 */
$(document).ready(function() {
    if($("input[name='tbJpStudentInfo.sex']").val()=="1"){
        $(".sex").find("span").each(function () {
            if($(this).text()=="男"){
                $(this).prev().addClass("active").parents(".sex h5").siblings().find("i").removeClass("active");
            }
        });
    }else if($("input[name='tbJpStudentInfo.sex']").val()=="2"){
        $(".sex").find("span").each(function () {
            if($(this).text()=="女"){
                $(this).prev().addClass("active").parents(".sex h5").siblings().find("i").removeClass("active");
            }
        });
    }

    if($("input[name='tbJpTeacher.sex']").val()=="1"){
        $("#sex").find("span").each(function () {
            if($(this).text()=="男"){
                $(this).prev().addClass("active").parents("#sex h5").siblings().find("i").removeClass("active");
            }
        });
    }else if($("input[name='tbJpTeacher.sex']").val()=="2"){
        $("#sex").find("span").each(function () {
            if($(this).text()=="女"){
                $(this).prev().addClass("active").parents("#sex h5").siblings().find("i").removeClass("active");
            }
        });
    }

    if($("input[name='tbJpStudentInfo.isLocal']").val()=="1"){
        $(".isLocal").find("span").each(function () {
            if($(this).text()=="是"){
                $(this).prev().addClass("active").parents(".isLocal h5").siblings().find("i").removeClass("active");
                $(".isHide").hide();
            }
        })
    }else if($("input[name='tbJpStudentInfo.isLocal']").val()=="0"){
        $(".isLocal").find("span").each(function () {
            if($(this).text()=="否"){
                $(this).prev().addClass("active").parents(".isLocal h5").siblings().find("i").removeClass("active");
                $(".isHide").show();
            }
        })
    }

    switch ($("input[name='tbJpStudentInfo.cardType']").val()) {
        case '1' :
            $(".cardType").find("span").each(function () {
                if($(this).text()=="身份证"){
                    $(this).prev().addClass("active").parents(".cardType h5").siblings().find("i").removeClass("active");
                }
            });
            break;
        case '2' :
            $(".cardType").find("span").each(function () {
                if($(this).text()=="护照"){
                    $(this).prev().addClass("active").parents(".cardType h5").siblings().find("i").removeClass("active");
                }
            });
            break;
        case '3' :
            $(".cardType").find("span").each(function () {
                if($(this).text()=="军官证"){
                    $(this).prev().addClass("active").parents(".cardType h5").siblings().find("i").removeClass("active");
                }
            });
            break;
        case '4' :
            $(".cardType").find("span").each(function () {
                if($(this).text()=="其他"){
                    $(this).prev().addClass("active").parents(".cardType h5").siblings().find("i").removeClass("active");
                }
            });
            break;

    }

    switch ($("input[name='tbJpTeacher.occupationlevel']").val()) {
        case '1' :
            $("#occupationlevel").find("span").each(function () {
                if($(this).text()=="一级"){
                    $(this).prev().addClass("active").parents("#occupationlevel h5").siblings().find("i").removeClass("active");
                }
            });
            break;
        case '2' :
            $("#occupationlevel").find("span").each(function () {
                if($(this).text()=="二级"){
                    $(this).prev().addClass("active").parents("#occupationlevel h5").siblings().find("i").removeClass("active");
                }
            });
            break;
        case '3' :
            $("#occupationlevel").find("span").each(function () {
                if($(this).text()=="三级"){
                    $(this).prev().addClass("active").parents("#occupationlevel h5").siblings().find("i").removeClass("active");
                }
            });
            break;
        case '4' :
            $("#occupationlevel").find("span").each(function () {
                if($(this).text()=="四级"){
                    $(this).prev().addClass("active").parents("#occupationlevel h5").siblings().find("i").removeClass("active");
                }
            });
            break;
    }
    //格式化初始日期
    if($("input[name='tbJpStudentInfo.brith']").val()){
        var time1 =  $("input[name='tbJpStudentInfo.brith']").val().substring(0,10).split("-");
        $("input[name='tbJpStudentInfo.brith']").val(time1[0]+"/"+time1[1]+"/"+time1[2]);
    }else if($("input[name='tbJpStudentInfo.applyDate']").val()){
        var time2 =  $("input[name='tbJpStudentInfo.applyDate']").val().substring(0,10).split("-");
        $("input[name='tbJpStudentInfo.applyDate']").val(time2[0]+"/"+time2[1]+"/"+time2[2]);
    }else if($("input[name='tbJpTeacher.fstdrilicdate']").val()){
        var time3 =  $("input[name='tbJpTeacher.fstdrilicdate']").val().substring(0,10).split("-");
        $("input[name='tbJpTeacher.fstdrilicdate']").val(time3[0]+"/"+time3[1]+"/"+time3[2]);
    }
    //保存
    $("#saveStudent").click(function () {
        var logo = $("#image_box").attr("src")
        if (logo.indexOf("base64")!= -1) {
             $("input[name='tbJpStudentInfo.photo']").val(logo)
        }
        if (!(/^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\d{8}$/.test($("input[name='tbJpStudentInfo.phone']").val()))) {
            alert("手机号码格式不正确,请输入正确的手机号码格式");
            $("input[name='tbJpStudentInfo.phone']").val("");
            $("input[name='tbJpStudentInfo.phone']").focus();
            return;
        }
        var reg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
        if (reg.test($("input[name='tbJpStudentInfo.cardNum']").val()) === false) {
            alert("身份证格式不正确,请输入正确的身份证格式格式");
            $("input[name='tbJpStudentInfo.cardNum']").val("");
            $("input[name='tbJpStudentInfo.cardNum']").focus();
            return;
        }

        var brith=$("input[name='tbJpStudentInfo.brith']").val();    //字符串日期格式
        var url = basePath+"/driving/elkc/ea_saveStudent.jspa?staffId="+staffId+"&brith="+brith;
        $.ajax({
            url : encodeURI(url),
            type : "post",
            processData:false,
            data : $("#studentForm").serialize(),
            success : function (data) {
                $(".overlay").addClass("active");
            }
        })



        // $("#studentForm").attr("action", basePath + "/driving/elkc/ea_saveStudent.jspa?staffId="+staffId);
        // $("#studentForm").submit();
    })

    $("#svaeApply").click(function () {
        var brith=$("input[name='tbJpStudentInfo.applyDate']").val()
        var url = basePath+"/driving/elkc/ea_saveStudentApplyInfo.jspa?staffId="+staffId+"&applyDate="+brith;
        $.ajax({
            url : encodeURI(url),
            type : "post",
            processData:false,
            data : $("#applyForm").serialize(),
            success : function (data) {
                $(".overlay").addClass("active");
            }
        })
    })

    $("#saveCoach").click(function () {
        var logo = $("#image_box").attr("src")
        if (logo.indexOf("base64")!= -1) {
            $("input[name='tbJpTeacher.photo']").val(logo)
        }
        if (!(/^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\d{8}$/.test($("input[name='tbJpTeacher.mobile']").val()))){
            alert("手机号码格式不正确,请输入正确的手机号码格式");
            $("input[name='tbJpTeacher.mobile']").val("");
            $("input[name='tbJpTeacher.mobile']").focus();
            return;
        }
        var reg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
        if (reg.test($("input[name='tbJpTeacher.idcard']").val()) === false) {
            alert("身份证格式不正确,请输入正确的身份证格式格式");
            $("input[name='tbJpTeacher.idcard']").val("");
            $("input[name='tbJpTeacher.idcard']").focus();
            return;
        }

        var brith=$("input[name='tbJpTeacher.fstdrilicdate']").val()
        var url = basePath+"/driving/elkc/sajax_ea_saveCoachInfo.jspa?staffId="+staffId+"&fstdrilicdate="+brith;
        $.ajax({
            url : encodeURI(url),
            type : "post",
            processData:false,
            data : $("#coachForm").serialize(),
            success : function (data) {
                $(".overlay").addClass("active");
            }
        })
    })
})