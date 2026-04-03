/**
 * Created by ljc on 2017/3/20 0020.
 */
$(document).ready(function () {
    loaded();
    $(".tab_box a").click(function(){
        var index=$(".tab_wrap a").index(this);
        $(this).parent().addClass("tab_cur").siblings().removeClass("tab_cur");
        flag = $(this).attr("id");
        $(".tab_content").eq(index).show().siblings(".tab_content").hide();
        $(".tab_content").eq(index).empty();
        loaded();
    })

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
function ajax(flag) {
    clearTimeout(t);
    pagenumber++;
    url = basePath + "/ea/productAgent/sajax_ajaxInvestmentPro.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "pageForm.pageSize": 10,
            "flag": flag,
            "companyId" : companyId
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var str = new Array();
            if (pageForm != null && pageForm.recordCount > 0) {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for (var i = 0; i < list.length; i++) {
                    var entity = list[i];
                    //招商中
                    if (flag == '00')
                    {
                        if (i== list.length - 1)
                        {
                            str.push('<div class="mer_list_box last">');
                        }
                        else
                        {
                            str.push('<div class="mer_list_box">');
                        }
                        str.push('<a href="'+basePath+'ea/productAgent/ea_investmentInfo.jspa?ppId=' + entity.ppID + '&companyId=' + companyId + '" class="mer_box clearfix">');
                        str.push('<img src="' +basePath + entity.image + '" class="mer_img" alt="">');
                        str.push('<div class="mer_text">');
                        str.push('<div class="mer_tit">' +entity.goodsName + '</div>');
                        //str.push('<div class="mer_price">￥' + entity.rePrice + '</div>');
                        str.push('<div class="mer_state clearfix">');
                        if (entity.pmlist.length > 0)
                        {
                            for (var j = 0;j < entity.pmlist.length;j++)
                            {
                                var pss = entity.pmlist[j];
                                if (pss.agenttype=='00')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">贴牌</span> ');
                                    }else
                                    {*/
                                        str.push('<span>贴牌</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='01')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">设备安装</span> ');
                                    }else
                                    {*/
                                        str.push('<span>设备安装</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='02')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">省级代理</span> ');
                                    }else
                                    {*/
                                        str.push('<span>省级代理</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='03')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">县级代理</span> ');
                                    }else
                                    {*/
                                        str.push('<span>县级代理</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='04')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">村级代理</span> ');
                                    }else
                                    {*/
                                        str.push('<span>村级代理</span> ');
                                    /*}*/
                                }
                            }
                        }
                        str.push('</div></div></a>');
                        str.push('<div class="operation_wrap clearfix"><div class="oper_box">');
                        str.push('<a href="javascript:;" class="oper_btn" onclick="edit(\'' + entity.ppID + '\')">');
                        str.push('<i class="edit_ico"></i><span>编辑项目</span></a></div>');
                        str.push('<div class="oper_box"><a href="javascript:;" class="oper_btn" onclick="endInvest(\''+ entity.ppID+ '\',\'01\')">');
                        str.push('<i class="end_ico"></i> <span>结束项目</span> </a></div></div></div>');
                    }else
                        //招商结束
                    {
                        if (i== list.length - 1)
                        {
                            str.push('<div class="mer_list_box last">');
                        }
                        else
                        {
                            str.push('<div class="mer_list_box">');
                        }
                        str.push('<a href="' +basePath + 'ea/productAgent/ea_investmentInfo.jspa?ppId=' +entity.ppID + '&companyId=' + companyId +'" class="mer_box clearfix">');
                        str.push('<img src="' +basePath + entity.image + '" class="mer_img" alt="">');
                        str.push('<div class="mer_text">');
                        str.push('<div class="mer_tit">' +entity.goodsName + '</div>');
                        /*str.push('<div class="mer_price">￥' + entity.rePrice + '</div>');*/
                        str.push('<div class="mer_state clearfix">');
                        if (entity.pmlist.length > 0)
                        {
                            for (var j = 0;j < entity.pmlist.length;j++)
                            {
                                var pss = entity.pmlist[j];
                                if (pss.agenttype=='00')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">贴牌</span> ');
                                    }else
                                    {*/
                                        str.push('<span>贴牌</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='01')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">设备安装</span> ');
                                    }else
                                    {*/
                                        str.push('<span>设备安装</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='02')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">省级代理</span> ');
                                    }else
                                    {*/
                                        str.push('<span>省级代理</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='03')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">县级代理</span> ');
                                    }else
                                    {*/
                                        str.push('<span>县级代理</span> ');
                                    /*}*/
                                }
                                else if (pss.agenttype=='04')
                                {
                                    /*if (pss.state=='01' )
                                    {
                                        str.push('<span class="disable">村级代理</span> ');
                                    }else
                                    {*/
                                        str.push('<span>村级代理</span> ');
                                    /*}*/
                                }
                            }
                        }
                        str.push('</div></div></a>');
                        str.push('<div class="operation_wrap clearfix"><div class="oper_box">');
                        str.push('<a href="javascript:;" class="oper_btn" onclick="edit(\'' + entity.ppID + '\')">');
                        str.push('<i class="edit_ico"></i><span>编辑项目</span></a></div>');
                        str.push('<div class="oper_box"><a href="javascript:;" class="oper_btn" onclick="endInvest(\''+ entity.ppID+ '\',\'00\')">');
                        str.push('<i class="reset_ico"></i> <span>继续招商</span> </a></div></div></div>');
                    }
                }
                if (flag == '00'){
                   $('.tab_content:first').append(str.join(""));
                }
                else
                {
                    $('.end_wrap').append(str.join(""));
                }
                getHeight();
            }
        }
    });
}

//结束/继续招商
function  endInvest(suid,flag) {
    if (flag=='01')
    {
        if (!confirm("确定要结束招商?"))
        {
            return;
        }
    }else
    {
       if (!confirm("确定要继续招商?"))
       {
            return;
       }
    }
    $.ajax({
        url : basePath + "ea/productAgent/sajax_endInvest.jspa?suid=" + suid +"&flag=" + flag,
        type : "get",
        async : false,
        success : function (data) {
            if(data == '1'){
                window.location.href = basePath + "ea/productAgent/ea_investmentPro.jspa?companyId=" + companyId + "&flag=00";
            }
        }
    });

}

//编辑项目
function edit(ppid) {
    window.location.href = basePath + "ea/productAgent/ea_checkedProducts.jspa?ppId=" +ppid + "&companyId=" + companyId +"&flag=edit";
   // window.location.href = basePath + "ea/productAgent/ea_investmentInfo.jspa?ppId=" + ppid + "&companyId=" + companyId;
}