$(document).ready(function(){   
            tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false);
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree.insertNewChild("0","002","个人邮箱",0,0,0,0);
			tree.insertNewChild("002","1","收件箱",0,0,0,0);
			
			
			tree.insertNewChild("1","12","已读",0,0,0,0);
			tree.insertNewChild("1","14","未读",0,0,0,0);
			
			
			tree.insertNewChild("002","02","草稿箱",0,0,0,0);
			tree.setUserData("02","Status","addresserStatus");
			tree.insertNewChild("002","01","已发送",0,0,0,0);
			tree.setUserData("01","Status","addresserStatus");
			tree.insertNewChild("002","03","已删除",0,0,0,0);
			tree.setUserData("03","Status","shanchu");
			tree.insertNewChild("002","04","写信",0,0,0,0);
			tree.insertNewChild("002","9","个人邮箱统计",0,0,0,0);
	        tree.setOnClickHandler(function(){
	            treeid= tree.getSelectedItemId();
			    treename = tree.getItemText(treeid);
			   if(treeid=="04")
			   {
			     $("#mainframe").attr("src",basePath+"page/ea/main/office_ea/email/email_add.jsp");    
			   }
			   else if(treeid=="02")
			   {
			     $("#mainframe").attr("src",basePath+"ea/email/ea_saveEmailDraft.jspa?");    
			   }
			    else if(treeid=="01")
			   {
			     $("#mainframe").attr("src",basePath+"ea/email/ea_getSendList.jspa?");     
			   }
			    else if(treeid=="03")
			   {
			     $("#mainframe").attr("src",basePath+"ea/email/ea_getEmailIndex.jspa?");      
			   }
			   else if(treeid=="1")
			   {
			     $("#mainframe").attr("src",basePath+"ea/email/ea_toSearch.jspa?");    
			   }
			   else{
			     $("#mainframe").attr("src",basePath+"page/ea/main/office_ea/email/jfreeCharPh.jsp");    
			   }
			
			});
			
		 	
});