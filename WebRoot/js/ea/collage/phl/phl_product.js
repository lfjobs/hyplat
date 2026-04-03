$(function() {
    $(".prourl").parents().scroll(function () {
        var Height = $(window).height();
        var scroll = $(".prourl").parents().scrollTop(); //滚动高度

        var Top = $(".last").offset().top; //元素距离顶部距离

        console.log(Top + '  ' + scroll);
        if (Top - Height - scroll <= 20) {
            if (pagenumber < pagecount) {
                loaded();
            }
        }
    })
    shopCartGoodNum();

    
    if(hotID!=null&&hotID!=""){
    	
    	hotSearch(hotID);
    }else{
    	 if(goodsName!=""||cateName!="") {
             if (cateName != "") {
             cateCrit = "pcf" + cateCrit;
            }

    	        loaded();
    	    }else{
             loaded();
         }
    }

    //查看产品详情
    $(document).on("click",".prourl li",function(e) {
               var ppid = $(this).attr("id");
               var goodsid = $(this).find(".goodsid").val();
               companyId = $(this).find(".companyid").val();
               ccompanyId = $(this).find(".ccompanyid").val();


        document.location.href = basePath+"ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId+"&ccompanyId="+ccompanyId+"&pricetype=1";
    });




//选择地区
    $(".content>div:first-of-type>a:first-of-type").click(function(){
        position();
        $(this).parent().find("a").removeClass("active");
        $(this).addClass("active");
        if($("#position").is(":hidden")){
            provice();
            $(".content>section:first-of-type").hide();
            $("#category").hide();
            $("#position").show();
            $(".content>div:first-of-type a:first-of-type img").addClass("active");
            $(".content>div:first-of-type a:nth-of-type(2) img").removeClass("active");
        }else{
            $(".content>section:first-of-type").show();
            $(".content>div:first-of-type a:first-of-type img").removeClass("active");
            $("#position").hide();
        }
    })
    //选择品类
    $(".content>div:first-of-type>a:nth-of-type(2)").click(function(){
        $(this).parent().find("a").removeClass("active");
        $(this).addClass("active");
        if($("#category").is(":hidden")){
            leftProCate();
            $(".content>section:first-of-type").hide();
            $(".content>div:first-of-type a:first-of-type img").removeClass("active");
            $(".content>div:first-of-type a:nth-of-type(2) img").addClass("active");
            $("#position").hide();
            $("#category").show();
        }else{
            $(".content>section:first-of-type").show();
            $(".content>div:first-of-type a:nth-of-type(2) img").removeClass("active");
            $("#category").hide();
        }
    })
    //选择排序
    $(".content>div:first-of-type>a:nth-of-type(3)").click(function(){
        $(this).parent().find("a").removeClass("active");
        $(this).addClass("active");
        $(".content>div:first-of-type a:first-of-type img").removeClass("active");
        $(".content>div:first-of-type a:nth-of-type(2) img").removeClass("active");
        $(".content>section:first-of-type").show();
        $("#category").hide();
        $("#position").hide();

    })
    //选择地区省份选中状态
    $(document).on("click","#position>menu:first-of-type>li",function(){
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
        if($(this).attr("id")=="qg"){
        	$(".content>div:first-of-type>a:first-of-type span").text($(this).text());
            $(".content>div:first-of-type a:first-of-type img").removeClass("active");
            $(".content>section:first-of-type").show();
            $("#position").hide();
            placeCrit = "";
            search();
        }
        city($(this).attr("id"));
    })
    //选择品货选中状态
    $(document).on("click","#category menu:first-of-type>li",function(){
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
        if($(this).text()=="全部"){
              $(".content>div:first-of-type>a:nth-of-type(2) span").text("品类");
              $(".content>section:first-of-type").show();
              $("#category").hide();
              $(".content>div:first-of-type a:nth-of-type(2) img").removeClass("active");
              cateCrit = "";
              search();
        }else{
        	
        	addRecentView($(this).attr("id"),$(this).text());
        }
        rightProCate($(this).attr("id"));
    })
    //选好地区关闭弹框
    $(document).on("click","#position>p>span,#position>menu:last-of-type li",function(){
        $(".content>div:first-of-type>a:first-of-type span").text($(this).text());
        $(".content>div:first-of-type a:first-of-type img").removeClass("active");
        $(".content>section:first-of-type").show();
        $("#position").hide();
        placeCrit = $(".provicem").find(".active").text()+$(this).text();
        search();
    })
    //选好品货关闭弹框
    $(document).on("click","#category>menu:last-of-type>li ul li,#category>menu:last-of-type>li h3",function(){
        $(".content>div:first-of-type>a:nth-of-type(2) span").text($(this).text())
        $(".content>section:first-of-type").show();
        $("#category").hide();
        $(".content>div:first-of-type a:nth-of-type(2) img").removeClass("active");
        cateCrit = $(this).attr("class");
        search();
    })

    //
    if(cateCrit!=""){
    	if(cateName!=""){
        $(".content>div:first-of-type>a:nth-of-type(2) span").text(cateName);
        $(".pl").addClass("active");
        search();
    	}
    }
    if(goodsName!=""){
        search();

    }
   //全部火拼
    $(document).on("click","#category>menu:last-of-type>li p",function(){
        var txt=$(this).parents("#category").find("li.active").text();
        $(".content>div:first-of-type>a:nth-of-type(2) span").text(txt)
        $(".content>section:first-of-type").show();
        $("#category").hide();
        $(".content>div:first-of-type a:nth-of-type(2) img").removeClass("active");
        cateCrit = "pcf"+$(this).parents("#category").find("li.active").attr("id");
        search();
    })
    //搜索按钮还原进入页面状态
    $("input[type='search']").click(function(){
        $(".content>section:first-of-type").show();
        $(".content>div:first-of-type a:first-of-type img").removeClass("active");
        $(".content>div:first-of-type a:nth-of-type(2) img").removeClass("active");
        $("#position").hide();
        $("#category").hide();
        document.location.href = basePath+"ea/phlpro/ea_jumpSearch.jspa?ccompanyID="+ccompanyID;
    })

    //选择排序方式
    $(".input_select").click(function(e) {
        var ul = $(".dropdown").find("ul");
        if(ul.css("display") == "none") {
            $(".box").show();
            ul.slideDown("fast");
        } else {
            $(".box").hide();
            ul.slideUp("fast");
        }
        e.stopPropagation();
    });
    $(".dropdown ul li").click(function(){
        var crit = $(this).attr("class");
        disCrit = "";
        saleCrit = "";
        priceCrit = "";
        if(crit=="disCrit"){
            disCrit = crit;
        }else if(crit=="saleCrit"){
            saleCrit = crit;
        }else{
           priceCrit = crit;

        }
        var txt = $(this).text();
        $(".input_select").text(txt);
        $(".dropdown ul").hide();
        search();
    });


    $("html").not(".dropdown,.input_select").click(function(){
        $(".dropdown ul").hide();
    })
    //选择排序组织冒泡
    $("html").not(".dropdown ul,.input_select").click(function(){
        $(".dropdown").parent().slideUp("fast");
    })
    //切换排序
    $("#switch").click(function(){
        if($(".content section:first-of-type ul").is(".one")){
            $(".content section:first-of-type ul").removeClass("one");
            $(".content section:first-of-type ul").addClass("two");
            $(this).attr("src",basePath+"images/ea/collage/phl/switch_2.png");
            //判断商品数量
            var bor_num=$(".content section:first-of-type ul li").length;
            if(bor_num%2!==0){
                $(".two li:last-of-type").css("border","0");
            }
        }else{
            $(".content section:first-of-type ul").addClass("one");
            $(".content section:first-of-type ul").removeClass("two");
            $(this).attr("src",basePath+"images/ea/collage/phl/switch_1.png");
            $(".one li:last-of-type").css("border-bottom","0.025rem solid #f4f4f4");
        }
    })



    //已加入购物车提示框animate_yes()
    function animate_yes() {
        $(".shelter_layer").show();
        setTimeout(function() {
            $(".shelter_layer").hide();
        }, 1000);
    }
    //购物车动画
    var animate_sd = 400; //动画速度
    //出现规格
    $(document).on("click",".prourl li .shopping_cart",function(e) {
        e.stopPropagation();

        $(".selstate").text("请选择");

        ppid = $(this).parents("li").attr("id");
        var goodsid = $(this).parents("li").find(".goodsid").val();
        var price = $(this).parents("li").find(".price").val();
       getProAttriList(goodsid,ppid,price,$(this));



    })
    var pecifications_hei = $(".box_per").outerHeight(true) + 40; //计算规格元素高度
    $(".box_per").css("bottom", -pecifications_hei + "px"); //隐藏规格
    $(".pecifications").hide();
    //取消规格
    $(".pecifications_del").click(function() {
        $(".box_per").animate({
            bottom: -pecifications_hei
        }, animate_sd, function() {
            $(".pecifications").hide();
        });
    })
    //规格确定
    $("#sure").click(function() {

        var xel = $(".xel").text();
        var tip = "请选择";
        if(xel.indexOf("请选择")!=-1){
            if(!$(".selsize").is(':hidden')) {
                tip+=" "+$(".selsize").text();
            }
            if(!$(".selcolor").is(':hidden')) {
                tip+=" "+$(".selcolor").text();
            }
           $(".warn_layer .cons").text(tip);
            $(".warn_layer").show();
            setTimeout(function () {
                $(".warn_layer").hide();
            },1800)

            return false;
        }


        $(".box_per").animate({
            bottom: -pecifications_hei
        }, animate_sd, function() {
            $(".pecifications").hide();
        });
        stardard = $(".selsize").text()+" "+$(".selcolor").text();
        stardard =  $.trim(stardard);
        shuliang = $(".purchase_quantity").val();
        var img = $("#img_src").attr("src").replace(basePath,"");
        ajaxShopcart(ppid,stardard,img,shuliang);

        var shopNum = $(".purchase_quantity").val() * 1;
        $("#num_shop").text(Number($("#num_shop").text())+shopNum);
        animate_yes();
    })
    //规格选中添加样式
    $(".choice_01").on("click", "p", function() {
        $(".selsize").text($(this).text());
        if(hcolor!=""&&$(".selcolor").text()==hcolor){
            $(".selsize").hide();
        }else{
            $(".selcolor").show();
            $(".selsize").show();
            $(".selstate").text("已选择：");
        }

        $(".choice_01 p").removeClass("active_cm");
        $(this).addClass("active_cm");
    })

    //点击选择颜色
    $(".choice_02").on("click", "p", function() {
        var img = $(this).find(".tp").text();
        if(img!=""){
            $("#img_src").attr("src",basePath+img);
        }
        $(".selcolor").text($(this).find(".at").text());
        if(hsize!=""&&$(".selsize").text()==hsize){
            $(".selcolor").hide();
        }else{
            $(".selcolor").show();
            $(".selsize").show();
            $(".selstate").text("已选择：");
        }

        $(".choice_02 p").removeClass("active_ys");
        $(this).addClass("active_ys");



    })
    //数量增加
    $(".Specifications_add").click(function() {
        var num = $(this).siblings(".purchase_quantity").val();
        if(num < 99) {
            num++;
        } else {
            num = 99;
        }
        $(this).siblings(".purchase_quantity").val(num);
    })
    //数量减少
    $(".Specifications_reduce").click(function() {
        var num = $(this).siblings(".purchase_quantity").val();
        if(num > 1) {
            num--;
        } else {
            num = 1;
        }
        $(this).siblings(".purchase_quantity").val(num);
    })



});





//左边分类
function leftProCate(){
    if($.trim($(".leftmenus").html()).length!=0){
        return false;
    }
    var url = basePath+"ea/phl/sajax_ea_getProCate.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            codePID:"scode20190415raqvqk3uvs0000000762"
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var catelist   = member.catelist;

            var htmlstr=new Array();
           
            htmlstr.push("<li>全部</li>");
            var obj;
            if(catelist!=null){
                for ( var i = 0; i <  catelist.length; i++) {

                    obj = catelist[i];
                    if(i==0){
                        htmlstr.push("<li id='"+obj[1]+"' class='active'>");

                    }else{
                        htmlstr.push("<li id='"+obj[1]+"'>");
                    }
                    htmlstr.push(obj[0]);
                    htmlstr.push("</li>");


                }
                $(".leftmenus").html(htmlstr.join(""));
                var codePID = $(".leftmenus").find(".active").attr("id");
                rightProCate(codePID);

            }

        },
        error:function(data){
            console.log("获取左边分类失败");
        }
    });

}

//右边分类
function rightProCate(codePID){
    var url = basePath+"ea/phl/sajax_ea_getProCate.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            codePID:codePID
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var catelist   = member.catelist;
            if(catelist!=null){
                var htmlstr=new Array();
                htmlstr.push("<li>");
                htmlstr.push("<p class='"+codePID+"'>全部</p>");
                htmlstr.push("</li>");
                htmlstr.push("<li>");
                htmlstr.push("<ul class='clearfix'>");
                var obj;
                for ( var i = 0; i <  catelist.length; i++) {

                    obj = catelist[i];

                    htmlstr.push("<li class='"+obj[1]+"'>"+obj[0]+"</li>");


                }
                htmlstr.push("</ul></li>");
                $(".rightmenus").html(htmlstr.join(""));

            }

        },
        error:function(data){
            console.log("获取右边分类失败");
        }
    });

}

//省份
function provice(){
    if( $.trim($(".provicem").html()).length!=0){
        return false;
    }
    var url = basePath+"ea/phlbusi/sajax_ea_getCDistricts.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var distinctlist   = member.distinctlist;


    if(distinctlist!=null){
                var htmlstr=new Array();
                var obj;
                     htmlstr.push("<li id='qg'>");
                     htmlstr.push("全国");
                     htmlstr.push("</li>");
                for ( var i = 0; i <  distinctlist.length; i++) {

                    obj = distinctlist[i];
                    if(i==0){
                        htmlstr.push("<li id='"+obj.districtID+"' class='active'>");

                    }else{
                        htmlstr.push("<li id='"+obj.districtID+"'>");
                    }
                    htmlstr.push(obj.districtName);
                    htmlstr.push("</li>");


                }
                $(".provicem").html(htmlstr.join(""));
                var districtPID = $(".provicem").find(".active").attr("id");
                city(districtPID);

            }

        },
        error:function(data){
            console.log("获取省份失败");
        }
    });

}


//城市
function city(districtPID){
	

    var url = basePath+"ea/phlbusi/sajax_ea_getCityDistricts.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            districtPID:districtPID
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var distinctlist   = member.distinctlist;
            if(distinctlist!=null){
                var htmlstr=new Array();
                var obj;
                for ( var i = 0; i <  distinctlist.length; i++) {

                    obj = distinctlist[i];
                    htmlstr.push("<li id='"+obj.districtID+"'>");
                    htmlstr.push(obj.districtName);
                    htmlstr.push("</li>");

                }
                $(".citym").html(htmlstr.join(""));

            }


        },
        error:function(data){
            console.log("获取城市失败");
        }
    });

}
// function getHeight(){
//
//     if($(".last").length>0){
//         if($(".last").offset().top-$(document).scrollTop()<=$(window).height()){
//
//             if(pagenumber<pagecount){
//                 loaded();
//             }else{
//                 clearInterval(t);
//             }
//         }
//     }
//
// }
//商家
function loaded(){
    pagenumber += 1;
    if(pagenumber==1){
        $(".prourl").html("");
    }
    var url = basePath+"ea/phlpro/sajax_ea_findSmallProduct.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        data : {
            "pageForm.pageNumber":pagenumber,
            ccompanyID:ccompanyID,
            goodsName:goodsName,
            placeCrit:placeCrit,
            cateCrit:cateCrit,
            disCrit:disCrit,
            saleCrit:saleCrit,
            priceCrit:priceCrit
        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;


            if(pageForm!=null){
                var htmlstr=new Array();
                var obj
                pagecount = pageForm.pageCount;

                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if($("#"+obj[0]).length>0){
                        continue;
                    }

                    if(i==pageForm.list.length-1){
                        $(".last").removeClass("last");
                        htmlstr.push("<li class='last clearfix'  id='"+obj[0]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' id='"+obj[0]+"'>");

                    }
                    htmlstr.push("<input type='hidden' class='goodsid' value='"+obj[1]+"' />");
                    htmlstr.push("<input type='hidden' class='companyid' value='"+obj[7]+"' />");
                    htmlstr.push("<input type='hidden' class='ccompanyid' value='"+obj[8]+"' />");
                    htmlstr.push("<input type='hidden' class='price' value='"+obj[5]+"' />");




                    htmlstr.push("<p>");

                     htmlstr.push("<img src='" + basePath + obj[3]+"' onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"'/>");


                  
                    htmlstr.push("</p>");
                    htmlstr.push("<p>"+obj[2]+"</p>");
                    htmlstr.push("<p>￥<i>"+obj[5]+"</i>");
                    if(obj[4]!=null){
                         htmlstr.push("/"+obj[4]);
                    }
                    htmlstr.push("</p>");
                    htmlstr.push("<p>"+(obj[6]==null?'':obj[6])+"</p>");
                    htmlstr.push("<p>销量"+obj[9]+"笔</p>");
                    htmlstr.push("<a href='#' class='shopping_cart'>");
                    htmlstr.push("<img src='"+basePath+"images/ea/collage/phl/shopping_cart.png'/>");
                    htmlstr.push("</a>");
                    htmlstr.push("</li>");


                }
                $(".prourl").append(htmlstr.join(""));

                // if(pagenumber==1&&pagecount>pagenumber){
                //     t=setInterval("getHeight()", 200);
                // }
            }


        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}
//条件查询加载
function search(){
    pagecount = 0;
    pagenumber = 0;
    // if(t!="") {
    //     clearInterval(t);
    // }
    $(".busiul").html("");
     loaded();
}
function getProAttriList(goodsid, ppid,price,obj) {
    var url = basePath + "ea/assicode/sajax_ea_getProAttriList.jspa?d=" + new Date();
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            goodsid: goodsid
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var color = member.color;
            var size = member.size;
            var listsize = member.listsize;
            var listcolor = member.listcolor;
            if (size != "" || color != "") {

                if (size != "") {
                    hsize = size;
                    $(".selsize").text(size);
                    $(".selsize").show();
                    $(".size").find("h4").text(size);
                    var sizearray = new Array();
                    for (var i = 0; i < listsize.length; i++) {
                        sizearray.push("<p>" + listsize[i].attrivalue + "</p>");

                    }
                    $(".psize").html(sizearray.join(""));

                }
                if (color != "") {
                    hcolor = color;
                    $(".selcolor").text(color);
                    $(".selcolor").show();
                    $(".color").find("h4").text(color);
                    var colorarray = new Array();
                    for (var i = 0; i < listcolor.length; i++) {
                        colorarray.push("<p><span class='at'>" + listcolor[i].attrivalue + "</span><span class='tp' style='display: none;'>" + listcolor[i].imgurl + "</span></p>");

                    }
                    $(".pcolor").html(colorarray.join(""));

                }
                $(".purchase_quantity").val(1);

                var imgSrc=$(obj).parents("li").children("p").eq(0).find("img").attr("src");
                $("#img_src").attr("src",imgSrc);
                $(".pecifications").show();
                $(".box_per").animate({
                    bottom: 0
                }, 400);


                //价格
                $("#price").text("￥"+price);

            }else{
                $(".shelter_layer").show();
                setTimeout(function () {
                    $(".shelter_layer").hide();
                },1000);
                $("#num_shop").text(Number($("#num_shop").text())+1);
                stardard =  "默认规格";
                shuliang = "1";
                ajaxShopcart(ppid,stardard,"",shuliang);
            }



        },
        error: function () {
            alert("获取销售属性失败");
        }
    });

}

//加入购物车
function ajaxShopcart(ppid,stardard,ftStr,tjNum){
	   var tjFlag = 0;
	      if(stardard!="默认规格"){
	    	  tjFlag = 1;
	      }

        var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxAddShoppingCart.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "pfscShoppingCart.ppid": ppid,//产品id
                "pfscShoppingCart.standard": stardard,//规格
                "pfscShoppingCart.ftStr": ftStr,//选中副图
                "pfscShoppingCart.tjNum": tjNum,//添加数量
                "pfscShoppingCart.tjFlag": tjFlag
            },
            dataType: "json",
            success: function (data) {
            	 var member = eval("(" + data + ")");
            	 var nologin = member.nologin;
                 if(nologin=="nologin"){

                     document.location.href = basePath
                         + "page/WFJClient/NewLogin.jsp?loginPage=login";
                     return;

                 }
            },
            error: function (data) {
                alert("数据获取失败！");
            }
        });

}


//查询购物车商品数目
function shopCartGoodNum(){
    var url=basePath+"/ea/phlpro/sajax_ea_shopPFCartGoodNum.jspa?d="+new Date();
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var goodNum = member.goodNum;
            $("#num_shop").text(goodNum);
        },
        error: function(){
            console.log("查询购物车商品数失败");
        }
    });
}
function calliosMapInfo(name){
    if(name!="-1"){
        var a = name.split(",");
      var  city = a[0];//所在城市
      var  eastLongitude = a[1];//东经
      var  northLatitude = a[2];//北纬
        $("#city").text(city);
    }else{
    	 $("#city").text("未知");
    }
}

//定位
function position(){

    try {
    	   var u = window.navigator.userAgent;
    	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    	    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    	    if (isAndroid == true) {
             
                var collection = Android.callgetLocal();//调用安卓接口
                if(collection!="-1"){
                    var a = collection.split(",");
                  var  city = a[0];//所在城市
                 var   eastLongitude = a[1];//东经
                 var   northLatitude = a[2];//北纬
               
                    $("#city").text(city);
                }else{
                    $("#city").text("未知");
                }
            } else if (isiOS == true) {
                console.log("IOS");
                var url= "func=" + 'calliosMapInfo';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
    } catch (e) {
       
    }
}
//热门分类查询
function hotSearch(hotID){
    $(".content>div:first-of-type>a:nth-of-type(2) span").text(hotValue);
    $(".content>section:first-of-type").show();
    $(".pl").addClass("active");
    cateCrit = "pcf"+hotID;
    search();
	
}

//添加最近看过
function addRecentView(codeID,codeValue){
	
    var url = basePath+"ea/phl/sajax_ea_addRecentView.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
        	ccompanyID:ccompanyID,
        	codeID:codeID,
        	codeValue:codeValue
        },
        success : function(data) {
        	console.log("添加最新看过成功");

        },
        error:function(data){
            console.log("添加最新看过失败");
        }
    });
	
}
