$(function(){
	$("#depotName").click(function(){
		$("#ckjqModel", $("#ckForms")).jqmShow();
	});
	$(".operation").click(function(){
		if($(this).val()=="提交"){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/sourcingsto/ea_AddStorage.jspa?fiveClear="+fiveClear);
			document.form.submit.click();
			token = 2;
		}else{
			window.close();
		}
	});
	//选择往来个人
	$("#staffsName").click(function(){
		$("#deptjqModel").jqmShow();
		getStaffMember("parameter=&selectDept="+orgId);
	});
	//添加选中的往来个人
	$(".JQueryreturns").click(function(){
		if($(this).val()=="查询"){
			getStaffMember("selectDept="+orgId+"&parameter="+$("#searchdept").find("#parameterrm").val());
		}else if($(this).val()=="确定"){
			$("#staffsName").val($("#"+staffId).find("#staffname").text());
			$("#staffsID").val(staffId);
		//	$(".staffName").css("background-color","#FFFFFF").removeClass("error");
			$("#deptjqModel").jqmHide();
		}else{
			$("#deptjqModel").jqmHide();
		}
	});
	$("tr[id]").live("click",function(){
		if($(this).attr("class")=="staff"){
			staffId=this.id;
			$(this).find(".radio").attr("checked","checked");
		}
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
});

//获取员工
function getStaffMember(typeCN) {
	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	$.ajax({
				url : encodeURI(searchurl + typeCN
						+ "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var tabletr = "";
					if (pageForm == null) {
						$("#body_02dept").html("");
						$("span#dpzycount").text(0);
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dpxy").attr("title", dqy + 1);
					}
					$("span#dpzycount").text(zys);
					for ( var i = 0; i < pageForm.list.length; i++) {
							tabletr += "<tr style='cursor: hand;' class='staff' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
									+ pageForm.list[i].staffID
									+ " name='checkradio'/></td>";
							tabletr += "<td id='mum' align='center'>"
									+ (i + 1) + "</td>";
							tabletr += "<td id='staffcode' align='center'>"
									+ pageForm.list[i].staffCode
									+ "</td>";
							tabletr += "<td id='staffname' align='center'>"
									+ pageForm.list[i].staffName
									+ "</td>";
							tabletr += "<td id='sex' align='center'>"
									+ pageForm.list[i].sex
									+ "</td>";
							tabletr += "<td id='birthday' align='center'>"
									+ pageForm.list[i].birthday
									+ "</td>";
							tabletr += "<td id='nativePlace'  align='center'>"
									+ pageForm.list[i].nativePlace
									+ "</td>";
							tabletr += "<td id='reference'  align='center'>"
									+ pageForm.list[i].reference
									+ "</td>";
							tabletr += "<td id='staffIdentityCard' align='center'>"
									+ pageForm.list[i].staffIdentityCard
									+ "</td>";
							tabletr += "<td id='staffid' align='center' style='display:none;'>" 
								+ pageForm.list[i].staffID
								+ "</td>";
							tabletr += " </tr>";
					}
					$("#body_02dept").html(tabletr);
				},
				error : function cbf(data) {
					notoken = 0;
				}
			});
}

/** **************************************科目管理******************************************* */
$(function(){
	$(".subrid").click(function(){
		if($(this).val()=="首页")
			subtrs("1",$(this).attr("id"));
		else if($(this).val()=="上一页")
			subtrs(parseInt(pagenum)-1,$(this).attr("id"));
		else if($(this).val()=="下一页")
			subtrs(parseInt(pagenum)+1,$(this).attr("id"));
		else if($(this).val()=="尾页")
			subtrs(Math.ceil(search2/14),$(this).attr("id"));
	});
	$(".subtr").live("click",function(){
		$(".subtr").each(function(){
			$(this).attr("style","background-color:#E0EEEE");
		});
		$(this).attr("style","background-color:#8DB6CD");
	});
	$("#determine").click(function(){
		$(".subtr").each(function(){
			if($(this).attr("style")=="background-color:#8DB6CD"){
				$("#subjectsid", $(".receivesubjects")).attr("value",
						$(this).find("td").find("input").val());
				$("#subjectscode", $(".receivesubjects")).attr("value",
						$(this).find("td").eq(0).text());
				$("#tosubjects", $(".receivesubjects")).attr("value",
						"("+$(this).find("td").eq(0).text()+")"+$(this).find("td").eq(1).text());
				$(".receivesubjects").removeClass("receivesubjects");
			}
		});
		$("#subjectr").jqmHide();	
	});
	$("#cancel").click(function(){
		$("#subjectr").jqmHide();		
	});
	$("#tosubjects").click(function(){
		$(this).parent().parent().addClass("receivesubjects");
		if(!$(".subNumber").text()){
		var subjecturl = basePath
			+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		$.ajax({
			url : encodeURI(subjecturl + "002&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var subjects = member.subjectsList;
				for(var i=0;i<subjects.length;i++){
					var tr;
					if(i==0){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line4.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}else if(i==(subjects.length-1)){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line2.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}else{
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line3.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}
					$("#kemuone").append(tr);
				}				
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		}
		$("#subjectr").jqmShow();	
	});
	$("#fudanshu").change(function(){
		if(/^[1-9]*[1-9][0-9]*$/.test($(this).val()))
			$(this).attr("style","border: 0px;width: 100%;height: 100%;").removeClass("err");
		else
			$(this).attr("style","border: 0px;width: 100%;height: 100%;background-color: red").addClass("err");
	});
	
	$.ajax({
		url : basePath+"ea/office/sajax_ea_sajaxGetThenFiveDepartmentsList.jspa",
		type : "post",
		async : false,
		dataType : "json",
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list;
			for(var i=0;i<list.length;i++){
				var option="<option value='"+list[i].organizationID+"'>"+list[i].organizationName+"</option>";
				$("#orgValue").append(option);
				$(".orgVAL").append(option);
			}
		},
		error:function(){
			alert("数据获取失败");
		}
	});
	$(".clickOrg").live("change",function(){
		$(this).parent().find("input#organizationName").val($("option:selected",this).text());
	});
	$(".billData").live("dblclick",function(){
		window.open(basePath
				+ "/ea/splitbill/ea_toSaveCashierBills.jspa?cashierBills.cashierBillsID="+ $(this).find("td").eq(8).text()+"&status=view");
	});
});
function subtrs(pageNumber,obj){
	if(pageNumber>(Math.ceil(search2/14)))
		pageNumber=(Math.ceil(search2/14));
	if(pageNumber<1)
		pageNumber=1;
	$(".subrid").attr("id",obj);
	$("#kemutoo").find(".subtr").remove();
	pagenum=pageNumber;
	var subjecturl = basePath
		+ "ea/csbjects/sajax_ea_getAjaxListCsubejstsByPID.jspa?subjectsID=";
	$.ajax({
		url : encodeURI(subjecturl + obj+"&pageForm.pageNumber="+pageNumber+"&pageNumber=14&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var subjects = member.pageForm.list;
			search2=member.search;
			for(var i=0;i<subjects.length;i++){
				var lb="其他";
				if(subjects[i].subjectsCategory=="A")
					lb="银行";
				if(subjects[i].subjectsCategory=="B")
					lb="现金";
				if(subjects[i].subjectsCategory=="C")
					lb="固定资产";
				var tr="<tr align='left' class='subtr' style=\"background-color:#E0EEEE\">" +
					   "<td>"+subjects[i].subjectsNumbers+"</td>" +
					   "<td><span class='sprs' title="+subjects[i].subjectsName+">"+subjects[i].subjectsName+"</span></td>"+
					   "<td>"+lb+"<input type='hidden' value="+subjects[i].subjectsID+"></td>" +
					   "<td>"+(subjects[i].subjectsDirection=='D'?'借':'贷')+"</td>" +
					   "<td>"+(subjects[i].subjectsAccounts=='Y'?'主账户':'虚账户')+"</td></tr>"; 

				$("#kemutoo").append(tr);
			}
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});		
};

/**-----------------------------------------------------------------仓库管理开始-----------------------------------------------------------------------------*/

function clWarehouse(){
	$(".cyc"+irr).each(function(){
		queryWarehouse("cl",$(this).attr("id"));
	});
	irr++;
	if($(".cyc"+(irr-1)).attr("class")){
		clWarehouse();irr=0;
	}
}

//选择调出仓库
$(function(){
	// 新增
	$(".xzck").click(function() {
		window.open(basePath
				+ "/ea/depotmanage/ea_getListDepotManage.jspa");
	});
	//选择
	$("#ckok").click(function(){
		
	});
	$("#ckok").live("click",function(){

		//判断选择物品
		$i=$("#mainframe");
	    if ($i.contents().find("[name='a']").is(':checked')) {
    	var id=$i.contents().find("input[type='radio']:checked").val();//库房id
    	$r=$i.contents().find("#" +id);
    	var depotName=$r.find("#depotName").text();//库房name
    	var useState=$r.find("#useState").text();//启用/未启用
    	if(useState=="未启用"){
		     alert("未启用，请选择已启用仓库!");
		     return;
		}
		//赋值

		$("input#depotID").val(id);	
		$("input#depotName").val(depotName);
		$("input#depotName").blur();
		
		$i.contents().find("input[type='radio']:checked").attr("checked", false);
		$(".jqmWindow", $("#ckForms")).jqmHide();
	    }else {
			alert("请选择仓库！");
		}
	});
	cycleWarehouse();
	$("#xmul").treeview({
		persist: "location",
	    collapsed: true,
	    unique: false
	});
	$(".Warehouse").click(function(){
		if("wu"==$(this).attr("name"))
			return;
	    var srcUrl=basePath +"ea/cdepotmanage1/ea_getListDepotManageTreenew.jspa?pageNumber=${pageNumber}&depotID="+ $(this).attr("id") + "&usetype=ck";	          					           
	    $("#mainframe").attr(
		"src",srcUrl);
		$(window).resize();
	});
});

function clickware(obj){
	if("wu"==$("#"+obj).attr("name"))
		return;
    var srcUrl=basePath +"ea/cdepotmanage1/ea_getListDepotManageTreenew.jspa?pageNumber=${pageNumber}&depotID="+ $("#"+obj).attr("id") + "&usetype=ck";	          					           
    $("#mainframe").attr(
	"src",srcUrl);
	$(window).resize();
}
//选择调出仓库
function cycleWarehouse(){
	$(".cycle"+r).each(function(){
		queryWarehouse("cy",$(this).attr("id"));
	});
	r++;
	if($(".cycle"+(r-1)).attr("class")){
		cycleWarehouse();r=0;
	}
}

function queryWarehouse(direction,obj){
    var	getListCCodeurl=basePath+"ea/cdepotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID="+obj+"&date="+new Date().toLocaleString();
    $.ajax({
        url: encodeURI(getListCCodeurl),
        type: "get",
        async: false,
        dataType: "json",
        success: function cbf(data){
           var member = eval("("+data+")");
            var nologin = member.nologin;
          if(nologin){
          document.location.href =basePath + "page/ea/not_login.jsp";
          }
           depotManagelist = member.depotManagelist;
           if(depotManagelist.length==0){
        	   $("."+direction+obj).removeClass("folder");
        	   $("."+direction+obj).addClass("file");
        	   $("."+direction+obj).attr("name","wu");
           } 				                
            for(var i=0;i<depotManagelist.length;i++)
		   {		             
            if(direction=="cy"){
            	var ul="<ul class='uls'><li><span class='Warehouse folder cycle"+(r+1)+" cy"+
         		depotManagelist[i].depotID+"' id='"+depotManagelist[i].depotID+"'>" +
         		depotManagelist[i].depotName+"</span></li></ul>";
            	$("."+direction+obj).after(ul);
            }else{
            	var ul="<ul class='uls2'><li><span class='Warehouse folder cyc"+(irr+1)+" cl"+
         		depotManagelist[i].depotID+"' id='"+depotManagelist[i].depotID+"' onclick=clickware('"+
         		depotManagelist[i].depotID+"')>" +
         		depotManagelist[i].depotName+"</span></li></ul>";
            	$("."+direction+obj).after(ul);
            }
             
           }
            token = 0;													 	
        },
        error: function cbf(data){
           alert("数据获取失败！");
        }
    });
    return;
}

/**-----------------------------------------------------------------------仓库管理结束----------------------------------------------------------------*/
function re_load(){
	window.opener.location.href=window.opener.location.href;
	window.close();
}