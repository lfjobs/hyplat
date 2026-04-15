package hy.ea.office.service.impl;

import com.alibaba.fastjson2.JSON;
import com.volcengine.ark.runtime.model.responses.response.ResponseObject;
import com.wechatpay.service.DoubaoSDK;
import hy.ea.bo.office.DoubaoParam;
import hy.ea.bo.office.DoubaoSession;
import hy.ea.office.service.DoubaoAIService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class DoubaoAiServiceImpl implements DoubaoAIService {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private BaseBeanDao baseBeanDao;
	@Resource
	private ServerService serverService;
	public Map<String,String> routeMode(String type, DoubaoParam doubaoParam){
		Map<String,String> mp = new HashMap<>();
		if("chat".equals(type)||"doc".equals(type)||"translate".equals(type)){


			if("translate".equals(type)){
				if("".equals(doubaoParam.getText())){
					doubaoParam.setText("分析一下上传的文件内容");
				}else{
					boolean isChinese = DoubaoSDK.containsChinese(doubaoParam.getText());
					String prompt;

					if (isChinese) {
						// 中文 → 英文
						prompt = "你是专业翻译，只输出纯英文，不要多余内容，不要解释。翻译：" + doubaoParam.getText();
					} else {
						// 英文 → 中文
						prompt = "你是专业翻译，只输出纯中文，不要多余内容，不要解释。翻译：" + doubaoParam.getText();
					}

					doubaoParam.setText(prompt);
				}


			}

			if(doubaoParam.getFormateFile()==null||doubaoParam.getFormateFile().equals("")) {


				ResponseObject resp = DoubaoSDK.textMode(doubaoParam.getText());
		//		String reasoning = DoubaoSDK.extractReasoningText(resp);

				String message = DoubaoSDK.extractCoreText(resp);
			//	mp.put("reasoning", reasoning);
				mp.put("message", message);
			}else{
				if("doc".equals(type)||"chat".equals(type)){//如果是分析文档就

					if("".equals(doubaoParam.getText())){
						doubaoParam.setText("分析一下上传的文件内容");
					}
				}

				ResponseObject resp = DoubaoSDK.multiMode(doubaoParam.getFlist(),doubaoParam.getFormateFile(),doubaoParam.getText());
			//	String reasoning = DoubaoSDK.extractReasoningText(resp);

				String message = DoubaoSDK.extractCoreText(resp);
			//	mp.put("reasoning", reasoning);
				mp.put("message", message);
			}



		}else if("image".equals(type)){
			List<String> imglist = new ArrayList<>();
			// 转 String 数组
			String[] strArray = JSON.parseObject(doubaoParam.getFlist().get(0), String[].class);
				for (String s : strArray) {
					imglist.add(s);
				}
			String imgurl = DoubaoSDK.imageGenerations(doubaoParam.getText(),"","2K",imglist);
			mp.put("message", imgurl);
		}else if("video".equals(type)){
			String videoUrl = DoubaoSDK.videoGeneration(doubaoParam.getText(),doubaoParam.getFlist());
			mp.put("message", videoUrl);

		}

		return mp;
	}


	/**
	 *
	 * 保存记录
	 * @param doubaoSession
	 * @param sccId
	 */
	public  String addSession(DoubaoSession doubaoSession,String sccId){
		doubaoSession.setId(serverService.getServerID("id"));
		doubaoSession.setCreateDate(new Date());
		doubaoSession.setSccId(sccId);
		baseBeanDao.save(doubaoSession);
		return doubaoSession.getId();


	}

	/**
	 *
	 * 获取记录
	 * @param sccId
	 * @return
	 */
	public List<BaseBean> getListRecord(String sccId){
         String hql = "from DoubaoSession where sccId = ? order by createDate";
         List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{sccId});

		return  list;
	}

	/**
	 *
	 * 删除记录
	 * @param id
	 */
	public void deleteRecord(String id){
		String hql = "delete from DoubaoSession where id = ?";
		baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,
				new String[]{hql}, new Object[]{id});


	}

}
