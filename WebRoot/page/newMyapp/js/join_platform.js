function ajax(){
    var url=basePath+"/ea/newpcend/sajax_ea_promotionProducts.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data : {
            "ppk.ppID" : ppid,
            "ppk.companyID" : "company201009046vxdyzy4wg0000000025"
        },
        success : function cbf(data) {
            var temporary=eval("(" + data + ")");
            var obj=temporary.object;
            if(obj!=null){
                ptppid = obj[1];
                companyId = obj[5];
                ptmorre = obj[2];
                ptstandard = obj[6];
            }
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}




function findIndustry(codepid,position){
    var url=basePath+"/ea/industry/sajax_ea_getIndustry.jspa?codePID="+codepid;
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member=eval("(" + data + ")");
            var list=member.industryList;
            var htl = [];
            for (var i = 0; i < list.length; i++) {
                htl.push("<li data-codeid='"+list[i].codeID+"'>"+list[i].codeValue+"</li>");
            }
            $("."+position).html(htl.join(""));
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}
//立即支付
function zhifu() {
    var money = $(".money").text();
    var name;
    var industy;
    if(hot=="公司"){
        name = $(".ipt2").find("input").val();
        industy = $(".ipt").find("p").text();
    }
    if (a != null && a != "") {
        if (ppid != null && ppid != "") {
            if(hot=="公司"){
                if($.trim(industy) == ""){
                    alert("请选择行业类别!")
                    return;
                }
                if($.trim(name) == ""){
                    alert("请输入公司名称!")
                    return;
                }
            }

            //查询收货地址
            var url = basePath + "/ea/newpcend/sajax_ea_shippingAddress.jspa?";
            $.ajax({
                url: url,
                type: "post",
                async: true,
                dataType: "json",
                success: function (data) {
                    var temporary = eval("(" + data + ")");
                    var address = temporary.address;
                    var user = temporary.user;
                    joinVip(address, user, money, name,industy);
                },
                error: function (data) {
                    console.log("获取失败");
                }
            });
        } else {
            alert("请选择平台会员类型!")
        }
    } else {
        alert("请登录!")
    }
}
// 加入会员
function joinVip(address,user,money, name,industy) {
    var url = basePath + "/ea/wfjshop/sajax_ea_validatecanBuy.jspa";
    $.ajax({
        url : url,
        type : "post",
        dataType : "json",
        data : {
            ppid : ppid,
            ccompanyId : ccompanyId
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            var mk = m.mk;
            if (result != "success") {
                alert(result);
                return;
            } else {
                ajaxsut(address,money,name,industy);
            }
        },
        error : function(data) {
            alert("验证失败");
        }
    });
}
function ajaxsut(address,money,name,industy) {
    var ulp = basePath + "/ea/wfjshop/sajax_ea_Shopping.jspa?";
    var da;
    if(hot=="公司"){
        da = {
            "ppids":"",
            "staffAddress.addressID": address,
            "sort": goodsName,
            "ccompanyId":ccompanyId,
            "standard":"",
            "indus": money,
            "ppid": ppid,
            "count": "1",
            "mkuserID":"",
            "industy":industy,
            "company.industryType":industy,
            "company.companyName":name,
            "company.companyIdentifier":name,
            "cdl.companyAddress":1,
            "cdl.companyEmail":1,
            "cdl.companyManager":1,
            "cdl.companyPhone":1,
            "morre": money,
            "recordId":"",

            "ptppid":ptppid,
            "companyId":companyId,
            "ptmorre":ptmorre,
            "ptstandard":ptstandard
        }
    }else{
        da = {
            "ppids":"",
            "staffAddress.addressID": address,
            "sort": goodsName,
            "ccompanyId":ccompanyId,
            "standard":"",
            "indus": money,
            "ppid": ppid,
            "count": "1",
            "mkuserID":"",
            "morre": money,
            "recordId":"",

            "ptppid":ptppid,
            "companyId":companyId,
            "ptmorre":ptmorre,
            "ptstandard":ptstandard
        }
    }
    $.ajax({
        type: "post",
        url: ulp,
        async: true,
        dataType: "json",
        data: da,
        success: function (data) {
            var member = eval("(" + data + ")");
            var ppt = member.ddid;
            if (ppt == null) {
                alert("数据提交失败。请重试");
            } else {
                ddid = ppt;
                if ($(".money").text() != "0") {
                    document.location.href = basePath + "ea/newpcend/ea_toGoodsPayMethod.jspa?payJournalNum="+ddid;
                } else {
                    document.location.href=basePath+"/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=03&ddid="+ddid;
                }
            }
        },
        error: function (data) {
            alert("提交订单失败");
        }
    });
}
