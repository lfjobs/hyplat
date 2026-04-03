$(document).ready(
		function() {
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
			// 遮罩程度%
			}).jqmAddClose('.close');// 添加触发关闭的selector
			// .jqDrag('.drag');// 添加拖拽的selector
			var name;
			if (weixinCompanyId == 1) {
				name = '订单管理';
			} else if (weixinCompanyId == 2) {
				name = '发货单管理';
			} else if (weixinCompanyId == 3) {
				name = '收货单管理';
			} else if (weixinCompanyId == 4) {
				name = '订单收款管理';
			} else {
				name = '其他管理';

			}
			$('.address').flexigrid({
				height : 445,
				width : 'auto',
				minwidth : 30,
				title : name,

				minheight : 80,
				buttons : [
                 {
                	  name:'订单负责人:<input type="test" id="zhi" name="dd"/>'
                	 
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
				
					var url = basePath
							+ "ea/activity/ea_getcomporder.jspa?pageNumber="
							+ pageNumber + "&weixinCompanyId="
							+ weixinCompanyId+"&inforType="+inforType;
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
