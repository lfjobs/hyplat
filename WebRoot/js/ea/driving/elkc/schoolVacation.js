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
						//
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						//
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
				addVacation();
				break;
			case '修改' :
				if (esrId == "") {
					alert('请选择!');
					return;
				}
                standard(esrId);
				break;
			case '删除' :
				if (esrId == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
                    delstandard(esrId);
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/aflovacation/ea_schoolVacation.jspa?beginDate="+$("#holidayBegin").val()+"&endDate="+$("#holidayEnd").val();
				numback(url);
				break;
		}
		//隐藏添加表单
		$(".tit").find("img").click(function(){
            $("#con .esrKey").val("");
            $("#con .esrId").val("");
            $("#con .beginDate").val("");
            $("#con .endDate").val("");
            $("#fees_").hide();
		})
	}

	function addVacation() {
		$("#fees_").show();
    }



	//删除休假
	function delstandard(obj){
	    if(status=="01"){
            return alert("已生效,无法删除");
        }
		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxDelLeaveThe.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"tbElycSchoolRest.esrId":obj
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
				if(standard.boolean){
					window.location.reload();
				}else{
					alert("该记录无法删除");
				}
			},
			error : function(data) {
				alert("删除失败");
			}
		});
	}
	
	//查询休假详情
	function standard(obj){
        if(status=="01"){
            return alert("已生效,无法修改");
        }
		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxLeaveTheDetails.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
            data :{
                "tbElycSchoolRest.esrId":obj
            },
			success : function(data) {
				var standard = eval("(" + data + ")");
				var object = standard.object;
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
                $("#fees_ .esrKey").val(object[0]);
                $("#fees_ .esrId").val(object[1]);
                $("#fees_ .beginDate").val(object[2]);
                $("#fees_ .endDate").val(object[3]);
                $("#fees_").show();
			},
			error : function(data) {
				alert("获取失败");
			}
		});
	}
	//添加修改驾校休假
	$(".sub").click(function(){
        $(".put3").trigger("blur");
        if($(".error").length>0){
            return;
        }
        var beginDate = $("#con .beginDate").val();
        var endDate = $("#con .endDate").val();
        var holdDate=new Date(beginDate.replace("-", "/").replace("-", "/"));
        var closingDate=new Date(endDate.replace("-", "/").replace("-", "/"));
        var mydate = new Date();
        if(holdDate <= mydate || closingDate <= mydate){
            return alert("请勿选择已过期时间");
        }
        if(holdDate > closingDate){
            return alert("结束时间必须大于开始时间");
        }


		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxAddOrUpdateLeaveThe.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data:$('#con').serialize(),// 你的formid
			success : function(data) {
				var standard = eval("(" + data + ")");
				var status = standard.status;
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				if(status=="00"){
					window.location.reload();
				}else if(status=="01"){
					alert("时间格式不正确");
				}else if(status=="02"){
                    alert("已有教练排班无法休假");
                }else if(status=="03"){
                    alert("请勿重复添加驾校休假信息");
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
        esrId = this.id;
        status = $(this).attr("data-status");
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});

	//根据时间查询
	$(".input-button").click(function(){
        ppageNumber = 0;
		var url = basePath
            + "/ea/aflovacation/ea_schoolVacation.jspa?beginDate="+$("#holidayBegin").val()+"&endDate="+$("#holidayEnd").val();
		document.location.href = url;
	})

});
