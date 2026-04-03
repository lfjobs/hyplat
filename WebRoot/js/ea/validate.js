$(document).ready(function() {
	$(".put3").trigger("blur");
	$("form :input").live("blur", function() {
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".password")) {
			if (pass != 1) {
				$(".password1").attr("value", "");
				$(".password1").parent().find(".error").remove();
				$(".password1").parent().find(".corect").remove();
				$(".password1").parent().find(".tooltip").remove();
			}
			if (inputValue.length < 6 || inputValue.indexOf(' ') != -1) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u8bf7\u8f93\u51656\u4f4d\u4ee5\u4e0a\u7684\u5bc6\u7801</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		// 企业注册全称 companyname
		if ($input.is(".put1")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u8bf7\u586b\u5199\u4f01\u4e1a\u6ce8\u518c\u5168\u79f0</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		// 请输入贵公司经营地址.如：北京市东直门外大街宇飞大厦801 ccompany address
		if ($input.is(".put2")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u8bf7\u8f93\u5165\u8d35\u516c\u53f8\u7ecf\u8425\u5730\u5740.\u5982\uff1a\u5317\u4eac\u5e02\u4e1c\u76f4\u95e8\u5916\u5927\u8857\u5b87\u98de\u5927\u53a6801</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		// not null
		if ($input.is(".put3")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
				return;
			}
			if ($.trim(inputValue) == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不能为空</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
        if ($input.is(".put0")) {
            if (inputValue == "") {
                $parent
                    .append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
                return;
            }
            if ($.trim(inputValue) == "") {
                $parent
                    .append("<span class=\"error\"><a class=\"tex\">不能为空格</a></span>");
                return;
            }
            //不能大于预算数量
			var yusuan=parseInt($("#quantity").text());
            if(parseInt(inputValue)>yusuan){
                $parent
                    .append("<span class=\"error\"><a class=\"tex\">不能大于预算数量</a></span>");
                return;
			}
            $parent
                .append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
        }
		// 请选择
		if ($input.is(".input3")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">请选择</a></span>");
				return;
			}
			if ($.trim(inputValue) == "") {

				$parent
						.append("<span class=\"error\"><a class=\"tex\">请选择</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		// not null
		if ($input.is(".put4")) {
			if (inputValue == "") {
				$input.addClass("newerror");
				return;
			}
			if ($.trim(inputValue) == "") {
				$input.addClass("newerror");
				return;
			}
			$input.removeClass("newerror");
		}
		// 判断是否为时间格式(小时可以为1位格式，如：6：00)
		if ($input.is(".timeformat")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
				return;
			} else if (inputValue != ""
					&& !inputValue
							.match(/^(\d{1}|0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不是时间格式</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		//判断是不是邮箱格式
		if ($input.is(".isemail")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不能为空!</a></span>");
				return;
			} else if (inputValue != ""
					&& !inputValue
							.match(/^\w{3,}@\w+(\.\w+)+$/)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">邮箱格式错误</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		// 判断是否为正数
		if ($input.is(".posnum")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
				return;
			} else if (inputValue != ""
					&& (isNaN(inputValue) || inputValue <= 0)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不是正数</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		//判断不能为负数，可以等于0，可以是小数点的
		if ($input.is(".posnum0")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
				return;
			} else if (inputValue != ""
					&& (isNaN(inputValue) || inputValue < 0)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不是正数</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		// 判断否为正整数
		if ($input.is(".positiveNum")) {
			if (inputValue != ''
					&& (!/^(\+|-)?\d+$/.test(inputValue) || inputValue <= 0)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">只能为正整数</a></span>");
			}
		}
		// 判断否为正整数或者零
		if ($input.is(".positiveNumZ")) {
			if (inputValue != ''
					&& (!/^(\+|-)?\d+$/.test(inputValue) || inputValue < 0)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">只能为零或正整数</a></span>");
			}
		}
		// 给字符串封装获取字符字节数量的方法
		String.prototype.getBytes = function() {
			var sTmpChar;
			var nOriginLen = 0;
			var nStrLength = 0;
			nOriginLen = this.length;
			for (var i = 0; i < nOriginLen; i++) {
				sTmpChar = this.charAt(i);
				// 从第一个字符开始算起
				if (escape(sTmpChar).length > 4) {
					// 如实ASCII大与255的值编码后字符长度变长
					nStrLength += 2;
				} else if (sTmpChar != '\r') {
					nStrLength++;
				}
			}
			return nStrLength;
		};
		//判断输入值是否大于100
		if($input.is(".maxLength")){
			if (inputValue != ''
				&& (!/^(100|[1-9]\d|\d)$/.test(inputValue) || inputValue < 0)) {
			$parent
					.append("<span class=\"error\"><a class=\"tex\">不能大于100</a></span>");
		}
		}
		// 判断字符长度(text length) maxlength 设置的是最大字符数
		if ($input.is(".ckTextLength")) {
			if (inputValue == "")
				return;
			var maxLength = $input.attr("maxlength");
			if (inputValue.getBytes() > maxLength) {
				$parent.append("<span class=\"error\"><a class=\"tex\">限"
						+ (maxLength / 2) + "个字</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		if($input.is(".special")){
			$parent
					.append("<span class=\"error\"><a class=\"tex\">含有特殊字符</a></span>");
		}
		if ($input.is(".isNaN")) {
			if (inputValue == "")
				return;
			if (isNaN(inputValue)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">必须为数字</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		if ($input.is(".chinese")) {
			reg = /^[\u0391-\uFFE5]+$/;
			if (inputValue == "" || !reg.test(inputValue)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">只支持中文</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		// 判断电话
		if ($input.is(".phone")) {
			//reg = /^[0][0-9]{2,3}-[0-9]{5,8}$/;
			reg=/^(([0\+]\d{1,4}-)?(0\d{2,4})-)?(\d{7,8})(-(\d{3,}))?$/;
			if(inputValue != ""&&inputValue!=null){
                if (!reg.test(inputValue)) {
                    $parent
                        .append("<span class=\"error\"><a class=\"tex\">\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u7535\u8bdd\u53f7\u7801\uff0c\u4f8b\u5982\uff1a+86-010-8781234</a></span>");
                    return;
                }
                $parent
                    .append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
            }
		}
		// 判断手机
		if ($input.is(".cellphone")) {
			reg = /^1[0-9]{10}$/;
            if(inputValue != ""&&inputValue!=null){
                if (!reg.test(inputValue)) {
                    $parent
                        .append("<span class=\"error\"><a class=\"tex\">\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u624b\u673a\u53f7\u7801,\u4f8b\u5982\uff1a+86-13012345678</a></span>");
                    return;
                }
                $parent
                    .append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
            }else{
                $parent
                    .append("<span class=\"error\">&nbsp;&nbsp;&nbsp;&nbsp;不能为空</span>");
			}
		}
		
		// 判断大小
		if ($input.is(".ckSize")) {
			var max = $input.attr("max");
			var min =$input.attr("min");
			if ($.trim(inputValue) == "" || inputValue>parseInt(max)||inputValue<parseInt(min)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">大于"+min+",小于"+max+".</a></span>");
				$input.val("");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;</span>");
		}
		// 判断组织名
		if ($input.is(".company")) {
			if (inputValue.indexOf(' ') != -1) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不能有空格</a></span>");
				return;
			}
			if (inputValue != "" && inputValue.length > 3) {
				var url = basePath + "ajax_register_isRegister.jspa?parameter="
						+ inputValue + "&date=" + new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						if (member.isR) {
							$parent
									.append("<span class=\"error\"><a class=\"tex\">\u5df2\u5b58\u5728</a></span>");
							return;
						}
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				return;
			}
			$parent
					.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a\u4e14\u5927\u4e8e4\u4f4d</a></span>");
		}
		
		if ($input.is(".account")) {
			if (inputValue.indexOf(' ') != -1) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不能有空格</a></span>");
				return;
			}
			if (inputValue != "" && inputValue.length > 5) {
				var url = basePath + "ea/wfj/sajax_ea_isRegister.jspa?parameter="
						+ inputValue + "&date=" + new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						if (member.isR) {
							$parent
									.append("<span class=\"error\"><a class=\"tex\">\u5df2\u5b58\u5728</a></span>");
							return;
						}
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				return;
			}
			$parent
					.append("<span class=\"error\"><a class=\"tex\">长度必须大于6位</a></span>");
		}
		
		// 判读总公司账号邮箱
		if ($input.is(".accountEmail")) {
			if (inputValue != "") {
				if ($("#accountEmail").attr("value") == accountEmail) {
					$parent
							.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					return;
				}
				var url = basePath
						+ "ea/caccount/sajax_ea_isAccount.jspa?parameter="
						+ encodeURI(inputValue) + "&date1=" + new Date();
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						if (member.isA) {
							$parent
									.append("<span class=\"error\"><a class=\"tex\">\u5df2\u5b58\u5728</a></span>");
							return;
						}
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				return;
			}
			$parent
					.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
		}
		// 判断子公司账户是否存在
		if ($input.is(".caccountEmail")) {
			if (inputValue != "") {
				if ($("#caccountEmail").attr("value") == accountEmail) {
					$parent
							.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					return;
				}
				var url = basePath
						+ "ea/ccaccount/sajax_ea_isAccount.jspa?parameter="
						+ encodeURI(inputValue) + "&date2=" + new Date();
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						if (member.isA) {
							$parent
									.append("<span class=\"error\"><a class=\"tex\">\u5df2\u5b58\u5728</a></span>");
							return;
						}
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				return;
			}
			$parent
					.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
		}
		// 判断验证码
		if ($input.is(".validate")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不能为空</a></span>");
				return;
			}
			var url1 = basePath + "ajax_register_validate.jspa?parameter="
					+ encodeURI(inputValue) + "&date3=" + new Date();
			$.ajax({
				url : url1,
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					if (member.success) {
						$parent
								.append("<span class=\"error\"><a class=\"tex\">\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u9a8c\u8bc1\u7801</a></span>");
						return;
					}
					$parent
							.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

			return;
		}
		// 判断密码
		if ($input.is(".password1")) {
			b = $(".password").val();

			if (inputValue == "" || !(inputValue == b)) {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">2\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u4e00\u6837</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
		if ($input.is(".email")) {
			reg = /^\w{3,}@\w+(\.\w+)+$/;
			if (reg.test(inputValue)) {
				$parent
						.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
				return;
			}
			if (inputValue != "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u90ae\u7bb1\u683c\u5f0f\u9519\u8bef</a></span>");
				return;
			}
		}
		// 非空后背景变红
		if ($input.is(".notnull")) {
			inputValue=$.trim(inputValue);
			if (inputValue == "" || inputValue.indexOf(' ') != -1) {
				$input.css("background-color", "red");
				$parent
						.append("<span class=\"error\"><a style=\"display:none;\"  class=\"tex\">1</a></span>");
				return;
			}
			$input.addClass("model3");
			$input.css("background-color", "#ffffff");
		}
		
		
		// 判断必须不为空，必须为数字 ，必须为非负数 否则数背景变红
		if ($input.is(".posnumred")) {
			if (inputValue == ""||isNaN(inputValue)||inputValue != ""
				&& (isNaN(inputValue) || inputValue <= 0)) {
				$input.css("background-color", "red");
				$parent
				.append("<span class=\"error\"><a style=\"display:none;\"  class=\"tex\">1</a></span>");
				return;
			}else{
				$input.css("background-color", "#ffffff");
				return;
			}
		
			
		}
		if ($input.is(".IdentityCard")) {
			if (inputValue != "") {
				if ((inputValue.length != 15 && inputValue.length != 18)
						|| inputValue.indexOf(' ') != -1) {
					$input.addClass("newerror");
					alert("身份证位数出错");
					return;
				}
				if (inputValue.length == 15) {
					reg = /^\d{15}$/;
					if (!reg.test(inputValue)) {
						$(this).addClass("newerror");
						alert("该身份证位数必须为15位或者18位");
						return;
					}
				}
				if (inputValue.length == 18) {
					reg = /^\d{17}\w{1}$/;
					if (!reg.test(inputValue)) {
						$input.addClass("newerror");
						alert("该身份证位数必须为15位或者18位");
						return;
					}
				}
				var url = basePath
						+ "/ea/cstaff/sajax_n_ea_IsLawfulIdentityCard.jspa?result="
						+ encodeURI(inputValue) + "&date5=" + new Date();
				if (personIdentityCard != inputValue) {
					staffsize = 0;
				}
				$.ajax({

							url : url,
							type : "get",
							async : false,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var nologin = member.nologin;
								if (nologin) {
									document.location.href = basePath
											+ "page/ea/not_login.jsp";
								}
								if (member.IDCarder > staffsize) {
									$input.addClass("newerror");
									alert("该身份证已被注册");
									return;
								}
								$input.addClass("model3");
								$input.css("background-color", "#ffffff");
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});
			}
		}
		
		if ($input.is(".chip")) {
			if (inputValue != "") {
				if(inputValue!=$("#oldchipid").val()){
				var url = basePath
						+ "/ea/cstaff/sajax_n_ea_IsLawfulChip.jspa?result="
						+ encodeURI(inputValue) + "&date5=" + new Date();
				$.ajax({

							url : url,
							type : "get",
							async : false,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var nologin = member.nologin;
								if (nologin) {
									document.location.href = basePath
											+ "page/ea/not_login.jsp";
								}
								if (member.result=="1") {
									$input.addClass("newerror");
									alert("该芯片已被使用");
									return;
								}
								$input.addClass("model3");
								$input.css("background-color", "#ffffff");
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});
			  }
		   }
		}
		// 安全类别名字不能重复
		if ($input.is(".ckTypeName")) {
			var staffsize = 0;
			if (inputValue != "") {
				var url = bPath
						+ "ea/oasafeKind/sajax_ea_checkTypeName.jspa?parameter="
						+ encodeURI(inputValue) + "&date123=" + new Date();
				$.ajax({

					url : url,
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath
									+ "page/ea/not_login.jsp";
						}
						if (member.size > staffsize) {
							$input.css("background-color", "red");
							$parent
									.append("<span class=\"error\"><a style=\"display:none;\"  class=\"tex\">1</a></span>");
							alert("该类别名称已被注册");
							return;
						}
						$input.addClass("model3");
						$input.css("background-color", "#ffffff");
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
			}
		}
		// 设备编号不能重复
		if ($input.is(".equipmentNumber")) {
			if (inputValue != "") {
				var url = basePath + "ea/carmanage/sajax_ea_verificationfacility.jspa";
				$.ajax({
					url : url,
					type : "post",
					async : false,
					dataType : "json",
					data:{
						"numberOrName":inputValue
					},
					success : function(data) {
						var verification = eval("(" + data + ")");
						if(verification.boolean){
							$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						}else{
							$parent.append("<span class=\"error\"><a class=\"tex\">已存在</a></span>");
						}
					},
					error : function(data) {
						$parent.append("<span class=\"error\"><a class=\"tex\">验证失败</a></span>");
					}
				});
			}else{
				$parent.append("<span class=\"error\"><a class=\"tex\">不能为空</a></span>");
			}
		}
		// 场地编号不能重复
		if ($input.is(".siteNumber")) {
			if (inputValue != "") {
				var url = basePath + "ea/carmanage/sajax_ea_verificationsite.jspa";
				$.ajax({
					url : url,
					type : "post",
					async : false,
					dataType : "json",
					data:{
						"numberOrName":inputValue
					},
					success : function(data) {
						var verification = eval("(" + data + ")");
						if(verification.boolean){
							$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						}else{
							$parent.append("<span class=\"error\"><a class=\"tex\">已存在</a></span>");
						}
					},
					error : function(data) {
						$parent.append("<span class=\"error\"><a class=\"tex\">验证失败</a></span>");
					}
				});
			}else{
				$parent.append("<span class=\"error\"><a class=\"tex\">不能为空</a></span>");
			}
		}
        // 考场编号不能重复
        if ($input.is(".erNumber")) {
            if (inputValue != "") {
                var url = basePath + "ea/carmanage/sajax_ea_verificationerRoom.jspa";
                $.ajax({
                    url : url,
                    type : "post",
                    async : false,
                    dataType : "json",
                    data:{
                        "numberOrName":inputValue
                    },
                    success : function(data) {
                        var verification = eval("(" + data + ")");
                        if(verification.boolean){
                            $parent
                                .append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
                        }else{
                            $parent.append("<span class=\"error\"><a class=\"tex\">已存在</a></span>");
                        }
                    },
                    error : function(data) {
                        $parent.append("<span class=\"error\"><a class=\"tex\">验证失败</a></span>");
                    }
                });
            }else{
                $parent.append("<span class=\"error\"><a class=\"tex\">不能为空</a></span>");
            }
        }
	});
	/**
	 * 非空 只提示 后背景不变色
	 */
	$(".notnull2").change(function() {
		if ($input.attr("value") == "") {
			$parent
					.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
			return;
		}
		$parent
				.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
	});
	// 焦点事件
	$("form :input").bind("focus", function() {
		$input = $(this);
		$parent = $input.parent();
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".company")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u7528\u6237\u540d\u6700\u5c114\u4e2a\u5b57\u7b26\uff0c\u6700\u957f20\u4e2a\u5b57\u7b26</a></span>");
		}
		if ($input.is(".password")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u5bc6\u7801\u987b\u75316\u201420\u4e2a\u82f1\u6587\u5b57\u6bcd\u6216\u6570\u5b57\u7ec4\u6210</a></span>");
		}
		if ($input.is(".password1")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u5bc6\u7801\u987b\u75316\u201420\u4e2a\u82f1\u6587\u5b57\u6bcd\u6216\u6570\u5b57\u7ec4\u6210</a></span>");
		}
		if ($input.is(".put1")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u6ce8\u518c\u4f01\u4e1a\u586b\u5199\u5de5\u5546\u5c40\u6ce8\u518c\u5168\u79f0\u3002</a></span>");
		}
		if ($input.is(".put2")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u8bf7\u8f93\u5165\u8d35\u516c\u53f8\u7ecf\u8425\u5730\u5740.\u5982\uff1a\u5317\u4eac\u5e02\u4e1c\u76f4\u95e8\u5916\u5927\u8857\u5b87\u98de\u5927\u53a6801</a></span>");
		}
		if ($input.is(".put3")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">必须填写</a></span>");
		}if ($input.is(".put0")) {
            $parent
                .append("<span class=\"tooltip\"><a class=\"tex\">必填,不超过预算</a></span>");
        }
		if ($input.is(".timeformat")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">必须是时间格式</a></span>");
		}
		if ($input.is(".posnum")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">必须是正数</a></span>");
		}
		// 判断字符长度(text length) maxlength 设置的是最大字符数
		if ($input.is(".ckTextLength")) {
			var maxLength = $input.attr("maxlength");
			$parent.append("<span class=\"tooltip\"><a class=\"tex\">限"
					+ (maxLength / 2) + "个字</a></span>");
		}
		if ($input.is(".phone")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u7535\u8bdd\u53f7\u7801\uff0c\u4f8b\u5982\uff1a+86-010-8781234</a></span>");
		}
		if($input.is(".cellphone")){
			$parent.append("<span class=\"tooltip\"><a class=\"tex\">\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u624b\u673a\u53f7\u7801,\u4f8b\u5982\uff1a+86-13012345678</a></span>");
		}
		if ($input.is(".validate")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u8f93\u5165\u9a8c\u8bc1\u7801</a></span>");
		}
		if ($input.is(".email")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u53d6\u56de\u5bc6\u7801\u7684\u9014\u5f84\uff0c\u8bf7\u8ba4\u771f\u586b\u5199</a></span>");
		}
		if ($input.is(".isemail")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">填写邮箱格式</a></span>");
		}
		if ($input.is(".companyname")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u8bf7\u8f93\u5165\u7ec4\u7ec7\u540d</a></span>");
		}
		if ($input.is(".username")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u8bf7\u8f93\u5165\u7528\u6237\u540d</a></span>");
		}
		if ($input.is(".peoplename")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">必须为人名格式</a></span>");
		}
		if ($input.is(".pword")) {
			$parent
					.append("<span class=\"tooltip\"><a class=\"tex\">\u8bf7\u8f93\u5165\u5bc6\u7801</a></span>");
		}
	});
});