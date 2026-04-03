$(document).ready(function () {

    //获取城市经济平台类型
    ajax1();
    //获取城市经济平台地域类型
    ajax2();

    $(document).on("click", ".down", function () {
        ajax2();
    });

    $('.search input').bind('keypress', function(event) {
        if (event.keyCode == "13") {
            pageNumber = 0;
            pageCount = null;
            goodsName = $(".search").find("input").val();
            ajax2();
        }
    });


})


function ajax1() {
    var url = basePath + "/ea/newpcend/sajax_ea_platformType.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: true,
        dataType: "json",
        data: {"hot": hot},
        success: function (data) {
            var type = eval("(" + data + ")");
            var temporary = [];
            if (type != null && type.list != null && type.list.length > 0) {
                $(type.list).each(function (i, dom) {
                    temporary.push("<li data-ppid='" + this[1] + "' data-price='" + this[2] + "'><p>" + this[0] + "</p></li>");
                })
                $(".att_list4").find(".sheng ul").append(temporary.join(""));
            }
        },
        error: function (data) {
            console.log("获取失败");
        }
    });
}


function ajax2() {
    if ((pageCount == null) ? 99 : pageCount >= pageNumber) {
        var name;
        if (hot.indexOf("省级") >= 0) {
            name = "a";
        } else if (hot.indexOf("县级") >= 0) {
            name = "b";
        } else if (hot.indexOf("村级") >= 0) {
            name = "c";
        }
        var url = basePath + "/ea/newpcend/sajax_ea_regionalTypes.jspa?";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "ppk.goodsName": goodsName,
                "pageForm.pageNumber": pageNumber + 1,
                "pageForm.pageSize": 9,
                "hot": name
            },
            success: function (data) {
                var regional = eval("(" + data + ")");
                var pageForm = regional.pageForm;
                var temporary = [];
                $(".att_list4").find(".jingji ul").empty();
                if (pageForm != null && pageForm.list != null
                    && pageForm.list.length > 0) {
                    $(pageForm.list).each(function (i, dom) {
                        temporary.push("<li data-ppid='" + this[1] + "'><p>" + this[0].substring(0, this[0].length - 1) + "联营经济平台</p></li>");
                    })
                }
                $(".att_list4").find(".jingji ul").append(temporary.join(""));

                if (pageForm != null) {
                    pageNumber = pageForm.pageNumber;
                    pageCount = pageForm.pageCount;
                }
            },
            error: function (data) {
                console.log("获取失败");
            }
        });
    }
}

function dianji() {
    pageNumber = 0;
    pageCount = null;
    goodsName = $(".search").find("input").val();
    ajax2();
}

//立即支付
function zhifu() {
    var money = $(".money").text();
    var name = $(".ipt").find("input").val();
    if (a != null && a != "") {
        if (membersppid != null && membersppid != "") {
            if (regionalppid != null && regionalppid != "") {
                if ($.trim(name) != "") {
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
                            generate(address, user, money, name);
                        },
                        error: function (data) {
                            console.log("获取失败");
                        }
                    });
                } else {
                    alert("请输入公司名称!")
                }
            } else {
                alert("请选择地域类型!")
            }
        } else {
            alert("请选择会员类型!")
        }
    } else {
        alert("请登录!")
    }
}

function generate(address, user, money, name) {

    var url = basePath + "ea/wfjplatform/sajax_ea_getzhifu.jspa?";
    $.ajax({
        type: "post",
        url: url,
        async: true,
        dataType: "json",
        data: {
            "goodsid": 3,
            "ppids": membersppid,
            "company.companyName": name,
            "company.companyManager": "",
            "cdl.companyPhone": user,
            "staffAddress.addressID": address,
            "company.companyIdentifier": name,
            "cdl.companyAddress": "01",
            "cdl.companyManager": "01",
            "moneys": money
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var page = member.count;

            if (page == "01") {
                var content = $(".att_con .att_list4 .mil.sheng ul li.active").text();
                ajaxsut(address, user, money, content, name);//确认订单 生成订单方法

            } else {
                alert("数据提交失败。请重试");
            }
        },
        error: function (data) {
            alert("提交订单失败");
        }
    });

}


// 确认订单 生成订单方法
function ajaxsut(address, user, money, content, name) {
    var ulp = basePath + "/ea/wfjshop/sajax_ea_Shopping.jspa?";
    $.ajax({
        type: "post",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            "sort": content,
            "platfromConpanyName": name,
            "platfromid": regionalppid,
            "platfromAccount": user,
            "staffAddress.addressID": address,
            "ppid": membersppid,
            "morre": money,
            "count": "1",
            "indus": money
        },
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

