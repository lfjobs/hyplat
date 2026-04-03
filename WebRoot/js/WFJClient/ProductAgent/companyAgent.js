/**
 * Created by ljc on 2017/3/17 0017.
 */
$(document).ready(function () {

loaded();

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
function  loaded() {
    clearTimeout(t);
    pagenumber ++;
    var url = basePath + "ea/productAgent/sajax_ajaxCompanyAgent.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "pageForm.pageNumber" : pagenumber,
            "pageForm.pageSize" : 10,
            "companyId" : companyId,
            "flag" : flag
        },
        success : function (data)
        {
            var member = eval("("+data+")");
            var comlist=member.comlist;
            var pageForm = member.pageForm;
            var str = new Array();
            if(pageForm!=null && pageForm.recordCount > 0)
            {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for (var i = 0; i < list.length; i++)
                {
                    var entity = list[i];
                    if(i==list.length - 1)
                    {
                        str.push('<a href="' + basePath + 'ea/productAgent/ea_proAgentDetail.jspa?ppId=' + entity.ppID+ '" class="cm_pro_box last">');
                    }else
                    {
                        str.push('<a href="' + basePath + 'ea/productAgent/ea_proAgentDetail.jspa?ppId=' + entity.ppID + '" class="cm_pro_box">');
                    }
                        str.push('<img src="' + basePath + entity.photoPath + '" class="c_pro_img" alt="">')
                        str.push('<div class="pro_tit">' + entity.goodsName + '</div>')
                        /*str.push('<div class="pro_yj">代理佣金：<span>'+ entity.pmlist[0].amount +'起</span></div></a>');*/
                }
                if (pagenumber==pagecount)
                {
                    var s = new Array();
                    s.push('<div class="all">已展示全部商品</div>');
                }
                $('.cm_pro_wrap').append(str.join(""));
                $('.cm_pro_wrap').after(s.join(""));
                getHeight();
            }
            if (comlist!=null && comlist.length > 0 )
            {
                $('.cm_tit img').attr("src",basePath + (comlist[0][3]==null ? "images/WFJClient/PersonalJoining/logo@2x.png" : comlist[0][3]));
                $('.cm_text div:first').text(comlist[0][0]);
                $('.cm_text div:last').text(comlist[0][2]);
                $('header h1').text(comlist[0][0]);
            }
        }
    });
}