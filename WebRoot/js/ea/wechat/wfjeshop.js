$(document).ready(function() {
	if(inforType=="00"){
		$('.JQueryflexme').flexigrid({
			title:"微分金店管理",
	        height: 200,
	        allDouble:true,
	        width: 'auto',
	        minwidth: 30,
	        minheight: 80,
			 buttons: [{
		            name: '添加',
		            bclass: 'add',
					onpress : action//当点击调用方法
		        },{
					separator : true
				},{
	            name: '设置每页显示条数',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
	        },{
	            separator: true
	        }]
	    });
	}else{
		$('.JQueryflexme').flexigrid({
			title:"个人加盟管理",
	        height: 200,
	        allDouble:true,
	        width: 'auto',
	        minwidth: 30,
	        minheight: 80,
			 buttons: [{
		            name: '添加',
		            bclass: 'add',
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
			
			break;
        	case '添加' :
    			var url = basePath
    					+  "";
    			window.open(url, '','');
    			break;	
        	
		     case '设置每页显示条数':
			   	var url = basePath
						+ "/ea/marketingWfj/ea_getWfjshopaction.jspa?";
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
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/marketsurvey/ea_getListMarketSurvey.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
