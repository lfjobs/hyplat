
$(function () {
    //取消
    $(".div-del .p-c").click(function () {

        $(".div-del").hide();

    })
    $.date('#date3');
    $(".li-fs").click(function(){
        if($("#contactor").val()==""){
            $(".div-del").show();
            $(".titlep").text("面试联系人不能为空");
            return false;
        }
        if($("#contactTel").val()==""){
            $(".div-del").show();
            $(".titlep").text("面试联系人电话不能为空");
            return false;
        }
        if(!$(".sec-remarks ").is(":hidden")) {
            if ($("#riId").val() == "") {
                $(".div-del").show();
                $(".titlep").text("请选择招聘职位");
                return false;
            }
        }

            if ($("#interviewPlace").val() == "") {
                $(".div-del").show();
                $(".titlep").text("请填写面试详细地址");
                return false;
            }

        if ($("#interviewDate").val() == "") {
            $(".div-del").show();
            $(".titlep").text("请选择面试时间");
            return false;
        }


        $(".div-msyq").show();
    })
    $(".div-yq p").click(function(){
        $(this).parents(".div-msyq").hide();
    })


   //抢人才邀约需要选择招聘职位
   $(".zpzw").click(function () {

          $(".sec-con").show();
   })
    //点击选择招聘职位
    $(document).on("click", ".ul2 li", function () {
        var riId = $(this).attr("id");
        var jobName = $.trim($(this).text());
        $("#mainForm #riId").val(riId);
        $("#mainForm .zw").text(jobName);
        $(".sec-con").hide();

    });

    //取消
    $(".div-yq .p-c").eq(0).click(function () {

        $(this).parents(".div-msyq").hide();

    })

    //确定按钮
    $(".div-yq .p-q").click(function () {

        $("#mainForm").attr("target", "hidden").attr("action",
            basePath + "ea/bidrecruit/ea_sendInvent.jspa");

        document.mainForm.submit.click();
        token = 13;
    })



});
function re_load() {
    window.history.go(-1);
    return false;
  //  document.location.href = basePath+"ea/bidrecruit/ea_getTalentResumeList.jspa?sccId="+sccId+"&back="+back;

}
function backs(){
    window.history.go(-1);
    return false;

    //   document.location.href = basePath+"ea/bidrecruit/ea_resumedetail.jspa?sccId="+sccId+"&resumeID="+resumeID+"&tpId="+tpId+"&back="+back;
}

