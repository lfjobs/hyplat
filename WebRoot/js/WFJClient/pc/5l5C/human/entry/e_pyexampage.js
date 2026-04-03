
    $(document).ready(function () {
        show();

        $(".py-li span").click(function(){

              var quesType = $(this).attr("data-value");
              document.location.replace(basePath+"ea/quest/ea_getPyExamQuesList.jspa?totalQuestionExam.tqeID="+tqeID+"&questionsExam.quesType="+quesType+"&examRelate.erId="+erId+"&isHg="+isHg);
        });


        //上传成绩
        $(".jj").click(function(){
            $(".div-tingyong").show();
            $(".titlep").text("确定要上传成绩?");
            return false;


        })


        $(".close-confirm").click(function(){
            $(".div-tingyong").hide();
            var titlep = $(".titlep").text();
            if(titlep.indexOf("确定要上传成绩")!=-1){

                var url = basePath + "ea/quest/sajax_ea_uploadScore.jspa";
                $.ajax({
                    url : url,
                    type : "post",
                    async : true,
                    dataType : "json",
                    data :{
                        "examRelate.erId":erId
                    },
                    success : function(data) {
                        document.location.replace(basePath + "ea/quest/ea_getExamResult.jspa?examRelate.erId="+erId+"&py=py");


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
        $('.pf-input').bind('input propertychange', function() {

            var score = Number($(this).val());
            var topscore = Number($(this).parents(".answer").find(".topscore").text());
            var qreID = $(this).parents(".answer").find(".qreID").text();
            var tqeID =$(this).parents(".answer").find(".tqeID").text();
            if(!isPositiveInteger(score)){
                $(".div-tingyong").show();
                $(".titlep").text("请输入0-"+topscore+"的整数");
                $(this).val("");
                return false;
            }else{
                if(score>topscore){
                    $(".div-tingyong").show();
                    $(".titlep").text("请输入0-"+topscore+"的整数");
                    $(this).val("");
                    return false;
                }
            }

            manualAudit(qreID,tqeID,score);
        });
    })
    
    function show(){

       if(quesType=="03"){
           if(sizt==0){
               $(".jj").hide();
               $(".py-li span").eq(0).hide();
           }else {
               $(".py-li span").eq(0).addClass("checkc");
               $(".py-li span").eq(1).removeClass("checkc");

           }

       }else{
           if(sizt==0){
               $(".py-li span").eq(1).hide();
           }else {
               $(".py-li span").eq(1).addClass("checkc");
               $(".py-li span").eq(0).removeClass("checkc");

           }



       }
        if(isHg!=""){
            $(".jj").hide();
            $(".pf-input").attr("readonly",true);
        }


    }




   //正整数
    function isPositiveInteger(str) {
        return /^\d+$/.test(str);
    }
    //记录答评分
    function manualAudit(qreID,tqeID,score){

        var url = basePath + "ea/quest/sajax_ea_manualAudit.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data :{
                "questionsExam.qreID":qreID,
                "questionsExam.tqeID":tqeID,
                "questionsExam.score":score
            },
            success : function(data) {


            },
            error : function(data) {

            }
        });
    }
