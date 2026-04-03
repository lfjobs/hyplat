$(function () {
    localStorage.setItem('guaranteeData', []);
    initCss();
    getDictDataListByCompanyId();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
}
/**
 * 保障设置
 * @param type
 */
function setGuaranteeData(type){
    const data = param.slice();
    data.push("&&type=" + type)
    document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/guaranteeSalarySet.jsp?" + data.join("") ;
}

function setGuaranteeAllData(){
    const data = param.slice();
    document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/guaranteeSalarySetAll.jsp?" + data.join("") ;
}
function returnPage(){
    localStorage.removeItem('guaranteeData');
    window.history.go(-1);
    return false
}
function getDictDataListByCompanyId() {
    let url = basePath + "ea/salarylevel/sajax_ea_getDictDataListByCompanyId.jspa?";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            const listData = eval("(" + data + ")");
            localStorage.setItem('guaranteeData', data);
            let listDataLength = listData.length;
            let dictTypeOld = "";
            let multiplier = 1;
            let salary = 0;
            if (listDataLength > 0){
                let dictType = "";
                let dictValue = "";
                let dictName = "";
                for (let i = 0; i < listData.length + 1; i++) {
                    if (i < listData.length){
                        dictType = listData[i]["dictType"];
                        dictValue = listData[i]["dictValue"];
                        dictName = listData[i]["dictName"];
                    }
                    if (dictTypeOld !== dictType  || i === listDataLength){
                        if (dictTypeOld !== "" && dictTypeOld !=="basic" && dictTypeOld !== "level"){
                            $(".div-" + dictTypeOld + " .span-salary").html("(" + (multiplier * salary) + "元)");
                            $(".div-" + dictTypeOld + " .label-no-set").html("已设置");
                            $(".div-" + dictTypeOld + " .label-no-set").addClass("label-set");
                            $(".div-" + dictTypeOld + " .label-no-set").removeClass("label-no-set");
                        }
                        dictTypeOld = dictType;

                    }
                    if (i < listData.length){
                        if(dictType === "basic" ){
                            if (dictName === "basicSalary"){
                                $(".div-" + dictType + " .span-salary").html("(" + dictValue + "元)");
                                $(".div-" + dictType + " .label-no-set").html("已设置");
                                $(".div-" + dictType + " .label-no-set").addClass("label-set");
                                $(".div-" + dictType + " .label-no-set").removeClass("label-no-set");
                            }
                        } else if(dictType === "level"){
                            if (dictName === "levelSalary"){
                                $(".div-" + dictType + " .span-salary").html("(" + dictValue + "元)");
                                $(".div-" + dictType + " .label-no-set").html("已设置");
                                $(".div-" + dictType + " .label-no-set").addClass("label-set");
                                $(".div-" + dictType + " .label-no-set").removeClass("label-no-set");
                            }
                        } else {
                            if (dictName === "multiplier"){
                                multiplier = parseInt(dictValue);
                            }
                            if (dictName === "salary"){
                                salary = parseInt(dictValue);
                            }
                        }
                    }



                }
            }
        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    });
}