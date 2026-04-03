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
				title : storeName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设备号SN：<input type='text' id='ch' style='width:100px'/><input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons : [{
							name : '添加设备',
							bclass : 'add',
							onpress : action
							// 当点击调用方法
					}		, {
							separator : true
						},  {
                    name : '解绑设备',
                    bclass : 'delete',
                    onpress : action
                    // 当点击调用方法
                }	,  {
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
			case '添加设备':
                document.addForm.reset();
                $(".error").remove();
                pfdID  = "";
              $("#jqModeladd").jqmShow();
                break;

			case '解绑设备':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                var sn = $("tr#" + pfdID).find("#sn").text();
                if(confirm("确定解绑设备"+sn+"?")) {
                    $("#addForm .posNum").val(sn);
                    $("#addForm").attr("target", "hidden").attr("action", basePath + "ea/face/ea_removeStoreBind.jspa");
                    document.addForm.submit.click();
                    token = 2;
                }
                break;
            case '绑定设备':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                var sn = $("tr#" + pfdID).find("#sn").text();
                if(sn!=null&&sn!=""){
                    alert("此商户已绑定设备，如需重新绑定，请先解绑");
                    return false;
                }
                var sn = $("tr#" + pfdID).find("#sn").text();

                $("#bindTable #sn").val(sn);

                $("#jqModelbind").jqmShow();
                break;
            case '解绑设备':
                url = basePath
                    + "ea/face/ea_showExcel.jspa?search="
                    + search;
                open(url);
                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/face/ea_getStoreAllDevice.jspa?search="
						+ search+"&bindDevice.subCompanyID="+subCompanyID+"&bindDevice.storeName="+storeName;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        pfdID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

    $(".flexme11 tr[id]").dblclick(function() {
        pfdID = this.id;
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
				basePath + "ea/face/ea_storeBindDevice.jspa");
        token = 2;
		document.addForm.submit.click();
	});


     //验证序号
    $("#addTable .posNum").live("blur", function() {
        $input = $(this);
        $parent = $input.parent();
        var inputValue = $input.attr("value");

        if ($input.is(".posNum")) {
            if (inputValue != "") {

                    if (checkPosNum(inputValue)=="1") {
                        $parent
                            .append("<span class=\"error\"><a class=\"tex\">系统无此设备号请录入</a></span>");
                        return;
                    } if (checkPosNum(inputValue)=="2") {
                    $parent
                        .append("<span class=\"error\"><a class=\"tex\">设备没有分配业务员</a></span>");
                    return;
                    }if (checkPosNum(inputValue)=="3") {
                    $parent
                        .append("<span class=\"error\"><a class=\"tex\">该设备已经绑定过商户</a></span>");
                    return;
                   }else {
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
            basePath + "ea/face/ea_toBDeviceSearch.jspa");
        token = 2;
        document.searchForm.submit.click();
    });


});

function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/face/ea_getStoreAllDevice.jspa?pageNumber=" + pNumber+"&bindDevice.subCompanyID="+subCompanyID+"&bindDevice.storeName="+storeName;
    }
}
// 判断设备号重复
function checkPosNum(posNum) {
    var result = "0";

    var bool = null;
    var url = basePath + "ea/face/sajax_ea_checkBindSn.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data : {
            "bdevice.sn" : posNum,
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            result = member.result;

        },
        error : function(data) {
            console.log("读取数据失败");
        }

    });

    return result;
}

