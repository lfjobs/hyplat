$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector


	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "<form  name='searchForm' method='post' id='searchForm' action='"+basePath+"ea/attence/ea_toSearchRecord.jspa'>员工考勤记录"+"&nbsp;&nbsp;&nbsp;&nbsp;<input type='submit' name='submit' style='display:none'/>" +
						"员工姓名/手机号/身份证：<input type='text' id='parameter' name='parameter' style='width:100px'/> &nbsp;开始时间：<input type='text' id='start' name='start' onfocus='date(this);' style='width:100px'/> &nbsp;结束时间：<input type='text' id='end' name='end' onfocus='date(this);' style='width:100px'/> 签到方式：<select id='sign' name='signType'><option value=''>全部</option><option value='01'>刷脸机器签到</option><option value='00'>手机APP签到</option></select><input type='submit' value=' 查询 ' id='toSearch' class='input-button'/> </form>",
				minheight : 80,
				buttons : [{
							name : '导出',
							bclass : 'excel',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						},  {
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					  } , {
							separator : true
				}]
	});

	function action(com, grid){
		switch (com) {
			case '查看':
                document.addForm.reset();
                $(".error").remove();
                posID  = "";
              $("#jqModeladd").jqmShow();
                break;
			
			case '导出':
				var url = basePath
				+ "ea/attence/ea_excelExport.jspa?parameter="+parameter+"&start="+start+"&end="+end+"&signType="+signType+"&pNumber="+pageNumber;
		open(url);
                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "/ea/attence/ea_getAttenceRcordPage.jspa?parameter="+parameter+"&start="+start+"&end="+end+"&signType="+signType+"&pNumber="+pageNumber;
				numback(url);
				break;
		}
    }
    

});

  
function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/pos/ea_getPosList.jspa?pageNumber=" + pNumber;
    }
}


