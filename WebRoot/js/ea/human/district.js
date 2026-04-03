 $(document).ready(function(){   
      //地域树
	       tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
		    treeid = "0";
			                        var url=basePath+"district/sajax_ea_getByDistrictParentID.jspa?parameter="+treeid+"&date="+new Date().toLocaleString();
								    $.ajax({
											 url: encodeURI(url),
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
			tree.setOnClickHandler(function(){
			      treeid= tree.getSelectedItemId();
			      parentid = tree.getParentId(treeid)  ;
			      parentname = tree.getItemText(parentid);
			      treename = tree.getItemText(treeid);
			       $("#userID").attr("value",parentid);
			       $("#userID1").attr("value",treeid);
			      $("#username").attr("value",parentname);
			      $("#usename").attr("value",treename);
			      $("#username1").attr("value",treename);
			      	  var state= tree.getOpenState(treeid) ;
			       if("1" == state){
								  return;
								  }
			       tree.deleteChildItems(treeid);
					  	            var url1=basePath+"district/sajax_ea_getByDistrictParentID.jspa?parameter="+treeid+"&date="+new Date().toLocaleString();
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
			});
 
 });
 function toedit(a){
                $(".input01").attr("value","");
                 treeid= tree.getSelectedItemId();
			     treename = tree.getItemText(treeid);
			     $(".input01").attr("value","");
			      $("#usename").attr("value",treename);
		          parentid = tree.getParentId(treeid)  ;
		          parentname = tree.getItemText(parentid);
		          
		          if('1' == a){
			      $("#userID").attr("value",treeid);
			      $("#username").attr("value",treename);
			      }
			       else{
			       $("#userID").attr("value",parentid);
			       $("#username").attr("value",parentname);
			       }
		 }
   
	  
	  