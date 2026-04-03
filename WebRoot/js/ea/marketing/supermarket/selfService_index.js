$(function(){
           // $(".barcode").focus();
           // $(".barcode").blur(function(){
           //
           //     $(".barcode").focus();
           // });
    $("body").click(function(){
        if($(".alert_hand-pd_").is(':hidden')) {
            $(".barcode").focus();
        }

    });
      //直接退出
    $(".exit").click(function(){

       document.location.href = basePath+"ea/sm/ea_backLogin.jspa?cs=1";

    });

    //再打印
    $(".printAgain").click(function(){
        var times = $("#printNum").val();

        if(times==""||times=="0"){
            times=1;
        }else{
          if(Number(times)>5){
              var shot = showTime();
              $(".alert_weigh_").show();
              $(".tipcon").text("最多打印5份");
              $("#printNum").val("1");
              shot;
            return false;
          }
        }
         printChangeInfo(Number(times));
        document.location.href = basePath+"ea/sm/ea_backLogin.jspa?cs=1";
    });

    //确定
    $("#btn").click(function(){
        var barcode = $(".barcode").val();
        if(barcode!=""&&barcode.length<=13){
            $(".barcode").val("");
            firstSearch();
            document.location.href = basePath+"ea/sm/ea_qdlist.jspa?barCode="+barcode;
        }

    });

    //中间大图标
    $(".cen").click(function(){

        document.location.href = basePath+"ea/sm/ea_qdlist.jspa";

    });


	/*交接班*/
	/*交接班登出*/
    $(".alert_address .btn input").click(function () {
        $(".alert_hand-pd_").show();
        $(".alert_hand-pd-1").show();
    });
	/*交接班登录弹框关闭*/
    $(".alert_hand img").click(function () {
        $(".alert_hand_").hide();
        $("#softkey").html("");
    });
	/*交接班完成打印关闭*/
    $(".alert_hand-pd img").click(function () {
        $(".alert_hand-pd_").hide();
        $("#softkey").html("");
    });



    document.onkeydown = function(evt){//捕捉回车
        evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
        var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
        if (key == 13) { //判断是否是回车事件。
            console.log(key);
            console.log($(".barcode").val());
            var barcode = $(".barcode").val();
            if(barcode!=""){
                $(".barcode").val("");
                if(barcode.length==20){
                    checkScardJiFen(barcode)
                    return false;
                }
                if(barcode.length<=13){
                    firstSearch();
                    document.location.href = basePath+"ea/sm/ea_qdlist.jspa?barCode="+barcode;

                }

            }

            return false;
        }
    }
});


//核对购物卡积分
function checkScardJiFen(scard){

    var ulp = basePath
        + "/ea/sm/sajax_ea_checkScardJiFen.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : false,
        dataType : "json",
        data:{
            scard:scard
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var result = member.result;
            if(result=="0") {
                var sccId = member.sccId;
                var staffName = member.staffName;
                var account = member.account;
                var staffid = member.staffId;
                document.location.href = basePath + "ea/bonuspoints/ea_getbpDetail.jspa?account="+account+"&sccid="+sccId+"&khd=0&flag=&staffid="+staffid+"&staffName="+staffName+"&platType=screen";

            }else{
                var shot = showTime();
                $(".alert_weigh_").show();
                $(".tipcon").text("此卡无效可以联系工作人员");
                shot;
            }


        },
        error : function(data) {
            console.log("查询支付结果失败");
        }
    });
}
function test(obj){
    var top = $(obj).offset().top+70;
    var left = $(obj).offset().left;

    if($(obj).attr("id")=="psd"||$(obj).attr("id")=="printNum"){
       /* var top = $(obj).offset().top+500;
        left = $(obj).offset().left-200;*/

    }


    VirtualKeyboard.toggle($(obj).attr("id"), 'softkey');
    $("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
  /*  $("#softkey").attr("style","position:absolute;z-index:99;top:"+top+"px;left:"+left+"px;");*/

}
//设定倒数秒数
var t = 4;
function showTime(){
    t -= 1;
    $(".alert_weigh p span").text(t);

    //每秒执行一次,showTime()
    var s = setTimeout("showTime()",1000);

    if(t==0){
        t = 4;
        $(".alert_weigh_").hide();
        clearTimeout(s);
        $(".alert_weigh p span").text(t);
    }
	/*商品称重弹框*/
    $("#confirm").click(function () {
        $(".alert_weigh_").hide();
        clearTimeout(s);
        t = 4;
        $(".alert_weigh p span").text(t);
    });
}

//返回登陆
function backLogin(){
    $(".alert_hand_").show();

    var url = basePath + "ea/sm/sajax_ea_getChangeWorkInfo.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        success : function(data) {
            var m = eval("("+data+")");
            $(".jjb li").find(".pos").text(m.pos);
            $(".jjb li").find(".staffcode").text(m.staffcode);
            $(".jjb li").find(".companyName").text(m.companyName);
            $(".jjb li").find(".companyAddress").text(m.companyAddress);
        },
        error:function(data){

        }
    });

}

function check(form) {
	/*交接班登录如果密码为空*/
    var txt = $(".alert_hand-pd .text input").val();
    if (txt == "") {
        var shot = showTime();
        $(".alert_weigh_").show();
        $(".tipcon").text("请填写登陆密码");
        shot;
        return false;
    }else {
        var url = basePath + "ea/sm/sajax_ea_checkChangeUser.jspa";
        $.ajax({
            url : url,
            type : "get",
            async : false,
            dataType : "json",
            data:{
                psw:txt
            },
            success : function(data) {
                var m = eval("("+data+")");
                var result = m.result;
                if(result=="2"){
                    var shot = showTime();
                    $(".alert_weigh_").show();
                    $(".tipcon").text("登陆密码错误");
                    $(".alert_hand-pd .text input").val("");
                    $(".alert_hand-pd .text input").focus();
                    shot;
                }else{
                    //打印
                    printChangeInfo(1);
                    $(".alert_hand-pd_").show();
                    $(".alert_hand-pd-2").show();
                    $("#softkey").html("");
                }
            },
            error:function(data){

            }
        });


        return false;
    }
}
//打印交班信息小票
function printChangeInfo(times){

    for(var i = 0;i<times;i++){
        myPrint();
    }


}

var LODOP; //声明为全局变量
function myPrint() {
    CreatePrintPage();
    LODOP.PRINT();
}
function CreatePrintPage() {

    LODOP=getLodop();
    LODOP.PRINT_INIT("交接班小票");
    LODOP.SET_PRINT_PAGESIZE(3,500,0.2,"");
    LODOP.SET_PRINT_STYLE("FontSize",9);
    LODOP.SET_PRINT_STYLE("Bold",1);


    LODOP.ADD_PRINT_TEXT(15,70,180,50,"交接班");



    var hPos=50;//小票上边距
    var pageWidth=100;//小票宽度
    var  rowHeight=15;//小票行距

    LODOP.SET_PRINT_STYLE("FontSize",8);

    LODOP.ADD_PRINT_LINE(hPos,5, hPos, 500,2,0);
    hPos+=rowHeight;
    LODOP.SET_PRINT_STYLE("FontSize",8);
    LODOP.SET_PRINT_STYLE("Bold",0);
    var companyName= $(".jjb").find(".companyName").text();
    var  qcom = "";
    var hcom = "";
    if(companyName.length>12){
        qcom = companyName.substring(0,12);
        hcom = companyName.substring(12,companyName.length);
    }else{
       qcom = companyName;
    }
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"店名："+qcom);
    hPos+=rowHeight;
    if(hcom!=""){
        LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,hcom);
        hPos+=rowHeight;
    }
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"收银员："+$(".jjb").find(".staffcode").text());
    hPos+=rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"POS终端:"+$(".jjb").find(".pos").text());
    hPos+=rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"交班时间:"+$(".jjb").find(".changeDate").text());
    hPos+=rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos+5,5,180,50,"交班地址:"+$(".jjb").find(".companyAddress").text());
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    hPos+=rowHeight;
    LODOP.ADD_PRINT_LINE(hPos,5, hPos, 500,2,0);



}


//记录首页进入
function firstSearch(){
        if(localStorage!=null) {
            localStorage.setItem("firstsearch", "index");
        }

}