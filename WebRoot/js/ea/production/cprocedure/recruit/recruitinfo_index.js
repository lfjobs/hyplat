$(function(){

    $(".main").scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        var Top = $(".last").offset().top; //元素距离顶部距离

        console.log(Top + '  ' + scroll);
        if (Top - Height - scroll <= 200) {
            if (pagenumber < pagecount) {
                loaded();
            }


        }

    })



	$(".main").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});
	$(".zhaopin_kuang").css("width",$(window).width()*0.8+"px");
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"});
	
	
	// //
	if(lei=="gs"){
		$(".person").addClass("dN");
		$(".com").removeClass("dN");
		$(".zhao_nav").find("li").eq(0).addClass("current");
		$(".zhao_nav").find("li").eq(1).removeClass("current");
		$(".sec-02").show();
        $(".sec-03").hide();
        if(current=="jl"){
            $(".sec-02 li").eq(0).removeClass("active")
            $(".sec-02 li").eq(1).addClass("active");
            $(".div-02 .sec-07").show();
            $(".zhao_main").hide();
		}

	}else{
		$(".person").removeClass("dN");
		$(".com").addClass("dN");
		$(".zhao_nav").find("li").eq(1).addClass("current");
		$(".zhao_nav").find("li").eq(0).removeClass("current");
        $(".sec-03").show();
        $(".sec-02").hide();
        if(current=="zw"){
            $(".sec-03 li").eq(0).removeClass("active")
            $(".sec-03 li").eq(1).addClass("active");
            $(".div-03 .sec-05").show();
            $(".zhao_main").hide();
        }
	}

	
	//取消
	$(".tipcan").click(function(){
		$(".tiptan").hide();
		token1 = 0;
	});
	
	
	//确认
	$(".tipconfirm").click(function(){
		if($(this).text()=="立即升级会员"){
			document.location.href = basePath+"/ea/wfjshop/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&zlyq=1"
		}else if($(this).text()=="查看金币池"){
			document.location.href = basePath+"/ea/jinbi/ea_gethyjifen.jspa?user="+users+"&sccid="+sccid+"&khd=1";
		}else if($(this).text()=="去完善简历"){
			document.location.href = basePath+"/page/ea/main/production/resume/resumeManagement/candidates.jsp?staffid="+staffid+"&type=";
		}else{
            $(".tiptan").hide();
        }
		token = 0;
	});
	

	//投简历
	$(document).on("click",'.tou',function(){
		if($(this).text()=="已投"){
	    	 return false;
	     }
		
		if(token1==1){
			return false;
		}
		token1 = 1;
		
		var riIds = $(this).parents("li").find(".riId").text();
		
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
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					if(result=="nojianli"){
						$(".tipcontent").text("公司账号不能投递简历");
						$(".tipconfirm").text("确定");
						$(".tan_mo").hide();
						$(".tiptan").show();
					}
					else if(result=="noresume"){
						$(".tipcontent").text("投递失败，请完善您的简历");
						$(".tipconfirm").text("去完善简历");
						$(".tan_mo").hide();
						$(".tiptan").show();
						
					}else if(result=="success")
					{
						//成功后跳转
						$(".tan_kuang").css("background","rgba(0,0,0,0.4)");
						$(".tan1").show();
						$(".tan_mo").hide();
						$("li#"+riIds).find(".tou").text("已投");
						$("li#"+riIds).find(".tou").removeClass("tou").addClass("yitou");
						
						
					}
					token1 = 0;
					
					
				},error:function(data){
					alert("投递失败");
				}
			   
		       });
		
	});
	
	
	
	
	 //抢人才
	$(document).on("click",'.qiang',function(){
	     if($(this).text()=="已抢"){
	    	 return;
	     }
		 resID = $(this).parents("li").attr("id");
		 $(".tan_mo").show();

		
	});
	
	
	//取消
	$(".no").click(function(){
		$(".tan_mo").hide();
	});
	
	//确定
	$(".confrim").click(function(){
		$(".tiptan").hide();
		token = 0;
	});
	
	//抢人才
	$(".yue").click(function(){
         if(token == 1){
        	 return;
         }
         token = 1;
		   var url = basePath+"ea/bidrecruit/sajax_ea_grabResume.jspa";
		   $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					resumeIDs:resID
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					var login = member.login;
					users = member.user;
					sccid=member.sccid;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					if(result!="success"){
	                     if(result=="nocom"){
							
							$(".tipcontent").text("公司会员才能进行此操作");
							$(".tipconfirm").text("立即升级会员");
							
						}else if(result=="nomoney"){
							$(".tipcontent").text("金币池余额不足,请到会员中心金币池充值");
							$(".tipconfirm").text("查看金币池");
							
						}
						
						$(".tan_mo").hide();
						$(".tiptan").show();
						
					
						token = 0;
						return false;
						
					}
					
					
					
					//成功后跳转
					$(".tan_kuang").css("background","rgba(0,0,0,0.4)");
					$(".tan2").show();
					$(".tan_mo").hide();
					$("li#"+resID).find(".qiang").text("已抢");
					$("li#"+resID).find(".qiang").removeClass("qiang").addClass("yiqiang");
					token = 0;
					
				},error:function(data){
					alert("抢人才失败");
				}
			   
		       });
		
	});
	
    //继续
	$(".again").click(function(){
		$(".tan_kuang").css("background","");
		$(".tan_kuang").hide();
	});
	
	
	
	//导航颜色切换
	$(".zhao_nav").find(".nav_zhao").click(function(){
		$(".zhao_nav").find(".nav_zhao").find("p").css("color","#333");
		$(this).find("p").css("color","#ff6600");
	});
	
		
	
	//tab切换
	  var tabs = function(tab, con){
		    tab.click(function() {
			var indx = tab.index(this);
			tab.removeClass('current');
			$(this).addClass('current');
			con.hide();
			con.eq(indx).show();
			var select = $(".current").find("p").text();
			if (select == "找工作") {
				document.location.href = basePath
						+ "ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
			} else {
               //个人求职
				document.location.href = basePath
				+ "ea/bidrecruit/ea_getResumeIndex.jspa?lei=gr";
			}
			pagenumber = 1;
		});
	};
 	tabs($(".zhao_nav li"), $('.zhao_main ul'));

    //tab切换2
    function my(m){
        $(m).parent().find("li").removeClass("active");
        $(m).addClass("active");
    }
    $(document).on("click",".center-block li",function(){
        my($(this));
        $(".div-01 section").hide();
        $(".div-01 section").eq($(this).index()).show();
        $(".div-num>div").hide();
        $(".div-num>div").eq($(this).index()).show();
        if($(this).index()==1){
            $(".sec-06").show();
        }
    })

	//招聘信息 和简历管理
    $(document).on("click",".sec-02 li",function(){
        my($(this));
        if($(this).index()==0){
            $(".div-02 .sec-07").hide();
            $(".zhao_main").show();
        }
        if($(this).index()==1){

            if(state==""){
                document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";

            }else if(state=="1"){

                $(".div-02 .sec-07").show();
                $(".zhao_main").hide();
            }else{
                $(".tipcontent").text("公司账号不能管理简历");
                $(".tipconfirm").text("确定");
                $(".tan_mo").hide();
                $(".tiptan").show();

			}




        }
    })
    $(document).on("click",".sec-03 li",function(){
        my($(this));
        if($(this).index()==0){
            $(".div-03 .sec-05").hide();
            $(".zhao_main").show();
        }
        if($(this).index()==1){


             if(state==""){
                 document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";

			 }else if(state=="2"){
                 $(".div-03 .sec-05").show();
                 $(".zhao_main").hide();
			 }else{
                 $(".tipcontent").text("个人账号不能发布职位");
                 $(".tipconfirm").text("确定");
                 $(".tan_mo").hide();
                 $(".tiptan").show();
			 }





        }
    })




 	//行业选择
 	$(".kexuan .xuan_lis").click(function(){
 			var _this = $(this).text();
 		var _xuan = '<div class="xuan_lis"><span>'+_this+'</span><img class="cha" src="../images/cha_03.png"/></div>';
 	
 		
 		$(".yixuan").find(".xuan_hangye").append(_xuan);
 	})
 	//删除所选
 	$(document).on('click','.cha',function(){
 		
 		$(this).parent().remove();
 	})
 	//职位
 	$(".big_zhiwei").click(function(){
 		$(this).parent().find(".qiehuan").toggleClass("dN");
 		$(this).parent().next(".zhiwei_nop").toggle();
 	})
 	$(".quan_xuan_d").click(function(){
 		$(this).find(".quan_xuan").toggleClass("dN")
 	})
 	$(".img_wai").click(function(){
 		$(this).find(".quan_xuan").toggleClass("dN")
 	})
 	$(".xuanz").click(function(){
 		var lei_main = $(this).parent().next().find(".zhiwei_fenlei").text();
 		var tianjia = '<div class="xuan_lis"><span>'+lei_main+'</span><img class="cha" src="../images/cha_03.png"/></div>';
		$(".xuan_hangye").append(tianjia)
 	})
 	//简历按钮
 	$(".jianli_footer").find(".qiangren").click(function(){
 		$(".jianli_tan_mo").show();
 	})
 	$(".jianli_tan_mo").find(".no").click(function(){
 		$(".jianli_tan_mo").hide();
 	})
 	//回退
 	// $(".arrar").click(function(){
 	// 	document.location.href = basePath+"ea/purchasebids/ea_findbidIndexList.jspa";
 	// });
 	

	
	$(".header ul.dropdown-menu li").click(function(){
		$(".dropdown-toggle").find(".toggle_text").text("");
		alert($(this).find("a").text());
		$(".dropdown-toggle").find(".toggle_text").append($(this).find("a").text());
		if($(this).text()=="找工作"){
			window.open(basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa","_self");
		}else if($(this).text()=="找人才"){
			window.open(basePath+"ea/bidrecruit/ea_getGrsearchIndex.jspa","_self");
		}
	});
	$(".tan_kuang").click(function(){
		$(this).hide();
	});
	$(".big_search").click(function(){
		window.open("../html/sousuojieguo.html","_self");
	});
	
	

	//点击职位展示职位详情
	$(document).on("click",".gs",function(){
		var riId = $(this).parents("li").find(".riId").text();
		var position = $(this).parents("li").find(".position").text();
		window.open(basePath+"ea/bidrecruit/ea_showPosdetail.jspa?riId="+riId+"&position="+position+"&back=1","_self");
	});
	
	//点击求职信息展示简历详情
	$(document).on("click",".gr",function(){
		var resumeID = $(this).parents("li").find(".resumeID").text();
		var position = $(this).parents("li").find(".position").text();
		window.open(basePath+"ea/bidrecruit/ea_showResumedetail.jspa?resumeID="+resumeID+"&position="+position+"&type=抢人才&back=1","_self");
	});
	//点击搜索框，跳转到搜索界面，默认为找工作搜索
	$(".header").find(".header_c").click(function(){
			
		window.open(basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa","_self");
		
	});
	
//	getHeight();


    //简历管理
    $("#manger").click(function() {
        document.location.href = basePath + "ea/resumes/ea_resumeManagement.jspa?staffid="+staffid+"&sccId="+sccId+"&jitype=demand&back=1";

    });
    //投递记录
    $("#record").click(function() {
        document.location.href = basePath + "ea/resumes/ea_getRecord.jspa?staffid="+staffid+"&type=00&back=1";

    });
    //邀请的
    $("#Invitation").click(function() {

        document.location.href = basePath + "ea/resumes/ea_getRecord.jspa?staffid="+staffid+"&type=01&back=1";
    });
    //职位关注
    $("#postion").click(function() {
        document.location.href = basePath + "ea/resumes/ea_getPostion.jspa?staffid="+staffid+"&sccId="+sccId+"&jitype=demand&back=1";

    });
    //我的考试
    $("#myexam").click(function() {
        document.location.href =  basePath + "ea/quest/ea_myExam.jspa";


    });





    //发布招聘
    $("#publish").click(function() {
        document.location.href = basePath + "ea/bidrecruit/ea_getPositionPub.jspa?sccId="+sccId+"&back=1";

    });
    //职位管理
    $("#positionMange").click(function() {
        document.location.href = basePath + "ea/bidrecruit/ea_findPositionList.jspa?sccId="+sccId+"&back=1";
    });
    //简历管理
    $("#resume").click(function() {
        document.location.href = basePath +  "ea/bidrecruit/ea_getTalentResumeList.jspa?sccId="+sccId+"&back=1";

    });
    //简历收藏
    $("#collect").click(function() {
        document.location.href = basePath +  "ea/bidrecruit/ea_getCollectResume.jspa?sccId="+sccId+"&back=1";

    });


});



function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").length>0) {
		if ($(".last").offset().top - $(".last").height() * 2 <= $(window)
				.height()) {
			if (pagenumber < pagecount) {
				loaded();
			}
		}
	}
}
    

function loaded() {
	pagenumber += 1;
	var url = "";
	if(lei=="gs"){
	   url = basePath + "ea/bidrecruit/sajax_ea_getRecruitIndex.jspa";
	}else{
	   url = basePath + "ea/bidrecruit/sajax_ea_getResumeIndex.jspa";
	}
	$.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : pagenumber,
					 type : "ajax",
					 lei:lei
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var html = "";
					var obj;

					if (pagenumber == 1) {
						$(".zhao_main").find("ul").html("");
					}
					$(".last").removeClass("last");

					if (pageForm != null) {

						pagecount = pageForm.pageCount;
						count = pageForm.recordCount;
						pageSize = pageForm.pageSize;
						var obj;
						var array = new Array();
						if(lei=="gs"){
						for ( var j = 0; j < pageForm.list.length; j++) {
							obj = pageForm.list[j];
							if (j == pageForm.list.length - 1) {
								array.push("<li class='last' id='"+obj[5]+"'>");
							} else {
								array.push("<li id='"+obj[5]+"'>");
							}
							array.push("<span style='display:none;' class='riId'>"+obj[5]+"</span>");
							array.push("<span style='display:none;' class='position'>"+obj[1]+"</span>");
							array.push("<div class='zhao_main_lis_left pull-left text-center img-responsive gs'>");
							array.push("<div class='img_wai'>");
							if(obj[0]==""){
								array.push("<img src='" + basePath
										+ "images/ea/recruit/gongsi_10.png'/>");
							}else{
								array.push("<img src='" + basePath+obj[0]+"'/>");
							}
							
							array.push("</div>");
							array.push("</div>");
							array.push("<div class='zhao_main_lis_center pull-left gs'>");
							array.push("<h4>" + obj[1] + "</h4>");
							array.push("<p class='comname'>" + obj[2] + "</p>");
							
							array.push("<span class='yaoqiu'>");
							array.push("<img class='little_img' src='"
											+ basePath
											+ "images/ea/recruit/ico_13.png' alt='' />");
							array.push(obj[3]);
							array.push("</span>");
							array.push("<span class='yaoqiu'>");
							array.push("<img class='little_img' src='"
									+ basePath
									+ "images/ea/recruit/ico_15.png'/>");
							array.push(obj[4]);
							array.push("</span>");
							array.push("</div>");
							array.push("</div>");
							array.push("<div class='pull-left zhao_main_lis_right'>");
							array.push("<div class='text_wai'>");
							
							if(obj[6]=="0"){
							  array.push("<div class='tou'>投</div>");
							}else{
							  array.push("<div class='yitou'>已投</div>");
							}
							array.push("</div>");
							array.push("</div>");
							array.push("</li>");
							

						}
						$(".zhao_main_1").append(array.join(""));
						}else{
							
							
						
						for ( var k = 0; k < pageForm.list.length; k++) {
							obj = pageForm.list[k];
							if (k == pageForm.list.length - 1) {
								array.push("<li class='last' id='"+obj[0]+"'>");
							} else {
								array.push("<li id='"+obj[0]+"'>");
							}
							array.push("<div class='zhao_main_lis_left pull-left text-center img-responsive gr'>");
							array.push("<div class='img_wai'>");
							if(obj[5]==""||obj[5]==null){
							array.push("<img src='" + basePath
									+ "images/ea/production/head2x.png'/>");
							}else{
							array.push("<img src='" + basePath
										+ obj[5]+"'/>");	
							}
							array.push("</div>");
							array.push("</div>");
							array.push("<div class='zhao_main_lis_center pull-left gr'>");
							array.push("<h4>" + obj[1] + "</h4>");
							array.push("<p class='comname'>" + obj[2] + "</p>");
							array.push("<div>");
							array.push("<span class='yaoqiu'>");
							array.push("<img class='little_img' src='"
											+ basePath
											+ "images/ea/recruit/ico_13.png' alt='' />");
							array.push(obj[3]);
							array.push("</span>");
							array.push("<span class='yaoqiu'>");
							array.push("<img class='little_img' src='"
									+ basePath
									+ "images/ea/recruit/ico_15.png'/>"+obj[4]);
							array.push("</span>");
							array.push("</div>");
							array.push("</div>");
							array.push("<div class='pull-left zhao_main_lis_right'>");
							array.push("<div class='text_wai'>");
							if(obj[7]=="0"){
							array.push("<div class='qiang'>抢</div>");
								
							}else{
							array.push("<div class='yiqiang'>已抢</div>");
							}
							
							array.push("</div>");
							array.push("</div>");
							array.push("</li>");
							

						}
						$(".person").append(array.join(""));
						}
						
					}

				},
				error : function(data) {
				//	alert("获取失败");
				}
			});
}

