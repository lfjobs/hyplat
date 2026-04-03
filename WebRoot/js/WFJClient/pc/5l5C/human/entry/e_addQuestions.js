var checks = "";
$(document).ready(function(){
    $(".res_bot").css("height",$(window).height()).css("overflow","auto");
      show();

    $("#sex,#sexjt").click(function(){
        var quesType = $("#quesType").val();
        if(quesType!="03") {
            $(".job-int_gzxz").show();
        }

    });

    $(document).on("click",".job-int_d2 p",function(){
        if($(this).is(".confirmationBox")){
            $(this).removeClass("confirmationBox");
        }else{
           var quesType = $("#quesType").val();
           if(quesType=="00"||quesType=="02") {
               $(".job-int_d2 p").removeClass("confirmationBox");
           }

            $(this).addClass("confirmationBox");
        }

    });
    $(".job-int_d3").click(function(){
        $(".job-int_gzxz").css("display", "none");
    });
  
    $("#btn_wc").click(function () {
        $(".job-int_gzxz").css("display", "none");

        $("#sex").text($(".job-int_d2 .confirmationBox").text());
        $("#sexs").val($.trim($("#sex").text()));

    });
    $("#btn_qx").click(function () {
        $(".job-int_gzxz").css("display", "none");
    });

    //保存
    $(".saveDraft").click(function() {
        // var seq = $("#seq").val();
        // if(seq == ''){
        //     $(".div-tingyong").show();
        //     $(".titlep").text("请填写序号");
        //
        //     return
        // }
        var title = $("#title").val();

        if(title == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请填写题目");

            return
        }
        var score =  $("#score").val();

        if(score == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请填写分值");

            return
        }
        var quesType = $("#quesType").val();
        if(quesType=="00"||quesType=="01") {
            var bool = true;
            $(".news").each(function () {
                var codeValue = $(this).find(".codeValue").val();
                 if($.trim(codeValue)==""){
                     bool = false;
                     return false;
                 }
            });

            if(!bool){
                $(".div-tingyong").show();
                $(".titlep").text("答案选项内容不能为空，不需要的选项请移除");

                return
            }

        }

        var sex = $("#sex").text();

        if(sex == ''){

            if(quesType!="03") {
                $(".div-tingyong").show();
                $(".titlep").text("请选择正确答案");
                return;
            }

        }

        $("#from1").attr("target", "hidden").attr("action",
            basePath + "ea/quest/ea_saveQuestion.jspa");
        document.form.submit.click();
        token = 13;

    });


    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){
           window.history.go(-1);
           return false;

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })

    //题型
    $("#quesType").change(function () {
        var quesType = $("#quesType").val();
        copyOpt(quesType);

        $("#sex").text("");
        $("#sexs").val("");
        if(quesType=="03"){
            $("#sexs").attr("type","text");
            $("#sexjt").hide();
            $("#sex").hide();
        }else{
            $("#sexs").attr("type","hidden");
            $("#sexjt").show();
            $("#sex").show();
        }


    })
    /**
     * 新增项
     */
    $(".img001").click(function () {
        var quesType = $("#quesType").val();
       var seq = $(".news").length;
        $("#sex").text("")
        $("#sexs").val("");
       if(quesType=="00"){
           if(seq<4){
               addABC(1,seq+1)
           }else{
               $(".div-tingyong").show();
               $(".titlep").text("最多添加4个选项");
           }
       }else{
           if(seq<8){
               addABC(1,seq+1)
           }else{
               $(".div-tingyong").show();
               $(".titlep").text("最多添加8个选项");
           }
       }



    })


    /**
     * 减项
     */
    $(".img002").click(function () {
        var quesType = $("#quesType").val();

        $(this).parents(".news").remove();

        $("#sex").text("")
        $("#sexs").val("");
        if(quesType=="01"){//多选
            var seq = $(".news").length;
           if(seq>4){
               $("#opt" + seq).find(".img002").css("visibility", "visible");
           }
        }

        jxOpt();

    })
});

function re_load(){
    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;

}


function show(){

    if(p=="a"){
        if(seqlast=="") {
            $("#seq").val("1");
        }else{
            $("#seq").val(Number(seqlast)+1);
            $("#score").val(scorelast);
        }
        if(quesTypelast=="") {
            copyOpt("00");
        }else{
            $(".st").val(quesTypelast);
            copyOpt(quesTypelast);

        }

    }
    if(qrID!=null&&qrID!=""){
        $(".st").val($("#st").val());
        $("#st,.st").hide();
        $("#est").show();
        $(".img001,.tip").hide();
       checks = $("#sex").text();

        jxOpt();




    }
}

/**
 *
 *重新生成正确答案选项
 */
function jxOpt(){
    $(".job-int_d2").html("");
    var quesType = $("#quesType").val();
    var  htmls = new Array();
    $(".news").each(function(){
        var letter = $(this).find(".letter").text();

        if(checks!=""){
            if(checks.indexOf(letter)!=-1){
                htmls.push("<p class='confirmationBox'>"+letter+"</p>");
            }else{
                htmls.push("<p>"+letter+"</p>");
            }
        }else{
            htmls.push("<p>"+letter+"</p>");
        }
    });

    $(".job-int_d2").append(htmls.join(""));

    if(quesType=="03"){
        $("#rxsj").hide();
        $("#sexs").attr("type","text");
        $("#sexjt").hide();
        $("#sex").hide();
    }
}


function copyOpt(quesType){
    $(".news").remove();
    $(".job-int_d2").html("");
    if(quesType=="00"){//单选
        addABC(4,0);
        $(".img001").show();
        $("#rxsj .tip").text("单选题最多4个选项");
        $("#rxsj").show();
    }else if(quesType=="01"){//多选
        addABC(4,0);
        $(".img001").show();
               $("#rxsj .tip").text("多选题最多8个选项");
        $("#rxsj").show();
    }else if(quesType=="02"){//判断
        addABC(2,0);
        $("#rxsj .tip").text("判断题有且只有2个选项");
        $(".img001").hide();
        $("#rxsj").show();
    }else if(quesType=="03"){//简答
        $("#rxsj").hide();
        $("#sexs").attr("type","text");
        $("#sexjt").hide();
        $("#sex").hide();
    }
}
function addOption(i){
    i = i-1;

    var arr = ["A","B","C","D","E","F","G","H"];
    
    return arr[i];
}

function addABC(a,seq){
    var quesType = $("#quesType").val();
    var  htmls = new Array();
    for(var i = 1;i<=a;i++) {
        var j = i;
        if(seq!=0){
            i=seq;
        }
        $(".option-a").before($(".option-a").clone(true).attr("id", "opt"+i).attr("data-value", i).removeClass("option-a").addClass("news").css('display', 'flex'));
        $("#opt"+i).find(".letter").text(addOption(i));
        $("#opt"+i).find(".codeName").attr("name","valueMap["+i+"].codeName").val($("#opt"+i).find(".letter").text());
        $("#opt"+i).find(".codeValue").attr("name","valueMap["+i+"].codeValue");

        htmls.push("<p>"+$("#opt"+i).find(".letter").text()+"</p>");


        if(quesType=="00"||quesType=="01"){
            if(seq!=0){

                $(".img002").css("visibility", "hidden");
                $("#opt" + i).find(".img002").css("visibility", "visible");
            }else if(i==a)
            {
                if(quesType=="00") {
                    $("#opt" + i).find(".img002").css("visibility", "visible");
                }
            }

        }

        i=j;

    }
    $(".job-int_d2 p").removeClass("confirmationBox");
    $(".job-int_d2").append(htmls.join(""));
}