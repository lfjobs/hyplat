<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>有车车主加入</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_carjoin.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_carjoin.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var  basePath='<%=basePath%>';
		var sel = "${param.sel}";
	
		$(function() {

			if (sel == "1") {
				$(".yc").show();
			} else {
				$(".yc").hide();
			}
		});
	</script>
</head>
<body>
<form name="Infoform" id="Infoform"  method="post" enctype="multipart/form-data">
	<input type="submit" name="submit" style="display:none"/>
<div class="content">
	<section class="clearfix">
		<div>
			<p>
				<label for="name">姓名</label>
				<input type="text" name="staff.staffName" id="name" value="" placeholder="请输入姓名" onblur="nameCheck()"/>
			</p>
			<p>
				<label for="sex">性别</label>
				<input type="text" name="" id="sex" value="" placeholder="请输入性别" />
			</p>
			<p>
				<label for="idCard">身份证</label>
				<input type="text" name="staff.staffIdentityCard" id="idCard"  value="" placeholder="请输入身份证号" onblur="idCardCheck()" />
			</p>
			
			<p>
				<label for="driverId">驾驶证</label>
				<input type="text" name="driverId" id="driverId" value="" placeholder="驾驶证"  onblur="driverIdCheck()"/>
			</p>
			<p>
				<label for="phone">手机号</label>
				<input type="text" name="staff.reference" id="phone" value="" placeholder="手机号" onblur="phoneCheck()"/>
			</p>
			<p class="yc">
				<label for="DrivingId">行驶证</label>
				<input type="text" name="drivingId" id="drivingId" value="" placeholder="行驶证" onblur="drivingIdCheck()"/>
			</p>
			
		</div>
	</section>
	<div class="clearfix yc">
		<label for="carImg">请上传车的图片</label>
		<div>
			<input type="file" <%--name="sdfFile"--%> name="file" id="carImg" value="" accept="image/*" onchange="f_change(this);" >
			<img src="<%=basePath%>images/ea/collage/phl/tianjaiimg.png" id="imgSdf" />
		</div>
	</div>
	<section>
		<p class="yc">
			<label for="license_plate_number">号牌号码</label>
			<input type="text" name="carInformation.carNum" id="license_plate_number" value="" placeholder="号牌号码" onblur="license_plate_numberCheck()"/>
		</p>
		<div class="yc">
			<label>车辆类型</label>
			<div class="dropdown" id="carTypeDown">
				<input class="input_select" id="carType"  type="text" placeholder="请选择车型" readonly="readonly"/>
				<input type="hidden" name="carInformation.carType" value="">
				<%--<ul>
					<li>
						<a>小型轿车</a>
					</li>
					<li>
						<a>小型轿车</a>
					</li>
					<li>
						<a>小型轿车</a>
					</li>
					<li>
						<a>小型轿车</a>
					</li>
					<li>
						<a>小型汽车</a>
					</li>
				</ul>--%>
			</div>
		</div>
		<p class="yc">
			<label for="license_plate_number">品牌型号</label>
			<input type="text" name="carInformation.vehicleBrand"  value="" id="vehicleBrand" placeholder="品牌型号"  />
		</p>
		<p class="yc">
			<label for="license_plate_number">发动机号码</label>
			<input type="text" name="carInformation.engineNum"  value="" placeholder="发动机号码" id="engineNum"  onblur="engineNumCheck()" />
		</p>
	<%-- 	<div class="yc">
			<label>使用性质</label>
			<div class="dropdown" id="carUseDown">
				<input class="input_select" id="useProp"  type="text" placeholder="使用性质" readonly="readonly"/><span style="color:red;"></span>
				<input type="hidden" name="carInformation.useProp" value="">
				<ul>
					<li>
						<a >货车</a>
					</li>
				</ul>
			</div>
		</div> --%>
		<p class="yc">
			<label for="load">载重</label>
			<input type="text" name="carInformation.ratifyQuality" id="load" value="" placeholder="请输入载重重量" onblur="loadCheck()"/>kg
		</p>
		<p class="yc">
			<label for="specifications">长宽高</label>
			<input type="text" name="carInformation.outerSize" id="specifications" value="" placeholder="请输入长宽高1.6*1.3*1.1" onblur="specificationsCheck()"/>
		</p>
		<p class="yc">
			<label for="loadVolume">载重体积</label>
			<input type="text" name="carInformation.loadVolume" id="loadVolume" value="" placeholder="请输入载重体积" onblur="loadVolumeCheck()"/>m³
		</p>
		<div>
			<label>批发市场</label>
			<div class="dropdown" id="marketDown">
				<input class="input_select" id="selMarket" type="text" placeholder="请选择市场" readonly="readonly"/>
				<input type="hidden" name="marketId" value="">
					<%--<ul>
                        <li>
                            <a href="#">市场01</a>
                        </li>
                        <li>
                            <a href="#">市场02</a>
                        </li>
                        <li>
                            <a href="#">市场03</a>
                        </li>
                        <li>
                            <a href="#">市场04</a>
                        </li>
                        <li>
                            <a href="#">市场05</a>
                        </li>
				</ul>--%>
			</div>
		</div>
	</section>
	<input type="button" name="joinIn" id="joinIn" value="确定" />
	<%--</form>--%>
</div>
</form>
<div id="prompt" style="width: 100%; display: none;z-index: 1001">
	<center>
		<div>
			<span style="position: relative; top: 19.8%;"></span>
		</div>
	</center>
</div>
</body>
</html>
