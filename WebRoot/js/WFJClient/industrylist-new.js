$(function() {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    $(".overlay_text").find("span").text("正在加载，请稍候……");
    $(".overlay_text").show();
    $(".nest_bd").find(".classify_wrap").remove();
    $.ajax({
        cache: true,
        type: "POST",
        url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=3",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var beanList=member.beanList;

            if(beanList.length > 0){
                var htmls="<div class='classify_wrap'>";
                var numl=0;
                for (var i=0;i<beanList.length;i++){
                    var a = beanList[i];
                    var codeid=a[0];
                    var ans=a[2];
                    if(ans=="2"){
                        numl=numl+1;
                        htmls+="<div class='level_wrap'><div class='level_box'><i class='classify_ico list_ico_0"+numl+"'></i>";
                        htmls+="<div class='level_text'><div class='level_1'>"+a[3]+"<input type='hidden' class='oid' value='"+a[0]+"'/></div>";
                        htmls+="<div class='level_2'>";
                        for (var j=0;j<beanList.length;j++){
                            var b = beanList[j];
                            var codepid=b[1];
                            var bns=b[2];
                            if(codeid==codepid){
                                htmls+=b[3]+"/<input type='hidden' class='tid' value='"+b[0]+"'/>";
                            }
                        }
                        htmls+="</div></div><i class='fold_ico'></i></div><div class='level_fold'></div></div>";
                    }
                }
                htmls+="</div>";
                $(".nest_bd").append(htmls);
            }
        }
    });
    $(".nest_page").show();
    $(".overlay_text").hide();

    //加载三四级工种
    $(document).on("click",".level_box",function() {
        var ind =  $(this).find('.level_1').text();
        $(".overlay_text").find("span").text("正在加载，请稍候……");
        $(".overlay_text").show();
        var pid=$(this).find(".oid").val();
        var level_fold=$(this).parent().find(".level_fold");
        $(this).parent().find(".level_3").remove();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=5&pid="+pid,
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList=member.beanList;

                if(beanList.length > 0){
                    var htmls="";

                    for (var i=0;i<beanList.length;i++){
                        var a = beanList[i];
                        var codeid=a[0];
                        var ans=a[2];
                        if(ans=="4"){
                            var numl=0;
                            var claval="no_level_4";
                            htmls+="<div class='level_3 '><div class='level_3_tit '><i class='fold_level3'></i>"+a[3]+"<input type='hidden' class='tid' value='"+a[0]+"'/></div>";
                            htmls+="<div class='level_4 clearfix'>";
                            for (var j=0;j<beanList.length;j++){
                                var b = beanList[j];
                                var codepid=b[1];
                                var bns=b[2];
                                // if(bns=="5"){
                                //     if(codeid==codepid){
                                //         numl=numl+1;
                                //         htmls+="<div class='level_4_box'>"+b[3]+"<input type='hidden' class='fid' value='"+b[0]+"'/></div>";
                                //     }
                                // }
                            }
                            htmls+="</div>";
                            claval="";
                            htmls+="</div>";
                        }
                    }
                    level_fold.append(htmls);
                    level_fold.find(".level_3").each(function(){
                        var level_4=$(this).find(".level_4").html();
                        if(level_4==""){
                            $(this).addClass("no_level_4");
                            $(this).find(".level_4").remove();
                        }
                    });

                }
            }
        });
        if($(this).next().children().length == 0){
            map($(this).find(".level_1").text(),$(this).find(".level_2").text());
        }
        $(".level_4").each(function() {
            $(this).slideUp(200);
        });
        $(this).parent().find(".level_fold").slideToggle(200)
            .end().find(".fold_ico").toggleClass("fold_up")
            .end().siblings().find(".level_fold").slideUp(200)
            .end().find(".fold_ico").removeClass("fold_up");
        $(".fold_level3").each(function() {
            $(this).removeClass("fold_up");
        });
        $(".overlay_text").hide();
    });
    //添加第三级工种
    $(document).on("click",".level_3_tit",function() {
        var L_4 = $(this).parent().find(".level_4");
        if (L_4.length) {
            $(this).find(".fold_level3").toggleClass("fold_up").parent().parent().siblings().find(".fold_level3").removeClass("fold_up");
            L_4.slideToggle(200).parent().siblings().find(".level_4").slideUp(200);
        } else {
            $(".overlay_text").find("span").text("正在操作，请稍候……");
            $(".overlay_text").show();
            cwid="";
            var t = $(this).text();
            var level_1 =  $(this).parent().parent().prev().find('.level_1').text()
            var b = $(this).find(".tid").val();
            map(level_1,t);
            $(".nest_page").hide();
            packInit();
            $(".overlay_text").hide();
        }
    });

    //跳转公司列表
    function map (industry,industry1) {
        var url = "ea/industry/ea_getPlatform.jspa?industryType="+ industry1
                // + industry.substr(0,industry.length -4) + "/" + industry1;
        if(industry1.indexOf("/")>0){
            var arr =  industry1.substring(0,industry1.length-1).split("/")
            var industryType = "";
            for (var i = 0;i<arr.length;i++){
                industryType += industry.substr(0,industry.length -4)+"/"+arr[i]+",";

            }
            industryType =industryType.substring(0,industryType.length-1);
            url = "ea/industry/ea_getPlatform.jspa?industryType="+industryType;
        }else if(!industry1){
            if(industry.indexOf("联营平台")!=-1){
                industry = industry.substr(0,industry.length -4);
            }
            url = "ea/industry/ea_getPlatform.jspa?industryType="+industry;
        }
        document.location.href = url;
        //
        // $.ajax({
        //     url : encodeURI(url),
        //     type : "get",
        //     async : false,
        //     dataType : "json",
        //     success : function (data) {
        //         var member = eval("(" + data + ")");
        //         var industryRelation = member.industryRelation;
        //         if(industryRelation.platforReturn!=null || industryRelation.platforReturn!=""){
        //
        //         }else {
        //            var vurl = basePath + "ea/industry/ea_getAllCompanyList.jspa?industryType="
        //                 + industry.substr(0,industry.length -4) + "/" + industry1;
        //             document.location.href = vurl;
        //         }
        //     }});
    };

    // 搜索框模糊查询
    $("#img").click(function() {
        var industryType = $(".wfj_search").find("input").val();
        if (industryType == "请输入行业名称或公司名称进行查询") {
            industryType = "";
        }
        var surl = basePath
            + "ea/industry/ea_getMySelCompanyList.jspa?industryType="
            + industryType;
        document.location.href = surl;
    });

    //初始化折叠选择行业分类
    function packInit() {
        $(".level_fold").each(function() {
            $(this).slideUp(200);
        });
        $(".level_4").each(function() {
            $(this).slideUp(200);
        });
        $(".fold_ico,.fold_level3").each(function() {
            $(this).removeClass("fold_up");
        });
    }
});
