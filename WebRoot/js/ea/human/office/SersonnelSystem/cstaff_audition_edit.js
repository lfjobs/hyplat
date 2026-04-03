$(function() {
	$("td.txt03").click(function(){
		var did = $(this).next().find("a:eq(0)").attr("id");
		var mid = did.substring(4);
		changemenu(did,mid,'edit');
	});
});

function toSave(formID, url) { // 保存
	if(retoken)
		return;
	$(".put4", 'form#'+formID).trigger("blur");	
	if ($(".newerror", 'form#'+formID).length) {
		retoken = 0;
		alert('请完善所有信息');
		return;
	}
	if(photosizes == 1){
		retoken = 0;
		alert("上传图片规格必须为102X126！ 大小必须小于100k");
		return;
	}
	retoken = 1;
	$("form#"+formID).attr("action",basePath+url);
	$("form#"+formID).submit();
}
//第一个参数为表单id，第二个参数为 table表头id
function changemenu(divid,menuid,opetype){
	if(opetype=='edit'){
		$("div#"+divid).find("span.isShow").removeClass("isShow").addClass("isHide");
		$("div#"+divid).find("input.isHide").removeClass("isHide").addClass("isShow");
		$("table.box"+menuid).find("a#mord"+menuid+"_close").removeClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid).addClass("isHide");
		switch(menuid){
			case 1:
				break;
			default:
				$("div#box"+menuid).show();
				
				var personvalue = $("span#staffID").text();
				if (personvalue == '') {
					alert("请先填写人员基本信息！");
					return;
				}
				var personurl = basePath + $("#mainframe"+menuid).attr("url");
				$("#mainframe"+menuid).attr("src", personurl + personvalue+"&d="+Math.ceil(Math.random()*1000));
				break;
		}
	}else if(opetype=='close'){
		$("div#"+divid).find("span.isHide").removeClass("isHide").addClass("isShow");
		$("div#"+divid).find("input.isShow").removeClass("isShow").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid+"_close").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid).removeClass("isHide");
		$("tr#tools").hide();
		$("div#box"+menuid).hide();
	}
}