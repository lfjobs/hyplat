$(document).ready(function() {
	
	

	var len = $("#tbwid").find(".trclass").length;
	window.parent.document.getElementById("mainframe23").style.height = 400 + len * 27 + "px";
		  $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
//                .jqDrag('.drag');// 添加拖拽的selector
		
        $('.address').flexigrid({
        	height : 400,
			width : 'auto',
			title:'网站配置',
			minwidth : 30,
            buttons: [{
                name: '添加',
                bclass: 'add',
                onpress: action//当点击调用方法
            }, {
               separator: true
        	},{
	            name: '修改',
	            bclass: 'edit',
				onpress : action//当点击调用方法
            },{
                separator: true
            },{
	            name: '删除',
	            bclass: 'delete',
				onpress : action//当点击调用方法
            },{
            	separator: true
            },{
	            name: '全部保存',
	            bclass: 'add',
				onpress : action//当点击调用方法
        	}, {
             separator: true
        	},{
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
        /* 验证公司简介只有一个，保证查询往来单位时有公司简介的只有一条*/
    	var url=basePath+"/ea/ccomconf/sajax_n_ea_ajaxCheck.jspa?ccomConf.ccompanyId="+ccompanyID;
    	$.ajax({
    		url : encodeURI(url),
    		type : "get",
    		async : true,
    		dataType : "json",
    		success : function(data){
    			var member= eval ("("+data+")");
    			var temp=member.flag;
    			if(temp=="0"){
            		$("#mT").append("<option value='0'>公司简介</option>");
            		}
    		},
    		error : function(data){
    			alert("数据加载失败！");
    		}
    	});
        
        
        function action(com, grid){
            switch (com) {
                case '添加':
               window.open(basePath+"ea/ccomconf/ea_findAddOrEditPage.jspa?ccomConf.ccompanyId="+ccompanyID);
            
				break;
                case '修改':
				  if(certificateID=='')
				  {
				   alert("请选择！");
				   return;
				  }


			  window.open(basePath+"ea/ccomconf/ea_findAddOrEditPage.jspa?ccomConf.ccompanyId="+ccompanyID+"&ccomConf.ccomConfId="+certificateID);
			  break;
			 case '全部保存':
			      if(notoken)return;
			      if(select==1)
				     {
					     return;
				     }
				     	var re = 0;
				$("input.aaaa", $(".check")).each(function() {
							var panduan = this.value;
							var $s = $(this);
							if ((panduan == "")||(panduan ==" ")) {
								$s.css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					alert("不能为空!");
					notoken = 0;
					return;
				}
		           notoken = 1;
			      $f = $('#ccomconfForm');
				   $('#ccomconfForm').attr("target","hidden").attr("action",basePath+"ea/ccomconf/ea_saveCconConf.jspa?pageNumber="+ppageNumber);
				   
				   document.ccomconfForm.submit.click();
				   token = 2;
				 break;
			 case '删除':
			     if(certificateID=='')
				  {
				   alert("请选择！");
				   return;
				  }
				  if(certificateID.substring(0,2) == "sa")
				  {
				      $("#"+certificateID).remove();
				      certificateID="";
				    return;
				  }
				  $f = $('#ccomconfForm');
			      if (confirm("是否删除？")) {
			    	  
					  $f.attr("target","hidden").attr("action",basePath+"ea/ccomconf/ea_delCcomConf.jspa?pageNumber="+ppageNumber+"&ccomConf.ccomConfId="+certificateID);
					  document.ccomconfForm.submit.click();
					  $("tr#"+certificateID).remove();
					  certificateID = '';
					  token = 11;
					    
			 	  }
				break;
                case '查询':
                    $("#jqModelSearch").jqmShow();
                    break;
                case '设置每页显示条数':
				  var url = basePath
						+ "/ea/ccomconf/ea_getCconConfList.jspa?ccomConf.ccompanyId="+ccompanyID
						+ "&search=" + pserch;
					numback(url);
					break;     
            }
        }
 
     $(".address tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    certificateID =this.id;
                }) ;               
    $(".address tr[id]").dblclick(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    certificateID =this.id;
                    action("修改");
                });

      $("#tosearch").click(function(){
	  	 $("form :input").trigger("blur");
	  	 if ($("form .error").length) {
   	   		  return false;
 		   }
          $("#postSearchForm").attr("action", basePath+"ea/ccomconf/ea_toSearch.jspa?pageNumber="+ppageNumber+"&ccomConf.ccompanyId="+ccompanyID);
          document.postSearchForm.submit.click();
      });
});
function re_load(){
 	if(token)
    document.location.href=basePath+"ea/ccomconf/ea_getCconConfList.jspa?ccomConf.ccompanyId="+ccompanyID+"&pageNumber="+ppageNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
    }