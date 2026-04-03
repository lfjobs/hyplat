package hy.ea.service.impl;

import com.wechat.bo.sft.ApplyParam;
import com.wechat.bo.sft.SalesSceneInfo;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.dao.CompanyDao;
import hy.ea.service.CompanyService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyDao companyDao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private ServerService idgec;
    @Resource
    private BaseBeanDao beandao;

    @Override
    public CDetail getCDetailByCompanyID(String companyID) {
        return companyDao.getCDetailByCompanyID(companyID);
    }

    @Override
    public Company getCompanyByCompanyID(String companyID) {
        return companyDao.getCompanyByCompanyID(companyID);
    }

    @Override
    public Company getCompanyByIdentifier(String identifier) {
        return companyDao.getCompanyByIdentifier(identifier);
    }

    @Override
    public ArrayList<String> getCompanyAndItsChildrenIDs(String companyID) {
        if (companyID == null) return null;
        ArrayList<String> cids = new ArrayList<String>();
        cids.add(companyID);
        DetachedCriteria dc = DetachedCriteria.forClass(Company.class);
        dc.add(Restrictions.eq("companyPID", companyID));
        List<BaseBean> list = baseBeanService.getListByDC(dc);
        for (BaseBean baseBean : list) {
            String cid = ((Company) baseBean).getCompanyID();
            cids.add(cid);
        }
        return cids;
    }

    @Override
    public List<Company> getCompanyListByPID(String companyID) {
        return companyDao.getCompanyListByPID(companyID);
    }

    /**
     * 保存公司信息
     *
     * @param contactCompany 往来单位信息
     * @param logo           公司logo文件
     * @param logoFileName   logo文件名
     * @param merchant_shortname 公司/商家简称
     * @deprecated 此方法不处理账号、认领业务员（BusiManager）与公司的关系（由后续购买逻辑处理）
     */
    @Override
    @Transactional
    public void saveCompanyInfo(ContactCompany contactCompany, File logo, String logoFileName, String merchant_shortname) {
        try {
            //初始化公司对象
            Company company = new Company();
            //初始化公司详细信息
            CDetail cdl = new CDetail();
            //初始化获取Web项目的全路径
            String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
            //初始化保存对象集
            List<BaseBean> beans = new ArrayList();
            //初始化图片路径
            String photoPath = "";
            //一.处理公司表
            Company companyOld = (Company) this.beandao.getBeanByHqlAndParams("from Company where companyName = ?", new Object[]{contactCompany.getCompanyName()});
            //新增公司
            if (Objects.isNull(companyOld)) {
                company.setCompanyID(this.idgec.getServerID("company"));
                company.setCompanyPID(this.idgec.getServerID("pcompany"));
                company.setCompanyRegisterDate(new Date());
                company.setGroupCompanySn(this.idgec.getServerID("groupCompanySn"));
                company.setCompanyRegisterDate(new Date());
                company.setCompanyLicenseCount(5);
                company.setTotalSales("0");
                company.setCompanyStatus("00");
                company.setCompanyType("01");
                company.setCompanyName(contactCompany.getCompanyName());
                if (company.getCcomtype() == null || company.getCcomtype().equals("")) {
                    company.setCcomtype("5");
                }
                /*if (company.getCompanyIdentifier() != null) {
                    company.setCompanyIdentifier(company.getCompanyIdentifier().trim().toLowerCase());
                } else {*/
                company.setCompanyIdentifier(contactCompany.getCompanyName());
                //}
                beans.add(company);
                //新增库房
                saveDepot(company.getCompanyID(),beans);
            } else {
                company = companyOld;
            }
            //二.生成店铺logo地址
            if (Objects.nonNull(logo)) {
                photoPath = fileService.savePhoto(path, logoFileName,
                        logo, company.getCompanyID(),
                        "logo/"
                                + Utilities.getDateString(new Date(),
                                "yyyy-MM-dd"));
            }
            //三.保存往来单位表
            String hqlcom = "from ContactCompany where companyName = ?";
            ContactCompany contactCompanyOld = (ContactCompany) this.beandao.getBeanByHqlAndParams(hqlcom, new Object[]{contactCompany.getCompanyName()});
            if (Objects.isNull(contactCompanyOld)) {
                contactCompany.setCcompanyID(this.idgec.getServerID("contactCompany"));
                contactCompany.setWebstatus("01");
                contactCompany.setCustStatus("02");
                contactCompany.setAuthState("00");
                //contactCompany.setAccuracy(contactCompany.getAccuracy());
                //contactCompany.setDimension(contactCompany.getDimension());
                //contactCompany.setShopname(contactCompany.getShopname());
                //contactCompany.setGdcate(contactCompany.getGdcate());
                //contactCompany.setGdcode(contactCompany.getGdcode());
                //contactCompany.setGdcate2(contactCompany.getGdcate2());
                //contactCompany.setGdcode2(contactCompany.getGdcode2());
                //contactCompany.setGdID(contactCompany.getGdID());
                contactCompany.setCompanyCode(contactCompany.getCompanyCode());//公司编号
                contactCompany.setCompanyName(contactCompany.getCompanyName());//公司名称
                //contactCompany.setCompanyShortName(contactCompany.getCompanyShortName());//公司简称
                contactCompany.setCompanyAddr(contactCompany.getCompanyAddr());//公司地址
                contactCompany.setCresponsible(contactCompany.getCresponsible());//责任人
                contactCompany.setResponsibleTel(contactCompany.getResponsibleTel());//责任人电话
                contactCompany.setIndustryType(contactCompany.getIndustryType());//行业
                contactCompany.setIndustryId(contactCompany.getIndustryId());//行业id
                contactCompany.setLogoPath(photoPath);//图片
                //contactCompany.setComPro(contactCompany.getComPro());//公司性质
                //物品/产品型号是0，则为个人加入
				/*if (ccmomtype.equals("0")) {
					contactCompanyNew.setPcState("1");
				}*/
                //保存二级商户进件
                ApplyParam app = new ApplyParam();
                app.setApplyID(idgec.getServerID("applyID"));
                app.setOut_request_no(idgec.getServerID("wfj"));
                contactCompany.setApplyID(app.getApplyID());
                app.setOrganization_type(contactCompany.getComPro());
                app.setMerchant_shortname(merchant_shortname);
                beans.add(app);
                beans.add(contactCompany);
            } else {
                contactCompany.setCcompanyID(contactCompanyOld.getCcompanyID());
                //保存二级商户进件
                ApplyParam app = (ApplyParam) this.beandao.getBeanByHqlAndParams("from ApplyParam where applyID = ?",new Object[]{contactCompanyOld.getApplyID()});
                //修改主体类型
                if(Objects.isNull(app)){
                    ApplyParam appNew = new ApplyParam();
                    appNew.setApplyID(idgec.getServerID("applyID"));
                    appNew.setOut_request_no(idgec.getServerID("wfj"));
                    appNew.setOrganization_type(contactCompany.getComPro());
                    appNew.setMerchant_shortname(merchant_shortname);
                    beans.add(appNew);
                    contactCompany.setApplyID(appNew.getApplyID());
                }else{
                    contactCompany.setApplyID(app.getApplyID());
                    baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{"update ApplyParam u set u.organization_type=?,u.merchant_shortname=? where u.applyID=?"}, new Object[]{contactCompany.getComPro(),merchant_shortname,app.getApplyID()});
                }
                //contactCompany = contactCompanyOld;
                baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{"update ContactCompany u set u.companyCode=?,u.companyName=?,u.companyAddr=?,u.cresponsible=?,u.companyTel=?,u.industryType=?,u.industryId=?,u.logoPath=?,u.comPro=? where u.companyName=?"}, new Object[]{contactCompany.getCompanyCode(),contactCompany.getCompanyName(),contactCompany.getCompanyAddr(),contactCompany.getCresponsible(),contactCompany.getCompanyTel(),contactCompany.getIndustryType(),contactCompany.getIndustryId(),photoPath,contactCompany.getComPro(),contactCompanyOld.getCompanyName()});
            }
            //四.新增保存店铺信息
            SalesSceneInfo sales_scene_info = new SalesSceneInfo();
            sales_scene_info.setStore_name(contactCompany.getShopname());
            sales_scene_info.setSsId(idgec.getServerID("ssid"));
            beans.add(sales_scene_info);
            //五.保存公司和往来单位中间表
            CcomCom ccomCom = (CcomCom) this.beandao.getBeanByHqlAndParams("from CcomCom where ccompanyId = ? and comanyId = ?", new Object[]{contactCompany.getCcompanyID(), company.getCompanyID()});
            if (ccomCom == null) {
                ccomCom = new CcomCom();
                ccomCom.setCcomRelationId(this.idgec.getServerID("ccomcom"));
                ccomCom.setCcompanyId(contactCompany.getCcompanyID());
                ccomCom.setComanyId(company.getCompanyID());
                ccomCom.setState("0");
                beans.add(ccomCom);
            }
            //六.保存公司详细信息
            CDetail cdlOld = (CDetail) this.beandao.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object[]{company.getCompanyID()});
            if (Objects.isNull(cdlOld)) {
                cdl.setDetailID(this.idgec.getServerID("cdetail"));
                cdl.setCompanyID(company.getCompanyID());
                cdl.setCompanyAddress(contactCompany.getCompanyAddr());
                cdl.setLogo(photoPath);
                cdl.setCompanyPhone(contactCompany.getResponsibleTel());
                cdl.setCompanyManager(contactCompany.getCresponsible());
                beans.add(cdl);
            } else {
                baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{"update CDetail u set u.companyAddress=?,u.logo=? where u.companyID=?"}, new Object[]{contactCompany.getCompanyAddr(), photoPath, company.getCompanyID()});
            }
            //七.执行保存
            this.beandao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存公司信息失败，请联系管理员");
        }
    }

    @Override
    public List<BaseBean> getCompanyAndItsChildren(String companyID) {
        if (companyID == null || !companyID.startsWith("company")) return null;
        ArrayList<BaseBean> cidsBaseBean = new ArrayList<BaseBean>();
        cidsBaseBean.add(getCompanyByCompanyID(companyID));
        DetachedCriteria dc = DetachedCriteria.forClass(Company.class);
        dc.add(Restrictions.eq("companyPID", companyID));
        List<BaseBean> list = baseBeanService.getListByDC(dc);
        for (BaseBean baseBean : list) {
            cidsBaseBean.add(baseBean);
        }
        return cidsBaseBean;
    }

    /**
     * 保存仓库信息
     * @param companyID
     * @param beans
     */
    private void saveDepot(String companyID,List<BaseBean> beans){
        //新增库房
        String[] stra = {
                "股东库",
                "董事会库",
                "监事会库",
                "工会库",
                "顾问会库",
                "董事长室库",
                "总经理室库",
                "人事库",
                "财务库",
                "生产库",
                "营销库",
                "创收库"
        };
        String[] strb = {"实物仓库","资料仓库", "资金仓库", "金币仓库"};
        String[] strs = {"原材料库", "成品库", "销售库", "退货库", "物流库"};
        for (int j = 0; j < stra.length; j++) {
            String apid=saveDepot2(companyID,"001",j+1,stra[j],beans);
            for (int i = 0; i < strb.length; i++){
                if (!stra[j].equals("财务库")&&i>=2){
                    break;
                }
                String bpid=saveDepot2(companyID,apid,i+1,strb[i],beans);
                if (stra[j].equals("营销库")&&strb[i].equals("实物仓库")){
                    for (int n = 0; n < strs.length; n++) {
                        saveDepot2(companyID,bpid,n+1,strs[n],beans);
                    }
                }
            }
        }
    }

    /**
     * 保存仓库信息
     * @param comid 公司id
     * @param pid 父级id
     * @param i 序号
     * @param name 仓库名
     * @param beans 仓库集合
     */
    private String saveDepot2(String comid,String pid,int i,String name,List<BaseBean> beans){
        DepotManage depot = new DepotManage();
        depot.setDepotID(this.idgec.getServerID("DepotManage"));
        depot.setCompanyID(comid);
        depot.setDepotPID(pid);
        depot.setDepotNum(i);
        depot.setDepotName(name);
        depot.setDepotState("02");
        depot.setDepotType("1");
        beans.add(depot);
        System.out.println(depot.getDepotID()+"--"+depot.getDepotPID()+":"+depot.getDepotName());
        return depot.getDepotID();
    }

}
