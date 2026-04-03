$(function(){
     //修改商品时调用
   if(posNum!=null&&posNum!=""){  //社区购物车
       //sqCartlist(posNum);
       ajaxShoppingCartList();
   }
});

/**
 * 异步获取购物车内所有商品信息
 */
function ajaxShoppingCartList() {
    var purl=basePath+"ea/wholesaleMall/sajax_ea_ajaxShoppingCartList.jspa";
    $.ajax({
        url: encodeURI(purl),
        type: "POST",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var shoppingCartList = member.shoppingCartList;//商品集合
            //var comps = member.comps;//公司集合
            var innerHtml = shoppingCartHtml(shoppingCartList);
            $('.shop').append(innerHtml);
            jisuan();
        },
        error: function (data) {
            alert("数据获取失败！");
        }
    });
}

/**
 * 拼接购物车页面
 * @param shoppingCartList 购物车商品集合
 * @param comps 公司集合
 */
function shoppingCartHtml(shoppingCartList) {
    var innerHtml, ggHtml, lrkHtml;
    innerHtml = ggHtml = lrkHtml = '';
    //循环公司
    var shopCart = null;
    for (var n = 0, m = shoppingCartList.length; n < m; n++) {
        ggHtml = '';
        lrkHtml = '';
        shopCart = shoppingCartList[n];
        if (shopCart.cmStr != null && shopCart.cmStr != "") {
            ggHtml += '尺码：' + shopCart.cmStr;
        }
        if (shopCart.ysStr != null && shopCart.ysStr != "") {
            ggHtml += '颜色：' + shopCart.ysStr;
        }
        if (shopCart.ftStr != null && shopCart.ftStr != "") {
            ggHtml += '副图：' + shopCart.ftStr;
        }
        if (shopCart.spStr != null && shopCart.spStr != "") {
            ggHtml += '视频：' + shopCart.spStr;
        }
        if ((shopCart.cmStr == null || shopCart.cmStr == "") && (shopCart.ysStr == null || shopCart.ysStr == "")
            && (shopCart.ftStr == null || shopCart.ftStr == "") && (shopCart.spStr == null || shopCart.spStr == "")) {
            ggHtml = shopCart.standard;
        }
        //录入框html
        if (fh == "1") {
            lrkHtml = '<div class="number">'
                        + '<input type="button" value="-" class="min jian ps" onclick="toJian(this);">'
                        + '<input type="text" value="' + shopCart.tjNum + '" class="nub lrNum" readonly="readonly">'
                        + '<input type="button" value="+" class="add jia ps" onclick="toJia(this);">'
                    + '</div>';
        } else {
            lrkHtml = '<div class="number number1">'
                        + '<input type="button" value="-" class="min jian ttsw_yq" onclick="toJian(this);"> '
                        + '<input type="text" value="' + shopCart.yqNum + '" class="nub lrNum" readonly="readonly">'
                        + '<input type="button" value="+" class="add jia ttsw_yq" onclick="toJia(this);">'
                    + '</div>'
                    + '<div class="number number2">'
                        + '<input type="button" value="-" class="min jian ps" onclick="toJian(this);"> '
                        + '<input type="text" value="' + shopCart.tjNum + '" class="nub lrNum" readonly="readonly">'
                        + '<input type="button" value="+" class="add jia ps" onclick="toJia(this);">'
                    + '</div>';
        }
        innerHtml += '<li class="tr">'
                        + '<h5 style="display:none;">'
                            + '<input type="hidden" class="stardard" name="stardard" value="' + ggHtml + '"/>'
                            + '<input type="hidden" name="companyId" value="' + shopCart.companyid + '"/>'
                            + '<input type="hidden" class="ttsw_dj" name="allPrice" value="' + shopCart.allPrice + '"/>'//购物车商品单机
                            + '<span class="companyID">' + shopCart.companyid + '</span>'
                            + '<input type="hidden" name="pscId" value="' + shopCart.pscId + '"/>'
                            + '<span class="pscId">' + shopCart.pscId + '</span>'
                            + '<input name="barCode" type="hidden" value="'+shopCart.barCode+'"/>'//barCode
                            + '<span class="bc">'+shopCart.barCode+'</span>'
                        + '</h5>'
                        + '<h5 class="serial">' + (n+1) + '</h5>'
                        + '<h5 class="name">'
                            + '<input name="goodsName" type="hidden" value="' + shopCart.goodsName + '" class="goodsName"/>' + shopCart.goodsName + '[' + ggHtml + ']'
                        + '</h5>'
                        + lrkHtml
                        + '<p class="money">'
                            + '&yen;<span class="tprice">' + shopCart.allPrice + '</span>'//显示单一商品总价
                        + '</p>'
                        + '<img src="' + basePath + 'images/supermarket/ico-del.png" class="del">'
                    + '</li>';
    }
    return innerHtml;
}


//增加数量
function toJia(obj){
    var $tr = $(obj).parents("li");
    var bc = $tr.find(".bc").text();//商品编号
    if(bc.substring(0,2)=="21"){//称重
        var shot = showTime();
        $(".mb").show();
        $(".tipcon").text("称重商品须依次扫描");
        $(".alert_weigh_").show();
        shot;
        return;
    }
    //遍历所有类为num的
    $(obj).prev(".lrNum").val(Number($(obj).prev(".lrNum").val())+1);//录入显示数量
    //判断类中是否包含特定字段
    if($(obj).attr("class").indexOf("ttsw_yq")!=-1) {//包含
        alert('1');
    }else{//不包含
        alert('2');
    }
    jisuan();
    if(posNum!=null&&posNum!=""){
        var pscId = $tr.find(".pscId").text();//购物车id
        var stardard = $tr.find(".stardard").val();//规格
        var barCode = $tr.find(".bc").text();//条码
        var showNum = 0;
        $tr.find(".lrNum").each(function(){
            showNum+= Number($(this).val());//总数
        })
        //说明是社区首页入口，需要增加社区购物车
        //shoppingCartChange(ppid, stardard, itemNum,"jia",barCode,sendNum,showNum);
    }
}

//减少数量
function toJian(obj){
    var $tr = $(obj).parents("li");
    //遍历所有类为num的
    var num = Number($(obj).next(".lrNum").val());//修改数量
    if(num <= 1){
        num = 1;
    }
    $(obj).next(".lrNum").val(num-1);//录入显示数量
    jisuan();
    if(posNum!=null&&posNum!=""){
        var pscId = $tr.find(".pscId").text();//购物车id
        var stardard = $tr.find(".stardard").val();//规格
        var barCode = $tr.find(".bc").text();//条码
        var showNum = 0;
        $tr.find(".lrNum").each(function(){
            showNum+= Number($(this).val());//总数
        })
        //说明是社区首页入口，需要增加社区购物车
        //shoppingCartChange(ppid, stardard, itemNum,"jia",barCode,sendNum,showNum);
    }
}

//社区加减购物车
function shoppingCartChange(ppid, stardard, amount,opr,barCode,sendNum,showNum) {
    var url = basePath + "ea/smg/sajax_sm_addShoppingCart.jspa";
    if(opr=="jian"){
        url = basePath + "ea/smg/sajax_sm_reduceShoppingCart.jspa";
    }
    $.ajax({
        type: "POST",
        url: url,
        async: true,
        dataType: "json",
        data: {
            ppid: ppid,
            stardard: stardard,
            totalNum: amount,
            posNum:posNum,
            barCode:barCode,
            sendNum:sendNum,
            showNum:showNum
        },
        success: function (data) {
        },
        error: function (data) {
            //商品加入购物车失败
            alert("数据处理失败!");
            return;
        }
    });
}


//统计总数量/总金额
function jisuan(){
    totalnum = 0;
    totalmoney = 0;
    $(".shop .tr").each(function(){
        var num1 = 0;
        //循环获取添加数量
        $(this).find(".lrNum").each(function(){
            num1+= Number($(this).val());
        });
        //获取单价金额
        var djPrice = Number($(this).find(".ttsw_dj").val());//单价
        var money = Number(num1*djPrice).toFixed(2);
        $(this).find(".tprice").text(money);
        totalnum = totalnum+num1;
        totalmoney = Number(totalmoney)+Number(money);

    });
    $(".totalnum").text(totalnum);
    $(".totalmoney").text(Number(totalmoney).toFixed(2));
}
//设定倒数秒数
var t = 4;
function showTime(){
    t -= 1;
    $(".alert_weigh p span").text(t);
    //每秒执行一次,showTime()
    var s = setTimeout("showTime()",1000);
    if(t==0){
        t = 4;
        $(".alert_weigh_").hide();
        clearTimeout(s);
        $(".alert_weigh p span").text(t);
    }
    /*商品称重弹框*/
    $("#confirm").click(function () {
        $(".alert_weigh_").hide();
        clearTimeout(s);
        t = 4;
        $(".alert_weigh p span").text(t);
    });
}

