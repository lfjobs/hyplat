$(document).ready(function() {
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
	$('.accommod').flexigrid({
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : '住宿管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
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
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}
				 , {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.location.href= basePath +"ea/accommod/ea_jumpHotel.jspa?date="
				+ new Date().toLocaleString(); 
				break;
			case '查询' :
				select3();
				$("#jqModelSearch").jqmShow();
				break;
			case '删除' :
				if (accommodID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#accommodForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/accommod/ea_deleteHotel.jspa?pageNumber="
											+ pNumber + "&accommod.accommodID="
											+ accommodID);
					document.accommodForm.submit.click();
					$("tr#" + accommodID).remove();
					accommodID = "";
					token = 11;
				}
				break;
				
			case '修改' :
				if (accommodID == '') {
					alert("请选择！");
					return;
				}
				
				document.location.href= basePath +"ea/accommod/ea_jumpHotel.jspa?accommod.accommodID="+ accommodID+"&date="
				+ new Date().toLocaleString();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/accommod/ea_toSearch.jspa?accommod.accommodID="+ accommodID;
				numback(url);
				break;
			

		}
	}

	$("tr").click(function() {
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		accommodID = this.id;
	});
	
	$(".accommod tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		accommodID = this.id;
		action("修改");
	});
	
	$("#tosearch").click(function() { // 查询
		$("#SearchForm").attr(
				"action",
				basePath
						+ "ea/accommod/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
});



function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/accommod/ea_getAllList.jspa?pageNumber=" + pNumber + "&pageForm.pageNumber="
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