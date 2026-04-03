var xmlHttpRequest = null;
/*初始化开始*/ 
function SWFLoad(swfu, settings) {
    swfu.SWFObj = new SWFUpload({
    	upload_url: basePath+"/servlet/FileUpload",
        post_params: settings.post_params,

        file_size_limit: settings.file_size_limit,
        file_types: settings.file_types,
        file_types_description: settings.file_types_description,
        file_upload_limit: settings.file_upload_limit,

        file_queue_error_handler: fileQueueError,
        file_dialog_complete_handler: fileDialogComplete,
        upload_progress_handler: uploadProgress,
        upload_error_handler: uploadError,
        upload_success_handler: settings.upload_success_handler,
        upload_complete_handler: uploadComplete,
        file_dialog_start_handler: fileDialogOpen,
        file_queued_handler:fileQueued,

        button_action: settings.button_action,
        button_cursor: SWFUpload.CURSOR.HAND,
        button_disabled: settings.button_disabled,
        button_image_url: settings.button_image,
        button_text: settings.button_text,
        button_placeholder_id: settings.button_placeholder_id,
        button_width: settings.button_width,
        button_height: settings.button_height,
        button_text_top_padding: settings.button_text_top_padding,
        button_text_left_padding: settings.button_text_left_padding,

        flash_url : basePath+"swfupload/swfupload.swf",	

        custom_settings: settings.custom_settings,
        debug: false
    });
}
/*初始化结束*/

//替代window.onload   解决不能同时使用多个window.onload的错误
function addLoadEvent(func) {
    var oldonload = window.onload;
    if (typeof window.onload != "function") {
        window.onload = func;
    } else {
        window.onload = function() {
            func();
            oldonload();
        }
    }
}

function fileDialogOpen() {
    if (typeof getLimitCount == "function") {
        var count = 0;
        var serverData = document.getElementById(this.customSettings.serverDataId).value;
        if (serverData != "") {
            count = serverData.split(",").length - 1;
        }
        count += getLimitCount();
        this.setFileUploadLimit(count == 0 ? -1 : count);
    }
}

//删除已上传文件
var isOk = true;
function DelPic(fileInfo, swfObj) {
    var url = basePath+"/servlet/FileUpload?cmd=del&f=" + encodeURI(fileInfo) + "&dt=" + new Date();
    //删除指定ServerData值
    var serverData = document.getElementById(swfObj.customSettings.serverDataId).value;
   
    //将删除后的数据存放在页面的Dom节点上
    //var newSData = JSON.stringify(sData);
    document.getElementById(swfObj.customSettings.serverDataId).value = serverData;
    //重新设置异步发送的数据
    swfObj.setPostParams({ 
        path: swfObj.settings.post_params.path,
        fn: swfObj.settings.post_params.fn,
        small: swfObj.settings.post_params.small,
        sw: swfObj.settings.post_params.sw,
        sh: swfObj.settings.post_params.sh,
        wm: swfObj.settings.post_params.wm,
        data: serverData
    });
    //修改SwfUpload已成功数量
    var stats = swfObj.getStats();
    stats.successful_uploads -= 1;
    swfObj.setStats(stats);

    //初始化XmlHttpRequest对象
    xmlHttpRequest = getXMLHttpRequest();
    try {
        xmlHttpRequest.open("get", url, false);
        xmlHttpRequest.onreadystatechange = onReadyStateChange;
        xmlHttpRequest.send(null);
        if (typeof delete_succeed == "function") {
            delete_succeed(fileInfo);
        }
    }
    catch (e) {
        isOk = false;
        alert("初始化XmlHttpRequest出现错误，详细：" + e);
    }
    return isOk;
}

//当选择文件对话框关闭，并且所有选择文件已经处理完成（加入上传队列成功或者失败）时，
//此事件被触发，numFilesSelected是选择的文件数目，numFilesQueued是此次选择的文件中成功加入队列的文件数目。
function fileDialogComplete(numFilesSelected, numFilesQueued) {
    if (numFilesQueued > 0 && typeof dialogOpen == "function") {
    	dialogOpen(this);
    }
}

function fileQueued(file) {
    var self = this;
    if (typeof isDisabled == "function") {
        if (isDisabled()) {
            this.cancelUpload(file.id, false);
            return false;            
        }
    }
    if (self.customSettings.uploadMode == "LIST") {
        var progress = GetFileProgressObject(self.customSettings, file, self.customSettings.upload_target);
        progress.setWait();
        progress.fileProgressWrapper.childNodes[3].childNodes[0].onclick = function() {
            self.cancelUpload(file.id, false);
            removeElement(progress.fileProgressWrapper);
        }
        progress.fileProgressWrapper.childNodes[3].childNodes[0].style.visibility = "visible";
    }
}

//该事件由flash定时触发，提供三个参数分别访问上传文件对象、已上传的字节数，总共的字节数。
//因此可以在这个事件中来定时更新页面中的UI元素，以达到及时显示上传进度的效果。
//正在上传
function uploadProgress(file, bytesLoaded, bytes) {
    try {
        var percent = Math.ceil((bytesLoaded / bytes) * 100);
        //获取设置的上传模式，动态更新上传进度
        var progress = GetFileProgressObject(this.customSettings, file, this.customSettings.upload_target);
        progress.setProgress(percent);
        if (percent === 100) {
            progress.toggleCancel(false, this);
        } else {
            progress.toggleCancel(true, this);
        }
    } catch (ex) {
        this.debug(ex);
    }
}

//当文件上传的处理已经完成（这里的完成只是指向目标处理程序发送了Files信息，只管发，不管是否成功接收），
//并且服务端返回了200的HTTP状态时，触发此事件
function uploadSuccess(file, serverData, swfObj) {
    try {
        //最后一次获取设置的上传模式，更新上传进度
    	serverData = serverData.trim();
        var progress = GetFileProgressObject(swfObj.customSettings, file, swfObj.customSettings.upload_target);
        //更新serverData
        document.getElementById(swfObj.customSettings.serverDataId).value += serverData;
        //重新设置异步发送的数据
        swfObj.setPostParams({ 
            path: swfObj.settings.post_params.path,
            fn: swfObj.settings.post_params.fn,
            small: swfObj.settings.post_params.small,
            sw: swfObj.settings.post_params.sw,
            sh: swfObj.settings.post_params.sh,
            wm: swfObj.settings.post_params.wm,
            data: serverData
        });
        var isSmall = swfObj.settings.post_params.small;
        var serverData = serverData.substr(0,serverData.lastIndexOf(','))
        //反序列化
        var dataList = serverData.split(",");
        var fileInfo = "";
        if (dataList.length > 0) {
            fileInfo = dataList[dataList.length - 1];
        }
        
        var isShow = true;
        if (typeof single_succeed == "function") {
            isShow = single_succeed(fileInfo);
        }
        if (!isShow) {
            progress.fileProgressWrapper.parentNode.removeChild(progress.fileProgressWrapper);
        }
        progress.fileProgressWrapper.setAttribute("fileId", file.id);
        
        progress.fileProgressWrapper.childNodes[0].childNodes[0].href = "javascript:";
        //如果为图片类型   则可使用图片预览功能
        if (CustomSettingsInfo.isImg(file.type)) {
            //计算图片尺寸
            //var width = fileInfo.Width;
            //var height = fileInfo.Height;
            var newWidth = 200;
            //var newHeight = 0;
            //if (width < 200) { newWidth = width; }
            var filename = fileInfo; //(fileInfo.Path + ((isSmall.toLowerCase() == "true") ? "/s/" : "/b/") + fileInfo.FileName);
            //newHeight = newWidth * height / width;
            if (swfObj.customSettings.uploadMode == "BUTTON") {
                //图片路径
                progress.fileProgressWrapper.childNodes[2].childNodes[0].src = filename;
                //设置图片尺寸
                progress.fileProgressWrapper.childNodes[2].childNodes[0].style.width = newWidth; 
                //progress.fileProgressWrapper.childNodes[2].childNodes[0].height = newHeight;
                //显示or隐藏图片
                progress.fileProgressWrapper.onmouseout = function() {
                    progress.fileProgressWrapper.childNodes[2].style.display = 'none';
                }
                progress.fileProgressWrapper.onmouseover = function() {
                    progress.fileProgressWrapper.childNodes[2].style.display = '';
                }
            } else {
            //图片路径
                progress.fileProgressWrapper.childNodes[0].childNodes[1].childNodes[0].src = filename;
                //设置图片尺寸
                progress.fileProgressWrapper.childNodes[0].childNodes[1].childNodes[0].style.width = newWidth + "px";
                progress.fileProgressWrapper.childNodes[0].childNodes[1].childNodes[0].style.height = "auto";
                //显示or隐藏图片
                progress.fileProgressWrapper.childNodes[0].childNodes[0].onmouseout = function() {
                    progress.fileProgressWrapper.childNodes[0].childNodes[1].style.display = 'none';
                }
                progress.fileProgressWrapper.childNodes[0].childNodes[0].onmouseover = function() {
                    progress.fileProgressWrapper.childNodes[0].childNodes[1].style.display = '';
                }
            }
        }
        try {
            progress.setStatus("上传成功");
            progress.toggleCancel(false);

            //设置文件大小
            if (setFileSize != undefined) {
                setFileSize(fileInfo["FileSize"]);
            }
        } catch (e) {

        }

        if (swfObj.customSettings.uploadMode == "BUTTON") {
            progress.fileProgressWrapper.childNodes[1].onclick = function() {
                if (confirm("删除后无法恢复,是否继续?")) {
                    DelPic(fileInfo, swfObj);
                    removeElement(progress.fileProgressWrapper);
                }
            }
            progress.fileProgressWrapper.childNodes[1].style.visibility = "visible";
        }
        else {
            progress.fileProgressWrapper.childNodes[3].childNodes[0].onclick = function() {
                if (confirm("删除后无法恢复,是否继续?")) {
                    DelPic(fileInfo, swfObj);
                    removeElement(progress.fileProgressWrapper);
                }
            }
            progress.fileProgressWrapper.childNodes[3].childNodes[0].style.visibility = "visible";
        }
    } catch (ex) {
        this.debug(ex);
    }
}

//删除指定节点下所有节点
function removeElement(_element) {
    var _parentElement = _element.parentNode;
    if (_parentElement) {
        _parentElement.removeChild(_element);
    }
}

//全部文件上传完毕
function uploadComplete(file) {
    try {
        if (this.getStats().files_queued > 0) {
            this.startUpload();
        } else {
            //启用提交按钮
            document.getElementById(this.customSettings.submitBtnId).disabled = false;
            if (typeof upload_completed == "function") {
                upload_completed();
            }
        }
    } catch (ex) {
        this.debug(ex);
    }
}
//message参数表示的是错误的描述。
function uploadError(file, errorCode, message) {
    var progress;
    try {
        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
                try {
                    progress = GetFileProgressObject(this.customSettings, file, this.customSettings.upload_target);
                    progress.setCancelled();
                    progress.setStatus("已取消");
                    progress.toggleCancel(false);
                }
                catch (ex1) {
                    this.debug(ex1);
                }
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
                try {
                    progress = GetFileProgressObject(this.customSettings, file, this.customSettings.upload_target);
                    progress.setCancelled();
                    progress.setStatus("已停止");
                    progress.toggleCancel(true);
                }
                catch (ex2) {
                    this.debug(ex2);
                }
            default:
                alert(message);
                break;
        }
    } catch (ex3) {
        this.debug(ex3);
    }

}
//获取FileProgress对象
function GetFileProgressObject(cSettings, file, targetID) {
    if (cSettings.uploadMode == "BUTTON")
        return new FileProgress_ButtomMode(file, targetID);
    else
        return new FileProgress(file, targetID);
}


/* ******************************************
*	控制对象显示HTML信息
* ****************************************** */
function FileProgress(file, targetID) {
    this.fileProgressID = file.id;
    this.fileProgressWrapper = document.getElementById(this.fileProgressID);
    if (!this.fileProgressWrapper) {

        this.fileProgressWrapper = document.createElement("tr");
        this.fileProgressWrapper.className = "swfupload_li";
        this.fileProgressWrapper.id = this.fileProgressID;

        //文件名
        var fileNameDiv = document.createElement("td");
        fileNameDiv.className = "swfupload_pic_name";
        fileNameDiv.setAttribute('title',file.name );
        var fileNameA = document.createElement("a");
        var names = file.name;
        if(names.length>15){
        	names = names.substring(0,14)+"...";
        }
        fileNameA.appendChild(document.createTextNode(names));

        //缩略图片
        var imgSpan = document.createElement("span");
        imgSpan.style.display = 'none';
        var imgFile = document.createElement("img");
        imgFile.src = "#";
        imgSpan.appendChild(imgFile);
        fileNameDiv.appendChild(fileNameA);
        fileNameDiv.appendChild(imgSpan);

        //上传状态
        var fileSizeDiv = document.createElement("td");
        fileSizeDiv.setAttribute("align","center");
        fileSizeDiv.className = "swfupload_pic_state";
        var fileStateLabel = document.createElement("label");
        fileSizeDiv.appendChild(fileStateLabel);

        //上传状态
        var fileUploadStateDiv = document.createElement("td");
          fileUploadStateDiv.setAttribute("align","center");
        fileUploadStateDiv.className = "swfupload_pic_option";
        var progressBar = document.createElement("div");
        progressBar.className = "swfUpload_progressBarInProgress";
        fileUploadStateDiv.appendChild(progressBar);

        //文件操作
        var deleteDiv = document.createElement("td");
        deleteDiv.setAttribute("align","center");
        deleteDiv.className = "swfupload_pic_percent";
        deleteDiv.id = "swfupload_pic_percent";
        var deleteA = document.createElement("a");
        deleteA.className = "swfupload_pic_del";
        deleteA.href = "javascript:";
        deleteA.style.visibility = "visible";
        deleteA.appendChild(document.createTextNode(" "));
        deleteDiv.appendChild(deleteA);

        //添加节点
        this.fileProgressWrapper.appendChild(fileNameDiv);
        this.fileProgressWrapper.appendChild(fileSizeDiv);
        this.fileProgressWrapper.appendChild(fileUploadStateDiv);
        this.fileProgressWrapper.appendChild(deleteDiv);

        document.getElementById(targetID).appendChild(this.fileProgressWrapper);
    } else {
    }
    this.height = this.fileProgressWrapper.offsetHeight;
}

FileProgress.prototype.addWait = function() { 
    
}

FileProgress.prototype.setProgress = function(percentage) {
    this.fileProgressWrapper.className = "swfUpload_progressContainer swfUpload_blue";
    this.fileProgressWrapper.childNodes[2].childNodes[0].className = "swfUpload_progressBarInProgress";
    this.fileProgressWrapper.childNodes[2].childNodes[0].style.width = percentage + "%";
    this.fileProgressWrapper.childNodes[1].childNodes[0].innerHTML = "已经上传:" + percentage + "%";
};

FileProgress.prototype.setWait = function() {
    this.fileProgressWrapper.className = "swfUpload_progressContainer swfUpload_gray";
    this.fileProgressWrapper.childNodes[2].childNodes[0].className = "swfUpload_wait";
    this.fileProgressWrapper.childNodes[2].childNodes[0].innerHTML = "等待中...";
    this.fileProgressWrapper.childNodes[2].childNodes[0].style.width = "";
    this.fileProgressWrapper.childNodes[1].childNodes[0].innerHTML = "等待上传";
};

FileProgress.prototype.setComplete = function() {
    this.fileProgressWrapper.className = "swfUpload_progressContainer green";
    this.fileProgressWrapper.childNodes[2].childNodes[0].className = "swfUpload_progressBarComplete";
    this.fileProgressWrapper.childNodes[2].childNodes[0].style.width = "";
};

FileProgress.prototype.setError = function() {
    this.fileProgressWrapper.className = "swfUpload_progressContainer swfUpload_red";
    this.fileProgressWrapper.childNodes[2].childNodes[0].className = "swfUpload_progressBarError";
    this.fileProgressWrapper.childNodes[2].childNodes[0].style.width = "";
};

FileProgress.prototype.setCancelled = function() {
    this.fileProgressWrapper.className = "swfUpload_progressContainer";
    this.fileProgressWrapper.childNodes[2].childNodes[0].className = "swfUpload_progressBarError";
    this.fileProgressWrapper.childNodes[2].childNodes[0].style.width = "";
};

FileProgress.prototype.setStatus = function(status) {
    this.fileProgressWrapper.childNodes[1].childNodes[0].innerHTML = status;
};

FileProgress.prototype.toggleCancel = function(show, swfuploadInstance) {
    this.fileProgressWrapper.childNodes[3].childNodes[0].style.visibility = show ? "visible" : "hidden";
    if (swfuploadInstance) {
        var fileID = this.fileProgressID;
        this.fileProgressWrapper.childNodes[3].childNodes[0].onclick = function() {
            swfuploadInstance.cancelUpload(fileID);
            return false;
        };
    }
};


function FileProgress_ButtomMode(file, targetID) {
    this.fileProgressID = file.id;
    this.fileProgressWrapper = document.getElementById(this.fileProgressID);
    if (!this.fileProgressWrapper) {
        //上传状态
        this.fileProgressWrapper = document.createElement("div");
        this.fileProgressWrapper.className = "divFileProgressContainer_BottomMode";
        this.fileProgressWrapper.id = this.fileProgressID;

        var divUpStateTop = document.createElement("div");
        divUpStateTop.className = "swfUpload_progressBarInProgress_BottomMode";
        var divUpState = document.createElement("div");
        var upState = document.createElement("label");
        divUpState.appendChild(upState);
        divUpStateTop.appendChild(divUpState);
        this.fileProgressWrapper.appendChild(divUpStateTop);

        var aDel = document.createElement("a");
        aDel.href = "javascript:";
        aDel.style.visibility = "hidden";
        aDel.alt = aDel.title = "删除：" + file.name;

        aDel.className = "swfupload_pic_del2";
        this.fileProgressWrapper.appendChild(aDel);

        //缩略图片
        var imgSpan = document.createElement("span");
        imgSpan.style.display = 'none';
        imgSpan.className = "swfupload_pic_name";
        var imgFile = document.createElement("img");
        imgFile.src = "#";
        imgSpan.appendChild(imgFile);
        this.fileProgressWrapper.appendChild(imgSpan);

        document.getElementById(targetID).appendChild(this.fileProgressWrapper);

    } else {

    }
}

FileProgress_ButtomMode.prototype.setProgress = function(percentage) {
    this.fileProgressWrapper.childNodes[0].childNodes[0].style.width = percentage + "%";
    this.fileProgressWrapper.childNodes[0].childNodes[0].childNodes[0].innerHTML = percentage + "%";
};
FileProgress_ButtomMode.prototype.setComplete = function() { };
FileProgress_ButtomMode.prototype.setError = function() { };
FileProgress_ButtomMode.prototype.setCancelled = function() { };
FileProgress_ButtomMode.prototype.setStatus = function(status) { };
FileProgress_ButtomMode.prototype.toggleCancel = function(show, swfuploadInstance) {
    //this.fileProgressWrapper.childNodes[1].style.visibility = show ? "visible" : "hidden";
    if (swfuploadInstance) {
        var fileID = this.fileProgressID;
        this.fileProgressWrapper.childNodes[1].onclick = function() {
            swfuploadInstance.cancelUpload(fileID);
            return false;
        };
    }
};




//定义常用属性
var CustomSettingsInfo =
{
    //可预览的图片类型
    imgType: new Array(".jpg", ".gif", ".png", ".bmp", ".jpeg"),
    //判断是否为可预览的图片类型
    isImg: function(currImgType) {
        currImgType = currImgType.toLowerCase();
        for (var i = 0; i < this.imgType.length; i++) {
            if (this.imgType[i] == currImgType) {
                return true;
            }
        }
        return false;
    }
};


/////////////////////////////////////////////////////////////////////////////////////////////////////////
//为异步请求数据提供支持   开始
//请求状态
function onReadyStateChange() {
    try {
        if (xmlHttpRequest.readyState == 4) {
            if (xmlHttpRequest.status == 200) {
                if (xmlHttpRequest.responseText != "删除成功!") {
                    alert(xmlHttpRequest.responseText);
                }
            } else if (xmlHttpRequest.status == 400) {
                alert("找不到异步请求所需处理文件!");
            } else if (xmlHttpRequest.status == 401) {
                alert("未授权客户机访问数据!");
            } else if (xmlHttpRequest.status == 403) {
                alert("服务器拒绝请求!");
            } else if (xmlHttpRequest.status == 500) {
                alert("服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理!");
            } else {
                alert("请求数据失败!");
            }
        }
    }
    catch (e) {
        alert("异步请求失败!详细：" + e);
    }
}
//获取XMLHttpRequest
function getXMLHttpRequest() {
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        var request = new ActiveXObject("Microsoft.XMLHTTP");
        if (!request) {
            request = new ActiveXObject("Msxml2.XHLHTTP");
        }
        return request;
    }
}
/*为异步请求数据提供支持   结束*/

function fileQueueError(file, errorCode, message) {
    try {
        var errorMsg = "";
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                if (message > 0) {
                    errorMsg = "您只能上传" + message + "个文件!";
                } else {
                    errorMsg = "您不可继续上传文件!";
                }
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                errorMsg = "您不可上传0字节的文件";
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                errorMsg = "文件不可大于" + this.settings.file_size_limit;
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
            default:
                alert(message);
                break;
        }
        if (errorMsg !== "") {
            alert(errorMsg);
            return;
        }
    } catch (ex) {
        this.debug(ex);
    }

}