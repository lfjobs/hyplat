$(function(){
	var workCity=$(".Certificate_con .select .Selected ul #local #province").val();
	var workPlace=$(".Certificate_con .select .Selected ul #local #city").val();
	var codePID=$(".Certificate_con .select .Selected ul #jobs #codePID").val();
	var codeID=$(".Certificate_con .select .Selected ul #jobs #codeID").val();
	var local=$(".Certificate_con .select .Selected ul #local #selLocal").val();
	var jobs=$(".Certificate_con .select .Selected ul #jobs #selJobs").val();
	var sel=$("#selPo").val();
	getCodeValueFirst();
	getHotPosition();
	if(sel!=""){
		if(workCity!=""||workPlace!=""){
			$(".Certificate_con .select .Selected ul #local p").text(local);
			$(".Certificate_con .select .Selected ul #local").show();
		}
		if(codePID!=""||codeID!=""){
			 $(".Certificate_con .select .Selected ul #jobs p").text(jobs);
			 $(".Certificate_con .select .Selected ul #jobs").show();
		}
		selPosition();
	}else{
		getRecruitmentList();
	}
});

//上一步
function lastStep(){
	var positionName=$(".Certificate_con .search .sea_left input").val();
	var workCity=$(".Certificate_con .select .Selected ul #local #province").val();
	var workPlace=$(".Certificate_con .select .Selected ul #local #city").val();
	var codePID=$(".Certificate_con .select .Selected ul #jobs #codePID").val();
	var codeID=$(".Certificate_con .select .Selected ul #jobs #codeID").val();
	if(pageNumber>1){
		pageNumber = parseInt(pageNumber)-1;
		if(positionName==""&&workCity==""&&workPlace==""&&codePID==""&&codeID==""){
			getRecruitmentList();
		}else{
			selPosition();
		}
	}
}
//下一步
function nextStep(){
	var positionName=$(".Certificate_con .search .sea_left input").val();
	var workCity=$(".Certificate_con .select .Selected ul #local #province").val();
	var workPlace=$(".Certificate_con .select .Selected ul #local #city").val();
	var codePID=$(".Certificate_con .select .Selected ul #jobs #codePID").val();
	var codeID=$(".Certificate_con .select .Selected ul #jobs #codeID").val();
	if(pageNumber<pageCount){
		pageNumber = parseInt(pageNumber)+1;
		if(positionName==""&&workCity==""&&workPlace==""&&codePID==""&&codeID==""){
			getRecruitmentList();
		}else{
			selPosition();
		}
	}
}

//选取页数
function choose(obj){
	var positionName=$(".Certificate_con .search .sea_left input").val();
	var workCity=$(".Certificate_con .select .Selected ul #local #province").val();
	var workPlace=$(".Certificate_con .select .Selected ul #local #city").val();
	var codePID=$(".Certificate_con .select .Selected ul #jobs #codePID").val();
	var codeID=$(".Certificate_con .select .Selected ul #jobs #codeID").val();
	pageNumber = $(obj).text();
	if(positionName==""&&workCity==""&&workPlace==""&&codePID==""&&codeID==""){
		getRecruitmentList();
	}else{
		selPosition();
	}
}
//获取职位的一级分类
function getCodeValueFirst(){
	var url=basePath+"ea/newpcend/sajax_ea_ajaxCodeValueFirst.jspa";
	$.ajax({
		url:url,
		type:"post",
		async : true,
		dataType:"json",
		success:function(data){
			var member = eval("(" + data + ")");
			var array = [];
			if(member!=null&&member.mainObj!=null&&member.mainObj.length>0){
				array.push("<li>全部</li>");
				$(member.mainObj).each(function(){
					array.push("<li class='job'>"+this[1]);
					array.push("<input type='hidden' value='"+this[0]+"' /></li>")
				});
			}
			$(".Certificate_con .select .jobs").append(array.join(""));
			/*添加事件*/
			$(".Certificate_con .select .jobs li").on('click',function(){
			        var txt = $(this).text();
			        var codePID=$(this).find("input").val();
			        $(".Certificate_con .select .Selected ul #jobs p").text(txt);
			        $(".Certificate_con .select .Selected ul #jobs #codePID").val(codePID);
			        $(".Certificate_con .select .Selected ul #jobs #codeID").val("");
			        $(".Certificate_con .select .Selected ul #jobs").show();
			    });
			array = [];
			if(member!=null&&member.mainObj!=null&&member.mainObj.length>0){
				$(member.mainObj).each(function(){
					array.push("<li><p>"+this[1]+"</p>");
					array.push("<input type='hidden' value='"+this[0]+"' />");
					array.push("<img src='"+basePath+"page/newMyapp/images/ico-right2.png'></li>")
				});
			}
			$(".Certificate_alert .left").find("ul").append(array.join(""));
			 /*职业更多*/
		    $(".Certificate_con .select .mil_se #more_js").click(function(){
		    	$(".Certificate_alert .right").find("ul").remove();
		    	var codeID=$(".Certificate_alert .left ul li:first").find("input").val();
		         $.ajax({
		        	 url:basePath+"ea/newpcend/sajax_ea_ajaxCodeValue.jspa",
		        	 type:"post",
		        	 async : true,
		        	 data:{"codeID":codeID},
		        	 dataType:"json",
		        	 success:function(data){
		        		 var members = eval("(" + data + ")");
		        		 var secArray = [];
		        		 if(members!=null&&members.secObj!=null&&members.secObj.length>0){
		        			 $(members.secObj).each(function(){
		        				 secArray.push("<ul><h5>"+this[1]+">></h5>");
		        				 var value=this[0];
		        				 $(members.nextObj).each(function(){
		        					 if(this[1]==value){
		        						 secArray.push("<li onclick='showPosition(this);'>"+this[2]);
		        						 secArray.push("<input type='hidden' value='"+this[0]+"' /></li>")
		        					 }
		        				 });
		        				 secArray.push("</ul>");
		        			 });
		        		 }
		        		 $(".Certificate_alert .right").append(secArray.join(""));
		        	 },
		        	 error:function(){
		        		 alert("职位加载失败！");
		        	 }
		         })
		         $(".Certificate_alert_").show();
		         $(".Certificate_alert .left ul li:first").addClass("active").siblings().removeClass("active");
		    });
			/*职业弹框左*/
		    $(".Certificate_alert .left ul li").click(function(){
		        $(this).addClass("active").siblings().removeClass("active");
		        var codeID=$(this).find("input").val();
		         $.ajax({
		        	 url:basePath+"ea/newpcend/sajax_ea_ajaxCodeValue.jspa",
		        	 type:"post",
		        	 async : true,
		        	 data:{"codeID":codeID},
		        	 dataType:"json",
		        	 success:function(data){
		        		 var members = eval("(" + data + ")");
		        		 var secArray = [];
		        		 if(members!=null&&members.secObj!=null&&members.secObj.length>0){
		        			 $(members.secObj).each(function(){
		        				 secArray.push("<ul><h5>"+this[1]+">></h5>");
		        				 var value=this[0];
		        				 $(members.nextObj).each(function(){
		        					 if(this[1]==value){
		        						 secArray.push("<li onclick='showPosition(this);'>"+this[2]);
		        						 secArray.push("<input type='hidden' value='"+this[0]+"' /></li>")
		        					 }
		        				 });
		        				 secArray.push("</ul>");
		        			 });
		        		 }
		        		 $(".Certificate_alert .right").find("ul").remove();
		        		 $(".Certificate_alert .right").append(secArray.join(""));
		        	 },
		        	 error:function(){
		        		 alert("职位加载失败！");
		        	 }
		         });
		    });
		},
		error: function(){
			alert("职位加载失败！");
		}
	});
}

//查询职位信息
function getRecruitmentList(){
	var url=basePath+"ea/newpcend/sajax_ea_ajaxRecruitment.jspa";
	$.ajax({
		url:url,
		type:"post",
		async : true,
		data:{
			"pageForm.pageNumber":pageNumber,
			"pageForm.pageSize":10
		},
		dataType:"json",
		success:function(data){
			var commodity = eval("(" + data + ")");
			var pageForm=commodity.pageForm;
			var array = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0){
				$(pageForm.list).each(function(i) {
					if(i%2==0){
						array.push("<ul class='one' onclick='details(this)'>");
					}else{
						array.push("<ul class='two' onclick='details(this)'>");
					}
					array.push("<input class='riID' type='hidden' value='"+this[0]+"'/>");
					array.push("<li class='job_title'>"+this[1]+"</li>");	
					array.push("<li class='job_category'>"+this[2]+"</li>");	
					array.push("<li class='number'>"+this[3]+"</li>");	
					array.push("<li class='site'>"+this[4]+"</li>");	
					array.push("<li class='pubdate'>"+this[5]+"</li>");	
					array.push("</ul>");
				});
			}
			$("#position").empty();
			$("#position").append(array.join(""));
			$("#positionCount").html("");
			$(".page_my").empty();
			if(pageForm!=null){
				pageCount = pageForm.pageCount;
		        pageNumber = pageForm.pageNumber;
		        var paging = [];
		        paging.push("<button style='float: left;' onclick='lastStep();'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-left.png'></button><ul>");
		        for ( var i = 1; i <= pageCount; i++) {
		        	if(pageNumber==i){
		        		paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
		        	}else{
		        		if(i==1||i==pageCount||(i>=pageNumber-2&&i<=pageNumber+2)){
		        			paging.push("<li onclick='choose(this)'>"+i+"</li>");
		        		}else if(i!=1&&i!=pageCount&&(i==pageNumber-3||i==pageNumber+3)){
		        			paging.push("<li style='border:none;background-color:white;' class='point'><span>...</span></li>");
		        		}
		        	}
				}
		        paging.push("</ul><button style='float: right;' onclick='nextStep();'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-right.png'></button>");
		        $("#positionCount").html("<span style='color:black;'>共</span>"+pageForm.recordCount+"<span style='color:black;'>个职位</span>");
		        $(".page_my").append(paging.join(""));
		        var nub = $(".bid_pages .page_my ul li").length;
		        $(".bid_pages .page_my ul").css("width",40*nub+"px");
		    	$(".bid_pages .page_my").css("width",80+40*nub+"px");
			}else{
				$("#positionCount").html("<span style='color:black;'>共</span>0<span style='color:black;'>个职位</span>");
			}
		},
		error:function(data){ 
			alert("获取失败！");
		}
	});
}	
	
	
//查询热门职位
function getHotPosition(){
	var url=basePath+"ea/newpcend/sajax_ea_ajaxHotPosition.jspa";
	$.ajax({
		url:url,
		type:"post",
		async : true,
		data:{
			"pageForm.pageNumber":pageNumber,
			"pageForm.pageSize":10
		},
		dataType:"json",
		success:function(data){
			var commodity = eval("(" + data + ")");
			var pageForm=commodity.pageForm;
			var array=[];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0){
				$(pageForm.list).each(function() {
					array.push("<li><label onclick='details(this)'>");
					array.push("<input class='riID' type='hidden' value='"+this[0]+"'/>");
					array.push("<span>"+this[1]+"</span>")
					array.push("<span class='pay'>"+this[2]+"</span></label></li>");
				});
			}
			$(".Certificate_con .right").find("ul").append(array.join(""));
		},
		error:function(data){
			alert("获取失败！");
		}
	});
}

//搜索职位
function selPosition(){
	var positionName=$(".Certificate_con .search .sea_left input").val();
	var workCity=$(".Certificate_con .select .Selected ul #local #province").val();
	var workPlace=$(".Certificate_con .select .Selected ul #local #city").val();
	var codePID=$(".Certificate_con .select .Selected ul #jobs #codePID").val();
	var codeID=$(".Certificate_con .select .Selected ul #jobs #codeID").val();
	var url=basePath+"ea/newpcend/sajax_ea_ajaxSelPosition.jspa";
	$.ajax({
		url:url,
		type:"post",
		async : true,
		data:{
			"pageForm.pageNumber":pageNumber,
			"pageForm.pageSize":10,
			"recruitInfo.positionName":positionName,
			"recruitInfo.workCity":workCity,
			"recruitInfo.workPlace":workPlace,
			"codePID":codePID,
			"codeID":codeID
		},
		dataType:"json",
		success:function(data){
			var commodity = eval("(" + data + ")");
			var pageForm=commodity.pageForm;
			var array = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0){
				$(pageForm.list).each(function(i) {
					if(i%2==0){
						array.push("<ul class='one' onclick='details(this)'>");
					}else{
						array.push("<ul class='two' onclick='details(this)'>");
					}
					array.push("<input class='riID' type='hidden' value='"+this[0]+"'/>");
					array.push("<li class='job_title'>"+this[1]+"</li>");	
					array.push("<li class='job_category'>"+this[2]+"</li>");	
					array.push("<li class='number'>"+this[3]+"</li>");	
					array.push("<li class='site'>"+this[4]+"</li>");	
					array.push("<li class='pubdate'>"+this[5]+"</li>");	
					array.push("</ul>");
				});
			}
			$("#position").empty();
			$("#position").append(array.join(""));
			$("#positionCount").html("");
			$(".page_my").empty();
			if(pageForm!=null){
				pageCount = pageForm.pageCount;
		        pageNumber = pageForm.pageNumber;
		        var paging = [];
		        paging.push("<button style='float: left;' onclick='lastStep();'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-left.png'></button><ul>");
		        for ( var i = 1; i <= pageCount; i++) {
		        	if(pageNumber==i){
		        		paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
		        	}else{
		        		if(i==1||i==pageCount||(i>=pageNumber-1&&i<=pageNumber+1)){
		        			paging.push("<li onclick='choose(this)'>"+i+"</li>");
		        		}else if(i!=1&&i!=pageCount&&(i==pageNumber-2||i==pageNumber+2)){
		        			paging.push("<li style='border:none;background-color:white;' class='point'><span>...</span></li>");
		        		}
		        	}
				}
		        paging.push("</ul><button style='float: right;' onclick='nextStep();'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-right.png'></button>");
		        $("#positionCount").html("<span style='color:black;'>共</span>"+pageForm.recordCount+"<span style='color:black;'>个职位</span>");
		        $(".page_my").append(paging.join(""));
		        var nub = $(".bid_pages .page_my ul li").length;
		        $(".bid_pages .page_my ul").css("width",40*nub+"px");
		    	$(".bid_pages .page_my").css("width",80+40*nub+"px");
			}else{
				$("#positionCount").html("<span style='color:black;'>共</span>0<span style='color:black;'>个职位</span>");
			}
		},
		error:function(data){ 
			alert("获取失败！");
		}
	});
}
	
//职位详情
function details(obj){
	var riID=$(obj).find(".riID").val();
	var url=basePath+"ea/newpcend/ea_pcRecruitmentDetails.jspa?recruitInfo.riId="+riID;
	window.location.href=url;
}

//展示选中的职位
function showPosition(obj){
	  var txt = $(obj).text();
	  var codeID=$(obj).find("input").val();
      $(".Certificate_con .select .Selected ul #jobs p").text(txt);
      $(".Certificate_con .select .Selected ul #jobs #codeID").val(codeID);
      $(".Certificate_con .select .Selected ul #jobs #codePID").val("");
      $(".Certificate_con .select .Selected ul #jobs").show();
      $(".Certificate_alert_").hide();
}
