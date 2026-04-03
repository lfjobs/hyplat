$(function() {

    findHotScaleGoods();
      //确定查询
    $(".cfm").click(function(){
     var  parameter = $.trim($(this).parent().find(".parameter").val());
     if(parameter!=""){
         findGoods(parameter);

     }

	});

    //关闭搜索框
	$("#del-3").click(function(){
        $(".alert_sssp_").hide();

	});

	//查询选择

    $(document).on("click",".salegoods tr",function(){
    	$(".active").removeClass("active");
    	 $(this).addClass("active");

    });

    //查询结果确定
	$("#sure2").click(function(){
		if($(".alert_sssp_ .active").length==0){
		     	return;
		}
     $(".parameter").val("");
     $(".alert_sssp_").hide();
     var goodsname = $(".active").find(".name").text();
     var plu = $(".active").find(".hh").text();
     var price = $(".active").find(".tm").text();
     var kpc = $(".active").find(".dj").text();
     var aal = $(".active").find(".aal").text();
      $(".scalemain .goodsname").text(goodsname+"("+plu+")");
      $(".scalemain .price").text(price);
      $(".scalemain .hhh").text(aal);
        showWeight(kpc);
	});




    /*无码称重-热销产品*/
    $(document).on("click",".alert_wed .hotsale li",function () {
        $(".alert_wed .goodsname").text($(this).find(".gp").text());
        $(".alert_wed .price").text($(this).find(".pr").text());
        $(".alert_wed .hhh").text($(this).find(".al").text());
        showWeight($(this).find(".prc").text());
    });


    $("#confirm").click(function () {
        $(".alert_weigh_").hide();
        $(".parameter").val("");
    });

    //打印
    $("#print").click(function(){
        var name =  $(".scalemain .goodsname").text();
        var goodsname = name.substring(0,name.indexOf("("));
        var al = $(".scalemain .hhh").text();
        var price=$(".scalemain .price").text();
        var money=$(".scalemain .totalMoney").text();
        if(money==""||Number(money)==0){
                  return false;
        }
        var value="";
        if(!$(".num").is(':hidden')) {
            value = $("#inputnum").val();
        }else{
            value = $(".scalemain .wvalue").val();
        }


        printLabel(goodsname,al,price,value,money,getEnBarCode(al,money));

    });
    //输入数量
    $("#inputnum").on("input",function(e){
        var price = $(".scalemain .price").text();
        if(!$(".num").is(':hidden')) {
            $(".totalMoney").text((Number(price) * Number($("#inputnum").val())).toFixed(2));
        }
    });

    //输入数量键盘
    $("#inputnum").on("focus",function () {
        $(this).addClass("jp-s");
        $("#wed_ipt").removeClass("jp-s");
        $("#wed_ipt-3").removeClass("jp-s");
    });
    //plu/名称/拼音码键盘
    $("#wed_ipt").on("focus",function () {
        $(".jp-se").show();
        $(this).addClass("jp-s");
        $("#inputnum").removeClass("jp-s");
        $("#wed_ipt-3").removeClass("jp-s");
    });
    //弹框plu/名称/拼音码键盘
    $("#wed_ipt-3").on("focus",function () {
        $(this).addClass("jp-s");
        $("#inputnum").removeClass("jp-s");
        $("#wed_ipt").removeClass("jp-s");
    });

});
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

//打印标签
function printLabel(goodsname,plu,price,value,money,barcode) {

    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();


    LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM1'));

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
    LODOP.SET_PRINTER_INDEX("");
    //CreateOneFormPage();
    //LODOP.SET_PRINT_MODE("TRYLINKPRINTER_NOALERT",true);//这个语句设置网络共享打印机连接不通时是否提示一下
    //if (LODOP.SET_PRINTER_INDEX(0))//这里指定第0号打印机打印
      //  LODOP.PRINT();


//  LODOP.PREVIEW();
    LODOP.PRINT();//直接打印
}



//称重页面隐藏显示
function showWeight(kpc){
    if(kpc=="KGM"){
        $(".scalemain .weight").show();
        $(".scalemain .num").hide();
        $(".alert_wed .scale1").show();
        $(".alert_wed .scale2").hide();

    }else{
        $(".scalemain .weight").hide();
        $(".scalemain .num").show();
        $(".alert_wed .scale1").hide();
        $(".alert_wed .scale2").show();
        $(".scalemain .num").find("input").focus();

    }
    $(".scalemain .totalMoney").text("0.00");
    $(".wvalue").val("0");
    $("#inputnum").val("");
    $(".scale1").text("请将以下商品置于电子称上");
    $(".parameter").val("");

}
//查询热销称重商品
function findHotScaleGoods(){


    var url = basePath + "ea/scale/sajax_ea_findHotSaleGoods.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data:{
            companyID:companyID
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var prolist = member.prolist;
            var htmlstr = new Array();
            for(var i = 0;i<prolist.length;i++){
                htmlstr.push("<li>");
                htmlstr.push("<span class='gp'>"+prolist[i][1]+"("+prolist[i][6]+")</span>");
                htmlstr.push("<span class='pr'style='display: none;'>"+prolist[i][3]+"</span>");
                htmlstr.push("<span class='prc'style='display: none;'>"+prolist[i][5]+"</span>");
                htmlstr.push("<span class='al'style='display: none;'>"+prolist[i][7]+"</span>");
                htmlstr.push("</li>");

			}
			$(".hotsale").html(htmlstr.join(""));


        },
        error : function cbf(data) {
            console.log("失败")
        }

    })

}


//查询称重商品
function findGoods(parameter){


    var url = basePath + "ea/scale/sajax_ea_findGoods.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data:{
            companyID:companyID,
	        parameter:parameter
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var prolist = member.prolist;
            $(".salegoods").html("");
            if(prolist!=null&&prolist.length==1){
                //如果查询结果只有一个直接跳转回称重页面
                $(".scalemain .goodsname").text(prolist[0][1]+"("+prolist[0][6]+")");
                $(".scalemain .price").text(prolist[0][3]);
                $(".scalemain .hhh").text(prolist[0][7]);
                showWeight(prolist[0][5]);
			}else if(prolist.length==0){
                $(".alert_weigh_").show();
            } else{
            	//否则跳转到查询页面
                var html = new Array();
                for(var i = 0;i<prolist.length;i++){
                    html.push("<tr>");
                    html.push("<td class='name'>"+prolist[i][1]+"</td>");
                    html.push("<td class='hh'>"+prolist[i][6]+"</td>");
                    html.push("<td class='tm'>"+prolist[i][3]+"</td>");
                    html.push("	<td class='dj'>"+prolist[i][5]+"</td>");
                    html.push("	<td class='aal' style='display: none;'>"+prolist[i][7]+"</td>");
                    html.push("</tr>");

                }
                $(".salegoods").html(html.join(""));
				$(".alert_sssp_").show();
            }




        },
        error : function cbf(data) {
            console.log("失败")
        }

    })

}


/*键盘*/
/*数字1-9*/
function btnNum_onclick(i) {
    var values = $('.jp-s').val();
    $('.jp-s').val(values+i);

    var id = $('.jp-s').attr("id");
    if(id=="inputnum"){
        var price = $(".scalemain .price").text();
        if(!$(".num").is(':hidden')) {
            $(".totalMoney").text((Number(price) * Number($("#inputnum").val())).toFixed(2));
        }

    }

}
/*点*/
function dian() {
    var values = $('.jp-s').val();
    var dian = ".";
    $('.jp-s').val(values+dian);

}
/*清空*/
function clearText() {
    $('.jp-s').val("");
}
/*删除*/
function delText() {
    var value = $('.jp-s').val();
    var str = value.substring(0,value.length-1);
    $('.jp-s').val(str);
}
