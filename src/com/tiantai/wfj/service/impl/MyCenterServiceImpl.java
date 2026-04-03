package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.MyCenterService;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCard;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional
@Service
public class MyCenterServiceImpl
        implements MyCenterService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serveService;

    @Resource
    private BaseBeanService baseBeanService;


    @Resource
    private BaseBeanDao baseBeanDao;
    
    
   /**
    * 微分金手机号账号 user 获取用户
    */
	public TEshopCusCom getCusCom(String user){
		
		String hql = "from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?";
		
		TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{user,"01"});
		
		  return tc;
	}

    /**
     * 微分金手机号账号 user 获取用户
     */
	public TEshopCustomer getCustomer(String user){
         String hql = "from TEshopCustomer where account = ? and logOff=0";
		
         TEshopCustomer tec = (TEshopCustomer)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{user});
		 return tec;
	}

	@Override
	public Object getUserInfo(String user) {
		String sql = "select f.staffid,f.staffname,pt.goodsname,f.headimage,f.realname,m.sccid,m.account " +
				"from dt_productpackaging pt,dt_hr_staff f,t_Eshop_Cuscom m where pt.model = m.custype and " +
				"pt.type=? and f.staffid= m.staffid and m.account = ? and m.acquiesce=? and m.logOff=0";
		Object obj = baseBeanDao.getObjectBySqlAndParams(sql, new Object[]{"会员类型级别",user,"01"});
		return obj;
	}


	/**
	 *
	 * 完善身份信息
	 * @param staff
	 * @return
     */
	public void editInfo(Staff staff,String sccid){
      if(staff.getStaffID()!=null&&!staff.getStaffID().equals("")) {

		  Staff staffod = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staff.getStaffID()});

		  if (staff.getRealname() != null && !staff.getRealname().equals("")) {
			  staffod.setRealname(staff.getRealname());
			  staffod.setStaffIdentityCard(staff.getStaffIdentityCard());
			  if(staff.getHeadimage()!= null){
				  staffod.setHeadimage(staff.getHeadimage());
			  }
			  String hql = "update Staff s set s.realname=?,s.staffIdentityCard=?,s.headimage=? where s.sccid = ?";
			  List<Object[]> parmsList = new ArrayList<Object[]>();
			  Object[] param = new Object[]{staff.getRealname(), staff.getStaffIdentityCard(),staff.getHeadimage(), sccid};
			  parmsList.add(param);
			  List<BaseBean> beans = new ArrayList<BaseBean>();
			  beans.add(staffod);
			  baseBeanService.executeHqlsByParamsList(beans, new String[]{hql}, parmsList);

		  }else{
			  if(staff.getHeadimage()!=null&&!staff.getHeadimage().equals("")) {
				  staffod.setHeadimage(staff.getHeadimage());
			  }
			  staffod.setStaffName(staff.getStaffName());
			  staffod.setSex(staff.getSex());
			  staffod.setBirthday(staff.getBirthday());
			  staffod.setCulturalDegree(staff.getCulturalDegree());
			  staffod.setNativePlace(staff.getNativePlace());
			  staffod.setStaffAddress(staff.getStaffAddress());
			  staffod.setReference(staff.getReference());
			  staffod.setReferenceOrganization(staff.getReferenceOrganization());
			  baseBeanDao.update(staffod);
		  }
	  }




	}

	/**
	 *
	 * 修改密码
	 * @param account
	 * @param pswtype
	 * @param yspsw
	 * @param newpsw
     * @return
     */
	public  String updatePsw(String account,String pswtype,String yspsw,String newpsw){
		String hql1 = "from TEshopCustomer t where account = ?";
		TEshopCustomer  tc = (TEshopCustomer)baseBeanDao.getBeanByHqlAndParams(hql1,new Object[]{account});
        if(yspsw!=null&&!yspsw.equals("")) {
			if (pswtype.equals("login")) {
				if (tc.getPassword() != null && !tc.getPassword().equals(yspsw)) {
					return "2";//原始密码不正确
				}
			} else {
				if (tc.getPaymentCode() != null && !tc.getPaymentCode().equals(yspsw)) {
					return "2";//原始密码不正确
				}

			}

		}



        String result = "0";
		String hql = "";
		if(pswtype.equals("login")) {
			hql = "update TEshopCustomer set password=? where account=? and logOff=0";
		}else{
			hql = "update TEshopCustomer set paymentCode=? where account=? and logOff=0";

		}
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hql }, new String[] { newpsw, account });
			result = "0";
		} catch (Exception e) {
			result = "1";
		}

		return result;
	}

	@Override
	public List<Object> getMenuInfo(String account, String staffid,Integer rNumber) {
		List<Object> rList=new LinkedList<>();
		//直接查询首页推荐的所有内容
		String sql="select MENU_TITLE,MENU_URL,MENU_ID from DT_PUBLIC_MENU_THREE where MENU_PARENT_ID=?";
		rList = baseBeanDao.getListObjectBySqlAndParams(sql, new Object[]{"2025040117116"});


		/*//首先查询当前账号有没有喜欢的推荐信息，如果有则返回有的，如果不足在查询公共的
		String sql = "select * from (select mt.MENU_TITLE,mt.MENU_URL,mt.MENU_ID from DT_PUBLIC_MENU_RECOMMEND  mr LEFT JOIN DT_PUBLIC_MENU_THREE  mt on mr.MENU_ID=mt.MENU_ID where mr.STAFF_ID=? and MT.MENU_ID is not null ORDER BY mr.UPDATE_TIME DESC) where ROWNUM <= ?";
		List<Object> listObjectBySqlAndParams = baseBeanDao.getListObjectBySqlAndParams(sql, new Object[]{staffid,rNumber});
		if(listObjectBySqlAndParams!=null&&listObjectBySqlAndParams.size()>=rNumber){
			//数据完整，直接返回
			int length=0;
			for (int i = 0; i < listObjectBySqlAndParams.size(); i++) {
				Object oi = listObjectBySqlAndParams.get(i);
				Object[] arri = (Object[]) oi;
				length+=arri[0].toString().length();
				if(length<=15){
					rList.add(oi);
				}else if(rList.size()<=2){
					rList.add(oi);
				}
			}
		}else if(listObjectBySqlAndParams!=null&& listObjectBySqlAndParams.size()<rNumber && listObjectBySqlAndParams.size()>0){
			int length=0;
			//数据不完整，继续查询公共的进行补充
			String sqlP = "select MENU_TITLE,MENU_URL,MENU_ID from DT_PUBLIC_MENU ORDER BY MENU_SORT ASC";
			List<Object> listP = baseBeanDao.getListObjectBySqlAndParams(sqlP, new Object[]{});
			//开始去重
			for (int i=0;i<listObjectBySqlAndParams.size();i++){
				Object oi = listObjectBySqlAndParams.get(i);
				Object[] arri = (Object[]) oi;
				length+=arri[0].toString().length();
				for (int j=0;j<listP.size();j++){
					Object oj = listP.get(j);
					Object[] arrj = (Object[]) oj;
					if(arri[0]==arrj[0]){
						listP.remove(oj);
						j--;
					}
				}
			}
			//去重完成后，将不足的补上
			Integer number=rNumber-listObjectBySqlAndParams.size();
			for (int k = 0; k < number; k++) {
				Object o = listP.get(k);
				Object[] ob = (Object[]) o;
				length+=ob[0].toString().length();
				if(length<=15){
					listObjectBySqlAndParams.add(o);
				}else if(listObjectBySqlAndParams.size()<=2){
					listObjectBySqlAndParams.add(o);
				}
			}
			//判断当前显示的标题字数是否超过15
			rList=listObjectBySqlAndParams;
		}else{
			//直接查询公共的数据进行返回
			String sqlP = "select * from (select MENU_TITLE,MENU_URL,MENU_ID from DT_PUBLIC_MENU ORDER BY MENU_SORT ASC) where ROWNUM <= ?";
			List<Object> listP = baseBeanDao.getListObjectBySqlAndParams(sqlP, new Object[]{rNumber});
			rList=listP;
		}*/
		return rList;
	}

	@Override
	public void editSatffCardInfo(StaffCard staffCard) {
		//查询是否存在身份证照片，如果存在即修改，不存在即新增
		if(staffCard.getCardFrontUrl()!=null || staffCard.getCardReverseUrl()!=null){
			String  sql="select * from DT_HR_STAFF_CARDINFO where STAFF_ID=?";
			Object objectBySqlAndParams = baseBeanDao.getObjectBySqlAndParams(sql, new Object[]{staffCard.getStaffID()});
			if(objectBySqlAndParams!=null){
				Object[] arri = (Object[]) objectBySqlAndParams;
				Object id=arri[0];
				staffCard.setCardKey(id.toString());
				if(staffCard.getCardFrontUrl()==null){
					if(arri[2]!=null){
						staffCard.setCardFrontUrl(arri[2].toString());
					}
				}
				if(staffCard.getCardReverseUrl()==null){
					if(arri[3]!=null){
						staffCard.setCardReverseUrl(arri[3].toString());
					}
				}
			}
			baseBeanDao.saveOrUpdate(staffCard);
		}
	}

	//添加推荐数据
	public static void main(String[] args) {
		for (int i=0;i<10;i++){
			UUID uuid = UUID.randomUUID();
			System.out.println(uuid.toString().replace("-",""));
		}
	}
}