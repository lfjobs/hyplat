package hy.ea.production.action.bsimtest;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.BsimTest;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 模拟测试
 */
@Controller
@Scope("prototype")
public class BsimtestAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;

	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;

	private int pageNumber;
	private int number;
	private String department;
	private BsimTest bsimtest;
	private String bsimTestId;
	private String auditTime;
	private String type;
	private String bsimTestkey;
	private String status;
	private String companyId;
	private String pass;
	private String category;
	private String fiveClear;
	
	private List<BaseBean> list;
	/**
	 * 产品设计实体类
	 */
	private ProductPackaging pt;
	/**
	 * 
	 * 获得session
	 */
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();
	/**
	 * 获得account
	 */
	private CAccount account = (CAccount) ActionContext.getContext()
			.getSession().get("account");

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearchByBsimtest() {
		session.put("tableBsimtest", bsimtest);

		return getBsimtestList();
	}

	/**
	 * 查询表单
	 * 
	 * @return
	 */
	public String getBsimtestList() {
		auditTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		department = (String) session.get("organizationName");
         companyId=account.getCompanyID();
		String hql = " from BsimTest where  companyId = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(companyId);
		if(status != null) {
			hql+=" and status = ?";
			params.add(status);
		}
		if(category!=null&&!"".equals(category)){
			hql+=" and category=?";
			params.add(category);
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			hql+=" and fiveClear=?";
			params.add(fiveClear);
		}
		if (search != null && ("search").equals(search)) {
			bsimtest = (BsimTest) session.get("tableBsimtest");
			if(bsimtest!=null&!"".equals(bsimtest)){
			if (bsimtest.getItemNumber() != null
					&& !"".equals(bsimtest.getItemNumber())) {
				hql += " and  itemNumber like ? ";
				params.add("%" + bsimtest.getItemNumber() + "%");
			}
			if (bsimtest.getGoodName() != null
					&& !"".equals(bsimtest.getGoodName())) {
				hql += " and  goodName like ? ";
				params.add("%" + bsimtest.getGoodName() + "%");
			}

		}
		}
		hql += " order by auditTime desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params.toArray());

		return "bsimtestlist";
	}
	
	public String agreed(){
		String hql = "from BsimTest where bsimTestkey=? ";

		bsimtest = (BsimTest) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { bsimtest.getBsimTestkey() });
		if(pass.equals("合格")){
			return "agreed";
			
		}else{
			
			
			return "agreed";
		}
		
	}
	

	/**
	 * 合格与不合格s
	 * 
	 * @return
	 */
	public String getBsimtestListByStatus() {
		List<BaseBean> list = new ArrayList<BaseBean>();
		BsimTest bt = new BsimTest();
		bt = (BsimTest) baseBeanService.getBeanByKey(BsimTest.class,
				bsimTestkey);
        String hql=" from  ProductPackaging p where  p.ppID = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(bt.getId());
        pt=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, params.toArray());
		if ("02".equals(type)) {
			bt.setStatus("02");
            pt.setProductstate("02");
            bt.setYieldnumber(bsimtest.getYieldnumber());
		} else {
			bt.setStatus("03");
			pt.setProductstate("03");
			bt.setNoyieldnumber(bsimtest.getNoyieldnumber());
		}
        
		bt.setBsimTestId(serverService.getServerID("bsimtest"));
		bt.setAuditoroption(bsimtest.getAuditoroption());
		bt.setOrganizationId((String) session.get("organizationID"));
		bt.setAuditTime(bsimtest.getAuditTime());
		bt.setOrganizationName(bsimtest.getOrganizationName());
		bt.setAuditor(bsimtest.getAuditor());
		bt.setAuditorId(account.getStaffID());
		bt.setCompanyId(account.getCompanyID());
		bt.setCompanyName(account.getCompanyName());
	
		list.add(bt);
		baseBeanService.update(bt);

		return "success";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String exportByBsimtest() {
		String hql = " from BsimTest where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		
		if (status != null && "02".equals(status)) {
			hql += " and status = ?";
			params.add(status);
		} else if (status != null && "03".equals(status)) {
			hql += " and status = ?";
			params.add(status);
		} else {
			hql += " and status = ?";
			params.add(status);
		}
		if (search != null && ("search").equals(search)) {
			bsimtest = (BsimTest) session.get("tableBsimtest");
		if(bsimtest!=null&!"".equals(bsimtest)){
		if (bsimtest.getItemNumber() != null
				&& !"".equals(bsimtest.getIndustryClassification())) {
			hql += " and  itemNumber like ? ";
			params.add("%" + bsimtest.getItemNumber() + "%");
		}
		if (bsimtest.getGoodName() != null
				&& !"".equals(bsimtest.getGoodName())) {
			hql += " and  goodName like ? ";
			params.add("%" + bsimtest.getGoodName() + "%");
		}
		}
		}
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				params.toArray());

		excelStream = excelService.showExcel(BsimTest.columnHeadings(), list);

		String organizationID = (String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"模拟测试管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	/**
	 * 打印预览
	 * 
	 * @return
	 */
	public String toPrintPreviewByBsimtest() {
		String hql = " from BsimTest where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (status != null && "02".equals(status)) {
			hql += " and status = ?";
			params.add(status);
		} else if (status != null && "03".equals(status)) {
			hql += " and status = ?";
			params.add(status);
		} else {
			hql += " and status = ?";
			params.add(status);
		}
		if (search != null && ("search").equals(search)) {
		bsimtest = (BsimTest) session.get("tableBsimtest");
		if(bsimtest!=null&!"".equals(bsimtest)){
		if (bsimtest.getItemNumber() != null
				&& !"".equals(bsimtest.getIndustryClassification())) {
			hql += " and  itemNumber like ? ";
			params.add("%" + bsimtest.getItemNumber() + "%");
		}
		if (bsimtest.getGoodName() != null
				&& !"".equals(bsimtest.getGoodName())) {
			hql += " and  goodName like ? ";
			params.add("%" + bsimtest.getGoodName() + "%");
		}
		}
		}
		hql += " order by auditTime";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		return "printPreview";
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getBsimTestId() {
		return bsimTestId;
	}

	public void setBsimTestId(String bsimTestId) {
		this.bsimTestId = bsimTestId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BsimTest getBsimtest() {
		return bsimtest;
	}

	public void setBsimtest(BsimTest bsimtest) {
		this.bsimtest = bsimtest;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBsimTestkey() {
		return bsimTestkey;
	}

	public void setBsimTestkey(String bsimTestkey) {
		this.bsimTestkey = bsimTestkey;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ProductPackaging getPt() {
		return pt;
	}

	public void setPt(ProductPackaging pt) {
		this.pt = pt;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}


}
