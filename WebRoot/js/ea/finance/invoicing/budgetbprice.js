var deatch2="";
$(function() {
		// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	

	$("#col").click(function(){
		
		$(".audit").hide();
		
	});
	
	
	
	if(deptpost!=""){
		$("."+deptpost).val($("#staffaudit").val());
	}
	if(bjstatus=="yqrbj"){
		$("#audittbl").hide();
	}else{
		$("#audittbl").show();
	}
	
 $(".btncon").click(function(){

	 var tip = "确认至未确定比价";
	 if(bjstatus=="wqrbj"){
		 tip = "确认至已确定比价";
	 }
	 if(confirm(tip)){
	 	$(".btncon").attr("disabled",true);
    $(".inputbottom").val("");
	$(this).parent().find(".inputbottom").val($("#staffaudit").val());
	$("#deptpost").val(this.id);
	
	
	var cahiserBillsIDs="";
	var goodsBillsIDs="";
	$("#tb :checkbox:checked").each(function(){
		var goodsBillsID = $(this).val();
		var cashierBillsID = $("tr#"+goodsBillsID).find("#cashierBillsID").text();
		goodsBillsIDs+=$.trim(goodsBillsID+",");
		cahiserBillsIDs+=$.trim(cashierBillsID+",");
	});
	
	$("#csdIDss").val(goodsBillsIDs);
	$("#csbIDss").val(cahiserBillsIDs);
	$("#remarks").val($("#remark").val());
	

	$("#searchForm").attr("target","hidden").attr("action",basePath+"ea/product/ea_confirmBpirce.jspa");
	document.searchForm.submit.click();
	token = 2;
	
	 }
 });	

    
//
//  $("#tree").find("span").click(function(){
//	  
//	  var xmtypename = $.trim($(this).text().substring($(this).text().lastIndexOf(")")+1));
//	  var xmtype = $.trim($(this).text().substring($(this).text().indexOf("(")+1,$(this).text().lastIndexOf(")")));
//	  $("#xmtypeddd").val(xmtype);
//		
//		if($("#xmtypeddd").val()!=""){
//			
//			$("#jqModel").find(".confirm").attr("disabled", false);
//		}else{
//			$("#jqModel").find(".confirm").attr("disabled", true);
//		}
//		
//		 $("#tree").find("span").each(function(){
//			
//			 $(this).css("color","");
//			
//			 
//			 
//		 });
//		 $(this).css("color","red");
//	  
//  });
    
    //确定按钮起不起作用
	    $("#jqModel").click(
			function() {
				if ($("#jqModel").find("#xmul").length
						|| $("#jqModel").find("#gdul").length||$("#jqModel").find("#treeBox").length) {
					if ($("#jqModel :checkbox:checked").length != 0) {
						$("#jqModel").find(".confirm").attr("disabled", false);
					} else {
						$("#jqModel").find(".confirm").attr("disabled", true);
					}
				} 

			});

    	
	    
	    
	    //列表Checkbox
		$("#tb tr").click(function() {
			$("input.JQuerypersonvalue", $(this)).attr("checked", !$("input.JQuerypersonvalue", $(this))
					.attr("checked"));
			
			 if($("#tb :checkbox:checked").length>0){
					
				  $(".btncon").attr("disabled",false);
			  }else{
				
				  $(".btncon").attr("disabled",true);
			  }
			

		});
		
		
		// 用于阻止复选框的冒泡行为；
		$("input.JQuerypersonvalue").click(function(event) {
					event.stopPropagation();

				});




//列表全选
$("#lqx").click(function(){
	$("#tb :checkbox").attr("checked", $(this).attr("checked"));
	if($("#tb :checkbox:checked").length>0){
		
		  $(".btncon").attr("disabled",false);
	  }else{
		
		  $(".btncon").attr("disabled",true);
	  }
	
}
);

//通过按钮是否起作用
$("#tb :checkbox").click(function(){
  if($("#tb :checkbox:checked").length>0){
	
	  $(".btncon").attr("disabled",false);
  }else{
	
	  $(".btncon").attr("disabled",true);
  }
	
});

//通过
$("#pass").click(function(){
	var cahiserBillsIDs="";
	var goodsBillsIDs="";
	$("#tb :checkbox:checked").each(function(){
		var goodsBillsID = $(this).val();
		var cashierBillsID = $("tr#"+goodsBillsID).find("#cashierBillsID").text();
		goodsBillsIDs+=$.trim(goodsBillsID+",");
		cahiserBillsIDs+=$.trim(cashierBillsID+",");
	});
	
	$("#csdIDss").val(goodsBillsIDs);
	$("#csbIDss").val(cahiserBillsIDs);
	

	$("#searchForm").attr("action",basePath+"ea/product/ea_confirmBpirce.jspa");
	document.searchForm.submit.click();
});


//键入查询项目分类
$("#xmfl").keyup(function(){
	getxmtype();
	
});
    
	
});

//点击下拉框按钮
function getPID(type,obj) {
		$("#xmtypeddd").val("");
		$("#ddate").val("");
		$("#jqModel").find(".confirm").attr("disabled", true);
		var left = $(obj).position().left;
		var top = $(obj).position().top;

    	if(document.getElementById("jqModel").style.display=="none"){
    		$(obj).children().attr("src",basePath + "images/down.jpg");
              $("#jqModel").css({
				position : "absolute",
				left : left-190,
				top : top+18
			}).show();
             var deatch = $("#treeBoxshow").detach();
             var deatch3 = $("#gdul").detach();
              if(type=="xm"){
     		     getProjectList();
     		     $("#channelsDialog").html(deatch);
       		    $("#goodsearch").html(deatch3);
              }
              if(type=="good"){
          		      $("#jqModel").html(deatch3);

          		     $("#channelsDialog").html(deatch);
               }
              
              if(type=="xmtype"){
            	$("#jqModel").html(deatch);
                $("#goodsearch").html(deatch3);
            	  getxmtype();
                 }
              
              if(type=="zddate"||type=="kydate"){
            	  $("#channelsDialog").html(deatch);
                   $("#types").val(type);
            	   getDate(type);
            	   deatch2 = $("#treeDate").detach();
             	  $("#jqModel").html(deatch2);
        		    $("#goodsearch").html(deatch3);
                  }
              

             
		}else{
			$(obj).children().attr("src",basePath + "images/up.jpg");
			$("#jqModel").hide();
		}
	}
	
	
	/**
	 * 
	 * 获取项目名称列表
	 */
	function getProjectList(){
		
		var url = basePath+"ea/product/sajax_n_ea_ajaxProjectList.jspa";
		$.ajax({
			url:url,
			type:"get",
			dataType:"json",
			async:false,
			success:function(data){
				var mem = eval("("+data+")");
				var projectList = mem.projectList;
				var str = "<ul id='xmul'><li>&nbsp;&nbsp;<input name='xm' type='checkbox' id='xmqx' onclick=\"qx('xmqx','xmul');\" value='qx'/><label for='xmqx'>全选</label></li>";
				var obj = "";
				var test = "";
				for ( var i = 0; i < projectList.length; i++) {
					 obj = projectList[i];
					 if(test.indexOf(obj.projectName)==-1){
						 str+="<li>&nbsp;&nbsp;<input name='xm' id='"+obj.ppID+"' type='checkbox' value='"+obj.goodsName+"'/><label for='"+obj.ppID+"'>"+obj.goodsName+"</label></li>";
					 }
					 
				}
				str+="</ul></br>&nbsp;&nbsp;<input type=\"button\" value=\"  确定  \" disabled=\"true\" onclick= \"submit('xmul','proname');\" class='input-button confirm'/><input class='input-button' type=\"button\" value=\"  取消  \"  onclick='cancel();'/>";
				$("#jqModel").html(str);
				
//			    if(results=="first"){
//			    	$("#xmul :checkbox").attr("checked",true);
//			    }
				
			},error:function(data){
			
		  }
			
		});	
		
	}
	


//全选
function qx(qxid,ulid) {
	//全选
	$("#"+ulid+ " :checkbox").each(function() {
		$(this).attr("checked", $("#"+qxid).attr("checked"));
		
	});

}



//确定
function submit(ulid,positionid){
	//获取选中的值
	var values = "";
	 
    if (ulid=="treeDate"){
		 values = $("#ddate").val();
			
	 }else{
	$("#"+ulid+" input:checked").each(function(){
		values+=$(this).val()+",";
		
	});
	  values = values.substring(0,values.length-1);
    }
    
	$("#"+positionid).val(values);
	$("#searchForm").attr("action",basePath+"ea/product/ea_getBudgetGoodList.jspa");
	document.searchForm.submit.click();

	
}

function cancel(){
	
	 $("#tablemain").find("img").attr("src",basePath + "images/up.jpg");
		$("#jqModel").hide();
}



//获取时间
function getDate(type){
	

       if(deatch2!=""){
    		$("#dateDialog").html(deatch2); 
       }
       $("#treeDate").html("");
	    tree1=new dhtmlXTreeObject('treeDate',"100%","100%",0);
	    tree1.enableDragAndDrop(false);
		tree1.enableHighlighting(1);
		tree1.enableCheckBoxes(1);//复选框
		tree1.enableThreeStateCheckboxes(true);//允许半选
		tree1.setSkin(basePath + 'js/tree/codebase/imgs/csh_dhx_skyblue');
		tree1.setImagePath(basePath + "js/tree/codebase/imgs/csh_dhx_skyblue/");
		tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
	   
	    tree1.enableTreeImages("-Icons");//设置是否显示图标
	   
	var getcodeurl = basePath
			+ "ea/product/sajax_n_ea_getDateList.jspa?date="
			+ new Date().toLocaleString();
	tree1.insertNewChild(0, "1", "全部", 0, 0, 0,
			0);
	$.ajax({
				url : encodeURI(getcodeurl),
				type : "get",
				async : true,
				dataType : "json",
				data:{
					type:type
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var   datelist= member. datelist;
					var   fdatelist= member. fdatelist;
					if (null ==  datelist) {
						return;
					}
					for (var i = 0; i <  datelist.length; i++) {
                     
						tree1.insertNewChild(
								"1",
								datelist[i], datelist[i], 0,
								0, 0, 0);

						getMonth(datelist[i],fdatelist);
						tree1.closeItem(datelist[i]);
					}
					
					 $("#treeDate").append("</br><input type=\"button\" value=\"  确定  \" disabled=\"ture\" onclick=\"submit('treeDate','startDates')\" class='input-button confirm'/><input class='input-button' type=\"button\" value=\"  取消  \" onclick='cancel();'/>");
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	
	
    tree1.setOnCheckHandler(function(){
    	$("#ddate").val(tree1.getAllChecked());
    if(tree1.getAllChecked()){
		$("#jqModel").find(".confirm").attr("disabled", false);
	}else{
		$("#jqModel").find(".confirm").attr("disabled", true);
	}
    });
	
}


/**
 * 
 * 获取月份
 */
function getMonth(selectdid, fdatelist) {
	  var arrayText = new Array("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");
	  var arrayValue = new Array("01","02","03","04","05","06","07","08","09","10","11","12");
	for ( var i = 0; i < fdatelist.length; i++) {
		var obj = fdatelist[i];
		var year = obj.substring(0, obj.indexOf("-"));
		
		if (year == selectdid) {
			var month = obj.substring(obj.indexOf("-") + 1, obj
					.lastIndexOf("-"));
			var monthtext = "";
		    for(var j = 0;j<arrayValue.length;j++){
			       if(arrayValue[j]==month){
			        monthtext = arrayText[j];
			        break;
			       }
			 }
			

			if (tree1.getSubItems(selectdid).indexOf(selectdid+"-"+month) == -1) {
				
				tree1.insertNewChild(selectdid,selectdid+"-"+month, monthtext, 0, 0, 0, 0);
				
				getDay(selectdid+"-"+month, fdatelist);
				tree1.closeItem(selectdid+"-"+month);
			}

		}
	}

}

/**
 * 
 * 获取日
 */
function getDay(selectdid, fdatelist) {

	for ( var i = 0; i < fdatelist.length; i++) {
		var obj = fdatelist[i];
		var mon = obj.substring(0, obj.lastIndexOf("-"));
		if (mon == selectdid) {
			var day = obj.substring(obj.lastIndexOf("-") + 1);
			if (tree1.getSubItems(selectdid).indexOf(obj) == -1) {
				tree1.insertNewChild(selectdid, obj, day+"日", 0, 0, 0, 0);
			}
		}
	}
	
	

}




//项目分类

function getxmtype(){
	var parameter = "";
	if($("#xmfl").val()!=""){
		parameter =$("#xmfl").val();
	}

		var url = basePath
				+ "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				parameter : parameter
			},
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var xmList = member.xmlist;
				
				var str="";

				for (var i = 0; i < xmList.length; i++) {
					//if(xmList[i].isLeaf =="00"){
						str+="&nbsp;&nbsp;<span style='color:#0066cc;'><input type='checkbox' name='xm' id='"+xmList[i].codeSn+"' value='"+xmList[i].codeSn+"'/><label for='"+xmList[i].codeSn+"'>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</label></span><br/>";
					//}else{
					//str+="&nbsp;&nbsp;<span>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</span><br/>";
					//}
				}
				if(str==""){
					str="&nbsp;无搜索结果";
				}
				
				$("#treeBox").html(str);


				

			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

}

function re_load(){
	if(token){
	document.location.href=basePath+"ea/product/ea_getBudgetGoodList.jspa?bjstatus="+bjstatus;
	}
}


//查看审核人记录
function showSee(goodsBillsID,obj) {
	var left = $(obj).position().left;
	var top = $(obj).position().top;

      $(".audit").css({
			position : "absolute",
			left : left-320,
			top : top
		}).show();

	var url = basePath
	+ "ea/product/sajax_n_ea_findAuditorList.jspa?date="
	+ new Date().toLocaleString();
$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"billCheck.newBillsID" : goodsBillsID
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var bclist = member.result;

			var trstr = "";

			for ( var i = 0; i < bclist.length; i++) {
				trstr += "<tr><td align=\"center\">" + bclist[i].auditorname + "</td><td align=\"center\">"
						+ bclist[i].audittime + "</td><td align=\"center\">"
						+ bclist[i].comments + "</td></tr>";

			}

			$("#record").html(trstr);
	
			

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

}
