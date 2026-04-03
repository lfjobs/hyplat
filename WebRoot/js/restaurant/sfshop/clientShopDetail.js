$(function () {


    //点击选择餐桌号
    $(".tn_act").click(function(){
        if($(".yid").length==0) {
            $(".select_table").show();
        }
    })
    //嵌套页面返回按钮
    $(".nest_back").click(function(){
        $(this).parents(".nest_page").hide();
    })
    //选择餐桌号页面，值改变事件
    $(".table_box").change(function(){
        var n = $(".tn_inp:checked").next().text();
        var cateID = $(".tn_inp:checked").next().next().text();
        //console.log(n);
        $(".tn_act .o_tn").text(n);
        $(".cateID").val(cateID);
        $(".boardNo").val(n);


        $(".select_table").hide();
    })


    $(".saomiao").click(function(event) {
        event.stopPropagation();
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


        if(isAndroid==true){
            Android.callYunDan();
        }else if(isiOS==true){
            var url= "func=" + 'callioscanzuo';
            window.webkit.messageHandlers.Native.postMessage(url);
        }

    }
    );

});
//companyId,cater,coID,remark
//提交订单
function submitOrder(){

    var boardNo = $(".boardNo").val()

    if(boardNo==""){
      alert("请选择桌号");
      return;
    }

    if(token ==1) {
       return;
    }
    token = 1;

    $.ajax({
        url: basePath + "ea/assicode/sajax_ea_genClientOrderDetail.jspa",
        type: "get",
        dataType: "json",
         async:true,
        data: $("#dcForm").serialize(),
        success: function (data) {
            document.location.href = basePath + "ea/assicode/ea_getClientOrderList.jspa?state=01";
        },
        error: function (data) {

        }


    });


}



//////处理
function callIOSyundanhao(danhao) {
    if (danhao != null&&danhao.indexOf("canzuo") != -1) {

            danhao= danhao.substring(danhao.indexOf("canzuo=")+7,danhao.lastIndexOf("&"));

        window.location.href = callurl + "&pl=" + danhao;
    }else{

        alert("请扫码正确的桌号");

    }

}
function yundan(tiaoma){

    if (tiaoma!=null&&tiaoma.indexOf("canzuo") != -1) {

            tiaoma= tiaoma.substring(tiaoma.indexOf("canzuo=")+7,tiaoma.lastIndexOf("&"));

        window.location.href = callurl + "&pl=" + tiaoma;
    }else{
        alert("请扫码正确的桌号");

    }
}