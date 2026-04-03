$(function(){
	ajaxGetProductMix("productPackaging.ppID="+goodsBillsID);
	$(".disImg").live("click",function(){
		cls="disImg";
		if($(this).parent().parent().attr("name")=="block"){
			$(this).parent().parent().attr("name","none");
			none($(this).parent().parent().attr("id"));
			$(this).attr("src",basePath+"images/mobile/add.png");
		}else{
			$(this).parent().parent().attr("name","block");
			block($(this).parent().parent().attr("id"));
			$(this).attr("src",basePath+"images/mobile/close.png");
		}
	});
	//上移
	var $up = $(".up");
    $up.click(function() {
        var $trs = $(this).parents("tr").nextAll();
        var $trss = $(this).parents("tr").prevAll();
        var ind=$(this).parents("tr").attr("hierarchical");
    	var $tr=new Array();
    	var $td;
    	$tr[0]=$(this).parents("tr");
    	var ir=1;
    	$trs.each(function(index){
    		if($(this).attr("hierarchical")==ind){
    			return false;
    		}else if($(this).attr("hierarchical")==ind-1){
    			return false;
    		}else{
    			$tr[ir]=$(this);
    			ir++;
    		}
    	});
    	if($(this).parents("tr").prev().attr("hierarchical")!=(ind-1)){
			$trss.each(function(index){
        		if($(this).attr("hierarchical")==ind){
    				var $tt=$(this);
    				for(var i=0;i<$tr.length;i++){
        				$tr[i].fadeOut().fadeIn();
        				$tt.before($tr[i]);
            		}
    				return false;
        		}
        	});
    	}
    	if($(this).parents("tr").attr("name")=="none"){
			none($(this).parent().parent().attr("id"));
			$(this).attr("src",basePath+"images/mobile/add.png");
		}
    });
    //下移
    var $down = $(".down");
    $down.click(function() {
    	var $trs=$(this).parents("tr").nextAll();
    	var ind=$(this).parents("tr").attr("hierarchical");
    	var $tr=new Array();
    	$tr[0]=$(this).parents("tr");
    	var ir=1,iir=0;
    	$trs.each(function(index){
    		if($(this).attr("hierarchical")==ind){
    			return false;
    		}else{
    			$tr[ir]=$(this);
    			ir++;
    		}
    	});
    	var len=$tr.length;
    	console.log("length:::"+len);
    	if($(this).parents("tr").next().attr("hierarchical")!=(ind-1)){
	    	$trs.each(function(index){
	    		if($tr.length==1&&$tr[0].next().next().attr("hierarchical")<ind){
	    			var $tt=$(this);
					$tr[0].fadeOut().fadeIn();
					$tt.after($tr[0]);
					return false;
				}else{
					if($(this).attr("hierarchical")==ind){
		    			if(iir==0){
		    				iir++;
		    			}else{
		    				var $tt=$(this);
		            		for(var i=0;i<$tr.length;i++){
		            			$tr[i].fadeOut().fadeIn();
		            			$tt.before($tr[i]);
		            		}
		            		return false;
		    			}    			
		    		}
				}
	    	});
    	}
    	if($(this).parents("tr").attr("name")=="none"){
			none($(this).parent().parent().attr("id"));
			$(this).attr("src",basePath+"images/mobile/add.png");
		}
    });
});

function ajaxGetProductMix(str){
	$("#tableR").find("tbody").html("");
	$.ajax({
		url:basePath+"ea/prodesign/sajax_ea_getSubProduct.jspa?"+str,
		type : "post",
		async : false,
		dataType : "json",
		success : function (data) {
			var member = eval("(" + data + ")");
			var list=member.sublist;
			for(var i=0;i<list.length;i++){

				var ss=(i==0?"style='border-top:0px;'":"");	
				var tr="<tr id="+list[i][1]+" class="+list[i][2]+" name='block' hierarchical="+list[i][3]+"><td width='300px;' "+ss+">";
				for(var r=0;r<list[i][3];r++){
					tr+="&nbsp;&nbsp;";
				}
					if(i!=list.length-1&&list[i+1][3]>list[i][3]){
						tr+="<img src='"+basePath+"images/mobile/close.png' style='width:10px;height:10px;' class='disImg' id='01'>";
					}else{
						tr+="&nbsp;&nbsp;&nbsp;";
					}
				
				tr+=list[i][0]+"</td><td width='148px;' "+ss+">"+(list[i][8]==null?"":list[i][8])+"</td><td width='148px;' "+ss+">"+(list[i][4]==null?"":list[i][4])+"</td><td width='148px;' "
				+ss+">"+(list[i][5]==null?"0":list[i][5])+"</td><td width='148px;' "+ss+">"
				+(list[i][6]==null?"":list[i][6])
				+"</td><td width='148px;' "+ss+">"+(list[i][9]==null?"":list[i][9])+"</td><td width='148px;'>&nbsp;&nbsp;<a class='up' ><img src='"+basePath+"images/mobile/s.png'></a>&nbsp;&nbsp;<a class='down'><img src='"+basePath+"images/mobile/x.png'/></a>&nbsp;&nbsp;<a href='javascript:' class='update'>修改</a>&nbsp;&nbsp;<a href='javascript:' class='del'>删除</a></td></tr>";
				$("#tableR").append(tr);
				$("#sel").append("<option value="+list[i][1]+">"+list[i][0]+"</option>");
		}
		},
		error:function(data){
			alert("数据获取失败");
		}
	});
	
}

function none(str){
	var strs=str.split(",");
	if(strs==""){
		return;
	}
	var s="";
	for(var i=0;i<strs.length;i++){
		$("."+strs[i]).each(function(){
			if(s=="")
				s+=$(this).attr("id");
			else
				s+=","+$(this).attr("id");
			$(this).css("display","none");
		});
	}
	none(s);
}
function block(str){
	var strs=str.split(",");
	if(strs==""){
		return;
	}
	var s="";
	for(var i=0;i<strs.length;i++){
		$("."+strs[i]).each(function(){
			if(s=="")
				s+=$(this).attr("id");
			else
				s+=","+$(this).attr("id");
			$(this).attr("name","block").css("display","");
			//$(this).find("img").attr("src",basePath+"images/mobile/close.png");
		});
	}
	block(s);
}
$("#save").live("click",function(){
	var i=1;
	var datalist= new Array();
	$("#tableR tr").each(function(){
		var object = new Object();
		object.id = i;
		object.ppid = $(this).attr("id");
		datalist.push(object);
		i++;
	});
	save(datalist);
	
});
function save(datalist){
	var url=basePath+"ea/prodesign/sajax_ea_saveSubProduct.jspa";
	$.ajax({
		type:"POST",
		url:url,
		data:{"datalist":JSON.stringify(datalist)},
		dataType: "json",
		success:function (data){
			alert("保存成功！");
			window.close();
		},error:function (){
		    alert("错误！");
		}
	});
}

$(".update").live("click",function(){
	var ppid = $(this).parents("tr").attr("id");
	var url=basePath+"ea/prodesign/ea_updateSubProduct.jspa?productPackaging.ppID="+ppid;
	document.location.href=url;
});

$(".del").live("click",function(){
	if(confirm("确定删除吗？")){
		var ppid = $(this).parent().parent().attr("id");
		alert(ppid);
		var url = basePath+"ea/prodesign/sajax_ea_deleteProduct.jspa?productPackaging.ppID="+ppid;
		$.ajax({
			url : url,
			type : "get",
			async : false,
			success : function (data) {
				alert("删除成功！");
				document.location.href=basePath+"/ea/prodesign/ea_getSubProductzz.jspa?productPackaging.ppID="+goodsBillsID;
			}
		});
	}
});

function re_load(){
	window.location.href=window.location.href;
}