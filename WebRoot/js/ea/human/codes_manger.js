$(document).ready(function(){   
     tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree.insertNewChild("0","001","代码树",0,0,0,0);
			tree.insertNewChild("0","002","库存编码树",0,0,0,0);
			tree.setOnClickHandler(function(){
			                        $(".input01").attr("value","");
                                    $("#desc").attr("html","");
                                     $("#checkbox").attr("checked",false);
			                       treeid= tree.getSelectedItemId();
			                       treename = tree.getItemText(treeid);
			                        $("#usename").attr("value",treeid);
                                  	if(treeid != "001" ){ 
						  	            var url1=basePath+"ea/code/sajax_ea_toEditCode.jspa?codeID="+treeid+"&datesfsf="+new Date().toLocaleString();
									    $.ajax({
													url: encodeURI(url1),
													type: "get",
													async: false,
													dataType: "json",
													success: function cbf(data){
															 var member = eval("("+data+")");
															 var nologin = member.nologin;
								                  		if(nologin){
								                  		document.location.href =basePath+"page/ea/not_login.jsp";
								                     }
													           var code = member.code;
													             $("#userID").attr("value",code.codePID);
															      $("#userID1").attr("value",code.codeID);
															      parentname=tree.getItemText(code.codePID);
															      $("#username").attr("value",parentname);
															      $("#username1").attr("value",code.codeValue);
															      $("#desc").attr("value",code.codeDesc);
															      $("#codeKey").attr("value",code.codeKey);
															      if(code.codetype == "00")
															      $("#checkbox").attr("checked",true);
															      else
															      $("#checkbox").attr("checked",false);
													},
												error: function cbf(data){
												alert("数据获取失败！");
												}
									    });     
					        }
			                  var state= tree.getOpenState(treeid) ;
									  if("1" == state){
									  return;
									  }
							           tree.deleteChildItems(treeid);
						  	            var url1=basePath+"ea/code/sajax_ea_getListCode.jspa?codeID="+treeid;
									    $.ajax({
												 url: encodeURI(url1),
													type: "get",
													async: false,
													dataType: "json",
													success: function cbf(data){
															 var member = eval("("+data+")");
															 var nologin = member.nologin;
								                  		if(nologin){
								                  		document.location.href =basePath+"page/ea/not_login.jsp";
								                     }
													           var codeList = member.codeList;
													           if(null == codeList){
													              return;
													           }    
													            for(var i=0;i<codeList.length;i++)
															   {
													             tree.insertNewChild(treeid,codeList[i].codeID,codeList[i].codeValue,0,0,0,0);
													             tree.setUserData(codeList[i].codeID,"code",codeList[i].codeStatus);
													             tree.setUserData(codeList[i].codeID,"type",codeList[i].codetype);
													           }
													},
												error: function cbf(data){
												alert("数据获取失败！");
												}
											});
								  
		  	
			});
			$("#editid").click(function(){
                                $(".input01").attr("value","");
                                $("#desc").attr("html","");
                                 $("#checkbox").attr("checked",false);
                                treeid= tree.getSelectedItemId();
							     treename = tree.getItemText(treeid);
							  $(".input01").attr("value","");
							  $("#desc").attr("html","");
						      parentid = tree.getParentId(treeid)  ;
						      $("#userID").attr("value",treeid);
						      $("#username").attr("value",treename);
							  $("#usename").attr("value",treename);
   });
   
     $("#deleteid").click(function(){
      treeid= tree.getSelectedItemId();
      treename = tree.getItemText(treeid);
      codecode = tree.getUserData(treeid,"code");
      if("001" == treeid){ alert("根结点不能删除"); return;}
      if("" == treeid){ alert("请选择删除节点"); return;}
      if(confirm("删除节点'"+treename+"'会把其下面的所有子节点一起删除？是否删除？")){
				   document.all.codeForm.action=basePath+"code_ea_delCode.jspa?pageNumber="+pNumber+"&codeID="+treeid;
                   document.all.codeForm.submit();
    	 }
   });
});
	 	 