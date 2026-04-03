  $(function(){
				
                var responsibilitiesID = "";
                
                $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
           // .jqDrag('.drag');// 添加拖拽的selector
                $('.JQueryflexme').flexigrid({
                    height: 350,
                    width: 'auto',
                    minwidth: 30,
                    title: '岗位职责汇总',
                    minheight: 80,
                    buttons: [{
                        name: '查看',
                        bclass: 'edit',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '查询',
                        bclass: 'mysearch',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '导出',
                        bclass: 'excel',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    },{
			            name: '设置每页显示条数',
			            bclass: 'mysearch',
						onpress : action//当点击调用方法
			        },{
			            separator: true
			        }]
                });
                function action(com, grid){
                    switch (com) {
                        case '查看':
                            if (responsibilitiesID == "") {
                                alert('请选择!');
                                return;
                            }
                            document.cstaffForm.reset();
                            $t = $("table#stafftable");
                            $p = $("tr#" + responsibilitiesID);
                            $p.find("span[id]").each(function(){
                                $t.find(":input[name]#" + this.id).val($(this).text()).attr('readonly','readonly');
                            });
                             var photo=$p.find("span#photo").text();
                            if(photo.length!=0)
                            {
                              $t.find('#pic').attr("src", basePath+photo);
                            }
                            $("#jqModel").jqmShow();
                            break;
                        case '查询':
                            $("#jqModelSearch").jqmShow();
                            break;
                        case '导出':
                            url = basePath+"ea/responsibilitiessummary/ea_showExcelSummary.jspa?search="+search+"&companyID="+companyID;
                            open(url);
                            break;
                        case '设置每页显示条数':
						    var url=basePath+"ea/responsibilitiessummary/ea_getResponsibilitiesListSummary.jspa?search="+search+"&companyID="+companyID;
							numback(url);
							break;    
                    }
                }
                $(".menu00").click(function(){
                    $(this).hide();
                });
                $(".JQueryflexme tr[id]").dblclick(function(){
                    action('查看');//当双击时出发 action方法.等价于先选中再点击修改按钮
                });
                $(".JQueryflexme tr[id]").click(function(){
                    responsibilitiesID = this.id;
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                });
                
                $("#cstaffForm").find("#staffID").change(function(){
                    var staffID = $(this).children("option:selected").val();
                    if(!staffID){
                         staffID=cstaffID;
                    } 
                    var url = basePath+"ea/responsibilities/sajax_n_ea_getcstaffByID.jspa?staffID="+staffID+"&date3="+new Date();
                    	$.ajax({
						 url: encodeURI(url),
							type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
									var member = eval("(" + data + ")");
			                        var staff = member.staff;
			                        $("#cstaffForm").find("#staffCode").val(staff.staffCode);
			                        $("#cstaffForm").find("#pic").attr("src", basePath + staff.photo);
							},
						error: function cbf(data){
						alert("数据获取失败！");
						}
					}); 
                    
                });
//                $(".JQueryflexme").find("select").each(function(){
//                    $s = $(this).hide()
//                    $o = $("<span/>").text($s.find("option:selected").text()).attr('id',this.id);
//                    $o.insertAfter($s)
//                })
                
                $("#tosearch").click(function(){
                    $("#postSearchForm").attr("action", basePath+"ea/responsibilitiessummary/ea_toSearchSummary.jspa?pageNumber="+pNumber+"&companyID="+companyID);
                    document.postSearchForm.submit.click();
                });
 });
$(function(){   
        //查询总公司下的所有子公司
		var url = basePath + "ea/company/sajax_n_ea_getCompanyList.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var companylist = member.companylist;
						var data1 = new Array();
						data1[0] = {
							id : comID,
							pid : '-1',
							text : comName
						};
						for (var i = 0; i < companylist.length; i++) {
							data1[i + 1] = {
								id : companylist[i].companyID,
								pid : companylist[i].companyPID,
								text : companylist[i].companyName
							};
						}
						var ts3 = new TreeSelector($("#deptID")[0], data1, -1);
						ts3.createTree();
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
		
        $("#deptID").change(function(){
            $("option", $("#orgID")).remove();
            var url = basePath+"ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="+this.value+"&date1="+new Date(); 
						$.ajax({
						    url:encodeURI(url),
						    type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
								var member = eval("(" + data + ")");
				                var oList = member.organizationlist;
				                 var data2 = new Array();
						            data2[0] = {
						                id: $("#deptID").attr("value"),
						                pid: '-1',
						                text: '全部'
						            };
				                for (var i = 0; i < oList.length; i++) {
				                    data2[i + 1] = {
				                        id: oList[i].organizationID,
				                        pid: oList[i].organizationPID,
				                        text: oList[i].organizationName
				                    };
				                }
				                 ts = new TreeSelector($("#orgID")[0], data2, -1);
            					ts.createTree();
							},
						error: function cbf(data){
						alert("数据获取失败！");
						}
					}); 
            
          
			$("option[value="+this.value+"]",$("#orgID")).val("");
        });
});
