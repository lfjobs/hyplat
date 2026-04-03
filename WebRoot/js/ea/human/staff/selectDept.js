//部门数据（列表数据）
let organizationData = {};
// 选择的部门id ,选择部门名称
let selectedOrganizationID = "", selectedOrganizationName = "";
// 是否点击下级按钮
let selectChildBool = false;
// 父级id列表（用于选择下级）   copySelectedData用于查看选择的部门（用于移除）
let parentIdList = [""],copySelectedData = [];
// 部门详情数据   leveloneOrgID一级部门
let dataList = {}
$(function () {
    initCss();
    bindEvents();
    getDeptList("","");
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    if ("checkbox" == type){
        $(".div-bottom").show();
        $("#deptBox").height($(".content").height() - $(".select-dept-data").height() - $(".div-bottom").height() - 20);
    } else {
        $("#deptBox").height($(".content").height() - $(".select-dept-data").height()  - 20);
    }
    $(".dept-data").width( $(".content").width() - $(".person-submit").width());
}

/**
 * 点击事件
 */
function bindEvents() {
    // 查看已选部门数据
    $(".dept-data").click(function(){
        $("#mask").fadeIn();
        $("#dataLayer").animate({"bottom": 0});
        getSelectedData(selectedData)
        copySelectedData = selectedData.concat();
    })
    // 确认已选部门数据
    $(".submit-mask").click(function(){
        $("#mask").fadeOut();
        $("#dataLayer").animate({"bottom": "-100%"});
        selectedData = copySelectedData.concat();
        deptDataListStr(organizationData[selectedOrganizationID]);
    })
}

/**
 * 删除部门
 * @param organizationID
 */
function deleteDept(organizationID){
    copySelectedData = copySelectedData.filter(item => item !== organizationID);
    $(".dept-" + organizationID).remove();
}

/**
 * 查询部门信息
 */
function getDeptList(organizationPID,organizationName){
    selectedOrganizationID = organizationPID;
    selectedOrganizationName = organizationName;
    if (organizationData[organizationPID] != undefined){
        deptDataListStr(organizationData[organizationPID]);
        return;
    }
    const url = basePath + "ea/menu/sajax_ea_getDeptListByDeptPID.jspa?organizationPID=" + organizationPID;
    $.ajax({
        url : encodeURI(url),
        type : "get",
        async : true,
        dataType : "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            organizationData[organizationPID] = codeList;
            deptDataListStr(codeList);
        },
        error : function cbf(data) {
            alert("数据获取失败！")
        }
    });
}

/**
 * 查看已选择的部门数据
 * @param data
 */
function getSelectedData(data){
    const htmlstr = new Array();
    const length = data.length;
    for (let i = 0; i < length; i++){
        let organizationID = selectedData[i];
        let data = dataList[organizationID];
        htmlstr.push("<li class='dept-" + organizationID + "'>");
        htmlstr.push("<div style='width:80%'>");
        htmlstr.push("<label>" + data["organizationName"] + "</label>");
        htmlstr.push("</div>");
        htmlstr.push("<button class=\"layui-btn layui-btn-primary layui-btn-xs\" style='font-weight:550;height:26px'onclick=deleteDept('" + organizationID + "')>移除</button>")
        htmlstr.push("<div>");
        htmlstr.push("</div>");
        htmlstr.push("</li>");
    }
    $("#selectedDataList").html(htmlstr.join(""));
}

/**
 * 根据数据获取部门列表
 * @param codeList
 */
function deptDataListStr(codeList){
    if (codeList.length == 0){
        layer.msg("无下级选项");
    } else {
        if(selectChildBool){
            parentIdList.push(selectedOrganizationID);
        }
        fillDeptNameData();
        const htmlstr = new Array();
        let organizationID = "", organizationName = "";
        for (let i = 0; i < codeList.length; i++) {
            organizationID = codeList[i].organizationID;
            organizationName = codeList[i].organizationName;
            htmlstr.push("<li>")
            htmlstr.push("<div style='width:80%;padding-left:10px' id=\"dept-" + organizationID +
                "\" onclick=selectData('" + organizationID + "','" + organizationName +"')>");
            if ("checkbox" == type){
                htmlstr.push("<div style='width:18px;float:left'>");
                const selected = selectedData.includes(organizationID);
                if (selected){
                    htmlstr.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:none' >&#xe63f;</i> ")
                    htmlstr.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:block' >&#x1005;</i> ")
                } else {
                    htmlstr.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:block' >&#xe63f;</i> ")
                    htmlstr.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:none' >&#x1005;</i> ")
                }
                htmlstr.push("</div>");
            }

            htmlstr.push("<label style='float:left;margin-left:20px'>" + organizationName + "</label></div>");
            htmlstr.push("<div class='selected-child'  id='icon-" + organizationID + "'" + "onclick=getChildDeptList('" + organizationID + "','" + organizationName + "',false)>");
            htmlstr.push("<i class=\"layui-icon\" >&#xe649;</i>");
            htmlstr.push("<label class='label-child-name'>下级</label>");
            htmlstr.push("</div>");
            htmlstr.push("</li>");
            dataList[organizationID] = codeList[i];
        }
        $("#deptList").html(htmlstr.join(""));
        $(".div-dept-data").html(selectedData.length + "/100");
        $(".person-num").html(selectedData.length + "部门（含子部门）");
    }
}

/**
 * 填充部门路径数据
 */
function fillDeptNameData(){
    if (selectChildBool){
        if (parentIdList.length > 1){
            $(".dept-name").html($(".dept-name").html() + ">" + selectedOrganizationName);
        } else {
            $(".dept-name").html($(".dept-name").html() + selectedOrganizationName);
        }
        if (selectedOrganizationID != ""){
            if (!parentIdList.includes(selectedOrganizationID)){
                parentIdList.push(selectedOrganizationID);
            }
        }
    } else {
        if (parentIdList.length > 1){
            const position = $(".dept-name").html().lastIndexOf("&gt;");
            $(".dept-name").html($(".dept-name").html().substring(0,position));
        } else {
            $(".dept-name").html("");
        }
        if (selectedOrganizationID != ""){
            parentIdList.pop();
        }

    }
}

/**
 * 选择下级部门
 * @param deptId
 * @param organizationName
 */
function getChildDeptList(organizationId,organizationName){
    selectChildBool = true;
    getDeptList(organizationId,organizationName);


}

/**
 * 关闭查看已选部门数据
 */
function closeMask() {
    $("#mask").fadeOut();
    $("#dataLayer").animate({"bottom": "-100%"});
}
/**
 * 返回选择菜单
 */
function deptBack(){
    selectChildBool = false;
    parentIdList.pop();
    const length = parentIdList.length;
    if (length > 0){
        getDeptList(parentIdList[length - 1],"");
    } else {
        parent.layer.close(parent.layer.index);

    }
}
/**
 * 勾选数据
 * @param deptId
 */
function selectData(organizationId,organizationName){
    if ("checkbox" == type){
        if (selectedOrganizationID != ""){
            const selectedPId = selectedData.includes(selectedOrganizationID);
            if (!selectedPId){
                selectedData.push(selectedOrganizationID);
            }
        }
        const selected = selectedData.includes(organizationId);
        if (selected){
            $("#dept-" + organizationId +" .div-selected").hide();
            $("#dept-" + organizationId +" .div-not-selected").show();
            selectedData = selectedData.filter(item => item !== organizationId);
        } else {
            $("#dept-" + organizationId +" .div-selected").show();
            $("#dept-" + organizationId +" .div-not-selected").hide();
            selectedData.push(organizationId);
        }
        $(".div-dept-data").html(selectedData.length + "/100");
        $(".person-num").html(selectedData.length + "部门（含子部门）");
    } else {
        let length = parentIdList.length;
        let deptId="",deptName = "";
        for (let i = 0; i < length; i++){
            if (parentIdList[i] != ""){
                deptId += "," + parentIdList[i];
                deptName += "-" + dataList[parentIdList[i]]["organizationName"];
            }
        }
        deptId += "," + organizationId;
        deptName += "-" + organizationName;
        if (deptId != ""){
            deptId = deptId.substring(1);
            deptName = deptName.substring(1);
        }
        parent.callbackDept(deptId,deptName);
        parent.layer.close(parent.layer.index);
    }

}

/**
 * 多选提交数据
 */
function submitData(){
    let length = selectedData.length;
    let deptId="",deptName = "";
    for (let i = 0; i < length; i++){
        if (selectedData[i] != ""){
            deptId += "," + selectedData[i];
            deptName += "," + dataList[selectedData[i]]["organizationName"];
        }
    }
    if (deptId != ""){
        deptId = deptId.substring(1);
        deptName = deptName.substring(1);
    }
    parent.callbackDept(deptId,deptName);
    parent.layer.close(parent.layer.index);
}