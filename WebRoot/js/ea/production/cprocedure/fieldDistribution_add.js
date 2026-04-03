$(function(){
	$("#satffName").click(function(){
		$("#deptjqModel").jqmShow();
		getStaffMember("parameter=&selectDept="+orgId);
	});
	
	$(".JQueryreturns").click(function(){
		if($(this).val()=="查询"){
			
		}else if($(this).val()=="确定"){
			$(".staffName").val($("#"+staffId).find("#staffname").text());
			$(".staffId").val(staffId);
			$(".staffName").attr("style","").removeClass("error");
			$("#deptjqModel").jqmHide();
		}else{
			$("#deptjqModel").jqmHide();
		}
	});
	
	
	//选择项目名称
	$("#projectName").click(function(){
 		$.ajax({
			url:basePath+"ea/fielddistr/sajax_ea_ajaxNotAllocatedSite.jspa?category="+category+"&grType="+grType,
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var list=member.list;
				$(".product").remove();
				for(var i=0;i<list.length;i++){
					var tr=$("#sampleTr").clone(true).attr("style","height:20px;").attr("id",list[i][0]).addClass("product");
					tr.find("td").eq(0).html("<span style='width:20px;height:20px;'><input type='radio' name='ppradio' class='radio'></span>");
					tr.find("td").eq(1).html("<span style='width:100px;height:20px;'>"+(list[i][2]==null?"":list[i][2])+"</span>");
					tr.find("td").eq(2).html("<span style='width:160px;height:20px;'>"+(list[i][1]==null?"":list[i][1])+"</span>");
					tr.find("td").eq(3).html("<span style='width:50px;height:20px;'>"+(list[i][3]==null?"":list[i][3])+"</span>");
					tr.find("td").eq(4).html("<span style='width:50px;height:20px;'>"+(list[i][4]==null?"":list[i][4])+"</span>");
					tr.find("td").eq(5).html("<span style='width:100px;height:20px;'>"+(list[i][5]==null?"":list[i][5])+"</span>");
					tr.find("td").eq(6).html("<span style='width:100px;height:20px;'>"+(list[i][6]==null?"":list[i][6])+"</span>");
					tr.find("td").eq(7).html("<span style='width:70px;height:20px;'>"+(list[i][7]==null?"":list[i][7])+"</span>");
					tr.find("td").eq(8).html("<span style='width:120px;height:20px;'>"+(list[i][8]==null?"":list[i][8])+"</span>");
					tr.find("td").eq(9).html("<span style='width:50px;height:20px;'>"+(list[i][9]==null?"":list[i][9])+"</span>");
					tr.find("td").eq(10).html("<span style='width:40px;height:20px;'>"+(list[i][10]==null?"":list[i][10])+"</span>");
					$("#tbodya").append(tr);
				}
				$("#goodsjqModel").jqmShow();
			},
			error:function(){
				alert("获取数据失败！");
			}
		});
	});
	
	//归档
	$("#button").click(function(){
		if($(".projectId").val()=="")
			$(".projectName").attr("style","background-color: red").addClass("error");
		if($("#edate").val()=="")
			$("#edate").attr("style","background-color: red").addClass("error");
		if($("#sdate").val()=="")
			$("#sdate").attr("style","background-color: red").addClass("error");
		if($("#site").val()=="")
			$("#site").attr("style","background-color: red").addClass("error");
		if($(".staffId").val()==""){
			$(".staffName").attr("style","background-color: red").addClass("error");}
		if(!$(".error").attr("id")){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/fielddistr/ea_addFieldDistribution.jspa?type=add");
			document.form.submit.click();
			token = 2;

		}
	});
	$(".succ").live("input propertychange change blur",function(){
		if($(this).val()=="")
			$(this).attr("style","background-color: red").addClass("error");
		else
			$(this).attr("style","").removeClass("error");
	});
	//项目按钮点击
	$(".goodsButton").click(function(){
		if($(this).val()=="查询"){
			
		}else if($(this).val()=="确定"){
			$(".projectName").val($("#"+productId).find("span").eq(6).text());
			$(".projectId").val(productId);
			$(".productCode").val($("#"+productId).find("span").eq(2).text());
			$(".projectName").attr("style","").removeClass("error");
			$("#goodsjqModel").jqmHide();
		}else{
			$("#goodsjqModel").jqmHide();
		}
	});
	
	//行点击事件
	$("tr[id]").live("click",function(){
		$(this).find(".radio").attr("checked","checked");
		if($(this).attr("class")=="staff")
			staffId=$(this).attr("id");
		if($(this).attr("class")=="product")
			productId=$(this).attr("id");
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
});









/** **********************************往来个人**************************************** */
// 选择往来个人
$(document)
		.ready(
				function() {
					treegr = new dhtmlXTreeObject("grTree", "100%", "100%", 0);
					treegr.enableDragAndDrop(false);
					treegr.enableHighlighting(1);
					treegr.enableCheckBoxes(0);
					treegr.enableThreeStateCheckboxes(false);
					treegr.setSkin(basePath + 'js/tree/dhx_skyblue');
					treegr.setImagePath(basePath + "js/tree/codebase/imgs/");
					treegr.loadXML(basePath + "js/tree/common/tree_b.xml");
					var getcodeurl = basePath
							+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110106hfjes5ucxp0000000017&date="
							+ new Date().toLocaleString();
					treegr.insertNewChild(0,
							"scode20110106hfjes5ucxp0000000017", "往来个人类别", 0,
							0, 0, 0);
					$.ajax({
						url : encodeURI(getcodeurl),
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
							var codeList = member.codeList;
							if (null == codeList) {
								return;
							}
							for ( var i = 0; i < codeList.length; i++) {

								treegr.insertNewChild(
										"scode20110106hfjes5ucxp0000000017",
										codeList[i].codeID,
										codeList[i].codeValue, 0, 0, 0, 0);
								treegr.setUserData(codeList[i].codeID,
										"codeNumber", codeList[i].codeNumber);

							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
					treegr.setOnClickHandler(function() {

						treeid = treegr.getSelectedItemId();
						treename = treegr.getItemText(treeid);

						var typeName = $("input#contactUserID",
								$("table#searchuser")).val();

						if (treeid == "scode20110106hfjes5ucxp0000000017") {
							treename = "";
						}
						$(":input#grparms").val(treename);
						
						var typeCN = "contactUser.staffName=" + typeName
						+ "&contactUser.relation=" + treename;
						
						if($("#xmtype").val()=="043111"){
							typeCN+="&dataIsComplete=00&identifier=baokaixue";
						}
					    
						cxwlgr(typeCN);
						return;

					});

					var cuID = "";
					var userstaffID = "";
					$("table#gouserstable tr[id]").live(
							"click",
							function(event) {
								cuID = this.id;
								userstaffID = this.title;
								$("input.rauser", $(this)).attr("checked",
										"checked");
							});

							// 选择往来个人
					$(".wlgr")
							.live("click",
									function() {
										
										$(".jqmWindow", $("#selectuserForm"))
												.jqmShow();
										cxwlgr("contactUser.staffName=&contactUser.relation=");
									});

					// 新增往来个人
					$(".xzgr")
							.click(
									function() {
										window
												.open(basePath
														+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
									});

					// 添加所选中的往来个人
					$("#qduser").click(
							function() {
								if (cuID != "") {

									var $tbl = $("#"+$("#linet").val());
									var clicktr = $("#line",$tbl).text();
									$("tr#" + clicktr,$tbl).find("#connectName")
											.val(
													$("tr#" + cuID).find(
															"#contactUserName")
															.text());
									$("tr#" + clicktr,$tbl).find("#connectID").val(
											cuID);
									$(".jqmWindow", $("#selectuserForm"))
											.jqmHide();
									$(".wlgr").parent().removeClass("rwl");
									return;
								} else {
									alert("请选择往来个人！");
								}
							});

					// 根据输入的往来个人名称查询
					$("input#searchuu").click(
							function() {
								cuID = "";
								userstaffID = "";
								var typeName = $("input#contactUserID",
										$("table#searchuser")).val();
								var relation = $(":input#grparms").val();
								cxwlgr("contactUser.staffName=" + typeName
										+ "&contactUser.relation=" + relation);
							});

					// 上一页
					$("#grsy").click(
							function() {
								var sy = $("#grsy").attr("title");
								if (sy != 0) {
									cuID = "";
									userstaffID = "";
									var typeName = $("input#contactUserID",
											$("table#searchuser")).val();
									var relation = $(":input#grparms").val();
									var typeCN = "contactUser.staffName="
											+ typeName
											+ "&contactUser.relation="
											+ relation
											+ "&pageForm.pageNumber=" + sy;
									cxwlgr(typeCN);
								} else {
									alert("已是首页！");
								}
							});

					// 下一页
					$("#grxy").click(
							function() {
								var xy = $("#grxy").attr("title");
								if (xy != 0) {
									cuID = "";
									userstaffID = "";
									var typeName = $("input#contactUserID",
											$("table#searchuser")).val();
									var relation = $(":input#grparms").val();
									var typeCN = "contactUser.staffName="
											+ typeName
											+ "&contactUser.relation="
											+ relation
											+ "&pageForm.pageNumber=" + xy;
									cxwlgr(typeCN);
								} else {
									alert("已是尾页！");
								}
							});

					// ajax查询往来个人列表
					function cxwlgr(typeCN) {
						if (notoken) {
							alert("正在获取数据！请稍等");
							return;
						}
						notoken = 1;
						$("#grsy").attr("title", 0);
						$("#grxy").attr("title", 0);
						$("#grzy").attr("title", 0);
						var searchurl = basePath
								+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
						
						$
								.ajax({
									url : encodeURI(searchurl + typeCN
											+ "&date="
											+ new Date().toLocaleString()),
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
										var codeRelationList = member.codeRelationList;
										if (pageForm == null) {
											alert("没有数据");
											notoken = 0;
											return;
										}

										var dqy = pageForm.pageNumber;// 当前页
										var zys = pageForm.pageCount;// 总页数
										if (dqy > 1) {
											$("#grsy").attr("title", dqy - 1);
										}
										if (dqy < zys) {
											$("#grxy").attr("title", dqy + 1);
										}
										$("span#grzycount").text(zys);
										var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
										tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
										tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
												+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th></tr>";
										for ( var i = 0; i < pageForm.list.length; i++) {
											tabletr += "<tr style='cursor: hand;' id = "
													+ pageForm.list[i].relationID
													+ " title= "
													+ pageForm.list[i].staffID
													+ ">";
											tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
													+ pageForm.list[i].relationID
													+ " name='checkradio'/></td>";
											tabletr += "<td id='contactUserName' align='center'>"
													+ pageForm.list[i].staffName
													+ "</td>";
											tabletr += "<td id='phone' align='center'>"
													+ pageForm.list[i].relation
													+ "</td>";
											tabletr += "<td id='tel' align='center'>"
													+ pageForm.list[i].reference
													+ "</td>";
											tabletr += "<td id='staffIdentityCard' align='center'>"
													+ pageForm.list[i].staffIdentityCard
													+ "</td>";
											tabletr += "<td id='userQq'  align='center'>"
													+ pageForm.list[i].referenceCode
													+ "</td>";
											tabletr += "<td id='email'  align='center'>"
													+ pageForm.list[i].referenceOrganization
													+ "</td>";
											tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
													+ pageForm.list[i].staffID
													+ "</td>";
											tabletr += " </tr>";
										}
										tabletr += " </table>";
										$("#body_02cu").html(tabletr);
										$("#body_02cu").show();
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

				});

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

						
							tabletr += "<tr style='cursor: hand;' class='staff' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
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
function re_load(){
	window.opener.location.href=window.opener.location.href;
	window.close();
}