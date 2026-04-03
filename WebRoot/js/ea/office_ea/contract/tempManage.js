

//删除
var del = 0;
var pageNumber = 0;
var pageSize = 25 ;
$(document).ready(function () {

    //本地上传
    $("#bd").click(function(){
        $('.con').hide();


        document.location.href = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet="+isSet;


    });
    //在线编辑
    $("#lineedit").click(function(){
        $('.con').hide();


        var url = basePath + "ea/zoffice/sajax_ea_createBlankOffice.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url : encodeURI(url),
            type : "get",
            dataType : "json",
            async : false,
            data : {
                fileType : "W",
                temp : "temp"
            },
            success : function(data) {
                var jsonresult = eval("(" + data + ")");
                var docPath = jsonresult.result;

                document.location.href = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=edit&docPath="+docPath+"&fileType=W&isSet="+isSet;

            },
            error : function(data) {
                alert("创建模板失败");
            }

        });



    });

    //新建
    $(".draft").click(function(event){
        $(".ul .active").removeClass("active");

        event.stopPropagation();
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


        if(isAndroid==true||isiOS==true){
            document.location.href = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet="+isSet;

        }else {
            var left = $(this).position().left;
            var top = $(this).position().top;
            if ($(".con").css("display") == "none") {
                $(".con").css({
                    position: "absolute",
                    left: left,
                    top: top + 45
                }).show();
            } else {

                $('.con').hide();
            }

        }

    });
    //查看
    $(".view").click(function(){
        var li = $(".ul-list li.active").length;
        if(li<1){
            return false;
        }
        var templateId = $(".ul-list li.active").attr("id");
        var fileShowName = $(".ul-list li.active").find(".fileShowName").text();
        var sysSet = $(".ul-list li.active").find("#sysSet").val();
        var templateTypeName = $(".ul-list li.active").find("#templateTypeName").val();
        var fileType = $(".ul-list li.active").find("#fileType").val();
        var templatePath = $(".ul-list li.active").find("#templatePath").val();

        document.location.href = basePath+"page/ea/main/office_ea/contract/tempView.jsp?sysSet="+sysSet+"&fileShowName="+fileShowName+"&templateId="+templateId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&isSet="+isSet;




    })
    //修改
    $(".edit").click(function(){
        var li = $(".ul-list li.active").length;
        if(li<1){
            return false;
        }
        var sysSet = $(".ul-list li.active").find("#sysSet").val();

        if(sysSet=="00"&&isSet=="1"){
            $(".div-tingyong").show();
            $(".titlep").text("系统模板没有权限修改");
            return false;

        }
        var templateId = $(".ul-list li.active").attr("id");
        var temptId = $(".ul-list li.active").find(".temptId").val();
        var fileShowName = $(".ul-list li.active").find(".fileShowName").text();

        var templateTypeName = $(".ul-list li.active").find("#templateTypeName").val();
        var fileType = $(".ul-list li.active").find("#fileType").val();
        var templatePath = $(".ul-list li.active").find("#templatePath").val();

        document.location.href = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=update&sysSet="+sysSet+"&fileShowName="+fileShowName+"&temptId="+temptId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&docPath="+templatePath+"&templateId="+templateId+"&isSet="+isSet;


    })

    //分类
    $("#iframe").attr("height",$(window).height());
    //选择模板
    $(".fenlei").click(function(){
        $(".iframecom").show();

    });


   //删除
    $(".del").click(function(){
        var li = $(".ul-list li.active").length;
        if(li<1){
            return false;
        }
        var sysSet = $(".ul-list li.active").find("#sysSet").text();
        if(sysSet=="00"&&isSet=="1"){
            $(".div-tingyong").show();
            $(".titlep").text("系统模板不能删除");
            return false;

        }else{
            $(".div-tingyong").show();
            $(".titlep").text("确定删除？");
            del = 1;
            return false;

        }




    })

    $(".close-tingyong").click(function(){

        $(".div-tingyong").hide();
    })
    //确认删除
    $(".close-confirm").click(function(){
        if(del==0){

            $(".div-tingyong").hide();
            return false;
        }
        del = 0;
        $(".div-tingyong").hide();
        var li = $(".ul-list li.active").length;
        if(li<1){
            return false;
        }

        var templateId = $(".ul-list li.active").attr("id");
        var $li = $(".ul-list li.active");
        var ulp = basePath
            + "ea/androiddoc/sajax_ea_deleteTemp.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "templateId": templateId
            },
            success: function (data) {
                var me = eval("("+data+")");
                var r  = me.r;
                if(r==0){
                    $li.remove();
                }else{
                    $(".div-tingyong").show();
                    $(".titlep").text("模板已经被使用不可删除");
                    return false;
                }




            },
            error: function (data) {
                console.log("失败")

            }


        });
    })
    $("body").click(function(){


        $('.con').hide();




    });
    $("#search").focus();
    $(".sec-bottom").click(function () {
        var li = $(".ul-list li.active");
        var length = $(li).length;
        if(length<1){
            return false;
        }
        var specificTemplate = $(li).attr("id");
        var fileType = $(li).attr("fileType-data");
        var tempName = $(li).find("p").text();
        var url = basePath + "ea/zoffice/sajax_ea_createOfficeByTemp.jspa?date="
            + new Date();
        $.ajax({
            url : encodeURI(url),
            type : "post",
            async : true,
            dataType : "json",
            data : {
                "templateId" : specificTemplate,
                "fileType" : fileType
            },
            success : function cbf(data) {
                var jsonresult = eval("(" + data + ")");
                var docPath = jsonresult.result;
                if (docPath != "failed") {

               document.location.href= basePath+"page/ea/main/office_ea/contract/bdUploadFile.jsp?opr=edit&docPath="+docPath+"&fileType="+fileType+"&tempName="+tempName+"&specificTemplateID="+specificTemplate;


                } else {
                    alert("获取模板失败！");
                }
            },
            error : function cbf(data) {
                alert("数据获取失败gege！");
            }
        });


    })

    $(window).scroll(function () {




        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        if(scroll>115){
            $(".sec-nav").addClass("nav");
        }else{
            $(".sec-nav").removeClass("nav");
        }

        var Top = $(".last1").offset().top; //元素距离顶部距离


        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                load();
            }
        }

    })

 

    $("#qsearch").click(function() {
        $(".ul-list").html("");
        pageNumber = 0;
        pageCount = 0;
        load();
    });

    $("#search").bind('keyup', function() {

        var parameter = $("#search").val();
        if(parameter=="") {
            $(".ul-list").html("");
            pageNumber = 0;
            pageCount = 0;
            load();
        }
    });


  //选中
    $(document).on("click",".ul-list li",function(){

        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{

            $(".ul-list .active").removeClass("active");

            $(this).addClass("active");
        }
    })


    $(".close-tingyong").click(function(){

        if (typee == "p"||typee == "A") {
            document.location.href = basePath+"ea/contract/ea_getMyFileList.jspa?module="+module;

        }else{
            document.location.href = basePath+"ea/contract/ea_getMyFileList.jspa?module="+module+"&deal=deal";

        }
    })

})


function load(){

    pageNumber = pageNumber+1;

    var parameter = $("#search").val();
    var temptId = $("#temptId").val();
    $.ajax({
        url:basePath+"/ea/androiddoc/sajax_ea_getDocTemp.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            pageNumber:pageNumber,
            pageSize:pageSize,
            parameter:parameter,
            temptId:temptId,
            ajax:"ajax"
        },
        success:function(data){
            if(data!=null) {
                var m = eval("("+data+")");
                var arry = m.pageForm;
                var htmlstr = new Array();
                if(arry==null){
                    return false;
                }

                pageNumber = arry.pageNumber;
                pageCount = arry.pageCount;
                $(".last1").removeClass("last1");
                for(var i=0;i<arry.list.length;i++){


                    if (i == arry.list.length - 1) {

                        htmlstr.push("<li class='clearfix last1' id='"+arry.list[i][0]+"' fileType-data='"+arry.list[i][3]+"'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='"+arry.list[i][0]+"' fileType-data='"+arry.list[i][3]+"'>");
                    }

                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");
                    if(arry.list[i][3]=="W") {
                        htmlstr.push("<img src='" + basePath + "images/ea/office/contract/word.png' >");
                    }else{
                    htmlstr.push("<img src='"+basePath+"images/ea/office/contract/excel-ext.png' >");
                    }

                    htmlstr.push("</div>");
                    htmlstr.push("<p class='fileShowName'>");

                    htmlstr.push(arry.list[i][4]);

                    htmlstr.push("</p>")
                    htmlstr.push("<p>")
                    htmlstr.push("<input type='hidden' id='sysSet' value='"+arry.list[i][7]+"'/>");
                    htmlstr.push("<input type='hidden' id='templateTypeName' value='"+arry.list[i][8]+"'/>");
                    htmlstr.push("<input type='hidden' id='templatePath' value='"+arry.list[i][1]+"'/>");
                    htmlstr.push("<input type='hidden' id='fileType' value='"+arry.list[i][3]+"'/>");
                    htmlstr.push("<input type='hidden' class='temptId' value='"+arry.list[i][10]+"'/>");


                    if(arry.list[i][5]!=null&&arry.list[i][5]!=""){
                        htmlstr.push("<span>"+arry.list[i][5].substring(0,10)+"</span>")
                    }


                    if(arry.list[i][7]=="00") {
                        //htmlstr.push("<img src='" + basePath + "images/ea/office/contract/selectp/sf.png'>")

                        htmlstr.push("<span style='color:red;'>限时免费</span>");
                    }else{
                         htmlstr.push("<span>"+(arry.list[i][6]==null?'':arry.list[i][6])+"</span>");
                    }
                    htmlstr.push("</span>")
                    htmlstr.push("</p>")
                    htmlstr.push("</li>")


                }

                $(".ul-list").append(htmlstr.join(""));
            }
            console.log(data);
        },
        error:function (data) {

        }

    });
}

function searchByFenlei(){
     $(".ul-list").html("");
     pageNumber = 0;
     pageCount = 0;
    load();
}

