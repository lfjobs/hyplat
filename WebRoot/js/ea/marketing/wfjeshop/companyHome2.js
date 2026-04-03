var postype;
$(document).ready(function () {
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
        } else {
            var obj = document.getElementById("ret");
            obj.parentElement.setAttribute("href", basePath + "/ea/earth/ea_earthIndex.jspa");
        }
    } catch (e) {
        console.log("报错啦");
    }


    try {
        if (isAndroid == true) {
            Android.callgetLocal();//调用安卓接口
        } else if (isiOS == true) {
            var url = "func=" + 'calliosMapInfo';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    } catch (e) {
        $("#consultingRegistered").show();
    }


    jQuery("#qrcodeCanvas").qrcode({
        render: "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
        text: window.location.href,
        width: "100",               //二维码的宽度
        height: "100",              //二维码的高度
        background: "#ffffff",       //二维码的后景色
        foreground: "#000000",        //二维码的前景色
        src: $("#logo").attr("src")    //二维码中间的图片
    })
});
function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".erweima").offset().top < $(window).height()) {
        if (only == 0) {
            ajax1();
            only = 1;
        }
    }
}
function ajax() {
    surl1 = basePath + "ea/industry/sajax_ea_homepage.jspa";
    $.ajax({
        url: encodeURI(surl1),
        type: "post",
        data: {"ccompanyId": ccompanyId},
        dataType: "json",
        async: false,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var addTo = "";
            var Pct = 1;
            companyId = jsonresult.companyMessage.details[2];
            var setsubsidize = jsonresult.setSubsidize;
            if (setsubsidize != null) {
                Pct = setsubsidize.totalPct / 100 + 1;
            }
            if (jsonresult.jointOperation != null) {
                if (jsonresult.jointOperation.list != null && jsonresult.jointOperation.list.length > 0) {
                    addTo += "<!--报名联营商城-->";
                    addTo += "<div class='ind-shop'>";
                    addTo += "<div class='ban'>";
                    addTo += "<div class='txt-mil'>";
                    addTo += "<p>联营商城</p><p>MALL</p>";
                    addTo += "</div>";
//						addTo+="<a href='#;'><img src='"+basePath+"images/WFJClient/PersonalJoining/ban.png' alt='' width='100%'></a>";
                    addTo += "</div>";
                    addTo += "<div class='so_shop'>";
                    addTo += "<ul>";
                    $(jsonresult.jointOperation.list).each(function (i, dom) {
                        if (i <= 3) {
                            addTo += "<li onclick='Description(this)'>";

                            addTo += "<input type='hidden' value=" + this[1] + " class='ppID'>";
                            addTo += "<input type='hidden' value=" + this[4] + " class='goodsID'>"
                            addTo += "<input type='hidden' class='companyID' value='" + this[5] + "'/>";
                            addTo += "<input type='hidden' class='type' value='" + this[6] + "'/>	";
                            addTo += "<img src='" + basePath + this[3] + "' alt=''>";
                            if (this[10] != null && this[10] != "" && this[8] != null && this[8] != "") {
                                if (this[10] == '00') {//促销活动
                                    addTo += "<span class='cx'><i></i></span>";
                                    addTo += "<div class='txt'>";
                                    addTo += "<h4>" + this[0] + "</h4>";
                                    addTo += " <p>¥<span>" + (this[8] * Pct).toFixed(2) + "</span></p>";
                                    activeStateUpdate(this[1]);
                                }
                                if (this[10] == '01') {//特价活动
                                    addTo += "<span class='tj'><i></i></span>";
                                    addTo += "<div class='txt'>";
                                    addTo += "<h4>" + this[0] + "</h4>";
                                    addTo += " <p>¥<span>" + (this[8] * Pct).toFixed(2) + "</span></p>";
                                    activeStateUpdate(this[1]);
                                }
                            } else {
                                //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                                var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                                $.ajax({
                                    url: url,
                                    type: 'POST',
                                    data: {"ppid": this[1]},
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
                                if (this[9] != null && this[9] != "") {//vip活动
                                    addTo += "<span class='vip'><i></i></span>";
                                    addTo += "<div class='txt'>";
                                    addTo += "<h4>" + this[0] + "</h4>";
                                    addTo += " <p>¥<span>" + (this[9] * Pct).toFixed(2) + "</span></p>";
                                } else {
                                    //普通零售
                                    addTo += "<div class='txt'>";
                                    addTo += "<h4>" + this[0] + "</h4>";
                                    addTo += " <p>¥<span>" + (this[2] * Pct).toFixed(2) + "</span></p>";
                                }

                            }
                            addTo += "</div></li>";
                        }
                    });
                    addTo += "</div>";
                    addTo += "<div class='more'>";
                    addTo += "<a href='" + basePath + "ea/industry/ea_CompanyProducts.jspa?companyId=" + companyId + "&ccompanyId=" + ccompanyId + "&onlyCompany=" + companyname + "'>";
                    addTo += "<h4>查看更多 <i></i></h4>";
                    addTo += "</a>";
                    addTo += "</div>";
                    addTo += "</div>";
                }
            }
            if (jsonresult.press != null && jsonresult.press.length > 0) {
                addTo += "<!--新闻资讯-->";
                addTo += "<div class='ind-news'>";
                addTo += "<div class='txt-mil'>";
                addTo += "<p>新闻资讯</p><p>NEWS</p></div>";
                addTo += "<div class='news'>";
                $(jsonresult.press).each(function (i, dom) {
                    if (i <= 2) {
                        addTo += "<div class='china_mil' onclick='information(this)'>";
                        addTo += "<a href='#;'>";
                        var t = "";
                        if (this[2] != null) {
                            t = this[2].split(".")[0] + "small." + this[2].split(".")[1];
                        }
                        addTo += "<img src='" + basePath + t + "' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>";
                        addTo += "<div class='china_mil_txt'>";
                        addTo += "<h4>" + this[0] + "</h4>";
                        addTo += "<div><h3><span>发布者:</span>" + this[8] + "</h5><p>" + this[1] + "</p></div>";
                        addTo += "</div>";
                        addTo += "<div class='clearfix'></div>";
                        addTo += "</a>";
                        addTo += "<input type='hidden' class='goodsID' value='" + this[3] + "'/>";
                        addTo += "<input type='hidden' class='' value='" + this[4] + "'/>";
                        addTo += "<input type='hidden' class='search' value='" + this[6] + "'/>";
                        addTo += "<input type='hidden' class='informationPpid' value='" + this[7] + "'/>";
                        addTo += "<input type='hidden' class='miniSystemJudge' value='02'/>";
                        addTo += "</div>";
                    }
                });
                addTo += "<div class='more'>";
                addTo += "<a href='" + basePath + "/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=" + ccompanyId + "&type=web&miniSystemJudge=02'>";
                addTo += "<h4>查看更多 <i></i></h4>";
                addTo += "</a>";
                addTo += "</div>";
                addTo += "</div>";
                addTo += "</div>";
            }
            $(".ico_grd").after(addTo);
            getHeight();
        }
    });

};

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
function resource() {
    document.location.href = basePath + "/mobile/office/mobileoffice_toMobileLogin.jspa?sys=sys&companyId=" + companyId + "&flag=yd";
}

function ajax1() {
    surl1 = basePath + "ea/industry/sajax_ea_homepage1.jspa";
    $.ajax({
        url: encodeURI(surl1),
        type: "post",
        data: {"ccompanyId": ccompanyId},
        dataType: "json",
        async: false,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var addTo1 = "";
            //公司招聘
            if (jsonresult.attractEngage != null && jsonresult.attractEngage.length > 0) {
                addTo1 += "<!--公司招聘-->";
                addTo1 += "<div class='ind-job'>";
                addTo1 += "<div class='txt-mil'>";
                addTo1 += "<p>公司招聘</p><p>RECRUITMENT</p>";
                addTo1 += "</div>";
                addTo1 += "<div class=''>";
                $(jsonresult.attractEngage).each(function (i, dom) {
                    if (i <= 2) {
                        addTo1 += "<div class='new-page_mil' onclick='bringIn(this)'>";
                        addTo1 += "<h3>" + this[0] + "</h3><span>" + this[1] + "</span>";
                        addTo1 += "<div class='clearfix'></div>";
                        addTo1 += "<h4>" + this[2] + "</h4><p>" + this[3] + "</p>";
                        addTo1 += "<div class='clearfix'></div>";
                        addTo1 += "<div class='weizhi'><img src='" + basePath + "images/WFJClient/PersonalJoining/companyHomepage/ico-weizhi.png'><span>" + this[4] + "</span></div>";
                        addTo1 += "<div class='weizhi'><img src='" + basePath + "images/WFJClient/PersonalJoining/ico-xueli.png'><span>" + this[5] + "</span></div>";
                        addTo1 += "<div class='clearfix'></div>";
                        addTo1 += "<input type='hidden' class='rikey' value='" + this[6] + "'/>";
                        addTo1 += "<input type='hidden' class='riid' value='" + this[8] + "'/>";
                        addTo1 += "</div>";
                    }
                })
                addTo1 += "</div>";
                addTo1 += "<div class='more'>";
                addTo1 += "<a href='#;' onclick='more(this.name)' name='招聘'>";
                addTo1 += "<h4>查看更多 <i></i></h4>";
                addTo1 += "</a>";
                addTo1 += "</div></div>";
            }
            //联营招商
            if (jsonresult.canvassBusinessOrders != null) {
                if (jsonresult.canvassBusinessOrders.beans != null && jsonresult.canvassBusinessOrders.beans.length > 0) {
                    addTo1 += "<!--会员赚钱-->";
                    addTo1 += "<div class='ind-money'>";
                    addTo1 += "<div class='txt-mil' onclick='sbsile()'>";
                    addTo1 += "<p>一键商城</p>";
                    addTo1 += "<p>ATTRACT INVESTMENT</p>";
                    addTo1 += "</div>";
                    addTo1 += "<div class='business_d'>";
                    addTo1 += "<ul>";
                    $(jsonresult.canvassBusinessOrders.beans).each(function (i, dom) {
                        if (i <= 3) {
                            addTo1 += "<li onclick='orders(this)'>";
                            addTo1 += "<img src='" + basePath + this[3] + "'><p>" + this[0] + "</p>";
                            addTo1 += "<div class='clearfix'></div>";
                            addTo1 += "<input type='hidden' class='ppId' value='" + this[1] + "'/>";
                            addTo1 += "<input type='hidden' class='goodsID' value='" + this[5] + "'/>";
                            addTo1 += "<input type='hidden' class='companyId' value='" + this[6] + "'/>";
                            addTo1 += "</li>";
                            addTo1 += "<hr style='border-top: 1px solid #ddd;margin: 10px 0'>";
                        }
                    });
                    addTo1 += "</ul>";
                    addTo1 += "<div class='more'>";
                    addTo1 += "<a href='#;' onclick='more(this.name)' name= '招商' >";
                    addTo1 += "<h4>查看更多 <i></i></h4>";
                    addTo1 += "</a>";
                    addTo1 += "</div>";
                    addTo1 += "</div>";
                    addTo1 += "</div>";
                }
            }
            if (jsonresult.exhibition != null && jsonresult.exhibition.length > 0) {
                addTo1 += "<!-- 团队展示 -->";
                addTo1 += "<div class='tearm'>";
                addTo1 += "<div class='txt-mil'>";
                addTo1 += "<p>团队展示</p><p>TEARM SHOWED</p>";
                addTo1 += "</div>";
                addTo1 += "<ul class='team'>";
                $(jsonresult.exhibition).each(function (i, dom) {
                    if (i <= 2) {
                        addTo1 += "<li>";
                        addTo1 += "<a href='" + basePath + "/people/manage/ea_personalDetail.jspa?staff.staffID=" + this[1] + "&flag=com&ccompanyId=" + ccompanyId + "'>";
                        addTo1 += "<img src='" + basePath + this[2] + "' alt='' class='head' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>";
                        addTo1 += "<div class='txt'>";
                        addTo1 += "<h4><span>" + this[0] + "</span></h4>";
                        addTo1 += "<p>" + this[3] + "</p>";
                        addTo1 += "<p>" + this[5] + "</p>";
                        addTo1 += "</a>";
                        addTo1 += "<input type='hidden' class='staffid' value='" + this[1] + "'/>";
                        addTo1 += "<input type='hidden' class='companyid' value='" + this[4] + "'/>";
                        addTo1 += "</li>";
                    }
                });
                addTo1 += "<div class='more'>";
                addTo1 += "<a href='" + basePath + "/people/manage/ea_teamShow.jspa?flag=com&ccompanyId=" + ccompanyId + "'>";
                addTo1 += "<h4>查看更多 <i></i></h4>";
                addTo1 += "</a>";
                addTo1 += "</div>";
                addTo1 += "</ul>";
                addTo1 += "</div>";
            }
            if (jsonresult.abstruct != null && jsonresult.abstruct.length > 0) {
                addTo1 += "<!--公司简介-->";
                addTo1 += "<div class='ind-intro'>";
                addTo1 += "<div class='txt-mil'>";
                addTo1 += "<p>公司简介</p><p>INTRODUCTION</p>";
                addTo1 += "</div>";
                addTo1 += "<div class='news ind-news'>";
                $(jsonresult.abstruct).each(function (i, dom) {
                    if (i <= 2) {
                        addTo1 += "<div class='china_mil' onclick='information(this)'>";
                        addTo1 += "<a href='#;'>";
                        var t = "";
                        if (this[2] != null) {
                            t = this[2].split(".")[0] + "small." + this[2].split(".")[1];
                        }
                        addTo1 += "<img src='" + basePath + t + "' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>";
                        addTo1 += "<div class='china_mil_txt'>";
                        addTo1 += "<h4>" + this[0] + "</h4>";
                        addTo1 += "<div><h3><span>发布者:</span>" + this[8] + "</h5><p>" + this[1] + "</p></div>";
                        addTo1 += "</div>";
                        addTo1 += "<div class='clearfix'></div>";
                        addTo1 += "</a>";
                        addTo1 += "<input type='hidden' class='goodsID' value='" + this[3] + "'/>";
                        addTo1 += "<input type='hidden' class='' value='" + this[4] + "'/>";
                        addTo1 += "<input type='hidden' class='search' value='" + this[6] + "'/>";
                        addTo1 += "<input type='hidden' class='informationPpid' value='" + this[7] + "'/>";
                        addTo1 += "<input type='hidden' class='miniSystemJudge' value='00'/>";
                        addTo1 += "</div>";
                    }
                });
                addTo1 += "</div>";
                addTo1 += "<div class='more'>";
                addTo1 += "<a href='#;' onclick='more(this.name)' name= '简介'>";
                addTo1 += "<h4>查看更多 <i></i></h4>";
                addTo1 += "</a>";
                addTo1 += "</div>";
                addTo1 += "</div>";
            }

            if ($(".ind-news").length > 0) {
                $(".ind-news").after(addTo1);
            } else {
                if ($(".ind-shop").length > 0) {
                    $(".ind-shop").after(addTo1);
                } else {
                    $(".ico_grd").after(addTo1);
                }
            }
            $(".synopsis").attr("style", "text-overflow: ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 4;overflow: hidden; letter-spacing: 1px;");
            $(".synopsis").find("img").css("display", "none");
        }
    });
}

//查询商品详情
function Description(obj) {
    var parms = new Array();
    parms.push("ppid=" + $(obj).find(".ppID").val());
    parms.push("&goodsid=" + $(obj).find(".goodsID").val());
    parms.push("&companyId=" + $(obj).find(".companyID").val());
    parms.push("&ccompanyId=" + ccompanyId);
    var url = basePath + "ea/wfjshop/ea_doodsDetail.jspa?" + parms.join("");
    document.location.href = url;
}
//查询招聘详情
function bringIn(obj) {
    var riid = $(obj).find(".riid").val();
    var position = $(obj).find(".position").text();
    window.open(basePath + "ea/bidrecruit/ea_showPosdetail.jspa?riId=" + riid + "&position=" + position + "&companyId=" + companyId + "&ccompanyID=" + ccompanyId + "&search=" + companyname + "&back=" + 3, "_self");
}
//查询资讯详情
function information(obj) {
    var goodsID = $(obj).find(".goodsID").val();
    var informationPpid = $(obj).find(".informationPpid").val();
    var miniSystemJudge = $(obj).find(".miniSystemJudge").val();
    document.location.href = basePath + "ea/industry/ea_informationDetails.jspa?ppId=" + informationPpid + "&ccompanyId=" + ccompanyId + "&type=web" + "&miniSystemJudge=" + miniSystemJudge;
}
//查询招商详情
function orders(obj) {
    var ppId = $(obj).find(".ppId").val();
    var goodsID = $(obj).find(".goodsID").val();
    var companyId = $(obj).find(".companyId").val();
    document.location.href = basePath
        + "ea/wfjshop/ea_doodsDetail.jspa?ppid=" + ppId + "&goodsid=" + goodsID + "&companyId=" + companyId + "&ccompanyId=" + ccompanyId;
}
function more(obj) {
    switch (obj) {
        case "招聘":
            document.location.href = basePath + "ea/bidrecruit/ea_showCompanydetail.jspa?ccompanyID=" + ccompanyId + "&companyId=" + companyId + "&search=" + companyname + "&back=3";
            break;
        case "招商":
            document.location.href = basePath + "ea/wfjshop/ea_getpk.jspa?ccompanyId=" + ccompanyId;
            break;
        case "简介":
            document.location.href = basePath + "ea/wfjplatform/ea_platformNews.jspa?ccompanyId=" + ccompanyId + "&type=web&miniSystemJudge=00";
            break;
    }
}
//安卓，苹果返回
function retAndroid() {
    try {
        Android.callAndroidjianli();
    } catch (err) {
        $("#ret").removeAttr("onclick");
        $("#ret").attr("onclick", "javascript: window.history.go(-1);return false;");
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
function retIOS() {
    try {
        calProduct('');
    }
    catch (err) {
        $("#ret").removeAttr("onclick");

        $("#ret").attr("onclick", "javascript: window.history.go(-1);return false;");
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
function forum() {
    if (companyId == '' || companyId == null) {
        alert("该公司暂未开通论坛功能!")
    } else {
        if (tcc) {
            document.location.href = basePath + "ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID=" + ccompanyId;
        } else {
            alert("该公司暂未开通论坛功能!")
        }
    }
}

function sbsile() {
    document.location.href = basePath + "/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform";
}


function consultantSubmit(obj) {
    var name = $(obj).parents(".ipt_con").find(".consultantName").val();
    var phone = $(obj).parents(".ipt_con").find(".consultantPhone").val();
    var regular1 = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
    var regular2 = /(^13\d{9}$)|(^14)[5,7]\d{8}$|(^15[0,1,2,3,5,6,7,8,9]\d{8}$)|(^17)[6,7,8]\d{8}$|(^18\d{9}$)/g;
    if (!regular1.test(name)) {
        alert("名称格式不正确");
        return;
    }
    if (!regular2.test(phone)) {
        alert("手机格式不正确");
        return;
    }

    var url = basePath + "ea/industry/sajax_ea_ajaxAddConsultant.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "post",
        data: {
            "consultingRegistration.companyId": companyId,
            "consultingRegistration.consultantName": name,
            "consultingRegistration.consultantPhone": phone
        },
        dataType: "json",
        async: true,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var stuts = jsonresult.stuts;
            if (stuts == "0") {
                alert("提交成功")
            } else if (stuts == "1") {
                alert("提交失败")
            }
        }
    })
}
//联营商城
function shopCart(){
    try {
      var  posNum = Android.forAndroidDeviceId();
        if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);
        }
    }catch(error){
        if(($(window).width()==1080&&$(window).height()==1546)||($(window).width()==534&&$(window).height()==636)) {
            posNum = 123;
            postype = "01";

        }
    }
    if(posNum!=null&&posNum!="") {
        document.location.href = basePath + "ea/industry/ea_CompanyProducts.jspa?ccompanyId="+ccompanyId+"&pos="+pos+"&posNum="+posNum+"&postype="+postype;
    }else{
        document.location.href = basePath + "ea/industry/ea_CompanyProducts.jspa?ccompanyId="+ccompanyId+"&pos="+pos;

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
            postype = m.postype;
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