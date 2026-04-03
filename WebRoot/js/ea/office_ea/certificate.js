$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	window.parent.document.getElementById("mainframe2").style.height = 180 + len * 27 + "px";
		  $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
//                .jqDrag('.drag');// 添加拖拽的selector
		
        $('.address').flexigrid({
            height: 'auto',
            width: 'auto',
            minwidth: 30,
            allDouble : true,
            buttons: [{
                name: '添加',
                bclass: 'add',
                onpress: action//当点击调用方法
            }, {
               separator: true
        	},{
	            name: '修改',
	            bclass: 'edit',
				onpress : action//当点击调用方法
            },{
                separator: true
            },{
	            name: '删除',
	            bclass: 'delete',
				onpress : action//当点击调用方法
            },{
            	separator: true
            },{
	            name: '全部保存',
	            bclass: 'add',
				onpress : action//当点击调用方法
        	}, {
             separator: true
        	},{
                name: '查询',
                bclass: 'mysearch',
                onpress: action//当点击调用方法
            }, {
                separator: true
            }, {
                name: '导出',
                bclass: 'excel',
                onpress: action//当点击调用方法
            }, {
                separator: true
            },{
	            name: '设置每页显示条数',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
            },{
            	separator: true
        }]
        });
        function action(com, grid){
            switch (com) {
                case '添加':
                   $("#sa").after($("#sa").clone(true).attr("id","sa"+select).addClass("check"));
                   $("#sa"+select).find(':input:gt(0)').each(function(){
                        $(this).attr("name","certificatemap["+select+"]."+this.name);
                     });
                $("#sa"+select).show();  
                 var heis = parent.document.getElementById("mainframe2").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe2").style.height = heis;
                select++;
				break;
                case '修改':
				  if(certificateID=='')
				  {
				   alert("请选择！");
				   return;
				  }
				  if($("#"+certificateID+" #status").attr("value") == '01'){
				  alert("不可修改");
				  break;
			      }
			     $p = $("tr#"+certificateID);
			     	if($p.hasClass("check"))
		              {
		              return;
		              }
                 $p.addClass("check");
                 $p.find(':input:gt(0)').each(function(){
                        $(this).attr("name","certificatemap["+select+"]."+this.name);
                     });
                  select++;
			   $p.find("span").addClass("model1");
			   $p.find("input").removeClass("model1");
			   $p.find("select").attr("disabled",false);
			   $p.find("select").show();
			   $(this).parent().children("span").show();
			  break;
			 case '全部保存':
			      if(notoken)return;
			      if(select==1)
				     {
					     return;
				     }
				     	var re = 0;
				$("input.aaaa", $(".check")).each(function() {
							var panduan = this.value;
							var $s = $(this);
							if ((panduan == "")||(panduan ==" ")) {
								$s.css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					alert("不能为空!");
					notoken = 0;
					return;
				}
		           notoken = 1;
			      $f = $('#certificateForm');
				   $('#certificateForm').attr("target","hidden").attr("action",basePath+"ea/certificate/ea_saveCertificate.jspa?pageNumber="+ppageNumber);
				   document.certificateForm.submit.click();
				   token = 2;
				 break;
			 case '删除':
			     if(certificateID=='')
				  {
				   alert("请选择！");
				   return;
				  }
				  if(certificateID.substring(0,2) == "sa")
				  {
				      $("#"+certificateID).remove();
				      certificateID="";
				    return;
				  }
				  $f = $('#certificateForm');
			      if (confirm("是否删除？")) {
					  $f.attr("target","hidden").attr("action",basePath+"ea/certificate/ea_delCertificate.jspa?pageNumber="+ppageNumber+"&certificate.certificateID="+certificateID);
					  document.certificateForm.submit.click();
					  $("tr#"+certificateID).remove();
					  certificateID = '';
					  token = 11;
					    
			 	  }
				break;
                case '导出':
                    var url = basePath+"ea/certificate/ea_showExcel.jspa?search="+pserch+"&certificate.ccompanyID="+ccompanyID;
                    open(url);
                    break;
                case '查询':
                    $("#jqModelSearch").jqmShow();
                    break;
                case '设置每页显示条数':
				  var url = basePath
						+ "/ea/certificate/ea_getaCertificateList.jspa?certificate.ccompanyID="
						+ ccompanyID + "&search=" + pserch;
					numback(url);
					break;     
            }
        }
        
     $("#certificateTiime").blur(function(){
		        	var ctime = $(this).val().replace(/-/g, '');
					var adate = $(this).parent().parent().parent().find("input#availabilityDate").val().replace(/-/g, '');
		        	if(ctime!=''&&adate != ''&&adate > ctime){
		        		alert("有效期小于发证时间！");
		        		$(this).parent().parent().parent().find(".ss").val("");
					    $(this).focus();
		        	}
	});
		        
	$("#availabilityDate").focus(function(){
		   if($(this).parent().parent().parent().find("input#certificateTiime").val() == ''){
		   		alert("请先填写发证时间！");
		   		$(this).parent().parent().parent().find("input#certificateTiime").focus();
		   }
	}).blur(function(){
			var adate = $(this).val().replace(/-/g, '');
			var ctime = $(this).parent().parent().parent().find("input#certificateTiime").val().replace(/-/g, '');
			if(ctime!=''&&adate != '' &&ctime.replace(/-/g, '') > adate.replace(/-/g, '')){
				if(times == '0'){
					alert("有效期小于发证时间！");
					$(this).parent().parent().parent().find(".ss").val("");
					$(this).parent().parent().parent().find("input#certificateTiime").focus();
					times++;
					}
					if($(this).parent().parent().parent().find("input#certificateTiime").focus()){
						times = '0';
					}
			}
	});
	
	//去掉所有空格
	String.prototype.time= function(){ 
		return this.replace(/\s+/g, ""); 
	};
	
	$("#certificateCode").blur(function(){
		var code=$(this).val().time();
		if(code!=''){
			if(code.length>20){
				alert("证书编号不能超过20个字符！");
				$(this).val("");
				$(this).focus();
				return;
			} 
			cc=/^[A-Za-z0-9]+$/;
			if(!cc.test(code)){
				alert("证书编号由数字和26个英文字母组成");
				$(this).val("");
				$(this).focus();
				return;
			}
		}
	});
        
     $(".address tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    certificateID =this.id;
                }) ;               
    $(".address tr[id]").dblclick(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    certificateID =this.id;
                    action("修改");
                });

      $("#tosearch").click(function(){
	  	 $("form :input").trigger("blur");
	  	 if ($("form .error").length) {
   	   		  return false;
 		   }
          $("#postSearchForm").attr("action", basePath+"ea/certificate/ea_toSearch.jspa?pageNumber="+ppageNumber+"&certificate.ccompanyID="+ccompanyID);
          document.postSearchForm.submit.click();
      });
});
 function re_load(){
     	if(token)
        document.location.href=basePath+"/ea/certificate/ea_getaCertificateList.jspa?certificate.ccompanyID="+ccompanyID+"&pageNumber="+ppageNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
        }