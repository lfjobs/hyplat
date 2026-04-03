$(document).ready(function(){
	loaded();
    $("header").css("height",$(window).height()*0.08-1+"px");
    $("header").css("line-height",$(window).height()*0.08-1+"px");
    $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
    $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
    $(".head_top").css("height",$(window).height()*0.08-1+"px");
    $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
    $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
    $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
    /*$(".con").css("height",$(window).height()*0.828+"px");*/
    $(".con").css("height",$(window).height()*0.92-$(".search_frd_").height()-5+"px");


    /*搜索*/
    $(".search_frd input").focus(function(){
        $(".search_frd .search_").hide();
    });
    $(".search_frd input").blur(function(){
        if( $(".search_frd input").val()==""){
            $(".search_frd .search_").show();
        }else{
            $(".search_frd .search_").hide();
        }
    });
    $(".search_frd .search_").click(function(){
        $(".search_frd .search_").hide();
        $(".search_frd input").focus();
    });
   
    $("#cusName").bind("propertychange input", function() {
		$("#cus").empty();		
		loaded();
	})
          
});
    



    function getHeight(){
   	 t=setTimeout("getHeight()",200);
   	 if($(".last").length>0){
   		 if($(".last").offset().top+$(".last").height()*5.5<$(window).height()){
   			 if(pagenumber<pagecount){
   				 loaded();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
   			 }
   		}		 
   	 }
    }
    
	function loaded(){
		
		clearTimeout(t);
	 	pagenumber++;
	 	var cusName = $("#cusName").val();
	 	var url = basePath+"ea/seller/sajax_ea_getListAjax.jspa?";
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data : {
				"pageNumber":pagenumber,				
				"companyid": companyid,
				"cusName":cusName
			},			
			success : function (data){
				var member = eval("("+data+")");				
				var pageForm = member.pageForm;				
				var str = new Array();
				
				if(pageForm != null&&pageForm.recordCount>0){
					
					$(".last").removeClass("last");
					var customer = pageForm.list;					
					pagenumber = pageForm.pageNumber;
					pagecount = pageForm.pageCount;
					
					for(var i = 0;i<customer.length;i++){
						var pp = customer[i];
						
						if(i==customer.length-1){
							str.push('<ul class="mil_frd last">');
						}else{
							str.push(' <ul class="mil_frd">');
						}	
						
						if(pp[0]==null){
							str.push('<li><img src="'+basePath+'images/WFJClient/VipCenter/headimage.png" alt="">');
						}else{
							str.push('<li><img src="'+basePath+pp[0]+'" alt="">');
						}								             
		                str.push('<h4>'+pp[1]+'<span>');
		                str.push(pp[2]+'</span></h4></li>');
		                str.push('</ul>');
					}					
				}				
				$("#cus").append(str.join(""));					
				getHeight();
			}		
		});		 			
	}	
