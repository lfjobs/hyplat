var name='';
var phone='';
var idCard='';
var driverId='';
var drivingId='';
var marketList='';
var carTypeList='';
var carUseList='';
var i;

$(function () {
    init();//初始化页面

    //提交司机信息
    $("#joinIn").click(function () {
        i=1;
       
   
        nameCheck(); if(i==0) return;//姓名
        idCardCheck(); if(i==0) return;//身份证
        driverIdCheck(); if(i==0) return;//驾驶证
        phoneCheck(); if(i==0) return;//手机号
        
        if(sel=="1"){
        drivingIdCheck(); if(i==0) return;//行驶证
       
        imgCheck(); if(i==0) return;//图片
        license_plate_numberCheck();if(i==0) return;//号牌号码
        carTypeCheck(); if(i==0) return;//车辆类型
      //  vehicleBrandCheck(); if(i==0) return;//品牌类型
        engineNumCheck(); if(i==0) return;//发动机号码
     //   usePropCheck(); if(i==0) return;//使用性质
        loadCheck(); if(i==0) return;//载重
        specificationsCheck(); if(i==0) return;//长宽高（外廓尺寸）
        loadVolumeCheck(); if(i==0) return;//载重体积
        }
        marketCheck(); if(i==0) return;//市场
        
        
        $('#Infoform').attr('action', basePath+"ea/phljoin/ea_saveJoinOwnCar.jspa?");
        document.Infoform.submit.click();
        prompt("加入成功！");
        $("#joinIn").attr("disabled","disabled").css("color","grey");
    });

    //提示框
    $("#prompt").css("position","fixed").css("top",$(window).height()*0.09+"px");
    $("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

    //模拟下拉框
    $(".input_select").click(function(e) {
        var ul = $(this).parent().find("ul");
        if(ul.children().length==0){
            prompt("请联系管理员添加数据");
            return;
        }
        if(ul.css("display") == "none") {
            ul.slideDown("fast");
        } else {
            ul.slideUp("fast");
        }
        e.stopPropagation();
    });
    $("html").not(".dropdown,.input_select").click(function(){
        $(".dropdown ul").hide();
    })
});

//初始化页面
function init() {
    var url = document.location.toString();
    var arrUrl = url.split("=");
    var ccompanyID = arrUrl[1];

    var url = basePath+"ea/phljoin/sajax_ea_initCarJoin.jspa?";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data){
            var member = eval("(" + data + ")");
            name = member.name;
            phone=member.phone;
            idCard=member.idCard;
            driverId=member.driverId;
            drivingId=member.drivingId;
            $("#name").val(name);
            $("#phone").val(phone);
            $("#idCard").val(idCard);
            if(driverId!=null&&driverId!=""){
                $("#driverId").val(driverId);
            }else{
                $("#driverId").val(idCard);
            }
            $("#drivingId").val(drivingId);
            getSex();
            var htmlstr=new Array();
            //市场列表
            marketList=member.marketList;
            htmlstr.push("<ul>");
            for(var i=0;i<marketList.length;i++){
                htmlstr.push("<li>");
                htmlstr.push("<a onclick='changeTxt(this)'>"+marketList[i][1]+"</a><input type='hidden' value='"+marketList[i][0]+"'/>");
                htmlstr.push("</li>");
                if(ccompanyID==marketList[i][0]){
                    $("#selMarket").val(marketList[i][1]);
                }
            }
            htmlstr.push("</ul>");
            $("#marketDown").append(htmlstr.join(""));

            //车辆类型列表
            carTypeList=member.carTypeList;
            htmlstr=new Array();
            htmlstr.push("<ul>");
            for(var i=0;i<carTypeList.length;i++){
                htmlstr.push("<li>");
                htmlstr.push("<a onclick='changeTxt(this)'>"+carTypeList[i]+"</a><input type='hidden' value='"+carTypeList[i]+"'/>");
                htmlstr.push("</li>");
            }
            htmlstr.push("</ul>");
            $("#carTypeDown").append(htmlstr.join(""));

            //车辆使用性质列表
            carUseList=member.carUseList;
            htmlstr=new Array();
            htmlstr.push("<ul>");
            for(var i=0;i<carUseList.length;i++){
                htmlstr.push("<li>");
                htmlstr.push("<a onclick='changeTxt(this)'>"+carUseList[i]+"</a><input type='hidden' value='"+carUseList[i]+"'/>");
                htmlstr.push("</li>");
            }
            htmlstr.push("</ul>");
            $("#carUseDown").append(htmlstr.join(""));
        },
        error: function cbf(data){
            alert("数据获取失败");
        }
    });
    $("#joinIn").removeAttr("disabled");
}


//选择模拟下拉框改变文本
function changeTxt(a) {
    var txt = $(a).text();
    $(a).parents(".dropdown").find("input:first-child").val(txt);
    $(a).parents(".dropdown").find("input:first-child").next().val($(a).next().val());
    $(".dropdown ul").hide();
}

//提示框样式
function prompt(obj){
    if($("#prompt").css("display")!="none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function(){
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 2000);
}
//输入验证
function nameCheck() {//姓名
    var nameReg = /^[\u4E00-\u9FA5A-Za-z]+$/;
    var name=$("#name").val();
    if(name==null||name==""){
        prompt("请输入姓名");
        i=0;
    }else if(!nameReg.test(name)){
        prompt("姓名只能输入中文和英文");
        i=0;
    }
}
function idCardCheck(){//身份证
    var idcardReg = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
    var idCard=$("#idCard").val();
    if(idCard==null||idCard==""){
        prompt("请输入身份证");
    }else if(!idcardReg.test(idCard)){
        prompt("身份证格式不正确");
        i=0;
    }else{
        getSex();
    }
}

function getSex() {//根据身份证自动获取性别
    var idCard=$("#idCard").val();
    if(idCard==null&&idCard==""){
        return;
    }
    if(idCard.length==18){
        if(idCard.slice(14, 17) % 2){//偶数女，奇数男
            $("#sex").val("男");
        }else{
            $("#sex").val("女");
        }
    }else if(idCard.length==15){
        if(idCard.substr(14, 1) % 2){
            $("#sex").val("男");
        }else{
            $("#sex").val("女");
        }
    }
}

function driverIdCheck() {//驾驶证
    var driverIdReg=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}(\d|x|X)$/;
    var driverId=$("#driverId").val();
    if(driverId==null||driverId==""){
        prompt("请输入驾驶证编号");
        i=0;
    }else if(!driverIdReg.test(driverId)){
        prompt("驾驶证编号格式不正确");
        i=0;
    }
}
function drivingIdCheck() {//行驶证
    var drivingIdReg=/^[A-Z\d]{17}$/;
    var drivingId=$("#drivingId").val();
    if(drivingId==null||drivingId==""){
        prompt("请输入行驶证编号");
        i=0;
    }else if(!drivingIdReg.test(drivingId)){
        prompt("行驶证编号格式不正确");
        i=0;
    }else{
        //验证行驶证是否注册
        var url = basePath+"ea/phljoin/sajax_ea_checkUnique.jspa?drivingId="+drivingId;
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var count=member.count;
                if(count>0){
                    prompt("行驶证编号已注册");
                    i=0;
                }
            },
            error: function cbf(data){
                alert("数据获取失败");
            }
        });
    }

}
function phoneCheck(){//手机号
    var phoneReg=/^1[3-9]\d{9}$/;
    var phone=$("#phone").val();
    if(phone==""||phone==null){
        prompt("请输入手机号");
        i=0;
    }else if(!phoneReg.test(phone)){
        prompt("手机号格式不正确");
        i=0;
    }
}
function license_plate_numberCheck() {//汽车号牌
    var license_plate_numberReg = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
    var lpn=$("#license_plate_number").val();
    if(lpn==null||lpn==""){
        prompt("请输入汽车号牌");
        i=0;
    }else if(!license_plate_numberReg.test(lpn)){
        prompt("号牌号码格式不正确");
        i=0;
    }else{
        //验证汽车号牌是否注册
        var url = basePath+"ea/phljoin/sajax_ea_checkUnique.jspa?carNum="+lpn;
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var count=member.count;
                if(count>0){
                    prompt("号牌号码已注册");
                    i=0;
                }
            },
            error: function cbf(data){
                alert("数据获取失败");
            }
        });
    }
}
function loadCheck(){//载重重量
    var loadReg=/^[0-9]+([.]{1}[0-9]+){0,2}$/;
    var load=$("#load").val();
    if(load==null||load==""){
        prompt("请输入载重重量");
        i=0;
    }else if(!loadReg.test(load)){
        prompt("请输入正确的载重重量");
        i=0;
    }
}
function specificationsCheck() {//长宽高
    var specificationsReg=/^([0-9]+([.]{1}[0-9]+){0,2}\*){2}([0-9]+([.]{1}[0-9]+){0,2}){1}$/;
    var specifications=$("#specifications").val();
    if(specifications==null||specifications==""){
        prompt("请输入长宽高");
        i=0;
    }else if(!specificationsReg.test(specifications)){
        prompt("请按1.6*1.3*1.1的格式输入");
        i=0;
    }
}
function loadVolumeCheck() {//载重体积
    var loadVolumeReg=/^[0-9]+([.]{1}[0-9]+){0,2}$/;
    var loadVolum=$("#loadVolume").val();
    if(loadVolum==null||loadVolum==""){
        prompt("请输入载重体积");
        i=0;
    }else if(!loadVolumeReg.test(loadVolum)){
        prompt("请输入正确的载重体积");
        i=0;
    }
}
function imgCheck() {//验证图片
    if(Infoform.file.value == ""){
        prompt("请上传车辆图片");
        i=0;
    }
}

function carTypeCheck() {//车辆类型
    var carType=$("#carType").val();
    if(carType==null||carType==""){
        prompt("请选择车辆类型");
        i=0;
    }
}
function vehicleBrandCheck() {//品牌型号
    var vehicleBrand=$("#vehicleBrand").val();
    if(vehicleBrand==null||vehicleBrand==""){
        prompt("请输入品牌型号");
        i=0;
    }
}
function engineNumCheck() {//发动机号码
    var engineNum=$("#engineNum").val();
    if(engineNum==null||engineNum==""){
        prompt("请输入发动机号码");
        i=0;
    }else{
        //验证发动机号码是否注册
        var url = basePath+"ea/phljoin/sajax_ea_checkUnique.jspa?engineNum="+engineNum;
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var count=member.count;
                if(count>0){
                    prompt("发动机号码已注册");
                    i=0;
                }
            },
            error: function cbf(data){
                alert("数据获取失败");
            }
        });
    }
}
function usePropCheck() {//车辆使用性质
    var useProp=$("#useProp").val();
    if(useProp==null||useProp==""){
        prompt("请选择车辆使用性质");
        i=0;
    }
}
function marketCheck() {//批发市场
    var market=$("#selMarket").val();
    if(market==null||market==""){
        prompt("请选择批发市场");
        i=0;
    }
}

//图片上传
//如需多个上传需要更改函数名f_change 以及id：imgSdf
var reader = new FileReader();
function f_change(file){
    var img = document.getElementById('imgSdf');
    //读取File对象的数据
    reader.onload = function(evt){
        //data:img base64 编码数据显示
        img.width  =  "100";
        img.height =  "100";
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
}
