$(document).ready(function() {
	// 切换
	$(".tab li").click(function() {
	    if($(this).attr("id")=="showfunc"){
	    	//如果是点击的是功能介绍
	    	var goodsID = $("#gdid").val();
	    	if(goodsID==""){
	    		alert("请先填写产品名称");
	    		return;
	    	}else{
	    		getFunctionList(goodsID);
	    	}
	    }
		$("." + $(".selected").attr("id")).addClass("hidcontent");
		$(".selected").removeClass("selected");
	
		$(this).addClass("selected");
		$("." + $(this).attr("id")).removeClass("hidcontent");
	});

   // 计算金额
	$(".jisuan").bind('input propertychange', function() {
		var a1 = $(this).val();
		if (isNaN(a1)) {
			// 非数字
			return;
		}

		var a2;

		if ($(this).attr("id") == "price") {
			a2 = $("#quantity").val();
		} else {
			a2 = $("#price").val();
		}
		$("#money").val(a1 * a2);

	});
	// 提交保存
	$(".save").click(function() {
		$("#mainForm").attr("target","hidden").attr("action",
				basePath + "ea/prodesign/ea_saveProducts.jspa");
		document.mainForm.submit.click();
        token = 2;
	});
});

//获取功能介绍列表ajax
function getFunctionList(goodsID) {
	var url = basePath + "ea/prodesign/sajax_ea_ajaxGoodFunction.jspa?d="+new Date()+"&category="+category;
	$.ajax({
		url : url,
		type : "get",
		dataType : "json",
		asycn : false,
		data : {
			"productPackaging.goodsID" : goodsID

		},
		success : function(data) {
			     
			var mem = eval("(" + data + ")");
			var functionlist = mem.functionlist;
			var maplist = mem.maplistjs;
			var str = "";
			for (var i = 0; i < functionlist.length; i++) {
				str+="<strong>"+functionlist[i].name+"</strong>";
				str+="</br></br>";
				
				$.each(maplist,function(key,values){     
                   if(key==i+1){
                   	  str+=values;
                   	  str+"</br>";
                   }
                     
                }); 
			}
			$(".showfunc").html(str);

		},
		error : function(data) {
			alert("获取功能介绍失败");
		}

	});
}


function re_load() {
	window.opener.location.href=window.opener.location.href;
	window.close();

}

