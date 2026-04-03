function openCustomWindow(title,url,height,width){
    $.ligerDialog.open({height: height, title:title, url: url, width: width, showMax:true});
}

function openViewWindow(title,url,height,width){
    return $.ligerDialog.open({height: height, title:title, url: url, width: width, showMax:true,  buttons: [{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ]});
}
/*错误提示窗口，图片是红叉的提示窗口，errorMsg：提示内容*/
function openErrorDialog(errorMsg){
    $.ligerDialog.error(errorMsg);
}

/*成功提示窗口，图片是绿钩的提示窗口，nextPath：页面跳转url*/
function openSuccessDialog(nextPath){
	$.ligerDialog.success('操作成功！', '提示', function(){window.location.href=nextPath;});
}
function openSuccessDialog1(msg){
	if (!msg){
		msg = '操作成功！';
	}
	$.ligerDialog.success(msg);
}
/** msg:提示信息 nextPath:页面跳转url */
function openDialog(msg,nextPath){
	if(!msg){
		msg = "操作成功！";
	}
	$.ligerDialog.confirm(msg, function (yes) {
		window.location.href=nextPath;
	 });
}
/*提示信息*/
function ligerWarn(content){
	$.ligerDialog.warn(content);
}
/*成功提示窗口，图片是绿钩的提示窗口，关闭当前窗口，刷新父窗口*/
function openSuccessDialog2(){
	$.ligerDialog.success('操作成功！', '提示', function(){parent.window.location.reload();dialog.close(); });
}
/*成功提示窗口，图片是绿钩的提示窗口，关闭当前窗口*/
function openSuccessDialog3(){
	$.ligerDialog.success('操作成功！', '提示', function(){window.close(); });
}
