<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>客户管理列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/customerData/custData_list.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link href="<%=basePath%>css/ea/register.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript">
			var cashierBillsID="";
			var search="${search}";
			var basePath="<%=basePath%>";
			var pNumber=${pageNumber};
			var token = 0;
			var sdate="${sdate}";
			var edate="${edate}";
			var companyKey="";
			var notoken = 0; 
		    var comType="${comType}";
			var retoken=0;
			function importCP(attachSearchTable,cID,name,addr,ble,tel,url){ //打开页面
				 $("#cID",$("#compJqm")).attr("value",cID);
				 $("#checkform",$("#compJqm")).attr("value",attachSearchTable);
				 $("#name",$("#compJqm")).attr("value",name);
				 $("#addr",$("#compJqm")).attr("value",addr);
				 $("#ble",$("#compJqm")).attr("value",ble);
				 $("#tel",$("#compJqm")).attr("value",tel);
				 
			     $("#daoRu").attr("src",basePath+url);
			     $("#compJqm").jqmShow();
			}
			
			$(document).ready(function() {//销售单FORM
			$("#DaoRuFan").click(function(){// 返回
		       $("#compJqm").jqmHide();
			}); 
			
			$("#DaoRuFanqd").click(function(){// 选择确定
				var cID =$("#cID",$("#compJqm")).attr("value");
				var checkform =$("#checkform",$("#compJqm")).attr("value");
				var name = $("#name",$("#compJqm")).attr("value");
				var addr = $("#addr",$("#compJqm")).attr("value");
				var ble = $("#ble",$("#compJqm")).attr("value");
				var tel = $("#tel",$("#compJqm")).attr("value");
				
				var cID = window.frames["daoRu"].opertionID;
				if(cID == ""){
					alert("请选择")
					return;
				}
				var name = window.frames["daoRu"].$('tr#'+cID).find("span#"+name).text();
				var addr = window.frames["daoRu"].$('tr#'+cID).find("span#"+addr).text();
				var ble = window.frames["daoRu"].$('tr#'+cID).find("span#"+ble).text();
				var tel = window.frames["daoRu"].$('tr#'+cID).find("span#"+tel).text();
				
				if(cID != ""){
					$("#"+cID,$("#"+checkform)).attr("value",cID).trigger("blur");
					$("#cID").val(cID);
				}
				if(name != "")
					$("#name").val(name).attr("readonly",true);
				if(addr != "")
					$("#addr").val(addr).attr("readonly",true);
				if(ble != "")
					$("#ble").val(ble).attr("readonly",true);
				if(tel != "")
					$("#tel").val(tel).attr("readonly",true);
				
				 $("#daoRu").attr("src","");
		         $("#compJqm").jqmHide();
		   });
		});
		</script>
		
		<script type="text/javascript">
			var pass;
			document.onkeydown = function(evt){//捕捉回车   
				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
				var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
				if (key == 13) { //判断是否是回车事件。
					save();
					$("#jqModelSearch").jqmHide();
				}
			}
			function save()
			{
				pass = '1';
				$("form :input").trigger("blur");
				$(".validate").trigger("blur");
				if($("form .error").length)
				{ 
					return;
				}     
				$('#Loginform').attr("target","hidden").attr('action', basePath+"custregister.jspa");
				document.Loginform.submit.click();
				$("#jqModelSearch").jqmHide();
				token=2;
			}
			function close()
			{
				$("#jqModelSearch").jqmHide();
				window.location.reload();
			}
			setTimeout(close,300000);
		</script>
		<style type="text/css">
		.yname {
		height: 14px;
		}
		</style>
	</head>
	<body>
		<form name="CashierBillsform" id="CashierBillsform">
			<input type="submit" name="submit" style="display: none" />
			<input name="cashierBills.cashierBillsID" id="cashierBillsID"
				style="display: none" />
			<s:token />
		</form>
		<form name="Companyform" id="Companyform">
			<input type="submit" name="submit" style="display: none" />
			<input name="company.companyKey" id="companyKey"
				style="display: none" />
			<s:token />
		</form>
		<div style="z-index: 90">
			<table class="flexme11">
				<thead>
					<tr>
						<th align="center" width="30">
							选择
						</th>
						<th align="center" width="30">
							序号
						</th>
						<th align="center" width="200">
							父公司名称
						</th>
						<th align="center" width="200">
							组织机构名
						</th>
						<th align="center" width="200">
							公司名称
						</th>
						<th align="center" width="250">
							公司地址
						</th>
						<th align="center" width="80">
							负责人
						</th>
						<th align="center" width="100">
							公司电话
						</th>
						<th align="center" width="180">
							邮箱
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var='arr' items="${pageForm.list}" varStatus="index">
						<tr id="${arr[7]}">
							<td>
								<input type="radio" name="com"  class="JQuerypersonvalue"/>
							</td>
							<td>
								<span>${index.index+1}</span>
							</td>
							<td>
								<span id="pcompanyname">${arr[0]}</span>
							</td>
							<td>
								<span id="companyidentifier">${arr[1]}</span>
							</td>
							<td>
								<span id="companyname">${arr[2]}</span>
							</td>
							<td>
								<span id="companyaddress">${arr[3]}</span>
							</td>
							<td>
								<span id="companyphone">${arr[4]}</span>
							</td>
							<td>
								<span id="companymanager">${arr[5]}</span>
							</td>
							<td>
								<span id="companyemail">${arr[6]}</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/custdata/ea_getCustomerDataList.jspa?pageNumber=${pageNumber}&comType=${comType}">
			</c:param>
		</c:import>
		
		
		<div class="jqmWindow" id="jqModelSearch"
			style="width: 740px; height: 560px; right: 30%; top: 0px; z-index: 120;position:absolute;">
		
			<div class="roundedCorners" >
				<div class="titlenav1">
					<span class="td21">账号注册</span>
					<span class="td22">(以下<span class="txt05"> * </span>为必填项)</span>
				</div>
				<form name="Loginform" id="Loginform" method="post">
					<div class="conten" style="height: 105px">
						<input type="submit" name="submit" style="display: none"/>
						<ul>
							<li>
								<span class="contenspan"><span class="txt05"> * </span>组织机构名：</span>
								<input type="text" name="company.companyIdentifier"
									class="yname company" type="hidden" />
							</li>
							<li>
								<span class="contenspan"><span class="txt05"> * </span>用户名：</span>sa
							</li>
							<li>
								<span class="contenspan"><span class="txt05"> * </span>密码：</span>
								<input type="password" name="account.accountPassword"
									class="yname password" />
							</li>
							<li>
								<span class="contenspan"><span class="txt05"> * </span>确认密码：</span>
								<input type="password" name="ddd" class="yname password1" />
							</li>
						</ul>
					</div>
					<div class="titlenav1">
						<span class="td21">基本资料注册</span><span class="td22">(以下各项为必填项)</span>
					</div>
					<div class="conten" style="height: 330px">
						<ul>
							<li>
								<span class="contenspan"> <span class="txt05">* </span>公司名称：</span>
								<span>
								<input type="hidden" id="cID" readonly="readonly" />
								<input type="text" id="name" name="company.companyName" class="yname put1" />
								
								<a href="#" onclick="importCP('table3',cID,'name','addr','ble','tel','ea/custdata/ea_getListContactCompanyJQM.jspa')">选择</a>
								</span>
							</li>
							<li>
								<span class="contenspan"> <span class="txt05">* </span>公司类型：</span>
								<s:select list="#{'11':'未付费注册客户','12':'已付费注册客户'}" name="company.companyType"
									theme="simple">
								</s:select>
							</li>
							<li>
								<span class="contenspan"> <span class="txt05">* </span>行业类别：</span>
								<%--<input type='text' id='type' name='company.industryType'  class="yname put3"  onfocus='blue()'/> --%>
								<s:select list="#{'汽车驾校园区':'汽车驾校园区','农副产品园区':'农副产品园区','物流仓储园区':'物流仓储园区','自燃能源园区':'自燃能源园区','健康保健园区':'健康保健园区',
								'教育培训园区':'教育培训园区','互联科技园区':'互联科技园区','房产装饰园区':'房产装饰园区'}" name="company.industryType"
									theme="simple">
								</s:select>
							</li>
							<li>
								<span class="contenspan"> <span class="txt05">* </span>公司地址：</span>
								<input type="text" id="addr" name="companyDetail.companyAddress"
									 class="yname put2" />
							</li>
							<li>
								<span class="contenspan"><span class="txt05">* </span>负责人：</span>
								<input type="text" id="ble" name="companyDetail.companyManager"
									class="yname put3" />
							</li>
							<li>
								<span class="contenspan"><span class="txt05"> * </span>电子邮箱：</span>
								<input type="text" name="companyDetail.companyEmail"
									class="yname email put3" />
							</li>
							<li>
								<span class="contenspan"><span class="txt05">* </span>电话:</span>
								<input type="text" id="tel" name="companyDetail.companyPhone"
									class="yname put3" />
							</li>
							<li>
								<span class="contenspan"> <span class="txt05">* </span>添加到联盟网：</span>
								<s:select list="#{'0':'不添加','1':'添加'}" name="company.isst"
									theme="simple">
								</s:select>
							</li>
							<li>
								<span class="contenspan"> <span class="txt05">* </span>添加到微信：</span>
								<s:select list="#{'00':'不添加','01':'添加'}" name="company.showwechat"
									theme="simple">
								</s:select>
							</li>
							<li>
								<span class="contenspan">图片：</span>
								<img border="0" name="validateImage"
									onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abc='+Math.random()"
									src="<%=basePath%>page/ea/security_code.jsp" />
							</li>
							<li>
								<span class="contenspan">输入验证码：</span>
								<input type="text" class="yname validate" />
								<s:token />
							</li>
						</ul>
						<input Type="hidden"   name='company.comType' value="${comType}"/>
					</div>
						<div align="center">
					<input type="button" id="saves" class="button" value="提 交" />
					<input type="button" id="closes" class="button01" value="关 闭" />
				</div>
					
				</form>
			
			</div>
		</div>
		
		<div class="jqmWindow" style="width: 400px;right: 50%;;top: 20%" id="jqModelSearch1">
          <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            
            <div class="drag">
                    查询客户资料
                    <div class="close">
                    </div>
            </div>
             <table width="396" id="cataffSearchTable">
              <tr>
                        <td width="123" align="right">
                          组织机构名：       </td>
						<td width="261">
							<input name="company.companyIdentifier" />
                        </td>
              </tr>
              <tr>
                        <td width="123" align="right">
                           公司名称：        </td>
						<td width="261">
							<input name="company.companyName" />
                        </td>
              </tr>
             </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
              <input name="comType" type="hidden" value="${comType}"/>
            </div>
            </form>
        </div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<div id="compJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="cID" />
				<input style="display: none;" id="name" />
				<input style="display: none;" id="addr" />
				<input style="display: none;" id="ble" />
				<input style="display: none;" id="tel" />
				
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;margin-left:400px;height:25px;width:60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;margin-left:40px;height:25px;width:60px"" />
			</div>
		</div>
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"  id="JQueryaddress">
			<form name="SearchForm1" id="SearchForm1" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					请选择行业类别
					<div class="close">
					</div>
				</div>
				<table width="400" id="cataffSearchTable">										
						<tr class="trheight">						
						<td colspan="2" class="JQueryaddress">
							<select name="addressProvince" id="province" number='0'
								style="width: 150px;">
							</select>
							<select name="addressCity" id="city" number='1'
								style="width: 150px;">
							</select>
							
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch1" onclick="getAddress()"
						value="确定" />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
<!--
 function testit(){ 	
var dianhua=$("#cstaffForm").find("input#companyTel").attr("value");
var filter= (/(^(\d{3,4}-)?\d{7,8}(-\d{1,3})?$)/.test(dianhua) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(dianhua) ) ; 
if(("0000000"==(dianhua))||("00000000"==(dianhua))||(filter==false)){
alert("请输入正确的电话号码");
};  
}
 function testits(){ 	
var shouji=$("#cstaffForm").find("input#responsibleTel").attr("value");
var filters= (/(^(\d{4}-)?\d{7,8}(-\d{1,3})?$)/.test(shouji) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(shouji) ) ; 
if(("0000000"==(shouji))||("00000000"==(shouji))||(filters==false)){
alert("请输入正确的电话号码");
};  
}
//-->
</script>

<script type="text/javascript">

  function getAddress(){
		
		if($("#province ").find("option:selected").text()=="--请选择--"){
			$("#province ").text("");
			}
		 var province=$("#province ").find("option:selected").text();
		 if($("#city ").find("option:selected").text()=="--请选择--"){
			 $("#city ").text("");
			 }
		 var city=$("#city ").find("option:selected").text();
		 if($("#county ").find("option:selected").text()=="--请选择--"){
			 $("#county ").text("");
			 }
	
		 if(city!=""){
			 address=city;
		 }else{
			 address=province;
			 }
		$("#JQueryaddress").jqmHide();
		$("#type").val(address);
	  }
 
</script>  
</html>
