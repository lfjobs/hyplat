$(document).ready(function(){
	
      tree=new dhtmlXTreeObject("addTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree.insertNewChild("0","001","报表类别",0,0,0,0);
			tree.insertNewChild("001","A","资产负债表",0,0,0,0);
			tree.insertNewChild("001","B","损益表",0,0,0,0);
			
			
			var url = basePath+"ea/debtasset/sajax_ea_initiAssetsDebtTree.jspa?dates="+new Date();
			
			   $.ajax({
                   url: url,
                   type: "get",
                   async: true,
                   dataType: "json",
                   data:{
                	   "invCCbsgl.tabPSymbol":"00"
                   },
                   success: function cbf(data){
                      var member = eval("("+data+")");
                      var nologin = member.nologin;
	                  if(nologin){
	                     document.location.href =basePath+"page/ea/not_login.jsp";
	                   }
			           var bsgllist = member.bsgllist;
			          
			           if(null == bsgllist){
			                return;
			           }    
			            for(var i=0;i<bsgllist.length;i++)
					   {
			            if(bsgllist[i].tabType=="A"){
			                 tree.insertNewChild("A",bsgllist[i].tabSymbol,bsgllist[i].tabSCaption,0,0,0,0);
			            }else{
			            	 tree.insertNewChild("B",bsgllist[i].tabSymbol,bsgllist[i].tabSCaption,0,0,0,0);
			            }
			           }
                   },
                   error: function cbf(data){
                      alert("数据获取失败！");
                   }
               });
			
			
			
			tree.setOnClickHandler(function(){
				var treeid= tree.getSelectedItemId();
                  treename = tree.getItemText(treeid);
                  if(treeid=="001"){
                	  return;
                  }
    
                  if(tree.hasChildren(treeid)<1){
                	  getItem(treeid);
   				
                  }		
                  $("#mainframe").attr(
      					"src",basePath+"ea/debtasset/ea_getTotalAssetsDebt.jspa?tabPSymbol="+ treeid);
						        
			});
});

//当新增或删除时更新树
function updateTree(treeid){

   tree.deleteChildItems(treeid);
    getItem(treeid);
    $("#mainframe").attr(
			"src",basePath+"ea/debtasset/ea_getTotalAssetsDebt.jspa?tabPSymbol="+ treeid);      
}


//根据父节点获取子节点
function getItem(treeid){
	  var url = basePath+"ea/debtasset/sajax_ea_getAssetsDebtTree.jspa?dates="+new Date();
		
		   $.ajax({
             url: url,
             type: "get",
             async: true,
             dataType: "json",
             data:{
          	   "invCCbsgl.tabPSymbol":treeid
             },
             success: function cbf(data){
                var member = eval("("+data+")");
                var nologin = member.nologin;
                if(nologin){
                   document.location.href =basePath+"page/ea/not_login.jsp";
                 }
		           var bsgllist = member.bsgllist;
		          
		          
		           if(null == bsgllist){
		                return;
		           }    
		            for(var i=0;i<bsgllist.length;i++)
				   {
		            
		                 tree.insertNewChild(treeid,bsgllist[i].tabSymbol,bsgllist[i].tabSCaption,0,0,0,0);
		            
		           }
             },
             error: function cbf(data){
                alert("数据获取失败！");
             }
         });
		  
}
	 	 