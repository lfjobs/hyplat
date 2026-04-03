/*function change(cla){
	$("#"+cla).attr("class","colorf00"); 
	$('a[name="col"]').each(
            function() {
            	if($(this).attr("id")!=cla){
            		$(this).attr("class","color0091f1");
            	}
            }
        );
}*/
function chan(cla){
	$("#"+cla).attr("style","color:#f00;"); 
	$('a[name="chan"]').each(
            function() {
            	if($(this).attr("id")!=cla){
            		$(this).attr("style","color:#666;");
            	}
            }
        );
	if(cla='huiyuan'){
		$("#jiaxiao").attr("style","display:none;");
		$("#jiaru").attr("style","display:block;");
	}
	
}
function colo(cla){
	$("#jiaxiao").attr("style","display:block;");
	$("#jiaru").attr("style","display:none;");
	$("#"+cla).attr("style","color:#f00;"); 
	$('a[name="jia"]').each(
        function() {
        	if($(this).attr("id")!=cla){
        		$(this).attr("style","");
        	}
        }
    );
	refre(cla);
}
function sele(){
	$("#jiaxiao").attr("style","display:block;");
	$("#jiaru").attr("style","display:none;");
	refre("zong");
}

/**ajax**/
function refre(ty){
	var url;
	if(ty=="mianyang"){
		url = basePath + "ea/distribution/ajax_ea_refre.jspa?result=绵阳&date01=" + new Date();
	}else if(ty=="ziyang"){
		url = basePath + "ea/distribution/ajax_ea_refre.jspa?result=资阳&date01=" + new Date();
	}else if(ty=="shengwei"){
		url = basePath + "ea/distribution/ajax_ea_refre.jspa?result=盛威&date01=" + new Date();
	}else if(ty=="chengdu"){
		url = basePath + "ea/distribution/ajax_ea_refre.jspa?result=成都&date01=" + new Date();
	}else{
		url = basePath + "ea/distribution/ajax_ea_refre.jspa?date01=" + new Date();
	}
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var ht='';
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					var data1 = new Array();
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath + "/page/WFJClient/Login.jsp?loginPage=login";
					}
					if(data!="{}"){
						var comID=member.companyID.split(",");
						var comName=member.companyName.split(",");
						var star = '<div class="wfj07_005"> <div class="wfj07_005_jiaxiao"> <div class="wfj07_width"> <ul>';
		                var end = '</ul> </div> </div> </div>';
						for (var i = 0; i < comID.length; i++) {        
							ht += (star + 
								 '<li><img src="<%=basePath %>/images/home/ttstlogo.png" /></li>'+
				                 '<li>'+ comName[i] +'</li>'+
				                 '<li style="float:right;"><a href="javascript:void(0);">加盟企业</a></li>'+
				                 end);
						}
					}
						$("#jiaxiao").html(ht);
					
				},
				error : function cbf(data) {
					alert("机构数据获取失败！");
				}
    });
}
