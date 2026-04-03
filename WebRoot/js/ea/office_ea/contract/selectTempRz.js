var treeid = "";
var treename = "";
var parentid = "";
var parentname = "";
var pageSize = 25;
$(document).ready(function () {
    load();
    $("#qsearch").click(function() {
        $(".ul-list").html("");
        pageNumber = 0;
        pageCount = 0;
        $(".sec-list").hide();
        $(".sec-ul").show();
        loadsearch();
    });

    $("#search").bind('keyup', function() {

        var parameter = $("#search").val();
        if(parameter=="") {
            $(".ul-list").html("");
            pageNumber = 0;
            pageCount = 0;
            $(".sec-list").show();
            $(".sec-ul").hide();

        }
    });



    $(".qdbtn").click(function(){

      document.location.href = basePath+"/page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet=0&sysSet=01&fileShowName="+fileShowName+"&contractType="+contractType+"&module=contract";


    })
   //确定
    $(".save").click(function () {
        if (treeid == "" || treeid == "main" || treeid.indexOf("temptid") != -1) {
            $(".div-tingyong").show();
            $(".titlep").text("请选择模板");
            return false;

        }


        var specificTemplate = treeid;

        var tempName = treename;
        var url = basePath
            + "ea/staff/sajax_ea_saveContractTemp.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data: {
                templateId: specificTemplate,
                contractType: contractType,
                contractTypeName:tempName
            },
            success: function (data) {
                var success = "操作成功";
                if ("exist" == data){
                    success = "该文件名已存在该公司！！";
                }
                $(".div-tingyong").show();
                $(".titlep").text(success);
            }, error: function cbf(data) {
                console.log("数据获取失败！")
            }

        })
    })

    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){
            if ("iframe" === pattern){
                $(this).parents(".div-tingyong").hide();
            } else {
                window.history.back();
            }

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })
    $(window).scroll(function () {
        var secUlHeight= $(".sec-ul").height();
        if (secUlHeight > 0){
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
                    loadsearch();
                }
            }
        }


    })



    //选中
    $(document).on("click",".ul-list li",function(event){
        treeid = $(this)[0].id;
        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{

            $(".ul-list .active").removeClass("active");

            $(this).addClass("active");
        }
    })

    //选中
    $(document).on("dblclick",".ul-list li",function(event){
        $(".ul-list .active").removeClass("active");

        $(this).addClass("active");
        $(".qdbtn").trigger("click");
    })




})


function loadsearch(){

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
            ajax:"ajax",
            module:module
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

                        htmlstr.push("<span style='color:red;'>共享模板</span>");
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

function load() {
    tree = new dhtmlXTreeObject("mbfl", "100%", "100%", 0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
    tree.enableThreeStateCheckboxes(false);
    tree.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree.loadXML(basePath + "js/tree/common/tree_b.xml");



    tree.insertNewChild("0", "main", "共享模板", 0, 0, 0, 0);
    getTypeList("main");


    if(companyID==""){//个人模板 分开le

        tree.insertNewChild("0", module, "个人模板", 0, 0, 0, 0);


    }else if(companyID!=""){//公司模板
        tree.insertNewChild("0", "me", "公司模板", 0, 0, 0, 0);
    }
    getTypeList("me");
    
    tree.setOnClickHandler(function() {
        treeid = tree.getSelectedItemId();
        treename = tree.getItemText(treeid);
        parentid = tree.getParentId(treeid);
        parentname = tree.getItemText(parentid);



        var state = tree.getOpenState(treeid);



        if(state==1){
            tree.closeItem(treeid);
        }else{

            if(!tree.hasChildren(treeid)){
                // tree.deleteChildItems(treeid);
                getTypeList(treeid);
                getTempList(treeid)
            }
            tree.openItem(treeid);
        }





    });
    tree.setOnDblClickHandler(function() {
        treeid = tree.getSelectedItemId();
        treename = tree.getItemText(treeid);
        //双击查看文件
        viewContent(treename,treeid);
    });


}
function viewContent(title,templateId){

    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getPdfTempView.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            templateId: templateId
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pdfpath = member.pdfpath;

            document.location.href = basePath + "page/ea/main/office_ea/contract/pdfTempView.jsp?pdfpath="+pdfpath+"&title="+title;


        },
        error: function (data) {

            console.log("获取链接失败");
        }


    });

}

//关闭返回弹窗
function close(){
    $(window.parent.document).find(".iframecom").hide();

}
//获取分类
function  getTypeList(treeid){
    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getDocTempTypeLists.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            parentId: treeid,
            module:module

        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var list = member.list;

            var obj = "";
            for (var i = 0; i < list.length; i++) {
                if(tree.getItemText(list[i].temptId)==0) {
                    tree.insertNewChild(treeid,
                        list[i].temptId,
                        list[i].templateTypeName,
                        0, 0, 0, 0);
                    tree.setUserData(list[i].temptId, "sysSet",
                        list[i].sysSet);

                }
            }



        },
        error: function (data) {
            console.log("失败");
        }
    });

}


//获取模板
function  getTempList(treeid){
    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getDocTempList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            temptId: treeid,
            module:module

        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var list = member.list;

            var obj = "";
            for (var i = 0; i < list.length; i++) {

                if(tree.getItemText(list[i][0])==0) {
                    if (list[i][3] == "W") {
                        tree.insertNewChild(treeid,
                            list[i][0],
                            list[i][4],
                            0, "word.png", "word.png", "word.png");
                    } else if (list[i][3] == "E") {
                        tree.insertNewChild(treeid,
                            list[i][0],
                            list[i][4],
                            0, "excel-ext.png", "excel-ext.png", "excel-ext.png");


                    }

                    tree.setUserData(list[i][0], "sysSet",
                        list[i][7]);
                    tree.setUserData(list[i][0], "fileType",
                        list[i][3]);

                }

            }



        },
        error: function (data) {
            console.log("失败");
        }
    });

}

