$(function(){
    var tt = "";
    $(".ul_list_sp").scroll(function () {
        if ($(".last").length > 0) {
            if ($(".last").offset().top <= $(window)
                    .height()) {
                if (pageNumber < pageCount) {
                    productCate(codeID);
                }
            }
        }
    });
    /*交接班*/
    /*交接班登出*/
    $(".alert_address .btn input").click(function () {
        $(".alert_hand-pd_").show();
        $(".alert_hand-pd-1").show();
    });
    /*交接班登录弹框关闭*/
    $(".alert_hand img").click(function () {
        $(".alert_hand_").hide();
        $("#softkey").html("");
    });
    /*交接班完成打印关闭*/
    $(".alert_hand-pd img").click(function () {
        $(".alert_hand-pd_").hide();
        $("#softkey").html("");
    });

    //直接退出
    $(".exit").click(function(){

        document.location.href = basePath+"ea/sm/ea_backLogin.jspa?cs=1";

    });

    //再打印
    $(".printAgain").click(function(){
        var times = $("#printNum").val();

        if(times==""||times=="0"){
            times=1;
        }else{
            if(Number(times)>5){
                var shot = showTime();
                $(".alert_weigh_").show();
                $(".tipcon").text("最多打印5份");
                $("#printNum").val("1");
                shot;
                return false;
            }
        }
        printChangeInfo(Number(times));
        document.location.href = basePath+"ea/sm/ea_backLogin.jspa?cs=1";
    });


    $("#inp_cz").blur(function(){
		if($(".chengzhong").is(':hidden')&&$(".alert_hand-pd_").is(':hidden')) {
            $("#inp_cz").focus();
        }
	});



    //去结算
    $("#js_end").click(function(){
        if( $(".shop ul").length==0){
            var shot = showTime();
            $(".alert_weigh_").show();
            $(".tipcon").text("没有商品可结算");
            shot;
            return false;
        }
        document.form.submit.click();
        token = 13;

    });



	//分页面模式切换
	
	//鍵盤點擊
	$(".t-keybord").click(function(){
		$(".cz").show();
	})
	$("#inp_cz").parent().children("img").click(function(){
		$(".cz").show();
	})

	//称重输入个数
	$(".chengzhong_js #inputnum").focus(function(){

        $(".cz2").show();

	});


    //左侧点击选中
    $(document).on("click",".left li",function(){
		$(this).parent().children("li").removeClass("active");
		$(this).addClass("active");
		// alert($(this).index());
		if($(this).index()==0){//点击全部
			$(".ul_list").hide();
			codeID="";
            pageNumber = 0;
            pageCount = 0;
            $(".ul_list_sp").html("");
            if(tt!=null&&tt!=""){
                clearTimeout(tt);
            }
            productCate(codeID);
			$(".right>ul").css("height","610px");
		}else{
            $(".right>ul").css("height","560px");
            $(".ul_list").show();
            var codePID = $(this).find(".codePID").text();
            secondCate(codePID);
		}

        // ulwidth();
	})
	//二级标签点击选中
    $(document).on("click",".ul_list li",function(){
         pageNumber = 0;
         pageCount = 0;
		$(this).parent().children("li").removeClass("active");
		$(this).addClass("active");
         codeID = $(this).find(".codeID").text();
        productCate(codeID);
	})
	//商品点击
    $(document).on("click",".ul_list_sp li",function(){
        $(".chengzhong_js .goodsname").text($(this).find(".gp").text());
        $(".chengzhong_js .plu").text($(this).find(".plu").text());
        $(".chengzhong_js .pr").text($(this).find(".pr").text());
        $(".chengzhong_js .prcc").text($(this).find(".prc").text());
        $(".chengzhong_js .hhh").text($(this).find(".al").text());
        $(".chengzhong_js .pd").text($(this).find(".ppd").text());
        $(".chengzhong_js .ppprit").text($(this).find(".prit").text());
        $(".chengzhong_js .cccostm").text($(this).find(".costm").text());
        $(".chengzhong_js .aaactivityID").text($(this).find(".activityIDm").text());

		$(this).parent().children("li").removeClass("active");
		$(this).addClass("active");

		$(".chengzhong").show();
		if($(this).find(".prc").text()=="KGM"){
            $(".chengzhong_js .weight").show();
            $(".chengzhong_js .inputnum").hide();
            $(".chengzhong_js .wvalue").text("0.00");
            getWeight();

        }else{
            $(".chengzhong_js .weight").hide();
            $(".chengzhong_js .inputnum").show();
            $(".chengzhong_js #inputnum").val("");
            $(".chengzhong_js #inputnum").focus();
		}

        $(".totalMoney").text("0.00");



    })
	//购物车点击显示
	$(".gwc").click(function(){
		if($(".right").is(".fen")){
			$(".right").removeClass("fen");
		}else{
			$(".right").addClass("fen");
		}
	})
	//点击购物袋加入购物车
	$(".bag").click(function(){
		var $pa = "";
		if($(this).attr("id")=="gig_shopping_bags"){
            $pa =  $(".bigBag");
		}else{
            $pa =  $(".smallBag");
		}

        var goodsname = $pa.find(".goodsName").text();
        if(goodsname==""){
            alert("暂无");
            return false;
        }
        var price = $pa.find(".price").text();
        var barCode = $pa.find(".barCode").val();
        var companyID = $pa.find(".companyid").val();
        var ppID = $pa.find(".pid").val();
        var pritype = $pa.find(".pritype").val();
        var costmoney = $pa.find(".costmoney").val();
        var activityID = $pa.find(".activityID").val();
        var num = 1;
        var bool = false;
        var $tr="";
        $(".bc").each(function(){
                if($(this).text()==barCode){
                    $tr = $(this).parents("ul");
                    var  specNum =  $tr.find(".specNum").val();
                    ppID =  $tr.find(".ppID").text();
                    $tr.find(".num").val(Number($tr.find(".num").val()) + 1);
                    $tr.find(".inum").val($tr.find(".num").val());
                    bool = true;
                    return false; //跳出循环
                }



        });
        if(bool){
            jisuan();
        }else {
                        number++;

                        var ar = new Array();

                        ar.push("<ul class='clearfix shuliang jh tr'>");

                        ar.push("<li class='li_xh'>" + number + "</li>");
                        ar.push("<li class='li_name'>" + goodsname + "</li>");
			            ar.push("<li class='li_l'>");
			            ar.push("<img class='jian' src='" + basePath + "images/supermarket/scale/jian.png'/>");
			            ar.push("<input disabled type='text' value='1' class='num'></input>");
			            ar.push("<img class='jia' src='" + basePath + "images/supermarket/scale/jia.png'/>");
			            ar.push("</li>");


                        ar.push("<li><span class='tprice'>" + price + "</span></li>");
                        ar.push("<li class='img_del'><img src='" + basePath + "images/supermarket/scale/img_close.png' class='del'/></li>");

                        ar.push("<li style='display:none;'><input name='selfCartmap[" + number + "].companyId' type='hidden' value='" + companyID + "'/><span class='companyID'>" + companyID + "</span><input name='selfCartmap[" + number + "].pid' type='hidden' value='" + ppID + "'/><span class='ppID'>" + ppID + "</span><input name='selfCartmap[" + number + "].barCode' type='hidden' value='" + barCode + "'/><span class='bc'>" + barCode + "</span></li>")
                        ar.push("<li style='display:none;'><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + num + "'/></li>")
                        ar.push("<li style='display:none;'><input name='selfCartmap[" + number + "].pritype' type='hidden' value='" + pritype + "'/><input name='selfCartmap[" + number + "].costmoney' type='hidden' value='" + costmoney + "'/><input name='selfCartmap[" + number + "].activityID' type='hidden' value='" + activityID + "'/></li>");
                        ar.push("<li style='display:none;'><input name='selfCartmap[" + number + "].goodsName' type='hidden' value='" + goodsname + "' class='goodsName'/></li>")
                        ar.push("<li style='display:none;'><input name='selfCartmap[" + number + "].price' type='hidden' value='" + price + "'/><span class='price'>" + price + "</span></li>");

                        ar.push("</ul>");
                        $(".shop").append(ar.join(""));
                        jisuan();

                    }


	})
	$("#small_shopping_bags").click(function(){
		if($(".jiesuan_box .smallshop").length==1){
		 	$('.jiesuan_box').append($('.smallshop').eq(0).clone(true).show().addClass("jh"));
            jisuan();
		}
	})
	//购物车点击删除
    $(document).on("click",".jiesuan_box .del",function(){
		$(this).parents("ul").remove();

        jisuan();

	})
	//购物车加
    $(document).on("click",".jiesuan_box .jia",function(){
        var $tr = $(this).parents("ul");
        var bc = $tr.find(".bc").text();
        var  specNum =  $tr.find(".specNum").val();
        $(this).prev(".num").val(Number($(this).prev(".num").val())+1);
        if(specNum!=""&&specNum!=undefined&&specNum!="null"){
            $tr.find(".inum").val(Number($tr.find(".inum").val())+Number(specNum));
        }else{
            $tr.find(".inum").val(Number($tr.find(".inum").val())+1);
        }
        jisuan();
	})
	//购物车减
    $(document).on("click",".jiesuan_box .jian",function(){
        var $tr = $(this).parents("ul");
        var  specNum =  $tr.find(".specNum").val();

        if(Number($(this).next(".num").val())==1){
            $tr.remove();
        }else {
            $(this).next(".num").val(Number($(this).next(".num").val()) - 1);
            if (specNum != "" && specNum != undefined) {
                $tr.find(".inum").val(Number($tr.find(".inum").val()) - Number(specNum));

            } else {
                $tr.find(".inum").val(Number($tr.find(".inum").val()) - 1);

            }

        }
        jisuan();

    })



	//无码称重
	$(".chengzhong img").click(function(){
		$(this).parents(".chengzhong").hide();
        $(".barcode").focus();
        if($(".chengzhong_js .inputnum").is(':hidden')) {
            clearTimeout(it);
        }
	})
    //打印标签
    $("#printLabel").click(function(){


        var goodsname =  $(".chengzhong_js .goodsname").text();
        var al = $(".chengzhong_js .hhh").text();
        var price=$(".chengzhong_js .pr").text();
        var money=$(".chengzhong_js .totalMoney").text();

        if(money==""||Number(money)<=0){

            var shot = showTime();
            $(".alert_weigh_").show();
            $(".tipcon").text("重量不能小于等于0");
            shot;
            return false;
        }
        var value="";
        if(!$(".inputnum").is(':hidden')) {
            value = $("#inputnum").val();
        }else{
            value = $(".chengzhong_js .wvalue").text();
        }


        printLabel(goodsname,al,price,value,money,getEnBarCode(al,money));
        if($(".chengzhong_js .inputnum").is(':hidden')) {
            clearTimeout(it);
        }
        $(".chengzhong").hide();


    });
    //加入购物车
	$("#jrgwc").click(function(){

        var money=$(".chengzhong_js .totalMoney").text();
        if(money==""||Number(money)<=0){
            var shot = showTime();
            $(".alert_weigh_").show();
            $(".tipcon").text("重量不能小于等于0");
            shot;
            return false;
        }

        var goodsname =  $(".chengzhong_js .goodsname").text();
        var prcc = $(".chengzhong_js .prcc").text();
        var al = $(".chengzhong_js .hhh").text();
        var price=$(".chengzhong_js .pr").text();
        var ppID=$(".chengzhong_js .pd").text();
        var barCode = getEnBarCode(al,money);
        var pritype = $(".chengzhong_js .ppprit").text();
        var costmoney = $(".chengzhong_js .cccostm").text();
        var activityID = $(".chengzhong_js .aaactivityID").text();

        var value="";
        if(!$(".chengzhong_js .inputnum").is(':hidden')) {
            value = $(".chengzhong_js #inputnum").val();
        }else{
            value = $(".chengzhong_js .wvalue").text();
        }

        number++;
        var num = 1;
        var ar = new Array();

        ar.push("<ul class='jh tr'>");

        ar.push("<li class='li_xh'>" + number + "</li>");
        ar.push("<li class='li_name'>"+goodsname+"</li>");

        ar.push("<li class='li_l'><span>"+value+"</span>"+prcc+"</li>");
        ar.push("<li style='display:none '><input  type='hidden' value='1' class='num'/></li>");



        ar.push("<li><span class='tprice'>" + price + "</span></li>");
        ar.push("<li class='img_del'><img src='"+basePath+"images/supermarket/scale/img_close.png' class='del'/></li>");

        ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].companyId' type='hidden' value='"+companyID+"'/><span class='companyID'>" + companyID + "</span><input name='selfCartmap["+number+"].pid' type='hidden' value='"+ppID+"'/><span class='ppID'>" + ppID + "</span><input name='selfCartmap["+number+"].barCode' type='hidden' value='"+barCode+"'/><span class='bc'>" + barCode + "</span></li>")
        ar.push("<li style='display:none;'><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + value + "'/></li>")
        ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].pritype' type='hidden' value='"+pritype+"'/><input name='selfCartmap["+number+"].costmoney' type='hidden' value='"+costmoney+"'/><input name='selfCartmap["+number+"].activityID' type='hidden' value='"+activityID+"'/></li>");
        ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].goodsName' type='hidden' value='"+goodsname+"' class='goodsName'/></li>")
        ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].price' type='hidden' value='"+price+"'/><span class='price'>" + price + "</span></li>");

        ar.push("</ul>");
        $(".shop").append(ar.join(""));
        if($(".chengzhong_js .inputnum").is(':hidden')) {
            clearTimeout(it);
        }
        $(".chengzhong").hide();

        $(".barcode").focus();
        if(!$(".right").is(".fen")){
            $(".right").addClass("fen");
        }
        jisuan();
	})
    codeID="";
    productCate(codeID);
    firstCate();
    getShopBag();


})
//加载称重一级分类
function firstCate(){
    var ulp = basePath
        + "/ea/scale/sajax_ea_findScaleGoodsCate.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : false,
        dataType : "json",
        data:{
          companyID:companyID
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var catelist = member.catelist;
            var htmlstr = new Array();
            for(var i = 0;i<catelist.length;i++){
                htmlstr.push("<li>");
                htmlstr.push("<span>"+catelist[i][1]+"</span>");
                htmlstr.push("<span class='codePID'style='display: none;'>"+catelist[i][0]+"</span>");
                htmlstr.push("</li>");

            }
            $(".fcate").append(htmlstr.join(""));

        },
        error : function(data) {
            console.log("加载一级分类失败");
        }
    });

}


//加载称重二级分类
function secondCate(codePID){
    var ulp = basePath
        + "/ea/scale/sajax_ea_findSecondCate.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            companyID:companyID,
			codePID:codePID
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var twolist = member.twolist;
            var htmlstr = new Array();
            for(var i = 0;i<twolist.length;i++){
            	 if(i==0){
                     htmlstr.push("<li class='active'>");
				 }else{
                     htmlstr.push("<li>");
				 }
                htmlstr.push(twolist[i][1]);
                htmlstr.push("<span class='codeID'style='display: none;'>"+twolist[i][0]+"</span>");
                htmlstr.push("</li>");

            }
            $(".scate").html(htmlstr.join(""));
            ulwidth();
            $(".ul_list .active").click();
        },
        error : function(data) {
            console.log("加载二级分类失败");
        }
    });

}


//加载称重商品
function productCate(codeID){
	if(pageNumber==0){
        $(".ul_list_sp").html("");
	}
    pageNumber += 1;
    var ulp = basePath
        + "/ea/scale/sajax_ea_findProductByCate.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            companyID:companyID,
            codeID:codeID,
            "pageForm.pageNumber" : pageNumber,
            "pageForm.pageSize":pageSize,
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var htmlstr = new Array();
            $(".last").removeClass("last");

            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var list = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for(var i = 0;i<list.length;i++) {
                	if(i==list.length-1){
                        htmlstr.push("<li data-name='"+list[i][0]+"' class='last'>");
					}else{
                        htmlstr.push("<li data-name='"+list[i][0]+"'>");
                       }
                    htmlstr.push("<img src='"+basePath+list[i][3]+"'/>");
                    htmlstr.push("<p class='txt_2'>");
                    htmlstr.push(list[i][1]);
                    htmlstr.push("</p>");
                    htmlstr.push("<span class='gp'>"+list[i][1]+"</span>");
                    htmlstr.push("<span class='pr'style='display: none;'>"+list[i][4]+"</span>");
                    htmlstr.push("<span class='prc'style='display: none;'>"+list[i][6]+"</span>");
                    htmlstr.push("<span class='plu'style='display: none;'>"+list[i][7]+"</span>");
                    htmlstr.push("<span class='al'style='display: none;'>"+list[i][8]+"</span>");
                    htmlstr.push("<span class='ppd'style='display: none;'>"+list[i][0]+"</span>");
                    htmlstr.push("<span class='prit'style='display: none;'>"+list[i][9]+"</span>");
                    htmlstr.push("<span class='costm'style='display: none;'>"+list[i][10]+"</span>");
                    htmlstr.push("<span class='activityIDm'style='display: none;'>"+list[i][11]+"</span>");
                    htmlstr.push("</li>");

                }
            }
            $(".ul_list_sp").append(htmlstr.join(""));



        },
        error : function(data) {
            console.log("加载商品");
        }
    });

}
//二级标签宽度计算
function ulwidth() {
    var ul_list_wid=0;
    $(".ul_list li").each(function(){
        ul_list_wid+=$(this).outerWidth(true);
    })
    // alert($(this).outerWidth(true))
    // alert($(".ul_list li").length);
    $(".ul_list").css("width",ul_list_wid+10)
}


//扫码回车自动查询商品信息

document.onkeydown = function (evt) {//捕捉回车
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        var barcode = $(".barcode").val();
        if(barcode != ""){
            $(".barcode").val("");

            if(barcode.length <= 13) {
                searchGoods(barcode);
                return false;
            }
            if(barcode.length==20){
                checkScardJiFen(barcode)
            }
        }

        return false;
    }
}

function searchGoods(barCode){

    if(!$(".right").is(".fen")){
        $(".right").addClass("fen");
    }
    var bool = false;
    var ppID = "";
    var $tr="";
    $(".bc").each(function(){
        if(barCode.substring(0,2)!="21"){
            if($(this).text()==barCode){
                $tr = $(this).parents("ul");
                var  specNum =  $tr.find(".specNum").val();
                ppID =  $tr.find(".ppID").text();
                 $tr.find(".num").val(Number($tr.find(".num").val()) + 1);

                if(specNum!=""&&specNum!=undefined){
                    $tr.find(".inum").val(Number($tr.find(".inum").val())+Number(specNum));
                    num = Number(specNum);
                }else{

					$tr.find(".inum").val($tr.find(".num").val());

                    num = 1;
                }



                bool = true;
                return false; //跳出循环
            }
        }


    });
    if(bool){
        jisuan();

        return false;
    }



    var url = basePath + "ea/sm/sajax_ea_searchProduct.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data:{
            barCode:barCode,
            comID:companyID
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var result = member.result;
            if(result=="0") {

                number++;
                var goodsname = member.goodsname;
                var price = member.price;
                var barCode = member.barCode;
                var companyID = member.companyID;
                var ppID = member.ppID;
                var money = member.money;
                var quantity = member.quantity;
                var pritype = member.pritype;
                var costmoney = member.costmoney;
                var activityID = member.activityID;
                var  unit = member.unit;
                var num = 1;
                if(money!=""){
                    num = round(Number(money/price));

                }
                if(quantity!=""){
                    num = quantity;
                }

                var ar = new Array();
                if(money!=""){
                    ar.push("<ul class='jh tr'>");
                }else{
                    ar.push("<ul class='clearfix shuliang jh tr'>");
                }
                ar.push("<li class='li_xh'>" + number + "</li>");
                ar.push("<li class='li_name'>"+goodsname+"</li>");

                if(money!=""){
                    ar.push("<li class='li_l'><span>"+num+"</span>"+unit+"</li>");
                    ar.push("<li style='display:none '><input  type='hidden' value='1' class='num'/></li>");
			    }else{
                    ar.push("<li class='li_l'>");
                    ar.push("<img class='jian' src='"+basePath+"images/supermarket/scale/jian.png'/>");
                    ar.push("<input disabled type='text' value='1' class='num'></input>");
                    ar.push("<img class='jia' src='"+basePath+"images/supermarket/scale/jia.png'/>");
                    ar.push("</li>");
				}


                ar.push("<li><span class='tprice'>" + price + "</span></li>");
                ar.push("<li class='img_del'><img src='"+basePath+"images/supermarket/scale/img_close.png' class='del'/></li>");

                ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].companyId' type='hidden' value='"+companyID+"'/><span class='companyID'>" + companyID + "</span><input name='selfCartmap["+number+"].pid' type='hidden' value='"+ppID+"'/><span class='ppID'>" + ppID + "</span><input name='selfCartmap["+number+"].barCode' type='hidden' value='"+barCode+"'/><span class='bc'>" + barCode + "</span></li>")
                ar.push("<li style='display:none;'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value='" + quantity + "'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + num + "'/></li>")
                ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].pritype' type='hidden' value='"+pritype+"'/><input name='selfCartmap["+number+"].costmoney' type='hidden' value='"+costmoney+"'/><input name='selfCartmap["+number+"].activityID' type='hidden' value='"+activityID+"'/></li>");
				ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].goodsName' type='hidden' value='"+goodsname+"' class='goodsName'/></li>")
                ar.push("<li style='display:none;'><input name='selfCartmap["+number+"].price' type='hidden' value='"+price+"'/><span class='price'>" + price + "</span></li>");

                ar.push("</ul>");
                $(".shop").append(ar.join(""));
                jisuan();

            }

        },
        error : function cbf(data) {
            console.log("失败")
        }

    })
}

//统计总数量/总金额
function jisuan(){

    totalnum = 0;
    totalmoney = 0;
    $(".shop .tr").each(function(){
        var num = Number($(this).find(".inum").val());
        var num1 = 0;
        $(this).find(".num").each(function(){
            num1+= Number($(this).val());

        })
        var price = Number($(this).find(".price").text());

        var money = Number(num*price).toFixed(2);
        $(this).find(".tprice").text(money);
        totalnum = totalnum+num1;
        totalmoney = Number(totalmoney)+Number(money);

    });
    $(".totalnum").text(totalnum);
    $(".totalmoney").text(Number(totalmoney).toFixed(2));
    $(".gwc span").text(totalnum);

}

function round(value){

    var aStr = value.toString();
    var aArr = aStr.split('.');
    if(aArr.length > 1) {
        if(aArr[1].length>3) {
            value = Number(aStr).toFixed(3);
        }
    }

    return Number(value);
}

//获取购物袋
function getShopBag(){

    var url = basePath + "ea/sm/sajax_ea_searchShoppBag.jspa?";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            comID:companyID
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var baglist = member.baglist;
            var ar = new Array();
            var $p = "";
            for(i=0;i<baglist.length;i++){
                var obj1 = baglist[i];
                if(i==0){
                   $p = $(".bigBag");
                }else{
                    $p = $(".smallBag");
                }
                $p.find(".goodsName").text(obj1[0]);
                $p.find(".price").text(obj1[2]);
                $p.find(".barCode").val(obj1[4]);
                $p.find(".companyid").val(obj1[3]);
                $p.find(".pid").val(obj1[1]);
                $p.find(".pritype").val(obj1[5]);
                $p.find(".costmoney").val(obj1[6]);
                $p.find(".activityID").val(obj1[7]);

            }
        },
        error : function cbf(data) {
            console.log("获取购物袋失败");
        }

    })
}

//补齐0
function pad2(num, n) {
    if ((num + "").length >= n) return num;
    return pad2("0" + num, n);
}
//计算生成条码
function getEnBarCode(al,money) {
    var C = "21"+al+pad2(Number(money)*100, 5);
    var C1 = 0;
    var C2 = 0;
    var V = 0;
    for(var i = 1;i<C.length+1;i++){
        if(i%2==1){
            C1+=Number(C[i-1]);
        }else{
            C2+=Number(C[i-1]);
        }
    }
    var G1 = (Number(C1)+Number(C2)*3).toString();
    var V = 10-Number(G1.substring(G1.length-1));

    return C+V+"";

}

//获取重量
function getWeight(){
    //100毫秒一次获取秤的重量和稳定值
  it =   setInterval(function () {

        $.ajax({
            url: "http://127.0.0.1:5017/api/Scale/Weight",
            dataType: "json",
            type: 'get',
            cache: false,//IE下要关闭cache，否则不会刷新
            data: null,
            success: function (data) {

                var  v = data.Weight.toFixed(3);


                $(".wvalue").text(v);
                var price = $(".chengzhong_js .pr").text();
                if(!$(".weight").is(':hidden')) {
                    $(".totalMoney").text((Number(price) * Number(v)).toFixed(2));
                }



            },
            error: function (XMLHttpRequest) {
                //  alert("获取重量失败, " + XMLHttpRequest.responseJSON.Message);
            }
        });
    }, 100);
}


//打印标签
function printLabel(goodsname,plu,price,value,money,barcode) {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();


     LODOP=getLodop();

    LODOP.PRINT_INIT("打印标签");
    LODOP.SET_PRINT_PAGESIZE(1,"580","300","");

    LODOP.SET_PRINT_STYLE("FontSize",10);
    LODOP.ADD_PRINT_TEXT("1mm","12mm","30mm","5mm",goodsname);
    LODOP.SET_PRINT_STYLE("FontSize",10);
    LODOP.ADD_PRINT_TEXT("1mm","40mm","20mm","5mm",plu);
    LODOP.SET_PRINT_STYLE("FontSize",8);
    LODOP.ADD_PRINT_TEXT("10mm","3mm","20mm","4mm",year+"-"+month+"-"+day);//打印时间
    LODOP.SET_PRINT_STYLE("FontSize",9);
    LODOP.ADD_PRINT_TEXT("8mm","35mm","10mm","4mm",price);//单价
    LODOP.ADD_PRINT_TEXT("8mm","48mm","10mm","4mm",value);//重量或者个数
    LODOP.ADD_PRINT_BARCODE("13mm","2mm","35mm","8mm","128auto",barcode);//条码
    LODOP.SET_PRINT_STYLE("FontSize",13);
    LODOP.SET_PRINT_STYLE("Bold",1);
    LODOP.ADD_PRINT_TEXT("16mm","35mm","20mm","7mm",money);//
    //
    // LODOP.SET_PRINT_MODE("TRYLINKPRINTER_NOALERT",true);//这个语句设置网络共享打印机连接不通时是否提示一下
    // if (LODOP.SET_PRINTER_INDEX("TSC TTP-244 Plus"))

    if (LODOP.CVERSION) {
        LODOP.On_Return=function(TaskID,Value){if(Value>=0)   LODOP.PRINT(); else alert("选择失败！");};
        LODOP.SELECT_PRINTER();

    };


}




//加载称重商品
function searchPlu(plu){

    var ulp = basePath
        + "/ea/scale/sajax_ea_findProductPLU.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            companyID:companyID,
            plu:plu
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var objs = member.objs;
            if(objs==null){
                return;
            }
            $(".chengzhong_js .goodsname").text(objs[1]);
            $(".chengzhong_js .plu").text(objs[7]);
            $(".chengzhong_js .pr").text(objs[4]);
            $(".chengzhong_js .prcc").text(objs[6]);
            $(".chengzhong_js .hhh").text(objs[8]);
            $(".chengzhong_js .pd").text(objs[0]);
            $(".chengzhong_js .ppprit").text(objs[9]);
            $(".chengzhong_js .cccostm").text(objs[10]);
            $(".chengzhong_js .aaactivityID").text(objs[11]);


            $(".chengzhong").show();
            if(objs[6]=="KGM"){
                $(".chengzhong_js .weight").show();
                $(".chengzhong_js .inputnum").hide();
                $(".chengzhong_js .wvalue").text("0.00");
                getWeight();

            }else{
                $(".chengzhong_js .weight").hide();
                $(".chengzhong_js .inputnum").show();
                $(".chengzhong_js #inputnum").val("");
                $(".chengzhong_js #inputnum").focus();
            }

            $(".totalMoney").text("0.00");
            $(".cz").fadeOut('fast');

        },
        error : function(data) {
            console.log("查询PLU商品");
        }
    });

}


//返回登陆
function backLogin(){
    $(".alert_hand_").show();

    var url = basePath + "ea/sm/sajax_ea_getChangeWorkInfo.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        success : function(data) {
            var m = eval("("+data+")");
            $(".jjb li").find(".pos").text(m.pos);
            $(".jjb li").find(".staffcode").text(m.staffcode);
            $(".jjb li").find(".companyName").text(m.companyName);
            $(".jjb li").find(".companyAddress").text(m.companyAddress);
        },
        error:function(data){

        }
    });

}

function check(form) {
    VirtualKeyboard.close();
    /*交接班登录如果密码为空*/
    var txt = $(".alert_hand-pd .text input").val();
    if (txt == "") {
        var shot = showTime();
        $(".alert_weigh_").show();
        $(".tipcon").text("请填写登陆密码");
        shot;
        return false;
    }else {
        var url = basePath + "ea/sm/sajax_ea_checkChangeUser.jspa";
        $.ajax({
            url : url,
            type : "get",
            async : false,
            dataType : "json",
            data:{
                psw:txt
            },
            success : function(data) {
                var m = eval("("+data+")");
                var result = m.result;
                if(result=="2"){
                    var shot = showTime();
                    $(".alert_weigh_").show();
                    $(".tipcon").text("登陆密码错误");
                    $(".alert_hand-pd .text input").val("");
                    $(".alert_hand-pd .text input").focus();
                    shot;
                }else{
                    //打印
                    printChangeInfo(1);
                    $(".alert_hand-pd_").show();
                    $(".alert_hand-pd-2").show();
                    $("#softkey").html("");
                }
            },
            error:function(data){

            }
        });


        return false;
    }
}
//打印交班信息小票
function printChangeInfo(times){

    for(var i = 0;i<times;i++){
        myPrint();
    }


}

var LODOP; //声明为全局变量
function myPrint() {
    printPage();
    // LODOP.SET_PRINT_MODE("TRYLINKPRINTER_NOALERT",true);//这个语句设置网络共享打印机连接不通时是否提示一下
    // if (LODOP.SET_PRINTER_INDEX("XP-58"))
        LODOP.PRINT();
}
function printPage() {

    LODOP=getLodop();
    LODOP.PRINT_INIT("交接班小票");
    LODOP.SET_PRINT_PAGESIZE(3,500,0.2,"");
    LODOP.SET_PRINT_STYLE("FontSize",9);
    LODOP.SET_PRINT_STYLE("Bold",1);


    LODOP.ADD_PRINT_TEXT(15,70,180,50,"交接班");



    var hPos=50;//小票上边距
    var pageWidth=100;//小票宽度
    var  rowHeight=15;//小票行距

    LODOP.SET_PRINT_STYLE("FontSize",8);

    LODOP.ADD_PRINT_LINE(hPos,5, hPos, 500,2,0);
    hPos+=rowHeight;
    LODOP.SET_PRINT_STYLE("FontSize",8);
    LODOP.SET_PRINT_STYLE("Bold",0);
    var companyName= $(".jjb").find(".companyName").text();
    var  qcom = "";
    var hcom = "";
    if(companyName.length>12){
        qcom = companyName.substring(0,12);
        hcom = companyName.substring(12,companyName.length);
    }else{
        qcom = companyName;
    }
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"店名："+qcom);
    hPos+=rowHeight;
    if(hcom!=""){
        LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,hcom);
        hPos+=rowHeight;
    }
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"收银员："+$(".jjb").find(".staffcode").text());
    hPos+=rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"POS终端:"+$(".jjb").find(".pos").text());
    hPos+=rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"交班时间:"+$(".jjb").find(".changeDate").text());
    hPos+=rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"交班地址:"+$(".jjb").find(".companyAddress").text());
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    LODOP.ADD_PRINT_LINE(hPos,5, hPos, 500,2,0);



}

function test(obj){
   /* var top = $(obj).offset().top+70;
    var left = $(obj).offset().left;*/

    /*if($(obj).attr("id")=="psd"||$(obj).attr("id")=="printNum"){
        var top = $(obj).offset().top+90;
        left = $(obj).offset().left-200;

    }*/
    VirtualKeyboard.toggle($(obj).attr("id"), 'softkey');
    $("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
   /* $("#softkey").attr("style","position:absolute;z-index:99;top:"+top+"px;left:"+left+"px;");*/

}
//设定倒数秒数
var t = 4;
function showTime(){
    t -= 1;
    $(".alert_weigh p span").text(t);

    //每秒执行一次,showTime()
    var s = setTimeout("showTime()",1000);

    if(t==0){
        t = 4;
        $(".alert_weigh_").hide();
        clearTimeout(s);
        $(".alert_weigh p span").text(t);
    }
    /*商品称重弹框*/
    $("#confirm").click(function () {
        $(".alert_weigh_").hide();
        clearTimeout(s);
        t = 4;
        $(".alert_weigh p span").text(t);
    });
}

function re_load(journalNum){
    if(token){
        var  totalmoney = $("#paymoney").text();
        document.location.href = basePath+"page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum="+journalNum+"&totalMoney="+totalmoney+"&totalNum="+totalnum+"&comID="+companyID;
    }

}

//核对购物卡积分
function checkScardJiFen(scard){

    var ulp = basePath
        + "/ea/sm/sajax_ea_checkScardJiFen.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : false,
        dataType : "json",
        data:{
            scard:scard
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var result = member.result;
            if(result=="0") {
                var sccId = member.sccId;
                var staffName = member.staffName;
                var account = member.account;
                var staffid = member.staffId;
                // document.location.href = basePath + "ea/sm/ea_getbpDetail.jspa?sccId=" + sccId+"&staffName="+staffName+"&" ;
                document.location.href = basePath + "ea/bonuspoints/ea_getbpDetail.jspa?account="+account+"&sccid="+sccId+"&khd=0&flag=&staffid="+staffid+"&staffName="+staffName+"&platType=screen";

            }else{
                var shot = showTime();
                $(".alert_weigh_").show();
                $(".tipcon").text("此卡无效可以联系工作人员");
                shot;
            }


        },
        error : function(data) {
            console.log("查询支付结果失败");
        }
    });
}

