$(function(){
    //结算
    $(".m_js").click(function(){
        var coId = $(".coID").val();
        var companyId = $(".companyId").val();
        document.location.href = basePath+"ea/assicode/ea_genJieSunCode.jspa?companyId="+companyId+"&coID="+coId;
    });
    //加单
    $(".m_add").click(function(){
        var coId = $(".coID").val();
        var ccompanyId = $(".ccompanyId").val();
        document.location.href = basePath+"ea/assicode/ea_getCompanyProList.jspa?ccompanyId="+ccompanyId+"&coID="+coId;
    });

});