
function fanhui(){
    	var url = basePath+"ea/pobuy/ea_getReceiptList.jspa?user="+user+"&state=00";
    	document.location.href=url;
    	}
    
    	$(document).ready(function(e) {
    	
		$("#commit").click(function() {
				$("#evaluation").val( $(".wfj12_018_width").find(".selected").text());
				$("#hide").val($(".wfj12_018_width").find("#sp").text());
				$("#image").val($("#table").find("#td1").find("img").attr("src"));
				var key = $("#key").val();
				//$("#from1").attr("action",basePath+ "/ea/consignee/ea_saveComments.jspa?tupn=goods&cskey="+key+"&companyID="+companyID); 
				if($("textarea").val()==""){
					$("textarea").val("此用户暂无评论....");
				}
				 var url=basePath+"/ea/consignee/sajax_ea_saveComments.jspa?tupn=goods&cskey="+key+"&companyID="+companyID;
				 var formData=new FormData($("#from1")[0]);
				 $.ajax({
				     url: encodeURI(url) ,  
				        type: 'POST',  
				        data: formData,  
				        async: false,  
				        cache: false,  
				        contentType: false,  
				        processData: false,  
				        success: function (data) {  
							var member = eval("(" + data + ")");
							var boo=member.s;
							if(boo=="1"){
								alert("保存成功");
								window.location.href=basePath+"/ea/pobuy/ea_getReceiptList.jspa?user="+user+"&state=11";
							}else{
								alert("保存失败");
							}
				        },  
				        error: function (data) {  
				            alert("保存失败！");  
				        } 
				 });
		});
											
									
    	
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj12_018_top_assessment").find("textarea").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.1+"px;");
            $(".wfj12_018_addimg").attr("style","position:relative;margin-right:0.8rem;margin-top:"+$(window).height()*0.015+"px;padding-bottom:"+$(window).height()*0.015+"px;");
            $(".wfj12_018_delimg").attr("style","top:-"+$(window).height()*0.07+"px;right:"+$(window).height()*0.01+"px;");
            $(".wfj12_018_add_imgs").attr("style","width:100%;height:"+parseInt($(".wfj12_018_addimg").find("div").eq(0).height())+"px;border:"+$(window).height()*0.002+"px solid #CCC;cursor:pointer;");
            $(".wfj12_018_add_imgs").find("img").attr("style","height:100%;display:block;width:100%;");
			$(".wfj12_018_add_imgs_dele").attr("style","height:2rem;position:absolute;top:-0.8rem;right:-0.8rem;");
			
			
            $(".wfj12_018_assessimg").attr("style","margin-top:"+$(window).height()*0.005+"px;");
            $(".wfj12_018_assessimg").find("td").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
            $(".wfj12_018_assessimg").find("td").eq(0).attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;color:#F74C31;font-size:"+$(window).height()*0.025+"px;");
            $(".wfj12_018_assessimg").find("td").find("img").attr("style","height:"+$(window).height()*0.03+"px; margin-right:"+$(window).height()*0.01+"px;");
			
            $(".wfj12_018_assess_star").attr("style"," margin-top:"+$(window).height()*0.015+"px;");
            $(".wfj12_018_assess_star").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;border-bottom:"+$(window).height()*0.002+"px solid #CCC;");
            $(".wfj12_018_assess_star").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
            $(".wfj12_018_assess_star").find("div").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
			
			
            $(".wfj12_018_bottom").find("td").attr("style","font-size:"+$(window).height()*0.03+"px;height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj12_018_bottom").find("td").find("div").attr("style","font-size:"+$(window).height()*0.03+"px;");
            $(".wfj12_018_bottom").find("td").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
           
		    $(".wfj12_018_addph").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom:"+$(window).height()*0.005+"px solid #F0F0F0;");
			$(".wfj12_018_addph").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");
			
		  
		   /*隐藏添加照片按钮*/
		   if($(".wfj12_018_proimg").length==3){
			   $(".wfj12_018_add_imgs").css("display","none");
		   }else{
			   $(".wfj12_018_add_imgs").css("display","block");
		   }
		   /*好评、中评、差评之间的转换*/
		   $(".wfj12_018_assessimg").find("td").click(
		   function(){
		   $("#divs").find(".selected").removeClass("selected");
			   if($(this).text()=="好评"){
				   $(this).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_01_01.png");
				   $(".wfj12_018_assessimg").find("td").css("color","#666");
				   $(this).css("color","#F74C31");
				   $(".wfj12_018_assessimg").find("td").eq(1).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_02.png");
				   $(".wfj12_018_assessimg").find("td").eq(2).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_03.png");
			   }else if($(this).text()=="中评"){
				   $(".wfj12_018_assessimg").find("td").eq(0).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_01.png");
				   $(this).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_02_02.png");
				   $(".wfj12_018_assessimg").find("td").css("color","#666");
				   $(this).css("color","#F74C31");
				   $(".wfj12_018_assessimg").find("td").eq(2).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_03.png");
			   }else{
				   $(".wfj12_018_assessimg").find("td").eq(0).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_01.png");
				   $(".wfj12_018_assessimg").find("td").eq(1).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_02.png");
				   $(this).find("img").attr("src",basePath+"images/contacts/comments/wfj_assessment_03_03.png");
				   $(".wfj12_018_assessimg").find("td").css("color","#666");
				   $(this).css("color","#F74C31");
			   }
			   $(this).addClass("selected");
		   });
		   
		   
		   
		   /*评价【星星的分数点击*/
		   
		    $(".comtbl img").click(function(){
              var name = $(this).attr("name");
              $(this).parent().parent().find(".score").val(name);
             
              });  
		   
		   
		   $(".wfj12_018_depict").find("li").click(
		   function(){
		   var p =$(".wfj12_018_width").find("td").text();
			   var r=parseInt($(this).find("img").attr("name"));
			   var $div=$(this).parent().parent();
			   for(var i=0;i<5;i++){
					if(i<=r){
						 $div.find("li").eq(i).find("img").attr("src",basePath+"images/contacts/comments/wfj_star_02.png"); 
					}else{
						$div.find("li").eq(i).find("img").attr("src",basePath+"images/contacts/comments/wfj_star_01.png");
					} 
			   }
			   
		   });
		   
		   
		   /*是否匿名*/
		    $(".wfj12_018_bottom").find("img").click(function(){
				if($(this).attr("src")==basePath+"images/contacts/comments/wfj_right_03.png"){
					$(this).attr("src",basePath+"images/contacts/comments/wfj_right_02.png");
				}else{
					$(this).attr("src",basePath+"images/contacts/comments/wfj_right_03.png");
				}
			});
		   
			$(".wfj12_018_add_imgs").find("img").eq(0).click(function(){
				if($(".wfj12_018_img").length>4){
					
				return;
				}
				$("#occlusion2").css("z-index",$(".wfj12_018").css("z-index")+1);
				var file=$("#pictureDiv").find("#file").clone(true).attr("id","file"+select).addClass("pictureFile");
				$this=$("#pictureDiv").find("#picture").clone(true).attr("id","picture"+select).attr("name",select);
				/*if($(".wfj12_018_img").length>5)
					$("#pictureDiv").append(file);
					return;*/
				$("#pictureDiv").append(file);
				file.click();
				select++;
				return false;
			});
			$(".wfj12_018_add_imgs_dele").click(function(){
				$(this).parent(".wfj12_018_addimg").remove();
			})
			
			$(".pictureFile").live("change",function(){
			$(".wfj12_018_addph").css("z-index",$("#occlusion2").css("z-index")+1);
				//$(".wfj12_018_addph").fadeIn(1000);
			getImgUrl($(this));
			});
			$(".jqmOverlay").live("click",function(){
				//$(".wfj12_018_addph").fadeOut();
				$("#occlusion2").jqmHide();
			});
			
			
			//上传图片预览
		
		 $(".wfj12_018_addph").find("li").eq(0).click(function(){
			$(this).find("input").click();
		});
	 	$(".wfj12_018_addph").find("li").eq(1).click(function(){							
			$(this).find("input").click();
		}); 
		//取消
		 $(".wfj12_018_addph").find("li").eq(2).click(function(){
			$(".wfj12_018_addph").fadeOut();
			$("#occlusion2").jqmHide();
		}); 
		 //阻止冒泡
		$("input[type='file']").click(function(event){
			event.stopPropagation();
		});
	    	//拍照 
	    	$(".wfj12_018_addph").find("li").eq(0).click(function(){
	    		$(".wfj01_001_con_right").before($("#sa").clone(true).prop("id","pic"+select));
	    		$("#pic"+select).find("input").prop("id","file"+select);	    		    		
	    		$("#file"+select).click();
	
	    		$(".file").live("change",function(){
	    			var picPath=$(this);
	    		 	getImgUrl(picPath);
	    		});
	    		
	    		$(".wfj12_018_addph").fadeOut();
				$("#occlusion2").jqmHide();
				select++;
	    	});
	    	

        });
       function show(id){
			var picPath=$("#"+id);
			getImgUrl(picPath);
			$(".wfj12_018_addph").fadeOut();
			$("#occlusion2").jqmHide();
		}
        function getImgUrl($t){	    		
	    		var img=new Image();
	    		var dw=$(".wfj12_018_add_imgs").width(),dh=$(".wfj12_018_add_imgs").height();
	    		var f=$t.prop("files")[0];
	    		if(f.type.match('image.*')){
	    			var r = new FileReader();
	    			r.onload = function(e){
	    				img.setAttribute('src',e.target.result);
	    		    };
	    			r.readAsDataURL(f);
	    		}
	    		img.onload=function(){
	    			var cv = document.createElement('div');
	    			cv.innerHTML="<canvas></canvas>";
	    			var rc = cv.children[0];
	    			var ct = rc.getContext('2d');
	    			//rc.width=dw;
	    			//rc.height=dh;
	    			rc.width=dw=img.width;
					rc.height=dh=img.height;
	    			ct.drawImage(img,0,0,dw,dh);
	    			var da=rc.toDataURL();
	    			$this.find("img").eq(0).attr("src",da);
	    			$("#redit").before($this);
	    			
	    			if($(".wfj12_018_img").length>5)
						$("#redit").css("display","none");
					/* $("#picture"+select).find("img").attr("src",da);
					$("#picture"+select).parent().css("display","block");
					
	    			$(".wfj12_018_add_imgs").find("img").attr("src",da); */
	    			
    				
	    		};
	    		
	    	}