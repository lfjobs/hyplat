package hy.ea.company.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 往来单位
 *
 * @author 王汝明
 */

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.DrivingSchool.TbJpCompany;
import hy.ea.bo.DrivingSchool.TbSysGeography;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.ContactConnection;
import hy.ea.bo.company.Contactresource;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.human.CS;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;


@Controller
@Scope("prototype")
public class ContactCompanyAction {
	private static final Logger logger = LoggerFactory.getLogger(ContactCompanyAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private ShowExcelService excelService;
    @Resource
    private UpLoadFileService fileService;
    public InputStream excelStream;
    private ContactCompany contactCompany;
    private PageForm pageForm;
    private String parameter;
    private int pageNumber;
    private String result;
    private String search;
    private ContactConnection cconnection;
    private String ccompanyID;
    @Resource
    private CCodeService codeService;
    private List<CCode> typelist;
    private List<CCode> distinctlist;
    private List<CCode> connectionlist;
    private List<CCode> codeRelationList;
    private String onlyCompany;
    private String type;
    private String showType;
    private Contactresource contactresource; // 显示隐藏
    private List<BaseBean> beans;
    private String districtPID;
    private File photo;
    private String photoFileName;
    private String flag;//往来单位网站管理标志ljc
    private String gtype;//维护网站
    private CcomCom ccomcom;//公司与往来单位关系ljc
    private Object rs;
    private String httpUrl;
    private String httpArg;
    private String content;
    private String imgPath;
    private String ccbPath;
    private TbJpCompany tcompany;
    private List<TbSysGeography> tbSysGeography;

    /**
     * 前台ajax获取单位往来关系
     *
     * @return
     */
    public String getConnectionList() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        List<CCode> connectionList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20110224xpd2t2jvda0000000002");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("connectionList", connectionList);
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    public String panduanSync() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String tempId = account.getCompanyID();
        String hql = "from TbJpCompany where companyId=?";
        TbJpCompany cc = (TbJpCompany) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tempId});
        if (cc.getPkTbJpCompanyid() != null && cc.getPkTbJpCompanyid().length() > 1)//存在返回
        {
            result = "1";
        } else {
            result = "0";
        }
        return "success";
    }

    public String syncComapny() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String tempId = account.getCompanyID();
        String hql = "from TbJpCompany where companyId=?";
        TbJpCompany cc = (TbJpCompany) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tempId});
        if (toSync(cc)) {
            hql = "update TbJpCompany set pkTbJpCompanyid=? where companyId=?";
            baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{cc.getPkTbJpCompanyid(), tempId});
        }
        return "success";
    }

    private String getVal(Object o) {
        if (o == null) {
            return "";
        } else {
            return o.toString();
        }
    }

    private boolean toSync(TbJpCompany parmEnt) {
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            String url = Constant.TIMING_DOMAIN + "/jpcompany/syncCompanyInfo";
            PostMethod postMethod = new PostMethod(url);
            NameValuePair[] data = {
                    new NameValuePair("companyId", getVal(parmEnt.getCompanyId())),
                    new NameValuePair("inscode", getVal(parmEnt.getInscode())),
                    new NameValuePair("name", getVal(parmEnt.getName())),
                    new NameValuePair("shortname", getVal(parmEnt.getShortname())),
                    new NameValuePair("business", getVal(parmEnt.getBusiness())),
                    new NameValuePair("licnum", getVal(parmEnt.getLicnum())),
                    new NameValuePair("address", getVal(parmEnt.getAddress())),
                    new NameValuePair("creditcode", getVal(parmEnt.getCreditcode())),
                    new NameValuePair("taxregcer", getVal(parmEnt.getTaxregcer())),
                    new NameValuePair("postcode", getVal(parmEnt.getPostcode())),
                    new NameValuePair("legal", getVal(parmEnt.getLegal())),
                    new NameValuePair("contact", getVal(parmEnt.getContact())),
                    new NameValuePair("phone", getVal(parmEnt.getPhone())),
                    new NameValuePair("busiscope", getVal(parmEnt.getBusiscope())),
                    new NameValuePair("busistatus", getVal(parmEnt.getBusistatus())),
                    new NameValuePair("checkStatus", getVal(parmEnt.getCheckStatus())),
                    new NameValuePair("syncType", getVal(parmEnt.getSyncType())),
                    new NameValuePair("syncStatus", getVal(parmEnt.getSyncStatus())),
                    new NameValuePair("companyLevel", getVal(parmEnt.getCompanyLevel())),
                    new NameValuePair("economicType", getVal(parmEnt.getEconomicType())),
                    new NameValuePair("district", getVal(parmEnt.getDistrict())),
                    new NameValuePair("legalIdcard", getVal(parmEnt.getLegalIdcard())),
                    new NameValuePair("registeredFund", getVal(parmEnt.getRegisteredFund())),
                    new NameValuePair("coachnumber", getVal(parmEnt.getCoachnumber())),
                    new NameValuePair("grasupvnum", getVal(parmEnt.getGrasupvnum())),
                    new NameValuePair("safmngnum", getVal(parmEnt.getSafmngnum())),
                    new NameValuePair("tracarnum", getVal(parmEnt.getTracarnum())),
                    new NameValuePair("classroom", getVal(parmEnt.getClassroom())),
                    new NameValuePair("thclassroom", getVal(parmEnt.getThclassroom())),
                    new NameValuePair("praticefield", getVal(parmEnt.getPraticefield())),
                    new NameValuePair("delFlag", getVal(parmEnt.getDelFlag())),
                    new NameValuePair("remark", getVal(parmEnt.getRemark())),
                    new NameValuePair("createperson", getVal(parmEnt.getCreateperson())),
                    new NameValuePair("updateperson", getVal(parmEnt.getUpdateperson())),
                    new NameValuePair("syncXlycStatus", getVal(parmEnt.getSyncXlycStatus())),
                    new NameValuePair("syncXlgjStatus", getVal(parmEnt.getSyncXlgjStatus())),
                    new NameValuePair("createdate", parmEnt.getCreatedate() == null ? "" : DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss", parmEnt.getCreatedate())),
                    new NameValuePair("licetime", parmEnt.getLicetime() == null ? "" : DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss", parmEnt.getLicetime())),
                    new NameValuePair("busisEndDate", parmEnt.getBusisEndDate() == null ? "" : DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss", parmEnt.getBusisEndDate())),
                    new NameValuePair("firstIssueDate", parmEnt.getFirstIssueDate() == null ? "" : DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss", parmEnt.getFirstIssueDate())),
                    new NameValuePair("updatedate", parmEnt.getUpdatedate() == null ? "" : DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss", parmEnt.getUpdatedate()))
            };
            postMethod.setRequestBody(data);
            httpClient.executeMethod(postMethod);
            // 读取内容
            InputStream resInputStream = null;
            resInputStream = postMethod.getResponseBodyAsStream();
            // 处理内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
            String tempBf = null;
            StringBuffer html = new StringBuffer();
            while ((tempBf = reader.readLine()) != null) {

                html.append(tempBf);
            }
            String resMes = html.toString();
            int dex = resMes.lastIndexOf("&");

            // 拆分页面应答数据
            String str[] = resMes.split("&");


            // 提取返回数据
            if (str.length == 41) {
                String companyId = str[0].substring(str[0].indexOf("=") + 1);
                String inscode = str[1].substring(str[1].indexOf("=") + 1);
                String name = str[2].substring(str[2].indexOf("=") + 1);
                String shortname = str[3].substring(str[3].indexOf("=") + 1);
                String business = str[4].substring(str[4].indexOf("=") + 1);
                String licnum = str[5].substring(str[5].indexOf("=") + 1);
                String address = str[6].substring(str[6].indexOf("=") + 1);
                String creditcode = str[7].substring(str[7].indexOf("=") + 1);
                String taxregcer = str[8].substring(str[8].indexOf("=") + 1);
                String postcode = str[9].substring(str[9].indexOf("=") + 1);
                String legal = str[10].substring(str[10].indexOf("=") + 1);
                String contact = str[11].substring(str[11].indexOf("=") + 1);
                String phone = str[12].substring(str[12].indexOf("=") + 1);
                String busiscope = str[13].substring(str[13].indexOf("=") + 1);
                String busistatus = str[14].substring(str[14].indexOf("=") + 1);
                String checkStatus = str[15].substring(str[15].indexOf("=") + 1);
                String syncType = str[16].substring(str[16].indexOf("=") + 1);
                String syncStatus = str[17].substring(str[17].indexOf("=") + 1);
                String companyLevel = str[18].substring(str[18].indexOf("=") + 1);
                String economicType = str[19].substring(str[19].indexOf("=") + 1);
                String district = str[20].substring(str[20].indexOf("=") + 1);
                String legalIdcard = str[21].substring(str[21].indexOf("=") + 1);
                String registeredFund = str[22].substring(str[22].indexOf("=") + 1);
                String coachnumber = str[23].substring(str[23].indexOf("=") + 1);
                String grasupvnum = str[24].substring(str[24].indexOf("=") + 1);
                String safmngnum = str[25].substring(str[25].indexOf("=") + 1);
                String tracarnum = str[26].substring(str[26].indexOf("=") + 1);
                String classroom = str[27].substring(str[27].indexOf("=") + 1);
                String thclassroom = str[28].substring(str[28].indexOf("=") + 1);
                String praticefield = str[29].substring(str[29].indexOf("=") + 1);
                String delFlag = str[30].substring(str[30].indexOf("=") + 1);
                String remark = str[31].substring(str[31].indexOf("=") + 1);
                String createperson = str[32].substring(str[32].indexOf("=") + 1);
                String updateperson = str[33].substring(str[33].indexOf("=") + 1);
                String syncXlycStatus = str[34].substring(str[34].indexOf("=") + 1);
                String syncXlgjStatus = str[35].substring(str[35].indexOf("=") + 1);
                String createDate = str[36].substring(str[36].indexOf("=") + 1);
                String licetime = str[37].substring(str[37].indexOf("=") + 1);
                String busisEndDate = str[38].substring(str[38].indexOf("=") + 1);
                String firstIssueDate = str[39].substring(str[39].indexOf("=") + 1);
                String updatedate = str[40].substring(str[40].indexOf("=") + 1);

                parmEnt.setCompanyId(companyId);
                parmEnt.setInscode(inscode);
                parmEnt.setName(name);
                parmEnt.setShortname(shortname);
                parmEnt.setBusiness(business);
                parmEnt.setLicnum(licnum);
                parmEnt.setAddress(address);
                parmEnt.setCreditcode(creditcode);
                parmEnt.setTaxregcer(taxregcer);
                parmEnt.setPostcode(postcode);
                parmEnt.setLegal(legal);
                parmEnt.setContact(contact);
                parmEnt.setPhone(phone);
                parmEnt.setBusiscope(busiscope);
                parmEnt.setBusistatus(busistatus);
                parmEnt.setCheckStatus(checkStatus);
                parmEnt.setSyncType(syncType);
                parmEnt.setSyncStatus(syncStatus);
                parmEnt.setCompanyLevel(companyLevel);
                parmEnt.setEconomicType(economicType);
                parmEnt.setDistrict(district);
                parmEnt.setLegalIdcard(legalIdcard);
                parmEnt.setRegisteredFund(registeredFund);
                parmEnt.setCoachnumber(coachnumber);
                parmEnt.setGrasupvnum(grasupvnum);
                parmEnt.setSafmngnum(safmngnum);
                parmEnt.setTracarnum(tracarnum);
                parmEnt.setClassroom(classroom);
                parmEnt.setThclassroom(thclassroom);
                parmEnt.setPraticefield(praticefield);
                parmEnt.setDelFlag(delFlag);
                parmEnt.setRemark(remark);
                parmEnt.setCreateperson(createperson);
                parmEnt.setUpdateperson(updateperson);
                parmEnt.setSyncXlycStatus(syncXlycStatus);
                parmEnt.setSyncXlgjStatus(syncXlgjStatus);
                parmEnt.setCreatedate(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", createDate));
                parmEnt.setLicetime(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", licetime));
                parmEnt.setBusisEndDate(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", busisEndDate));
                parmEnt.setFirstIssueDate(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", firstIssueDate));
                parmEnt.setUpdatedate(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", updatedate));


                parmEnt.setPkTbJpCompanyid(companyId);

                return true;
            }
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
        return false;
    }

    // 添加或修改往来单位
    public String saveContactCompany() {

        String photoPath = "";
        List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String comid = null;
        String staffName = null;
        if (account == null) {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom tm = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
            Staff s = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{tm.getStaffid()});
            comid = request.getParameter("companyID");
            staffName = s.getStaffName();
        } else {
            if (account.getStaffName() != null) {
                staffName = account.getStaffName();
            } else {
                Staff s = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{account.getStaffID()});
                staffName = s.getStaffName();
            }
            comid = account.getCompanyID();
        }
        if (photo != null) {
            String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
            photoPath = fileService.savePhoto(path, photoFileName, photo, comid,
                    "/contactcompany/" + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
            contactCompany.setLogoPath(photoPath);
        } else {
            photoPath = contactCompany.getLogoPath();
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";

        if (null == contactCompany.getCcompanyID() || "".equals(contactCompany.getCcompanyID())) {
            //ContactCompany contactcompany=new ContactCompany();
            contactCompany.setCcompanyID(serverService.getServerID("contactCompany"));
            contactCompany.setCustStatus("01"); // 设置单位状态为未注册单位
            contactCompany.setEntryPersonnel(staffName);


            if (flag != null && flag.equals("web")) {
                ccomcom = new CcomCom();
                contactCompany.setWebstatus("01");
                ccomcom.setCcomRelationId(serverService.getServerID("ccomCom"));
                ccomcom.setCcompanyId(contactCompany.getCcompanyID());
                ccomcom.setState("0");
            }
            parameter = "添加联系单位：（单位名称" + contactCompany.getCompanyName() + ")";
        } else {
            String hql = "from ContactCompany where ccompanyID = ?";
            Object[] param = {contactCompany.getCcompanyID()};
            ContactCompany cc = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hql, param);


            parameter = "修改往来单位（单位名称" + cc.getCompanyName() + "）";
        }
        String defaultURL = basePath + "ea/industry/ea_CompanyProducts.jspa?ccompanyId=" + contactCompany.getCcompanyID();
        if (contactCompany.getCompanyWeb() == null || contactCompany.getCompanyWeb().equals("")) {
            contactCompany.setCompanyWeb(defaultURL);
        }
        String httpUrl = Constant.HTTP + "text=" + contactCompany.getCompanyWeb();

        String httpArg = "&logo=" + basePath + photoPath + "&key=" + Constant.API_KEY;

        contactCompany.setErweimaImage(httpUrl + httpArg);

        CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
        baseBeansList.add(contactCompany);
        baseBeansList.add(cLogBook);
        baseBeansList.add(ccomcom);

        CcomCom ccom = (CcomCom) baseBeanService.getBeanByHqlAndParams(" from  CcomCom where ccompanyId = ?", new Object[]{contactCompany.getCcompanyID()});

        if (contactCompany.getIndustryType().contains("驾校")) {
            TbJpCompany tbJpCompany = (TbJpCompany) baseBeanService.getBeanByHqlAndParams(" from TbJpCompany where companyId = ?", new Object[]{ccom.getComanyId()});
            if (tbJpCompany == null) {
                tbJpCompany = new TbJpCompany();
                tbJpCompany.setTbjpcompanyId(serverService.getServerID("tcom"));
                tbJpCompany.setCompanyId(ccom.getComanyId());
                tbJpCompany.setName(contactCompany.getCompanyName());
                tbJpCompany.setShortname(tcompany.getShortname());
                tbJpCompany.setBusiness(tcompany.getBusiness()); //
                tbJpCompany.setLicnum(tcompany.getLicnum());//
                tbJpCompany.setLicetime(tcompany.getLicetime());
                tbJpCompany.setAddress(contactCompany.getCompanyAddr());
                tbJpCompany.setLegal(contactCompany.getRepresentative());
                tbJpCompany.setCoachnumber(tcompany.getCoachnumber());
                tbJpCompany.setPraticefield(tcompany.getPraticefield());
                tbJpCompany.setFirstIssueDate(tcompany.getFirstIssueDate());
                tbJpCompany.setCreditcode(tcompany.getCreditcode());
                tbJpCompany.setPostcode(tcompany.getPostcode());
                tbJpCompany.setBusistatus(tcompany.getBusistatus());
                tbJpCompany.setBusisEndDate(tcompany.getBusisEndDate());
                tbJpCompany.setCreatedate(new Date());
                tbJpCompany.setDistrict(tcompany.getDistrict());
                tbJpCompany.setUpdatedate(new Date());
                tbJpCompany.setCreateperson(contactCompany.getCompanyName());
                tbJpCompany.setUpdateperson(contactCompany.getCompanyName());
                tbJpCompany.setSyncXlgjStatus("1");
                tbJpCompany.setSyncXlycStatus("1");
                tbJpCompany.setBusiscope(tcompany.getBusiscope());
                tbJpCompany.setInscode(tcompany.getInscode());
                tbJpCompany.setPhone(tcompany.getPhone());
                baseBeansList.add(tbJpCompany);

            } else {
                tbJpCompany.setCompanyId(ccom.getComanyId());
                tbJpCompany.setName(contactCompany.getCompanyName());
                tbJpCompany.setShortname(tcompany.getShortname());
                tbJpCompany.setBusiness(tcompany.getBusiness()); //
                tbJpCompany.setLicnum(tcompany.getLicnum());//
                tbJpCompany.setLicetime(tcompany.getLicetime());
                tbJpCompany.setAddress(contactCompany.getCompanyAddr());
                tbJpCompany.setLegal(contactCompany.getRepresentative());
                tbJpCompany.setCoachnumber(tcompany.getCoachnumber());
                tbJpCompany.setPraticefield(tcompany.getPraticefield());
                tbJpCompany.setFirstIssueDate(tcompany.getFirstIssueDate());
                tbJpCompany.setCreditcode(tcompany.getCreditcode());
                tbJpCompany.setPostcode(tcompany.getPostcode());
                tbJpCompany.setBusistatus(tcompany.getBusistatus());
                tbJpCompany.setBusisEndDate(tcompany.getBusisEndDate());
                tbJpCompany.setCreatedate(new Date());
                tbJpCompany.setDistrict(tcompany.getDistrict());
                tbJpCompany.setUpdatedate(new Date());
                tbJpCompany.setCreateperson(contactCompany.getCompanyName());
                tbJpCompany.setUpdateperson(contactCompany.getCompanyName());
                tbJpCompany.setSyncXlgjStatus("1");
                tbJpCompany.setSyncXlycStatus("1");
                tbJpCompany.setBusiscope(tcompany.getBusiscope());
                tbJpCompany.setInscode(tcompany.getInscode());
                tbJpCompany.setTaxregcer(tcompany.getTaxregcer());
                tbJpCompany.setPhone(tcompany.getPhone());
                baseBeanService.update(tbJpCompany);

            }
        }

        baseBeanService.executeHqlsByParamsList(baseBeansList, null, null);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ccompanyID", contactCompany.getCcompanyID());
        map.put("flag", flag);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";
    }

    /**
     * 跳转添加页面 add / edit
     *
     * @return
     */

    public String toSaveJsp() {
        HttpServletRequest request = ServletActionContext.getRequest();

        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        typelist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20150815wygb79q82p0000000005");
        String hql = "from Contactresource where companyid = ?";
        contactresource = (Contactresource) baseBeanService.getBeanByHqlAndParams(hql,
                new Object[]{account.getCompanyID()});
        Object[] params = {ccompanyID};
        if (!showType.equals("add")) {
            contactCompany = (ContactCompany) baseBeanService
                    .getBeanByHqlAndParams("from ContactCompany where ccompanyID = ? ", params);
        }
        tcompany = (TbJpCompany) baseBeanService.getBeanByHqlAndParams(" from TbJpCompany where companyId = ?", new Object[]{account.getCompanyID()});


        tbSysGeography = codeService.getAllTbSysGeographyBygeoLevel("1");

        String hqlp = "from ProductPackaging where parentName = ? and type=? order by createTime";
        List<BaseBean> comtypelist = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{"公司性质", "招聘"});
        List<BaseBean> scalelist = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{"公司规模", "招聘"});
        request.setAttribute("comtypelist", comtypelist);
        request.setAttribute("scalelist", scalelist);
        return "toSaveJsp";


    }

    /**
     * 获取市级城市和省级城市
     */
    public String getChengshi() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String geoName = request.getParameter("xingzhengs");
        TbSysGeography tbSy = (TbSysGeography) baseBeanService.getBeanByHqlAndParams(" from TbSysGeography where geoName = ?", new Object[]{geoName});

        Object[] params = {tbSy.getGeoBh()};
        List<BaseBean> industryList = baseBeanService.getListBeanByHqlAndParams(" from TbSysGeography where parentBh = ?", params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("industryList", industryList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    /**
     * @param @urlAll
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;

        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        String strRead = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "83a998bddbe09bf1fd5fd633067638e1");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();

            result = sbf.toString();


            result = result.substring(result.indexOf(":") + 2, result.indexOf("}") - 1);

        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return result;
    }

    /**
     * 保存往来单位菜单启用项
     *
     * @return
     */
    public String savecontactCompany() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",
                new Object[]{account.getStaffID()});
        if (contactresource.getContactresourceid() == null || "".equals(contactresource.getContactresourceid())) {
            contactresource.setContactresourceid(serverService.getServerID("contactresource"));
            contactresource.setCname(staff.getStaffName());
            contactresource.setCtime(new Date());
            parameter = staff.getStaffName() + "添加了人事菜单启用项";
        } else {
            contactresource.setUname(staff.getStaffName());
            contactresource.setUtime(new Date());
            parameter = staff.getStaffName() + "修改了人事菜单启用项";
        }
        contactresource.setCompanyid(account.getCompanyID());
        beans = new ArrayList<BaseBean>();
        beans.add(contactresource);
        CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
        beans.add(logBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return "success";
    }

    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", contactCompany);
        return getListContactCompany();
    }

    public DetachedCriteria getListBYDC() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        Map<String, Object> session = ActionContext.getContext().getSession();
        DetachedCriteria dc = DetachedCriteria.forClass(ContactCompany.class, "p");
        connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
        codeRelationList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
        if (search != null && search.equals("search")) {
            this.contactCompany = (ContactCompany) session.get("tablesearch");
            if (null != contactCompany.getCompanyName() && !"".equals(contactCompany.getCompanyName())) {
                dc.add(Restrictions.like("companyName", contactCompany.getCompanyName(), MatchMode.ANYWHERE));
            }
            if (null != contactCompany.getCresponsible() && !"".equals(contactCompany.getCresponsible())) {
                dc.add(Restrictions.like("cresponsible", contactCompany.getCresponsible(), MatchMode.ANYWHERE));
            }
            if (null != contactCompany.getCompanyAddr() && !"".equals(contactCompany.getCompanyAddr())) {
                dc.add(Restrictions.like("companyAddr", contactCompany.getCompanyAddr(), MatchMode.ANYWHERE));
            }
            if (null != contactCompany.getIndustryType() && !"".equals(contactCompany.getIndustryType())) {
                dc.add(Restrictions.eq("industryType", contactCompany.getIndustryType()));
            }
            if (null != contactCompany.getEntryPersonnel() && !"".equals(contactCompany.getEntryPersonnel())) {
                dc.add(Restrictions.like("entryPersonnel", contactCompany.getEntryPersonnel(), MatchMode.ANYWHERE));
            }
            if (null != contactCompany.getAccountID() && !"".equals(contactCompany.getAccountID())) {
                dc.add(Restrictions.like("accountID", contactCompany.getAccountID(), MatchMode.ANYWHERE));
            }
        }
        if (parameter != null && showType != null) {
            if (showType.equals("type")) {
                dc.add(Restrictions.eq("industryType", parameter));
            }
            if (showType.equals("address")) {
                dc.add(Restrictions.like("companyAddr", parameter, MatchMode.ANYWHERE));
            }
            if (showType.equals("connection")) {

                DetachedCriteria cri = DetachedCriteria.forClass(ContactConnection.class, "c");
                cri.add(Property.forName("c.ccompanyID").eqProperty("p.ccompanyID"));
                cri.add(Restrictions.eq("contactConnections", parameter));
                dc.add(Subqueries.exists(cri.setProjection(Projections.property("c.contactConnectionID"))));
            }
        }
        if (flag != null && flag.equals("web")) {
            dc.add(Restrictions.eq("webstatus", "01"));
        }
        if (gtype != null && gtype.equals("office")) {
            DetachedCriteria cri = DetachedCriteria.forClass(CcomCom.class, "z");
            cri.add(Property.forName("z.ccompanyId").eqProperty("p.ccompanyID"));
            cri.add(Restrictions.eq("z.comanyId", account.getCompanyID()));
            dc.add(Subqueries.exists(cri.setProjection(Projections.property("z.ccompanyId"))));

        }
        dc.addOrder(Order.desc("ccompanyID"));
        return dc;
    }

    // 往来单位列表
    public String getListContactCompany() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        typelist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20150815wygb79q82p0000000005");

        HttpServletRequest request = ServletActionContext.getRequest();
        String flexbutton = request.getParameter("flexbutton");
        if (("flexbutton").equals(flexbutton)) {
            String hql = " from ContactCompany c where exists (select s.companyID from CS s"
                    + " where s.companyID = c.ccompanyID and s.staffID = ?)";
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 15 : pageNumber), hql, new Object[]{request.getParameter("staffID")});
        } else {
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 15 : pageNumber), getListBYDC());
        }
        return "list";
    }


    /**
     * 判断往来单位是否已添加到往来单位 判断添加社会单位是否存在
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public String isContactConnection() throws UnsupportedEncodingException {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        int count = 0;
        String name = URLDecoder.decode(onlyCompany, "UTF-8");
        if (name != null && !"".equals(name)) {
            String hql = "select count(*) from ContactCompany where companyName = ? ";
            Object[] params = {name};
            count = baseBeanService.getConutByByHqlAndParams(hql, params);
        } else {
            String hql = "select count(*) from ContactConnection where ccompanyID = ? and companyID = ? and contactConnections = ?";
            Object[] params = {cconnection.getCcompanyID(), account.getCompanyID(),
                    cconnection.getContactConnections()};
            count = baseBeanService.getConutByByHqlAndParams(hql, params);
            if (type != null && !type.equals("")) {
                map.put("c", count);
                JSONObject obj = JSONObject.fromObject(map);
                result = obj.toString();
                type = "";
                return "success";
            }
            if (count <= 0) {
                cconnection.setCompanyID(account.getCompanyID());

                if (null == cconnection.getContactConnectionID() || "".equals(cconnection.getContactConnectionID())) {
                    cconnection.setContactConnectionID(serverService.getServerID("contactConnection"));
                }
                baseBeanService.update(cconnection);
            }
        }
        map.put("c", count);
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 导出往来单位
     */
    public String showExcel() {
        List<BaseBean> contactCompanyList = baseBeanService.getListByDC(getListBYDC());
        excelStream = excelService.showExcel(ContactCompany.columnHeadings(), contactCompanyList);
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        CLogBook cLogBook = logBookService.saveCLogBook(null, "导出往来单位", account);
        baseBeanService.update(cLogBook);
        return "showexcel";
    }

    /**
     * 根据往来单位名称模糊查询列表
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getListContactCompanyByCompanyName() throws UnsupportedEncodingException {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String searchCC = request.getParameter("searchCC");
        Map<String, Object> map = new HashMap<String, Object>();

        if ("searchCC".equals(searchCC)) { // 查询社会往来单位
            DetachedCriteria dc1 = DetachedCriteria.forClass(ContactCompany.class);
            dc1.add(Restrictions.like("companyName", URLDecoder.decode(contactCompany.getCompanyName(), "UTF-8"),
                    MatchMode.ANYWHERE));
            dc1.addOrder(Order.desc("ccompanyID"));
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 10 : pageNumber), dc1);
        } else { // 查询往来单位
            DetachedCriteria dc = DetachedCriteria.forClass(ContactCompanyView.class);
            dc.add(Restrictions.like("companyName", URLDecoder.decode(contactCompany.getCompanyName(), "UTF-8"),
                    MatchMode.ANYWHERE));
            dc.add(Restrictions.eq("companyID", account.getCompanyID()));
            dc.add(Restrictions.like("contactConnections", cconnection.getContactConnections(), MatchMode.ANYWHERE));
            dc.addOrder(Order.desc("companyName"));

            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 10 : pageNumber), dc);
            connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
            map.put("connectionlist", connectionlist);
        }
        map.put("pageForm", pageForm);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    /**
     * ajax保存单位个人关系
     *
     * @return
     */
    public String saveCS() {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        CS cs = new CS();
        cs.setCsID(serverService.getServerID("cs"));
        HttpServletRequest request = ServletActionContext.getRequest();
        cs.setCompanyID(request.getParameter("contactcID"));
        cs.setStaffID(request.getParameter("staffID"));
        beans.add(cs);
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        return "success";
    }

    //往来单位取消网站
    public String cancelWeb() {
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany c where c.ccompanyID= ? ", new Object[]{ccompanyID});
        contactCompany.setWebstatus("");
        ccomcom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom cc where cc.ccompanyId=?", new Object[]{ccompanyID});
        beans = new ArrayList<BaseBean>();
        beans.add(contactCompany);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        baseBeanService.deleteBeanByKey(CcomCom.class, ccomcom.getCcomRelationKey());
        return getListContactCompany();
    }

    //往来单位设置网站ljc
    public String setWeb() {
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany c where c.ccompanyID= ? ", new Object[]{ccompanyID});
        contactCompany.setWebstatus("01");
        ccomcom = new CcomCom();
        ccomcom.setCcomRelationId(serverService.getServerID("ccomCom"));
        ccomcom.setCcompanyId(ccompanyID);
        ccomcom.setState("0");
        beans = new ArrayList<BaseBean>();
        beans.add(contactCompany);
        beans.add(ccomcom);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return getListContactCompany();
    }

    /**
     * 根据往来单位查询银行账户
     *
     * @return
     */
    public String getListRegistration() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        Object[] params = {contactCompany.getCcompanyID()};
        // 查询银行账户
        String hql = "from Registration where ccompanyID= ?";
        List<BaseBean> bankList = baseBeanService.getListBeanByHqlAndParams(hql, params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bankList", bankList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    // 用JSON取得行业类别树
    public String getCDistricts() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        distinctlist = codeService.getCCodeListByPID(account.getCompanyID(), districtPID);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("distinctlist", distinctlist);

        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    public DetachedCriteria getListMTBYDC() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        DetachedCriteria dc = DetachedCriteria.forClass(ContactCompany.class);
        dc.addOrder(Order.desc("ccompanyID"));
        return dc;
    }

    // 往来单位列表
    public String getListContactCompany_Mt() {
        HttpServletRequest request = ServletActionContext.getRequest();
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),
                15, getListMTBYDC());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 跳转添加页面 add / edit
     *
     * @return
     */

    public String toSaveMtJsp() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String hqlp = "from ProductPackaging where parentName = ? and type=? order by createTime";
        List<BaseBean> comtypelist = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{"公司性质", "招聘"});
        List<BaseBean> scalelist = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{"公司规模", "招聘"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("comtypelist", comtypelist);
        map.put("scalelist", scalelist);
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 判断往来单位是否已添加到往来单位 判断添加社会单位是否存在
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public String isContactConnectionMt() throws UnsupportedEncodingException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyID = request.getParameter("companyID");
        Map<String, Object> map = new HashMap<String, Object>();
        int count = 0;
        String name = URLDecoder.decode(onlyCompany, "UTF-8");
        if (name != null && !"".equals(name)) {
            String hql = "select count(*) from ContactCompany where companyName = ? ";
            Object[] params = {name};
            count = baseBeanService.getConutByByHqlAndParams(hql, params);
        } else {
            String hql = "select count(*) from ContactConnection where ccompanyID = ? and companyID = ? and contactConnections = ?";
            Object[] params = {cconnection.getCcompanyID(), companyID,
                    cconnection.getContactConnections()};
            count = baseBeanService.getConutByByHqlAndParams(hql, params);
            if (type != null && !type.equals("")) {
                map.put("c", count);
                JSONObject obj = JSONObject.fromObject(map);
                result = obj.toString();
                type = "";
                return "success";
            }
            if (count <= 0) {
                cconnection.setCompanyID(companyID);
                if (null == cconnection.getContactConnectionID() || "".equals(cconnection.getContactConnectionID())) {
                    cconnection.setContactConnectionID(serverService.getServerID("contactConnection"));
                }
                baseBeanService.update(cconnection);
            }
        }
        map.put("c", count);
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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

    public List<CCode> getTypelist() {
        return typelist;
    }

    public void setTypelist(List<CCode> typelist) {
        this.typelist = typelist;
    }

    public CCodeService getCodeService() {
        return codeService;
    }

    public void setCodeService(CCodeService codeService) {
        this.codeService = codeService;
    }

    public ContactConnection getCconnection() {
        return cconnection;
    }

    public void setCconnection(ContactConnection cconnection) {
        this.cconnection = cconnection;
    }

    public List<CCode> getConnectionlist() {
        return connectionlist;
    }

    public void setConnectionlist(List<CCode> connectionlist) {
        this.connectionlist = connectionlist;
    }

    public String getOnlyCompany() {
        return onlyCompany;
    }

    public void setOnlyCompany(String onlyCompany) {
        this.onlyCompany = onlyCompany;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CCode> getCodeRelationList() {
        return codeRelationList;
    }

    public void setCodeRelationList(List<CCode> codeRelationList) {
        this.codeRelationList = codeRelationList;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Contactresource getContactresource() {
        return contactresource;
    }

    public void setContactresource(Contactresource contactresource) {
        this.contactresource = contactresource;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getDistrictPID() {
        return districtPID;
    }

    public void setDistrictPID(String districtPID) {
        this.districtPID = districtPID;
    }

    public List<CCode> getDistinctlist() {
        return distinctlist;
    }

    public void setDistinctlist(List<CCode> distinctlist) {
        this.distinctlist = distinctlist;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public CcomCom getCcomcom() {
        return ccomcom;
    }

    public void setCcomcom(CcomCom ccomcom) {
        this.ccomcom = ccomcom;
    }

    public Object getRs() {
        return rs;
    }

    public void setRs(Object rs) {
        this.rs = rs;
    }

    public TbJpCompany getTcompany() {
        return tcompany;
    }

    public void setTcompany(TbJpCompany tcompany) {
        this.tcompany = tcompany;
    }

    public List<TbSysGeography> getTbSysGeography() {
        return tbSysGeography;
    }

    public void setTbSysGeography(List<TbSysGeography> tbSysGeography) {
        this.tbSysGeography = tbSysGeography;
    }
}
