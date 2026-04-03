
$(function () {



    //修改
    $(document).on("click", ".p-edit", function (e) {
        e.preventDefault();
        e.stopPropagation();

        document.location.href = basePath+"ea/bidrecruit/ea_getPositionPub.jspa?sccId="+sccId+"&recruitInfo.riId="+riId;

    })

    //删除弹框
    $(document).on("click", ".p-del", function (e) {
        e.preventDefault();
        e.stopPropagation();
        cz = 0;
        $(".div-del").show();
        $(".titlep").text("确定要删除吗?");
    })
    // 上线下线
    $(document).on("click", ".p-onoff", function (e) {
        e.preventDefault();
        e.stopPropagation();
        cz = 1;
        $(".titlep").text("确定要"+$(this).text()+"吗?");
        $(".div-del").show();

    })

    //取消
    $(".div-del .p-c").eq(0).click(function () {

        $(this).parents(".div-del").hide();

    })

    //删除确定按钮
    $(".div-del .p-q").click(function () {
       if(cz==0) {
           var ulp = basePath
               + "ea/bidrecruit/sajax_ea_deleteRecruitInfo.jspa";
       }else{
           var ulp = basePath
               + "ea/bidrecruit/sajax_ea_onOfflineRecruit.jspa?sccId="+sccId;
       }
           $(this).parents(".div-del").hide();

           $.ajax({
               type: "GET",
               url: ulp,
               async: true,
               dataType: "json",
               data: {
                   "recruitInfo.riId": riId
               },
               success: function (data) {

                   re_load();
               },
               error: function (data) {

               }
           });



    })





});


function re_load() {
    document.location.href = basePath+"ea/bidrecruit/ea_findPositionList.jspa?sccId="+sccId;

}