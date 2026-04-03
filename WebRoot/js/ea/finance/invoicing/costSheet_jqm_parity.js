 $(function(){
             $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector 
      
       $(".jqmreturn").click(function(){
    		notoken = 0;
     		$("#documentsjqModel").jqmHide();
      		$("#previewjqModel").jqmHide();
      		$("#journalNumAjax").attr("value","");
      		$("#taxDateAjax").attr("value","");
      		showDocument=false;
    	});
      
                $('.flexme11').flexigrid({
                    height: 350,
                    width: 'auto',
                    minwidth: 30,
                    title: "比价管理",
                    minheight: 80,
                    buttons: [{
                        name: '查询',
                        bclass: 'mysearch',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    },{
		            name: '设置每页显示条数',
		            bclass: 'mysearch',
					onpress : action//当点击调用方法
		        },{
		            separator: true
		        }]
                });
				
                function action(com, grid){
                    switch (com) {
                        case '设置每页显示条数':
						    var url = basePath
							+ "/ea/costsheet/ea_getCostSheetList.jspa?search="
							+ search + "&sdate=" + sdate + "&edate=" + edate
							+ "&type=" + type+"&treeType="+treeType+"&jumptype="+jumptype+"&parity=00";
							numback(url);
							break;  
					    case '查询':
                            $("#jqModelSearch").jqmShow();
                            $("input#journalNum").focus();
                            break;
                    }
                }
                //这一行的单击事件
				 $(".chx").live("click", function(event){
			        var b = $(this).attr("checked");
			        $(this).attr("checked", !b);
			    });
			    $(".flexme11 tr[id]").click(function(){
			        var d = $("input.chx", $(this)).attr("checked");
			        $("input.chx", $(this)).attr("checked", !d);
			    });
                //查询按钮单击事件
                 $("#tosearch").click(function(){
                 	if(treeType==""){
                        treeType = "0";
                    }
                    $("#SearchForm").attr("action", basePath+"/ea/costsheet/ea_toSearch.jspa?pageNumber="+pNumber+"&treeType="+treeType+"&jumptype="+jumptype+"&parity=00");
                    document.SearchForm.submit.click();
                });
});

function re_load(){
	document.location.href=basePath+"/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype+"&parity=00";
}



	          