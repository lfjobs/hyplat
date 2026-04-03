$(document).ready(function() {

    //一级行业
   ajax();

    //loaded (industryType,companyname);
    loaded (industryType);



    $(document).on("click",".Expo_con ul li",function(){
        var ccompanyid = $(this).attr("data-ccompanyid");
        var url = basePath + "/ea/newpcend/ea_companyWebsite.jspa?titleJudge=00&ccompanyId="+ccompanyid;
        window.open(url);
    })




})

//一级行业
function ajax() {
    var url = basePath + "/ea/industry/sajax_ea_getIndustry.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        async : false,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var list = member.industryList;
            if (null == list) {
                return;
            } else {
                var htl = new Array();
                htl.push("<li data-codeID='' class='active'>全部分类</li>");
                for (var i = 0; i < list.length; i++) {
                    htl.push("<li data-codeID='"+list[i].codeID+"'>" + list[i].codeValue+ "</li>");
                }
                $(".shop-industry_1 .ind_con ul").html(htl.join(""));
            }
        }
    });
}

//二级行业
function getMoreIndustry(codepid,pagenumber) {
    var url = basePath + "/ea/industry/sajax_ea_getAjaxIndustry.jspa?codePID="+codepid;
    $.ajax({
        url : encodeURI(url),
        type : "post",
        async : false,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pagenumber
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var list = pageForm.list;
            var number = pageForm.pageNumber;
            var count = pageForm.pageCount;
            if (pageForm == null) {
                return;
            } else {
                for (var i = 0; i < list.length; i++) {
                    cmt.push("<li data-codeID='"+list[i].codeID+"'>" + list[i].codeValue+ "</li>");
                }
                if(number<count){
                    getMoreIndustry(codepid,number+1);
                }
                $(".shop-industry_2 .ind_con ul").html(cmt.join(""));
            }
        }
    });
}

function dianji() {
    loaded (industryType);
}

//上一步
function lastStep(){
    if(pagenumber>1){
        pagenumber = parseInt(pagenumber)-1;
        loaded(industryType);
    }
}
//下一步
function nextStep(){
    if(pagenumber<pagecount){
        pagenumber = parseInt(pagenumber)+1;
        loaded(industryType);
    }
}
//选取
function choose(obj){
    pagenumber = $(obj).text();
    loaded(industryType);
}



function loaded (industryType) {
    var companyAddr = "";
    var companyname = $(".sousuo").val();
    var url=basePath+"/ea/industry/sajax_ea_getAjax.jspa?industryType="+industryType+"&companyAddr="+companyAddr+"&search="+companyname;
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pagenumber,
            "accuracy":"",
            "dimension":""
        },
        success : function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var companystr = new Array();
            $(".Expo_con ul").empty();
            $(".page_my").empty();
            if(pageForm!=null&&pageForm.recordCount>0){
                var companylist=pageForm.list;
                pagecount=pageForm.pageCount;
                pagenumber=pageForm.pageNumber;
                $(pageForm.list).each(function(i, dom) {
                    companystr.push("<li data-ccompanyid='"+this[5]+"'>");
                    companystr.push("<img src='"+basePath+(this[4]!=null?this[4]:acquiesceLoGo)+"'>");
                    companystr.push("<div class='text'>");
                    companystr.push("<h5>"+this[6]+"</h5>");
                    if(this[7]==null || this[7]=="/"){
                        companystr.push("<p></p>");
                    }else if(this[7].substr(this[7].length-1,1)=="/"){
                        companystr.push("<p>"+this[7].substring(0,this[7].length-1)+"</p>");
                    }else{
                        companystr.push("<p>"+this[7]+"</p>");
                    }

                    companystr.push("</div>");
                    companystr.push("</li>");
                })
                $(".Expo_con ul").append(companystr.join(""));

                var paging = [];
                paging.push("<button style='float: left;' onclick='lastStep()'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-left.png'></button><ul>");
                for ( var i = 1; i <= pagecount; i++) {
                    if(pagenumber==i){
                        paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
                    }else{
                        if(i==1||i==pagecount||(i>=pagenumber-2&&i<=pagenumber+2)){
                            paging.push("<li onclick='choose(this)'>"+i+"</li>");
                        }else if(i!=1&&i!=pagecount&&(i==pagenumber-3||i==pagenumber+3)){
                            paging.push("<li style='border:none;background-color:white;' class='point'><span>...</span></li>");
                        }
                    }
                }
                paging.push("</ul><button style='float: right;' onclick='nextStep()'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-right.png'></button>");
                $(".page_my").append(paging.join(""));
                var nub = $(".page_my ul li").length;
                $(".page_my").css("width",80+40*nub+"px");
                $(".page_my ul").css("width","auto");
            }
        },
        error:function(data){
            alert("获取项目失败");
        }
    });
}
