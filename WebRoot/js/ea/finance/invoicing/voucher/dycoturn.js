$(function() {
	if(stas!=""){
		alert(stas);
	}
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.fexlist').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : false,
				minheight : 50
			});
	$("div.bDiv",$("div#flex")).css("height","500px");
	// 根据条件查询
	$("#tosearch").click(function() {
		$("#searchForm")
				.attr("action",
						basePath + "ea/dycoturn/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.searchForm.submit.click();
	});
	//确定作业
	$(".savedycturn").click(function() {
		var checkboxtype;
		var yearm=$("table#qdzy").find("input#yearm").val();
		var checkbox1=$("input[name='jiez']").attr("checked");
		var checkbox2=$("input[name='tjiez']").attr("checked");
		if(yearm==""){
			alert("请输入结转年月！");
			return;
		}
		if(!checkbox1&&!checkbox2){
			alert("选择结转或者是退结转！");
			return;
		}
		//凭证月结和会计期间限制
		var ym,ym1,ym2,ym3;
		var URL = basePath
		+ "ea/dycoturn/sajax_ea_getyearmonth.jspa?dyco_ym="
		+ yearm + "&date=" + new Date().toLocaleString();
		$.ajax({
			url : encodeURI(URL),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath
							+ "page/ea/not_login.jsp";
				}
				ym = member.a;
				ym1 = member.b;
			    ym2 = member.c;
			    ym3 = member.d;
			    
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
		//只选择了结转
        if(checkbox1&&!checkbox2){
        	//判断会计期间  最大年度  终止月份是否等于输入的结转年月的上一个月如果等于是 true
   		    if (ym1==false) {
   			alert("结转年月不是有效日期！");
   			return;
            }
   		    //年度结转 表是否有要结转年月重复数据
			if (ym==true) {
				alert("已经年结，不能重新年结!");
				return;
	        }
			//月结表中 yearmonth 有没有比输入年月大的  如果有是true
		   	if (ym2==true) {
			  if(!confirm("在该月份以后已有月结资料，是否需要重新月结？")){
				  checkboxtype="jie1";
				  return;
			  }
		    }
			checkboxtype="jiez";
		}
        //只选择了退结转
		if(!checkbox1&&checkbox2){
			 //查会计期间表，输入结转年月  等于 会计年度 最大的数据的起始年月。
			 if (ym3==false) {
					alert("退结转年月不是有效日期！");
					return;
			   }
			 //查年结表年结数据是否存在 不存在是false
			 if (ym==false) {
				alert("没有结转数据,不能退结转！");
				return;
		     }
		    checkboxtype="tjiez";
		}
		//结转 退结转都选择
        if(checkbox1&&checkbox2){
        	 if(confirm("是否退结转并重新年结？")){
				 //查会计期间表，输入结转年月  等于 会计年度 最大的数据的起始年月。
				 if (ym3==false) {
						alert("退结转年月不是有效日期！");
						return;
				 }
	        	 //查年结表年结数据是否存在 不存在是false
				 if (ym==false) {
					alert("没有结转数据,不能退结转！");
					return;
			     }
				//月结表中 yearmonth 有没有比输入年月大的  如果有是true
			   	if (ym2==true) {
				  if(!confirm("在该月份以后已有月结资料，是否需要重新月结？")){
					  checkboxtype="jie2";
					  return;
				  }
			    }
				 checkboxtype="all";
			 }
		}
		$("#saveTurnForm").attr("target", "hidden").attr("action",
						basePath + "ea/dycoturn/ea_SaveDyCoTurn.jspa?dyco_ym="
		+ yearm +"&dycotype="+checkboxtype+"&pageNumber="+ pNumber);
		document.saveTurnForm.submit.click();
		document.saveTurnForm.reset();
		token = 2;
	    return;
	});
});

$(function(){
	$(".orgId").each(function(){
		var orgid=$(this).val();
		var comid=$(this).parents("tr").find(".cliId").val();
		var comtext="";
		var orgtext="";
		$.ajax({
			url:basePath +"ea/dycoturn/sajax_ea_ajaxorganization.jspa?comid="+comid+"&orgid="+orgid,
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var cor=member.org;
				var com=member.com;
				orgtext=cor.organizationName;
				comtext=com.companyName;
			},
			error:function(data){
				alert(">>>");
			}
		});
		$(this).parent().find("span").text(orgtext);
		$(this).parents("tr").find("#clientname").text(comtext);
	});
});
function re_load() {
	    //window.location.reload();//刷新当前页面
		var url = basePath + "/ea/dycoturn/ea_toSearch.jspa?pageNumber="+ pNumber;
        document.location.href = url;
}