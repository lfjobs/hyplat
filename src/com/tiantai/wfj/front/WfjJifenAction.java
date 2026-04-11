package com.tiantai.wfj.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.base.action.BaseAction;
import hy.ea.util.Constant;

import java.io.IOException;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.batch.chinapay.bean.BatchPayBean;
import com.batch.chinapay.meth.BatchPay;
import com.daifu.chinapay.model.bean.BankWfjBean;
import com.tiantai.wfj.bo.WfjGuize;
import com.tiantai.wfj.bo.WfjJifen;

/**
 * 微金币(积分)
 * @author zg
 *
 */
@Controller
@Scope(value="prototype")
public class WfjJifenAction extends BaseAction<WfjJifen>{
	private static final long serialVersionUID = -2978638926456928144L;
	private BankWfjBean entity;
	//预设规则 直接访问这个路径 ea/jifen/ea_saveGuize.jspa
	//只能预设展示在页面上的积分任务
	//其他的请预设
	public String saveGuize(){
		String gzCompany=request.getParameter("gzCompany");
		String gzName=request.getParameter("gzName");
		String gzScore=request.getParameter("gzScore");
		String gzOrder=request.getParameter("gzOrder");
		String gzType=request.getParameter("gzType");
		String gzUnit=request.getParameter("gzUnit");
		if(gzName==null){
			return "toAddGuize";
		}
		WfjGuize wg = new WfjGuize();
		wg.setWfjGuizeId(serverService.getServerID("WfjGuize"));
		wg.setCompanyId(gzCompany.equals("")?Constant.COMPAYN_ID:gzCompany);
		wg.setWfjGuizeCalc(gzType);
		wg.setWfjGuizeName(gzName);
		wg.setWfjGuizeOrder(Integer.parseInt(gzOrder));
		wg.setWfjGuizeScore(Integer.parseInt(gzScore));
		wg.setWfjGuizeUnit(Integer.parseInt(gzUnit));
		baseBeanService.save(wg);
		return "toAddGuize";
	}
	
	
	
	
	//批量代付测试第一步   更改配置文件cp_config.properties
	//http://127.0.0.1:8080/hyplat/ea/jifen/ea_A.jspa
	public String A(){
		BatchPayBean bean= BatchPay.createFile();
		request.setAttribute("chargeInput", bean);
		return "aaa";
	}
	//批量代付测试第二步
	public String B(){
		BatchPayBean bean=null;
		String rep=null;
		try {
			String fileName=request.getParameter("fileName");
			String fileContent=request.getParameter("fileContent");
			Map<String,Object> map=BatchPay.batchSer(fileName,fileContent);
			if(map.get("result").equals(0)){
				bean=(BatchPayBean) map.get("bean");
				rep="bbb";
			}else{
				rep="ccc";
			}
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
		request.setAttribute("chargeInput", bean);
		return rep;
	}
	
	public BankWfjBean getEntity() {
		return entity;
	}
	public void setEntity(BankWfjBean entity) {
		this.entity = entity;
	}
	
}
