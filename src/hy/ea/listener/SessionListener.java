package hy.ea.listener;

import hy.ea.action.CLoginAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.service.CLogBookService;
import hy.ea.util.SpringContextUtil;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Controller;

@Controller
public class SessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		//session为null是 全局的。  当一个用户都没有的 时候认为 服务器 刚刚重启。将所有账户标记离线
		if (session.getServletContext().getAttribute("online") == null) {
			BaseBeanService baseBeanService = (BaseBeanService) SpringContextUtil
					.getBean("baseBeanServiceImpl", BaseBeanService.class);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { "update CAccount set accountOnLine = ? " },
					new Object[] { "00" });
		}
		int i = (Integer) (null == session.getServletContext().getAttribute(
				"online") ? 0 : session.getServletContext().getAttribute(
				"online"));// 获得当前在线人数，并将其加一
		session.getServletContext().setAttribute("online", i + 1);

	}

	private CLoginAction cLoginAction;

	@SuppressWarnings("static-access")
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		CAccount account = (CAccount) arg0.getSession().getAttribute("account");
		BaseBeanService baseBeanService = (BaseBeanService) SpringContextUtil
				.getBean("baseBeanServiceImpl", BaseBeanService.class);
		CLogBookService cLogBookService = (CLogBookService) SpringContextUtil
				.getBean("CLogBookServiceImpl", CLogBookService.class);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (account != null) {
			account = (CAccount) baseBeanService.getBeanByKey(CAccount.class,
					account.getAccountKey());
			account.setAccountOnLine("00");
			beans.add(account);
			CLogBook logBook = cLogBookService.saveCLogBook(null, "退出系统！",
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
			if (cLoginAction.getSessionMap()
					.containsKey(account.getAccountID())) {
				cLoginAction.getSessionMap().remove(account.getAccountID());
			}
		}
		baseBeanService = null;
		cLogBookService = null;
	}
}
