$(function () {

    object.push("khd=", khd);
    object.push("&flag=", flag);
    object.push("&identifying=", identifying);
    object.push("&ccompanyId=", ccompanyId);

    findconWealth();//获取经济平台
    bankNum();//获取该用户银行卡张数
    bz();

    //跳转到银行列表
    if (flag == 'sys') {
        if (identifying == 'company') {
            $(".bank_num").click(function () {
                window.location.href = basePath + "ea/industry/ea_getBankCardsList.jspa?" + object.join("") + "&staffid="
                    + staffid + "&sccid=" + sccid + "&user=" + user + "&editType=00&mark=01";
            });
        }
    } else {
        $(".bank_num").click(function () {
            window.location.href = basePath + "ea/perinfor/ea_getBankCardInformation.jspa?" + object.join("") + "&staffId="
                + staffid + "&sccid=" + sccid + "&user=" + user + "&editType=00&mark=01";
        });
    }

});

function findconWealth() {
    var url = basePath + "/ea/jinbi/sajax_findconWealth.jspa?" + object.join("") + "&sccid=" + sccid;
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var mark = member.mark;
            var custypename = member.custypename;
            var cuscom = member.cuscom;
            var contactCompany = member.contactCompany;
            var custypename = member.custypename;
            var productPackaging = member.productPackaging;
            if (mark = 'ok') {

                if (cuscom.state == "1") {
                    $("#cusname").text(staffName);
                }
                if (cuscom.state == "2") {
                    $("#cusname").text(contactCompany.companyName);
                }
                $(".text_bottom").text(custypename);
                state = cuscom.state;//判断是  公司还是个人（1  个人      2  公司）
            } else {
                alert("获取数据出错！")
            }
        }
    });
}

function bankNum() {

    var url = basePath + "/ea/jinbi/sajax_getUserBank.jspa?" + object.join("") + "&staffid=" + staffid + "&sccid=" + sccid;
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var count = member.count;
            var wfj_staffid = member.wfj_staffid;
            if (count <= 0) {
                $(".part_banknum").text("0");
            } else {
                $(".part_banknum").text(count);
            }
        }
    });
}

function bz() {
    var url = basePath + "/ea/jinbi/sajax_AjxaNotWithDrawGlod.jspa";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data:{
            wfj_jifen_id:$("#wfjJifenId").val()
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var count = member.count;
        }
    });
}





