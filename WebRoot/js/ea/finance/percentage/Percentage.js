$(function () {
    // 为弹出框准备下拉表内容
    $(".jqmWindow").jqm({
        modal: true,//
        overlay: 20
        //
    }).jqmAddClose('.close');//

    // 各个弹出框的关闭功能
    $(".JQueryreturns").click(function () {
        //notoken = 0;
        $(".jqmWindow").jqmHide();
    });
    // 键入时模糊查询项目分类
    $("#moc").click(function () {

        getCate($("#inputmoc").val());

    });


})

$(document)
    .ready(
        function () {

            // 项目分类
            $(".projectbtn")
                .click(
                    function () {
                        $(".jqmWindow", $("#selectxmForm"))
                            .jqmShow();
                        getProjectByxmtype("parameter=&xmtype=");//默认获取该部门下的所有项目
                    });

            // 新增项目
            $(".xzxm")
                .click(
                    function () {
                        window
                            .open(basePath
                                + "ea/productdesign/ea_getListProductdesign.jspa?ghua=ghua&fiveClear=4");
                    });


            var cID = "";

            $("table#xmtable tr[id]").live(
                "click",
                function (event) {
                    cID = this.id;

                    $("input.ra", $(this)).attr("checked",
                        "checked");
                });

            // 添加所选中的项目
            $("#qdxm").click(function () {
                if (cID != "" && cID != null && cID != undefined && cID != null) {
                    $("#ppId").val(cID);//产品id
                    $("#pName").val($("tr#" + cID).find("#projectName").text());//产品名称
                    $("#xmtypename").val($("tr#" + cID).find("#xmtypename")
                        .text());
                    $("#xmtype").val($("tr#" + cID).find("#xmtype")
                        .text());
                    if ($("#xmtype").val() == "043111") {
                        $(".baokx").show();
                    } else {
                        $(".baokx").hide();
                    }

                    $("#projectName").val(
                        $("tr#" + cID).find("#projectName")
                            .text());
                    $("#proID").val(cID);
                    $("#projectCode").val(
                        $("tr#" + cID).find(
                            "#projectCode")
                            .text());
                    $(".jqmWindow", $("#selectxmForm"))
                        .jqmHide();

                    /* //准备项目产品树
                     getProjectByParent(cID, $("tr#" + cID).find("#projectName")
                     .text());*/

                    return;
                } else {
                    //alert("请选择项目！");
                    $(".jqmWindow").jqmHide();//关闭弹框

                }
            });

            // 根据输入的项目名称查询
            $("input#searchxmbtn").click(
                function () {
                    cuID = "";
                    var parameter = $("input#parameterxm", $("table#searchxm")).val();
                    var xmtype = $("input#selectxm").val();
                    getProjectByxmtype("parameter=" + parameter + "&xmtype=" + xmtype);
                });

            // 上一页
            $("#xmsy").click(
                function () {
                    var sy = $("#xmsy").attr("title");
                    if (sy != 0) {
                        var parameter = $("input#parameterxm", $("table#searchxm")).val();
                        var xmtype = $("input#selectxm").val();
                        getProjectByxmtype("parameter=" + parameter + "&xmtype=" + xmtype + "&pageForm.pageNumber=" + sy);
                    } else {
                        alert("已是首页！");
                    }
                });

            // 下一页
            $("#xmxy").click(
                function () {
                    var xy = $("#xmxy").attr("title");
                    if (xy != 0) {
                        cuID = "";
                        var parameter = $("input#parameterxm", $("table#searchxm")).val();
                        var xmtype = $("input#selectxm").val();
                        getProjectByxmtype("parameter=" + parameter + "&xmtype=" + xmtype + "&pageForm.pageNumber=" + xy);

                    } else {
                        alert("已是尾页！");
                    }
                });

        });

/*$(document)
 .ready(
 function () {

 treedw = new dhtmlXTreeObject("dwTree", "100%", "100%", 0);
 treedw.enableDragAndDrop(false);
 treedw.enableHighlighting(1);
 treedw.enableCheckBoxes(0);
 treedw.enableThreeStateCheckboxes(false);
 treedw.setSkin(basePath + 'js/tree/dhx_skyblue');
 treedw.setImagePath(basePath + "js/tree/codebase/imgs/");
 treedw.loadXML(basePath + "js/tree/common/tree_b.xml");
 var getcodeurl = basePath
 + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110224xpd2t2jvda0000000002&date="
 + new Date().toLocaleString();
 treedw.insertNewChild(0,
 "scode20110224xpd2t2jvda0000000002", "单位类别", 0, 0,
 0, 0);
 $.ajax({
 url: encodeURI(getcodeurl),
 type: "get",
 async: true,
 dataType: "json",
 success: function cbf(data) {
 var member = eval("(" + data + ")");
 var nologin = member.nologin;
 if (nologin) {
 document.location.href = basePath
 + "page/ea/not_login.jsp";
 }
 var codeList = member.codeList;
 if (null == codeList) {
 return;
 }
 for (var i = 0; i < codeList.length; i++) {

 treedw.insertNewChild(
 "scode20110224xpd2t2jvda0000000002",
 codeList[i].codeID,
 codeList[i].codeValue, 0, 0, 0, 0);
 treedw.setUserData(codeList[i].codeID,
 "codeNumber", codeList[i].codeNumber);

 }
 },
 error: function cbf(data) {
 alert("数据获取失败！");
 }
 });
 treedw
 .setOnClickHandler(function () {

 treeid = treedw.getSelectedItemId();
 treename = treedw.getItemText(treeid);

 var typeName = $("input#ccompanyIDs",
 $("table#searchcompany")).val();

 if (treeid == "scode20110224xpd2t2jvda0000000002") {
 treename = "";
 }
 $(":input#dwparms").val(treename);
 cxwldw("contactCompany.companyName=" + typeName
 + "&cconnection.contactConnections="
 + treename);
 return;

 });

 var contactcID = "";// 已经添加到往来单位的ID
 var ccID = "";// ccompanyID
 $("table#gostable tr[id]").live("click", function (event) {
 contactcID = this.id;
 ccID = this.title;
 $("input.ra", $(this)).attr("checked", "checked");
 });

 // 选择往来单位
 $(".wanladw")
 .live("click",
 function () {
 $("#clicktr").val(
 $(this).parent().parent()
 .parent().attr("id"));
 $(".jqmWindow", $("#selectcompanyForm"))
 .jqmShow();
 cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
 });

 // 新增往来单位
 $(".xzdw")
 .live("click",
 function () {
 window
 .open(basePath
 + "ea/contactcompany/ea_getListContactCompany.jspa?");
 });

 // 添加所选中的往来单位
 $("#qdcompany").click(
 function () {

 if (contactcID != "") {
 var $tbl = $("#" + $("#linet").val());
 var clicktr = $("#line", $tbl).text();

 $("tr#" + clicktr, $tbl).find("#ccompanyName")
 .val(
 $("tr#" + contactcID).find(
 "#ccompanyname")
 .text());

 $("tr#" + clicktr, $tbl).find("#ccompanyID").val(
 contactcID);
 $(".jqmWindow", $("#selectcompanyForm"))
 .jqmHide();

 } else {
 alert("请选择往来单位！");
 }
 });

 // 根据输入的往来单位名称查询
 $("input#searchcc").click(
 function () {
 contactcID = "";
 var typeName = $("input#ccompanyIDs",
 $("table#searchcompany")).val();
 var contactConnections = $(":input#dwparms")
 .val();

 cxwldw("contactCompany.companyName=" + typeName
 + "&cconnection.contactConnections="
 + contactConnections);
 });

 // 上一页
 $("#dwsy")
 .click(
 function () {
 var sy = $("#dwsy").attr("title");
 if (sy != 0) {
 contactcID = "";
 var typeName = $(
 "input#ccompanyIDs",
 $("table#searchcompany"))
 .val();
 var contactConnections = $(
 ":input#dwparms").val();
 var typeCN = "contactCompany.companyName="
 + typeName
 + "&cconnection.contactConnections="
 + contactConnections
 + "&pageForm.pageNumber="
 + sy;
 cxwldw(typeCN);
 } else {
 alert("已是首页！");
 }
 });

 // 下一页
 $("#dwxy")
 .click(
 function () {
 var xy = $("#dwxy").attr("title");
 if (xy != 0) {
 contactcID = "";
 var typeName = $(
 "input#ccompanyIDs",
 $("table#searchcompany"))
 .val();
 var contactConnections = $(
 ":input#dwparms").val();
 var typeCN = "contactCompany.companyName="
 + typeName
 + "&cconnection.contactConnections="
 + contactConnections
 + "&pageForm.pageNumber="
 + xy;
 cxwldw(typeCN);
 } else {
 alert("已是尾页！");
 }
 });

 // ajax查询往来单位列表
 function cxwldw(typeCN) {
 if (notoken) {
 alert("正在获取数据！请稍等");
 return;
 }
 notoken = 1;
 $("#dwsy").attr("title", 0);
 $("#dwxy").attr("title", 0);
 $("#dwzy").attr("title", 0);
 var searchurl = basePath
 + "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
 $
 .ajax({
 url: encodeURI(searchurl + typeCN
 + "&date="
 + new Date().toLocaleString()),
 type: "get",
 async: true,
 dataType: "json",
 success: function cbf(data) {
 var member = eval("(" + data + ")");
 var nologin = member.nologin;
 if (nologin) {
 document.location.href = basePath
 + "page/ea/not_login.jsp";
 }
 var pageForm = member.pageForm;

 if (pageForm == null) {
 alert("没有数据");
 notoken = 0;
 return;
 }

 var dqy = pageForm.pageNumber;// 当前页
 var zys = pageForm.pageCount;// 总页数
 if (dqy > 1) {
 $("#dwsy").attr("title", dqy - 1);
 }
 if (dqy < zys) {
 $("#dwxy").attr("title", dqy + 1);
 }
 $("span#dwzycount").text(zys);
 var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
 tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
 tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
 tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
 tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
 tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";

 for (var i = 0; i < pageForm.list.length; i++) {
 tabletr += "<tr style='cursor: hand;' title="
 + pageForm.list[i].ccompanyID
 + " id = "
 + pageForm.list[i].ccompanyID
 + ">";
 tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
 + pageForm.list[i].contactConnectionID
 + " name='checkradio'/></td>";
 tabletr += "<td id='ccompanyname' align='center'>"
 + pageForm.list[i].companyName
 + "</td>";
 tabletr += "<td id='contactConnections' align='center'>"
 + pageForm.list[i].contactConnections
 + "</td>";
 tabletr += "<td id='industryType' align='center'>"
 + pageForm.list[i].industryType
 + "</td>";
 tabletr += "<td id='companyTel'  align='center'>"
 + pageForm.list[i].companyTel
 + "</td>";
 tabletr += "<td id='cresponsible' align='center'>"
 + pageForm.list[i].cresponsible
 + "</td>";
 tabletr += "<td id='responsibleTel' align='center'>"
 + pageForm.list[i].responsibleTel
 + "</td>";

 tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
 + pageForm.list[i].ccompanyID
 + "</td>";
 tabletr += " </tr>";
 }
 tabletr += " </table>";
 $("#body_02cc").html(tabletr);
 $("#body_02cc").show();
 // alert("数据加载成功");
 notoken = 0;
 window.status = "数据加载成功";
 },
 error: function cbf(data) {
 notoken = 0;
 alert("数据获取失败！");
 }
 });
 }
 });*/

/** ***********************项目分类模糊查询**************************** */
//键入时查询项目分类
function getCate(value) {


    var url = basePath + "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
        + new Date().toLocaleString();

    $
        .ajax({
            url: encodeURI(url),
            type: "post",
            async: false,
            dataType: "json",
            data: {
                parameter: $.trim(value)
            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var xmList = member.xmlist;
                var parameter = $("input#parameterxm", $("table#searchxm")).val();
                var params = "parameter=" + parameter + "&xmtype=";

                var str = "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\"" + params + "\");'>所有项目分类</a></span><br/>";

                for (var i = 0; i < xmList.length; i++) {
                    params = "parameter=" + parameter + "&xmtype=" + xmList[i].codeSn;

                    str += "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\"" + params + "\");'>("
                        + xmList[i].codeSn
                        + ")"
                        + xmList[i].codeValue + "</a></span><br/>";

                }
                if (str == "") {
                    str = "&nbsp;无搜索结果";
                }
                $(".mohuc").html("");
                $(".mohuc").html(str).show();
                $("#treeg").hide();


            },
            error: function cbf(data) {
                alert("数据获取失败！");
            }
        });

}
//根据项目分类获取项目
function getProjectByxmtype(prams) {
    $("#xmsy").attr("title", 0);
    $("#xmxy").attr("title", 0);
    $("#xmzy").attr("title", 0);
    var url = basePath
        + "ea/costsheet/sajax_ea_getProjectList.jspa?" + prams + "&dat=" + Math.random();

    $.ajax({
        url: encodeURI(url),
        type: "POST",
        async: true,
        dataType: "json",
        success: function cbf(data) {

            var member = eval("(" + data + ")");
            //console.log(member)
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath
                    + "page/ea/not_login.jsp";
            }
            var pageForm = member.pageForm;
            if (pageForm == null) {
                $("#body_02xm").html("");
                return;
            }
            var dqy = pageForm.pageNumber;// 当前页
            var zys = pageForm.pageCount;// 总页数
            if (dqy > 1) {
                $("#cdpsy").attr("title", dqy - 1);
            }
            if (dqy < zys) {
                $("#xmxy").attr("title", dqy + 1);
            }
            //
            $("span#xmzycount").text(zys);
            var tabletr = "";

            for (var i = 0; i < pageForm.list.length; i++) {
                tabletr += "<tr style='cursor: hand;' title="
                    + pageForm.list[i].ppID + " id = "
                    + pageForm.list[i].ppID + ">";
                tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
                    + pageForm.list[i].ppID
                    + " name='checkradio'/></td>";
                tabletr += "<td align='center'>" + (i + 1) + "</td>";
                tabletr += "<td id='projectCode' align='center'>"
                    + pageForm.list[i].goodsNum + "</td>";
                tabletr += "<td id='projectName' align='center'>"
                    + pageForm.list[i].goodsName + "</td>";
                tabletr += "<td id='xmtypename' align='center'>"
                    + pageForm.list[i].xmtypename + "</td>";
                tabletr += "<td id='xmtype' style='display:none;'>"
                    + pageForm.list[i].xmtype + "</td>";

                tabletr += " </tr>";
            }


            $("#body_02xm").html(tabletr);
        },
        error: function cbf(data) {
            alert("数据获取失败！");
        }
    });

}
/** ***********************项目分类结束**************************** */
/*function getProjectByParent(ppID, parentName) {
 $("#xmul").html("");
 var url = basePath + "ea/costsheet/sajax_ea_getProjectByParent.jspa?date="
 + new Date().toLocaleString();
 $
 .ajax({
 url: url,
 type: "POST",
 async: false,
 dataType: "json",
 data: {
 "productPack.ppID": ppID
 },
 success: function (data) {
 var member = eval("(" + data + ")");
 var oList = member.productlist;


 var data1 = new Array();

 for (var i = 0; i < oList.length; i++) {
 data1[i] = {
 id: oList[i][0],
 pid: oList[i][1],
 text: oList[i][2]
 };
 }

 var ts = new TreeSelector($("ul#xmul"),
 data1, null);
 ts.createTree();

 $("#xmul").treeview({
 collapsed: false,
 unique: false
 });

 $("#xmul").find("span").each(function () {


 var $newtable = $("#Layer1").clone();
 $newtable.find("table").attr("id", $(this).parent().attr("id") + "tbl").addClass("datatbl");
 $(this).after($newtable.html());
 });


 }, error: function (data) {
 alert("获取项目失败");
 }
 });

 }*/
