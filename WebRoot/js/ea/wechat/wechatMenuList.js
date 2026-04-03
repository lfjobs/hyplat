$(document).ready(function() {
	
	$('.JQueryflexme').flexigrid({
		title:"微信菜单",
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
            name: '修改',
            bclass: 'edit',
			onpress : action//当点击调用方法
        },{
			separator : true
		}/*,{
            name: '删除',
            bclass: 'delete',
			onpress : action//当点击调用方法
        },{
			separator : true
		}*/,{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
    });
    function action(com, grid){
        switch (com) {
        	case '添加' :
        		$("input.JQuerypersonvalue").attr("checked", false);
        		$("#jqModel").show();
        		$("#pimage").hide();
			break;	
			case '修改' :
			
			if (menuId == "") {
				alert('请选择一条数据!');
				return;
			}
			var url = basePath + "ea/wechatmenu/getMenuById.jspa?menuId="+menuId;
			
			window.open(url, '','scrollbars=yes,resizable=yes,channelmode,width=910,left=250,top=100');
			break;			
			case '删除' :
				if (menuId == "") {
					alert('请选择!');
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#menuId').val(menuId);
				if (confirm("确定继续？")) {
					$f.attr(
									"action",
									basePath
									+  "ea/wechatmenu/delMenu.jspa?menuId="+menuId+"&menuPid="+menuPid);
					$("#submit").click();
					token = 11;
				}

				break;	
		     case '设置每页显示条数':
			   	var url = basePath
						+ "ea/wechatmenu/getMenuList.jspa?menuPid="+menuPid;
				numback(url);
				break; 
        }
    }
    $(".close").click(function() {// 取消
		$("#jqModel").hide();
		
	});
    $(".JQueryreturn").click(function() {
		$("#jqModel").hide();
	});

    $("input#tosave").click(function() {
    	var hidIdList=$("#hidIdList").val();
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/wechatmenu/addWechatMenu.jspa?pageNumber="
								+ pNumber+"&menuPid="+menuPid+"&menuId="+menuId+"&hidIdList="+hidIdList);

		$("#submit").click();
		token = 2;
		//window.close();
	});
    $(".JQueryflexme tr[id]").click(function() {
    	menuId = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	 $(".JQueryflexme tr[id]").dblclick(function(){
	 	$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	 	menuId =this.id;
        action("修改");
                });
});

function re_load(){
	if(token){
		document.location.href =basePath+"ea/wechatmenu/getMenuList.jspa?menuPid="+menuPid;
	}
}