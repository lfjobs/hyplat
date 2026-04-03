$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	window.parent.document.getElementById("mainframe3").style.height = 180 + len * 27 + "px";
	 $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
//                .jqDrag('.drag');// 添加拖拽的selector
	$('.staffappraisal').flexigrid({
        height: 'auto',
        width: 'auto',
         allDouble : true,
        minwidth: 30,
        minheight: 80,
		 buttons: [ 
		{
            name: '添加',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, {
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
        }, {
            separator: true
        }, {
            name: '导出',
            bclass: 'excel',
			onpress : action//当点击调用方法
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
                        $(this).attr("name","contactTypeMap["+select+"]."+this.name);
                     });
                $("#sa"+select).show(); 
                var heis = parent.document.getElementById("mainframe3").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe3").style.height = heis;
                select++;
				break;
				case '修改':
				  if(contactTypeID=='')
				  {
				   alert("请选择！");
				   return;
				  }
				   if($("#"+contactTypeID+" #status").attr("value") == '01'){
				  alert("不可修改");
				  break;
			      }
			     $p = $("tr#"+contactTypeID);
			     if($p.hasClass("check"))
	              {
	              return;
	              }
                 $p.addClass("check");
                 $p.find(':input:gt(0)').each(function(){
                        $(this).attr("name","contactTypeMap["+select+"]."+this.name);
                     });
                  select++;
			   $p = $("tr#"+contactTypeID);
               $p.addClass("check");
			   $p.find("span").addClass("model1");
			   $p.find("input").removeClass("model1");
			   $p.find("s:select").attr("disabled",false);
			   $p.find("select").show();
			   $(this).parent().children("span").show();
			  break;
		    case '全部保存':
		        if(notoken)
			    	{return;}
			    if(select==1)
				     {
					     return;
				     }
			     notoken = 1;
		       var $f = $('#lForm');
               $('#lForm').attr("target","hidden").attr("action",basePath+"ea/contacttype/ea_saveContactType.jspa?pageNumber="+pNumber);
			   document.lForm.submit.click();
			   token=2;
			 break;
			 case '删除':
			     if(contactTypeID=='')
				  {
				   alert("请选择！");
				   return;
				  }
				    if(contactTypeID.substring(0,2) == "sa")
				  {	    
				      $("#"+contactTypeID).remove();
				     contactTypeID="";		     
				     return;
				  }
				  $f = $('#lForm');
				var url= basePath+"ea/contacttype/ea_delContactType.jspa?pageNumber="+pNumber+"&contactType.contactTypeID="+contactTypeID;
			    if (confirm("是否删除？")){
				$f.attr("target","hidden").attr("action",url);
				document.lForm.submit.click();
				$("tr#"+contactTypeID).remove();
				contactTypeID="";
				token=11;
			 }
				break;
			case '导出':
				var url = basePath+"ea/contacttype/ea_showContactTypeExcel.jspa?contactType.ccompanyID="+ccompanyID;
				open(url);
				break;
		   case '设置每页显示条数':
			   	var url = basePath
						+ "ea/contacttype/ea_getContactTypeList.jspa?contactType.ccompanyID="
						+ ccompanyID;
				numback(url);
				break; 
        }
    } 
	 $(".staffappraisal tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    contactTypeID =this.id;
                });                  
    $(".staffappraisal tr[id]").dblclick(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    contactTypeID =this.id;
                    action("修改");
                });
     $(".staffappraisal").find("select[id!=xxx]").each(function(){
                    $s = $(this).hide();
                    $o = $("<span/>").text($s.find("option:selected").text());
                    $o.insertAfter($s);
                });
       
       $("#conNum").blur(function(){
       		var num=$.trim($(this).val());
       		var conType=$(this).parent().parent().parent().find("#xxx").val();
       		var aa=0;
       		if(num!=''){
	       		if(conType=="scode201004233ern4m24yx0000000263"){//固定电话号
	       			var cc=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	       			if(!cc.test(num)){
	       				aa=1;
	       			}
	       		}else if(conType=="scode20100426c8rdqacjae0000000002"){//Email
	       			var cc=/^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/;
	       			if(!cc.test(num)){
	       				aa=1;
	       			}
	       		}else if(conType=="scode20100426c8rdqacjae0000000001"){//手机号
	       			var cc=/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	       			if(!cc.test(num)){
	       				aa=1;
	       			}
	       		}
	       		if(aa){
	       			alert("输入联系方式格式不正确!");
	       			notoken = 0;
	       			//$(this).focus();
		            return;
	       		}
       		}
       });
});
function re_load(){
	if(token){
		 document.location.href=basePath+"ea/contacttype/ea_getContactTypeList.jspa?contactType.ccompanyID="+ccompanyID+"&pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
	}
}