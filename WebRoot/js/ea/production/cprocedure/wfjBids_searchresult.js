$(document).ready(function() {
	
	$("#ipt").click(function(){
		document.location.href= basePath+"page/ea/main/production/cprocedure/wfjbids_searchop.jsp";
		
	});
	
	if(tradeName!=""){
		$(".shangpin").text(tradeName);
	}
	
	computeDays();
	getHeight();

	
});

function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").length>0){
		if($(".last").offset().top-$(".last").height()*3<=$(window).height()){
			
		if(pagenumber<pagecount){
			loaded();
		}	
	}
	}
}

//点击查看具体项目
function viewMainProduct(ppID,goodsID,cashierBillID){
	
	document.location.href=basePath+"ea/purchasebids/ea_viewMainProduct.jspa?ppid="+ppID+"&bidsinfo.goodsID="+goodsID+"&inviteBids.cashierBillsID="+cashierBillID;
	
	
}


function loaded(){
	pagenumber += 1;
	var url = basePath+"ea/purchasebids/sajax_ea_ajaxGoodbidList.jspa";
	$.ajax({
		url : url,
		type : "get",
		async : false,
		data : {
			search : "search",
			parameter:parameter,
			tradeID:tradeID,
			tradeName:tradeName,
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
			    	   htmlstr+="<li class='last' onclick=\"viewMainProduct('"+obj[1]+"','"+obj[2]+"','"+obj[8]+"')\">";
			    	 }else{
			    	   htmlstr+="<li onclick=\"viewMainProduct('"+obj[1]+"','"+obj[2]+"','"+obj[8]+"')\">";	 
			    		 
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
  if($(this).find("input").val()!=""&&$(this).find("input").val()!=undefined&&$(this).find("input").val()!="null"){
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

