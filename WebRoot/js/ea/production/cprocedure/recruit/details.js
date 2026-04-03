$(function(){
    $("#returnClick").click(function() {
        history.back();
        return false;
      //  document.location.href = basePath+"ea/resumes/ea_getRecord.jspa?staffid="+staffid+"&type="+type;
    });
    var _tel="tel:"+$("#jdet_tell").text();
    $(".jdet_content a").attr("href",_tel);

})
/**
 *
 * 改变状态
 * @param state
 */
function operate(state){


    var ulp = basePath
        + "ea/resumes/sajax_ea_changeState.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            staffid: staffid,
            tpId: tpId,
            state:state
        },
        success: function (data) {
            if(state=="05"){
                $(".jdet_btn_wrap").html(" <i class='refuse'></i><span>已拒绝</span>");

            }else if(state=="03") {
                $(".jdet_btn_wrap").html("<i class='accpet'></i><span>已接受</span>");
            }

        },error: function (data) {

        }
    });

}