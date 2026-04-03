	$(document).ready(function(){   
      tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			/*if(basic=='scode20141029rnzhjs7ap60000000004'){
				//tree.insertNewChild("0","001","物品管理",0,0,0,0);
				tree.insertNewChild("0","002","库存编码树",0,0,0,0);
			}else */
			if(basic=='scode20150501kze3xkwxgv0000000006'){
				tree.insertNewChild("0","scode20150501kze3xkwxgv0000000006","代码元素管理",0,0,0,0);
				tree.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000005","人事代码元素",0,0,0,0);
				tree.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000006","办公室代码元素",0,0,0,0);
				tree.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000026","财务代码元素",0,0,0,0);
				tree.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000027","生产代码元素",0,0,0,0);
				tree.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000028","营销代码元素",0,0,0,0);
			}else if(basic=='004'){
				/*tree.insertNewChild("0",basic,"行业类别",0,0,0,0);*/
				tree.insertNewChild("0",basic,"物品元素管理",0,0,0,0);
				console.log(basic);
				
			}else{
				tree.insertNewChild("0",basic,basicMap[basic],0,0,0,0);
			}
			

			tree.setOnClickHandler(function(){
				$("#mainframe").contents().empty();
				treeid= tree.getSelectedItemId();
				var iid="";
				 if(treeid.length>33){
		        	  iid=treeid.substring("33",treeid.length);
		        	  treeid=treeid.substring("0","33");
		          }
				if(treeid=="scode20150501kze3xkwxgv0000000006"){
					return;
				}
				if(treeid=='scode20141029WKC79JEKJT0000000507'){//凭证管理》》科目
					$("#mainframe").css({"height":"auto"}).attr(
							"src",basePath+"/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp");    
				$(window).resize();
				}else if(tree.getUserData(treeid+iid, "groupSn")=="wplb"&&treeid.length>33){//判断行业类型的。查询物品
					$("#mainframe").css({"height":"auto"}).attr("src",basePath+"ea/goodsmanage/ea_getListGoodsManage.jspa?treeid="+treename +"&iid="+treeid+iid+"&basic=004" );  
					$(window).resize();
				}else{
					$(".input01").attr("value","");
                    $("#desc").attr("html","");
		        $("#mainframe").css({"height":"auto"}).attr(
					"src","ea/ccode/ea_getCodeListAll.jspa?codeID="+ treeid + "&treename=" + treename);    
		        $(window).resize();
				}
				treename = tree.getItemText(treeid+iid);
                //Submit_onclick();//让左侧导航栏隐藏
                 $("#codeName").attr("value",treename);
                // tree.deleteChildItems(treeid+iid);
                 var li = tree.getAllSubItems(tree.getParentId(treeid+iid));
                 var ll=li.split(",");
                 for(var z=0;z<ll.length;z++){
                	 tree.deleteChildItems(ll[z]);
                 }
			         
		  	     var getListCCodeurl=basePath+"ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString();
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
						            for(var i=0;i<codeList.length;i++){
						             tree.insertNewChild(treeid+iid,codeList[i].codeID+iid, codeList[i].codeSn+codeList[i].codeValue,0,0,0,0);
						             tree.setUserData(codeList[i].codeID+iid,"groupSn",codeList[i].groupSn);
						           }
						            if(treeid=="scode20141029rnzhjs7ap60000000006"){//判断办公室基础数据时
										tree.insertNewChild("scode20141029rnzhjs7ap60000000006","002","库存编码树",0,0,0,0);
									}
						            if(treeid=='scode20150817mqy3awyt3t0000000013' || treeid=='scode20150817mqy3awyt3t0000000012' || treeid=='scode20150817mqy3awyt3t0000000011' || 
						               treeid=='scode20150817mqy3awyt3t0000000010' || treeid=='scode20150817mqy3awyt3t0000000009'){
						            	tree.deleteChildItems("scode20150817mqy3awyt3t0000000012");
						            	tree.deleteChildItems("scode20150817mqy3awyt3t0000000013");
						            	tree.deleteChildItems("scode20150817mqy3awyt3t0000000011");
						            	tree.deleteChildItems("scode20150817mqy3awyt3t0000000010");
						            	tree.deleteChildItems("scode20150817mqy3awyt3t0000000009");
						            	tree.insertNewChild(treeid,"scode20150815wygb79q82p0000000005"+treeid,"行业类别",0,0,0,0);
						            }
						            if(tree.getUserData(treeid+iid, "groupSn")==codeHylb && codeList.length==0){
						            	tree.insertNewChild(treeid+iid,"scode20101014v5zed7cukk0000000002"+treeid+iid,"物品类别",0,0,0,0);
						            }
						           /* var list=["scode20150817mqy3awyt3t0000000012","scode20150817mqy3awyt3t0000000013","scode20150817mqy3awyt3t0000000011","scode20150817mqy3awyt3t0000000010","scode20150817mqy3awyt3t0000000009"];
						            if(list.indexOf(treeid) > 0){
							            for(var j=0;j<list.length;j++){
						            		if(treeid == list[j]){
						            			tree.insertNewChild(treeid,"scode20150815wygb79q82p0000000005","行业类别",0,0,0,0);
						            		}else{
						            			tree.deleteChildItems(list[j]);
						            		}
						            	}
						            }*/
		                        },
		                        error: function cbf(data){
		                           alert("数据获取失败！");
		                        }
		                    });
			});		 	
});
	 	 