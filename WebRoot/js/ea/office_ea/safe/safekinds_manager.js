var token = 0;
$(document).ready(function(){   
     tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false); 
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree.insertNewChild("0","001","安全类别",0,0,0,0);
			tree.setItemImage("0", basePath+"js/tree/codebase/imgs/","","");
			tree.setOnClickHandler(function(){
			                        if(token)return;
			                        token = 1;
			                        //$(".input01").attr("value","");
                                    //$("#desc").attr("html","");
			                        treeid= tree.getSelectedItemId();
			                        //treename = tree.getItemText(treeid);
			                        //$("#codeName").attr("value",treename);
							        tree.deleteChildItems(treeid);
						  	        var getListCCodeurl=basePath+"ea/oasafeKind/sajax_ea_getSafeKindsByPID.jspa?parentID="+treeid+"&date="+new Date().toLocaleString();
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
										           var safeKindList = member.safeKindList;
										          
										           if(null == safeKindList){
										              return;
										           }    
										            for(var i=0;i<safeKindList.length;i++){
										             tree.insertNewChild(treeid,safeKindList[i].id,safeKindList[i].name,0,0,0,0);
										           }
										            token = 0;
								            },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
						     
			         				$("#mainframe").attr("src",basePath+"ea/oasafeKind/ea_getSafeKinds.jspa?parentID="+ treeid+"&data="+new Date());
			         		 
				 				
			});
});