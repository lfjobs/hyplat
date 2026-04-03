$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector


	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "预置键管理",
				minheight : 80,
				buttons : [{
							name : '添加',
							bclass : 'add',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '修改',
							bclass : 'edit',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '删除',
							bclass : 'delete',
							onpress : action
							// 当点击调用方法
					   }	,  {
							separator : true
						}, {
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					  } , {
							separator : true
				}]
	});

	function action(com, grid){
		switch (com) {
			case '添加':
                document.addForm.reset();
                $("#scpIds").val("");
                scpId  = "";
               $("#jqModeladd").jqmShow();
                break;
			case '修改' :
				if (scpId == "") {
					alert("请选择！");
					return;
				}
                document.addForm.reset();
				var keycount = $("tr#"+scpId).find("#keyCount").text();
                var scpKey = $("tr#"+scpId).find("#scpKey").text();
                $("#scpIds").val(scpId);

                var searchurl = basePath
                    + "ea/scale/sajax_ea_ajaxPrekeyListByPreID.jspa";
                $.ajax({
                    url : searchurl,
                    type : "get",
                    async : false,
                    dataType : "json",
                    data:{
                        "presetPage.scpId":scpId
                    },
                    success : function cbf(data) {
                        var member = eval("(" + data + ")");
                        var prekeylist = member.prekeylist;
                        number = 0;
                        if(keycount=="28"){
                            //row :4 column:7
                            var html = new Array();
                            for(var i = 0; i<7;i++){
                                html.push("<tr>")
                                for(var j = 0;j<4;j++){

                                    number++;
                                    var obj = prekeylist[number];
                                    html.push("<td style=' border:1px solid #00a0e9;height: 1.5cm;width: 1.5cm;display: block;margin: 0.0875cm;float: left;'>");
                                    html.push("<input type='hidden' name='presetKeymap["+number+"].plu' class='plu' value='"+prekeylist[number-1].plu+"' id='plu'/>");
                                    html.push("<input type='hidden' name='presetKeymap["+number+"].title' class='title' value='"+prekeylist[number-1].title+"'/>");
                                    html.push("<input type='hidden' name='presetKeymap["+number+"].rowx'  value='"+(i+1)+"'/>");
                                    html.push("<input type='hidden' name='presetKeymap["+number+"].columns'  value='"+(j+1)+"'/>");
                                    html.push("<input type='hidden' name='presetKeymap["+number+"].keyNo'  value='"+number+"'/>");
                                    html.push("<div style='font-size: 12px;text-align:center;padding: 0.25cm 0;'><span style='display:block;line-height: 0.5cm;'>"+prekeylist[number-1].title+"</span><span style='display:block;'>"+prekeylist[number-1].plu+"</span></div>");
                                    html.push("</td>");
                                }
                                html.push("</tr>")
                            }
                            $("#setTable").html(html.join(""));
                        }
                    },
                    error:function cbf(data){
                        alert("获取修改数据失败");
                    }
                });
                pcx("");

                $("#jqModelset").jqmShow();
                break;
			case '删除':
                if (scpId == "") {
                    alert("请选择！");
                    return;
                }
                if(confirm("确定删除？")) {
                    $("#addForm #scpIds").val(scpId);
                    $("#addForm").attr("target", "hidden").attr("action", basePath + "ea/scale/ea_deletePresetPage.jspa");
                    document.addForm.submit.click();
                    token = 2;
                }
                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/scale/ea_getPreKeyList.jspa?search="
						+ search;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        scpId = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
      //设置
	$("#setup").click(function() {
        var  keycount = $(".keyCount").val();
         $("#kec").val(keycount);
         if(keycount=="28"){
             //row :4 column:7
            var html = new Array();
             for(var i = 0; i<7;i++){
                 html.push("<tr>")
                 for(var j = 0;j<4;j++){
                     number++;
                     html.push("<td style=' border:1px solid #00a0e9;height: 1.5cm;width: 1.5cm;display: block;margin: 0.0875cm;float: left;'>");
                     html.push("<input type='hidden' name='presetKeymap["+number+"].plu' class='plu'/>");
                     html.push("<input type='hidden' name='presetKeymap["+number+"].title' class='title'/>");
                     html.push("<input type='hidden' name='presetKeymap["+number+"].rowx'  value='"+(i+1)+"'/>");
                     html.push("<input type='hidden' name='presetKeymap["+number+"].columns'  value='"+(j+1)+"'/>");
                     html.push("<input type='hidden' name='presetKeymap["+number+"].keyNo'  value='"+number+"'/>");
                     html.push("<div style='font-size: 12px;text-align:center;padding: 0.25cm 0;'><span style='display:block;line-height: 0.5cm;'></span><span style='display:block;'></span></div>");
                     html.push("</td>");
                 }
                 html.push("</tr>")
             }
             $("#setTable").html(html.join(""));
         }


        $("#jqModelset").jqmShow();
        $("#jqModeladd").jqmHide();
        pcx("");
	});

	//保存
    $("#save").click(function() {

          if($("#setTable #plu").length==0){
              alert("请单击每个单元格设置预置键");
              return;
          }
        $("#addForm").attr("target","hidden").attr(
            "action",
            basePath + "ea/scale/ea_savePreKey.jspa");
        token = 2;
        document.addForm.submit.click();
    });
	//单击单元格
	$("#setTable td").live("click",function(){

        obj = null;
		obj = $(this);

        pop('products');
        $("#jqModelset").jqmHide();

	});
     //关闭
	$("#gb").click(function(){

        $("#jqModelset").jqmShow();
    });



    // 双击选中产品
    $("table#protable tr[id]").live("click", function(event) {
        var b = $("input.raporducts", $(this)).attr("checked");
        $("input.raporducts", $(this)).attr("checked", !b);
    });

    // 单选框选中物品
    $(".raporducts").live("click", function(event) {
        var b = $(this).attr("checked");
        $(this).attr("checked", !b);
    });



    // 上一页
    $("#wpsyp").click(function() {
        var sy = $("#wpsyp").attr("title");
        if (sy != 0) {
            var typeName = $(":input#parameter").val();
            var typeCN = "&parameter=" +typeName + "&pageForm.pageNumber=" + sy;
            pcx(typeCN);
        } else {
            alert("已是首页！");
        }
    });

    // 下一页
    $("#wpxyp").click(function() {
        var xy = $("#wpxyp").attr("title");
        if (xy != 0) {
            var typeName = $(":input#parameter").val();
            var typeCN = "&parameter=" +typeName + "&pageForm.pageNumber=" + xy;
            pcx(typeCN);
        } else {
            alert("已是尾页！");
        }
    });

    // 根据输入的产品编号或产品名称查询
    $("input#searchProduct").click(function() {
        var typeName = $("#parameter", $("table#searchpro")).val();
        pcx("&parameter=" + typeName);
    });

    // 选择产品确定
    $("#selectProduct").click(function() {

        if ($("[name='check']").is(':checked')) {
            var sgID = $("input[name='check']:checked").val();
            var goodsName = $("#protable tr#" + sgID).find("#goodsName").text();
            var plu =  $("#protable tr#" + sgID).find("#plu").text();
            if(goodsName.length>4){
                goodsName = goodsName.substring(0,4);
            }
            $(obj).find("span:eq(0)").text(goodsName);
            $(obj).find("span:eq(1)").text(plu);
            $(obj).find(".plu").val(Number(plu)).attr("id","plu");
            $(obj).find(".plu").attr("id","plu");
            $(obj).find(".title").val(goodsName);
            $("#products").hide();
            $("#jqModelset").jqmShow();

        } else {
            alert("请选择商品");
        }
    });
});

function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/scale/ea_getPreKeyList.jspa?pageNumber=" + pNumber;
    }
}
// 查询项目产品
function pcx(typeCN) {
    if (notoken) {
        alert("正在获取数据！请稍等");
        return;
    }
    notoken = 1;
    $("#wpsyp").attr("title", 0);
    $("#wpxyp").attr("title", 0);
    $("#wpzyp").attr("title", 0);
    var searchurl = basePath
        + "ea/scale/sajax_ea_ajaxGetScaleGoods.jspa?1=1";
    $.ajax({
        url : encodeURI(searchurl + typeCN + "&date="
            + new Date().toLocaleString()),
        type : "get",
        async : true,
        dataType : "json",
        data:{
            search:"search"
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            if (pageForm == null) {
                alert(pageForm+"没有数据");
                notoken = 0;
                return;
            }
            var dqy = pageForm.pageNumber;// 当前页
            var zys = pageForm.pageCount;// 总页数
            if (dqy > 1) {
                $("#wpsyp").attr("title", dqy - 1);
            }
            if (dqy < zys) {
                $("#wpxyp").attr("title", dqy + 1);
            }

            $("span#wpzycountp").text(zys);
            var tabletr ="";
            tabletr += "<table width='100%' align='center' id='protable' cellpadding='0' cellspacing='0' class='table' style='margin-top:15px;'>";
            tabletr += " <tr><th height='27' align='center' bgcolor='#E4F1FA'>选择</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>PLU</th>";
            tabletr += "<th align='center'  bgcolor='#E4F1FA'>产品条码</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>产品名称</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>计价单位</th></tr>";

            for (var i = 0; i < pageForm.list.length; i++) {
                tabletr += "<tr style='cursor: hand;' id = "
                    + pageForm.list[i][3] + ">";
                tabletr += "<td height='27' id='check' align='center'>"
                    + "<input type ='radio' class='raporducts' value="
                    + pageForm.list[i][3] + " name='check'/></td>";
                tabletr += "<td id='plu' align='center'>"
                    + pageForm.list[i][0] + "</td>";
                tabletr += "<td id='barCode' align='center'>"
                    + pageForm.list[i][4] + "</td>";
                tabletr += "<td id='goodsName' align='center'>"
                    + pageForm.list[i][1] + "</td>";

                tabletr += "<td id='unitOfMeasureCode'  align='center'>"
                    + pageForm.list[i][5] + "</td>";

                tabletr += " </tr>";
            }
            tabletr += " </table>";
            $("#body_03").html(tabletr);
            $("#body_03").show();
            notoken = 0;
            window.status = "数据加载成功";
        },
        error : function cbf(data) {
            notoken = 0;
            alert("数据获取失败！");
        }
    });
}
var LODOP; //声明为全局变量
function prn1_preview() {
    if($("#scpIds").val()==""){

        if($("#setTable #plu").length==0){
            alert("请单击每个单元格设置预置键");
            return;
        }
        $("#addForm").attr("target","hidden").attr(
            "action",
            basePath + "ea/scale/ea_savePreKey.jspa");
        token  = 2;
        document.addForm.submit.click();
    }
    CreateOneFormPage();
    LODOP.PREVIEW();
};
function CreateOneFormPage(){
    LODOP=getLodop();
    LODOP.PRINT_INIT("打印预置键");
    LODOP.SET_PRINT_STYLE("FontSize",18);
    LODOP.SET_PRINT_STYLE("Bold",1);
    LODOP.ADD_PRINT_HTM(88,200,350,600,document.getElementById("outline").innerHTML);
};


