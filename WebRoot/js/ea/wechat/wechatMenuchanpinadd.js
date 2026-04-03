$(document).ready(function() {

	 $("input[name]").each(function()
			 {
		    
		   
		 
			 });


});

function re_load(){
	if(token){
		document.location.href =basePath+"ea/wechatmenu/getMenuList.jspa?menuPid="+menuPid;
	}
};
function shangchuan(name)
{
	
	   if(name=="logo")
		   {
       var  a= 	$("#tuplogo");
       a.click();
		   }
	   if(name=="zhuti")
	   {
			   var  a= 	$("#tupphoto");
			   a.click();
	   }
}

function chuan(name) {

	if (name == 'logo') {
		var b1 = $("#tuplogo");
		b1.click();
	} else if (name == 'zhuti') {
		var b1 = $("#tupphoto");
		b1.click();
	}
}

function tijiao(){
	
	
	if($("#goodsname").val()==null&&$("#goodsname").val()=="")
	{
		alert("产品名称不能为空");
		return false;
		
	}

	$("#cstaffForm").attr("target", "hidden").attr(
			"action",
			basePath
			+  "ea/wfjcustomer/ea_add.jspa");
	document.cstaffForm.submit();	
}
function fanhui()
{
	window.history.go(-1);	
}