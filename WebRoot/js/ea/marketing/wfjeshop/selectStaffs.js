
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


$(document).ready(function () {
    if(window.parent.typez=="tc") {
        getMember();
    }
    $("#search").focus();
    $(".sec-bottom").click(function () {

        var li = $(".ul-list li.active").length;
        if(li<1){
            return false;
        }

                var receiverID = $(".ul-list li.active").attr("id");
                var staffname = $(".ul-list li.active").attr("staffname");

                $(window.parent.document).find("#responsibleID").val(receiverID);
                $(window.parent.document).find(".responsibleName").text(staffname);
                $(window.parent.document).find("#responsibleName").val(staffname);
                $(window.parent.document).find(".iframecom").hide();
    });



    $("#qsearch").click(function() {
        var parameter = $("#search").val();
        if($.trim(parameter)==""){

            return false;
        }


        $(".ul-list").html("");
        $(".navrecent").hide();
        getMember();

    })
   




  //选中
    $(document).on("click",".ul-list li",function(){

        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{
            $(".active").removeClass("active");
            $(this).addClass("active");
        }
    })


    $(".close-tingyong").click(function(){

        if (isAndroid == true || isiOS == true) {
            window.history.back()
        } else {
            if(window!=window.top){
                window.history.back()
            }else{
                window.opener.location.reload();
                window.close();
            }

        }
    })

})


function getMember(){
    var parameter = $("#search").val();

    $.ajax({
        url:basePath+"ea/carmanage/sajax_ea_queryStaffn.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            parameter:parameter
        },
        success:function(data) {
            if (data != null) {
                var htmlstr = new Array();
                var arry = eval("("+data+")").list;
                if (arry == null || arry.length == 0) {

                    return false;
                }
                for (var i = 0; i < arry.length; i++) {
                    htmlstr.push("<li class='clearfix' id='" + arry[i][0] + "'  staffname='" + arry[i][1] + "'>");

                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='" + basePath + arry[i][3] + "' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p>");

                    htmlstr.push(arry[i][1]);

                    htmlstr.push("</p>")


                    htmlstr.push("</li>")
                }
                $(".ul-list").html(htmlstr.join(""));

            }
        }
, error:function (data) {
              console.log(data);
        }

    });
}

function back(){
    $(window.parent.document).find(".iframecom").hide();
}


