$(document).ready(function() {
	 $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
//                .jqDrag('.drag');// 添加拖拽的selector
    if(usetype=="ck"){
    $('.JQueryflexme').flexigrid({
		allDouble:true,
        height: 120,
        width: 'auto',
        minwidth: 30,
        title: '当前库房：' +parent.tree.getSelectedItemText(),
        minheight: 80,
		 buttons: [{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
    });
    }else{
    $('.JQueryflexme').flexigrid({
		allDouble:true,
        height: 120,
        width: 'auto',
        minwidth: 30,
        title: '当前库房：' +parent.tree.getSelectedItemText(),
        minheight: 80,
		 buttons: [ 
		{
            name: '添加下级',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, 
        {
          separator: true
         }, {
            name: '删除下级',
            bclass: 'delete',
             onpress: action//当点击调用方法
            }, {
          separator: true
             }, {
             name: '修改下级',
             bclass: 'edit',
             onpress: action//当点击调用方法
          }, {
           separator: true
          },{
            name: '排序下属代码',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        },{
            name: '库房责任人',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
    });
    
    }
    function action(com, grid){
        switch (com) {
            case '添加下级':
                depotID = "";
                $("input.JQuerypersonvalue").attr("checked", false);
               if ("" == parent.treeid) {
                    alert("请选择代码");
                    return;
                }
               document.cstaffForm.reset();
               $("#jqModel").jqmShow();
               break;
			 case '修改下级':
               if (depotID == "") {
                                alert('请选择下级!');
                                return;
                            }
                            document.cstaffForm.reset();
                            $t = $("div#jqModel");
                            $p = $("tr#" + depotID);
                            $p.find("span[id]").each(function(){
                                $t.find(":input[name]#" + this.id).val($(this).text());
                            });
                      $("#jqModel").jqmShow();
                      break;  
             case '删除下级':
                            if (depotID == "") {
                                alert('请选择下级！');
                                return
                            }
                            $f = $('#cstaffForm');
                            $f.find(':input#depotID').val(depotID);
                            if (confirm("确定继续？")){
	                            $("#cstaffForm").attr("target","hidden").attr("action", basePath+"ea/cdepotmanage/ea_delDepotManage.jspa?pageNumber="+pNumber);
	                            document.cstaffForm.submit.click();
	                            $("tr#"+depotID).remove();
	                             depotID = "";
	                             token = 11;
                            } 
                            break;	
		   case '查询':
                    $("#jqModelSearch").jqmShow();
                    break;
            case '设置每页显示条数':
			  pNumber = prompt("输入显示条数","请输入小于50正整数");
			   if(pNumber <0 || pNumber!=parseInt(pNumber)||pNumber>50) 
				{ 
				    alert("请输入小于50的正整数"); 
				    return;
				} 
			   parent.document.getElementById("mainframe").src=basePath+"ea/cdepotmanage/ea_getListDepotManageTree.jspa?pageNumber="+pNumber+"&depotID="+depotPID+"&usetype=ck";
				break; 
		case '排序下属代码':
				$("#oID").val(parent.treeid);
			
				$("#oName").val(parent.treename);
		 		$("#sortchildren").attr("action",basePath+"ea/cdepotmanage/ea_toSortChildDepotManage.jspa?pageNumber="+pNumber);
		        document.sortchildren.submit.click();
				break;	
		  case '库房责任人':          
                  if (depotID == "") {
                              alert("请选择具体库房！");
                              return;
                         }
                  personurl = basePath + "/ea/cdepotperson/ea_getListDepotPerson.jspa?pageNumber="+pNumber+"&depotPerson.depotID=";
               $("#mainframe").css({"height":"auto"}).attr("src", basePath + "ea/cdepotperson/ea_getListDepotPerson.jspa?depotPerson.depotID="+depotID+"&pageNumber="+pNumber);
               $(window).resize();
              break;
        }
    }
	//点击选中
    $(".JQueryflexme tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    depotID =this.id;
                    if(personurl){                              
                    $("#mainframe").attr("src",personurl +depotID);
                    }
                    depotName = $(this).find("span#depotName").text();   
                     
                });
    $(".JQueryflexme tr[id]").dblclick(function(){
                    action('修改下级');//当双击时出发 action方法.等价于先选中再点击修改按钮
          });
      $("input.JQuerySubmit").click(function(){// 保存
                    if ($("form .error").length) {
                        return;
                    }
                    if ($("input.depotCoding").val()=='') {
                    	alert("仓库编码不能为空");
                    $("input.depotCoding").css("background-color","red");
                        return;
                    }
                    
                    if ($("input.depotName").val()=='') {
                    	alert("仓库名称不能为空");
                    $("input.depotName").css("background-color","red");
                        return;
                    }
                      if (depotID == ""){
	                    $("#cstaffForm").attr("target","hidden").attr("action", basePath+"ea/cdepotmanage/ea_saveDepotManage.jspa?pageNumber="+pNumber+"&depotManage.depotPID="+depotPID);
	                    document.cstaffForm.submit.click();
	                     document.cstaffForm.reset();
                        token = 1;
	                    return;
	                  }
	                    $("#cstaffForm").attr("target","hidden").attr("action",basePath+ "ea/cdepotmanage/ea_saveDepotManage.jspa?pageNumber="+pNumber+"&depotManage.depotPID="+depotPID);
                     document.cstaffForm.submit.click();
                     token = 2; 
                    
                });
                $("input.JQueryreturn").click(function(){// 取消
                    $("#jqModel").jqmHide();
                    re_load();
                }); 
                 $(".JQueryflexme").find("select").each(function(){
                    $s = $(this).hide();
                    $o = $("<span/>").text($s.find("option:selected").text());
                    $o.insertAfter($s);
                }) ;
                 $(".close").click(function(){// 取消
                    $("#jqModel").jqmHide();
                    re_load();
                   
                });    
                
                
});
 function re_load(){
     if(token)
    parent.document.getElementById("mainframe").src=basePath+"ea/cdepotmanage/ea_getListDepotManageTree.jspa?usetype=ck&pageNumber="+pNumber+"&depotID="+depotPID+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
 }
 $(document).ready(function(){
                                  
									     var getListCCodeurl=basePath+"ea/cdepotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID="+depotPID+"&date="+new Date().toLocaleString();
									     $.ajax({
						                        url: encodeURI(getListCCodeurl),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(data){
						                           var member = eval("("+data+")");
						                           var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath+"page/ea/not_login.jsp";
								                  }
										           var depotManagelist = member.depotManagelist;
										           if(null == depotManagelist){
										              return;
										           }    
										             window.parent.tree.deleteChildItems(depotPID);
										            for(var i=0;i<depotManagelist.length;i++)
												   {
										             window.parent.tree.insertNewChild(depotManagelist[i].depotPID,depotManagelist[i].depotID,depotManagelist[i].depotName,0,0,0,0);
										         
										           }
						                        },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
 });