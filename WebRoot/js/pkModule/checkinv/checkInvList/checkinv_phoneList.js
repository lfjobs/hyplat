$(function () {
   // loaded();
    //计算总列表宽度
    var listWidth_1 = $(".ul_nav li").length;
    var listWidth = 0;
    for (var i = 0; i < listWidth_1; i++) {
        listWidth += $(".ul_nav").children("li").eq(i).outerWidth(true);
    }
    $(".ul_nav").width(listWidth + 25);
    //商品点击选中
    $(document).on("click",".ul_con aside",function(){
        // alert("hi");
        // debugger;
        if($(this).is(".aside_yes")){
            $(this).removeClass().addClass("aside_no");
        }else{
            $(this).removeClass().addClass("aside_yes");
        }
    });
    $(document).on("click","#div_table tr",function(){
        if($(this).find(".img_no").is(":hidden")){
            $(this).find(".img_no").show();
            $(this).find(".img_yes").hide();
            $(this).removeClass("active");
        }else{
            $("#div_table aside .img_yes").hide();
            $("#div_table aside .img_no").show();
            $("#div_table tr").removeClass("active");
            $(this).find(".img_no").hide();
            $(this).find(".img_yes").show();
            $(this).addClass("active");
        }
    });
});
function getHeight(){
    t=setTimeout("getHeight()", 200);
    if($(".last").offset().top+$(".last").height()-$(".top").height()*3<$(window).height()){
        if(pagenumber<pagecount){
            loaded();
        }
    }
}
/**
 * 拼接页面
 * @param obj 循环数据
 * @param i 循环自增i
 * @param num 集合总数-1
 * @returns {string} 返回拼接好的页面
 */

function getHtmlStr(obj,i,num){
    var liStr = getLi(i,num);//获取li
    var cashierDate = getFormatDate(obj.cashierDate);//格式化日期
    var strutsStr = getStrutsStr(obj);//拼接状态
    var htmlstr = liStr +'<section id="sec-checked">'
        +'<aside class="aside_no"  checkCasId="'+obj.cashierBillsID+'">'
        +'<img class="img_no" src="'+basePath+'images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>'
        +'<img class="img_yes" src="'+basePath+'images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>'
        +'</aside>'
        +'<h5> 单据编号：'+obj.journalNum+'<img src="'+basePath+'images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>'
        +'</section>'
        +'<div onclick="toDetail(\''+obj.cashierBillsID+'\');">'
        +'<p>公司名称：'+obj.companyName+'</p>'
        +'<p>单据类型：'+obj.billstype+'</p>'
        +'<p> 盘库人：'+obj.staffname+'</p>'
        +'<p> 部门：'+obj.orgName+'</p>'
        +'<p>单据状态：'+obj.billstatus+'</p>'
        +'<p>制单日期：'+cashierDate+'</p>'

        +'<p>责任人：'+obj.staffName+'</p>'
        +'<p>制单人：'+obj.inputName+'</p>'
        +'<p>制单日期：'+cashierDate+'</p>'
        +'<p class="un">单据状态：<span>'+strutsStr+'</span></p>'
        +'</div>'
        +'</li>';
    return htmlstr;
}
/**
 * 获取li
 * @param i 循环自增i
 * @param num 集合总数-1
 * @returns {string} 返回li
 */
function getLi(i,num){
    if(i==num){
        return "<li class=\"clearfix ttsw_last\"\">";
    }else{
        return "<li class=\"clearfix\"\">";
    }
}




