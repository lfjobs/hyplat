$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
 if(type=="message"){
 	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 150,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [  {
					name : '查看',
					bclass : 'mysearch',
					onpress : action
						
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				} ]
			});
 	
 }else{
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 150,
				width : 'auto',
				minwidth : 30,
				title : '个人往来关系',
				minheight : 80,
				buttons : [{
					name : '人事菜单',
					bclass : 'menu1',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '吊牌打印',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				} ]
			});
 }
	function action(com, grid) {
		switch (com) {
			case '人事菜单' :
				$(".menu00").toggle("fast");
				break;
			case '修改' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								relationID = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				var personvalue = $("tr#"+relationID).find("#staffID").text();
				var url = basePath
						+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
						+ personvalue;
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				break;
			case '查看' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								relationID = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				var personvalue = $("tr#"+relationID).find("#staffID").text();
				var url = basePath
						+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
						+ personvalue;
				window.open(url);
				break;
			case '删除' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";
							}
						});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#relationID').val(str);
				if (confirm("确定继续？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/contactuser/ea_delContactUser.jspa?pageNumber="
											+ pNumber);
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;
			case '查询' :
				$("#relations").children('option:eq(0)').attr("selected","selected");
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/contactuser/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '吊牌打印' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str++;
							}
						});
				if (!str) {
					alert('请具体选择!');
					return;
				}
				$("#jqModelprintIn").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/contactuser/ea_getListContactUser.jspa?search="
						+ search+"&type="+type+"&title="+title+"&typemes="+typemes+"&contactUser.companyID="+companyID;
				numback(url);
				break;
		}
	}
	// 复选框选中物品
	$(".chx").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		action("查看");
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		opertionID=this.id;
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
			
	$(".close").click(function() {// 取消
		$("#jqModel").jqmHide();
		re_load();
	});
	
	$("input.JQueryreturn").click(function() {
		$("#jqModel").jqmHide();
	});
	
	$("#tosearch").click(function() { // 查询
		var reValue = $("#relations").children('option:selected').text();
		var reVal = '';
		if(reValue != '请选择'){
			reVal = reValue.substring(reValue.indexOf('├')+1); //个人往来关系名称
		}
		$("#SearchForm").attr(
				"action",
				basePath
						+ "ea/contactuser/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&contactUser.relation=" + reVal);
		document.SearchForm.submit.click();
	});
	
	$("#queding").click(function() { // 打印
		var str = "";
		$("[name='chbox']").each(function() {
			if ($(this).is(':checked')) {
				str += $(this).attr("title") + ",";
			}
		});
		newwin = window
				.open(
						basePath
								+ "ea/printInfo/ea_savePrintInformation.jspa?staffIDS="
								+ str + "&"
								+ $("#printInfoForm").serialize(), '',
						'menuBar=yes,scrollBars=yes,overflow:auto,width=400,height=600,left=0,top=0');
		newwin.resizeTo(screen.availWidth, screen.availHeight);
		document.printInfoForm.reset();
		$("#jqModelSearchss").jqmHide();
	});
	//radio选择
	$(".radio").click(function() {
		roomNumid = this.id;
		$("#alldiv").find(".radio").each(function(i, tmp){
				if(this.checked==true){
					this.checked=false;
				}
		});
		this.checked=true;
		rid = this.value;
	});
	//酒店名称改变事件
	$("#hotelName").change(function(){
		hotelnameid = $("#hotelName").attr("value");
		roomNumid = "";
		 roomTypeChild(hotelnameid,"","");
	});
	//酒店房间类别改变事件
	$("#roomtype").change(function(){
		roomtpID = $("#roomtype").attr("value");
		roomNumid = "";
		roomTypeQiTi(roomtpID,hotelnameid,"");
	});		
	//保存
	$("#addh").click(function(){
		if(notoken)
			return
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		if(roomNumid ==""){
			alert("请选择具体房间");
			return
		}
		
		notoken = 1;
		var tab = $("table#hotelSearchTable");
		var cc = tab.find("#remarks").val();
		var aa = tab.find("input#roomDisPrice").attr("value");
		var bb = tab.find("input#roomAgrPrice").attr("value");
		var ee = tab.find("input#accommodHotKey").attr("value");
		var ff = tab.find("input#accommodHotID").attr("value");
		var gg = tab.find("input#createName").attr("value");
		var hh = tab.find("input#createDate").attr("value");
		var dd = tab.find("input#bedOccNum").attr("value");
		var tt = tab.find("input#companyID").attr("value");
		var rr = tab.find("input#organizationID").attr("value");
		var ajaxUrl = basePath +"ea/accommodhot/sajax_ea_addAccHot.jspa?date=" + new Date().toLocaleString();
		$.ajax({
			url:encodeURI(ajaxUrl), 
			type:"get", 
			async:true, 
			dataType:"json",
            contentType:'application/json;charset=UTF-8',  
			data:{
				"accHot.roomDisPrice":aa,
				"accHot.roomAgrPrice":bb,
				"accHot.remarks":encodeURI(cc),
				"accommod.bedOccNum":dd,
				"accHot.accommodID":accommodid,
				"accHot.roomNum":roomNumid,
				"accHot.accommodHotKey":ee,
				"accHot.accommodHotID":ff,
				"accHot.createName":gg,
				"accHot.createDate":hh,
				"accHot.staffID":staffID,
				"accHot.companyID":tt,
				"accHot.organizationID":rr,
				"deitnumid":deitnumid
			}, 
			success:function cbf(data) {
				var member = eval("(" + data + ")");
				var ok = member.ok;
				if(ok!=null){
					alert(ok);
					notoken = 0;
					deitnumid="";
					accommodid = "";
					$("#jqModelSearchses").jqmHide();
					window.frames["main"].loadseft();
				}
			},
			error : function cbf(data) {
					alert("数据获取失败！");
					notoken = 0;
					$("#jqModelSearchses").jqmHide();
					window.frames["main"].loadseft();
			}
		});
	});
	
	$("#hotList").click(function() {
		$(".menu00").toggle();
		personurl = basePath+ "ea/accommodhot/ea_getAllAccHot.jspa?date=" + new Date().toLocaleString();
		$("#mainframe").css({"height":"auto"}).attr("src", personurl);
		$(window).resize();
	});
	$("#billList").click(function() {
		$(".menu00").toggle();
		personurl = basePath+ "ea/piaowuManager/ea_getListpiaowu.jspa?date=" + new Date().toLocaleString()+"&aa=aa";
		$("#mainframe").css({"height":"auto"}).attr("src", personurl);
		$(window).resize();
	});
	
});

$(function() {
   	var treeid = 'scode20110106hfjes5ucxp0000000017'; //往来关系
	var url = basePath + "ea/ccode/sajax_ea_getAllListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.codeList;
                var data2 = new Array();
		        data2[0] = {
	                id: treeid,
	                pid: '-1',
	                text: '请选择'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].codeID,
                        pid: oList[i].codePID,
                        text: oList[i].codeValue
                    };
                }
                ts = new TreeSelector($("#relations")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
});
    
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/contactuser/ea_getListContactUser.jspa?pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
//酒店名称
function hotelvo(accommod,accHot){
	var ajaxUrl =  basePath+ "ea/accommod/sajax_ea_disRoom.jspa?date=" + new Date().toLocaleString();
	$.ajax({
		url:encodeURI(ajaxUrl), 
		type:"get", 
		async:true, 
		dataType:"json", 
		success:function cbf(data) { 
			var member = eval("(" + data + ")");
			var hotelList = member.hotelList;
							//酒店名称赋值
			var params_ = document.getElementById("hotelName");
			params_.options.length=0;
			for (var i = 0; i < hotelList.length; i++) {		
						        //创建标签
				var opt = document.createElement("option");
						        //创建文本
				var _text = document.createTextNode(hotelList[i].companyname);
						        //把文本赋给标签
				opt.appendChild(_text);
						        //属性赋值
				opt.setAttribute("value", hotelList[i].hotelname);
				
				if(accommod != ""){	
					if(accommod.hotelName == hotelList[i].hotelname){
						opt.setAttribute("selected","selected");
						hotelnameid = hotelList[i].hotelname;
					}
				}else{
					hotelnameid = hotelList[0].hotelname;
				}
						        //添加
				params_.appendChild(opt);
			}
					roomTypeChild(hotelnameid,accommod,accHot);
		},
		error : function cbf(data) {
				alert("数据获取失败！");
		}
	});
}
//房间类别
function roomTypeChild(hotelnameid,accommod,accHot){
	var ajaxUrl =  basePath+ "ea/accommod/sajax_ea_disRoomType.jspa?accommod.hotelName="+hotelnameid+"&date=" + new Date().toLocaleString();
	$.ajax({
		url:encodeURI(ajaxUrl), 
		type:"get", 
		async:true, 
		dataType:"json", 
		success:function cbf(data) {
			var member = eval("(" + data + ")");
			var roomTypeListChind = member.roomTypeListChind;
							//酒店名称赋值
			var params_ = document.getElementById("roomtype");
				params_.options.length=0;
			for (var i = 0; i < roomTypeListChind.length; i++) {		
						        //创建标签
				var opt = document.createElement("option");
						        //创建文本
				var _text = document.createTextNode(roomTypeListChind[i].codeValue);
						        //把文本赋给标签
				opt.appendChild(_text);
						        //属性赋值
				opt.setAttribute("value", roomTypeListChind[i].roomType);
				if(accommod != ""){					
					if(accommod.roomType == roomTypeListChind[i].roomType){
						opt.setAttribute("selected","selected");
						roomtype = roomTypeListChind[i].roomType;
						
					}
				}else{
					roomtype = roomTypeListChind[0].roomType;
				}
						        //添加
				params_.appendChild(opt);
			}
			
				roomTypeQiTi(roomtype,hotelnameid,accHot);
			
			$("table#hotelSearchTable").find("input#stars").attr("value",roomTypeListChind[0].starsName);
			$("table#hotelSearchTable").find("input#starsType").attr("value",roomTypeListChind[0].stars);	
		},
		error : function cbf(data) {
				alert("数据获取失败！");
		}
	});
}

// 酒店 其他
function roomTypeQiTi(roomtpID,hotelnameid,accHot){
	var ajaxUrl =  basePath+ "ea/accommod/sajax_ea_disHotelQiTi.jspa?accommod.hotelName="+hotelnameid+
			"&accommod.roomType="+roomtpID+"&date=" + new Date().toLocaleString();
	$.ajax({
		url:encodeURI(ajaxUrl), 
		type:"get", 
		async:true, 
		dataType:"json", 
		success:function cbf(data) {
			var member = eval("(" + data + ")");
			var accommodList = member.accommodList;
				$t = $("table#hotelSearchTable");
				$t.find("input#floor").attr("value",accommodList[0].floor);
				$t.find("input#roomPrice").attr("value",accommodList[0].roomPrice);
				$t.find("input#bedNum").attr("value",accommodList[0].bedNum);
				$t.find("input#bedOccNum").attr("value",accommodList[0].bedOccNum);
				$t.find("input#wz").attr("value",accommodList[0].bedNum - accommodList[0].bedOccNum);
				if(accHot != ""){
					$t.find("input#roomDisPrice").attr("value",accHot.roomDisPrice);
					$t.find("input#roomAgrPrice").attr("value",accHot.roomAgrPrice);
					staffID = accHot.staffID;
					$t.find("#remarks").val(accHot.remarks);
					accommodid = accHot.accommodID;
					
					$t.find("input#accommodHotKey").attr("value",accHot.accommodHotKey);
					$t.find("input#accommodHotID").attr("value",accHot.accommodHotID);
					$t.find("input#createName").attr("value",accHot.createName);
					$t.find("input#createDate").attr("value",accHot.createDate);
					$t.find("input#companyID").attr("value",accHot.companyID);
					$t.find("input#organizationID").attr("value",accHot.organizationID);
				}else{
					
					$t.find("input#roomDisPrice").attr("value",accommodList[0].roomDisPrice);
					$t.find("input#roomAgrPrice").attr("value",accommodList[0].roomAgrPrice);
					$t.find("#remarks").val(accommodList[0].remarks);
					accommodid = accommodList[0].accommodID;
				}
				
			var roomNumberList = member.roomNumberList;
			if(roomNumberList != ""){
				$t.find("div.len0").each(function(){
					$(this).remove();
				});
				for (var i = 0; i < roomNumberList.length; i++){
					var cnValue=roomNumberList[i].roomNum; //房间号
					var roomid = roomNumberList[i].roomNumID; // id
					var str = roomNumberList[i].starts; // 状态
					
					$("#d000").after($("#d000").clone(true).attr("id",cnValue).addClass("check").addClass("len0"));
					$("#" + cnValue).find('input#r').attr("id",roomid);
					$("#" + cnValue).find('input#t').attr("value",cnValue).attr("id","t"+cnValue);
					$("#" + cnValue).show();	
					if(accHot != "" ){
					
						if(roomid == accHot.roomNum && str == "01"){
							$("input#"+roomid).attr("checked","checked");
							roomNumid = roomid;
							deitnumid = roomid;
						}else{
							if(str == "01"){
							$("input#"+roomid).attr("disabled","true");
							$("#t"+cnValue).css("background-color","red");
							$("#"+cnValue).css("border-color","red");
							}
						}
					}else{
						if(str == "01"){
							$("input#"+roomid).attr("disabled","true");
							$("#t"+cnValue).css("background-color","red");
							$("#"+cnValue).css("border-color","red");
						}
					}
				}
			}
		},
		error : function cbf(data) {
				alert("数据获取失败！");
		}
	});
}
//验证分配人员
function valdatastaff(hotid){
	if(hotid == "fp" ){
		var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								staffID = $(this).attr("title");
								str++;
							}
						});
		if (str != 1) {
			alert('请选择具体一个往来个人!');
			return;
		}
		var str = 0;
	    $("[name='chbox']").each(function() {
			if ($(this).is(':checked')) {
				staffID = $(this).attr("title");
				str++;
			}
		});
	    if (str != 1) {
			alert('请选择具体一个往来个人!');
			return;
	    }
		var ajaxUrl = basePath +"ea/accommodhot/sajax_ea_getStaff.jspa?date=" + new Date().toLocaleString();
				$.ajax({
					url:encodeURI(ajaxUrl), 
					type:"get", 
					async:true, 
					dataType:"json",
					data:{
						"accHot.staffID":staffID
					}, 
					success:function cbf(data) {
						var member = eval("(" + data + ")");
						var i = member.i;
						if(i>0){
							alert("当前人员已分配住宿！");
							re_load();
							return
						}
						document.cstaffSearchForm.reset();
						hotelvo("","");
						$("#jqModelSearchses").jqmShow();
					},
					error : function cbf(data) {
							alert("数据获取失败！");
					}
				});
		}else{
			document.cstaffSearchForm.reset();
			$("#jqModelSearchses").jqmShow();
			editval(hotid);
		}	
}

function editval(hotid){
		var ajaxUrl = basePath +"ea/accommodhot/sajax_ea_editval.jspa?date=" + new Date().toLocaleString();
		$.ajax({
					url:encodeURI(ajaxUrl), 
					type:"get", 
					async:true, 
					dataType:"json",
					data:{
						"accHot.accommodHotID":hotid
					}, 
					success:function cbf(data) { 
						var member = eval("(" + data + ")");
						var accommod = member.accommod;
						var accHot = member.accHot;
						hotelvo(accommod,accHot);
					},
					error : function cbf(data) {
							alert("数据获取失败！");
					}
		});
	}
