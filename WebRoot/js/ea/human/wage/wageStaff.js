$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$('#item').flexigrid({
		title:"工资关联管理",
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [{
			name : '修改',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}]
	});
function action(com, grid) {
switch (com) {
	case '修改' :
		if (cid == '') {
			Showbo.Msg.alert("请选择！");
			return;
		}
		$("#addform").find("input#showstaffid").val($("tr#"+cid).find("#datastaffid").val());
		$("#addform").find("#showstaffname").text($("tr#"+cid).find("#datastaffname").text());
		$("#addform").find("#editId").val(cid);
		$("#addform").find("tr.trev").remove();
		$("#jqmadd").jqmShow();
		break;
}
}
$("select#changeGrade").change(function(){
	var gid = $(this).val();
	$.ajax({
		url:basePath+"ea/wagestaff/sajax_ea_changeGrade.jspa",
		data:"gid="+gid +"&d="+new Date(),
		type:"get",
		success:function(data){
			var member = eval("("+data+")");
			var lst = member.wr;
			var stm = new Array();
			var mm=0;
			var select = 0;
			$("#jqmadd").find("tr.trev").remove();
			if(lst.length%2==0){
				for(var i =0;i<=lst.length/2;i+=2){
					mm+=parseFloat(lst[i][1]);
					mm+=parseFloat(lst[i+1][1]);
					stm.push("<tr class='trev'>");
					stm.push("<td style='text-align:right'>"+lst[i][0]+":</td><td style='text-align:left'>");
					stm.push("<input type='hidden' name='wscmap["+select+"].wageItemId' value='"+lst[i][2]+"'/>");
					stm.push("<input type='hidden' name='wscmap["+select+"].wageItemName' value='"+lst[i][0]+"'/>");
					stm.push("<input type='text' class='jsm' name='wscmap["+select+"].scMoney' maxlength='10' onkeyup=\"jsmoney(this,'edtTable')\" onchange=\"jsmoney(this,'edtTable')\" value='"+lst[i][1]+"'/></td>");
					select++;
					stm.push("<td style='text-align:right'>"+lst[i+1][0]+":</td><td style='text-align:left'>");
					stm.push("<input type='hidden' name='wscmap["+select+"].wageItemId' value='"+lst[i+1][2]+"'/>");
					stm.push("<input type='hidden' name='wscmap["+select+"].wageItemName' value='"+lst[i+1][0]+"'/>");
					stm.push("<input type='text' class='jsm' name='wscmap["+select+"].scMoney' maxlength='10' onkeyup=\"jsmoney(this,'edtTable')\" onchange=\"jsmoney(this,'edtTable')\" value='"+lst[i+1][1]+"'/></td>");
					select++;
					stm.push("</tr>");
				}
			}else{
				for(var i =0;i<lst.length/2+1;i+=2){
					if(i>=lst.length/2){
						mm+=parseFloat(lst[i][1]);
						stm.push("<tr class='trev'>");
						stm.push("<td style='text-align:right'>"+lst[i][0]+":</td><td style='text-align:left'>");
						stm.push("<input type='hidden' name='wscmap["+select+"].wageItemId' value='"+lst[i][2]+"'/>");
						stm.push("<input type='hidden' name='wscmap["+select+"].wageItemName' value='"+lst[i][0]+"'/>");
						stm.push("<input type='text' class='jsm' name='wscmap["+select+"].scMoney' maxlength='10' onkeyup=\"jsmoney(this,'edtTable')\" onchange=\"jsmoney(this,'edtTable')\" value='"+lst[i][1]+"'/></td>");
						select++;
						stm.push("<td style='text-align:right'></td><td style='text-align:left'></td>");
						stm.push("</tr>");
					}else{
						mm+=parseFloat(lst[i][1]);
						mm+=parseFloat(lst[i+1][1]);
						stm.push("<tr class='trev'>");
						stm.push("<td style='text-align:right'>"+lst[i][0]+":</td><td style='text-align:left'>");
						stm.push("<input type='hidden' name='wscmap["+select+"].wageItemId' value='"+lst[i][2]+"'/>");
						stm.push("<input type='hidden' name='wscmap["+select+"].wageItemName' value='"+lst[i][0]+"'/>");
						stm.push("<input type='text' class='jsm' name='wscmap["+select+"].scMoney' maxlength='10' onkeyup=\"jsmoney(this,'edtTable')\" onchange=\"jsmoney(this,'edtTable')\" value='"+lst[i][1]+"'/></td>");
						select++;
						stm.push("<td style='text-align:right'>"+lst[i+1][0]+":</td><td style='text-align:left'>");
						stm.push("<input type='hidden' name='wscmap["+select+"].wageItemId' value='"+lst[i+1][2]+"'/>");
						stm.push("<input type='hidden' name='wscmap["+select+"].wageItemName' value='"+lst[i+1][0]+"'/>");
						stm.push("<input type='text' class='jsm'  name='wscmap["+select+"].scMoney' maxlength='10' onkeyup=\"jsmoney(this,'edtTable')\" onchange=\"jsmoney(this,'edtTable')\" value='"+lst[i+1][1]+"'/></td>");
						select++;
						stm.push("</tr>");
					}
					
				}
			}
			if(stm.length>0){
//				stm.push("<tr class='trev' style='text-align:left'><td colspan='4'>&nbsp;&nbsp;月工资:<span id='mm'>"+mm+"</span>元&nbsp;&nbsp;日工资:"+Math.round(mm/workDay*100)/100+"元&nbsp;&nbsp;小时工资:"+Math.round(mm/workDay/workHour*100)/100+"元</td></tr>");
//				stm.push("<tr class='trev' style='text-align:left'><td colspan='4'>&nbsp;&nbsp;调整金额:<input type='text' name='xyMoney' size='10' maxlength='10' onkeyup=\"jsmoney(this,'edtTable')\" onchange=\"jsmoney(this,'edtTable')\"/>(允许负值)&nbsp;&nbsp;&nbsp;调整后如下:</td></tr>");
				stm.push("<tr class='trev' style='text-align:left'><td colspan='4'>&nbsp;&nbsp;月工资:<span id='monthm'>"+mm+"</span>元&nbsp;&nbsp;日工资:<span id='daym'>"+Math.round(mm/workDay*100)/100+"</span>元&nbsp;&nbsp;小时工资:<span id='hourm'>"+Math.round(mm/workDay/workHour*100)/100+"</span>元</td></tr>");
			}
			$("#jqmadd").find("table").append(stm.join(""));
			
		}
	});
});
$("#item tr").click(function() {
	cid=$(this).find("input#cid").val();
	$(this).find("input#cid").attr("checked",true);
});
$("#item tr").dblclick(function() {
	cid=$(this).find("input#cid").val();
//	action('修改');
});
});

function saveM(){
	var mm= $("#addform").find("span#monthm").text();
	var dm= $("#addform").find("span#daym").text();
	var hm= $("#addform").find("span#hourm").text();
	$("#addform").attr("action",basePath+"ea/wagestaff/ea_saveItem.jspa?mm="+mm+"&dm="+dm+"&hm="+hm);
	$("#addform").submit();
}
function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	$("#jqmdetail").jqmHide();
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
		$("#"+tableid).find("span#monthm").text(monthM);
		$("#"+tableid).find("span#daym").text(Math.round(monthM/workDay*100)/100);
		$("#"+tableid).find("span#hourm").text(Math.round(monthM/workDay/workHour*100)/100);
	}
	
	
	
	
}
//个人工资构成详细查询
function gzgc(id,st,tid){
	if(st == "1"){
		id = tid;	
	}
	$.ajax({
		url:basePath+"ea/wagestaff/sajax_ea_findDetail.jspa",
		data:"gid="+id + "&st="+st + "&d="+ new Date(),
		type:"get",
		success:function(data){
			var member = eval("("+data+")");
			var lst = member.wr;
			var htr = "<tr><td width='100' align='right'>姓名:</td><td width='160' align='center'>"+$("#"+cid).find("#datastaffname").text()+"</td></tr>";
			htr += "<tr><td width='100' align='right'>工资等级:</td><td width='160' align='center'>"+$("#"+cid).find("#gradesn").text()+"</td></tr>";
			if(lst.length>0){
				for ( var i = 0; i < lst.length; i++) {
					htr+="<tr><td width='100' align='right'>"+lst[i][0]+":</td><td width='160' align='center'>"+lst[i][1]+"元</td></tr>";
				}
			}
			htr += "<tr><td width='100' align='right'>月工资:</td><td width='160' align='center'>"+$("#"+cid).find("#mmoney").text()+"元</td></tr>";
			htr += "<tr><td width='100' align='right'>日工资:</td><td width='160' align='center'>"+$("#"+cid).find("#dmoney").text()+"元</td></tr>";
			htr += "<tr><td width='100' align='right'>小时工资:</td><td width='160' align='center'>"+$("#"+cid).find("#hmoney").text()+"元</td></tr>";
			$("table#detailTable").html(htr);
			$("#jqmdetail").jqmShow();
		}
	});
}