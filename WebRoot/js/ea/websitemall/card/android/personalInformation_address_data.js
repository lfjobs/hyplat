$(function(){

	var style="height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;";
	$(".arrow").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getReceiptAddressList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl+"&flag="+flag;
	});
	//初始化省市区显示
    addressValue();

	$("#edit").live("click",function(){
		$(".data").find("input").removeAttr("readonly");
		var test="";
		$(".address").each(function(){
			if(test=="")
				test+=$(this).find("input").val();
			else
				test+=","+$(this).find("input").val();
			$(this).find("input").remove();
		});
		var tests=test.split(",");	
		$.ajax({
			url:basePath+"ea/perinfor/sajax_ea_getCitiesList.jspa",
			type:"post",
			async:false,
			success:function(data){
				var member=eval("("+data+")");
				var result=member.result;
				var province="<select id='location_p' style="+style+">",city="",area="";
				for(var i=0;i<result.length;i++){
					province+="<option value="+result[i].id+">"+result[i].province+"</option>";
					city="<select id="+result[i].id+" style='display:none;height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;"
							+"background-color:#fff;font-size:"+$(window).height()*0.03+"px;'><option>请选择</option>";
					for(var r=0;r<result[i].city.length;r++){
						city+="<option value="+result[i].city[r].id+">"+result[i].city[r].city+"</option>";
						area="<select id="+result[i].city[r].id+" style='display:none;height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;"
								+"background-color:#fff;font-size:"+$(window).height()*0.03+"px;'><option>请选择</option>";
						for(var s=0;s<result[i].city[r].district.length;s++){ 
							area+="<option value="+result[i].city[r].district[s].id+">"+result[i].city[r].district[s].district+"</option>";
						}
						area+="</select>";
						$("#a").append(area);
					}
					city+="</select>";
					$("#c").append(city);
				}
				province+="</select>";
				$("#p").append(province);
				// $("#c").append("<select style="+style+" id='location_c'  value="+tests[1]+"><option>"+tests[1]+"</option></select>");
				// $("#a").append("<select style="+style+" id='location_a' value="+tests[2]+"><option>"+tests[2]+"</option></select>");
				// $("#postcode").append("<select style="+style+"  name=\"staffVo.receiptAddress['0'].postcode\"  value="+$("#postcode").find("input").val()+"><option>"+$("#postcode").find("input").val()+"</option></select>");
				// $("#postcode").find("input").remove();
			},
			error:function(data){
				alert("数据获取失败");
			}
		});
        $("#edit").attr("id","preservation").find("li").text("完成");
        $("#p option:contains("+tests[0]+")").attr("selected", "selected");
		$("#c #"+$('#p option:selected').val()+"").css('display','block').addClass("location_c");
        $("#c option:contains("+tests[1]+")").attr("selected", "selected");
        $("#a").find("#"+$(".location_c").find("option:selected").val()).css('display','block').addClass("location_a");
        $("#a option:contains("+tests[2]+")").attr("selected", "selected");
	});
	$("#p").find("select").live("change",function(){
		$("#location_c").remove();
		$("#provincer").remove();
		$("#location_a").find("option").text("请选择");
		$("#c").find("select").css("display","none").removeClass();
		$("#c").find("#"+$(this).find("option:selected").val()).css("display","block").addClass("location_c");
	});
	$("#c").find("select").live("change",function(){
		$("#a").find("select").css("display","none").removeClass();
		$("#a").find("#"+$(this).find("option:selected").val()).css("display","block").addClass("location_a");
	});
	$("#address").change(function(){
		var s=$("#address").val().indexOf("乡");
		if(s==-1)
			s=$("#address").val().indexOf("镇");
		var str=$("#address").val().substring(0,s+1);
		var url="provinceID="+$("#location_p option:selected").val()+"&cityID="+$(".location_c option:selected").val()
		+"&areaID="+$(".location_a option:selected").val()+"&address="+str;
		$.ajax({
			url:basePath+"ea/perinfor/sajax_ea_getZipCode.jspa?"+url,
			type:"post",
			async:false,
			success:function(data){
				var member=eval("("+data+")");
				var list=member.result.list;
				var postcode="<option>请选择</option>";
				if(list==null)
					return;
				var strs=new Array();
				for(var s=0;s<list.length;s++){
					strs[s]=list[s].PostNumber;
				}
				var str=getUnique2(strs);
				for(var i=0;i<str.length;i++){
					postcode+="<option>"+str[i]+"</option>";
				}
				$("#postcode").find("select").remove();
				$("#postcode").append("<select style="+style+" name=\"staffVo.receiptAddress['0'].postcode\">"+postcode+"</select>");
			},error:function(data){
				alert("数据获取失败");
			}			
		});
	});
	$("#a").find("select").live("change",function(){
		var s=$("#address").val().indexOf("乡");
		if(s==-1)
			s=$("#address").val().indexOf("镇");
		var str=$("#address").val().substring(0,s+1);
		var url="provinceID="+$("#location_p option:selected").val()+"&cityID="+$(".location_c option:selected").val()
				+"&areaID="+$(".location_a option:selected").val()+"&address="+str;
		$.ajax({
			url:basePath+"ea/perinfor/sajax_ea_getZipCode.jspa?"+url,
			type:"post",
			async:false,
			success:function(data){
				var member=eval("("+data+")");
				var list=member.result.list;
				var postcode="<option>请选择</option>";
				if(list==null)
					return;
				var strs=new Array();
				for(var s=0;s<list.length;s++){
					strs[s]=list[s].PostNumber;
				}
				var str=getUnique2(strs);
				for(var i=0;i<str.length;i++){
					postcode+="<option>"+str[i]+"</option>";
				}
				$("#postcode").find("select").remove();
				$("#postcode").append("<select style="+style+"  name=\"staffVo.receiptAddress['0'].postcode\">"+postcode+"</select>");
			},error:function(data){
				alert("数据获取失败");
			}			
		});
	});
	$("#preservation").live("click",function(){
		var zz=/[0-9]/;						//判断数字
		if($(".ul").find("input").eq(0).val()==""){
			prompt("请填写收货人");
		}else if($(".ul").find("input").eq(1).val()==""){
			prompt("请填写手机号码");
		}else if($(".ul").find("input").eq(2).val()==""){
			prompt("请填写详细地址");
		}else{
			var str="staffVo.address['0'].area="+$("#location_p option:selected").text()+","+$(".location_c option:selected").text()+","+$(".location_a option:selected").text();
			$("#form").attr("target", "hidden").attr("action",encodeURI(basePath+"ea/perinfor/ea_ReceivingAddressAddOrEdit.jspa?editType="+editType+"&"+str+"&flag="+flag));
			document.form.submit.click();
			token = 2;
		}
	});
	//del click
	$(".content .del").click(function(){
			//if(confirm("是否删除该收货地址")){
		$(".del_c .ctip").text("是否删除该收货地址");
		$(".del_c").show();
        $(".del_c #yes").removeClass("setDefault").addClass("delAdd");

			//}
		});
	//确定删除收货地址
    $(".del_c .delAdd").live("click",function(){

        $("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_ReceivingAddressDefaultOrDelete.jspa?editType="+editType+"&flag="+flag+"&stauts=del");
        document.form.submit.click();
        token = 2;

	});
    //关闭弹框
    $(".del_c #no").click(function(){

        $(".del_c").hide();

    });


  //设置默认
	$(".default").click(function(){

        $(".del_c .ctip").text("设置该为默认收货地址");
        $(".del_c").show();
        $(".del_c #yes").removeClass("delAdd").addClass("setDefault");


	});
    //确定设置默认
    $(".del_c .setDefault").live("click",function(){
        $("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_ReceivingAddressDefaultOrDelete.jspa?editType="+editType+"&stauts=default");
        document.form.submit.click();
        token = 2;

    });
	
});
function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}
$(document).ready(function(e){
		$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
			//头部
        $(".top").attr("style","height:"+$(window).height()*0.058+"px;line-height:"+$(window).height()*0.058+"px;");
        $(".top").find("li").attr("style","width:15%;font-size:"+$(window).height()*0.03+"px;");
        $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
        $(".top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.035+"px; letter-spacing:4px; ");
		//add
		$(".add button").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.03+"px;border-radius:"+$(window).height()*0.01+"px; margin:"+$(window).height()*0.02+"px auto;");
		//line
		$(".line").attr("style","height:"+$(window).height()*0.01+"px;");
		//content
		$(".content").attr("style","height:"+$(window).height()*0.83+"px;");
		$(".content li h5").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
		$(".content li input").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
		$(".content li select").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px;");
		//del
		$(".content .del").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;font-size:"+$(window).height()*0.03+"px; margin:"+$(window).height()*0.03+"px 0;");
	
		$("#prompt").css("position","absolute").css("top",$(window).height()*0.135+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
    if(flag==2){
        $(".arrow").show();
    }else {
        $(".arrow").hide();
    }
});
function getUnique2(data){  
	   data = data || [];  
	   var a = {};  
	   for (var i=0; i<data.length; i++) {  
	       var v = data[i];  
	       if (typeof(a[v]) == 'undefined'){  
	            a[v] = 1;  
	       }  
	   };  
	   data.length=0;  
	   for (var i in a){  
	        data[data.length] = i;  
	   }  
	   return data;  
	}
function addressValue() {
    if(area!=null){
        var areaArray = area.split(",");
        $("#p input").val(areaArray[0]!=null?areaArray[0]:"");
        $("#c input").val(areaArray[1]!=null?areaArray[1]:"");
        $("#a input").val(areaArray[2]!=null?areaArray[2]:"");
	}
}

function re_load (){
	if(source=="address"){
        window.location.href=basePath+"/ea/wfjshop/ea_getAddressList.jspa?inventory.inventoryID="+inventoryID+"&ppid="+ppid+"&count="+count+"&intf="+intf+"&staffid="+staffid+"&backurls="+encodeURIComponent(backurls);
	}else {
        window.location.href=basePath+"ea/perinfor/ea_getReceiptAddressList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	}
}