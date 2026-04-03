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
				title : "电子秤管理"+"&nbsp;&nbsp;&nbsp;&nbsp;称号：<input type='text' id='ch' style='width:100px'/> IP地址:<input type='text'  style='width:100px' id='ip'/><input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons : [{
							name : '添加',
							bclass : 'add',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '修改',
							bclass : 'edit',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '删除',
							bclass : 'delete',
							onpress : action
							// 当点击调用方法
					   }	,  {
							separator : true
						},{
                    name : '预置键',
                    bclass : 'excel',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                }, {
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
			case '添加':
                scId  = "";
              $("#jqModeladd").jqmShow();
                break;
			case '修改' :
				if (scId == "") {
					alert("请选择！");
					return;
				}
                document.addForm.reset();
                $t = $("table#addTable");
                $p = $("tr#" + scId);
                $p.find("span[id]").each(function() {
                    $t.find(":input[name]#" + this.id).val($(this).text());
                });
                $("#jqModeladd").jqmShow();
                break;
			case '删除':
                if (scId == "") {
                    alert("请选择！");
                    return;
                }
                if(confirm("确定删除？")) {
                    var scKey = $("tr#" + scId).find("#scKey").text();
                    $("#addForm #scKey").val(scKey);
                    $("#addForm").attr("target", "hidden").attr("action", basePath + "ea/scale/ea_deleteScale.jspa");
                    document.addForm.submit.click();
                    token = 2;
                }
                break;

            case '预置键':
                document.location.href = basePath+"ea/scale/ea_getPreKeyList.jspa";
                break;
		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/scale/ea_getScaleList.jspa?search="
						+ search;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        scId = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

	$("#save").click(function() {

        $("#addForm :input").blur();
        if($("#addForm .error").length>0){
        	 return;
		}
		$("#addForm").attr("target","hidden").attr(
				"action",
				basePath + "ea/scale/ea_addOrUpdate.jspa");
        token = 2;
		document.addForm.submit.click();
	});

    $("#toSearch").click(function() {
        $("#searchForm").find("#caleNo").val($("#ch").val());
        $("#searchForm").find("#address").val($("#ip").val());
        $("#searchForm").attr(
            "action",
            basePath + "ea/scale/ea_toSearch.jspa");
        token = 2;
        document.searchForm.submit.click();
    });

});

function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/scale/ea_getScaleList.jspa?pageNumber=" + pNumber;
    }
}


