package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Document implements BaseBean,ExcelBean,java.io.Serializable{
	// public static final Short SHARED=1;
	// public static final Short NOT_SHARED=0;

	// public static final Short HAVE_ATTACH=1;
	// public static final Short NOT_HAVE_ATTACH=0;

	private String key;
	private String docId;
	private String docNum;// 文件
	private String formalNum;// 完整正式编号_字【年】第_号
	private String numTime;
	private String numWord;// 正式编号中的字
	private String numCode;// 正式编号中的号
	private String docName;
	private String title;
	private String theme;
	private Date createTime;// 第一次拟稿发送时间
	private Date updateTime;// 最后一次发送时间（可能多次驳回，再发送）
	private String fileType;
	private String docType;
	private String specificTemplate;
	private String emergencyType;
	private String haveAttach; // 未使用
	private String processInstanceId;
	private String status;
	private String drafterMessageStatus;// 拟稿时是否允许短信提醒：作废
	private Long round; // 审批的当前轮回数 //作废

	private String deptIDofDrafter;// 拟稿人部门
	private String organizationID;// 拟稿人部门
	private String companyID;  //公司Id
	private String drafterID;  //拟稿人Id
	private String drafterComment;
	private String commonComment;// 拟稿人，和传阅人的意见合并；
	private String appendComment;// 用于拟稿人暂时存储意见的；发送以后置空；
	private Date draftTime;// 公文拟稿最新保存时间


	private String archiveSite;//档案地点 暂定公司地址
	private String archiveCustodian;//档案保管人，暂定拟稿人

	private String postName;// 拟稿人岗位名称;数据库木有
	// 当前审批人信息
	private String companyIDofSubscriber;
	private String deptIDofSubscriber;
	private String subscriberID;
	private String subscriberComment;
	private Date subscribeTime; // 驳回、不批准、批准或最近一次转他人审批时间、未盖章时间

	private String companyIDofSealer;
	private String deptIDofSealer;
	private String sealerID;
	private String sealerComment;
	private Date sealTime; // 已盖章时间、未分发时间

	private String companyIDofPublisher;
	private String deptIDofPublisher;
	private String publisherID;
	private String publisherComment;
	private Date publishTime;// 已分发时间、未阅读时间

	private Date readTime;// 已阅读时间

	private Date guidangTime;// 归档时间

	private String companyIDofSubscriber2;// 传阅至的收件人公司
	private String deptIDofSubscriber2;// 传阅至的收件人部门
	private String subscriberID2;// 传阅至的收件人

	private String fromMember;
	private Date passTime;

	private Date startValidity;
	private Date endValidity;
	
	private String tipStatus;//提醒状态 为空：提醒中；00：已取消提醒

	private String social;

	private String receiverID;// 统一接收人ID
	private String receiverDeptID;// 同意接收人部门ID
	private String receiverCompanyID;// 统一接收人公司ID；
	private String receiverName;// 数据库没有

	// 以下为数据库没有字段
	private String drafterName;
	private String deptNameOfDraft;
	private String companyName;
	private String  telphone;//拟稿人电话
	// 当前审批人信息
	private String comNameofSub;
	private String deptNameofSub;
	private String subscriberName;

	private String comNameofSealer;
	private String deptNameofSealer;
	private String sealerName;
	private String subscriberName2;
	private String comNameofPublisher;
	private String deptNameofPublisher;
	private String publisherName;

	private String guidangName;// 归档时间
	private String examineResult; // 审批结果
	private String transfer; // 某个用户是否对该DOCUMENT有转发权限
	private String load; // 某个用户是否对该DOCUMENT有下载权限
	private String print; // 某个用户是否对该DOCUMENT有打印权限
	private String share;// 共享
	private String pub;
	private String module;

	private String sourceOfExamine; // 进入待审批任务的来源。1，first：第一次发送。2，resend：再次发送。3，transfer：他人转发
	private Date timeOfExamine; // 进入待审批任务的时间。
	private static String sortField; // 定义以那个字段排序

    private String groupCompanySn;//公文所属集团；暂时让归档用
	private Set<DocumentFileAttach> attachFiles = new HashSet<DocumentFileAttach>();
	private Set<DocumentAuditing> subscribers = new HashSet<DocumentAuditing>();
	
	//用于企业合同
	private String partyA;//甲方公司
	private String partyB;//乙方公司
	private String partyAName;//甲方公司
	private String partyBName;//乙方公司
	
	
	private String partyAstaff;//甲方人ID
	private String partyBstaff;//乙方人ID
	private String staffIdentityCardA;
	private String staffIdentityCardB;
	
	private String partyAstaffnames;//甲方责任人
	private String partyBstaffnames;//乙方责任人
	
	private String journalNum;//项目凭证号产品ID
	private String projectName;//项目名称产品名称

	private String scene;//场景  00：驾校学员协议  01公文  03:other
	private String applyNo;//签约文件ID
	private String delstatus;//处理数据用
	private String barCode;//自定义生成
	private String pdfUrl;//PDF链接 不管是什么类型的公文，君子签盖章时都要有个PDF个是的文件
     
	
	public static String[] columnHeadings() { 
		String[] titles = { "序号", "公文编号", "文件标题", "主题词", "公文类型","缓急","申报人","申报人部门", 
				"申报单位名称", "合同生效日期", "合同截止日期","甲方公司","甲方责任人","乙方公司","乙方责任人"};
		return titles;
	}
	
	public static String[] columnHeadings1() { 
		String[] titles = { "序号", "公文编号", "文件标题", "主题词", "公文类型","缓急","申报人","申报人部门", 
				"申报单位名称"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {
				docNum,
				title,
				theme,
				docType,
				emergencyType,
				drafterName,
				deptNameOfDraft,
				companyName,
				String.format("%1$tF", startValidity),
				String.format("%1$tF", endValidity),
				partyAName,
				partyAstaffnames,
				partyAName,
				partyBstaffnames,
				};
		return properties;
	}

	public Document(String docId, String title) {
		super();
		this.docId = docId;
		this.title = title;
	}


	public Document(String docId, String title,Date updateTime,String status) {
        this.updateTime  = updateTime;
		this.docId = docId;
		this.title = title;
		this.status = status;
	}
	public Document() {}

	public String getPartyAstaff() {
		return partyAstaff;
	}

	public void setPartyAstaff(String partyAstaff) {
		this.partyAstaff = partyAstaff;
	}

	public String getPartyBstaff() {
		return partyBstaff;
	}

	public void setPartyBstaff(String partyBstaff) {
		this.partyBstaff = partyBstaff;
	}


	public String getPartyA() {
		return partyA;
	}

	public void setPartyA(String partyA) {
		this.partyA = partyA;
	}

	public String getPartyB() {
		return partyB;
	}

	public void setPartyB(String partyB) {
		this.partyB = partyB;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDrafterComment() {
		return drafterComment;
	}

	public void setDrafterComment(String drafterComment) {
		this.drafterComment = drafterComment;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getHaveAttach() {
		return haveAttach;
	}

	public void setHaveAttach(String haveAttach) {
		this.haveAttach = haveAttach;
	}

	public Set<DocumentFileAttach> getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(Set<DocumentFileAttach> attachFiles) {
		this.attachFiles = attachFiles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getEmergencyType() {
		return emergencyType;
	}

	public void setEmergencyType(String emergencyType) {
		this.emergencyType = emergencyType;
	}

	public String getDeptIDofDrafter() {
		return deptIDofDrafter;
	}

	public void setDeptIDofDrafter(String deptIDofDrafter) {
		this.deptIDofDrafter = deptIDofDrafter;
	}

	public String getDrafterID() {
		return drafterID;
	}

	public void setDrafterID(String drafterID) {
		this.drafterID = drafterID;
	}

	public Date getDraftTime() {
		return draftTime;
	}

	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}

	public Date getSealTime() {
		return sealTime;
	}

	public void setSealTime(Date sealTime) {
		this.sealTime = sealTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getDrafterName() {
		return drafterName;
	}

	public void setDrafterName(String drafterName) {
		this.drafterName = drafterName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getComNameofSub() {
		return comNameofSub;
	}

	public void setComNameofSub(String comNameofSub) {
		this.comNameofSub = comNameofSub;
	}

	public String getDeptNameofSub() {
		return deptNameofSub;
	}

	public void setDeptNameofSub(String deptNameofSub) {
		this.deptNameofSub = deptNameofSub;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getCompanyIDofSealer() {
		return companyIDofSealer;
	}

	public void setCompanyIDofSealer(String companyIDofSealer) {
		this.companyIDofSealer = companyIDofSealer;
	}

	public String getDeptIDofSealer() {
		return deptIDofSealer;
	}

	public void setDeptIDofSealer(String deptIDofSealer) {
		this.deptIDofSealer = deptIDofSealer;
	}

	public String getSealerID() {
		return sealerID;
	}

	public void setSealerID(String sealerID) {
		this.sealerID = sealerID;
	}

	public String getSealerComment() {
		return sealerComment;
	}

	public void setSealerComment(String sealerComment) {
		this.sealerComment = sealerComment;
	}

	public String getCompanyIDofPublisher() {
		return companyIDofPublisher;
	}

	public void setCompanyIDofPublisher(String companyIDofPublisher) {
		this.companyIDofPublisher = companyIDofPublisher;
	}

	public String getDeptIDofPublisher() {
		return deptIDofPublisher;
	}

	public void setDeptIDofPublisher(String deptIDofPublisher) {
		this.deptIDofPublisher = deptIDofPublisher;
	}

	public String getPublisherID() {
		return publisherID;
	}

	public void setPublisherID(String publisherID) {
		this.publisherID = publisherID;
	}

	public String getPublisherComment() {
		return publisherComment;
	}

	public void setPublisherComment(String publisherComment) {
		this.publisherComment = publisherComment;
	}

	public String getComNameofSealer() {
		return comNameofSealer;
	}

	public void setComNameofSealer(String comNameofSealer) {
		this.comNameofSealer = comNameofSealer;
	}

	public String getDeptNameofSealer() {
		return deptNameofSealer;
	}

	public void setDeptNameofSealer(String deptNameofSealer) {
		this.deptNameofSealer = deptNameofSealer;
	}

	public String getComNameofPublisher() {
		return comNameofPublisher;
	}

	public void setComNameofPublisher(String comNameofPublisher) {
		this.comNameofPublisher = comNameofPublisher;
	}

	public String getDeptNameofPublisher() {
		return deptNameofPublisher;
	}

	public void setDeptNameofPublisher(String deptNameofPublisher) {
		this.deptNameofPublisher = deptNameofPublisher;
	}

	public String getSealerName() {
		return sealerName;
	}

	public void setSealerName(String sealerName) {
		this.sealerName = sealerName;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getExamineResult() {
		return examineResult;
	}

	public void setExamineResult(String examineResult) {
		this.examineResult = examineResult;
	}

	public String getSpecificTemplate() {
		return specificTemplate;
	}

	public void setSpecificTemplate(String specificTemplate) {
		this.specificTemplate = specificTemplate;
	}

	public String getDrafterMessageStatus() {
		return drafterMessageStatus;
	}

	public void setDrafterMessageStatus(String drafterMessageStatus) {
		this.drafterMessageStatus = drafterMessageStatus;
	}

	public Set<DocumentAuditing> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<DocumentAuditing> subscribers) {
		this.subscribers = subscribers;
	}

	public String getCompanyIDofSubscriber() {
		return companyIDofSubscriber;
	}

	public void setCompanyIDofSubscriber(String companyIDofSubscriber) {
		this.companyIDofSubscriber = companyIDofSubscriber;
	}

	public String getDeptIDofSubscriber() {
		return deptIDofSubscriber;
	}

	public void setDeptIDofSubscriber(String deptIDofSubscriber) {
		this.deptIDofSubscriber = deptIDofSubscriber;
	}

	public String getSubscriberID() {
		return subscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}

	public String getSubscriberComment() {
		return subscriberComment;
	}

	public void setSubscriberComment(String subscriberComment) {
		this.subscriberComment = subscriberComment;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Long getRound() {
		return round;
	}

	public void setRound(Long round) {
		this.round = round;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Date getGuidangTime() {
		return guidangTime;
	}

	public void setGuidangTime(Date guidangTime) {
		this.guidangTime = guidangTime;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getSourceOfExamine() {
		return sourceOfExamine;
	}

	public void setSourceOfExamine(String sourceOfExamine) {
		this.sourceOfExamine = sourceOfExamine;
	}

	public Date getTimeOfExamine() {
		return timeOfExamine;
	}

	public void setTimeOfExamine(Date timeOfExamine) {
		this.timeOfExamine = timeOfExamine;
	}

	public static String getSortField() {
		return sortField;
	}

	public static void setSortField(String sortField) {
		Document.sortField = sortField;
	}

	// public int compareTo(Document doc) {
	// if(sortField.equalsIgnoreCase("unexamine")){
	// if(this.getTimeOfExamine().before(doc.getTimeOfExamine()))
	// return -1;
	// else if(this.getTimeOfExamine().compareTo(doc.getTimeOfExamine())==0)
	// return 0;
	// else
	// return 1;
	// }
	// return 0;
	// }
	//
	// 公文查询信息
	public static class DocumentSearchInfo implements java.io.Serializable{
		private String docType;
		private String status;
		private String title;
		private String theme;
		private String docNum;
		private Date startTime;
		private Date endTime;
		private String fromMember;
		private String sStart;
		private String sEnd;

		private String delposition;// 用于回收站查找删除位置；

		public String getSStart() {
			return sStart;
		}

		public void setSStart(String start) {
			sStart = start;
		}

		public String getSEnd() {
			return sEnd;
		}

		public void setSEnd(String end) {
			sEnd = end;
		}

		public String getDocType() {
			return docType;
		}

		public void setDocType(String docType) {
			this.docType = docType;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public String getDocNum() {
			return docNum;
		}

		public void setDocNum(String docNum) {
			this.docNum = docNum;
		}

		public String getTheme() {
			return theme;
		}

		public void setTheme(String theme) {
			this.theme = theme;
		}

		public String getDelposition() {
			return delposition;
		}

		public void setDelposition(String delposition) {
			this.delposition = delposition;
		}

		public String getFromMember() {
			return fromMember;
		}

		public void setFromMember(String fromMember) {
			this.fromMember = fromMember;
		}
	}

	public String getFormalNum() {
		return formalNum;
	}

	public void setFormalNum(String formalNum) {
		this.formalNum = formalNum;
	}

	public String getNumWord() {
		return numWord;
	}

	public void setNumWord(String numWord) {
		this.numWord = numWord;
	}

	public String getNumCode() {
		return numCode;
	}

	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}

	public String getNumTime() {
		return numTime;
	}

	public void setNumTime(String numTime) {
		this.numTime = numTime;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getCompanyIDofSubscriber2() {
		return companyIDofSubscriber2;
	}

	public void setCompanyIDofSubscriber2(String companyIDofSubscriber2) {
		this.companyIDofSubscriber2 = companyIDofSubscriber2;
	}

	public String getDeptIDofSubscriber2() {
		return deptIDofSubscriber2;
	}

	public void setDeptIDofSubscriber2(String deptIDofSubscriber2) {
		this.deptIDofSubscriber2 = deptIDofSubscriber2;
	}

	public String getSubscriberID2() {
		return subscriberID2;
	}

	public void setSubscriberID2(String subscriberID2) {
		this.subscriberID2 = subscriberID2;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getPub() {
		return pub;
	}

	public void setPub(String pub) {
		this.pub = pub;
	}

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public String getDeptNameOfDraft() {
		return deptNameOfDraft;
	}

	public void setDeptNameOfDraft(String deptNameOfDraft) {
		this.deptNameOfDraft = deptNameOfDraft;
	}

	public Date getStartValidity() {
		return startValidity;
	}

	public void setStartValidity(Date startValidity) {
		this.startValidity = startValidity;
	}

	public Date getEndValidity() {
		return endValidity;
	}

	public void setEndValidity(Date endValidity) {
		this.endValidity = endValidity;
	}

	public String getFromMember() {
		return fromMember;
	}

	public void setFromMember(String fromMember) {
		this.fromMember = fromMember;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverDeptID() {
		return receiverDeptID;
	}

	public void setReceiverDeptID(String receiverDeptID) {
		this.receiverDeptID = receiverDeptID;
	}

	public String getReceiverCompanyID() {
		return receiverCompanyID;
	}

	public void setReceiverCompanyID(String receiverCompanyID) {
		this.receiverCompanyID = receiverCompanyID;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getCommonComment() {
		return commonComment;
	}

	public void setCommonComment(String commonComment) {
		this.commonComment = commonComment;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getAppendComment() {
		return appendComment;
	}

	public void setAppendComment(String appendComment) {
		this.appendComment = appendComment;
	}

	public String getGroupCompanySn() {
		return groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public String getPartyAName() {
		return partyAName;
	}

	public void setPartyAName(String partyAName) {
		this.partyAName = partyAName;
	}

	public String getPartyBName() {
		return partyBName;
	}

	public void setPartyBName(String partyBName) {
		this.partyBName = partyBName;
	}

	public String getPartyAstaffnames() {
		return partyAstaffnames;
	}

	public void setPartyAstaffnames(String partyAstaffnames) {
		this.partyAstaffnames = partyAstaffnames;
	}

	public String getPartyBstaffnames() {
		return partyBstaffnames;
	}

	public void setPartyBstaffnames(String partyBstaffnames) {
		this.partyBstaffnames = partyBstaffnames;
	}

	public String getStaffIdentityCardA() {
		return staffIdentityCardA;
	}

	public void setStaffIdentityCardA(String staffIdentityCardA) {
		this.staffIdentityCardA = staffIdentityCardA;
	}

	public String getStaffIdentityCardB() {
		return staffIdentityCardB;
	}

	public void setStaffIdentityCardB(String staffIdentityCardB) {
		this.staffIdentityCardB = staffIdentityCardB;
	}

	public String getTipStatus() {
		return tipStatus;
	}

	public void setTipStatus(String tipStatus) {
		this.tipStatus = tipStatus;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getDelstatus() {
		return delstatus;
	}

	public void setDelstatus(String delstatus) {
		this.delstatus = delstatus;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public String getArchiveSite() {
		return archiveSite;
	}

	public void setArchiveSite(String archiveSite) {
		this.archiveSite = archiveSite;
	}

	public String getArchiveCustodian() {
		return archiveCustodian;
	}

	public void setArchiveCustodian(String archiveCustodian) {
		this.archiveCustodian = archiveCustodian;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

    public String getSubscriberName2() {
        return subscriberName2;
    }

    public void setSubscriberName2(String subscriberName2) {
        this.subscriberName2 = subscriberName2;
    }

	public String getGuidangName() {
		return guidangName;
	}

	public void setGuidangName(String guidangName) {
		this.guidangName = guidangName;
	}
}
