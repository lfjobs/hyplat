$(function() {
    $("input.info_search").change(function(){
        temp = $("input.info_search").val();
        phone = "";
        title = "";
        if(temp != ""){
            var pattern = /^([1-9]+\d*)$/;
            var isNum = pattern.test(temp);
            if(isNum){
                phone = temp;
            }else{
                title = temp;
            }
        }
        loaded();
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
loaded();

function loaded(){
    pagenumber++;
    clearTimeout(t);
    var url = basePath+"/ea/dserve/sajax_ea_shouDan.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "pageNumber":pagenumber,
            "phone":phone,
            "title":title
        },
        success : function(data){
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = new Array();
            if((phone != null && phone != "") || (title != null && title != "")){
                $("div.qd_con").html("");
            }
            if(pageForm != null&&pageForm.recordCount>0)
            {
                var lists = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                for(var i = 0; i<lists.length;i++)
                {
                    var list = lists[i];
                    if(list[3] == "新注册用户"){
                        str.push('<a href="###" class="qd_box qd_rec last">');
                    }else{
                        str.push("<a href='javascript:xq("+list[0]+");' class='qd_box qd_rec last' id='"+list[0]+"'>");
                    }
                    str.push('<div class="qd_top qd_two clearfix">');
                    str.push('<span>'+list[1]+'</span>');
                    str.push('<span>'+list[2]+'</span>');
                    str.push('</div>');
                    str.push('<div class="qd_bottom qd_two clearfix">');
                    str.push('<span>'+list[3]+'</span>');
                    if(list[4] != null && list[4] != ""){
                        str.push('<span>'+list[4]+'</span>');
                    }
                    str.push('</div>');
                    str.push('</a>');
                }
                $("div.qd_con").append(str.join(""));
                getHeight();
            }
        }
    });
}


//加载需求详情
function xq(ddid) {
    var temp = ddid.id;
    $(".overlay_text").show();
    $(".qd_info").remove();
    $(".state_box").remove();
    var url = basePath + "/ea/dserve/sajax_ea_detailByDdid.jspa?";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "ddid": temp
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var demandDetail = member.demandDetail;
            var str=new Array();
            if (demandDetail != null) {
                str.push("<div class='qd_info'>");
                str.push("<div class='qd_info_line'><span>地址：</span><span>"+demandDetail.ddaddress+"</span></div>");
                str.push("<div class='qd_info_line'><span>行业类别：</span><span>"+demandDetail.ddworktype+"</span></div>");
                str.push("<div class='qd_info_line'><span>标题：</span><span>"+demandDetail.ddtitle+"</span></div>");
                str.push("<div class='qd_info_line'><span>备注：</span><span>"+demandDetail.ddremark+"</span></div>");
                var tempTime = demandDetail.ddexpectdate;
                if(tempTime == "null" || tempTime == null){
                    str.push("<div class='qd_info_line'><span>期望时间：</span><span></span></div>");
                }else{
                    str.push("<div class='qd_info_line'><span>期望时间：</span><span>"+demandDetail.ddexpectdate+"</span></div>");
                }
                str.push("<div class='qd_info_line'><span>期望价格：</span><span>"+demandDetail.ddexpectprice+"</span></div>");
                str.push("<div class='qd_info_line'><span>联系人：</span><span>"+demandDetail.ddcontactname+"</span></div>");
                str.push("<div class='qd_info_line'><span>联系电话：</span><span>"+demandDetail.ddcontactphone+"</span></div></div>");
                var temp = demandDetail.ddstatus;
                var status = "";
                if(temp == "0"){
                    status = "抢单中";
                }else if(temp == "1"){
                    status = "已确认订单";
                }else if(temp == "2"){
                    status = "过期";
                }else{
                    status = "移除";
                }
                str.push("<div class='state_box'>状态："+status+"</div>");
            }
            $(".nest_bd").append(str.join(""));
            $(".nest_page").show();
        }
    });
    $(".overlay_text").hide();
}