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

function prompt(obj) {
	if ($("#prompt").css("display") != "none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function() {
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 1500);
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
	var url = basePath + "ea/bonuspoints/sajax_bindWfj.jspa";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		data : {
			openid : openid,
			tel : tel,
			companyId : companyId
		},
		dataType : "json",
		async : false,
		success : function(data) {
			var jsonresult = eval("(" + data + ")");
			var result = jsonresult.result;
			clearInterval(clock); // 清除js定时器
			$("#ver_btn").attr("disabled", false);
			$("#ver_btn").val('获取验证码');
			nums = 60; // 重置时间
			$("#valnum").val("");
			var tipcontent = "";
			if (result == "0") {
				// 此手机号没有注册微分金
				ntoken = 0;
				try {
					prompt("此手机号没有注册微分金!");
					Android.speechOutputForAndroid("此手机号没有注册微分金!");
				} catch (error) {

				}

			} else if (result == "2") {

				tipcontent = "您没入职该公司!";
				ntoken = 0;
				document.location.href = basePath
						+ "page/ea/main/human/attence/signFail.jsp";

			} else if (result == "1") {
				// 该手机账号已绑定微信，不能重复绑定
				ntoken = 0;
				try {
					prompt("该手机账号已绑定其他微信");
					Android.speechOutputForAndroid("该手机账号已绑定其他微信");
				} catch (error) {

				}
			} else if (result == "3") {
				var signDate = jsonresult.signDate;
            	var signCount = jsonresult.signCount;
            	var staffName = jsonresult.staffName;
            	
				// 签到成功
				try {
					Android.speechOutputForAndroid("签到成功");
	                document.location.href = basePath+"page/ea/main/human/attence/signSuc.jsp?signDate="+signDate+"&signCount="+signCount+"&staffName="+staffName+"&companyname="+companyname;

				} catch (error) {
	                document.location.href = basePath+"page/ea/main/human/attence/signSuc.jsp?signDate="+signDate+"&signCount="+signCount+"&staffName="+staffName+"&companyname="+companyname;

				}
				
			}

		},
		error : function(data) {

		}
	})

}
