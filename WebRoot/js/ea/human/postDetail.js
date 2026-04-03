let choose ="",saveBool = false,levelExamine = false;
$(function () {
    initCss();
    bindEvents();
    initData();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".div-form").height($(".content").height() - $(".div-bottom").height() - 20);
}

function initData(){

}

/**
 * 点击事件
 */
function bindEvents() {
      $(".dept").click(function () {
          if ("edit" === type){
              return false;
          }
          // 选择部门
          const param = new Array();
          const url = basePath + "page/WFJClient/pc/5l5C/human/staff/selectDept.jsp?" + param.join("");
          layer.open({
              type: 2,
              title:false,
              closeBtn:false,
              anim: 5,
              isOutAnim: false,
              content: url,
              area: ['100%', '100%'],
          });
      })

}
function callbackDept(deptId,deptName){
    const idList = deptId.split(",");
    const nameList = deptName.split("-");
    const size = idList.length;
    $("#organizationName").val(nameList[size-1]);
    $("#organizationID").val(idList[size-1]);
    $("#leveloneOrgID").val(idList[0]);

}

function checkData(){
    let fieldArray = ["postName","organizationName","postResponsibility","responsibilityRequire"];
    let fieldName = ["岗位名称","所属部门","岗位职责","任职要求"];
    let length = fieldArray.length;
    for (let i = 0; i < length; i++){
        const field = fieldArray[i];
        value = $("#" + field).val().trim();
        if (value == ""){
            layer.msg("请填写"+ fieldName[i]);
            return false;
        }
    }
    return true;
}
/**
 * 保存数据
 */
function save(){
    if (!checkData()){
        return false;
    }
    let url = "ea/post/sajax_ea_saveDepartmentPost.jspa?orgName=" + $("#organizationName").val();
    const formData = new FormData($("#form")[0]);
    if (saveBool){
        layer.msg("请勿重复提交");
        return false;
    }
    saveBool = true;
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: formData,
        dataType:"json",
        async : true,
        processData : false,
        contentType : false,
        success: function (data) {
            saveBool = false;
            if (data === "success") {
                layer.msg("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/human/postManage.jsp?";

            } else  {
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}
