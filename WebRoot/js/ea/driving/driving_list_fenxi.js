$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	var searchFormHtml=$("div#jqModelSearchss").html();	
	$("div#jqModelSearchss").remove();
	$('.address').flexigrid({
				height : 240,
				width : 'auto',
				minwidth : 30,
				title : '考试合格率分析 > ' +(docstatus=='01'?'科一':docstatus=='02'?'科二':docstatus=='03'?'科三':docstatus=='04'?'科四':'汇总')+searchFormHtml,
				minheight : 50,
				buttons : [  {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '(个人)教练业绩明细表打印',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '(月度)教练培训质量排行榜报表打印',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法 
					}, {
					separator : true
				}, {
					name : '(年度)教练培训质量排行榜报表打印',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '(年度)各科目合格率统计报表打印',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '返回上级':
				if(other=='other')
				document.location.href=basePath+"page/ea/main/navigation/driving_management_kaoshi.jsp";
				else
				document.location.href=basePath+"page/ea/main/navigation/driving_management.jsp";
				break; 
			case '查询' :
				$("#jqModelSearchss").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getAnalysisList.jspa?docstatus="+docstatus+"&sdate="+sdate+"&edate="+edate+"&search="+search+"&other="+other;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/driving/ea_getAnalysisList.jspa?docstatus="+docstatus+"&sdate="+sdate+"&edate="+edate+"&search="+search+"&excel=excel";
				open(url);
				break;
			case '(个人)教练业绩明细表打印' :
				$("div#jqModelPrint").jqmShow();
				break;
			case '(月度)教练培训质量排行榜报表打印' :
				$("div#jqModelPrintThree").jqmShow();	
				break;
			case '(年度)教练培训质量排行榜报表打印' :
				$("div#jqModelPrintFour").jqmShow();	
				break;	
			case '(年度)各科目合格率统计报表打印' :
				$("div#jqModelPrintTwo").jqmShow();	
		}
	}
	 $("#tosearch").click(function(){
            $(".put3").trigger("blur");
            if ($("#SearchForm .error").length) {
                alert("请填完所有必填项");
                return;
            }
            $("#SearchForm").attr("action", basePath+"ea/driving/ea_toSearchAnalysis.jspa?other="+other);
            document.SearchForm.submit.click();
     });
	 /**************打印****************/
	 $("tr").click(function(){
		 coachid=$.trim($(this).find("span#coachid").text());
		 $(this).find("input.JQuerypersonvalue").attr("checked","checked");
	 });
	 $("#tosearchOfPrint").click(function(){
         $(".put3").trigger("blur");
         if ($("#PrintForm .error").length) {
             alert("请填完所有必填项");
             return;
         }
         $("#PrintForm").attr("target","_blank").attr("action", basePath+"ea/driving/ea_getListOfStudent.jspa?other="+other);
         document.PrintForm.submit.click();
	 });
	 $("#tosearchOfPrintTwo").click(function(){
         $(".put3").trigger("blur");
         if ($("#PrintFormTwo .error").length) {
             alert("请填完所有必填项");
             return;
         }
         $("#PrintFormTwo").attr("target","_blank").attr("action", basePath+"ea/driving/ea_getListQualificationOfSubjects.jspa?other="+other);
         document.PrintFormTwo.submit.click();
	 });
	 $("#tosearchOfPrintThree").click(function(){
         $(".put3").trigger("blur");
         if ($("#PrintFormThree .error").length) {
             alert("请填完所有必填项");
             return;
         }
         $("#PrintFormThree").attr("target","_blank").attr("action", basePath+"ea/driving/ea_getListOfCoachsOfqualified.jspa?other="+other);
         document.PrintFormThree.submit.click();
	 });
	 $("#tosearchOfPrintFour").click(function(){
         $(".put3").trigger("blur");
         if ($("#PrintFormFour .error").length) {
             alert("请填完所有必填项");
             return;
         }
         $("#PrintFormFour").attr("target","_blank").attr("action", basePath+"ea/driving/ea_getListOfCoachsOfqualifiedOfAnnual.jspa?other="+other);
         document.PrintFormFour.submit.click();
	 });
	 $("span#xzjl").click(function(){
		 $("#daoRu").attr("src", basePath + "ea/driving/ea_getCarInformationList.jspa?");
	     $("#bankJqm").jqmShow();
	 });
	 $("#DaoRuFan").click(function(){// 返回
	        $("#bankJqm").jqmHide();
	 });
	 $("#DaoRuFanqd").click(function(){// 选择确定
	        var checkform = $("#checkform", $("#bankJqm")).attr("value");
	        var carID = window.frames["daoRu"].carID;
	        if (carID == "") {
	            alert("请选择");
	            return;
	        }
	        var staffName = window.frames["daoRu"].$('tr#' + carID).find("span#staffName").text();
	        var carsID = window.frames["daoRu"].$('tr#' + carID).find("span#carID").text();
	        
	        $("input#coachname", $("form#PrintForm")).attr("value", staffName).trigger("blur");
	        $("input#coachid", $("form#PrintForm")).attr("value", carsID).trigger("blur");
	        $("#daoRu").attr("src", "");
	        $("#bankJqm").jqmHide();
	    });
});