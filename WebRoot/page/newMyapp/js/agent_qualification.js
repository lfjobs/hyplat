$(document).ready(function() {

    //获取代理资格
    ajax();


    $(document).on("click", ".down", function () {
        ajax();
    });
})


function ajax() {
    if ((pageCount == null) ? 99 : pageCount >= pageNumber) {
        var name;
        var ppid;
        if (hot.indexOf("省级") >= 0) {
            name = "a";
            ppid='p20170220ZVZR76B88M0000000018';
        } else if (hot.indexOf("县级") >= 0) {
            name = "b";
            ppid='p20170220ZVZR76B88M0000000019';
        } else if (hot.indexOf("村级") >= 0) {
            name = "c";
            ppid='p20170220ZVZR76B88M0000000020';
        }
        var url = basePath + "/ea/newpcend/sajax_ea_qualification.jspa?";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "ppk.goodsName": goodsName,
                "pageForm.pageNumber": pageNumber + 1,
                "pageForm.pageSize": 9,
                "hot": name,
                "ppk.ppID":ppid,
                "ppk.companyID":"company201009046vxdyzy4wg0000000025"
            },
            success: function (data) {
                var regional = eval("(" + data + ")");
                var pageForm = regional.pageForm;
                var object = regional.object;
                var temporary = [];
                $(".att_list4").find(".mil ul").empty();
                if (pageForm != null && pageForm.list != null
                    && pageForm.list.length > 0) {
                    $(pageForm.list).each(function (i, dom) {
                        temporary.push("<li data-ppid='" + this[1] + "'><p>" + this[0].substring(0, this[0].length - 1) + "代理资格</p></li>");
                    })
                }
                $(".att_list4").find(".mil ul").append(temporary.join(""));

                if (pageForm != null) {
                    pageNumber = pageForm.pageNumber;
                    pageCount = pageForm.pageCount;
                }

                $(".money").text(object[2]);
                price = object[2];
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
    ajax();
}

function zhifu() {
    if (a != null && a != "") {
        if (agentid != null && agentid != "") {
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
                    zhifu1(address);
                },
                error: function (data) {
                    console.log("获取失败");
                }
            });
        } else {
            alert("请选择代理类型!")
        }
    } else {
        alert("请登录!")
    }
}

function zhifu1(address) {
    var name;
    var ppid;
    var sort;
    if (hot.indexOf("省级") >= 0) {
        name = "a";
        ppid='p20170220ZVZR76B88M0000000018';
        sort='省级代理';
    } else if (hot.indexOf("县级") >= 0) {
        name = "b";
        ppid='p20170220ZVZR76B88M0000000019';
        sort='县级代理';
    } else if (hot.indexOf("村级") >= 0) {
        name = "c";
        ppid='p20170220ZVZR76B88M0000000020';
        sort='村级代理';
    }
    // 验证代理平台是否重复购买
    if(ppid =="p20170220ZVZR76B88M0000000018"||ppid =="p20170220ZVZR76B88M0000000019"||ppid=="p20170220ZVZR76B88M0000000020"){
        var url = basePath + "/ea/wfjshop/sajax_ea_yanzhengzg.jspa";
        $.ajax({
            url : url,
            type : "post",
            dataType : "json",
            data : {
                ppids : ppid
            },
            success : function(data) {

                var mss = eval("(" + data + ")");
                var productpp=mss.productp;
                if (productpp == "login") {
                    return alert("请登录!");;
                }
                if (productpp== "productp") {
                    alert("已经购买过同一级别的代理资格");
                }
                if (productpp== "productpt") {
                    zhifu2(ppid,sort,address);
                }
            },
            error : function(data) {
                alert("验证失败");
            }
        });
        return ;
    }
}



function zhifu2(ppid,sort,address) {
    var ulp = basePath + "/ea/wfjshop/sajax_ea_Shopping.jspa?";
    $.ajax({
        type : "post",
        url : ulp,
        async : true,
        data : {
            "ppids" : agentid,
            "sort":sort,
            "ccompanyId":"",
            "standard":"",
            "indus":price,
            "ppid":ppid,
            "count":1,
            "mkuserID":"",
            "morre":price,
            "recordId":"",
            "staffAddress.addressID":address
        },
        dataType : "json",
        success : function(data) {
            var json = eval('(' + data + ')');
            if (json == null) {
                alert("数据提交失败。请重试");
            } else {
                ddid = json.ddid;
                if(ddid=="fail"){
                    alert("该推荐人没有注册过微分金，请重新填写，或者不填写！");
                    return false;
                }
                if ($(".money").text() != "0") {
                    document.location.href = basePath + "ea/newpcend/ea_toGoodsPayMethod.jspa?payJournalNum="+ddid;
                } else {
                    document.location.href=basePath+"/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=03&ddid="+ddid;
                }
            }
        },
        error : function(data) {
            alert("提交订单失败");
        }
    });
}