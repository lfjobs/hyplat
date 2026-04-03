$(document).ready(function(){
	var SignExURL = signparam.SignExURL;
	var SignListUrl = signparam.SignListUrl;
	//$("input[type='button']").css({ color: "#000", background: "#ccc" });
	$(".jqmWindow").jqm({
		modal: true,// 限制输入（鼠标点击，按键）的对话  
		overlay: 20 // 遮罩程度%  
	}).jqmAddClose('.close');// 添加触发关闭的selector  
	//.jqDrag('.drag');// 添加拖拽的selector
	$("#flexi").flexigrid   
	({   
		url:SignListUrl+"?signmanagerid="+encodeURI(signmanagerid)+"&signid="+encodeURI(signid)+"&signstat="+
						signstat+"&relationtable="+relationtable+"&starttime="+starttime+"&endtime="+endtime,
		dataType: 'json',
		colModel : 
		[
			{ display : '用户', name : 'signmanagerid',width : 140,align : 'center'},
         	{ display : '印章名称', name : 'signid',width : 160,align : 'center'},
         	{ display : '印章位置', name : 'position',width : 120,align : 'center'},
         	{ display : '关联的表', name : 'relationtable',width : 120,align : 'center'},
         	{ display : '关联表的ID', name : 'relationid',width : 140,align : 'center'},
         	{ display : '盖章时间', name : 'datetime',width : 180,align : 'center'},
         	{ display : '印章状态', name : 'signstat',width : 100,align : 'center'},
			{ display : 'ID', name : 'signmanagerkey',width : 100,align : 'center', hide : 'true'}
		],
		buttons : 
		[   
			{name: '查询', bclass: 'query', onpress : button},                  
			{separator: true},
			{name: '查看', bclass: 'update', onpress : button}
		],
			usepager : true,
			title : '电子印章实例',
			useRp : true,
			checkbox : true,// 是否要多选框
			rowId : 'path',// 多选框绑定行的id
			rp : 10,
			showTableToggleBtn : true,
			width : 'auto',
			height : 400 
        });
	function button(com,grid)   
	{   
		if(com=="查询"){
			$("#jqModelSearch").jqmShow();
		}
		if(com=="查看"){
			var data = new Array();
			var selected_count = $('.trSelected', grid).length; 
			$('.trSelected td', grid).each(function(i) {  
				data[i] = $(this).children('div').text();
			});  
			if(selected_count==1)
			{
				var position = data[2];
				var signmanagerkey = data[7];
				document.location.href=SignExURL+"?signmanagerkey="+signmanagerkey+"&position="+position;
			}
			else if(selected_count>1)
			{
				alert("抱歉只能选择一条");
			}
			else if($(".trSelected").length==0)
			{
				alert("请选择一条记录");
			}
		}
	}
	//点击行时设置多选框选中
	$('table').click(function(){
		$('#Selected td').attr("checked", "checked");
	});
	//关闭查询div清空文本框
	$(".close").click(function(){
		$('#signid').val("");
		$('#endtime').val("");
		$('#signstat').val("");
		$('#starttime').val("");
		$('#signmanagerid').val("");
		$('#relationtable').val("");
	});
});
