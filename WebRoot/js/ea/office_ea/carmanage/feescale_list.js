$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						//
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
                document.addForm.reset();
                // $("#fees_").find("#zjprice").attr("disabled",false);
				standard();
				break;
			case '修改' :
				if (goodsID == "") {
					alert('请选择!');
					return;
				}
				updateStandard(goodsID);
				break;
			case '删除' :
				if (goodsID == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
					delstandard(goodsID);
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carmanage/ea_feescale.jspa?search="+"&vf.siteNumber="+$("#siteNumber").val();
				numback(url);
				break;
		}
		//隐藏添加表单
		$(".tit").find("img").click(function(){
			$(".con")[0].reset();  
			$(".site").empty();
			$(".staff").empty();
			$("#fees_").hide();
			$(".siteName").val("");
			$(".siteId").val("");
			$(".staffID").val("");
			$(".staffName").val("");
			$(".CompanyID").val("");
            $(".feecID").val("");
		})
		$(".site").change(function(){
			var $this = $(this).find("option:selected");
			$(".siteId").val($this.attr("date-siteId"));
			$(".siteName").val($this.attr("date-siteName"));
		})
		$(".staff").change(function(){
			var $this = $(this).find("option:selected");
			$(".staffID").val($this.attr("date-staffID"));
			$(".staffName").val($this.attr("date-staffName"));
			$(".CompanyID").val($this.attr("date-CompanyID"));
		})


		//计算金额

		$(".cbprice,.yjprice,.jfprice").change(function(){
			var cbprice = Number($(".cbprice").val());
            var yjprice = Number($(".yjprice").val());
            var jfprice =  Number($(".jfprice").val());

            $(".sysprice").val(cbprice+yjprice+jfprice);
            $(".zjprice").val((Number($(".sysprice").val())*(1+Number(totalPct)/100)).toFixed(2));
		})
       //是否加消费红包
		$(".check").click(function (e) {
			e.stopPropagation();
               if($("#totalPct").attr("checked")==true){
                         $(".zjprice").attr("disabled",true);
                         $(".zjprice").val(0);

			   }else{
                      $(".zjprice").attr("disabled",false);
                   $(".zjprice").val( Number($(".sysprice").val())*(1+Number(totalPct)/100));
               }
        })
       //切换时间制
		$("#fees_ #timeUnits").change(function(){
              if($(this).val()=="1"){
                  $("#fees_  #timeType").parents(".mil").show();
			  }else{
                  $("#fees_  #timeType").parents(".mil").hide();

			  }

		});
	}
	//删除收费标准
	function delstandard(obj){
		var url = basePath + "ea/carmanage/sajax_ea_delStandard.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"feeScale.feecID":obj
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
				if(standard.boolean){
					window.location.reload();
				}else{
					alert("删除失败");
				}
			},
			error : function(data) {
				alert("删除失败");
			}
		});
	}
	
	//查询公司员工以及场地编号
	function standard(){
		var url = basePath + "ea/carmanage/sajax_ea_queryNumberStaffn.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			success : function(data) {
				var standard = eval("(" + data + ")");
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				var number = standard.number;//场地编号
				var list = standard.list;//公司员工
				if (standard!=null) {
					$(".chargeNumber").attr({ readonly: 'true' });
					$(".siteName").attr({ readonly: 'true' });
					
					$(".chargeNumber").val("自动生成");
					var a = [];
					$(number).each(function(i, dom) {
						a.push("<option date-siteId='"+this[1]+"' date-siteName='"+this[2]+"'>"+this[0]+"</option>");
						if(i==0){
							$(".siteId").val(this[1]);
							$(".siteName").val(this[2]);
						}
					});
					$(".site").append(a.join(""));
					
					var b = [];
					$(list).each(function(i, dom) {
						b.push("<option date-staffID='"+this[0]+"' date-staffName='"+this[1]+"' date-companyID='"+this[2]+"'>"+this[1]+"</option>");
						if(i==0){
							$(".staffID").val(this[0]);
							$(".staffName").val(this[1]);
							$(".CompanyID").val(this[2]);
						}
					});
					$(".staff").append(b.join(""));
					
					$("#fees_").show();
				}
			},
			error : function(data) {
				alert("获取失败");
			}
		});
	}
	//查询收费标准,公司员工以及场地编号
	function updateStandard(obj){


        $t = $("#fees_");
        $p = $("tr#" + obj);
        $p.find("span[id]").each(function() {
            $t.find("#" + this.id).val($(this).text());
            if(this.id=="timeUnits"){
                if($t.find("#" + this.id).val()=="1"){
                          $(".btzd").show();
				}else{
                    $(".btzd").hide();
				}
			}

        });

		var suid = $("tr#"+obj).find("#suid").text();
        var staffID = $("tr#"+obj).find("#staffID").text();
        var staffName = $("tr#"+obj).find("#staffName").text();
     //    var isTotalPct =$("tr#"+obj).find("#isTotalPct").text();
     //    if(isTotalPct=="1"){
     //      $t.find("#totalPct").attr("checked",true);
     //        $t.find("#zjprice").attr("disabled",true);
     //        $t.find("#zjprice").val(0);
     // }
		var url = basePath + "ea/carmanage/sajax_ea_queryStandard.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data :{
				"feeScale.feecID":obj,
                "proSetup.suid":suid
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
                var nologin = standard.nologin;
                var setupSublist = standard.setupSublist;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
				if (standard!=null) {
					var feeScale = standard.feeScale;//收费详情
					var number = standard.number;//场地编号
					var list = standard.list;//公司员工
					$(".chargeNumber").attr({ readonly: 'true' });
					$(".siteName").attr({ readonly: 'true' });
					$(".feecID").val(feeScale.feecID);
					$(".chargeNumber").val(feeScale.chargeNumber);
					var a = [];
					$(number).each(function(i, dom) {
						a.push("<option onclick='assignment1(this)' date-siteId='"+this[1]+"' date-siteName='"+this[2]+"'>"+this[0]+"</option>");
						if(i==0){
							$(".siteId").val(this[1]);
							$(".siteName").val(this[2]);
						}
					});
					$(".site").append(a.join(""));
					$(".theFirstHourPrice").val(feeScale.theFirstHourPrice);
					$(".averagePrice").val(feeScale.averagePrice);
					var b = [];
					$(list).each(function(i, dom) {
						b.push("<option onclick='assignment2(this)' date-staffID='"+this[0]+"' value='"+this[0]+"' date-staffName='"+this[1]+"' date-companyID='"+this[2]+"'>"+this[1]+"</option>");
						if(i==0){
							$(".staffID").val(staffID);
							$(".staffName").val(staffName);
							$(".CompanyID").val(this[2]);
						}
					});
					$(".staff").append(b.join(""));
                    $(".staff").val(staffID);
					$("#fees_").show();

                    $(setupSublist).each(function(i, dom) {
                    	var typePpid = this.typePpid;
                    	var amount = this.amount;
                        $(".dlyj").each(function(){
                           $(this).find("#" + typePpid).val(amount);
                        });
                    });

				}
			},
			error : function(data) {
				alert("获取失败");
			}
		});
	}
	//添加修改收费标准
	$(".sub").click(function(){
		$(".posnum").trigger("blur");
		if($(".error").length>0){
			return;
		}
		var site = $(".site").val();
		if(site==""){
			return "您未设置场地,请设置";
		}
		var url = basePath + "ea/carmanage/sajax_ea_addOrUpdatefeescale.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data:$('#con').serialize(),// 你的formid
			success : function(data) {
				var standard = eval("(" + data + ")");
				var checkFee = standard.checkFee;
				if(checkFee) {
                    if (standard.boolean) {
                        window.location.reload();
                    } else {
                        alert("添加失败");
                    }
                }else{
                     alert("同一个场地同一种车型同一种时间单位只能添加一条");
				}
			},
			error : function(data) {
				alert("添加失败");
			}
		});
	})
	
	

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				goodsID = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});

	//like查询场地编号
	$(".input-button").click(function(){
		ppageNumber = 0;
		var url = basePath
		+ "ea/carmanage/ea_feescale.jspa?vf.siteNumber="+$("#siteNumber").val();
		document.location.href = url;
	})
	//启用标准
	$(".start").click(function(){
		var obj = $(this).parents("tr").attr("id")
		var url = basePath + "ea/carmanage/sajax_ea_startUsing.jspa";
        var cr=$(this).parents("tr").find("#startUsing").text();
        var startUsing = "00";
        if(cr=="00"){
            startUsing="01";
		}
        $.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data :{
				"feeScale.feecID":obj,
				"feeScale.startUsing":startUsing
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
                if(standard.boolean){
                    window.location.reload();
                }else{
                    alert("启用失败");
                }
			},
			error : function(data) {
				alert("启用失败");
			}
		});
	})
});
