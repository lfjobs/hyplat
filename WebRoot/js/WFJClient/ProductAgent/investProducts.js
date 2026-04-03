/**
 * Created by ljc on 2017/3/23 0023.
 */
$(document).ready(function () {

    loaded();

    //选择产品跳转发布
    $('.save_btn').click(function () {
       if ($('input[class="pro_inp"]:checked').length > 0)
       {
           $('input[class="pro_inp"]:checked').each(function () {
               ppId += $(this).attr("id") + ",";
           });
           window.location.href = basePath + "ea/productAgent/ea_checkedProducts.jspa?ppId=" +ppId +"&companyId=" + companyId;
       }
       else
       {
       alert("请选择产品!");
       }
    });

})
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
function  loaded()
{
    clearTimeout(t);
    pagenumber++;
    var url = basePath + "ea/productAgent/sajax_ajaxCompanyAgent.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "pageForm.pageSize": 10,
            "companyId": companyId,
            "flag": flag
        },
        success: function (data)
        {
            var member = eval("(" + data + ")");
            var comlist = member.comlist;
            var pageForm = member.pageForm;
            var str = new Array();
            if (pageForm != null && pageForm.recordCount > 0)
            {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for (var i = 0; i < list.length; i++)
                {
                    var entity = list[i];
                    if (i == list.length -1)
                    {
                        str.push('<label class="pro_box last">');
                    }
                    else
                    {
                        str.push('<label class="pro_box">');
                    }
                        str.push('<input type="checkbox" class="pro_inp" name="pro_select" id="' + entity.ppID + '"><i></i>');
                        str.push('<img src="' + basePath + entity.image + '" class="pro_img" alt="">');
                        str.push('<div class="pro_info"><div class="pro_tit">' + entity.goodsName + '</div></div></label>');
                        /*str.push('<div class="pro_state clearfix"><span class="pro_price">￥' + entity.rePrice + '</span></div>');
                        str.push('<span class="pro_yj">代理佣金<br>￥<b>' + entity.pmlist[0].amount + '起</b></span>');*/
                }
                $('.pro_wrap').append(str.join(""));
                getHeight();
            }else{
                str.push('<div class="wrap_page"><div class="no_pro">');
                str.push('<img src="' + basePath + 'images/WFJClient/ProductAgent/no-project.jpg" alt="">');
                str.push('<p>还没有可以招商的产品哦~</p><a href="' +basePath + 'ea/vipcenter/ea_vipDemand.jspa?" class="add_pro">添加产品</a>');
                str.push('</div></div>');
                $('.pro_wrap').append(str.join(""));
            }
        }
    });
}