
$(function() {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    $(".d_r_site").click(function () {
        if (isAndroid) {
            Android.callgetRoundLocal();
        } else if (isiOS) {
            var url = "func=" + 'iosMapaddress';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    });


    //行业
    $(".industryType").click(function(){
        $(".hyfl").show();
        $("#sel").text("请选择行业");
        getIndustry("");

    });

    //行业返回
    $(".hyback").click(function(){
        $(".hyfl").hide();
    });

    //查询子行业
    $(document).on("click",".hyfl li",function(){

        var codeID = $(this).attr("id");
        if(codeID!="") {
            $("#selid").val(codeID);
        }
        var codeValue = $(this).text();
        var codePID = $(this).attr("codepid-data");
        var codename = $(this).attr("typename-data");

        if($("#sel").text()=="请选择行业"){
            $("#sel").html("<span class='"+codePID+"' >"+codename+"</span>");
        }else {
            $("#sel").append("<span class='" + codePID + "' >/" + codename + "</span>");
        }

        getIndustry(codeID);
    });
    //按层级回退行业
    $(document).on("click","#sel span",function(){
        var codePID = $(this).attr("class");
        if(codePID!="") {
            $("#sel ." + codePID).nextAll().remove();
            $("#sel ." + codePID).remove();
        }else{
            $("#sel").text("请选择行业");
        }
        getIndustry(codePID);

    });


  //下一步
   $("#next1").click(function () {

       var error = 0;

       $(".p-01 .notnull").each(function(){
           var tip = $(this).attr("data");
           if($.trim($(this).val())==""){

                if(tip=="店铺logo"){
                       if($(this).attr("src").indexOf("pbapply/img_015.png")!=-1){
                           $(".div-p-02").show();
                           $(".div-p-02 p").text(tip+"不能为空");
                           error = 1;
                           return false;
                       }

                   }else{
                       $(".div-p-02").show();
                       $(".div-p-02 p").text(tip+"不能为空");
                       error = 1;
                    return false;
                   }

           }else{
               if(tip=="店铺电话"){
                   if(!checkTel($.trim($(this).val()))){
                       $(".div-p-02").show();
                       $(".div-p-02 p").text("请填写正确的店铺电话号");
                       error = 1;
                       return false;
                   }
                }
           }

       });


  if(error==0) {
      $("#form1").attr("target", "hidden").attr("action", basePath + "ea/merch/ea_saveMaterial.jspa?message=11");
      token = 13;
      document.form1.submit.click();
  }

   })

});


//获取行业
function getIndustry(codePID) {
    $.ajax({
        url: basePath + "/ea/qyrz/sajax_ea_getIndustry.jspa",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            codePID: codePID
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var industryList = member.industryList;
            if (industryList == null || industryList.length == 0) {
                $(".hyfl").hide();
                $(".industryType").val($("#sel").text());
                $(".industryId").val($("#selid").val());
            }
            var html = new Array();
            var obj;
            for (var i = 0; i < industryList.length; i++) {
                obj = industryList[i];
                html.push("<li class='clearfix' id='" + obj.typeId + "' codepid-data='" + obj.typePID + "' typeName-data='" + obj.typeName + "'>");
                html.push("<p>" + obj.typeNum+obj.typeName + "</p>");
                html.push("<p><img src='" + basePath + "/images/scMobile/qyrz/a.png'/></p>");
                html.push("</li>");

            }
            $(".hy").html(html.join(""));
        },
        error: function (data) {
            console.log("获取行业失败");
        }
    });

}


function re_load() {
    if(token) {
        if(organization_type=="2401"){
            document.location.href = basePath+"ea/merch/ea_getApplyCard.jspa?applyParam.out_request_no="+out_request_no+"&companyID="+companyID+"&staffID="+staffID;
        }else{
            document.location.href = basePath+"ea/merch/ea_getApplyLicense.jspa?applyParam.out_request_no="+out_request_no+"&companyID="+companyID+"&staffID="+staffID;
        }
    }
}

/**************************定位获取地址开始************************/
function ios_address(param) {
    var p = param.substring(0, param.indexOf(">"));
    var address = p.substring(0, p.indexOf("<"));
    var jv = p.substring(p.indexOf("<") + 1);
    var j = jv.substring(1, jv.indexOf(","));
    var v = jv.substring(jv.indexOf(",") + 2);
    alert(j+","+v);
    $("#ddaddress").val(address);
    $("#accuracy").val(j);
    $("#dimension").val(v);

}

function a_address(param) {

    var address = param.substring(0, param.indexOf("["));

     var ad = param.substring(param.indexOf("[")+1,param.indexOf("]"));
     var array = ad.split(",");


    var provice = array[0];
    var city =  array[1];
    var divide =  array[2];


    $("#pname").val(provice);
    $("#cityname").val(city);
    $("#adname").val(divide);

    var street = address.replace(provice,"").replace(city,"").replace(divide,"");

    $("#street").val(street);





    var coordinate = param.substring(param.indexOf("]") + 2);

    var jv = coordinate.split(",");

    var j = jv[0];


    var v = jv[1];

    $("#ddaddress").val(address);

    $("#accuracy").val(j);
    $("#dimension").val(v);
}

/**************************定位获取地址结束************************/

function checkTel(mobile) {

    var isMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
    var isPhone = /^(?:(?:0\d{2,3})-)?(?:\d{7,8})(-(?:\d{3,}))?$/;
    //如果为1开头则验证手机号码
    if (mobile.substring(0, 1) == 1) {
        if (!isMobile.exec(mobile) && mobile.length != 11) {
            return false;
        }
    }
    //如果为0开头则验证固定电话号码
    else if (mobile.substring(0, 1) == 0) {
        if (!isPhone.test(mobile)) {

            return false;
        }
    }
    //否则全部不通过
    else {

        return false;
    }
    return true;
}


