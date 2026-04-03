
$(function () {

    $("#txtNum").focus(function(){
        if(posNum!=null&&posNum!="") {
            document.activeElement.blur();//屏蔽默认键盘弹出；
        }
    });

    /*显示隐藏键盘*/
    $(".cz-je p input").focus(function () {
        $(".cz-jp333").show();
    });
    $(".jp tr #btnEnter").click(function () {
        $(".cz-jp333").hide();
    });
    $(".cz-je ul li").click(function () {
        var txt = $(this).text();
        $(".cz-je p input").val(txt);
    });



    $("#cz").click(function(){
        if( $("#txtNum").val()==null || $("#txtNum").val()=="" || $("#txtNum").val()== 0){
            alert("请输入正确的数字")
            return;
        }
        document.topUpForm.submit.click();
        token = 13;

    });
});

/*数字1-9*/
function btnNum_onclick(i) {
    var values = document.getElementById("txtNum").value;
    txtNum.value=txtNum.value+i;
    checkBlus();
}

/*点*/
function dian() {
    var values = document.getElementById("txtNum").value;
    txtNum.value=txtNum.value+".";
    checkBlus()
}
/*清空*/
function clearText() {
    document.getElementById("txtNum").value = "";
}
/*删除*/
function delText() {
    var value = document.getElementById("txtNum").value;
    var str = value.substring(0,value.length-1);
    document.getElementById("txtNum").value = str;
}

//校验文本框中输入的数字是否以0开头
function checkNum(value){
    var str = value;
    var len1 = str.substr(0,1);
    var len2 = str.substr(1,1);

    //如果第一位是0，第二位不是点，就用数字把点替换掉
    if(str.length > 1 && len1==0 && len2 != '.'){
        str = str.substr(1,1);
    }

    //第一位不能是.
    if(len1=='.'){
        str = '';
    }

    //限制只能输入一个小数点
    if(str.indexOf(".")!=-1){
        var str_=str.substr(str.indexOf(".")+1);
        //限制只能输入一个小数点
        if(str_.indexOf(".")!=-1){
            str=str.substr(0,str.indexOf(".")+str_.indexOf(".")+1);
        }
    }
    return str;
}
function checkBlus(){
    var rechargeMoney = $("#txtNum").val();
    var checkedValue = checkNum(rechargeMoney);
    $("#txtNum").val(checkedValue);
}
/*input只能输入数字或者点*/
$('input[type=number]').keypress(function(e) {
    if (!String.fromCharCode(e.keyCode).match(/[0-9\.]/)) {
        return false;
    }
});


function re_load(journalNum){
    if(token){
        var  totalmoney = $("#txtNum").val();
        document.location.href = basePath+"page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum="+journalNum+"&totalMoney="+totalmoney+"&totalNum=1&cardNum="+$("#cardNum").val()+"&posNum="+posNum+"&comID="+comID+"&companyName="+companyName+"&fhform="+fhform;
        // document.location.href = basePath+"ea/sm/ea_showErCode.jspa?journalNum="+journalNum+"&totalMoney="+totalmoney+"&totalNum="+totalnum;
    }
}
