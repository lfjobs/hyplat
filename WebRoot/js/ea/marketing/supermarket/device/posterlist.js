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
				title : (deviceType=="00"?"微信海报设置":"支付宝海报设置")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;海报名称：<input type='text' id='ch' style='width:100px'/><input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
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
						},  {
                    name : '删除',
                    bclass : 'delete',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },{
                    name : '上线',
                    bclass : 'add',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },{
                    name : '下线',
                    bclass : 'delete',
                    onpress : action
                    // 当点击调用方法
                },{
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
                spID  = "";
              $("#jqModeladd").jqmShow();
                break;
			case '修改' :
				if (spID == "") {
					alert("请选择！");
					return;
				}
                document.addForm.reset();
                $t = $("table#addTable");
                $p = $("tr#" + spID);
                $p.find("span[id]").each(function() {
                    $t.find("#" + this.id).val($(this).text());

                });
                $("#jqModeladd").jqmShow();
                break;
			case '删除':
                if (spID == "") {
                    alert("请选择！");
                    return;
                }
                if(confirm("确定删除？")) {
                    var spKey = $("tr#" + spID).find("#spKey").text();
                    $("#searchForm #spKey").val(spKey);
                    $("#searchForm").attr("target", "hidden").attr("action", basePath + "ea/face/ea_deletePoster.jspa");
                    document.searchForm.submit.click();
                    token = 2;


                }
                break;
            case '上线':
                if (spID == "") {
                    alert("请选择！");
                    return;
                }
                var isPublish = $("tr#" + spID).find("#isPublish").text();
                 if(isPublish=="00"){
                     if(confirm("确定上线？")) {
                         $("#searchForm #spID").val(spID);
                         $("#searchForm #isPublish").val("01");
                         $("#searchForm").attr("target", "hidden").attr("action", basePath + "ea/face/ea_onOffLine.jspa");
                         document.searchForm.submit.click();
                         token = 2;
                     }
                 }else{
                     alert("无法操作已是上线状态");
                 }


                break;
            case '下线':
                if (spID == "") {
                    alert("请选择！");
                    return;
                }

                var isPublish = $("tr#" + spID).find("#isPublish").text();
                if(isPublish=="01"){
                    if(confirm("确定下线？")) {
                        $("#searchForm #spID").val(spID);
                        $("#searchForm #isPublish").val("00");
                        $("#searchForm").attr("target", "hidden").attr("action", basePath + "ea/face/ea_onOffLine.jspa");
                        document.searchForm.submit.click();
                        token = 2;
                    }
                }else{
                    alert("无法操作已是下线状态");
                }

                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/face/ea_getPosterList.jspa?search="
						+ search+"&deviceType="+deviceType;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        spID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

    $(".flexme11 tr[id]").dblclick(function() {
        spID = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");
        $("tr#" + spID).find("#sorts").hide();
        $("tr#" + spID).find("#inputsort").show().focus();


    });

    //失去焦点保存序号
    $(".inputsort").blur(function(){
        var zz = /^[0-9]*[1-9][0-9]*$/;
        var newsort = $(this).val();
        if(newsort==""){
            return false;
        }
        if (!zz.test(newsort)) {
            alert("请输入正整数");
            $(".inputsort").focus();
            $(".inputsort").val("");
            return false;
        }

        var $sorts =  $(this).parent().find("#sorts");
         $("tr#" + spID).find("#sorts").show();
         $("tr#" + spID).find("#inputsort").hide();
        var url = basePath + "ea/face/sajax_ea_posterSorts.jspa?date="
            + new Date();
        $.ajax({
            url : url,
            type : "get",
            async : false,
            dataType : "json",
            data : {
                "setPoster.spID" : spID,
                "setPoster.sorts":newsort
            },
            success : function(data) {
                 window.location.reload();

            },
            error : function(data) {
                console.log("读取数据失败");
            }

        });


    });

	//保存
	$("#save").click(function() {
        $(".put3").trigger("blur");
        if($("#addForm .error").length>0){
        	 return;
		}
		$("#addForm").attr("target","hidden").attr(
				"action",
				basePath + "ea/face/ea_addPoster.jspa");
        token = 2;
		document.addForm.submit.click();
	});




	//查询
    $("#toSearch").click(function() {
        $("#searchForm").find("#posterName").val($("#ch").val());


        $("#searchForm").attr(
            "action",
            basePath + "ea/face/ea_toSearchPoster.jspa");
        token = 2;
        document.searchForm.submit.click();
    });
});

function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/face/ea_getPosterList.jspa?pageNumber=" + pNumber+"&deviceType="+deviceType;
    }
}

