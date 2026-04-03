$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
		
	$('.hot').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '分配住宿信息',
				minheight : 80,
				buttons : [{
					name : '分配住宿',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '分配住宿' :
				window.parent.$("table#hotelSearchTable").find("input#accommodHotID").attr("value","");
				window.parent.$("table#hotelSearchTable").find("input#accommodHotKey").attr("value","");
				window.parent.valdatastaff("fp");
				break;
			case '修改' :
				if(hotID ==""){
					alert("请选择具体信息!");
					return
				}
				window.parent.valdatastaff(hotID);
				break;
			case '删除' :
				if(hotID ==""){
				alert("请选择具体信息!");
				 return
				}
				$f = $('#hotForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/accommodhot/ea_deleteHot.jspa?pageNumber="
											+ pNumber + "&accHot.accommodHotID="
											+ hotID);
					document.hotForm.submit.click();
					$("tr#" + hotID).remove();
					hotID = "";
					token = 11;
				}
				break;

			case '查询' :
				select3();
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/accommodhot/ea_toSeach.jspa?";
				numback(url);
				break;
			case '导出' :
				var url = basePath+ "ea/accommodhot/ea_showAccHot.jspa?search="+search;
				open(url);
				break;

		}
	}
	$(".close").click(function() {// 取消
		$("#jqModel").jqmHide();
		re_load();
	});
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
			});
	$(".hot tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		hotID = this.id;
	});
	
	$(".hot tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		hotID = this.id;
		action("修改");
	});
	
	$("input#tosearch").click(function (){
		$("#SearchForm").attr(
						"action",
						basePath
								+ "ea/accommodhot/ea_toSeach.jspa?pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();		
	});
});
function loadseft(){
	document.location.href = basePath
				+ "ea/accommodhot/ea_toSeach.jspa?pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/accommodhot/ea_toSeach.jspa?pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

//预设查询下拉框
function select3(){
	var ajaxUrl = basePath + "ea/accommod/sajax_ea_jumpHotel1.jspa?date=" + new Date().toLocaleString();
	$.ajax({
		url:encodeURI(ajaxUrl), 
		type:"get", 
		async:true, 
		dataType:"json", 
		success:function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var starsList = member.starsList;
							//星级赋值
			var params_ = document.getElementById("addstart");
			
			for (var i = 0; i < starsList.length; i++) {		
						        //创建标签
				var opt = document.createElement("option");
						        
						        //创建文本
				var _text = document.createTextNode(starsList[i].codeValue);
						        
						        //把文本赋给标签
				opt.appendChild(_text);
						        
						        //属性赋值
				opt.setAttribute("value", starsList[i].codeID);
						        //添加
				params_.appendChild(opt);
			}
						
						// 房间类别赋值
			var roomTypeList = member.roomTypeList;
			var params_ = document.getElementById("addRoomtype");
			for (var i = 0; i < roomTypeList.length; i++) {		
						        //创建标签
				var opt = document.createElement("option");
						        
						        //创建文本
				var _text = document.createTextNode(roomTypeList[i].codeValue);
						        
						        //把文本赋给标签
				opt.appendChild(_text);
						        
						        //属性赋值codeID
				opt.setAttribute("value", roomTypeList[i].codeID);
						        
						        //添加
				params_.appendChild(opt);
			}
						// 酒店名称赋值
			var hotelList = member.hotelList;
			var params_ = document.getElementById("addhotname");
			for (var i = 0; i < hotelList.length; i++) {		
						        //创建标签
				var opt = document.createElement("option");
						        
						        //创建文本
				var _text = document.createTextNode(hotelList[i].companyname);
						        
						        //把文本赋给标签
				opt.appendChild(_text);
						        
						        //属性赋值codeID
				opt.setAttribute("value", hotelList[i].hotelname);
						        
						        //添加
				params_.appendChild(opt);
			}
		},
		error : function cbf(data) {
				alert("数据获取失败！");
		}
	});
}
