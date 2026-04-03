$(function(){
	institution("",0,"","");
	$(".ifrom1").find("iframe").attr("src",basePath+"ea/gooddesign/ea_getGoodDesignList.jspa?1=1"+"&fiveClear="+fiveClear).attr("name","ea/gooddesign/ea_getGoodDesignList.jspa?1=1");
	
	$(".application ul .institution").live("click",function(e){
		institution($(this).attr("id"),$(this).attr("app"),$(this).find("span").text(),"");
	});
	$(".tab_top").find(".div1").live("click",function(){
		if($(this).attr("id"))
			institution($(this).attr("id"),$(this).attr("app"),goodsName+" - "+$(this).find("span").text(),"00");
		else
			institution("",1,goodsName+" - 组织机构","00");
	});
	
	$("#ret").click(function(){
		var len=parseInt($(".tab_top").find(".div1").length)-1;
		console.log(len);
		if(len!=1){
			if(len!=2){
				var id=$(".tab_top").find(".div1").eq(len-1).attr("id");
				var gn=goodsName+" - "+$(".tab_top").find(".div1").eq(len-1).find("span").text();
				var app=$(".tab_top").find(".div1").eq(len-1).attr("app");
				institution(id,app,$(".tab_top").find(".div1").eq(len-1).find("span").text(),"00");
			}else{
				institution("",1,"组织机构","00");
			}
		}
	});
});



















function institution(ppID,app,$goods,road){
	$.ajax({
		url:basePath+"ea/prodesign/sajax_ea_ajaxGetOrganizational.jspa?",
		type:"post",
		data:{"ppID":ppID},
		async : false,
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list; 
			if(list.length>0)
				$(".institution").remove();
			var ir=1;
			if($goods!=""&&list.length>0){
				$(".application").find(".active").find("img").eq(1).attr("src",basePath+"images/ea/production/app"+app+".png");
				$(".application").find(".active").find("span").text(goodsName+" - "+$goods);
				$(".application").find(".active").css("background","#fff");
				if(road!="00"){
					var $road=$("#road").clone(true).attr("style","").attr("id",ppID).attr("app",app);
					$road.find("span").text($goods);
					$(".tab_top").append($road);
				}else{
					if(ppID!="")
						$(".tab_top").find("#"+ppID).nextAll(".div1").remove();
					else
						$(".tab_top").find("#road").nextAll(".div1").remove();
				}
			}
			for(var i=0;i<list.length;i++){
				if(app<2)
					ir++;
				else
					ir=app;
				
				var $li=$("#organizational").clone(true).attr("id",list[i].ppID).attr("style","").attr("app",ir);
				$li.attr("name",list[i].goodsName).addClass("institution").attr("title",list[i].goodsName);
				$li.find("img").attr("src",basePath+"images/ea/production/app"+ir+".png");
				$li.find("span").text(list[i].goodsName);
				$(".application").find("ul").append($li);
			}
		},error:function(data){
			alert("获取数据失败");
		}
	});
	$(".ifrom1").find("iframe").attr("src",basePath+$(".ifrom1").find("iframe").attr("name")+"&fiveClear="+ppID+"&fiveClearName="+$goods);
	fiveClear=ppID;
}














/**
 * Created by AdFistrator on 2016/6/17 0017.
 */
$(document).ready(function(){
        //所有选项卡


    //内容
    $(".con-right").css("height",$(window).height()-$(".header").height()+"px");
    $(".con-right").css("margin-left",$(".con-left").width()+"px");
    $(".con-right").css("width",$(window).width()*1.0-$(".con-left").width()+"px");
    $(".content").css("height",$(window).height()-60+"px");
    $(".con-left").css("height",$(window).height()-60+"px");

    /*$("#myTab li").click(function(){
        $(this).attr("style","background-color:#4384E7;color:#fff;").siblings().attr("style","background-color:#E3E9F3;color:#000;");
    });*/
    $(".layer1 ul li").click(function(){
        $(this).attr("style","color:#FF974B;").siblings().attr("style","color:#000;");
    });

    $(".tab-content > .active").css("height",$(window).height()-$(".header").height()-$(".layer2").height()-$("#myTab").height()-$(".layer1").height()-10+"px");
    $(".tab-pane").css("height",$(window).height()-$(".header").height()-$(".layer2").height()-$("#myTab").height()-$(".layer1").height()-10+"px");
    //$(".layer1_ .tabItem").css("height",$(window).height()-$(".header").height()-$(".layer2").height()-$(".layer1").height()+"px");
    //$(".layer2_").css("height",$(window).height()-$(".header").height()-$(".layer2").height()-$(".layer1").height()+"px");

    $(".tabWrap2").css("height",$(".tab-content > .active").height()+"px");
    $(".layer4_").css("height",$(".tabWrap2").height()-60+"px");
    $(".layer4_ .tabItem").css("height",$(".layer4_").height()+"px");
    $(".layer5_").css("height",$(".tabWrap2").height()-120+"px");
    $(".layer5_ .tabItem").css("height",$(".layer5_").height()+"px");
    $(".layer4 ul li").click(function(){
       /* $(this).attr("style","background-color:#4384E7;color:#fff;").siblings().attr("style","background-color:#fff;color:#000;");*/
    });
    $(".layer5 ul li").click(function(){
        $(this).attr("style","border-bottom:1px solid #FA8D49;color:#FA8D49;").siblings().attr("style","border-bottom:none;color:#000;");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });

    $(".layer2 ul li").click(function(){
        $(".ice-img2_").removeClass("ice-img3");
        $(".ice-img2_").removeClass("ice-img4");
    });
    $(".layer2_ div ul li").click(function(){
        $(".ice-img2_").removeClass("ice-img2");
    });

    $("#zbjb").click(function(){
        $(".ice-img2_").addClass("ice-img2");
    });
    $("#zbjb2").click(function(){
        $(".ice-img2_").addClass("ice-img3");
    });
    $("#zbjb3").click(function(){
        $(".ice-img2_").addClass("ice-img4");
    });
    $("#zbjb4").click(function(){
        $(".ice-img2_").addClass("ice-img3");
    });
    


    $(".layer1 ul li:nth-child(1)").click(function(){
        $(".layer1-img1").css("display","none");
        $(".layer1-img1_").css("display","block");
        $(".layer1-img2").css("display","block");
        $(".layer1-img2_").css("display","none");
        $(".layer1-img3").css("display","block");
        $(".layer1-img3_").css("display","none");
        $(".layer1-img4").css("display","block");
        $(".layer1-img4_").css("display","none");
        $(".layer1-img5").css("display","block");
        $(".layer1-img5_").css("display","none");
    });
    $(".layer1 ul li:nth-child(2)").click(function(){
        $(".layer1-img1").css("display","block");
        $(".layer1-img1_").css("display","none");
        $(".layer1-img2").css("display","none");
        $(".layer1-img2_").css("display","block");
        $(".layer1-img3").css("display","block");
        $(".layer1-img3_").css("display","none");
        $(".layer1-img4").css("display","block");
        $(".layer1-img4_").css("display","none");
        $(".layer1-img5").css("display","block");
        $(".layer1-img5_").css("display","none");
    });
    $(".layer1 ul li:nth-child(3)").click(function(){
        $(".layer1-img1").css("display","block");
        $(".layer1-img1_").css("display","none");
        $(".layer1-img2").css("display","block");
        $(".layer1-img2_").css("display","none");
        $(".layer1-img3").css("display","none");
        $(".layer1-img3_").css("display","block");
        $(".layer1-img4").css("display","block");
        $(".layer1-img4_").css("display","none");
        $(".layer1-img5").css("display","block");
        $(".layer1-img5_").css("display","none");
    });
    $(".layer1 ul li:nth-child(4)").click(function(){
        $(".layer1-img1").css("display","block");
        $(".layer1-img1_").css("display","none");
        $(".layer1-img2").css("display","block");
        $(".layer1-img2_").css("display","none");
        $(".layer1-img3").css("display","block");
        $(".layer1-img3_").css("display","none");
        $(".layer1-img4").css("display","none");
        $(".layer1-img4_").css("display","block");
        $(".layer1-img5").css("display","block");
        $(".layer1-img5_").css("display","none");
    });
    $(".layer1 ul li:nth-child(5)").click(function(){
        $(".layer1-img1").css("display","block");
        $(".layer1-img1_").css("display","none");
        $(".layer1-img2").css("display","block");
        $(".layer1-img2_").css("display","none");
        $(".layer1-img3").css("display","block");
        $(".layer1-img3_").css("display","none");
        $(".layer1-img4").css("display","block");
        $(".layer1-img4_").css("display","none");
        $(".layer1-img5").css("display","none");
        $(".layer1-img5_").css("display","block");
    });

    $(".application ul li").click(function() {
        $(this).attr("style","background:#fff;").siblings().attr("style","background:#E3E9F3;");
    });

    //项目设计
    $(".layer2_ div ul #wupinsheji").click(function() {
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #danxiangmu-1").click(function() {
        $(this).find('.danxiangmu1').css("display","none");
        $(this).find('.danxiangmu1_').show();
        $('.danxiangmu2').show();
        $('.danxiangmu2_').css("display","none");
        
        $(".danxiangmu3_").css("display","none");
        $(".danxiangmu3").show();
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #danxiangmu-2").click(function() {
        $(this).find('.danxiangmu2').css("display","none");
        $(this).find('.danxiangmu2_').show();
        $('.danxiangmu1').show();
        $('.danxiangmu1_').css("display","none");
        
        $(".danxiangmu3_").css("display","none");
        $(".danxiangmu3").show();
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #danxiangmu-3").click(function() {
        $('.danxiangmu').show();
        $('.danxiangmu_').css("display","none");
        $(this).find('.danxiangmu3').css("display","none");
        $(this).find('.danxiangmu3_').show();
     
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #danxiangmu-4").click(function() {
        $('.danxiangmu').show();
        $('.danxiangmu_').css("display","none");
        $(this).find('.danxiangmu4').css("display","none");
        $(this).find('.danxiangmu4_').show();
       
        $(".danxiangmu3_").css("display","none");
        $(".danxiangmu3").show();
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #danxiangmu-5").click(function() {
        $('.danxiangmu').show();
        $('.danxiangmu_').css("display","none");
        $(this).find('.danxiangmu5').css("display","none");
        $(this).find('.danxiangmu5_').show();
        
        $(".danxiangmu3_").css("display","none");
        $(".danxiangmu3").show();
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    //项目立项
    $(".layer2_ div ul #design1").click(function() {
        $(this).find('.design1').css("display","none");
        $(this).find('.design1_').show();
        $('.design2').show();
        $('.design2_').css("display","none");
        $('.design3').show();
        $('.design3_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #design2").click(function() {
        $(this).find('.design2').css("display","none");
        $(this).find('.design2_').show();
        $('.design1').show();
        $('.design1_').css("display","none");
        $('.design3').show();
        $('.design3_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #design3").click(function() {
        $(this).find('.design3').css("display","none");
        $(this).find('.design3_').show();
        $('.design1').show();
        $('.design1_').css("display","none");
        $('.design2').show();
        $('.design2_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #design1_").click(function() {
        $(this).find('.design1-2').css("display","none");
        $(this).find('.design1-2_').show();
        $('.design2-2').show();
        $('.design2-2_').css("display","none");
        $('.design3-2').show();
        $('.design3-2_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #design2_").click(function() {
        $(this).find('.design2-2').css("display","none");
        $(this).find('.design2-2_').show();
        $('.design1-2').show();
        $('.design1-2_').css("display","none");
        $('.design3-2').show();
        $('.design3-2_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #design3_").click(function() {
        $(this).find('.design3-2').css("display","none");
        $(this).find('.design3-2_').show();
        $('.design1-2').show();
        $('.design1-2_').css("display","none");
        $('.design2-2').show();
        $('.design2-2_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });

    //项目生产
    $(".layer2_ div ul #ded1").click(function() {
        $(this).find('.ded1').css("display","none");
        $(this).find('.ded1_').show();
        $('.ded2').show();
        $('.ded2_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".dede1").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".dede1").find("li").eq(0).attr("src"));
        goodsName=$(".dede1").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #ded2").click(function() {
        $(this).find('.ded2').css("display","none");
        $(this).find('.ded2_').show();
        $('.ded1').show();
        $('.ded1_').css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".dede2").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".dede2").find("li").eq(0).attr("src"));
        goodsName=$(".dede2").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #ded2-1").click(function() {
        $(this).find('.ded2-1').css("display","none");
        $(this).find('.ded2-1_').show();
        $('.ded2-2').show();
        $('.ded2-2_').css("display","none");
        $(".shengchan_div1").show();
        $(".shengchan_div2").hide();
        $(".shengchan_div").hide();
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".shengchan_div1").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".shengchan_div1").find("li").eq(0).attr("src"));
        goodsName=$(".shengchan_div1").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #ded2-2").click(function() {
        $(this).find('.ded2-2').css("display","none");
        $(this).find('.ded2-2_').show();
        $('.ded2-1').show();
        $('.ded2-1_').css("display","none");
        $(".shengchan_div1").hide();
        $(".shengchan_div2").show();
        
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".shengchan_div2").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".shengchan_div2").find("li").eq(0).attr("src"));
        goodsName=$(".shengchan_div2").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".layer2_ div ul #ded3-1").click(function() {
        $(this).find('.ded3-1').css("display","none");
        $(this).find('.ded3-1_').show();
        $('.ded3-2').show();
        $('.ded3-2_').css("display","none");
        $('.ded3-3').show();
        $('.ded3-3_').css("display","none");
        $('.ded3-4').show();
        $('.ded3-4_').css("display","none");
    });
    $(".layer2_ div ul #ded3-2").click(function() {
        $(this).find('.ded3-2').css("display","none");
        $(this).find('.ded3-2_').show();
        $('.ded3-1').show();
        $('.ded3-1_').css("display","none");
        $('.ded3-3').show();
        $('.ded3-3_').css("display","none");
        $('.ded3-4').show();
        $('.ded3-4_').css("display","none");
    });
    $(".layer2_ div ul #ded3-3").click(function() {
        $(this).find('.ded3-3').css("display","none");
        $(this).find('.ded3-3_').show();
        $('.ded3-1').show();
        $('.ded3-1_').css("display","none");
        $('.ded3-2').show();
        $('.ded3-2_').css("display","none");
        $('.ded3-4').show();
        $('.ded3-4_').css("display","none");
    });
    $(".layer2_ div ul #ded3-4").click(function() {
        $(this).find('.ded3-4').css("display","none");
        $(this).find('.ded3-4_').show();
        $('.ded3-1').show();
        $('.ded3-1_').css("display","none");
        $('.ded3-2').show();
        $('.ded3-2_').css("display","none");
        $('.ded3-3').show();
        $('.ded3-3_').css("display","none");
    });

    //项目验收
    $(".layer2_ div ul #dyans1").click(function() {
        $(this).find('.dyans1').css("display","none");
        $(this).find('.dyans1_').show();
        $('.dyans2').show();
        $('.dyans2_').css("display","none");
        $('.dyans3').show();
        $('.dyans3_').css("display","none");
        $('.dyans4').show();
        $('.dyans4_').css("display","none");
        $('.dyans5').show();
        $('.dyans5_').css("display","none");
    });
    $(".layer2_ div ul #dyans2").click(function() {
        $(this).find('.dyans2').css("display","none");
        $(this).find('.dyans2_').show();
        $('.dyans1').show();
        $('.dyans1_').css("display","none");
        $('.dyans3').show();
        $('.dyans3_').css("display","none");
        $('.dyans4').show();
        $('.dyans4_').css("display","none");
        $('.dyans5').show();
        $('.dyans5_').css("display","none");
    });
    $(".layer2_ div ul #dyans3").click(function() {
        $(this).find('.dyans3').css("display","none");
        $(this).find('.dyans3_').show();
        $('.dyans1').show();
        $('.dyans1_').css("display","none");
        $('.dyans2').show();
        $('.dyans2_').css("display","none");
        $('.dyans4').show();
        $('.dyans4_').css("display","none");
        $('.dyans5').show();
        $('.dyans5_').css("display","none");
    });
    $(".layer2_ div ul #dyans4").click(function() {
        $(this).find('.dyans4').css("display","none");
        $(this).find('.dyans4_').show();
        $('.dyans1').show();
        $('.dyans1_').css("display","none");
        $('.dyans2').show();
        $('.dyans2_').css("display","none");
        $('.dyans3').show();
        $('.dyans3_').css("display","none");
        $('.dyans5').show();
        $('.dyans5_').css("display","none");
    });
    $(".layer2_ div ul #dyans5").click(function() {
        $(this).find('.dyans5').css("display","none");
        $(this).find('.dyans5_').show();
        $('.dyans1').show();
        $('.dyans1_').css("display","none");
        $('.dyans2').show();
        $('.dyans2_').css("display","none");
        $('.dyans3').show();
        $('.dyans3_').css("display","none");
        $('.dyans4').show();
        $('.dyans4_').css("display","none");
    });
    $(".layer2_ div ul #zyans1").click(function() {
        $(this).find('.zyans1').css("display","none");
        $(this).find('.zyans1_').show();
        $('.zyans2').show();
        $('.zyans2_').css("display","none");
        $('.zyans3').show();
        $('.zyans3_').css("display","none");
        $('.zyans4').show();
        $('.zyans4_').css("display","none");
        $('.zyans5').show();
        $('.zyans5_').css("display","none");
    });
    $(".layer2_ div ul #zyans2").click(function() {
        $(this).find('.zyans2').css("display","none");
        $(this).find('.zyans2_').show();
        $('.zyans1').show();
        $('.zyans1_').css("display","none");
        $('.zyans3').show();
        $('.zyans3_').css("display","none");
        $('.zyans4').show();
        $('.zyans4_').css("display","none");
        $('.zyans5').show();
        $('.zyans5_').css("display","none");
    });
    $(".layer2_ div ul #zyans3").click(function() {
        $(this).find('.zyans3').css("display","none");
        $(this).find('.zyans3_').show();
        $('.zyans1').show();
        $('.zyans1_').css("display","none");
        $('.zyans2').show();
        $('.zyans2_').css("display","none");
        $('.zyans4').show();
        $('.zyans4_').css("display","none");
        $('.zyans5').show();
        $('.zyans5_').css("display","none");
    });
    $(".layer2_ div ul #zyans4").click(function() {
        $(this).find('.zyans4').css("display","none");
        $(this).find('.zyans4_').show();
        $('.zyans1').show();
        $('.zyans1_').css("display","none");
        $('.zyans2').show();
        $('.zyans2_').css("display","none");
        $('.zyans3').show();
        $('.zyans3_').css("display","none");
        $('.zyans5').show();
        $('.zyans5_').css("display","none");
    });
    $(".layer2_ div ul #zyans5").click(function() {
        $(this).find('.zyans5').css("display","none");
        $(this).find('.zyans5_').show();
        $('.zyans1').show();
        $('.zyans1_').css("display","none");
        $('.zyans2').show();
        $('.zyans2_').css("display","none");
        $('.zyans3').show();
        $('.zyans3_').css("display","none");
        $('.zyans4').show();
        $('.zyans4_').css("display","none");
    });

    //项目成果
    $(".layer2_ div ul #cguo1").click(function() {
        $(this).find('.cguo1').css("display","none");
        $(this).find('.cguo1_').show();
        $('.cguo2').show();
        $('.cguo2_').css("display","none");
        $('.cguo3').show();
        $('.cguo3_').css("display","none");
    });
    $(".layer2_ div ul #cguo2").click(function() {
        $(this).find('.cguo2').css("display","none");
        $(this).find('.cguo2_').show();
        $('.cguo1').show();
        $('.cguo1_').css("display","none");
        $('.cguo3').show();
        $('.cguo3_').css("display","none");
    });
    $(".layer2_ div ul #cguo3").click(function() {
        $(this).find('.cguo3').css("display","none");
        $(this).find('.cguo3_').show();
        $('.cguo1').show();
        $('.cguo1_').css("display","none");
        $('.cguo2').show();
        $('.cguo2_').css("display","none");
    });
    $(".layer2_ div ul #cguo1-1").click(function() {
        $(this).find('.cguo1-1').css("display","none");
        $(this).find('.cguo1-1_').show();
        $('.cguo1-2').show();
        $('.cguo1-2_').css("display","none");
        $('.cguo1-3').show();
        $('.cguo1-3_').css("display","none");
    });
    $(".layer2_ div ul #cguo1-2").click(function() {
        $(this).find('.cguo1-2').css("display","none");
        $(this).find('.cguo1-2_').show();
        $('.cguo1-1').show();
        $('.cguo1-1_').css("display","none");
        $('.cguo1-3').show();
        $('.cguo1-3_').css("display","none");
    });
    $(".layer2_ div ul #cguo1-3").click(function() {
        $(this).find('.cguo1-3').css("display","none");
        $(this).find('.cguo1-3_').show();
        $('.cguo1-1').show();
        $('.cguo1-1_').css("display","none");
        $('.cguo1-2').show();
        $('.cguo1-2_').css("display","none");
    });


    //8.15
    $(".p1").css("right",$(".sp2").width()+45+"px");
   /* $(".div1").css("width",$(".sp2").width()+85+"px");*/

    $(".layer2 ul li").click(function(){
        /*$(this).css("color","#FF974B").siblings().css("color","#000");*/
    	 $(this).addClass("active").siblings().removeClass("active");
    });
    $(".layer2_ div ul li").click(function(){
       $(this).addClass("active").siblings().removeClass("active");
    });

    $(".application").css("height",$(".con-left").height()-$(".icon").height()-$(".con-left_txt").height()-50+"px");
    $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-40+"px");

    //设计
    $("#sheji").click(function(){
        $(".sheji").show().siblings().css("display","none");
        $(".tab_div").css("display","none");
        $(".sheji .layer2_ #myTab").show().siblings().css("display","none");
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-40+"px");
       
        $(".ifrom1").find("iframe").attr("src",basePath+$(".sheji").find("#myTab").find("li").attr("src")+"&fiveClear="+fiveClear).attr("name",$(".sheji").find("#myTab").find("li").attr("src"));
        goodsName=$(".sheji").find("#myTab").find("li").attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".sheji .sheji_ li:nth-child(1)").click(function(){
        $(".sheji #myTab").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".sheji").find("#myTab").find("li").attr("src")+"&fiveClear="+fiveClear).attr("name",$(".sheji").find("#myTab").find("li").attr("src"));
        goodsName=$(".sheji").find("#myTab").find("li").attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".sheji .sheji_ li:nth-child(2)").click(function(){
        $(".sheji #myTab1").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".sheji").find("#myTab1").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".sheji").find("#myTab1").find("li").eq(0).attr("src"));
        goodsName=$(".sheji").find("#myTab1").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".sheji .sheji_ li:nth-child(3)").click(function(){
        $(".sheji #myTab2").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".sheji").find("#myTab2").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".sheji").find("#myTab2").find("li").eq(0).attr("src"));
        goodsName=$(".sheji").find("#myTab2").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    //立项
    $("#lixiang").click(function(){
        $(".lixiang").show().siblings().css("display","none");
        $(".tab_div").css("display","none");
        $(".lixiang .layer2_ #myTab").show().siblings().css("display","none");
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-40+"px");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".lixiang").find("#myTab").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".lixiang").find("#myTab").find("li").eq(0).attr("src"));
        goodsName=$(".lixiang").find("#myTab").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".lixiang .lixiang_ li:nth-child(1)").click(function(){
        $(".lixiang #myTab").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".lixiang").find("#myTab").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".lixiang").find("#myTab").find("li").eq(0).attr("src"));
        goodsName=$(".lixiang").find("#myTab").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".lixiang .lixiang_ li:nth-child(2)").click(function(){
        $(".lixiang #myTab2").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".lixiang").find("#myTab2").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".lixiang").find("#myTab2").find("li").eq(0).attr("src"));
        goodsName=$(".lixiang").find("#myTab2").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    //生产
    $("#shengchan").click(function(){
        $(".shengchan").show().siblings().css("display","none");
        $(".tab_div").show();
        $(".shengchan_div").css("display","block").siblings().css("display","none");
        $(".shengchan .layer2_ #myTab").show().siblings().css("display","none");
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-$(".tab_div").height()-40+"px");

        $(".ifrom1").find("iframe").attr("src",basePath+$(".dede1").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".dede1").find("li").eq(0).attr("src"));
        goodsName=$(".dede1").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
        
      
    });
    $(".shengchan .shengchan_ li:nth-child(1)").click(function(){
        $(".shengchan #myTab").css("display","block").siblings().css("display","none");
        $(".shengchan_div").css("display","block").siblings().css("display","none");
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-$(".tab_div").height()-40+"px");

        if($("#zbjb").hasClass("active")){
            $(".layer5").css("display","block");
        }else{
            $(".layer5").css("display","none");
        }
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".dede1").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".dede1").find("li").eq(0).attr("src"));
        goodsName=$(".dede1").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".shengchan .shengchan_ li:nth-child(2)").click(function(){
        $(".shengchan #myTab2").css("display","block").siblings().css("display","none");
        $(".shengchan_div1").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-$(".tab_div").height()-40+"px");

        $(".ifrom1").find("iframe").attr("src",basePath+$(".shengchan_div1").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".shengchan_div1").find("li").eq(0).attr("src"));
        goodsName=$(".shengchan_div1").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".shengchan .shengchan_ li:nth-child(3)").click(function(){
    	
        $(".shengchan #myTab3").css("display","block").siblings().css("display","none");
        $(".shengchan_div3").siblings().css("display","none");
        if($("#ded3-4").hasClass("active")){
            $(".shengchan_div3").css("display","block");
        }else{
            $(".shengchan_div3").css("display","none");
        }
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-$(".tab_div").height()-40+"px");

        $(".ifrom1").find("iframe").attr("src",basePath+$("#myTab3").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$("#myTab3").find("li").eq(0).attr("src"));
        goodsName=$("#myTab3").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $("#ded1").click(function(){
        $(".dede1").show().siblings().css("display","none");
    });
    $("#ded2").click(function(){
        $(".dede2").show().siblings().css("display","none");
    });
    $(".shengchan_div ul li").click(function () {
        $(".tab_d").css("display","none");
    });
    $(".xiangmupinggu2 ul li:nth-child(2)").click(function(){
    	$(".zuzhuang").show();
    	 if($(this).attr("src")){
         	$(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
             goodsName=$(this).attr("tagging");
             $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
         }else{
         	
         	$(".ifrom1").find("iframe").attr("src",basePath+$("."+$(this).parent().attr("name")).find("."+$(this).attr("name")).find("li").eq(0).attr("src")+"&fiveClear="+fiveClear);
             goodsName=$("."+$(this).parent().attr("name")).find("."+$(this).attr("name")).find("li").eq(0).attr("tagging");
             $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
         }
    });
    $(".dede1 li").click(function(){
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-$(".tab_div").height()-$(".tab_d").height()-20+"px");
    });
    $("#zbjb").click(function(){
        $(".tab_d").show();
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-$(".tab_div").height()-$(".tab_d").height()-20+"px");

    });
    $(".shengchan #myTab3 li").click(function(){
        $(".shengchan_div3").css("display","none");
    });
    $("#ded3-4").click(function () {
        $(".shengchan_div3").css("display","block");
    });
    
    //9.8
    $(".zzcpgl").click(function(){
    	$(".zuzhuang").hide();
    	$(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });

    //验收
    $("#yanshou").click(function(){
        $(".yanshou").show().siblings().css("display","none");
        $(".tab_div").css("display","none");
        $(".yanshou .layer2_ #myTab").show().siblings().css("display","none");
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-40+"px");

        $(".ifrom1").find("iframe").attr("src",basePath+$(".yanshou").find("#myTab").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".yanshou").find("#myTab").find("li").eq(0).attr("src"));
        goodsName=$(".yanshou").find("#myTab").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".yanshou .yanshou_ li:nth-child(1)").click(function(){
        $(".yanshou #myTab").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".yanshou").find("#myTab").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".yanshou").find("#myTab").find("li").eq(0).attr("src"));
        goodsName=$(".yanshou").find("#myTab").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".yanshou .yanshou_ li:nth-child(2)").click(function(){
        $(".yanshou #myTab2").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".yanshou").find("#myTab2").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".yanshou").find("#myTab2").find("li").eq(0).attr("src"));
        goodsName=$(".yanshou").find("#myTab2").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".yanshou").find(".tabItem").find("li").click(function(){
    	 $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")).attr("name",$(this).attr("src")+"&fiveClear="+fiveClear);
         goodsName=$(this).attr("tagging");
         $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    //成果
    $("#chengguo").click(function(){
        $(".chengguo").show().siblings().css("display","none");
        $(".tab_div").css("display","none");
        $(".chengguo .layer2_ #myTab").show().siblings().css("display","none");
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-40+"px");

        $(".ifrom1").find("iframe").attr("src",basePath+$(".chengguo").find("#myTab").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".chengguo").find("#myTab").find("li").eq(0).attr("src"));
        goodsName=$(".chengguo").find("#myTab").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".chengguo .chengguo_ li:nth-child(1)").click(function(){
        $(".chengguo #myTab").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".chengguo").find("#myTab").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".chengguo").find("#myTab").find("li").eq(0).attr("src"));
        goodsName=$(".chengguo").find("#myTab").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });
    $(".chengguo .chengguo_ li:nth-child(2)").click(function(){
        $(".chengguo #myTab2").css("display","block").siblings().css("display","none");
        
        $(".ifrom1").find("iframe").attr("src",basePath+$(".chengguo").find("#myTab2").find("li").eq(0).attr("src")+"&fiveClear="+fiveClear).attr("name",$(".chengguo").find("#myTab2").find("li").eq(0).attr("src"));
        goodsName=$(".chengguo").find("#myTab2").find("li").eq(0).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });

    $(".chengguo .tabItem  li").click(function(){        
    	
    	if(fiveClear){
            var fiveClearName=$(".application").find("#"+fiveClear).find("span").text();
            $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName).attr("name",$(this).attr("src"));
    	}else{
            $(".ifrom1").find("iframe").attr("src",basePath+$(this).attr("src")+"&fiveClear="+fiveClear).attr("name",$(this).attr("src"));
    	}
        goodsName=$(this).attr("tagging");
        $(".application").find(".active"). find("span").text(goodsName+" - "+$(".application").find(".active"). find("span").text().split(" - ")[1]);
    });

    $(".lf_click").click(function(){
    	$(".eject").hide();
    	$(".eject").find(".tabTitle").hide();
    	$("."+$(this).parent().attr("name")).show();
    	$("."+$(this).parent().attr("name")).find("."+$(this).attr("name")).show();
    	
    	$("."+$(this).parent().attr("name")).find("."+$(this).attr("name")).find("li").attr("style","border-bottom:none;color:#000;");
    	$("."+$(this).parent().attr("name")).find("."+$(this).attr("name")).find("li").eq(0).attr("style","border-bottom:1px solid #FA8D49;color:#FA8D49;");
        $(".ifrom1").css("height",$(".con-right").height()-$(".tab_top").height()-$(".tab").height()-$(".layer1").height()-$(".tab_div").height()-$(".tab_d").height()-20+"px");  
    });

    $(".layer1 > ul >li").click(function(){
    	$(".layer2_ div ul li:nth-child(1)").addClass("active").siblings().removeClass("active");
        $(".layer4 ul li:nth-child(1)").addClass("active").siblings().removeClass("active");
        $(".layer2 > ul > li:nth-child(1)").addClass("active").siblings().removeClass("active");
        $(".shengchan #myTab").addClass("block");
        $(".dede1").show().siblings().css("display","none");
        $(".shengchan_div").css("display","block");
       $(".layer2_ div ul li:nth-child(1)").find("a").find("img:first-child").css("display","none").parent().parent().siblings().find("a").find("img:first-child").css("display","inline");
       $(".layer2_ div ul li:nth-child(1)").find("a").find("img:last-child").css("display","inline").parent().parent().siblings().find("a").find("img:last-child").css("display","none");
        
       $(".eject").css("display","none");
    });
    $(".layer2 > ul > li").click(function(){
    	$(".layer2_ div ul li:nth-child(1)").addClass("active").siblings().removeClass("active");
        $(".layer4 ul li:nth-child(1)").addClass("active").siblings().removeClass("active");
      
        $(".dede1").show().siblings().css("display","none");
        $(".shengchan #myTab").addClass("block");
        $(".layer2_ div ul li:nth-child(1)").find("a").find("img:first-child").css("display","none").parent().parent().siblings().find("a").find("img:first-child").css("display","inline");
        $(".layer2_ div ul li:nth-child(1)").find("a").find("img:last-child").css("display","inline").parent().parent().siblings().find("a").find("img:last-child").css("display","none");
         
    });
    
    $(".layer2_1 div ul li").click(function(){
    
        $(".shengchan_div ul li:nth-child(1)").addClass("active").siblings().removeClass("active");
        $(".eject").css("display","none");
    
    });

    /*  // var num1=num2=num3=0
      window.onload = window.onresize = function(){
          //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
          //获取窗口的尺寸
          var clientWidth = document.documentElement.clientWidth;
          //console.log(clientWidth);
          //通过屏幕宽度去设置不同的后台根字体的大小
          //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
          document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
      }*/


});
