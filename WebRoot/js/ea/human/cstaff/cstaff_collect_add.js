$(function() {
		$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		// .jqDrag('.drag');// 添加拖拽的selector
		
		
	//----------------选择在职人员操作------------//
	$("#DaoRuFan1").click(function(){// 返回
       $("#bankJqm1").hide();
	}); 
	$("#DaoRuFanqd1").click(function(){// 选择确定
		var childopertionID = window.frames["daoRu1"].opertionID;
		if(childopertionID == ""){
			alert("请选择");
			return;
		}
		var staffName = $(window.frames["daoRu1"].$('tr#'+childopertionID)[0]).find("span#staffName").text();
		var staffIdentityCard =$(window.frames["daoRu1"].$('tr#'+childopertionID)[0]).find("span#staffIdentityCard").text();
		var reference =$(window.frames["daoRu1"].$('tr#'+childopertionID)[0]).find("span#reference").text();//mainframe4
		
		$t=$("input.JQuerypersonvalue:checked",window.frames["mainframe53"].document).parents("tr");
		$t.find("input#referrer").attr("value",staffName);
		$t.find("input#referrerID").attr("value",childopertionID);
		$t.find("input#referrerPhone").attr("value",reference);
		$t.find("input#referrerIdentityCard").attr("value",staffIdentityCard);
		 $("#daoRu1").attr("src","");
         $("#bankJqm1").hide();
   		});
   
   $("#DaoRuFan").click(function(){// 返回
       		$("div#bankJqm").jqmHide();
		}); 
		
		$("#DaoRuFanqd").click(function(){// 选择确定
		window.frames["daoRu"].$("input#selectPerson").click();
		var childopertionID = window.frames["daoRu"].opertionID;
		if (childopertionID == "" || childopertionID.length == 0) {
					alert('请选择');
					return;
				}
		var url = basePath+ "/ea/stafftrack/sajax_ea_getCompanybyID.jspa?childopertionID="+childopertionID+"&date="+new Date();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
						var co=member.co;
						if(co==1){
							alert("不能重复添加");
							return;
						}else{
							var cstaff=member.cstaff;
							$("table#stafftable").find("span#staffCode").text(cstaff.staffCode);
							$("table#stafftable").find("span#recordCode").text(cstaff.recordCode);
							$("table#stafftable").find("span#staffName").text(cstaff.staffName);
							$("table#stafftable").find("input#staffName").attr("value",cstaff.staffName);
							$("table#stafftable").find("input#staffID").attr("value",cstaff.staffID);
							$("table#stafftable").find("span#staffID").text("");	
							$("table#stafftable").find("span#usedNmae").text(cstaff.usedNmae);	
							$("table#stafftable").find("span#sex").text(cstaff.sex);
							$("table#stafftable").find("span#birthday").text(cstaff.birthday);
							$("table#stafftable").find("span#nation").text(cstaff.nation);
							$("table#stafftable").find("span#nativePlace").text(cstaff.nativePlace);
							$("table#stafftable").find("span#reference").text(cstaff.reference);
							$("table#stafftable").find("span#staffIdentityCard").text(cstaff.staffIdentityCard);
							$("table#stafftable").find("span#passportNum").text(cstaff.passportNum);
							staffName = cstaff.staffName ;
							
							
							$("#daoRu").attr("src","");
							$("#bankJqm").jqmHide();
							$("tr#tools").show();
							$("div#box1").find("input.isHide").removeClass("isHide").addClass("isShow");
						}
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});
		
   });
		
		$('.JQueryflexme').flexigrid({
			buttons : [{
				name : '编辑菜单',
				bclass : 'menu1',
				onpress : action2
				}]
		});
		function action2(com, grid) {
			switch (com) {
				case '编辑菜单' :
					$(".menu00").toggle();
					break;
			}
		}
	var i = 0;
	$("div.showorhide").each(function(){
		if(this.id == '1'){
			i++;
			$(this).show();
			$("#"+this.name).attr("checked",true);
			if(i == 15){ //为全选状态
				$(".oroupboxAll").attr("checked",true);
			}
			/*
			if(showType == 'edit'){ 
				$(this).find("a:first").click();
			}*/
		}
	});
	
	$(".oroupboxAll").click(function(){ //全选
        if($(this).attr("checked")){
            $("input[type='checkbox']").each(function(){
          		$(this).attr("checked",true);
            });
        }else{
            $("input[type='checkbox']").each(function(){
            	$(this).attr("checked",false);
            });
        }
    });

	$(".JQuerySubmits").click(function(){ //编辑菜单保存
		var formData = $("#humanForm").serialize();
		formData = decodeURIComponent(formData,true);
		var url = basePath + "ea/stafftrack/sajax_ea_saveHumanCollect.jspa?date="+new Date()+"&showType="+showType+"&";
		$.ajax({
				url: encodeURI(url + formData),
				type: "get",
				async: false,
				dataType: "json",
				success: function(data){
					if(showType == 'add'){
						window.location.href = basePath+"ea/stafftrack/ea_toSaveJsp.jspa?showType=add&baokaixuecheguan="+baokaixuecheguan;//baokaixuecheguan参数  为了区分是否为学员报名模块   特显示另外几个模块
					}else{
						var staffid = $("#staffID").text();
						window.location.href = basePath+"ea/stafftrack/ea_toSaveJsp.jspa?showType=edit&cstaff.staffID="+staffid+"&baokaixuecheguan="+baokaixuecheguan;
					}
				},
				error:function(data){
					alert('保存失败！');
				}
		});
	});
	
	$("select#staus").change(function() {// 信息类型转变
		if($(this).val()=='00'){//确定人员信息
			$("#staffIdentityCard").addClass("put4");
		}else{//不确定人员信息
			$("#staffIdentityCard").removeClass("put4");
			$("#staffIdentityCard").removeClass("newerror");
		}
	});
	
	$("td.txt03").click(function(){
		var did = $(this).next().find("a:eq(0)").attr("id");
		var mid = did.substring(4);
		changemenu(did,mid,'edit');
	});
	
});
function toSave(formID, url) { // 保存
	var chilID = $("table#stafftable").find("input#staffID").attr("value");
	var url = basePath+url+"?showType=edit&childopertionID="+chilID+"&cstaff.staffID="+ chilID+"&date="+new Date();
	$.ajax({
			url: encodeURI(url ),
			type: "get",
			async: false,
			dataType: "json",
			success: function(data){
				alert("保存成功！");
				$("tr#tools").show();
				$("div#box1").find("input.isShow").removeClass("isShow").addClass("isHide");
				window.opener.refresh();//刷新父页面 
				$("span#staffID").text($("input#staffID").val());
				for(var i = 2 ;i < 6 ; i++){
					$("div#box"+i).hide();
				}
			},
			error:function(data){
				alert('保存失败！');
			}
		});
	
}
//第一个参数为表单id，第二个参数为 table表头id
function changemenu(divid,menuid,opetype){
	
	if(opetype=='edit'){
		if(menuid != '1'){
			$("div#"+divid).find("span.isShow").removeClass("isShow").addClass("isHide");
			$("div#"+divid).find("input.isHide").removeClass("isHide").addClass("isShow");
			$("table.box"+menuid).find("a#mord"+menuid+"_close").removeClass("isHide");
			$("table.box"+menuid).find("a#mord"+menuid).addClass("isHide");
		}
		switch(menuid){
			case 1:
				$("#daoRu").attr("src",basePath+'ea/stafftrack/ea_getStaffForCashier.jspa');
				$("div#bankJqm").jqmShow();
				break;
			default:
				$("div#box"+menuid).show();
				var personvalue = $("span#staffID").text();
				if (personvalue == '') {
					alert("请先提交！");
					return;
				}
				var personurl = basePath + $("#mainframe"+menuid).attr("url");
				$("#mainframe"+menuid).attr("src", personurl + personvalue);
				break;
		}
	}else if(opetype=='close'){
		if(menuid!=1){
			$("div#"+divid).find("span.isHide").removeClass("isHide").addClass("isShow");
			$("div#"+divid).find("input.isShow").removeClass("isShow").addClass("isHide");
			$("table.box"+menuid).find("a#mord"+menuid+"_close").addClass("isHide");
			$("table.box"+menuid).find("a#mord"+menuid).removeClass("isHide");
			$("tr#tools").hide();
			$("div#box"+menuid).hide();
		}
	}
}
