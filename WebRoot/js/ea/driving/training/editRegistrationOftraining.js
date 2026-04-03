$(document).ready(function(){
	 self.opener.location.reload(); //刷新父窗口  
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
					if($(this).attr("id")=="subject"){
						$(this).setSelect(ppID);
						$(this).attr("value",docstatus=="01"?"科目一":docstatus=="02"?"科目二":docstatus=="03"?"科目三":docstatus=="04"?"科目四":"");
					}
					if($(this).attr("id")=="program"){
						$(this).setSelect($.trim( $(this).parents("tr").find("select#subject option:selected").attr("ppID")));
					}
		});
		index++;
	});
	$("img.edit",$("tr[class!='clone']")).click(function(){
		$(this).parents("tr").find(":input").each(function(){
			$(this).attr("disabled",false).attr("name","ddsrtrainingrecordMap[" + index + "]."+$(this).attr("id")).show();
			if($(this).attr("id")=="subject"){
				$(this).setSelect(ppID);
				$(this).attr("value",$.trim( $(this).next("span#"+$(this).attr("id")).text() ));
			}
			if($(this).attr("id")=="program"){
				$(this).setSelect($.trim( $(this).parents("tr").find("select#subject option:selected").attr("ppID")));
				$(this).attr("value",$.trim( $(this).next("span#"+$(this).attr("id")).text() ));
			}
		});
		$(this).parents("tr").find("span").each(function(){
			$(this).css("display","none");
		});
		index++;
	});
	$("tr[class!='clone']").dblclick(function(){
		$("img.edit",$(this)).trigger("click");
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
		var hours;
		if($.trim($(this).attr("value"))!=""){
			if($.trim($(this).attr("id"))=="startDate"){
				startDate=new Date($.trim($(this).attr("value")).replace(/-/g,"/"));
				endDate=new Date($.trim($(this).parents("tr").find("input#endDate").attr("value")).replace(/-/g,"/"));
			}else if($.trim($(this).attr("id"))=="endDate"){
				startDate=new Date($.trim($(this).parents("tr").find("input#startDate").attr("value")).replace(/-/g,"/"));
				endDate=new Date($.trim($(this).attr("value")).replace(/-/g,"/"));
			}
			var ms=endDate.getTime()-startDate.getTime();  //时间差的毫秒数
			hours=ms/(3600*1000);
			if(!isNaN(hours.toFixed(2))){
				$(this).parents("tr").find("input#time").attr("value",hours.toFixed(2));
			}
		}
	});
	$("input#time").live("blur focus",function(){
		var startDate;
		var endDate;
		var hours;
		if($.trim($(this).attr("value"))!=""){
			if($.trim($(this).parents("tr").find("input#startDate").attr("value"))!=""){
				startDate=new Date($.trim($(this).parents("tr").find("input#startDate").attr("value")).replace(/-/g,"/"));
				startDate.setMinutes(startDate.getMinutes()+(parseFloat($(this).attr("value"))*60));
				var formatDate=startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+startDate.getDate()+" "+startDate.getHours()+":"+startDate.getMinutes()+":"+startDate.getSeconds();
				$(this).parents("tr").find("input#endDate").attr("value",formatDate);
			}else if($.trim($(this).parents("tr").find("input#endDate").attr("value"))!=""){
				endDate=new Date($.trim($(this).parents("tr").find("input#endDate").attr("value")).replace(/-/g,"/"));
				endDate.setMinutes(endDate.getMinutes()-(parseFloat($(this).attr("value"))*60));
				var formatDate=endDate.getFullYear()+"-"+(endDate.getMonth()+1)+"-"+endDate.getDate()+" "+endDate.getHours()+":"+endDate.getMinutes()+":"+endDate.getSeconds();
				$(this).parents("tr").find("input#startDate").attr("value",formatDate);
			}
		}
	});
	
	 if($(this).attr("id")=="subject"){
		 $(this).trigger("change");
	 	}
	
	$("select#subject").change(function(){
		if($.trim( $(this).parents("tr").find("select#subject option:selected").attr("ppID"))!=""){
			$(this).parents("tr").find("select#program").setSelect($.trim( $(this).parents("tr").find("select#subject option:selected").attr("ppID")));
			$(this).parents("tr").find("select#program").attr("value",$.trim( $(this).parents("tr").find("select#program").next("span#program").text() ));
		}
	});
	
	$.fn.extend({
		setSelect:function(ppID){
			$this=$(this);
			var urlString=basePath+"/ea/productdesign/sajax_ea_getProductPackagingListByPID.jspa?date="+new Date();
			$this.html("");
         	$this.append($("<option/>").attr("value","").text("请选择"));
			$.ajax({
		             type: "post",
		             url: urlString,
		             data: {"productDesign.ppID":ppID},
		             dataType: "json",
		             async: false,
		             success: function(data){
		             var data = eval("(" + data + ")");
                         var nologin = member.nologin;
                         if (nologin) {
                             document.location.href = basePath
                                 + "page/ea/not_login.jsp";
                         }
		             var beanList=data.productPackagingList;
			         	for ( var i = 0; i < beanList.length; i++) {
							var productDesign=beanList[i];
							$this.append($("<option/>").attr("value",productDesign.goodsName).attr("ppID",productDesign.ppID).text(productDesign.goodsName));
						 }
		             },
		             error: function(data) {                                  
		                    alert("数据异常");
		             }
		
		     });
		}
	});
});
