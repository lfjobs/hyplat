 

$(document).ready(function(){   
       tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			   treeid = "0";
                        var url3=basePath+"district/sajax_ea_getByDistrictParentID.jspa?parameter="+treeid+"&date="+new Date().toLocaleString();
					    $.ajax({
								 url: encodeURI(url3),
									type: "get",
									async: true,
									dataType: "json",
									success: function cbf(data){
												   var member = eval("("+data+")");
												   var nologin = member.nologin;
								                  		if(nologin){
								                  		document.location.href =basePath+"page/ea/not_login.jsp";
								                     }
										           var districtList = member.districtList;
										           if(null == districtList){
										              return;
										           }    
										            for(var i=0;i<districtList.length;i++)
												   {
										             tree.insertNewChild(treeid,districtList[i].districtID,districtList[i].districtName,0,0,0,0);
										           }
									},
								error: function cbf(data){
								alert("数据获取失败！");
								}
							});   
			
			optiontree=new dhtmlXTreeObject("option11","100%","100%",4);
		     optiontree.enableDragAndDrop(false);
		    optiontree.enableHighlighting(1);
	        optiontree.enableCheckBoxes(0);
			optiontree.enableThreeStateCheckboxes(false);
			optiontree.setSkin(basePath+'js/tree/dhx_skyblue');
			optiontree.setImagePath(basePath+"js/tree/codebase/imgs/");
			optiontree.loadXML(basePath+"js/tree/common/tree_d.xml");
			
			
			parenttree=new dhtmlXTreeObject("parentaadTree","100%","100%",3);
		    parenttree.enableDragAndDrop(false);
		    parenttree.enableHighlighting(1);
	        parenttree.enableCheckBoxes(0);
			parenttree.enableThreeStateCheckboxes(false);
			parenttree.setSkin(basePath+'js/tree/dhx_skyblue');
			parenttree.setImagePath(basePath+"js/tree/codebase/imgs/");
			parenttree.loadXML(basePath+"js/tree/common/tree_a.xml");
			
			selfaadTreetree=new dhtmlXTreeObject("selfaadTree","100%","100%",2);
		    selfaadTreetree.enableDragAndDrop(false);
		    selfaadTreetree.enableHighlighting(1);
	        selfaadTreetree.enableCheckBoxes(0);
			selfaadTreetree.enableThreeStateCheckboxes(false);
			selfaadTreetree.setSkin(basePath+'js/tree/dhx_skyblue');
			selfaadTreetree.setImagePath(basePath+"js/tree/codebase/imgs/");
			selfaadTreetree.loadXML(basePath+"js/tree/common/tree_c.xml");
	
	        parenttree.setOnOpenEndHandler(function(){
	             parenttreeid= parenttree.getSelectedItemId();
			     parenttreename = parenttree.getItemText(parenttreeid);
			     $("#user_name").attr("value",parenttreename);
			     $("#user_id").attr("value",parenttreeid);
	        });
	
	
	            selfaadTreetree.setOnOpenEndHandler(function(){
	             selfaadTreetreeid= selfaadTreetree.getSelectedItemId();
			     selfaadTreetreename = selfaadTreetree.getItemText(selfaadTreetreeid);
			     optiontree.insertNewChild("4",selfaadTreetreeid,selfaadTreetreename,0,0,0,0);
			     optiontree.setOnOpenEndHandler(function(){
			     optiontreeid = optiontree.getSelectedItemId();
			     optiontree.deleteItem(optiontreeid);
			     });
			     
	        });
	
	
			tree.setOnClickHandler(function(){
			   var treeid= tree.getSelectedItemId();
			  var state= tree.getOpenState(treeid) ;
			  
			  if("0" == state){
			           tree.deleteChildItems(treeid);
		  	            var url2=basePath+"district/sajax_ea_getByDistrictParentID.jspa?parameter="+treeid+"&date="+new Date().toLocaleString();
					    $.ajax({
							 url: encodeURI(url2),
								type: "get",
								async: true,
								dataType: "json",
								success: function cbf(data){
												   var member = eval("("+data+")");
												   var nologin = member.nologin;
								                  		if(nologin){
								                  		document.location.href =basePath+"page/ea/not_login.jsp";
								                     }
										           var districtList = member.districtList;
										           if(null == districtList){
										              return;
										           }    
										            for(var i=0;i<districtList.length;i++)
												   {
										               tree.insertNewChild(treeid,districtList[i].districtID,districtList[i].districtName,0,0,0,0);
										           }
								},
							error: function cbf(data){
							alert("数据获取失败！");
							}
						});															
					         
		  	     }
			});
 
   //单位绑定
    $("#getparentid").click(function(){
              parenttree.deleteChildItems("3");
              var treeid= tree.getSelectedItemId();//地域树结点ID,用于后台查找此ID下的单位
		      treename = tree.getItemText(treeid);
		      $("#regionID").attr("value",treeid);//地域id
		      $("#regionName").attr("value",treename);//地域名称
		    var url= basePath+"ea/units/sajax_ea_getListUnitsbyID.jspa?districtID="+treeid+"&date="+new Date().toLocaleString();
		  	$.ajax({
						 url: encodeURI(url),
							type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
											parentid = tree.getParentId(treeid)  ;
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
								            	 parenttree.insertNewChild("3",unitsList[i].companyID,unitsList[i].companyName,0,0,0,0);
								           }
							},
						error: function cbf(data){
						alert("数据获取失败！");
						}
					});
	  
					 
					
		 	
   });

 $("#getid").click(function(){
              selfaadTreetree.deleteChildItems("2");
              var treeid= tree.getSelectedItemId();//地域树结点ID,用于后台查找此ID下的单位
		      treename = tree.getItemText(treeid);
		      $("#regionID").attr("value",treeid);//地域id
		      $("#regionName").attr("value",treename);//地域名称
		   
		      
		   // var url= basePath+"dtunits/sajax_getListUnits.jspa";
	       // var params = {regionID:treeid};
		  
	     var url= basePath+"ea/units/sajax_ea_getListUnitsbyID.jspa?districtID="+treeid+"&date="+new Date().toLocaleString();
				  	$.ajax({
						 url: encodeURI(url),
							type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
									parentid = tree.getParentId(treeid)  ;
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
								            	 selfaadTreetree.insertNewChild("2",unitsList[i].companyID,unitsList[i].companyName,0,0,0,0);
								           }
							},
						error: function cbf(data){
						alert("数据获取失败！");
						}
					});
	    
   });
   
});
	 	 