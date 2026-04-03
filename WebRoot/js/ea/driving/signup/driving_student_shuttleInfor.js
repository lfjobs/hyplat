$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe7").style.height = 80 + len * 27 + "px";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.contact').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"studentRegInformap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe7").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe7").style.height = heis;
				select++;
				break;

			case '修改' :
				if (drivingAllInformationID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingAllInformationID);
				if ($p.hasClass("check")) {
					return;
				}

				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"studentRegInformap[" + select + "]." + this.name);
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
				$("#shuttleDate,#endShuttleDate,#shuttleStaffPhone,#shuttleSumPeople", $(".check")).each(function(i, tmp) {
					if (this.value == "") {
						alert("不能为空");
						$(this).css("background-color", "red");
						re = 1;
					}
					if(this.value!= null&&(this.id=="shuttleStaffPhone"||this.id=="shuttleSumPeople")&&isNaN(this.value)){
						alert("请输入整数");
						$(this).css("background-color", "red");
						re = 1;
					}
					if (this.value!=null&&this.value.length>30) {
						alert("最多可输入30个字");
						$(this).css("background-color", "red");
						re = 1;
					}
				});
				if (re) {
					notoken = 0;
					return;
				}
				$('#contactForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/enroll/ea_saveStudentRegInfors.jspa?");
				document.contactForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (drivingAllInformationID == '') {
					alert("请选择！");
					return;
				}
				if (drivingAllInformationID.substring(0, 2) == "sa") {
					$("#" + drivingAllInformationID).remove();
					drivingAllInformationID = '';
					var heis = parent.document.getElementById("mainframe7").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe7").style.height = heis;
					return;
				}
				$f = $('#contactForm');
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/enroll/ea_deleteStudentRegInfors.jspa?dtDrivingAllInformation.drivingAllInformationID="
										+ drivingAllInformationID);
				document.contactForm.submit.click();
				$("tr#" + drivingAllInformationID).remove();
				drivingAllInformationID = "";
				token = 11;
				break;
		}
	}

	$(".contact tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingAllInformationID = this.id;
	});
	
	$(".contact tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingAllInformationID = this.id;
		action("修改");
	});

	$(".contact").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});
	//物品返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				$(":input#parms").val("");
				$(".jqmWindow", $("#goodsForm")).jqmHide();
				parent.document.getElementById("mainframe7").style.height = mainheught + 'px';
	})	;
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/enroll/ea_getStudentShuttleList.jspa?dtDrivingAllInformation.dataTitle=07&dtDrivingAllInformation.staffID="+ staffID+"&dtDrivingAllInformation.relationID="+relationID;
}

/** **********************************选择物品**************************************** */
$(document).ready(function() {
	var cuID = "";
	$("table#gotable tr[id]").live("click", function(event) {
				cuID = this.id;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 导入数据（选择物品）;
		$("#newG").live("click",function() {
				cx("");
				$(".jqmWindow", $("#goodsForm")).jqmShow();
				mainheught = parent.document.getElementById("mainframe7").offsetHeight;
				parent.document.getElementById("mainframe7").style.height = 400 + 'px';
			});
	// 根据车牌号查询
	$("#chaxun").click(function() {
				var typeName = $.trim($("#carNum", $("table#searchgood")).val());
				var typeJia=$.trim($("#carFrameNum", $("table#searchgood")).val());
				var typeType=$.trim($("#carType", $("table#searchgood")).val());
				$(":input#parms").val("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
				cx("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
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
					$("input#shuttleCarNumber", $("tr#"+drivingAllInformationID)).val($(this).text());
				}
				if (this.id == "carType") {
					$("input#shuttleCarXinHao", $("tr#"+drivingAllInformationID)).val($(this).text());
				}	
			});
			cuID="";
			$(".jqmWindow", $("#goodsForm")).jqmHide();
			parent.document.getElementById("mainframe7").style.height = mainheught + 'px';
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