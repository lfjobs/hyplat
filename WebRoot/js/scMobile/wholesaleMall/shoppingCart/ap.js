var d;
var ppid = "";
var orderid;
$(function() {
    //异步获取购物车数据
    ajaxShoppingCartList();
	// 样式处理开始
	$("body").css("width", $(window).width());
	$("body").css("height", $(window).height());

	// 中联园区头部
	/*$(".wfj_top").attr("style","height:" + $(window).height() * 0.07 + "px;line-height:"+ $(window).height() * 0.06 + "px;border-bottom:1px solid #E5E5E5");
	$(".wfj_top").find("li").eq(0).attr("style", "width:10%;");
    $(".wfj_top").find("li").eq(2).attr("style", "width:10%;");
    $(".wfj_top").find("li").eq(0).find("img").attr("style","margin-top:"+$(window).height()*0.015+"px;height:"+$(window).height()*0.035+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");

	$(".wfj_top").find("li").eq(1).attr("style","width:80%;font-size:" + $(window).height() * 0.025 + "px;");
    $(".wfj_top").find("li").eq(2).find("img").attr("style","margin-top:"+$(window).height()*0.015+"px;height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");*/
    $(".kgwc").attr("style","font-size:" + $(window).height() * 0.025 + "px;");

    //
    $(".logopic").attr("style","height:"+$(window).height()*0.05+"px; width:auto; vertical-align:middle;");

    $(".wfj12_004_title").attr("style","height:" + $(window).height() * 0.08 + "px;line-height:"+ $(window).height() * 0.08 + "px;");
	$(".wfj12_004_height").attr("style","height:" + $(window).height() * 0.04 + "px;line-height:"+ $(window).height() * 0.04 + "px;padding:"+ $(window).height() * 0.02 + "px 0;");
	$(".title").find("a").attr("style","font-size:" + $(window).height() * 0.02 + "px;padding:"+ $(window).height() * 0.005 + "px " + $(window).height()* 0.015 + "px");
    $(".logo").attr("style","height:"+ $(window).height() * 0.04 + "px;line-height:"+ $(window).height() * 0.04 + "px;");
	$(".change").find("a").attr("style","font-size:" + $(window).height() * 0.02 + "px;");

	$(".wfj12_004_product").attr("style"," border-bottom:" + $(window).height() * 0.005+ "px solid #FFF; padding:" + $(window).height() * 0.01+ "px 0;");
	$(".wfj12_004_proInfo").find("ul").attr("style"," line-height:" + $(window).height() * 0.03 + "px;");
	$(".wfj12_004_proInfo").find("li").attr("style"," font-size:" + $(window).height() * 0.02 + "px;");

	$(".wfj12_004_nums").find("ul").attr("style"," line-height:" + $(window).height() * 0.04 + "px;");
	$(".wfj12_004_nums").find("li").attr("style"," font-size:" + $(window).height() * 0.02 + "px;");
	$(".wfj12_004_nums").find("li").find("input").attr("style"," font-size:" + $(window).height() * 0.03 + "px;");
	$(".borders").find("li").attr("style"," font-size:" + $(window).height() * 0.03 + "px; border-bottom:"+ $(window).height() * 0.005 + "px solid #FFF;");

	$(".borders").each(function(index, element) {
		$(this).find("#nums").attr("style"," font-size:" + $(window).height() * 0.03+ "px; border-left:#FFF " + $(window).height() * 0.005+ "px solid; border-right:" + $(window).height()* 0.005 + "px solid #FFF; border-bottom:"+ $(window).height() * 0.005 + "px solid #FFF;");
	});

	$(".wfj12_004_bottom").attr("style"," height:" + $(window).height() * 0.06 + "px; line-height:"+ $(window).height() * 0.06 + "px;");
	$(".wfj12_004_bottom").find("li").attr("style","font-size:" + $(window).height() * 0.02 + "px;");
	$(".wfj12_004_bottom").find("span").attr("style","font-size:" + $(window).height() * 0.025 + "px;");
	$(".wfj12_004_bottom").find("div").attr("style","font-size:" + $(window).height() * 0.025 + "px;");
	$(".wfj12_004_allchecked").find("li").eq(0).attr("style","width:40%;text-align:center;");

	$(".wfj12_004_delete").attr("style","height:" + $(".wfj12_004_product").height() + "px;line-height:"+ $(".wfj12_004_product").height() + "px;");
	$(".wfj12_004_delete").find("li").attr("style","font-size:" + $(window).height() * 0.025 + "px; ");

	$(".wfj12_004_choice").attr("style","height:" + $(".wfj12_004_product").height() + "px;line-height:"+ $(".wfj12_004_product").height()+ "px;vertical-align:middle;");
	 $(".wfj12_004_choice").find("img").css("vertical-align", "middle");
    $(".wfj12_004 .wfj12_004_con .wfj12_004_product div .wfj12_004_proimg").css("height", $(".wfj12_004 .wfj12_004_con .wfj12_004_product div .wfj12_004_proimg").width() + "px");
	// 样式处理结束
	//全部的选择
	$(".allchangeimg").click(function() {
		if ($(this).attr("src") == basePath + "/images/choice_02.png") {
			$(this).attr("src", basePath + "/images/choice_01.png");
			$(".bj01").find(".change_imgs").attr("src",basePath + "/images/choice_01.png");
			$(".bj01").find(".change_img").attr("src",basePath + "/images/choice_01.png");


			$(".change_img").each(function(){
				$(this).attr("src",basePath + "/images/choice_01.png");
				$(this).parents("ul").addClass("selected");
			});
			$(this).parents(".wfj12_004").find(".dx").val("1");
		} else {
			$(this).attr("src", basePath + "/images/choice_02.png");
			$(".bj01").find(".change_imgs").attr("src",basePath + "/images/choice_02.png");
			$(".bj01").find(".change_img").attr("src",basePath + "/images/choice_02.png");

			$(".change_img").each(function(){
				$(this).attr("src",basePath + "/images/choice_02.png");
				$(this).parents("ul").removeClass("selected");
			});

			$(this).parents(".wfj12_004").find(".dx").val("2");
		}
		//计算
		$("span#zh").html("0");
		$("div#allprod").find(".oneProduct").each(function(){
			var panduan=$(this).parents(".wfj12_004_width").find(".dx").val();//没选中2,选中1
			if (panduan=="1") {//选中
				var price=$(this).find("#price").text();
				var num=$(this).find("#num").text();
				sumCart(price,num);
			}else{
				var price=-$(this).find("#price").text();
				var num=$(this).find("#num").text();
				sumCart(price,num);
			}
		});

	});
	//公司的选择
	$(".change_imgs").click(function() {
		var sc=$("span#zh").text();
		if ($(this).attr("src") == basePath + "/images/choice_02.png") {//公司全选,选中状态(图片初始为未选中)
			var fcomid = $(this).parents("ul").find(".fcomid").val();//获取公司
			$("img."+fcomid).each(function(){
				if($(this).attr("src")==basePath + "/images/choice_01.png"){//判断之前已选中商品.先减去
					var price=$(this).parents(".wfj12_004_product").find("#price").text();
					var num=$(this).parents(".wfj12_004_product").find("#num").text();
					sumCart(-price,num);
				}
			});
			$("img."+fcomid).each(function(){//循环所有商品累加.
				$(this).attr("src",basePath + "/images/choice_01.png");
				$(this).parents("ul").addClass("selected");
				var price=$(this).parents(".wfj12_004_product").find("#price").text();
				var num=$(this).parents(".wfj12_004_product").find("#num").text();
				sumCart(price,num);
			});
			$(this).attr("src",basePath + "/images/choice_01.png");
			$(this).parents(".wfj12_004_con").find(".dx").val("1");
		} else {
			var fcomid = $(this).parents("ul").find(".fcomid").val();
			$("img."+fcomid).each(function(){
				if($(this).attr("src")==basePath + "/images/choice_01.png"){//判断之前已选中商品.减去
					$(this).attr("src",basePath + "/images/choice_02.png");
					$(this).parents("ul").removeClass("selected");
					price=$(this).parents(".wfj12_004_product").find("#price").text();
					num=$(this).parents(".wfj12_004_product").find("#num").text();
					sumCart(-price,num);
				}
			});
			$(this).parents(".bj01").find(".change_imgs").attr("src",basePath + "/images/choice_02.png");
			$(this).parents(".wfj12_004_con").find(".dx").val("2");
		}

	});
	//选择框选中货移除
	$(".change_img").click(function() {
		var price=0;
		var num=0;
		price=$(this).parents(".wfj12_004_product").find("#price").text();
		num=$(this).parents(".wfj12_004_product").find("#num").text();
		if ($(this).attr("src") == basePath + "/images/choice_02.png") {
			$(this).attr("src", basePath + "/images/choice_01.png");
			$(this).parents("ul").addClass("selected");
		} else {
			price=-price;
			$(this).attr("src", basePath + "/images/choice_02.png");
			$(this).parents("ul").removeClass("selected");
		}
		sumCart(price,num);
	});
	//编辑
	$(".bj01").find(".change").find("a").click(function() {
		if ($(this).text() == "编辑") {
			$(this).text("完成");

			var fcomid = $(this).parents("ul").find(".fcomid").val();
			$("div.changes").find("img."+fcomid).each(function(){
				$(this).parents(".bj01").find(".proinfo02").slideUp(500);
				$(this).parents(".bj01").find(".wfj12_004_changedelete").addClass("oprOpen").slideDown(500);
			});
		} else {
			var parmsDel=new Array();
			$("#allprod").find(".oprOpen").each(function(){
				var num=$(this).find("input.ls").val();//数量
				var ppid=$(this).find("input#editppid").val();//购物车id
                //var stardard=$(this).prev(".oneProduct").find("li.stard").text();//规格
                parmsDel.push(ppid+'#'+num+'@');
			});
            var delPscIdStr = $("#ttsw_hid_del_pscid").val();//购物车删除id拼接字符串
            if(parmsDel.join("")!="" || delPscIdStr != ""){
				var purl=basePath+"ea/wholesaleMall/sajax_ea_ajaxChangeCartNum.jspa";
                $.ajax({
                    url: encodeURI(purl),
                    type: "POST",
                    async: false,
                    data: {
                        "shoppingCartParmStr": parmsDel.join(""),//物类id[该超市所有商品分类的父id]
                        "delPscIdStr": delPscIdStr//购物车删除id拼接字符串
                    },
                    dataType: "json",
                    success: function (data) {
                        $("#ttsw_hid_del_pscid").val("");
                        if(data=="succ"){
                            alert("操作成功");
						}else{
                    		alert("操作失败");
						}
                    },
                    error: function (data) {
                        alert("数据获取失败！");
                    }
                });
			}
			$(this).text("编辑");
			var fcomid = $(this).parents("ul").find(".fcomid").val();
			$("div.changes").find("img."+fcomid).each(function(){
				$(this).parents(".bj01").find(".proinfo02").slideDown(500);
				$(this).parents(".bj01").find(".wfj12_004_changedelete").removeClass("oprOpen").slideUp(500);
			});
		}
	});

	$(".wfj12_004_content").attr("style","width:"+ $(window).width()+ "px;height:"+ parseInt($(window).height() - $(".wfj_top").height()- $(".wfj12_004_bottom").height())+ "px; overflow:hidden;");
	$(".wfj12_004_hidden").attr("height:",$(".wfj12_004_content").height() + "px; overflow:auto;");

	var h = parseInt($(".wfj12_004_con").height() * $(".wfj12_004_con").length);

	if (h < $(".wfj12_004_content").height()) {
		$(".wfj12_004_hidden").css("width",$(".wfj12_004_content").width() + "px");
		$(".wfj12_004_hidden").css("height",parseInt($(".wfj12_004_content").height()) + "px");
	} else {
		$(".wfj12_004_content").css("width", $(window).width() + "px")
		$(".wfj12_004_hidden").css("width",parseInt($(".wfj12_004_content").width() + 17) + "px");
		$(".wfj12_004_hidden").css("height",parseInt($(".wfj12_004_content").height()) + "px");
	}
	//增加商品数量
	$(".li2").click(function() {
		var num = parseInt($(this).parent().find("input").val());
		num=num + 1;
		$(this).parent().find("input").val(num);//展示的数量变化
		var price = parseFloat($(this).parents(".wfj12_004_product").find("#moue").val());//单价
		var temp = parseFloat($(this).parents(".wfj12_004_product").find("span#oprSum").html());//合计
		temp = temp + price;
		temp=toDecimal2(temp);
		$(this).parent().parent().find("span#oprSum").html(temp);
		$(this).parents(".wfj12_004_product").find(".right").find("span").text(num);

		$temp= $(this).parents(".wfj12_004_product").find(".change_img");
		if($temp.attr("src").indexOf("choice_01.png")>0){
			sumCart(price,1);
		}
	});
	//减少商品数量
	$(".li1").click(function() {
		var num = parseInt($(this).parent().find("input").val());
		$(this).parent().find("input").val(num - 1);
		num = parseInt($(this).parent().find("input").val());
		if (num < 1) {

			$(this).parent().find("input").val(num + 1);
		//	alert("不能小于0");
			return;
		} else {
			var price = parseFloat($(this).parents(".wfj12_004_product").find("#moue").val());//单价
			var temp = parseFloat($(this).parents(".wfj12_004_product").find("span#oprSum").html());//合计
			temp = temp - price;
			temp=toDecimal2(temp);
			$(this).parents(".wfj12_004_product").find("span#oprSum").html(temp);
			$(this).parents(".wfj12_004_product").find(".right").find("span").text(num);
			sumCart(-price,1);
		}
	});

	$(".dele").click(function() {
		var ddi = $(this).parents(".wfj12_004_product").find(".ppid").val();//购物车id
		var delPscIdStr = $("#ttsw_hid_del_pscid").val();
        $("#ttsw_hid_del_pscid").val(ddi+'@'+delPscIdStr);
		var $p = $(this).parents(".zcp");
        var cartid = $p.find(".cartid").val();
		$this = $(this);
		//实现效果
        var price = parseFloat($this.parents(".wfj12_004_product").find("#moue").val());//单价
        var num = parseInt($this.parents(".wfj12_004_product").find("input.ls").val());
        $temp= $this.parents(".wfj12_004_product").find(".change_img");
        if($temp.attr("src").indexOf("choice_01.png")>0){
            sumCart(-price,num);
        }
        var len = $this.parents(".changes").find(".zcp").length;
        var $parents =  $this.parents(".changes");

        $p.remove();
        var lencxp = $("."+ddi+"zcp").length;
        if(lencxp==0){
            $("."+ddi).remove();
        }
	});

    // 提交订单页面
    $(".wfj12_004_commit").click(function() {
        if ($("#zh").text() == "0"){
            //alert("必须选择一件产品");
            alert("结算金额不能为0");
            return;
        }
        $("#f1").attr("action", basePath + "ea/wholesaleMall/ea_toSettlement.jspa");
        pinjie();
        var scParmStr = $("#ppic").val();
		window.location.href = basePath + "ea/wholesaleMall/ea_toSettlement.jspa?shoppingCartParmStr="+scParmStr;
       //$("#f1").submit();
    });
});

//金额计算
function toDecimal2(x) {
	var f = parseFloat(x);
	var f = Math.round(x * 100) / 100;
	var s = f.toString();
	var rs = s.indexOf('.');
	if (rs < 0) {
		rs = s.length;
		s += '.';
	}
	while (s.length <= rs + 2) {
		s += '0';
	}
	return s;
}

//计算显示总计金额
function sumCart(price,num){
	if(price.length<1||num.length<1){
		return;
	}
	var sc=$("span#zh").text();
    var shuliang=$("span#shuliang").text();
	sc=parseFloat(sc)+(parseFloat(price)*parseFloat(num));
	sc=Math.round(sc*100)/100;
	if(parseFloat(price)<0){
        num = -num;
	}
    shuliang =  Number(shuliang)+Number(num);
	if(sc<0){
		sc=0;
	}
	$("span#zh").html(sc);
    $("span#shuliang").html(shuliang);
}


// 获取选中的产品 ID 跟数量拼接 成 ppid-数量*ppid-数量-stardard
function pinjie() {
    //拼接选中的公司
    var pid = "";
    var comid = "";
    $(".selected").each(function(){
        var ppid = $(this).parent().find(".ppid").val();
        //var stardard = $(this).parents(".wfj12_004_product").find(".stard").text();
        var companyid = $(this).parent().find(".companyId").val();
        if(comid.indexOf(companyid)==-1){
            comid+=companyid+",";
        }
        pid+=ppid+"@";
    });
    $("#companyIds").val(comid);
    $("#ppic").val(pid);
}

/**
 * 异步获取购物车内所有商品信息
 */
function ajaxShoppingCartList() {
    var purl=basePath+"ea/wholesaleMall/sajax_ea_ajaxShoppingCartList.jspa";
    $.ajax({
        url: encodeURI(purl),
        type: "POST",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var shoppingCartList = member.shoppingCartList;//商品集合
            var comps = member.comps;//公司集合
            var innerHtml = shoppingCartHtml(shoppingCartList,comps);
            $('#allprod').append(innerHtml);
        },
        error: function (data) {
            alert("数据获取失败！");
        }
    });
}

/**
 * 拼接购物车页面
 * @param shoppingCartList 购物车商品集合
 * @param comps 公司集合
 */
function shoppingCartHtml(shoppingCartList,comps){
    var innerHtml = '';
    if(shoppingCartList.length <=0){//无数据
        innerHtml = '<div class="kgwc">'
                        +'<img src="'+basePath+'images/scMobile/wholesaleMall/shoppingCart/emptycar.png">'
                    +'</div>';
    }else{
        //循环公司
        var scInnerHtml = '';
        var com = null;
        var shopCart = null;
        var ggHtml = '';
        for(var i=0,j=comps.length;i<j;i++){
            com = comps[i];
            scInnerHtml = '';
            for(var n=0,m=shoppingCartList.length;n<m;n++){
                ggHtml = '';
                shopCart = shoppingCartList[n];
                if(shopCart.cmStr != null && shopCart.cmStr !=""){
                    ggHtml += '尺码：'+shopCart.cmStr;
                }
                if(shopCart.ysStr != null && shopCart.ysStr !=""){
                    ggHtml +=  '颜色：'+shopCart.ysStr;
                }
//                if(shopCart.ftStr != null && shopCart.ftStr !=""){
//                    ggHtml += '副图：'+ shopCart.ftStr;
//                }
//                if(shopCart.spStr != null && shopCart.spStr !=""){
//                    ggHtml +=  '视频：'+shopCart.spStr;
//                }
               // if((shopCart.cmStr == null || shopCart.cmStr == "") &&  (shopCart.ysStr == null || shopCart.ysStr == "")
				//	&& (shopCart.ftStr == null || shopCart.ftStr == "") && (shopCart.spStr == null || shopCart.spStr == "")){
                    ggHtml = shopCart.standard;
             //   }
                if(com[0] == shopCart.companyid){
                    scInnerHtml += '<div class="wfj12_004_product zcp '+shopCart.pscId+'zcp">'
                                    +'<div class="wfj12_004_width">'
                                        +'<div class="wfj12_004_choice">'
                                            +'<ul>'
                                                +'<li>'
                                                    +'<img class="change_img '+shopCart.companyid+'" src="'+basePath+'/images/choice_02.png"/>'
                                                +'</li>'
                                            +'</ul>'
                                            +'<input type="hidden" class="dx" name="xuanz" value="2"/>'
                                            +'<input type="hidden" class="ppid" name="ppid" value="'+shopCart.pscId+'"/>'
                                            +'<input type="hidden" value="'+shopCart.companyid+'" class="companyId '+shopCart.companyid+'"/>'
                                        +'</div>'
                                        +'<div class="wfj12_004_proimg">'
                                            +'<img src="'+basePath+shopCart.image+'"/>'
                                        +'</div>'
                                        +'<div class="wfj12_004_proInfo proinfo02 oneProduct">'
                                            +'<ul>'
                                                +'<li>'+shopCart.goodsName+'</li>'
                                            +'</ul>'
                                            +'<ul>'
                                                +'<li class="stard">'+ggHtml+'</li>'
                                            +'</ul>'
                                            +'<ul>'
                                                +'<li class="left">￥<span id="price">'+shopCart.allPrice+'</span></li>'
                                                +'<li class="right">x<span id="num">'+shopCart.tjNum+'</span></li>'
                                            +'</ul>'
                                        +'</div>'
                                        +'<div class="wfj12_004_changedelete deletes01" style="display:none;">'
                                            +'<div class="wfj12_004_nums">'
                                                +'<ul class="borders">'
                                                    +'<li class="li1">-</li>'
                                                    +'<li id="nums">'
                                                        +'<input type="text" readonly="readonly" value="'+shopCart.tjNum+'" class="ls"/>'
                                                    +'</li>'
                                                    +'<li class="li2">+</li>'
                                                +'</ul>'
                                                +'<ul>'
                                                    +'<li style="font-size: 15px;">'
                                                        +'单价:￥<span id="oprPrice">'+shopCart.allPrice+'</span>'
                                                    +'</li>'
                                                    +'<li style="font-size: 15px;">'
                                                        +'小计:￥<span id="oprSum">'+(shopCart.allPrice*shopCart.tjNum)+'</span>'
                                                    +'</li>'
                                                    +'<input type="hidden" value="'+shopCart.allPrice+'" id="moue"/>'
                                                    +'<input type="hidden" id="editppid" value="'+shopCart.pscId+'"/>'
                                                +'</ul>'
                                            +'</div>'
                                            +'<div class="wfj12_004_delete">'
                                                +'<ul>'
                                                    +'<li class="dele">删除</li>'
                                                +'</ul>'
                                            +'</div>'
                                        +'</div>'
                                    +'</div>'
                                +'</div>';
                }
            }
            innerHtml += '<div class="changes bj01">'
                            +'<div class="wfj12_004_con">'
                                +'<div class="wfj12_004_title">'
                                    +'<div class="wfj12_004_height">'
                                        +'<div class="wfj12_004_width">'
                                            +'<ul>'
                                                +'<li id="choice">'
                                                    +'<a href="javascript:;">'
                                                        +'<img class="change_imgs" src="'+basePath+'/images/choice_02.png"/>'
                                                    +'</a>'
                                                +'</li>'
                                                +'<li class="title">'
                                                    +'<a href="'+basePath+'/ea/industry/ea_getCompanyHome.jspa?ccompanyId='+com[2]+'">'
                                                        +'<img class="logopic" src="'+basePath+(com[3] != null ? com[3]:"images/WFJClient/PersonalJoining/logo@2x.png")+'">&nbsp;'+com[1]
                                                    +'</a>'
                                                    +'<input type="hidden" value="'+com[0]+'" class="fcomid"/>'
                                                +'</li>'
                                                +'<li class="changefont change"><a href="javascript:;">编辑</a></li>'
                                            +'</ul>'
                                        +'</div>'
                                    +'</div>'
                                +'</div>'
                                +scInnerHtml
            				+'</div>';
        }
    }
    return innerHtml;
}