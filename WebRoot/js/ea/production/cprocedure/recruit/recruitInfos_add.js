$(document).ready(function() {

  
	if(riId!=""){
    	$(".option").each(function(){
    		var v = $(this).find("input").eq(0).val();
    		$(this).find(".sel").find("option[text='"+v+"']").attr("selected",true);
    	});
    	
    	$(".check").each(function(){
    		var v = $(this).find("input").eq(0).val();
    		$(this).find("input:radio[value='"+v+"']").attr("checked","checked");
    	});
    }
	// 提交保存
	$(".save").click(function() {
		
		
		$("form :input:.ckTextLength").trigger("blur");
		$("form :input:.input3").trigger("blur");
		$("form :input:.posnum").trigger("blur");
		  $("form :input:.put3").trigger("blur");
          if($("form .error").length)
          { 
            return;
          } 

		$("#mainForm").attr("target", "hidden").attr("action",
				basePath + "ea/bidrecruit/ea_saveRecruitInfo.jspa");

		document.mainForm.submit.click();
		token = 2;
		    
	});



	// $("#selectpr").click(function() {
    //
	// 			pcx("");
    //
	// 		});

	// 双击选中产品
	// $("table#protable tr[id]").live("click", function(event) {
	// 			var b = $("input.raporducts", $(this)).attr("checked");
	// 			$("input.raporducts", $(this)).attr("checked", !b);
	// 		});
    //
	// // 单选框选中物品
	// $(".raporducts").live("click", function(event) {
	// 			var b = $(this).attr("checked");
	// 			$(this).attr("checked", !b);
	// 		});
    //
	// // 上一页
	// $("#wpsyp").click(function() {
	// 			var sy = $("#wpsyp").attr("title");
	// 			if (sy != 0) {
	// 				var typeName = $(":input#parameter").val();
	// 				var typeCN = typeName + "&pageForm.pageNumber=" + sy;
	// 				pcx(typeCN);
	// 			} else {
	// 				alert("已是首页！");
	// 			}
	// 		});
    //
	// // 下一页
	// $("#wpxyp").click(function() {
	// 			var xy = $("#wpxyp").attr("title");
	// 			if (xy != 0) {
	// 				var typeName = $(":input#parameter").val();
	// 				var typeCN = typeName + "&pageForm.pageNumber=" + xy;
	// 				pcx(typeCN);
	// 			} else {
	// 				alert("已是尾页！");
	// 			}
	// 		});
    //
	// // 根据输入的产品编号或产品名称查询
	// $("input#searchProduct").click(function() {
	// 			var typeName = $("#parameter", $("table#searchpro")).val();
	// 			pcx("parameter=" + typeName);
	// 		});
    //
	// // 选择职位确定
	// $("#selectProduct").click(function() {
    //
	// 	if ($("[name='check']").is(':checked')) {
	// 		var ppID = $("input[name='check']:checked").val();
	// 		$("table#productbl").find("#positionID").val(ppID);
	// 		$("table#productbl").find("#positionName").val($("#protable tr#" + ppID).find("td#goodsName").text());
	// 		$("#products").hide();
    //
	// 	} else {
	// 		alert("请选择职位");
	// 	}
	// });

	/*关闭职业弹框*/
    $("#delete img").click(function(){
        $(".Certificate_alert_").hide();
    });

});

function re_load() {
	window.opener.location.href = window.opener.location.href;
	window.close();

}

// 查询项目产品
// function pcx(typeCN) {
// 	if (notoken) {
// 		alert("正在获取数据！请稍等");
// 		return;
// 	}
// 	notoken = 1;
// 	$("#wpsyp").attr("title", 0);
// 	$("#wpxyp").attr("title", 0);
// 	$("#wpzyp").attr("title", 0);
// 	var searchurl = basePath
// 			+ "ea/prodesign/sajax_ea_getProductDesignList.jspa?";
// 	$.ajax({
// 		url : encodeURI(searchurl + typeCN + "&date="
// 				+ new Date().toLocaleString()+"&type=02"),
// 		type : "get",
// 		async : true,
// 		dataType : "json",
// 		data : {
// 			iscall : "call",
// 			search : "search",
// 		    typeID:"职位"
// 		},
// 		success : function cbf(data) {
// 			var member = eval("(" + data + ")");
// 			var pageForm = member.pageForm;
// 			if (pageForm == null) {
// 				alert("没有数据");
// 				notoken = 0;
// 				return;
// 			}
// 			var dqy = pageForm.pageNumber;// 当前页
// 			var zys = pageForm.pageCount;// 总页数
// 			if (dqy > 1) {
// 				$("#wpsyp").attr("title", dqy - 1);
// 			}
// 			if (dqy < zys) {
// 				$("#wpxyp").attr("title", dqy + 1);
// 			}
// 			//
// 			$("span#wpzycountp").text(zys);
// 			var tabletr = "";
// 			tabletr += "<table width='100%' align='center' id='protable' cellpadding='0' cellspacing='0' class='table'>";
// 			tabletr += " <tr><th height='27' align='center' bgcolor='#E4F1FA'>选择</th>";
// 			tabletr += "<th align='center' bgcolor='#E4F1FA'>行业</th>";
// 			tabletr += "<th align='center'  bgcolor='#E4F1FA'>条码</th>";
// 			tabletr += "<th align='center' bgcolor='#E4F1FA'>编码</th>";
// 			tabletr += "<th align='center' bgcolor='#E4F1FA'>名称</th>";
// 			tabletr += "</tr>";
//
// 			for (var i = 0; i < pageForm.list.length; i++) {
// 				tabletr += "<tr style='cursor: hand;' id = "
// 						+ pageForm.list[i].ppID + ">";
// 				tabletr += "<td  height='27' id='check' align='center'>"
// 						+ "<input type ='radio' class='raporducts' value="
// 						+ pageForm.list[i].ppID + " name='check'/></td>";
// 				tabletr += "<td id='tradeCode' align='center'>"
// 						+ pageForm.list[i].tradeCode + "</td>";
// 				tabletr += "<td id='barCode' align='center'>"
// 						+ pageForm.list[i].barCode + "</td>";
// 				tabletr += "<td id='productCode' align='left'>"
// 						+ pageForm.list[i].productCode + "</td>";
// 				tabletr += "<td id='goodsName'  align='center'>"
// 						+ pageForm.list[i].goodsName + "</td>";
// 				tabletr += "<td id='ppID' align='center' style='display:none;'>"
// 						+ pageForm.list[i].ppID + "</td>";
// 				tabletr += " </tr>";
// 			}
// 			tabletr += " </table>";
// 			$("#body_03").html(tabletr);
// 			$("#body_03").show();
// 			notoken = 0;
// 			window.status = "数据加载成功";
// 		},
// 		error : function cbf(data) {
// 			notoken = 0;
// 			alert("数据获取失败！");
// 		}
// 	});
// }



//获取职位的一级分类
function getCodeValueFirst(){
    var url=basePath+"ea/newpcend/sajax_ea_ajaxCodeValueFirst.jspa";
    $.ajax({
        url:url,
        type:"post",
        async : true,
        dataType:"json",
        success:function(data){
            var member = eval("(" + data + ")");
            var array = [];
            if(member!=null&&member.mainObj!=null&&member.mainObj.length>0){
                $(member.mainObj).each(function(){
                    array.push("<li><p>"+this[1]+"</p>");
                    array.push("<input type='hidden' value='"+this[0]+"' />");
                    array.push("<img src='"+basePath+"page/newMyapp/images/ico-right2.png'></li>")
                });
            }
            $(".Certificate_alert .left").find("ul").empty();
            $(".Certificate_alert .left").find("ul").append(array.join(""));
			/*职业更多*/

                $(".Certificate_alert .right").find("ul").remove();
                var codeID=$(".Certificate_alert .left ul li:first").find("input").val();
                $.ajax({
                    url:basePath+"ea/newpcend/sajax_ea_ajaxCodeValue.jspa",
                    type:"post",
                    async : true,
                    data:{"codeID":codeID},
                    dataType:"json",
                    success:function(data){
                        var members = eval("(" + data + ")");
                        var secArray = [];
                        if(members!=null&&members.secObj!=null&&members.secObj.length>0){
                            $(members.secObj).each(function(){
                                secArray.push("<ul><h5 onclick='showPosition(this)'>"+this[1]+">><input type='hidden' value='"+this[0]+"' /></h5>");
                                var value=this[0];
                                $(members.nextObj).each(function(){
                                    if(this[1]==value){
                                        secArray.push("<li onclick='showPosition(this);'>"+this[2]);
                                        secArray.push("<input type='hidden' value='"+this[0]+"' /></li>")
                                    }
                                });
                                secArray.push("</ul>");
                            });
                        }
                        $(".Certificate_alert .right").append(secArray.join(""));
                    },
                    error:function(){
						/*alert("职位加载失败！");*/
                    }
                })
            	$(".Certificate_alert_").show();
                $(".Certificate_alert .left ul li:first").addClass("active").siblings().removeClass("active");

			/*职业弹框左*/
            $(".Certificate_alert .left ul li").click(function(){
                $(this).addClass("active").siblings().removeClass("active");
                var codeID=$(this).find("input").val();
                $.ajax({
                    url:basePath+"ea/newpcend/sajax_ea_ajaxCodeValue.jspa",
                    type:"post",
                    async : true,
                    data:{"codeID":codeID},
                    dataType:"json",
                    success:function(data){
                        var members = eval("(" + data + ")");
                        var secArray = [];
                        if(members!=null&&members.secObj!=null&&members.secObj.length>0){
                            $(members.secObj).each(function(){
                                secArray.push("<ul><h5 onclick='showPosition(this)'>"+this[1]+">><input type='hidden' value='"+this[0]+"' /></h5>");
                                var value=this[0];
                                $(members.nextObj).each(function(){
                                    if(this[1]==value){
                                        secArray.push("<li onclick='showPosition(this);'>"+this[2]);
                                        secArray.push("<input type='hidden' value='"+this[0]+"' /></li>")
                                    }
                                });
                                secArray.push("</ul>");
                            });
                        }
                        $(".Certificate_alert .right").find("ul").remove();
                        $(".Certificate_alert .right").append(secArray.join(""));
                    },
                    error:function(){
						/* alert("职位加载失败！");*/
                    }
                });
            });
        },
        error: function(){
			/*alert("职位加载失败！");*/
        }
    });
}
//展示选中的职位
function showPosition(obj){
    var txt = $(obj).text();
    var codeID=$(obj).find("input").val();
    $("#positionName").val(txt);
    $("#positionCode").val(codeID);
    $(".Certificate_alert_").hide();
}
