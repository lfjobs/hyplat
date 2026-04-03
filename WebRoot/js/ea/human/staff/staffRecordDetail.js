

$(function () {
    if ("interviewRegistration" === type){
        // 面试登记
        getInterviewRegistration();
    } else if ("interviewResult" === type){
        // 面试结果
        getInterviewResult();
    }

});


/**
 * 面试登记
 */
function getInterviewRegistration(){
    const param = new Array();
    param.push("staffID=" + staffID);
    const url = basePath + "ea/staffRecord/sajax_ea_getInterviewRegistrationByStaffId.jspa?" + param.join("");
    getDataByUrl(url);
}
/**
 * 面试结果
 */
function getInterviewResult(){
    const param = new Array();
    param.push("staffID=" + staffID);
    const url = basePath + "ea/staffRecord/sajax_ea_getInterviewResultByStaffID.jspa?" + param.join("");
    getDataByUrl(url);
}

function getDataByUrl(url){
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null){
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {
                const list = codeList;
                const length = list.length;
                const htmlstr = new Array();
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<li class='clearfix'>");
                    htmlstr.push("<div class='title-pc'>");
                    htmlstr.push("<div class='date-p' title='" + list[i][1] + "'>" + list[i][1] + "</div>");
                    htmlstr.push("<div class='date-s' title='" + list[i][2] + "'>" + list[i][2] + "</div>");
                    htmlstr.push("<div class='date-p' title='" + list[i][3] + "'>" + list[i][3] + "</div>");
                    htmlstr.push("<div class='date-p' title='" + list[i][4] + "'>" + list[i][4] + "</div>");
                    htmlstr.push("</div>");
                    htmlstr.push("</li>");
                }
                $(".dataList .ul").append(htmlstr.join(""));
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
            layer.close(layer.index);
        }
    });
}