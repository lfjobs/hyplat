$(document).ready(function(){   
            tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree.insertNewChild("0","002","科目树",0,0,0,0);
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
										             tree.insertNewChild(treeid,subjectsList[i].subjectsID,subjectsList[i].subjectsName,0,0,0,0);
										             tree.setUserData(subjectsList[i].subjectsID,"subjectsName",subjectsList[i].subjectsName);
										             tree.setUserData(subjectsList[i].subjectsID,"subjectsID",subjectsList[i].subjectsID);
										             tree.setUserData(subjectsList[i].subjectsID,"subjectsNumbers",subjectsList[i].subjectsNumbers);
										             tree.setUserData(subjectsList[i].subjectsID,"currentLevel",subjectsList[i].currentLevel);
										             tree.setUserData(subjectsList[i].subjectsID,"subjectsPID",subjectsList[i].subjectsPID);
										           }
								               },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
						        $("#mainframe").attr(
									"src",basePath+"ea/csbjects/ea_getListBalanceByPID.jspa?pageNumber="+basePath+"&subjectsID="+ treeid + "&treename=" + treename);    
			});
			
			
		 	
});