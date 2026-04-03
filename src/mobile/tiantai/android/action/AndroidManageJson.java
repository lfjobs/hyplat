package mobile.tiantai.android.action;

import hy.ea.util.DateJsonValueProcessor;
import hy.plat.bo.PageForm;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/**
 * @see hy.plat.bo.PageForm
 * @author admin
 *
 */
@Controller
@Scope("prototype")
public class AndroidManageJson {
	private String result;
	/**
	 * @param 分页
	 */
	private PageForm pageForm;
	/**
	 * @param  处理消息
	 */
	private String message;
	
	/**
	 * @param 01 已转发处理后  00  未转发处理
	 */
	private String isforward;  
	
	/**
	 * 获取短信列表信息转换Lits集合为json格式数据
	 * @return
	 */
	public String getTelMessageAndroidList(){
		if(isforward!=null&&"01".equals(isforward)){
			JSONObject jsonObjList = new JSONObject();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出  
			if(pageForm!=null){
				try {
					JSONArray jsonArray2 = JSONArray.fromObject( pageForm.getList() ,jsonConfig);
					jsonObjList.accumulate("list", jsonArray2);
					jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
					jsonObjList.accumulate("pageSize", pageForm.getPageSize());
					jsonObjList.accumulate("recordCount", pageForm.getRecordCount());
					jsonObjList.accumulate("pageCount", pageForm.getPageCount());
					jsonObjList.accumulate("message", "成功");
				} catch (Exception e) {
					e.printStackTrace();
					jsonObjList.accumulate("content", "数据异常");
					jsonObjList.accumulate("message", "失败");
				}
			}else {
				jsonObjList.accumulate("content", "无数据");
				jsonObjList.accumulate("message", "成功");
			}
			result=jsonObjList.toString();
			return "success";
		}else {
			return "extension_android_forward";
		}
	}
	
	/**
	 * Android 请求转发  发送短信
	 * @return
	 */
	public String sendMessageAndroid(){
		return "extension_android_forward_send";
	}
	
	/**
	 * Android 请求转发 查询短信
	 * @return
	 */
	public String toSearchAndroid(){
		return "extension_android_forward_search";
	}
	
	/**
	 * Android 请求转发 删除短息
	 * @return
	 */
	public String delTelMessageAndroid(){
		return "extension_android_forward_del";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getIsforward() {
		return isforward;
	}


	public void setIsforward(String isforward) {
		this.isforward = isforward;
	}


	public PageForm getPageForm() {
		return pageForm;
	}


	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}

}
