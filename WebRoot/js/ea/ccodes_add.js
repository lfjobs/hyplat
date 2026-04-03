function toCCode(codePID,selectID,formID){
    $(".jqmWindow").jqmHide();
    $("#codePID").attr("value",codePID);
    $("#selectID").attr("value",selectID);
     $("#formID").attr("value",formID);
    $("#ccodevalue").attr("value","");
    $("#newccode").jqmShow();
}
function saveCCode(){
  var codePID =  $("#codePID").attr("value");

  var codeValue = $("#ccodevalue").attr("value");
    //alert(codeValue);
   var selectID = $("#selectID").attr("value");
   var formID = $($("#formID").attr("value"));
  if(($.trim(codeValue)=="")||(codeValue==null)){
  	alert("代码名称不能为空");
  }else{
  var url =  basePath+"ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="+codePID+"&code.codeValue="+codeValue+"&date="+new Date();
        $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(data){
						                         var member = eval("(" + data + ")");
						                         var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath+"page/ea/not_login.jsp";
								                  }
								                   var alerts=member.alert;
						                         if(alerts != ""){
						                         	alert(alerts);
						                         	//$("#wareName",$("#newccode")).alert("value","");
						                         	notoken=0;
						                         	return;
						                         }
											      var code = member.code;
											      $("#newccode").jqmHide();
											      $op = $("<option selected='selected'/>");
											      $op.attr("value",code.codeValue).text(code.codeValue);
											      if(selectID == "#variableID"){
											        $(selectID,formID).parent().parent().find("select").append($op);
											      }else{
											        $(selectID,formID).append($op);
											      }
											      $(".jqmWindow",formID).jqmShow();
						                        },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
}
}
$(document).ready(function() {
$("input.JQueryreturn1").click(function(){// 取消
var formID = $($("#formID").attr("value"));
                    $("#newccode").jqmHide();
                   $(".jqmWindow",formID).jqmShow();
                });   
                
                }); 