$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '邀请我的会议',
				minheight : 80,
				buttons : [{
					name : '加入会议',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '会议号加入会议',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				} ]
			});
	function action(com, grid) {
		switch (com) {
			case '加入会议' :
				if (roomid == "") {
					alert('请选择!');
					return;
				}
				var url = basePath + "ea/videoroom/sajax_ea_getRoomPsw.jspa";
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data:{
						roomid:roomid
					},
					success : function(data) {
						var me = eval("(" + data + ")");
						var roompsw = me.result;
						window.open("https://47.94.154.17:8443/launch/toEnterMeeting.do?roomID="+roomid+"&userName="+user+"&userPwd=123456wfj&roomPwd="+roompsw);
						

					},
					error : function(data) {
						console.log("添加用户失败");
					}

				});
				break;
		
			case '会议号加入会议' :
				document.joinMeeting.reset();
				
				$(".psw").hide();
				$("#jqModelJoin").jqmShow();
				
				break;
		
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
		roomid = this.id;
				action('立即加入');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
		roomid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	var js = 0;
	//加入会议
	$("#join").click(function(){
		
		
		var roomid = $("#rom").val();
		if(roomid==""){
			return;
		}

			var url = basePath + "ea/videoroom/sajax_ea_getRoom.jspa";
			$.ajax({
				url : url,
				type : "get",
				async : false,
				data:{
					roomid:roomid
				},
				dataType : "json",
				success : function(data) {
					var me = eval("(" + data + ")");
					 var roompsw = me.result;
					 var num = 0;
					if(roompsw!=""&&roompsw!=null){
						
						$(".psw").show();
						if(js==0){
						   $("#psw").val("");
						}
						js=1;
					}else{
						$(".psw").hide();
						num = 1;
						if(roompsw==null){
							alert("会议号不存在或没有正式发布通知");
							return;
						}
					}
					
					
					$(".put3").trigger("blur");
					

					if ($("#jqModelJoin .error").length>num){
						return;
					}else{
						if($("#psw").val()!=roompsw){
							
							alert("密码错误");
							return;
						}
						
					}
					
					
					joinMeeting(roompsw,roomid);
					
				

				},
				error : function(data) {
					console.log("获取密码失败");
				}

			});

		
		
	
		
	})


});



	


function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/meetingroom/ea_getMeetingRoomList.jspa?search=" + psearch
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
// 加入会议并授权
function joinMeeting(roompsw,roomid){

	var url = basePath + "ea/videoroom/sajax_ea_doUserRightByRoomid.jspa";
	$.ajax({
		url : url,
		type : "get",
		async : false,
		data:{
			roomid:roomid,
			user:user
		},
		dataType : "json",
		success : function(data) {
			window.open("https://47.94.154.17:8443/launch/toEnterMeeting.do?roomID="+roomid+"&userName="+user+"&userPwd=123456wfj&roomPwd="+roompsw);

		},
		error : function(data) {
			console.log("获取密码失败");
		}

	});

	

}
