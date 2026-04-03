$(function(){


    if(document.documentElement.clientWidth >= 1366){
        document.getElementById('sdate').onfocus = function(){date(this)};
        document.getElementById('edate').onfocus = function(){date(this)};

    }else {
        $('#sdate').date();
        $('#edate').date();
    }
    $("#sdate").click(function () {
        $("iframe").parent().css({"top":"745px","left": "3032px"});
    })
    $("#edate").click(function () {
        $("iframe").parent().css({"top":"745px","left": "3032px"});
    })
    if (document.documentElement.clientWidth >= 1080){
        $("#monthwrapper div").css("opacity","0");
        $(".Recharge .box").css("height",$(window).height()-$("header ul li").height()-$(".Recharge .top").height()-$(".Recharge .time").height()-$(".Recharge .bottom").height()+"px");
        $(".content").css("margin-top",$("header ul li").height())
        $(".content").css("height",$(window).height()-$("header ul li").height())
    } if (document.documentElement.clientWidth >= 1366) {
        $(".Recharge .box").css("height",$(window).height()/0.35-$("header ul li").height()-$(".Recharge .top").height()-$(".Recharge .time").height()-$(".Recharge .bottom").height()+"px");
        $(".content").css("margin-top",$("header ul li").height())
        $(".content").css("height","100%")
    }else {
        $(".Recharge .box").css("height",$(window).height()-$("header ul li").height()-$(".Recharge .top").height()-$(".Recharge .time").height()-$(".Recharge .bottom").height()+"px");
        $(".content").css("margin-top",$("header ul li").height())
        $(".content").css("height",$(window).height()-$("header ul li").height())
    }

        /*选择收入或支出*/
    $(".Recharge .top li").click(function () {
    	if($(this).hasClass("active")){
            $(this).removeClass("active")
			inAndExp = "";
		}else {
            $(this).addClass("active").siblings().removeClass("active");
            if($(this).attr("id") == "income"){
                inAndExp = "A"
			}else if($(this).attr("id") == "expend"){
                inAndExp = "M"
			}
		}
        pagenumber = 0;
		$(".box").empty();
        loaded();
    });

    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth();
    var day = myDate.getDate();
    $("#sdate").val(year+"-"+(month+1)+"-"+day);
    $("#edate").val(year+"-"+(month+1)+"-"+day);
	/*选择时间按钮*/
    $(".Recharge .time li").click(function () {
        // if($(this).hasClass("active")){
         //    $(this).removeClass("active")
		// }else {
            $(this).addClass("active").siblings().removeClass("active");
		// }

        var lastDay = new Date(year, month + 1, 0);
        var lastDays = new Date(year, month, 0);
		var time =  $(".Recharge .time .active").text();
		switch (time){
			case "今天":
				$("#sdate").val(year+"-"+(month+1)+"-"+day);
                $("#edate").val(year+"-"+(month+1)+"-"+day);
				break;
            case "昨天":
                $("#sdate").val(year+"-"+(month+1)+"-"+(day-1));
                $("#edate").val(year+"-"+(month+1)+"-"+(day-1));
                break;
            case "本月":
                $("#sdate").val(year+"-"+ (month+1)+"-1");
                $("#edate").val(year+"-"+ (month+1)+"-"+ lastDay.getDate());
                break;
            case "上月":
                $("#sdate").val(year+"-"+ month+"-1");
                $("#edate").val(year+"-"+ month+"-"+ lastDays.getDate());
                break;
            case "本年":
                $("#sdate").val(year+"-1-1");
                $("#edate").val(year+"-12-31");
                break;
            default:
                $("#sdate").val("");
                $("#edate").val("");
                break;
		}
        pagenumber = 0;
        $(".box").empty();
        loaded();
    });
	/*返回顶部*/
    $("#top").click(function () {
        $(".Recharge .box").animate({scrollTop:0},300);
    });

    //总积分隐藏显示
    $(".Recharge .box").bind("scroll", function () {
        var sTop = $(".Recharge .box").scrollTop();
        var sTop = parseInt(sTop);
        var box_h = $(".Recharge .box").height();
        if (sTop >= box_h/4) {
            $("#top").show();
            $(".Recharge .z-score").hide();
            $(".Recharge .bottom").show();
        }
        else {
            $("#top").hide();
            $(".Recharge .z-score").show();
            $(".Recharge .bottom").hide();
        }
    });
    //筛选界面
    $("#btn_screen").click(function(){
        $("#section_screen").show();
        $("#section_screen").css("left","0px")
//		$("#section_screen").animate({left:'0px'});
    })
    //右滑关闭
//    $("#section_screen").touchwipe({
//        min_move_x : 30, // 灵敏度
//        wipeRight : function() {
//            var left_wid=$("#section_screen").width();
//            $("#section_screen").css("left",left_wid)
//			$("#section_screen").animate({left:left_wid},function(){
//				$("#section_screen").hide();
//			});
//        }
//    });
    //点击确定关闭
    $("#section_screen footer input").click(function(){
        pagenumber = 0;
        $(".box").empty();
        loaded();
        var left_wid=$("#section_screen").width();
        $("#section_screen").css("left",left_wid)
    })
    //右侧关闭
    $(".content").click(function(e){
        e.stopPropagation();
    })
    $("#section_screen").not(".content").click(function(){
        var left_wid=$("#section_screen").width();
        $("#section_screen").css("left",left_wid)
    })
    //选择时间

    typeList();
    operatorList()
    //下拉
    $(".input_select").click(function(e) {
        $(".dropdown ul").slideUp("fast");
        var ul = $(this).parent().find("ul");
        if(ul.css("display") == "none") {
            ul.slideDown("fast");
        } else {
            ul.slideUp("fast");
        }
        e.stopPropagation();
    });
    $(".content ul li:last-of-type section input").click(function() {
        $("#yearwrapper").css("overflow","auto");
        $("#monthwrapper").css("overflow","auto");
        $("#daywrapper").css("overflow","auto");
    });


    $(".dropdown ul li a").click(function() {
        var txt = $(this).text();
        var id = $(this).attr("id");
        $(this).parents(".dropdown").find("input").val(txt);
        $(this).parents(".dropdown").find("span").text(id==undefined?"":id);
        if($(this).parents(".dropdown").find("span").attr("id")=="type") {
            if (id == 'WfjGuize20170318Y4QJHU3G6F0000000073') {
                $(".oper").show();
                $(".print").show();

            } else {
                $(".oper").find("input").val("");
                $(".oper").find("span").text("");
                $(".oper").hide();
                $(".print").hide();
            }
        }
        $(".dropdown ul").hide();
    });
    $("html").not(".dropdown,.input_select").click(function(){
        $(".dropdown ul").hide();
    })

	 loaded();

    $(document).on("click",".box li",function(){
        window.location.href = basePath+"page/ea/main/finance/BenDis/Gold_Pool/integral_detail.jsp?sccId="+sccid+"&"+$(this).find("form").serialize();
    });

    $(document).on("click",".topup",function(){
        window.location.href = basePath+"ea/sm/ea_integralTopup.jspa?sccId="+sccid+"&staffName="+staffName+"&posNum="+posNum+"&ccompanyID="+ccompanyID;
    });

    $(document).on("click",".print",function(){
        var url = basePath+"/ea/bonuspoints/sajax_printMsg.jspa?";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "pageNumber": pagenumber,
                "sccid": sccid,
                "type": $("#type").text(),
                "operator": $("#operator").text(),
                "sdate": $("#sdate").val(),
                "edate": $("#edate").val(),
                "inAndExp": inAndExp,
                "staffid": staffid,
                "schType":"all"
            },
            success: function (data) {
                var u = window.navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                try {
                    if (isAndroid == true) {
                        Android.androidPrintIntegralList(data+"");//调用安卓接口
                    }else if (isiOS == true) {
                        var url= "func=" + 'iosSetAPAuth';
                        params={'data':data};
                        for(var i in params){
                            url = url + "&" + i + "=" + params[i];
                            console.log(url);
                        }
                        window.webkit.messageHandlers.Native.postMessage(url);
                    }else {
                        var member = eval("("+data+")");
                        var list = member.list;
                        var sumMoney = member.sumMoney;
                        var name = member.name;
                        var date = new Date();
                        var year = date.getFullYear();
                        var month = date.getMonth()+1;
                        var day = date.getDate();
                        var hour = date.getHours();
                        var minute = date.getMinutes();
                        var second = date.getSeconds();
                        LODOP=getLodop();
                        LODOP.PRINT_INIT("积分充值");
                        LODOP.SET_PRINT_PAGESIZE(3,500,0.2,"");
                        LODOP.ADD_PRINT_TEXT(20,60,200,30,"充值记录");
                        LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
                        LODOP.ADD_PRINT_TEXT(60,20,40,20,"时间");
                        LODOP.ADD_PRINT_TEXT(60,90,40,20,"金额");
                        LODOP.ADD_PRINT_TEXT(60,140,50,20,"充值人");
                        var i = 0;
                        for (i;i<list.length;i++){
                            LODOP.SET_PRINT_STYLE("FontSize",8);
                            LODOP.ADD_PRINT_TEXT(60+30*(i+1),20,100,20,list[i][0]);
                            LODOP.ADD_PRINT_TEXT(60+30*(i+1),90,150,20,list[i][1]);
                            LODOP.ADD_PRINT_TEXT(60+30*(i+1),140,150,20,list[i][2]);
                        }
                        LODOP.ADD_PRINT_TEXT(60+30*(i+1),70,125,20,"总金额:"+sumMoney);
                        LODOP.ADD_PRINT_TEXT(60+30*(i+2),20,190,20,"打印人:"+name);
                        LODOP.ADD_PRINT_TEXT(60+30*(i+2.5),20,190,20,"打印时间:"+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
                        LODOP.ADD_PRINT_TEXT(60+30*(i+7),70,250,50,".");
                        LODOP.PRINT();
                    }
                }catch(e){
                    // window.history.go(-1);return false;
                }
            }
        })
    });

     $(window).load(function(){
         var h = document.documentElement.clientHeight-$(".com_head").outerHeight()-$(".hb_top").outerHeight()-$(".hb_warn").outerHeight()-$(".hb_th").outerHeight()-16;
         $("#jifen").css({
        	 "overflow-y": "scroll",
 			"height":''+h+'px' ,
 			"padding":0
         })
     })
	
	
	
	// $("#goRecharge").click(function(){
	//
	// 	object.push("account=",user);
	// 	object.push("&sccid=",sccid);
	// 	object.push("&staffid=",staffid);
	// 	object.push("&identifying=",identifying);
	// 	object.push("khd=",khd);
	// 	object.push("&flag=",flag);
	// 	object.push("&ccompanyId=",ccompanyId);
	//
	//
	// 	window.location.href = basePath+"/ea/bonuspoints/ea_getbpDetail.jspa?"+object.join("")+"&charge=charge";
	// });
	
	
});

function getHeight(){
	
	t = setTimeout("getHeight()",200);
	if($(".last").length>0){
  		 if($(".last").offset().top+$(".last").height()-($(".header").height()+$(".top").height()+$(".time").height()+$(".z-score").height())*0.1<$(window).height()){
  			 if(pagenumber<pagecount){
                 pagenumber++;
  				 loaded();
  			 }
  		}		 
  	 }		
}

function loaded(){
	
	clearTimeout(t);
 	
 	var url = basePath+"/ea/bonuspoints/sajax_DetailList.jspa?";
 	$.ajax({
 		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"pageNumber":pagenumber,								
			"sccid":sccid,
			"type":$("#type").text(),
			"operator":$("#operator").text(),
			"sdate":$("#sdate").val(),
            "edate":$("#edate").val(),
            "inAndExp":inAndExp,
            "staffid":staffid
		},
		success : function(data){
			var map = eval("("+data+")");
			var pageForm = map.pageForm;
			var str = new Array();
			
			if(pageForm != null&&pageForm.recordCount>0)
			{
				$(".last").removeClass("last");
				var list = pageForm.list;
				pagenumber = pageForm.pageNumber;
				pagecount = pageForm.pageCount;
				for(var i = 0; i<list.length;i++)
				{
					var exp = list[i];
					var time = exp[2].match(/(\S*) /)[1].split("-");
                    if(i==list.length-1)
                    {
                        str.push('<li class="last" >');
                    }else
                    {
                        str.push('<li>');
                    }
                    str.push("<form>");
                    str.push("<input type='hidden' name='pdName' value='"+exp[0]+"' /> ");
                    str.push("<input type='hidden' name='rules' value='"+exp[7]+"' /> ");
                    str.push("<input type='hidden' name='time' value='"+exp[2]+"' /> ");
                    str.push("<input type='hidden' name='inAndExp' value='"+exp[4]+"' /> ");
                    str.push("<input type='hidden' name='operator' value='"+exp[6]+"' /> ");
                    str.push("<input type='hidden' name='journalNum' value='"+exp[8]+"' /> ");
                    str.push("<input type='hidden' name='integral' value='"+exp[1].toFixed(2)+"' /> ");
                    str.push("</form>");
                    str.push("<span class='left' style='display: none;'>"+exp[7]+"</span>");
                    str.push("<div class='left'>");
                    str.push("<p>"+time[0]+"</p><p>"+time[1]+"-"+time[2]+"</p>");
                    str.push("</div><div class='lie'>");
                    str.push("<img src='"+basePath+exp[5]+"'>");
                    str.push("<div class='txt'><p>"+(exp[4]=="M"?"-":"+")+(exp[1]/100).toFixed(4)+" /<span>积分"+exp[1].toFixed(2)+"</span></p>");
                    str.push("<h5>"+exp[0]+"</h5></div></div><p class='name'>"+exp[6]+"</p></li>");
				}	
				$(".box").append(str.join(""));
				getHeight();
			}else {
                $(".box").append("<div style='margin-top: 4rem;font-size: 1.5rem;color: #6d6464;text-align: center;'><span>暂无数据！</span></div>");
            }
						
		}	
 	});	
}

function typeList() {
    $.ajax({
        url:basePath+"/ea/bonuspoints/sajax_typeList.jspa",
        type:"get",
        dataType:"json",
        async:false,
        success:function (data) {
            var member = eval("("+data+")");
            var list = member.ruleList;
            var str = new Array();
            for (var i = 0;i<list.length;i++ ){
                str.push("<li><a href='javascript:void(0);' rel='' id='"+list[i][0]+"'>"+list[i][1]+"</a></li>")
            }
            $(".type").append(str);
        }
    })
}

function operatorList() {
    $.ajax({
        url:basePath+"/ea/bonuspoints/sajax_operatorList.jspa",
        type:"get",
        dataType:"json",
        async:false,
        success:function (data) {
            var member = eval("("+data+")");
            var list = member.operatorList;
            var str = new Array();
            for (var i = 0;i<list.length;i++ ){
                str.push("<li><a href='javascript:void(0);' rel='' id='"+list[i][0]+"'>"+list[i][1]+"</a></li>")
            }
            $(".operator").append(str);
        }
    })
}