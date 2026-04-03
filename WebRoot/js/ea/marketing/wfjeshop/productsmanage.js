/**
 * 
 */ 
$(function(){

		$(".top").find("li").eq(2).find("img").attr("style","height:"+$(".top").height()*0.7+"px;");
        $(".main_lis_left").css({"height":$(".main_lis_right").height()+"px"});
        $(".main_hide").css("height",$(window).height()-$(".top").height()-$(".main_nav").height()-$("footer").height()+"px");
        
    	var u = window.navigator.userAgent;
    	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

	if (companyId==null||companyId==""){
		alert("你还没有店铺，请先升级为店铺会员再发布产品");
		window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
		history.back();
		/*let prostr="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
		if(flag=="onsale"){
			$(".onsale").append(prostr);
		}
		else{
			$(".oncang").append(prostr);
		}*/
	}else {
		loaded();
	}
        //
    	// if(isAndroid==true){
    	// 	var obj = document.getElementById("returnClick");
    	// 	obj.setAttribute("href","#");
    	// 	obj.setAttribute("onclick", "retAndroid()");
    	// }else if(isiOS==true){
    	// 	var obj = document.getElementById("returnClick");
    	// 	obj.setAttribute("href","#");
    	// 	obj.setAttribute("onclick", "retIOS()");
    	// }
         $(".main_nav li").click(function(){
            $(".main_nav li div").removeClass("on");
            $(this).find("div").addClass("on");
            if($(this).text()=="出售中"){
            	$(".onsale").html("");
            	$(".oncang").html("");
            	$(".main").scrollTop(0);
            	pagenumber=0;
            	flag="onsale";           	
                $(".onsale").show();
                $(".oncang").hide();
				/*if (companyId==null||companyId==""){
					//alert("你不是公司会员，请先升级为公司会员再发布产品");
					//window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
					//history.back();
					let prostr="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
					if(flag=="onsale"){
						$(".onsale").append(prostr);
					}
					else{
						$(".oncang").append(prostr);
					}
				}else {*/
					loaded();
				//}

            }else if($(this).text()=="仓库中"){
            	$(".onsale").html("");
            	$(".oncang").html("");
            	$(".main").scrollTop(0);           
            	pagenumber=0;           
            	flag="oncang";           	       
                $(".onsale").hide();
                $(".oncang").show();
				/*if (companyId==null||companyId==""){
					//alert("你不是公司会员，请先升级为公司会员再发布产品");
					//window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
					//history.back();
					let prostr="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
					if(flag=="onsale"){
						$(".onsale").append(prostr);
					}
					else{
						$(".oncang").append(prostr);
					}
				}else {*/
					loaded();
				//}
            }
             $(".top").find("li").eq(2).find("img").attr("style","height:"+$(".top").height()*0.7+"px;");
             $(".main_lis_left").css({"height":$(".main_lis_right").height()+"px"});
             $(".main_hide").css("height",$(window).height()-$(".top").height()-$(".main_nav").height()-$("footer").height()+"px");
        });
 
$(document).on("click",".xiajia",function() {
		if (window.confirm('你确定要下架吗？')) {
			flag="onsale";
			var ppId=$(this).parent().parent().parent().find("input[id='ppid']").val();
			if(ajaxUpOrdown(flag,ppId)){
				$(this).parent().parent().parent().remove();
			}
		} 
	});

       
$(document).on("click",".shangjia",function() {
		if (window.confirm('你确定要上架吗？')) {
			flag="oncang";
			var ppId=$(this).parent().parent().parent().find("input[id='ppid']").val();
			if(ajaxUpOrdown(flag,ppId)){
				$(this).parent().parent().parent().remove();
			}			
		}
	});
$(document).on("click",".edit",function(){
	var ppId=$(this).parent().parent().parent().find("input[id='ppid']").val();
	var goodsId=$(this).parent().parent().parent().find("input[id='goodsid']").val();
	var params=new Array();
	params.push("user="+user);
	params.push("&companyId="+companyId);
	params.push("&ppId="+ppId);
	params.push("&goodsId="+goodsId);
    window.location.href=basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?"+params.join("");
});


  //搜索
  $("#search").click(function(){
      $(".alert123").show();
      $(".alert_search").show();
  });
    //取消
    $("#qx").click(function(){
        $(".alert123").hide();
        $(".alert_search").hide();
    })
    $(".alert_search input:nth-child(1)").keyup(function () {
        var t = $(".alert_search input:nth-child(1)");
        if (t.val() == "") {
            $(".alert_search .top #qx").show();
            $(".alert_search .top #ss").hide();
        }
        else{
            $(".alert_search .top #ss").show();
            $(".alert_search .top #qx").hide();
        }
    });

    $("#ss").click(function(){
        pagenumber=0;
        search=$.trim($(".sousuo").val());


        if(search==''){
            alert("产品名称");
        }else{
            $(".onsale").html("");
            $(".oncang").html("");
			/*if (companyId==null||companyId==""){
				//alert("你不是公司会员，请先升级为公司会员再发布产品");
				//window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
				//history.back();
				let prostr="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
				if(flag=="onsale"){
					$(".onsale").append(prostr);
				}
				else{
					$(".oncang").append(prostr);
				}
			}else {*/
				loaded();
			//}
            $(".alert123").hide();
            $(".alert_search").hide();
        }
    });

});//加载完毕
//扫扫
function calltiaoma(tiaoma){
	var params=new Array();
	params.push("user="+user);
	params.push("&companyId="+companyId);	
	var member=eval("("+tiaoma+")");
	var result=member.result;
	var p1,p2,p3;
	var summary=result.summary;
	p1=summary.barcode;
	p2=summary.interval;
	p3=summary.name;
	params.push("&barcode="+p1);	
	params.push("&interval="+p2);
	params.push("&name="+p3);
	window.location.href=basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?"+params.join("");				
}

//产品发布跳转
function addOrEdit(){
	var params=new Array();
	params.push("user="+user);
	params.push("&companyId="+companyId);
    window.location.href=basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?"+params.join("");
}
function ajaxUpOrdown(flag,ppId){
	var f=false;
	var url=basePath+"ea/productslaunch/sajax_ea_upOrdown.jspa?flag="+flag+"&ppId="+ppId;
	$.ajax({
		url : url,
		type : "get",
		async : false,
		success : function cbf(data){
			var member=eval("("+data+")");
			var flag=member.flag;
			if(flag=="1"){
				alert("操作成功！");
				f=true;
			}
		},
		error : function (){
			alert("操作失败！");
			f=false;
		}
	});
	return f;
}
function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").offset().top+$(".last").height()-$(".top").height()*3<$(window).height()){				
		if(pagenumber<pagecount){
			/*if (companyId==null||companyId==""){
				//alert("你不是公司会员，请先升级为公司会员再发布产品");
				//window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
				//history.back();
				let prostr="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
				if(flag=="onsale"){
					$(".onsale").append(prostr);
				}
				else{
					$(".oncang").append(prostr);
				}
			}else {*/
				loaded();
			//}
		}		
	}
}

function loaded (){
	clearTimeout(t);
	pagenumber++;		
	var url=basePath+"ea/productslaunch/sajax_ea_ajaxProducts.jspa?";	
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber,		 
		  "flag":flag,
		  "user":user,
		  "companyId":companyId,
			search:search
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			if(member!=null){
				var pageForm = member.pageForm;
				var prostr=""; 
				if(pageForm!=null&&pageForm.recordCount>0){
					var productlist=pageForm.list;
					pagenumber=pageForm.pageNumber;
					pagecount=pageForm.pageCount;						             
					if(productlist!=null){
						$(".last").removeClass("last");
						for(var i=0;i<productlist.length;i++){
							var pro=productlist[i];					
			                if(i==productlist.length-1){                	
			                	prostr+="<div class='main_lis last'>";
			                }
			                else{
			                 	prostr+="<div class='main_lis'>";
			                }	
							prostr+="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
							prostr+="<input type='hidden' value='"+pro[4]+"' id='goodsid'/>";
							prostr+="<input type='hidden' value='"+pro[5]+"' id='companyid'/>";
				            prostr+="<div class='main_lis_bian'>";
				            prostr+="<div class='main_lis_left'>";
				            prostr+="<img src='"+basePath+pro[3]+"' alt='产品主图'/></div>"
				            prostr+="<div class='main_lis_right'>";
				            prostr+="<p>"+pro[0]+"</p>";
				            prostr+="<p><span>&yen;</span><span>"+pro[2]+"</span></p>";                       
				            prostr+="<p><i><span>已售</span><span>"+pro[8]+"</span></i>";
				            prostr+="<i><span>库存</span><span>"+pro[7]+"件</span></i></p></div></div>";			            		              	                	
				            if(flag=="onsale"){
				            	prostr+=" <div class='main_lis_bot'><ul id='main_lis_bot'><li class='edit'>编辑产品</li><li class='xiajia'>下架</li></ul></div></div>"
							}else{
								prostr+=" <div class='main_lis_bot'><ul id='main_lis_bot'><li class='edit'>编辑产品</li><li class='shangjia'>上架</li></ul></div></div>"
							}
						}
						if(pagenumber == pagecount){
							prostr+="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
						}				
						if(flag=="onsale"){
							$(".onsale").append(prostr);
						}									
						else{
							$(".oncang").append(prostr);
						}									
						getHeight();
					}
				}else{
					prostr+="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
					if(flag=="onsale"){
						$(".onsale").append(prostr);
					}									
					else{
						$(".oncang").append(prostr);
					}
				}
			}			
		},
		error: function cbf(data){
			alert("产品加载失败");
		}
	});
}
//安卓，苹果返回
function retAndroid(){
	try{       		
		Android.callAndroidjianli();
	}catch(err){
			$("#returnClick").removeAttr("onclick");
			if(sys=='sys'){
				$("#returnClick").attr("href",basePath+"mobile/office/mobileoffice_fastApplication.jspa?");
			}else{
				$("#returnClick").attr("href",basePath+"ea/vipcenter/ea_vipDemand.jspa");
			}
	}
}
	function retIOS(){
		if(ret=='ret'){
			var url= "func=" + "calProduct"; 
	        window.webkit.messageHandlers.Native.postMessage(url);
			//calProduct('');
		}else{
			$("#returnClick").removeAttr("onclick");
			if(sys=='sys'){
				$("#returnClick").attr("href",basePath+"mobile/office/mobileoffice_fastApplication.jspa?");
			}else{
				$("#returnClick").attr("href",basePath+"ea/vipcenter/ea_vipDemand.jspa");
			}			
		}			 		
	}