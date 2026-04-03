//加载需求详情
function xp(ddid) {
    let p=false;
    if (originPage != "" && originPage != null) {
        requestPage = originPage.split("-");
        if (requestPage[0] == "win") {
            if (requestPage[1]=="pd"){
                p=true;
            }
        }
    }
    //$(".overlay_text").show();
    //$(".qd_info").remove();
    //$(".state_box").remove();
    window.parent.$(".nest_bd").empty();
    var url = basePath + "/ea/dserve/sajax_ea_detailByDdid.jspa?";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "ddid": ddid,
            "dlsccid":sccid
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var demandDetail = member.demandDetail;
            var str=new Array();
            if (demandDetail != null) {
                str.push("<div class='qd_info'>");
                var temp = demandDetail.ddstatus;
                var status = "";
                if(temp == "0"){
                    status = "抢单中";
                }else if(temp == "1"){
                    status = "已确认订单";
                }else if(temp == "2"){
                    status = "过期";
                }else if (temp=="4"){
                    status = "已结算";
                }else {
                    status = "移除";
                }
                str.push("<div class='state_box'>状态："+status+"</div>");
                str.push("<div class='qd_info_line'><span>地址：</span><span>"+demandDetail.ddaddress+"</span></div>");
                str.push("<div class='qd_info_line'><span>项目工种：</span><span>"+demandDetail.ddworktype+"</span></div>");
                str.push("<div class='qd_info_line'><span>项目：</span><span>"+demandDetail.ddtitle+"</span></div>");
                str.push("<div class='qd_info_line'><span>备注：</span><span>"+demandDetail.ddremark+"</span></div>");
                /*var tempTime = demandDetail.ddexpectdate;
                if(tempTime == "null" || tempTime == null){
                    str.push("<div class='qd_info_line'><span>期望时间：</span><span></span></div>");
                }else{
                    str.push("<div class='qd_info_line'><span>期望时间：</span><span>"+demandDetail.ddexpectdate+"</span></div>");
                }*/
                str.push("<div class='qd_info_line'><span>期望价格：</span><span>"+demandDetail.ddexpectprice+"</span></div>");
                str.push("<div class='qd_info_line'><span>联系人：</span><span>"+demandDetail.ddcontactname+"</span></div>");
                str.push("<div class='qd_info_line'><span>联系电话：</span><span>"+demandDetail.ddcontactphone+"</span></div>");
                if (p||sccid==demandDetail.ddsccid){
                    if (temp==0){
                        str.push("<div class='qd_info_line'><span>抢单人：</span>");
                        str.push("<div><a href='"+basePath+"/ea/dserve/ea_serveListByDdid.jspa?ddid="+demandDetail.ddid+"&wtvalue="+demandDetail.ddworktype+"&dlsccid="+sccid+"&tle=1' class='my_rel_btn'>"+demandDetail.dscount+"人抢单</a></div></div>");
                    }
                    if (temp==1){
                        str.push("<div class='qd_info_line'><span>接单人：</span><span></span></div>");
                    }
                    if (temp==4){
                        str.push("<div class='qd_info_line'><span>接单人：</span><span></span></div>");
                        str.push("<div class='qd_info_line'><span>接单电话：</span><span></span></div>");
                        str.push("<div class='qd_info_line'><span>约定单价金额：</span><span></span></div>");
                        str.push("<div class='qd_info_line'><span>施工结算明细：</span><span></span></div>");
                        str.push("<div class='qd_info_line'><span>结算金额：</span><span></span></div>");
                        str.push("<div class='qd_info_line'><span>已付结金额：</span><span></span></div>");
                        str.push("<div class='qd_info_line'><span>未付金额：</span><span></span></div>");
                    }
                }
                str.push("</div>");

            }
            window.parent.$(".nest_bd").append(str.join(""));
            window.parent.$(".nest_page").show();
        }
    });
    $(".overlay_text").hide();
}