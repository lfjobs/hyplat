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
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					},{
					name : '修改',
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
			case '添加' :
				deviceLnfo(equipmentid,false);
				break;
			case '修改' :
				if (equipmentid == "") {
					alert('请选择');
					return
				}
				deviceLnfo(equipmentid,true);
				break;
			case '删除' :
				if (equipmentid == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
					removeEquipment(equipmentid);
				}
				break;
			case '设置每页显示条数' :
				var numberOrName = $("#numberOrName").val();
				var hasBeenUnder = $(".whether").find("option:selected").attr("date-status");
				var url = basePath
						+ "ea/carmanage/ea_facilityList.jspa?numberOrName="+numberOrName+"&hasBeenUnder="+hasBeenUnder;
				numback(url);
				break;
		}
	}
	
	
	//查询场地列表
	$(".input-button").click(function(){
		ppageNumber = 0;
		var url = basePath
		+ "ea/carmanage/ea_facilityList.jspa?numberOrName="+$("#numberOrName").val()+"&hasBeenUnder="+$(".whether").find("option:selected").attr("date-status");
		document.location.href = url;
	})
	
	$(".site").change(function(){
		var $this = $(this).find("option:selected");
		$(".siteId").val($this.attr("date-siteId"));
	})
	$(".select").change(function(){
		var $this = $(this).find("option:selected");
		$(".status").val($this.attr("date-status"));
	})

	//隐藏添加表单
	$(".tit").find("img").click(function(){
		$(".con")[0].reset();  
		$("#fees_").hide();
		$(".companyName").val("");
		$(".equipmentNumber").val("");
		$(".deviceName").val("");
		$(".unitType").val("");
		$(".manufacturer").val("");
		$(".siteName").val("");
		$(".siteId").val("");
		$(".equipmentkey").val("");
		$(".staffId").val("");
		$(".status").val("");
		$(".site").empty();
		$(".temporary").addClass("equipmentNumber");
		$(".temporary").removeClass("temporary");
	})
	//提交保存
	$(".sub").click(function(){
		$("#con input[type='text']").trigger("blur");
		if($(".error").length>0){
			return;
		}
		var site = $(".site").val();
		if(site==""){
			return "您未设置场地,请设置";
		}
		var url = basePath + "ea/carmanage/sajax_ea_addOrUpdateDeviceLnfo.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data:$('#con').serialize(),// 你的formid
			success : function(data) {
				var standard = eval("(" + data + ")");
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
				equipmentid = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	
	//查询设备详情
	function deviceLnfo(equipmentId,b){
		var url = basePath + "ea/carmanage/sajax_ea_deviceLnfo.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data:{
				"ef.equipmentId":equipmentId,
		        "siteJudge":b
			},
			success : function(data) {
				var site = eval("(" + data + ")");
                var nologin = site.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				var obj = site.obj;
				var list = site.list;
				$(".equipmentNumber").removeAttr("readonly");
				$(".companyName").attr({ readonly: 'true' });
				if(b){
					$(".equipmentNumber").attr({ readonly: 'true' });
					$(".siteId").val(obj[7]);

					$(".equipmentId").val(obj[1]);
					$(".companyName").val(obj[2]);
					$(".equipmentNumber").val(obj[3]);
					$(".deviceName").val(obj[4]);
					$(".unitType").val(obj[5]);
					$(".manufacturer").val(obj[6]);
					$(".equipmentkey").val(obj[0]);
					$(".equipmentNumber").addClass("temporary");
					$(".equipmentNumber").removeClass("equipmentNumber");
					$(".status").val(obj[9]);
					$(".channel").val(obj[10]);
				}else{
					$(".companyName").val(obj[1]);
				}
				var a = [];
				$(list).each(function(i, dom) {
					a.push("<option date-siteid='"+this[0]+"' value='"+this[0]+"'>"+this[1]+"</option>");
					if(i==0){
						$(".siteId").val(this[0]);
					}

				});
				$(".site").append(a.join(""));
				$(".site").val(obj[7]);
				//$(".status").val("01");
				$("#fees_").show();
			},
			error : function(data) { 
				alert("查询失败");
			}
		});
	}
	
	
	//删除设备
	function removeEquipment(obj){
		var url = basePath + "ea/carmanage/sajax_ea_removeEquipment.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"ef.equipmentId":obj
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
});
