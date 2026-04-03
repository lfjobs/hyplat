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
				erDetails(erId,false);
				break;
			case '修改' :
				if (erId == "") {
					alert('请选择');
					return
				}
                erDetails(erId,true);
				break;
			case '删除' :
				if (erId == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
					delExoom(erId);
				}
				break;
			case '设置每页显示条数' :
				var numberOrName = $("#numberOrName").val();
				var url = basePath
						+ "ea/carmanage/ea_examinationRoomList.jspa?numberOrName="+numberOrName;
				numback(url);
				break;
		}
	}
	//查询场地列表
	$(".input-button").click(function(){
		ppageNumber = 0;
		var url = basePath
		+ "ea/carmanage/ea_examinationRoomList.jspa?numberOrName="+$("#numberOrName").val();
		document.location.href = url;
	})
	
	$(".staff").change(function(){
		var $this = $(this).find("option:selected");
		$(".staffId").val($this.attr("date-staffId"));
	})

    $(".regulators").change(function(){
        var $this = $(this).find("option:selected");
        $(".reviewerStaffId").val($this.attr("date-staffId"));
        queryAccount($this.attr("date-staffId"));
    })

    $(".sType").change(function(){
        var $this = $(this).find("option:selected");
        $(".siteType").val($this.attr("data-status"));
    })


	//隐藏添加表单
	$(".tit").find("img").click(function(){
		$(".con")[0].reset();  
		$("#fees_").hide();
		$(".companyName").val("");
		$(".erNumber").val("");
		$(".erName").val("");
		$(".erId").val("");
		$(".companyId").val("");
		$(".staffId").val("");
        $(".reviewerStaffId").val("");
        $(".ItsLocation").val("");
		$(".staff").empty();
        $(".regulators").empty();
        $(".siteType").val("");
        $(".erkey").val("");
		$(".temporary").addClass("erNumber");
		$(".temporary").removeClass("temporary");
	})
	
	//提交保存
	$(".sub").click(function(){
		$("#con input[type='text']").trigger("blur");
		if($(".error").length>0){
			return;
		}
		var url = basePath + "ea/carmanage/sajax_ea_addOrUpdateErDetails.jspa";
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
        erId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	
	//查询考场详情
	function erDetails(erId,b){
		var url = basePath + "ea/carmanage/sajax_ea_erDetails.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data:{
				"exRoom.erId":erId,
		        "siteJudge":b
			},
			success : function(data) {
				var er = eval("(" + data + ")");
                var nologin = er.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				var obj = er.obj;
				var list = er.list;
				$(".erNumber").removeAttr("readonly");
				$(".companyName").attr({ readonly: 'true' });
				if(b){
					$(".erNumber").attr({ readonly: 'true' });
                    $(".erkey").val(obj[0]);
					$(".erId").val(obj[1]);
					$(".erNumber").val(obj[2]);
					$(".erName").val(obj[3]);
                    $(".companyId").val(obj[4]);
                    $(".companyName").val(obj[5]);
                    $(".ItsLocation").val(obj[6]);

					$(".erNumber").addClass("temporary");
					$(".erNumber").removeClass("erNumber");
				}else{
					$(".companyName").val(obj[1]);
					$(".companyId").val(obj[0]);
				}
                $(".siteType").val("0");
				//监管人
				var a = [];
                $(list).each(function(i, dom) {
                	if(this[0]==obj[8]){
                        a.push("<option date-staffId='"+this[0]+"' selected>"+this[1]+"</option>");
					}else{
                        a.push("<option date-staffId='"+this[0]+"'>"+this[1]+"</option>");
                    }
                    if(i==0){
                        $(".staffId").val(this[0]);
                        $(".reviewerStaffId").val(this[0]);
                        queryAccount(this[0]);
                    }
                });
                $(".regulators").append(a.join(""));
                //责任人
                var bb = [];
                $(list).each(function(i, dom) {
                    if(this[0]==obj[9]){
                        bb.push("<option date-staffId='"+this[0]+"' selected>"+this[1]+"</option>");
                    }else{
                        bb.push("<option date-staffId='"+this[0]+"'>"+this[1]+"</option>");
                    }
                    if(i==0){
                        $(".staffId").val(this[0]);
                        $(".reviewerStaffId").val(this[0]);
                        queryAccount(this[0]);
                    }
                });
                $(".staff").append(bb.join(""));
				//类型
                $(".sType").html("");
				var c = [];
				if(obj[7]==0){
                    c.push("<option data-status='0' selected>考场</option>");
                    c.push("<option data-status='1' >练习场</option>");
				}else{
                    c.push("<option data-status='0' >考场</option>");
                    c.push("<option data-status='1' selected>练习场</option>");
				}
                $(".sType").append(c.join(""));
				$("#fees_").show();
			},
			error : function(data) {
				alert("查询失败");
			}
		});
	}

    //查询微分金账号
    function queryAccount(obj){
        var url = basePath + "ea/carmanage/sajax_ea_queryAccount.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data :{
                "caccount.staffID":obj
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
				$(".wfjzh").val(standard.Account);
            },
            error : function(data) {
                alert("查询失败");
            }
        });
    }


	
	//删除场地
	function delExoom(obj){
		var url = basePath + "ea/carmanage/sajax_ea_delExoom.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"exRoom.erId":obj
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
    //启用考场
    $(".enable").click(function(){
        var obj = $(this).parents("tr").attr("id")
        var url = basePath + "ea/carmanage/sajax_ea_enableTheTest.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data :{
                "exRoom.erId":obj
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
                if(standard.boolean){
                    window.location.reload();
                }else{
                    alert("失败");
                }
            },
            error : function(data) {
                alert("失败");
            }
        });
    })
});
