function resource(){
    document.location.href = basePath+"/mobile/office/mobileoffice_toMobileLogin.jspa?sys=sys&companyId="+companyId+"&flag=yd";
}
//安卓，苹果返回
function retAndroid(){
    try{
        Android.callAndroidjianli();
    }catch(err){
        $("#ret").removeAttr("onclick");
        $("#ret").attr("onclick","javascript: window.history.go(-1);return false;");
        $("#ret").trigger('click');
        /*if(isflag == "gjpt"){
         $("#ret").attr("href",basePath+"/page/ea/BuildPlatform/wfjIndustryPlatfrom/resources.jsp");
         }else if(typeback =="typeback"){
         $("#ret").attr("href",basePath+"/ea/wfjshop/ea_getWFJshops.jspa");
         }else if(typeback =="st"){
         $("#ret").attr("href",basePath+"ea/industry/ea_getCompanyHome.jspa");
         }else{
         if(typelei=="ZHANGZHAOLIANG"&&typelei!=null){
         $("#ret").attr("href",basePath+"ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid="+sccid);
         }else{
         $("#ret").attr("href",basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType="+industryType+"&etype="+etype);
         }
         }*/
    }
}
function retIOS(){
    try{
        calProduct('');
    }
    catch(err){
        $("#ret").removeAttr("onclick");

        $("#ret").attr("onclick","javascript: window.history.go(-1);return false;");
        $("#ret").trigger('click');
        /*if(isflag == "gjpt"){
         $("#ret").attr("href",basePath+"/page/ea/BuildPlatform/wfjIndustryPlatfrom/resources.jsp");
         }else if(typeback =="typeback"){
         $("#ret").attr("href",basePath+"/ea/wfjshop/ea_getWFJshops.jspa");
         }else if(typeback =="st"){
         $("#ret").attr("href",basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170713test&industryType="+industryType+"&etype="+etype);
         }else{
         if(typelei=="ZHANGZHAOLIANG"&&typelei!=null){
         $("#ret").attr("href",basePath+"ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid="+sccid);
         }else{
         $("#ret").attr("href",basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType="+industryType+"&etype="+etype);
         }
         }*/
    }
}
//新闻详情
$(document)
    .on(
        "click",
        "#china_mil",
        function() {
            var goodsid = $(this).attr("id");
            var ppID = $(this).find(".ppID").val();
            var types = $(this).find(".types").val();
            if (types == "会员分享") {
                document.location.href = basePath
                    + "ea/industry/ea_informationDetails.jspa?ppId=" + ppID
                    + "&ccompanyId=" + ccompanyId
                    + "&type=web"
                    + "&miniSystemJudge=03";
            } else if (types == "公司新闻") {
                if (type == 'web') {
                    document.location.href = basePath
                        + "ea/industry/ea_informationDetails.jspa?ppId=" + ppID
                        + "&ccompanyId=" + ccompanyId
                        + "&type=" + type
                        + "&miniSystemJudge=02";
                } else {
                    document.location.href = basePath
                        + "ea/wfjplatform/ea_newdetail.jspa?goodsid="
                        + goodsid + "&ppid=" + ppid;
                }
            }
        });
$(document).ready(function(e) {
    ajax();
    //app返回
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    try {
        if (isAndroid == true) {
            var obj = document.getElementById("ret");
            obj.setAttribute("href", "#");
            obj.setAttribute("onclick", "retAndroid()");
        } else if (isiOS == true) {
            var obj = document.getElementById("ret");
            obj.setAttribute("href", "#");
            obj.setAttribute("onclick", "retIOS()");
        }
    } catch (e) {
        console.log("报错啦");
    }
    function ajax() {
        surl1= basePath + "ea/wfjplatform/sajax_ea_AjaxNews.jspa"
        $.ajax({
            url:encodeURI(surl1),
            type:"post",
            data : {
                "ccompanyId" : ccompanyId,
                "type" : "web",
                "miniSystemJudge" : "02"
            },
            dataType:"json",
            async:false,
            success:function (data) {
                var member = eval("(" + data + ")");
                var list = member.list;
                pagecount = member.pagecount;
                var addTo = "";
                if(list.length>0){
                    for ( var int = 0; int < list.length; int++) {
                        if(int<3){
                        var news = list[int];
                        var name = "";
                        if (news[4] != "") {
                            name = news[7] + "平台";
                        } else {
                            name = news[6];
                        }
                        var t = news[2].split(".")[0] + "small." + news[2].split(".")[1];
                        addTo += "<li id='china_mil'>";
                        addTo += "<input type='hidden' class='types' value='" + news[9] + "' /><img class='left' src='" + basePath + t + "' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'><input class='ppID' type='hidden' value='" + news[8] + "'/>";
                        addTo += "<div class='text'>";
                        addTo += "<h5>" + news[0] + "</h5>";
                        addTo += "<p></p>";
                        addTo += "</div>";
                        addTo += "<div class='bottom'><p>" + name + "</p><p>" + news[1].substring(0, 10) + "</p></div>";
                        addTo += "<div class='clearfix'></div>";
                        addTo += "</li>";
                    }}

                }else {
                    addTo = "<li><span>暂无新闻</span></li>";
                }
                $(".new").append(addTo);
            }
        });

    }


    var surl = basePath + "ea/lottery/sajax_ea_allActivityList.jspa?flag=1&ccompanyId="+ccompanyId;
    $.ajax({
        url : encodeURI(surl),
        type : "get",
        async : false,
        dataType : "json",
        success : function (data) {
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = new Array();
            if (pageForm != null && pageForm.recordCount> 0)
            {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                    var entity = list[0];
                    $("#signlist").append('<a href='+basePath+'ea/bonuspoints/ea_getSign.jspa?ccompanyId='+ccompanyId+'><li class="activity" style="float: none; font-size: 0.7rem;text-decoration: underline;line-height: 1.5rem;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;height: 1.5rem;">'+entity[3]+'</li>');
            }else {
                $("#signlist").append('<li><span>暂无签到活动</span></li>')
            }
        }
    });


    var signurl = basePath + "/ea/bonuspoints/sajax_getPrizeActivity.jspa?ccompanyId="+ccompanyId;
    $.ajax({
        url : encodeURI(signurl),
        type : "get",
        async : false,
        dataType : "json",
        success : function (data) {
            var mark = data.flag;
            var str = new Array();
            if (mark == "sign")
            {
                str.push('<li>');
                str.push('<a href="'+basePath+'ea/bonuspoints/ea_getSign.jspa?ccompanyId='+ccompanyId+'">');
                str.push('<img src="'+basePath+'images/WFJClient/PersonalJoining/companyHomepage/bp.png" alt="">');
                str.push('<p>签到</p>');
                str.push('</a></li>');
                $(".ico_grd li:last").before(str.join(""));

            }
        }
    });
})