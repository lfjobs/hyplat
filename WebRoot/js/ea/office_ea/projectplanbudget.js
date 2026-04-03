$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 430,
				width : 'auto',
				minwidth : 30,
				title : '公司项目计划预算',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// �
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
	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (planbudgetID == "") {
					alert('请选择!');
					return;
				}
				var cresponsible = $("tr[title="+staffid+"]").find("span#responsiblePeople").html();
				/* $.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var billlist = member.billlist;
							
							alert(billlist[0][1]);
							$("#companyname").html(billlist[0][0]);$("#projectname").html(billlist[0][3]);$("#billtype").html(billlist[0][4]);
							$("#orgname").html(billlist[0][1]);$("#zerenren").html(billlist[0][2]);$("#procontext").html(billlist[0][5]);
							$("#starttime").html(billlist[0][6]);$("#endtime").html(billlist[0][7]);$("#projectType").html(billlist[0][8]);
							$("#procode").html(billlist[0][9]);$("#madebill").html(billlist[0][10]);$("#madebilldep").html(billlist[0][12])
							$("#madebillcom").html(billlist[0][11]);$("#madedate").html(billlist[0][13]);$("#shenhe").html(billlist[0][14]);
							$("#cresponsible").html(cresponsible);
							if(billlist[0][15]=="00"){
								$("#status").html("未审核");
								
							}if(billlist[0][15]=="01"){
								$("#status").html("审核中-招标前");
								
							}if(billlist[0][15]=="13"){
								$("#status").html("审核通过-招标前");
							}if(billlist[0][15]=="11"){
								
								$("#status").html("驳回待修改");
							}
						
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
						
					});*/
				 window.location.href= basePath + "ea/budget/ea_seedetail.jspa?csbid="+planbudgetID+"&staffid="+staffid ;
				//$("#jqModelbudgetSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/budget/ea_getprojectPlanbudget.jspa?";
				numback(url);
				break;
		}
	}
	
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				planbudgetID = this.id;
				staffid = this.title;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/budget/ea_getprojectPlanbudget.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
