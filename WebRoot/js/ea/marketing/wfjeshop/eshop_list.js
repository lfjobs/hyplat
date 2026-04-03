$(document).ready(function() {	
	window.parent.document.getElementById("mainframe6").style.height = 256 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.address').flexigrid({
				height : 110,
				width : 'auto',
				minwidth : 30,
				title : '机构负责人----当前机构：' + ogName,
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				/*$("#body_02",$("#goodsForm",$(window.parent.window.document ))).html("");
				$("#goodsjqModel",$("#goodsForm",$(window.parent.window.document ))).jqmShow();*/
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"eshopsMap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				var heis = parent.document.getElementById("mainframe6").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe6").style.height = heis;
				
				break;

			case '修改' :
				if (eshopkey == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + eshopkey);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"eshopsMap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("#chioce").removeClass("model1");
				$p.find("s:select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
				$p.find("div.btncon").removeClass("model1");
				$p.find("div.btncon").show();
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#regdate", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								alert("请输入日期");
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					notoken = 0;
					return;
				}
				$('#addressForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/marketingWfj/ea_saveWfjEshop.jspa?pageNumber=" + pNumber);
				document.addressForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (eshopkey == '') {
					alert("请选择！");
					return;
				}
				if (eshopkey.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + eshopkey).remove();
						eshopkey = "";
					}

					return;
				}
				$f = $('#addressForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/marketingWfj/ea_delEshop.jspa?eshop.eshopkey=" + eshopkey + "&pageNumber=" + pNumber);
					document.addressForm.submit.click();
					$("tr#" + eshopkey).remove();
					eshopkey = "";
					token = 11;
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/marketingWfj/ea_getWfjEshopList.jspa?ogName="+ogName+"&eshop.organizationID="
						+ organizationID;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/marketingWfj/ea_showWfjEshopExcel.jspa?eshop.organizationID="
						+ organizationID + "&pageNumber=" + pNumber;
				open(url);
				break;
		}
	}

	$(".address tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				eshopkey = this.id;
			});
	$(".address tr[id]").dblclick(function() {

				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				eshopkey = this.id;
				action("修改");
			});

	$(".address").find("select[id!=xxx]").each(function() {
				//$s = $(this).hide();
			});
	$(".onc").click(function(){
		heigh = parent.document.getElementById("mainframe6").offsetHeight;
		parent.document.getElementById("mainframe6").style.height = 400 + "px";
		$("#jqModelEntry").jqmHide();
		$("#jqModelEntry").jqmShow();
	});
	
	$("#isSubmit").click(function(){// 选择确定
		var parm1 = $("#parm1",$("#jqmWindow2")).attr("value");
		var parm2 = $("#parm2",$("#jqmWindow2")).attr("value");
		var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择");
			return;
		}
		var value5 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm1).text();//编号
		var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm2).text();//弹出框的页面存在于span中才取得到   姓名
		
		if(parm1 != ""){
		$("#partnerName",$("#addressForm")).attr("value",value2).trigger("blur");
		$("#partnerID",$("#addressForm")).attr("value",value1).trigger("blur");
		$("#childPartnerName",$("#addressForm")).attr("value",value5).trigger("blur");
		}
		$("#ifrs").attr("src","");
		
		parent.document.getElementById("mainframe6").style.height = heigh + "px";
	    $(".jqmWindow").jqmHide();
	    $("#jqModelEntry").jqmShow();
	});
	
	$("#isBack").click(function(){// 返回
	   parent.document.getElementById("mainframe6").style.height = heigh + "px";
       $(".jqmWindow").jqmHide();
	   $("#jqModelEntry").jqmShow();
    });
	
	$("img",$("table#addressTable")).live("mouseover",function(event){  
		
	     	var $div = $("#div_allFun");  
	         $div.show();  
	         $div.css("left",document.body.scrollLeft+event.clientX+1);  
	         $div.css("top","10%");
	         $div.css("width",200);
	         $div.css("height",document.body.clientHeight-30);
	         $div.html($("<img>").attr("src",$(this).attr("src")).css("width",200).css("height",document.body.clientHeight-30));
	         $div.css("background-color","#EFF7FE");     
	 });  
	$("img",$("table#addressTable")).live("mouseout",function(event){  
	     var $div = $("#div_allFun");   
	        $div.hide();   
	 }); 
});
function aa(){
	accift("04",paret,"01");
}
//父页面必须用此方法，以供子页(弹出层返回数据)调用**********************
function paret(e){
	var ds = e.split(",");
	var logoPath=basePath+ds[18];
	var photoPath=basePath+ds[19];
	var tradeCode=ds[20];
	$p = $("tr#" + eshopkey);
	$p.find("input#logo").val(ds[18]);
	$p.find("input#titleimage").val(ds[19]);
	$p.find("input#tradeid").val(ds[20]);
	$p.find("img#logo").attr("src",logoPath);
	$p.find("img#titleimage").attr("src",photoPath);
  	//隐藏弹出层
	$(window.parent.document).find("#accift").hide();
}		
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/marketingWfj/ea_getWfjEshopList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
				+ "&eshop.organizationID=" + organizationID+"&ogName="+ogName;
}