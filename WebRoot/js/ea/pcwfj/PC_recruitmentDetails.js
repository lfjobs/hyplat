$(function(){
	getCodeValueFirst();



    $(document).on("click",'.applyJob',function(){
        var riIds =$(this).prev().text();

        var url = basePath+"ea/bidrecruit/sajax_ea_postResume.jspa";
        $.ajax({
            url : url,
            type : "get",
            asycn : false,
            dataType : "json",
            data : {
                riIds:riIds
            },
            success : function(data) {
                var member = eval("(" + data + ")");
                var result = member.result;
                var login = member.login;
                staffid = member.staffid;
                if(login=="login"){
                    document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
                    return;
                }
                if(result=="nojianli"){
                    // $(".tipcontent").text("公司账号没有个人简历");
                    // $(".tipconfirm").text("确定");
                    // $(".tan_mo").hide();
                    // $(".tiptan").show();
                    alert("公司账号没有个人简历")
                }
                else if(result=="noresume"){
                    // $(".tipcontent").text("投递失败，请完善您的简历");
                    // $(".tipconfirm").text("去完善简历");
                    // $(".tan_mo").hide();
                    // $(".tiptan").show();
                    alert("投递失败，请完善您的简历")

                }else if(result=="success")
                {
                    //成功后跳转
                    // $(".tan_kuang").css("background","rgba(0,0,0,0.4)");
                    // $(".tan1").show();
                    // $(".tan_mo").hide();
                    // $("li#"+riIds).find(".tou").text("已投");
                    // $("li#"+riIds).find(".tou").removeClass("tou").addClass("yitou");
					alert("投递成功!");
					$(".applyJob").removeClass("applyJob").attr("style","background-color: #d6cdc9").text("已申请");

                }
                token1 = 0;


            },error:function(data){
                alert("投递失败");
            }

        });

    });
});

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
		        		/* alert("职位加载失败！");*/
		        	 }
		         })
		         $(".Certificate_alert_").show();
		         $(".Certificate_alert .left ul li:first").addClass("active").siblings().removeClass("active");
		    });
			/*职业弹框左*/
		    $(".Certificate_alert .left ul li").click(function(){
		        $(this).addClass("active").siblings().removeClass("active");
		        $(".Certificate_alert .right").find("ul").remove();
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
		        		 $(".Certificate_alert .right").append(secArray.join(""));
		        	 },
		        	 error:function(){
		        		 /*alert("职位加载失败！");*/
		        	 }
		         })
		    });
		},
		error: function(){
			/*alert("职位加载失败！");*/
		}
	});
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

//搜索职位
function selPosition(){
	var positionName=$(".Certificate_con .search .sea_left input").val();
	var workCity=$(".Certificate_con .select .Selected ul #local #province").val();
	var workPlace=$(".Certificate_con .select .Selected ul #local #city").val();
	var codePID=$(".Certificate_con .select .Selected ul #jobs #codePID").val();
	var codeID=$(".Certificate_con .select .Selected ul #jobs #codeID").val();
	var local=$(".Certificate_con .select .Selected ul #local p").text();
	var jobs=$(".Certificate_con .select .Selected ul #jobs p").text();
	var url = encodeURI(basePath+"ea/newpcend/ea_skip.jspa?titleJudge=07&seven=01&sel=01&positionName="
	+positionName+"&workCity="+workCity+"&workPlace="+workPlace+"&codePID="+codePID+"&codeID="+codeID+"&local="+local+"&jobs="+jobs);
	window.location.href=url;
}
