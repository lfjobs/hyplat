$(function(){



});
//文书合同
function flow(module){
	document.location.href = basePath+"ea/documentcommon/ea_showDocumentModule.jspa?module="+module+"&d="+new Date()+"&bb=new";

}

//公司招聘
function recruit() {
	document.location.href = basePath+"page/ea/main/production/resume/resumeManagement/gscandidates.jsp?sccId="+sccId;

}

function order(){

	document.location.href = basePath+"ea/seller/ea_getcomporder.jspa?companyid="+companyID+"&staffid="+staffID+"&sccId="+sccId;
}

function yj(){

	document.location.href = basePath+"/ea/jinbi/ea_gethyjifen.jspa?user="+user+"&sccid="+sccId+"&khd=0&app=00";
}


function backcard(){

	document.location.href = basePath+"ea/perinfor/ea_getBankCardInformation.jspa?khd=0&flag=&identifying=&ccompanyId="+companyID+"&staffId="+staffID+"&sccid="+sccId+"&user="+user+"&editType=00&mark=01";
}

function auth(){
	document.location.href = basePath+"/ea/qrshare/ea_queryState.jspa?auditSkip=01&companyID="+companyID+"&staffID="+staffID
}