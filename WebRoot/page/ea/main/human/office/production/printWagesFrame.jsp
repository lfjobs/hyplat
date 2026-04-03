<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>工资汇总打印框架</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>css/ea/main.css" rel="stylesheet"
			type="text/css" />
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css" media="Print">
@media Print {
	.noPrint {
		DISPLAY: none;
	}
	@media Screen {
		.noDisaplay {
			DISPLAY: none;
		}
	}
}
</style>
		<script type="text/javascript">
    var basePath ='<%=basePath%>';
	var pageNumber = '${param.pageNumber}';
	var search = '${param.search}'; 
	var pstaffName = '${param.staffName}';
	var sdate = '${param.sdate}';
	var edate = '${param.edate}';
	var staffcategoryid ='${param.staffcategoryid}';
	var arg= '${param.arg}';
	var pageNum=0;
	var pageCount='${param.pageCount}';
	var one='${param.one}';
	var weibokuan='${param.weibokuan}';	
	var level='${param.level}';
	var cother='${param.cother}';
	
	  $(function(){ 
            $(".xsxyCloneClass").live('click',function(){
            	pageNum++;
            	if(pageCount!=''&&pageNum<=parseInt($.trim(pageCount))){
					var url="";
					if($.trim(one)=='one0'){
						 url=basePath+ "ea/logbooksummary/ea_printWages.jspa?staffName="
							  + pstaffName + "&sdate=" + sdate + "&edate=" + edate
							  + "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&pageForm.pageNumber="+pageNum+"&pageNumber="+pageNumber;
					}
					if($.trim(one)=='one'){
						 url =basePath
								+ "ea/logbooksummary/ea_printEveryoneWages.jspa?staffName="
								+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
								+ "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&pageForm.pageNumber="+pageNum+"&pageNumber="+pageNumber;
					}
					if($.trim(one)=='one1'){
						if(cother=="cother"){
							url =basePath
							+ "ea/cashapplybills/ea_toCash.jspa?sdate=" + sdate + "&edate=" + edate
							+ "&search=" + search+"&pageForm.pageNumber="+pageNum+"&pageNumber="+pageNumber+"&weibokuan="+weibokuan+"&print=print&cother=cother"+"&level="+level;
						}else{
							url =basePath
							+ "ea/cashapplybills/ea_toCash.jspa?sdate=" + sdate + "&edate=" + edate
							+ "&search=" + search+"&pageForm.pageNumber="+pageNum+"&pageNumber="+pageNumber+"&weibokuan="+weibokuan+"&print=print"+"&level="+level;
						}	 
					}
					if($.trim(one)=='one2'){
						 url =basePath
								+ "ea/cosdimission/ea_printDimissionWages.jspa?staffName="
								+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
								+ "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&pageForm.pageNumber="+pageNum+"&pageNumber="+pageNumber;
					}
					if($.trim(one)=='one3'){
						 url =basePath
								+ "ea/cosdimission/ea_printEveryoneDimWages.jspa?staffName="
								+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
								+ "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&pageForm.pageNumber="+pageNum+"&pageNumber="+pageNumber;
					}
					if($.trim(one)=='one4'){
						 url =basePath 
								+ "ea/monthSalary/ea_printManthSalary.jspa?search=" 
								+ search + "&pageForm.pageNumber="+pageNum+"&pageNumber="
								+ pageNumber;
					}			  
	            	var  titleClone=$("div#titleClone").clone(true);
	            	titleClone.attr("id","titleClone"+pageNum);
	            	titleClone.find("#contextClone").attr("id","contextClone"+pageNum);
	            	titleClone.find("#frameClone").attr("id","frameClone"+pageNum);
	            	titleClone.find("#buttonClone").attr("id","buttonClone"+pageNum);
	            	titleClone.find("#dayinClone").attr("id","dayinClone"+pageNum);
	            	titleClone.find("#xsxyClone").attr("id","xsxyClone"+pageNum);
	            	titleClone.find("#xsqbClone").attr("id","xsqbClone"+pageNum);
	            	titleClone.find("#pageCode").text("第 "+pageNum+" 页/共 "+pageCount+" 页");
	            	titleClone.attr("style","width: 100%;height: 100%");
	            	titleClone.find("#frameClone"+pageNum).attr("src",url);
	            	$("body").append(titleClone);
           		};
            });
            $(".dayinCloneClass").live('click',function(){
            	var printframe="frameClone"+($(this).attr("id").substring(10));
            	var  height=$("#"+printframe).css("height");
            	window.open($("#"+printframe).attr("src"),'newwindow','height='+height+',top=10,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,alwaysRaised=1');
            });
            $(".xsqbCloneClass").live('click',function(){
	            	while(pageNum<parseInt($.trim(pageCount))){
	            		$(".xsxyCloneClass").trigger("click");
	            	}
            });
        });
     </script>
	</head>
	<body onload="document.getElementById('xsxyClone').click()">
		<div style="width: 100%; display: none; height: 100%" id="titleClone">
			<div id="buttonClone" style="text-align: center">
				<input id="dayinClone" type="button"
					class="dayinCloneClass noPrint input-button" value="打印本页" />
				<input id="xsxyClone" class="xsxyCloneClass noPrint input-button"
					type="button" value="显示下页" />
				<input id="xsqbClone" class="xsqbCloneClass noPrint input-button"
					type="button" value="显示全部" />
				<span id="pageCode"
					style="font-weight: bold; position: absolute; margin-left: 300px"
					class="noPrint"></span>
			</div>
			<div id="contextClone">
				<iframe name="main" width="100%" scrolling="no" frameborder="0"
					noresize="noResize" vspale="0" id="frameClone"
					class="frameCloneClass" border="0"
					onload="height=this.contentWindow.document.getElementById('content').offsetHeight">
				</iframe>
			</div>
		</div>
	</body>
</html>
