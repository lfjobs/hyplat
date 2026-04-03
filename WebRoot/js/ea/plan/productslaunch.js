/**
 * 初始化判断是安卓/IOS终端
 */
$(function(){
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if(isiOS==true){
        var obj_sao=document.getElementById("ttsw_smq_id");
        obj_sao.setAttribute("onclick", "callIOScamera()");
    }
});

/**
 * 接收安卓端扫码返回值
 * @param tiaoma 条码
 */
function calltiaomaBack(tiaoma){
    //是否是选择全部false全部true单一部门
    // $("#ttsw_hid_showFlag").val(showFlag);
    //所选部门id名称
    var hid_departmentID,hid_departmentName;
    if(showFlag == true){
        hid_departmentID = $("#ttsw_dep_y_id").val();
        hid_departmentName = $("#ttsw_dep_y_name").val();
    }else{
        hid_departmentID = $("#ttsw_dep_n_id").val();
        hid_departmentName = $("#ttsw_dep_n_name").val();
    }
    if(tenantFlag!="personal" && (hid_departmentID == null || hid_departmentID == "" || hid_departmentName == null || hid_departmentName == "" ) ){
        alert("请选择部门");
        return false;
    }
    $("#ttsw_hid_departmentID").val(hid_departmentID);
    $("#ttsw_hid_departmentName").val(hid_departmentName);

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
    var hid_itemName = $("#project-name").val();
    if(hid_itemName == null || hid_itemName == "" ){
        alert("请选择项目名称");
        return false;
    }
    $("#ttsw_hid_itemName").val(hid_itemName);
    //项目分类
    var hid_itemType = $("#project-fl").val();
    if(hid_itemType == null || hid_itemType == "" ){
        alert("请选择项目分类");
        return false;
    }
    $("#ttsw_hid_itemType").val(hid_itemType);
    //项目类型
    var xmType = $("#ttsw_item_check_type").val();
    $("#ttsw_hid_xmType").val(xmType);
    //项目编号
    var itemCode = $("#ttsw_item_check_code").val();
    $("#ttsw_hid_itemCode").val(itemCode);
    //项目id
    var hid_itemId = $("#ttsw_item_check_id").val();
    $("#ttsw_hid_itemId").val(hid_itemId);
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

    //条形码信息
    // var member=eval("("+tiaoma+")");

    //条形码
    // var barcode = tiaoma;
    // var barcode = "01334";//测试用

    var member=eval("("+tiaoma+")");
    //条形码
    var barcode = member.code;
    $("#ttsw_hid_barcode").val(barcode);
    //公司id
    //treeid = "company20180510CQZCDKTT690000006064";//测试用
    $("#ttsw_hid_companyId").val(treeid);
    var url = "ea_scanningPlanCostBudgetInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId;
    $("#f1").attr('action',url);    //通过jquery为action属性赋值
    $("#f1").submit();    //提交ID为myform的表单
}

function calltiaoma(tiaoma){
    // $("#ttsw_hid_singleName").val(hid_singleName);
    // var address = $("#address").val();
    // var coordinate = $("#coordinate").val();
    // var billsType = $("#billsType").val();
    // $("#ttsw_hid_billsType").val(billsType);
    // $("#ttsw_hid_coordinate").val(coordinate);
    // $("#ttsw_hid_address").val(address);

    //条形码信息
    var member=eval("("+tiaoma+")");
    //条形码
    var barcode = member.code;
    // var barcode = "01334";//测试用
    $("#ttsw_hid_barcode").val(barcode);

    var wupinType = $("#wupinType").val();
    var wupinTypeId = $("#wupinTypeId").val();
    var billsType = $("#billsType").val();
    if(!wupinTypeId){
        alert("请选择物品分类");
        return false;
    }
    $("#ttsw_hid_goodsPurpose").val(wupinType);
    $("#ttsw_hid_goodsPurposeId").val(wupinTypeId);
    $("#ttsw_hid_billsType").val(billsType);
    // console.log("条码值："+$("#ttsw_hid_barcode").val());
    //公司id
    //treeid = "company20180510CQZCDKTT690000006064";//测试用
    // $("#ttsw_hid_companyId").val(treeid);
    var url = "ea_scanningPlanCostBudgetInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId;
    $("#f1").attr('action',url);    //通过jquery为action属性赋值
    $("#f1").submit();    //提交ID为myform的表单
}

/**
 * 接收IOS端扫码返回值
 * @param tiaoma
 */
function calltiaomaIOS(tiaoma){
    alert("IOS:"+tiaoma);
    //是否是选择全部false全部true单一部门
    $("#ttsw_hid_showFlag").val(showFlag);
    //所选部门id名称
    var hid_departmentID,hid_departmentName;
    if(showFlag == true){
        hid_departmentID = $("#ttsw_dep_y_id").val();
        hid_departmentName = $("#ttsw_dep_y_name").val();
    }else{
        hid_departmentID = $("#ttsw_dep_n_id").val();
        hid_departmentName = $("#ttsw_dep_n_name").val();
    }
    if(hid_departmentID == null || hid_departmentID == "" || hid_departmentName == null || hid_departmentName == ""  ){
        alert("请选择创收部门");
        return false;
    }
    $("#ttsw_hid_departmentID").val(hid_departmentID);
    $("#ttsw_hid_departmentName").val(hid_departmentName);
    //单据凭证号
    var hid_billId = $("#ttsw_billID").val();
    if(hid_billId == null || hid_billId == "" ){
        alert("请录入单据凭证号");
        return false;
    }
    $("#ttsw_hid_billId").val(hid_billId);
    //公司名称
    var hid_companyName = $("#ttsw_companyNmae").val();
    if(hid_companyName == null || hid_companyName == "" ){
        alert("请录入公司名称");
        return false;
    }
    $("#ttsw_hid_companyName").val(hid_companyName);
    //项目名称
    var hid_itemName = $("#project-name").val();
    if(hid_itemName == null || hid_itemName == "" ){
        alert("请选择项目名称");
        return false;
    }
    $("#ttsw_hid_itemName").val(hid_itemName);
    //项目分类
    var hid_itemType = $("#project-fl").val();
    if(hid_itemType == null || hid_itemType == "" ){
        alert("请选择项目分类");
        return false;
    }
    $("#ttsw_hid_itemType").val(hid_itemType);
    //项目类型
    var xmType = $("#ttsw_item_check_type").val();
    $("#ttsw_hid_xmType").val(xmType);
    //项目编号
    var itemCode = $("#ttsw_item_check_code").val();
    $("#ttsw_hid_itemCode").val(itemCode);
    //项目id
    var hid_itemId = $("#ttsw_item_check_id").val();
    $("#ttsw_hid_itemId").val(hid_itemId);
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
    //条形码信息
    var member=eval("("+tiaoma+")");
    //条形码
    var barcode = member.code;
    //var barcode = "6922255451427";//测试用
    $("#ttsw_hid_barcode").val(barcode);
    //公司id
    //treeid = "company20180510CQZCDKTT690000006064";//测试用
    $("#ttsw_hid_companyId").val(treeid);
    var url = "ea_scanningPlanCostBudgetInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId;
    $("#f1").attr('action',url);    //通过jquery为action属性赋值
    $("#f1").submit();    //提交ID为myform的表单
}

/**
 *IOS终端调用扫码方法
 */
function callIOScamera(){
    var url= "func=" + 'callioscamera';
    window.webkit.messageHandlers.Native.postMessage(url);
}