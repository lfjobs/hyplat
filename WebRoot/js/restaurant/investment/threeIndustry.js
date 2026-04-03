function fanhui(){
 var url = basePath + "/ea/industry/ea_getiInvestment.jspa?stype=two&codesId="+codesId+"&ajax=";
	document.location.href = url;
 }
 function dianji(obj){
 	var xitong = $(obj).find("#xitong").html();
 	var ricep = $(obj).find("#ricep").val();
 	var ppid = $(obj).find("#ppid").val();
 	var image = $(obj).find("#srp").attr("src");
 		
 	var url = basePath + "/page/conacts/investment/fours.jsp?xitong="+xitong+"&ricep="+ricep+"&ppid="+ppid+"&image="+image;
	document.location.href = url;
 }	
    
    $(function(){
        $(".top").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
        $(".top").find("li").attr("style","width:15%;font-size:"+$(window).height()*0.03+"px;");
        $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
        $(".top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.035+"px; letter-spacing:4px; ");

        $(".header_hide").css("height",$(".top").height()+"px")
        $(".main_hide").css({"height":$(window).height()-$(".top").height()+"px","overflow":"auto"})
        $(".biaot").css({"line-height":$(".ico1").height()+"px","color":"#f75311"});
        $(".nav_right").css({"width":$(".nav_left").width()+"px","height":$(".nav_left").height()+"px"})
		$(".fix_top").css("height",$(".navbar-fixed-top").height()+"px")
				
		$(".list-group-item").css({"overflow":"hidden"})
		$(".nav_img").css({"width":$(window).width()*0.08+"px","height":$(window).width()*0.08+"px"})
		$(".list-group-item-text").css({"line-height":$(".list-group-item-img").height()+"px","margin-left":"15px"})
		$(".list-group").css({"padding":"0 10px"})
		$(".list-group a").css({"border":"none","border-bottom":"1px solid #e3e3e3","border-bottom-right-radius":"0","border-bottom-left-radius":"0","margin-bottom":"0","border-bottom":"1px solid #e3e3e3"})
		
				/*//var url = basePath + "/ea/industry/sajax_ea_huiyuan.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&zlyq=1";
				var ccompantId="contactCompany20101230UB4U5884S30000000176";
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : false,
							dataType : "json",
							success : function(data) {
								var member = eval("(" + data + ")");
								var page= member.productList;
								var pageto = member.beans;
								var cus = member.cus;
								if(cus==null){
									
									open(basePath+ "page/WFJClient/NewLogin.jsp","_self");
									
								}
								console.log(page);
								console.log(pageto);
								var car ="";
							for(var i = 0; i < page.length; i++){
							var gs=page[i];
								car +="<div class='lis_K'><a onclick='dianji(this)'><div class='col-xs-10 pull-left'>";
								car +="";
								car +="<img class='li_lis_img pull-left' id='srp' src='"+basePath+gs[3]+"'/>";
								
								car +="<span class='li_lis pull-left' style='line-height:2rem;' id='xitong' >"+gs[0]+"</span><input type='hidden' id='ricep' value='"+gs[2]+"'><input type='hidden' id='ppid' value='"+gs[1]+"'></div>";
								car +="<div class='col-xs-2 pull-left'>";
								car +="</div></a></div>";
							}
							$("#divs").append(car);
							},
							error : function(data) {
								alert("获取信息失败");
							}
						});*/
 })