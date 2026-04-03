var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var isWin = u.indexOf('Windows') > 1;

$(function () {
    $(".div_search").hide();
    getHistoryBusinessType();

    $(document).on("click", ".search_box", function () {
        $(".div-search").show();
    });
});

function searchBusinessType() {
    var search = $('#ttsw_item_search_id').val();
    if("" == search.trim()){
        return;
    }
    var url = basePath + "ea/scBudget/sajax_ea_getBusinessType.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data: {
            "search": search.trim(),
        },
        dataType: "json",
        success: function(data) {
            var businessTypeList = null;
            var member = (new Function("return " + data))();
            if(null != member){
                businessTypeList = member.businessTypeList;
            }
            var innerHtml="";
            if (businessTypeList != null && businessTypeList.length > 0) {
                innerHtml = itemHtml(businessTypeList);
            }else {
                innerHtml += nullHtml();
            }
            $('#searchUl').html(innerHtml);
            $(".div_search").show();
        }, error: function cbf(data) {
            alert("数据获取失败！");
        }
    });
}
function nullHtml() {
    var innerHtml = '<li>未查询到行业数据</li>';
    return innerHtml;
}
function itemHtml(businessTypeList) {
    var innerHtml = '';
    for (var i = 0; i < businessTypeList.length; i++) {
        var businessType = businessTypeList[i];
        innerHtml += '<li id="'+ businessType.typeId +'" onclick="addBusinessTypeRecent(this)">';
        innerHtml += businessType.typeName;
        innerHtml += '</li>';
    }
    return innerHtml;
}

function getHistoryBusinessType(){
    $.ajax({
        url:basePath + "ea/scBudget/sajax_ea_findBusinessTypeRecent.jspa",
        type:"POST",
        dataType:"json",
        aysnc:true,
        data:{
            flag:"budget"
        },
        success:function(data){
            var innerHtml = '';
            var businessTypeList = null;
            var member = eval("("+data+")");
            if(null != member){
                businessTypeList = member.businessTypeRecentList;
            }
            if (businessTypeList != null && businessTypeList.length > 0) {
                var typeNameArray = new Array();
                for (var i = 0; i < businessTypeList.length; i++) {
                    var businessType = businessTypeList[i];
                    if(!typeNameArray.includes(businessType.typeName)){
                        typeNameArray.push(businessType.typeName);
                        innerHtml += '<li id="'+ businessType.typeId +'" onclick="setBusinessType(this)">';
                        innerHtml += businessType.typeName;
                        innerHtml += '</li>';
                    }
                }
                $(".navRecent").show();
            }else{
                $(".navRecent").hide();
            }
            $(".ul-list2").html(innerHtml);
        },
        error:function (data) {
        }
    });
}

function addBusinessTypeRecent(obj){
    var url = basePath + "ea/scBudget/sajax_ea_addBusinessTypeRecent.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "businessTypeRecent.typeId": obj.id,
            "businessTypeRecent.typeName": obj.innerText,
            "businessTypeRecent.flag": "budget"
        },
        success: function(data) {
            setBusinessType(obj);
        },
        error: function () {
        }
    });
}

function setBusinessType(obj){
    if(editFlag == "edit"){
        window.location.href = basePath + "ea/scBudget/ea_toUpdateProjectBill.jspa?" +
            "commitFlag='no'&cashierBillsId="+cashierBillsId+"&billsType="+billsType+"&typeId="+obj.id+"&typeName="+obj.innerText;
    }else{
        window.location.href = basePath + "ea/scBudget/ea_toAddProjectBill.jspa?" +
            "commitFlag='no'&billsType="+billsType+"&typeId="+obj.id+"&typeName="+obj.innerText;
    }
}