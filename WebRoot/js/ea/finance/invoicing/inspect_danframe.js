$(function () {
    //制单时间
    /*var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var date = d.getDate();
    var day = d.getDay();
    var hours = d.getHours();
    var minutes = d.getMinutes();
    var seconds = d.getSeconds();
    var ms = d.getMilliseconds();
    var curDateTime = year + "-";
    if (month > 9)
        curDateTime = curDateTime + month + "-";
    else
        curDateTime = curDateTime + "0" + month + "-";
    if (date > 9)
        curDateTime = curDateTime + date;
    else
        curDateTime = curDateTime + "0" + date;
    //初始化
    pageNum++;
    var jnum = journalNum();
    if (pageCount != '' && pageNum <= parseInt($.trim(pageCount))) {
        var url = "";
        url = basePath
            + "/ea/purchase1/ea_toInspectAddDan.jspa?pageForm.pageNumber=" + pageNum
            + "&pageNumber=" + pageNumber + "&print=abc&curDateTime=" + curDateTime
            + "&pageNum=" + pageNum + "&pageCount=" + pageCount + "&jnum=" + jnum;


        var titleClone = $("div#titleClone").clone(true);
        titleClone.attr("id", "titleClone" + pageNum);
        titleClone.find("#buttonClone").attr("id", "buttonClone" + pageNum);
        titleClone.find("#contextClone").attr("id", "contextClone" + pageNum);
        titleClone.find("#frameClone").attr("id", "frameClone" + pageNum);
        titleClone.find("iframe[name='main']").attr("name", "main" + pageNum);
        titleClone.find("#dayinClone").attr("id", "dayinClone" + pageNum);
        titleClone.find("#xsxyClone").attr("id", "xsxyClone" + pageNum);
        titleClone.find("#xsqbClone").attr("id", "xsqbClone" + pageNum);
        titleClone.find("span#JQuerySubmitgd").attr("id", "JQuerySubmitgd" + pageNum);
        titleClone.find("#noth").attr("id", "noth" + pageNum);
        titleClone.find("#pageCode").text("第 " + pageNum + " 张/共 " + pageCount + " 张");
        titleClone.attr("style", "width: 100%;height: 100%");
        //titleClone.find("#frameClone"+pageNum).attr("src",url);
        titleClone.find("span#JQuerySubmitgd" + pageNum).empty();
        titleClone.find("span#JQuerySubmitgd" + pageNum).append("<input id=\"JQuerySubmitgd" + pageNum + "\" class=\"menubtn\" type=\"button\" value=\"保存\" onclick=\"save(" + pageNum + ")\"/>");
        titleClone.find("span#okrk").empty();
        titleClone.find("span#okrk").append("<input id=\"okrk\" class=\"menubtn\" type=\"button\" value=\"合格入库\" onclick=\"saverk(" + pageNum + ")\"/>");
        $("body").append(titleClone);
        tijiao(url, pageNum);
    }
    ;
    $(".xsqbCloneClass").live('click', function () {
        var c = 0;
        if (pageNum == 1) {
            alert("已经是首页");
            return false;
        } else {
            c = pageNum - 1;
            $("div#titleClone" + pageNum).remove();
        }
        pageNum = c;
        var jnum = journalNum();
        if (pageCount != '' && pageNum <= parseInt($.trim(pageCount))) {
            var url = "";
            url = basePath
                + "/ea/purchase1/ea_toInspectAddDan.jspa?pageForm.pageNumber=" + pageNum
                + "&pageNumber=" + pageNumber + "&print=abc&curDateTime=" + curDateTime
                + "&pageNum=" + pageNum + "&pageCount=" + pageCount + "&jnum=" + jnum;

            var titleClone = $("div#titleClone").clone(true);
            titleClone.attr("id", "titleClone" + pageNum);
            titleClone.find("#buttonClone").attr("id", "buttonClone" + pageNum);
            titleClone.find("#contextClone").attr("id", "contextClone" + pageNum);
            titleClone.find("#frameClone").attr("id", "frameClone" + pageNum);
            titleClone.find("iframe[name='main']").attr("name", "main" + pageNum);
            titleClone.find("#dayinClone").attr("id", "dayinClone" + pageNum);
            titleClone.find("#xsxyClone").attr("id", "xsxyClone" + pageNum);
            titleClone.find("#xsqbClone").attr("id", "xsqbClone" + pageNum);
            titleClone.find("span#JQuerySubmitgd").attr("id", "JQuerySubmitgd" + pageNum);
            titleClone.find("#noth").attr("id", "noth" + pageNum);
            titleClone.find("#pageCode").text("第 " + pageNum + " 张/共 " + pageCount + " 张");
            titleClone.attr("style", "width: 100%;height: 100%");
            //titleClone.find("#frameClone"+pageNum).attr("src",url);
            titleClone.find("span#JQuerySubmitgd" + pageNum).empty();
            titleClone.find("span#JQuerySubmitgd" + pageNum).append("<input id=\"JQuerySubmitgd" + pageNum + "\" class=\"menubtn\" type=\"button\" value=\"提交审核\" onclick=\"save(" + pageNum + ")\"/>");
            titleClone.find("span#okrk").empty();
            titleClone.find("span#okrk").append("<input id=\"okrk\" class=\"menubtn\" type=\"button\" value=\"合格入库\" onclick=\"saverk(" + pageNum + ")\"/>");
            $("body").append(titleClone);
            tijiao(url, pageNum);
        }
        ;
    });
    $(".xsxyCloneClass").live('click', function () {
        pageNum++;
        if (pageNum > parseInt($.trim(pageCount))) {
            pageNum = parseInt($.trim(pageCount));
            alert("已经是尾页");
            return;
        }
        var jnum = journalNum();
        if (pageCount != '' && pageNum <= parseInt($.trim(pageCount))) {
            var b = pageNum - 1;
            $("div#titleClone" + b).remove();
            var url = "";
            url = basePath
                + "/ea/purchase1/ea_toInspectAddDan.jspa?pageForm.pageNumber=" + pageNum
                + "&pageNumber=" + pageNumber + "&print=abc&curDateTime=" + curDateTime
                + "&pageNum=" + pageNum + "&pageCount=" + pageCount + "&jnum=" + jnum;

            var titleClone = $("div#titleClone").clone(true);
            titleClone.attr("id", "titleClone" + pageNum);
            titleClone.find("#buttonClone").attr("id", "buttonClone" + pageNum);
            titleClone.find("#contextClone").attr("id", "contextClone" + pageNum);
            titleClone.find("#frameClone").attr("id", "frameClone" + pageNum);
            titleClone.find("iframe[name='main']").attr("name", "main" + pageNum);
            titleClone.find("#dayinClone").attr("id", "dayinClone" + pageNum);
            titleClone.find("#xsxyClone").attr("id", "xsxyClone" + pageNum);
            titleClone.find("#xsqbClone").attr("id", "xsqbClone" + pageNum);
            titleClone.find("span#JQuerySubmitgd").attr("id", "JQuerySubmitgd" + pageNum);
            titleClone.find("#noth").attr("id", "noth" + pageNum);
            titleClone.find("#pageCode").text("第 " + pageNum + " 张/共 " + pageCount + " 张");
            titleClone.attr("style", "width: 100%;height: 100%;");
            //titleClone.find("#frameClone"+pageNum).attr("src",url);
            titleClone.find("span#JQuerySubmitgd" + pageNum).empty();
            titleClone.find("span#JQuerySubmitgd" + pageNum).append("<input id=\"JQuerySubmitgd" + pageNum + "\" class=\"menubtn\" type=\"button\" value=\"提交审核\" onclick=\"save(" + pageNum + ")\"/>");
            titleClone.find("span#okrk").empty();
            titleClone.find("span#okrk").append("<input id=\"okrk\" class=\"menubtn\" type=\"button\" value=\"合格入库\" onclick=\"saverk(" + pageNum + ")\"/>");
            $("body").append(titleClone);
            tijiao(url, pageNum);
        };
    });

    //编号
    function journalNum() {
        var url = basePath + "ea/cashierbills/sajax_ea_getBillID.jspa?date=" + new Date().toLocaleString();
        var str = "";
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }
                str += member.BillID;
            },
            error: function cbf(data) {
                alert("数据获取失败！")
            }
        });
        return str;
    }*/

    tijiao(basePath+ "/ea/purchase1/ea_toInspectAddDan.jspa",null);

    $(".dayinCloneClass").live('click', function () {
        var printframe = "" + ($(this).attr("id").substring(10))
        var height = $("#" + printframe).css("height");
        window.open($("#" + printframe).attr("src"), 'newwindow', 'height=' + height + ',width=1060,top=10,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,alwaysRaised=1');
    });
});


function tijiao(url) {
    var html1 = '<form id="queryForm" name="queryForm" method="post" target="_self" action="' + url + '">' +
        '<input type="hidden" id="financialbillID" name="financialbillID" value="'+financialbillID+'"/>' +      //pk_id是一个长度超过3000字符的值
        '</form>';
    document.getElementById('frameClone').contentWindow.document.write(html1);  //将表单写入iframe中
    document.getElementById('frameClone').contentWindow.document.getElementById('queryForm').submit();//执行iframe中表单的提交

}
// --------------------------提交审核---------------
function save() {
    $i = $("#frameClone");
    //notnull失去焦点
    $i.contents().find(".notnull").trigger("blur");
    $err = $i.contents().find("1 .error");
    var re = 0;
    $(".notnull", $i.contents().find("#table1")).each(function () {
        if (this.value == "") {
            $(this).css("background-color", "red");
            re = 1;
        }
    });
    if (notoken) {
        alert("正在提交数据！请稍等");
        return;
    }
    //货物合格数量判断
    $(".put3", $i.contents().find("tr.checkgoods")).each(function () {
        if (this.value == "") {
            $(this).focus();
            $(this).blur();
            re = 1;
        }
    });
    //$(".put3", $i.contents().find("tr.checkgoods")).trigger("blur");
    if ($err.length) {
        re = 1;
    }
    if (re) {
        alert("请填完所有必填项！");
        return;
    }
    //验货单分页提交结果
    var jnum = $i.contents().find("#journalNumcg").val();//采购单编号
    var strs = "";
    $i.contents().find("[id='goodsBillsID']").each(function () {
        strs += $(this).val() + "-" + jnum + ",";
    });
    $i.contents().find("#billGoodsForm").attr("target", "hidden").attr("action",
        basePath + "ea/purchase1/ea_toUpdateIsQualify.jspa");
    var bform = "billGoodsForm";
    window.frames["frameClone"].contentWindow.document.forms[bform].submit.click();
    token=2;
    $("#yyh", $i.contents().find("#table1")).attr("checked", "checked");
    //保存验货单数据
    /*if (strs == "2") {
        $("#yyh", $i.contents().find("#table1")).attr("checked", "checked");
        alert("操作成功！所有物品已经验货！");
        window.close();
    } else {*/
        /*alert("操作成功");
        $("#yyh", $i.contents().find("#table1")).attr("checked", "checked");
        window.close();*/
        /*var St = "";
        for (var i = 0; i < str.split(",").length - 1; i++) {
            var isRepeated = 0;
            var str1 = str.split(",")[i];//循环所有的选中验货物品
            for (var j = 0; j < strs.split(",").length - 1; j++) {
                if (str1 == strs.split(",")[j]) {
                    isRepeated++;
                }
            }
            if (isRepeated == 0) {
                St += str1 + ",";
            }
        }
       var url = basePath
            + "/ea/purchase1/ea_toInspectAddDan.jspa?print=danframe&financialbillID="+financialbillID;
        document.location.href = url*/
    /*？*/

}
//----------------------------------提交审核结束-----------------------
//-----------------------------------合格入库-------------------------
function saverk() {

    $i = $("#frameClone");
    //notnull失去焦点
    $i.contents().find(".notnull").trigger("blur");
    $err = $i.contents().find("1 .error");
    var re = 0;
    $(".notnull", $i.contents().find("#table1")).each(function () {
        if (this.value == "") {
            $(this).css("background-color", "red");
            re = 1;
        }
    });
    if (notoken) {
        alert("正在提交数据！请稍等");
        return;
    }
    //货物合格数量判断
    $(".put3", $i.contents().find("tr.checkgoods")).each(function () {
        if (this.value == "") {
            $(this).focus();
            $(this).blur();
            re = 1;
        }
    });
    if ($err.length) {
        re = 1;
    }
    if (re) {
        alert("请填完所有必填项");
        return;
    }
    $("#isQualify", $i.contents().find("tr.checkgoods")).each(function () {
        if (this.value == "0") {
            this.value = "";
            $(this).focus();
            re = 2;
        }
    });
    if (re == 2) {
        alert("合格数量为零！不能入库！");
        return;
    }
    $("input#okrk").addClass("grey").addClass("disabled").attr({disabled: "disabled"});
    //保存验货单数据并添加入库单
    alert("操作成功！");
    document.getElementById("frameClone").contentWindow.child();//inspect_danbill.jsp toUpdateIsQualify
    //token=2;
}
//取消添加验货单
$("#JQueryClose").live('click', function () {
    if (confirm("确定要关闭验货窗口？")) {
        window.close();
    }
});
function re_load() {
    window.close();
    /*if (token)
        var url = basePath + "/ea/purchase1/ea_getinspectList.jspa";
    document.location.href = url;*/
}