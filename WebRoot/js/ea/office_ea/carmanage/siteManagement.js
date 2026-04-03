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
                $(".fees_ #isAudit").val("00");
				siteDetails(siteId,false);
				break;
			case '修改' :
				if (siteId == "") {
					alert('请选择');
					return
				}
				var isAudit = $("tr#"+siteId).find("#isAudit").text();
                $(".fees_ #isAudit").val(isAudit);
				siteDetails(siteId,true);
				break;
			case '删除' :
				if (siteId == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
					delSite(siteId);
				}
				break;
			case '设置每页显示条数' :
				var numberOrName = $("#numberOrName").val();
				var hasBeenUnder = $(".whether").find("option:selected").attr("date-name");
				var url = basePath
						+ "ea/carmanage/ea_siteList.jspa?numberOrName="+numberOrName+"&hasBeenUnder="+hasBeenUnder;
				numback(url);
				break;
		}
	}
	//查询场地列表
	$(".input-button").click(function(){
		ppageNumber = 0;
		var url = basePath
		+ "ea/carmanage/ea_siteList.jspa?numberOrName="+$("#numberOrName").val()+"&hasBeenUnder="+$(".whether").find("option:selected").attr("date-name");
		document.location.href = url;
	})
	
	$(".staff").change(function(){
		var $this = $(this).find("option:selected");
		$(".staffId").val($this.attr("date-staffId"));
		$(".companyId").val($this.attr("date-companyId"));
	})

	//隐藏添加表单
	$(".tit").find("img").click(function(){
		$(".con")[0].reset();  
		$("#fees_").hide();
		$(".companyName").val("");
		$(".siteNumber").val("");
		$(".siteName").val("");
		$(".ItsLocation").val("");
		$(".siteArea").val("");
		$(".fieldCapacity").val("");
		$(".siteId").val("");
		$(".companyId").val("");
		$(".staffId").val("");
        $(".eastLongitude").val("");
        $(".northLatitude").val("");
		$(".staff").empty();
        $(".sitekey").val("");
		$(".temporary").addClass("siteNumber");
		$(".temporary").removeClass("temporary");
	})
	
	//提交保存
	$(".sub").click(function(){
		$("#con input[type='text']").trigger("blur");
		if($(".error").length>0){
			return;
		}
		var url = basePath + "ea/carmanage/sajax_ea_addOrUpdateSiteDetails.jspa";
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
				siteId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	
	//查询场地详情
	function siteDetails(siteId,b){
		var url = basePath + "ea/carmanage/sajax_ea_siteDetails.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data:{
				"vf.siteId":siteId,
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
				$(".siteNumber").removeAttr("readonly");
				$(".companyName").attr({ readonly: 'true' });
				if(b){
					$(".siteNumber").attr({ readonly: 'true' });
					$(".siteId").val(obj[0]);
					$(".companyName").val(obj[1]);
					$(".siteNumber").val(obj[2]);
					$(".siteName").val(obj[3]);
					$(".ItsLocation").val(obj[4]);
					$(".siteArea").val(obj[5]);
					$(".fieldCapacity").val(obj[6]);
					$(".sitekey").val(obj[8]);
                    $(".eastLongitude").val(obj[9]);
                    $(".northLatitude").val(obj[10]);
					$(".siteNumber").addClass("temporary");
					$(".siteNumber").removeClass("siteNumber");
				}else{
					$(".companyName").val(obj[1]);
					$(".companyId").val(obj[0]);
				}
				var a = [];
				$(list).each(function(i, dom) {
					a.push("<option date-staffId='"+this[0]+"' date-companyId='"+this[2]+"'>"+this[1]+"</option>");
					if(i==0){
						$(".staffId").val(this[0]);
						$(".companyId").val(this[2]);
					}
				});
				$(".staff").append(a.join(""));
				$("#fees_").show();
			},
			error : function(data) {
				alert("查询失败");
			}
		});
	}
	
	
	//删除场地
	function delSite(obj){
		var url = basePath + "ea/carmanage/sajax_ea_delSite.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"vf.siteId":obj
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

