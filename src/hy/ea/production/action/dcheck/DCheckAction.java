package hy.ea.production.action.dcheck;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.production.DCheck;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;


import javax.annotation.Resource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 考核测试
 */
@Controller
@Scope("prototype")

public class DCheckAction {
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
	private String department;
	private String dcheckTime;
	private String type;
	private String dcheckkey;
	private String status;
	private List<BaseBean> list;
	private String id;
	private int yield;
    private Object base;
    private String show;  //传参进行判断
    private String pass;
    private String category;
	/**
	 * 考核测试
	 */
	private DCheck dcheck;
	/**
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
	public String toSearchByCheck() {
		session.put("tableDcheck", dcheck);
		return getDCheckList();
	}
	/**
	 * 合格率查询
	 * 
	 * @return
	 */
	public String toSearchByYield() {
		session.put("tableDcheck", dcheck);
		return getDCheckyieldList();
	}

	/**
	 * 查询表单
	 * 
	 * @return
	 */
	public String getDCheckList() {
		dcheckTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        department = (String) session.get("organizationName");
		String sql = "  from dt_ProductPackaging p left join production_Ptrack pp" +
				" on pp.id = p.ppid left join production_DCheck d "+
                 "on pp.ptrackeid = d.id where d.status = ? and pp.companyID= ?";
		List<Object> params = new ArrayList<Object>();
	    if (status != null && "01".equals(status)) {
			params.add(status);
		} else if (status != null && "02".equals(status)) {
			params.add(status);
		} else {
			params.add(status);
		}
	    params.add(account.getCompanyID());
			if("01".equals(show)&&!"".equals(show)){
			dcheck = (DCheck) session.get("tableDcheck");
			 if(dcheck!=null&!"".equals(dcheck)){
			if (dcheck.getItemNumber() != null
					&& !"".equals(dcheck.getItemNumber())) {
				sql += " and  d.itemNumber like ? ";
				params.add("%" + dcheck.getItemNumber() + "%");
			}
			if (dcheck.getGoodName() != null
					&& !"".equals(dcheck.getGoodName())) {
				sql += " and  d.goodName like ? ";
				params.add("%" + dcheck.getGoodName() + "%");
			}
			 }
		}
		sql += " order by dcheckTime desc";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber),
				"select d.dcheckkey,p.tradecode,d.itemnumber,p.barcode,d.goodname," +
				"p.standard,p.price ,d.btnumber,d.yieldnumber,d.noyieldnumber," +
				"d.auditor,d.organizationname,d.dchecktime"+sql,"select count(*)"+sql, params.toArray());

		return "dchecklist";
	}
    public String agreed(){
    	   dcheckTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
           department = (String) session.get("organizationName");
	    String sql = "  from dt_ProductPackaging p left join production_Ptrack pp" +
			" on pp.id = p.ppid left join production_DCheck d "+
             "on pp.ptrackeid = d.id where d.status = ? and d.dcheckkey=? ";
	    
	    List<Object> params = new ArrayList<Object>();
	    params.add(status);
	    params.add(dcheckkey);
		 base = baseBeanService.getObjectBySqlAndParams(
				"select d.dcheckkey,p.tradecode,d.itemnumber,p.barcode,d.goodname," +
				"p.standard,p.price ,d.btnumber,d.yieldnumber,d.noyieldnumber," +
				"d.auditor,d.organizationname,d.dchecktime,p.ppid,d.dcheckkey"+sql, params.toArray());
		
		if(pass.equals("合格")){
			return "agreed";
			
		}else{
			
			
			return "agreed";
		}
    	
    }
	/**
	 * 合格率
	 * 
	 * @return
	 */
	public String getDCheckyieldList() {
        
		String sql =" from dt_ProductPackaging p left join production_Ptrack pp " +
				"on pp.id = p.ppid left join production_DCheck d" +
				" on pp.ptrackeid = d.id where status='01' and pp.companyID= ?";
		List<Object> params = new ArrayList<Object>();
		 params.add(account.getCompanyID());
		if("01".equals(show)&&!"".equals(show)){
		dcheck = (DCheck) session.get("tableDcheck");
		if(dcheck!=null&!"".equals(dcheck)){
			if (dcheck.getItemNumber() != null
					&& !"".equals(dcheck.getItemNumber())) {
				sql += " and  d.itemNumber like ? ";
				params.add("%" + dcheck.getItemNumber() + "%");
			}
			if (dcheck.getGoodName() != null
					&& !"".equals(dcheck.getGoodName())) {
				sql += " and  d.goodName like ? ";
				params.add("%" + dcheck.getGoodName() + "%");
			}
			 }
		}
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), "select sum(d.yieldnumber) y,sum(d.btnumber) s,sum(d.yieldnumber)/sum(d.btnumber),d.id, p.tradecode,d.itemnumber,p.barcode,d.goodname," +
						"p.standard,d.btnumber,d.yieldnumber "
				+sql +" group by d.id,p.tradecode,d.itemnumber,p.barcode,d.goodname,p.standard,d.btnumber,d.yieldnumber ","select count(*)"+sql,params.toArray());
		
		return "yieldlist";
	}
	
	
	

	/**
	 * 合格与不合格
	 *
	 * @return
	 */
	public String getDCheckListByStatus() {

		List<BaseBean> list = new ArrayList<BaseBean>();
		DCheck dc= new DCheck();
		dc = (DCheck) baseBeanService.getBeanByKey(DCheck.class, dcheckkey);

		if ("01".equals(type)) {
			dc.setStatus("01");
			dc.setYieldnumber(dcheck.getYieldnumber());
		} else {
			dc.setStatus("02");
			dc.setNoyieldnumber(dc.getBtnumber());
		}
		dc.setDcheckId(serverService.getServerID("dcheck"));
		dc.setOrganizationId((String) session.get("organizationID"));
		dc.setAuditoroption(dcheck.getAuditoroption());
		dc.setOrganizationId((String) session.get("organizationID"));
		dc.setDcheckTime(dcheck.getDcheckTime());
		dc.setOrganizationName(dcheck.getOrganizationName());
		dc.setAuditor(account.getStaffName());
		dc.setAuditorId(account.getStaffID());
		dc.setCompanyId(account.getCompanyID());
		dc.setCompanyName(account.getCompanyName());
		list.add(dc);
		baseBeanService.update(dc);

		return "success";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportByDCheck() {
		String sql = "  from dt_ProductPackaging p left join production_Ptrack pp" +
				" on pp.id = p.ppid left join production_DCheck d "+
                 " on pp.ptrackeid = d.id where d.status = ? and pp.companyID= ?";
		List<Object> params = new ArrayList<Object>();
		if (status != null && "01".equals(status)) {
			params.add(status);
		} else if (status != null && "02".equals(status)) {
			params.add(status);
		} else {
			params.add(status);
		}
		params.add(account.getCompanyID());
		if("01".equals(show)&&!"".equals(show)){
		dcheck = (DCheck) session.get("tableDcheck");
		if(dcheck!=null&!"".equals(dcheck)){
			if (dcheck.getItemNumber() != null
					&& !"".equals(dcheck.getItemNumber())) {
				sql += " and  d.itemNumber like ? ";
				params.add("%" + dcheck.getItemNumber() + "%");
			}
			if (dcheck.getGoodName() != null
					&& !"".equals(dcheck.getGoodName())) {
				sql += " and  d.goodName like ? ";
				params.add("%" + dcheck.getGoodName() + "%");
			}
			 }
		}
		sql += " order by dcheckTime";
		
		list = baseBeanService.getListBeanBySqlAndParams("select p.tradecode,d.itemnumber,p.barcode,d.goodname," +
				"p.standard,p.price ,d.btnumber,d.yieldnumber,d.noyieldnumber," +
				"d.auditor,d.organizationname,d.dchecktime,d.status"+sql, params.toArray());

		excelStream = excelService.showExcel(DCheck.columnHeadings(), list);

		String organizationID = (String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"考核测试管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	/**
	 * 打印预览
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toPrintPreviewByDCheck() {
		String sql = "  from dt_ProductPackaging p left join production_Ptrack pp" +
				" on pp.id = p.ppid left join production_DCheck d "+
                 " on pp.ptrackeid = d.id where d.status = ? and pp.companyID= ?";
		List<Object> params = new ArrayList<Object>();
		if (status != null && "01".equals(status)) {
			
			params.add(status);
		} else if (status != null && "02".equals(status)) {
			params.add(status);
		} else {
			params.add(status);
		}
		params.add(account.getCompanyID());
		if("01".equals(show)&&!"".equals(show)){
		dcheck = (DCheck) session.get("tableDcheck");
		if(dcheck!=null&!"".equals(dcheck)){
			if (dcheck.getItemNumber() != null
					&& !"".equals(dcheck.getItemNumber())) {
				sql += " and  d.itemNumber like ? ";
				params.add("%" + dcheck.getItemNumber() + "%");
			}
			if (dcheck.getGoodName() != null
					&& !"".equals(dcheck.getGoodName())) {
				sql += " and  d.goodName like ? ";
				params.add("%" + dcheck.getGoodName() + "%");
			}
			 }
		}
		sql += " order by dcheckTime";
		list = baseBeanService.getListBeanBySqlAndParams("select p.tradecode,d.itemnumber,p.barcode,d.goodname," +
				"p.standard,p.price ,d.btnumber,d.yieldnumber,d.noyieldnumber," +
				"d.auditor,d.organizationname,d.dchecktime"+sql, params.toArray());
		return "printPreview";
	}
	/**
	 * 导出合格率excel
	 * 
	 * @return
	 */
	public InputStream export(List<Object[]> l) {	
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("First Sheet",0);
			sheet.setColumnView(0,15);//第0列的宽度
			sheet.setColumnView(1,20);//第1列的宽度
			sheet.setColumnView(2,15);//第2列的宽度
			sheet.setColumnView(3,15);//第3列的宽度
			sheet.setColumnView(4,15);//第4列的宽度
			sheet.setColumnView(5,15);//第5列的宽度
			sheet.setColumnView(6,15);//第6列的宽度
			sheet.setColumnView(7,15);//第7列的宽度
			//设置excel里的文本为居中显示
			WritableFont titleWf = new WritableFont(WritableFont.createFont("仿宋_GB2312"),// 字体  
                    10,//WritableFont.DEFAULT_POINT_SIZE,   // 字号  
                    WritableFont.BOLD,                  // 粗体  
                    false,                                 // 斜体  
                    UnderlineStyle.NO_UNDERLINE,            // 下划线  
                    Colour.BLACK,                       // 字体颜色  
                    ScriptStyle.NORMAL_SCRIPT); 
			WritableCellFormat cellFormat=new WritableCellFormat(titleWf); 
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setWrap(true); 
			
			// 创建数字格式化对象实例，并设置为千分位显示
			
			WritableCellFormat numFormat = new WritableCellFormat( new NumberFormat("#,##0.00"));
			numFormat.setAlignment(Alignment.RIGHT);
			sheet.addCell(new Label(0,0,"行业分类",cellFormat));
			sheet.addCell(new Label(1,0,"产品编号",cellFormat));
			sheet.addCell(new Label(2,0,"产品条码",cellFormat));
			sheet.addCell(new Label(3,0,"产品名称",cellFormat));
			sheet.addCell(new Label(4,0,"产品规格",cellFormat));
			sheet.addCell(new Label(5,0,"考核数量",cellFormat));
			sheet.addCell(new Label(6,0,"合格数量",cellFormat));
			sheet.addCell(new Label(7,0,"合格率",cellFormat));		
			for(int i=0;i<l.size();i++){
				Object[] obj=l.get(i);
				for(int r=0;r<obj.length;r++){
					//判断当前列的内容
						sheet.addCell(new Label(r,i+1,obj[r]==null?" ":obj[r].toString(),cellFormat));
				}
			}
			workbook.write();
			workbook.close();
			out.close();
		} catch (Exception e) {
		     
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
	
  @SuppressWarnings("unchecked")
  public String exportByYield(){
	  String sql = "  from dt_ProductPackaging p left join production_Ptrack pp" +
				" on pp.id = p.ppid left join production_DCheck d "+
               "on pp.ptrackeid = d.id where d.status ='01' and pp.companyID= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		if("01".equals(show)&&!"".equals(show)){
		dcheck = (DCheck) session.get("tableDcheck");
		if(dcheck!=null&!"".equals(dcheck)){
			if (dcheck.getItemNumber() != null
					&& !"".equals(dcheck.getItemNumber())) {
				sql += " and  d.itemNumber like ? ";
				params.add("%" + dcheck.getItemNumber() + "%");
			}
			if (dcheck.getGoodName() != null
					&& !"".equals(dcheck.getGoodName())) {
				sql += " and  d.goodName like ? ";
				params.add("%" + dcheck.getGoodName() + "%");
			}
			 }
		}
		sql += " group by p.tradecode,d.itemnumber,p.barcode,d.goodname,p.standard,d.btnumber,d.yieldnumber ";
		List<Object[]> lists = baseBeanService
				.getListBeanBySqlAndParams(
						"select  p.tradecode,d.itemnumber,p.barcode,d.goodname,p.standard,d.btnumber,d.yieldnumber,sum(d.yieldnumber)/sum(d.btnumber) "
								+ sql, params.toArray());
		 int i=0;
         for (Object[] yield : lists) {
		   java.text.NumberFormat a=new DecimalFormat("#%");
		   yield[7]=a.format(yield[7]);
		   lists.set(i, yield);
		   i++;
		};
         
		excelStream =export(lists);

		return "showexcelyield";
	}

	/**
	 * 打印合格率预览
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toPrintPreviewByYield() {
		List<Object> params = new ArrayList<Object>();
		String sql =" from dt_ProductPackaging p left join production_Ptrack pp " +
				"on pp.id = p.ppid left join production_DCheck d" +
				" on pp.ptrackeid = d.id where status='01' and pp.companyID= ?";
		params.add(account.getCompanyID());
		if("01".equals(show)&&!"".equals(show)){
		dcheck = (DCheck) session.get("tableDcheck");
		if(dcheck!=null&!"".equals(dcheck)){
			if (dcheck.getItemNumber() != null
					&& !"".equals(dcheck.getItemNumber())) {
				sql += " and  d.itemNumber like ? ";
				params.add("%" + dcheck.getItemNumber() + "%");
			}
			if (dcheck.getGoodName() != null
					&& !"".equals(dcheck.getGoodName())) {
				sql += " and  d.goodName like ? ";
				params.add("%" + dcheck.getGoodName() + "%");
			}
			 }
		}
		sql += " group by d.id,p.tradecode,d.itemnumber,p.barcode,d.goodname,p.standard,d.btnumber,d.yieldnumber ";
		List<Object[]> lists = baseBeanService
				.getListBeanBySqlAndParams(
						"select  d.id,p.tradecode,d.itemnumber,p.barcode,d.goodname,p.standard,d.btnumber,d.yieldnumber,sum(d.yieldnumber)/sum(d.btnumber) "
								+ sql, params.toArray());
		 int i=0;
         for (Object[] yield : lists) {
		   java.text.NumberFormat a=new DecimalFormat("#%");
		   yield[8]=a.format(yield[8]);
		   lists.set(i, yield);
		   i++;
		};
		session.put("lists", lists);
		return "yieldprintPreview";
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public String getDcheckTime() {
		return dcheckTime;
	}

	public void setDcheckTime(String dcheckTime) {
		this.dcheckTime = dcheckTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DCheck getDcheck() {
		return dcheck;
	}

	public void setDcheck(DCheck dcheck) {
		this.dcheck = dcheck;
	}


	public String getDcheckkey() {
		return dcheckkey;
	}

	public void setDcheckkey(String dcheckkey) {
		this.dcheckkey = dcheckkey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYield() {
		return yield;
	}

	public void setYield(int yield) {
		this.yield = yield;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}


	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Object getBase() {
		return base;
	}
	public void setBase(Object base) {
		this.base = base;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
