<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<title>完善资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">

<script src="<%=basePath%>/js/jquery-2.0.0.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_data.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style02.css"/>
<script src="<%=basePath%>js/ea/websitemall/card/android/jquery.Jcrop.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/jquery.Jcrop.css"/>
<script src="<%=basePath%>js/ea/websitemall/card/android/jquery.time.selector.js" type="text/javascript"></script>

<style type="text/css">
.demo{width:300px;margin:40px auto 0 auto;}
.demo .lie{margin:0 0 20px 0;}
#prompt div{
	width: 70%;
	background: rgba(0,0,0, 0.5);
}
.position_body hr{
	border:0px;
	border-bottom:1px solid #BABABA;
}

</style>
<script type="text/javascript">
var basePath="<%=basePath%>";
var editType="${editType}";
var select=0,positionSel=0;
var backurl="<%=backurl%>";
var boo=true;
</script>
</head>
<body>
<form method="post"  id="form" name="form" enctype="multipart/form-data" style="width: 100%;height: 100%;">
<input type="submit" name="submit"  id="submit" style="display: none;">
<iframe name="hidden"  style="display: none;"></iframe>
<div id="datePlugin"></div>
<div class="main">
	<div class="top">
        	<ul>
            	<li class="wfj12_007_top"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png"/></a></li>
            	<li>完善个人资料</li>
            	<li class="wfj12_007_bottom display">保存</li>
                <div class="clear"></div>
            </ul>
     </div>
     
        <div id="prompt" style="width: 100%;display: none;">
        <center>
        	<div>
        		<span style="position: relative;top: 19.8%;"></span>
        	</div>
        	</center>
        </div>
     <div id="main_hidden">
     	<div id="content_width">
             <div class="content">
             <input type="hidden" id="wheelbase" name="wheelbases">
                    <section class="one">
                        <div class="image" id="exhibition">
                        		<img src="<%=basePath%>${staff.headimage==null?'images/ea/websitemall/card/no_pictures2.png':staff.headimage}" />
                        		<input type="hidden" id="headimage" name="original" value="${staff.headimage}">		
                        </div>
                        <div class="one_con">
                            <ul>
                                <li>
                                		<input id="name" name="staffVo.cstaff.staffName"  type="text" placeholder="--姓名--" required="required" value="${staff.staffName==null?'':staff.staffName}" />
                                		<div class="clear"></div><input type="hidden" id="staffId" name="staffVo.cstaff.staffID" value="${staff.staffID}"></li>
                                <li><input id="phone" name="staffVo.cstaff.reference" type="tel" placeholder="--手机号--" required="required"  value="${staff.reference==null?'':staff.reference}" /><div class="clear"></div></li>
                            </ul>
                        </div>
                        <div class="clear"></div>
                    </section>
                    <section class="two">
                        <ul>
                            <li><label>行业分类</label>
                            		<input id="trade" name="staffVo.cstaff.industryType" type="text" value="${staff.industryType==null?'':staff.industryType}"  readonly="readonly"/><div class="clear"></div></li>
                            <li><label for="company">公司名称</label>
                            		<input id="company" name="staffVo.cstaff.whereCompany" type="text"  value="${staff.whereCompany==null?'':staff.whereCompany}"/><div class="clear"></div></li>
                            <li><label for="job">职位工种</label>
                            		<input id="job" name="staffVo.cstaff.position" type="text" value="${staff.position==null?'':staff.position}"  readonly="readonly"/><div class="clear"></div></li>
                        </ul>
                    </section>
                    
             </div>
             <div class="bottom">
                <div class="addmore">添加更多项目</div>
             </div>
      	</div>
     </div>
     
     
</div>


<div id="box">
	<div class="cover"></div>
    <div class="addmore_box">
    	<h3>添加更多项目</h3>
        <div class="hidden">
        	<ul>
            	<li class="staffVo.cstaff.birthday" name="birthday"  id="${staff.birthday==null?'':staff.birthday}" >生日</li>
                <li class="staffVo.cstaff.weixin" name="weixin"  id="${staff.weixin==null?'':staff.weixin}" >微信</li>
                <li class="staffVo.cstaff.referenceCode" name="referenceCode"  id="${staff.referenceCode==null?'':staff.referenceCode}">QQ</li>
                <li class="staffVo.cstaff.referenceOrganization" name="referenceOrganization" id="${staff.referenceOrganization==null?'':staff.referenceOrganization}" >邮箱</li>
                <li class="address"  id="${staff.provinceAddress==null?'':staff.provinceAddress}">地址</li>
                <li class="staffVo.cstaff.localAreaValue" name="localAreaValue"   id="${staff.localAreaValue==null?'':staff.localAreaValue}" >详细地址</li>
                 <li class="staffVo.cstaff.educationValue" name="educationValue"   id="${staff.educationValue==null?'':staff.educationValue}" >学历</li>
            </ul>
        </div>
        <h3 class="cancel">取消</h3>
    </div>
</div>

	<div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="intercept">
		<div style="width: 80%;height: 40%;background-color: #FFFFFF;position: relative;top: 15%;left: 10%;" id="interceptDiv">
			<div id="operation" style="position: relative;top: -14%;text-align: right;height:0px;">
				<span id="determine" class="determine" style="display:-moz-inline-box;display:inline-block;position:relative;right:10%">
					<img src="<%=basePath%>images/ea/websitemall/card/choice_01.png"> 
				</span>
				<span id="cancel" class="determine" >
					<img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_02.png"> 
				</span>
			</div>
			<img id="interceptImg" >
		</div>
	</div>             
	
	
		<div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: #fff;display: none;" id="position">
			<div style="position: relative;top: 3%;left: 5%;width: 90%;height:97%;overflow: hidden;" class="position_title">
				<div class="position_body">
				</div>
			</div>
		</div>
</form>
<script type="text/javascript">
    	
    $(document).ready(function(e){
			$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;")
			$(".content").find("li").find("input").attr("style","font-size:"+$(window).height()*0.03+"px;");
			
				//头部
            $(".top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".top").find("li").attr("style","width:15%;font-size:"+$(window).height()*0.03+"px;");
            $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
			$(".top").find("li").find("button").attr("style","font-size:"+$(window).height()*0.03+"px;");
			$(".top").find("li").eq(0).attr("style","width:15%;font-size:"+$(window).height()*0.03+"px;margin-top:-"+$(window).height()*0.004+"px;");
            $(".top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.03+"px;");
			 
			$(".content ul").find("li").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;border-radius:"+$(window).height()*0.01+"px; font-size:"+$(window).height()*0.03+"px;")
			$(".one .image").attr("style","height:"+($(".one .image").width())+"px;overflow:hidden;margin:"+$(window).height()*0.0031+"px 0;");
			$(".one .image img").attr("style","height:"+$(".one .image img").height()+"px");
			
			$(".bottom").attr("style","margin-top:"+$(window).height()*0.05+"px;");
			$(".addmore").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px; border-radius:"+$(window).height()*0.015+"px; margin-bottom:"+$(window).height()*0.03+"px;font-size:"+$(window).height()*0.03+"px;");
			$(".addmore_box h3").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;font-size:"+$(window).height()*0.03+"px;")
			$(".addmore_box ul").find("li").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;font-size:"+$(window).height()*0.03+"px;")
			$(".addmore_box .hidden").attr("style","height:"+($(window).height()*0.6-$(".addmore_box h3").height()*2-5)+"px;")
if(editType!="00"){
				$(".display").css("display","none");
				var local=document.getElementsByName("localAreaValue");
				local[0].style.display="none"; 
				$("input").attr("readonly","readonly");				
	   	 	}
var o = {cancel : function(){
		$("#box").css("display","none")
		}
	}
	
    $(".addmore").click(function(){
        $("#box").css("display","block")
        
    })
	//取消选择
    $("#box .cover").click(o.cancel)
	$("#box .cancel").click(o.cancel)
	
	$("#box ul").find("li").click(function(){
		
		$("#box").css("display","none")
		$(this).remove();
		
		var name = this.className
		//alert (name)
		var wenzi = $(this).text()
		var val=this.id;
		var id=$(this).attr("name");
		if (name == "address") {
			if(editType=="00"){
				$(".two ul:last-child").append("<li><label>省</label><select name='location_p' id='location_p'></select><div class='clear'></div></li><li><label>市</label><select  name='location_c' id='location_c'></select><div class='clear'></div></li><li><label>区</label><select  name='location_a' id='location_a'></select><div class='clear'></div></li>")
				if(val!=""&&val!=null){
					var strs=val.split("/");
					new PCAS('location_p', 'location_c', 'location_a', strs[0],strs[1], strs[2]);
				}else{
					new PCAS('location_p', 'location_c', 'location_a', '', '', '');
				}
			}else{
				var strs=val.split("/");
				var li="<li><label>省</label><input  type='text'  value='"+strs[0]+"' "+(editType=="00"?"":"readonly='readonly'")+"/><div class='clear'></div></li>"+
							 "<li><label>市</label><input type='text'  value='"+strs[1]+"' "+(editType=="00"?"":"readonly='readonly'")+"/><div class='clear'></div></li>"+
							 "<li><label>区</label><input  type='text'  value='"+strs[2]+"' "+(editType=="00"?"":"readonly='readonly'")+"/><div class='clear'></div></li>";
				$(".two ul:last-child").append(li);
				
			}
		} else {
			$(".two ul:last-child").append("<li><label>"+wenzi+"</label><input id="+id+"  type='text' name='"+name+"' value='"+val+"' "+(editType=="00"?"":"readonly='readonly'")+(id=="birthday"&&editType=="00"?"class='time'":"")+"/><div class='clear'></div></li>")
	        $("#beginTime").date();

		}
	   
		$(".content ul").find("li").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;border-radius:"+$(window).height()*0.01+"px; font-size:"+$(window).height()*0.03+"px;")
		$(".content").find("li").find("input").attr("style","font-size:"+$(window).height()*0.03+"px;");
		
		})
	})
</script>

<script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/date.js" ></script>
<script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/iscroll.js" ></script>

 <script src="<%=basePath%>js/ea/websitemall/card/android/region_select.js"></script>


</body>
</html>
