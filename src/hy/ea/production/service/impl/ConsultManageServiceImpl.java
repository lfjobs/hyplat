package hy.ea.production.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.Company;
import hy.ea.bo.company.ConsultingRegistration;
import hy.ea.bo.human.Staff;
import hy.ea.production.service.ConsultManageService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ConsultManageServiceImpl implements ConsultManageService {

	@Resource
	private BaseBeanDao basebeandao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private MobileMessage msage;
	/**
	 * 根据来源查询已提交的咨询人数
	 * @param ppid
	 * @return
	 */
	public String getCountBySource(String ppid){
		String sql = "select count(ppid) from dtCltRegistration where ppid = ?";
		int count = basebeandao.getConutByBySqlAndParams(sql,new Object[]{ppid});

		return count+150+"";
	}

	/**
	 * 咨询保存
	 * @param consult
	 */

	@Transactional
	@Override
	public void saveConsult(ConsultingRegistration consult){
		 consult.setCrId(serverService.getServerID("crId"));
		TEshopCusCom tEshopCusCom = null;
		consult.setSource("00");
		 if(consult.getCcompanyId()!=null&&!consult.getCcompanyId().equals("")) {
			 String hql = "from Company c where c.companyID = (select m.comanyId from CcomCom m where m.ccompanyId = ?)";
			 Company company = (Company) basebeandao.getBeanByHqlAndParams(hql, new Object[]{consult.getCcompanyId()});
			  if(company!=null) {
				  consult.setCompanyId(company.getCompanyID());
				  consult.setCompanyName(company.getCompanyName());
			  }
			  tEshopCusCom = (TEshopCusCom)basebeandao.getBeanByHqlAndParams("from TEshopCusCom where companyId = ?",new Object[]{consult.getCompanyId()});
		 }else{
			  tEshopCusCom = (TEshopCusCom)basebeandao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?",new Object[]{consult.getStaffId()});

		 }
		 consult.setConsultingDate(new Date());
		 consult.setState("00");
		 consult.setReturnVisit("00");
		 basebeandao.save(consult);
		JSONObject json = new JSONObject();
		json.accumulate("goodsname", consult.getGoodsname()==null?"公司网站":consult.getGoodsname());
		json.accumulate("date",Utilities.getDateString(consult.getConsultingDate(),"yyyy-MM-dd HH:mm:ss"));
		json.accumulate("tel", consult.getConsultantPhone());
		json.accumulate("name", consult.getConsultantName());
		json.accumulate("ppid", consult.getPpid());
		json.accumulate("crId", consult.getCrId());
		 zfMessage(tEshopCusCom, "咨询", json.toString(), "consult",consult);

	}



	private void zfMessage(TEshopCusCom cus, String type, String body, String id,ConsultingRegistration consult) {
		List<String> slist = new ArrayList<String>();//极光推送设备号
		String sql = "select t.contactway  from dt_hr_staff_contact t where t.staffid= ? and t.contactType in (select e.codeID from dtCCode e where  e.codevalue= ? and e.companyid = ?)";
		List list = basebeandao.getListBeanBySqlAndParams(sql, new Object[]{cus.getStaffid(), "短信提醒", cus.getCompanyId()});
		String cellphoneMark = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String obj = list.get(i).toString();
				if (obj != null) {
					cellphoneMark += obj + ",";
				}
			}
		}
		cellphoneMark += cus.getAccount();
		try {
			StringBuilder content = new StringBuilder();
			content.append("有客户咨询,请尽快回访！");
			content.append("咨询来源："+consult.getGoodsname()==null?"公司网站":consult.getGoodsname()+";");
			content.append("咨询客户电话："+consult.getConsultantPhone()+";");
			content.append("咨询客户姓名："+consult.getConsultantName()+";");
			content.append("咨询日期："+Utilities.getDateString(consult.getConsultingDate(),"yyyy-MM-dd HH:mm:ss")+"。");

			msage.setMobiles(cellphoneMark);
			msage.setMessage(content.toString());
			msage.sendMsg("【微分金平台】");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//保存账号
		String[] arr = cellphoneMark.split(",");
		slist = Arrays.asList(arr);
		//极光推送
		JushMain.sendjiguangMessage("有新客户咨询啦，请尽快进行电话回访", type, body, id, slist);

	}
	/**
	 * 验证是否咨询过该项目
	 * @param tel
	 * @param ppid
	 * @return
	 */
	public  String checkIn(String tel,String ppid){
		String hql = "from ConsultingRegistration where ppid = ? and consultantPhone = ?";
		ConsultingRegistration con = (ConsultingRegistration)basebeandao.getBeanByHqlAndParams(hql,new Object[]{ppid,tel});
		if(con==null){
			return "0";
		}else{
			return "1";
		}
	}

	/**
	 *
	 * 回访记录
	 * @param consult
	 * @return
	 */
	@Transactional
	@Override
	public void saveReturnVisit(ConsultingRegistration consult){

            if(consult.getCrId()!=null&&!consult.getCrId().equals("")){

				String hql = "from ConsultingRegistration where crId = ?";
				ConsultingRegistration consultingRegistration = (ConsultingRegistration)basebeandao.getBeanByHqlAndParams(hql,new Object[]{consult.getCrId()});

				if(consult.getIslisten()!=null&&!consult.getIslisten().equals("")) {
					consultingRegistration.setIslisten(consult.getIslisten());
				}
				consultingRegistration.setReturnVisit("01");
                if(consultingRegistration.getVisitContent()!=null) {
					if(consult.getVisitContent()!=null&&!consult.getVisitContent().equals("")) {
						consultingRegistration.setVisitContent(consultingRegistration.getVisitContent() + ";" + consult.getVisitContent());
					}
				}else{
                	if(consult.getVisitContent()!=null&&!consult.getVisitContent().equals("")) {
						consultingRegistration.setVisitContent(consult.getVisitContent());
					}
				}

				  if(consult.getClientType()!=null&&!consult.getClientType().equals("")) {
					  consultingRegistration.setClientType(consult.getClientType());
				  }
				 if(consult.getIsIntentCustomer()!=null&&!consult.getIsIntentCustomer().equals("")) {
					consultingRegistration.setIsIntentCustomer(consult.getIsIntentCustomer());
				 }

				if(consult.getVisitStaffID()!=null&&!consult.getVisitStaffID().equals("")) {
					consultingRegistration.setVisitStaffID(consult.getVisitStaffID());
					String hqlstaff = "from Staff where staffID = ?";
					Staff f   =  (Staff)basebeandao.getBeanByHqlAndParams(hqlstaff,new Object[]{consult.getVisitStaffID()});
					if(f!=null) {
						consultingRegistration.setVisitStaffName(f.getStaffName());
					}
				}
				consultingRegistration.setVisitDate(new Date());
				basebeandao.update(consultingRegistration);
            }

	}

	/**
	 *
	 * 安卓调用
	 * @param start
	 * @param end
	 * @param isIntentCustomer
	 * @param returnVisit
	 * @param companyId
	 * @param staffId
	 * @param parameter
	 * @return
	 */
 	public List<BaseBean> getConsultList(String start,String end,String isIntentCustomer,String returnVisit,String companyId,String staffId,String parameter){
		List<Object> param = new ArrayList<Object>();
 		String hql = "from ConsultingRegistration where 1=1 ";
 		System.out.println(start);
		System.out.println(end);

		if(start!=null&&!start.equals("")&&end!=null&&!end.equals("")){
            hql+= " and consultingDate between ? and ?";
			param.add(Utilities.getDateFromString(start,"yyyy-MM-dd HH:mm:ss"));
			param.add(Utilities.getDateFromString(end,"yyyy-MM-dd HH:mm:ss"));

		}

		if(isIntentCustomer!=null&&!isIntentCustomer.equals("")){
			hql+= " and isIntentCustomer = ?";
			param.add(isIntentCustomer);
		}

		if(returnVisit!=null&&!returnVisit.equals("")){
			hql+= " and returnVisit = ?";
			param.add(returnVisit);
		}
		if(companyId!=null&&!companyId.equals("")){
			hql+= " and companyId = ?";
			param.add(companyId);
		}
		if(staffId!=null&&!staffId.equals("")){
			hql+= " and staffId = ?";
			param.add(staffId);
		}
		if(parameter!=null&&!parameter.equals("")){
			hql+= " and (consultantName like ? or consultantPhone = ?)";
			param.add(parameter);
			param.add(parameter);
		}
 		List<BaseBean> list = basebeandao.getListBeanByHqlAndParams(hql,param.toArray());
		return list;
	}


	/**
	 *
	 * 新版查询客户咨询列表暂时只查个人
	 * @param pageNumber
	 * @param pageSize
	 * @param staffID
	 * @param parameter
	 * @param returnVisit
	 * @return
	 */
	public PageForm getConsultslist(int pageNumber, int pageSize, String staffID,String companyID, String parameter, String returnVisit){

		List<Object> params = new ArrayList<Object>();


         String hql = "";



		if(companyID!=null&&!companyID.equals("")){
			hql = "from ConsultingRegistration where companyId  = ?";
			params.add(companyID);
		}else{
		   hql = "from ConsultingRegistration where staffId = ? and companyId is null";
			params.add(staffID);
		}
		if(parameter!=null&&!parameter.equals("")){

			hql+=" and (consultantName  = ? or  consultantPhone = ?)";
			params.add(parameter);
			params.add(parameter);
		}

		if(returnVisit!=null&&!returnVisit.equals("")){

			hql+=" and returnVisit = ?";
			params.add(returnVisit);
		}

		hql+=" order by consultingDate desc";
		PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,params.toArray());

		return pageForm;
	}


	/**
	 *
	 * 导出打印用
	 * @param staffID
	 * @param companyID
	 * @param parameter
	 * @param returnVisit
     * @return
     */
	public List<BaseBean> getConsultForList(String staffID,String companyID,String parameter, String returnVisit){

		List<Object> params = new ArrayList<Object>();
		String hql = "";

		if(companyID!=null&&!companyID.equals("")){
			hql = "from ConsultingRegistration where companyId  = ?";
			params.add(companyID);
		}else{
			hql = "from ConsultingRegistration where staffId = ? and companyId is null";
			params.add(staffID);
		}
		if(parameter!=null&&!parameter.equals("")){

			hql+=" and (consultantName  = ? or  consultantPhone = ?)";
			params.add(parameter);
			params.add(parameter);
		}

		if(returnVisit!=null&&!returnVisit.equals("")){

			hql+=" and returnVisit = ?";
			params.add(returnVisit);
		}

		hql+=" order by consultingDate desc";
        List<BaseBean> list = basebeandao.getListBeanByHqlAndParams(hql,params.toArray());
		return list;

	}


	/**
	 *
	 * 查看详情
	 * @param crId
	 * @return
	 */
	public ConsultingRegistration viewDetail(String crId){
		String hql = "from ConsultingRegistration where crId = ?";

		ConsultingRegistration  consult = (ConsultingRegistration)basebeandao.getBeanByHqlAndParams(hql,new Object[]{crId});

		return consult;
	}


	/**
	 *
	 * 获取报名的这个文章的
	 * @param ppid
	 * @return
	 */
	public List<Object> getConBmlist(String ppid){

		 String sql = "select c.consultantname,f.headimage from dtCltRegistration c left join t_eshop_customer r on c.consultantphone = r.account left join dt_hr_staff f on r.staffid = f.staffid where c.ppid=? and rownum <=10 order by c.consultingdate desc";


	   List<Object> list = basebeandao.getListObjectBySqlAndParams(sql,new Object[]{ppid});

		return list;
	}
}
