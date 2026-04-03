$(function(){
	$(".arrow").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getReceiptAddressList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
	$("#subs").click(function(){
		var zz=/[0-9]/;//判断数字
        var phone=new RegExp("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
		if($(".ul").find("input").eq(0).val()==""){
			prompt("请填写收货人");
		}else if($(".ul").find("input").eq(1).val()=="")
            prompt("手机号码不可为空");
        else if(!phone.test($(".ul").find("input").eq(1).val()))
            prompt("手机号码格式不对");
        else if($("#location_p").find("option:selected").text()=='请选择')
            prompt("请选择省");
        else if($("#location_c").find("option:selected").text()=='请选择')
            prompt("请选择市");
        else if($("#location_d").find("option:selected").text()=='请选择')
            prompt("请选择区");
       else if($(".ul").find("input").eq(2).val()==""){
			prompt("请填写详细地址");
		}/*else if($("#postcode").find("option:selected").val().length!=6||!zz.test($("#postcode").find("option:selected").val())){
			prompt("请选择邮政编码");
		}*/else{
			//var str="staffVo.address['0'].area="+$("#location_p option:selected").text()+","+$(".location_c option:selected").text()+","+$(".location_a option:selected").text();
			$("#str").val($("#location_p option:selected").text()+","+$(".location_c option:selected").text()+","+$(".location_a option:selected").text());
			
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_ReceivingAddressAddOrEdit.jspa?editType="+editType);
			document.form.submit.click();
			token = 2;
		}
	});
	$.ajax({
		url:basePath+"ea/perinfor/sajax_ea_getCitiesList.jspa",
		type:"post",
		success:function(data){
			var member=eval("("+data+")");
			var result=member.result;
			var province="<select style='height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px;" 
						+"background-color:#fff;'><option>请选择</option>",city="",area="";
			for(var i=0;i<result.length;i++){
				province+="<option value="+result[i].id+">"+result[i].province+"</option>";
				city="<select id="+result[i].id+" style='display:none;height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;" 
						+"background-color:#fff;font-size:"+$(window).height()*0.03+"px;'><option>请选择</option>";
				for(var r=0;r<result[i].city.length;r++){
					city+="<option value="+result[i].city[r].id+">"+result[i].city[r].city+"</option>";
					area="<select id="+result[i].city[r].id+" style='display:none;height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;" 
							+"background-color:#fff;font-size:"+$(window).height()*0.03+"px;'><option>请选择</option>";
					for(var s=0;s<result[i].city[r].district.length;s++){ 
						area+="<option value="+result[i].city[r].district[s].id+">"+result[i].city[r].district[s].district+"</option>";
					}
					area+="</select>";
					$("#location_a").append(area);
				}
				city+="</select>";
				$("#location_c").append(city);
			}
			province+="</select>";
			$("#location_p").append(province);
		},
		error:function(data){
			alert("数据获取失败");
		}
	});
	$("#location_p").find("select").live("change",function(){
		$("#location_c").find("select").css("display","none").removeClass();
		$("#location_c").find("#"+$(this).find("option:selected").val()).css("display","block").addClass("location_c");
	});
	$("#location_c").find("select").live("change",function(){
		$("#location_a").find("select").css("display","none").removeClass();
		$("#location_a").find("#"+$(this).find("option:selected").val()).css("display","block").addClass("location_a");
	});
/*	$("#address").change(function(){
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
				$("#postcode").html(postcode);
			},error:function(data){
				alert("数据获取失败");
			}			
		});
	});
	$("#location_a").find("select").live("change",function(){
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
				$("#postcode").html(postcode);
			},error:function(data){
				alert("数据获取失败");
			}			
		});
	});*/
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

$(document).ready(function(e){
		$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
			//头部
        $(".top").attr("style","height:"+$(window).height()*0.058+"px;line-height:"+$(window).height()*0.058+"px;");
        $(".top").find("li").attr("style","width:10%;");
        $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
        $(".top").find("li").eq(1).attr("style","width:80%;font-size:"+$(window).height()*0.035+"px;");
		//add
		$(".add button").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.03+"px;border-radius:"+$(window).height()*0.01+"px; margin:"+$(window).height()*0.02+"px auto;");
		//line
		$(".line").attr("style","height:"+$(window).height()*0.01+"px;");
		//content
		$(".content").attr("style","height:"+$(window).height()*0.83+"px;");
		$(".content li h5").attr("style","height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px;");
		$(".content li input").attr("style","height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px;");
		$(".content li select").attr("style","height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px;background-color:#fff;");
		$(".content .del").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px; margin:"+$(window).height()*0.03+"px 0;");
		$("#prompt").css("position","absolute").css("top",$(window).height()*0.086+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
			$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
		var u = window.navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		if(isAndroid==true){
			$(".new_").attr("style","height:"+$(window).height()*0.058+"px;");
		}else if(isiOS==true){
			$(".new_").hide();
		}
		if(flag==2){
			$(".top").show();
		}else {
			$(".top").hide();
		}
});
function re_load (){
    if(source=="address"){
        window.location.href=basePath+"/ea/wfjshop/ea_getAddressList.jspa?inventory.inventoryID="+inventoryID+"&ppid="+ppid+"&count="+count+"&intf="+intf;
    }else {
        window.location.href = basePath + "ea/perinfor/ea_getReceiptAddressList.jspa?staffId=" + $("#staffId").val() + "&editType=" + editType + "&backurl=" + backurl + "&flag=" + flag;
    }
}