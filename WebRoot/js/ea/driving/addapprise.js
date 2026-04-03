/**
 * Created by Administrator on 2017/9/26 0026.
 */

 function addApprise () {

   if($(".eval_text").val() == ""){
       alert("请输入教练评价");
    return;
   }
   if($(".eval_cur").text() == ""){
       alert("请对教练作出评价");
       return;
   }

   var pinglun=$(".eval_text").val();
   var pingji=$(".eval_cur").text();
   var xingone=xingo;
   var xingtwo=xingt;
    var url = basePath
        + "ea/student/sajax_ea_addStudentAppraise.jspa";
    photo = tubas.join("lkilklk");
    $.ajax({
        url:url,
        type:"post",
        async:false,
        dataType:"json",
        data: {
            "tbly.evaluateContent":pinglun,
            "tbly.evaluateType":pingji,
            "tbly.serviceScore":xingone,
            "tbly.teachleveScore":xingtwo,
            "photo":photo,
            "tbly.studentId":studentId,
            "tbly.teacherId":teacherId,
            "tbly.orderRecordId":etoId
        },
        success : function (data) {
            var member = eval("("+data+")");
            var fanhui=member.fanhui;
            if(fanhui == "ok"){
                window.location.href= basePath+"page/WFJClient/Student/appriceSuccess.jsp";
            }else {
                alert("评价失败");
            }

        },
        error : function cbf(data) {
            alert("数据保存失败！");
        }
    });
}