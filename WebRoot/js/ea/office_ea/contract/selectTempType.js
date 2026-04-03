var treeid = "";
var treename = "";
var parentid = "";

$(document).ready(function () {
    load();



    $(".close-tingyong").click(function(){

        $(".div-tingyong").hide();
    })




     //选择分类后确定
    $(".qdbtn").click(function () {
        if(treeid==""||treeid=="main"||treeid=="me"){
            return false;
        }
        var  sysSet = tree.getUserData(treeid, 'sysSet');
        $(window.parent.document).find("#temptId").val(treeid);


        $(window.parent.document).find("#specificTemplate").val(treename);
        $(window.parent.document).find("#sysSet").val(sysSet);
        $(window.parent.document).find(".p-leixing").text(sysSet=="01"?"否":"是");

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
    if(companyID==""){//个人模板分类


        tree.insertNewChild("0", "me", "模板分类", 0, 0, 0, 0);
        tree.insertNewChild("me", "doc", "公文模板分类", 0, 0, 0, 0);
        tree.insertNewChild("me", "contract", "合同模板分类", 0, 0, 0, 0);

    }else if(companyID!=""){//公司模板
        tree.insertNewChild("0", "me", "公司模板分类", 0, 0, 0, 0);
    }
    getTypeList("me");
    
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
    tree.setOnDblClickHandler(function() {

        $(".qdbtn").trigger("click");

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

//关闭返回弹窗
function close(){
    $(window.parent.document).find(".iframecom").hide();

}

