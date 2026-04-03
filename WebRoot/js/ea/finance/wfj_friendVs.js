$(document).ready(function(){
	loaded();
	 $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position:fixed;");
     $(".content_hidden").attr("style",";overflow: hidden;position: relative;"+"top:"+$(window).height()*0.08+"px");

     $(".content").attr("style",";overflow: hidden;width:100%;margin:0 auto;"+"height:"+$(window).height()*0.92+"px;");
    if(khd == "1"){
        $(".content_hidden").css("top",0);
        $(".content").css("height","100%");
        $(".content").css("background","#FFCC0C");
    }
     $(".frid_h3 a").attr("style","font-size:"+$(window).width()*0.07+"px");
     $(".frid_text table td").attr("style","height:"+$(window).width()*0.14+"px;"+"font-size:"+$(window).width()*0.035+"px;"+"line-height:"+$(window).width()*0.12+"px;");
     $(".frid_text table tr td:nth-child(1)").attr("style","font-size:"+$(window).width()*0.04+"px;")
     $(".frid_text table thead tr:nth-child(1) td").attr("style","height:"+$(window).width()*0.12+"px;"+"font-size:"+$(window).width()*0.04+"px;"+"color: #000;");
     $(".frid_text table tr td:nth-child(1) img").attr("style","width:"+$(window).width()*0.08+"px;"+"position: absolute;top: 15%;left: 50%;margin-left:"+"-"+$(window).width()*0.04+"px;");
     $(".frid_head").attr("style","width:"+$(window).width()*0.08+"px;"+"height:"+$(window).width()*0.08+"px;");
     $(".frid_text_").css("height",$(window).height()*0.92+"px");
             
     $(".frid_text table tr td:nth-child(2) p").css("max-width",$(window).width()*0.28+"px");       
     $(".frid_text_ #head").attr("style","line-height:"+$(window).width()*0.12+"px;"+"height:"+$(window).width()*0.12+"px;"+"font-size:"+$(window).width()*0.04+"px;"+"color: #000;");
     $(".frid_text_ #head li").attr("style","line-height:"+$(window).width()*0.12+"px;"+"height:"+$(window).width()*0.12+"px;"+"font-size:"+$(window).width()*0.04+"px;"+"color: #000;");
     $(".frid_text").css("top",$(window).height()*0.32+$("#head").height()+"px");
     $("header ul li:nth-child(1)").attr("style","width:15%;");
     $("header ul li:nth-child(1) img").attr("style","width: 0.6rem;margin-top: 0;position: absolute;top: 50%;transform: translateY(-50%);-webkit-transform: translateY(-50%);");
     $("header ul li:nth-child(2)").attr("style","width:70%;margin-left: 15%;");
     
});
    
    function getHeight(){
   	 t=setTimeout("getHeight()",200);
   	 if($(".last").length>0){
   		 if($(".last").offset().top+$(".last").height()-$(".header").height()*3<$(window).height()){
   			 if(pagenumber<pagecount){
   				 loaded();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
   			 }
   		}		 
   	 }
    }
    
	function loaded(){
		
		clearTimeout(t);
	 	pagenumber++;
	 	
	 	var url = basePath+"ea/jinbi/sajax_getAjax.jspa?";
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data : {
				"pageNumber":pagenumber,								
				"user":user
			},			
			success : function (data){
				var member = eval("("+data+")");				
				var pageForm = member.pageForm;				
				var str = new Array();
				
				if(pageForm != null&&pageForm.recordCount>0){
					
					$(".last").removeClass("last");
					var friends = pageForm.list;					
					pagenumber = pageForm.pageNumber;
					pagecount = pageForm.pageCount;
					var rank = member.rank;
					
					if(rank == null){
						$(".frid_h3 a").text("0");
					}else{
						$(".frid_h3 a").text(rank);
					}									
					
					for(var i = 0;i<friends.length;i++){
						var ff = friends[i];
						
						if(i==friends.length-1){
							str.push('<tr class = "grd last">');
						}else{
							str.push('<tr class = "grd">');
						}
						
						str.push('<td >');
						if(ff[4]==1){
			                str.push('<img src="'+basePath +'images/ea/finance/BenDis/jin.png" alt="">');		                	        
						}else if(ff[4]==2){
			                str.push('<img src="'+basePath +'images/ea/finance/BenDis/yin.png" alt="">');		                	        
						}else if(ff[4]==3){
			                str.push('<img src="'+basePath +'images/ea/finance/BenDis/tong.png" alt="">');		                	        
						}else{
							str.push(ff[4]);
						}
						str.push('</td >');
						
						str.push('<td >');
						if(ff[6]==null){
							str.push('<div class="frid_head"><img src="'+basePath +'images/ea/finance/BenDis/头像@2x.png" alt=""></div>');
						}else{
							str.push('<div class="frid_head"><img src="'+basePath+ff[3]+'" alt=""></div>');
						}
						str.push('<p>'+ff[1]+'</p>');		
						str.push('</td >');
						
						str.push('<td >'+ff[0]+'</td >');						
						str.push('</tr >');													                
				
					}
				}
				$("#frinds").append(str.join(""));//退货成功和拒绝退货
			    $(".frid_text table td").attr("style","height:"+$(window).width()*0.14+"px;"+"font-size:"+$(window).width()*0.035+"px;"+"line-height:"+$(window).width()*0.12+"px;");
			    $(".frid_head").attr("style","width:"+$(window).width()*0.08+"px;"+"height:"+$(window).width()*0.08+"px;");		
				getHeight();
			}		
		});		 			
	}
	
//退货(退款)详情
function refund(cashid){
	
	document.location.href=basePath+"/ea/pobuy/ea_getCashBill.jspa?cbid="+cashid+"&refund=1";	
	
	}			

//退货进度
function ret(cashid){
	window.location.href=basePath+"ea/refundMoney/ea_details.jspa?cashId="+cashid+"&tp=01";	

}
//退款进度
function ref(cashid){
	window.location.href=basePath+"ea/refundMoney/ea_details.jspa?cashId="+cashid+"&tp=00";	

}









