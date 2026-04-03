$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	var query = "<form name='cofjjSearchForm' id='cofjjSearchForm' method='post'>" +
		"<span style=\"margin-left:20px;\">设定时间：</span>" +
		"<input type='text' style=\"width: 90px\" id='CofDate' name='wcofjj.CofDate'  onfocus=\"WdatePicker({dateFmt:'yyyy'})\"/>" +
		"<span style=\"margin-left:10px;\">产品名称：</span>" +
		"<input type='text' style=\"width: 130px\" id='codeValue' name='wcofjj.goodsName' size=\"10\"/>" +
		"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='searchCofjj'/>" +
		"<input name='search' type='hidden' value='search' /><input type=\"submit\" name=\"submit\" style=\"display:none\"/>" +
		"</form>";
	$('#item').flexigrid({
		title: "级差工资设定管理"+query,
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [{
			name : '添加',
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
		}, {
			name : '停用',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}
//		, {
//			name : '导出',
//			bclass : 'mysearch',
//			onpress : action
//				// 当点击调用方法
//			}, {
//			separator : true
//		}
		, {
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
			document.forms["addform"].reset();
			$("#jqmadd").jqmShow();
			break;
		case '修改' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			} 
			if($("#"+cid).find("input#confRaState").val() == "1"){
				alert("已停用不可修改！");
				return;
			}
			document.forms["addform"].reset();
			$("#jqmadd").jqmShow();
			
			var url = basePath + "ea/cofra/sajax_ea_toEdit.jspa?wcofra.cofRaID="+cid
				+"&date="+ new Date().toLocaleString();
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var wcr = member.wcr;
					var wcl = member.wcl;
					$p = $("#addform");
					
					if(wcr != ""){
						$p.find("input#cofSortSn").attr("value",wcr.cofSortSn);
						$p.find("input#cofRaDate").attr("value",wcr.yyyyCofDate);
						$p.find("input#raNum").attr("value",wcr.raNum);
						$p.find("input#crName").attr("value",wcr.crName);
						$p.find("input#sumMoney").attr("value",wcr.sumMoney);
						$p.find("input#cofRaKey").attr("value",wcr.cofRaKey);
						$p.find("input#cofRaID").attr("value",wcr.cofRaID);
						$p.find("input#groupCompanySn").attr("value",wcr.groupCompanySn);
						$p.find("input#companyID").attr("value",wcr.companyID);
						$p.find("input#cjAname").attr("value",wcr.cjAname);
					}
					var num = 0;
					$p.find("input.com").each(function(){
						$(this).attr("id", select + this.id);
						if(num == 4){
							num = 0;
							select++;
						}else{
							num ++;
						}
					});
					select = 0;
					if(wcr != ""){
						for(var i = 0 ; i < wcl.length ; i++){
							$p.find("input#"+i+"typeMoney").attr("value",wcl[i].typeMoney);
						}
					}
					$p.find("input.com").each(function(){
						$(this).attr("id", this.id.substring(1));
					});
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
			break;
		case '删除' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#confRaState").val() == "1"){
				alert("已停用不可修改！");
				return;
			}
		
			notoken = 1;
			Showbo.Msg.confirm("确定删除吗？", function(item){
				if(item=="yes"){
					$('#cofraForm').attr("action",basePath + "ea/cofra/ea_delItem.jspa?wcofra.cofRaID="+ cid);
					$('#cofraForm').submit();
					$("tr#" + cid).remove();
					cid = "";
				}
	
			});
			break;
		case '停用' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#confRaState").val() == "1"){
				return;
			}
			notoken = 1;
			Showbo.Msg.confirm("确定停用吗？", function(item){
				if(item=="yes"){
					$('#cofraForm').attr("action",basePath + "ea/cofra/ea_upItem.jspa?wcofra.cofRaID="+ cid);
					$('#cofraForm').submit();
					cid = "";
				}
		
			});
			break;
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/cofra/ea_findItem.jspa?1=1";
			numback(url);
			break;
	}
}

	$("#item tr").click(function() {
		cid=$(this).find("input.cid").val();
		$(this).find("input.cid").attr("checked",true);
	});
	$("#item tr").dblclick(function() {
		cid=$(this).find("input.cid").val();
		action('修改');
	});
	
	//主项目目录关闭
	$("#closeml").click(function(){
		$("#jqModel").hide();
		
	});
	$("#searchCofjj").click(function(){
		alert($("#CofDate").val());
		$('#cofjjSearchForm').attr("action",basePath + "ea/cofra/ea_toSearch.jspa?yyyyCofDate="+$("#CofDate").val());
		document.cofjjSearchForm.submit.click();
	});
});

function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	
}
function saveM(){
	$("#addform .put3").trigger("blur");
	if ($("#addform .error").length) {
		return;
	}
	var num = 0 ;
	$("#addform").find(".com").each(function(){
		$(this).attr(
				"name",
				"wcofclmap[" + select + "]."
						+ this.name);
		
		if(num == 4){
			num = 0;
			select++;
		}else{
			num ++;
		}
	});
	$("#addform").attr("action",basePath+"ea/cofra/ea_saveItem.jspa?");
	$("#addform").submit();
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "cofra/ea_findItem.jspa?";
}

/****************************计算数据开始********************************/
function pnm(obj){
	var a = /^[0-9]+\.?[0-9]{0,2}$/; // 判断 加小数点的入力值。
	var b = /^\d+$/;// 不加小数点的入力值。
	$p = $("table#addTab");

if(a.test($(obj).attr("value")) || b.test($(obj).attr("value"))){
		var sum = 0;
		$p.find(".comt").each(function(){
			if($(this).val() != ""){
				sum = accAdd(sum,$(this).val(),$(this).parent().find(".jianfei").val());
				
		}
		});
		$p.find("#sumMoney").attr("value",sum);
	}else{
		a.test($(obj).attr("value",""));
		}
		};
		

function accAdd(arg1,arg2,arg3){ 
	var r1,r2,m; 
	
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
	m=Math.pow(10,Math.max(r1,r2)); 
    var a;
	if(arg3==0)
		{
				a=(arg1*m+arg2*m)/m;
		}
	else{
	  a=(arg1*m-arg2*m)/m ;
	}
	return a;
} 
/****************************计算数据结束********************************/