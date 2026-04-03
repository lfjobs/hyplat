$(function () {

    if (originPage != "" && originPage != null) {
        requestPage = originPage.split("-");
        if (requestPage[0] == "win") {
            $(".rec_tab_wrap").hide();
            $(".rec_con_wrap").css("padding-top","0");
        }
    }

    if (type=="1"){
        ajax();
    }

    $(".rec_tab").click(function() {
        pagenumber=0;
        var _index = $(".rec_tab_wrap .rec_tab").index($(this));
        $(this).addClass("rec_tab_cur").siblings().removeClass("rec_tab_cur");
        $(".rec_con").eq(_index).show().siblings(".rec_con").hide();
    });

    $(".nest_back").click(function() {
        $(".nest_page").hide();
    });
});

function getHeight(){
    t=setTimeout("getHeight()",200);
    if($(".last").length>0){
        if($(".last").offset().top+$(".last").height()-$(".header").height()*4<$(window).height()){
            if(pagenumber<pagecount){
                loaded();
            }
        }
    }
}

function loaded(){
    ajax($.trim(search));
}

function ajax(s){
    $(".overlay_text").show();
    if(pagenumber==0){
        $(".qd_info_box").remove();
    }
    clearTimeout(t);
    pagenumber++;
    var url=basePath+"ea/dserve/sajax_ea_detailListBydssccid.jspa?";
    $.ajax({
        url : url,
        type: "get",
        async:false,
        dataType : "json",
        data:{
            "pagenumber":pagenumber,
            "sccid":sccid,
            "originPage":originPage
        },
        success : function cbf(data){
            var member=eval("("+data+")");
            var pageForm=member.pageForm;
            var str=new Array();
            if(pageForm!=null&&pageForm.recordCount>0){
                pagenumber=pageForm.pageNumber;
                pagecount=pageForm.pageCount;
                var dlist=pageForm.list;
                $(".last").removeClass("last");
                var tradeCode="";
                for(var i=0;i<dlist.length;i++){
                    var demand=dlist[i];
                    if(i==dlist.length-1){
                        str.push("<a href='javascript:xp(\""+demand[0]+"\");' class='qd_box qd_rec qd_info_box last' id='"+demand[0]+"'>");
                    }else{
                        str.push("<a href='javascript:xp(\""+demand[0]+"\");' class='qd_box qd_rec qd_info_box' id='"+demand[0]+"'>");
                    }
                    str.push("<div class='qd_top qd_two clearfix'>");
                    str.push("<span>"+(demand[1]==null||demand[1]==""?"--":demand[1])+"</span>");
                    str.push("<span class='state_cancel'>"+(demand[2]==null||demand[2]==""?"--":hidePhone(demand[2]))+"</span></div>");
                    str.push("<div class='qd_bottom qd_two clearfix'>");
                    str.push("<span>"+(demand[3]==null||demand[3]==""?"--":demand[3])+"</span>");
                    str.push("<span>"+(demand[4]==null||demand[4]==""?"--":demand[4])+"</span></div></a>");

                    /*str.push("<span>"+(demand[1]!=null&&demand[1]!=""?demand[1]:"--")+"</span>");
                    str.push("<span class='state_cancel'>"+demand[2]+"</span></div>");
                    str.push("<div class='qd_bottom qd_two clearfix'>");
                    str.push("<span>"+demand[3]+"</span>");
                    str.push("<span>"+demand[4]+"</span></div></a>");*/
                }
            }else{
                //<!--无货显示-->
                //str.push("<div class='no'>");
                //str.push("<img src='"+basePath+"images/ea/finance/NewPhoneOrders/wu.png' alt='' class='wu'>");
                //str.push("<p>目前还没有订单哦～</p></div>");
            }
            $(".jdjl").append(str.join(""));
            getHeight();
        }
    });
    $(".overlay_text").hide();
}

// 定义电话号码隐藏函数
function hidePhone(phone) {
    // 校验是否为11位手机号
    if (!/^1[3-9]\d{9}$/.test(phone)) {
        return phone; // 非11位手机号返回原内容
    }
    // 替换中间4位为星号
    return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
}