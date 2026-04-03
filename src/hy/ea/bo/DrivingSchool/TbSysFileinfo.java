package hy.ea.bo.DrivingSchool;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息表
 * Created by Administrator on 2017/8/17 0017.
 */

public class TbSysFileinfo implements BaseBean,Serializable {
    private String fileKey;
    private String fileBh; //文件编号
    private String fileName;  //文件名称
    private String mimeType;  //MIME类型
    private Long fileSize;  //文件大小
    private String fileMd5;  //MD5值
    private String serverPath;  //服务器路径
    private String ftpPath;  //FTP路径
    private Date uploadTime;  //上传时间
    private String uploadUser;  //上传用户
    private String mocFileId;  //全国平台返回文件ID
    private String syncType;  //同步类型：1新增；2修改；3删除
    private String syncStatus;  //同步状态：0待同步1同步成功2同步失败
    private String aspFileId;  //省监管平台返回文件ID
    private String aspSyncType;  //省监管平台同步类型
    private String aspSyncStatus;  //省监管平台同步状态

    public TbSysFileinfo() {
    }

    public TbSysFileinfo(String fileKey, String fileBh, String fileName, String mimeType, Long fileSize, String fileMd5, String serverPath, String ftpPath, Date uploadTime, String uploadUser, String mocFileId, String syncType, String syncStatus, String aspFileId, String aspSyncType, String aspSyncStatus) {
        this.fileKey = fileKey;
        this.fileBh = fileBh;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.fileMd5 = fileMd5;
        this.serverPath = serverPath;
        this.ftpPath = ftpPath;
        this.uploadTime = uploadTime;
        this.uploadUser = uploadUser;
        this.mocFileId = mocFileId;
        this.syncType = syncType;
        this.syncStatus = syncStatus;
        this.aspFileId = aspFileId;
        this.aspSyncType = aspSyncType;
        this.aspSyncStatus = aspSyncStatus;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileBh() {
        return fileBh;
    }

    public void setFileBh(String fileBh) {
        this.fileBh = fileBh;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getMocFileId() {
        return mocFileId;
    }

    public void setMocFileId(String mocFileId) {
        this.mocFileId = mocFileId;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getAspFileId() {
        return aspFileId;
    }

    public void setAspFileId(String aspFileId) {
        this.aspFileId = aspFileId;
    }

    public String getAspSyncType() {
        return aspSyncType;
    }

    public void setAspSyncType(String aspSyncType) {
        this.aspSyncType = aspSyncType;
    }

    public String getAspSyncStatus() {
        return aspSyncStatus;
    }

    public void setAspSyncStatus(String aspSyncStatus) {
        this.aspSyncStatus = aspSyncStatus;
    }
}
