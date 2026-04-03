$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	var query = "<form name='cofiSearchForm' id='cofiSearchForm' method='post'>" +
		"<span style=\"margin-left:20px;\">设定时间：</span>" +
		"<input type='text' style=\"width: 90px\" id='CofDate' name='wcofi.cofDate'  onfocus=\"WdatePicker({dateFmt:'yyyy'})\"/>" +
		"<span style=\"margin-left:10px;\">项目名称：</span>" +
		"<input type='text' style=\"width: 130px\" id='codeValue' name='wcofi.codeValue' size=\"10\"/>" +
		"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='searchCofi'/>" +
		"<input name='search' type='hidden' value='search' /><input type=\"submit\" name=\"submit\" style=\"display:none\"/>" +
		"</form>";
	var tit = "";

	$('#item').flexigrid({
		title: tit+"设定管理"+query,
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
			name : '全部保存',
			bclass : 'add',
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
			$("#sa").after($("#sa").clone(true).attr("id","sa" + select).addClass("check"));
			$("#sa" + select).find(".cid").attr("value","sa" + select);
			$("#sa" + select).find('input:gt(0)').each(
					function() {
						$(this).attr(
								"name",
								"wcofimap[" + select + "]."
										+ this.name);
						if($(this).hasClass("yz")){
							$(this).addClass("notnull");
						}
					});
			$("#sa" + select).find('select').each(
					function() {
						$(this).attr(
								"name",
								"wcofimap[" + select + "]."
										+ this.name);
					});
			$("#sa" + select).show();
			$("#sa" + select).find("select").show();
			select++;
			break;
		case '修改' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#wageCofState").val() == "1"){
				alert("已停用不可修改！");
				return;
			}
			$p = $("tr#" + cid);
			if ($p.hasClass("check")) {
				return;
			}
			$p.addClass("check");
			$p.find('input:gt(0)').each(
					function() {
						$(this).attr(
								"name",
								"wcofimap[" + select + "]."
										+ this.name);
						if($(this).hasClass("yz")){
							$(this).addClass("notnull");
						}
					});
			$p.find('select').each(
					function() {
						$(this).attr(
								"name",
								"wcofimap[" + select + "]."
										+ this.name);
					});
			select++;
			$p.find("span").addClass("model1");
			$p.find("input").removeClass("model1");
			$p.find("select").show();
			break;
		case '删除' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if (notoken)
				return;
			notoken = 1;
			Showbo.Msg.confirm("确定删除吗？", function(item){
				if(item=="yes"){
					window.location.href=basePath + "ea/cofi/ea_delItem.jspa?id="+ cid;
					$("tr#" + cid).remove();
					cid = "";
					token = 11;
				}else{
					notoken = 0;
				}
			});
			break;
		case '停用' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#wageCofState").val() != "0"){
				alert("不可停用！");
				return;
			}
			if (notoken)
				return;
			notoken = 1;
			Showbo.Msg.confirm("确定停用吗？", function(item){
				if(item=="yes"){
					window.location.href=basePath + "ea/cofi/ea_upItem.jspa?id="+ cid;
				}else{
					notoken = 0;
				}
			});
			break;
		case '全部保存':
			$("#cofiForm input[name^='wcofimap'].notnull").trigger("blur");
			if($("#cofiForm").find(".error").length>0){
				return;
			}
			notoken = 1;
			$('#cofiForm').attr("action",basePath + "/ea/cofi/ea_saveItem.action");
			$('#cofiForm').submit();
			break;
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/cofi/ea_findItem.jspa?wageState=" + wstate;
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
	$("#searchCofi").click(function(){

		
		$('#cofiSearchForm').attr("action",basePath + "ea/cofi/ea_toSearch.jspa?wageState=" + 1+"&CofDate="+$("#CofDate").val()+"&codeValue="+$("#codeValue").val());
		document.cofiSearchForm.submit.click();
	});
	$("select").each(function(){
		$(this).attr("style", "display:none");
	});
	
	$("#wcXslb").live("change",function(){
		if($(this).val()==1){			
			$(this).next().removeAttr("readonly").addClass("notnull").blur();
		}else{
			$p=$("#"+cid).find("#price");
			$n=$("#"+cid).find("#nums");
			if($p.val().length>0&&$n.val().length>0){
				$("#"+cid).find("#moneys").val($p.val()*$n.val());
			}else{
				$("#"+cid).find("#moneys").val("");
			}
			$(this).next().attr("readonly","readonly").removeClass("notnull").val("").blur();
		}
		
	});
});

function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cofi/ea_findItem.jspa?wageState=" + wstate;
}
/** ***********************项目分类**************************** */
// 键入时查询项目分类
function getCate(obj) {
	var pY = pageY(obj);
	if(pY > GetPageSize()[1]/2){
		pY = pageY(obj) - 350 ;
	}else{
		pY = pageY(obj) + 30;
	}
	var url = basePath + "ea/cofi/sajax_ea_getXmtypeByCode.jspa?xmtype="+xmtype+"&date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					parameter : $.trim($(obj).val())
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var xmList = member.xmlist;

					var str = "";

					for ( var i = 0; i < xmList.length; i++) {
						str += "&nbsp;&nbsp;<span><a href='#' style='color:royalblue;'"
							+ "title='" + xmList[i][2] +"' onclick='selectz(this);'>"
							+ xmList[i][0]
							+ "/"
							+ xmList[i][1] + "</a></span><br/>";
					}
					if (str == "") {
						str = "&nbsp;无搜索结果";
					}

					$("#treeBoxs").html(str);

					$("#jqModel").css({
						"width" : "230px",
						"height" : "350px",
						"overflow" : "auto",
						"background-color" : "#e1ecfc",
						"border" : "1px solid #a8c7ce",
						"position" : "absolute",
						"left" : pageX(obj),
						"top" : pY
					}).show();

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}
// 选中一个项目分类
function selectz(obj) {
	$("#jqModel").hide();
	var codeValues = $(obj).text();
	var xmtypename = codeValues.substring(0,codeValues.indexOf("/"));
	var xmtype = codeValues.substring(codeValues.indexOf("/") + 1);
	var xmtypeid = $(obj).attr("title");
	$("tr#"+cid).find("#codeValue").attr("value",xmtypename);
	$("tr#"+cid).find("#codeID").attr("value",xmtypeid);

}
//获取当前的x坐标值

function pageX(elem){
      return elem.offsetParent?(elem.offsetLeft+pageX(elem.offsetParent)):elem.offsetLeft;
      }

//获取当前的Y坐标值
  function pageY(elem){
      return elem.offsetParent?(elem.offsetTop+pageY(elem.offsetParent)):elem.offsetTop;
      }
/** ***********************项目分类结束**************************** */
/****************************计算数据开始********************************/
function pnm(obj){
	
	var a = /^[0-9]+\.?[0-9]{0,2}$/; // 判断 加小数点的入力值。
	var b = /^\d+$/;// 不加小数点的入力值。
	var flag=false;
	if(a.test($(obj).attr("value")) || b.test($(obj).attr("value"))){
		var oid = $(obj).attr("id");
		var pvalue = $("tr#" + cid).find("input#price").val();
		var nvalue = $("tr#" + cid).find("input#nums").val();
		var mvalue = $("tr#" + cid).find("input#moneys").val();
		var l=$("tr#" + cid).find("select#wcXslb option:selected").val();
		var temp1=$("tr#" + cid).find("input#wcTcxs").val();
		if(l=="1"){//百分比
			if(pvalue.length>0&&nvalue.length>0&&temp1.length>0){				
				pvalue=(pvalue*temp1/100).toFixed(2);
				flag=true;
			}
		}else{
			if(pvalue.length>0&&nvalue.length>0){				
				flag=true;
			}
		}
		if((oid == "price"||oid == "nums"||oid == "wcTcxs") && flag){
			$("tr#" + cid).find("input#moneys").attr("value",(pvalue * nvalue).toFixed(2));
		}else if(oid == "moneys" && flag){
			if(nvalue == "" || nvalue == 0){
				$("tr#" + cid).find("input#nums").attr("value","1");
				$("tr#" + cid).find("input#price").attr("value",mvalue);
			}else{
				$("tr#" + cid).find("input#price").attr("value",(mvalue / (nvalue*temp1/100)).toFixed(2));
			}
		}
		return;
	}else{
		$("tr#" + cid).find("input#price").attr("value","");
		$("tr#" + cid).find("input#nums").attr("value","");
		$("tr#" + cid).find("input#moneys").attr("value","");
		$("tr#" + cid).find("input#wcTcxs").attr("value","");
	} 
}
/****************************计算数据结束********************************/