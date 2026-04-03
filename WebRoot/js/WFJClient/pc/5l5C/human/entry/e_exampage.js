
    $(document).ready(function () {

   //收藏
    $(".sc").click(function(){
        if($(this).attr("class").indexOf("checks")!=-1){

        //说明收藏过了，要取消
            $(this).find("img").attr("src",basePath+"images/ea/office/contract/selectp/wsc.png");
            $(this).removeClass("checks");

        }else{
            $(this).find("img").attr("src",basePath+"images/ea/office/contract/selectp/sc.png");

            $(this).addClass("checks");


        }
        var url = basePath + "ea/quest/sajax_ea_collectQuestion.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data :{
                "questionsExam.qreID":qreID,
                "questionsExam.qrID":qrID
            },
            success : function(data) {


            },
            error : function(data) {

            }
        });


    })
   //交卷
  $(".jj").click(function(){
      $(".div-tingyong").show();
      $(".titlep").text("确定要交卷?");
      return false;


  })
    $(".close-confirm").click(function(){
        $(".div-tingyong").hide();
        var titlep = $(".titlep").text();
        if(titlep.indexOf("确定要交卷")!=-1){

            if (quesType == "03") {
                var  anwser = $(".anwser03").val();

                recordAnswer(anwser);

            }


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
                    parent.document.location.replace(basePath + "ea/quest/ea_getExamResult.jspa?examRelate.erId="+erId);


                },
                error : function(data) {

                }
            });

        } else{

            $(".div-tingyong").hide();
        }
        return false;


    })
    $(".close-tingyong").click(function(){
        $(".div-tingyong").hide();
    })

   //点击页数
  $(".pages img,.pages span").click(function(){
      if ($(".sl-div").is(":hidden")){
          $(".sl-div").fadeIn(500);
          $(".over").fadeIn(400);
          loadQues();

      }else{

          $(".sl-div").fadeOut(500);
          $(".over").fadeOut(600);


      }



  })
    $(".over").click(function(){

        $(".sl-div").fadeOut(500);
        $(".over").fadeOut(600);

    })




    //点击选择答案
    $(".news").click(function(){
        if($(this).find(".letter").is(".checked")){
            if (quesType == "01"  ) {
                //多选

                $(this).find(".letter").removeClass("checked");

            }
        }else{

            if (quesType == "00" || quesType == "02" ) {
                //单选和判断

                $(".news").find(".letter").removeClass("checked");

            }

            $(this).find(".letter").addClass("checked");
        }


        var anwser = $(".news .checked").text();

          recordAnswer(anwser);


    })


   //下一题
    $(".next").click(function(){
        var  bool = false;

        if (quesType == "00" || quesType == "01" || quesType == "02" ) {
            //单选多选判断

            if($(".news .checked").length>0){

                bool = true;
            }

        }else{
             if($(".anwser03").val()!=""){
                 bool = true;
             }
        }
        if(bool==false){
            $(".div-tingyong").show();
            $(".titlep").text("此题还没有作答");

            return false;
        }else {

            var anwser = "";

            if (quesType == "03") {
                anwser = $(".anwser03").val();



            } else {
                anwser = $(".news .checked").text();
            }

            recordAnswer(anwser);

        }

        document.location.replace(basePath+"ea/quest/ea_getExamPage.jspa?totalQuestionExam.tqeID="+tqeID+"&totalQuestionExam.tqID="+tqID+"&pageNumber="+(pageNumber+1)+"&next=next");


    })


   //跳转到对应题目
   $(document).on("click",".sl-ul li", function() {

       if (quesType == "03") {
           anwser = $(".anwser03").val();



       } else {
           anwser = $(".news .checked").text();
       }

       recordAnswer(anwser);
        
        pageNumber = $(this).attr("index");
       document.location.replace(basePath+"ea/quest/ea_getExamPage.jspa?totalQuestionExam.tqeID="+tqeID+"&totalQuestionExam.tqID="+tqID+"&pageNumber="+pageNumber+"&next=next");


   })
     showAnswer();



})




function showAnswer(){
   if(answer!=""&&answer!=null) {
       if (quesType == "01" || quesType == "02" || quesType == "00") {
           $(".news .letter").each(function () {
               var letter = $(this).text();
               if(answer.indexOf(letter)!=-1){
                   $(this).addClass("checked");
               }
           })

       }
   }

}

function loadQues(){
    $(".sl-ul").html("");

    var url = basePath + "ea/quest/sajax_ea_getQusStatusList.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data :{
            "totalQuestionExam.tqeID":tqeID

        },
        success : function(data) {
            var m = eval("("+data+")");
            var  list = m.list;
            var htmlstr = new Array();

            for(var i = 0;i<list.length;i++){
                var obj = list[i];
                var qreID = obj[0];
                var qrID = obj[1];
                var seq = obj[2];
                var answer = obj[3];
                var qcID = obj[4];
                htmlstr.push("<li qreID = '"+qreID+"' qrID = '"+qrID+"' index="+(i+1)+">");
                if(answer=="NOANSWER"){
                    htmlstr.push("<div class='selectw'>"+(i+1)+"</div>");

                }else{
                    htmlstr.push("<div class='selected'>"+(i+1)+"</div>");

                }

                if(qcID=="NSC"){
                    htmlstr.push("<p></p>");

                }else{

                    htmlstr.push("<p  class='scd'></p>");
                }


                htmlstr.push("</li>");

            }

            $(".sl-ul").append(htmlstr.join(""));

        },
        error : function(data) {

        }
    });

}
//记录答案
function recordAnswer(anwser){

    var url = basePath + "ea/quest/sajax_ea_recordAnswer.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data :{
            "examAnswer.qreID":qreID,
            "examAnswer.qrID":qrID,
            "examAnswer.erId":erId,
            "examAnswer.answer":anwser
        },
        success : function(data) {


        },
        error : function(data) {

        }
    });
}
function back(){


    $(".div-tingyong").show();
    $(".titlep").text("您还没有交卷，确定退出吗?");

    return false;

}

//退出时候记录
function exitRecord(){
    if (quesType == "03") {
        anwser = $(".anwser03").val();



    } else {
        anwser = $(".news .checked").text();
    }

    recordAnswer(anwser);

}





