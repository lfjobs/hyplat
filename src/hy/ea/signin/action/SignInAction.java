package hy.ea.signin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.codec.Base64;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.facebody.model.v20191230.AddFaceEntityResponse;
import com.aliyuncs.facebody.model.v20191230.AddFaceResponse;
import com.aliyuncs.facebody.model.v20191230.RecognizeFaceResponse;
import com.aliyuncs.facebody.model.v20191230.SearchFaceResponse;
import com.tiantai.wfj.bo.TEshopCusFace;
import hy.ea.bo.Company;
import hy.ea.signin.service.FaceBodyService;
import hy.ea.signin.service.OSSService;
import hy.ea.signin.service.SignInService;
import hy.ea.interceptor.JsonHandlerException;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 签到
 */
public class SignInAction {
	private static final Logger logger = LoggerFactory.getLogger(SignInAction.class);

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SignInService signInService;
    @Resource
    private FaceBodyService faceBodyService;
    @Resource
    private OSSService ossService;

    private Object result;

    /**
     * 根据终端机编号获取公司信息
     *
     * @return
     */
    public String toSignIn() throws JsonHandlerException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String posNum = request.getParameter("posNum");
        Company company = signInService.getCompanyByPosNum(posNum);
        request.setAttribute("company", company);
        request.setAttribute("posNum", posNum);
        return "signin";
    }

    /**
     * 查询个人所属公司
     *
     * @param @faceEntityId faceEntityId
     */
    public String findCompanies() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String faceEntityId = request.getParameter("faceEntityId");

        String hql = "from TEshopCusFace where faceEntityId = ?";
        TEshopCusFace cusFace = (TEshopCusFace) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{faceEntityId});

        StringBuffer sql = new StringBuffer();
        sql.append("select * from(SELECT COM.COMPANYNAME,\n" +
                "       COM.COMPANYID,\n" +
                "       CUS.ACCOUNT,\n" +
                "       CT.LOGOPATH,\n" +
                "       COM.HXID,\n" +
                "       CT.COMSCALE,\n" +
                "       CT.INDUSTRYTYPE,\n" +
                "       CT.COMPANYADDR,\n" +
                "       CUS.STAFFID,\n" +
                "       CT.CCOMPANYID,ct.seqs FROM T_ESHOP_CUSCOM_COMPANY CUSC\n" +
                "LEFT JOIN  DTCOMPANY COM ON COM.COMPANYID = CUSC.COMPANYID\n" +
                "LEFT JOIN T_ESHOP_CUSCOM CUS ON CUS.SCCID = CUSC.SCCID\n" +
                "LEFT JOIN  DT_CCOM_COM CCC ON CCC.COMPNAY_ID = COM.COMPANYID\n" +
                "LEFT JOIN  DTCONTACTCOMPANY CT ON  CT.CCOMPANYID = CCC.CCOMPANY_ID\n");
        sql.append("WHERE CUSC.SCCID = ? union ");
        sql.append(" select com.companyname, com.companyid, cus.account, t.logopath,com.hxID,t.comScale,t.industryType,t.companyAddr,cus.staffid,t.ccompanyID ,t.seqs from T_ESHOP_CUSCOM cus, dtCompany com, dt_ccom_com d, dtcontactcompany t where cus.companyid = com.companyid ");
        sql.append(" and d.ccompany_id = t.ccompanyid and com.companyid = d.compnay_id and cus.account in (select j.account  from T_ESHOP_CUSCOM j  where j.sccid  = ?) union  select com.companyname, com.companyid, ccus.account, t.logopath,com.hxID,t.comScale,t.industryType,t.companyAddr,staff.staffid,ccompanyID ,t.seqs from  dt_ccom_com d, ");
        sql.append(" dtCompany com,dtcontactcompany t,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff, T_ESHOP_CUSCOM   ccus where staff.staffid = cos.staffid and staff.reference = cus.account and cos.companyid=com.companyid and cos.status='01' and cos.cosStatus='50' and com.companyid=d.compnay_id ");
        sql.append(" and d.ccompany_id=t.ccompanyid  and com.companyid = ccus.companyid and cus.sccid = ?");
        sql.append(") sc order by to_number(sc.seqs)");

        String sccid = cusFace.getSccId();
        List<String> params = new ArrayList<>();
        params.add(sccid);
        params.add(sccid);
        params.add(sccid);

        List<BaseBean> companyList = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray());
        logger.info("调试信息");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < companyList.size(); i++) {
            Object[] company = (Object[]) (Object) companyList.get(i);
            if (!jsonArray.contains(isNull(company[1]))) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.accumulate("companyName", isNull(company[0]));
                jsonObj.accumulate("companyId", isNull(company[1]));
                jsonObj.accumulate("account", isNull(company[2]));
                jsonObj.accumulate("logo", isNull(company[3]));
                jsonObj.accumulate("hxID", isNull(company[4]));
                jsonObj.accumulate("comScale", isNull(company[5]));
                jsonObj.accumulate("industryType", isNull(company[6]));
                jsonObj.accumulate("companyAddr", isNull(company[7]));
                jsonObj.accumulate("staffID", isNull(company[8]));
                jsonObj.accumulate("ccompanyID", isNull(company[9]));
                jsonArray.add(jsonObj);
            }
        }
        this.result = jsonArray;
        return "success";
    }

    /**
     * null转空字符串
     *
     * @param tep
     * @return
     */
    private Object isNull(Object tep) {
        if (tep == null || "null".equals(tep)) {
            return "";
        } else {
            return tep;
        }
    }

    /**
     * FaceId签到
     * @return
     */
    public String signIn() throws JsonHandlerException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String faceEntityId = request.getParameter("faceEntityId");
        String companyId = request.getParameter("companyId");

        Map<String, Object> map = signInService.signIn(faceEntityId, companyId);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;
        return "success";
    }

    /**
     * 微分金账号绑定FaceEntityId
     *
     * @return
     */
    public String bindFaceEntityId() throws JsonHandlerException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String mobile = request.getParameter("mobile");
        String faceEntityId = request.getParameter("faceEntityId");

        TEshopCusFace cusFace = signInService.bindFaceEntityId(faceEntityId, mobile);
        JSONObject jo = JSONObject.fromObject(cusFace);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 识别人脸信息并返回质量检查
     * @return
     * @throws ClientException
     */
    public String recognizeFace() throws JsonHandlerException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String localFileUrl = Base64.decodeStr(request.getParameter("localFileUrl"));

        String objectName = UUID.randomUUID() + ".jpg";
        String ossFileUrl = ossService.uploadObject(localFileUrl, objectName);

        logger.info("识别人脸信息并返回质量检查");
        logger.info("值：{}", ossFileUrl);

        // 检测图片综合得分
        RecognizeFaceResponse recognizeFaceResponse = faceBodyService.recognizeFace(ossFileUrl);
        Float score = recognizeFaceResponse.getData().getQualities().getScoreList().get(0);

        Map<String, Object> map = new HashMap<>();
        map.put("ossFileUrl", ossFileUrl);
        map.put("score", score);

        JSONObject js = JSONObject.fromObject(map);
        result = js.toString();
        return "success";
    }

    /**
     * 添加人脸到样本库
     * @return
     * @throws ClientException
     */
    public String addFace() throws JsonHandlerException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ossFileUrl = Base64.decodeStr(request.getParameter("ossFileUrl"));

        String entityId = UUID.randomUUID().toString().replaceAll("-", "");
        String extraData = "";

        logger.info("添加样本");
        AddFaceEntityResponse addFaceEntityResponse = faceBodyService.addFaceEntity(entityId);

        logger.info("添加样本图片");
        AddFaceResponse addFaceResponse = faceBodyService.addFace(ossFileUrl, entityId, extraData);

        Map<String, Object> map = new HashMap<>();
        map.put("entityId", entityId);
        map.put("faceId", addFaceResponse.getData().getFaceId());
        map.put("qualitieScore", addFaceResponse.getData().getQualitieScore());

        JSONObject js = JSONObject.fromObject(map);

        logger.info("调试信息");

        result = js.toString();
        return "success";
    }

    /**
     * 搜索人脸信息
     * @return
     * @throws ClientException
     */
    public String searchFace() throws ClientException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ossFileUrl = Base64.decodeStr(request.getParameter("ossFileUrl"));

        logger.info("==人脸比对样本==");
        try {
            SearchFaceResponse searchFaceResponse = faceBodyService.searchFace(ossFileUrl);

            List<SearchFaceResponse.Data.MatchListItem> matchList = searchFaceResponse.getData().getMatchList();
            if (matchList.isEmpty() || matchList.get(0).getFaceItems().isEmpty()) {
                logger.info("-匹配上人脸-");
                return "success";
            }

            logger.info("-匹配上人脸-");
            SearchFaceResponse.Data.MatchListItem.FaceItemsItem faceItemsItem = matchList.get(0).getFaceItems().get(0);

            Map<String, Object> map = new HashMap<>();
            map.put("entityId", faceItemsItem.getEntityId());
            map.put("faceId", faceItemsItem.getFaceId());
            map.put("score", faceItemsItem.getScore());
            map.put("extraData", faceItemsItem.getExtraData());

            JSONObject js = JSONObject.fromObject(map);
            logger.info("调试信息");
            result = js.toString();
            return "success";
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
