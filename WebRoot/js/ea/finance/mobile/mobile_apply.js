$(document).ready(function() {
                $("body").css("width",$(window).width());
                $("body").css("height",$(window).height());
                
				//中联园区头部
				$(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
				$(".wfj_top").find("li").attr("style","width:15%;");
				$(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
				$(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
				
				
				$(".wfj12_006_con").find("li").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
				$(".wfj12_006_paytype").css("font-size",$(window).height()*0.025+"px");
				
				$(".wfj12_006_choice").find("select").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
				$(".wfj12_006_choice").find("input").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
				$(".wfj12_006_choice").find("div").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
				
				$(".wfj12_006_paytype").find("span").attr("style","font-size:"+$(window).height()*0.02+"px;");
				
				
				$(".wfj12_006_bottom").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
				$(".wfj12_006_float").attr("style","max-height:"+$(window).height()*0.8+"px;margin-bottom:"+$(window).height()*0.1+"px;");
				$(".wfj12_006_float").find("ul").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px; border-bottom:"+$(window).height()*0.005+"px solid #F0F0F0;");
				$(".wfj12_006_float").find("ul").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
				$(".wfj12_006_float").find("ul").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
				
				
				$(".wfj12_006_content").attr("style","width:"+$(".wfj12_006_float").width()+"px;height:"+$(".wfj12_006_float").height()+"px;");
				$(".wfj12_006_hidden").attr("style","width:"+$(".wfj12_006_content").width()+"px;height:"+$(".wfj12_006_content").height()+"px;");
				//alert($(".wfj12_006_hidden").height() < $(window).height()*0.8)
				if($(".wfj12_006_hidden").height() < $(window).height()*0.8){
					$(".wfj12_006_hidden").attr("style","width:"+$(".wfj12_006_content").width()+"px;height:"+$(".wfj12_006_content").height()+"px;");
				}else{
					$(".wfj12_006_hidden").attr("style","width:"+parseInt($(".wfj12_006_content").width()+17)+"px;height:"+$(".wfj12_006_content").height()+"px;overflow:auto;");
				}
				
				$(".wfj12_006_addp").attr("style"," border:"+$(window).height()*0.002+"px dashed #9A9A9A;height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
				$(".wfj12_006_addp").find("img").attr("style"," height:"+$(window).height()*0.04+"px;width:auto;");
				$(".wfj12_006_addp").find("ul").eq(0).find("img").css("paddingLeft",$(window).height()*0.01+"px");
				$(".change_width").attr("style"," height:"+$(window).height()*0.06+"px; top:-"+$(window).height()*0.062+"px;left:"+$(window).height()*0.002+"px;");
				$(".wfj12_006_hiddenimg").find("img").attr("style"," height:"+$(window).height()*0.04+"px;width:auto;");
				$(".img_delete").attr("style"," top:-"+$(window).height()*0.015+"px; right:"+$(window).height()*0.01+"px; width:"+$(window).height()*0.02+"px; height:auto;");
				
				$(".hiddenfont").attr("style","font-size:"+$(window).height()*0.02+"px;padding-right:"+$(window).height()*0.01+"px;");
				$(".wfj12_006_addimg").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom:"+$(window).height()*0.005+"px solid #F0F0F0;");
				$(".wfj12_006_addimg").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");
				
				
				
				$(".changeimg").click(function(){
					$(".change_img").attr("src",basePath+"js/jqModal/css/images_blue/choice_02.png");
					$(this).find(".change_img").attr("src",basePath+"js/jqModal/css/images_blue/choice_01.png");
					
					$(".paytype").val($(this).find("li:first-child").text());
				});
				$(".changeimg li").click(function(){
					$(".paytype").val($(this).text());
				});
				
			
				$(".paytype").click(function(){
					
					$("#occlusion2").css("z-index",$(".wfj12_006").css("z-index")+1);
					$("#occlusion2").jqmShow();
					$(".wfj12_006_float").css("z-index",$("#occlusion2").css("z-index")+1);
					$(".wfj12_006_float").fadeIn(1000);
				});
				$(".jqmOverlay").live("click",function(){
					$(".wfj12_006_float").fadeOut();
					$("#occlusion2").jqmHide();
				});
				
				$(".changeimg").live("click",function(){
					
					$(".wfj12_006_float").fadeOut();
					$("#occlusion2").jqmHide();
				});
				
				if($(".wfj12_006_hiddenimg").length!=0){
					$(".hiddenfont").css("display","none");
					$(".wfj12_006_hiddenimg").show();
				}else{
					$(".hiddenfont").css("display","block");
					$(".wfj12_006_hiddenimg").hide();
				}
				
				if($(".wfj12_006_hiddenimg").length==1){
					$(".change_width").css("width","85%");
				}else if($(".wfj12_006_hiddenimg").length==2){
					$(".change_width").css("width","70%");
				}else if($(".wfj12_006_hiddenimg").length==3){
					$(".change_width").css("width","55%");
				}
				$(".wfj12_006_addp").click(function(){
					$(".wfj12_006_hiddenimg").show();
					
					if($(".wfj12_006_hiddenimg").length==3){
						return;
					}
					$("#occlusion3").css("z-index",$(".wfj12_006").css("z-index")+1);
					$("#occlusion3").jqmShow();
					$(".wfj12_006_addimg").css("z-index",$("#occlusion3").css("z-index")+1);
					$(".wfj12_006_addimg").fadeIn(1000);
				});
				
				$(".jqmOverlay").live("click",function(){
					$(".wfj12_006_addimg").fadeOut();
					$("#occlusion3").jqmHide();
				});
				
	    		
                $("#countSub").blur(function(){
                	var value=$(this).val();
                	//判断是否为数字
                   if(isNaN(value)) {
                	   alert("输入错误");
                		}
                   if(value=="请输入退款数量"){
                	   
						alert("请重新填写");
						$("#countSub").val("");
					}
					if(eval($(this).val())<=0||eval($(this).val())>count){
						alert("数量为正数且不能超过"+count);
						$("#countSub").val("");
					}
				});
				$("#priceSub").blur(function(){
					var value=$(this).val();
					var str="";
					var length="";
					if(value.indexOf(".")!=-1){
						str = value.substring(value.indexOf(".")+1,value.length);
						length=str.length;
					}
					//判断是否为数字
				    if(isNaN(value)||value=="") {
						alert("输入错误");
						}else{
							if(value=="请输入退款金额"){
								alert("请填写");
								$("#countSub").val("");
								
							}
							  if(length==1||length==2){
							    	 if(eval($(this).val())<=0||eval($(this).val())>priceSub){
								    	  alert("数量不能超过"+priceSub);
								    	  $("#priceSub").val("");
								         }
							    }else{
							    	alert("不合法数字");
							    	return;
							      
							      }
						}
				  
				    
				  
				});
				
				//提交保存
				$(".wfj12_006_bottom").click(function(){
					var value=$(".paytype").val();
					if(value=="请先选择退款类型"){
						alert("请选择");
						return;
					}
					var value1=$("#countSub").val();
					if(value1=="请输入退款数量"){
						$("#countSub").val("");
						alert("请填写");
						return;
					}
					var value2=$("#priceSub").val();
					if(value2=="请输入退款金额"){
						$("#countSub").val("");
						alert("请填写");
						return;
					}
					if(confirm("确定提交吗？")){
						var url=basePath+"/ea/bdbill/ea_refundApply.jspa?id="+id+"&ppid="+ppid+"&staid="+staid;
						$("form#applyRefundForm")
						     .attr("target","hidden")
						     .attr("action",url);
						document.applyRefundForm.submit.click();
						token=2;
						
						
						}
					
					
				});
						
					
				//弹出层初始化
				$(".jqmWindow").jqm({
					modal : true, 
					overlay : 20
				}).jqmAddClose('.close');

				
            });
function re_load(){
	window.location.href=basePath+"/ea/hypb/ea_getCashBill.jspa?staid="+id+"&id="+id;
}