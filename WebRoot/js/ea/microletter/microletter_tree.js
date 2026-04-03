$(document).ready(function(){   
            tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree.insertNewChild("0","001","微信导航菜单",0,0,0,0);
			tree.insertNewChild("0","002","微信活动管理",0,0,0,0);
			tree.setOnClickHandler(function(){
			                        $(".input01").attr("value","");
                                    $("#desc").attr("html","");
                                    treeid= tree.getSelectedItemId();
			                        treename = tree.getItemText(treeid);
			                        $("#codeName").attr("value",treename);
							           tree.deleteChildItems(treeid);
						  	            var urlMicroMenu=basePath+"ea/microletter/sajax_ea_getAjaxListDtMicroletterMenuAll.jspa?dtMicroletterMenu.microlettermenupid="+treeid+"&date="+new Date().toLocaleString();
									     $.ajax({
						                        url: encodeURI(urlMicroMenu),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(data){
										           var member = eval("("+data+")");
										           var listMicroMenu = member.listMicroMenu;
										           if(null == listMicroMenu){
										              return;
										           }    
										            for(var i=0;i<listMicroMenu.length;i++)
												   {
										             tree.insertNewChild(treeid,listMicroMenu[i].microlettermenuid,listMicroMenu[i].microlettermenuname,0,0,0,0);
										           }
								               },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
									if(treeid=="002"){
										 $("#mainframe").attr(
													"src",basePath+"ea/activity/activityList.jspa");    
									}else{
										 $("#mainframe").attr(
													"src",basePath+"ea/microletter/ea_getListDtMicroletterMenuAll.jspa?dtMicroletterMenu.microlettermenupid="+ treeid + "&treename=" + encodeURI(treename));    
										
									}
						       
			});
			
			
		 	
});