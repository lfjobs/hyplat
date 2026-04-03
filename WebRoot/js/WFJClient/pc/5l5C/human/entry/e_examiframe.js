var counttime ;
    $(document).ready(function () {


    var ct = setInterval(updateCount,1000);

    var startTime = new Date(startDate);

    var endTime = new Date();
    var timeDiff = endTime - startTime;
     counttime =  duration*60-Math.floor(timeDiff / (1000));



    $(".close-confirm").click(function(){
        $(".div-tingyong").hide();
        var titlep = $(".titlep").text();
       if(titlep.indexOf("退出")!=-1){
           myFrame.window.exitRecord();
            window.history.back();
            return false;
        }else{

            $(".div-tingyong").hide();
        }
        return false;


    })
    $(".close-tingyong").click(function(){
        $(".div-tingyong").hide();
    })


})


function updateCount(){

    var hours = Math.floor(counttime / 3600); // 计算小时数
    var minutes = Math.floor((counttime % 3600) / 60); // 计算分钟数
    var seconds = counttime % 60; // 计算剩余的秒数


    hours = hours < 10 ? "0" + hours : hours;
    minutes = minutes < 10 ? "0" + minutes : minutes;
    seconds = seconds < 10 ? "0" + seconds : seconds;

    $('#timer').text(hours+":"+minutes + ":" + seconds);

     if(counttime>0){
         counttime--;
     }else{
         clearInterval(ct);
         var url = basePath + "ea/quest/sajax_ea_handPaper.jspa";
         $.ajax({
             url : url,
             type : "post",
             async : true,
             dataType : "json",
             data :{
                 "questionsExam.tqeID":tqeID,
                 "questionsExam.tqID":tqID,
             },
             success : function(data) {
                 document.location.replace(basePath + "ea/quest/ea_getExamResult.jspa?examRelate.erId="+erId);


             },
             error : function(data) {

             }
         });
     }
}

function back(){


    $(".div-tingyong").show();
    $(".titlep").text("您还没有交卷，确定退出吗?");

    return false;

}





