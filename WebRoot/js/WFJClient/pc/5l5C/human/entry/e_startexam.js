
$(document).ready(function () {




    //开始考试
    $(".startExam").click(function(event){

      //  document.location.replace(basePath+"ea/quest/ea_getExamPage.jspa?totalQuestionExam.tqeID="+tqeID+"&totalQuestionExam.tqID="+tqID);
         document.location.replace(basePath+"ea/quest/ea_getTimePage.jspa?totalQuestionExam.tqeID="+tqeID+"&totalQuestionExam.tqID="+tqID);


    });




    $(".close-tingyong").click(function(){

        $(".div-tingyong").hide();
    })








})


