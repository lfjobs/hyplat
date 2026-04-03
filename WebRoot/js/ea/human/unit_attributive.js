$(document).ready(function(){   
         tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			 var url=basePath+"/ea/units/sajax_ea_getunitmessage.jspa?organizationID=1";
		     $.ajax({
						 url: url,
							type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
									   var member = eval("("+data+")");
									   var nologin = member.nologin;
								                  		if(nologin){
								                  		document.location.href =basePath+"page/ea/not_login.jsp";
								                     }
							           var units = member.units;
							           if(null == units){
							              return;
							           }    
							          tree.insertNewChild("0",units.companyID,units.companyName,0,0,0,0);
							},
						error: function cbf(data){
						alert("数据获取失败！");
						}
					});  
			tree.setOnOpenEndHandler(function(){
			  var treeid= tree.getSelectedItemId();
			    var state= tree.getOpenState(treeid) ;
			  if("0" == state){
						           tree.deleteChildItems(treeid);
					  	            var url1=basePath+"ea/units/sajax_ea_getListUnitsbyID.jspa?companyID="+treeid+"&date="+new Date().toLocaleString();
								    $.ajax({
											 url: encodeURI(url1),
												type: "get",
												async: true,
												dataType: "json",
												success: function cbf(data){
														  var member = eval("("+data+")");
														  var nologin = member.nologin;
										                  		if(nologin){
										                  		document.location.href =basePath+"page/ea/not_login.jsp";
										                     }
												           var unitsList = member.unitsList;
												           if(null == unitsList){
												              return;
												           }    
												            for(var i=0;i<unitsList.length;i++)
														   {
												               tree.insertNewChild(treeid,unitsList[i].companyID,unitsList[i].companyName,0,0,0,0);
												           }
												},
											error: function cbf(data){
											alert("数据获取失败！");
											}
										});     
		  	     }
		 	});       
});
	 	 