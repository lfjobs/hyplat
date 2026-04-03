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
				}, /*{
					name : '修改',
					bclass : 'edit',
					onpress : action
						//
					}, {
					separator : true
				}, */{
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
				if (etlid == "") {
					alert('请选择!');
					return;
				}
                standard(etlid);
				break;
			case '删除' :
				if (etlid == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
                    delstandard(etlid);
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/aflovacation/ea_coachAsksForLeave.jspa?beginDate="+$("#time").val()+"&tbJpTeacher.name="+$("#clname").val()+"&tbJpTeacher.idcard="+$("#clidcard").val();
				numback(url);
				break;
		}
		//隐藏添加表单
		$(".tit").find("img").click(function(){
            $("#fees_ .etlKey").val("");
            $("#fees_ .etlId").val("");
            $("#fees_ .trainer_id").val("");
            $("#fees_ .relay_trainer_id").val("");
            $("#fees_ .name").val("");
            $("#fees_ .sex").val("");
            $("#fees_ .idcard").val("");
            $("#fees_ .mobile").val("");
            $("#fees_ .data").val("");
            $("#fees_ .substitutename").val("");
            $("#fees_").hide();
		})
	}
    //
	function addVacation() {
		$("#fees_").show();
    }



	//删除教练请假
	function delstandard(obj){
	    if(status=="01"){
            return alert("已生效,无法删除");
        }
		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxDelCafLeave.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
                "elycTrainerLeave.etlId":obj
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
	
	//查询教练请假详情
	function standard(obj){
        if(status=="01"){
            return alert("已生效,无法修改");
        }
		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxCafLeaveDetails.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
            data :{
                "elycTrainerLeave.etlId":obj
            },
			success : function(data) {
				var standard = eval("(" + data + ")");
				var object = standard.object;
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
                $("#fees_ .etlKey").val(object[0]);
                $("#fees_ .etlId").val(object[1]);
                $("#fees_ .name").val(object[2]);
                $("#fees_ .sex").val(object[3]);
                $("#fees_ .idcard").val(object[4]);
                $("#fees_ .mobile").val(object[5]);
                $("#fees_ .leaveData").val(object[6]);
                $("#fees_ .beginDate").val(object[7]);
                $("#fees_ .endDate").val(object[8]);
                $("#fees_ .relay_trainer_id").val(object[9]);
                $("#fees_ .substitutename").val(object[10]);
                $("#fees_ .trainer_id").val(object[11]);
                $(".substitutename").parent("div").parent(".mil").hide();
                $(".substitutename").removeClass("put3");
                $("#fees_").show();
			},
			error : function(data) {
				alert("获取失败");
			}
		});
	}
	//添加修改教练请假
	$(".sub").click(function(){

        var leaveData = $("#con .leaveData").val();
        var beginDate = $("#con .beginDate").val();
        var endDate = $("#con .endDate").val();
        var trainer_id = $("#con .trainer_id").val();
        if(trainer_id.trim()!=""){
            if(leaveData.trim()!="" && beginDate.trim()!="" && endDate.trim()!=""){
                var leave=new Date(leaveData.replace("-", "/").replace("-", "/"));
                var hold=new Date(beginDate.replace("-", "/").replace("-", "/"));
                var closing=new Date(endDate.replace("-", "/").replace("-", "/"));
                var mydate = new Date();
                var result = DateDiff(leaveData.split(" ")[0],beginDate.split(" ")[0]);
                if(hold <= mydate || closing <= mydate || leaveData<= mydate){
                    return alert("请勿选择已过期时间");
                }else if(result>1){
                    return alert("申请日期必须在开始时间的前一天或当天申请");
                }else if(leave >= hold){
                    return alert("开始时间必须大于申请日期");
                }else if(hold >= closing){
                    return alert("结束时间必须大于开始时间");
                }else if(beginDate.split(" ")[0] != endDate.split(" ")[0]){
                    return alert("开始时间与结束时间必须为同一天");
                }
            }else{
                return alert("请选择日期");
            }
        }else{
            return alert("请选择教练");
        }

        $(".put3").trigger("blur");
        if($(".error").length>0){
            return;
        }
		var url = basePath + "/ea/aflovacation/sajax_ea_ajaxAddOrUpdateAskForLeave.jspa?substitutename="+$('#con').find(".substitutename").val();
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
                    alert("请勿重复添加请假信息");
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
        etlid = this.id;
        status = $(this).attr("data-status");
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});

	//根据时间查询
	$(".input-button").click(function(){
        pageNumber = 0;
		var url = basePath
            + "/ea/aflovacation/ea_coachAsksForLeave.jspa?beginDate="+$("#time").val()+"&tbJpTeacher.name="+$("#clname").val()+"&tbJpTeacher.idcard="+$("#clidcard").val();
		document.location.href = url;
	})

    $(".data").blur(function(){
        $("#con .relay_trainer_id").val("");
        $("#con .substitutename").val("");
        $(".substitutename").parent("div").parent(".mil").hide();
        $(".substitutename").removeClass("put3");

        var trainerid = $(".trainer_id").val();
        var beginDate = $(".beginDate").val();
        var endDate = $(".endDate").val();
        if($.trim(trainerid)!="" && $.trim(beginDate)!="" && $.trim(endDate)!=""){
            ajaxIsAbout(trainerid,beginDate,endDate);
        }
    });
    //查询已约数
    function ajaxIsAbout(trainerid,beginDate,endDate){
        var url = basePath + "/ea/aflovacation/sajax_ea_ajaxIsAbout.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data:{
                "tbJpTeacher.teacherId":trainerid,
                "beginDate":beginDate,
                "endDate":endDate
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
                var count = standard.count;
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
                if(count>0){
                    $(".substitutename").parent("div").parent(".mil").show();
                    $(".substitutename").addClass("put3");
                }
            },
            error : function(data) {
                alert("查询失败");
            }
        });
    }


    //查询教练员信息
    $(".btn_formTip").click(function () {
        sstatus = $(this).attr("data-status");
        enquiries();
        $("#coach").show();
        $("#fees_").hide();
    })
    //查询休班教练员信息
    $(".substitute").click(function () {
        sstatus = $(this).attr("data-status");
        var leaveData = $("#con .leaveData").val();
        var beginDate = $("#con .beginDate").val();
        var endDate = $("#con .endDate").val();
        var trainer_id = $("#con .trainer_id").val();
        if(trainer_id.trim()!=""){
            if(leaveData.trim()!="" && beginDate.trim()!="" && endDate.trim()!=""){
                var leave=new Date(leaveData.replace("-", "/").replace("-", "/"));
                var hold=new Date(beginDate.replace("-", "/").replace("-", "/"));
                var closing=new Date(endDate.replace("-", "/").replace("-", "/"));
                var mydate = new Date();
                var result = DateDiff(leaveData.split(" ")[0],beginDate.split(" ")[0]);
                if(hold <= mydate || closing <= mydate || leaveData<= mydate){
                    return alert("请勿选择已过期时间");
                }else if(result>1){
                    return alert("申请日期必须在开始时间的前一天或当天申请");
                }else if(leave >= hold){
                    return alert("开始时间必须大于申请日期");
                }else if(hold >= closing){
                    return alert("结束时间必须大于开始时间");
                }else if(beginDate.split(" ")[0] != endDate.split(" ")[0]){
                    return alert("开始时间与结束时间必须为同一天");
                }
            }else{
                return alert("请选择日期");
            }
        }else{
            return alert("请选择教练");
        }

        enquiries();
        $("#coach").show();
        $("#fees_").hide();
    })
    //根据时间获取相差的天数
    function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是yyyy-MM-dd格式
        var aDate, oDate1, oDate2, iDays;

        aDate = sDate1.split("-");

        oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式

        aDate = sDate2.split("-");

        oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);

        iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数

        return iDays;  //返回相差天数
    }


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
            if(sstatus=="00"){
                $("#con .trainer_id").val($('input[name="teacherId"]:checked').val());
                $("#con .name").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thname").text());
                $("#con .sex").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thsex").text());
                $("#con .idcard").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thidcard").text());
                $("#con .mobile").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thmobile").text());
                var trainerid = $(".trainer_id").val();
                var beginDate = $(".beginDate").val();
                var endDate = $(".endDate").val();
                if($.trim(trainerid)!="" && $.trim(beginDate)!="" && $.trim(endDate)!=""){
                    ajaxIsAbout(trainerid,beginDate,endDate);
                }
            }else if(sstatus=="01"){
                $("#con .relay_trainer_id").val($('input[name="teacherId"]:checked').val());
                $("#con .substitutename").val($('input[name="teacherId"]:checked').parent("td").parent("tr").find(".thname").text());
            }
            $("#coach").hide();
            $("#fees_").show();
        }else{
            alert("请选择")
        }
    })
    //查询
    function enquiries() {
        var dt;
	    if(sstatus=="00"){
            var url = basePath + "/ea/aflovacation/sajax_ea_ajaxCoachList.jspa";
            dt = {
                "pageForm.pageNumber":cpageNumber,
                "tbJpTeacher.name":cname
            }
        }else if(sstatus=="01"){
            var url = basePath + "/ea/aflovacation/sajax_ea_ajaxOffDutyCoachList.jspa";
            var beginDate = $("#con .beginDate").val();
            var trainer_id = $("#con .trainer_id").val();
            dt = {
                "pageForm.pageNumber":cpageNumber,
                "tbJpTeacher.name":cname,
                "beginDate":beginDate,
                "tbJpTeacher.teacherId":trainer_id
            }
	    }
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data : dt,
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
