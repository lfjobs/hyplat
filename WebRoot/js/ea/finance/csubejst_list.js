$(function(){ 
//parent.tree.deleteItem(parent.treeid,false)
//   ///////////lwt规则使用说明.alert 文件 不会用就看这里
//   alert(parent.subRule.usableLevel+"   <----------------此级次以内可设置新会计科目")
//   alert(parent.subRule.usedLevel+"     <----------------此级次以上编码规则不能修改")
//   alert(parent.subRule.rulesArray+"    <----------------这是一个包含了每一级位数的数组,从0开始计数.如取第二级规定位数为  "+parent.subRule.rulesArray[1])
//   ///////////lwt
	
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	
   
//   if (parent.tree.getLevel(parent.treeid) > parent.subRule.usableLevel) {
//       buttons.shift();
//   }
   
   $('.flexme11').flexigrid({
       height: 300,
       width: 'auto',
       minwidth: 30,
       title: '当前科目--' + parent.tree.getSelectedItemText(),
       minheight: 80,
       buttons:[{
           name: "添加下级",
           bclass: 'add',
           onpress: action //当点击调用方法
       }, {
           // 设置分割线  
           separator: true
       }, {
           name: "添加物品",
           bclass: 'add',
           onpress: action //当点击调用方法
       }, {
           // 设置分割线  
           separator: true
       }, {
           name: "删除当前科目",
           bclass: 'delete',
           onpress: action//当点击调用方法
       }, {
           separator: true
       }, {
           name: "科目编码规则",
           bclass: 'mysearch',
           onpress: action//当点击调用方法
       }, {
           name: "设置每页显示条数",
           bclass: 'mysearch',
    	   onpress : action//当点击调用方法
       }, {
           separator: true
       }]
   });
   
    function action(com, grid){
    
        switch (com) {
            case "添加下级":
				if(parent.tree.getLevel(parent.treeid) > parent.subRule.usableLevel){
					alert('未设置下级一编码规则');
					return;
				}
		        if ("" == parent.treeid) {
                    alert("请选择科目");
                    return;
                }
                document.location.href=basePath+"page/ea/main/finance/production/csubejsts/csubejst_add.jsp?oper=save";
                break;
            case "添加物品":
            	var  treeid= parent.treeid;
             	if ("" == parent.treeid) {
                    alert("请选择科目");
                    return;
                }
             	var url=basePath+"/ea/csbjects/sajax_ea_ajaxCsubejstChild.jspa?subjectsID="+treeid+"&date="+new Date().toLocaleString();
             	$.ajax({
                       url: encodeURI(url),
                       type: "get",
                       async: true,
                       dataType: "json",
                       success: function cbf(data){
                       var member = eval("(" + data + ")");
                       var nologin = member.nologin;
                       if(nologin){
                    	   document.location.href =basePath+"page/ea/not_login.jsp";
                       }
                       var count = member.count;
                       if(count<=0){ 
                    	   $(".jqmWindow", $("#SubjectsForm")).jqmShow();
                       }else{
                    	   alert("该科目不是最底层科目！");
                       }
                    },error: function cbf(data){
                          alert("数据获取失败！");
                       }
                });
                
             	break;
           	case "删除当前科目":
                 var  treeid= parent.treeid;
                 if ("002"== treeid) {
                    alert("不能删除根节点");
                    return;
                 }
                 if (confirm("确定要删除吗?")) {
                 var subjectsPID=parent.tree.getParentId(treeid);
                 var url=basePath+"/ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID="+treeid+"&date="+new Date().toLocaleString();
                 $.ajax({
                        url: encodeURI(url),
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
                        var member = eval("(" + data + ")");
                         var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href =basePath+"page/ea/not_login.jsp";
			                  }
                        var subjectsList = member.subjectsList;
                        if(subjectsList.length==0)
                        { 
                           window.parent.tree.deleteItem(treeid);
                           $("#subjectsForm").attr("action",basePath+"ea/csbjects/t_ea_delCsubejsts.jspa?pageNumber=${pageNumber}&csbjects.subjectsID="+treeid+"&csbjects.subjectsPID="+subjectsPID);
                           document.subjectsForm.submit.click(); 
                        }else
                        {
                         alert("该节点正在使用，不能删除！");
                        }
                     },error: function cbf(data){
                           alert("数据获取失败！");
                        }
                    });
                 }
               	 break;
			case "科目编码规则":
				document.location.href=basePath+"ea/csubjectsrule/ea_checkCSubjectsRule.jspa?pageNumber=${pageNumber}";
				break;
		    case "设置每页显示条数":
				var url=basePath+"ea/csbjects/ea_getCsubejstsListAll.jspa?subjectsID=${subjectsID}";
				numback(url);
				break; 
        }
    }
 });
 function toedit(subjectsID){
      if ("" == subjectsID) {
            alert("请选择科目");
            return;
        }
        //判断是否有子节点，如果有子节点不能修改
        var url=basePath+"/ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID="+subjectsID+"&date="+new Date().toLocaleString();
         $.ajax({
                url: encodeURI(url),
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data){
                var member = eval("(" + data + ")");
                 var nologin = member.nologin;
	                  if(nologin){
	                  document.location.href =basePath+"page/ea/not_login.jsp";
	                  }
                var subjectsList = member.subjectsList;
                if(subjectsList.length==0)
                { 
                   document.location.href=basePath+"/ea/csbjects/ea_toAdd.jspa?oper=update&subjectsID="+subjectsID;
                }else
                {
                 alert("该节点正在使用，不能修改！");
                }
             },
				error: function cbf(data){
                   alert("数据获取失败！");
                }
            });
 }
function deleteid(subjectsID,subjectsStatus){
      var  treeid= parent.treeid;
      var url=basePath+"/ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID="+subjectsID+"&date="+new Date().toLocaleString();
      $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data){
            var member = eval("(" + data + ")");
             var nologin = member.nologin;
                  if(nologin){
                  document.location.href =basePath+"page/ea/not_login.jsp";
                  }
            var subjectsList = member.subjectsList;
            if(subjectsList.length==0)
            { 
              if (confirm("确定要删除吗?")) {
                     window.parent.tree.deleteItem(subjectsID);
                     $("#subjectsForm").attr("action",basePath+"ea/csbjects/t_ea_delCsubejsts.jspa?pageNumber=${pageNumber}&csbjects.subjectsID="+subjectsID+"&csbjects.subjectsPID="+treeid);
                      document.subjectsForm.submit.click(); 
               }
            }else
            {
             alert("该节点正在使用，不能删除！");
            }
         },
        error: function cbf(data){
               alert("数据获取失败！");
            }
        });
}
function re(){
    $("#parmiter").attr("value", "");
    $("#codePID").attr("value", "");
    $("#codeID").attr("value", "");
    $("#codeKey").attr("value", "");
    $("#codeStatus").attr("value", "");
    $("#codeNumber").attr("value", "");
    $("#codeValue").attr("value", "");
    $("#desc").attr("value", "");
    $("#jqModel").jqmHide();
}
function alee(codeID){
   toedit(codeID);
}

/** **********************************选择物品**************************************** */
$(document).ready(function() {
	tree1 = new dhtmlXTreeObject("SubjectsAadTree", "100%", "100%", 0);
	tree1.enableDragAndDrop(false);
	tree1.enableHighlighting(1);
	tree1.enableCheckBoxes(0);
	tree1.enableThreeStateCheckboxes(false);
	tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
	var getcodeurl = basePath
			+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
			+ new Date().toLocaleString();
	tree1.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0, 0, 0,
			0);
	$.ajax({
				url : encodeURI(getcodeurl),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var codeList = member.codeList;
					if (null == codeList) {
						return;
					}
					for (var i = 0; i < codeList.length; i++) {

						tree1.insertNewChild(
								"scode20101014v5zed7cukk0000000002",
								codeList[i].codeID, codeList[i].codeValue, 0,
								0, 0, 0);
						tree1.setUserData(codeList[i].codeID, "codeNumber",
								codeList[i].codeNumber);

					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	tree1.setOnClickHandler(function() {
		var oldtreeid = treeid;
		treeid = tree1.getSelectedItemId();
		treename = tree1.getItemText(treeid);
		if (oldtreeid != treeid) {
			if (treeid != "scode20101014v5zed7cukk0000000002") {
				$(":input#parms").val("typeID=" + treename);
				cx("typeID=" + treename);
				return;
			}
		}
	});
	
	// 双击选中物品
	$("table#gotable tr[id]").live("click", function(event) {
		var b = $("input.ragood", $(this)).attr("checked");
		$("input.ragood", $(this)).attr("checked", !b);
	});
	
	// 复选框选中物品
	$(".ragood").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	
	
	// 上一页
	$("#wpsy").click(function() {
		var sy = $("#wpsy").attr("title");
		if (sy != 0) {
			var typeName = $(":input#parms").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + sy;
			cx(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	
	// 下一页
	$("#wpxy").click(function() {
		var xy = $("#wpxy").attr("title");
		if (xy != 0) {
			var typeName = $(":input#parms").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + xy;
			cx(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	
	// 新增物品
	$(".xzwp").click(function() {
		window.open(basePath
				+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
	});
	
	// 添加所选中的物品到物品单
	$("#selectGood").click(function() {
		if ($("[name='check']").is(':checked')) {
			var str="";
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					str+=$(this).val()+";";
				}
			});
			$(".jqmWindow", $("#SubjectsForm")).jqmHide();
			document.location.href =basePath+"/ea/csbjects/ea_saveGoodNum?goodstr="+str;
		} else {
			alert("请选择物品！");
		}
	});
	
	// 根据输入的物品编号或物品名称查询
	$("input#searchGood").click(function() {
		var typeName = $("#parameter", $("table#searchgood")).val();
		$(":input#parms").val("parameter=" + typeName );
		cx("parameter=" + typeName );
	});
	
	// ajax查询物品列表
	function cx(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy").attr("title", 0);
		$("#wpxy").attr("title", 0);
		$("#wpzy").attr("title", 0);
			var searchurl = basePath
					+ "ea/cashierbills/sajax_ea_getGoodsManageByTypeID.jspa?";
			$.ajax({
				url : encodeURI(searchurl + typeCN + "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath + "page/ea/not_login.jsp";
					}
					var pageForm = member.pageForm;
					if (pageForm == null) {
						alert("没有数据");
						notoken = 0;
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#wpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#wpxy").attr("title", dqy + 1);
					}
					$("span#wpzycount").text(zys);
					var tabletr = "<table width='98%' height='26' align='center' id='dixzwp' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
			tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th><th align='center' bgcolor='#E4F1FA'>芯片号</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th><th align='center' bgcolor='#E4F1FA'>类型</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>换算单位</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th><th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].goodsID + ">";
				tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
						+ pageForm.list[i].goodsID + " name='check' id='cb'/></td>";
				tabletr += "<td id='goodsCoding' align='center'>"
						+ pageForm.list[i].goodsCoding + "</td>";
				tabletr += "<td id='goodsName'  align='center'>"
						+ pageForm.list[i].goodsName + "</td>";
				tabletr += "<td id='defaultStorage'  align='center'>"
						+ pageForm.list[i].defaultStorage + "</td>";
				tabletr += "<td id='mnemonicCode' align='center'>"
						+ pageForm.list[i].mnemonicCode + "</td>";
				tabletr += "<td id='typeID' align='center'>"
						+ pageForm.list[i].typeID + "</td>";
				tabletr += "<td id='model' align='center'>"
						+ pageForm.list[i].model + "</td>";
				tabletr += "<td id='variableID'  align='center'>"
						+ pageForm.list[i].goodsvariable + "</td>";
				tabletr += "<td id='acquiesceStandard' align='center'>"
						+ pageForm.list[i].acquiesceStandard + "</td>";
				tabletr += "<td id='goodsID' style='display:none' align='center'>"
						+ pageForm.list[i].goodsID + "</td>";
				tabletr += "<td id='standard' align='center'>"
						+ pageForm.list[i].standard + "</td>";
				tabletr += "<td id='variableID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variableID + "</td>";
				tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable1ID + "</td>";
				tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable2ID + "</td>";
				tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable3ID + "</td>";
				tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable4ID + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02").html(tabletr);
			$("#body_02").show();
			// alert("数据加载成功")
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
			});
	}
});