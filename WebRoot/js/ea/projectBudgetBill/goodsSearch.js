var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var isWin = u.indexOf('Windows') > 1;

$(function () {
    $(".div_search").hide();
    getHistoryGoods();

    $(document).on("click", ".search_box", function () {
        $(".div-search").show();
    });
});

function searchGoods() {
    var search = $('#ttsw_item_search_id').val();
    if("" == search.trim()){
        return;
    }
    var url = basePath + "ea/scBudget/sajax_ea_getGoods.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data: {
            "search": search.trim(),
        },
        dataType: "json",
        success: function(data) {
            var goodsList = null;
            var member = (new Function("return " + data))();
            if(null != member){
                goodsList = member.goodsList;
            }
            var innerHtml="";
            if (goodsList != null && goodsList.length > 0) {
                innerHtml = itemHtml(goodsList);
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
    var innerHtml = '<li>未查询到产品数据</li>';
    return innerHtml;
}
function itemHtml(goodsList) {
    var innerHtml = '';
    for (var i = 0; i < goodsList.length; i++) {
        var goods = goodsList[i];
        innerHtml += '<li id="'+ goods.goodsID +'" title="'+ goods.barCode +'" onclick="addGoodsRecent(this)">';
        innerHtml += goods.goodsName + '(' + goods.barCode + ')';
        innerHtml += '</li>';
    }
    return innerHtml;
}

function getHistoryGoods(){
    $.ajax({
        url:basePath + "ea/scBudget/sajax_ea_findGoodsRecent.jspa",
        type:"POST",
        dataType:"json",
        aysnc:true,
        data:{
            flag:"budget"
        },
        success:function(data){
            var innerHtml = '';
            var goodsRecentList = null;
            var member = eval("("+data+")");
            if(null != member){
                goodsRecentList = member.goodsRecentList;
            }
            if (goodsRecentList != null && goodsRecentList.length > 0) {
                var goodsNameArray = new Array();
                for (var i = 0; i < goodsRecentList.length; i++) {
                    var goodsRecent = goodsRecentList[i];
                    if(!goodsNameArray.includes(goodsRecent.goodsName + goodsRecent.barCode)){
                        goodsNameArray.push(goodsRecent.goodsName + goodsRecent.barCode);
                        innerHtml += '<li id="'+ goodsRecent.goodsId +'" title="'+ goodsRecent.barCode +'" onclick="setGoods(this)">';
                        innerHtml += goodsRecent.goodsName + '(' + goodsRecent.barCode + ')';
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

function addGoodsRecent(obj){
    var url = basePath + "ea/scBudget/sajax_ea_addGoodsRecent.jspa";
    var goodsName = obj.innerHTML;
    goodsName = goodsName.substring(0,goodsName.indexOf('('));
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "goodsRecent.goodsId": obj.id,
            "goodsRecent.barCode": obj.title,
            "goodsRecent.goodsName": goodsName,
            "goodsRecent.flag": "budget"
        },
        success: function(data) {
            setGoods(obj);
        },
        error: function () {
        }
    });
}

function setGoods(obj){
    var goodsName = obj.innerHTML;
    goodsName = goodsName.substring(0,goodsName.indexOf('('));
    if(editFlag == "edit"){
        window.location.href = basePath + "ea/scBudget/ea_toProjectItemUpdate.jspa?keyNum=" + goodsBillsID + "&editFlag=edit&" +
            "showFlag="+showFlag+"&departmentID="+departmentId+"&cashierBillsId="+cashierBillsId+"&menuId="+menuId+"&billsType="+
            billsType+"&goodsId="+obj.id+"&goodsName="+goodsName+"&barCode="+obj.title+"&typeId="+typeId+"&typeName="+typeName;
    }else{
        $("#ttsw_hid_barcode").val(obj.title);
        var url = "ea_scanningProjectInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId+"&billsType="+billsType
            +"&goodsId="+obj.id+"&goodsName="+goodsName+"&barCode="+obj.title+"&typeId="+typeId+"&typeName="+typeName;
        $("#f1").attr('action',url);
        $("#f1").submit();
    }
}