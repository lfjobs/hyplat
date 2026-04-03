$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.DtDrivingAppointmentRecordTable').flexigrid({
				height : 150,
				allDouble : true,
				width : 'auto',
				minwidth : 30,
				title: '预约培训记录',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '全部保存',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"dtDrivingAppointmentRecordmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;
			case '修改' :
				if (drivingappointmentrecordid == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingappointmentrecordid);
				if($.trim($p.find("span#isappointmentstatusHidden").text())=="01"){
					alert("该数据已“预约成功”不可修改!");
					return;
				}
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"dtDrivingAppointmentRecordmap[" + select + "]." + this.name);
				});

				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("a").removeClass("model1");
				$p.find("select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#submitCheGuanDate", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								alert("请输入日期");
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					notoken = 0;
					return;
				}
				$('#DtDrivingAppointmentRecordForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/appointment/ea_saveDtDrivingAppointmentRecord.jspa?");
				document.DtDrivingAppointmentRecordForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (drivingappointmentrecordid == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingappointmentrecordid);
				if($.trim($p.find("span#isappointmentstatusHidden").text())=="01"){
					alert("该数据已“预约成功”不可删除!");
					return;
				}
				if (drivingappointmentrecordid.substring(0, 2) == "sa") {
					$("#" + drivingappointmentrecordid).remove();
					drivingappointmentrecordid = "";
					return;
				}
				$f = $('#DtDrivingAppointmentRecordForm');
				if (notoken)
					return;
				notoken = 1;
				$f.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/appointment/ea_delDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingappointmentrecordid="
										+ drivingappointmentrecordid);
				document.DtDrivingAppointmentRecordForm.submit.click();
				$("tr#" + drivingappointmentrecordid).remove();
				drivingappointmentrecordid = "";
				token = 11;
				break;
			case '导出':
				var url = basePath
				+ "/ea/appointment/ea_showDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingprincipalid="
				+ drivingprincipalid+"&dtDrivingAppointmentRecord.docstatus="
				+ docstatus;
				open(url);
				break;
		}
	}

	$(".DtDrivingAppointmentRecordTable tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingappointmentrecordid = this.id;
	});
	$("input#startdate,input#startshuttledate").blur(function(){
    	$("input#startHour").val($(this).val());
    });
	$("input#enddate,input#endshuttledate").blur(function(){
    	$("input#endHour").val($(this).val());
    });
	
	$("input#startdate,input#enddate").live("blur focus",function(){
		var startdate;
		var enddate;
	})
	
	$(".DtDrivingAppointmentRecordTable tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingappointmentrecordid = this.id;
		action("修改");
	});
	$(".DtDrivingAppointmentRecordTable").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
	});
	
	/***
	 * @returns 选择教练员
	 */
	$("#xuanze").live("click",function(){
        $("#daoRu").attr("src", basePath + "ea/driving/ea_getCarInformationList.jspa?");
         $("#bankJqm").jqmShow();
    });
	
	$("#DaoRuFan").click(function(){// 返回
        $("#bankJqm").jqmHide();
    });
    $("#DaoRuFanqd").click(function(){// 选择确定
        var checkform = $("#checkform", $("#bankJqm")).attr("value");
        var carID = window.frames["daoRu"].carID;
        if (carID == "") {
            alert("请选择");
            return;
        }
        var staffName = window.frames["daoRu"].$('tr#' + carID).find("span#staffName").text();
        var carsID = window.frames["daoRu"].$('tr#' + carID).find("span#carID").text();
        var carNum = window.frames["daoRu"].$('tr#' + carID).find("span#carNum").text();
        var carType = window.frames["daoRu"].$('tr#' + carID).find("span#carType").text();
        var staffName1="";
        var staffNameCode="";
        if(staffName.indexOf('---')<0){
        	staffName1=staffName;
        }else{
        	 staffName1=(staffName.split("---"))[0];
             staffNameCode=(staffName.split("---"))[1];
        }
        $("input#coach", $("tr#" + drivingappointmentrecordid)).attr("value", staffName1).trigger("blur");
        $("input#coachcode", $("tr#" + drivingappointmentrecordid)).attr("value", staffNameCode).trigger("blur");
        $("input#carcode", $("tr#" + drivingappointmentrecordid)).attr("value", carNum).trigger("blur");
        
        $("input#carType", $("tr#" + drivingappointmentrecordid)).attr("value", carType).trigger("blur");
        $("input#carID", $("tr#" + drivingappointmentrecordid)).attr("value", carsID).trigger("blur");
        $("#daoRu").attr("src", "");
        $("#bankJqm").jqmHide();
    });
	
    /*********代码结束行********/
	
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/appointment/ea_getListDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingprincipalid="
				+ drivingprincipalid+"&dtDrivingAppointmentRecord.docstatus="
				+ docstatus;
}

/** **********************************选择人员人力资源数据**************************************** */
function getValueForParm(){ //打开页面
  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
  	$("#jqmWindow2").jqmShow();
}

$(document).ready(function() {
	$("#isBack").click(function(){// 返回
       $("#jqmWindow2").jqmHide();
    }); 
   
	$("#isSubmit").click(function(){// 选择确定
		var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择")
			return;
		}
		var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
		var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffID").text();//弹出框的页面存在于span中才取得到
		var value4 = window.frames["ifr"].$('tr#'+value1).find("span#reference").text();//弹出框的页面存在于span中才取得到
		
		$("#"+drivingappointmentrecordid).find("#shuttlestaff").val(value2);
		$("#"+drivingappointmentrecordid).find("#shuttlestaffid").val(value3);
		$("#"+drivingappointmentrecordid).find("#shuttlestaffphone").val(value4);
		$("#ifr").attr("src","");
        $("#jqmWindow2").jqmHide();
    });
});

/** **********************************选择物品**************************************** */
$(document).ready(function() {
	var cuID = "";
	$("table#gotable tr[id]").live("click", function(event) {
				cuID = this.id;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	//物品返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				$(":input#parms").val("");
				$(".jqmWindow", $("#goodsForm")).jqmHide();
	})	;
	// 导入数据（选择物品）;
		$("#newG").live("click",function() {
				cx("");
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	// 根据车牌号查询
	$("#chaxun").click(function() {
				//var typeName = $("#carNum", $("table#searchgood")).val();
				//var typeJia=$("#carFrameNum", $("table#searchgood")).val();
				var typeType=$.trim($("#carType", $("table#searchgood")).val());
				$(":input#parms").val("typeType="+typeType);
				cx("typeType="+typeType);
			});
	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy").click(function() {
				var xy = $("#wpxy").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx(typeCN);
				} else {
					alert("已是尾页！");
				}
			});	
		$("#qdcar").click(function() {
		if (cuID != "") {
			$("tr #" + cuID).children("td").each(function() {
				if (this.id == "carNum") {
					$("input#shuttlecarnumber", $("tr#"+drivingappointmentrecordid)).val($(this).text());
				}
				if (this.id == "carType") {
					$("input#shuttlecarxinhao", $("tr#"+drivingappointmentrecordid)).val($(this).text());
				}	
			});
			cuID="";
			$(".jqmWindow", $("#goodsForm")).jqmHide();
		} else {
			alert("请选择车辆！");
		}
	});
	
	function cx(typeCN){
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy").attr("title", 0);
		$("#wpxy").attr("title", 0);
		$("#wpzy").attr("title", 0);
		$("span#wpzycount").text(0);
		var searchurl = basePath
				+ "ea/car/sajax_ea_getcarnull.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN+ "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				
				var pageForm = member.pageForm;
				if (pageForm == null) {
					$("#body_02").html("");
					notoken = 0;
					return;
				}
				
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#wpsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy").attr("title", dqy + 1);
				}
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车牌号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>发动号</th><th align='center' bgcolor='#E4F1FA'>车辆类型</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车架号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>注册日期</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].carID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
							+ pageForm.list[i].carID + " name='check'/></td>";
					tabletr += "<td id='carNum' align='center'>"
							+ pageForm.list[i].carNum + "</td>";
					tabletr += "<td id='engineNum'  align='center'>"
							+ pageForm.list[i].engineNum + "</td>";
					tabletr += "<td id='carType'  align='center'>"
							+ pageForm.list[i].carType + "</td>";
					tabletr += "<td id='carFrameNum' align='center'>"
							+ pageForm.list[i].carFrameNum + "</td>"	;
					tabletr += "<td id='registrationDate'  align='center'>"
							+ pageForm.list[i].registrationDate + "</td>";
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].carID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02").html(tabletr);
				$("#body_02").show();
				
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}

});