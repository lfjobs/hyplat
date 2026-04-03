var treeid = "";
var treename = "";
var parentid = "";
var parentname = "";
var pageSize = 25;
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

$(document).ready(function () {
    load();
    $("#qsearch").click(function() {
        var parameter = $("#search").val();
        if(parameter!=""){
        $(".ul-list").html("");
        pageNumber = 0;
        pageCount = 0;
        $("#mbfl").hide();
        $(".sec-ul").show();


            loadsearch();

        }
    });

    $("#search").bind('keyup', function() {

        var parameter = $("#search").val();
        if(parameter=="") {
            $(".ul-list").html("");
            pageNumber = 0;
            pageCount = 0;
            $("#mbfl").show();
            $(".sec-ul").hide();
           // loadsearch();
        }
    });


    //新建
    $(".draft").click(function(event){
        $(".ul .active").removeClass("active");

        event.stopPropagation();

        if(treeid==""||treeid.indexOf("temptid")==-1){
            $(".div-tingyong").show();
            $(".titlep").text("请选择模板分类");
            return false;
        }
        if(isAndroid==true||isiOS==true){

            var sysSet = tree.getUserData(treeid, 'sysSet');
            if(module==""){
                module = tree.getUserData(treeid, 'module');

            }
            if(window!=window.top){
              parent.location.href = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet="+isSet+"&templateTypeName="+treename+"&temptId="+treeid+"&module="+module+"&sysSet="+sysSet+"&ifr=true";

            }else{
                window.location.href  = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet="+isSet+"&templateTypeName="+treename+"&temptId="+treeid+"&module="+module+"&sysSet="+sysSet;

            }

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

    //本地上传
    $("#bd").click(function(){
        $('.con').hide();
        if(treeid==""||treeid.indexOf("temptid")==-1){
            $(".div-tingyong").show();
            $(".titlep").text("请选择模板分类");
            return false;
        }

        var sysSet = tree.getUserData(treeid, 'sysSet');
        if(module==""){
            module = tree.getUserData(treeid, 'module');

        }

        window.open(basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet="+isSet+"&templateTypeName="+treename+"&temptId="+treeid+"&module="+module+"&sysSet="+sysSet);


    });
    //在线编辑
    $("#lineedit").click(function(){
        $('.con').hide();
        if(treeid==""||treeid.indexOf("temptid")==-1){
            $(".div-tingyong").show();
            $(".titlep").text("请选择模板分类");
            return false;
        }

        var sysSet = tree.getUserData(treeid, 'sysSet');
        if(module==""){
            module = tree.getUserData(treeid, 'module');

        }

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

                window.open(basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=edit&docPath="+docPath+"&fileType=W&isSet="+isSet+"&templateTypeName="+treename+"&temptId="+treeid+"&module="+module+"&sysSet="+sysSet);

            },
            error : function(data) {
                alert("创建模板失败");
            }

        });



    });

    //查看
    $(".view").click(function(){
        var tempId = treeid;
        if($("#mbfl").is(":hidden")){
            var li = $(".ul-list li.active");
            var length = $(li).length;
            if(length<1){
                return false;
            }
            tempId = $(li).attr("id");

        }else{
            if(treeid==""||treeid=="main"||treeid.indexOf("temptid")!=-1){
                $(".div-tingyong").show();
                $(".titlep").text("请选择模板");
                return false;

            }
        }
        


        var ulp = basePath
            + "ea/androiddoc/sajax_ea_getTempInfoByID.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "templateId": tempId
            },
            success: function (data) {
                var me = eval("("+data+")");
                var temp = me.temp;
                var templateId = treeid;
                var fileShowName = temp.fileShowName;
                var sysSet = temp.sysSet;
                var templateTypeName = parentname;
                var fileType = temp.fileType;
                var templatePath = temp.templatePath;


                if(isAndroid==true||isiOS==true){

                    if(window!=window.top){
                        parent.location.href = basePath+"page/ea/main/office_ea/contract/tempView.jsp?sysSet="+sysSet+"&fileShowName="+fileShowName+"&templateId="+templateId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&isSet="+isSet;

                    }else{
                        window.location.href  =basePath+"page/ea/main/office_ea/contract/tempView.jsp?sysSet="+sysSet+"&fileShowName="+fileShowName+"&templateId="+templateId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&isSet="+isSet;

                    }

                }else{
                   window.open(basePath+"page/ea/main/office_ea/contract/tempView.jsp?sysSet="+sysSet+"&fileShowName="+fileShowName+"&templateId="+templateId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&isSet="+isSet);


                }



            },
            error: function (data) {
                console.log("失败")

            }


        });



    })
    //修改
    $(".edit").click(function(){


        var tempId = treeid;
        if($("#mbfl").is(":hidden")){
            var li = $(".ul-list li.active");
            var length = $(li).length;
            if(length<1){
                return false;
            }
            tempId = $(li).attr("id");

        }else{
            if(treeid==""||treeid=="main"||treeid.indexOf("temptid")!=-1){
                $(".div-tingyong").show();
                $(".titlep").text("请选择模板");
                return false;

            }
        }




        var ulp = basePath
            + "ea/androiddoc/sajax_ea_getTempInfoByID.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "templateId": tempId
            },
            success: function (data) {
                var me = eval("("+data+")");
                var temp = me.temp;

                var templateId = treeid;
                var temptId = temp.temptId;
                var fileShowName = temp.fileShowName;
                var templateTypeName = parentname;
                var fileType = temp.fileType;
                var templatePath = temp.templatePath;
                var sysSet = temp.sysSet;
                var module = temp.module;
                if(isAndroid==true||isiOS==true){

                    if(window!=window.top){
                        parent.location.href = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=update&sysSet="+sysSet+"&fileShowName="+fileShowName+"&temptId="+temptId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&docPath="+templatePath+"&templateId="+templateId+"&isSet="+isSet+"&module="+module+"&ifr=true";


                    }else{
                        window.location.href  = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=update&sysSet="+sysSet+"&fileShowName="+fileShowName+"&temptId="+temptId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&docPath="+templatePath+"&templateId="+templateId+"&isSet="+isSet+"&module="+module;

                    }

                }else{
                window.open(basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=update&sysSet="+sysSet+"&fileShowName="+fileShowName+"&temptId="+temptId+"&templateTypeName="+templateTypeName+"&fileType="+fileType+"&docPath="+templatePath+"&templateId="+templateId+"&isSet="+isSet+"&module="+module);

                }

            },
            error: function (data) {
                console.log("失败")

            }


        });



    })




    //排序
    $(".sort").click(function(event){
        if(treeid==""||treeid.indexOf("temptid")==-1){
            $(".div-tingyong").show();
            $(".titlep").text("请选择模板分类");
            return false;
        }

        parent.location.replace(basePath+"ea/androiddoc/ea_getTempSortlist.jspa?temptId="+treeid+"&module="+module);



    });

    //删除
    var del = 0;
    $(".del").click(function(){
        if(treeid==""||treeid=="main"||treeid.indexOf("temptid")!=-1){
            $(".div-tingyong").show();
            $(".titlep").text("请选择模板");
            return false;

        }
        var sysSet = tree.getUserData(treeid, 'sysSet');

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



        var ulp = basePath
            + "ea/androiddoc/sajax_ea_deleteTemp.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "templateId": treeid
            },
            success: function (data) {
                var me = eval("("+data+")");
                var r  = me.r;
                if(r==0){
                    tree.deleteItem(treeid,true);
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
                loadsearch();
            }
        }

    })



    //选中
    $(document).on("click",".ul-list li",function(event){

        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{

            $(".ul-list .active").removeClass("active");

            $(this).addClass("active");
        }
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
    if(isSet=="0"){

        var mbname = "";

        tree.insertNewChild("0", "main", "共享模板", 0, 0, 0, 0);
        getTypeList("main");
    }
    if(companyID==""){//个人模板合同和公文模板在一起了

        tree.insertNewChild("0", "me", "文书模板", 0, 0, 0, 0);

        tree.insertNewChild("me", "doc", "公文模板", 0, 0, 0, 0);
        tree.insertNewChild("me", "contract", "合同模板", 0, 0, 0, 0);
    }else if(companyID!=""){//公司模板
        tree.insertNewChild("0", "me", "公司模板", 0, 0, 0, 0);
    }
    getTypeList("me");

   // tree.openAllItems("main");
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
                  tree.setUserData(list[i].temptId, "module",
                      list[i].module);
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

                }

            }



        },
        error: function (data) {
            console.log("失败");
        }
    });

}

