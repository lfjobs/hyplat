package hy.ea.office.action;


import com.faceSDK.FaceRecSDK;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.office.FaceRec;
import hy.ea.office.service.FaceRecService;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 人脸闸机业务
 */
@Controller
@Scope("prototype")
public class FaceRecMangeAction {

 private FaceRec faceRec;
 private int pageNumber;
 private String result;
 private PageForm pageForm;
 private int pageSize;
 private String search;
 @Resource
 private FaceRecService faceRecService;

    /**
     * 人脸闸机查询
     * @return
     */
   public String toSearch(){
       Map<String, Object> session = ActionContext.getContext().getSession();
       session.put("faceRec", faceRec);
       return getListFaceRec();
   }

    public String getListFaceRec(){

          Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount)session.get("account");
        if(account == null){
              return "nologin";
        }
        if(search!=null&&search.equals("search")) {
            faceRec = (FaceRec) session.get("faceRec");
        }else{
            faceRec = null;

        }
         pageForm = faceRecService.getListFaceRec(faceRec,(pageSize==0?5:pageSize),(pageNumber==0?1:pageNumber),account.getCompanyID());
         return "tofacelist";
    }

    /**
     *
     * 人脸闸机添加修改保存
     * @return
     */
    public String addOrUpdate(){
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");

           faceRecService.addEquip(faceRec,account.getStaffID(),account.getCompanyID());
        return "success";

    }

    /**
     *
     * 删除人脸闸机
     * @return
     */
    public String deleteFaceRec(){
        faceRecService.deleteEquip(faceRec.getFrkey());
      return "success";
    }


    /**
     *
     * 判断设备号是否重复
     * @return
     */
    public  String  checkRepPosNum(){

        String result = faceRecService.checkRecSn(faceRec.getSn(),faceRec.getFrId());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("result",result);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     *
     * 启动服务
     * @return
     */
    public String startService(){

        boolean configServer = FaceRecSDK.startConfigServer();
        boolean dataServer = FaceRecSDK.startDataServer();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("configServer",configServer);
        map.put("dataServer",dataServer);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     *
     * 判断设备是否在线
     * @return
     */
    public String getCameraOnlineState(){
        boolean onlineState = FaceRecSDK.getCameraOnlineState(faceRec.getSn());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("onlineState",onlineState);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }
    public FaceRec getFaceRec() {
        return faceRec;
    }

    public void setFaceRec(FaceRec faceRec) {
        this.faceRec = faceRec;
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

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
