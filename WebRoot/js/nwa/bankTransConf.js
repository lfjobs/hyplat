$(document).ready(function() {
            getBanksFromXml();// 加载银行信息	
            
//            var enameSx = $("#bank").find("option:selected").val();
//            $('#dg').datagrid({
//				pageSize : 5,
//				pageList : [5, 10, 15, 20],
//				nowrap : false,
//				striped : true,
//				collapsible : true,
//				url : basePath+"nwa/bank/datagrid_nwa_getTransConfByBank.jspa",
//				queryParams:{
//                "transConf.enameSx":enameSx
//                },
//				sortName : 'code',
//				sortOrder : 'desc',
//				remoteSort : false,
//				frozenColumns : [[
//
//				]],
//				pagination : true,
//				rownumbers : true
//
//			});
//    $('#dg').datagrid('getPager').pagination({
//         displayMsg:'当前显示从{from}到{to}共{total}记录',
//         onChangePageSize:function(pageSize){
//         	 var enameSx = $("#bank").find("option:selected").val();
//             $("#dg").attr("url",basePath+"nwa/bank/datagrid_nwa_getTransConfByBank.jspa?transConf.enameSx="+enameSx);
//           
//         },
//         onBeforeRefresh:function(pageNumber, pageSize){
//         var enameSx = $("#bank").find("option:selected").val();
//      	 $("#dg").attr("url",basePath+"nwa/bank/datagrid_nwa_getTransConfByBank.jspa?transConf.enameSx="+enameSx);
//          
//         $(this).pagination('loading');
//         $(this).pagination('loaded');
//    }
//});
			$("#bank").change(function() {
				
			var enameSx = $(this).find("option:selected").val();
            
             getTransConfByBank(enameSx);

			});
					
			// 保存参数设置
					
//		$("#save").click(function() {
//
//		    	$("#fm").attr("action",basePath+url);
//	            document.fm.submit.click();
//	            alert("操作成功");
//
//	        });
	        
	        

		});
		
		
		

//读取银行信息
function getBanksFromXml() {
	var xmlpath = "xmlConf/bank.xml";
	var url = basePath + "nwa/bank/sajax_nwa_getBanksFromXml.jspa";

	$.ajax({

				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					xmlpath : xmlpath
				},
				success : function(data) {
					var mem = eval("(" + data + ")");
					var result = mem.result;
					$(parent.document.getElementById("bank")).html(result);
					$("#bank").html(result);
				},
				error : function(data) {
					alert("获取银行信息失败");
				}

			});
}
var url;
function newUser() {
	var enameSx = $("#bank").find("option:selected").val();
	$('#dlg').dialog('open').dialog('setTitle', 'New User');
	
	$('#fm').form('clear');
	$("#enameSxs").val(enameSx);
	url ="nwa/bank/nwa_saveTransConf.jspa";
}
function editUser() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('setTitle', 'Edit User');
		
  
      $('#fm :input').each(function(){   
        var name = this.name;  
        
        var names = name.substr(name.indexOf(".")+1);
        $(this).attr('value', row[names]); 
        if(names=="isUsed"){
        	$(this).find("select#" + name).attr("value",
												row[names]);
        }

      });    

	 
//		$('#fm').form('load', row); //用这种方法前提是name与json中的name相同
		
		
		url ="nwa/bank/nwa_saveTransConf.jspa";
	}
}
function saveUser() {
	$('#fm').form('submit', {
				url : basePath+url,
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
//					var result = eval('(' + result + ')');
//					if (result.errorMsg) {
//						$.messager.show({
//									title : 'Error',
//									msg : result.errorMsg
//								});
//					} else {
						$('#dlg').dialog('close'); // close the dialog
//						$('#dg').datagrid('reload'); // reload the user data
						var enameSx = $("#bank").find("option:selected").val();
						getTransConfByBank(enameSx);
						$('#fm').form('clear');
//					}
				}
			});




  
			
			
//	$('#fm').submit(function()// 提交表单
//			{
//				var options = {
//					target : '#Tip', // 后台将把传递过来的值赋给该元素
//					url : basePath+url, // 提交给哪个执行
//					type : 'POST',
//					success : function() {
//						alert($('#Tip').text());
//					} // 显示操作提示
//				};
//				$('#fm').ajaxSubmit(options);
//				return false; //为了不刷新页面,返回false，反正都已经在后台执行完了，没事！ 
//			});

	
}
function destroyUser() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm',
				'Are you sure you want to destroy this record?', function(r) {
					if (r) {
						$.post(basePath+'nwa/bank/sajax_nwa_deleteConf.jspa', {
									"transConf.key" : row.key
								}, function(result) {
									var result = eval('(' + result + ')');
									if (result.success) {
//										$('#dg').datagrid('reload'); // reload the user data
										var enameSx = $("#bank").find("option:selected").val();
						                getTransConfByBank(enameSx);
									} else {
										$.messager.show({ // show error message
											title : 'Error',
											msg : result.errorMsg
										});
									}
								}, 'json');
					}
				});
	}
}

function getTransConfByBank(enameSx) {

	var url = basePath + "nwa/bank/sajax_nwa_getTransConfByBank.jspa";

	$.ajax({

				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"transConf.enameSx" : enameSx
				},
				success : function(data) {
					var mem = eval("("+data+")");
					if(data!=null){
					$('#dg').datagrid('loadData',mem);
					}else{
						$('#dg').datagrid('loadData',{total:0,rows:[]});
					}

				},
				error : function(data) {
					alert("获取银行参数信息失败");
				}

			});
	
	
	

}