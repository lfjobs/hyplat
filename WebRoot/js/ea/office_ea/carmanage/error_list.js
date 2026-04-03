$(document).ready(function() {
	
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [/*{
					name : '扫描车牌',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					},*/{
					name : '车牌对比',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					},{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			/*case '扫描车牌' :
				edit();
				break;*/
			  case '车牌对比' :
				if (carmID == "") {
					alert('请选择');
					return
				}
				edit(carmID);
				break;
			case '删除' :
				if (carmID == "") {
					alert('请选择');
					return
				}
				if(model=="1"){
					if (confirm("确定删除？")) {
						delVehicleInformation(carmID);
					}
				}else if(model=="0"){
					alert("该数据无法删除");
				}
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carmanage/ea_getList.jspa?cm.status="+status+"&cm.carNumber="+$("#carNumber").val();
				numback(url);
				break;
		}
	}
	
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				carmID = this.id;
				model = $(this).find(".model").val();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('车牌对比');
			});
	//删除车辆停车信息
	function delVehicleInformation(obj){
		var url = basePath + "ea/carmanage/sajax_ea_delVehicleInformation.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"cm.carmID":obj
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
				if(standard.boolean){
					window.location.reload();
				}else{
					alert("删除失败");
				}
			},
			error : function(data) {
				alert("删除失败");
			}
		});
	}
	
	
	
	//查询车辆停车信息
	function edit(carmID){
		var url = basePath + "ea/carmanage/sajax_ea_queryVehicleInformation.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"cm.carmID":carmID
			},
			success : function(data) {
				var vehicle = eval("(" + data + ")");
                var nologin = vehicle.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				if (vehicle!=null) {
					var obj = vehicle.obj;
					//编号
					$("#error").find(".number").text(obj[1]);
					//车牌号
					$("#error").find(".license").find(".carNumber").val(obj[9]);
					//离场时间
					$("#error").find(".departure").text(obj[3]);
					//场地编号
					$("#error").find(".site_nub").text(obj[4]);
					//场地名称
					$("#error").find(".site_name").text(obj[5]);
					//设备编号
					$("#error").find(".site_nub").text(obj[6]);
					//场地负责人
					$("#error").find(".device_id").text(obj[7]);
					//车牌数据id
					$("#error").attr("date-cid",obj[0]);
					//场地id
					$("#error").attr("date-vid",obj[14]);
					//错误数据全景图
					$(".img_alert").find(".abnormal").find(".img_con").find(".panorama").find("img").attr("src",basePath+obj[15]);
					//错误数据车牌图
					$(".img_alert").find(".abnormal").find(".img_con").find(".picture").find("img").attr("src",basePath+obj[16]);
					$("#fees_").show();
					$(".fees").css("margin-top",-$(".fees").height()*0.5+"px");
				}
			},
			error : function(data) {
				alert("查询失败");
			}
		});
	}
	
	//修改车牌号
	$("#matching").click(function(){
		var carNumber = $("#error").find(".carNumber").val();
		var vid = $("#error").attr("date-vid");
		carmID = $("#error").attr("date-cid");
		if($("#error").find(".circle").length>0){
			ajax1(carNumber,carmID,vid);
		}else{
			ajax(carNumber,vid);
		}
	})
	
	//修改车牌
	function ajax1(carNumber,carmID,vid){
		var url = basePath + "ea/carmanage/sajax_ea_addOrUpdateVehicle.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"cm.carNumber":carNumber,
				"cm.carmID":carmID
			},
			success : function(data) {
				var vehicle = eval("(" + data + ")");
                var nologin = vehicle.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				var boolean = vehicle.boolean;
				if(boolean){
					$(".carNumber").attr("readonly",true);
					$(".carNumber").parent(".circle").removeClass("circle");
					ajax(carNumber,vid);
				}else{
					alert("修改车牌失败");
				}
			},
			error : function(data) {
				alert("修改车牌失败");
			}
		});
	}
	
	
	
	//查询匹配
	function ajax(carNumber,vid){
		var url = basePath + "ea/carmanage/sajax_ea_errorLicensePlateDetails.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"vf.siteId":vid,
				"cm.carNumber":carNumber
			},
			success : function(data) {
				var vehicle = eval("(" + data + ")");
				var nologin = vehicle.nologin;
				if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				var list = vehicle.list;
				if(list!=null){
					var a = [];
					$(".matching").find(".tab").find(".tab_con").empty();
					$(list).each(function(i, dom) {
						if(i==0){
							a.push("<ul class='active'>");
						}else{
							a.push("<ul>");
						}
						var b = i+1;
						a.push("<li class='xuan' date-cid='"+this[0]+"'><p><span></span></p></li>");
						a.push("<li class='order'>"+b+"</li>");
						a.push("<li class='number'>"+this[8]+"</li>");
						a.push("<li class='license'>"+this[1]+"</li>");
						a.push("<li class='departure'>"+this[2]+"</li>");
						a.push("<li class='site_nub'>"+this[3]+"</li>");
						a.push("<li class='site_name'>"+this[4]+"</li>");
						a.push("<li class='site_nub'>"+this[5]+"</li>");
						a.push("<li class='device_id'>"+this[6]+"</li>");
						a.push("<li class='img' date-panorama='"+this[11]+"' date-picture='"+this[12]+"'>");
						a.push("<img src='"+basePath+"/images/img.png'></li>");
						a.push("</ul>");
					})	
					$(".matching").find(".tab").find(".tab_con").append(a.join(""));
					$(".matching").find(".wu").hide();
					$(".matching").find(".tab").show();
				}
			},
			error : function(data) {
				alert("查询失败");
			}
		});
	}
	
	$(".matching .tab .tab_con .img").live("click",function(){
        $(".img_alert").find(".mate").find(".panorama").find("img").attr("src",basePath+$(this).attr("date-panorama"));
        $(".img_alert").find(".mate").find(".picture").find("img").attr("src",basePath+$(this).attr("date-picture"));
    });
	//提交保存
	$(".Choose").click(function(){
		var carmID1 = $("#error").attr("date-cid");
		var carmID2 = $(".matching").find(".tab").find(".tab_con").find(".active").find(".xuan").attr("date-cid");
		var url = basePath + "ea/carmanage/sajax_ea_errorCorrection.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data:{
				"cm.carmID":carmID1,
				"carmID":carmID2
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
				if(standard.boolean){
					window.location.reload();
				}else{
					alert("修改失败");
				}
			},
			error : function(data) {
				alert("修改失败");
			}
		});
	})
	
});
