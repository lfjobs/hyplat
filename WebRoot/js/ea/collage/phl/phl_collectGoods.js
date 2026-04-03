$(function() {

    loaded();
    loadedShops();
    //商品批量取消收藏
    $("#cacel-collect2").click(function(){
        var gcids = "";
        var gcnum = 0;
        $(".prourl .checkbox").find("input[type=checkbox]:checked").each(function () {
                 var gcid =  $(this).parents("li").find(".gcid").val();
                   gcids+=gcid+",";
                gcnum=gcnum+1;
                $(this).parents("li").remove();
        })

        if(gcnum==0){

            return false;
        }

        gcids = gcids.substring(0,gcids.length-1);
        var url = basePath+"ea/wfjshop/sajax_ea_batchCCGoods.jspa";
        $.ajax({
            url : url,
            type : "get",
            async : true,
            data : {
                "gcids":gcids
            },
            dataType : "json",
            success : function(data) {
                $(".shelter_layer .tip").text("取消收藏店铺成功");
                animate_yes();
                $("#goodsnum").text(Number($("#goodsnum").text())-Number(gcnum));
            },
            error:function(data){
                console.log("取消收藏商品失败");
            }
        });
    });

    //店铺批量取消收藏
    $("#cacel-collect").click(function(){
        var jfids = "";
        var jdnum = 0;
        $(".ul-02 .checkbox").find("input[type=checkbox]:checked").each(function () {
            var jfid =  $(this).parents("li").attr("id");
            jfids+=jfid+",";
            jdnum=jdnum+1;
            $(this).parents("li").remove();
        })
        if(jdnum==0){

            return false;
        }
        jfids = jfids.substring(0,jfids.length-1);
        var url = basePath+"ea/wfjshop/sajax_ea_batchCCShops.jspa";
        $.ajax({
            url : url,
            type : "get",
            async : true,
            data : {
                "jfids":jfids
            },
            dataType : "json",
            success : function(data) {
                $(".shelter_layer .tip").text("取消收藏店铺成功");
                animate_yes();
                $("#shopsnum").text(Number($("#shopsnum").text())-Number(jdnum));
            },
            error:function(data){
                console.log("取消收藏店铺失败");
            }
        });
    });
   //已加入购物车提示框animate_yes()
    function animate_yes() {
        $(".shelter_layer").show();
        setTimeout(function() {
            $(".shelter_layer").hide();
        }, 1000);
    }
    //购物车动画
    var animate_sd = 400; //动画速度
    $(document).on("click",".checkbox",function(event) {
        event.stopPropagation();
    });
    //查看产品详情
    $(document).on("click",".prourl li",function(e) {
        var ppid = $(this).attr("id");
        var goodsid = $(this).find(".goodsid").val();
        companyId = $(this).find(".companyid").val();
        ccompanyId = $(this).find(".ccompanyid").val();
        pricetype = $(this).find(".pricetype").val();

      document.location.href = basePath+"ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId+"&ccompanyId="+ccompanyId+"&pricetype="+pricetype;
    });
    //全选店铺
    $(document).on("click","#ckx-quanxuan",function() {

        if ( $("#ckx-quanxuan input").attr("data-ckx")  == 1){
            $("[name='dianpu']").prop("checked", true);
            $("#ckx-quanxuan input").attr("data-ckx","2");
            $("#ckx-quanxuan input").prop("checked", true);
        }else{
            $("[name='dianpu']").prop("checked", false);
            $("#ckx-quanxuan input").attr("data-ckx","1");
            $("#ckx-quanxuan input").prop("checked", false);
        }

    })
    //全选商品
    $(document).on("click","#ckx-quanxuan2",function() {
        // data-ckx
        if ( $("#ckx-quanxuan2 input").attr("data-ckx")  == 1){
            $("[name='shangpin']").prop("checked", true);
            $("#ckx-quanxuan2 input").attr("data-ckx","2");
            $("#ckx-quanxuan2 input").prop("checked", true);
        }else{
            $("[name='shangpin']").prop("checked", false);
            $("#ckx-quanxuan2 input").attr("data-ckx","1");
            $("#ckx-quanxuan2 input").prop("checked", false);
        }
    })

    //选择商品
    $(".content>div:first-of-type>a:first-of-type").click(function(){
        $(this).parent().find("a").removeClass("active");
        $(".content section").removeClass("sec-bianji");
        $(this).addClass("active");
        $(".content section").hide();
        $(".content section").eq($(this).index()).show();
        $(".div-bot-01").hide()
        $(".div-bot-02").hide()
        $("#a-bianji").text("编辑");
        $(".prourl").html("");
        if(tt!="") {
            clearInterval(tt);
        }
        pagenumber = 0;
        loaded();
    })
    //选择店铺
    $(".content>div:first-of-type>a:nth-of-type(2)").click(function(){
        $(".content section").removeClass("sec-bianji");
        $(this).parent().find("a").removeClass("active");
        $(this).addClass("active");
        $(".content section").hide();
        $(".content section").eq($(this).index()).show();
        $(".div-bot-01").hide()
        $(".div-bot-02").hide()
        $("#a-bianji").text("编辑");
        $(".ul-02").html("");
        if(t!="") {
            clearInterval(t);
        }
        pagenumber1 = 0;
        loadedShops();
    })
    //编辑
    $(".content>div:first-of-type>a:nth-of-type(3)").click(function(){

        if ($("#tab-sec .active").text().indexOf("商品") != -1) {

                if($(".prourl").find("li").length==0){

                        return false;
                }

        }else{
            if($(".ul-02").find("li").length==0){

                return false;
            }
        }

    if($("#a-bianji").text()=="编辑"){
            $(".content section").removeClass("sec-bianji");
            $(".content section").eq($(this).parent().find(".active").index()).addClass("sec-bianji");
            $(".div-bot-01").show();
            $(".div-bot-02").show();
            $("#a-bianji").text("完成");
        }else {
            $(".content section").removeClass("sec-bianji");
            $("#a-bianji").text("编辑");
            $(".div-bot-01").hide();
            $(".div-bot-02").hide();
        }


    })
});


function getHeight(){

    if($(".last").length>0){
        if($(".last").offset().top-$(document).scrollTop()<=$(window).height()){

            if(pagenumber<pagecount){
                loaded();
            }else{
                clearInterval(t);
            }
        }
    }

}

function getHeightShop(){

    if($(".last").length>0){
        if($(".last").offset().top-$(document).scrollTop()<=$(window).height()){

            if(pagenumber1<pagecount1){
                loadedShops();
            }else{
                clearInterval(tt);
            }
        }
    }

}
//商品
function loaded(){
    pagenumber += 1;
    if(pagenumber==1){
        $(".prourl").html("");
    }
    var parameter = $("#search").val();
    var url = basePath+"ea/wfjshop/sajax_ea_getCollectGoodsList.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        data : {
            "pageForm.pageNumber":pagenumber,
             parameter:parameter
        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;


            if(pageForm!=null){
                var htmlstr=new Array();
                var obj
                pagecount = pageForm.pageCount;
                if(pagenumber==1) {
                    var count = pageForm.recordCount;
                    $("#goodsnum").text(count);
                }
                $(".last").removeClass("last");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if($(".prourl li#"+obj[4]).length>0){
                        break;
                    }

                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li class='last clearfix'  id='"+obj[4]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' id='"+obj[4]+"'>");

                    }
                    htmlstr.push("<input type='hidden' class='goodsid' value='"+obj[5]+"' />");
                    htmlstr.push("<input type='hidden' class='companyid' value='"+obj[11]+"' />");
                    htmlstr.push("<input type='hidden' class='ccompanyid' value='"+obj[10]+"' />");

                    htmlstr.push("<input type='hidden' class='pricetype' value='"+obj[1]+"' />");

                    htmlstr.push("<input type='hidden' class='gcid' value='"+obj[6]+"' />");


                    htmlstr.push(" <div class='checkbox'><input type='checkbox' name='shangpin'></div>");


                    htmlstr.push("<p>");

                    htmlstr.push("<img src='" + basePath + obj[2]+"' onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"'/>");



                    htmlstr.push("</p>");
                    htmlstr.push("<p>"+obj[3]+"</p>");
                    htmlstr.push("<p>￥<i>"+obj[0]+"</i>");
                    if(obj[8]!=null){
                        htmlstr.push("/"+obj[8]);
                    }
                    htmlstr.push("</p>");
                    htmlstr.push("<p>"+(obj[9]==null?'':obj[9])+"</p>");
                    htmlstr.push("<p>销量"+obj[7]+"笔</p>");

                    htmlstr.push("</li>");


                }
                $(".prourl").append(htmlstr.join(""));

                if(pagenumber==1&&pagecount>pagenumber){
                    t=setInterval("getHeight()", 2000);
                }
            }else{
                if(pagenumber==1){
                  $(".zwsp").show();
                }

            }


        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}


//商家
//商家
function loadedShops(){
    pagenumber1 += 1;
    if(pagenumber1==1){
        $(".ul-02").html("");
    }
    var parameter = $("#search").val();
    var url = basePath+"ea/wfjshop/sajax_ea_getCollectShopList.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        data : {
            "pageForm.pageNumber":pagenumber1,
            parameter:parameter
        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;


            if(pageForm!=null){
                var htmlstr=new Array();
                var obj
                pagecount1 = pageForm.pageCount;
                if(pagenumber1==1) {
                    var count = pageForm.recordCount;
                    $("#shopsnum").text(count);
                }
                $(".last").removeClass("last");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if($(".ul-02 li#"+obj[0]).length>0){
                        break;
                    }
                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li class='last clearfix'  id='"+obj[0]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' id='"+obj[0]+"'>");

                    }
                    htmlstr.push("<a href='"+basePath+"/ea/industry/ea_getCompanyHome.jspa?ccompanyId="+obj[4]+"&industryType=&etype=&sc=web'>");
                    htmlstr.push("<div class='checkbox'>");
                    htmlstr.push("<input type='checkbox' name='dianpu'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<img src='"+basePath+obj[0]+"'  onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"'/>");

                    htmlstr.push("<p class='txt'>"+(obj[3]==null?'':obj[3])+"</p>");
                    htmlstr.push("<p class='txt'>"+obj[1]+"</p>");
                    htmlstr.push("<p class='txt'>"+(obj[2]==null?'':obj[2])+"</p>");
                    htmlstr.push("</a>");
                    htmlstr.push("</li>");




                }
                $(".ul-02").append(htmlstr.join(""));
                if($("#tab-sec .active").text().indexOf("店铺") != -1){

                    if(pagenumber1==1&&pagecount1>pagenumber1){
                        tt=setInterval("getHeightShop()", 2000);
                    }
                }

            }else{
                if(pagenumber==1){
                    $(".zwdp").show();
                }

            }


        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}
//条件查询加载
function search() {
    if($.trim($("#search").val())==""){

        return false;
    }
    pagecount = 0;
    pagenumber = 0;

    pagecount1 = 0;
    pagenumber1 = 0;
    if (t != "") {
        clearInterval(t);
    }

    if (tt != "") {
        clearInterval(tt);
    }


    if ($("#tab-sec .active").text().indexOf("商品") != -1) {

     $(".prourl").html("");
      loaded();
}else{
        $(".ul-02").html("");
        loadedShops();

    }
}


