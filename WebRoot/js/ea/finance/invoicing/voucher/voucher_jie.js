$(function() {
	
	var query ="<form method='post' name='SearchForm' id='SearchForm'><input type='submit' style='display:none;' name='submit' /><input type='hidden' name='search' value='search'/>凭证月结"	
		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月结年月：<input id='stime' name='stime' onfocus='\cher_dm(this);\' style='width: 85px' />至<input id='etime' name='etime' onfocus='\cher_dm(this);\' style='width: 85px' />" +
				"&nbsp;&nbsp;<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/>"
		+ "</form>";
	
	  
	
	$('.flexme11').flexigrid({
		height : 300,
		width : 'auto',
		minwidth : 30,
		title : query,
		minheight : 80,
		buttons : [{
					// 设置分割线
					separator : true
			},{
					name : '添加',
					bclass : 'add',
					onpress : action
					// 当点击调用方法
			},{
					separator : true
			},{
					name : '保存',
					bclass : 'store',
					onpress : action
					// 当点击调用方法
			},{
					separator : true
			},{
					name : '删除',
					bclass : 'delete',
					onpress :action 
					// 当点击调用方法
			},{ 	
					separator : true				
			},{
				name : '放弃',
				bclass : 'delete',
				onpress :action 
				// 当点击调用方法
			},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
					// 当点击调用方法
			},{
					separator : true
			}]
	});


	function action(com, grid) {
		switch (com) {
			case '添加' :
				if($(".checkgoods").size()>0){
					alert("不能批量添加多条月结资料！");
					return;
				}
				 $("#kelong").before($("#kelong").clone(true)
						 .attr("id","kelong" + select)
						 .addClass("checkgoods"));
				 $("#kelong" + select).find(":input").each(function(){
					 if($(this).attr("id")=="com_id"){
						 $(this).attr("name","dtInvMco.companyid");
					 }
					 if($(this).attr("id")=="y_m"){
						 $(this).attr("name","dtInvMco.yearmonth");
					 }
					 if($(this).attr("id")=="mcos_id"){
						 $(this).attr("name","dtInvMco.mcostaffid");
					 }
					 if($(this).attr("id")=="mco_date"){
						 $(this).attr("name","dtInvMco.mcodate");
					 }
					 $(this).attr("id",this.id+select);
				 });
				 
				 var dt=new Date();
				 var year=dt.getYear()+1900;   
		         var month=dt.getMonth()+1;   
		         var date=dt.getDate();   
		         var hour=dt.getHours();   
		         var minute=dt.getMinutes();   
		         var second=dt.getSeconds();
		         $("#mco_date"+select).val(String(year)+String(month)
		        		 +String(date)+String(hour)+String(minute)
		        		 +String(second));
		         
				$(".checkgoods").show();
				select++;
				break;
			case '保存' :
				var searchurl =  basePath+"/ea/mco/sajax_ea_getmy.jspa?mco_ym="+$("#y_m"+(select-1)).val();
				$.ajax({
					url : encodeURI(searchurl+"&date="+ new Date().toLocaleString()),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath + "page/ea/not_login.jsp";
						}
						var c=member.c;
						var b=member.b;
						if(b){
							if(c>0){
								var ym=$("#y_m"+(select-1)).val();
								var y=ym.substring(0,4);
								var m=ym.substring(4,6);
								alert(y+"年"+m+"月凭证已经结转！不能再次结转！");
							}else{
								if($(".checkgoods").size()>0){
									$("#mcoform").attr("target", "hidden")
										.attr("action",basePath+"/ea/mco/ea_save.jspa?");
									document.mcoform.submit.click();
									token = 2;
								}
							}
						}else{
							alert("年月字段不合理！请重新输入！");
						}
					}
				});
				break;
			case '删除' :
				if(mco_id==null||mco_id==""){
					alert("请选择要删除的数据");
					return;
				}
				var searchurl =  basePath+"/ea/mco/sajax_ea_getmy.jspa?mco_ym="+$("#"+mco_id).find("span#ny_time").text();
				$.ajax({
					url : encodeURI(searchurl+"&date="+ new Date().toLocaleString()),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath + "page/ea/not_login.jsp";
						}
						var b=member.b;
						if(b){
							var searchurl =  basePath+"/ea/mco/sajax_ea_delGetMco.jspa?mco_id="+mco_id;
							$.ajax({
								url : encodeURI(searchurl+"&date="
										+ new Date().toLocaleString()),
								type : "get",
								async : true,
								dataType : "json",
								success : function cbf(data) {
									var member = eval("(" + data + ")");
									$("tr#"+mco_id).remove();
									
								}
							});
						}else{
							alert("该月归属的会计年度已进行年度结转，不可删除！");
						}
					}
				});
				
				break;
			case '放弃' :
				if(mco_id=="kelong"+(select-1)){
					$("#kelong"+(select-1)).remove();
					select--;
					break;
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/accountant/ea_getAccountantList.jspa?search="
						+ search +"&sdate="+sdate+"&edate="+edate;
				numback(url);
				break;
			}
		}
	
	$("tr.mcotr").click(function(event) {
		$(this).find("input.JQuerypersonvalue").attr("checked", "true");
		mco_id = $(this).attr("id");
	});
	
	$("#tosearch").click(function(){
		$("#SearchForm").attr(
				"action",
				basePath + "/ea/mco/ea_getSerch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
});

function re_load(){
	document.location.href = basePath+ "/ea/mco/ea_getSerch.jspa?pageNumber="+ pNumber;
}