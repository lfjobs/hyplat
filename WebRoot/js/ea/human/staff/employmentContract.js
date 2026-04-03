var treeid = "";
var treename = "";
var parentid = "";
var parentname = "";
var staffContractBool = true;
var module = "contract";
var companyTempNum = 0;
let t = {pageNumber:1, pageSize:20,pageCount:0,
    contractStatus :{"A":"待签约","R":"已驳回","U":"不批准","F":"已签约","P":"待分发","S":"审核中","T":"审核中","I":"拟稿中","O":"待阅读","K":"待支付","y":"邀请"}};
var clickTabNum = "contract_0"
$(function () {
    initCss();
    bindEvents();
    clickTabNum = localStorage.getItem('clickContractTabNum');
    if (clickTabNum === "undefined" || clickTabNum === null){
        clickTabNum = "contract_0";
    }
    if (queryStaffId !== "" ){
        clickTabNum = "contract_2";
    }
    $("#" + clickTabNum).click();


});
function initCss(){
    if ("iframe" == type){
        $("#div-header").css("display","none");
        $(".content").height($(window).height()- 20);
    } else {
        $(".content").height($(window).height() - $("#div-header").height() - 20);
    }
    $(".layui-tab-contract").height($(".layui-tab-brief").height() - $(".div-contract-tabs").height());


}
function bindEvents(){
    // 监听滚动事件
    const divElement = document.querySelector('.data-list-contract');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1==1) {
            if (t.pageNumber < t.pageCount && scrollBool){
                t.pageNumber++;
                getStaffDocList();            }
        }
    })
    $(document).on("click", ".div-contract-tabs li", function (event) {
        var type = event.currentTarget.id;
        localStorage.setItem('clickContractTabNum',type);
        jumpTab(type);
    })

    $(".draft").click(function(){
        top.location.href = basePath+"/page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet=0&sysSet=01&module=contract&pattern=iframe";
    })
}

function jumpTab(type){

    if (type === "contract_0" ){
        var url ="/ea/androiddoc/ea_getSelectTemp.jspa?module=contract&source=rz&pattern=iframe";
        var original = document.getElementsByTagName("iframe")[0];
        var newFrame = document.createElement("iframe");
        newFrame.setAttribute('frameborder', '0', 0);
        newFrame.src = basePath+url;
        var parent = original.parentNode;
        parent.replaceChild(newFrame,original);
        $(".contrat-temp-data").height($(".layui-tab-contract").height() - $(".div-temp-nav").height());
        $(".sec-list-contract-temp").height($(".contrat-temp-data").height()  - $(".dtd-oa-search-bar").height()  - $(".contract-temp-select").height() - $(".div-temp-nav").height() -30);

    } else if (type === "contract_1" ){
        getCompanyTempContract();
    } else if (type === "contract_2" && staffContractBool){
        $(".dtd-oa-search-bar-input").width($(".content").width() - 20 - 55 - 70);
        $(".close-data").css("right","85px");
        $(".sec-list-contract").height($(".content").height()  - $(".layui-tab-title").height()  - 42);
        $(".data-list-contract").height($(".sec-list-contract").height() - 30);
        getStaffDocList();
        staffContractBool = false;
    }
}
function getCompanyTempContract(){
    layer.load();
    $.ajax({
        url: basePath + "ea/contract/sajax_ea_getTempConType.jspa",
        type: 'POST',
        dataType: "json",
        async: true,
        processData: false,
        contentType: false,
        success: function (data) {
            var m = eval("(" + data + ")");
            var list = m.list;
            var isSet = m.isSet;
            const htmlstr = new Array();
            for (var i = 0; i < list.length; i++){
                var obj = list[i];
                var templatePath = obj[0];
                var templateId = obj[1];
                var fileType = obj[2];
                var sysSet = obj[3];
                var fileShowName = obj[4];
                var temptId = obj[5];
                var templateTypeName = obj[6];
                var contractType = obj[7];
                htmlstr.push(" <ul class=\"div-temp-ul\">");
                htmlstr.push("<li class='div-contract-name'>" + fileShowName + "</li>");
                htmlstr.push("<li onclick=getViewContract('" + fileShowName + "','" + temptId + "','" + templateTypeName + "','" +fileType +　"','"　+templatePath + "','"
                    + templateId+ "','"  +isSet + "','" +contractType + "','"  + "')>编辑查看</li>");
                htmlstr.push("<li onclick=deleteCompanyContract('" + templateId + "')>删除</li>");
                htmlstr.push("</ul>");
                companyTempNum = obj[7];
            }
            $(".contract-temp-data").html(htmlstr.join(""));
            $(".div-contract-name").width($(".contract-temp-data").width() - 150 - 30);
            layer.close(layer.index);
        },
        error: function (data) {
            layer.close(layer.index);
        }
    });
}

/**
 * 编辑查看
 * @param fileShowName
 * @param temptId
 * @param templateTypeName
 * @param fileType
 * @param templatePath
 * @param templateId
 * @param isSet
 * @param contractType
 */
function getViewContract(fileShowName,temptId,templateTypeName,fileType,templatePath,templateId,isSet,contractType){
    var url = basePath + "/page/ea/main/office_ea/contract/tempAdd.jsp?opr=update&sysSet=01&fileShowName="
        + fileShowName + "&temptId=" + temptId + "&templateTypeName=" + templateTypeName + "&fileType=" + fileType + "&docPath=" + templatePath + "&templateId=" + templateId + "&isSet=" + isSet + "&module=contract&contractType=" + contractType;
    if (type === "iframe"){
        // 在iframe中
        top.location.href = url;

    } else {
        document.location.href = url;
    }

}

/**
 * 查看员工文件列表
 */
function getStaffDocList(){
    layer.load();
    const param = new Array();
    param.push("pageNumber=" + t.pageNumber);
    param.push("&&pageSize=" + t.pageSize);
    param.push("&&queryName=" + $("#queryNameContract").val());
    param.push("&&staffID=" + queryStaffId);
    const url = basePath + "ea/staff/sajax_ea_getStaffDocList.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null){
                $(".data-list-contract").html("暂无数据");
                $(".data-list-contract").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {
                const list = codeList.list;
                t.pageCount = codeList["pageCount"];
                $(".personData").html(codeList["recordCount"]);
                const length = list.length;
                const htmlstr = new Array();
                let name= "";
                for (let i = 0; i < length; i++) {
                    name = list[i][2];
                    if (name.length >5 ){
                        name = name.substring(0,5) + "...";
                    }
                    var docId = list[i][0];
                    var status = list[i][4];
                    htmlstr.push("<ul id='" + docId + "' class='data-ul-contract data-ul-" + docId +"'  onclick=getStaffContractData('" + docId +
                        "','" +  list[i][1] +"')>");
                    htmlstr.push("<li>" + name + "</li>");
                    htmlstr.push("<li>" + (list[i][3] == null ? " " : list[i][3]) + "</li>");
                    htmlstr.push("<li>" + (t.contractStatus[status]) + "</li>");
                    htmlstr.push("<li>" + (list[i][6] == null ? " " : list[i][6]) + "</li>");
                    htmlstr.push("<li>" + (list[i][7] == null ? " " : list[i][7]) + "</li>");
                    htmlstr.push("<li>" + (list[i][8] == null ? " " : list[i][8]) + "</li>");
                    htmlstr.push("<li>" + (list[i][9] == "01" ? "专岗" : "兼岗") + "</li>");
                    htmlstr.push("<li>" + (list[i][11] == null ? " " : list[i][11]) + "</li>");
                    htmlstr.push("<li>" + (list[i][12] == null ? " " : list[i][12]) + "</li>");
                    htmlstr.push("<li>" + (list[i][13] == null ? "无" : (list[i][13] + "级")) + "</li>");
                    htmlstr.push("</ul>")
                }
                if (t.pageNumber > 1){
                    $(".data-list-contract").append(htmlstr.join(""));
                } else {
                    $(".data-list-contract").html(htmlstr.join(""));
                }

            }
            layer.closeAll();
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}
function clearQueryName(){
    $("#queryNameContract").val("");
    t.pageNumber = 1;
    getStaffDocList();
}
function getDataByQueryName(){
    pageNumber = 1;
    getStaffDocList();
}
// 查看员工合同数据
function getStaffContractData(docId,staffId){
    top.location.href = basePath + "ea/staff/ea_getStaffContractByStaffId.jspa?staffId=" + staffId + "&&docId=" + docId;
}
// 查看合同
function viewFile(docId,status,v1,v2){
    if(status=="I"&&v1==""&&v2==curstaffID) {
        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId=" + docId + "&type=draftupdate&poe=edit&opr=bd&rz=1";
    }else{
        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="+docId+"&type=draftupdate&poe=view&rz=1";
    }
}
function getTempContractData(){
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
    // 点击树形
}
// 查看文件
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
//获取分类
function getTypeList(treeid){
    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getDocTempTypeLists.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            parentId: treeid,
            module:'contract'
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
                    tree.setUserData(list[i].temptId, "sysSet",list[i].sysSet);
                }
            }
        },
        error: function (data) {
            console.log("失败");
        }
    });

}

function deleteCompanyContract(templateId){
    layer.load();
    layer.confirm('确定删除', {
        title:'温馨提示',
        skin: 'delete-class',
        btn: ['取消','确定']
    }, function(){
        layer.close(layer.index);
    }, function() {

        var url = basePath
            + "ea/staff/sajax_ea_deleteRelateDoc.jspa?";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data: {
                templateId: templateId,
            },
            success: function (data) {
                if (data === "success"){
                    layer.msg("保存成功");
                    getCompanyTempContract();
                } else {
                    layer.msg("该模板已被使用，不能删除！！");
                }
                layer.close();
            }, error: function cbf(data) {
                console.log("数据获取失败！")
                layer.close();
            }

        })

    });

}