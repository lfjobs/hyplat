$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
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
	if(flag=="web"){
		$(".conf").show();
		$(".other").hide();
		$(".other1").hide();
		
		
	}else{
		
		$(".conf1").hide();
		$(".conf").hide();
		$(".other").show();
		
	}
	var i = 0;
	$("div.showorhide").each(function(){
		if(this.id == '1'){
			i++;
			$(this).show();
			$("#"+$(this).attr("name")).attr("checked",true);
			if(i == 16){ //为全选状态
				$(".oroupboxAll").attr("checked",true);
			}
			
			if(flag=="web"){
			  
				$(".other1").hide();
				
				
				
			}else{
				$(".conf1").hide();
				
				
			}
			
			/*
			if(showType == 'edit'){ 
				$(this).find("a:first").click();
			}*/
		}
	});
	//复选框 全选
	$(".oroupboxAll").click(function(){ //全选
		

        if($(this).attr("checked")){
        	
        	
            $("input[type='checkbox']").each(function(){
            	var display =$(this).parent().css('display');
            	if(display != 'none'){
          		$(this).attr("checked",true);
            	
                }
             });
        	
        }else{
        	
            $("input[type='checkbox']").each(function(){
            	var display =$(this).parent().css('display');
            	if(display != 'none'){
            	$(this).attr("checked",false);
            	}
            });
        	}
        
    });
	 //输入框的字数限制
	 $(".Max").blur(function(){
			if($(this).val().length>10)
			{
				alert("您的输入超出限制");
				$(this).val("");
				}
		});
	 
	$(".JQuerySubmits").click(function(){ //编辑菜单保存
		var formData = $("#contactcomForm").serialize();
		formData = decodeURIComponent(formData,true);
		var url = basePath + "ea/contactcompany/sajax_ea_savecontactCompany.jspa?flag="+flag+"&";
		$.ajax({
				url: encodeURI(url + formData+"&date="+new Date()),
				type: "post",
				dataType: "json",
		        async: true,  
				success: function(data){
					if(showType == 'add'){
						window.location.href = basePath+"ea/contactcompany/ea_toSaveJsp.jspa?showType=add&flag="+flag;
					}else{
						window.location.href = basePath+"ea/contactcompany/ea_toSaveJsp.jspa?showType=edit&ccompanyID="+ccompanyID+"&flag="+flag;
					}
				},
				error:function(data){
					alert('保存失败！');
				}
		});
	});

	$("td.txt03").click(function(){
		var did = $(this).next().find("a:eq(0)").attr("id");
		var mid = did.substring(4);
		changemenu(did,mid,'edit');
	});
	//ljc 二级行业
	$("#industryType").change(function(){
		var codepid=$(this).find("option:selected").val();
		$("#industryType1").show();
		var url=basePath+"/ea/industry/sajax_ea_getIndustry.jspa?codePID="+codepid;
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : true,
			dataType : "json",
			success : function cbf(data){
				var member=eval("("+data+")");
				var list=member.industryList;
				if(list==null){
					return;
				}else{
				$("#industryType1").empty();
				$td=$("#industryType1");
				for(var i=0;i<list.length;i++){
					$td.append("<option value='"+list[i].codeID+"'>"+list[i].codeValue+"</option>");
				}
				
					
				}
			},
			error : function cbf(data){
				alert("数据加载失败！");
			}
		});
	});
	
	
	
	
	//
	if(ccompanyID!=""){
		$("#comPro").val($.trim($("#comPro").parent().find("span").text()));
		$("#comScale").val($.trim($("#comScale").parent().find("span").text()));
	}
	
});

function toSave(formID, url) { // 保存
	$(".put3", 'form#'+formID).trigger("blur");	
	$("input#responsibleTel").trigger("blur");
	$("input#companyTel").trigger("blur");
	//$("input#companyName").trigger("blur");	
	if ($(".error", 'form#'+formID).length) {
		retoken = 0;
		alert('请完善所有信息');
		return;
	}	
/*	var dianhua=$("#box1Form").find("input#companyTel").attr("value");
	if(dianhua != ""){
		var filter= (/(^(\d{3,4}-)?\d{7,8}(-\d{1,3})?$)/.test(dianhua) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(dianhua) ) ; 
		if(("0000000"==(dianhua))||("00000000"==(dianhua))||(filter==false)){
		alert("请输入正确的电话号码");
		return
		};  
	}*/
/*	var shouji=$("#box1Form").find("input#responsibleTel").attr("value");
	if(shouji != ""){
		var filters= (/(^(\d{4}-)?\d{7,8}(-\d{1,3})?$)/.test(shouji) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(shouji) ) ; 
		if(("0000000"==(shouji))||("00000000"==(shouji))||(filters==false)){
		alert("请输入正确的电话号码");
		return
		};
	}*/
	var addr = "";
	$(".JQueryaddress").find("select")
			.find("option[value]:selected").each(function() {
				if ($(this).text() != '--新增--'
						&& $(this).text() != '--请选择--')
					addr = addr + $(this).text();
			});
		
	$(".JQueryaddress").find("input#companyAddr").attr("value",addr);
	var indus=$("#industryType").find("option:selected").text();
	var indus1=$("#industryType1").find("option:selected").text();
    var xingzhengs= $("#xingzheng").find("option:selected").text();
    var xingzhengs1= $("#xingzheng1").find("option:selected").text();
    var xingzhengs2= $("#xingzheng2").find("option:selected").text();
	var url = basePath+url+"?date="+new Date()+"&contactCompany.industryType="+indus+"/"+indus1+"&tcompany.district="+xingzhengs+"/"+xingzhengs1+"/"+xingzhengs2;
	//利用ajax利用FormData 提交表单和上传图片，jquery版本jquery-1.6.1.min.js
	var formData=new FormData($("#box1Form")[0]);
	$.ajax({
        url: encodeURI(url) ,  
        type: 'POST',  
        data: formData,  
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,  
        success: function (data) {  
			var member = eval("(" + data + ")");
			var ccompanyID = member.ccompanyID;
			var flag=member.flag;
			window.location.href = basePath+"ea/contactcompany/ea_toSaveJsp.jspa?showType=edit&ccompanyID="+ccompanyID+"&flag="+flag; 
        },  
        error: function (data) {  
            alert("保存失败！");  
        }  
		
	});
//ljc注释	
//	var formData = $("#box1Form").serialize();
//		formData = decodeURIComponent(formData,true);	
//	var url = basePath+url+"?date="+new Date();
//	$.ajax({
//			url: encodeURI(url+"&"+formData),
//			type: "post",
//			async: false,
//			dataType: "json",
//			success: function(data){
//				var member = eval("(" + data + ")");
//				var ccompanyID = member.ccompanyID;
//				window.location.href = basePath+"ea/contactcompany/ea_toSaveJsp.jspa?showType=edit&ccompanyID="+ccompanyID;
//			},
//			error:function(data){
//				alert('保存失败！');
//			}
//		});
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
				$("tr#tools").show();
				$("a#mord2").show();
				$("#industryType").show();
				$("#comPro").show();
				$("#comScale").show();
				$("#tta").show();
				$("#tta1").show();
				$(".JQueryaddress").find("select").show();
				break;
			default:
				$("div#box"+menuid).show();
				var personvalue = $("div#box1").find("span#ccompanyID").text();
				if (personvalue == '') {
					alert("请先选择公司！");
					return;
				}
				var personurl = basePath + $("#mainframe"+menuid).attr("url");
				$("#mainframe"+menuid).attr("src", personurl + personvalue+"&date="+new Date());
				break;
		}
	}else if(opetype=='close'){
		
		$("div#"+divid).find("span.isHide").removeClass("isHide").addClass("isShow");
		$("div#"+divid).find("input.isShow").removeClass("isShow").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid+"_close").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid).removeClass("isHide");
		$("tr#tools").hide();
		if(menuid==1){
			$("a#mord2").hide();
			$("#industryType").hide();
			$("#comPro").hide();
			$("#comScale").hide();
			$("#tta").hide();
			$("#tta1").hide();
			$("#industryType1").hide();
			$(".JQueryaddress").find("select").hide();
		}else{
			$("div#box"+menuid).hide();
		}
	}
}
$(document).ready(function() {
	//添加或修改公司名称判断
	$("input#companyName").live("blur",function(){
    	var onlyCompany = $.trim(this.value);
    	if(ccompanyID == ''&&onlyCompany!=''){
    		prompt(onlyCompany);
    	}else{
    		if(onlyCompany != $.trim(companyName)&&onlyCompany!=''){
    			prompt(onlyCompany);
    		}
    	}
    });
    function prompt(onlyCompany){
		var url = basePath
				+ "ea/contactcompany/sajax_ea_isContactConnection.jspa?onlyCompany="
				+ encodeURI(onlyCompany) + "&date=" + new Date();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var result = member.c;
						if (result != 0) {
							$("#box1Form").find("input#companyName").after("<span class=\"error\"><a class=\"tex\">该公司已存在</a></span>");
							$("#corects").find(".corect").remove();
						}
					},error:function(data){
						alert("读取失败");
						
					}
		});
	}
    
    
    //验证公司宗旨字符长度
    $("input#compurpose").blur(function(){
    	var str=$(this).val();
    	var c=str.getBytes();
    	if(c>=50){
    		alert("包含标点符号，字数不可以超过25个");
    		$(this).focus();
    		return;
    	}
    });
    //验证品牌信息
    $("#tta1").blur(function(){
    	var str=$(this).val();
    	var c=str.getBytes();
    	if(c>=800){
    		alert("包含标点符号，字数不可以超过400个");
    		$(this).focus();
    		return;
    	}
    });
    //验证经营范围信息
    $("#dealIn").blur(function(){
    	var str=$(this).val();
    	var c=str.getBytes();
    	if(c>=256){
    		alert("包含标点符号，字数不可以超过178个");
    		$(this).focus();
    		return;
    	}
    });
    //验证备注信息
    $("#remark").blur(function(){
    	var str=$(this).val();
    	var c=str.getBytes();
    	if(c>=256){
    		alert("包含标点符号，字数不可以超过178个");
    		$(this).focus();
    		return;
    	}
    });
/*	//座机电话验证
	$("input#companyTel").blur(function(){
		var dianhua=$("#box1Form").find("input#companyTel").attr("value");
		var filter= (/(^(\d{3,4}-)?\d{7,8}(-\d{1,3})?$)/.test(dianhua) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(dianhua) ) ; 
		if(("0000000"==(dianhua))||("00000000"==(dianhua))||(filter==false)){
		alert("请输入正确的电话号码");
		};  
	  });
	  //手机验证
	$("input#responsibleTel").blur(function(){	
		var shouji=$("#box1Form").find("input#responsibleTel").attr("value");
		var filters= (/(^(\d{4}-)?\d{7,8}(-\d{1,3})?$)/.test(shouji) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(shouji) ) ; 
		if(("0000000"==(shouji))||("00000000"==(shouji))||(filters==false)){
		alert("请输入正确的电话号码");
		};  
	});*/
	
	$("input.JQueryreturn").click(function() {// 取消
				$(".jqmWindow").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$(".jqmWindow").jqmHide();
				re_load();
			});
	$("input.JQueryreturn2").click(function() {// 城市添加取消
		retoken = 0;
		$(".jqmWindow").jqmHide();
		$("#jqModel").jqmShow();
	});
	
	//地址
		
	var PID="";// 当点新曾时,上一级被选中项的id
	var rovince="";// 被改变的那个的id
	var districtPID;
	function LiuZhongYaoDeShaGuaDiZhi(address) {
		
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		$(".IdentityCard").css("background-color", "#ffffff");
		// 非空验证End
		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		$('td.JQueryaddress input[name=changes]').show();
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
					+ "&date1=" + new Date();
			$.ajax({
						url : url,
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
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
							retoken = 0;
						},
						error : function cbf(data) {
							retoken = 0;
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ encodeURI(DistrictID) + "&date2=" + new Date();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
				retoken = 0;
				for (var i = 0; i < distinctlistSaved.length; i++) {
					if (i == 9) {
						return;
					}
					$td.children('select:eq(' + i + ')').empty();
					$td.children('select:eq(' + i + ')').append($select);
					for (var j = 0; j < list[i].length; j++) {
						$op = $("<option />");
						$op.attr("value", list[i][j].districtID).attr("id",
								list[i][j].districtID)
								.text(list[i][j].districtName);
						$td.children('select:eq(' + i + ')').append($op);
					}
					$opp = $("<option  selected='selected'/>");
					$opp.attr("value", distinctlistSaved[i].districtID).attr(
							"id", distinctlistSaved[i].districtID)
							.text(distinctlistSaved[i].districtName);
					$td.children('select:eq(' + i + ')').append($opp);
					$add = "<option class='add'  value = '"
							+ distinctlistSaved[i].districtPID
							+ "' >--新增--</option>";
					$td.children('select:eq(' + i + ')').append($add);
				}
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($select);
				for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
					$op = $("<option />");
					$op
							.attr(
									"value",
									list[distinctlistSaved.length][z].districtID)
							.attr(
									"id",
									list[distinctlistSaved.length][z].districtID)
							.text(list[distinctlistSaved.length][z].districtName);
					$td.children('select:eq(' + distinctlistSaved.length + ')')
							.append($op);
				}
				$addd = "<option class='add'  value = '"
						+ distinctlistSaved[distinctlistSaved.length - 1].districtID
						+ "' >--新增--</option>";
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($addd);
				// num = distinctlistSaved.length -1 ;
				// $td.children('select:gt(' + num + ')').hide();
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});
	}
	
	$('td.JQueryaddress select[number]').change(function() {

		if (retoken)
			return;
		retoken = 1;
		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddress");
		rovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#jqModel").jqmHide();
			$("#newdistrict").jqmShow();
			retoken = 0;
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			if (number != "0") {
				var nu = parseInt(number) - 1;
				districtPID = $("select[number=" + nu + "]", $td).val();
			} else {
				districtPID = "";
			}
			$td.find('input#address').val(districtPID);
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ encodeURI(districtPID) + "&date3=" + new Date();
		$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlist = member.distinctlist;
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				if (distinctlist.length) {
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option/>");
						$op.attr("value", distinctlist[i].districtID).attr(
								"id", distinctlist[i].districtID)
								.text(distinctlist[i].districtName);
						$(rovince, $td).next().append($op);
					}
				}
				$add = "<option class='add'  value = '" + districtPID
						+ "' >--新增--</option>";
				$(rovince, $td).next().append($add);
				$td.find('input#address').val(districtPID);
				retoken = 0;
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");

			}
		});
	});
	$('input#savedistrict').click(function() {
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		number = $('input#districtNames').attr('title');
		districtName = $.trim($('input#districtNames').val());
		$td.children('select:gt(' + number + ')').empty();
		if ('' == districtName) {
			alert("请填写地域名称");
			retoken = 0;
			return;
		}
		var lastname = districtName.substring(districtName.length - 1,
				districtName.length);
		if (number == 1) {
			var tfname = true;
			var arrname = ["市", "县", "区", "盟"];
			$.each(arrname, function(key, value) {
						if (lastname == value) {
							tfname = false;
						}
					});
			if (tfname) {
				alert("输入错误!请填写以市、县、区、盟为结尾的地址名称");
				$('input#districtNames').val("");
				retoken = 0;
				return;
			}
		}
		if (number == 2) {
			var tfname = true;
			var arrname = ["区", "县", "乡", "镇", "港", "巷", "旗", "路", "环", "街",
					"湾", "号"];
			$.each(arrname, function(key, value) {
						if (lastname == value) {
							tfname = false;
						}
					});
			if (tfname) {
				alert("输入错误!请填写以区, 县,乡,镇,港,巷,旗,路,环,街为结尾的地址名称");
				$('input#districtNames').val("");
				retoken = 0;
				return;
			}

		}
		$("#newdistrict").jqmHide();
		$("#jqModel").jqmShow();
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_saveDistrict.jspa?district.districtPID="
				+ encodeURI(PID) + "&district.districtName="
				+ encodeURI(districtName) + "&date4=" + new Date();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var sdistrict = member.sdistrict;
				$op1 = $("<option selected='selected'/>").attr("value",
						sdistrict.districtID).attr("id", sdistrict.districtID)
						.text(sdistrict.districtName);
				$("#" + sdistrict.districtID, $td).remove();
				$(rovince, $td).append($op1);
				districtPID = sdistrict.districtID;
				//var params = {
				//	"districtPID" : districtPID
				//};
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				$add = "<option class='add'  value = '" + districtPID
						+ "' >--新增--</option>";
				$(rovince, $td).next().append($add);
				$td.find('input#address').val(districtPID);
				retoken = 0;
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});

	});

	//加载地址	
	if(showType=='add'){
		$("form#box1Form").find(".isHide").removeClass("isHide");
		$("span.statusinfo").hide();
		$("a.mord").hide();
		$("a#mord2").show();
		LiuZhongYaoDeShaGuaDiZhi("");
	}else {
		$("#industryType").hide();
		$("#comPro").hide();
		$("#comScale").hide();
		$("#tta").hide();
		$("#tta1").hide();
		$(".JQueryaddress").find("select").hide();
		$("tr#tools").hide();
		LiuZhongYaoDeShaGuaDiZhi($("span#address").text());
	}
		
});
//处理客户咨询
function checkAsk() {
	
	$("#box1Form").attr("target", "hidden").attr("action",
			basePath + "/ea/companytrack/ea_updateCustomerAsk.jspa");
	document.getElementById("box1Form").submit();
	token = 2;
}
//客户咨询列表
function re_load(){
	if(token){
		 document.location.href=basePath+"/ea/companytrack/ea_getCustomerAskList.jspa";
	}
}



