$(function () {
    initCss();
    initTree();

});

/**
 * 初始化Tree
 */
function initTree(){
        const diagram = new dhx.Diagram("orgTree", {
            type: "org",scroll: true,
        });
    var url= basePath + "ea/organization/sajax_ea_getOrganizationListByCompanyId.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function(data) {
            var dataList = new Array();
            var codeList = eval("(" + data + ")");
            let name = companyName;
            if (companyName.length > 10){
                name = companyName.substring(0,10)
            }
            dataList.push({"id":companyId,"text":name,"horizontal":true});
            for (var i = 0; i < codeList.length; i++){
                var obj = {};
                obj["id"] = codeList[i]["organizationID"];
                obj["text"] = codeList[i]["organizationName"];
                var parent = codeList[i]["organizationPID"];

                if (parent !== ""){
                    obj["parent"] = parent;
                }
                dataList.push(obj);
            }
            // 加载数据
            diagram.data.parse(dataList);
            diagram.layout();
            // 监听窗口大小变化
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });

}
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() );
}

// 响应式处理
function handleResize() {
    const container = document.getElementById("diagram_container");
    const containerWidth = container.clientWidth;
    const containerHeight = container.clientHeight || 400; // 默认最小高度

    // 设置图表大小
    diagram.setSize(containerWidth, containerHeight);

    // 可选：调整缩放以适应内容
    diagramautoZoom("fit");
}