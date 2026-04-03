var topWin= (function (p,c){
		while(p!=c){
		    c = p;
		    p = p.parent;
		}
		return c;
	})(window.parent,window);
$(document).ready(function() {
	load();
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
});
function load(){
	if(!topWin.frames["rightFrame"]){
		var html = "<div id='accift' class='jqmWindow'" +
			"style='width: 900px; position: fixed; top:200px;left:200px;height: 420px; absolute; display: none; background: #eff; '>";
			html += "<div style='background: #efg; margin-right: 500px;'>";
			html += "<input style='display: none;' id='myform'/>";
			html += "<input style='display: none;' id='parm'/>";
			html += "</div>";
			html += "<div class='drag'><span id='dragsapn'></span>";
			html += "</div>";
			html += "<div style=\"width:49%;float:left;\"><span id=\"tabname\"></span>";
			html += "<input style=\"margin-left: 2px; margin-right: 10px;\" size=\"15\" id=\"svalue\"/>&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "<input type=\"button\" id=\"search\" value=\"查询\" style=\"width:70px;height:24px;font-size: 12px;\"/>&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "<input type=\"button\" id=\"btn\" value=\"确定\" style=\"width:70px;height:24px;font-size: 12px;\"/>&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "<input type=\"button\" id=\"close\" value=\"关闭\" style=\"width:70px;height:24px;font-size: 12px;\"/>&nbsp;&nbsp;&nbsp;&nbsp;</div>";
			html += "<div style=\"text-align:right;float:right;\"><a id=\"grsy\" title=\"0\">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "<a id=\"grxy\" title=\"0\">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "<a id=\"grzy\">共&nbsp;&nbsp; <span style=\"color: red\" id=\"grzycount\"></span>&nbsp;&nbsp; 页</a>&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "<a id=\"\">第&nbsp;&nbsp; <span style=\"color: red\" id=\"grzydqy\"></span>&nbsp;&nbsp; 页</a>";

			html += "</div>";
			html += "<iframe name='ifrm' id='ifrm' width='100%' height='350px' frameborder='0'></iframe>";
			html += "</div>";
			$(topWin.document).find("body").eq(0).find("#accift").remove();
			$(topWin.document).find("body").eq(0).last().append(html);
	}else{
		var html = "<div id='accift' class='jqmWindow'" +
			"style='width:100%; height: 90%; absolute; display: none; top: 0%; background: #eff; '>";
		html += "<div style='background: #efg; margin-right: 500px;'>";
		html += "<input style='display: none;' id='myform'/>";
		html += "<input style='display: none;' id='parm'/>";
		html += "</div>";
		html += "<div class='drag'><span id='dragsapn'></span>";
		html += "</div>";
		html += "<div style=\"width:49%;float:left;\"><span id=\"tabname\"></span>";
		html += "<input style=\"margin-left: 2px; margin-right: 10px;\" size=\"15\" id=\"svalue\"/>&nbsp;&nbsp;&nbsp;&nbsp;";
		html += "<input type=\"button\" id=\"search\" value=\"查询\" style=\"width:70px;height:24px;font-size: 12px;\"/>&nbsp;&nbsp;&nbsp;&nbsp;";
		html += "<input type=\"button\" id=\"btn\" value=\"确定\" style=\"width:70px;height:24px;font-size: 12px;\"/>&nbsp;&nbsp;&nbsp;&nbsp;";
		html += "<input type=\"button\" id=\"close\" value=\"关闭\" style=\"width:70px;height:24px;font-size: 12px;\"/>&nbsp;&nbsp;&nbsp;&nbsp;</div>";
		html += "<div style=\"text-align:right;float:right;\"><a id=\"grsy\" title=\"0\">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;";
		html += "<a id=\"grxy\" title=\"0\">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;";
		html += "<a id=\"grzy\">共&nbsp;&nbsp; <span style=\"color: red\" id=\"grzycount\"></span>&nbsp;&nbsp; 页</a>&nbsp;&nbsp;&nbsp;&nbsp;";
		html += "<a id=\"\">第&nbsp;&nbsp; <span style=\"color: red\" id=\"grzydqy\"></span>&nbsp;&nbsp; 页</a>";
		html += "</div>";
		html += "<iframe name='ifrm' id='ifrm' width='100%' height='90%' frameborder='0'></iframe>";
		html += "</div>";
		if($(topWin.frames["rightFrame"].document).find("div#accift","body").length > 0 ){
			$(topWin.frames["rightFrame"].document).find("div#accift","body").remove();
		}
		$(topWin.frames["rightFrame"].document).find("body").append(html);
	}
}
//e 类型     callback 回调函数callback  t 弹框类型 00：小框 、 01：大框  
function accift(e,callback,t){
	if(!topWin.frames["rightFrame"]){
		$doc=$(topWin.document).find("#accift");
		$("#ifrm",$doc).contents().empty();
		if(typeof callback == "function"){
			$("#btn",$doc).click(function(){
				var pareID = $doc.find("#ifrm").contents().find("#pareID").val();
				if(pareID == ""){
					alert("请选择!");
				}else{
					//获取全部
					//parent.paret("全部"+$("#"+pareID).find("#all").text());
					//********单个根据条件自己写*********
					if(e == "04"){
						var chaeckall = "";
						//分次显示全部信息
						if ($doc.find("#ifrm").contents().find("[name='check']").is(':checked')) {
							$doc.find("#ifrm").contents().find("input[name='check']").each(function() {
								if ($(this).is(':checked')) {
									chaeckall +=$("#ifrm",$doc).contents().find("tr#"+$(this).val()).find("#all").text() + ";";
								}
							});
						}
						callback(chaeckall);
					}else{
						callback($("#ifrm",$doc).contents().find("#"+pareID).find("#all").text());
					}
					$doc.find("#accift").hide();
				}
			});
		}
		
		$doc.show();
		if(e == "01"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=01&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("往来单位选择"); 
		}else if(e == "02"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=02&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("社会人力选择"); 
		}else if(e == "03"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=03&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("在职员工选择"); 
		}else if(e == "04"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=04&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("物品选择"); 
		}
	}else{
		$(topWin.frames["rightFrame"].document).find("#ifrm").contents().empty();
		//绑定事件
		if(typeof callback == "function"){
			$(topWin.frames["rightFrame"].document).find("#btn").click(function(){
				var pareID = $(topWin.frames["rightFrame"].document).find("#ifrm").contents().find("#pareID").val();
				if(pareID == ""){
					alert("请选择!");
				}else{
					//获取全部
					//parent.paret("全部"+$("#"+pareID).find("#all").text());
					//********单个根据条件自己写*********
					if(e == "04"){
						var chaeckall = "";
						//分次显示全部信息
						if ($("[name='check']").is(':checked')) {
							$("input[name='check']").each(function() {
								if ($(this).is(':checked')) {
									chaeckall +=$(topWin.frames["rightFrame"].document).find("#ifrm").contents().find("tr#"+$(this).val()).find("#all").text() + ";";
								}
							});
						}
						callback(chaeckall);
					}else{
						callback($(topWin.frames["rightFrame"].document).find("#ifrm").contents().find("#"+pareID).find("#all").text());
					}
					$(topWin.frames["rightFrame"].document).find("#accift").hide();
				}
			});
		}
		$(topWin.frames["rightFrame"].document).find("#accift").show();
		if(e == "01"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=01&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
			$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("往来单位选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("单位名称:"); 
		}else if(e == "02"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=02&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
		 	$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("社会人力选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("姓名:"); 
		}else if(e == "03"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=03&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
		 	$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("在职员工选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("姓名:"); 
		}else if(e == "04"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=04&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
		 	$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("物品选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("物品编码或名称:"); 
		}
	}
}
/**
 * @param e 类型  01往来单位选择 02 社会人力选择 03 在职员工选择 04 物品
 * @param callback 回调函数callback  
 * @param t 弹框类型 00：小框 、 01：大框   
 * @param areaId 返回后赋值区域Id(必须唯一)
 */
function areaDiv(e,callback,t,areaId){
	if(!topWin.frames["rightFrame"]){
		$doc=$(topWin.document).find("#accift");
		$("#ifrm",$doc).contents().empty();
		if(typeof callback == "function"){
			$("#btn",$doc).click(function(){
				var pareID = $doc.find("#ifrm").contents().find("#pareID").val();
				if(pareID == ""){
					alert("请选择!");
				}else{
					//获取全部
					//parent.paret("全部"+$("#"+pareID).find("#all").text());
					//********单个根据条件自己写*********
					if(e == "04"){
						var chaeckall = "";
						//分次显示全部信息
						if ($doc.find("#ifrm").contents().find("[name='check']").is(':checked')) {
							$doc.find("#ifrm").contents().find("input[name='check']").each(function() {
								if ($(this).is(':checked')) {
									chaeckall +=$("#ifrm",$doc).contents().find("tr#"+$(this).val()).find("#all").text() + ";";
								}
							});
						}
						callback(chaeckall,areaId);
					}else{
						callback($("#ifrm",$doc).contents().find("#"+pareID).find("#all").text(),areaId);
					}
					$doc.find("#accift").hide();
				}
			});
		}
		
		$doc.show();
		if(e == "01"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=01&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("往来单位选择"); 
		}else if(e == "02"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=02&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("社会人力选择"); 
		}else if(e == "03"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=03&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("在职员工选择"); 
		}else if(e == "04"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=04&jsptype="+t;
			$doc.find("#ifrm").attr("src",url);
			$doc.find("span#dragsapn").text("物品选择"); 
		}
	}else{
		$(topWin.frames["rightFrame"].document).find("#ifrm").contents().empty();
		//绑定事件
		if(typeof callback == "function"){
			$(topWin.frames["rightFrame"].document).find("#btn").click(function(){
				var pareID = $(topWin.frames["rightFrame"].document).find("#ifrm").contents().find("#pareID").val();
				if(pareID == ""){
					alert("请选择!");
				}else{
					//获取全部
					//parent.paret("全部"+$("#"+pareID).find("#all").text());
					//********单个根据条件自己写*********
					if(e == "04"){
						var chaeckall = "";
						//分次显示全部信息
						if ($("[name='check']").is(':checked')) {
							$("input[name='check']").each(function() {
								if ($(this).is(':checked')) {
									chaeckall +=$(topWin.frames["rightFrame"].document).find("#ifrm").contents().find("tr#"+$(this).val()).find("#all").text() + ";";
								}
							});
						}
						callback(chaeckall,areaId);
					}else{
						callback($(topWin.frames["rightFrame"].document).find("#ifrm").contents().find("#"+pareID).find("#all").text(),areaId);
					}
					$(topWin.frames["rightFrame"].document).find("#accift").hide();
				}
			});
		}
		$(topWin.frames["rightFrame"].document).find("#accift").show();
		if(e == "01"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=01&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
			$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("往来单位选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("单位名称:"); 
		}else if(e == "02"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=02&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
		 	$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("社会人力选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("姓名:"); 
		}else if(e == "03"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=03&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
		 	$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("在职员工选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("姓名:"); 
		}else if(e == "04"){
			var url = basePath + "ea/accessresource/ea_getTreeHead.jspa?stuts=04&jsptype="+t;
			$(topWin.frames["rightFrame"].document).find("#ifrm").attr("src",url);
		 	$(topWin.frames["rightFrame"].document).find("span#dragsapn").text("物品选择"); 
			$(topWin.frames["rightFrame"].document).find("span#tabname").text("物品编码或名称:"); 
		}
	}
}