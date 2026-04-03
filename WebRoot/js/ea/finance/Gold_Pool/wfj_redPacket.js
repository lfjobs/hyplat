$(function(){	
	
	loaded();
	$(window).load(function(){
		var t=document.documentElement.clientHeight-$(".hb_top").outerHeight()-$(".hb_warn").outerHeight()-$(".hb_th").outerHeight()-$(".com_head").outerHeight()-16;
		$("#detail").css({
			"overflow-y": "scroll",
			"height":''+t+'px'
		});
	});
	
});

function getHeight(){
	
	t = setTimeout("getHeight()",200);
	if($(".last").length>0){
  		 if($(".last").offset().top-$(".last").height()<$(window).height()){
  			 if(pagenumber<pagecount){
  				 loaded();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
  			 }
  		}		 
  	 }		
}

function loaded(){
	
	clearTimeout(t);
 	pagenumber++;
 	
 	var url = basePath+"ea/jinbi/sajax_redPacketAjax.jspa?";
 	$.ajax({
 		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"pageNumber":pagenumber,								
			"sccid":sccid
		},
		success : function(data){
			
			var member = eval("("+data+")");				
			var pageForm = member.pageForm;				
			var str = new Array();
			
			if(pageForm != null&&pageForm.recordCount>0){
				
				$(".last").removeClass("last");
				var lists = pageForm.list;					
				pagenumber = pageForm.pageNumber;
				pagecount = pageForm.pageCount;
				
				for(var i = 0; i<lists.length;i++){
					var list = lists[i];
					
					if(i==lists.length-1){
						str.push('<a  href="###" class="hb_tr last">');
					}else{
						str.push('<a  href="###" class="hb_tr">');
					}
					
					if(list[0] == sccid ){//如果相同，则该用户为发送人
						str.push('<span>赠给'+list[2]+'</span>');
						str.push('<span class="hb_price">-'+list[3]+'</span>');
					}else{
						str.push('<span>'+list[1]+'赠送</span>');
						str.push('<span class="hb_price">+'+list[3]+'</span>');
					}				
					str.push('<span>'+list[4]+'</span>');
					str.push('</a>');															
				}
				
				
				
			}
			
			$("#detail").append(str.join(""));
			//每加载一页就给所加载页面加样式
			$(".hb_tr:even").css("background","#f2f2f2");
			$(".hb_price").each(function(){
				var str=$(this).text();
				if(str.indexOf("-")>=0){
					$(this).css("color","#6179af");
				}else{
					$(this).css("color","#ff4810");
				}
			});
			getHeight();			
		}	
 	});	
}
