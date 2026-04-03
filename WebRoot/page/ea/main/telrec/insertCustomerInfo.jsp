<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head>
	    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title>客户信息登记</title>		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

	</head>

	<body>
		<h3>
			客户信息登记
		</h3>
		<hr/>

		<form action="webInsertCustomerInfo.do" id="form" method="post">
			<table>
				<tr>
					<td>
						客户类型
					</td>
					<td>
						<select name="customerInfo.customer_type">
							<option value="1">
								个人
							</option>
							<option value="2">
								单位
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						客户名称
					</td>
					<td>
						<input type="text" name="customerInfo.customer_name" id="customer_name" value="" />
					</td>

					<td>
						联系电话
					</td>
					<td>
						<input type="text" name="customerInfo.customer_tel" id="customer_tel" value=""/>
					</td>
				</tr>
				<tr>
					<td>
						移动电话
					</td>
					<td>
						<input type="text" name="customerInfo.customer_mobile" id="customer_mobile" value=""/>
					</td>
					<td>
						家庭电话
					</td>
					<td>
						<input type="text" name="customerInfo.customer_hometel" id="customer_hometel" value=""/>
					</td>
				</tr>

				<tr>
					<td>
						传真
					</td>
					<td>
						<input type="text" name="customerInfo.customer_fax" id="customer_fax" value=""/>
					</td>
					<td>
						电子邮件
					</td>
					<td>
						<input type="text" name="customerInfo.customer_email" id="customer_email" value=""/>
					</td>
				</tr>
				<tr>
					<td>
						生日
					</td>
					<td>
						<input type="text" name="customerInfo.customer_birthday" id="customer_birthday" value="" onfocus="date(this)"/>
					</td>
					<td>
						单位名称
					</td>
					<td>
						<input type="text" name="customerInfo.customer_unit" id="customer_unit" value=""/>
					</td>
				</tr>
				<tr>
					<td>
						职务
					</td>
					<td>
						<input type="text" name="customerInfo.customer_title" id="customer_title" value=""/>
					</td>
					<td>
						地址
					</td>
					<td>
						<input type="text" name="customerInfo.customer_address" value="" id="customer_address"/>
					</td>
				</tr>
				<tr>
					<td>
						邮编
					</td>
					<td>
						<input type="text" name="customerInfo.customer_postcode" value="" id="customer_postcode" />
					</td>
				</tr>
				<tr>
					<td>
						备注
					</td>
					<td>
						<textarea rows="5" cols="50" name="customerInfo.memo" id="memo"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="id" name="customerInfo.id" />
						<input type="hidden" id="rela_companyid" name="customerInfo.rela_companyid" />

					</td>
					<td>
						<input type="button" id="submitFormButton" value=" 提 交 " onclick="submitForm()" />
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		    var customerid="<%=request.getParameter("customerid")%>";		
		    
			if(customerid!="null"){
				$.ajax({
					url:'<%=basePath%>telrecjson/webQueryCustomerInfoById.do?id='+customerid,
					type:'get',
					dataType:"json",
					success:function(obj){
						setValue(obj);
					},
					error:function(obj){
						alert("error");
					}
				});
			}
			
			function setValue(obj){			
				$("#id").val(obj.id);
				$("#customer_type").val(obj.customer_type);
				$("#customer_name").val(obj.customer_name);
				$("#customer_tel").val(obj.customer_tel);
				$("#customer_mobile").val(obj.customer_mobile);
				$("#customer_hometel").val(obj.customer_hometel);
				$("#customer_fax").val(obj.customer_fax);
				$("#customer_email").val(obj.customer_email);
				$("#customer_birthday").val(obj.customer_birthday);
				$("#customer_unit").val(obj.customer_unit);
				$("#customer_title").val(obj.customer_title);
				$("#customer_address").val(obj.customer_address);
				$("#rela_companyid").val(obj.rela_companyid);
				$("#memo").val(obj.memo);
		    }
		    
		    function submitForm(){
				alert($("#rela_companyid").val());
				if(customerid!="null"){
						alert("update");
						document.getElementById("form").action="webUpdateCustomerInfo.do";
				}else{
						alert("insert");
						document.getElementById("form").action="webInsertCustomerInfo.do";
				}
				document.getElementById("form").submit();
			}
			 
			/*	$("from input").blur(function(){
				var $parent = $(this).parent();
				$parent.find(".addd").remove();
				$parent.find(".add").remove();
				if ($(this).is('#username2'))// 登录账号
				{
					if (this.value == "") {
						var error = "<img src='../images/c.gif'></img><a class='display'>不能为空</a>";
						$parent.append('<span class="addd">' + error + '</span>');
					} else {
						$parent
								.append("<span class='add'><img src='../images/d.gif'></img></span>");
					}
				}
			});*/
			</script>		
	</body>
</html>
