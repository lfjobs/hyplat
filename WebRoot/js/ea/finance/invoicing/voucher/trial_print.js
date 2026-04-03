
$(function(){
	//根据部门ID查询部门NAME
	$(".orgId").each(function(){
		var orgtext="";
		$.ajax({
			url:basePath +"ea/dycoturn/sajax_ea_ajaxorganization.jspa?comid="+$(this).attr("id")+"&orgid="+$(this).attr("name"),
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var cor=member.org;
				orgtext=cor.organizationName;
			},
			error:function(data){
				alert(">>>");
			}
		});
		$(this).parent().find("span").text(orgtext);
		$(this).val(orgtext);
	});
	//导出EXCEL表格
	$("#but").click(function(){			
			var obj=new Array();
		$(".dps").each(function(i,element){				
			obj[i]=new Array();
			for(var r=0;r<11;r++){
				if($(this).find("input").eq(r).val()!=""){
					obj[i][r]=$(this).find("input").eq(r).val();
				}else{
					obj[i][r]=0;
				}
			}
		}); 
		var json=arrayToJson(obj);
		window.location.href=basePath+"ea/voucher/ea_voucherExcel.jspa?json="+json;
	});
});


function arrayToJson(o) {     
    var r = [];     
    if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";     
    if (typeof o == "object") {     
    if (!o.sort) {     
    for (var i in o)     
    r.push(i + ":" + arrayToJson(o[i]));     
    if (!!document.all && !/^\n?function\s*toString\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {     
    r.push("toString:" + o.toString.toString());     
    }     
    r = "{" + r.join() + "}";     
    } else {     
    for (var i = 0; i < o.length; i++) {     
    r.push(arrayToJson(o[i]));     
    }     
    r = "[" + r.join() + "]";     
    }     
    return r;     
    }     
    return o.toString();     
}    