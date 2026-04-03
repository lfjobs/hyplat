var c_topWin= (function (p,c){
		while(p!=c){
		    c = p;
		    p = p.parent;
		}
		return c;
	})(window.parent,window);
$(document).ready(function() {
	load();
	$(".jqmWindowPrd").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	
	
	
});
function load(){
	var html = "<div id='category'  style='position:absolute;left:250px;top:120px;z-index:5000;background-color:#ccc;display:none;' class='jqmWindowPrd' >";
	    html +="<div class='' id='' style='width:700px; height:400px; display:block;'>";
	    html +="<table style='background-color:#e4f1fa;'>	<tr> <td width='298px;'>";
	    html +="<div style=\" float:left; height:400px;\" id=''><iframe name='cfrm' id='cfrm'  style='width:295px;height:360px;' frameborder='0'></iframe> </div>";
	    html +="</td> <td style='width:100px; vertical-align:middle; text-align:center;'>";
	    html +="<div style='height:110px;' id=''>";
	    html +="<p><input type='button' name='addItem' id='addItem' value='增加'/> </p>";
	    html +="<p><input type='button' name='delItem' id='delItem' value='删除'/> </p>";
	    html +="<p><input type='button' name='confirmBtn' id='confirmBtn' value='确定'/> </p>";
	    html +="</div> </td> <td width='298px;'>";
	    html +="<div id=''><select id='selRight' size='23' style='width:298px'></select></div>";
	    html +="</td> </tr>	</table>";
	    html +="</div>";
	    $("body").last().append(html);
}

function category(c,callback){	
	var url ="";
	if (c=="pro")
		url = basePath + "ea/accessresource/cea_getTreeHead.jspa?stuts=01&jsptype=00";
	else if(c=="wfj")
		url = "";//	
	$("#cfrm").attr("src",url);	
	$(".jqmWindowPrd").show();
	
	if (typeof callback =="function"){ 
		// 确定按钮事件
		/*$("#confirmBtn",window.parent.document).click(function(){
			var res = "";
			$("#selRight option").each(function(){
				res += $(this).val() + "," + $(this).text() +";";				
			});
			callback(res);			
		});*/
	}	
}