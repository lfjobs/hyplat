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
				if (etvId == "") {
					alert('请选择!');
					return;
				}
                standard(etvId);
				break;
			case '删除' :
				if (etvId == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
                    delstandard(etvId);
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/aflovacation/ea_coachVeave.jspa?beginDate="+$("#time").val()+"&tbJpTeacher.name="+$("#clname").val()+"&tbJpTeacher.idcard="+$("#clidcard").val();
				numback(url);
				break;
		}
		//隐藏添加表单
		$(".tit").find("img").click(function(){
            $("#fees_ .etvKey").val("");
            $("#fees_ .etvId").val("");
            $("#fees_ .name").val("");
            $("#fees_ .sex").val("");
            $("#fees_ .idcard").val("");
            $("#fees_ .mobile").val("");
            $("#fees_ .beginDate").val("");
            $("#fees_ .endDate").val("");
            $("#fees_").hide();
		})
	}

	function addVacation() {
		$("#fees_").show();
    }



	//删除教练休班
	function delstandard(obj){
	    if(status=="01"){
            return alert("已生效,无法删除");
        }
		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxDelCoachVeave.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
                "elycTrainerVacation.etvId":obj
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
	
	//查询教练休班详情
	function standard(obj){
        if(status=="01"){
            return alert("已生效,无法修改");
        }
		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxCoachVeaveDetails.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
            data :{
                "elycTrainerVacation.etvId":obj
            },
			success : function(data) {
				var standard = eval("(" + data + ")");
				var object = standard.object;
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
                $("#fees_ .etvKey").val(object[0]);
                $("#fees_ .etvId").val(object[1]);
                $("#fees_ .name").val(object[2]);
                $("#fees_ .sex").val(object[3]);
                $("#fees_ .idcard").val(object[4]);
                $("#fees_ .mobile").val(object[5]);
                $("#fees_ .beginDate").val(object[6]);
                $("#fees_ .endDate").val(object[7]);
                $("#fees_").show();
			},
			error : function(data) {
				alert("获取失败");
			}
		});
	}
	//添加修改教练休班
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


		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxAddOrUpdateCoach.jspa";
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
                    alert("教练已排班无法休班");
                }else if(status=="03"){
                    alert("请勿重复添加休班信息");
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
        etvId = this.id;
        status = $(this).attr("data-status");
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});

	//根据时间查询
	$(".input-button").click(function(){
        ppageNumber = 0;
		var url = basePath
            + "/ea/aflovacation/ea_coachVeave.jspa?beginDate="+$("#time").val()+"&tbJpTeacher.name="+$("#clname").val()+"&tbJpTeacher.idcard="+$("#clidcard").val();
		document.location.href = url;
	})



    //查询教练员信息
    $(".btn_formTip").click(function(){
        enquiries();
        $("#coach").show();
        $("#fees_").hide();
    })
    //关闭教练员弹框
    $(".JQueryreturns").click(function(){
        cpageNumber = 0;
        cpageCount = 0;
        cname = "";
        $("#coach").hide();
        $("#fees_").show();
    })
    //上一页
    $("#xmsy").click(function(){
        if(cpageNumber>1){
            cpageNumber = cpageNumber-1;
            enquiries();
        }
    })
    //下一页
    $("#xmxy").click(function(){
        if(cpageNumber<cpageCount){
            cpageNumber = cpageNumber+1;
            enquiries();
        }
    })
    //查询
    $("#searchxmbtn").click(function(){
        cpageNumber = 0;
        cpageCount = 0;
        cname = $("#parameterxm").val();
        enquiries();
    })
    //确定
    $("#qdxm").click(function(){
        if($('input[name="teacherId"]:checked').val()!=null){
            cpageNumber = 0;
            cpageCount = 0;
            cname = "";

            $("#con .trainer_id").val($('input[name="teacherId"]:checked').val());
            $("#con .name").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thname").text());
            $("#con .sex").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thsex").text());
            $("#con .idcard").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thidcard").text());
            $("#con .mobile").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thmobile").text());
            $("#coach").hide();
            $("#fees_").show();
        }else{
            alert("请选择")
        }
    })
    //查询
    function enquiries() {
        var url = basePath + "/ea/aflovacation/sajax_ea_ajaxCoachList.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data :{
                "pageForm.pageNumber":cpageNumber,
                "tbJpTeacher.name":cname
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
                var nologin = standard.nologin;
                var pageForm = standard.pageForm;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
                $("#body_02xm").empty();
                var an = [];
                if(pageForm!=null){
                    $(pageForm.list).each(function(i, dom) {
                        an.push("<tr style='cursor: hand;'>");
                        an.push("<td id='checkcc' align='center'>");
                        an.push("<input type='radio' class='teacherId' value='"+this[0]+"' name='teacherId'>");
                        an.push("</td>");
                        an.push("<td align='center'>"+(i+1)+"</td>");
                        an.push("<td class='thname' align='center'>"+(this[1]==null?"":this[1])+"</td>");
                        an.push("<td class='thidcard' align='center'>"+(this[2]==null?"":this[2])+"</td>");
                        an.push("<td class='thmobile' align='center'>"+(this[3]==null?"":this[3])+"</td>");
                        an.push("<td class='thsex' align='center'>"+(this[4]==null?"":(this[4]=="1"?"男":"女"))+"</td>");
                        an.push("</tr>");
                    });
                    $("#body_02xm").append(an.join(""));
                    $("#xmzycount").text(pageForm.pageCount);
                    $("#parameterxm").val(cname);
                    cpageCount = pageForm.pageCount;
                    cpageNumber = pageForm.pageNumber;
                }
            },
            error : function(data) {
                alert("查询失败");
            }
        });
    }

});
