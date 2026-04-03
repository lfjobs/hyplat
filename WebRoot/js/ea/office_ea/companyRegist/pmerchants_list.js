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
				title : (applyment_state==""?"":(applyment_state=="FINISH"?"已审核":"待审核"))+(organization_type=="2"?"企业商户":"个人商户")+"&nbsp;&nbsp;&nbsp;&nbsp;商户名称：<input type='text' id='ch' style='width:100px'/> &nbsp;<input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons :   applyment_state!=""?(applyment_state=="FINISH"?[

                    {
                        name : '查看详情',
                        bclass : 'see',
                        onpress : action
                        // 当点击调用方法
                    },{
                        separator : true
                    },

                    {
                        name : '设置每页显示条数',
                        bclass : 'mysearch',
                        onpress : action
                        // 当点击调用方法
                    },{
                        separator : true
                    }]:[

                    {
                        name : '查看审核',
                        bclass : 'see',
                        onpress : action
                        // 当点击调用方法
                    }	, {
                        separator : true
                    }, {
                        name : '初审驳回',
                        bclass : 'delete',
                        onpress : action
                        // 当点击调用方法
                    }	,  {
                        separator : true
                    },{
                        name : '审核通过',
                        bclass : 'examine',
                        onpress : action
                        // 当点击调用方法
                    }	,  {
                        separator : true
                    },

                    {
                        name : '设置每页显示条数',
                        bclass : 'mysearch',
                        onpress : action
                        // 当点击调用方法
                    } , {
                        separator : true
                    }]):[{
                    name : '查看详情',
                    bclass : 'see',
                    onpress : action
                    // 当点击调用方法
                }	, {
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
			case '查看审核':
                if (out_request_no == "") {
                	alert("请选择！");
                	return;
                }

                window.open(basePath+"/ea/merch/ea_getMaterialPage.jspa?applyParam.out_request_no="+out_request_no);
                break;
            case '查看详情':
                if (out_request_no == "") {
                    alert("请选择！");
                    return;
                }

                window.open(basePath+"/ea/merch/ea_getMaterialPage.jspa?applyParam.out_request_no="+out_request_no);
                break;

			case '初审驳回':
                if (out_request_no == "") {
                    alert("请选择！");
                    return;
                }

                var state =$("tr#"+out_request_no).find("#state").text()

                if (state == "01") {
                    $("#jqModeladd").jqmShow();


                    $(".ccompanyID").val($("tr#"+out_request_no).find("#ccompanyid").text());
                    $(".auid").val($("tr#"+out_request_no).find("#auid").text());
                    $(".state").val("03");
                }else{
                     alert("无法操作");

                }

                break;

            case '审核通过':
                if (out_request_no == "") {
                    alert("请选择！");
                    return;
                }

                var state =$("tr#"+out_request_no).find("#state").text()

                if (state == "01") {
                    if(confirm("确认通过")) {
                        $(".ccompanyID").val($("tr#" + out_request_no).find("#ccompanyid").text());
                        $(".auid").val($("tr#" + out_request_no).find("#auid").text());
                        $(".auid").val($("tr#" + out_request_no).find("#auid").text());
                        $(".state").val("02");
                        submit();
                    }
                }else{
                    alert("无法操作");

                }

                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/merch/ea_getMerchanetsRegistList.jspa?applyParam.organization_type="+organization_type+"&applyResult.applyment_state="+applyment_state+"&search="
						+ search;
				numback(url);
				break;
		}
    }
     //同步到微信

    $(".syn").click(function(){

        var url = basePath + "/ea/merch/sajax_ea_synwx.jspa";

       var out_request_no = $(this).parents("tr").attr("id");

        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            data:{
                "applyParam.out_request_no":out_request_no
            },
            success: function cbf(data){
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if(nologin){
                    document.location.href =basePath+"page/ea/not_login.jsp";
                }
                //  var applyResult = member.applyResult;
                // var account_validation = member.account_validation;
                // var audit_detail = member.audit_detail;
                window.location.reload();

            },
            error: function cbf(data){
                alert("数据获取失败！");
            }
        });

    });


    //查询结果

    $(".synsearch").click(function(){

        var url = basePath + "/ea/merch/sajax_ea_searchSyncResult.jspa";

        var out_request_no = $(this).parents("tr").attr("id");

        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            data:{
                "applyParam.out_request_no":out_request_no
            },
            success: function cbf(data){
                var member = eval("(" + data + ")");
                var applyResult = member.applyResult;
                var account_validation = member.account_validation;
                var audit_detail = member.audit_detail;
                window.location.reload();

            },
            error: function cbf(data){
                alert("数据获取失败！");
            }
        });

    });



    //同步君子签

    $(".synJzq").click(function(){

        var url = basePath + "/ea/contract/sajax_ea_synJzqCompany.jspa";

        var out_request_no = $(this).parents("tr").attr("id");

        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            data:{
                out_request_no:out_request_no
            },
            success: function cbf(data){


                window.location.reload();



            },
            error: function cbf(data){
                alert("数据获取失败！");
            }
        });

    });


    //同步君子签查询结果

    $(".synJzqsearch").click(function(){

        var url = basePath + "/ea/contract/sajax_ea_searchJzqCompany.jspa";

        var out_request_no = $(this).parents("tr").attr("id");

        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            data:{
                out_request_no:out_request_no
            },
            success: function cbf(data){

                window.location.reload();

            },
            error: function cbf(data){
                alert("数据获取失败！");
            }
        });

    });




    $(".flexme11 tr[id]").click(function() {
        out_request_no = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

    $(".flexme11 tr[id]").dblclick(function() {
        out_request_no = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

        action("修改");
    });

	//提交驳回
	$("#save").click(function() {


        submit();

    });






	//查询
    $("#toSearch").click(function() {
        $("#searchForm").find(".merchant_shortname").val($("#ch").val());

        $("#searchForm").attr(
            "action",
            basePath + "ea/merch/ea_toSearch.jspa");
        token = 2;
        document.searchForm.submit.click();
    });






});

function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/merch/ea_getMerchanetsRegistList.jspa?pageNumber=" + pNumber+"&applyParam.organization_type="+organization_type+"&applyResult.applyment_state="+applyment_state;
    }
}

function submit(){




    $.ajax({
        url: basePath + "ea/qrshare/sajax_ea_submitAudit.jspa",
        type: "POST",
        data: $('#addForm').serialize(),
        dataType: "json",
        async: false,
        success:function (data) {
            var judge = eval("(" + data + ")");
            window.location.reload();

        },error:function (data) {
        }


    });
}



