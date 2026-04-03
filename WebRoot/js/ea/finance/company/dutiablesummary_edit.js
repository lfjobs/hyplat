$(function(){
//为弹出框准备下拉表内容
	 $(".jqmWindow").jqm({
                    modal: true,// 
                    overlay: 20 // 
                }).jqmAddClose('.close');//
       $(".JQueryClose").click(function(){
        re_load();
    });
});
function re_load(){
    document.location.href=basePath+"/ea/dutiablessummarycompany/ea_getDutiableList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&search="+search+"&statusDu="+statusDu;
}