<%@page import="hy.ea.bo.Company"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
<title>成功率报表</title>

<script type="text/javascript">
var basePath = "<%=basePath%>";
var  qb="${qb}";
var companyid="<%=c.getCompanyID()%>";
var companyname="<%=c.getCompanyName()%>";
$(function(){
var pid=companyid;
		var pname=companyname;
				var url = basePath
						+ "ea/company/sajax_n_ea_getCompanyList.jspa?date="
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
									id : pid,
									pid : '-1',
									text :pname
								};
								for (var i = 0; i < companylist.length; i++) {
									data1[i + 1] = {
										id : companylist[i].companyID,
										pid : companylist[i].companyPID,
										text : companylist[i].companyName
									};
								}
								var ts3 = new TreeSelector($("#deptID")[0],
										data1, -1);
								ts3.createTree();
								$("select#deptID").get(0).selectedIndex=0;
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});
    $("#tosearch").click(function(){
    	if($(".rad:checked").val() == undefined){
    		alert('请选择查询方式！');
    		return;
    	};
        if($(".rad:checked").val() == '1'){
        	$(".put3",$("tr.rad1")).trigger("blur");
	        if ($("#SearchForm .error").length){
	            alert("请填完所有必填项");
	            return;
	        }
	        if(qb=="02"){
	         window.open(encodeURI(basePath+"ea/cashtime/ea_completeList.jspa?qb="+qb+"&nian="+"01"+"&stime="+$("#sdates").val()+"&etime="+$("#edates").val()+"&level="+$("select#deptID").val()+"&names="+$("select#deptID").text()));
	        }else{
	         window.open(encodeURI(basePath+"ea/cashtime/ea_completeList.jspa?qb="+qb+"&nian="+"01"+"&stime="+$("#sdates").val()+"&etime="+$("#edates").val()));
	        }
       
        }else{
       		$(".put3",$("tr.rad2")).trigger("blur");
	        if ($("#SearchForm .error").length){
	            alert("请填完所有必填项");
	            return;
	        }
	        if(qb=="02"){
	        window.open(encodeURI(basePath+"ea/cashtime/ea_completeList.jspa?qb="+qb+"&nian="+"10"+"&stime="+$("#sdate").val()+"&etime="+$("#edate").val()+"&level="+$("select#deptID").val()+"&names="+$("select#deptID").text()));
	        }else{
	        window.open(encodeURI(basePath+"ea/cashtime/ea_completeList.jspa?qb="+qb+"&nian="+"10"+"&stime="+$("#sdate").val()+"&etime="+$("#edate").val()));
	        }
        	
        }
        $(".rad:checked").removeAttr("checked"); //去除选中事件
        document.SearchForm.submit.click();
    });
});
 
</script>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form  name="SearchForm" method="post" id="SearchForm">
<input type="submit" name="submit" style="display:none"/>
<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">查询</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
  		</tr>
</table>

		 <table id="cataffSearchTable" width="98%" height="180" align="center" style="border:#D1DDE9 1px solid;">
                   <tr>
                    <td width="35%" align="right" class="txt02">
                      <input type="radio" name="ra" class="rad" value="1"/>按年查询
                      
                    </td>
                    <td width="40%" style="font-size: 12px;color: #000000;">
                      <input type="radio" name="ra" class="rad" value="2"/>按月查询
                    </td>
                    </tr> 
                    <tr class="rad3" style="display: none;">
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID">
							</select>
						</td>
					</tr>
                    <tr class="rad2" style="display: none;">
                        <td width="35%"  align="right" class="txt02">
                            		起月：
                        </td>
                        <td width="40%">
                            <input name="sdate" id="sdate" class="put3" onfocus="daymonth(this);"/>
                        </td>
                    </tr>
                    <tr class="rad2" style="display: none;">
                        <td width="35%"  align="right" class="txt02">
                           		 止月：
                        </td>
                        <td width="40%">
                            <input name="edate" id="edate" class="put3" onfocus="daymonth(this);"/>
                        </td>
                    </tr>
                     <tr class="rad1" style="display: none;">
                        <td width="35%"  align="right" class="txt02">
                            		起年：
                        </td>
                        <td width="40%">
                            <input name="sdate" id="sdates" class="put3" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>
                        </td>
                    </tr>
                    <tr class="rad1" style="display: none;">
                        <td width="35%"  align="right" class="txt02">
                           		 止年：
                        </td>
                        <td width="40%">
                            <input name="edate" id="edates" class="put3" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>
                        </td>
                    </tr>
                    <tr height="40">
                        <td  colspan="1"  align="center" class="txt02">
                            <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>
<script type="text/javascript">
		$(".rad").change(function() { 
		if(qb=="02"){
			$(".rad3").show();
			if(this.value=="1"){
				$(".rad1").show();
				$(".rad2").hide();
			}else{
				$(".rad1").hide();
				$(".rad2").show();
			}
		}else{
			if(this.value=="1"){
				$(".rad1").show();
				$(".rad2").hide();
			}else{
				$(".rad1").hide();
				$(".rad2").show();
			}
		}
		}); 
</script>
</body>
</html>