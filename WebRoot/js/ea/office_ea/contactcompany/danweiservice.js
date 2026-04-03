$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');
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
	var i = 0;
	$("div.showorhide").each(function(){
		if(this.id == '1'){
			i++;
			$(this).show();
			$("#"+this.name).attr("checked",true);
			if(i == 15){ //为全选状态
				$(".oroupboxAll").attr("checked",true);
			}
			if(showType == 'edit'){ 
				$(this).find("a:first").click();
			}
		}
	});
	$("#danweichoice").click(function(){
			$("#checkopertionID",$("#bankJqm")).attr("value",'partnerID'); 
				 $("#checkform",$("#bankJqm")).attr("value",'table3');
				 $("#checkopertionName",$("#bankJqm")).attr("value",'partnerName');
				 $("#childopertionName",$("#bankJqm")).attr("value",'childPartnerName');
			  	$("#daoRu").attr("src",basePath+'ea/companytrack/ea_getcompanyForCashier.jspa');
			  	 $("#bankJqm").jqmShow();
	});
	$("#DaoRuFan").click(function(){
	 $("#bankJqm").jqmHide();
	});

	$(".JQuerySubmits").click(function(){ //编辑菜单保存
		var formData = $("#danweiForm").serialize();
		formData = decodeURIComponent(formData,true);
		var url = basePath + "ea/companytrack/sajax_ea_saveCompanyTrack.jspa?";
		$.ajax({
				url: encodeURI(url + formData),
				type: "get",
				async: true,
				dataType: "json",
				success: function(data){
					
					if(showType == 'add'){
						window.location.href = basePath+"ea/companytrack/ea_getCompanyList.jspa?showType=add";
					}else{
						var ccompanyID = $("#ccompanyID").text();
						window.location.href = basePath+"ea/companytrack/ea_getCompanyList.jspa?contactCompany.ccompanyID="+ccompanyID+"&showType=edit";
					}
				},
				error:function(data){
					alert('保存失败！');
				}
		});
	});
	
	
	$("#tosave").click(function(){
		//onclick="toSave('box1Form','/ea/contactcompany/ea_saveContactCompany.jspa')"
		var childopertionID = window.frames["daoRu"].opertionID;
		if($("#companyName").val()==""){
		alert("请选择往来单位！");
		return false;
		}else{
			$("#box1Form").attr(
									"action",
									basePath
											+ "/ea/companytrack/ea_saveCompanyTrack.jspa?contactCompany.ccompanyID="+childopertionID);
			document.box1Form.submit.click();
		}
	});
	

});
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
				$("span.staffIdentityCard").show();
				$("tr#tools").show();
				break;
			default:
				$("div#box"+menuid).show();
				
				var personvalue = $("span#staffID").text();
	
				if ($("#companyName").val()=='') {
					alert("请先选择往来单位！");
					return;
				}
				var personurl = basePath + $("#mainframe"+menuid).attr("url");
				$("#mainframe"+menuid).attr("src", personurl,personvalue);
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
