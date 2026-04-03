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
function calltiaoma(tiaoma){
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


    //盘库仓库
    var hid_depotName = $("#ttsw_depotName").val();
    if(hid_depotName == null || hid_depotName == "" ){
        alert("请录入盘库仓库");
        return false;
    }
    $("#ttsw_hid_depotName").val(hid_depotName);

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


    //条形码信息
    var member=eval("("+tiaoma+")");
    //条形码
    var barcode = member.code;
     var barcode = "6922454311287";//测试用
    $("#ttsw_hid_barcode").val(barcode);
    //公司id
    var treeid = "company201009046vxdyzy4wg0000000025";//测试用
    $("#ttsw_hid_companyId").val(treeid);
    ajaxGetGoodsHtml();
}

/**
 * 接收IOS端扫码返回值
 * @param tiaoma
 */
function calltiaomaIOS(tiaoma){
    // alert("IOS:"+tiaoma);
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


    //盘库仓库
    var hid_depotName = $("#ttsw_depotName").val();
    if(hid_depotName == null || hid_depotName == "" ){
        alert("请录入盘库仓库");
        return false;
    }
    $("#ttsw_hid_depotName").val(hid_depotName);

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
    var hid_depotNum=$("#hid_depotNum").val();
    $("#ttsw_hid_depotNum").val(hid_depotNum);
    //条形码信息
    var member=eval("("+tiaoma+")");
    //条形码
    var barcode = member.code;
    var barcode = "6922454311287";//测试用
    $("#ttsw_hid_barcode").val(barcode);
    //公司id
   var treeid = "company201009046vxdyzy4wg0000000025";//测试用
    $("#ttsw_hid_companyId").val(treeid);
    ajaxGetGoodsHtml();
}

/**
 * 异步根据隐藏域中的值获取盘库商品信息
 */
function ajaxGetGoodsHtml(){
    var formData = $("#f1").serialize();    //提交ID为myform的表单
    var url = basePath+"ea/cashinv/sajax_ea_scanningInventoryInfo.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data : formData,
        dataType: "json",
        success: function cbf(data){
            var member = (new Function("return " + data))();//格式化返回参数
           // console.log(member);
            if (member!=null){
                loadTable(member)
                $("#ttsw_hid_barcode").val(null);
                window.location.reload();
            }else{
                window.location.href = basePath + "/page/pkModule/checkinv/pkinventory/addInventory.jsp";
            }
        },error: function cbf(data){
            alert("数据获取失败！");
        }
    });
}

function loadTable(data) {
    console.log(data)
    //把section中原有的数据获取出来（审核，添加。。。）
    var table ;
    if (rowNum == 0){
        table = '<tr>'+
            '<td type="text">'+
            '物品名称'+
            '</td>'+
            '<td>'+
            '单价'+
            '</td>'+
            '<td>'+
            '库存数'+
            '</td>'+
            '<td>'+
            '盘库数量'+
            '</td>'+
            '<td>'+
            '误差'+
            '</td>'+
            '</tr>'
    }
     table += '<tr>\n' +
            '<td  type="text">\n' +
            data.goodsName +
            '</td>\n' +
            '<td class="ttsw_jj" >\n' +
            data.unitPrice +
            '</td>\n' +
            '<td  class="dnum">\n' +
            data.invenQuantity +
            '</td>\n' +
            '<td>\n' +
            '<input type="text"  value="0"  id="ttsw_depotNum"   name="cashierBills.depotNum"  onkeyup="clearNoNum(this)"  maxlength="5" size="40"/><input hidden class="goodPrice"  value="0" >\n' +
            '</td>\n' +
            '<td class="wucha"></td>\n' +
            '</tr>'
    $("#goodsL").append(table);
    //计算数量
    var num = $("#ttsw_num_pro").text();
    var number = parseInt(num);
    number = number + 1;
    $("#ttsw_num_pro").text(number);;
    rowNum=rowNum+1;
}

/**
 *IOS终端调用扫码方法
 */
// function callIOScamera(){
//     var url= "func=" + 'callioscamera';
//     window.webkit.messageHandlers.Native.postMessage(url);
// }