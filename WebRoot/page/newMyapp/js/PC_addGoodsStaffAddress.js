$(function(){
	ajaxSelAllAddress();
	ajaxShowAllCity();
	//新增收货地址
	 $("#add_address").click(function(){
		 var url=basePath+"ea/newpcend/sajax_ea_ajaxValidateCusLogin.jspa";
			$.ajax({
				url:url,
				type:"post",
				async : true,
				success:function(data){
					var result=eval("(" + data + ")");
					var login = result.login;
					if(login == "login"){
						document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
						return;
					}
					window.location.href=basePath+"page/newMyapp/PC_addGoodsStaffAddress.jsp?ppID="+ppID+"&standard="+standard+"&count="+count;
					return;
				},
				dataType : "json",
				error:function(){
					alert("验证失败！");
				}
			});
	 });
	
	//设置表单为异步提交
	$("#mainForm").submit(function(){
		if($("#formLimit").val()=="false"){
			$.ajax({
				url: basePath+"ea/newpcend/sajax_ea_ajaxAddStaffAddress.jspa",
				type: "post",
				async : true,
				data: $("#mainForm").serialize(),
				success: function(result){
					if(result=="addSuccess"){
						if(confirm("保存成功!是否继续?")==false){
							document.location.href=basePath+"ea/newpcend/ea_pcGoodsPayNow.jspa?ppk.ppID="+ppID+"&standard="+standard+"&count="+count;
							return;
						}
						window.location.href=basePath+"page/newMyapp/PC_addGoodsStaffAddress.jsp?ppID="+ppID+"&standard="+standard+"&count="+count;
						return;
					}
					alert("保存失败！最多添加10个收货地址。");
					window.location.href=basePath+"ea/newpcend/ea_pcGoodsPayNow.jspa?ppk.ppID="+ppID+"&standard="+standard+"&count="+count;
					return;
				},
				error: function(){
					alert("保存地址失败!");
				},
				complete: function(){
					$("#formLimit").val("true");
				}
			});
		}
		return false;
	});
	//给收货地址隐藏域赋值
	$("#Select3").click(function(){
		$(".right2 #area").val($(".right2 #Select1").val()+","+$(".right2 #Select2").val()+","+$(".right2 #Select3").val());
	});
});

//查询所有收货地址
function ajaxSelAllAddress(){
	var url=basePath+"ea/newpcend/sajax_ea_ajaxPcStaffAddress.jspa";
	$.ajax({
		url:url,
		type:'post',
		async:false,
		success:function(data){
			var result=eval("(" + data + ")");
			var staffAddress=result.staffAddress;
			var addressDefaultObj=result.staffAddress.addressDefaultObj;
			var addressObjs=result.staffAddress.addressObjs;
			var addressCount=result.staffAddress.addressCount;
			var array=[];
			if(staffAddress!=null&&addressCount!=null){
				$(".title h5 .addressCount").text(addressCount);
			}
			if(staffAddress!=null&&addressDefaultObj!=null&&addressDefaultObj.length>0){
				var addressArray=addressDefaultObj[1].split(",");
				array.push("<li class='active'>");
				array.push("<h5><span class='dizhi'>"+addressArray[0]+addressArray[1]+"</span>");
				array.push("<span class='mingzi'>"+addressDefaultObj[2]+"</span>");
				array.push("<span>收</span></h5>");
				array.push("<input type='hidden' class='addressID' value='"+addressDefaultObj[0]+"'>");
				array.push("<input type='hidden' class='addressStr' value='"+addressDefaultObj[1]+"'>");
				array.push("<div class='text'>");
				array.push("<p limit='25'>"+addressArray[2]+"&nbsp;"+addressDefaultObj[3]+"&nbsp;"+addressDefaultObj[4]+"</p>");
				array.push("</div>");
				array.push("<img src='"+basePath+"page/newMyapp/images/address.png' class='address' />");
				array.push("<img src='"+basePath+"page/newMyapp/images/address_.png' class='address_set'/>");
				array.push("<p class='revise_' onclick='alterShowStaffAddress(this);'>修改</p><p class='delete_'>删除</p>");
				array.push("<img src='"+basePath+"page/newMyapp/images/add_gou.png' class='gou' /></li>");
			}
			if(staffAddress!=null&&addressObjs!=null&&addressObjs.length>0){
				$(addressObjs).each(function(){
					var addressArray=$(this)[1].split(",");
					array.push("<li><h5><span class='dizhi'>"+addressArray[0]+addressArray[1]+"</span>");
					array.push("<span class='mingzi'>"+$(this)[2]+"</span>");
					array.push("<span>收</span></h5>");
					array.push("<input type='hidden' class='addressID' value='"+$(this)[0]+"'>");
					array.push("<input type='hidden' class='addressStr' value='"+$(this)[1]+"'>");
					array.push("<div class='text'>");
					array.push("<p limit='25'>"+addressArray[2]+"&nbsp;"+$(this)[3]+"&nbsp;"+$(this)[4]+"</p>");
					array.push("</div>");
					array.push("<img src='"+basePath+"page/newMyapp/images/address.png' class='address' />");
					array.push("<img src='"+basePath+"page/newMyapp/images/address_.png' class='address_set'/>");
					array.push("<p class='revise_' onclick='alterShowStaffAddress(this);'>修改</p><p class='delete_'>删除</p>");
					array.push("<img src='"+basePath+"page/newMyapp/images/add_gou.png' class='gou' /></li>");
				});
			}
			$(".add_con").append(array.join(""));
		},
		error:function(data){
			alert("获取地址失败！");
		}
	});
}

//查询所有的收货地址
function ajaxShowAllCity(){
	$("#Select1").append("<option selected><span>--请选择--</span></option>");
	$("#Select2").append("<option selected><span>--请选择--</span></option>");
	$("#Select3").append("<option selected><span>--请选择--</span></option>");
	var url = basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictCity.jspa";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		success:function(data){
			var member = eval("(" + data + ")");
			var cityMap=member.city;
			var array=[];
			$(cityMap.districtCity).each(function(){
				array.push("<option id='"+$(this)[0]+"'><span>"+$(this)[1]+"</span></option>");
			});
			$("#Select1").append(array.join(""));
		},
		error:function(){
			alert("地址获取失败！");
		}
	});
	//省、市地址联动
	$("#Select1").change(function(){
		var sel=$(this).find("option:selected").text();
		var districtID=$(this).find("option:selected").attr("id");
		if(sel!='--请选择--'){
			$.ajax({
				url : basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa",
				type : "post",
				async : false,
				data: { 
						"sdistrict.districtID":districtID,
						"showParam":"true"
				},
				dataType : "json",
				success: function(data){
					var subMember = eval("(" + data + ")");	
					var country=subMember.district.country;
					var array=[];
					$("#Select2 option").remove();
					$("#Select3 option").remove();
					if(country!=null&&country.length>0){
						$("#Select2").append("<option selected><span>--请选择--</span></option>");
						$("#Select3").append("<option selected><span>--请选择--</span></option>");
						$(country).each(function(){
							array.push("<option id='"+$(this)[0]+"'><span>"+$(this)[1]+"</span></option>");
						});
					}
					$("#Select2").append(array.join(""));
				},
				error:function(){
					alert("地址获取失败！");
				}
			});
		}else{
			$("#Select2").html("<option class='c' selected><span>--请选择--</span></option>");
			$("#Select3").html("<option class='d' selected><span>--请选择--</span></option>");
		}
	});
	//市、区地址联动
	$("#Select2").change(function(){
		var subSel=$(this).find("option:selected").text();
		var districtID=$(this).find("option:selected").attr("id");
		if(subSel!='--请选择--'){
			$.ajax({
				url : basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa",
				type : "post",
				async : false,
				data: { 
						"sdistrict.districtID":districtID,
						"showParam":"false"
				},
				dataType : "json",
				success: function(data){
					var subMember = eval("(" + data + ")");	
					var country=subMember.district.country;
					var array=[];
					$("#Select3 option").remove();
					if(country!=null&&country.length>0){
						$("#Select3").append("<option selected><span>--请选择--</span></option>");
						$(country).each(function(){
							array.push("<option id='"+$(this)[0]+"'><span>"+$(this)[1]+"</span></option>");
						});
					}
					$("#Select3").append(array.join(""));
				},
				error:function(){
					alert("地址获取失败！");
				}
			});
		}else{
			$("#Select3").html("<option class='d' selected><span>--请选择--</span></option>");
		}
	});
}

//保存收货地址
function ajaxAddStaffAddress(){
	var country=$(".right2 #Select2").val()!=null?$(".right2 #Select2").val():"";
	var city=$(".right2 #Select3").val()!=null?$(".right2 #Select3").val():"";
	$(".right2 #area").val($(".right2 #Select1").val()+","+country+","+city);
	if($(".right #consignee").val()==""){
		alert("收货人姓名不能为空。");
		return false;
	}
	if($(".right2 #Select1").val()=="--请选择--"){
		alert("请选择省份。");
		return false;
	}
	if($(".right2 #Select3").val()=="--请选择--"){
		alert("请选择地区。");
		return false;
	}
	if($(".address_right #addressDetailed").val()=="--请选择--"){
		alert("详细地址不能为空。");
		return false;
	}
	if($(".right #phone").val()==""){
		alert("手机号码不能为空。");
		return false;
	}
	if(!$(".right #phone").val().match(/^1[34578]\d{9}$/)){
		alert("手机号码格式不正确。");
		return false;
	}
	$.ajax({
		url: basePath+"ea/newpcend/sajax_ea_ajaxValidateCusLogin.jspa",
		type: "post",
		async: true,
		dataType: "json",
		success: function(data){
			var result=eval("(" + data + ")");
			var login = result.login;
			if(login == "login"){
				document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
				return;
			}
			$("#mainForm").submit();
		},
		error: function(){
			alert("身份验证失败！");
		}
	});
}

//回显修改收货地址
function alterShowStaffAddress(obj){
	var addressArray=$(obj).parent("li").find(".addressStr").val().split(",");
	var messageArray=$(obj).parent("li").find(".text p").html().split("&nbsp;");
	var titleMessage=$(".add_address .title h4").html();
	$(".add_address .title h4").html(titleMessage.replace("新增","修改"));
	$(".right #consignee").val($(obj).parent("li").find(".mingzi").text());
	$(".address_right #addressDetailed").val(messageArray[1]);
	$(".right #phone").val(messageArray[2]);
	$("#Select1").val(addressArray[0]);
	$("#Select1").change();
	$("#Select2").val(addressArray[1]);
	if($("#Select2").val()!=null){
		$("#Select2").change();
	}
	$("#Select3").val(addressArray[2]);
	$(".right2 #area").val($(".right2 #Select1").val()+","+$(".right2 #Select2").val()+","+$(".right2 #Select3").val());
	if($(obj).parent("li").hasClass("active")){
		$(".add_address .add_add_con .default_address .right").addClass("on");
		$(".add_address .add_add_con .default_address #isDefault").val("是");
	}else{
		$(".add_address .add_add_con .default_address .right").removeClass("on");
		$(".add_address .add_add_con .default_address #isDefault").val("");
	}
	$("#addressID").val($(obj).parent("li").find(".addressID").val());
}



