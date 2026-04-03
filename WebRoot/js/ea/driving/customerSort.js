$(document).ready(function() {
	var len = $(".trclass").length;
	window.parent.document.getElementById(frameid).style.height = 115 + len * 27 + "px";
	$(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话  
        overlay: 20 // 遮罩程度%  
    }).jqmAddClose('.close');// 添加触发关闭的selector  
                
	$('.staffappraisal').flexigrid({
        height: 200,
        allDouble:true,
        width: 'auto',
        minwidth: 30,
        minheight: 80,
		 buttons: [{
			name : '查询',
			bclass : 'mysearch',
			onpress : action
		}, {
			separator : true
		}, {
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            separator: true
        }]
    });
    function action(com, grid){
        switch (com) {
			case '查询' :
		   	  	mainheight = parent.document.getElementById(frameid).offsetHeight;
				parent.document.getElementById(frameid).style.height = 200 + "px";
				$("#jqModelSearch").jqmShow();
				break;
		     case '设置每页显示条数':
			   	var url = basePath
						+ "/ea/customermanage/ea_getSortList.jspa?frameid="+frameid+"&goodsid="+goodsid+"&status1="+status1+"&search="+search;
				numback(url);
				break; 
        }
    }
    
    $(".close").click(function(){
    	parent.document.getElementById(frameid).style.height = mainheight + "px";
    });
    
    // 查询按钮单据事件
	$("#tosearch").click(function(){
		$("#SearchForm").attr("action",
				basePath + "/ea/customermanage/ea_toSearchSort.jspa?frameid="+frameid+"&goodsid="+goodsid+"&status1="+status1+"&pageNumber=" + pNumber);
		document.SearchForm.submit.click();
		document.SearchForm.reset();
	});
});