$(document).ready(function(){
		//语音查询
		$("#wavshow").click(function(){
			parent.main.location = basePath+'ea/tel/tel_soundInRecords.jspa?type='+type;
		});
		//语音查询
		$("#wavshow1").click(function(){
			parent.main.location = basePath+'ea/tel/tel_soundOutRecords.jspa?type='+type;
		});
		
		//接待单位
		$("#Company").click(function(){
			parent.main.location= basePath+'ea/tel/tel_companyRecords.jspa?type='+type;
		});
		
		//接待个人
		$("#Personal").click(function(){
			parent.main.location= basePath+'ea/tel/tel_personRecords.jspa?type='+type;
		});
		
		//回访记录
		$("#returnVisited").click(function(){
			parent.main.location= basePath+"ea/tel/tel_returnsVisit.jspa?type="+type;
		});
		
	/*	//电话访问统计
		$("#tongji").click(function(){
			parent.main.location = basePath + "ea/tel/tel_tongji.jspa?type="+type;
		});*/
		
		//座机信息处理中心
		$("#infoDealz").click(function(){
			//setcookie("telcodetype","0");
			parent.main.location = basePath + "ea/tel/tel_infoDealCenter.jspa?type="+type+"&telcodetype=0";
		});
		//手机信息处理中心
		$("#infoDealp").click(function(){
			//setcookie("telcodetype","1");
			parent.main.location = basePath + "ea/tel/tel_infoDealCenter.jspa?type="+type+"&telcodetype=1";
		});
});
