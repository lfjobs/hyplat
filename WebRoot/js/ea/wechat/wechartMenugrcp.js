$(document).ready(function() {	
	
	

});
function addcp()
{
	
	$('#cpform').attr("action",basePath + "/ea/wfjcustomer/ea_addcpjsp.jspa");
	$('#cpform').submit(); 
};
function yanz()
{
	 var yan=0;
	  	$(".goods").each(function()
	  			{ 
	  			if($(this).find("div").find("div").find(".chebox").attr("checked"))
				  {	  		   	 
	  				yan=1;
				  }
			});
	  	if(yan==0)
	  		{
	  		    alert("请选择!");
	  		  return  false ;
	  		
	  		}
	  	else{
	  		
	  		return true;
	  	}	  
}
function del()
{  
		if(yanz()){
			ifxh();
	  	$("#ID").val(cpid);
	  	if (confirm("确定删除？")) {
	  		$('#cpform')
			.attr("target", "hidden").attr("action",basePath + "/ea/wfjcustomer/ea_delchenp.jspa?type=1");
			  	token=2;
			  
				$('#cpform').submit();
		}
		$("#ID").val("");	
		cpid="";
		}
};

function xiajia()
{	
	if(yanz()){
		ifxh();		
	  	$("#ID").val(cpid);
		if (confirm("确定下架？")) {
			$('#cpform')
			.attr("target", "hidden").attr("action",basePath + "/ea/wfjcustomer/ea_toxia.jspa?type=0");
		  	token=2;
			$('#cpform').submit();
		}
		$("#ID").val("");
		cpid="";
	}		
}
function ifxh()
{
	$(".goods").each(function()
  			{ 
  			if($(this).find(".chebox").attr("checked"))
			  {	  		   	 
  				  cpid+=$(this).attr("id");	  					
  				  	cpid+=",";					  
			  }	  			
		});
}
function huifu(){
	if(yanz()){	
		ifxh();
	  	$("#ID").val(cpid);
	  	if (confirm("确定出售？")) {
	  		$('#cpform')
					.attr("target", "hidden")
					.attr("action",basePath + "/ea/wfjcustomer/ea_toxia.jspa?type=1");
	  		$('#cpform').submit();
	  		token=2;
	  	}	
		$("#ID").val("");
		cpid="";
		/*Showbo.Msg.confirm("确定出售吗？", function(item){
			  	$('#cpform').attr("action",basePath + "/ea/wfjcustomer/ea_toxia.jspa?type=1");
			  	token=2;
				$('#cpform').submit();
		});*/
	}
};
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/wfjcustomer/ea_getlist.jspa";
}
function fanhui()
{
	window.history.go(-1);	
}
function dui(dd){
						if($(dd).find("div").find("#images").attr("src")==(basePath+"/images/WFJClient/choice_blank.png"))
			         {
							$(dd).find("div").find("#images").attr("src",basePath+"/images/WFJClient/choice_choice.png");
							$(dd).find("div").find("div").find("#cpid").attr("checked","checked");
			         }
			         else
			         {
			        	 $(dd).find("div").find("#images").attr("src",basePath+"/images/WFJClient/choice_blank.png");
			        	 $(dd).find("div").find("div").find("#cpid").attr("checked","");
			         }
}
function qx(){
	  	$("#chenID").val($("#ppID").val());
		if (confirm("取消代理？")) {
			$('#qxform')
			.attr("target", "hidden").attr("action",basePath + "/ea/wfjcustomer/ea_delDaiLi.jspa?type=2");
		  	token=6;
			$('#qxform').submit();
		}
		$("#chenpID").val("");
		cpid="";
}
function dl(){
		$("#chenpID").val($("#pID").val());	
		if (confirm("确定代理？")) {
			$('#dlform')
			.attr("target", "hidden").attr("action",basePath + "/ea/wfjcustomer/ea_sevedailicp.jspa");
		  	token=6;
			$('#dlform').submit();
		}
		$("#chenpID").val("");
		cpid="";
}
//成功后的正确转回本页面
function re_skip() {
	if (token)
		document.location.href = basePath
				+ "/ea/wfjcustomer/ea_getProductAgency.jspa";
}
$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	//关闭分享
	$(".JQueryreturns").click(function() {
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	
});
//弹出分享
/*$("#wfjshare").click(function(){*/
function fenxiang(a,b,c){
	/*a 自定义分享内容
	b 自定义分享url地址
	c 自定义分享图片*/
	/*$("#bdText").val("");
	$("#ckbody").html("");
	$("input#parmss").val("dc");
	$("#bdText").val(a);	
	$("#bdUrl").val(basePath + b); 	
	$("#bdPic").val(basePath + c);*/
	bdText = a;
	bdUrl = basePath+b;
	bdPic = basePath+c;
	/*$(".jqmWindow", $("#ckForms")).jqmShow();*/
	url = basePath + "/ea/wfjcustomer/ea_share.jspa?bdText="+ bdText +"&bdUrl="+bdUrl+"&bdPic="+bdPic;
	window.open(url, '','toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,depended=yes,width=250,height=50,top=100, left=55');
	/*alert($("#bdText").val());
	alert($("#bdUrl").val());
	alert($("#bdPic").val());*/
}