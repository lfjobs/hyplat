$(document).ready(function() {
    $(".close").click(function () {
        pageNumByBill=1;
        $(".register").val("");
        $(this).parent().parent().parent().hide();
    });

    $(".up").click(function () {
        pageNumByBill--;
        if(pageNumByBill<0){
            pageNumByBill = 1;
        }
        billdal()
    });
    $(".next").click(function () {
        pageNumByBill++;
        billdal()
    });

    $(".jqmWindow").jqm({
        modal : true,
        overlay : 20
        //
    }).jqmAddClose('.close');//
    // .jqDrag('.drag');//
    var html =  $(".query").html();
    $(".query").remove();
    $('.JQueryflexme').flexigrid({
        height : 350,
        width : 'auto',
        minwidth : 30,
        title : html,
        minheight : 80,
        buttons : [{
            name : '开通购物卡',
            bclass : 'add',
            onpress : action
            // 当点击调用方法
        },
         //    {
         // name : '开通购物卡',
         // bclass : 'add',
         // onpress : action
         // // 当点击调用方法
         // },
            {
            name : '充值',
            bclass : 'add',
            onpress : action
            // 当点击调用方法
        },{
            name : '补卡',
            bclass : 'add',
            onpress : action
            // 当点击调用方法mysearch
        },{
            name : '挂失',
            bclass : 'edit',
            onpress : action
            // 当点击调用方法
        },{
                name : '取消挂失',
                bclass : 'edit',
                onpress : action
                // 当点击调用方法
            },{
            name : '账单',
            bclass : 'mysearch',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '设置每页显示条数',
            bclass : 'mysearch',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        }]
    });


    function action(com, grid) {
        switch (com) {
            case '开通购物卡' :
                //form数据初始化
                $(".register").val("");
                $("#registered").show();
                break;
            // case '开通购物卡' :
            //     $(".input_").val("");
            //     $("#reissueCard h2 span").text("开通购物卡");
            //     $("#reissueCard span:last").attr("style","letter-spacing: 12px").text("购物卡号");
            //     $("#reissueCard .open").removeAttr("readonly").removeAttr("style").attr("placeholder","请输入")
            //     $("#reissueCard").show();
            //     break;
            case '充值' :
                var checkTr = $("input[name='a']:checked").parents("tr");
                var staffID = checkTr.attr("id");
                var sccId = checkTr.find("input[name='a']").val();
                var user = checkTr.find(".reference").text();
                var integral = checkTr.find(".bonuspointscore").text();
                if(staffID==undefined){
                    alert("请选择！")
                    return;
                }
                order(user)
                window.open(basePath+"ea/giftcard/ea_topup.jspa?integral="+integral+"&jum="+jum+"&staffID="+staffid+"&sccid="+sccId);
                break;
            case '补卡' :
                var checkTr = $("input[name='a']:checked").parents("tr");
                var staffID = checkTr.attr("id");
                $("#openName").val(checkTr.find(".mkname").text());
                $("#openCall").val(checkTr.find(".reference").text());
                $("#openIdCard").val(checkTr.find(".staffidentitycard").text());
                if(staffID==undefined){
                    alert("请选择！")
                    return;
                }
                var state =  $("input[name='a']:checked").parents("tr").find(".state").text();
                if(state=="正常"){
                    alert("请选择挂失的购物卡！");
                    return;
                }
                $("input[name='a']:checked")
                $("#reissueCard h2 span").text("补卡");
                $("#reissueCard span:last").attr("style","letter-spacing: 0px").text("新的购物卡号");
                $("#reissueCard .open").attr("style","border-bottom:none;").removeAttr("placeholder").attr("readonly","readonly");
                $("#reissueCard").show();
                break;
            case '挂失' :
                var staffID = $("input[name='a']:checked").parents("tr").attr("id")
                if(staffID==undefined){
                    alert("请选择！")
                    return;
                }
                var state =  $("input[name='a']:checked").parents("tr").find(".state").text();
                if(state=="挂失"){
                    alert("已经挂失的购物卡不能再次挂失！");
                    return;
                }
                reportLoss("0",staffID);
                break;
            case '取消挂失' :
                var staffID = $("input[name='a']:checked").parents("tr").attr("id")
                if(staffID==undefined){
                    alert("请选择！")
                    return;
                }
                var state =  $("input[name='a']:checked").parents("tr").find(".state").text();
                if(state=="正常"){
                    alert("请选择挂失的购物卡！");
                    return;
                }
                reportLoss("1",staffID);
                break;
            case '账单' :
                var checkTr = $("input[name='a']:checked").parents("tr");
                var staffID = checkTr.attr("id");
                var integral = checkTr.find(".bonuspointscore").text();
                if(staffID==undefined){
                    alert("请选择！")
                    return;
                }
                sccid = checkTr.find("input[name='a']").val();
                $(".integralNumber").text(integral==""?0:integral);
                $(".money").text(integral/100);
                billdal();
                $("#bill").show();
                break;
            case '设置每页显示条数' :
                var url = basePath
                    + "ea/giftcard/ea_getSearchListByLimit.jspa?staff.staffName="+$("#name").val()
                    + "staff.reference"+$("#phoneNo").val()+"staff.staffIdentityCard"+$("#idCard").val()+"staff.cardNumber"+$("#cardNumber").val();
                numback(url);

        }
    }

    $("#toSearch").click(function () {
        $("#SearchForm").attr(
            "action",
            basePath + "ea/giftcard/ea_getSearchList.jspa?pageNumber=0");
        document.getElementById("SearchForm").submit();
    });

    $("tr").bind("click",function(){
        $("input:radio").attr("checked",false);
        $(this).find("td").find("input:radio").attr("checked",true)
    });

    var telReg = /^1(3|4|5|6|7|8|9)\d{9}$/;
    var isIDCardReg=/(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
    $("#registeredSubmit").click(function () {
        if($("#registerName").val()==null || $("#registerName").val()==""){
                alert("姓名不能为空！")
                return;
        }
        if(!$("#call").val().match(telReg)){
            alert("手机号码输入有误！")
            return;
        }
        if(!$("#referee").val().match(telReg)){
            alert("推荐人手机号码输入有误！")
            return;
        }
        if($("#confirm_call").val()!=$("#call").val()){
            alert("两次输入的手机号码不一致！")
            return;
        }
        if($("#registerPassWord").val().length<6){
            alert("密码长度不安全！")
            return;
        }
        if($("#id_card").val()!=null && $("#id_card").val()!=""){
            if(!$("#id_card").val().match(isIDCardReg)){
                alert("身份证号输入有误！")
                return;
            }
        }
        if($("#shopping_card").val().length!=20){
            alert("购物卡号输入有误！")
            return;
        }
        regis("registere");
    })

    $("#reissueSubmit").click(function () {
        var type = $("#reissueCard h2 span").text();
        if(type=="开通购物卡"){
            if(!$("#openCall").val().match(telReg)){
                alert("手机号码输入有误！");
                return;
            }
            if($("#id_card").val()!=null && $("#id_card").val()!=""){
                if(!$("#id_card").val().match(isIDCardReg)){
                    alert("身份证号输入有误！")
                    return;
                }
            }
            if($("#openCard").val().length!=20){
                alert("购物卡号输入有误！");
                return;
            }
            regis("add");
        }else if(type = "补卡"){
            if($("#openCard").val().length!=20){
                alert("购物卡号输入有误！");
                return;
            }
            var id = $("input[name='a']:checked").parents("tr").attr("id")
            reissueCard(id);
        }
    })
});


//注册
function regis(flag) {
    var dataFrom ;
    if(flag=="registere"){
        dataFrom = $("#registerForm").serialize();
    }else if(flag=="add"){
        dataFrom = $("#openForm").serialize();
    }
    var url =basePath+"ea/giftcard/sajax_ea_registered.jspa?flag="+flag;
    $.ajax({
        url:encodeURI(url),
        type:"post",
        data:dataFrom,
        async:false,
        dataType:"json",
        success:function (data) {
            var member = eval("("+data+")");
            var isRegister = member.isRegister;
            var message = member.message;
            if(isRegister==false){
                alert(message);
            }else if(isRegister==true){
                alert(message);
                $(".register").val("");
                $("#registered").hide();
                $("#reissueCard .input_").val("");
                $("#reissueCard").hide();
                window.location.href=basePath+"/ea/giftcard/ea_getGidtCardPage.jspa";
            }
        }
    })
}

function reissueCard(staffID) {
    var url =basePath+"ea/giftcard/sajax_ea_reissueCard.jspa?staffID="+staffID+"&cardNumber="+$("#openCard").val();
    $.ajax({
        url:encodeURI(url),
        type:"post",
        dataType:"json",
        async:false,
        success:function (data) {
            var member = eval("("+data+")");
            var message = member.message;
            alert(message);
            window.location.href=basePath+"/ea/giftcard/ea_getGidtCardPage.jspa";
        }
    })
}

//挂失
function reportLoss(state,staffID) {
    var url =basePath+"ea/giftcard/sajax_ea_reportLoss.jspa?staffID="+staffID+"&state="+state;
    $.ajax({
        url:encodeURI(url),
        type:"post",
        dataType:"json",
        async:false,
        success:function (data) {
            if(state=="1"){
                alert("取消挂失成功！");
            }else {
                alert("挂失成功！")
            }
            window.location.href=basePath+"/ea/giftcard/ea_getGidtCardPage.jspa";
        }
    })
}

//账单
function billdal() {
    var url = basePath+"/ea/bonuspoints/sajax_DetailList.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "pageNumber":pageNumByBill,
            "sccid":sccid
        },
        success : function(data){
            var map = eval("("+data+")").map;
            var pageForm = map.pageForm;
            var signList = map.signList;
            var str = new Array();
            $(".billdal .bill").empty();
            $(".pageNum").text(0);
            $(".pageSum").text(0);
            if(pageForm != null&&pageForm.recordCount>0) {
                var list = pageForm.list;
                $(".pageNum").text(pageForm.pageNumber);
                $(".pageSum").text(pageForm.pageCount);
                for(var i = 0; i<list.length;i++)
                {
                    var bonus = list[i];
                    str.push("<tr class='bill'><td>"+bonus[0]+"</td><td>"+(bonus[4]=="A"?"<span style='color: red'>+"+bonus[1]+"</span>":"<span style='color: rgb(69, 167, 64)'>-"+bonus[1])+"</span>"+"  </td><td>"+bonus[2]+"</td></tr>");
                }
                $(".billdal").append(str.join(""))
            }
        }
    })
}

//生成订单号
function order(user) {
    $.ajax({
        url:basePath+"/ea/bonuspoints/sajax_getJum.jspa?user="+user+"&khd=",
        type:"get",
        dataType:"json",
        async:false,
        success:function(data){
            var mb=eval("("+data+")");
            jum=mb.jum;
            staffid=mb.wfj_staffid;
        }
    });
}
