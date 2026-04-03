$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$("input.JQuerySubmit").click(function(){  //提交保存
        $(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		if($("#organizationID").val() == comID){
			$("#organizationID").parent().append("<span class=\"error\"><a class=\"tex\">请选择部门！</a></span>");
			return;
		}
		if($("#postResponsibility").val() == ''){
			$("#postResponsibility").parent().append("<span class=\"error\"><a class=\"tex\">岗位职责不能为空！</a></span>");
			return;
		}
		if($("#responsibilityRequire").val() == ''){
			$("#responsibilityRequire").parent().append("<span class=\"error\"><a class=\"tex\">任职要求不能为空！</a></span>");
			return;
		}
		var orgName = $("select :selected").text(); //部门赋值
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						pbasePath
								+ "/ea/departmentpost/ea_saveDepartmentPost.jspa?pageNumber="
								+ ppageNumber + "&orgName=" + orgName) + "&aa=aa&companyName="
								+ companyName;
		document.cstaffForm.submit.click();
		if(depPostID == ''){
			token = 1;
			document.cstaffForm.reset();
			$("#cstaffForm").find(".error").remove();
			$("#cstaffForm").find(".corect").remove();
			for(var i=0,j=i+1;i<3;i++,j++){
				if(maxNum.substring(i,j) != '0'){
					maxNum = maxNum.substring(i);
					var nums = (parseInt(maxNum)+1).toString();
					if(nums.length < 4){
						var str = "0000";
						nums = str.substring(0,4-nums.length) + nums;
					}
					$("#postNum").val(nums); //继续添加时职位编号赋值
					return;
				}
			}
		}else{
			token = 2;
		}
		
    });
    
    $("input.JQueryNoSubmit").click(function(){  //取消
    	token = 2;
    	re_load();
    });
    
    $(function() {
    	if(postNum == ''){
    		$("#postNum").val(maxNum); //职位编号赋值
    	}
    	//职位所属部门
		var url = pbasePath+"ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="+comID+"&date1="+new Date(); 
		$.ajax({
			    url:encodeURI(url),
			    type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
					var member = eval("(" + data + ")");
	                var oList = member.organizationlist;
	                var data2 = new Array();
	                //var selid;
			        data2[0] = {
		                id: comID,
		                pid: '-1',
		                text: '选择部门'
		            };
	                for (var i = 0; i < oList.length; i++) {
	                    data2[i + 1] = {
	                        id: oList[i].organizationID,
	                        pid: oList[i].organizationPID,
	                        text: oList[i].organizationName
	                    };
	                }
	                ts = new TreeSelector($("#organizationID")[0], data2, -1);
	        		ts.createTree();
	        		$("#organizationID").val(organizationName);
	        		$("#cstaffForm").find(".corect").remove();
				},
				error: function cbf(data){
					alert("数据获取失败！");
				}
		});
    });
    
    //获取一级部门ID(leveloneOrgID)
    $("#organizationID").change(function(){
    	var index = this.options.selectedIndex;
    	var parNode = this.options[index].parentNode;
    	for(var i=index; i>=0; i--){
    		if(parNode.childNodes[i].text.split("　").length == 2){
    			$("#leveloneOrgID").val(parNode.childNodes[i].value);
    			return;
    		}
    	}
    });
});

function re_load() {
	if (token)
		if(reValue == "1"){
			document.location.href=pbasePath
						+ "page/ea/main/human/office/SersonnelSystem/staff_post.jsp?staffName="
						+ stName + "&staffID=" + stID
						+ "&auditionID=" + auID;
		}else if(reValue == "2"){
			document.location.href=pbasePath
						+ "page/ea/main/human/office/production/staff_post.jsp?pageNumber="
						+ ppageNumber + "&staffName=" + stName + "&staffID="
						+ stID + "&pagetype=01 &pnum=" + pnum 
						+ "&aa=aa&companyName="+ companyName;
		}else{
			document.location.href=pbasePath
    				+"ea/departmentpost/ea_getDepartmentPostList.jspa?pageNumber="+ppageNumber+"&search="+psearch;
		}
}