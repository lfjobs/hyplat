$(document).ready(function(e) {	
	
	object.push("khd=",khd);
	object.push("&flag=",flag);		
	object.push("&identifying=",identifying);
	
	loaded();
	if(dc=="2") {
		loadedwx();
	}
	$(".content_hidden").attr("style",";overflow: auto;position: relative;"+"top:"+$(window).height()*0.08+"px");
    $(".content").attr("style",";overflow: auto;width:95%;margin:0 auto;");
	
	//中联园区头部
	$(".wfj_top").attr("style","height:" + $(window).height()* 0.08 + "px;line-height:"+ $(window).height() * 0.08+ "px;");
	//$(".wfj01_019 .wfj_top ul").attr("style","height:" + $(window).height()* 0.08 + "px;line-height:"+ $(window).height() * 0.08+ "px;");

	$(".wfj_top").find("li").attr("style", "width:15%;");
	$(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px");
		$(".wfj_top").find("li").find("img").each(function(){
    	var ihei=imgPosition($(".wfj_top").height(),$(this).height());
    	/* $(this).attr("style",$(this).attr("style")+";margin-top:"+ihei+"px"); */
    	$(this).attr("style",$(this).attr("style")+";");

		});
	$(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:" + $(window).height()* 0.06 + "px;");
	$(".wfj01_019_top").attr("style","height:" + $(window).height()* 0.12 + "px;line-height:"+ $(window).height() * 0.12+ "px;");
	$(".wfj01_019_top").find("li").attr("style","font-size:" + $(window).height()* 0.025 + "px;line-height:"+ $(window).height() * 0.06+ "px;");
	$(".wfj01_019_top").find("font").attr("style","font-size:" + $(window).height() * 0.04+ "px;");
	$(".wfj01_019_top").find("img").attr("style","width:" + $(window).height() * 0.025+ "px;");
	$(".wfj01_019_top_date").attr("style"," border-right:" + $(window).height()* 0.003 + "px dotted #FFF;;");
	$(".wfj01_019_top_title").attr("style","height:" + $(window).height() * 0.1+ "px;line-height:"+ $(window).height() * 0.1 + "px;");
	$(".wfj01_019_top_link").find("li").attr("style","font-size:" + $(window).height() * 0.02+ "px;");
	$(".wfj01_019_top_link").find("img").attr("style","height:" + $(window).height()* 0.06+ "px; margin-right:"+ $(window).height() * 0.01+ "px;");
	$(".wfj01_019_top_link").find("li").eq(0).attr("style","font-size:" + $(window).height() * 0.02+ "px;color:#ea8010;");
	$(".wfj01_019_con").attr("style","margin-top:" + $(window).height() * 0.01+ "px;");
	$(".wfj01_019_con_bill").find("td").attr("style","font-size:" + $(window).height() * 0.02+ "px; border-bottom:"+ $(window).height() * 0.003+ "px solid #F0F0F0; height:"+ $(window).height() * 0.065+ "px;");
	$(".wfj01_019_con_type").attr("style","margin-top:" + $(window).height() * 0.02+ "px;");
	$(".wfj01_019_con_type").find("td").attr("style","height:" + $(window).height()* 0.05 + "px;font-size:"+ $(window).height()* 0.025+ "px; padding-left:"+ $(window).height() * 0.01+ "px;");
	$(".wfj01_019_con_type").find("input").attr("style","width:" + $(window).height() * 0.1+ "px;height:" + $(window).height()* 0.03 + "px;");
	$(".wfj01_019_con_type").find("#myChart").attr("style","margin-top:" + $(window).height() * 0.03+ "px;");
	$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").css({"position":"absolute","top":$(window).height()*0.5+"px","width":$(window).width()*0.27+"px","left":"41%"})
	$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").find("p").css({"font-size":$(window).height()*0.02+"px","line-height":$(window).height()*0.033+"px"})
	$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").find("p").find(".span1").css({"color":"#6387A8","font-size":$(window).height()*0.03+"px"})
	$(".wfj01_019_con .wfj01_019_con_type .wfj01_019_con_type_width .wfj01_019_con_type_text").find("p").find(".span2").css({"color":"#FA5C29","font-size":$(window).height()*0.03+"px"})	
	$(".wfj01_019_date").attr("style","top:" + $(window).height() * 0.2 + "px;");
	$(".wfj01_019_date").find("li").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
	$(".wfj01_019_choice_date").attr("style"," height:" + $(window).height() * 0.06+ "px; line-height:"+ $(window).height() * 0.06+ "px; border-bottom:"+ $(window).height() * 0.008+ "px solid #4CACCE;font-size:"+ $(window).height() * 0.025+ "px;");
	$(".wfj01_019_choice_date").find("font").attr("style","padding-left:" + $(window).height()* 0.015 + "px;");
	$(".wfj01_019_date").find("li").find("div").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
	$(".wfj01_019_date").find(".wfj01_019_date_center").attr("style"," height:" + $(window).height()* 0.18 + "px;");
	$(".wfj01_019_date_bottom").attr("style"," height:" + $(window).height()* 0.06 + "px; line-height:"+ $(window).height() * 0.06+ "px;");
	$(".allgold").css("backgroundColor", "#6387A8");
	$(".othergold").css("backgroundColor", "#FA5C29");
	
	$(".zj").css("font-size",$(window).width()*0.04+"px");
	$(".sj").css("font-size",$(window).width()*0.04+"px");
	//$(".con-bill_txt h3").css("font-size",$(window).width()*0.04+"px");
	$(".con-bill_mil li p").css("font-size",$(window).width()*0.04+"px");
	$(".con-bill_mil li .con-bill_head").attr("style","width:"+$(window).width()*0.126+"px;"+"height:"+$(window).width()*0.126+"px;");
	
	 $(".wfj01_019 .wfj_top ul").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
        $(".wfj01_019 .wfj_top ul li").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
        $(".wfj01_019 .wfj_top ul li:nth-child(1)").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:15%;");

        $(".wfj01_019 .wfj_top ul li:nth-child(2)").attr("style","width:70%;font-size:" + $(window).height()* 0.025 + "px;");

        $(".wfj01_019 .wfj_top ul li:nth-child(2)").attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");

        $(".wfj01_019 .wfj_top ul li:nth-child(3)").attr("style","width:15%;");
	
	var len = $(".wfj01_019_con_bill").find("tr").length;
	for ( var i = 0; i < len; i++) {
		if ($(".wfj01_019_con_bill").find("tr").eq(i).find(".wfjGuizeCalc").val() == "A") {
			$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#5584D3");
			$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
		} else {
			$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#EB4F38");
			$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
		}
	}

	$(".wfj01_019_top_link").find("li").click(function() {
		if ($(this).attr("id") == "title1") {
			$(".wfj01_019_top_link").find("li").css("color","#666");
			$(this).css("color","#ea8010");
			$(".wfj01_019_con_bill").css("display","block");
			$(".wfj01_019_con_type").css("display","none");
			$(".dh").text(xjtx);
			$(".sy").text(jdetail);
		} else {
			$(".dh").text(xjtx2);
			$(".sy").text(jdetail2);
			$(".wfj01_019_top_link").find("li").css("color","#666");
			$(this).css("color","#5D9164");
			$(".wfj01_019_con_bill").css("display","none");
			$(".wfj01_019_con_type").css("display","block");

			if(dc=="1") {
				$("#myChart").css("width", "100%");
				$("#myChart").css("height", "auto");

				var ctx = document.getElementById("myChart").getContext('2d');
				var myChart = new Chart(ctx).Doughnut(data);
			}
		}
	});

	if ($(window).width() > $(window).height()) {
		$(".wfj01_019").css("width", "70%");
	} else {
		$(".wfj01_019").css("width", "100%");
	}

	var h = $(".wfj01_019_con").height()+ $(window).height() * 0.01;

	$(".wfj01_019_content").attr("style","width:"+ $(".wfj01_019").width()+ "px; height:"+ parseInt($(window).height()- $(".wfj_top").height()- $(".wfj01_019_top").height()- $(".wfj01_019_top_title").height()- $(window).height()* 0.01)	+ "px;overflow:hidden;");

	if (h > $(".wfj01_019_content").height()) {
		$(".wfj01_019_hidden").attr("style","width:"+ parseInt($(".wfj01_019_content").width() + 17)+ "px; height:"+ $(".wfj01_019_content").height()+ "px;overflow:auto;");
	} else {
		$(".wfj01_019_hidden").attr("style","width:"+ parseInt($(".wfj01_019_content").width())+ "px; height:"+ $(".wfj01_019_content").height()+ "px;overflow:auto;");
		
	}
	
	if($("#jbtab").height()<$(".wfj01_019_content").height()){
		$("#scroller").attr("style","width:"+ parseInt($(".wfj01_019_content").width())+ "px; height:"+ parseInt($(window).height()-($(".wfj_top").height()+ $(".wfj01_019_top").height()+ $(".wfj01_019_top_title").height()+ $(window).height()* 0.01))+ "px");
	}
	
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
	}).jqmAddClose('.close');
	
	
	

});
//取零
function getNow(s) {
    return s < 10 ? '0' + s : s;
}

function toWeek(date){
	var  str=date.toString();
		str =  str.replace(/-/g,"/");
		var oDate1 = new Date(str);
	var d = new Date(oDate1.getTime());
	var week = WEEKs[d.getDay()];
    return week;
}

function toDDMMMYYYY(date) {  
    var d = new Date(date.getTime());  
    var dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate().toString();  
    var mmm = mths[d.getMonth()];
    var yyyy = d.getFullYear().toString(); //2011  
    return mmm+"-"+dd;
}

function toHHMISS(date) {
    var myDate = new Date(date.getTime());
    var hour = myDate.getHours();  //获取当前小时数(0-23)
    var min = myDate.getMinutes(); //获取当前分钟数(0-59)
    var sec = myDate.getSeconds();//获取当前秒数(0-59)
    return getNow(hour)+':'+getNow(min) +':'+ getNow(sec );
}

function daysBetween(DateOne,DateTwo){
	var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));
	var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);
	var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));
	
	var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));
	var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);
	var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));
	
	var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);
	return Math.abs(cha);
}

function fmoney(s, n){
	   n = n > 0 && n <= 20 ? n : 2;
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	   var l = s.split(".")[0].split("").reverse(),
	   r = s.split(".")[1];
	   t = "";
	   for(i = 0; i < l.length; i ++ )
	   {
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	   }
	   return t.split("").reverse().join("") + "." + r;
}

function getHeight(){

	
	t=setTimeout("getHeight()",200);
	if($(".last").length>0){
	   	if($(".last").offset().top-$(".wfj01_019_top").height()*3.5<$(window).height()){
	   		if(pagenumber<pagecount){
	   			loaded();
	   		}
	   	}		 
	  }
}
function getHeight2(){
	tt=setTimeout("getHeight2()",200);
	if($(".last2").length>0){
		if($(".last2").offset().top-$(".wfj01_019_top").height()*3.5<$(window).height()){
			if(pagenumber2<pagecount2){
				loadedwx();
			}
		}
	}
}

function loaded () {
	clearTimeout(t);
	 pagenumber++;						
			var url=basePath+"/ea/jinbi/sajax_AjxaHyjifenBill.jspa?";
			$.ajax({
				url : url,
				type : "get",
				async : true,
				dataType : "json",
				data:{
				  "user":user,
				  "sccid":sccid,
				  "year":year,
				  "month":month,
				  "pageNumber":pagenumber
				},
				success : function (data) {
					if(data==null){
						return false;
					}
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var jbtab="";	
					if(pageForm == null  ){
						
					}else{
					pagenumber = pageForm.pageNumber;
					pagecount = pageForm.pageCount;
					var jblist=pageForm.list;
					$(".last").removeClass("last");				
					for( var i = 0; i < jblist.length; i++){
						var jb=jblist[i];									
						var zj=toWeek(jb.jifenDetailDate.toString());
						var  str=jb.jifenDetailDate.toString();
							str =  str.replace(/-/g,"/");
							var sj=toDDMMMYYYY(new Date(str));
							var sh=toHHMISS(new Date(str));

							if(i==jblist.length-1){
							jbtab+="<ul class='con-bill_mil last' id='"+jb.jifenDetailId +"' onclick='details(\""+jb.jifenDetailId +"\",\""+jb.wfjGuizeCalc+"\")'><li><div align=center style='width:15%;'><span class='zj'>"+zj+"</span>";
							}else{
							jbtab+="<ul class='con-bill_mil' id='"+jb.jifenDetailId +"' onclick='details(\""+jb.jifenDetailId +"\",\""+jb.wfjGuizeCalc+"\")'><li><div align=center style='width:15%;'><span class='zj'>"+zj+"</span>";
							}
							
		                jbtab+="<p>"+sj +"</p></div>";
		                jbtab+="<div class='con-bill_head'>";
		                
		                if(jb.wfjGuizeId=="WfjGuize20151120R5HZ2UPCV50000000006"){
		                	jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/赠@2x.png' />";
		                }	
						if(jb.wfjGuizeId=="69CD270453D54AE4817274AC0D269B71"){
							jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/取@2x.png' />";
						}
						if(jb.wfjGuizeId=="WfjGuize201605152R4ASTNAP90000000001"){
							jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/本@2x.png' />";
						}
						if(jb.wfjGuizeId=="WfjGuize20160901AII9IY89ZX0000000001"){
							jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/充值@2x.png' />";
						}
						if(jb.wfjGuizeId=="WfjGuize20160726AHW88UNBRN0000000001"||jb.wfjGuizeId=="WfjGuize20160726AHW88UNBRN0000000002"){
							jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/包@2x.png' />";
						}
						if(jb.wfjGuizeId=='WfjGuize20161227Z4S665XKBB0000000005'){
							jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/惩罚icon@2x.png' />";
						}
						if(jb.wfjGuizeId=='WfjGuize201701113KR9JGB8MT0000000001'){
							jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/收回@2x.png' />";
						}
                        if(jb.wfjGuizeId=='WfjGuize201905072I849AXCPK0000000095'){
                            jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/gold.png' />";
                        }
						jbtab+="</div><div class='con-bill_txt'><h3>";
						if(jb.wfjGuizeCalc=="A"){
							jbtab+="+";
						}else if(jb.wfjGuizeCalc=="M"){
							jbtab+="-";
						}
						
						jbtab+=fmoney(jb.jifenDetailScore,2) +"</span></h3><p>"+jb.wfjGuizeName+"</p></div><div align=center style='width:15%;'><span class='sh'>"+sh+"</span></div></li></ul>";
						var jfdate=jb.jifenDetailDate;
		            	jfdate=jfdate.substring(0,19);	            	
        				var zj=toWeek(jfdate);
        				$("#"+"jb.jifenDetailId").find(".zj").text(zj);
					}
					$("div#jbtab").append(jbtab);
					
					$(".wfj01_019_con_bill").find("td").attr("style","font-size:" + $(window).height() * 0.02+ "px; border-bottom:"+ $(window).height() * 0.003+ "px solid #F0F0F0; height:"+ $(window).height() * 0.065+ "px;");								
					//$(".wfj01_019_con_type").attr("style","margin-top:" + $(window).height() * 0.02+ "px;");
					$(".wfj01_019_con_type").find("td").attr("style","height:" + $(window).height()* 0.05 + "px;font-size:"+ $(window).height()* 0.025+ "px; padding-left:"+ $(window).height() * 0.01+ "px;");
					$(".wfj01_019_con_type").find("input").attr("style","width:" + $(window).height() * 0.1+ "px;height:" + $(window).height()* 0.03 + "px;");
					$(".wfj01_019_con_type").find("#myChart").attr("style","margin-top:" + $(window).height() * 0.03+ "px;");
					
									
					$(".wfj01_019_date").attr("style","top:" + $(window).height() * 0.2 + "px;");
					$(".wfj01_019_date").find("li").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
					$(".wfj01_019_choice_date").attr("style"," height:" + $(window).height() * 0.06+ "px; line-height:"+ $(window).height() * 0.06+ "px; border-bottom:"+ $(window).height() * 0.008+ "px solid #4CACCE;font-size:"+ $(window).height() * 0.025+ "px;");
					$(".wfj01_019_choice_date").find("font").attr("style","padding-left:" + $(window).height()* 0.015 + "px;");
					$(".wfj01_019_date").find("li").find("div").attr("style","font-size:" + $(window).height() * 0.025+ "px;");
					
					var len = $(".wfj01_019_con_bill").find("tr").length;
					for ( var i = 0; i < len; i++) {
						if ($(".wfj01_019_con_bill").find("tr").eq(i).find(".wfjGuizeCalc").val() == "A") {
							$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#5584D3");
							$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
						} else {
							$(".wfj01_019_con_bill").find("tr").eq(i).find("td").eq(2).css("color","#EB4F38");
							$(".wfj01_019_con_bill").find("tr").eq(i+1).find("td").eq(0).css("color","#5584D3");
						}
					}
					getHeight();
					
				}
				},
				error:function(data){
					alert("获取单据失败");
				}
			}); 
		
	}

//微信商户
function loadedwx() {
	clearTimeout(tt);
	pagenumber2++;
	var url=basePath+"/ea/jinbi/sajax_getWxzhDetailList.jspa?da="+new Date();
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		data:{
			"user":user,
			"sccid":sccid,
			"year":year,
			"month":month,
			"pageNumber":pagenumber2
		},
		success : function (data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var jbtab="";
			if(pagenumber2==1) {
				xjtx2 = member.xjtx;
				jdetail2 = member.jdetail;
			}
			if(pageForm == null  ){

			}else{
				pagenumber2 = pageForm.pageNumber;
				pagecount2= pageForm.pageCount;
				var jblist=pageForm.list;

				$(".last2").removeClass("last2");
				for( var i = 0; i < jblist.length; i++){
					var jb=jblist[i];
					var zj=toWeek(jb.createDate.toString());
					var  str=jb.createDate.toString();
					str =  str.replace(/-/g,"/");
					var sj=toDDMMMYYYY(new Date(str));
                    var sh=toHHMISS(new Date(str));

					if(i==jblist.length-1){
						jbtab+="<ul class='con-bill_mil last2' id='"+jb.wxdId +"' onclick='details2(\""+jb.wxdId +"\",\""+jb.sztype+"\")'><li><div align=center style='width:15%;'><span class='zj'>"+zj+"</span>";
					}else{
						jbtab+="<ul class='con-bill_mil' id='"+jb.wxdId +"' onclick='details2(\""+jb.wxdId +"\",\""+jb.sztype+"\")'><li><div align=center style='width:15%;'><span class='zj'>"+zj+"</span>";
					}

					jbtab+="<p>"+sj +"</p></div>";
					jbtab+="<div class='con-bill_head'>";

					if(jb.sztype=="A"||jb.sztype=="D"){
						jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/本@2x.png' />";
					}else{

						jbtab+="<img src='"+basePath+"images/ea/finance/BenDis/取@2x.png' />";
					}

					jbtab+="</div><div class='con-bill_txt'><h3>";
					if(jb.sztype=="A"||jb.sztype=="D"){
						jbtab+="+";
					}else if(jb.sztype=="M"){
						jbtab+="-";
					}

					jbtab+=fmoney(jb.money,2) +"</span></h3><p>"+jb.type+"</p></div><div align=center style='width:15%;'><p style='font-size:1.2rem;'>"+jb.staffName+"</p><span class='sh'>"+sh+"</span></div></li></ul>";
					var jfdate=jb.createDate;
					jfdate=jfdate.substring(0,19);
					var zj=toWeek(jfdate);
					$("#"+"jb.wxdId").find(".zj").text(zj);


				}
				$("div#jbtab2").append(jbtab);

		
				getHeight2();

			}
		},
		error:function(data){
			alert("获取单据失败");
		}
	});

}
function details(detailid,wfjGuizeCalc){
	console.dirxml(detailid)
	open(basePath+"/ea/jinbi/ea_getdetails.jspa?"+object.join("")+"&detailid="+detailid+"&wfjGuizeCalc="+wfjGuizeCalc,"_self");
}


function details2(detailid,wfjGuizeCalc){
	console.dirxml(detailid)
	open(basePath+"/ea/jinbi/ea_getWxzhDetail.jspa?"+object.join("")+"&wxdId="+detailid+"&wfjGuizeCalc="+wfjGuizeCalc,"_self");
}
function imgPosition(phi,thi){
	return (phi-thi)/2;
}

function pickedFunc(){
	$("#year").text($dp.cal.getP('y'));
	$("#month").text($dp.cal.getP('M'));
	window.location.href=basePath+"/ea/jinbi/ea_gethyjifenBill.jspa?"+object.join("")+"&year="+$dp.cal.getP('y')+"&month="+$dp.cal.getP('M')+"&user="+user+"&sccid="+sccid+"&ccompanyId="+ccompanyId;
}
