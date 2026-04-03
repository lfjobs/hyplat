$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "收费补录"+"&nbsp;&nbsp;&nbsp;&nbsp;车牌号：<input type='text' id='ch' style='width:100px'/> &nbsp;场地编号/名称：<input type='text' id='cd' style='width:100px'/> <input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					},{
                    separator : true
                },{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					},
					{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					},
					{
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
			$("#jqModeladd").jqmShow();
			document.addForm.reset();
			tcId = "";
                getSiteList();

				$(".JQuerypersonvalue").attr("checked",false);
				break;
			case '修改' :
				if (tcId == "") {
					alert("请选择！");
					return;
				}
                document.addForm.reset();
                $t = $("table#addTable");
                $p = $("tr#" + tcId);
                $p.find("span[id]").each(function() {
                    $t.find("#" + this.id).val($(this).text());

                });
                $("#jqModeladd").jqmShow();
                getSiteList();
				break;
			case '删除' :
				if (tcId == "") {
					alert('请选择');
					return
				}

				if (confirm("确定删除？")) {
					
	                $("#addForm #tcId").val(tcId);
					$("#addForm").attr("target","hidden").attr("action",basePath+"ea/carmanage/ea_deleteVIP.jspa");
					  document.addForm.submit.click();
					token = 2;
				
				}
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carmanage/ea_getVipFeeList.jspa?search="+search;
				numback(url);
				break;
		}
	}
	/**
	 * 
	 * 查询
	 */
	$("#toSearch").click(function(){
		$("#searchForm").find("#carNumber").val($("#ch").val());
		$("#searchForm").find("#siteId").val($("#cd").val());
		
		$("#searchForm").attr("action",basePath+"ea/carmanage/ea_toSearchFeeVIP.jspa");
		document.searchForm.submit.click();
	});

	//提交保存
	$("#save").click(function(){
	   $("#addForm .put3").trigger("blur");
	    if($("#addForm .error").length>0){
	        	 return;
		}
		$("#addForm").attr("target","hidden").attr("action",basePath+"ea/carmanage/ea_addUpdateVIP.jspa");
		document.addForm.submit.click();
		token = 2;

	});
	


	

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				tcId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});

    // 判断编号重复
    $("#addTable .carNumber").live("blur", function() {
        $input = $(this);
        $parent = $input.parent();
        var inputValue = $input.attr("value");

        if ($input.is(".carNumber")) {
            if (inputValue != "") {

                if (!checkcarNum(inputValue)) {
                    $parent
                        .append("<span class=\"error\"><a class=\"tex\">该车牌已录入</a></span>");
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


});


function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/carmanage/ea_getVipFeeList.jspa?pageNumber=" + ppageNumber;
    }
}
//获取地址
function getSiteList(){
	$(".site").html("");
    var url = basePath + "ea/carmanage/sajax_ea_getSiteList.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var list = member.sitelist;
        	var a = [];
			$(list).each(function(i, dom) {
				a.push("<option value='"+this[0]+"'>"+this[1]+"</option>");
			
			});
			$(".site").append(a.join(""));

            if(tcId!=""){
             var siteId = $("tr#"+tcId).find("#siteId").text();
             if(siteId!=""){
                 $(".site").val(siteId);
			 }

			}
        },
        error : function(data) {
            console.log("读取数据失败");
        }

    });
	
}


//判断编号是否重复
function checkcarNum(carNum) {

    var bool = null;
    var url = basePath + "ea/carmanage/sajax_ea_checkcarNum.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data : {
            "timingCharging.carNumber" : carNum,
            "timingCharging.tcId":tcId,
            "timingCharging.siteId":$("#addTable #siteId").val()
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

