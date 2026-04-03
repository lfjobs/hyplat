   $(function(){	  
	   load();
	   //判断是否认证
	   if(authState=='00'){
		   $(".popup_rz").show();
		   $(".rz_infotop").html("您的公司信息尚未认证");
	   }else if(authState=='03'){
		   $(".popup_rz").show();
		   $(".rz_infotop").html("认证失败,请重新认证");
	   }
	   //关闭认证
	   $(".rz_close").click(function(){
		   $(".popup_rz").hide();
	   })
	   //跳转认证
	   $(".rz_btn").click(function(){
		   document.location.href = basePath + "/ea/qrshare/ea_queryState.jspa?auditSkip=00";
	   })
	   
	 //点击加号，添加快捷应用
       $(".nav_add").click(function(){
    	   var head=$(this).prev().text();
    	   $(".overlay").addClass("active");         
    	   $(".al_head").text(head);          
    	   var ppId=$(this).attr("id");
    	   $(".s_nav_list").empty();
    	   $.ajax({
    		  url:basePath+"/mobile/office/sajax_ea_fastApplicationAjax.jspa?",
    		  type:"get",
    		  data:{"ppId":ppId,"flag":"toAdd"},
    		  async:false,
    		  success: function (data){
    			  var member=eval("("+data+")");
    			  var top=member.top;
    			  var list=member.child;
    			  var str=new Array();
    			  if(list.length>0){
    					for(var i=0;i<list.length;i++){
    						var pp=list[i];
    						if(pp[3]==top){
    						str.push('<li><div class="s_navico s_nav_01" style="background:url('+basePath+pp[5]+') no-repeat center;background-size:60%"></div>');
    	                    str.push('<span>'+pp[0]+'</span>');
    	                    if(pp[6]==''||pp[6]==null){
    	                    	str.push('<a id="'+pp[1]+'" href="javascript:;" class="add_btn " onclick="add(this.id,\'add\')">添加</a></li>');
    	                    }else{
    	                    	str.push('<a href="javascript:;" class="add_btn added">已添加</a></li>');
    	                    }
    	                    var rem=1;
    	                    digui1(list,pp,str,rem);	                                   
    	                    str.push('</ul></div>');               	            
    						}
    					}
    					$(".s_nav_list").append(str.join(""));
    					$('.s_nav_list').show();
    				}
    		  },
    		  error: function(){
    			  alert("加载失败");
    		  }
    	   });
       });
       //遮罩层返回按钮
       $(".overlay_back").click(function(){
           $(".overlay").removeClass("active");
           $(".s_nav_list").hide();
       });
       //点击编辑完成
       $(".edit").click(function(){
           var text=$(this).text();
           if(text=='编辑'){
               $(this).text("完成");
               $(".list_dele").show();
           }else{
               $(this).text("编辑")
               $(".list_dele").hide();
           }
       });
       //点击删除按钮
       $(".list_dele").click(function(){
    	   var ppId=$(this).attr("id");
    	   $.ajax({
    		  url: basePath+"/mobile/office/sajax_ea_addOrDelApplication.jspa?",
    		  type: "get",
    		  data:{
    			"ppId":ppId,  
    			"flag":"del"
    		  },
    		  async:false,
    		  success:function (data){
    			  var member=eval("("+data+")");
    			  var b=member.b;
    			  if(b){
    				 $("a#"+ppId).parent().animate({left:"-100%"},500,function(){
    		               $(this).addClass("nav_hide");
    		           });  
    				 $("li#"+ppId).animate({left:"-100%"},500,function(){
    					 $(this).addClass("nav_hide");
    				 })		               
    				 // re_load();
    			  }
    		  }
    	   });          
       });
   });//加载结束

	function load(){
		$.ajax({
			url: basePath+"/mobile/office/sajax_ea_fastApplicationAjax.jspa?",
			type:"post",
			async:false,
			success:function (data){
				var member=eval("("+data+")");
				var list=member.child;
				var top=member.top;
				var str=new Array();
				if(list.length>0){
					for(var i=0;i<list.length;i++){
						var pp=list[i];
						if(pp[3]==top){
						str.push('<div class="list_wrap">');
		                str.push('<div class="list_nav clearfix">');
	                    str.push('<div class="list_nav_ico nav_ico_01" style="background:url('+basePath+pp[6]+') no-repeat center;background-size:60%"></div>');
		                str.push('<div class="list_nav_con">');
		                if(pp[6]==null){
		                	str.push('<a href="javascript:;">'+pp[0]+'</a>');
		                }else{
		                	str.push('<a href="'+basePath+pp[6]+'">'+pp[0]+'</a>');
		                }
	                    str.push('<a id="'+pp[1]+'" href="javascript:;" class="nav_add" data-popup="hr"></a></div></div>');
	                    str.push('<ul class="list_con">');
	                    var rem=1;
	                    digui(list,pp,str,rem);	                                   
	                    str.push('</ul></div>');               	            
						}
					}
					$(".al_wrap").append(str.join(""));
				}			
			},
			error: function (){
				alert("加载失败");
			}
		});
	}
	//递归查询
	function digui(arr,ob,str,rem){
		for (var k=0;k<arr.length;k++){
			var p=arr[k];
			if(p[3]!=null&&p[3]==ob[1]){
				str.push('<li id="'+ob[1]+'" class="list_box" style="margin-left:'+rem+'rem">');
                if(p[6]==null){
                	str.push('<a href="javascript:;">'+p[0]+'</a>');
                }else{
                	str.push('<a href="'+basePath+p[6]+'">'+p[0]+'</a>');
                }
                str.push('<a id="'+p[1]+'" href="javascript:;" class="list_dele"></a></li>');                           
                digui(arr,p,str,rem+1);              
			}else{			
				continue;
			}
			
		}	
	}
	function digui1(arr,ob,str,rem){
		for (var k=0;k<arr.length;k++){
			var p=arr[k];
			if(p[3]!=null&&p[3]==ob[1]){
				str.push('<li style="margin-left:'+rem+'rem"><div class="s_navico s_nav_01"></div>');
                str.push('<span>'+p[0]+'</span>');
                if(p[6]=='01'||p[6]==null){
                	str.push('<a id="'+p[1]+'" href="javascript:;" class="add_btn" onclick="add(this.id,\'add\')">添加</a></li>');
                }else{
                	str.push('<a href="javascript:;" class="add_btn added">已添加</a></li>');
                } 
                digui1(arr,p,str,rem+1);               
			}else{
				continue;
			}			
		}	
	}

	function add(ppId,flag){
		console.log(ppId+flag);
		$.ajax({
			url: basePath+"/mobile/office/sajax_ea_addOrDelApplication.jspa?",		
			type:"get",
			async:false,
			data:{"ppId":ppId,"flag":flag},
			success:function (data){
				var member =eval("("+data+")");
				var b=member.b;
				if(b){
					re_load();
				}
			}
		});
	}
	
	function re_load(){
		window.location.href=basePath+"/mobile/office/mobileoffice_fastApplication.jspa?";
	}