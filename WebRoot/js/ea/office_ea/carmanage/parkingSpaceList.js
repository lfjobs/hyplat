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
				title : "停车位管理"+"&nbsp;&nbsp;&nbsp;&nbsp;停车位编号/名称：<input type='text' id='ch' style='width:100px'/> &nbsp;场地编号/名称：<input type='text' id='cd' style='width:100px'/> &nbsp;使用状态：<select id='sta' ><option value=''>全部</option><option value='00'>未使用</option><option value='01'>已占用</option><option value='02'>已弃用</option></select><input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					},{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					},{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '修改状态',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '批量初始化停车位',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
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
			getSiteList();
			parksId = "";
				break;
			case '修改' :
				if (parksId == "") {
					alert("请选择！");
					return;
				}
				var status = $("tr#"+parksId).find("#status").text();
				if(status=="01"){
					alert("该停车位正在被使用无法修改");
					return false;
				}
                document.addForm.reset();
                $t = $("table#addTable");
                $p = $("tr#" + parksId);
                $p.find("span[id]").each(function() {
                    $t.find("#" + this.id).val($(this).text());

                });
                $("#jqModeladd").jqmShow();
                getSiteList();
				break;
			case '删除' :
				if (parksId == "") {
					alert('请选择');
					return
				}
				var status = $("tr#"+parksId).find("#status").text();
				if(status=="01"){
					alert("该停车位正在被使用无法删除");
					return false;
				}
				if (confirm("确定删除？")) {
					
	                $("#addForm #parksId").val(parksId);
					$("#addForm").attr("target","hidden").attr("action",basePath+"ea/carmanage/ea_deleteSpace.jspa");
					  document.addForm.submit.click();
					token = 2;
				
				}
				break;
			case '修改状态' :
				if (parksId == "") {
					alert("请选择！");
					return;
				}
				var status = $("tr#"+parksId).find("#status").text();
//				if(status=="01"){
//					alert("该停车位正在被使用无法修改");
//					return false;
//				}
                document.addForm.reset();
                $("#updateForm").find("#parksId").val(parksId);
                $("#updateForm").find("#status").val(status);
                $("#jqModelupdate").jqmShow();
				break;
			case '批量初始化停车位' :
				$("#jqModelBat").jqmShow();
				document.addForm.reset();
				getSiteList();
					break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carmanage/ea_parkingSpaceList.jspa?search="+search;
				numback(url);
				break;
		}
	}
	/**
	 * 
	 * 查询
	 */
	$("#toSearch").click(function(){
		$("#searchForm").find("#parkingCode").val($("#ch").val());
		$("#searchForm").find("#siteId").val($("#cd").val());
		$("#searchForm").find("#status").val($("#sta").val());
		
		$("#searchForm").attr("action",basePath+"ea/carmanage/ea_toSearchSpace.jspa");
		document.searchForm.submit.click();
	});
	



	
	//提交保存
	$("#save").click(function(){
	   $("#addForm .put3").trigger("blur");
	   $("#addForm .parkingCode").trigger("blur");
	    if($("#addForm .error").length>0){
	        	 return;
		}
		$("#addForm").attr("target","hidden").attr("action",basePath+"ea/carmanage/ea_addUpdateSpace.jspa");
		document.addForm.submit.click();
		token = 2;

	});
	
	//提交修改状态
	$("#update").click(function(){
		$("#updateForm").attr("target","hidden").attr("action",basePath+"ea/carmanage/ea_updateStatus.jspa");
		document.updateForm.submit.click();
		token = 2;

	});

	
	//批量提交保存
	$("#batSave").click(function(){
	   $("#batForm .put3").trigger("blur");
	    if($("#batForm .error").length>0){
	        	 return;
		}
	    
	   var startNum = Number($("#startNum").val());
	   var endNum = Number($("#endNum").val());
	   if(startNum>=endNum){
		   alert("请填写正确的区间范围");
		   return;
	   }else if(endNum-startNum>1000){
		   alert("一次最多批量生成1000个停车位");
		   return;
	   }

	  
		$("#batForm").attr("target","hidden").attr("action",basePath+"ea/carmanage/ea_batAddSpace.jspa");
		document.batForm.submit.click();
		token = 2;

	});
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				parksId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	

    // 判断编号重复
    $("#addTable .parkingCode").live("blur", function() {
        $input = $(this);
        $parent = $input.parent();
        var inputValue = $input.attr("value");

        if ($input.is(".parkingCode")) {
            if (inputValue != "") {

                    if (!checkParkingNum(inputValue)) {
                        $parent
                            .append("<span class=\"error\"><a class=\"tex\">停车位编号已存在</a></span>");
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
            + "ea/carmanage/ea_parkingSpaceList.jspa?pageNumber=" + ppageNumber;
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
        },
        error : function(data) {
            console.log("读取数据失败");
        }

    });
	
}


//判断编号是否重复
function checkParkingNum(parkingCode) {

    var bool = null;
    var url = basePath + "ea/carmanage/sajax_ea_checkParkingNum.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data : {
            "parkingSpace.parkingCode" : $("#addTable #parkingCode").val(),
            "parkingSpace.parksId":parksId,
            "parkingSpace.siteId":$("#addTable #siteId").val()
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
