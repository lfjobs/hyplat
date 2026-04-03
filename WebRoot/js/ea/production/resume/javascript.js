/**
 * Created by Administrator on 2016/6/12 0012.
 */
$(document).ready(function(){
	
    //导航虚浮
    //$(".res_top").attr("style","position:fixed;overflow:hidden;z-index:999;height:"+$(window).height()*0.08+"px");
    //$(".res_bot").css("margin-top",$(window).height()*0.08+"px");
    $(".res_top").attr("style","width:100%;position:fixed;overflow:hidden;z-index:999;height:45px;border-bottom: 1px solid #9e9e9e;");

    $(".res_bot").css("margin-top",45+"px");
    $(".res_top2").css("position","static");
    //隐身设置
    $("#each p").click(
        function() {
            $(this).attr("style","background:#9ECCE2;color:#fff;").siblings().attr("style","background:#fff;color:#000;");
        });
    // $(".pri-set_grd2 p").click(
    //     function() {
    //         $(this).attr("style","background: url('"+basePath+"images/resume/gou.png') 14rem 0.8rem no-repeat;background-size: 0.9rem;").siblings().attr("style","background:#fff;");
    //     });

    //求职意向
        //工作性质
    $(".job-int_d button").click(function() {
        $(this).attr("style","background:#FF6600;color:#fff;border: none;").siblings().attr("style","background:#fff;color:#FF6600;");
    });
    $("#gzxz").click(function () {
        $(".job-int_gzxz").css("display", "block");
    });
    $("#btn_wc").click(function () {
        $(".job-int_gzxz").css("display", "none");
    });
    $("#btn_qx").click(function () {
        $(".job-int_gzxz").css("display", "none");
    });
    $(".job-int_d2 p").click(function(){
    	$(".confirmationBox").removeClass("confirmationBox");
    	$(this).addClass("confirmationBox");
        $(this).attr("style","background:url('"+basePath+"images/resume/gou2.png') 14rem 12px no-repeat;background-size: 26px;").siblings().attr("style","background:url('"+basePath+"images/resume/gou2_.png') 14rem 12px no-repeat;background-size: 26px;");
    });
    $(".job-int_d3").click(function(){
        $(".job-int_gzxz").css("display", "none");
    });
        //离职状态
    $(".job-int_d2 button").click(function() {
        $(this).attr("style","background:#FF6600;color:#fff;border: none;").siblings().attr("style","background:#fff;color:#FF6600;");
    });
    $("#qzzt").click(function () {
        $(".job-int_qzzt").css("display", "block");
    });
    $("#btn_wc2").click(function () {
        $(".job-int_qzzt").css("display", "none");
    });
    $("#btn_qx2").click(function () {
        $(".job-int_qzzt").css("display", "none");
    });
    $(".job-int_d2_2 p").click(function(){
    	$(".confirmationBox2").removeClass("confirmationBox2");
    	$(this).addClass("confirmationBox2");
        $(this).attr("style","background:url('"+basePath+"images/resume/gou2.png') 14rem 12px no-repeat;background-size: 26px;").siblings().attr("style","background:url('"+basePath+"images/resume/gou2_.png') 14rem 12px no-repeat;background-size: 26px;");
    });
    $(".job-int_d3-2").click(function(){
        $(".job-int_qzzt").css("display", "none");
    });

    //期望薪资
    $(".sal p").click(
        function() {
            $(this).attr("style","background: url('"+basePath+"images/resume/gou3.png') 14rem 23px no-repeat;background-size: 28px;color:#42BEFF;").siblings().attr("style","background:#fff;color:#000;");
          /*  console.log($(this).html());
           $("#pp").val($(this).html());*/
           
        });

    //行业搜索、行业搜索2
    $(function(){
        $("input").focus(function(){
            $(".wancheng").css("display","none");
            $(".quxiao").css("display","block");
            $(".search_down").css("display","block");
            $(".arrar").css("display","none");
            $(".dropdown").css("display","none");
            $(".header_c .search_k input").css("background","none");
            $(".header_c .search_k input").removeClass("ipt2");
        });
        $("input").blur(function(){

            $(".wancheng").css("display","block");
            $(".quxiao").css("display","none");
            $(".search_down").css("display","none");
            $(".arrar").css("display","block");
            $(".dropdown").css("display","block");
            $(".header_c .search_k input").addClass("ipt2");
        });
        $(".xuan_head").click(function(){
            $("#xuanze").toggleClass("xianshi");

            $(".xia").toggleClass("shang");
        });

                //添加所选
        $(".kexuan .xuan_lis").click(function(){
            var nLen=$("#xuanze .xuan_lis").length;
            if(nLen < 3){
                var _this = $(this).text();
                var _xuan = '<div class="xuan_lis"><span>'+_this+'</span><img class="cha" src="'+basePath+'images/resume/cha_03.png"/></div>';

                $(".yixuan").find(".xuan_hangye").append(_xuan);
                $("#shuzhi").text(++nLen);
            }else{
                $(".alert_div").css("display","block");
                setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
            }
        });
                //删除所选
        /*$(document).on('click','.cha',function(){

         $(this).parent().remove();
         })*/
        $(document).on('click','.cha',function(){

            $(this).parent().remove();
            var asd=$("#xuanze .xuan_lis").length;

            $("#shuzhi").html(asd);
        })
    });

    //职位搜索
    $("#service").click(function(){
        $(".ser_ul").toggleClass("market");
        $(".ser_xia").toggleClass("shang2");
    });
    $("#market").click(function(){
        $(".mar_ul").toggleClass("market");
        $(".mar_xia").toggleClass("shang2");
    });
        //客服售前售后
    $("#service_img").click(function(){
        var nLen=$("#xuanze .xuan_lis").length;
        if(nLen < 3){
            var _this = $("#service").text();
            var _xuan = '<div class="xuan_lis"><span>'+_this+'</span><img class="cha" src="'+basePath+'images/resume/cha_03.png"/></div>';

            $(".yixuan").find(".xuan_hangye").append(_xuan);
            $("#shuzhi").text(++nLen);
            $(".service_img_2").toggleClass("market-1_img_1");
            $(".service_img_1").toggleClass("market-1_img_2");

        }else{
            $(".alert_div").css("display","block");
            setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
        }
    });
         //市场
    $("#market_img").click(function(){
        var nLen=$("#xuanze .xuan_lis").length;
        if(nLen < 3){
            var _this = $("#market").text();
            var _xuan = '<div class="xuan_lis"><span>'+_this+'</span><img class="cha" src="'+basePath+'images/resume/cha_03.png"/></div>';

            $(".yixuan").find(".xuan_hangye").append(_xuan);
            $("#shuzhi").text(++nLen);
            $(".market_img_1").toggleClass("market_img_2");
            $(".market_img_2").toggleClass("market_img_1");

        }else{
            $(".alert_div").css("display","block");
            setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
        }
    });
        //公关媒体
    $("#public_img").click(function(){
        var nLen=$("#xuanze .xuan_lis").length;
        if(nLen < 3){
            var _this = $("#public").text();
            var _xuan = '<div class="xuan_lis"><span>'+_this+'</span><img class="cha" src="'+basePath+'images/resume/cha_03.png"/></div>';

            $(".yixuan").find(".xuan_hangye").append(_xuan);
            $("#shuzhi").text(++nLen);
            $(".public_img_1").toggleClass("public_img_2");
            $(".public_img_2").toggleClass("public_img_1");
        }else{
            $(".alert_div").css("display","block");
            setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
        }
    });
        //市场下拉菜单
    $(".kexuan .xuan_lis_2").click(function(){
        var nLen=$("#xuanze .xuan_lis").length;
        if(nLen < 3){
            var _this = $(this).text();
            var _xuan = '<div class="xuan_lis"><span>'+_this+'</span><img class="cha" src="'+basePath+'images/resume/cha_03.png"/></div>';

            $(".yixuan").find(".xuan_hangye").append(_xuan);
            $("#shuzhi").text(++nLen);


        }else{
            $(".alert_div").css("display","block");
            setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
        }
    });

    $(".img").click(function(){
        var nLen=$("#xuanze .xuan_lis").length;
        if(nLen < 3){
            var _this = "";
            var _xuan = '';

            $(".yixuan").find(".xuan_hangye").append(_xuan);
            $("#shuzhi").text(++nLen);

            $(this).addClass("img2")
        }else{
        }(w)
    });
    $(".alert_div2").css("height",$);
    $(".header_c_text").click(function(){
        $(".alert_div2").css("display","block");
    });
    $("#queding").click(function(){
        $(".alert_div2").css("display","none");
    });
    $("#quxiao").click(function(){
        $(".alert_div2").css("display","none");
    });

    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //通过屏幕宽度去设置不同的后台根字体的大小
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
});