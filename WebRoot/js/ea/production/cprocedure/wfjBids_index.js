$(document).ready(function() {
	
	$(".ipt").click(function(){
		document.location.href= basePath+"page/ea/main/production/cprocedure/wfjbids_searchop.jsp";
		
	});
	
	
	//点击行业查询行业下的招标
	
	$(".indus").live("click",function(){
		var tradeID = $(this).find(".tradeID").val();
		var tradeName =  $(this).find(".tradeName").val();
	    window.open(basePath+"ea/purchasebids/ea_findGoodbidList.jspa?search=search&tradeID="+tradeID+"&tradeName="+tradeName,"_self");
		
	});
	//全部行业
	$(".ali").live("click",function(){
		
		document.location.href= basePath+"/ea/bidrecruit/ea_getIndustryList.jspa?type=zb";
	
	});
	initload();
	getHeight();
	computeDays();

    //返回 
//	$(".top-dh-img").click(function(){
//    	
//    	 document.location.href=basePath+"ea/wfjshop/ea_getWFJshops.jspa";
//    	 
//     });
     //发布需求
	$(".xuqiu").click(function(){
		alert("暂未开放，敬请期待");
		//document.location.href=basePath+"ea/purchasebids/ea_findPublishBidPage.jspa";
		
	});
	
	//定位选择
	
	$(".csb").click(function(){
		document.location.href=basePath+"page/ea/main/production/cprocedure/cityPosition.jsp";
		
	});
     
     
     
	
});
//招标商品
function viewBidsGood(){
	
	document.location.href=basePath+"ea/purchasebids/ea_findGoodbidList.jspa";
}


//招聘
function viewRecruitIndex(){
	
	document.location.href=basePath+"ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
}
//点击查看具体项目
function viewMainProduct(ppID,goodsID,cashierBillID){
	
	document.location.href=basePath+"ea/purchasebids/ea_viewMainProduct.jspa?ppid="+ppID+"&bidsinfo.goodsID="+goodsID+"&inviteBids.cashierBillsID="+cashierBillID;
}
//招商
function investService ()
{
	document.location.href = basePath + "ea/productAgent/ea_productAgentList.jspa?";
}

function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").length>0){
		if($(".last").offset().top-$(document).scrollTop()<=$(window).height()){
			
		if(pagenumber<pagecount){
			loaded();
		}	
	}
	}
}

function loaded(){
	pagenumber += 1;
	var url = basePath+"ea/purchasebids/sajax_ea_ajaxGoodbidList.jspa";
	$.ajax({
		url : url,
		type : "get",
		async : false,
		data : {
			"pageForm.pageNumber":pagenumber

		},
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var htmlstr="";
             var obj;
             $(".last").removeClass("last");
             if(pageForm!=null){
				pagecount = pageForm.pageCount;
				count = pageForm.recordCount;
				pageSize = pageForm.pageSize;
			    $(".last").removeClass("last");
			    
			    for ( var i = 0; i <  pageForm.list.length; i++) {
			    	 obj = pageForm.list[i];
			    	 if(i==pageForm.list.length-1){
			    	   htmlstr+="<li class='last' style='width:100%;' onclick=\"viewMainProduct('"+obj[1]+"','"+obj[2]+"','"+obj[8]+"')\">";
			    	 }else{
			    	   htmlstr+="<li style='width:100%;' onclick=\"viewMainProduct('"+obj[1]+"','"+obj[2]+"','"+obj[8]+"')\">";	 
			    		 
			    	 }
			    	 htmlstr+="<div class='tj_img'><img src='"+basePath+obj[3]+"' /></div>";
			         htmlstr+="<div class='tj_text'>";
			         htmlstr+="<h3>"+obj[0]+"</h3>";
			         htmlstr+="<ul>";
			         htmlstr+="<li class='price'>￥"+obj[5]+"</li>";
			         htmlstr+="<li class='biao'>"+((obj[4]==null||obj[4]==""||obj[4]=="null")?0:obj[4])+"次投标</li>";
			         htmlstr+="<li class='days'><input type='hidden' value='"+obj[6]+"'/></li>";
			         htmlstr+="</ul></div></li>";
			            
			        
			    }
			    $(".tj_con").append(htmlstr);
			    $(".tj_img").css({"height":"10rem"});
			    $(".tj_text").css({"height":$(".tj_img").height()-"30"+"px"});
			    computeDays();
             }
		   
	
		 },
	     error:function(data){
		   alert("加载下一页失败");
		   }
	   });
	
}

function computeDays(){
	$(".days").each(function(){
	var v = $(this).find("input").val();
	if(v!=""&&v!=undefined&&v!=null&&v!="null"&&v!="null"){
		

	 var str=$(this).find("input").val().split(" ");
     
     var ar_ds = str[0].split('-');
     var ar_ts = str[1].split(':');
     var ds = new Date(ar_ds[0], ar_ds[1] - 1, ar_ds[2], ar_ts[0], ar_ts[1],ar_ts[2]); 
 
     var cur = new Date();
     var result = ds.getTime()-cur.getTime();
     
     //计算出相差天数  
     var days=Math.floor(result/(24*3600*1000));
        
     //计算出小时数  
       
     var leave1=result%(24*3600*1000);    //计算天数后剩余的毫秒数  
     var hours=Math.floor(leave1/(3600*1000));  
//     //计算相差分钟数  
//     var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数  
//     var minutes=Math.floor(leave2/(60*1000));  
     //计算相差秒数  
//     var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数  
//     var seconds=Math.round(leave3/1000); 

     var day = hours<0&&days<0?"已截止":"剩";
     if(days>0){
    	 day+=days+"天";
     } 
     if(hours>0){
    	 day+=hours+"时";
     }

     $(this).text(day).attr("class","haveday");
	}else{
		 $(this).text("不限");
		
	}
	});
}

//定位城市，选择城市
function initload(){
	     if(cityselect!=""){
	          $("#city").text(cityselect);
	     }else{
	    	 
	    	 showCityInfo();
	    
	     }
	
	
}


function showCityInfo() {

	// 加载城市查询插件

	AMap.service([ "AMap.CitySearch" ], function() {

		// 实例化城市查询类

		var citysearch = new AMap.CitySearch();

		// 自动获取用户IP，返回当前城市

		citysearch.getLocalCity(function(status, result) {

			if (status === 'complete' && result.info === 'OK') {

				if (result && result.city && result.bounds) {

					var cityinfo = result.city;

					 $("#city").text(cityinfo);

				}

			} else {

				console.log("您当前所在城市：" + result.info + "");

			}

		});

	});

}

