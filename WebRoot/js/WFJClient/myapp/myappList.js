//地球数字商城详情
function Mall(obj){
	var ppid=$(obj).find("#ppid").val();
	var goodsid=$(obj).find("#goodsid").val();
	var companyid=$(obj).find("#companyid").val();
	var ccompanyid=$(obj).find("#ccompanyid").val();
	var url = basePath + "ea/wfjshop/ea_doodsDetail.jspa?ppid=" + ppid + "&goodsid=" + goodsid + "&companyId=" + companyid + "&ccompanyId=" + ccompanyid;
	document.location.href = url;
}
//地区查看跟多
function diqu(obj){
	var ppid=$(obj).find("#idd").val();
	var url=basePath+"ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid="+ppid+"&type=summary";
	document.location.href = url;
}
function Expo(obj){
	
	var title = $(obj).find("#idu").text();
	var url =null;
	if(title=="汽车驾校"){
		url=basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType=汽车交通工具/"+title;
	}else if(title=="台式机"){
		url=basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType=软件网络计算机/"+title;
	}else if(title=="卧室家具"){
		url=basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType=家具家居装饰/"+title;
	}else if(title=="休闲装"){
		url=basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType=服饰皮革纺织/"+title;
	}else if(title=="能源五金"){
		url=basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType=家电五金日用品/"+title;
	}else if(title=="西餐厅"){
		url=basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType=旅游酒店餐饮/西餐厅";
	}
	document.location.href = url;
}

$(document).ready(function(e) {
	//查看新闻详情
	$(document).on("click",".new_box",function() {
		var types=$(this).find("#types").val();
		var goodsid = $(this).attr("id");
		var ppid = $(this).find("#ppid").val();
		var companyId = $(this).find("#companyId").val();
		var ccompanyId = $(this).find("#ccompanyId").val();
		if(types=="会员分享"){
			document.location.href = basePath + "/ea/industry/ea_informationDetails.jspa?ppId="+ppid+"&ccompanyId="+ccompanyId+"&type=time&miniSystemJudge=03";
					
		}else if(types=="新闻"){
			var share = $(this).find(".share").text();
			document.location.href = basePath
					+ "ea/wfjshop/ea_getWFJnews.jspa?ccompanyId="+ccompanyId+"&search="+search+"&goodsid=" + goodsid;
		}
		
	});
	
	var urL = basePath + "ea/industry/sajax_ea_getExpo.jspa";
	$.ajax({
		url : encodeURI(urL),
		type : "get",
		async : false,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var page = member.productList;
			var str = "";
			for(var i=0;i<page.length;i++){
				var obj = page[i];
			
			str +="<li class='col-lg-2 col-md-2 col-sm-4 col-xs-4 zlzs'>";
			str +="<div onclick='Expo(this)'><h5 id='idu'>"+obj[0]+"</h5>";
			str +="<p>专业平台服务</p>";
			str +="<img src="+basePath+obj[1]+">";
			str +="</div>";
			str +="</li>";
			}
			
			$("#Expo").append(str);

		},
		error : function(data) {
		}
	});

	var vurl = basePath + "ea/lottery/sajax_ea_vipActivityList.jspa"
    $.ajax({
        url : encodeURI(vurl),
        type : "get",
        async : false,
        dataType : "json",
        success : function (data) {
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = new Array();
            if (pageForm != null && pageForm.recordCount> 0)
            {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for (var i = 0;i < 3;i++)
                {
                    var entity = list[i];
                    str.push('<a href='+basePath+'/ea/wfjshop/ea_doodsDetail.jspa?ppid='+entity[0]+'&goodsid='+entity[1]+'&companyId='+entity[2]+'>');
                    str.push('<li><img src='+basePath+entity[4]+'><p>'+entity[3]+'</p></li>');
                }
                $("#vip").append(str.join(""));
            }else {
                $("#vip").append('<li><p>暂无活动</p>></li>>');
            }
        }
    });

	//活动
	var reurl = basePath + "ea/lottery/sajax_ea_allActivityList.jspa?flag=1"
    $.ajax({
        url : encodeURI(reurl),
        type : "get",
        async : false,
        dataType : "json",
        success : function (data) {
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = new Array();
            if (pageForm != null && pageForm.recordCount> 0)
            {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for (var i = 0;i < list.length;i++)
                {
                    var entity = list[i];
                    str.push('<a href='+basePath+'/ea/industry/ea_getCompanyHome.jspa?typeback=typeback&ccompanyId='+entity[0]+' >');
                        str.push('<li><img src='+basePath+entity[2]+'><p>'+entity[3]+'</p></li></a>');
                }
                $("#signlist").append(str.join(""));
            }else {
                $("#signlist").append('<li></li><li style="height:30px"><span style="font-size: 14px">暂无积分活动</span></li><li></li>')
            }
        }
    });

    //活动
    var reurl = basePath + "ea/lottery/sajax_ea_allActivityList.jspa?flag=0"
    $.ajax({
        url : encodeURI(reurl),
        type : "get",
        async : false,
        dataType : "json",
        success : function (data) {
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = new Array();
            if (pageForm != null && pageForm.recordCount> 0)
            {
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var list = pageForm.list;
                $(".last").removeClass("last");
                for (var i = 0;i < list.length;i++)
                {
                    var entity = list[i];
                    str.push('<a href='+basePath+'/ea/industry/ea_getCompanyHome.jspa?typeback=typeback&ccompanyId='+entity[0]+' >');
                    str.push('<li><img src='+basePath+entity[2]+'><p>'+entity[3]+'</p></li></a>');
                }
                $("#relist").append(str.join(""));
            }else {
                $("#relist").append('<li></li><li style="height:30px"><span style="font-size: 14px">暂无抽奖活动</span></li><li></li>')
            }
        }
    });

	//新闻的展示
	var url = basePath+"ea/wfjshop/sajax_ea_AjaxNewsList.jspa";
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : false,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var page=member.pageForm;
			var sp=page.list;
			var newstr="";
			var i=0;
			var len =3;
			for(i;i<len;i++){
				var news=sp[i];
				if(news[1].hours<10){
					news[1].hours = "0"+news[1].hours;
				}
				if(news[1].minutes<10){
					news[1].minutes = "0"+news[1].minutes;
				}
				var time = news[1].hours+":"+news[1].minutes;
				if(news[1].month < 9){
					if(news[1].date<10){
						var date=(news[1].year+1900)+"-0"+(news[1].month+1)+"-0"+news[1].date;
					}else{
						var date=(news[1].year+1900)+"-0"+(news[1].month+1)+"-"+news[1].date;
					}
				}else{
					var date=(news[1].year+1900)+"-"+(news[1].month+1)+"-"+news[1].date;
				}
				var name = news[9]==null ? "":news[9];
				if(news[2]!=null){
					var t = news[2].split(".")[0]+"small."+news[2].split(".")[1];
					newstr+= "<a href='###' class='new_box clearfix' id='"+news[3]+"'>";
					newstr+= "<input type='hidden' id='ppid' value='"+news[5]+"' />";
					newstr+= "<input type='hidden' id='types' value='"+news[6]+"' />";
					newstr+= "<input type='hidden' id='companyId' value='"+news[7]+"' />";
					newstr+= "<input type='hidden' id='ccompanyId' value='"+news[8]+"' />";
		            newstr+= "<img src='"+basePath+t+"' class='new_img' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>";
		            newstr+= "<div class='new_text'>";
		            newstr+= "<div class='new_tit' style='text-align:left;'>"+news[0]+"</div>";
		            newstr+= "<div class='new_state'><span class='new_name'>"+name+"</span>";
		            newstr+= "<span class='new_time' data-date='"+date+"' data-time='"+time+"'></span>";
		            newstr+= "</div></div></a>";
				}
			}
			$("#uls").append(newstr);
			
			 //当前时间
            var curDate=getNowFormatDate();
            $(".new_time").each(function(){
                var d=$(this).attr("data-date");
                var t=$(this).attr("data-time");
                if(curDate==d){
                    $(this).text(t);
                }else{
                    $(this).text(d);
                }
            });
		         //获取当前时间（格式：2017-01-23）
            function getNowFormatDate() {
                var day = new Date();
                var Year = 0;
                var Month = 0;
                var Day = 0;
                var CurrentDate = "";
                //初始化时间
                Year = day.getFullYear(); 
                Month = day.getMonth() + 1;
                Day = day.getDate();
                //Hour = day.getHours();
                // Minute = day.getMinutes();
                // Second = day.getSeconds();
                CurrentDate += Year + "-";
                if (Month >= 10) {
                    CurrentDate += Month + "-";
                } else {
                    CurrentDate += "0" + Month + "-";
                }
                if (Day >= 10) {
                    CurrentDate += Day;
                } else {
                    CurrentDate += "0" + Day;
                }
                return CurrentDate;
            }
		},
		error : function(data) {
			
		}
	});


    //地球数字商城
    var urL=basePath+"ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";
    $.ajax({
        url : encodeURI(urL),
        type : "get",
        async : false,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var page=member.pageForm;
            var perlist=page.list;
            var str="";
            var i=0;
            var len =4;
            for(i;i<len;i++){
                var obj=perlist[i];
                str +="<li id='news'><a onclick='Mall(this)'><img src="+basePath+obj[4]+">";
                str+="<input type='hidden' value="+obj[1]+" id='ppid'/>";
                str+="<input type='hidden' value="+obj[5]+" id='goodsid'/>";
                str+="<input type='hidden' value="+obj[6]+" id='companyid'/>";
                str+="<input type='hidden' value="+obj[10]+" id='ccompanyid'/>";
                str +="<div class='text'>";
                str +="<h5>"+obj[0]+"</h5>";
                str +="<div class='bottom'>";
                str +="<span class='qian'>&yen;<b>"+obj[2]+"</b></span>";
                //str +="<span class='mai'>"+obj[8]+"321人购买</span>";
                str +="</div></div></a></li>";
            }


            $("#myMall").append(str);
        },
        error : function(data) {

        }
    });


    //中联园招商
	var urt = basePath+"ea/wfjplatform/sajax_ea_getPlatform.jspa?type=getPlatform&ajax=myajax";
	$.ajax({
		url : encodeURI(urt),
		type : "get",
		async : false,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var list=member.list;
			var address = member.address;
			var str=new Array();
			for(var i=0; i < list.length; i++){
				var CCode = list[i];
                str.push('<a href='+basePath+'ea/wfjplatform/ea_getPlatform.jspa?type=qurey&content='+CCode.codeValue+'&addre='+address+'><li><span>'+CCode.codeValue.substring(0,1)+'</span><p>'+CCode.codeValue+'经济平台</p></li></a>');
			}

            str.push('<a href='+basePath+'ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany"><li><span>公</span><p>公司申请加入经济平台</p></li></a>');
            str.push('<a href='+basePath+'ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews="><li><span>个</span><p>个人申请加入经济平台</p></li></a>');


            for(var j = 0; j < list.length; j++){
                var CCode = list[j];
                str.push('<a href='+basePath+'ea/wfjplatform/ea_getPlatform.jspa?type=qureyy&content='+CCode.codeValue+'&addre='+address+' ><li><span>'+CCode.codeValue.substring(0,1)+'</span><p>产品'+CCode.codeValue.substring(0,2)+'代理商</p></li></a>');
            }
		
			$("#investment").append(str);
		},
		error : function(data) {
				
		
		}

	});





	//地区
	var urL=basePath+"ea/wfjplatform/sajax_ea_AjaxCity.jspa?ppid=";	
	$.ajax({
		url : encodeURI(urL),
		type : "get",
		async : false,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var page=member.list;
			var str="";
			var i=0;
			var len =5;
			for(i; i <=len; i++){
				var obj=page[i];
				if(obj) {
                    str += '<li onclick="diqu(this)" id=' + obj[1] + '><div class="xianyu">';
                    str += '<input type="hidden" id="idd" value="' + obj[1] + '">';
                    str += '<div class="mydiv">' + '</div><p>' + obj[4] + '</p><h5>' + obj[0] + '</h5></div></li>';
                }
				}
				$(".xianyu_n").append(str);
		},
		error : function(data) {
			
		}
	});
	$(".xianyu_n li").each(function(){
        var string = "#FF501A,#68B83A,#78CF7F,#53BBCB,#ED837B,#C5E6F9,#1F75BB,#B41717";   
        var array = string.split(",");          
        var col = array[Math.round(Math.random()*(array.length-1))]; 
        $(this).find("div.mydiv").attr("style","border-color:"+col+";color:"+col+";height: 0.5rem;");
        $(this).find("p").attr("style","color:"+col+";border-color:"+col);
    });
	
});
function addbus(obj){
	var url=basePath+obj;
	document.location.href = url;
}