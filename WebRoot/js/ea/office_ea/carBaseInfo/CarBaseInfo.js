$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	if(showType=='add'){
		$("form#box1Form").find(".isHide").removeClass("isHide");
		$("span.statusinfo").hide();
		$("a.mord").hide();
	}else{
		$("input#newG").hide();
		$("tr#tools").hide();
	}
	$("select#staus").change(function() {// 信息类型转变
				if($(this).val()=='00'){//确定人员信息
					$("#staffIdentityCard").addClass("put4");
				}else{//不确定人员信息
					$("#staffIdentityCard").removeClass("put4");
					$("#staffIdentityCard").removeClass("newerror");
				}
	});
	
	$("input",$(".navMenu")).click(function(){
		var classVar=$(this).attr("class")
		if(classVar=="all"){
			$("div.gdkd table").show();
			$("div.gdkd > div").hide();
		}else{
			$("div.gdkd table.biaoti").hide();
			$("div.gdkd > div").hide();
			$("div.gdkd table."+classVar).show();
		}
	})
	//----------------选择在职人员操作------------//
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").hide();
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择");
			return;
		}
		var staffName = $(window.frames["daoRu"].$('tr#'+childopertionID)[0]).find("span#staffName").text();
		var staffIdentityCard =$(window.frames["daoRu"].$('tr#'+childopertionID)[0]).find("span#staffIdentityCard").text();
		var reference =$(window.frames["daoRu"].$('tr#'+childopertionID)[0]).find("span#reference").text();//mainframe4
		$t=$("input.JQuerypersonvalue:checked",window.frames["mainframe53"].document).parents("tr");
		$t.find("input#referrer").attr("value",staffName);
		$t.find("input#referrerID").attr("value",childopertionID);
		$t.find("input#referrerPhone").attr("value",reference);
		$t.find("input#referrerIdentityCard").attr("value",staffIdentityCard);
		 $("#daoRu").attr("src","");
         $("#bankJqm").hide();
   });
});

function toSave(formID, url) { // 保存
	$(".put4", 'form#'+formID).trigger("blur");	
	if ($(".newerror", 'form#'+formID).length) {
		retoken = 0;
		alert('请完善所有信息');
		return;
	}
	$("form#"+formID).attr("action",basePath+url);
	$("form#"+formID).submit();
}
//第一个参数为表单id，第二个参数为 table表头id
function changemenu(divid,menuid,opetype){
	if(opetype=='edit'){
		$("div#"+divid).find("span.isShow").removeClass("isShow").addClass("isHide");
		$("div#"+divid).find("input.isHide").removeClass("isHide").addClass("isShow");
		$("table.box"+menuid).find("a#mord"+menuid+"_close").removeClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid).addClass("isHide");
		switch(menuid){
			case 1:
				$("select#nation").show();
				$("select#nativePlace").show();
				$("select#staus").show();
				$("span.statusinfo").hide();
				$("input#staffIdentityCard").hide();
				$("span.staffIdentityCard").show();
				$("input#ReadCardBtn").hide();
				$("tr#tools").show();
				break;
			default:
				$("div#box"+menuid).show();
				//var personvalue = $("#relationID").val();
//				if (staffID == '') {
//					alert("请保存人员基本信息！");
//					return;
//				}changemenu('box3',3,'edit')
				var personurl = basePath + $("#mainframe"+menuid).attr("url");
				$("#mainframe"+menuid).attr("src", personurl + carID);
				break;
		}
	}else if(opetype=='close'){
		$("div#"+divid).find("span.isHide").removeClass("isHide").addClass("isShow");
		$("div#"+divid).find("input.isShow").removeClass("isShow").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid+"_close").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid).removeClass("isHide");
		$("tr#tools").hide();
		if(menuid==1){
			$("select#nation").hide();
			$("select#nativePlace").hide();
			$("select#staus").hide();
			$("span.statusinfo").show();
		}else{
			$("div#box"+menuid).hide();
		}
	}
}

function re_load() {
	if (token)
		document.location.href = basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+$("input#contactUserID", $("table#stafftable")).val();
}
/** **********************************选择人员信息**************************************** */
$(document).ready(function() {
	var cuID = "";
	$("table#gotable tr[id]").live("click", function(event) {
				cuID = this.id;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 导入数据（选择人员）
		$("#newG").click(function() {
				cx("");
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	// 根据社会人力查询
	$("#chaxun").click(function() {
				var typeName = $("#carNum", $("table#searchgood")).val();
				var typeType=$("#carType", $("table#searchgood")).val();
				$(":input#parms").val("parameter=" + typeName+"&typeType="+typeType);
				cx("parameter=" + typeName+"&typeType="+typeType);
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
	//报名人员提交保存
	$("input.JQuerySubmit").click(function() {
				if($("#staffName",$("#box1Form")).text() == ''){
					alert("请选择人员！");
					return;
				}
				$("#box1Form").attr("target", "hidden")
								.attr(
									"action",
									basePath
											+ "ea/enroll/ea_savepeople.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.box1Form.submit.click();
					document.box1Form.reset();
					$("tr#tools").hide();
					$("a.mord").show();
					token = 2;
					return;
			});
	//选择人员信息关闭
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				var numes="";
				$("#staffName", $("table#searchgood")).attr("value",numes);
				$("#staffIdentityCard", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			});
		$("#qdpeople").click(function() {
		if (cuID != "") {
			$("tr #" + cuID).children("td").each(function() {
				
				if (this.id == "contactUserID") {
					$("input#contactUserID", $("table#stafftable")).val($(this)
							.text());
				} else {
					$("span#" + this.id, $("table#stafftable"))
							.text($(this).text());
				}
			});
			cuID="";
			$(".jqmWindow", $("#goodsForm")).jqmHide();
		} else {
			alert("请选择人员！");
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
		var searchurl = basePath+ "ea/enroll/sajax_n_ea_getpeople.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN+ "&date="+ new Date().toLocaleString()),
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
					alert("没有数据");
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
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>员工编号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>档案编号</th><th align='center' bgcolor='#E4F1FA'>姓名</th><th align='center' bgcolor='#E4F1FA'>曾用名</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>性别</th><th align='center' bgcolor='#E4F1FA'>出生日期</th><th align='center' bgcolor='#E4F1FA'>国籍</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>民族</th><th align='center' bgcolor='#E4F1FA'>籍贯</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>护照号</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].staffID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
							+ pageForm.list[i].staffID + " name='check'/></td>";
					tabletr += "<td id='staffCode' align='center'>"
							+ pageForm.list[i].staffCode + "</td>";
					tabletr += "<td id='recordCode'  align='center'>"
							+ pageForm.list[i].recordCode + "</td>";
					tabletr += "<td id='staffName'  align='center'>"
							+ pageForm.list[i].staffName + "</td>";
					tabletr += "<td id='usedNmae'  align='center'>"
							+ pageForm.list[i].usedNmae + "</td>";
					tabletr += "<td id='sex' align='center'>"
							+ pageForm.list[i].sex + "</td>"	;
					tabletr += "<td id='birthday' align='center'>"
							+ pageForm.list[i].birthday + "</td>"	;
					tabletr += "<td id='nationality' align='center'>"
							+ pageForm.list[i].nationality + "</td>"	;
					tabletr += "<td id='nation' align='center'>"
							+ pageForm.list[i].nation + "</td>"	;
					tabletr += "<td id='nativePlace' align='center'>"
							+ pageForm.list[i].nativePlace + "</td>"	;
					tabletr += "<td id='staffIdentityCard' align='center'>"
							+ pageForm.list[i].staffIdentityCard + "</td>"	;
					tabletr += "<td id='passportNum' align='center'>"
							+ pageForm.list[i].passportNum + "</td>"	;
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].staffID + "</td>";
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
