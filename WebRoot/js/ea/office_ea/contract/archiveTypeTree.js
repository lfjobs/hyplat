var treeid = "";
var treename = "";
var parentid = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {
    load();


    //新建
    $(".draft").click(function(event){



        if(companyID==""){
            if(treeid==""||treeid=="me"){
                $(".div-tingyong").show();
                $(".titlep").text("请选择档案盒类型");
                treeid="";
                return false;
            }else{
                module = tree.getUserData(treeid, 'module');
            }

        }

        if(isAndroid==true||isiOS==true){
            window.location.href = basePath+"page/ea/main/office_ea/contract/archiveTypeAdd.jsp?parentId="+treeid+"&module="+module+"&companyID="+companyID;
        }else{
            window.open(basePath+"page/ea/main/office_ea/contract/archiveTypeAdd.jsp?parentId="+treeid+"&module="+module+"&companyID="+companyID);


        }



    });




    //修改
    $(".edit").click(function(){
        if(treeid==""||treeid=="me"||treeid=="doc"||treeid=="contract"){

            $(".div-tingyong").show();
            $(".titlep").text("请选择要修改的档案盒");
            return false;
        }
        module = tree.getUserData(treeid, 'module');
        if(isAndroid==true||isiOS==true){
            window.location.href=basePath+"page/ea/main/office_ea/contract/archiveTypeAdd.jsp?typeName="+treename+"&adtId="+treeid+"&parentId="+parentid+"&module="+module+"&companyID="+companyID;
        }else{
            window.open(basePath+"page/ea/main/office_ea/contract/archiveTypeAdd.jsp?typeName="+treename+"&adtId="+treeid+"&parentId="+parentid+"&module="+module+"&companyID="+companyID);



        }


    })



    //查看
    $(".view").click(function(){
        if(treeid==""||treeid=="me"||treeid=="doc"||treeid=="contract"){

            $(".div-tingyong").show();
            $(".titlep").text("请选择要查看档案盒");
            return false;
        }
        document.location.href = basePath
            + "ea/androiddoc/ea_viewArchiveBox.jspa?adtId="+treeid;

        if(isAndroid==true||isiOS==true){
           window.location.href = basePath
                + "ea/androiddoc/ea_viewArchiveBox.jspa?adtId="+treeid;
        }else {
            window.open(basePath
                + "ea/androiddoc/ea_viewArchiveBox.jspa?adtId=" + treeid);

        }

        })



    //删除
    var del = 0;
    $(".del").click(function(){
        if(treeid=="contract"||treeid=="doc"||treeid=="me"){
            return false;
        }


            $(".div-tingyong").show();
            $(".titlep").text("确定删除？");
            del = 1;
            return false;






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
        if(treeid=="doc"||treeid=="contract"||treeid=="me"){
            return false;
        }


        var adtId = treeid;
        var $li = $(".ul li.active");
        var ulp = basePath
            + "ea/androiddoc/sajax_ea_deleteArchiveType.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "adtId": adtId
            },
            success: function (data) {
                var me = eval("("+data+")");
                var r  = me.r;
                if(r==0){
                    tree.deleteItem(treeid,true);


                }else{
                    $(".div-tingyong").show();
                    $(".titlep").text("档案盒已有归档文件无法删除");
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


    if(companyID==""){//个人办公里面的

        tree.insertNewChild("0", "me", "档案盒", 0, 0, 0, 0);
        tree.insertNewChild("me", "doc", "公文档案盒", 0, 0, 0, 0);
        tree.setUserData("doc", "module", "doc");
        tree.insertNewChild("me","contract", "合同档案盒", 0, 0, 0, 0);
        tree.setUserData("contract", "module", "contract");
    }else if(companyID!=""){//公司
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
                    if (tree.getItemText(list[i].adtId) == 0) {
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

