var treeid = "";
var treename = "";
var parentid = "";

$(document).ready(function () {
    load();



    $(".close-tingyong").click(function(){

        $(".div-tingyong").hide();
         if($(".titlep").text()=="操作成功"||$(".titlep").text()=="已归档不能重复归档"){
             document.location.replace(basePath+"/ea/contract/ea_getDealFileByState.jspa?state=readedlist");

         }

    })




     //选择分类后确定
    $(".qdbtn").click(function () {
        if(treeid==""||treeid=="me"||treeid=="doc"||treeid=="contract"){
            $(".div-tingyong").show();
            $(".titlep").text("请选择具体档案盒");
            return false;
        }

        var url = basePath + "ea/androiddoc/sajax_ea_archiveDoc.jspa?date="
            + new Date();

        $.ajax({
            url : encodeURI(url),
            type : "get",
            async : false,
            dataType : "json",
            data : {
                docId : docId,
                adtId:treeid
            },
            success : function(data) {
                $(".div-tingyong").show();
                var m = eval("("+data+")");
                var r = m.result;
                if(r=="1"){
                $(".titlep").text("已归档不能重复归档");
                }else{
                    $(".titlep").text("操作成功");

                }

            }
        });



    })




})

function load() {
    tree = new dhtmlXTreeObject("mbfl", "100%", "100%", 0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
    tree.enableThreeStateCheckboxes(false);
    tree.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree.loadXML(basePath + "js/tree/common/tree_b.xml");



    if(module=="doc"){
        tree.insertNewChild("0", "doc", "公文档案盒", 0, 0, 0, 0);
        tree.setUserData("doc", "module", "doc");
        getTypeList("doc");
    }else{
        tree.insertNewChild("0", "contract", "合同档案盒", 0, 0, 0, 0);
        tree.setUserData("contract", "module", "contract");
        getTypeList("contract");
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
                    if (tree.getItemText(list[i].temptId) == 0) {
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

