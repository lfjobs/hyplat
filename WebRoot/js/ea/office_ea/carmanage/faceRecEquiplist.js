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
				title : "人脸闸机管理"+"&nbsp;&nbsp;&nbsp;&nbsp;设备号：<input type='text' id='ch' style='width:100px'/> <input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
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
						}, {
                    name : '实时视频',
                    bclass : 'see',
                    onpress : action
                    // 当点击调用方法
                } , {
                    separator : true
                },{
                    name : '监听',
                    bclass : 'see',
                    onpress : action
                    // 当点击调用方法
                } , {
                    separator : true
                },{
                    name : '查看在线状态',
                    bclass : 'see',
                    onpress : action
                    // 当点击调用方法
                } , {
                    separator : true
                },{
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
                document.addForm.reset();
                $(".error").remove();
                frId  = "";
                $("#jqModeladd").jqmShow();
                break;
			case '修改' :
				if (frId == "") {
					alert("请选择！");
					return;
				}
                document.addForm.reset();
                $t = $("table#addTable");
                $p = $("tr#" + frId);
                $p.find("span[id]").each(function() {
                    $t.find("#" + this.id).val($(this).text());

                });
                $("#jqModeladd").jqmShow();

                
                break;
			case '删除':
                if (frId == "") {
                    alert("请选择！");
                    return;
                }
                if(confirm("确定删除？")) {
                    var frkey = $("tr#" + frId).find("#frkey").text();
                    $("#addForm #frkey").val(frkey);
                    $("#addForm").attr("target", "hidden").attr("action", basePath + "ea/facerec/ea_deleteFaceRec.jspa");
                    document.addForm.submit.click();
                    token = 2;
                }
                break;
            case '实时视频':
                if (frId == "") {
                    alert("请选择！");
                    return;
                }

                break;
            case '监听':
                if (frId == "") {
                    alert("请选择！");
                    return;
                }
                var sn = $("tr#" + frId).find("#sn").text();
                var bool = null;
                var url = basePath + "ea/facerec/sajax_ea_startService.jspa?date="
                    + new Date();
                $.ajax({
                    url : url,
                    type : "get",
                    async : false,
                    dataType : "json",
                    data : {
                        "faceRec.sn" : sn
                    },
                    success : function(data) {
                        var member = eval("(" + data + ")");
                        var configServer = member.configServer;
                        var dataServer = member.dataServer;
                        alert(configServer);
                        alert(dataServer);


                    },
                    error : function(data) {
                        console.log("启动服务失败");
                    }

                });
                break;

            case '查看在线状态':
                if (frId == "") {
                    alert("请选择！");
                    return;
                }
                var sn = $("tr#" + frId).find("#sn").text();
                var bool = null;
                var url = basePath + "ea/facerec/sajax_ea_getCameraOnlineState.jspa?date="
                    + new Date();
                $.ajax({
                    url : url,
                    type : "get",
                    async : false,
                    dataType : "json",
                    data : {
                        "faceRec.sn" : sn
                    },
                    success : function(data) {
                        var member = eval("(" + data + ")");
                        var onlineState = member.onlineState;

                        alert(onlineState);


                    },
                    error : function(data) {
                        console.log("启动服务失败");
                    }

                });
                break;
            case '设置每页显示条数' :
			var url = basePath
						+ "ea/facerec/ea_getListFaceRec.jspa?search="
						+ search;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        frId = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

    $(".flexme11 tr[id]").dblclick(function() {
        frId = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

        action("修改");
    });

	//保存
	$("#save").click(function() {
        $(".put3").trigger("blur");
        $(".posNum").trigger("blur");
        if($("#addForm .error").length>0){
        	 return;
		}
		$("#addForm").attr("target","hidden").attr(
				"action",
				basePath + "ea/facerec/ea_addOrUpdate.jspa");
        token = 2;
		document.addForm.submit.click();
	});






    // 判断设备号重复
    $("#addTable .posNum").live("blur", function() {
        $input = $(this);
        $parent = $input.parent();
        var inputValue = $input.attr("value");

        if ($input.is(".posNum")) {
            if (inputValue != "") {

                    if (!checkPosNum(inputValue)) {
                        $parent
                            .append("<span class=\"error\"><a class=\"tex\">设备号已存在</a></span>");
                        return;
                    } else {
                        $parent
                            .append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
                        return;
                    }

            } else {
                $parent
                    .append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
            }
        }
    });

	//查询
    $("#toSearch").click(function() {
        $("#searchForm").find("#posNum").val($("#ch").val());

        $("#searchForm").attr(
            "action",
            basePath + "ea/facerec/ea_toSearch.jspa");
        token = 2;
        document.searchForm.submit.click();
    });

});

// 判断设备号重复
function checkPosNum(posNum) {

    var bool = null;
    var url = basePath + "ea/facerec/sajax_ea_checkRepPosNum.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data : {
            "faceRec.sn" : posNum,
            "faceRec.frId":frId
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var result = member.result;
            if (result == "1") {// 重复
                bool = false;
            } else {
                bool = true;// 不重复
            }

        },
        error : function(data) {
            console.log("读取数据失败");
        }

    });

    return bool;
}

function re_load() {
    if(token) {
        document.location.href = basePath
            + "ea/facerec/ea_getListFaceRec.jspa?pageNumber=" + pNumber;
    }
}