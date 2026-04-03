var treeid = "";
var treename = "";
var parentid = "";
var parentname = "";
var pageSize = 25;
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {

    if(adtId!=""&&adtId!=null){
        $(".ul-list").html("");
        pageNumber = 0;
        pageCount = 0;
        $("#mbfl").hide();
        $(".sec-ul").show();
        loadsearch();
    }else{
        load();
    }


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
           loadsearch();
        }
    });



    //查看
    $(".view").click(function(){
        var id = "";
        if($("#mbfl").is(":hidden")){
            var li = $(".ul-list li.active");
            var length = $(li).length;
            if(length<1){
                return false;
            }
            id = $(li).attr("id");

        }else {
            if (treeid == "" || treeid == "me" || treeid == "doc" || treeid == "contract") {
                $(".div-tingyong").show();
                $(".titlep").text("请选择具体文件或者档案盒");
                return false;

            }
            id = treeid;
        }
        if(isAndroid==true||isiOS==true){
            if(id.indexOf("adtId") != -1){
                document.location.href = basePath
                    + "ea/androiddoc/ea_viewArchiveBox.jspa?adtId="+id;
            }else{
                document.location.href = basePath
                    + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                    + id + "&type=draftupdate&poe=view&isRead=1";
            }
        }else{
            if(id.indexOf("adtId") != -1){
               window.open(basePath
                    + "ea/androiddoc/ea_viewArchiveBox.jspa?adtId="+id);
            }else{
                window.open(basePath
                    + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                    + id + "&type=draftupdate&poe=view&isRead=1");
            }


        }



    })







    $(".close-tingyong").click(function(){

        $(".div-tingyong").hide();
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
    if(companyID==""||companyID==null){
        module = "";
    }

    var parameter = $("#search").val();
    $.ajax({
        url:basePath+"/ea/androiddoc/sajax_ea_getPageFormArchive.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            pageNumber:pageNumber,
            pageSize:pageSize,
            parameter:parameter,
            module:module,
            adtId:adtId
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

                        htmlstr.push("<li class='clearfix last1' id='"+arry.list[i][0]+"'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='"+arry.list[i][0]+"' >");
                    }

                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");

                    htmlstr.push("<img src='"+basePath+"images/ea/office/contract/PDF.png' >");


                    htmlstr.push("</div>");
                    htmlstr.push("<p class='fileShowName'>");

                    htmlstr.push(arry.list[i][2]);

                    htmlstr.push("</p>")
                    htmlstr.push("<p>")

                    htmlstr.push("<input type='hidden' id='pdfPath' value='"+arry.list[i][1]+"'/>");


                    if(arry.list[i][3]!=null&&arry.list[i][3]!=""){
                        htmlstr.push("<span>"+arry.list[i][3].substring(0,10)+"</span>")
                    }


                    htmlstr.push("<span>"+(arry.list[i][4]==null?'':arry.list[i][4])+"</span>");
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

    if(companyID==""){//个人模板合同和公文模板在一起了

        tree.insertNewChild("0", "me","档案盒", 0, 0, 0, 0);

        tree.insertNewChild("me", "doc", "公文档案盒", 0, 0, 0, 0);
        tree.setUserData("doc", "module", "doc");
        tree.insertNewChild("me", "contract", "合同档案盒", 0, 0, 0, 0);
        tree.setUserData("contract", "module", "contract");
        getTypeList("me");
    }else if(companyID!=""){//公司模板
        if(module=="doc"){
            tree.insertNewChild("0", "doc", "公文档案盒", 0, 0, 0, 0);
            tree.setUserData("doc", "module", "doc");
            getTypeList("doc");
        }else{
            tree.insertNewChild("0", "contract", "合同档案盒", 0, 0, 0, 0);
            tree.setUserData("contract", "module", "contract");
            getTypeList("contract");
        }
    }


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
                getArchiveList(treeid)
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
    module = tree.getUserData(treeid, 'module');

    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getArchiveTypeLists.jspa";
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
              if(tree.getItemText(list[i].adtId)==0) {
                  tree.insertNewChild(treeid,
                      list[i].adtId,
                      list[i].typeName,
                      0, 0, 0, 0);
                  tree.setUserData(list[i].adtId, "module",
                      list[i].module);
              }
            }



        },
        error: function (data) {
            console.log("失败");
        }
    });

}


//获取归档
function  getArchiveList(treeid){
    module = tree.getUserData(treeid, 'module');

    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getArchiveList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            adtId: treeid,
            module:module

        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var list = member.list;

            var obj = "";
            for (var i = 0; i < list.length; i++) {

                if(tree.getItemText(list[i][0])==0) {
                    tree.insertNewChild(treeid,
                        list[i][0],
                        list[i][2],
                        0, "PDF.png", "PDF.png", "PDF.png");

                    tree.setUserData(list[i][0], "module",
                        list[i][8]);

                }

            }



        },
        error: function (data) {
            console.log("失败");
        }
    });

}

