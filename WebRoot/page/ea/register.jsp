<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>注册</title>
<link href="<%=basePath%>css/ea/register.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
 

<style type="text/css">
<!--
.roundedCorners{
width:935px;
height:540px;
padding: 10px;
background-color: #FFF;
border:1px solid #83A2C6;
margin:0px auto;
margin-top:5px;
 
/* Do rounding (native in Safari, Firefox and Chrome) */
-webkit-border-radius: 6px;
-moz-border-radius: 6px;
 
}

.conten ul li {

    height:16px;

}
</style>


<script type="text/javascript">
  var basePath="<%=basePath%>";
  var pass;
  document.onkeydown = function(evt){//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
       save();
    }
}
  function save()
  {
		
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
		$("#type").val(address);
      pass = '1';
      $("form :input").trigger("blur");
      $(".validate").trigger("blur");
      if($("form .error").length)
      { 
          return;
      }  
      
     $('#Loginform').attr('action', "<%=basePath%>register.jspa");
    document.Loginform.submit.click();
    
  }
  
  function toReturn(){
		 window.parent.document.getElementById("rightFrame").src="<%=basePath%>/ea/comregist/ea_getCompanyRegistList.jspa"
  }
</script>
</head>
<body>
<div class="main">
	<div class="conter">
        <div class="logo_zc"></div>
        <div class="logo_right"></div>
        <div class="logo_rightwz">注册</div>
    </div>
    <div class="roundedCorners">
    	<div class="titlenav1"><span class="td21">账号注册</span>
        <span class="td22">(以下<span class="txt05"> * </span>为必填项)</span></div>
        	 <form name="Loginform" id="Loginform"  method="post" >
        <div class="conten"><input type="submit" name="submit" style="display:none"/>
          <ul>
              <li>
              	<span class="contenspan"><span class="txt05"> * </span>组织机构名：</span>
                <input type="text" name="company.companyIdentifier"  class="yname company" />            
              </li>
              <li>
              	<span class="contenspan"><span class="txt05"> * </span>用户名：</span>sa
              </li>
               <li>
              	<span class="contenspan"><span class="txt05"> * </span>密码：</span>
                <input type="password" name="account.accountPassword"  class="yname  password" />            
              </li>
               <li>
              	<span class="contenspan"><span class="txt05"> * </span>确认密码：</span>
                <input type="password" name="ddd"  class="yname password1" />            
              </li>
           </ul>
           </div>
        <div class="titlenav1"><span class="td21">基本资料注册</span><span class="td22">(以下各项为必填项)</span></div>
        <div class="conten">
          <ul>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>公司名称：</span>
                <input type="text" name="company.companyName"  class="yname put1" />
              </li>
               <li>
              	<span class="contenspan"> <span class="txt05">* </span>公司类型：</span>
              	  企业<input name="company.companyType" style="display:none" value="00" />
              </li>
              <li>
				<span class="contenspan"> <span class="txt05">* </span>行业类别：</span>
					
					<div class="jqmWindow"   id="JQueryaddress"  class="yname put3" >
					<form name="SearchForm1" id="SearchForm1" method="post">
						<table id="cataffSearchTable">										
								<tr class="trheight">						
								<td colspan="2" class="JQueryaddress">
									<select name="addressProvince" id="province" number='0'
										style="width: 120px;">
									</select>
									<select name="addressCity" id="city" number='1'
										style="width: 120px;">
									</select>	
									<input type='hidden' id='type'  name='company.industryType' class="yname1 put31"/>		
								</td>
							</tr>
						</table>				
					</form>
				</div>
			  </li>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>公司地址：</span>
                <input type="text" name="companyDetail.companyAddress"  class="yname put2" />
              </li>
                <li>
                	<span class="contenspan"><span class="txt05">* </span>负责人：</span>
                  	<input type="text" name="companyDetail.companyManager" class="yname put3" />
                </li>
                <li>
                	<span class="contenspan"><span class="txt05"> * </span>电子邮箱：</span>
                    <input type="text" name="companyDetail.companyEmail"  class="yname email put3" />
                </li>
                <li>
                	<span class="contenspan"><span class="txt05">* </span>电话:</span>
                    <input type="text" name="companyDetail.companyPhone"  class="yname phone" />
                </li>
                <li>
					<span class="contenspan"> <span class="txt05">* </span>添加到微信：</span>
					<s:select list="#{'00':'不添加','01':'添加'}" name="company.showwechat"
						theme="simple">
					</s:select>
				</li>
				<li>
               	  <span class="contenspan">图片：</span>
                   <img border="0" name="validateImage" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abc='+Math.random()" src="<%=basePath%>page/ea/security_code.jsp"/>          
                </li>
                <li>
               	  <span class="contenspan">输入验证码 ：</span>
                  <input type="text"  class="yname validate" />
                  <s:token/>
                </li>
              </ul>
             </div>
		    </form>
		        <div class="conten1">
		          <ul>
		            <li>
		              <input  type="button" onclick="save()" class="button" value="提 交" />
		              <input  type="button"  onclick="toReturn()" class="button01" value="返 回" />
		            </li>
		          </ul>
		   </div>
  </div>
    </div>
   
     <div class="footer">北京天太世统科技有限公司 版权所有   服务电话：010-64167113</div>
    	
</body>
<script type="text/javascript">
var notoken = 0; 
var retoken=0;
  function getAddress(){

	  }
  function blue1(){
		$("#JQueryaddress").show();
	}
  $(document).ready(function() {
  	var PID="";// 当点新曾时,上一级被选中项的id
  	var rovince="";// 被改变的那个的id
  	var districtPID;
  	function LiuZhongYaoDeShaGuaDiZhi() {
  		// 非空验证还原
  		if (retoken)
  			return;
  		retoken = 1;
  		
  		$td = $("td.JQueryaddress");
  		$td.children('select').empty();
  		$select = "<option selected='selected'>--请选择--</option>";
  		$("#province", $td).append($select);
  		$td = $("td.JQueryaddress");
  		$('td.JQueryaddress input[name=changes]').show();
  		var url = basePath
  				+ "/ea/contactcompany/sajax_n_ea_getCDistricts.jspa?districtPID=scode20110106hfjes5ucxp0000000003"
  				+ "&date1=" + new Date();
  		$.ajax({
  					url : url,
  					type : "get",
  					async : true,
  					dataType : "json",
  					success : function cbf(data) {
  						var member = eval("(" + data + ")");
  						var nologin = member.nologin;
  						if (nologin) {
  							document.location.href = basePath
  									+ "page/ea/not_login.jsp";
  						}
  						var distinctlist = member.distinctlist;
  						for (var i = 0; i < distinctlist.length; i++) {
  							$op = $("<option />");
  							$op.attr("value", distinctlist[i].codeID)
  									.attr("id", distinctlist[i].codeID)
  									.text(distinctlist[i].codeDesc+""+distinctlist[i].codeValue);
  							$("#province", $td).append($op);
  						}
  						retoken = 0;
  					},
  					error : function cbf(data) {
  						retoken = 0;
  						alert("数据获取失败！");
  					}
  				});
  		return;
  	}
  		
  	$('td.JQueryaddress select[number]').change(function() {

  		if (retoken)
  			return;
  		retoken = 1;
  		var province = this.id;
  		var number = $(this).attr("number");
  		$td = $("td.JQueryaddress");
  		rovince = "#" + province;
  		$('#newdistrict', $td).hide();
  		$td.children('select:gt(' + number + ')').empty();
  		$td.children('select:gt(' + number + ')').show();
  		var D = $(rovince, $td).children("option:selected").attr("class");
  		if (D == 'add') {
  			PID = $(rovince, $td).children("option:selected").val();
  			$('#districtNames').attr("title", number).attr("value", "");
  			$("#jqModel").jqmHide();
  			$("#newdistrict").jqmShow();
  			retoken = 0;
  			return;
  		}
  		$($td).children('select:gt(' + number + ')').attr("disabled", false);
  		districtPID = $(rovince, $td).children("option:selected").val();
  		if (districtPID == '--请选择--') {
  			if (number != "0") {
  				var nu = parseInt(number) - 1;
  				districtPID = $("select[number=" + nu + "]", $td).val();
  			} else {
  				districtPID = "";
  			}
  			$td.find('input#address').val(districtPID);
  			retoken = 0;
  			return;
  		}
  		var url = basePath
  				+ "ea/contactcompany/sajax_n_ea_getCDistricts.jspa?districtPID="
  				+ encodeURI(districtPID) + "&date3=" + new Date();
  		$.ajax({
  			url : url,
  			type : "get",
  			async : true,
  			dataType : "json",
  			success : function cbf(data) {
  				var member = eval("(" + data + ")");
  				var nologin = member.nologin;
  				if (nologin) {
  					document.location.href = basePath + "page/ea/not_login.jsp";
  				}
  				var distinctlist = member.distinctlist;
  				$select = "<option selected='selected'>--请选择--</option>";
  				$(rovince, $td).next().append($select);
  				if (distinctlist.length) {
  					for (var i = 0; i < distinctlist.length; i++) {
  						$op = $("<option/>");
  						$op.attr("value", distinctlist[i].codeID).attr(
  								"id", distinctlist[i].codeID)
  								.text(distinctlist[i].codeDesc+""+distinctlist[i].codeValue);
  						$(rovince, $td).next().append($op);
  					}
  				}
  				
  				$td.find('input#address').val(districtPID);
  				retoken = 0;
  			},
  			error : function cbf(data) {
  				retoken = 0;
  				alert("数据获取失败！");

  			}
  		});
  	});	
  	LiuZhongYaoDeShaGuaDiZhi();
  });
   
</script>  
</html>