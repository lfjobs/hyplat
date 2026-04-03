var  posNum = "";
$(function () {

    try {
        posNum = Android.forAndroidDeviceId();

        if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);
        }
    }catch(error){
        if(($(window).width()==1080&&$(window).height()==1546)||($(window).width()==534&&$(window).height()==636)) {
            posNum = 123;

        }
    }
    if(posNum!=null&&posNum!="") {
        $(".kqin").show();

    }else{
    	$(".kqin").hide();
    }
    
    
    
    
    //查看新闻详情
    $(document).on("click", ".news", function () {
        var types = $(this).find("#types").val();
        var goodsid = $(this).attr("id");
        var ppid = $(this).find("#ppid").val();
        var companyId = $(this).find("#companyId").val();
        var ccompanyId = $(this).find("#ccompanyId").val();
        if (types == "会员分享") {
            document.location.href = basePath + "/ea/industry/ea_informationDetails.jspa?ppId=" + ppid + "&ccompanyId=" + ccompanyId + "&type=time&miniSystemJudge=03";

        } else if (types == "新闻") {
            var share = $(this).find(".share").text();
            document.location.href = basePath
                + "ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=" + ccompanyId + "&search=" + search + "&goodsid=" + goodsid;
        }

    });

    $("#search").focus(function () {
        document.location.href = basePath + "ea/digitalmall/ea_DigitalMall.jspa?flag=search";
        $("#search").css("background-color", "#FFFFCC");
    });

    //会员
    var vurl = basePath + "ea/lottery/sajax_ea_vipActivityList.jspa"
    $.ajax({
        url: encodeURI(vurl),
        type: "POST",
        async: true,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var str = [];
            if (pageForm != null && pageForm.recordCount > 0) {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for (var i = 0; i < 3; i++) {
                    var entity = list[i];
                    str.push("<a href=" + basePath + "/ea/wfjshop/ea_doodsDetail.jspa?ppid=" + entity[0] + "&goodsid=" + entity[1] + "&companyId=" + entity[2] + "&ccompanyId=contactCompany20101230UB4U5884S30000000176>");
                    str.push("<li><div>");
                    str.push("<img src='" + basePath + entity[4] + "' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"' >");
                    str.push("<p>" + entity[3] + "</p></li>");
                    str.push("</div>");
                    str.push("</li></a>");
                }
                $(".mem_con").append(str.join(""));
            } else {
                $(".mem_con").append('<li><p>暂无</p>></li>>');
            }
        }
    });


    //导航
    $(".nav").find("li").on("click", function () {
        var title = $.trim($(this).text());

        var he = 0;// 数字商城查询块高度
        if (title == "简介") {
            document.location.href = basePath + "page/WFJClient/PersonalJoining/BriefIntroduction.jsp";

        } else if (title == "资讯") {

            document.location.href = basePath + "ea/wfjshop/ea_getNewsList.jspa?typeNews=";
            //document.location.href = basePath+"/page/WFJClient/PersonalJoining/new/CompanyBuy.jsp";
        } else if (title == "办公") {
            open(basePath + "/page/ea/index.jsp", "_self");
        } else if (title == "入驻") {
            //$(".slider").css("display", "none");// 隐藏头部


            open(basePath + "/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform",
                "_self");

        } else if (title == "会员") {
            //$(".slider").css("display", "none");// 隐藏头部
            open(basePath + "/ea/consignee/ea_toVipCenter.jspa?backu=2", "_self");
        } else if (title == "商家") {//ea_getAllCompanyList ea_getAllIndustryList
            document.location.href = basePath + "ea/industry/ea_getAllCompanyList.jspa?industryType="
        } else if (title == '商城') {
            document.location.href = basePath + "ea/digitalmall/ea_DigitalMall.jspa?back=index";
        } else if (title == '产品发布') {
            alert("加入中");
            document.location.href = basePath + "ea/wfjshop/ea_consultation.jspa?";
        }else if (title == '考场约车') {
            preCar();
        } else if (title == '考勤打卡') {
            
            document.location.href = basePath + "ea/bonuspoints/ea_getCurrCompany.jspa?posNum="+posNum;
        }
    })

    //商家
    // var murl=basePath+"/ea/industry/sajax_ea_getMerchant.jspa";
    var murl = basePath + "ea/android/sajax_ea_getCompanyList.jspa?sccid=" + sccid;
    $.ajax({
        url: encodeURI(murl),
        type: "POST",
        async: true,
        dataType: "json",
        success: function (data) {
            var merchantList = data;
            var str = [];
            for (var i = 0; i < merchantList.companyList.length; i++) {
                if (i < 3) {
                    var company = merchantList.companyList[i];
                    str.push("<li><a href='" + basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + company.ccompanyid + "'><input type='hidden' id='companyname' value='" + company.companyname + "'/>");
                    str.push("<img src='" + basePath + (company.logopath != null ? company.logopath : acquiesceLoGo) + "' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"' ><p>" + company.companyname + "</p></a></li>");
                }
            }
            $(".mer_con").append(str.join(""))
        }

    })
    //商城
    var urL = basePath + "ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";
    $.ajax({
        url: encodeURI(urL),
        type: "POST",
        async: true,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var page = member.pageForm;
            var perlist = page.list;
            var str = "";
            var i = 0;
            var len = 6;
            for (i; i < len; i++) {
                var obj = perlist[i];
                str += "<li onclick='Mall(this)'>";
                str += "<img src=" + basePath + obj[4] + " onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"' >";
                str += "<input type='hidden' value='" + obj[1] + "' id='ppid'/>";
                str += "<input type='hidden' value='" + obj[5] + "' id='goodsid'/>";
                str += "<input type='hidden' value='" + obj[6] + "' id='companyid'/>";
                str += "<input type='hidden' value='" + obj[10] + "' id='ccompanyid'/>";

                str += "<input type='hidden' value='" + obj[0] + "' id='goodsName'/>";
                str += "<input type='hidden' value='" + obj[4] + "' id='photo'/>";
                str += "<input type='hidden' value='" + obj[16] + "' id='remark'/>";
                str += "<input type='hidden' value='" + obj[15] + "' id='type'/>";
                str += "<input type='hidden' value='" + obj[3] + "' id='brand'/>";
                str += "<input type='hidden' value='" + obj[17] + "' id='categoryName'/>";
                str += "<input type='hidden' value='" + obj[18] + "' id='categoryId'/>";
                str += "<input type='hidden' value='" + obj[2] + "' id='cost'/>";
                str += "<input type='hidden' value='" + obj[14] + "' id='hdtype'/>";
                if (obj[14] != null && obj[14] != "" && obj[12] != null && obj[12] != "") {
                    if (obj[14] == '00') {//促销活动
                        str += "<span class='sp cx'><i></i></span>";
                        str += "<div class='text'>";
                        str += "<h1>" + obj[0] + "</h1>";
                        str += "<span class='money'>&yen;<b>" + obj[12] + "</b></span>";
                        str += "<input type='hidden' value='" + obj[12] + "' id='price'/>";
                        str += "<input type='hidden' value='" + obj[20] + "' id='activityid'/>";
                        str += "<input type='hidden' value='3' id='priceType'/>";
                        activeStateUpdate(obj[1]);
                    }
                    if (obj[14] == '01') {//特价活动
                        str += "<span class='sp tj'><i></i></span>";
                        str += "<div class='text'>";
                        str += "<h1>" + obj[0] + "</h1>";
                        str += "<span class='money'>&yen;<b>" + obj[12] + "</b></span>";
                        str += "<input type='hidden' value='" + obj[12] + "' id='price'/>"
                        str += "<input type='hidden' value='" + obj[20] + "' id='activityid'/>";
                        str += "<input type='hidden' value='4' id='priceType'/>";
                        activeStateUpdate(obj[1]);
                    }
                } else {
                    //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                    var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {"ppid": obj[1]},
                        async: true,
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            if (member.code == '200') {
                                //活动状态更改成功
                            } else {
                                //活动状态更改异常
                            }
                        },
                        error: function (err) {
                            console.log('err')
                        }
                    })
                    if (obj[13] != null && obj[13] != "") {//vip活动
                        str += "<span class='sp vip'><i></i></span>";
                        str += "<div class='text'>";
                        str += "<h1>" + obj[0] + "</h1>";
                        str += "<span class='money'>&yen;<b>" + obj[13] + "</b></span>";
                        str += "<input type='hidden' value='" + obj[13] + "' id='price'/>";
                        str += "<input type='hidden' value='" + obj[19] + "' id='activityid'/>";
                        str += "<input type='hidden' value='2' id='priceType'/>";
                    } else {//普通零售
                        str += "<div class='text'>";
                        str += "<h1>" + obj[0] + "</h1>";
                        str += "<span class='money'>&yen;<b>" + obj[2] + "</b></span>";
                        str += "<input type='hidden' value='" + obj[2] + "' id='price'/>";
                        str += "<input type='hidden' value='0' id='priceType'/>";
                    }
                }


                /*{<span class='per'>"+obj[8]+"人购买</span>}*/
                str += "</div></li>";
            }
            $(".sto_con").append(str);
        },
        error: function (data) {

        }
    });

    //招标
    var tenderurl = basePath + "ea/purchasebids/sajax_ea_ajaxGoodbidList.jspa";
    $.ajax({
        url: encodeURI(tenderurl),
        dataType: "json",
        async: true,
        type: "POST",
        success: function (data) {
            var member = eval("(" + data + ")");
            var page = member.pageForm;
            var perlist = page.list;
            var str = "";
            for (var i = 0; i < 3; i++) {
                str += "<li onclick='viewMainProduct(\"" + perlist[i][1] + "\",\"" + perlist[i][2] + "\",\"" + perlist[i][8] + "\")'><img src=" + basePath + perlist[i][3] + " onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"' class='header'>";
                str += "<div class='text'>";
                str += "<h1>" + perlist[i][0] + "</h1>";
                str += "<p>&yen;<span>" + perlist[i][5] + "</span></p>";
                str += "</div></li>";
            }
            $(".bids_con").append(str);
        }
    })

    //新闻的展示
    var url = basePath + "ea/wfjshop/sajax_ea_AjaxNewsList.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "POST",
        async: true,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var page = member.pageForm;
            var sp = page.list;
            var newstr = "";
            var i = 0;
            var len = 4;
            for (i; i < len; i++) {
                var news = sp[i];
                var img = news[2].split(".")[0] + "small." + news[2].split(".")[1];
                newstr += "<li class='news'><img src='" + basePath + (i == 0 ? news[2] : img) + "' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"' >";
                newstr += "<input type='hidden' id='ppid' value='" + news[5] + "' />";
                newstr += "<input type='hidden' id='types' value='" + news[6] + "' />";
                newstr += "<input type='hidden' id='companyId' value='" + news[7] + "' />";
                newstr += "<input type='hidden' id='ccompanyId' value='" + news[8] + "' />";
                newstr += "<p>" + news[0] + "</p></li>";
            }
            $(".news_con").append(newstr);
        },
        error: function (data) {
            console.info("新闻加载失败")
        }
    });

    //招聘
    var recurl = basePath + "/ea/bidrecruit/sajax_ea_getRecruitIndex.jspa?lei=gs&type=ajax";
    $.ajax({
        url: encodeURI(recurl),
        type: "POST",
        async: true,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var recList = pageForm.list
            var recStr = "";
            for (var i = 0; i < 4; i++) {
                recStr += "<li onclick='recdet(this)'><img src='" + basePath + recList[i][0] + "'onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"' />";
                recStr += "<input type='hidden'value='" + recList[i][5] + "' class='riId' />";
                recStr += "<input type='hidden' value='" + recList[i][1] + "' class='position'/>";
                recStr += "<div class='text'>";
                recStr += "<h1>" + recList[i][1] + "</h1>";
                recStr += "<p>" + recList[i][2] + "</p>";
                recStr += "</div></li>";
            }
            $(".rec_con").append(recStr)
        },
        error: function (data) {

        }
    })

    var purl = basePath + "/ea/WfjIndustryPlatform/sajax_ea_platformByStaff.jspa?staffID=" + staffID;
    $.ajax({
        url: encodeURI(purl),
        type: "POST",
        async: true,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var platform = member.platform;
            var str = "";
            for (var i = 0; i < platform.length; i++) {
                str += "<a href=" + basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + platform[i][2] + " '>";
                str += "<li><div><img src=" + basePath + "images/home/ico-bg-" + (i + 1) + ".png>";
                str += "<p>" + platform[i][1] + "</p></div></li></a>";
            }
            $(".Consortium_con").append(str);
        }
    })
})

//活动状态更改[改为01:已开启]
function activeStateUpdate(ppid) {
    var url = basePath + "ea/digitalmall/sajax_ea_activeStateUpdate.jspa";
    $.ajax({
        url: url,
        type: 'POST',
        data: {"ppid": ppid},
        async: true,
        success: function (data) {
            var member = eval("(" + data + ")");
            if (member.code == '200') {
                //活动状态更改成功
            } else {
                //活动状态更改异常
            }
        },
        error: function (err) {
            console.log('err')
        }
    })


}

//更多
function addbus(obj) {
    document.location.href = basePath + obj;
}

//招聘详情
function recdet(obj) {
    var riId = $(obj).find(".riId").val();
    var position = $(obj).find(".position").val();
    window.open(basePath + "ea/bidrecruit/ea_showPosdetail.jspa?riId=" + riId + "&position=" + position + "", "_self");
}

//商城详情
function Mall(obj) {
    var type=$(obj).find("#type").val();
    var parms = [];
    var url ="";
    parms.push("ppid=" + $(obj).find("#ppid").val());
    parms.push("&ccompanyId=" + $(obj).find("#ccompanyid").val());
    if(type=="学员报名"){
        parms.push("&goodsName=" + $(obj).find("#goodsName").val());
        parms.push("&companyID=" + $(obj).find("#companyid").val());
        parms.push("&photo=" + $(obj).find("#photo").val());
        parms.push("&remark=" + $(obj).find("#remark").val());
        if (posNum != null && posNum != "") {
            if ($(obj).find("#priceType").val() == "2") {
                parms.push("&price=" + $(obj).find("#cost").val());
            } else {
                parms.push("&price=" + $(obj).find("#price").val());
            }
        }else{
            parms.push("&price=" + $(obj).find("#price").val());
        }

        parms.push("&goodsID=" + $(obj).find("#goodsid").val());
        parms.push("&licenceType=" + $(obj).find("#type").val())
        parms.push("&brand=" + $(obj).find("#brand").val());
        parms.push("&categoryName=" + $(obj).find("#categoryName").val());
        parms.push("&categoryId=" + $(obj).find("#categoryId").val());
        parms.push("&priceType=" + $(obj).find("#priceType").val());
        parms.push("&activityid=" + $(obj).find("#activityid").val());
        parms.push("&cost=" + $(obj).find("#cost").val());
        url = basePath + "/st/enroll/ea_getEnroll.jspa?" + parms.join("");
    }else {
        parms.push("&goodsid=" + $(obj).find("#goodsid").val());
        parms.push("&companyId=" + $(obj).find("#companyid").val());
        url = basePath + "ea/wfjshop/ea_doodsDetail.jspa?" + parms.join("");
    }
    window.location.href = url;
}
//招标详情
function viewMainProduct(ppID, goodsID, cashierBillID) {

    document.location.href = basePath + "ea/purchasebids/ea_viewMainProduct.jspa?ppid=" + ppID + "&bidsinfo.goodsID=" + goodsID + "&inviteBids.cashierBillsID=" + cashierBillID;
}
//超市
function superjump(){

    if(posNum!=null&&posNum!="") {

        document.location.href = basePath + "page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp?posNum="+posNum;
    }else{
        document.location.href = basePath + "page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp";

    }
}
//拼货拉
function phl(){
	if(sccid==""){
		 document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
		
	}else if(companyID==""||companyID=="null"){
		 document.location.href = basePath + "ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany";
		
	}else{
		
		 document.location.href = basePath + "ea/phl/ea_getPhlIndex.jspa";
	}
	
	 
	
}

//判断是否是终端机器
function isExistPosNum(posNum){
    var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        async:false,
        data : {
            posNum:posNum
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            if(result!="0"){
                posNum = "";
            }

        },
        error : function(data) {
            // alert("验证失败");
        }

    });
    return posNum;
}

function preCar(){
    if(posNum!=null&&posNum!=""){
        document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId="+sccid+"&posNum="+posNum+"&sc=web";
        return false;
    }
    if(sccid==null||sccid==""){
        try{
            Android.jumpToLoginActivity();
        }catch(e){

            document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
        }
    }else{
           document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId="+sccid+"&sc=web";
    }

}