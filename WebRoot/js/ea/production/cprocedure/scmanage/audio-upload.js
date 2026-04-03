$(function(){
	
	 //移除
    $(document).on("click",".up_dele",function(){
       var filepath = $(this).parent().find(".path").val();
       $(this).parent().detach();
        var url = basePath+"ea/scm/sajax_ea_deleteTempFile.jspa";
        $.ajax({
        	url:url,
        	type:"get",
        	dataType:"json",
        	async:true,
        	data:{
        		"materialContent.filepath":filepath
        	},
        	error:function(data){
        		alert("移除文件失败");
        		
        	}
        	
        });
    });
});
  var filechooser = document.getElementById("choose");
  var maxsize = 30000 * 1024;
  filechooser.onchange = function() {
    if (!this.files.length) return;
    var files = Array.prototype.slice.call(this.files);
    if (files.length > 5) {
      alert("最多同时只可上传5个音频");
      return;
    }
    files.forEach(function(file, i) {
      if (!/\/(?:mp3)/i.test(file.type)) return;
      var reader = new FileReader();
      var li = document.createElement("li");
//        获取音频大小
      var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
      li.innerHTML = '<i class="up_dele"></i><i class="audio_ico"></i><div class="progress"><span></span></div><div class="size">' + size + '</div>';
      $(".add_photo").before($(li));
      reader.onload = function() {
        var result = this.result;
//        var img = new Image();
//        img.src = result;
        
        //如果视频大小小于maxsize，则直接上传
        if (result.length <= maxsize) {
//          img = null;
          upload(result, file.type, $(li));
          return;
        }else{
            alert('请上传小于'+(~~(10 * maxsize / 1024 / 1024)) / 10+'M的文件')
        }
      };
      reader.readAsDataURL(file);
    })
  };
 
  //    音频上传，将base64的视频转成二进制对象，塞进formdata上传
  function upload(basestr, type, $li) {
    var text = window.atob(basestr.split(",")[1]);
    var buffer = new Uint8Array(text.length);
    var pecent = 0, loop = null;
    for (var i = 0; i < text.length; i++) {
      buffer[i] = text.charCodeAt(i);
    }
    var blob = getBlob([buffer], type);
    var xhr = new XMLHttpRequest();
    var formdata = getFormData();
    formdata.append('file', blob);
    formdata.append('fileType', type);
    formdata.append('materialContent.filesize',$li.find(".size").text());
    xhr.open('post', basePath+'/ea/scm/sajax_ea_uploadFile.jspa');
    xhr.onreadystatechange = function() {
    	if (xhr.readyState == 4 && xhr.status == 200) {
            var jsonData = eval("("+JSON.parse(xhr.responseText)+")");
            var text = jsonData.path ? '上传成功' : '上传失败';
            clearInterval(loop);
            //当收到该消息时上传完毕
            $li.find(".progress span")
            $li.find(".progress span").animate({'width': "100%"}, pecent < 95 ? 200 : 0, function() {
              $(this).html(text);
            });
            $li.find(".progress").find(".path").val(jsonData.path);
           
          }
    };
    //数据发送进度，前50%展示该进度
    xhr.upload.addEventListener('progress', function(e) {
      if (loop) return;
      pecent = ~~(100 * e.loaded / e.total) / 2;
      $li.find(".progress span").css('width', pecent + "%");
      if (pecent == 50) {
        mockProgress();
      }
    }, false);
    //数据后50%用模拟进度
    function mockProgress() {
      if (loop) return;
      loop = setInterval(function() {
        pecent++;
        $li.find(".progress span").css('width', pecent + "%");
        if (pecent == 99) {
          clearInterval(loop);
        }
      }, 100)
    }
    xhr.send(formdata);
  }
  /**
   * 获取blob对象的兼容性写法
   * @param buffer
   * @param format
   * @returns {*}
   */
  function getBlob(buffer, format) {
    try {
      return new Blob(buffer, {type: format});
    } catch (e) {
      var bb = new (window.BlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder);
      buffer.forEach(function(buf) {
        bb.append(buf);
      });                                                                                                                                                         
      return bb.getBlob(format);
    }
  }
  /**
   * 获取formdata
   */
  function getFormData() {
    var isNeedShim = ~navigator.userAgent.indexOf('Android')
        && ~navigator.vendor.indexOf('Google')
        && !~navigator.userAgent.indexOf('Chrome')
        && navigator.userAgent.match(/AppleWebKit\/(\d+)/).pop() <= 534;
    return isNeedShim ? new FormDataShim() : new FormData()
  }
  /**
   * formdata 补丁, 给不支持formdata上传blob的android机打补丁
   * @constructor
   */
  function FormDataShim() {
    console.warn('using formdata shim');
    var o = this,
        parts = [],
        boundary = Array(21).join('-') + (+new Date() * (1e16 * Math.random())).toString(36),
        oldSend = XMLHttpRequest.prototype.send;
    this.append = function(name, value, filename) {
      parts.push('--' + boundary + '\r\nContent-Disposition: form-data; name="' + name + '"');
      if (value instanceof Blob) {
        parts.push('; filename="' + (filename || 'blob') + '"\r\nContent-Type: ' + value.type + '\r\n\r\n');
        parts.push(value);
      }
      else {
        parts.push('\r\n\r\n' + value);
      }
      parts.push('\r\n');
    };
    // Override XHR send()
    XMLHttpRequest.prototype.send = function(val) {
      var fr,
          data,
          oXHR = this;
      if (val === o) {
        // Append the final boundary string
        parts.push('--' + boundary + '--\r\n');
        // Create the blob
        data = getBlob(parts);
        // Set up and read the blob into an array to be sent
        fr = new FileReader();
        fr.onload = function() {
          oldSend.call(oXHR, fr.result);
        };
        fr.onerror = function(err) {
          throw err;
        };
        fr.readAsArrayBuffer(data);
        // Set the multipart content type and boudary
        this.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
        XMLHttpRequest.prototype.send = oldSend;
      }
      else {
        oldSend.call(this, val);
      }
    };
  }
  
  
//离开页面

  function leavePage(){
	  if($(".photos_wrap>li").length==1){
			history.go(-1);
			return;
	  }
  	if(confirm("你尚未保存，确定要离开该页面?")){
  	 var url = basePath+"ea/scm/sajax_ea_deleteTempFileByBat.jspa";
       $.ajax({
       	url:url,
       	type:"get",
       	dataType:"json",
       	async:true,
       	success:function(data){
			history.go(-1);
		},
		error : function(data) {
			alert("移除文件失败");

		}
       });
  	}
   }