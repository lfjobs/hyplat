<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML>
<html>
<head lang="en">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">


<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"
	charset="utf-8"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/human/attence/base.css" />
<link rel="stylesheet/less" type="text/css"
	href="<%=basePath%>css/ea/human/attence/signSuc.less" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/human/attence/signSuc.css" />
<script src="<%=basePath%>js/font-size.js" type="text/javascript"
	charset="utf-8"></script>

<script type="text/javascript">
    	var basePath="<%=basePath%>";
        var posNum  = "";
    	


      $(function(){

          setInterval(function(){
              ajaxGetAccess();
          }, 2000);

	  })
        function back(){

            document.location.href = basePath+"/ea/bonuspoints/ea_getCurrCompany.jspa?posNum="+posNum;
        }
        //判断是否是终端机器
        function isExistPosNum(posNum){
            var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
            $.ajax({
                url : url,
                type : "get",
                dataType : "json",
                async:false,
                data : {
                    posNum:posNum
                },
                success : function(data) {
                    var m = eval("(" + data + ")");
                    var result = m.result;
                    if(result!="0"){
                        posNum = "";
                    }

                },
                error : function(data) {
                    // alert("验证失败");
                }

            });
            return posNum;
        }




        /**
         *
         * 获取返回的首页参数
         * @param posNum
         */
        function ajaxGetAccess() {
            try {
                posNum = Android.forAndroidDeviceId();

                if (posNum != "" && posNum != null) {
                    posNum = isExistPosNum(posNum);
                }
            }catch(error){
                if(($(window).width()==1080&&$(window).height()==1546)||($(window).width()==534&&$(window).height()==636)) {
                    posNum = 123;

                }
            }
            var ulp = basePath
                + "ea/smg/sajax_sm_ajaxGetAccess.jspa";
            $.ajax({
                type: "GET",
                url: ulp,
                dataType: "json",
                data: {
                    posNum: posNum
                },
                success: function (data) {
                    var me = eval("(" + data + ")");
                    var result = me.result;
                    var industryType = me.industryType;
                    var ccompanyID = me.ccompanyID;
                    var  companyName = me.companyName;
                    var  postype = me.postype;
                    var app = me.app;


                    if(result=="FAIL"){
                        document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
                    }else{
                        if(app=="04"){
                            document.location.href = basePath+"/ea/bonuspoints/ea_getCurrCompany.jspa?posNum="+posNum;
                        }else{
                            document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
						}
                    }
                },
                error: function (data) {
                    console.log("获取失败入口");
                }
            });
        }


</script>
<title>签到成功</title>

</head>
<body>
	<header>
		<ul class="clearfix">
			<li onclick="back();">
				<%--<img src="<%=basePath%>images/home/img-1.png">--%>
			</li>
			<li>人脸识别考勤打卡</li>
		</ul>
	</header>
	<div class="content">
		<p class="p-img">
			<img src="<%=basePath%>images/home/img-2.png">
		</p>
		<p class="p-jg">签到人姓名：${param.staffName}</p>
		<p class="p-txt">
			今日您已签到<span>${param.signCount+1}</span>次
		</p>

		<p class="p-gs">
			<img src="<%=basePath%>images/home/img-04.png">签到公司：${param.companyname}
		</p>
		<p class="p-sj">
			<img src="<%=basePath%>images/home/img-05.png">签到时间：${param.signDate}

		</p>
	</div>

</body>
<script src="<%=basePath%>js/ea/human/attence/less.js"
	type="text/javascript" charset="utf-8"></script>
</html>