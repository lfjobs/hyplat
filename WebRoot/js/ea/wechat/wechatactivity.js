$(document).ready(function() {
	if(inforType=="00"){
		$('.JQueryflexme').flexigrid({
			title:"微信活动",
	        height: 200,
	        allDouble:true,
	        width: 'auto',
	        minwidth: 30,
	        minheight: 80,
			 buttons: [{
		            name: '发布活动',
		            bclass: 'add',
					onpress : action//当点击调用方法
		        },{
					separator : true
				},{
	            name: '报名信息',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
	        },{
				separator : true
			},{
	            name: '活动详情',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
	        },{
				separator : true
			},{
	            name: '删除',
	            bclass: 'delete',
				onpress : action//当点击调用方法
	        },{
				separator : true
			}, {
	            name: '设置每页显示条数',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
	        },{
	            separator: true
	        }]
	    });
	}else{
		$('.JQueryflexme').flexigrid({
			title:"公共信息",
	        height: 200,
	        allDouble:true,
	        width: 'auto',
	        minwidth: 30,
	        minheight: 80,
			 buttons: [{
		            name: '发布信息',
		            bclass: 'add',
					onpress : action//当点击调用方法
		        },{
					separator : true
				},{
	            name: '信息详情',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
	        },{
				separator : true
			},{
	            name: '删除',
	            bclass: 'delete',
				onpress : action//当点击调用方法
	        },{
				separator : true
			}, {
	            name: '设置每页显示条数',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
	        },{
	            separator: true
	        }]
	    });
	}
	
    function action(com, grid){
        switch (com) {
        	case '发布活动' :
			var url = basePath
					+  "ea/activity/ea_publishLogin.jspa?inforType=00";
			window.open(url, '','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=1300,height=750,top=20, left=30');
			break;	
        	case '报名信息' :
			if (activityId == "") {
				alert('请选择一条活动');
				return;
			}
			document.location.href=basePath+"ea/activity/ea_listByActivityId.jspa?activityId="+activityId;
			/*var url = basePath
					+  "ea/activity/ea_listByActivityId.jspa?activityId="+activityId;
			window.open(url, '','height=500, width=900, top=100, left=100');*/
			break;
        	case '发布信息' :
    			var url = basePath
    					+  "ea/activity/ea_publishLogin.jspa?inforType=01";
    			window.open(url, '','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=1300,height=750,top=20, left=30');
    			break;	
        	case '信息详情' :
				if (activityId == "") {
					alert('请选择一条信息！');
					return;
				}
				var url = basePath
						+  "ea/activity/ea_activityDetails.jspa?activityId="+activityId;
				window.open(url, '','height=750, width=1300, top=20, left=30');
				break;	
			case '活动详情' :
				if (activityId == "") {
					alert('请选择一个产品');
					return;
				}
				var url = basePath
						+  "ea/activity/ea_activityDetails.jspa?activityId="+activityId;
				window.open(url, '','height=750, width=1300, top=20, left=30');
				break;
			case '删除' :
				if (activityId == "") {
					alert('请选择!');
					return;
				}
				$f = $('#addressForm');
				if (confirm("确定继续？")) {
					$f.attr(
									"action",
									basePath
									+  "ea/activity/ea_activityDelete.jspa?activityId="+activityId+"&inforType="+inforType);
					document.addressForm.submit.click();
					token = 11;
				}

				break;	
		     case '设置每页显示条数':
			   	var url = basePath
						+ "ea/activity/ea_activityList.jspa?frameid="+frameid+"&search="+search+"&inforType="+inforType;
				numback(url);
				break; 
		    
        }
    }
    
    $(".close").click(function(){
    	parent.document.getElementById(frameid).style.height = mainheight + "px";
    });
    $(".JQueryflexme tr[id]").click(function() {
    	activityId = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	 $(".JQueryflexme tr[id]").dblclick(function(){
	 	$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	 	activityId =this.id;
        action("活动详情");
                });
});