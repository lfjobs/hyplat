$(document).ready(function(){
	var arr=ajax();
	var phtl=new Array();	
	var ch=0;
	var ch1=0;	
	//省
	for(var i=0;i<arr.length;i++){
		phtl.push("<li class='"+arr[i].id+"'><span>"+arr[i].province+"</span></li>");		
	}
	$(".grade-w").append(phtl.join(""));
	//市
	$(".grade-w").find("li").live("click",function(){	
		$(this).attr("style","background:rgb(238,238,238)");
		$(this).siblings().attr("style","background:");
		$(".grade-t").css("left","33.48%");
		ch=$(this).attr("class");
		region+=$(this).find("span").text();
		$(".grade-t").empty();
		$(".grade-s").empty();
		var list=arr[ch-1].city;
		var chtl=new Array();
		for(var i=0;i<list.length;i++){			
			chtl.push("<li class='"+list[i].id+"' id='"+i+"'><span>"+list[i].city+"</span></li>");
		}
		$(".grade-t").append(chtl.join(""));
	});
	//区
	$(".grade-t").find("li").live("click",function(){
		$(this).attr("style","background:rgb(255,255,255)");
		$(this).siblings().attr("style","background:");
	    $(".grade-s").css("left","66.96%")
		$(".grade-s").empty();
		ch1=$(this).attr("id");
		region+=$(this).find("span").text();
		var list=arr[ch-1].city[ch1].district;
		var dhtl=new Array();
		for(var i=0;i<list.length;i++){
			dhtl.push("<li class='"+list[i].id+"'><span>"+list[i].district+"</span></li>");
		}		
		$(".grade-s").append(dhtl.join(""));
	});
	//选择区完后赋值并搜索
	$(".grade-s").find("li").live("click",function(){
		$(this).attr("style","border-bottom:solid 1px #ff7c08");
		$(this).siblings().attr("style","border-bottom:");
		region+=$(this).find("span").text();
		$('.grade-eject').removeClass('grade-w-roll');
		$(".grade-s").attr("style","left:");
		$(".grade-t").attr("style","left:");
		var params=new Array();
		params.push("proName="+proName);		
		params.push("&flag="+flag);
		params.push("&region="+region);
		params.push("&industry="+industry);
		params.push("&tradecode="+tradecode);
		params.push("&search="+search);
		 var url=basePath+"ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?"+params.join("");			
		 var prostr1="";
		 var prostr2="";
		 var productlist=null;
		 var pageForm=ajaxProlist(url);
		 if(pageForm!=null){
			 productlist=pageForm.list;		
			 if(productlist!=null){
			 	$(".style2_con").empty();
				$(".style1_con").empty();
					if (flag=="2") {
						for (var i = 0; i < productlist.length; i++) {
							var pro = productlist[i];
							if(i==productlist.length-1){
								prostr2 += "<li class='goodsDetail last'>";
							}else{
								prostr2 += "<li class='goodsDetail'>";
							}
							prostr2 +="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
							prostr2 +="<input type='hidden' value='"+pro[5]+"' id='goodsid'/>";
							prostr2 +="<input type='hidden' value='"+pro[6]+"' id='companyid'/>";
							prostr2 +="<input type='hidden' value='"+pro[10]+"' id='ccompanyid'/>";
							prostr2 += "<div class='img'><img src='" + basePath+pro[4] + "'/></div>";
							prostr2 += "<div class='text'>";
							prostr2 += "<h3>" + pro[0] + "</h3>";
							prostr2 += "<h4>" + "￥" + pro[2] + "<span>" + pro[8]+ "人已购买</span></h4>";
							prostr2 += "</div></li>";
						}
						$(".style2_con").append(prostr2);
					}else{
						for(var i=0;i<productlist.length;i++){
							var pro=productlist[i];
							if(i==productlist.length-1){
								prostr1 += "<li class='goodsDetail last'>";
							}else{
								prostr1 += "<li class='goodsDetail'>";
							}
							prostr1+="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
							prostr1+="<input type='hidden' value='"+pro[5]+"' id='goodsid'/>";
							prostr1+="<input type='hidden' value='"+pro[6]+"' id='companyid'/>";
							prostr1+="<input type='hidden' value='"+pro[10]+"' id='ccompanyid'/>";
			              	prostr1+="<div class='style1_img'><img src='"+basePath+pro[4]+"'/></div>";
			                prostr1+="<h3>"+pro[0]+"</h3>";
			                prostr1+="<h4>"+"￥"+pro[2]+"<span>"+pro[8]+"人已购买</span></h4>";                       
			                prostr1+="</li>";
						}
						$(".style1_con").append(prostr1);
					} 
					pagenumber=pageForm.pageNumber;
					pagecount=pageForm.pageCount;
				}else{
					$(".style1_con").empty();
					$(".style2_con").empty();
				}
		 	}
			});
});//加载完毕

//地域省市区
function ajax(){
	var arr=new Array();
	var url=basePath+"/ea/wfjshop/sajax_ea_getCitiesList.jspa?";
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			arr=member.result;		
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
	return arr;
}

function ajaxProlist(url){
	var pageForm=null;
	 $.ajax({
			url: url,
			type : "get",
			async : false,
			success : function cbf(data){
				var member = eval("(" + data + ")");
				pageForm = member.pageForm;				         			
			},
			erro: function cbf(data){
				alert("搜索失败");
			}
			});
	 return pageForm;
}
//综合排序搜索
function Sorts(id){
	$("#"+id).css("border-bottom","solid 1px #ff7c08");
	$("#"+id).siblings().css("border-bottom","");
	search=$("#"+id).attr("id");
	var params=new Array();
	params.push("proName="+proName);
	params.push("&search="+id);
	params.push("&flag="+flag);
	params.push("&industry="+industry);
	params.push("&tradecode="+tradecode);
	var url=basePath+"ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?"+params.join("");
	var productlist=null;
	var prostr1="";
	var prostr2="";
	var pageForm=ajaxProlist(url);
	if(pageForm!=null){
		productlist=pageForm.list;
		if(productlist!=null){
			$(".style2_con").empty();
			$(".style1_con").empty();
			if (flag=="2") {
				for (var i = 0; i < productlist.length; i++) {
					var pro = productlist[i];
					if(i==productlist.length-1){
						prostr2 += "<li class='goodsDetail last'>";
					}else{
						prostr2 += "<li class='goodsDetail'>";
					}					
					prostr2 +="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
					prostr2 +="<input type='hidden' value='"+pro[5]+"' id='goodsid'/>";
					prostr2 +="<input type='hidden' value='"+pro[6]+"' id='companyid'/>";
					prostr2 +="<input type='hidden' value='"+pro[9]+"' id='ccompanyid'/>";
					prostr2 += "<div class='img'><img src='" + basePath+pro[4] + "'/></div>";
					prostr2 += "<div class='text'>";
					prostr2 += "<h3>" + pro[0] + "</h3>";
					prostr2 += "<h4>" + "￥" + pro[2] + "<span>" + pro[8]+ "人已购买</span></h4>";
					prostr2 += "</div></li>";
				}
				$(".style2_con").append(prostr2);
			}else{
				for(var i=0;i<productlist.length;i++){
					var pro=productlist[i];
					if(i==productlist.length-1){
						prostr1 += "<li class='goodsDetail last'>";
					}else{
						prostr1 += "<li class='goodsDetail'>";
					}
					prostr1+="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
					prostr1+="<input type='hidden' value='"+pro[5]+"' id='goodsid'/>";
					prostr1+="<input type='hidden' value='"+pro[6]+"' id='companyid'/>";
					prostr1+="<input type='hidden' value='"+pro[9]+"' id='ccompanyid'/>";
	              	prostr1+="<div class='style1_img'><img src='"+basePath+pro[4]+"'/></div>";
	                prostr1+="<h3>"+pro[0]+"</h3>";
	                prostr1+="<h4>"+"￥"+pro[2]+"<span>"+pro[8]+"人已购买</span></h4>";                       
	                prostr1+="</li>";
				}
				$(".style1_con").append(prostr1);
			}	
			pagenumber=pageForm.pageNumber;
			pagecount=pageForm.pageCount;
		}else{
			$(".style1_con").empty();
			$(".style2_con").empty();
		}
	}
	 $('.Sort-eject').removeClass('grade-w-roll');
	 $('.Sort-eject').find("li").css("border-bottom","");
	 search="";
}

//转换风格
function changeStyle(){
	var parms=new Array();
	if(flag=='1'){
	parms.push("flag=2");}
	else{ 
	parms.push("flag=1");}
	parms.push("&proName="+proName);
	parms.push("&search="+search);
	parms.push("&region="+region);
	parms.push("&tradecode="+tradecode);
	parms.push("&industry="+industry);
	window.location.href=basePath+"ea/digitalmall/ea_DigitalMall.jspa?"+parms.join("");
}
//重新搜索
$(".sea").find("img").live("click",function(){
	proName=$(this).prev().val();
	if(proName==''){
		alert("请输入关键字");
	}else{
		window.location.href=basePath+"ea/digitalmall/ea_DigitalMall.jspa?flag="+flag+"&proName="+proName;
	}
});
//产品详情
$(".goodsDetail").live("click",function(){
	var parms=new Array();
	parms.push("ppid="+$(this).find("#ppid").val());
	parms.push("&goodsid="+$(this).find("#goodsid").val());
	parms.push("&companyId="+$(this).find("#companyid").val());
	parms.push("&ccompanyId="+$(this).find("#ccompanyid").val());
	var url=basePath+"ea/wfjshop/ea_doodsDetail.jspa?"+parms.join("");
	window.location.href=url;
});

$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')) {
            $('.grade-eject').removeClass('grade-w-roll');
        } else {
            $('.grade-eject').addClass('grade-w-roll');
            //重新点击，清空地域的值
            region="";
        }
    });
});



//Sort开始

$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')) {
            $('.Sort-eject').removeClass('grade-w-roll');
        } else {
            $('.Sort-eject').addClass('grade-w-roll');
        }
    });
});


//判断页面是否有弹出

$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };

    });
});



