//新增物品、物品管理切换
$(document).on("click", ".gl_tab li", function () {
    $(this).parent().children("li").removeClass("active");
    $(this).addClass("active");
    $("#tab_div>div").hide();
    $("#tab_div>div").eq($(this).index()).show();
})
// 商品点击选中
$(document).on("click", ".ul_con aside", function () {
    if ($(this).is(".aside_yes")) {
        $(this).removeClass().addClass("aside_no");
    } else {
        $(this).removeClass().addClass("aside_yes");
    }
})
$(document).on("click", "#div_table tr", function () {
    if ($(this).find(".img_no").is(":hidden")) {
        $(this).find(".img_no").show();
        $(this).find(".img_yes").hide();
        $(this).removeClass("active");
    } else {
        $("#div_table aside .img_yes").hide();
        $("#div_table aside .img_no").show();
        $("#div_table tr").removeClass("active");
        $(this).find(".img_no").hide();
        $(this).find(".img_yes").show();
        $(this).addClass("active");
    }
})

//点击创收部门，异步获取部门信息，初始化下拉列表
$('.csbm_xiala-1').on('click', function () {
    var url = basePath + "ea/scBudget/sajax_ea_getOrganizationByStaff.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data: {companyid: companyid, staffId: staffId},
        dataType: "json",
        success: function cbf(data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var oList = member.organizationList;
            depIdArray = new Array(oList.length);
            depNameArray = new Array(oList.length);
            for (var i = 0; i < oList.length; i++) {
                depIdArray[i] = oList[i].organizationID;//部门id
                depNameArray[i] = oList[i].organizationName;//部门名称
            }
            initSelectSwiper(0);
        }, error: function cbf(data) {
            alert("数据获取失败！");
        }
    });
});

//异步获取负责人信息，初始化下拉列表
// $('.xiala-1').on('click', function () {
//     var depId = '';
//     if (showFlag == true) {
//         depId = $("#ttsw_dep_y_id").val();//部门id
//     } else {
//         depId = $("#ttsw_dep_n_id").val();//部门id
//     }
//     //值为0则未选部门，提示信息
//     if (depId == 0) {
//         alert("请先选择创收部门");
//         return false;
//     }
//     var url = basePath + "ea/scBudget/sajax_ea_ajaxStaffForDep.jspa";
//     $.ajax({
//         url: url,
//         type: "POST",
//         data: {
//             "departmentID": depId,
//         },
//         dataType: "json",
//         success: function cbf(data) {
//             var member = (new Function("return " + data))();//格式化返回参数
//             var oList = member.staffList;
//             staffIdArray = new Array(oList.length);
//             staffNameArray = new Array(oList.length);
//             staffCodeArray = new Array(oList.length);
//             for (var i = 0; i < oList.length; i++) {
//                 staffIdArray[i] = oList[i][0];//负责人id
//                 staffNameArray[i] = oList[i][1];//负责人名称
//                 staffCodeArray[i] = oList[i][2];//负责人编码
//             }
//             initSelectSwiper(1);
//         }, error: function cbf(data) {
//             alert("数据获取失败！");
//         }
//     });
// });

/**
 *初始化下拉信息
 */
function initSelectSwiper(num) {
    if (num == 0) {//部门下拉初始化
        depSelectSwiper();
    } else {//责任人下拉初始化
        empSelectSwiper();
    }
}

/**
 * 部门下拉初始化数据
 */
function depSelectSwiper() {
    if (showFlag == false) {//所有部门.
        //创收部门模拟下拉框
        var csS1 = new selectSwiper({
            el: '.csbm_select_box1',
            data: depNameArray,
            init: function (index) {
                if (index !== -1) {
                    $('.btn1').html(this.data[index]);
                }
            },
            okFunUndefind: function () {
                alert('必须选择一项');
                return false;
            },
            okFun: function (index) {
                $('.csbm_xiala-1').val(this.data[index]);
                $('.csbm_id_xiala-1').val(depIdArray[index]);
                $("body").removeClass("body_yc");
            },
            closeFun: function () {
                $("body").removeClass("body_yc");
            },
        });
        csS1.openSelectSwiper();
        $("body").addClass("body_yc");
    }
}

/**
 *负责人下拉初始化
 */
function empSelectSwiper() {
    var hgS1 = new selectSwiper({
        el: '.select_box1',
        data: staffNameArray,
        init: function (index) {
            if (index !== -1) {
                $('.btn1').html(this.data[index]);
            }
        },
        okFunUndefind: function () {
            alert('必须选择一项');
            return false;
        },
        okFun: function (index) {
            $('.xiala-1').val(this.data[index] + "(" + staffCodeArray[index] + ")");
            $('.ttsw_emp_id').val(staffIdArray[index]);
            $('.ttsw_emp_name').val(staffNameArray[index]);
            $('.ttsw_emp_code').val(staffCodeArray[index]);
            $("body").removeClass("body_yc");
        },
        closeFun: function () {
            $("body").removeClass("body_yc");
        },
    });
    hgS1.openSelectSwiper();
    $("body").addClass("body_yc");
}

$('.inv_name').on('click', function () {
    var url = basePath + "ea/scBudget/sajax_ea_getDepot.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data: {companyid: companyid},
        dataType: "json",
        success: function cbf(data) {
            var member = (new Function("return " + data))();
            var invList = member.depotList;
            depotIdArray = new Array(invList.length);
            depotNameArray = new Array(invList.length);
            for (var i = 0; i < invList.length; i++) {
                depotIdArray[i] = invList[i].depotID;
                depotNameArray[i] = invList[i].depotName;
            }
            invSelectSwiper();
        }, error: function cbf(data) {
            alert("数据获取失败！");
        }
    });
});

function invSelectSwiper() {
    var csS1 = new selectSwiper({
        el: '.csbm_select_box2',
        data: depotNameArray,
        init: function (index) {
            if (index !== -1) {
                $('.btn1').html(this.data[index]);
            }
        },
        okFunUndefind: function () {
            alert('必须选择一项');
            return false;
        },
        okFun: function (index) {
            $('.inv_name').val(this.data[index]);
            $('.inv_id').val(depotIdArray[index]);
            $("body").removeClass("body_yc");
        },
        closeFun: function () {
            $("body").removeClass("body_yc");
        },
    });
    csS1.openSelectSwiper();
    $("body").addClass("body_yc");

}

//点击项目名称触发弹框
$(document).on("click", "#project-name", function () {
    initItemInfo();
})

//点击项目分类触发弹框
$(document).on("click", "#project-fl", function () {
    initItemInfo();
})


/**
 * 添加
 */
function toAddItem(){
    //是否是选择全部false全部true单一部门
    // $("#ttsw_hid_showFlag").val(showFlag);
    //所选部门id名称
    var hid_departmentID,hid_departmentName;
    var hid_dataDepotID,hid_dataDepotName;
    if(showFlag == true){
        hid_departmentID = $("#ttsw_dep_y_id").val();
        hid_departmentName = $("#ttsw_dep_y_name").val();
    }else{
        hid_departmentID = $("#ttsw_dep_n_id").val();
        hid_departmentName = $("#ttsw_dep_n_name").val();
        hid_dataDepotID = $("#ttsw_inv_n_id").val();
        hid_dataDepotName = $("#ttsw_inv_n_name").val();
    }
    if(tenantFlag!="personal" && (hid_departmentName == null || hid_departmentName == "" ) ){
        alert("请选择部门");
        return false;
    }
    $("#ttsw_hid_departmentID").val(hid_departmentID);
    $("#ttsw_hid_departmentName").val(hid_departmentName);

    var billsType = $("#billsType").val();
    if((billsType == "入库单" || billsType == "出库单" || billsType == "盘库单" || billsType == "报损单") && (hid_dataDepotName == null || hid_dataDepotName == "")){
        alert("请选择仓库");
        return false;
    }
    $("#ttsw_hid_dataDepotID").val(hid_dataDepotID);
    $("#ttsw_hid_dataDepotName").val(hid_dataDepotName);

    //单据凭证号
    var hid_billId = $("#ttsw_billID").val();
    if(hid_billId == null || hid_billId == "" ){
        alert("请录入单据凭证号");
        return false;
    }
    $("#ttsw_hid_billId").val(hid_billId);
    //公司名称
    var hid_companyName = $("#ttsw_companyNmae").val();
    if(tenantFlag!="personal" && (hid_companyName == null || hid_companyName == "") ){
        alert("请录入公司名称");
        return false;
    }
    $("#ttsw_hid_companyName").val(hid_companyName);
    //项目名称
    // var hid_itemName = $("#project-name").val();
    // if(hid_itemName == null || hid_itemName == "" ){
    //     alert("请选择项目名称");
    //     return false;
    // }
    // $("#ttsw_hid_itemName").val(hid_itemName);
    //项目分类
    var hid_xmTypeName = $("#xiangmu").val();
    var hid_xmTyp = $("#xiangmuId").val();
    if(hid_xmTyp == null || hid_xmTyp == "" ){
        alert("请选择项目分类");
        return false;
    }
    $("#ttsw_hid_xmType").val(hid_xmTyp);
    $("#ttsw_hid_xmTypeName").val(hid_xmTypeName);

    //行业分类
    var hid_hyfl = $("#hyfl").val();
    var hid_hyflId = $("#hyflId").val();
    if(hid_hyflId == null || hid_hyflId == "" ){
        alert("请选择行业分类");
        return false;
    }
    $("#ttsw_hid_tradeId").val(hid_hyflId);
    $("#ttsw_hid_tradeName").val(hid_hyfl);
    // //项目类型
    // var xmType = $("#ttsw_item_check_type").val();
    // $("#ttsw_hid_xmType").val(xmType);
    //项目编号
    // var itemCode = $("#ttsw_item_check_code").val();
    // $("#ttsw_hid_itemCode").val(itemCode);
    // //项目id
    // var hid_itemId = $("#ttsw_item_check_id").val();
    // $("#ttsw_hid_itemId").val(hid_itemId);
    //负责人id
    var hid_staffFzrId = $(".ttsw_emp_id").val();
    if(hid_staffFzrId == null || hid_staffFzrId == "" ){
        alert("请选择负责人");
        return false;
    }
    $("#ttsw_hid_staffFzrId").val(hid_staffFzrId);
    //负责人名称
    var hid_staffName = $(".ttsw_emp_name").val();
    $("#ttsw_hid_staffName").val(hid_staffName);
    //负责人编号
    var hid_staffCode = $(".ttsw_emp_code").val();
    $("#ttsw_hid_staffCode").val(hid_staffCode);
    //制单人名称
    var hid_singleName = $("#ttsw_singleName").val();
    if(hid_singleName == null || hid_singleName == "" ){
        alert("请填写制单人");
        return false;
    }
    $("#ttsw_hid_singleName").val(hid_singleName);
    var address = $("#address").val();
    var coordinate = $("#coordinate").val();
    var billsType = $("#billsType").val();
    $("#ttsw_hid_billsType").val(billsType);
    $("#ttsw_hid_coordinate").val(coordinate);
    $("#ttsw_hid_address").val(address);
    var hid_hyfl = $("#hyfl").val();
    var hid_hyflId = $("#hyflId").val();
    if(hid_hyflId == null || hid_hyflId == "" ){
        alert("请选择行业分类");
        return false;
    }
    $("#ttsw_hid_tradeId").val(hid_hyflId);
    $("#ttsw_hid_tradeName").val(hid_hyfl);

    //条形码信息
    // var member=eval("("+tiaoma+")");

    //条形码
    // var barcode = tiaoma;
    // var barcode = "01334";//测试用
    //新增页面不传条码值
    // $("#ttsw_hid_barcode").val(barcode);
    console.log("条码值："+$("#ttsw_hid_barcode").val());
    //公司id
    //treeid = "company20180510CQZCDKTT690000006064";//测试用
    $("#ttsw_hid_companyId").val(treeid);
    // var url = "ea_toAddCostBudgetItem.jspa?commitFlag=back";
    var cashierBillsId = $("#cashierBillsId").val();
    var url = "ea_toAddCostBudgetItem.jspa?editFlag=edit&cashierBillsId="+cashierBillsId+"&billsType="+billsType;
    $("#f1").attr('action',url);    //通过jquery为action属性赋值
    $("#f1").submit();    //提交ID为myform的表单
}

function toDelItem(){
    //循环获取选中的值
    var id = "";
    $(".aside_yes").each(function (){
        id=$(this).attr("checkCasId");
    });
}

/**
 * 初始化项目信息
 * @param num
 */
function initItemInfo() {
    pagenumber = 1;
    var search = $('#ttsw_item_search_id').val();//取录入的模糊查询条件
    var url = basePath + "ea/scBudget/sajax_ea_ajaxProjectList.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data: {
            "search": search,
        },
        dataType: "json",
        success: function cbf(data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var oList = member.proList;
            //拼接html页面
            var innerHtml = '<tr>'
                + '<th colspan="2"></th>'
                + '<th>项目名称</th>'
                + '<th>项目分类</th>'
                + '</tr>';
            if (oList != null) {
                pagecount = oList.pageCount;
                count = oList.recordCount;
                pageSize = oList.pageSize;
                var pageNum = oList.list.length - 1;
                getHeight();//启动定时器刷新下拉距离刷新用
                innerHtml += itemHtml(oList, pageNum);
            } else {
                innerHtml += nullHtml();
            }
            $('#ttsw_item_id').html(innerHtml);
            //初始化显示页面
            $(".div-name").show();
            $("body").addClass("body_yc");
        }, error: function cbf(data) {
            alert("数据获取失败！");
        }
    });
}

/**
 * 根据数据拼接项目页面
 * @param oList 异步获取信息
 * @param pageNum 总数据数-1
 * @returns {string} 返回拼接好的td 页面
 */
function itemHtml(oList, pageNum) {
    var innerHtml = '';
    for (var i = 0; i < oList.list.length; i++) {
        obj = oList.list[i];
        if (obj.xmtypename != null && obj.xmtypename != "") {
            innerHtml += (i + 1) != pageNum ? '<tr class="ttsw_last">' : '<tr>';
            innerHtml += '<td colspan="2" id="' + obj.ppID + '" cusXmtype="' + obj.xmtype + '" cusXmcode="' + obj.goodsNum + '">'
                + '<aside class="aside_no">'
                + '<img class="img_no" src="' + basePath + 'images/scMobile/payBudget/addBudget/wupinguanli_07.png"/>'
                + '<img class="img_yes" src="' + basePath + 'images/scMobile/payBudget/addBudget/wupinguanli_10.png"/>'
                + '</aside>'
                + '</td>'
                + '<td>' + obj.goodsName + '</td>'
                + '<td>' + (obj.xmtypename != null ? obj.xmtypename : "") + '</td>'
                + '</tr>';
        }
    }
    return innerHtml;
}

/**
 * 无数据拼接页面
 */
function nullHtml() {
    var innerHtml = '<tr><td colspan="4">未查询到数据</td></tr>';
    return innerHtml;
}

/**
 * 定时刷新判断页面距底高度
 */
function getHeight() {
    clearTimeout(timer);//清空定时器
    timer = setTimeout("getHeight()", 1000);
    if ($(".ttsw_last").length > 0) {
        //li偏移量-li的3倍高度<=页面高度时
        if ($(".ttsw_last").offset().top - $(".ttsw_last").height() * 3 <= $(window).height()) {
            if (pagenumber < pagecount) {
                ajaxItemInfor();
            }
        }
    }
}


/**
 * 异步获取加载数据
 */
function ajaxItemInfor() {
    pagenumber += 1;
    var search = $('#ttsw_item_search_id').val();//取录入的模糊查询条件
    var url = basePath + "ea/scBudget/sajax_ea_ajaxProjectList.jspa";
    $.ajax({
        url: url,
        type: "POST",
        async: true,//默认设置为true，所有请求均为异步请求
        data: {
            "pageForm.pageNumber": pagenumber,
            "search": search,
        },
        dataType: "json",
        success: function (data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var oList = member.proList;
            $(".ttsw_last").removeClass("ttsw_last");//清空样式
            var innerHtml = '';
            if (oList != null) {
                pagecount = oList.pageCount;
                count = oList.recordCount;
                pageSize = oList.pageSize;
                var pageNum = oList.list.length - 1;
                $(".ttsw_last").removeClass("ttsw_last");
                innerHtml += itemHtml(oList, pageNum);
            } else {
                innerHtml += nullHtml();
            }
            $('#ttsw_item_id').append(innerHtml);
        },
        error: function (data) {
            alert("加载下一页失败");
        }
    });
}

//将选中的项目在父级页面呈现
$("#div_table").height(($(".div-name").height() - $(".header").outerHeight(true) - $(".button").outerHeight(true)) * 0.835 + "px");
$("#p_add").click(function () {
    clearTimeout(timer);//暂时定时器
    timer = null;
    var ppid = $("#div_table").find(".active").children("td:nth-of-type(1)").attr("id");
    var xmtype = $("#div_table").find(".active").children("td:nth-of-type(1)").attr("cusXmtype");
    var xmcode = $("#div_table").find(".active").children("td:nth-of-type(1)").attr("cusXmcode");
    $("#ttsw_item_check_id").val(ppid);//所选项目id
    $("#ttsw_item_check_type").val(xmtype);//所选项目类型
    $("#ttsw_item_check_code").val(xmcode);//所选项目编号
    $("#div_table").find(".active").children("td:nth-of-type(2)").text();
    // $("#project-name").val($("#div_table").find(".active").children("td:nth-of-type(2)").text());
    // $("#project-fl").val($("#div_table").find(".active").children("td:nth-of-type(3)").text());
    $(this).parents(".div-name").hide();
    $("body").removeClass("body_yc");
});

/**
 * 移除扫描到的盘库数据
 * @param mapKey map的key值
 */
function toRemove(mapKey) {
    var url = basePath + "ea/scBudget/sajax_ea_ajaxDelMapForKey.jspa";
    $.ajax({
        url: url,
        type: "POST",
        async: true,//默认设置为true，所有请求均为异步请求
        data: {
            "mapKey": mapKey
        },
        dataType: "json",
        success: function (data) {
            $("#" + mapKey).remove();
            countPrice();//计算商品价格
        },
        error: function (data) {
            alert("加载下一页失败");
        }
    });
}

$(document).ready(function () {
    if(billsType != "初始项目单"){
        $("#billsTypeSection").html("<div class='clearfix'>" +
            "                <p>" +
            "                    <label for=''>单据类别：</label>" +
            "                    <input type='text' id='billsType' name='cashierBills.billsType' value='"+ billsType + "'/>" +
            "                </p>" +
            "            </div>");
    }
    if(billsType == "入库单" || billsType == "出库单" || billsType == "盘库单" || billsType == "报损单"){
        $("#inv").show();
    }else{
        $("#inv").hide();
    }
    delGoodsBills();//加载完页面删除跳转前删除的数据
    countPrice();//计算商品价格
});

/**
 * 计算金额
 */
function countPrice() {
    var vals = 0;//总金额
    var num = 0;//商品数量
    $(".ttsw_jj").each(function () {
        num++;
        console.log(vals+";"+$(this).val());
        console.log(vals+";"+parseFloat(isNumber($(this).val()) ? $(this).val() : 0));
        vals = parseFloat(vals) + (parseFloat(isNumber($(this).val()) ? $(this).val() : 0));
console.log(vals);
    });
    $("#ttsw_allPrice").text(vals);
    $("#ttsw_num_pro").text(num);
    $("#allPrice").val(vals);
}

function isNumber(str) {
    return /^[+-]?[0-9]*(\.[0-9]+)?$/.test(str);
}

/**
 * 加载完页面删除跳转前删除的数据
 */
function delGoodsBills() {
    var delGoodsBillsIds = $("#ttsw_hid_delGoods").val();
    if (delGoodsBillsIds != null && delGoodsBillsIds != "") {
        var attr = delGoodsBillsIds.split(',');
        for (var i = 0; i < attr.length; i++) {
            $("#" + attr[i]).remove();
        }
    }
}


//商品点击选中
$(document).on("click","#sec-checked",function(){
    if($(this).find("aside").is(".aside_yes")){
        $(this).find("aside").removeClass().addClass("aside_no");
    }else{
        $(this).find("aside").removeClass().addClass("aside_yes");
    }
})

//项目分类、行业类别数据查询
function getProductTypeNew(pid, pvalue, ti, type) {
    var getcodeurl = basePath + "ea/scBudget/sajax_ea_getBusinessTypeByPID.jspa";
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: true,
        dataType: "json",
        data: {
            codeID: pid
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var oList = member.codeList;
            var listhtml = "";
            var tii = ti + 1;
            var claname = "";
            var $divHtml;
            var str = [];
            if (null == oList || oList.length <= 0) {
                if (type == 1 && ti != 5) {
                    $(".xiangmu2 #producttype").val($("#" + pid).find(".codeValue").text());
                    $(".xiangmu2 #productCode").val($("#" + pid).find(".codeSn").text());
                    $(".div-xiangmu").hide();
                    $(".xiangmu1").hide();
                    $(".xiangmu2").show();
                }
                return;
            }
            if (type == 1) {
                claname = "xmfl-right";
                $divHtml = $(".xmfl");
            } else if (type == 2) {
                claname = "hyfl-right";
                $divHtml = $(".hyfl");
            } else if (type == 3) {
                claname = "wplb-right";
                $divHtml = $(".wplb");
            }

            if (ti == 1) {
                str.push("<ul class='ul-left'>");
            } else if (ti == 2) {
                str.push("<div class='div-right " + claname + "'> <ul class='ul-02'>");
            } else if (ti == 3) {
                str.push("<ul class='clearfix ul-03'>");
            } else if (ti == 4) {
                str.push("<ul class='clearfix ul-04'>");
            }
            if (pvalue != null && pvalue != "") {
                pvalue = pvalue + "\>";
            }else{
                pvalue="";
            }
            for (var i = 0; i < oList.length; i++) {
                str.push("<li id='" + oList[i].typeId + "' onclick='getProductTypeNew(" + "\"" + oList[i].typeId + "\",\"" + pvalue + oList[i].typeNum + oList[i].typeName + "\"," + tii + "," + type + ")'>");
                str.push("<span class='codeValue'>" + oList[i].typeName + "</span>");
                str.push("<span class='codeSn' style='display: none'>" + oList[i].typeShowNum + "</span>");
                str.push("<span class='pvalue' style='display: none'>" + pvalue + oList[i].codeSn + oList[i].typeName + "</span>");
                str.push("</li>");
                //(" + oList[i].codeSn + ")
            }
            if (ti == 1) {
                str.push("</ul>");
                $divHtml.append(str.join(""));
            } else if (ti == 2) {
                str.push("</ul></div>");
                $divHtml.append(str.join(""));
            } else if (ti == 3 || ti == 4) {
                str.push("</ul>");
                $("." + claname + "").append(str.join(""));
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

// 行业分类
// 	显示
$(document).on("click", "#hyfl", function () {
    $(".hyfl").empty();
    getProductTypeNew("", "", 1, 2);
    $(".div-hangye").show();
});
// 	关闭
$(document).on("click", ".div-hangye .div-close", function () {
    $(".div-hangye").hide();
    $(".hyfl").empty();
});
// 	切换选项
$(document).on("click", ".div-hangye .ul-left li", function () {
    $(".div-right").remove();
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul1");
});
// 	缩进
$(document).on("click", ".div-hangye .div-right .ul-02 li", function () {
    $(".div-hangye .div-right .ul-03").remove();
    $(".div-hangye .div-right .ul-04").remove();
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul2");
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
});
$(document).on("click", ".div-hangye .div-right .ul-03 li", function () {
    $(".div-hangye .div-right .ul-04").remove();
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul3");
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
});
// 	传入选项 更改显示模块
$(document).on("click", ".div-hangye .div-right .ul-04 li", function () {
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
    $("#hyfl").val($(this).find(".codeValue").text());
    $("#hyflId").val($(this).attr("id"));
    $(".div-hangye").hide();
    $(".hangye1").hide();
    $(".hangye2").show();
    $(".hyfl").empty();
});

// 项目分类
// 	显示
$(document).on("click", "#xiangmu", function () {
    $(".xmfl").empty();
    getProductTypeNew("", "", 1, 1);
    $(".div-xiangmu").show();
});
// 	关闭
$(document).on("click", ".div-xiangmu .div-close", function () {
    $(".div-xiangmu").hide();
    $(".xmfl").empty();
});
// 	切换选项
$(document).on("click", ".div-xiangmu .ul-left li", function () {
    $(".div-right").remove();
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul1");
});
// 	缩进
$(document).on("click", ".div-xiangmu .div-right .ul-02 li", function () {
    $(".div-xiangmu .div-right .ul-03").remove();
    $(".div-xiangmu .div-right .ul-04").remove();
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul2");
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
});
$(document).on("click", ".div-xiangmu .div-right .ul-03 li", function () {
    $(".div-xiangmu .div-right .ul-04").remove();
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul3");
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
});
$(document).on("click", ".div-xiangmu .div-right .ul-04 li", function () {
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
    $("#xiangmu").val($(this).find(".codeValue").text());
    $("#xiangmuId").val($(this).attr("id"));
    $(".div-xiangmu").hide();
    $(".xiangmu1").hide();
    $(".xiangmu2").show();
    $(".xmfl").empty();
});