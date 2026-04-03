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
				title : "项目咨询"+"&nbsp;&nbsp;&nbsp;&nbsp;客户姓名：<input type='text' id='ch' style='width:100px'/> &nbsp;客户电话：<input type='text' id='tel' style='width:100px'/> &nbsp;是否回访：<select id='sta' ><option value=''>全部</option><option value='01'>已回访</option><option value='00'>未回访</option></select><input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons : [{
							name : '查看',
							bclass : 'see',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '电话记录',
							bclass : 'edit',
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

			case '查看' :
				if (crId == "") {
					alert("请选择！");
					return;
				}
                document.viewForm.reset();
                $t = $("table#viewTable");
                $p = $("tr#" + crId);
                $p.find("span[id]").each(function() {
                $t.find("#" + this.id).val($(this).text());
                if(this.id=="returnVisit"){
                    $t.find("#" + this.id).text($(this).text()=="00"?"未回访":"已回访");
                }else if(this.id=="islisten"){
                    $t.find("#" + this.id).text($(this).text()=="00"?"未接听":"已接听");
                }else if(this.id=="clientType"){
                    $t.find("#" + this.id).text($(this).text()=="01"?"公司":"个人");
                }else if(this.id=="isIntentCustomer"){
                    $t.find("#" + this.id).text($(this).text()=="00"?"不感兴趣":($(this).text()=="01"?"意向客户":"成交客户"));
                }
            });
                $("#jqModelview").jqmShow();
                break;
			case '电话记录':
                if (crId == "") {
                    alert("请选择！");
                    return;
                }

                $("#addForm #crId").val(crId);
                $("#addForm .conname").text($("tr#"+crId).find("#consultantName").text());
                $("#jqModeladd").jqmShow();

                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "/ea/consult/ea_getConsultList.jspa?search="
						+ search;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        crId = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});


	//保存
	$("#save").click(function() {
        if($("#addForm .error").length>0){
        	 return;
		}
		$("#addForm").attr("target","hidden").attr(
				"action",
				basePath + "ea/consult/ea_saveReturnVisit.jspa");
        token = 2;
		document.addForm.submit.click();
	});




	//查询
    $("#toSearch").click(function() {
        $("#searchForm").find("#consultantName").val($("#ch").val());
        $("#searchForm").find("#consultantPhone").val($("#tel").val());
        $("#searchForm").find("#returnVisit").val($("#sta").val());
        $("#searchForm").attr(
            "action",
            basePath + "ea/consult/ea_toSearch.jspa");
        token = 2;
        document.searchForm.submit.click();
    });

    //改变接听
    $("#addForm #isIntentCustomer").change(function(){
        var  isIntentCustomer = $(this).val();
        if(isIntentCustomer=="00"){
            $(".jt").hide();
            $("#addForm #visitContent").removeClass("put3")
        }else{
            $(".jt").show();
            $("#addForm #visitContent").addClass("put3");

        }
    });
});



function re_load() {
	if(token) {
        document.location.href = basePath
            + "/ea/consult/ea_getConsultList.jspa?pageNumber=" + pNumber;
    }
}

