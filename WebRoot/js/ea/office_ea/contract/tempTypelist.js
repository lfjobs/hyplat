
$(document).ready(function () {

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


    //新建
     $(".draft").click(function(event){
         $(".ul .active").removeClass("active");

       document.location.href = basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?isSet="+isSet;



     });




    //修改
    $(".edit").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var temptId = $(".ul li.active").attr("id");
        var templateTypeName = $(".ul li.active").find(".templateTypeName").text();
        var sysSet = $(".ul li.active").find("#sysSet").text();

        document.location.href = basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?sysSet="+sysSet+"&templateTypeName="+templateTypeName+"&temptId="+temptId+"&isSet="+isSet;


    })





    //删除
    var del = 0;
    $(".del").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var sysSet = $(".ul li.active").find("#sysSet").text();
        if(sysSet=="00"&&isSet=="1"){
            $(".div-tingyong").show();
            $(".titlep").text("系统分类不能删除");
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
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var tempId = $(".ul li.active").attr("id");
        var $li = $(".ul li.active");
        var ulp = basePath
            + "ea/androiddoc/sajax_ea_deleteTempType.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "tempId": tempId
            },
            success: function (data) {
                var me = eval("("+data+")");
                var r  = me.r;
                if(r==0){
                    $li.remove();
                }else{
                    $(".div-tingyong").show();
                    $(".titlep").text("分类已被模板引用不可删除");
                    return false;
                }




            },
            error: function (data) {
                console.log("失败")

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



    //选中
    $(document).on("click",".ul li",function(event){

        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{

                $(".ul .active").removeClass("active");

            $(this).addClass("active");
        }
    })

   //创建模板时选择模板分类后的确定|查询选择模板后的确定查询可以多选
    $(".sec-bottom").click(function () {
        var li = $(".ul li.active");
        var length = $(li).length;
        if(length<1){
            return false;
        }
        var temptid = $(li).attr("id");
        $(window.parent.document).find("#temptId").val(temptid);
        if(pos=="add") {
            var templateTypeName = $(li).find(".templateTypeName").text();


            $(window.parent.document).find("#specificTemplate").val(templateTypeName);

        }else if(pos=="cx") {

            window.parent.window.searchByFenlei();
        }
        $(".ul .active").removeClass("active");
        $(window.parent.document).find(".iframecom").hide();


    })




})
function load() {
    if(pageNumber==0){
        $(".sec-list .ul").html("");
    }
    pageNumber = pageNumber + 1;

    var parameter = $("#search").val();

    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getDocTempTypeList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            ajax: "ajax",
            parameter: parameter
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last1").removeClass("last1");
            var obj = "";
            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if($("#"+obj.docId).length==0) {
                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='clearfix last1' id='" + obj.temptId + "'>");
                    } else {
                        html.push("<li class='clearfix' id='" + obj.temptId + "'>");
                    }

                    html.push("<div class='sex'>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");
                    html.push("<p class='templateTypeName'>" + obj.templateTypeName + "</p>");
                    html.push("<p class='p-wq'>");
                    html.push("<span>"+obj.time+"</span>");
                    html.push("<span class='sysSet'>"+(obj.sysSet == "01"?obj.createrName:"系统")+"</span>");
                    html.push("<span id='sysSet' style='display: none;'>"+obj.sysSet+"</span>");
                    html.push("</p>");
                    html.push("</li>");
                }

            }

            $(".sec-list .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}

//关闭返回弹窗
function close(){
    $(window.parent.document).find(".iframecom").hide();

}

