$(document).ready(function() {
	var ts = "";//状态
	var es = "";//页数
	load();
	// $(".jqmWindow").jqm({
	// modal : true,// 限制输入（鼠标点击，按键）的对话
	// overlay : 20// 遮罩程度%
	// }).jqmAddClose('.close');// 添加触发关闭的selector
	//	
	// 下一页
	$('#grxy').click(function(){
		var xy = $(this).attr("title");
		if (xy != 0) {
			es = "&pageForm.pageNumber=" + xy ;
			selectv(es);
		} else {
			alert("已是尾页！");
		}
	});
	// 上一页
	$("#grsy").click(function() {
		var xy = $(this).attr("title");
		if (xy != 0) {
			es = "&pageForm.pageNumber=" + xy ;
			selectv(es);
		} else {
			alert("已是首页！");
		}
	});
	//查询
	$("#sel").click(function(){
		selectv("");
	});
	//关闭
	$("#clo").click(function(){
		$("#wfjift").hide();
		$("#selectv").attr("value","");
	});
	
});
function load() {
	var html = "<div id='wfjift' class='jqmWindow'"
			+ "style='width:95%; position:relative; display: none; bottom:800px; left:15px; background: #eff; '>";
	html += "<table style='width:100%; height: 100%;' border='1'>"
			+ "<tr><td colspan='2' style='height:50px;' align='center'>"
			+ "<input name='selectv' id='selectv' style='border: 1px solid #CCC;'/>&nbsp;<a href='javascript:void(0);' id='sel'>查询</a>&nbsp;"
			+ "<a href='javascript:void(0);' id='clo'>关闭</a></td></tr>"
			+ "<tr><td colspan='2' id='newtable'></td></tr>"
			+ "<tr><td style='height:30px;' align='center'>" +
					"<a href='javascript:void(0);' style='width:100%;height:100%;display:block;vertical-align:middle;padding-top:19px;' id=\"grsy\" title=\"0\">上一页</a></td>" +
					"<td align='center'><a href='javascript:void(0);' style='width:100%;height:100%;display:block;vertical-align:middle;padding-top:19px;' id=\"grxy\" title=\"0\">下一页</a></td></tr></table>";
	html += "</div>";
	$("body").append(html);
}
//行事件
function trclick(e){
	var trval = $("#"+e.id).find("#all").text();
	$("#wfjift").hide();
	$("#selectv").attr("value","");
	paret(trval);
}
// 显示
function showwfjift(t) {
	$("#wfjift").css("margin-top", "140px");
	//$("#wfjift").css("height", $(window).height()); // 高度
	// $(window).width();
	$("#wfjift").show();
	ts = t;
	selectv("");
}
// 查询
function selectv(es) {
	var selecv = $("#selectv").val();
	var url = basePath
			+ "ea/accessresource/sajax_ea_getWfjift.jspa?searchvalue=" + selecv
			+ "&stuts=" + ts + es;
	$.ajax({
		url : encodeURI(url + "&date=" + new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			if (pageForm == null) {
				var tabletr = "没有查询到数据！";
				$("#body_02cu").html(tabletr);
				notoken = 0;
				return;
			}
			var dqy = pageForm.pageNumber;// 当前页
			var zys = pageForm.pageCount;// 总页数
			if (dqy > 1) {
				$("#grsy").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#grxy").attr("title", dqy + 1);
			}
			var tabletr = "<table width='100%' height='100%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
			if(ts == "01"){ //单位
				tabletr += " <tr>"
					+ "<th align='center' bgcolor='#E4F1FA' height='40px'>往来单位名称</th>"
					+ "<th align='center' bgcolor='#E4F1FA' width='35%' >单位电话</th>"
					+ "</tr>";
				for ( var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i][0] + " onclick='javascript:trclick(this)'>";
					tabletr += "<td id='companyName' align='center'>"
							+ pageForm.list[i][1] + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
						+ pageForm.list[i][3] + "</td>";
					tabletr += "<td id='all' style='display:none' align='center'>"
						+ pageForm.list[i] + "</td>";
					tabletr += " </tr>";
				}
			}
			tabletr += "</table>";
			$("#newtable").html(tabletr);
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}