package hy.ea.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {

	/**
	 * 获取指定键的Cookie值
	 *
	 * @param cookieName
	 * @param req
	 * @return
	 * @return 如果找到Cookie则返回 否则返回null
	 */
	public static String getCookieValue(String cookieName, HttpServletRequest req) {
		Cookie cookie = getCookie(cookieName, req);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * 保存一个对象到Cookie Cookie只在会话内有效
	 * @param cookieName	键
	 * @param cookieValue	值
	 * @param res
	 * @return void
	 */
	public static void setCookie(String cookieName, String cookieValue, HttpServletResponse res) {
		if (cookieValue == null) {
			return;
		}
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(360*24*60);//设置一年有效期
		cookie.setPath("/");
		res.addCookie(cookie);
	}

	/**
	 * 获取指定cookie
	 * @param cookieName	名称
	 * @param req
	 * @return
	 * @return Cookie
	 */
	private static Cookie getCookie(String cookieName, HttpServletRequest req) {
		if (req.getCookies() == null)
			return null;
		for (Cookie cookie : req.getCookies()) {
			if (cookieName.equals(cookie.getName()))
				return cookie;
		}
		return null;
	}

}