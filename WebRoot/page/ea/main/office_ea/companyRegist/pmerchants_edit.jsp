<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/index.css">

    <!--<link rel="stylesheet" type="text/css" href="theme/default/laydate.css"/>-->
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <!--日历插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/dist/zane-calendar.min.css"/>
    <script src="<%=basePath%>css/ea/office_ea/mbapply/dist/zane-calendar.min.js" type="text/javascript" charset="utf-8"></script>
    <!--文件上传多-->
    <!--<link rel="stylesheet" type="text/css" href="css/semantic.min.css"/>-->
    <!--地址联动-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/iconfont.css"/>
    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/area.js" type="text/javascript" charset="utf-8"></script>
    <script language="javascript" type="text/javascript"
            src="<%=basePath%>js/ea/office_ea/companyRegist/pmerchants_edit.js"></script>
    <script type="text/javascript">

        var basePath="<%=basePath%>";
        var mode = "${param.mode}";
        var out_request_no = "${applyParam.out_request_no}";
        var organization_type = "${applyParam.organization_type}";
        var  id_card_valid_time =  "${id_card_info.id_card_valid_time}";
        var  business_time =  "${business_license_info.business_time}";
        var organization_time =  "${organization_cert_info.organization_time}";
        var bank_account_type =  "${account_info.bank_account_type}";
        var contact_type = "${contact_info.contact_type}";


    </script>

</head>
<body>
<form name="form1" id="form1" method="post" enctype="multipart/form-data">
<div class="content">
    <section class="sec002 info organization_type">
        <h3>
            基本账户信息修改
            <div class="right">
                <p class="submit1">确定</p>
            </div>
        </h3>
        <p>
            <label for="">业务申请编号</label>
            <input type="text" value="${applyParam.out_request_no}"  readonly/>
        </p>
        <p>
            <label for="">主体类型</label>
            <select name="applyParam.organization_type" id="organization_type">
                <option value="2401">个人卖家</option>
                <option value="2">企业</option>
                <option value="4">个体工商户</option>
                <option value="3">党政、机关及事业单位</option>
            </select>
        </p>

    </section>
    <section class="sec002 info sales_scene_info">
        <h3>店铺基本信息修改<div class="right">
            <p class="submit1">确定</p>
        </div></h3>
        <section class="clearfix sec-tupian">
            <p>
                店铺logo:
            </p>
            <div>
                <input type="file" name="logo" id="sdfFile4" value="" onchange="f_change4(this);" >
                <img width="80px" src="<%=basePath%>${contactCompany.logoPath}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_12.png'" id="imgSdf4"/>
            </div>
        </section>
        <p>
            <label for="">店铺名称</label>
            <input type="text" name="sales_scene_info.store_name" value="${sales_scene_info.store_name}" />
            <input type="text" style="display:none"  name="sales_scene_info.ssKey" value="${sales_scene_info.ssKey}" />
            <input type="text" style="display:none"  name="sales_scene_info.ssId" value="${sales_scene_info.ssId}" />
        </p>
        <p>
            <label for="">商户简称</label>
            <input type="text" name="applyParam.merchant_shortname" value="${applyParam.merchant_shortname}" />
        </p>
        <p>
            <label for="">店铺行业</label>
            <input type="text"   name="contactCompany.industryType" value="${contactCompany.industryType}" readonly/>
            <input type="hidden"   name="contactCompany.industryId" value="${contactCompany.industryId}" />
            <input type="hidden"   name="contactCompany.ccompanyKey" value="${contactCompany.ccompanyKey}" />
            <input type="hidden"   name="contactCompany.ccompanyID" value="${contactCompany.ccompanyID}" />
        </p>
        <p>
            <label for="">费率</label>
            <input type="text" value="${wxMainAccount.rate}" name="contactCompany.remark"  />
        </p>
        <p>
            <label for="">店铺地址</label>
            <input type="text" name="contactCompany.companyAddr" value="${contactCompany.companyAddr}" />
        </p>
        <%--<div class="liandong clearfix">--%>
            <%--<label for="">店铺地址</label>--%>
            <%--<div id="bigOne">--%>
                <%--<div id="box">--%>
                    <%--<span class="loadText text">请选择省/市/区</span>--%>
                    <%--<span class="textProvince text"></span>--%>
                    <%--<span class="textCity text"></span>--%>
                    <%--<span class="textArea text"></span>--%>
                    <%--<span class="iconfont icon-arrow-down arrow"></span>--%>
                <%--</div>--%>
                <%--<input type="text" value="" id="totalConfirm" readonly="">--%>
                <%--<div id="content">--%>
                    <%--<div class="chose-tab">--%>
                        <%--<div id="province" class="chosePCA select">省份--%>
                            <%--<ul class="province-list list">--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                        <%--<div id="city" class="chosePCA">城市--%>
                            <%--<ul class="city-list list">--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                        <%--<div id="area" class="chosePCA">区县--%>
                            <%--<ul class="area-list list">--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<button class="reset btn">重置</button>--%>
                <%--<button class="confirm btn">确定</button>--%>
            <%--</div>--%>
            <%--<input type="text"  placeholder="详细门牌号"/>--%>


        <%--</div>--%>


    </section>
    <section class="sec002 info id_card_info">
        <h3>
            经营者或法人身份证信息修改
            <div class="right">
                <p class="submit1">确定</p>
            </div>
        </h3>
        <section class="clearfix sec-tupian">
            <p>
                身份证人像面照片:
            </p>
            <div>
                <input type="file" name="cardcopy" id="sdfFile2" value="" onchange="f_change2(this);" >
                <img alt="图片" width="70px" src="<%=basePath%>${id_card_info.id_card_copy}"   onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_10.png'" id="imgSdf2">
            </div>
        </section>
        <section class="clearfix sec-tupian">
            <p>
                身份证国徽面照片:
            </p>
            <div>
                <input type="file" name="cardnational" id="sdfFile3" value="" onchange="f_change3(this);" >
                <img alt="图片" width="70px" src="<%=basePath%>${id_card_info.id_card_national}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_11.png'" id="imgSdf3">
            </div>
        </section>
        <p>
            <label for="">身份证姓名</label>
            <input type="text" name="id_card_info.id_card_name" value="${id_card_info.id_card_name}"/>
            <input type="text" style="display:none" name="id_card_info.cardKey" value="${id_card_info.cardKey}"/>
            <input type="text" style="display:none" name="id_card_info.cardId" value="${id_card_info.cardId}"/>
            <input type="text" style="display:none" name="id_card_info.id_card_copy" value="${id_card_info.id_card_copy}"/>
            <input type="text" style="display:none" name="id_card_info.id_card_national" value="${id_card_info.id_card_national}"/>

        </p>
        <p>
            <label for="">身份证号码</label>
            <input type="text" name="id_card_info.id_card_number" value="${id_card_info.id_card_number}" />
        </p>
        <p>
            <label for="">身份证住址</label>
            <input type="text" name="id_card_info.id_card_address" value="${id_card_info.id_card_address}" />
        </p>
        <section class="clearfix sec-riqi">
            <p>身份证开始日期：</p>
            <div>
                <input type="text" id="start" name="id_card_info.id_card_valid_time_begin" class="input-last" value="${id_card_info.id_card_valid_time_begin}"/>
                <label class="input-last" for="start"><img src="<%=basePath%>images/ea/office/pbapply/pc_img_15.png"></label>

            </div>
        </section>
        <section class="clearfix sec-riqi">
            <p>身份证有效期限：</p>
            <div>
                <input type="hidden" class="final" name="id_card_info.id_card_valid_time" id="id_card_valid_time" value="${id_card_info.id_card_valid_time eq '长期'?'':id_card_info.id_card_valid_time}"/>
                <input type="text" id="validtime" class="input-last" value="" />
                <label class="input-last" for="validtime"><img src="<%=basePath%>images/ea/office/pbapply/pc_img_15.png"></label>
                <section>
                    <input type="checkbox"  class="cq" id="cardcq" value="长期">长期

                </section>
            </div>
        </section>
    </section>
    <section class="sec002 info business_license_info">
        <h3>
            营业执照信息修改
            <div class="right">
                <p class="submit1">确定</p>
            </div>
        </h3>
        <section class="clearfix sec-tupian">
            <p>
                证件扫描件:
            </p>
            <div>
                <input type="file" name="licensecopy" id="sdfFile1" value="" onchange="f_change1(this);" >
                <img alt="图片" width="70px" src="<%=basePath%>${business_license_info.business_license_copy}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_09.png'" id="imgSdf1">
            </div>
        </section>
        <p>
            <label for="">证件注册号</label>
            <input type="text"  name="business_license_info.business_license_number" value="${business_license_info.business_license_number}"/>
            <input type="text"  style="display:none;" name="business_license_info.bliKey" value="${business_license_info.bliKey}"/>
            <input type="text"  style="display:none;" name="business_license_info.bliID" value="${business_license_info.bliID}"/>
            <input type="text"  style="display:none;" name="business_license_info.business_license_copy" value="${business_license_info.business_license_copy}"/>

        </p>
        <p>
            <label for="">商户名称</label>
            <input type="text" name="business_license_info.merchant_name" value="${business_license_info.merchant_name}"/>
        </p>
        <p>
            <label for="">经营者/法定代表人姓名</label>
            <input type="text" name="business_license_info.legal_person" value="${business_license_info.legal_person}" />
        </p>
        <p>
            <label for="">注册地址</label>
            <input type="text" name="business_license_info.company_address" value="${business_license_info.company_address}" />
        </p>
        <section class="clearfix sec-riqi">
            <p>营业期限：</p>
            <div>
                <input type='hidden' class='final' name='business_license_info.business_time' id='bustime' value='${business_license_info.business_time}'/>
                <input type="text" id="start" value="" />
                <label for="start"><img src="<%=basePath%>images/ea/office/pbapply/pc_img_15.png"></label>
                <input class="input-last" type="text" id="end" value="" />
                <label class="input-last" for="end"><img src="<%=basePath%>images/ea/office/pbapply/pc_img_15.png"></label>
                <section>
                    <input type="checkbox" class="cq" id="bucq" value="长期">长期
                </section>
            </div>
        </section>
        <section class="clearfix sec-tupian">
            <p>
                组织机构代码证照片:
            </p>
            <div>
                <input type="file" name="orgcopy" id="sdfFile" value="" onchange="f_change(this);" >
                <img alt="图片" src="<%=basePath%>${organization_cert_info.organization_copy}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_08.png'" id="imgSdf">
            </div>
        </section>
        <p>
            <label for="">组织机构代码</label>
            <input type="text" name="organization_cert_info.organization_number" value="${organization_cert_info.organization_number}" />
            <input type="text" style="display:none" name="organization_cert_info.ociKey" value="${organization_cert_info.ociKey}" />
            <input type="text" style="display:none" name="organization_cert_info.ociID" value="${organization_cert_info.ociID}" />
            <input type="text" style="display:none" name="organization_cert_info.organization_copy" value="${organization_cert_info.organization_copy}" />


        </p>
        <section class="clearfix sec-riqi">
            <p>组织机构代码有效期限：</p>
            <div>
                <input type="hidden" class="final" name="organization_cert_info.organization_time" value="${organization_cert_info.organization_time}" id="orgtime"/>
                <input type="text" id="start1" value="" />
                <label for="start1"><img src="<%=basePath%>images/ea/office/pbapply/pc_img_15.png"></label>
                <input class="input-last" type="text" id="end1" value="" />
                <label class="input-last" for="end1"><img src="<%=basePath%>images/ea/office/pbapply/pc_img_15.png"></label>
                <section>
                    <input type="checkbox" id="orcq"  class="cq"  value="长期">长期
                </section>
            </div>
        </section>
    </section>
    <section class="sec002 info account_info">
        <h3>结算银行账户修改<div class="right">
            <p class="submit1">确定</p>
        </div></h3>
        <p>
            <label for="">账户类型</label>
            <select name="account_info.bank_account_type" id="bank_account_type">
                <option value="74">对公</option>
                <option value="75">对私</option>

            </select>
        </p>
        <section class="clearfix sec-tupian">
            <p>
                银行卡照片:
            </p>
            <div>
                <img alt="图片" width="70px" src="<%=basePath%>${account_info.account_copy}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_05.png'" id="imgSdf3">
            </div>
        </section>
        <p>
            <label for="">开户银行</label>
            <input type="text" name="account_info.account_bank" value="${account_info.account_bank}" />
            <input type="text" style="display:none" name="account_info.acKey" value="${account_info.acKey}" />
            <input type="text" style="display:none" name="account_info.acId" value="${account_info.acId}" />
            <input type="text" style="display:none" name="account_info.account_copy" value="${account_info.account_copy}" />
        </p>
        <p>
            <label for="">开户名称</label>
            <input type="text" name="account_info.account_name" value="${account_info.account_name}" />
        </p>
        <p>
            <label for="">开户银行省市</label>
            <input type="text" name="account_info.bank_address" value="${account_info.bank_address}" />
        </p>
        <p>
            <label for="">开户银行省市编码</label>
            <input type="text" name="account_info.bank_address_code" value="${account_info.bank_address_code}" />
        </p>
        <p>
            <label for="">开户银行全称</label>
            <input type="text" name="account_info.bank_name" value="${account_info.bank_name}"/>
        </p>
        <p>
            <label for="">银行卡号</label>
            <input type="text" name="account_info.account_number" value="${account_info.account_number}" />
        </p>
    </section>
    <section class="sec002 info contact_info">
        <h3>超级管理员信息修改<div class="right">
            <p class="submit1">确定</p>
        </div></h3>
        <p>
            <label for="">超级管理员类型</label>
            <select name="contact_info.contact_type" id="contact_type">
                <option value="65">法人</option>
                <%--<option value="66">负责人</option>--%>
            </select>
        </p>
        <p>
            <label for="">超级管理员姓名</label>
            <input type="text" name="contact_info.contact_name" value="${contact_info.contact_name}" />
            <input type="text"  style="display:none" name="contact_info.coKey" value="${contact_info.coKey}" />
            <input type="text"  style="display:none" name="contact_info.coId" value="${contact_info.coId}" />
        </p>
        <p>
            <label for="">超级管理员身份证号码</label>
            <input type="text" name="contact_info.contact_id_card_number" value="${contact_info.contact_id_card_number}"/>
        </p>
        <p>
            <label for="">手机号</label>
            <input type="text" name="contact_info.mobile_phone" value="${contact_info.mobile_phone}"/>
        </p>
        <p>
            <label for="">超级管理员邮箱</label>
            <input type="text" name="contact_info.contact_email" value="${contact_info.contact_email}" />
        </p>
    </section>
    <section class="sec002 info business_addition">
        <h3>补充信息修改<div class="right">
            <p class="submit1">确定</p>
        </div></h3>
        <section class="clearfix sec-tupian">
            <p>
                特殊资质:
            </p>
            <div>
                <!--<input type="file" multiple name="sdfFile5" id="sdfFile5" value="" onchange="f_change5(this);" >
                <img alt="图片" src="img/pc_img_13.png" id="imgSdf5">-->
                <div class="m-container-small m-padded-tb-big">
                    <div class="ui container">
                        <%--<form class="ui form">--%>
                            <div class="field">
                                <input type="file" id="upload_file" name="files" style="display: none;">
                                <div class="imageContainer">
                                    <div class="ui centered image" style="width:100px; height: 100px; background-color: white; border: 2px dashed black; display: inline-block;">
                                        <div class="center">
                                            <!--<img src="../static/images/add.png" alt="" class="ui  mini centered image">-->
                                            <p style="margin-top: 8px;" id="uploadMoreImages">上传图片</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--<button class="ui button" type="submit">上传</button>-->
                        <%--</form>--%>
                    </div>
                </div>
            </div>
        </section>
        <section class="clearfix sec-tupian">
            <p>
                补充材料:
            </p>
            <div>
                <!--<input type="file" name="sdfFile6" id="sdfFile6" value="" onchange="f_change6(this);" >
                <img alt="图片" src="img/pc_img_14.png" id="imgSdf6">-->
                <div class="m-container-small m-padded-tb-big">
                    <div class="ui container">
                        <%--<form class="ui form">--%>
                            <div class="field">
                                <input type="file" id="upload_file2" name="files" style="display: none;">
                                <div class="imageContainer2">
                                    <div class="ui centered image" style="width:100px; height: 100px; background-color: white; border: 2px dashed black; display: inline-block;">
                                        <div class="center2">
                                            <!--<img src="../static/images/add.png" alt="" class="ui  mini centered image">-->
                                            <p style="margin-top: 8px;" id="uploadMoreImages2">上传图片</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--<button class="ui button" type="submit">上传</button>-->
                        <%--</form>--%>
                    </div>
                </div>
            </div>
        </section>
        <p class="p-textarea">
            <label for="">补充说明</label>
            <textarea name="" rows="" cols="" name="applyParam.business_addition_desc">${applyParam.business_addition_desc}</textarea>
        </p>
    </section>
</div>


    <input type="submit" name="submit" style="display:none;"/>
    <input type="hidden" name="mode" value="${param.mode}" style="display:none;"/>
    <input type="hidden" name="applyParam.out_request_no" value="${applyParam.out_request_no}"/>
    <input type="hidden" name="applyParam.applyID" value="${applyParam.applyID}"/>

    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</form>
</body>

<%--<script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/script.js" type="text/javascript" charset="utf-8"></script>--%>
<!--<script src="js/laydate.js" type="text/javascript" charset="utf-8"></script>-->
<script type="text/javascript">

    //JS file 图片 即选即得 显示
    //创建一个FileReader对象
    var reader = new FileReader();
    function f_change(file){
        var img = document.getElementById('imgSdf');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "300";
//		        img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
    function f_change1(file){
        var img = document.getElementById('imgSdf1');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "300";
//		        img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
    function f_change2(file){
        var img = document.getElementById('imgSdf2');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "300";
//		        img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
    function f_change3(file){
        var img = document.getElementById('imgSdf3');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "300";
//		        img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
    function f_change4(file){
        var img = document.getElementById('imgSdf4');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "300";
//		        img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
    function f_change5(file){
        var img = document.getElementById('imgSdf5');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "300";
//		        img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
    function f_change6(file){
        var img = document.getElementById('imgSdf6');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "300";
//		        img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
    //日历
    zaneDate({
        elem:'#start',
    });
    zaneDate({
        elem:'#end',
    });
    zaneDate({
        elem:'#start1',
    });
    zaneDate({
        elem:'#end1',
    });
    zaneDate({
        elem:'#validtime',
    });

    //多文件上传
    $('.menu_toggle').click(function() {
        $(".m-item").toggleClass('m-mobile-hide')
    })
    $(".center").click(function() {
        $("#upload_file").click();
    })
    $(".center2").click(function() {
        $("#upload_file2").click();
    })
    var img
    $("#upload_file").change(function() {
        var images = []; //定义一个空的数组用来储存img路径
        var files = $(this).get(0).files;
        console.log(files)
        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/; //只能上传图片
        $($(this)[0].files).each(function() {
            var file = $(this);
            console.log(file)
            console.log($(".imageContainer div").length)
            //图片数量判断
            if($(".imageContainer div").length<=6){
                if(regex.test(file[0].name.toLowerCase())) {
                    //如果是图片
                    var reader = new FileReader();
                    reader.onload = function(e) { //读取图片成功会执行这个函数
                        var div = $("<div></div>")
                        div.attr("style", "position: relative; display:inline-block;")
                        var delImg = $("<img/>")
                        delImg.attr("style", "position: absolute; right: 15px; top: 15px; width: 20px;height: 20px; z-index:1; display:none;")
                        delImg.attr("src", "<%=basePath%>images/ea/office/pbapply/close.png")
                        delImg.attr("class", "delImg")

                        var img = $("<img />");
                        img.attr("style", "display: inline-block; height:104px; margin:8px;");
                        img.attr("src", e.target.result);
                        img.attr("class", "ui small centered image")
                        div.append(delImg)
                        div.append(img);
                        $(".imageContainer").append(div);
                    }
                    reader.readAsDataURL(file[0]);
                    $("#uploadMoreImages").text("上传更多图片")
                } else {
                    alert(file[0].name + "不是一张图片");
                    return false;
                }
            }

        })
    })
    $("#upload_file2").change(function() {
        var images = []; //定义一个空的数组用来储存img路径
        var files = $(this).get(0).files;
        console.log(files)
        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/; //只能上传图片
        $($(this)[0].files).each(function() {
            var file = $(this);
            console.log(file)
            if($(".imageContainer2 div").length<=6){
                if(regex.test(file[0].name.toLowerCase())) {
                    //如果是图片
                    var reader = new FileReader();
                    reader.onload = function(e) { //读取图片成功会执行这个函数
                        var div = $("<div></div>")
                        div.attr("style", "position: relative; display:inline-block;")
                        var delImg = $("<img/>")
                        delImg.attr("style", "position: absolute; right: 15px; top: 15px; width: 20px;height: 20px; z-index:1; display:none;")
                        delImg.attr("src", "<%=basePath%>images/ea/office/pbapply/close.png")
                        delImg.attr("class", "delImg")

                        var img = $("<img />");
                        img.attr("style", "display: inline-block; height:104px; margin:8px;");
                        img.attr("src", e.target.result);
                        img.attr("class", "ui small centered image")
                        div.append(delImg)
                        div.append(img);
                        $(".imageContainer2").append(div);
                    }
                    reader.readAsDataURL(file[0]);
                    $("#uploadMoreImages2").text("上传更多图片")
                } else {
                    alert(file[0].name + "不是一张图片");
                    return false;
                }
            }
        })
    })

    //鼠标悬浮显示删除按钮
    $(".imageContainer,.imageContainer2").on("mouseover mouseout", "img", function(event) {
        // console.log($(this).prev()[0])
        if(event.type == "mouseover") {
            //鼠标悬浮
            $($(this).prev()[0]).show();
        } else if(event.type == "mouseout") {
            //鼠标离开
            $($(this).prev()[0]).hide();
        }
    })
    //删除逻辑数量限制
    //删除图片
    $(".imageContainer,.imageContainer2").on("click", ".delImg", function() {
//			$($(this).next()[0]).remove();
        $($(this).parent("div")).remove();
    })
</script>
</html>
