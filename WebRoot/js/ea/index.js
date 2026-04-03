//登录按钮点击触发事件，判断文本框输入是否为空，是否合法
function login() {
    var png = $("#daohang").attr("src");
    var orgainze = $("#strj1").val();
    var username = $("#username").val();
    var password = $("#password").val();
    var validate = $("#validate").val();
    if (orgainze == "请输入组织机构名"||orgainze =="") {
    	$("#strj1").val("请输入组织机构名");
        return false;
    }
    if (username == "请输入用户名"||username =="") {
    	$("#username").val("请输入用户名");
        return false;
    }
    if (password== "请输入密码"||password== "") {
        return false;
    }
    if (validate == "验证码"||validate == "") {
    	$("#validate").val("验证码");
        return false;
    }
    $("#loginForm").attr("action", basePath + "ea/login.jspa?");
    document.loginForm.submit.click();
}
document.onkeydown = function (evt) {//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : ""); //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        login();
    }
};
