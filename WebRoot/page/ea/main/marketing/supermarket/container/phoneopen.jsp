<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<title>验证手机号</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/container/phoneopen.css">

<script>
    var basePath="<%=basePath%>";
    var posNum = "${param.posNum}";
    var  hgcode = "${param.hgcode}";

    $(function () {
        $(".close-tingyong").click(function(){
            $(".div-tingyong").hide();
        })

    })

    var ntoken = 0;
		var clock = '';
		var nums = 60;
		function sendCode(thisBtn) {
        var tel = $("#tel").val();
        if (tel == "") {
        prompt("手机号不能为空");
        return false;
    }
        var Sreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
        if (!Sreg.test(tel)) {
        prompt("请输入正确格式手机号！");
        return false;
    }
        // 发送短信
        $.ajax({
        cache : true,
        type : "POST",
        url : basePath + "/ea/android/sajax_ea_getduanxin.jspa?pahe=" + tel,
        async : false,
        dataType : "json",
        success : function(data) {
        var member = data;
        i = member.returna;
    }
    });
        $("#ver_btn").attr("disabled", true); // 将按钮置为不可点击
        $("#ver_btn").val("剩余" + nums + '秒');
        clock = setInterval(doLoop, 1000); // 一秒执行一次

    }

		function doLoop() {
        nums--;
        if (nums > 0) {
        $("#ver_btn").val("剩余" + nums + '秒');
    } else {
        clearInterval(clock); // 清除js定时器
        $("#ver_btn").attr("disabled", false);
        $("#ver_btn").val('获取验证码');
        nums = 60; // 重置时间
    }
    }


    function prompt(c){
           $(".titlep").text(c);
           $(".div-tingyong").show();
	}



    /**
     * 绑定手机号
     */
    function bindAccount() {

        var tel = $("#tel").val();
        if (tel == "") {
            prompt("手机号不能为空");

            return false;
        }
        var valnum = $("#valnum").val();
        if (valnum == "") {
            prompt("请填写验证码");
            return false;
        } else if (valnum != i) {
            prompt("验证码不正确");
            return false;
        }
        if (ntoken == 1) {
            return false;
        }
        ntoken = 1;
        var url = basePath + "ea/sm/sajax_ea_validAccount.jspa?n="+Math.random();
        $.ajax({
            url : encodeURI(url),
            type : "post",
            data : {
                phoneNumber : tel,
				hgcode:hgcode

            },
            dataType : "json",
            async : false,
            success : function(data) {
                var jsonresult = eval("(" + data + ")");
                var sccId = jsonresult.sccId;
                var r = jsonresult.r;

                clearInterval(clock); // 清除js定时器
                $("#ver_btn").attr("disabled", false);
                $("#ver_btn").val('获取验证码');
                nums = 60; // 重置时间
                $("#valnum").val("");
                if(r=="1"){
                    var journalNum = jsonresult.journalNum;
                    var totalMoney = jsonresult.totalMoney;
                    var totalNum = jsonresult.totalNum;
                    var ccompanyID = jsonresult.ccompanyID;
                    var comID = jsonresult.comID;
                    var companyName = jsonresult.companyName;
                    var remainMoney = jsonresult.remainMoney;

                    //http://localhost:8080/hyplat/page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum=2024110211122700013&totalMoney=56.1&totalNum=1&posNum=123&staffID=&ccompanyID=contactCompany20101230UB4U5884S30000000176&comID=company201009046vxdyzy4wg0000000025&directUrl=&companyName=%E5%8C%97%E4%BA%AC%E5%A4%A9%E5%A4%AA%E4%B8%96%E7%BB%9F%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E8%B4%A3%E4%BB%BB%E5%85%AC%E5%8F%B8&fh=1&fhform=3&remainMoney=56.10
                    document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum="+journalNum+"&totalMoney="+totalMoney+"&totalNum="+totalNum+"&posNum="+posNum+"&staffID=&ccompanyID="+ccompanyID+"&comID="+comID+"&directUrl=&companyName="+companyName+"&fh=1&fhform=3&remainMoney="+remainMoney+"&lastPay=yes";

				}else {

                    document.location.href = basePath+"/page/ea/main/marketing/supermarket/container/selectDoor.jsp?hgcode=" + hgcode + "&sccId=" + sccId+"&loginMode=phone&posNum="+posNum;

                }

            },
            error : function(data) {

            }
        })

    }

</script>

</head>
<body>
<header>
	<ul class="clearfix">
		<li>
			<a onclick="javascript: window.history.go(-1);return false;"  target="_self">
				<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />
			</a>
		</li>
		<li>
			验证手机号开门
		</li>
	</ul>
</header>
<div class="content_hidden">
	<div class="content">
		<div class="con">
			<ul class="row">
				<li>
					<p class="left">手机号</p>
					<input type="number" placeholder="请输入" id="tel">
				</li>
				<li>
					<p class="left">验证码</p>
					<input type="number" placeholder="请输入" id="valnum">
					<input type="button" id="ver_btn" class="inp-hq" value="获取验证码" onclick="sendCode(this);return false;">
				</li>

			</ul>
		</div>
		<div class="btn" >
		<button onclick="bindAccount()">确定</button>
		</div>
	</div>
</div>



<!--表单提示-->
<div class="div-tingyong">
	<div class="box">
		<p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
		<div class="div-box">
			<p class="titlep"></p>
			<div class="clearfix">
				<p class="left close-tingyong">取消</p>
				<p class="right close-tingyong">确定</p>
			</div>
		</div>
	</div>
</div>
</body>
</html>