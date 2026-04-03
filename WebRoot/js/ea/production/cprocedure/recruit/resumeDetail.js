var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function () {

    //不合适
   $(".li-bhs").click(function(){

       if(state=="04"){

           return false;
       }

       $(".div-del").show();


   })
    //约面试
    $(".li-yms").click(function(){

       if(state=="00"){
           document.location.href = basePath
               + "ea/bidrecruit/ea_getInventInfo.jspa?sccId="+sccId+"&tpId="+tpId+"&resumeID="+resumeID+"&back="+back;

       }





    })




    //取消
    $(".div-del .p-c").eq(0).click(function () {

        $(this).parents(".div-del").hide();

    })

    //确定按钮
    $(".div-del .p-q").click(function () {


      var ulp = basePath
                + "ea/bidrecruit/sajax_ea_setOperate.jspa?sccId="+sccId;



        $.ajax({
            type: "GET",
            url: ulp,
            async: true,
            dataType: "json",
            data: {
                "tpId": tpId,
                state: "04"
            },
            success: function (data) {
                window.location.reload()
            },
            error: function (data) {

            }
        });



    })



});

function backs(){
    window.history.go(-1);
    return false;

  //  document.location.href = basePath+"ea/bidrecruit/ea_getTalentResumeList.jspa?sccId="+sccId+"&back="+back;
}

//打电话

function getPhone(num) {
    if (num != null && num != ""){

        if (isAndroid == true) {
            if (confirm("确定呼叫?")) {
                Android.callPhone(num+"");
            }
        } else if (isiOS == true) {
            if (confirm("确定呼叫?")) {
                var url = "func=" + 'iosCallphone';
                params = {'phoneNum': num};
                for (var i in params) {
                    url = url + "&" + i + "=" + params[i];
                }
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        }
    }else {
        alert("无电话号码");
    }
}

//立即聊天
function sendMes(){
    if(isAndroid==true){

        Android.toChat(retel,sccIdt,name);//手机号
    }else if(isiOS==true){
        var url= "func=" + 'ioschat';
        params={'sccid':sccIdt,
            'account' :retel,
            'username' :name};
        for(var i in params){
            url = url + "&" + i + "=" + params[i];
        }
        window.webkit.messageHandlers.Native.postMessage(url);
    }


}





