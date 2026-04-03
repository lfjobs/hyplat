var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
let dsaddress="";
let coordinate="";

$(function () {

    if (cwts==null||cwts==""||wts==null||wts==""){

        window.parent.location.href=basePath +"page/ea/main/marketing/edmandServe/workType_list.jsp";
    }

    ajax();
    $(document).on("click",".qd_box",function() {
        qd($(this).attr("id"));
        return false;
    });

    /*if(isAndroid==true){
        var obj = document.getElementById("ret");
        obj.setAttribute("href","#");
        obj.setAttribute("onclick", "retAndroid()");
    }else if(isiOS==true){
        var obj = document.getElementById("ret");
        obj.setAttribute("href","#");
        obj.setAttribute("onclick", "retIOS()");
    }else{
        alert(22222222);
    }*/

});

function getHeight(){
    t=setTimeout("getHeight()",200);
    if($(".last").length>0){
        if($(".last").offset().top+$(".last").height()-$(".header").height()*4<$(window).height()){
            if(pagenumber<pagecount){
                loaded();
            }
        }
    }
}

function loaded(){
    ajax($.trim(search));
}

function ajax(s){
    clearTimeout(t);
    pagenumber++;
    var url=basePath+"ea/dserve/sajax_ea_detailListBywts.jspa?";
    $.ajax({
        url : url,
        type: "get",
        async:false,
        dataType : "json",
        data:{
            "pagenumber":pagenumber,
            "search":s,
            "wts":wts
        },
        success : function cbf(data){
            var member=eval("("+data+")");
            var pageForm=member.pageForm;
            var str=new Array();
            if(pageForm!=null&&pageForm.recordCount>0){
                pagenumber=pageForm.pageNumber;
                pagecount=pageForm.pageCount;
                var dlist=pageForm.list;
                $(".last").removeClass("last");
                var tradeCode="";
                for(var i=0;i<dlist.length;i++){
                    var demand=dlist[i];
                    if(i==dlist.length-1){
                        str.push("<a href='' class='qd_box qd_rec last' id='"+demand.ddid+"'>");
                    }else{
                        str.push("<a href='' class='qd_box qd_rec' id='"+demand.ddid+"'>");
                    }
                    str.push("<div class='qd_top qd_two clearfix'>");
                    str.push("<span>"+(demand.ddcontactphone==null||demand.ddcontactphone==""?"--":hidePhone(demand.ddcontactphone))+"</span>");
                    str.push("<span>"+(demand.ddexpectdate==null||demand.ddexpectdate==""?demand.ddadddate:demand.ddexpectdate)+"</span></div>");
                    str.push("<div class='qd_bottom qd_two clearfix'>");
                    str.push("<span>"+(demand.ddtitle==null||demand.ddtitle==""?"--":demand.ddtitle)+"</span>");
                    str.push("<span>"+(demand.ddworktype==null||demand.ddworktype==""?"--":demand.ddworktype)+"</span></div></a>");
                }
            }else{
                //<!--无货显示-->
                //str.push("<div class='no'>");
                //str.push("<img src='"+basePath+"images/ea/finance/NewPhoneOrders/wu.png' alt='' class='wu'>");
                //str.push("<p>目前还没有订单哦～</p></div>");
            }
            $("#recordCount").text(pageForm!=null?pageForm.recordCount:0);
            $(".qd_con").append(str.join(""));
            getHeight();
        }
    });
}

function qd(ddid){
    var url=basePath+"/ea/dserve/sajax_ea_saveServe.jspa?";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "sccid": sccid,
            "ddid": ddid,
            "dsaddress":dsaddress,
            "coordinate":coordinate
        },
        success: function cbf(data) {
            //var member=eval("("+data+")");
            var flag=data.flag;
            if(flag=="操作成功"){
                document.location.href=basePath+"ea/dserve/ea_toPage_demandListBydssccid.jspa?sccid="+sccid+"&tle=1";
            }else{
                alert(flag);
            }
        }
    });
}

//安卓，苹果返回
function retAndroid(){
    try{
        Android.callAndroidjianli();
    }catch(err){
        $(".back").removeAttr("onclick");
    }
}
function retIOS(){
    try{
        calImport('');
    }
    catch(err){
        $(".back").removeAttr("onclick");
    }
}

AMap.plugin(['AMap.Geolocation', 'AMap.PlaceSearch', 'AMap.Geocoder'], function () {
    // 定位
    var geolocation = new AMap.Geolocation({
        enableHighAccuracy: true, // 是否使用高精度定位，默认：true
        timeout: 10000, // 设置定位超时时间，默认：无穷大
        buttonOffset: new AMap.Pixel(10, 20), // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
        // offset: [10, 20], // 定位按钮的停靠位置的偏移量
        position: 'RB' //  定位按钮的排放位置,  RB表示右下
    });
    geolocation.getCurrentPosition(function (status, result) {
        if (status == 'complete') {
            onComplete(result)
        } else {
            onError(result)
        }
    });

    function onComplete(data) {
        // data是具体的定位信息
        // console.log(data.position.lng)
        // console.log(data.position.lat)
        // console.log(data)
        const position = [data.position.lng, data.position.lat]
        //map.setCenter(position);
        // 逆地理编码
        const geocoder = new AMap.Geocoder({
            // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
            city: '全国'
        });
        geocoder.getAddress(position, function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
                const address = result.regeocode.formattedAddress
                dsaddress=address;
                coordinate=position;
            }
        });
    }

    function onError(data) {
        console.log('定位出错');
    }
});

// 替换alert
function showAlert(type, title, message) {
    Swal.fire({
        icon: type,
        title: title,
        text: message,
        toast: true,
        //position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true
    });
}

// 替换confirm
function showConfirm(title, text, confirmCallback) {
    Swal.fire({
        title: title,
        text: text,
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#e74c3c',
        cancelButtonColor: '#7f8c8d',
        confirmButtonText: '确定',
        cancelButtonText: '取消'
    }).then((result) => {
        if (result.isConfirmed) {
            confirmCallback();
        }
    });
}

// 定义电话号码隐藏函数
function hidePhone(phone) {
    // 校验是否为11位手机号
    if (!/^1[3-9]\d{9}$/.test(phone)) {
        return phone; // 非11位手机号返回原内容
    }
    // 替换中间4位为星号
    return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
}