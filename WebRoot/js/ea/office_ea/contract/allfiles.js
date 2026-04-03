var siteId = "";
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
				title : "学员培训协议汇总"+"&nbsp;&nbsp;&nbsp;&nbsp;学员姓名/身份证号/手机号：<input type='text' id='ch' style='width:150px'/> &nbsp; <input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons : [{
							name : '查看',
							bclass : 'see',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						},
                    // {
					// 		name : '下载',
					// 		bclass : 'load',
					// 		onpress : action
					// 		// 当点击调用方法
					// }	,
                    {
                    name : '上传公章',
                    bclass : 'delete',
                    onpress : action
                    // 当点击调用方法
                }, {
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
			case '查看':
                if (docId == "") {
                    alert("请选择！");
                    return;
                }

                var ulp = basePath
                    + "ea/contract/sajax_ea_viewUrl.jspa";
                $.ajax({
                    type: "GET",
                    url: ulp,
                    async: false,
                    dataType: "json",
                    data: {
                        "doc.docId": docId
                    },
                    success: function (data) {
                        var member = eval('(' + data + ')');
                        var viewUrl = member.viewUrl;
                        open(viewUrl);


                    },
                    error: function (data) {

                        console.log("获取链接失败");
                    }


                });
                break;
			case '下载' :
				if (docId == "") {
					alert("请选择！");
					return;
				}

                var ulp = basePath
                    + "ea/contract/sajax_ea_getLoadLink.jspa";
                $.ajax({
                    type: "GET",
                    url: ulp,
                    async: false,
                    dataType: "json",
                    data: {
                        "doc.docId": docId
                    },
                    success: function (data) {
                        var member = eval('(' + data + ')');
                        var loadUrl = member.loadUrl;

                        open(loadUrl);


                    },
                    error: function (data) {

                        console.log("获取链接失败");
                    }


                });
                break;
			case '上传公章':
                document.addForm.reset();
                $("#jqModeladd").jqmShow();

                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/contract/ea_getAllFileList.jspa?search="
						+ search;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        docId = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});






	//查询
    $("#toSearch").click(function() {
        $("#searchForm").find("#parameter").val($("#ch").val());

        $("#searchForm").attr(
            "action",
            basePath + "ea/contract/ea_toSearch.jspa");
        token = 2;
        document.searchForm.submit.click();
    });


    //保存
    $("#save").click(function() {
        $(".put3").trigger("blur");
        if($("#addForm .error").length>0){
            return;
        }
        $("#addForm").attr("target","hidden").attr(
            "action",
            basePath + "ea/contract/ea_uploadStamp.jspa");
        token = 2;
        document.addForm.submit.click();
    });



});

