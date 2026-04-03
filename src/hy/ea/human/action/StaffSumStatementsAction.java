package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.util.statisticaBean;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 在职员工汇总报表
 * 年龄统计
 * 学历统计
 * @author SumInfoAction
 * 
 */
@Controller
@Scope("prototype")
public class StaffSumStatementsAction {
	@Resource
	private BaseBeanService baseBeanService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private List<String> headerList = new ArrayList<String>();
	private String actionName;// 信息列表方法名称
	private String actionNameExcel;// 信息导出方法名称
	private String basicInfo;// 单位人员信息名称
	private String feildName;
	private String conditions;
	private JFreeChart chart;
	private List<List<BaseBean>> lists;// 数据统计信息
	/**
	 * 类别信息
	 * 
	 * @return
	 * 
	 */
	private String[] informa;
	/**
	 * 行的总数统计（根据类别分组总人数）
	 * 
	 * @return
	 * 
	 */
	private int[] sumInforma;
	/**
	 * 列的总数统计（各部门所有类别人数）
	 * 
	 * @return
	 * 
	 */
	private int[] sumInforma2;
	/**
	 * 行总数,列总数（在职员工总数）
	 * 
	 * @return
	 * 
	 */
	private int sumAll;
	/**
	 * 图形报表标题名称
	 * 
	 * @return
	 * 
	 */
	private String headerMessage;
	/**
	 * 图形报表统计类别信息名称
	 * 
	 * @return
	 * 
	 */
	private String statisticsMessage;

	/**
	 * 取得员工学历统计表信息
	 * 员工学历统计表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getStaffStatements() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		basicInfo = "学历统计表";
		headerMessage = "人事员工学历报表";
		statisticsMessage = "学历信息";
		String sql = "select o.organizationname,o.organizationid,(select count(1) from dtcos cos ,dt_hr_staff s where s.staffid = cos.staffid and s.culturaldegree =? and cos.companyid=? and cos.organizationid=o.organizationid and cos.cosstatus='50' and cos.status='01' ) summan"
				+ " from dtcorganization o where o.companyid=? "
				+ "and o.organizationpid=? and status='00' "
				+ "group by o.organizationname,o.organizationid";
		informa = new String[] { "博士", "硕士", "本科", "专科", "中专", "中专/高中", "中专以下" };
		sumInforma = new int[informa.length];
		lists = new ArrayList<List<BaseBean>>();
		for (int i = 0; i < informa.length; i++) {
			List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(
					sql, new Object[] { informa[i], account.getCompanyID(),
							account.getCompanyID(), account.getCompanyID() });
			Object[] obj = list.toArray();
			int a = 0;
			if (i == 0) {
				sumInforma2 = new int[list.size()];
			}
			for (int j = 0; j < list.size(); j++) {
				Object[] objs = (Object[]) obj[j];
				int ss = Integer.parseInt(objs[2].toString());
				a += ss;
				sumInforma2[j] = sumInforma2[j]
						+ Integer.parseInt(objs[2].toString());
				sumAll += Integer.parseInt(objs[2].toString());
			}
			sumInforma[i] = a;
			lists.add(list);
		}
		session.put("informa", informa);
		session.put("lists", lists);
		session.put("sumInforma", sumInforma);
		session.put("headerMessage", headerMessage);
		session.put("statisticsMessage", statisticsMessage);
		return "list";

	}

	/**
	 * 取得员工年龄统计表信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getStaffStatementsAge() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		basicInfo = "年龄统计表";
		headerMessage = "人事员工年龄报表";
		statisticsMessage = "年龄信息";
		String sql = "select o.organizationname,o.organizationid,(select count(1) from dtcos cos ,dt_hr_staff s where s.staffid = cos.staffid and to_number(to_char(sysdate,'yyyy'))-to_number(substr(s.birthday,1,4)) between ? and ? and cos.companyid=? and cos.organizationid=o.organizationid and cos.cosstatus='50' and cos.status='01' ) summan"
				+ " from dtcorganization o where o.companyid=? "
				+ "and o.organizationpid=? and status='00' "
				+ "group by o.organizationname,o.organizationid";
		informa = new String[] { "18-25", "26-30", "31-40", "41-50", "51-60",
				"60-100" };
		sumInforma = new int[informa.length];
		lists = new ArrayList<List<BaseBean>>();
		for (int i = 0; i < informa.length; i++) {
			List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(
					sql, new Object[] { informa[i].substring(0, 2),
							informa[i].substring(3, informa[i].length()),
							account.getCompanyID(), account.getCompanyID(),
							account.getCompanyID() });
			Object[] obj = list.toArray();
			int a = 0;
			if (i == 0) {
				sumInforma2 = new int[list.size()];
			}
			for (int j = 0; j < list.size(); j++) {
				Object[] objs = (Object[]) obj[j];
				int ss = Integer.parseInt(objs[2].toString());
				a += ss;
				sumInforma2[j] = sumInforma2[j]
						+ Integer.parseInt(objs[2].toString());
				sumAll += Integer.parseInt(objs[2].toString());
			}
			sumInforma[i] = a;
			lists.add(list);
		}
		session.put("informa", informa);
		session.put("lists", lists);
		session.put("sumInforma", sumInforma);
		session.put("headerMessage", headerMessage);
		session.put("statisticsMessage", statisticsMessage);
		return "list";

	}

	public String getJchart() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String headerMessage = (String) session.get("headerMessage");
		String statisticsMessage = (String) session.get("statisticsMessage");
		chart = ChartFactory
				.createBarChart3D(headerMessage, statisticsMessage, "数量：人",
						getData(), PlotOrientation.VERTICAL, true, false, false);
		statisticaBean.resetPiePlotByzz(chart);

		return "success";
	}

	/**
	 * 
	 * 取得学历信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CategoryDataset getData() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DefaultCategoryDataset defaultData = new DefaultCategoryDataset();
		String[] informa = (String[]) session.get("informa");
		List<List<Object>> lists = (List<List<Object>>) session.get("lists");
		for (int j = 0; j < lists.size(); j++) {
			List<Object> list = lists.get(j);
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				defaultData.setValue(Integer.parseInt(obj[2].toString()),
						informa[j].toString(), obj[0].toString());
			}

		}
		int[] sumInforma = (int[]) session.get("sumInforma");
		for (int i = 0; i < informa.length; i++) {
			defaultData.setValue(sumInforma[i], informa[i].toString(), "统计");
		}

		return defaultData;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public List<String> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<String> headerList) {
		this.headerList = headerList;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionNameExcel() {
		return actionNameExcel;
	}

	public void setActionNameExcel(String actionNameExcel) {
		this.actionNameExcel = actionNameExcel;
	}

	public String getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}

	public String getFeildName() {
		return feildName;
	}

	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public List<List<BaseBean>> getLists() {
		return lists;
	}

	public void setLists(List<List<BaseBean>> lists) {
		this.lists = lists;
	}

	public String[] getInforma() {
		return informa;
	}

	public void setInforma(String[] informa) {
		this.informa = informa;
	}

	public int[] getSumInforma() {
		return sumInforma;
	}

	public void setSumInforma(int[] sumInforma) {
		this.sumInforma = sumInforma;
	}

	public int[] getSumInforma2() {
		return sumInforma2;
	}

	public void setSumInforma2(int[] sumInforma2) {
		this.sumInforma2 = sumInforma2;
	}

	public int getSumAll() {
		return sumAll;
	}

	public void setSumAll(int sumAll) {
		this.sumAll = sumAll;
	}

}
