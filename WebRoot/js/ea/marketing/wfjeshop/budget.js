/**
 * 预算
 */

var ppid_arr=new Array();
$(document).ready(function(){
	
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	$("a").css("color", "#666");
    $(".main").css({"height":$(window).height()-$("header").height()-$("footer").height()+"px","overflow":"auto"});
    $(".img_div1").css({"height":$(".big_list").height()+"px"});
    $(".img_div1 img").css({"margin-top":$(".img_div1").height()*0.1+"px"});
    //搜索产品页面
    
    ppid_arr.splice(0,ppid_arr.length);//清空
    $(".shop_img").click(function(){
        $(this).find(".shop_img1").toggleClass("shop_img2")
        $(this).find(".shop_img3").toggleClass("shop_img4")
    });
    var shop1=$("#shop_img1").length;
    $(".shop_img3").click(function(){
        shop1++;
        $("#shop_span").text(shop1);
        ppid_arr.push($(this).parent().parent().find("#ppid").val());
    });
    var shop1=$("#shop_img1").length;
    $(".shop_img1").click(function(){
        shop1--;
        $("#shop_span").text(shop1);
       var index=ppid_arr.indexOf($(this).parent().parent().find("#ppid").val());
       if(index!=-1){
    	   Array.prototype.remove(ppid_arr,index);
        }
    });
    $(".res_bot").css("height",$(".shop_lie").height()+$(".search_shop").height()+$(".res_top").height()+"px")
    
    
    //增加产品   
    $(".addmore").click(function(){
        var more="<ul class='list-group'><li class='list-group-item big_list'><div class='pull-left img_div1'><img src='"+basePath+"images/WFJClient/budget/ico_1_07.png' alt='' class='img-responsiv center-block dele'/>" +
        		"</div><ul class='list-group pull-left small_list'><li class='list-group-item'><div class='pull-left' style='width: 50%;'><input type='text' class='form-control product' placeholder='请输入产品名称'></div>" +
        		"<div class='pull-right' style='width: 50%;'><input type='text' class='form-control price' placeholder='输入价格'></div></li></ul></li></ul>";
        $(".morechan").append(more);
    });
    //总价
    var total=0;
    var content="";	
	var product="";
	var price="";
    $(document).on("keyup",".price",function(){
    		total=0;
    	  $(".price").each(function(){
    		  if(!reg.test($(this).val())){	 
    			  prompt("请输入正确价格！");
    		  }else{
    			 	total+=parseFloat($(this).val());
    	           	if(isNaN(total)){
    	           		total=0;
    	           	}    	           
    		  }    
    			$("#total").text(total.toFixed(2));
           });
    });
    //删除产品
    $(document).on("click", ".dele", function () {
        $(this).parents(".list-group").remove();  
        total=0;
        if($(".price").length>0){
        	  $(".price").each(function(){
               	total+=parseFloat($(this).val());
               	if(isNaN(total)){
               		total=0;
                 	}
               	$("#total").text(total.toFixed(2));
               });
        }else{
        	$("#total").text(0);
        }
      
    });
	//保存budget
	$("#done").click(function(){
		if($("#projectName").val()==''){
			prompt("项目名称不能为空");
		}else if(checkProAndPrice()){			
			document.budgetForm.submit.click();
		}
	});
	//管理
	$(document).on("click",".manage",function(){	
        $(".xiangq").each(function(){
        	var prostr_manage="";
        	if($(this).hasClass("last")){
        		prostr_manage+="<ul class='list-group'><li class='list-group-item big_list last'>";
        	}else{
        		prostr_manage+="<ul class='list-group'><li class='list-group-item big_list'>";
        	}
            prostr_manage+="<div class='pull-left img_div'><img src='"+basePath+"images/WFJClient/budget/yusuan_03.png' alt='' class='img-responsiv center-block'/></div>";
            prostr_manage+="<ul class='list-group pull-left small_list'>";
            prostr_manage+="<li class='list-group-item'>";
            prostr_manage+="<div class='pull-left'>"+$(this).find(".proname").text()+"</div>";
            prostr_manage+="<div class='pull-right yusuan'><span>"+$(this).find(".yusuan").text()+"</span></div></li>";
            prostr_manage+="<li class='list-group-item'>";
            prostr_manage+="<div class='pull-right'>";
            prostr_manage+="<input type='hidden' value='"+$(this).find("input").val()+"'/>"
            prostr_manage+="<div class='pull-left bbtn bianji'>";
            prostr_manage+="<img src='"+basePath+"images/WFJClient/budget/yusuan_07.png' alt=''/>编辑</div>";
            prostr_manage+="<div class='pull-right bbtn delebudget'>";
            prostr_manage+="<img src='"+basePath+"images/WFJClient/budget/yusuan_09.png' alt=''/>删除</div>";
            prostr_manage+="</div></li></ul></li></ul>"; 
        	$(this).remove();
        	$(".buttom").before(prostr_manage);       	
        });
        $(".manage").text("完成");
        $(".manage").attr("class","wancheng");
	});
	//完成
	$(document).on("click",".wancheng",function(){
		$("body").html("");
		pagenumber=0;
		pagecount=0;
		loaded();	
	});
	// 管理编辑
	$(document).on("click",".bianji",function(){
		cashierBillsID=$(this).parent().find("input").val();
		var url=basePath+"ea/wfjbudget/ea_budgetDetail.jspa?cashierBillsID="+cashierBillsID+"&user="+user+"&companyId="+companyId;
		window.location.href=url;
	});
	//单击详情
	$(document).on("click",".xiangq",function(){
		cashierBillsID=$(this).find("input").val();
		var url=basePath+"ea/wfjbudget/ea_budgetDetail.jspa?cashierBillsID="+cashierBillsID+"&user="+user+"&companyId="+companyId;
		window.location.href=url;
	});
	//删除项目预算
	$(document).on("click",".delebudget",function(){
		if(window.confirm("确定删除此项目预算？")){
			cashierBillsID=$(this).parent().find("input").val();
			var url=basePath+"ea/wfjbudget/sajax_ea_delBudget.jspa?"
			$.ajax({
				url: url,
				type:"get",
				async : false,
				dataType:"json",
				data:{"cashierBillsID":cashierBillsID},
				success: function (data){
					var member=eval("("+data+")");
					var flag=member.flag;
					if(flag!=null&&flag=='1'){
						window.location.href=basePath+"ea/wfjbudget/ea_toBudget.jspa?user="+user+"&companyId="+companyId;
					}
				},
				error:function (){
					alert("删除失败！");
				}
			});
		}
	});
	//单击预算内产品搜索产品
	$(document).on("click",".ppname",function(){
		var ppname=$(this).text();
		var goodsBillsID=$(this).parent().find("input").val();
		var params=new Array();
		params.push("ppname="+ppname);
		params.push("&goodsBillsID="+goodsBillsID);
		params.push("&cashierBillsID="+cashierBillsID);
		params.push("&user="+user);
		params.push("&companyId="+companyId);
		window.location.href=basePath+"ea/wfjbudget/ea_searchPro.jspa?"+params.join("");
	});
	//点击产品详情
	$(document).on("click",".shop_txt",function(){
		var parms=new Array();
		parms.push("ppid="+$(this).parent().find("#ppid").val());
		parms.push("&goodsid="+$(this).parent().find("#goodsid").val());
		parms.push("&companyId="+$(this).parent().find("#companyid").val());
		parms.push("&ccompanyId="+$(this).parent().find("#ccompanyid").val());
		parms.push("&user="+user);
		parms.push("&goodsBillsID="+goodsBillsID);
		parms.push("&cashierBillsID="+cashierBillsID);
		parms.push("&budget=budget");
		var url=basePath+"ea/wfjshop/ea_doodsDetail.jspa?"+parms.join("");
		window.location.href=url;
	});
	
	$(document).on("click",".delcp",function(){
		var ppId=$(this).parent().find("input").val();
		var goodsBillsID=$(this).parent().parent().parent().attr("id");
		var parms=new Array();
		parms.push("ppId="+ppId);	
		parms.push("&companyId="+companyId);
		parms.push("&user="+user);
		parms.push("&goodsBillsID="+goodsBillsID);
		parms.push("&cashierBillsID="+cashierBillsID);
		if(window.confirm("确定删除！")){
			window.location.href=basePath+"ea/wfjbudget/ea_delChancePro.jspa?"+parms.join("");
		}
	});
	
	
});//加载完毕
function checkProAndPrice(){
	var flag=true;
	content="";	
	product="";
	price="";
	$(".product").each(function(){
		if($(this).val()==''){
			prompt("产品名称不能为空");
			flag=false;
		}else{
			product+=$(this).val()+"#";
		}
	});
	content+=product+",";
	$(".price").each(function(){
		if($(this).val()==''){
			prompt("价格不能为空");
			flag=false;
		}else if(!reg.test($(this).val())){
			prompt("价格是非负小数");
			flag=false;
		}else{
			price+=$(this).val()+"*"
		}			
	});
	content+=price;
	if(content==','){
		prompt("添加新产品");
		flag=false;
	}
	$("#content").val(content);	
	return flag;
}

function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").offset().top+$(".last").height()-$("header").height()*3<$(window).height()){				
		if(pagenumber<pagecount){
			loaded();
		}		
	}
}

function loaded(){
	if(state==null||state=='1'){
		alert("您还没有此项功能的权限！");
		window.history.go(-2);
	}else{
	clearTimeout(t);
	pagenumber++;		
	var url=basePath+"ea/wfjbudget/sajax_ea_ajaxBudget.jspa?";	
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber,		 
		  "user":user,
		  "companyId":companyId
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var bodystr="";
			if(pageForm==null||pageForm.list.length==0){
				bodystr+="<header><p><img src='"+basePath+"images/WFJClient/budget/back_03.png' alt=''/></p>";
				bodystr+="<span>日用办公消耗品</span><p><a href='"+basePath+"ea/wfjbudget/ea_toAddBudget.jspa?user="+user+"&companyId="+companyId+"'>添加</a></p></header>";
				bodystr+="<div class='main'><div class='main_hide'><div class='main_nthing col-xs-12'>";
				bodystr+="<img src='"+basePath+"images/WFJClient/budget/loin_03.png' alt='' class='pull-left' /><p class='pull-left col-lg-12'>咦！什么都没有</p></div>";
				bodystr+="<a href='"+basePath+"ea/wfjbudget/ea_toAddBudget.jspa?user="+user+"&companyId="+companyId+"'><div class='more center'>现在添加</div></a></div></div>";
				$("body").append(bodystr);
			}else{
				var productlist=pageForm.list;
				pagenumber=pageForm.pageNumber;
				pagecount=pageForm.pageCount;			
				var prostr="";
				$(".last").removeClass("last");
				bodystr+="<header><p><a href='"+basePath+"ea/digitalmall/ea_DigitalMall.jspa?'>"; 
				bodystr+="<img src='"+basePath+"images/WFJClient/budget/back_03.png' alt='' /></a></p>";
				bodystr+="<span>项目预算</span><p class='manage'>管理</p></header>";
				bodystr+="<div class='main'><div class='main_hide'></div></div>";
				for(var i=0;i<productlist.length;i++){
					var pro=productlist[i];					
	                if(i==productlist.length-1){                	
	                	prostr+="<ul class='list-group xiangq last'>";
	                }
	                else{
	                 	prostr+="<ul class='list-group xiangq'>";
	                }
	                prostr+="<input type='hidden' value='"+pro[0]+"'/>";						
		            prostr+="<li class='list-group-item big_list'>";
		            prostr+="<div class='pull-left img_div1'>";
		            prostr+="<img src='"+basePath+"images/WFJClient/budget/yusuan_03.png' alt='' class='img-responsiv center-block' /></div>";
		            prostr+="<ul class='list-group pull-left small_list'>";
		            prostr+="<li class='list-group-item'>";		
		            prostr+="<div class='pull-left proname'>"+(pro[1]==null? "项目名称": pro[1])+"</div><div class='pull-right yusuan'>项目预算:<span>"+pro[2]+"元</span></div></li>";
					prostr+="</ul></li></ul>";
				}
				if(pagenumber == pagecount){
					prostr+="<li class='buttom' style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多项目预算</div></li>";
				}
				$("body").append(bodystr);
			    $(".main").css({"height":$(window).height()-$("header").height()-$("footer").height()+"px","overflow":"auto"});

				$(".main_hide").append(prostr);
				var footstr="<footer onclick='toadd()'><p>添加项目预算</p></footer>";
				$(".main_hide").after(footstr);
				$("footer").css({"background":"#F74C31","cursor":"pointer"});
				$("footer").find("p").css({"width":"100%","color":"#FFF","text-align":"center","font-size":"16px","padding-top":"12.5px"});
				getHeight();
			}
		},
		error: function cbf(data){
			alert("产品加载失败");
		}
	});
	}
}
function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}
function detailLoaded(){
	$.ajax({
	url :basePath+"ea/wfjbudget/sajax_ea_ajaxbudgetdetail.jspa?",
	type : "get",
	async : false,
	dataType : "json",
	data :{"cashierBillsID":cashierBillsID,"user":user,"companyId":companyId},
	success : function cbf(data){
		var member=eval("("+data+")");
		var list=member.list;
		var newlist=new Array();
		var prostr="";
		for(var x=0;x<list.length;x++){
			var pro=list[x];
			if(!contains(newlist,pro[2])){
				newlist.push(pro);
			}
		}
		for(var y=0;y<newlist.length;y++){
			var p=newlist[y];
			prostr="";
			prostr+="<ul class='list-group' id='"+p[2]+"'><li class='list-group-item big_list'>";
		    prostr+="<div class='pull-left img_div1'>";
		    if(p[9]!=null&&p[9]!=''){
			    prostr+="<img src='"+basePath+"images/WFJClient/budget/ico_03.png' alt='' class='img-responsiv center-block'/>";	
		    }else{
			    prostr+="<img src='"+basePath+"images/WFJClient/budget/ico_06.png' alt='' class='img-responsiv center-block'/>";	
		    }
		    prostr+="</div><ul class='list-group pull-left small_list'>";		    
		    prostr+="<li class='list-group-item'>";
		    prostr+="<input type='hidden' value='"+p[2]+"'/>"
		    prostr+="<div class='pull-left ppname'>"+p[3]+"</div>";		    
		    prostr+="<div class='pull-right yusuan'>项目预算:<span>"+p[4]+"</span></div></li></ul></li></ul>";
		    $(".main_hide").append(prostr);		   
		}
		total=0;
		for(var i=0;i<list.length;i++){
			var pp=list[i];
			var pstr="";		
			if(pp[6]!=null&&pp[6].length>0){
				pstr+="<ul class='list-group'><li class='list-group-item big_list'>";
				pstr+="<div class='pull-left img_div1 delcp'>";
				pstr+="<img src='"+basePath+"images/WFJClient/budget/ico_1_07.png' alt='' class='img-responsiv center-block'/>";
			    pstr+="</div><ul class='list-group pull-left small_list'>";		    
			    pstr+="<li class='list-group-item realpro'>";
			    pstr+="<input type='hidden' value='"+pp[9]+"'/>"
			    pstr+="<div class='pull-left ppname'>"+pp[8]+"</div>";		    
			    pstr+="<div class='pull-right yusuan' style='color:#4EB35A;'>需支付:<span>"+pp[7]+"</span></div></li></ul></li></ul>";
			    $("#"+pp[6]).append(pstr);
			    total+=parseFloat(pp[7]);
			}
		}
		$(".total").text(list[0][5]);
		$("header").find("span").text(list[0][1]);
		$("title").text(list[0][1]);
		$(".real").text(total.toFixed(2));
	}
});
}
function contains(arr,obj){
	var i=arr.length;
	while(i--){
		if(arr[i][2]===obj){
			return true;
		}
	}
	return false;
}
/* 
*  方法:Array.remove(dx) 通过遍历,重构数组 
*  功能:删除数组元素. 
*  参数:dx删除元素的下标. 
*/ 
Array.prototype.remove=function(arr,dx) 
{ 
    if(isNaN(dx)||dx>arr.length){return false;} 
    for(var i=0,n=0;i<arr.length;i++) 
    { 
        if(arr[i]!=arr[dx]) 
        { 
            arr[n++]=arr[i] 
        } 
    } 
    arr.length-=1 
} 
function searchPro(){
	var ppname=$("#sea_shop").find("input").val();
	if($.trim(ppname)==''){
		alert("请输入产品名称！");
	}else{		
		var params=new Array();
		params.push("ppname="+ppname);
		params.push("&goodsBillsID="+goodsBillsID);
		params.push("&cashierBillsID="+cashierBillsID);
		params.push("&user="+user);
		params.push("&companyId="+companyId);
		window.location.href=basePath+"ea/wfjbudget/ea_searchPro.jspa?"+params.join("");
	}
}
//多选产品
function chanceMorePro(){
	if(ppid_arr.length==0){
		alert("请选择产品");
	}else{
		$("#ppids").val(ppid_arr.join(","));
		document.productForm.submit.click();
	}
}
//加入购物车
function joinCart(){
	ppid_arr.splice(0,ppid_arr.length);
	$(".realpro").each(function(){
		ppid_arr.push($(this).find("input").val());
		
	});
	if(ppid_arr.length==0){
		alert("请搜索产品后选择产品");
	}else{
		$("#ppids").val(ppid_arr.join(","));
		$.ajax({
			cache:true,
			url :basePath+"ea/wfjbudget/sajax_ea_oneKeyJoinCart.jspa?",
			type : "post",
			async :false,
			data:$("#cartForm").serialize(),
			success:function cbf(data){
				var member=eval("("+data+")");
				var flag=member.flag;
				if(flag=="1"){
					alert("已成功加入购物车！");
					window.location.href=basePath+"/ea/wfjshop/ea_getcity.jspa?";
				}
			},
			error: function (){
				alert("加入购物车失败！");
			}
		})
	}
}
function toadd(){
window.location.href=basePath+"ea/wfjbudget/ea_toAddBudget.jspa?user="+user+"&companyId="+companyId;
}