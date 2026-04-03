$(function () {
    $(".hy .tab-box table tr").click(function () {
        $(this).addClass("active").siblings().removeClass("active")
    });

    $("#notJion").click(function () {
        document.location.href = basePath+"ea/sm/ea_index.jspa?cs=1";
    })

    $("#join").click(function () {
        if($(".tab-box table tr.active").length==0){
            alert("请选择");
        }else {
            var name = $(".tab-box table tr.active .name").text();
            var ppid = $(".tab-box table tr.active [name='ppid']").val();
            var goodsid = $(".tab-box table tr.active [name='goodsid']").val();
            var companyId = $(".tab-box table tr.active [name='companyId']").val();
            var money = $(".tab-box table tr.active .money span").text();
            // document.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId+"&ccompanyId=contactCompany20101230UB4U5884S30000000176";

            var url = basePath + "/ea/wfjshop/sajax_ea_validatecanBuy.jspa";
            $.ajax({
                url : url,
                type : "get",
                dataType : "json",
                data : {
                    ppid : ppid,
                    ccompanyId : "contactCompany20101230UB4U5884S30000000176",
                    account :account
                },
                success : function(data) {
                    var m = eval("(" + data + ")");
                    var result = m.result;
                    var mk = m.mk;
                    var login = m.login;
                    if (login == "login") {
                        document.location.href = basePath
                            + "page/WFJClient/NewLogin.jsp?loginPage=login";
                        return;
                    }
                    if (result != "success") {

                        alert(result);
                        return;
                    } else {
                        $.ajax({
                            url:encodeURI(basePath+"ea/sm/sajax_ea_payErCodeByIgl.jspa"),
                            type:"get",
                            dateType:"json",
                            data:{
                                cardNum:cardNum,
                                totalMoney:money,
                                ppId:ppid,
                                proName:name,
                                companyId:companyId

                            },
                            success:function (data) {
                                document.location.href = basePath+"page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum="+data+"&totalMoney="+money+"&totalNum=1&cardNum="+cardNum;
                            }
                        });
                    }
                },
                error : function(data) {
                    //alert("验证失败");
                }

            });

        }
    });
})
