$(document).ready(function(){
/*	 $("#address").focus(function(){ 
z		 	$(this).val("");
			$("#occlusion2").css("z-index",$(".wfj11_018").css("z-index")+1);
			$("#occlusion2").jqmShow();
			$(".tanchu").css("z-index",$("#occlusion2").css("z-index")+1);
			$(".tanchu").fadeIn(1000);
			$(".city").attr("style","display:none;");
			$("#city").empty();
			$(".district").attr("style","display:none;");
			$("#district").empty();
			$(".province").attr("style","display:block;");
		//省
			//$select = "<option selected='selected'><span>--请选择--</span></option>";
			var url = basePath
					+ "/ea/wfjshop/sajax_ea_getCitiesList.jspa?"
			$("#location_p").append("<option class='p' selected='selected'><span>--请选择--</span></option>");
			$("#location_c").append("<option class='c' selected='selected'><span>--请选择--</span></option>");
			$("#location_d").append("<option class='d' selected='selected'><span>--请选择--</span></option>");
			$.ajax({
				url : url,
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var list=member.result;
					var htl=new Array();
					for (var x = 0; x < list.length; x++) {
						htl.push("<option class='"+list[x].id+"'><span>"+list[x].province+"</span></option>");
					}
					$("#location_p").append(htl.join(""));
					//滚动效果
					$(".tanchu").find("div").find("ul").css("height",parseInt($(".tanchu").height()-20)+"px");			
					$("#location_p").find("option").attr("style"," height:"+$(window).height()*0.065+"px;font-size:"+$(window).height()*0.025+"px;text-align:left;width:96%;padding-left:5px;");
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
			//市
			$("#location_p").change(function(){
					$(".city").attr("style","display:block;");
					$(".province").attr("style","display:none;");
					var sel=$(this).find("option:selected").text();
					if(sel!='--请选择--'){
					$("#location_c").empty();
					$("#location_c").append("<option class='c' selected='selected'><span>--请选择--</span></option>");
					change= $(this).find("option:selected").attr("class");
						var url = basePath
							+ "/ea/wfjshop/sajax_ea_getCitiesList.jspa";
							$.ajax({
								url : url,
								type : "get",
								async : true,
								dataType : "json",
								success : function cbf(data) {
									var member = eval("(" + data + ")");						
									var list = member.result[change-1].city;
									var htl=new Array();
									for (var i = 0; i < list.length; i++) {
										htl.push("<option class='"+list[i].id+"' id='"+i+"'><span>"+list[i].city+"</span></option>");
									}
									$("#location_c").append(htl.join(""));
									$("#location_c option:first").prop("selected", 'selected');
									$("#city").find("option").attr("style"," height:"+$(window).height()*0.065+"px;font-size:"+$(window).height()*0.025+"px;text-align:left;width:96%;padding-left:5px;");

								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
								
							});
						}else{
							$("#location_c").html("<option class='c' selected='selected'><span>--请选择--</span></option>");
							$("#location_d").html("<option class='d' selected='selected'><span>--请选择--</span></option>");
						}
						
					});

				//区
				$("#location_c").change(function(){
					$(".district").attr("style","display:block;");
					$(".city").attr("style","display:none;");
					var sel=$(this).find("option:selected").text();
					if(sel!='--请选择--'){
					$("#location_d").empty();
					$("#location_d").append("<option class='d' selected='selected'><span>--请选择--</span></option>");
					change1= $(this).find("option:selected").attr("id");
						var url = basePath
							+ "/ea/wfjshop/sajax_ea_getCitiesList.jspa?"
							$.ajax({
								url : url,
								type : "get",
								async : true,
								dataType : "json",
								success : function cbf(data) {
									var member = eval("(" + data + ")");						
									var list = member.result[change-1].city[change1].district ;
									var htl=new Array();
									for (var i = 0; i < list.length; i++) {
										htl.push("<option class='"+list[i].id+"'><span>"+list[i].district+"</span></option>");
									}
									$("#location_d").append(htl.join(""));
									$("#location_d option:first").prop("selected", 'selected');
									$("#district").find("option").attr("style"," height:"+$(window).height()*0.065+"px;font-size:"+$(window).height()*0.025+"px;text-align:left;width:96%;padding-left:5px;");
		
									
								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
							});
					}
				});	 
				$("#location_d").change(function(){
					$("#address").val($("#location_p").find("option:selected").text()+$("#location_c").find("option:selected").text()+$("#location_d").find("option:selected").text());

				});
				//选择区，地址赋值
				$("add1").focus(function(){
					$("#address").val("");
					$("#address").val($("#location_p").find("option:selected").text()+$("#location_c").find("option:selected").text()+$("#location_d").find("option:selected").text());
					$("#post").html("<h5>邮政编码</h5><input type='text' name='postCode' maxlength='6'/>");
					$(".content li input").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
					$(".content li h5").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
					$("#postCode").remove();
				})
				$("#add1").blur(function(){
				
				$("#location_code").empty();
				var temp=$(this).val();
				var q="";
				if(temp.indexOf("街")!=-1)
					q=temp.substring(0,temp.indexOf("街"));
				else if(temp.indexOf("道")!=-1)
					q=temp.substring(0,temp.indexOf("道"));
				else if(temp.indexOf("开发区")!=-1)
					q=temp.substring(0,temp.indexOf("开发区"));
				else if(temp.indexOf("乡")!=-1)
					q=temp.substring(0,temp.indexOf("乡"));
				else if(temp.indexOf("镇")!=-1)					
					q=temp.substring(0,temp.indexOf("镇"));
				else if(temp.indexOf("路")!=-1)
					q=temp.substring(0,temp.indexOf("路"));
				var pid=$("#location_p").find("option:selected").attr("class");
				var cid=$("#location_c").find("option:selected").attr("class");
				var did=$("#location_d").find("option:selected").attr("class");	
					if(pid=='p')
						prompt("请选择省!");
					else if(cid=='c')
						prompt("请选择市！");
					else if(did=='d')
						prompt("请选择区！");
					else if(q==''){
						$("#post").html("<h5>邮政编码</h5><input type='text' name='postCode' maxlength='6'/>");
						$(".content li input").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
						$(".content li h5").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
						$("#postCode").remove();
					}
					else{
					var url = basePath
					+ "/ea/wfjshop/sajax_ea_getZipCode.jspa?"
						$.ajax({
							url : url,
							type : "get",
							async : false,
							dataType : "json",
							data :{
								"pid":pid,
								"cid":cid,
								"did":did,
								"q": q
							},
							success:function cbf(data){
								var member=eval("("+data+")");
								var list=member.result.list;
								var htl=new Array();
								var thl=new Array();
								if(list==null){
								$("#post").html("<h5>邮政编码</h5><input type='text' name='postCode' maxlength='6'/>");
								$(".content li input").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
								$(".content li h5").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
								$("#postCode").remove();
								}
								else{
									$("#post").html("<h5>邮政编码</h5><select id='location_code'></select>");
									$(".content li select").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
									$(".content li h5").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
									for(var i=0; i<list.length;i++){
											if($.inArray(list[i].PostNumber,thl)==-1){
												thl.push(list[i].PostNumber);
												htl.push("<option class='"+list[i].PostNumber+"'><span>"+list[i].PostNumber+"</span></option>");
											}
										}
									}
								$("#location_code").append(htl.join(""));
								$("#location_code option:first").prop("selected", 'selected');	
							},
							error:function cbf(){
								prompt("邮编获取失败");
							}
							});
					}

				});*/
	ajaxShowAllCity();
});//加载完毕

//查询所有的收货地址
function ajaxShowAllCity(){
	$("#location_p").append("<option class='p' selected='selected'><span>--请选择--</span></option>");
	$("#location_c").append("<option class='c' selected='selected'><span>--请选择--</span></option>");
	$("#location_d").append("<option class='d' selected='selected'><span>--请选择--</span></option>");
	var url = basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictCity.jspa";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		success:function(data){
			var member = eval("(" + data + ")");
			var cityMap=member.city;
			var array=[];
			$(cityMap.districtCity).each(function(){
				array.push("<option id='"+$(this)[0]+"'><span>"+$(this)[1]+"</span></option>");
			});
			$("#location_p").append(array.join(""));
		},
		error:function(){
			alert("地址获取失败！");
		}
	});
	//省、市地址联动
	$("#location_p").change(function(){
		var sel=$(this).find("option:selected").text();
		var districtID=$(this).find("option:selected").attr("id");
		if(sel!='--请选择--'){
			$.ajax({
				url : basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa",
				type : "post",
				async : true,
				data: { 
						"sdistrict.districtID":districtID,
						"showParam":"true"
				},
				dataType : "json",
				success: function(data){
					var subMember = eval("(" + data + ")");	
					var country=subMember.district.country;
					var array=[];
					$("#location_c option").remove();
					$("#location_d option").remove();
					if(country!=null&&country.length>0){
						$("#location_c").append("<option selected><span>--请选择--</span></option>");
						$("#location_d").append("<option selected><span>--请选择--</span></option>");
						$(country).each(function(){
							array.push("<option id='"+$(this)[0]+"'><span>"+$(this)[1]+"</span></option>");
						});
					}
					$("#location_c").append(array.join(""));
				},
				error:function(){
					alert("地址获取失败！");
				}
			});
		}else{
			$("#location_c").html("<option class='c' selected='selected'><span>--请选择--</span></option>");
			$("#location_d").html("<option class='d' selected='selected'><span>--请选择--</span></option>");
		}
	});
	//市、区地址联动
	$("#location_c").change(function(){
		var subSel=$(this).find("option:selected").text();
		var districtID=$(this).find("option:selected").attr("id");
		if(subSel!='--请选择--'){
			$.ajax({
				url : basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa",
				type : "post",
				async : true,
				data: { 
						"sdistrict.districtID":districtID,
						"showParam":"false"
				},
				dataType : "json",
				success: function(data){
					var subMember = eval("(" + data + ")");	
					var country=subMember.district.country;
					var array=[];
					$("#location_d option").remove();
					if(country!=null&&country.length>0){
						$("#location_d").append("<option selected><span>--请选择--</span></option>");
						$(country).each(function(){
							array.push("<option id='"+$(this)[0]+"'><span>"+$(this)[1]+"</span></option>");
						});
                        array.push("<option id='00'><span>其他区</span></option>");
					}
					$("#location_d").append(array.join(""));
				},
				error:function(){
					alert("地址获取失败！");
				}
			});
		}else{
			$("#location_d").html("<option class='d' selected><span>--请选择--</span></option>");
		}
	});
	$("#location_d").change(function(){
		$("#address").val($("#location_p").find("option:selected").text()+","+$("#location_c").find("option:selected").text()+","+$("#location_d").find("option:selected").text());
	});
}

//市

/*//验证电话
function checkphone(){
	var reg=new RegExp("^1[3|4|5|7|8][0-9]\\d{8}$");
	var phone=$("#phone").val();
	if(!reg.test(phone)){
		alert("电话格式不正确！");
		return false;
	}
	return true;
}
//验证非空
function isNull(){
	var input=document.getElementsByTagName("input");
	for(var i=0;i<input.length;i++){
		if(input[i].type=='text'){
			if(input[i].value==''){
    			alert("信息不完整！");
    			return false;
			}
			if(input[i].value=='请输入详细地址'||input[i].value=='请输入收货人姓名'||input[i].value=='请输入电话号码'||input[i].value=='请输入邮政编码'){
				alert("不可以为默认值！");
				return false;
			}
		}
	
	}
	return true;
}
//验证邮政编码
function checkpostcode(){
	var reg=new RegExp("[1-9]\\d{5}(?!\d)");
	var pcod=$("#postCode").val();
	if(!reg.test(pcod)){
		alert("邮政编码不正确！");
		return false;
	}
	return true;
}*/