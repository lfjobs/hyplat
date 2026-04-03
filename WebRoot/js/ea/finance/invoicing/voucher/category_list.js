$(function(){
	var query = "<form name='searchForm' id='searchForm' method='post' action='ea/category/ea_getVoucherCategory.jspa'>" +
			"<table border='0' id='searchtbl' ><tr><td><strong>凭证类别</strong></td><td>&nbsp;&nbsp;&nbsp;</td><td>类别名称：</td><td>" +
			"<input name='name' style='height:18px;width:100px;'type='text' value=''/>&nbsp;&nbsp;&nbsp;" +
			"<td>类别代号</td><td><input type='text' name='code' style='height:18px;width:100px;' value=''></td>" +
			"<td>类别简称</td><td><input type='text' name='abbreviation' style='height:18px;width:100px;' value=''>"+
			"<input  type='submit' value='查询' id='tosearch' style='margin:2px;'class='input-button'/>" +
			"</td></tr></table></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title : query,
		minheight : 80,
		buttons : [{
			name : '添加',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '保存',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '修改',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '删除',
			bclass : 'delete',
			onpress : action
				// 当点击调用方法
			},{
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}]
	});
	function action(com, grid) {
		switch (com) {

			case '添加' :
				var aut=authority();
				if(aut=="not"){
					alert("抱歉，您没有此权限");
					break;
				}
				if(select>1){
					alert("请先保存");
					break;}
				var tr=$("#tr").clone(true).addClass("trOne");
				tr.find("input").each(function(index){
					if(index!=0)
					$(this).attr("name","invt["+select+"]."+$(this).attr("name"));
				});
				tr.find("select").attr("name","invt["+select+"]."+tr.find("select").attr("name"))
				$(".fexlist").append(tr.removeAttr("style").addClass("inv"));
				select++;
				break;
			case '保存' :
				if(Id!=""){
					var cla=$("#"+Id).find("input").eq(2).attr("class");
					if(cla!=""){
						alert("请选择修改或新添加的内容");
						break;
					}
				}
				if(status=="up"&&Id!=""){
					upd(Id);
				}else{
					add();		
				}
			
				break;
			case '修改' :
				var aut=authority();
				if(aut=="not"){
					alert("抱歉，您没有此权限");
					break;
				}
				if(select>1){
					alert("请先保存");
					break;}
				if(Id==""||Id==null){
					alert("请选择一条数据，不能选择未保存的");
					return;
				}
				$("tr#"+Id).find("input").attr("readonly","").each(function(index){
					if(index!=0)
					$(this).attr("name","invt["+select+"]."+$(this).attr("name"));
				});	
				$("#"+Id).find("span").attr("style","display: none");
				$("#"+Id).find("input").removeClass("model1");
				var stu=$("tr#"+Id).find("td").eq(6).text();
				if(stu=="Y"){
					$("tr#"+Id).find("td").eq(6).html("<select class='le' name='invt["+select+"].VTPd"+"' style=\"position: relative; left:23px\">" +
						"<option value=Y>Y</option><option value=N>N</option></select>");
				}else{
					$("tr#"+Id).find("td").eq(6).html("<select class='le' name='invt["+select+"].VTPd"+"' style=\"position: relative; left:23px\">" +
						"<option value=N>N</option><option value=Y>Y</option></select>");
				}
				select++;
				status="up";
				break;
			case '删除' :
					if(Id=="tr"){
						$(".trOne").remove();
						select--;Id="";
						break;}
					if(Id==""||Id==null){
						alert("请选择一条数据");
						break;
					}
					if(confirm("是否确认删除")){
						var url=basePath+"ea/category/ea_delVoucherCategory.jspa?vtId="+Id;
						$("#form").attr("target", "hidden").attr("action",url);
						document.form.submit.click();
						token = 2;
						}
				break;
			case '设置每页显示条数' :
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/category/ea_getVoucherCategory.jspa?pageNumber="+pr;
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
			
		}
	}
	//每行的单击事件
	$("tr[id]").click(function(){
		Id=this.id;
		$(this).find("td").find("input").attr("checked","checked");
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
	$(".subjecks").click(function(){
		if(!(this.id!="vtds"&&this.id!="vtcs"&&$(this).attr("readonly")!="")){
			vr(this.id);
			$("#subject").jqmShow();
		}
		
	});
	//科目树初始化
	tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
    tree.enableCheckBoxes(1);//设置复选框
    tree.enableThreeStateCheckboxes(false);
    tree.setSkin(basePath+"js/tree/dhx_skyblue");
    tree.setImagePath(basePath+"js/tree/codebase/imgs/");
    tree.loadXML(basePath+"js/tree/common/tree_b.xml");

    tree1 = new dhtmlXTreeObject("text_tree", "100%", "100%", 3);
    tree1.enableDragAndDrop(false);
    tree1.enableHighlighting(1);
    tree1.enableCheckBoxes(1);//设置复选框
    tree1.enableThreeStateCheckboxes(false);
    tree1.setSkin(basePath+'js/tree/dhx_skyblue');
    tree1.setImagePath(basePath+"js/tree/codebase/imgs/");
    tree1.loadXML(basePath+"js/tree/common/tree_a.xml");
    tree.setOnClickHandler(function(){
    	treeid = tree.getSelectedItemId();
    	if(tree.isItemChecked(treeid) == "0"){
    		tree.setCheck(treeid,1);
    	}else{
    		tree.setCheck(treeid,0);
    	}
    		
    });
    tree1.setOnClickHandler(function(){
    	treeid1 = tree1.getSelectedItemId();
    	if(tree1.isItemChecked(treeid1) == "0"){
    		tree1.setCheck(treeid1,1);
    	}else{
    		tree1.setCheck(treeid1,0);
    	}
    		
    });
    tree1.setOnDblClickHandler(function(){
       treeid1 = tree1.getSelectedItemId();
       tree1.deleteItem(treeid1);
       });
   tree.setOnClickHandler(function(){
      $(".input01").attr("value","");
      $("#desc").attr("html","");
	  treeid= tree.getSelectedItemId();
	  treename = tree.getItemText(treeid);
	  $("#codeName").attr("value",treename);
	  tree.deleteChildItems(treeid);
	  var getListCSbjectsurl=basePath+"ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID="+treeid+"&date="+new Date().toLocaleString();
	     $.ajax({
	           url: encodeURI(getListCSbjectsurl),
	           type: "get",
	           async: true,
	           dataType: "json",
	           success: function cbf(data){
		           var member = eval("("+data+")");
		           var nologin = member.nologin;
	              		if(nologin){
	              		document.location.href =basePath+"page/ea/not_login.jsp";
	                 }
		           var subjectsList = member.subjectsList;
		           if(null == subjectsList){
		              return;
		           }    
		            for(var i=0;i<subjectsList.length;i++)
				   {
				    
		             tree.insertNewChild(treeid,subjectsList[i].subjectsID,subjectsList[i].subjectsNumbers+" "+subjectsList[i].subjectsName,0,0,0,0);
		             tree.setUserData(subjectsList[i].subjectsID,"subjectsStatus",subjectsList[i].subjectsStatus);
		             tree.setUserData(subjectsList[i].subjectsID,"subjectsNumbers",subjectsList[i].subjectsNumbers);
		           }
	           },
	           error: function cbf(data){
	              alert("数据获取失败！");
	           }
       });
       $("#mainframe").attr(
			"src",basePath+"ea/csbjects/ea_getCsubejstsListAll.jspa?pageNumber="+basePath+"&subjectsID="+ treeid + "&treename=" + treename);    
       });
   

});




function vr(id){
  
         //单位下所有的部门列表
       var url = basePath +"ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=002";
       $.ajax({
                    url: url,
                    type: "get",
                    async: true,
                    dataType: "json",
                    success: function cbf(data){
	                var member = eval("(" + data + ")");
	                var nologin = member.nologin;
				                  if(nologin){
				                  document.location.href =basePath+"page/ea/not_login.jsp";
				                  }
				                  
	                var subjectsList = member.subjectsList;
	                var companylist = member.companylist;	     
	                if (null != subjectsList) {
	                      for (var i = 0; i < subjectsList.length; i++) {
	                      		tree.insertNewChild('0', subjectsList[i].subjectsID, subjectsList[i].subjectsNumbers+" "+subjectsList[i].subjectsName,0, 0, 0, 0);
	                      }
	                }
	            }, 
	             error: function cbf(data)
	             {
	                           alert("数据获取失败！");
	             }
	});
	
	function findChild(pare,vals){
			for (var i = 0; i < vals.length; i++) {
            	if(vals[i].organizationPID==pare){
            		tree.insertNewChild(pare, vals[i].organizationID, vals[i].organizationName, 0, 0, 0, 0);
            	}
            }
	}
    //确定按钮
    $("#rightdan").click(function(){
    	tree1.deleteChildItems("3");
    
        var treeids = tree.getAllCheckedBranches();
        var tids = treeids.split(",");
        
        for(var i = 0 ; i < tids.length ; i++){
       		treeid = tids[i];
        	if(treeid == "00a" || treeid == 0){continue;}
        	treename = tree.getItemText(treeid);
        	if(tree1.getItemText(treeid)){continue;}
         	tree1.insertNewChild("3",treeid,treename,0,0,0,0);
        }
        return;
    });
    //取消按钮
     $("#leftdan").click(function(){
    	 var treeids = tree1.getAllCheckedBranches();
         var tids = treeids.split(",");
         for(var i = 0 ; i < tids.length ; i++){
        	 treeid1 = tids[i];
        	 tree1.deleteItem(treeid1);
         }     
    });
     $("#leftsuang").click(function(){
       tree1.deleteChildItems(3);
    });   
     
     $(".jecks").click(function(){
     	if("确定"==$(this).val()){
     		var treeids = tree1.getAllSubItems("3");
             var tids = treeids.split(",");
             if(id=="")
             	return;
             $("."+id).each(function(){
     				if($(this).val()!="")
     					$(this).val("");
     				if(tids==""){
     					$(this).val("");return;}
     				for(var i=0;i<tids.length;i++){
     					if(i!=0)
     						$(this).val($(this).val()+",");
     						var s=tree1.getItemText(tids[i]).split(" ");
     						
     	            	$(this).val($(this).val()+s[0]);   	            	    	            	
     	            }
     			
     		});
             $("#subject").jqmHide();
             tree.deleteChildItems('0');
             id="";
     	}else{
     		id="";
     		 tree1.deleteChildItems(3);
     		$("#subject").jqmHide();
     		tree.deleteChildItems('0');
     	}
     });
    
	
}
function add(){
	var bl=true;
	if(!$(".invc").eq(1).attr("checked")){
		alert("请选择新添加的内容");
		return;
	}
	$(".inv").find("td").find("input").each(function(index){
		if(index!=0){
			if(this.id!="vtr"&&this.id!="vtds"&&this.id!="vtcs"){
				if($(this).val()==""){
					alert("除备注说明和科目外，其他为必填项");
					bl=false;
					return false;
				}
			}
		}
	});
	$.ajax({
		url:basePath +"ea/category/sajax_ea_getVoucherCategorys.jspa",
		type:"post",
		async: false,
		success:function(data){
			var member = eval("("+data+")");
			var list=member.list;
			var vt1=$(".inv").find("td").find("input").eq(1).val();
			var vt2=$(".inv").find("td").find("input").eq(2).val();
			var vt3=$(".inv").find("td").find("input").eq(3).val();
			if(vt1.length>10||vt2.length>10){
				alert("代号或简称的长度最高为10");
				bl=false;return;
			}
			for(var i=0;i<list.length;i++){							
				if(list[i].VTDh==vt1){
					alert("代号已存在，请更换");
					bl=false;
					return;
				}
				if(list[i].VTJc==vt2){
					alert("简称已存在，请更换");
					bl=false;
					return;
				}
				if(list[i].VTName==vt3){
					alert("名称已存在，请更换");
					bl=false;
					return;
				}
				if(list[i].VTPd=="Y"&&$(".sel").eq(1).val()=="Y"){
					alert("主账号已存在，请更改为虚账号");
					bl=false;
					return;
				}
				
			}
		},
		error:function(data){
			alert("数据获取失败");
			return;
		}
	});
	if(bl){					
		var url=basePath+"ea/category/ea_addVoucherCategory.jspa";	
		$("#form").attr("target", "hidden").attr("action",url);
		document.form.submit.click();
		token = 2;
	}
}
function upd(id){
	var bl=true;
	var i=0;
	$("tr#"+Id).find("input").each(function(){
		if(i!=5&&i!=6&&i!=7){
			if($(this).val()==""){
				alert("除备注说明和科目外，其他为必填项");
				bl=false;
				return false;
			}
		}
		i++;
	});
	$.ajax({
		url:basePath +"ea/category/sajax_ea_getVoucherCategorys.jspa",
		type:"post",
		async: false,
		success:function(data){
			var member = eval("("+data+")");
			var list=member.list;
			var vt1=$("tr#"+Id).find("input").eq(2).val();
			var vt2=$("tr#"+Id).find("input").eq(3).val();
			var vt3=$("tr#"+Id).find("input").eq(4).val();
			if(vt1.length>10||vt2.length>10){
				alert("代号或简称的长度最高为10");
				bl=false;return;
			}
			for(var i=0;i<list.length;i++){	
				if(list[i].VTId==id)
					 continue;
				if(list[i].VTDh==vt1){
					alert("代号已存在，请更换");
					bl=false;
					return;
				}
				if(list[i].VTJc==vt2){
					alert("简称已存在，请更换");
					bl=false;
					return;
				}
				if(list[i].VTName==vt3){
					alert("名称已存在，请更换");
					bl=false;
					return;
				}
				if(list[i].VTPd=="Y"&&$(".le").val()=="Y"){
					alert("主账号已存在，请更改为虚账号");
					bl=false;
					return;
				}
				
			}
		},
		error:function(data){
			alert("数据获取失败");
			return;
		}
	});
	if(bl){					
		var url=basePath+"ea/category/ea_updVoucherCategory.jspa?vtId="+Id;
		$("#form").attr("target", "hidden").attr("action",url);
		document.form.submit.click();
		token = 2;
	}
}

//查询是否有添加的权限
function authority(){
	var s="";
	$.ajax({
		url:basePath+"ea/category/sajax_ea_authority.jspa",
		type:"post",
        async: false,
		success:function(data){
			var member = eval("("+data+")");
			s=member.authority;
		},
		error:function(data){
			alert(">>>>");
		}		
	});
	return s;
}
 		
function re_load(){
	document.location.href = basePath+ "ea/category/ea_getVoucherCategory.jspa";
}