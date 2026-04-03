var choosetype;
$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
/******修改项目加载赋值责任人和人员-----开始----------*/
var smpid1="";
var smpid2="";
$("span[name='smpid1']").each(function(){//遍历
	   smpid1=smpid1+$(this).text();
      })
$("span[name='smpid2']").each(function(){//遍历
	   smpid2=smpid2+$(this).text();
      })
$("input#smpid1").val(smpid1);
$("input#smpid2").val(smpid2);
/******修改项目加载赋值责任人和人员-------结束--------*/
  //编号--------------------开始-----------------
    //项目编号根据后台自动生成
    if (proid == "") 
 	{
	$("input#code1").val(code1);
	$("input#code2").val(code2);
	var allcode=code1+"-"+code2;
	$("input#procode").val(allcode);
	}
	//项目编号还原重置
	$("a#editor").click(function() {
		if (proid == "") 
 	      {
	       $("input#code1").val(code1);
	       $("input#code2").val(code2);
	       var allcode=code1+"-"+code2;
	       $("input#procode").val(allcode);
	      }
	});
	//项目所有编号数据
	function getprocode(){
	     var url = basePath + "ea/projectmanager/sajax_ea_getProjectCode.jspa?date="
				+ new Date();
			 var str="";
			 $.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var prods = member.prolist;
					for (var i = 0; i < prods.length; i++) {
						var prod = prods[i];
						str+=prod.procode+",";
					}
				},
		        error:function(data){
			      alert("数据加载失败");
		        }
			});
			return str;
	}
  //编号--------------------结束-----------------
//==============================加载项目任务开始=========================================================
//修改项目时限制删除已经有任务的人员。
	function gettaskstaffid(){
		    var url = basePath + "ea/projectmanager/sajax_ea_getTaskByproid.jspa?date="
				+ new Date();
			var str = ""; 	
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"Proid" : proid
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var tasks = member.tasklist;
					for (var i = 0; i < tasks.length; i++) {
						var task = tasks[i];
						str+=task.staffid+",";
					}
				},
		        error:function(data){
			      alert("数据加载失败");
		        }
			});
			return str;
	}
//===================================加载项目任务结束=============================================
	//返回
	$("input.JQueryClose").click(function() {
			var url = basePath
			+ "ea/projectmanager/ea_getDtMyprojectList.jspa?pageNumber="+ pNumber + "&date=" + new Date();
	        document.location.href = encodeURI(url);
			});
	// 提交保存
	$("input.JQuerySubmit").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		$(".put3").trigger("blur");
		$(".isNaN").trigger("blur");
		if ($("#projectAddForm .error").length) {
			notoken = 0;
			return;
		 }
		    //启动时间和计划完成时间的判断日期大小开始
		    var startdate =$("input#startdate").val();
            var planfinishdate =$("input#planfinishdate").val();
		    //startdate,planfinishdate格式为yyyy-MM-dd 
		    var a1=startdate.split("-");
		    var b1=planfinishdate.split("-");
		    var a2=new Date(a1[0],a1[1],a1[2]);
		    var b2=new Date(b1[0],b1[1],b1[2]);
		    if(Date.parse(a2)-Date.parse(b2)>0)
		     {
		      alert("启动时间和计划完成时间不合理！");
		      return;
		     }
		   if(Date.parse(a2)-Date.parse(b2)==0)
		     {
		      alert("启动时间和计划完成时间不合理！");
		      return;
		     }
			//启动时间和计划完成时间的判断日期大小结束
		    //选择人员的判断
			var smp1=$("span#smp1").text();
			var smp2=$("span#smp2").text();
			if(smp1==""){
		    $("span#managererror").html("<image src='"+basePath+"images/ea/login/cuo_01.gif'/> <span style='color:red;'>负责人必选</span>");
		    return;
			}else if(smp1!="")
			{$("span#managererror").html("<image src='"+basePath+"images/ea/login/dui_01.gif'/>");}
			if(smp2==""){
		    $("span#membererror").html("<image src='"+basePath+"images/ea/login/cuo_01.gif'/> <span style='color:red;'>成员必选</span>");
		    return;
			}else if(smp2!="")
			{$("span#membererror").html("<image src='"+basePath+"images/ea/login/dui_01.gif'/>");}
		   //判断项目编号重复
			if(type=="add"){
			var a=$("input#code1").val();
	        var b=$("input#code2").val();
	        var cod=""
		    if(a!=""&&b!=""){
			    cod=a+"-"+b;
			    $("input#procode").val(cod);
		    }
		    var cc=getprocode();
		    var xx=0;
	        for(var j=0;j<cc.split(",").length;j++){
			    var c=cc.split(",")[j];
				if(cod==c){
					xx+=1;
				}
		    }
		      if(xx==1){
		        alert("项目编号重复！");
		        return;
		      }
				
			}
	       
		if (proid == "") {
			$("#projectAddForm").attr("target","main")
					.attr(
							"action",
							basePath
									+ "ea/projectmanager/ea_saveDtMyproject.jspa?type="+type+"&pageNumber="
									+ pNumber + "&date=" + new Date());
			document.projectAddForm.submit.click();
			document.projectAddForm.reset();
			$("span#smp1").text("");
			$("span#smp2").text("");
			token = 2;
			return;
		}
		var addtime=$("input#addtime").val();
		$("#projectAddForm").attr("target","main").attr(
						"action",basePath
								+ "ea/projectmanager/ea_saveDtMyproject.jspa?type="+type+"&addtime="+addtime+"&pageNumber="
								+ pNumber + "&date=" + new Date());
		document.projectAddForm.submit.click();
		token = 2;
	});
//组织机构数加载数据--------------------------------开始----------------------------------------------------
	var url1 = basePath
			+ "ea/office/sajax_ea_getOrganizationList.jspa?organizationID="
			+ encodeURI(treeid) + "&datesete=" + new Date();
	$.ajax({
				url : url1,
				type : "get",
				dataType : "json",
				success : function cbf(data) {

					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var oList = member.organizationList;
					var data = new Array();
					data[0] = {
						id : treeid,
						pid : '-1',
						durl : 0,
						text : companyName,
						str : '00'
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName,
							str : oList[i].status

						};
					}
					parentMenu(treeid, data);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	//双击备选人员
	$("#leftfields").dblclick(function() {
				var left_vo, right_vo, vos, i,j;
				vos = document.getElementsByName('leftfields');
				if (vos == null)
					return false;
				left_vo = vos[0];
				vos = document.getElementsByName('rightfields');
				if (vos == null)
					return false;
				right_vo = vos[0];
				for (i = 0; i < left_vo.options.length; i++) {
				    if (left_vo.options[i].selected) {
				    	   var left_value=left_vo.options[i].value.split("-")[0];
				    	   for (j = 0; j < right_vo.options.length; j++) {
							   var right_value=right_vo.options[j].value.split("-")[0];
							   if(left_value==right_value)
							   {
								   	 alert("人员已经选择，不能重复！");
								   	 return false;
						       }
						    }
							var no = new Option();
							no.value = left_vo.options[i].value;
							no.text = left_vo.options[i].text;
							right_vo.options[right_vo.options.length] = no;
						}
				}
				
				
				// 设为要可选状态
				for (i = 0; i < right_vo.options.length; i++) {
					right_vo.options[i].selected = true;
				}

				return true;
			});
    //双击已选人员
	$("#rightfields").dblclick(function() {
				var vos, right_vo, i;
				vos = document.getElementsByName('rightfields');
				if (vos == null)
					return false;
				right_vo = vos[0];
				for (i = right_vo.options.length - 1; i >= 0; i--){
					if (right_vo.options[i].selected) {
						var stid1=right_vo.options[i].value.split("-")[0];
						if(choosetype=="member"){   
				            var taskstaffids= gettaskstaffid();
							for(var j=0;j<taskstaffids.split(",").length-1;j++){
							      var stid2=taskstaffids.split(",")[j];
							      if(stid1==stid2)
							      {
							      alert("人员已经分配任务不能替换！");
							      return false;
							      }
							}
						}
					    right_vo.options.remove(i);
					}
				}
				// 设为要可选状态
				for (i = 0; i < right_vo.options.length; i++) {
					right_vo.options[i].selected = true;
				}
				return true;
			});
	//确定选择人员
	$("#query_add").click(function() {
				$("#leftfields").dblclick();
			});
   //删除已经选的人员
	$("#query_delete").click(function() {
				$("#rightfields").dblclick();
			});
//组织机构数加载数据-----------------------结束------------------------------
});
//选择责任人
function selectPeople(ctype) {
	choosetype=ctype;
    if(choosetype=="manager"){
		if($("input#smpid1").val()!=""){
			var resultID=$("input#smpid1").val().split(",");
			var resultName=$("span#smp1").text().split(";");
			var right_vo, vos,f;
			vos = document.getElementsByName('rightfields');
			if (vos == null)
					return false;
			right_vo = vos[0];
			for(var i=0,j=0;i<resultID.length-1,j<resultName.length-1;i++,j++){
				var no = new Option();
				no.value = resultID[i];
				no.text = resultName[j];
				right_vo.options[right_vo.options.length] = no;
			}	
			// 设为要可选状态
			for (f = 0; f < right_vo.options.length; f++) {
				right_vo.options[f].selected = true;
			}
			
		}
	}else if(choosetype=="member"){
		if($("input#smpid2").val()!=""){
				var resultID=$("input#smpid2").val().split(",");
				var resultName=$("span#smp2").text().split(";");
				var right_vo, vos,f;
				vos = document.getElementsByName('rightfields');
				if (vos == null)
						return false;
				right_vo = vos[0];
				for(var i=0,j=0;i<resultID.length-1,j<resultName.length-1;i++,j++){
					var no = new Option();
					no.value = resultID[i];
					no.text = resultName[j];
					right_vo.options[right_vo.options.length] = no;
				}	
				// 设为要可选状态
				for (f = 0; f < right_vo.options.length; f++) {
					right_vo.options[f].selected = true;
				}
				
			}
	}
    $("#zj").jqmShow();
}

//选择人员确定
function submit() {
	var lengths = document.getElementById("rightfields").options.length;// 下拉项的长度
	var strid = "";
	var strname = "";
	var flag = true;
	for (var i = 0; i < lengths; i++) {
		flag = true;
		for (var j = i + 1; j < lengths; j++) {
			var stri = document.getElementById("rightfields").options[i].value;
			var strj = document.getElementById("rightfields").options[j].value;
			if (stri == strj) {
				flag = false;
				break;
			}
		}
		if (flag == true) {
			strid += document.getElementById("rightfields").options[i].value;
			strid += ",";
			strname += document.getElementById("rightfields").options[i].text;
			strname += ";";

		}
	}
	if(choosetype=="manager"){
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("input#smpid1").val(strid);
	$("span#smp1").text(strname);
	$("span[name='smpid1']").each(function(){//遍历
	   $(this).remove();
      })
    //判断是否选责
        var smpp1=$("span#smp1").text();
        if(smpp1==""){
		$("span#managererror").html("<image src='"+basePath+"images/ea/login/cuo_01.gif'/> <span style='color:red;'>负责人必选</span>");
		}else if(smpp1!="")
		{$("span#managererror").html("<image src='"+basePath+"images/ea/login/dui_01.gif'/>");}
	}else if(choosetype=="member"){
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("input#smpid2").val(strid);
	$("span#smp2").text(strname);
	$("span[name='smpid2']").each(function(){//遍历
	   $(this).remove();
      })
    //判断是否选责
        var smpp2=$("span#smp2").text();
        if(smpp2==""){
		    $("span#membererror").html("<image src='"+basePath+"images/ea/login/cuo_01.gif'/> <span style='color:red;'>成员必选</span>");
		}else if(smpp2!="")
		{$("span#membererror").html("<image src='"+basePath+"images/ea/login/dui_01.gif'/>");}
	}
	$("#zj").jqmHide();
}
//树形机构菜单
function parentMenu(companyID, vals) {// 1级
	result += "<ul id='browser' class='filetree'><li title='"
			+ vals[0].text
			+ "' id='"
			+ vals[0].id
			+ "' class='curor'><a><span class='folder' onclick='javascript:getPerson(\""
			+ companyID + "\",\"1\",\"company\")'>" + vals[0].text
			+ "</span></a><ul id='child'>";
	childMenu(companyID, vals);
	result += "</ul></li></ul>";
	$(result).appendTo("#tree1");
	$("#browser").treeview();
	result = "";
}

function childMenu(companyID, vals) {// 2级
	for (var j = 0; j < vals.length; j++) {
		if (vals[j].pid == companyID && vals[j].str == "00") {
			result += "<li title='" + vals[j].text + "'><a><span id='"
					+ vals[j].id
					+ "' class='folder curor' onclick='javascript:getPerson(\""
					+ companyID + "\",\"" + vals[j].id + "\",\"org\")'>"
					+ vals[j].text + "</span></a></li>";
		}
	}
}

function getPerson(company, org, searchType) {
	var url = basePath + "ea/smeeting/sajax_ea_getAllStaff.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"companyID" : company,
					"orgID" : org,
					"searchType" : searchType
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "";
					//obj.staffCode人员编号
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "-" + org
								+ "-" + "'>" + obj.staffName + "</option>";
					}
					$("#leftfields").html(str);
				}
			});
}
//关闭选择人员
function closed() {
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#zj").jqmHide();

}
function re_load() {
	if (token)
	var url = basePath
			+ "ea/projectmanager/ea_getDtMyprojectList.jspa?pageNumber="+ pNumber + "&date=" + new Date();
	document.location.href = encodeURI(url);
}