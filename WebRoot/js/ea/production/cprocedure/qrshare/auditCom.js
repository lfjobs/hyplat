$(function(){

	
  //公司招部门
  $(document).on('click','.c_box',function(){
	  
	var companyID =  $(this).attr("id");
	var companyName = $(this).find(".c_name").text();
	var $this = $(this);
	var $ul = $this.find("ul");
	if($ul.length>0){
		return;
	}
	
	 $.ajax({
     	url:basePath + "ea/workmessage/sajax_ea_ajaxFindOrgList.jspa",
     	type:"post",
     	async : false,
     	dataType : "json",
     	data:{
     		companyID:companyID
     	},
     	success:function(data){	
     	  var da  = eval("(" + data + ")")
     	  var orglist=da.orglist;
     	  var array = new Array();
     	  array.push("<ul class='c_section_wrap'>");
     	  for (var i = 0; i < orglist.length; i++) {
     		 array.push("<li><a href='javascript:findStaff('"+companyName+"','"+orglist[i].organizationName+"','"+companyID+"','"+orglist[i].organizationID+"');' class='c_section'>"+orglist[i].organizationName+"</a></li>");
		  }
     	  
     	  array.push("</ul>");
     	  
          $this.append(array.join(""));
     	
     		
     	},error:function(data){
     		alert("查询失败！");
     	}
    });
  });
});


function findStaff(companyName,orgName,companyID,orgID){
	
	document.location.href  = basePath+"ea/workmessage/ea_findStaffList.jspa?companyID="+companyID+"&orgID="+orgID+"&companyName="+companyName+"&orgName="+orgName+"&auid="+auid+"&comment="+comment;
	
}