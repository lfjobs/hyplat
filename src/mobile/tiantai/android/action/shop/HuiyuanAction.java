package mobile.tiantai.android.action.shop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.wfj.bo.WfjHuiyuan;

import hy.ea.util.MD5Util;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class HuiyuanAction extends ActionSupport implements ServletRequestAware,
ServletResponseAware{

	private static final long serialVersionUID = 1L;
	@Resource
	protected BaseBeanService baseBeanService;
	@Resource
	private ServerService serviceService;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	
	private Object result;
	
	//激活会员
	public String jihuoCard(){
		String usrId=request.getParameter("staffId");
		String kahao=request.getParameter("kahao");
		String kami=request.getParameter("kami");
		JSONObject jrt=new JSONObject();
		if(usrId==null||usrId.equals("")){//用户id不能为空
			jrt.accumulate("result", 1);
		}else if(kahao==null||kahao.trim().equals("")){//卡号不能为空
			jrt.accumulate("result", 2);
		}else if(kami==null||kami.equals("")){//卡密不能为空
			jrt.accumulate("result", 3);
		}else{
			
			
			try {
				String hql="from WfjHuiyuan where huiyuanSn=?";
				String staffhql="update Staff set vipno='0' where  staffid = ? ";
				WfjHuiyuan entity=(WfjHuiyuan) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{new Long(kahao.trim())});
				if(entity!=null){
					if(!entity.getHuiyuanPas().equalsIgnoreCase(MD5Util.md5s(kami.trim()))){
						jrt.accumulate("result", 4);//卡密码不正确
					}else if(entity.getHuiyuanState()==1){
						jrt.accumulate("result", 5);//卡已被使用
					}else{
						entity.setStaffId(usrId.trim());
						entity.setHuiyuanState(1);
						baseBeanService.update(entity);
						//修改表示 修改这个的人状态 已经激活
						baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{staffhql}, new String[]{usrId.trim()});
						jrt.accumulate("result", 0);
					}
				}else{//不存在
					jrt.accumulate("result", 9);
				}
			} catch (Exception e) {
				jrt.accumulate("result", 8);//系统错误
			}
			
		}
		result=jrt;
		return Action.SUCCESS;
	}
	
	//使用会员卡
	public String shiyongCard(){
		String kahao=request.getParameter("kahao");
		JSONObject jrt=new JSONObject();
		if(kahao==null||kahao.trim().equals("")){
			jrt.accumulate("result", 1);//卡号不能为空
		}else{
			try {
				String hql = "from WfjHuiyuan where huiyuanSn=? and huiyuanState=?";
				WfjHuiyuan entity= (WfjHuiyuan)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{new Long(kahao),1});
				if(entity==null||entity.getStaffId()==null||entity.getStaffId().equals("")){
					jrt.accumulate("result", 2);//卡号不存在
				}else{
					jrt.accumulate("staffId", entity.getStaffId());//root为默认值其他为staffid
					jrt.accumulate("result", 0);
				}
			} catch (Exception e) {
				jrt.accumulate("result", 8);//系统错误
			}
		}
		result=jrt;
		return Action.SUCCESS;
	}
	//使用会员卡
	public String shiyongSafeCard(){
		JSONObject jrt=new JSONObject();
		String kahao=request.getParameter("kahao");
		String kami=request.getParameter("kami");
		if(kahao==null||kahao.trim().equals("")){
			jrt.accumulate("result", 1);//卡号不能为空
		}else{
			try {
				String hql = "from WfjHuiyuan where huiyuanSn=? and huiyuanState=?";
				WfjHuiyuan entity= (WfjHuiyuan)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{new Long(kahao.trim()),1});
				if(entity==null||entity.getStaffId()==null||entity.getStaffId().equals("")){
					jrt.accumulate("result", 2);//卡号不存在
				}else if(!entity.getHuiyuanPas().equalsIgnoreCase(MD5Util.md5s(kami.trim()))){
					jrt.accumulate("result", 3);//卡密不正确
				}else{
					jrt.accumulate("staffId", entity.getStaffId());
					jrt.accumulate("result", 0);
				}
			} catch (Exception e) {
				jrt.accumulate("result", 8);//系统错误
			}
		}
		result=jrt;
		return Action.SUCCESS;
	}
	@Override
	public void setServletResponse(HttpServletResponse rps) {
		this.response=rps;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.request=req;
		
	}
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
