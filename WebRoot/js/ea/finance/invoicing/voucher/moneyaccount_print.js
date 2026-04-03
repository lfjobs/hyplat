
$(function(){
	$("#but").click(function(){
		    var obj1=new Array();
		    var obj2=new Array();
			
		$(".together").each(function(i,element){				
			obj1[i]=new Array();
			obj1[i][0]=$(this).find("td").find("span").eq(0).text();
			obj1[i][1]=$(this).find("td").find("span").eq(1).text();
			obj1[i][2]=$(this).find("td").find("span").eq(2).text();
			obj1[i][3]=$(this).find("td").find("span").eq(3).text();
			obj1[i][4]=$(this).find("td").find("span").eq(4).text();
			obj1[i][5]=$(this).find("td").find("span").eq(5).text();
			obj1[i][6]=$(this).find("td").find("span").eq(6).text();
			obj1[i][7]=$(this).find("td").find("span").eq(7).text();
			if(zz=="01"){
			   obj1[i][8]=$(this).find("td").find("span").eq(8).text();
			   obj1[i][9]=$(this).find("td").find("span").eq(9).text();
			   obj1[i][10]=$(this).find("td").find("span").eq(10).text();
			}
			
		});
		$(".detail").each(function(i,element){				
			obj2[i]=new Array();
			obj2[i][0]=$(this).find("td").find("span").eq(0).text();
			obj2[i][1]=$(this).find("td").find("span").eq(1).text();
			obj2[i][2]=$(this).find("td").find("span").eq(2).text();
			obj2[i][3]=$(this).find("td").find("span").eq(3).text();
			obj2[i][4]=$(this).find("td").find("span").eq(4).text();
			obj2[i][5]=$(this).find("td").find("span").eq(5).text();
			obj2[i][6]=$(this).find("td").find("span").eq(6).text();
			obj2[i][7]=$(this).find("td").find("span").eq(7).text();
			obj2[i][8]=$(this).find("td").find("span").eq(8).text();
			obj2[i][9]=$(this).find("td").find("span").eq(9).text();
			if(zz=="01"){
			obj1[i][10]=$(this).find("td").find("span").eq(10).text();
			obj1[i][11]=$(this).find("td").find("span").eq(11).text();
			obj1[i][12]=$(this).find("td").find("span").eq(12).text();
			}
		});
		
		
		
		var json1=arrayToJson(obj1);
		var json2=arrayToJson(obj2);
		window.location.href=basePath+"ea/vaccount/ea_AccountExcel.jspa?json1="+json1+"&json2="+json2+"&type="+zz;
	});
});


function arrayToJson(o) {     
    var r = [];     
    if (typeof o == "string") 
    	return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";     
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