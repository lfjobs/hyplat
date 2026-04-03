$(document).ready(function () {
    getComplaylist();

    //选中
    $(document).on("click",".ul-list .main",function(){
        var  companyID = $(this).attr("id");
        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{
            getOrgList(companyID);
            $(this).addClass("active");
        }
    })

    //选中
    $(document).on("click",".org li",function(event){
        event.stopPropagation();
        var  orgID = $(this).attr("id");
        var companyID = $(this).parent("ul").attr("data-com");
        var orgName = $(this).find("p").text();

        window.location.replace(basePath+"page/WFJClient/pc/5l5C/office/config/selectMemberSh.jsp?companyID="+companyID+"&orgID="+orgID+"&orgName="+orgName+"&type="+type+"&keyId="+keyId);
    })
    //搜索查询
    $("#search").click(function(event){
        event.stopPropagation();
        window.location.replace(basePath+"page/WFJClient/pc/5l5C/office/config/selectMemberSh.jsp?type="+type+"&keyId="+keyId);

    });
})

function getComplaylist(){

    $.ajax({
       url:basePath+"ea/android/video_getCompanyList.jspa",
        type:"get",
        dataType:"json",
        aysnc:true,
        success:function(data){
            if(data!=null) {
                var htmlstr = new Array();
                var arry = data.result;



                for(var i=0;i<arry.length;i++){
                    htmlstr.push("<li id='"+arry[i].companyID+"'class='main'>");

                    htmlstr.push("<div class='box clearfix'>");
                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_08.png'/>");
                    htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_09.png'/>");
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='"+basePath+arry[i].logo+"' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p>");
                    htmlstr.push(arry[i].companyName);
                    htmlstr.push("</p>");
                    htmlstr.push("</div>");
                    htmlstr.push("<ul class='clearfix org "+arry[i].companyID+"' data-com='"+arry[i].companyID+"'>");
                    htmlstr.push("</ul>");
                    htmlstr.push("</li>");
                }
                $(".ul-list").append(htmlstr.join(""));
            }
            // console.log(data);
        },
        error:function (data) {
            
        }

    });
}

function getOrgList(companyID){

    $.ajax({
        url:basePath+"ea/android/video_getOrgList.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            companyID:companyID
        },
        success:function(data){
            if(data!=null) {
                var htmlstr = new Array();
                var arry = data.result;

                for(var i=0;i<arry.length;i++){
                    htmlstr.push("<li class='clearfix' id='"+arry[i].orgID+"'>");

                    htmlstr.push("<p>"+arry[i].orgName+"</p>");
                    htmlstr.push("<div>");
                    htmlstr.push("<img src='"+basePath+"images/ea/office/contract/selectp/img_10.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("</li>");

                }

                $("ul."+companyID).html(htmlstr.join(""));
            }
        },
        error:function (data) {

        }

    });
}