package hy.ea.driving.action.elkc;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.bo.driving.DrivingDealCheGuan;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.driving.service.ElkcCompanyConfigService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

@Controller
@Scope("prototype")
public class ElkcCompanyConfigAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	private TbElycCompanyConfig tbElycCompanyConfig;
	@Resource
	private ElkcCompanyConfigService companyComfigService;



	private String result;

	/**
	 *
	 * 保存或者更新驾校预约配置信息
	 * @return
	 */
	public String saveCompanyConfig(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account==null){
			return "login";
		}

		companyComfigService.saveConfig(tbElycCompanyConfig,account.getCompanyID(),account.getStaffID());

		return "success";
	}

	/**
	 *
	 * 修改时获取内容
	 * @return
	 */
	public String findCompanyConfig(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account==null){
			return "login";
		}

		tbElycCompanyConfig = companyComfigService.findCompanyConfig(account.getCompanyID());

         return "toconfig";
	}

	/**
	 *
	 * 初始化约车配置
	 * @return
	 */
	public String initOrderConfig(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		   tbElycCompanyConfig = companyComfigService.findCompanyConfig(account.getCompanyID());
		   Map<String, Object> map = new HashMap<String, Object>();

			if(this.tbElycCompanyConfig == null) {
				 map.put("msg","可预约时间段信息初始化成功！");

			} else if(this.tbElycCompanyConfig.getGenday() != null) {

				Integer days = Integer.valueOf(this.tbElycCompanyConfig.getGenday());
				Date date = new Date();

				for(int i = 0; i < days.intValue(); ++i) {

					companyComfigService.createTeacherTime(account.getCompanyID(), account.getStaffID(), DateUtilElkc.addday(date, i));
				}

				map.put("msg","可预约时间段信息初始化成功！");

			}else{
				map.put("msg","请先完善预约配置信息并保存配置！");
			}
	    	JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		 return "success";
	}


	/**
	 *
	 * 初始化约车配置
	 * @return
	 */
	public void timeOrderConfig(){

		List<BaseBean>   configlist = companyComfigService.getCompanyConfig();
		for (int j=0;j<configlist.size();j++) {
			TbElycCompanyConfig tbElycCompanyConfig = (TbElycCompanyConfig)configlist.get(j);
			if (tbElycCompanyConfig != null) {
				Integer days = Integer.valueOf(tbElycCompanyConfig.getGenday());
				Date date = new Date();
				for (int i = 0; i < days.intValue(); ++i) {
					companyComfigService.createTeacherTime(tbElycCompanyConfig.getCompanyId(),"database", DateUtilElkc.addday(date, i));
				}
			}
		}
	}
	public TbElycCompanyConfig getTbElycCompanyConfig() {
		return tbElycCompanyConfig;
	}

	public void setTbElycCompanyConfig(TbElycCompanyConfig tbElycCompanyConfig) {
		this.tbElycCompanyConfig = tbElycCompanyConfig;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
