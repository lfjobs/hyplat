$(function () {
    // 为弹出框准备下拉表内容
    $(".jqmWindow").jqm({
        modal: true,//
        overlay: 20
        //
    }).jqmAddClose('.close');//

    if (cid != "company201009046vxdyzy4wg0000000025") {
        $(".yjsj").find("input").attr("readonly", true);
    } else {
        $(".yjsj").find("input").attr("readonly", false);
    }


    if (showType == "edit") {
        $(".projectbtn").hide();
        $("#edit").show();
        $("#cpid").hide();
    } else if (showType == "editYj") {
        $(".projectbtn").hide();
        $("#edit").hide();
        $("#cpid").show();
        getProjectByParent(parentId, ppname, producttype, true);
    } else {
        $(".projectbtn").show();
        $("#edit").hide();
        $("#cpid").show();
    }


    /*if(showType=="edit"){
     $(".projectbtn").hide();
     $("#edit").show();
     $("#cpid").hide();
     }else if(showType == "add"){
     $(".projectbtn").show();
     $("#edit").hide();
     $("#cpid").show();
     }else{
     $(".projectbtn").hide();
     $("#edit").show();
     $("#cpid").hide();
     document.getElementById("editYj").setAttribute("readOnly",false);
     }*/
    var oList;
    $.ajax({
        url: basePath + "ea/productdesign/sajax_ea_getProjectByParent.jspa?date="
        + new Date().toLocaleString(),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                z
                document.location.href = basePath
                    + "page/ea/not_login.jsp";
            }
            oList = member.productlist;
        }, error: function (data) {
            alert("获取项目失败");
        }
    });
    // 选择产品分类
    $("input.xzcpfl").click(function () {
        category("pro", paret);
    });
    //主项目目录关闭
    $("#closeml").click(function () {
        $("#jqModel").hide();

    });

    //菜单起效
    //关闭
    $(".closewindow").click(function () {
        if (confirm("确定要关闭添加窗口？")) {
            window.close();
        }

    });
    //#saveDJInfo
    //保存方法
    $("#saveCPDJInfo").click(function () {

        var ppID = $("#ppID").val();
        var compId = $("#compId").val();
        var ppccj = $("#ppccj").val();
        var pplsjpplsj = $("#pplsj").val();
        var compyxyj = $("#compyxyj").val();
        var compgjhhryj = $("#compgjhhryj").val();
        var comphhryj = $("#comphhryj").val();
        var wfjdyj = $("#wfjdyj").val();
        var dlsVIPhyyj = $("#dlsVIPhyyj").val();
        var dlshyyj = $("#dlshyyj").val();
        var xfzjf = $("#xfzjf").val();

        if (ppccj == "" || ppccj == null ||
            pplsjpplsj == "" || pplsjpplsj == null ||
            compyxyj == "" || compyxyj == null ||
            compgjhhryj == "" || compgjhhryj == null ||
            comphhryj == "" || comphhryj == null ||
            wfjdyj == "" || wfjdyj == null ||
            dlsVIPhyyj == "" || dlsVIPhyyj == null ||
            dlshyyj == "" || dlshyyj == null ||
            xfzjf == "" || xfzjf == null) {

            alert("请填完所有必填项");
            return false;
        }

        $("#CostCPDJForm").attr("target", "hidden")
            .attr(
                "action",
                basePath
                + "/ea/productdesign/ea_getpackegejiage.jspa");
        document.CostCPDJForm.submit.click();

    });

    // 整个添加页面 获得焦点带边框，失去焦点不带边框
    $("#CostSheetForm input.xiaoguo").blur(function () {

        if ($(this).attr("class").indexOf("model1") == -1) {
            $(this).addClass("model1");
        }
    }).focus(function () {
        if ($(this).attr("class").indexOf("model1") != -1) {
            $(this).removeClass("model1");
        }
    });

    //物品新增一行

    $(".addline").click(function () {
        getProjectByParent($("#proID").val(), $("#projectName").val(), $("#xmtypename").val(), false);
        var max = 0;
        $(".datatbl").each(function () {
            var trid = $(this).attr("id").substring(3);
            if (trid != "") {
                if (trid > max) {
                    max = trid;
                }
            }
        });
        var data = new Array();
        data[0] = {
            id: cid,
            pid: '-1',
            text: gongsiname
        };
        for (var i = 0; i < oList.length; i++) {
            data[i + 1] = {
                id: oList[i][0],
                pid: oList[i][2],
                text: oList[i][1]
            };
        }
        var ts = new TreeSelector($("select#orgfd" + max)[0],
            data, -1);
        ts.createTree();

        /*$("#xmul").find("span").each(function(){
         var $newtable = $("#Layer1").clone();
         $newtable.find("table").attr("id",$(this).parent().attr("id")+"tbl").addClass("datatbl");
         $(this).after($newtable.html());
         });*/

        $("#Layer2").find("#cpid").each(function (i) {
            $(this).find("#kelongcp").find(".cpname").val($("#projectName").val());
        });
    });
    //记录产品配件选中的id
    $("#goodtable").find("tr").click(function () {
        $("#line").text($(this).attr("id"));
    });
    //记录产品选中的id
    $("input.productSelect").click(function () {
        $("#line").text("");
        placeholder = "";
        $("#body_02").html("");
        $("#selectType").val("goods");
        $(".jqmWindow", $("#goodsForm")).jqmShow();
    });

    //物品新增一行

    $(".deleteline").click(function () {
        $("#" + $("#line").text()).remove();
    });


    // 物品表格单击样式问题
    $("#goodtable :input").click(function () {
        $("#goodtable td").find("div").each(function () {
            $(this).css("border", "none");
            $(this).find(".caz").hide();

        });
        $(this).parent("div").css("border", "1px solid #000000");
        $(this).parent("div").find(".fou").focus();
        $(this).parent("div").find(".caz").show();

    });
    $("#goodtable td").click(function () {
        $("#goodtable td").each(function () {
            $(this).find("div").css("border", "none");
            $(this).find(".caz").hide();

        });

        $(this).find("div").css("border", "1px solid #000000");
        $(this).find(".fou").focus();
        $(this).find(".caz").show();

    });

    // 责任人,项目名称，银行选中查询结果
    $("#goodsQuery tr[id]").live("click", function (event) {
        var types = $("#types").val();
        var $trs = $("#goodsQuery tr#" + this.id);

        if (types == "projectName" || types == "staffname" || types == "bankaccount") {
            $trs.find("td").each(function () {
                if (this.id != "") {
                    $("#table3").find("#" + this.id).val($(this).text());
                    if (this.id == "xmtype") {
                        $("#table3").find("#xmtype2").val($(this).text());
                    }
                }


            });


        }

        var kelongid = $("#line").text();
        var id = kelongid.substring(6);

        if (types == "goodsNum" || types == "goodsName" || types == "ccompanyName" || types == "connectName") {
            $trs.find("td").each(function () {
                $("tr#" + kelongid).find("#" + this.id).val($(this).text());


                if (this.title == "ava" && $(this).text().length != 0) {
                    $op = $("<option />");
                    $op.attr("value", $(this).text()).text(($(this).text() == "null" || $(this).text() == null) ? null : $(this).text());
                    $("select#goodsVariableID", $("#" + kelongid)).append($op);
                }

            });
            if (types == "goodsNum" || types == "goodsName") {
//				 修改当前行的所有input的name
                $("#kelong" + id).find(':input[name]').each(function () {

                    $(this).attr("name", "logbookmap[" + id + "]." + this.name);

                });

            }
        }

        $("input.rauser", $(this)).attr("checked", "checked");
        $("#goodsQuery").hide();
    });

    // 键入时模糊查询项目分类
    $("#moc").click(function () {

        getCate($("#inputmoc").val());

    });

    // 各个弹出框的关闭功能
    $(".JQueryreturns").click(function () {
        notoken = 0;
        $(".jqmWindow").jqmHide();
    });
    // 添加页面的返回功能；
    $(".JQueryClose").click(function () {
        notoken = 0;
        re_load();
    });

//	// 选择物品弹出带有物品分类的弹出框
//	$("#shuju").click(function() {
//	
//		$(".jqmWindow", $("#goodsForm")).jqmShow();
//	});


    // 迭代的商品删除
    $(".ajaxsc")
        .click(
            function () {
                var taxstatus = $("#cashiertaxstatus").val();
                if (taxstatus != "00") {
                    alert("已归档不能删除该条数据！");
                    return;
                }
                if (confirm("是否删除？")) {
                    var goodsBillsID = $(this).parent().find(
                        "input#goodsBillsID").val();
                    var delurl = basePath
                        + "ea/cashierbills/sajax_ea_delGoodsBills.jspa?typeID="
                        + goodsBillsID + "&date="
                        + new Date().toLocaleString();
                    $.ajax({
                        url: encodeURI(delurl),
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
                            var typeID = member.typeID;
                            alert("删除成功！");
                            $("tr#" + typeID).remove();
                        },
                        error: function cbf(data) {
                            alert("数据获取失败！");
                        }
                    });
                }
            });

    // 保存
    $("input.JQuerySubmitgd").click(function () {
        if (notoken) {
            alert("正在提交数据！请稍等");
            return;
        }
        var bp = false;
        $(".datatbl").find(".brokerage").each(function () {
            if ($(this).val() == null || $(this).val() == "" || parseFloat($(this).val()) < 0) {
                alert("出厂价、零售价和佣金不能为空,并且为正数!");
                bp = true;
            }
        });

        //出厂价
        var cc = parseFloat($.trim($("#cc").val()) == "" ? "0.00" : $.trim($("#cc").val()));
        //零售价
        var ls = parseFloat($.trim($("#ls").val()) == "" ? "0.00" : $.trim($("#ls").val()));
        //代理佣金
        var dl = 0;
        $(".dl").each(function () {
            dl = dl + parseFloat($.trim($(this).val()));
        });
        //佣金
        var yj1 = Number(ls - cc - dl).toFixed(4);
        var yj2 = Number($("#yj").val()).toFixed(4);
        if (yj1 != yj2) {
            alert("佣金计算有误请重新计算！")
            return "";
        }

        $(".qian").each(function () {
            var num = $(this).val();
            if ($(this).attr("id") == "p20170605KY3VAANZJG0000000003" && parseFloat(num) > 0 && $("#cp").val() == "") {
                alert("请选择正确的投资设备类型");
                bp = true;
            }
        });
        if (!bp) {
            var tt = false;
            $(".datatbl").each(function () {
                var re = /^(1-9|[1-9][0-9]?|100)%$/;
                $(".yjbl").each(function () {
                    if ($(this).val() != null && $(this).val() != "" && $(this).val() != " ") {
                        if (!re.test($(this).val())) {
                            $(this).css({color: "red"});
                            tt = true;
                        } else {
                            $(this).css({color: "gray"});
                        }
                    }
                });
                /*if(!tt){
                 var $tableid=$(this).find("table.yjsj");
                 var $td=$(this).parent().parent().prev().find("td");
                 var num=$tableid.find("th").index("th");
                 for(var i=1;i<num;i++){
                 var num1=0;
                 $tableid.find("tr").each(function(i1){
                 if(i1>0){
                 $(this).find("td").each(function(i2){
                 if(i==i2&&i>0){
                 var a=0;
                 if($(this).find("input:eq(0)").val()!=" "&&$(this).find("input:eq(0)").val()!=""&&$(this).find("input:eq(0)").val()!=null){
                 a=parseFloat($(this).find("input:eq(0)").val());
                 }
                 num1=parseFloat(num1.toFixed(3))+parseFloat(a);
                 }
                 });
                 }
                 });

                 if(num1!=parseFloat($(this).find(".brokerage").val())){
                 var blfa=$tableid.find("th:eq("+i+")").find(".yjname").text();
                 alert($(this).find(".brokerage").val()+"--"+num1+"--'"+blfa+"'佣金方案总和之不正确");
                 bp=true;
                 //return;
                 }
                 }
                 }*/
            });
            if (tt) {
                alert("数据格式不正确");
                bp = true;
            }
        }

        if (bp) {
            return;
        }
        $("input.notnull", $("#CostSheetForm")).trigger("blur");
        /*if ($("form .error").length) {
         alert("请填完所有必填项");
         return;
         }*/
        var val = $("#projectCode").val();
        if (val == null || val == '' || val == 0) {
            alert("请填完所有必填项!");
            return;
        }
        $("tr.checkgoods", $("table#goodtable")).each(function (index) {
            $(this).find("input#sorting").attr("value", index);
        });
        $("#CostSheetForm").attr("target", "hidden")
            .attr(
                "action",
                basePath
                + "/ea/productdesign/ea_addyj.jspa");
        document.CostSheetForm.submit.click();
        token = 2;
        $("#CostSheetForm")
            .attr(
                "action", "");
        return false;
    });

    //产品发布
    $("input.JQuerySubmitfb").click(function () {

        if (notoken) {
            alert("正在提交数据！请稍等");
            return;
        }

        $("tr.checkgoods", $("table#goodtable")).each(function (index) {
            $(this).find("input#sorting").attr("value", index);
        });
        $("#profitForm").attr("target", "hidden")
            .attr("action", basePath + "/ea/productdesign/ea_productPublish.jspa?pptype=" + typestring);
        document.profitForm.submit.click();
        token = 2;
        $("#profitForm").attr("action", "");
        return false;
    });

    $("table#xmtable tr[id]").live("click", function (event) {
        bankID = this.id;
        registrationID = this.title;
        $("input.ra", $(this)).attr("checked", "checked");
    });

});

/** **********************************项目分类和项目**************************************** */

$(document).ready(function () {
    $(".jisuan", $("table#cpid")).live("keyup keydown focus blur", function (event) {
        if (this.value != "" && this.value != " " && this.value != null) {
            var re = /^(1-9|[1-9][0-9]?|100)%$/;
            if (isNaN(this.value)) {
                $(this).css("color", "red");
            } else {
                var $trThis = $(this).parent().parent().parent().parent().parent().parent();
                //出厂价
                var cc = parseFloat($.trim($trThis.find("input#cc").val()) == "" ? "0.00" : $.trim($trThis.find("input#cc").val()));
                //零售价
                var ls = parseFloat($.trim($trThis.find("input#ls").val()) == "" ? "0.00" : $.trim($trThis.find("input#ls").val()));
                //代理佣金
                var dl = 0;
                if (showType == "editYj") {
                    $(".editDlyj").each(function () {
                        dl = dl + parseFloat($.trim($(this).val()));
                    });
                } else {
                    $(".dl").each(function () {
                        dl = dl + parseFloat($.trim($(this).val()));
                    });
                }
                //佣金
                var yj = ls - cc - dl;
                $trThis.find("input#yj").attr("value", yj.toFixed(4));
                $trThis.find("input.bfb").each(function () {
                    var t = $(this).attr("title")
                    if ($(this).val() != "" && $(this).val() != null && $(this).val() != " ") {
                        var bfb = yj * (parseFloat($(this).val() == "" || $(this).val() == null ? "0.00" : $(this).val()) / 100);
                        if (bfb < 0)
                            bfb = 0;
                        $(this).parent().parent().find("span.jbs" + t).text(bfb.toFixed(3));
                    }
                });
            }
        } else {
            $(this).parent().parent().find("input#yj").attr("value", "");
        }
    });

    $(".jisuan2", $("table#cpid")).live("keyup keydown focus blur", function (event) {
        if (this.value != "" && this.value != " " && this.value != null) {
            var re = /^(1-9|[1-9][0-9]?|100)%$/;
            if (!re.test(this.value)) {
                $(this).css("color", "red");
            } else {
                var $trThis = $(this).parent().parent().parent().parent().parent().parent();
                console.dirxml($trThis);
                //出厂价
                var cc = parseFloat($.trim($trThis.find("input#cc").val()) == "" ? "0.00" : $.trim($trThis.find("input#cc").val()));
                //零售价
                var ls = parseFloat($.trim($trThis.find("input#ls").val()) == "" ? "0.00" : $.trim($trThis.find("input#ls").val()));
                //佣金
                var yj = ls - cc;
                $trThis.find("input#yj").attr("value", yj.toFixed(2));
                $trThis.find("input.bfb").each(function () {
                    var t = $(this).attr("title");
                    if ($(this).val() != "" && $(this).val() != null && $(this).val() != " ") {
                        var bfb = yj * (parseFloat($(this).val() == "" || $(this).val() == null ? "0.00" : $(this).val()) / 100);
                        if (bfb < 0)
                            bfb = 0;
                        $(this).parent().parent().find("span.jbs" + t).text(bfb.toFixed(3));
                    }
                });
                $(this).css("color", "#000");
            }
        } else {
            $(this).parent().parent().find("input#yj").attr("value", "");
        }
    });

    // 项目分类
    $(".projectbtn").click(function () {

        $(".jqmWindow", $("#selectxmForm")).jqmShow();

        getProjectByxmtype("parameter=&xmtype=&yj=01");// 默认获取该部门下的所有项目
    });

    // 新增项目
    $(".xzxm").click(function () {
        window
            .open(basePath
                + "ea/productdesign/ea_getListProductdesign.jspa?ghua=ghua&fiveClear=2");
    });

    var cID = "";

    $("table#xmtable tr[id]").live("click", function (event) {
        cID = this.id;

        $("input.ra", $(this)).attr("checked", "checked");
    });

    // 添加所选中的项目
    $("#qdxm").click(function () {
        if (cID != "") {

            $.ajax({
                url: encodeURI(basePath + "ea/productdesign/sajax_ea_ajaxgetprice.jspa?date="
                    + new Date().toLocaleString()),
                type: "post",
                async: false,
                dataType: "json",
                data: {
                    pid: cID
                },
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath
                            + "page/ea/not_login.jsp";
                    }
                    var ps = member.ps;
                    if (ps != null) {
                        cc = ps[0];
                        ls = ps[1];
                        yj = parseFloat(ls) - parseFloat(cc);
                    }

                },
                error: function cbf(data) {
                    alert("数据获取失败！");
                }
            });

            $("#producttype").val(
                $("tr#" + cID).find("#producttype")
                    .text());

            $("#projectName").val(
                $("tr#" + cID).find("#projectName")
                    .text());
            $("#proID").val(cID);
            $("#projectCode").val(
                $("tr#" + cID).find("#projectCode")
                    .text());
            $(".jqmWindow", $("#selectxmForm"))
                .jqmHide();


            // 准备项目产品树
            getProjectByParent($("#proID").val(), $("tr#" + cID)
                .find("#projectName").text(), $(
                "tr#" + cID).find("#producttype")
                .text(), true);
            //return;
        } else {
            alert("请选择项目！");
        }
    });

    // 根据输入的项目名称查询
    $("input#searchxmbtn").click(function () {
        cuID = "";

        var parameter = $("input#parameterxm",
            $("table#searchxm")).val();
        var xmtype = $("input#selectxm").val();

        getProjectByxmtype("parameter=" + parameter
            + "&xmtype=" + xmtype + "&yj=01");
    });

    // 上一页
    $("#xmsy").click(function () {
        var sy = $("#xmsy").attr("title");
        if (sy != 0) {

            var parameter = $("input#parameterxm",
                $("table#searchxm")).val();
            var xmtype = $("input#selectxm").val();

            getProjectByxmtype("parameter=" + parameter
                + "&xmtype=" + xmtype
                + "&pageForm.pageNumber=" + sy + "&yj=01");
        } else {
            alert("已是首页！");
        }
    });

    // 下一页
    $("#xmxy").click(function () {
        var xy = $("#xmxy").attr("title");
        if (xy != 0) {
            cuID = "";
            var parameter = $("input#parameterxm",
                $("table#searchxm")).val();
            var xmtype = $("input#selectxm").val();
            getProjectByxmtype("parameter=" + parameter
                + "&xmtype=" + xmtype
                + "&pageForm.pageNumber=" + xy + "&yj=01");

        } else {
            alert("已是尾页！");
        }
    });

    /*$(".comsel").live("change",function(){
     var num=$(this).attr("id").substring(5);
     var url = basePath + "ea/productdesign/sajax_ea_AjxaVal.jspa?date="
     + new Date().toLocaleString();

     $.ajax({
     url : encodeURI(url),
     type : "post",
     async : false,
     dataType : "json",
     data : {
     comid : $(this).find("option:selected").val()
     },
     success : function cbf(data) {
     var member = eval("(" + data + ")");
     var main = member.main;
     var tableval="";
     var widnum=0;
     if(main==null|| main.length<=0){
     tableval = $("#Layer1").find(".yjsj").clone();
     widnum=$("#Layer1").find(".yjsj").find("tr:eq(0)").children('th').length;
     widnum=(widnum-1)*90+130;
     }else{
     var yjlist=member.yjlist;
     widnum=130+(main.length)*90;
     tableval = "<tr>" +
     "<th align='center' bgcolor='#E4F1FA' width='130px' height='20px'>" +
     "<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>" +
     "<img src='<%=basePath%>/images/header_bg.gif' width='1' height='14' />" +
     "</span><span class='xx'>*</span>会员名称</th>";
     for ( var int = 0; int < main.length; int++) {
     tableval+="<th align='center' bgcolor='#E4F1FA' width='130px' height='20px'>" +
     "<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>" +
     "<img src='<%=basePath%>/images/header_bg.gif' width='1' height='14' />" +
     "</span><span class='xx'>*</span><span class='yjname'>"+main[int].yjName+"</span>" +
     "<input type='hidden' value='"+main[int].yjId+"' class='hxid'/></th>";
     }
     tableval+="</tr>";
     for ( var int = 0; int<yjlist.length; int++) {
     tableval+="<tr>" +
     "<td align='center' bgcolor='#FFFFFF'><input " +
     "name='goodsName' readonly='readonly' " +
     "style='width:100%;border:0px;' class='querys fou' " +
     "value='"+yjlist[int][1]+"' /><input type='hidden' value='"+yjlist[int][0]+"' class='zxid'/></td>";
     for ( var z = 0; z < main.length; z++) {
     tableval+="<td align='center' bgcolor='#FFFFFF'><input " +
     "name='proMoney' class='gstd bfb yjbl' size='3' " +
     "style='width:100%; border:0px; text-align: right;' " +
     "title='"+yjlist[int][z+2]+"' value='"+yjlist[int][z+2]+"'/>" +
     "<input name='bsid' type='hidden' value='"+main[z].yjId+"'/>" +
     "<input name='meid' type='hidden' value='"+yjlist[int][0]+"' /></td>";
     }
     tableval+="</tr>";
     }
     }
     $("#fgs"+num+"").find("table").html("");
     $("#fgs"+num+"").find("table").parent().width(widnum);
     $("#fgs"+num+"").find("table").html(tableval);

     },
     error : function cbf(data) {
     alert("数据获取失败！");
     }
     });
     });*/
});

/** **********************************项目分类和项目**************************************** */
/** ***********************项目分类模糊查询**************************** */
//键入时查询项目分类
function getCate(value) {


    var url = basePath + "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
        + new Date().toLocaleString();

    $.ajax({
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
            var params = "parameter=" + parameter + "&xmtype=&yj=01";

            var str = "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\"" + params + "\");'>所有项目分类</a></span><br/>";

            for (var i = 0; i < xmList.length; i++) {
                params = "parameter=" + parameter + "&xmtype=" + xmList[i].codeSn + "&yj=01";

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
        + "/ea/productdesign/sajax_ea_getProjectList.jspa?" + prams + "&dat=" + Math.random();

    $.ajax({
        url: encodeURI(url),
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
                    + pageForm.list[i].productCode + "</td>";
                tabletr += "<td id='projectName' align='center'>"
                    + pageForm.list[i].goodsName + "</td>";
                tabletr += "<td id='producttype' align='center'>"
                    + pageForm.list[i].producttype +
                    "<input type='hidden' class='productstate' value=" + pageForm.list[i].productstate + "></td>";

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
function getProjectByParent(ppid, parentName, proName, bool) {
    if (bool) {
        $("#cpid").empty();
    }

    var max = 0;
    $(".datatbl").each(function () {
        var trid = $(this).attr("id").substring(3);
        if (trid != "") {
            if (trid > max) {
                max = trid;
            }
        }
    });
    var str = "";
    var num = parseInt(max) + 1;
    if (max == 0) {
        str = "应用于所有子公司";
    } else {
        str = "<select class='comsel' id='orgfd" + num + "' name='str[" + num + "].fcomId' style='border:0'></select>";
    }
    if (showType == "editYj") {
        var $newtable = $("#Layer3").clone();
    } else {
        var $newtable = $("#Layer1").clone();
    }
    $newtable.find("div:eq(0)").attr("id", "fgs" + num).addClass("datatbl");
    /*var tabval="<tr><td height='20px'></td></tr><td>"+str+"</td></tr><tr><td>"+$newtable.html()+"</td></tr>";*/
    var tabval = "<tr><td height='20px'></td></tr><tr><td>" + $newtable.html() + "</td></tr>";
    $("#cpid").append(tabval);
    $("#fgs" + num).find(".cpname").val(parentName);
    $("#fgs" + num).find(".cpid").val(ppid);
    if (showType != "editYj") {
        $("#fgs" + num).find(".cp").each(function () {
            if ($(this).attr("id") == "cc") {
                $(this).val(cc);//.attr("readonly","readonly");
            } else if ($(this).attr("id") == "ls") {
                $(this).val(ls);
            } else if ($(this).attr("id") == "yj") {
                $(this).val(yj);//.attr("readonly","readonly");
            }
            var strname = $(this).attr("name");
            $(this).attr("name", "str[" + num + "]." + strname);
        });
    } else {
        $("#fgs" + num).find(".cp").each(function () {
            //console.log(cc);
            ls = pclist[0].rePrice;
            cc = pclist[0].efPrice;
            yj = pclist[0].brokerage;
            if ($(this).attr("id") == "cc") {
                $(this).val(cc);//.attr("readonly","readonly");
            } else if ($(this).attr("id") == "ls") {
                $(this).val(ls);
            } else if ($(this).attr("id") == "yj") {
                $(this).val(yj);//.attr("readonly","readonly");
            }
            var strname = $(this).attr("name");
            $(this).attr("name", "str[" + num + "]." + strname);
        });
    }

    $("#fgs" + num).find("input.yjbl").each(function (i) {
        $(this).parent().find("input").each(function () {
            var strname = $(this).attr("name");
            $(this).attr("name", "str[" + num + "].layout[" + i + "]." + strname);
        });
    });
    if (showType == "editYj") {
        $("div#Layer3").empty();
    }
    $(".jisuan", $("table#cpid")).trigger("keydown");
    $(".addline").attr("disabled", false).removeClass("grey");
    $(".JQuerySubmitgd").attr("disabled", false).removeClass("grey");
}

function re_load() {
    if (token) {
        if (showType == "editYj") {
            document.location.href = basePath + "/ea/productdesign/ea_toAddOrEditProductDesign.jspa?showType=edit&flexbutton=yongjin&productDesign.ppID=" + parentId;
            window.opener.location.href = window.opener.location.href;
        }
        else {
            document.location.href = basePath + "/ea/productdesign/ea_toAddOrEditProductDesign.jspa?showType=edit&flexbutton=yongjin&productDesign.ppID=" + $("input#proID").val();
            window.opener.location.href = window.opener.location.href;
        }
    }
}
function showGood() {
    $("#body_02").html("");
    $("#selectType").val("projects");
    $(".jqmWindow", $("#goodsForm")).jqmShow();
}

function close3() {
    $("#jqModel3").hide();
    $("#query").html("");
}