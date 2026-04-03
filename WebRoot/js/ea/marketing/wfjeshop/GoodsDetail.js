// JavaScript Document
var isAndroid
var isiOS
var posNum = "";
$(function() {
    try {
        posNum = Android.forAndroidDeviceId();

        if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);

            if (posNum != "" && posNum != null) {
                var href = $(".cartList").find("a").attr("href");
                $(".cartList").find("a").attr("href", href + "&posNum=" + posNum);
            }
        }
    }catch(error){
        if(($(window).width()==1080&&$(window).height()==1546)||($(window).width()==534&&$(window).height()==636)) {
            posNum = 123;

            var href = $(".cartList").find("a").attr("href");
            $(".cartList").find("a").attr("href",href+"&posNum="+posNum);


        }
    }


    if(pos=="1"){
        $("#buynow").text("生成收款码");
        $(".backt").html("&nbsp;");

    }


    var u = navigator.userAgent;
    isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if(isAndroid==true){
        var obj = document.getElementById("contact");
        if (obj!=undefined && obj!=null)
        {
            obj.setAttribute("onclick", "AndroidtoChat(this)");
        }
    }else if(isiOS==true){
        var obj = document.getElementById("contact");
        if (obj!=undefined && obj!=null)
        {
            obj.setAttribute("onclick", "ioschat(this)");
        }
    }

    // 对象
    var o = {
        selected : function() {

            $(".box").css("display", "none");
            $(".wfj11_013_bottoms").css("display", "block");
            $("body,.wfj11_013").css("overflow", "auto");

            // 获取所选label
            var yanse;
            var daxiao;

            daxiao = $("#daxiao .items .xz").html();
            yanse = $("#yanse .items .xz").html();


            if (daxiao == null||daxiao=="") {
                if (yanse == null||yanse =="") {
                } else {
                    $(".wfj11_013_choose ul").find("li").eq(0).text(
                        "已选择:  “" + yanse + "”     " + shuliang + "件 ");

                }
            } else {
                if (yanse == null||yanse =="") {
                    $(".wfj11_013_choose ul").find("li").eq(0).text(
                        "已选择:  “" + daxiao + "”     " + shuliang + "件 ");
                } else {
                    $(".wfj11_013_choose ul").find("li").eq(0).text(
                        "已选择:  “" + daxiao + "”  “" + yanse + "”     "
                        + shuliang + "件 ");

                }
            }
        },
        openBox : function() {
            $(".box").css("display", "block");
            $(".wfj11_013_bottoms").css("display", "none");
            $("body,.wfj11_013").css("overflow", "hidden");
        }

    }

    // 打开弹框
    $(".wfj11_013_choose").click(function() {
        $(".box").css("display", "block");
        $(".wfj11_013_bottoms").css("display", "none");
        $("body,.wfj11_013").css("overflow", "hidden");

        $(".bottom_button1").css("display", "block");
        $(".bottom_button2").css("display", "none");
    });

    $("#buy").find("li").find("div").click(function() {
        if(posNum==null||posNum=="") {
            if (supername == "17310850569"&&type=="学员报名") {
                //alert("请联系工作人员，确认一下您的邀请人");
                if (confirm("您还没有绑定服务人员，请前往需求发布记录页面进行确认服务人员进行绑定")) {
                    document.location.href = path + "/ea/dserve/ea_detailListBySccid.jspa?sccId=" + sccid;
                }
                return;
            }
        }

        var id=$(this).attr("id");
        $("#optype").val(id);
        stardard = $(".wfj11_013_choose ul").find("li").eq(0).text();
        if(((size!=null&&size!="")||(color!=null&&color!=""))&&(stardard.indexOf("规格参数")!=-1&&stardard.indexOf("已选择")==-1)){
            $(".box").css("display", "block");
            $(".wfj11_013_bottoms").css("display", "none");
            $("body,.wfj11_013").css("overflow", "hidden");
            $(".bottom_button1").css("display", "none");
            $(".bottom_button2").css("display", "block");
        }else if(id=="shopcart"){
            if(pos!="1") {

                ajaxCxpcart();


                if (length > 0 && cxpcart == "0") {
                    $(".alert_new_").show();
                    $(".alert_new").show();
                    return false;
                }
            }
            if(pricetype=="1"){
                ajaxShopcart(ppid,stardard,"",$("#shuliang").val());
            }else{
                putShopCar();
            }
        }else if(id=="buynow"){
            if(length>0&&$("#ptppid").val()==''){
                $(".alert_new_").show();
                $(".alert_new").show();
                return false;

            }

            if(pos=="1"){
                //生成收款码goodsName quality price
                var goodsname = $(".wfj11_013_details_name").text();
                var quality = $("#shuliang").val();

                document.location.href = path+"ea/assicode/ea_genDpJieSunCode.jspa?goodsName="+goodsname+"&quality="+quality+"&price="+pprice+"&companyId="+companyId+"&ppid="+ppid;

                return;
            }


            gm();



        }

    });

    //确定
    $(".confirmop").click(function(){
        if(color!=null&&color!=""){

            var yanse = $("#yanse .items .xz").text();
            if(yanse==""){
                alert("请选择"+color);
                return false;
            }

        }

        if(size!=null&&size!=""){
            var daxiao = $("#daxiao .items .xz").text();
            if(daxiao==""){
                alert("请选择"+size);
                return false;
            }
        }


        var id = $("#optype").val();
        $(".box").css("display", "none");
        $(".wfj11_013_bottoms").css("display", "block");
        $(".bottom_button1").css("display", "block");
        $(".bottom_button2").css("display", "block");
        if(id=="shopcart"){

            ajaxCxpcart();

            if(length>0&&cxpcart=="0"){
                $(".alert_new_").show();
                $(".alert_new").show();
                return false;
            }
            if(pricetype=="1"){
                ajaxShopcart(ppid,stardard,"",$("#shuliang").val());
            }else{
                putShopCar();
            }
            $(".box").css("display", "none");
            $(".wfj11_013_bottoms").css("display", "block");
            $("body,.wfj11_013").css("overflow", "auto");

        }else if(id=="buynow"){
            if(length>0&&$("#ptppid").val()==''){
                $(".alert_new_").show();
                $(".alert_new").show();
                return false;

            }

            if(pos=="1"){
                //生成收款码goodsName quality price
                var goodsname = $(".wfj11_013_details_name").text();
                var quality = $("#shuliang").val();

                document.location.href = path+"ea/assicode/ea_genDpJieSunCode.jspa?goodsName="+goodsname+"&quality="+quality+"&price="+pprice+"&companyId="+companyId+"&ppid="+ppid;

                return;
            }


            gm();
        }
    });
	/*$(".confirmop").click(o.selected);*/
    // 关闭框
    $(".box .cover").click(o.selected);

    $(".box .sback").click(o.selected);

    // 数量选择
    var old;
    var novo;
    $(".box .increase").click(function() {

        old = $(this).prev("h5").html();
        // alert(old)
        if (parseInt(old) == "99") {
            novo = old;
        } else {
            novo = parseInt(old) + 1;

        }
        $(this).prev("h5").html(novo);
        $("#shuliang").val(novo);

    });

    $(".box .decrease").click(function() {

        old = $(this).next("h5").html();
        if (parseInt(old) == "0") {
            novo = old;
        } else {
            novo = parseInt(old) - 1;

        }
        $(this).next("h5").html(novo);
        $("#shuliang").val(novo);
    });

    // 判断输入数量
    $("#shuliang").keyup(function() {
        var num = $("#shuliang").val();
        if (num != '' && (!/^(\+|-)?\d+$/.test(num) || num <= 0)) {
            alert("数量只能为正整数");
            $("#shuliang").val(1);
            return;
        }
		/*	if (num > parseInt(shuliang)) {
		 alert("购买数量不能超过库存");
		 $("#shuliang").val(shuliang);
		 return;
		 }*/

    });


    //外部的加入购物车操作
	/*$(".jr").click(function() {
	 if(supername=="17310850569"){
	 alert("请联系工作人员，确认一下您的邀请人");
	 return;
	 }

	 stardard = $(".wfj11_013_choose ul").find("li").eq(0).text();
	 if(((size!=null&&size!="")||(color!=null&&color!=""))&&(stardard.indexOf("规格参数")!=-1&&stardard.indexOf("已选择")==-1)){

	 $(".box").css("display", "block");
	 $(".wfj11_013_bottoms").css("display", "none");
	 $("body,.wfj11_013").css("overflow", "hidden");

	 $(".bottom_button1").css("display", "none");
	 $(".bottom_button2").css("display", "block");


	 }else{


	 putShopCar();

	 }



	 });
	 */

    //尺码页面里的加入购物车
    $(".jrl").click(function() {

        if(putShopCar()==1){
            return;
        }

        $(".box").css("display", "none");
        $(".wfj11_013_bottoms").css("display", "block");
        $("body,.wfj11_013").css("overflow", "auto");


    });

    $(".jrl").click(o.selected);



    //点击颜色跳转不同图片
    $(".items label").click(function() {

        $(this).parent().find("label").css({
            "background" : "#e4e4e4",
            "color" : "#000"
        });
        $(this).parent().find("label").removeClass("xz");

        $(this).css({
            "background" : "#f74c31",
        });
        $(this).addClass("xz");

        var s = $(this).parents("li").attr("id");
        if (s == "yanse") {
            var imgurl = $(this).next("input").val();
            if (imgurl != "") {
                $(".summary .img").find("img").attr("src", path + imgurl);

            }
        }

    });


	/*$(".wfj11_013_choose2").click(function(){
	 $(this).parent().attr("href",basePath+"/ea/productslaunch/ea_PromotionsDetail.jspa?companyId="+companyId+"&ppId="+ppid+"&count="+$("#shuliang").val());
	 });*/


    //滚动显示详情

    $(".wfj11_013_auto").scroll(function(){

        if(!$(".sctop").is(":hidden")){
            var bod=parseInt($("body").height()-$(".sctop").height());

            if($(".sctop").offset().top<bod){

                $("div.wfj11_013_referral_con").slideDown();
                $(".013_ccgd").css("display","none");
                $(".sctop").css("display","none");
                // 查询详情

                var url = path+"ea/wfjshop/sajax_ea_ajaxFindProDetail.jspa";
                $.ajax({
                    url:url,
                    type:"get",
                    async:false,
                    dataType:"json",
                    data:{
                        goodsid:goodsid
                    },
                    success:function(data){
                        var member = eval("("+data+")");
                        var functionlist = member.functionlist;
                        var maplist = member.maplist;
                        var htmlstring = "";
                        for ( var i = 0; i < functionlist.length; i++) {
                            //htmlstring+="<ul><li><h2>"+functionlist[i].name+"</h2></li></ul>";
                            htmlstring+="<ul id=\"small\"><li>"+maplist[functionlist[i].gfid].replace(/此处添加文字描述/g,"")+"</li></ul>";

                        }

                        $(".wfj11_013_referral_con").append(htmlstring);


                        $(".wfj11_013_referral").find("img").load(function() {
                            if ($(this).width() > $(window).width()) {
                                $(this).attr("style", "width:100%;text-align:center;");
                            }
                        });


                    },error:function(data){

                        alert("获取详情失败");
                    }

                });

            }
        }

    });


    //有无评论
    var com_numbel = $(".com_head span");
    if( com_numbel.text() == "0"){

        $(".wfj11_013_comment .com_head").html("暂无评论（<span>0</span>）");
        $(".wfj11_013_comment .com_con,.wfj11_013_comment .com_foot").css("display","none");
        $(".wfj11_013_referral").css("margin-top","0");

    }
	/*2016.10.26*/
    var ptstandard='';
    var ptppid='';
    var id='';//选择判断
    $(".wfj11_013_choose2").click(function(){
        $(".alert_new_").show();
        $(".alert_new").show();
    });
    $(".alert_new_").click(function(){
        $(".alert_new_").hide();
        $(".alert_new").hide();
        $(".box2 .choose_size2").hide();
    });
    $(".alert_new .top h5 #close").click(function(){
        $(".alert_new_").hide();
        $(".alert_new").hide();
    });
    $(".shop_new .mil .left i").click(function(){
        $(this).toggleClass("gou");
        if(!$(this).hasClass("gou")){
            $(this).find("input[class='ptstandard']").val("");
            $(this).parent().siblings().find(".txt p").eq(1).find("span").text("");
        }
    });
    $(".shop_new .mil .left i").click(function(){
        if($(this).hasClass("gou")){
            id=$(this).parent().parent().attr("id");
            ptalert(this);
        }
    });
    $(".shop_new .mil .right .txt").click(function(){
        id=$(this).parent().parent().attr("id");
        var obj=$(this).parent().siblings().find("i");
        ptalert(obj);
    });


    //赠品规格确认
    $(".ptbutton").click(function(){
        var pt='';
        if($(".con_control2").find(".xz").length>0){
            var temp=$(".color").find(".xz").text()+" "+$(".set-meal").find(".xz").text();
            pt=temp+",,";
            $(".shop_new").find("#"+id).find("input[class='ptstandard']").val(pt.substring(0,pt.indexOf(",,")));
            $(".shop_new").find("#"+id).find(".right .txt p").eq(1).find("span").text(pt.substring(0,pt.indexOf(",,")));
            $(".box2 .choose_size2").hide();
            $(".alert_new").show();
        }else{
            alert("请选择规格！");
        }
    });
    $(".box2 .summary2 .sback").click(function(){
        $(".box2 .choose_size2").hide();
        $(".alert_new").show();
    });

    //赠品选择属性
    $(document).on("click",".color label",function(){
        $(".box2 .summary2 .stock p span:nth-child(1)").text("添加");
        $(".box2 .summary2 .sback ").css("right","10px");
        $(".box2 .summary2 .stock p span").css("padding","0");
        var col=$(this).text();
        $("#color").text('“'+col+'”');
    });
    $(document).on("click",".set-meal label",function(){
        $(".box2 .summary2 .stock p span:nth-child(1)").text("添加");
        $(".box2 .summary2 .sback ").css("right","10px");
        $(".box2 .summary2 .stock p span").css("padding","0");
        var set=$(this).text();
        $("#set-meal").text('“'+set+'”');
    });
    //赠品选择后确认
    $(".cxqr").click(function(){
        if($(".shop_new .mil .left .gou").length>0){
            var ptprice=0;
            var txt=$(".wfj11_013_peice").text();
            txt=txt.substring(1,txt.length);
            var price=parseFloat(txt*$("#shuliang").val());
            $(".shop_new .mil .left .gou").find("input[class='price']").each(function(){
                ptprice+=parseFloat($(this).val());
            });
            if(ptprice>price){
                alert("选择的赠品总价格不能超过主产品价格");
            }else{
                var temp = "";
                var temp_ps = "";
                var str="已选择赠品：";
                $(".shop_new .mil .left .gou").each(function(){
                    temp+=$(this).find("input[class='ppId']").val()+",";
                    var tp=$(this).find("input[class='ptstandard']").val();
                    tp=(tp==''?"规格暂无":tp);
                    temp_ps+=tp+",,";
                });
                $("#ptppid").val(temp);
                $("#ptstandard").val(temp_ps);
                $(".sales").text(str);
                $(".alert_new").hide();
                $(".alert_new_").hide();
                stardard = $(".wfj11_013_choose ul").find("li").eq(0).text();

                var optype = $("#optype").val();
                if(optype=="buynow"){

                    gm();

                }else{
                    cxpcart = "1";
                    putShopCar();


                }



            }
        }else{
            alert("请选择赠品！");
        }
    });


    $(".shop_new").css("height",$(window).height()-$(".top").height()+"px");
    $(".shop_new").css("padding-top","47px");
    $(".shop_new").css("padding-bottom",$(".cxqr").height()+10+"px");

});
//弹出赠品规格
function ptalert(obj){
    var ppId=$(obj).find("input[class='ppId']").val()
    var goodsId=$(obj).find("input[class='goodsId']").val();
    var url=basePath+"/ea/productslaunch/sajax_ea_getAttr.jspa?ppId="+ppId+"&goodsId="+goodsId;
    $.ajax({
        url :url,
        type: "get",
        async: false,
        success: function cbf(data){
            var member=eval("("+data+")");
            var list=member.attrlist;
            var count=member.count;
            var str1="";
            var str2="";
            var f1="";
            var f2="";
            if(count>0){
                if(list!=null&&list.length>0){
                    $(".pricer").empty();
                    var product=list[0];
                    $("#image").attr("src",basePath+product[3]);
                    $(".pricer").append("&yen;"+product[2]+"<span> ( 库存"+product[7]+"件 ) </span>");
                }

                $(".color").empty();
                $(".set-meal").empty();
                for(var i=0;i<list.length;i++){
                    var product=list[i];
                    if(product[9]=='1'){
                        f1=product[10];
                        str1+="<label>"+product[8]+"</label>";
                    }else{
                        f2=product[10];
                        str2+="<label>"+product[8]+"</label>";
                    }
                }
                $("#dx").find("h2").text(f1);
                $(".color").append(str1);
                $("#ys").find("h2").text(f2);
                $(".set-meal").append(str2);
                $(".box2 .choose_size2").show();
                $(".alert_new").hide();
            }
        },
        error: function(){
            alert("产品规格加载失败");
        }
    });
}

//加入购物车
function  putShopCar(){
    if (type == "个人会员" || type == "公司会员"||type == "佣金分配代理类别" ) {
        alert("会员产品不能加入购物车购买，请点击立即购买！");
        return false;
    }

    if (pprice=="0") {
        alert("0元产品不能加入购物车，请点击立即购买！");
        return false;
    }

    //xgb
    if (type.indexOf("包月计时")>=0 || type.indexOf("包年计时")>=0 || type.indexOf("驾校预约")>=0) {
        alert("计时产品不能加入购物车购买,请点击立即购买!");
        return false;
    }

    var str = "";

    if(color!=null&&color!=""){

        var yanse = $("#yanse .items .xz").text();
        if(yanse==""){
            alert("请选择"+color);
            return 1;
        }
        str= color+":"+yanse;
    }

    if(size!=null&&size!=""){
        var daxiao = $("#daxiao .items .xz").text();
        if(daxiao==""){
            alert("请选择"+size);
            return 1;
        }
        if(color!=null&&color!=""){
            str+=","+size+":"+daxiao;
        }else{
            str= size+":"+daxiao;
        }
    }
    var stardard = str;
    var shuliang = $("#shuliang").val();
    ajaxCxpcart();
    if(cxpcart!="0"){
        $("#ptppid").val("");
    }
    var ulp = path + "/ea/wfjshop/sajax_ea_setcity.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async:false,
        dataType : "json",
        data:{
            ppid:ppid,
            stardard:(stardard==""?"默认规格":stardard),
            itemNum:shuliang,
            ptppid:	$("#ptppid").val(),
            ptstandard: $("#ptstandard").val(),
            pos:pos,
            posNum:posNum
        },
        success : function(data) {
            var json = eval('(' + data + ')');
            if(json.login=="login"){

                document.location.href = path
                    + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;

            }
            $("#btn_gwc").find("p").text("成功加入购物车");
            if (json.sect == '1') {
                $("#ptppid").val("");
                cxpcart = "1";
                if(!($("#btn_gwc").is(":animated"))){
                    $("#btn_gwc").show();
                    setTimeout(function () {
                        $("#btn_gwc").animate({
                            opacity: "0",
                        },1000,function () {
                            $("#btn_gwc").css("opacity","1");
                            $("#btn_gwc").hide();
                        })
                    }, 1000);
                }

                //alert("成功加入购物车");
            } else {
                if(!($("#btn_gwc").is(":animated"))){
                    $("#btn_gwc").show();
                    setTimeout(function () {
                        $("#btn_gwc").animate({
                            opacity: "0",
                        },1000,function () {
                            $("#btn_gwc").css("opacity","1");
                            $("#btn_gwc").hide();
                        })
                    }, 1000);
                }

                //alert("已加入购物车");
            }

        }
    });

}

function jian() {

    if ($("#shuliang").val() == "") {
        $("#shuliang").val(1);
    }
    if ((parseInt($("#shuliang").val()) - 1) < 1) {
        alert("最少购买一个");
        return;

    }

    var d = $("#shuliang").val();
    $("#shuliang").val(d - 1);
    $(".box .decrease").next("h5").html(d - 1);

}
function jia()

{
    if ($("#shuliang").val() == "") {
        $("#shuliang").val(1);
    }
    var str = $(".wfj11_013_peice").text()
    var n=str.substr(1,2);
    if(n=="0"){

        alert("只能购买一个");
        return
    }
	/*	if ((parseInt($("#shuliang").val()) + 1) > shuliang) {
	 alert("数量不能大于产品数量");
	 return;
	 }*/

    var d = parseInt($("#shuliang").val());
    $("#shuliang").val(d + 1);


    $(".box .decrease").next("h5").html(d + 1);
}
var temp="";
var temp_ps="";
function gm() {

    temp="";
    temp_ps="";
    if ($("#shuliang").val() == "") {
        $("#shuliang").val(1);
    }
    if(type.indexOf("驾校预约")>=0){
        document.location.href=basePath+"/ea/mappointment/ea_jumpToBuy.jspa?ppk.ppID="+ppid;
        return;
    }

    // 验证代理平台是否重复购买
    if(ppid =="p20170220ZVZR76B88M0000000018"||ppid =="p20170220ZVZR76B88M0000000019"||ppid=="p20170220ZVZR76B88M0000000020"){
        var url = path + "/ea/wfjshop/sajax_ea_yanzhengzg.jspa";
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            data : {
                ppids : ppid,
            },
            success : function(data) {

                var mss = eval("(" + data + ")");
                var productpp=mss.productp;
                if (productpp == "login") {
                    document.location.href = path
                        + "page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
                if (productpp== "productp") {
                    alert("已经购买过同一级别的代理资格");
                }
                if (productpp== "productpt") {
                    document.location.href = path + "/ea/wfjshop/ea_gm.jspa?ppid="
                        + ppid + "&ccompanyId=" + ccompanyId + "&count=1&ptppid="+temp+"&ptstandard="+temp_ps+"&ppids="+ppids;
                }
            },
            error : function(data) {
                // alert("验证失败");
            }
        });
        return ;
    }
    if(ppid=="p20170220ZVZR76B88M0000000016"||ppid=="p20170220ZVZR76B88M0000000017"){
        var url = path + "/ea/wfjshop/sajax_ea_yanzhengzg.jspa";
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            data : {
                ppids : ppid,
            },
            success : function(data) {

                var mss = eval("(" + data + ")");
                var productpp=mss.productp;
                if (productpp == "login") {
                    document.location.href = path
                        + "page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
                if (productpp== "productp") {
                    alert("已经购买过相同的资格");
                }
                if (productpp== "productpt") {
                    document.location.href = path + "/ea/wfjshop/ea_gm.jspa?ppid="
                        + ppid + "&ccompanyId=" + ccompanyId + "&count=1&ptppid="+temp+"&ptstandard="+temp_ps+"&ppids="+ppids;
                }
            },
            error : function(data) {
                // alert("验证失败");
            }
        });
        return ;
    }
    if (length > 0) {
        $(".shop_new .mil .left .gou").each(function () {
            temp += $(this).find("input[class='ppId']").val() + ",";
            var tp = $(this).find("input[class='ptstandard']").val();
            tp = (tp == '' ? "规格暂无" : tp);
            temp_ps += tp + ",,";
        });
        $("#ptppid").val(temp);
        $("#ptstandard").val(temp_ps);
    }

    if (type == "个人会员" || type == "公司会员") {
        joinVip(ppid, ccompanyId);
    } else {
        var url = path + "/ea/wfjshop/sajax_ea_validateOrdiGoods.jspa";
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            async:false,
            data : {
                ppid : ppid,
                goodsid:goodsid,
                companyId:companyId,
                ccompanyId:ccompanyId,
            },
            success : function(data) {
                var m = eval("(" + data + ")");
                var login = m.login;
                if(posNum==null||posNum=="") {
                    if (login == "login") {
                        document.location.href = path
                            + "page/WFJClient/NewLogin.jsp?loginPage=login";
                        return;
                    }

                    var result = m.result;
                    if (result != "") {

                        alert(result);
                        if (result == "此产品限会员购买请立即升级") {
                            document.location.href = path + "/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform";
                        }
                        return false;

                    }
                }

                var str = "";

                if (color != null && color != "") {

                    var yanse = $("#yanse .items .xz").text();
                    if (yanse == "") {
                        alert("请选择" + color);
                        return;
                    }
                    str = color + ":" + yanse;
                }

                if (size != null && size != "") {
                    var daxiao = $("#daxiao .items .xz").text();
                    if (daxiao == "") {
                        alert("请选择" + size);
                        return;
                    }
                    if (color != null && color != "") {
                        str += "," + size + ":" + daxiao;
                    } else {
                        str = size + ":" + daxiao;
                    }
                }

                if (str != "") {
                    $("#standards").val(str);
                    stardard = str;
                }


                if(posNum!=null&&posNum!="") {

                    var  shuliang = $("#shuliang").val();
                    var  totalMoney = (Number(price)*Number(shuliang)).toFixed(2);
                    var directUrl = "";
                    //生成订单号
                    $.ajax({
                        url:basePath+"/ea/sm/sajax_ea_getJumBycomID.jspa?d="+new Date(),
                        type:"get",
                        async: false,
                        dataType : "json",
                        data:{
                            comID:companyId,
                            ppid:ppid,
                            posNum:posNum,
                            stardard:stardard,
                            price:price,
                            itemNum:shuliang

                        },
                        success:function(data){
                            var mb=eval("("+data+")");
                            var jum=mb.jum;
                            if(type.indexOf("学员报名")!=-1){

                                var goodsname = $(".wfj11_013_details_name").text();
                                var photo = $(".wfj11_013_top_img").find(".item").eq(0).find("img").attr("src").replace(basePath,"");

                                directUrl =basePath+"/st/enroll/ea_getEnroll.jspa?ppid="+ppid+"&companyID="+companyId+"&photo="+photo+"&price="+price+"&goodsID="+goodsid+"&ccompanyId="+ccompanyId+"&licenceType="+type
                                    +"&categoryName="+categoryName+"&categoryId="+categoryId+"&goodsName="+goodsname+"&ptppid=" + temp + "&ptstandard=" + temp_ps;


                            }else {
                                directUrl = basePath + "/ea/wfjshop/ea_gm.jspa?ppid="
                                    + ppid + "&ccompanyId=" + ccompanyId + "&count="+shuliang+"&ptppid=" + temp + "&ptstandard=" + temp_ps + "&ppids=" + ppids+"&stardard="+stardard;

                            }

                            document.location.href = basePath+"page/ea/main/marketing/supermarket/selfservice/payMode.jsp?directUrl="+encodeURIComponent(directUrl)+"&journalNum="+jum+"&totalMoney="+totalMoney+"&totalNum="+shuliang
                                +"&type="+type+"&posNum="+posNum+"&fh=2&comID="+companyId+"&companyName="+indus;


                        },error:function(data){
                            console.log("生成单号");
                        }
                    });

                    return false;
                }
                $("#tj").click();

            },
            error : function(data) {
                //alert("验证失败");
            }

        });







    }

}

// 加入会员

function joinVip(ppid, ccompanyId) {
    var url = path + "/ea/wfjshop/sajax_ea_validatecanBuy.jspa";
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        data : {
            ppid : ppid,
            ccompanyId : ccompanyId
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            var mk = m.mk;
            var login = m.login;
            if (login == "login") {
                document.location.href = path
                    + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            }
            if (result != "success") {

                alert(result);
                return;
            } else {
                document.location.href = path + "/ea/wfjshop/ea_gm.jspa?ppid="
                    + ppid + "&ccompanyId=" + ccompanyId + "&count=1&mk="+mk+"&ptppid="+temp+"&ptstandard="+temp_ps+"&ppids"+ppids;
            }
        },
        error : function(data) {
            //alert("验证失败");
        }

    });

}

function promotionProduct(obj){
    ppId = $("#promotionID").val();
    user = $("#user").val();
    var url=basePath+"ea/productslaunch/ea_queryPromotionProduct.jspa?productPackaging.ppID="+ppId+"&ppId="+ppid+"&user="+user;
    document.location.href = url;
}
//预算选择产品
function sel(){
    budget="";
    var parms=new Array();
    parms.push("cashierBillsID="+cashierBillsID);
    parms.push("&user="+user);
    parms.push("&companyId="+companyId);
    parms.push("&ppId="+ppid);
    parms.push("&goodsBillsID="+goodsBillsID);
    window.location.href=path+"ea/wfjbudget/ea_chancePro.jspa?"+parms.join("");
}

function ajaxCxpcart(){
    var url = path + "/ea/wfjshop/sajax_ea_ajaxCxpcart.jspa";
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        async:false,
        data : {
            ppid : ppid,
            posNum:posNum
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            cxpcart = m.cxpcart;
            var login = m.login;
            if (login == "login") {
                document.location.href = path
                    + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            }

        },
        error : function(data) {
            // alert("验证失败");
        }

    });

}


//批发加入购物车
function ajaxShopcart(ppid,stardard,ftStr,tjNum){

    var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxAddShoppingCart.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "POST",
        async: false,
        data: {
            "pfscShoppingCart.ppid": ppid,//产品id
            "pfscShoppingCart.standard": stardard,//规格
            "pfscShoppingCart.ftStr": ftStr,//选中副图
            "pfscShoppingCart.tjNum": tjNum,//添加数量
        },
        dataType: "json",
        success: function (data) {
            var json = eval('(' + data + ')');
            if(json.login=="login"){

                document.location.href = path
                    + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;

            }
            $("#btn_gwc").find("p").text("成功加入购物车");
            if(!($("#btn_gwc").is(":animated"))){
                $("#btn_gwc").show();
                setTimeout(function () {
                    $("#btn_gwc").animate({
                        opacity: "0",
                    },1000,function () {
                        $("#btn_gwc").css("opacity","1");
                        $("#btn_gwc").hide();
                    })
                }, 1000);
            }
        },
        error: function (data) {
            alert("数据获取失败！");
        }
    });

}
//联系商家
function ioschat(obj){
    var url= "func=" + 'ioschat';
    params={'sccid':$(obj).children().eq(0).val(),
        'account' :$(obj).children().eq(1).val(),
        'username' :$(obj).children().eq(2).val()};
    for(var i in params){
        url = url + "&" + i + "=" + params[i];
    }
    window.webkit.messageHandlers.Native.postMessage(url);

}
function AndroidtoChat(obj){
    var userId= $(obj).children().eq(1).val();
    var sccId= $(obj).children().eq(0).val();
    var userPickeName= $(obj).children().eq(2).val();

    Android.toChat(userId,sccId,userPickeName);
}

//判断是否是终端机器
function isExistPosNum(posNum){
    var url = path + "ea/smg/sajax_sm_isExistPosNum.jspa";
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        async:false,
        data : {
            posNum:posNum
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            if(result!="0"){
                posNum = "";
            }

        },
        error : function(data) {
            // alert("验证失败");
        }

    });
    return posNum;
}
//收藏商品
function collectGoods(ppid,pricetype){
    var url = path + "/ea/wfjshop/sajax_ea_collectGoods.jspa?date="
        + new Date().toLocaleString();
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        async:true,
        data : {
            ppid:ppid,
            pricetype:pricetype
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            if(m.login=="login"){

                document.location.href = path
                    + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;

            }
            var result = m.result;
            if(result=="0"){
                $("#btn_gwc").find("p").text("取消收藏成功");
                $(".scsp").attr("src",basePath+"images/WFJClient/PersonalJoining/xwsc.png");
                if(!($("#btn_gwc").is(":animated"))){


                    $("#btn_gwc").show();
                    setTimeout(function () {
                        $("#btn_gwc").animate({
                            opacity: "0",
                        },1000,function () {
                            $("#btn_gwc").css("opacity","1");
                            $("#btn_gwc").hide();
                        })
                    }, 1000);
                }
            }else{
                $("#btn_gwc").find("p").text("收藏成功");
                $(".scsp").attr("src",basePath+"images/WFJClient/PersonalJoining/xsc.png");
                if(!($("#btn_gwc").is(":animated"))){
                    $("#btn_gwc").show();
                    setTimeout(function () {
                        $("#btn_gwc").animate({
                            opacity: "0",
                        },1000,function () {
                            $("#btn_gwc").css("opacity","1");
                            $("#btn_gwc").hide();
                        })
                    }, 1000);
                }

            }

        },
        error : function(data) {
            // alert("验证失败");
        }

    });


}