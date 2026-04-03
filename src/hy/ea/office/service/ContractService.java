package hy.ea.office.service;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.office.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface ContractService {
    /**
     * 获取当前驾校全部培训合同
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param companyID
     * @return
     */
    public PageForm getALLFileList(int pageNumber, int pageSize, String parameter, String companyID);

    /**
     * 获取我的文件
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param staffID
     * @return
     */
    public PageForm getMyFileList(int pageNumber, int pageSize, String parameter, String staffID, String module, String companyID, String state);

    /**
     * 待审批，待盖章，待发布，待阅读
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param staffID
     * @return
     */
    public PageForm getDealFileList(int pageNumber, int pageSize, String parameter, String staffID, String companyID, String module, String sccid);

    public PageForm getDealFileByState(int pageNumber, int pageSize, String parameter, String staffID, String companyID, String module, String state);

    /**
     * 文件预览
     *
     * @return
     */
    public String getFilePre(String scene, String companyID, String docID);

    /**
     * 获取签约链接
     *
     * @return
     */
    public String signUrl(String docID, String companyID, String scene);

    /**
     * 获取查看链接
     *
     * @param docID
     * @return
     */
    public String viewUrl(String docID);


    /**
     * 获取价格
     *
     * @param companyID
     * @param sence
     * @return
     */
    public Object getProduct(String companyID, String sence);


    /**
     * 更新合同状态
     *
     * @param docId
     * @return
     */
    public void updateDocState(String docId);


    /**
     * 创建一个Document学员协议，并记录学员的信息
     */
    public void docTempleateParams(CashierBills cashierBills, String staffID, String journalNum, String ppId, String money);


    /**
     * 君子签公司认证
     * * @return
     */
    public void synJzqCompany(String out_request_no, String realpath);

    /**
     * 君子签公司认证结果
     * * @return
     */
    public void searchJzqCompany(String out_request_no);

    /**
     * 根据订单号获取签署的合同
     *
     * @param journalNum
     * @return
     */
    public Document getDocInfo(String journalNum);

    /**
     * 生成pdf
     *
     * @param docId
     * @return
     */
    public String getPdfView(String docId, String realPath);


    /**
     * 生成pdf
     *
     * @param tempId
     * @return
     */
    public String getPdfTempView(String templateId, String realPath);

    /**
     * 改变合同状态
     *
     * @param docId
     * @return
     */
    public String updateState(String docId);

    /**
     * 创建文件
     *
     * @param staffID
     * @param companyID
     * @param receiptType
     * @return
     */
    public String createDoc(String realpath, String staffID, String companyID, String receiptType);


    /**
     * 获取下载地址
     *
     * @param docId
     * @return
     */
    public String getLoadLink(String docId);


    /**
     * 上传印章
     *
     * @param companyId
     * @param stampname
     * @param stampimgs
     * @return
     */
    public String uploadStamp(String companyId, String stampname, String stampimgs);

    /**
     * 发起签约
     *
     * @param docID
     * @param companyID
     * @param scene
     */
    public void applySign(String docID, String scene);

    /**
     * 查询专岗部门
     *
     * @param staffID
     * @param companyID
     * @return
     */
    public String getCheckOrg(String staffID, String companyID);


    /**
     * 获取签约链接
     *
     * @return
     */
    public String signUrlDoc(String docID, String basePath, String tel, String realpath);


    /**
     * 更新盖章信息
     *
     * @param docId
     * @param path
     * @return
     */
    public String updateSealer(String docId, String path);


    /**
     *
     * 修改状态
     * @param sence
     * @param docId
     */
    public void updateRelate(String sence,String docId,String jump);
    /**
     * 追加签约人
     *
     * @param docId
     */

    public void applySignAdd(String docId);


    public List<Object> getStaffList(String sccid);

    /**
     * 获取模板
     *
     * @param pageNumber
     * @param pageSize
     * @param companyID
     * @param parameter
     * @param fileType
     * @param temptId
     * @return
     */
    public PageForm getDocTemp(int pageNumber, int pageSize, String companyID, String staffID, String parameter, String fileType, String temptId, String module);

    public List<BaseBean> getDocTempTree(String companyID, String staffID, String parameter, String fileType, String temptId, String module, String range);

    /**
     * 添加模板分类
     *
     * @return
     */
    public void addDocTempType(TemplateType templateType, String companyID, String staffID);

    /**
     * 获取模板分类
     *
     * @return
     */
    public PageForm getDocTempTypeList(int pageNumber, int pageSize, String companyID, String staffID, String parameter, String module);

    /**
     * 获取模板分类不分页
     *
     * @return
     */
    public List<BaseBean> getDocTempTypeLists(String parentId, String companyID, String staffID, String parameter, String module);


    /**
     * 添加模板
     *
     * @return
     */
    public void addDocTemp(String companyID, DocumentTemplate documentTemplate, String staffID);

    /**
     * 保存模板排序
     */
    public void saveTempSort(String childrenID);

    /**
     * 保存分类排序
     */
    public void saveCateSort(String childrenID);


    /**
     * 删除模板分类
     *
     * @return
     */
    public String deleteTempType(String tempId);


    //删除模板
    public String deleteTemp(String templateId, String realpath);

    /*

    重新发起
     */
    public String repeatApplySign(String docId, String applyNo, String sealerID, String realpath, String receiveID, String receiveOrgID, String receiveComID, String tel);


    /**
     * 下载视频证据
     *
     * @param docId
     * @return
     */
    public String downVideoURL(String docId, String path);


    /**
     * 添加公文邀请记录
     */
    public String addInventRecord(String docId, String status, String telphone, String staffID);

    /**
     *
     * 注册的时候接收文件
     * @param telphone
     * @return
     */
    public String receiveDoc(String telphone);


    /**
     * 获取档案夹不分页
     *
     * @return
     */
    public List<BaseBean> getArchiveTypeLists(String parentId, String companyID, String staffID, String parameter, String module);


    /**
     * 添加档案盒
     *
     * @return
     */
    public void addArchiveType(ArchiveDocType archiveDocType, String companyID, String staffID);



    /**
     * 删除档案夹
     *
     * @return
     */
    public String deleteArchiveType(String adtId);


    /**
     *
     *
     * 个人归档
     * @param docId
     * @param staffID
     * @param adtId
     * @return
     */
    public String archiveDoc(String docId,String staffID,String companyID,String adtId);


    /**
     *
     * 获取归档为念
     * @param companyID
     * @param staffID
     * @param parameter
     * @param adtId
     * @param module
     * @return
     */
    public List<BaseBean> getArchiveTree(String companyID, String staffID, String parameter, String adtId, String module);


    /**
     * 获取已归档分页
     *
     * @param companyID
     * @param parameter

     * @return
     */
    public PageForm getPageFormArchive(int pageNumber, int pageSize, String companyID, String staffID, String parameter, String adtId, String module);


    /**
     *
     * 查看详情
     * @param adtId
     * @return
     */
    public ArchiveDocType getArchiveDeatil(String adtId);

    /**
     *
     * 转移
     * @param docId
     * @param module
     */
    public void transferDoc(String docId,String module);

    /**
     *
     * 添加发送人记录
     * @param staffID
     * @param companyID
     * @param sstaffID
     * @param sorgID
     * @param scompanyID
     * @return
     */
    public String addRecentContact(String staffID,String companyID,String sstaffID,String sorgID,String scompanyID,String source);

    public void updateRecentContact(String id,String oprState);

    public  List<Object> getRecentContactInfo(String staffID,String source);

    /**
     *
     *
     * @param staffID
     * @param oprState 根据操作查询
     * @return
     */
    public  List<Object> getRecentInfoByOpr(String staffID,String oprState);


    /**
     *
     *
     * 获取学员报名信息
     * @param docId
     * @return
     */
    public CashierBills checkInfo(String docId);


    /**
     *
     *
     * 修改身份信息
     * @param noviceName
     * @param noviceCode
     * @param cashierBillsID
     */
    public void updateInfo(String noviceName,String noviceCode,String reference,String cashierBillsID);




    /**
     *
     * html转PDF返回路径
     */
    public DocumentFileAttach  createAttach(DocRelateOther docRelateOther,String realPath,String basePath,String companyID);


    /**
     *
     * 做一些其余操作
     * @param docRelateOther
     * @return
     */
    public void addOthers(DocRelateOther docRelateOther,String stage,Document document);

    /**
     *
     * 获取未注册用户
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param companyID
     * @return
     */
    public PageForm getWregMember(int pageNumber, int pageSize, String parameter, String companyID);


    /**
     * 自己公司起草的公文
     * * @param companyID
     *
     * @return
     */
    public PageForm getAllFiles(int pageNumber, int pageSize, String companyID, String module, String parameter);
}