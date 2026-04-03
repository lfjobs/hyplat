$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 330,
				allDouble:true,
				width : 'auto',
				minwidth : 30,
				title : '微型企业版  --->  '+orgname,
				minheight : 80,
				buttons : [ {
					name : '分配人员',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '职务人员',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}
				]
			});
	function action(com, grid) {
		switch (com) {
			case '分配人员':
				if(depPostID==""){
					alert("请选择职务！");
					return 
				}
				var postN = $("tr#"+depPostID,"#orgPostForm").find("span#postName").html();
				$("#orgEntryForm").find("input#postName").attr("value",postN);
				$("#orgEntryForm").find("input#postID").attr("value",depPostID);
				$("#orgEntryForm").find("input#orgID").attr("value",$("tr#"+depPostID,"#orgPostForm").find("input#organizationID").val());
				$("#jqModelEntry").jqmShow();
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/departmentpost/ea_getDeployList.jspa?search="+ search+"&departmentPost.organizationID="+orgId+"&star=00";
				numback(url);
				break;
			case '职务人员':
				if(depPostID==""){
						alert("请选择职务！");
						return 
					}
				personurl = basePath + "ea/departmentpost/ea_getListPerson.jspa?departmentPost.depPostID=" ;
				 $("#mainframe").css("height","auto").attr("src",  basePath + "ea/departmentpost/ea_getListPerson.jspa?departmentPost.depPostID=" + depPostID);
				$(window).resize();
				break;
		}
	}
		//单击事件
	$(".JQueryflexme tr[id]").click(function() {
			$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			depPostID = this.id;
			if(personurl){
				$("#mainframe").css("height","auto").attr("src",personurl+depPostID);
				$(window).resize();
			}

	});
    $("#start").blur(function() {
				var start = $(this).val();
				if (start != '') {
					$("#start").attr("value", start);
				}
			});
	$("#end").blur(function() {
				var end = $(this).val();
				if (end != '') {
					$("#end").attr("value", end);
				}
			});
	// radio按钮事件;
	$(".radio").click(function(){
		if(this.id =="radio1"){
			$("tr#lb").show();
			$("tr#jb").show();
			$("div#div1").show();
			$("tr#trdj").show();
		}else{
			$("tr#lb").hide();
			$("tr#jb").hide();
			$("div#div1").hide();
			$("tr#trdj").hide();
		}
		
	});
    $("#tosearch").click(function() {
		$f = $('#orgPostSearchForm');
		$f.attr("action", basePath
						+ "ea/departmentpost/ea_getDeployList.jspa?pageNumber="
						+ pNumber+"&departmentPost.organizationID="+orgId+"&star=00");
		document.orgPostSearchForm.submit.click();
	});
	
	$("#toCommit").click(function() {
		if($("#orgEntryForm").find("#principal").val() == ""){
			alert("人员不能为空！");
			return 
		}
		if($("#orgEntryForm").find("#radio1").attr("checked") == true){
			//alert($("#orgEntryForm").find("#staffType").val());
			if($("#orgEntryForm").find("#staffType").val() == ""){
				alert("员工类别不能为空！");
				return
			}
			if($("#orgEntryForm").find("#payScaleID").val() == ""){
				alert("职务级别不能为空！");
				return
			}
			/*if($("#startDate").val()==""){
				alert("合同起日期不能为空！");
				return;
			}
			if($("#ednDate").val()==""){
				alert("合同止日期不能为空！");
				return;
			}*/
		}
		var url = basePath +"ea/departmentpost/sajax_ea_orgPostEntry.jspa?date="+new Date()+"&entity.categoryName="+$("select#staffType option:selected").text();
        var formData = $("#orgEntryForm").serialize();
		formData = decodeURIComponent(formData,true);
		$.ajax({
			  url : encodeURI(url),
              type: "get",
              async: false,
              dataType: "json",
              data:formData,
              success: function cbf(data){
       		var member = eval("(" + data + ")");
       		var vals = member.vals;
       			alert(vals);
     		},error: function cbf(data){
                alert("数据获取失败！");
            }
        });			
		$("#jqModelEntry").jqmHide();
		document.orgEntryForm.reset();
		document.location.href = basePath
		+ "ea/departmentpost/ea_getDeployList.jspa?pageNumber="
		+ pNumber + "&pageForm.pageNumber="
		+ $("#pageNumber").attr("value")+"&departmentPost.organizationID="+orgId+"&star=00";
	});  
	$("#toReset").click(function() {
		var i = $("#orgEntryForm").find("input#postName").val();
		var ii = $("#orgEntryForm").find("input#postID").val();
		document.orgEntryForm.reset();
		$("#orgEntryForm").find("input#postName").attr("value",i);
		$("#orgEntryForm").find("input#postID").attr("value",ii);
	}); 
	$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
			    }); 
			   
	$("#isSubmit").click(function(){// 选择确定
		var parm = $("#parm",$("#jqmWindow2")).attr("value");
		var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择");
			return;
		}
		
		var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
		if(parm != "")
		$("#principal",$("#orgEntryForm")).attr("value",value2).trigger("blur");
		$("#principalID",$("#orgEntryForm")).attr("value",value1).trigger("blur");
		$("#ifr").attr("src","");
	       $("#jqmWindow2").jqmHide();
	   });
	
	//员工类别
       	var url = basePath +"ea/saudition/sajax_n_ea_getBillID.jspa?date="+new Date();
        $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    dataType: "json",
                    success: function cbf(data){
		           		var member = eval("(" + data + ")");
		           		var nologin = member.nologin;
						if(nologin){
		                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
		                }
		           		var staffTypeList = member.staffTypeList;
		           		if (null != staffTypeList) {
		           			var htmlStr="<option value=''>请选择</option>";
			               	for (var i = 0; i < staffTypeList.length; i++) {
			               		htmlStr+="<option value='"+staffTypeList[i].codeID+"'>"+staffTypeList[i].codeValue+"</option>";
			             	}
			             	$("#staffType").html(htmlStr);
			          if(staffCategoryID!="")
	                $("#staffType").find("option[value='"+staffCategoryID+"']").attr("selected","selected");
          				}
           			},error: function cbf(data){
                          		alert("数据获取失败！");
                    }
        });
    	
    	//获取工资级别
       	var url1 = basePath +"ea/soincumbent/sajax_n_ea_getStaffListForPost.jspa?date="+new Date();
        $.ajax({
                url: url1,
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data){
				var member = data;
				var nologin = member.nologin;
				if(nologin){
					document.location.href =basePath+"page/ea/not_login.jsp";
				}
           		var paylist = member.paylist;
				if (null != paylist) {
					for (var j = 0; j < paylist.length; j++) {
						$op = $("<option/>");
						$op.val(paylist[j].payScaleID).text(paylist[j].position+"/"+paylist[j].scale);
						$(".PayScale").append($op);
		     		}
				}
       			},error: function cbf(data){
                      alert("数据获取失败！");
                }
        });
       /* 
        //读取芯片号
    	$("#readchipid").click(function() {

		$("#orgEntryForm #loadcab")
				.attr(
						"src",
						basePath
								+ "page/ea/main/office_ea/archives/loadActiveX.html?code="
								+ Math.random());

	});  */
	
 
        
    			
});

// 判断芯片号是否重复
function checkChip(chipid) {
	if ($("#postEnterForm #oldchipids").val() == chipid) {
		return true;
	}
	var bool=false;
	var url = basePath + "ea/archive/sajax_ea_IsChipRepeat.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					chipid : chipid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "fail") {// 重复
						bool = false;
					} else {
						bool = true;// 不重复
					}

				},
				error : function(data) {
					alert("读取数据失败");
				} 
			}); 
	return bool;
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/departmentpost/ea_getOrgPostListByOrg.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&departmentPost.organizationID="+orgId+"&star=00";
}
 //选择责任人       
function searchCoach(){
	 var parm = "zm";
	 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
	 getValueForParm('cstaffForm','partnerName',url);
}

function getValueForParm(attachTable,parm,url){ //打开页面
	 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
	 $("#parm",$("#jqmWindow2")).attr("value",parm);
  	 $("#ifr").attr("src",basePath+url);
  	 $("#jqmWindow2").jqmShow();
}

