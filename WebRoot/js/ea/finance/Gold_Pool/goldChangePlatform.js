$(function(){
	
	object.push("khd=",khd);
	object.push("&flag=",flag);		
	object.push("&identifying=",identifying);
	
	
	$(".plam_box").each(function(){
		//随机颜色
		var str=$(this).find("span").eq(0).text();
        var str2=(str.substr(0, 1));
        $(this).find("div").eq(0).html(str2);
        var string = "#FF501A,#68B83A,#78CF7F,#53BBCB,#ED837B,#C5E6F9,#1F75BB,#B41717";   //原始数据
        var array = string.split(",");    
        var string1 = "#F7C2B1,#B2E893,#C3EEC7,#C4EBF1,#F5C1BD,#EEF5F9,#AAD0F1,#E4A0A0";   //原始数据
        var array1 = string1.split(",");        //转化为数组
        var iii=Math.round(Math.random()*(array.length-1));
        var col = array[iii];  //随机抽取一个值
        var col1 = array1[iii]; 
        $(this).find(".m_list_L").attr("style","border:2px solid "+col+";color:"+col+";background:"+col1+";"); 
    });
	
	$(".plam_box").click(function(){
		var sccid=$(this).find("#sccid").val();
		if(type==1){
			var url = basePath +"/ea/jinbi/ea_gethyjifen.jspa?"+object.join("")+"&user="+user+"&sccid="+sccid+"&ccompanyId="+ccompanyId;
		}    		
		document.location.href = url;
	});
	
	$(".back").click(function(){
		if(type==1){
			var url = basePath +"/ea/jinbi/ea_gethyjifen.jspa?"+object.join("")+"&user="+user+"&sccid="+sccid+"&ccompanyId="+ccompanyId;
		}   		
		document.location.href = url;
	});
	
});






