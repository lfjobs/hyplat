/*$(document).ready(function(){
	var index=1;
	$(":checkbox").click(function(){  
		if($(this).attr("checked")){
			$(this).parents("tr").find("input#complete").val("1");
		}else{
			$(this).parents("tr").find("input#complete").val("0");
		}
	});
	$("img.add").click(function(){
		$("tr#clone").after(
					$("tr#clone").clone(true).attr("id","clone"+index).css("display","")
		);
		$("tr#clone" + index).find(':input').each(function() {
					$(this).attr("disabled",false).attr("name","ddsrtrainingrecordMap[" + index + "]."+$(this).attr("id"));
		});
		index++;
	});
	$("img.edit",$("tr[class!='clone']")).click(function(){
		$(this).parents("tr").find(":input").each(function(){
			$(this).attr("disabled",false).attr("name","ddsrtrainingrecordMap[" + index + "]."+$(this).attr("id")).show();
			if($(this).attr("id")=="subject"|$(this).attr("id")=="program"){
				$(this).attr("value",$.trim( $(this).next("span#"+$(this).attr("id")).text() ));
			}
		});
		$(this).parents("tr").find("span").each(function(){
			$(this).css("display","none");
		});
		index++;
	});
	$("tr[class!='clone']").dblclick(function(){
		$(this).find(":input").each(function(){
			$(this).attr("disabled",false).attr("name","ddsrtrainingrecordMap[" + index + "]."+$(this).attr("id")).show();
			if($(this).attr("id")=="subject"|$(this).attr("id")=="program"){
				$(this).attr("value",$.trim( $(this).next("span#"+$(this).attr("id")).text() ));
			}
		});
		$(this).find("span").each(function(){
			$(this).css("display","none");
		});
		index++;
	});
	$("img.del").click(function(){
		$(this).parents("tr").remove();
		var key=$(this).parents("tr").find("input#key").attr("value");
		if(key==""){
			return false;
		}
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"100%"}).appendTo("#divOfDdsrtrainingrecord");     
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在删除。。。").appendTo("#divOfDdsrtrainingrecord").css({display:"block",left:($("#divOfDdsrtrainingrecord").outerWidth(true)-100) / 2,top:($("#divOfDdsrtrainingrecord").outerHeight(true) - 45) / 2});
		
        var urlString=basePath+"/ea/trainingregistration/sajax_ea_delOfDdsrtrainingrecordByAjax.jspa?date="+new Date();
		$.ajax({
	             type: "post",
	             url: urlString,
	             data: {"ddsrtrainingrecord.key":key},
	             dataType: "json",
	             async: false,
	             success: function(data){
		             var data = eval("(" + data + ")"); 
		             $("#divOfDdsrtrainingrecord").find("div.datagrid-mask-msg").remove();
		             $("#divOfDdsrtrainingrecord").find("div.datagrid-mask").remove();
	             },
	             error: function(data) {
	                    alert("数据异常");
	             }
	         });
	});
	$("#JQuerySubmitOfDdsrtrainingrecord").click(function() {
		if(index==1){
			return false;
		}
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"100%"}).appendTo("#divOfDdsrtrainingrecord");     
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在保存。。。").appendTo("#divOfDdsrtrainingrecord").css({display:"block",left:($("#divOfDdsrtrainingrecord").outerWidth(true)-100) / 2,top:($("#divOfDdsrtrainingrecord").outerHeight(true) - 45) / 2});
        
        var urlString=basePath+"/ea/trainingregistration/sajax_ea_toSaveDdsrtrainingrecordByAjax.jspa?date="+new Date();
		$.ajax({
	             type: "post",
	             url: urlString,
	             data: $("#box1Form").serialize(),
	             dataType: "json",
	             async: false,
	             success: function(data){
		             var data = eval("(" + data + ")"); 
		             window.location.reload();
	             },
	             error: function(data) {
	                    alert("数据异常");
	             }
	         });
	});
	
	$("input#startDate,input#endDate").live("blur focus",function(){
		var startDate;
		var endDate;
		var ms;
		if($.trim($(this).attr("value"))!=""){
			if($.trim($(this).attr("id"))=="startDate"){
				startDate=new Date($.trim($(this).attr("value")).replace(/-/g,"/"));
				endDate=new Date($.trim($(this).parents("tr").find("input#endDate").attr("value")).replace(/-/g,"/"));
			}else{
				startDate=new Date($.trim($(this).parents("tr").find("input#startDate").attr("value")).replace(/-/g,"/"));
				endDate=new Date($.trim($(this).attr("value")).replace(/-/g,"/"));
			}
			ms=endDate.getTime()-startDate.getTime();  //时间差的毫秒数
			var hours=ms/(3600*1000);
			$(this).parents("tr").find("input#time").attr("value",hours.toFixed(2));
		}
	});
	 if($(this).attr("id")=="subject"){
		 $(this).trigger("change");
	 	}
	
	$("select#subject").change(function(){
		var $t=$(this);
		var urlString=basePath+"/ea/trainingregistration/sajax_ea_getListOfDdsrsyllabusByAjax.jspa?date="+new Date();
		var subject=$t.attr("value");
		var type=$t.parents("tr").find("input#type").attr("value");
		$.ajax({
	             type: "post",
	             url: urlString,
	             data: {"ddsrsyllabus.subject":subject,"ddsrsyllabus.type":type},
	             dataType: "json",
	             async: false,
	             success: function(data){
	             var data = eval("(" + data + ")"); 
	             var beanList=data.beanList;
	             	$t.parents("tr").find("select#program").html("");
	             	$t.parents("tr").find("select#program").append($("<option/>").attr("value","").text("请选择"));
		         	for ( var i = 0; i < beanList.length; i++) {
						var ddsrsyllabus=beanList[i];
						$t.parents("tr").find("select#program").append($("<option/>").attr("value",ddsrsyllabus.program).text(ddsrsyllabus.program));
					 }
	             },
	             error: function(data) {
	                    alert("数据异常");
	             }
	
	     });
	});
});
*/