$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	if(type=="org"){
		$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '部门行车日志',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	}else if(type=="my"){
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '个人行车日志',
				minheight : 80,
				buttons : [{
					name : '行车记录',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	}
	function action(com, grid) {
		switch (com) {
			case '行车记录' :
			    if (carid == "") {
					alert('请选择!');
					return;
				}
			    $("input.JQuerypersonvalue").attr("checked", false);
				$t = $("table#carapplytable");
				$t1 = $("table#carusetable");
				$p = $("tr#" + carid);
				
			    var carcode = $("tr#"+carid).find("span#carcode").text();
			    var cname = $("tr#"+carid).find("span#carusername").text();
				var carusereason = $("tr#"+carid).find("span#carusereason").text();
				var destination = $("tr#"+carid).find("span#destination").text();
				var remarks = $("tr#"+carid).find("span#remarks").text();
				var cardriver = $("tr#"+carid).find("span#cardriver").text();
				$t.find("span#carcode").text(carcode);
				$t.find("span#carusername").text(cname);
				$t.find("span#carusereason").text(carusereason);
				$t.find("span#destination").text(destination);
				$t.find("span#remarks").text(remarks);
				$t1.find("span#cardriver").text(cardriver);
				
				var lid = $("tr#"+carid).find("span#lid").text();
				if(lid==""){
					var endkm= getcarkms(carcode);//起点里程数
				    if(endkm=="no")
				       {
				    	$t1.find("input#starnum").val("1");
				    	}
				    else{
				        $t1.find("input#starnum").val(endkm);
				    }
				    $p.find("span[id]").each(function() {
							$t1.find(":input[name]#" + this.id).val($(this).text());
						});
					
				}else{
				   var starnums = $("tr#"+carid).find("span#starnums").text();
				   $t1.find("input#starnum").val(starnums);
				   $p.find("span[id]").each(function() {
							$t1.find(":input[name]#" + this.id).val($(this).text());
						});
				}
				carid =""
				if(type=="org"){
					$f = $("form#carapplyForm");
                	$t1 = $("table#carusetable");
                	$f.find("div#button").css("display","none");
                	$t1.find("input#endnum").attr("readonly",true);
                	$t1.find("input#endnum").attr("class","");
                	$t1.find("input#roadtoll").attr("readonly",true);
                	$t1.find("input#roadtoll").attr("class","");
                	$t1.find("input#parkingfees").attr("readonly",true);
                	$t1.find("input#parkingfees").attr("class","");
                	$t1.find("textarea#driverremarks").attr("readonly",true);
                	$t1.find("textarea#driverremarks").attr("class","");
                }
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#postSearchForm").find("input#carcode").focus();
				break;
			case '导出' :
			    var url = basePath+ "ea/carapply/ea_showExcelcarlog.jspa?type="+type+"&search="+search;
			    open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carapply/ea_getDtMyusecarapplyListysh.jspa?type="+type+"&1=1";
				numback(url);
				break;
		}
	}
	$(".close").click(function() {// 取消查询搜索
		        $("table#cataffSearchTable").find("input#carcode").val("");
		        $("table#cataffSearchTable").find("input#carusername").val("");
				$("#jqModelSearch").jqmHide();
			});
	//添加，点击取消
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	//条件查询确定
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/carapply/ea_toSearchysh.jspa?type="+type+"&1=1");
		document.postSearchForm.submit.click();
	});

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('行车记录');// 当双击时出发 action方法.等价于先选中再点击查看按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				carid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
});
//添加、修改单据
function  tosave() {
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		//判断添加数据
        $("#carapplyForm")
			.attr("target", "hidden")
			.attr(
					"action",
					basePath
							+ "ea/carapply/ea_saveCarlog.jspa?1=1"
							);
	    document.carapplyForm.submit.click();
	    document.carapplyForm.reset();
	    token = 2;
	    return;
	}
//出车公里数计算
function Reduction(){
  $t1 = $("table#carusetable");
  var star=$t1.find("input#starnum").val();
  var end=$t1.find("input#endnum").val();
  //正整数验证
  var r = /^\+?[1-9][0-9]*$/;
  if(!r.test(end)){
   	$t1.find("input#endnum").val("");
  }
  var a=end-star;
  $t1.find("input#countnum").val(a);
}
//ajax获取选中行车单对应的车俩使用前的公里数。
	function getcarkms(carcode){
		    var url = basePath + "ea/carapply/sajax_ea_getcarkm.jspa?date="
				+ new Date();
			var str = ""; 	
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"carcode" : carcode
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var tasks = member.list;
					if(tasks=="no")
					{str="no"}
					else{
					var task = tasks[0];
					str=task.endnum;
					}
					
				},
		        error:function(data){
			      alert("数据加载失败");
		        }
			});
			return str;
	}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/carapply/ea_getDtMyusecarapplyListysh.jspa?type="+type+"&1=1";
}
