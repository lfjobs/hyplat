var treeid = "";
var treename = "";
var parentid = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {
    load();
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


        var sysSet = "01";
        if(treeid==""&&isSet=="0"){

            $(".div-tingyong").show();
            $(".titlep").text("请选择模板分类");
            return false;

        }else if(treeid==""&&isSet=="1"){
            treeid="me";
        }else if(treeid=="main"){
            sysSet="00";
        }else if(treeid=="me"){
            sysSet="01";
        }else if(treeid=="doc"||treeid=="contract"){
            sysSet="01";
        }else{

            sysSet = tree.getUserData(treeid, 'sysSet');
        }



        if(companyID==""){
            if(treeid==""||treeid=="me"){
                $(".div-tingyong").show();
                $(".titlep").text("请选择公文模板分类或者合同模板分类");
                treeid="";
                return false;
            }else{
                module = treeid;
            }

        }


        if(window!=window.top){
            if(isAndroid==true||isiOS==true){
                parent.location.href = basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?isSet="+isSet+"&parentId="+treeid+"&sysSet="+sysSet+"&module="+module+"&companyID="+companyID+"&ifr=true";

            }else{
                window.open(basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?isSet="+isSet+"&parentId="+treeid+"&sysSet="+sysSet+"&module="+module+"&companyID="+companyID);

            }

        }else{
           window.location.href = basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?isSet="+isSet+"&parentId="+treeid+"&sysSet="+sysSet+"&module="+module+"&companyID="+companyID;

    }




    });




    //修改
    $(".edit").click(function(){
        if(treeid==""||treeid=="main"||treeid=="me"){

            $(".div-tingyong").show();
            $(".titlep").text("请选择具体模板分类");
            return false;
        }

       var  sysSet = tree.getUserData(treeid, 'sysSet');//有问题

        if(window!=window.top){
            if(isAndroid==true||isiOS==true){
                parent.location.href = basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?sysSet="+sysSet+"&templateTypeName="+treename+"&temptId="+treeid+"&isSet="+isSet+"&parentId="+parentid+"&module="+module+"&ifr=true";

            }else{
                window.open(basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?sysSet="+sysSet+"&templateTypeName="+treename+"&temptId="+treeid+"&isSet="+isSet+"&parentId="+parentid+"&module="+module);

            }

        }else{
            window.location.href = basePath+"page/ea/main/office_ea/contract/tempTypeAdd.jsp?sysSet="+sysSet+"&templateTypeName="+treename+"&temptId="+treeid+"&isSet="+isSet+"&parentId="+parentid+"&module="+module;

        }


    })




    //排序
    $(".sort").click(function(event){
        if(treeid==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择要排序的上级分类");
            return false;
        }
        parent.location.replace(basePath+"ea/androiddoc/ea_getSortlist.jspa?parentId="+treeid+"&module="+module);



    });

    //删除
    var del = 0;
    $(".del").click(function(){
        if(treeid==""||treeid=="main"||treeid=="me"){
            return false;
        }

        var sysSet = "";
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
        if(treeid==""||treeid=="main"||treeid=="me"){
            return false;
        }


        var tempId = treeid;
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
                    tree.deleteItem(treeid,true);


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
//共享分类
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



        tree.insertNewChild("0", "main", "共享模板分类", 0, 0, 0, 0);
        getTypeList("main");

    }
    if(companyID==""){//个人模板合同和公文模板在一起了

        tree.insertNewChild("0", "me", "模板分类", 0, 0, 0, 0);
        tree.insertNewChild("me", "doc", "公文模板分类", 0, 0, 0, 0);
        tree.insertNewChild("me","contract", "合同模板分类", 0, 0, 0, 0);
    }else if(companyID!=""){//公司模板
        tree.insertNewChild("0", "me", "公司模板分类", 0, 0, 0, 0);
        getTypeList("me");
    }


   // tree.openAllItems("main");
    tree.setOnClickHandler(function() {
        treeid = tree.getSelectedItemId();
         treename = tree.getItemText(treeid);
         parentid = tree.getParentId(treeid);
        // parentname = tree.getItemText(parentid);



      var state = tree.getOpenState(treeid);



        if(state==1){
            tree.closeItem(treeid);
        }else{

            if(!tree.hasChildren(treeid)){
                // tree.deleteChildItems(treeid);
                getTypeList(treeid,"00");
            }
            tree.openItem(treeid);
        }





    });



}




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
                    if (tree.getItemText(list[i].temptId) == 0) {
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

