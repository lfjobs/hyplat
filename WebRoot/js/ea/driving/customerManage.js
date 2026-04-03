$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	window.parent.document.getElementById(frameid).style.height = 105 + len * 27 + "px";
	$(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话  
        overlay: 20 // 遮罩程度%  
    }).jqmAddClose('.close');// 添加触发关闭的selector  
                
	$('.staffappraisal').flexigrid({
        height: 200,
        allDouble:true,
        width: 'auto',
        minwidth: 30,
        minheight: 80,
		 buttons: [{
            name: '添加',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },  {
            name: '删除',
            bclass: 'delete',
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改',
            bclass: 'edit',
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
			name : '查询',
			bclass : 'mysearch',
			onpress : action
						// 当点击调用方法
		}, {
			separator : true
		}, {
            name: '全部保存',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, {
            separator: true
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
                    $(this).attr("name","customermap["+select+"]."+this.name);
                });
                $("#sa"+select).find('select').each(function(){
                    $(this).attr("name","customermap["+select+"]."+this.name);
                });
                $("input#start").val('');   
                $("input#end").val('');
                $("#sa"+select).show(); 
                var heis = parent.document.getElementById(frameid).offsetHeight + 27 + "px";
				parent.document.getElementById(frameid).style.height = heis;
                select++;
				break;
			case '删除':
			    if(customerManageID==''){
					alert("请选择！");
					return;
				}
				if (customerManageID.substring(0, 2) == "sa") {
					$("#" + customerManageID).remove();
					customerManageID = "";
					var heis = parent.document.getElementById(frameid).offsetHeight - 27 + "px";
					parent.document.getElementById(frameid).style.height = heis;
					return;
				}
				$f = $('#lForm');
				var url= basePath+"/ea/customermanage/ea_delCustomer.jspa?pageNumber="
					+pNumber+"&customerManage.customerManageID="+customerManageID+"&status1="+status1;
				
			    if (confirm("是否删除？")){
					$f.attr("target","hidden").attr("action",url);
					document.lForm.submit.click();
					$("tr#"+customerManageID).remove();
					customerManageID="";
					token=11;
			 	}
				break;
			case '修改':
				if(customerManageID==''){
				    alert("请选择！");
				    return;
				}
			    $p = $("tr#"+customerManageID);
			    if($p.hasClass("check")){
	              	return;
	            }
                $p.addClass("check");
                $p.find('input:gt(0)').each(function(){
                    $(this).attr("name","customermap["+select+"]."+this.name);
                });
                $p.find('select').each(function(){
                    $(this).attr("name","customermap["+select+"]."+this.name);
                });
                select++;
			    $p = $("tr#"+customerManageID);
                $p.addClass("check");
			    $p.find("span").addClass("model1");
			    $p.find("input").removeClass("model1");
			    $p.find("a").removeClass("model1");
			    $p.find("select").removeClass("model1");
			    $p.find("select").show();
			   
			    $("input#start").val('');   
                $("input#end").val('');
			    $(this).parent().children("span").show();
			    break;
			case '查询' :
		   	  	mainheight = parent.document.getElementById(frameid).offsetHeight;
				parent.document.getElementById(frameid).style.height = 200 + "px";
				$("#jqModelSearch").jqmShow();
				break;
		    case '全部保存':
		        if(notoken){
		        	alert("数据正在提交！");
		        	return;
		        }
			    if(select==1){
					 return;
				}
			    notoken = 1;
                $('#lForm').attr("target","hidden").attr("action",basePath+"/ea/customermanage/ea_saveCustomer.jspa?" 
                		+ "pageNumber=" + pNumber + "&status1="+status1);
			    document.lForm.submit.click();
			    token=2;
			 	break;
		     case '设置每页显示条数':
			   	var url = basePath
						+ "/ea/customermanage/ea_getCustomerList.jspa?frameid="+frameid+"&staffid="
						+ staffid + "&status1=" + status1 +"&search="+search;
				numback(url);
				break; 
        }
    }
    
	$(".selectedname").change(function(){
		var codename = $(this).find("option:selected").text();
		$(this).parent().parent().find("#codeName").val(codename);
	});
    
    $(".close").click(function(){
    	parent.document.getElementById(frameid).style.height = mainheight + "px";
    });
    
    $("input#startDate").blur(function(){
    	$("input#start").val($(this).val());
    });
    
    $("input#endDate").blur(function(){
    	$("input#end").val($(this).val());
    });
    
    // 查询按钮单据事件
	$("#tosearch").click(function(){
		$("#SearchForm").attr("action",
				basePath + "/ea/customermanage/ea_toSearch.jspa?frameid="+frameid+"&staffid="+staffid+"&status1="+status1+"&pageNumber=" + pNumber);
		document.SearchForm.submit.click();
		document.SearchForm.reset();
	});
	
	$(".staffappraisal tr[id]").click(function(){
        $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
        customerManageID =this.id;
    });
    
    $(".staffappraisal tr[id]").dblclick(function(){
        $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
        customerManageID =this.id;
        action("修改");
    });
});

function re_load(){
	if(token==2){
		 document.location.href=basePath+"/ea/customermanage/ea_getCustomerList.jspa?frameid="+frameid+"&staffid="+staffid+"&status1="+status1+"&search="+search+"&pageNumber="+pNumber;
	}
}