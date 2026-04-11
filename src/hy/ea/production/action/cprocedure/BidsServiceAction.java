package hy.ea.production.action.cprocedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.Bidsinformation;
import hy.ea.bo.production.GoodFunction;
import hy.ea.bo.production.InviteBids;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 招标服务
 */
@Controller
@Scope("prototype")
public class BidsServiceAction {
	private static final Logger logger = LoggerFactory.getLogger(BidsServiceAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;


	private PageForm pageForm;
	private String result;
	private int pageNumber;
	private String search;
	private GoodFunction goodFunction;
	private List<BaseBean> list;
	private String fiveClear;
	private Staff staff;
	private String ppid;
	private CashierBills cashierBills;
	private String parameter;
	private InviteBids inviteBids;
	private Bidsinformation bidsinfo;
	private File[] photo;
	private String[] photoFileName;
	

    /**
	 * 
	 * 
	 * 发布需求页面
	 * @return
	 */
	public String findPublishBidPage(){
		
		
		
		
		return "topub";
	}
	
	/**
	 * 
	 * 发布保存
	 * @return
	 */
	public String savePublish(){
		
		if(photo!=null){
			String path = ServletActionContext.getRequest()
					.getSession().getServletContext().getRealPath("/");
			try {
			  String[] imgurls =  fileService.mutiFileUpload(path, photoFileName, photo, "bidsdemand", "/upload_files/bids/"
							+Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			} catch (Exception e) {
				logger.info("保存上传图片失败");
				logger.error("操作异常", e);
			}
		}
		return "success";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public GoodFunction getGoodFunction() {
		return goodFunction;
	}

	public void setGoodFunction(GoodFunction goodFunction) {
		this.goodFunction = goodFunction;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public InviteBids getInviteBids() {
		return inviteBids;
	}

	public void setInviteBids(InviteBids inviteBids) {
		this.inviteBids = inviteBids;
	}

	public Bidsinformation getBidsinfo() {
		return bidsinfo;
	}

	public void setBidsinfo(Bidsinformation bidsinfo) {
		this.bidsinfo = bidsinfo;
	}

	public File[] getPhoto() {
		return photo;
	}

	public void setPhoto(File[] photo) {
		this.photo = photo;
	}
	
	
	/*************************************End发布招标需求************************************************************/
   

	
	 
	
}
