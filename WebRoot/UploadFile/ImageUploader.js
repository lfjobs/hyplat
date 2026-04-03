/*
	版权所有 2009-2014 北京天太世统科技有限公司
	保留所有权利
	官方网站：http://www.impf2010.com
	官方博客：暂无
	开发文档：http://www.cnblogs.com/xproer/archive/2011/01/09/1931278.html
	升级日志：http://www.cnblogs.com/xproer/archive/2010/10/06/1844816.html
	示例下载(ASP)：http://yunpan.cn/lk/sVWMU3ftgUKF3
	示例下载(.NET)：http://yunpan.cn/lk/sVW6u7gIPx2VE
	示例下载(JSP)：http://yunpan.cn/lk/sVW6BnPQJtKZm
	示例下载(PHP)：http://yunpan.cn/lk/sVW6MIc3dzfgL
	文档下载：http://yunpan.cn/sV8bHNu2g3SiM
	联系邮箱：1085617561@qq.com
	联系QQ：1085617561
*/

//图片上传对象
function ImageUploader()
{
	var _this = this;
	_this.Com = null;
	_this.Domain = "http://" + document.location.host;
	_this.Config = {
		"EncodeType"		: "GB2312"
		, "Version"			: "2,7,73,11573"
		, "Company"			: "北京天太世统科技有限公司"
		, "License"			: ""
		, "FileFieldName"	: "img"
		, "ThumbWidth"		: "0"//缩略图宽度，必须同时设置缩略图高度
		, "ThumbHeight"		: "0"//缩略图高度，必须同时设置缩略图宽度
		, "ImageUploadType"	: ""//图片上专格式：JPG,PNG,GIF,BMP，留空则按实际图片格式上传。
		, "JpegQuality"		: 100//JPG图片质量。0~100。此数值越小，图片大小越小
		, "FileSizeLimit"	: 0//文件大小限制，以字节为单位。0表示不限制。
		, "Watermark"		: false//是否启用水印
		, "Debug"			: false//是否开启日志
		, "LogFile"			: "c:\\log.txt"//日志文件路径。
		, "Clsid"			: "2025D1D7-921B-49bd-8073-7C64ABFDFFD4"
		, "ProjID"			: "Xproer.ImageUploader"
		, "AppPath"			: "/"	//根目录："/"，子目录："/web/"
		, "CabPath"			: "http://www.impf2010.com/js/cabs/ImageUploader.cab"
		, "PostUrl"			: basePath + "ea/image/ea_ProcessRequest.jspa?"
		//64bit
		, "Clsid64"			: "AF8D049A-BC51-4122-BCD4-AD15FBD7894F"
		, "ProjID64"		: "Xproer.ImageUploader64"
		, "CabPath64"		: "http://www.ncmem.com/products/image-uploader/demo-pre/ImageUploader64.cab"
		//Firefox
		, "CabPathFF"		: "http://www.ncmem.com/products/image-uploader/demo/ImageUploader.xpi"
		, "MimeType"		: "application/npImgUp"
		//Chrome
		, "CabPathChr"		: "http://www.ncmem.com/products/image-uploader/demo/ImageUploader.crx"
		, "MimeTypeChr"		: "application/npImgUp"
		, "CrxName"			: "npImgUp"
	};
	
	//附加信息
	_this.Fields = {
		"UserID": "102"
	};
	
	//检查版本 Win32/Win64/Firefox/Chrome
	var browserName = navigator.userAgent.toLowerCase();
	this.ie = browserName.indexOf("msie") > 0;
	//IE11
	this.ie = this.ie ? this.ie : browserName.search(/(msie\s|trident.*rv:)([\w.]+)/)!=-1;
	this.firefox = browserName.indexOf("firefox") > 0;
	this.chrome = browserName.indexOf("chrome") > 0;
	
	_this.CheckVersion = function()
	{
		//Win64
		if (window.navigator.platform == "Win64")
		{
			_this.Config["CabPath"] = _this.Config["CabPath64"];
			_this.Config["Clsid"] = _this.Config["Clsid64"];

		} //Firefox
		else if ( this.firefox )
		{
			_this.Config["CabPath"] = _this.Config["CabPathFF"];
		}//Chrome
		else if ( this.chrome )
		{
			_this.Config["CabPath"] = _this.Config["CabPathChr"];
			_this.Config["MimeType"] = _this.Config["MimeTypeChr"];
		}
	};
	_this.CheckVersion();
	
	//IE浏览器信息管理对象
	_this.BrowserIE = {
		"Check": function()//检查插件是否已安装
		{
			try
			{
				var com = new ActiveXObject(_this.ActiveX["Uploader"]);
				return true;
			}
			catch (e) { return false; }
		}
		, "GetHtml": function()
		{
			/*
			静态加截控件代码：
			<object id="objImageUploader" classid="clsid:2025D1D7-921B-49bd-8073-7C64ABFDFFD4" codebase="http://www.ncmem.com/ImageUploader.cab#version=2,7,41,55081" width="677" height="500"></object>
			*/
			var acx = '<object id="objImageUploader" classid="clsid:' + _this.Config["Clsid"] + '"';
			acx += ' codebase="' + _this.Config["CabPath"] + '#version=' + _this.Config["Version"] + '" width="677" height="500"></object>';
			return acx;
		}
		, "Init": function()
		{
			_this.Com = document.getElementById("objImageUploader");
			_this.Com.Object 			= _this;
			_this.Com.EncodeType 		= _this.Config["EncodeType"];
			_this.Com.FileSizeLimit 	= _this.Config["FileSizeLimit"];
			_this.Com.JpegQuality 		= _this.Config["JpegQuality"];
			_this.Com.Licensed 			= _this.Config["Company"];
			_this.Com.License 			= _this.Config["License"];
			_this.Com.PostUrl 			= _this.Config["PostUrl"];
			_this.Com.FileFieldName		= _this.Config["FileFieldName"];
			_this.Com.ThumbnailWidth 	= _this.Config["ThumbWidth"];
			_this.Com.ThumbnailHeight 	= _this.Config["ThumbHeight"];
			_this.Com.ImageUploadType 	= _this.Config["ImageUploadType"];
			_this.Com.Watermark 		= _this.Config["Watermark"];
			_this.Com.Debug				= _this.Config["Debug"];
			_this.Com.LogFile			= _this.Config["LogFile"];
			_this.Com.AfterImagesPosted = AfterImagesPosted;
			_this.Com.OnError 			= ImageUploader_OnError;

			//初始化参数
			for (var name in _this.Fields)
			{
				_this.Com.AddField(name, _this.Fields[name]);
			}
		}
		, "SetDefaultFolder": function(folder) { _this.Com.DefaultFolder = folder;}
		, "ClearFields": function() { _this.Com.ClearFields(); }
		, "GetPostedImages": function()//获取已上传的图片列表
		{
			var imgs = _this.Com.GetPostedFiles();
			if (imgs == null) return;
			var strImgs = new Array();

			for (var index = imgs.lbound(1); index <= imgs.ubound(1); index++)
			{
				strImgs.push(imgs.getItem(index));
			}
			return strImgs;
		}
		, "GetResponse": function() { return _this.Com.Response; }
	};
	//FireFox浏览器信息管理对象
	_this.BrowserFF = {
		"Check": function()//检查插件是否已安装
		{
			var mimetype = navigator.mimeTypes[_this.Config["MimeType"]];
			if (mimetype)
			{
				return mimetype.enabledPlugin;
			}
			return false;
		}
		, "Setup": function()//安装插件
		{
			var xpi = new Object();
			xpi["Calendar"] = _this.Config["CabPathFF"];
			InstallTrigger.install(xpi, function(name, result) { });
		}
		, "GetHtml": function()
		{ 
			/*
				静态加截控件代码：
				<embed id="objImageUploader" type="application/npImageUploader" width="677" height="500"/>
			*/
			var acx = '<embed id="objImageUploader" type="' + _this.Config["MimeType"] + '"';
			acx += ' pluginspage="' + _this.Config["CabPathFF"] + '" width="677" height="500"></object>';
			return acx;
		}
		, "Init": function()//初始化控件
		{
			_this.Com = document.getElementById("objImageUploader");
			_this.Com.Object = _this;
			_this.Com.EncodeType 		= _this.Config["EncodeType"];
			_this.Com.FileSizeLimit 	= _this.Config["FileSizeLimit"];
			_this.Com.JpegQuality 		= _this.Config["JpegQuality"];
			_this.Com.Licensed 			= _this.Config["Company"];
			_this.Com.License 			= _this.Config["License"];
			_this.Com.PostUrl 			= _this.Config["PostUrl"];
			_this.Com.FileFieldName 	= _this.Config["FileFieldName"];
			_this.Com.ThumbnailWidth 	= _this.Config["ThumbWidth"];
			_this.Com.ThumbnailHeight 	= _this.Config["ThumbHeight"];
			_this.Com.ImageUploadType 	= _this.Config["ImageUploadType"];
			_this.Com.Watermark 		= _this.Config["Watermark"];
			_this.Com.AfterImagesPosted = AfterImagesPosted;
			_this.Com.OnError 			= ImageUploader_OnError;

			//初始化参数
			for (var name in _this.Fields)
			{
				_this.Com.AddField(name, _this.Fields[name]);
			}
		}
		, "SetDefaultFolder": function(folder) { _this.Com.DefaultFolder = folder;}
		, "ClearFields": function() { _this.Com.ClearFields(); }
		, "GetPostedImages": function()//获取已上传的图片列表
		{
			var imgs = _this.Com.GetPostedFiles();
			return imgs;
		}
		, "GetResponse": function() { return _this.Com.Response; }
	};
	//Chrome浏览器
	_this.BrowserChrome = {
		"Check": function()//检查插件是否已安装
		{
			for (var i = 0, l = navigator.plugins.length; i < l; i++)
			{
				if (navigator.plugins[i].name == _this.Config["CrxName"])
				{
					return true;
				}
			}
			return false;
		}
		, "Setup": function()//安装插件
		{
			document.write('<iframe style="display:none;" src="' + _this.Config["CabPathChr"] + '"></iframe>');
		}
	};
	
	//浏览器环境检查
	_this.Browser = _this.BrowserIE;
	//Firefox
	if ( this.firefox )
	{
		_this.Browser = _this.BrowserFF;
		if (!_this.Browser.Check()) { _this.Browser.Setup(); }
	}//Chrome
	else if (this.chrome )
	{
		_this.BrowserFF.Check = _this.BrowserChrome.Check;
		_this.BrowserFF.Setup = _this.BrowserChrome.Setup;
		_this.Browser = _this.BrowserFF;
		if (!_this.Browser.Check()) { _this.Browser.Setup(); }
	}
	
	//初始化路径
	_this.InitPath = function()
	{
		_this.Domain += _this.Config["AppPath"];
		_this.Config["PostUrl"] = _this.Domain + _this.Config["PostUrl"];
		_this.Config["CabPath"] = _this.Domain + _this.Config["CabPath"];
	};

	_this.Load = function()
	{
		document.write(_this.Browser.GetHtml());
	};

	_this.LoadTo = function(oid)
	{
		$("#" + id).html(_this.Browser.GetHtml());
	};
	
	_this.Load();

	//初始化
	_this.Init = function()
	{
		_this.Browser.Init();
	};
	
	//更新附加字段信息
	_this.UpdateFields = function()
	{
		if (null == _this.Com) return;

		_this.Browser.ClearFields();
		for (var name in _this.Fields)
		{
			_this.Browser.AddField(name, _this.Fields[name]);
		}
	};
	
	//设置默认打开的文件夹
	_this.SetDefaultFolder = function(path)
	{
		_this.Browser.SetDefaultFolder(path);
	};
}

function ImageUploader_OnError(obj)
{
	alert(obj.Browser.GetResponse());
}

//所有图片传输完毕
function AfterImagesPosted(obj)
{
	var imgs = obj.Browser.GetPostedImages();

	$("#msg").html(imgs.join("<br/>"));
	//IE6使用此方法
	//setTimeout('goToUrl()', 1000);
}

//IE6使用此方法
function goToUrl()
{
	window.location.href = basePath+ "ea/image/getOfficeImageManagerList.jspa?";
}