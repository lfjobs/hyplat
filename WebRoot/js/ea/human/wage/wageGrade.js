$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$('#wageGrade').flexigrid({
				title:"工资等级管理",
				width : 'auto',
				height:'auto',
				minwidth : 30,
				minheight : 80,
				buttons : [{
					name : '创建工资等级',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '创建工资等级' :
				document.forms["addform"].reset();
				$.ajax({
					url:basePath+"ea/wgrade/sajax_ea_findItem.jspa",
					type:"get",
					success:function(data){
						var member = eval("("+data+")");
						var lst = member.item;
						if(lst.length>0){
							var htr="<tr><td style=\"text-align: left;\">工资构成</td><td>数额</td><td>递进规则</td></tr>";
							for ( var i = 0; i < lst.length; i++) {
								htr+="<tr><td style=\"text-align: left;\" id=\"sname\">"+lst[i].wageItemName;
								htr+="<input type='hidden' name='ritem["+i+"].wageItemId' value='"+lst[i].wageItemId+"'/>";
								htr+="</td><td><input class='jsm' type=\"text\" name='ritem["+i+"].wmoney' id='vm"+i+"'";
								htr+=" onkeyup=\"jsmoney(this,'items')\" onchange=\"jsmoney(this,'items')\" size=\"16\" maxlength='8'/>元</td>";
								htr+="<td><table width=\"100%\" style=\"text-align: center;\">";
								htr+="<tr><td><input id='gdm"+i+"' type=\"checkbox\" value='0' class='ckall' onclick=\"ckMoney('vm"+i+"','gdm"+i+"','m"+i+"')\" disabled/>固定数额<input disabled class='dis' type=\"text\" name='ritem["+i+"].recursiveMoney' id='m"+i+"' size=\"8\"/>元</td>";
								htr+="</tr><tr><td><input id='gdr"+i+"' type=\"checkbox\" value='0' class='ckall' onclick=\"ckMoney('vm"+i+"','gdr"+i+"','r"+i+"')\" disabled/>固定比率<input disabled class='dis' type=\"text\" name='ritem["+i+"].recursiveRate' id='r"+i+"' size=\"8\"/>%</td>";
								htr+="</tr></table></td></tr>";
							}
							htr+="<tr><td style=\"text-align: left;\">月工资<span id='monthM'>0</span>元</td><td>日工资<span id='dayM'>0</span>元</td><td>小时工资<span id='hourM'>0</span>元</td></tr>";
							htr+="<tr><td colspan='3' style=\"text-align: left;\">备注：<input maxlength='25' name='remark' size='50'/></td></tr>";
							$("table#items").html(htr);
						}
					}
				});
				$("#jqmadd").jqmShow();
				break;
			case '修改' :
				if (operatorId == '') {
					Showbo.Msg.alert("请选择！");
					return;
				}
				$.ajax({
					url:basePath+"ea/wgrade/sajax_ea_findEditGrade.jspa",
					data:"gid="+operatorId,
					type:"get",
					success:function(data){
						var member = eval("("+data+")");
						var wageGrade = member.wageGrade;
						var lst = member.items;
						if(lst.length>0){
							var htr="<tr><td style=\"text-align: left;\">工资构成</td><td></td></tr>";
							for ( var i = 0; i < lst.length; i++) {
								htr+="<tr><td style=\"text-align: right;\">"+lst[i][1]+":</td><td style=\"text-align: left;\">";
								htr+="<input type='hidden' name='ritem["+i+"].relationId' value='"+lst[i][0]+"'/>";
								htr+="<input maxlength='8' class='jsm' type=\"text\" onkeyup=\"jsmoney(this,'edtTable')\" onchange=\"jsmoney(this,'edtTable')\" ";
								htr+=" name='ritem["+i+"].wmoney' value='"+lst[i][2]+"' size=\"16\"/>元</td>";
								htr+="</tr>";
							}
							htr+="<tr><td colspan='2' style=\"text-align: left;\">月工资<span id='monthM'>";
							htr+=wageGrade.wageGradeMonth+"</span>元&nbsp;&nbsp;日工资<span id='dayM'>"+wageGrade.wageGradeDay;
							htr+="</span>元&nbsp;&nbsp;小时工资<span id='hourM'>"+wageGrade.wageGradeHour+"</span>元</td></tr>";
							htr+="<tr><td colspan='2' style=\"text-align: left;\">";
							htr+="备注：<input name='remark' maxlength='25' size='50' value='"+wageGrade.wgRemark+"'/></td></tr>";
							$("table#edtTable").html(htr);
						}
						$("#editform").find("#pre").val(wageGrade.wageGradeSn);
						
						$("#editform").find("#editId").val(operatorId);
					}
				});
				$("#jqmedit").jqmShow();
				break;
			case '删除' :
				if (operatorId == '') {
					Showbo.Msg.alert("请选择！");
					return;
				}
				Showbo.Msg.confirm("确定删除吗？", function(item){
					if(item=="yes")
						document.location.href=basePath+"ea/wgrade/ea_delGrade.jspa?gid="+operatorId;
				});
				break;
		}
	}
	$("#wageGrade tr").click(function() {
		operatorId=$(this).find("input#operatorId").val();
		$(this).find("input#operatorId").attr("checked",true);
	});
});
function closeM(){
	$("#jqmadd").jqmHide();
	$("#jqmedit").jqmHide();
	$("#jqmdetail").hide();
}
function saveM(){
	$("#addform .notnull").trigger("blur");
	var l = $("table#items").find("tr").length;
	if ($("#addform .error").length||l==1) {
		return;
	}
	var pre = $("#addform").find("pre");
	$.ajax({
		type:"get",
		data:$("#addform").serializeArray(),
		url:basePath+"ea/wgrade/sajax_ea_ckSn.jspa",
		success:function(data){
			if(data!=null&&data=="T"){
				$("#addform").attr("action",basePath+"ea/wgrade/ea_saveGrade.jspa");
				document.addform.submit.click();
			}else{
				Showbo.Msg.alert("编号重复,请修改编号,不能小于"+data+"!");
			}
		}
		
	});
}
function editM(){
	$("#editform").attr("action",basePath+"ea/wgrade/ea_editGrade.jspa");
	document.editform.submit.click();
}
function sdetail(id){
	
	$.ajax({
		url:basePath+"ea/wgrade/sajax_ea_findDetail.jspa",
		data:"gid="+id,
		type:"get",
		success:function(data){
			var member = eval("("+data+")");
			var wg = member.wg;
			var lst = member.wr;
			var htr = "<tr><td width='80' align='right'>等级编号:</td><td width='180'>"+wg.wageGradeSn+"</td></tr>";
			if(lst.length>0){
				for ( var i = 0; i < lst.length; i++) {
					htr+="<tr><td width='80' align='right'>"+lst[i][0]+":</td><td width='180'>"+lst[i][1]+"元</td></tr>";
				}
			}
			$("table#detailTable").html(htr);
			$("#jqmdetail").show();
		}
	});
}
function ckbulk(item){
	if($("#"+item).val()==0){
		$("#"+item).val(1);
		$("input#bulkNo").removeAttr("disabled");
		$("."+item).removeAttr("disabled");
	}else{
		$("#"+item).val(0);
		$("#bulkNo").attr("disabled","disabled");
		$("."+item).attr("checked","").attr("value","0").attr("disabled","disabled");
		$(".dis").attr("disabled","disabled");
	}
	
}
function ckMoney(vmId,item,mid){
	if($("#"+item).val()==0){
		if($("table#items").find("#"+vmId).val()==""){
			Showbo.Msg.alert("请先设定金额，再设定递进规则.");
			$("#"+item).attr("checked","");
		}else{
			$("#"+item).val(1);
			$("table#items").find("#"+mid).removeAttr("disabled");
		}
	}else{
		$("#"+item).val(0);
		$("table#items").find("#"+mid).attr("disabled","disabled");
	}
}
function jsmoney(ope,tableid){
	if (isNaN(ope.value)) {
		ope.value=ope.value.replace(/[^\-\d.]/g,'');
		return;
	} else {
		var monthM=0;
		$("#"+tableid).find(".jsm").each(function(){
			if($(this).val()!=""){
				monthM+=parseFloat($(this).val());
			}
		});
		$("#"+tableid).find("span#monthM").text(monthM);
		$("#"+tableid).find("span#dayM").text(Math.round(monthM/workDay*100)/100);
		$("#"+tableid).find("span#hourM").text(Math.round(monthM/workDay/workHour*100)/100);
	}
	
}