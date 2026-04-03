$(document).ready(
		function() {
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
			// 遮罩程度%
			}).jqmAddClose('.close');// 添加触发关闭的selector
			// .jqDrag('.drag');// 添加拖拽的selector
			/*var name="";
			if(staffName=="1")
			{
				name="集团公司管理";
			}
			else if(staffName=="2")
			{
				name="合伙创业管理";
				
			}
			
			else if(staffName=="3")
			{
				name="公司微分金店管理";
			}
			else if(staffName=="4")
			{
				name="公司代理商管理";
			}
			else if(staffName=="5")
			{
				
				name="公司消费者管理";
				
			}*/
	
			$('.address').flexigrid({
				height : 445,
				width : 'auto',
				minwidth : 30,
				//title : name,
				minheight : 80,
				buttons : [
                 {
                	  name:'按照名字查询:<input type="test" id="zhi" name="dd"/>'
                	 
                 },
				{
					separator : true
				}, {

					name : '查询',
					bclass : 'mysearch',
					onpress : action
				// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
				// 当点击调用方法
				}, {
					separator : true
				} ]
			});
			function action(com, grid) {
				switch (com) {

				case '查询':		
						 $("#formtest").val($("#zhi").val());
					    
							document.getElementById("addressForm").submit.click();
							break;
						break;
				case '设置每页显示条数':
					var url = ""
					if(status=='1'){
						url=basePath
							+ "ea/activity/ea_findhehuochuangye.jspa?inforType="+inforType+"&status="+status+"&custype="+custype;
					}else if(status=='2'){
						url=basePath
							+ "ea/activity/ea_findCompord.jspa?inforType="+inforType+"&status="+status+"&custype="+custype;
					}
					numback(url);
					break;
				}
			}
			$("table tr[id]").click(
					function() {
						clientPositioningID = this.id;
						$("input.JQuerypersonvalue", $(this)).attr("checked",
								"checked");
					});

		});

/**
 *
 * 下载名片二维码
 */
function downLoadCode(sccId,companyID,obj){

    var url = basePath
        + "ea/activity/sajax_createEwCode.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "get",
        async : false,
        dataType : "json",
		data:{
         companyID:companyID,
            sccId:sccId
		},
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var qrcodePath = member.qrcodePath;
            window.open(basePath + "/servlets/render?filename=" + encodeURI(qrcodePath));

        },
        error : function cbf(data) {
            console.log("生成二维码失败");
        }
    });
}
