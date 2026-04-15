package hy.ea.office.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import com.wechatpay.service.WordGenerator;
import hy.ea.bo.office.DoubaoParam;
import hy.ea.bo.office.DoubaoSession;
import hy.ea.office.service.DoubaoAIService;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class DoubaoAIAction {
	@Resource
	private BaseBeanService baseBeanService;

	private DoubaoParam doubaoParam;
	@Resource

	private DoubaoAIService doubaoAIService;
	@Resource
	private UpLoadFileService fileService;
     private String result;
     private String type;
     private DoubaoSession doubaoSession;

     private File file;
     private String fileFileName;
    public String routeMode(){
		Map<String,String> res = doubaoAIService.routeMode(type,doubaoParam);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reasoning",res.get("reasoning"));
		map.put("message",res.get("message"));
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
    	return "success";
	}

	public String updateFile(){

		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String dir = "ulogin";
		if(tc!=null){
			dir = tc.getStaffid();
		}
		String filepath = fileService.savePhoto(path, fileFileName, file, "ai",
				dir);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filepath",filepath);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
    	return "success";
	}

	public String streamChat() {

		// 1. 获取 HttpServletResponse 对象
		HttpServletResponse response = ServletActionContext.getResponse();
		// 2. 设置 SSE 响应头（关键）
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		try {
			// 3. 获取输出流
			PrintWriter out = response.getWriter();

			// 4. 初始化 ArkService（复用你原来的代码）
		//	String apiKey = System.getenv("ARK_API_KEY");
			String  apiKey = "e448ff60-7748-41e8-8d18-d60447a64596";
			ArkService service = ArkService.builder()
					.apiKey(apiKey)
					.baseUrl("https://ark.cn-beijing.volces.com/api/v3")
					.build();

			// 5. 构建请求参数
			List<ChatMessage> messages = new ArrayList<>();
			ChatMessage userMessage = ChatMessage.builder()
					.role(ChatMessageRole.USER)
					.content(doubaoParam.getText()) // 前端传入的提问内容
					.build();
			messages.add(userMessage);

			ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
					.model("doubao-seed-2-0-pro-260215")
					.messages(messages)
					.stream(true)
					.thinking(new ChatCompletionRequest.ChatCompletionRequestThinking("disabled"))
					.build();

//			// 6. 流式处理返回结果，推送给前端
//			service.streamChatCompletion(chatCompletionRequest)
//					.doOnError(e -> {
//						// 异常时推送错误信息
//						out.write("data: 【错误】" + e.getMessage() + "\n\n");
//						out.flush();
//						out.close();
//					})
//			 .blockingForEach(responseData -> {
//				if (responseData.getChoices() != null && !responseData.getChoices().isEmpty()) {
//					String content = String.valueOf(responseData.getChoices().get(0).getMessage().getContent());
//					if (content != null) {
//						System.out.print(content); // 注意用print而非println，保持内容连续
//						out.write("data: " + content + "\n\n");
//							// 8. 刷新输出流，立即推送给前端
//							out.flush();
//					}
//				}
//			});

			// 9. 推送完成，关闭输出流
			out.write("data: [DONE]\n\n"); // 标记结束
			out.flush();
			out.close();
			// 关闭 ArkService
			service.shutdownExecutor();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Struts2 不需要返回视图，直接返回 null
		return null;
	}
	/**
	 *
	 * 保存记录

	 */
      public String addSession(){
		  HttpServletRequest request = ServletActionContext.getRequest();
		  HttpSession session = request.getSession();
		  SessionWrap sw = SessionWrap.getInstance();
		  TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				  SessionWrap.KEY_SHOPCUSCOM);
		  Map<String, Object> map = new HashMap<String, Object>();
		  if(tc!=null){
			  String id = doubaoAIService.addSession(doubaoSession,tc.getSccId());
			  map.put("id", id);

		  }

		  JSONObject obj = JSONObject.fromObject(map);
		  result = obj.toString();
		  return "success";
	  }

	/**
	 *
	 * 获取记录
	 * @return
	 */
	public String getListRecord(){
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		Map<String, Object> map = new HashMap<String, Object>();

		if(tc!=null) {
			 List<BaseBean> list = doubaoAIService.getListRecord(tc.getSccId());
			 map.put("list", list);

		 }
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
      	return "success";
	  }

	/**
	 *
	 * 删除记录
	 * @return
	 */
	public String deleteRecord(){

		doubaoAIService.deleteRecord(doubaoSession.getId());


		  return "success";
	  }

	/**
	 *
	 * 创建文档
	 * @return
	 */
	public String createDoc(){
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String filepath = "upload_files/ai/"+tc.getStaffid()+"/";

		try {
			String fileName = WordGenerator.createWord(doubaoSession.getText(),path+filepath);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("wordpath", "/"+filepath+fileName);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		} catch (IOException e) {
               e.printStackTrace();
		}
		return "success";
	  }
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DoubaoParam getDoubaoParam() {
		return doubaoParam;
	}

	public void setDoubaoParam(DoubaoParam doubaoParam) {
		this.doubaoParam = doubaoParam;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public DoubaoSession getDoubaoSession() {
		return doubaoSession;
	}

	public void setDoubaoSession(DoubaoSession doubaoSession) {
		this.doubaoSession = doubaoSession;
	}
}
