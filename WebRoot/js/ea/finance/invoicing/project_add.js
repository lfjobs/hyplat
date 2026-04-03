
$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//

        //兼容性
		$(".industry").hide();
        $("#cltabl").hide();
        $("#recruiterTable").hide();
        

        
        if(view=="view"){
        	
        	$(".JQuerySubmitgd").attr("disabled","disabled");
        	$(".JQuerySubmitgd").addClass("grey");
        	$(document).attr("title","项目管理查看");//修改title值
        	if(pproID==""){
        		$("#pprojectName").val("无");
        	}
        
        	
        	if(status=="01"||status=="02"){
        	 	$(".updatesheet").attr("disabled",true);
            	$(".updatesheet").addClass("grey");
            	
            	$(".deletesheet").attr("disabled",true);
            	$(".deletesheet").addClass("grey");
        	}else{
        	 	$(".updatesheet").attr("disabled",false);
            	$(".updatesheet").removeClass("grey");
            	
            	$(".deletesheet").attr("disabled",false);
            	$(".deletesheet").removeClass("grey");
        	}
        	
        	
        }else if(view=="add"){
        	
        	$(".JQuerySubmitgd").attr("disabled",false);
        	$(".JQuerySubmitgd").removeClass("grey");
        	$(".add").attr("disabled","disabled");
        	$(".add").addClass("grey");
        	
        	$(".deletesheet").attr("disabled",true);
        	$(".deletesheet").addClass("grey");
        	
           	$(".updatesheet").attr("disabled",true);
        	$(".updatesheet").addClass("grey");
        	$(document).attr("title","项目管理添加");//修改title值
        	
        }else{

        	if(pproID==""){
        		$("#pprojectName").val("无");
        	}
        	$(".JQuerySubmitgd").attr("disabled",false);
        	$(".JQuerySubmitgd").removeClass("grey");
        	
        	$(".add").attr("disabled",false);
        	$(".add").removeClass("grey");
        	
        	$(".updatesheet").attr("disabled",true);
        	$(".updatesheet").addClass("grey");
        	$(".deletesheet").attr("disabled",false);
        	$(".deletesheet").removeClass("grey");
        	$(document).attr("title","项目管理修改");//修改title值
        	
        	
        }
        //增加
        $(".add").click(function(){
         document.location.href=basePath
					+ "/ea/promanage/ea_getAddPage.jspa?view=add";	
        });
        
        //修改
        $(".updatesheet").click(function(){
        	 document.location.href=basePath
			+ "/ea/promanage/ea_getAddPage.jspa?projectManage.proID="+proID+"&view=update";	
        });
        
        //删除
        $(".deletesheet").click(function(){
    		if (confirm("确定删除？")) {
				var url = basePath
						+ "ea/promanage/sajax_ea_deleteProject.jspa?date=" + new Date().toLocaleString();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					data:{
						"projectManage.proID":proID
						
					},
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var result = member.result;
						if(result=="success"){
							alert("操作成功！");
							$("tr#" + proID).remove();
						}else{
							alert("无权限删除");
						}
						
						
						
					},
					error : function cbf(data) {
						alert("数据获取失败3！");
					}
				});
			}
        });
    	
        
		// 项目分类和上级项目
		$(".projectbtn")
				.click(
						function() {
							
							
							$(".jqmWindow", $("#selectxmForm"))
									.jqmShow();
							
							//getProjectByxmtype("parameter=&xmtype=");//默认获取该部门下的所有项目
						});
	
	//修改状态时处理特殊项目分类
	
	  if(xmtype!=""){
		  //订单管理
		  if(xmtype=="0542"){
			  $("#recruiterTable").show();
			  bmDept(companyID, companyName);
		  }else{
			  $("#recruiterTable").hide();
		  }
			//汽车
			if(xmtype.substring(0,7)=="0240511"){
				$("#cltabl").show();
			}else{
				$("#cltabl").hide();
			}
			//产品设计推广
			if(xmtype=="0521"){
				$(".industry").show();
			}else{
				$(".industry").hide();
			}
		  
	  }
	
	
	/*********************************************规划*********************************/
	// 选择项目规划
	$("#planxz").click(function() {

		$(".jqmWindow", $("#selectplanForm")).jqmShow();
		cxplan("1=1");
	});

	
	// 根据输入的往来个人名称查询
	$("input#searchplan").click(function() {
		var title = $("input#pp", $("table#plantbl")).val();
		
		
		var module = $("select#module", $("table#plantbl")).val();
		cxplan("document.title=" +title + "&document.module="
				+ module+"&search=search");
	});
	
	// 上一页
	$("#plsy").click(function() {
		var sy = $("#plsy").attr("title");
		if (sy != 0) {
			
		var title = $("input#pp", $("table#plantbl")).val();
		var module = $("select#module", $("table#plantbl")).val();
		var search = $("#selectplanForm #search1").val();
		var typeCN = "document.title=" +title + "&document.module="
				+ module+"&search="+search
					+ "&pageForm.pageNumber=" + sy;
			cxplan(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	
	// 下一页
	$("#plxy").click(function() {
		var xy = $("#plxy").attr("title");
		if (xy != 0) {
			var title = $("input#pp", $("table#plantbl")).val();
		    var module = $("select#module", $("table#plantbl")).val();
		    var search = $("#selectplanForm #search1").val();
			var typeCN = "document.title=" +title + "&document.module="
				+ module+"&search="+search
					+ "&pageForm.pageNumber=" + xy;
			cxplan(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	
	$(".JQueryreturns").click(function() { 
		notoken = 0;
 		$(".jqmWindow").jqmHide();
 });
	
	 // 双击选中物品
	$("table#plantable tr[id]").live("click", function(event) {
		var b = $("input.plan", $(this)).attr("checked");
		$("input.plan", $(this)).attr("checked", !b);
	});
	
	
	//复选框选中
	$(".plan").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	
	
	// 添加所选中的项目规划
	$("#qdplan").click(function() {
	  var b=0;
	  var titles = "";
	  var docIds = "";
	  $("input:checkbox[name=checkplan]:checked").each(function(){
		titles += "《"+$("tr#"+$(this).val()).find("#title").text()+"》,";
		docIds+=$(this).val()+",";
	  	b=1;
	  	
	  });
	   if(b==0){
	   	alert("请选择规划");
	   	return;
	   }
	   $("#xmgh").val(titles.substring(0,titles.length-1));
	   
	   $("#ProjectForm #titles").val(titles.substring(0,titles.length-1));
	   $("#ProjectForm #docIds").val(docIds.substring(0,docIds.length-1));
	   $(".jqmWindow").jqmHide();
	});
	/***************************规划结束*********************************/
	//招生员报名点
	$("select#assignsID",$("table#recruiterTable")).bind('change',function(){
		$("input#assignsID",$("table#recruiterTable")).attr("value",$(this).children("option:selected").val());
		var num=$(this).children("option:selected").text().indexOf("├");
		$("input#assignsName",$("table#recruiterTable")).attr("value",$(this).children("option:selected").text().substr(num+1));
	});
	
    //整个添加页面 获得焦点带边框，失去焦点不带边框
	 $("#ProjectForm input.xiaoguo").blur(function(){
		 
		 if($(this).attr("class").indexOf("model1")==-1){
	    	   $(this).addClass("model1"); 
	       }   
	 }).focus(function(){
       if($(this).attr("class").indexOf("model1")!=-1){
    	   $(this).removeClass("model1"); 
       }    	
    });
	 
	 
	 $(".JQueryClose").click(function(){
		   token = 1;
		   re_load();
	 });
	 
		/**
		 * 
		 * 键入时查询项目分类
		 */
		$("#xmtypename").focus(function(){
			 getCate($(this),"xmtype");
		}).keyup(function(){
		
			 getCate($(this),"xmtype");

			
		});
	
		/**
		 * 
		 * 键入时查询行业
		 */
		$("#industry").focus(function(){
			 getCate($(this),"industry");
		}).keyup(function(){
		
			 getCate($(this),"industry");

			
		});
	 
	

	//键入查询	
	$(".querys").focus(function() {

		showtime($(this));

	}).keyup(function() {

		showtime($(this));
       
	});
	
	$(".closes").click(function(){
		$("#goodsQuery").hide();
		
	});
	
	//选中查询结果
	$("#goodsQuery tr[id]").live(
			"click",
			function(event) {
				var $trs = $("#goodsQuery tr#" + this.id);
				var staffName = $trs.find("#staffName").text();
				var staffCode = $trs.find("#staffCode").text();
				var email = $trs.find("#referenceOrganization").text();
				var address = $trs.find("#staffAddress").text();
				var identityCard = $trs.find("#staffIdentityCard").text();
				var phone = $trs.find("#reference").text();
				if ($("#types").val() == "staffname") {
					$("#staffid").val(this.id);
					$("#staffname").val(staffName);
					$("#staffcode").val(staffCode);
				}
				if ($("#types").val() == "recruiterName") {
					$("#recruiterID").val(this.id);
					$("#recruiterName").val(staffName);
					$("#recruiterCode").val(staffCode);
					$("#identityCard").val(identityCard);
					$("#email").val(email);
					$("#address").val(address);
					$("#phone").val(phone);
					bmDept(companyID, companyName);
				}

				if ($("#types").val() == "salesman") {
					$("#salesmanid").val(this.id);
					$("#salesman").val(staffName);
					$("#salesmancode").val(staffCode);
				}

				if ($("#types").val() == "salesman") {
					$("#salesmanid").val(this.id);
					$("#salesman").val(staffName);
					$("#salesmancode").val(staffCode);
				}

				if ($("#types").val() == "carCode"
						|| $("#types").val() == "carNum"
						|| $("#types").val() == "engineNum"
						|| $("#types").val() == "carFrameNum") {
					$("#carCode").val($trs.find("#carCode").text());
					$("#carNum").val($trs.find("#carNum").text());
					$("#engineNum").val($trs.find("#engineNum").text());
					$("#carFrameNum").val($trs.find("#carFrameNum").text());
					$("#carID").val($trs.find("#carID").text());
				}
				$("input.rauser", $(this)).attr("checked", "checked");
				$("#goodsQuery").hide();
			});
	
	
	// 上一页
	$("#wpsyq").click(function() {
		var sy = $("#wpsyq").attr("title");
		if (sy != 0) {
			var typeName = $("input#querys").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + sy;
			querysometing(typeCN,$("#types").val());
		} else {
			alert("已是首页！");
		}
	});
	
	// 下一页
	$("#wpxyq").click(function() {
		var xy = $("#wpxyq").attr("title");
		if (xy != 0) {
			var typeName = $("input#querys").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + xy;
			querysometing(typeCN, $("#types").val());
		} else {
			alert("已是尾页！");
		}
	});
	
	//项目保存

	$(".JQuerySubmitgd").click(
			function() {
				

				if($("#assignsID").val()!=null){
					$("#applyPlaceID").val($("#assignsID").val());
					$("#applyPlaceName").val($("#assignsID").find("option:selected").text());
				}
				$(".notnull", $("#ProjectForm")).trigger("blur");
				$(".put3",$("#ProjectForm")).trigger("blur");
		        if ($("form .error").length){
		          return;
		        }
		        
		        //产品设计推广
		    	if($("#xmtype").val()=="0521"){
		    		$("tr.industry").find(':input').each(function() {
		    			
						$(this).attr("name","pbyIndusty." + this.name);
					
						
					});
		    		
		    		
		    	}
		    	
		    	//汽车
		    	if($("#xmtype").val().substring(0,7)=="0240511"){
		               $("#cltabl").find(':input').each(function() {
		    			
						$(this).attr("name","projectMcar." + this.name);
						
					});
		    	}
		    	//订单管理
		    	if($("#xmtype").val()=="0542"){
		    		
                     $("#recruiterTable").find('input').each(function() {
		    			
						$(this).attr("name","recruiter." + this.name);
						
					});
		    	}
		     
		        
				
				$("#ProjectForm").attr("action",
						basePath + "ea/promanage/ea_saveProject.jspa");
				
				document.ProjectForm.submit.click();
				alert("操作成功");

			});
	
	
	/** **********************************项目分类和项目**************************************** */

	$(document)
			.ready(
					function() {

						// 键入时模糊查询项目分类
						$("#moc").click(function() {

							getCate($("#inputmoc").val());

						});
	                  

						// 项目分类
						$(".projectbtn")
								.click(
										function() {
											
											$(".jqmWindow", $("#selectxmForm"))
													.jqmShow();
											
											getProjectByxmtype("parameter=&xmtype=");//默认获取该部门下的所有项目
										});

						// 新增项目
						$(".xzxm")
								.click(
										function() {
											window
													.open(basePath
															+ "ea/promanage/ea_getProjectList.jspa?type=xm");
										});
						
						
						var cID = "";
						
						$("table#xmtable tr[id]").live(
								"click",
								function(event) {
									cID = this.id;
									
									$("input.ra", $(this)).attr("checked",
											"checked");
								});

						// 添加所选中的项目
						$("#qdxm").click(
								function() {
									
									if (cID == "") {
										if($("#selectxm").val()==""){
											alert("请选择项目分类");
											return;
										}
                                     if(confirm("您没有选择上级项目，将视为添加新项目，确定继续？")){
                                    	 $("#xmtypename").val($("#selectxms").val());
 										$("#xmtype").val($("#selectxm").val());
 	

 										$("#pprojectName").val("无");
 										$("#pproID").val("");

 										$(".jqmWindow", $("#selectxmForm"))
 												.jqmHide();
                                    	 
                                    	 return;
                                      }
									}
										$("#xmtypename").val($("tr#" + cID).find("#xmtypename")
												.text());
										$("#xmtype").val($("tr#" + cID).find("#xmtype")
												.text());
	

										$("#pprojectName").val(
												$("tr#" + cID).find("#projectName")
														.text());
										$("#pproID").val(
														$("tr#" + cID).find(
																"#proID")
																.text());
										$("#projectCode").val(
												$("tr#" + cID).find(
												"#projectCode")
												.text());
										$(".jqmWindow", $("#selectxmForm"))
												.jqmHide();
										 cID = "";
										return;
									
								});

						// 根据输入的项目名称查询
						$("input#searchxmbtn").click(
								function() {
									cuID = "";
									
									var parameter = $("input#parameterxm",$("table#searchxm")).val();
									var xmtype = $("input#selectxm").val();
									
									
									getProjectByxmtype("parameter="+parameter+"&xmtype="+xmtype);
								});

						// 上一页
						$("#xmsy").click(
								function() {
									var sy = $("#xmsy").attr("title");
									if (sy != 0) {
									
										
										var parameter = $("input#parameterxm",$("table#searchxm")).val();
										var xmtype = $("input#selectxm").val();
										
									
										getProjectByxmtype("parameter="+parameter+"&xmtype="+xmtype+ "&pageForm.pageNumber=" + sy);
									} else {
										alert("已是首页！");
									}
								});

						// 下一页
						$("#xmxy").click(
								function() {
									var xy = $("#xmxy").attr("title");
									if (xy != 0) {
										cuID = "";
										var parameter = $("input#parameterxm",$("table#searchxm")).val();
										var xmtype = $("input#selectxm").val();
										getProjectByxmtype("parameter="+parameter+"&xmtype="+xmtype+ "&pageForm.pageNumber=" + xy);
										
									} else {
										alert("已是尾页！");
									}
								});


					});

	/** **********************************项目分类和项目**************************************** */
	
	/** **********************************部门树和员工 **************************************** */

	$(document)
			.ready(
					function() {
	                    var cID="";
						$("table#dptable tr[id]").live(
								"click",
								function(event) {
									cID = this.id;
									
									$("input.rauser", $(this)).attr("checked",
											"checked");
								});


						// 选择部门
						$(".deptbtn")
								.click(
										function() {
											$(".jqmWindow", $("#selectdeptForm"))
													.jqmShow();
											
											
											$("#selectdeptname").val(treeNames);
											$("#selectdept").val(treeID);
										getStaffMember("parameter=&selectDept="+treeID);
										});


			

						// 添加所选中的人员
						$("#qddept").click(
								function() {
									if (cID != "") {
											$("#staffid").val($("tr#" + cID).find(
													"#staffid").text());
											$("#staffname").val($("tr#" + cID).find(
											"#staffname").text());
											$("#staffcode").val($("tr#" + cID).find(
											"#staffcode").text());
										
								
										$(".jqmWindow", $("#selectdeptForm"))
												.jqmHide();
										 cID = "";
									
										return;
									} else {
										alert("请选择员工！");
									}
								});

						// 根据输入的往来个人名称查询
						$("input#searchdeptbtn").click(
								function() {
									
									cID = "";
									
									var parameter = $("input#parameterrm",
											$("table#searchdept")).val();
									var selectDept = $(":input#selectdept").val();
									getStaffMember("parameter="+parameter+"&selectDept="+selectDept);
								});

						// 上一页
						$("#dpsy").click(
								function() {
									var sy = $("#dpsy").attr("title");
									if (sy != 0) {
									
										
										var parameter = $("input#parameterrm",
												$("table#searchdept")).val();
										var selectDept = $(":input#selectdept").val();
										getStaffMember("parameter="+parameter+"&selectDept="+selectDept+"&pageForm.pageNumber=" + sy);
									} else {
										alert("已是首页！");
									}
								});

						// 下一页
						$("#dpxy").click(
								function() {
									var xy = $("#dpxy").attr("title");
									if (xy != 0) {
										cuID = "";
										
										var parameter = $("input#parameterrm",
												$("table#searchdept")).val();
										var selectDept = $("input#selectdept").val();
										getStaffMember("parameter="+parameter+"&selectDept="+selectDept+ "&pageForm.pageNumber=" + xy);
									} else {
										alert("已是尾页！");
									}
								});
						
						
					


					});

	/** **********************************部门树和员工**************************************** */

});
//触发查询事件
function showtime(obj){
	var query = $.trim($(obj).val());
	var type = $(obj).attr("id");
	var top = $(obj).position().top;
	

 
	$("#goodsQuery").css({
   			position : "absolute",
   		    left :200,
   			top : top+30
   		}).show();  
	
	    querysometing("parameter="+query,type);
      $("#querys").val("parameter="+query);
      $("#types").val(type);
}

//物品键入实时查询
function querysometing(typeCN,type) {
	$("#wpsyq").attr("title", 0);
	$("#wpxyq").attr("title", 0);
	$("#wpzyq").attr("title", 0);
	var searchurl = "";
	
	if(type=="staffname"){
		searchurl = basePath
		+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";	
	}
	if(type=="recruiterName"||type=="salesman"){
		searchurl = basePath
		+ "ea/promanage/sajax_ea_getFormalStaffForCompany.jspa?";	
	}
	if(type=="carCode"||type=="carNum"||type=="engineNum"||type=="carFrameNum"){
		searchurl = basePath
		+ "ea/promanage/sajax_ea_getCarInfoByOrg.jspa?ajaxtype=ajax&";	
	}
	$.ajax({
		url : encodeURI(searchurl+typeCN+"&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var tabletr="";
			var thead = "";
			if(type=="recruiterName"||type=="staffname"||type=="salesman"){
			thead ="<tr><th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>" +
			            "<th align='center' bgcolor='#E4F1FA' width='30' >序号</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70' >人员编号</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70' >人员姓名</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='30' >性别</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='100' >出生日期</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='30' >籍贯</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='30' >民族</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70' >邮箱</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='230' >地址</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70' >手机号 </th>" +
						"<th align='center' bgcolor='#E4F1FA'  >身份证</th>" +
						"</tr>";
			}
			
			if(type=="carCode"||type=="carNum"||type=="engineNum"||type=="carFrameNum"){
				thead ="<tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>" +
				            "<th align='center' bgcolor='#E4F1FA'>序号</th>" +
							"<th align='center' bgcolor='#E4F1FA'>车辆自编号</th>" +
							"<th align='center' bgcolor='#E4F1FA'>车牌号</th>" +
							"<th align='center' bgcolor='#E4F1FA'>发动机号</th>" +
							"<th align='center' bgcolor='#E4F1FA'>车架号</th>" +
							"<th align='center' bgcolor='#E4F1FA'>品牌</th>" +
							"</tr>";
				}
	        $("#goodthead").html(thead);
			if(pageForm==null){
				$("#goodboy").html("");
				$("span#wpzycountq").text(0);
				return;
			}
			var dqy = pageForm.pageNumber;// 当前页
			var zys = pageForm.pageCount;// 总页数
			if (dqy > 1) {
				$("#wpsyq").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#wpxyq").attr("title", dqy + 1);
			}
			
			$("span#wpzycountq").text(zys);
			

			for(var i = 0;i<pageForm.list.length;i++){
				
				if(type=="recruiterName"||type=="staffname"||type=="salesman"){
		    tabletr += "<tr style='cursor: hand;' id = "
					+ pageForm.list[i].staffID + " title= "
					+ pageForm.list[i].staffID + ">";
			tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
					+ pageForm.list[i].staffID
					+ " name='checkradio'/></td>";
			tabletr += "<td id='mum' align='center'>"
				+(i+1) + "</td>";
			tabletr += "<td id='staffCode' align='center'>"
					+ pageForm.list[i].staffCode + "</td>";
			tabletr += "<td id='staffName' align='center'>"
					+ pageForm.list[i].staffName + "</td>";
			tabletr += "<td id='sex' align='center'>"
					+ pageForm.list[i].sex + "</td>";
			tabletr += "<td id='birthday' align='center'>"
					+ pageForm.list[i].birthday + "</td>";
			tabletr += "<td id='nativePlace'  align='center'>"
					+ pageForm.list[i].nativePlace + "</td>";
			tabletr += "<td id='nation'  align='center'>"
					+ pageForm.list[i].nation + "</td>";
			tabletr += "<td id='referenceOrganization'  align='center'>"
				+ pageForm.list[i].referenceOrganization + "</td>";
			tabletr += "<td id='staffAddress'  align='center'>"
				+ pageForm.list[i].staffAddress + "</td>";
			tabletr += "<td id='reference'  align='center'>"
				+ pageForm.list[i].reference + "</td>";
			tabletr += "<td id='staffIdentityCard'  align='center'>"
					+ pageForm.list[i].staffIdentityCard + "</td>";
			tabletr += " </tr>";
				
			}
				
				
				if(type=="carCode"||type=="carNum"||type=="engineNum"||type=="carFrameNum"){
				    tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].carID + " title= "
							+ pageForm.list[i].carID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
							+ pageForm.list[i].carID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='mum' align='center'>"
						+(i+1) + "</td>";
					tabletr += "<td id='carCode' align='center'>"
							+ pageForm.list[i].goodsCoding + "</td>";
					tabletr += "<td id='carNum' align='center'>"
							+ pageForm.list[i].carNum + "</td>";
					tabletr += "<td id='engineNum' align='center'>"
							+ pageForm.list[i].engineNum + "</td>";
					tabletr += "<td id='carFrameNum' align='center'>"
							+ pageForm.list[i].carFrameNum + "</td>";
					tabletr += "<td id='vehicleBrand' align='center'>"
						+ pageForm.list[i].vehicleBrand + "</td>";
					tabletr += "<td id='carID' style='display:none;' align='center'>"
						+ pageForm.list[i].carID + "</td>";
		
					tabletr += " </tr>";
						
					}
			}
			
		
			
			$("#goodboy").html(tabletr);

		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}


//列表
function re_load() {
	if(token){
		document.location.href=basePath+"ea/promanage/ea_getProjectList.jspa";
	}

}




/*************************招生员信息操作部分开始*****************************/
/**
 * 查询公司下所有子部门
 * @param id
 */
function bmDept(idStr,nameStr) {
	var url = basePath
			+ "ea/responsibilities/sajax_n_ea_getoList.jspa?companyID="+ idStr + "&date=" + new Date().toLocaleString();//+"&series=one"+"&level=organization";
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					/** **添加部门列表** */
					var member = eval("(" + data + ")");
					var oList = member.organizationlist;
					var data2 = new Array();
					data2[0] = {
						id : idStr,
						pid : '-1',
						text : nameStr
					};
					for (var i = 0; i < oList.length; i++) {
						data2[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					ts = new TreeSelector($("table#recruiterTable").find("select#assignsID")[0], data2, '-1');
					ts.createTree();
					$("table#recruiterTable").find("select#assignsID").show();
					if($("#applyPlaceID").val()!=""){
						$("#select#assignsID").val($("#applyPlaceID").val());
						$("#select#assignsID").show();
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

/*************************招生员信息操作部分结束*****************************/

/*************************项目规划*****************************/


//ajax查询往来个人列表
function cxplan(typeCN) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	notoken = 1;
	$("#plsy").attr("title", 0);
	$("#plxy").attr("title", 0);
	$("#plzy").attr("title", 0);
	var searchurl = basePath
			+ "/ea/promanage/sajax_ea_getxmPlanList.jspa?";
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
	
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}

			var pageForm = member.pageForm;
			var search = member.search;
			$("#selectplanForm #search1").val(search);
			
			if (pageForm == null) {
				$("#body_02plan").html("");
				alert("没有数据");
				notoken = 0;
				return;
			}
		
			
			var dqy = pageForm.pageNumber;// 当前页
			var zys = pageForm.pageCount;// 总页数
			if (dqy > 1) {
				$("#plsy").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#plxy").attr("title", dqy + 1);
			}
			$("span#plzycount").text(zys);
			var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择项目规划</td></tr></table>";
			tabletr += "<table width='99%' align='center' id='plantable' cellpadding='0'  cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>规划名称</th><th align='center' bgcolor='#E4F1FA'>规划编号</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>起草人姓名</th><th align='center' bgcolor='#E4F1FA'>签发状态</th><th align='center' bgcolor='#E4F1FA'>规划范围</th></tr>";
			for (var i = 0; i < pageForm.list.length; i++) {
				
				var status = pageForm.list[i].status;

				if(status=="I"){
					status = "草稿";
					
				}
				if(status=="R"){
					status = "返回修改";
					
				}
				if(status=="S"||status=="T"){
					status = "审批中";
					
				}
				if(status=="A"){
					status = "盖章中";
					
				}
				if(status=="U"){
					status = "不批准";
					
				}
				if(status=="F"){
					status = "盖章人存档";
					
				}
				if(status=="P"){
					status = "待群发";
					
				}
				if(status=="T"){
					status = "已群发";
					
				}
				if(status=="G"){
					status = "已归档";
					
				}
				if(status=="Z"){
					status = "传至信息平台";
					
				}
				if(status=="I"){
					status = "传阅中";
					
				}
				
				var module = pageForm.list[i].module;

				if(module=="cg"){
					module = "公司规划";
					
				}
				if(module=="dg"){
					module = "部门规划";
					
				}
				if(module=="pg"){
					module = "个人规划";
					
				}
				if(module=="jg"){
					module = "职业规划";
					
				}
				
				
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].docId + " title= "
						+ pageForm.list[i].title + ">";
				tabletr += "<td id='checks' align='center'><input type ='checkbox'  class='plan' value="
						+ pageForm.list[i].docId
						+ " name='checkplan'/></td>";
				tabletr += "<td id='title' align='center'>"
						+ pageForm.list[i].title + "</td>";
				tabletr += "<td id='docNum' align='center'>"
						+ pageForm.list[i].docNum + "</td>";
				tabletr += "<td id='drafterName' align='center'>"
						+ pageForm.list[i].drafterName + "</td>";
				tabletr += "<td id='status' align='center'>"
						+ status + "</td>";
		        tabletr += "<td id='module' align='center'>"
						+ module + "</td>";

				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02plan").html(tabletr);
			$("#body_02plan").show();
			// alert("数据加载成功")
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}
/*************************项目规划结束*****************************/

//获取员工
function getStaffMember(typeCN) {


	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	
	
	$
			.ajax({
				url : encodeURI(searchurl + typeCN
						+ "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var tabletr = "";

					if (pageForm == null) {
						$("#body_02dept").html("");
						$("span#dpzycount").text(0);
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dpxy").attr("title", dqy + 1);
					}

					$("span#dpzycount").text(zys);

					for ( var i = 0; i < pageForm.list.length; i++) {

						
							tabletr += "<tr style='cursor: hand;' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
									+ pageForm.list[i].staffID
									+ " name='checkradio'/></td>";
							tabletr += "<td id='mum' align='center'>"
									+ (i + 1) + "</td>";
							tabletr += "<td id='staffcode' align='center'>"
									+ pageForm.list[i].staffCode
									+ "</td>";
							tabletr += "<td id='staffname' align='center'>"
									+ pageForm.list[i].staffName
									+ "</td>";
							tabletr += "<td id='sex' align='center'>"
									+ pageForm.list[i].sex
									+ "</td>";
							tabletr += "<td id='birthday' align='center'>"
									+ pageForm.list[i].birthday
									+ "</td>";
							tabletr += "<td id='nativePlace'  align='center'>"
									+ pageForm.list[i].nativePlace
									+ "</td>";
						
						
							tabletr += "<td id='reference'  align='center'>"
									+ pageForm.list[i].reference
									+ "</td>";
							tabletr += "<td id='staffIdentityCard' align='center'>"
									+ pageForm.list[i].staffIdentityCard
									+ "</td>";
							tabletr += "<td id='staffid' align='center' style='display:none;'>" 
								+ pageForm.list[i].staffID
								+ "</td>";
							tabletr += " </tr>";

					}
					
					
					$("#body_02dept").html(tabletr);


				},
				error : function cbf(data) {
					notoken = 0;

				}
			});
}

/** ***********************项目分类模糊查询**************************** */
//键入时查询项目分类
function getCate(value) {


	var url = basePath + "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
			+ new Date().toLocaleString();

	$
			.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					parameter : $.trim(value)
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var xmList = member.xmlist;
					var parameter = $("input#parameterxm",$("table#searchxm")).val();
					var params = "parameter="+parameter+"&xmtype=";

					var str = "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' class='al' onclick='clicklist(\""+params+"\",\"\",\"\",this);'>所有项目分类</a></span><br/>";

					for ( var i = 0; i < xmList.length; i++) {
						params = "parameter="+parameter+"&xmtype="+xmList[i].codeSn;

							str += "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' class='al' onclick='clicklist(\""+params+"\",\""+xmList[i].codeSn+"\",\""+xmList[i].codeValue+"\",this);'>("
									+ xmList[i].codeSn
									+ ")"
									+ xmList[i].codeValue + "</a></span><br/>";
						
					}
					if (str == "") {
						str = "&nbsp;无搜索结果";
					}
					$(".mohuc").html("");
					$(".mohuc").html(str).show();
					$("#treeg").hide();

				
					

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}

function clicklist(params,xmtype,xmtypename,obj){
	$(".mohuc").find("a.al").css({"background-color":"","color":"#333"});
    $(obj).css({"background-color":"navy","color":"white"});
	$("#selectxm").val(xmtype);
	$("#selectxms").val(xmtypename);
	getProjectByxmtype(prams);
}

//根据项目分类获取项目
function getProjectByxmtype(prams) {
	$("#xmsy").attr("title", 0);
	$("#xmxy").attr("title", 0);
	$("#xmzy").attr("title", 0);
	var url = basePath
			+ "ea/costsheet/sajax_ea_getProjectList.jspa?"+prams;

	$
			.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {

					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var pageForm = member.pageForm;
					if (pageForm == null) {
						$("#body_02xm").html("");
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#cdpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#xmxy").attr("title", dqy + 1);
					}
					//
					$("span#xmzycount").text(zys);
					var tabletr="";
      
					for ( var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' title="
								+ pageForm.list[i].proID + " id = "
								+ pageForm.list[i].proID + ">";
						tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
								+ pageForm.list[i].proID
								+ " name='checkradio'/></td>";
						tabletr += "<td align='center'>" + (i + 1) + "</td>";
						tabletr += "<td id='projectCode' align='center'>"
								+ pageForm.list[i].projectCode + "</td>";
						tabletr += "<td id='projectName' align='center'>"
								+ pageForm.list[i].projectName + "</td>";
						tabletr += "<td id='xmtypename' align='center'>"
								+ pageForm.list[i].xmtypename + "</td>";
						tabletr += "<td id='xmtype' style='display:none;'>"
								+ pageForm.list[i].xmtype + "</td>";

						tabletr += "<td  align='center'>"
								+ pageForm.list[i].staffName + "</td>";
						tabletr += "<td  align='center'>"
								+ pageForm.list[i].createName + "</td>";
						tabletr += "<td id='content'  align='center' style='display:none;'>"
								+ pageForm.list[i].content + "</td>";
						tabletr += "<td id='proID'  align='center' style='display:none;'>"
								+ pageForm.list[i].proID + "</td>";
						tabletr += " </tr>";
					}
	
					
					$("#body_02xm").html(tabletr);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}
/** ***********************项目分类结束**************************** */
