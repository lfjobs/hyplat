
$(document).ready(function(){
		  $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
                //.jqDrag('.drag');// 添加拖拽的selector
        $('.address').flexigrid({
            height: 145,
            width: 'auto',
            minwidth: 30,
            title: '企业印章使用管理',
            minheight: 80,
            buttons: [{
                name: '添加',
                bclass: 'add',
                onpress: action
            },{
                     separator: true
            }, {
                   name: '删除',
                   bclass: 'delete',
                   onpress: action
             }, {
                  separator: true
              }, {
                   name: '修改位置',
                   bclass: 'updatePosition',
                   onpress: action
                }, {
                 	separator: true
                },{
					name: '修改状态',
                   bclass: 'updateSignstat',
                   onpress: action
				},{
					separator: true
				}
	        ]
	        });
        function action(com, grid){
          switch (com) {
		  	case '添加':
            	break;
			case '删除':
				break;
			case '修改位置':
				$("#showSignManager").jqmShow();
				//$("#jqModel").jqmShow();
				//$("#showSignManager").show();
				break;
			case '修改状态':
				break;			
		  }
      }
});
