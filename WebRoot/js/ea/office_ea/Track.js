$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	window.parent.document.getElementById("mainframe51").style.height = 140 + len * 27 + "px";
	 $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
//                .jqDrag('.drag');// 添加拖拽的selector
	$('.staffappraisal').flexigrid({
        height: 200,
        allDouble:true,
        width: 'auto',
        minwidth: 30,
        title: status=='1'?('咨询跟踪信息----当前单位：' + parent.companyName) :('咨询跟踪信息----当前人员：' + parent.staffName),
        minheight: 80,
		 buttons: [{
            name: '添加',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, {
            name: '修改',
            bclass: 'edit',
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '删除',
            bclass: 'delete',
			onpress : action//当点击调用方法
        },{
            separator: true
        }, {
            name: '全部保存',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '导出',
            bclass: 'excel',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },  {
			name : '查询',
			bclass : 'mysearch',
			onpress : action
						// 当点击调用方法
		}, {
			separator : true
		}, {
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            separator: true
        }]
    });
    function action(com, grid){
        switch (com) {
            case '添加':
            
            	   $("#sa").after($("#sa").clone(true).attr("id","sa"+select).addClass("check"));
                   $("#sa"+select).find('input:gt(0)').each(function(){
                        $(this).attr("name","tracksmap["+select+"]."+this.name);
                   });
                   $("#sa"+select).find('select').each(function(){
                        $(this).attr("name","tracksmap["+select+"]."+this.name);
                   });
               // $("input#start").val('');   
               // $("input#end").val('');
                $("#sa"+select).show(); 
                var heis = parent.document.getElementById("mainframe51").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe51").style.height = heis;
                select++;
				break;
			case '修改':
				  if(trackID==''){
					   alert("请选择！");
					   return;
				  }
			     $p = $("tr#"+trackID);
			     if($p.hasClass("check")){
	              	return;
	             }
                 $p.addClass("check");
                 $p.find('input:gt(0)').each(function(){
                        $(this).attr("name","tracksmap["+select+"]."+this.name);
                 });
                 $p.find('select').each(function(){
                        $(this).attr("name","tracksmap["+select+"]."+this.name);
                 });
                 select++;
			   $p = $("tr#"+trackID);
               $p.addClass("check");
			   $p.find("span").addClass("model1");
			   $p.find("input").removeClass("model1");
			   $p.find("s:select").attr("disabled",false);
			   $p.find("select").show();
			   
			   $("input#start").val('');   
               $("input#end").val('');
			   $(this).parent().children("span").show();
			  break;
		    case '全部保存':
		        if(notoken){
		        	return;
		        }
			    if(select==1){
					 return;
				}
				var err = 0;
				$(".check").find('input.err').each(function(){
					if($(this).val()==''){
						$(this).css("background-color","red");
						err = 1;
					}
				});
				if(err){
					return;
				}
			    notoken = 1;
			    
		        var $f = $('#lForm');
                $('#lForm').attr("target","hidden").attr("action",basePath+"/ea/track/ea_trackSave.jspa?status="+status+"&pageNumber="+pNumber);
			    document.lForm.submit.click();
			    token=2;
			 	break;
			 case '删除':
			    if(trackID==''){
					alert("请选择！");
					return;
				}
				if (trackID.substring(0, 2) == "sa") {
					$("#" + trackID).remove();
					trackID = "";
					var heis = parent.document.getElementById("mainframe51").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe51").style.height = heis;
					return;
				}
				$f = $('#lForm');
				var url= basePath+"/ea/track/ea_delTrack.jspa?pageNumber="+pNumber+"&track.trackID="+trackID;
			    if (confirm("是否删除？")){
					$f.attr("target","hidden").attr("action",url);
					document.lForm.submit.click();
					$("tr#"+trackID).remove();
					trackID="";
					token=11;
			 	}
				break;
			 case '导出' :
				url = basePath + "/ea/track/ea_showExcel.jspa?trackrelationID="+trackrelationID+"&status="+status+"&search=search&sdate=" + sdate + "&edate=" + edate;
				open(url);
				break;
		   	 case '查询' :
		   	  	mainheight = parent.document.getElementById("mainframe51").offsetHeight;
				parent.document.getElementById("mainframe51").style.height = 200 + "px";
				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				$("#SearchForm").find("#serviceMode").val("");
				$("#SearchForm").find("#handleStatus").val("");
				break;
		     case '设置每页显示条数':
			   	var url = basePath
						+ "/ea/track/ea_getTrackById.jspa?foreignKeyID="
						+ foreignKeyID+"&status="+status+"&sdate=" + sdate + "&edate=" + edate+"&trackrelationID="+trackrelationID;
				numback(url);
				break; 
        }
    }
    
    $(".close").click(function(){
    	parent.document.getElementById("mainframe51").style.height = mainheight + "px";
    });
    
    $("input#startDate").blur(function(){
    	$("input#start").val($(this).val());
    });
    
    $("input#endDate").blur(function(){
    	$("input#end").val($(this).val());
    });
    
    // 查询按钮单据事件
	$("#tosearch").click(function() {
		$("#SearchForm").attr("action",
				basePath + "/ea/track/ea_toSearch.jspa?foreignKeyID="+foreignKeyID+"&status="+status+"&pageNumber=" + pNumber+"&trackrelationID="+trackrelationID);
		document.SearchForm.submit.click();
	});
	
	 $(".staffappraisal tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    trackID =this.id;
                });
    $(".staffappraisal tr[id]").dblclick(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    trackID =this.id;
                    action("修改");
                });
});
function re_load(){
	if(token==2){
		 document.location.href=basePath+"/ea/track/ea_getTrackById.jspa?foreignKeyID="+foreignKeyID+"&status="+status+"&search="+search+"&sdate=" + sdate + "&edate=" + edate+"&pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&trackrelationID="+trackrelationID;
	}
}