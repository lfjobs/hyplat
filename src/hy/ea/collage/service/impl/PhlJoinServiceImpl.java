package hy.ea.collage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.bo.human.StaffPosInfo;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarRelationMarket;
import hy.ea.bo.production.AttriProduction;
import hy.ea.collage.service.PhlJoinService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.ToChineseFirstLetter;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;

/**
 * Created by lyc on 2019-01-21.
 */
@Service
public class PhlJoinServiceImpl implements PhlJoinService {
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private UpLoadFileService fileService;

    /**
     * 保存车辆信息
     */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void saveCarjoin(CarInformation carInformation, String driverId,
			String drivingId, TEshopCusCom tc, Staff newStaff, File file,
			String fileFileName, String marketId) {
		
		
		String hqlc = "from Company c where c.companyID=(select cc.comanyId from CcomCom  cc where cc.ccompanyId = ?)";
		
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				hqlc, new Object[] { marketId });
		
		
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hqls = "from Staff s where s.staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqls,
				new Object[] { tc.getStaffid() });
		// 更新员工表
		staff.setStaffName(newStaff.getStaffName());
		staff.setReference(newStaff.getReference());
		staff.setStaffIdentityCard(newStaff.getStaffIdentityCard());

		ContactRelation conRelation = (ContactRelation) saveRelation(
				tc.getStaffid(), company.getCompanyID(), "司机");
		if (conRelation != null) {
			beans.add(conRelation);

		}

		if (drivingId != null && !drivingId.equals("")) {

			// 保存车辆信息
			carInformation
					.setCarID(serverService.getServerID("carInformation"));
			carInformation.setStaffID(staff.getStaffID());
			carInformation.setStaffName(staff.getStaffName());
			carInformation.setDriver(staff.getStaffName());
			carInformation.setCompanyID(company.getCompanyID());
			carInformation.setCompanyName(company.getCompanyName());
			carInformation.setState1("00");// 加盟车
			carInformation.setState2("01");// 新增车
			carInformation.setState3("00");// 未使用

			carInformation.setJoinDate(new Date());// 注册日期
		}
		// 身份证、驾驶证、行驶证
		StaffCertificate cre = null;
		if (newStaff.getStaffIdentityCard() != null) {
			// 判断身份证是否存在
			cre = getCertificate(staff.getStaffID(), "身份证");
			if (cre == null) {
				cre = new StaffCertificate();
				cre.setCredentialsID(serverService.getServerID("credentials"));
				logger.info("调试信息");
				cre.setCompanyID(tc.getCompanyId());
				cre.setStaffID(staff.getStaffID());
				cre.setCredentialsName("身份证");
				cre.setCredentialsType("身份证");
				cre.setCredentialsNo(newStaff.getStaffIdentityCard());
			} else {
				cre.setCredentialsNo(newStaff.getStaffIdentityCard());
			}
			beans.add(cre);
		}
		if (driverId != null&&!driverId.equals("")) {
			// 判断驾驶证是否存在
			cre = getCertificate(staff.getStaffID(), "驾驶证");
			if (cre == null) {
				cre = new StaffCertificate();
				cre.setCredentialsID(serverService.getServerID("credentials"));
				logger.info("调试信息");
				cre.setCompanyID(tc.getCompanyId());
				cre.setStaffID(staff.getStaffID());
				cre.setCredentialsName("驾驶证");
				cre.setCredentialsType("驾驶证");
				cre.setCredentialsNo(driverId);
			} else {
				cre.setCredentialsNo(driverId);
			}
			beans.add(cre);
		}

		// 行驶证
		if (drivingId != null&&!drivingId.equals("")) {

			cre = new StaffCertificate();
			cre.setCredentialsID(serverService.getServerID("credentials"));
			logger.info("调试信息");
			cre.setCompanyID(tc.getCompanyId());
			cre.setStaffID(staff.getStaffID());
			cre.setCredentialsName("行驶证");
			cre.setCredentialsType("行驶证");
			cre.setCredentialsNo(drivingId);
			carInformation.setDrivRelation(drivingId);// 车辆信息关联行驶证

			beans.add(cre);

			// 物品表
			GoodsManage gm = new GoodsManage();
			gm.setGoodsID(serverService.getServerID("goodsID"));
			System.out.print(gm.getGoodsID());
			gm.setGoodsName(carInformation.getCarType());
			gm.setFiveClear("4");
			gm.setCompanyID(tc.getCompanyId());
			gm.setGoodsState("00");
			gm.setCreatedate(new Date());
			gm.setTypeID(carInformation.getCarType());

			// 保存图片
			if (file != null && file.length() > 0) {
				String path = ServletActionContext.getRequest().getSession()
						.getServletContext().getRealPath("/");
				String photopath = "";
				AttriProduction attrp = null;
				photopath = fileService.savePhoto(
						path,
						fileFileName,
						file,
						tc.getCompanyId(),
						"/gooddesign/"
								+ Utilities.getDateString(new Date(),
										"yyyy-MM-dd"));
				String jjPath = photopath.split("\\.")[0] + "."
						+ photopath.split("\\.")[1];
				// String jjPath = photopath.split("\\.")[0] + "small." +
				// photopath.split("\\.")[1];
				// ImageCut.scale(path + photopath, path + jjPath, 414, 431);
				// 图片保存在goodsmanage表与carInformation表，同时也保存attriproduction表
				gm.setPhotoPath(jjPath);
				carInformation.setPhoto(jjPath);
				attrp = new AttriProduction();
				attrp.setApid(serverService.getServerID("apid"));
				attrp.setType("2");
				attrp.setImgurl(jjPath);
				attrp.setGoodsid(gm.getGoodsID());
				attrp.setSort(1);
				beans.add(attrp);
			}
			// 编号处理
			String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
			Object[] params = { staff.getGroupCompanySn(), gm.getTypeID() };
			// 获取拼音码
			String pinyin = ToChineseFirstLetter.getFirstLetter(gm.getTypeID());
			String Upstr = pinyin.toUpperCase();// 转换为大写
			int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql,
					params);
			DecimalFormat form = new DecimalFormat("000000");
			String ss = form.format(goodscodingnum + 1);
			gm.setGoodsCoding(Upstr + "_" + ss);
			// 物品表与车辆信息进行关联
			carInformation.setGoodsID(gm.getGoodsID());
			carInformation.setGoodsCoding(gm.getGoodsCoding());
			beans.add(gm);
			beans.add(carInformation);
		}

		// 关联市场
		if (marketId != null&&!marketId.equals("")) {
			CarRelationMarket crm = new CarRelationMarket();
			crm.setRmId(serverService.getServerID("relacarm"));
			crm.setCarId(carInformation.getCarID());
			crm.setMarketId(marketId);
			beans.add(crm);
		}
		beans.add(staff);

		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
	}
    
    
	/**
	 * 保存往来关系
	 * @return
	 */
     private BaseBean saveRelation(String staffID,String companyID,String relationshipValue){
		
		ContactRelation conRelation=null;
		if(relationshipValue!=null&&!"".equals(relationshipValue)){
		String hql = "select count(*) from ContactRelation where staffID = ? and companyID = ? and relation = ?";
		Object[] params = { staffID, companyID,relationshipValue.trim() };
			int count = baseBeanService.getConutByByHqlAndParams(hql, params);
			if(count==0){
				conRelation=new ContactRelation();
				conRelation.setRelationID(serverService
						.getServerID("contactrelation"));
				conRelation.setCompanyID(companyID);
				conRelation.setStaffID(staffID);
				conRelation.setRelation(relationshipValue.trim() );
			}
		}
		return conRelation;
	}

    /**
     * 获取车辆列表
     * @return
     */
    @Override
    public PageForm getPageFormCar(int pageNumber, int pageSize,String industryId,String ccompanyID){
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sbf=new StringBuilder();
        sbf.append("select ci.photo,ci.driver,ci.carnum,ci.ratifyquality,ci.loadvolume,ci.cartype,ci.outersize,dc.companyname");
        sbf.append(" from dt_Car_CarInformation ci left join dt_Car_RelationMarket crm on ci.carid=crm.carid");
        sbf.append(" left join Dtcontactcompany dc on crm.marketid=dc.ccompanyid where dc.industryid=?");
        objList.add(industryId);

        //如果市场存在 就只查本市场的车辆
        String sql="select count(1) from Dtcontactcompany d where d.industryid = ? and d.ccompanyid=?";
        Object count=baseBeanService.getObjectBySqlAndParams(sql,new Object[]{industryId,ccompanyID});
        if("1".equals(count.toString())){
            sbf.append(" and dc.ccompanyid=?");
            objList.add(ccompanyID);
        }
        sbf.append(" order by ci.joindate desc");
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sbf.toString(), "select count(*) from (" + sbf + ")",
                objList.toArray());
        return pageForm;
    }
    
    /**
     * 最新车辆
     */
    public List<BaseBean> getListFromCar(String industryId,String ccompanyID){
    	List<Object> objList = new ArrayList<Object>();
        StringBuilder sbf=new StringBuilder();
        sbf.append("select ci.photo,ci.driver,ci.carnum,ci.ratifyquality,ci.loadvolume,ci.cartype,ci.outersize,dc.companyname");
        sbf.append(" from dt_Car_CarInformation ci left join dt_Car_RelationMarket crm on ci.carid=crm.carid");
        sbf.append(" left join Dtcontactcompany dc on crm.marketid=dc.ccompanyid where dc.industryid=?");
        objList.add(industryId);

        //如果市场存在 就只查本市场的车辆
        String sql="select count(1) from Dtcontactcompany d where d.industryid = ? and d.ccompanyid=?";
        Object count= baseBeanService.getObjectBySqlAndParams(sql,new Object[]{industryId,ccompanyID});
        if("1".equals(count.toString())){
            sbf.append(" and dc.ccompanyid=?");
            objList.add(ccompanyID);
        }
        sbf.append(" and rownum <3 order by ci.joindate desc");
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sbf.toString(), objList.toArray());
        
        return list;
    	
    }


    /**
     * 获取市场列表
     * @return
     */
    @Override
    public List<BaseBean> getMarketList(String industryID, String staffId) {
        StaffPosInfo sf = (StaffPosInfo)baseBeanService.getBeanByHqlAndParams("from StaffPosInfo where staffID = ?",new Object[]{staffId});
        List<Object> objList = new ArrayList<Object>();
        if(sf!=null){
            objList.add(sf.getLongitude());
            objList.add(sf.getLatitude());
        }else{
            objList.add("");
            objList.add("");

        }
        String sql = "select d.ccompanyid,d.companyname,getdistance(?,?,d.accuracy,d.dimension) c from Dtcontactcompany d where d.industryid = ? order by c";
        objList.add(industryID);
        List<BaseBean> marketList=(List<BaseBean>)baseBeanService.getListBeanBySqlAndParams(sql,objList.toArray());
        return marketList;
    }

    /**
     * 获取车辆类型/车辆使用性质
     * @return
     */
    @Override
    public List<BaseBean> getCarTypeOrUse(String companyId,String codeId) {
      //  String sql="select cd.codevalue from Dtccode cd where cd.codepid=? and cd.companyid=?";
    	String sql="select cd.codevalue from DtScode cd where cd.codepid=?";
        List<BaseBean> objList=(List<BaseBean>)baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{codeId});
        return objList;
    }

    /**
     * 获取证件
     * @param staffId
     * @param certificateName
     * @return
     */
    @Override
    public StaffCertificate getCertificate(String staffId, String certificateName) {
        String hql="from StaffCertificate ss where ss.staffID=? and ss.credentialsName=?";
        StaffCertificate cre=(StaffCertificate)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{staffId,certificateName});
        return cre;
    }

    /**
     * 验证行驶证、车牌号、发动机号是否已注册
     * @param carNum
     * @param engineNum
     * @param industryId
     * @return
     */
    @Override
    public String checkUnique(String drivingId,String carNum,String engineNum, String industryId) {
        StringBuilder sbf=new StringBuilder();
        sbf.append("select count(1) from dt_Car_CarInformation ci");
        sbf.append(" left join dt_Car_RelationMarket crm on ci.carid = crm.carid left join Dtcontactcompany dc on crm.marketid = dc.ccompanyid");
        sbf.append(" where  dc.industryid = ?");
        String num="";
        if(drivingId!=null){
            sbf.append(" and ci.drivRelation = ?");
            num=drivingId;
        }else if(carNum!=null){
            sbf.append(" and ci.carnum = ?");
            num=carNum;
        }else if(engineNum!=null){
            sbf.append(" and ci.enginenum = ?");
            num=engineNum;
        }
        Object count=baseBeanService.getObjectBySqlAndParams(sbf.toString(),new Object[]{industryId,num});
        return count.toString();
    }
}
