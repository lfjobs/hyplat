$(function() {
    loaded();
    indexProCate();
    shopCartGoodNum();
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
    $(document).on("click",".promenu li .shopping_cart",function(e) {
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
    
    

    //点击选中
    $(document).on("click",".listWidth_last li",function(){
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
        codePID = $(this).attr("id");
         pagenumber = 0;
         pagecount = 0;
        $(".promenu").html("");
        loaded();
    })
    //点击查看产品详情
    $(document).on("click",".promenu li",function(){
        var ppid = $(this).attr("id");
        var  goodsid = $(this).find(".goodsid").val();
        var compnay_id = $(this).find(".compnay_id").val();
        var ccompany_id = $(this).find(".ccompany_id").val();
        document.location.href = basePath+"ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+compnay_id+"&ccompanyId="+ccompany_id+"&pricetype=1";

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
//产品
function loaded(){
    pagenumber += 1;
    var url = basePath+"ea/phl/sajax_ea_getIndexProduct.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        data : {
            "pageForm.pageNumber":pagenumber,
            codePID:codePID,
            companyID:companyID

        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;

            if(pageForm!=null){
                var htmlstr=new Array();
                var obj;
                pagecount = pageForm.pageCount;

                var term = "";
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if($("#"+obj[0]).length>0){
                        continue;
                    }
                    if(i==pageForm.list.length-1){
                        $(".last").removeClass("last");
                        htmlstr.push("<li class='clearfix last' id='"+obj[0]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' id='"+obj[0]+"'>");

                    }
                    htmlstr.push("<input type='hidden' class='goodsid' value='"+obj[1]+"'>");
                    htmlstr.push("<input type='hidden' class='compnay_id' value='"+obj[7]+"'>");
                    htmlstr.push("<input type='hidden' class='ccompany_id' value='"+obj[8]+"'>");
                    htmlstr.push("<input type='hidden' class='price' value='"+obj[5]+"'>");

                    htmlstr.push("<img src='"+basePath+obj[3]+"'  onerror=\"this.src=\'' + basePath + '/images/ea/production/forum/reportAnError.png\'\"/>");
					if (i == 0) {
								if (obj[2] != null && obj[2].length > 4) {
									term = obj[2].substring(0,4);
								} else {
									term = obj[2];
								}
								if (term != "") {
									$("#search").attr("placeholder", term);
								}
					}
                   
                    
                    htmlstr.push("<p>"+obj[2]+"</p>");
                    htmlstr.push("<p>"+(obj[6]==null?'':obj[6])+"</p>");
                    htmlstr.push("<p>"+obj[5]+"元");
                    if(obj[4]!=null){
                       htmlstr.push("/"+obj[4]);
                    }
                    htmlstr.push("<a class='shopping_cart'><img class='cart' src='"+basePath+"images/ea/collage/phl/shopping_cart.png'></a>");
                    htmlstr.push("</p>");
                    htmlstr.push("</li>");

                }
                $(".promenu").append(htmlstr.join(""));

                if(pagenumber==1&&pagecount>pagenumber){
                    t=setInterval("getHeight()", 200);
                }
            }


        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}

//首页产品分类
function indexProCate(){

    var url = basePath+"ea/phl/sajax_ea_getIndexProOrCate.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var catelist   = member.catelist;;


            if(catelist!=null){
                var htmlstr=new Array();
                var obj;
                for ( var i = 0; i <  catelist.length; i++) {
                    obj = catelist[i];
                    htmlstr.push("<li id='"+obj[1]+"'>");
                    htmlstr.push("<a href='javascript:;'>"+obj[0]+"</a>");
                    htmlstr.push("</li>");

                }
                $(".listWidth_last").append(htmlstr.join(""));
                //计算总列表宽度
                var listWidth_1=$(".listWidth_last li").length;
                var listWidth=0;
                for(var i=0;i<listWidth_1;i++){
                    listWidth+=$(".listWidth_last").children("li").eq(i).outerWidth(true);
                }
                $(".listWidth_last").width(listWidth+10);

            }

        },
        error:function(data){
            console.log("获取失败");
        }
    });

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

                var imgSrc=$(obj).parents("li").find("img").eq(0).attr("src");
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
            async: true,
            data: {
                "pfscShoppingCart.ppid": ppid,//产品id
                "pfscShoppingCart.standard": stardard,//规格
                "pfscShoppingCart.ftStr": ftStr,//选中副图
                "pfscShoppingCart.tjNum": tjNum,//添加数量
                "pfscShoppingCart.tjFlag": tjFlag
            },
            dataType: "json",
            success: function (data) {
            	 var json = eval('(' + data + ')');
                 if(json.login=="login"){

                     document.location.href = path
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

