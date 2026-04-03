let classify=''
$(document).ready(function(){
    ClassifyComponent.init({
        callImpl: function (item) {
            if (item.level>4){
                classify=item.name
                hidewin()
                let notNull = document.getElementById("hangyelb1");
                let text = document.getElementById("hangyelb");

                text.innerText=classify
                notNull.value=classify
                console.log("elementsByName", elementsByName);

            }else {
                console.log("其他", item.name);

            }

        }
    });



	$(".che").attr("style","height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.025+"px;");
	$(".che option").attr("style","height:"+$(window).height()*0.03+"px; font-size:"+$(window).height()*0.025+"px;");
	
    $("body").css("height",$(window).height());
	//修改字体大小
	$("#tops").find("li").attr("style","float:left;");
	$("#tops").find("li").eq(0).attr("style","width:15%;position:relative;");
    $("#tops").find("li").eq(0).find("img").attr("style","margin-top:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
	$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
	$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;position:relative;top:"+$(window).height()*0.0234+"px;");
	$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
		
		
	$(".wfj11_015_top").css("height",$(window).height()*0.08+"px");
	$(".wfj11_015_top").css("lineHeight",$(window).height()*0.08+"px");

	$(".wfj11_015_consignee").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.03+"px;");
	$(".wfj11_015_consignee").attr("style","padding:"+$(window).height()*0.02+"px 0; margin-bottom:"+$(window).height()*0.01+"px;");
	$(".wfj11_015_consignee").css("position","relative")
	if($(window).width()=="320"){
		$(".wfj11_015_consignee img.left").css({"width":"20px"})			
	}else if($(window).width()>="375"){
		$("#FontScroll p img").css({"width":"60%"})
	}
	$(".img_ico").css({"height":$(".wfj11_015_consignee .wfj11_015_width").height()+"px","top":$(window).height()*0.02+"px"})
	$(".img_ico>p").css({"height":$(".img_ico").height()+"px"})
	$(".img_ico1").css("left",$(window).height()*0.02+"px")
	$(".wfj11_015_com").attr("style"," padding:"+$(window).height()*0.015+"px 0;");
	$(".wfj11_015_com").find("a").attr("style","padding:"+$(window).height()*0.005+"px "+$(window).height()*0.01+"px; border-radius:"+$(window).height()*0.005+"px; font-size:"+$(window).height()*0.02+"px;");
	$(".wfj11_015_proinfo").attr("style"," padding:"+$(window).height()*0.01+"px 0;");
	$(".wfj11_015_proinfo").find("td").attr("style"," font-size:"+$(window).height()*0.03+"px;line-height:"+$(window).height()*0.03+"px;position: relative;");
	$(".wfj11_015_delivery").find("td").attr("style"," height:"+$(window).height()*0.075+"px; line-height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.026+"px;font-weight:600");
	$(".wfj11_015_delivery").find("tr").eq(1).find("span").attr("style","padding-left:"+$(window).height()*0.01+"px;");
	$(".wfj11_015_delivery").find("tr").eq(1).find("span").find("img").attr("style","width:"+$(window).height()*0.01+"px;");
	$(".wfj11_015_delivery").find("tr").eq(2).find("span").attr("style","padding-left:"+$(window).height()*0.01+"px;");
	$(".wfj11_015_delivery").find("tr").eq(2).find("span").find("img").attr("style","width:"+$(window).height()*0.01+"px;");
	$(".wfj11_015_delivery").find("input").attr("style"," height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.026+"px;");
	$(".wfj11_015_bottom").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px; font-size:"+$(window).height()*0.025+"px;");
	$(".wfj11_015_bottom").find("li").attr("style"," font-size:"+$(window).height()*0.02+"px;");
	$(".wfj11_015_bottom").find("li").find("span").attr("style"," font-size:"+$(window).height()*0.03+"px;");
	$(".wfj11_015_bottom").find("li").find("a").eq(0).attr("style"," font-size:"+$(window).height()*0.03+"px;");
	$(".wfj11_015_bottom").find("li").find("a").eq(1).attr("style"," font-size:"+$(window).height()*0.025+"px;");
	
	$(".wfj11_015_need").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
	$(".wfj11_015_need").find("li").attr("style","font-size:"+$(window).height()*0.03+"px;color:#000;");
	$(".wfj11_015_need").find("li").find("span").attr("style","font-size:"+$(window).height()*0.03+"px;color:#F74C31;");
	$(".wfj11_015_allbay").find("td").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.06+"px;");
	$(".wfj11_015_allbay").find("td").eq(0).css("height",$(window).height()*0.05+"px")
	$(".wfj11_015_allbay").find("tr").eq(5).css("height",$(window).height()*0.1+"px")
	$(".wfj11_015_allbay").find("td").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto");
	$(".wfj11_015_allbay").find("td").find(".all_pay").attr("style","height:"+$(window).height()*0.04+"px;");
	$(".wfj11_015_allbay").find("td").find("#paycommit").attr("style"," width:50%; background-color:#F74C31; color:#FFF; cursor:pointer; border-radius:"+$(window).height()*0.006+"px; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
    $(".tanchu").attr("style","width:100%;height:"+$(window).height()*0.93+"px;position: fixed;top:4%;display:none;background:#FFF;");
	
	$(".wfj11_015").attr("style","height:"+($(window).height()-$(".wfj11_015_bottom").height())+"px;");
	$(".wfj11_015_body").attr("style","width:"+$(window).width()+";height:"+($(window).height()-$(".wfj11_015_bottom").height()-
				$(".wfj11_015_top").height())+"px;overflow:hidden;")
	$(".wfj11_015_hidden").attr("style","width:"+($(window).width())+"px;height:"+
			($(window).height()-$(".wfj11_015_bottom").height()-$(".wfj11_015_top").height())+"px;overflow:auto;");

	

	/*积分抵用 2017.4.19  后台改的*/
    // $(".integral h3").attr("style","font-size:"+$(window).height()*0.02+"px;");
    // $(".integral .slide_btn").attr("style","width:"+$(window).height()*0.07+"px;height:"+$(window).height()*0.035+"px;margin-top:-"+$(window).height()*0.0175+"px;");
    // $(".integral .slide_btn p").attr("style","width:"+$(window).height()*0.035+"px;height:"+$(window).height()*0.035+"px;");
    // $(".integral .slide_btn").click(function(){
    //     $(this).toggleClass("on");
    //     if(!$(".slide_btn").hasClass("on")){
    //         $("#sapnqian").html("￥"+jineshu);
    //         goumaitype="jinqian";
    //     }else{
    //         $("#sapnqian").html("￥"+0);
    //         goumaitype="jifen";
    //     }
    // });
	
	
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true, 
		overlay : 20
	}).jqmAddClose('.close');
	if(producttype=="公司会员"||producttype=="个人会员"||$("#goodsName").val()=="省级代理"||$("#goodsName").val()=="县级代理"||$("#goodsName").val()=="村级代理"){
		$("#shuliag").attr("readonly",true);
	}else{
		$("#shuliag").attr("readonly",false);
	}
	$("input#pwdzch").click(function(){
		if( $(this).val()!=""){
			$("label#pwdPlaceholder").hide();
		}else{
			$("label#pwdPlaceholder").show();
		}
	});
	$("input#pwdzch").blur(function(){
		if( $(this).val()!=""){
			$("label#pwdPlaceholder").hide();
		}else{
			$("label#pwdPlaceholder").show();
		}
	});
	$("input#pwdzch").keyup(function(){
		if( $(this).val()!=""){
			$("label#pwdPlaceholder").hide();
		}else{
			$("label#pwdPlaceholder").show();
		}
	});

	//确认订单
	$("#commit").click(function() {
        var shuliang=$("#shuliag").val();
        if(shuliang==""||shuliang=="0"){
            alert("数量必须>0");
            return;
        }

        //xgb
        if ($(".carID").val() == "") {
            alert("请选择车辆");
            return false;
        }
        if ($("#addressDetailed").val() == ""&&(noviceAddress==""||noviceAddress==null)) {
            alert("请添加收货地址");
            return false;
        }
        if (producttype == "公司会员") {
            var bool = true;
            $(".notnull").each(function () {
                if ($(this).val() == "") {
                    console.log($(this).val())

                    bool = false;
                }

            });
            if (!bool) {
                alert("会员信息均需填写");
                return false;
            }
            var re1 = checkidentifer($("#companyIdentifier").val(), $("#companyName").val(), "name");
            if (re1 == 1) {
                alert("该公司已注册过网站");
                return false;
            }

        }else if(producttype=="包天计时"){
            var  carNumber = $(".che option:selected").text();
            var ppid = $(".wfj11_015_delivery .ppid").val();
            if(!checkbday(carNumber,ppid)){
                  alert("已购买过包天可以明天再买")
                return false;
            }
        }

        //促销品id ljc
        var ptppid = "";
        var ptstandard = "";
        $(".con-ord_grd").each(function () {
            var temp = $(this).find("#ptppid").val();
            var pt = $(this).find("h3").text();
            ptppid += temp + ",";
            ptstandard += (pt == "" ? "无" : pt) + ",,";
        });
        $("input[name='ptppid']").val(ptppid);
        $("input[name='ptstandard']").val(ptstandard);
        //公司id
        var companyIds = ""
        var ptmorre = 0;
        var list = new Array();
        $(".companyId").each(function () {
            var temp = $(this).val();
            if ($.inArray(temp, list) == -1) {
                list.push(temp);
                companyIds += temp + ",";
            }
        });
        if ($(".companyId").length > 1 && list.length == 1) {
            $(".con-ord_grd").each(function () {
                ptmorre += parseFloat($(this).find("#price_item_2").find("span").text());
            });
        }
        $("input[name='companyId']").val(companyIds);
        $("input[name='ptmorre']").val(ptmorre);
         if($("#novice").length>0) {
             if (tradename == "B102汽车交通工具>z30001汽车驾校") {
                 var novice = true;
                 $(".isNotNull").each(function () {
                     if ($(this).val() == "") {
                         novice = false;
                     }
                 });
                 if (!novice) {
                     alert("学员信息均需填写！");
                     return false;
                 }
             }
         }
       // alert("温馨提示：发展让其您所属商城业主会员或客户粉丝下订单，您才能享受更多佣金金币");
       if (bbb) {
           ajaxsut();
       }


	});

	$("#addNovice").click(function(){
		$("#novice").show();
	});
	
	
	//购物车确定订单
	$("#commitmulti").click(function() {
        if(panduan == "00"){
            panduan =="01";
   //     alert("温馨提示：发展让其您所属商城业主会员或客户粉丝下订单，您才能享受更多佣金金币");
        if ($("#addressDetailed").val() == "") {
            alert("请添加收货地址");
            return false;
        }
        //获取公司列表ID，以及PID-数量-stardard
        var companyId = "";
        var leavemessage = "";

        //公司
        $(".companyId").each(function () {
            companyId += $(this).val() + ",";
        });
        //买家留言
        $(".leavemessage").each(function () {
            leavemessage += ($(this).val() == "" ? "无" : $(this).val()) + ",";
        });
        var pid = "";

        $(".ptbl").each(function () {
            var ppid = $(this).find(".ppid").val();
            var num = $(this).find(".num").val();
            var stardard = $(this).find(".stardard").text();
            var priceid= $(this).find(".priceid").val();
            var prt= $(this).find(".prt").val();
            var price= $(this).find(".price").val();
            var ccj= $(this).find(".ccj").val();
            if (stardard == "") {

                stardard = "默认规格";
            }
            pid += ppid + "-" + num + "-" + stardard + "-"+ priceid + "-"+ prt + "-"+ price + "-"+ ccj;
            pid += "zz";


        });

        /*		data:{

         companyId:companyId,
         pid:pid,
         "staffAddress.addressID":$("#addressDetailed").val(),
         leavemessage:leavemessage
         },*/

        $("#companyIds").val(companyId);
        $("#pids").val(pid);
        $("#leavemessages").val(leavemessage);
        var ulp = basePath
            + "/ea/wfjshop/sajax_ea_ShoppingMulti.jspa?";
        var formData = $("#formsutm").serialize();
        formData = decodeURIComponent(formData, true);

        $.ajax({
            type: "GET",
            url: ulp + formData,
            async: false,
            dataType: "json",
            success: function (data) {

                if (data == null) {
                    alert("数据提交失败。请重试");
                } else {
                    ddid = data;
                    var ret = checkGoldInte();
                    if(ret){
                        $(".wfj11_015_bottom").css("display", "none");
                        $("#occlusion2").css("z-index", $(".wfj11_015").css("z-index") + 1);
                        $("#occlusion2").jqmShow();
                        $(".wfj11_015_buy_commit").css("z-index", $("#occlusion2").css("z-index") + 1);
                        $(".wfj11_015_buy_commit").fadeIn(1000);
                    }else {
                        alert("查询积分失败")
                    }



                }
            },
            error: function (data) {
                alert("提交订单失败");
            }
        });
    }
	});
	
	
	
	
	
	//切换支付方式
	 $(".wfj11_015_choice").click(function(){
            if($(this).find("span").text()!="(您金币为0,无法选择)"&& $(this).find("span").text()!="(您的积分不足，无法选择)"&& $(this).find("span").text()!="(您的金币不足，无法选择)"&& $(this).find("span").text()!="(您积分为0,无法选择)"&& $(this).find("span").text()!="(您的金币已冻结，无法选择)") {
                $(".wfj11_015_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
                $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
                zffs = $(this).find(".second").find("img").attr("name");
            }
	});
	


	$(".jqmOverlay").live("click",function(){
		$(".tanchu").fadeOut();
		$("#occlusion2").jqmHide();
        $(".wfj11_015_buy_commit").fadeOut();
        $("#occlusion2").jqmHide();
        $(".wfj11_015_bottom").css("display","");
        panduan="00";
	});
	
	 //点击行业文本域
     var hylb=$("#hangyelb").text();
     $("#hangyelb").click(function(){
         // $(".hangyelb_").show();
         // $(".hangyelb").show();
         // findIndustry("","hylb_p");
         showMore()
     });
     $(".hangyelb img").click(function(){
         $(".hangyelb_").css("display","none");
         $(".hangyelb").css("display","none");
     });
     //点击行业外层，隐藏弹窗
     // $(".hangyelb_").click(function(){
     //     $(".hangyelb_").css("display","none");
     //     $(".hangyelb").css("display","none");
     // });
     //点击一级行业
     $("#hylb_p p").live("click",function(){
         var hylb_p=$(this).text();
         $("#hangyelb").text(hylb_p+"/");
         $("#hangyelb").parent().find("input").val($("#hangyelb").text());
         $(".hangyelb_").css("display","none");
         $(".hangyelb").css("display","none");
         var codeID= $(this).attr("class");
         $("#industryId").val(codeID);
         console.log($("#industryId").val());
         findIndustry(codeID,"hylb_p2");
         $(".hangyelb2").find("h3").text(hylb_p);
         $(".hangyelb2_").show();
         $(".hangyelb2").show();
     });
   //点击二级行业赋值到行业文本域
     $("#hylb_p2 p").live("click",function(){
         var hylb_p2=$(this).text();
         $("#hangyelb").append(hylb_p2+"/");
         $("#hangyelb").parent().find("input").val($("#hangyelb").text());
         $(".hangyelb_").css("display","none");
         $(".hangyelb").css("display","none");
         $(".hangyelb2_").css("display","none");
         $(".hangyelb2").css("display","none");
         var codeID= $(this).attr("class");
         $("#industryId").val(codeID);
         findIndustry(codeID,"hylb_p3");
         $(".hangyelb3").find("h3").text(hylb_p2);
         $(".hangyelb3_").show();
         $(".hangyelb3").show();
     });
    //点击三级行业赋值到行业文本域
    $("#hylb_p3 p").live("click",function(){
        var hylb_p3=$(this).text();
        var codeID= $(this).attr("class");
        $("#industryId").val(codeID);
        console.log($("#industryId").val());
        $("#hangyelb").append(hylb_p3);
        $("#hangyelb").parent().find("input").val($("#hangyelb").text());
        $(".hangyelb_").css("display","none");
        $(".hangyelb").css("display","none");
        $(".hangyelb2_").css("display","none");
        $(".hangyelb2").css("display","none");
        $(".hangyelb3_").css("display","none");
        $(".hangyelb3").css("display","none");
    });



    $("#companyName").live("blur",function(){
    	$("#companyIdentifier").val($(this).val()); 
     });
       		 $.fn.FontScroll = function(options){
	            var d = {time: 3000,s: 'fontColor',num: 1}
	            var o = $.extend(d,options);
	            this.children('ul').addClass('line');
	            var _con = $('.line').eq(0);
	            var _conH = _con.height(); //滚动总高度
                 // $("#FontScroll .line li").height("3rem");
                var _conChildH = $("#FontScroll .line li").outerHeight(true);//一次滚动高度
                // alert(_conChildH)
	            var _temp = _conChildH;  //临时变量
	            var _time = d.time;  //滚动间隔
	            var _s = d.s;  //滚动间隔
	            _con.clone().insertAfter(_con);//初始化克隆

	            //样式控制
	            var num = d.num;
	            var _p = this.find('li');
	            var allNum = _p.length;
	
	            _p.eq(num).addClass(_s);
	
	
	            var timeID = setInterval(Up,_time);
	            this.hover(function(){clearInterval(timeID)},function(){timeID = setInterval(Up,_time);});

	            function Up(){
	                _con.animate({marginTop: '-'+_conChildH});
	                //样式控制
	                _p.removeClass(_s);
	                num += 1;
	                _p.eq(num).addClass(_s);
	
	                if(_conH == _conChildH){
	                    _con.animate({marginTop: '-'+_conChildH},"normal",over);
	                } else {
	                    _conChildH += _temp;
	                }
	            }
	            function over(){
	                _con.attr("style",'margin-top:0');
	                _conChildH = _temp;
	                num = 1;
	                _p.removeClass(_s);
	                _p.eq(num).addClass(_s);
	            }
	        }     
        $('#FontScroll').FontScroll({time: 2000,num: 1});
    	$(".gonggao_tu p").css({"line-height":$("#FontScroll").height()+"px","width":$(".gonggao_tu").width()+"px"});
    	$(".jqmWindow").css("height",$(window).height()+"px")
    	$("#occlusion2").css("display","none")
  
        window.onload = window.onresize = function(){
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //console.log(clientWidth);
            //通过屏幕宽度去设置不同的后台根字体的大小
            //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
        }

	 	
    	//xgb选择车辆
    	$(".che").change(function(){
    		$(".carID").val($('.che option:selected').attr("date-carID"));
    	});

});


//确认订单 生成订单方法
function ajaxsut() {
    if(journalNum!=null&&journalNum!="") {
        ajaxPayBackUp();
        return;
    }
    bbb=false;
	var formData = $("#formsutm").serialize();
	formData = decodeURIComponent(formData,true);
	var morre = $("#formsutm").find("#morre").val();
	console.log(morre);


    // if(goumaitype == "jifen"){
    //     if(window.confirm("您确认要用积分购买吗?")){
    //
    //     var ulp = basePath
    //         + "/ea/wfjshop/sajax_ea_Shopping.jspa?";
    //     $.ajax({
    //         type : "GET",
    //         url : ulp+formData,
    //         async : true,
    //         dataType : "json",
    //         success : function(data) {
    //             var json = eval('(' + data + ')');
    //             if (json == null) {
    //                 alert("数据提交失败。请重试");
    //                 bbb=true;
    //             } else {
    //                 ddid = json.ddid;
    //                 if(ddid=="fail"){
    //                     alert("该推荐人没有注册过微分金，请重新填写，或者不填写！");
    //                     bbb=true;
    //                     return false;
    //                 }
    //
    //                 var companyName = json.companyName;
    //                 if((model=="8"&&producttype=="个人会员")||(model=="1"&&producttype=="个人会员")||(model=="0.5"&&producttype=="个人会员")){
    //                     if (buyIsOkPage=="buyIsOkPage"){
    //                         document.location.href = basePath+"st/buyIsOkPage.jsp?ddid="+ddid+"&companyName="+companyName+"&staffName="+staffName+"&companyIdentifier="+companyIdentifier;
    //                     }else {
    //                         document.location.href = basePath + "page/WFJClient/suc/fansOk.jsp?ddid=" + ddid + "&companyName=" + companyName;
    //                     }
    //                 }else if(parseFloat(morre)=="0"){
    //                     if (buyIsOkPage=="buyIsOkPage"){
    //                         document.location.href = basePath+"st/buyIsOkPage.jsp?ddid="+ddid+"&companyName="+companyName+"&staffName="+staffName+"&companyIdentifier="+companyIdentifier;
    //                     }else {
    //                         document.location.href = basePath + "page/WFJClient/suc/fansOk.jsp?ddid=" + ddid + "&companyName=" + companyName;
    //                     }
    //                 }else{
    //                     var ulp = basePath
    //                         + "/ea/wfjshop/sajax_ea_diaoyongJifen.jspa?";
    //                     $.ajax({
    //                         type : "GET",
    //                         url : ulp+formData,
    //                         async : false,
    //                         dataType : "json",
    //                         data:{
    //                             ddid:ddid
    //                         },
    //                         success : function(data) {
    //                             var json = eval('(' + data + ')');
    //                              var ss=json.chenggong;
    //                              if(ss == "cg"){
    //                                  if (buyIsOkPage=="buyIsOkPage"){
    //                                      document.location.href = basePath+"st/buyIsOkPage.jsp?ddid="+ddid+"&companyName="+companyName+"&staffName="+staffName+"&companyIdentifier="+companyIdentifier;
    //                                  }else {
    //                                      document.location.href = basePath + "page/WFJClient/suc/fansOk.jsp?ddid=" + ddid + "&companyName=" + companyName;
    //                                  }
    //                              }else  if(ss=="shibai"){
    //                                  alert("您的帐号有问题，请联系工作人员");
    //                                  bbb=true;
    //                              }
    //                         },
    //                         error : function(data) {
    //                             alert("提交订单失败");
    //                             bbb=true;
    //                         }
    //                     });
    //
    //                 }
    //
    //             }
    //         },
    //         error : function(data) {
    //             alert("提交订单失败");
    //             bbb=true;
    //         }
    //     });
    //     }else{
    //         bbb=true;
    //     	panduan="00";
		// }
    // }else {
	var ulp = basePath
			+ "/ea/wfjshop/sajax_ea_Shopping.jspa?";
	 $.ajax({
		type : "GET",
		url : ulp+formData,
		async : false,
		dataType : "json",
		success : function(data) {
			var json = eval('(' + data + ')');
			if (json == null) {
				alert("数据提交失败。请重试");
			} else {
				ddid = json.ddid;
				if(ddid=="fail"){
					alert("该推荐人没有注册过微分金，请重新填写，或者不填写！");
					return false;
				}
				
				var companyName = json.companyName;
				if((model=="8"&&producttype=="个人会员")||(model=="1"&&producttype=="个人会员")||(model=="0.5"&&producttype=="个人会员")){
					document.location.href = basePath+"page/WFJClient/suc/fansOk.jsp?ddid="+ddid+"&companyName="+companyName;
				}else if(parseFloat(morre)=="0"){
					
					document.location.href = basePath+"page/WFJClient/suc/fansOk.jsp?ddid="+ddid+"&companyName="+companyName+"&showType=lottery";
				}else{
					$(".wfj11_015_bottom").css("display","none");
					$("#occlusion2").css("z-index",$(".wfj11_015").css("z-index")+1);
					$("#occlusion2").jqmShow();
					$(".wfj11_015_buy_commit").css("z-index",$("#occlusion2").css("z-index")+1);
					$(".wfj11_015_buy_commit").fadeIn(1000);
				}
				
			}
		},
		error : function(data) {
			alert("提交订单失败");
		}
	});
    // }
}

//点击确认支付
function zf() {
    var formData = $("#formsutm").serialize();
    formData = decodeURIComponent(formData,true);
    var companyName="";
    $(".wfj11_015_width>a").each(function(){
        companyName+=$(this).text().trim()+",";
    });
    companyName=companyName.substr(0,companyName.length-1);
	if (zffs == null) {
		alert("请选择支付方式");
		return false;
	} else if ($("#addressDetailed").val() == "") {
		alert("请选择收货地址");
		return false;
	} else {

		if (typeof(ddid) == "undefined")
		{
		    alert("订单提交失败，无法支付");
		    return;
		}
		
		var goodsname = "("+companyName+")";
		$(".goodsName").each(function(){
			goodsname+=$(this).val()+",";
			
		});
		if(goodsname!=""){
			goodsname=goodsname.substring(0,goodsname.length-1);
            if(goodsname.length>10){
                goodsname=goodsname.substr(0,10)+"...";
            }
		}
		

		if (zffs == '1') {			
			var par = new Array();
			par.push(basePath);
            if(journalNum!=""&&journalNum!=null){
                par.push("page/ea/main/marketing/supermarket/selfservice/wfjAlipay.jsp?");
            }else{
                par.push("page/WFJClient/ShopOwner/wfjAlipay.jsp?");
            }
			par.push("WIDout_trade_no=" + ddid);
			
			par.push("&WIDtotal_fee="+ $("#morre").val());
		
			par.push("&WIDsubject=" + goodsname);
            if(journalNum!=""&&journalNum!=null){
                par.push("&WIDbody='selfpay'");//订单描述
            }else{
                par.push("&WIDbody=''");//订单描述
            }

			par.push("&WIDit_b_pay=''");//超时时间
			par.push("&WIDextern_token=''");//钱包
            par.push("&buyIsOkPage="+buyIsOkPage);
			//window.location.href = encodeURI(par.join(""));
			_AP.pay(encodeURI(par.join("")));
		} else if (zffs == '2') {
			$("#formsutm").attr(
					"action",
					basePath + "ea/wfjshop/ea_zfgs.jspa?ddid=" + ddid
							+ "&baseUrl=" + basePath+"&code=2&buyIsOkPage="+buyIsOkPage);
			$("#submit").click();
		} else if (zffs == '3') {

			var money = $("#morre").val();
			var ua = navigator.userAgent.toLowerCase();
			var isWeixin = ua.indexOf('micromessenger') != -1;
			var attach = "other";
              if(journalNum!=""&&journalNum!=null){
                  attach = "selfpay";
              }
			if (!isWeixin) {
                //调用ios/android
                var u = navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                var elkc = "";
                 try {
                     if(isAndroid==true){
                         Android.isElKCApp();
                         elkc = "elkc";
                     }else if(isiOS==true){
                        // var url= "func=" + 'isElKCApp';
                        // window.webkit.messageHandlers.Native.postMessage(url);
                         elkc = "";
                     }
                     
                 }catch(e){
                     elkc = "";
                 }
                $(".loading").show();
			 //app微信支付
				$.ajax({
					url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa",
					type:"get",
					data:{
						"payDto.orderId":ddid,
						"payDto.totalFee":money,
						"payDto.body":goodsname,
				        "payDto.attach":attach,
                         elkc:elkc,
                        isiOS:isiOS
					},
					success:function suc(data){
						var mb=eval("("+data+")");
						var appPackage=mb.appPackage;


			            var  appid = appPackage.appid
			            var partnerid = appPackage.partnerid;
			            var prepayid = appPackage.prepayid;
			            var packages = appPackage.packages;
			            var noncestr = appPackage.noncestr;
			            var timestamp = appPackage.timestamp;
			            var err_code = appPackage.err_code;
			            var sign = appPackage.sign;


                       $(".loading").hide();
                        try
                        {
                            if(isAndroid==true){
                                Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,ddid,err_code,goodsname,attach);
                            }else if(isiOS==true){
                                var url= "func=" + 'ioscallappChat';
                                params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':ddid,'errcode':err_code,'goodsname':goodsname,'attach':attach};
                                for(var i in params){
                                    url = url + "&" + i + "=" + params[i];
                                }
                                window.webkit.messageHandlers.Native.postMessage(url);
                            }
                        }
                        catch(err)
                        {
                           alert("微信支付升级中，请改用其他支付方式");
							return;
                        }

					}
					
				});
				
			}else{
				window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+goodsname+"-"+money+"-"+staffID+"-1-1-"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
				
			}	
			
			//return false;
		}else if(zffs=='5'){
			//钱盒子支付
			var morre = $("#morre").val();
			document.location.href = basePath+"ea/wfjshop/ea_moneyBoxPay.jspa?ddid="+ddid+"&morre="+morre;
			return false;
		}else if(zffs == "6"){
            var money = $("#morre").val();
            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(money);
            return;
            // //金币支付
            // if(journalNum!=""&&journalNum!=null){
            //     selfBuyPB("07");
            // }else{
            //     buy(formData,companyName,"07");
            // }


        }else if(zffs == "7"){
            var money = $("#morre").val();
            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(money);
            return;
            // //积分支付
            //
            // if(journalNum!=""&&journalNum!=null){
            //     selfBuyPB("05");
            // }else{
            //     buy(formData,companyName,"05");
            // }
        }else {
			if(token!=0){
				return;
			}
			token=1;
			$.ajax({
				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
				type:"get",
				data:"fenlei=03&ddid="+ddid,
				success:function suc(data){
					var mb=eval("("+data+")");
					var succ=mb.succ;
					var threeNo=mb.threeNo;
					if(succ=="success"){
						window.location.href=basePath+"page/WFJClient/suc/xxzf.jsp?threeNo="+threeNo+"&user1="+user1;
							
					};
				}
				
			});
			
		}
	}
}


function yz(){
   var shuliang=$("#shuliag").val();
   if(shuliang<0){
      alert("数量大于0"+shuliang);
 	  $("#shuliag").val("1");
   }else{
          var privace=Number($("#indus").val());
		  var shuliang=Number($("#shuliag").val());
		  var qian=Number(privace*shuliang).toFixed(2);
		  $("#morre").val(qian);
		  $(".xzf").text(qian);
		 $("#sapnqian").text("￥"+qian);
		 console.info($("#sapnqian").text());
   }
    totalNum = shuliang;
    totalMoney = qian;


    // if(mode=="cash"||mode=="scard"||mode=="scan"){
    //     $(".integral").hide();
    // }else {
    //     var shuliang=$("#shuliag").val();
    //     if (parseFloat(bonusPoints) - (parseFloat(shuliang)*parseFloat(jineshu) * parseFloat("100")) < 0) {
    //         $(".slide_btn").removeClass("on");
    //         bind=0;
    //         $(".integral h3").html("当前积分:" + bonusPoints + "个，不足以用积分兑换此商品");
    //         goumaitype = "jinqian";
    //     } else if (bonusPoints == "0" ) {
    //         $(".slide_btn").removeClass("on");
    //         bind=0;
    //         $(".integral h3").html("您积分为0");
    //         goumaitype = "jinqian";
    //     } else if (parseFloat(bonusPoints) - (parseFloat(shuliang)*parseFloat(jineshu) * parseFloat("100")) >= 0) {
    //         $(".slide_btn").addClass("on");
    //         bind=1;
    //         $("#sapnqian").html("￥" + 0);
    //         $(".integral h3").html("可用积分" + (Number($("#morre").val())*100).toFixed(2) + "支付" + "￥" + $("#morre").val());
    //         goumaitype = "jifen";
    //     }
    // }




}


function findIndustry(codepid,position){
	var url=basePath+"/ea/industry/sajax_ea_getIndustry.jspa?codePID="+codepid;
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member=eval("(" + data + ")");
			var list=member.industryList;
			var htl=new Array();

			for (var i = 0; i < list.length; i++) {
			    // if(list[i].codeValue.indexOf("联营平台")==-1) {
                    htl.push("<p class='" + list[i].codeID + "'>" + list[i].codeValue + "</p>");
                // }else {
                //     htl.push("<p class='" + list[i].codeID + "'>" + list[i].codeValue.substring(0,list[i].codeValue.length-4)+ "</p>");
                // }
			}
			if(list.length==0){
                $("#"+position).empty();
                $("#"+position).parent().hide();
                $("#"+position).parent().prev().hide();
            }
			$("#"+position).html(htl.join(""));
			//滚动效果
			if(position=="oneindustry"){
			$(".tanchu").find("div").find("ul").css("height",parseInt($(".tanchu").height()-20)+"px");
			 }
		 },
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
	
}

//验证邮箱
function CheckMail(mail) {
	 var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	 if (filter.test(mail)) {		
		 return true;
	 }
	 else {
	     return false;
	  }
}
function checkidentifer(ci,companyName,type){
   var re = "";
    var ulp=basePath + "/ea/wfjshop/sajax_ea_getcompispmun.jspa";
    $.ajax({
        type: "GET",
        url: ulp ,
        async:false,
        dataType: "json",
        data:{
        	companyIdentifier:ci,
        	companyName:companyName,
        	type:type       	
        },
        success: function(data){
        var json = eval('(' + data + ')');
            re = json.result;
        }
      });
    
    return re;

}
//新增临时地址，不保存到数据库，因为没有用户
function addAddress(){
    var backurls = window.location.href;
    if(mode=="cash"||mode=="face") {
        if(backurls.indexOf("&staffAddress.consignee=")!=-1){
            backurls = backurls.substring(0,backurls.indexOf("&staffAddress.consignee="))

        }
        document.location.href = basePath + "ea/wfjshop/ea_toaddAddress.jspa?backurls=" + encodeURIComponent(backurls);
    }else if(mode=="scard") {
        if(backurls.indexOf("&staffAddress.addressID=")!=-1){
            backurls = backurls.substring(0,backurls.indexOf("&staffAddress.addressID="))

        }
        document.location.href = basePath+"ea/wfjshop/ea_getAddressList.jspa?backurls=" + encodeURIComponent(backurls)+"&intf=31&staffid="+staffID;
    }else{
        if(backurls.indexOf("&staffAddress.addressID=")!=-1){
            backurls = backurls.substring(0,backurls.indexOf("&staffAddress.addressID="))

        }
        document.location.href = basePath+"ea/wfjshop/ea_getAddressList.jspa?intf=31&staffid="+staffID+"&backurls="+encodeURIComponent(backurls);

    }



}

//备份信息
function ajaxPayBackUp() {
    var shuliang=$("#shuliag").val();
    var ppid = $(".ppid").val();
    var noviceCode ="";
    if($("#noviceCode").length>0) {
         noviceCode = $("#noviceCode").val();
         consignee =$("#noviceName").val();
         phone =$("#novicePhone").val();
        noviceAddress =$("#noviceAddress").val();
    }
    var ulp = basePath
        + "/ea/sm/sajax_ea_ajaxPayBackUp.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : false,
        data: {
            "payBackupBill.addressID": addressID,
            "payBackupBill.noviceName": consignee,
            "payBackupBill.novicePhone": phone,
            "payBackupBill.noviceAddress": noviceAddress,
            "payBackupBill.noviceCode": noviceCode,

            "payBackupBill.journalNum":journalNum,
            "payBackupBill.ptppid":ptppid,
            "payBackupBill.ptstandard":ptstandard,
            "payBackupBill.remark":$(".remark").val(),
             ppid:ppid,
             itemNum:shuliang

        },
        dataType : "json",
        success : function(data) {
            ddid = journalNum;
            if(mode=="scan") {
                $(".wfj11_015_bottom").css("display", "none");
                $(".wfj11_015_buy_commit").css("z-index", $("#occlusion2").css("z-index") + 1);
                $(".wfj11_015_buy_commit").fadeIn(1000);
            }else if(mode=="scard"){//购物卡支付
                //跳回输入密码页面如果有密码的话
                document.location.href = basePath+"/page/ea/main/marketing/supermarket/selfservice/scardPay.jsp?journalNum="+journalNum+"&totalMoney="+totalMoney+"&totalNum="+totalNum+"&cardNum="+cardNum+"&posNum="+posNum+"&paymentCode="+paymentCode+"&sccId="+sccId+"&vipmoney="+vipmoney+"&openid="+openid;

            }else if(mode=="cash"){//现金支付
                document.location.href = basePath + "ea/sm/ea_showCash.jspa?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum+"&posNum="+posNum+"&comID="+companyID+"&address=1";

            }else if(mode=="face"){//刷脸支付
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" +  journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum +"&posNum=" + posNum+"&comID="+companyID+"&companyName="+companyName;

            }else if(mode=="facecard"){//刷脸购物卡支付
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" +  journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum +"&posNum=" + posNum+"&comID="+companyID+"&companyName="+companyName+"&sccId="+sccId+"&zf=1";

            }


        },
        error : function(data) {
            console.log("备份信息");
        }
    });

}
function buy(formData,companyName,buyPatter) {
    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_diaoyongJifen.jspa?buyPatter="+buyPatter+"&";
    $.ajax({
        type : "GET",
        url : ulp+formData,
        async : true,
        dataType : "json",
        data:{
            ddid:ddid
        },
        beforeSend : function() {
            $(".loading").show();
        },
        success : function(data) {
            $(".loading").hide();
            var json = eval('(' + data + ')');
            var ss=json.chenggong;
            if(ss == "cg"){
                if (buyIsOkPage=="buyIsOkPage"){
                    document.location.href = basePath+"st/buyIsOkPage.jsp?ddid="+ddid+"&companyName="+companyName+"&staffName="+staffName+"&companyIdentifier="+companyIdentifier;
                }else {
                    document.location.href = basePath + "page/WFJClient/suc/fansOk.jsp?ddid=" + ddid + "&companyName=" + companyName;
                }
            }else  if(ss=="shibai"){
                alert("您的帐号有问题，请联系工作人员");
                bbb=true;
            }
        },
        error : function(data) {
            alert("提交订单失败");
            bbb=true;
        }
    });
}
//终端机积分金币购物
function selfBuyPB(wfStatus4){
    var totalMoney = $("#morre").val();
    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_genSelfPayPoint.jspa?";
    $.ajax({
        type: "GET",
        url: ulp,
        dataType: "json",
        data: {
            morre: totalMoney,
            journalNum: ddid,
            wfStatus4:wfStatus4
        },beforeSend : function() {
            $(".loading").show();
        },
        success: function (data) {
            $(".alert_dh").hide();
            var m = eval('(' + data + ')');
            var result = m.result;
           // var bo = m.bo;

            // if(bo==false){
            //
            //     alert("请勿重复支付");
            //
            //     return;
            // }
            if (result) {

                document.location.href = basePath + "page/conacts/restaurant/paySuc.jsp";

            } else {
                console.log("支付出错");
            }

        },
        error: function (data) {

            console.log("支付失败")
        }
    });
}
function checkGoldInte() {
    var isOk;
    var price = $("#morre").val();
    $.ajax({
        type: "GET",
        url: basePath + "/ea/jinbi/sajax_checkGoldInte.jspa?morre="+price,
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("("+data+")");
            var integral  = member.integral;
            var gold = member.gold;
            var status = member.status;
            if (data == null) {
                alert("数据提交失败。请重试");
            } else {
                if (parseFloat(integral) - accMul(parseFloat(price) , parseFloat("100")) < 0) {
                    $(".integral_ span").text("(您的积分不足，无法选择)").show();
                    $(".integral_ .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (integral == "" || isNaN(integral) || integral == null) {
                    $(".integral_ span").text("(您积分为0,无法选择)");
                    $(".integral_ .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (parseFloat(integral) - accMul(parseFloat(price) , parseFloat("100")) >= 0) {
                    //goumaitype = "jifen";
                } else {
                    $(".integral_ span").text("(您的积分不足，无法选择)");
                    $("integral_").attr("style","pointer-events: none")
                    $(".integral_ .second").hide()
                }
                if(status!=null && status!=""){
                    $(".gold span").text("(您的金币已冻结，无法选择)").show();
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                }else if (parseFloat(gold) - accMul(parseFloat(price) , parseFloat("100")) < 0) {
                    $(".gold span").text("(您的金币不足，无法选择)").show();
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (gold == "" || isNaN(gold) || gold == null) {
                    $(".gold span").text("(您金币为0,无法选择)");
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (parseFloat(gold) - accMul(parseFloat(price) , parseFloat("100")) >= 0) {
                    //goumaitype = "jifen";
                } else {
                    $(".gold span").text("(您的金币不足，无法选择)");
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                }

                isOk = true;
            }
        },
        error: function (data) {
            isOk = false
        }
    });
    return isOk;
}

function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

function payInput(obj){
    var len = $(obj).val().length - 1;
    if (len >= 0) {
        $(".pay_label li").eq(len).addClass("pass").nextAll().removeClass();
        if (len == 5) {
            //    setTimeout("initPay()", 400);//设置延时（验证密码方法放这位置）
            $(".pay_inp").blur();
            var code = $(".pay_inp").val(); //获取密码
            //查找用户的支付密码
            var url = basePath + "/ea/jinbi/sajax_toSearchPaymentCode.jspa?paymentCode="+code+"&user="+user;
            $.ajax({
                url : url,
                type : "get",
                async : false,
                dataType : "json",
                success : function (data) {
                    var member = eval("(" + data + ")");
                    var paymentCode=member.code;
                    if(paymentCode == '00'){//支付密码正确

                        initPay();
                        var formData = $("#formsutm").serialize();
                        formData = decodeURIComponent(formData,true);
                        var companyName="";
                        $(".wfj11_015_width>a").each(function(){
                            companyName+=$(this).text().trim()+",";
                        });
                        companyName=companyName.substr(0,companyName.length-1);

                        if(zffs == "6"){
                            //金币支付
                            if(journalNum!=""&&journalNum!=null){
                                selfBuyPB("07");
                            }else{
                                buy(formData,companyName,"07");
                            }


                        }else if(zffs == "7"){
                            //积分支付

                            if(journalNum!=""&&journalNum!=null){
                                selfBuyPB("05");
                            }else{
                                buy(formData,companyName,"05");
                            }
                        }
                    }else if(paymentCode == '01'){//支付密码不正确
                        prompt("支付密码不正确");
                    }else{//没有支付密码
                        prompt("未设置支付密码");
                    }
                }
            });
        }
    } else {
        $(".pay_label li").removeClass();
    }

}


function checkbday(carNumber,ppid){
  var bool = true;

    var url = basePath + "/ea/mappointment/sajax_ea_checkbday.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        data : {
            carNumber:carNumber,
            ppid:ppid
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var jsonresult = eval("(" + data + ")");
            var result = jsonresult.result;
            if(result=="1"){
                bool = false;
            }

        }
    })
    return bool;
}