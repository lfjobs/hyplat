$(document).ready(function() {
            


	        
	        

		});
		
		
var url;
function newUser() {
	$('#dlg').dialog('open').dialog('setTitle', 'New User');
	
	$('#fm').form('clear');
	url ="nwa/bank/nwa_saveIpConf.jspa";
}
function editUser() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('setTitle', 'Edit User');
		
  
      $('#fm :input').each(function(){   
        var name = this.name;  
        
        var names = name.substr(name.indexOf(".")+1);
        $(this).attr('value', row[names]); 

      });    

	 
//		$('#fm').form('load', row); //用这种方法前提是name与json中的name相同
		
		
		url ="nwa/bank/nwa_saveIpConf.jspa";
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
					    $('#dg').datagrid('reload'); // reload the user data

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
						$.post(basePath+'nwa/bank/sajax_nwa_deleteIpConf.jspa', {
									"ipConf.key" : row.key
								}, function(result) {
									var result = eval('(' + result + ')');
									if (result.success) {
									$('#dg').datagrid('reload'); // reload the user data

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
