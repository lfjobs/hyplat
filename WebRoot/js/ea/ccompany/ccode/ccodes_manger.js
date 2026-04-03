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
			                       treeid= tree.getSelectedItemId();
			                       treename = tree.getItemText(treeid);
			                        $("#codeName").attr("value",treename);
							           tree.deleteChildItems(treeid);
						  	            var getListCCodeurl=basePath+"ea/cccode/sajax_ea_getListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString();
						  	            
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
										           var codeList = member.codeList;
										           if(null == codeList){
										              return;
										           }    
										            for(var i=0;i<codeList.length;i++)
												   {
										             tree.insertNewChild(treeid,codeList[i].codeID,codeList[i].codeValue,0,0,0,0);
										             tree.setUserData(codeList[i].codeID,"Status",codeList[i].codeStatus);
										           }
						                        },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
						        $("#mainframe").attr(
									"src","ea/cccode/ea_getCodeListAll.jspa?codeID="+ treeid + "&treename=" + treename);  
						        
			});
});
	 	 