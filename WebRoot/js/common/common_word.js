
	// 设置word模板打开word
function OpenWord(submitType,WorkMode) {
	var url = submitType;
	jQuery.ajaxSetup({cache:false});
	fileTypes = "W";
	if (submitType !== null && $.trim(submitType) != "") {
		window.showModalDialog(basePath + "page/ea/common/common_word.jsp?docPath=" + submitType + "&fileType=" + fileTypes+ "&WorkMode=" + WorkMode, "", "dialogHeight: 1000px; dialogWidth:1900px; dialogTop: 220px; dialogLeft:448px; center: yes; help: no; scroll: no;resizable:no; status: no;");
	} else {
		var urll = basePath + "ea/uploadfile/sajax_n_ea_editWord.jspa?fileType=" + fileTypes + "&date=" + new Date().toLocaleString();
		$.ajax({
			url:encodeURI(urll),
		 	type:"get",
		  	dataType:"json",
		   	async:false, 
		   	success:function (data) {
			var jsonresult = eval("(" + data + ")");
			var result = jsonresult.result;
			url = result;
			window.showModalDialog(basePath + "page/ea/common/common_word.jsp?docPath=" + result + "&fileType=" + fileTypes+ "&WorkMode=" + WorkMode, "", "dialogHeight: 1000px; dialogWidth:1900px; dialogTop: 220px; dialogLeft:448px; center: yes; help: no; scroll: no;resizable:no; status: no;");
		}, error:function (data) {
			alert("\u521b\u5efa\u6a21\u677f\u5931\u8d25");
		}});
	}
	return url;
}

