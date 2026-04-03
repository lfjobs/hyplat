var d;
var ppid = "";
var orderid;
$(function() {

  if(pos=="1"||posNum!=""){
         $(".change_imgs").hide();
         $(".qx").html("&nbsp;");

          $(".change_img").each(function(){
              $(this).attr("src",pahe + "/images/choice_01.png");
              $(this).parents("ul").addClass("selected");
          });
          $(".change_img").hide();
          $(".allchangeimg").parents(".wfj12_004").find(".dx").val("1");
          $(".allchangeimg").hide();

      //计算
      $("span#zh").html("0");
      $("div#allprod").find(".oneProduct").each(function(){

              var price=$(this).find("#price").text();
              var num=$(this).find("#num").text();
              sumCart(price,num);

      });
  }
	
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
	// 样式处理结束


    if(pos=="1"){
        if(coID=="") {
            $(".wfj12_004_commit").show();
            $(".wfj12_004_commit").text("生成收款码");

        }else{
            $(".wfj12_004_commit").remove();
            $(".wfj12_004_allmoney").css("width","70%");

		}
        $(".wfj12_004_allchecked").remove();
        $(".ordercommit").show();

    }else{
        $(".ordercommit").hide();
        $(".wfj12_004_commit").text("结算");

    }
    if(num=="0"){
        $(".kgwc").show();
        $(".wfj12_004_bottom").css("display","none");
    }else{

        $(".kgwc").hide();
        $(".wfj12_004_bottom").css("display","display");
    }
	//全部的选择
	$(".allchangeimg").click(function() {

		if ($(this).attr("src") == pahe + "/images/choice_02.png") {
			$(this).attr("src", pahe + "/images/choice_01.png");
			$(".bj01").find(".change_imgs").attr("src",pahe + "/images/choice_01.png");
			$(".bj01").find(".change_img").attr("src",pahe + "/images/choice_01.png");
			

			$(".change_img").each(function(){
				$(this).attr("src",pahe + "/images/choice_01.png");
				$(this).parents("ul").addClass("selected");
			});
			$(this).parents(".wfj12_004").find(".dx").val("1");
		} else {
			$(this).attr("src", pahe + "/images/choice_02.png");
			$(".bj01").find(".change_imgs").attr("src",pahe + "/images/choice_02.png");
			$(".bj01").find(".change_img").attr("src",pahe + "/images/choice_02.png");
			
			$(".change_img").each(function(){
				$(this).attr("src",pahe + "/images/choice_02.png");
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
		if ($(this).attr("src") == pahe + "/images/choice_02.png") {//公司全选,选中状态(图片初始为未选中)
			var fcomid = $(this).parents("ul").find(".fcomid").val();//获取公司
			$("img."+fcomid).each(function(){
				if($(this).attr("src")==pahe + "/images/choice_01.png"){//判断之前已选中商品.先减去
					var price=$(this).parents(".wfj12_004_product").find("#price").text();
					var num=$(this).parents(".wfj12_004_product").find("#num").text();
					sumCart(-price,num);
				}
				
			});
			$("img."+fcomid).each(function(){//循环所有商品累加.
				$(this).attr("src",pahe + "/images/choice_01.png");
				$(this).parents("ul").addClass("selected");
				var price=$(this).parents(".wfj12_004_product").find("#price").text();
				var num=$(this).parents(".wfj12_004_product").find("#num").text();
				sumCart(price,num);
			});
			$(this).parents(".bj01").find(".change_imgs").attr("src",pahe + "/images/choice_01.png");
			$(this).parents(".wfj12_004_con").find(".dx").val("1");
		} else {
			var fcomid = $(this).parents("ul").find(".fcomid").val();
			$("img."+fcomid).each(function(){
				if($(this).attr("src")==pahe + "/images/choice_01.png"){//判断之前已选中商品.减去
					$(this).attr("src",pahe + "/images/choice_02.png");
					$(this).parents("ul").removeClass("selected");
					price=$(this).parents(".wfj12_004_product").find("#price").text();
					num=$(this).parents(".wfj12_004_product").find("#num").text();
					sumCart(-price,num);
				}
				
			});
			$(this).parents(".bj01").find(".change_imgs").attr("src",pahe + "/images/choice_02.png");
			$(this).parents(".wfj12_004_con").find(".dx").val("2");
		}
		
	});
	$(".change_img").click(function() {
		
		var price=0;
		var num=0;
		price=$(this).parents(".wfj12_004_product").find("#price").text();
		num=$(this).parents(".wfj12_004_product").find("#num").text();
		if ($(this).attr("src") == pahe + "/images/choice_02.png") {
			$(this).attr("src", pahe + "/images/choice_01.png");
			$(this).parents("ul").addClass("selected");
		} else {
			price=-price;
			$(this).attr("src", pahe + "/images/choice_02.png");
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
				var num=$(this).find("input.ls").val();
				var ppid=$(this).find("input#editppid").val();
                var stardard=$(this).prev(".oneProduct").find("li.stard").text();
				parmsDel.push(ppid+'#'+num+'@'+stardard+"zz");
			});
			if(parmsDel.join("")!=""){
			var purl=pahe+"ea/wfjshop/sajax_ea_changeCartNum.jspa";
			$.ajax({
				url:purl,
				type:"post",
				data:"lei="+parmsDel.join("")+"&posNum="+posNum,
				success:function suc(data){}
			});
			}
			$(this).text("编辑");
			var fcomid = $(this).parents("ul").find(".fcomid").val();
			$("div.changes").find("img."+fcomid).each(function(){
				$(this).parents(".bj01").find(".proinfo02").slideDown(500);
				$(this).parents(".bj01").find(".wfj12_004_changedelete").removeClass("oprOpen").slideUp(500);
			});
			
			// 点击完成后将改变的钱跟 数量 从新覆盖到下面
			//$(this).parents(".wfj12_004_con").find()

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

	// 提交订单页面
	// 获取ppid 跟数量

	$(".wfj12_004_commit").click(function() {
		if ($("#zh").text() == "0"){
			alert("必须选择一件产品");
			return;
		}
		/*if(scandc=="scandc"){
            var money = $("#zh").text();
            document.location.href  = pahe
                + "ea/restaurant/ea_queryLoginName.jspa?companyId="+companyId+"&ccompanyId=" + ccompanyId + "&morre="
                + money+"&scandc="+scandc;

		}else */
        if(posNum!=null&&posNum!="") {

            var directUrl = "";
            //生成订单号
            $.ajax({
                url:pahe+"/ea/sm/sajax_ea_getJumBycomID.jspa?d="+new Date(),
                type:"get",
                async: false,
                dataType : "json",
                data:{
                    posNum:posNum,
                },
                success:function(data){
                    var mb=eval("("+data+")");
                    var jum=mb.jum;
                    var totalMoney = $("#zh").text();
                    var shuliang = $("#shuliang").text();
                  //  var   directUrl = pahe + "ea/wfjshop/ea_getcitys.jspa?pos=&pricetype=";



                    document.location.href = pahe+"page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum="+jum+"&totalMoney="+totalMoney+"&totalNum="+shuliang+"&posNum="+posNum+"&fh=2";


                },error:function(data){
                    console.log("生成单号");
                }
            });

            return false;
        }
		if(pos=="1"){

              document.location.href = pahe+"ea/assicode/ea_genJieSunCode.jspa?companyId="+companyId;

		}else{

            $("#form").attr("action", pahe + "/ea/wfjshop/ea_getcitys.jspa?pos="+pos+"&pricetype="+pricetype);
            pinjie();
            $("#submit").click();
		}
	});

     //生成订单
	$(".ordercommit").click(function(){
        document.location.href = pahe+"ea/assicode/ea_getClientShopDetail.jspa?companyId="+companyId;

	});
	$(".dele").click(function() {
		var ddi = $(this).parents(".wfj12_004_product").find(".ppid").val();

		var $p = $(this).parents(".zcp");
        var cartid = $p.find(".cartid").val();
		var ulp = pahe + "/ea/wfjshop/sajax_ea_delecity.jspa";
		$this = $(this);
		$.ajax({
			type : "GET",
			url : ulp,
			dataType : "json",
			data:{
                cartid:cartid,
				posNum:posNum
			},
			success : function(data) {
				var json = eval('(' + data + ')');
				if (json == null) {
					alert("系统错误 请联系管理员");
				} else if (json.succes == '1') {
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
                    if(len-1<=0){
                        $parents.remove();
					}
					if($(".changes").length==0){
                        $(".kgwc").show();
                        $(".wfj12_004_bottom").css("display","none");
					}

				}
			}
		});

	});

    dealPro();

});
// 获取选中的产品 ID 跟数量拼接 成 ppid-数量*ppid-数量-stardard
function pinjie() {
	
	//拼接选中的公司
	
	var pid = "";
	var comid = "";
	$(".selected").each(function(){
		var ppid = $(this).parent().find(".ppid").val();
		var stardard = $(this).parents(".wfj12_004_product").find(".stard").text();
	   var companyid = $(this).parent().find(".companyId").val();
		if(comid.indexOf(companyid)==-1){
			comid+=companyid+",";
		}
		pid+=ppid+"-"+(stardard==""?"*":stardard)+"zz";
	});

	$("#companyIds").val(comid);
	$("#ppic").val(pid);

	


}
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
//处理促销品
function dealPro(){
    var str = "";
	$(".zcp").each(function(){
        var ppid  = $(this).find(".ppid").val();

        var len = $("."+ppid+"zcp").length;
        if(len>1){
        	var xh = 1;
            $("."+ppid+"zcp").each(function(){
            	if(xh!=len) {
                    var cartid = $(this).find(".cartid").val();
                    xh++;
                    $("."+cartid).remove();
                }
			});
		}
    });
}