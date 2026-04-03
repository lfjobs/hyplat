/**
 * Created by ljc on 2017/3/14 0014.
 */
$(document).ready(function () {

    loaded(flag);
    //切换产品
    $(".filtrate_nav").on("click",function () {
        $(".mer_list").empty();
        flag = $(this).attr("id");
        pagenumber = 0;
        pagecount = 0;
        $(this).addClass("mertab_cur");
        $(this).siblings().removeClass("mertab_cur");
        loaded();
    });
    //搜索
    $(".search").focus(function(){
        $(".search_overly").hide();
    })
    $(".search").blur(function(){
        var val=$(this).val();
        if(val==''){
            $(".search_overly").show();
        }
    })
    $(".search").on("input",function(){
        $(".mer_list").empty();
        search = $(this).val();
        if (search!='')
        {
            flag = "";
            pagenumber = 0;
            pagecount = 0;
            $('.filtrate_nav').removeClass("mertab_cur");
            loaded();
        }
    });
});
function loaded(){
    ajax(flag);
}
function getHeight(){
    t=setTimeout("getHeight()",200);
    if($(".last").length>0){
        if($(".last").offset().top + $(".last").height() - $("header").height() * 4 < $(window).height()){
            if(pagenumber < pagecount){
                loaded();
            }
        }
    }
}
function ajax(flag){
    clearTimeout(t);
    pagenumber ++;
    url = basePath + "/ea/productAgent/sajax_ajaxProAgentList.jspa";
    $.ajax({
        url : url,
        type: "post",
        async:false,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pagenumber,
            "pageForm.pageSize":10,
            "flag":flag,
            "search":search
        },
        success : function cbf(data){
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = new Array();
            if(pageForm!=null && pageForm.recordCount > 0){
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for(var i = 0;i < list.length;i++){
                    var entity = list[i];
                    if(i==list.length - 1){
                        str.push('<a href="' + basePath + 'ea/productAgent/ea_proAgentDetail.jspa?ppId=' + entity[0] + '" class="mer_box clearfix last">');
                    }else{
                        str.push('<a href="' + basePath + 'ea/productAgent/ea_proAgentDetail.jspa?ppId=' + entity[0] + '" class="mer_box clearfix">');
                    }
                    str.push('<img src="' + basePath + entity[4] + '" class="mer_img" alt="">');
                    str.push('<div class="mer_R">');
                    str.push('<div class="mer_tit">' + entity[5] + '</div>');
                    /*str.push('<div class="mer_price">￥' + entity[4] + '</div>');*/

                    /*if (entity[3]=='02')
                    {
                        str.push('<div class="mer_commission">省级代理佣金￥' + entity[5] + '</div>');
                    }
                    else if (entity[3]=='03')
                    {
                        str.push('<div class="mer_commission">县级代理佣金￥' + entity[5] + '</div>');
                    }else if (entity[3]=='04')
                    {
                        str.push('<div class="mer_commission">村级代理佣金￥' + entity[5] + '</div>');
                    }else if (entity[3]=='00')
                    {
                        str.push('<div class="mer_commission">贴牌佣金￥' + entity[5] + '</div>');
                    }else if (entity[3]=='01')
                    {
                        str.push('<div class="mer_commission">设备安装佣金￥' + entity[5] + '</div>');
                    }*/
                    str.push('</div></a>');
                }
                $(".mer_list").append(str.join(""));
                getHeight();
            }
        }
    })
}
