$(function(){
	$(".interview_body").attr("style","width:"+(window.innerWidth*0.4)+"px;height:"+(window.innerHeight*0.75)+
			"px;position:fixed;top:"+(window.innerHeight*0.1)+"px;left:"+(window.innerWidth*0.32)+
			"px;border:1px solid;background-color:#fff;z-index:3001;");
	$(".interview_title").attr("style","width: "+$(window).width()+"px;height: "+$(window).height()+"px;position: absolute;top:0px;left:0px;background-color: rgba(0,0,0,0.5);z-index:3000;display:none;");
	
	var title=" <form name='likeForm' id='likeForm'>人才简历库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 职位名称：" +
			"<input type='text' id='jobTitle'  style='width:100px;'>&nbsp;&nbsp;&nbsp;性别：" +
			"<select id='sex' style='width:60px;'><option value=''>全部</option><option value='男'>男</option><option value='女'>女</option></select>&nbsp;&nbsp;&nbsp;学历：" +
			"<input type='text' id='educationValue' style='width:100px;'>&nbsp;&nbsp;&nbsp;投递时间：" +
			"<input type='text' id='postDates' style='width:100px;' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\">&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp;&nbsp;" +
			"<input type='text' id='postDatee' style='width:100px;' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\">&nbsp;&nbsp;&nbsp;" +
			"<input type='button' id='like' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title : title,
		minheight : 80,
		buttons : [{
            name : '查看',
            bclass : 'see',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
				name : '面试通知',
				bclass : 'add',
				onpress : action
					// 当点击调用方法
			}, {
			separator : true
			},{
            name : '不合适',
            bclass : 'delete',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}]
	});
	function action(com, grid) {
		switch (com) {

			case '面试通知' :
                tpID = "";
                name = "";
                var array = new Array();
				var str=$("input[name='checkbox']:checked").attr("id");
				if(str==""||str==null){
					alert("请选择");
					return;
				}
				var bool=true;
				var bool1 =true;

				$("input[name='checkbox']:checked").each(function(){
					if($(this).parent().parent().parent().find("td").eq(7).text()!="待处理"){
						bool=false;
					}
						tpID+=$(this).attr("id")+",";
						name+=$(this).parents("tr").find(".name").text()+",";
					    type= $(this).parents("tr").find(".type").text();
                   if($.inArray(type, array)<0)  {
                          array.push(type);

				   }else{
                       bool1 = false;
                       return false;
				   }

				});

				if(bool1==false){
					alert("抢人才和投递的简历请分别面试通知");
					return false;
				}
				if(tpID!=""){
                    tpID =  tpID.substring(0,tpID.length-1);
                    name = name.substring(0,name.length-1);
				}


                name+"  你好："
				if(!bool){
					alert("选中列表中有已发送面试通知的！");
					return;
				}
				$(".interview_title").show();
				if(array[0]=="投递"){
                    $(".qrc").hide();
				}else{
                    $(".qrc").show();
				}
				break;
			case '查看' :
				if($("input[name='checkbox']:checked").length>1){
					alert("查看时，只能选择一条");
					return;
				}
				var str=$("input[name='checkbox']:checked").attr("id");
				if(str==""||str==null){
					alert("请选择");
					return;
				}
				open(basePath+"ea/tresume/ea_getPageData.jspa?str="+str);
				break;

            case '不合适' :
                tpID = "";
                name = "";
                var array = new Array();
                var str=$("input[name='checkbox']:checked").attr("id");
                if(str==""||str==null){
                    alert("请选择");
                    return;
                }
                $("input[name='checkbox']:checked").each(function(){
                    tpID+=$(this).attr("id")+",";
                });

                if(tpID!=""){
                    tpID = tpID.substring(0,tpID.length-1);

                }
                var url = basePath + "ea/tresume/sajax_ea_auditResume.jspa?talentPool.tpId="+tpID;

                $.ajax({
                    url : url,
                    type : "get",
                    async : false,
                    dataType : "json",
                    success : function cbf(data) {
                        var member = eval("(" + data + ")");
                        alert("操作成功")
                        window.location.href=window.location.href;

                    },
                    error : function cbf(data) {
                        alert("数据获取失败！");
                    }
                });

                break;
			case '设置每页显示条数' :
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/tresume/ea_getListPage.jspa?pageNumber="+pr+"&statusbill="+$("#statusbill").val();
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
			
		}
	}
	$(".buttom").click(function(){
		if($(this).text()=="发送"){
            if(!$(".qrc").is(':hidden')) {
                if ($(".seri").val() == "") {
                    alert("面试职位不能为空！");
                    return;
                }
            }
			if($(".interviewTime").val()==""){
				alert("面试时间不可为空！");return;
			}
			if($(".interviewPlace").val()==""){
				alert("面试地点不可为空！");return;
			}
			if($(".contactor").val()==""){
				alert("请选择联系人！");return;
			}
			if($(".contactTel").val()==""){
				alert("联系人电话不可为空！");return;
			}
			var s="ws="+$(".ws option:selected").text()+"&sj="+$(".sj option:selected").text()+"&tpID="+tpID+
					"&rq="+$(".interviewTime").val()+"&isSMS="+($(".ccbox").attr("checked")?"00":"01")+"&xm="+name;
			// $("#form").attr("target", "hidden").attr("action",basePath+"ea/tresume/ea_sendMessages.jspa?"+s);
            var url = basePath + "ea/tresume/sajax_ea_sendMessages.jspa?"
                + $("#form").serialize() + "&"+s;
            $.ajax({
                url : url,
                type : "get",
                async : true,
                dataType : "json",
                success : function cbf(data) {
                    var member = eval("(" + data + ")");
                    alert("操作成功")
                    window.location.href=window.location.href;

                },
                error : function cbf(data) {
                    alert("数据获取失败！");
                }
            });
		}else{
			$(".inputr").val("");$(".chbox").attr("checked",false);
			$(".interview_title").hide();
		};
	});
	$("#like").click(function(){
		if($("#postDatee").val()!=""&&$("#postDatee").val()<=$("#postDates").val()){
			alert("结束日期不可小于开始日期");
			return;
		}
		var str="jobTitle="+($("#jobTitle").val()!=""?$("#jobTitle").val():"")
				+"&sex="+($("#sex option:selected").val()!=""?$("#sex option:selected").val():"")
				+"&educationValue="+($("#educationValue").val()!=""?$("#educationValue").val():"")
				+"&postDates="+($("#postDates").val()!=""?$("#postDates").val():"")
				+"&postDatee="+($("#postDatee").val()!=""?$("#postDatee").val():"");
		window.location.href=basePath+"ea/tresume/ea_getListPage.jspa?"+str;
	});
	// //每行的点击事件
	// $("tr[id]").live("click",function(){
	// 	$(this).find(".box").attr("checked",!$(this).find(".box").attr("checked"));
	// });
	// $(".box").live("click",function(){
	// 	$(this).attr("checked",!$(this).attr("checked"));
	// });
    //


    $(".tbody tr").toggle(function() {
        $("input.box", $(this))
            .attr("checked", "checked");

    }, function() {
        $("input.box", $(this)).attr("checked", false);
    });
    //用于阻止复选框的冒泡行为；
    $("input.box").click(function(event) {
        event.stopPropagation();

    });
});		
