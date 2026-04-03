var valid = {
    isDigit: isDigit,
    isRegisterUserName: isRegisterUserName,
    isPasswd: isPasswd,
    isMobil: isMobil,
    isEmail: isEmail,
    ischinese: isChinese,
    isEffective: isEffective,
    isTelno: isTelno,
	isDate:isDate,
	isDecimal:isDecimal,
	isFloatField:isFloatField
}
// 校验是否全由数字组成
function isDigit(s){
    var patrn = /^[0-9]{1,20}$/;
    if (!patrn.exec(s)) 
        return false;
    return true;
}

// 校验登录名：只能输入5-20个以字母开头、可带数字、“_”、 “.”的字串
function isRegisterUserName(s){
    var patrn = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;
    if (!patrn.exec(s)) 
        return false;
    return true;
}

// 校验密码：只能输入6-20个字母、数字、下划线
function isPasswd(s){
    var patrn = /^(\w){6,20}$/;
    if (!patrn.exec(s)) 
        return false;
    return true;
}

// 校验手机号码：必须以数字开头，除数字外，可含有“-”
function isMobil(s){
    var patrn = /^([0-9]{11,13})?$/;
    if (!patrn.exec(s)) 
        return false;
    return true;
}

//校验电子邮件
function isEmail(src){
    var isEmail1 = /^\w+([\.\-]\w+)*\@\w+([\.\-]\w+)*\.\w+$/;
    var isEmail2 = /^.*@[^_]*$/;
    return (isEmail1.test(src) && isEmail2.test(src));
}

//是否为中文
function isChinese(s){
    var patrn = /^[\u4e00-\u9fa5]+$/;
    if (!patrn.exec(s)) 
        return false;
    return true;
}

//是否有效字符
function isEffective(s){
    var patrn = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
    if (!patrn.exec(s)) 
        return false;
    return true;
}

//校验电话号码
function isTelno(s){
    var patrn = /^[0-9]{3}-[0-9]{8}|[0-9]{4}-[0-9]{7}$/;
    if (!patrn.exec(s)) 
        return false;
    return true;
}
//日期校验
function isDate(s){
	var patrn=/^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))$/;
	if(!patrn.exec(s))return false;
	return true;
}
function isDecimal(s){
	var patrn=/^\d{1,7}(\.\d{1,2})?$/;
	if(!patrn.exec(s))
		return false;
	return true;
}
//浮点数字段验证
function isFloatField(s){
	if(s.indexOf(".")==-1)return true;
	var att=s.split(".");
	if(att.length>2) return true;
	if(parseInt(att[0])<parseInt(att[1])) return true;
	return false;
}
