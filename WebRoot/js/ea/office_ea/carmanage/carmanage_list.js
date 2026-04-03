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
				buttons : [{
					name : '扫描车牌',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					},/*{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					},*/{
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
			case '扫描车牌' :
				edit();
				break;
			/*case '修改' :
				if (carmID == "") {
					alert('请选择');
					return
				}
				edit(carmID);
				break;*/
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
	/*$(".carm").change(function(){
		var $this = $(this).find("option:selected");
		$(".carNumber").val($this.attr("date-carNumber"));
	})*/
	
	$(".site").change(function(){
		var $this = $(this).find("option:selected");
		$(".siteName").val($this.attr("date-siteName"));
		$(".siteId").val($this.attr("date-siteId"));
		serialnumber($this.attr("date-siteId"));
	})
	$(".equipment").change(function(){
		var $this = $(this).find("option:selected");
		$(".equipmentNumber").val($this.attr("date-equipmentNumber"));
	})
	//查询设备编号
	function serialnumber(obj){
		var url = basePath + "ea/carmanage/sajax_ea_queryequipmentNumber.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"ef.siteId":obj
			},
			success : function(data) {
				var sd = eval("(" + data + ")");
				var list = sd.standard;
				if (list!=null){
					$(".equipment").empty();
					var a =[];
					for ( var i = 0; i < list.length; i++) {
						a.push("<option date-equipmentNumber='"+list[i]+"'>"+list[i]+"</option>");
						if(i==0){
							$(".equipmentNumber").val(list[i]);
						}
					}
					$(".equipment").append(a.join(""));
				}
			},
			error : function(data) {
				alert("查询失败");
			}
		});
	}
	
	
	//like查询车牌号
	$(".input-button").click(function(){
		ppageNumber = 0;
		var url = basePath
		+ "ea/carmanage/ea_getList.jspa?cm.status="+status+"&numberOrName="+$("#numberOrName").val();
		document.location.href = url;
	})
	//隐藏添加表单
	$(".tit").find("img").click(function(){
		$(".con")[0].reset();  
		$("#fees_").hide();
	})
	//提交保存
	$(".sub").click(function(){
		$(".put3").trigger("blur");
		if($(".error").length>0){
			return;
		}
		var site = $(".site").val();
		if(site==""){
			return "您未设置场地,请设置";
		}
		var equipment = $(".equipment").val();
		if(equipment==""){
			return "您未设置设备,请设置";
		}
		var url = basePath + "ea/carmanage/sajax_ea_addOrUpdateVehicle.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data:$('#con').serialize(),// 你的formid
			success : function(data) {
				var standard = eval("(" + data + ")");
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				if(standard.boolean){
					window.location.reload();
				}else{
					alert("添加失败");
				}
			},
			error : function(data) {
				alert("添加失败");
			}
		});
	})

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				carmID = this.id;
				model = $(this).find(".model").val();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
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
	//查询场地编号
	function edit(){
		var url = basePath + "ea/carmanage/sajax_ea_queryNumber.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			success : function(data) {
				var nm = eval("(" + data + ")");
                var nologin = nm.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				var list = nm.number;
				if(list!=null){
					$(".carmNumber").attr({ readonly: 'true' });
					$(".siteName").attr({ readonly: 'true' });
					$(".site").empty();
					var a =[];
					for ( var i = 0; i < list.length; i++) {
						a.push("<option date-siteId='"+list[i][1]+"' date-siteName='"+list[i][2]+"'>"+list[i][0]+"</option>");
						if(i==0){
							$(".siteName").val(list[i][2]);
							$(".siteId").val(list[i][1]);
							serialnumber(list[i][1]);
						}
					}
					$(".site").append(a.join(""));
					$("#fees_").show();
				}
			},
			error : function(data) {
				alert("查询失败");
			}
		});
	}
	
	
	
	
	
	
	//查询车辆停车信息
	/*function edit(carmID){
		var url = basePath + "ea/carmanage/sajax_ea_queryVehicleInformation.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"cm.carmID":carmID,
				"cm.status":status
			},
			success : function(data) {
				var vehicle = eval("(" + data + ")");
				if (vehicle!=null) {
					var obj = vehicle.obj;
					var list = vehicle.list;
					$(".carmID").val(obj[0]);
					$(".carmNumber").val(obj[1]);
					$(".carmNumber").attr({ readonly: 'true' });
					$(".indate").val(obj[2]);
					$(".indate").attr({ readonly: 'true' });
					$(".outdate").val(obj[3]);
					$(".outdate").attr({ readonly: 'true' });
					$(".number").val(obj[4]);
					$(".number").attr({ readonly: 'true' });
					$(".siteName").val(obj[5]);
					$(".siteName").attr({ readonly: 'true' });
					$(".equipment").val(obj[6]);
					$(".equipment").attr({ readonly: 'true' });
					$(".staffname").val(obj[7]);
					$(".staffname").attr({ readonly: 'true' });
					if (obj[8]=="0") {
						$(".indate").parents(".mil").remove();
					}else if(obj[8]=="1"){
						$(".outdate").parents(".mil").remove();
					}
					$(".carm").empty();
					var a =[];
					for ( var i = 0; i < list.length; i++) {
						a.push("<option date-carNumber='"+list[i]+"'>"+list[i]+"</option>");
						if(i==0){
							$(".carNumber").val(list[i]);
						}
					}
					$(".carm").append(a.join(""));
					$("#fees_").show();
				}
			},
			error : function(data) {
				alert("查询失败");
			}
		});
	}*/
	
});
