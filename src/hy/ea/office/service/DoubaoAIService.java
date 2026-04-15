package hy.ea.office.service;

import hy.ea.bo.office.DoubaoParam;
import hy.ea.bo.office.DoubaoSession;
import hy.plat.bo.BaseBean;

import java.util.List;
import java.util.Map;

public interface DoubaoAIService {



	public Map<String,String> routeMode(String type, DoubaoParam doubaoParam);


	public  String addSession(DoubaoSession doubaoSession,String sccId);


	public List<BaseBean> getListRecord(String sccId);

	public void deleteRecord(String id);

	
}
