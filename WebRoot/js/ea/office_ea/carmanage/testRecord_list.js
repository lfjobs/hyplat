$(document).ready(function() {

    /*var wh = $(".whether").find("option");
    for (var i=0;i<wh.length;i++)
    {
        if($(wh[i]).attr("data-signInState")==signInState){
            $(wh[i]).attr("selected",true);
		}
    }*/


	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [{
					name : '查看',
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
                    name : '打印预览',
                    bclass : 'mysearch',
                    onpress : action
                    // 当点击调用方法
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
			case '查看' :
                if (bifId == "") {
                    alert('请选择');
                    return
                }
                var url = basePath
                    + "ea/carmanage/ea_viewExaminationRecords.jspa?bfmation.bifId="+bifId;
                window.open(url);
				break;
			case '删除' :
				if (bifId == "") {
					alert('请选择');
					return
				}
                delBookingInformation(bifId);
				break;
            case '打印预览' :
                if (bifId == "") {
                    alert('请选择');
                    return
                }
                var url = basePath
                    + "ea/carmanage/ea_printingTestRecord.jspa?bfmation.bifId="+bifId;
                window.open(url);
                break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carmanage/ea_testRecordList.jspa?tcc.account="+$("#account").val()+"&bfmation.signInState="+$(".signInState").val();
				numback(url);
				break;
		}
	}

	$(".whether").change(function(){
		var $this = $(this).find("option:selected");
		$(".signInState").val($this.attr("data-signInState"));
	})

	//like查询记录
	$(".input-button").click(function(){
		ppageNumber = 0;
		var url = basePath
            + "ea/carmanage/ea_testRecordList.jspa?pageNumber="+ppageNumber+"&tcc.account="+$("#account").val()+"&bfmation.signInState="+$(".signInState").val();
		document.location.href = url;
	})

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
        bifId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	//删除考场记录
	function delBookingInformation(obj){
		var url = basePath + "ea/carmanage/sajax_ea_delBookingInformation.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"bfmation.bifId":obj
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
				if(standard.boolean){
					window.location.reload();
				}else{
					alert("删除失败");
				}
			},
			error : function(data) {
				alert("删除失败");
			}
		});
	}
});
