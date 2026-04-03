$(function(){
    connum();
    //认证
	$(".rz").click(function(){

      /*if($(this).attr("class").indexOf("finish")==-1){

          //说明没有认证去认证
              document.location.href = basePath+"ea/mycenter/ea_getInfo.jspa";
      }else{
          //查看信息

          document.location.href = basePath+"ea/mycenter/ea_getInfo.jspa?op=view";

      }*/

        document.location.href = basePath+"ea/mycenter/ea_findMyAuthentication.jspa";
    });
    //个人发布
    $(".grfb").click(function(){
        $(".zx-div").hide();
      document.location.href = basePath+"ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02";
    });
    //公司发布
    $(".gsfb").click(function(){
        $(".zx-div").hide();
        document.location.href = basePath+"page/WFJClient/pc/5l5C/selectCompany.jsp?sccId="+sccid+"&bd=zx";

    });
    $(".close-zx").click(function(){
        $(".zx-div").hide();
    });

	
});

//发布产品
function publishproduct() {
    //document.location.href = basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?sccId="+sccid+"&user="+user+"&companyId="+companyID+"&sys=wdhy";

    document.location.href = basePath + "/ea/productslaunch/ea_productsManage.jspa?user=" + user;
}
//基本信息
function baseInfo(){
    /*document.location.href = basePath+"ea/mycenter/ea_getBaseInfo.jspa";*/
    document.location.href = basePath+"page/WFJClient/pc/my/myRelated.jsp?number=1";
}
//二维码
function mycode(){
    document.location.href = basePath+"ea/mycenter/ea_getMyCode.jspa?sccid="+sccid;
}

//咨询发布选择个人或者公司发不
function zxselect(){

    var url = basePath + "ea/android/sajax_ea_findConpany.jspa";
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        aysnc: false,
        data: {
            sccId: sccid
        },
        success: function (data) {
            var comlist = data.company;
            if (comlist.length > 0) {
                $(".zx-div").show();
            } else {
                document.location.href = basePath+"ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02";
            }


        },
        error: function (data) {

        }
    })
}


function connum(){

    var url = basePath + "/ea/consult/sajax_ea_getConsultsNum.jspa";
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        aysnc: true,
        success: function (data) {
            var m = eval("("+data+")");
            var num = m.num;
            $(".tip-span").text(num);

        },
        error: function (data) {

        }
    })

}
