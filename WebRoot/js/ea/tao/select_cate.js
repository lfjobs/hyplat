$(function(){
	$(".cs-type").each(function(){
		$this=$(this);
		$.ajax({
			url :basePath+"ea/babyclass/sajax_ea_sajaxGetSubCategoryList.jspa?type="+$(this).text(),
			type:"post",
			async: false,
			success:function(data){
                var member = eval("(" + data + ")");
                var list=member.list;
                var ul=$("#ul").clone(true).attr("id","").attr("style","");
                for(var i=0;i<list.length;i++){
                	var li=$("#li").clone(true).attr("style","").attr("id",list[i].cateID).text(list[i].catename).addClass("li-cate");
                	ul.append(li);
                }
                $this.after(ul);
			},
			error:function(data){
				alert("数据获取失败");
			}
		});
	});
	$(".li-cate").live("click",function(){
		var num=parseInt($(this).parents(".cs-cateList").attr("name")); 
		$(this).parents(".cs-cateList").find(".level").removeClass("level");
		$(this).addClass("level");
		var cateId=$(this).attr("id");
		$.ajax({
			url :basePath+"ea/babyclass/sajax_ea_sajaxGetSubCategoryList.jspa?catePid="+cateId,
			type:"post",
			async: false,
			success:function(data){
                var member = eval("(" + data + ")");
                var list=member.list;
                if(num!=4){
                	 $("#list"+num).nextAll().each(function(){
                     	$(this).remove();;
                     }); 
                }
                if(list.length>0){
                	if(num!=4){
                        var levelMenu=$("#levelMenu").clone(true).attr("style","").attr("id","list"+(num+1)).attr("name",num+1);
                        levelMenu.find("input").attr("id",cateId);
                        var ul=$("#cascadeUl").clone(true).attr("id","").attr("style","");
                        for(var i=0;i<list.length;i++){
                        	var li=$("#li").clone(true).attr("style","").attr("id",list[i].cateID).text(list[i].catename).addClass("li-cate").attr("name","available");
                        	ul.append(li);
                        }
                        levelMenu.find(".cc-cbox").append(ul);
                        $("#list"+num).after(levelMenu);                   
                    }else{
                    	$("#list1").attr("style","display: none;").attr("id","").attr("name","");
                    	$("#list2").attr("id","list1").attr("name","1"); $("#list3").attr("id","list2").attr("name","2");
                    	$("#list4").attr("id","list3").attr("name","3");
                    	
                    	 var levelMenu=$("#levelMenu").clone(true).attr("style","").attr("id","list4").attr("name","4");
                         levelMenu.find("input").attr("id",cateId);
                         var ul=$("#cascadeUl").clone(true).attr("id","").attr("style","");
                         for(var i=0;i<list.length;i++){
                         	var li=$("#li").clone(true).attr("style","").attr("id",list[i].cateID).text(list[i].catename).addClass("li-cate");
                         	ul.append(li);
                         }
                         levelMenu.find(".cc-cbox").append(ul);
                         $("#list3").after(levelMenu);
                    }
                }
			},
			error:function(data){
				alert("数据获取失败");
			}
		});
		$(".category-path").find("li").remove();
		var li="<li id='"+cateId+"'>";
		$(".level").each(function(index){
			if(index==0)
				li+=$(this).text();
			else
				li+="&nbsp;&gt;&gt;&nbsp;"+$(this).text();
		});
		li+="</li>";
		$(".category-path").append(li);
		if($(this).attr("name")=="available"){
			$(".cateBtn").removeClass("cateBtn-disabled");
			$("#J_CatePubBtn").addClass("available");
		}else{
			$(".cateBtn").addClass("cateBtn-disabled");
			$("#J_CatePubBtn").removeClass("available");
		}
	});
	$(".j_Link").click(function(){
		if($(this).attr("id")=="J_LinkNext"){
			if($("#list4").next().attr("style")){
				$("#list1").attr("style","display: none;").attr("id","").attr("name","");
	        	$("#list2").attr("id","list1").attr("name","1"); $("#list3").attr("id","list2").attr("name","2");
	        	 $("#list4").attr("id","list3").attr("name","3");
	        	$("#list3").next().attr("id","list4").attr("name","4").attr("style","");
			}
		}else{
			if($("#list1").prev().attr("style")){
				$("#list4").attr("style","display: none;").attr("id","").attr("name","");
				$("#list3").attr("id","list4").attr("name","4");
				$("#list2").attr("id","list3").attr("name","3");$("#list1").attr("id","list2").attr("name","2");
				$("#list2").prev().attr("id","list1").attr("name","1").attr("style","");
			}
		}
	});
	$(".cs-type").live("click",function(){
		if($(this).attr("name")=="hide")
			$(this).attr("name","").parent().find("ul").attr("style","");
		else
			$(this).attr("name","hide").parent().find("ul").attr("style","display: none;");
	});
	$(".fuzzyQuery").live("input propertychange",function(){
		$this=$(this);
		var url=basePath+"ea/babyclass/sajax_ea_sajaxGetFuzzyQueryList.jspa?catePid="+$(this).attr("id")+"&catename="+$(this).val();
		$.ajax({
			url :url,
			type:"post",
			async: false,
			success:function(data){
                var member = eval("(" + data + ")");
                var list=member.list;
                var ul=$("#cascadeUl").clone(true).attr("id","").attr("style","");
                for(var i=0;i<list.length;i++){
                	var li=$("#li").clone(true).attr("style","").attr("id",list[i].cateID).text(list[i].catename).addClass("li-cate");
                	ul.append(li);
                }
                $this.parents(".cs-cateList").find(".cc-cbox").html(ul);
                $this.parents(".cs-cateList").nextAll().remove();
			},error:function(data){
				alert("数据获取失败");
			}
			
		});
	});
	$("#J_SearchButton").click(function(){
		if($("#J_SearchKeyWord").val()){
            $("#globalFuzzyQuery").html("");
			$.ajax({
				url:basePath+"ea/babyclass/sajax_ea_sajaxGetGlobalFuzzyQueryList.jspa?catename="+$("#J_SearchKeyWord").val(),
				type:"post",
				async: false,
				success:function(data){
	                var member = eval("(" + data + ")");
	                var list=member.list;
	                for(var i=0;i<list.length;i++){
	                	var s="background-color: #FFFFE0;";
	                	if(i%2==0){
	                		s="background-color: #FFEFDB;";
	                	}
	                	var str=list[i].split("+");
	                	var li="<li id='"+str[1]+"' style=\"height:30px;"+s+"\" name="+i+" class='global'>"+str[0]+"</li>";
	                	$("#globalFuzzyQuery").append(li);
	                }
	                $("#J_OlCascadingList").css("display","none");
	                $("#globalFuzzyQuery").attr("style","");
				},
				error:function(data){
					alert("数据获取失败");
				}
			});
		}
	});
	$(".global").live("click",function(){
		var int=$(".glo").attr("name");
		if(int%2==0){
			$(".glo").attr("style","height:30px;background-color: #FFEFDB;").removeClass("glo");
		}else{
			$(".glo").attr("style","height:30px;background-color: #FFFFE0;").removeClass("glo");
		}
		$(this).addClass("glo").attr("style","height:30px;background-color:#E0FFFF;");
		var li="<li id='"+$(this).attr("id")+"'>"+$(this).text()+"</li>";
		$(".clearfix").find(".#J_OlCatePath").html(li);
		$(".catePublish").removeClass("cateBtn-disabled");
	});
	
	
	$(".firstLayer").live("input propertychange",function(){
		$(".cc-tree-cont").html("");
		$this=$(this);
		$.ajax({
			url:basePath+"ea/babyclass/sajax_ea_getTypePageList.jspa?tp=ajax&catename="+$(this).val(),
			type:"post",
			async: false,
			success:function(data){
                var member = eval("(" + data + ")");
                var list=member.list;
                if(list.length>0){
                	for(var i=0;i<list.length;i++){
                		var li=$("#cascadeLi").clone(true).attr("id","").attr("style","");
                		li.find("div.cc-focused").text(list[i]);
                		$.ajax({
                			url:basePath+"ea/babyclass/sajax_ea_sajaxGetSubCategoryList.jspa?tp=ajax&type="+list[i]+"&catename="+$this.val(),
                			type:"post",
                			async: false,
                			success:function(data){
                                var member = eval("(" + data + ")");
                                var list2=member.list;
                                var ul2=$("#ul").clone(true).attr("id","123").attr("style","");
                                for(var i=0;i<list2.length;i++){
                                	var li2=$("#li").clone(true).attr("style","").attr("id",list2[i].cateID).text(list2[i].catename).addClass("li-cate");
                                	ul2.append(li2);
                                }
                                li.append(ul2);
                			},error:function(data){
                				alert("数据获取失败");
                			}
                		});
                		$(".cc-tree-cont").append(li);
                	}
                }
			},
			error:function(data){
				alert("数据获取失败");
			}
		});
	});
});