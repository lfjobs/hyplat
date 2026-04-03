//登陆
function toLogin(){
   	document.location.href = basePath+"ea/wfjshop/ea_register.jspa?sccid="+sccid+"&ccompanyId="+ccompanyId;
}

//注册
function toRegister(){
    if(ccompanyId!=""){
    	document.location.href = basePath+"ea/wfjshop/ea_findWebUser.jspa?ccompanyId="+ccompanyId;
    }else{
    	document.location.href = basePath+"ea/wfjshop/ea_getjspzc.jspa?sccid="+sccid;
    }
}