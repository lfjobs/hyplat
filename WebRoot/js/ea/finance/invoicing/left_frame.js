

$(document).ready(function() {


$("#tree").find("span").click(function(){
	var xmtypename = $.trim($(this).text().substring($(this).text().lastIndexOf(")")+1));
	var xmtype = $.trim($(this).text().substring($(this).text().indexOf("(")+1,$(this).text().lastIndexOf(")")));
	
	var jumptype="fxlb";
	if(xmtypename=="未审核项目"){
		jumptype="zbqsh";
	}
	if(xmtypename=="已审核项目"){
		jumptype="zbqysh";
	}
	
	if(xmtypename=="未招标比价项目"){
		jumptype="bjlb";
	}
	
	if(xmtypename=="已招标比价物品"){
		jumptype="ybjlb";
	}
	if(xmtypename=="项目拟定"||xmtypename=="项目审核"||xmtypename=="招标比价"){
		return;
	}

	getList(jumptype,xmtype,xmtypename);
	
	$("#tree").find("span").each(function(){
		
		 $(this).css("color","");
		
		 
		 
	 });
	 $(this).css("color","red");
	
	
	
});

});


//获取右边列表
//jumptype:00：项目录入阶段列表
function getList(jumptype,xmtype,xmtypename){
	
     

	     //项目录入
		if(jumptype == "fxlb"||jumptype=="zbqsh"||jumptype=="zbqysh"){
		
			$("#mainframe11").attr(
				"src",basePath+
				"ea/costsheet/ea_getCostSheetList.jspa?jumptype="+jumptype+"&xmtype="+xmtype+"&xmtypename="+encodeURI(xmtypename));
			
		
		}//审核项目
		else if(jumptype == "01"){
			$("#mainframe11").attr(
				"src",basePath+
				"ea/costsheet/ea_getCostSheetList.jspa?type="+type+"&treeType="+treeid+"&jumptype="+jumptype+"&xmtype="+xmtype);
			
		}//未招标比价项目
		 else if(jumptype == "bjlb"){

			$("#mainframe11").attr(
				"src",basePath+
				"ea/costsheet/ea_getCostSheetList.jspa?jumptype="+jumptype);
		}//已招标比较物品03
		 else{
           
			$("#mainframe11").attr(
					"src",basePath+
					"ea/product/ea_getZbPriceedList.jspa");
		}
		
		

		

}





