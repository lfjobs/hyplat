var treeid = "";
var treename = "";
var parentid = "";
var parentname = "";

$(document).ready(function () {
    load();


    //新建
    $(".draft").click(function(event){


        if(treeid==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择所属机构上级");
            return false;
        }
        let organizationLevel = tree.getUserData(treeid,'organizationLevel');
        if (organizationLevel === undefined){
            organizationLevel = 0;
        }
        document.location.href = basePath+"/ea/corganization/n_ea_toAdd.jspa?porganization.organizationID="+treeid+"&news=news&organization.organizationID=&porganization.organizationName="+treename
        +"&porganization.organizationLevel=" + organizationLevel;



    });




    //修改
    $(".edit").click(function(){
        if(treeid==""||parentid=="0"){
            $(".div-tingyong").show();
            $(".titlep").text("请选择要修改的部门");
            return false;
        }
        document.location.href=basePath+"/ea/corganization/n_ea_toAdd.jspa?porganization.organizationID="+parentid+"&news=news&organization.organizationID="+treeid+"&porganization.organizationName="+parentname;


    })



    //查看
    $(".view").click(function(){
        if(treeid==""||parentid=="0"){
            $(".div-tingyong").show();
            $(".titlep").text("请选择要查看的部门");
            return false;
        }
        document.location.href = basePath+"/ea/corganization/n_ea_toAdd.jspa?porganization.organizationID="+parentid+"&news=news&organization.organizationID="+treeid+"&porganization.organizationName="+parentname+"&view=view";




    })


    //排序
    $(".sort").click(function(){
        if(treeid==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择要排序的上级部门");
            return false;
        }
        document.location.href = basePath+"/ea/corganization/n_ea_toSortChildOrganization.jspa?organizationID="+treeid+"&news=news";




    })
    //删除
    var del = 0;
    $(".del").click(function(){
        if(treeid==""||parentid=="0"){
            return false;
        }


            $(".div-tingyong").show();
            $(".titlep").text("会丢失其下级部门及岗位!确认删除？");
            del = 1;
            return false;



    })
    //查看机构图
    $(".viewPic").click(function(){
        document.location.href = basePath+"/page/WFJClient/pc/5l5C/human/orgPersonEchart.jsp";
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





        var url1 = basePath + "ea/corganization/sajax_ea_delOrg.jspa";
        $.ajax({
            url: encodeURI(url1),
            type: "post",
            async: false,
            dataType: "json",
            data:{
                organizationID:treeid
            },
            success: function cbf(data){
                tree.deleteItem(treeid);
            },
            error: function cbf(data){
                alert("数据获取失败！");
            }
        });



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

})

function load() {
    tree = new dhtmlXTreeObject("org", "100%", "100%", 0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
    tree.enableThreeStateCheckboxes(false);
    tree.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree.loadXML(basePath + "js/tree/common/tree_b.xml");
    tree.insertNewChild("0", companyID, companyName, 0, 0, 0, 0);
    getItemOrg(companyID);
    tree.setOnClickHandler(function() {
        treeid = tree.getSelectedItemId();
        treename = tree.getItemText(treeid);
        parentid = tree.getParentId(treeid);
        parentname = tree.getItemText(parentid);
        tree.deleteChildItems(treeid);
        getItemOrg(treeid);



    });



}

function getItemOrg(treeid){
    var url1 = basePath + "ea/organization/sajax_ea_getOrganizationList.jspa?organizationID="+treeid+"&date="+new Date().toLocaleString();
    $.ajax({
        url: encodeURI(url1),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data){
            var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if(nologin){
                document.location.href =basePath+"page/WFJClient/pc/pc_login.jsp";
            }
            var organizationList = member.organizationList;
            if (null == organizationList) {
                return;
            }
            for (var i = 0; i < organizationList.length; i++) {
                tree.insertNewChild(treeid,
                    organizationList[i].organizationID,
                    organizationList[i].organizationName,
                    0, 0, 0, 0);
                tree.setUserData(organizationList[i].organizationID, "organizationLevel", organizationList[i].organizationLevel);
            }
        },
        error: function cbf(data){
            alert("数据获取失败！");
        }
    });
}



