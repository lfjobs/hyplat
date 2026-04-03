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
    $(document).on("click", "aside", function () {
        if ($(this).is(".aside_yes")) {
            $(this).removeClass().addClass("aside_no");
        } else {
            $(this).removeClass().addClass("aside_yes");
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
/*异步获取加载数据*/
function loaded (){
    clearTimeout(t);
    pagenumber++;
    var url=basePath+"ea/cashinv/sajax_ea_getCheckInvList.jspa?";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pagenumber,
            "companyId":"company2017120766WCD285420000000088",
            search:search
        },
        dataType:"json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            if(member!=null){
                var pageForm = member.pageForm;
                var prostr="";
                if(pageForm!=null&&pageForm.recordCount>0){
                    var ifbclist=pageForm.list;
                    pagenumber=pageForm.pageNumber;
                    pagecount=pageForm.pageCount;
                    if(ifbclist!=null){
                        $(".last").removeClass("last");
                        for(var i=0;i<ifbclist.length;i++){
                            var inc=ifbclist[i];
                            if(i==ifbclist.length-1){
                                prostr+="<li class='clearfix last'>";
                            }
                            else{
                                prostr+="<li class='clearfix'>";
                            }
                            prostr+="<aside class='aside_no'>";
                            prostr+="<img class='img_no' src='/images/ea/finance/BenDis/choice_02.png'/>";
                            prostr+="<img class='img_yes' src='/images/ea/finance/wupinguanli_10.png'/>";
                            prostr+="</aside>";
                            prostr+="<div class='div_ul_c'>";
                            prostr+="<p>单据编号："+inc.journalnum+"<img src='/images/ea/edmandServe/arr_r.png'/></p>";
                            prostr+="<ul><li class='txt'>公司名称："+inc.companyName+"</li>";
                            prostr+="<li>单据类型：盘库单</li>";
                            prostr+="<li>盘库人： "+inc.staffname+"</li></ul>";

                            prostr+="<ul><li class='txt'>部门："+inc.orgName+"</li>";
                            prostr+="<li>单据状态：审核中</li>"
                            prostr+="<li>制单日期："+inc.billsdate.substring(0,10)+"</li></ul></div></li>";
                        }
                        $(".ul_con").append(prostr);
                        getHeight();
                    }
                }else{
                    prostr+="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多单据</div></li>";
                    $(".ul_con").append(prostr);
                }
            }
            return prostr;
        },
        error: function cbf(data){
            alert("产品加载失败");
        }

    });
}
