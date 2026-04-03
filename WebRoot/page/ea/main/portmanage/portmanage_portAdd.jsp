<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>接口</title>
    <script src="<%=basePath%>js/ea/portmanage/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/ea/portmanage/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/ea/portmanage/css/port.css">
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
    </script>
</head>
<body>
<form action="" method="post" id="submitForm">
    <div class="clearfix">
        <p>
            接口详情
        </p>
        <input type="button" name="" id="" value="保存接口" onclick="save()"/>
    </div>
    <section class="clearfix">
        <div class="clearfix btn_1">
            <select id="portRequestType">
                <option value="POST">POST</option>
                <option value="GET">GET</option>
                <option value="PUT">PUT</option>
                <option value="DELETE">DELETE</option>
            </select>
        </div>
        <div class="clearfix btn_1">
            <select  id="dataFormat">
                <option value="JSON">JSON</option>
                <option value="XML">XML</option>
                <option value="HTML">HTML</option>
                <option value="text">text</option>
            </select>
            <input type="hidden" id="portId"/>
            <input type="hidden" id="documentsId"/>
        </div>
        <input type="text" name="" id="portUrl"  placeholder="接口地址"/>
    </section>
    <input type="text" name="" id="portName" placeholder="接口名称"/>
    <textarea name="" rows="" cols="" placeholder="接口描述" id="portDescription"></textarea>
    <p>请求参数</p>
    <section id="sec_fir">
        <menu class="clearfix hed">
            <li>参数名称</li>
            <li>参数类型</li>
            <li>参数说明</li>
            <li>必填</li>
            <li>示例</li>
            <li>删除</li>
        </menu>
        <!--模板-->
        <menu class="clearfix copy_menu none">
            <li>
                <input type="text" name="parameterName"  value=""/>
            </li>
            <li>
                <div class="clearfix btn_1">
                        <select name="parameterType">
                            <option value="string">string</option>
                            <option value="double">double</option>
                            <option value="int">int</option>
                            <option value="boolean">boolean</option>
                            <option value="byte">byte</option>
                            <option value="short">short</option>
                            <option value="long">long</option>
                            <option value="float">float</option>
                            <option value="date">date</option>
                            <option value="datetime">datetime</option>
                            <option value="object">object</option>
                            <option value="File">File</option>
                        </select>
                </div>
        </li>
        <li>
            <input type="text" name="parameterExplain"  value=""/>
        </li>
        <li>
            <input type="checkbox" name="required"  value="1"/>
            <input type="hidden" name="required" value="0" />
        </li>
        <li>
            <input type="text" name="example" value=""/>
        </li>
        <li>
            <input type="text" name="" value="" readonly="readonly"/>
        </li>
        <li class="del none">
            <img src="<%=basePath%>js/ea/portmanage/images/pic_09.png "/>
        </li>
        </menu>

    </section>
    <p>
        <img src="<%=basePath%>/js/ea/portmanage/images/pic_07.png"/>
        <span id="add_menu">添加属性</span>
    </p>
    <section>
        <div class="clearfix" id="tab_a">
            <p class="p_active">
                返回示例
            </p>
            <p>
                异常示例
            </p>
        </div>
        <div id="tab_b">
            <textarea name="" rows="" cols="" id="successParam"></textarea>
            <textarea class="none" name="" rows="" cols="" id="failParam"></textarea>
        </div>
    </section>
</form>
</body>
<script type="text/javascript">

    $(".btn_1").click(function (evevt) {
        event.stopPropagation();
        $(this).children("menu").toggle();
    })

    $("#sec_fir menu").hover(function () {
        $(this).children(".del").toggleClass("none");
    })
    $(".del").click(function () {
        if(confirm("确定要删除该参数吗？")){
            $(this).parents(".copy_menu").remove();
        }
    })
    $("#add_menu").mouseover(function () {
        $(this).css({
            "color": "blue"
        });
    }).mouseout(function () {
        $(this).css({
            "color": ""
        });
    });
    $("#add_menu").click(function () {
        $("#sec_fir").append($("#sec_fir menu:nth-of-type(2)").clone(true).removeClass("none"))
    })
    $("#tab_a p").click(function () {
        $(this).parent().children("p").removeClass("p_active")
        $(this).addClass("p_active");
        $("#tab_b textarea").addClass("none");
        $("#tab_b textarea").eq($(this).index()).removeClass("none")
    })
</script>
<script>
    function save(){
       //非空校验
        var textcount=$("input[name='parameterName']").length;
        if(textcount==1){
            alert("参数不能为空,请您输入！");
            return;
        }
        /*if ($("#portRequestType").val() == ''||$("#portRequestType").val().replace(/\s*!/g, "") == "") {
            alert("参数不能为空,请您输入！");
            return;
        }
        if ($("#dataFormat").val() == ''||$("#portRequestType").val().replace(/\s*!/g, "") == "") {
            alert("参数不能为空,请您输入！");
            return;
        }*/
        if ($("#portUrl").val() == ''||$("#portUrl").val().replace(/\s*/g, "") == "") {
            alert("接口地址不能为空,请您输入！");
            return;
        }
        if ($("#portName").val() == ''||$("#portName").val().replace(/\s*/g, "") == "") {
            alert("接口名称不能为空,请您输入！");
            return;
        }
        if ($("#portDescription").val() == ''||$("#portDescription").val().replace(/\s*/g, "") == "") {
            alert("接口描述不能为空,请您输入！");
            return;
        }
        if ($("#successParam").val() == ''||$("#successParam").val().replace(/\s*/g, "") == "") {
            alert("返回示例不能为空,请您输入！");
            return;
        }
        if ($("#failParam").val() == ''||$("#failParam").val().replace(/\s*/g, "") == "") {
            alert("异常示例不能为空,请您输入！");
            return;
        }
        /*if ($("#documentsId").val() == ''||$("#documentsId").val().replace(/\s*!/g, "") == "") {
            alert("参数不能为空,请您输入！");
            return;
        }*/
        //数据[=,&...]处理
        $("#sec_fir input[type='text']").each(function(){
            var str = $(this).val();
            //js验证 `~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？
            var pattern = new RegExp("[~'!@#$%^&*()-+_=:]");
            if(str != "" && str != null){
                if(pattern.test(str)){
                    var reg =new RegExp("=","g");
                    var newstart=str.replace(reg,"等于");
                    var reg1 =new RegExp("&","g");
                    var newstart1=newstart.replace(reg1,"并且");
                    $(this).val(newstart1);
                }
            }
        });

        //移除被选元素[#sec_fir menu:nth-of-type(2)]，包括所有文本和子节点。
        $("#sec_fir menu:nth-of-type(2)").remove();
        //表单序列化并解码
        var msg = decodeURIComponent($("#submitForm").serialize());
        var json = "[{";
        var msg2 = msg.split("&");   //先以“&”符号进行分割，得到一个key=value形式的数组
        var t = false;
        for(var i = 0; i<msg2.length; i++){
            var msg3 = msg2[i].split("=");  //再以“=”进行分割，得到key，value形式的数组
            for(var j = 0; j<msg3.length; j++){
                json+="\""+msg3[j]+"\"";
                if(j+1 != msg3.length){
                    json+=":";
                }
                if(t){
                    json+="}";
                    if(i+1 != msg2.length){  //表示是否到了当前行的最后一列
                        json+=",{";
                    }
                    t=false;
                }
                if(msg3[j] == "example"){  //这里的“example”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
                    t = true;
                }
            }
            if(!msg2[i].match("example")){  //同上
                json+=",";
            }

        }
        json+="]";
        var portRequestType =  $("#portRequestType option:selected").val();
        var dataFormat =  $("#dataFormat option:selected").val();
        var portUrl=$("#portUrl").val();
        var portName=$("#portName").val();
        var portDescription=$("#portDescription").val();
        var successParameter=$("#successParam").val();
        var failParameter=$("#failParam").val();
        var documentsId=$("#documentsId").val();
        var portParms={
            "portRequestType":portRequestType,
            "dataFormat":dataFormat,
            "portUrl":portUrl,
            "portName":portName,
            "portDescription":portDescription,
            "successParameter":successParameter,
            "failParameter":failParameter,
            "documentsId":documentsId,
            "parameters":json
        };
       //alert(JSON.stringify(portParms))

        //接口添加
        $.ajax({
            type: "POST",
            url: basePath + "ea/ccode1/sajax_pm_addPortAndParameters.jspa",
            data:{"portAndParameter":JSON.stringify(portParms)},
            dateType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                var addPort = member.addPort;
                //接口添加成功
                if (code == '200') {
                    alert(addPort);
                    location.href = basePath + 'ea/ccode1/ea_pm_selectPorts.jspa?documentsId='+documentsId;
                } else if(code == '400'){
                    //未登录
                    alert(addPort);
                    location.href = basePath + "page/ea/not_login.jsp";
                }else{
                    //接口添加失败
                    alert(addPort);
                    location.reload();
                }

            },
            error: function (data) {
                alert("接口添加失败！");
                location.reload();
            }
        });
    }
    //取消保存
    function cancelSave() {

    }

    //接收一个值
    function oneValues(){
        var result;
        var url=window.location.search; //获取url中"?"符后的字串
        if(url.indexOf("?")!=-1){
            result = url.substr(url.indexOf("=")+1);
        }
        return result;
    }


</script>
<script>

       $(function(){
            $("#documentsId").val(oneValues());

        });

</script>
</html>
